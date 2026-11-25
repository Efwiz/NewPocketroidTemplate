package org.apache.commons.beanutils;

import java.beans.PropertyDescriptor;
import java.util.Set;

/* JADX INFO: loaded from: classes12.dex */
public interface IntrospectionContext {
    void addPropertyDescriptor(PropertyDescriptor propertyDescriptor);

    void addPropertyDescriptors(PropertyDescriptor[] propertyDescriptorArr);

    PropertyDescriptor getPropertyDescriptor(String str);

    Class<?> getTargetClass();

    boolean hasProperty(String str);

    Set<String> propertyNames();

    void removePropertyDescriptor(String str);
}
