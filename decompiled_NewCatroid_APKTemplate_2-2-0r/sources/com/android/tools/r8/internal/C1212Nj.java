package com.android.tools.r8.internal;

import io.netty.util.internal.StringUtil;
import java.lang.reflect.GenericSignatureFormatError;
import java.nio.CharBuffer;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1212Nj<T> {
    static final /* synthetic */ boolean g = true;
    private InterfaceC1184Mj a;
    private char b;
    private String c;
    private boolean d;
    private char[] e;
    private int f;

    public C1212Nj(InterfaceC1184Mj<T> interfaceC1184Mj) {
        this.a = interfaceC1184Mj;
    }

    private void d() {
        b();
        if (!g && this.c == null) {
            throw new AssertionError();
        }
        this.a.a(this.c);
        this.a.a(this.b);
        a(':');
        char c = this.b;
        if (c == 'L' || c == '[' || c == 'T') {
            b(InterfaceC1184Mj.a.c);
        }
        while (true) {
            char c2 = this.b;
            if (c2 != ':') {
                return;
            }
            this.a.a(c2);
            c();
            b(InterfaceC1184Mj.a.c);
        }
    }

    public void a(String str) {
        char c;
        try {
            this.a.b();
            this.e = str.toCharArray();
            this.d = false;
            this.f = 0;
            this.b = (char) 0;
            this.c = null;
            c();
            char c2 = this.b;
            if (c2 == '<') {
                this.a.a(c2);
                c();
                d();
                while (true) {
                    c = this.b;
                    if (c == '>' || c <= 0) {
                        break;
                    } else {
                        d();
                    }
                }
                this.a.a(c);
                a('>');
            }
            a(InterfaceC1184Mj.a.a);
            while (this.b > 0) {
                a(InterfaceC1184Mj.a.a);
            }
            this.a.a();
        } catch (GenericSignatureFormatError e) {
            throw e;
        } catch (Throwable th) {
            GenericSignatureFormatError genericSignatureFormatError = new GenericSignatureFormatError("Unknown error parsing class signature: " + th.getMessage());
            genericSignatureFormatError.addSuppressed(th);
            throw genericSignatureFormatError;
        }
    }

    public void b(String str) {
        try {
            this.a.b();
            this.e = str.toCharArray();
            this.d = false;
            this.f = 0;
            this.b = (char) 0;
            this.c = null;
            c();
            b(InterfaceC1184Mj.a.c);
            this.a.a();
        } catch (GenericSignatureFormatError e) {
            throw e;
        } catch (Throwable th) {
            GenericSignatureFormatError genericSignatureFormatError = new GenericSignatureFormatError("Unknown error parsing field signature: " + th.getMessage());
            genericSignatureFormatError.addSuppressed(th);
            throw genericSignatureFormatError;
        }
    }

    public void c(String str) {
        try {
            this.a.b();
            this.e = str.toCharArray();
            this.d = false;
            this.f = 0;
            this.b = (char) 0;
            this.c = null;
            c();
            a();
            this.a.a();
        } catch (GenericSignatureFormatError e) {
            throw e;
        } catch (Throwable th) {
            GenericSignatureFormatError genericSignatureFormatError = new GenericSignatureFormatError("Unknown error parsing method signature: " + th.getMessage());
            genericSignatureFormatError.addSuppressed(th);
            throw genericSignatureFormatError;
        }
    }

    private void b(InterfaceC1184Mj.a aVar) {
        char c = this.b;
        if (c == 'L') {
            a(aVar);
            return;
        }
        if (c == 'T') {
            this.a.a(c);
            a(Util.C_TYPE_VARIABLE);
            b();
            if (!g && this.c == null) {
                throw new AssertionError();
            }
            this.a.a(this.c);
            this.a.a(this.b);
            a(';');
            return;
        }
        if (c == '[') {
            this.a.a(c);
            c();
            char c2 = this.b;
            if (c2 != 'F' && c2 != 'S' && c2 != 'Z' && c2 != 'I' && c2 != 'J') {
                switch (c2) {
                    case 'B':
                    case 'C':
                    case 'D':
                        break;
                    default:
                        b(aVar);
                        return;
                }
            }
            this.a.a(c2);
            c();
            return;
        }
        a(this.f, "Expected L, [ or T");
        throw null;
    }

    private void c() {
        if (!this.d) {
            if (!g && this.e == null) {
                throw new AssertionError();
            }
            int i = this.f;
            char[] cArr = this.e;
            if (i < cArr.length) {
                this.b = cArr[i];
                this.f = i + 1;
                return;
            } else {
                this.b = (char) 0;
                this.d = true;
                return;
            }
        }
        a(this.f, "Unexpected end of signature");
        throw null;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0080  */
    private void a() {
        char c;
        char c2;
        char c3 = this.b;
        if (c3 == '<') {
            this.a.a(c3);
            c();
            d();
            while (true) {
                c2 = this.b;
                if (c2 == '>' || c2 <= 0) {
                    break;
                } else {
                    d();
                }
            }
            this.a.a(c2);
            a('>');
        }
        this.a.a(this.b);
        a('(');
        while (true) {
            c = this.b;
            if (c != ')' && c > 0) {
                InterfaceC1184Mj.a aVar = InterfaceC1184Mj.a.c;
                if (c != 'F' && c != 'S' && c != 'Z' && c != 'I' && c != 'J') {
                    switch (c) {
                        case 'B':
                        case 'C':
                        case 'D':
                            break;
                        default:
                            b(aVar);
                            continue;
                    }
                }
                this.a.a(c);
                c();
            }
        }
        this.a.a(c);
        a(')');
        char c4 = this.b;
        if (c4 != 'V') {
            InterfaceC1184Mj.a aVar2 = InterfaceC1184Mj.a.c;
            if (c4 == 'F' || c4 == 'S' || c4 == 'Z' || c4 == 'I' || c4 == 'J') {
                this.a.a(c4);
                c();
            } else {
                switch (c4) {
                    case 'B':
                    case 'C':
                    case 'D':
                        this.a.a(c4);
                        c();
                        break;
                    default:
                        b(aVar2);
                        break;
                }
            }
        } else {
            this.a.a(c4);
            c();
        }
        if (this.b == '^') {
            do {
                this.a.a(this.b);
                c();
                char c5 = this.b;
                if (c5 == 'T') {
                    this.a.a(c5);
                    a(Util.C_TYPE_VARIABLE);
                    b();
                    if (!g && this.c == null) {
                        throw new AssertionError();
                    }
                    this.a.a(this.c);
                    this.a.a(this.b);
                    a(';');
                } else {
                    a(InterfaceC1184Mj.a.c);
                }
            } while (this.b == '^');
        }
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
    private void b() {
        /*
            r8 = this;
            boolean r0 = r8.d
            r1 = 0
            if (r0 != 0) goto L7b
            int r0 = r8.f
            char[] r2 = r8.e
            int r2 = r2.length
            if (r0 >= r2) goto L7b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r2 = 32
            r0.<init>(r2)
            char r2 = r8.b
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
            char[] r1 = r8.e
            boolean r2 = com.android.tools.r8.internal.C1212Nj.g
            if (r2 != 0) goto L34
            if (r1 == 0) goto L2e
            goto L34
        L2e:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L34:
            int r2 = r8.f
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
            int r2 = r8.f
            int r2 = r2 + r5
            r8.f = r2
            int r7 = r1.length
            if (r2 != r7) goto L34
            java.lang.String r0 = r0.toString()
            r8.c = r0
            r8.b = r3
            r8.d = r5
            return
        L65:
            java.lang.String r0 = r0.toString()
            r8.c = r0
            r8.c()
            return
        L6f:
            r8.b = r3
            r8.d = r5
            int r0 = r8.f
            java.lang.String r2 = "Unexpected"
            r8.a(r0, r2)
            throw r1
        L7b:
            int r0 = r8.f
            java.lang.String r2 = "Unexpected end of signature"
            r8.a(r0, r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C1212Nj.b():void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void a(InterfaceC1184Mj.a aVar) {
        char c;
        char c2;
        this.a.a(this.b);
        a('L');
        StringBuilder sb = new StringBuilder();
        b();
        if (!g && this.c == null) {
            throw new AssertionError();
        }
        while (this.b == '/') {
            sb.append(this.c).append(this.b);
            c();
            b();
            if (!g && this.c == null) {
                throw new AssertionError();
            }
        }
        sb.append(this.c);
        Object objA = this.a.a(sb.toString(), aVar);
        char c3 = this.b;
        if (c3 == '<') {
            this.a.a(c3);
            c();
            char c4 = this.b;
            if (c4 == '*') {
                this.a.a(c4);
                c();
            } else if (c4 == '+' || c4 == '-') {
                this.a.a(c4);
                c();
                b(InterfaceC1184Mj.a.b);
            } else {
                b(InterfaceC1184Mj.a.b);
            }
            while (true) {
                c2 = this.b;
                if (c2 == '>' || c2 <= 0) {
                    break;
                }
                if (c2 == '*') {
                    this.a.a(c2);
                    c();
                } else if (c2 == '+') {
                    this.a.a(c2);
                    c();
                    b(InterfaceC1184Mj.a.b);
                } else if (c2 == '-') {
                    this.a.a(c2);
                    c();
                    b(InterfaceC1184Mj.a.b);
                } else {
                    b(InterfaceC1184Mj.a.b);
                }
            }
            this.a.a(c2);
            a('>');
        }
        while (true) {
            char c5 = this.b;
            if (c5 == '.') {
                this.a.a(c5);
                c();
                b();
                if (!g && this.c == null) {
                    throw new AssertionError();
                }
                objA = this.a.a(objA, this.c);
                char c6 = this.b;
                if (c6 == '<') {
                    this.a.a(c6);
                    c();
                    char c7 = this.b;
                    if (c7 == '*') {
                        this.a.a(c7);
                        c();
                    } else if (c7 == '+' || c7 == '-') {
                        this.a.a(c7);
                        c();
                        b(InterfaceC1184Mj.a.b);
                    } else {
                        b(InterfaceC1184Mj.a.b);
                    }
                    while (true) {
                        c = this.b;
                        if (c == '>' || c <= 0) {
                            break;
                        }
                        if (c == '*') {
                            this.a.a(c);
                            c();
                        } else if (c == '+') {
                            this.a.a(c);
                            c();
                            b(InterfaceC1184Mj.a.b);
                        } else if (c == '-') {
                            this.a.a(c);
                            c();
                            b(InterfaceC1184Mj.a.b);
                        } else {
                            b(InterfaceC1184Mj.a.b);
                        }
                    }
                    this.a.a(c);
                    a('>');
                }
            } else {
                this.a.a(c5);
                a(';');
                return;
            }
        }
    }

    private void a(char c) {
        if (!this.d) {
            if (this.b == c) {
                c();
                return;
            } else {
                a(this.f - 1, "Expected " + c);
                throw null;
            }
        }
        a(this.f, "Unexpected end of signature");
        throw null;
    }

    private void a(int i, String str) {
        throw new GenericSignatureFormatError(str + " at position " + (i + 1) + "\n" + String.valueOf(this.e) + "\n" + (CharBuffer.allocate(i).toString().replace((char) 0, StringUtil.SPACE) + "^"));
    }
}
