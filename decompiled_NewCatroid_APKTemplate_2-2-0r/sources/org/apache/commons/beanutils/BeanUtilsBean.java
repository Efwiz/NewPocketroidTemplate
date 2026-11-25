package org.apache.commons.beanutils;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.expression.Resolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/* JADX INFO: loaded from: classes12.dex */
public class BeanUtilsBean {
    private static final ContextClassLoaderLocal<BeanUtilsBean> BEANS_BY_CLASSLOADER = new ContextClassLoaderLocal<BeanUtilsBean>() { // from class: org.apache.commons.beanutils.BeanUtilsBean.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.apache.commons.beanutils.ContextClassLoaderLocal
        public BeanUtilsBean initialValue() {
            return new BeanUtilsBean();
        }
    };
    private static final Method INIT_CAUSE_METHOD = getInitCauseMethod();
    private final ConvertUtilsBean convertUtilsBean;
    private final Log log;
    private final PropertyUtilsBean propertyUtilsBean;

    public static BeanUtilsBean getInstance() {
        return BEANS_BY_CLASSLOADER.get();
    }

    public static void setInstance(BeanUtilsBean beanUtilsBean) {
        BEANS_BY_CLASSLOADER.set(beanUtilsBean);
    }

    public BeanUtilsBean() {
        this(new ConvertUtilsBean(), new PropertyUtilsBean());
    }

    public BeanUtilsBean(ConvertUtilsBean convertUtilsBean) {
        this(convertUtilsBean, new PropertyUtilsBean());
    }

    public BeanUtilsBean(ConvertUtilsBean convertUtilsBean, PropertyUtilsBean propertyUtilsBean) {
        this.log = LogFactory.getLog(BeanUtils.class);
        this.convertUtilsBean = convertUtilsBean;
        this.propertyUtilsBean = propertyUtilsBean;
    }

