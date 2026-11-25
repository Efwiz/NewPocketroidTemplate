package org.jetbrains.kotlin.descriptors.runtime.structure;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: reflectClassUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\u001a\u000e\u0010#\u001a\u00020$*\u0006\u0012\u0002\b\u00030\u0002\"&\u0010\u0000\u001a\u001a\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001c\u0010\u0005\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\",\u0010\t\u001a \u0012\u000e\u0012\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\u0002\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\",\u0010\n\u001a \u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0002\u0012\u000e\u0012\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0019\u0010\u000b\u001a\u00020\f*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\"\u0019\u0010\u000f\u001a\u00020\u0010*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\"\u001b\u0010\u0013\u001a\u0004\u0018\u00010\u0004*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\"\u001b\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0006*\u00020\u00178F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019\"\u001f\u0010\u001a\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0002*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c\"\u0019\u0010\u001d\u001a\u00020\u001e*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 \"\u001f\u0010!\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0002*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\"\u0010\u001c¨\u0006%"}, d2 = {"FUNCTION_CLASSES", "", "Ljava/lang/Class;", "Lkotlin/Function;", "", "PRIMITIVE_CLASSES", "", "Lkotlin/reflect/KClass;", "", "PRIMITIVE_TO_WRAPPER", "WRAPPER_TO_PRIMITIVE", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "(Ljava/lang/Class;)Lorg/jetbrains/kotlin/name/ClassId;", "desc", "", "getDesc", "(Ljava/lang/Class;)Ljava/lang/String;", "functionClassArity", "getFunctionClassArity", "(Ljava/lang/Class;)Ljava/lang/Integer;", "parameterizedTypeArguments", "Ljava/lang/reflect/Type;", "getParameterizedTypeArguments", "(Ljava/lang/reflect/Type;)Ljava/util/List;", "primitiveByWrapper", "getPrimitiveByWrapper", "(Ljava/lang/Class;)Ljava/lang/Class;", "safeClassLoader", "Ljava/lang/ClassLoader;", "getSafeClassLoader", "(Ljava/lang/Class;)Ljava/lang/ClassLoader;", "wrapperByPrimitive", "getWrapperByPrimitive", "isEnumClassOrSpecializedEnumEntryClass", "", "descriptors.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReflectClassUtilKt {
    private static final Map<Class<? extends Function<?>>, Integer> FUNCTION_CLASSES;
    private static final List<KClass<? extends Object>> PRIMITIVE_CLASSES;
    private static final Map<Class<? extends Object>, Class<? extends Object>> PRIMITIVE_TO_WRAPPER;
    private static final Map<Class<? extends Object>, Class<? extends Object>> WRAPPER_TO_PRIMITIVE;

    public static final ClassLoader getSafeClassLoader(Class<?> cls) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        ClassLoader classLoader = cls.getClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        Intrinsics.checkNotNullExpressionValue(systemClassLoader, "getSystemClassLoader(...)");
        return systemClassLoader;
    }

    public static final boolean isEnumClassOrSpecializedEnumEntryClass(Class<?> cls) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        return Enum.class.isAssignableFrom(cls);
    }

    static {
        int i = 0;
        List<KClass<? extends Object>> listListOf = CollectionsKt.listOf((Object[]) new KClass[]{Reflection.getOrCreateKotlinClass(Boolean.TYPE), Reflection.getOrCreateKotlinClass(Byte.TYPE), Reflection.getOrCreateKotlinClass(Character.TYPE), Reflection.getOrCreateKotlinClass(Double.TYPE), Reflection.getOrCreateKotlinClass(Float.TYPE), Reflection.getOrCreateKotlinClass(Integer.TYPE), Reflection.getOrCreateKotlinClass(Long.TYPE), Reflection.getOrCreateKotlinClass(Short.TYPE)});
        PRIMITIVE_CLASSES = listListOf;
        List<KClass<? extends Object>> list = listListOf;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KClass kClass = (KClass) it.next();
            arrayList.add(TuplesKt.to(JvmClassMappingKt.getJavaObjectType(kClass), JvmClassMappingKt.getJavaPrimitiveType(kClass)));
        }
        WRAPPER_TO_PRIMITIVE = MapsKt.toMap(arrayList);
        List<KClass<? extends Object>> list2 = PRIMITIVE_CLASSES;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            KClass kClass2 = (KClass) it2.next();
            arrayList2.add(TuplesKt.to(JvmClassMappingKt.getJavaPrimitiveType(kClass2), JvmClassMappingKt.getJavaObjectType(kClass2)));
        }
        PRIMITIVE_TO_WRAPPER = MapsKt.toMap(arrayList2);
        List listListOf2 = CollectionsKt.listOf((Object[]) new Class[]{Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class});
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf2, 10));
        for (Object obj : listListOf2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList3.add(TuplesKt.to((Class) obj, Integer.valueOf(i)));
            i = i2;
        }
        FUNCTION_CLASSES = MapsKt.toMap(arrayList3);
    }

    public static final Class<?> getPrimitiveByWrapper(Class<?> cls) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        return WRAPPER_TO_PRIMITIVE.get(cls);
    }

    public static final Class<?> getWrapperByPrimitive(Class<?> cls) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        return PRIMITIVE_TO_WRAPPER.get(cls);
    }

    public static final Integer getFunctionClassArity(Class<?> cls) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        return FUNCTION_CLASSES.get(cls);
    }

    public static final ClassId getClassId(Class<?> cls) {
        ClassId classIdCreateNestedClassId;
        ClassId classId;
        Intrinsics.checkNotNullParameter(cls, "<this>");
        if (cls.isPrimitive()) {
            throw new IllegalArgumentException("Can't compute ClassId for primitive type: " + cls);
        }
        if (cls.isArray()) {
            throw new IllegalArgumentException("Can't compute ClassId for array type: " + cls);
        }
        if (cls.getEnclosingMethod() == null && cls.getEnclosingConstructor() == null) {
            String simpleName = cls.getSimpleName();
            Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
            if (simpleName.length() != 0) {
                Class<?> declaringClass = cls.getDeclaringClass();
                if (declaringClass == null || (classId = getClassId(declaringClass)) == null || (classIdCreateNestedClassId = classId.createNestedClassId(Name.identifier(cls.getSimpleName()))) == null) {
                    classIdCreateNestedClassId = ClassId.topLevel(new FqName(cls.getName()));
                }
                Intrinsics.checkNotNull(classIdCreateNestedClassId);
                return classIdCreateNestedClassId;
            }
        }
        FqName fqName = new FqName(cls.getName());
        return new ClassId(fqName.parent(), FqName.topLevel(fqName.shortName()), true);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final String getDesc(Class<?> cls) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        if (!cls.isPrimitive()) {
            if (cls.isArray()) {
                String name2 = cls.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                return StringsKt.replace$default(name2, '.', '/', false, 4, (Object) null);
            }
            StringBuilder sb = new StringBuilder("L");
            String name3 = cls.getName();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            return sb.append(StringsKt.replace$default(name3, '.', '/', false, 4, (Object) null)).append(';').toString();
        }
        String name4 = cls.getName();
        if (name4 != null) {
            switch (name4.hashCode()) {
                case -1325958191:
                    if (name4.equals("double")) {
                        return "D";
                    }
                    break;
                case 104431:
                    if (name4.equals("int")) {
                        return "I";
                    }
                    break;
                case 3039496:
                    if (name4.equals("byte")) {
                        return "B";
                    }
                    break;
                case 3052374:
                    if (name4.equals(PsiKeyword.CHAR)) {
                        return "C";
                    }
                    break;
                case 3327612:
                    if (name4.equals("long")) {
                        return "J";
                    }
                    break;
                case 3625364:
                    if (name4.equals(PsiKeyword.VOID)) {
                        return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
                    }
                    break;
                case 64711720:
                    if (name4.equals("boolean")) {
                        return "Z";
                    }
                    break;
                case 97526364:
                    if (name4.equals("float")) {
                        return "F";
                    }
                    break;
                case 109413500:
                    if (name4.equals("short")) {
                        return ExifInterface.LATITUDE_SOUTH;
                    }
                    break;
            }
        }
        throw new IllegalArgumentException("Unsupported primitive type: " + cls);
    }

    public static final List<Type> getParameterizedTypeArguments(Type type) {
        Intrinsics.checkNotNullParameter(type, "<this>");
        if (!(type instanceof ParameterizedType)) {
            return CollectionsKt.emptyList();
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        if (parameterizedType.getOwnerType() != null) {
            return SequencesKt.toList(SequencesKt.flatMap(SequencesKt.generateSequence(type, new Function1<ParameterizedType, ParameterizedType>() { // from class: org.jetbrains.kotlin.descriptors.runtime.structure.ReflectClassUtilKt$parameterizedTypeArguments$1
                @Override // kotlin.jvm.functions.Function1
                public final ParameterizedType invoke(ParameterizedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Type ownerType = it.getOwnerType();
                    if (ownerType instanceof ParameterizedType) {
                        return (ParameterizedType) ownerType;
                    }
                    return null;
                }
            }), new Function1<ParameterizedType, Sequence<? extends Type>>() { // from class: org.jetbrains.kotlin.descriptors.runtime.structure.ReflectClassUtilKt$parameterizedTypeArguments$2
                @Override // kotlin.jvm.functions.Function1
                public final Sequence<Type> invoke(ParameterizedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Type[] actualTypeArguments = it.getActualTypeArguments();
                    Intrinsics.checkNotNullExpressionValue(actualTypeArguments, "getActualTypeArguments(...)");
                    return ArraysKt.asSequence(actualTypeArguments);
                }
            }));
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Intrinsics.checkNotNullExpressionValue(actualTypeArguments, "getActualTypeArguments(...)");
        return ArraysKt.toList(actualTypeArguments);
    }
}
