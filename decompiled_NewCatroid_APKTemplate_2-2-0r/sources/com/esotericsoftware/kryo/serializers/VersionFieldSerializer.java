package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.minlog.Log;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes8.dex */
public class VersionFieldSerializer<T> extends FieldSerializer<T> {
    private final VersionFieldSerializerConfig config;
    private int[] fieldVersion;
    private int typeVersion;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Since {
        int value() default 0;
    }

    public VersionFieldSerializer(Kryo kryo, Class cls) {
        this(kryo, cls, new VersionFieldSerializerConfig());
    }

    public VersionFieldSerializer(Kryo kryo, Class cls, VersionFieldSerializerConfig versionFieldSerializerConfig) {
        super(kryo, cls, versionFieldSerializerConfig);
        this.config = versionFieldSerializerConfig;
        setAcceptsNull(true);
        initializeCachedFields();
    }

    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer
    protected void initializeCachedFields() {
        FieldSerializer.CachedField[] cachedFieldArr = this.cachedFields.fields;
        this.fieldVersion = new int[cachedFieldArr.length];
        int length = cachedFieldArr.length;
        for (int i = 0; i < length; i++) {
            Since since = (Since) cachedFieldArr[i].field.getAnnotation(Since.class);
            if (since != null) {
                this.fieldVersion[i] = since.value();
                this.typeVersion = Math.max(this.fieldVersion[i], this.typeVersion);
            } else {
                this.fieldVersion[i] = 0;
            }
        }
        if (Log.DEBUG) {
            Log.debug("Version for type " + getType().getName() + ": " + this.typeVersion);
        }
    }

    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer
    public void removeField(String str) {
        super.removeField(str);
        initializeCachedFields();
    }

    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer
    public void removeField(FieldSerializer.CachedField cachedField) {
        super.removeField(cachedField);
        initializeCachedFields();
    }

    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer, com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, T t) {
        if (t == null) {
            output.writeByte((byte) 0);
            return;
        }
        int iPushTypeVariables = pushTypeVariables();
        FieldSerializer.CachedField[] cachedFieldArr = this.cachedFields.fields;
        output.writeVarInt(this.typeVersion + 1, true);
        int length = cachedFieldArr.length;
        for (int i = 0; i < length; i++) {
            if (Log.TRACE) {
                log("Write", cachedFieldArr[i], output.position());
            }
            cachedFieldArr[i].write(output, t);
        }
        popTypeVariables(iPushTypeVariables);
    }

    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer, com.esotericsoftware.kryo.Serializer
    public T read(Kryo kryo, Input input, Class<? extends T> cls) {
        int varInt = input.readVarInt(true);
        if (varInt == 0) {
            return null;
        }
        int i = varInt - 1;
        if (!this.config.compatible && i != this.typeVersion) {
            throw new KryoException("Version is not compatible: " + i + " != " + this.typeVersion);
        }
        int iPushTypeVariables = pushTypeVariables();
        T tCreate = create(kryo, input, cls);
        kryo.reference(tCreate);
        FieldSerializer.CachedField[] cachedFieldArr = this.cachedFields.fields;
        int length = cachedFieldArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (this.fieldVersion[i2] > i) {
                if (Log.DEBUG) {
                    Log.debug("Skip field: " + cachedFieldArr[i2].field.getName());
                }
            } else {
                if (Log.TRACE) {
                    log("Read", cachedFieldArr[i2], input.position());
                }
                cachedFieldArr[i2].read(input, tCreate);
            }
        }
        popTypeVariables(iPushTypeVariables);
        return tCreate;
    }

    public VersionFieldSerializerConfig getVersionFieldSerializerConfig() {
        return this.config;
    }

    public static class VersionFieldSerializerConfig extends FieldSerializer.FieldSerializerConfig {
        boolean compatible = true;

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.FieldSerializerConfig
        /* JADX INFO: renamed from: clone */
        public VersionFieldSerializerConfig mo2542clone() {
            return (VersionFieldSerializerConfig) super.mo2542clone();
        }

        public void setCompatible(boolean z) {
            this.compatible = z;
            if (Log.TRACE) {
                Log.trace("kryo", "VersionFieldSerializerConfig setCompatible: " + z);
            }
        }

        public boolean getCompatible() {
            return this.compatible;
        }
    }
}
