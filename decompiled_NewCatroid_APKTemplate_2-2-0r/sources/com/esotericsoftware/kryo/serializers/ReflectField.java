package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Generics;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes8.dex */
class ReflectField extends FieldSerializer.CachedField {
    final FieldSerializer fieldSerializer;
    final Generics.GenericType genericType;

    ReflectField(Field field, FieldSerializer fieldSerializer, Generics.GenericType genericType) {
        super(field);
        this.fieldSerializer = fieldSerializer;
        this.genericType = genericType;
    }

    public Object get(Object obj) throws IllegalAccessException {
        return this.field.get(obj);
    }

    public void set(Object obj, Object obj2) throws IllegalAccessException {
        this.field.set(obj, obj2);
    }

    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
    public void write(Output output, Object obj) {
        Kryo kryo = this.fieldSerializer.kryo;
        try {
            Object obj2 = get(obj);
            Serializer serializer = this.serializer;
            Class clsResolveFieldClass = resolveFieldClass();
            if (clsResolveFieldClass != null) {
                if (serializer == null) {
                    serializer = kryo.getSerializer(clsResolveFieldClass);
                    if (this.valueClass != null && this.reuseSerializer) {
                        this.serializer = serializer;
                    }
                }
                kryo.getGenerics().pushGenericType(this.genericType);
                if (this.canBeNull) {
                    kryo.writeObjectOrNull(output, obj2, serializer);
                } else {
                    if (obj2 == null) {
                        throw new KryoException("Field value cannot be null when canBeNull is false: " + this.f134name + " (" + obj.getClass().getName() + Constant.AFTER_QUTO);
                    }
                    kryo.writeObject(output, obj2, serializer);
                }
            } else {
                if (obj2 == null) {
                    kryo.writeClass(output, null);
                    return;
                }
                Registration registrationWriteClass = kryo.writeClass(output, obj2.getClass());
                if (serializer == null) {
                    serializer = registrationWriteClass.getSerializer();
                }
                kryo.getGenerics().pushGenericType(this.genericType);
                kryo.writeObject(output, obj2, serializer);
            }
            kryo.getGenerics().popGenericType();
        } catch (KryoException e) {
            e.addTrace(this.f134name + " (" + obj.getClass().getName() + Constant.AFTER_QUTO);
            throw e;
        } catch (IllegalAccessException e2) {
            throw new KryoException("Error accessing field: " + this.f134name + " (" + obj.getClass().getName() + Constant.AFTER_QUTO, e2);
        } catch (StackOverflowError e3) {
            throw new KryoException("A StackOverflow occurred. The most likely cause is that your data has a circular reference resulting in infinite recursion. Try enabling references with Kryo.setReferences(true). If your data structure is really more than " + kryo.getDepth() + " levels deep then try increasing your Java stack size.", e3);
        } catch (Throwable th) {
            KryoException kryoException = new KryoException(th);
            kryoException.addTrace(this.f134name + " (" + obj.getClass().getName() + Constant.AFTER_QUTO);
            throw kryoException;
        }
    }

    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
    public void read(Input input, Object obj) {
        Object object;
        Kryo kryo = this.fieldSerializer.kryo;
        try {
            Serializer serializer = this.serializer;
            Class clsResolveFieldClass = resolveFieldClass();
            if (clsResolveFieldClass == null) {
                Registration registration = kryo.readClass(input);
                if (registration == null) {
                    set(obj, null);
                    return;
                }
                if (serializer == null) {
                    serializer = registration.getSerializer();
                }
                kryo.getGenerics().pushGenericType(this.genericType);
                object = kryo.readObject(input, registration.getType(), serializer);
            } else {
                if (serializer == null) {
                    serializer = kryo.getSerializer(clsResolveFieldClass);
                    if (this.valueClass != null && this.reuseSerializer) {
                        this.serializer = serializer;
                    }
                }
                kryo.getGenerics().pushGenericType(this.genericType);
                if (this.canBeNull) {
                    object = kryo.readObjectOrNull(input, clsResolveFieldClass, serializer);
                } else {
                    object = kryo.readObject(input, clsResolveFieldClass, serializer);
                }
            }
            kryo.getGenerics().popGenericType();
            set(obj, object);
        } catch (KryoException e) {
            e.addTrace(this.f134name + " (" + this.fieldSerializer.type.getName() + Constant.AFTER_QUTO);
            throw e;
        } catch (IllegalAccessException e2) {
            throw new KryoException("Error accessing field: " + this.f134name + " (" + this.fieldSerializer.type.getName() + Constant.AFTER_QUTO, e2);
        } catch (Throwable th) {
            KryoException kryoException = new KryoException(th);
            kryoException.addTrace(this.f134name + " (" + this.fieldSerializer.type.getName() + Constant.AFTER_QUTO);
            throw kryoException;
        }
    }

    Class resolveFieldClass() {
        Class clsResolve;
        return (this.valueClass == null && (clsResolve = this.genericType.resolve(this.fieldSerializer.kryo.getGenerics())) != null && this.fieldSerializer.kryo.isFinal(clsResolve)) ? clsResolve : this.valueClass;
    }

