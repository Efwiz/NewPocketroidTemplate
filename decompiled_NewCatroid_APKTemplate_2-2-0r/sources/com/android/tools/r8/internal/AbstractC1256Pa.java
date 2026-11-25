package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Pa, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1256Pa {
    protected final int a;
    protected AbstractC1256Pa b;

    protected AbstractC1256Pa(int i) {
        this(i, null);
    }

    public void a(int i, int i2, String str, String str2, String str3, String[] strArr) {
        if (this.a < 524288 && (65536 & i2) != 0) {
            throw new UnsupportedOperationException("Records requires ASM8");
        }
        AbstractC1256Pa abstractC1256Pa = this.b;
        if (abstractC1256Pa != null) {
            abstractC1256Pa.a(i, i2, str, str2, str3, strArr);
        }
    }

    public void b(String str) {
        if (this.a < 458752) {
            throw new UnsupportedOperationException("NestMember requires ASM7");
        }
        AbstractC1256Pa abstractC1256Pa = this.b;
        if (abstractC1256Pa != null) {
            abstractC1256Pa.b(str);
        }
    }

    public void c(String str) {
        if (this.a < 589824) {
            throw new UnsupportedOperationException("PermittedSubclasses requires ASM9");
        }
        AbstractC1256Pa abstractC1256Pa = this.b;
        if (abstractC1256Pa != null) {
            abstractC1256Pa.c(str);
        }
    }

    protected AbstractC1256Pa(int i, C3220va c3220va) {
        if (i != 589824 && i != 524288 && i != 458752 && i != 393216 && i != 327680 && i != 262144 && i != 17432576) {
            throw new IllegalArgumentException(AbstractC1857dg.a("Unsupported api ", i));
        }
        if (i == 17432576) {
            AbstractC1258Pc.a(this);
        }
        this.a = i;
        this.b = c3220va;
    }

    public void a(String str, String str2) {
        AbstractC1256Pa abstractC1256Pa = this.b;
        if (abstractC1256Pa != null) {
            abstractC1256Pa.a(str, str2);
        }
    }

    public CQ b(String str, String str2, String str3) {
        if (this.a >= 524288) {
            AbstractC1256Pa abstractC1256Pa = this.b;
            if (abstractC1256Pa != null) {
                return abstractC1256Pa.b(str, str2, str3);
            }
            return null;
        }
        throw new UnsupportedOperationException("Record requires ASM8");
    }

    public AbstractC3116uF a(int i, String str, String str2) {
        if (this.a >= 393216) {
            AbstractC1256Pa abstractC1256Pa = this.b;
            if (abstractC1256Pa != null) {
                return abstractC1256Pa.a(i, str, str2);
            }
            return null;
        }
        throw new UnsupportedOperationException("Module requires ASM6");
    }

    public void a(String str) {
        if (this.a >= 458752) {
            AbstractC1256Pa abstractC1256Pa = this.b;
            if (abstractC1256Pa != null) {
                abstractC1256Pa.a(str);
                return;
            }
            return;
        }
        throw new UnsupportedOperationException("NestHost requires ASM7");
    }

    public void a(String str, String str2, String str3) {
        AbstractC1256Pa abstractC1256Pa = this.b;
        if (abstractC1256Pa != null) {
            abstractC1256Pa.a(str, str2, str3);
        }
    }

    public AbstractC2509m2 a(String str, boolean z) {
        AbstractC1256Pa abstractC1256Pa = this.b;
        if (abstractC1256Pa != null) {
            return abstractC1256Pa.a(str, z);
        }
        return null;
    }

    public AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        if (this.a >= 327680) {
            AbstractC1256Pa abstractC1256Pa = this.b;
            if (abstractC1256Pa != null) {
                return abstractC1256Pa.a(i, c00, str, z);
            }
            return null;
        }
        throw new UnsupportedOperationException("TypeAnnotation requires ASM5");
    }

    public void a(T3 t3) {
        AbstractC1256Pa abstractC1256Pa = this.b;
        if (abstractC1256Pa != null) {
            abstractC1256Pa.a(t3);
        }
    }

    public void a(int i, String str, String str2, String str3) {
        AbstractC1256Pa abstractC1256Pa = this.b;
        if (abstractC1256Pa != null) {
            abstractC1256Pa.a(i, str, str2, str3);
        }
    }

    public AbstractC2252ii a(int i, String str, String str2, String str3, Object obj) {
        AbstractC1256Pa abstractC1256Pa = this.b;
        if (abstractC1256Pa != null) {
            return abstractC1256Pa.a(i, str, str2, str3, obj);
        }
        return null;
    }

    public AbstractC1899eF a(int i, String str, String str2, String str3, String[] strArr) {
        AbstractC1256Pa abstractC1256Pa = this.b;
        if (abstractC1256Pa != null) {
            return abstractC1256Pa.a(i, str, str2, str3, strArr);
        }
        return null;
    }

    public void a() {
        AbstractC1256Pa abstractC1256Pa = this.b;
        if (abstractC1256Pa != null) {
            abstractC1256Pa.a();
        }
    }
}
