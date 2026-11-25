package net.mgsx.gltf.scene3d.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.scene3d.animation.NodeAnimationHack;

/* JADX INFO: loaded from: classes12.dex */
public class ModelInstanceHack extends ModelInstance {
    public ModelInstanceHack(Model model) {
        super(model);
    }

    public ModelInstanceHack(Model model, String... strArr) {
        super(model, strArr);
    }

    @Override // com.badlogic.gdx.graphics.g3d.ModelInstance
    public void copyAnimation(Animation animation, boolean z) {
        Animation animation2 = new Animation();
        animation2.f102id = animation.f102id;
        animation2.duration = animation.duration;
        Array.ArrayIterator<NodeAnimation> it = animation.nodeAnimations.iterator();
        while (it.hasNext()) {
            NodeAnimation next = it.next();
            Node node = getNode(next.node.f104id);
            if (node != null) {
                NodeAnimationHack nodeAnimationHack = new NodeAnimationHack();
                nodeAnimationHack.node = node;
                NodeAnimationHack nodeAnimationHack2 = (NodeAnimationHack) next;
                nodeAnimationHack.translationMode = nodeAnimationHack2.translationMode;
                nodeAnimationHack.rotationMode = nodeAnimationHack2.rotationMode;
                nodeAnimationHack.scalingMode = nodeAnimationHack2.scalingMode;
                nodeAnimationHack.weightsMode = nodeAnimationHack2.weightsMode;
                if (z) {
                    nodeAnimationHack.translation = next.translation;
                    nodeAnimationHack.rotation = next.rotation;
                    nodeAnimationHack.scaling = next.scaling;
                    nodeAnimationHack.weights = nodeAnimationHack2.weights;
                } else {
                    if (next.translation != null) {
                        nodeAnimationHack.translation = new Array<>();
                        Array.ArrayIterator<NodeKeyframe<Vector3>> it2 = next.translation.iterator();
                        while (it2.hasNext()) {
                            NodeKeyframe<Vector3> next2 = it2.next();
                            nodeAnimationHack.translation.add(new NodeKeyframe<>(next2.keytime, next2.value));
                        }
                    }
                    if (next.rotation != null) {
                        nodeAnimationHack.rotation = new Array<>();
                        Array.ArrayIterator<NodeKeyframe<Quaternion>> it3 = next.rotation.iterator();
                        while (it3.hasNext()) {
                            NodeKeyframe<Quaternion> next3 = it3.next();
                            nodeAnimationHack.rotation.add(new NodeKeyframe<>(next3.keytime, next3.value));
                        }
                    }
                    if (next.scaling != null) {
                        nodeAnimationHack.scaling = new Array<>();
                        Array.ArrayIterator<NodeKeyframe<Vector3>> it4 = next.scaling.iterator();
                        while (it4.hasNext()) {
                            NodeKeyframe<Vector3> next4 = it4.next();
                            nodeAnimationHack.scaling.add(new NodeKeyframe<>(next4.keytime, next4.value));
                        }
                    }
                    if (nodeAnimationHack2.weights != null) {
                        nodeAnimationHack2.weights = new Array<>();
                        Array.ArrayIterator<NodeKeyframe<WeightVector>> it5 = nodeAnimationHack2.weights.iterator();
                        while (it5.hasNext()) {
                            NodeKeyframe<WeightVector> next5 = it5.next();
                            nodeAnimationHack2.weights.add(new NodeKeyframe<>(next5.keytime, next5.value));
                        }
                    }
                }
                if (nodeAnimationHack.translation != null || nodeAnimationHack.rotation != null || nodeAnimationHack.scaling != null || nodeAnimationHack2.weights != null) {
                    animation2.nodeAnimations.add(nodeAnimationHack);
                }
            }
        }
        if (animation2.nodeAnimations.size > 0) {
            this.animations.add(animation2);
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.ModelInstance
    public Renderable getRenderable(Renderable renderable, Node node, NodePart nodePart) {
        super.getRenderable(renderable, node, nodePart);
        if (nodePart instanceof NodePartPlus) {
            renderable.userData = ((NodePartPlus) nodePart).morphTargets;
        }
        return renderable;
    }
}
