package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3116uF {
    protected AbstractC3116uF a;

    protected AbstractC3116uF() {
        this(589824, null);
    }

    public void a(String str) {
        AbstractC3116uF abstractC3116uF = this.a;
        if (abstractC3116uF != null) {
            abstractC3116uF.a(str);
        }
    }

    public void b(String str) {
        AbstractC3116uF abstractC3116uF = this.a;
        if (abstractC3116uF != null) {
            abstractC3116uF.b(str);
        }
    }

    public void c(String str) {
        AbstractC3116uF abstractC3116uF = this.a;
        if (abstractC3116uF != null) {
            abstractC3116uF.c(str);
        }
    }

    protected AbstractC3116uF(int i, AbstractC3116uF abstractC3116uF) {
        if (i != 589824 && i != 524288 && i != 458752 && i != 393216 && i != 327680 && i != 262144 && i != 17432576) {
            throw new IllegalArgumentException(AbstractC1857dg.a("Unsupported api ", i));
        }
        if (i == 17432576) {
            AbstractC1258Pc.a(this);
        }
        this.a = abstractC3116uF;
    }

    public void a(int i, String str, String str2) {
        AbstractC3116uF abstractC3116uF = this.a;
        if (abstractC3116uF != null) {
            abstractC3116uF.a(i, str, str2);
        }
    }

    public void b(String str, int i, String... strArr) {
        AbstractC3116uF abstractC3116uF = this.a;
        if (abstractC3116uF != null) {
            abstractC3116uF.b(str, i, strArr);
        }
    }

    public void a(String str, int i, String... strArr) {
        AbstractC3116uF abstractC3116uF = this.a;
        if (abstractC3116uF != null) {
            abstractC3116uF.a(str, i, strArr);
        }
    }

    public void a(String str, String... strArr) {
        AbstractC3116uF abstractC3116uF = this.a;
        if (abstractC3116uF != null) {
            abstractC3116uF.a(str, strArr);
        }
    }

    public void a() {
        AbstractC3116uF abstractC3116uF = this.a;
        if (abstractC3116uF != null) {
            abstractC3116uF.a();
        }
    }
}
