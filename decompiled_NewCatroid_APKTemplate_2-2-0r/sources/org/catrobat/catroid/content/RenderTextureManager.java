package org.catrobat.catroid.content;

import android.util.Log;
import androidx.constraintlayout.motion.widget.Key;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.ShowTextActor;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.utils.BufferVideoRecorder;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/RenderTextureManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: RenderTextureManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\"\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&J\u0018\u0010'\u001a\u00020#2\u0006\u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u0019H\u0007J\u0018\u0010*\u001a\u00020#2\u0006\u0010+\u001a\u00020\u00072\u0006\u0010,\u001a\u00020-H\u0002J\u0006\u0010.\u001a\u00020#J\u001e\u0010/\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00192\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000201J\u000e\u00103\u001a\u0002012\u0006\u0010$\u001a\u00020\u0019J\u0010\u00104\u001a\u0004\u0018\u0001052\u0006\u0010$\u001a\u00020\u0019J\u000e\u00106\u001a\u0002012\u0006\u0010$\u001a\u00020\u0019J\b\u00107\u001a\u00020#H\u0002J\u0016\u00108\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&J\u0018\u00109\u001a\u00020#2\u0006\u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u0019H\u0007J\u000e\u0010:\u001a\u00020#2\u0006\u0010,\u001a\u00020-J\u0016\u0010;\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010<\u001a\u00020\u0019J\u0016\u0010=\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010>\u001a\u00020\nJ\u0016\u0010?\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010@\u001a\u00020\nJ\u001e\u0010A\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010B\u001a\u00020\n2\u0006\u0010C\u001a\u00020\nJ\u0016\u0010D\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010@\u001a\u00020\nJ\"\u0010E\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00192\b\u0010F\u001a\u0004\u0018\u00010\u00192\b\u0010G\u001a\u0004\u0018\u00010\u0019J \u0010H\u001a\u00020#2\u0006\u0010(\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\u00192\u0006\u0010I\u001a\u00020\u0001H\u0007J.\u0010J\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\u00042\u0006\u0010M\u001a\u00020\u00042\u0006\u0010N\u001a\u00020\u0004JF\u0010O\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00192\u0006\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\u00042\u0006\u0010P\u001a\u00020\u00042\u0006\u0010Q\u001a\u00020\u00042\u0006\u0010R\u001a\u00020\u00042\u0006\u0010S\u001a\u00020\u00042\u0006\u0010T\u001a\u00020\u0004J\u0018\u0010U\u001a\u00020#2\u0006\u0010)\u001a\u00020\u00192\u0006\u0010V\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u000b\u0010\u0002\u001a\u0004\b\t\u0010\f\"\u0004\b\r\u0010\u000eR$\u0010\u000f\u001a\u00020\n8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0010\u0010\u0002\u001a\u0004\b\u000f\u0010\f\"\u0004\b\u0011\u0010\u000eR$\u0010\u0012\u001a\u00020\n8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0013\u0010\u0002\u001a\u0004\b\u0012\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001e\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\fR\u001d\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00070\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006W"}, d2 = {"Lorg/catrobat/catroid/content/RenderTextureManager;", "", "()V", "GLOBAL_ROTATION_FIX", "", "activeTexturesList", "Ljava/util/ArrayList;", "Lorg/catrobat/catroid/content/RenderTexture;", "Lkotlin/collections/ArrayList;", "isMain2DRenderEnabled", "", "isMain2DRenderEnabled$annotations", "()Z", "setMain2DRenderEnabled", "(Z)V", "isMain3DRenderEnabled", "isMain3DRenderEnabled$annotations", "setMain3DRenderEnabled", "isMainFast2DRenderEnabled", "isMainFast2DRenderEnabled$annotations", "setMainFast2DRenderEnabled", "<set-?>", "isRenderingToBuffer", "renderTextures", "", "", "getRenderTextures", "()Ljava/util/Map;", "shaderCamera", "Lcom/badlogic/gdx/graphics/OrthographicCamera;", "tempFbo", "Lcom/badlogic/gdx/graphics/glutils/FrameBuffer;", "tempMatrix", "Lcom/badlogic/gdx/math/Matrix4;", "addSpriteToTarget", "", "name", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "addVariableTextToTarget", "bufferName", "variableName", "applyShaderPostProcessing", "target", "batch", "Lcom/badlogic/gdx/graphics/g2d/Batch;", "clearAll", "createRenderTarget", "width", "", "height", "getHeight", "getTextureRegion", "Lcom/badlogic/gdx/graphics/g2d/TextureRegion;", "getWidth", "rebuildActiveTexturesList", "removeSpriteFromTarget", "removeVariableTextFromTarget", "renderAllTargets", "saveBufferToFile", PsiTreeChangeEvent.PROP_FILE_NAME, "setAutoUpdate", "auto", "setBufferMipmapping", CompilerOptions.ENABLED, "setBufferMode", "r2d", "r3d", "setBufferPostProcessing", "setBufferShader", "vertexCode", "fragmentCode", "setBufferShaderUniform", "value", "setTargetCamera2D", "x", "y", "zoom", Key.ROTATION, "setTargetCamera3D", CompressorStreamFactory.Z, "yaw", "pitch", "roll", "fov", "setTextBufferOnly", "onlyInBuffer", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RenderTextureManager {
    private static final float GLOBAL_ROTATION_FIX = 90.0f;
    private static boolean isRenderingToBuffer;
    private static FrameBuffer tempFbo;
    public static final RenderTextureManager INSTANCE = new RenderTextureManager();
    private static final Map<String, RenderTexture> renderTextures = new LinkedHashMap();
    private static final ArrayList<RenderTexture> activeTexturesList = new ArrayList<>();
    private static boolean isMain2DRenderEnabled = true;
    private static boolean isMainFast2DRenderEnabled = true;
    private static boolean isMain3DRenderEnabled = true;
    private static final Matrix4 tempMatrix = new Matrix4();
    private static final OrthographicCamera shaderCamera = new OrthographicCamera();

    @JvmStatic
    public static /* synthetic */ void isMain2DRenderEnabled$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isMain3DRenderEnabled$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isMainFast2DRenderEnabled$annotations() {
    }

    private RenderTextureManager() {
    }

    public final Map<String, RenderTexture> getRenderTextures() {
        return renderTextures;
    }

    public final boolean isRenderingToBuffer() {
        return isRenderingToBuffer;
    }

    public static final boolean isMain2DRenderEnabled() {
        return isMain2DRenderEnabled;
    }

    public static final void setMain2DRenderEnabled(boolean z) {
        isMain2DRenderEnabled = z;
    }

    public static final boolean isMainFast2DRenderEnabled() {
        return isMainFast2DRenderEnabled;
    }

    public static final void setMainFast2DRenderEnabled(boolean z) {
        isMainFast2DRenderEnabled = z;
    }

    public static final boolean isMain3DRenderEnabled() {
        return isMain3DRenderEnabled;
    }

    public static final void setMain3DRenderEnabled(boolean z) {
        isMain3DRenderEnabled = z;
    }

    public final void createRenderTarget(final String name2, final int width, final int height) {
        Intrinsics.checkNotNullParameter(name2, "name");
        RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture != null && renderTexture.getWidth() == width && renderTexture.getHeight() == height) {
            return;
        }
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.RenderTextureManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                RenderTextureManager.createRenderTarget$lambda$0(name2, width, height);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createRenderTarget$lambda$0(String name2, int i, int i2) {
        Intrinsics.checkNotNullParameter(name2, "$name");
        RenderTextureManager renderTextureManager = INSTANCE;
        Map<String, RenderTexture> map = renderTextures;
        RenderTexture renderTexture = map.get(name2);
        if (renderTexture != null) {
            renderTexture.dispose();
        }
        map.put(name2, new RenderTexture(i, i2));
        renderTextureManager.rebuildActiveTexturesList();
    }

    private final void rebuildActiveTexturesList() {
        ArrayList<RenderTexture> arrayList = activeTexturesList;
        arrayList.clear();
        arrayList.addAll(renderTextures.values());
    }

    @JvmStatic
    public static final void addVariableTextToTarget(String bufferName, String variableName) {
        StageListener activeStageListener;
        Intrinsics.checkNotNullParameter(bufferName, "bufferName");
        Intrinsics.checkNotNullParameter(variableName, "variableName");
        RenderTexture renderTexture = renderTextures.get(bufferName);
        if (renderTexture == null || (activeStageListener = StageActivity.getActiveStageListener()) == null) {
            return;
        }
        Stage stage = activeStageListener.getStage();
        Array<Actor> actors = stage != null ? stage.getActors() : null;
        if (actors == null) {
            return;
        }
        int i = actors.size;
        for (int i2 = 0; i2 < i; i2++) {
            Actor actor = actors.get(i2);
            if ((actor instanceof ShowTextActor) && StringsKt.equals(((ShowTextActor) actor).getVariableNameToCompare(), StringsKt.trim((CharSequence) variableName).toString(), true)) {
                if (renderTexture.getActorsToRender().contains(actor)) {
                    return;
                }
                renderTexture.getActorsToRender().add(actor);
                return;
            }
        }
    }

    @JvmStatic
    public static final void setTextBufferOnly(String variableName, boolean onlyInBuffer) {
        Intrinsics.checkNotNullParameter(variableName, "variableName");
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null) {
            return;
        }
        Stage stage = activeStageListener.getStage();
        Array<Actor> actors = stage != null ? stage.getActors() : null;
        if (actors == null) {
            return;
        }
        String string = StringsKt.trim((CharSequence) variableName).toString();
        int i = actors.size;
        for (int i2 = 0; i2 < i; i2++) {
            Actor actor = actors.get(i2);
            if (actor instanceof ShowTextActor) {
                ShowTextActor showTextActor = (ShowTextActor) actor;
                if (StringsKt.equals(showTextActor.getVariableNameToCompare(), string, true)) {
                    showTextActor.setDrawOnlyInBuffer(onlyInBuffer);
                    return;
                }
            }
        }
    }

    @JvmStatic
    public static final void removeVariableTextFromTarget(String bufferName, String variableName) {
        Intrinsics.checkNotNullParameter(bufferName, "bufferName");
        Intrinsics.checkNotNullParameter(variableName, "variableName");
        RenderTexture renderTexture = renderTextures.get(bufferName);
        if (renderTexture == null) {
            return;
        }
        Iterator<Actor> it = renderTexture.getActorsToRender().iterator();
        while (it.hasNext()) {
            Actor next = it.next();
            if ((next instanceof ShowTextActor) && StringsKt.equals(((ShowTextActor) next).getVariableNameToCompare(), StringsKt.trim((CharSequence) variableName).toString(), true)) {
                it.remove();
                return;
            }
        }
    }

    public final void addSpriteToTarget(String name2, Sprite sprite) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture == null || renderTexture.getSpritesToRender().contains(sprite)) {
            return;
        }
        renderTexture.getSpritesToRender().add(sprite);
    }

    public final void removeSpriteFromTarget(String name2, Sprite sprite) {
        List<Sprite> spritesToRender;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture == null || (spritesToRender = renderTexture.getSpritesToRender()) == null) {
            return;
        }
        spritesToRender.remove(sprite);
    }

    public final void setBufferMode(String name2, boolean r2d, boolean r3d) {
        Intrinsics.checkNotNullParameter(name2, "name");
        RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture != null) {
            renderTexture.setRender2D(r2d);
            renderTexture.setRender3D(r3d);
        }
    }

    public final void setBufferMipmapping(String name2, boolean enabled) {
        Intrinsics.checkNotNullParameter(name2, "name");
        RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture == null) {
            return;
        }
        renderTexture.setUseMipmaps(enabled);
    }

    public final void setBufferShader(String name2, final String vertexCode, final String fragmentCode) {
        Intrinsics.checkNotNullParameter(name2, "name");
        final RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture == null) {
            return;
        }
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.RenderTextureManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RenderTextureManager.setBufferShader$lambda$2(renderTexture, vertexCode, fragmentCode);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setBufferShader$lambda$2(RenderTexture target, String str, String str2) {
        String str3;
        Intrinsics.checkNotNullParameter(target, "$target");
        ShaderProgram shader = target.getShader();
        if (shader != null) {
            shader.dispose();
        }
        String str4 = str;
        if (str4 == null || str4.length() == 0 || (str3 = str2) == null || str3.length() == 0) {
            target.setShader(null);
            return;
        }
        ShaderProgram shaderProgram = new ShaderProgram(str, str2);
        if (shaderProgram.isCompiled()) {
            target.setShader(shaderProgram);
            return;
        }
        Log.e("RenderTextureManager", "Ошибка шейдера буфера: " + shaderProgram.getLog());
        shaderProgram.dispose();
        target.setShader(null);
    }

    @JvmStatic
    public static final void setBufferShaderUniform(String bufferName, String name2, Object value) {
        Intrinsics.checkNotNullParameter(bufferName, "bufferName");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        RenderTexture renderTexture = renderTextures.get(bufferName);
        if (renderTexture == null) {
            return;
        }
        renderTexture.getCustomUniforms().put("u_" + name2, value);
    }

    public final void setBufferPostProcessing(String name2, final boolean enabled) {
        Intrinsics.checkNotNullParameter(name2, "name");
        final RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture == null) {
            return;
        }
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        final ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.RenderTextureManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                RenderTextureManager.setBufferPostProcessing$lambda$3(renderTexture, enabled, threeDManager);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setBufferPostProcessing$lambda$3(RenderTexture target, boolean z, ThreeDManager tDM) {
        Intrinsics.checkNotNullParameter(target, "$target");
        Intrinsics.checkNotNullParameter(tDM, "$tDM");
        target.setUse3DPostProcessing(z);
        tDM.setupBufferPipeline(target.getFbo(), target.getWidth(), target.getHeight(), z);
    }

    public final void setTargetCamera2D(String name2, float x, float y, float zoom, float rotation) {
        OrthographicCamera camera2D;
        Intrinsics.checkNotNullParameter(name2, "name");
        RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture == null || (camera2D = renderTexture.getCamera2D()) == null) {
            return;
        }
        camera2D.position.set(x, y, 0.0f);
        camera2D.zoom = zoom;
        camera2D.up.set(0.0f, 1.0f, 0.0f);
        camera2D.direction.set(0.0f, 0.0f, -1.0f);
        camera2D.rotate(rotation + 90.0f);
        camera2D.update();
    }

    public final void setTargetCamera3D(String name2, float x, float y, float z, float yaw, float pitch, float roll, float fov) {
        PerspectiveCamera camera3D;
        Intrinsics.checkNotNullParameter(name2, "name");
        RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture == null || (camera3D = renderTexture.getCamera3D()) == null) {
            return;
        }
        camera3D.position.set(x, y, z);
        camera3D.fieldOfView = fov;
        Quaternion eulerAngles = new Quaternion().setEulerAngles(yaw, pitch, roll);
        camera3D.direction.set(0.0f, 0.0f, -1.0f);
        eulerAngles.transform(camera3D.direction);
        camera3D.up.set(0.0f, 1.0f, 0.0f);
        eulerAngles.transform(camera3D.up);
        camera3D.update();
    }

    public final void setAutoUpdate(String name2, boolean auto) {
        Intrinsics.checkNotNullParameter(name2, "name");
        RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture == null) {
            return;
        }
        renderTexture.setAutoUpdate(auto);
    }

    public final void saveBufferToFile(final String name2, final String fileName) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.RenderTextureManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                RenderTextureManager.saveBufferToFile$lambda$7(name2, fileName);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveBufferToFile$lambda$7(String name2, final String fileName) {
        Intrinsics.checkNotNullParameter(name2, "$name");
        Intrinsics.checkNotNullParameter(fileName, "$fileName");
        final RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture == null) {
            return;
        }
        renderTexture.getFbo().begin();
        final byte[] frameBufferPixels = ScreenUtils.getFrameBufferPixels(0, 0, renderTexture.getWidth(), renderTexture.getHeight(), true);
        renderTexture.getFbo().end();
        new Thread(new Runnable() { // from class: org.catrobat.catroid.content.RenderTextureManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                RenderTextureManager.saveBufferToFile$lambda$7$lambda$6(renderTexture, frameBufferPixels, fileName);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveBufferToFile$lambda$7$lambda$6(RenderTexture target, byte[] bArr, String fileName) {
        Intrinsics.checkNotNullParameter(target, "$target");
        Intrinsics.checkNotNullParameter(fileName, "$fileName");
        Pixmap pixmap = new Pixmap(target.getWidth(), target.getHeight(), Pixmap.Format.RGBA8888);
        ByteBuffer pixels = pixmap.getPixels();
        pixels.clear();
        pixels.put(bArr);
        pixels.position(0);
        FileHandle fileHandleAbsolute = Gdx.files.absolute(ProjectManager.getInstance().getCurrentProject().getFilesDir().getAbsolutePath() + "/" + fileName);
        PixmapIO.writePNG(fileHandleAbsolute, pixmap);
        pixmap.dispose();
        Log.d("RenderTextureManager", "Сохранен скриншот буфера: " + fileHandleAbsolute.path());
    }

    public final TextureRegion getTextureRegion(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture != null) {
            return renderTexture.getTextureRegion();
        }
        return null;
    }

    public final int getWidth(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture != null) {
            return renderTexture.getWidth();
        }
        return 0;
    }

    public final int getHeight(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        RenderTexture renderTexture = renderTextures.get(name2);
        if (renderTexture != null) {
            return renderTexture.getHeight();
        }
        return 0;
    }

    public final void renderAllTargets(Batch batch) {
        Object next;
        String str;
        StageListener activeStageListener;
        ThreeDManager threeDManager;
        Intrinsics.checkNotNullParameter(batch, "batch");
        ArrayList<RenderTexture> arrayList = activeTexturesList;
        if (arrayList.isEmpty()) {
            return;
        }
        isRenderingToBuffer = true;
        tempMatrix.set(batch.getProjectionMatrix());
        boolean zIsDrawing = batch.isDrawing();
        if (zIsDrawing) {
            batch.end();
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            RenderTexture renderTexture = activeTexturesList.get(i);
            Intrinsics.checkNotNullExpressionValue(renderTexture, "get(...)");
            RenderTexture renderTexture2 = renderTexture;
            if (renderTexture2.getAutoUpdate() || renderTexture2.getNeedsUpdate()) {
                if (renderTexture2.getRender3D() && (activeStageListener = StageActivity.getActiveStageListener()) != null && (threeDManager = activeStageListener.getThreeDManager()) != null) {
                    threeDManager.renderSceneForCustomCamera(renderTexture2.getCamera3D(), renderTexture2.getFbo(), renderTexture2.getUse3DPostProcessing());
                }
                if (renderTexture2.getRender2D()) {
                    renderTexture2.getFbo().begin();
                    if (!renderTexture2.getRender3D()) {
                        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                        Gdx.gl.glClear(16640);
                    }
                    renderTexture2.getCamera2D().update();
                    batch.setProjectionMatrix(renderTexture2.getCamera2D().combined);
                    batch.begin();
                    int size2 = renderTexture2.getSpritesToRender().size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        Look look = renderTexture2.getSpritesToRender().get(i2).look;
                        if (look != null) {
                            look.draw(batch, 1.0f);
                        }
                    }
                    int size3 = renderTexture2.getActorsToRender().size();
                    for (int i3 = 0; i3 < size3; i3++) {
                        renderTexture2.getActorsToRender().get(i3).draw(batch, 1.0f);
                    }
                    batch.end();
                    renderTexture2.getFbo().end();
                }
                if (renderTexture2.getShader() != null) {
                    applyShaderPostProcessing(renderTexture2, batch);
                }
                if (renderTexture2.getUseMipmaps()) {
                    renderTexture2.getFbo().getColorBufferTexture().bind();
                    Gdx.gl.glGenerateMipmap(3553);
                    renderTexture2.getFbo().getColorBufferTexture().setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
                }
                renderTexture2.setNeedsUpdate(false);
                Iterator<T> it = renderTextures.entrySet().iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((Map.Entry) next).getValue(), renderTexture2));
                Map.Entry entry = (Map.Entry) next;
                if (entry == null || (str = (String) entry.getKey()) == null) {
                    str = "";
                }
                if (str.length() > 0) {
                    BufferVideoRecorder.INSTANCE.onFrameRendered(str, renderTexture2);
                }
            }
        }
        batch.setProjectionMatrix(tempMatrix);
        if (zIsDrawing) {
            batch.begin();
        }
        isRenderingToBuffer = false;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0029  */
    /* JADX WARN: Code duplicated, block: B:13:0x002d  */
    private final void applyShaderPostProcessing(RenderTexture target, Batch batch) {
        FrameBuffer frameBuffer;
        ShaderProgram shader = target.getShader();
        if (shader == null) {
            return;
        }
        FrameBuffer frameBuffer2 = tempFbo;
        if (frameBuffer2 != null) {
            Intrinsics.checkNotNull(frameBuffer2);
            if (frameBuffer2.getWidth() == target.getWidth()) {
                FrameBuffer frameBuffer3 = tempFbo;
                Intrinsics.checkNotNull(frameBuffer3);
                if (frameBuffer3.getHeight() != target.getHeight()) {
                    frameBuffer = tempFbo;
                    if (frameBuffer != null) {
                        frameBuffer.dispose();
                    }
                    tempFbo = new FrameBuffer(Pixmap.Format.RGBA8888, target.getWidth(), target.getHeight(), true);
                }
            } else {
                frameBuffer = tempFbo;
                if (frameBuffer != null) {
                    frameBuffer.dispose();
                }
                tempFbo = new FrameBuffer(Pixmap.Format.RGBA8888, target.getWidth(), target.getHeight(), true);
            }
        } else {
            frameBuffer = tempFbo;
            if (frameBuffer != null) {
                frameBuffer.dispose();
            }
            tempFbo = new FrameBuffer(Pixmap.Format.RGBA8888, target.getWidth(), target.getHeight(), true);
        }
        FrameBuffer frameBuffer4 = tempFbo;
        Intrinsics.checkNotNull(frameBuffer4);
        frameBuffer4.begin();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(16640);
        OrthographicCamera orthographicCamera = shaderCamera;
        orthographicCamera.setToOrtho(false, target.getWidth(), target.getHeight());
        orthographicCamera.update();
        ShaderProgram shader2 = batch.getShader();
        Matrix4 projectionMatrix = batch.getProjectionMatrix();
        batch.setShader(shader);
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.begin();
        for (Map.Entry<String, Object> entry : target.getCustomUniforms().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Float) {
                shader.setUniformf(key, ((Number) value).floatValue());
            } else if (value instanceof Integer) {
                shader.setUniformi(key, ((Number) value).intValue());
            } else if (value instanceof Vector2) {
                Vector2 vector2 = (Vector2) value;
                shader.setUniformf(key, vector2.x, vector2.y);
            } else if (value instanceof Vector3) {
                Vector3 vector3 = (Vector3) value;
                shader.setUniformf(key, vector3.x, vector3.y, vector3.z);
            } else if (value instanceof Vector4) {
                Vector4 vector4 = (Vector4) value;
                shader.setUniformf(key, vector4.x, vector4.y, vector4.z, vector4.w);
            } else if (value instanceof Color) {
                Color color = (Color) value;
                shader.setUniformf(key, color.r, color.g, color.b, color.a);
            } else if (value instanceof Matrix4) {
                shader.setUniformMatrix(key, (Matrix4) value);
            }
        }
        batch.draw(target.getFbo().getColorBufferTexture(), 0.0f, 0.0f, target.getWidth(), target.getHeight(), 0, 0, target.getWidth(), target.getHeight(), false, true);
        batch.end();
        batch.setShader(shader2);
        batch.setProjectionMatrix(projectionMatrix);
        frameBuffer4.end();
        target.getFbo().begin();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(16640);
        batch.setProjectionMatrix(shaderCamera.combined);
        batch.begin();
        batch.draw(frameBuffer4.getColorBufferTexture(), 0.0f, 0.0f, target.getWidth(), target.getHeight(), 0, 0, target.getWidth(), target.getHeight(), false, true);
        batch.end();
        target.getFbo().end();
    }

    public final void clearAll() {
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        for (RenderTexture renderTexture : renderTextures.values()) {
            if (threeDManager != null) {
                threeDManager.removeBufferPipeline(renderTexture.getFbo());
            }
            renderTexture.dispose();
        }
        renderTextures.clear();
        activeTexturesList.clear();
        FrameBuffer frameBuffer = tempFbo;
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
        tempFbo = null;
        isMain2DRenderEnabled = true;
        isMainFast2DRenderEnabled = true;
        isMain3DRenderEnabled = true;
    }
}
