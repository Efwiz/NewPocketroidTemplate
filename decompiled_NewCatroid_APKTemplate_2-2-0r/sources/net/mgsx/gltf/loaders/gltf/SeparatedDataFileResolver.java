package net.mgsx.gltf.loaders.gltf;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import net.mgsx.gltf.data.GLTF;
import net.mgsx.gltf.data.data.GLTFBuffer;
import net.mgsx.gltf.data.data.GLTFBufferView;
import net.mgsx.gltf.data.texture.GLTFImage;
import net.mgsx.gltf.loaders.exceptions.GLTFIllegalException;
import net.mgsx.gltf.loaders.shared.data.DataFileResolver;
import net.mgsx.gltf.loaders.shared.texture.PixmapBinaryLoaderHack;

/* JADX INFO: loaded from: classes12.dex */
public class SeparatedDataFileResolver implements DataFileResolver {
    private ObjectMap<Integer, ByteBuffer> bufferMap = new ObjectMap<>();
    private GLTF glModel;
    private FileHandle path;

    @Override // net.mgsx.gltf.loaders.shared.data.DataFileResolver
    public void load(FileHandle fileHandle) {
        this.glModel = (GLTF) new Json().fromJson(GLTF.class, fileHandle);
        FileHandle fileHandleParent = fileHandle.parent();
        this.path = fileHandleParent;
        loadBuffers(fileHandleParent);
    }

    @Override // net.mgsx.gltf.loaders.shared.data.DataFileResolver
    public GLTF getRoot() {
        return this.glModel;
    }

    private ObjectMap<Integer, ByteBuffer> loadBuffers(FileHandle fileHandle) {
        if (this.glModel.buffers != null) {
            for (int i = 0; i < this.glModel.buffers.size; i++) {
                GLTFBuffer gLTFBuffer = this.glModel.buffers.get(i);
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(gLTFBuffer.byteLength);
                byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
                if (gLTFBuffer.uri.startsWith("data:")) {
                    byteBufferAllocate.put(Base64Coder.decode(gLTFBuffer.uri.split(",", 2)[1]));
                } else {
                    byteBufferAllocate.put(fileHandle.child(decodePath(gLTFBuffer.uri)).readBytes());
                }
                this.bufferMap.put(Integer.valueOf(i), byteBufferAllocate);
            }
        }
        return this.bufferMap;
    }

    private String decodePath(String str) {
        int i;
        byte[] bytes = str.getBytes();
        byte[] bArr = new byte[bytes.length];
        int i2 = 0;
        int i3 = 0;
        while (i2 < bytes.length) {
            byte b = bytes[i2];
            if (b == 37) {
                i = i3 + 1;
                bArr[i3] = (byte) Integer.parseInt(str.substring(i2 + 1, i2 + 3), 16);
                i2 += 2;
            } else {
                i = i3 + 1;
                bArr[i3] = b;
            }
            i3 = i;
            i2++;
        }
        try {
            return new String(bArr, 0, i3, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new GdxRuntimeException(e);
        }
    }

    @Override // net.mgsx.gltf.loaders.shared.data.DataFileResolver
    public ByteBuffer getBuffer(int i) {
        return this.bufferMap.get(Integer.valueOf(i));
    }

    @Override // net.mgsx.gltf.loaders.shared.data.DataFileResolver
    public Pixmap load(GLTFImage gLTFImage) {
        if (gLTFImage.uri == null) {
            if (gLTFImage.mimeType == null) {
                throw new GLTFIllegalException("GLTF image: both URI and mimeType cannot be null");
            }
            if (gLTFImage.mimeType.equals("image/png") || gLTFImage.mimeType.equals("image/jpeg")) {
                GLTFBufferView gLTFBufferView = this.glModel.bufferViews.get(gLTFImage.bufferView.intValue());
                ByteBuffer byteBuffer = this.bufferMap.get(gLTFBufferView.buffer, null);
                int i = gLTFBufferView.byteLength;
                byte[] bArr = new byte[i];
                byteBuffer.position(gLTFBufferView.byteOffset);
                byteBuffer.get(bArr, 0, gLTFBufferView.byteLength);
                byteBuffer.rewind();
                return PixmapBinaryLoaderHack.load(bArr, 0, i);
            }
            throw new GLTFIllegalException("GLTF image: unexpected mimeType: " + gLTFImage.mimeType);
        }
        if (gLTFImage.uri.startsWith("data:")) {
            String[] strArrSplit = gLTFImage.uri.split(",", 2);
            System.out.println(strArrSplit[0]);
            byte[] bArrDecode = Base64Coder.decode(strArrSplit[1]);
            return PixmapBinaryLoaderHack.load(bArrDecode, 0, bArrDecode.length);
        }
        return new Pixmap(this.path.child(decodePath(gLTFImage.uri)));
    }

    public FileHandle getImageFile(GLTFImage gLTFImage) {
        if (gLTFImage.uri == null || gLTFImage.uri.startsWith("data:")) {
            return null;
        }
        return this.path.child(decodePath(gLTFImage.uri));
    }
}
