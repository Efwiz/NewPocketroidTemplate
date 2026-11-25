package org.jetbrains.kotlin.com.intellij.util.xmlb;

import java.lang.ref.Reference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.JDOMExternalizableStringList;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.serialization.ClassUtil;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.CollectionBean;
import org.jetbrains.kotlin.org.jdom.Element;

/* JADX INFO: loaded from: classes3.dex */
public final class XmlSerializerImpl {
    static final XmlSerializer serializer = new XmlSerializer();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 12 || i == 13 || i == 16 || i == 17) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 12 || i == 13 || i == 16 || i == 17) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 12:
            case 13:
            case 16:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/XmlSerializerImpl";
                break;
            case 3:
            case 6:
            case 10:
                objArr[0] = "valueClass";
                break;
            case 4:
            case 7:
                objArr[0] = "host";
                break;
            case 5:
            case 9:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
            case 8:
            case 11:
                objArr[0] = "value";
                break;
            case 14:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 15:
                objArr[0] = "defaultText";
                break;
            default:
                objArr[0] = "object";
                break;
        }
        if (i == 1 || i == 2) {
            objArr[1] = "serialize";
        } else if (i == 12 || i == 13) {
            objArr[1] = "convertToString";
        } else if (i == 16 || i == 17) {
            objArr[1] = "getTextValue";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/XmlSerializerImpl";
        }
        switch (i) {
            case 1:
            case 2:
            case 12:
            case 13:
            case 16:
            case 17:
                break;
            case 3:
                objArr[2] = "convert";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "doSet";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "callFromStringIfDefined";
                break;
            case 11:
                objArr[2] = "convertToString";
                break;
            case 14:
            case 15:
                objArr[2] = "getTextValue";
                break;
            default:
                objArr[2] = "serialize";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 12 && i != 13 && i != 16 && i != 17) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static abstract class XmlSerializerBase implements Serializer {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        /* JADX WARN: Code duplicated, block: B:20:0x002d  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 5 ? 3 : 2];
            if (i == 1) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else if (i == 8 || i == 3) {
                objArr[0] = "originalType";
            } else if (i == 4) {
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
            } else if (i == 5) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/XmlSerializerImpl$XmlSerializerBase";
            } else if (i != 6) {
                objArr[0] = "aClass";
            } else {
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
            }
            if (i != 5) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/XmlSerializerImpl$XmlSerializerBase";
            } else {
                objArr[1] = "getRootBinding";
            }
            if (i == 2 || i == 3 || i == 4) {
                objArr[2] = "getRootBinding";
            } else if (i != 5) {
                if (i == 7 || i == 8) {
                    objArr[2] = "createClassBinding";
                } else {
                    objArr[2] = "getBinding";
                }
            }
            String str2 = String.format(str, objArr);
            if (i == 5) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Serializer
        public final Binding getBinding(Class<?> cls, Type type) {
            if (cls == null) {
                $$$reportNull$$$0(0);
            }
            if (type == null) {
                $$$reportNull$$$0(1);
            }
            if (ClassUtil.isPrimitive(cls)) {
                return null;
            }
            return getRootBinding(cls, type);
        }

        public final synchronized Binding getRootBinding(Class<?> cls, Type type, MutableAccessor mutableAccessor) {
            Binding bindingCreateClassBinding;
            if (cls == null) {
                try {
                    $$$reportNull$$$0(2);
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (type == null) {
                $$$reportNull$$$0(3);
            }
            if (mutableAccessor == null) {
                $$$reportNull$$$0(4);
            }
            bindingCreateClassBinding = createClassBinding(cls, mutableAccessor, type);
            if (bindingCreateClassBinding == null) {
                bindingCreateClassBinding = getRootBinding(cls, type);
            } else {
                bindingCreateClassBinding.init(type, this);
            }
            if (bindingCreateClassBinding == null) {
                $$$reportNull$$$0(5);
            }
            return bindingCreateClassBinding;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Serializer
        public final Binding getBinding(MutableAccessor mutableAccessor) {
            if (mutableAccessor == null) {
                $$$reportNull$$$0(6);
            }
            Type genericType = mutableAccessor.getGenericType();
            Class<?> clsTypeToClass = ClassUtil.typeToClass(genericType);
            if (ClassUtil.isPrimitive(clsTypeToClass)) {
                return null;
            }
            return getRootBinding(clsTypeToClass, genericType, mutableAccessor);
        }

        protected static Binding createClassBinding(Class<?> cls, MutableAccessor mutableAccessor, Type type) {
            if (cls == null) {
                $$$reportNull$$$0(7);
            }
            if (type == null) {
                $$$reportNull$$$0(8);
            }
            if (cls.isArray()) {
                if (Element.class.isAssignableFrom(cls.getComponentType())) {
                    return new JDOMElementBinding(mutableAccessor);
                }
                return new ArrayBinding(cls, mutableAccessor);
            }
            if (Collection.class.isAssignableFrom(cls) && (type instanceof ParameterizedType)) {
                if (mutableAccessor != null && ((CollectionBean) mutableAccessor.getAnnotation(CollectionBean.class)) != null) {
                    return new CompactCollectionBinding(mutableAccessor);
                }
                return new CollectionBinding((ParameterizedType) type, mutableAccessor);
            }
            if (Map.class.isAssignableFrom(cls) && (type instanceof ParameterizedType)) {
                return new MapBinding(mutableAccessor, cls);
            }
            if (mutableAccessor == null) {
                return null;
            }
            if (Element.class.isAssignableFrom(cls)) {
                return new JDOMElementBinding(mutableAccessor);
            }
            if (JDOMExternalizableStringList.class == cls) {
                return new CompactCollectionBinding(mutableAccessor);
            }
            return null;
        }
    }

    static class XmlSerializer extends XmlSerializerBase {
        private Reference<Map<Type, Binding>> ourBindings;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
            if (i == 1) {
                objArr[0] = "aClass";
            } else if (i != 2) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/XmlSerializerImpl$XmlSerializer";
            } else {
                objArr[0] = "originalType";
            }
            if (i == 1 || i == 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/XmlSerializerImpl$XmlSerializer";
            } else if (i != 3) {
                objArr[1] = "getBindingCacheMap";
            } else {
                objArr[1] = "getRootBinding";
            }
            if (i == 1 || i == 2) {
                objArr[2] = "getRootBinding";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        XmlSerializer() {
        }

        private Map<Type, Binding> getBindingCacheMap() {
            Map<Type, Binding> concurrentHashMap = (Map) SoftReference.dereference(this.ourBindings);
            if (concurrentHashMap == null) {
                concurrentHashMap = new ConcurrentHashMap<>();
                this.ourBindings = new java.lang.ref.SoftReference(concurrentHashMap);
            }
            if (concurrentHashMap == null) {
                $$$reportNull$$$0(0);
            }
            return concurrentHashMap;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Serializer
        public synchronized Binding getRootBinding(Class<?> cls, Type type) {
            Binding bindingCreateClassBinding;
            if (cls == null) {
                try {
                    $$$reportNull$$$0(1);
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (type == null) {
                $$$reportNull$$$0(2);
            }
            Map<Type, Binding> bindingCacheMap = getBindingCacheMap();
            bindingCreateClassBinding = bindingCacheMap.get(type);
            if (bindingCreateClassBinding == null) {
                bindingCreateClassBinding = createClassBinding(cls, null, type);
                if (bindingCreateClassBinding == null) {
                    bindingCreateClassBinding = new BeanBinding(cls);
                }
                bindingCacheMap.put(type, bindingCreateClassBinding);
                try {
                    bindingCreateClassBinding.init(type, this);
                } catch (Error | RuntimeException e) {
                    bindingCacheMap.remove(type);
                    throw e;
                }
            }
            if (bindingCreateClassBinding == null) {
                $$$reportNull$$$0(3);
            }
            return bindingCreateClassBinding;
        }
    }

    static Object convert(String str, Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(3);
        }
        if (str == null) {
            return null;
        }
        if (cls == String.class) {
            return str;
        }
        if (cls == Integer.TYPE || cls == Integer.class) {
            return Integer.valueOf(Integer.parseInt(str));
        }
        if (cls == Boolean.TYPE || cls == Boolean.class) {
            return Boolean.valueOf(Boolean.parseBoolean(str));
        }
        if (cls == Double.TYPE || cls == Double.class) {
            return Double.valueOf(Double.parseDouble(str));
        }
        if (cls == Float.TYPE || cls == Float.class) {
            return Float.valueOf(Float.parseFloat(str));
        }
        if (cls == Long.TYPE || cls == Long.class) {
            return Long.valueOf(Long.parseLong(str));
        }
        if (cls.isEnum()) {
            for (Object obj : cls.getEnumConstants()) {
                if (obj.toString().equals(str)) {
                    return obj;
                }
            }
            return null;
        }
        if (!Date.class.isAssignableFrom(cls)) {
            return str;
        }
        try {
            return new Date(Long.parseLong(str));
        } catch (NumberFormatException unused) {
            return new Date(0L);
        }
    }

    static void doSet(Object obj, String str, MutableAccessor mutableAccessor, Class<?> cls) {
        Object objValueOf;
        if (obj == null) {
            $$$reportNull$$$0(4);
        }
        if (mutableAccessor == null) {
            $$$reportNull$$$0(5);
        }
        if (cls == null) {
            $$$reportNull$$$0(6);
        }
        if (str == null) {
            mutableAccessor.set(obj, null);
            return;
        }
        if (cls == String.class) {
            mutableAccessor.set(obj, str);
            return;
        }
        if (cls == Integer.TYPE) {
            mutableAccessor.setInt(obj, Integer.parseInt(str));
            return;
        }
        if (cls == Boolean.TYPE) {
            mutableAccessor.setBoolean(obj, Boolean.parseBoolean(str));
            return;
        }
        if (cls == Double.TYPE) {
            mutableAccessor.setDouble(obj, Double.parseDouble(str));
            return;
        }
        if (cls == Float.TYPE) {
            mutableAccessor.setFloat(obj, Float.parseFloat(str));
            return;
        }
        if (cls == Long.TYPE) {
            mutableAccessor.setLong(obj, Long.parseLong(str));
            return;
        }
        if (cls == Short.TYPE) {
            mutableAccessor.setShort(obj, Short.parseShort(str));
            return;
        }
        if (cls.isEnum()) {
            mutableAccessor.set(obj, ClassUtil.stringToEnum(str, cls, false));
            return;
        }
        if (Date.class.isAssignableFrom(cls)) {
            try {
                mutableAccessor.set(obj, new Date(Long.parseLong(str)));
                return;
            } catch (NumberFormatException unused) {
                mutableAccessor.set(obj, new Date(0L));
                return;
            }
        }
        if (cls == Boolean.class) {
            objValueOf = Boolean.valueOf(Boolean.parseBoolean(str));
        } else if (cls == Integer.class) {
            objValueOf = Integer.valueOf(Integer.parseInt(str));
        } else if (cls == Short.class) {
            objValueOf = Short.valueOf(Short.parseShort(str));
        } else if (cls == Long.class) {
            objValueOf = Long.valueOf(Long.parseLong(str));
        } else if (cls == Double.class) {
            objValueOf = Double.valueOf(Double.parseDouble(str));
        } else if (cls == Float.class) {
            objValueOf = Float.valueOf(Float.parseFloat(str));
        } else if (callFromStringIfDefined(obj, str, mutableAccessor, cls)) {
            objValueOf = str;
            return;
        }
        objValueOf = str;
        mutableAccessor.set(obj, objValueOf);
    }

    private static boolean callFromStringIfDefined(Object obj, String str, MutableAccessor mutableAccessor, Class<?> cls) {
        if (obj == null) {
            $$$reportNull$$$0(7);
        }
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (mutableAccessor == null) {
            $$$reportNull$$$0(9);
        }
        if (cls == null) {
            $$$reportNull$$$0(10);
        }
        try {
            Method method = cls.getMethod("fromText", String.class);
            try {
                method.setAccessible(true);
            } catch (SecurityException unused) {
            }
            mutableAccessor.set(obj, method.invoke(null, str));
            return true;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused2) {
            return false;
        }
    }
}
