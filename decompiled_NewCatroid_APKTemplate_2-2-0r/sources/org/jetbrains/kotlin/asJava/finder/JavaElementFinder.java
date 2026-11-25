package org.jetbrains.kotlin.asJava.finder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.asJava.KotlinAsJavaSupport;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.KotlinFinderMarker;

/* JADX INFO: compiled from: JavaElementFinder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 42\u00020\u00012\u00020\u0002:\u00014B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J#\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u00122\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¢\u0006\u0002\u0010\u0014J&\u0010\u0015\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\f0\u0019H\u0002J&\u0010\u001a\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\f0\u0019H\u0002J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0013\u001a\u00020\u000eH\u0016J&\u0010\u001d\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\f0\u0019H\u0002JB\u0010\u001e\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\f0\u00192\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!H\u0002J\u001e\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000e0%2\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J#\u0010'\u001a\b\u0012\u0004\u0012\u00020\f0\u00122\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¢\u0006\u0002\u0010(J#\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u00122\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¢\u0006\u0002\u0010+J\u001e\u0010,\u001a\b\u0012\u0004\u0012\u00020*0-2\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J#\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00122\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¢\u0006\u0002\u0010/J\u001e\u00100\u001a\u00020\u00162\f\u00101\u001a\b\u0012\u0004\u0012\u00020\f022\u0006\u00103\u001a\u00020\u0010H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\b\u001a\u00070\t¢\u0006\u0002\b\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/asJava/finder/JavaElementFinder;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementFinder;", "Lorg/jetbrains/kotlin/resolve/jvm/KotlinFinderMarker;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "kotlinAsJavaSupport", "Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupport;", "psiManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "Lorg/jetbrains/annotations/NotNull;", "findClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "qualifiedName", "", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "findClasses", "", "qualifiedNameString", "(Ljava/lang/String;Lcom/intellij/psi/search/GlobalSearchScope;)[Lcom/intellij/psi/PsiClass;", "findClassesAndObjects", "", "Lorg/jetbrains/kotlin/name/FqName;", ServerAuthenticationConstants.JSON_ANSWER, "", "findInterfaceDefaultImpls", "findPackage", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiPackage;", "findRepeatableAnnotationContainer", "findSyntheticInnerClass", "syntheticName", "predicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "", "getClassNames", "", "psiPackage", "getClasses", "(Lcom/intellij/psi/PsiPackage;Lcom/intellij/psi/search/GlobalSearchScope;)[Lcom/intellij/psi/PsiClass;", "getPackageFiles", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "(Lcom/intellij/psi/PsiPackage;Lcom/intellij/psi/search/GlobalSearchScope;)[Lcom/intellij/psi/PsiFile;", "getPackageFilesFilter", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Condition;", "getSubPackages", "(Lcom/intellij/psi/PsiPackage;Lcom/intellij/psi/search/GlobalSearchScope;)[Lcom/intellij/psi/PsiPackage;", "sortByPreferenceToSourceFile", SchemaSymbols.ATTVAL_LIST, "Lorg/jetbrains/kotlin/com/intellij/util/SmartList;", "searchScope", "Companion", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaElementFinder extends PsiElementFinder implements KotlinFinderMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final KotlinAsJavaSupport kotlinAsJavaSupport;
    private final Project project;
    private final PsiManager psiManager;

    public JavaElementFinder(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
        PsiManager psiManager = PsiManager.getInstance(project);
        Intrinsics.checkNotNullExpressionValue(psiManager, "getInstance(...)");
        this.psiManager = psiManager;
        this.kotlinAsJavaSupport = KotlinAsJavaSupport.INSTANCE.getInstance(project);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass findClass(String qualifiedName, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return (PsiClass) ArraysKt.firstOrNull(findClasses(qualifiedName, scope));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass[] findClasses(String qualifiedNameString, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(qualifiedNameString, "qualifiedNameString");
        Intrinsics.checkNotNullParameter(scope, "scope");
        if (!FqNamesUtilKt.isValidJavaFqName(qualifiedNameString)) {
            PsiClass[] EMPTY_ARRAY = PsiClass.EMPTY_ARRAY;
            Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
            return EMPTY_ARRAY;
        }
        SmartList<PsiClass> smartList = new SmartList<>();
        FqName fqName = new FqName(qualifiedNameString);
        findClassesAndObjects(fqName, scope, smartList);
        smartList.addAll(this.kotlinAsJavaSupport.getFacadeClasses(fqName, scope));
        smartList.addAll(this.kotlinAsJavaSupport.getKotlinInternalClasses(fqName, scope));
        sortByPreferenceToSourceFile(smartList, scope);
        return (PsiClass[]) smartList.toArray(new PsiClass[0]);
    }

    private final void findClassesAndObjects(FqName qualifiedName, GlobalSearchScope scope, List<PsiClass> answer) {
        KtLightClass lightClass;
        findInterfaceDefaultImpls(qualifiedName, scope, answer);
        findRepeatableAnnotationContainer(qualifiedName, scope, answer);
        for (KtClassOrObject ktClassOrObject : this.kotlinAsJavaSupport.findClassOrObjectDeclarations(qualifiedName, scope)) {
            if (!(ktClassOrObject instanceof KtEnumEntry) && (lightClass = this.kotlinAsJavaSupport.getLightClass(ktClassOrObject)) != null) {
                answer.add(lightClass);
            }
        }
    }

    private final void findInterfaceDefaultImpls(FqName qualifiedName, GlobalSearchScope scope, List<PsiClass> answer) {
        findSyntheticInnerClass(qualifiedName, JvmAbi.DEFAULT_IMPLS_CLASS_NAME, scope, answer, new Function1<KtClassOrObject, Boolean>() { // from class: org.jetbrains.kotlin.asJava.finder.JavaElementFinder.findInterfaceDefaultImpls.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtClassOrObject it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof KtClass) && ((KtClass) it).isInterface());
            }
        });
    }

    private final void findRepeatableAnnotationContainer(FqName qualifiedName, GlobalSearchScope scope, List<PsiClass> answer) {
        findSyntheticInnerClass(qualifiedName, JvmAbi.REPEATABLE_ANNOTATION_CONTAINER_NAME, scope, answer, new Function1<KtClassOrObject, Boolean>() { // from class: org.jetbrains.kotlin.asJava.finder.JavaElementFinder.findRepeatableAnnotationContainer.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtClassOrObject it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(LightClassUtilsKt.getHasRepeatableAnnotationContainer(it));
            }
        });
    }

    private final void findSyntheticInnerClass(FqName qualifiedName, String syntheticName, GlobalSearchScope scope, List<PsiClass> answer, Function1<? super KtClassOrObject, Boolean> predicate) {
        KtLightClass lightClass;
        PsiClass psiClassFindInnerClassByName;
        if (qualifiedName.isRoot() || !Intrinsics.areEqual(qualifiedName.shortName().asString(), syntheticName)) {
            return;
        }
        KotlinAsJavaSupport kotlinAsJavaSupport = this.kotlinAsJavaSupport;
        FqName fqNameParent = qualifiedName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        for (KtClassOrObject ktClassOrObject : kotlinAsJavaSupport.findClassOrObjectDeclarations(fqNameParent, scope)) {
            ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
            if (predicate.invoke(ktClassOrObject).booleanValue() && (lightClass = this.kotlinAsJavaSupport.getLightClass(ktClassOrObject)) != null && (psiClassFindInnerClassByName = lightClass.findInnerClassByName(syntheticName, false)) != null) {
                answer.add(psiClassFindInnerClassByName);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public Set<String> getClassNames(PsiPackage psiPackage, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(psiPackage, "psiPackage");
        Intrinsics.checkNotNullParameter(scope, "scope");
        FqName fqName = new FqName(psiPackage.getQualifiedName());
        Collection<KtClassOrObject> collectionFindClassOrObjectDeclarationsInPackage = this.kotlinAsJavaSupport.findClassOrObjectDeclarationsInPackage(fqName, scope);
        HashSet hashSet = new HashSet();
        hashSet.addAll(this.kotlinAsJavaSupport.getFacadeNames(fqName, scope));
        Iterator<KtClassOrObject> it = collectionFindClassOrObjectDeclarationsInPackage.iterator();
        while (it.hasNext()) {
            String name2 = it.next().getName();
            if (name2 != null) {
                hashSet.add(name2);
            }
        }
        return hashSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiPackage findPackage(String qualifiedNameString) {
        Intrinsics.checkNotNullParameter(qualifiedNameString, "qualifiedNameString");
        if (!FqNamesUtilKt.isValidJavaFqName(qualifiedNameString)) {
            return null;
        }
        FqName fqName = new FqName(qualifiedNameString);
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(this.project);
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeAllScope, "allScope(...)");
        if (this.kotlinAsJavaSupport.packageExists(fqName, globalSearchScopeAllScope)) {
            return new KtLightPackage(this.psiManager, fqName, globalSearchScopeAllScope);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiPackage[] getSubPackages(PsiPackage psiPackage, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(psiPackage, "psiPackage");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Collection<FqName> subPackages = this.kotlinAsJavaSupport.getSubPackages(new FqName(psiPackage.getQualifiedName()), scope);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(subPackages, 10));
        Iterator<T> it = subPackages.iterator();
        while (it.hasNext()) {
            arrayList.add(new KtLightPackage(this.psiManager, (FqName) it.next(), scope));
        }
        return (PsiPackage[]) arrayList.toArray(new PsiPackage[0]);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass[] getClasses(PsiPackage psiPackage, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(psiPackage, "psiPackage");
        Intrinsics.checkNotNullParameter(scope, "scope");
        SmartList<PsiClass> smartList = new SmartList<>();
        FqName fqName = new FqName(psiPackage.getQualifiedName());
        smartList.addAll(this.kotlinAsJavaSupport.getFacadeClassesInPackage(fqName, scope));
        Iterator<KtClassOrObject> it = this.kotlinAsJavaSupport.findClassOrObjectDeclarationsInPackage(fqName, scope).iterator();
        while (it.hasNext()) {
            KtLightClass lightClass = this.kotlinAsJavaSupport.getLightClass(it.next());
            if (lightClass != null) {
                smartList.add(lightClass);
            }
        }
        sortByPreferenceToSourceFile(smartList, scope);
        return (PsiClass[]) smartList.toArray(new PsiClass[0]);
    }

    private final void sortByPreferenceToSourceFile(SmartList<PsiClass> list, GlobalSearchScope searchScope) {
        if (list.size() < 2) {
            return;
        }
        SmartList<PsiClass> smartList = list;
        ContainerUtil.quickSort(smartList, INSTANCE.byClasspathComparator(searchScope));
        if (smartList.size() > 1) {
            CollectionsKt.sortWith(smartList, new Comparator() { // from class: org.jetbrains.kotlin.asJava.finder.JavaElementFinder$sortByPreferenceToSourceFile$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(Boolean.valueOf(!(((PsiClass) t) instanceof ClsClassImpl)), Boolean.valueOf(!(((PsiClass) t2) instanceof ClsClassImpl)));
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiFile[] getPackageFiles(PsiPackage psiPackage, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(psiPackage, "psiPackage");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return (PsiFile[]) this.kotlinAsJavaSupport.findFilesForPackage(new FqName(psiPackage.getQualifiedName()), scope).toArray(new PsiFile[0]);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public Condition<PsiFile> getPackageFilesFilter(final PsiPackage psiPackage, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(psiPackage, "psiPackage");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return new Condition() { // from class: org.jetbrains.kotlin.asJava.finder.JavaElementFinder$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return JavaElementFinder.getPackageFilesFilter$lambda$2(psiPackage, (PsiFile) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getPackageFilesFilter$lambda$2(PsiPackage psiPackage, PsiFile psiFile) {
        Intrinsics.checkNotNullParameter(psiPackage, "$psiPackage");
        if (psiFile instanceof KtFile) {
            return Intrinsics.areEqual(psiPackage.getQualifiedName(), ((KtFile) psiFile).getPackageFqName().asString());
        }
        return true;
    }

    /* JADX INFO: compiled from: JavaElementFinder.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/asJava/finder/JavaElementFinder$Companion;", "", "()V", "byClasspathComparator", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lkotlin/Comparator;", "searchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "getInstance", "Lorg/jetbrains/kotlin/asJava/finder/JavaElementFinder;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JavaElementFinder getInstance(Project project) {
            PsiElementFinder psiElementFinder;
            Intrinsics.checkNotNullParameter(project, "project");
            PsiElementFinder[] extensions = PsiElementFinder.EP.getPoint(project).getExtensions();
            Intrinsics.checkNotNullExpressionValue(extensions, "getExtensions(...)");
            int length = extensions.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    psiElementFinder = null;
                    break;
                }
                psiElementFinder = extensions[i];
                if (psiElementFinder instanceof JavaElementFinder) {
                    break;
                }
                i++;
            }
            JavaElementFinder javaElementFinder = (JavaElementFinder) psiElementFinder;
            if (javaElementFinder != null) {
                return javaElementFinder;
            }
            throw new IllegalStateException(("JavaElementFinder is not found for project " + project).toString());
        }

        public final Comparator<PsiElement> byClasspathComparator(final GlobalSearchScope searchScope) {
            Intrinsics.checkNotNullParameter(searchScope, "searchScope");
            return new Comparator() { // from class: org.jetbrains.kotlin.asJava.finder.JavaElementFinder$Companion$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return JavaElementFinder.Companion.byClasspathComparator$lambda$0(searchScope, (PsiElement) obj, (PsiElement) obj2);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final int byClasspathComparator$lambda$0(GlobalSearchScope searchScope, PsiElement psiElement, PsiElement psiElement2) {
            Intrinsics.checkNotNullParameter(searchScope, "$searchScope");
            VirtualFile virtualFile = PsiUtilCore.getVirtualFile(psiElement);
            VirtualFile virtualFile2 = PsiUtilCore.getVirtualFile(psiElement2);
            if (virtualFile == virtualFile2) {
                return 0;
            }
            if (virtualFile == null) {
                return -1;
            }
            if (virtualFile2 == null) {
                return 1;
            }
            return searchScope.compare(virtualFile2, virtualFile);
        }
    }
}
