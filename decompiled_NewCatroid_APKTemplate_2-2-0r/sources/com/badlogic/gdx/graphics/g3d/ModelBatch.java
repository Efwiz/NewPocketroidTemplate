package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.DefaultRenderableSorter;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.g3d.utils.RenderableSorter;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FlushablePool;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/ModelBatch.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ModelBatch implements Disposable {
    protected Camera camera;
    protected final RenderContext context;
    private final boolean ownContext;
    protected final Array<Renderable> renderables;
    protected final RenderablePool renderablesPool;
    protected final ShaderProvider shaderProvider;
    protected final RenderableSorter sorter;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/ModelBatch$RenderablePool.class
      classes8.dex
     */
    protected static class RenderablePool extends FlushablePool<Renderable> {
        protected RenderablePool() {
        }

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
    }

    public ModelBatch(RenderContext renderContext, ShaderProvider shaderProvider, RenderableSorter renderableSorter) {
        this.renderablesPool = new RenderablePool();
        this.renderables = new Array<>();
        this.sorter = renderableSorter == null ? new DefaultRenderableSorter() : renderableSorter;
        this.ownContext = renderContext == null;
        this.context = renderContext == null ? new RenderContext(new DefaultTextureBinder(1, 1)) : renderContext;
        this.shaderProvider = shaderProvider == null ? new DefaultShaderProvider() : shaderProvider;
    }

    public ModelBatch(RenderContext renderContext, ShaderProvider shaderProvider) {
        this(renderContext, shaderProvider, null);
    }

    public ModelBatch(RenderContext renderContext, RenderableSorter renderableSorter) {
        this(renderContext, null, renderableSorter);
    }

    public ModelBatch(RenderContext renderContext) {
        this(renderContext, null, null);
    }

    public ModelBatch(ShaderProvider shaderProvider, RenderableSorter renderableSorter) {
        this(null, shaderProvider, renderableSorter);
    }

    public ModelBatch(RenderableSorter renderableSorter) {
        this(null, null, renderableSorter);
    }

    public ModelBatch(ShaderProvider shaderProvider) {
        this(null, shaderProvider, null);
    }

    public ModelBatch(FileHandle fileHandle, FileHandle fileHandle2) {
        this(null, new DefaultShaderProvider(fileHandle, fileHandle2), null);
    }

    public ModelBatch(String str, String str2) {
        this(null, new DefaultShaderProvider(str, str2), null);
    }

    public ModelBatch() {
        this(null, null, null);
    }

    public void begin(Camera camera) {
        if (this.camera != null) {
            throw new GdxRuntimeException("Call end() first.");
        }
        this.camera = camera;
        if (this.ownContext) {
            this.context.begin();
        }
    }

    public void setCamera(Camera camera) {
        if (this.camera == null) {
            throw new GdxRuntimeException("Call begin() first.");
        }
        if (this.renderables.size > 0) {
            flush();
        }
        this.camera = camera;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public boolean ownsRenderContext() {
        return this.ownContext;
    }

    public RenderContext getRenderContext() {
        return this.context;
    }

    public ShaderProvider getShaderProvider() {
        return this.shaderProvider;
    }

    public RenderableSorter getRenderableSorter() {
        return this.sorter;
    }

    public void flush() {
        this.sorter.sort(this.camera, this.renderables);
        Shader shader = null;
        for (int i = 0; i < this.renderables.size; i++) {
            Renderable renderable = this.renderables.get(i);
            if (shader != renderable.shader) {
                if (shader != null) {
                    shader.end();
                }
                shader = renderable.shader;
                shader.begin(this.camera, this.context);
            }
            shader.render(renderable);
        }
        if (shader != null) {
            shader.end();
        }
        this.renderablesPool.flush();
        this.renderables.clear();
    }

    public void end() {
        flush();
        if (this.ownContext) {
            this.context.end();
        }
        this.camera = null;
    }

    public void render(Renderable renderable) {
        renderable.shader = this.shaderProvider.getShader(renderable);
        this.renderables.add(renderable);
    }

    public void render(RenderableProvider renderableProvider) {
        renderableProvider.getRenderables(this.renderables, this.renderablesPool);
        for (int i = this.renderables.size; i < this.renderables.size; i++) {
            Renderable renderable = this.renderables.get(i);
            renderable.shader = this.shaderProvider.getShader(renderable);
        }
    }

    public <T extends RenderableProvider> void render(Iterable<T> iterable) {
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            render(it.next());
        }
    }

    public void render(RenderableProvider renderableProvider, Environment environment) {
        renderableProvider.getRenderables(this.renderables, this.renderablesPool);
        for (int i = this.renderables.size; i < this.renderables.size; i++) {
            Renderable renderable = this.renderables.get(i);
            renderable.environment = environment;
            renderable.shader = this.shaderProvider.getShader(renderable);
        }
    }

    public <T extends RenderableProvider> void render(Iterable<T> iterable, Environment environment) {
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            render(it.next(), environment);
        }
    }

    public void render(RenderableProvider renderableProvider, Shader shader) {
        renderableProvider.getRenderables(this.renderables, this.renderablesPool);
        for (int i = this.renderables.size; i < this.renderables.size; i++) {
            Renderable renderable = this.renderables.get(i);
            renderable.shader = shader;
            renderable.shader = this.shaderProvider.getShader(renderable);
        }
    }

    public <T extends RenderableProvider> void render(Iterable<T> iterable, Shader shader) {
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            render(it.next(), shader);
        }
    }

    public void render(RenderableProvider renderableProvider, Environment environment, Shader shader) {
        renderableProvider.getRenderables(this.renderables, this.renderablesPool);
        for (int i = this.renderables.size; i < this.renderables.size; i++) {
            Renderable renderable = this.renderables.get(i);
            renderable.environment = environment;
            renderable.shader = shader;
            renderable.shader = this.shaderProvider.getShader(renderable);
        }
    }

    public <T extends RenderableProvider> void render(Iterable<T> iterable, Environment environment, Shader shader) {
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            render(it.next(), environment, shader);
        }
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.shaderProvider.dispose();
    }
}
