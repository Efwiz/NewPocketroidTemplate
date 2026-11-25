package com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.ClosureSerializer;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import com.esotericsoftware.kryo.serializers.ImmutableCollectionsSerializers;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import com.esotericsoftware.kryo.serializers.OptionalSerializers;
import com.esotericsoftware.kryo.serializers.RecordSerializer;
import com.esotericsoftware.kryo.serializers.TimeSerializers;
import com.esotericsoftware.kryo.util.DefaultClassResolver;
import com.esotericsoftware.kryo.util.DefaultGenerics;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.util.Generics;
import com.esotericsoftware.kryo.util.IdentityMap;
import com.esotericsoftware.kryo.util.IntArray;
import com.esotericsoftware.kryo.util.MapReferenceResolver;
import com.esotericsoftware.kryo.util.NoGenerics;
import com.esotericsoftware.kryo.util.ObjectMap;
import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Currency;
import java.util.Date;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.strategy.InstantiatorStrategy;

/* JADX INFO: loaded from: classes8.dex */
public class Kryo {
    private static final int DEFAULT_SERIALIZER_SIZE = 68;
    public static final byte NOT_NULL = 1;
    private static final int NO_REF = -2;
    public static final byte NULL = 0;
    private static final int REF = -1;
    private boolean autoReset;
    private ClassLoader classLoader;
    private final ClassResolver classResolver;
    private ObjectMap context;
    private int copyDepth;
    private boolean copyReferences;
    private boolean copyShallow;
    private SerializerFactory defaultSerializer;
    private final ArrayList<DefaultSerializerEntry> defaultSerializers;
    private int depth;
    private Generics generics;
    private ObjectMap graphContext;
    private final int lowPriorityDefaultSerializerCount;
    private int maxDepth;
    private Object needsCopyReference;
    private int nextRegisterID;
    private IdentityMap originalToCopy;
    private Object readObject;
    private final IntArray readReferenceIds;
    private ReferenceResolver referenceResolver;
    private boolean references;
    private boolean registrationRequired;
    private InstantiatorStrategy strategy;
    private volatile Thread thread;
    private boolean warnUnregisteredClasses;

    public Kryo() {
        this(new DefaultClassResolver(), null);
    }

    public Kryo(ReferenceResolver referenceResolver) {
        this(new DefaultClassResolver(), referenceResolver);
    }

