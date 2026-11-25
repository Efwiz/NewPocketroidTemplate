package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.SerializerFactory;
import com.esotericsoftware.kryo.util.Generics;
import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
class CachedFields implements Comparator<FieldSerializer.CachedField> {
    static final FieldSerializer.CachedField[] emptyCachedFields = new FieldSerializer.CachedField[0];
    private Object access;
    private final FieldSerializer serializer;
    FieldSerializer.CachedField[] fields = new FieldSerializer.CachedField[0];
    FieldSerializer.CachedField[] copyFields = new FieldSerializer.CachedField[0];
    private final ArrayList<Field> removedFields = new ArrayList<>();

    public CachedFields(FieldSerializer fieldSerializer) {
        this.serializer = fieldSerializer;
    }

    public void rebuild() {
        if (this.serializer.type.isInterface()) {
            FieldSerializer.CachedField[] cachedFieldArr = emptyCachedFields;
            this.fields = cachedFieldArr;
            this.copyFields = cachedFieldArr;
            this.serializer.initializeCachedFields();
            return;
        }
        ArrayList<FieldSerializer.CachedField> arrayList = new ArrayList<>();
        ArrayList<FieldSerializer.CachedField> arrayList2 = new ArrayList<>();
        boolean z = (Util.unsafe || Util.isAndroid || !Modifier.isPublic(this.serializer.type.getModifiers())) ? false : true;
        for (Class superclass = this.serializer.type; superclass != Object.class; superclass = superclass.getSuperclass()) {
            for (Field field : superclass.getDeclaredFields()) {
                addField(field, z, arrayList, arrayList2);
            }
        }
        if (this.fields.length != arrayList.size()) {
            this.fields = new FieldSerializer.CachedField[arrayList.size()];
        }
        arrayList.toArray(this.fields);
        Arrays.sort(this.fields, this);
        if (this.copyFields.length != arrayList2.size()) {
            this.copyFields = new FieldSerializer.CachedField[arrayList2.size()];
        }
        arrayList2.toArray(this.copyFields);
        Arrays.sort(this.copyFields, this);
        this.serializer.initializeCachedFields();
    }

