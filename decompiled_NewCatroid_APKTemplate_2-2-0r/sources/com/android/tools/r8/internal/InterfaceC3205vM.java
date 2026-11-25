package com.android.tools.r8.internal;

import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC3205vM extends Predicate {
    boolean apply(Object obj);

    boolean equals(Object obj);

    @Override // java.util.function.Predicate
    default boolean test(Object obj) {
        return apply(obj);
    }
}
