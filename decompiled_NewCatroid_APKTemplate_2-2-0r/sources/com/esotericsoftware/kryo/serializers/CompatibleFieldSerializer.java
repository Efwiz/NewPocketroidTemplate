package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.InputChunked;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.OutputChunked;
import com.esotericsoftware.kryo.util.ObjectMap;
import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes8.dex */
public class CompatibleFieldSerializer<T> extends FieldSerializer<T> {
    private static final int binarySearchThreshold = 32;
    private final CompatibleFieldSerializerConfig config;

    public CompatibleFieldSerializer(Kryo kryo, Class cls) {
        this(kryo, cls, new CompatibleFieldSerializerConfig());
    }

    public CompatibleFieldSerializer(Kryo kryo, Class cls, CompatibleFieldSerializerConfig compatibleFieldSerializerConfig) {
        super(kryo, cls, compatibleFieldSerializerConfig);
        this.config = compatibleFieldSerializerConfig;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00b3  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer, com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, T t) {
        Output outputChunked;
        OutputChunked outputChunked2;
        Class<?> cls;
        int iPushTypeVariables = pushTypeVariables();
        FieldSerializer.CachedField[] cachedFieldArr = this.cachedFields.fields;
        ObjectMap graphContext = kryo.getGraphContext();
        if (!graphContext.containsKey(this)) {
            if (Log.TRACE) {
                Log.trace("kryo", "Write fields for class: " + this.type.getName());
            }
            graphContext.put(this, null);
            output.writeVarInt(cachedFieldArr.length, true);
            int length = cachedFieldArr.length;
            for (int i = 0; i < length; i++) {
                if (Log.TRACE) {
                    Log.trace("kryo", "Write field name: " + cachedFieldArr[i].f134name + Util.pos(output.position()));
                }
                output.writeString(cachedFieldArr[i].f134name);
            }
        }
        boolean z = this.config.chunked;
        boolean z2 = this.config.readUnknownFieldData;
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

    /* JADX WARN: Code duplicated, block: B:70:0x01c7  */
    /* JADX WARN: Code duplicated, block: B:73:0x01d5  */
    /* JADX WARN: Code duplicated, block: B:90:0x01d8 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer, com.esotericsoftware.kryo.Serializer
    public T read(Kryo kryo, Input input, Class<? extends T> cls) {
        InputChunked inputChunked;
        Input inputChunked2;
        boolean z;
        Kryo kryo2 = kryo;
        int iPushTypeVariables = pushTypeVariables();
        T tCreate = create(kryo, input, cls);
        kryo2.reference(tCreate);
        FieldSerializer.CachedField[] fields = (FieldSerializer.CachedField[]) kryo.getGraphContext().get(this);
        if (fields == null) {
            fields = readFields(kryo, input);
        }
        FieldSerializer.CachedField[] cachedFieldArr = fields;
        boolean z2 = this.config.chunked;
        boolean z3 = this.config.readUnknownFieldData;
        if (z2) {
            inputChunked2 = new InputChunked(input, this.config.chunkSize);
            inputChunked = inputChunked2;
        } else {
            inputChunked = 0;
            inputChunked2 = input;
        }
        int length = cachedFieldArr.length;
        int i = 0;
        while (i < length) {
            FieldSerializer.CachedField cachedField = cachedFieldArr[i];
            FieldSerializer.CachedField[] cachedFieldArr2 = cachedFieldArr;
            if (z3) {
                try {
                    Registration registration = kryo2.readClass(inputChunked2);
                    if (registration == null) {
                        if (z2) {
                            inputChunked.nextChunk();
                        }
                        z = z3;
                        length = length;
                    } else {
                        z = z3;
                        Class type = registration.getType();
                        if (cachedField == null) {
                            if (Log.TRACE) {
                                Log.trace("kryo", "Read unknown data, type: " + Util.className(type) + Util.pos(input.position()));
                            }
                            try {
                                kryo2.readObject(inputChunked2, type);
                            } catch (KryoException e) {
                                String str = "Unable to read unknown data, type: " + Util.className(type) + " (" + getType().getName() + "#" + cachedField + Constant.AFTER_QUTO;
                                if (!z2) {
                                    throw new KryoException(str, e);
                                }
                                if (Log.DEBUG) {
                                    Log.debug("kryo", str, e);
                                }
                            }
                            if (z2) {
                                inputChunked.nextChunk();
                            }
                        } else {
                            length = length;
                            if (cachedField.valueClass != null && !Util.isAssignableTo(type, cachedField.field.getType())) {
                                String str2 = "Read type is incompatible with the field type: " + Util.className(type) + " -> " + Util.className(cachedField.valueClass) + " (" + getType().getName() + "#" + cachedField + Constant.AFTER_QUTO;
                                if (!z2) {
                                    throw new KryoException(str2);
                                }
                                if (Log.DEBUG) {
                                    Log.debug("kryo", str2);
                                }
                                inputChunked.nextChunk();
                            } else {
                                cachedField.setCanBeNull(false);
                                cachedField.setValueClass(type);
                                cachedField.setReuseSerializer(false);
                                if (Log.TRACE) {
                                    log("Read", cachedField, input.position());
                                }
                                cachedField.read(inputChunked2, tCreate);
                                if (z2) {
                                    inputChunked.nextChunk();
                                }
                            }
                        }
                    }
                } catch (KryoException e2) {
                    z = z3;
                    length = length;
                    String str3 = "Unable to read unknown data (unknown type). (" + getType().getName() + "#" + cachedField + Constant.AFTER_QUTO;
                    if (!z2) {
                        throw new KryoException(str3, e2);
                    }
                    if (Log.DEBUG) {
                        Log.debug("kryo", str3, e2);
                    }
                    inputChunked.nextChunk();
                }
            } else {
                z = z3;
                length = length;
                if (cachedField != null) {
                    if (Log.TRACE) {
                        log("Read", cachedField, input.position());
                    }
                    cachedField.read(inputChunked2, tCreate);
                    if (z2) {
                        inputChunked.nextChunk();
                    }
                } else {
                    if (!z2) {
                        throw new KryoException("Unknown field. (" + getType().getName() + Constant.AFTER_QUTO);
                    }
                    if (Log.TRACE) {
                        Log.trace("kryo", "Skip unknown field.");
                    }
                    inputChunked.nextChunk();
                }
            }
            i++;
            kryo2 = kryo;
            cachedFieldArr = cachedFieldArr2;
            z3 = z;
            length = length;
        }
        popTypeVariables(iPushTypeVariables);
        return tCreate;
    }

    private FieldSerializer.CachedField[] readFields(Kryo kryo, Input input) {
        if (Log.TRACE) {
            Log.trace("kryo", "Read fields for class: " + this.type.getName());
        }
        int varInt = input.readVarInt(true);
        String[] strArr = new String[varInt];
        for (int i = 0; i < varInt; i++) {
            strArr[i] = input.readString();
            if (Log.TRACE) {
                Log.trace("kryo", "Read field name: " + strArr[i]);
            }
        }
        FieldSerializer.CachedField[] cachedFieldArr = new FieldSerializer.CachedField[varInt];
        FieldSerializer.CachedField[] cachedFieldArr2 = this.cachedFields.fields;
        if (varInt < 32) {
            for (int i2 = 0; i2 < varInt; i2++) {
                String str = strArr[i2];
                int length = cachedFieldArr2.length;
                int i3 = 0;
                while (true) {
                    if (i3 < length) {
                        if (cachedFieldArr2[i3].f134name.equals(str)) {
                            cachedFieldArr[i2] = cachedFieldArr2[i3];
                            break;
                        }
                        i3++;
                    } else {
                        if (!Log.TRACE) {
                            break;
                        }
                        Log.trace("kryo", "Unknown field will be skipped: " + str);
                        break;
                    }
                }
            }
        } else {
            int length2 = cachedFieldArr2.length - 1;
            for (int i4 = 0; i4 < varInt; i4++) {
                String str2 = strArr[i4];
                int i5 = 0;
                int i6 = length2;
                while (true) {
                    if (i5 <= i6) {
                        int i7 = (i5 + i6) >>> 1;
                        int iCompareTo = str2.compareTo(cachedFieldArr2[i7].f134name);
                        if (iCompareTo >= 0) {
                            if (iCompareTo <= 0) {
                                cachedFieldArr[i4] = cachedFieldArr2[i7];
                                break;
                            }
                            i5 = i7 + 1;
                        } else {
                            i6 = i7 - 1;
                        }
                    } else {
                        if (!Log.TRACE) {
                            break;
                        }
                        Log.trace("kryo", "Unknown field will be skipped: " + str2);
                        break;
                    }
                }
            }
        }
        kryo.getGraphContext().put(this, cachedFieldArr);
        return cachedFieldArr;
    }

    public CompatibleFieldSerializerConfig getCompatibleFieldSerializerConfig() {
        return this.config;
    }

    public static class CompatibleFieldSerializerConfig extends FieldSerializer.FieldSerializerConfig {
        boolean chunked;
        boolean readUnknownFieldData = true;
        int chunkSize = 1024;

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.FieldSerializerConfig
        /* JADX INFO: renamed from: clone */
        public CompatibleFieldSerializerConfig mo2542clone() {
            return (CompatibleFieldSerializerConfig) super.mo2542clone();
        }

        public void setReadUnknownFieldData(boolean z) {
            this.readUnknownFieldData = z;
        }

        public boolean getReadUnknownTagData() {
            return this.readUnknownFieldData;
        }

        public void setChunkedEncoding(boolean z) {
            this.chunked = z;
            if (Log.TRACE) {
                Log.trace("kryo", "CompatibleFieldSerializerConfig setChunked: " + z);
            }
        }

        public boolean getChunkedEncoding() {
            return this.chunked;
        }

        public void setChunkSize(int i) {
            this.chunkSize = i;
            if (Log.TRACE) {
                Log.trace("kryo", "CompatibleFieldSerializerConfig setChunkSize: " + i);
            }
        }

        public int getChunkSize() {
            return this.chunkSize;
        }
    }
}
