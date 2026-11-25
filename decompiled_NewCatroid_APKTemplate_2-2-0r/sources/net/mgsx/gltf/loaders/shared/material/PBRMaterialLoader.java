package net.mgsx.gltf.loaders.shared.material;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.math.MathUtils;
import net.mgsx.gltf.data.extensions.KHRMaterialsEmissiveStrength;
import net.mgsx.gltf.data.extensions.KHRMaterialsIOR;
import net.mgsx.gltf.data.extensions.KHRMaterialsIridescence;
import net.mgsx.gltf.data.extensions.KHRMaterialsPBRSpecularGlossiness;
import net.mgsx.gltf.data.extensions.KHRMaterialsSpecular;
import net.mgsx.gltf.data.extensions.KHRMaterialsTransmission;
import net.mgsx.gltf.data.extensions.KHRMaterialsUnlit;
import net.mgsx.gltf.data.extensions.KHRMaterialsVolume;
import net.mgsx.gltf.data.extensions.KHRTextureTransform;
import net.mgsx.gltf.data.material.GLTFMaterial;
import net.mgsx.gltf.data.material.GLTFpbrMetallicRoughness;
import net.mgsx.gltf.data.texture.GLTFTextureInfo;
import net.mgsx.gltf.loaders.exceptions.GLTFIllegalException;
import net.mgsx.gltf.loaders.shared.GLTFLoaderBase;
import net.mgsx.gltf.loaders.shared.GLTFTypes;
import net.mgsx.gltf.loaders.shared.texture.TextureResolver;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFlagAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRHDRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRIridescenceAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRVolumeAttribute;

/* JADX INFO: loaded from: classes12.dex */
public class PBRMaterialLoader extends MaterialLoaderBase {
    public PBRMaterialLoader(TextureResolver textureResolver) {
        super(textureResolver, new Material(new PBRColorAttribute(PBRColorAttribute.BaseColorFactor, Color.WHITE)));
    }

