package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.utils.Array;
import com.gaurav.avnc.vnc.XKeySym;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/attributes/DirectionalLightsAttribute.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class DirectionalLightsAttribute extends Attribute {
    public static final String Alias = "directionalLights";
    public static final long Type = register(Alias);
    public final Array<DirectionalLight> lights;

    public static final boolean is(long j) {
        return (Type & j) == j;
    }

    public DirectionalLightsAttribute() {
        super(Type);
        this.lights = new Array<>(1);
    }

    public DirectionalLightsAttribute(DirectionalLightsAttribute directionalLightsAttribute) {
        this();
        this.lights.addAll(directionalLightsAttribute.lights);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public DirectionalLightsAttribute copy() {
        return new DirectionalLightsAttribute(this);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public int hashCode() {
        int iHashCode = super.hashCode();
        Array.ArrayIterator<DirectionalLight> it = this.lights.iterator();
        while (it.hasNext()) {
            DirectionalLight next = it.next();
            iHashCode = (iHashCode * XKeySym.XK_kana_HE) + (next == null ? 0 : next.hashCode());
        }
        return iHashCode;
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        if (this.type != attribute.type) {
            return this.type < attribute.type ? -1 : 1;
        }
        return 0;
    }
}
