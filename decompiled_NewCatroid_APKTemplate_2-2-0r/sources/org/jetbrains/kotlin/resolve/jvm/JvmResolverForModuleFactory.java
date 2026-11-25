package org.jetbrains.kotlin.resolve.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleContent;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.analyzer.ResolverForModule;
import org.jetbrains.kotlin.analyzer.ResolverForModuleFactory;
import org.jetbrains.kotlin.analyzer.ResolverForProject;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsPackageFragmentProvider;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.CompositePackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.frontend.java.di.InjectionKt;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.load.java.lazy.ModuleClassResolverImpl;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.jvm.JvmPlatformKt;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.CliSealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.CodeAnalyzerInitializer;
import org.jetbrains.kotlin.resolve.SealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.TargetEnvironment;
import org.jetbrains.kotlin.resolve.jvm.extensions.PackageFragmentProviderExtension;
import org.jetbrains.kotlin.resolve.lazy.AbsentDescriptorHandler;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactoryService;
import org.jetbrains.kotlin.resolve.scopes.optimization.OptimizingOptions;

/* JADX INFO: compiled from: JvmResolverForModuleFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJj\u0010\t\u001a\u00020\n\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0010\u0010\u001b\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u001d\u0018\u00010\u001cH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmResolverForModuleFactory;", "Lorg/jetbrains/kotlin/analyzer/ResolverForModuleFactory;", "platformParameters", "Lorg/jetbrains/kotlin/resolve/jvm/JvmPlatformParameters;", "targetEnvironment", "Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "(Lorg/jetbrains/kotlin/resolve/jvm/JvmPlatformParameters;Lorg/jetbrains/kotlin/resolve/TargetEnvironment;Lorg/jetbrains/kotlin/platform/TargetPlatform;)V", "createResolverForModule", "Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "M", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "moduleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "moduleContent", "Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "resolverForProject", "Lorg/jetbrains/kotlin/analyzer/ResolverForProject;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "sealedInheritorsProvider", "Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "resolveOptimizingOptions", "Lorg/jetbrains/kotlin/resolve/scopes/optimization/OptimizingOptions;", "absentDescriptorHandlerClass", "Ljava/lang/Class;", "Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmResolverForModuleFactory extends ResolverForModuleFactory {
    private final TargetPlatform platform;
    private final JvmPlatformParameters platformParameters;
    private final TargetEnvironment targetEnvironment;

    public JvmResolverForModuleFactory(JvmPlatformParameters platformParameters, TargetEnvironment targetEnvironment, TargetPlatform platform) {
        Intrinsics.checkNotNullParameter(platformParameters, "platformParameters");
        Intrinsics.checkNotNullParameter(targetEnvironment, "targetEnvironment");
        Intrinsics.checkNotNullParameter(platform, "platform");
        this.platformParameters = platformParameters;
        this.targetEnvironment = targetEnvironment;
        this.platform = platform;
    }

    @Override // org.jetbrains.kotlin.analyzer.ResolverForModuleFactory
    public <M extends ModuleInfo> ResolverForModule createResolverForModule(ModuleDescriptorImpl moduleDescriptor, ModuleContext moduleContext, ModuleContent<? extends M> moduleContent, final ResolverForProject<M> resolverForProject, LanguageVersionSettings languageVersionSettings, SealedClassInheritorsProvider sealedInheritorsProvider, OptimizingOptions resolveOptimizingOptions, Class<? extends AbsentDescriptorHandler> absentDescriptorHandlerClass) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(moduleContent, "moduleContent");
        Intrinsics.checkNotNullParameter(resolverForProject, "resolverForProject");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(sealedInheritorsProvider, "sealedInheritorsProvider");
        final ModuleInfo moduleInfoComponent1 = moduleContent.component1();
        Collection<KtFile> collectionComponent2 = moduleContent.component2();
        GlobalSearchScope moduleContentScope = moduleContent.getModuleContentScope();
        Project project = moduleContext.getProject();
        DeclarationProviderFactory declarationProviderFactoryCreateDeclarationProviderFactory = DeclarationProviderFactoryService.INSTANCE.createDeclarationProviderFactory(project, moduleContext.getStorageManager(), collectionComponent2, moduleContentScope, moduleInfoComponent1);
        ModuleClassResolverImpl moduleClassResolverImpl = new ModuleClassResolverImpl(new Function1<JavaClass, JavaDescriptorResolver>() { // from class: org.jetbrains.kotlin.resolve.jvm.JvmResolverForModuleFactory$createResolverForModule$moduleClassResolver$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Incorrect types in method signature: (Lorg/jetbrains/kotlin/resolve/jvm/JvmResolverForModuleFactory;TM;Lorg/jetbrains/kotlin/analyzer/ResolverForProject<TM;>;)V */
            {
                super(1);
            }

            /* JADX WARN: Code duplicated, block: B:14:0x004c  */
            @Override // kotlin.jvm.functions.Function1
            public final JavaDescriptorResolver invoke(JavaClass javaClass) {
                ResolverForModule resolverForModuleTryGetResolverForModule;
                ResolverForModule resolverForModule;
                Intrinsics.checkNotNullParameter(javaClass, "javaClass");
                ModuleInfo moduleInfoInvoke = this.this$0.platformParameters.getModuleByJavaClass().invoke(javaClass);
                if (moduleInfoInvoke != null) {
                    JvmResolverForModuleFactory jvmResolverForModuleFactory = this.this$0;
                    ModuleInfo moduleInfo = moduleInfoComponent1;
                    ResolverForProject<M> resolverForProject2 = resolverForProject;
                    if (jvmResolverForModuleFactory.platformParameters.getResolverForReferencedModule() != null) {
                        resolverForModuleTryGetResolverForModule = jvmResolverForModuleFactory.platformParameters.getResolverForReferencedModule().invoke(moduleInfoInvoke, moduleInfo);
                    } else {
                        resolverForModuleTryGetResolverForModule = resolverForProject2.tryGetResolverForModule(moduleInfoInvoke);
                    }
                } else {
                    resolverForModuleTryGetResolverForModule = null;
                }
                if (resolverForModuleTryGetResolverForModule != null) {
                    resolverForModule = JvmPlatformKt.isJvm(moduleInfoInvoke.getPlatform()) ? resolverForModuleTryGetResolverForModule : null;
                    if (resolverForModule == null) {
                        resolverForModule = resolverForProject.resolverForModule(moduleInfoComponent1);
                    }
                } else {
                    resolverForModule = resolverForProject.resolverForModule(moduleInfoComponent1);
                }
                return (JavaDescriptorResolver) DslKt.getService(resolverForModule.getComponentProvider(), JavaDescriptorResolver.class);
            }
        });
        BindingTrace bindingTraceCreateTrace = CodeAnalyzerInitializer.INSTANCE.getInstance(project).createTrace();
        LookupTracker.DO_NOTHING do_nothing = LookupTracker.DO_NOTHING.INSTANCE;
        PackagePartProvider packagePartProviderInvoke = this.platformParameters.getPackagePartProviderFactory().invoke(moduleContent);
        TargetPlatform platform = moduleDescriptor.getPlatform();
        Intrinsics.checkNotNull(platform);
        LookupTracker.DO_NOTHING do_nothing2 = do_nothing;
        StorageComponentContainer storageComponentContainerCreateContainerForLazyResolveWithJava = InjectionKt.createContainerForLazyResolveWithJava(platform, moduleContext, bindingTraceCreateTrace, declarationProviderFactoryCreateDeclarationProviderFactory, moduleContentScope, moduleClassResolverImpl, this.targetEnvironment, do_nothing2, ExpectActualTracker.DoNothing.INSTANCE, InlineConstTracker.DoNothing.INSTANCE, EnumWhenTracker.DoNothing.INSTANCE, packagePartProviderInvoke, languageVersionSettings, this.platformParameters.getUseBuiltinsProviderForModule().invoke(moduleInfoComponent1).booleanValue(), (950272 & 16384) != 0 ? null : null, (32768 & 950272) != 0 ? null : null, (65536 & 950272) != 0 ? null : null, (131072 & 950272) != 0 ? CliSealedClassInheritorsProvider.INSTANCE : sealedInheritorsProvider, (262144 & 950272) != 0 ? null : resolveOptimizingOptions, (950272 & 524288) != 0 ? null : absentDescriptorHandlerClass);
        ArrayList arrayListArrayListOf = CollectionsKt.arrayListOf(((ResolveSession) DslKt.getService(storageComponentContainerCreateContainerForLazyResolveWithJava, ResolveSession.class)).getPackageFragmentProvider(), ((JavaDescriptorResolver) DslKt.getService(storageComponentContainerCreateContainerForLazyResolveWithJava, JavaDescriptorResolver.class)).getPackageFragmentProvider());
        ArrayList arrayList = arrayListArrayListOf;
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, DslKt.tryGetService(storageComponentContainerCreateContainerForLazyResolveWithJava, JvmBuiltInsPackageFragmentProvider.class));
        List<PackageFragmentProviderExtension> instances = PackageFragmentProviderExtension.INSTANCE.getInstances(project);
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it = instances.iterator();
        while (it.hasNext()) {
            PackageFragmentProvider packageFragmentProvider = ((PackageFragmentProviderExtension) it.next()).getPackageFragmentProvider(project, moduleDescriptor, moduleContext.getStorageManager(), bindingTraceCreateTrace, moduleInfoComponent1, do_nothing2);
            if (packageFragmentProvider != null) {
                arrayList2.add(packageFragmentProvider);
            }
        }
        CollectionsKt.addAll(arrayList, arrayList2);
        return new ResolverForModule(new CompositePackageFragmentProvider(arrayListArrayListOf, "CompositeProvider@JvmResolver for " + moduleDescriptor), storageComponentContainerCreateContainerForLazyResolveWithJava);
    }
}
