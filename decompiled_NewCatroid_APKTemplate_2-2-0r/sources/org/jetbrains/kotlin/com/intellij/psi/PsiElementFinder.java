package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ProjectExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiElementFinder {
    public static final ProjectExtensionPointName<PsiElementFinder> EP = new ProjectExtensionPointName<>("org.jetbrains.kotlin.com.intellij.java.elementFinder");

    /* JADX WARN: Code duplicated, block: B:11:0x001e  */
    /* JADX WARN: Code duplicated, block: B:43:0x0082  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 6 && i != 10 && i != 26 && i != 16 && i != 17) {
            switch (i) {
                case 29:
                case 30:
                case 31:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 6 && i != 10 && i != 26 && i != 16 && i != 17) {
            switch (i) {
                case 29:
                case 30:
                case 31:
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
        switch (i) {
            case 1:
            case 4:
            case 8:
            case 11:
            case 13:
            case 18:
            case 21:
            case 24:
                objArr[0] = "psiPackage";
                break;
            case 2:
            case 5:
            case 7:
            case 9:
            case 12:
            case 14:
            case 19:
            case 22:
            case 25:
                objArr[0] = "scope";
                break;
            case 3:
            case 6:
            case 10:
            case 16:
            case 17:
            case 26:
            case 29:
            case 30:
            case 31:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiElementFinder";
                break;
            case 15:
            case 28:
                objArr[0] = "classes";
                break;
            case 20:
            case 23:
                objArr[0] = "consumer";
                break;
            case 27:
                objArr[0] = "className";
                break;
            default:
                objArr[0] = "qualifiedName";
                break;
        }
        if (i == 3) {
            objArr[1] = "getSubPackages";
        } else if (i == 6) {
            objArr[1] = "getClasses";
        } else if (i == 10) {
            objArr[1] = "getPackageFiles";
        } else if (i == 26) {
            objArr[1] = "getClasses";
        } else if (i != 16 && i != 17) {
            switch (i) {
                case 29:
                case 30:
                case 31:
                    objArr[1] = "filterByName";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiElementFinder";
                    break;
            }
        } else {
            objArr[1] = "getClassNames";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "getSubPackages";
                break;
            case 3:
            case 6:
            case 10:
            case 16:
            case 17:
            case 26:
            case 29:
            case 30:
            case 31:
                break;
            case 4:
            case 5:
            case 24:
            case 25:
                objArr[2] = "getClasses";
                break;
            case 7:
                objArr[2] = "getClassesFilter";
                break;
            case 8:
            case 9:
                objArr[2] = "getPackageFiles";
                break;
            case 11:
            case 12:
                objArr[2] = "getPackageFilesFilter";
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "getClassNames";
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                objArr[2] = "processPackageDirectories";
                break;
            case 27:
            case 28:
                objArr[2] = "filterByName";
                break;
            default:
                objArr[2] = "findPackage";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 6 && i != 10 && i != 26 && i != 16 && i != 17) {
            switch (i) {
                case 29:
                case 30:
                case 31:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public abstract PsiClass findClass(String str, GlobalSearchScope globalSearchScope);

    public abstract PsiClass[] findClasses(String str, GlobalSearchScope globalSearchScope);

    public PsiPackage findPackage(String str) {
        if (str != null) {
            return null;
        }
        $$$reportNull$$$0(0);
        return null;
    }

    public Predicate<PsiClass> getClassesFilter(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope != null) {
            return null;
        }
        $$$reportNull$$$0(7);
        return null;
    }

    public Condition<PsiFile> getPackageFilesFilter(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(11);
        }
        if (globalSearchScope != null) {
            return null;
        }
        $$$reportNull$$$0(12);
        return null;
    }

    public boolean processPackageDirectories(PsiPackage psiPackage, GlobalSearchScope globalSearchScope, Processor<? super PsiDirectory> processor, boolean z) {
        if (psiPackage == null) {
            $$$reportNull$$$0(21);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(22);
        }
        if (processor != null) {
            return true;
        }
        $$$reportNull$$$0(23);
        return true;
    }

    public PsiPackage[] getSubPackages(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(1);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(2);
        }
        PsiPackage[] psiPackageArr = PsiPackage.EMPTY_ARRAY;
        if (psiPackageArr == null) {
            $$$reportNull$$$0(3);
        }
        return psiPackageArr;
    }

    public PsiClass[] getClasses(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(4);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(5);
        }
        PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
        if (psiClassArr == null) {
            $$$reportNull$$$0(6);
        }
        return psiClassArr;
    }

    public PsiFile[] getPackageFiles(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(8);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(9);
        }
        PsiFile[] psiFileArr = PsiFile.EMPTY_ARRAY;
        if (psiFileArr == null) {
            $$$reportNull$$$0(10);
        }
        return psiFileArr;
    }

    public Set<String> getClassNames(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(13);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(14);
        }
        return getClassNames(getClasses(psiPackage, globalSearchScope));
    }

    protected static Set<String> getClassNames(PsiClass[] psiClassArr) {
        if (psiClassArr == null) {
            $$$reportNull$$$0(15);
        }
        if (psiClassArr.length == 0) {
            Set<String> setEmptySet = Collections.emptySet();
            if (setEmptySet == null) {
                $$$reportNull$$$0(16);
            }
            return setEmptySet;
        }
        HashSet hashSet = new HashSet(psiClassArr.length);
        for (PsiClass psiClass : psiClassArr) {
            ContainerUtil.addIfNotNull(hashSet, psiClass.getName());
        }
        return hashSet;
    }

    public boolean processPackageDirectories(PsiPackage psiPackage, GlobalSearchScope globalSearchScope, Processor<? super PsiDirectory> processor) {
        if (psiPackage == null) {
            $$$reportNull$$$0(18);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(19);
        }
        if (processor == null) {
            $$$reportNull$$$0(20);
        }
        return processPackageDirectories(psiPackage, globalSearchScope, processor, false);
    }

    public PsiClass[] getClasses(String str, PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(24);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(25);
        }
        PsiClass[] classes = getClasses(psiPackage, globalSearchScope);
        if (str != null) {
            return filterByName(str, classes);
        }
        if (classes == null) {
            $$$reportNull$$$0(26);
        }
        return classes;
    }

    public static PsiClass[] filterByName(String str, PsiClass[] psiClassArr) {
        if (str == null) {
            $$$reportNull$$$0(27);
        }
        if (psiClassArr == null) {
            $$$reportNull$$$0(28);
        }
        if (psiClassArr.length == 0) {
            PsiClass[] psiClassArr2 = PsiClass.EMPTY_ARRAY;
            if (psiClassArr2 == null) {
                $$$reportNull$$$0(29);
            }
            return psiClassArr2;
        }
        if (psiClassArr.length == 1) {
            if (!str.equals(psiClassArr[0].getName())) {
                psiClassArr = PsiClass.EMPTY_ARRAY;
            }
            if (psiClassArr == null) {
                $$$reportNull$$$0(30);
            }
            return psiClassArr;
        }
        SmartList smartList = new SmartList();
        for (PsiClass psiClass : psiClassArr) {
            if (str.equals(psiClass.getName())) {
                smartList.add(psiClass);
            }
        }
        PsiClass[] psiClassArr3 = smartList.isEmpty() ? PsiClass.EMPTY_ARRAY : (PsiClass[]) smartList.toArray(PsiClass.EMPTY_ARRAY);
        if (psiClassArr3 == null) {
            $$$reportNull$$$0(31);
        }
        return psiClassArr3;
    }
}
