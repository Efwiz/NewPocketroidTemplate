package org.jetbrains.kotlin.resolve.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.PlatformToKotlinClassMapper;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.QualifiedExpressionResolver;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.scopes.optimization.OptimizingOptions;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: LazyImportScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/ImportResolutionComponents;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "qualifiedExpressionResolver", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "platformToKotlinClassMapper", "Lorg/jetbrains/kotlin/builtins/PlatformToKotlinClassMapper;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "optimizingOptions", "Lorg/jetbrains/kotlin/resolve/scopes/optimization/OptimizingOptions;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/builtins/PlatformToKotlinClassMapper;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/resolve/scopes/optimization/OptimizingOptions;)V", "getDeprecationResolver", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getOptimizingOptions", "()Lorg/jetbrains/kotlin/resolve/scopes/optimization/OptimizingOptions;", "getPlatformToKotlinClassMapper", "()Lorg/jetbrains/kotlin/builtins/PlatformToKotlinClassMapper;", "getQualifiedExpressionResolver", "()Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImportResolutionComponents {
    private final DeprecationResolver deprecationResolver;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor moduleDescriptor;
    private final OptimizingOptions optimizingOptions;
    private final PlatformToKotlinClassMapper platformToKotlinClassMapper;
    private final QualifiedExpressionResolver qualifiedExpressionResolver;
    private final StorageManager storageManager;

    public ImportResolutionComponents(StorageManager storageManager, QualifiedExpressionResolver qualifiedExpressionResolver, ModuleDescriptor moduleDescriptor, PlatformToKotlinClassMapper platformToKotlinClassMapper, LanguageVersionSettings languageVersionSettings, DeprecationResolver deprecationResolver, OptimizingOptions optimizingOptions) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(qualifiedExpressionResolver, "qualifiedExpressionResolver");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(platformToKotlinClassMapper, "platformToKotlinClassMapper");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(optimizingOptions, "optimizingOptions");
        this.storageManager = storageManager;
        this.qualifiedExpressionResolver = qualifiedExpressionResolver;
        this.moduleDescriptor = moduleDescriptor;
        this.platformToKotlinClassMapper = platformToKotlinClassMapper;
        this.languageVersionSettings = languageVersionSettings;
        this.deprecationResolver = deprecationResolver;
        this.optimizingOptions = optimizingOptions;
    }

    public final StorageManager getStorageManager() {
        return this.storageManager;
    }

    public final QualifiedExpressionResolver getQualifiedExpressionResolver() {
        return this.qualifiedExpressionResolver;
    }

    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    public final PlatformToKotlinClassMapper getPlatformToKotlinClassMapper() {
        return this.platformToKotlinClassMapper;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final DeprecationResolver getDeprecationResolver() {
        return this.deprecationResolver;
    }

    public final OptimizingOptions getOptimizingOptions() {
        return this.optimizingOptions;
    }
}
