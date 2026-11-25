package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.runtime.components.ReflectJavaClassFinder;
import org.jetbrains.kotlin.descriptors.runtime.components.ReflectKotlinClassFinder;
import org.jetbrains.kotlin.descriptors.runtime.components.RuntimeErrorReporter;
import org.jetbrains.kotlin.descriptors.runtime.components.RuntimeSourceElementFactory;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.load.java.components.JavaResolverCache;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaPackageFragmentProvider;
import org.jetbrains.kotlin.load.java.lazy.SingleModuleClassResolver;
import org.jetbrains.kotlin.load.kotlin.DeserializationComponentsForJavaKt;
import org.jetbrains.kotlin.load.kotlin.DeserializedDescriptorResolver;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.jvm.JavaDescriptorResolver;
import org.jetbrains.kotlin.resolve.jvm.extensions.PackageFragmentProviderExtension;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.MetadataHelpersKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: PackageFragmentFromClassLoaderProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B-\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ:\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R!\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/PackageFragmentFromClassLoaderProviderExtension;", "Lorg/jetbrains/kotlin/resolve/jvm/extensions/PackageFragmentProviderExtension;", "classLoaderGetter", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "Ljava/lang/ClassLoader;", "Lkotlin/script/experimental/jvm/ClassLoaderByConfiguration;", "scriptCompilationConfiguration", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "(Lkotlin/jvm/functions/Function1;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lorg/jetbrains/kotlin/config/CompilerConfiguration;)V", "getClassLoaderGetter", "()Lkotlin/jvm/functions/Function1;", "getCompilerConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getScriptCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getPackageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackageFragmentFromClassLoaderProviderExtension implements PackageFragmentProviderExtension {
    private final Function1<ScriptCompilationConfiguration, ClassLoader> classLoaderGetter;
    private final CompilerConfiguration compilerConfiguration;
    private final ScriptCompilationConfiguration scriptCompilationConfiguration;

    /* JADX WARN: Multi-variable type inference failed */
    public PackageFragmentFromClassLoaderProviderExtension(Function1<? super ScriptCompilationConfiguration, ? extends ClassLoader> classLoaderGetter, ScriptCompilationConfiguration scriptCompilationConfiguration, CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(classLoaderGetter, "classLoaderGetter");
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        this.classLoaderGetter = classLoaderGetter;
        this.scriptCompilationConfiguration = scriptCompilationConfiguration;
        this.compilerConfiguration = compilerConfiguration;
    }

    public final Function1<ScriptCompilationConfiguration, ClassLoader> getClassLoaderGetter() {
        return this.classLoaderGetter;
    }

    public final ScriptCompilationConfiguration getScriptCompilationConfiguration() {
        return this.scriptCompilationConfiguration;
    }

    public final CompilerConfiguration getCompilerConfiguration() {
        return this.compilerConfiguration;
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.extensions.PackageFragmentProviderExtension
    public PackageFragmentProvider getPackageFragmentProvider(Project project, ModuleDescriptor module, StorageManager storageManager, BindingTrace trace, ModuleInfo moduleInfo, LookupTracker lookupTracker) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        ClassLoader classLoaderInvoke = this.classLoaderGetter.invoke(this.scriptCompilationConfiguration);
        ReflectKotlinClassFinder reflectKotlinClassFinder = new ReflectKotlinClassFinder(classLoaderInvoke);
        DeserializedDescriptorResolver deserializedDescriptorResolver = new DeserializedDescriptorResolver();
        SingleModuleClassResolver singleModuleClassResolver = new SingleModuleClassResolver();
        NotFoundClasses notFoundClasses = new NotFoundClasses(storageManager, module);
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(this.compilerConfiguration);
        Object obj = this.compilerConfiguration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNull(obj);
        ReflectKotlinClassFinder reflectKotlinClassFinder2 = reflectKotlinClassFinder;
        LazyJavaPackageFragmentProvider lazyJavaPackageFragmentProviderMakeLazyJavaPackageFragmentProvider = DeserializationComponentsForJavaKt.makeLazyJavaPackageFragmentProvider(new ReflectJavaClassFinder(classLoaderInvoke), module, storageManager, notFoundClasses, reflectKotlinClassFinder2, deserializedDescriptorResolver, RuntimeErrorReporter.INSTANCE, RuntimeSourceElementFactory.INSTANCE, singleModuleClassResolver, new PackagePartFromClassLoaderProvider(classLoaderInvoke, languageVersionSettings, (MessageCollector) obj));
        deserializedDescriptorResolver.setComponents(DeserializationComponentsForJavaKt.makeDeserializationComponentsForJava(module, storageManager, notFoundClasses, lazyJavaPackageFragmentProviderMakeLazyJavaPackageFragmentProvider, reflectKotlinClassFinder2, deserializedDescriptorResolver, RuntimeErrorReporter.INSTANCE, MetadataHelpersKt.toMetadataVersion(languageVersionSettings.getLanguageVersion())));
        JavaResolverCache EMPTY = JavaResolverCache.EMPTY;
        Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
        singleModuleClassResolver.setResolver(new JavaDescriptorResolver(lazyJavaPackageFragmentProviderMakeLazyJavaPackageFragmentProvider, EMPTY));
        return lazyJavaPackageFragmentProviderMakeLazyJavaPackageFragmentProvider;
    }
}
