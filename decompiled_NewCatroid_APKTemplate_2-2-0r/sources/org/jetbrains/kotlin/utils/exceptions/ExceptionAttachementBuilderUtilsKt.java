package org.jetbrains.kotlin.utils.exceptions;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.utils.PsiUtilsKt;

/* JADX INFO: compiled from: exceptionAttachementBuilderUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u001c\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t¨\u0006\n"}, d2 = {"withPsiEntry", "", "Lorg/jetbrains/kotlin/utils/exceptions/ExceptionAttachmentBuilder;", "name", "", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "withVirtualFileEntry", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExceptionAttachementBuilderUtilsKt {
    public static final void withPsiEntry(ExceptionAttachmentBuilder exceptionAttachmentBuilder, String name2, PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        exceptionAttachmentBuilder.withEntry(name2, psiElement, new Function1<PsiElement, String>() { // from class: org.jetbrains.kotlin.utils.exceptions.ExceptionAttachementBuilderUtilsKt.withPsiEntry.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(PsiElement psiElement2) {
                Intrinsics.checkNotNullParameter(psiElement2, "psiElement");
                return PsiUtilsKt.getElementTextWithContext(psiElement2);
            }
        });
    }

    public static final void withVirtualFileEntry(ExceptionAttachmentBuilder exceptionAttachmentBuilder, String name2, VirtualFile virtualFile) {
        Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        exceptionAttachmentBuilder.withEntry(name2, virtualFile, new Function1<VirtualFile, String>() { // from class: org.jetbrains.kotlin.utils.exceptions.ExceptionAttachementBuilderUtilsKt.withVirtualFileEntry.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(VirtualFile file) {
                Intrinsics.checkNotNullParameter(file, "file");
                return "path: " + file.getPath() + ", filetype: " + file.getFileType() + " ,filesystem," + file.getFileSystem();
            }
        });
    }
}