    public Object cloneBean(Object obj) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Object objNewInstance;
        if (this.log.isDebugEnabled()) {
            this.log.debug("Cloning bean: " + obj.getClass().getName());
        }
        if (obj instanceof DynaBean) {
            objNewInstance = ((DynaBean) obj).getDynaClass().newInstance();
        } else {
            objNewInstance = obj.getClass().newInstance();
        }
        getPropertyUtils().copyProperties(objNewInstance, obj);
        return objNewInstance;
    }

    public void copyProperties(Object obj, Object obj2) throws IllegalAccessException, InvocationTargetException {
        if (obj == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }
        if (obj2 == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("BeanUtils.copyProperties(" + obj + ", " + obj2 + Constant.AFTER_QUTO);
        }
        int i = 0;
        if (obj2 instanceof DynaBean) {
            DynaBean dynaBean = (DynaBean) obj2;
            DynaProperty[] dynaProperties = dynaBean.getDynaClass().getDynaProperties();
            int length = dynaProperties.length;
            while (i < length) {
                String name2 = dynaProperties[i].getName();
                if (getPropertyUtils().isReadable(obj2, name2) && getPropertyUtils().isWriteable(obj, name2)) {
                    copyProperty(obj, name2, dynaBean.get(name2));
                }
                i++;
            }
            return;
        }
        if (obj2 instanceof Map) {
            for (Map.Entry entry : ((Map) obj2).entrySet()) {
                String str = (String) entry.getKey();
                if (getPropertyUtils().isWriteable(obj, str)) {
                    copyProperty(obj, str, entry.getValue());
                }
            }
            return;
        }
        PropertyDescriptor[] propertyDescriptors = getPropertyUtils().getPropertyDescriptors(obj2);
        int length2 = propertyDescriptors.length;
        while (i < length2) {
            String name3 = propertyDescriptors[i].getName();
            if (!"class".equals(name3) && getPropertyUtils().isReadable(obj2, name3) && getPropertyUtils().isWriteable(obj, name3)) {
                try {
                    copyProperty(obj, name3, getPropertyUtils().getSimpleProperty(obj2, name3));
                } catch (NoSuchMethodException unused) {
                }
            }
            i++;
        }
    }

    public void copyProperty(Object obj, String str, Object obj2) throws IllegalAccessException, InvocationTargetException {
        Class<?> propertyType;
        if (this.log.isTraceEnabled()) {
            StringBuilder sb = new StringBuilder("  copyProperty(");
            sb.append(obj);
            sb.append(", ");
            sb.append(str);
            sb.append(", ");
            if (obj2 == null) {
                sb.append("<NULL>");
            } else if (obj2 instanceof String) {
                sb.append((String) obj2);
            } else if (obj2 instanceof String[]) {
                String[] strArr = (String[]) obj2;
                sb.append('[');
                for (int i = 0; i < strArr.length; i++) {
                    if (i > 0) {
                        sb.append(',');
                    }
                    sb.append(strArr[i]);
                }
                sb.append(']');
            } else {
                sb.append(obj2.toString());
            }
            sb.append(')');
            this.log.trace(sb.toString());
        }
        Resolver resolver = getPropertyUtils().getResolver();
        while (resolver.hasNested(str)) {
            try {
                obj = getPropertyUtils().getProperty(obj, resolver.next(str));
                str = resolver.remove(str);
            } catch (NoSuchMethodException unused) {
                return;
            }
        }
        if (this.log.isTraceEnabled()) {
            this.log.trace("    Target bean = " + obj);
            this.log.trace("    Target name = " + str);
        }
        String property = resolver.getProperty(str);
        int index = resolver.getIndex(str);
        String key = resolver.getKey(str);
        if (obj instanceof DynaBean) {
            DynaProperty dynaProperty = ((DynaBean) obj).getDynaClass().getDynaProperty(property);
            if (dynaProperty == null) {
                return;
            } else {
                propertyType = dynaPropertyType(dynaProperty, obj2);
            }
        } else {
            try {
                PropertyDescriptor propertyDescriptor = getPropertyUtils().getPropertyDescriptor(obj, str);
                if (propertyDescriptor == null) {
                    return;
                }
                propertyType = propertyDescriptor.getPropertyType();
                if (propertyType == null) {
                    if (this.log.isTraceEnabled()) {
                        this.log.trace("    target type for property '" + property + "' is null, so skipping ths setter");
                        return;
                    }
                    return;
                }
            } catch (NoSuchMethodException unused2) {
                return;
            }
        }
        if (this.log.isTraceEnabled()) {
            this.log.trace("    target propName=" + property + ", type=" + propertyType + ", index=" + index + ", key=" + key);
        }
        if (index >= 0) {
            try {
                getPropertyUtils().setIndexedProperty(obj, property, index, convertForCopy(obj2, propertyType.getComponentType()));
                return;
            } catch (NoSuchMethodException e) {
                throw new InvocationTargetException(e, "Cannot set " + property);
            }
        }
        if (key != null) {
            try {
                getPropertyUtils().setMappedProperty(obj, property, key, obj2);
            } catch (NoSuchMethodException e2) {
                throw new InvocationTargetException(e2, "Cannot set " + property);
            }
        } else {
            try {
                getPropertyUtils().setSimpleProperty(obj, property, convertForCopy(obj2, propertyType));
            } catch (NoSuchMethodException e3) {
                throw new InvocationTargetException(e3, "Cannot set " + property);
            }
        }
    }

    public Map<String, String> describe(Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (obj == null) {
            return new HashMap();
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Describing bean: " + obj.getClass().getName());
        }
        HashMap map = new HashMap();
        int i = 0;
        if (obj instanceof DynaBean) {
            DynaProperty[] dynaProperties = ((DynaBean) obj).getDynaClass().getDynaProperties();
            int length = dynaProperties.length;
            while (i < length) {
                String name2 = dynaProperties[i].getName();
                map.put(name2, getProperty(obj, name2));
                i++;
            }
        } else {
            PropertyDescriptor[] propertyDescriptors = getPropertyUtils().getPropertyDescriptors(obj);
            Class<?> cls = obj.getClass();
            int length2 = propertyDescriptors.length;
            while (i < length2) {
                PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
                String name3 = propertyDescriptor.getName();
                if (getPropertyUtils().getReadMethod(cls, propertyDescriptor) != null) {
                    map.put(name3, getProperty(obj, name3));
                }
                i++;
            }
        }
        return map;
    }

    public String[] getArrayProperty(Object obj, String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object property = getPropertyUtils().getProperty(obj, str);
        if (property == null) {
            return null;
        }
        if (property instanceof Collection) {
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : (Collection) property) {
                if (obj2 == null) {
                    arrayList.add(null);
                } else {
                    arrayList.add(getConvertUtils().convert(obj2));
                }
            }
            return (String[]) arrayList.toArray(new String[arrayList.size()]);
        }
        if (property.getClass().isArray()) {
            int length = Array.getLength(property);
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                Object obj3 = Array.get(property, i);
                if (obj3 == null) {
                    strArr[i] = null;
                } else {
                    strArr[i] = getConvertUtils().convert(obj3);
                }
            }
            return strArr;
        }
        return new String[]{getConvertUtils().convert(property)};
    }

    public String getIndexedProperty(Object obj, String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getConvertUtils().convert(getPropertyUtils().getIndexedProperty(obj, str));
    }

    public String getIndexedProperty(Object obj, String str, int i) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getConvertUtils().convert(getPropertyUtils().getIndexedProperty(obj, str, i));
    }

    public String getMappedProperty(Object obj, String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getConvertUtils().convert(getPropertyUtils().getMappedProperty(obj, str));
    }

    public String getMappedProperty(Object obj, String str, String str2) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getConvertUtils().convert(getPropertyUtils().getMappedProperty(obj, str, str2));
    }

    public String getNestedProperty(Object obj, String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getConvertUtils().convert(getPropertyUtils().getNestedProperty(obj, str));
    }

    public String getProperty(Object obj, String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getNestedProperty(obj, str);
    }

    public String getSimpleProperty(Object obj, String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getConvertUtils().convert(getPropertyUtils().getSimpleProperty(obj, str));
    }

    public void populate(Object obj, Map<String, ? extends Object> map) throws IllegalAccessException, InvocationTargetException {
        if (obj == null || map == null) {
            return;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("BeanUtils.populate(" + obj + ", " + map + Constant.AFTER_QUTO);
        }
        for (Map.Entry<String, ? extends Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key != null) {
                setProperty(obj, key, entry.getValue());
            }
        }
    }

    public void setProperty(Object obj, String str, Object obj2) throws IllegalAccessException, InvocationTargetException {
        Class<?> propertyType;
        Object objConvert;
        if (this.log.isTraceEnabled()) {
            StringBuilder sb = new StringBuilder("  setProperty(");
            sb.append(obj);
            sb.append(", ");
            sb.append(str);
            sb.append(", ");
            if (obj2 == null) {
                sb.append("<NULL>");
            } else if (obj2 instanceof String) {
                sb.append((String) obj2);
            } else if (obj2 instanceof String[]) {
                String[] strArr = (String[]) obj2;
                sb.append('[');
                for (int i = 0; i < strArr.length; i++) {
                    if (i > 0) {
                        sb.append(',');
                    }
                    sb.append(strArr[i]);
                }
                sb.append(']');
            } else {
                sb.append(obj2.toString());
            }
            sb.append(')');
            this.log.trace(sb.toString());
        }
        Resolver resolver = getPropertyUtils().getResolver();
        while (resolver.hasNested(str)) {
            try {
                obj = getPropertyUtils().getProperty(obj, resolver.next(str));
                if (obj == null) {
                    return;
                } else {
                    str = resolver.remove(str);
                }
            } catch (NoSuchMethodException unused) {
                return;
            }
        }
        if (this.log.isTraceEnabled()) {
            this.log.trace("    Target bean = " + obj);
            this.log.trace("    Target name = " + str);
        }
        String property = resolver.getProperty(str);
        int index = resolver.getIndex(str);
        String key = resolver.getKey(str);
        if (obj instanceof DynaBean) {
            DynaProperty dynaProperty = ((DynaBean) obj).getDynaClass().getDynaProperty(property);
            if (dynaProperty == null) {
                return;
            }
            propertyType = dynaPropertyType(dynaProperty, obj2);
            if (index >= 0 && List.class.isAssignableFrom(propertyType)) {
                propertyType = Object.class;
            }
        } else if (obj instanceof Map) {
            propertyType = Object.class;
        } else if (obj != null && obj.getClass().isArray() && index >= 0) {
            propertyType = Array.get(obj, index).getClass();
        } else {
            try {
                IndexedPropertyDescriptor propertyDescriptor = getPropertyUtils().getPropertyDescriptor(obj, str);
                if (propertyDescriptor == null) {
                    return;
                }
                if (propertyDescriptor instanceof MappedPropertyDescriptor) {
                    MappedPropertyDescriptor mappedPropertyDescriptor = (MappedPropertyDescriptor) propertyDescriptor;
                    if (mappedPropertyDescriptor.getMappedWriteMethod() == null) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Skipping read-only property");
                            return;
                        }
                        return;
                    }
                    propertyType = mappedPropertyDescriptor.getMappedPropertyType();
                } else if (index >= 0 && (propertyDescriptor instanceof IndexedPropertyDescriptor)) {
                    IndexedPropertyDescriptor indexedPropertyDescriptor = propertyDescriptor;
                    if (indexedPropertyDescriptor.getIndexedWriteMethod() == null) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Skipping read-only property");
                            return;
                        }
                        return;
                    }
                    propertyType = indexedPropertyDescriptor.getIndexedPropertyType();
                } else if (index >= 0 && List.class.isAssignableFrom(propertyDescriptor.getPropertyType())) {
                    propertyType = Object.class;
                } else if (key != null) {
                    if (propertyDescriptor.getReadMethod() == null) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Skipping read-only property");
                            return;
                        }
                        return;
                    }
                    propertyType = obj2 == null ? Object.class : obj2.getClass();
                } else {
                    if (propertyDescriptor.getWriteMethod() == null) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Skipping read-only property");
                            return;
                        }
                        return;
                    }
                    propertyType = propertyDescriptor.getPropertyType();
                }
            } catch (NoSuchMethodException unused2) {
                return;
            }
        }
        if (!propertyType.isArray() || index >= 0) {
            if (propertyType.isArray()) {
                if ((obj2 instanceof String) || obj2 == null) {
                    objConvert = getConvertUtils().convert((String) obj2, propertyType.getComponentType());
                } else if (obj2 instanceof String[]) {
                    objConvert = getConvertUtils().convert(((String[]) obj2)[0], propertyType.getComponentType());
                } else {
                    objConvert = convert(obj2, propertyType.getComponentType());
                }
            } else if (obj2 instanceof String) {
                objConvert = getConvertUtils().convert((String) obj2, propertyType);
            } else if (obj2 instanceof String[]) {
                objConvert = getConvertUtils().convert(((String[]) obj2)[0], propertyType);
            } else {
                objConvert = convert(obj2, propertyType);
            }
        } else if (obj2 == null) {
            objConvert = getConvertUtils().convert(new String[]{null}, propertyType);
        } else if (obj2 instanceof String) {
            objConvert = getConvertUtils().convert(obj2, propertyType);
        } else if (obj2 instanceof String[]) {
            objConvert = getConvertUtils().convert((String[]) obj2, propertyType);
        } else {
            objConvert = convert(obj2, propertyType);
        }
        try {
            getPropertyUtils().setProperty(obj, str, objConvert);
        } catch (NoSuchMethodException e) {
            throw new InvocationTargetException(e, "Cannot set " + property);
        }
    }

    public ConvertUtilsBean getConvertUtils() {
        return this.convertUtilsBean;
    }

    public PropertyUtilsBean getPropertyUtils() {
        return this.propertyUtilsBean;
    }

    public boolean initCause(Throwable th, Throwable th2) {
        Method method = INIT_CAUSE_METHOD;
        if (method != null && th2 != null) {
            try {
                method.invoke(th, th2);
                return true;
            } catch (Throwable unused) {
            }
        }
        return false;
    }

    protected Object convert(Object obj, Class<?> cls) {
        Converter converterLookup = getConvertUtils().lookup(cls);
        if (converterLookup == null) {
            return obj;
        }
        this.log.trace("        USING CONVERTER " + converterLookup);
        return converterLookup.convert(cls, obj);
    }

    private Object convertForCopy(Object obj, Class<?> cls) {
        return obj != null ? convert(obj, cls) : obj;
    }

    private static Method getInitCauseMethod() {
        try {
            return Throwable.class.getMethod("initCause", Throwable.class);
        } catch (NoSuchMethodException unused) {
            Log log = LogFactory.getLog(BeanUtils.class);
            if (log.isWarnEnabled()) {
                log.warn("Throwable does not have initCause() method in JDK 1.3");
            }
            return null;
        } catch (Throwable th) {
            Log log2 = LogFactory.getLog(BeanUtils.class);
            if (log2.isWarnEnabled()) {
                log2.warn("Error getting the Throwable initCause() method", th);
            }
            return null;
        }
    }

    private static Class<?> dynaPropertyType(DynaProperty dynaProperty, Object obj) {
        if (dynaProperty.isMapped()) {
            return obj == null ? String.class : obj.getClass();
        }
        return dynaProperty.getType();
    }
}
