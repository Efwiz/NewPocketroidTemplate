package org.catrobat.catroid.raptor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.gaurav.avnc.vnc.XKeySym;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/DepthShader.class
  classes14.dex
 */
/* JADX INFO: compiled from: ThreeDManager.java */
/* JADX INFO: loaded from: classes14.dex */
class DepthShader implements Shader {
    private static final float[] boneBuffer = new float[XKeySym.XK_Cyrillic_YU];
    Camera camera;
    ShaderProgram program;
    private int u_bones;
    private int u_objReflectivity;

    @Override // com.badlogic.gdx.graphics.g3d.Shader
    public boolean canRender(Renderable instance) {
        return true;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Shader
    public int compareTo(Shader other) {
        return 0;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Shader
    public void init() {
    }

    public DepthShader(ShaderProgram program) {
        this.u_bones = -1;
        this.u_objReflectivity = -1;
        this.program = program;
        this.u_bones = program.getUniformLocation("u_bones");
        this.u_objReflectivity = program.getUniformLocation("u_objReflectivity");
    }

    @Override // com.badlogic.gdx.graphics.g3d.Shader
    public void begin(Camera camera, RenderContext context) {
        this.camera = camera;
        this.program.begin();
        this.program.setUniformMatrix("u_projViewTrans", camera.combined);
        this.program.setUniformMatrix("u_viewTrans", camera.view);
        this.program.setUniformf("u_farPlane", camera.far);
        context.setDepthTest(515);
        context.setDepthMask(true);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Shader
    public void render(Renderable renderable) {
        if (renderable.material != null) {
            if (renderable.material.has(ColorAttribute.Diffuse) && ((ColorAttribute) renderable.material.get(ColorAttribute.Diffuse)).color.a <= 0.01f) {
                return;
            }
            if (renderable.material.has(PBRColorAttribute.BaseColorFactor) && ((PBRColorAttribute) renderable.material.get(PBRColorAttribute.BaseColorFactor)).color.a <= 0.01f) {
                return;
            }
            if (renderable.material.has(BlendingAttribute.Type) && ((BlendingAttribute) renderable.material.get(BlendingAttribute.Type)).opacity <= 0.01f) {
                return;
            }
        }
        this.program.setUniformMatrix("u_worldTrans", renderable.worldTransform);
        if (this.u_objReflectivity != -1) {
            this.program.setUniformf(this.u_objReflectivity, renderable.userData instanceof Float ? ((Float) renderable.userData).floatValue() : 1.0f);
        }
        if (renderable.bones != null && this.u_bones != -1) {
            int length = renderable.bones.length;
            for (int i = 0; i < length && i < 110; i++) {
                System.arraycopy(renderable.bones[i].val, 0, boneBuffer, i * 16, 16);
            }
            this.program.setUniformMatrix4fv(this.u_bones, boneBuffer, 0, length * 16);
        }
        renderable.meshPart.render(this.program);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Shader
    public void end() {
        this.program.end();
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.program.dispose();
    }
}
