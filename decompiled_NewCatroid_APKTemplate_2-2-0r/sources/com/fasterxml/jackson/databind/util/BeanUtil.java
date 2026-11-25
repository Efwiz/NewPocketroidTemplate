package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;

/* JADX INFO: loaded from: classes8.dex */
public class BeanUtil {
    @Deprecated
    public static String okNameForGetter(AnnotatedMethod annotatedMethod, boolean z) {
        String name2 = annotatedMethod.getName();
        String strOkNameForIsGetter = okNameForIsGetter(annotatedMethod, name2, z);
        return strOkNameForIsGetter == null ? okNameForRegularGetter(annotatedMethod, name2, z) : strOkNameForIsGetter;
    }

    @Deprecated
    public static String okNameForRegularGetter(AnnotatedMethod annotatedMethod, String str, boolean z) {
        if (!str.startsWith("get")) {
            return null;
        }
        if ("getCallbacks".equals(str)) {
            if (isCglibGetCallbacks(annotatedMethod)) {
                return null;
            }
        } else if ("getMetaClass".equals(str) && isGroovyMetaClassGetter(annotatedMethod)) {
            return null;
        }
        if (z) {
            return stdManglePropertyName(str, 3);
        }
        return legacyManglePropertyName(str, 3);
    }

    @Deprecated
    public static String okNameForIsGetter(AnnotatedMethod annotatedMethod, String str, boolean z) {
        if (!str.startsWith(LanguageCodeUtil.IS)) {
            return null;
        }
        Class<?> rawType = annotatedMethod.getRawType();
        if (rawType != Boolean.class && rawType != Boolean.TYPE) {
            return null;
        }
        if (z) {
            return stdManglePropertyName(str, 2);
        }
        return legacyManglePropertyName(str, 2);
    }

    @Deprecated
    public static String okNameForSetter(AnnotatedMethod annotatedMethod, boolean z) {
        return okNameForMutator(annotatedMethod, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, z);
    }

    @Deprecated
    public static String okNameForMutator(AnnotatedMethod annotatedMethod, String str, boolean z) {
        String name2 = annotatedMethod.getName();
        if (!name2.startsWith(str)) {
            return null;
        }
        if (z) {
            return stdManglePropertyName(name2, str.length());
        }
        return legacyManglePropertyName(name2, str.length());
    }

    public static Object getDefaultValue(JavaType javaType) {
        Class<?> rawClass = javaType.getRawClass();
        Class<?> clsPrimitiveType = ClassUtil.primitiveType(rawClass);
        if (clsPrimitiveType != null) {
            return ClassUtil.defaultValue(clsPrimitiveType);
        }
        if (javaType.isContainerType() || javaType.isReferenceType()) {
            return JsonInclude.Include.NON_EMPTY;
        }
        if (rawClass == String.class) {
            return "";
        }
        if (javaType.isTypeOrSubTypeOf(Date.class)) {
            return new Date(0L);
        }
        if (!javaType.isTypeOrSubTypeOf(Calendar.class)) {
            return null;
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(0L);
        return gregorianCalendar;
    }

    protected static boolean isCglibGetCallbacks(AnnotatedMethod annotatedMethod) {
        Class<?> rawType = annotatedMethod.getRawType();
        if (!rawType.isArray()) {
            return false;
        }
        String name2 = rawType.getComponentType().getName();
        if (name2.contains(".cglib")) {
            return name2.startsWith("net.sf.cglib") || name2.startsWith("org.hibernate.repackage.cglib") || name2.startsWith("org.springframework.cglib");
        }
        return false;
    }

    protected static boolean isGroovyMetaClassGetter(AnnotatedMethod annotatedMethod) {
        return annotatedMethod.getRawType().getName().startsWith("groovy.lang");
    }

    protected static String legacyManglePropertyName(String str, int i) {
        int length = str.length();
        if (length == i) {
            return null;
        }
        char cCharAt = str.charAt(i);
        char lowerCase = Character.toLowerCase(cCharAt);
        if (cCharAt == lowerCase) {
            return str.substring(i);
        }
        StringBuilder sb = new StringBuilder(length - i);
        sb.append(lowerCase);
        while (true) {
            i++;
            if (i >= length) {
                break;
            }
            char cCharAt2 = str.charAt(i);
            char lowerCase2 = Character.toLowerCase(cCharAt2);
            if (cCharAt2 == lowerCase2) {
                sb.append((CharSequence) str, i, length);
                break;
            }
            sb.append(lowerCase2);
        }
        return sb.toString();
    }

    public static String stdManglePropertyName(String str, int i) {
        int length = str.length();
        if (length == i) {
            return null;
        }
        char cCharAt = str.charAt(i);
        char lowerCase = Character.toLowerCase(cCharAt);
        if (cCharAt == lowerCase) {
            return str.substring(i);
        }
        int i2 = i + 1;
        if (i2 < length && Character.isUpperCase(str.charAt(i2))) {
            return str.substring(i);
        }
        StringBuilder sb = new StringBuilder(length - i);
        sb.append(lowerCase);
        sb.append((CharSequence) str, i2, length);
        return sb.toString();
    }

    public static String checkUnsupportedType(JavaType javaType) {
        String str;
        String str2;
        String name2 = javaType.getRawClass().getName();
        if (isJava8TimeClass(name2)) {
            if (name2.indexOf(46, 10) >= 0) {
                return null;
            }
            str = "Java 8 date/time";
            str2 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310";
        } else {
            if (!isJodaTimeClass(name2)) {
                return null;
            }
            str = "Joda date/time";
            str2 = "com.fasterxml.jackson.datatype:jackson-datatype-joda";
        }
        return String.format("%s type %s not supported by default: add Module \"%s\" to enable handling", str, ClassUtil.getTypeDescription(javaType), str2);
    }

    public static boolean isJava8TimeClass(Class<?> cls) {
        return isJava8TimeClass(cls.getName());
    }

    private static boolean isJava8TimeClass(String str) {
        return str.startsWith("java.time.");
    }

    public static boolean isJodaTimeClass(Class<?> cls) {
        return isJodaTimeClass(cls.getName());
    }

    private static boolean isJodaTimeClass(String str) {
        return str.startsWith("org.joda.time.");
    }
}
