package org.apache.commons.beanutils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/* JADX INFO: loaded from: classes12.dex */
public class FluentPropertyBeanIntrospector implements BeanIntrospector {
    public static final String DEFAULT_WRITE_METHOD_PREFIX = "set";
    private final Log log;
    private final String writeMethodPrefix;

    public FluentPropertyBeanIntrospector(String str) {
        this.log = LogFactory.getLog(getClass());
        if (str == null) {
            throw new IllegalArgumentException("Prefix for write methods must not be null!");
        }
        this.writeMethodPrefix = str;
    }

    public FluentPropertyBeanIntrospector() {
        this(DEFAULT_WRITE_METHOD_PREFIX);
    }

    public String getWriteMethodPrefix() {
        return this.writeMethodPrefix;
    }

    @Override // org.apache.commons.beanutils.BeanIntrospector
    public void introspect(IntrospectionContext introspectionContext) throws IntrospectionException {
        for (Method method : introspectionContext.getTargetClass().getMethods()) {
            if (method.getName().startsWith(getWriteMethodPrefix())) {
                String strPropertyName = propertyName(method);
                PropertyDescriptor propertyDescriptor = introspectionContext.getPropertyDescriptor(strPropertyName);
                if (propertyDescriptor == null) {
                    try {
                        introspectionContext.addPropertyDescriptor(createFluentPropertyDescritor(method, strPropertyName));
                    } catch (IntrospectionException e) {
                        this.log.info("Error when creating PropertyDescriptor for " + method + "! Ignoring this property.");
                        this.log.debug("Exception is:", e);
                    }
                } else if (propertyDescriptor.getWriteMethod() == null) {
                    propertyDescriptor.setWriteMethod(method);
                }
            }
        }
    }

    private String propertyName(Method method) {
        String strSubstring = method.getName().substring(getWriteMethodPrefix().length());
        return strSubstring.length() > 1 ? Introspector.decapitalize(strSubstring) : strSubstring.toLowerCase(Locale.ENGLISH);
    }

    private PropertyDescriptor createFluentPropertyDescritor(Method method, String str) throws IntrospectionException {
        return new PropertyDescriptor(propertyName(method), (Method) null, method);
    }
}
