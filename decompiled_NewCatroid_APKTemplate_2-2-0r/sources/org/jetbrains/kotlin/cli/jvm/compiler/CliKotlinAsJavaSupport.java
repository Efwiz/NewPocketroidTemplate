package org.jetbrains.kotlin.cli.jvm.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.asJava.classes.KtDescriptorBasedFakeLightClass;
import org.jetbrains.kotlin.asJava.classes.KtFakeLightClass;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiSearchScopeUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.load.java.components.FilesByFacadeFqNameIndexer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.lazy.ResolveSessionUtils;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: CliKotlinAsJavaSupport.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0002H\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J \u0010\u0013\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0017H\u0014J\u0010\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J\u001e\u0010\u0019\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0017H\u0014J\u0010\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0014J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u000e\u001a\u00020\u0002H\u0014J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u0002H\u0014J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00120#2\u0006\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\tH\u0016J\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00120#2\u0006\u0010'\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\tH\u0016J\u001e\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\tH\u0016J\u001e\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00020\u00172\u0006\u0010'\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\tH\u0016J\u001e\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00020#2\u0006\u0010'\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\tH\u0016J\u001e\u0010+\u001a\b\u0012\u0004\u0012\u00020\u001c0#2\u0006\u0010,\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\tH\u0016J\u0010\u0010-\u001a\u00020.2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u001e\u0010/\u001a\b\u0012\u0004\u0012\u0002000#2\u0006\u0010$\u001a\u00020\u00152\u0006\u00101\u001a\u00020\tH\u0016J\u001e\u00102\u001a\b\u0012\u0004\u0012\u00020\u00150#2\u0006\u00103\u001a\u00020\u00152\u0006\u00101\u001a\u00020\tH\u0016J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u000207H\u0014J\u0018\u00108\u001a\u00020 2\u0006\u0010$\u001a\u00020\u00152\u0006\u00101\u001a\u00020\tH\u0016J\f\u00109\u001a\u00020\u0002*\u00020\u0002H\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\b\u001a\u00020\t*\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/CliKotlinAsJavaSupport;", "Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupportBase;", "Lorg/jetbrains/kotlin/psi/KtFile;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "traceHolder", "Lorg/jetbrains/kotlin/cli/jvm/compiler/CliTraceHolder;", "(Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/cli/jvm/compiler/CliTraceHolder;)V", "contentSearchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "getContentSearchScope", "(Lorg/jetbrains/kotlin/psi/KtFile;)Lcom/intellij/psi/search/GlobalSearchScope;", "createFacadeForSyntheticFile", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForFacade;", "file", "createInstanceOfDecompiledLightClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "createInstanceOfDecompiledLightFacade", "facadeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "files", "", "createInstanceOfLightClass", "createInstanceOfLightFacade", "createInstanceOfLightScript", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "declarationLocation", "Lorg/jetbrains/kotlin/asJava/KotlinAsJavaSupportBase$DeclarationLocation;", "facadeIsApplicable", "", "module", "findClassOrObjectDeclarations", "", "fqName", "searchScope", "findClassOrObjectDeclarationsInPackage", "packageFqName", "findFilesForFacade", "findFilesForFacadeByPackage", "findFilesForPackage", "findFilesForScript", "scriptFqName", "getFakeLightClass", "Lorg/jetbrains/kotlin/asJava/classes/KtFakeLightClass;", "getKotlinInternalClasses", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "scope", "getSubPackages", "fqn", "librariesTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "packageExists", "findModule", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliKotlinAsJavaSupport extends KotlinAsJavaSupportBase<KtFile> {
    private final CliTraceHolder traceHolder;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase
    public KtFile findModule(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        return ktFile;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CliKotlinAsJavaSupport(Project project, CliTraceHolder traceHolder) {
        super(project);
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(traceHolder, "traceHolder");
        this.traceHolder = traceHolder;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public List<KtFile> findFilesForFacadeByPackage(FqName packageFqName, GlobalSearchScope searchScope) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        Collection collection = (Collection) this.traceHolder.getBindingContext().get(FilesByFacadeFqNameIndexer.FACADE_FILES_BY_PACKAGE_NAME, packageFqName);
        if (collection != null) {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : collection) {
                if (PsiSearchScopeUtil.isInScope(searchScope, (PsiElement) obj)) {
                    arrayList2.add(obj);
                }
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        return arrayList == null ? CollectionsKt.emptyList() : arrayList;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase
    protected KtLightClassForFacade createInstanceOfDecompiledLightFacade(FqName facadeFqName, List<? extends KtFile> files) {
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        Intrinsics.checkNotNullParameter(files, "files");
        throw new IllegalStateException("Should not be called".toString());
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase
    protected ModificationTracker librariesTracker(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        throw new IllegalStateException("Should not be called".toString());
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase
    protected KtLightClassForFacade createInstanceOfLightFacade(FqName facadeFqName, List<? extends KtFile> files) {
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        Intrinsics.checkNotNullParameter(files, "files");
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = ((KtFile) CollectionsKt.first((List) files)).getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).createUltraLightClassForFacade(facadeFqName, files);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase
    public GlobalSearchScope getContentSearchScope(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(ktFile.getProject());
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeAllScope, "allScope(...)");
        return globalSearchScopeAllScope;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase
    public boolean facadeIsApplicable(KtFile module, KtFile file) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(file, "file");
        return !module.getIsCompiled();
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public List<KtFile> findFilesForFacade(FqName facadeFqName, GlobalSearchScope searchScope) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        if (facadeFqName.isRoot()) {
            return CollectionsKt.emptyList();
        }
        Collection collection = (Collection) this.traceHolder.getBindingContext().get(FilesByFacadeFqNameIndexer.FACADE_FILES_BY_FQ_NAME, facadeFqName);
        if (collection != null) {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : collection) {
                if (PsiSearchScopeUtil.isInScope(searchScope, (PsiElement) obj)) {
                    arrayList2.add(obj);
                }
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        return arrayList == null ? CollectionsKt.emptyList() : arrayList;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public Collection<PsiClass> getKotlinInternalClasses(FqName fqName, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public KtFakeLightClass getFakeLightClass(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        return new KtDescriptorBasedFakeLightClass(classOrObject);
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public Collection<KtClassOrObject> findClassOrObjectDeclarationsInPackage(FqName packageFqName, GlobalSearchScope searchScope) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        Collection<KtFile> collectionFindFilesForPackage = findFilesForPackage(packageFqName, searchScope);
        SmartList smartList = new SmartList();
        Iterator<KtFile> it = collectionFindFilesForPackage.iterator();
        while (it.hasNext()) {
            for (KtDeclaration ktDeclaration : it.next().getDeclarations()) {
                if (ktDeclaration instanceof KtClassOrObject) {
                    smartList.add(ktDeclaration);
                }
            }
        }
        return smartList;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public boolean packageExists(FqName fqName, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return !this.traceHolder.getModule().getPackage(fqName).isEmpty();
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public Collection<FqName> getSubPackages(FqName fqn, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(fqn, "fqn");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Collection<DeclarationDescriptor> contributedDescriptors = this.traceHolder.getModule().getPackage(fqn).getMemberScope().getContributedDescriptors(DescriptorKindFilter.PACKAGES, MemberScope.INSTANCE.getALL_NAME_FILTER());
        ArrayList arrayList = new ArrayList();
        for (DeclarationDescriptor declarationDescriptor : contributedDescriptors) {
            PackageViewDescriptor packageViewDescriptor = declarationDescriptor instanceof PackageViewDescriptor ? (PackageViewDescriptor) declarationDescriptor : null;
            FqName fqName = packageViewDescriptor != null ? packageViewDescriptor.getFqName() : null;
            if (fqName != null) {
                arrayList.add(fqName);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase
    protected KtLightClass createInstanceOfLightScript(KtScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = script.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).createUltraLightClassForScript(script);
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public Collection<KtClassOrObject> findClassOrObjectDeclarations(FqName fqName, GlobalSearchScope searchScope) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        Collection<ClassDescriptor> classDescriptorsByFqName = ResolveSessionUtils.getClassDescriptorsByFqName(this.traceHolder.getModule(), fqName);
        Intrinsics.checkNotNullExpressionValue(classDescriptorsByFqName, "getClassDescriptorsByFqName(...)");
        ArrayList arrayList = new ArrayList();
        for (ClassDescriptor classDescriptor : classDescriptorsByFqName) {
            Intrinsics.checkNotNull(classDescriptor);
            PsiElement sourceFromDescriptor = DescriptorToSourceUtils.getSourceFromDescriptor(classDescriptor);
            KtClassOrObject ktClassOrObject = ((sourceFromDescriptor instanceof KtClassOrObject) && PsiSearchScopeUtil.isInScope(searchScope, sourceFromDescriptor)) ? (KtClassOrObject) sourceFromDescriptor : null;
            if (ktClassOrObject != null) {
                arrayList.add(ktClassOrObject);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public Collection<KtFile> findFilesForPackage(FqName packageFqName, GlobalSearchScope searchScope) {
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        Collection collection = (Collection) this.traceHolder.getBindingContext().get(BindingContext.PACKAGE_TO_FILES, packageFqName);
        if (collection != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : collection) {
                if (PsiSearchScopeUtil.isInScope(searchScope, (PsiElement) obj)) {
                    arrayList.add(obj);
                }
            }
            arrayListEmptyList = arrayList;
        } else {
            arrayListEmptyList = null;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        return arrayListEmptyList;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public Collection<KtScript> findFilesForScript(FqName scriptFqName, GlobalSearchScope searchScope) {
        Intrinsics.checkNotNullParameter(scriptFqName, "scriptFqName");
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        FqName fqNameParent = scriptFqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        Collection<KtFile> collectionFindFilesForPackage = findFilesForPackage(fqNameParent, searchScope);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collectionFindFilesForPackage.iterator();
        while (it.hasNext()) {
            KtScript script = ((KtFile) it.next()).getScript();
            if (script == null || !Intrinsics.areEqual(script.mo7391getFqName(), scriptFqName)) {
                script = null;
            }
            if (script != null) {
                arrayList.add(script);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase, org.jetbrains.kotlin.asJava.KotlinAsJavaSupport
    public KtLightClassForFacade createFacadeForSyntheticFile(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        throw new IllegalStateException("Should not be called".toString());
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase
    protected KotlinAsJavaSupportBase.DeclarationLocation declarationLocation(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return KotlinAsJavaSupportBase.DeclarationLocation.ProjectSources;
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase
    protected KtLightClass createInstanceOfDecompiledLightClass(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        throw new IllegalStateException("Should not be called".toString());
    }

    @Override // org.jetbrains.kotlin.asJava.KotlinAsJavaSupportBase
    protected KtLightClass createInstanceOfLightClass(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = classOrObject.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).createUltraLightClass(classOrObject);
    }
}
