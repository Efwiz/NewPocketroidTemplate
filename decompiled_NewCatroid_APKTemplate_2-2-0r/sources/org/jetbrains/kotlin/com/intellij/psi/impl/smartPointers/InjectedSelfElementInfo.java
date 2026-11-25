package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.injected.editor.DocumentWindow;
import org.jetbrains.kotlin.com.intellij.injected.editor.VirtualFileWindow;
import org.jetbrains.kotlin.com.intellij.lang.LanguageUtil;
import org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.ProperTextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiFileRange;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
class InjectedSelfElementInfo extends SmartPointerElementInfo {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final AffixOffsets myAffixOffsets;
    private final SmartPsiElementPointer<PsiLanguageInjectionHost> myHostContext;
    private final SmartPsiFileRange myInjectedFileRangeInHostFile;
    private final Identikit myType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 15 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 15 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "injectedElement";
                break;
            case 2:
                objArr[0] = "injectedRange";
                break;
            case 3:
                objArr[0] = "containingFile";
                break;
            case 4:
            case 8:
                objArr[0] = "hostContext";
                break;
            case 5:
            case 6:
            case 7:
            case 12:
            case 13:
                objArr[0] = "manager";
                break;
            case 9:
                objArr[0] = "hostFile";
                break;
            case 10:
                objArr[0] = "rangeInHostFile";
                break;
            case 11:
                objArr[0] = "other";
                break;
            case 14:
                objArr[0] = "hostRange";
                break;
            case 15:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/InjectedSelfElementInfo";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i != 15) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/InjectedSelfElementInfo";
        } else {
            objArr[1] = "getProject";
        }
        switch (i) {
            case 5:
                objArr[2] = "getRange";
                break;
            case 6:
                objArr[2] = "getPsiRange";
                break;
            case 7:
                objArr[2] = "restoreElement";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "getInjectedFileIn";
                break;
            case 11:
            case 12:
                objArr[2] = "pointsToTheSameElementAs";
                break;
            case 13:
                objArr[2] = "restoreFile";
                break;
            case 14:
                objArr[2] = "hostToInjected";
                break;
            case 15:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 15) {
            throw new IllegalStateException(str2);
        }
    }

    InjectedSelfElementInfo(Project project, PsiElement psiElement, TextRange textRange, PsiFile psiFile, SmartPsiElementPointer<PsiLanguageInjectionHost> smartPsiElementPointer) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (textRange == null) {
            $$$reportNull$$$0(2);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(3);
        }
        if (smartPsiElementPointer == null) {
            $$$reportNull$$$0(4);
        }
        this.myHostContext = smartPsiElementPointer;
        InjectedLanguageManager injectedLanguageManager = InjectedLanguageManager.getInstance(project);
        this.myInjectedFileRangeInHostFile = SmartPointerManager.getInstance(project).createSmartPsiFileRangePointer(smartPsiElementPointer.getContainingFile(), injectedLanguageManager.injectedToHost(psiElement, textRange));
        this.myType = Identikit.fromPsi(psiElement, LanguageUtil.getRootLanguage(psiFile));
        List<TextRange> nonEditableFragments = injectedLanguageManager.getNonEditableFragments((DocumentWindow) psiFile.getViewProvider().getDocument());
        int i = -1;
        int i2 = -1;
        int startOffset = -1;
        int endOffset = -1;
        for (int i3 = 0; i3 < nonEditableFragments.size(); i3++) {
            TextRange textRange2 = nonEditableFragments.get(i3);
            if (textRange2.containsOffset(textRange.getStartOffset())) {
                startOffset = textRange.getStartOffset() - textRange2.getStartOffset();
                i = i3;
            }
            if (textRange2.containsOffset(textRange.getEndOffset())) {
                endOffset = textRange.getEndOffset() - textRange2.getStartOffset();
                i2 = i3;
            }
        }
        this.myAffixOffsets = (i >= 0 || i2 >= 0) ? new AffixOffsets(i, startOffset, i2, endOffset) : null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    VirtualFile getVirtualFile() {
        PsiElement psiElementRestoreElement = restoreElement((SmartPointerManagerImpl) SmartPointerManager.getInstance(getProject()));
        if (psiElementRestoreElement == null) {
            return null;
        }
        return psiElementRestoreElement.getContainingFile().getVirtualFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Segment getRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(5);
        }
        return getInjectedRange(false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Segment getPsiRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(6);
        }
        return getInjectedRange(true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiElement restoreElement(SmartPointerManagerImpl smartPointerManagerImpl) {
        PsiElement element;
        Segment psiRange;
        PsiFile injectedFileIn;
        ProperTextRange properTextRangeHostToInjected;
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(7);
        }
        PsiFile containingFile = this.myHostContext.getContainingFile();
        if (containingFile == null || !containingFile.isValid() || (element = this.myHostContext.getElement()) == null || (psiRange = this.myInjectedFileRangeInHostFile.getPsiRange()) == null || (properTextRangeHostToInjected = hostToInjected(true, psiRange, (injectedFileIn = getInjectedFileIn(element, containingFile, TextRange.create(psiRange))), this.myAffixOffsets)) == null) {
            return null;
        }
        return this.myType.findPsiElement(injectedFileIn, properTextRangeHostToInjected.getStartOffset(), properTextRangeHostToInjected.getEndOffset());
    }

    private PsiFile getInjectedFileIn(PsiElement psiElement, PsiFile psiFile, final TextRange textRange) {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(9);
        }
        if (textRange == null) {
            $$$reportNull$$$0(10);
        }
        final PsiDocumentManagerBase psiDocumentManagerBase = (PsiDocumentManagerBase) PsiDocumentManager.getInstance(getProject());
        final PsiFile[] psiFileArr = {null};
        PsiLanguageInjectionHost.InjectedPsiVisitor injectedPsiVisitor = new PsiLanguageInjectionHost.InjectedPsiVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.InjectedSelfElementInfo$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost.InjectedPsiVisitor
            public final void visit(PsiFile psiFile2, List list) {
                InjectedSelfElementInfo.lambda$getInjectedFileIn$0(psiDocumentManagerBase, textRange, psiFileArr, psiFile2, list);
            }
        };
        PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(getProject());
        Document document = psiDocumentManager.getDocument(psiFile);
        if (document != null && psiDocumentManager.isUncommited(document)) {
            Iterator<DocumentWindow> it = InjectedLanguageManager.getInstance(getProject()).getCachedInjectedDocumentsInRange(psiFile, textRange).iterator();
            while (it.hasNext()) {
                PsiFile psiFile2 = psiDocumentManager.getPsiFile(it.next());
                if (psiFile2 != null) {
                    injectedPsiVisitor.visit(psiFile2, Collections.emptyList());
                }
            }
        } else {
            List<Pair<PsiElement, TextRange>> injectedPsiFiles = InjectedLanguageManager.getInstance(getProject()).getInjectedPsiFiles(psiElement);
            if (injectedPsiFiles != null) {
                Iterator<Pair<PsiElement, TextRange>> it2 = injectedPsiFiles.iterator();
                while (it2.hasNext()) {
                    injectedPsiVisitor.visit(it2.next().first.getContainingFile(), ContainerUtil.emptyList());
                }
            }
        }
        return psiFileArr[0];
    }

    static /* synthetic */ void lambda$getInjectedFileIn$0(PsiDocumentManagerBase psiDocumentManagerBase, TextRange textRange, PsiFile[] psiFileArr, PsiFile psiFile, List list) {
        Document document = psiDocumentManagerBase.getDocument(psiFile);
        if ((document instanceof DocumentWindow) && ((DocumentWindow) psiDocumentManagerBase.getLastCommittedDocument(document)).injectedToHost(new TextRange(0, psiFile.getTextLength())).contains(textRange)) {
            psiFileArr[0] = psiFile;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    boolean pointsToTheSameElementAs(SmartPointerElementInfo smartPointerElementInfo, SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerElementInfo == null) {
            $$$reportNull$$$0(11);
        }
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(12);
        }
        if (getClass() != smartPointerElementInfo.getClass()) {
            return false;
        }
        InjectedSelfElementInfo injectedSelfElementInfo = (InjectedSelfElementInfo) smartPointerElementInfo;
        if (injectedSelfElementInfo.myHostContext.equals(this.myHostContext)) {
            return ((SmartPsiElementPointerImpl) this.myInjectedFileRangeInHostFile).getElementInfo().pointsToTheSameElementAs(((SmartPsiElementPointerImpl) injectedSelfElementInfo.myInjectedFileRangeInHostFile).getElementInfo(), smartPointerManagerImpl);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiFile restoreFile(SmartPointerManagerImpl smartPointerManagerImpl) {
        PsiElement element;
        Segment psiRange;
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(13);
        }
        PsiFile containingFile = this.myHostContext.getContainingFile();
        if (containingFile == null || !containingFile.isValid() || (element = this.myHostContext.getElement()) == null || (psiRange = this.myInjectedFileRangeInHostFile.getPsiRange()) == null) {
            return null;
        }
        return getInjectedFileIn(element, containingFile, TextRange.create(psiRange));
    }

    private ProperTextRange getInjectedRange(boolean z) {
        if (this.myHostContext.getElement() == null) {
            return null;
        }
        SmartPsiFileRange smartPsiFileRange = this.myInjectedFileRangeInHostFile;
        Segment psiRange = z ? smartPsiFileRange.getPsiRange() : smartPsiFileRange.getRange();
        if (psiRange == null) {
            return null;
        }
        return hostToInjected(z, psiRange, restoreFile((SmartPointerManagerImpl) SmartPointerManager.getInstance(getProject())), this.myAffixOffsets);
    }

    private static ProperTextRange hostToInjected(boolean z, Segment segment, PsiFile psiFile, AffixOffsets affixOffsets) {
        if (segment == null) {
            $$$reportNull$$$0(14);
        }
        ModificationTracker virtualFile = psiFile == null ? null : psiFile.getVirtualFile();
        if (!(virtualFile instanceof VirtualFileWindow)) {
            return null;
        }
        Project project = psiFile.getProject();
        DocumentWindow documentWindow = ((VirtualFileWindow) virtualFile).getDocumentWindow();
        if (z) {
            documentWindow = (DocumentWindow) ((PsiDocumentManagerBase) PsiDocumentManager.getInstance(project)).getLastCommittedDocument(documentWindow);
        }
        int iHostToInjected = documentWindow.hostToInjected(segment.getStartOffset());
        int iHostToInjected2 = documentWindow.hostToInjected(segment.getEndOffset());
        if (affixOffsets != null) {
            return affixOffsets.expandRangeToAffixes(iHostToInjected, iHostToInjected2, InjectedLanguageManager.getInstance(project).getNonEditableFragments(documentWindow));
        }
        return ProperTextRange.create(iHostToInjected, iHostToInjected2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    void cleanup() {
        SmartPointerManager.getInstance(getProject()).removePointer(this.myInjectedFileRangeInHostFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Document getDocumentToSynchronize() {
        return ((SmartPsiElementPointerImpl) this.myHostContext).getElementInfo().getDocumentToSynchronize();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    int elementHashCode() {
        return ((SmartPsiElementPointerImpl) this.myHostContext).getElementInfo().elementHashCode();
    }

    private Project getProject() {
        Project project = this.myHostContext.getProject();
        if (project == null) {
            $$$reportNull$$$0(15);
        }
        return project;
    }

    public String toString() {
        return "injected{type=" + this.myType + ", range=" + this.myInjectedFileRangeInHostFile + ", host=" + this.myHostContext + "}";
    }

    private static class AffixOffsets {
        final int endAffixIndex;
        final int endAffixOffset;
        final int startAffixIndex;
        final int startAffixOffset;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "fragments", "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/InjectedSelfElementInfo$AffixOffsets", "expandRangeToAffixes"));
        }

        AffixOffsets(int i, int i2, int i3, int i4) {
            this.startAffixIndex = i;
            this.startAffixOffset = i2;
            this.endAffixIndex = i3;
            this.endAffixOffset = i4;
        }

        ProperTextRange expandRangeToAffixes(int i, int i2, List<? extends TextRange> list) {
            if (list == null) {
                $$$reportNull$$$0(0);
            }
            int i3 = this.startAffixIndex;
            if (i3 >= 0) {
                TextRange textRange = i3 < list.size() ? list.get(this.startAffixIndex) : null;
                if (textRange == null || this.startAffixOffset > textRange.getLength()) {
                    return null;
                }
                i = textRange.getStartOffset() + this.startAffixOffset;
            }
            int i4 = this.endAffixIndex;
            if (i4 >= 0) {
                TextRange textRange2 = i4 < list.size() ? list.get(this.endAffixIndex) : null;
                if (textRange2 == null || this.endAffixOffset > textRange2.getLength()) {
                    return null;
                }
                i2 = textRange2.getStartOffset() + this.endAffixOffset;
            }
            return ProperTextRange.create(i, i2);
        }
    }
}
