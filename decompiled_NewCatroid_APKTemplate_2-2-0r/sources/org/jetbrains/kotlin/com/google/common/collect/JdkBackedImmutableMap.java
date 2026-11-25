package org.jetbrains.kotlin.com.google.common.collect;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.google.common.base.Preconditions;

/* JADX INFO: loaded from: classes3.dex */
final class JdkBackedImmutableMap<K, V> extends ImmutableMap<K, V> {
    private final transient Map<K, V> delegateMap;
    private final transient ImmutableList<Map.Entry<K, V>> entries;

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableMap
    boolean isPartialView() {
        return false;
    }

    static <K, V> ImmutableMap<K, V> create(int i, Map.Entry<K, V>[] entryArr) {
        HashMap mapNewHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(i);
        for (int i2 = 0; i2 < i; i2++) {
            entryArr[i2] = RegularImmutableMap.makeImmutable(entryArr[i2]);
            Object objPutIfAbsent = mapNewHashMapWithExpectedSize.putIfAbsent(entryArr[i2].getKey(), entryArr[i2].getValue());
            if (objPutIfAbsent != null) {
                throw conflictException(ASTExpr.DEFAULT_MAP_KEY_NAME, entryArr[i2], entryArr[i2].getKey() + ContainerUtils.KEY_VALUE_DELIMITER + objPutIfAbsent);
            }
        }
        return new JdkBackedImmutableMap(mapNewHashMapWithExpectedSize, ImmutableList.asImmutableList(entryArr, i));
    }

    JdkBackedImmutableMap(Map<K, V> map, ImmutableList<Map.Entry<K, V>> immutableList) {
        this.delegateMap = map;
        this.entries = immutableList;
    }

    @Override // java.util.Map
    public int size() {
        return this.entries.size();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableMap, java.util.Map
    public V get(Object obj) {
        return this.delegateMap.get(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableMap
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new ImmutableMapEntrySet.RegularEntrySet(this, this.entries);
    }

    @Override // java.util.Map
    public void forEach(final BiConsumer<? super K, ? super V> biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        this.entries.forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.google.common.collect.JdkBackedImmutableMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Map.Entry entry = (Map.Entry) obj;
                biConsumer.accept(entry.getKey(), entry.getValue());
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableMap
    ImmutableSet<K> createKeySet() {
        return new ImmutableMapKeySet(this);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableMap
    ImmutableCollection<V> createValues() {
        return new ImmutableMapValues(this);
    }
}
