package net.mgsx.gltf.exporters;

import androidx.constraintlayout.motion.widget.Key;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.nio.FloatBuffer;
import net.mgsx.gltf.data.animation.GLTFAnimation;
import net.mgsx.gltf.data.animation.GLTFAnimationChannel;
import net.mgsx.gltf.data.animation.GLTFAnimationSampler;
import net.mgsx.gltf.data.animation.GLTFAnimationTarget;
import net.mgsx.gltf.data.data.GLTFAccessor;
import net.mgsx.gltf.loaders.exceptions.GLTFIllegalException;
import net.mgsx.gltf.loaders.shared.GLTFTypes;
import net.mgsx.gltf.loaders.shared.animation.Interpolation;
import net.mgsx.gltf.scene3d.animation.NodeAnimationHack;
import net.mgsx.gltf.scene3d.model.CubicQuaternion;
import net.mgsx.gltf.scene3d.model.CubicVector3;
import net.mgsx.gltf.scene3d.model.CubicWeightVector;
import net.mgsx.gltf.scene3d.model.WeightVector;

/* JADX INFO: loaded from: classes12.dex */
class GLTFAnimationExporter {
    private final GLTFExporter base;
    private static final ChannelExporter<Vector3> channelExporterVector3 = new ChannelExporter<Vector3>(3, GLTFTypes.TYPE_VEC3) { // from class: net.mgsx.gltf.exporters.GLTFAnimationExporter.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.mgsx.gltf.exporters.GLTFAnimationExporter.ChannelExporter
        public void getOutput(FloatBuffer floatBuffer, Vector3 vector3) {
            if (vector3 instanceof CubicVector3) {
                CubicVector3 cubicVector3 = (CubicVector3) vector3;
                floatBuffer.put(cubicVector3.tangentIn.x);
                floatBuffer.put(cubicVector3.tangentIn.y);
                floatBuffer.put(cubicVector3.tangentIn.z);
                floatBuffer.put(vector3.x);
                floatBuffer.put(vector3.y);
                floatBuffer.put(vector3.z);
                floatBuffer.put(cubicVector3.tangentOut.x);
                floatBuffer.put(cubicVector3.tangentOut.y);
                floatBuffer.put(cubicVector3.tangentOut.z);
                return;
            }
            floatBuffer.put(vector3.x);
            floatBuffer.put(vector3.y);
            floatBuffer.put(vector3.z);
        }
    };
    private static final ChannelExporter<Quaternion> channelExporterQuaternion = new ChannelExporter<Quaternion>(4, GLTFTypes.TYPE_VEC4) { // from class: net.mgsx.gltf.exporters.GLTFAnimationExporter.2
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.mgsx.gltf.exporters.GLTFAnimationExporter.ChannelExporter
        public void getOutput(FloatBuffer floatBuffer, Quaternion quaternion) {
            if (quaternion instanceof CubicQuaternion) {
                CubicQuaternion cubicQuaternion = (CubicQuaternion) quaternion;
                floatBuffer.put(cubicQuaternion.tangentIn.x);
                floatBuffer.put(cubicQuaternion.tangentIn.y);
                floatBuffer.put(cubicQuaternion.tangentIn.z);
                floatBuffer.put(cubicQuaternion.tangentIn.w);
                floatBuffer.put(quaternion.x);
                floatBuffer.put(quaternion.y);
                floatBuffer.put(quaternion.z);
                floatBuffer.put(quaternion.w);
                floatBuffer.put(cubicQuaternion.tangentOut.x);
                floatBuffer.put(cubicQuaternion.tangentOut.y);
                floatBuffer.put(cubicQuaternion.tangentOut.z);
                floatBuffer.put(cubicQuaternion.tangentOut.w);
                return;
            }
            floatBuffer.put(quaternion.x);
            floatBuffer.put(quaternion.y);
            floatBuffer.put(quaternion.z);
            floatBuffer.put(quaternion.w);
        }
    };

    static abstract class ChannelExporter<T> {
        private final int numComponents;
        private final int numElements;
        private final String outputType;

        protected abstract void getOutput(FloatBuffer floatBuffer, T t);

        public ChannelExporter(int i, String str) {
            this(i, str, 1);
        }

