package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/attributes/ColorAttribute.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ColorAttribute extends Attribute {
    public static final long Ambient;
    public static final String AmbientAlias = "ambientColor";
    public static final long AmbientLight;
    public static final String AmbientLightAlias = "ambientLightColor";
    public static final long Diffuse;
    public static final String DiffuseAlias = "diffuseColor";
    public static final long Emissive;
    public static final String EmissiveAlias = "emissiveColor";
    public static final long Fog;
    public static final String FogAlias = "fogColor";
    protected static long Mask = 0;
    public static final long Reflection;
    public static final String ReflectionAlias = "reflectionColor";
    public static final long Specular;
    public static final String SpecularAlias = "specularColor";
    public final Color color;

    static {
        long jRegister = register(DiffuseAlias);
        Diffuse = jRegister;
        long jRegister2 = register(SpecularAlias);
        Specular = jRegister2;
        long jRegister3 = register(AmbientAlias);
        Ambient = jRegister3;
        long jRegister4 = register(EmissiveAlias);
        Emissive = jRegister4;
        long jRegister5 = register(ReflectionAlias);
        Reflection = jRegister5;
        long jRegister6 = register(AmbientLightAlias);
        AmbientLight = jRegister6;
        long jRegister7 = register(FogAlias);
        Fog = jRegister7;
        Mask = jRegister | jRegister3 | jRegister2 | jRegister4 | jRegister5 | jRegister6 | jRegister7;
    }

    public static final boolean is(long j) {
        return (j & Mask) != 0;
    }

    public static final ColorAttribute createAmbient(Color color) {
        return new ColorAttribute(Ambient, color);
    }

    public static final ColorAttribute createAmbient(float f, float f2, float f3, float f4) {
        return new ColorAttribute(Ambient, f, f2, f3, f4);
    }

    public static final ColorAttribute createDiffuse(Color color) {
        return new ColorAttribute(Diffuse, color);
    }

    public static final ColorAttribute createDiffuse(float f, float f2, float f3, float f4) {
        return new ColorAttribute(Diffuse, f, f2, f3, f4);
    }

    public static final ColorAttribute createSpecular(Color color) {
        return new ColorAttribute(Specular, color);
    }

    public static final ColorAttribute createSpecular(float f, float f2, float f3, float f4) {
        return new ColorAttribute(Specular, f, f2, f3, f4);
    }

    public static final ColorAttribute createReflection(Color color) {
        return new ColorAttribute(Reflection, color);
    }

    public static final ColorAttribute createReflection(float f, float f2, float f3, float f4) {
        return new ColorAttribute(Reflection, f, f2, f3, f4);
    }

    public static final ColorAttribute createEmissive(Color color) {
        return new ColorAttribute(Emissive, color);
    }

    public static final ColorAttribute createEmissive(float f, float f2, float f3, float f4) {
        return new ColorAttribute(Emissive, f, f2, f3, f4);
    }

    public static final ColorAttribute createAmbientLight(Color color) {
        return new ColorAttribute(AmbientLight, color);
    }

    public static final ColorAttribute createAmbientLight(float f, float f2, float f3, float f4) {
        return new ColorAttribute(AmbientLight, f, f2, f3, f4);
    }

    public static final ColorAttribute createFog(Color color) {
        return new ColorAttribute(Fog, color);
    }

    public static final ColorAttribute createFog(float f, float f2, float f3, float f4) {
        return new ColorAttribute(Fog, f, f2, f3, f4);
    }

    public ColorAttribute(long j) {
        super(j);
        this.color = new Color();
        if (!is(j)) {
            throw new GdxRuntimeException("Invalid type specified");
        }
    }

    public ColorAttribute(long j, Color color) {
        this(j);
        if (color != null) {
            this.color.set(color);
        }
    }

    public ColorAttribute(long j, float f, float f2, float f3, float f4) {
        this(j);
        this.color.set(f, f2, f3, f4);
    }

    public ColorAttribute(ColorAttribute colorAttribute) {
        this(colorAttribute.type, colorAttribute.color);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new ColorAttribute(this);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public int hashCode() {
        return (super.hashCode() * 953) + this.color.toIntBits();
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        return this.type != attribute.type ? (int) (this.type - attribute.type) : ((ColorAttribute) attribute).color.toIntBits() - this.color.toIntBits();
    }
}
