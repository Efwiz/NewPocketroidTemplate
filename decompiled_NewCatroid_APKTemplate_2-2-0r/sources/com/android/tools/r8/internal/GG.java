package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface GG<T> extends Iterator<T> {
    default <S extends T> S a(Predicate<T> predicate) {
        while (hasNext()) {
            T next = next();
            if (predicate.test(next)) {
                return next;
            }
        }
        return null;
    }
}
