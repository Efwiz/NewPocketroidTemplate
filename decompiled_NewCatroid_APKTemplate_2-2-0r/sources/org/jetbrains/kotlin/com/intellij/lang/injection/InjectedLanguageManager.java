package org.jetbrains.kotlin.com.intellij.lang.injection;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.injected.editor.DocumentWindow;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost;

/* JADX INFO: loaded from: classes3.dex */
public abstract class InjectedLanguageManager {
    public static final Key<Boolean> FRANKENSTEIN_INJECTION = Key.create("FRANKENSTEIN_INJECTION");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "project", "org/jetbrains/kotlin/com/intellij/lang/injection/InjectedLanguageManager", "getInstance"));
    }

    public abstract List<DocumentWindow> getCachedInjectedDocumentsInRange(PsiFile psiFile, TextRange textRange);

    public abstract List<Pair<PsiElement, TextRange>> getInjectedPsiFiles(PsiElement psiElement);

    public abstract PsiLanguageInjectionHost getInjectionHost(PsiElement psiElement);

    public abstract List<TextRange> getNonEditableFragments(DocumentWindow documentWindow);

    public abstract PsiFile getTopLevelFile(PsiElement psiElement);

    public abstract TextRange injectedToHost(PsiElement psiElement, TextRange textRange);

    public abstract boolean isInjectedFragment(PsiFile psiFile);

    public static InjectedLanguageManager getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return (InjectedLanguageManager) project.getService(InjectedLanguageManager.class);
    }
}
