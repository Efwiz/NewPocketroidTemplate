package com.thoughtworks.xstream.converters.javabean;

import com.thoughtworks.xstream.converters.reflection.MissingFieldException;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import com.thoughtworks.xstream.core.Caching;
import com.thoughtworks.xstream.core.util.OrderRetainingMap;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/javabean/PropertyDictionary.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class PropertyDictionary implements Caching {
    static /* synthetic */ Class class$java$lang$Object;
    private transient Map propertyNameCache;
    private final PropertySorter sorter;

    public PropertyDictionary() {
        this(new NativePropertySorter());
    }

    public PropertyDictionary(PropertySorter propertySorter) {
        this.propertyNameCache = Collections.synchronizedMap(new HashMap());
        this.sorter = propertySorter;
    }

    public Iterator serializablePropertiesFor(Class cls) {
        ArrayList arrayList = new ArrayList();
        for (PropertyDescriptor propertyDescriptor : buildMap(cls).values()) {
            if (propertyDescriptor.getReadMethod() != null && propertyDescriptor.getWriteMethod() != null) {
                arrayList.add(new BeanProperty(cls, propertyDescriptor.getName(), propertyDescriptor.getPropertyType()));
            }
        }
        return arrayList.iterator();
    }

    public BeanProperty property(Class cls, String str) {
        PropertyDescriptor propertyDescriptorPropertyDescriptorOrNull = propertyDescriptorOrNull(cls, str);
        if (propertyDescriptorPropertyDescriptorOrNull == null) {
            throw new MissingFieldException(cls.getName(), str);
        }
        if (propertyDescriptorPropertyDescriptorOrNull.getReadMethod() == null || propertyDescriptorPropertyDescriptorOrNull.getWriteMethod() == null) {
            return null;
        }
        return new BeanProperty(cls, propertyDescriptorPropertyDescriptorOrNull.getName(), propertyDescriptorPropertyDescriptorOrNull.getPropertyType());
    }

    public Iterator propertiesFor(Class cls) {
        return buildMap(cls).values().iterator();
    }

    public PropertyDescriptor propertyDescriptor(Class cls, String str) {
        PropertyDescriptor propertyDescriptorPropertyDescriptorOrNull = propertyDescriptorOrNull(cls, str);
        if (propertyDescriptorPropertyDescriptorOrNull != null) {
            return propertyDescriptorPropertyDescriptorOrNull;
        }
        throw new MissingFieldException(cls.getName(), str);
    }

    public PropertyDescriptor propertyDescriptorOrNull(Class cls, String str) {
        return (PropertyDescriptor) buildMap(cls).get(str);
    }

    private Map buildMap(Class cls) throws Throwable {
        Map map = (Map) this.propertyNameCache.get(cls);
        if (map != null) {
            return map;
        }
        try {
            Class clsClass$ = class$java$lang$Object;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_OBJECT);
                class$java$lang$Object = clsClass$;
            }
            BeanInfo beanInfo = Introspector.getBeanInfo(cls, clsClass$);
            OrderRetainingMap orderRetainingMap = new OrderRetainingMap();
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                orderRetainingMap.put(propertyDescriptor.getName(), propertyDescriptor);
            }
            Map mapSort = this.sorter.sort(cls, orderRetainingMap);
            this.propertyNameCache.put(cls, mapSort);
            return mapSort;
        } catch (IntrospectionException e) {
            ObjectAccessException objectAccessException = new ObjectAccessException("Cannot get BeanInfo of type", e);
            objectAccessException.add("bean-type", cls.getName());
            throw objectAccessException;
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.core.Caching
    public void flushCache() {
        this.propertyNameCache.clear();
    }
}
