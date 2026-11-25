package org.jetbrains.kotlin.com.intellij.psi.impl.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.codeInsight.completion.scope.JavaCompletionHints;
import org.jetbrains.kotlin.com.intellij.core.CoreJavaDirectoryService;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.com.intellij.openapi.roots.PackageIndex;
import org.jetbrains.kotlin.com.intellij.openapi.roots.impl.PackageDirectoryCache$$ExternalSyntheticLambda0;
import org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Conditions;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiFacadeImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiCompositeModifierList;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiSearchScopeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiClassUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.Processors;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.one.util.streamex.StreamEx;

/* JADX INFO: loaded from: classes3.dex */
public class PsiPackageImpl extends PsiPackageBase implements Queryable, PsiPackage {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiPackageImpl.class);
    private volatile CachedValue<PsiModifierList> myAnnotationList;
    private volatile SoftReference<Map<String, PsiClass[]>> myClassCache;
    private volatile CachedValue<Collection<PsiDirectory>> myDirectories;
    private volatile CachedValue<Collection<PsiDirectory>> myDirectoriesWithLibSources;
    private volatile SoftReference<Map<GlobalSearchScope, Map<String, PsiClass[]>>> myDumbModeFullCache;
    private volatile SoftReference<Map<Pair<GlobalSearchScope, String>, PsiClass[]>> myDumbModePartialCache;

    /* JADX WARN: Code duplicated, block: B:12:0x001e A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:23:0x0035 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:40:0x006e  */
    /* JADX WARN: Code duplicated, block: B:41:0x0073  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 4 && i != 7 && i != 9 && i != 12 && i != 14) {
            switch (i) {
                default:
                    switch (i) {
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                        case 35:
                            break;
                        default:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 19:
                case 20:
                case 21:
                case 22:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 1 && i != 4 && i != 7 && i != 9 && i != 12 && i != 14) {
            switch (i) {
                default:
                    switch (i) {
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                        case 35:
                            break;
                        default:
                            i2 = 2;
                            break;
                    }
                case 19:
                case 20:
                case 21:
                case 22:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 1) {
            objArr[0] = "newQualifiedName";
        } else if (i == 4) {
            objArr[0] = "visitor";
        } else if (i == 7 || i == 9 || i == 12) {
            objArr[0] = "scope";
        } else if (i != 14) {
            switch (i) {
                case 19:
                    objArr[0] = "shortName";
                    break;
                case 20:
                case 21:
                    objArr[0] = "name";
                    break;
                case 22:
                    objArr[0] = "scope";
                    break;
                default:
                    switch (i) {
                        case 26:
                        case 35:
                            objArr[0] = "name";
                            break;
                        case 27:
                        case 30:
                            objArr[0] = "processor";
                            break;
                        case 28:
                        case 31:
                            objArr[0] = "state";
                            break;
                        case 29:
                            objArr[0] = "place";
                            break;
                        case 32:
                            objArr[0] = "nameCondition";
                            break;
                        case 33:
                            objArr[0] = "classes";
                            break;
                        case 34:
                            objArr[0] = "scope";
                            break;
                        default:
                            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/PsiPackageImpl";
                            break;
                    }
                    break;
            }
        } else {
            objArr[0] = "name";
        }
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 9:
            case 12:
            case 14:
            case 19:
            case 20:
            case 21:
            case 22:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/PsiPackageImpl";
                break;
            case 2:
                objArr[1] = "occursInPackagePrefixes";
                break;
            case 3:
                objArr[1] = "getLanguage";
                break;
            case 5:
            case 8:
                objArr[1] = "getClasses";
                break;
            case 6:
                objArr[1] = "allScope";
                break;
            case 10:
                objArr[1] = "getFiles";
                break;
            case 11:
            case 13:
                objArr[1] = "getSubPackages";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[1] = "getCachedClassesByName";
                break;
            case 23:
            case 24:
            case 25:
                objArr[1] = "findClassByShortName";
                break;
            default:
                objArr[1] = "createCachedDirectories";
                break;
        }
        if (i == 1) {
            objArr[2] = "handleQualifiedNameChange";
        } else if (i == 4) {
            objArr[2] = "accept";
        } else if (i == 7) {
            objArr[2] = "getClasses";
        } else if (i == 9) {
            objArr[2] = "getFiles";
        } else if (i == 12) {
            objArr[2] = "getSubPackages";
        } else if (i != 14) {
            switch (i) {
                case 19:
                    objArr[2] = "findAllClasses";
                    break;
                case 20:
                    objArr[2] = "containsClassNamed";
                    break;
                case 21:
                case 22:
                    objArr[2] = "findClassByShortName";
                    break;
                default:
                    switch (i) {
                        case 26:
                            objArr[2] = "findSubPackageByName";
                            break;
                        case 27:
                        case 28:
                        case 29:
                            objArr[2] = "processDeclarations";
                            break;
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                            objArr[2] = "processClasses";
                            break;
                        case 34:
                            objArr[2] = "mayHaveContentInScope";
                            break;
                        case 35:
                            objArr[2] = "hasModifierProperty";
                            break;
                    }
                    break;
            }
        } else {
            objArr[2] = "getCachedClassesByName";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 4 && i != 7 && i != 9 && i != 12 && i != 14) {
            switch (i) {
                case 19:
                case 20:
                case 21:
                case 22:
                    break;
                default:
                    switch (i) {
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                        case 35:
                            break;
                        default:
                            throw new IllegalStateException(str2);
                    }
                    break;
            }
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str != null) {
            return false;
        }
        $$$reportNull$$$0(35);
        return false;
    }

    public PsiPackageImpl(PsiManager psiManager, String str) {
        super(psiManager, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageBase
    protected Collection<PsiDirectory> getAllDirectories(final GlobalSearchScope globalSearchScope) {
        if (!globalSearchScope.getModelBranchesAffectingScope().isEmpty()) {
            Collection<VirtualFile> collectionFindAll = PackageIndex.getInstance(getProject()).getDirsByPackageName(getQualifiedName(), globalSearchScope).findAll();
            final PsiManager manager = getManager();
            Objects.requireNonNull(manager);
            return ContainerUtil.mapNotNull(collectionFindAll, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return manager.findDirectory((VirtualFile) obj);
                }
            });
        }
        if (globalSearchScope.isForceSearchingInLibrarySources()) {
            if (this.myDirectoriesWithLibSources == null) {
                this.myDirectoriesWithLibSources = createCachedDirectories(true);
            }
            return ContainerUtil.filter(this.myDirectoriesWithLibSources.getValue(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return globalSearchScope.contains(((PsiDirectory) obj).getVirtualFile());
                }
            });
        }
        if (this.myDirectories == null) {
            this.myDirectories = createCachedDirectories(false);
        }
        return ContainerUtil.filter(this.myDirectories.getValue(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return globalSearchScope.contains(((PsiDirectory) obj).getVirtualFile());
            }
        });
    }

    private CachedValue<Collection<PsiDirectory>> createCachedDirectories(final boolean z) {
        CachedValue<Collection<PsiDirectory>> cachedValueCreateCachedValue = CachedValuesManager.getManager(getProject()).createCachedValue(new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl$$ExternalSyntheticLambda7
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6614x9fa512cf(z);
            }
        }, false);
        if (cachedValueCreateCachedValue == null) {
            $$$reportNull$$$0(0);
        }
        return cachedValueCreateCachedValue;
    }

    /* JADX INFO: renamed from: lambda$createCachedDirectories$2$org-jetbrains-kotlin-com-intellij-psi-impl-file-PsiPackageImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6614x9fa512cf(boolean z) {
        ArrayList arrayList = new ArrayList();
        getFacade().processPackageDirectories(this, allScope(), Processors.cancelableCollectProcessor(arrayList), z);
        return CachedValueProvider.Result.create(arrayList, PsiPackageImplementationHelper.getInstance().getDirectoryCachedValueDependencies(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageBase
    public PsiPackageImpl findPackage(String str) {
        return (PsiPackageImpl) getFacade().findPackage(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackage
    public void handleQualifiedNameChange(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        PsiPackageImplementationHelper.getInstance().handleQualifiedNameChange(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackage
    public VirtualFile[] occursInPackagePrefixes() {
        VirtualFile[] virtualFileArrOccursInPackagePrefixes = PsiPackageImplementationHelper.getInstance().occursInPackagePrefixes(this);
        if (virtualFileArrOccursInPackagePrefixes == null) {
            $$$reportNull$$$0(2);
        }
        return virtualFileArrOccursInPackagePrefixes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageBase
    public PsiPackageImpl getParentPackage() {
        return (PsiPackageImpl) super.getParentPackage();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        JavaLanguage javaLanguage = JavaLanguage.INSTANCE;
        if (javaLanguage == null) {
            $$$reportNull$$$0(3);
        }
        return javaLanguage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return !getProject().isDisposed() && (PsiPackageImplementationHelper.getInstance().packagePrefixExists(this) || getDirectories().length > 0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitPackage(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageBase, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiPackage:" + getQualifiedName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackage
    public PsiClass[] getClasses() {
        PsiClass[] classes = getClasses(allScope());
        if (classes == null) {
            $$$reportNull$$$0(5);
        }
        return classes;
    }

    protected GlobalSearchScope allScope() {
        GlobalSearchScope globalSearchScopeAdjustAllScope = PsiPackageImplementationHelper.getInstance().adjustAllScope(this, GlobalSearchScope.allScope(getProject()));
        if (globalSearchScopeAdjustAllScope == null) {
            $$$reportNull$$$0(6);
        }
        return globalSearchScopeAdjustAllScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackage
    public PsiClass[] getClasses(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(7);
        }
        PsiClass[] classes = getFacade().getClasses(this, globalSearchScope);
        if (classes == null) {
            $$$reportNull$$$0(8);
        }
        return classes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackage
    public PsiFile[] getFiles(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(9);
        }
        PsiFile[] packageFiles = getFacade().getPackageFiles(this, globalSearchScope);
        if (packageFiles == null) {
            $$$reportNull$$$0(10);
        }
        return packageFiles;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackage
    public PsiModifierList getAnnotationList() {
        if (this.myAnnotationList == null) {
            this.myAnnotationList = CachedValuesManager.getManager(getProject()).createCachedValue(new PackageAnnotationValueProvider(), false);
        }
        return this.myAnnotationList.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackage
    public PsiPackage[] getSubPackages() {
        PsiPackage[] subPackages = getSubPackages(allScope());
        if (subPackages == null) {
            $$$reportNull$$$0(11);
        }
        return subPackages;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackage
    public PsiPackage[] getSubPackages(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(12);
        }
        PsiPackage[] subPackages = getFacade().getSubPackages(this, globalSearchScope);
        if (subPackages == null) {
            $$$reportNull$$$0(13);
        }
        return subPackages;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JavaPsiFacadeImpl getFacade() {
        return (JavaPsiFacadeImpl) JavaPsiFacade.getInstance(getProject());
    }

    private PsiClass[] getCachedClassesByName(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        DumbService dumbService = DumbService.getInstance(getProject());
        if (dumbService.isDumb() && dumbService.isAlternativeResolveEnabled()) {
            PsiClass[] cachedClassesInDumbMode = getCachedClassesInDumbMode(str, globalSearchScope);
            if (cachedClassesInDumbMode == null) {
                $$$reportNull$$$0(15);
            }
            return cachedClassesInDumbMode;
        }
        if (!globalSearchScope.getModelBranchesAffectingScope().isEmpty()) {
            PsiClass[] psiClassArrFindAllClasses = findAllClasses(str, globalSearchScope);
            if (psiClassArrFindAllClasses == null) {
                $$$reportNull$$$0(16);
            }
            return psiClassArrFindAllClasses;
        }
        Map map = (Map) SoftReference.dereference(this.myClassCache);
        if (map == null) {
            ConcurrentMap concurrentMapCreateConcurrentSoftValueMap = ContainerUtil.createConcurrentSoftValueMap();
            this.myClassCache = new SoftReference<>(concurrentMapCreateConcurrentSoftValueMap);
            map = concurrentMapCreateConcurrentSoftValueMap;
        }
        PsiClass[] psiClassArr = (PsiClass[]) map.get(str);
        if (psiClassArr != null) {
            if (psiClassArr == null) {
                $$$reportNull$$$0(17);
            }
            return psiClassArr;
        }
        RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
        PsiClass[] psiClassArrFindAllClasses2 = findAllClasses(str, GlobalSearchScope.everythingScope(getProject()));
        if (stackStampMarkStack.mayCacheNow()) {
            map.put(str, psiClassArrFindAllClasses2);
        }
        if (psiClassArrFindAllClasses2 == null) {
            $$$reportNull$$$0(18);
        }
        return psiClassArrFindAllClasses2;
    }

    private PsiClass[] findAllClasses(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        String qualifiedName = getQualifiedName();
        if (!qualifiedName.isEmpty()) {
            str = qualifiedName + "." + str;
        }
        return getFacade().findClasses(str, globalSearchScope);
    }

    private PsiClass[] getCachedClassesInDumbMode(String str, GlobalSearchScope globalSearchScope) {
        Map map = (Map) SoftReference.dereference(this.myDumbModeFullCache);
        if (map == null) {
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
            this.myDumbModeFullCache = new SoftReference<>(concurrentHashMap);
            map = concurrentHashMap;
        }
        Map map2 = (Map) map.get(globalSearchScope);
        if (map2 == null) {
            PsiClass[] psiClassArrFindClassesHeuristically = findClassesHeuristically(str, globalSearchScope);
            if (psiClassArrFindClassesHeuristically != null) {
                return psiClassArrFindClassesHeuristically;
            }
            RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
            HashMap map3 = new HashMap();
            for (PsiClass psiClass : getClasses(globalSearchScope)) {
                String name2 = psiClass.getName();
                if (name2 != null) {
                    PsiClass[] psiClassArr = (PsiClass[]) map3.get(name2);
                    map3.put(name2, psiClassArr == null ? new PsiClass[]{psiClass} : (PsiClass[]) ArrayUtil.append(psiClassArr, psiClass));
                }
            }
            if (stackStampMarkStack.mayCacheNow()) {
                map.put(globalSearchScope, map3);
            }
            map2 = map3;
        }
        PsiClass[] psiClassArr2 = (PsiClass[]) map2.get(str);
        return psiClassArr2 == null ? PsiClass.EMPTY_ARRAY : psiClassArr2;
    }

    private PsiClass[] findClassesHeuristically(final String str, GlobalSearchScope globalSearchScope) {
        if (findSubPackageByName(str) != null) {
            return PsiClass.EMPTY_ARRAY;
        }
        Map map = (Map) SoftReference.dereference(this.myDumbModePartialCache);
        if (map == null) {
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
            this.myDumbModePartialCache = new SoftReference<>(concurrentHashMap);
            map = concurrentHashMap;
        }
        PsiClass[] psiClassArr = (PsiClass[]) map.get(Pair.create(globalSearchScope, str));
        if (psiClassArr != null) {
            return psiClassArr;
        }
        RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
        ArrayList arrayList = new ArrayList();
        for (PsiDirectory psiDirectory : getDirectories(globalSearchScope)) {
            for (PsiClass psiClass : CoreJavaDirectoryService.getPsiClasses(psiDirectory, (PsiFile[]) ContainerUtil.filter(psiDirectory.getFiles(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl$$ExternalSyntheticLambda6
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return ((PsiFile) obj).getName().contains(str);
                }
            }).toArray(PsiFile.EMPTY_ARRAY))) {
                if (str.equals(psiClass.getName())) {
                    arrayList.add(psiClass);
                }
            }
        }
        if (arrayList.isEmpty() || !stackStampMarkStack.mayCacheNow()) {
            return psiClassArr;
        }
        Pair pairCreate = Pair.create(globalSearchScope, str);
        PsiClass[] psiClassArr2 = (PsiClass[]) arrayList.toArray(PsiClass.EMPTY_ARRAY);
        map.put(pairCreate, psiClassArr2);
        return psiClassArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackage
    public boolean containsClassNamed(String str) {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        return getCachedClassesByName(str, GlobalSearchScope.everythingScope(getProject())).length > 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackage
    public PsiClass[] findClassByShortName(String str, final GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(21);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(22);
        }
        PsiClass[] cachedClassesByName = getCachedClassesByName(str, globalSearchScope);
        if (cachedClassesByName.length == 0) {
            if (cachedClassesByName == null) {
                $$$reportNull$$$0(23);
            }
            return cachedClassesByName;
        }
        if (cachedClassesByName.length == 1) {
            PsiClass[] psiClassArr = PsiSearchScopeUtil.isInScope(globalSearchScope, (PsiElement) cachedClassesByName[0]) ? (PsiClass[]) cachedClassesByName.clone() : PsiClass.EMPTY_ARRAY;
            if (psiClassArr == null) {
                $$$reportNull$$$0(24);
            }
            return psiClassArr;
        }
        PsiClass[] psiClassArr2 = (PsiClass[]) ((StreamEx) ((StreamEx) StreamEx.of((Object[]) cachedClassesByName).filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PsiPackageImpl.lambda$findClassByShortName$4(globalSearchScope, (PsiClass) obj);
            }
        })).sorted((Comparator) PsiClassUtil.createScopeComparator(globalSearchScope).thenComparing(new java.util.function.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PsiClass) obj).getQualifiedName();
            }
        }, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(new java.util.function.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PsiPackageImpl.lambda$findClassByShortName$6((PsiClass) obj);
            }
        }))).toArray(PsiClass.EMPTY_ARRAY);
        if (psiClassArr2 == null) {
            $$$reportNull$$$0(25);
        }
        return psiClassArr2;
    }

    static /* synthetic */ boolean lambda$findClassByShortName$4(GlobalSearchScope globalSearchScope, PsiClass psiClass) {
        return PsiSearchScopeUtil.isInScope(globalSearchScope, (PsiElement) psiClass) && psiClass.mo6092getContainingClass() == null;
    }

    static /* synthetic */ String lambda$findClassByShortName$6(PsiClass psiClass) {
        PsiFile containingFile = psiClass.getContainingFile();
        return containingFile instanceof PsiClassOwner ? ((PsiClassOwner) containingFile).getPackageName() : "";
    }

    private PsiPackage findSubPackageByName(String str) {
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        String qualifiedName = getQualifiedName();
        if (!qualifiedName.isEmpty()) {
            str = qualifiedName + "." + str;
        }
        return getFacade().findPackage(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(27);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(28);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(29);
        }
        GlobalSearchScope resolveScope = psiElement2.getResolveScope();
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        Condition conditionAlwaysTrue = (Condition) psiScopeProcessor.getHint(JavaCompletionHints.NAME_FILTER);
        NameHint nameHint = (NameHint) psiScopeProcessor.getHint(NameHint.KEY);
        String name2 = nameHint == null ? null : nameHint.getName(resolveState);
        if (elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.CLASS)) {
            if (name2 != null) {
                PsiClass[] psiClassArrFindClassByShortName = findClassByShortName(name2, resolveScope);
                if (psiClassArrFindClassByShortName.length == 0 && PsiUtil.isInsideJavadocComment(psiElement2)) {
                    psiClassArrFindClassByShortName = findClassByShortName(name2, allScope());
                }
                if (!processClasses(psiScopeProcessor, resolveState, psiClassArrFindClassByShortName, Conditions.alwaysTrue())) {
                    return false;
                }
            } else {
                PsiClass[] classes = getClasses(resolveScope);
                if (classes.length == 0 && PsiUtil.isInsideJavadocComment(psiElement2)) {
                    classes = getClasses(allScope());
                }
                if (conditionAlwaysTrue == null) {
                    conditionAlwaysTrue = Conditions.alwaysTrue();
                }
                if (!processClasses(psiScopeProcessor, resolveState, classes, conditionAlwaysTrue)) {
                    return false;
                }
            }
        }
        if (elementClassHint != null && !elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.PACKAGE)) {
            return true;
        }
        if (name2 != null) {
            PsiPackage psiPackageFindSubPackageByName = findSubPackageByName(name2);
            return psiPackageFindSubPackageByName == null || psiScopeProcessor.execute(psiPackageFindSubPackageByName, resolveState);
        }
        for (PsiPackage psiPackage : getSubPackages(resolveScope)) {
            String name3 = psiPackage.getName();
            if (name3 != null && PsiNameHelper.getInstance(getProject()).isIdentifier(name3, PsiUtil.getLanguageLevel(this)) && !psiScopeProcessor.execute(psiPackage, resolveState)) {
                return false;
            }
        }
        return true;
    }

    private static boolean processClasses(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiClass[] psiClassArr, Condition<? super String> condition) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(30);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(31);
        }
        if (condition == null) {
            $$$reportNull$$$0(32);
        }
        if (psiClassArr == null) {
            $$$reportNull$$$0(33);
        }
        for (PsiClass psiClass : psiClassArr) {
            String name2 = psiClass.getName();
            if (name2 != null && condition.value(name2)) {
                try {
                    if (!psiScopeProcessor.execute(psiClass, resolveState)) {
                        return false;
                    }
                } catch (ProcessCanceledException e) {
                    throw e;
                } catch (IndexNotReadyException e2) {
                    throw e2;
                } catch (Exception e3) {
                    LOG.error((Throwable) e3);
                }
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public void navigate(boolean z) {
        PsiPackageImplementationHelper.getInstance().navigate(this, z);
    }

    public boolean mayHaveContentInScope(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(34);
        }
        if (getDirectories(globalSearchScope).length <= 0 && getClasses(globalSearchScope).length <= 0) {
            VirtualFile[] virtualFileArrOccursInPackagePrefixes = occursInPackagePrefixes();
            Objects.requireNonNull(globalSearchScope);
            if (!ContainerUtil.exists(virtualFileArrOccursInPackagePrefixes, new PackageDirectoryCache$$ExternalSyntheticLambda0(globalSearchScope))) {
                return false;
            }
        }
        return true;
    }

    private class PackageAnnotationValueProvider implements CachedValueProvider<PsiModifierList> {
        private PackageAnnotationValueProvider() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
        public CachedValueProvider.Result<PsiModifierList> compute() {
            ArrayList arrayList = new ArrayList();
            PsiDirectory[] directories = PsiPackageImpl.this.getDirectories();
            int length = directories.length;
            int i = 0;
            while (true) {
                PsiModifierList annotationList = null;
                if (i >= length) {
                    break;
                }
                PsiFile psiFileFindFile = directories[i].findFile(PsiPackage.PACKAGE_INFO_FILE);
                PsiPackageStatement psiPackageStatement = psiFileFindFile == null ? null : (PsiPackageStatement) PsiTreeUtil.getChildOfType(psiFileFindFile, PsiPackageStatement.class);
                if (psiPackageStatement != null) {
                    annotationList = psiPackageStatement.getAnnotationList();
                }
                ContainerUtil.addIfNotNull(arrayList, annotationList);
                i++;
            }
            for (PsiClass psiClass : PsiPackageImpl.this.getFacade().findClasses(PsiPackageImpl.this.getQualifiedName() + ".package-info", PsiPackageImpl.this.allScope())) {
                ContainerUtil.addIfNotNull(arrayList, psiClass.mo6095getModifierList());
            }
            return new CachedValueProvider.Result<>(arrayList.isEmpty() ? null : new PsiCompositeModifierList(PsiPackageImpl.this.getManager(), arrayList), PsiModificationTracker.MODIFICATION_COUNT);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return getAnnotationList();
    }
}
