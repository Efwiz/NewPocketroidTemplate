package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import java.util.EnumMap;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class EnumMapSerializer extends MapSerializer<EnumMap> {
    private final Class<? extends Enum> enumType;

    @Override // com.esotericsoftware.kryo.serializers.MapSerializer
    protected /* bridge */ /* synthetic */ Map create(Kryo kryo, Input input, Class cls, int i) {
        return create(kryo, input, (Class<? extends EnumMap>) cls, i);
    }

    public EnumMapSerializer(Class<? extends Enum> cls) {
        this.enumType = cls;
    }

    @Override // com.esotericsoftware.kryo.serializers.MapSerializer
    protected EnumMap create(Kryo kryo, Input input, Class<? extends EnumMap> cls, int i) {
        return new EnumMap(this.enumType);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.esotericsoftware.kryo.serializers.MapSerializer
    public EnumMap createCopy(Kryo kryo, EnumMap enumMap) {
        return new EnumMap(enumMap);
    }
}
