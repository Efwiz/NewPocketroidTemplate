package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/ImmediateModeRenderer20.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ImmediateModeRenderer20 implements ImmediateModeRenderer {
    private final int colorOffset;
    private final int maxVertices;
    private final Mesh mesh;
    private final int normalOffset;
    private int numSetTexCoords;
    private final int numTexCoords;
    private int numVertices;
    private boolean ownsShader;
    private int primitiveType;
    private final Matrix4 projModelView;
    private ShaderProgram shader;
    private final String[] shaderUniformNames;
    private final int texCoordOffset;
    private int vertexIdx;
    private final int vertexSize;
    private final float[] vertices;

    public ImmediateModeRenderer20(boolean z, boolean z2, int i) {
        this(5000, z, z2, i, createDefaultShader(z, z2, i));
        this.ownsShader = true;
    }

    public ImmediateModeRenderer20(int i, boolean z, boolean z2, int i2) {
        this(i, z, z2, i2, createDefaultShader(z, z2, i2));
        this.ownsShader = true;
    }

    public ImmediateModeRenderer20(int i, boolean z, boolean z2, int i2, ShaderProgram shaderProgram) {
        this.projModelView = new Matrix4();
        this.maxVertices = i;
        this.numTexCoords = i2;
        this.shader = shaderProgram;
        Mesh mesh = new Mesh(false, i, 0, buildVertexAttributes(z, z2, i2));
        this.mesh = mesh;
        this.vertices = new float[i * (mesh.getVertexAttributes().vertexSize / 4)];
        this.vertexSize = mesh.getVertexAttributes().vertexSize / 4;
        this.normalOffset = mesh.getVertexAttribute(8) != null ? mesh.getVertexAttribute(8).offset / 4 : 0;
        this.colorOffset = mesh.getVertexAttribute(4) != null ? mesh.getVertexAttribute(4).offset / 4 : 0;
        this.texCoordOffset = mesh.getVertexAttribute(16) != null ? mesh.getVertexAttribute(16).offset / 4 : 0;
        this.shaderUniformNames = new String[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            this.shaderUniformNames[i3] = "u_sampler" + i3;
        }
    }

    private VertexAttribute[] buildVertexAttributes(boolean z, boolean z2, int i) {
        Array array = new Array();
        array.add(new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE));
        if (z) {
            array.add(new VertexAttribute(8, 3, ShaderProgram.NORMAL_ATTRIBUTE));
        }
        if (z2) {
            array.add(new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE));
        }
        for (int i2 = 0; i2 < i; i2++) {
            array.add(new VertexAttribute(16, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + i2));
        }
        VertexAttribute[] vertexAttributeArr = new VertexAttribute[array.size];
        for (int i3 = 0; i3 < array.size; i3++) {
            vertexAttributeArr[i3] = (VertexAttribute) array.get(i3);
        }
        return vertexAttributeArr;
    }

    public void setShader(ShaderProgram shaderProgram) {
        if (this.ownsShader) {
            this.shader.dispose();
        }
        this.shader = shaderProgram;
        this.ownsShader = false;
    }

    public ShaderProgram getShader() {
        return this.shader;
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public void begin(Matrix4 matrix4, int i) {
        this.projModelView.set(matrix4);
        this.primitiveType = i;
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public void color(Color color) {
        this.vertices[this.vertexIdx + this.colorOffset] = color.toFloatBits();
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public void color(float f, float f2, float f3, float f4) {
        this.vertices[this.vertexIdx + this.colorOffset] = Color.toFloatBits(f, f2, f3, f4);
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public void color(float f) {
        this.vertices[this.vertexIdx + this.colorOffset] = f;
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public void texCoord(float f, float f2) {
        int i = this.vertexIdx + this.texCoordOffset;
        float[] fArr = this.vertices;
        int i2 = this.numSetTexCoords;
        fArr[i + i2] = f;
        fArr[i + i2 + 1] = f2;
        this.numSetTexCoords = i2 + 2;
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public void normal(float f, float f2, float f3) {
        int i = this.vertexIdx + this.normalOffset;
        float[] fArr = this.vertices;
        fArr[i] = f;
        fArr[i + 1] = f2;
        fArr[i + 2] = f3;
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public void vertex(float f, float f2, float f3) {
        int i = this.vertexIdx;
        float[] fArr = this.vertices;
        fArr[i] = f;
        fArr[i + 1] = f2;
        fArr[i + 2] = f3;
        this.numSetTexCoords = 0;
        this.vertexIdx = i + this.vertexSize;
        this.numVertices++;
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public void flush() {
        if (this.numVertices == 0) {
            return;
        }
        this.shader.bind();
        this.shader.setUniformMatrix("u_projModelView", this.projModelView);
        for (int i = 0; i < this.numTexCoords; i++) {
            this.shader.setUniformi(this.shaderUniformNames[i], i);
        }
        this.mesh.setVertices(this.vertices, 0, this.vertexIdx);
        this.mesh.render(this.shader, this.primitiveType);
        this.numSetTexCoords = 0;
        this.vertexIdx = 0;
        this.numVertices = 0;
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public void end() {
        flush();
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public int getNumVertices() {
        return this.numVertices;
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public int getMaxVertices() {
        return this.maxVertices;
    }

    @Override // com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
    public void dispose() {
        ShaderProgram shaderProgram;
        if (this.ownsShader && (shaderProgram = this.shader) != null) {
            shaderProgram.dispose();
        }
        this.mesh.dispose();
    }

    private static String createVertexShader(boolean z, boolean z2, int i) {
        String str;
        String str2 = "";
        StringBuilder sbAppend = new StringBuilder("attribute vec4 a_position;\n").append(z ? "attribute vec3 a_normal;\n" : "");
        if (!z2) {
            str = "";
        } else {
            str = "attribute vec4 a_color;\n";
        }
        String string = sbAppend.append(str).toString();
        for (int i2 = 0; i2 < i; i2++) {
            string = string + "attribute vec2 a_texCoord" + i2 + ";\n";
        }
        StringBuilder sbAppend2 = new StringBuilder().append(string).append("uniform mat4 u_projModelView;\n");
        if (z2) {
            str2 = "varying vec4 v_col;\n";
        }
        String string2 = sbAppend2.append(str2).toString();
        for (int i3 = 0; i3 < i; i3++) {
            string2 = string2 + "varying vec2 v_tex" + i3 + ";\n";
        }
        String str3 = string2 + "void main() {\n   gl_Position = u_projModelView * a_position;\n";
        if (z2) {
            str3 = str3 + "   v_col = a_color;\n   v_col.a *= 255.0 / 254.0;\n";
        }
        for (int i4 = 0; i4 < i; i4++) {
            str3 = str3 + "   v_tex" + i4 + " = a_texCoord" + i4 + ";\n";
        }
        return str3 + "   gl_PointSize = 1.0;\n}\n";
    }

    private static String createFragmentShader(boolean z, boolean z2, int i) {
        String str = z2 ? "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec4 v_col;\n" : "#ifdef GL_ES\nprecision mediump float;\n#endif\n";
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            str = (str + "varying vec2 v_tex" + i3 + ";\n") + "uniform sampler2D u_sampler" + i3 + ";\n";
        }
        String str2 = str + "void main() {\n   gl_FragColor = " + (z2 ? "v_col" : "vec4(1, 1, 1, 1)");
        if (i > 0) {
            str2 = str2 + " * ";
        }
        while (i2 < i) {
            str2 = i2 == i + (-1) ? str2 + " texture2D(u_sampler" + i2 + ",  v_tex" + i2 + Constant.AFTER_QUTO : str2 + " texture2D(u_sampler" + i2 + ",  v_tex" + i2 + ") *";
            i2++;
        }
        return str2 + ";\n}";
    }

    public static ShaderProgram createDefaultShader(boolean z, boolean z2, int i) {
        ShaderProgram shaderProgram = new ShaderProgram(createVertexShader(z, z2, i), createFragmentShader(z, z2, i));
        if (shaderProgram.isCompiled()) {
            return shaderProgram;
        }
        throw new GdxRuntimeException("Error compiling shader: " + shaderProgram.getLog());
    }
}
