package net.mgsx.gltf.exporters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import net.mgsx.gltf.data.GLTFExtensions;
import net.mgsx.gltf.data.extensions.KHRMaterialsEmissiveStrength;
import net.mgsx.gltf.data.extensions.KHRMaterialsIOR;
import net.mgsx.gltf.data.extensions.KHRMaterialsIridescence;
import net.mgsx.gltf.data.extensions.KHRMaterialsSpecular;
import net.mgsx.gltf.data.extensions.KHRMaterialsTransmission;
import net.mgsx.gltf.data.extensions.KHRMaterialsUnlit;
import net.mgsx.gltf.data.extensions.KHRMaterialsVolume;
import net.mgsx.gltf.data.material.GLTFMaterial;
import net.mgsx.gltf.data.material.GLTFpbrMetallicRoughness;
import net.mgsx.gltf.data.texture.GLTFImage;
import net.mgsx.gltf.data.texture.GLTFNormalTextureInfo;
import net.mgsx.gltf.data.texture.GLTFOcclusionTextureInfo;
import net.mgsx.gltf.data.texture.GLTFSampler;
import net.mgsx.gltf.data.texture.GLTFTexture;
import net.mgsx.gltf.data.texture.GLTFTextureInfo;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFlagAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRHDRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRIridescenceAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRVolumeAttribute;

/* JADX INFO: loaded from: classes12.dex */
class GLTFMaterialExporter {
    private final GLTFExporter base;

    public GLTFMaterialExporter(GLTFExporter gLTFExporter) {
        this.base = gLTFExporter;
    }

    public void export(Iterable<Node> iterable) {
        for (Node node : iterable) {
            Array.ArrayIterator<NodePart> it = node.parts.iterator();
            while (it.hasNext()) {
                export(it.next().material);
            }
            export(node.getChildren());
        }
    }

