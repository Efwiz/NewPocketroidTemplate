package net.mgsx.gltf.scene3d.shaders;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DepthShader;
import net.mgsx.gltf.scene3d.model.WeightVector;

/* JADX INFO: loaded from: classes12.dex */
public class PBRDepthShader extends DepthShader {
    public final long morphTargetsMask;
    private int u_morphTargets1;
    private int u_morphTargets2;

    public PBRDepthShader(Renderable renderable, DepthShader.Config config, String str) {
        super(renderable, config, str);
        this.morphTargetsMask = computeMorphTargetsMask(renderable);
    }

    protected long computeMorphTargetsMask(Renderable renderable) {
        VertexAttributes vertexAttributes = renderable.meshPart.mesh.getVertexAttributes();
        int size = vertexAttributes.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            VertexAttribute vertexAttribute = vertexAttributes.get(i2);
            if (vertexAttribute.usage == 512) {
                i |= 1 << vertexAttribute.unit;
            }
        }
        return i;
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.DepthShader, com.badlogic.gdx.graphics.g3d.shaders.DefaultShader, com.badlogic.gdx.graphics.g3d.Shader
    public boolean canRender(Renderable renderable) {
        if (this.morphTargetsMask != computeMorphTargetsMask(renderable)) {
            return false;
        }
        return super.canRender(renderable);
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.DefaultShader, com.badlogic.gdx.graphics.g3d.Shader
    public void init() {
        super.init();
        this.u_morphTargets1 = this.program.fetchUniformLocation("u_morphTargets1", false);
        this.u_morphTargets2 = this.program.fetchUniformLocation("u_morphTargets2", false);
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.DepthShader, com.badlogic.gdx.graphics.g3d.shaders.DefaultShader, com.badlogic.gdx.graphics.g3d.shaders.BaseShader
    public void render(Renderable renderable, Attributes attributes) {
        if (this.u_morphTargets1 >= 0) {
            if (renderable.userData instanceof WeightVector) {
                WeightVector weightVector = (WeightVector) renderable.userData;
                this.program.setUniformf(this.u_morphTargets1, weightVector.get(0), weightVector.get(1), weightVector.get(2), weightVector.get(3));
            } else {
                this.program.setUniformf(this.u_morphTargets1, 0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
        if (this.u_morphTargets2 >= 0) {
            if (renderable.userData instanceof WeightVector) {
                WeightVector weightVector2 = (WeightVector) renderable.userData;
                this.program.setUniformf(this.u_morphTargets2, weightVector2.get(4), weightVector2.get(5), weightVector2.get(6), weightVector2.get(7));
            } else {
                this.program.setUniformf(this.u_morphTargets2, 0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
        super.render(renderable, attributes);
    }
}
