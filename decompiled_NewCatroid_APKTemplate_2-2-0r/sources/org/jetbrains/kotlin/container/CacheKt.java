package org.jetbrains.kotlin.container;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: Cache.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0002\u001a\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0002\u001a\u001a\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\t2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\tH\u0002\u001a\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\tH\u0002\u001a\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\r2\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0002\u001a\u0014\u0010\u0010\u001a\u00020\u00112\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0002\u001a\u000e\u0010\u0012\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030\t¨\u0006\u0013"}, d2 = {"collectInterfacesRecursive", "", ModuleXmlParser.TYPE, "Ljava/lang/reflect/Type;", "result", "", "getConstructorInfo", "Lorg/jetbrains/kotlin/container/ConstructorInfo;", "c", "Ljava/lang/Class;", "getDefaultImplementation", "klass", "getRegistrations", "", "getSetterInfos", "Lorg/jetbrains/kotlin/container/SetterInfo;", "traverseClass", "Lorg/jetbrains/kotlin/container/ClassInfo;", "getInfo", "container"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CacheKt {
    public static final ClassInfo getInfo(Class<?> cls) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        return ClassTraversalCache.INSTANCE.getClassInfo(cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassInfo traverseClass(Class<?> cls) {
        return new ClassInfo(getConstructorInfo(cls), getSetterInfos(cls), getRegistrations(cls), getDefaultImplementation(cls));
    }

    private static final List<SetterInfo> getSetterInfos(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        Method[] methods = cls.getMethods();
        Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
        for (Method method : methods) {
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "getDeclaredAnnotations(...)");
            for (Annotation annotation : declaredAnnotations) {
                String name2 = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation)).getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (StringsKt.endsWith$default(name2, ".Inject", false, 2, (Object) null)) {
                    Intrinsics.checkNotNull(method);
                    Type[] genericParameterTypes = method.getGenericParameterTypes();
                    Intrinsics.checkNotNullExpressionValue(genericParameterTypes, "getGenericParameterTypes(...)");
                    arrayList.add(new SetterInfo(method, ArraysKt.toList(genericParameterTypes)));
                }
            }
        }
        return arrayList;
    }

    private static final ConstructorInfo getConstructorInfo(Class<?> cls) {
        List list;
        if (Modifier.isAbstract(cls.getModifiers()) || cls.isPrimitive()) {
            return null;
        }
        Constructor<?>[] constructors = cls.getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        ArrayList arrayList = new ArrayList();
        for (Constructor<?> constructor : constructors) {
            Constructor<?> constructor2 = constructor;
            if (Modifier.isPublic(constructor2.getModifiers()) && !constructor2.isSynthetic()) {
                arrayList.add(constructor);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.size() != 1) {
            return null;
        }
        Constructor constructor3 = (Constructor) CollectionsKt.single((List) arrayList2);
        if (cls.getDeclaringClass() != null && !Modifier.isStatic(cls.getModifiers())) {
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.add(cls.getDeclaringClass());
            Type[] genericParameterTypes = constructor3.getGenericParameterTypes();
            Intrinsics.checkNotNullExpressionValue(genericParameterTypes, "getGenericParameterTypes(...)");
            spreadBuilder.addSpread(genericParameterTypes);
            list = CollectionsKt.listOf(spreadBuilder.toArray(new Type[spreadBuilder.size()]));
        } else {
            Type[] genericParameterTypes2 = constructor3.getGenericParameterTypes();
            Intrinsics.checkNotNullExpressionValue(genericParameterTypes2, "getGenericParameterTypes(...)");
            list = ArraysKt.toList(genericParameterTypes2);
        }
        Intrinsics.checkNotNull(constructor3);
        return new ConstructorInfo(constructor3, list);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0019  */
    private static final void collectInterfacesRecursive(Type type, Set<Type> set) {
        Class cls;
        Type[] genericInterfaces;
        if (type instanceof Class) {
            cls = (Class) type;
        } else if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                cls = (Class) rawType;
            } else {
                cls = null;
            }
        } else {
            cls = null;
        }
        if (cls == null || (genericInterfaces = cls.getGenericInterfaces()) == null) {
            return;
        }
        for (Type type2 : genericInterfaces) {
            Intrinsics.checkNotNull(type2);
            if (set.add(type2)) {
                collectInterfacesRecursive(type2, set);
            }
        }
    }

    private static final Class<?> getDefaultImplementation(Class<?> cls) {
        DefaultImplementation defaultImplementation = (DefaultImplementation) cls.getAnnotation(DefaultImplementation.class);
        if (defaultImplementation != null) {
            return defaultImplementation.impl();
        }
        return null;
    }

    private static final List<Type> getRegistrations(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        Sequence sequenceGenerateSequence = SequencesKt.generateSequence(cls, new Function1<Type, Type>() { // from class: org.jetbrains.kotlin.container.CacheKt$getRegistrations$superClasses$1
            @Override // kotlin.jvm.functions.Function1
            public final Type invoke(Type it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it instanceof Class) {
                    return ((Class) it).getGenericSuperclass();
                }
                if (it instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) it).getRawType();
                    Class cls2 = rawType instanceof Class ? (Class) rawType : null;
                    if (cls2 != null) {
                        return cls2.getGenericSuperclass();
                    }
                }
                return null;
            }
        });
        CollectionsKt.addAll(arrayList, sequenceGenerateSequence);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = sequenceGenerateSequence.iterator();
        while (it.hasNext()) {
            collectInterfacesRecursive((Type) it.next(), linkedHashSet);
        }
        arrayList.addAll(linkedHashSet);
        arrayList.remove(Object.class);
        return arrayList;
    }
}