    public Kryo(ClassResolver classResolver, ReferenceResolver referenceResolver) {
        this.defaultSerializer = new SerializerFactory.FieldSerializerFactory();
        ArrayList<DefaultSerializerEntry> arrayList = new ArrayList<>(68);
        this.defaultSerializers = arrayList;
        this.classLoader = getClass().getClassLoader();
        this.strategy = new DefaultInstantiatorStrategy();
        this.registrationRequired = true;
        this.maxDepth = Integer.MAX_VALUE;
        this.autoReset = true;
        this.readReferenceIds = new IntArray(0);
        this.copyReferences = true;
        this.generics = new DefaultGenerics(this);
        if (classResolver == null) {
            throw new IllegalArgumentException("classResolver cannot be null.");
        }
        this.classResolver = classResolver;
        classResolver.setKryo(this);
        this.referenceResolver = referenceResolver;
        if (referenceResolver != null) {
            referenceResolver.setKryo(this);
            this.references = true;
        }
        addDefaultSerializer(byte[].class, DefaultArraySerializers.ByteArraySerializer.class);
        addDefaultSerializer(char[].class, DefaultArraySerializers.CharArraySerializer.class);
        addDefaultSerializer(short[].class, DefaultArraySerializers.ShortArraySerializer.class);
        addDefaultSerializer(int[].class, DefaultArraySerializers.IntArraySerializer.class);
        addDefaultSerializer(long[].class, DefaultArraySerializers.LongArraySerializer.class);
        addDefaultSerializer(float[].class, DefaultArraySerializers.FloatArraySerializer.class);
        addDefaultSerializer(double[].class, DefaultArraySerializers.DoubleArraySerializer.class);
        addDefaultSerializer(boolean[].class, DefaultArraySerializers.BooleanArraySerializer.class);
        addDefaultSerializer(String[].class, DefaultArraySerializers.StringArraySerializer.class);
        addDefaultSerializer(Object[].class, DefaultArraySerializers.ObjectArraySerializer.class);
        addDefaultSerializer(BigInteger.class, DefaultSerializers.BigIntegerSerializer.class);
        addDefaultSerializer(BigDecimal.class, DefaultSerializers.BigDecimalSerializer.class);
        addDefaultSerializer(Class.class, DefaultSerializers.ClassSerializer.class);
        addDefaultSerializer(Date.class, DefaultSerializers.DateSerializer.class);
        addDefaultSerializer(Enum.class, DefaultSerializers.EnumSerializer.class);
        addDefaultSerializer(EnumSet.class, DefaultSerializers.EnumSetSerializer.class);
        addDefaultSerializer(Currency.class, DefaultSerializers.CurrencySerializer.class);
        addDefaultSerializer(StringBuffer.class, DefaultSerializers.StringBufferSerializer.class);
        addDefaultSerializer(StringBuilder.class, DefaultSerializers.StringBuilderSerializer.class);
        addDefaultSerializer(Collections.EMPTY_LIST.getClass(), DefaultSerializers.CollectionsEmptyListSerializer.class);
        addDefaultSerializer(Collections.EMPTY_MAP.getClass(), DefaultSerializers.CollectionsEmptyMapSerializer.class);
        addDefaultSerializer(Collections.EMPTY_SET.getClass(), DefaultSerializers.CollectionsEmptySetSerializer.class);
        addDefaultSerializer(Collections.singletonList(null).getClass(), DefaultSerializers.CollectionsSingletonListSerializer.class);
        addDefaultSerializer(Collections.singletonMap(null, null).getClass(), DefaultSerializers.CollectionsSingletonMapSerializer.class);
        addDefaultSerializer(Collections.singleton(null).getClass(), DefaultSerializers.CollectionsSingletonSetSerializer.class);
        addDefaultSerializer(TreeSet.class, DefaultSerializers.TreeSetSerializer.class);
        addDefaultSerializer(Collection.class, CollectionSerializer.class);
        addDefaultSerializer(ConcurrentSkipListMap.class, DefaultSerializers.ConcurrentSkipListMapSerializer.class);
        addDefaultSerializer(TreeMap.class, DefaultSerializers.TreeMapSerializer.class);
        addDefaultSerializer(Map.class, MapSerializer.class);
        addDefaultSerializer(TimeZone.class, DefaultSerializers.TimeZoneSerializer.class);
        addDefaultSerializer(Calendar.class, DefaultSerializers.CalendarSerializer.class);
        addDefaultSerializer(Locale.class, DefaultSerializers.LocaleSerializer.class);
        addDefaultSerializer(Charset.class, DefaultSerializers.CharsetSerializer.class);
        addDefaultSerializer(URL.class, DefaultSerializers.URLSerializer.class);
        addDefaultSerializer(Arrays.asList(new Object[0]).getClass(), DefaultSerializers.ArraysAsListSerializer.class);
        addDefaultSerializer(Void.TYPE, new DefaultSerializers.VoidSerializer());
        addDefaultSerializer(PriorityQueue.class, new DefaultSerializers.PriorityQueueSerializer());
        addDefaultSerializer(BitSet.class, new DefaultSerializers.BitSetSerializer());
        addDefaultSerializer(KryoSerializable.class, DefaultSerializers.KryoSerializableSerializer.class);
        OptionalSerializers.addDefaultSerializers(this);
        TimeSerializers.addDefaultSerializers(this);
        ImmutableCollectionsSerializers.addDefaultSerializers(this);
        if (Util.isClassAvailable(CommonClassNames.JAVA_LANG_RECORD)) {
            addDefaultSerializer(CommonClassNames.JAVA_LANG_RECORD, RecordSerializer.class);
        }
        this.lowPriorityDefaultSerializerCount = arrayList.size();
        register(Integer.TYPE, new DefaultSerializers.IntSerializer());
        register(String.class, new DefaultSerializers.StringSerializer());
        register(Float.TYPE, new DefaultSerializers.FloatSerializer());
        register(Boolean.TYPE, new DefaultSerializers.BooleanSerializer());
        register(Byte.TYPE, new DefaultSerializers.ByteSerializer());
        register(Character.TYPE, new DefaultSerializers.CharSerializer());
        register(Short.TYPE, new DefaultSerializers.ShortSerializer());
        register(Long.TYPE, new DefaultSerializers.LongSerializer());
        register(Double.TYPE, new DefaultSerializers.DoubleSerializer());
    }

