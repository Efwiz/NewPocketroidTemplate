package org.jetbrains.kotlin.analyzer.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: CommonResolverForModuleFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/common/PackageFragmentProviderCreationContext;", "", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModuleInfo", "()Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackageFragmentProviderCreationContext {
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor moduleDescriptor;
    private final ModuleInfo moduleInfo;
    private final StorageManager storageManager;

    public PackageFragmentProviderCreationContext(ModuleInfo moduleInfo, StorageManager storageManager, LanguageVersionSettings languageVersionSettings, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        this.moduleInfo = moduleInfo;
        this.storageManager = storageManager;
        this.languageVersionSettings = languageVersionSettings;
        this.moduleDescriptor = moduleDescriptor;
    }

    public final ModuleInfo getModuleInfo() {
        return this.moduleInfo;
    }

    public final StorageManager getStorageManager() {
        return this.storageManager;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }
}