    private void addField(Field field, boolean z, ArrayList<FieldSerializer.CachedField> arrayList, ArrayList<FieldSerializer.CachedField> arrayList2) {
        int index;
        FieldSerializer.CachedField cachedFieldNewReflectField;
        int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers)) {
            return;
        }
        FieldSerializer.FieldSerializerConfig fieldSerializerConfig = this.serializer.config;
        if (field.isSynthetic() && fieldSerializerConfig.ignoreSyntheticFields) {
            return;
        }
        if (!field.isAccessible()) {
            if (!fieldSerializerConfig.setFieldsAsAccessible) {
                return;
            }
            try {
                field.setAccessible(true);
            } catch (AccessControlException unused) {
                if (Log.DEBUG) {
                    Log.debug("kryo", "Unable to set field as accessible: " + field);
                    return;
                }
                return;
            }
        }
        FieldSerializer.Optional optional = (FieldSerializer.Optional) field.getAnnotation(FieldSerializer.Optional.class);
        if ((optional == null || this.serializer.kryo.getContext().containsKey(optional.value())) && !this.removedFields.contains(field)) {
            boolean zIsTransient = Modifier.isTransient(modifiers);
            if (!zIsTransient || fieldSerializerConfig.serializeTransient || fieldSerializerConfig.copyTransient) {
                Class<?> declaringClass = field.getDeclaringClass();
                Generics.GenericType genericType = new Generics.GenericType(declaringClass, this.serializer.type, field.getGenericType());
                Class<?> type = genericType.getType() instanceof Class ? (Class) genericType.getType() : field.getType();
                if (z && !Modifier.isFinal(modifiers) && Modifier.isPublic(modifiers) && Modifier.isPublic(type.getModifiers())) {
                    try {
                        if (this.access == null) {
                            this.access = FieldAccess.get(this.serializer.type);
                        }
                        index = ((FieldAccess) this.access).getIndex(field);
                    } catch (RuntimeException e) {
                        if (Log.DEBUG) {
                            Log.debug("kryo", "Unable to use ReflectASM.", e);
                        }
                        index = -1;
                    }
                } else {
                    index = -1;
                }
                if (Util.unsafe) {
                    cachedFieldNewReflectField = newUnsafeField(field, type, genericType);
                } else if (index != -1) {
                    FieldSerializer.CachedField cachedFieldNewAsmField = newAsmField(field, type, genericType);
                    cachedFieldNewAsmField.access = (FieldAccess) this.access;
                    cachedFieldNewAsmField.accessIndex = index;
                    cachedFieldNewReflectField = cachedFieldNewAsmField;
                } else {
                    cachedFieldNewReflectField = newReflectField(field, type, genericType);
                }
                cachedFieldNewReflectField.varEncoding = fieldSerializerConfig.varEncoding;
                if (fieldSerializerConfig.extendedFieldNames) {
                    cachedFieldNewReflectField.f134name = declaringClass.getSimpleName() + "." + field.getName();
                } else {
                    cachedFieldNewReflectField.f134name = field.getName();
                }
                if (cachedFieldNewReflectField instanceof ReflectField) {
                    cachedFieldNewReflectField.canBeNull = fieldSerializerConfig.fieldsCanBeNull && !field.isAnnotationPresent(FieldSerializer.NotNull.class);
                    if (this.serializer.kryo.isFinal(type) || fieldSerializerConfig.fixedFieldTypes) {
                        cachedFieldNewReflectField.valueClass = type;
                    }
                    if (Log.TRACE) {
                        Log.trace("kryo", "Cached " + type.getSimpleName() + " field: " + field.getName() + " (" + Util.className(declaringClass) + Constant.AFTER_QUTO);
                    }
                } else {
                    cachedFieldNewReflectField.canBeNull = type == String.class && fieldSerializerConfig.fieldsCanBeNull;
                    cachedFieldNewReflectField.valueClass = type;
                    if (Log.TRACE) {
                        Log.trace("kryo", "Cached " + type.getSimpleName() + " field: " + field.getName() + " (" + Util.className(declaringClass) + Constant.AFTER_QUTO);
                    }
                }
                applyAnnotations(cachedFieldNewReflectField);
                if (zIsTransient) {
                    if (fieldSerializerConfig.serializeTransient) {
                        arrayList.add(cachedFieldNewReflectField);
                    }
                    if (fieldSerializerConfig.copyTransient) {
                        arrayList2.add(cachedFieldNewReflectField);
                        return;
                    }
                    return;
                }
                arrayList.add(cachedFieldNewReflectField);
                arrayList2.add(cachedFieldNewReflectField);
            }
        }
    }

    private FieldSerializer.CachedField newUnsafeField(Field field, Class cls, Generics.GenericType genericType) {
        if (cls.isPrimitive()) {
            if (cls == Integer.TYPE) {
                return new UnsafeField.IntUnsafeField(field);
            }
            if (cls == Float.TYPE) {
                return new UnsafeField.FloatUnsafeField(field);
            }
            if (cls == Boolean.TYPE) {
                return new UnsafeField.BooleanUnsafeField(field);
            }
            if (cls == Long.TYPE) {
                return new UnsafeField.LongUnsafeField(field);
            }
            if (cls == Double.TYPE) {
                return new UnsafeField.DoubleUnsafeField(field);
            }
            if (cls == Short.TYPE) {
                return new UnsafeField.ShortUnsafeField(field);
            }
            if (cls == Character.TYPE) {
                return new UnsafeField.CharUnsafeField(field);
            }
            if (cls == Byte.TYPE) {
                return new UnsafeField.ByteUnsafeField(field);
            }
        }
        if (cls == String.class && (!this.serializer.kryo.getReferences() || !this.serializer.kryo.getReferenceResolver().useReferences(String.class))) {
            return new UnsafeField.StringUnsafeField(field);
        }
        return new UnsafeField(field, this.serializer, genericType);
    }

    private FieldSerializer.CachedField newAsmField(Field field, Class cls, Generics.GenericType genericType) {
        if (cls.isPrimitive()) {
            if (cls == Integer.TYPE) {
                return new AsmField.IntAsmField(field);
            }
            if (cls == Float.TYPE) {
                return new AsmField.FloatAsmField(field);
            }
            if (cls == Boolean.TYPE) {
                return new AsmField.BooleanAsmField(field);
            }
            if (cls == Long.TYPE) {
                return new AsmField.LongAsmField(field);
            }
            if (cls == Double.TYPE) {
                return new AsmField.DoubleAsmField(field);
            }
            if (cls == Short.TYPE) {
                return new AsmField.ShortAsmField(field);
            }
            if (cls == Character.TYPE) {
                return new AsmField.CharAsmField(field);
            }
            if (cls == Byte.TYPE) {
                return new AsmField.ByteAsmField(field);
            }
        }
        if (cls == String.class && (!this.serializer.kryo.getReferences() || !this.serializer.kryo.getReferenceResolver().useReferences(String.class))) {
            return new AsmField.StringAsmField(field);
        }
        return new AsmField(field, this.serializer, genericType);
    }

    private FieldSerializer.CachedField newReflectField(Field field, Class cls, Generics.GenericType genericType) {
        if (cls.isPrimitive()) {
            if (cls == Integer.TYPE) {
                return new ReflectField.IntReflectField(field);
            }
            if (cls == Float.TYPE) {
                return new ReflectField.FloatReflectField(field);
            }
            if (cls == Boolean.TYPE) {
                return new ReflectField.BooleanReflectField(field);
            }
            if (cls == Long.TYPE) {
                return new ReflectField.LongReflectField(field);
            }
            if (cls == Double.TYPE) {
                return new ReflectField.DoubleReflectField(field);
            }
            if (cls == Short.TYPE) {
                return new ReflectField.ShortReflectField(field);
            }
            if (cls == Character.TYPE) {
                return new ReflectField.CharReflectField(field);
            }
            if (cls == Byte.TYPE) {
                return new ReflectField.ByteReflectField(field);
            }
        }
        return new ReflectField(field, this.serializer, genericType);
    }

    @Override // java.util.Comparator
    public int compare(FieldSerializer.CachedField cachedField, FieldSerializer.CachedField cachedField2) {
        return cachedField.f134name.compareTo(cachedField2.f134name);
    }

    public void removeField(String str) {
        boolean z;
        boolean z2;
        int i = 0;
        while (true) {
            FieldSerializer.CachedField[] cachedFieldArr = this.fields;
            z = true;
            if (i >= cachedFieldArr.length) {
                z2 = false;
                break;
            }
            FieldSerializer.CachedField cachedField = cachedFieldArr[i];
            if (cachedField.f134name.equals(str)) {
                FieldSerializer.CachedField[] cachedFieldArr2 = this.fields;
                int length = cachedFieldArr2.length - 1;
                FieldSerializer.CachedField[] cachedFieldArr3 = new FieldSerializer.CachedField[length];
                System.arraycopy(cachedFieldArr2, 0, cachedFieldArr3, 0, i);
                System.arraycopy(this.fields, i + 1, cachedFieldArr3, i, length - i);
                this.fields = cachedFieldArr3;
                this.removedFields.add(cachedField.field);
                z2 = true;
                break;
            }
            i++;
        }
        int i2 = 0;
        while (true) {
            FieldSerializer.CachedField[] cachedFieldArr4 = this.copyFields;
            if (i2 >= cachedFieldArr4.length) {
                z = z2;
                break;
            }
            FieldSerializer.CachedField cachedField2 = cachedFieldArr4[i2];
            if (cachedField2.f134name.equals(str)) {
                FieldSerializer.CachedField[] cachedFieldArr5 = this.copyFields;
                int length2 = cachedFieldArr5.length - 1;
                FieldSerializer.CachedField[] cachedFieldArr6 = new FieldSerializer.CachedField[length2];
                System.arraycopy(cachedFieldArr5, 0, cachedFieldArr6, 0, i2);
                System.arraycopy(this.copyFields, i2 + 1, cachedFieldArr6, i2, length2 - i2);
                this.copyFields = cachedFieldArr6;
                this.removedFields.add(cachedField2.field);
                break;
            }
            i2++;
        }
        if (!z) {
            throw new IllegalArgumentException("Field \"" + str + "\" not found on class: " + this.serializer.type.getName());
        }
    }

    public void removeField(FieldSerializer.CachedField cachedField) {
        boolean z;
        boolean z2;
        int i = 0;
        while (true) {
            FieldSerializer.CachedField[] cachedFieldArr = this.fields;
            z = true;
            if (i >= cachedFieldArr.length) {
                z2 = false;
                break;
            }
            FieldSerializer.CachedField cachedField2 = cachedFieldArr[i];
            if (cachedField2 == cachedField) {
                int length = cachedFieldArr.length - 1;
                FieldSerializer.CachedField[] cachedFieldArr2 = new FieldSerializer.CachedField[length];
                System.arraycopy(cachedFieldArr, 0, cachedFieldArr2, 0, i);
                System.arraycopy(this.fields, i + 1, cachedFieldArr2, i, length - i);
                this.fields = cachedFieldArr2;
                this.removedFields.add(cachedField2.field);
                z2 = true;
                break;
            }
            i++;
        }
        int i2 = 0;
        while (true) {
            FieldSerializer.CachedField[] cachedFieldArr3 = this.copyFields;
            if (i2 >= cachedFieldArr3.length) {
                z = z2;
                break;
            }
            FieldSerializer.CachedField cachedField3 = cachedFieldArr3[i2];
            if (cachedField3 == cachedField) {
                int length2 = cachedFieldArr3.length - 1;
                FieldSerializer.CachedField[] cachedFieldArr4 = new FieldSerializer.CachedField[length2];
                System.arraycopy(cachedFieldArr3, 0, cachedFieldArr4, 0, i2);
                System.arraycopy(this.copyFields, i2 + 1, cachedFieldArr4, i2, length2 - i2);
                this.copyFields = cachedFieldArr4;
                this.removedFields.add(cachedField3.field);
                break;
            }
            i2++;
        }
        if (!z) {
            throw new IllegalArgumentException("Field \"" + cachedField + "\" not found on class: " + this.serializer.type.getName());
        }
    }

    private void applyAnnotations(FieldSerializer.CachedField cachedField) {
        Field field = cachedField.field;
        if (field.isAnnotationPresent(FieldSerializer.Bind.class)) {
            if (cachedField.serializer != null) {
                throw new KryoException("@Bind applied to a field that already has a serializer: " + cachedField.field.getDeclaringClass().getName() + "." + cachedField.field.getName());
            }
            FieldSerializer.Bind bind = (FieldSerializer.Bind) field.getAnnotation(FieldSerializer.Bind.class);
            Class clsValueClass = bind.valueClass();
            if (clsValueClass == Object.class) {
                clsValueClass = null;
            }
            if (clsValueClass != null) {
                cachedField.setValueClass(clsValueClass);
            }
            Serializer serializerNewSerializer = newSerializer(clsValueClass, bind.serializer(), bind.serializerFactory());
            if (serializerNewSerializer != null) {
                cachedField.setSerializer(serializerNewSerializer);
            }
            cachedField.setCanBeNull(bind.canBeNull());
            cachedField.setVariableLengthEncoding(bind.variableLengthEncoding());
            cachedField.setOptimizePositive(bind.optimizePositive());
        }
        if (field.isAnnotationPresent(CollectionSerializer.BindCollection.class)) {
            if (cachedField.serializer != null) {
                throw new KryoException("@BindCollection applied to a field that already has a serializer: " + cachedField.field.getDeclaringClass().getName() + "." + cachedField.field.getName());
            }
            if (!Collection.class.isAssignableFrom(field.getType())) {
                throw new KryoException("@BindCollection can only be used with a field implementing Collection: " + Util.className(field.getType()));
            }
            CollectionSerializer.BindCollection bindCollection = (CollectionSerializer.BindCollection) field.getAnnotation(CollectionSerializer.BindCollection.class);
            Class clsElementClass = bindCollection.elementClass();
            if (clsElementClass == Object.class) {
                clsElementClass = null;
            }
            Serializer serializerNewSerializer2 = newSerializer(clsElementClass, bindCollection.elementSerializer(), bindCollection.elementSerializerFactory());
            CollectionSerializer collectionSerializer = new CollectionSerializer();
            collectionSerializer.setElementsCanBeNull(bindCollection.elementsCanBeNull());
            if (clsElementClass != null) {
                collectionSerializer.setElementClass(clsElementClass);
            }
            if (serializerNewSerializer2 != null) {
                collectionSerializer.setElementSerializer(serializerNewSerializer2);
            }
            cachedField.setSerializer(collectionSerializer);
        }
        if (field.isAnnotationPresent(MapSerializer.BindMap.class)) {
            if (cachedField.serializer != null) {
                throw new KryoException("@BindMap applied to a field that already has a serializer: " + cachedField.field.getDeclaringClass().getName() + "." + cachedField.field.getName());
            }
            if (!Map.class.isAssignableFrom(field.getType())) {
                throw new KryoException("@BindMap can only be used with a field implementing Map: " + Util.className(field.getType()));
            }
            MapSerializer.BindMap bindMap = (MapSerializer.BindMap) field.getAnnotation(MapSerializer.BindMap.class);
            Class clsValueClass2 = bindMap.valueClass();
            if (clsValueClass2 == Object.class) {
                clsValueClass2 = null;
            }
            Serializer serializerNewSerializer3 = newSerializer(clsValueClass2, bindMap.valueSerializer(), bindMap.valueSerializerFactory());
            Class clsKeyClass = bindMap.keyClass();
            Class cls = clsKeyClass != Object.class ? clsKeyClass : null;
            Serializer serializerNewSerializer4 = newSerializer(cls, bindMap.keySerializer(), bindMap.keySerializerFactory());
            MapSerializer mapSerializer = new MapSerializer();
            mapSerializer.setKeysCanBeNull(bindMap.keysCanBeNull());
            mapSerializer.setValuesCanBeNull(bindMap.valuesCanBeNull());
            if (cls != null) {
                mapSerializer.setKeyClass(cls);
            }
            if (serializerNewSerializer4 != null) {
                mapSerializer.setKeySerializer(serializerNewSerializer4);
            }
            if (clsValueClass2 != null) {
                mapSerializer.setValueClass(clsValueClass2);
            }
            if (serializerNewSerializer3 != null) {
                mapSerializer.setValueSerializer(serializerNewSerializer3);
            }
            cachedField.setSerializer(mapSerializer);
        }
    }

    private Serializer newSerializer(Class cls, Class cls2, Class cls3) {
        if (cls2 == Serializer.class) {
            cls2 = null;
        }
        if (cls3 == SerializerFactory.class) {
            cls3 = null;
        }
        if (cls3 == null && cls2 != null) {
            cls3 = SerializerFactory.ReflectionSerializerFactory.class;
        }
        if (cls3 == null) {
            return null;
        }
        return Util.newFactory(cls3, cls2).newSerializer(this.serializer.kryo, cls);
    }
}
