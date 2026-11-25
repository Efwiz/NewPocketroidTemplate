package com.android.tools.r8.internal;

import java.util.LinkedHashMap;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BB extends CN {
    BB() {
        super(new Supplier() { // from class: com.android.tools.r8.internal.BB$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return BB.f();
            }
        }, f());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LinkedHashMap f() {
        return new LinkedHashMap();
    }

    private static LinkedHashMap l(int i) {
        return new LinkedHashMap(i);
    }

    BB(int i) {
        super(new Supplier() { // from class: com.android.tools.r8.internal.BB$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return BB.f();
            }
        }, l(i));
    }
}
