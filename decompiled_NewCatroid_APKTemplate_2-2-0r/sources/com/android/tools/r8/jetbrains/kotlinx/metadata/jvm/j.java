package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.AbstractC0936Ds;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class j extends g {
    private final String a;
    private final String b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public j(String str, String str2) {
        super(0);
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(str2, "desc");
        this.a = str;
        this.b = str2;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.g
    public String a() {
        return c() + this.b;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.g
    public final String b() {
        return this.b;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.g
    public String c() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof j)) {
            return false;
        }
        j jVar = (j) obj;
        return AbstractC0936Ds.a((Object) this.a, (Object) jVar.a) && AbstractC0936Ds.a((Object) this.b, (Object) jVar.b);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }
}
