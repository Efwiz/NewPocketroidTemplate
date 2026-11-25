package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextFileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextLanguage;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.HintedReferenceHost;
import org.jetbrains.kotlin.com.intellij.psi.PlainTextTokenTypes;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiPlainTextFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;

/* JADX INFO: loaded from: classes3.dex */
public class PsiPlainTextFileImpl extends PsiFileImpl implements HintedReferenceHost, PsiPlainTextFile {
    private final FileType myFileType;

    /* JADX WARN: Code duplicated, block: B:21:0x002d  */
    /* JADX WARN: Code duplicated, block: B:22:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 4) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiPlainTextFileImpl";
        } else if (i == 3) {
            objArr[0] = "hints";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiPlainTextFileImpl";
        } else if (i != 5) {
            objArr[0] = "visitor";
        } else {
            objArr[0] = "hints";
        }
        if (i == 1) {
            objArr[1] = "getFileType";
        } else if (i == 2 || i == 4) {
            objArr[1] = "getReferences";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiPlainTextFileImpl";
        }
        if (i != 1 && i != 2) {
            if (i == 3) {
                objArr[2] = "getReferences";
            } else if (i != 4) {
                if (i != 5) {
                    objArr[2] = "accept";
                } else {
                    objArr[2] = "shouldAskParentForReferences";
                }
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.HintedReferenceHost
    public boolean shouldAskParentForReferences(PsiReferenceService.Hints hints) {
        if (hints != null) {
            return false;
        }
        $$$reportNull$$$0(5);
        return false;
    }

    public PsiPlainTextFileImpl(FileViewProvider fileViewProvider) {
        super(PlainTextTokenTypes.PLAIN_TEXT_FILE, PlainTextTokenTypes.PLAIN_TEXT_FILE, fileViewProvider);
        this.myFileType = fileViewProvider.getBaseLanguage() != PlainTextLanguage.INSTANCE ? PlainTextFileType.INSTANCE : fileViewProvider.getFileType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        psiElementVisitor.visitPlainTextFile(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiFile(plain text):" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public FileType getFileType() {
        FileType fileType = this.myFileType;
        if (fileType == null) {
            $$$reportNull$$$0(1);
        }
        return fileType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference[] getReferences() {
        PsiReference[] referencesFromProviders = ReferenceProvidersRegistry.getReferencesFromProviders(this);
        if (referencesFromProviders == null) {
            $$$reportNull$$$0(2);
        }
        return referencesFromProviders;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.HintedReferenceHost
    public PsiReference[] getReferences(PsiReferenceService.Hints hints) {
        if (hints == null) {
            $$$reportNull$$$0(3);
        }
        PsiReference[] referencesFromProviders = ReferenceProvidersRegistry.getReferencesFromProviders(this, hints);
        if (referencesFromProviders == null) {
            $$$reportNull$$$0(4);
        }
        return referencesFromProviders;
    }
}
