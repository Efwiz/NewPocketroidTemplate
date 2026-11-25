package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class MK {
    private static InterfaceC1665b4 a(final Function function, final String str) {
        return new InterfaceC1665b4() { // from class: com.android.tools.r8.internal.MK$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC1665b4
            public final B7 a(I7 i7, C0722p1 c0722p1) {
                return MK.a(function, str, i7, c0722p1);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ B7 a(Function function, String str, I7 i7, C0722p1 c0722p1) {
        return new I7(182, c0722p1.a((C0767v2) function.apply(c0722p1), i7.T().h, str), false);
    }

    public static InterfaceC1665b4 b() {
        return a(new Function() { // from class: com.android.tools.r8.internal.MK$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0722p1) obj).X2;
            }
        }, "getAsInt");
    }

    public static InterfaceC1665b4 c() {
        return a(new Function() { // from class: com.android.tools.r8.internal.MK$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0722p1) obj).Y2;
            }
        }, "getAsLong");
    }

    public static InterfaceC1665b4 d() {
        return a(new Function() { // from class: com.android.tools.r8.internal.MK$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0722p1) obj).V2;
            }
        }, "get");
    }

    public static InterfaceC1665b4 a() {
        return a(new Function() { // from class: com.android.tools.r8.internal.MK$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0722p1) obj).W2;
            }
        }, "getAsDouble");
    }
}
