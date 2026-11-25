package com.android.tools.r8.internal;

import com.reandroid.archive.PathTree;
import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class PU extends QU {
    public static LinkedHashSet a(Set set, LinkedHashSet linkedHashSet) {
        int size;
        AbstractC0936Ds.c(linkedHashSet, PathTree.NAME_elements);
        Integer numValueOf = Integer.valueOf(linkedHashSet.size());
        if (numValueOf != null) {
            size = set.size() + numValueOf.intValue();
        } else {
            size = set.size() * 2;
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet(SD.a(size));
        linkedHashSet2.addAll(set);
        AbstractC0945Eb.a(linkedHashSet, linkedHashSet2);
        return linkedHashSet2;
    }
}
