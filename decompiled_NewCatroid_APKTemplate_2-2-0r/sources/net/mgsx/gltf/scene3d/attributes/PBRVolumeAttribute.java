package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.math.MathUtils;

/* JADX INFO: loaded from: classes12.dex */
public class PBRVolumeAttribute extends Attribute {
    public static final String Alias = "volume";
    public static final long Type = register("volume");
    public final Color attenuationColor;
    public float attenuationDistance;
    public float thicknessFactor;

    public PBRVolumeAttribute() {
        super(Type);
        this.thicknessFactor = 0.0f;
        this.attenuationDistance = 0.0f;
        this.attenuationColor = new Color(Color.WHITE);
    }

    public PBRVolumeAttribute(float f, float f2, Color color) {
        super(Type);
        this.thicknessFactor = 0.0f;
        this.attenuationDistance = 0.0f;
        Color color2 = new Color(Color.WHITE);
        this.attenuationColor = color2;
        this.thicknessFactor = f;
        this.attenuationDistance = f2;
        color2.set(color);
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        if (this.type != attribute.type) {
            return this.type < attribute.type ? -1 : 1;
        }
        PBRVolumeAttribute pBRVolumeAttribute = (PBRVolumeAttribute) attribute;
        if (!MathUtils.isEqual(this.thicknessFactor, pBRVolumeAttribute.thicknessFactor)) {
            return this.thicknessFactor < pBRVolumeAttribute.thicknessFactor ? -1 : 1;
        }
        if (MathUtils.isEqual(this.attenuationDistance, pBRVolumeAttribute.attenuationDistance)) {
            return this.attenuationColor.toIntBits() - pBRVolumeAttribute.attenuationColor.toIntBits();
        }
        return this.attenuationDistance < pBRVolumeAttribute.attenuationDistance ? -1 : 1;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new PBRVolumeAttribute(this.thicknessFactor, this.attenuationDistance, this.attenuationColor);
    }
}
