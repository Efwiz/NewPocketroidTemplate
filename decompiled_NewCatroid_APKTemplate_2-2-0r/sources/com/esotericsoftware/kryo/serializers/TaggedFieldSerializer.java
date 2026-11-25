package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.InputChunked;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.OutputChunked;
import com.esotericsoftware.kryo.util.IntMap;
import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes8.dex */
public class TaggedFieldSerializer<T> extends FieldSerializer<T> {
    private final TaggedFieldSerializerConfig config;
    private IntMap<FieldSerializer.CachedField> readTags;
    private FieldSerializer.CachedField[] writeTags;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Tag {
        int value();
    }

    protected void writeHeader(Kryo kryo, Output output, T t) {
    }

    public TaggedFieldSerializer(Kryo kryo, Class cls) {
        this(kryo, cls, new TaggedFieldSerializerConfig());
    }

    public TaggedFieldSerializer(Kryo kryo, Class cls, TaggedFieldSerializerConfig taggedFieldSerializerConfig) {
        super(kryo, cls, taggedFieldSerializerConfig);
        this.config = taggedFieldSerializerConfig;
        setAcceptsNull(true);
    }

    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer
    protected void initializeCachedFields() {
        FieldSerializer.CachedField[] cachedFieldArr = this.cachedFields.fields;
        int length = cachedFieldArr.length;
        for (int i = 0; i < length; i++) {
            if (cachedFieldArr[i].field.getAnnotation(Tag.class) == null) {
                if (Log.TRACE) {
                    Log.trace("kryo", "Ignoring field without tag: " + cachedFieldArr[i]);
                }
                super.removeField(cachedFieldArr[i]);
            }
        }
        FieldSerializer.CachedField[] cachedFieldArr2 = this.cachedFields.fields;
        ArrayList arrayList = new ArrayList(cachedFieldArr2.length);
        this.readTags = new IntMap<>((int) (cachedFieldArr2.length / 0.8f));
        for (FieldSerializer.CachedField cachedField : cachedFieldArr2) {
            Field field = cachedField.field;
            int iValue = ((Tag) field.getAnnotation(Tag.class)).value();
            if (this.readTags.containsKey(iValue)) {
                throw new KryoException(String.format("Duplicate tag %d on fields: %s and %s", Integer.valueOf(iValue), field, arrayList.get(iValue)));
            }
            this.readTags.put(iValue, cachedField);
            if (field.getAnnotation(Deprecated.class) == null) {
                arrayList.add(cachedField);
            }
            cachedField.tag = iValue;
        }
        this.writeTags = (FieldSerializer.CachedField[]) arrayList.toArray(new FieldSerializer.CachedField[arrayList.size()]);
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

    /* JADX WARN: Code duplicated, block: B:23:0x0060  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer, com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, T t) {
        Output outputChunked;
        OutputChunked outputChunked2;
        Class<?> cls;
        if (t == null) {
            output.writeByte((byte) 0);
            return;
        }
        int iPushTypeVariables = pushTypeVariables();
        FieldSerializer.CachedField[] cachedFieldArr = this.writeTags;
        output.writeVarInt(cachedFieldArr.length + 1, true);
        writeHeader(kryo, output, t);
        boolean z = this.config.chunked;
        boolean z2 = this.config.readUnknownTagData;
        if (z) {
            outputChunked = new OutputChunked(output, this.config.chunkSize);
            outputChunked2 = outputChunked;
        } else {
            outputChunked = output;
            outputChunked2 = 0;
        }
        for (FieldSerializer.CachedField cachedField : cachedFieldArr) {
            if (Log.TRACE) {
                log("Write", cachedField, output.position());
            }
            output.writeVarInt(cachedField.tag, true);
            if (z2) {
                if (t != null) {
                    try {
                        Object obj = cachedField.field.get(t);
                        if (obj != null) {
                            cls = obj.getClass();
                        } else {
                            cls = null;
                        }
                    } catch (IllegalAccessException unused) {
                        cls = null;
                    }
                } else {
                    cls = null;
                }
                kryo.writeClass(outputChunked, cls);
                if (cls != null) {
                    cachedField.setCanBeNull(false);
                    cachedField.setValueClass(cls);
                    cachedField.setReuseSerializer(false);
                } else if (z) {
                    outputChunked2.endChunk();
                }
            }
            cachedField.write(outputChunked, t);
            if (z) {
                outputChunked2.endChunk();
            }
        }
        popTypeVariables(iPushTypeVariables);
    }

    /* JADX WARN: Code duplicated, block: B:60:0x0199  */
    /* JADX WARN: Code duplicated, block: B:63:0x01a9  */
    /* JADX WARN: Code duplicated, block: B:78:0x01ac A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer, com.esotericsoftware.kryo.Serializer
    public T read(Kryo kryo, Input input, Class<? extends T> cls) {
        InputChunked inputChunked;
        Input inputChunked2;
        T t;
        boolean z;
        Input input2 = input;
        boolean z2 = true;
        int varInt = input2.readVarInt(true);
        if (varInt == 0) {
            return null;
        }
        int i = varInt - 1;
        int iPushTypeVariables = pushTypeVariables();
        T tCreate = create(kryo, input, cls);
        kryo.reference(tCreate);
        boolean z3 = this.config.chunked;
        boolean z4 = this.config.readUnknownTagData;
        if (z3) {
            inputChunked2 = new InputChunked(input2, this.config.chunkSize);
            inputChunked = inputChunked2;
        } else {
            inputChunked = 0;
            inputChunked2 = input2;
        }
        IntMap<FieldSerializer.CachedField> intMap = this.readTags;
        int i2 = 0;
        while (i2 < i) {
            int i3 = i;
            int varInt2 = input2.readVarInt(z2);
            FieldSerializer.CachedField cachedField = intMap.get(varInt2);
            IntMap<FieldSerializer.CachedField> intMap2 = intMap;
            int i4 = iPushTypeVariables;
            if (z4) {
                try {
                    Registration registration = kryo.readClass(inputChunked2);
                    if (registration == null) {
                        if (z3) {
                            inputChunked.nextChunk();
                        }
                        z = z4;
                    } else {
                        z = z4;
                        Class type = registration.getType();
                        if (cachedField == null) {
                            if (Log.TRACE) {
                                Log.trace("kryo", "Read unknown tag " + varInt2 + " data, type: " + Util.className(type));
                            }
                            try {
                                kryo.readObject(inputChunked2, type);
                            } catch (KryoException e) {
                                String str = "Unable to read unknown tag " + varInt2 + " data, type: " + Util.className(type) + " (" + getType().getName() + "#" + cachedField + Constant.AFTER_QUTO;
                                if (!z3) {
                                    throw new KryoException(str, e);
                                }
                                if (Log.DEBUG) {
                                    Log.debug("kryo", str, e);
                                }
                            }
                            if (z3) {
                                inputChunked.nextChunk();
                            }
                        } else {
                            t = tCreate;
                            i2 = i2;
                            cachedField.setCanBeNull(false);
                            cachedField.setValueClass(type);
                            cachedField.setReuseSerializer(false);
                            if (Log.TRACE) {
                                log("Read", cachedField, input.position());
                            }
                            tCreate = t;
                            cachedField.read(inputChunked2, tCreate);
                            if (z3) {
                                inputChunked.nextChunk();
                            }
                        }
                    }
                } catch (KryoException e2) {
                    t = tCreate;
                    z = z4;
                    i2 = i2;
                    String str2 = "Unable to read unknown tag " + varInt2 + " data (unknown type). (" + getType().getName() + "#" + cachedField + Constant.AFTER_QUTO;
                    if (!z3) {
                        throw new KryoException(str2, e2);
                    }
                    if (Log.DEBUG) {
                        Log.debug("kryo", str2, e2);
                    }
                    inputChunked.nextChunk();
                    tCreate = t;
                }
            } else {
                t = tCreate;
                z = z4;
                i2 = i2;
                if (cachedField != null) {
                    if (Log.TRACE) {
                        log("Read", cachedField, input.position());
                    }
                    tCreate = t;
                    cachedField.read(inputChunked2, tCreate);
                    if (z3) {
                        inputChunked.nextChunk();
                    }
                } else {
                    if (!z3) {
                        throw new KryoException("Unknown field tag: " + varInt2 + " (" + getType().getName() + Constant.AFTER_QUTO);
                    }
                    if (Log.TRACE) {
                        Log.trace("kryo", "Skip unknown field tag: " + varInt2);
                    }
                    inputChunked.nextChunk();
                    tCreate = t;
                }
            }
            i2++;
            tCreate = tCreate;
            i = i3;
            intMap = intMap2;
            iPushTypeVariables = i4;
            z4 = z;
            z2 = true;
            input2 = input;
        }
        T t2 = tCreate;
        popTypeVariables(iPushTypeVariables);
        return t2;
    }

    public TaggedFieldSerializerConfig getTaggedFieldSerializerConfig() {
        return this.config;
    }

    public static class TaggedFieldSerializerConfig extends FieldSerializer.FieldSerializerConfig {
        int chunkSize = 1024;
        boolean chunked;
        boolean readUnknownTagData;

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.FieldSerializerConfig
        /* JADX INFO: renamed from: clone */
        public TaggedFieldSerializerConfig mo2542clone() {
            return (TaggedFieldSerializerConfig) super.mo2542clone();
        }

        public void setReadUnknownTagData(boolean z) {
            this.readUnknownTagData = z;
        }

        public boolean getReadUnknownTagData() {
            return this.readUnknownTagData;
        }

        public void setChunkedEncoding(boolean z) {
            this.chunked = z;
            if (Log.TRACE) {
                Log.trace("kryo", "TaggedFieldSerializerConfig setChunked: " + z);
            }
        }

        public boolean getChunkedEncoding() {
            return this.chunked;
        }

        public void setChunkSize(int i) {
            this.chunkSize = i;
            if (Log.TRACE) {
                Log.trace("kryo", "TaggedFieldSerializerConfig setChunkSize: " + i);
            }
        }

        public int getChunkSize() {
            return this.chunkSize;
        }
    }
}
