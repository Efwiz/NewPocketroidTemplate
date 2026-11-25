package com.thoughtworks.xstream.converters.reflection;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/SunUnsafeReflectionProvider.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SunUnsafeReflectionProvider extends SunLimitedUnsafeReflectionProvider {
    private transient Map fieldOffsetCache;

    public SunUnsafeReflectionProvider() {
    }

    public SunUnsafeReflectionProvider(FieldDictionary fieldDictionary) {
        super(fieldDictionary);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider, com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public void writeField(Object obj, String str, Object obj2, Class cls) {
        write(this.fieldDictionary.field(obj.getClass(), str, cls), obj, obj2);
    }

    private void write(Field field, Object obj, Object obj2) {
        if (exception != null) {
            ObjectAccessException objectAccessException = new ObjectAccessException("Cannot set field", exception);
            objectAccessException.add("field", new StringBuffer().append(obj.getClass()).append(".").append(field.getName()).toString());
            throw objectAccessException;
        }
        try {
            long fieldOffset = getFieldOffset(field);
            Class<?> type = field.getType();
            if (type.isPrimitive()) {
                if (type.equals(Integer.TYPE)) {
                    unsafe.putInt(obj, fieldOffset, ((Integer) obj2).intValue());
                    return;
                }
                if (type.equals(Long.TYPE)) {
                    unsafe.putLong(obj, fieldOffset, ((Long) obj2).longValue());
                    return;
                }
                if (type.equals(Short.TYPE)) {
                    unsafe.putShort(obj, fieldOffset, ((Short) obj2).shortValue());
                    return;
                }
                if (type.equals(Character.TYPE)) {
                    unsafe.putChar(obj, fieldOffset, ((Character) obj2).charValue());
                    return;
                }
                if (type.equals(Byte.TYPE)) {
                    unsafe.putByte(obj, fieldOffset, ((Byte) obj2).byteValue());
                    return;
                }
                if (type.equals(Float.TYPE)) {
                    unsafe.putFloat(obj, fieldOffset, ((Float) obj2).floatValue());
                    return;
                }
                if (type.equals(Double.TYPE)) {
                    unsafe.putDouble(obj, fieldOffset, ((Double) obj2).doubleValue());
                    return;
                } else {
                    if (type.equals(Boolean.TYPE)) {
                        unsafe.putBoolean(obj, fieldOffset, ((Boolean) obj2).booleanValue());
                        return;
                    }
                    ObjectAccessException objectAccessException2 = new ObjectAccessException("Cannot set field of unknown type", exception);
                    objectAccessException2.add("field", new StringBuffer().append(obj.getClass()).append(".").append(field.getName()).toString());
                    objectAccessException2.add("unknown-type", type.getName());
                    throw objectAccessException2;
                }
            }
            unsafe.putObject(obj, fieldOffset, obj2);
        } catch (IllegalArgumentException e) {
            ObjectAccessException objectAccessException3 = new ObjectAccessException("Cannot set field", e);
            objectAccessException3.add("field", new StringBuffer().append(obj.getClass()).append(".").append(field.getName()).toString());
            throw objectAccessException3;
        }
    }

    private synchronized long getFieldOffset(Field field) {
        Long l;
        l = (Long) this.fieldOffsetCache.get(field);
        if (l == null) {
            l = new Long(unsafe.objectFieldOffset(field));
            this.fieldOffsetCache.put(field, l);
        }
        return l.longValue();
    }

    private Object readResolve() {
        init();
        return this;
    }

    @Override // com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider
    protected void init() {
        super.init();
        this.fieldOffsetCache = new WeakHashMap();
    }
}
