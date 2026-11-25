package org.jetbrains.kotlin.load.java.structure.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.load.java.structure.JavaClassObjectAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: annotationArgumentsImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\f\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassObjectAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassObjectAnnotationArgument;", "psiExpressionSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassObjectAccessExpression;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;Lorg/jetbrains/kotlin/name/Name;)V", "getReferencedType", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaTypeImpl;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassObjectAnnotationArgumentImpl extends JavaAnnotationArgumentImpl implements JavaClassObjectAnnotationArgument {
    private final JavaElementPsiSource<PsiClassObjectAccessExpression> psiExpressionSource;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaClassObjectAnnotationArgumentImpl(JavaElementPsiSource<PsiClassObjectAccessExpression> psiExpressionSource, Name name2) {
        super(name2, null);
        Intrinsics.checkNotNullParameter(psiExpressionSource, "psiExpressionSource");
        this.psiExpressionSource = psiExpressionSource;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassObjectAnnotationArgument
    public JavaTypeImpl<?> getReferencedType() {
        PsiTypeElement operand = ((PsiClassObjectAccessExpression) this.psiExpressionSource.getPsi()).getOperand();
        Intrinsics.checkNotNullExpressionValue(operand, "getOperand(...)");
        JavaTypeImpl<?> javaTypeImplCreate = JavaTypeImpl.create(operand.getType(), this.psiExpressionSource.getFactory().createTypeSource(operand.getType()));
        Intrinsics.checkNotNullExpressionValue(javaTypeImplCreate, "create(...)");
        return javaTypeImplCreate;
    }
}
