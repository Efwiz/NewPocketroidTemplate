package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.swing.Icon;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.ide.util.PsiNavigationSupport;
import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.model.ModelBranch;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.openapi.application.AppUIExecutor;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileWithId;
import org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiLock;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.BlockSupportImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.FreeThreadedFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.ResolveScopeManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.SharedPsiElementImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiFileImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.FileContextUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.AstSpine;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubTree;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTree;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeBuilder;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.testFramework.ReadOnlyLightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.AstLoadingFilter;
import org.jetbrains.kotlin.com.intellij.util.FileContentUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.PatchedWeakReference;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.indexing.IndexingDataKeys;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiFileImpl extends ElementBase implements Queryable, PsiFileEx, PsiFileWithStubSupport, Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected IElementType myContentElementType;
    private IElementType myElementType;
    private volatile boolean myLoadingAst;
    protected final PsiManagerEx myManager;
    private long myModificationStamp;
    protected PsiFile myOriginalFile;
    private volatile boolean myPossiblyInvalidated;
    private final PsiLock myPsiLock;
    private volatile FileTrees myTrees;
    private final AbstractFileViewProvider myViewProvider;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiFileImpl.class);
    public static final Key<Boolean> BUILDING_STUB = new Key<>("Don't use stubs mark!");
    private static final Comparator<PsiFile> FILE_BY_LANGUAGE_ID = Comparator.comparing(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return ((PsiFile) obj).getLanguage().getID();
        }
    });

    /* JADX WARN: Code duplicated, block: B:12:0x0020 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 12 && i != 13 && i != 23 && i != 24 && i != 35) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 39:
                                case 40:
                                case 41:
                                case 42:
                                case 43:
                                    break;
                                default:
                                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                    break;
                            }
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 12 && i != 13 && i != 23 && i != 24 && i != 35) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 39:
                                case 40:
                                case 41:
                                case 42:
                                case 43:
                                    i2 = 2;
                                    break;
                                default:
                                    i2 = 3;
                                    break;
                            }
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
            case 2:
                objArr[0] = org.catrobat.catroid.common.Constants.EXTRA_PROVIDER;
                break;
            case 3:
            default:
                objArr[0] = "elementType";
                break;
            case 4:
            case 36:
                objArr[0] = "processor";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 35:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiFileImpl";
                break;
            case 11:
                objArr[0] = "name";
                break;
            case 14:
                objArr[0] = "originalFile";
                break;
            case 21:
                objArr[0] = "trees";
                break;
            case 22:
                objArr[0] = "treeElement";
                break;
            case 25:
                objArr[0] = "visitor";
                break;
            case 26:
                objArr[0] = "text";
                break;
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 32:
                objArr[0] = "first";
                break;
            case 33:
                objArr[0] = "last";
                break;
            case 34:
                objArr[0] = "newElement";
                break;
            case 37:
                objArr[0] = "state";
                break;
            case 38:
                objArr[0] = "place";
                break;
            case 44:
            case 46:
                objArr[0] = CompilerOptions.INFO;
                break;
            case 45:
                objArr[0] = "psiFile";
                break;
        }
        if (i == 12) {
            objArr[1] = "getContainingFile";
        } else if (i == 13) {
            objArr[1] = "getOriginalFile";
        } else if (i == 23) {
            objArr[1] = "createTreeElementPointer";
        } else if (i == 24) {
            objArr[1] = "getChildren";
        } else if (i != 35) {
            switch (i) {
                case 5:
                case 6:
                    objArr[1] = "loadTreeElement";
                    break;
                case 7:
                case 8:
                    objArr[1] = "getStubbedSpine";
                    break;
                case 9:
                    objArr[1] = "createFileElement";
                    break;
                case 10:
                    objArr[1] = "getName";
                    break;
                default:
                    switch (i) {
                        case 15:
                            objArr[1] = "getPsiRoots";
                            break;
                        case 16:
                            objArr[1] = "getLanguage";
                            break;
                        case 17:
                            objArr[1] = "getViewProvider";
                            break;
                        case 18:
                            objArr[1] = "textToCharArray";
                            break;
                        case 19:
                            objArr[1] = "findChildrenByClass";
                            break;
                        case 20:
                            objArr[1] = "setStubTree";
                            break;
                        default:
                            switch (i) {
                                case 39:
                                    objArr[1] = "getResolveScope";
                                    break;
                                case 40:
                                    objArr[1] = "getUseScope";
                                    break;
                                case 41:
                                    objArr[1] = "getProject";
                                    break;
                                case 42:
                                case 43:
                                    objArr[1] = "calcStubTree";
                                    break;
                                default:
                                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiFileImpl";
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getReferences";
        }
        switch (i) {
            case 3:
                objArr[2] = ConfigConstants.CONFIG_INIT_SECTION;
                break;
            case 4:
                objArr[2] = "processChildren";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 35:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
                break;
            case 11:
                objArr[2] = "setName";
                break;
            case 14:
                objArr[2] = "setOriginalFile";
                break;
            case 21:
                objArr[2] = "updateTrees";
                break;
            case 22:
                objArr[2] = "createTreeElementPointer";
                break;
            case 25:
                objArr[2] = "acceptChildren";
                break;
            case 26:
            case 27:
                objArr[2] = "textMatches";
                break;
            case 28:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 29:
                objArr[2] = "addBefore";
                break;
            case 30:
                objArr[2] = "addAfter";
                break;
            case 31:
                objArr[2] = "checkAdd";
                break;
            case 32:
            case 33:
                objArr[2] = "addRangeBefore";
                break;
            case 34:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 36:
            case 37:
            case 38:
                objArr[2] = "processDeclarations";
                break;
            case 44:
            case 45:
            case 46:
                objArr[2] = "putInfo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 12 && i != 13 && i != 23 && i != 24 && i != 35) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    break;
                default:
                    switch (i) {
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                            break;
                        default:
                            switch (i) {
                                case 39:
                                case 40:
                                case 41:
                                case 42:
                                case 43:
                                    break;
                                default:
                                    throw new IllegalArgumentException(str2);
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ FileElement lambda$createTreeElementPointer$3(FileElement fileElement) {
        return fileElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getStartOffsetInParent() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
    public boolean isDirectory() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return this == psiElement;
    }

    public boolean isTemplateDataFile() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
    public boolean processChildren(PsiElementProcessor<? super PsiFileSystemItem> psiElementProcessor) {
        if (psiElementProcessor != null) {
            return true;
        }
        $$$reportNull$$$0(4);
        return true;
    }

    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(36);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(37);
        }
        if (psiElement2 != null) {
            return true;
        }
        $$$reportNull$$$0(38);
        return true;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected PsiFileImpl(IElementType iElementType, IElementType iElementType2, FileViewProvider fileViewProvider) {
        this(fileViewProvider);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        if (fileViewProvider == null) {
            $$$reportNull$$$0(1);
        }
        init(iElementType, iElementType2);
    }

    protected PsiFileImpl(FileViewProvider fileViewProvider) {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(2);
        }
        this.myTrees = FileTrees.noStub(null, this);
        this.myManager = (PsiManagerEx) fileViewProvider.getManager();
        AbstractFileViewProvider abstractFileViewProvider = (AbstractFileViewProvider) fileViewProvider;
        this.myViewProvider = abstractFileViewProvider;
        this.myPsiLock = abstractFileViewProvider.getFilePsiLock();
    }

    public void setContentElementType(IElementType iElementType) {
        LOG.assertTrue(iElementType instanceof ILazyParseableElementType, iElementType);
        this.myContentElementType = iElementType;
    }

    public IElementType getContentElementType() {
        return this.myContentElementType;
    }

    protected void init(IElementType iElementType, IElementType iElementType2) {
        if (iElementType == null) {
            $$$reportNull$$$0(3);
        }
        this.myElementType = iElementType;
        setContentElementType(iElementType2);
    }

    public TreeElement createContentLeafElement(CharSequence charSequence) {
        IElementType iElementType = this.myContentElementType;
        if (iElementType instanceof ILazyParseableElementType) {
            return ASTFactory.lazy((ILazyParseableElementType) iElementType, charSequence);
        }
        return ASTFactory.leaf(iElementType, charSequence);
    }

    public FileElement getTreeElement() {
        FileElement fileElementDerefTreeElement = derefTreeElement();
        if (fileElementDerefTreeElement != null) {
            return fileElementDerefTreeElement;
        }
        if (getViewProvider().isPhysical()) {
            return null;
        }
        return loadTreeElement();
    }

    FileElement derefTreeElement() {
        return this.myTrees.derefTreeElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile, org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
    public VirtualFile getVirtualFile() {
        VirtualFile virtualFile = IndexingDataKeys.VIRTUAL_FILE.get(this);
        if (virtualFile != null) {
            return virtualFile;
        }
        if (getViewProvider().isEventSystemEnabled() || ModelBranch.getPsiBranch(this) != null) {
            return getViewProvider().getVirtualFile();
        }
        return null;
    }

    public boolean isValid() {
        if (this.myManager.getProject().isDisposed() || !this.myViewProvider.getVirtualFile().isValid()) {
            return false;
        }
        if (!this.myPossiblyInvalidated) {
            return true;
        }
        if (!((FileManagerImpl) this.myManager.getFileManager()).evaluateValidity(this)) {
            return false;
        }
        this.myPossiblyInvalidated = false;
        PsiInvalidElementAccessException.setInvalidationTrace(this, null);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx
    public final void markInvalidated() {
        this.myPossiblyInvalidated = true;
        DebugUtil.onInvalidated(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx
    public boolean isContentsLoaded() {
        return derefTreeElement() != null;
    }

    protected void assertReadAccessAllowed() {
        if (this.myViewProvider.getVirtualFile() instanceof ReadOnlyLightVirtualFile) {
            return;
        }
        ApplicationManager.getApplication().assertReadAccessAllowed();
    }

    private FileElement loadTreeElement() {
        assertReadAccessAllowed();
        if (this.myPossiblyInvalidated) {
            PsiUtilCore.ensureValid(this);
        }
        FileViewProvider viewProvider = getViewProvider();
        if (viewProvider.isPhysical()) {
            VirtualFile virtualFile = viewProvider.getVirtualFile();
            AstLoadingFilter.assertTreeLoadingAllowed(virtualFile);
            if (this.myManager.isAssertOnFileLoading(virtualFile)) {
                reportProhibitedAstAccess(virtualFile);
            }
        }
        synchronized (this.myPsiLock) {
            FileElement fileElementDerefTreeElement = derefTreeElement();
            if (fileElementDerefTreeElement != null) {
                if (fileElementDerefTreeElement == null) {
                    $$$reportNull$$$0(5);
                }
                return fileElementDerefTreeElement;
            }
            FileElement fileElementCreateFileElement = createFileElement(viewProvider.getContents());
            fileElementCreateFileElement.setPsi(this);
            this.myLoadingAst = true;
            try {
                updateTrees(this.myTrees.withAst(createTreeElementPointer(fileElementCreateFileElement)));
                this.myLoadingAst = false;
                Logger logger = LOG;
                if (logger.isDebugEnabled() && viewProvider.isPhysical()) {
                    logger.debug("Loaded text for file " + viewProvider.getVirtualFile().getPresentableUrl());
                }
                if (fileElementCreateFileElement == null) {
                    $$$reportNull$$$0(6);
                }
                return fileElementCreateFileElement;
            } catch (Throwable th) {
                this.myLoadingAst = false;
                throw th;
            }
        }
    }

    private static void reportProhibitedAstAccess(VirtualFile virtualFile) {
        LOG.error("Access to tree elements not allowed for '" + virtualFile.getPresentableUrl() + "'.\nTry using stub-based PSI API to avoid expensive AST loading for files that aren't already opened in the editor.\nConsult this method's javadoc for more details.");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileWithStubSupport
    public StubbedSpine getStubbedSpine() {
        StubTree greenStubTree = getGreenStubTree();
        if (greenStubTree != null) {
            StubbedSpine spine = greenStubTree.getSpine();
            if (spine == null) {
                $$$reportNull$$$0(7);
            }
            return spine;
        }
        AstSpine stubbedSpine = calcTreeElement().getStubbedSpine();
        if (!this.myTrees.useSpineRefs()) {
            synchronized (this.myPsiLock) {
                updateTrees(this.myTrees.switchToSpineRefs(FileTrees.getAllSpinePsi(stubbedSpine)));
            }
        }
        if (stubbedSpine == null) {
            $$$reportNull$$$0(8);
        }
        return stubbedSpine;
    }

    public IStubFileElementType<?> getElementTypeForStubBuilder() {
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(getLanguage());
        IFileElementType fileNodeType = parserDefinitionForLanguage == null ? null : parserDefinitionForLanguage.getFileNodeType();
        if (fileNodeType instanceof IStubFileElementType) {
            return (IStubFileElementType) fileNodeType;
        }
        return null;
    }

    protected FileElement createFileElement(CharSequence charSequence) {
        FileElement fileElement;
        TreeElement treeElementCreateContentLeafElement = createContentLeafElement(charSequence);
        if (treeElementCreateContentLeafElement instanceof FileElement) {
            fileElement = (FileElement) treeElementCreateContentLeafElement;
        } else {
            FileElement fileElement2 = (FileElement) ASTFactory.composite(this.myElementType);
            fileElement2.rawAddChildrenWithoutNotifications(treeElementCreateContentLeafElement);
            fileElement = fileElement2;
        }
        if (fileElement == null) {
            $$$reportNull$$$0(9);
        }
        return fileElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public void clearCaches() {
        this.myModificationStamp++;
    }

    public String getText() {
        FileElement fileElementDerefTreeElement = derefTreeElement();
        if (!isValid()) {
            ProgressManager.checkCanceled();
            if (fileElementDerefTreeElement != null) {
                return fileElementDerefTreeElement.getText();
            }
            throw new PsiInvalidElementAccessException(this);
        }
        String string = getViewProvider().getContents().toString();
        if (fileElementDerefTreeElement == null || string.length() == fileElementDerefTreeElement.getTextLength()) {
            return string;
        }
        throw new AssertionError("File text mismatch: tree.length=" + fileElementDerefTreeElement.getTextLength() + "; psi.length=" + string.length() + "; this=" + this + "; vp=" + getViewProvider());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextLength() {
        FileElement fileElementDerefTreeElement = derefTreeElement();
        if (fileElementDerefTreeElement != null) {
            return fileElementDerefTreeElement.getTextLength();
        }
        PsiUtilCore.ensureValid(this);
        return getViewProvider().getContents().length();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        return new TextRange(0, getTextLength());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNextSibling() {
        return SharedPsiElementImplUtil.getNextSibling(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getPrevSibling() {
        return SharedPsiElementImplUtil.getPrevSibling(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public long getModificationStamp() {
        PsiElement context = getContext();
        PsiFile containingFile = (context == null || !context.isValid()) ? null : context.getContainingFile();
        return this.myModificationStamp + (containingFile == null ? 0L : containingFile.getModificationStamp());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public void subtreeChanged() {
        FileElement treeElement = getTreeElement();
        if (treeElement != null) {
            treeElement.clearCaches();
        }
        synchronized (this.myPsiLock) {
            if (this.myTrees.useSpineRefs()) {
                LOG.error("Somebody has requested stubbed spine during PSI operations; not only is this expensive, but will also cause stub PSI invalidation");
            }
            updateTrees(this.myTrees.clearStub("subtreeChanged"));
        }
        clearCaches();
        getViewProvider().rootChanged(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public PsiFileImpl clone() {
        FileViewProvider viewProvider = getViewProvider();
        FileViewProvider fileViewProviderClone = viewProvider.clone();
        Language language = getLanguage();
        if (fileViewProviderClone == null) {
            throw new AssertionError("Unable to clone the view provider: " + viewProvider + "; " + language);
        }
        PsiFileImpl fileCopy = BlockSupportImpl.getFileCopy(this, fileViewProviderClone);
        copyCopyableDataTo(fileCopy);
        if (getTreeElement() != null) {
            FileElement fileElement = (FileElement) calcTreeElement().clone();
            fileCopy.setTreeElementPointer(fileElement);
            fileElement.setPsi(fileCopy);
        } else {
            fileCopy.setTreeElementPointer(null);
        }
        if (viewProvider.isEventSystemEnabled()) {
            fileCopy.myOriginalFile = this;
        } else {
            PsiFile psiFile = this.myOriginalFile;
            if (psiFile != null) {
                fileCopy.myOriginalFile = psiFile;
            }
        }
        FileManagerImpl.clearPsiCaches(fileViewProviderClone);
        return fileCopy;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String name2 = getViewProvider().getVirtualFile().getName();
        if (name2 == null) {
            $$$reportNull$$$0(10);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        checkSetName(str);
        return PsiFileImplUtil.setName(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCheckedRenameElement
    public void checkSetName(String str) {
        if (getViewProvider().isEventSystemEnabled()) {
            PsiFileImplUtil.checkSetName(this, str);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return getViewProvider().getVirtualFile().isWritable();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile, org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiDirectory getLightParent() {
        return getContainingDirectory();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public PsiDirectory getContainingDirectory() {
        VirtualFile virtualFile = getViewProvider().getVirtualFile();
        VirtualFile parent = virtualFile.getParent();
        if (parent == null) {
            return null;
        }
        if (!parent.isValid()) {
            LOG.error("Invalid parent: " + parent + " of file " + virtualFile + ", file.valid=" + virtualFile.isValid());
            return null;
        }
        return getManager().findDirectory(parent);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        checkDelete();
        PsiFileImplUtil.doDelete(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkDelete() throws IncorrectOperationException {
        if (!getViewProvider().isEventSystemEnabled()) {
            throw new IncorrectOperationException();
        }
        CheckUtil.checkWritable(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public PsiFile getOriginalFile() {
        PsiFile psiFile = this.myOriginalFile;
        if (psiFile == null) {
            psiFile = this;
        }
        if (psiFile == null) {
            $$$reportNull$$$0(13);
        }
        return psiFile;
    }

    public void setOriginalFile(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(14);
        }
        PsiFile originalFile = psiFile.getOriginalFile();
        this.myOriginalFile = originalFile;
        ((AbstractFileViewProvider) originalFile.getViewProvider()).registerAsCopy(this.myViewProvider);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public PsiFile[] getPsiRoots() {
        FileViewProvider viewProvider = getViewProvider();
        Set<Language> languages = viewProvider.getLanguages();
        int size = languages.size();
        PsiFile[] psiFileArr = new PsiFile[size];
        int i = 0;
        for (Language language : languages) {
            PsiFile psi = viewProvider.getPsi(language);
            if (psi == null) {
                LOG.error("PSI is null for " + language + "; in file: " + this);
            }
            psiFileArr[i] = psi;
            i++;
        }
        if (size > 1) {
            Arrays.sort(psiFileArr, FILE_BY_LANGUAGE_ID);
        }
        return psiFileArr;
    }

    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return getViewProvider().isEventSystemEnabled();
    }

    public Language getLanguage() {
        Language language = this.myElementType.getLanguage();
        if (language == null) {
            $$$reportNull$$$0(16);
        }
        return language;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public IFileElementType getFileElementType() {
        IElementType iElementType = this.myElementType;
        return iElementType instanceof IFileElementType ? (IFileElementType) iElementType : (IFileElementType) ObjectUtils.tryCast(this.myContentElementType, IFileElementType.class);
    }

    public FileViewProvider getViewProvider() {
        AbstractFileViewProvider abstractFileViewProvider = this.myViewProvider;
        if (abstractFileViewProvider == null) {
            $$$reportNull$$$0(17);
        }
        return abstractFileViewProvider;
    }

    public void setTreeElementPointer(FileElement fileElement) {
        updateTrees(FileTrees.noStub(fileElement, this));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement findElementAt(int i) {
        return getViewProvider().findElementAt(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference findReferenceAt(int i) {
        return getViewProvider().findReferenceAt(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public char[] textToCharArray() {
        char[] cArrFromSequence = CharArrayUtil.fromSequence(getViewProvider().getContents());
        if (cArrFromSequence == null) {
            $$$reportNull$$$0(18);
        }
        return cArrFromSequence;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T[] findChildrenByClass(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        for (PsiElement psiElement : getChildren()) {
            if (cls.isInstance(psiElement)) {
                arrayList.add(psiElement);
            }
        }
        T[] tArr = (T[]) arrayList.toArray(ArrayUtil.newArray(cls, arrayList.size()));
        if (tArr == null) {
            $$$reportNull$$$0(19);
        }
        return tArr;
    }

    public <T> T findChildByClass(Class<T> cls) {
        for (PsiElement psiElement : getChildren()) {
            T t = (T) psiElement;
            if (cls.isInstance(t)) {
                return t;
            }
        }
        return null;
    }

    public PsiElement getContext() {
        return FileContextUtil.getFileContext(this);
    }

    public void onContentReload() throws Throwable {
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        clearContent("onContentReload");
    }

    final void clearContent(final String str) throws Throwable {
        DebugUtil.performPsiModification(str, new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() {
                this.f$0.m6651xd7fc23ca(str);
            }
        });
        clearCaches();
    }

    /* JADX INFO: renamed from: lambda$clearContent$1$org-jetbrains-kotlin-com-intellij-psi-impl-source-PsiFileImpl, reason: not valid java name */
    /* synthetic */ void m6651xd7fc23ca(String str) throws RuntimeException {
        synchronized (this.myPsiLock) {
            FileElement fileElementDerefTreeElement = derefTreeElement();
            if (fileElementDerefTreeElement != null) {
                fileElementDerefTreeElement.detachFromFile();
                DebugUtil.onInvalidated(fileElementDerefTreeElement);
            }
            updateTrees(this.myTrees.clearStub(str));
            setTreeElementPointer(null);
        }
    }

    public StubElement getStub() {
        StubTree stubTree = getStubTree();
        if (stubTree != null) {
            return stubTree.getRoot();
        }
        return null;
    }

    public final StubElement<?> getGreenStub() {
        StubTree greenStubTree = getGreenStubTree();
        if (greenStubTree != null) {
            return greenStubTree.getRoot();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileWithStubSupport
    public StubTree getStubTree() {
        assertReadAccessAllowed();
        StubTree stubTree = null;
        if (getTreeElement() != null) {
            return null;
        }
        StubTree stubTreeDerefStub = derefStub();
        if (stubTreeDerefStub != null) {
            return stubTreeDerefStub;
        }
        if (Boolean.TRUE.equals(getUserData(BUILDING_STUB)) || this.myLoadingAst || getElementTypeForStubBuilder() == null) {
            return null;
        }
        ObjectStubTree<?> orBuild = StubTreeLoader.getInstance().readOrBuild(getProject(), getVirtualFile(), this);
        if (!(orBuild instanceof StubTree)) {
            return null;
        }
        FileViewProvider viewProvider = getViewProvider();
        List<Pair<IStubFileElementType, PsiFile>> stubbedRoots = StubTreeBuilder.getStubbedRoots(viewProvider);
        synchronized (this.myPsiLock) {
            if (getTreeElement() != null) {
                return null;
            }
            StubTree stubTreeDerefStub2 = derefStub();
            if (stubTreeDerefStub2 != null) {
                return stubTreeDerefStub2;
            }
            PsiFileStubImpl psiFileStubImpl = (PsiFileStubImpl) ((StubTree) orBuild).getRoot();
            if (!psiFileStubImpl.rootsAreSet()) {
                LOG.error("Stub roots must be set when stub tree was read or built with StubTreeLoader");
                return null;
            }
            PsiFileStub[] stubRoots = psiFileStubImpl.getStubRoots();
            if (stubRoots.length != stubbedRoots.size()) {
                LOG.error("readOrBuilt roots = " + StringUtil.join(stubRoots, new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return PsiFileImpl.lambda$getStubTree$2((PsiFileStub) obj);
                    }
                }, ", ") + "; " + StubTreeLoader.getFileViewProviderMismatchDiagnostics(viewProvider));
                rebuildStub();
                return null;
            }
            for (int i = 0; i < stubbedRoots.size(); i++) {
                PsiFileImpl psiFileImpl = (PsiFileImpl) stubbedRoots.get(i).second;
                if (psiFileImpl.derefStub() == null) {
                    StubTree stubTree2 = psiFileImpl.setStubTree(stubRoots[i]);
                    if (psiFileImpl == this) {
                        stubTree = stubTree2;
                    }
                }
            }
            return stubTree;
        }
    }

    static /* synthetic */ String lambda$getStubTree$2(PsiFileStub psiFileStub) {
        return "{" + psiFileStub.getClass().getSimpleName() + " " + psiFileStub.getType().getLanguage() + "}";
    }

    /* JADX WARN: Multi-variable type inference failed */
    private StubTree setStubTree(PsiFileStub<?> psiFileStub) {
        ((StubBase) psiFileStub).setPsi(this);
        StubTree stubTree = new StubTree(psiFileStub);
        FileElement treeElement = getTreeElement();
        stubTree.setDebugInfo("created in getStubTree(), with AST = " + (treeElement != null));
        updateTrees(this.myTrees.withStub(stubTree, treeElement));
        return stubTree;
    }

    public final StubTree derefStub() {
        return this.myTrees.derefStub();
    }

    private void updateTrees(FileTrees fileTrees) {
        if (fileTrees == null) {
            $$$reportNull$$$0(21);
        }
        this.myTrees = fileTrees;
    }

    protected PsiFileImpl cloneImpl(FileElement fileElement) {
        PsiFileImpl psiFileImpl = (PsiFileImpl) super.clone();
        psiFileImpl.setTreeElementPointer(fileElement);
        fileElement.setPsi(psiFileImpl);
        return psiFileImpl;
    }

    private boolean isKeepTreeElementByHardReference() {
        return !getViewProvider().isEventSystemEnabled();
    }

    private Supplier<FileElement> createTreeElementPointer(final FileElement fileElement) {
        if (fileElement == null) {
            $$$reportNull$$$0(22);
        }
        if (isKeepTreeElementByHardReference()) {
            return new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return PsiFileImpl.lambda$createTreeElementPointer$3(fileElement);
                }
            };
        }
        if (this.myManager.isBatchFilesProcessingMode()) {
            return new PatchedWeakReference(fileElement);
        }
        return new SoftReference(fileElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final PsiManager getManager() {
        return this.myManager;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getOriginalElement() {
        return getOriginalFile();
    }

    public final FileElement calcTreeElement() {
        FileElement treeElement = getTreeElement();
        return treeElement != null ? treeElement : loadTreeElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] childrenAsPsiElements = calcTreeElement().getChildrenAsPsiElements((TokenSet) null, PsiElement.ARRAY_FACTORY);
        if (childrenAsPsiElements == null) {
            $$$reportNull$$$0(24);
        }
        return childrenAsPsiElements;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getFirstChild() {
        return SharedImplUtil.getFirstChild(getNode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getLastChild() {
        return SharedImplUtil.getLastChild(getNode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void acceptChildren(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(25);
        }
        SharedImplUtil.acceptChildren(psiElementVisitor, getNode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(26);
        }
        return calcTreeElement().textMatches(charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(27);
        }
        return calcTreeElement().textMatches(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textContains(char c) {
        return calcTreeElement().textContains(c);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final PsiElement copy() {
        return clone();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(28);
        }
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        calcTreeElement().addInternal(treeElementCopyToElement, treeElementCopyToElement, null, null);
        return SourceTreeToPsiMap.treeElementToPsi(ChangeUtil.decodeInformation(treeElementCopyToElement));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(29);
        }
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        calcTreeElement().addInternal(treeElementCopyToElement, treeElementCopyToElement, SourceTreeToPsiMap.psiElementToTree(psiElement2), Boolean.TRUE);
        return SourceTreeToPsiMap.treeElementToPsi(ChangeUtil.decodeInformation(treeElementCopyToElement));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(30);
        }
        CheckUtil.checkWritable(this);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement);
        calcTreeElement().addInternal(treeElementCopyToElement, treeElementCopyToElement, SourceTreeToPsiMap.psiElementToTree(psiElement2), Boolean.FALSE);
        return SourceTreeToPsiMap.treeElementToPsi(ChangeUtil.decodeInformation(treeElementCopyToElement));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void checkAdd(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(31);
        }
        CheckUtil.checkWritable(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        return SharedImplUtil.addRange(this, psiElement, psiElement2, null, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRangeBefore(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(32);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(33);
        }
        return SharedImplUtil.addRange(this, psiElement, psiElement2, SourceTreeToPsiMap.psiElementToTree(psiElement3), Boolean.TRUE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addRangeAfter(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) throws IncorrectOperationException {
        return SharedImplUtil.addRange(this, psiElement, psiElement2, SourceTreeToPsiMap.psiElementToTree(psiElement3), Boolean.FALSE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void deleteChildRange(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        if (psiElement == null) {
            LOG.assertTrue(psiElement2 == null);
            return;
        }
        ASTNode aSTNodePsiElementToTree = SourceTreeToPsiMap.psiElementToTree(psiElement);
        ASTNode aSTNodePsiElementToTree2 = SourceTreeToPsiMap.psiElementToTree(psiElement2);
        FileElement fileElementCalcTreeElement = calcTreeElement();
        Logger logger = LOG;
        logger.assertTrue(aSTNodePsiElementToTree.getTreeParent() == fileElementCalcTreeElement);
        logger.assertTrue(aSTNodePsiElementToTree2.getTreeParent() == fileElementCalcTreeElement);
        CodeEditUtil.removeChildren(fileElementCalcTreeElement, aSTNodePsiElementToTree, aSTNodePsiElementToTree2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(34);
        }
        return SharedImplUtil.doReplace(this, calcTreeElement(), psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference[] getReferences() {
        PsiReference[] references = SharedPsiElementImplUtil.getReferences(this);
        if (references == null) {
            $$$reportNull$$$0(35);
        }
        return references;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope elementResolveScope = ResolveScopeManager.getElementResolveScope(this);
        if (elementResolveScope == null) {
            $$$reportNull$$$0(39);
        }
        return elementResolveScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        GlobalSearchScope elementUseScope = ResolveScopeManager.getElementUseScope(this);
        if (elementUseScope == null) {
            $$$reportNull$$$0(40);
        }
        return elementUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return new ItemPresentation() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl.1
            public String getPresentableText() {
                return PsiFileImpl.this.getName();
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public String getLocationString() {
                VirtualFile parent = PsiFileImpl.this.getViewProvider().getVirtualFile().getParent();
                if (parent != null && parent.isValid() && parent.isDirectory()) {
                    return parent.getPresentableUrl();
                }
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public Icon getIcon(boolean z) {
                return PsiFileImpl.this.getIcon(0);
            }
        };
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public void navigate(boolean z) {
        PsiNavigationSupport.getInstance().getDescriptor(this).navigate(z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public boolean canNavigate() {
        return PsiNavigationSupport.getInstance().canNavigate(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public boolean canNavigateToSource() {
        return canNavigate();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final Project getProject() {
        Project project = getManager().getProject();
        if (project == null) {
            $$$reportNull$$$0(41);
        }
        return project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public FileASTNode getNode() {
        return calcTreeElement();
    }

    public FileASTNode getNodeIfLoaded() {
        return getTreeElement();
    }

    public final StubTree getGreenStubTree() {
        StubTree stubTreeDerefStub = derefStub();
        return stubTreeDerefStub != null ? stubTreeDerefStub : getStubTree();
    }

    public StubTree calcStubTree() {
        StubTree stubTreeDerefStub;
        StubTree stubTreeDerefStub2 = derefStub();
        if (stubTreeDerefStub2 != null) {
            if (stubTreeDerefStub2 == null) {
                $$$reportNull$$$0(42);
            }
            return stubTreeDerefStub2;
        }
        FileElement fileElementCalcTreeElement = calcTreeElement();
        synchronized (this.myPsiLock) {
            stubTreeDerefStub = derefStub();
            if (stubTreeDerefStub == null) {
                assertReadAccessAllowed();
                IStubFileElementType<?> elementTypeForStubBuilder = getElementTypeForStubBuilder();
                if (elementTypeForStubBuilder == null) {
                    VirtualFile virtualFile = getVirtualFile();
                    String str = "ContentElementType: " + getContentElementType() + "; file: " + this + (virtualFile.isValid() ? "" : " (" + virtualFile + " invalid)") + "\n\tBoolean.TRUE.equals(getUserData(BUILDING_STUB)) = " + Boolean.TRUE.equals(getUserData(BUILDING_STUB)) + "\n\tgetTreeElement() = " + getTreeElement() + "\n\tvFile instanceof VirtualFileWithId = " + (virtualFile instanceof VirtualFileWithId) + "\n\tStubUpdatingIndex.canHaveStub(vFile) = " + StubTreeLoader.getInstance().canHaveStub(virtualFile);
                    rebuildStub();
                    throw new AssertionError(str);
                }
                StubElement stubElementBuildStubTree = elementTypeForStubBuilder.getBuilder().buildStubTree(this);
                if (stubElementBuildStubTree == null) {
                    throw new AssertionError("Stub tree wasn't built for " + elementTypeForStubBuilder + "; file: " + this);
                }
                stubTreeDerefStub = new StubTree((PsiFileStub) stubElementBuildStubTree);
                stubTreeDerefStub.setDebugInfo("created in calcStubTree");
                updateTrees(this.myTrees.withStub(stubTreeDerefStub, fileElementCalcTreeElement));
            }
        }
        if (stubTreeDerefStub == null) {
            $$$reportNull$$$0(43);
        }
        return stubTreeDerefStub;
    }

    final void rebuildStub() {
        AppUIExecutor.onWriteThread(ModalityState.NON_MODAL).later().submit(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6652x84adcb70();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$rebuildStub$4$org-jetbrains-kotlin-com-intellij-psi-impl-source-PsiFileImpl, reason: not valid java name */
    /* synthetic */ void m6652x84adcb70() {
        if (!this.myManager.isDisposed()) {
            this.myManager.dropPsiCaches();
        }
        VirtualFile virtualFile = getVirtualFile();
        if (virtualFile == null || !virtualFile.isValid()) {
            return;
        }
        Document cachedDocument = FileDocumentManager.getInstance().getCachedDocument(virtualFile);
        if (cachedDocument != null) {
            FileDocumentManager.getInstance().saveDocument(cachedDocument);
        }
        FileContentUtilCore.reparseFiles(virtualFile);
        StubTreeLoader.getInstance().rebuildStubTree(virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable
    public void putInfo(Map<? super String, ? super String> map) {
        if (map == null) {
            $$$reportNull$$$0(44);
        }
        putInfo(this, map);
    }

    public static void putInfo(PsiFile psiFile, Map<? super String, ? super String> map) {
        if (psiFile == null) {
            $$$reportNull$$$0(45);
        }
        if (map == null) {
            $$$reportNull$$$0(46);
        }
        map.put(PsiTreeChangeEvent.PROP_FILE_NAME, psiFile.getName());
        map.put("fileType", psiFile.getFileType().toString());
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return this.myElementType.toString();
    }

    public final void beforeAstChange() {
        checkWritable();
        synchronized (this.myPsiLock) {
            FileTrees fileTreesSwitchToStrongRefs = this.myTrees.switchToStrongRefs();
            if (fileTreesSwitchToStrongRefs != this.myTrees) {
                updateTrees(fileTreesSwitchToStrongRefs);
            }
        }
    }

    private void checkWritable() {
        PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(getProject());
        if (!(psiDocumentManager instanceof PsiDocumentManagerBase) || ((PsiDocumentManagerBase) psiDocumentManager).isCommitInProgress() || (this.myViewProvider instanceof FreeThreadedFileViewProvider)) {
            return;
        }
        CheckUtil.checkWritable(this);
    }
}
