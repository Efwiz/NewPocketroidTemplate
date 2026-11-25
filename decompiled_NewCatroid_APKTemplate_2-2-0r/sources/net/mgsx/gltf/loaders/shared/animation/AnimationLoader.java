package net.mgsx.gltf.loaders.shared.animation;

import androidx.constraintlayout.motion.widget.Key;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import net.mgsx.gltf.data.animation.GLTFAnimation;
import net.mgsx.gltf.data.animation.GLTFAnimationChannel;
import net.mgsx.gltf.data.animation.GLTFAnimationSampler;
import net.mgsx.gltf.loaders.exceptions.GLTFUnsupportedException;
import net.mgsx.gltf.loaders.shared.GLTFTypes;
import net.mgsx.gltf.loaders.shared.data.DataResolver;
import net.mgsx.gltf.loaders.shared.scene.NodeResolver;
import net.mgsx.gltf.scene3d.animation.NodeAnimationHack;
import net.mgsx.gltf.scene3d.model.CubicQuaternion;
import net.mgsx.gltf.scene3d.model.CubicVector3;
import net.mgsx.gltf.scene3d.model.CubicWeightVector;
import net.mgsx.gltf.scene3d.model.NodePlus;
import net.mgsx.gltf.scene3d.model.WeightVector;

/* JADX INFO: loaded from: classes12.dex */
public class AnimationLoader {
    public final Array<Animation> animations = new Array<>();

    public void load(Array<GLTFAnimation> array, NodeResolver nodeResolver, DataResolver dataResolver) {
        if (array != null) {
            for (int i = 0; i < array.size; i++) {
                GLTFAnimation gLTFAnimation = array.get(i);
                Animation animationLoad = load(gLTFAnimation, nodeResolver, dataResolver);
                animationLoad.f102id = gLTFAnimation.f408name == null ? "animation" + i : gLTFAnimation.f408name;
                this.animations.add(animationLoad);
            }
        }
    }