    @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
    public void copy(Object obj, Object obj2) {
        try {
            set(obj2, this.fieldSerializer.kryo.copy(get(obj)));
        } catch (KryoException e) {
            e.addTrace(this.f134name + " (" + this.fieldSerializer.type.getName() + Constant.AFTER_QUTO);
            throw e;
        } catch (IllegalAccessException e2) {
            throw new KryoException("Error accessing field: " + this.f134name + " (" + this.fieldSerializer.type.getName() + Constant.AFTER_QUTO, e2);
        } catch (Throwable th) {
            KryoException kryoException = new KryoException(th);
            kryoException.addTrace(this.f134name + " (" + this.fieldSerializer.type.getName() + Constant.AFTER_QUTO);
            throw kryoException;
        }
    }

    static final class IntReflectField extends FieldSerializer.CachedField {
        public IntReflectField(Field field) {
            super(field);
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void write(Output output, Object obj) {
            try {
                if (this.varEncoding) {
                    output.writeVarInt(this.field.getInt(obj), false);
                } else {
                    output.writeInt(this.field.getInt(obj));
                }
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (int)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void read(Input input, Object obj) {
            try {
                if (this.varEncoding) {
                    this.field.setInt(obj, input.readVarInt(false));
                } else {
                    this.field.setInt(obj, input.readInt());
                }
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (int)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void copy(Object obj, Object obj2) {
            try {
                this.field.setInt(obj2, this.field.getInt(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (int)");
                throw kryoException;
            }
        }
    }

    static final class FloatReflectField extends FieldSerializer.CachedField {
        public FloatReflectField(Field field) {
            super(field);
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void write(Output output, Object obj) {
            try {
                output.writeFloat(this.field.getFloat(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (float)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void read(Input input, Object obj) {
            try {
                this.field.setFloat(obj, input.readFloat());
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (float)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void copy(Object obj, Object obj2) {
            try {
                this.field.setFloat(obj2, this.field.getFloat(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (float)");
                throw kryoException;
            }
        }
    }

    static final class ShortReflectField extends FieldSerializer.CachedField {
        public ShortReflectField(Field field) {
            super(field);
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void write(Output output, Object obj) {
            try {
                output.writeShort(this.field.getShort(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (short)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void read(Input input, Object obj) {
            try {
                this.field.setShort(obj, input.readShort());
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (short)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void copy(Object obj, Object obj2) {
            try {
                this.field.setShort(obj2, this.field.getShort(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (short)");
                throw kryoException;
            }
        }
    }

    static final class ByteReflectField extends FieldSerializer.CachedField {
        public ByteReflectField(Field field) {
            super(field);
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void write(Output output, Object obj) {
            try {
                output.writeByte(this.field.getByte(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (byte)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void read(Input input, Object obj) {
            try {
                this.field.setByte(obj, input.readByte());
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (byte)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void copy(Object obj, Object obj2) {
            try {
                this.field.setByte(obj2, this.field.getByte(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (byte)");
                throw kryoException;
            }
        }
    }

    static final class BooleanReflectField extends FieldSerializer.CachedField {
        public BooleanReflectField(Field field) {
            super(field);
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void write(Output output, Object obj) {
            try {
                output.writeBoolean(this.field.getBoolean(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (boolean)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void read(Input input, Object obj) {
            try {
                this.field.setBoolean(obj, input.readBoolean());
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (boolean)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void copy(Object obj, Object obj2) {
            try {
                this.field.setBoolean(obj2, this.field.getBoolean(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (boolean)");
                throw kryoException;
            }
        }
    }

    static final class CharReflectField extends FieldSerializer.CachedField {
        public CharReflectField(Field field) {
            super(field);
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void write(Output output, Object obj) {
            try {
                output.writeChar(this.field.getChar(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (char)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void read(Input input, Object obj) {
            try {
                this.field.setChar(obj, input.readChar());
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (char)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void copy(Object obj, Object obj2) {
            try {
                this.field.setChar(obj2, this.field.getChar(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (char)");
                throw kryoException;
            }
        }
    }

    static final class LongReflectField extends FieldSerializer.CachedField {
        public LongReflectField(Field field) {
            super(field);
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void write(Output output, Object obj) {
            try {
                if (this.varEncoding) {
                    output.writeVarLong(this.field.getLong(obj), false);
                } else {
                    output.writeLong(this.field.getLong(obj));
                }
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (long)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void read(Input input, Object obj) {
            try {
                if (this.varEncoding) {
                    this.field.setLong(obj, input.readVarLong(false));
                } else {
                    this.field.setLong(obj, input.readLong());
                }
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (long)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void copy(Object obj, Object obj2) {
            try {
                this.field.setLong(obj2, this.field.getLong(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (long)");
                throw kryoException;
            }
        }
    }

    static final class DoubleReflectField extends FieldSerializer.CachedField {
        public DoubleReflectField(Field field) {
            super(field);
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void write(Output output, Object obj) {
            try {
                output.writeDouble(this.field.getDouble(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (double)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void read(Input input, Object obj) {
            try {
                this.field.setDouble(obj, input.readDouble());
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (double)");
                throw kryoException;
            }
        }

        @Override // com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField
        public void copy(Object obj, Object obj2) {
            try {
                this.field.setDouble(obj2, this.field.getDouble(obj));
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(this.f134name + " (double)");
                throw kryoException;
            }
        }
    }
}
