package com.android.tools.r8.utils;

import com.android.tools.r8.internal.C3405y10;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.utils.f, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public enum EnumC3891f implements com.android.tools.r8.utils.structural.s<EnumC3891f> {
    B(1),
    B_1_1(2),
    C(3),
    D(4),
    E(5),
    E_0_1(6),
    E_MR1(7),
    F(8),
    G(9),
    G_MR1(10),
    H(11),
    H_MR1(12),
    H_MR2(13),
    I(14),
    I_MR1(15),
    J(16),
    J_MR1(17),
    J_MR2(18),
    K(19),
    K_WATCH(20),
    L(21),
    L_MR1(22),
    M(23),
    N(24),
    N_MR1(25),
    O(26),
    O_MR1(27),
    P(28),
    Q(29),
    R(30),
    S(31),
    Sv2(32),
    T(33),
    U(34),
    MASTER(35),
    ANDROID_PLATFORM(10000);

    public static final EnumC3891f L;
    public static final EnumC3891f M;
    static final /* synthetic */ boolean O = true;
    private final int a;

    EnumC3891f(int i) {
        this.a = i;
    }

    public static List<EnumC3891f> a() {
        return Arrays.asList(values());
    }

    public static EnumC3891f b() {
        return B;
    }

    public EnumC3891f c(EnumC3891f enumC3891f) {
        return (EnumC3891f) com.android.tools.r8.utils.structural.s.a(this, enumC3891f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.s
    public final /* bridge */ /* synthetic */ int compareTo(com.android.tools.r8.utils.structural.s sVar) {
        return compareTo((Enum) sVar);
    }

    public int d() {
        return this.a;
    }

    public final String e() {
        return "Android " + name();
    }

    public EnumC3891f g() {
        return c(d() + 1);
    }

    static {
        EnumC3891f enumC3891f = U;
        L = enumC3891f;
        M = enumC3891f;
    }

    public static EnumC3891f a(H0 h0) {
        switch (AbstractC3886e.a[h0.ordinal()]) {
            case 1:
                return B;
            case 2:
                return N;
            case 3:
                return O;
            case 4:
                return P;
            case 5:
                return R;
            case 6:
                return ANDROID_PLATFORM;
            default:
                throw new C3405y10();
        }
    }

    public H0 c() {
        return H0.c(this);
    }

    public static EnumC3891f c(int i) {
        boolean z = O;
        if (!z && i <= 0) {
            throw new AssertionError();
        }
        if (!z && U != L) {
            throw new AssertionError();
        }
        if (!z && !ANDROID_PLATFORM.d(L)) {
            throw new AssertionError();
        }
        if (i != 10000) {
            switch (i) {
                case 1:
                    return B;
                case 2:
                    return B_1_1;
                case 3:
                    return C;
                case 4:
                    return D;
                case 5:
                    return E;
                case 6:
                    return E_0_1;
                case 7:
                    return E_MR1;
                case 8:
                    return F;
                case 9:
                    return G;
                case 10:
                    return G_MR1;
                case 11:
                    return H;
                case 12:
                    return H_MR1;
                case 13:
                    return H_MR2;
                case 14:
                    return I;
                case 15:
                    return I_MR1;
                case 16:
                    return J;
                case 17:
                    return J_MR1;
                case 18:
                    return J_MR2;
                case 19:
                    return K;
                case 20:
                    return K_WATCH;
                case 21:
                    return L;
                case 22:
                    return L_MR1;
                case 23:
                    return M;
                case 24:
                    return N;
                case 25:
                    return N_MR1;
                case 26:
                    return O;
                case 27:
                    return O_MR1;
                case 28:
                    return P;
                case 29:
                    return Q;
                case 30:
                    return R;
                case 31:
                    return S;
                case 32:
                    return Sv2;
                case 33:
                    return T;
                case 34:
                    return U;
                case 35:
                    return MASTER;
                default:
                    return L;
            }
        }
        return ANDROID_PLATFORM;
    }
}
