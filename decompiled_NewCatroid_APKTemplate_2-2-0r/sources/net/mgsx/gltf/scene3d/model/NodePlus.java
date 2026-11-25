package net.mgsx.gltf.scene3d.model;

import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.utils.Array;

/* JADX INFO: loaded from: classes12.dex */
public class NodePlus extends Node {
    public Array<String> morphTargetNames;
    public WeightVector weights;

    @Override // com.badlogic.gdx.graphics.g3d.model.Node
    public Node copy() {
        return new NodePlus().set(this);
    }

    @Override // com.badlogic.gdx.graphics.g3d.model.Node
    protected Node set(Node node) {
        NodePlus nodePlus;
        WeightVector weightVector;
        if ((node instanceof NodePlus) && (weightVector = (nodePlus = (NodePlus) node).weights) != null) {
            this.weights = weightVector.cpy();
            this.morphTargetNames = nodePlus.morphTargetNames;
        }
        return super.set(node);
    }
}