    private void export(Material material) {
        if (this.base.materialMapping.contains(material, true)) {
            return;
        }
        this.base.materialMapping.add(material);
        GLTFMaterial gLTFMaterial = new GLTFMaterial();
        if (this.base.root.materials == null) {
            this.base.root.materials = new Array<>();
        }
        this.base.root.materials.add(gLTFMaterial);
        gLTFMaterial.f408name = material.f100id;
        boolean z = false;
        for (Attribute attribute : material) {
            if (attribute.type == ColorAttribute.Diffuse) {
                pbr(gLTFMaterial).baseColorFactor = GLTFExportTypes.rgba(defaultNull(Color.WHITE, (ColorAttribute) attribute));
            } else if (attribute.type == PBRColorAttribute.BaseColorFactor) {
                pbr(gLTFMaterial).baseColorFactor = GLTFExportTypes.rgba(defaultNull(Color.WHITE, (PBRColorAttribute) attribute));
            } else if (attribute.type == ColorAttribute.Emissive) {
                gLTFMaterial.emissiveFactor = GLTFExportTypes.rgb(defaultNull(Color.BLACK, (ColorAttribute) attribute));
            } else if (attribute.type == BlendingAttribute.Type) {
                z = true;
            } else if (attribute.type == IntAttribute.CullFace) {
                gLTFMaterial.doubleSided = defaultNull(true, ((IntAttribute) attribute).value == 0);
            } else if (attribute.type == FloatAttribute.AlphaTest) {
                gLTFMaterial.alphaCutoff = Float.valueOf(((FloatAttribute) attribute).value);
            } else if (attribute.type == PBRFloatAttribute.Metallic) {
                pbr(gLTFMaterial).metallicFactor = ((PBRFloatAttribute) attribute).value;
            } else if (attribute.type == PBRFloatAttribute.Roughness) {
                pbr(gLTFMaterial).roughnessFactor = ((PBRFloatAttribute) attribute).value;
            } else if (attribute.type == PBRTextureAttribute.BaseColorTexture) {
                pbr(gLTFMaterial).baseColorTexture = texture((TextureAttribute) attribute);
            } else if (attribute.type == PBRTextureAttribute.MetallicRoughnessTexture) {
                pbr(gLTFMaterial).metallicRoughnessTexture = texture((TextureAttribute) attribute);
            } else if (attribute.type == PBRTextureAttribute.EmissiveTexture) {
                gLTFMaterial.emissiveTexture = texture((TextureAttribute) attribute);
            } else if (attribute.type == PBRTextureAttribute.NormalTexture) {
                gLTFMaterial.normalTexture = normalTexture((PBRTextureAttribute) attribute, material);
            } else if (attribute.type == PBRTextureAttribute.OcclusionTexture) {
                gLTFMaterial.occlusionTexture = occlusionTexture((PBRTextureAttribute) attribute, material);
            } else if (attribute.type == PBRFlagAttribute.Unlit) {
                ext(gLTFMaterial, KHRMaterialsUnlit.class, KHRMaterialsUnlit.EXT);
            } else if (attribute.type == PBRFloatAttribute.TransmissionFactor) {
                extTransmission(gLTFMaterial).transmissionFactor = ((PBRFloatAttribute) attribute).value;
            } else if (attribute.type == PBRTextureAttribute.TransmissionTexture) {
                extTransmission(gLTFMaterial).transmissionTexture = texture((PBRTextureAttribute) attribute);
            } else if (attribute.type == PBRVolumeAttribute.Type) {
                KHRMaterialsVolume kHRMaterialsVolumeExtVolume = extVolume(gLTFMaterial);
                PBRVolumeAttribute pBRVolumeAttribute = (PBRVolumeAttribute) attribute;
                kHRMaterialsVolumeExtVolume.thicknessFactor = pBRVolumeAttribute.thicknessFactor;
                kHRMaterialsVolumeExtVolume.attenuationDistance = pBRVolumeAttribute.attenuationDistance > 0.0f ? Float.valueOf(pBRVolumeAttribute.attenuationDistance) : null;
                kHRMaterialsVolumeExtVolume.attenuationColor = rgb(pBRVolumeAttribute.attenuationColor);
            } else if (attribute.type == PBRTextureAttribute.ThicknessTexture) {
                extVolume(gLTFMaterial).thicknessTexture = texture((PBRTextureAttribute) attribute);
            } else if (attribute.type == PBRFloatAttribute.IOR) {
                extIOR(gLTFMaterial).ior = ((PBRFloatAttribute) attribute).value;
            } else if (attribute.type == PBRFloatAttribute.SpecularFactor) {
                extSpecular(gLTFMaterial).specularFactor = ((PBRFloatAttribute) attribute).value;
            } else if (attribute.type == PBRHDRColorAttribute.Specular) {
                PBRHDRColorAttribute pBRHDRColorAttribute = (PBRHDRColorAttribute) attribute;
                extSpecular(gLTFMaterial).specularColorFactor = new float[]{pBRHDRColorAttribute.r, pBRHDRColorAttribute.g, pBRHDRColorAttribute.b};
            } else if (attribute.type == PBRTextureAttribute.SpecularFactorTexture) {
                extSpecular(gLTFMaterial).specularTexture = texture((PBRTextureAttribute) attribute);
            } else if (attribute.type == PBRTextureAttribute.Specular) {
                extSpecular(gLTFMaterial).specularColorTexture = texture((TextureAttribute) attribute);
            } else if (attribute.type == PBRIridescenceAttribute.Type) {
                PBRIridescenceAttribute pBRIridescenceAttribute = (PBRIridescenceAttribute) attribute;
                KHRMaterialsIridescence kHRMaterialsIridescenceExtIridescence = extIridescence(gLTFMaterial);
                kHRMaterialsIridescenceExtIridescence.iridescenceFactor = pBRIridescenceAttribute.factor;
                kHRMaterialsIridescenceExtIridescence.iridescenceIor = pBRIridescenceAttribute.ior;
                kHRMaterialsIridescenceExtIridescence.iridescenceThicknessMinimum = pBRIridescenceAttribute.thicknessMin;
                kHRMaterialsIridescenceExtIridescence.iridescenceThicknessMaximum = pBRIridescenceAttribute.thicknessMax;
            } else if (attribute.type == PBRTextureAttribute.IridescenceTexture) {
                extIridescence(gLTFMaterial).iridescenceTexture = texture((PBRTextureAttribute) attribute);
            } else if (attribute.type == PBRTextureAttribute.IridescenceThicknessTexture) {
                extIridescence(gLTFMaterial).iridescenceThicknessTexture = texture((PBRTextureAttribute) attribute);
            } else if (attribute.type == PBRFloatAttribute.EmissiveIntensity) {
                extEmissive(gLTFMaterial).emissiveStrength = ((PBRFloatAttribute) attribute).value;
            }
        }
        if (z) {
            if (gLTFMaterial.alphaCutoff != null) {
                gLTFMaterial.alphaMode = "MASK";
            } else {
                gLTFMaterial.alphaMode = "BLEND";
            }
        }
    }

    private float[] rgb(Color color) {
        return new float[]{color.r, color.g, color.b};
    }

