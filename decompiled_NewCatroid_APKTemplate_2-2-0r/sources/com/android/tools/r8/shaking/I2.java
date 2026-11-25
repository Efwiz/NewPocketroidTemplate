package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.internal.C3405y10;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class I2 {
    public static final I2 a;
    public static final I2 b;
    public static final I2 c;
    public static final I2 d;
    public static final I2 e;
    private static final /* synthetic */ I2[] f;

    /* JADX WARN: Multi-variable type inference failed */
    static {
        C2 c2 = new C2();
        a = c2;
        D2 d2 = new D2();
        b = d2;
        E2 e2 = new E2();
        c = e2;
        F2 f2 = new F2();
        d = f2;
        G2 g2 = new G2();
        e = g2;
        f = new I2[]{c2, d2, e2, f2, g2};
    }

    private I2(String str, int i) {
        super(str, i);
    }

    public abstract boolean a(AbstractC0727q0 abstractC0727q0);

    @Override // java.lang.Enum
    public final String toString() {
        int i = H2.a[ordinal()];
        if (i == 1) {
            return "@interface";
        }
        if (i == 2) {
            return "class";
        }
        if (i == 3) {
            return PsiKeyword.ENUM;
        }
        if (i == 4) {
            return PsiKeyword.INTERFACE;
        }
        if (i == 5) {
            return "";
        }
        throw new C3405y10("Invalid proguard class type '" + this + FormatHelper.QUOTE);
    }
}
