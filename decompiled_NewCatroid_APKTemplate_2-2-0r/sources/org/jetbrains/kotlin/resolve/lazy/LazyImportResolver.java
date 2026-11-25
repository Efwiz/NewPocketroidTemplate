package org.jetbrains.kotlin.resolve.lazy;

import com.google.firebase.analytics.FirebaseAnalytics;
import gnu.trove.THashSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtImportInfo;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.resolve.scopes.optimization.OptimizingOptions;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt;

/* JADX INFO: compiled from: LazyImportScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B;\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ8\u0010$\u001a\b\u0012\u0004\u0012\u0002H%0\t\"\b\b\u0001\u0010%*\u00020&2\u0006\u0010'\u001a\u00020\u00122\u0018\u0010(\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u0002H%0\t0)J\u000e\u0010*\u001a\u00020+2\u0006\u0010'\u001a\u00020\u0012J\u000e\u0010,\u001a\u00020\u001d2\u0006\u0010-\u001a\u00020\u0002J\u0016\u0010.\u001a\u00020/2\u0006\u0010'\u001a\u00020\u00122\u0006\u00100\u001a\u000201R#\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00118FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u001d0\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/LazyImportResolver;", "I", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "", "components", "Lorg/jetbrains/kotlin/resolve/lazy/ImportResolutionComponents;", "indexedImports", "Lorg/jetbrains/kotlin/resolve/lazy/IndexedImports;", "excludedImportNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "traceForImportResolve", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "packageFragment", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "(Lorg/jetbrains/kotlin/resolve/lazy/ImportResolutionComponents;Lorg/jetbrains/kotlin/resolve/lazy/IndexedImports;Ljava/util/Collection;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;)V", "allNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getAllNames", "()Ljava/util/Set;", "allNames$delegate", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "getComponents$frontend", "()Lorg/jetbrains/kotlin/resolve/lazy/ImportResolutionComponents;", "getExcludedImportNames", "()Ljava/util/Collection;", "importedScopesProvider", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "getIndexedImports", "()Lorg/jetbrains/kotlin/resolve/lazy/IndexedImports;", "getPackageFragment", "()Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getTraceForImportResolve", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "collectFromImports", "D", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "name", "descriptorsSelector", "Lkotlin/Function1;", "definitelyDoesNotContainName", "", "getImportScope", "directive", "recordLookup", "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LazyImportResolver<I extends KtImportInfo> {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyImportResolver.class), "allNames", "getAllNames()Ljava/util/Set;"))};

    /* JADX INFO: renamed from: allNames$delegate, reason: from kotlin metadata */
    private final NullableLazyValue allNames;
    private final ImportResolutionComponents components;
    private final Collection<FqName> excludedImportNames;
    private final MemoizedFunctionToNullable<KtImportInfo, ImportingScope> importedScopesProvider;
    private final IndexedImports<I> indexedImports;
    private final PackageFragmentDescriptor packageFragment;
    private final BindingTrace traceForImportResolve;

    public LazyImportResolver(final ImportResolutionComponents components, IndexedImports<I> indexedImports, Collection<FqName> excludedImportNames, BindingTrace traceForImportResolve, PackageFragmentDescriptor packageFragmentDescriptor) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(indexedImports, "indexedImports");
        Intrinsics.checkNotNullParameter(excludedImportNames, "excludedImportNames");
        Intrinsics.checkNotNullParameter(traceForImportResolve, "traceForImportResolve");
        this.components = components;
        this.indexedImports = indexedImports;
        this.excludedImportNames = excludedImportNames;
        this.traceForImportResolve = traceForImportResolve;
        this.packageFragment = packageFragmentDescriptor;
        this.importedScopesProvider = components.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<KtImportInfo, ImportingScope>() { // from class: org.jetbrains.kotlin.resolve.lazy.LazyImportResolver$importedScopesProvider$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ImportingScope invoke(KtImportInfo directive) {
                Intrinsics.checkNotNullParameter(directive, "directive");
                return components.getQualifiedExpressionResolver().processImportReference(directive, components.getModuleDescriptor(), this.getTraceForImportResolve(), this.getExcludedImportNames(), this.getPackageFragment());
            }
        });
        this.allNames = components.getStorageManager().createNullableLazyValue(new Function0<THashSet<Name>>(this) { // from class: org.jetbrains.kotlin.resolve.lazy.LazyImportResolver$allNames$2
            final /* synthetic */ LazyImportResolver<I> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final THashSet<Name> invoke() {
                Iterable iterableAsIterable = ArraysKt.asIterable(this.this$0.getIndexedImports().getImports());
                THashSet<Name> tHashSet = new THashSet<>();
                LazyImportResolver<I> lazyImportResolver = this.this$0;
                Iterator it = iterableAsIterable.iterator();
                while (it.hasNext()) {
                    Set<Name> setComputeImportedNames = lazyImportResolver.getImportScope((KtImportInfo) it.next()).computeImportedNames();
                    if (setComputeImportedNames == null) {
                        tHashSet = null;
                        break;
                    }
                    CollectionsKt.addAll(tHashSet, setComputeImportedNames);
                }
                return tHashSet;
            }
        });
    }

    /* JADX INFO: renamed from: getComponents$frontend, reason: from getter */
    public final ImportResolutionComponents getComponents() {
        return this.components;
    }

    public final IndexedImports<I> getIndexedImports() {
        return this.indexedImports;
    }

    public final Collection<FqName> getExcludedImportNames() {
        return this.excludedImportNames;
    }

    public final BindingTrace getTraceForImportResolve() {
        return this.traceForImportResolve;
    }

    public final PackageFragmentDescriptor getPackageFragment() {
        return this.packageFragment;
    }

    public final <D extends DeclarationDescriptor> Collection<D> collectFromImports(final Name name2, final Function1<? super ImportingScope, ? extends Collection<? extends D>> descriptorsSelector) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(descriptorsSelector, "descriptorsSelector");
        return (Collection) this.components.getStorageManager().compute(new Function0<Collection<? extends D>>(this) { // from class: org.jetbrains.kotlin.resolve.lazy.LazyImportResolver.collectFromImports.1
            final /* synthetic */ LazyImportResolver<I> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<D> invoke() {
                Iterator<I> it = this.this$0.getIndexedImports().importsForName(name2).iterator();
                Collection<D> collectionConcat = null;
                while (it.hasNext()) {
                    collectionConcat = ScopeUtilsKt.concat(collectionConcat, descriptorsSelector.invoke(this.this$0.getImportScope(it.next())));
                }
                return collectionConcat == null ? CollectionsKt.emptyList() : collectionConcat;
            }
        });
    }

    public final ImportingScope getImportScope(KtImportInfo directive) {
        Intrinsics.checkNotNullParameter(directive, "directive");
        ImportingScope importingScopeInvoke = this.importedScopesProvider.invoke(directive);
        return importingScopeInvoke == null ? ImportingScope.Empty.INSTANCE : importingScopeInvoke;
    }

    public final Set<Name> getAllNames() {
        return (Set) StorageKt.getValue(this.allNames, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    public final boolean definitelyDoesNotContainName(Name name2) {
        Set<Name> allNames;
        Intrinsics.checkNotNullParameter(name2, "name");
        OptimizingOptions optimizingOptions = this.components.getOptimizingOptions();
        PackageFragmentDescriptor packageFragmentDescriptor = this.packageFragment;
        return optimizingOptions.shouldCalculateAllNamesForLazyImportScopeOptimizing(packageFragmentDescriptor != null ? packageFragmentDescriptor.getContainingDeclaration() : null) && (allNames = getAllNames()) != null && (allNames.contains(name2) ^ true);
    }

    public final void recordLookup(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (getAllNames() == null) {
            return;
        }
        Iterator<I> it = this.indexedImports.importsForName(name2).iterator();
        while (it.hasNext()) {
            ImportingScope importScope = getImportScope(it.next());
            if (importScope != ImportingScope.Empty.INSTANCE) {
                importScope.mo7479recordLookup(name2, location);
            }
        }
    }
}
