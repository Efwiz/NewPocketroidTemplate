package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes12.dex */
public class MirrorSourceAttribute extends Attribute {
    public final Vector3 normal;
    public final TextureDescriptor<Texture> textureDescription;
    public static final String TypeAlias = "mirrorSource";
    public static final long Type = register(TypeAlias);

    public MirrorSourceAttribute() {
        super(Type);
        this.textureDescription = new TextureDescriptor<>();
        this.normal = new Vector3();
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        if (this.type != attribute.type) {
            return this.type < attribute.type ? -1 : 1;
        }
        MirrorSourceAttribute mirrorSourceAttribute = (MirrorSourceAttribute) attribute;
        int iCompareTo = this.textureDescription.compareTo((TextureDescriptor) mirrorSourceAttribute.textureDescription);
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        Vector3 vector3 = mirrorSourceAttribute.normal;
        if (!MathUtils.isEqual(this.normal.x, vector3.x)) {
            return this.normal.x < vector3.x ? -1 : 1;
        }
        if (!MathUtils.isEqual(this.normal.y, vector3.y)) {
            return this.normal.y < vector3.y ? -1 : 1;
        }
        if (MathUtils.isEqual(this.normal.z, vector3.z)) {
            return 0;
        }
        return this.normal.z < vector3.z ? -1 : 1;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return set(this.textureDescription, this.normal);
    }

    public MirrorSourceAttribute set(TextureDescriptor<Texture> textureDescriptor, Vector3 vector3) {
        this.textureDescription.set(textureDescriptor);
        this.normal.set(vector3);
        return this;
    }
}
