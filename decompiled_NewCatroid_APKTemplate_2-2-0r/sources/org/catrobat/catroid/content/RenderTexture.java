package org.catrobat.catroid.content;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.crashinvaders.vfx.VfxManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/RenderTexture.class
  classes14.dex
 */
/* JADX INFO: compiled from: RenderTextureManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0006\u0010O\u001a\u00020PR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001d\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00010\u001e¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001a\u0010\"\u001a\u00020#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u000e\"\u0004\b,\u0010\u0010R\u001a\u0010-\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u000e\"\u0004\b/\u0010\u0010R\u001a\u00100\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u000e\"\u0004\b2\u0010\u0010R\u001c\u00103\u001a\u0004\u0018\u000104X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u0017\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u0007¢\u0006\b\n\u0000\u001a\u0004\b;\u0010\nR\u001a\u0010<\u001a\u00020=X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u001a\u0010B\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010\u000e\"\u0004\bD\u0010\u0010R\u001a\u0010E\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010\u000e\"\u0004\bG\u0010\u0010R\u001c\u0010H\u001a\u0004\u0018\u00010IX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bN\u0010)¨\u0006Q"}, d2 = {"Lorg/catrobat/catroid/content/RenderTexture;", "", "width", "", "height", "(II)V", "actorsToRender", "", "Lcom/badlogic/gdx/scenes/scene2d/Actor;", "getActorsToRender", "()Ljava/util/List;", "autoUpdate", "", "getAutoUpdate", "()Z", "setAutoUpdate", "(Z)V", "camera2D", "Lcom/badlogic/gdx/graphics/OrthographicCamera;", "getCamera2D", "()Lcom/badlogic/gdx/graphics/OrthographicCamera;", "setCamera2D", "(Lcom/badlogic/gdx/graphics/OrthographicCamera;)V", "camera3D", "Lcom/badlogic/gdx/graphics/PerspectiveCamera;", "getCamera3D", "()Lcom/badlogic/gdx/graphics/PerspectiveCamera;", "setCamera3D", "(Lcom/badlogic/gdx/graphics/PerspectiveCamera;)V", "customUniforms", "", "", "getCustomUniforms", "()Ljava/util/Map;", "fbo", "Lcom/badlogic/gdx/graphics/glutils/FrameBuffer;", "getFbo", "()Lcom/badlogic/gdx/graphics/glutils/FrameBuffer;", "setFbo", "(Lcom/badlogic/gdx/graphics/glutils/FrameBuffer;)V", "getHeight", "()I", "needsUpdate", "getNeedsUpdate", "setNeedsUpdate", "render2D", "getRender2D", "setRender2D", "render3D", "getRender3D", "setRender3D", "shader", "Lcom/badlogic/gdx/graphics/glutils/ShaderProgram;", "getShader", "()Lcom/badlogic/gdx/graphics/glutils/ShaderProgram;", "setShader", "(Lcom/badlogic/gdx/graphics/glutils/ShaderProgram;)V", "spritesToRender", "Lorg/catrobat/catroid/content/Sprite;", "getSpritesToRender", "textureRegion", "Lcom/badlogic/gdx/graphics/g2d/TextureRegion;", "getTextureRegion", "()Lcom/badlogic/gdx/graphics/g2d/TextureRegion;", "setTextureRegion", "(Lcom/badlogic/gdx/graphics/g2d/TextureRegion;)V", "use3DPostProcessing", "getUse3DPostProcessing", "setUse3DPostProcessing", "useMipmaps", "getUseMipmaps", "setUseMipmaps", "vfxManager", "Lcom/crashinvaders/vfx/VfxManager;", "getVfxManager", "()Lcom/crashinvaders/vfx/VfxManager;", "setVfxManager", "(Lcom/crashinvaders/vfx/VfxManager;)V", "getWidth", "dispose", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RenderTexture {
    private OrthographicCamera camera2D;
    private PerspectiveCamera camera3D;
    private FrameBuffer fbo;
    private final int height;
    private boolean render3D;
    private ShaderProgram shader;
    private TextureRegion textureRegion;
    private boolean use3DPostProcessing;
    private boolean useMipmaps;
    private VfxManager vfxManager;
    private final int width;
    private final List<Sprite> spritesToRender = new ArrayList();
    private final List<Actor> actorsToRender = new ArrayList();
    private boolean autoUpdate = true;
    private boolean needsUpdate = true;
    private boolean render2D = true;
    private final Map<String, Object> customUniforms = new LinkedHashMap();

    public RenderTexture(int i, int i2) {
        this.width = i;
        this.height = i2;
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, i, i2, true);
        this.camera2D = new OrthographicCamera(i, i2);
        this.camera3D = new PerspectiveCamera(67.0f, i, i2);
        TextureRegion textureRegion = new TextureRegion(this.fbo.getColorBufferTexture());
        textureRegion.flip(false, true);
        this.textureRegion = textureRegion;
        this.camera3D.near = 0.1f;
        this.camera3D.far = 2500.0f;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getWidth() {
        return this.width;
    }

    public final FrameBuffer getFbo() {
        return this.fbo;
    }

    public final void setFbo(FrameBuffer frameBuffer) {
        Intrinsics.checkNotNullParameter(frameBuffer, "<set-?>");
        this.fbo = frameBuffer;
    }

    public final OrthographicCamera getCamera2D() {
        return this.camera2D;
    }

    public final void setCamera2D(OrthographicCamera orthographicCamera) {
        Intrinsics.checkNotNullParameter(orthographicCamera, "<set-?>");
        this.camera2D = orthographicCamera;
    }

    public final PerspectiveCamera getCamera3D() {
        return this.camera3D;
    }

    public final void setCamera3D(PerspectiveCamera perspectiveCamera) {
        Intrinsics.checkNotNullParameter(perspectiveCamera, "<set-?>");
        this.camera3D = perspectiveCamera;
    }

    public final TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    public final void setTextureRegion(TextureRegion textureRegion) {
        Intrinsics.checkNotNullParameter(textureRegion, "<set-?>");
        this.textureRegion = textureRegion;
    }

    public final List<Sprite> getSpritesToRender() {
        return this.spritesToRender;
    }

    public final List<Actor> getActorsToRender() {
        return this.actorsToRender;
    }

    public final boolean getAutoUpdate() {
        return this.autoUpdate;
    }

    public final void setAutoUpdate(boolean z) {
        this.autoUpdate = z;
    }

    public final boolean getNeedsUpdate() {
        return this.needsUpdate;
    }

    public final void setNeedsUpdate(boolean z) {
        this.needsUpdate = z;
    }

    public final boolean getRender2D() {
        return this.render2D;
    }

    public final void setRender2D(boolean z) {
        this.render2D = z;
    }

    public final boolean getRender3D() {
        return this.render3D;
    }

    public final void setRender3D(boolean z) {
        this.render3D = z;
    }

    public final ShaderProgram getShader() {
        return this.shader;
    }

    public final void setShader(ShaderProgram shaderProgram) {
        this.shader = shaderProgram;
    }

    public final Map<String, Object> getCustomUniforms() {
        return this.customUniforms;
    }

    public final boolean getUseMipmaps() {
        return this.useMipmaps;
    }

    public final void setUseMipmaps(boolean z) {
        this.useMipmaps = z;
    }

    public final boolean getUse3DPostProcessing() {
        return this.use3DPostProcessing;
    }

    public final void setUse3DPostProcessing(boolean z) {
        this.use3DPostProcessing = z;
    }

    public final VfxManager getVfxManager() {
        return this.vfxManager;
    }

    public final void setVfxManager(VfxManager vfxManager) {
        this.vfxManager = vfxManager;
    }

    public final void dispose() {
        this.fbo.dispose();
        ShaderProgram shaderProgram = this.shader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
        }
        VfxManager vfxManager = this.vfxManager;
        if (vfxManager != null) {
            vfxManager.dispose();
        }
        this.spritesToRender.clear();
        this.actorsToRender.clear();
        this.customUniforms.clear();
    }
}
