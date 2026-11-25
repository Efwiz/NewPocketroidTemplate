package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/* JADX INFO: loaded from: classes8.dex */
public class BeanSerializer<T> extends Serializer<T> {
    static final Object[] noArgs = new Object[0];
    Object access;
    private CachedProperty[] properties;

    public BeanSerializer(Kryo kryo, Class cls) {
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(cls).getPropertyDescriptors();
            Arrays.sort(propertyDescriptors, new Comparator<PropertyDescriptor>() { // from class: com.esotericsoftware.kryo.serializers.BeanSerializer.1
                @Override // java.util.Comparator
                public int compare(PropertyDescriptor propertyDescriptor, PropertyDescriptor propertyDescriptor2) {
                    return propertyDescriptor.getName().compareTo(propertyDescriptor2.getName());
                }
            });
            ArrayList arrayList = new ArrayList(propertyDescriptors.length);
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String name2 = propertyDescriptor.getName();
                if (!name2.equals("class")) {
                    Method readMethod = propertyDescriptor.getReadMethod();
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    if (readMethod != null && writeMethod != null) {
                        Class<?> returnType = readMethod.getReturnType();
                        Serializer serializer = kryo.isFinal(returnType) ? kryo.getRegistration(returnType).getSerializer() : null;
                        CachedProperty cachedProperty = new CachedProperty();
                        cachedProperty.f133name = name2;
                        cachedProperty.getMethod = readMethod;
                        cachedProperty.setMethod = writeMethod;
                        cachedProperty.serializer = serializer;
                        cachedProperty.setMethodType = writeMethod.getParameterTypes()[0];
                        arrayList.add(cachedProperty);
                    }
                }
            }
            this.properties = (CachedProperty[]) arrayList.toArray(new CachedProperty[arrayList.size()]);
            try {
                this.access = MethodAccess.get(cls);
                int length = this.properties.length;
                for (int i = 0; i < length; i++) {
                    CachedProperty cachedProperty2 = this.properties[i];
                    cachedProperty2.getterAccessIndex = ((MethodAccess) this.access).getIndex(cachedProperty2.getMethod.getName(), cachedProperty2.getMethod.getParameterTypes());
                    cachedProperty2.setterAccessIndex = ((MethodAccess) this.access).getIndex(cachedProperty2.setMethod.getName(), cachedProperty2.setMethod.getParameterTypes());
                }
            } catch (Throwable unused) {
            }
        } catch (IntrospectionException e) {
            throw new KryoException("Error getting bean info.", e);
        }
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, T t) {
        Class<?> cls = t.getClass();
        int length = this.properties.length;
        for (int i = 0; i < length; i++) {
            CachedProperty cachedProperty = this.properties[i];
            try {
                if (Log.TRACE) {
                    Log.trace("kryo", "Write property: " + cachedProperty + " (" + cls.getName() + Constant.AFTER_QUTO);
                }
                Object obj = cachedProperty.get(t);
                Serializer serializer = cachedProperty.serializer;
                if (serializer != null) {
                    kryo.writeObjectOrNull(output, obj, serializer);
                } else {
                    kryo.writeClassAndObject(output, obj);
                }
            } catch (KryoException e) {
                e.addTrace(cachedProperty + " (" + cls.getName() + Constant.AFTER_QUTO);
                throw e;
            } catch (IllegalAccessException e2) {
                throw new KryoException("Error accessing getter method: " + cachedProperty + " (" + cls.getName() + Constant.AFTER_QUTO, e2);
            } catch (InvocationTargetException e3) {
                throw new KryoException("Error invoking getter method: " + cachedProperty + " (" + cls.getName() + Constant.AFTER_QUTO, e3);
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(cachedProperty + " (" + cls.getName() + Constant.AFTER_QUTO);
                throw kryoException;
            }
        }
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public T read(Kryo kryo, Input input, Class<? extends T> cls) {
        Object classAndObject;
        T t = (T) kryo.newInstance(cls);
        kryo.reference(t);
        int length = this.properties.length;
        for (int i = 0; i < length; i++) {
            CachedProperty cachedProperty = this.properties[i];
            try {
                if (Log.TRACE) {
                    Log.trace("kryo", "Read property: " + cachedProperty + " (" + t.getClass() + Constant.AFTER_QUTO);
                }
                Serializer serializer = cachedProperty.serializer;
                if (serializer != null) {
                    classAndObject = kryo.readObjectOrNull(input, cachedProperty.setMethodType, serializer);
                } else {
                    classAndObject = kryo.readClassAndObject(input);
                }
                cachedProperty.set(t, classAndObject);
            } catch (KryoException e) {
                e.addTrace(cachedProperty + " (" + t.getClass().getName() + Constant.AFTER_QUTO);
                throw e;
            } catch (IllegalAccessException e2) {
                throw new KryoException("Error accessing setter method: " + cachedProperty + " (" + t.getClass().getName() + Constant.AFTER_QUTO, e2);
            } catch (InvocationTargetException e3) {
                throw new KryoException("Error invoking setter method: " + cachedProperty + " (" + t.getClass().getName() + Constant.AFTER_QUTO, e3);
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(cachedProperty + " (" + t.getClass().getName() + Constant.AFTER_QUTO);
                throw kryoException;
            }
        }
        return t;
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public T copy(Kryo kryo, T t) {
        T t2 = (T) kryo.newInstance(t.getClass());
        int length = this.properties.length;
        for (int i = 0; i < length; i++) {
            CachedProperty cachedProperty = this.properties[i];
            try {
                cachedProperty.set(t2, cachedProperty.get(t));
            } catch (KryoException e) {
                e.addTrace(cachedProperty + " (" + t2.getClass().getName() + Constant.AFTER_QUTO);
                throw e;
            } catch (Exception e2) {
                throw new KryoException("Error copying bean property: " + cachedProperty + " (" + t2.getClass().getName() + Constant.AFTER_QUTO, e2);
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(cachedProperty + " (" + t2.getClass().getName() + Constant.AFTER_QUTO);
                throw kryoException;
            }
        }
        return t2;
    }

    class CachedProperty<X> {
        Method getMethod;
        int getterAccessIndex;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        String f133name;
        Serializer serializer;
        Method setMethod;
        Class setMethodType;
        int setterAccessIndex;

        CachedProperty() {
        }

        public String toString() {
            return this.f133name;
        }

        Object get(Object obj) throws IllegalAccessException, InvocationTargetException {
            return BeanSerializer.this.access != null ? ((MethodAccess) BeanSerializer.this.access).invoke(obj, this.getterAccessIndex, new Object[0]) : this.getMethod.invoke(obj, BeanSerializer.noArgs);
        }

        void set(Object obj, Object obj2) throws IllegalAccessException, InvocationTargetException {
            if (BeanSerializer.this.access != null) {
                ((MethodAccess) BeanSerializer.this.access).invoke(obj, this.setterAccessIndex, obj2);
            } else {
                this.setMethod.invoke(obj, obj2);
            }
        }
    }
}
