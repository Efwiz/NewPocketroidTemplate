package com.crashinvaders.vfx.gl;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class VfxGLUtils {
    private static final String TAG = "VfxGLUtils";
    public static boolean enableGLQueryStates;
    public static VfxGlExtension glExtension;
    private static final IntBuffer tmpIntBuf = ByteBuffer.allocateDirect(64).order(ByteOrder.nativeOrder()).asIntBuffer();
    private static final ByteBuffer tmpByteBuffer = BufferUtils.newByteBuffer(32);
    private static final VfxGlViewport tmpViewport = new VfxGlViewport();
    public static String prependVertexCode = "";
    public static String prependFragmentCode = "";

    static {
        if (Gdx.f82app.getType() == Application.ApplicationType.WebGL) {
            try {
                glExtension = (VfxGlExtension) ClassReflection.newInstance(ClassReflection.forName("com.crashinvaders.vfx.gwt.GwtVfxGlExtension"));
                Gdx.f82app.log("VfxGLUtils", "GWT GL Extension initialized.");
            } catch (ReflectionException e) {
                throw new GdxRuntimeException("Cannot find GwtVfxGlExtension class.Are you sure you connected \"gdx-vfx-gwt\" library? \nPlease visit GWT setup wiki page for instructions: https://github.com/crashinvaders/gdx-vfx/wiki/GWT-HTML-Library-Integration", e);
            }
        } else {
            glExtension = new DefaultVfxGlExtension();
        }
        enableGLQueryStates = false;
    }

    public static int getBoundFboHandle() {
        return glExtension.getBoundFboHandle();
    }

    public static VfxGlViewport getViewport() {
        IntBuffer intBuffer = tmpIntBuf;
        Gdx.gl.glGetIntegerv(GL20.GL_VIEWPORT, intBuffer);
        return tmpViewport.set(intBuffer.get(0), intBuffer.get(1), intBuffer.get(2), intBuffer.get(3));
    }

    public static ShaderProgram compileShader(FileHandle fileHandle, FileHandle fileHandle2) {
        return compileShader(fileHandle, fileHandle2, "");
    }

    public static ShaderProgram compileShader(FileHandle fileHandle, FileHandle fileHandle2, String str) {
        if (fileHandle2 == null) {
            throw new IllegalArgumentException("Vertex shader file cannot be null.");
        }
        if (fileHandle == null) {
            throw new IllegalArgumentException("Fragment shader file cannot be null.");
        }
        if (str == null) {
            throw new IllegalArgumentException("Defines cannot be null.");
        }
        StringBuilder sb = new StringBuilder("Compiling \"");
        sb.append(fileHandle.name()).append('/').append(fileHandle2.name()).append('\"');
        if (str.length() > 0) {
            sb.append(" w/ (").append(str.replace("\n", ", ")).append(Constant.AFTER_QUTO);
        }
        sb.append("...");
        Gdx.f82app.log(TAG, sb.toString());
        ShaderProgram shaderProgram = new ShaderProgram((prependVertexCode + str) + "\n" + fileHandle.readString(), (prependFragmentCode + str) + "\n" + fileHandle2.readString());
        if (shaderProgram.isCompiled()) {
            return shaderProgram;
        }
        throw new GdxRuntimeException("Shader compile error: " + fileHandle.name() + "/" + fileHandle2.name() + "\n" + shaderProgram.getLog());
    }

    public static boolean isGLEnabled(int i) {
        boolean z = false;
        if (!enableGLQueryStates) {
            return false;
        }
        if (i == 3042) {
            GL20 gl20 = Gdx.gl20;
            ByteBuffer byteBuffer = tmpByteBuffer;
            gl20.glGetBooleanv(GL20.GL_BLEND, byteBuffer);
            z = byteBuffer.get() == 1;
        }
        return z;
    }
}
