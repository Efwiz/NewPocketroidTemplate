package org.jetbrains.kotlin.util.slicedMap;

/* JADX INFO: loaded from: classes6.dex */
public interface RewritePolicy {
    public static final RewritePolicy DO_NOTHING = new RewritePolicy() { // from class: org.jetbrains.kotlin.util.slicedMap.RewritePolicy.1
        @Override // org.jetbrains.kotlin.util.slicedMap.RewritePolicy
        public <K> boolean rewriteProcessingNeeded(K k) {
            return false;
        }

        @Override // org.jetbrains.kotlin.util.slicedMap.RewritePolicy
        public <K, V> boolean processRewrite(WritableSlice<K, V> writableSlice, K k, V v, V v2) {
            throw new UnsupportedOperationException();
        }
    };

    <K, V> boolean processRewrite(WritableSlice<K, V> writableSlice, K k, V v, V v2);

    <K> boolean rewriteProcessingNeeded(K k);
}
