package org.jetbrains.kotlin.load.java.structure.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: annotationArgumentsImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0011\b\u0004\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0005\b\t\n\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/JavaAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "Factory", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaAnnotationAsAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaArrayAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassObjectAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaEnumValueAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaUnknownAnnotationArgumentImpl;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JavaAnnotationArgumentImpl implements JavaAnnotationArgument {

    /* JADX INFO: renamed from: Factory, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Name name;

    public /* synthetic */ JavaAnnotationArgumentImpl(Name name2, DefaultConstructorMarker defaultConstructorMarker) {
        this(name2);
    }

    private JavaAnnotationArgumentImpl(Name name2) {
        this.name = name2;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument
    public Name getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.load.java.structure.impl.JavaAnnotationArgumentImpl$Factory, reason: from kotlin metadata */
    /* JADX INFO: compiled from: annotationArgumentsImpl.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/JavaAnnotationArgumentImpl$Factory;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "argument", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "name", "Lorg/jetbrains/kotlin/name/Name;", "sourceFactory", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JavaAnnotationArgument create(PsiAnnotationMemberValue argument, Name name2, JavaElementSourceFactory sourceFactory) {
            Intrinsics.checkNotNullParameter(argument, "argument");
            Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
            if (argument instanceof PsiClassObjectAccessExpression) {
                return new JavaClassObjectAnnotationArgumentImpl(sourceFactory.createPsiSource(argument), name2);
            }
            PsiAnnotationMemberValue psiAnnotationMemberValue = argument;
            Object objComputeConstantExpression = JavaPsiFacade.getInstance(argument.getProject()).getConstantEvaluationHelper().computeConstantExpression(psiAnnotationMemberValue);
            if (objComputeConstantExpression instanceof Enum) {
                return new JavaEnumValueAnnotationArgumentImpl(sourceFactory.createPsiSource((PsiReferenceExpression) argument), name2);
            }
            if (objComputeConstantExpression != null || (argument instanceof PsiLiteralExpression)) {
                return new JavaLiteralAnnotationArgumentImpl(name2, objComputeConstantExpression);
            }
            if (argument instanceof PsiReferenceExpression) {
                return new JavaEnumValueAnnotationArgumentImpl(sourceFactory.createPsiSource(psiAnnotationMemberValue), name2);
            }
            if (argument instanceof PsiArrayInitializerMemberValue) {
                return new JavaArrayAnnotationArgumentImpl(sourceFactory.createPsiSource(psiAnnotationMemberValue), name2);
            }
            return argument instanceof PsiAnnotation ? new JavaAnnotationAsAnnotationArgumentImpl(sourceFactory.createPsiSource(psiAnnotationMemberValue), name2) : new JavaUnknownAnnotationArgumentImpl(name2);
        }
    }
}
