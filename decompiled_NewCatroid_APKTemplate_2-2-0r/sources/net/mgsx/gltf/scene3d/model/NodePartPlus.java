package net.mgsx.gltf.scene3d.model;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.model.NodePart;

/* JADX INFO: loaded from: classes12.dex */
public class NodePartPlus extends NodePart {
    public WeightVector morphTargets;

    @Override // com.badlogic.gdx.graphics.g3d.model.NodePart
    public Renderable setRenderable(Renderable renderable) {
        renderable.material = this.material;
        renderable.meshPart.set(this.meshPart);
        renderable.bones = this.bones;
        renderable.userData = this.morphTargets;
        return renderable;
    }

    @Override // com.badlogic.gdx.graphics.g3d.model.NodePart
    public NodePart copy() {
        return new NodePartPlus().set(this);
    }

    @Override // com.badlogic.gdx.graphics.g3d.model.NodePart
    protected NodePart set(NodePart nodePart) {
        super.set(nodePart);
        if (nodePart instanceof NodePartPlus) {
            WeightVector weightVector = ((NodePartPlus) nodePart).morphTargets;
            this.morphTargets = weightVector != null ? weightVector.cpy() : null;
        }
        return this;
    }
}
