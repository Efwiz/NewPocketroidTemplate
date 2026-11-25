package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;

/* JADX INFO: loaded from: classes12.dex */
public class MirrorAttribute extends Attribute {
    public static final String SpecularAlias = "specularMirror";
    public static final long Specular = register(SpecularAlias);

    public static MirrorAttribute createSpecular() {
        return new MirrorAttribute(Specular);
    }

    public MirrorAttribute(long j) {
        super(j);
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        if (this.type != attribute.type) {
            return this.type < attribute.type ? -1 : 1;
        }
        return 0;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new MirrorAttribute(this.type);
    }
}
