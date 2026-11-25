package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.reflect.ArrayReflection;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.value.CompoundEntry;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/Json.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Json {
    private static final boolean debug = false;
    private final ObjectMap<Class, Object[]> classToDefaultValues;
    private final ObjectMap<Class, Serializer> classToSerializer;
    private final ObjectMap<Class, String> classToTag;
    private Serializer defaultSerializer;
    private boolean enumNames;
    private final Object[] equals1;
    private final Object[] equals2;
    private boolean ignoreDeprecated;
    private boolean ignoreUnknownFields;
    private JsonWriter.OutputType outputType;
    private boolean quoteLongValues;
    private boolean readDeprecated;
    private boolean sortFields;
    private final ObjectMap<String, Class> tagToClass;
    private String typeName;
    private final ObjectMap<Class, OrderedMap<String, FieldMetadata>> typeToFields;
    private boolean usePrototypes;
    private JsonWriter writer;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/Json$ReadOnlySerializer.class
      classes8.dex
     */
    public static abstract class ReadOnlySerializer<T> implements Serializer<T> {
        @Override // com.badlogic.gdx.utils.Json.Serializer
        public abstract T read(Json json, JsonValue jsonValue, Class cls);

        @Override // com.badlogic.gdx.utils.Json.Serializer
        public void write(Json json, T t, Class cls) {
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/Json$Serializable.class
      classes8.dex
     */
    public interface Serializable {
        void read(Json json, JsonValue jsonValue);

        void write(Json json);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/Json$Serializer.class
      classes8.dex
     */
    public interface Serializer<T> {
        T read(Json json, JsonValue jsonValue, Class cls);

        void write(Json json, T t, Class cls);
    }

    protected boolean ignoreUnknownField(Class cls, String str) {
        return false;
    }

    public Json() {
        this.typeName = "class";
        this.usePrototypes = true;
        this.enumNames = true;
        this.typeToFields = new ObjectMap<>();
        this.tagToClass = new ObjectMap<>();
        this.classToTag = new ObjectMap<>();
        this.classToSerializer = new ObjectMap<>();
        this.classToDefaultValues = new ObjectMap<>();
        this.equals1 = new Object[]{null};
        this.equals2 = new Object[]{null};
        this.outputType = JsonWriter.OutputType.minimal;
    }

    public Json(JsonWriter.OutputType outputType) {
        this.typeName = "class";
        this.usePrototypes = true;
        this.enumNames = true;
        this.typeToFields = new ObjectMap<>();
        this.tagToClass = new ObjectMap<>();
        this.classToTag = new ObjectMap<>();
        this.classToSerializer = new ObjectMap<>();
        this.classToDefaultValues = new ObjectMap<>();
        this.equals1 = new Object[]{null};
        this.equals2 = new Object[]{null};
        this.outputType = outputType;
    }

    public void setIgnoreUnknownFields(boolean z) {
        this.ignoreUnknownFields = z;
    }

    public boolean getIgnoreUnknownFields() {
        return this.ignoreUnknownFields;
    }

    public void setIgnoreDeprecated(boolean z) {
        this.ignoreDeprecated = z;
    }

    public void setReadDeprecated(boolean z) {
        this.readDeprecated = z;
    }

    public void setOutputType(JsonWriter.OutputType outputType) {
        this.outputType = outputType;
    }

    public void setQuoteLongValues(boolean z) {
        this.quoteLongValues = z;
    }

    public void setEnumNames(boolean z) {
        this.enumNames = z;
    }

    public void addClassTag(String str, Class cls) {
        this.tagToClass.put(str, cls);
        this.classToTag.put(cls, str);
    }

    public Class getClass(String str) {
        return this.tagToClass.get(str);
    }

    public String getTag(Class cls) {
        return this.classToTag.get(cls);
    }

    public void setTypeName(String str) {
        this.typeName = str;
    }

    public void setDefaultSerializer(Serializer serializer) {
        this.defaultSerializer = serializer;
    }

    public <T> void setSerializer(Class<T> cls, Serializer<T> serializer) {
        this.classToSerializer.put(cls, serializer);
    }

    public <T> Serializer<T> getSerializer(Class<T> cls) {
        return this.classToSerializer.get(cls);
    }

    public void setUsePrototypes(boolean z) {
        this.usePrototypes = z;
    }

    public void setElementType(Class cls, String str, Class cls2) {
        FieldMetadata fieldMetadata = getFields(cls).get(str);
        if (fieldMetadata == null) {
            throw new SerializationException("Field not found: " + str + " (" + cls.getName() + Constant.AFTER_QUTO);
        }
        fieldMetadata.elementType = cls2;
    }

    public void setDeprecated(Class cls, String str, boolean z) {
        FieldMetadata fieldMetadata = getFields(cls).get(str);
        if (fieldMetadata == null) {
            throw new SerializationException("Field not found: " + str + " (" + cls.getName() + Constant.AFTER_QUTO);
        }
        fieldMetadata.deprecated = z;
    }

    public void setSortFields(boolean z) {
        this.sortFields = z;
    }

    protected void sortFields(Class cls, Array<String> array) {
        if (this.sortFields) {
            array.sort();
        }
    }

    private OrderedMap<String, FieldMetadata> getFields(Class cls) {
        OrderedMap<String, FieldMetadata> orderedMap = this.typeToFields.get(cls);
        if (orderedMap != null) {
            return orderedMap;
        }
        Array array = new Array();
        for (Class superclass = cls; superclass != Object.class; superclass = superclass.getSuperclass()) {
            array.add(superclass);
        }
        ArrayList arrayList = new ArrayList();
        for (int i = array.size - 1; i >= 0; i--) {
            java.util.Collections.addAll(arrayList, ClassReflection.getDeclaredFields((Class) array.get(i)));
        }
        OrderedMap<String, FieldMetadata> orderedMap2 = new OrderedMap<>(arrayList.size());
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Field field = (Field) arrayList.get(i2);
            if (!field.isTransient() && !field.isStatic() && !field.isSynthetic()) {
                if (!field.isAccessible()) {
                    try {
                        field.setAccessible(true);
                    } catch (RuntimeException unused) {
                    }
                }
                orderedMap2.put(field.getName(), new FieldMetadata(field));
            }
        }
        sortFields(cls, orderedMap2.keys);
        this.typeToFields.put(cls, orderedMap2);
        return orderedMap2;
    }

    public String toJson(Object obj) {
        return toJson(obj, obj == null ? null : obj.getClass(), (Class) null);
    }

    public String toJson(Object obj, Class cls) {
        return toJson(obj, cls, (Class) null);
    }

    public String toJson(Object obj, Class cls, Class cls2) {
        StringWriter stringWriter = new StringWriter();
        toJson(obj, cls, cls2, stringWriter);
        return stringWriter.toString();
    }

    public void toJson(Object obj, FileHandle fileHandle) {
        toJson(obj, obj == null ? null : obj.getClass(), (Class) null, fileHandle);
    }

    public void toJson(Object obj, Class cls, FileHandle fileHandle) {
        toJson(obj, cls, (Class) null, fileHandle);
    }

    public void toJson(Object obj, Class cls, Class cls2, FileHandle fileHandle) {
        Writer writer = null;
        try {
            try {
                writer = fileHandle.writer(false, "UTF-8");
                toJson(obj, cls, cls2, writer);
                StreamUtils.closeQuietly(writer);
            } catch (Exception e) {
                throw new SerializationException("Error writing file: " + fileHandle, e);
            }
        } catch (Throwable th) {
            StreamUtils.closeQuietly(writer);
            throw th;
        }
    }

    public void toJson(Object obj, Writer writer) {
        toJson(obj, obj == null ? null : obj.getClass(), (Class) null, writer);
    }

    public void toJson(Object obj, Class cls, Writer writer) {
        toJson(obj, cls, (Class) null, writer);
    }

    public void toJson(Object obj, Class cls, Class cls2, Writer writer) {
        setWriter(writer);
        try {
            writeValue(obj, cls, cls2);
        } finally {
            StreamUtils.closeQuietly(this.writer);
            this.writer = null;
        }
    }

    public void setWriter(Writer writer) {
        if (!(writer instanceof JsonWriter)) {
            writer = new JsonWriter(writer);
        }
        JsonWriter jsonWriter = (JsonWriter) writer;
        this.writer = jsonWriter;
        jsonWriter.setOutputType(this.outputType);
        this.writer.setQuoteLongValues(this.quoteLongValues);
    }

    public JsonWriter getWriter() {
        return this.writer;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0081  */
    public void writeFields(Object obj) {
        Object[] objArr;
        OrderedMap<String, FieldMetadata> orderedMap;
        Class<?> cls = obj.getClass();
        Object[] defaultValues = getDefaultValues(cls);
        OrderedMap<String, FieldMetadata> fields = getFields(cls);
        Array<String> arrayOrderedKeys = fields.orderedKeys();
        int i = arrayOrderedKeys.size;
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            FieldMetadata fieldMetadata = fields.get(arrayOrderedKeys.get(i2));
            if (this.ignoreDeprecated && fieldMetadata.deprecated) {
                objArr = defaultValues;
                orderedMap = fields;
            } else {
                Field field = fieldMetadata.field;
                try {
                    Object obj2 = field.get(obj);
                    if (defaultValues != null) {
                        int i4 = i3 + 1;
                        Object obj3 = defaultValues[i3];
                        if (obj2 == null && obj3 == null) {
                            objArr = defaultValues;
                            orderedMap = fields;
                        } else {
                            if (obj2 == null || obj3 == null) {
                                objArr = defaultValues;
                                orderedMap = fields;
                            } else if (obj2.equals(obj3)) {
                                objArr = defaultValues;
                                orderedMap = fields;
                            } else if (obj2.getClass().isArray() && obj3.getClass().isArray()) {
                                objArr = defaultValues;
                                Object[] objArr2 = this.equals1;
                                objArr2[0] = obj2;
                                orderedMap = fields;
                                Object[] objArr3 = this.equals2;
                                objArr3[0] = obj3;
                                if (Arrays.deepEquals(objArr2, objArr3)) {
                                }
                            } else {
                                objArr = defaultValues;
                                orderedMap = fields;
                            }
                            i3 = i4;
                        }
                        i3 = i4;
                    } else {
                        objArr = defaultValues;
                        orderedMap = fields;
                    }
                    this.writer.name(field.getName());
                    writeValue(obj2, field.getType(), fieldMetadata.elementType);
                } catch (SerializationException e) {
                    e.addTrace(field + " (" + cls.getName() + Constant.AFTER_QUTO);
                    throw e;
                } catch (ReflectionException e2) {
                    throw new SerializationException("Error accessing field: " + field.getName() + " (" + cls.getName() + Constant.AFTER_QUTO, e2);
                } catch (Exception e3) {
                    SerializationException serializationException = new SerializationException(e3);
                    serializationException.addTrace(field + " (" + cls.getName() + Constant.AFTER_QUTO);
                    throw serializationException;
                }
            }
            i2++;
            defaultValues = objArr;
            fields = orderedMap;
        }
    }

    private Object[] getDefaultValues(Class cls) {
        if (!this.usePrototypes) {
            return null;
        }
        if (this.classToDefaultValues.containsKey(cls)) {
            return this.classToDefaultValues.get(cls);
        }
        try {
            Object objNewInstance = newInstance(cls);
            OrderedMap<String, FieldMetadata> fields = getFields(cls);
            Object[] objArr = new Object[fields.size];
            this.classToDefaultValues.put(cls, objArr);
            Array<String> arrayOrderedKeys = fields.orderedKeys();
            int i = arrayOrderedKeys.size;
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                FieldMetadata fieldMetadata = fields.get(arrayOrderedKeys.get(i3));
                if (!this.ignoreDeprecated || !fieldMetadata.deprecated) {
                    Field field = fieldMetadata.field;
                    int i4 = i2 + 1;
                    try {
                        objArr[i2] = field.get(objNewInstance);
                        i2 = i4;
                    } catch (SerializationException e) {
                        e.addTrace(field + " (" + cls.getName() + Constant.AFTER_QUTO);
                        throw e;
                    } catch (ReflectionException e2) {
                        throw new SerializationException("Error accessing field: " + field.getName() + " (" + cls.getName() + Constant.AFTER_QUTO, e2);
                    } catch (RuntimeException e3) {
                        SerializationException serializationException = new SerializationException(e3);
                        serializationException.addTrace(field + " (" + cls.getName() + Constant.AFTER_QUTO);
                        throw serializationException;
                    }
                }
            }
            return objArr;
        } catch (Exception unused) {
            this.classToDefaultValues.put(cls, null);
            return null;
        }
    }

    public void writeField(Object obj, String str) {
        writeField(obj, str, str, null);
    }

    public void writeField(Object obj, String str, Class cls) {
        writeField(obj, str, str, cls);
    }

    public void writeField(Object obj, String str, String str2) {
        writeField(obj, str, str2, null);
    }

    public void writeField(Object obj, String str, String str2, Class cls) {
        Class<?> cls2 = obj.getClass();
        FieldMetadata fieldMetadata = getFields(cls2).get(str);
        if (fieldMetadata == null) {
            throw new SerializationException("Field not found: " + str + " (" + cls2.getName() + Constant.AFTER_QUTO);
        }
        Field field = fieldMetadata.field;
        if (cls == null) {
            cls = fieldMetadata.elementType;
        }
        try {
            this.writer.name(str2);
            writeValue(field.get(obj), field.getType(), cls);
        } catch (SerializationException e) {
            e.addTrace(field + " (" + cls2.getName() + Constant.AFTER_QUTO);
            throw e;
        } catch (ReflectionException e2) {
            throw new SerializationException("Error accessing field: " + field.getName() + " (" + cls2.getName() + Constant.AFTER_QUTO, e2);
        } catch (Exception e3) {
            SerializationException serializationException = new SerializationException(e3);
            serializationException.addTrace(field + " (" + cls2.getName() + Constant.AFTER_QUTO);
            throw serializationException;
        }
    }

    public void writeValue(String str, Object obj) {
        try {
            this.writer.name(str);
            if (obj == null) {
                writeValue(obj, (Class) null, (Class) null);
            } else {
                writeValue(obj, obj.getClass(), (Class) null);
            }
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeValue(String str, Object obj, Class cls) {
        try {
            this.writer.name(str);
            writeValue(obj, cls, (Class) null);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeValue(String str, Object obj, Class cls, Class cls2) {
        try {
            this.writer.name(str);
            writeValue(obj, cls, cls2);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeValue(Object obj) {
        if (obj == null) {
            writeValue(obj, (Class) null, (Class) null);
        } else {
            writeValue(obj, obj.getClass(), (Class) null);
        }
    }

    public void writeValue(Object obj, Class cls) {
        writeValue(obj, cls, (Class) null);
    }

    public void writeValue(Object obj, Class cls, Class cls2) {
        try {
            if (obj == null) {
                this.writer.value(null);
                return;
            }
            if ((cls == null || !cls.isPrimitive()) && cls != String.class && cls != Integer.class && cls != Boolean.class && cls != Float.class && cls != Long.class && cls != Double.class && cls != Short.class && cls != Byte.class && cls != Character.class) {
                Class<?> superclass = obj.getClass();
                if (!superclass.isPrimitive() && superclass != String.class && superclass != Integer.class && superclass != Boolean.class && superclass != Float.class && superclass != Long.class && superclass != Double.class && superclass != Short.class && superclass != Byte.class && superclass != Character.class) {
                    if (obj instanceof Serializable) {
                        writeObjectStart(superclass, cls);
                        ((Serializable) obj).write(this);
                        writeObjectEnd();
                        return;
                    }
                    Serializer serializer = this.classToSerializer.get(superclass);
                    if (serializer != null) {
                        serializer.write(this, obj, cls);
                        return;
                    }
                    int i = 0;
                    if (obj instanceof Array) {
                        if (cls != null && superclass != cls && superclass != Array.class) {
                            throw new SerializationException("Serialization of an Array other than the known type is not supported.\nKnown type: " + cls + "\nActual type: " + superclass);
                        }
                        writeArrayStart();
                        Array array = (Array) obj;
                        int i2 = array.size;
                        while (i < i2) {
                            writeValue(array.get(i), cls2, (Class) null);
                            i++;
                        }
                        writeArrayEnd();
                        return;
                    }
                    if (obj instanceof Queue) {
                        if (cls != null && superclass != cls && superclass != Queue.class) {
                            throw new SerializationException("Serialization of a Queue other than the known type is not supported.\nKnown type: " + cls + "\nActual type: " + superclass);
                        }
                        writeArrayStart();
                        Queue queue = (Queue) obj;
                        int i3 = queue.size;
                        while (i < i3) {
                            writeValue(queue.get(i), cls2, (Class) null);
                            i++;
                        }
                        writeArrayEnd();
                        return;
                    }
                    if (obj instanceof Collection) {
                        if (this.typeName != null && superclass != ArrayList.class && (cls == null || cls != superclass)) {
                            writeObjectStart(superclass, cls);
                            writeArrayStart(FirebaseAnalytics.Param.ITEMS);
                            Iterator it = ((Collection) obj).iterator();
                            while (it.hasNext()) {
                                writeValue(it.next(), cls2, (Class) null);
                            }
                            writeArrayEnd();
                            writeObjectEnd();
                            return;
                        }
                        writeArrayStart();
                        Iterator it2 = ((Collection) obj).iterator();
                        while (it2.hasNext()) {
                            writeValue(it2.next(), cls2, (Class) null);
                        }
                        writeArrayEnd();
                        return;
                    }
                    if (superclass.isArray()) {
                        if (cls2 == null) {
                            cls2 = superclass.getComponentType();
                        }
                        int length = ArrayReflection.getLength(obj);
                        writeArrayStart();
                        while (i < length) {
                            writeValue(ArrayReflection.get(obj, i), cls2, (Class) null);
                            i++;
                        }
                        writeArrayEnd();
                        return;
                    }
                    if (obj instanceof ObjectMap) {
                        if (cls == null) {
                            cls = ObjectMap.class;
                        }
                        writeObjectStart(superclass, cls);
                        ObjectMap.Entries it3 = ((ObjectMap) obj).entries().iterator();
                        while (it3.hasNext()) {
                            ObjectMap.Entry next = it3.next();
                            this.writer.name(convertToString(next.key));
                            writeValue(next.value, cls2, (Class) null);
                        }
                        writeObjectEnd();
                        return;
                    }
                    if (obj instanceof ObjectIntMap) {
                        if (cls == null) {
                            cls = ObjectIntMap.class;
                        }
                        writeObjectStart(superclass, cls);
                        ObjectIntMap.Entries it4 = ((ObjectIntMap) obj).entries().iterator();
                        while (it4.hasNext()) {
                            ObjectIntMap.Entry next2 = it4.next();
                            this.writer.name(convertToString(next2.key));
                            writeValue(Integer.valueOf(next2.value), Integer.class);
                        }
                        writeObjectEnd();
                        return;
                    }
                    if (obj instanceof ObjectFloatMap) {
                        if (cls == null) {
                            cls = ObjectFloatMap.class;
                        }
                        writeObjectStart(superclass, cls);
                        ObjectFloatMap.Entries it5 = ((ObjectFloatMap) obj).entries().iterator();
                        while (it5.hasNext()) {
                            ObjectFloatMap.Entry next3 = it5.next();
                            this.writer.name(convertToString(next3.key));
                            writeValue(Float.valueOf(next3.value), Float.class);
                        }
                        writeObjectEnd();
                        return;
                    }
                    if (obj instanceof ObjectSet) {
                        if (cls == null) {
                            cls = ObjectSet.class;
                        }
                        writeObjectStart(superclass, cls);
                        this.writer.name(CompoundEntry.NAME_values);
                        writeArrayStart();
                        ObjectSet.ObjectSetIterator it6 = ((ObjectSet) obj).iterator();
                        while (it6.hasNext()) {
                            writeValue(it6.next(), cls2, (Class) null);
                        }
                        writeArrayEnd();
                        writeObjectEnd();
                        return;
                    }
                    if (obj instanceof IntMap) {
                        if (cls == null) {
                            cls = IntMap.class;
                        }
                        writeObjectStart(superclass, cls);
                        for (IntMap.Entry entry : ((IntMap) obj).entries()) {
                            this.writer.name(String.valueOf(entry.key));
                            writeValue(entry.value, cls2, (Class) null);
                        }
                        writeObjectEnd();
                        return;
                    }
                    if (obj instanceof LongMap) {
                        if (cls == null) {
                            cls = LongMap.class;
                        }
                        writeObjectStart(superclass, cls);
                        for (LongMap.Entry entry2 : ((LongMap) obj).entries()) {
                            this.writer.name(String.valueOf(entry2.key));
                            writeValue(entry2.value, cls2, (Class) null);
                        }
                        writeObjectEnd();
                        return;
                    }
                    if (obj instanceof IntSet) {
                        if (cls == null) {
                            cls = IntSet.class;
                        }
                        writeObjectStart(superclass, cls);
                        this.writer.name(CompoundEntry.NAME_values);
                        writeArrayStart();
                        IntSet.IntSetIterator it7 = ((IntSet) obj).iterator();
                        while (it7.hasNext) {
                            writeValue(Integer.valueOf(it7.next()), Integer.class, (Class) null);
                        }
                        writeArrayEnd();
                        writeObjectEnd();
                        return;
                    }
                    if (obj instanceof ArrayMap) {
                        if (cls == null) {
                            cls = ArrayMap.class;
                        }
                        writeObjectStart(superclass, cls);
                        ArrayMap arrayMap = (ArrayMap) obj;
                        int i4 = arrayMap.size;
                        while (i < i4) {
                            this.writer.name(convertToString(arrayMap.keys[i]));
                            writeValue(arrayMap.values[i], cls2, (Class) null);
                            i++;
                        }
                        writeObjectEnd();
                        return;
                    }
                    if (obj instanceof Map) {
                        if (cls == null) {
                            cls = HashMap.class;
                        }
                        writeObjectStart(superclass, cls);
                        for (Map.Entry entry3 : ((Map) obj).entrySet()) {
                            this.writer.name(convertToString(entry3.getKey()));
                            writeValue(entry3.getValue(), cls2, (Class) null);
                        }
                        writeObjectEnd();
                        return;
                    }
                    if (ClassReflection.isAssignableFrom(Enum.class, superclass)) {
                        if (superclass.getEnumConstants() == null) {
                            superclass = superclass.getSuperclass();
                        }
                        if (this.typeName != null && (cls == null || cls != superclass)) {
                            writeObjectStart(superclass, null);
                            this.writer.name("value");
                            this.writer.value(convertToString((Enum) obj));
                            writeObjectEnd();
                            return;
                        }
                        this.writer.value(convertToString((Enum) obj));
                        return;
                    }
                    writeObjectStart(superclass, cls);
                    writeFields(obj);
                    writeObjectEnd();
                    return;
                }
                writeObjectStart(superclass, null);
                writeValue("value", obj);
                writeObjectEnd();
                return;
            }
            this.writer.value(obj);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeObjectStart(String str) {
        try {
            this.writer.name(str);
            writeObjectStart();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeObjectStart(String str, Class cls, Class cls2) {
        try {
            this.writer.name(str);
            writeObjectStart(cls, cls2);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeObjectStart() {
        try {
            this.writer.object();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeObjectStart(Class cls, Class cls2) {
        try {
            this.writer.object();
            if (cls2 == null || cls2 != cls) {
                writeType(cls);
            }
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeObjectEnd() {
        try {
            this.writer.pop();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeArrayStart(String str) {
        try {
            this.writer.name(str);
            this.writer.array();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeArrayStart() {
        try {
            this.writer.array();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeArrayEnd() {
        try {
            this.writer.pop();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public void writeType(Class cls) {
        if (this.typeName == null) {
            return;
        }
        String tag = getTag(cls);
        if (tag == null) {
            tag = cls.getName();
        }
        try {
            this.writer.set(this.typeName, tag);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public <T> T fromJson(Class<T> cls, Reader reader) {
        return (T) readValue(cls, (Class) null, new JsonReader().parse(reader));
    }

    public <T> T fromJson(Class<T> cls, Class cls2, Reader reader) {
        return (T) readValue(cls, cls2, new JsonReader().parse(reader));
    }

    public <T> T fromJson(Class<T> cls, InputStream inputStream) {
        return (T) readValue(cls, (Class) null, new JsonReader().parse(inputStream));
    }

    public <T> T fromJson(Class<T> cls, Class cls2, InputStream inputStream) {
        return (T) readValue(cls, cls2, new JsonReader().parse(inputStream));
    }

    public <T> T fromJson(Class<T> cls, FileHandle fileHandle) {
        try {
            return (T) readValue(cls, (Class) null, new JsonReader().parse(fileHandle));
        } catch (Exception e) {
            throw new SerializationException("Error reading file: " + fileHandle, e);
        }
    }

    public <T> T fromJson(Class<T> cls, Class cls2, FileHandle fileHandle) {
        try {
            return (T) readValue(cls, cls2, new JsonReader().parse(fileHandle));
        } catch (Exception e) {
            throw new SerializationException("Error reading file: " + fileHandle, e);
        }
    }

    public <T> T fromJson(Class<T> cls, char[] cArr, int i, int i2) {
        return (T) readValue(cls, (Class) null, new JsonReader().parse(cArr, i, i2));
    }

    public <T> T fromJson(Class<T> cls, Class cls2, char[] cArr, int i, int i2) {
        return (T) readValue(cls, cls2, new JsonReader().parse(cArr, i, i2));
    }

    public <T> T fromJson(Class<T> cls, String str) {
        return (T) readValue(cls, (Class) null, new JsonReader().parse(str));
    }

    public <T> T fromJson(Class<T> cls, Class cls2, String str) {
        return (T) readValue(cls, cls2, new JsonReader().parse(str));
    }

    public void readField(Object obj, String str, JsonValue jsonValue) {
        readField(obj, str, str, (Class) null, jsonValue);
    }

    public void readField(Object obj, String str, Class cls, JsonValue jsonValue) {
        readField(obj, str, str, cls, jsonValue);
    }

    public void readField(Object obj, String str, String str2, JsonValue jsonValue) {
        readField(obj, str, str2, (Class) null, jsonValue);
    }

    public void readField(Object obj, String str, String str2, Class cls, JsonValue jsonValue) {
        Class<?> cls2 = obj.getClass();
        FieldMetadata fieldMetadata = getFields(cls2).get(str);
        if (fieldMetadata == null) {
            throw new SerializationException("Field not found: " + str + " (" + cls2.getName() + Constant.AFTER_QUTO);
        }
        Field field = fieldMetadata.field;
        if (cls == null) {
            cls = fieldMetadata.elementType;
        }
        readField(obj, field, str2, cls, jsonValue);
    }

    public void readField(Object obj, Field field, String str, Class cls, JsonValue jsonValue) {
        JsonValue jsonValue2 = jsonValue.get(str);
        if (jsonValue2 == null) {
            return;
        }
        try {
            field.set(obj, readValue(field.getType(), cls, jsonValue2));
        } catch (SerializationException e) {
            e.addTrace(field.getName() + " (" + field.getDeclaringClass().getName() + Constant.AFTER_QUTO);
            throw e;
        } catch (ReflectionException e2) {
            throw new SerializationException("Error accessing field: " + field.getName() + " (" + field.getDeclaringClass().getName() + Constant.AFTER_QUTO, e2);
        } catch (RuntimeException e3) {
            SerializationException serializationException = new SerializationException(e3);
            serializationException.addTrace(jsonValue2.trace());
            serializationException.addTrace(field.getName() + " (" + field.getDeclaringClass().getName() + Constant.AFTER_QUTO);
            throw serializationException;
        }
    }

    public void readFields(Object obj, JsonValue jsonValue) {
        Class<?> cls = obj.getClass();
        OrderedMap<String, FieldMetadata> fields = getFields(cls);
        for (JsonValue jsonValue2 = jsonValue.child; jsonValue2 != null; jsonValue2 = jsonValue2.next) {
            FieldMetadata fieldMetadata = fields.get(jsonValue2.name().replace(" ", "_"));
            if (fieldMetadata == null) {
                if (!jsonValue2.f125name.equals(this.typeName) && !this.ignoreUnknownFields && !ignoreUnknownField(cls, jsonValue2.f125name)) {
                    SerializationException serializationException = new SerializationException("Field not found: " + jsonValue2.f125name + " (" + cls.getName() + Constant.AFTER_QUTO);
                    serializationException.addTrace(jsonValue2.trace());
                    throw serializationException;
                }
            } else if (!this.ignoreDeprecated || this.readDeprecated || !fieldMetadata.deprecated) {
                Field field = fieldMetadata.field;
                try {
                    field.set(obj, readValue(field.getType(), fieldMetadata.elementType, jsonValue2));
                } catch (SerializationException e) {
                    e.addTrace(field.getName() + " (" + cls.getName() + Constant.AFTER_QUTO);
                    throw e;
                } catch (ReflectionException e2) {
                    throw new SerializationException("Error accessing field: " + field.getName() + " (" + cls.getName() + Constant.AFTER_QUTO, e2);
                } catch (RuntimeException e3) {
                    SerializationException serializationException2 = new SerializationException(e3);
                    serializationException2.addTrace(jsonValue2.trace());
                    serializationException2.addTrace(field.getName() + " (" + cls.getName() + Constant.AFTER_QUTO);
                    throw serializationException2;
                }
            }
        }
    }

    public <T> T readValue(String str, Class<T> cls, JsonValue jsonValue) {
        return (T) readValue(cls, (Class) null, jsonValue.get(str));
    }

    public <T> T readValue(String str, Class<T> cls, T t, JsonValue jsonValue) {
        JsonValue jsonValue2 = jsonValue.get(str);
        return jsonValue2 == null ? t : (T) readValue(cls, (Class) null, jsonValue2);
    }

    public <T> T readValue(String str, Class<T> cls, Class cls2, JsonValue jsonValue) {
        return (T) readValue(cls, cls2, jsonValue.get(str));
    }

    public <T> T readValue(String str, Class<T> cls, Class cls2, T t, JsonValue jsonValue) {
        return (T) readValue(cls, cls2, t, jsonValue.get(str));
    }

    public <T> T readValue(Class<T> cls, Class cls2, T t, JsonValue jsonValue) {
        return jsonValue == null ? t : (T) readValue(cls, cls2, jsonValue);
    }

    public <T> T readValue(Class<T> cls, JsonValue jsonValue) {
        return (T) readValue(cls, (Class) null, jsonValue);
    }

    /* JADX WARN: Code duplicated, block: B:242:0x0361  */
    /* JADX WARN: Code duplicated, block: B:316:0x042a A[RETURN] */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x0346, code lost:
    
        if (r9 != java.lang.Boolean.class) goto L239;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v4, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v0, types: [T, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r9v51, types: [T, java.util.Map] */
    /* JADX WARN: Type inference failed for: r9v52, types: [T, com.badlogic.gdx.utils.ArrayMap] */
    /* JADX WARN: Type inference failed for: r9v53, types: [T, com.badlogic.gdx.utils.IntSet] */
    /* JADX WARN: Type inference failed for: r9v54, types: [T, com.badlogic.gdx.utils.LongMap] */
    /* JADX WARN: Type inference failed for: r9v55, types: [T, com.badlogic.gdx.utils.IntMap] */
    /* JADX WARN: Type inference failed for: r9v56, types: [T, com.badlogic.gdx.utils.ObjectSet] */
    /* JADX WARN: Type inference failed for: r9v57, types: [T, com.badlogic.gdx.utils.ObjectFloatMap] */
    /* JADX WARN: Type inference failed for: r9v58, types: [T, com.badlogic.gdx.utils.ObjectIntMap] */
    /* JADX WARN: Type inference failed for: r9v59, types: [T, com.badlogic.gdx.utils.ObjectMap] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public <T> T readValue(java.lang.Class<T> r9, java.lang.Class r10, com.badlogic.gdx.utils.JsonValue r11) {
        /*
            Method dump skipped, instruction units count: 1067
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.Json.readValue(java.lang.Class, java.lang.Class, com.badlogic.gdx.utils.JsonValue):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void copyFields(Object obj, Object obj2) {
        OrderedMap<String, FieldMetadata> fields = getFields(obj2.getClass());
        ObjectMap.Entries<String, FieldMetadata> it = getFields(obj.getClass()).iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            FieldMetadata fieldMetadata = fields.get(next.key);
            Field field = ((FieldMetadata) next.value).field;
            if (fieldMetadata == null) {
                throw new SerializationException("To object is missing field: " + ((String) next.key));
            }
            try {
                fieldMetadata.field.set(obj2, field.get(obj));
            } catch (ReflectionException e) {
                throw new SerializationException("Error copying field: " + field.getName(), e);
            }
        }
    }

    private String convertToString(Enum r2) {
        return this.enumNames ? r2.name() : r2.toString();
    }

    private String convertToString(Object obj) {
        if (obj instanceof Enum) {
            return convertToString((Enum) obj);
        }
        return obj instanceof Class ? ((Class) obj).getName() : String.valueOf(obj);
    }

    protected Object newInstance(Class cls) {
        try {
            return ClassReflection.newInstance(cls);
        } catch (Exception e) {
            e = e;
            try {
                Constructor declaredConstructor = ClassReflection.getDeclaredConstructor(cls, new Class[0]);
                declaredConstructor.setAccessible(true);
                return declaredConstructor.newInstance(new Object[0]);
            } catch (ReflectionException unused) {
                if (ClassReflection.isAssignableFrom(Enum.class, cls)) {
                    if (cls.getEnumConstants() == null) {
                        cls = cls.getSuperclass();
                    }
                    return cls.getEnumConstants()[0];
                }
                if (cls.isArray()) {
                    throw new SerializationException("Encountered JSON object when expected array of type: " + cls.getName(), e);
                }
                if (ClassReflection.isMemberClass(cls) && !ClassReflection.isStaticClass(cls)) {
                    throw new SerializationException("Class cannot be created (non-static member class): " + cls.getName(), e);
                }
                throw new SerializationException("Class cannot be created (missing no-arg constructor): " + cls.getName(), e);
            } catch (SecurityException unused2) {
                throw new SerializationException("Error constructing instance of class: " + cls.getName(), e);
            } catch (Exception e2) {
                e = e2;
                throw new SerializationException("Error constructing instance of class: " + cls.getName(), e);
            }
        }
    }

    public String prettyPrint(Object obj) {
        return prettyPrint(obj, 0);
    }

    public String prettyPrint(String str) {
        return prettyPrint(str, 0);
    }

    public String prettyPrint(Object obj, int i) {
        return prettyPrint(toJson(obj), i);
    }

    public String prettyPrint(String str, int i) {
        return new JsonReader().parse(str).prettyPrint(this.outputType, i);
    }

    public String prettyPrint(Object obj, JsonValue.PrettyPrintSettings prettyPrintSettings) {
        return prettyPrint(toJson(obj), prettyPrintSettings);
    }

    public String prettyPrint(String str, JsonValue.PrettyPrintSettings prettyPrintSettings) {
        return new JsonReader().parse(str).prettyPrint(prettyPrintSettings);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/Json$FieldMetadata.class
      classes8.dex
     */
    private static class FieldMetadata {
        boolean deprecated;
        Class elementType;
        final Field field;

        public FieldMetadata(Field field) {
            this.field = field;
            this.elementType = field.getElementType((ClassReflection.isAssignableFrom(ObjectMap.class, field.getType()) || ClassReflection.isAssignableFrom(Map.class, field.getType())) ? 1 : 0);
            this.deprecated = field.isAnnotationPresent(Deprecated.class);
        }
    }
}
