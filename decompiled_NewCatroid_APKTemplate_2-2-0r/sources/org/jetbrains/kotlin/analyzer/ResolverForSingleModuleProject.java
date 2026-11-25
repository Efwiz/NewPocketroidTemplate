package org.jetbrains.kotlin.analyzer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.context.ContextKt;
import org.jetbrains.kotlin.context.ProjectContext;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.CliSealedClassInheritorsProvider;

/* JADX INFO: compiled from: ResolverForSingleModuleProject.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003Bs\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00028\u0000\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00018\u0000\u0012\u0014\b\u0002\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00170\u0016¢\u0006\u0002\u0010\u0018J\u0015\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001bJ\u001d\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010 J\u001b\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000\"2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010#J\u0017\u0010\u0014\u001a\u0004\u0018\u00018\u00002\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010$R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00028\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0019R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0014\u001a\u0004\u0018\u00018\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0019R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/ResolverForSingleModuleProject;", "M", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "Lorg/jetbrains/kotlin/analyzer/AbstractResolverForProject;", "debugName", "", "projectContext", "Lorg/jetbrains/kotlin/context/ProjectContext;", "module", "resolverForModuleFactory", "Lorg/jetbrains/kotlin/analyzer/ResolverForModuleFactory;", "searchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "syntheticFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "sdkDependency", "knownDependencyModuleDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/context/ProjectContext;Lorg/jetbrains/kotlin/analyzer/ModuleInfo;Lorg/jetbrains/kotlin/analyzer/ResolverForModuleFactory;Lcom/intellij/psi/search/GlobalSearchScope;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Ljava/util/Collection;Lorg/jetbrains/kotlin/analyzer/ModuleInfo;Ljava/util/Map;)V", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "builtInsForModule", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "createResolverForModule", "Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "descriptor", "moduleInfo", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "modulesContent", "Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolverForSingleModuleProject<M extends ModuleInfo> extends AbstractResolverForProject<M> {
    private final KotlinBuiltIns builtIns;
    private final LanguageVersionSettings languageVersionSettings;
    private final M module;
    private final ResolverForModuleFactory resolverForModuleFactory;
    private final M sdkDependency;
    private final GlobalSearchScope searchScope;
    private final Collection<KtFile> syntheticFiles;

    public /* synthetic */ ResolverForSingleModuleProject(String str, ProjectContext projectContext, ModuleInfo moduleInfo, ResolverForModuleFactory resolverForModuleFactory, GlobalSearchScope globalSearchScope, KotlinBuiltIns kotlinBuiltIns, LanguageVersionSettings languageVersionSettings, Collection collection, ModuleInfo moduleInfo2, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, projectContext, moduleInfo, resolverForModuleFactory, globalSearchScope, (i & 32) != 0 ? DefaultBuiltIns.INSTANCE.getInstance() : kotlinBuiltIns, (i & 64) != 0 ? LanguageVersionSettingsImpl.DEFAULT : languageVersionSettings, (i & 128) != 0 ? CollectionsKt.emptyList() : collection, (i & 256) != 0 ? null : moduleInfo2, (i & 512) != 0 ? MapsKt.emptyMap() : map);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ResolverForSingleModuleProject(String debugName, ProjectContext projectContext, M module, ResolverForModuleFactory resolverForModuleFactory, GlobalSearchScope searchScope, KotlinBuiltIns builtIns, LanguageVersionSettings languageVersionSettings, Collection<? extends KtFile> syntheticFiles, M m, Map<M, ? extends ModuleDescriptor> knownDependencyModuleDescriptors) {
        ModificationTracker fallbackModificationTracker;
        super(debugName, projectContext, CollectionsKt.plus((Collection) CollectionsKt.listOf(module), (Iterable) knownDependencyModuleDescriptors.keySet()), null, new EmptyResolverForProject(), PackageOracleFactory.OptimisticFactory.INSTANCE);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(projectContext, "projectContext");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(resolverForModuleFactory, "resolverForModuleFactory");
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(syntheticFiles, "syntheticFiles");
        Intrinsics.checkNotNullParameter(knownDependencyModuleDescriptors, "knownDependencyModuleDescriptors");
        this.module = module;
        this.resolverForModuleFactory = resolverForModuleFactory;
        this.searchScope = searchScope;
        this.builtIns = builtIns;
        this.languageVersionSettings = languageVersionSettings;
        this.syntheticFiles = syntheticFiles;
        this.sdkDependency = m;
        for (Map.Entry<M, ? extends ModuleDescriptor> entry : knownDependencyModuleDescriptors.entrySet()) {
            M key = entry.getKey();
            ModuleDescriptor value = entry.getValue();
            HashMap<M, AbstractResolverForProject.ModuleData> descriptorByModule = getDescriptorByModule();
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl");
            ModuleDescriptorImpl moduleDescriptorImpl = (ModuleDescriptorImpl) value;
            TrackableModuleInfo trackableModuleInfo = key instanceof TrackableModuleInfo ? (TrackableModuleInfo) key : null;
            if (trackableModuleInfo == null || (fallbackModificationTracker = trackableModuleInfo.createModificationTracker()) == null) {
                fallbackModificationTracker = getFallbackModificationTracker();
            }
            descriptorByModule.put(key, new AbstractResolverForProject.ModuleData(moduleDescriptorImpl, fallbackModificationTracker));
        }
    }

    @Override // org.jetbrains.kotlin.analyzer.AbstractResolverForProject
    public M sdkDependency(M module) {
        Intrinsics.checkNotNullParameter(module, "module");
        return this.sdkDependency;
    }

    @Override // org.jetbrains.kotlin.analyzer.AbstractResolverForProject
    public ModuleContent<M> modulesContent(M module) {
        Intrinsics.checkNotNullParameter(module, "module");
        return Intrinsics.areEqual(module, this.module) ? new ModuleContent<>(module, this.syntheticFiles, this.searchScope) : new ModuleContent<>(module, CollectionsKt.emptyList(), this.searchScope);
    }

    @Override // org.jetbrains.kotlin.analyzer.AbstractResolverForProject
    public KotlinBuiltIns builtInsForModule(M module) {
        Intrinsics.checkNotNullParameter(module, "module");
        return this.builtIns;
    }

    @Override // org.jetbrains.kotlin.analyzer.AbstractResolverForProject
    public ResolverForModule createResolverForModule(ModuleDescriptor descriptor, M moduleInfo) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        return this.resolverForModuleFactory.createResolverForModule((ModuleDescriptorImpl) descriptor, ContextKt.withModule(getProjectContext(), descriptor), modulesContent(moduleInfo), this, this.languageVersionSettings, CliSealedClassInheritorsProvider.INSTANCE, null, null);
    }
}
