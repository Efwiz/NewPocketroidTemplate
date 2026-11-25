package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Serializer;

/* JADX INFO: loaded from: classes8.dex */
public abstract class ImmutableSerializer<T> extends Serializer<T> {
    public ImmutableSerializer() {
        setImmutable(true);
    }
}
