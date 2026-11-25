package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3170uw extends AbstractC3244vw {
    static final /* synthetic */ boolean b = true;
    private final String a;

    public C3170uw(String str) {
        super();
        if (!b && str == null) {
            throw new AssertionError();
        }
        this.a = str;
    }

    @Override // com.android.tools.r8.internal.AbstractC3244vw
    public final C3170uw b() {
        return this;
    }

    public final String d() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C3170uw.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C3170uw) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a;
    }
}
