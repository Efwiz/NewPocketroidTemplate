package com.android.tools.r8.graph;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.graph.o2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0717o2 implements com.android.tools.r8.utils.structural.x {
    public static final /* synthetic */ int a = 0;

    AbstractC0717o2() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.o2$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC0717o2) obj).a();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.o2$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC0717o2) obj).b();
            }
        });
    }

    public abstract C0759u2 a();

    public abstract C0737r2 b();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractC0717o2)) {
            return false;
        }
        AbstractC0717o2 abstractC0717o2 = (AbstractC0717o2) obj;
        return a() == abstractC0717o2.a() && b() == abstractC0717o2.b();
    }

    public final int hashCode() {
        return Objects.hash(a(), b());
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.o2$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                AbstractC0717o2.a(zVar);
            }
        };
    }

    public final String toString() {
        StringBuilder sbAppend = new StringBuilder().append(b().v0().L0()).append(" ").append(a()).append("(");
        for (int i = 0; i < b().t0(); i++) {
            if (i != 0) {
                sbAppend.append(", ");
            }
            sbAppend.append(b().k(i).L0());
        }
        return sbAppend.append(Constant.AFTER_QUTO).toString();
    }

    public final AbstractC0717o2 a(C0759u2 c0759u2) {
        return new C0711n2(b(), c0759u2);
    }

    public final AbstractC0717o2 a(C0800x2 c0800x2, C0722p1 c0722p1) {
        return new C0711n2(c0722p1.a(b().v0(), c0800x2), a());
    }
}