    public void setDefaultSerializer(SerializerFactory serializerFactory) {
        if (serializerFactory == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        this.defaultSerializer = serializerFactory;
    }

    public void setDefaultSerializer(Class<? extends Serializer> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        this.defaultSerializer = new SerializerFactory.ReflectionSerializerFactory(cls);
    }

    public void addDefaultSerializer(Class cls, Serializer serializer) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        insertDefaultSerializer(cls, new SerializerFactory.SingletonSerializerFactory(serializer));
    }

    public void addDefaultSerializer(Class cls, SerializerFactory serializerFactory) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (serializerFactory == null) {
            throw new IllegalArgumentException("serializerFactory cannot be null.");
        }
        insertDefaultSerializer(cls, serializerFactory);
    }

    private void addDefaultSerializer(String str, Class<? extends Serializer> cls) {
        try {
            addDefaultSerializer(Class.forName(str), cls);
        } catch (ClassNotFoundException unused) {
            throw new KryoException("default serializer cannot be added: " + str);
        }
    }

    public void addDefaultSerializer(Class cls, Class<? extends Serializer> cls2) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (cls2 == null) {
            throw new IllegalArgumentException("serializerClass cannot be null.");
        }
        insertDefaultSerializer(cls, new SerializerFactory.ReflectionSerializerFactory(cls2));
    }

    private int insertDefaultSerializer(Class cls, SerializerFactory serializerFactory) {
        int size = this.defaultSerializers.size() - this.lowPriorityDefaultSerializerCount;
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (cls.isAssignableFrom(this.defaultSerializers.get(i2).type)) {
                i = i2 + 1;
            }
        }
        this.defaultSerializers.add(i, new DefaultSerializerEntry(cls, serializerFactory));
        return i;
    }

    public Serializer getDefaultSerializer(Class cls) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        Serializer defaultSerializerForAnnotatedType = getDefaultSerializerForAnnotatedType(cls);
        if (defaultSerializerForAnnotatedType != null) {
            return defaultSerializerForAnnotatedType;
        }
        int size = this.defaultSerializers.size();
        for (int i = 0; i < size; i++) {
            DefaultSerializerEntry defaultSerializerEntry = this.defaultSerializers.get(i);
            if (defaultSerializerEntry.type.isAssignableFrom(cls) && defaultSerializerEntry.serializerFactory.isSupported(cls)) {
                return defaultSerializerEntry.serializerFactory.newSerializer(this, cls);
            }
        }
        return newDefaultSerializer(cls);
    }

    protected Serializer getDefaultSerializerForAnnotatedType(Class cls) {
        if (!cls.isAnnotationPresent(DefaultSerializer.class)) {
            return null;
        }
        DefaultSerializer defaultSerializer = (DefaultSerializer) cls.getAnnotation(DefaultSerializer.class);
        return Util.newFactory(defaultSerializer.serializerFactory(), defaultSerializer.value()).newSerializer(this, cls);
    }

    protected Serializer newDefaultSerializer(Class cls) {
        return this.defaultSerializer.newSerializer(this, cls);
    }

    public Registration register(Class cls) {
        Registration registration = this.classResolver.getRegistration(cls);
        return registration != null ? registration : register(cls, getDefaultSerializer(cls));
    }

    public Registration register(Class cls, int i) {
        Registration registration = this.classResolver.getRegistration(cls);
        return registration != null ? registration : register(cls, getDefaultSerializer(cls), i);
    }

    public Registration register(Class cls, Serializer serializer) {
        Registration registration = this.classResolver.getRegistration(cls);
        if (registration != null) {
            registration.setSerializer(serializer);
            return registration;
        }
        return this.classResolver.register(new Registration(cls, serializer, getNextRegistrationId()));
    }

    public Registration register(Class cls, Serializer serializer, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("id must be >= 0: " + i);
        }
        return register(new Registration(cls, serializer, i));
    }

    public Registration register(Registration registration) {
        int id2 = registration.getId();
        if (id2 < 0) {
            throw new IllegalArgumentException("id must be > 0: " + id2);
        }
        Registration registrationUnregister = this.classResolver.unregister(id2);
        if (Log.DEBUG && registrationUnregister != null && registrationUnregister.getType() != registration.getType()) {
            Log.debug("kryo", "Registration overwritten: " + registrationUnregister + " -> " + registration);
        }
        return this.classResolver.register(registration);
    }

    public int getNextRegistrationId() {
        while (true) {
            int i = this.nextRegisterID;
            if (i != -2) {
                if (this.classResolver.getRegistration(i) == null) {
                    return this.nextRegisterID;
                }
                this.nextRegisterID++;
            } else {
                throw new KryoException("No registration IDs are available.");
            }
        }
    }

    public Registration getRegistration(Class cls) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        Registration registration = this.classResolver.getRegistration(cls);
        if (registration != null) {
            return registration;
        }
        if (Proxy.isProxyClass(cls)) {
            registration = getRegistration(InvocationHandler.class);
        } else if (!cls.isEnum() && Enum.class.isAssignableFrom(cls) && cls != Enum.class) {
            while (true) {
                cls = cls.getSuperclass();
                if (cls == null) {
                    break;
                }
                if (cls.isEnum()) {
                    registration = this.classResolver.getRegistration(cls);
                    break;
                }
            }
        } else if (EnumSet.class.isAssignableFrom(cls)) {
            registration = this.classResolver.getRegistration(EnumSet.class);
        } else if (isClosure(cls)) {
            registration = this.classResolver.getRegistration(ClosureSerializer.Closure.class);
        }
        if (registration != null) {
            return registration;
        }
        if (this.registrationRequired) {
            throw new IllegalArgumentException(unregisteredClassMessage(cls));
        }
        if (Log.WARN && this.warnUnregisteredClasses) {
            Log.warn(unregisteredClassMessage(cls));
        }
        return this.classResolver.registerImplicit(cls);
    }

    protected String unregisteredClassMessage(Class cls) {
        return "Class is not registered: " + Util.className(cls) + "\nNote: To register this class use: kryo.register(" + Util.canonicalName(cls) + ".class);";
    }

    public Registration getRegistration(int i) {
        return this.classResolver.getRegistration(i);
    }

    public Serializer getSerializer(Class cls) {
        return getRegistration(cls).getSerializer();
    }

    public Registration writeClass(Output output, Class cls) {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        try {
            return this.classResolver.writeClass(output, cls);
        } finally {
            if (this.depth == 0 && this.autoReset) {
                reset();
            }
        }
    }

    public void writeObject(Output output, Object obj) {
        int i;
        boolean z;
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        if (obj == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }
        beginObject();
        try {
            if (this.references && writeReferenceOrNull(output, obj, false)) {
                if (i == 0) {
                    if (z) {
                        return;
                    } else {
                        return;
                    }
                }
                return;
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", obj, output.position());
            }
            getRegistration(obj.getClass()).getSerializer().write(this, output, obj);
        } finally {
            i = this.depth - 1;
            this.depth = i;
            if (i == 0 && this.autoReset) {
                reset();
            }
        }
    }

    public void writeObject(Output output, Object obj, Serializer serializer) {
        int i;
        boolean z;
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        if (obj == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        beginObject();
        try {
            if (this.references && writeReferenceOrNull(output, obj, false)) {
                if (i == 0) {
                    if (z) {
                        return;
                    } else {
                        return;
                    }
                }
                return;
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", obj, output.position());
            }
            serializer.write(this, output, obj);
        } finally {
            i = this.depth - 1;
            this.depth = i;
            if (i == 0 && this.autoReset) {
                reset();
            }
        }
    }

    public void writeObjectOrNull(Output output, Object obj, Class cls) {
        int i;
        boolean z;
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        beginObject();
        try {
            Serializer serializer = getRegistration(cls).getSerializer();
            if (this.references) {
                if (writeReferenceOrNull(output, obj, true)) {
                    if (i == 0) {
                        if (z) {
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                }
            } else if (!serializer.getAcceptsNull()) {
                if (obj == null) {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log("Write", obj, output.position());
                    }
                    output.writeByte((byte) 0);
                    if (i == 0) {
                        if (z) {
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                }
                if (Log.TRACE) {
                    Log.trace("kryo", "Write: <not null>" + Util.pos(output.position()));
                }
                output.writeByte((byte) 1);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", obj, output.position());
            }
            serializer.write(this, output, obj);
        } finally {
            i = this.depth - 1;
            this.depth = i;
            if (i == 0 && this.autoReset) {
                reset();
            }
        }
    }

    public void writeObjectOrNull(Output output, Object obj, Serializer serializer) {
        int i;
        boolean z;
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        beginObject();
        try {
            if (this.references) {
                if (writeReferenceOrNull(output, obj, true)) {
                    if (i == 0) {
                        if (z) {
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                }
            } else if (!serializer.getAcceptsNull()) {
                if (obj == null) {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log("Write", null, output.position());
                    }
                    output.writeByte((byte) 0);
                    if (i == 0) {
                        if (z) {
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                }
                if (Log.TRACE) {
                    Log.trace("kryo", "Write: <not null>" + Util.pos(output.position()));
                }
                output.writeByte((byte) 1);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", obj, output.position());
            }
            serializer.write(this, output, obj);
        } finally {
            i = this.depth - 1;
            this.depth = i;
            if (i == 0 && this.autoReset) {
                reset();
            }
        }
    }

    public void writeClassAndObject(Output output, Object obj) {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        beginObject();
        try {
            if (obj == null) {
                writeClass(output, null);
                int i = this.depth - 1;
                this.depth = i;
                if (i == 0 && this.autoReset) {
                    reset();
                    return;
                }
                return;
            }
            Registration registrationWriteClass = writeClass(output, obj.getClass());
            if (!this.references || !writeReferenceOrNull(output, obj, false)) {
                if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                    Util.log("Write", obj, output.position());
                }
                registrationWriteClass.getSerializer().write(this, output, obj);
                int i2 = this.depth - 1;
                this.depth = i2;
                if (i2 == 0 && this.autoReset) {
                    reset();
                    return;
                }
                return;
            }
            int i3 = this.depth - 1;
            this.depth = i3;
            if (i3 == 0 && this.autoReset) {
                reset();
            }
        } catch (Throwable th) {
            int i4 = this.depth - 1;
            this.depth = i4;
            if (i4 == 0 && this.autoReset) {
                reset();
            }
            throw th;
        }
    }

    boolean writeReferenceOrNull(Output output, Object obj, boolean z) {
        if (obj == null) {
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", null, output.position());
            }
            output.writeByte((byte) 0);
            return true;
        }
        if (!this.referenceResolver.useReferences(obj.getClass())) {
            if (z) {
                if (Log.TRACE) {
                    Log.trace("kryo", "Write: <not null>" + Util.pos(output.position()));
                }
                output.writeByte((byte) 1);
            }
            return false;
        }
        int writtenId = this.referenceResolver.getWrittenId(obj);
        if (writtenId != -1) {
            if (Log.DEBUG) {
                Log.debug("kryo", "Write reference " + writtenId + ": " + Util.string(obj) + Util.pos(output.position()));
            }
            output.writeVarInt(writtenId + 2, true);
            return true;
        }
        int iAddWrittenObject = this.referenceResolver.addWrittenObject(obj);
        if (Log.TRACE) {
            Log.trace("kryo", "Write: <not null>" + Util.pos(output.position()));
        }
        output.writeByte((byte) 1);
        if (Log.TRACE) {
            Log.trace("kryo", "Write initial reference " + iAddWrittenObject + ": " + Util.string(obj) + Util.pos(output.position()));
        }
        return false;
    }

    public Registration readClass(Input input) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        try {
            return this.classResolver.readClass(input);
        } finally {
            if (this.depth == 0 && this.autoReset) {
                reset();
            }
        }
    }

    public <T> T readObject(Input input, Class<T> cls) {
        T t;
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        beginObject();
        try {
            if (this.references) {
                int referenceOrNull = readReferenceOrNull(input, cls, false);
                if (referenceOrNull == -1) {
                    return (T) this.readObject;
                }
                t = (T) getRegistration(cls).getSerializer().read(this, input, cls);
                if (referenceOrNull == this.readReferenceIds.size) {
                    reference(t);
                }
            } else {
                t = (T) getRegistration(cls).getSerializer().read(this, input, cls);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Read", t, input.position());
            }
            return t;
        } finally {
            int i = this.depth - 1;
            this.depth = i;
            if (i == 0 && this.autoReset) {
                reset();
            }
        }
    }

    public <T> T readObject(Input input, Class<T> cls, Serializer serializer) {
        T t;
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        beginObject();
        try {
            if (this.references) {
                int referenceOrNull = readReferenceOrNull(input, cls, false);
                if (referenceOrNull == -1) {
                    return (T) this.readObject;
                }
                t = (T) serializer.read(this, input, cls);
                if (referenceOrNull == this.readReferenceIds.size) {
                    reference(t);
                }
            } else {
                t = (T) serializer.read(this, input, cls);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Read", t, input.position());
            }
            return t;
        } finally {
            int i = this.depth - 1;
            this.depth = i;
            if (i == 0 && this.autoReset) {
                reset();
            }
        }
    }

    public <T> T readObjectOrNull(Input input, Class<T> cls) {
        T t;
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        beginObject();
        try {
            if (this.references) {
                int referenceOrNull = readReferenceOrNull(input, cls, true);
                if (referenceOrNull == -1) {
                    return (T) this.readObject;
                }
                t = (T) getRegistration(cls).getSerializer().read(this, input, cls);
                if (referenceOrNull == this.readReferenceIds.size) {
                    reference(t);
                }
            } else {
                Serializer serializer = getRegistration(cls).getSerializer();
                if (!serializer.getAcceptsNull() && input.readByte() == 0) {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log("Read", null, input.position());
                    }
                    return null;
                }
                t = (T) serializer.read(this, input, cls);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Read", t, input.position());
            }
            return t;
        } finally {
            int i = this.depth - 1;
            this.depth = i;
            if (i == 0 && this.autoReset) {
                reset();
            }
        }
    }

    public <T> T readObjectOrNull(Input input, Class<T> cls, Serializer serializer) {
        T t;
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        beginObject();
        try {
            if (this.references) {
                int referenceOrNull = readReferenceOrNull(input, cls, true);
                if (referenceOrNull == -1) {
                    return (T) this.readObject;
                }
                t = (T) serializer.read(this, input, cls);
                if (referenceOrNull == this.readReferenceIds.size) {
                    reference(t);
                }
            } else {
                if (!serializer.getAcceptsNull() && input.readByte() == 0) {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log("Read", null, input.position());
                    }
                    return null;
                }
                t = (T) serializer.read(this, input, cls);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Read", t, input.position());
            }
            return t;
        } finally {
            int i = this.depth - 1;
            this.depth = i;
            if (i == 0 && this.autoReset) {
                reset();
            }
        }
    }

    public Object readClassAndObject(Input input) {
        Object obj;
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        beginObject();
        try {
            Registration registration = readClass(input);
            if (registration == null) {
                return null;
            }
            Class type = registration.getType();
            if (this.references) {
                int referenceOrNull = readReferenceOrNull(input, type, false);
                if (referenceOrNull == -1) {
                    return this.readObject;
                }
                obj = registration.getSerializer().read(this, input, type);
                if (referenceOrNull == this.readReferenceIds.size) {
                    reference(obj);
                }
            } else {
                obj = registration.getSerializer().read(this, input, type);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Read", obj, input.position());
            }
            return obj;
        } finally {
            int i = this.depth - 1;
            this.depth = i;
            if (i == 0 && this.autoReset) {
                reset();
            }
        }
    }

    int readReferenceOrNull(Input input, Class cls, boolean z) {
        int varInt;
        if (cls.isPrimitive()) {
            cls = Util.getWrapperClass(cls);
        }
        boolean zUseReferences = this.referenceResolver.useReferences(cls);
        if (z) {
            varInt = input.readVarInt(true);
            if (varInt == 0) {
                if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                    Util.log("Read", null, input.position());
                }
                this.readObject = null;
                return -1;
            }
            if (!zUseReferences) {
                this.readReferenceIds.add(-2);
                return this.readReferenceIds.size;
            }
        } else {
            if (!zUseReferences) {
                this.readReferenceIds.add(-2);
                return this.readReferenceIds.size;
            }
            varInt = input.readVarInt(true);
        }
        if (varInt == 1) {
            if (Log.TRACE) {
                Log.trace("kryo", "Read: <not null>" + Util.pos(input.position()));
            }
            int iNextReadId = this.referenceResolver.nextReadId(cls);
            if (Log.TRACE) {
                Log.trace("kryo", "Read initial reference " + iNextReadId + ": " + Util.className(cls) + Util.pos(input.position()));
            }
            this.readReferenceIds.add(iNextReadId);
            return this.readReferenceIds.size;
        }
        int i = varInt - 2;
        this.readObject = this.referenceResolver.getReadObject(cls, i);
        if (Log.DEBUG) {
            Log.debug("kryo", "Read reference " + i + ": " + Util.string(this.readObject) + Util.pos(input.position()));
        }
        return -1;
    }

    public void reference(Object obj) {
        int iPop;
        if (this.copyDepth > 0) {
            Object obj2 = this.needsCopyReference;
            if (obj2 != null) {
                if (obj == null) {
                    throw new IllegalArgumentException("object cannot be null.");
                }
                this.originalToCopy.put(obj2, obj);
                this.needsCopyReference = null;
                return;
            }
            return;
        }
        if (!this.references || obj == null || (iPop = this.readReferenceIds.pop()) == -2) {
            return;
        }
        this.referenceResolver.setReadObject(iPop, obj);
    }

    public void reset() {
        this.depth = 0;
        ObjectMap objectMap = this.graphContext;
        if (objectMap != null) {
            objectMap.clear(2048);
        }
        this.classResolver.reset();
        if (this.references) {
            this.referenceResolver.reset();
            this.readObject = null;
        }
        this.copyDepth = 0;
        IdentityMap identityMap = this.originalToCopy;
        if (identityMap != null) {
            identityMap.clear(2048);
        }
        if (Log.TRACE) {
            Log.trace("kryo", "Object graph complete.");
        }
    }

    public <T> T copy(T t) {
        T t2;
        if (t == null) {
            return null;
        }
        if (this.copyShallow) {
            return t;
        }
        this.copyDepth++;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            T t3 = (T) this.originalToCopy.get(t);
            if (t3 != null) {
                return t3;
            }
            if (this.copyReferences) {
                this.needsCopyReference = t;
            }
            if (t instanceof KryoCopyable) {
                t2 = (T) ((KryoCopyable) t).copy(this);
            } else {
                t2 = (T) getSerializer(t.getClass()).copy(this, t);
            }
            if (this.needsCopyReference != null) {
                reference(t2);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Copy", t2, -1);
            }
            return t2;
        } finally {
            int i = this.copyDepth - 1;
            this.copyDepth = i;
            if (i == 0) {
                reset();
            }
        }
    }

    public <T> T copy(T t, Serializer serializer) {
        T t2;
        if (t == null) {
            return null;
        }
        if (this.copyShallow) {
            return t;
        }
        this.copyDepth++;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            T t3 = (T) this.originalToCopy.get(t);
            if (t3 != null) {
                return t3;
            }
            if (this.copyReferences) {
                this.needsCopyReference = t;
            }
            if (t instanceof KryoCopyable) {
                t2 = (T) ((KryoCopyable) t).copy(this);
            } else {
                t2 = (T) serializer.copy(this, t);
            }
            if (this.needsCopyReference != null) {
                reference(t2);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Copy", t2, -1);
            }
            return t2;
        } finally {
            int i = this.copyDepth - 1;
            this.copyDepth = i;
            if (i == 0) {
                reset();
            }
        }
    }

    public <T> T copyShallow(T t) {
        T t2;
        if (t == null) {
            return null;
        }
        this.copyDepth++;
        this.copyShallow = true;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            T t3 = (T) this.originalToCopy.get(t);
            if (t3 != null) {
                return t3;
            }
            if (this.copyReferences) {
                this.needsCopyReference = t;
            }
            if (t instanceof KryoCopyable) {
                t2 = (T) ((KryoCopyable) t).copy(this);
            } else {
                t2 = (T) getSerializer(t.getClass()).copy(this, t);
            }
            if (this.needsCopyReference != null) {
                reference(t2);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Shallow copy", t2, -1);
            }
            return t2;
        } finally {
            this.copyShallow = false;
            int i = this.copyDepth - 1;
            this.copyDepth = i;
            if (i == 0) {
                reset();
            }
        }
    }

    public <T> T copyShallow(T t, Serializer serializer) {
        T t2;
        if (t == null) {
            return null;
        }
        this.copyDepth++;
        this.copyShallow = true;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            T t3 = (T) this.originalToCopy.get(t);
            if (t3 != null) {
                return t3;
            }
            if (this.copyReferences) {
                this.needsCopyReference = t;
            }
            if (t instanceof KryoCopyable) {
                t2 = (T) ((KryoCopyable) t).copy(this);
            } else {
                t2 = (T) serializer.copy(this, t);
            }
            if (this.needsCopyReference != null) {
                reference(t2);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Shallow copy", t2, -1);
            }
            return t2;
        } finally {
            this.copyShallow = false;
            int i = this.copyDepth - 1;
            this.copyDepth = i;
            if (i == 0) {
                reset();
            }
        }
    }

    private void beginObject() {
        if (Log.DEBUG) {
            if (this.depth == 0) {
                this.thread = Thread.currentThread();
            } else if (this.thread != Thread.currentThread()) {
                throw new ConcurrentModificationException("Kryo must not be accessed concurrently by multiple threads.");
            }
        }
        int i = this.depth;
        if (i == this.maxDepth) {
            throw new KryoException("Max depth exceeded: " + this.depth);
        }
        this.depth = i + 1;
    }

    public ClassResolver getClassResolver() {
        return this.classResolver;
    }

    public ReferenceResolver getReferenceResolver() {
        return this.referenceResolver;
    }

    public void setClassLoader(ClassLoader classLoader) {
        if (classLoader == null) {
            throw new IllegalArgumentException("classLoader cannot be null.");
        }
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public void setRegistrationRequired(boolean z) {
        this.registrationRequired = z;
        if (Log.TRACE) {
            Log.trace("kryo", "Registration required: " + z);
        }
    }

    public boolean isRegistrationRequired() {
        return this.registrationRequired;
    }

    public void setWarnUnregisteredClasses(boolean z) {
        this.warnUnregisteredClasses = z;
        if (Log.TRACE) {
            Log.trace("kryo", "Warn unregistered classes: " + z);
        }
    }

    public boolean getWarnUnregisteredClasses() {
        return this.warnUnregisteredClasses;
    }

    public boolean setReferences(boolean z) {
        boolean z2 = this.references;
        if (z == z2) {
            return z;
        }
        if (z2) {
            this.referenceResolver.reset();
            this.readObject = null;
        }
        this.references = z;
        if (z && this.referenceResolver == null) {
            this.referenceResolver = new MapReferenceResolver();
        }
        if (Log.TRACE) {
            Log.trace("kryo", "References: " + z);
        }
        return !z;
    }

    public void setCopyReferences(boolean z) {
        this.copyReferences = z;
    }

    public void setReferenceResolver(ReferenceResolver referenceResolver) {
        if (referenceResolver == null) {
            throw new IllegalArgumentException("referenceResolver cannot be null.");
        }
        this.references = true;
        this.referenceResolver = referenceResolver;
        if (Log.TRACE) {
            Log.trace("kryo", "Reference resolver: " + referenceResolver.getClass().getName());
        }
    }

    public boolean getReferences() {
        return this.references;
    }

    public void setInstantiatorStrategy(InstantiatorStrategy instantiatorStrategy) {
        this.strategy = instantiatorStrategy;
    }

    public InstantiatorStrategy getInstantiatorStrategy() {
        return this.strategy;
    }

    protected ObjectInstantiator newInstantiator(Class cls) {
        return this.strategy.newInstantiatorOf(cls);
    }

    public <T> T newInstance(Class<T> cls) {
        Registration registration = getRegistration(cls);
        ObjectInstantiator instantiator = registration.getInstantiator();
        if (instantiator == null) {
            instantiator = newInstantiator(cls);
            registration.setInstantiator(instantiator);
        }
        return (T) instantiator.newInstance();
    }

    public ObjectMap getContext() {
        if (this.context == null) {
            this.context = new ObjectMap();
        }
        return this.context;
    }

    public ObjectMap getGraphContext() {
        if (this.graphContext == null) {
            this.graphContext = new ObjectMap();
        }
        return this.graphContext;
    }

    public int getDepth() {
        return this.depth;
    }

    public IdentityMap getOriginalToCopyMap() {
        return this.originalToCopy;
    }

    public void setAutoReset(boolean z) {
        this.autoReset = z;
    }

    public void setMaxDepth(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxDepth must be > 0.");
        }
        this.maxDepth = i;
    }

    public boolean isFinal(Class cls) {
        if (cls != null) {
            return cls.isArray() ? Modifier.isFinal(Util.getElementClass(cls).getModifiers()) : Modifier.isFinal(cls.getModifiers());
        }
        throw new IllegalArgumentException("type cannot be null.");
    }

    public boolean isClosure(Class cls) {
        if (cls != null) {
            return cls.getName().indexOf(47) >= 0;
        }
        throw new IllegalArgumentException("type cannot be null.");
    }

    public Generics getGenerics() {
        return this.generics;
    }

    public void setOptimizedGenerics(boolean z) {
        this.generics = z ? new DefaultGenerics(this) : NoGenerics.INSTANCE;
    }

    static final class DefaultSerializerEntry {
        final SerializerFactory serializerFactory;
        final Class type;

        DefaultSerializerEntry(Class cls, SerializerFactory serializerFactory) {
            this.type = cls;
            this.serializerFactory = serializerFactory;
        }
    }
}
