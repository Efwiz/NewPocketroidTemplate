package org.jetbrains.kotlin.load.java.structure.impl.source;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;

/* JADX INFO: compiled from: JavaSourceFactoryOwner.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J(\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\b\b\u0000\u0010\b*\u00020\t2\u000e\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u000bH\u0016J%\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u000b\"\b\b\u0000\u0010\u000e*\u00020\u000f2\u0006\u0010\u0010\u001a\u0002H\u000eH\u0016¢\u0006\u0002\u0010\u0011J%\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\b\b\u0000\u0010\b*\u00020\t2\u0006\u0010\u0013\u001a\u0002H\bH\u0016¢\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\b\b\u0000\u0010\b*\u00020\t2\u000e\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00170\u000bH\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0018À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaSourceFactoryOwner;", "", "sourceFactory", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "getSourceFactory", "()Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "createMethodReturnTypeSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementTypeSource;", "TYPE", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "psiMethodSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "createPsiSource", "PSI", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "psi", "(Lcom/intellij/psi/PsiElement;)Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "createTypeSource", ModuleXmlParser.TYPE, "(Lcom/intellij/psi/PsiType;)Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementTypeSource;", "createVariableReturnTypeSource", "psiVariableSource", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiVariable;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaSourceFactoryOwner {
    JavaElementSourceFactory getSourceFactory();

    default <PSI extends PsiElement> JavaElementPsiSource<PSI> createPsiSource(PSI psi) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        return getSourceFactory().createPsiSource(psi);
    }

    default <TYPE extends PsiType> JavaElementTypeSource<TYPE> createTypeSource(TYPE type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return getSourceFactory().createTypeSource(type);
    }

    default <TYPE extends PsiType> JavaElementTypeSource<TYPE> createVariableReturnTypeSource(JavaElementPsiSource<? extends PsiVariable> psiVariableSource) {
        Intrinsics.checkNotNullParameter(psiVariableSource, "psiVariableSource");
        return getSourceFactory().createVariableReturnTypeSource(psiVariableSource);
    }

    default <TYPE extends PsiType> JavaElementTypeSource<TYPE> createMethodReturnTypeSource(JavaElementPsiSource<? extends PsiMethod> psiMethodSource) {
        Intrinsics.checkNotNullParameter(psiMethodSource, "psiMethodSource");
        return getSourceFactory().createMethodReturnTypeSource(psiMethodSource);
    }
}
