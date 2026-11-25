package com.android.tools.r8.internal;

import io.netty.util.internal.StringUtil;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2314jZ extends AbstractC1899eF {
    public final ZM j;

    public C2314jZ(ZY zy) {
        super(589824, null);
        this.j = zy;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(String str, boolean z) {
        return new C2243iZ(super.a(str, z), this.j.a(str, z));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, String str) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("// parameter ");
        if ((i & 1) != 0) {
            zy.b.append("public ");
        }
        if ((i & 2) != 0) {
            zy.b.append("private ");
        }
        if ((i & 4) != 0) {
            zy.b.append("protected ");
        }
        if ((i & 16) != 0) {
            zy.b.append("final ");
        }
        if ((i & 8) != 0) {
            zy.b.append("static ");
        }
        if ((i & 32) != 0) {
            zy.b.append("synchronized ");
        }
        if ((i & 64) != 0) {
            zy.b.append("volatile ");
        }
        if ((i & 128) != 0) {
            zy.b.append("transient ");
        }
        if ((i & 1024) != 0) {
            zy.b.append("abstract ");
        }
        if ((i & 2048) != 0) {
            zy.b.append("strictfp ");
        }
        if ((i & 4096) != 0) {
            zy.b.append("synthetic ");
        }
        if ((32768 & i) != 0) {
            zy.b.append("mandated ");
        }
        if ((i & 16384) != 0) {
            zy.b.append("enum ");
        }
        zy.b.append(StringUtil.SPACE).append(str == null ? "<no name>" : str).append('\n');
        zy.c.add(zy.b.toString());
        super.b(i, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 c(int i, C00 c00, String str, boolean z) {
        return new C2243iZ(super.c(i, c00, str, z), ((ZY) this.j).b(i, c00, str, z));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void d(int i, int i2) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append(ZM.d[i]).append(StringUtil.SPACE).append(i2).append('\n');
        zy.c.add(zy.b.toString());
        super.d(i, i2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(T3 t3) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.f).append("ATTRIBUTE ");
        zy.a(-1, t3.a);
        zy.b.append(" : unknown\n");
        zy.c.add(zy.b.toString());
        super.a(t3);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c(int i, String str) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append(ZM.d[i]).append(StringUtil.SPACE);
        zy.a(0, str);
        zy.b.append('\n');
        zy.c.add(zy.b.toString());
        super.c(i, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c(int i, int i2) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("MAXSTACK = ").append(i).append('\n');
        zy.c.add(zy.b.toString());
        zy.b.setLength(0);
        zy.b.append(zy.g).append("MAXLOCALS = ").append(i2).append('\n');
        zy.c.add(zy.b.toString());
        super.c(i, i2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a() {
        return new C2243iZ(super.a(), this.j.a());
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, boolean z) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("// annotable parameter count: ");
        zy.b.append(i);
        zy.b.append(z ? " (visible)\n" : " (invisible)\n");
        zy.c.add(zy.b.toString());
        super.a(i, z);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c() {
        this.j.getClass();
        super.c();
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, String str, boolean z) {
        return new C2243iZ(super.a(i, str, z), this.j.a(i, str, z));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        this.j.a(i, i2, objArr, i3, objArr2);
        super.a(i, i2, objArr, i3, objArr2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append(ZM.d[i]).append('\n');
        zy.c.add(zy.b.toString());
        super.a(i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str, String str2, String str3) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append(ZM.d[i]).append(StringUtil.SPACE);
        zy.a(0, str);
        zy.b.append('.').append(str2).append(" : ");
        zy.a(1, str3);
        zy.b.append('\n');
        zy.c.add(zy.b.toString());
        super.a(i, str, str2, str3);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b() {
        this.j.getClass();
        super.b();
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, int i2) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append(ZM.d[i]).append(StringUtil.SPACE).append(i == 188 ? ZM.e[i2] : Integer.toString(i2)).append('\n');
        zy.c.add(zy.b.toString());
        super.b(i, i2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str, String str2, String str3, boolean z) {
        ZM zm = this.j;
        if (zm.a < 327680) {
            if (z == (i == 185)) {
                zm.getClass();
                zm.a(i, str, str2, str3, i == 185);
            } else {
                throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM5");
            }
        } else {
            zm.a(i, str, str2, str3, z);
        }
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(i, str, str2, str3, z);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 b(int i, C00 c00, String str, boolean z) {
        return new C2243iZ(super.b(i, c00, str, z), this.j.a(i, c00, str, z));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, C3263wA c3263wA) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("LINENUMBER ").append(i).append(StringUtil.SPACE);
        zy.a(c3263wA);
        zy.b.append('\n');
        zy.c.add(zy.b.toString());
        super.b(i, c3263wA);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(String str, String str2, C1944ek c1944ek, Object... objArr) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("INVOKEDYNAMIC").append(StringUtil.SPACE);
        zy.b.append(str);
        zy.a(3, str2);
        zy.b.append(" [");
        zy.b.append('\n');
        zy.b.append(zy.h);
        zy.a(c1944ek);
        zy.b.append('\n');
        zy.b.append(zy.h).append("// arguments:");
        if (objArr.length == 0) {
            zy.b.append(" none");
        } else {
            zy.b.append('\n');
            for (Object obj : objArr) {
                zy.b.append(zy.h);
                if (obj instanceof String) {
                    ZM.a(zy.b, (String) obj);
                } else if (obj instanceof CZ) {
                    CZ cz = (CZ) obj;
                    if (cz.e() == 11) {
                        zy.a(3, cz.b());
                    } else {
                        zy.b.append(cz.a()).append(".class");
                    }
                } else if (obj instanceof C1944ek) {
                    zy.a((C1944ek) obj);
                } else {
                    zy.b.append(obj);
                }
                zy.b.append(", \n");
            }
            StringBuilder sb = zy.b;
            sb.setLength(sb.length() - 3);
        }
        zy.b.append('\n');
        zy.b.append(zy.g).append("]\n");
        zy.c.add(zy.b.toString());
        super.a(str, str2, c1944ek, objArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, C3263wA c3263wA) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append(ZM.d[i]).append(StringUtil.SPACE);
        zy.a(c3263wA);
        zy.b.append('\n');
        zy.c.add(zy.b.toString());
        super.a(i, c3263wA);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.i);
        zy.a(c3263wA);
        zy.b.append('\n');
        zy.c.add(zy.b.toString());
        super.a(c3263wA);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(Object obj) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("LDC ");
        if (obj instanceof String) {
            ZM.a(zy.b, (String) obj);
        } else if (obj instanceof CZ) {
            zy.b.append(((CZ) obj).b()).append(".class");
        } else {
            zy.b.append(obj);
        }
        zy.b.append('\n');
        zy.c.add(zy.b.toString());
        super.a(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("IINC ").append(i).append(StringUtil.SPACE).append(i2).append('\n');
        zy.c.add(zy.b.toString());
        super.a(i, i2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2, C3263wA c3263wA, C3263wA... c3263wAArr) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("TABLESWITCH\n");
        for (int i3 = 0; i3 < c3263wAArr.length; i3++) {
            zy.b.append(zy.h).append(i + i3).append(": ");
            zy.a(c3263wAArr[i3]);
            zy.b.append('\n');
        }
        zy.b.append(zy.h).append("default: ");
        zy.a(c3263wA);
        zy.b.append('\n');
        zy.c.add(zy.b.toString());
        super.a(i, i2, c3263wA, c3263wAArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA, int[] iArr, C3263wA[] c3263wAArr) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("LOOKUPSWITCH\n");
        for (int i = 0; i < c3263wAArr.length; i++) {
            zy.b.append(zy.h).append(iArr[i]).append(": ");
            zy.a(c3263wAArr[i]);
            zy.b.append('\n');
        }
        zy.b.append(zy.h).append("default: ");
        zy.a(c3263wA);
        zy.b.append('\n');
        zy.c.add(zy.b.toString());
        super.a(c3263wA, iArr, c3263wAArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("MULTIANEWARRAY ");
        zy.a(1, str);
        zy.b.append(StringUtil.SPACE).append(i).append('\n');
        zy.c.add(zy.b.toString());
        super.a(i, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        return new C2243iZ(super.a(i, c00, str, z), ((ZY) this.j).b(i, c00, str, z));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA, C3263wA c3263wA2, C3263wA c3263wA3, String str) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("TRYCATCHBLOCK ");
        zy.a(c3263wA);
        zy.b.append(StringUtil.SPACE);
        zy.a(c3263wA2);
        zy.b.append(StringUtil.SPACE);
        zy.a(c3263wA3);
        zy.b.append(StringUtil.SPACE);
        zy.a(0, str);
        zy.b.append('\n');
        zy.c.add(zy.b.toString());
        super.a(c3263wA, c3263wA2, c3263wA3, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(String str, String str2, String str3, C3263wA c3263wA, C3263wA c3263wA2, int i) {
        ZY zy = (ZY) this.j;
        zy.b.setLength(0);
        zy.b.append(zy.g).append("LOCALVARIABLE ").append(str).append(StringUtil.SPACE);
        zy.a(1, str2);
        zy.b.append(StringUtil.SPACE);
        zy.a(c3263wA);
        zy.b.append(StringUtil.SPACE);
        zy.a(c3263wA2);
        zy.b.append(StringUtil.SPACE).append(i).append('\n');
        if (str3 != null) {
            zy.b.append(zy.g);
            zy.a(2, str3);
            zy.b.append(zy.g);
            C2545mZ c2545mZ = new C2545mZ();
            new XU(str3).a(c2545mZ);
            zy.b.append("// declaration: ");
            if (c2545mZ.d() != null) {
                zy.b.append(c2545mZ.d());
                zy.b.append(StringUtil.SPACE);
            }
            zy.b.append(str);
            zy.b.append(c2545mZ.b());
            if (c2545mZ.c() != null) {
                zy.b.append(" throws ").append(c2545mZ.c());
            }
            zy.b.append('\n');
        }
        zy.c.add(zy.b.toString());
        super.a(str, str2, str3, c3263wA, c3263wA2, i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, C00 c00, C3263wA[] c3263wAArr, C3263wA[] c3263wAArr2, int[] iArr, String str, boolean z) {
        return new C2243iZ(super.a(i, c00, c3263wAArr, c3263wAArr2, iArr, str, z), this.j.a(i, c00, c3263wAArr, c3263wAArr2, iArr, str, z));
    }
}
