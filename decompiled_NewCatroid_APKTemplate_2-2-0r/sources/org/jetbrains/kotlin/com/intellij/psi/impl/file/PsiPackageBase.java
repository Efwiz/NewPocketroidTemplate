package org.jetbrains.kotlin.com.intellij.psi.impl.file;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Collection;
import java.util.Map;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectoryContainer;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase;
import org.jetbrains.kotlin.com.intellij.psi.search.EverythingGlobalScope;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiPackageBase extends PsiElementBase implements Queryable, PsiDirectoryContainer {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiPackageBase.class);
    private final PsiManager myManager;
    private final String myQualifiedName;

    /* JADX WARN: Code duplicated, block: B:14:0x001c  */
    /* JADX WARN: Code duplicated, block: B:8:0x000f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 4 && i != 5) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 2 && i != 4 && i != 5) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    i2 = 3;
                    break;
                default:
                    i2 = 2;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 2) {
            objArr[0] = "scope";
        } else if (i != 4 && i != 5) {
            switch (i) {
                case 8:
                    objArr[0] = "text";
                    break;
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    break;
                case 14:
                    objArr[0] = "newElement";
                    break;
                case 15:
                    objArr[0] = CompilerOptions.INFO;
                    break;
                default:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/PsiPackageBase";
                    break;
            }
        } else {
            objArr[0] = "name";
        }
        switch (i) {
            case 1:
            case 3:
                objArr[1] = "getDirectories";
                break;
            case 2:
            case 4:
            case 5:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/PsiPackageBase";
                break;
            case 6:
                objArr[1] = "getChildren";
                break;
            case 7:
                objArr[1] = "textToCharArray";
                break;
            default:
                objArr[1] = "getQualifiedName";
                break;
        }
        if (i == 2) {
            objArr[2] = "getDirectories";
        } else if (i == 4) {
            objArr[2] = "setName";
        } else if (i != 5) {
            switch (i) {
                case 8:
                case 9:
                    objArr[2] = "textMatches";
                    break;
                case 10:
                    objArr[2] = ProductAction.ACTION_ADD;
                    break;
                case 11:
                    objArr[2] = "addBefore";
                    break;
                case 12:
                    objArr[2] = "addAfter";
                    break;
                case 13:
                    objArr[2] = "checkAdd";
                    break;
                case 14:
                    objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                    break;
                case 15:
                    objArr[2] = "putInfo";
                    break;
            }
        } else {
            objArr[2] = "checkSetName";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 5) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    break;
                default:
                    throw new IllegalStateException(str2);
            }
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public boolean canNavigateToSource() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement findElementAt(int i) {
        return null;
    }

    protected abstract PsiPackageBase findPackage(String str);

    protected abstract Collection<PsiDirectory> getAllDirectories(GlobalSearchScope globalSearchScope);

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public ASTNode getNode() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getStartOffsetInParent() {
        return -1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextLength() {
        return -1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return -1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(CharSequence charSequence) {
        if (charSequence != null) {
            return false;
        }
        $$$reportNull$$$0(8);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(PsiElement psiElement) {
        if (psiElement != null) {
            return false;
        }
        $$$reportNull$$$0(9);
        return false;
    }

    public PsiPackageBase(PsiManager psiManager, String str) {
        this.myManager = psiManager;
        this.myQualifiedName = str;
    }

    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            PsiPackageBase psiPackageBase = (PsiPackageBase) obj;
            if (this.myManager == psiPackageBase.myManager && this.myQualifiedName.equals(psiPackageBase.myQualifiedName)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.myQualifiedName.hashCode();
    }

    public String getQualifiedName() {
        String str = this.myQualifiedName;
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectoryContainer
    public PsiDirectory[] getDirectories() {
        PsiDirectory[] directories = getDirectories(new EverythingGlobalScope());
        if (directories == null) {
            $$$reportNull$$$0(1);
        }
        return directories;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectoryContainer
    public PsiDirectory[] getDirectories(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(2);
        }
        Collection<PsiDirectory> allDirectories = getAllDirectories(globalSearchScope);
        PsiDirectory[] psiDirectoryArr = allDirectories.isEmpty() ? PsiDirectory.EMPTY_ARRAY : (PsiDirectory[]) allDirectories.toArray(PsiDirectory.EMPTY_ARRAY);
        if (psiDirectoryArr == null) {
            $$$reportNull$$$0(3);
        }
        return psiDirectoryArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public RowIcon getElementIcon(int i) {
        return IconManager.getInstance().createLayeredIcon(this, PlatformIcons.PACKAGE_ICON, i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        if (DebugUtil.CHECK_INSIDE_ATOMIC_ACTION_ENABLED) {
            ApplicationManager.getApplication().assertReadAccessAllowed();
        }
        if (this.myQualifiedName.isEmpty()) {
            return null;
        }
        int iLastIndexOf = this.myQualifiedName.lastIndexOf(46);
        if (iLastIndexOf <= 0) {
            return this.myQualifiedName;
        }
        return this.myQualifiedName.substring(iLastIndexOf + 1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        checkSetName(str);
        for (PsiDirectory psiDirectory : getDirectories()) {
            psiDirectory.setName(str);
        }
        return findPackage(PsiUtilCore.getQualifiedNameAfterRename(getQualifiedName(), str));
    }

    public void checkSetName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        for (PsiDirectory psiDirectory : getDirectories()) {
            psiDirectory.checkSetName(str);
        }
    }

    public PsiPackageBase getParentPackage() {
        if (this.myQualifiedName.isEmpty()) {
            return null;
        }
        return findPackage(StringUtil.getPackageName(this.myQualifiedName));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiManager getManager() {
        return this.myManager;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        LOG.error("method not implemented in " + getClass());
        PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(6);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return getParentPackage();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public char[] textToCharArray() {
        char[] cArr = ArrayUtilRt.EMPTY_CHAR_ARRAY;
        if (cArr == null) {
            $$$reportNull$$$0(7);
        }
        return cArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        LOG.error("method not implemented in " + getClass());
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(11);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(13);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        checkDelete();
        for (PsiDirectory psiDirectory : getDirectories()) {
            psiDirectory.delete();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkDelete() throws IncorrectOperationException {
        for (PsiDirectory psiDirectory : getDirectories()) {
            psiDirectory.checkDelete();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(14);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        for (PsiDirectory psiDirectory : getDirectories()) {
            if (!psiDirectory.isWritable()) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiPackageBase:" + getQualifiedName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public boolean canNavigate() {
        return isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable
    public void putInfo(Map<? super String, ? super String> map) {
        if (map == null) {
            $$$reportNull$$$0(15);
        }
        map.put("packageName", getName());
        map.put("packageQualifiedName", getQualifiedName());
    }
}
