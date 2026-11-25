package org.jetbrains.kotlin;

import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: compiled from: KtSourceFile.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/KtPsiSourceFile;", "Lorg/jetbrains/kotlin/KtSourceFile;", "psiFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "(Lcom/intellij/psi/PsiFile;)V", "name", "", "getName", "()Ljava/lang/String;", "path", "getPath", "getPsiFile", "()Lcom/intellij/psi/PsiFile;", "getContentsAsStream", "Ljava/io/InputStream;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtPsiSourceFile implements KtSourceFile {
    private final PsiFile psiFile;

    public KtPsiSourceFile(PsiFile psiFile) {
        Intrinsics.checkNotNullParameter(psiFile, "psiFile");
        this.psiFile = psiFile;
    }

    public final PsiFile getPsiFile() {
        return this.psiFile;
    }

    @Override // org.jetbrains.kotlin.KtSourceFile
    public String getName() {
        String name2 = this.psiFile.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    @Override // org.jetbrains.kotlin.KtSourceFile
    public String getPath() {
        VirtualFile virtualFile = this.psiFile.getVirtualFile();
        if (virtualFile != null) {
            return virtualFile.getPath();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.KtSourceFile
    public InputStream getContentsAsStream() throws IOException {
        InputStream inputStream = this.psiFile.getVirtualFile().getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
        return inputStream;
    }
}
