package net.mgsx.gltf.scene3d.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.utils.RenderableSorter;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FlushablePool;
import java.util.Iterator;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;

/* JADX INFO: loaded from: classes12.dex */
public class TransmissionSource implements Disposable {
    private Array<Renderable> allRenderables;
    public final PBRTextureAttribute attribute;
    private ModelBatch batch;
    private Camera camera;
    private FrameBuffer fbo;
    private boolean hasTransmission;
    private int height;
    private FlushablePool<Renderable> renderablePool;
    private Array<Renderable> selectedRenderables;
    private int width;

    public TransmissionSource(ShaderProvider shaderProvider) {
        this(shaderProvider, new SceneRenderableSorter());
    }

    public TransmissionSource(ShaderProvider shaderProvider, RenderableSorter renderableSorter) {
        PBRTextureAttribute pBRTextureAttribute = new PBRTextureAttribute(PBRTextureAttribute.TransmissionSourceTexture);
        this.attribute = pBRTextureAttribute;
        this.allRenderables = new Array<>();
        this.selectedRenderables = new Array<>();
        this.renderablePool = new FlushablePool<Renderable>() { // from class: net.mgsx.gltf.scene3d.scene.TransmissionSource.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.badlogic.gdx.utils.Pool
            public Renderable newObject() {
                return new Renderable();
            }

            @Override // com.badlogic.gdx.utils.FlushablePool, com.badlogic.gdx.utils.Pool
            public Renderable obtain() {
                Renderable renderable = (Renderable) super.obtain();
                renderable.environment = null;
                renderable.material = null;
                renderable.meshPart.set("", null, 0, 0, 0);
                renderable.shader = null;
                renderable.userData = null;
                return renderable;
            }
        };
        this.batch = new ModelBatch(shaderProvider, renderableSorter);
        pBRTextureAttribute.textureDescription.minFilter = Texture.TextureFilter.MipMap;
        pBRTextureAttribute.textureDescription.magFilter = Texture.TextureFilter.Linear;
    }

    protected FrameBuffer createFrameBuffer(int i, int i2) {
        return new FrameBuffer(Pixmap.Format.RGBA8888, i, i2, true);
    }

    public void setSize(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public void begin(Camera camera) {
        this.camera = camera;
        ensureFrameBufferSize(this.width, this.height);
        this.hasTransmission = false;
    }

    private void ensureFrameBufferSize(int i, int i2) {
        if (i <= 0) {
            i = Gdx.graphics.getBackBufferWidth();
        }
        if (i2 <= 0) {
            i2 = Gdx.graphics.getBackBufferHeight();
        }
        FrameBuffer frameBuffer = this.fbo;
        if (frameBuffer != null && frameBuffer.getWidth() == i && this.fbo.getHeight() == i2) {
            return;
        }
        FrameBuffer frameBuffer2 = this.fbo;
        if (frameBuffer2 != null) {
            frameBuffer2.dispose();
        }
        this.fbo = createFrameBuffer(i, i2);
    }

    public void render(Iterable<RenderableProvider> iterable, Environment environment) {
        Iterator<RenderableProvider> it = iterable.iterator();
        while (it.hasNext()) {
            render(it.next(), environment);
        }
    }

    public void render(RenderableProvider renderableProvider, Environment environment) {
        renderableProvider.getRenderables(this.allRenderables, this.renderablePool);
        for (int i = this.allRenderables.size; i < this.allRenderables.size; i++) {
            Renderable renderable = this.allRenderables.get(i);
            if (shouldBeRendered(renderable)) {
                renderable.environment = environment;
                this.selectedRenderables.add(renderable);
            }
        }
    }

    public void render(RenderableProvider renderableProvider) {
        renderableProvider.getRenderables(this.allRenderables, this.renderablePool);
        for (int i = this.allRenderables.size; i < this.allRenderables.size; i++) {
            Renderable renderable = this.allRenderables.get(i);
            if (shouldBeRendered(renderable)) {
                this.selectedRenderables.add(renderable);
            }
        }
    }

    public void end() {
        if (this.hasTransmission) {
            this.fbo.begin();
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            Gdx.gl.glClear(16640);
            this.batch.begin(this.camera);
            Array.ArrayIterator<Renderable> it = this.selectedRenderables.iterator();
            while (it.hasNext()) {
                this.batch.render(it.next());
            }
            this.batch.end();
            this.fbo.end();
            this.fbo.getColorBufferTexture().bind();
            Gdx.gl.glGenerateMipmap(3553);
        }
        this.attribute.textureDescription.texture = this.fbo.getColorBufferTexture();
        this.renderablePool.flush();
        this.selectedRenderables.clear();
        this.allRenderables.clear();
    }

    private boolean shouldBeRendered(Renderable renderable) {
        boolean z = renderable.material.has(PBRTextureAttribute.TransmissionTexture) || (renderable.material.has(PBRFloatAttribute.TransmissionFactor) && ((PBRFloatAttribute) renderable.material.get(PBRFloatAttribute.class, PBRFloatAttribute.TransmissionFactor)).value > 0.0f);
        this.hasTransmission |= z;
        return !z;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        FrameBuffer frameBuffer = this.fbo;
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
        this.batch.dispose();
    }
}
