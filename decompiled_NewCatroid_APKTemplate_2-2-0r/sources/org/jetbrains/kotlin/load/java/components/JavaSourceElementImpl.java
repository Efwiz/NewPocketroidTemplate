package org.jetbrains.kotlin.load.java.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElement;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.impl.JavaElementImpl;
import org.jetbrains.kotlin.resolve.source.PsiSourceElement;

/* JADX INFO: compiled from: JavaSourceElementFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/JavaSourceElementImpl;", "Lorg/jetbrains/kotlin/resolve/source/PsiSourceElement;", "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElement;", "javaElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "getJavaElement", "()Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getPsi", "()Lcom/intellij/psi/PsiElement;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JavaSourceElementImpl implements JavaSourceElement, PsiSourceElement {
    private final JavaElement javaElement;

    public JavaSourceElementImpl(JavaElement javaElement) {
        Intrinsics.checkNotNullParameter(javaElement, "javaElement");
        this.javaElement = javaElement;
    }

    @Override // org.jetbrains.kotlin.load.java.sources.JavaSourceElement
    public JavaElement getJavaElement() {
        return this.javaElement;
    }

    @Override // org.jetbrains.kotlin.resolve.source.PsiSourceElement
    public PsiElement getPsi() {
        JavaElement javaElement = getJavaElement();
        JavaElementImpl javaElementImpl = javaElement instanceof JavaElementImpl ? (JavaElementImpl) javaElement : null;
        if (javaElementImpl != null) {
            return javaElementImpl.getPsi();
        }
        return null;
    }
}
