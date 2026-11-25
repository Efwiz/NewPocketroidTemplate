package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Set;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase;
import org.jetbrains.kotlin.com.intellij.injected.editor.VirtualFileWindow;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.FreeThreadedFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.Identikit;
import org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SelfElementInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerAnchorProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileWithStubSupport;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiAnchor {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiAnchor.class);

    /* JADX WARN: Code duplicated, block: B:23:0x003e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 9) ? 2 : 3];
        if (i != 1 && i != 3) {
            switch (i) {
                case 6:
                    objArr[0] = "containingFile";
                    break;
                case 7:
                    objArr[0] = "file";
                    break;
                case 8:
                    objArr[0] = "psi";
                    break;
                case 9:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnchor";
                    break;
                case 10:
                    objArr[0] = "elementType";
                    break;
                default:
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    break;
            }
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnchor";
        }
        if (i == 1) {
            objArr[1] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else if (i == 3) {
            objArr[1] = "doCreateAnchor";
        } else if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnchor";
        } else {
            objArr[1] = "retrieveOrThrow";
        }
        switch (i) {
            case 1:
            case 3:
            case 9:
                break;
            case 2:
                objArr[2] = "doCreateAnchor";
                break;
            case 4:
                objArr[2] = "wrapperOrHardReference";
                break;
            case 5:
            case 6:
                objArr[2] = "createStubReference";
                break;
            case 7:
                objArr[2] = "canHaveStub";
                break;
            case 8:
                objArr[2] = "calcStubIndex";
                break;
            case 10:
                objArr[2] = "restoreFromStubIndex";
                break;
            default:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract int getEndOffset();

    public abstract PsiFile getFile();

    public abstract int getStartOffset();

    public abstract PsiElement retrieve();

    public static PsiAnchor create(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        PsiUtilCore.ensureValid(psiElement);
        PsiAnchor psiAnchorDoCreateAnchor = doCreateAnchor(psiElement);
        if (ApplicationManager.getApplication().isUnitTestMode() && !ApplicationManagerEx.isInStressTest()) {
            PsiElement psiElementRetrieve = psiAnchorDoCreateAnchor.retrieve();
            if (!psiElement.equals(psiElementRetrieve)) {
                LOG.error("Cannot restore element " + psiElement + " of " + psiElement.getClass() + " from anchor " + psiAnchorDoCreateAnchor + ", getting " + psiElementRetrieve + " instead");
            }
        }
        if (psiAnchorDoCreateAnchor == null) {
            $$$reportNull$$$0(1);
        }
        return psiAnchorDoCreateAnchor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static PsiAnchor doCreateAnchor(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        Language language = null;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        if (psiElement instanceof PsiFile) {
            PsiFile psiFile = (PsiFile) psiElement;
            VirtualFile virtualFile = psiFile.getVirtualFile();
            if (virtualFile != null) {
                return new PsiFileReference(virtualFile, psiFile);
            }
            return new HardReference(psiElement);
        }
        if (psiElement instanceof PsiDirectory) {
            return new PsiDirectoryReference(((PsiDirectory) psiElement).getVirtualFile(), psiElement.getProject());
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile == null) {
            return new HardReference(psiElement);
        }
        VirtualFile virtualFile2 = containingFile.getVirtualFile();
        if (virtualFile2 == null || (virtualFile2 instanceof VirtualFileWindow)) {
            return new HardReference(psiElement);
        }
        StubIndexReference stubIndexReferenceCreateStubReference = createStubReference(psiElement, containingFile);
        if (stubIndexReferenceCreateStubReference != null) {
            if (stubIndexReferenceCreateStubReference == null) {
                $$$reportNull$$$0(3);
            }
            return stubIndexReferenceCreateStubReference;
        }
        if (!psiElement.getIsPhysical()) {
            return wrapperOrHardReference(psiElement);
        }
        TextRange textRange = psiElement.getTextRange();
        if (textRange == null) {
            return wrapperOrHardReference(psiElement);
        }
        FileViewProvider viewProvider = containingFile.getViewProvider();
        for (Language language2 : viewProvider.getLanguages()) {
            if (viewProvider.getPsi(language2) == containingFile) {
                language = language2;
                break;
            }
        }
        if (language == null) {
            return wrapperOrHardReference(psiElement);
        }
        return new TreeRangeReference(containingFile, textRange.getStartOffset(), textRange.getEndOffset(), Identikit.fromPsi(psiElement, language), virtualFile2);
    }

    private static PsiAnchor wrapperOrHardReference(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        for (SmartPointerAnchorProvider smartPointerAnchorProvider : SmartPointerAnchorProvider.EP_NAME.getExtensions()) {
            PsiElement anchor = smartPointerAnchorProvider.getAnchor(psiElement);
            if (anchor != null && anchor != psiElement) {
                PsiAnchor psiAnchorCreate = create(anchor);
                if (!(psiAnchorCreate instanceof HardReference)) {
                    return new WrappedElementAnchor(smartPointerAnchorProvider, psiAnchorCreate);
                }
            }
        }
        return new HardReference(psiElement);
    }

    public static StubIndexReference createStubReference(PsiElement psiElement, PsiFile psiFile) {
        int iCalcStubIndex;
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(6);
        }
        if (!(psiElement instanceof StubBasedPsiElement) || !psiElement.getIsPhysical()) {
            return null;
        }
        if (!(psiElement instanceof PsiCompiledElement) && !canHaveStub(psiFile)) {
            return null;
        }
        StubBasedPsiElement stubBasedPsiElement = (StubBasedPsiElement) psiElement;
        IStubElementType elementType = stubBasedPsiElement.getElementType();
        if ((stubBasedPsiElement.getStub() != null || elementType.shouldCreateStub(psiElement.getNode())) && (iCalcStubIndex = calcStubIndex(stubBasedPsiElement)) != -1) {
            return new StubIndexReference(psiFile, iCalcStubIndex, psiFile.getLanguage(), elementType);
        }
        return null;
    }

    private static boolean canHaveStub(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(7);
        }
        if (!(psiFile instanceof PsiFileImpl)) {
            return false;
        }
        VirtualFile virtualFile = psiFile.getVirtualFile();
        IStubFileElementType<?> elementTypeForStubBuilder = ((PsiFileImpl) psiFile).getElementTypeForStubBuilder();
        return (elementTypeForStubBuilder == null || virtualFile == null || !elementTypeForStubBuilder.shouldBuildStubFor(virtualFile)) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static int calcStubIndex(StubBasedPsiElement<?> stubBasedPsiElement) {
        if (stubBasedPsiElement == 0) {
            $$$reportNull$$$0(8);
        }
        if (stubBasedPsiElement instanceof PsiFile) {
            return 0;
        }
        StubElement greenStub = stubBasedPsiElement instanceof StubBasedPsiElementBase ? ((StubBasedPsiElementBase) stubBasedPsiElement).getGreenStub() : stubBasedPsiElement.getStub();
        if (greenStub != null) {
            return ((StubBase) greenStub).getStubId();
        }
        return ((PsiFileImpl) stubBasedPsiElement.getContainingFile()).calcTreeElement().getStubbedSpine().getStubIndex(stubBasedPsiElement);
    }

    public PsiElement retrieveOrThrow() {
        String strDiagnoseNull;
        PsiElement psiElementRetrieve = retrieve();
        if (psiElementRetrieve != null) {
            if (psiElementRetrieve == null) {
                $$$reportNull$$$0(9);
            }
            return psiElementRetrieve;
        }
        if (this instanceof StubIndexReference) {
            strDiagnoseNull = ((StubIndexReference) this).diagnoseNull();
        } else {
            strDiagnoseNull = "Anchor hasn't survived: " + this;
        }
        throw new PsiInvalidElementAccessException((PsiElement) null, strDiagnoseNull);
    }

    private static final class TreeRangeReference extends PsiAnchor {
        private final int myEndOffset;
        private final Identikit myInfo;
        private final Project myProject;
        private final int myStartOffset;
        private final VirtualFile myVirtualFile;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = CompilerOptions.INFO;
            } else if (i != 2) {
                objArr[0] = "file";
            } else {
                objArr[0] = "virtualFile";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnchor$TreeRangeReference";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private TreeRangeReference(PsiFile psiFile, int i, int i2, Identikit identikit, VirtualFile virtualFile) {
            if (psiFile == null) {
                $$$reportNull$$$0(0);
            }
            if (identikit == null) {
                $$$reportNull$$$0(1);
            }
            if (virtualFile == null) {
                $$$reportNull$$$0(2);
            }
            this.myVirtualFile = virtualFile;
            this.myProject = psiFile.getProject();
            this.myStartOffset = i;
            this.myEndOffset = i2;
            this.myInfo = identikit;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public PsiElement retrieve() {
            PsiFile file = getFile();
            if (file == null || !file.isValid()) {
                return null;
            }
            return this.myInfo.findPsiElement(file, this.myStartOffset, this.myEndOffset);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public PsiFile getFile() {
            Language fileLanguage = this.myInfo.getFileLanguage();
            if (fileLanguage == null) {
                return null;
            }
            return SelfElementInfo.restoreFileFromVirtual(this.myVirtualFile, this.myProject, fileLanguage);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public int getStartOffset() {
            return this.myStartOffset;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public int getEndOffset() {
            return this.myEndOffset;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TreeRangeReference)) {
                return false;
            }
            TreeRangeReference treeRangeReference = (TreeRangeReference) obj;
            return this.myEndOffset == treeRangeReference.myEndOffset && this.myStartOffset == treeRangeReference.myStartOffset && this.myInfo.equals(treeRangeReference.myInfo) && this.myVirtualFile.equals(treeRangeReference.myVirtualFile);
        }

        public int hashCode() {
            return (((((this.myInfo.hashCode() * 31) + this.myStartOffset) * 31) + this.myEndOffset) * 31) + this.myVirtualFile.hashCode();
        }
    }

    public static class HardReference extends PsiAnchor {
        private final PsiElement myElement;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/PsiAnchor$HardReference", "<init>"));
        }

        public HardReference(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(0);
            }
            this.myElement = psiElement;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public PsiElement retrieve() {
            if (this.myElement.isValid()) {
                return this.myElement;
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public PsiFile getFile() {
            return this.myElement.getContainingFile();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public int getStartOffset() {
            return this.myElement.getTextRange().getStartOffset();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public int getEndOffset() {
            return this.myElement.getTextRange().getEndOffset();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof HardReference) {
                return this.myElement.equals(((HardReference) obj).myElement);
            }
            return false;
        }

        public int hashCode() {
            return this.myElement.hashCode();
        }
    }

    private static final class PsiFileReference extends PsiAnchor {
        private final VirtualFile myFile;
        private final Language myLanguage;
        private final Project myProject;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "psiFile";
            } else if (i != 3) {
                objArr[0] = "file";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnchor$PsiFileReference";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnchor$PsiFileReference";
            } else {
                objArr[1] = "findLanguage";
            }
            if (i == 2) {
                objArr[2] = "findLanguage";
            } else if (i != 3) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public int getStartOffset() {
            return 0;
        }

        private PsiFileReference(VirtualFile virtualFile, PsiFile psiFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(0);
            }
            if (psiFile == null) {
                $$$reportNull$$$0(1);
            }
            this.myFile = virtualFile;
            this.myProject = psiFile.getProject();
            this.myLanguage = findLanguage(psiFile);
        }

        private static Language findLanguage(PsiFile psiFile) {
            if (psiFile == null) {
                $$$reportNull$$$0(2);
            }
            FileViewProvider viewProvider = psiFile.getViewProvider();
            Set<Language> languages = viewProvider.getLanguages();
            for (Language language : languages) {
                if (psiFile.equals(viewProvider.getPsi(language))) {
                    if (language == null) {
                        $$$reportNull$$$0(3);
                    }
                    return language;
                }
            }
            throw new AssertionError("Non-retrievable file: " + psiFile.getClass() + "; " + psiFile.getLanguage() + "; " + languages);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public PsiElement retrieve() {
            return getFile();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public PsiFile getFile() {
            return SelfElementInfo.restoreFileFromVirtual(this.myFile, this.myProject, this.myLanguage);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public int getEndOffset() {
            return (int) this.myFile.getLength();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PsiFileReference)) {
                return false;
            }
            PsiFileReference psiFileReference = (PsiFileReference) obj;
            return this.myFile.equals(psiFileReference.myFile) && this.myLanguage.equals(psiFileReference.myLanguage) && this.myProject.equals(psiFileReference.myProject);
        }

        public int hashCode() {
            return (this.myFile.hashCode() * 31) + this.myLanguage.hashCode();
        }
    }

    private static final class PsiDirectoryReference extends PsiAnchor {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final VirtualFile myFile;
        private final Project myProject;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "file";
            } else {
                objArr[0] = "project";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnchor$PsiDirectoryReference";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public int getEndOffset() {
            return -1;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public PsiFile getFile() {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public int getStartOffset() {
            return 0;
        }

        private PsiDirectoryReference(VirtualFile virtualFile, Project project) {
            if (virtualFile == null) {
                $$$reportNull$$$0(0);
            }
            if (project == null) {
                $$$reportNull$$$0(1);
            }
            this.myFile = virtualFile;
            this.myProject = project;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public PsiElement retrieve() {
            return SelfElementInfo.restoreDirectoryFromVirtual(this.myFile, this.myProject);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PsiDirectoryReference)) {
                return false;
            }
            PsiDirectoryReference psiDirectoryReference = (PsiDirectoryReference) obj;
            return this.myFile.equals(psiDirectoryReference.myFile) && this.myProject.equals(psiDirectoryReference.myProject);
        }

        public int hashCode() {
            return this.myFile.hashCode();
        }
    }

    public static PsiElement restoreFromStubIndex(PsiFileWithStubSupport psiFileWithStubSupport, int i, IStubElementType<?, ?> iStubElementType, boolean z) {
        if (iStubElementType == null) {
            $$$reportNull$$$0(10);
        }
        if (psiFileWithStubSupport == null) {
            if (z) {
                throw new AssertionError("Null file");
            }
            return null;
        }
        if (i == 0) {
            return psiFileWithStubSupport;
        }
        StubbedSpine stubbedSpine = psiFileWithStubSupport.getStubbedSpine();
        StubBasedPsiElement stubBasedPsiElement = (StubBasedPsiElement) stubbedSpine.getStubPsi(i);
        if (stubBasedPsiElement == null) {
            if (z) {
                throw new AssertionError("Too large index: " + i + ">=" + stubbedSpine.getStubCount());
            }
            return null;
        }
        if (stubBasedPsiElement.getElementType() == iStubElementType) {
            return stubBasedPsiElement;
        }
        if (z) {
            throw new AssertionError("Element type mismatch: " + stubBasedPsiElement.getElementType() + "!=" + iStubElementType);
        }
        return null;
    }

    public static final class StubIndexReference extends PsiAnchor {
        private final IStubElementType<?, ?> myElementType;
        private final int myIndex;
        private final Language myLanguage;
        private final Project myProject;
        private final VirtualFile myVirtualFile;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? 2 : 3];
            switch (i) {
                case 1:
                    objArr[0] = "language";
                    break;
                case 2:
                    objArr[0] = "elementType";
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnchor$StubIndexReference";
                    break;
                default:
                    objArr[0] = "file";
                    break;
            }
            if (i == 3 || i == 4) {
                objArr[1] = "diagnoseNull";
            } else if (i == 5) {
                objArr[1] = "getTextRange";
            } else if (i == 6) {
                objArr[1] = "getVirtualFile";
            } else if (i != 7) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiAnchor$StubIndexReference";
            } else {
                objArr[1] = "getProject";
            }
            if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        private StubIndexReference(PsiFile psiFile, int i, Language language, IStubElementType<?, ?> iStubElementType) {
            if (psiFile == null) {
                $$$reportNull$$$0(0);
            }
            if (language == null) {
                $$$reportNull$$$0(1);
            }
            if (iStubElementType == null) {
                $$$reportNull$$$0(2);
            }
            this.myLanguage = language;
            this.myElementType = iStubElementType;
            this.myVirtualFile = psiFile.getVirtualFile();
            if (psiFile.getViewProvider() instanceof FreeThreadedFileViewProvider) {
                throw new IllegalArgumentException("Must not use StubIndexReference for injected file; take a closer look at HardReference instead");
            }
            this.myProject = psiFile.getProject();
            this.myIndex = i;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public PsiFile getFile() {
            if (this.myProject.isDisposed() || !this.myVirtualFile.isValid()) {
                return null;
            }
            FileViewProvider fileViewProviderFindViewProvider = PsiManager.getInstance(this.myProject).findViewProvider(this.myVirtualFile);
            PsiFile psi = fileViewProviderFindViewProvider == null ? null : fileViewProviderFindViewProvider.getPsi(this.myLanguage);
            if (psi instanceof PsiFileWithStubSupport) {
                return psi;
            }
            return null;
        }

        /* JADX INFO: renamed from: lambda$retrieve$0$org-jetbrains-kotlin-com-intellij-psi-PsiAnchor$StubIndexReference, reason: not valid java name */
        /* synthetic */ PsiElement m6525x3d680294() throws RuntimeException {
            return restoreFromStubIndex((PsiFileWithStubSupport) getFile(), this.myIndex, this.myElementType, false);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public PsiElement retrieve() {
            return (PsiElement) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiAnchor$StubIndexReference$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                public final Object compute() {
                    return this.f$0.m6525x3d680294();
                }
            });
        }

        public String diagnoseNull() {
            final PsiFile psiFile = (PsiFile) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiAnchor$StubIndexReference$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                public final Object compute() {
                    return this.f$0.getFile();
                }
            });
            try {
                PsiElement psiElement = (PsiElement) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiAnchor$StubIndexReference$$ExternalSyntheticLambda2
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                    public final Object compute() {
                        return this.f$0.m6524x3430d6ea(psiFile);
                    }
                });
                String str = "No diagnostics, element=" + psiElement + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + (psiElement == null ? 0 : System.identityHashCode(psiElement));
                if (str == null) {
                    $$$reportNull$$$0(3);
                }
                return str;
            } catch (AssertionError e) {
                String str2 = e.getMessage() + (psiFile == null ? "\n no PSI file" : "\n current file stamp=" + ((int) ((short) psiFile.getModificationStamp())));
                Document cachedDocument = FileDocumentManager.getInstance().getCachedDocument(this.myVirtualFile);
                if (cachedDocument != null) {
                    str2 = (str2 + "\n committed=" + PsiDocumentManager.getInstance(this.myProject).isCommitted(cachedDocument)) + "\n saved=" + (!FileDocumentManager.getInstance().isDocumentUnsaved(cachedDocument));
                }
                if (str2 == null) {
                    $$$reportNull$$$0(4);
                }
                return str2;
            }
        }

        /* JADX INFO: renamed from: lambda$diagnoseNull$1$org-jetbrains-kotlin-com-intellij-psi-PsiAnchor$StubIndexReference, reason: not valid java name */
        /* synthetic */ PsiElement m6524x3430d6ea(PsiFile psiFile) throws RuntimeException {
            return restoreFromStubIndex((PsiFileWithStubSupport) psiFile, this.myIndex, this.myElementType, true);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StubIndexReference)) {
                return false;
            }
            StubIndexReference stubIndexReference = (StubIndexReference) obj;
            return this.myIndex == stubIndexReference.myIndex && this.myVirtualFile.equals(stubIndexReference.myVirtualFile) && Comparing.equal(this.myElementType, stubIndexReference.myElementType) && this.myLanguage == stubIndexReference.myLanguage;
        }

        public int hashCode() {
            return (((((this.myVirtualFile.hashCode() * 31) + this.myIndex) * 31) + this.myElementType.hashCode()) * 31) + this.myLanguage.hashCode();
        }

        public String toString() {
            return "StubIndexReference{myVirtualFile=" + this.myVirtualFile + ", myProject=" + this.myProject + ", myIndex=" + this.myIndex + ", myLanguage=" + this.myLanguage + ", myElementType=" + this.myElementType + '}';
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public int getStartOffset() {
            return getTextRange().getStartOffset();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
        public int getEndOffset() {
            return getTextRange().getEndOffset();
        }

        private TextRange getTextRange() {
            PsiElement psiElementRetrieve = retrieve();
            if (psiElementRetrieve == null) {
                throw new PsiInvalidElementAccessException((PsiElement) null, "Element type: " + this.myElementType + "; " + this.myVirtualFile);
            }
            TextRange textRange = psiElementRetrieve.getTextRange();
            if (textRange == null) {
                $$$reportNull$$$0(5);
            }
            return textRange;
        }

        public VirtualFile getVirtualFile() {
            VirtualFile virtualFile = this.myVirtualFile;
            if (virtualFile == null) {
                $$$reportNull$$$0(6);
            }
            return virtualFile;
        }

        public Project getProject() {
            Project project = this.myProject;
            if (project == null) {
                $$$reportNull$$$0(7);
            }
            return project;
        }
    }
}