    private Animation load(GLTFAnimation gLTFAnimation, NodeResolver nodeResolver, DataResolver dataResolver) {
        int i;
        int i2;
        GLTFAnimation gLTFAnimation2 = gLTFAnimation;
        ObjectMap objectMap = new ObjectMap();
        Animation animation = new Animation();
        Array.ArrayIterator<GLTFAnimationChannel> it = gLTFAnimation2.channels.iterator();
        while (it.hasNext()) {
            GLTFAnimationChannel next = it.next();
            GLTFAnimationSampler gLTFAnimationSampler = gLTFAnimation2.samplers.get(next.sampler.intValue());
            Node node = nodeResolver.get(next.target.node.intValue());
            NodeAnimation nodeAnimationHack = (NodeAnimation) objectMap.get(node);
            if (nodeAnimationHack == null) {
                nodeAnimationHack = new NodeAnimationHack();
                nodeAnimationHack.node = node;
                objectMap.put(node, nodeAnimationHack);
                animation.nodeAnimations.add(nodeAnimationHack);
            }
            float[] bufferFloat = dataResolver.readBufferFloat(gLTFAnimationSampler.input.intValue());
            float[] bufferFloat2 = dataResolver.readBufferFloat(gLTFAnimationSampler.output.intValue());
            Interpolation interpolationMapInterpolation = GLTFTypes.mapInterpolation(gLTFAnimationSampler.interpolation);
            int i3 = 0;
            if (interpolationMapInterpolation == Interpolation.CUBICSPLINE) {
                i2 = 3;
                i = 1;
            } else {
                i = 0;
                i2 = 1;
            }
            animation.duration = Math.max(animation.duration, dataResolver.getAccessor(gLTFAnimationSampler.input.intValue()).max[0]);
            String str = next.target.path;
            if ("translation".equals(str)) {
                ((NodeAnimationHack) nodeAnimationHack).translationMode = interpolationMapInterpolation;
                nodeAnimationHack.translation = new Array<>();
                if (interpolationMapInterpolation == Interpolation.CUBICSPLINE) {
                    if (bufferFloat[0] > 0.0f) {
                        nodeAnimationHack.translation.add(new NodeKeyframe<>(0.0f, GLTFTypes.map(new CubicVector3(), bufferFloat2, 0)));
                    }
                    while (i3 < bufferFloat.length) {
                        nodeAnimationHack.translation.add(new NodeKeyframe<>(bufferFloat[i3], GLTFTypes.map(new CubicVector3(), bufferFloat2, i3 * i2 * 3)));
                        i3++;
                    }
                } else {
                    if (bufferFloat[0] > 0.0f) {
                        nodeAnimationHack.translation.add(new NodeKeyframe<>(0.0f, GLTFTypes.map(new Vector3(), bufferFloat2, i * 3)));
                    }
                    while (i3 < bufferFloat.length) {
                        nodeAnimationHack.translation.add(new NodeKeyframe<>(bufferFloat[i3], GLTFTypes.map(new Vector3(), bufferFloat2, (i + (i3 * i2)) * 3)));
                        i3++;
                    }
                }
            } else if (Key.ROTATION.equals(str)) {
                ((NodeAnimationHack) nodeAnimationHack).rotationMode = interpolationMapInterpolation;
                nodeAnimationHack.rotation = new Array<>();
                if (interpolationMapInterpolation == Interpolation.CUBICSPLINE) {
                    if (bufferFloat[0] > 0.0f) {
                        nodeAnimationHack.rotation.add(new NodeKeyframe<>(0.0f, GLTFTypes.map(new CubicQuaternion(), bufferFloat2, 0)));
                    }
                    while (i3 < bufferFloat.length) {
                        nodeAnimationHack.rotation.add(new NodeKeyframe<>(bufferFloat[i3], GLTFTypes.map(new CubicQuaternion(), bufferFloat2, i3 * i2 * 4)));
                        i3++;
                    }
                } else {
                    if (bufferFloat[0] > 0.0f) {
                        nodeAnimationHack.rotation.add(new NodeKeyframe<>(0.0f, GLTFTypes.map(new Quaternion(), bufferFloat2, i * 4)));
                    }
                    while (i3 < bufferFloat.length) {
                        nodeAnimationHack.rotation.add(new NodeKeyframe<>(bufferFloat[i3], GLTFTypes.map(new Quaternion(), bufferFloat2, (i + (i3 * i2)) * 4)));
                        i3++;
                    }
                }
            } else if ("scale".equals(str)) {
                ((NodeAnimationHack) nodeAnimationHack).scalingMode = interpolationMapInterpolation;
                nodeAnimationHack.scaling = new Array<>();
                if (interpolationMapInterpolation == Interpolation.CUBICSPLINE) {
                    if (bufferFloat[0] > 0.0f) {
                        nodeAnimationHack.scaling.add(new NodeKeyframe<>(0.0f, GLTFTypes.map(new CubicVector3(), bufferFloat2, 0)));
                    }
                    while (i3 < bufferFloat.length) {
                        nodeAnimationHack.scaling.add(new NodeKeyframe<>(bufferFloat[i3], GLTFTypes.map(new CubicVector3(), bufferFloat2, i3 * i2 * 3)));
                        i3++;
                    }
                } else {
                    if (bufferFloat[0] > 0.0f) {
                        nodeAnimationHack.scaling.add(new NodeKeyframe<>(0.0f, GLTFTypes.map(new Vector3(), bufferFloat2, i * 3)));
                    }
                    while (i3 < bufferFloat.length) {
                        nodeAnimationHack.scaling.add(new NodeKeyframe<>(bufferFloat[i3], GLTFTypes.map(new Vector3(), bufferFloat2, (i + (i3 * i2)) * 3)));
                        i3++;
                    }
                }
            } else if ("weights".equals(str)) {
                NodeAnimationHack nodeAnimationHack2 = (NodeAnimationHack) nodeAnimationHack;
                nodeAnimationHack2.weightsMode = interpolationMapInterpolation;
                int i4 = ((NodePlus) node).weights.count;
                nodeAnimationHack2.weights = new Array<>();
                if (interpolationMapInterpolation == Interpolation.CUBICSPLINE) {
                    if (bufferFloat[0] > 0.0f) {
                        nodeAnimationHack2.weights.add(new NodeKeyframe<>(0.0f, GLTFTypes.map(new CubicWeightVector(i4), bufferFloat2, 0)));
                    }
                    while (i3 < bufferFloat.length) {
                        nodeAnimationHack2.weights.add(new NodeKeyframe<>(bufferFloat[i3], GLTFTypes.map(new CubicWeightVector(i4), bufferFloat2, i3 * i2 * i4)));
                        i3++;
                    }
                } else {
                    if (bufferFloat[0] > 0.0f) {
                        nodeAnimationHack2.weights.add(new NodeKeyframe<>(0.0f, GLTFTypes.map(new WeightVector(i4), bufferFloat2, i * i4)));
                    }
                    while (i3 < bufferFloat.length) {
                        nodeAnimationHack2.weights.add(new NodeKeyframe<>(bufferFloat[i3], GLTFTypes.map(new WeightVector(i4), bufferFloat2, (i + (i3 * i2)) * i4)));
                        i3++;
                    }
                }
            } else {
                throw new GLTFUnsupportedException("unsupported " + str);
            }
            gLTFAnimation2 = gLTFAnimation;
        }
        return animation;
    }
}
