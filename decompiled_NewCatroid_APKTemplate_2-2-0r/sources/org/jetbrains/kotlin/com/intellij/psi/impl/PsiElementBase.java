package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.ide.util.PsiNavigationSupport;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.pom.Navigatable;
import org.jetbrains.kotlin.com.intellij.psi.NavigatablePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiElementBase extends ElementBase implements NavigatablePsiElement, Cloneable {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiElementBase.class);

    /* JADX WARN: Code duplicated, block: B:7:0x000c A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1) {
            switch (i) {
                default:
                    switch (i) {
                        case 19:
                        case 20:
                        case 21:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 12:
                case 13:
                case 14:
                case 15:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1) {
            switch (i) {
                case 12:
                case 13:
                case 14:
                case 15:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 19:
                        case 20:
                        case 21:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 12:
            case 13:
            case 14:
            case 15:
            case 19:
            case 20:
            case 21:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiElementBase";
                break;
            case 2:
                objArr[0] = "first";
                break;
            case 3:
                objArr[0] = "last";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 17:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 8:
                objArr[0] = "newElement";
                break;
            case 9:
                objArr[0] = "processor";
                break;
            case 10:
                objArr[0] = "state";
                break;
            case 11:
                objArr[0] = "place";
                break;
            case 16:
                objArr[0] = "text";
                break;
            case 18:
            default:
                objArr[0] = "visitor";
                break;
        }
        if (i != 1) {
            switch (i) {
                case 12:
                    objArr[1] = "getNavigationElement";
                    break;
                case 13:
                    objArr[1] = "getResolveScope";
                    break;
                case 14:
                    objArr[1] = "getUseScope";
                    break;
                case 15:
                    objArr[1] = "getProject";
                    break;
                default:
                    switch (i) {
                        case 19:
                            objArr[1] = "notNullChild";
                            break;
                        case 20:
                            objArr[1] = "findChildrenByClass";
                            break;
                        case 21:
                            objArr[1] = "findNotNullChildByClass";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiElementBase";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getReferences";
        }
        switch (i) {
            case 1:
            case 12:
            case 13:
            case 14:
            case 15:
            case 19:
            case 20:
            case 21:
                break;
            case 2:
            case 3:
                objArr[2] = "addRangeBefore";
                break;
            case 4:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 5:
                objArr[2] = "addBefore";
                break;
            case 6:
                objArr[2] = "addAfter";
                break;
            case 7:
                objArr[2] = "checkAdd";
                break;
            case 8:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "processDeclarations";
                break;
            case 16:
            case 17:
                objArr[2] = "textMatches";
                break;
            case 18:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "acceptChildren";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1) {
            switch (i) {
                case 12:
                case 13:
                case 14:
                case 15:
                    break;
                default:
                    switch (i) {
                        case 19:
                        case 20:
                        case 21:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    public String getName() {
        return null;
    }

    public PsiElement getNavigationElement() {
        return this;
    }

    public PsiElement getOriginalElement() {
        return this;
    }

    public ItemPresentation getPresentation() {
        return null;
    }

    public PsiReference getReference() {
        return null;
    }

    public boolean isEquivalentTo(PsiElement psiElement) {
        return this == psiElement;
    }

    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(9);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(10);
        }
        if (psiElement2 != null) {
            return true;
        }
        $$$reportNull$$$0(11);
        return true;
    }

    public PsiElement getFirstChild() {
        PsiElement[] children = getChildren();
        if (children.length == 0) {
            return null;
        }
        return children[0];
    }

    public PsiElement getLastChild() {
        PsiElement[] children = getChildren();
        if (children.length == 0) {
            return null;
        }
        return children[children.length - 1];
    }

    public PsiElement getNextSibling() {
        return SharedPsiElementImplUtil.getNextSibling(this);
    }

    public PsiElement getPrevSibling() {
        return SharedPsiElementImplUtil.getPrevSibling(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void acceptChildren(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            firstChild.accept(psiElementVisitor);
        }
    }

    public PsiReference[] getReferences() {
        PsiReference[] references = SharedPsiElementImplUtil.getReferences(this);
        if (references == null) {
            $$$reportNull$$$0(1);
        }
        return references;
    }

    public PsiReference findReferenceAt(int i) {
        return SharedPsiElementImplUtil.findReferenceAt(this, i);
    }

    public PsiElement addRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        throw new IncorrectOperationException("Operation not supported in: " + getClass());
    }

    public PsiElement addRangeBefore(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(3);
        }
        throw new IncorrectOperationException("Operation not supported in: " + getClass());
    }

    public PsiElement addRangeAfter(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException {
        throw new IncorrectOperationException("Operation not supported in: " + getClass());
    }

    public void deleteChildRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        throw new IncorrectOperationException("Operation not supported in: " + getClass());
    }

    public PsiElement copy() {
        return (PsiElement) clone();
    }

    public PsiElement add(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        throw new UnsupportedOperationException(getClass().getName());
    }

    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        throw new UnsupportedOperationException(getClass().getName());
    }

    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void delete() throws IncorrectOperationException {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void checkDelete() throws IncorrectOperationException {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        throw new UnsupportedOperationException(getClass().getName());
    }

    public boolean textContains(char c) {
        return getText().indexOf(c) >= 0;
    }

    public PsiElement getContext() {
        return getLightParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope elementResolveScope = ResolveScopeManager.getElementResolveScope(this);
        if (elementResolveScope == null) {
            $$$reportNull$$$0(13);
        }
        return elementResolveScope;
    }

    public SearchScope getUseScope() {
        GlobalSearchScope elementUseScope = ResolveScopeManager.getElementUseScope(this);
        if (elementUseScope == null) {
            $$$reportNull$$$0(14);
        }
        return elementUseScope;
    }

    public void navigate(boolean z) {
        Navigatable descriptor = PsiNavigationSupport.getInstance().getDescriptor(this);
        if (descriptor != null) {
            descriptor.navigate(z);
        }
    }

    public boolean canNavigate() {
        return PsiNavigationSupport.getInstance().canNavigate(this);
    }

    public boolean canNavigateToSource() {
        return canNavigate();
    }

    public Project getProject() {
        PsiManager manager = getManager();
        if (manager == null) {
            throw new PsiInvalidElementAccessException(this);
        }
        Project project = manager.getProject();
        if (project == null) {
            $$$reportNull$$$0(15);
        }
        return project;
    }

    public PsiFile getContainingFile() {
        PsiElement parent = getLightParent();
        if (parent == null) {
            throw new PsiInvalidElementAccessException(this);
        }
        return parent.getContainingFile();
    }

    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        PsiElement parent = getLightParent();
        return parent != null && parent.getIsPhysical();
    }

    public boolean isWritable() {
        PsiElement parent = getLightParent();
        return parent != null && parent.isWritable();
    }

    public boolean isValid() {
        PsiElement parent = getLightParent();
        while (parent != null && parent.getClass() == getClass()) {
            parent = parent.getLightParent();
        }
        return parent != null && parent.isValid();
    }

    public boolean textMatches(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(16);
        }
        return Comparing.equal((CharSequence) getText(), charSequence, true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(17);
        }
        return getText().equals(psiElement.getText());
    }

    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(18);
        }
        psiElementVisitor.visitElement(this);
    }

    protected <T> T notNullChild(T t) {
        if (t == null) {
            LOG.error(getText() + "\n parent=" + getLightParent().getText());
        }
        if (t == null) {
            $$$reportNull$$$0(19);
        }
        return t;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T> T[] findChildrenByClass(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (cls.isInstance(firstChild)) {
                arrayList.add(firstChild);
            }
        }
        T[] tArr = (T[]) arrayList.toArray(ArrayUtil.newArray(cls, arrayList.size()));
        if (tArr == null) {
            $$$reportNull$$$0(20);
        }
        return tArr;
    }

    protected <T> T findChildByClass(Class<T> cls) {
        for (T t = (T) getFirstChild(); t != null; t = (T) ((PsiElement) t).getNextSibling()) {
            if (cls.isInstance(t)) {
                return t;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T> T findNotNullChildByClass(Class<T> cls) {
        T t = (T) notNullChild(findChildByClass(cls));
        if (t == null) {
            $$$reportNull$$$0(21);
        }
        return t;
    }

    public PsiManager getManager() {
        return PsiManager.getInstance(getProject());
    }
}
