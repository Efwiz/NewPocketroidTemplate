package com.android.tools.r8.internal;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiPredicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.b2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1660b2 {
    static final /* synthetic */ boolean b = true;
    public static final /* synthetic */ int c = 0;
    private int a = -1;

    public static int a() {
        return 4;
    }

    public static int b() {
        return 18;
    }

    public static int c() {
        return 17;
    }

    abstract int a(int i, int i2, byte[] bArr, BiPredicate biPredicate);

    abstract boolean a(byte[] bArr, int i, int i2);

    abstract byte b(byte[] bArr, int i, int i2);

    abstract C1580a2 c(int i);

    abstract int d();

    public static boolean a(String str) {
        return "resources/new_api_database.ser".equals(str);
    }

    public static int b(int i) {
        return a() + (i * 6);
    }

    public static AbstractC1660b2 a(com.android.tools.r8.utils.A1 a1, DiagnosticsHandler diagnosticsHandler) {
        if (AbstractC1660b2.class.getClassLoader().getResource("resources/new_api_database.ser") == null) {
            diagnosticsHandler.warning(new StringDiagnostic("Could not find the api database at resources/new_api_database.ser"));
            return new Z1();
        }
        a1.a().getClass();
        try {
            InputStream resourceAsStream = AbstractC1660b2.class.getClassLoader().getResourceAsStream("resources/new_api_database.ser");
            try {
                if (resourceAsStream == null) {
                    diagnosticsHandler.warning(new StringDiagnostic("Could not open the api database at resources/new_api_database.ser"));
                    Z1 z1 = new Z1();
                    if (resourceAsStream != null) {
                        resourceAsStream.close();
                    }
                    return z1;
                }
                Y1 y1 = new Y1(V5.a(resourceAsStream));
                resourceAsStream.close();
                return y1;
            } catch (Throwable th) {
                if (resourceAsStream != null) {
                    try {
                        resourceAsStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            diagnosticsHandler.warning(new ExceptionDiagnostic(e));
            return new Z1();
        }
    }

    public final int b(C0759u2 c0759u2) {
        int iA = a(c0759u2);
        if (this.a == -1) {
            this.a = d();
        }
        C1580a2 c1580a2C = c((iA * 6) + b(this.a));
        c1580a2C.getClass();
        if (c1580a2C == C1580a2.c) {
            return -1;
        }
        int i = c1580a2C.a;
        int i2 = c1580a2C.b;
        if (i < 0) {
            if (!b && i >= 0) {
                throw new AssertionError();
            }
            int i3 = C1589a6.b;
            int i4 = Integer.MAX_VALUE & i;
            if (a(i4, c0759u2.e)) {
                return i4;
            }
            return -1;
        }
        if (!b && i2 <= 0) {
            throw new AssertionError();
        }
        if (this.a == -1) {
            this.a = d();
        }
        int i5 = this.a;
        return a(a(i5) + ((1 << b()) * 6) + i, i2, c0759u2.e, new BiPredicate() { // from class: com.android.tools.r8.internal.b2$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return this.f$0.a(((Integer) obj).intValue(), (byte[]) obj2);
            }
        });
    }

    public static int a(AbstractC0744s2 abstractC0744s2) {
        int iB = 1 << (b() - 1);
        return (abstractC0744s2.hashCode() % iB) + iB;
    }

    public static int a(C0759u2 c0759u2) {
        int iC = 1 << (c() - 1);
        return (c0759u2.hashCode() % iC) + iC;
    }

    public static int a(int i) {
        return b(i) + ((1 << c()) * 6);
    }

    public final boolean a(int i, byte[] bArr) {
        C1580a2 c1580a2C = c((i * 6) + a());
        c1580a2C.getClass();
        if (c1580a2C == C1580a2.c || bArr.length != c1580a2C.b) {
            return false;
        }
        if (this.a == -1) {
            this.a = d();
        }
        int i2 = this.a;
        return a(bArr, a(i2) + ((1 << b()) * 6) + c1580a2C.a, c1580a2C.b);
    }

    public final byte a(byte[] bArr, AbstractC0744s2 abstractC0744s2) {
        int iA = a(abstractC0744s2);
        if (this.a == -1) {
            this.a = d();
        }
        C1580a2 c1580a2C = c((iA * 6) + a(this.a));
        c1580a2C.getClass();
        if (c1580a2C == C1580a2.c) {
            return (byte) 0;
        }
        if (this.a == -1) {
            this.a = d();
        }
        int i = this.a;
        return b(bArr, a(i) + ((1 << b()) * 6) + c1580a2C.a, c1580a2C.b);
    }
}
