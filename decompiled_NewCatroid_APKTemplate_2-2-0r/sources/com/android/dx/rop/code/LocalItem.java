package com.android.dx.rop.code;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstString;

/* JADX INFO: loaded from: classes7.dex */
public class LocalItem implements Comparable<LocalItem> {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final CstString f76name;
    private final CstString signature;

    public static LocalItem make(CstString cstString, CstString cstString2) {
        if (cstString == null && cstString2 == null) {
            return null;
        }
        return new LocalItem(cstString, cstString2);
    }

    private LocalItem(CstString cstString, CstString cstString2) {
        this.f76name = cstString;
        this.signature = cstString2;
    }

    public boolean equals(Object obj) {
        return (obj instanceof LocalItem) && compareTo((LocalItem) obj) == 0;
    }

    private static int compareHandlesNulls(CstString cstString, CstString cstString2) {
        if (cstString == cstString2) {
            return 0;
        }
        if (cstString == null) {
            return -1;
        }
        if (cstString2 == null) {
            return 1;
        }
        return cstString.compareTo((Constant) cstString2);
    }

    @Override // java.lang.Comparable
    public int compareTo(LocalItem localItem) {
        int iCompareHandlesNulls = compareHandlesNulls(this.f76name, localItem.f76name);
        return iCompareHandlesNulls != 0 ? iCompareHandlesNulls : compareHandlesNulls(this.signature, localItem.signature);
    }

    public int hashCode() {
        CstString cstString = this.f76name;
        int iHashCode = (cstString == null ? 0 : cstString.hashCode()) * 31;
        CstString cstString2 = this.signature;
        return iHashCode + (cstString2 != null ? cstString2.hashCode() : 0);
    }

    public String toString() {
        CstString cstString = this.f76name;
        if (cstString != null && this.signature == null) {
            return cstString.toQuoted();
        }
        String quoted = "";
        if (cstString == null && this.signature == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder("[");
        CstString cstString2 = this.f76name;
        StringBuilder sbAppend = sb.append(cstString2 == null ? "" : cstString2.toQuoted()).append("|");
        CstString cstString3 = this.signature;
        if (cstString3 != null) {
            quoted = cstString3.toQuoted();
        }
        return sbAppend.append(quoted).toString();
    }

    public CstString getName() {
        return this.f76name;
    }

    public CstString getSignature() {
        return this.signature;
    }
}
