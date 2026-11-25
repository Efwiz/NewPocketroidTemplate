package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.AbstractC0936Ds;
import com.android.tools.r8.internal.AbstractC2129h3;
import com.android.tools.r8.internal.AbstractC2462lQ;
import com.android.tools.r8.internal.C0970Eu;
import com.android.tools.r8.internal.C1283Px;
import com.android.tools.r8.internal.C1312Qz;
import com.android.tools.r8.internal.C1640an;
import com.android.tools.r8.internal.C1761cO;
import com.android.tools.r8.internal.C2772pL;
import com.android.tools.r8.internal.C3095ty;
import com.android.tools.r8.internal.C3247vy;
import com.android.tools.r8.internal.C3354xO;
import com.android.tools.r8.internal.C3394xu;
import com.android.tools.r8.internal.CO;
import com.android.tools.r8.internal.InterfaceC3115uE;
import com.android.tools.r8.internal.UA;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class KotlinClassMetadata {
    public static final int[] b;
    private final InterfaceC3115uE a;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class Class extends KotlinClassMetadata {
        private final UA classData$delegate;

        public Class(InterfaceC3115uE interfaceC3115uE) {
            super(interfaceC3115uE, 0);
            this.classData$delegate = com.android.tools.r8.jetbrains.kotlin.a.a(new q(interfaceC3115uE));
        }

        public final C1283Px b() {
            C1283Px c1283Px = new C1283Px();
            C2772pL c2772pL = (C2772pL) this.classData$delegate.getValue();
            AbstractC2462lQ.a((C1761cO) c2772pL.b(), c1283Px, (C0970Eu) c2772pL.a());
            return c1283Px;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class FileFacade extends KotlinClassMetadata {
        private final UA packageData$delegate;

        public FileFacade(InterfaceC3115uE interfaceC3115uE) {
            super(interfaceC3115uE, 0);
            this.packageData$delegate = com.android.tools.r8.jetbrains.kotlin.a.a(new t(interfaceC3115uE));
        }

        public final C3247vy b() {
            C3247vy c3247vy = new C3247vy();
            C2772pL c2772pL = (C2772pL) this.packageData$delegate.getValue();
            AbstractC2462lQ.a((CO) c2772pL.b(), c3247vy, (C0970Eu) c2772pL.a());
            return c3247vy;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class MultiFileClassPart extends KotlinClassMetadata {
        private final UA packageData$delegate;

        public MultiFileClassPart(InterfaceC3115uE interfaceC3115uE) {
            super(interfaceC3115uE, 0);
            this.packageData$delegate = com.android.tools.r8.jetbrains.kotlin.a.a(new v(interfaceC3115uE));
        }

        public final C3247vy b() {
            C3247vy c3247vy = new C3247vy();
            C2772pL c2772pL = (C2772pL) this.packageData$delegate.getValue();
            AbstractC2462lQ.a((CO) c2772pL.b(), c3247vy, (C0970Eu) c2772pL.a());
            return c3247vy;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class SyntheticClass extends KotlinClassMetadata {
        private final UA functionData$delegate;

        public SyntheticClass(InterfaceC3115uE interfaceC3115uE) {
            super(interfaceC3115uE, 0);
            this.functionData$delegate = com.android.tools.r8.jetbrains.kotlin.a.a(new x(interfaceC3115uE));
        }

        public final boolean b() {
            return !(a().d1().length == 0);
        }

        public final C3095ty c() {
            if (!b()) {
                return null;
            }
            C3095ty c3095ty = new C3095ty();
            if (!b()) {
                throw new IllegalStateException("accept(KmLambdaVisitor) is only possible for synthetic classes which are lambdas (isLambda = true)");
            }
            C2772pL c2772pL = (C2772pL) this.functionData$delegate.getValue();
            AbstractC0936Ds.a(c2772pL);
            AbstractC2462lQ.a((C3354xO) c2772pL.b(), c3095ty, (C0970Eu) c2772pL.a());
            return c3095ty;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class a extends KotlinClassMetadata {
        private final List c;

        public a(InterfaceC3115uE interfaceC3115uE) {
            super(interfaceC3115uE, 0);
            this.c = AbstractC2129h3.a(interfaceC3115uE.d1());
        }

        public final List<String> b() {
            return this.c;
        }
    }

    static {
        new r(0);
        int[] iArrC = C3394xu.g.c();
        int[] iArrCopyOf = Arrays.copyOf(iArrC, iArrC.length);
        AbstractC0936Ds.b(iArrCopyOf, "copyOf(this, size)");
        b = iArrCopyOf;
    }

    public /* synthetic */ KotlinClassMetadata(InterfaceC3115uE interfaceC3115uE, int i) {
        this(interfaceC3115uE);
    }

    public final InterfaceC3115uE a() {
        return this.a;
    }

    private KotlinClassMetadata(InterfaceC3115uE interfaceC3115uE) {
        this.a = interfaceC3115uE;
    }

    public static final KotlinClassMetadata a(C1312Qz c1312Qz) {
        KotlinClassMetadata fileFacade;
        if (!new C3394xu(c1312Qz.mv(), (c1312Qz.xi() & 8) != 0).d()) {
            return null;
        }
        try {
            int iK = c1312Qz.k();
            if (iK == 1) {
                fileFacade = new Class(c1312Qz);
            } else if (iK == 2) {
                fileFacade = new FileFacade(c1312Qz);
            } else if (iK == 3) {
                fileFacade = new SyntheticClass(c1312Qz);
            } else if (iK != 4) {
                fileFacade = iK != 5 ? new y(c1312Qz) : new MultiFileClassPart(c1312Qz);
            } else {
                fileFacade = new a(c1312Qz);
            }
            return fileFacade;
        } catch (C1640an e) {
            throw e;
        } catch (Throwable th) {
            throw new C1640an("Exception occurred when reading Kotlin metadata", th);
        }
    }
}
