package org.jetbrains.kotlin.scripting.resolve;

import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptDescriptorUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0019\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006\u0007"}, d2 = {"classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lkotlin/reflect/KClass;", "getClassId", "(Lkotlin/reflect/KClass;)Lorg/jetbrains/kotlin/name/ClassId;", "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KType;)Lorg/jetbrains/kotlin/name/ClassId;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptDescriptorUtilKt {
    public static final ClassId getClassId(KClass<?> kClass) {
        ClassId classIdCreateNestedClassId;
        KClass kotlinClass;
        ClassId classId;
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Class<?> enclosingClass = JvmClassMappingKt.getJavaClass((KClass) kClass).getEnclosingClass();
        if (enclosingClass == null || (kotlinClass = JvmClassMappingKt.getKotlinClass(enclosingClass)) == null || (classId = getClassId((KClass<?>) kotlinClass)) == null) {
            classIdCreateNestedClassId = null;
        } else {
            String simpleName = kClass.getSimpleName();
            Intrinsics.checkNotNull(simpleName);
            classIdCreateNestedClassId = classId.createNestedClassId(Name.identifier(simpleName));
        }
        if (classIdCreateNestedClassId != null) {
            return classIdCreateNestedClassId;
        }
        String qualifiedName = kClass.getQualifiedName();
        Intrinsics.checkNotNull(qualifiedName);
        ClassId classId2 = ClassId.topLevel(new FqName(qualifiedName));
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        return classId2;
    }

    public static final ClassId getClassId(KType kType) {
        Intrinsics.checkNotNullParameter(kType, "<this>");
        KClassifier classifier = kType.getClassifier();
        if (classifier == null) {
            return null;
        }
        KClass kClass = classifier instanceof KClass ? (KClass) classifier : null;
        if (kClass != null) {
            return getClassId((KClass<?>) kClass);
        }
        return null;
    }
}
