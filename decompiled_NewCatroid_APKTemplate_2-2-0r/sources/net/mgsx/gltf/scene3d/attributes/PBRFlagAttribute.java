package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;

/* JADX INFO: loaded from: classes12.dex */
public class PBRFlagAttribute extends Attribute {
    public static final String UnlitAlias = "unlit";
    public static final long Unlit = register(UnlitAlias);

    public PBRFlagAttribute(long j) {
        super(j);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new PBRFlagAttribute(this.type);
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        return (int) (this.type - attribute.type);
    }
}
