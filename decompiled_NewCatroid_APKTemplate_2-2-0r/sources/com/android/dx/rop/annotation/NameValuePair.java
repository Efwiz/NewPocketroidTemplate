package com.android.dx.rop.annotation;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstString;

/* JADX INFO: loaded from: classes7.dex */
public final class NameValuePair implements Comparable<NameValuePair> {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final CstString f75name;
    private final Constant value;

    public NameValuePair(CstString cstString, Constant constant) {
        if (cstString == null) {
            throw new NullPointerException("name == null");
        }
        if (constant == null) {
            throw new NullPointerException("value == null");
        }
        this.f75name = cstString;
        this.value = constant;
    }

    public String toString() {
        return this.f75name.toHuman() + ":" + this.value;
    }

    public int hashCode() {
        return (this.f75name.hashCode() * 31) + this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NameValuePair)) {
            return false;
        }
        NameValuePair nameValuePair = (NameValuePair) obj;
        return this.f75name.equals(nameValuePair.f75name) && this.value.equals(nameValuePair.value);
    }

    @Override // java.lang.Comparable
    public int compareTo(NameValuePair nameValuePair) {
        int iCompareTo = this.f75name.compareTo((Constant) nameValuePair.f75name);
        return iCompareTo != 0 ? iCompareTo : this.value.compareTo(nameValuePair.value);
    }

    public CstString getName() {
        return this.f75name;
    }

    public Constant getValue() {
        return this.value;
    }
}
