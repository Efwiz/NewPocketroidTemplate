package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.UnknownFileType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.SharedPsiElementImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.LocalTimeCounter;

/* JADX INFO: loaded from: classes3.dex */
public class DummyHolderViewProvider extends AbstractFileViewProvider {
    private DummyHolder myHolder;
    private final long myModificationStamp;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 4 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/DummyHolderViewProvider";
                break;
            case 3:
                objArr[0] = "target";
                break;
            case 7:
                objArr[0] = "dummyHolder";
                break;
            case 8:
                objArr[0] = "lang";
                break;
            case 9:
                objArr[0] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 1) {
            objArr[1] = "getBaseLanguage";
        } else if (i == 2) {
            objArr[1] = "getLanguages";
        } else if (i == 4) {
            objArr[1] = "getCachedPsiFiles";
        } else if (i == 5) {
            objArr[1] = "getKnownTreeRoots";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/DummyHolderViewProvider";
        } else {
            objArr[1] = "getAllFiles";
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
                break;
            case 3:
                objArr[2] = "getCachedPsi";
                break;
            case 7:
                objArr[2] = "setDummyHolder";
                break;
            case 8:
                objArr[2] = "findElementAt";
                break;
            case 9:
                objArr[2] = "createCopy";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DummyHolderViewProvider(PsiManager psiManager) {
        super(psiManager, new LightVirtualFile("DummyHolder", UnknownFileType.INSTANCE, ""), false);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        this.myModificationStamp = LocalTimeCounter.currentTime();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider, org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public CharSequence getContents() {
        DummyHolder dummyHolder = this.myHolder;
        return dummyHolder != null ? dummyHolder.getNode().getText() : "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public Language getBaseLanguage() {
        Language language = this.myHolder.getLanguage();
        if (language == null) {
            $$$reportNull$$$0(1);
        }
        return language;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public Set<Language> getLanguages() {
        Set<Language> setSingleton = Collections.singleton(getBaseLanguage());
        if (setSingleton == null) {
            $$$reportNull$$$0(2);
        }
        return setSingleton;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    protected PsiFile getPsiInner(Language language) {
        return getCachedPsi(language);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    public PsiFile getCachedPsi(Language language) {
        if (language == null) {
            $$$reportNull$$$0(3);
        }
        getManager().getFileManager().setViewProvider(getVirtualFile(), this);
        if (language == getBaseLanguage()) {
            return this.myHolder;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    public List<PsiFile> getCachedPsiFiles() {
        List<PsiFile> listSingletonList = Collections.singletonList(this.myHolder);
        if (listSingletonList == null) {
            $$$reportNull$$$0(4);
        }
        return listSingletonList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    public List<FileASTNode> getKnownTreeRoots() {
        List<FileASTNode> listSingletonList = Collections.singletonList(this.myHolder.getTreeElement());
        if (listSingletonList == null) {
            $$$reportNull$$$0(5);
        }
        return listSingletonList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public List<PsiFile> getAllFiles() {
        List<PsiFile> cachedPsiFiles = getCachedPsiFiles();
        if (cachedPsiFiles == null) {
            $$$reportNull$$$0(6);
        }
        return cachedPsiFiles;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider, org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public long getModificationStamp() {
        return this.myModificationStamp;
    }

    public void setDummyHolder(DummyHolder dummyHolder) {
        if (dummyHolder == null) {
            $$$reportNull$$$0(7);
        }
        this.myHolder = dummyHolder;
        ((LightVirtualFile) getVirtualFile()).setFileType(dummyHolder.getFileType());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiReference findReferenceAt(int i) {
        return SharedPsiElementImplUtil.findReferenceAt(getPsi(getBaseLanguage()), i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiElement findElementAt(int i, Class<? extends Language> cls) {
        if (cls == null) {
            $$$reportNull$$$0(8);
        }
        if (cls.isAssignableFrom(getBaseLanguage().getClass())) {
            return findElementAt(i);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public FileViewProvider createCopy(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(9);
        }
        throw new RuntimeException("Clone is not supported for DummyHolderProviders. Use DummyHolder clone directly.");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiElement findElementAt(int i) {
        LeafElement leafElementFindLeafElementAt = this.myHolder.calcTreeElement().findLeafElementAt(i);
        if (leafElementFindLeafElementAt != null) {
            return leafElementFindLeafElementAt.getPsi();
        }
        return null;
    }
}
