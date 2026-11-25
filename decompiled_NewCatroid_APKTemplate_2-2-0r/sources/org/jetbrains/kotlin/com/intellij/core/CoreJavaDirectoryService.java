package org.jetbrains.kotlin.com.intellij.core;

import java.util.ArrayList;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class CoreJavaDirectoryService extends JavaDirectoryService {
    private static final Logger LOG = Logger.getInstance((Class<?>) CoreJavaDirectoryService.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        switch (i) {
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreJavaDirectoryService";
                break;
            case 5:
            case 7:
            case 10:
            case 13:
            case 17:
            case 19:
            case 21:
            case 23:
            case 25:
            default:
                objArr[0] = "dir";
                break;
            case 6:
            case 8:
            case 11:
            case 14:
            case 18:
            case 20:
            case 22:
            case 24:
            case 26:
                objArr[0] = "name";
                break;
            case 9:
            case 12:
            case 15:
                objArr[0] = "templateName";
                break;
            case 16:
                objArr[0] = "additionalProperties";
                break;
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreJavaDirectoryService";
        } else {
            objArr[1] = "getPsiClasses";
        }
        switch (i) {
            case 1:
                objArr[2] = "getPackageInSources";
                break;
            case 2:
                objArr[2] = "getClasses";
                break;
            case 3:
                objArr[2] = "getPsiClasses";
                break;
            case 4:
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "createClass";
                break;
            case 17:
            case 18:
                objArr[2] = "checkCreateClass";
                break;
            case 19:
            case 20:
                objArr[2] = "createInterface";
                break;
            case 21:
            case 22:
                objArr[2] = "createEnum";
                break;
            case 23:
            case 24:
                objArr[2] = "createRecord";
                break;
            case 25:
            case 26:
                objArr[2] = "createAnnotationType";
                break;
            case 27:
                objArr[2] = "isSourceRoot";
                break;
            case 28:
                objArr[2] = "getLanguageLevel";
                break;
            default:
                objArr[2] = "getPackage";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public boolean isSourceRoot(PsiDirectory psiDirectory) {
        if (psiDirectory != null) {
            return false;
        }
        $$$reportNull$$$0(27);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public PsiPackage getPackage(PsiDirectory psiDirectory) {
        if (psiDirectory == null) {
            $$$reportNull$$$0(0);
        }
        return ((CoreJavaFileManager) psiDirectory.getProject().getService(CoreJavaFileManager.class)).getPackage(psiDirectory);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public PsiPackage getPackageInSources(PsiDirectory psiDirectory) {
        if (psiDirectory == null) {
            $$$reportNull$$$0(1);
        }
        return getPackage(psiDirectory);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public PsiClass[] getClasses(PsiDirectory psiDirectory) {
        if (psiDirectory == null) {
            $$$reportNull$$$0(2);
        }
        LOG.assertTrue(psiDirectory.isValid());
        return getPsiClasses(psiDirectory, psiDirectory.getFiles());
    }

    public static PsiClass[] getPsiClasses(PsiDirectory psiDirectory, PsiFile[] psiFileArr) {
        if (psiDirectory == null) {
            $$$reportNull$$$0(3);
        }
        FileIndexFacade fileIndexFacade = FileIndexFacade.getInstance(psiDirectory.getProject());
        VirtualFile virtualFile = psiDirectory.getVirtualFile();
        boolean z = fileIndexFacade.isInLibraryClasses(virtualFile) && !fileIndexFacade.isInSourceContent(virtualFile);
        ArrayList arrayList = null;
        for (PsiFile psiFile : psiFileArr) {
            if ((!z || (psiFile instanceof ClsFileImpl)) && (psiFile instanceof PsiClassOwner) && psiFile.getViewProvider().getLanguages().size() == 1) {
                PsiClass[] classes = ((PsiClassOwner) psiFile).getClasses();
                if (classes.length != 0) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    ContainerUtil.addAll(arrayList, classes);
                }
            }
        }
        PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
        if (arrayList != null) {
            psiClassArr = (PsiClass[]) arrayList.toArray(psiClassArr);
        }
        if (psiClassArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiClassArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public PsiClass createClass(PsiDirectory psiDirectory, String str) throws IncorrectOperationException {
        if (psiDirectory == null) {
            $$$reportNull$$$0(5);
        }
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public PsiClass createClass(PsiDirectory psiDirectory, String str, String str2) throws IncorrectOperationException {
        if (psiDirectory == null) {
            $$$reportNull$$$0(7);
        }
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (str2 == null) {
            $$$reportNull$$$0(9);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public PsiClass createClass(PsiDirectory psiDirectory, String str, String str2, boolean z) throws IncorrectOperationException {
        if (psiDirectory == null) {
            $$$reportNull$$$0(10);
        }
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        if (str2 == null) {
            $$$reportNull$$$0(12);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public PsiClass createClass(PsiDirectory psiDirectory, String str, String str2, boolean z, Map<String, String> map) throws IncorrectOperationException {
        if (psiDirectory == null) {
            $$$reportNull$$$0(13);
        }
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (str2 == null) {
            $$$reportNull$$$0(15);
        }
        if (map == null) {
            $$$reportNull$$$0(16);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public void checkCreateClass(PsiDirectory psiDirectory, String str) throws IncorrectOperationException {
        if (psiDirectory == null) {
            $$$reportNull$$$0(17);
        }
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public PsiClass createInterface(PsiDirectory psiDirectory, String str) throws IncorrectOperationException {
        if (psiDirectory == null) {
            $$$reportNull$$$0(19);
        }
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public PsiClass createEnum(PsiDirectory psiDirectory, String str) throws IncorrectOperationException {
        if (psiDirectory == null) {
            $$$reportNull$$$0(21);
        }
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public PsiClass createRecord(PsiDirectory psiDirectory, String str) throws IncorrectOperationException {
        if (psiDirectory == null) {
            $$$reportNull$$$0(23);
        }
        if (str == null) {
            $$$reportNull$$$0(24);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public PsiClass createAnnotationType(PsiDirectory psiDirectory, String str) throws IncorrectOperationException {
        if (psiDirectory == null) {
            $$$reportNull$$$0(25);
        }
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService
    public LanguageLevel getLanguageLevel(PsiDirectory psiDirectory) {
        if (psiDirectory == null) {
            $$$reportNull$$$0(28);
        }
        return LanguageLevel.HIGHEST;
    }
}
