package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageUtil;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;

/* JADX INFO: loaded from: classes3.dex */
final class FileElementInfo extends SmartPointerElementInfo {
    private final String myFileClassName;
    private final String myLanguageId;
    private final Project myProject;
    private final VirtualFile myVirtualFile;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 6:
            case 7:
                objArr[0] = "manager";
                break;
            case 3:
                objArr[0] = "other";
                break;
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/FileElementInfo";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/FileElementInfo";
        } else {
            objArr[1] = "getVirtualFile";
        }
        switch (i) {
            case 1:
                objArr[2] = "restoreElement";
                break;
            case 2:
                objArr[2] = "restoreFile";
                break;
            case 3:
            case 4:
                objArr[2] = "pointsToTheSameElementAs";
                break;
            case 5:
                break;
            case 6:
                objArr[2] = "getRange";
                break;
            case 7:
                objArr[2] = "getPsiRange";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    FileElementInfo(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        this.myVirtualFile = psiFile.getViewProvider().getVirtualFile();
        this.myProject = psiFile.getProject();
        this.myLanguageId = LanguageUtil.getRootLanguage(psiFile).getID();
        this.myFileClassName = psiFile.getClass().getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiElement restoreElement(SmartPointerManagerImpl smartPointerManagerImpl) {
        PsiFile psiFileRestoreFileFromVirtual;
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(1);
        }
        Language languageFindLanguageByID = Language.findLanguageByID(this.myLanguageId);
        if (languageFindLanguageByID == null || (psiFileRestoreFileFromVirtual = SelfElementInfo.restoreFileFromVirtual(this.myVirtualFile, this.myProject, languageFindLanguageByID)) == null || !psiFileRestoreFileFromVirtual.getClass().getName().equals(this.myFileClassName)) {
            return null;
        }
        return psiFileRestoreFileFromVirtual;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiFile restoreFile(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(2);
        }
        PsiElement psiElementRestoreElement = restoreElement(smartPointerManagerImpl);
        if (psiElementRestoreElement == null) {
            return null;
        }
        return psiElementRestoreElement.getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    int elementHashCode() {
        return this.myVirtualFile.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    boolean pointsToTheSameElementAs(SmartPointerElementInfo smartPointerElementInfo, SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerElementInfo == null) {
            $$$reportNull$$$0(3);
        }
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(4);
        }
        return (smartPointerElementInfo instanceof FileElementInfo) && Objects.equals(this.myVirtualFile, ((FileElementInfo) smartPointerElementInfo).myVirtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    VirtualFile getVirtualFile() {
        VirtualFile virtualFile = this.myVirtualFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(5);
        }
        return virtualFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Segment getRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        Document document;
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(6);
        }
        if (this.myVirtualFile.isValid() && (document = FileDocumentManager.getInstance().getDocument(this.myVirtualFile)) != null) {
            return TextRange.from(0, document.getTextLength());
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Segment getPsiRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(7);
        }
        Document cachedDocument = FileDocumentManager.getInstance().getCachedDocument(this.myVirtualFile);
        DocumentEx lastCommittedDocument = cachedDocument == null ? null : ((PsiDocumentManagerBase) PsiDocumentManager.getInstance(this.myProject)).getLastCommittedDocument(cachedDocument);
        return lastCommittedDocument == null ? getRange(smartPointerManagerImpl) : new TextRange(0, lastCommittedDocument.getTextLength());
    }

    public String toString() {
        return "file{" + this.myVirtualFile + ", " + this.myLanguageId + "}";
    }
}
