package net.mgsx.gltf.data.data;

import net.mgsx.gltf.data.GLTFEntity;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFAccessor extends GLTFEntity {
    public Integer bufferView;
    public int componentType;
    public int count;
    public float[] max;
    public float[] min;
    public GLTFAccessorSparse sparse;
    public String type;
    public boolean normalized = false;
    public int byteOffset = 0;
}
