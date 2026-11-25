package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.model.psi.PsiSymbolReference;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Iconable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiElement extends Iconable, UserDataHolder {
    public static final PsiElement[] EMPTY_ARRAY = new PsiElement[0];
    public static final ArrayFactory<PsiElement> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiElement$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiElement.lambda$static$0(i);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiElement";
        if (i == 1) {
            objArr[1] = "getOwnDeclarations";
        } else if (i != 2) {
            objArr[1] = "getTextRangeInParent";
        } else {
            objArr[1] = "getOwnReferences";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    void accept(PsiElementVisitor psiElementVisitor);

    void acceptChildren(PsiElementVisitor psiElementVisitor);

    PsiElement add(PsiElement psiElement) throws IncorrectOperationException;

    PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException;

    PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException;

    PsiElement addRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException;

    PsiElement addRangeAfter(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException;

    PsiElement addRangeBefore(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException;

    @Deprecated
    void checkAdd(PsiElement psiElement) throws IncorrectOperationException;

    @Deprecated
    void checkDelete() throws IncorrectOperationException;

    PsiElement copy();

    void delete() throws IncorrectOperationException;

    void deleteChildRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException;

    PsiElement findElementAt(int i);

    PsiReference findReferenceAt(int i);

    PsiElement[] getChildren();

    PsiFile getContainingFile() throws PsiInvalidElementAccessException;

    PsiElement getContext();

    <T> T getCopyableUserData(Key<T> key);

    PsiElement getFirstChild();

    Language getLanguage();

    PsiElement getLastChild();

    PsiManager getManager();

    PsiElement getNavigationElement();

    PsiElement getNextSibling();

    ASTNode getNode();

    PsiElement getOriginalElement();

    PsiElement getParent();

    PsiElement getPrevSibling();

    Project getProject() throws PsiInvalidElementAccessException;

    PsiReference getReference();

    PsiReference[] getReferences();

    GlobalSearchScope getResolveScope();

    int getStartOffsetInParent();

    String getText();

    int getTextLength();

    int getTextOffset();

    TextRange getTextRange();

    SearchScope getUseScope();

    boolean isEquivalentTo(PsiElement psiElement);

    boolean isPhysical();

    boolean isValid();

    boolean isWritable();

    boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2);

    <T> void putCopyableUserData(Key<T> key, T t);

    PsiElement replace(PsiElement psiElement) throws IncorrectOperationException;

    boolean textContains(char c);

    boolean textMatches(CharSequence charSequence);

    boolean textMatches(PsiElement psiElement);

    char[] textToCharArray();

    String toString();

    static /* synthetic */ PsiElement[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiElement[i];
    }

    default TextRange getTextRangeInParent() {
        TextRange textRangeFrom = TextRange.from(getStartOffsetInParent(), getTextLength());
        if (textRangeFrom == null) {
            $$$reportNull$$$0(0);
        }
        return textRangeFrom;
    }

    default Collection<? extends Object> getOwnDeclarations() {
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(1);
        }
        return listEmptyList;
    }

    default Collection<? extends PsiSymbolReference> getOwnReferences() {
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(2);
        }
        return listEmptyList;
    }
}
