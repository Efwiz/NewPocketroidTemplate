package org.apache.commons.collections.buffer;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.functors.InstanceofPredicate;

/* JADX INFO: loaded from: classes12.dex */
public class TypedBuffer {
    public static Buffer decorate(Buffer buffer, Class cls) {
        return new PredicatedBuffer(buffer, InstanceofPredicate.getInstance(cls));
    }

    protected TypedBuffer() {
    }
}
