package org.jetbrains.kotlin.com.intellij.util.diff;

/* JADX INFO: loaded from: classes3.dex */
public interface DiffTreeChangeBuilder<OT, NT> {
    void nodeDeleted(OT ot, OT ot2);

    void nodeInserted(OT ot, NT nt, int i);

    void nodeReplaced(OT ot, NT nt);
}
