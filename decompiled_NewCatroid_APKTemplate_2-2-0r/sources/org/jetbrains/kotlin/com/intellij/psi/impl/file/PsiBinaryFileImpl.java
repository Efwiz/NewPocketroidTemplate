package org.jetbrains.kotlin.com.intellij.psi.impl.file;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.IOException;
import java.util.Map;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiBinaryFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.FileContextUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiBinaryFileImpl extends PsiElementBase implements Queryable, PsiBinaryFile, PsiFileEx, Cloneable {
    private byte[] myContents;
    private final PsiManagerImpl myManager;
    private String myName;
    private volatile boolean myPossiblyInvalidated;
    private final AbstractFileViewProvider myViewProvider;

    /* JADX WARN: Code duplicated, block: B:10:0x0016  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 4 && i != 6 && i != 7 && i != 8) {
            switch (i) {
                case 17:
                case 18:
                case 19:
                case 20:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 4 && i != 6 && i != 7 && i != 8) {
            switch (i) {
                case 17:
                case 18:
                case 19:
                case 20:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "viewProvider";
                break;
            case 2:
            case 4:
            case 6:
            case 7:
            case 8:
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/PsiBinaryFileImpl";
                break;
            case 3:
                objArr[0] = "processor";
                break;
            case 5:
                objArr[0] = "name";
                break;
            case 9:
                objArr[0] = "text";
                break;
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 11:
                objArr[0] = "visitor";
                break;
            case 16:
                objArr[0] = "newElement";
                break;
            case 21:
                objArr[0] = CompilerOptions.INFO;
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 2) {
            objArr[1] = "getVirtualFile";
        } else if (i == 4) {
            objArr[1] = "getName";
        } else if (i == 6) {
            objArr[1] = "getLanguage";
        } else if (i == 7) {
            objArr[1] = "getChildren";
        } else if (i != 8) {
            switch (i) {
                case 17:
                    objArr[1] = "getOriginalFile";
                    break;
                case 18:
                    objArr[1] = "getFileType";
                    break;
                case 19:
                    objArr[1] = "getPsiRoots";
                    break;
                case 20:
                    objArr[1] = "getViewProvider";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/PsiBinaryFileImpl";
                    break;
            }
        } else {
            objArr[1] = "textToCharArray";
        }
        switch (i) {
            case 2:
            case 4:
            case 6:
            case 7:
            case 8:
            case 17:
            case 18:
            case 19:
            case 20:
                break;
            case 3:
                objArr[2] = "processChildren";
                break;
            case 5:
                objArr[2] = "setName";
                break;
            case 9:
            case 10:
                objArr[2] = "textMatches";
                break;
            case 11:
                objArr[2] = "accept";
                break;
            case 12:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 13:
                objArr[2] = "addBefore";
                break;
            case 14:
                objArr[2] = "addAfter";
                break;
            case 15:
                objArr[2] = "checkAdd";
                break;
            case 16:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 21:
                objArr[2] = "putInfo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 6 && i != 7 && i != 8) {
            switch (i) {
                case 17:
                case 18:
                case 19:
                case 20:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public PsiElement findElementAt(int i) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public FileASTNode getNode() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public PsiFile getOriginalFile() {
        return this;
    }

    public int getStartOffsetInParent() {
        return -1;
    }

    public int getTextLength() {
        return -1;
    }

    public int getTextOffset() {
        return -1;
    }

    public TextRange getTextRange() {
        return null;
    }

    public boolean isContentsLoaded() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
    public boolean isDirectory() {
        return false;
    }

    public void onContentReload() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
    public boolean processChildren(PsiElementProcessor<? super PsiFileSystemItem> psiElementProcessor) {
        if (psiElementProcessor != null) {
            return true;
        }
        $$$reportNull$$$0(3);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement != null) {
            return null;
        }
        $$$reportNull$$$0(16);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public void subtreeChanged() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(CharSequence charSequence) {
        if (charSequence != null) {
            return false;
        }
        $$$reportNull$$$0(9);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(PsiElement psiElement) {
        if (psiElement != null) {
            return false;
        }
        $$$reportNull$$$0(10);
        return false;
    }

    public PsiBinaryFileImpl(PsiManagerImpl psiManagerImpl, FileViewProvider fileViewProvider) {
        if (psiManagerImpl == null) {
            $$$reportNull$$$0(0);
        }
        if (fileViewProvider == null) {
            $$$reportNull$$$0(1);
        }
        this.myViewProvider = (AbstractFileViewProvider) fileViewProvider;
        this.myManager = psiManagerImpl;
    }

    public VirtualFile getVirtualFile() {
        VirtualFile virtualFile = this.myViewProvider.getVirtualFile();
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        return virtualFile;
    }

    byte[] getStoredContents() {
        return this.myContents;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String name2 = !isCopy() ? getVirtualFile().getName() : this.myName;
        if (name2 == null) {
            $$$reportNull$$$0(4);
        }
        return name2;
    }

    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        checkSetName(str);
        if (isCopy()) {
            this.myName = str;
            return this;
        }
        return PsiFileImplUtil.setName(this, str);
    }

    public void checkSetName(String str) throws IncorrectOperationException {
        if (isCopy()) {
            return;
        }
        PsiFileImplUtil.checkSetName(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public PsiDirectory getContainingDirectory() {
        VirtualFile parent = getVirtualFile().getParent();
        if (parent == null) {
            return null;
        }
        return getManager().findDirectory(parent);
    }

    public PsiDirectory getParentDirectory() {
        return getContainingDirectory();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public long getModificationStamp() {
        return getVirtualFile().getModificationStamp();
    }

    public Language getLanguage() {
        Language language = Language.ANY;
        if (language == null) {
            $$$reportNull$$$0(6);
        }
        return language;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiManager getManager() {
        return this.myManager;
    }

    public PsiElement[] getChildren() {
        PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(7);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiDirectory getLightParent() {
        return getContainingDirectory();
    }

    public String getText() {
        return "";
    }

    public char[] textToCharArray() {
        char[] cArr = ArrayUtilRt.EMPTY_CHAR_ARRAY;
        if (cArr == null) {
            $$$reportNull$$$0(8);
        }
        return cArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(11);
        }
        psiElementVisitor.visitBinaryFile(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        PsiBinaryFileImpl psiBinaryFileImpl = (PsiBinaryFileImpl) clone();
        psiBinaryFileImpl.myName = getName();
        try {
            psiBinaryFileImpl.myContents = !isCopy() ? getVirtualFile().contentsToByteArray() : this.myContents;
        } catch (IOException unused) {
        }
        return psiBinaryFileImpl;
    }

    private boolean isCopy() {
        return this.myName != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(13);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(14);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(15);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        checkDelete();
        PsiFileImplUtil.doDelete(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkDelete() throws IncorrectOperationException {
        if (isCopy()) {
            throw new IncorrectOperationException();
        }
        CheckUtil.checkWritable(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        if (isCopy()) {
            return true;
        }
        if (getVirtualFile().isValid() && !this.myManager.getProject().isDisposed()) {
            if (!this.myPossiblyInvalidated) {
                return true;
            }
            if (((FileManagerImpl) this.myManager.getFileManager()).evaluateValidity(this)) {
                this.myPossiblyInvalidated = false;
                PsiInvalidElementAccessException.setInvalidationTrace(this, null);
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return isCopy() || getVirtualFile().isWritable();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return !isCopy();
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiBinaryFile:" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public FileType getFileType() {
        FileType fileType = this.myViewProvider.getFileType();
        if (fileType == null) {
            $$$reportNull$$$0(18);
        }
        return fileType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public PsiFile[] getPsiRoots() {
        return new PsiFile[]{this};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public FileViewProvider getViewProvider() {
        AbstractFileViewProvider abstractFileViewProvider = this.myViewProvider;
        if (abstractFileViewProvider == null) {
            $$$reportNull$$$0(20);
        }
        return abstractFileViewProvider;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        return FileContextUtil.getFileContext(this);
    }

    public void putInfo(Map<? super String, ? super String> map) {
        if (map == null) {
            $$$reportNull$$$0(21);
        }
        map.put(PsiTreeChangeEvent.PROP_FILE_NAME, getName());
        map.put("fileType", getFileType().getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx
    public void markInvalidated() {
        this.myPossiblyInvalidated = true;
        DebugUtil.onInvalidated(this);
    }
}
