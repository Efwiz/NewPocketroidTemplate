package org.jetbrains.kotlin.com.intellij.psi;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageUtil;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.PersistentFSConstants;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class SingleRootFileViewProvider extends AbstractFileViewProvider implements FileViewProvider {
    private final Language myBaseLanguage;
    private volatile PsiFile myPsiFile;
    private static final Key<Boolean> OUR_NO_SIZE_LIMIT_KEY = Key.create("no.size.limit");
    private static final Logger LOG = Logger.getInstance((Class<?>) SingleRootFileViewProvider.class);
    private static final AtomicReferenceFieldUpdater<SingleRootFileViewProvider, PsiFile> myPsiFileUpdater = AtomicReferenceFieldUpdater.newUpdater(SingleRootFileViewProvider.class, PsiFile.class, "myPsiFile");

    /* JADX WARN: Code duplicated, block: B:33:0x0060  */
    /* JADX WARN: Code duplicated, block: B:34:0x0065  */
    /* JADX WARN: Code duplicated, block: B:35:0x006a  */
    /* JADX WARN: Code duplicated, block: B:8:0x0012  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 10 && i != 14 && i != 15) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 10 && i != 14 && i != 15) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        if (i == 1) {
            objArr[0] = "file";
        } else if (i == 3 || i == 5) {
            objArr[0] = "virtualFile";
        } else if (i != 6) {
            switch (i) {
                case 8:
                    objArr[0] = "virtualFile";
                    break;
                case 9:
                    objArr[0] = "language";
                    break;
                case 10:
                case 14:
                case 15:
                case 18:
                case 19:
                case 20:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/SingleRootFileViewProvider";
                    break;
                case 11:
                case 22:
                    objArr[0] = "file";
                    break;
                case 12:
                    objArr[0] = "project";
                    break;
                case 13:
                    objArr[0] = "fileType";
                    break;
                case 16:
                case 17:
                    objArr[0] = "target";
                    break;
                case 21:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                    objArr[0] = "vFile";
                    break;
                case 28:
                    objArr[0] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                    break;
                case 29:
                    objArr[0] = "lang";
                    break;
                case 30:
                    objArr[0] = "psiFile";
                    break;
                default:
                    objArr[0] = "manager";
                    break;
            }
        } else {
            objArr[0] = "fileType";
        }
        if (i == 10) {
            objArr[1] = "getBaseLanguage";
        } else if (i == 14) {
            objArr[1] = "getLanguages";
        } else if (i != 15) {
            switch (i) {
                case 18:
                    objArr[1] = "getCachedPsiFiles";
                    break;
                case 19:
                case 20:
                    objArr[1] = "getKnownTreeRoots";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/SingleRootFileViewProvider";
                    break;
            }
        } else {
            objArr[1] = "getAllFiles";
        }
        switch (i) {
            case 10:
            case 14:
            case 15:
            case 18:
            case 19:
            case 20:
                break;
            case 11:
            case 12:
            case 13:
                objArr[2] = "calcBaseLanguage";
                break;
            case 16:
                objArr[2] = "getPsiInner";
                break;
            case 17:
                objArr[2] = "getCachedPsi";
                break;
            case 21:
            case 22:
                objArr[2] = "isTooLargeForIntelligence";
                break;
            case 23:
            case 24:
                objArr[2] = "isTooLargeForContentLoading";
                break;
            case 25:
                objArr[2] = "checkFileSizeLimit";
                break;
            case 26:
                objArr[2] = "doNotCheckFileSizeLimit";
                break;
            case 27:
                objArr[2] = "fileSizeIsGreaterThan";
                break;
            case 28:
                objArr[2] = "createCopy";
                break;
            case 29:
                objArr[2] = "findElementAt";
                break;
            case 30:
                objArr[2] = "forceCachedPsi";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 10 && i != 14 && i != 15) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SingleRootFileViewProvider(PsiManager psiManager, VirtualFile virtualFile) {
        this(psiManager, virtualFile, true);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SingleRootFileViewProvider(PsiManager psiManager, VirtualFile virtualFile, boolean z) {
        this(psiManager, virtualFile, z, calcBaseLanguage(virtualFile, psiManager.getProject(), virtualFile.getFileType()));
        if (psiManager == null) {
            $$$reportNull$$$0(2);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SingleRootFileViewProvider(PsiManager psiManager, VirtualFile virtualFile, boolean z, FileType fileType) {
        this(psiManager, virtualFile, z, calcBaseLanguage(virtualFile, psiManager.getProject(), fileType));
        if (psiManager == null) {
            $$$reportNull$$$0(4);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(5);
        }
        if (fileType == null) {
            $$$reportNull$$$0(6);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected SingleRootFileViewProvider(PsiManager psiManager, VirtualFile virtualFile, boolean z, Language language) {
        super(psiManager, virtualFile, z);
        if (psiManager == null) {
            $$$reportNull$$$0(7);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(8);
        }
        if (language == null) {
            $$$reportNull$$$0(9);
        }
        this.myBaseLanguage = language;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public Language getBaseLanguage() {
        Language language = this.myBaseLanguage;
        if (language == null) {
            $$$reportNull$$$0(10);
        }
        return language;
    }

    private static Language calcBaseLanguage(VirtualFile virtualFile, Project project, FileType fileType) {
        if (virtualFile == null) {
            $$$reportNull$$$0(11);
        }
        if (project == null) {
            $$$reportNull$$$0(12);
        }
        if (fileType == null) {
            $$$reportNull$$$0(13);
        }
        if (fileType.isBinary()) {
            return Language.ANY;
        }
        if (isTooLargeForIntelligence(virtualFile)) {
            return PlainTextLanguage.INSTANCE;
        }
        Language languageForPsi = LanguageUtil.getLanguageForPsi(project, virtualFile, fileType);
        return languageForPsi != null ? languageForPsi : PlainTextLanguage.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public Set<Language> getLanguages() {
        Set<Language> setSingleton = Collections.singleton(getBaseLanguage());
        if (setSingleton == null) {
            $$$reportNull$$$0(14);
        }
        return setSingleton;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public List<PsiFile> getAllFiles() {
        List<PsiFile> listCreateMaybeSingletonList = ContainerUtil.createMaybeSingletonList(getPsi(getBaseLanguage()));
        if (listCreateMaybeSingletonList == null) {
            $$$reportNull$$$0(15);
        }
        return listCreateMaybeSingletonList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    protected PsiFile getPsiInner(Language language) throws Throwable {
        if (language == null) {
            $$$reportNull$$$0(16);
        }
        if (language != getBaseLanguage()) {
            return null;
        }
        final PsiFile psiFileCreateFile = this.myPsiFile;
        if (psiFileCreateFile == null) {
            psiFileCreateFile = createFile();
            if (psiFileCreateFile == null) {
                psiFileCreateFile = PsiUtilCore.NULL_PSI_FILE;
            }
            if (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(myPsiFileUpdater, this, null, psiFileCreateFile) && psiFileCreateFile != PsiUtilCore.NULL_PSI_FILE) {
                PsiFile psiFile = this.myPsiFile;
                if (psiFile == psiFileCreateFile) {
                    LOG.error(this + ".createFile() must create new file instance but got the same: " + psiFileCreateFile);
                }
                if (psiFileCreateFile instanceof PsiFileEx) {
                    DebugUtil.performPsiModification("invalidating throw-away copy", new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider$$ExternalSyntheticLambda0
                        @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                        public final void run() {
                            ((PsiFileEx) psiFileCreateFile).markInvalidated();
                        }
                    });
                }
                psiFileCreateFile = psiFile;
            }
        }
        return (PsiFile) ObjectUtils.nullizeIfDefaultValue(psiFileCreateFile, PsiUtilCore.NULL_PSI_FILE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    public final PsiFile getCachedPsi(Language language) {
        if (language == null) {
            $$$reportNull$$$0(17);
        }
        if (language != getBaseLanguage()) {
            return null;
        }
        return (PsiFile) ObjectUtils.nullizeIfDefaultValue(this.myPsiFile, PsiUtilCore.NULL_PSI_FILE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    public final List<PsiFile> getCachedPsiFiles() {
        List<PsiFile> listCreateMaybeSingletonList = ContainerUtil.createMaybeSingletonList(getCachedPsi(getBaseLanguage()));
        if (listCreateMaybeSingletonList == null) {
            $$$reportNull$$$0(18);
        }
        return listCreateMaybeSingletonList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    public final List<FileASTNode> getKnownTreeRoots() {
        PsiFile cachedPsi = getCachedPsi(getBaseLanguage());
        if (cachedPsi instanceof PsiFileImpl) {
            List<FileASTNode> listCreateMaybeSingletonList = ContainerUtil.createMaybeSingletonList(((PsiFileImpl) cachedPsi).getNodeIfLoaded());
            if (listCreateMaybeSingletonList == null) {
                $$$reportNull$$$0(20);
            }
            return listCreateMaybeSingletonList;
        }
        List<FileASTNode> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(19);
        }
        return listEmptyList;
    }

    private PsiFile createFile() {
        try {
            if (shouldCreatePsi()) {
                return createFile(getManager().getProject(), getVirtualFile(), getFileType());
            }
            return null;
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            LOG.error(th);
            return null;
        }
    }

    public static boolean isTooLargeForIntelligence(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(21);
        }
        return isTooLargeForIntelligence(virtualFile, null);
    }

    public static boolean isTooLargeForIntelligence(VirtualFile virtualFile, Long l) {
        if (virtualFile == null) {
            $$$reportNull$$$0(22);
        }
        if (!checkFileSizeLimit(virtualFile)) {
            return false;
        }
        if ((virtualFile instanceof LightVirtualFile) && ((LightVirtualFile) virtualFile).isTooLargeForIntelligence() == ThreeState.YES) {
            return false;
        }
        int maxIntellisenseFileSize = PersistentFSConstants.getMaxIntellisenseFileSize();
        if (l == null) {
            return fileSizeIsGreaterThan(virtualFile, maxIntellisenseFileSize);
        }
        return l.longValue() > ((long) maxIntellisenseFileSize);
    }

    public static boolean isTooLargeForContentLoading(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(23);
        }
        return fileSizeIsGreaterThan(virtualFile, PersistentFSConstants.FILE_LENGTH_TO_CACHE_THRESHOLD);
    }

    public static boolean isTooLargeForContentLoading(VirtualFile virtualFile, Long l) {
        if (virtualFile == null) {
            $$$reportNull$$$0(24);
        }
        long j = PersistentFSConstants.FILE_LENGTH_TO_CACHE_THRESHOLD;
        if (l == null) {
            return fileSizeIsGreaterThan(virtualFile, j);
        }
        return l.longValue() > j;
    }

    private static boolean checkFileSizeLimit(VirtualFile virtualFile) {
        VirtualFile originalFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(25);
        }
        if (Boolean.TRUE.equals(virtualFile.getCopyableUserData(OUR_NO_SIZE_LIMIT_KEY))) {
            return false;
        }
        if (!(virtualFile instanceof LightVirtualFile) || (originalFile = ((LightVirtualFile) virtualFile).getOriginalFile()) == null) {
            return true;
        }
        return checkFileSizeLimit(originalFile);
    }

    public static void doNotCheckFileSizeLimit(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(26);
        }
        virtualFile.putCopyableUserData(OUR_NO_SIZE_LIMIT_KEY, Boolean.TRUE);
    }

    public static boolean fileSizeIsGreaterThan(VirtualFile virtualFile, long j) {
        if (virtualFile == null) {
            $$$reportNull$$$0(27);
        }
        if ((virtualFile instanceof LightVirtualFile) && !virtualFile.getFileType().isBinary()) {
            long length = ((LightVirtualFile) virtualFile).getContent().length();
            if (length < j / 2) {
                return false;
            }
            if (length > j) {
                return true;
            }
        }
        return virtualFile.getLength() > j;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public SingleRootFileViewProvider createCopy(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(28);
        }
        return new SingleRootFileViewProvider((PsiManager) getManager(), virtualFile, false, getBaseLanguage());
    }

    public PsiReference findReferenceAt(int i) {
        return findReferenceAt(getPsi(getBaseLanguage()), i);
    }

    public PsiElement findElementAt(int i) {
        return findElementAt(getPsi(getBaseLanguage()), i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiElement findElementAt(int i, Class<? extends Language> cls) {
        if (cls == null) {
            $$$reportNull$$$0(29);
        }
        if (ReflectionUtil.isAssignable(cls, getBaseLanguage().getClass())) {
            return findElementAt(i);
        }
        return null;
    }

    public final void forceCachedPsi(PsiFile psiFile) throws Throwable {
        final PsiFile psiFile2;
        if (psiFile == null) {
            $$$reportNull$$$0(30);
        }
        do {
            psiFile2 = this.myPsiFile;
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(myPsiFileUpdater, this, psiFile2, psiFile));
        if (psiFile2 != psiFile && (psiFile2 instanceof PsiFileEx)) {
            DebugUtil.performPsiModification(getClass().getName() + " PSI change", new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                public final void run() {
                    ((PsiFileEx) psiFile2).markInvalidated();
                }
            });
        }
        getManager().getFileManager().setViewProvider(getVirtualFile(), this);
    }
}
