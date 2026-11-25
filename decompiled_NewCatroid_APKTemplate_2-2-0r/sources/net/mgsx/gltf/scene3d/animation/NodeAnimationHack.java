package net.mgsx.gltf.scene3d.animation;

import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.loaders.shared.animation.Interpolation;
import net.mgsx.gltf.scene3d.model.WeightVector;

/* JADX INFO: loaded from: classes12.dex */
public class NodeAnimationHack extends NodeAnimation {
    public Interpolation rotationMode;
    public Interpolation scalingMode;
    public Interpolation translationMode;
    public Array<NodeKeyframe<WeightVector>> weights = null;
    public Interpolation weightsMode;
}
