package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.Fields;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/PureJavaReflectionProvider.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class PureJavaReflectionProvider implements ReflectionProvider {
    static /* synthetic */ Class class$java$io$Serializable;
    protected FieldDictionary fieldDictionary;
    private transient Map serializedDataCache;

    public PureJavaReflectionProvider() {
        this(new FieldDictionary(new ImmutableFieldKeySorter()));
    }

    public PureJavaReflectionProvider(FieldDictionary fieldDictionary) {
        this.fieldDictionary = fieldDictionary;
        init();
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public Object newInstance(Class cls) throws Throwable {
        ObjectAccessException objectAccessException;
        ObjectAccessException objectAccessException2;
        try {
            for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
                if (constructor.getParameterTypes().length == 0) {
                    if (!constructor.isAccessible()) {
                        constructor.setAccessible(true);
                    }
                    return constructor.newInstance(new Object[0]);
                }
            }
            Class clsClass$ = class$java$io$Serializable;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_IO_SERIALIZABLE);
                class$java$io$Serializable = clsClass$;
            }
            if (clsClass$.isAssignableFrom(cls)) {
                return instantiateUsingSerialization(cls);
            }
            objectAccessException = new ObjectAccessException("Cannot construct type as it does not have a no-args constructor");
            objectAccessException.add("construction-type", cls.getName());
            throw objectAccessException;
        } catch (IllegalAccessException e) {
            objectAccessException2 = new ObjectAccessException("Cannot construct type", e);
            objectAccessException = objectAccessException2;
        } catch (InstantiationException e2) {
            objectAccessException2 = new ObjectAccessException("Cannot construct type", e2);
            objectAccessException = objectAccessException2;
        } catch (InvocationTargetException e3) {
            if (e3.getTargetException() instanceof RuntimeException) {
                throw ((RuntimeException) e3.getTargetException());
            }
            if (e3.getTargetException() instanceof Error) {
                throw ((Error) e3.getTargetException());
            }
            objectAccessException = new ObjectAccessException("Constructor for type threw an exception", e3.getTargetException());
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private Object instantiateUsingSerialization(final Class cls) {
        ObjectAccessException objectAccessException;
        Object object;
        try {
            synchronized (this.serializedDataCache) {
                byte[] byteArray = (byte[]) this.serializedDataCache.get(cls);
                if (byteArray == null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    dataOutputStream.writeShort(-21267);
                    dataOutputStream.writeShort(5);
                    dataOutputStream.writeByte(115);
                    dataOutputStream.writeByte(114);
                    dataOutputStream.writeUTF(cls.getName());
                    dataOutputStream.writeLong(ObjectStreamClass.lookup(cls).getSerialVersionUID());
                    dataOutputStream.writeByte(2);
                    dataOutputStream.writeShort(0);
                    dataOutputStream.writeByte(120);
                    dataOutputStream.writeByte(112);
                    byteArray = byteArrayOutputStream.toByteArray();
                    this.serializedDataCache.put(cls, byteArray);
                }
                object = new ObjectInputStream(new ByteArrayInputStream(byteArray)) { // from class: com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider.1
                    @Override // java.io.ObjectInputStream
                    protected Class resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                        return Class.forName(objectStreamClass.getName(), false, cls.getClassLoader());
                    }
                }.readObject();
            }
            return object;
        } catch (IOException e) {
            objectAccessException = new ObjectAccessException("Cannot create type by JDK serialization", e);
            objectAccessException.add("construction-type", cls.getName());
            throw objectAccessException;
        } catch (ClassNotFoundException e2) {
            objectAccessException = new ObjectAccessException("Cannot find class", e2);
            objectAccessException.add("construction-type", cls.getName());
            throw objectAccessException;
        }
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public void visitSerializableFields(Object obj, ReflectionProvider.Visitor visitor) {
        Iterator itFieldsFor = this.fieldDictionary.fieldsFor(obj.getClass());
        while (itFieldsFor.hasNext()) {
            Field field = (Field) itFieldsFor.next();
            if (fieldModifiersSupported(field)) {
                validateFieldAccess(field);
                visitor.visit(field.getName(), field.getType(), field.getDeclaringClass(), Fields.read(field, obj));
            }
        }
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public void writeField(Object obj, String str, Object obj2, Class cls) throws Throwable {
        Field field = this.fieldDictionary.field(obj.getClass(), str, cls);
        validateFieldAccess(field);
        Fields.write(field, obj, obj2);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public Class getFieldType(Object obj, String str, Class cls) {
        return this.fieldDictionary.field(obj.getClass(), str, cls).getType();
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public boolean fieldDefinedInClass(String str, Class cls) throws Throwable {
        Field fieldFieldOrNull = this.fieldDictionary.fieldOrNull(cls, str, null);
        return fieldFieldOrNull != null && fieldModifiersSupported(fieldFieldOrNull);
    }

    protected boolean fieldModifiersSupported(Field field) {
        int modifiers = field.getModifiers();
        return (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) ? false : true;
    }

    protected void validateFieldAccess(Field field) {
        if (Modifier.isFinal(field.getModifiers())) {
            if (JVM.isVersion(5)) {
                if (field.isAccessible()) {
                    return;
                }
                field.setAccessible(true);
                return;
            }
            throw new ObjectAccessException(new StringBuffer("Invalid final field ").append(field.getDeclaringClass().getName()).append(".").append(field.getName()).toString());
        }
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public Field getField(Class cls, String str) {
        return this.fieldDictionary.field(cls, str, null);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public Field getFieldOrNull(Class cls, String str) {
        return this.fieldDictionary.fieldOrNull(cls, str, null);
    }

    public void setFieldDictionary(FieldDictionary fieldDictionary) {
        this.fieldDictionary = fieldDictionary;
    }

    private Object readResolve() {
        init();
        return this;
    }

    protected void init() {
        this.serializedDataCache = new WeakHashMap();
    }
}
