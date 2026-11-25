package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.FrozenDocument;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.ProperTextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.util.UnfairTextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;

/* JADX INFO: loaded from: classes3.dex */
public class SelfElementInfo extends SmartPointerElementInfo {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final FileDocumentManager ourFileDocManager = FileDocumentManager.getInstance();
    private int myEndOffset;
    private final VirtualFile myFile;
    private final boolean myForInjected;
    private volatile Identikit myIdentikit;
    private int myStartOffset;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 17 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 17 ? 3 : 2];
        switch (i) {
            case 1:
            case 19:
                objArr[0] = "containingFile";
                break;
            case 2:
            case 3:
            case 4:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 5:
                objArr[0] = "pointerRange";
                break;
            case 6:
            case 7:
            case 8:
            case 16:
            case 18:
                objArr[0] = "manager";
                break;
            case 9:
            case 12:
            case 14:
                objArr[0] = "virtualFile";
                break;
            case 10:
            case 13:
                objArr[0] = "project";
                break;
            case 11:
                objArr[0] = "language";
                break;
            case 15:
                objArr[0] = "other";
                break;
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SelfElementInfo";
                break;
            case 20:
                objArr[0] = "document";
                break;
            case 21:
                objArr[0] = "segment";
                break;
            default:
                objArr[0] = "identikit";
                break;
        }
        if (i != 17) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SelfElementInfo";
        } else {
            objArr[1] = "getVirtualFile";
        }
        switch (i) {
            case 2:
                objArr[2] = "switchToAnchor";
                break;
            case 3:
                objArr[2] = "findAnchor";
                break;
            case 4:
                objArr[2] = "switchTo";
                break;
            case 5:
                objArr[2] = "updateRangeToPsi";
                break;
            case 6:
                objArr[2] = "restoreElement";
                break;
            case 7:
                objArr[2] = "getPsiRange";
                break;
            case 8:
                objArr[2] = "restoreFile";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "restoreFileFromVirtual";
                break;
            case 12:
            case 13:
                objArr[2] = "restoreDirectoryFromVirtual";
                break;
            case 14:
                objArr[2] = "restoreVFile";
                break;
            case 15:
            case 16:
                objArr[2] = "pointsToTheSameElementAs";
                break;
            case 17:
                break;
            case 18:
                objArr[2] = "getRange";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "calcActualRangeAfterDocumentEvents";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 17) {
            throw new IllegalStateException(str2);
        }
    }

    SelfElementInfo(ProperTextRange properTextRange, Identikit identikit, PsiFile psiFile, boolean z) {
        if (identikit == null) {
            $$$reportNull$$$0(0);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        this.myForInjected = z;
        this.myIdentikit = identikit;
        this.myFile = psiFile.getViewProvider().getVirtualFile();
        setRange(properTextRange);
    }

    void switchToAnchor(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        switchTo(psiElement, findAnchor(psiElement));
    }

    private Pair<Identikit.ByAnchor, PsiElement> findAnchor(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        Language fileLanguage = this.myIdentikit.getFileLanguage();
        if (fileLanguage == null) {
            return null;
        }
        return Identikit.withAnchor(psiElement, fileLanguage);
    }

    private void switchTo(PsiElement psiElement, Pair<Identikit.ByAnchor, PsiElement> pair) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        if (pair != null) {
            this.myIdentikit = pair.first;
            setRange(pair.second.getTextRange());
        } else {
            setRange(psiElement.getTextRange());
        }
    }

    boolean updateRangeToPsi(Segment segment, PsiElement psiElement) {
        if (segment == null) {
            $$$reportNull$$$0(5);
        }
        Pair<Identikit.ByAnchor, PsiElement> pairFindAnchor = findAnchor(psiElement);
        TextRange textRange = (pairFindAnchor != null ? pairFindAnchor.second : psiElement).getTextRange();
        if (textRange == null || !textRange.intersects(segment)) {
            return false;
        }
        switchTo(psiElement, pairFindAnchor);
        return true;
    }

    void setRange(Segment segment) {
        if (segment == null) {
            this.myStartOffset = -1;
            this.myEndOffset = -1;
        } else {
            this.myStartOffset = segment.getStartOffset();
            this.myEndOffset = segment.getEndOffset();
        }
    }

    boolean hasRange() {
        return this.myStartOffset >= 0;
    }

    int getPsiStartOffset() {
        return this.myStartOffset;
    }

    int getPsiEndOffset() {
        return this.myEndOffset;
    }

    boolean isGreedy() {
        return this.myForInjected || this.myIdentikit.isForPsiFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Document getDocumentToSynchronize() {
        return ourFileDocManager.getCachedDocument(getVirtualFile());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiElement restoreElement(SmartPointerManagerImpl smartPointerManagerImpl) {
        PsiFile psiFileRestoreFile;
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(6);
        }
        TextRange psiRange = getPsiRange(smartPointerManagerImpl);
        if (psiRange == null || (psiFileRestoreFile = restoreFile(smartPointerManagerImpl)) == null || !psiFileRestoreFile.isValid()) {
            return null;
        }
        return this.myIdentikit.findPsiElement(psiFileRestoreFile, psiRange.getStartOffset(), psiRange.getEndOffset());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    public TextRange getPsiRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(7);
        }
        return calcPsiRange();
    }

    boolean isForInjected() {
        return this.myForInjected;
    }

    private TextRange calcPsiRange() {
        if (hasRange()) {
            return new UnfairTextRange(this.myStartOffset, this.myEndOffset);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiFile restoreFile(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(8);
        }
        Language fileLanguage = this.myIdentikit.getFileLanguage();
        if (fileLanguage == null) {
            return null;
        }
        return restoreFileFromVirtual(getVirtualFile(), smartPointerManagerImpl.getProject(), fileLanguage);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    void cleanup() {
        setRange(null);
    }

    public static PsiFile restoreFileFromVirtual(final VirtualFile virtualFile, final Project project, final Language language) {
        if (virtualFile == null) {
            $$$reportNull$$$0(9);
        }
        if (project == null) {
            $$$reportNull$$$0(10);
        }
        if (language == null) {
            $$$reportNull$$$0(11);
        }
        return (PsiFile) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SelfElementInfo$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return SelfElementInfo.lambda$restoreFileFromVirtual$0(project, virtualFile, language);
            }
        });
    }

    static /* synthetic */ PsiFile lambda$restoreFileFromVirtual$0(Project project, VirtualFile virtualFile, Language language) throws RuntimeException {
        VirtualFile virtualFileRestoreVFile;
        PsiFile psiFileFindFile;
        if (project.isDisposed() || (virtualFileRestoreVFile = restoreVFile(virtualFile)) == null || !virtualFileRestoreVFile.isValid() || (psiFileFindFile = PsiManager.getInstance(project).findFile(virtualFileRestoreVFile)) == null) {
            return null;
        }
        FileViewProvider viewProvider = psiFileFindFile.getViewProvider();
        if (language == Language.ANY) {
            language = psiFileFindFile.getViewProvider().getBaseLanguage();
        }
        return viewProvider.getPsi(language);
    }

    public static PsiDirectory restoreDirectoryFromVirtual(final VirtualFile virtualFile, final Project project) {
        if (virtualFile == null) {
            $$$reportNull$$$0(12);
        }
        if (project == null) {
            $$$reportNull$$$0(13);
        }
        return (PsiDirectory) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SelfElementInfo$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return SelfElementInfo.lambda$restoreDirectoryFromVirtual$1(project, virtualFile);
            }
        });
    }

    static /* synthetic */ PsiDirectory lambda$restoreDirectoryFromVirtual$1(Project project, VirtualFile virtualFile) throws RuntimeException {
        VirtualFile virtualFileRestoreVFile;
        PsiDirectory psiDirectoryFindDirectory;
        if (project.isDisposed() || (virtualFileRestoreVFile = restoreVFile(virtualFile)) == null || !virtualFileRestoreVFile.isValid() || (psiDirectoryFindDirectory = PsiManager.getInstance(project).findDirectory(virtualFileRestoreVFile)) == null || !psiDirectoryFindDirectory.isValid()) {
            return null;
        }
        return psiDirectoryFindDirectory;
    }

    private static VirtualFile restoreVFile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(14);
        }
        if (virtualFile.isValid()) {
            return virtualFile;
        }
        VirtualFile parent = virtualFile.getParent();
        if (parent == null || !parent.isValid()) {
            return null;
        }
        return parent.findChild(virtualFile.getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    int elementHashCode() {
        return getVirtualFile().hashCode() + (this.myIdentikit.hashCode() * 31);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    boolean pointsToTheSameElementAs(SmartPointerElementInfo smartPointerElementInfo, final SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerElementInfo == null) {
            $$$reportNull$$$0(15);
        }
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(16);
        }
        if (smartPointerElementInfo instanceof SelfElementInfo) {
            final SelfElementInfo selfElementInfo = (SelfElementInfo) smartPointerElementInfo;
            if (getVirtualFile().equals(smartPointerElementInfo.getVirtualFile()) && this.myIdentikit == selfElementInfo.myIdentikit) {
                return ((Boolean) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SelfElementInfo$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                    public final Object compute() {
                        return this.f$0.m6631x67ec896c(smartPointerManagerImpl, selfElementInfo);
                    }
                })).booleanValue();
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: lambda$pointsToTheSameElementAs$2$org-jetbrains-kotlin-com-intellij-psi-impl-smartPointers-SelfElementInfo, reason: not valid java name */
    /* synthetic */ Boolean m6631x67ec896c(SmartPointerManagerImpl smartPointerManagerImpl, SelfElementInfo selfElementInfo) throws RuntimeException {
        TextRange psiRange = getPsiRange(smartPointerManagerImpl);
        TextRange psiRange2 = selfElementInfo.getPsiRange(smartPointerManagerImpl);
        return Boolean.valueOf(psiRange != null && psiRange2 != null && psiRange.getStartOffset() == psiRange2.getStartOffset() && psiRange.getEndOffset() == psiRange2.getEndOffset());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    final VirtualFile getVirtualFile() {
        VirtualFile virtualFile = this.myFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(17);
        }
        return virtualFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Segment getRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        Document documentToSynchronize;
        SmartPointerTracker tracker;
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(18);
        }
        if (hasRange() && (documentToSynchronize = getDocumentToSynchronize()) != null) {
            PsiDocumentManagerBase psiDocumentManager = smartPointerManagerImpl.getPsiDocumentManager();
            List<DocumentEvent> eventsSinceCommit = psiDocumentManager.getEventsSinceCommit(documentToSynchronize);
            if (!eventsSinceCommit.isEmpty() && (tracker = smartPointerManagerImpl.getTracker(getVirtualFile())) != null) {
                return tracker.getUpdatedRange(this, (FrozenDocument) psiDocumentManager.getLastCommittedDocument(documentToSynchronize), eventsSinceCommit);
            }
        }
        return calcPsiRange();
    }

    public String toString() {
        return "psi:range=" + calcPsiRange() + ",type=" + this.myIdentikit;
    }

    public static Segment calcActualRangeAfterDocumentEvents(PsiFile psiFile, Document document, Segment segment, boolean z) {
        SmartPointerTracker tracker;
        if (psiFile == null) {
            $$$reportNull$$$0(19);
        }
        if (document == null) {
            $$$reportNull$$$0(20);
        }
        if (segment == null) {
            $$$reportNull$$$0(21);
        }
        Project project = psiFile.getProject();
        PsiDocumentManagerBase psiDocumentManagerBase = (PsiDocumentManagerBase) PsiDocumentManager.getInstance(project);
        List<DocumentEvent> eventsSinceCommit = psiDocumentManagerBase.getEventsSinceCommit(document);
        if (eventsSinceCommit.isEmpty() || (tracker = ((SmartPointerManagerImpl) SmartPointerManager.getInstance(project)).getTracker(psiFile.getViewProvider().getVirtualFile())) == null) {
            return null;
        }
        return tracker.getUpdatedRange(psiFile, segment, z, (FrozenDocument) psiDocumentManagerBase.getLastCommittedDocument(document), eventsSinceCommit);
    }
}
