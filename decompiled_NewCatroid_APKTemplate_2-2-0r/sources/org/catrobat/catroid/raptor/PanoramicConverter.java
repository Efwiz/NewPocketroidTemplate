package org.catrobat.catroid.raptor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.FrameBufferCubemap;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/PanoramicConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PanoramicConverter implements Disposable {
    private final PerspectiveCamera camera;
    private final ShaderProgram shader;
    private final Mesh skyboxMesh;

    public PanoramicConverter() {
        Gdx.f82app.log("PanoramicConverter", "Compiling shader...");
        ShaderProgram shaderProgram = new ShaderProgram("attribute vec3 a_position;\nvarying vec3 v_position;\nuniform mat4 u_projViewTrans;\nvoid main() {\n  v_position = a_position;\n  gl_Position = u_projViewTrans * vec4(a_position, 1.0);\n}", "#ifdef GL_ES\nprecision highp float;\n#endif\nvarying vec3 v_position;\nuniform sampler2D u_equirectangularMap;\nconst vec2 invPI = vec2(0.15915494309189533576, 0.31830988618379067153);\nvoid main() {\n  vec3 dir = normalize(v_position);\n  vec2 uv = vec2(atan(dir.x, dir.z), asin(dir.y));\n  uv *= invPI;\n  uv.x = 0.5 - uv.x;\n  uv.y = 0.5 -  uv.y;\n  gl_FragColor = texture2D(u_equirectangularMap, uv);\n}");
        this.shader = shaderProgram;
        if (!shaderProgram.isCompiled()) {
            Gdx.f82app.error("PanoramicConverter", "FATAL: Shader compilation failed: " + shaderProgram.getLog());
            throw new IllegalArgumentException("Error compiling shader: " + shaderProgram.getLog());
        }
        Gdx.f82app.log("PanoramicConverter", "Shader compiled successfully.");
        this.skyboxMesh = createSkyboxMesh();
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(90.0f, 1.0f, 1.0f);
        this.camera = perspectiveCamera;
        perspectiveCamera.near = 0.1f;
        perspectiveCamera.far = 10.0f;
    }

    public Cubemap convert(Texture equirectangularTexture, int cubemapSize) {
        Gdx.f82app.log("PanoramicConverter", "Starting conversion to " + cubemapSize + "x" + cubemapSize + " Cubemap.");
        FrameBufferCubemap frameBufferCubemap = new FrameBufferCubemap(Pixmap.Format.RGBA8888, cubemapSize, cubemapSize, false);
        equirectangularTexture.bind(0);
        this.shader.bind();
        this.shader.setUniformi("u_equirectangularMap", 0);
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDisable(GL20.GL_CULL_FACE);
        frameBufferCubemap.begin();
        while (frameBufferCubemap.nextSide()) {
            this.camera.direction.set(frameBufferCubemap.getSide().direction);
            this.camera.up.set(frameBufferCubemap.getSide().up);
            this.camera.update();
            Gdx.gl.glClear(16384);
            this.shader.setUniformMatrix("u_projViewTrans", this.camera.combined);
            this.skyboxMesh.render(this.shader, 4);
        }
        frameBufferCubemap.end();
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_CULL_FACE);
        Cubemap colorBufferTexture = frameBufferCubemap.getColorBufferTexture();
        Gdx.f82app.log("PanoramicConverter", "Conversion finished.");
        return colorBufferTexture;
    }

    private Mesh createSkyboxMesh() {
        Mesh mesh = new Mesh(true, 24, 36, new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE));
        mesh.setVertices(new float[]{-1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f});
        mesh.setIndices(new short[]{0, 1, 2, 0, 2, 3, 4, 5, 6, 4, 6, 7, 8, 9, 10, 8, 10, 11, 12, 13, 14, 12, 14, 15, 16, 17, 18, 16, 18, 19, 20, 21, 22, 20, 22, 23});
        return mesh;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.shader.dispose();
        this.skyboxMesh.dispose();
    }
}
