package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes12.dex */
public class ClippingPlaneAttribute extends Attribute {
    public final Plane plane;
    public static final String TypeAlias = "clippingPlane";
    public static final long Type = register(TypeAlias);

    public ClippingPlaneAttribute(Plane plane) {
        super(Type);
        this.plane = plane;
    }

    public ClippingPlaneAttribute(Vector3 vector3, float f) {
        super(Type);
        this.plane = new Plane(vector3, f);
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        if (this.type != attribute.type) {
            return this.type < attribute.type ? -1 : 1;
        }
        ClippingPlaneAttribute clippingPlaneAttribute = (ClippingPlaneAttribute) attribute;
        Vector3 vector3 = this.plane.normal;
        Vector3 vector4 = clippingPlaneAttribute.plane.normal;
        if (!MathUtils.isEqual(vector3.x, vector4.x)) {
            return vector3.x < vector4.x ? -1 : 1;
        }
        if (!MathUtils.isEqual(vector3.y, vector4.y)) {
            return vector3.y < vector4.y ? -1 : 1;
        }
        if (!MathUtils.isEqual(vector3.z, vector4.z)) {
            return vector3.z < vector4.z ? -1 : 1;
        }
        if (MathUtils.isEqual(this.plane.d, clippingPlaneAttribute.plane.d)) {
            return 0;
        }
        return this.plane.d < clippingPlaneAttribute.plane.d ? -1 : 1;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new ClippingPlaneAttribute(this.plane.normal, this.plane.d);
    }
}
