package org.jetbrains.kotlin.resolve.source;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SourceFile;

/* JADX INFO: compiled from: PsiSourceElement.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H\u0016R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0002À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/source/PsiSourceElement;", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getPsi", "()Lcom/intellij/psi/PsiElement;", "getContainingFile", "Lorg/jetbrains/kotlin/descriptors/SourceFile;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PsiSourceElement extends SourceElement {
    PsiElement getPsi();

    @Override // org.jetbrains.kotlin.descriptors.SourceElement
    default SourceFile getContainingFile() {
        PsiFile containingFile;
        PsiElement psi = getPsi();
        if (psi != null && (containingFile = psi.getContainingFile()) != null) {
            return new PsiSourceFile(containingFile);
        }
        SourceFile NO_SOURCE_FILE = SourceFile.NO_SOURCE_FILE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE_FILE, "NO_SOURCE_FILE");
        return NO_SOURCE_FILE;
    }
}
