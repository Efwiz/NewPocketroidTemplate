package org.jetbrains.kotlin.analyzer;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;

/* JADX INFO: compiled from: AnalyzerFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0015\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00020\u0013H\u0016J\u0015\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00020\u0016H\u0016¢\u0006\u0002\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0016H\u0016J\u0017\u0010\u001b\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u000f\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001cR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/EmptyResolverForProject;", "M", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "Lorg/jetbrains/kotlin/analyzer/ResolverForProject;", "()V", "allModules", "", "getAllModules", "()Ljava/util/Collection;", "name", "", "getName", "()Ljava/lang/String;", "descriptorForModule", "", "moduleInfo", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Ljava/lang/Void;", "diagnoseUnknownModuleInfo", AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS, "", "moduleInfoForModuleDescriptor", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "resolverForModuleDescriptor", "Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "descriptor", "tryGetResolverForModule", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptyResolverForProject<M extends ModuleInfo> extends ResolverForProject<M> {
    private final Collection<M> allModules = CollectionsKt.emptyList();

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public ResolverForModule tryGetResolverForModule(M moduleInfo) {
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        return null;
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    /* JADX INFO: renamed from: descriptorForModule, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ModuleDescriptor mo6073descriptorForModule(ModuleInfo moduleInfo) {
        return (ModuleDescriptor) descriptorForModule(moduleInfo);
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public String getName() {
        return "Empty resolver";
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public ResolverForModule resolverForModuleDescriptor(ModuleDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        throw new IllegalStateException(descriptor + " is not contained in this resolver");
    }

    public Void descriptorForModule(M moduleInfo) {
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        diagnoseUnknownModuleInfo(CollectionsKt.listOf(moduleInfo));
        throw null;
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public Collection<M> getAllModules() {
        return this.allModules;
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public Void diagnoseUnknownModuleInfo(List<? extends ModuleInfo> infos) {
        Intrinsics.checkNotNullParameter(infos, "infos");
        throw new IllegalStateException("Should not be called for " + infos);
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForProject
    public M moduleInfoForModuleDescriptor(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        throw new IllegalStateException(moduleDescriptor + " is not contained in this resolver");
    }
}
