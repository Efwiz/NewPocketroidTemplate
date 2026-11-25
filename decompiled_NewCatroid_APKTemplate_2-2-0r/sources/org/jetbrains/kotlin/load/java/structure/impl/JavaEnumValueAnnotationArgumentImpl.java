package org.jetbrains.kotlin.load.java.structure.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: annotationArgumentsImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/JavaEnumValueAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/JavaEnumValueAnnotationArgument;", "psiReferenceSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceExpression;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;Lorg/jetbrains/kotlin/name/Name;)V", "entryName", "getEntryName", "()Lorg/jetbrains/kotlin/name/Name;", "enumClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getEnumClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaEnumValueAnnotationArgumentImpl extends JavaAnnotationArgumentImpl implements JavaEnumValueAnnotationArgument {
    private final JavaElementPsiSource<PsiReferenceExpression> psiReferenceSource;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaEnumValueAnnotationArgumentImpl(JavaElementPsiSource<PsiReferenceExpression> psiReferenceSource, Name name2) {
        super(name2, null);
        Intrinsics.checkNotNullParameter(psiReferenceSource, "psiReferenceSource");
        this.psiReferenceSource = psiReferenceSource;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument
    public ClassId getEnumClassId() {
        PsiElement psiElementResolve = ((PsiReferenceExpression) this.psiReferenceSource.getPsi()).resolve();
        if (psiElementResolve instanceof PsiEnumConstant) {
            JavaClass containingClass = new JavaFieldImpl((JavaElementPsiSource<PsiField>) this.psiReferenceSource.getFactory().createPsiSource(psiElementResolve)).getContainingClass();
            Intrinsics.checkNotNullExpressionValue(containingClass, "<get-containingClass>(...)");
            return JavaElementsKt.getClassId(containingClass);
        }
        PsiElement qualifier = ((PsiReferenceExpression) this.psiReferenceSource.getPsi()).getQualifier();
        PsiReferenceExpression psiReferenceExpression = qualifier instanceof PsiReferenceExpression ? (PsiReferenceExpression) qualifier : null;
        String qualifiedName = psiReferenceExpression != null ? psiReferenceExpression.getQualifiedName() : null;
        if (qualifiedName == null) {
            return null;
        }
        return ClassId.topLevel(new FqName(qualifiedName));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument
    public Name getEntryName() {
        String referenceName = ((PsiReferenceExpression) this.psiReferenceSource.getPsi()).getReferenceName();
        if (referenceName != null) {
            return Name.identifier(referenceName);
        }
        return null;
    }
}
