package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.SerializerFactory;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes8.dex */
public class CollectionSerializer<T extends Collection> extends Serializer<T> {
    private Class elementClass;
    private Serializer elementSerializer;
    private boolean elementsCanBeNull = true;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BindCollection {
        Class elementClass() default Object.class;

        Class<? extends Serializer> elementSerializer() default Serializer.class;

        Class<? extends SerializerFactory> elementSerializerFactory() default SerializerFactory.class;

        boolean elementsCanBeNull() default true;
    }

    protected void writeHeader(Kryo kryo, Output output, T t) {
    }

    public CollectionSerializer() {
        setAcceptsNull(true);
    }

    public void setElementsCanBeNull(boolean z) {
        this.elementsCanBeNull = z;
    }

    public void setElementClass(Class cls) {
        this.elementClass = cls;
    }

    public Class getElementClass() {
        return this.elementClass;
    }

    public void setElementClass(Class cls, Serializer serializer) {
        this.elementClass = cls;
        this.elementSerializer = serializer;
    }

    public void setElementSerializer(Serializer serializer) {
        this.elementSerializer = serializer;
    }

    public Serializer getElementSerializer() {
        return this.elementSerializer;
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, T t) {
        Class clsNextGenericClass;
        boolean z = false;
        if (t == null) {
            output.writeByte((byte) 0);
            return;
        }
        int size = t.size();
        if (size == 0) {
            output.writeByte(1);
            writeHeader(kryo, output, t);
            return;
        }
        boolean z2 = this.elementsCanBeNull;
        Serializer serializer = this.elementSerializer;
        if (serializer == null && (clsNextGenericClass = kryo.getGenerics().nextGenericClass()) != null && kryo.isFinal(clsNextGenericClass)) {
            serializer = kryo.getSerializer(clsNextGenericClass);
        }
        try {
            if (serializer != null) {
                if (z2) {
                    Iterator it = t.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            output.writeVarIntFlag(false, size + 1, true);
                            break;
                        } else if (it.next() == null) {
                            output.writeVarIntFlag(true, size + 1, true);
                        }
                    }
                    writeHeader(kryo, output, t);
                    z2 = z;
                } else {
                    output.writeVarInt(size + 1, true);
                }
                z = z2;
                writeHeader(kryo, output, t);
                z2 = z;
            } else {
                Iterator it2 = t.iterator();
                Class<?> cls = null;
                boolean z3 = false;
                while (true) {
                    if (!it2.hasNext()) {
                        output.writeVarIntFlag(true, size + 1, true);
                        writeHeader(kryo, output, t);
                        if (cls == null) {
                            output.writeByte((byte) 0);
                            kryo.getGenerics().popGenericType();
                            return;
                        }
                        kryo.writeClass(output, cls);
                        serializer = kryo.getSerializer(cls);
                        if (!z2) {
                            break;
                        }
                        output.writeBoolean(z3);
                        z2 = z3;
                        break;
                    }
                    Object next = it2.next();
                    if (next == null) {
                        z3 = true;
                    } else if (cls == null) {
                        cls = next.getClass();
                    } else if (next.getClass() != cls) {
                        output.writeVarIntFlag(false, size + 1, true);
                        writeHeader(kryo, output, t);
                        break;
                    }
                }
            }
            if (serializer == null) {
                Iterator it3 = t.iterator();
                while (it3.hasNext()) {
                    kryo.writeClassAndObject(output, it3.next());
                }
            } else if (z2) {
                Iterator it4 = t.iterator();
                while (it4.hasNext()) {
                    kryo.writeObjectOrNull(output, it4.next(), serializer);
                }
            } else {
                Iterator it5 = t.iterator();
                while (it5.hasNext()) {
                    kryo.writeObject(output, it5.next(), serializer);
                }
            }
            kryo.getGenerics().popGenericType();
        } catch (Throwable th) {
            kryo.getGenerics().popGenericType();
            throw th;
        }
    }

    protected T create(Kryo kryo, Input input, Class<? extends T> cls, int i) {
        if (cls == ArrayList.class) {
            return new ArrayList(i);
        }
        if (cls == HashSet.class) {
            return new HashSet(Math.max(((int) (i / 0.75f)) + 1, 16));
        }
        T t = (T) kryo.newInstance(cls);
        if (t instanceof ArrayList) {
            ((ArrayList) t).ensureCapacity(i);
        }
        return t;
    }

    /* JADX WARN: Code duplicated, block: B:37:0x008b A[DONT_INVERT, PHI: r0 r1 r2 r5 r10
      0x008b: PHI (r0v4 java.lang.Class) = (r0v1 java.lang.Class), (r0v3 java.lang.Class), (r0v3 java.lang.Class), (r0v1 java.lang.Class) binds: [B:28:0x0065, B:35:0x0085, B:36:0x0087, B:19:0x0044] A[DONT_GENERATE, DONT_INLINE]
      0x008b: PHI (r1v3 com.esotericsoftware.kryo.Serializer) = 
      (r1v1 com.esotericsoftware.kryo.Serializer)
      (r1v2 com.esotericsoftware.kryo.Serializer)
      (r1v2 com.esotericsoftware.kryo.Serializer)
      (r1v1 com.esotericsoftware.kryo.Serializer)
     binds: [B:28:0x0065, B:35:0x0085, B:36:0x0087, B:19:0x0044] A[DONT_GENERATE, DONT_INLINE]
      0x008b: PHI (r2v2 boolean) = (r2v0 boolean), (r2v0 boolean), (r2v1 boolean), (r2v5 boolean) binds: [B:28:0x0065, B:35:0x0085, B:36:0x0087, B:19:0x0044] A[DONT_GENERATE, DONT_INLINE]
      0x008b: PHI (r5v3 int) = (r5v2 int), (r5v2 int), (r5v2 int), (r5v6 int) binds: [B:28:0x0065, B:35:0x0085, B:36:0x0087, B:19:0x0044] A[DONT_GENERATE, DONT_INLINE]
      0x008b: PHI (r10v2 T extends java.util.Collection) = 
      (r10v1 T extends java.util.Collection)
      (r10v1 T extends java.util.Collection)
      (r10v1 T extends java.util.Collection)
      (r10v4 T extends java.util.Collection)
     binds: [B:28:0x0065, B:35:0x0085, B:36:0x0087, B:19:0x0044] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:38:0x008d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:39:0x008f A[ADDED_TO_REGION, LOOP:0: B:39:0x008f->B:40:0x0091, LOOP_START, PHI: r4
      0x008f: PHI (r4v7 int) = (r4v0 int), (r4v8 int) binds: [B:38:0x008d, B:40:0x0091] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:40:0x0091 A[Catch: all -> 0x00b3, LOOP:0: B:39:0x008f->B:40:0x0091, LOOP_END, TryCatch #0 {all -> 0x00b3, blocks: (B:9:0x001b, B:12:0x0024, B:18:0x003d, B:40:0x0091, B:42:0x009d, B:44:0x00a9, B:13:0x002d, B:22:0x004e, B:25:0x0059, B:29:0x0067, B:32:0x006f, B:33:0x0075, B:34:0x007d, B:36:0x0087), top: B:49:0x001b }] */
    /* JADX WARN: Code duplicated, block: B:41:0x009b A[ADDED_TO_REGION, LOOP:1: B:41:0x009b->B:42:0x009d, LOOP_START, PHI: r4
      0x009b: PHI (r4v5 int) = (r4v0 int), (r4v6 int) binds: [B:38:0x008d, B:42:0x009d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:42:0x009d A[Catch: all -> 0x00b3, LOOP:1: B:41:0x009b->B:42:0x009d, LOOP_END, TryCatch #0 {all -> 0x00b3, blocks: (B:9:0x001b, B:12:0x0024, B:18:0x003d, B:40:0x0091, B:42:0x009d, B:44:0x00a9, B:13:0x002d, B:22:0x004e, B:25:0x0059, B:29:0x0067, B:32:0x006f, B:33:0x0075, B:34:0x007d, B:36:0x0087), top: B:49:0x001b }] */
    /* JADX WARN: Code duplicated, block: B:43:0x00a7 A[ADDED_TO_REGION, LOOP:2: B:43:0x00a7->B:45:0x00b0, LOOP_START, PHI: r4
      0x00a7: PHI (r4v3 int) = (r4v0 int), (r4v4 int) binds: [B:37:0x008b, B:45:0x00b0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:44:0x00a9 A[Catch: all -> 0x00b3, TRY_LEAVE, TryCatch #0 {all -> 0x00b3, blocks: (B:9:0x001b, B:12:0x0024, B:18:0x003d, B:40:0x0091, B:42:0x009d, B:44:0x00a9, B:13:0x002d, B:22:0x004e, B:25:0x0059, B:29:0x0067, B:32:0x006f, B:33:0x0075, B:34:0x007d, B:36:0x0087), top: B:49:0x001b }] */
    @Override // com.esotericsoftware.kryo.Serializer
    public T read(Kryo kryo, Input input, Class<? extends T> cls) {
        int i;
        T t;
        int varInt;
        Class clsNextGenericClass;
        Class type = this.elementClass;
        Serializer serializer = this.elementSerializer;
        if (serializer == null && (clsNextGenericClass = kryo.getGenerics().nextGenericClass()) != null && kryo.isFinal(clsNextGenericClass)) {
            serializer = kryo.getSerializer(clsNextGenericClass);
            type = clsNextGenericClass;
        }
        try {
            boolean varIntFlag = this.elementsCanBeNull;
            int i2 = 0;
            if (serializer == null) {
                boolean varIntFlag2 = input.readVarIntFlag();
                int varIntFlag3 = input.readVarIntFlag(true);
                if (varIntFlag3 != 0) {
                    i = varIntFlag3 - 1;
                    t = (T) create(kryo, input, cls, i);
                    kryo.reference(t);
                    if (i != 0) {
                        if (varIntFlag2) {
                            Registration registration = kryo.readClass(input);
                            if (registration == null) {
                                while (i2 < i) {
                                    t.add(null);
                                    i2++;
                                }
                                kryo.getGenerics().popGenericType();
                            } else {
                                type = registration.getType();
                                serializer = kryo.getSerializer(type);
                                if (varIntFlag) {
                                    varIntFlag = input.readBoolean();
                                }
                                if (serializer == null) {
                                    while (i2 < i) {
                                        t.add(kryo.readClassAndObject(input));
                                        i2++;
                                    }
                                } else if (varIntFlag) {
                                    while (i2 < i) {
                                        t.add(kryo.readObjectOrNull(input, type, serializer));
                                        i2++;
                                    }
                                } else {
                                    while (i2 < i) {
                                        t.add(kryo.readObject(input, type, serializer));
                                        i2++;
                                    }
                                }
                            }
                        } else if (serializer == null) {
                            while (i2 < i) {
                                t.add(kryo.readClassAndObject(input));
                                i2++;
                            }
                        } else if (varIntFlag) {
                            while (i2 < i) {
                                t.add(kryo.readObjectOrNull(input, type, serializer));
                                i2++;
                            }
                        } else {
                            while (i2 < i) {
                                t.add(kryo.readObject(input, type, serializer));
                                i2++;
                            }
                        }
                    }
                } else {
                    kryo.getGenerics().popGenericType();
                    return null;
                }
            } else {
                if (varIntFlag) {
                    varIntFlag = input.readVarIntFlag();
                    varInt = input.readVarIntFlag(true);
                } else {
                    varInt = input.readVarInt(true);
                }
                if (varInt != 0) {
                    i = varInt - 1;
                    t = (T) create(kryo, input, cls, i);
                    kryo.reference(t);
                    if (i != 0) {
                        if (serializer == null) {
                            while (i2 < i) {
                                t.add(kryo.readClassAndObject(input));
                                i2++;
                            }
                        } else if (varIntFlag) {
                            while (i2 < i) {
                                t.add(kryo.readObjectOrNull(input, type, serializer));
                                i2++;
                            }
                        } else {
                            while (i2 < i) {
                                t.add(kryo.readObject(input, type, serializer));
                                i2++;
                            }
                        }
                    }
                }
                kryo.getGenerics().popGenericType();
                return null;
            }
            kryo.getGenerics().popGenericType();
            return t;
        } catch (Throwable th) {
            kryo.getGenerics().popGenericType();
            throw th;
        }
    }

    protected T createCopy(Kryo kryo, T t) {
        return (T) kryo.newInstance(t.getClass());
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public T copy(Kryo kryo, T t) {
        T t2 = (T) createCopy(kryo, t);
        kryo.reference(t2);
        Iterator it = t.iterator();
        while (it.hasNext()) {
            t2.add(kryo.copy(it.next()));
        }
        return t2;
    }
}
