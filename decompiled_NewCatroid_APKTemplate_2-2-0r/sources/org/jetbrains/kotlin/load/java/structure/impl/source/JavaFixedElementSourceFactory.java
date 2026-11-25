package org.jetbrains.kotlin.load.java.structure.impl.source;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;

/* JADX INFO: compiled from: JavaElementSourceFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\bH\u0016J(\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\bH\u0016J&\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00042\u000e\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J%\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00140\b\"\b\b\u0000\u0010\u0014*\u00020\u00152\u0006\u0010\u0016\u001a\u0002H\u0014H\u0016¢\u0006\u0002\u0010\u0017J&\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00042\u000e\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\b2\u0006\u0010\u0019\u001a\u00020\u0012H\u0016J0\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u000e\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\b2\u0006\u0010\u001c\u001a\u00020\u0012H\u0016J%\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\u001e\u001a\u0002H\u0005H\u0016¢\u0006\u0002\u0010\u001fJ(\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u000e\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\"0\bH\u0016¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaFixedElementSourceFactory;", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "()V", "createExpressionTypeSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementTypeSource;", "TYPE", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "psiExpressionSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiExpression;", "createMethodReturnTypeSource", "psiMethodSource", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "createPermittedTypeSource", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "psiTypeParameterSource", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "permittedTypeIndex", "", "createPsiSource", "PSI", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "psi", "(Lcom/intellij/psi/PsiElement;)Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "createSuperTypeSource", "superTypeIndex", "createTypeParameterUpperBoundTypeSource", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "boundIndex", "createTypeSource", ModuleXmlParser.TYPE, "(Lcom/intellij/psi/PsiType;)Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementTypeSource;", "createVariableReturnTypeSource", "psiVariableSource", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiVariable;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaFixedElementSourceFactory extends JavaElementSourceFactory {
    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory
    public <PSI extends PsiElement> JavaElementPsiSource<PSI> createPsiSource(PSI psi) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        return new JavaElementPsiSourceWithFixedPsi(psi);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory
    public <TYPE extends PsiType> JavaElementTypeSource<TYPE> createTypeSource(TYPE type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new JavaElementTypeSourceWithFixedType(type, this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory
    public <TYPE extends PsiType> JavaElementTypeSource<TYPE> createVariableReturnTypeSource(JavaElementPsiSource<? extends PsiVariable> psiVariableSource) {
        Intrinsics.checkNotNullParameter(psiVariableSource, "psiVariableSource");
        PsiType type = ((PsiVariable) psiVariableSource.getPsi()).getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type TYPE of org.jetbrains.kotlin.load.java.structure.impl.source.JavaFixedElementSourceFactory.createVariableReturnTypeSource");
        return createTypeSource(type);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory
    public <TYPE extends PsiType> JavaElementTypeSource<TYPE> createTypeParameterUpperBoundTypeSource(JavaElementPsiSource<? extends PsiTypeParameter> psiTypeParameterSource, int boundIndex) {
        Intrinsics.checkNotNullParameter(psiTypeParameterSource, "psiTypeParameterSource");
        Object obj = ((PsiTypeParameter) psiTypeParameterSource.getPsi()).getBounds()[boundIndex];
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type TYPE of org.jetbrains.kotlin.load.java.structure.impl.source.JavaFixedElementSourceFactory.createTypeParameterUpperBoundTypeSource");
        return createTypeSource((PsiType) obj);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory
    public JavaElementTypeSource<PsiClassType> createSuperTypeSource(JavaElementPsiSource<? extends PsiClass> psiTypeParameterSource, int superTypeIndex) {
        Intrinsics.checkNotNullParameter(psiTypeParameterSource, "psiTypeParameterSource");
        PsiClassType psiClassType = ((PsiClass) psiTypeParameterSource.getPsi()).getSuperTypes()[superTypeIndex];
        Intrinsics.checkNotNullExpressionValue(psiClassType, "get(...)");
        return createTypeSource(psiClassType);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory
    public JavaElementTypeSource<PsiClassType> createPermittedTypeSource(JavaElementPsiSource<? extends PsiClass> psiTypeParameterSource, int permittedTypeIndex) {
        Intrinsics.checkNotNullParameter(psiTypeParameterSource, "psiTypeParameterSource");
        PsiClassType psiClassType = ((PsiClass) psiTypeParameterSource.getPsi()).getPermitsListTypes()[permittedTypeIndex];
        Intrinsics.checkNotNullExpressionValue(psiClassType, "get(...)");
        return createTypeSource(psiClassType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory
    public <TYPE extends PsiType> JavaElementTypeSource<TYPE> createExpressionTypeSource(JavaElementPsiSource<? extends PsiExpression> psiExpressionSource) {
        Intrinsics.checkNotNullParameter(psiExpressionSource, "psiExpressionSource");
        PsiType type = ((PsiExpression) psiExpressionSource.getPsi()).getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type TYPE of org.jetbrains.kotlin.load.java.structure.impl.source.JavaFixedElementSourceFactory.createExpressionTypeSource");
        return createTypeSource(type);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory
    public <TYPE extends PsiType> JavaElementTypeSource<TYPE> createMethodReturnTypeSource(JavaElementPsiSource<? extends PsiMethod> psiMethodSource) {
        Intrinsics.checkNotNullParameter(psiMethodSource, "psiMethodSource");
        PsiType returnType = ((PsiMethod) psiMethodSource.getPsi()).getReturnType();
        if (returnType == null) {
            throw new IllegalStateException(("Method is not a constructor and has no return type: " + ((PsiMethod) psiMethodSource.getPsi()).getName()).toString());
        }
        return createTypeSource(returnType);
    }
}
