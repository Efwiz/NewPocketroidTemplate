package org.jetbrains.kotlin.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.context.ProjectContext;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: AbstractResolverForProject.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000f\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004:\u0001aBI\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\u0015\u00102\u001a\u0002032\u0006\u00104\u001a\u00028\u0000H&¢\u0006\u0002\u00105J\u0015\u00106\u001a\u0002072\u0006\u00108\u001a\u00028\u0000H\u0002¢\u0006\u0002\u00109J\b\u0010:\u001a\u000207H\u0002J\u0015\u0010;\u001a\u00020\u00182\u0006\u00104\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010<J\u001d\u0010=\u001a\u0002012\u0006\u0010>\u001a\u0002002\u0006\u00108\u001a\u00028\u0000H&¢\u0006\u0002\u0010?J\u0015\u0010@\u001a\u00020%2\u0006\u00108\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010AJ\u0016\u0010B\u001a\u00020C2\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00020EH\u0016J\b\u0010F\u001a\u000207H\u0016J\u0015\u0010G\u001a\u00020%2\u0006\u00104\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010AJ\u001a\u0010H\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030I\u0012\u0006\u0012\u0004\u0018\u00010J0'H\u0014J\u0015\u0010K\u001a\u00020\u001d2\u0006\u00108\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010LJ\u0015\u0010M\u001a\u00020\u001d2\u0006\u0010>\u001a\u000200H\u0000¢\u0006\u0002\bNJ\u0015\u0010O\u001a\u00028\u00002\u0006\u0010P\u001a\u000200H\u0016¢\u0006\u0002\u0010QJ\u001b\u0010R\u001a\b\u0012\u0004\u0012\u00028\u00000S2\u0006\u00104\u001a\u00028\u0000H&¢\u0006\u0002\u0010TJ\u0015\u0010U\u001a\u00020\u00182\u0006\u00104\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010<J\b\u0010V\u001a\u00020\u0006H\u0002J\b\u0010W\u001a\u00020\u0006H\u0002J\b\u0010X\u001a\u000207H\u0014J\u000e\u0010Y\u001a\u0002012\u0006\u0010>\u001a\u000200J\u0012\u0010Z\u001a\u0004\u0018\u0001012\u0006\u0010>\u001a\u000200H\u0002J\u0017\u0010[\u001a\u0004\u0018\u00018\u00002\u0006\u00104\u001a\u00028\u0000H&¢\u0006\u0002\u0010\\J\u001d\u0010]\u001a\u0002072\u0006\u00104\u001a\u00028\u00002\u0006\u0010P\u001a\u00020%H\u0002¢\u0006\u0002\u0010^J\u0017\u0010_\u001a\u0004\u0018\u0001012\u0006\u00108\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010`R!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\n8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R0\u0010\u0016\u001a\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00180\u0017j\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0018`\u0019X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0016\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R*\u0010$\u001a\u001e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00028\u00000\u0017j\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00028\u0000`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010&\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00000'X\u0082\u0004¢\u0006\b\n\u0000\u0012\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010,R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R*\u0010/\u001a\u001e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u0002010\u0017j\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u000201`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/AbstractResolverForProject;", "M", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "Lorg/jetbrains/kotlin/analyzer/ResolverForProject;", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "debugName", "", "projectContext", "Lorg/jetbrains/kotlin/context/ProjectContext;", "modules", "", "fallbackModificationTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "delegateResolver", "packageOracleFactory", "Lorg/jetbrains/kotlin/analyzer/PackageOracleFactory;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/context/ProjectContext;Ljava/util/Collection;Lcom/intellij/openapi/util/ModificationTracker;Lorg/jetbrains/kotlin/analyzer/ResolverForProject;Lorg/jetbrains/kotlin/analyzer/PackageOracleFactory;)V", "allModules", "getAllModules", "()Ljava/util/Collection;", "allModules$delegate", "Lkotlin/Lazy;", "descriptorByModule", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/analyzer/AbstractResolverForProject$ModuleData;", "Lkotlin/collections/HashMap;", "getDescriptorByModule", "()Ljava/util/HashMap;", "disposed", "", "getDisposed", "()Z", "setDisposed", "(Z)V", "getFallbackModificationTracker", "()Lcom/intellij/openapi/util/ModificationTracker;", "moduleInfoByDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "moduleInfoToResolvableInfo", "", "getModuleInfoToResolvableInfo$annotations", "()V", "name", "getName", "()Ljava/lang/String;", "getProjectContext", "()Lorg/jetbrains/kotlin/context/ProjectContext;", "resolverByModuleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "builtInsForModule", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "module", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "checkModuleIsCorrect", "", "moduleInfo", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)V", "checkValid", "createModuleDescriptor", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/analyzer/AbstractResolverForProject$ModuleData;", "createResolverForModule", "descriptor", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "descriptorForModule", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "diagnoseUnknownModuleInfo", "", AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS, "", "dispose", "doGetDescriptorForModule", "getAdditionalCapabilities", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "", "isCorrectModuleInfo", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Z", "isResolverForModuleDescriptorComputed", "isResolverForModuleDescriptorComputed$frontend", "moduleInfoForModuleDescriptor", "moduleDescriptor", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "modulesContent", "Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "recreateModuleDescriptor", "renderResolverModuleInfos", "renderResolversChainContents", "reportInvalidResolver", "resolverForModuleDescriptor", "resolverForModuleDescriptorImpl", "sdkDependency", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "setupModuleDescriptor", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;)V", "tryGetResolverForModule", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "ModuleData", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractResolverForProject<M extends ModuleInfo> extends ResolverForProject<M> implements Disposable {

    /* JADX INFO: renamed from: allModules$delegate, reason: from kotlin metadata */
    private final Lazy allModules;
    private final String debugName;
    private final ResolverForProject<M> delegateResolver;
    private final HashMap<M, ModuleData> descriptorByModule;
    private volatile boolean disposed;
    private final ModificationTracker fallbackModificationTracker;
    private final HashMap<ModuleDescriptorImpl, M> moduleInfoByDescriptor;
    private final Map<M, M> moduleInfoToResolvableInfo;
    private final PackageOracleFactory packageOracleFactory;
    private final ProjectContext projectContext;
    private final HashMap<ModuleDescriptor, ResolverForModule> resolverByModuleDescriptor;

    public abstract KotlinBuiltIns builtInsForModule(M module);

    public abstract ResolverForModule createResolverForModule(ModuleDescriptor descriptor, M moduleInfo);

    public abstract ModuleContent<M> modulesContent(M module);

    public abstract M sdkDependency(M module);

    protected final ProjectContext getProjectContext() {
        return this.projectContext;
    }

    protected final ModificationTracker getFallbackModificationTracker() {
        return this.fallbackModificationTracker;
    }

    public /* synthetic */ AbstractResolverForProject(String str, ProjectContext projectContext, Collection collection, ModificationTracker modificationTracker, EmptyResolverForProject emptyResolverForProject, PackageOracleFactory.OptimisticFactory optimisticFactory, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, projectContext, collection, (i & 8) != 0 ? null : modificationTracker, (i & 16) != 0 ? new EmptyResolverForProject() : emptyResolverForProject, (i & 32) != 0 ? PackageOracleFactory.OptimisticFactory.INSTANCE : optimisticFactory);
    }

    public AbstractResolverForProject(String debugName, ProjectContext projectContext, Collection<? extends M> modules, ModificationTracker modificationTracker, ResolverForProject<M> delegateResolver, PackageOracleFactory packageOracleFactory) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(projectContext, "projectContext");
        Intrinsics.checkNotNullParameter(modules, "modules");
        Intrinsics.checkNotNullParameter(delegateResolver, "delegateResolver");
        Intrinsics.checkNotNullParameter(packageOracleFactory, "packageOracleFactory");
        this.debugName = debugName;
        this.projectContext = projectContext;
        this.fallbackModificationTracker = modificationTracker;
        this.delegateResolver = delegateResolver;
        this.packageOracleFactory = packageOracleFactory;
        this.descriptorByModule = new HashMap<>();
        this.moduleInfoByDescriptor = new HashMap<>();
        Collection<? extends M> collection = modules;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            ModuleInfo moduleInfo = (ModuleInfo) it.next();
            List<ModuleInfo> listFlatten = AnalyzerFacadeKt.flatten(moduleInfo);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFlatten, 10));
            Iterator<T> it2 = listFlatten.iterator();
            while (it2.hasNext()) {
                arrayList2.add(TuplesKt.to((ModuleInfo) it2.next(), moduleInfo));
            }
            CollectionsKt.addAll(arrayList, arrayList2);
        }
        Map<M, M> map = MapsKt.toMap(arrayList);
        Intrinsics.checkNotNull(map, "null cannot be cast to non-null type kotlin.collections.Map<M of org.jetbrains.kotlin.analyzer.AbstractResolverForProject, M of org.jetbrains.kotlin.analyzer.AbstractResolverForProject>");
        this.moduleInfoToResolvableInfo = map;
        Intrinsics.areEqual(CollectionsKt.toSet(map.values()), CollectionsKt.toSet(collection));
        this.resolverByModuleDescriptor = new HashMap<>();
        this.allModules = LazyKt.lazy(new Function0<Set<? extends M>>(this) { // from class: org.jetbrains.kotlin.analyzer.AbstractResolverForProject$allModules$2
            final /* synthetic */ AbstractResolverForProject<M> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<M> invoke() {
                return SetsKt.plus(((AbstractResolverForProject) this.this$0).moduleInfoToResolvableInfo.keySet(), (Iterable) ((AbstractResolverForProject) this.this$0).delegateResolver.getAllModules());
            }
        });
    }

    /* JADX INFO: compiled from: AbstractResolverForProject.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u000f\u001a\u00020\u0010R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/AbstractResolverForProject$ModuleData;", "", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "modificationTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "(Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;Lcom/intellij/openapi/util/ModificationTracker;)V", "modificationCount", "", "getModificationCount", "()J", "getModificationTracker", "()Lcom/intellij/openapi/util/ModificationTracker;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "isOutOfDate", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class ModuleData {
        private final long modificationCount;
        private final ModificationTracker modificationTracker;
        private final ModuleDescriptorImpl moduleDescriptor;

        public ModuleData(ModuleDescriptorImpl moduleDescriptor, ModificationTracker modificationTracker) {
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            this.moduleDescriptor = moduleDescriptor;
            this.modificationTracker = modificationTracker;
            this.modificationCount = modificationTracker != null ? modificationTracker.getModificationCount() : Long.MIN_VALUE;
        }

        public final ModuleDescriptorImpl getModuleDescriptor() {
            return this.moduleDescriptor;
        }

        public final ModificationTracker getModificationTracker() {
            return this.modificationTracker;
        }

        public final long getModificationCount() {
            return this.modificationCount;
        }

        public final boolean isOutOfDate() {
            ModificationTracker modificationTracker = this.modificationTracker;
            Long lValueOf = modificationTracker != null ? Long.valueOf(modificationTracker.getModificationCount()) : null;
            return lValueOf != null && lValueOf.longValue() > this.modificationCount;
        }
    }

    protected final boolean getDisposed() {
        return this.disposed;
    }

    protected final void setDisposed(boolean z) {
        this.disposed = z;
    }

    protected final HashMap<M, ModuleData> getDescriptorByModule() {
        return this.descriptorByModule;
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public ResolverForModule tryGetResolverForModule(M moduleInfo) {
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        checkValid();
        if (isCorrectModuleInfo(moduleInfo)) {
            return resolverForModuleDescriptor(doGetDescriptorForModule(moduleInfo));
        }
        return null;
    }

    private final void setupModuleDescriptor(M module, ModuleDescriptorImpl moduleDescriptor) {
        checkValid();
        moduleDescriptor.setDependencies(new LazyModuleDependencies(this.projectContext.getStorageManager(), module, sdkDependency(module), this));
        moduleDescriptor.initialize(new DelegatingPackageFragmentProvider(this, moduleDescriptor, modulesContent(module), this.packageOracleFactory.createOracle(module)));
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public Collection<M> getAllModules() {
        return (Collection) this.allModules.getValue();
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public String getName() {
        return "Resolver for '" + this.debugName + '\'';
    }

    private final boolean isCorrectModuleInfo(M moduleInfo) {
        ModuleInfo originalModule;
        Collection<M> allModules = getAllModules();
        DerivedModuleInfo derivedModuleInfo = moduleInfo instanceof DerivedModuleInfo ? (DerivedModuleInfo) moduleInfo : null;
        if (derivedModuleInfo != null && (originalModule = derivedModuleInfo.getOriginalModule()) != null) {
            moduleInfo = (M) originalModule;
        }
        return CollectionsKt.contains(allModules, moduleInfo);
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public final ResolverForModule resolverForModuleDescriptor(ModuleDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ResolverForModule resolverForModuleResolverForModuleDescriptorImpl = resolverForModuleDescriptorImpl(descriptor);
        if (resolverForModuleResolverForModuleDescriptorImpl != null) {
            return resolverForModuleResolverForModuleDescriptorImpl;
        }
        KotlinExceptionWithAttachments kotlinExceptionWithAttachments = new KotlinExceptionWithAttachments(descriptor + " is not contained in resolver " + getName());
        kotlinExceptionWithAttachments.withAttachment("resolverContents.txt", (Object) ("Expected module descriptor: " + descriptor + "\n\n" + renderResolversChainContents()));
        throw kotlinExceptionWithAttachments;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ResolverForModule resolverForModuleDescriptorImpl(final ModuleDescriptor descriptor) {
        return (ResolverForModule) this.projectContext.getStorageManager().compute(new Function0<ResolverForModule>(this) { // from class: org.jetbrains.kotlin.analyzer.AbstractResolverForProject.resolverForModuleDescriptorImpl.1
            final /* synthetic */ AbstractResolverForProject<M> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final ResolverForModule invoke() {
                this.this$0.checkValid();
                descriptor.mo7478assertValid();
                ModuleInfo moduleInfo = (ModuleInfo) ((AbstractResolverForProject) this.this$0).moduleInfoByDescriptor.get(descriptor);
                if (moduleInfo == null) {
                    if (((AbstractResolverForProject) this.this$0).delegateResolver instanceof EmptyResolverForProject) {
                        return null;
                    }
                    ResolverForProject resolverForProject = ((AbstractResolverForProject) this.this$0).delegateResolver;
                    Intrinsics.checkNotNull(resolverForProject, "null cannot be cast to non-null type org.jetbrains.kotlin.analyzer.AbstractResolverForProject<M of org.jetbrains.kotlin.analyzer.AbstractResolverForProject>");
                    return ((AbstractResolverForProject) resolverForProject).resolverForModuleDescriptorImpl(descriptor);
                }
                HashMap map = ((AbstractResolverForProject) this.this$0).resolverByModuleDescriptor;
                ModuleDescriptor moduleDescriptor = descriptor;
                AbstractResolverForProject<M> abstractResolverForProject = this.this$0;
                Object objCreateResolverForModule = map.get(moduleDescriptor);
                if (objCreateResolverForModule == null) {
                    abstractResolverForProject.checkModuleIsCorrect(moduleInfo);
                    ResolverForModuleComputationTracker companion = ResolverForModuleComputationTracker.INSTANCE.getInstance(abstractResolverForProject.getProjectContext().getProject());
                    if (companion != null) {
                        companion.onResolverComputed(moduleInfo);
                    }
                    objCreateResolverForModule = abstractResolverForProject.createResolverForModule(moduleDescriptor, moduleInfo);
                    map.put(moduleDescriptor, objCreateResolverForModule);
                }
                return (ResolverForModule) objCreateResolverForModule;
            }
        });
    }

    public final boolean isResolverForModuleDescriptorComputed$frontend(final ModuleDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return ((Boolean) this.projectContext.getStorageManager().compute(new Function0<Boolean>(this) { // from class: org.jetbrains.kotlin.analyzer.AbstractResolverForProject$isResolverForModuleDescriptorComputed$1
            final /* synthetic */ AbstractResolverForProject<M> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(((AbstractResolverForProject) this.this$0).resolverByModuleDescriptor.containsKey(descriptor));
            }
        })).booleanValue();
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    /* JADX INFO: renamed from: descriptorForModule */
    public ModuleDescriptorImpl mo6073descriptorForModule(M moduleInfo) {
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        checkValid();
        checkModuleIsCorrect(moduleInfo);
        return doGetDescriptorForModule(moduleInfo);
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public M moduleInfoForModuleDescriptor(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        checkValid();
        M m = this.moduleInfoByDescriptor.get(moduleDescriptor);
        return m == null ? (M) this.delegateResolver.moduleInfoForModuleDescriptor(moduleDescriptor) : m;
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public Void diagnoseUnknownModuleInfo(List<? extends ModuleInfo> infos) {
        Intrinsics.checkNotNullParameter(infos, "infos");
        DiagnoseUnknownModuleInfoReporter.INSTANCE.report(getName(), infos, getAllModules());
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkModuleIsCorrect(M moduleInfo) {
        if (isCorrectModuleInfo(moduleInfo)) {
            return;
        }
        diagnoseUnknownModuleInfo(CollectionsKt.listOf(moduleInfo));
        throw null;
    }

    private final ModuleDescriptorImpl doGetDescriptorForModule(M module) {
        final M m = ((module instanceof DerivedModuleInfo) && this.moduleInfoToResolvableInfo.containsKey(((DerivedModuleInfo) module).getOriginalModule())) ? module : null;
        if (m == null && (m = this.moduleInfoToResolvableInfo.get(module)) == null) {
            ModuleDescriptor moduleDescriptorMo6073descriptorForModule = this.delegateResolver.mo6073descriptorForModule(module);
            Intrinsics.checkNotNull(moduleDescriptorMo6073descriptorForModule, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl");
            return (ModuleDescriptorImpl) moduleDescriptorMo6073descriptorForModule;
        }
        return (ModuleDescriptorImpl) this.projectContext.getStorageManager().compute(new Function0<ModuleDescriptorImpl>(this) { // from class: org.jetbrains.kotlin.analyzer.AbstractResolverForProject.doGetDescriptorForModule.1
            final /* synthetic */ AbstractResolverForProject<M> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final ModuleDescriptorImpl invoke() {
                HashMap<M, ModuleData> descriptorByModule = this.this$0.getDescriptorByModule();
                M m2 = m;
                AbstractResolverForProject<M> abstractResolverForProject = this.this$0;
                ModuleData moduleDataCreateModuleDescriptor = descriptorByModule.get(m2);
                if (moduleDataCreateModuleDescriptor == null) {
                    moduleDataCreateModuleDescriptor = abstractResolverForProject.createModuleDescriptor(m2);
                    descriptorByModule.put(m2, moduleDataCreateModuleDescriptor);
                }
                ModuleData moduleDataRecreateModuleDescriptor = moduleDataCreateModuleDescriptor;
                if (moduleDataRecreateModuleDescriptor.isOutOfDate()) {
                    moduleDataRecreateModuleDescriptor = this.this$0.recreateModuleDescriptor(m);
                }
                return moduleDataRecreateModuleDescriptor.getModuleDescriptor();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ModuleData recreateModuleDescriptor(M module) {
        ModuleData moduleData = this.descriptorByModule.get(module);
        ModuleDescriptorImpl moduleDescriptor = moduleData != null ? moduleData.getModuleDescriptor() : null;
        if (moduleDescriptor != null) {
            moduleDescriptor.setValid(false);
            this.moduleInfoByDescriptor.remove(moduleDescriptor);
            this.resolverByModuleDescriptor.remove(moduleDescriptor);
            ((ModuleDescriptorListener) this.projectContext.getProject().getMessageBus().syncPublisher(ModuleDescriptorListener.TOPIC)).moduleDescriptorInvalidated(moduleDescriptor);
        }
        ModuleData moduleDataCreateModuleDescriptor = createModuleDescriptor(module);
        this.descriptorByModule.put(module, moduleDataCreateModuleDescriptor);
        return moduleDataCreateModuleDescriptor;
    }

    protected Map<ModuleCapability<?>, Object> getAdditionalCapabilities() {
        return MapsKt.emptyMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ModuleData createModuleDescriptor(M module) {
        ModificationTracker modificationTrackerCreateModificationTracker;
        ModuleDescriptorImpl moduleDescriptorImpl = new ModuleDescriptorImpl(module.getName(), this.projectContext.getStorageManager(), builtInsForModule(module), module.getPlatform(), MapsKt.plus(module.getCapabilities(), getAdditionalCapabilities()), module.getStableName());
        this.moduleInfoByDescriptor.put(moduleDescriptorImpl, module);
        setupModuleDescriptor(module, moduleDescriptorImpl);
        TrackableModuleInfo trackableModuleInfo = module instanceof TrackableModuleInfo ? (TrackableModuleInfo) module : null;
        if (trackableModuleInfo == null || (modificationTrackerCreateModificationTracker = trackableModuleInfo.createModificationTracker()) == null) {
            modificationTrackerCreateModificationTracker = this.fallbackModificationTracker;
        }
        return new ModuleData(moduleDescriptorImpl, modificationTrackerCreateModificationTracker);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkValid() {
        if (this.disposed) {
            reportInvalidResolver();
        }
    }

    protected void reportInvalidResolver() {
        throw new InvalidResolverException(getName() + " is invalidated");
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        this.projectContext.getStorageManager().compute(new Function0<Unit>(this) { // from class: org.jetbrains.kotlin.analyzer.AbstractResolverForProject.dispose.1
            final /* synthetic */ AbstractResolverForProject<M> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.setDisposed(true);
                Collection<ModuleData> collectionValues = this.this$0.getDescriptorByModule().values();
                Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
                AbstractResolverForProject<M> abstractResolverForProject = this.this$0;
                for (ModuleData moduleData : collectionValues) {
                    ((AbstractResolverForProject) abstractResolverForProject).moduleInfoByDescriptor.remove(moduleData.getModuleDescriptor());
                    moduleData.getModuleDescriptor().setValid(false);
                }
                this.this$0.getDescriptorByModule().clear();
                Set setKeySet = ((AbstractResolverForProject) this.this$0).moduleInfoByDescriptor.keySet();
                Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
                Iterator it = setKeySet.iterator();
                while (it.hasNext()) {
                    ((ModuleDescriptorImpl) it.next()).setValid(false);
                }
                ((AbstractResolverForProject) this.this$0).moduleInfoByDescriptor.clear();
            }
        });
    }

    private final String renderResolversChainContents() {
        return SequencesKt.joinToString$default(SequencesKt.generateSequence(this, new Function1<AbstractResolverForProject<M>, AbstractResolverForProject<M>>() { // from class: org.jetbrains.kotlin.analyzer.AbstractResolverForProject$renderResolversChainContents$resolversChain$1
            @Override // kotlin.jvm.functions.Function1
            public final AbstractResolverForProject<M> invoke(AbstractResolverForProject<M> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ResolverForProject resolverForProject = ((AbstractResolverForProject) it).delegateResolver;
                if (resolverForProject instanceof AbstractResolverForProject) {
                    return (AbstractResolverForProject) resolverForProject;
                }
                return null;
            }
        }), "\n\n", null, null, 0, null, new Function1<AbstractResolverForProject<M>, CharSequence>() { // from class: org.jetbrains.kotlin.analyzer.AbstractResolverForProject.renderResolversChainContents.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(AbstractResolverForProject<M> resolver) {
                Intrinsics.checkNotNullParameter(resolver, "resolver");
                return "Resolver: " + resolver.getName() + "\n'moduleInfoByDescriptor' content:\n[" + resolver.renderResolverModuleInfos() + ']';
            }
        }, 30, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String renderResolverModuleInfos() {
        return (String) this.projectContext.getStorageManager().compute(new Function0<String>(this) { // from class: org.jetbrains.kotlin.analyzer.AbstractResolverForProject.renderResolverModuleInfos.1
            final /* synthetic */ AbstractResolverForProject<M> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                Set setEntrySet = ((AbstractResolverForProject) this.this$0).moduleInfoByDescriptor.entrySet();
                Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
                return CollectionsKt.joinToString$default(setEntrySet, ",\n", null, null, 0, null, new Function1<Map.Entry<ModuleDescriptorImpl, M>, CharSequence>() { // from class: org.jetbrains.kotlin.analyzer.AbstractResolverForProject.renderResolverModuleInfos.1.1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(Map.Entry<ModuleDescriptorImpl, M> entry) {
                        Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                        return StringsKt.trimIndent("\n            {\n                moduleDescriptor: " + entry.getKey() + "\n                moduleInfo: " + entry.getValue() + "\n            }\n            ");
                    }
                }, 30, null);
            }
        });
    }
}
