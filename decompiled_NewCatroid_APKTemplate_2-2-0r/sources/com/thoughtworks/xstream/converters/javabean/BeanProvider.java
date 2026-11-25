package com.thoughtworks.xstream.converters.javabean;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.ErrorWritingException;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/javabean/BeanProvider.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class BeanProvider implements JavaBeanProvider {
    protected static final Object[] NO_PARAMS = new Object[0];
    protected PropertyDictionary propertyDictionary;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/javabean/BeanProvider$Visitor.class
      classes11.dex
     */
    public interface Visitor extends JavaBeanProvider.Visitor {
    }

    public BeanProvider() {
        this(new PropertyDictionary(new NativePropertySorter()));
    }

    public BeanProvider(Comparator comparator) {
        this(new PropertyDictionary(new ComparingPropertySorter(comparator)));
    }

    public BeanProvider(PropertyDictionary propertyDictionary) {
        this.propertyDictionary = propertyDictionary;
    }

    @Override // com.thoughtworks.xstream.converters.javabean.JavaBeanProvider
    public Object newInstance(Class cls) {
        ErrorWritingException objectAccessException;
        try {
            return cls.newInstance();
        } catch (ExceptionInInitializerError e) {
            objectAccessException = new ConversionException("Cannot construct type", e);
            objectAccessException.add("construction-type", cls.getName());
            throw objectAccessException;
        } catch (IllegalAccessException e2) {
            objectAccessException = new ObjectAccessException("Cannot construct type", e2);
            objectAccessException.add("construction-type", cls.getName());
            throw objectAccessException;
        } catch (InstantiationException e3) {
            objectAccessException = new ConversionException("Cannot construct type", e3);
            objectAccessException.add("construction-type", cls.getName());
            throw objectAccessException;
        } catch (SecurityException e4) {
            objectAccessException = new ObjectAccessException("Cannot construct type", e4);
            objectAccessException.add("construction-type", cls.getName());
            throw objectAccessException;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.thoughtworks.xstream.converters.ErrorWritingException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r5v7 */
    @Override // com.thoughtworks.xstream.converters.javabean.JavaBeanProvider
    public void visitSerializableProperties(Object obj, JavaBeanProvider.Visitor visitor) {
        Throwable conversionException;
        ?? r5;
        for (PropertyDescriptor propertyDescriptor : getSerializableProperties(obj)) {
            try {
                Method readMethod = propertyDescriptor.getReadMethod();
                String name2 = propertyDescriptor.getName();
                Class<?> declaringClass = readMethod.getDeclaringClass();
                if (visitor.shouldVisit(name2, declaringClass)) {
                    visitor.visit(name2, propertyDescriptor.getPropertyType(), declaringClass, readMethod.invoke(obj, new Object[0]));
                }
                r5 = 0;
            } catch (IllegalAccessException e) {
                conversionException = new ObjectAccessException("Cannot access property", e);
                r5 = conversionException;
            } catch (IllegalArgumentException e2) {
                conversionException = new ConversionException("Cannot get property", e2);
                r5 = conversionException;
            } catch (InvocationTargetException e3) {
                conversionException = new ConversionException("Cannot get property", e3.getTargetException());
                r5 = conversionException;
            }
            if (r5 != 0) {
                r5.add("property", new StringBuffer().append(obj.getClass()).append(".").append(propertyDescriptor.getName()).toString());
                throw r5;
            }
        }
    }

    @Override // com.thoughtworks.xstream.converters.javabean.JavaBeanProvider
    public void writeProperty(Object obj, String str, Object obj2) {
        ErrorWritingException conversionException;
        ErrorWritingException objectAccessException;
        PropertyDescriptor property = getProperty(str, obj.getClass());
        try {
            property.getWriteMethod().invoke(obj, obj2);
            objectAccessException = null;
        } catch (IllegalAccessException e) {
            objectAccessException = new ObjectAccessException("Cannot access property", e);
        } catch (IllegalArgumentException e2) {
            conversionException = new ConversionException("Cannot set property", e2);
            objectAccessException = conversionException;
        } catch (InvocationTargetException e3) {
            conversionException = new ConversionException("Cannot set property", e3.getTargetException());
            objectAccessException = conversionException;
        }
        if (objectAccessException == null) {
            return;
        }
        objectAccessException.add("property", new StringBuffer().append(obj.getClass()).append(".").append(property.getName()).toString());
        throw objectAccessException;
    }

    @Override // com.thoughtworks.xstream.converters.javabean.JavaBeanProvider
    public Class getPropertyType(Object obj, String str) {
        return getProperty(str, obj.getClass()).getPropertyType();
    }

    @Override // com.thoughtworks.xstream.converters.javabean.JavaBeanProvider
    public boolean propertyDefinedInClass(String str, Class cls) {
        return this.propertyDictionary.propertyDescriptorOrNull(cls, str) != null;
    }

    @Override // com.thoughtworks.xstream.converters.javabean.JavaBeanProvider
    public boolean canInstantiate(Class cls) {
        if (cls == null) {
            return false;
        }
        try {
            return newInstance(cls) != null;
        } catch (ErrorWritingException unused) {
            return false;
        }
    }

    protected Constructor getDefaultConstrutor(Class cls) {
        for (Constructor<?> constructor : cls.getConstructors()) {
            if (constructor.getParameterTypes().length == 0 && Modifier.isPublic(constructor.getModifiers())) {
                return constructor;
            }
        }
        return null;
    }

    protected PropertyDescriptor[] getSerializableProperties(Object obj) {
        ArrayList arrayList = new ArrayList();
        Iterator itPropertiesFor = this.propertyDictionary.propertiesFor(obj.getClass());
        while (itPropertiesFor.hasNext()) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) itPropertiesFor.next();
            if (canStreamProperty(propertyDescriptor)) {
                arrayList.add(propertyDescriptor);
            }
        }
        return (PropertyDescriptor[]) arrayList.toArray(new PropertyDescriptor[arrayList.size()]);
    }

    protected boolean canStreamProperty(PropertyDescriptor propertyDescriptor) {
        return (propertyDescriptor.getReadMethod() == null || propertyDescriptor.getWriteMethod() == null) ? false : true;
    }

    public boolean propertyWriteable(String str, Class cls) {
        return getProperty(str, cls).getWriteMethod() != null;
    }

    protected PropertyDescriptor getProperty(String str, Class cls) {
        return this.propertyDictionary.propertyDescriptor(cls, str);
    }
}
