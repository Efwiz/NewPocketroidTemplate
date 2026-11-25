package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.JavaMember;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: ReflectJavaMember.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u0013\u0010\u001d\u001a\u00020\u000f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J=\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0012\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020'0$0$2\u0006\u0010(\u001a\u00020\u000fH\u0004¢\u0006\u0002\u0010)J\b\u0010*\u001a\u00020\u0016H\u0016J\b\u0010+\u001a\u00020,H\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaMember;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaElement;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotationOwner;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaModifierListOwner;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMember;", "()V", "containingClass", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaClass;", "getContainingClass", "()Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaClass;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Ljava/lang/reflect/AnnotatedElement;", "getElement", "()Ljava/lang/reflect/AnnotatedElement;", "isFromSource", "", "()Z", "member", "Ljava/lang/reflect/Member;", "getMember", "()Ljava/lang/reflect/Member;", "modifiers", "", "getModifiers", "()I", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", Namer.EQUALS_METHOD_NAME, "other", "", "getValueParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "parameterTypes", "", "Ljava/lang/reflect/Type;", "parameterAnnotations", "", "isVararg", "([Ljava/lang/reflect/Type;[[Ljava/lang/annotation/Annotation;Z)Ljava/util/List;", "hashCode", "toString", "", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ReflectJavaMember extends ReflectJavaElement implements ReflectJavaAnnotationOwner, ReflectJavaModifierListOwner, JavaMember {
    public abstract Member getMember();

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public final boolean isFromSource() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaAnnotationOwner
    public AnnotatedElement getElement() {
        Member member = getMember();
        Intrinsics.checkNotNull(member, "null cannot be cast to non-null type java.lang.reflect.AnnotatedElement");
        return (AnnotatedElement) member;
    }

    @Override // org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaModifierListOwner
    public int getModifiers() {
        return getMember().getModifiers();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        String name2 = getMember().getName();
        Name nameIdentifier = name2 != null ? Name.identifier(name2) : null;
        return nameIdentifier == null ? SpecialNames.NO_NAME_PROVIDED : nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMember
    /* JADX INFO: renamed from: getContainingClass */
    public ReflectJavaClass mo7272getContainingClass() {
        Class<?> declaringClass = getMember().getDeclaringClass();
        Intrinsics.checkNotNullExpressionValue(declaringClass, "getDeclaringClass(...)");
        return new ReflectJavaClass(declaringClass);
    }

    protected final List<JavaValueParameter> getValueParameters(Type[] parameterTypes, Annotation[][] parameterAnnotations, boolean isVararg) throws IllegalAccessException, InvocationTargetException {
        String str;
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(parameterAnnotations, "parameterAnnotations");
        ArrayList arrayList = new ArrayList(parameterTypes.length);
        List<String> listLoadParameterNames = Java8ParameterNamesLoader.INSTANCE.loadParameterNames(getMember());
        int size = listLoadParameterNames != null ? listLoadParameterNames.size() - parameterTypes.length : 0;
        int length = parameterTypes.length;
        int i = 0;
        while (i < length) {
            ReflectJavaType reflectJavaTypeCreate = ReflectJavaType.INSTANCE.create(parameterTypes[i]);
            if (listLoadParameterNames != null) {
                str = (String) CollectionsKt.getOrNull(listLoadParameterNames, i + size);
                if (str == null) {
                    throw new IllegalStateException(("No parameter with index " + i + '+' + size + " (name=" + getName() + " type=" + reflectJavaTypeCreate + ") in " + this).toString());
                }
            } else {
                str = null;
            }
            arrayList.add(new ReflectJavaValueParameter(reflectJavaTypeCreate, parameterAnnotations[i], str, isVararg && i == ArraysKt.getLastIndex(parameterTypes)));
            i++;
        }
        return arrayList;
    }

    public boolean equals(Object other) {
        return (other instanceof ReflectJavaMember) && Intrinsics.areEqual(getMember(), ((ReflectJavaMember) other).getMember());
    }

    public int hashCode() {
        return getMember().hashCode();
    }

    public String toString() {
        return getClass().getName() + ": " + getMember();
    }
}