    @Override // net.mgsx.gltf.loaders.shared.material.MaterialLoaderBase
    public Material loadMaterial(GLTFMaterial gLTFMaterial) {
        boolean z;
        Material material = new Material();
        if (gLTFMaterial.f408name != null) {
            material.f100id = gLTFMaterial.f408name;
        }
        if (gLTFMaterial.emissiveFactor != null) {
            material.set(new ColorAttribute(ColorAttribute.Emissive, GLTFTypes.mapColor(gLTFMaterial.emissiveFactor, Color.BLACK)));
        }
        if (gLTFMaterial.emissiveTexture != null) {
            material.set(getTexureMap(PBRTextureAttribute.EmissiveTexture, gLTFMaterial.emissiveTexture));
        }
        if (gLTFMaterial.doubleSided == Boolean.TRUE) {
            material.set(IntAttribute.createCullFace(0));
        }
        if (gLTFMaterial.normalTexture != null) {
            material.set(getTexureMap(PBRTextureAttribute.NormalTexture, gLTFMaterial.normalTexture));
            material.set(PBRFloatAttribute.createNormalScale(gLTFMaterial.normalTexture.scale));
        }
        if (gLTFMaterial.occlusionTexture != null) {
            material.set(getTexureMap(PBRTextureAttribute.OcclusionTexture, gLTFMaterial.occlusionTexture));
            material.set(PBRFloatAttribute.createOcclusionStrength(gLTFMaterial.occlusionTexture.strength));
        }
        if ("OPAQUE".equals(gLTFMaterial.alphaMode)) {
            z = false;
        } else {
            if ("MASK".equals(gLTFMaterial.alphaMode)) {
                material.set(FloatAttribute.createAlphaTest(gLTFMaterial.alphaCutoff == null ? 0.5f : gLTFMaterial.alphaCutoff.floatValue()));
                material.set(new BlendingAttribute());
            } else if ("BLEND".equals(gLTFMaterial.alphaMode)) {
                material.set(new BlendingAttribute());
                z = true;
            } else if (gLTFMaterial.alphaMode != null) {
                throw new GLTFIllegalException("unknow alpha mode : " + gLTFMaterial.alphaMode);
            }
            z = false;
        }
        if (gLTFMaterial.pbrMetallicRoughness != null) {
            GLTFpbrMetallicRoughness gLTFpbrMetallicRoughness = gLTFMaterial.pbrMetallicRoughness;
            Color colorMapColor = GLTFTypes.mapColor(gLTFpbrMetallicRoughness.baseColorFactor, Color.WHITE);
            material.set(new PBRColorAttribute(PBRColorAttribute.BaseColorFactor, colorMapColor));
            material.set(PBRFloatAttribute.createMetallic(gLTFpbrMetallicRoughness.metallicFactor));
            material.set(PBRFloatAttribute.createRoughness(gLTFpbrMetallicRoughness.roughnessFactor));
            if (gLTFpbrMetallicRoughness.metallicRoughnessTexture != null) {
                material.set(getTexureMap(PBRTextureAttribute.MetallicRoughnessTexture, gLTFpbrMetallicRoughness.metallicRoughnessTexture));
            }
            if (gLTFpbrMetallicRoughness.baseColorTexture != null) {
                material.set(getTexureMap(PBRTextureAttribute.BaseColorTexture, gLTFpbrMetallicRoughness.baseColorTexture));
            }
            if (z) {
                ((BlendingAttribute) material.get(BlendingAttribute.class, BlendingAttribute.Type)).opacity = colorMapColor.a;
            }
        }
        if (gLTFMaterial.extensions != null) {
            KHRMaterialsPBRSpecularGlossiness kHRMaterialsPBRSpecularGlossiness = (KHRMaterialsPBRSpecularGlossiness) gLTFMaterial.extensions.get(KHRMaterialsPBRSpecularGlossiness.class, KHRMaterialsPBRSpecularGlossiness.EXT);
            if (kHRMaterialsPBRSpecularGlossiness != null) {
                Gdx.f82app.error(GLTFLoaderBase.TAG, "KHR_materials_pbrSpecularGlossiness extension is deprecated by glTF 2.0 specification and not fully supported.");
                material.set(new ColorAttribute(ColorAttribute.Diffuse, GLTFTypes.mapColor(kHRMaterialsPBRSpecularGlossiness.diffuseFactor, Color.WHITE)));
                material.set(new ColorAttribute(ColorAttribute.Specular, GLTFTypes.mapColor(kHRMaterialsPBRSpecularGlossiness.specularFactor, Color.WHITE)));
                material.set(new FloatAttribute(FloatAttribute.Shininess, MathUtils.lerp(1.0f, 100.0f, kHRMaterialsPBRSpecularGlossiness.glossinessFactor)));
                if (kHRMaterialsPBRSpecularGlossiness.diffuseTexture != null) {
                    material.set(getTexureMap(PBRTextureAttribute.Diffuse, kHRMaterialsPBRSpecularGlossiness.diffuseTexture));
                }
                if (kHRMaterialsPBRSpecularGlossiness.specularGlossinessTexture != null) {
                    material.set(getTexureMap(PBRTextureAttribute.Specular, kHRMaterialsPBRSpecularGlossiness.specularGlossinessTexture));
                }
            }
            if (((KHRMaterialsUnlit) gLTFMaterial.extensions.get(KHRMaterialsUnlit.class, KHRMaterialsUnlit.EXT)) != null) {
                material.set(new PBRFlagAttribute(PBRFlagAttribute.Unlit));
            }
            KHRMaterialsTransmission kHRMaterialsTransmission = (KHRMaterialsTransmission) gLTFMaterial.extensions.get(KHRMaterialsTransmission.class, KHRMaterialsTransmission.EXT);
            if (kHRMaterialsTransmission != null) {
                material.set(PBRFloatAttribute.createTransmissionFactor(kHRMaterialsTransmission.transmissionFactor));
                if (kHRMaterialsTransmission.transmissionTexture != null) {
                    material.set(getTexureMap(PBRTextureAttribute.TransmissionTexture, kHRMaterialsTransmission.transmissionTexture));
                }
            }
            KHRMaterialsVolume kHRMaterialsVolume = (KHRMaterialsVolume) gLTFMaterial.extensions.get(KHRMaterialsVolume.class, KHRMaterialsVolume.EXT);
            if (kHRMaterialsVolume != null) {
                material.set(new PBRVolumeAttribute(kHRMaterialsVolume.thicknessFactor, kHRMaterialsVolume.attenuationDistance == null ? 0.0f : kHRMaterialsVolume.attenuationDistance.floatValue(), GLTFTypes.mapColor(kHRMaterialsVolume.attenuationColor, Color.WHITE)));
                if (kHRMaterialsVolume.thicknessTexture != null) {
                    material.set(getTexureMap(PBRTextureAttribute.ThicknessTexture, kHRMaterialsVolume.thicknessTexture));
                }
            }
            KHRMaterialsIOR kHRMaterialsIOR = (KHRMaterialsIOR) gLTFMaterial.extensions.get(KHRMaterialsIOR.class, KHRMaterialsIOR.EXT);
            if (kHRMaterialsIOR != null) {
                material.set(PBRFloatAttribute.createIOR(kHRMaterialsIOR.ior));
            }
            KHRMaterialsSpecular kHRMaterialsSpecular = (KHRMaterialsSpecular) gLTFMaterial.extensions.get(KHRMaterialsSpecular.class, KHRMaterialsSpecular.EXT);
            if (kHRMaterialsSpecular != null) {
                material.set(PBRFloatAttribute.createSpecularFactor(kHRMaterialsSpecular.specularFactor));
                material.set(new PBRHDRColorAttribute(PBRHDRColorAttribute.Specular, kHRMaterialsSpecular.specularColorFactor[0], kHRMaterialsSpecular.specularColorFactor[1], kHRMaterialsSpecular.specularColorFactor[2]));
                if (kHRMaterialsSpecular.specularTexture != null) {
                    material.set(getTexureMap(PBRTextureAttribute.SpecularFactorTexture, kHRMaterialsSpecular.specularTexture));
                }
                if (kHRMaterialsSpecular.specularColorTexture != null) {
                    material.set(getTexureMap(PBRTextureAttribute.SpecularColorTexture, kHRMaterialsSpecular.specularColorTexture));
                }
            }
            KHRMaterialsIridescence kHRMaterialsIridescence = (KHRMaterialsIridescence) gLTFMaterial.extensions.get(KHRMaterialsIridescence.class, KHRMaterialsIridescence.EXT);
            if (kHRMaterialsIridescence != null) {
                material.set(new PBRIridescenceAttribute(kHRMaterialsIridescence.iridescenceFactor, kHRMaterialsIridescence.iridescenceIor, kHRMaterialsIridescence.iridescenceThicknessMinimum, kHRMaterialsIridescence.iridescenceThicknessMaximum));
                if (kHRMaterialsIridescence.iridescenceTexture != null) {
                    material.set(getTexureMap(PBRTextureAttribute.IridescenceTexture, kHRMaterialsIridescence.iridescenceTexture));
                }
                if (kHRMaterialsIridescence.iridescenceThicknessTexture != null) {
                    material.set(getTexureMap(PBRTextureAttribute.IridescenceThicknessTexture, kHRMaterialsIridescence.iridescenceThicknessTexture));
                }
            }
            KHRMaterialsEmissiveStrength kHRMaterialsEmissiveStrength = (KHRMaterialsEmissiveStrength) gLTFMaterial.extensions.get(KHRMaterialsEmissiveStrength.class, KHRMaterialsEmissiveStrength.EXT);
            if (kHRMaterialsEmissiveStrength != null) {
                material.set(PBRFloatAttribute.createEmissiveIntensity(kHRMaterialsEmissiveStrength.emissiveStrength));
            }
        }
        return material;
    }

    protected PBRTextureAttribute getTexureMap(long j, GLTFTextureInfo gLTFTextureInfo) {
        KHRTextureTransform kHRTextureTransform;
        PBRTextureAttribute pBRTextureAttribute = new PBRTextureAttribute(j, this.textureResolver.getTexture(gLTFTextureInfo));
        pBRTextureAttribute.uvIndex = gLTFTextureInfo.texCoord;
        if (gLTFTextureInfo.extensions != null && (kHRTextureTransform = (KHRTextureTransform) gLTFTextureInfo.extensions.get(KHRTextureTransform.class, KHRTextureTransform.EXT)) != null) {
            pBRTextureAttribute.offsetU = kHRTextureTransform.offset[0];
            pBRTextureAttribute.offsetV = kHRTextureTransform.offset[1];
            pBRTextureAttribute.scaleU = kHRTextureTransform.scale[0];
            pBRTextureAttribute.scaleV = kHRTextureTransform.scale[1];
            pBRTextureAttribute.rotationUV = kHRTextureTransform.rotation;
            if (kHRTextureTransform.texCoord != null) {
                pBRTextureAttribute.uvIndex = kHRTextureTransform.texCoord.intValue();
            }
        }
        return pBRTextureAttribute;
    }
}
