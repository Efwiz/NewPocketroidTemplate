package org.jetbrains.kotlin.resolve.lazy;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtImportInfo;
import org.jetbrains.kotlin.resolve.annotations.ThrowUtilKt;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: LazyImportScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001:\u00015B9\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000eH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J2\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001a\u001a\u00020\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00110\u001d2\u0006\u0010\u001e\u001a\u00020\u0011H\u0016J\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u00182\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0012\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u00182\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0018\u0010%\u001a\u00020\u00112\u0006\u0010&\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u0014H\u0002J\u0018\u0010(\u001a\u00020\u00112\u0006\u0010&\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u0014H\u0002J\u0010\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0016J\u0018\u0010-\u001a\u00020*2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010.\u001a\u00020\tH\u0016J\"\u0010/\u001a\u0004\u0018\u00010\u0014*\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u00100\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030\u00042\u0006\u00101\u001a\u00020\u0014H\u0002J\f\u00102\u001a\u00020\u0011*\u00020\u0014H\u0002J\f\u00103\u001a\u00020\u0011*\u00020\u0014H\u0002J\f\u00104\u001a\u00020\u0011*\u00020\u0014H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/LazyImportScope;", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "parent", "importResolver", "Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolver;", "secondaryImportResolver", "filteringKind", "Lorg/jetbrains/kotlin/resolve/lazy/LazyImportScope$FilteringKind;", "debugName", "", "(Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolver;Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolver;Lorg/jetbrains/kotlin/resolve/lazy/LazyImportScope$FilteringKind;Ljava/lang/String;)V", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "computeImportedNames", "", "Lorg/jetbrains/kotlin/name/Name;", "definitelyDoesNotContainName", "", "name", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "changeNamesForAliased", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getContributedPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "isKotlinOrJvmThrowsAmbiguity", "c1", "c2", "isKotlinOrNativeThrowsAmbiguity", "printStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "recordLookup", "toString", "getClassifier", "isClassifierVisible", "descriptor", "isKotlinOrJvmThrows", "isKotlinOrNativeThrows", "isKotlinThrows", "FilteringKind", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyImportScope implements ImportingScope {
    private final String debugName;
    private final FilteringKind filteringKind;
    private final LazyImportResolver<?> importResolver;
    private final ImportingScope parent;
    private final LazyImportResolver<?> secondaryImportResolver;

    /* JADX INFO: compiled from: LazyImportScope.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/LazyImportScope$FilteringKind;", "", "(Ljava/lang/String;I)V", "ALL", "VISIBLE_CLASSES", "INVISIBLE_CLASSES", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum FilteringKind {
        ALL,
        VISIBLE_CLASSES,
        INVISIBLE_CLASSES;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<FilteringKind> getEntries() {
            return $ENTRIES;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    /* JADX INFO: renamed from: getContributedPackage */
    public PackageViewDescriptor mo7443getContributedPackage(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return null;
    }

    public LazyImportScope(ImportingScope importingScope, LazyImportResolver<?> importResolver, LazyImportResolver<?> lazyImportResolver, FilteringKind filteringKind, String debugName) {
        Intrinsics.checkNotNullParameter(importResolver, "importResolver");
        Intrinsics.checkNotNullParameter(filteringKind, "filteringKind");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        this.parent = importingScope;
        this.importResolver = importResolver;
        this.secondaryImportResolver = lazyImportResolver;
        this.filteringKind = filteringKind;
        this.debugName = debugName;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation lookupLocation) {
        return ImportingScope.DefaultImpls.getContributedClassifierIncludeDeprecated(this, name2, lookupLocation);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public ImportingScope getParent() {
        return this.parent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isClassifierVisible(LazyImportResolver<?> lazyImportResolver, ClassifierDescriptor classifierDescriptor) {
        ModuleDescriptor moduleDescriptor;
        if (this.filteringKind == FilteringKind.ALL) {
            return true;
        }
        if (DeprecationResolver.isHiddenInResolution$default(lazyImportResolver.getComponents().getDeprecationResolver(), classifierDescriptor, null, null, false, true, 14, null)) {
            return false;
        }
        Intrinsics.checkNotNull(classifierDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility");
        DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility = (DeclarationDescriptorWithVisibility) classifierDescriptor;
        DescriptorVisibility visibility = declarationDescriptorWithVisibility.getVisibilityImpl();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        boolean z = this.filteringKind == FilteringKind.VISIBLE_CLASSES;
        if (!visibility.mo7471mustCheckInImports()) {
            return z;
        }
        if (lazyImportResolver.getComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperInternalVisibilityCheckInImportingScope)) {
            DeclarationDescriptor packageFragment = lazyImportResolver.getPackageFragment();
            if (packageFragment == null) {
                packageFragment = lazyImportResolver.getComponents().getModuleDescriptor();
            }
            moduleDescriptor = packageFragment;
        } else {
            moduleDescriptor = lazyImportResolver.getComponents().getModuleDescriptor();
        }
        return DescriptorVisibilityUtils.isVisibleIgnoringReceiver(declarationDescriptorWithVisibility, moduleDescriptor, lazyImportResolver.getComponents().getLanguageVersionSettings()) == z;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        ClassifierDescriptor classifier = getClassifier(this.importResolver, name2, location);
        if (classifier != null) {
            return classifier;
        }
        LazyImportResolver<?> lazyImportResolver = this.secondaryImportResolver;
        if (lazyImportResolver != null) {
            return getClassifier(lazyImportResolver, name2, location);
        }
        return null;
    }

    private final ClassifierDescriptor getClassifier(final LazyImportResolver<?> lazyImportResolver, final Name name2, final LookupLocation lookupLocation) {
        return (ClassifierDescriptor) lazyImportResolver.getComponents().getStorageManager().compute(new Function0<ClassifierDescriptor>() { // from class: org.jetbrains.kotlin.resolve.lazy.LazyImportScope.getClassifier.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassifierDescriptor invoke() {
                Iterator it = lazyImportResolver.getIndexedImports().importsForName(name2).iterator();
                ClassifierDescriptor classifierDescriptor = null;
                while (it.hasNext()) {
                    ClassifierDescriptor contributedClassifier = lazyImportResolver.getImportScope((KtImportInfo) it.next()).mo7465getContributedClassifier(name2, lookupLocation);
                    if ((contributedClassifier instanceof ClassDescriptor) || (contributedClassifier instanceof TypeAliasDescriptor)) {
                        if (this.isClassifierVisible(lazyImportResolver, contributedClassifier)) {
                            if (classifierDescriptor != null && !Intrinsics.areEqual(classifierDescriptor, contributedClassifier)) {
                                if (!this.isKotlinOrJvmThrowsAmbiguity(contributedClassifier, classifierDescriptor) && !this.isKotlinOrNativeThrowsAmbiguity(contributedClassifier, classifierDescriptor)) {
                                    return null;
                                }
                                if (this.isKotlinThrows(contributedClassifier)) {
                                }
                            }
                            classifierDescriptor = contributedClassifier;
                        } else {
                            continue;
                        }
                    }
                }
                return classifierDescriptor;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isKotlinOrJvmThrowsAmbiguity(ClassifierDescriptor c1, ClassifierDescriptor c2) {
        return isKotlinOrJvmThrows(c1) && isKotlinOrJvmThrows(c2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isKotlinOrNativeThrowsAmbiguity(ClassifierDescriptor c1, ClassifierDescriptor c2) {
        return isKotlinOrNativeThrows(c1) && isKotlinOrNativeThrows(c2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isKotlinThrows(ClassifierDescriptor classifierDescriptor) {
        return Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull(classifierDescriptor), ThrowUtilKt.getKOTLIN_THROWS_ANNOTATION_FQ_NAME());
    }

    private final boolean isKotlinOrJvmThrows(ClassifierDescriptor classifierDescriptor) {
        if (Intrinsics.areEqual(classifierDescriptor.getShortName(), ThrowUtilKt.JVM_THROWS_ANNOTATION_FQ_NAME.shortName())) {
            return isKotlinThrows(classifierDescriptor) || Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull(classifierDescriptor), ThrowUtilKt.JVM_THROWS_ANNOTATION_FQ_NAME);
        }
        return false;
    }

    private final boolean isKotlinOrNativeThrows(ClassifierDescriptor classifierDescriptor) {
        if (Intrinsics.areEqual(classifierDescriptor.getShortName(), ThrowUtilKt.getKOTLIN_THROWS_ANNOTATION_FQ_NAME().shortName())) {
            return isKotlinThrows(classifierDescriptor) || Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull(classifierDescriptor), ThrowUtilKt.getKOTLIN_NATIVE_THROWS_ANNOTATION_FQ_NAME());
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<VariableDescriptor> getContributedVariables(final Name name2, final LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (this.filteringKind == FilteringKind.INVISIBLE_CLASSES) {
            return CollectionsKt.emptyList();
        }
        Collection collectionCollectFromImports = this.importResolver.collectFromImports(name2, new Function1<ImportingScope, Collection<? extends VariableDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.LazyImportScope.getContributedVariables.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<? extends VariableDescriptor> invoke(ImportingScope scope) {
                Intrinsics.checkNotNullParameter(scope, "scope");
                return scope.getContributedVariables(name2, location);
            }
        });
        if (!collectionCollectFromImports.isEmpty()) {
            return collectionCollectFromImports;
        }
        LazyImportResolver<?> lazyImportResolver = this.secondaryImportResolver;
        List listCollectFromImports = lazyImportResolver != null ? lazyImportResolver.collectFromImports(name2, new Function1<ImportingScope, Collection<? extends VariableDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.LazyImportScope$getContributedVariables$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<? extends VariableDescriptor> invoke(ImportingScope scope) {
                Intrinsics.checkNotNullParameter(scope, "scope");
                return scope.getContributedVariables(name2, location);
            }
        }) : null;
        if (listCollectFromImports == null) {
            listCollectFromImports = CollectionsKt.emptyList();
        }
        return listCollectFromImports;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<FunctionDescriptor> getContributedFunctions(final Name name2, final LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (this.filteringKind == FilteringKind.INVISIBLE_CLASSES) {
            return CollectionsKt.emptyList();
        }
        Collection collectionCollectFromImports = this.importResolver.collectFromImports(name2, new Function1<ImportingScope, Collection<? extends FunctionDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.LazyImportScope.getContributedFunctions.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<? extends FunctionDescriptor> invoke(ImportingScope scope) {
                Intrinsics.checkNotNullParameter(scope, "scope");
                return scope.getContributedFunctions(name2, location);
            }
        });
        if (!collectionCollectFromImports.isEmpty()) {
            return collectionCollectFromImports;
        }
        LazyImportResolver<?> lazyImportResolver = this.secondaryImportResolver;
        List listCollectFromImports = lazyImportResolver != null ? lazyImportResolver.collectFromImports(name2, new Function1<ImportingScope, Collection<? extends FunctionDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.LazyImportScope$getContributedFunctions$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<? extends FunctionDescriptor> invoke(ImportingScope scope) {
                Intrinsics.checkNotNullParameter(scope, "scope");
                return scope.getContributedFunctions(name2, location);
            }
        }) : null;
        if (listCollectFromImports == null) {
            listCollectFromImports = CollectionsKt.emptyList();
        }
        return listCollectFromImports;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(final DescriptorKindFilter kindFilter, final Function1<? super Name, Boolean> nameFilter, final boolean changeNamesForAliased) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        if (this.filteringKind == FilteringKind.INVISIBLE_CLASSES) {
            return CollectionsKt.emptyList();
        }
        StorageManager storageManager = this.importResolver.getComponents().getStorageManager();
        LazyImportResolver<?> lazyImportResolver = this.secondaryImportResolver;
        if (lazyImportResolver != null) {
            lazyImportResolver.getComponents().getStorageManager();
        }
        return (Collection) storageManager.compute(new Function0<LinkedHashSet<DeclarationDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.LazyImportScope.getContributedDescriptors.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LinkedHashSet<DeclarationDescriptor> invoke() {
                LinkedHashSet<DeclarationDescriptor> linkedHashSet = new LinkedHashSet<>();
                HashSet hashSet = LazyImportScope.this.secondaryImportResolver == null ? null : new HashSet();
                for (KtImportInfo ktImportInfo : LazyImportScope.this.importResolver.getIndexedImports().getImports()) {
                    Name importedName = ktImportInfo.getImportedName();
                    if (importedName == null || nameFilter.invoke(importedName).booleanValue()) {
                        Collection<DeclarationDescriptor> contributedDescriptors = LazyImportScope.this.importResolver.getImportScope(ktImportInfo).getContributedDescriptors(kindFilter, nameFilter, changeNamesForAliased);
                        linkedHashSet.addAll(contributedDescriptors);
                        if (hashSet != null) {
                            Iterator<DeclarationDescriptor> it = contributedDescriptors.iterator();
                            while (it.hasNext()) {
                                hashSet.add(it.next().getShortName());
                            }
                        }
                    }
                }
                LazyImportResolver lazyImportResolver2 = LazyImportScope.this.secondaryImportResolver;
                if (lazyImportResolver2 != null) {
                    DescriptorKindFilter descriptorKindFilter = kindFilter;
                    Function1<Name, Boolean> function1 = nameFilter;
                    boolean z = changeNamesForAliased;
                    for (KtImportInfo ktImportInfo2 : lazyImportResolver2.getIndexedImports().getImports()) {
                        for (DeclarationDescriptor declarationDescriptor : lazyImportResolver2.getImportScope(ktImportInfo2).getContributedDescriptors(descriptorKindFilter, function1, z)) {
                            Intrinsics.checkNotNull(hashSet);
                            if (!hashSet.contains(declarationDescriptor.getShortName())) {
                                linkedHashSet.add(declarationDescriptor);
                            }
                        }
                    }
                }
                return linkedHashSet;
            }
        });
    }

    public String toString() {
        return "LazyImportScope: " + this.debugName;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public void printStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println(getClass().getSimpleName(), ": ", this.debugName, " {");
        p.pushIndent();
        p.popIndent();
        p.println("}");
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        LazyImportResolver<?> lazyImportResolver;
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.importResolver.definitelyDoesNotContainName(name2) && ((lazyImportResolver = this.secondaryImportResolver) == null || lazyImportResolver.definitelyDoesNotContainName(name2));
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.importResolver.recordLookup(name2, location);
        LazyImportResolver<?> lazyImportResolver = this.secondaryImportResolver;
        if (lazyImportResolver != null) {
            lazyImportResolver.recordLookup(name2, location);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    public Set<Name> computeImportedNames() {
        Set<Name> allNames = this.importResolver.getAllNames();
        if (allNames == null) {
            return null;
        }
        Set<Name> set = allNames;
        LazyImportResolver<?> lazyImportResolver = this.secondaryImportResolver;
        Set<Name> allNames2 = lazyImportResolver != null ? lazyImportResolver.getAllNames() : null;
        if (allNames2 == null) {
            allNames2 = SetsKt.emptySet();
        }
        return CollectionsKt.union(set, allNames2);
    }
}
