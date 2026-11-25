package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaSourceFactoryOwner;

/* JADX INFO: loaded from: classes5.dex */
public abstract class JavaElementImpl<Psi extends PsiElement> implements JavaElement, JavaSourceFactoryOwner {
    protected final JavaElementPsiSource<Psi> psiElementSource;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaElementImpl";
        } else {
            objArr[0] = "psiElementSource";
        }
        if (i == 1) {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaElementImpl";
        } else if (i != 2) {
            objArr[1] = "getSourceFactory";
        } else {
            objArr[1] = "getPsi";
        }
        if (i == 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaSourceFactoryOwner
    public JavaElementSourceFactory getSourceFactory() {
        JavaElementSourceFactory factory = this.psiElementSource.getFactory();
        if (factory == null) {
            $$$reportNull$$$0(0);
        }
        return factory;
    }

    protected JavaElementImpl(JavaElementPsiSource<Psi> javaElementPsiSource) {
        if (javaElementPsiSource == null) {
            $$$reportNull$$$0(1);
        }
        this.psiElementSource = javaElementPsiSource;
    }

    public Psi getPsi() {
        Psi psi = (Psi) this.psiElementSource.getPsi();
        if (psi == null) {
            $$$reportNull$$$0(2);
        }
        return psi;
    }

    public int hashCode() {
        return this.psiElementSource.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.psiElementSource, ((JavaElementImpl) obj).psiElementSource);
    }

    public String toString() {
        return getClass().getSimpleName() + ": " + this.psiElementSource;
    }
}
