package org.jetbrains.kotlin.analyzer;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;

/* JADX INFO: compiled from: AnalyzerFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u0000 \u001f*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001\u001fB\u0005¢\u0006\u0002\u0010\u0004J\u0015\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014H&J\u0015\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u000eH&¢\u0006\u0002\u0010\u0017J\u0013\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00028\u0000¢\u0006\u0002\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u000eH&J\b\u0010\u001d\u001a\u00020\nH\u0016J\u0017\u0010\u001e\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u000f\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u001aR\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/analyzer/ResolverForProject;", "M", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "", "()V", "allModules", "", "getAllModules", "()Ljava/util/Collection;", "name", "", "getName", "()Ljava/lang/String;", "descriptorForModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "moduleInfo", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "diagnoseUnknownModuleInfo", "", AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS, "", "moduleInfoForModuleDescriptor", "moduleDescriptor", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "resolverForModule", "Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;)Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "resolverForModuleDescriptor", "descriptor", "toString", "tryGetResolverForModule", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResolverForProject<M extends ModuleInfo> {
    public static final String resolverForLibrariesName = "project libraries";
    public static final String resolverForModulesName = "project source roots and libraries";
    public static final String resolverForScriptDependenciesName = "dependencies of scripts";
    public static final String resolverForSdkName = "sdk";
    public static final String resolverForSpecialInfoName = "completion/highlighting in ";

    /* JADX INFO: renamed from: descriptorForModule */
    public abstract ModuleDescriptor mo6073descriptorForModule(M moduleInfo);

    public abstract Void diagnoseUnknownModuleInfo(List<? extends ModuleInfo> infos);

    public abstract Collection<M> getAllModules();

    public abstract String getName();

    public abstract M moduleInfoForModuleDescriptor(ModuleDescriptor moduleDescriptor);

    public abstract ResolverForModule resolverForModuleDescriptor(ModuleDescriptor descriptor);

    public abstract ResolverForModule tryGetResolverForModule(M moduleInfo);

    public final ResolverForModule resolverForModule(M moduleInfo) {
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        return resolverForModuleDescriptor(mo6073descriptorForModule(moduleInfo));
    }

    public String toString() {
        return getName();
    }
}
