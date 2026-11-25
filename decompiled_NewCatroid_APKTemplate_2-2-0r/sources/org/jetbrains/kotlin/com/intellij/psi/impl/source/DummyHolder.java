package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextFileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextLanguage;
import org.jetbrains.kotlin.com.intellij.psi.DummyHolderViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class DummyHolder extends PsiFileImpl {
    protected final PsiElement myContext;
    private final Boolean myExplicitlyValid;
    private final Language myLanguage;
    private final CharTable myTable;
    private final DummyHolderTreeLock myTreeElementLock;
    private FileViewProvider myViewProvider;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 8:
                objArr[0] = "visitor";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/DummyHolder";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        switch (i) {
            case 9:
            case 10:
                objArr[1] = "getFileType";
                break;
            case 11:
            case 12:
                objArr[1] = "getTreeElement";
                break;
            case 13:
                objArr[1] = "getLanguage";
                break;
            case 14:
            case 15:
                objArr[1] = "getViewProvider";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/DummyHolder";
                break;
        }
        switch (i) {
            case 8:
                objArr[2] = "accept";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    private static class DummyHolderTreeLock {
        private DummyHolderTreeLock() {
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DummyHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement) {
        this(psiManager, treeElement, psiElement, SharedImplUtil.findCharTableByTree(treeElement));
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DummyHolder(PsiManager psiManager, CharTable charTable, boolean z) {
        this(psiManager, null, null, charTable, Boolean.valueOf(z), PlainTextLanguage.INSTANCE);
        if (psiManager == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DummyHolder(PsiManager psiManager, PsiElement psiElement) {
        this(psiManager, null, psiElement, null);
        if (psiManager == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DummyHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement, CharTable charTable) {
        this(psiManager, treeElement, psiElement, charTable, null, language(psiElement, PlainTextLanguage.INSTANCE));
        if (psiManager == null) {
            $$$reportNull$$$0(3);
        }
    }

    protected static Language language(PsiElement psiElement, Language language) {
        PsiFile containingFile;
        if (psiElement == null || (containingFile = psiElement.getContainingFile()) == null) {
            return language;
        }
        Language language2 = psiElement.getLanguage();
        Language language3 = containingFile.getLanguage();
        return language3.isKindOf(language2) ? language3 : language2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DummyHolder(PsiManager psiManager, TreeElement treeElement, PsiElement psiElement, CharTable charTable, Boolean bool, Language language) {
        super(TokenType.DUMMY_HOLDER, TokenType.DUMMY_HOLDER, new DummyHolderViewProvider(psiManager));
        if (psiManager == null) {
            $$$reportNull$$$0(4);
        }
        this.myTreeElementLock = new DummyHolderTreeLock();
        this.myLanguage = language;
        ((DummyHolderViewProvider) getViewProvider()).setDummyHolder(this);
        this.myContext = psiElement;
        charTable = charTable == null ? new CharTableImpl() : charTable;
        this.myTable = charTable;
        if (treeElement instanceof FileElement) {
            FileElement fileElement = (FileElement) treeElement;
            fileElement.setPsi(this);
            fileElement.setCharTable(charTable);
            setTreeElementPointer(fileElement);
        } else if (treeElement != null) {
            getTreeElement().rawAddChildren(treeElement);
            clearCaches();
        }
        this.myExplicitlyValid = bool;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DummyHolder(PsiManager psiManager, PsiElement psiElement, CharTable charTable) {
        this(psiManager, null, psiElement, charTable);
        if (psiManager == null) {
            $$$reportNull$$$0(5);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DummyHolder(PsiManager psiManager, CharTable charTable, Language language) {
        this(psiManager, null, null, charTable, null, language);
        if (psiManager == null) {
            $$$reportNull$$$0(6);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DummyHolder(PsiManager psiManager, Language language, PsiElement psiElement) {
        this(psiManager, null, psiElement, null, null, language);
        if (psiManager == null) {
            $$$reportNull$$$0(7);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        PsiElement psiElement = this.myContext;
        return (psiElement == null || !psiElement.isValid()) ? super.getContext() : this.myContext;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        Boolean bool = this.myExplicitlyValid;
        return bool != null ? bool.booleanValue() : super.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(8);
        }
        psiElementVisitor.visitFile(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "DummyHolder";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public FileType getFileType() {
        PsiFile containingFile;
        PsiElement context = getContext();
        if (context != null && (containingFile = context.getContainingFile()) != null) {
            FileType fileType = containingFile.getFileType();
            if (fileType == null) {
                $$$reportNull$$$0(9);
            }
            return fileType;
        }
        LanguageFileType associatedFileType = this.myLanguage.getAssociatedFileType();
        if (associatedFileType == null) {
            associatedFileType = PlainTextFileType.INSTANCE;
        }
        if (associatedFileType == null) {
            $$$reportNull$$$0(10);
        }
        return associatedFileType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl
    public FileElement getTreeElement() {
        FileElement fileElementDerefTreeElement;
        FileElement fileElementDerefTreeElement2 = super.derefTreeElement();
        if (fileElementDerefTreeElement2 != null) {
            if (fileElementDerefTreeElement2 == null) {
                $$$reportNull$$$0(11);
            }
            return fileElementDerefTreeElement2;
        }
        synchronized (this.myTreeElementLock) {
            fileElementDerefTreeElement = super.derefTreeElement();
            if (fileElementDerefTreeElement == null) {
                fileElementDerefTreeElement = new FileElement(TokenType.DUMMY_HOLDER, null);
                fileElementDerefTreeElement.setPsi(this);
                CharTable charTable = this.myTable;
                if (charTable != null) {
                    fileElementDerefTreeElement.setCharTable(charTable);
                }
                setTreeElementPointer(fileElementDerefTreeElement);
                clearCaches();
            }
        }
        if (fileElementDerefTreeElement == null) {
            $$$reportNull$$$0(12);
        }
        return fileElementDerefTreeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        Language language = this.myLanguage;
        if (language == null) {
            $$$reportNull$$$0(13);
        }
        return language;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public PsiFileImpl clone() {
        DummyHolder dummyHolder = (DummyHolder) cloneImpl((FileElement) calcTreeElement().clone());
        DummyHolderViewProvider dummyHolderViewProvider = new DummyHolderViewProvider(getManager());
        dummyHolder.myViewProvider = dummyHolderViewProvider;
        dummyHolderViewProvider.setDummyHolder(dummyHolder);
        dummyHolder.myOriginalFile = getIsPhysical() ? this : this.myOriginalFile;
        return dummyHolder;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public FileViewProvider getViewProvider() {
        FileViewProvider fileViewProvider = this.myViewProvider;
        if (fileViewProvider != null) {
            if (fileViewProvider == null) {
                $$$reportNull$$$0(14);
            }
            return fileViewProvider;
        }
        FileViewProvider viewProvider = super.getViewProvider();
        if (viewProvider == null) {
            $$$reportNull$$$0(15);
        }
        return viewProvider;
    }
}
