package org.jetbrains.kotlin.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDependencies;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: AnalyzerFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u001e*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001\u001eB-\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\b\u0010\u0007\u001a\u0004\u0018\u00018\u0000\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t¢\u0006\u0002\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR!\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u00118VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\r0\f8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u0015\u001a\u0004\b\u0019\u0010\u000fR\u0010\u0010\u0006\u001a\u00028\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001bR\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\r0\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0013R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/LazyModuleDependencies;", "M", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDependencies;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "firstDependency", "resolverForProject", "Lorg/jetbrains/kotlin/analyzer/AbstractResolverForProject;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/analyzer/ModuleInfo;Lorg/jetbrains/kotlin/analyzer/ModuleInfo;Lorg/jetbrains/kotlin/analyzer/AbstractResolverForProject;)V", "allDependencies", "", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "getAllDependencies", "()Ljava/util/List;", "allExpectedByDependencies", "", "getAllExpectedByDependencies", "()Ljava/util/Set;", "allExpectedByDependencies$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "dependencies", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "directExpectedByDependencies", "getDirectExpectedByDependencies", "directExpectedByDependencies$delegate", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "modulesWhoseInternalsAreVisible", "getModulesWhoseInternalsAreVisible", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyModuleDependencies<M extends ModuleInfo> implements ModuleDependencies {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyModuleDependencies.class), "directExpectedByDependencies", "getDirectExpectedByDependencies()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyModuleDependencies.class), "allExpectedByDependencies", "getAllExpectedByDependencies()Ljava/util/Set;"))};

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: allExpectedByDependencies$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue allExpectedByDependencies;
    private final NotNullLazyValue<List<ModuleDescriptorImpl>> dependencies;

    /* JADX INFO: renamed from: directExpectedByDependencies$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue directExpectedByDependencies;
    private final M module;
    private final AbstractResolverForProject<M> resolverForProject;

    public LazyModuleDependencies(StorageManager storageManager, M module, final M m, AbstractResolverForProject<M> resolverForProject) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(resolverForProject, "resolverForProject");
        this.module = module;
        this.resolverForProject = resolverForProject;
        this.dependencies = storageManager.createLazyValue(new Function0<List<? extends ModuleDescriptorImpl>>() { // from class: org.jetbrains.kotlin.analyzer.LazyModuleDependencies$dependencies$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Incorrect types in method signature: (TM;Lorg/jetbrains/kotlin/analyzer/LazyModuleDependencies<TM;>;)V */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends ModuleDescriptorImpl> invoke() {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                ModuleInfo moduleInfo = m;
                if (moduleInfo != null) {
                    LazyModuleDependencies<M> lazyModuleDependencies = this;
                    LazyModuleDependencies.INSTANCE.assertModuleDependencyIsCorrect(((LazyModuleDependencies) lazyModuleDependencies).module, moduleInfo);
                    linkedHashSet.add(((LazyModuleDependencies) lazyModuleDependencies).resolverForProject.mo6073descriptorForModule(moduleInfo));
                }
                ModuleDescriptorImpl moduleDescriptorImplMo6073descriptorForModule = ((LazyModuleDependencies) this).resolverForProject.mo6073descriptorForModule(((LazyModuleDependencies) this).module);
                ModuleInfo.DependencyOnBuiltIns dependencyOnBuiltIns = ((LazyModuleDependencies) this).module.dependencyOnBuiltIns();
                if (dependencyOnBuiltIns == ModuleInfo.DependencyOnBuiltIns.AFTER_SDK) {
                    ModuleDescriptorImpl builtInsModule = moduleDescriptorImplMo6073descriptorForModule.getBuiltIns().getBuiltInsModule();
                    Intrinsics.checkNotNullExpressionValue(builtInsModule, "getBuiltInsModule(...)");
                    LazyModuleDependencies.INSTANCE.assertModuleDependencyIsCorrect(((LazyModuleDependencies) this).module, builtInsModule);
                    linkedHashSet.add(builtInsModule);
                }
                for (ModuleInfo moduleInfo2 : ((LazyModuleDependencies) this).module.dependencies()) {
                    if (!Intrinsics.areEqual(moduleInfo2, m)) {
                        LazyModuleDependencies.INSTANCE.assertModuleDependencyIsCorrect(((LazyModuleDependencies) this).module, moduleInfo2);
                        AbstractResolverForProject abstractResolverForProject = ((LazyModuleDependencies) this).resolverForProject;
                        Intrinsics.checkNotNull(moduleInfo2, "null cannot be cast to non-null type M of org.jetbrains.kotlin.analyzer.LazyModuleDependencies");
                        linkedHashSet.add(abstractResolverForProject.mo6073descriptorForModule(moduleInfo2));
                    }
                }
                if (dependencyOnBuiltIns == ModuleInfo.DependencyOnBuiltIns.LAST) {
                    ModuleDescriptorImpl builtInsModule2 = moduleDescriptorImplMo6073descriptorForModule.getBuiltIns().getBuiltInsModule();
                    Intrinsics.checkNotNullExpressionValue(builtInsModule2, "getBuiltInsModule(...)");
                    LazyModuleDependencies.INSTANCE.assertModuleDependencyIsCorrect(((LazyModuleDependencies) this).module, builtInsModule2);
                    linkedHashSet.add(builtInsModule2);
                }
                return CollectionsKt.toList(linkedHashSet);
            }
        });
        this.directExpectedByDependencies = storageManager.createLazyValue(new Function0<List<? extends ModuleDescriptorImpl>>(this) { // from class: org.jetbrains.kotlin.analyzer.LazyModuleDependencies$directExpectedByDependencies$2
            final /* synthetic */ LazyModuleDependencies<M> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends ModuleDescriptorImpl> invoke() {
                List<ModuleInfo> expectedBy = ((LazyModuleDependencies) this.this$0).module.getExpectedBy();
                LazyModuleDependencies<M> lazyModuleDependencies = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(expectedBy, 10));
                for (ModuleInfo moduleInfo : expectedBy) {
                    LazyModuleDependencies.INSTANCE.assertModuleDependencyIsCorrect(((LazyModuleDependencies) lazyModuleDependencies).module, moduleInfo);
                    AbstractResolverForProject abstractResolverForProject = ((LazyModuleDependencies) lazyModuleDependencies).resolverForProject;
                    Intrinsics.checkNotNull(moduleInfo, "null cannot be cast to non-null type M of org.jetbrains.kotlin.analyzer.LazyModuleDependencies.<no name provided>.invoke$lambda$0");
                    arrayList.add(abstractResolverForProject.mo6073descriptorForModule(moduleInfo));
                }
                return arrayList;
            }
        });
        this.allExpectedByDependencies = storageManager.createLazyValue(new Function0<HashSet<ModuleDescriptorImpl>>(this) { // from class: org.jetbrains.kotlin.analyzer.LazyModuleDependencies$allExpectedByDependencies$2
            final /* synthetic */ LazyModuleDependencies<M> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final HashSet<ModuleDescriptorImpl> invoke() {
                Set<ModuleInfo> setCollectAllExpectedByModules = ModuleInfoUtilsKt.collectAllExpectedByModules(((LazyModuleDependencies) this.this$0).module);
                HashSet<ModuleDescriptorImpl> hashSet = new HashSet<>();
                LazyModuleDependencies<M> lazyModuleDependencies = this.this$0;
                for (ModuleInfo moduleInfo : setCollectAllExpectedByModules) {
                    LazyModuleDependencies.INSTANCE.assertModuleDependencyIsCorrect(((LazyModuleDependencies) lazyModuleDependencies).module, moduleInfo);
                    AbstractResolverForProject abstractResolverForProject = ((LazyModuleDependencies) lazyModuleDependencies).resolverForProject;
                    Intrinsics.checkNotNull(moduleInfo, "null cannot be cast to non-null type M of org.jetbrains.kotlin.analyzer.LazyModuleDependencies.<no name provided>.invoke$lambda$0");
                    hashSet.add(abstractResolverForProject.mo6073descriptorForModule(moduleInfo));
                }
                return hashSet;
            }
        });
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleDependencies
    public List<ModuleDescriptorImpl> getAllDependencies() {
        return this.dependencies.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleDependencies
    public List<ModuleDescriptorImpl> getDirectExpectedByDependencies() {
        return (List) StorageKt.getValue(this.directExpectedByDependencies, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleDependencies
    public Set<ModuleDescriptorImpl> getAllExpectedByDependencies() {
        return (Set) StorageKt.getValue(this.allExpectedByDependencies, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleDependencies
    public Set<ModuleDescriptorImpl> getModulesWhoseInternalsAreVisible() {
        Collection<ModuleInfo> collectionModulesWhoseInternalsAreVisible = this.module.modulesWhoseInternalsAreVisible();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (ModuleInfo moduleInfo : collectionModulesWhoseInternalsAreVisible) {
            INSTANCE.assertModuleDependencyIsCorrect(this.module, moduleInfo);
            AbstractResolverForProject<M> abstractResolverForProject = this.resolverForProject;
            Intrinsics.checkNotNull(moduleInfo, "null cannot be cast to non-null type M of org.jetbrains.kotlin.analyzer.LazyModuleDependencies._get_modulesWhoseInternalsAreVisible_$lambda$0");
            linkedHashSet.add(abstractResolverForProject.mo6073descriptorForModule(moduleInfo));
        }
        return linkedHashSet;
    }

    /* JADX INFO: compiled from: AnalyzerFacade.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002J\u0014\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/LazyModuleDependencies$Companion;", "", "()V", "assertModuleDependencyIsCorrect", "", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "dependency", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void assertModuleDependencyIsCorrect(ModuleInfo moduleInfo, ModuleDescriptor moduleDescriptor) {
            ModuleInfo moduleInfo2 = (ModuleInfo) moduleDescriptor.getCapability(ModuleInfo.INSTANCE.getCapability());
            if (moduleInfo2 == null) {
                return;
            }
            assertModuleDependencyIsCorrect(moduleInfo, moduleInfo2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void assertModuleDependencyIsCorrect(ModuleInfo moduleInfo, ModuleInfo moduleInfo2) {
            if (moduleInfo2 instanceof DerivedModuleInfo) {
                boolean z = moduleInfo instanceof DerivedModuleInfo;
            }
        }
    }
}
