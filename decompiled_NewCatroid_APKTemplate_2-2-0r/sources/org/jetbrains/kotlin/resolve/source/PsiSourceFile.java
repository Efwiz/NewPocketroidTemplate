package org.jetbrains.kotlin.resolve.source;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: PsiSourceElement.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\n\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/source/PsiSourceFile;", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "psiFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "(Lcom/intellij/psi/PsiFile;)V", "getPsiFile", "()Lcom/intellij/psi/PsiFile;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getName", "", "hashCode", "", "toString", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiSourceFile implements SourceFile {
    private final PsiFile psiFile;

    public PsiSourceFile(PsiFile psiFile) {
        Intrinsics.checkNotNullParameter(psiFile, "psiFile");
        this.psiFile = psiFile;
    }

    public final PsiFile getPsiFile() {
        return this.psiFile;
    }

    public boolean equals(Object other) {
        return (other instanceof PsiSourceFile) && Intrinsics.areEqual(this.psiFile, ((PsiSourceFile) other).psiFile);
    }

    public int hashCode() {
        return this.psiFile.hashCode();
    }

    public String toString() {
        String path = this.psiFile.getVirtualFile().getPath();
        Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
        return path;
    }

    @Override // org.jetbrains.kotlin.descriptors.SourceFile
    /* JADX INFO: renamed from: getName */
    public String getName_() {
        VirtualFile virtualFile = this.psiFile.getVirtualFile();
        if (virtualFile != null) {
            return virtualFile.getName();
        }
        return null;
    }
}
