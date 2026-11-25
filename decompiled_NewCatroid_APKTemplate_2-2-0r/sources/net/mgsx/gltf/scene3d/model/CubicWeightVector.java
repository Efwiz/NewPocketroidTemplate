package net.mgsx.gltf.scene3d.model;

/* JADX INFO: loaded from: classes12.dex */
public class CubicWeightVector extends WeightVector {
    public final WeightVector tangentIn;
    public final WeightVector tangentOut;

    public CubicWeightVector(int i) {
        super(i);
        this.tangentIn = new WeightVector(i);
        this.tangentOut = new WeightVector(i);
    }
}
