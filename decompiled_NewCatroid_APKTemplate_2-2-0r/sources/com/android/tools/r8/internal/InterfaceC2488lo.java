package com.android.tools.r8.internal;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lo, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC2488lo extends Iterator<AbstractC2412ko>, GG<AbstractC2412ko> {
    boolean hasPrevious();

    default AbstractC2412ko j() {
        if (!hasPrevious()) {
            return null;
        }
        AbstractC2412ko abstractC2412koPrevious = previous();
        next();
        return abstractC2412koPrevious;
    }

    default AbstractC2412ko o() {
        if (!hasNext()) {
            return null;
        }
        AbstractC2412ko next = next();
        previous();
        return next;
    }

    AbstractC2412ko previous();

    @Override // java.util.Iterator, java.util.ListIterator
    default void remove() {
        throw new UnsupportedOperationException(ProductAction.ACTION_REMOVE);
    }
}
