package org.jetbrains.kotlin.com.intellij.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public class CoreJavaFileManager implements JavaFileManager {
    private static final Logger LOG = Logger.getInstance((Class<?>) CoreJavaFileManager.class);
    private final List<VirtualFile> myClasspath = new ArrayList();
    private final PsiManager myPsiManager;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 8 || i == 9 || i == 16 || i == 17 || i == 20) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 8 || i == 9 || i == 16 || i == 17 || i == 20) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 14:
                objArr[0] = "qName";
                break;
            case 2:
            case 6:
            case 15:
            case 19:
                objArr[0] = "scope";
                break;
            case 4:
                objArr[0] = "root";
                break;
            case 5:
                objArr[0] = "psiManager";
                break;
            case 7:
                objArr[0] = "classNameWithInnerClasses";
                break;
            case 8:
            case 9:
            case 16:
            case 17:
            case 20:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreJavaFileManager";
                break;
            case 10:
                objArr[0] = "classNameWithInnerClassesDotSeparated";
                break;
            case 11:
                objArr[0] = "file";
                break;
            case 12:
                objArr[0] = "className";
                break;
            case 13:
                objArr[0] = "topLevelClass";
                break;
            case 18:
                objArr[0] = "moduleName";
                break;
            default:
                objArr[0] = "packageName";
                break;
        }
        if (i == 8 || i == 9) {
            objArr[1] = "substringBeforeFirstDot";
        } else if (i == 16) {
            objArr[1] = "findClasses";
        } else if (i == 17) {
            objArr[1] = "getNonTrivialPackagePrefixes";
        } else if (i != 20) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreJavaFileManager";
        } else {
            objArr[1] = "findModules";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "findClass";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[2] = "findClassInClasspathRoot";
                break;
            case 7:
                objArr[2] = "substringBeforeFirstDot";
                break;
            case 8:
            case 9:
            case 16:
            case 17:
            case 20:
                break;
            case 10:
            case 11:
                objArr[2] = "findClassInPsiFile";
                break;
            case 12:
            case 13:
                objArr[2] = "findClassByTopLevelClass";
                break;
            case 14:
            case 15:
                objArr[2] = "findClasses";
                break;
            case 18:
            case 19:
                objArr[2] = "findModules";
                break;
            default:
                objArr[2] = "findPackage";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 8 && i != 9 && i != 16 && i != 17 && i != 20) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public CoreJavaFileManager(PsiManager psiManager) {
        this.myPsiManager = psiManager;
    }

    private List<VirtualFile> roots() {
        return this.myClasspath;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager
    public PsiPackage findPackage(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (findDirectoriesByPackageName(str).isEmpty()) {
            return null;
        }
        return new PsiPackageImpl(this.myPsiManager, str);
    }

    private List<VirtualFile> findDirectoriesByPackageName(String str) {
        ArrayList arrayList = new ArrayList();
        String strReplace = str.replace(".", "/");
        Iterator<VirtualFile> it = roots().iterator();
        while (it.hasNext()) {
            VirtualFile virtualFileFindFileByRelativePath = it.next().findFileByRelativePath(strReplace);
            if (virtualFileFindFileByRelativePath != null) {
                arrayList.add(virtualFileFindFileByRelativePath);
            }
        }
        return arrayList;
    }

    public PsiPackage getPackage(PsiDirectory psiDirectory) {
        String relativePath;
        VirtualFile virtualFile = psiDirectory.getVirtualFile();
        for (VirtualFile virtualFile2 : this.myClasspath) {
            if (VfsUtilCore.isAncestor(virtualFile2, virtualFile, false) && (relativePath = FileUtil.getRelativePath(virtualFile2.getPath(), virtualFile.getPath(), '/')) != null) {
                return new PsiPackageImpl(this.myPsiManager, relativePath.replace('/', '.'));
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager
    public PsiClass findClass(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(2);
        }
        Iterator<VirtualFile> it = roots().iterator();
        while (it.hasNext()) {
            PsiClass psiClassFindClassInClasspathRoot = findClassInClasspathRoot(str, it.next(), this.myPsiManager, globalSearchScope);
            if (psiClassFindClassInClasspathRoot != null) {
                return psiClassFindClassInClasspathRoot;
            }
        }
        return null;
    }

    public static PsiClass findClassInClasspathRoot(String str, VirtualFile virtualFile, PsiManager psiManager, GlobalSearchScope globalSearchScope) {
        VirtualFile virtualFileFindChild;
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(4);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(5);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(6);
        }
        VirtualFile virtualFile2 = virtualFile;
        while (true) {
            int iIndexOf = str.indexOf(46);
            if (iIndexOf < 0 || (virtualFileFindChild = virtualFile2.findChild(str.substring(0, iIndexOf))) == null) {
                break;
            }
            str = str.substring(iIndexOf + 1);
            virtualFile2 = virtualFileFindChild;
        }
        String strSubstringBeforeFirstDot = substringBeforeFirstDot(str);
        VirtualFile virtualFileFindChild2 = virtualFile2.findChild(strSubstringBeforeFirstDot + ".class");
        if (virtualFileFindChild2 == null) {
            virtualFileFindChild2 = virtualFile2.findChild(strSubstringBeforeFirstDot + SuffixConstants.SUFFIX_STRING_java);
        }
        if (virtualFileFindChild2 == null) {
            return null;
        }
        if (!virtualFileFindChild2.isValid()) {
            LOG.error("Invalid child of valid parent: " + virtualFileFindChild2.getPath() + "; " + virtualFile.isValid() + " path=" + virtualFile.getPath());
            return null;
        }
        if (!globalSearchScope.contains(virtualFileFindChild2)) {
            return null;
        }
        PsiFile psiFileFindFile = psiManager.findFile(virtualFileFindChild2);
        if (psiFileFindFile instanceof PsiClassOwner) {
            return findClassInPsiFile(str, (PsiClassOwner) psiFileFindFile);
        }
        return null;
    }

    private static String substringBeforeFirstDot(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        int iIndexOf = str.indexOf(46);
        if (iIndexOf < 0) {
            if (str == null) {
                $$$reportNull$$$0(8);
            }
            return str;
        }
        String strSubstring = str.substring(0, iIndexOf);
        if (strSubstring == null) {
            $$$reportNull$$$0(9);
        }
        return strSubstring;
    }

    private static PsiClass findClassInPsiFile(String str, PsiClassOwner psiClassOwner) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (psiClassOwner == null) {
            $$$reportNull$$$0(11);
        }
        for (PsiClass psiClass : psiClassOwner.getClasses()) {
            PsiClass psiClassFindClassByTopLevelClass = findClassByTopLevelClass(str, psiClass);
            if (psiClassFindClassByTopLevelClass != null) {
                return psiClassFindClassByTopLevelClass;
            }
        }
        return null;
    }

    private static PsiClass findClassByTopLevelClass(String str, PsiClass psiClass) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(13);
        }
        if (str.indexOf(46) < 0) {
            if (str.equals(psiClass.getMyName())) {
                return psiClass;
            }
            return null;
        }
        Iterator<String> it = StringUtil.split(str, ".").iterator();
        if (!it.hasNext() || !it.next().equals(psiClass.getMyName())) {
            return null;
        }
        while (it.hasNext()) {
            psiClass = psiClass.findInnerClassByName(it.next(), false);
            if (psiClass == null) {
                return null;
            }
        }
        return psiClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager
    public PsiClass[] findClasses(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(15);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<VirtualFile> it = roots().iterator();
        while (it.hasNext()) {
            PsiClass psiClassFindClassInClasspathRoot = findClassInClasspathRoot(str, it.next(), this.myPsiManager, globalSearchScope);
            if (psiClassFindClassInClasspathRoot != null) {
                arrayList.add(psiClassFindClassInClasspathRoot);
            }
        }
        PsiClass[] psiClassArr = (PsiClass[]) arrayList.toArray(PsiClass.EMPTY_ARRAY);
        if (psiClassArr == null) {
            $$$reportNull$$$0(16);
        }
        return psiClassArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager
    public Collection<String> getNonTrivialPackagePrefixes() {
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(17);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager
    public Collection<PsiJavaModule> findModules(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(19);
        }
        Set setEmptySet = Collections.emptySet();
        if (setEmptySet == null) {
            $$$reportNull$$$0(20);
        }
        return setEmptySet;
    }

    public void addToClasspath(VirtualFile virtualFile) {
        this.myClasspath.add(virtualFile);
    }
}
