package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.function.ToIntFunction;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.d20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum EnumC1812d20 implements com.android.tools.r8.utils.structural.x<EnumC1812d20> {
    OBJECT,
    INT,
    FLOAT,
    LONG,
    DOUBLE;

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.x
    /* JADX INFO: renamed from: a */
    public final /* bridge */ /* synthetic */ int compareTo(com.android.tools.r8.utils.structural.x xVar) {
        return compareTo((Enum) xVar);
    }

    public final boolean b() {
        return this == LONG || this == DOUBLE;
    }

    public final int c() {
        return b() ? 2 : 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.x, com.android.tools.r8.utils.structural.s
    public final /* bridge */ /* synthetic */ int compareTo(com.android.tools.r8.utils.structural.s sVar) {
        return compareTo((Enum) sVar);
    }

    public final XM d() {
        int i = AbstractC1735c20.c[ordinal()];
        if (i == 2) {
            return AbstractC3472z00.k();
        }
        if (i == 3) {
            return AbstractC3472z00.j();
        }
        if (i == 4) {
            return AbstractC3472z00.l();
        }
        if (i == 5) {
            return AbstractC3472z00.i();
        }
        throw new C3405y10("Unexpected type in conversion to primitive: " + this);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.d20$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                EnumC1812d20.a(zVar);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.internal.d20$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((EnumC1812d20) obj).ordinal();
            }
        });
    }

    public final boolean a() {
        return this == OBJECT;
    }

    public static EnumC1812d20 a(EnumC2523mE enumC2523mE) {
        switch (enumC2523mE.ordinal()) {
            case 0:
                return OBJECT;
            case 1:
            case 2:
            case 3:
            case 4:
                return INT;
            case 5:
                return FLOAT;
            case 6:
                return LONG;
            case 7:
                return DOUBLE;
            case 8:
            case 9:
                throw new C3405y10("Unexpected imprecise type: " + enumC2523mE);
            default:
                throw new C3405y10("Unexpected member type: " + enumC2523mE);
        }
    }

    public static EnumC1812d20 a(char c) {
        if (c != 'F') {
            if (c != 'L') {
                if (c != 'S') {
                    if (c == 'V') {
                        throw new C3089ts("No value type for void type.");
                    }
                    if (c != 'I') {
                        if (c == 'J') {
                            return LONG;
                        }
                        if (c != 'Z') {
                            if (c != '[') {
                                switch (c) {
                                    case 'B':
                                    case 'C':
                                        break;
                                    case 'D':
                                        return DOUBLE;
                                    default:
                                        throw new C3405y10("Invalid descriptor char '" + c + FormatHelper.QUOTE);
                                }
                            }
                        }
                    }
                }
                return INT;
            }
            return OBJECT;
        }
        return FLOAT;
    }

    public static EnumC1812d20 a(C0767v2 c0767v2) {
        return a((char) c0767v2.e.e[0]);
    }

    public static EnumC1812d20 a(EnumC2527mI enumC2527mI) {
        switch (AbstractC1735c20.b[enumC2527mI.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                return INT;
            case 5:
                return FLOAT;
            case 6:
                return LONG;
            case 7:
                return DOUBLE;
            default:
                throw new C3405y10("Invalid numeric type '" + enumC2527mI + FormatHelper.QUOTE);
        }
    }

    public static EnumC1812d20 a(AbstractC3472z00 abstractC3472z00) {
        if (abstractC3472z00.I()) {
            return OBJECT;
        }
        if (abstractC3472z00.C()) {
            return INT;
        }
        if (abstractC3472z00.B()) {
            return FLOAT;
        }
        if (abstractC3472z00.D()) {
            return LONG;
        }
        if (abstractC3472z00.z()) {
            return DOUBLE;
        }
        throw new C3405y10("Unexpected conversion of imprecise type: " + abstractC3472z00);
    }

    public final C0767v2 a(C0722p1 c0722p1) {
        int i = AbstractC1735c20.c[ordinal()];
        if (i == 1) {
            return c0722p1.Z1;
        }
        if (i == 2) {
            return c0722p1.A1;
        }
        if (i == 3) {
            return c0722p1.z1;
        }
        if (i == 4) {
            return c0722p1.B1;
        }
        if (i != 5) {
            throw new C3405y10();
        }
        return c0722p1.y1;
    }
}
