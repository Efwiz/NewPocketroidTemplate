package org.jetbrains.kotlin.analyzer;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.resolve.SealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.lazy.AbsentDescriptorHandler;
import org.jetbrains.kotlin.resolve.scopes.optimization.OptimizingOptions;

/* JADX INFO: compiled from: AnalyzerFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JN\u0010\u0003\u001a\u00020\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00050\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0017JX\u0010\u0003\u001a\u00020\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00050\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0017Jj\u0010\u0003\u001a\u00020\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00050\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0010\u0010\u0015\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0017\u0018\u00010\u0016H\u0016¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/ResolverForModuleFactory;", "", "()V", "createResolverForModule", "Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "M", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "moduleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "moduleContent", "Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "resolverForProject", "Lorg/jetbrains/kotlin/analyzer/ResolverForProject;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "sealedInheritorsProvider", "Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "resolveOptimizingOptions", "Lorg/jetbrains/kotlin/resolve/scopes/optimization/OptimizingOptions;", "absentDescriptorHandlerClass", "Ljava/lang/Class;", "Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResolverForModuleFactory {
    public <M extends ModuleInfo> ResolverForModule createResolverForModule(ModuleDescriptorImpl moduleDescriptor, ModuleContext moduleContext, ModuleContent<? extends M> moduleContent, ResolverForProject<M> resolverForProject, LanguageVersionSettings languageVersionSettings, SealedClassInheritorsProvider sealedInheritorsProvider, OptimizingOptions resolveOptimizingOptions, Class<? extends AbsentDescriptorHandler> absentDescriptorHandlerClass) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(moduleContent, "moduleContent");
        Intrinsics.checkNotNullParameter(resolverForProject, "resolverForProject");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(sealedInheritorsProvider, "sealedInheritorsProvider");
        return createResolverForModule(moduleDescriptor, moduleContext, moduleContent, resolverForProject, languageVersionSettings, sealedInheritorsProvider, resolveOptimizingOptions);
    }

    @Deprecated(message = "Left only for compatibility, please use full version", replaceWith = @ReplaceWith(expression = "createResolverForModule(moduleDescriptor, moduleContext, moduleContent, resolverForProject, languageVersionSettings, sealedInheritorsProvider, null, null)", imports = {}))
    public <M extends ModuleInfo> ResolverForModule createResolverForModule(ModuleDescriptorImpl moduleDescriptor, ModuleContext moduleContext, ModuleContent<? extends M> moduleContent, ResolverForProject<M> resolverForProject, LanguageVersionSettings languageVersionSettings, SealedClassInheritorsProvider sealedInheritorsProvider, OptimizingOptions resolveOptimizingOptions) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(moduleContent, "moduleContent");
        Intrinsics.checkNotNullParameter(resolverForProject, "resolverForProject");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(sealedInheritorsProvider, "sealedInheritorsProvider");
        return createResolverForModule(moduleDescriptor, moduleContext, moduleContent, resolverForProject, languageVersionSettings, sealedInheritorsProvider);
    }

    @Deprecated(message = "Left only for compatibility, please use full version", replaceWith = @ReplaceWith(expression = "createResolverForModule(moduleDescriptor, moduleContext, moduleContent, resolverForProject, languageVersionSettings, sealedInheritorsProvider, null, null)", imports = {}))
    public <M extends ModuleInfo> ResolverForModule createResolverForModule(ModuleDescriptorImpl moduleDescriptor, ModuleContext moduleContext, ModuleContent<? extends M> moduleContent, ResolverForProject<M> resolverForProject, LanguageVersionSettings languageVersionSettings, SealedClassInheritorsProvider sealedInheritorsProvider) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(moduleContent, "moduleContent");
        Intrinsics.checkNotNullParameter(resolverForProject, "resolverForProject");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(sealedInheritorsProvider, "sealedInheritorsProvider");
        return createResolverForModule(moduleDescriptor, moduleContext, moduleContent, resolverForProject, languageVersionSettings, sealedInheritorsProvider, null, null);
    }
}
