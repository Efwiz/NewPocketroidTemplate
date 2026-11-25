package org.jetbrains.kotlin.com.google.common.collect;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Map;
import org.jetbrains.kotlin.com.google.common.base.Objects;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ForwardingMapEntry<K, V> extends ForwardingObject implements Map.Entry<K, V> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingObject
    public abstract Map.Entry<K, V> delegate();

    protected ForwardingMapEntry() {
    }

    @Override // java.util.Map.Entry
    public K getKey() {
        return delegate().getKey();
    }

    @Override // java.util.Map.Entry
    public V getValue() {
        return delegate().getValue();
    }

    @Override // java.util.Map.Entry
    public V setValue(V v) {
        return delegate().setValue(v);
    }

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        return delegate().equals(obj);
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        return delegate().hashCode();
    }

    protected boolean standardEquals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return Objects.equal(getKey(), entry.getKey()) && Objects.equal(getValue(), entry.getValue());
    }

    protected int standardHashCode() {
        K key = getKey();
        V value = getValue();
        return (key == null ? 0 : key.hashCode()) ^ (value != null ? value.hashCode() : 0);
    }

    protected String standardToString() {
        return getKey() + ContainerUtils.KEY_VALUE_DELIMITER + getValue();
    }
}
