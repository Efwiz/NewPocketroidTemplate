package org.jf.dexlib2.writer.pool;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes6.dex */
public class BasePool<Key, Value> implements Markable {
    protected final DexPool dexPool;
    protected final Map<Key, Value> internedItems = new LinkedHashMap();
    private int markedItemCount = -1;

    public BasePool(DexPool dexPool) {
        this.dexPool = dexPool;
    }

    @Override // org.jf.dexlib2.writer.pool.Markable
    public void mark() {
        this.markedItemCount = this.internedItems.size();
    }

    @Override // org.jf.dexlib2.writer.pool.Markable
    public void reset() {
        int i = this.markedItemCount;
        if (i < 0) {
            throw new IllegalStateException("mark() must be called before calling reset()");
        }
        if (i == this.internedItems.size()) {
            return;
        }
        Iterator<Key> it = this.internedItems.keySet().iterator();
        for (int i2 = 0; i2 < this.markedItemCount; i2++) {
            it.next();
        }
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    public int getItemCount() {
        return this.internedItems.size();
    }
}
