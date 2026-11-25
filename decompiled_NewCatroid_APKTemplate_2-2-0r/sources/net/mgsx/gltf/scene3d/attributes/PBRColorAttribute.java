package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;

/* JADX INFO: loaded from: classes12.dex */
public class PBRColorAttribute extends ColorAttribute {
    public static final long BaseColorFactor;
    public static final String BaseColorFactorAlias = "BaseColorFactor";

    static {
        long jRegister = register(BaseColorFactorAlias);
        BaseColorFactor = jRegister;
        Mask = jRegister | Mask;
    }

    public static PBRColorAttribute createBaseColorFactor(Color color) {
        return new PBRColorAttribute(BaseColorFactor, color);
    }

    public PBRColorAttribute(long j, Color color) {
        super(j, color);
    }

    @Override // com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute, com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new PBRColorAttribute(this.type, this.color);
    }
}
