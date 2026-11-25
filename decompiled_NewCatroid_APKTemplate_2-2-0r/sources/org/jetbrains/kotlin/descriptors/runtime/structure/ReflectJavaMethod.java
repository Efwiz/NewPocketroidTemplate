package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;

/* JADX INFO: compiled from: ReflectJavaMethod.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0014¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaMethod;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaMember;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "member", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "annotationParameterDefaultValue", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "getAnnotationParameterDefaultValue", "()Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "getMember", "()Ljava/lang/reflect/Method;", "returnType", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaType;", "getReturnType", "()Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaType;", "typeParameters", "", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "valueParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "getValueParameters", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaMethod extends ReflectJavaMember implements JavaMethod {
    private final Method member;

    public ReflectJavaMethod(Method member) {
        Intrinsics.checkNotNullParameter(member, "member");
        this.member = member;
    }

    @Override // org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaMember
    public Method getMember() {
        return this.member;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public List<JavaValueParameter> getValueParameters() {
        Type[] genericParameterTypes = getMember().getGenericParameterTypes();
        Intrinsics.checkNotNullExpressionValue(genericParameterTypes, "getGenericParameterTypes(...)");
        Annotation[][] parameterAnnotations = getMember().getParameterAnnotations();
        Intrinsics.checkNotNullExpressionValue(parameterAnnotations, "getParameterAnnotations(...)");
        return getValueParameters(genericParameterTypes, parameterAnnotations, getMember().isVarArgs());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public ReflectJavaType getReturnType() {
        ReflectJavaType.Companion companion = ReflectJavaType.INSTANCE;
        Type genericReturnType = getMember().getGenericReturnType();
        Intrinsics.checkNotNullExpressionValue(genericReturnType, "getGenericReturnType(...)");
        return companion.create(genericReturnType);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public JavaAnnotationArgument getAnnotationParameterDefaultValue() {
        Object defaultValue = getMember().getDefaultValue();
        return defaultValue != null ? ReflectJavaAnnotationArgument.INSTANCE.create(defaultValue, null) : null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters */
    public List<ReflectJavaTypeParameter> mo7258getTypeParameters() {
        TypeVariable<Method>[] typeParameters = getMember().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        TypeVariable<Method>[] typeVariableArr = typeParameters;
        ArrayList arrayList = new ArrayList(typeVariableArr.length);
        for (TypeVariable<Method> typeVariable : typeVariableArr) {
            arrayList.add(new ReflectJavaTypeParameter(typeVariable));
        }
        return arrayList;
    }
}
