package org.apache.commons.collections;

import java.util.Comparator;

/* JADX INFO: loaded from: classes12.dex */
public interface SortedBag extends Bag {
    Comparator comparator();

    Object first();

    Object last();
}
