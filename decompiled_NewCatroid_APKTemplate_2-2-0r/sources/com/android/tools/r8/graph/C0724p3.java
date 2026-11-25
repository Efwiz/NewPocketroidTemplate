package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.utils.C3970u0;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.GenericSignatureFormatError;
import java.nio.CharBuffer;
import java.util.List;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: renamed from: com.android.tools.r8.graph.p3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0724p3 {
    static final /* synthetic */ boolean g = true;
    private char a;
    private String b;
    private boolean c;
    private char[] d;
    private int e;
    private final C0722p1 f;

    private C0724p3(C0722p1 c0722p1) {
        this.f = c0722p1;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0031  */
    /* JADX WARN: Code duplicated, block: B:25:0x0065  */
    private C0706m3.g d() {
        C0706m3.h hVar;
        C0706m3.i iVarC;
        Object objC;
        AbstractC1639am abstractC1639amE = e();
        a('(');
        C1507Xl c1507XlG = AbstractC1639am.g();
        while (true) {
            char c = this.a;
            if (c != ')' && c > 0) {
                if (c != 'F' && c != 'S' && c != 'Z' && c != 'I' && c != 'J') {
                    switch (c) {
                        case 'B':
                        case 'C':
                        case 'D':
                            C0712n3 c0712n3 = new C0712n3(this.f.e(String.valueOf(c)));
                            g();
                            objC = c0712n3;
                            break;
                        default:
                            objC = c();
                            break;
                    }
                } else {
                    C0712n3 c0712n4 = new C0712n3(this.f.e(String.valueOf(c)));
                    g();
                    objC = c0712n4;
                }
                c1507XlG.a(objC);
            }
        }
        a(')');
        char c2 = this.a;
        if (c2 != 'V') {
            if (c2 != 'F' && c2 != 'S' && c2 != 'Z' && c2 != 'I' && c2 != 'J') {
                switch (c2) {
                    case 'B':
                    case 'C':
                    case 'D':
                        C0712n3 c0712n5 = new C0712n3(this.f.e(String.valueOf(c2)));
                        g();
                        iVarC = c0712n5;
                        break;
                    default:
                        iVarC = c();
                        break;
                }
            } else {
                C0712n3 c0712n6 = new C0712n3(this.f.e(String.valueOf(c2)));
                g();
                iVarC = c0712n6;
            }
            hVar = new C0706m3.h(iVarC);
        } else {
            g();
            hVar = C0706m3.h.b;
        }
        C1507Xl c1507XlG2 = AbstractC1639am.g();
        if (this.a == '^') {
            do {
                g();
                if (this.a == 'T') {
                    a(Util.C_TYPE_VARIABLE);
                    f();
                    if (!g && this.b == null) {
                        throw new AssertionError();
                    }
                    a(';');
                    c1507XlG2.a(new C0706m3.j(this.b, C0706m3.k.a));
                } else {
                    c1507XlG2.a(b());
                }
            } while (this.a == '^');
        }
        return new C0706m3.g(abstractC1639amE, c1507XlG.a(), hVar, c1507XlG2.a());
    }

    private AbstractC1639am e() {
        if (this.a != '<') {
            return (AbstractC1639am) C0706m3.a;
        }
        g();
        C1507Xl c1507XlG = AbstractC1639am.g();
        while (true) {
            char c = this.a;
            if (c == '>' || c <= 0) {
                break;
            }
            f();
            if (!g && this.b == null) {
                throw new AssertionError();
            }
            String str = this.b;
            a(':');
            C0706m3.e eVarC = C0706m3.e;
            char c2 = this.a;
            if (c2 == 'L' || c2 == '[' || c2 == 'T') {
                eVarC = c();
            }
            C1507Xl c1507XlG2 = null;
            while (this.a == ':') {
                if (c1507XlG2 == null) {
                    c1507XlG2 = AbstractC1639am.g();
                }
                g();
                c1507XlG2.a(c());
            }
            c1507XlG.a(c1507XlG2 == null ? new C0706m3.f(str, eVarC, C0706m3.b) : new C0706m3.f(str, eVarC, c1507XlG2.a()));
        }
        a('>');
        return c1507XlG.a();
    }

    /*  JADX ERROR: UnsupportedOperationException in pass: RegionMakerVisitor
        java.lang.UnsupportedOperationException
        	at java.base/java.util.Collections$UnmodifiableCollection.add(Collections.java:1093)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker$1.leaveRegion(SwitchRegionMaker.java:419)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:91)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:31)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaksForCase(SwitchRegionMaker.java:399)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaks(SwitchRegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.leaveRegion(PostProcessRegions.java:31)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:91)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.process(PostProcessRegions.java:21)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:31)
        */
    private void f() {
        /*
            r8 = this;
            boolean r0 = r8.c
            r1 = 0
            if (r0 != 0) goto L7b
            int r0 = r8.e
            char[] r2 = r8.d
            int r2 = r2.length
            if (r0 >= r2) goto L7b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r2 = 32
            r0.<init>(r2)
            char r2 = r8.a
            r3 = 0
            r4 = 46
            r5 = 1
            if (r2 == r4) goto L6f
            r6 = 47
            if (r2 == r6) goto L6f
            switch(r2) {
                case 58: goto L6f;
                case 59: goto L6f;
                case 60: goto L6f;
                default: goto L22;
            }
        L22:
            r0.append(r2)
            char[] r1 = r8.d
            boolean r2 = com.android.tools.r8.graph.C0724p3.g
            if (r2 != 0) goto L34
            if (r1 == 0) goto L2e
            goto L34
        L2e:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L34:
            int r2 = r8.e
            char r2 = r1[r2]
            r7 = 97
            if (r2 < r7) goto L40
            r7 = 122(0x7a, float:1.71E-43)
            if (r2 <= r7) goto L4f
        L40:
            r7 = 65
            if (r2 < r7) goto L48
            r7 = 90
            if (r2 <= r7) goto L4f
        L48:
            if (r2 == r4) goto L65
            if (r2 == r6) goto L65
            switch(r2) {
                case 58: goto L65;
                case 59: goto L65;
                case 60: goto L65;
                default: goto L4f;
            }
        L4f:
            r0.append(r2)
            int r2 = r8.e
            int r2 = r2 + r5
            r8.e = r2
            int r7 = r1.length
            if (r2 != r7) goto L34
            java.lang.String r0 = r0.toString()
            r8.b = r0
            r8.a = r3
            r8.c = r5
            return
        L65:
            java.lang.String r0 = r0.toString()
            r8.b = r0
            r8.g()
            return
        L6f:
            r8.a = r3
            r8.c = r5
            int r0 = r8.e
            java.lang.String r2 = "Unexpected"
            r8.a(r0, r2)
            throw r1
        L7b:
            int r0 = r8.e
            java.lang.String r2 = "Unexpected end of signature"
            r8.a(r0, r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.graph.C0724p3.f():void");
    }

    private void g() {
        if (this.c) {
            a(this.e, "Unexpected end of signature");
            throw null;
        }
        if (!g && this.d == null) {
            throw new AssertionError();
        }
        int i = this.e;
        char[] cArr = this.d;
        if (i < cArr.length) {
            this.a = cArr[i];
            this.e = i + 1;
        } else {
            this.a = (char) 0;
            this.c = true;
        }
    }

    final C0706m3.b a(String str) {
        try {
            this.d = str.toCharArray();
            this.c = false;
            this.e = 0;
            this.a = (char) 0;
            this.b = null;
            g();
            return a();
        } catch (GenericSignatureFormatError e) {
            throw e;
        } catch (Throwable th) {
            GenericSignatureFormatError genericSignatureFormatError = new GenericSignatureFormatError("Unknown error parsing class signature: " + th.getMessage());
            genericSignatureFormatError.addSuppressed(th);
            throw genericSignatureFormatError;
        }
    }

    final C0706m3.e b(String str) {
        try {
            this.d = str.toCharArray();
            this.c = false;
            this.e = 0;
            this.a = (char) 0;
            this.b = null;
            g();
            return c();
        } catch (GenericSignatureFormatError e) {
            throw e;
        } catch (Throwable th) {
            GenericSignatureFormatError genericSignatureFormatError = new GenericSignatureFormatError("Unknown error parsing field signature: " + th.getMessage());
            genericSignatureFormatError.addSuppressed(th);
            throw genericSignatureFormatError;
        }
    }

    final C0706m3.g c(String str) {
        try {
            this.d = str.toCharArray();
            this.c = false;
            this.e = 0;
            this.a = (char) 0;
            this.b = null;
            g();
            return d();
        } catch (GenericSignatureFormatError e) {
            throw e;
        } catch (Throwable th) {
            GenericSignatureFormatError genericSignatureFormatError = new GenericSignatureFormatError("Unknown error parsing method signature: " + th.getMessage());
            genericSignatureFormatError.addSuppressed(th);
            throw genericSignatureFormatError;
        }
    }

    private C0767v2 a(String str, C0767v2 c0767v2) {
        if (c0767v2 == null) {
            return null;
        }
        if (!g && !c0767v2.O0()) {
            throw new AssertionError();
        }
        return this.f.e(C3970u0.l(C3970u0.i(c0767v2.b1()) + "$" + str));
    }

    private C0706m3.c b() {
        C0706m3.e eVarA;
        C0706m3.e eVarA2;
        C0706m3.e eVarA3;
        C0706m3.e eVarA4;
        a('L');
        StringBuilder sb = new StringBuilder();
        f();
        if (!g && this.b == null) {
            throw new AssertionError();
        }
        while (this.a == '/') {
            sb.append(this.b).append(this.a);
            g();
            f();
            if (!g && this.b == null) {
                throw new AssertionError();
            }
        }
        sb.append(this.b);
        C0767v2 c0767v2E = this.f.e(C3970u0.l(sb.toString()));
        C1507Xl c1507XlG = AbstractC1639am.g();
        if (this.a == '<') {
            g();
            char c = this.a;
            if (c == '*') {
                g();
                eVarA3 = C0733q3.b;
            } else if (c == '+') {
                g();
                eVarA3 = c().a(C0706m3.k.d);
            } else if (c == '-') {
                g();
                eVarA3 = c().a(C0706m3.k.c);
            } else {
                eVarA3 = c().a(C0706m3.k.b);
            }
            c1507XlG.a(eVarA3);
            while (true) {
                char c2 = this.a;
                if (c2 == '>' || c2 <= 0) {
                    break;
                }
                if (c2 == '*') {
                    g();
                    eVarA4 = C0733q3.b;
                } else if (c2 == '+') {
                    g();
                    eVarA4 = c().a(C0706m3.k.d);
                } else if (c2 == '-') {
                    g();
                    eVarA4 = c().a(C0706m3.k.c);
                } else {
                    eVarA4 = c().a(C0706m3.k.b);
                }
                c1507XlG.a(eVarA4);
            }
            a('>');
        }
        List listA = c1507XlG.a();
        if (listA.isEmpty()) {
            listA = C0706m3.b;
        }
        C0706m3.c cVar = new C0706m3.c(c0767v2E, listA);
        C0706m3.c cVar2 = null;
        C0706m3.c cVar3 = cVar;
        while (this.a == '.') {
            g();
            f();
            if (!g && this.b == null) {
                throw new AssertionError();
            }
            c0767v2E = a(this.b, c0767v2E);
            C1507Xl c1507XlG2 = AbstractC1639am.g();
            if (this.a == '<') {
                g();
                char c3 = this.a;
                if (c3 == '*') {
                    g();
                    eVarA = C0733q3.b;
                } else if (c3 == '+') {
                    g();
                    eVarA = c().a(C0706m3.k.d);
                } else if (c3 == '-') {
                    g();
                    eVarA = c().a(C0706m3.k.c);
                } else {
                    eVarA = c().a(C0706m3.k.b);
                }
                c1507XlG2.a(eVarA);
                while (true) {
                    char c4 = this.a;
                    if (c4 == '>' || c4 <= 0) {
                        break;
                    }
                    if (c4 == '*') {
                        g();
                        eVarA2 = C0733q3.b;
                    } else if (c4 == '+') {
                        g();
                        eVarA2 = c().a(C0706m3.k.d);
                    } else if (c4 == '-') {
                        g();
                        eVarA2 = c().a(C0706m3.k.c);
                    } else {
                        eVarA2 = c().a(C0706m3.k.b);
                    }
                    c1507XlG2.a(eVarA2);
                }
                a('>');
            }
            List listA2 = c1507XlG2.a();
            if (listA2.isEmpty()) {
                listA2 = C0706m3.b;
            }
            cVar2 = new C0706m3.c(c0767v2E, listA2, cVar3, C0706m3.k.a);
            cVar3 = cVar2;
        }
        a(';');
        return cVar2 != null ? cVar2 : cVar3;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0030  */
    private C0706m3.e c() {
        C0706m3.i iVarC;
        char c = this.a;
        if (c == 'L') {
            return b();
        }
        if (c == 'T') {
            a(Util.C_TYPE_VARIABLE);
            f();
            if (!g && this.b == null) {
                throw new AssertionError();
            }
            a(';');
            return new C0706m3.j(this.b, C0706m3.k.a);
        }
        if (c == '[') {
            g();
            char c2 = this.a;
            if (c2 == 'F' || c2 == 'S' || c2 == 'Z' || c2 == 'I' || c2 == 'J') {
                C0712n3 c0712n3 = new C0712n3(this.f.e(String.valueOf(c2)));
                g();
                iVarC = c0712n3;
            } else {
                switch (c2) {
                    case 'B':
                    case 'C':
                    case 'D':
                        C0712n3 c0712n4 = new C0712n3(this.f.e(String.valueOf(c2)));
                        g();
                        iVarC = c0712n4;
                        break;
                    default:
                        iVarC = c();
                        break;
                }
            }
            return iVarC.f().d();
        }
        a(this.e, "Expected L, [ or T");
        throw null;
    }

    private C0706m3.b a() {
        AbstractC1639am abstractC1639amE = e();
        C0706m3.c cVarB = b();
        C1507Xl c1507XlG = AbstractC1639am.g();
        while (this.a > 0) {
            c1507XlG.a(b());
        }
        return new C0706m3.b(abstractC1639amE, cVarB, c1507XlG.a());
    }

    private void a(char c) {
        if (!this.c) {
            if (this.a == c) {
                g();
                return;
            } else {
                a(this.e - 1, "Expected " + c);
                throw null;
            }
        }
        a(this.e, "Unexpected end of signature");
        throw null;
    }

    private void a(int i, String str) {
        String str2 = CharBuffer.allocate(i).toString().replace((char) 0, StringUtil.SPACE) + "^";
        throw new GenericSignatureFormatError(str + " at position " + (i + 1) + System.lineSeparator() + String.valueOf(this.d) + System.lineSeparator() + str2);
    }
}
