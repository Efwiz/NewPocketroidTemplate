package org.jetbrains.kotlin.com.intellij.psi;

import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderEx;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public interface FileViewProvider extends UserDataHolderEx, Cloneable {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "language", "org/jetbrains/kotlin/com/intellij/psi/FileViewProvider", "hasLanguage"));
    }

    void beforeContentsSynchronized();

    FileViewProvider clone();

    void contentsSynchronized();

    FileViewProvider createCopy(VirtualFile virtualFile);

    PsiElement findElementAt(int i);

    PsiElement findElementAt(int i, Class<? extends Language> cls);

    PsiElement findElementAt(int i, Language language);

    PsiReference findReferenceAt(int i);

    PsiReference findReferenceAt(int i, Language language);

    List<PsiFile> getAllFiles();

    Language getBaseLanguage();

    CharSequence getContents();

    Document getDocument();

    FileType getFileType();

    Set<Language> getLanguages();

    PsiManager getManager();

    long getModificationStamp();

    PsiFile getPsi(Language language);

    PsiFile getStubBindingRoot();

    VirtualFile getVirtualFile();

    boolean isEventSystemEnabled();

    boolean isPhysical();

    void rootChanged(PsiFile psiFile);

    boolean supportsIncrementalReparse(Language language);

    default boolean hasLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(0);
        }
        return getLanguages().contains(language);
    }
}
