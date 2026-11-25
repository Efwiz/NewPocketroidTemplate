package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.m2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2509m2 {
    protected AbstractC2509m2 a;

    protected AbstractC2509m2(int i) {
        this(i, null);
    }

    public void a(String str, Object obj) {
        AbstractC2509m2 abstractC2509m2 = this.a;
        if (abstractC2509m2 != null) {
            abstractC2509m2.a(str, obj);
        }
    }

    protected AbstractC2509m2(int i, AbstractC2509m2 abstractC2509m2) {
        if (i != 589824 && i != 524288 && i != 458752 && i != 393216 && i != 327680 && i != 262144 && i != 17432576) {
            throw new IllegalArgumentException(AbstractC1857dg.a("Unsupported api ", i));
        }
        if (i == 17432576) {
            AbstractC1258Pc.a(this);
        }
        this.a = abstractC2509m2;
    }

    public void a(String str, String str2, String str3) {
        AbstractC2509m2 abstractC2509m2 = this.a;
        if (abstractC2509m2 != null) {
            abstractC2509m2.a(str, str2, str3);
        }
    }

    public AbstractC2509m2 a(String str, String str2) {
        AbstractC2509m2 abstractC2509m2 = this.a;
        if (abstractC2509m2 != null) {
            return abstractC2509m2.a(str, str2);
        }
        return null;
    }

    public AbstractC2509m2 a(String str) {
        AbstractC2509m2 abstractC2509m2 = this.a;
        if (abstractC2509m2 != null) {
            return abstractC2509m2.a(str);
        }
        return null;
    }

    public void a() {
        AbstractC2509m2 abstractC2509m2 = this.a;
        if (abstractC2509m2 != null) {
            abstractC2509m2.a();
        }
    }
}
