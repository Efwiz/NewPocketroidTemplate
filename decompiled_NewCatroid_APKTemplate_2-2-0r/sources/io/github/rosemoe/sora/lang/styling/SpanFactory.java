package io.github.rosemoe.sora.lang.styling;

import io.github.rosemoe.sora.lang.styling.span.internal.SpanImpl;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class SpanFactory {
    private SpanFactory() {
    }

    public static Span obtain(int i, long j) {
        return SpanImpl.obtain(i, j);
    }

    public static void recycleAll(Collection<Span> collection) {
        Iterator<Span> it = collection.iterator();
        while (it.hasNext() && it.next().recycle()) {
        }
    }
}