        public ChannelExporter(int i, String str, int i2) {
            this.numComponents = i;
            this.outputType = str;
            this.numElements = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void export(GLTFExporter gLTFExporter, GLTFAnimation gLTFAnimation, int i, Array<NodeKeyframe<T>> array, String str, Interpolation interpolation) {
            if (gLTFAnimation.channels == null) {
                gLTFAnimation.channels = new Array<>();
            }
            GLTFAnimationChannel gLTFAnimationChannel = new GLTFAnimationChannel();
            gLTFAnimation.channels.add(gLTFAnimationChannel);
            if (gLTFAnimation.samplers == null) {
                gLTFAnimation.samplers = new Array<>();
            }
            GLTFAnimationSampler gLTFAnimationSampler = new GLTFAnimationSampler();
            gLTFAnimation.samplers.add(gLTFAnimationSampler);
            gLTFAnimationChannel.sampler = Integer.valueOf(gLTFAnimation.samplers.size - 1);
            gLTFAnimationSampler.interpolation = GLTFAnimationExporter.mapInterpolation(interpolation);
            gLTFAnimationChannel.target = new GLTFAnimationTarget();
            gLTFAnimationChannel.target.node = Integer.valueOf(i);
            gLTFAnimationChannel.target.path = str;
            int i2 = array.size;
            float[] fArr = new float[i2];
            int i3 = (interpolation == Interpolation.CUBICSPLINE ? 3 : 1) * i2 * this.numElements;
            FloatBuffer floatBufferBeginFloats = gLTFExporter.binManager.beginFloats(this.numComponents * i3);
            for (int i4 = 0; i4 < i2; i4++) {
                NodeKeyframe<T> nodeKeyframe = array.get(i4);
                fArr[i4] = nodeKeyframe.keytime;
                getOutput(floatBufferBeginFloats, nodeKeyframe.value);
            }
            GLTFAccessor gLTFAccessorObtainAccessor = gLTFExporter.obtainAccessor();
            gLTFAccessorObtainAccessor.bufferView = Integer.valueOf(gLTFExporter.binManager.end());
            gLTFAccessorObtainAccessor.componentType = 5126;
            gLTFAccessorObtainAccessor.count = i3;
            gLTFAccessorObtainAccessor.type = this.outputType;
            gLTFAnimationSampler.output = Integer.valueOf(gLTFExporter.root.accessors.size - 1);
            gLTFExporter.binManager.beginFloats(i2).put(fArr);
            GLTFAccessor gLTFAccessorObtainAccessor2 = gLTFExporter.obtainAccessor();
            gLTFAccessorObtainAccessor2.componentType = 5126;
            gLTFAccessorObtainAccessor2.count = i2;
            gLTFAccessorObtainAccessor2.type = GLTFTypes.TYPE_SCALAR;
            gLTFAccessorObtainAccessor2.bufferView = Integer.valueOf(gLTFExporter.binManager.end());
            gLTFAccessorObtainAccessor2.min = new float[]{fArr[0]};
            gLTFAccessorObtainAccessor2.max = new float[]{fArr[i2 - 1]};
            gLTFAnimationSampler.input = Integer.valueOf(gLTFExporter.root.accessors.size - 1);
        }
    }

    private static ChannelExporter<WeightVector> channelExporterWeights(int i) {
        return new ChannelExporter<WeightVector>(1, GLTFTypes.TYPE_SCALAR, i) { // from class: net.mgsx.gltf.exporters.GLTFAnimationExporter.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.mgsx.gltf.exporters.GLTFAnimationExporter.ChannelExporter
            public void getOutput(FloatBuffer floatBuffer, WeightVector weightVector) {
                if (weightVector instanceof CubicWeightVector) {
                    CubicWeightVector cubicWeightVector = (CubicWeightVector) weightVector;
                    floatBuffer.put(cubicWeightVector.tangentIn.values, 0, weightVector.count);
                    floatBuffer.put(weightVector.values, 0, weightVector.count);
                    floatBuffer.put(cubicWeightVector.tangentOut.values, 0, weightVector.count);
                    return;
                }
                floatBuffer.put(weightVector.values, 0, weightVector.count);
            }
        };
    }

    public GLTFAnimationExporter(GLTFExporter gLTFExporter) {
        this.base = gLTFExporter;
    }

    public void export(Array<Animation> array) {
        Array.ArrayIterator<Animation> it = array.iterator();
        while (it.hasNext()) {
            export(it.next());
        }
    }

    private void export(Animation animation) {
        GLTFAnimation gLTFAnimation = new GLTFAnimation();
        gLTFAnimation.f408name = animation.f102id;
        if (this.base.root.animations == null) {
            this.base.root.animations = new Array<>();
        }
        this.base.root.animations.add(gLTFAnimation);
        Array.ArrayIterator<NodeAnimation> it = animation.nodeAnimations.iterator();
        while (it.hasNext()) {
            NodeAnimation next = it.next();
            int iIndexOf = this.base.nodeMapping.indexOf(next.node, true);
            if (next.translation != null) {
                channelExporterVector3.export(this.base, gLTFAnimation, iIndexOf, next.translation, "translation", translationInterpolation(next));
            }
            if (next.rotation != null) {
                channelExporterQuaternion.export(this.base, gLTFAnimation, iIndexOf, next.rotation, Key.ROTATION, rotationInterpolation(next));
            }
            if (next.scaling != null) {
                channelExporterVector3.export(this.base, gLTFAnimation, iIndexOf, next.scaling, "scale", scaleInterpolation(next));
            }
            if (next instanceof NodeAnimationHack) {
                NodeAnimationHack nodeAnimationHack = (NodeAnimationHack) next;
                if (nodeAnimationHack.weights != null) {
                    channelExporterWeights(nodeAnimationHack.weights.first().value.count).export(this.base, gLTFAnimation, iIndexOf, nodeAnimationHack.weights, "weights", nodeAnimationHack.weightsMode);
                }
            }
        }
    }

    private Interpolation translationInterpolation(NodeAnimation nodeAnimation) {
        if (nodeAnimation instanceof NodeAnimationHack) {
            return ((NodeAnimationHack) nodeAnimation).translationMode;
        }
        return null;
    }

    private Interpolation rotationInterpolation(NodeAnimation nodeAnimation) {
        if (nodeAnimation instanceof NodeAnimationHack) {
            return ((NodeAnimationHack) nodeAnimation).rotationMode;
        }
        return null;
    }

    private Interpolation scaleInterpolation(NodeAnimation nodeAnimation) {
        if (nodeAnimation instanceof NodeAnimationHack) {
            return ((NodeAnimationHack) nodeAnimation).scalingMode;
        }
        return null;
    }

    public static String mapInterpolation(Interpolation interpolation) {
        if (interpolation == null || interpolation == Interpolation.LINEAR) {
            return null;
        }
        if (interpolation == Interpolation.STEP) {
            return "STEP";
        }
        if (interpolation == Interpolation.CUBICSPLINE) {
            return "CUBICSPLINE";
        }
        throw new GLTFIllegalException("unexpected interpolation type " + interpolation);
    }
}
