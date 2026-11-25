package org.jetbrains.kotlin.library.metadata.impl;

import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.library.metadata.KlibModuleDescriptorFactory;
import org.jetbrains.kotlin.library.metadata.KlibModuleOrigin;
import org.jetbrains.kotlin.library.metadata.KlibModuleOriginKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.konan.NativePlatforms;
import org.jetbrains.kotlin.resolve.ImplicitIntegerCoercion;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibModuleDescriptorFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006JB\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00102\u0018\u0010\u0011\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0012H\u0016J:\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0018\u0010\u0011\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0012H\u0016R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/impl/KlibModuleDescriptorFactoryImpl;", "Lorg/jetbrains/kotlin/library/metadata/KlibModuleDescriptorFactory;", "createBuiltIns", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/storage/StorageManager;", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lkotlin/jvm/functions/Function1;)V", "getCreateBuiltIns", "()Lkotlin/jvm/functions/Function1;", "createDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "name", "Lorg/jetbrains/kotlin/name/Name;", "storageManager", "builtIns", "origin", "Lorg/jetbrains/kotlin/library/metadata/KlibModuleOrigin;", "customCapabilities", "", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "", "createDescriptorAndNewBuiltIns", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibModuleDescriptorFactoryImpl implements KlibModuleDescriptorFactory {
    private final Function1<StorageManager, KotlinBuiltIns> createBuiltIns;

    /* JADX WARN: Multi-variable type inference failed */
    public KlibModuleDescriptorFactoryImpl(Function1<? super StorageManager, ? extends KotlinBuiltIns> createBuiltIns) {
        Intrinsics.checkNotNullParameter(createBuiltIns, "createBuiltIns");
        this.createBuiltIns = createBuiltIns;
    }

    public final Function1<StorageManager, KotlinBuiltIns> getCreateBuiltIns() {
        return this.createBuiltIns;
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibModuleDescriptorFactory
    public ModuleDescriptorImpl createDescriptor(Name name2, StorageManager storageManager, KotlinBuiltIns builtIns, KlibModuleOrigin origin, Map<ModuleCapability<?>, ? extends Object> customCapabilities) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(customCapabilities, "customCapabilities");
        return new ModuleDescriptorImpl(name2, storageManager, builtIns, NativePlatforms.INSTANCE.getUnspecifiedNativePlatform(), MapsKt.plus(customCapabilities, MapsKt.mapOf(TuplesKt.to(KlibModuleOrigin.INSTANCE.getCAPABILITY(), origin), TuplesKt.to(ImplicitIntegerCoercion.INSTANCE.getMODULE_CAPABILITY(), Boolean.valueOf(KlibModuleOriginKt.isInteropLibrary(origin))))), null, 32, null);
    }

    @Override // org.jetbrains.kotlin.library.metadata.KlibModuleDescriptorFactory
    public ModuleDescriptorImpl createDescriptorAndNewBuiltIns(Name name2, StorageManager storageManager, KlibModuleOrigin origin, Map<ModuleCapability<?>, ? extends Object> customCapabilities) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(customCapabilities, "customCapabilities");
        KotlinBuiltIns kotlinBuiltInsInvoke = this.createBuiltIns.invoke(storageManager);
        ModuleDescriptorImpl moduleDescriptorImplCreateDescriptor = createDescriptor(name2, storageManager, kotlinBuiltInsInvoke, origin, customCapabilities);
        kotlinBuiltInsInvoke.setBuiltInsModule(moduleDescriptorImplCreateDescriptor);
        return moduleDescriptorImplCreateDescriptor;
    }
}
