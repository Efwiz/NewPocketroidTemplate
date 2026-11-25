package org.jetbrains.kotlin.synthetic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.sam.SamConversionOracle;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopes;
import org.jetbrains.kotlin.resolve.scopes.synthetic.FunInterfaceConstructorsSyntheticScope;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: JavaSyntheticScopes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/JavaSyntheticScopes;", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "samConventionResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "samConversionOracle", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/incremental/components/LookupTracker;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "scopes", "", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope;", "getScopes", "()Ljava/util/Collection;", "scopesWithForceEnabledSamAdapters", "getScopesWithForceEnabledSamAdapters", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaSyntheticScopes implements SyntheticScopes {
    private final ModuleDescriptor moduleDescriptor;
    private final Project project;
    private final Collection<SyntheticScope> scopes;
    private final Collection<SyntheticScope> scopesWithForceEnabledSamAdapters;

    public JavaSyntheticScopes(Project project, ModuleDescriptor moduleDescriptor, StorageManager storageManager, LookupTracker lookupTracker, LanguageVersionSettings languageVersionSettings, SamConversionResolver samConventionResolver, SamConversionOracle samConversionOracle, DeprecationResolver deprecationResolver, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(samConventionResolver, "samConventionResolver");
        Intrinsics.checkNotNullParameter(samConversionOracle, "samConversionOracle");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.project = project;
        this.moduleDescriptor = moduleDescriptor;
        boolean z = languageVersionSettings.supportsFeature(LanguageFeature.SamConversionPerArgument) && languageVersionSettings.supportsFeature(LanguageFeature.NewInference);
        JavaSyntheticPropertiesScope javaSyntheticPropertiesScope = new JavaSyntheticPropertiesScope(storageManager, lookupTracker, kotlinTypeRefiner, languageVersionSettings.supportsFeature(LanguageFeature.JvmRecordSupport));
        List<SyntheticScopeProviderExtension> instances = SyntheticScopeProviderExtension.INSTANCE.getInstances(project);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = instances.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticScopeProviderExtension) it.next()).getScopes(this.moduleDescriptor, javaSyntheticPropertiesScope));
        }
        ArrayList arrayList2 = arrayList;
        this.scopes = CollectionsKt.plus((Collection) CollectionsKt.listOf((Object[]) new SyntheticScope.Default[]{javaSyntheticPropertiesScope, new SamAdapterFunctionsScope(storageManager, samConventionResolver, samConversionOracle, deprecationResolver, lookupTracker, z, !languageVersionSettings.supportsFeature(LanguageFeature.ProhibitVarargAsArrayAfterSamArgument)), new FunInterfaceConstructorsSyntheticScope(storageManager, lookupTracker, samConventionResolver, samConversionOracle)}), (Iterable) arrayList2);
        if (z) {
            this.scopesWithForceEnabledSamAdapters = CollectionsKt.plus((Collection) CollectionsKt.listOf((Object[]) new SyntheticScope.Default[]{javaSyntheticPropertiesScope, new SamAdapterFunctionsScope(storageManager, samConventionResolver, samConversionOracle, deprecationResolver, lookupTracker, false, false)}), (Iterable) arrayList2);
        } else {
            this.scopesWithForceEnabledSamAdapters = getScopes();
        }
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScopes
    public Collection<SyntheticScope> getScopes() {
        return this.scopes;
    }

    public final Collection<SyntheticScope> getScopesWithForceEnabledSamAdapters() {
        return this.scopesWithForceEnabledSamAdapters;
    }
}