    private KHRMaterialsTransmission extTransmission(GLTFMaterial gLTFMaterial) {
        return (KHRMaterialsTransmission) ext(gLTFMaterial, KHRMaterialsTransmission.class, KHRMaterialsTransmission.EXT);
    }

    private KHRMaterialsIOR extIOR(GLTFMaterial gLTFMaterial) {
        return (KHRMaterialsIOR) ext(gLTFMaterial, KHRMaterialsIOR.class, KHRMaterialsIOR.EXT);
    }

    private KHRMaterialsEmissiveStrength extEmissive(GLTFMaterial gLTFMaterial) {
        return (KHRMaterialsEmissiveStrength) ext(gLTFMaterial, KHRMaterialsEmissiveStrength.class, KHRMaterialsEmissiveStrength.EXT);
    }

    private KHRMaterialsSpecular extSpecular(GLTFMaterial gLTFMaterial) {
        return (KHRMaterialsSpecular) ext(gLTFMaterial, KHRMaterialsSpecular.class, KHRMaterialsSpecular.EXT);
    }

    private KHRMaterialsIridescence extIridescence(GLTFMaterial gLTFMaterial) {
        return (KHRMaterialsIridescence) ext(gLTFMaterial, KHRMaterialsIridescence.class, KHRMaterialsIridescence.EXT);
    }

