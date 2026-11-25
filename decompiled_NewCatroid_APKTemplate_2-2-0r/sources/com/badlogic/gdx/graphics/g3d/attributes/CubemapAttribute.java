package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gaurav.avnc.vnc.XKeySym;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/attributes/CubemapAttribute.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class CubemapAttribute extends Attribute {
    public static final long EnvironmentMap;
    public static final String EnvironmentMapAlias = "environmentCubemap";
    protected static long Mask;
    public final TextureDescriptor<Cubemap> textureDescription;

    static {
        long jRegister = register(EnvironmentMapAlias);
        EnvironmentMap = jRegister;
        Mask = jRegister;
    }

    public static final boolean is(long j) {
        return (j & Mask) != 0;
    }

    public CubemapAttribute(long j) {
        super(j);
        if (!is(j)) {
            throw new GdxRuntimeException("Invalid type specified");
        }
        this.textureDescription = new TextureDescriptor<>();
    }

    public <T extends Cubemap> CubemapAttribute(long j, TextureDescriptor<T> textureDescriptor) {
        this(j);
        this.textureDescription.set(textureDescriptor);
    }

    public CubemapAttribute(long j, Cubemap cubemap) {
        this(j);
        this.textureDescription.texture = cubemap;
    }

    public CubemapAttribute(CubemapAttribute cubemapAttribute) {
        this(cubemapAttribute.type, cubemapAttribute.textureDescription);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new CubemapAttribute(this);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public int hashCode() {
        return (super.hashCode() * XKeySym.XK_Iogonek) + this.textureDescription.hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        return this.type != attribute.type ? (int) (this.type - attribute.type) : this.textureDescription.compareTo((TextureDescriptor) ((CubemapAttribute) attribute).textureDescription);
    }
}
