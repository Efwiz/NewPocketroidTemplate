package org.jetbrains.kotlin.builtins.jvm;

import java.io.InputStream;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.BuiltInFictitiousFunctionClassFactory;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.serialization.deserialization.AbstractDeserializedPackageFragmentProvider;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationAndConstantLoaderImpl;
import org.jetbrains.kotlin.serialization.deserialization.ContractDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.DeserializedClassDataFinder;
import org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragment;
import org.jetbrains.kotlin.serialization.deserialization.ErrorReporter;
import org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.JvmEnumEntriesDeserializationSupport;
import org.jetbrains.kotlin.serialization.deserialization.LocalClassifierTypeSettings;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInsPackageFragmentImpl;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;

/* JADX INFO: compiled from: JvmBuiltInsPackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/builtins/jvm/JvmBuiltInsPackageFragmentProvider;", "Lorg/jetbrains/kotlin/serialization/deserialization/AbstractDeserializedPackageFragmentProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "finder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "notFoundClasses", "Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", "additionalClassPartsProvider", "Lorg/jetbrains/kotlin/descriptors/deserialization/AdditionalClassPartsProvider;", "platformDependentDeclarationFilter", "Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter;", "deserializationConfiguration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "kotlinTypeChecker", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "samConversionResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;Lorg/jetbrains/kotlin/descriptors/deserialization/AdditionalClassPartsProvider;Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter;Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;)V", "findPackage", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializedPackageFragment;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "Companion", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmBuiltInsPackageFragmentProvider extends AbstractDeserializedPackageFragmentProvider {
    public static final String DOT_BUILTINS_METADATA_FILE_EXTENSION = ".kotlin_builtins";

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmBuiltInsPackageFragmentProvider(StorageManager storageManager, KotlinClassFinder finder, ModuleDescriptor moduleDescriptor, NotFoundClasses notFoundClasses, AdditionalClassPartsProvider additionalClassPartsProvider, PlatformDependentDeclarationFilter platformDependentDeclarationFilter, DeserializationConfiguration deserializationConfiguration, NewKotlinTypeChecker kotlinTypeChecker, SamConversionResolver samConversionResolver) {
        super(storageManager, finder, moduleDescriptor);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(finder, "finder");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(additionalClassPartsProvider, "additionalClassPartsProvider");
        Intrinsics.checkNotNullParameter(platformDependentDeclarationFilter, "platformDependentDeclarationFilter");
        Intrinsics.checkNotNullParameter(deserializationConfiguration, "deserializationConfiguration");
        Intrinsics.checkNotNullParameter(kotlinTypeChecker, "kotlinTypeChecker");
        Intrinsics.checkNotNullParameter(samConversionResolver, "samConversionResolver");
        JvmBuiltInsPackageFragmentProvider jvmBuiltInsPackageFragmentProvider = this;
        DeserializedClassDataFinder deserializedClassDataFinder = new DeserializedClassDataFinder(jvmBuiltInsPackageFragmentProvider);
        AnnotationAndConstantLoaderImpl annotationAndConstantLoaderImpl = new AnnotationAndConstantLoaderImpl(moduleDescriptor, notFoundClasses, BuiltInSerializerProtocol.INSTANCE);
        LocalClassifierTypeSettings.Default r8 = LocalClassifierTypeSettings.Default.INSTANCE;
        ErrorReporter DO_NOTHING = ErrorReporter.DO_NOTHING;
        Intrinsics.checkNotNullExpressionValue(DO_NOTHING, "DO_NOTHING");
        setComponents(new DeserializationComponents(storageManager, moduleDescriptor, deserializationConfiguration, deserializedClassDataFinder, annotationAndConstantLoaderImpl, jvmBuiltInsPackageFragmentProvider, r8, DO_NOTHING, LookupTracker.DO_NOTHING.INSTANCE, FlexibleTypeDeserializer.ThrowException.INSTANCE, CollectionsKt.listOf((Object[]) new ClassDescriptorFactory[]{new BuiltInFictitiousFunctionClassFactory(storageManager, moduleDescriptor), new JvmBuiltInClassDescriptorFactory(storageManager, moduleDescriptor, null, 4, null)}), notFoundClasses, ContractDeserializer.INSTANCE.getDEFAULT(), additionalClassPartsProvider, platformDependentDeclarationFilter, BuiltInSerializerProtocol.INSTANCE.getExtensionRegistry(), kotlinTypeChecker, samConversionResolver, null, JvmEnumEntriesDeserializationSupport.INSTANCE, 262144, null));
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AbstractDeserializedPackageFragmentProvider
    protected DeserializedPackageFragment findPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        InputStream inputStreamFindBuiltInsData = getFinder().findBuiltInsData(fqName);
        return inputStreamFindBuiltInsData != null ? BuiltInsPackageFragmentImpl.INSTANCE.create(fqName, getStorageManager(), getModuleDescriptor(), inputStreamFindBuiltInsData, false) : null;
    }
}
