package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.math.MathUtils;

/* JADX INFO: loaded from: classes12.dex */
public class PBRTextureAttribute extends TextureAttribute {
    public static final long BRDFLUTTexture;
    public static final String BRDFLUTTextureAlias = "brdfLUTSampler";
    public static final long BaseColorTexture;
    public static final String BaseColorTextureAlias = "diffuseTexture";
    public static final long EmissiveTexture;
    public static final String EmissiveTextureAlias = "emissiveTexture";
    public static final long IridescenceTexture;
    public static final String IridescenceTextureAlias = "IridescenceTexture";
    public static final long IridescenceThicknessTexture;
    public static final String IridescenceThicknessTextureAlias = "IridescenceThicknessTexture";
    public static final long MetallicRoughnessTexture;
    public static final String MetallicRoughnessTextureAlias = "MetallicRoughnessSampler";
    public static final long NormalTexture;
    public static final String NormalTextureAlias = "normalTexture";
    public static final long OcclusionTexture;
    public static final String OcclusionTextureAlias = "OcclusionSampler";
    public static final long SpecularColorTexture;
    public static final String SpecularColorTextureAlias = "SpecularColorTexture";
    public static final long SpecularFactorTexture;
    public static final String SpecularFactorTextureAlias = "SpecularFactorTexture";
    public static final long ThicknessTexture;
    public static final String ThicknessTextureAlias = "ThicknessTexture";
    public static final long TransmissionSourceTexture;
    public static final String TransmissionSourceTextureAlias = "TransmissionSourceTexture";
    public static final long TransmissionTexture;
    public static final String TransmissionTextureAlias = "TransmissionTexture";
    public float rotationUV;

    static {
        long jRegister = register("diffuseTexture");
        BaseColorTexture = jRegister;
        long jRegister2 = register("emissiveTexture");
        EmissiveTexture = jRegister2;
        long jRegister3 = register("normalTexture");
        NormalTexture = jRegister3;
        long jRegister4 = register(MetallicRoughnessTextureAlias);
        MetallicRoughnessTexture = jRegister4;
        long jRegister5 = register(OcclusionTextureAlias);
        OcclusionTexture = jRegister5;
        long jRegister6 = register(BRDFLUTTextureAlias);
        BRDFLUTTexture = jRegister6;
        long jRegister7 = register(TransmissionTextureAlias);
        TransmissionTexture = jRegister7;
        long jRegister8 = register(ThicknessTextureAlias);
        ThicknessTexture = jRegister8;
        long jRegister9 = register(SpecularFactorTextureAlias);
        SpecularFactorTexture = jRegister9;
        long jRegister10 = register(IridescenceTextureAlias);
        IridescenceTexture = jRegister10;
        long jRegister11 = register(IridescenceThicknessTextureAlias);
        IridescenceThicknessTexture = jRegister11;
        long jRegister12 = register(TransmissionSourceTextureAlias);
        TransmissionSourceTexture = jRegister12;
        long jRegister13 = register(SpecularColorTextureAlias);
        SpecularColorTexture = jRegister13;
        Mask |= jRegister | jRegister4 | jRegister5 | jRegister3 | jRegister2 | jRegister6 | jRegister7 | jRegister8 | jRegister9 | jRegister10 | jRegister11 | jRegister12 | jRegister13;
    }

    public PBRTextureAttribute(long j) {
        super(j);
        this.rotationUV = 0.0f;
    }

    public PBRTextureAttribute(long j, TextureDescriptor<Texture> textureDescriptor) {
        super(j, textureDescriptor);
        this.rotationUV = 0.0f;
    }

    public PBRTextureAttribute(long j, Texture texture) {
        super(j, texture);
        this.rotationUV = 0.0f;
    }

    public PBRTextureAttribute(long j, TextureRegion textureRegion) {
        super(j, textureRegion);
        this.rotationUV = 0.0f;
    }

    public PBRTextureAttribute(PBRTextureAttribute pBRTextureAttribute) {
        super(pBRTextureAttribute);
        this.rotationUV = 0.0f;
        this.rotationUV = pBRTextureAttribute.rotationUV;
    }

    public static PBRTextureAttribute createBaseColorTexture(Texture texture) {
        return new PBRTextureAttribute(BaseColorTexture, texture);
    }

