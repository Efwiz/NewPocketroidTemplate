package com.android.dx.rop.cst;

import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.Type;

/* JADX INFO: loaded from: classes7.dex */
public class CstCallSiteRef extends Constant {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final int f77id;
    private final CstInvokeDynamic invokeDynamic;

    @Override // com.android.dx.rop.cst.Constant
    public boolean isCategory2() {
        return false;
    }

    CstCallSiteRef(CstInvokeDynamic cstInvokeDynamic, int i) {
        if (cstInvokeDynamic == null) {
            throw new NullPointerException("invokeDynamic == null");
        }
        this.invokeDynamic = cstInvokeDynamic;
        this.f77id = i;
    }

    @Override // com.android.dx.rop.cst.Constant
    public String typeName() {
        return "CallSiteRef";
    }

    @Override // com.android.dx.rop.cst.Constant
    protected int compareTo0(Constant constant) {
        CstCallSiteRef cstCallSiteRef = (CstCallSiteRef) constant;
        int iCompareTo = this.invokeDynamic.compareTo((Constant) cstCallSiteRef.invokeDynamic);
        return iCompareTo != 0 ? iCompareTo : Integer.compare(this.f77id, cstCallSiteRef.f77id);
    }

    @Override // com.android.dx.util.ToHuman
    public String toHuman() {
        return getCallSite().toHuman();
    }

    public String toString() {
        return getCallSite().toString();
    }

    public Prototype getPrototype() {
        return this.invokeDynamic.getPrototype();
    }

    public Type getReturnType() {
        return this.invokeDynamic.getReturnType();
    }

    public CstCallSite getCallSite() {
        return this.invokeDynamic.getCallSite();
    }
}
