package net.mgsx.gltf.data.geometry;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import net.mgsx.gltf.data.GLTFObject;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFPrimitive extends GLTFObject {
    public ObjectMap<String, Integer> attributes;
    public Integer indices;
    public Integer material;
    public Integer mode;
    public Array<GLTFMorphTarget> targets;
}
