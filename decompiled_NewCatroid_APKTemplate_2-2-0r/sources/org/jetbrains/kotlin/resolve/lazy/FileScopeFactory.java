package org.jetbrains.kotlin.resolve.lazy;

import gnu.trove.THashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.KtImportInfo;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.ImportPath;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.extensions.ExtraImportsProviderExtension;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeKt;
import org.jetbrains.kotlin.resolve.scopes.SubpackagesImportingScope;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: FileScopeFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001:\u00056789:B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJN\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u00192\u0006\u0010\u001a\u001a\u00020\u00052\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0010\b\u0002\u0010 \u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u0012H\u0002J$\u0010!\u001a\u00020\f2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00170\u001c2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0002JH\u0010#\u001a\u00020$2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020%0\u00192\u0006\u0010\u001a\u001a\u00020\u00052\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0010\b\u0002\u0010 \u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u0012H\u0002J$\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\n\b\u0002\u0010*\u001a\u0004\u0018\u00010+2\b\b\u0002\u0010,\u001a\u00020-J6\u0010.\u001a\u00020+2\u0006\u0010/\u001a\u0002002\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020+H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006;²\u0006\u001a\u0010<\u001a\u0012\u0012\f\u0012\n ?*\u0004\u0018\u00010>0>\u0018\u00010=X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory;", "", "topLevelDescriptorProvider", "Lorg/jetbrains/kotlin/resolve/lazy/TopLevelDescriptorProvider;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "components", "Lorg/jetbrains/kotlin/resolve/lazy/ImportResolutionComponents;", "(Lorg/jetbrains/kotlin/resolve/lazy/TopLevelDescriptorProvider;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;Lorg/jetbrains/kotlin/resolve/lazy/ImportResolutionComponents;)V", "defaultImportResolvers", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$DefaultImportResolvers;", "getDefaultImportResolvers", "()Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$DefaultImportResolvers;", "defaultImportResolvers$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "defaultImports", "", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$DefaultImportImpl;", "defaultLowPriorityImports", "createDefaultImportResolver", "Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolver;", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "indexedImports", "Lorg/jetbrains/kotlin/resolve/lazy/IndexedImports;", "trace", "aliasImportNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "packageFragment", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "excludedImports", "createDefaultImportResolvers", "extraImports", "createImportResolver", "Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolverForKtImportDirective;", "Lorg/jetbrains/kotlin/psi/KtImportDirective;", "createScopesForFile", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopes;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "existingImports", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "createDefaultImportingScopes", "", "currentPackageScope", "packageView", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "fromDescriptor", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$DummyContainerDescriptor;", "filteringKind", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$FilteringKind;", "parentScope", "DefaultImportImpl", "DefaultImportResolvers", "DummyContainerDescriptor", "FilesScopesBuilder", "FilteringKind", "frontend", "names", "Lgnu/trove/THashSet;", "Lorg/jetbrains/kotlin/name/Name;", JvmProtoBufUtil.PLATFORM_TYPE_ID}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileScopeFactory {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FileScopeFactory.class), "defaultImportResolvers", "getDefaultImportResolvers()Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$DefaultImportResolvers;"))};
    private final PlatformDependentAnalyzerServices analyzerServices;
    private final BindingTrace bindingTrace;
    private final ImportResolutionComponents components;

    /* JADX INFO: renamed from: defaultImportResolvers$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue defaultImportResolvers;
    private final List<DefaultImportImpl> defaultImports;
    private final List<DefaultImportImpl> defaultLowPriorityImports;
    private final TopLevelDescriptorProvider topLevelDescriptorProvider;

    /* JADX INFO: compiled from: FileScopeFactory.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$FilteringKind;", "", "(Ljava/lang/String;I)V", "VISIBLE_CLASSES", "INVISIBLE_CLASSES", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum FilteringKind {
        VISIBLE_CLASSES,
        INVISIBLE_CLASSES;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<FilteringKind> getEntries() {
            return $ENTRIES;
        }
    }

    public FileScopeFactory(TopLevelDescriptorProvider topLevelDescriptorProvider, BindingTrace bindingTrace, PlatformDependentAnalyzerServices analyzerServices, ImportResolutionComponents components) {
        Intrinsics.checkNotNullParameter(topLevelDescriptorProvider, "topLevelDescriptorProvider");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(components, "components");
        this.topLevelDescriptorProvider = topLevelDescriptorProvider;
        this.bindingTrace = bindingTrace;
        this.analyzerServices = analyzerServices;
        this.components = components;
        List<ImportPath> defaultImports = analyzerServices.getDefaultImports(components.getLanguageVersionSettings(), false);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(defaultImports, 10));
        Iterator<T> it = defaultImports.iterator();
        while (it.hasNext()) {
            arrayList.add(new DefaultImportImpl((ImportPath) it.next()));
        }
        this.defaultImports = arrayList;
        List<ImportPath> defaultLowPriorityImports = this.analyzerServices.getDefaultLowPriorityImports();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(defaultLowPriorityImports, 10));
        Iterator<T> it2 = defaultLowPriorityImports.iterator();
        while (it2.hasNext()) {
            arrayList2.add(new DefaultImportImpl((ImportPath) it2.next()));
        }
        this.defaultLowPriorityImports = arrayList2;
        this.defaultImportResolvers = this.components.getStorageManager().createLazyValue(new Function0<DefaultImportResolvers>() { // from class: org.jetbrains.kotlin.resolve.lazy.FileScopeFactory$defaultImportResolvers$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FileScopeFactory.DefaultImportResolvers invoke() {
                return this.this$0.createDefaultImportResolvers(CollectionsKt.emptyList(), CollectionsKt.emptyList());
            }
        });
    }

    /* JADX INFO: compiled from: FileScopeFactory.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$DefaultImportImpl;", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "importPath", "Lorg/jetbrains/kotlin/resolve/ImportPath;", "(Lorg/jetbrains/kotlin/resolve/ImportPath;)V", "aliasName", "", "getAliasName", "()Ljava/lang/String;", "importContent", "Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent$FqNameBased;", "getImportContent", "()Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent$FqNameBased;", "importedFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getImportedFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "isAllUnder", "", "()Z", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class DefaultImportImpl implements KtImportInfo {
        private final KtImportInfo.ImportContent.FqNameBased importContent;
        private final ImportPath importPath;

        public DefaultImportImpl(ImportPath importPath) {
            Intrinsics.checkNotNullParameter(importPath, "importPath");
            this.importPath = importPath;
            this.importContent = new KtImportInfo.ImportContent.FqNameBased(importPath.getFqName());
        }

        @Override // org.jetbrains.kotlin.psi.KtImportInfo
        public boolean isAllUnder() {
            return this.importPath.isAllUnder();
        }

        @Override // org.jetbrains.kotlin.psi.KtImportInfo
        public KtImportInfo.ImportContent.FqNameBased getImportContent() {
            return this.importContent;
        }

        @Override // org.jetbrains.kotlin.psi.KtImportInfo
        public String getAliasName() {
            Name alias = this.importPath.getAlias();
            if (alias != null) {
                return alias.asString();
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.psi.KtImportInfo
        public FqName getImportedFqName() {
            return this.importPath.getFqName();
        }
    }

    public static /* synthetic */ FileScopes createScopesForFile$default(FileScopeFactory fileScopeFactory, KtFile ktFile, ImportingScope importingScope, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            importingScope = null;
        }
        if ((i & 4) != 0) {
            z = true;
        }
        return fileScopeFactory.createScopesForFile(ktFile, importingScope, z);
    }

    public final FileScopes createScopesForFile(KtFile file, ImportingScope existingImports, boolean createDefaultImportingScopes) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new FilesScopesBuilder(this, file, existingImports, this.topLevelDescriptorProvider.getPackageFragmentOrDiagnoseFailure(file.getPackageFqName(), file), this.components.getModuleDescriptor().getPackage(file.getPackageFqName()), createDefaultImportingScopes).getResult();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FileScopeFactory.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B/\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J9\u0010\u000f\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$DefaultImportResolvers;", "", "explicit", "Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolver;", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "allUnder", "lowPriority", "(Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolver;Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolver;Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolver;)V", "getAllUnder", "()Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolver;", "getExplicit", "getLowPriority", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class DefaultImportResolvers {
        private final LazyImportResolver<KtImportInfo> allUnder;
        private final LazyImportResolver<KtImportInfo> explicit;
        private final LazyImportResolver<KtImportInfo> lowPriority;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DefaultImportResolvers copy$default(DefaultImportResolvers defaultImportResolvers, LazyImportResolver lazyImportResolver, LazyImportResolver lazyImportResolver2, LazyImportResolver lazyImportResolver3, int i, Object obj) {
            if ((i & 1) != 0) {
                lazyImportResolver = defaultImportResolvers.explicit;
            }
            if ((i & 2) != 0) {
                lazyImportResolver2 = defaultImportResolvers.allUnder;
            }
            if ((i & 4) != 0) {
                lazyImportResolver3 = defaultImportResolvers.lowPriority;
            }
            return defaultImportResolvers.copy(lazyImportResolver, lazyImportResolver2, lazyImportResolver3);
        }

        public final LazyImportResolver<KtImportInfo> component1() {
            return this.explicit;
        }

        public final LazyImportResolver<KtImportInfo> component2() {
            return this.allUnder;
        }

        public final LazyImportResolver<KtImportInfo> component3() {
            return this.lowPriority;
        }

        public final DefaultImportResolvers copy(LazyImportResolver<KtImportInfo> explicit, LazyImportResolver<KtImportInfo> allUnder, LazyImportResolver<KtImportInfo> lowPriority) {
            Intrinsics.checkNotNullParameter(explicit, "explicit");
            Intrinsics.checkNotNullParameter(allUnder, "allUnder");
            Intrinsics.checkNotNullParameter(lowPriority, "lowPriority");
            return new DefaultImportResolvers(explicit, allUnder, lowPriority);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DefaultImportResolvers)) {
                return false;
            }
            DefaultImportResolvers defaultImportResolvers = (DefaultImportResolvers) other;
            return Intrinsics.areEqual(this.explicit, defaultImportResolvers.explicit) && Intrinsics.areEqual(this.allUnder, defaultImportResolvers.allUnder) && Intrinsics.areEqual(this.lowPriority, defaultImportResolvers.lowPriority);
        }

        public int hashCode() {
            return (((this.explicit.hashCode() * 31) + this.allUnder.hashCode()) * 31) + this.lowPriority.hashCode();
        }

        public String toString() {
            return "DefaultImportResolvers(explicit=" + this.explicit + ", allUnder=" + this.allUnder + ", lowPriority=" + this.lowPriority + ')';
        }

        public DefaultImportResolvers(LazyImportResolver<KtImportInfo> explicit, LazyImportResolver<KtImportInfo> allUnder, LazyImportResolver<KtImportInfo> lowPriority) {
            Intrinsics.checkNotNullParameter(explicit, "explicit");
            Intrinsics.checkNotNullParameter(allUnder, "allUnder");
            Intrinsics.checkNotNullParameter(lowPriority, "lowPriority");
            this.explicit = explicit;
            this.allUnder = allUnder;
            this.lowPriority = lowPriority;
        }

        public final LazyImportResolver<KtImportInfo> getExplicit() {
            return this.explicit;
        }

        public final LazyImportResolver<KtImportInfo> getAllUnder() {
            return this.allUnder;
        }

        public final LazyImportResolver<KtImportInfo> getLowPriority() {
            return this.lowPriority;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DefaultImportResolvers createDefaultImportResolvers(Collection<? extends KtImportInfo> extraImports, Collection<FqName> aliasImportNames) {
        TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(this.bindingTrace, "Transient trace for default imports lazy resolve", (Object) false);
        Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
        ArrayList arrayListConcat = FileScopeFactoryKt.concat(this.defaultImports, extraImports);
        if (!aliasImportNames.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : arrayListConcat) {
                KtImportInfo ktImportInfo = (KtImportInfo) obj;
                if (ktImportInfo.isAllUnder() || !CollectionsKt.contains(aliasImportNames, ktImportInfo.getImportedFqName())) {
                    arrayList.add(obj);
                }
            }
            arrayListConcat = arrayList;
        }
        StorageManager storageManager = this.components.getStorageManager();
        Collection collection = arrayListConcat;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : collection) {
            if (!((KtImportInfo) obj2).isAllUnder()) {
                arrayList2.add(obj2);
            }
        }
        TemporaryBindingTrace temporaryBindingTrace = temporaryBindingTraceCreate;
        LazyImportResolver lazyImportResolverCreateDefaultImportResolver$default = createDefaultImportResolver$default(this, new ExplicitImportsIndexed((KtImportInfo[]) arrayList2.toArray(new KtImportInfo[0]), storageManager), temporaryBindingTrace, aliasImportNames, null, null, 16, null);
        ArrayList arrayList3 = new ArrayList();
        for (Object obj3 : collection) {
            if (((KtImportInfo) obj3).isAllUnder()) {
                arrayList3.add(obj3);
            }
        }
        LazyImportResolver<KtImportInfo> lazyImportResolverCreateDefaultImportResolver = createDefaultImportResolver(new IndexedImports<>((KtImportInfo[]) arrayList3.toArray(new KtImportInfo[0])), temporaryBindingTrace, aliasImportNames, null, this.analyzerServices.getExcludedImports());
        List<DefaultImportImpl> list = this.defaultLowPriorityImports;
        List<DefaultImportImpl> list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (it.hasNext() && ((DefaultImportImpl) it.next()).isAllUnder()) {
            }
        }
        ArrayList arrayList4 = new ArrayList();
        for (Object obj4 : list) {
            if (((KtImportInfo) obj4).isAllUnder()) {
                arrayList4.add(obj4);
            }
        }
        return new DefaultImportResolvers(lazyImportResolverCreateDefaultImportResolver$default, lazyImportResolverCreateDefaultImportResolver, createDefaultImportResolver$default(this, new IndexedImports((KtImportInfo[]) arrayList4.toArray(new KtImportInfo[0])), temporaryBindingTrace, aliasImportNames, null, null, 16, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DefaultImportResolvers getDefaultImportResolvers() {
        return (DefaultImportResolvers) StorageKt.getValue(this.defaultImportResolvers, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ LazyImportResolver createDefaultImportResolver$default(FileScopeFactory fileScopeFactory, IndexedImports indexedImports, BindingTrace bindingTrace, Collection collection, PackageFragmentDescriptor packageFragmentDescriptor, List list, int i, Object obj) {
        if ((i & 16) != 0) {
            list = null;
        }
        return fileScopeFactory.createDefaultImportResolver(indexedImports, bindingTrace, collection, packageFragmentDescriptor, list);
    }

    private final LazyImportResolver<KtImportInfo> createDefaultImportResolver(IndexedImports<KtImportInfo> indexedImports, BindingTrace trace, Collection<FqName> aliasImportNames, PackageFragmentDescriptor packageFragment, List<FqName> excludedImports) {
        return new LazyImportResolver<>(this.components, indexedImports, FileScopeFactoryKt.concat(aliasImportNames, excludedImports), trace, packageFragment);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ LazyImportResolverForKtImportDirective createImportResolver$default(FileScopeFactory fileScopeFactory, IndexedImports indexedImports, BindingTrace bindingTrace, Collection collection, PackageFragmentDescriptor packageFragmentDescriptor, List list, int i, Object obj) {
        if ((i & 16) != 0) {
            list = null;
        }
        return fileScopeFactory.createImportResolver(indexedImports, bindingTrace, collection, packageFragmentDescriptor, list);
    }

    private final LazyImportResolverForKtImportDirective createImportResolver(IndexedImports<KtImportDirective> indexedImports, BindingTrace trace, Collection<FqName> aliasImportNames, PackageFragmentDescriptor packageFragment, List<FqName> excludedImports) {
        return new LazyImportResolverForKtImportDirective(this.components, indexedImports, FileScopeFactoryKt.concat(aliasImportNames, excludedImports), trace, packageFragment);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FileScopeFactory.kt */
    @Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010*\u001a\u00020+H\u0002J\u0006\u0010,\u001a\u00020-R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0016\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0018\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0011R\u0011\u0010\u001f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010&\u001a\u00020'¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$FilesScopesBuilder;", "", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "existingImports", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "packageFragment", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "packageView", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "createDefaultImportingScopes", "", "(Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory;Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;Z)V", "aliasImportNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "getAliasImportNames", "()Ljava/util/List;", "allUnderImportResolver", "Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolverForKtImportDirective;", "getAllUnderImportResolver", "()Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolverForKtImportDirective;", "explicitImportResolver", "getExplicitImportResolver", "importResolver", "Lorg/jetbrains/kotlin/resolve/lazy/ImportForceResolver;", "getImportResolver", "()Lorg/jetbrains/kotlin/resolve/lazy/ImportForceResolver;", "imports", "Lorg/jetbrains/kotlin/psi/KtImportDirective;", "getImports", "lazyImportingScope", "getLazyImportingScope", "()Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "lexicalScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope$Base;", "getLexicalScope", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope$Base;", "result", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopes;", "getResult", "()Lorg/jetbrains/kotlin/resolve/lazy/FileScopes;", "createDefaultImportResolversForFile", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$DefaultImportResolvers;", "createImportingScope", "Lorg/jetbrains/kotlin/resolve/lazy/LazyImportScope;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class FilesScopesBuilder {
        private final List<FqName> aliasImportNames;
        private final LazyImportResolverForKtImportDirective allUnderImportResolver;
        private final boolean createDefaultImportingScopes;
        private final ImportingScope existingImports;
        private final LazyImportResolverForKtImportDirective explicitImportResolver;
        private final KtFile file;
        private final ImportForceResolver importResolver;
        private final List<KtImportDirective> imports;
        private final ImportingScope lazyImportingScope;
        private final LexicalScope.Base lexicalScope;
        private final PackageFragmentDescriptor packageFragment;
        private final PackageViewDescriptor packageView;
        private final FileScopes result;
        final /* synthetic */ FileScopeFactory this$0;

        public FilesScopesBuilder(FileScopeFactory fileScopeFactory, KtFile file, ImportingScope importingScope, PackageFragmentDescriptor packageFragment, PackageViewDescriptor packageView, boolean z) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
            Intrinsics.checkNotNullParameter(packageView, "packageView");
            this.this$0 = fileScopeFactory;
            this.file = file;
            this.existingImports = importingScope;
            this.packageFragment = packageFragment;
            this.packageView = packageView;
            this.createDefaultImportingScopes = z;
            List<KtImportDirective> importDirectives = file.getImportDirectives();
            this.imports = importDirectives;
            ArrayList arrayList = new ArrayList();
            for (KtImportDirective ktImportDirective : importDirectives) {
                FqName importedFqName = ktImportDirective.getAliasName() != null ? ktImportDirective.getImportedFqName() : null;
                if (importedFqName != null) {
                    arrayList.add(importedFqName);
                }
            }
            this.aliasImportNames = arrayList;
            FileScopeFactory fileScopeFactory2 = this.this$0;
            List<KtImportDirective> list = this.imports;
            StorageManager storageManager = fileScopeFactory2.components.getStorageManager();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : list) {
                if (!((KtImportInfo) obj).isAllUnder()) {
                    arrayList2.add(obj);
                }
            }
            this.explicitImportResolver = FileScopeFactory.createImportResolver$default(fileScopeFactory2, new ExplicitImportsIndexed((KtImportInfo[]) arrayList2.toArray(new KtImportDirective[0]), storageManager), this.this$0.bindingTrace, this.aliasImportNames, this.packageFragment, null, 16, null);
            FileScopeFactory fileScopeFactory3 = this.this$0;
            List<KtImportDirective> list2 = this.imports;
            ArrayList arrayList3 = new ArrayList();
            for (Object obj2 : list2) {
                if (((KtImportInfo) obj2).isAllUnder()) {
                    arrayList3.add(obj2);
                }
            }
            this.allUnderImportResolver = FileScopeFactory.createImportResolver$default(fileScopeFactory3, new IndexedImports((KtImportInfo[]) arrayList3.toArray(new KtImportDirective[0])), this.this$0.bindingTrace, this.aliasImportNames, this.packageFragment, null, 16, null);
            final FileScopeFactory fileScopeFactory4 = this.this$0;
            ImportingScope importingScope2 = new ImportingScope(fileScopeFactory4, this) { // from class: org.jetbrains.kotlin.resolve.lazy.FileScopeFactory$FilesScopesBuilder$lazyImportingScope$1
                static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FileScopeFactory$FilesScopesBuilder$lazyImportingScope$1.class), "parent", "getParent()Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;"))};
                private final /* synthetic */ ImportingScope.Empty $$delegate_0 = ImportingScope.Empty.INSTANCE;

                /* JADX INFO: renamed from: parent$delegate, reason: from kotlin metadata */
                private final NotNullLazyValue parent;

                @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
                public Set<Name> computeImportedNames() {
                    return this.$$delegate_0.computeImportedNames();
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
                public boolean definitelyDoesNotContainName(Name name2) {
                    Intrinsics.checkNotNullParameter(name2, "name");
                    return this.$$delegate_0.definitelyDoesNotContainName(name2);
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
                /* JADX INFO: renamed from: getContributedClassifier */
                public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
                    Intrinsics.checkNotNullParameter(name2, "name");
                    Intrinsics.checkNotNullParameter(location, "location");
                    return this.$$delegate_0.mo7465getContributedClassifier(name2, location);
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
                public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation location) {
                    Intrinsics.checkNotNullParameter(name2, "name");
                    Intrinsics.checkNotNullParameter(location, "location");
                    return this.$$delegate_0.getContributedClassifierIncludeDeprecated(name2, location);
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
                public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
                    Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
                    Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
                    return this.$$delegate_0.getContributedDescriptors(kindFilter, nameFilter);
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
                public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, boolean changeNamesForAliased) {
                    Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
                    Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
                    return this.$$delegate_0.getContributedDescriptors(kindFilter, nameFilter, changeNamesForAliased);
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
                public Collection<? extends FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
                    Intrinsics.checkNotNullParameter(name2, "name");
                    Intrinsics.checkNotNullParameter(location, "location");
                    return this.$$delegate_0.getContributedFunctions(name2, location);
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
                /* JADX INFO: renamed from: getContributedPackage */
                public PackageViewDescriptor mo7443getContributedPackage(Name name2) {
                    Intrinsics.checkNotNullParameter(name2, "name");
                    return this.$$delegate_0.mo7443getContributedPackage(name2);
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
                public Collection<? extends VariableDescriptor> getContributedVariables(Name name2, LookupLocation location) {
                    Intrinsics.checkNotNullParameter(name2, "name");
                    Intrinsics.checkNotNullParameter(location, "location");
                    return this.$$delegate_0.getContributedVariables(name2, location);
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
                public void printStructure(Printer p) {
                    Intrinsics.checkNotNullParameter(p, "p");
                    this.$$delegate_0.printStructure(p);
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
                /* JADX INFO: renamed from: recordLookup */
                public void mo7479recordLookup(Name name2, LookupLocation location) {
                    Intrinsics.checkNotNullParameter(name2, "name");
                    Intrinsics.checkNotNullParameter(location, "location");
                    this.$$delegate_0.mo7479recordLookup(name2, location);
                }

                {
                    this.parent = fileScopeFactory4.components.getStorageManager().createLazyValue(new Function0<LazyImportScope>() { // from class: org.jetbrains.kotlin.resolve.lazy.FileScopeFactory$FilesScopesBuilder$lazyImportingScope$1$parent$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final LazyImportScope invoke() {
                            return this.createImportingScope();
                        }
                    });
                }

                @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
                public ImportingScope getParent() {
                    return (ImportingScope) StorageKt.getValue(this.parent, this, (KProperty<?>) $$delegatedProperties[0]);
                }
            };
            this.lazyImportingScope = importingScope2;
            LexicalScope.Base base = new LexicalScope.Base(importingScope2, this.this$0.topLevelDescriptorProvider.getPackageFragmentOrDiagnoseFailure(this.file.getPackageFqName(), this.file));
            this.lexicalScope = base;
            ImportForceResolver importForceResolver = new ImportForceResolver() { // from class: org.jetbrains.kotlin.resolve.lazy.FileScopeFactory$FilesScopesBuilder$importResolver$1
                @Override // org.jetbrains.kotlin.resolve.lazy.ImportForceResolver
                public void forceResolveNonDefaultImports() {
                    this.this$0.getExplicitImportResolver().forceResolveNonDefaultImports();
                    this.this$0.getAllUnderImportResolver().forceResolveNonDefaultImports();
                }

                @Override // org.jetbrains.kotlin.resolve.lazy.ImportForceResolver
                public void forceResolveImport(KtImportDirective importDirective) {
                    Intrinsics.checkNotNullParameter(importDirective, "importDirective");
                    if (importDirective.isAllUnder()) {
                        this.this$0.getAllUnderImportResolver().forceResolveImport(importDirective);
                    } else {
                        this.this$0.getExplicitImportResolver().forceResolveImport(importDirective);
                    }
                }
            };
            this.importResolver = importForceResolver;
            this.result = new FileScopes(base, importingScope2, importForceResolver);
        }

        public final List<KtImportDirective> getImports() {
            return this.imports;
        }

        public final List<FqName> getAliasImportNames() {
            return this.aliasImportNames;
        }

        public final LazyImportResolverForKtImportDirective getExplicitImportResolver() {
            return this.explicitImportResolver;
        }

        public final LazyImportResolverForKtImportDirective getAllUnderImportResolver() {
            return this.allUnderImportResolver;
        }

        public final ImportingScope getLazyImportingScope() {
            return this.lazyImportingScope;
        }

        public final LexicalScope.Base getLexicalScope() {
            return this.lexicalScope;
        }

        public final ImportForceResolver getImportResolver() {
            return this.importResolver;
        }

        public final FileScopes getResult() {
            return this.result;
        }

        private final DefaultImportResolvers createDefaultImportResolversForFile() {
            ExtraImportsProviderExtension.Companion companion = ExtraImportsProviderExtension.INSTANCE;
            Project project = this.file.getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            Collection<KtImportInfo> extraImports = companion.getInstance(project).getExtraImports(this.file);
            return (extraImports.isEmpty() && this.aliasImportNames.isEmpty()) ? this.this$0.getDefaultImportResolvers() : this.this$0.createDefaultImportResolvers(extraImports, this.aliasImportNames);
        }

        public final LazyImportScope createImportingScope() {
            DefaultImportResolvers defaultImportResolversCreateDefaultImportResolversForFile = createDefaultImportResolversForFile();
            LazyImportResolver<KtImportInfo> lazyImportResolverComponent1 = defaultImportResolversCreateDefaultImportResolversForFile.component1();
            LazyImportResolver<KtImportInfo> lazyImportResolverComponent2 = defaultImportResolversCreateDefaultImportResolversForFile.component2();
            LazyImportResolver<KtImportInfo> lazyImportResolverComponent3 = defaultImportResolversCreateDefaultImportResolversForFile.component3();
            DummyContainerDescriptor dummyContainerDescriptor = new DummyContainerDescriptor(this.file, this.packageFragment);
            LazyImportScope lazyImportScope = this.existingImports;
            String str = "LazyFileScope for file " + this.file.getName();
            if (this.createDefaultImportingScopes) {
                lazyImportScope = new LazyImportScope(lazyImportScope, lazyImportResolverComponent2, lazyImportResolverComponent3, LazyImportScope.FilteringKind.INVISIBLE_CLASSES, "Default all under imports in " + str + " (invisible classes only)");
            }
            LazyImportScope lazyImportScopeCurrentPackageScope = this.this$0.currentPackageScope(this.packageView, this.aliasImportNames, dummyContainerDescriptor, FilteringKind.INVISIBLE_CLASSES, new LazyImportScope(lazyImportScope, this.allUnderImportResolver, null, LazyImportScope.FilteringKind.INVISIBLE_CLASSES, "All under imports in " + str + " (invisible classes only)"));
            if (this.createDefaultImportingScopes) {
                lazyImportScopeCurrentPackageScope = new LazyImportScope(lazyImportScopeCurrentPackageScope, lazyImportResolverComponent2, lazyImportResolverComponent3, LazyImportScope.FilteringKind.VISIBLE_CLASSES, "Default all under imports in " + str + " (visible classes)");
            }
            LazyImportScope lazyImportScope2 = new LazyImportScope(lazyImportScopeCurrentPackageScope, this.allUnderImportResolver, null, LazyImportScope.FilteringKind.VISIBLE_CLASSES, "All under imports in " + str + " (visible classes)");
            if (this.createDefaultImportingScopes) {
                lazyImportScope2 = new LazyImportScope(lazyImportScope2, lazyImportResolverComponent1, null, LazyImportScope.FilteringKind.ALL, "Default explicit imports in " + str);
            }
            ModuleDescriptor moduleDescriptor = this.this$0.components.getModuleDescriptor();
            FqName ROOT = FqName.ROOT;
            Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
            return new LazyImportScope(this.this$0.currentPackageScope(this.packageView, this.aliasImportNames, dummyContainerDescriptor, FilteringKind.VISIBLE_CLASSES, new SubpackagesImportingScope(lazyImportScope2, moduleDescriptor, ROOT)), this.explicitImportResolver, null, LazyImportScope.FilteringKind.ALL, "Explicit imports in " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ImportingScope currentPackageScope(PackageViewDescriptor packageView, Collection<FqName> aliasImportNames, DummyContainerDescriptor fromDescriptor, FilteringKind filteringKind, ImportingScope parentScope) {
        final MemberScope memberScope = packageView.getMemberScope();
        Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<THashSet<Name>>() { // from class: org.jetbrains.kotlin.resolve.lazy.FileScopeFactory$currentPackageScope$names$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final THashSet<Name> invoke() {
                Set<Name> setComputeAllNames = MemberScopeKt.computeAllNames(memberScope);
                if (setComputeAllNames != null) {
                    return new THashSet<>(setComputeAllNames);
                }
                return null;
            }
        });
        FqName fqName = packageView.getFqName();
        ArrayList arrayList = new ArrayList();
        for (FqName fqName2 : aliasImportNames) {
            Name nameShortName = Intrinsics.areEqual(fqName2.parent(), fqName) ? fqName2.shortName() : null;
            if (nameShortName != null) {
                arrayList.add(nameShortName);
            }
        }
        return new ImportingScope(parentScope, arrayList, memberScope, fromDescriptor, this, filteringKind, packageView, lazy) { // from class: org.jetbrains.kotlin.resolve.lazy.FileScopeFactory.currentPackageScope.1
            final /* synthetic */ List<Name> $excludedNames;
            final /* synthetic */ FilteringKind $filteringKind;
            final /* synthetic */ DummyContainerDescriptor $fromDescriptor;
            final /* synthetic */ Lazy<THashSet<Name>> $names$delegate;
            final /* synthetic */ PackageViewDescriptor $packageView;
            final /* synthetic */ MemberScope $scope;
            private final ImportingScope parent;
            final /* synthetic */ FileScopeFactory this$0;

            public Void getContributedPackage(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                return null;
            }

            /* JADX WARN: Multi-variable type inference failed */
            {
                this.$excludedNames = arrayList;
                this.$scope = memberScope;
                this.$fromDescriptor = fromDescriptor;
                this.this$0 = this;
                this.$filteringKind = filteringKind;
                this.$packageView = packageView;
                this.$names$delegate = lazy;
                this.parent = parentScope;
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation lookupLocation) {
                return ImportingScope.DefaultImpls.getContributedClassifierIncludeDeprecated(this, name2, lookupLocation);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
            /* JADX INFO: renamed from: getContributedPackage, reason: collision with other method in class */
            public /* bridge */ /* synthetic */ PackageViewDescriptor mo7443getContributedPackage(Name name2) {
                return (PackageViewDescriptor) getContributedPackage(name2);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            /* JADX INFO: renamed from: recordLookup */
            public void mo7479recordLookup(Name name2, LookupLocation lookupLocation) {
                ImportingScope.DefaultImpls.recordLookup(this, name2, lookupLocation);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
            public ImportingScope getParent() {
                return this.parent;
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            /* JADX INFO: renamed from: getContributedClassifier */
            public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
                ClassifierDescriptor contributedClassifier;
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(location, "location");
                if (this.$excludedNames.contains(name2) || (contributedClassifier = this.$scope.mo7465getContributedClassifier(name2, location)) == null) {
                    return null;
                }
                if (this.$filteringKind == (DescriptorVisibilityUtils.isVisibleIgnoringReceiver((DeclarationDescriptorWithVisibility) contributedClassifier, this.$fromDescriptor, this.this$0.components.getLanguageVersionSettings()) ? FilteringKind.VISIBLE_CLASSES : FilteringKind.INVISIBLE_CLASSES)) {
                    return contributedClassifier;
                }
                return null;
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(location, "location");
                if (this.$filteringKind != FilteringKind.INVISIBLE_CLASSES && !this.$excludedNames.contains(name2)) {
                    return this.$scope.getContributedVariables(name2, location);
                }
                return CollectionsKt.emptyList();
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public Collection<FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(location, "location");
                if (this.$filteringKind != FilteringKind.INVISIBLE_CLASSES && !this.$excludedNames.contains(name2)) {
                    return this.$scope.getContributedFunctions(name2, location);
                }
                return CollectionsKt.emptyList();
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
            public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, final Function1<? super Name, Boolean> nameFilter, boolean changeNamesForAliased) {
                Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
                Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
                if (this.$filteringKind == FilteringKind.INVISIBLE_CLASSES) {
                    return CollectionsKt.emptyList();
                }
                MemberScope memberScope2 = this.$scope;
                DescriptorKindFilter descriptorKindFilterWithoutKinds = kindFilter.withoutKinds(DescriptorKindFilter.INSTANCE.getPACKAGES_MASK());
                final List<Name> list = this.$excludedNames;
                Collection<DeclarationDescriptor> contributedDescriptors = memberScope2.getContributedDescriptors(descriptorKindFilterWithoutKinds, new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.resolve.lazy.FileScopeFactory$currentPackageScope$1$getContributedDescriptors$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(Name name2) {
                        Intrinsics.checkNotNullParameter(name2, "name");
                        return Boolean.valueOf(!list.contains(name2) && nameFilter.invoke(name2).booleanValue());
                    }
                });
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : contributedDescriptors) {
                    if (!(((DeclarationDescriptor) obj) instanceof PackageViewDescriptor)) {
                        arrayList2.add(obj);
                    }
                }
                return arrayList2;
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
            public Set<Name> computeImportedNames() {
                return MemberScopeKt.computeAllNames(this.$packageView.getMemberScope());
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public boolean definitelyDoesNotContainName(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                THashSet tHashSetCurrentPackageScope$lambda$5 = FileScopeFactory.currentPackageScope$lambda$5(this.$names$delegate);
                return tHashSetCurrentPackageScope$lambda$5 != null && (tHashSetCurrentPackageScope$lambda$5.contains(name2) ^ true);
            }

            public String toString() {
                return "Scope for current package (" + this.$filteringKind.name() + ')';
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
            public void printStructure(Printer p) {
                Intrinsics.checkNotNullParameter(p, "p");
                p.println(toString());
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final THashSet<Name> currentPackageScope$lambda$5(Lazy<? extends THashSet<Name>> lazy) {
        return lazy.getValue();
    }

    /* JADX INFO: compiled from: FileScopeFactory.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J7\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\u000f2\u0014\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u000f\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013J\u001e\u0010\u0014\u001a\u00020\u00152\u0014\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0011H\u0016J\b\u0010\u0017\u001a\u00020\u0005H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0000H\u0016J\b\u0010\u001b\u001a\u00020\fH\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory$DummyContainerDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorNonRoot;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "packageFragment", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "(Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "sourceElement", "Lorg/jetbrains/kotlin/resolve/source/KotlinSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "getContainingDeclaration", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getSource", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class DummyContainerDescriptor implements DeclarationDescriptorNonRoot {
        private final PackageFragmentDescriptor packageFragment;
        private final KotlinSourceElement sourceElement;

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        public DummyContainerDescriptor getOriginal() {
            return this;
        }

        public DummyContainerDescriptor(KtFile file, PackageFragmentDescriptor packageFragment) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
            this.packageFragment = packageFragment;
            this.sourceElement = new KotlinSourceElement(file);
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        /* JADX INFO: renamed from: getContainingDeclaration, reason: from getter */
        public PackageFragmentDescriptor getPackageFragment() {
            return this.packageFragment;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
        /* JADX INFO: renamed from: getSource, reason: from getter */
        public KotlinSourceElement getSourceElement() {
            return this.sourceElement;
        }

        @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
        public Annotations getAnnotations() {
            return Annotations.INSTANCE.getEMPTY();
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        /* JADX INFO: renamed from: acceptVoid */
        public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.descriptors.Named
        /* JADX INFO: renamed from: getName */
        public Name getShortName() {
            throw new UnsupportedOperationException();
        }
    }
}
