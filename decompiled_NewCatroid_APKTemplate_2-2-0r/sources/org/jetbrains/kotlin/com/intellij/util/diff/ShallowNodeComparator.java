package org.jetbrains.kotlin.com.intellij.util.diff;

import org.jetbrains.kotlin.com.intellij.util.ThreeState;

/* JADX INFO: loaded from: classes3.dex */
public interface ShallowNodeComparator<OT, NT> {
    ThreeState deepEqual(OT ot, NT nt);

    boolean hashCodesEqual(OT ot, NT nt);

    boolean typesEqual(OT ot, NT nt);
}
