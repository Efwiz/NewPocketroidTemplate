package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmClass;
import org.jetbrains.kotlin.com.intellij.lang.jvm.facade.JvmFacade;
import org.jetbrains.kotlin.com.intellij.lang.jvm.facade.JvmFacadeImpl;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Conditions;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JvmPsiConversionHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaParserFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaDummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaDummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.FileContextUtil;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class JavaPsiFacadeImpl extends JavaPsiFacadeEx {
    private static final Logger LOG = Logger.getInstance((Class<?>) JavaPsiFacadeImpl.class);
    private final ConcurrentMap<GlobalSearchScope, Map<String, PsiClass>> myClassCache;
    private final PsiConstantEvaluationHelper myConstantEvaluationHelper;
    private final JvmPsiConversionHelper myConversionHelper;
    private final JavaFileManager myFileManager;
    private final NotNullLazyValue<JvmFacadeImpl> myJvmFacade;
    private final Map<GlobalSearchScope, Map<String, Collection<PsiJavaModule>>> myModuleCache;
    private final ConcurrentMap<String, PsiPackage> myPackageCache;
    private final Project myProject;

    /* JADX WARN: Code duplicated, block: B:17:0x0037 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 13 && i != 16 && i != 23 && i != 36 && i != 39 && i != 44 && i != 50 && i != 56 && i != 58 && i != 19 && i != 20) {
            switch (i) {
                default:
                    switch (i) {
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 7:
                case 8:
                case 9:
                case 10:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 13 && i != 16 && i != 23 && i != 36 && i != 39 && i != 44 && i != 50 && i != 56 && i != 58 && i != 19 && i != 20) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 11:
            case 14:
            case 17:
            case 24:
                objArr[0] = "qualifiedName";
                break;
            case 2:
            case 4:
            case 6:
            case 12:
            case 15:
            case 18:
            case 21:
            case 26:
            case 28:
            case 35:
            case 38:
            case 43:
            case 46:
            case 49:
                objArr[0] = "scope";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
            case 16:
            case 19:
            case 20:
            case 23:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 36:
            case 39:
            case 44:
            case 50:
            case 56:
            case 58:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/JavaPsiFacadeImpl";
                break;
            case 22:
                objArr[0] = "finders";
                break;
            case 25:
            case 27:
                objArr[0] = "moduleName";
                break;
            case 34:
            case 37:
            case 42:
            case 45:
            case 48:
                objArr[0] = "psiPackage";
                break;
            case 40:
                objArr[0] = "result";
                break;
            case 41:
                objArr[0] = "classes";
                break;
            case 47:
                objArr[0] = "consumer";
                break;
            case 51:
                objArr[0] = "packageName";
                break;
            case 52:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 53:
                objArr[0] = "aPackage";
                break;
            case 54:
                objArr[0] = "element1";
                break;
            case 55:
                objArr[0] = "element2";
                break;
            case 57:
                objArr[0] = "expression";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 13) {
            objArr[1] = "findClasses";
        } else if (i == 16) {
            objArr[1] = "findClassesWithJvmFacade";
        } else if (i == 23) {
            objArr[1] = "getConstantEvaluationHelper";
        } else if (i == 36) {
            objArr[1] = "getClassNames";
        } else if (i == 39) {
            objArr[1] = "getClasses";
        } else if (i == 44) {
            objArr[1] = "getPackageFiles";
        } else if (i == 50) {
            objArr[1] = "getSubPackages";
        } else if (i == 56) {
            objArr[1] = "getProject";
        } else if (i == 58) {
            objArr[1] = "getElementFactory";
        } else if (i != 19 && i != 20) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    objArr[1] = "findClassesInDumbMode";
                    break;
                default:
                    switch (i) {
                        case 29:
                            objArr[1] = "findModules";
                            break;
                        case 30:
                            objArr[1] = "filteredFinders";
                            break;
                        case 31:
                            objArr[1] = "getParserFacade";
                            break;
                        case 32:
                            objArr[1] = "getResolveHelper";
                            break;
                        case 33:
                            objArr[1] = "getNameHelper";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/JavaPsiFacadeImpl";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "findClassesWithoutJvmFacade";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "findClass";
                break;
            case 3:
            case 4:
                objArr[2] = "doFindClass";
                break;
            case 5:
            case 6:
                objArr[2] = "findClassesInDumbMode";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
            case 16:
            case 19:
            case 20:
            case 23:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 36:
            case 39:
            case 44:
            case 50:
            case 56:
            case 58:
                break;
            case 11:
            case 12:
                objArr[2] = "findClasses";
                break;
            case 14:
            case 15:
                objArr[2] = "findClassesWithJvmFacade";
                break;
            case 17:
            case 18:
                objArr[2] = "findClassesWithoutJvmFacade";
                break;
            case 21:
            case 22:
                objArr[2] = "getFilterFromFinders";
                break;
            case 24:
                objArr[2] = "findPackage";
                break;
            case 25:
            case 26:
                objArr[2] = "findModule";
                break;
            case 27:
            case 28:
                objArr[2] = "findModules";
                break;
            case 34:
            case 35:
                objArr[2] = "getClassNames";
                break;
            case 37:
            case 38:
                objArr[2] = "getClasses";
                break;
            case 40:
            case 41:
                objArr[2] = "filterClassesAndAppend";
                break;
            case 42:
            case 43:
                objArr[2] = "getPackageFiles";
                break;
            case 45:
            case 46:
            case 47:
                objArr[2] = "processPackageDirectories";
                break;
            case 48:
            case 49:
                objArr[2] = "getSubPackages";
                break;
            case 51:
                objArr[2] = "isPartOfPackagePrefix";
                break;
            case 52:
            case 53:
                objArr[2] = "isInPackage";
                break;
            case 54:
            case 55:
                objArr[2] = "arePackagesTheSame";
                break;
            case 57:
                objArr[2] = "isConstantExpression";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13 && i != 16 && i != 23 && i != 36 && i != 39 && i != 44 && i != 50 && i != 56 && i != 58 && i != 19 && i != 20) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    break;
                default:
                    switch (i) {
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    public JavaPsiFacadeImpl(final Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myPackageCache = ContainerUtil.createConcurrentSoftValueMap();
        this.myClassCache = ContainerUtil.createConcurrentSoftKeySoftValueMap();
        this.myModuleCache = ContainerUtil.createConcurrentSoftKeySoftValueMap();
        this.myProject = project;
        this.myFileManager = JavaFileManager.getInstance(project);
        this.myConstantEvaluationHelper = new PsiConstantEvaluationHelperImpl();
        this.myJvmFacade = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiFacadeImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaPsiFacadeImpl.lambda$new$0(project);
            }
        });
        this.myConversionHelper = JvmPsiConversionHelper.getInstance(project);
        project.getMessageBus().connect().subscribe(PsiModificationTracker.TOPIC, new PsiModificationTracker.Listener() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiFacadeImpl$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker.Listener
            public final void modificationCountChanged() {
                this.f$0.m6550x714ff78a();
            }
        });
        DummyHolderFactory.setFactory(new JavaDummyHolderFactory());
    }

    static /* synthetic */ JvmFacadeImpl lambda$new$0(Project project) {
        return (JvmFacadeImpl) JvmFacade.getInstance(project);
    }

    /* JADX INFO: renamed from: lambda$new$1$org-jetbrains-kotlin-com-intellij-psi-impl-JavaPsiFacadeImpl, reason: not valid java name */
    /* synthetic */ void m6550x714ff78a() {
        this.myClassCache.clear();
        this.myPackageCache.clear();
        this.myModuleCache.clear();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public PsiClass findClass(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(2);
        }
        ProgressIndicatorProvider.checkCanceled();
        Map<String, PsiClass> mapComputeIfAbsent = this.myClassCache.computeIfAbsent(globalSearchScope, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiFacadeImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ContainerUtil.createConcurrentWeakValueMap();
            }
        });
        PsiClass psiClass = mapComputeIfAbsent.get(str);
        if (psiClass != null) {
            return psiClass;
        }
        RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
        PsiClass psiClassDoFindClass = doFindClass(str, globalSearchScope);
        if (psiClassDoFindClass != null && stackStampMarkStack.mayCacheNow()) {
            mapComputeIfAbsent.put(str, psiClassDoFindClass);
        }
        return psiClassDoFindClass;
    }

    private PsiClass doFindClass(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(4);
        }
        if (shouldUseSlowResolve()) {
            PsiClass[] psiClassArrFindClassesInDumbMode = findClassesInDumbMode(str, globalSearchScope);
            if (psiClassArrFindClassesInDumbMode.length != 0) {
                return psiClassArrFindClassesInDumbMode[0];
            }
            return null;
        }
        List<PsiElementFinder> listFilteredFinders = filteredFinders();
        Predicate<PsiClass> filterFromFinders = getFilterFromFinders(globalSearchScope, listFilteredFinders);
        Iterator<PsiElementFinder> it = listFilteredFinders.iterator();
        while (it.hasNext()) {
            PsiClass psiClassFindClass = it.next().findClass(str, globalSearchScope);
            if (psiClassFindClass != null && (filterFromFinders == null || filterFromFinders.test(psiClassFindClass))) {
                return psiClassFindClass;
            }
        }
        return null;
    }

    private PsiClass[] findClassesInDumbMode(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(6);
        }
        String packageName = StringUtil.getPackageName(str);
        PsiPackage psiPackageFindPackage = findPackage(packageName);
        String shortName = StringUtil.getShortName(str);
        if (psiPackageFindPackage != null || packageName.length() >= str.length()) {
            if (psiPackageFindPackage == null) {
                PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
                if (psiClassArr == null) {
                    $$$reportNull$$$0(9);
                }
                return psiClassArr;
            }
            PsiClass[] psiClassArrFindClassByShortName = psiPackageFindPackage.findClassByShortName(shortName, globalSearchScope);
            if (psiClassArrFindClassByShortName == null) {
                $$$reportNull$$$0(10);
            }
            return psiClassArrFindClassByShortName;
        }
        PsiClass[] psiClassArrFindClassesInDumbMode = findClassesInDumbMode(packageName, globalSearchScope);
        if (psiClassArrFindClassesInDumbMode.length == 1) {
            PsiClass[] psiClassArrFilterByName = PsiElementFinder.filterByName(shortName, psiClassArrFindClassesInDumbMode[0].getInnerClasses());
            if (psiClassArrFilterByName == null) {
                $$$reportNull$$$0(7);
            }
            return psiClassArrFilterByName;
        }
        PsiClass[] psiClassArr2 = PsiClass.EMPTY_ARRAY;
        if (psiClassArr2 == null) {
            $$$reportNull$$$0(8);
        }
        return psiClassArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public PsiClass[] findClasses(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(12);
        }
        List<PsiClass> listFindClassesWithJvmFacade = findClassesWithJvmFacade(str, globalSearchScope);
        PsiClass[] psiClassArr = listFindClassesWithJvmFacade.isEmpty() ? PsiClass.EMPTY_ARRAY : (PsiClass[]) listFindClassesWithJvmFacade.toArray(PsiClass.EMPTY_ARRAY);
        if (psiClassArr == null) {
            $$$reportNull$$$0(13);
        }
        return psiClassArr;
    }

    private List<PsiClass> findClassesWithJvmFacade(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(15);
        }
        List<PsiClass> listFindClassesWithoutJvmFacade = findClassesWithoutJvmFacade(str, globalSearchScope);
        List<PsiClass> arrayList = !listFindClassesWithoutJvmFacade.isEmpty() ? new ArrayList<>(listFindClassesWithoutJvmFacade) : null;
        List<JvmClass> listFindClassesWithoutJavaFacade = this.myJvmFacade.getValue().findClassesWithoutJavaFacade(str, globalSearchScope);
        if (!listFindClassesWithoutJavaFacade.isEmpty()) {
            List map = ContainerUtil.map((Collection) listFindClassesWithoutJavaFacade, new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiFacadeImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return this.f$0.m6548xb4bbd446((JvmClass) obj);
                }
            });
            if (arrayList == null) {
                arrayList = new ArrayList<>(map);
            } else {
                arrayList.addAll(map);
            }
        }
        if (arrayList == null) {
            arrayList = Collections.emptyList();
        }
        if (arrayList == null) {
            $$$reportNull$$$0(16);
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: lambda$findClassesWithJvmFacade$3$org-jetbrains-kotlin-com-intellij-psi-impl-JavaPsiFacadeImpl, reason: not valid java name */
    /* synthetic */ PsiClass m6548xb4bbd446(JvmClass jvmClass) {
        return this.myConversionHelper.convertTypeDeclaration(jvmClass);
    }

    public List<PsiClass> findClassesWithoutJvmFacade(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(18);
        }
        if (shouldUseSlowResolve()) {
            List<PsiClass> listAsList = Arrays.asList(findClassesInDumbMode(str, globalSearchScope));
            if (listAsList == null) {
                $$$reportNull$$$0(19);
            }
            return listAsList;
        }
        List<PsiElementFinder> listFilteredFinders = filteredFinders();
        Predicate<PsiClass> filterFromFinders = getFilterFromFinders(globalSearchScope, listFilteredFinders);
        List<PsiClass> listEmptyList = null;
        for (PsiElementFinder psiElementFinder : listFilteredFinders) {
            PsiClass[] psiClassArrFindClasses = psiElementFinder.findClasses(str, globalSearchScope);
            if (psiClassArrFindClasses.length != 0) {
                if (listEmptyList == null) {
                    listEmptyList = new ArrayList<>(psiClassArrFindClasses.length);
                }
                filterClassesAndAppend(psiElementFinder, filterFromFinders, psiClassArrFindClasses, listEmptyList);
            }
        }
        if (listEmptyList == null) {
            listEmptyList = Collections.emptyList();
        }
        if (listEmptyList == null) {
            $$$reportNull$$$0(20);
        }
        return listEmptyList;
    }

    private static Predicate<PsiClass> getFilterFromFinders(GlobalSearchScope globalSearchScope, List<? extends PsiElementFinder> list) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(21);
        }
        if (list == null) {
            $$$reportNull$$$0(22);
        }
        Iterator<? extends PsiElementFinder> it = list.iterator();
        Predicate<PsiClass> predicate = null;
        while (it.hasNext()) {
            Predicate<PsiClass> classesFilter = it.next().getClassesFilter(globalSearchScope);
            if (classesFilter != null) {
                if (predicate != null) {
                    classesFilter = predicate.and(classesFilter);
                }
                predicate = classesFilter;
            }
        }
        return predicate;
    }

    private boolean shouldUseSlowResolve() {
        DumbService dumbService = DumbService.getInstance(getProject());
        return dumbService.isDumb() && dumbService.isAlternativeResolveEnabled();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public PsiConstantEvaluationHelper getConstantEvaluationHelper() {
        PsiConstantEvaluationHelper psiConstantEvaluationHelper = this.myConstantEvaluationHelper;
        if (psiConstantEvaluationHelper == null) {
            $$$reportNull$$$0(23);
        }
        return psiConstantEvaluationHelper;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public PsiPackage findPackage(String str) {
        if (str == null) {
            $$$reportNull$$$0(24);
        }
        PsiPackage psiPackage = this.myPackageCache.get(str);
        if (psiPackage != null) {
            return psiPackage;
        }
        Iterator<PsiElementFinder> it = filteredFinders().iterator();
        while (it.hasNext()) {
            PsiPackage psiPackageFindPackage = it.next().findPackage(str);
            if (psiPackageFindPackage != null) {
                return (PsiPackage) ConcurrencyUtil.cacheOrGet(this.myPackageCache, str, psiPackageFindPackage);
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public PsiJavaModule findModule(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(26);
        }
        Collection<PsiJavaModule> collectionFindModules = findModules(str, globalSearchScope);
        if (collectionFindModules.size() == 1) {
            return collectionFindModules.iterator().next();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public Collection<PsiJavaModule> findModules(String str, final GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(27);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(28);
        }
        Collection<PsiJavaModule> collectionComputeIfAbsent = this.myModuleCache.computeIfAbsent(globalSearchScope, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiFacadeImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ContainerUtil.createConcurrentWeakValueMap();
            }
        }).computeIfAbsent(str, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiFacadeImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6549xe6667558(globalSearchScope, (String) obj);
            }
        });
        if (collectionComputeIfAbsent == null) {
            $$$reportNull$$$0(29);
        }
        return collectionComputeIfAbsent;
    }

    /* JADX INFO: renamed from: lambda$findModules$5$org-jetbrains-kotlin-com-intellij-psi-impl-JavaPsiFacadeImpl, reason: not valid java name */
    /* synthetic */ Collection m6549xe6667558(GlobalSearchScope globalSearchScope, String str) {
        return JavaFileManager.getInstance(this.myProject).findModules(str, globalSearchScope);
    }

    private List<PsiElementFinder> filteredFinders() {
        List<PsiElementFinder> listFilterByDumbAwareness = DumbService.getInstance(getProject()).filterByDumbAwareness(PsiElementFinder.EP.getPoint(this.myProject).getExtensionList());
        if (listFilterByDumbAwareness == null) {
            $$$reportNull$$$0(30);
        }
        return listFilterByDumbAwareness;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public PsiJavaParserFacade getParserFacade() {
        PsiElementFactory elementFactory = getElementFactory();
        if (elementFactory == null) {
            $$$reportNull$$$0(31);
        }
        return elementFactory;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public PsiResolveHelper getResolveHelper() {
        PsiResolveHelper service = PsiResolveHelper.SERVICE.getInstance(this.myProject);
        if (service == null) {
            $$$reportNull$$$0(32);
        }
        return service;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public PsiNameHelper getNameHelper() {
        PsiNameHelper psiNameHelper = PsiNameHelper.getInstance(this.myProject);
        if (psiNameHelper == null) {
            $$$reportNull$$$0(33);
        }
        return psiNameHelper;
    }

    public Set<String> getClassNames(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(34);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(35);
        }
        HashSet hashSet = new HashSet();
        Iterator<PsiElementFinder> it = filteredFinders().iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().getClassNames(psiPackage, globalSearchScope));
        }
        return hashSet;
    }

    public PsiClass[] getClasses(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(37);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(38);
        }
        List<PsiElementFinder> listFilteredFinders = filteredFinders();
        Predicate<PsiClass> filterFromFinders = getFilterFromFinders(globalSearchScope, listFilteredFinders);
        ArrayList arrayList = null;
        for (PsiElementFinder psiElementFinder : listFilteredFinders) {
            PsiClass[] classes = psiElementFinder.getClasses(psiPackage, globalSearchScope);
            if (classes.length != 0) {
                if (arrayList == null) {
                    arrayList = new ArrayList(classes.length);
                }
                filterClassesAndAppend(psiElementFinder, filterFromFinders, classes, arrayList);
            }
        }
        PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
        if (arrayList != null) {
            psiClassArr = (PsiClass[]) arrayList.toArray(psiClassArr);
        }
        if (psiClassArr == null) {
            $$$reportNull$$$0(39);
        }
        return psiClassArr;
    }

    private static void filterClassesAndAppend(PsiElementFinder psiElementFinder, Predicate<? super PsiClass> predicate, PsiClass[] psiClassArr, List<? super PsiClass> list) {
        if (list == null) {
            $$$reportNull$$$0(40);
        }
        if (psiClassArr == null) {
            $$$reportNull$$$0(41);
        }
        for (PsiClass psiClass : psiClassArr) {
            if (psiClass == null) {
                LOG.error("Finder " + psiElementFinder + " returned null PsiClass");
            } else if (predicate == null || predicate.test(psiClass)) {
                list.add(psiClass);
            }
        }
    }

    public PsiFile[] getPackageFiles(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(42);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(43);
        }
        Iterator<PsiElementFinder> it = filteredFinders().iterator();
        Condition<PsiFile> conditionAnd = null;
        while (it.hasNext()) {
            Condition<PsiFile> packageFilesFilter = it.next().getPackageFilesFilter(psiPackage, globalSearchScope);
            if (packageFilesFilter != null) {
                conditionAnd = conditionAnd == null ? packageFilesFilter : Conditions.and(conditionAnd, packageFilesFilter);
            }
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (PsiDirectory psiDirectory : psiPackage.getDirectories(globalSearchScope)) {
            for (PsiFile psiFile : psiDirectory.getFiles()) {
                if (conditionAnd == null || conditionAnd.value(psiFile)) {
                    linkedHashSet.add(psiFile);
                }
            }
        }
        Iterator<PsiElementFinder> it2 = filteredFinders().iterator();
        while (it2.hasNext()) {
            Collections.addAll(linkedHashSet, it2.next().getPackageFiles(psiPackage, globalSearchScope));
        }
        PsiFile[] psiFileArr = (PsiFile[]) linkedHashSet.toArray(PsiFile.EMPTY_ARRAY);
        if (psiFileArr == null) {
            $$$reportNull$$$0(44);
        }
        return psiFileArr;
    }

    public boolean processPackageDirectories(PsiPackage psiPackage, GlobalSearchScope globalSearchScope, Processor<? super PsiDirectory> processor, boolean z) {
        if (psiPackage == null) {
            $$$reportNull$$$0(45);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(46);
        }
        if (processor == null) {
            $$$reportNull$$$0(47);
        }
        Iterator<PsiElementFinder> it = filteredFinders().iterator();
        while (it.hasNext()) {
            if (!it.next().processPackageDirectories(psiPackage, globalSearchScope, processor, z)) {
                return false;
            }
        }
        return true;
    }

    public PsiPackage[] getSubPackages(PsiPackage psiPackage, GlobalSearchScope globalSearchScope) {
        if (psiPackage == null) {
            $$$reportNull$$$0(48);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(49);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<PsiElementFinder> it = filteredFinders().iterator();
        while (it.hasNext()) {
            for (PsiPackage psiPackage2 : it.next().getSubPackages(psiPackage, globalSearchScope)) {
                linkedHashMap.putIfAbsent(psiPackage2.getName(), psiPackage2);
            }
        }
        PsiPackage[] psiPackageArr = (PsiPackage[]) linkedHashMap.values().toArray(PsiPackage.EMPTY_ARRAY);
        if (psiPackageArr == null) {
            $$$reportNull$$$0(50);
        }
        return psiPackageArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public boolean isPartOfPackagePrefix(String str) {
        if (str == null) {
            $$$reportNull$$$0(51);
        }
        Iterator<String> it = this.myFileManager.getNonTrivialPackagePrefixes().iterator();
        while (it.hasNext()) {
            if (PsiNameHelper.isSubpackageOf(it.next(), str)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public boolean isInPackage(PsiElement psiElement, PsiPackage psiPackage) {
        if (psiElement == null) {
            $$$reportNull$$$0(52);
        }
        if (psiPackage == null) {
            $$$reportNull$$$0(53);
        }
        PsiFile contextFile = FileContextUtil.getContextFile(psiElement);
        if (contextFile instanceof JavaDummyHolder) {
            return ((JavaDummyHolder) contextFile).isInPackage(psiPackage);
        }
        if (contextFile instanceof PsiJavaFile) {
            return ((PsiJavaFile) contextFile).getPackageName().equals(psiPackage.getQualifiedName());
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public boolean arePackagesTheSame(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(54);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(55);
        }
        PsiFile contextFile = FileContextUtil.getContextFile(psiElement);
        PsiFile contextFile2 = FileContextUtil.getContextFile(psiElement2);
        if (Comparing.equal(contextFile, contextFile2)) {
            return true;
        }
        boolean z = contextFile instanceof JavaDummyHolder;
        if (z && (contextFile2 instanceof JavaDummyHolder)) {
            return true;
        }
        if (z || (contextFile2 instanceof JavaDummyHolder)) {
            JavaDummyHolder javaDummyHolder = (JavaDummyHolder) (z ? contextFile : contextFile2);
            if (z) {
                contextFile = contextFile2;
            }
            return javaDummyHolder.isSamePackage(contextFile);
        }
        if ((contextFile instanceof PsiClassOwner) && (contextFile2 instanceof PsiClassOwner)) {
            return Objects.equals(((PsiClassOwner) contextFile).getPackageName(), ((PsiClassOwner) contextFile2).getPackageName());
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public Project getProject() {
        Project project = this.myProject;
        if (project == null) {
            $$$reportNull$$$0(56);
        }
        return project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public boolean isConstantExpression(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(57);
        }
        IsConstantExpressionVisitor isConstantExpressionVisitor = new IsConstantExpressionVisitor();
        psiExpression.accept(isConstantExpressionVisitor);
        return isConstantExpressionVisitor.isConstant();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade
    public PsiElementFactory getElementFactory() {
        PsiElementFactory psiElementFactory = PsiElementFactory.getInstance(this.myProject);
        if (psiElementFactory == null) {
            $$$reportNull$$$0(58);
        }
        return psiElementFactory;
    }
}
