package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiFileFactory {
    public static final Key<PsiFile> ORIGINAL_FILE = Key.create("ORIGINAL_FILE");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "language";
        } else {
            objArr[0] = "text";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiFileFactory";
        objArr[2] = "createFileFromText";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract PsiFile createFileFromText(CharSequence charSequence, PsiFile psiFile) throws IncorrectOperationException;

    @Deprecated
    public abstract PsiFile createFileFromText(String str, String str2) throws IncorrectOperationException;

    public abstract PsiFile createFileFromText(String str, Language language, CharSequence charSequence) throws IncorrectOperationException;

    public abstract PsiFile createFileFromText(String str, Language language, CharSequence charSequence, boolean z, boolean z2) throws IncorrectOperationException;

    public abstract PsiFile createFileFromText(String str, Language language, CharSequence charSequence, boolean z, boolean z2, boolean z3) throws IncorrectOperationException;

    public abstract PsiFile createFileFromText(String str, Language language, CharSequence charSequence, boolean z, boolean z2, boolean z3, VirtualFile virtualFile) throws IncorrectOperationException;

    public abstract PsiFile createFileFromText(String str, FileType fileType, CharSequence charSequence) throws IncorrectOperationException;

    public abstract PsiFile createFileFromText(String str, FileType fileType, CharSequence charSequence, long j, boolean z) throws IncorrectOperationException;

    public abstract PsiFile createFileFromText(String str, FileType fileType, CharSequence charSequence, long j, boolean z, boolean z2) throws IncorrectOperationException;

    public abstract PsiFile createFileFromText(FileType fileType, String str, CharSequence charSequence, int i, int i2) throws IncorrectOperationException;

    public static PsiFileFactory getInstance(Project project) {
        return (PsiFileFactory) project.getService(PsiFileFactory.class);
    }

    public PsiFile createFileFromText(Language language, CharSequence charSequence) throws IncorrectOperationException {
        if (language == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        return createFileFromText("foo.bar", language, charSequence);
    }
}
