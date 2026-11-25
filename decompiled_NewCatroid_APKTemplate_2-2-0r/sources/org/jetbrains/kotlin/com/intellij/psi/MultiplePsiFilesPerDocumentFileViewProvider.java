package org.jetbrains.kotlin.com.intellij.psi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.SharedPsiElementImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.templateLanguages.OuterLanguageElement;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MultiplePsiFilesPerDocumentFileViewProvider extends AbstractFileViewProvider {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private MultiplePsiFilesPerDocumentFileViewProvider myOriginal;
    protected final ConcurrentMap<Language, PsiFileImpl> myRoots;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 10 || i == 7 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 10 || i == 7 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "virtualFile";
                break;
            case 2:
            case 7:
            case 8:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/MultiplePsiFilesPerDocumentFileViewProvider";
                break;
            case 3:
                objArr[0] = "language";
                break;
            case 4:
            case 5:
            case 6:
                objArr[0] = "target";
                break;
            case 9:
                objArr[0] = "fileCopy";
                break;
            case 11:
                objArr[0] = "lang";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 2) {
            objArr[1] = "getAllFiles";
        } else if (i == 10) {
            objArr[1] = "createCopy";
        } else if (i == 7) {
            objArr[1] = "getCachedPsiFiles";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/MultiplePsiFilesPerDocumentFileViewProvider";
        } else {
            objArr[1] = "getKnownTreeRoots";
        }
        switch (i) {
            case 2:
            case 7:
            case 8:
            case 10:
                break;
            case 3:
                objArr[2] = "removeFile";
                break;
            case 4:
                objArr[2] = "getPsiInner";
                break;
            case 5:
                objArr[2] = "createPsiFileImpl";
                break;
            case 6:
                objArr[2] = "getCachedPsi";
                break;
            case 9:
                objArr[2] = "createCopy";
                break;
            case 11:
                objArr[2] = "findElementAt";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 10 && i != 7 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected abstract MultiplePsiFilesPerDocumentFileViewProvider cloneInner(VirtualFile virtualFile);

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public abstract Language getBaseLanguage();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultiplePsiFilesPerDocumentFileViewProvider(PsiManager psiManager, VirtualFile virtualFile, boolean z) {
        super(psiManager, virtualFile, z);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        this.myRoots = new ConcurrentHashMap(1, 0.75f, 1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public List<PsiFile> getAllFiles() {
        ArrayList arrayList = new ArrayList();
        Iterator<Language> it = getLanguages().iterator();
        while (it.hasNext()) {
            PsiFile psi = getPsi(it.next());
            if (psi != null) {
                arrayList.add(psi);
            }
        }
        PsiFile psi2 = getPsi(getBaseLanguage());
        if (!arrayList.isEmpty() && arrayList.get(0) != psi2) {
            arrayList.remove(psi2);
            arrayList.add(0, psi2);
        }
        return arrayList;
    }

    protected final void removeFile(Language language) {
        if (language == null) {
            $$$reportNull$$$0(3);
        }
        PsiFileImpl psiFileImplRemove = this.myRoots.remove(language);
        if (psiFileImplRemove != null) {
            psiFileImplRemove.markInvalidated();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    protected PsiFile getPsiInner(Language language) {
        PsiFileImpl psiFileImplCreatePsiFileImpl;
        PsiFile psi;
        if (language == null) {
            $$$reportNull$$$0(4);
        }
        PsiFileImpl psiFileImpl = this.myRoots.get(language);
        if (psiFileImpl != null) {
            return psiFileImpl;
        }
        if (!shouldCreatePsi()) {
            return null;
        }
        if ((language != getBaseLanguage() && !getLanguages().contains(language)) || (psiFileImplCreatePsiFileImpl = createPsiFileImpl(language)) == null) {
            return null;
        }
        MultiplePsiFilesPerDocumentFileViewProvider multiplePsiFilesPerDocumentFileViewProvider = this.myOriginal;
        if (multiplePsiFilesPerDocumentFileViewProvider != null && (psi = multiplePsiFilesPerDocumentFileViewProvider.getPsi(language)) != null) {
            psiFileImplCreatePsiFileImpl.setOriginalFile(psi);
        }
        return (PsiFileImpl) ConcurrencyUtil.cacheOrGet(this.myRoots, language, psiFileImplCreatePsiFileImpl);
    }

    protected PsiFileImpl createPsiFileImpl(Language language) {
        if (language == null) {
            $$$reportNull$$$0(5);
        }
        return (PsiFileImpl) createFile(language);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    public final PsiFile getCachedPsi(Language language) {
        if (language == null) {
            $$$reportNull$$$0(6);
        }
        return this.myRoots.get(language);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    public final List<PsiFile> getCachedPsiFiles() {
        List<PsiFile> listMapNotNull = ContainerUtil.mapNotNull(this.myRoots.keySet(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.getCachedPsi((Language) obj);
            }
        });
        if (listMapNotNull == null) {
            $$$reportNull$$$0(7);
        }
        return listMapNotNull;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    public final List<FileASTNode> getKnownTreeRoots() {
        ArrayList arrayList = new ArrayList(this.myRoots.size());
        Iterator<PsiFileImpl> it = this.myRoots.values().iterator();
        while (it.hasNext()) {
            FileASTNode nodeIfLoaded = it.next().getNodeIfLoaded();
            if (nodeIfLoaded != null) {
                arrayList.add(nodeIfLoaded);
            }
        }
        return arrayList;
    }

    public void checkAllTreesEqual() {
        Collection<PsiFileImpl> collectionValues = this.myRoots.values();
        PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(getManager().getProject());
        psiDocumentManager.m6553xce7c3507();
        for (PsiFileImpl psiFileImpl : collectionValues) {
            PsiDocumentManagerBase.checkConsistency(psiFileImpl, psiDocumentManager.getDocument(psiFileImpl));
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public final MultiplePsiFilesPerDocumentFileViewProvider createCopy(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(9);
        }
        MultiplePsiFilesPerDocumentFileViewProvider multiplePsiFilesPerDocumentFileViewProviderCloneInner = cloneInner(virtualFile);
        MultiplePsiFilesPerDocumentFileViewProvider multiplePsiFilesPerDocumentFileViewProvider = this.myOriginal;
        if (multiplePsiFilesPerDocumentFileViewProvider == null) {
            multiplePsiFilesPerDocumentFileViewProvider = this;
        }
        multiplePsiFilesPerDocumentFileViewProviderCloneInner.myOriginal = multiplePsiFilesPerDocumentFileViewProvider;
        if (multiplePsiFilesPerDocumentFileViewProviderCloneInner == null) {
            $$$reportNull$$$0(10);
        }
        return multiplePsiFilesPerDocumentFileViewProviderCloneInner;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiElement findElementAt(int i, Class<? extends Language> cls) {
        if (cls == null) {
            $$$reportNull$$$0(11);
        }
        PsiFile psi = getPsi(getBaseLanguage());
        PsiElement psiElement = null;
        for (Language language : getLanguages()) {
            if (ReflectionUtil.isAssignable(cls, language.getClass()) && (!cls.equals(Language.class) || getLanguages().contains(language))) {
                PsiFile psi2 = getPsi(language);
                PsiElement psiElementFindElementAt = findElementAt(psi2, i);
                if (psiElementFindElementAt != null && !(psiElementFindElementAt instanceof OuterLanguageElement) && (psiElement == null || psi2 != psi)) {
                    psiElement = psiElementFindElementAt;
                }
            }
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiElement findElementAt(int i) {
        return findElementAt(i, Language.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiReference findReferenceAt(int i) {
        TextRange textRange = new TextRange(0, getContents().length());
        PsiReference psiReference = null;
        for (Language language : getLanguages()) {
            PsiReference psiReferenceFindReferenceAt = SharedPsiElementImplUtil.findReferenceAt(getPsi(language), i, language);
            if (psiReferenceFindReferenceAt != null) {
                TextRange textRangeShiftRight = psiReferenceFindReferenceAt.getRangeInElement().shiftRight(psiReferenceFindReferenceAt.getPsiElement().getTextRange().getStartOffset());
                if (textRange.contains(textRangeShiftRight) && (!textRangeShiftRight.contains(textRange) || psiReference == null)) {
                    psiReference = psiReferenceFindReferenceAt;
                    textRange = textRangeShiftRight;
                }
            }
        }
        return psiReference;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider, org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public void contentsSynchronized() throws Throwable {
        Set<Language> languages = getLanguages();
        Iterator<Map.Entry<Language, PsiFileImpl>> it = this.myRoots.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Language, PsiFileImpl> next = it.next();
            if (!languages.contains(next.getKey())) {
                final PsiFileImpl value = next.getValue();
                it.remove();
                DebugUtil.performPsiModification(getClass().getName() + " root change", new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                    public final void run() {
                        value.markInvalidated();
                    }
                });
            }
        }
        super.contentsSynchronized();
    }
}
