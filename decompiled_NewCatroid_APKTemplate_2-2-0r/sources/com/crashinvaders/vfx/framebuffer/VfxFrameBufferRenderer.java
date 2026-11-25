package com.crashinvaders.vfx.framebuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.crashinvaders.vfx.utils.ViewportQuadMesh;

/* JADX INFO: loaded from: classes8.dex */
public class VfxFrameBufferRenderer implements Disposable {
    private final ViewportQuadMesh mesh = new ViewportQuadMesh();
    private final ShaderProgram shader = new ShaderProgram("#ifdef GL_ES\n    #define PRECISION mediump\n    precision PRECISION float;\n#else\n    #define PRECISION\n#endif\nattribute vec4 a_position;\nattribute vec2 a_texCoord0;\nvarying vec2 v_texCoords;\nvoid main() {\n    v_texCoords = a_texCoord0;\n    gl_Position = a_position;\n}", "#ifdef GL_ES\n    #define PRECISION mediump\n    precision PRECISION float;\n#else\n    #define PRECISION\n#endif\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture0;\nvoid main() {\n    gl_FragColor = texture2D(u_texture0, v_texCoords);\n}");

    public VfxFrameBufferRenderer() {
        rebind();
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.shader.dispose();
        this.mesh.dispose();
    }

    public void rebind() {
        this.shader.begin();
        this.shader.setUniformi("u_texture0", 0);
        this.shader.end();
    }

    public void renderToScreen(VfxFrameBuffer vfxFrameBuffer) {
        renderToScreen(vfxFrameBuffer.getTexture());
    }

    public void renderToScreen(Texture texture) {
        renderToScreen(texture, 0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
    }

    public void renderToScreen(VfxFrameBuffer vfxFrameBuffer, int i, int i2, int i3, int i4) {
        renderToScreen(vfxFrameBuffer.getTexture(), i, i2, i3, i4);
    }

    public void renderToScreen(Texture texture, int i, int i2, int i3, int i4) {
        texture.bind(0);
        Gdx.graphics.getGL20().glViewport(i, i2, i3, i4);
        this.shader.begin();
        this.mesh.render(this.shader);
        this.shader.end();
    }

    public void renderToFbo(VfxFrameBuffer vfxFrameBuffer, VfxFrameBuffer vfxFrameBuffer2) {
        renderToFbo(vfxFrameBuffer.getTexture(), vfxFrameBuffer2);
    }

    public void renderToFbo(Texture texture, VfxFrameBuffer vfxFrameBuffer) {
        texture.bind(0);
        vfxFrameBuffer.begin();
        this.shader.begin();
        this.mesh.render(this.shader);
        this.shader.end();
        vfxFrameBuffer.end();
    }

    public ViewportQuadMesh getMesh() {
        return this.mesh;
    }
}
