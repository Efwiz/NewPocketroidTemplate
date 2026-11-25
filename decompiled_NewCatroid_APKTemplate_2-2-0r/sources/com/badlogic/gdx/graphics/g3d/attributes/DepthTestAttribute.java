package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.NumberUtils;
import org.eclipse.jdt.core.compiler.IProblem;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/attributes/DepthTestAttribute.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class DepthTestAttribute extends Attribute {
    public static final String Alias = "depthStencil";
    protected static long Mask;
    public static final long Type;
    public int depthFunc;
    public boolean depthMask;
    public float depthRangeFar;
    public float depthRangeNear;

    static {
        long jRegister = register(Alias);
        Type = jRegister;
        Mask = jRegister;
    }

    public static final boolean is(long j) {
        return (j & Mask) != 0;
    }

    public DepthTestAttribute() {
        this(515);
    }

    public DepthTestAttribute(boolean z) {
        this(515, z);
    }

    public DepthTestAttribute(int i) {
        this(i, true);
    }

    public DepthTestAttribute(int i, boolean z) {
        this(i, 0.0f, 1.0f, z);
    }

    public DepthTestAttribute(int i, float f, float f2) {
        this(i, f, f2, true);
    }

    public DepthTestAttribute(int i, float f, float f2, boolean z) {
        this(Type, i, f, f2, z);
    }

    public DepthTestAttribute(long j, int i, float f, float f2, boolean z) {
        super(j);
        if (!is(j)) {
            throw new GdxRuntimeException("Invalid type specified");
        }
        this.depthFunc = i;
        this.depthRangeNear = f;
        this.depthRangeFar = f2;
        this.depthMask = z;
    }

    public DepthTestAttribute(DepthTestAttribute depthTestAttribute) {
        this(depthTestAttribute.type, depthTestAttribute.depthFunc, depthTestAttribute.depthRangeNear, depthTestAttribute.depthRangeFar, depthTestAttribute.depthMask);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new DepthTestAttribute(this);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public int hashCode() {
        return (((((((super.hashCode() * IProblem.ImplicitObjectBoundNoNullDefault) + this.depthFunc) * IProblem.ImplicitObjectBoundNoNullDefault) + NumberUtils.floatToRawIntBits(this.depthRangeNear)) * IProblem.ImplicitObjectBoundNoNullDefault) + NumberUtils.floatToRawIntBits(this.depthRangeFar)) * IProblem.ImplicitObjectBoundNoNullDefault) + (this.depthMask ? 1 : 0);
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        if (this.type != attribute.type) {
            return (int) (this.type - attribute.type);
        }
        DepthTestAttribute depthTestAttribute = (DepthTestAttribute) attribute;
        int i = this.depthFunc;
        int i2 = depthTestAttribute.depthFunc;
        if (i != i2) {
            return i - i2;
        }
        boolean z = this.depthMask;
        if (z != depthTestAttribute.depthMask) {
            return z ? -1 : 1;
        }
        if (!MathUtils.isEqual(this.depthRangeNear, depthTestAttribute.depthRangeNear)) {
            return this.depthRangeNear < depthTestAttribute.depthRangeNear ? -1 : 1;
        }
        if (MathUtils.isEqual(this.depthRangeFar, depthTestAttribute.depthRangeFar)) {
            return 0;
        }
        return this.depthRangeFar < depthTestAttribute.depthRangeFar ? -1 : 1;
    }
}
