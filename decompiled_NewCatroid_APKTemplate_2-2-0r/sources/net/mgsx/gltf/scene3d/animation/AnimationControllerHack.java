package net.mgsx.gltf.scene3d.animation;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import net.mgsx.gltf.loaders.shared.animation.Interpolation;
import net.mgsx.gltf.scene3d.model.CubicQuaternion;
import net.mgsx.gltf.scene3d.model.CubicVector3;
import net.mgsx.gltf.scene3d.model.CubicWeightVector;
import net.mgsx.gltf.scene3d.model.NodePartPlus;
import net.mgsx.gltf.scene3d.model.NodePlus;
import net.mgsx.gltf.scene3d.model.WeightVector;

/* JADX INFO: loaded from: classes12.dex */
public class AnimationControllerHack extends AnimationController {
    private boolean applying;
    public boolean calculateTransforms;
    private final Pool<Transform> transformPool;
    private static final ObjectMap<Node, Transform> transforms = new ObjectMap<>();
    private static final Transform tmpT = new Transform();
    private static final Quaternion q1 = new Quaternion();
    private static final Quaternion q2 = new Quaternion();
    private static final Quaternion q3 = new Quaternion();
    private static final Quaternion q4 = new Quaternion();

    public static class Transform implements Pool.Poolable {
        public final Vector3 translation = new Vector3();
        public final Quaternion rotation = new Quaternion();
        public final Vector3 scale = new Vector3(1.0f, 1.0f, 1.0f);
        public final WeightVector weights = new WeightVector();

        public Transform idt() {
            this.translation.set(0.0f, 0.0f, 0.0f);
            this.rotation.idt();
            this.scale.set(1.0f, 1.0f, 1.0f);
            this.weights.set();
            return this;
        }

        public Transform set(Vector3 vector3, Quaternion quaternion, Vector3 vector4, WeightVector weightVector) {
            this.translation.set(vector3);
            this.rotation.set(quaternion);
            this.scale.set(vector4);
            WeightVector weightVector2 = this.weights;
            if (weightVector != null) {
                weightVector2.set(weightVector);
            } else {
                weightVector2.set();
            }
            return this;
        }

        public Transform set(Transform transform) {
            return set(transform.translation, transform.rotation, transform.scale, transform.weights);
        }

        public Transform lerp(Transform transform, float f) {
            return lerp(transform.translation, transform.rotation, transform.scale, transform.weights, f);
        }

        public Transform lerp(Vector3 vector3, Quaternion quaternion, Vector3 vector4, WeightVector weightVector, float f) {
            this.translation.lerp(vector3, f);
            this.rotation.slerp(quaternion, f);
            this.scale.lerp(vector4, f);
            if (weightVector != null) {
                this.weights.lerp(weightVector, f);
            }
            return this;
        }

        public Matrix4 toMatrix4(Matrix4 matrix4) {
            return matrix4.set(this.translation, this.rotation, this.scale);
        }

        @Override // com.badlogic.gdx.utils.Pool.Poolable
        public void reset() {
            idt();
        }

        public String toString() {
            return this.translation.toString() + " - " + this.rotation.toString() + " - " + this.scale.toString() + " - " + this.weights.toString();
        }
    }

    public AnimationControllerHack(ModelInstance modelInstance) {
        super(modelInstance);
        this.transformPool = new Pool<Transform>() { // from class: net.mgsx.gltf.scene3d.animation.AnimationControllerHack.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.badlogic.gdx.utils.Pool
            public Transform newObject() {
                return new Transform();
            }
        };
        this.applying = false;
        this.calculateTransforms = true;
    }

    @Override // com.badlogic.gdx.graphics.g3d.utils.BaseAnimationController
    protected void begin() {
        if (this.applying) {
            throw new GdxRuntimeException("You must call end() after each call to being()");
        }
        this.applying = true;
    }

