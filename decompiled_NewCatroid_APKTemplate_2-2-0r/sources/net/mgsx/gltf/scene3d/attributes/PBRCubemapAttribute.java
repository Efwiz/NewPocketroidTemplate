package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;

/* JADX INFO: loaded from: classes12.dex */
public class PBRCubemapAttribute extends CubemapAttribute {
    public static final long DiffuseEnv;
    public static final String DiffuseEnvAlias = "DiffuseEnvSampler";
    public static final long SpecularEnv;
    public static final String SpecularEnvAlias = "SpecularEnvSampler";

    static {
        long jRegister = register(DiffuseEnvAlias);
        DiffuseEnv = jRegister;
        long jRegister2 = register(SpecularEnvAlias);
        SpecularEnv = jRegister2;
        Mask = jRegister | jRegister2 | Mask;
    }

    public PBRCubemapAttribute(long j, TextureDescriptor<Cubemap> textureDescriptor) {
        super(j, textureDescriptor);
    }

    public PBRCubemapAttribute(long j, Cubemap cubemap) {
        super(j, cubemap);
    }

    public static Attribute createDiffuseEnv(Cubemap cubemap) {
        return new PBRCubemapAttribute(DiffuseEnv, cubemap);
    }

    public static Attribute createSpecularEnv(Cubemap cubemap) {
        return new PBRCubemapAttribute(SpecularEnv, cubemap);
    }

    @Override // com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute, com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new PBRCubemapAttribute(this.type, this.textureDescription);
    }
}
