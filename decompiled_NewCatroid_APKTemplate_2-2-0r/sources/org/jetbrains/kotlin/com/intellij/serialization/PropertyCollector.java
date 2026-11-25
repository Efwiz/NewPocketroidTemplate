package org.jetbrains.kotlin.com.intellij.serialization;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.awt.Rectangle;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Couple;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes3.dex */
public class PropertyCollector {
    private final ClassValue<List<MutableAccessor>> classToOwnFields;
    private final Configuration configuration;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 8 || i == 11 || i == 15) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 8 || i == 11 || i == 15) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 5:
                objArr[0] = "aClass";
                break;
            case 3:
            case 7:
            case 12:
            case 14:
            default:
                objArr[0] = "configuration";
                break;
            case 4:
            case 8:
            case 11:
            case 15:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/serialization/PropertyCollector";
                break;
            case 6:
                objArr[0] = "accessors";
                break;
            case 9:
                objArr[0] = "methodName";
                break;
            case 10:
                objArr[0] = "name";
                break;
            case 13:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 4) {
            objArr[1] = "doCollect";
        } else if (i == 8) {
            objArr[1] = "collectPropertyAccessors";
        } else if (i == 11) {
            objArr[1] = "decapitalize";
        } else if (i != 15) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/serialization/PropertyCollector";
        } else {
            objArr[1] = "doCollectOwnFields";
        }
        switch (i) {
            case 1:
                objArr[2] = "collect";
                break;
            case 2:
            case 3:
                objArr[2] = "doCollect";
                break;
            case 4:
            case 8:
            case 11:
            case 15:
                break;
            case 5:
            case 6:
            case 7:
                objArr[2] = "collectPropertyAccessors";
                break;
            case 9:
                objArr[2] = "getPropertyData";
                break;
            case 10:
                objArr[2] = "decapitalize";
                break;
            case 12:
                objArr[2] = "isAcceptableProperty";
                break;
            case 13:
            case 14:
                objArr[2] = "doCollectOwnFields";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 8 && i != 11 && i != 15) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PropertyCollector(Configuration configuration) {
        if (configuration == null) {
            $$$reportNull$$$0(0);
        }
        this.configuration = configuration;
        this.classToOwnFields = new PropertyCollectorListClassValue(configuration);
    }

    public List<MutableAccessor> collect(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        return doCollect(cls, this.configuration, this.classToOwnFields);
    }

    public static List<MutableAccessor> doCollect(Class<?> cls, Configuration configuration, ClassValue<List<MutableAccessor>> classValue) {
        Map<String, Pair<Method, Method>> mapEmptyMap;
        if (cls == null) {
            $$$reportNull$$$0(2);
        }
        if (configuration == null) {
            $$$reportNull$$$0(3);
        }
        ArrayList arrayList = new ArrayList();
        if (!configuration.collectAccessors || cls == Rectangle.class) {
            mapEmptyMap = Collections.emptyMap();
        } else {
            mapEmptyMap = collectPropertyAccessors(cls, arrayList, configuration);
        }
        int size = arrayList.size();
        do {
            arrayList.addAll(classValue == null ? doCollectOwnFields(cls, configuration) : classValue.get(cls));
            cls = cls.getSuperclass();
            if (cls == null || cls == Object.class || cls == AtomicReference.class) {
                break;
            }
        } while (!configuration.isAnnotatedAsTransient(cls));
        int i = size;
        while (size < arrayList.size()) {
            String name2 = ((MutableAccessor) arrayList.get(size)).getName();
            if (mapEmptyMap.containsKey(name2)) {
                for (int i2 = 0; i2 < i; i2++) {
                    if (((MutableAccessor) arrayList.get(i2)).getName().equals(name2)) {
                        arrayList.remove(i2);
                        i--;
                        size--;
                        break;
                    }
                }
            }
            size++;
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static Map<String, Pair<Method, Method>> collectPropertyAccessors(Class<?> cls, List<? super MutableAccessor> list, Configuration configuration) {
        NameAndIsSetter propertyData;
        if (cls == null) {
            $$$reportNull$$$0(5);
        }
        if (list == null) {
            $$$reportNull$$$0(6);
        }
        if (configuration == null) {
            $$$reportNull$$$0(7);
        }
        TreeMap treeMap = new TreeMap();
        for (Method method : cls.getMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && (propertyData = getPropertyData(method.getName())) != null && method.getParameterCount() == propertyData.isSetter && !propertyData.f719name.equals("class")) {
                Pair empty = (Pair) treeMap.get(propertyData.f719name);
                if (empty == null) {
                    empty = Couple.getEmpty();
                }
                if (((Method) (propertyData.isSetter ? empty.second : empty.first)) == null) {
                    Method method2 = propertyData.isSetter ? (Method) empty.first : method;
                    if (!propertyData.isSetter) {
                        method = (Method) empty.second;
                    }
                    treeMap.put(propertyData.f719name, new Couple(method2, method));
                }
            }
        }
        Iterator it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = (Pair) entry.getValue();
            Method method3 = (Method) pair.first;
            Method method4 = (Method) pair.second;
            if (isAcceptableProperty(method3, method4, configuration)) {
                list.add(new PropertyAccessor((String) entry.getKey(), method3.getReturnType(), method3, method4));
            } else {
                it.remove();
            }
        }
        return treeMap;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x003d A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:18:0x003e  */
    /* JADX WARN: Code duplicated, block: B:20:0x0046  */
    /* JADX WARN: Code duplicated, block: B:22:0x004e A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:23:0x004f  */
    private static NameAndIsSetter getPropertyData(String str) {
        String strSubstring;
        boolean z;
        int iIndexOf;
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (str.startsWith("get")) {
            strSubstring = str.substring(3);
        } else if (str.startsWith(LanguageCodeUtil.IS)) {
            strSubstring = str.substring(2);
        } else {
            if (!str.startsWith(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX)) {
                strSubstring = "";
            } else {
                strSubstring = str.substring(3);
                z = true;
            }
            if (strSubstring.isEmpty()) {
                return null;
            }
            iIndexOf = strSubstring.indexOf(36);
            if (iIndexOf > 0) {
                if (strSubstring.endsWith(JvmAbi.ANNOTATED_PROPERTY_METHOD_NAME_SUFFIX)) {
                    return null;
                }
                strSubstring = strSubstring.substring(0, iIndexOf);
            }
            return new NameAndIsSetter(decapitalize(strSubstring), z);
        }
        z = false;
        if (strSubstring.isEmpty()) {
            return null;
        }
        iIndexOf = strSubstring.indexOf(36);
        if (iIndexOf > 0) {
            if (strSubstring.endsWith(JvmAbi.ANNOTATED_PROPERTY_METHOD_NAME_SUFFIX)) {
                return null;
            }
            strSubstring = strSubstring.substring(0, iIndexOf);
        }
        return new NameAndIsSetter(decapitalize(strSubstring), z);
    }

    private static String decapitalize(String str) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (str.isEmpty() || (str.length() > 1 && Character.isUpperCase(str.charAt(1)) && Character.isUpperCase(str.charAt(0)))) {
            if (str == null) {
                $$$reportNull$$$0(11);
            }
            return str;
        }
        char[] charArray = str.toCharArray();
        charArray[0] = Character.toLowerCase(str.charAt(0));
        return new String(charArray);
    }

    private static boolean isAcceptableProperty(Method method, Method method2, Configuration configuration) {
        if (configuration == null) {
            $$$reportNull$$$0(12);
        }
        if (method == null || configuration.isAnnotatedAsTransient(method) || method.getDeclaringClass() == AtomicReference.class) {
            return false;
        }
        if (method2 == null) {
            return (Collection.class.isAssignableFrom(method.getReturnType()) || Map.class.isAssignableFrom(method.getReturnType())) && configuration.hasStoreAnnotations(method);
        }
        return !configuration.isAnnotatedAsTransient(method2) && method.getReturnType().equals(method2.getParameterTypes()[0]);
    }

    private static final class NameAndIsSetter {
        final boolean isSetter;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        final String f719name;

        NameAndIsSetter(String str, boolean z) {
            this.f719name = str;
            this.isSetter = z;
        }
    }

    public static class Configuration {
        private final boolean collectAccessors;
        private final boolean collectFinalFields;
        private final boolean collectPrivateFields;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            objArr[1] = "org/jetbrains/kotlin/com/intellij/serialization/PropertyCollector$Configuration";
            if (i != 1) {
                objArr[2] = "isAnnotatedAsTransient";
            } else {
                objArr[2] = "hasStoreAnnotations";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public boolean hasStoreAnnotations(AccessibleObject accessibleObject) {
            if (accessibleObject != null) {
                return false;
            }
            $$$reportNull$$$0(1);
            return false;
        }

        public boolean isAnnotatedAsTransient(AnnotatedElement annotatedElement) {
            if (annotatedElement == null) {
                $$$reportNull$$$0(0);
            }
            return false;
        }

        public Configuration(boolean z, boolean z2, boolean z3) {
            this.collectAccessors = z;
            this.collectPrivateFields = z2;
            this.collectFinalFields = z3;
        }
    }

    private static final class PropertyCollectorListClassValue extends ClassValue<List<MutableAccessor>> {
        private final Configuration configuration;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "configuration", "org/jetbrains/kotlin/com/intellij/serialization/PropertyCollector$PropertyCollectorListClassValue", "<init>"));
        }

        @Override // java.lang.ClassValue
        protected /* bridge */ /* synthetic */ List<MutableAccessor> computeValue(Class cls) {
            return computeValue2((Class<?>) cls);
        }

        private PropertyCollectorListClassValue(Configuration configuration) {
            if (configuration == null) {
                $$$reportNull$$$0(0);
            }
            this.configuration = configuration;
        }

        @Override // java.lang.ClassValue
        /* JADX INFO: renamed from: computeValue, reason: avoid collision after fix types in other method */
        protected List<MutableAccessor> computeValue2(Class<?> cls) {
            return PropertyCollector.doCollectOwnFields(cls, this.configuration);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:29:0x005e  */
    /* JADX WARN: Code duplicated, block: B:32:0x0065 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:33:0x0067  */
    /* JADX WARN: Code duplicated, block: B:47:0x0074 A[SYNTHETIC] */
    public static List<MutableAccessor> doCollectOwnFields(Class<?> cls, Configuration configuration) {
        if (cls == null) {
            $$$reportNull$$$0(13);
        }
        if (configuration == null) {
            $$$reportNull$$$0(14);
        }
        List<MutableAccessor> listEmptyList = null;
        for (Field field : cls.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                if (!configuration.hasStoreAnnotations(field)) {
                    if (configuration.collectPrivateFields || Modifier.isPublic(modifiers)) {
                        if (!configuration.collectFinalFields && Modifier.isFinal(modifiers)) {
                            Class<?> type = field.getType();
                            if (Collection.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type)) {
                                if (configuration.isAnnotatedAsTransient(field)) {
                                    if (listEmptyList == null) {
                                        listEmptyList = new ArrayList<>();
                                    }
                                    listEmptyList.add(new FieldAccessor(field));
                                }
                            }
                        } else if (configuration.isAnnotatedAsTransient(field)) {
                            if (listEmptyList == null) {
                                listEmptyList = new ArrayList<>();
                            }
                            listEmptyList.add(new FieldAccessor(field));
                        }
                    }
                } else {
                    if (listEmptyList == null) {
                        listEmptyList = new ArrayList<>();
                    }
                    listEmptyList.add(new FieldAccessor(field));
                }
            }
        }
        if (listEmptyList == null) {
            listEmptyList = Collections.emptyList();
        }
        if (listEmptyList == null) {
            $$$reportNull$$$0(15);
        }
        return listEmptyList;
    }
}
