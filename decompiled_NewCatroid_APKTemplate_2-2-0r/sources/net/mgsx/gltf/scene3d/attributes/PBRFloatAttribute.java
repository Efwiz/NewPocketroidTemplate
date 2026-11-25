package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;

/* JADX INFO: loaded from: classes12.dex */
public class PBRFloatAttribute extends FloatAttribute {
    public static final String MetallicAlias = "Metallic";
    public static final long Metallic = register(MetallicAlias);
    public static final String RoughnessAlias = "Roughness";
    public static final long Roughness = register(RoughnessAlias);
    public static final String NormalScaleAlias = "NormalScale";
    public static final long NormalScale = register(NormalScaleAlias);
    public static final String OcclusionStrengthAlias = "OcclusionStrength";
    public static final long OcclusionStrength = register(OcclusionStrengthAlias);
    public static final String ShadowBiasAlias = "ShadowBias";
    public static final long ShadowBias = register(ShadowBiasAlias);
    public static final String EmissiveIntensityAlias = "EmissiveIntensity";
    public static final long EmissiveIntensity = register(EmissiveIntensityAlias);
    public static final String TransmissionFactorAlias = "TransmissionFactor";
    public static final long TransmissionFactor = register(TransmissionFactorAlias);
    public static final String IORAlias = "IOR";
    public static final long IOR = register(IORAlias);
    public static final String SpecularFactorAlias = "SpecularFactor";
    public static final long SpecularFactor = register(SpecularFactorAlias);

    public PBRFloatAttribute(long j, float f) {
        super(j, f);
    }

    @Override // com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute, com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new PBRFloatAttribute(this.type, this.value);
    }

    public static Attribute createMetallic(float f) {
        return new PBRFloatAttribute(Metallic, f);
    }

    public static Attribute createRoughness(float f) {
        return new PBRFloatAttribute(Roughness, f);
    }

    public static Attribute createNormalScale(float f) {
        return new PBRFloatAttribute(NormalScale, f);
    }

    public static Attribute createOcclusionStrength(float f) {
        return new PBRFloatAttribute(OcclusionStrength, f);
    }

    public static Attribute createEmissiveIntensity(float f) {
        return new PBRFloatAttribute(EmissiveIntensity, f);
    }

    public static Attribute createTransmissionFactor(float f) {
        return new PBRFloatAttribute(TransmissionFactor, f);
    }

    public static Attribute createIOR(float f) {
        return new PBRFloatAttribute(IOR, f);
    }

    public static Attribute createSpecularFactor(float f) {
        return new PBRFloatAttribute(SpecularFactor, f);
    }
}
