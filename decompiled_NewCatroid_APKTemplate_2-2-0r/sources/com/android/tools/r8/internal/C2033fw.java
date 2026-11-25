package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2033fw extends AbstractC2188hw {
    public C2033fw() {
        super();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Consumer consumer, Collection collection) {
        C1571Zw c1571ZwA = C1651ax.a();
        c1571ZwA.b.addAll(collection);
        consumer.accept(c1571ZwA.a());
    }

    @Override // com.android.tools.r8.internal.AbstractC1116Jv
    final String a() {
        return "options";
    }

    @Override // com.android.tools.r8.internal.AbstractC2188hw
    final AbstractC2509m2 a(final Consumer consumer, String str) {
        if (str.equals("disallow")) {
            return new C1544Yv("KeepTarget", new InterfaceC2118gw() { // from class: com.android.tools.r8.internal.fw$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.internal.InterfaceC2118gw
                public final void accept(Object obj) {
                    C2033fw.a(consumer, (Collection) obj);
                }
            });
        }
        if (str.equals("allow")) {
            return new C1544Yv("KeepTarget", new InterfaceC2118gw() { // from class: com.android.tools.r8.internal.fw$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.internal.InterfaceC2118gw
                public final void accept(Object obj) {
                    C2033fw.b(consumer, (Collection) obj);
                }
            });
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Consumer consumer, Collection collection) {
        C1571Zw c1571ZwB = C1651ax.b();
        c1571ZwB.b.addAll(collection);
        consumer.accept(c1571ZwB.a());
    }
}
