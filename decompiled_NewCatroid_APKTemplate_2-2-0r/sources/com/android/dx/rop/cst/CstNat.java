package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;
import net.bytebuddy.description.method.MethodDescription;

/* JADX INFO: loaded from: classes7.dex */
public final class CstNat extends Constant {
    public static final CstNat PRIMITIVE_TYPE_NAT = new CstNat(new CstString("TYPE"), new CstString("Ljava/lang/Class;"));
    private final CstString descriptor;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final CstString f78name;

    @Override // com.android.dx.rop.cst.Constant
    public boolean isCategory2() {
        return false;
    }

    public CstNat(CstString cstString, CstString cstString2) {
        if (cstString == null) {
            throw new NullPointerException("name == null");
        }
        if (cstString2 == null) {
            throw new NullPointerException("descriptor == null");
        }
        this.f78name = cstString;
        this.descriptor = cstString2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CstNat)) {
            return false;
        }
        CstNat cstNat = (CstNat) obj;
        return this.f78name.equals(cstNat.f78name) && this.descriptor.equals(cstNat.descriptor);
    }

    public int hashCode() {
        return (this.f78name.hashCode() * 31) ^ this.descriptor.hashCode();
    }

    @Override // com.android.dx.rop.cst.Constant
    protected int compareTo0(Constant constant) {
        CstNat cstNat = (CstNat) constant;
        int iCompareTo = this.f78name.compareTo((Constant) cstNat.f78name);
        return iCompareTo != 0 ? iCompareTo : this.descriptor.compareTo((Constant) cstNat.descriptor);
    }

    public String toString() {
        return "nat{" + toHuman() + '}';
    }

    @Override // com.android.dx.rop.cst.Constant
    public String typeName() {
        return "nat";
    }

    public CstString getName() {
        return this.f78name;
    }

    public CstString getDescriptor() {
        return this.descriptor;
    }

    @Override // com.android.dx.util.ToHuman
    public String toHuman() {
        return this.f78name.toHuman() + ':' + this.descriptor.toHuman();
    }

    public Type getFieldType() {
        return Type.intern(this.descriptor.getString());
    }

    public final boolean isInstanceInit() {
        return this.f78name.getString().equals("<init>");
    }

    public final boolean isClassInit() {
        return this.f78name.getString().equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME);
    }
}
