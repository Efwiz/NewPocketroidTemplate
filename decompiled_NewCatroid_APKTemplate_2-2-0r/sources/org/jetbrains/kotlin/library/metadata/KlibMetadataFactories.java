package org.jetbrains.kotlin.library.metadata;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.library.metadata.impl.KlibMetadataDeserializedPackageFragmentsFactoryImpl;
import org.jetbrains.kotlin.library.metadata.impl.KlibMetadataModuleDescriptorFactoryImpl;
import org.jetbrains.kotlin.library.metadata.impl.KlibModuleDescriptorFactoryImpl;
import org.jetbrains.kotlin.library.metadata.impl.KlibResolvedModuleDescriptorsFactoryImpl;
import org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: KlibMetadataFactories.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B!\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u0012J\u000e\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u000eR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibMetadataFactories;", "", "createBuiltIns", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/storage/StorageManager;", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "flexibleTypeDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "(Lkotlin/jvm/functions/Function1;Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;)V", "DefaultDescriptorFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibModuleDescriptorFactory;", "getDefaultDescriptorFactory", "()Lorg/jetbrains/kotlin/library/metadata/KlibModuleDescriptorFactory;", "DefaultDeserializedDescriptorFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataModuleDescriptorFactory;", "getDefaultDeserializedDescriptorFactory", "()Lorg/jetbrains/kotlin/library/metadata/KlibMetadataModuleDescriptorFactory;", "DefaultPackageFragmentsFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragmentsFactory;", "getDefaultPackageFragmentsFactory", "()Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragmentsFactory;", "DefaultResolvedDescriptorsFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibResolvedModuleDescriptorsFactory;", "getDefaultResolvedDescriptorsFactory", "()Lorg/jetbrains/kotlin/library/metadata/KlibResolvedModuleDescriptorsFactory;", "getFlexibleTypeDeserializer", "()Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "createDefaultKonanDeserializedModuleDescriptorFactory", "descriptorFactory", "packageFragmentsFactory", "createDefaultKonanResolvedModuleDescriptorsFactory", "moduleDescriptorFactory", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataFactories {
    private final KlibModuleDescriptorFactory DefaultDescriptorFactory;
    private final KlibMetadataModuleDescriptorFactory DefaultDeserializedDescriptorFactory;
    private final KlibMetadataDeserializedPackageFragmentsFactory DefaultPackageFragmentsFactory;
    private final KlibResolvedModuleDescriptorsFactory DefaultResolvedDescriptorsFactory;
    private final FlexibleTypeDeserializer flexibleTypeDeserializer;

    public KlibMetadataFactories(Function1<? super StorageManager, ? extends KotlinBuiltIns> createBuiltIns, FlexibleTypeDeserializer flexibleTypeDeserializer) {
        Intrinsics.checkNotNullParameter(createBuiltIns, "createBuiltIns");
        Intrinsics.checkNotNullParameter(flexibleTypeDeserializer, "flexibleTypeDeserializer");
        this.flexibleTypeDeserializer = flexibleTypeDeserializer;
        KlibModuleDescriptorFactoryImpl klibModuleDescriptorFactoryImpl = new KlibModuleDescriptorFactoryImpl(createBuiltIns);
        this.DefaultDescriptorFactory = klibModuleDescriptorFactoryImpl;
        KlibMetadataDeserializedPackageFragmentsFactoryImpl klibMetadataDeserializedPackageFragmentsFactoryImpl = new KlibMetadataDeserializedPackageFragmentsFactoryImpl();
        this.DefaultPackageFragmentsFactory = klibMetadataDeserializedPackageFragmentsFactoryImpl;
        KlibMetadataModuleDescriptorFactory klibMetadataModuleDescriptorFactoryCreateDefaultKonanDeserializedModuleDescriptorFactory = createDefaultKonanDeserializedModuleDescriptorFactory(klibModuleDescriptorFactoryImpl, klibMetadataDeserializedPackageFragmentsFactoryImpl);
        this.DefaultDeserializedDescriptorFactory = klibMetadataModuleDescriptorFactoryCreateDefaultKonanDeserializedModuleDescriptorFactory;
        this.DefaultResolvedDescriptorsFactory = createDefaultKonanResolvedModuleDescriptorsFactory(klibMetadataModuleDescriptorFactoryCreateDefaultKonanDeserializedModuleDescriptorFactory);
    }

    public final FlexibleTypeDeserializer getFlexibleTypeDeserializer() {
        return this.flexibleTypeDeserializer;
    }

    public final KlibModuleDescriptorFactory getDefaultDescriptorFactory() {
        return this.DefaultDescriptorFactory;
    }

    public final KlibMetadataDeserializedPackageFragmentsFactory getDefaultPackageFragmentsFactory() {
        return this.DefaultPackageFragmentsFactory;
    }

    public final KlibMetadataModuleDescriptorFactory getDefaultDeserializedDescriptorFactory() {
        return this.DefaultDeserializedDescriptorFactory;
    }

    public final KlibResolvedModuleDescriptorsFactory getDefaultResolvedDescriptorsFactory() {
        return this.DefaultResolvedDescriptorsFactory;
    }

    public final KlibMetadataModuleDescriptorFactory createDefaultKonanDeserializedModuleDescriptorFactory(KlibModuleDescriptorFactory descriptorFactory, KlibMetadataDeserializedPackageFragmentsFactory packageFragmentsFactory) {
        Intrinsics.checkNotNullParameter(descriptorFactory, "descriptorFactory");
        Intrinsics.checkNotNullParameter(packageFragmentsFactory, "packageFragmentsFactory");
        return new KlibMetadataModuleDescriptorFactoryImpl(descriptorFactory, packageFragmentsFactory, this.flexibleTypeDeserializer);
    }

    public final KlibResolvedModuleDescriptorsFactory createDefaultKonanResolvedModuleDescriptorsFactory(KlibMetadataModuleDescriptorFactory moduleDescriptorFactory) {
        Intrinsics.checkNotNullParameter(moduleDescriptorFactory, "moduleDescriptorFactory");
        return new KlibResolvedModuleDescriptorsFactoryImpl(moduleDescriptorFactory);
    }
}
