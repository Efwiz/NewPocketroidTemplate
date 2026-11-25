package net.mgsx.gltf.loaders.glb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.LittleEndianInputStream;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import net.mgsx.gltf.data.GLTF;
import net.mgsx.gltf.data.data.GLTFBufferView;
import net.mgsx.gltf.data.texture.GLTFImage;
import net.mgsx.gltf.loaders.exceptions.GLTFIllegalException;
import net.mgsx.gltf.loaders.exceptions.GLTFRuntimeException;
import net.mgsx.gltf.loaders.shared.GLTFLoaderBase;
import net.mgsx.gltf.loaders.shared.data.DataFileResolver;
import net.mgsx.gltf.loaders.shared.texture.PixmapBinaryLoaderHack;

/* JADX INFO: loaded from: classes12.dex */
public class BinaryDataFileResolver implements DataFileResolver {
    private ObjectMap<Integer, ByteBuffer> bufferMap = new ObjectMap<>();
    private GLTF glModel;

    @Override // net.mgsx.gltf.loaders.shared.data.DataFileResolver
    public void load(FileHandle fileHandle) {
        load(fileHandle.read());
    }

    public void load(byte[] bArr) {
        load(new ByteArrayInputStream(bArr));
    }

    public void load(InputStream inputStream) {
        load(new LittleEndianInputStream(inputStream));
    }

    public void load(LittleEndianInputStream littleEndianInputStream) {
        try {
            try {
                loadInternal(littleEndianInputStream);
                StreamUtils.closeQuietly(littleEndianInputStream);
            } catch (IOException e) {
                throw new GLTFRuntimeException(e);
            }
        } catch (Throwable th) {
            StreamUtils.closeQuietly(littleEndianInputStream);
            throw th;
        }
    }

    private void loadInternal(LittleEndianInputStream littleEndianInputStream) throws IOException {
        if (littleEndianInputStream.readInt() != 1179937895) {
            throw new GLTFIllegalException("bad magic");
        }
        if (littleEndianInputStream.readInt() != 2) {
            throw new GLTFIllegalException("bad version");
        }
        long j = littleEndianInputStream.readInt();
        byte[] bArr = new byte[1048576];
        String str = null;
        int i = 12;
        while (i < j) {
            int i2 = littleEndianInputStream.readInt();
            int i3 = littleEndianInputStream.readInt();
            int i4 = i + 8;
            if (i3 == 1313821514) {
                byte[] bArr2 = new byte[i2];
                littleEndianInputStream.readFully(bArr2, 0, i2);
                str = new String(bArr2);
            } else if (i3 == 5130562) {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i2);
                byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
                int i5 = i2;
                while (i5 > 0) {
                    int i6 = littleEndianInputStream.read(bArr, 0, Math.min(1048576, i5));
                    if (i6 == -1) {
                        break;
                    }
                    byteBufferAllocate.put(bArr, 0, i6);
                    i5 -= i6;
                }
                if (i5 > 0) {
                    throw new GLTFIllegalException("premature end of file");
                }
                byteBufferAllocate.flip();
                ObjectMap<Integer, ByteBuffer> objectMap = this.bufferMap;
                objectMap.put(Integer.valueOf(objectMap.size), byteBufferAllocate);
            } else {
                Gdx.f82app.log(GLTFLoaderBase.TAG, "skip buffer type " + i3);
                if (i2 > 0) {
                    littleEndianInputStream.skip(i2);
                }
            }
            i = i4 + i2;
        }
        this.glModel = (GLTF) new Json().fromJson(GLTF.class, str);
    }

    @Override // net.mgsx.gltf.loaders.shared.data.DataFileResolver
    public GLTF getRoot() {
        return this.glModel;
    }

    @Override // net.mgsx.gltf.loaders.shared.data.DataFileResolver
    public ByteBuffer getBuffer(int i) {
        return this.bufferMap.get(Integer.valueOf(i));
    }

    @Override // net.mgsx.gltf.loaders.shared.data.DataFileResolver
    public Pixmap load(GLTFImage gLTFImage) {
        if (gLTFImage.bufferView != null) {
            GLTFBufferView gLTFBufferView = this.glModel.bufferViews.get(gLTFImage.bufferView.intValue());
            ByteBuffer byteBuffer = this.bufferMap.get(gLTFBufferView.buffer);
            byteBuffer.position(gLTFBufferView.byteOffset);
            int i = gLTFBufferView.byteLength;
            byte[] bArr = new byte[i];
            byteBuffer.get(bArr);
            return PixmapBinaryLoaderHack.load(bArr, 0, i);
        }
        throw new GLTFIllegalException("GLB image should have bufferView");
    }
}
