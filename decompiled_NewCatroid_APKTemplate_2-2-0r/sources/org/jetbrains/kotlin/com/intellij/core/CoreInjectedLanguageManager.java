package org.jetbrains.kotlin.com.intellij.core;

import java.util.Collections;
import java.util.List;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.injected.editor.DocumentWindow;
import org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost;

/* JADX INFO: loaded from: classes3.dex */
public class CoreInjectedLanguageManager extends InjectedLanguageManager {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 10 || i == 13 || i == 21 || i == 28 || i == 31) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 10 || i == 13 || i == 21 || i == 28 || i == 31) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "injectedElement";
                break;
            case 2:
            case 5:
            case 6:
                objArr[0] = "injectedContext";
                break;
            case 3:
                objArr[0] = "injectedTextRange";
                break;
            case 4:
            case 10:
            case 13:
            case 21:
            case 28:
            case 31:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreInjectedLanguageManager";
                break;
            case 7:
                objArr[0] = "injector";
                break;
            case 8:
                objArr[0] = "parentDisposable";
                break;
            case 9:
                objArr[0] = "injectedNode";
                break;
            case 11:
                objArr[0] = "injectedPsi";
                break;
            case 12:
                objArr[0] = "rangeToEdit";
                break;
            case 14:
                objArr[0] = "injectedFile";
                break;
            case 15:
                objArr[0] = "hostFile";
                break;
            case 16:
            case 22:
            case 24:
                objArr[0] = "host";
                break;
            case 17:
                objArr[0] = "file";
                break;
            case 18:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 19:
                objArr[0] = "hostPsiFile";
                break;
            case 20:
                objArr[0] = "range";
                break;
            case 23:
            case 26:
                objArr[0] = "visitor";
                break;
            case 25:
                objArr[0] = "containingFile";
                break;
            case 27:
                objArr[0] = ConfigConstants.CONFIG_KEY_WINDOW;
                break;
            case 29:
                objArr[0] = "hostDocument";
                break;
            case 30:
                objArr[0] = "document";
                break;
            default:
                objArr[0] = "injectedProvider";
                break;
        }
        if (i == 4) {
            objArr[1] = "injectedToHost";
        } else if (i == 10) {
            objArr[1] = "getUnescapedText";
        } else if (i == 13) {
            objArr[1] = "intersectWithAllEditableFragments";
        } else if (i == 21) {
            objArr[1] = "getCachedInjectedDocumentsInRange";
        } else if (i == 28) {
            objArr[1] = "getNonEditableFragments";
        } else if (i != 31) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreInjectedLanguageManager";
        } else {
            objArr[1] = "freezeWindow";
        }
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
                objArr[2] = "injectedToHost";
                break;
            case 4:
            case 10:
            case 13:
            case 21:
            case 28:
            case 31:
                break;
            case 7:
            case 8:
                objArr[2] = "registerMultiHostInjector";
                break;
            case 9:
                objArr[2] = "getUnescapedText";
                break;
            case 11:
            case 12:
                objArr[2] = "intersectWithAllEditableFragments";
                break;
            case 14:
                objArr[2] = "isInjectedFragment";
                break;
            case 15:
                objArr[2] = "findInjectedElementAt";
                break;
            case 16:
                objArr[2] = "getInjectedPsiFiles";
                break;
            case 17:
                objArr[2] = "dropFileCaches";
                break;
            case 18:
                objArr[2] = "getTopLevelFile";
                break;
            case 19:
            case 20:
                objArr[2] = "getCachedInjectedDocumentsInRange";
                break;
            case 22:
            case 23:
                objArr[2] = "enumerate";
                break;
            case 24:
            case 25:
            case 26:
                objArr[2] = "enumerateEx";
                break;
            case 27:
                objArr[2] = "getNonEditableFragments";
                break;
            case 29:
                objArr[2] = "mightHaveInjectedFragmentAtOffset";
                break;
            case 30:
                objArr[2] = "freezeWindow";
                break;
            default:
                objArr[2] = "getInjectionHost";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 10 && i != 13 && i != 21 && i != 28 && i != 31) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager
    public List<Pair<PsiElement, TextRange>> getInjectedPsiFiles(PsiElement psiElement) {
        if (psiElement != null) {
            return null;
        }
        $$$reportNull$$$0(16);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager
    public PsiLanguageInjectionHost getInjectionHost(PsiElement psiElement) {
        if (psiElement != null) {
            return null;
        }
        $$$reportNull$$$0(1);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager
    public boolean isInjectedFragment(PsiFile psiFile) {
        if (psiFile != null) {
            return false;
        }
        $$$reportNull$$$0(14);
        return false;
    }

    CoreInjectedLanguageManager() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager
    public TextRange injectedToHost(PsiElement psiElement, TextRange textRange) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (textRange == null) {
            $$$reportNull$$$0(3);
        }
        if (textRange == null) {
            $$$reportNull$$$0(4);
        }
        return textRange;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager
    public PsiFile getTopLevelFile(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(18);
        }
        return psiElement.getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager
    public List<DocumentWindow> getCachedInjectedDocumentsInRange(PsiFile psiFile, TextRange textRange) {
        if (psiFile == null) {
            $$$reportNull$$$0(19);
        }
        if (textRange == null) {
            $$$reportNull$$$0(20);
        }
        List<DocumentWindow> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(21);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager
    public List<TextRange> getNonEditableFragments(DocumentWindow documentWindow) {
        if (documentWindow == null) {
            $$$reportNull$$$0(27);
        }
        List<TextRange> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(28);
        }
        return listEmptyList;
    }
}
