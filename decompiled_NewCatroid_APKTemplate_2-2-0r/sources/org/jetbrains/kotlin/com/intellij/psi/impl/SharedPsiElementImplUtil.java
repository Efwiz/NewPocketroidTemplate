package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.HintedReferenceHost;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService;
import org.jetbrains.kotlin.com.intellij.psi.ReferenceRange;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.PsiMultiReference;
import org.jetbrains.kotlin.com.intellij.psi.templateLanguages.OuterLanguageElement;

/* JADX INFO: loaded from: classes3.dex */
public final class SharedPsiElementImplUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) SharedPsiElementImplUtil.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/SharedPsiElementImplUtil", "getReferences"));
    }

    private SharedPsiElementImplUtil() {
    }

    public static PsiReference findReferenceAt(PsiElement psiElement, int i, Language language) {
        PsiElement psiElementFindElementAt;
        if (psiElement == null) {
            return null;
        }
        if (language != null) {
            psiElementFindElementAt = psiElement.getContainingFile().getViewProvider().findElementAt(i, language);
        } else {
            psiElementFindElementAt = psiElement.findElementAt(i);
        }
        if (psiElementFindElementAt == null || (psiElementFindElementAt instanceof OuterLanguageElement)) {
            return null;
        }
        int startOffset = (psiElement.getTextRange().getStartOffset() + i) - psiElementFindElementAt.getTextRange().getStartOffset();
        ArrayList arrayList = new ArrayList();
        while (psiElementFindElementAt != null) {
            addReferences(startOffset, psiElementFindElementAt, arrayList);
            if ((psiElementFindElementAt instanceof PsiFile) || ((psiElementFindElementAt instanceof HintedReferenceHost) && !((HintedReferenceHost) psiElementFindElementAt).shouldAskParentForReferences(new PsiReferenceService.Hints(null, Integer.valueOf(startOffset))))) {
                break;
            }
            startOffset += psiElementFindElementAt.getStartOffsetInParent();
            psiElementFindElementAt = psiElementFindElementAt.getLightParent();
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList.size() == 1 ? (PsiReference) arrayList.get(0) : new PsiMultiReference((PsiReference[]) arrayList.toArray(PsiReference.EMPTY_ARRAY), ((PsiReference) arrayList.get(arrayList.size() - 1)).getPsiElement());
    }

    public static PsiReference findReferenceAt(PsiElement psiElement, int i) {
        return findReferenceAt(psiElement, i, null);
    }

    private static void addReferences(int i, PsiElement psiElement, Collection<? super PsiReference> collection) {
        PsiReference[] references;
        if (psiElement instanceof HintedReferenceHost) {
            references = ((HintedReferenceHost) psiElement).getReferences(new PsiReferenceService.Hints(null, Integer.valueOf(i)));
        } else {
            references = psiElement.getReferences();
        }
        for (PsiReference psiReference : references) {
            if (psiReference == null) {
                LOG.error("Null reference returned from " + psiElement + " of " + psiElement.getClass());
            } else {
                Iterator<TextRange> it = ReferenceRange.getRanges(psiReference).iterator();
                while (it.hasNext()) {
                    TextRange next = it.next();
                    LOG.assertTrue(next != null, psiReference);
                    if (next.containsOffset(i)) {
                        collection.add(psiReference);
                    }
                }
            }
        }
    }

    public static PsiReference[] getReferences(PsiElement psiElement) {
        PsiReference reference = psiElement.getReference();
        PsiReference[] psiReferenceArr = reference != null ? new PsiReference[]{reference} : PsiReference.EMPTY_ARRAY;
        if (psiReferenceArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiReferenceArr;
    }

    public static PsiElement getNextSibling(PsiElement psiElement) {
        PsiElement parent;
        PsiElement[] children;
        int childIndex;
        if (psiElement instanceof PsiFile) {
            FileViewProvider viewProvider = ((PsiFile) psiElement).getViewProvider();
            psiElement = viewProvider.getPsi(viewProvider.getBaseLanguage());
        }
        if (psiElement != null && (parent = psiElement.getLightParent()) != null && (childIndex = getChildIndex((children = parent.getChildren()), psiElement)) >= 0 && childIndex < children.length - 1) {
            return children[childIndex + 1];
        }
        return null;
    }

    public static PsiElement getPrevSibling(PsiElement psiElement) {
        PsiElement parent;
        PsiElement[] children;
        int childIndex;
        if (psiElement instanceof PsiFile) {
            FileViewProvider viewProvider = ((PsiFile) psiElement).getViewProvider();
            psiElement = viewProvider.getPsi(viewProvider.getBaseLanguage());
        }
        if (psiElement == null || (parent = psiElement.getLightParent()) == null || (childIndex = getChildIndex((children = parent.getChildren()), psiElement)) <= 0) {
            return null;
        }
        return children[childIndex - 1];
    }

    private static int getChildIndex(PsiElement[] psiElementArr, PsiElement psiElement) {
        for (int i = 0; i < psiElementArr.length; i++) {
            if (psiElementArr[i] == psiElement) {
                return i;
            }
        }
        LOG.error("Cannot find element among its parent' children. element: '" + psiElement + "'; parent: '" + psiElement.getLightParent() + "'; children: " + Arrays.asList(psiElementArr) + ";  file:" + psiElement.getContainingFile());
        return -1;
    }
}
