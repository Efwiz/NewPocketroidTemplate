package org.jetbrains.kotlin.util.slicedMap;

import net.bytebuddy.implementation.MethodDelegation;

/* JADX INFO: loaded from: classes6.dex */
public class DelegatingSlice<K, V> implements WritableSlice<K, V> {
    private final WritableSlice<K, V> delegate;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
        } else {
            objArr[0] = "org/jetbrains/kotlin/util/slicedMap/DelegatingSlice";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/util/slicedMap/DelegatingSlice";
        } else {
            objArr[1] = "getKey";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public DelegatingSlice(WritableSlice<K, V> writableSlice) {
        if (writableSlice == null) {
            $$$reportNull$$$0(0);
        }
        this.delegate = writableSlice;
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
    public boolean isCollective() {
        return this.delegate.isCollective();
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
    public boolean check(K k, V v) {
        return this.delegate.check(k, v);
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
    public void afterPut(MutableSlicedMap mutableSlicedMap, K k, V v) {
        this.delegate.afterPut(mutableSlicedMap, k, v);
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
    public RewritePolicy getRewritePolicy() {
        return this.delegate.getRewritePolicy();
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice, org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
    public KeyWithSlice<K, V, WritableSlice<K, V>> getKey() {
        KeyWithSlice<K, V, WritableSlice<K, V>> key = this.delegate.getKey();
        if (key == null) {
            $$$reportNull$$$0(1);
        }
        return key;
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
    public V computeValue(SlicedMap slicedMap, K k, V v, boolean z) {
        return this.delegate.computeValue(slicedMap, k, v, z);
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
    public ReadOnlySlice<K, V> makeRawValueVersion() {
        return this.delegate.makeRawValueVersion();
    }
}