    public static PBRTextureAttribute createEmissiveTexture(Texture texture) {
        return new PBRTextureAttribute(EmissiveTexture, texture);
    }

    public static PBRTextureAttribute createNormalTexture(Texture texture) {
        return new PBRTextureAttribute(NormalTexture, texture);
    }

    public static PBRTextureAttribute createMetallicRoughnessTexture(Texture texture) {
        return new PBRTextureAttribute(MetallicRoughnessTexture, texture);
    }

    public static PBRTextureAttribute createOcclusionTexture(Texture texture) {
        return new PBRTextureAttribute(OcclusionTexture, texture);
    }

    public static PBRTextureAttribute createBRDFLookupTexture(Texture texture) {
        return new PBRTextureAttribute(BRDFLUTTexture, texture);
    }

    public static PBRTextureAttribute createTransmissionTexture(Texture texture) {
        return new PBRTextureAttribute(TransmissionTexture, texture);
    }

    public static PBRTextureAttribute createThicknessTexture(Texture texture) {
        return new PBRTextureAttribute(ThicknessTexture, texture);
    }

    public static PBRTextureAttribute createSpecularFactorTexture(Texture texture) {
        return new PBRTextureAttribute(SpecularFactorTexture, texture);
    }

    public static PBRTextureAttribute createIridescenceTexture(Texture texture) {
        return new PBRTextureAttribute(IridescenceTexture, texture);
    }

    public static PBRTextureAttribute createIridescenceThicknessTexture(Texture texture) {
        return new PBRTextureAttribute(IridescenceThicknessTexture, texture);
    }

    public static PBRTextureAttribute createBaseColorTexture(TextureRegion textureRegion) {
        return new PBRTextureAttribute(BaseColorTexture, textureRegion);
    }

    public static PBRTextureAttribute createEmissiveTexture(TextureRegion textureRegion) {
        return new PBRTextureAttribute(EmissiveTexture, textureRegion);
    }

    public static PBRTextureAttribute createNormalTexture(TextureRegion textureRegion) {
        return new PBRTextureAttribute(NormalTexture, textureRegion);
    }

    public static PBRTextureAttribute createMetallicRoughnessTexture(TextureRegion textureRegion) {
        return new PBRTextureAttribute(MetallicRoughnessTexture, textureRegion);
    }

    public static PBRTextureAttribute createOcclusionTexture(TextureRegion textureRegion) {
        return new PBRTextureAttribute(OcclusionTexture, textureRegion);
    }

    public static PBRTextureAttribute createBRDFLookupTexture(TextureRegion textureRegion) {
        return new PBRTextureAttribute(BRDFLUTTexture, textureRegion);
    }

    public static PBRTextureAttribute createTransmissionTexture(TextureRegion textureRegion) {
        return new PBRTextureAttribute(TransmissionTexture, textureRegion);
    }

    public static PBRTextureAttribute createThicknessTexture(TextureRegion textureRegion) {
        return new PBRTextureAttribute(ThicknessTexture, textureRegion);
    }

    public static PBRTextureAttribute createSpecularFactorTexture(TextureRegion textureRegion) {
        return new PBRTextureAttribute(SpecularFactorTexture, textureRegion);
    }

    public static PBRTextureAttribute createIridescenceTexture(TextureRegion textureRegion) {
        return new PBRTextureAttribute(IridescenceTexture, textureRegion);
    }

    public static PBRTextureAttribute createIridescenceThicknessTexture(TextureRegion textureRegion) {
        return new PBRTextureAttribute(IridescenceThicknessTexture, textureRegion);
    }

    @Override // com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute, com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new PBRTextureAttribute(this);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute, java.lang.Comparable
    public int compareTo(Attribute attribute) {
        int iCompareTo = super.compareTo(attribute);
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!(attribute instanceof PBRTextureAttribute)) {
            return 0;
        }
        PBRTextureAttribute pBRTextureAttribute = (PBRTextureAttribute) attribute;
        if (MathUtils.isEqual(this.rotationUV, pBRTextureAttribute.rotationUV)) {
            return 0;
        }
        return this.rotationUV < pBRTextureAttribute.rotationUV ? -1 : 1;
    }
}
