package org.catrobat.catroid.utils;

import android.util.Log;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/GlobalShaderManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: GlobalShaderManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0013J\u0006\u0010\u0015\u001a\u00020\u0013J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\"\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\t\u001a\u0004\u0018\u00010\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\"\u0010\u000e\u001a\u0004\u0018\u00010\u00042\b\u0010\t\u001a\u0004\u0018\u00010\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0006R\"\u0010\u0010\u001a\u0004\u0018\u00010\u00042\b\u0010\t\u001a\u0004\u0018\u00010\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0006¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/utils/GlobalShaderManager;", "", "()V", "DEFAULT_TEST_SHADER", "", "getDEFAULT_TEST_SHADER", "()Ljava/lang/String;", "DEFAULT_VERTEX_SHADER", "getDEFAULT_VERTEX_SHADER", "<set-?>", "Lcom/badlogic/gdx/graphics/glutils/ShaderProgram;", "customSceneShader", "getCustomSceneShader", "()Lcom/badlogic/gdx/graphics/glutils/ShaderProgram;", "fragmentShaderCode", "getFragmentShaderCode", "vertexShaderCode", "getVertexShaderCode", LineReader.CLEAR, "", "dispose", "resetToDefault", "setCustomShader", "fragmentCode", "vertexCode", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GlobalShaderManager {
    private static ShaderProgram customSceneShader;
    private static String fragmentShaderCode;
    private static String vertexShaderCode;
    public static final GlobalShaderManager INSTANCE = new GlobalShaderManager();
    private static final String DEFAULT_VERTEX_SHADER = "attribute vec4 a_position;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans; // <--- ЭТО ВАЖНО\nvarying vec2 v_texCoords;\n\nvoid main() {\n    v_texCoords = a_texCoord0;\n    gl_Position = u_projTrans * a_position;\n}";
    private static final String DEFAULT_TEST_SHADER = "// Код для вашего фрагментного шейдера\n#ifdef GL_ES\nprecision mediump float;\n#endif\n\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\n\nvoid main() {\n    // 1. Пытаемся получить цвет из текстуры FBO\n    vec4 sceneColor = texture2D(u_texture, v_texCoords);\n\n    // 2. Генерируем \"контрольный\" цвет-градиент, который не зависит от текстуры.\n    // Он доказывает, что сам шейдер и его координаты работают.\n    vec4 gradientColor = vec4(v_texCoords.x, v_texCoords.y, 0.0, 1.0);\n\n    // 3. Смешиваем их. 50% от сцены, 50% от градиента.\n    gl_FragColor = mix(sceneColor, gradientColor, 0.5);\n}";

    private GlobalShaderManager() {
    }

    public final ShaderProgram getCustomSceneShader() {
        return customSceneShader;
    }

    public final String getFragmentShaderCode() {
        return fragmentShaderCode;
    }

    public final String getVertexShaderCode() {
        return vertexShaderCode;
    }

    public final String getDEFAULT_VERTEX_SHADER() {
        return DEFAULT_VERTEX_SHADER;
    }

    public final String getDEFAULT_TEST_SHADER() {
        return DEFAULT_TEST_SHADER;
    }

    public final String setCustomShader(String fragmentCode, String vertexCode) {
        Intrinsics.checkNotNullParameter(fragmentCode, "fragmentCode");
        Intrinsics.checkNotNullParameter(vertexCode, "vertexCode");
        Log.d("ShaderDebug", "--- GlobalShaderManager.setCustomShader CALLED ---");
        ShaderProgram shaderProgram = customSceneShader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
        }
        customSceneShader = null;
        if (StringsKt.isBlank(fragmentCode)) {
            fragmentShaderCode = null;
            Log.d("ShaderDebug", "Fragment code is blank. Resetting shader.");
            return null;
        }
        if (StringsKt.isBlank(vertexCode)) {
            vertexShaderCode = DEFAULT_VERTEX_SHADER;
        } else {
            vertexShaderCode = vertexCode;
        }
        Log.d("ShaderDebug", "Attempting to compile shader.");
        ShaderProgram shaderProgram2 = new ShaderProgram(vertexShaderCode, fragmentCode);
        if (!shaderProgram2.isCompiled()) {
            String log = shaderProgram2.getLog();
            Log.e("ShaderDebug", "SHADER COMPILATION FAILED! Log: " + log);
            shaderProgram2.dispose();
            vertexShaderCode = null;
            return log;
        }
        Log.d("ShaderDebug", "SHADER COMPILED SUCCESSFULLY!");
        customSceneShader = shaderProgram2;
        fragmentShaderCode = fragmentCode;
        return null;
    }

    public final void resetToDefault() {
        ShaderProgram shaderProgram = customSceneShader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
        }
        customSceneShader = null;
        fragmentShaderCode = null;
        vertexShaderCode = null;
    }

    public final void dispose() {
        ShaderProgram shaderProgram = customSceneShader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
        }
        customSceneShader = null;
    }

    public final void clear() {
        ShaderProgram shaderProgram = customSceneShader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
        }
        customSceneShader = null;
    }
}