    private KHRMaterialsVolume extVolume(GLTFMaterial gLTFMaterial) {
        return (KHRMaterialsVolume) ext(gLTFMaterial, KHRMaterialsVolume.class, KHRMaterialsVolume.EXT);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private <T> T ext(GLTFMaterial gLTFMaterial, Class<T> cls, String str) {
        if (gLTFMaterial.extensions == null) {
            gLTFMaterial.extensions = new GLTFExtensions();
        }
        T t = (T) gLTFMaterial.extensions.get(cls, str);
        if (t == null) {
            this.base.useExtension(str, false);
            try {
                t = (T) ClassReflection.newInstance(cls);
                gLTFMaterial.extensions.set(str, t);
            } catch (ReflectionException e) {
                throw new GdxRuntimeException(e);
            }
        }
        return t;
    }

    private Boolean defaultNull(boolean z, boolean z2) {
        if (z == z2) {
            return null;
        }
        return Boolean.valueOf(z2);
    }

    protected Color defaultNull(Color color, Color color2) {
        if (color2.equals(color)) {
            return null;
        }
        return color2;
    }

    private ColorAttribute defaultNull(Color color, ColorAttribute colorAttribute) {
        if (colorAttribute.color.equals(color)) {
            return null;
        }
        return colorAttribute;
    }

    private GLTFOcclusionTextureInfo occlusionTexture(PBRTextureAttribute pBRTextureAttribute, Material material) {
        GLTFOcclusionTextureInfo gLTFOcclusionTextureInfo = new GLTFOcclusionTextureInfo();
        gLTFOcclusionTextureInfo.strength = ((PBRFloatAttribute) material.get(PBRFloatAttribute.class, PBRFloatAttribute.OcclusionStrength)).value;
        gLTFOcclusionTextureInfo.texCoord = pBRTextureAttribute.uvIndex;
        gLTFOcclusionTextureInfo.index = Integer.valueOf(getTexture(pBRTextureAttribute));
        return gLTFOcclusionTextureInfo;
    }

    private GLTFNormalTextureInfo normalTexture(PBRTextureAttribute pBRTextureAttribute, Material material) {
        GLTFNormalTextureInfo gLTFNormalTextureInfo = new GLTFNormalTextureInfo();
        gLTFNormalTextureInfo.scale = ((PBRFloatAttribute) material.get(PBRFloatAttribute.class, PBRFloatAttribute.NormalScale)).value;
        gLTFNormalTextureInfo.texCoord = pBRTextureAttribute.uvIndex;
        gLTFNormalTextureInfo.index = Integer.valueOf(getTexture(pBRTextureAttribute));
        return gLTFNormalTextureInfo;
    }

    private GLTFTextureInfo texture(TextureAttribute textureAttribute) {
        GLTFTextureInfo gLTFTextureInfo = new GLTFTextureInfo();
        gLTFTextureInfo.texCoord = textureAttribute.uvIndex;
        gLTFTextureInfo.index = Integer.valueOf(getTexture(textureAttribute));
        return gLTFTextureInfo;
    }

    private int getTexture(TextureAttribute textureAttribute) {
        GLTFTexture gLTFTexture = new GLTFTexture();
        gLTFTexture.sampler = sampler(textureAttribute);
        gLTFTexture.source = source((Texture) textureAttribute.textureDescription.texture);
        if (this.base.root.textures == null) {
            this.base.root.textures = new Array<>();
        }
        this.base.root.textures.add(gLTFTexture);
        return this.base.root.textures.size - 1;
    }

    private Integer source(Texture texture) {
        int iIndexOf = this.base.textureMapping.indexOf(texture, true);
        if (iIndexOf >= 0) {
            return Integer.valueOf(iIndexOf);
        }
        GLTFImage gLTFImage = new GLTFImage();
        if (this.base.root.images == null) {
            this.base.root.images = new Array<>();
        }
        this.base.root.images.add(gLTFImage);
        this.base.textureMapping.add(texture);
        this.base.binManager.export(gLTFImage, texture, this.base.getImageName(texture));
        return Integer.valueOf(this.base.root.images.size - 1);
    }

    private Integer sampler(TextureAttribute textureAttribute) {
        GLTFSampler gLTFSampler = new GLTFSampler();
        gLTFSampler.minFilter = mapMin(textureAttribute.textureDescription.minFilter);
        gLTFSampler.magFilter = mapMag(textureAttribute.textureDescription.magFilter);
        gLTFSampler.wrapS = map(textureAttribute.textureDescription.uWrap);
        gLTFSampler.wrapT = map(textureAttribute.textureDescription.vWrap);
        if (gLTFSampler.minFilter == null && gLTFSampler.magFilter == null && gLTFSampler.wrapS == null && gLTFSampler.wrapT == null) {
            return null;
        }
        if (this.base.root.samplers == null) {
            this.base.root.samplers = new Array<>();
        }
        this.base.root.samplers.add(gLTFSampler);
        return Integer.valueOf(this.base.root.samplers.size - 1);
    }

    private Integer map(Texture.TextureWrap textureWrap) {
        if (textureWrap == null) {
            return null;
        }
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$graphics$Texture$TextureWrap[textureWrap.ordinal()];
        if (i == 1) {
            return Integer.valueOf(GL20.GL_CLAMP_TO_EDGE);
        }
        if (i != 2) {
            return null;
        }
        return Integer.valueOf(GL20.GL_MIRRORED_REPEAT);
    }

    /* JADX INFO: renamed from: net.mgsx.gltf.exporters.GLTFMaterialExporter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureWrap;

        static {
            int[] iArr = new int[Texture.TextureFilter.values().length];
            $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter = iArr;
            try {
                iArr[Texture.TextureFilter.Linear.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[Texture.TextureFilter.Nearest.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[Texture.TextureFilter.MipMap.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[Texture.TextureFilter.MipMapLinearLinear.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[Texture.TextureFilter.MipMapLinearNearest.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[Texture.TextureFilter.MipMapNearestLinear.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[Texture.TextureFilter.MipMapNearestNearest.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[Texture.TextureWrap.values().length];
            $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureWrap = iArr2;
            try {
                iArr2[Texture.TextureWrap.ClampToEdge.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureWrap[Texture.TextureWrap.MirroredRepeat.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$Texture$TextureWrap[Texture.TextureWrap.Repeat.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    private Integer mapMag(Texture.TextureFilter textureFilter) {
        if (textureFilter != null && AnonymousClass1.$SwitchMap$com$badlogic$gdx$graphics$Texture$TextureFilter[textureFilter.ordinal()] == 2) {
            return Integer.valueOf(GL20.GL_NEAREST);
        }
        return null;
    }

    private Integer mapMin(Texture.TextureFilter textureFilter) {
        if (textureFilter == null) {
            return null;
        }
        switch (textureFilter) {
            case Nearest:
                return Integer.valueOf(GL20.GL_NEAREST);
            case MipMap:
            case MipMapLinearLinear:
                return Integer.valueOf(GL20.GL_LINEAR_MIPMAP_LINEAR);
            case MipMapLinearNearest:
                return Integer.valueOf(GL20.GL_LINEAR_MIPMAP_NEAREST);
            case MipMapNearestLinear:
                return Integer.valueOf(GL20.GL_NEAREST_MIPMAP_LINEAR);
            case MipMapNearestNearest:
                return Integer.valueOf(GL20.GL_NEAREST_MIPMAP_NEAREST);
            default:
                return null;
        }
    }

    private GLTFpbrMetallicRoughness pbr(GLTFMaterial gLTFMaterial) {
        if (gLTFMaterial.pbrMetallicRoughness == null) {
            gLTFMaterial.pbrMetallicRoughness = new GLTFpbrMetallicRoughness();
        }
        return gLTFMaterial.pbrMetallicRoughness;
    }
}
