package com.esotericsoftware.kryo.serializers;

import com.android.tools.r8.internal.AT$$ExternalSyntheticBackport0;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.util.Util;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes8.dex */
public final class ImmutableCollectionsSerializers {
    public static void addDefaultSerializers(Kryo kryo) {
        if (Util.isClassAvailable("java.util.ImmutableCollections")) {
            JdkImmutableListSerializer.addDefaultSerializers(kryo);
            JdkImmutableMapSerializer.addDefaultSerializers(kryo);
            JdkImmutableSetSerializer.addDefaultSerializers(kryo);
        }
    }

    static class JdkImmutableListSerializer extends CollectionSerializer<List<Object>> {
        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        protected /* bridge */ /* synthetic */ Collection create(Kryo kryo, Input input, Class cls, int i) {
            return create(kryo, input, (Class<? extends List<Object>>) cls, i);
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer, com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends List<Object>>) cls);
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer, com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Collection read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends List<Object>>) cls);
        }

        private JdkImmutableListSerializer() {
            setElementsCanBeNull(false);
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        protected List<Object> create(Kryo kryo, Input input, Class<? extends List<Object>> cls, int i) {
            return new ArrayList(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        public List<Object> createCopy(Kryo kryo, List<Object> list) {
            return new ArrayList(list.size());
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer, com.esotericsoftware.kryo.Serializer
        public List<Object> read(Kryo kryo, Input input, Class<? extends List<Object>> cls) {
            List list = (List) super.read(kryo, input, (Class) cls);
            if (list == null) {
                return null;
            }
            return AT$$ExternalSyntheticBackport0.m(list.toArray());
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        public List<Object> copy(Kryo kryo, List<Object> list) {
            return AT$$ExternalSyntheticBackport0.m(super.copy(kryo, list));
        }

        static void addDefaultSerializers(Kryo kryo) {
            JdkImmutableListSerializer jdkImmutableListSerializer = new JdkImmutableListSerializer();
            kryo.addDefaultSerializer(Collections.emptyList().getClass(), jdkImmutableListSerializer);
            kryo.addDefaultSerializer(AT$$ExternalSyntheticBackport0.m(new Object[]{1}).getClass(), jdkImmutableListSerializer);
            kryo.addDefaultSerializer(AT$$ExternalSyntheticBackport0.m(new Object[]{1, 2, 3, 4}).getClass(), jdkImmutableListSerializer);
            kryo.addDefaultSerializer(AT$$ExternalSyntheticBackport0.m(new Object[]{1, 2, 3, 4}).subList(0, 2).getClass(), jdkImmutableListSerializer);
        }
    }

    static class JdkImmutableMapSerializer extends MapSerializer<Map<Object, Object>> {
        @Override // com.esotericsoftware.kryo.serializers.MapSerializer, com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Map<Object, Object>>) cls);
        }

        private JdkImmutableMapSerializer() {
            setKeysCanBeNull(false);
            setValuesCanBeNull(false);
        }

        @Override // com.esotericsoftware.kryo.serializers.MapSerializer
        protected Map<Object, Object> create(Kryo kryo, Input input, Class<? extends Map<Object, Object>> cls, int i) {
            return new HashMap();
        }

        @Override // com.esotericsoftware.kryo.serializers.MapSerializer
        protected Map<Object, Object> createCopy(Kryo kryo, Map<Object, Object> map) {
            return new HashMap();
        }

        @Override // com.esotericsoftware.kryo.serializers.MapSerializer, com.esotericsoftware.kryo.Serializer
        public Map<Object, Object> read(Kryo kryo, Input input, Class<? extends Map<Object, Object>> cls) {
            Map map = super.read(kryo, input, (Class<? extends Map>) cls);
            if (map == null) {
                return null;
            }
            return AT$$ExternalSyntheticBackport0.m(map);
        }

        @Override // com.esotericsoftware.kryo.serializers.MapSerializer, com.esotericsoftware.kryo.Serializer
        public Map<Object, Object> copy(Kryo kryo, Map<Object, Object> map) {
            return AT$$ExternalSyntheticBackport0.m(super.copy(kryo, map));
        }

        static void addDefaultSerializers(Kryo kryo) {
            JdkImmutableMapSerializer jdkImmutableMapSerializer = new JdkImmutableMapSerializer();
            kryo.addDefaultSerializer(Collections.emptyMap().getClass(), jdkImmutableMapSerializer);
            kryo.addDefaultSerializer(AT$$ExternalSyntheticBackport0.m(new Map.Entry[]{new AbstractMap.SimpleEntry(1, 2)}).getClass(), jdkImmutableMapSerializer);
            kryo.addDefaultSerializer(AT$$ExternalSyntheticBackport0.m(new Map.Entry[]{new AbstractMap.SimpleEntry(1, 2), new AbstractMap.SimpleEntry(3, 4)}).getClass(), jdkImmutableMapSerializer);
        }
    }

    static class JdkImmutableSetSerializer extends CollectionSerializer<Set<Object>> {
        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        protected /* bridge */ /* synthetic */ Collection create(Kryo kryo, Input input, Class cls, int i) {
            return create(kryo, input, (Class<? extends Set<Object>>) cls, i);
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer, com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Set<Object>>) cls);
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer, com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Collection read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Set<Object>>) cls);
        }

        private JdkImmutableSetSerializer() {
            setElementsCanBeNull(false);
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        protected Set<Object> create(Kryo kryo, Input input, Class<? extends Set<Object>> cls, int i) {
            return new HashSet();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        public Set<Object> createCopy(Kryo kryo, Set<Object> set) {
            return new HashSet();
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer, com.esotericsoftware.kryo.Serializer
        public Set<Object> read(Kryo kryo, Input input, Class<? extends Set<Object>> cls) {
            Set set = (Set) super.read(kryo, input, (Class) cls);
            if (set == null) {
                return null;
            }
            return AT$$ExternalSyntheticBackport0.m738m(set.toArray());
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        public Set<Object> copy(Kryo kryo, Set<Object> set) {
            return AT$$ExternalSyntheticBackport0.m737m(super.copy(kryo, set));
        }

        static void addDefaultSerializers(Kryo kryo) {
            JdkImmutableSetSerializer jdkImmutableSetSerializer = new JdkImmutableSetSerializer();
            kryo.addDefaultSerializer(Collections.emptySet().getClass(), jdkImmutableSetSerializer);
            kryo.addDefaultSerializer(AT$$ExternalSyntheticBackport0.m738m(new Object[]{1}).getClass(), jdkImmutableSetSerializer);
            kryo.addDefaultSerializer(AT$$ExternalSyntheticBackport0.m738m(new Object[]{1, 2, 3, 4}).getClass(), jdkImmutableSetSerializer);
        }
    }
}
