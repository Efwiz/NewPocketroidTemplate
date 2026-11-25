package org.jetbrains.kotlin.com.intellij.psi.impl.file;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import javax.swing.Icon;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.ide.util.PsiNavigationSupport;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.impl.LoadTextUtil;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.InvalidVirtualFileAccessException;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.NonPhysicalFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiCheckedRenameElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiFileSystemItemProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDirectoryImpl extends PsiElementBase implements Queryable, PsiDirectory {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final VirtualFile myFile;
    private final PsiManagerImpl myManager;
    private static final Key<Boolean> UPDATE_ADDED_FILE_KEY = Key.create("UPDATE_ADDED_FILE_KEY");
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiDirectoryImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 7 || i == 8 || i == 9 || i == 13 || i == 14 || i == 18 || i == 21 || i == 24 || i == 25) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 7 || i == 8 || i == 9 || i == 13 || i == 14 || i == 18 || i == 21 || i == 24 || i == 25) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "file";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 13:
            case 14:
            case 18:
            case 21:
            case 24:
            case 25:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/PsiDirectoryImpl";
                break;
            case 6:
            case 10:
            case 11:
            case 17:
            case 19:
            case 20:
            case 26:
                objArr[0] = "name";
                break;
            case 12:
                objArr[0] = "processor";
                break;
            case 15:
                objArr[0] = "text";
                break;
            case 16:
            case 27:
            case 28:
            case 29:
            case 30:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 22:
                objArr[0] = "newName";
                break;
            case 23:
                objArr[0] = "originalFile";
                break;
            case 31:
                objArr[0] = "newElement";
                break;
            case 32:
                objArr[0] = "visitor";
                break;
            case 33:
                objArr[0] = CompilerOptions.INFO;
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 2) {
            objArr[1] = "getVirtualFile";
        } else if (i == 3) {
            objArr[1] = "getLanguage";
        } else if (i == 4) {
            objArr[1] = "getManager";
        } else if (i == 5) {
            objArr[1] = "getName";
        } else if (i == 7) {
            objArr[1] = "setName";
        } else if (i == 8) {
            objArr[1] = "getSubdirectories";
        } else if (i == 9) {
            objArr[1] = "getFiles";
        } else if (i == 13) {
            objArr[1] = "getChildren";
        } else if (i == 14) {
            objArr[1] = "textToCharArray";
        } else if (i == 18) {
            objArr[1] = "createSubdirectory";
        } else if (i == 21) {
            objArr[1] = "createFile";
        } else if (i == 24) {
            objArr[1] = "copyFileFrom";
        } else if (i != 25) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/PsiDirectoryImpl";
        } else {
            objArr[1] = "findCopy";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 13:
            case 14:
            case 18:
            case 21:
            case 24:
            case 25:
                break;
            case 6:
                objArr[2] = "setName";
                break;
            case 10:
                objArr[2] = "findSubdirectory";
                break;
            case 11:
                objArr[2] = "findFile";
                break;
            case 12:
                objArr[2] = "processChildren";
                break;
            case 15:
            case 16:
                objArr[2] = "textMatches";
                break;
            case 17:
                objArr[2] = "createSubdirectory";
                break;
            case 19:
                objArr[2] = "checkCreateSubdirectory";
                break;
            case 20:
                objArr[2] = "createFile";
                break;
            case 22:
            case 23:
                objArr[2] = "copyFileFrom";
                break;
            case 26:
                objArr[2] = "checkCreateFile";
                break;
            case 27:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 28:
                objArr[2] = "checkAdd";
                break;
            case 29:
                objArr[2] = "addBefore";
                break;
            case 30:
                objArr[2] = "addAfter";
                break;
            case 31:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 32:
                objArr[2] = "accept";
                break;
            case 33:
                objArr[2] = "putInfo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 7 && i != 8 && i != 9 && i != 13 && i != 14 && i != 18 && i != 21 && i != 24 && i != 25) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public boolean canNavigateToSource() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement findElementAt(int i) {
        return null;
    }

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

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
    public boolean isDirectory() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(CharSequence charSequence) {
        if (charSequence != null) {
            return false;
        }
        $$$reportNull$$$0(15);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(PsiElement psiElement) {
        if (psiElement != null) {
            return false;
        }
        $$$reportNull$$$0(16);
        return false;
    }

    public PsiDirectoryImpl(PsiManagerImpl psiManagerImpl, VirtualFile virtualFile) {
        if (psiManagerImpl == null) {
            $$$reportNull$$$0(0);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        this.myManager = psiManagerImpl;
        this.myFile = virtualFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory, org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
    public VirtualFile getVirtualFile() {
        VirtualFile virtualFile = this.myFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        return virtualFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return this.myFile.isValid() && !getProject().isDisposed();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        Language language = Language.ANY;
        if (language == null) {
            $$$reportNull$$$0(3);
        }
        return language;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiManager getManager() {
        PsiManagerImpl psiManagerImpl = this.myManager;
        if (psiManagerImpl == null) {
            $$$reportNull$$$0(4);
        }
        return psiManagerImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String get_name() {
        String name2 = this.myFile.getName();
        if (name2 == null) {
            $$$reportNull$$$0(5);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        checkSetName(str);
        try {
            this.myFile.rename(this.myManager, str);
            return this;
        } catch (IOException e) {
            throw new IncorrectOperationException(e.toString());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCheckedRenameElement
    public void checkSetName(String str) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        VirtualFile parent = this.myFile.getParent();
        if (parent == null) {
            throw new IncorrectOperationException(CoreBundle.message("cannot.rename.root.directory", this.myFile.getPath()));
        }
        VirtualFile virtualFileFindChild = parent.findChild(str);
        if (virtualFileFindChild != null && !virtualFileFindChild.equals(this.myFile)) {
            throw new IncorrectOperationException(CoreBundle.message("file.already.exists.error", virtualFileFindChild.getPresentableUrl()));
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory
    public PsiDirectory getParentDirectory() {
        VirtualFile parent = this.myFile.getParent();
        if (parent == null) {
            return null;
        }
        if (!parent.isValid()) {
            LOG.error("Invalid parent: " + parent + " of dir " + this.myFile + ", dir.valid=" + this.myFile.isValid());
            return null;
        }
        return this.myManager.findDirectory(parent);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory
    public PsiDirectory[] getSubdirectories() {
        VirtualFile[] children = this.myFile.getChildren();
        ArrayList arrayList = new ArrayList();
        for (VirtualFile virtualFile : children) {
            PsiDirectory psiDirectoryFindDirectory = this.myManager.findDirectory(virtualFile);
            if (psiDirectoryFindDirectory != null) {
                arrayList.add(psiDirectoryFindDirectory);
            }
        }
        PsiDirectory[] psiDirectoryArr = (PsiDirectory[]) arrayList.toArray(PsiDirectory.EMPTY_ARRAY);
        if (psiDirectoryArr == null) {
            $$$reportNull$$$0(8);
        }
        return psiDirectoryArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory
    public PsiFile[] getFiles() {
        if (!this.myFile.isValid()) {
            throw new InvalidVirtualFileAccessException(this.myFile);
        }
        VirtualFile[] children = this.myFile.getChildren();
        ArrayList arrayList = new ArrayList();
        for (VirtualFile virtualFile : children) {
            PsiFile psiFileFindFile = this.myManager.findFile(virtualFile);
            if (psiFileFindFile != null) {
                arrayList.add(psiFileFindFile);
            }
        }
        PsiFile[] psiFileArray = PsiUtilCore.toPsiFileArray(arrayList);
        if (psiFileArray == null) {
            $$$reportNull$$$0(9);
        }
        return psiFileArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory
    public PsiDirectory findSubdirectory(String str) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        ProgressManager.checkCanceled();
        VirtualFile virtualFileFindChild = this.myFile.findChild(str);
        if (virtualFileFindChild == null) {
            return null;
        }
        return this.myManager.findDirectory(virtualFileFindChild);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory
    public PsiFile findFile(String str) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        ProgressManager.checkCanceled();
        VirtualFile virtualFileFindChild = this.myFile.findChild(str);
        if (virtualFileFindChild == null) {
            return null;
        }
        if (!virtualFileFindChild.isValid()) {
            LOG.error("Invalid file: " + virtualFileFindChild + " in dir " + this.myFile + ", dir.valid=" + this.myFile.isValid());
            return null;
        }
        return this.myManager.findFile(virtualFileFindChild);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
    public boolean processChildren(PsiElementProcessor<? super PsiFileSystemItem> psiElementProcessor) {
        if (psiElementProcessor == null) {
            $$$reportNull$$$0(12);
        }
        checkValid();
        for (VirtualFile virtualFile : this.myFile.getChildren()) {
            ProgressManager.checkCanceled();
            if (virtualFile.isValid()) {
                boolean zIsDirectory = virtualFile.isDirectory();
                if (!(psiElementProcessor instanceof PsiFileSystemItemProcessor) || ((PsiFileSystemItemProcessor) psiElementProcessor).acceptItem(virtualFile.getName(), zIsDirectory)) {
                    PsiCheckedRenameElement psiCheckedRenameElementFindDirectory = zIsDirectory ? this.myManager.findDirectory(virtualFile) : this.myManager.findFile(virtualFile);
                    if (psiCheckedRenameElementFindDirectory != null && !psiElementProcessor.execute(psiCheckedRenameElementFindDirectory)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        checkValid();
        final ArrayList arrayList = new ArrayList(this.myFile.getChildren().length);
        processChildren(new PsiElementProcessor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiDirectoryImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
            public final boolean execute(PsiElement psiElement) {
                return PsiDirectoryImpl.lambda$getChildren$0(arrayList, (PsiFileSystemItem) psiElement);
            }
        });
        PsiElement[] psiElementArray = PsiUtilCore.toPsiElementArray(arrayList);
        if (psiElementArray == null) {
            $$$reportNull$$$0(13);
        }
        return psiElementArray;
    }

    static /* synthetic */ boolean lambda$getChildren$0(ArrayList arrayList, PsiFileSystemItem psiFileSystemItem) {
        arrayList.add(psiFileSystemItem);
        return true;
    }

    private void checkValid() {
        if (!isValid()) {
            throw new PsiInvalidElementAccessException(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiDirectory getLightOwner() {
        return getParentDirectory();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public char[] textToCharArray() {
        char[] cArr = ArrayUtilRt.EMPTY_CHAR_ARRAY;
        if (cArr == null) {
            $$$reportNull$$$0(14);
        }
        return cArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final boolean isWritable() {
        return this.myFile.isWritable();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return ((this.myFile.getFileSystem() instanceof NonPhysicalFileSystem) || this.myFile.getFileSystem().getProtocol().equals("temp")) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory
    public PsiDirectory createSubdirectory(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        checkCreateSubdirectory(str);
        try {
            VirtualFile virtualFileCreateChildDirectory = getVirtualFile().createChildDirectory(this.myManager, str);
            PsiDirectory psiDirectoryFindDirectory = this.myManager.findDirectory(virtualFileCreateChildDirectory);
            if (psiDirectoryFindDirectory == null) {
                throw new IncorrectOperationException("Cannot find directory in '" + virtualFileCreateChildDirectory.getPresentableUrl() + FormatHelper.QUOTE);
            }
            if (psiDirectoryFindDirectory == null) {
                $$$reportNull$$$0(18);
            }
            return psiDirectoryFindDirectory;
        } catch (IOException e) {
            throw new IncorrectOperationException(e.toString());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory
    public void checkCreateSubdirectory(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        VirtualFile virtualFileFindChild = getVirtualFile().findChild(str);
        if (virtualFileFindChild != null) {
            throw new IncorrectOperationException(CoreBundle.message("file.already.exists.error", virtualFileFindChild.getPresentableUrl()));
        }
        CheckUtil.checkWritable(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory
    public PsiFile createFile(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        checkCreateFile(str);
        try {
            PsiFile psiFileFindFile = this.myManager.findFile(getVirtualFile().createChildData(this.myManager, str));
            if (psiFileFindFile == null) {
                $$$reportNull$$$0(21);
            }
            return psiFileFindFile;
        } catch (IOException e) {
            throw new IncorrectOperationException(e.toString());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory
    public PsiFile copyFileFrom(String str, PsiFile psiFile) throws Throwable {
        VirtualFile virtualFileCopyFile;
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(23);
        }
        checkCreateFile(str);
        Document document = PsiDocumentManager.getInstance(getProject()).getDocument(psiFile);
        if (document != null) {
            FileDocumentManager.getInstance().saveDocument(document);
        }
        VirtualFile virtualFile = getVirtualFile();
        try {
            VirtualFile virtualFile2 = psiFile.getVirtualFile();
            if (virtualFile2 == null) {
                throw new IncorrectOperationException("Cannot copy non-physical file: " + psiFile);
            }
            if (virtualFile.getFileSystem() == virtualFile2.getFileSystem()) {
                virtualFileCopyFile = virtualFile2.copy(this, virtualFile, str);
            } else if (virtualFile2 instanceof LightVirtualFile) {
                virtualFileCopyFile = virtualFile.createChildData(this, str);
                virtualFileCopyFile.setBinaryContent(virtualFile2.contentsToByteArray());
            } else {
                virtualFileCopyFile = VfsUtilCore.copyFile(this, virtualFile2, virtualFile, str);
            }
            if (UPDATE_ADDED_FILE_KEY.get(this, true).booleanValue()) {
                DumbService.getInstance(getProject()).completeJustSubmittedTasks();
                PsiFile psiFileFindCopy = findCopy(virtualFileCopyFile, virtualFile2);
                UpdateAddedFileProcessor.updateAddedFiles(Collections.singletonList(psiFileFindCopy));
                if (psiFileFindCopy == null) {
                    $$$reportNull$$$0(24);
                }
                return psiFileFindCopy;
            }
            return findCopy(virtualFileCopyFile, virtualFile2);
        } catch (IOException e) {
            throw new IncorrectOperationException(e);
        }
    }

    private PsiFile findCopy(VirtualFile virtualFile, VirtualFile virtualFile2) {
        PsiFile psiFileFindFile = this.myManager.findFile(virtualFile);
        if (psiFileFindFile == null) {
            throw new IncorrectOperationException("Could not find file " + virtualFile + " after copying " + virtualFile2);
        }
        if (psiFileFindFile == null) {
            $$$reportNull$$$0(25);
        }
        return psiFileFindFile;
    }

    public <T extends Throwable> void executeWithUpdatingAddedFilesDisabled(ThrowableRunnable<T> throwableRunnable) throws Throwable {
        try {
            putUserData(UPDATE_ADDED_FILE_KEY, false);
            throwableRunnable.run();
        } finally {
            putUserData(UPDATE_ADDED_FILE_KEY, null);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDirectory
    public void checkCreateFile(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        VirtualFile virtualFileFindChild = getVirtualFile().findChild(str);
        if (virtualFileFindChild != null) {
            throw new IncorrectOperationException(CoreBundle.message("file.already.exists.error", virtualFileFindChild.getPresentableUrl()));
        }
        CheckUtil.checkWritable(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement psiElement) throws Throwable {
        VirtualFile virtualFileCopyFile;
        if (psiElement == null) {
            $$$reportNull$$$0(27);
        }
        checkAdd(psiElement);
        if (psiElement instanceof PsiFile) {
            PsiFile psiFile = (PsiFile) psiElement;
            try {
                Document document = null;
                if (psiFile instanceof PsiFileImpl) {
                    virtualFileCopyFile = this.myFile.createChildData(this.myManager, psiFile.get_name());
                    String text = psiFile.getText();
                    PsiFile psiFileFindFile = getManager().findFile(virtualFileCopyFile);
                    if (psiFileFindFile != null) {
                        document = psiFileFindFile.getViewProvider().getDocument();
                    }
                    FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();
                    if (document != null) {
                        document.setText(text);
                        if (psiFileFindFile.getIsPhysical()) {
                            fileDocumentManager.saveDocument(document);
                        }
                        PsiDocumentManager.getInstance(getProject()).commitDocument(document);
                    } else {
                        String lineSeparator = fileDocumentManager.getLineSeparator(virtualFileCopyFile, getProject());
                        if (!lineSeparator.equals("\n")) {
                            text = StringUtil.convertLineSeparators(text, lineSeparator);
                        }
                        LoadTextUtil.write(getProject(), virtualFileCopyFile, this.myManager, text, -1L);
                    }
                } else {
                    byte[] storedContents = ((PsiBinaryFileImpl) psiFile).getStoredContents();
                    if (storedContents != null) {
                        VirtualFile virtualFileCreateChildData = this.myFile.createChildData(this.myManager, psiFile.get_name());
                        virtualFileCreateChildData.setBinaryContent(storedContents);
                        virtualFileCopyFile = virtualFileCreateChildData;
                    } else {
                        virtualFileCopyFile = VfsUtilCore.copyFile(null, psiFile.getVirtualFile(), this.myFile);
                    }
                }
                PsiFile psiFileFindFile2 = this.myManager.findFile(virtualFileCopyFile);
                if (psiFileFindFile2 == null) {
                    throw new IncorrectOperationException("Could not find file " + virtualFileCopyFile);
                }
                UpdateAddedFileProcessor.updateAddedFiles(Collections.singletonList(psiFileFindFile2));
                return psiFileFindFile2;
            } catch (IOException e) {
                throw new IncorrectOperationException(e);
            }
        }
        throw new IncorrectOperationException(psiElement + " (" + psiElement.getClass() + Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(28);
        }
        CheckUtil.checkWritable(this);
        if ((psiElement instanceof PsiDirectory) || (psiElement instanceof PsiFile)) {
            final String str = ((PsiFileSystemItem) psiElement).get_name();
            final boolean zIsCaseSensitive = getVirtualFile().isCaseSensitive();
            VirtualFile virtualFile = (VirtualFile) ContainerUtil.find(getVirtualFile().getChildren(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiDirectoryImpl$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return Comparing.strEqual(((VirtualFile) obj).getName(), str, zIsCaseSensitive);
                }
            });
            if (virtualFile != null) {
                throw new IncorrectOperationException(CoreBundle.message(virtualFile.isDirectory() ? "dir.already.exists.error" : "file.already.exists.error", virtualFile.getPresentableUrl()));
            }
            return;
        }
        throw new IncorrectOperationException(psiElement.getClass().getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(29);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(30);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws Throwable {
        checkDelete();
        try {
            this.myFile.delete(this.myManager);
        } catch (IOException e) {
            throw new IncorrectOperationException(e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkDelete() throws Throwable {
        CheckUtil.checkDelete(this.myFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(31);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(32);
        }
        psiElementVisitor.visitDirectory(this);
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiDirectory:" + this.myFile.getPresentableUrl();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public void navigate(boolean z) {
        PsiNavigationSupport.getInstance().navigateToDirectory(this, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected Icon getElementIcon(int i) {
        return IconManager.getInstance().tooltipOnlyIfComposite(PlatformIcons.FOLDER_ICON);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable
    public void putInfo(Map<? super String, ? super String> map) {
        if (map == null) {
            $$$reportNull$$$0(33);
        }
        map.put(PsiTreeChangeEvent.PROP_FILE_NAME, get_name());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PsiDirectoryImpl psiDirectoryImpl = (PsiDirectoryImpl) obj;
        return this.myManager.equals(psiDirectoryImpl.myManager) && this.myFile.equals(psiDirectoryImpl.myFile);
    }

    public int hashCode() {
        return (this.myManager.hashCode() * 31) + this.myFile.hashCode();
    }
}
