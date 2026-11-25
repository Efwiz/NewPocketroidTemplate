package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;

/* JADX INFO: compiled from: ReflectJavaConstructor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0011\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004¢\u0006\u0002\u0010\u0005R\u0018\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaConstructor;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaMember;", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", "member", "Ljava/lang/reflect/Constructor;", "(Ljava/lang/reflect/Constructor;)V", "getMember", "()Ljava/lang/reflect/Constructor;", "typeParameters", "", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "valueParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "getValueParameters", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaConstructor extends ReflectJavaMember implements JavaConstructor {
    private final Constructor<?> member;

    public ReflectJavaConstructor(Constructor<?> member) {
        Intrinsics.checkNotNullParameter(member, "member");
        this.member = member;
    }

    @Override // org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaMember
    public Constructor<?> getMember() {
        return this.member;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaConstructor
    public List<JavaValueParameter> getValueParameters() {
        Type[] genericParameterTypes = getMember().getGenericParameterTypes();
        Intrinsics.checkNotNull(genericParameterTypes);
        if (genericParameterTypes.length == 0) {
            return CollectionsKt.emptyList();
        }
        Class<?> declaringClass = getMember().getDeclaringClass();
        if (declaringClass.getDeclaringClass() != null && !Modifier.isStatic(declaringClass.getModifiers())) {
            genericParameterTypes = (Type[]) ArraysKt.copyOfRange(genericParameterTypes, 1, genericParameterTypes.length);
        }
        Annotation[][] parameterAnnotations = getMember().getParameterAnnotations();
        Annotation[][] annotationArr = parameterAnnotations;
        if (annotationArr.length < genericParameterTypes.length) {
            throw new IllegalStateException("Illegal generic signature: " + getMember());
        }
        if (annotationArr.length > genericParameterTypes.length) {
            Intrinsics.checkNotNull(parameterAnnotations);
            parameterAnnotations = (Annotation[][]) ArraysKt.copyOfRange(annotationArr, annotationArr.length - genericParameterTypes.length, annotationArr.length);
        }
        Intrinsics.checkNotNull(genericParameterTypes);
        Intrinsics.checkNotNull(parameterAnnotations);
        return getValueParameters(genericParameterTypes, parameterAnnotations, getMember().isVarArgs());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters */
    public List<ReflectJavaTypeParameter> mo7258getTypeParameters() {
        TypeVariable<Constructor<?>>[] typeParameters = getMember().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        TypeVariable<Constructor<?>>[] typeVariableArr = typeParameters;
        ArrayList arrayList = new ArrayList(typeVariableArr.length);
        for (TypeVariable<Constructor<?>> typeVariable : typeVariableArr) {
            arrayList.add(new ReflectJavaTypeParameter(typeVariable));
        }
        return arrayList;
    }
}
