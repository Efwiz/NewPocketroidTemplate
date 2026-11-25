package com.android.tools.r8.utils;

import com.android.tools.r8.internal.BY;
import com.android.tools.r8.internal.InterfaceC3442yY;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class Z2 {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(InterfaceC3949p3 interfaceC3949p3) {
        try {
            return interfaceC3949p3.get();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    public static <T, E extends Throwable> Supplier<T> b(final InterfaceC3949p3<T, E> interfaceC3949p3) {
        return BY.a(new InterfaceC3442yY() { // from class: com.android.tools.r8.utils.Z2$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return Z2.a(interfaceC3949p3);
            }
        });
    }
}
