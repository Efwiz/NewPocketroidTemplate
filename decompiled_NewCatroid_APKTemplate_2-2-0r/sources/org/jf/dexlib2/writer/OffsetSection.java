package org.jf.dexlib2.writer;

import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: classes6.dex */
public interface OffsetSection<Key> {
    int getItemOffset(Key key);

    Collection<? extends Map.Entry<? extends Key, Integer>> getItems();
}
