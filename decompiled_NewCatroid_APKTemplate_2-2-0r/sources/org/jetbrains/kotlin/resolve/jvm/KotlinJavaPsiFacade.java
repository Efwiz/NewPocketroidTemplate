package org.jetbrains.kotlin.resolve.jvm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.asJava.KtLightClassMarker;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbAware;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.PackageIndex;
import org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcher;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.BulkFileListener;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileCopyEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileCreateEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileDeleteEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileMoveEvent;
import org.jetbrains.kotlin.com.intellij.psi.NonClasspathClassFinder;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementFinderImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.CommonProcessors;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.Query;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.impl.JavaClassImpl;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;

/* JADX INFO: loaded from: classes6.dex */
public class KotlinJavaPsiFacade implements Disposable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final PsiPackage NULL_PACKAGE = new PsiPackageImpl(null, "NULL_PACKAGE");
    private volatile KotlinPsiElementFinderWrapper[] elementFinders;
    private final LightModifierList emptyModifierList;
    private volatile NotFoundPackagesCachingStrategy notFoundPackagesCachingStrategy;
    private volatile PackageCache packageCache;
    private final Project project;

    interface KotlinPsiElementFinderWrapper {
        PsiClass findClass(String str, GlobalSearchScope globalSearchScope);

        PsiClass[] findClasses(String str, GlobalSearchScope globalSearchScope);

        PsiPackage findPackage(String str, GlobalSearchScope globalSearchScope);

        boolean isSameResultForAnyScope();
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001c  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 6 && i != 7 && i != 10 && i != 22 && i != 25 && i != 26) {
            switch (i) {
                case 15:
                case 16:
                case 17:
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
        if (i != 6 && i != 7 && i != 10 && i != 22 && i != 25 && i != 26) {
            switch (i) {
                case 15:
                case 16:
                case 17:
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
        switch (i) {
            case 1:
            case 21:
                objArr[0] = "project";
                break;
            case 2:
            case 4:
                objArr[0] = "request";
                break;
            case 3:
            case 5:
            case 12:
            case 14:
                objArr[0] = "scope";
                break;
            case 6:
            case 7:
            case 10:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 22:
            case 25:
            case 26:
                objArr[0] = "org/jetbrains/kotlin/resolve/jvm/KotlinJavaPsiFacade";
                break;
            case 8:
                objArr[0] = "classId";
                break;
            case 9:
                objArr[0] = "psiClass";
                break;
            case 11:
                objArr[0] = "packageFqName";
                break;
            case 13:
            case 23:
            case 24:
                objArr[0] = "qualifiedName";
                break;
            default:
                objArr[0] = "psiPackage";
                break;
        }
        if (i == 6 || i == 7) {
            objArr[1] = "findClasses";
        } else if (i == 10) {
            objArr[1] = "createJavaClass";
        } else if (i == 22) {
            objArr[1] = "findJavaFileManager";
        } else if (i == 25) {
            objArr[1] = "filteredFinders";
        } else if (i != 26) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                case 18:
                    objArr[1] = "findClassesInDumbMode";
                    break;
                case 19:
                    objArr[1] = "finders";
                    break;
                case 20:
                    objArr[1] = "calcFinders";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/resolve/jvm/KotlinJavaPsiFacade";
                    break;
            }
        } else {
            objArr[1] = "getProject";
        }
        switch (i) {
            case 1:
                objArr[2] = "<init>";
                break;
            case 2:
            case 3:
                objArr[2] = "findClass";
                break;
            case 4:
            case 5:
                objArr[2] = "findClasses";
                break;
            case 6:
            case 7:
            case 10:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 22:
            case 25:
            case 26:
                break;
            case 8:
            case 9:
                objArr[2] = "createJavaClass";
                break;
            case 11:
            case 12:
                objArr[2] = "knownClassNamesInPackage";
                break;
            case 13:
            case 14:
                objArr[2] = "findClassesInDumbMode";
                break;
            case 21:
                objArr[2] = "findJavaFileManager";
                break;
            case 23:
                objArr[2] = "findPackage";
                break;
            case 24:
                objArr[2] = "certainlyDoesNotExist";
                break;
            default:
                objArr[2] = "unwrap";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 7 && i != 10 && i != 22 && i != 25 && i != 26) {
            switch (i) {
                case 15:
                case 16:
                case 17:
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

    private static class PackageCache {
        final ConcurrentMap<String, Boolean> hasPackageInAllScopeCache;
        final ConcurrentMap<GlobalSearchScope, ConcurrentMap<String, PsiPackage>> packageInLibScopeCache;
        final ConcurrentMap<GlobalSearchScope, ConcurrentMap<String, PsiPackage>> packageInScopeCache;

        private PackageCache() {
            this.packageInLibScopeCache = new ConcurrentHashMap();
            this.packageInScopeCache = new ConcurrentHashMap();
            this.hasPackageInAllScopeCache = new ConcurrentHashMap();
        }

        void clear() {
            this.packageInScopeCache.clear();
            this.hasPackageInAllScopeCache.clear();
        }
    }

    private static PsiPackage unwrap(PsiPackage psiPackage) {
        if (psiPackage == null) {
            $$$reportNull$$$0(0);
        }
        if (psiPackage == NULL_PACKAGE) {
            return null;
        }
        return psiPackage;
    }

    public static KotlinJavaPsiFacade getInstance(Project project) {
        return (KotlinJavaPsiFacade) project.getService(KotlinJavaPsiFacade.class);
    }

    public KotlinJavaPsiFacade(Project project) {
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        this.notFoundPackagesCachingStrategy = NotFoundPackagesCachingStrategy.Default.INSTANCE;
        this.project = project;
        this.emptyModifierList = new LightModifierList(PsiManager.getInstance(project), KotlinLanguage.INSTANCE, new String[0]);
        LowMemoryWatcher.register(new Runnable() { // from class: org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.clearPackageCaches();
            }
        }, this);
        MessageBusConnection messageBusConnectionConnect = project.getMessageBus().connect(this);
        messageBusConnectionConnect.subscribe(VirtualFileManager.VFS_CHANGES, new BulkFileListener() { // from class: org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "events", "org/jetbrains/kotlin/resolve/jvm/KotlinJavaPsiFacade$1", "after"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.BulkFileListener
            public void after(List<? extends VFileEvent> list) {
                if (list == null) {
                    $$$reportNull$$$0(0);
                }
                boolean z = false;
                for (VFileEvent vFileEvent : list) {
                    VirtualFile file = vFileEvent.getFile();
                    z = ((vFileEvent instanceof VFileCreateEvent) && ((VFileCreateEvent) vFileEvent).isDirectory()) || (file != null && file.isDirectory() && ((vFileEvent instanceof VFileDeleteEvent) || (vFileEvent instanceof VFileMoveEvent) || (vFileEvent instanceof VFileCopyEvent)));
                    if (z) {
                        break;
                    }
                }
                if (z) {
                    KotlinJavaPsiFacade.this.clearPackageCaches(false);
                }
            }
        });
        final PsiModificationTracker modificationTracker = PsiManager.getInstance(project).getModificationTracker();
        messageBusConnectionConnect.subscribe(PsiModificationTracker.TOPIC, new PsiModificationTracker.Listener() { // from class: org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.2
            private long lastTimeSeen = -1;

            @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker.Listener
            public void modificationCountChanged() {
                long modificationCount = modificationTracker.getModificationCount();
                if (this.lastTimeSeen != modificationCount) {
                    this.lastTimeSeen = modificationCount;
                    KotlinJavaPsiFacade.this.clearPackageCaches(false);
                }
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        clearPackageCaches();
    }

    public void clearPackageCaches() {
        clearPackageCaches(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearPackageCaches(boolean z) {
        this.elementFinders = null;
        if (z) {
            this.packageCache = null;
        } else {
            obtainPackageCache().clear();
        }
    }

    public void setNotFoundPackagesCachingStrategy(NotFoundPackagesCachingStrategy notFoundPackagesCachingStrategy) {
        this.notFoundPackagesCachingStrategy = notFoundPackagesCachingStrategy;
    }

    public LightModifierList getEmptyModifierList() {
        return this.emptyModifierList;
    }

    public JavaClass findClass(JavaClassFinder.Request request, GlobalSearchScope globalSearchScope) {
        if (request == null) {
            $$$reportNull$$$0(2);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(3);
        }
        if (globalSearchScope == GlobalSearchScope.EMPTY_SCOPE) {
            return null;
        }
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        ClassId classId = request.getClassId();
        String strAsString = classId.asSingleFqName().asString();
        if (shouldUseSlowResolve()) {
            PsiClass[] psiClassArrFindClassesInDumbMode = findClassesInDumbMode(strAsString, globalSearchScope);
            if (psiClassArrFindClassesInDumbMode.length != 0) {
                return createJavaClass(classId, psiClassArrFindClassesInDumbMode[0]);
            }
            return null;
        }
        for (KotlinPsiElementFinderWrapper kotlinPsiElementFinderWrapper : finders()) {
            ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
            if (kotlinPsiElementFinderWrapper instanceof CliFinder) {
                JavaClass javaClassFindClass = ((CliFinder) kotlinPsiElementFinderWrapper).findClass(request, globalSearchScope);
                if (javaClassFindClass != null) {
                    return javaClassFindClass;
                }
            } else {
                PsiClass psiClassFindClass = kotlinPsiElementFinderWrapper.findClass(strAsString, globalSearchScope);
                if (psiClassFindClass != null) {
                    return createJavaClass(classId, psiClassFindClass);
                }
            }
        }
        return null;
    }

    public List<JavaClass> findClasses(JavaClassFinder.Request request, GlobalSearchScope globalSearchScope) {
        if (request == null) {
            $$$reportNull$$$0(4);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(5);
        }
        if (globalSearchScope == GlobalSearchScope.EMPTY_SCOPE) {
            List<JavaClass> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(6);
            }
            return listEmptyList;
        }
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        ClassId classId = request.getClassId();
        String strAsString = classId.asSingleFqName().asString();
        SmartList smartList = new SmartList();
        for (KotlinPsiElementFinderWrapper kotlinPsiElementFinderWrapper : finders()) {
            ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
            for (PsiClass psiClass : kotlinPsiElementFinderWrapper.findClasses(strAsString, globalSearchScope)) {
                smartList.add(createJavaClass(classId, psiClass));
            }
        }
        return smartList;
    }

    private JavaClass createJavaClass(ClassId classId, PsiClass psiClass) {
        if (classId == null) {
            $$$reportNull$$$0(8);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(9);
        }
        JavaClassImpl javaClassImpl = new JavaClassImpl((JavaElementPsiSource<PsiClass>) JavaElementSourceFactory.getInstance(this.project).createPsiSource(psiClass));
        FqName fqNameAsSingleFqName = classId.asSingleFqName();
        if (!fqNameAsSingleFqName.equals(javaClassImpl.getFqName())) {
            throw new IllegalStateException("Requested " + fqNameAsSingleFqName + ", got " + javaClassImpl.getFqName());
        }
        if (psiClass instanceof KtLightClassMarker) {
            throw new IllegalStateException("Kotlin light classes should not be found by JavaPsiFacade, resolving: " + fqNameAsSingleFqName);
        }
        return javaClassImpl;
    }

    public Set<String> knownClassNamesInPackage(FqName fqName, GlobalSearchScope globalSearchScope) {
        if (fqName == null) {
            $$$reportNull$$$0(11);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(12);
        }
        if (globalSearchScope == GlobalSearchScope.EMPTY_SCOPE) {
            return Collections.emptySet();
        }
        KotlinPsiElementFinderWrapper[] kotlinPsiElementFinderWrapperArrFinders = finders();
        if (canComputeKnownClassNamesInPackage().booleanValue()) {
            return ((CliFinder) kotlinPsiElementFinderWrapperArrFinders[0]).knownClassNamesInPackage(fqName);
        }
        return null;
    }

    public Boolean canComputeKnownClassNamesInPackage() {
        KotlinPsiElementFinderWrapper[] kotlinPsiElementFinderWrapperArrFinders = finders();
        boolean z = false;
        if (kotlinPsiElementFinderWrapperArrFinders.length == 1 && (kotlinPsiElementFinderWrapperArrFinders[0] instanceof CliFinder)) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    private PsiClass[] findClassesInDumbMode(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(14);
        }
        String packageName = StringUtil.getPackageName(str);
        PsiPackage psiPackageFindPackage = findPackage(packageName, globalSearchScope);
        String shortName = StringUtil.getShortName(str);
        if (psiPackageFindPackage == null && packageName.length() < str.length()) {
            PsiClass[] psiClassArrFindClassesInDumbMode = findClassesInDumbMode(packageName, globalSearchScope);
            if (psiClassArrFindClassesInDumbMode.length == 1) {
                PsiClass[] psiClassArrFilterByName = PsiElementFinder.filterByName(shortName, psiClassArrFindClassesInDumbMode[0].getInnerClasses());
                if (psiClassArrFilterByName == null) {
                    $$$reportNull$$$0(15);
                }
                return psiClassArrFilterByName;
            }
            PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
            if (psiClassArr == null) {
                $$$reportNull$$$0(16);
            }
            return psiClassArr;
        }
        if (psiPackageFindPackage == null || !psiPackageFindPackage.containsClassNamed(shortName)) {
            PsiClass[] psiClassArr2 = PsiClass.EMPTY_ARRAY;
            if (psiClassArr2 == null) {
                $$$reportNull$$$0(17);
            }
            return psiClassArr2;
        }
        PsiClass[] psiClassArrFindClassByShortName = psiPackageFindPackage.findClassByShortName(shortName, globalSearchScope);
        if (psiClassArrFindClassByShortName == null) {
            $$$reportNull$$$0(18);
        }
        return psiClassArrFindClassByShortName;
    }

    private boolean shouldUseSlowResolve() {
        DumbService dumbService = DumbService.getInstance(getProject());
        return dumbService.isDumb() && dumbService.isAlternativeResolveEnabled();
    }

    private KotlinPsiElementFinderWrapper[] finders() {
        KotlinPsiElementFinderWrapper[] kotlinPsiElementFinderWrapperArrCalcFinders = this.elementFinders;
        if (kotlinPsiElementFinderWrapperArrCalcFinders == null) {
            kotlinPsiElementFinderWrapperArrCalcFinders = calcFinders();
            this.elementFinders = kotlinPsiElementFinderWrapperArrCalcFinders;
        }
        if (kotlinPsiElementFinderWrapperArrCalcFinders == null) {
            $$$reportNull$$$0(19);
        }
        return kotlinPsiElementFinderWrapperArrCalcFinders;
    }

    private KotlinPsiElementFinderWrapper[] calcFinders() {
        ArrayList arrayList = new ArrayList();
        JavaFileManager javaFileManagerFindJavaFileManager = findJavaFileManager(this.project);
        arrayList.add(javaFileManagerFindJavaFileManager instanceof KotlinCliJavaFileManager ? new CliFinder((KotlinCliJavaFileManager) javaFileManagerFindJavaFileManager) : new NonCliFinder(this.project, javaFileManagerFindJavaFileManager));
        ArrayList arrayList2 = new ArrayList();
        for (PsiElementFinder psiElementFinder : PsiElementFinder.EP.getExtensions(getProject())) {
            if ((psiElementFinder instanceof KotlinSafeClassFinder) || (!(psiElementFinder instanceof NonClasspathClassFinder) && !(psiElementFinder instanceof KotlinFinderMarker) && !(psiElementFinder instanceof PsiElementFinderImpl))) {
                arrayList2.add(psiElementFinder);
            }
        }
        arrayList.addAll(CollectionsKt.map(arrayList2, new Function1() { // from class: org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KotlinJavaPsiFacade.wrap((PsiElementFinder) obj);
            }
        }));
        KotlinPsiElementFinderWrapper[] kotlinPsiElementFinderWrapperArr = (KotlinPsiElementFinderWrapper[]) arrayList.toArray(new KotlinPsiElementFinderWrapper[0]);
        if (kotlinPsiElementFinderWrapperArr == null) {
            $$$reportNull$$$0(20);
        }
        return kotlinPsiElementFinderWrapperArr;
    }

    private static JavaFileManager findJavaFileManager(Project project) {
        if (project == null) {
            $$$reportNull$$$0(21);
        }
        JavaFileManager javaFileManager = (JavaFileManager) project.getService(JavaFileManager.class);
        if (javaFileManager == null) {
            throw new IllegalStateException("JavaFileManager component is not found in project");
        }
        if (javaFileManager == null) {
            $$$reportNull$$$0(22);
        }
        return javaFileManager;
    }

    public PsiPackage findPackage(String str, GlobalSearchScope globalSearchScope) {
        ConcurrentMap<GlobalSearchScope, ConcurrentMap<String, PsiPackage>> concurrentMap;
        PsiPackage psiPackageFindPackage;
        if (str == null) {
            $$$reportNull$$$0(23);
        }
        if (globalSearchScope == GlobalSearchScope.EMPTY_SCOPE) {
            return null;
        }
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        if (certainlyDoesNotExist(str, globalSearchScope)) {
            return null;
        }
        PackageCache packageCacheObtainPackageCache = obtainPackageCache();
        Boolean bool = packageCacheObtainPackageCache.hasPackageInAllScopeCache.get(str);
        if (bool != null && !bool.booleanValue()) {
            return null;
        }
        ConcurrentMap<String, PsiPackage> concurrentMap2 = packageCacheObtainPackageCache.packageInLibScopeCache.get(globalSearchScope);
        PsiPackage psiPackage = concurrentMap2 != null ? concurrentMap2.get(str) : null;
        if (psiPackage != null) {
            return unwrap(psiPackage);
        }
        ConcurrentMap<String, PsiPackage> concurrentMap3 = packageCacheObtainPackageCache.packageInScopeCache.get(globalSearchScope);
        PsiPackage psiPackage2 = concurrentMap3 != null ? concurrentMap3.get(str) : null;
        if (psiPackage2 != null) {
            return unwrap(psiPackage2);
        }
        boolean zIsALibrarySearchScope = isALibrarySearchScope(globalSearchScope);
        NotFoundPackagesCachingStrategy.CacheType cacheTypeChooseStrategy = this.notFoundPackagesCachingStrategy.chooseStrategy(zIsALibrarySearchScope, str);
        ConcurrentMap<GlobalSearchScope, ConcurrentMap<String, PsiPackage>> concurrentMap4 = zIsALibrarySearchScope ? packageCacheObtainPackageCache.packageInLibScopeCache : packageCacheObtainPackageCache.packageInScopeCache;
        KotlinPsiElementFinderWrapper[] kotlinPsiElementFinderWrapperArrFilteredFinders = filteredFinders();
        boolean z = false;
        if (bool != null) {
            for (KotlinPsiElementFinderWrapper kotlinPsiElementFinderWrapper : kotlinPsiElementFinderWrapperArrFilteredFinders) {
                if (!kotlinPsiElementFinderWrapper.isSameResultForAnyScope() && (psiPackageFindPackage = kotlinPsiElementFinderWrapper.findPackage(str, globalSearchScope)) != null) {
                    return unwrap((PsiPackage) ConcurrencyUtil.cacheOrGet((ConcurrentMap) ConcurrencyUtil.cacheOrGet(concurrentMap4, globalSearchScope, new ConcurrentHashMap()), str, psiPackageFindPackage));
                }
            }
        } else {
            for (KotlinPsiElementFinderWrapper kotlinPsiElementFinderWrapper2 : kotlinPsiElementFinderWrapperArrFilteredFinders) {
                PsiPackage psiPackageFindPackage2 = kotlinPsiElementFinderWrapper2.findPackage(str, globalSearchScope);
                if (psiPackageFindPackage2 != null) {
                    return unwrap((PsiPackage) ConcurrencyUtil.cacheOrGet((ConcurrentMap) ConcurrencyUtil.cacheOrGet(concurrentMap4, globalSearchScope, new ConcurrentHashMap()), str, psiPackageFindPackage2));
                }
            }
            for (KotlinPsiElementFinderWrapper kotlinPsiElementFinderWrapper3 : kotlinPsiElementFinderWrapperArrFilteredFinders) {
                if (!kotlinPsiElementFinderWrapper3.isSameResultForAnyScope() && kotlinPsiElementFinderWrapper3.findPackage(str, GlobalSearchScope.allScope(this.project)) != null) {
                    z = true;
                    break;
                }
            }
            if (z || cacheTypeChooseStrategy != NotFoundPackagesCachingStrategy.CacheType.NO_CACHING) {
                packageCacheObtainPackageCache.hasPackageInAllScopeCache.put(str, Boolean.valueOf(z));
            }
        }
        int i = AnonymousClass3.$SwitchMap$org$jetbrains$kotlin$resolve$jvm$NotFoundPackagesCachingStrategy$CacheType[cacheTypeChooseStrategy.ordinal()];
        if (i == 1) {
            concurrentMap = packageCacheObtainPackageCache.packageInLibScopeCache;
        } else {
            if (i != 2) {
                if (i == 3) {
                    return null;
                }
                throw new IllegalStateException("Impossible enum value: " + cacheTypeChooseStrategy.toString());
            }
            concurrentMap = packageCacheObtainPackageCache.packageInScopeCache;
        }
        return unwrap((PsiPackage) ConcurrencyUtil.cacheOrGet((ConcurrentMap) ConcurrencyUtil.cacheOrGet(concurrentMap, globalSearchScope, new ConcurrentHashMap()), str, NULL_PACKAGE));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$resolve$jvm$NotFoundPackagesCachingStrategy$CacheType;

        static {
            int[] iArr = new int[NotFoundPackagesCachingStrategy.CacheType.values().length];
            $SwitchMap$org$jetbrains$kotlin$resolve$jvm$NotFoundPackagesCachingStrategy$CacheType = iArr;
            try {
                iArr[NotFoundPackagesCachingStrategy.CacheType.LIB_SCOPE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$jvm$NotFoundPackagesCachingStrategy$CacheType[NotFoundPackagesCachingStrategy.CacheType.SCOPE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$jvm$NotFoundPackagesCachingStrategy$CacheType[NotFoundPackagesCachingStrategy.CacheType.NO_CACHING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private PackageCache obtainPackageCache() {
        PackageCache packageCache = this.packageCache;
        if (packageCache != null) {
            return packageCache;
        }
        PackageCache packageCache2 = new PackageCache();
        this.packageCache = packageCache2;
        return packageCache2;
    }

    private static boolean isALibrarySearchScope(GlobalSearchScope globalSearchScope) {
        return globalSearchScope.isSearchInLibraries();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean certainlyDoesNotExist(String str, GlobalSearchScope globalSearchScope) {
        Set<String> topPackageNames;
        if (str == null) {
            $$$reportNull$$$0(24);
        }
        if ((globalSearchScope instanceof TopPackageNamesProvider) && (topPackageNames = ((TopPackageNamesProvider) globalSearchScope).getTopPackageNames()) != null) {
            int iIndexOf = str.indexOf(46);
            if (iIndexOf > 0) {
                str = str.substring(0, iIndexOf);
            }
            if (!topPackageNames.contains(str)) {
                return true;
            }
        }
        return false;
    }

    private KotlinPsiElementFinderWrapper[] filteredFinders() {
        DumbService dumbService = DumbService.getInstance(getProject());
        KotlinPsiElementFinderWrapper[] kotlinPsiElementFinderWrapperArrFinders = finders();
        if (dumbService.isDumb()) {
            kotlinPsiElementFinderWrapperArrFinders = (KotlinPsiElementFinderWrapper[]) dumbService.filterByDumbAwareness(Arrays.asList(kotlinPsiElementFinderWrapperArrFinders)).toArray(new KotlinPsiElementFinderWrapper[0]);
        }
        if (kotlinPsiElementFinderWrapperArrFinders == null) {
            $$$reportNull$$$0(25);
        }
        return kotlinPsiElementFinderWrapperArrFinders;
    }

    public Project getProject() {
        Project project = this.project;
        if (project == null) {
            $$$reportNull$$$0(26);
        }
        return project;
    }

    public static KotlinPsiElementFinderWrapper wrap(PsiElementFinder psiElementFinder) {
        return psiElementFinder instanceof DumbAware ? new KotlinPsiElementFinderWrapperImplDumbAware(psiElementFinder) : new KotlinPsiElementFinderWrapperImpl(psiElementFinder);
    }

    private static class KotlinPsiElementFinderWrapperImpl implements KotlinPsiElementFinderWrapper {
        private final PsiElementFinder finder;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                case 3:
                case 5:
                    objArr[0] = "qualifiedName";
                    break;
                case 2:
                case 4:
                case 6:
                    objArr[0] = "scope";
                    break;
                default:
                    objArr[0] = "finder";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/resolve/jvm/KotlinJavaPsiFacade$KotlinPsiElementFinderWrapperImpl";
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "findClass";
                    break;
                case 3:
                case 4:
                    objArr[2] = "findClasses";
                    break;
                case 5:
                case 6:
                    objArr[2] = "findPackage";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public boolean isSameResultForAnyScope() {
            return true;
        }

        private KotlinPsiElementFinderWrapperImpl(PsiElementFinder psiElementFinder) {
            if (psiElementFinder == null) {
                $$$reportNull$$$0(0);
            }
            this.finder = psiElementFinder;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public PsiClass findClass(String str, GlobalSearchScope globalSearchScope) {
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(2);
            }
            return this.finder.findClass(str, globalSearchScope);
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public PsiClass[] findClasses(String str, GlobalSearchScope globalSearchScope) {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(4);
            }
            return this.finder.findClasses(str, globalSearchScope);
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public PsiPackage findPackage(String str, GlobalSearchScope globalSearchScope) {
            if (str == null) {
                $$$reportNull$$$0(5);
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(6);
            }
            return this.finder.findPackage(str);
        }

        public String toString() {
            return this.finder.toString();
        }
    }

    private static class KotlinPsiElementFinderWrapperImplDumbAware extends KotlinPsiElementFinderWrapperImpl implements DumbAware {
        private KotlinPsiElementFinderWrapperImplDumbAware(PsiElementFinder psiElementFinder) {
            super(psiElementFinder);
        }
    }

    private static class CliFinder implements DumbAware, KotlinPsiElementFinderWrapper {
        private final KotlinCliJavaFileManager javaFileManager;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                case 5:
                case 8:
                    objArr[0] = "qualifiedName";
                    break;
                case 2:
                case 4:
                case 6:
                case 9:
                    objArr[0] = "scope";
                    break;
                case 3:
                    objArr[0] = "request";
                    break;
                case 7:
                    objArr[0] = "packageFqName";
                    break;
                default:
                    objArr[0] = "javaFileManager";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/resolve/jvm/KotlinJavaPsiFacade$CliFinder";
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                    objArr[2] = "findClass";
                    break;
                case 5:
                case 6:
                    objArr[2] = "findClasses";
                    break;
                case 7:
                    objArr[2] = "knownClassNamesInPackage";
                    break;
                case 8:
                case 9:
                    objArr[2] = "findPackage";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public boolean isSameResultForAnyScope() {
            return false;
        }

        public CliFinder(KotlinCliJavaFileManager kotlinCliJavaFileManager) {
            if (kotlinCliJavaFileManager == null) {
                $$$reportNull$$$0(0);
            }
            this.javaFileManager = kotlinCliJavaFileManager;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public PsiClass findClass(String str, GlobalSearchScope globalSearchScope) {
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(2);
            }
            return this.javaFileManager.findClass(str, globalSearchScope);
        }

        public JavaClass findClass(JavaClassFinder.Request request, GlobalSearchScope globalSearchScope) {
            if (request == null) {
                $$$reportNull$$$0(3);
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(4);
            }
            return this.javaFileManager.findClass(request, globalSearchScope);
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public PsiClass[] findClasses(String str, GlobalSearchScope globalSearchScope) {
            if (str == null) {
                $$$reportNull$$$0(5);
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(6);
            }
            return this.javaFileManager.findClasses(str, globalSearchScope);
        }

        public Set<String> knownClassNamesInPackage(FqName fqName) {
            if (fqName == null) {
                $$$reportNull$$$0(7);
            }
            return this.javaFileManager.knownClassNamesInPackage(fqName);
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public PsiPackage findPackage(String str, GlobalSearchScope globalSearchScope) {
            if (str == null) {
                $$$reportNull$$$0(8);
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(9);
            }
            return this.javaFileManager.findPackage(str);
        }
    }

    private static class NonCliFinder implements DumbAware, KotlinPsiElementFinderWrapper {
        private final JavaFileManager javaFileManager;
        private final PackageIndex packageIndex;
        private final PsiManager psiManager;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "javaFileManager";
                    break;
                case 2:
                case 4:
                case 6:
                    objArr[0] = "qualifiedName";
                    break;
                case 3:
                case 5:
                case 7:
                    objArr[0] = "scope";
                    break;
                default:
                    objArr[0] = "project";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/resolve/jvm/KotlinJavaPsiFacade$NonCliFinder";
            switch (i) {
                case 2:
                case 3:
                    objArr[2] = "findClass";
                    break;
                case 4:
                case 5:
                    objArr[2] = "findClasses";
                    break;
                case 6:
                case 7:
                    objArr[2] = "findPackage";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public boolean isSameResultForAnyScope() {
            return false;
        }

        public NonCliFinder(Project project, JavaFileManager javaFileManager) {
            if (project == null) {
                $$$reportNull$$$0(0);
            }
            if (javaFileManager == null) {
                $$$reportNull$$$0(1);
            }
            this.javaFileManager = javaFileManager;
            this.packageIndex = PackageIndex.getInstance(project);
            this.psiManager = PsiManager.getInstance(project);
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public PsiClass findClass(String str, GlobalSearchScope globalSearchScope) {
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(3);
            }
            return this.javaFileManager.findClass(str, globalSearchScope);
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public PsiClass[] findClasses(String str, GlobalSearchScope globalSearchScope) {
            if (str == null) {
                $$$reportNull$$$0(4);
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(5);
            }
            return this.javaFileManager.findClasses(str, globalSearchScope);
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.KotlinPsiElementFinderWrapper
        public PsiPackage findPackage(String str, GlobalSearchScope globalSearchScope) {
            if (str == null) {
                $$$reportNull$$$0(6);
            }
            if (globalSearchScope == null) {
                $$$reportNull$$$0(7);
            }
            if (hasDirectoriesInScope(this.packageIndex.getDirsByPackageName(str, true), globalSearchScope)) {
                return new PsiPackageImpl(this.psiManager, str);
            }
            return null;
        }

        private static boolean hasDirectoriesInScope(Query<VirtualFile> query, final GlobalSearchScope globalSearchScope) {
            CommonProcessors.FindProcessor<VirtualFile> findProcessor = new CommonProcessors.FindProcessor<VirtualFile>() { // from class: org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.NonCliFinder.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // org.jetbrains.kotlin.com.intellij.util.CommonProcessors.FindProcessor
                public boolean accept(VirtualFile virtualFile) {
                    return globalSearchScope.accept(virtualFile);
                }
            };
            query.forEach(findProcessor);
            return findProcessor.isFound();
        }
    }
}
