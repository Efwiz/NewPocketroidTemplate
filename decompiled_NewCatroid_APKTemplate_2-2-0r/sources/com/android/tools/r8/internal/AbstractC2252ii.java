package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ii, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2252ii {
    protected final int a;
    protected AbstractC2252ii b;

    protected AbstractC2252ii(int i) {
        this(i, null);
    }

    public AbstractC2509m2 a(String str, boolean z) {
        AbstractC2252ii abstractC2252ii = this.b;
        if (abstractC2252ii != null) {
            return abstractC2252ii.a(str, z);
        }
        return null;
    }

    protected AbstractC2252ii(int i, AbstractC2252ii abstractC2252ii) {
        if (i != 589824 && i != 524288 && i != 458752 && i != 393216 && i != 327680 && i != 262144 && i != 17432576) {
            throw new IllegalArgumentException(AbstractC1857dg.a("Unsupported api ", i));
        }
        if (i == 17432576) {
            AbstractC1258Pc.a(this);
        }
        this.a = i;
        this.b = abstractC2252ii;
    }

    public AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        if (this.a >= 327680) {
            AbstractC2252ii abstractC2252ii = this.b;
            if (abstractC2252ii != null) {
                return abstractC2252ii.a(i, c00, str, z);
            }
            return null;
        }
        throw new UnsupportedOperationException("This feature requires ASM5");
    }

    public void a(T3 t3) {
        AbstractC2252ii abstractC2252ii = this.b;
        if (abstractC2252ii != null) {
            abstractC2252ii.a(t3);
        }
    }

    public void a() {
        AbstractC2252ii abstractC2252ii = this.b;
        if (abstractC2252ii != null) {
            abstractC2252ii.a();
        }
    }
}
