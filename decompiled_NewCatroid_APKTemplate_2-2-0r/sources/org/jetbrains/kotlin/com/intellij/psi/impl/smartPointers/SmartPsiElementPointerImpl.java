package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LanguageUtil;
import org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.ProperTextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnchor;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.FreeThreadedFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
class SmartPsiElementPointerImpl<E extends PsiElement> implements SmartPointerEx<E> {
    private static final Logger LOG = Logger.getInstance((Class<?>) SmartPsiElementPointerImpl.class);
    private Reference<E> myElement;
    private final SmartPointerElementInfo myElementInfo;
    protected final SmartPointerManagerImpl myManager;
    private byte myReferenceCount;
    SmartPointerTracker.PointerReference pointerReference;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 8 || i == 11 || i == 18) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 8 || i == 11 || i == 18) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 7:
            case 10:
            case 12:
            case 14:
            case 16:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
            case 6:
            default:
                objArr[0] = "manager";
                break;
            case 4:
                objArr[0] = "elementInfo";
                break;
            case 5:
            case 8:
            case 11:
            case 18:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartPsiElementPointerImpl";
                break;
            case 9:
                objArr[0] = "project";
                break;
            case 13:
            case 15:
            case 17:
                objArr[0] = "containingFile";
                break;
            case 19:
                objArr[0] = "pointer1";
                break;
            case 20:
                objArr[0] = "pointer2";
                break;
        }
        if (i == 5) {
            objArr[1] = "getProject";
        } else if (i == 8) {
            objArr[1] = "createElementInfo";
        } else if (i == 11) {
            objArr[1] = "doCreateElementInfo";
        } else if (i != 18) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartPsiElementPointerImpl";
        } else {
            objArr[1] = "getElementInfo";
        }
        switch (i) {
            case 5:
            case 8:
            case 11:
            case 18:
                break;
            case 6:
            case 7:
                objArr[2] = "createElementInfo";
                break;
            case 9:
            case 10:
                objArr[2] = "doCreateElementInfo";
                break;
            case 12:
            case 13:
                objArr[2] = "hasReliableRange";
                break;
            case 14:
            case 15:
                objArr[2] = "isFakePsiInNormalFile";
                break;
            case 16:
            case 17:
                objArr[2] = "createAnchorInfo";
                break;
            case 19:
            case 20:
                objArr[2] = "pointsToTheSameElementAs";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 8 && i != 11 && i != 18) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    SmartPsiElementPointerImpl(SmartPointerManagerImpl smartPointerManagerImpl, E e, PsiFile psiFile, boolean z) {
        this(smartPointerManagerImpl, e, createElementInfo(smartPointerManagerImpl, e, psiFile, z));
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(0);
        }
        if (e == null) {
            $$$reportNull$$$0(1);
        }
    }

    SmartPsiElementPointerImpl(SmartPointerManagerImpl smartPointerManagerImpl, E e, SmartPointerElementInfo smartPointerElementInfo) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(2);
        }
        if (e == null) {
            $$$reportNull$$$0(3);
        }
        if (smartPointerElementInfo == null) {
            $$$reportNull$$$0(4);
        }
        this.myReferenceCount = (byte) 1;
        ApplicationManager.getApplication().assertReadAccessAllowed();
        this.myElementInfo = smartPointerElementInfo;
        this.myManager = smartPointerManagerImpl;
        cacheElement(e);
    }

    public boolean equals(Object obj) {
        return (obj instanceof SmartPsiElementPointer) && pointsToTheSameElementAs(this, (SmartPsiElementPointer) obj);
    }

    public int hashCode() {
        return this.myElementInfo.elementHashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer
    public Project getProject() {
        Project project = this.myManager.getProject();
        if (project == null) {
            $$$reportNull$$$0(5);
        }
        return project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer
    public E getElement() {
        if (getProject().isDisposed()) {
            return null;
        }
        E e = (E) getCachedElement();
        if (e != null && e.isValid()) {
            return e;
        }
        E e2 = (E) doRestoreElement();
        cacheElement(e2);
        return e2;
    }

    E doRestoreElement() {
        E e = (E) this.myElementInfo.restoreElement(this.myManager);
        if (e == null || e.isValid()) {
            return e;
        }
        return null;
    }

    void cacheElement(E e) {
        Reference<E> weakReference;
        if (e == null) {
            weakReference = null;
        } else {
            weakReference = PsiManagerEx.getInstanceEx(getProject()).isBatchFilesProcessingMode() ? new WeakReference<>(e) : new SoftReference<>(e);
        }
        this.myElement = weakReference;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerEx
    public E getCachedElement() {
        return (E) org.jetbrains.kotlin.com.intellij.reference.SoftReference.dereference(this.myElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer
    public PsiFile getContainingFile() {
        PsiFile psiFileRestoreFile = getElementInfo().restoreFile(this.myManager);
        if (psiFileRestoreFile != null) {
            return psiFileRestoreFile;
        }
        Document documentToSynchronize = this.myElementInfo.getDocumentToSynchronize();
        if (documentToSynchronize == null) {
            PsiElement element = getElement();
            if (element == null) {
                return null;
            }
            return element.getContainingFile();
        }
        return PsiDocumentManager.getInstance(getProject()).getPsiFile(documentToSynchronize);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer
    public VirtualFile getVirtualFile() {
        return this.myElementInfo.getVirtualFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer
    public Segment getRange() {
        return this.myElementInfo.getRange(this.myManager);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer
    public Segment getPsiRange() {
        return this.myElementInfo.getPsiRange(this.myManager);
    }

    private static <E extends PsiElement> SmartPointerElementInfo createElementInfo(SmartPointerManagerImpl smartPointerManagerImpl, E e, PsiFile psiFile, boolean z) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(6);
        }
        if (e == null) {
            $$$reportNull$$$0(7);
        }
        SmartPointerElementInfo smartPointerElementInfoDoCreateElementInfo = doCreateElementInfo(smartPointerManagerImpl.getProject(), e, psiFile, z);
        if (ApplicationManager.getApplication().isUnitTestMode() && !ApplicationManagerEx.isInStressTest()) {
            PsiElement psiElementRestoreElement = smartPointerElementInfoDoCreateElementInfo.restoreElement(smartPointerManagerImpl);
            if (!e.equals(psiElementRestoreElement)) {
                LOG.error("Cannot restore " + e + " of " + e.getClass() + " from " + smartPointerElementInfoDoCreateElementInfo + "; restored=" + psiElementRestoreElement + (psiElementRestoreElement == null ? "" : " of " + psiElementRestoreElement.getClass()) + " in " + e.getProject());
            }
        }
        if (smartPointerElementInfoDoCreateElementInfo == null) {
            $$$reportNull$$$0(8);
        }
        return smartPointerElementInfoDoCreateElementInfo;
    }

    private static <E extends PsiElement> SmartPointerElementInfo doCreateElementInfo(Project project, E e, PsiFile psiFile, boolean z) {
        if (project == null) {
            $$$reportNull$$$0(9);
        }
        if (e == null) {
            $$$reportNull$$$0(10);
        }
        if (e instanceof PsiDirectory) {
            return new DirElementInfo((PsiDirectory) e);
        }
        boolean z2 = e instanceof PsiCompiledElement;
        if (z2 || psiFile == null) {
            if ((e instanceof StubBasedPsiElement) && z2) {
                if (e instanceof PsiFile) {
                    return new FileElementInfo((PsiFile) e);
                }
                PsiAnchor.StubIndexReference stubIndexReferenceCreateStubReference = PsiAnchor.createStubReference(e, psiFile);
                if (stubIndexReferenceCreateStubReference != null) {
                    return new ClsElementInfo(stubIndexReferenceCreateStubReference);
                }
            }
            return new HardElementInfo(e);
        }
        FileViewProvider viewProvider = psiFile.getViewProvider();
        if ((viewProvider instanceof FreeThreadedFileViewProvider) && hasReliableRange(e, psiFile)) {
            PsiLanguageInjectionHost injectionHost = InjectedLanguageManager.getInstance(psiFile.getProject()).getInjectionHost(psiFile);
            TextRange textRange = e.getTextRange();
            if (injectionHost != null && textRange != null) {
                return new InjectedSelfElementInfo(project, e, textRange, psiFile, SmartPointerManager.getInstance(project).createSmartPsiElementPointer(injectionHost));
            }
        }
        VirtualFile virtualFile = viewProvider.getVirtualFile();
        if (e instanceof PsiFile) {
            FileViewProvider fileViewProviderFindViewProvider = PsiManager.getInstance(project).findViewProvider(virtualFile);
            if (fileViewProviderFindViewProvider != null && fileViewProviderFindViewProvider.getPsi(LanguageUtil.getRootLanguage(e)) == e) {
                return new FileElementInfo((PsiFile) e);
            }
            return new HardElementInfo(e);
        }
        if (!hasReliableRange(e, psiFile)) {
            return new HardElementInfo(e);
        }
        Document cachedDocument = FileDocumentManager.getInstance().getCachedDocument(virtualFile);
        if (cachedDocument != null && ((PsiDocumentManagerBase) PsiDocumentManager.getInstance(project)).getSynchronizer().isDocumentAffectedByTransactions(cachedDocument)) {
            LOG.error("Smart pointers must not be created during PSI changes");
        }
        SmartPointerElementInfo smartPointerElementInfoCreateAnchorInfo = createAnchorInfo(e, psiFile);
        if (smartPointerElementInfoCreateAnchorInfo != null) {
            if (smartPointerElementInfoCreateAnchorInfo == null) {
                $$$reportNull$$$0(11);
            }
            return smartPointerElementInfoCreateAnchorInfo;
        }
        TextRange textRange2 = e.getTextRange();
        if (textRange2 == null) {
            return new HardElementInfo(e);
        }
        Identikit.ByType byTypeFromPsi = Identikit.fromPsi(e, LanguageUtil.getRootLanguage(e));
        if (textRange2.isEmpty() && byTypeFromPsi.findPsiElement(psiFile, textRange2.getStartOffset(), textRange2.getEndOffset()) != e) {
            return new HardElementInfo(e);
        }
        if (!psiFile.getIsPhysical() && cachedDocument == null) {
            return new HardElementInfo(e);
        }
        return new SelfElementInfo(ProperTextRange.create((Segment) textRange2), byTypeFromPsi, psiFile, z);
    }

    private static boolean hasReliableRange(PsiElement psiElement, PsiFile psiFile) {
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(13);
        }
        return ((psiElement instanceof ASTDelegatePsiElement) || (psiElement instanceof ASTNode)) && !isFakePsiInNormalFile(psiElement, psiFile);
    }

    private static boolean isFakePsiInNormalFile(PsiElement psiElement, PsiFile psiFile) {
        if (psiElement == null) {
            $$$reportNull$$$0(14);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(15);
        }
        if (psiElement.getIsPhysical()) {
            return false;
        }
        if (psiFile.getIsPhysical()) {
            return true;
        }
        TextRange textRange = psiElement.getTextRange();
        return textRange == null || PsiTreeUtil.findElementOfClassAtRange(psiFile, textRange.getStartOffset(), textRange.getEndOffset(), psiElement.getClass()) != psiElement;
    }

    private static SmartPointerElementInfo createAnchorInfo(PsiElement psiElement, PsiFile psiFile) {
        PsiFileImpl psiFileImpl;
        IStubFileElementType<?> elementTypeForStubBuilder;
        StubBasedPsiElement stubBasedPsiElement;
        int iCalcStubIndex;
        if (psiElement == null) {
            $$$reportNull$$$0(16);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(17);
        }
        if ((psiElement instanceof StubBasedPsiElement) && (psiFile instanceof PsiFileImpl) && (elementTypeForStubBuilder = (psiFileImpl = (PsiFileImpl) psiFile).getElementTypeForStubBuilder()) != null && elementTypeForStubBuilder.shouldBuildStubFor(psiFile.getViewProvider().getVirtualFile()) && (iCalcStubIndex = PsiAnchor.calcStubIndex((stubBasedPsiElement = (StubBasedPsiElement) psiElement))) != -1) {
            return new AnchorElementInfo(psiElement, psiFileImpl, iCalcStubIndex, stubBasedPsiElement.getElementType());
        }
        Pair<Identikit.ByAnchor, PsiElement> pairWithAnchor = Identikit.withAnchor(psiElement, LanguageUtil.getRootLanguage(psiFile));
        if (pairWithAnchor != null) {
            return new AnchorElementInfo(pairWithAnchor.second, psiFile, pairWithAnchor.first);
        }
        return null;
    }

    SmartPointerElementInfo getElementInfo() {
        SmartPointerElementInfo smartPointerElementInfo = this.myElementInfo;
        if (smartPointerElementInfo == null) {
            $$$reportNull$$$0(18);
        }
        return smartPointerElementInfo;
    }

    static boolean pointsToTheSameElementAs(SmartPsiElementPointer<?> smartPsiElementPointer, SmartPsiElementPointer<?> smartPsiElementPointer2) {
        if (smartPsiElementPointer == null) {
            $$$reportNull$$$0(19);
        }
        if (smartPsiElementPointer2 == null) {
            $$$reportNull$$$0(20);
        }
        if (smartPsiElementPointer == smartPsiElementPointer2) {
            return true;
        }
        ProgressManager.checkCanceled();
        if ((smartPsiElementPointer instanceof SmartPsiElementPointerImpl) && (smartPsiElementPointer2 instanceof SmartPsiElementPointerImpl)) {
            SmartPsiElementPointerImpl smartPsiElementPointerImpl = (SmartPsiElementPointerImpl) smartPsiElementPointer;
            SmartPsiElementPointerImpl smartPsiElementPointerImpl2 = (SmartPsiElementPointerImpl) smartPsiElementPointer2;
            if (!smartPsiElementPointerImpl.getElementInfo().pointsToTheSameElementAs(smartPsiElementPointerImpl2.getElementInfo(), smartPsiElementPointerImpl.myManager)) {
                return false;
            }
            PsiElement cachedElement = smartPsiElementPointerImpl.getCachedElement();
            PsiElement cachedElement2 = smartPsiElementPointerImpl2.getCachedElement();
            return cachedElement == null || cachedElement2 == null || cachedElement.equals(cachedElement2);
        }
        return Comparing.equal(smartPsiElementPointer.getElement(), smartPsiElementPointer2.getElement());
    }

    synchronized int incrementAndGetReferenceCount(int i) {
        byte b = this.myReferenceCount;
        if (b == 127) {
            return 127;
        }
        if (b == 0) {
            return -1;
        }
        byte b2 = (byte) (b + i);
        this.myReferenceCount = b2;
        return b2;
    }

    public String toString() {
        return this.myElementInfo.toString();
    }
}
