package org.jetbrains.kotlin.load.java.structure.impl.source;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JavaElementPsiSource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0004\u001a\u00028\u0000X\u0096\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSourceWithFixedPsi;", "PSI", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "psi", "(Lcom/intellij/psi/PsiElement;)V", "factory", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "getFactory", "()Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "getPsi", "()Lcom/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaElementPsiSourceWithFixedPsi<PSI extends PsiElement> extends JavaElementPsiSource<PSI> {
    private final PSI psi;

    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource
    public PSI getPsi() {
        return this.psi;
    }

    public JavaElementPsiSourceWithFixedPsi(PSI psi) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        this.psi = psi;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource
    public JavaElementSourceFactory getFactory() {
        JavaElementSourceFactory.Companion companion = JavaElementSourceFactory.INSTANCE;
        Project project = getPsi().getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return (other instanceof JavaElementPsiSourceWithFixedPsi) && Intrinsics.areEqual(getPsi(), ((JavaElementPsiSourceWithFixedPsi) other).getPsi());
    }

    public int hashCode() {
        return getPsi().hashCode();
    }

    public String toString() {
        return getPsi().toString();
    }
}
