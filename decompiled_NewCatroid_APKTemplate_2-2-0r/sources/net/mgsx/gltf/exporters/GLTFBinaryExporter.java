package net.mgsx.gltf.exporters;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import net.mgsx.gltf.data.data.GLTFBuffer;
import net.mgsx.gltf.data.data.GLTFBufferView;
import net.mgsx.gltf.data.texture.GLTFImage;
import net.mgsx.gltf.loaders.exceptions.GLTFRuntimeException;
import net.mgsx.gltf.loaders.exceptions.GLTFUnsupportedException;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: loaded from: classes12.dex */
class GLTFBinaryExporter {
    private final GLTFExporterConfig config;
    private Buffer currentBuffer;
    private FileHandle folder;
    private final Array<ByteBuffer> buffers = new Array<>();
    final Array<GLTFBufferView> views = new Array<>();

    public GLTFBinaryExporter(FileHandle fileHandle, GLTFExporterConfig gLTFExporterConfig) {
        this.config = gLTFExporterConfig;
        this.folder = fileHandle;
    }

    void reset() {
        this.buffers.clear();
        this.views.clear();
        this.currentBuffer = null;
    }

    private ByteBuffer createBuffer() {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(this.config.maxBinaryFileSize);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        return byteBufferAllocate;
    }

    private ByteBuffer begin(int i) {
        if (this.buffers.size == 0) {
            ByteBuffer byteBufferCreateBuffer = createBuffer();
            this.buffers.add(byteBufferCreateBuffer);
            return byteBufferCreateBuffer;
        }
        ByteBuffer byteBufferPeek = this.buffers.peek();
        if (byteBufferPeek.remaining() >= i) {
            return byteBufferPeek;
        }
        ByteBuffer byteBufferCreateBuffer2 = createBuffer();
        this.buffers.add(byteBufferCreateBuffer2);
        return byteBufferCreateBuffer2;
    }

    public FloatBuffer beginFloats(int i) {
        FloatBuffer floatBufferAsFloatBuffer = begin(i * 4).asFloatBuffer();
        this.currentBuffer = floatBufferAsFloatBuffer;
        return floatBufferAsFloatBuffer;
    }

    public ShortBuffer beginShorts(int i) {
        ShortBuffer shortBufferAsShortBuffer = begin(i * 2).asShortBuffer();
        this.currentBuffer = shortBufferAsShortBuffer;
        return shortBufferAsShortBuffer;
    }

    public int end() {
        int iPosition;
        GLTFBufferView gLTFBufferView = new GLTFBufferView();
        gLTFBufferView.buffer = Integer.valueOf(this.buffers.size - 1);
        int iPosition2 = this.buffers.peek().position();
        gLTFBufferView.byteOffset = iPosition2;
        Buffer buffer = this.currentBuffer;
        if (buffer instanceof FloatBuffer) {
            iPosition = buffer.position() * 4;
        } else if (buffer instanceof ShortBuffer) {
            iPosition = buffer.position() * 2;
        } else {
            throw new GLTFUnsupportedException("bad buffer type");
        }
        this.currentBuffer = null;
        gLTFBufferView.byteLength = iPosition;
        this.buffers.peek().position(iPosition2 + iPosition);
        this.views.add(gLTFBufferView);
        return this.views.size - 1;
    }

    public Array<GLTFBuffer> flushAllToFiles(String str) {
        Array<GLTFBuffer> array = new Array<>();
        Array.ArrayIterator<ByteBuffer> it = this.buffers.iterator();
        int i = 0;
        while (it.hasNext()) {
            ByteBuffer next = it.next();
            GLTFBuffer gLTFBuffer = new GLTFBuffer();
            gLTFBuffer.byteLength = next.position();
            gLTFBuffer.uri = (this.buffers.size == 1 ? new StringBuilder().append(str) : new StringBuilder().append(str).append(i + 1)).append(".bin").toString();
            byte[] bArr = new byte[next.position()];
            next.flip();
            next.get(bArr);
            this.folder.child(gLTFBuffer.uri).writeBytes(bArr, false);
            array.add(gLTFBuffer);
            i++;
        }
        return array;
    }

    public void export(GLTFImage gLTFImage, Texture texture, String str) {
        String str2 = str + Constants.DEFAULT_IMAGE_EXTENSION;
        gLTFImage.uri = str2;
        FileHandle fileHandleChild = this.folder.child(str2);
        FrameBuffer frameBuffer = new FrameBuffer(texture.getTextureData().getFormat(), texture.getWidth(), texture.getHeight(), false);
        frameBuffer.begin();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(16384);
        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.getProjectionMatrix().setToOrtho2D(0.0f, 0.0f, 1.0f, 1.0f);
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, 1);
        spriteBatch.begin();
        spriteBatch.draw(texture, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f);
        spriteBatch.end();
        Pixmap frameBufferPixmap = ScreenUtils.getFrameBufferPixmap(0, 0, texture.getWidth(), texture.getHeight());
        frameBuffer.end();
        spriteBatch.dispose();
        frameBuffer.dispose();
        savePNG(fileHandleChild, frameBufferPixmap);
        frameBufferPixmap.dispose();
    }

    public static void savePNG(FileHandle fileHandle, Pixmap pixmap) {
        if (Gdx.f82app.getType() == Application.ApplicationType.WebGL) {
            throw new GLTFUnsupportedException("saving pixmap not supported for WebGL");
        }
        try {
            ClassReflection.getMethod(ClassReflection.forName("com.badlogic.gdx.graphics.PixmapIO"), "writePNG", FileHandle.class, Pixmap.class).invoke(null, fileHandle, pixmap);
        } catch (ReflectionException e) {
            throw new GLTFRuntimeException(e);
        }
    }
}
