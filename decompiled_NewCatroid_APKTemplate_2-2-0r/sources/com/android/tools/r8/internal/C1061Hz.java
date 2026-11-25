package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import java.util.function.Consumer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Hz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1061Hz implements InterfaceC3680a0 {
    private static final C1061Hz c = new C1061Hz(JvmProtoBufUtil.PLATFORM_TYPE_ID, null);
    static final /* synthetic */ boolean d = true;
    private final String a;
    private final C2759pA b;

    private C1061Hz(String str, C2759pA c2759pA) {
        this.a = str;
        this.b = c2759pA;
        if (!d && !JvmProtoBufUtil.PLATFORM_TYPE_ID.equals(str)) {
            throw new AssertionError();
        }
    }

    static C1061Hz a(C2742oy c2742oy, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        return c2742oy == null ? c : new C1061Hz(c2742oy.b(), C2759pA.a(c0722p1, o2, c2742oy.a()));
    }

    final boolean a(final Consumer consumer, C0702m c0702m) {
        if (this == c) {
            return false;
        }
        C2759pA c2759pA = this.b;
        if (c2759pA == null) {
            if (d) {
                return false;
            }
            throw new AssertionError();
        }
        return c2759pA.a(new Consumer() { // from class: com.android.tools.r8.internal.Hz$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (C0942Dy) obj);
            }
        }, c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, C0942Dy c0942Dy) {
        consumer.accept(new C2742oy(c0942Dy, this.a));
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        if (this == c) {
            return;
        }
        C2759pA c2759pA = this.b;
        if (c2759pA == null) {
            if (!d) {
                throw new AssertionError();
            }
        } else {
            c2759pA.a(q0);
        }
    }
}
