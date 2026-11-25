package org.catrobat.catroid.raptor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.FrameBufferCubemap;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/IBLBuilderCompat.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class IBLBuilderCompat implements Disposable {
    private final PerspectiveCamera camera;
    private final ShaderProgram irradianceShader;
    private final ShaderProgram radianceShader;
    private final Mesh skyboxMesh;

    public IBLBuilderCompat() {
        ShaderProgram shaderProgram = new ShaderProgram("attribute vec3 a_position;\nvarying vec3 v_position;\nuniform mat4 u_projViewTrans;\nvoid main() {\n  v_position = a_position;\n  gl_Position = u_projViewTrans * vec4(a_position, 1.0);\n}", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec3 v_position;\nuniform samplerCube u_environmentMap;\nconst float PI = 3.14159265359;\nvoid main() {\n  vec3 N = normalize(v_position);\n  vec3 irradiance = vec3(0.0);\n  vec3 up    = vec3(0.0, 1.0, 0.0);\n  vec3 right = cross(up, N);\n  up = cross(N, right);\n  float sampleDelta = 0.025;\n  float nrSamples = 0.0;\n  for(float phi = 0.0; phi < 2.0 * PI; phi += sampleDelta) {\n    for(float theta = 0.0; theta < 0.5 * PI; theta += sampleDelta) {\n      vec3 tangentSample = vec3(sin(theta) * cos(phi),  sin(theta) * sin(phi), cos(theta));\n      vec3 sampleVec = tangentSample.x * right + tangentSample.y * up + tangentSample.z * N;\n      irradiance += textureCube(u_environmentMap, sampleVec).rgb * cos(theta) * sin(theta);\n      nrSamples++;\n    }\n  }\n  irradiance = PI * irradiance * (1.0 / nrSamples);\n  gl_FragColor = vec4(irradiance, 1.0);\n}");
        this.irradianceShader = shaderProgram;
        if (!shaderProgram.isCompiled()) {
            throw new GdxRuntimeException("Irradiance shader compile error: " + shaderProgram.getLog());
        }
        ShaderProgram shaderProgram2 = new ShaderProgram("attribute vec3 a_position;\nvarying vec3 v_position;\nuniform mat4 u_projViewTrans;\nvoid main() {\n  v_position = a_position;\n  gl_Position = u_projViewTrans * vec4(a_position, 1.0);\n}", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec3 v_position;\nuniform samplerCube u_environmentMap;\nuniform float u_roughness;\n// ... (Complex GGX/PBR functions would go here, but for simplicity we use a simpler blur)\nvoid main() {\n    // A simple LOD-based blur is what gdx-gltf does internally. We just sample directly.\n    gl_FragColor = textureCube(u_environmentMap, normalize(v_position), u_roughness * 10.0);\n}");
        this.radianceShader = shaderProgram2;
        if (!shaderProgram2.isCompiled()) {
            throw new GdxRuntimeException("Radiance shader compile error: " + shaderProgram2.getLog());
        }
        this.skyboxMesh = createSkyboxMesh();
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(90.0f, 1.0f, 1.0f);
        this.camera = perspectiveCamera;
        perspectiveCamera.near = 0.1f;
        perspectiveCamera.far = 10.0f;
    }

    public Cubemap buildIrradianceMap(Cubemap source, int size) {
        return renderToCubemap(source, size, this.irradianceShader, -1.0f);
    }

    public Cubemap buildRadianceMap(Cubemap source, int size, int maxMipLevels) {
        return renderToCubemap(source, size, this.radianceShader, 1.0f);
    }

    private Cubemap renderToCubemap(Cubemap source, int size, ShaderProgram shader, float roughness) {
        FrameBufferCubemap frameBufferCubemap = new FrameBufferCubemap(Pixmap.Format.RGBA8888, size, size, false);
        source.bind(0);
        shader.bind();
        shader.setUniformi("u_environmentMap", 0);
        if (roughness >= 0.0f) {
            shader.setUniformf("u_roughness", roughness);
        }
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDisable(GL20.GL_CULL_FACE);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        frameBufferCubemap.begin();
        while (frameBufferCubemap.nextSide()) {
            this.camera.direction.set(frameBufferCubemap.getSide().direction);
            this.camera.up.set(frameBufferCubemap.getSide().up);
            this.camera.update();
            Gdx.gl.glClear(16384);
            shader.setUniformMatrix("u_projViewTrans", this.camera.combined);
            this.skyboxMesh.render(shader, 4);
        }
        frameBufferCubemap.end();
        return frameBufferCubemap.getColorBufferTexture();
    }

    private Mesh createSkyboxMesh() {
        Mesh mesh = new Mesh(true, 24, 36, new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE));
        mesh.setVertices(new float[]{-1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f});
        mesh.setIndices(new short[]{0, 1, 2, 0, 2, 3, 4, 5, 6, 4, 6, 7, 8, 9, 10, 8, 10, 11, 12, 13, 14, 12, 14, 15, 16, 17, 18, 16, 18, 19, 20, 21, 22, 20, 22, 23});
        return mesh;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.irradianceShader.dispose();
        this.radianceShader.dispose();
        this.skyboxMesh.dispose();
    }
}