    @Override // com.badlogic.gdx.graphics.g3d.utils.BaseAnimationController
    protected void apply(Animation animation, float f, float f2) {
        if (!this.applying) {
            throw new GdxRuntimeException("You must call begin() before adding an animation");
        }
        applyAnimationPlus(transforms, this.transformPool, f2, animation, f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.badlogic.gdx.graphics.g3d.utils.BaseAnimationController
    protected void end() {
        if (!this.applying) {
            throw new GdxRuntimeException("You must call begin() first");
        }
        ObjectMap.Entries<Node, Transform> it = transforms.entries().iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            ((Transform) next.value).toMatrix4(((Node) next.key).localTransform);
            this.transformPool.free(next.value);
        }
        transforms.clear();
        if (this.calculateTransforms) {
            this.target.calculateTransforms();
        }
        this.applying = false;
    }

    @Override // com.badlogic.gdx.graphics.g3d.utils.BaseAnimationController
    protected void applyAnimation(Animation animation, float f) {
        if (this.applying) {
            throw new GdxRuntimeException("Call end() first");
        }
        applyAnimationPlus(null, null, 1.0f, animation, f);
        if (this.calculateTransforms) {
            this.target.calculateTransforms();
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.utils.BaseAnimationController
    protected void applyAnimations(Animation animation, float f, Animation animation2, float f2, float f3) {
        if (animation2 == null || f3 == 0.0f) {
            applyAnimation(animation, f);
            return;
        }
        if (animation == null || f3 == 1.0f) {
            applyAnimation(animation2, f2);
        } else {
            if (this.applying) {
                throw new GdxRuntimeException("Call end() first");
            }
            begin();
            apply(animation, f, 1.0f);
            apply(animation2, f2, f3);
            end();
        }
    }

    private static final <T> int getFirstKeyframeIndexAtTime(Array<NodeKeyframe<T>> array, float f) {
        int i = array.size - 1;
        for (int i2 = 0; i2 < i; i2++) {
            if (f >= array.get(i2).keytime && f <= array.get(i2 + 1).keytime) {
                return i2;
            }
        }
        return i;
    }

    private static final Vector3 getTranslationAtTime(NodeAnimation nodeAnimation, float f, Vector3 vector3) {
        if (nodeAnimation.translation == null) {
            return vector3.set(nodeAnimation.node.translation);
        }
        if (nodeAnimation.translation.size == 1) {
            return vector3.set(nodeAnimation.translation.get(0).value);
        }
        int firstKeyframeIndexAtTime = getFirstKeyframeIndexAtTime(nodeAnimation.translation, f);
        Interpolation interpolation = nodeAnimation instanceof NodeAnimationHack ? ((NodeAnimationHack) nodeAnimation).translationMode : null;
        if (interpolation == Interpolation.STEP) {
            vector3.set(nodeAnimation.translation.get(firstKeyframeIndexAtTime).value);
        } else if (interpolation == Interpolation.LINEAR) {
            NodeKeyframe<Vector3> nodeKeyframe = nodeAnimation.translation.get(firstKeyframeIndexAtTime);
            vector3.set(nodeKeyframe.value);
            int i = firstKeyframeIndexAtTime + 1;
            if (i < nodeAnimation.translation.size) {
                NodeKeyframe<Vector3> nodeKeyframe2 = nodeAnimation.translation.get(i);
                vector3.lerp(nodeKeyframe2.value, (f - nodeKeyframe.keytime) / (nodeKeyframe2.keytime - nodeKeyframe.keytime));
            } else {
                vector3.set(nodeKeyframe.value);
            }
        } else if (interpolation == Interpolation.CUBICSPLINE) {
            NodeKeyframe<Vector3> nodeKeyframe3 = nodeAnimation.translation.get(firstKeyframeIndexAtTime);
            int i2 = firstKeyframeIndexAtTime + 1;
            if (i2 < nodeAnimation.translation.size) {
                NodeKeyframe<Vector3> nodeKeyframe4 = nodeAnimation.translation.get(i2);
                float f2 = (f - nodeKeyframe3.keytime) / (nodeKeyframe4.keytime - nodeKeyframe3.keytime);
                CubicVector3 cubicVector3 = (CubicVector3) nodeKeyframe3.value;
                CubicVector3 cubicVector4 = (CubicVector3) nodeKeyframe4.value;
                cubic(vector3, f2, cubicVector3, cubicVector3.tangentOut, cubicVector4, cubicVector4.tangentIn);
            } else {
                vector3.set(nodeKeyframe3.value);
            }
        }
        return vector3;
    }

    private static void cubic(Vector3 vector3, float f, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7) {
        float f2 = f * f;
        float f3 = f2 * f;
        float f4 = 3.0f * f2;
        vector3.set(vector4).scl(((f3 * 2.0f) - f4) + 1.0f).mulAdd(vector5, (f3 - (2.0f * f2)) + f).mulAdd(vector6, ((-2.0f) * f3) + f4).mulAdd(vector7, f3 - f2);
    }

    private static void cubic(Quaternion quaternion, float f, float f2, Quaternion quaternion2, Quaternion quaternion3, Quaternion quaternion4, Quaternion quaternion5) {
        float f3 = -f2;
        float f4 = f * f;
        float f5 = f4 * f;
        Quaternion quaternion6 = q1;
        float f6 = 3.0f * f4;
        quaternion6.set(quaternion2).mul(((f5 * 2.0f) - f6) + 1.0f);
        Quaternion quaternion7 = q2;
        quaternion7.set(quaternion3).mul(f3).mul((f5 - (2.0f * f4)) + f);
        Quaternion quaternion8 = q3;
        quaternion8.set(quaternion4).mul(((-2.0f) * f5) + f6);
        Quaternion quaternion9 = q4;
        quaternion9.set(quaternion5).mul(f3).mul(f5 - f4);
        quaternion.set(quaternion6).add(quaternion7).add(quaternion8).add(quaternion9).nor();
    }

    private static void cubic(WeightVector weightVector, float f, WeightVector weightVector2, WeightVector weightVector3, WeightVector weightVector4, WeightVector weightVector5) {
        float f2 = f * f;
        float f3 = f2 * f;
        float f4 = 3.0f * f2;
        weightVector.set(weightVector2).scl(((f3 * 2.0f) - f4) + 1.0f).mulAdd(weightVector3, (f3 - (2.0f * f2)) + f).mulAdd(weightVector4, ((-2.0f) * f3) + f4).mulAdd(weightVector5, f3 - f2);
    }

    private static final Quaternion getRotationAtTime(NodeAnimation nodeAnimation, float f, Quaternion quaternion) {
        if (nodeAnimation.rotation == null) {
            return quaternion.set(nodeAnimation.node.rotation);
        }
        if (nodeAnimation.rotation.size == 1) {
            return quaternion.set(nodeAnimation.rotation.get(0).value);
        }
        int firstKeyframeIndexAtTime = getFirstKeyframeIndexAtTime(nodeAnimation.rotation, f);
        Interpolation interpolation = nodeAnimation instanceof NodeAnimationHack ? ((NodeAnimationHack) nodeAnimation).rotationMode : null;
        if (interpolation == Interpolation.STEP) {
            quaternion.set(nodeAnimation.rotation.get(firstKeyframeIndexAtTime).value);
        } else if (interpolation == Interpolation.LINEAR) {
            NodeKeyframe<Quaternion> nodeKeyframe = nodeAnimation.rotation.get(firstKeyframeIndexAtTime);
            quaternion.set(nodeKeyframe.value);
            int i = firstKeyframeIndexAtTime + 1;
            if (i < nodeAnimation.rotation.size) {
                NodeKeyframe<Quaternion> nodeKeyframe2 = nodeAnimation.rotation.get(i);
                quaternion.slerp(nodeKeyframe2.value, (f - nodeKeyframe.keytime) / (nodeKeyframe2.keytime - nodeKeyframe.keytime));
            } else {
                quaternion.set(nodeKeyframe.value);
            }
        } else if (interpolation == Interpolation.CUBICSPLINE) {
            NodeKeyframe<Quaternion> nodeKeyframe3 = nodeAnimation.rotation.get(firstKeyframeIndexAtTime);
            int i2 = firstKeyframeIndexAtTime + 1;
            if (i2 < nodeAnimation.rotation.size) {
                NodeKeyframe<Quaternion> nodeKeyframe4 = nodeAnimation.rotation.get(i2);
                float f2 = (f - nodeKeyframe3.keytime) / (nodeKeyframe4.keytime - nodeKeyframe3.keytime);
                CubicQuaternion cubicQuaternion = (CubicQuaternion) nodeKeyframe3.value;
                CubicQuaternion cubicQuaternion2 = (CubicQuaternion) nodeKeyframe4.value;
                cubic(quaternion, f2, nodeKeyframe4.keytime - nodeKeyframe3.keytime, cubicQuaternion, cubicQuaternion.tangentOut, cubicQuaternion2, cubicQuaternion2.tangentIn);
            } else {
                quaternion.set(nodeKeyframe3.value);
            }
        }
        return quaternion;
    }

    private static final Vector3 getScalingAtTime(NodeAnimation nodeAnimation, float f, Vector3 vector3) {
        if (nodeAnimation.scaling == null) {
            return vector3.set(nodeAnimation.node.scale);
        }
        if (nodeAnimation.scaling.size == 1) {
            return vector3.set(nodeAnimation.scaling.get(0).value);
        }
        int firstKeyframeIndexAtTime = getFirstKeyframeIndexAtTime(nodeAnimation.scaling, f);
        Interpolation interpolation = nodeAnimation instanceof NodeAnimationHack ? ((NodeAnimationHack) nodeAnimation).scalingMode : null;
        if (interpolation == Interpolation.STEP) {
            vector3.set(nodeAnimation.scaling.get(firstKeyframeIndexAtTime).value);
        } else if (interpolation == Interpolation.LINEAR) {
            NodeKeyframe<Vector3> nodeKeyframe = nodeAnimation.scaling.get(firstKeyframeIndexAtTime);
            vector3.set(nodeKeyframe.value);
            int i = firstKeyframeIndexAtTime + 1;
            if (i < nodeAnimation.scaling.size) {
                NodeKeyframe<Vector3> nodeKeyframe2 = nodeAnimation.scaling.get(i);
                vector3.lerp(nodeKeyframe2.value, (f - nodeKeyframe.keytime) / (nodeKeyframe2.keytime - nodeKeyframe.keytime));
            } else {
                vector3.set(nodeKeyframe.value);
            }
        } else if (interpolation == Interpolation.CUBICSPLINE) {
            NodeKeyframe<Vector3> nodeKeyframe3 = nodeAnimation.scaling.get(firstKeyframeIndexAtTime);
            int i2 = firstKeyframeIndexAtTime + 1;
            if (i2 < nodeAnimation.scaling.size) {
                NodeKeyframe<Vector3> nodeKeyframe4 = nodeAnimation.scaling.get(i2);
                float f2 = (f - nodeKeyframe3.keytime) / (nodeKeyframe4.keytime - nodeKeyframe3.keytime);
                CubicVector3 cubicVector3 = (CubicVector3) nodeKeyframe3.value;
                CubicVector3 cubicVector4 = (CubicVector3) nodeKeyframe4.value;
                cubic(vector3, f2, cubicVector3, cubicVector3.tangentOut, cubicVector4, cubicVector4.tangentIn);
            } else {
                vector3.set(nodeKeyframe3.value);
            }
        }
        return vector3;
    }

    private static final WeightVector getMorphTargetAtTime(NodeAnimationHack nodeAnimationHack, float f, WeightVector weightVector) {
        if (nodeAnimationHack.weights == null) {
            return weightVector.set();
        }
        if (nodeAnimationHack.weights.size == 1) {
            return weightVector.set(nodeAnimationHack.weights.get(0).value);
        }
        int firstKeyframeIndexAtTime = getFirstKeyframeIndexAtTime(nodeAnimationHack.weights, f);
        Interpolation interpolation = nodeAnimationHack instanceof NodeAnimationHack ? nodeAnimationHack.weightsMode : null;
        if (interpolation == Interpolation.STEP) {
            weightVector.set(nodeAnimationHack.weights.get(firstKeyframeIndexAtTime).value);
        } else if (interpolation == Interpolation.LINEAR) {
            NodeKeyframe<WeightVector> nodeKeyframe = nodeAnimationHack.weights.get(firstKeyframeIndexAtTime);
            weightVector.set(nodeKeyframe.value);
            int i = firstKeyframeIndexAtTime + 1;
            if (i < nodeAnimationHack.weights.size) {
                NodeKeyframe<WeightVector> nodeKeyframe2 = nodeAnimationHack.weights.get(i);
                weightVector.lerp(nodeKeyframe2.value, (f - nodeKeyframe.keytime) / (nodeKeyframe2.keytime - nodeKeyframe.keytime));
            } else {
                weightVector.set(nodeKeyframe.value);
            }
        } else if (interpolation == Interpolation.CUBICSPLINE) {
            NodeKeyframe<WeightVector> nodeKeyframe3 = nodeAnimationHack.weights.get(firstKeyframeIndexAtTime);
            int i2 = firstKeyframeIndexAtTime + 1;
            if (i2 < nodeAnimationHack.weights.size) {
                NodeKeyframe<WeightVector> nodeKeyframe4 = nodeAnimationHack.weights.get(i2);
                float f2 = (f - nodeKeyframe3.keytime) / (nodeKeyframe4.keytime - nodeKeyframe3.keytime);
                CubicWeightVector cubicWeightVector = (CubicWeightVector) nodeKeyframe3.value;
                CubicWeightVector cubicWeightVector2 = (CubicWeightVector) nodeKeyframe4.value;
                cubic(weightVector, f2, cubicWeightVector, cubicWeightVector.tangentOut, cubicWeightVector2, cubicWeightVector2.tangentIn);
            } else {
                weightVector.set(nodeKeyframe3.value);
            }
        }
        return weightVector;
    }

    private static final Transform getNodeAnimationTransform(NodeAnimation nodeAnimation, float f) {
        Transform transform = tmpT;
        getTranslationAtTime(nodeAnimation, f, transform.translation);
        getRotationAtTime(nodeAnimation, f, transform.rotation);
        getScalingAtTime(nodeAnimation, f, transform.scale);
        if (nodeAnimation instanceof NodeAnimationHack) {
            getMorphTargetAtTime((NodeAnimationHack) nodeAnimation, f, transform.weights);
        }
        return transform;
    }

    private static final void applyNodeAnimationDirectly(NodeAnimation nodeAnimation, float f) {
        Node node = nodeAnimation.node;
        node.isAnimated = true;
        Transform nodeAnimationTransform = getNodeAnimationTransform(nodeAnimation, f);
        nodeAnimationTransform.toMatrix4(node.localTransform);
        if (node instanceof NodePlus) {
            NodePlus nodePlus = (NodePlus) node;
            if (nodePlus.weights != null) {
                nodePlus.weights.set(nodeAnimationTransform.weights);
                Array.ArrayIterator<NodePart> it = node.parts.iterator();
                while (it.hasNext()) {
                    ((NodePartPlus) it.next()).morphTargets.set(nodeAnimationTransform.weights);
                }
            }
        }
    }

    private static final void applyNodeAnimationBlending(NodeAnimation nodeAnimation, ObjectMap<Node, Transform> objectMap, Pool<Transform> pool, float f, float f2) {
        Node node = nodeAnimation.node;
        node.isAnimated = true;
        Transform nodeAnimationTransform = getNodeAnimationTransform(nodeAnimation, f2);
        Transform transform = objectMap.get(node, null);
        if (transform != null) {
            if (f > 0.999999f) {
                transform.set(nodeAnimationTransform);
                return;
            } else {
                transform.lerp(nodeAnimationTransform, f);
                return;
            }
        }
        if (f > 0.999999f) {
            objectMap.put(node, pool.obtain().set(nodeAnimationTransform));
        } else {
            objectMap.put(node, pool.obtain().set(node.translation, node.rotation, node.scale, ((NodePlus) node).weights).lerp(nodeAnimationTransform, f));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected static void applyAnimationPlus(ObjectMap<Node, Transform> objectMap, Pool<Transform> pool, float f, Animation animation, float f2) {
        if (objectMap == null) {
            Array.ArrayIterator<NodeAnimation> it = animation.nodeAnimations.iterator();
            while (it.hasNext()) {
                applyNodeAnimationDirectly(it.next(), f2);
            }
            return;
        }
        ObjectMap.Keys<Node> it2 = objectMap.keys().iterator();
        while (it2.hasNext()) {
            it2.next().isAnimated = false;
        }
        Array.ArrayIterator<NodeAnimation> it3 = animation.nodeAnimations.iterator();
        while (it3.hasNext()) {
            applyNodeAnimationBlending(it3.next(), objectMap, pool, f, f2);
        }
        ObjectMap.Entries<Node, Transform> it4 = objectMap.entries().iterator();
        while (it4.hasNext()) {
            ObjectMap.Entry next = it4.next();
            if (!((Node) next.key).isAnimated) {
                ((Node) next.key).isAnimated = true;
                ((Transform) next.value).lerp(((Node) next.key).translation, ((Node) next.key).rotation, ((Node) next.key).scale, ((NodePlus) next.key).weights, f);
            }
        }
    }

    public void setAnimationDesc(AnimationController.AnimationDesc animationDesc) {
        setAnimation(animationDesc.animation, animationDesc.offset, animationDesc.duration, animationDesc.loopCount, animationDesc.speed, animationDesc.listener);
    }

    public void setAnimation(Animation animation) {
        setAnimation(animation, 1);
    }

    public void setAnimation(Animation animation, int i) {
        setAnimation(animation, 0.0f, animation.duration, i, 1.0f, (AnimationController.AnimationListener) null);
    }
}
