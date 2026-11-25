package org.catrobat.catroid.raptor;

import com.badlogic.gdx.graphics.g3d.Attribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/EmissiveIntensityAttribute.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EmissiveIntensityAttribute extends PBRFloatAttribute {
    public static final String Alias = "emissiveIntensity";
    public static final long Type = register(Alias);

    public EmissiveIntensityAttribute(float value) {
        super(Type, value);
    }

    @Override // net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute, com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute, com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new EmissiveIntensityAttribute(this.value);
    }
}
