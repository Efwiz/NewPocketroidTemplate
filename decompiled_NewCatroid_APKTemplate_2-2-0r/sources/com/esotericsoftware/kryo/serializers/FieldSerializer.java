package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.SerializerFactory;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Generics;
import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.reflectasm.FieldAccess;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes8.dex */
public class FieldSerializer<T> extends Serializer<T> {
    final CachedFields cachedFields;
    final FieldSerializerConfig config;
    private final Generics.GenericsHierarchy genericsHierarchy;
    final Kryo kryo;
    final Class type;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Bind {
        boolean canBeNull() default true;

        boolean optimizePositive() default false;

        Class<? extends Serializer> serializer() default Serializer.class;

        Class<? extends SerializerFactory> serializerFactory() default SerializerFactory.class;

        Class valueClass() default Object.class;

        boolean variableLengthEncoding() default true;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NotNull {
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Optional {
        String value();
    }

    protected void initializeCachedFields() {
    }

    public FieldSerializer(Kryo kryo, Class cls) {
        this(kryo, cls, new FieldSerializerConfig());
    }

    public FieldSerializer(Kryo kryo, Class cls, FieldSerializerConfig fieldSerializerConfig) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (cls.isPrimitive()) {
            throw new IllegalArgumentException("type cannot be a primitive class: " + cls);
        }
        if (fieldSerializerConfig == null) {
            throw new IllegalArgumentException("config cannot be null.");
        }
        this.kryo = kryo;
        this.type = cls;
        this.config = fieldSerializerConfig;
        this.genericsHierarchy = new Generics.GenericsHierarchy(cls);
        CachedFields cachedFields = new CachedFields(this);
        this.cachedFields = cachedFields;
        cachedFields.rebuild();
    }

    public FieldSerializerConfig getFieldSerializerConfig() {
        return this.config;
    }

    public void updateFields() {
        if (Log.TRACE) {
            Log.trace("kryo", "Update fields: " + Util.className(this.type));
        }
        this.cachedFields.rebuild();
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, T t) {
        int iPushTypeVariables = pushTypeVariables();
        CachedField[] cachedFieldArr = this.cachedFields.fields;
        int length = cachedFieldArr.length;
        for (int i = 0; i < length; i++) {
            if (Log.TRACE) {
                log("Write", cachedFieldArr[i], output.position());
            }
            cachedFieldArr[i].write(output, t);
        }
        popTypeVariables(iPushTypeVariables);
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public T read(Kryo kryo, Input input, Class<? extends T> cls) {
        int iPushTypeVariables = pushTypeVariables();
        T tCreate = create(kryo, input, cls);
        kryo.reference(tCreate);
        CachedField[] cachedFieldArr = this.cachedFields.fields;
        int length = cachedFieldArr.length;
        for (int i = 0; i < length; i++) {
            if (Log.TRACE) {
                log("Read", cachedFieldArr[i], input.position());
            }
            cachedFieldArr[i].read(input, tCreate);
        }
        popTypeVariables(iPushTypeVariables);
        return tCreate;
    }

    protected int pushTypeVariables() {
        Generics.GenericType[] genericTypeArrNextGenericTypes = this.kryo.getGenerics().nextGenericTypes();
        if (genericTypeArrNextGenericTypes == null) {
            return 0;
        }
        int iPushTypeVariables = this.kryo.getGenerics().pushTypeVariables(this.genericsHierarchy, genericTypeArrNextGenericTypes);
        if (Log.TRACE && iPushTypeVariables > 0) {
            Log.trace("kryo", "Generics: " + this.kryo.getGenerics());
        }
        return iPushTypeVariables;
    }

    protected void popTypeVariables(int i) {
        Generics generics = this.kryo.getGenerics();
        if (i > 0) {
            generics.popTypeVariables(i);
        }
        generics.popGenericType();
    }

    protected T create(Kryo kryo, Input input, Class<? extends T> cls) {
        return (T) kryo.newInstance(cls);
    }

    protected void log(String str, CachedField cachedField, int i) {
        String simpleName;
        if (cachedField instanceof ReflectField) {
            ReflectField reflectField = (ReflectField) cachedField;
            Class<?> clsResolveFieldClass = reflectField.resolveFieldClass();
            if (clsResolveFieldClass == null) {
                clsResolveFieldClass = cachedField.field.getType();
            }
            simpleName = Util.simpleName(clsResolveFieldClass, reflectField.genericType);
        } else if (cachedField.valueClass != null) {
            simpleName = cachedField.valueClass.getSimpleName();
        } else {
            simpleName = cachedField.field.getType().getSimpleName();
        }
        Log.trace("kryo", str + " field " + simpleName + ": " + cachedField.f134name + " (" + Util.className(cachedField.field.getDeclaringClass()) + ')' + Util.pos(i));
    }

    public CachedField getField(String str) {
        for (CachedField cachedField : this.cachedFields.fields) {
            if (cachedField.f134name.equals(str)) {
                return cachedField;
            }
        }
        throw new IllegalArgumentException("Field \"" + str + "\" not found on class: " + this.type.getName());
    }

    public void removeField(String str) {
        this.cachedFields.removeField(str);
    }

    public void removeField(CachedField cachedField) {
        this.cachedFields.removeField(cachedField);
    }

    public CachedField[] getFields() {
        return this.cachedFields.fields;
    }

    public CachedField[] getCopyFields() {
        return this.cachedFields.copyFields;
    }

    public Class getType() {
        return this.type;
    }

    public Kryo getKryo() {
        return this.kryo;
    }

    protected T createCopy(Kryo kryo, T t) {
        return (T) kryo.newInstance(t.getClass());
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public T copy(Kryo kryo, T t) {
        T tCreateCopy = createCopy(kryo, t);
        kryo.reference(tCreateCopy);
        int length = this.cachedFields.copyFields.length;
        for (int i = 0; i < length; i++) {
            this.cachedFields.copyFields[i].copy(t, tCreateCopy);
        }
        return tCreateCopy;
    }

    public static abstract class CachedField {
        FieldAccess access;
        boolean canBeNull;
        final Field field;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        String f134name;
        long offset;
        boolean optimizePositive;
        Serializer serializer;
        int tag;
        Class valueClass;
        boolean varEncoding = true;
        boolean reuseSerializer = true;
        int accessIndex = -1;

        public abstract void copy(Object obj, Object obj2);

        public abstract void read(Input input, Object obj);

        public abstract void write(Output output, Object obj);

        public CachedField(Field field) {
            this.field = field;
        }

        public void setValueClass(Class cls) {
            this.valueClass = cls;
        }

        public Class getValueClass() {
            return this.valueClass;
        }

        public void setValueClass(Class cls, Serializer serializer) {
            this.valueClass = cls;
            this.serializer = serializer;
        }

        public void setSerializer(Serializer serializer) {
            this.serializer = serializer;
        }

        public Serializer getSerializer() {
            return this.serializer;
        }

        public void setCanBeNull(boolean z) {
            this.canBeNull = z;
        }

        public boolean getCanBeNull() {
            return this.canBeNull;
        }

        public void setVariableLengthEncoding(boolean z) {
            this.varEncoding = z;
        }

        public boolean getVariableLengthEncoding() {
            return this.varEncoding;
        }

        public void setOptimizePositive(boolean z) {
            this.optimizePositive = z;
        }

        public boolean getOptimizePositive() {
            return this.optimizePositive;
        }

        void setReuseSerializer(boolean z) {
            this.reuseSerializer = z;
        }

        boolean getReuseSerializer() {
            return this.reuseSerializer;
        }

        public String getName() {
            return this.f134name;
        }

        public Field getField() {
            return this.field;
        }

        public String toString() {
            return this.f134name;
        }
    }

    public static class FieldSerializerConfig implements Cloneable {
        boolean extendedFieldNames;
        boolean fixedFieldTypes;
        boolean serializeTransient;
        boolean fieldsCanBeNull = true;
        boolean setFieldsAsAccessible = true;
        boolean ignoreSyntheticFields = true;
        boolean copyTransient = true;
        boolean varEncoding = true;

        @Override // 
        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public FieldSerializerConfig mo2542clone() {
            try {
                return (FieldSerializerConfig) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new KryoException(e);
            }
        }

        public void setFieldsCanBeNull(boolean z) {
            this.fieldsCanBeNull = z;
            if (Log.TRACE) {
                Log.trace("kryo", "FieldSerializerConfig fieldsCanBeNull: " + z);
            }
        }

        public boolean getFieldsCanBeNull() {
            return this.fieldsCanBeNull;
        }

        public void setFieldsAsAccessible(boolean z) {
            this.setFieldsAsAccessible = z;
            if (Log.TRACE) {
                Log.trace("kryo", "FieldSerializerConfig setFieldsAsAccessible: " + z);
            }
        }

        public boolean getSetFieldsAsAccessible() {
            return this.setFieldsAsAccessible;
        }

        public void setIgnoreSyntheticFields(boolean z) {
            this.ignoreSyntheticFields = z;
            if (Log.TRACE) {
                Log.trace("kryo", "FieldSerializerConfig ignoreSyntheticFields: " + z);
            }
        }

        public boolean getIgnoreSyntheticFields() {
            return this.ignoreSyntheticFields;
        }

        public void setFixedFieldTypes(boolean z) {
            this.fixedFieldTypes = z;
            if (Log.TRACE) {
                Log.trace("kryo", "FieldSerializerConfig fixedFieldTypes: " + z);
            }
        }

        public boolean getFixedFieldTypes() {
            return this.fixedFieldTypes;
        }

        public void setCopyTransient(boolean z) {
            this.copyTransient = z;
            if (Log.TRACE) {
                Log.trace("kryo", "FieldSerializerConfig copyTransient: " + z);
            }
        }

        public boolean getCopyTransient() {
            return this.copyTransient;
        }

        public void setSerializeTransient(boolean z) {
            this.serializeTransient = z;
            if (Log.TRACE) {
                Log.trace("kryo", "FieldSerializerConfig serializeTransient: " + z);
            }
        }

        public boolean getSerializeTransient() {
            return this.serializeTransient;
        }

        public void setVariableLengthEncoding(boolean z) {
            this.varEncoding = z;
            if (Log.TRACE) {
                Log.trace("kryo", "FieldSerializerConfig variable length encoding: " + z);
            }
        }

        public boolean getVariableLengthEncoding() {
            return this.varEncoding;
        }

        public void setExtendedFieldNames(boolean z) {
            this.extendedFieldNames = z;
            if (Log.TRACE) {
                Log.trace("kryo", "FieldSerializerConfig extendedFieldNames: " + z);
            }
        }

        public boolean getExtendedFieldNames() {
            return this.extendedFieldNames;
        }
    }
}
