package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadActionProcessor;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbAware;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.roots.PackageIndex;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwnerEx;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiClassUtil;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiElementFinderImpl extends PsiElementFinder implements DumbAware {
    private final JavaFileManager myFileManager;
    private final Project myProject;

    /* JADX WARN: Code duplicated, block: B:39:0x006f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 9 || i == 12 || i == 19 || i == 15 || i == 16) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 9 || i == 12 || i == 19 || i == 15 || i == 16) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 8:
            case 11:
            case 14:
            case 18:
            case 21:
                objArr[0] = "scope";
                break;
            case 2:
            case 6:
            default:
                objArr[0] = "qualifiedName";
                break;
            case 4:
            case 5:
            case 9:
            case 12:
            case 15:
            case 16:
            case 19:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiElementFinderImpl";
                break;
            case 7:
            case 10:
            case 13:
            case 17:
            case 20:
                objArr[0] = "psiPackage";
                break;
            case 22:
                objArr[0] = "consumer";
                break;
        }
        if (i == 4 || i == 5) {
            objArr[1] = "findClasses";
        } else if (i == 9) {
            objArr[1] = "getSubPackages";
        } else if (i == 12) {
            objArr[1] = "getClasses";
        } else if (i == 19) {
            objArr[1] = "getClassNames";
        } else if (i == 15 || i == 16) {
            objArr[1] = "getClasses";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiElementFinderImpl";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "findClasses";
                break;
            case 4:
            case 5:
            case 9:
            case 12:
            case 15:
            case 16:
            case 19:
                break;
            case 6:
                objArr[2] = "findPackage";
                break;
            case 7:
            case 8:
                objArr[2] = "getSubPackages";
                break;
            case 10:
            case 11:
            case 13:
            case 14:
                objArr[2] = "getClasses";
                break;
            case 17:
            case 18:
                objArr[2] = "getClassNames";
                break;
            case 20:
            case 21:
            case 22:
                objArr[2] = "processPackageDirectories";
                break;
            default:
                objArr[2] = "findClass";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 9 && i != 12 && i != 19 && i != 15 && i != 16) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiElementFinderImpl(Project project) {
        this.myProject = project;
        this.myFileManager = JavaFileManager.getInstance(project);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass findClass(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(1);
        }
        if (skipIndices()) {
            return null;
        }
        return this.myFileManager.findClass(str, globalSearchScope);
    }

    private boolean skipIndices() {
        DumbService dumbService = DumbService.getInstance(this.myProject);
        return dumbService.isDumb() && dumbService.isAlternativeResolveEnabled();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass[] findClasses(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(3);
        }
        if (skipIndices()) {
            PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
            if (psiClassArr == null) {
                $$$reportNull$$$0(4);
            }
            return psiClassArr;
        }
        PsiClass[] psiClassArrFindClasses = this.myFileManager.findClasses(str, globalSearchScope);
        if (psiClassArrFindClasses == null) {
            $$$reportNull$$$0(5);
        }
        return psiClassArrFindClasses;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiPackage findPackage(String str) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return this.myFileManager.findPackage(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiPackage[] getSubPackages(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(7);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(8);
        }
        HashMap map = new HashMap();
        String qualifiedName = psiPackage.getQualifiedName();
        for (PsiDirectory psiDirectory : psiPackage.getDirectories(globalSearchScope)) {
            for (PsiDirectory psiDirectory2 : psiDirectory.getSubdirectories()) {
                PsiPackage psiPackage2 = JavaDirectoryService.getInstance().getPackage(psiDirectory2);
                if (psiPackage2 != null) {
                    String qualifiedName2 = psiPackage2.getQualifiedName();
                    if (qualifiedName2.startsWith(qualifiedName) && !map.containsKey(qualifiedName2)) {
                        map.put(psiPackage2.getQualifiedName(), psiPackage2);
                    }
                }
            }
        }
        map.remove(qualifiedName);
        PsiPackage[] psiPackageArr = (PsiPackage[]) map.values().toArray(PsiPackage.EMPTY_ARRAY);
        if (psiPackageArr == null) {
            $$$reportNull$$$0(9);
        }
        return psiPackageArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass[] getClasses(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(10);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(11);
        }
        PsiClass[] classes = getClasses(null, psiPackage, globalSearchScope);
        if (classes == null) {
            $$$reportNull$$$0(12);
        }
        return classes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass[] getClasses(String str, PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(13);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(14);
        }
        String qualifiedName = psiPackage.getQualifiedName();
        ArrayList arrayList = null;
        for (PsiDirectory psiDirectory : psiPackage.getDirectories(globalSearchScope)) {
            PsiClass[] classes = JavaDirectoryService.getInstance().getClasses(psiDirectory);
            if (classes.length != 0) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                for (PsiClass psiClass : classes) {
                    String qualifiedName2 = psiClass.getQualifiedName();
                    if (qualifiedName2 != null) {
                        qualifiedName2 = StringUtil.getPackageName(qualifiedName2);
                    }
                    if (Comparing.strEqual(qualifiedName2, qualifiedName) && (str == null || str.equals(psiClass.getMyName()))) {
                        arrayList.add(psiClass);
                    }
                }
            }
        }
        if (arrayList == null) {
            PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
            if (psiClassArr == null) {
                $$$reportNull$$$0(15);
            }
            return psiClassArr;
        }
        if (arrayList.size() > 1) {
            ContainerUtil.quickSort(arrayList, PsiClassUtil.createScopeComparator(globalSearchScope));
        }
        PsiClass[] psiClassArr2 = (PsiClass[]) arrayList.toArray(PsiClass.EMPTY_ARRAY);
        if (psiClassArr2 == null) {
            $$$reportNull$$$0(16);
        }
        return psiClassArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public Set<String> getClassNames(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        VirtualFile virtualFile;
        if (psiPackage == null) {
            $$$reportNull$$$0(17);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(18);
        }
        FileIndexFacade fileIndexFacade = FileIndexFacade.getInstance(this.myProject);
        Set<String> setEmptySet = null;
        for (PsiDirectory psiDirectory : psiPackage.getDirectories(globalSearchScope)) {
            for (PsiFile psiFile : psiDirectory.getFiles()) {
                if ((psiFile instanceof PsiClassOwner) && psiFile.getViewProvider().getLanguages().size() == 1 && ((virtualFile = psiFile.getVirtualFile()) == null || (psiFile instanceof PsiCompiledElement) || fileIndexFacade.isInSourceContent(virtualFile) || (globalSearchScope.isForceSearchingInLibrarySources() && StubTreeLoader.getInstance().canHaveStub(virtualFile)))) {
                    Set<String> classNames = psiFile instanceof PsiClassOwnerEx ? ((PsiClassOwnerEx) psiFile).getClassNames() : getClassNames(((PsiClassOwner) psiFile).getClasses());
                    if (!classNames.isEmpty()) {
                        if (setEmptySet == null) {
                            setEmptySet = new HashSet<>();
                        }
                        setEmptySet.addAll(classNames);
                    }
                }
            }
        }
        if (setEmptySet == null) {
            setEmptySet = Collections.emptySet();
        }
        if (setEmptySet == null) {
            $$$reportNull$$$0(19);
        }
        return setEmptySet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public boolean processPackageDirectories(PsiPackage psiPackage, final GlobalSearchScope globalSearchScope, final Processor<? super PsiDirectory> processor, boolean z) {
        if (psiPackage == null) {
            $$$reportNull$$$0(20);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(21);
        }
        if (processor == null) {
            $$$reportNull$$$0(22);
        }
        final PsiManager psiManager = PsiManager.getInstance(this.myProject);
        return PackageIndex.getInstance(this.myProject).getDirsByPackageName(psiPackage.getQualifiedName(), z).forEach(new ReadActionProcessor<VirtualFile>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementFinderImpl.1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.application.ReadActionProcessor
            public boolean processInReadAction(VirtualFile virtualFile) {
                PsiDirectory psiDirectoryFindDirectory;
                return !globalSearchScope.contains(virtualFile) || (psiDirectoryFindDirectory = psiManager.findDirectory(virtualFile)) == null || processor.process(psiDirectoryFindDirectory);
            }
        });
    }
}
