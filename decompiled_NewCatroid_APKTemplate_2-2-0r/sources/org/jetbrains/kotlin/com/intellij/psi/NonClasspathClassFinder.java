package org.jetbrains.kotlin.com.intellij.psi;

import com.reandroid.apk.ApkUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointUtil;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.impl.PackageDirectoryCache;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcher;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.BulkFileListener;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl;
import org.jetbrains.kotlin.com.intellij.psi.search.EverythingGlobalScope;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.NonClasspathDirectoriesScope;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.CommonProcessors;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class NonClasspathClassFinder extends PsiElementFinder {
    private volatile PackageDirectoryCache myCache;
    private final String[] myFileExtensions;
    private final PsiManager myManager;
    protected final Project myProject;
    private static final Logger LOG = Logger.getInstance((Class<?>) NonClasspathClassFinder.class);
    private static final EverythingGlobalScope ALL_SCOPE = new EverythingGlobalScope();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 7 || i == 10 || i == 24 || i == 20 || i == 21 || i == 27 || i == 28) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 7 || i == 10 || i == 24 || i == 20 || i == 21 || i == 27 || i == 28) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "fileExtensions";
                break;
            case 2:
            case 7:
            case 10:
            case 20:
            case 21:
            case 24:
            case 27:
            case 28:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/NonClasspathClassFinder";
                break;
            case 3:
            case 11:
            case 15:
            case 22:
                objArr[0] = "qualifiedName";
                break;
            case 4:
            case 6:
            case 9:
            case 13:
            case 16:
            case 19:
            case 23:
                objArr[0] = "scope";
                break;
            case 5:
            case 8:
            case 12:
            case 18:
                objArr[0] = "psiPackage";
                break;
            case 14:
                objArr[0] = "consumer";
                break;
            case 17:
                objArr[0] = "processor";
                break;
            case 25:
            default:
                objArr[0] = "project";
                break;
            case 26:
                objArr[0] = ApkUtil.DEF_MODULE_NAME;
                break;
            case 29:
                objArr[0] = "root";
                break;
            case 30:
                objArr[0] = "relPath";
                break;
            case 31:
                objArr[0] = "extensions";
                break;
        }
        if (i == 2) {
            objArr[1] = "getCache";
        } else if (i == 7) {
            objArr[1] = "getClasses";
        } else if (i == 10) {
            objArr[1] = "getClassNames";
        } else if (i == 24) {
            objArr[1] = "findClasses";
        } else if (i == 20 || i == 21) {
            objArr[1] = "getSubPackages";
        } else if (i == 27 || i == 28) {
            objArr[1] = "addNonClasspathScope";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/NonClasspathClassFinder";
        }
        switch (i) {
            case 2:
            case 7:
            case 10:
            case 20:
            case 21:
            case 24:
            case 27:
            case 28:
                break;
            case 3:
            case 4:
                objArr[2] = "findClass";
                break;
            case 5:
            case 6:
                objArr[2] = "getClasses";
                break;
            case 8:
            case 9:
                objArr[2] = "getClassNames";
                break;
            case 11:
                objArr[2] = "findPackage";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "processPackageDirectories";
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "processDirectories";
                break;
            case 18:
            case 19:
                objArr[2] = "getSubPackages";
                break;
            case 22:
            case 23:
                objArr[2] = "findClasses";
                break;
            case 25:
            case 26:
                objArr[2] = "addNonClasspathScope";
                break;
            case 29:
            case 30:
            case 31:
                objArr[2] = "findChild";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 7 && i != 10 && i != 24 && i != 20 && i != 21 && i != 27 && i != 28) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected abstract List<VirtualFile> calcClassRoots();

    public NonClasspathClassFinder(Project project, String... strArr) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (strArr == null) {
            $$$reportNull$$$0(1);
        }
        this.myProject = project;
        this.myManager = PsiManager.getInstance(project);
        this.myFileExtensions = (String[]) ArrayUtil.append(strArr, "class");
        Disposable disposableCreateExtensionDisposable = ExtensionPointUtil.createExtensionDisposable(this, EP.getPoint(project));
        project.getMessageBus().connect(disposableCreateExtensionDisposable).subscribe(VirtualFileManager.VFS_CHANGES, new BulkFileListener() { // from class: org.jetbrains.kotlin.com.intellij.psi.NonClasspathClassFinder.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "events", "org/jetbrains/kotlin/com/intellij/psi/NonClasspathClassFinder$1", "after"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.BulkFileListener
            public void after(List<? extends VFileEvent> list) {
                if (list == null) {
                    $$$reportNull$$$0(0);
                }
                NonClasspathClassFinder.this.m6523x109f6334();
            }
        });
        LowMemoryWatcher.register(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.NonClasspathClassFinder$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6523x109f6334();
            }
        }, disposableCreateExtensionDisposable);
    }

    protected PackageDirectoryCache getCache(GlobalSearchScope globalSearchScope) {
        PackageDirectoryCache packageDirectoryCacheCreateCache = this.myCache;
        if (packageDirectoryCacheCreateCache == null) {
            List<VirtualFile> listCalcClassRoots = calcClassRoots();
            List listFilter = ContainerUtil.filter(listCalcClassRoots, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.NonClasspathClassFinder$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return NonClasspathClassFinder.lambda$getCache$1((VirtualFile) obj);
                }
            });
            if (!listFilter.isEmpty()) {
                listCalcClassRoots = ContainerUtil.filter(listCalcClassRoots, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.NonClasspathClassFinder$$ExternalSyntheticLambda3
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                    public final boolean value(Object obj) {
                        return ((VirtualFile) obj).isValid();
                    }
                });
                PluginException.logPluginError(LOG, "Invalid roots returned by " + getClass().getName() + ": " + listFilter, null, getClass());
            }
            packageDirectoryCacheCreateCache = PackageDirectoryCache.createCache(listCalcClassRoots);
            this.myCache = packageDirectoryCacheCreateCache;
        }
        if (packageDirectoryCacheCreateCache == null) {
            $$$reportNull$$$0(2);
        }
        return packageDirectoryCacheCreateCache;
    }

    static /* synthetic */ boolean lambda$getCache$1(VirtualFile virtualFile) {
        return !virtualFile.isValid();
    }

    /* JADX INFO: renamed from: clearCache, reason: merged with bridge method [inline-methods] */
    public void m6523x109f6334() {
        this.myCache = null;
    }

    protected List<VirtualFile> getClassRoots(GlobalSearchScope globalSearchScope) {
        return getCache(globalSearchScope).getDirectoriesByPackageName("");
    }

    public List<VirtualFile> getClassRoots() {
        return getClassRoots(ALL_SCOPE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass findClass(final String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(4);
        }
        final Ref refCreate = Ref.create();
        processDirectories(StringUtil.getPackageName(str), globalSearchScope, new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.NonClasspathClassFinder$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return this.f$0.m6520xe212b315(str, refCreate, (VirtualFile) obj);
            }
        });
        return (PsiClass) refCreate.get();
    }

    /* JADX INFO: renamed from: lambda$findClass$2$org-jetbrains-kotlin-com-intellij-psi-NonClasspathClassFinder, reason: not valid java name */
    /* synthetic */ boolean m6520xe212b315(String str, Ref ref, VirtualFile virtualFile) {
        VirtualFile virtualFileFindChild = findChild(virtualFile, StringUtil.getShortName(str), this.myFileExtensions);
        PsiFile psiFileFindFile = virtualFileFindChild == null ? null : this.myManager.findFile(virtualFileFindChild);
        if (psiFileFindFile instanceof PsiClassOwner) {
            PsiClass[] classes = ((PsiClassOwner) psiFileFindFile).getClasses();
            if (classes.length == 1) {
                ref.set(classes[0]);
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass[] getClasses(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(5);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(6);
        }
        final ArrayList arrayList = new ArrayList();
        processDirectories(psiPackage.getQualifiedName(), globalSearchScope, new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.NonClasspathClassFinder$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return this.f$0.m6522xbd0971a1(arrayList, (VirtualFile) obj);
            }
        });
        PsiClass[] psiClassArr = (PsiClass[]) arrayList.toArray(PsiClass.EMPTY_ARRAY);
        if (psiClassArr == null) {
            $$$reportNull$$$0(7);
        }
        return psiClassArr;
    }

    /* JADX INFO: renamed from: lambda$getClasses$3$org-jetbrains-kotlin-com-intellij-psi-NonClasspathClassFinder, reason: not valid java name */
    /* synthetic */ boolean m6522xbd0971a1(List list, VirtualFile virtualFile) {
        for (VirtualFile virtualFile2 : virtualFile.getChildren()) {
            if (!virtualFile2.isDirectory() && ArrayUtil.contains(virtualFile2.getExtension(), this.myFileExtensions)) {
                PsiFile psiFileFindFile = this.myManager.findFile(virtualFile2);
                if (psiFileFindFile instanceof PsiClassOwner) {
                    ContainerUtil.addAll(list, ((PsiClassOwner) psiFileFindFile).getClasses());
                }
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public Set<String> getClassNames(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(8);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(9);
        }
        final HashSet hashSet = new HashSet();
        processDirectories(psiPackage.getQualifiedName(), globalSearchScope, new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.NonClasspathClassFinder$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return this.f$0.m6521x2ec94b7e(hashSet, (VirtualFile) obj);
            }
        });
        return hashSet;
    }

    /* JADX INFO: renamed from: lambda$getClassNames$4$org-jetbrains-kotlin-com-intellij-psi-NonClasspathClassFinder, reason: not valid java name */
    /* synthetic */ boolean m6521x2ec94b7e(Set set, VirtualFile virtualFile) {
        for (VirtualFile virtualFile2 : virtualFile.getChildren()) {
            if (!virtualFile2.isDirectory() && ArrayUtil.contains(virtualFile2.getExtension(), this.myFileExtensions)) {
                set.add(virtualFile2.getNameWithoutExtension());
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiPackage findPackage(String str) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        CommonProcessors.FindFirstProcessor findFirstProcessor = new CommonProcessors.FindFirstProcessor();
        processDirectories(str, ALL_SCOPE, findFirstProcessor);
        if (findFirstProcessor.getFoundValue() != 0) {
            return createPackage(str);
        }
        return null;
    }

    private PsiPackageImpl createPackage(String str) {
        return new PsiPackageImpl(this.myManager, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public boolean processPackageDirectories(final PsiPackage psiPackage, GlobalSearchScope globalSearchScope, final Processor<? super PsiDirectory> processor, boolean z) {
        if (psiPackage == null) {
            $$$reportNull$$$0(12);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(13);
        }
        if (processor == null) {
            $$$reportNull$$$0(14);
        }
        return processDirectories(psiPackage.getQualifiedName(), globalSearchScope, new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.NonClasspathClassFinder$$ExternalSyntheticLambda7
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return NonClasspathClassFinder.lambda$processPackageDirectories$5(psiPackage, processor, (VirtualFile) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$processPackageDirectories$5(PsiPackage psiPackage, Processor processor, VirtualFile virtualFile) {
        PsiDirectory psiDirectoryFindDirectory = psiPackage.getManager().findDirectory(virtualFile);
        return psiDirectoryFindDirectory == null || processor.process(psiDirectoryFindDirectory);
    }

    private boolean processDirectories(String str, final GlobalSearchScope globalSearchScope, final Processor<? super VirtualFile> processor) {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(16);
        }
        if (processor == null) {
            $$$reportNull$$$0(17);
        }
        if (globalSearchScope instanceof EverythingGlobalScope) {
            globalSearchScope = ALL_SCOPE;
        }
        return ContainerUtil.process((List) getCache(globalSearchScope).getDirectoriesByPackageName(str), new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.NonClasspathClassFinder$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return NonClasspathClassFinder.lambda$processDirectories$6(globalSearchScope, processor, (VirtualFile) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$processDirectories$6(GlobalSearchScope globalSearchScope, Processor processor, VirtualFile virtualFile) {
        return !globalSearchScope.contains(virtualFile) || processor.process(virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiPackage[] getSubPackages(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(18);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(19);
        }
        String qualifiedName = psiPackage.getQualifiedName();
        Set<String> subpackageNames = getCache(globalSearchScope).getSubpackageNames(qualifiedName, globalSearchScope);
        if (subpackageNames.isEmpty()) {
            PsiPackage[] subPackages = super.getSubPackages(psiPackage, globalSearchScope);
            if (subPackages == null) {
                $$$reportNull$$$0(20);
            }
            return subPackages;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : subpackageNames) {
            if (!qualifiedName.isEmpty()) {
                str = qualifiedName + "." + str;
            }
            arrayList.add(createPackage(str));
        }
        PsiPackage[] psiPackageArr = (PsiPackage[]) arrayList.toArray(PsiPackage.EMPTY_ARRAY);
        if (psiPackageArr == null) {
            $$$reportNull$$$0(21);
        }
        return psiPackageArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass[] findClasses(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(23);
        }
        PsiClass psiClassFindClass = findClass(str, globalSearchScope);
        PsiClass[] psiClassArr = psiClassFindClass == null ? PsiClass.EMPTY_ARRAY : new PsiClass[]{psiClassFindClass};
        if (psiClassArr == null) {
            $$$reportNull$$$0(24);
        }
        return psiClassArr;
    }

    public static GlobalSearchScope addNonClasspathScope(Project project, GlobalSearchScope globalSearchScope) {
        GlobalSearchScope globalSearchScopeCompose;
        if (project == null) {
            $$$reportNull$$$0(25);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(26);
        }
        SmartList smartList = new SmartList();
        for (PsiElementFinder psiElementFinder : EP.getExtensions(project)) {
            if ((psiElementFinder instanceof NonClasspathClassFinder) && (globalSearchScopeCompose = NonClasspathDirectoriesScope.compose(((NonClasspathClassFinder) psiElementFinder).getClassRoots())) != GlobalSearchScope.EMPTY_SCOPE) {
                smartList.add(globalSearchScopeCompose);
            }
        }
        if (smartList.isEmpty()) {
            if (globalSearchScope == null) {
                $$$reportNull$$$0(27);
            }
            return globalSearchScope;
        }
        GlobalSearchScope globalSearchScopeUnion = GlobalSearchScope.union((GlobalSearchScope[]) ArrayUtil.prepend(globalSearchScope, (GlobalSearchScope[]) smartList.toArray(GlobalSearchScope.EMPTY_ARRAY)));
        if (globalSearchScopeUnion == null) {
            $$$reportNull$$$0(28);
        }
        return globalSearchScopeUnion;
    }

    public PsiManager getPsiManager() {
        return this.myManager;
    }

    private static VirtualFile findChild(VirtualFile virtualFile, String str, String[] strArr) {
        if (virtualFile == null) {
            $$$reportNull$$$0(29);
        }
        if (str == null) {
            $$$reportNull$$$0(30);
        }
        if (strArr == null) {
            $$$reportNull$$$0(31);
        }
        VirtualFile virtualFileFindChild = null;
        for (String str2 : strArr) {
            virtualFileFindChild = virtualFile.findChild(str + '.' + str2);
            if (virtualFileFindChild != null) {
                break;
            }
        }
        return virtualFileFindChild;
    }
}
