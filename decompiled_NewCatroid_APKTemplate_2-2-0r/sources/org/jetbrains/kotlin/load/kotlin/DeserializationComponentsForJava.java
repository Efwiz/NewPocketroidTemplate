package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltIns;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsPackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized;
import org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter;
import org.jetbrains.kotlin.descriptors.impl.CompositePackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.load.java.components.JavaResolverCache;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaPackageFragmentProvider;
import org.jetbrains.kotlin.load.java.lazy.SingleModuleClassResolver;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElementFactory;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.JavaDescriptorResolver;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImpl;
import org.jetbrains.kotlin.serialization.deserialization.ContractDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.ErrorReporter;
import org.jetbrains.kotlin.serialization.deserialization.JvmEnumEntriesDeserializationSupport;
import org.jetbrains.kotlin.serialization.deserialization.LocalClassifierTypeSettings;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;
import org.jetbrains.kotlin.types.extensions.TypeAttributeTranslators;

/* JADX INFO: compiled from: DeserializationComponentsForJava.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fBe\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019¢\u0006\u0002\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u001c¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/DeserializationComponentsForJava;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "classDataFinder", "Lorg/jetbrains/kotlin/load/kotlin/JavaClassDataFinder;", "annotationAndConstantLoader", "Lorg/jetbrains/kotlin/load/kotlin/BinaryClassAnnotationAndConstantLoaderImpl;", "packageFragmentProvider", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaPackageFragmentProvider;", "notFoundClasses", "Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", "errorReporter", "Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "contractDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer;", "kotlinTypeChecker", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "typeAttributeTranslators", "Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;Lorg/jetbrains/kotlin/load/kotlin/JavaClassDataFinder;Lorg/jetbrains/kotlin/load/kotlin/BinaryClassAnnotationAndConstantLoaderImpl;Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaPackageFragmentProvider;Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;Lorg/jetbrains/kotlin/incremental/components/LookupTracker;Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer;Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;)V", "components", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "getComponents", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "Companion", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeserializationComponentsForJava {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final DeserializationComponents components;

    public DeserializationComponentsForJava(StorageManager storageManager, ModuleDescriptor moduleDescriptor, DeserializationConfiguration configuration, JavaClassDataFinder classDataFinder, BinaryClassAnnotationAndConstantLoaderImpl annotationAndConstantLoader, LazyJavaPackageFragmentProvider packageFragmentProvider, NotFoundClasses notFoundClasses, ErrorReporter errorReporter, LookupTracker lookupTracker, ContractDeserializer contractDeserializer, NewKotlinTypeChecker kotlinTypeChecker, TypeAttributeTranslators typeAttributeTranslators) {
        PlatformDependentDeclarationFilter customizer;
        AdditionalClassPartsProvider customizer2;
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(classDataFinder, "classDataFinder");
        Intrinsics.checkNotNullParameter(annotationAndConstantLoader, "annotationAndConstantLoader");
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "packageFragmentProvider");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(errorReporter, "errorReporter");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(contractDeserializer, "contractDeserializer");
        Intrinsics.checkNotNullParameter(kotlinTypeChecker, "kotlinTypeChecker");
        Intrinsics.checkNotNullParameter(typeAttributeTranslators, "typeAttributeTranslators");
        KotlinBuiltIns builtIns = moduleDescriptor.getBuiltIns();
        JvmBuiltIns jvmBuiltIns = builtIns instanceof JvmBuiltIns ? (JvmBuiltIns) builtIns : null;
        this.components = new DeserializationComponents(storageManager, moduleDescriptor, configuration, classDataFinder, annotationAndConstantLoader, packageFragmentProvider, LocalClassifierTypeSettings.Default.INSTANCE, errorReporter, lookupTracker, JavaFlexibleTypeDeserializer.INSTANCE, CollectionsKt.emptyList(), notFoundClasses, contractDeserializer, (jvmBuiltIns == null || (customizer2 = jvmBuiltIns.getCustomizer()) == null) ? AdditionalClassPartsProvider.None.INSTANCE : customizer2, (jvmBuiltIns == null || (customizer = jvmBuiltIns.getCustomizer()) == null) ? PlatformDependentDeclarationFilter.NoPlatformDependent.INSTANCE : customizer, JvmProtoBufUtil.INSTANCE.getEXTENSION_REGISTRY(), kotlinTypeChecker, new SamConversionResolverImpl(storageManager, CollectionsKt.emptyList()), typeAttributeTranslators.getTranslators(), JvmEnumEntriesDeserializationSupport.INSTANCE);
    }

    public final DeserializationComponents getComponents() {
        return this.components;
    }

    /* JADX INFO: compiled from: DeserializationComponentsForJava.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u0010B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/DeserializationComponentsForJava$Companion;", "", "()V", "createModuleData", "Lorg/jetbrains/kotlin/load/kotlin/DeserializationComponentsForJava$Companion$ModuleData;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "jvmBuiltInsKotlinClassFinder", "javaClassFinder", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder;", "moduleName", "", "errorReporter", "Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;", "javaSourceElementFactory", "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElementFactory;", "ModuleData", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: compiled from: DeserializationComponentsForJava.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/DeserializationComponentsForJava$Companion$ModuleData;", "", "deserializationComponentsForJava", "Lorg/jetbrains/kotlin/load/kotlin/DeserializationComponentsForJava;", "deserializedDescriptorResolver", "Lorg/jetbrains/kotlin/load/kotlin/DeserializedDescriptorResolver;", "(Lorg/jetbrains/kotlin/load/kotlin/DeserializationComponentsForJava;Lorg/jetbrains/kotlin/load/kotlin/DeserializedDescriptorResolver;)V", "getDeserializationComponentsForJava", "()Lorg/jetbrains/kotlin/load/kotlin/DeserializationComponentsForJava;", "getDeserializedDescriptorResolver", "()Lorg/jetbrains/kotlin/load/kotlin/DeserializedDescriptorResolver;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ModuleData {
            private final DeserializationComponentsForJava deserializationComponentsForJava;
            private final DeserializedDescriptorResolver deserializedDescriptorResolver;

            public ModuleData(DeserializationComponentsForJava deserializationComponentsForJava, DeserializedDescriptorResolver deserializedDescriptorResolver) {
                Intrinsics.checkNotNullParameter(deserializationComponentsForJava, "deserializationComponentsForJava");
                Intrinsics.checkNotNullParameter(deserializedDescriptorResolver, "deserializedDescriptorResolver");
                this.deserializationComponentsForJava = deserializationComponentsForJava;
                this.deserializedDescriptorResolver = deserializedDescriptorResolver;
            }

            public final DeserializationComponentsForJava getDeserializationComponentsForJava() {
                return this.deserializationComponentsForJava;
            }

            public final DeserializedDescriptorResolver getDeserializedDescriptorResolver() {
                return this.deserializedDescriptorResolver;
            }
        }

        public final ModuleData createModuleData(KotlinClassFinder kotlinClassFinder, KotlinClassFinder jvmBuiltInsKotlinClassFinder, JavaClassFinder javaClassFinder, String moduleName, ErrorReporter errorReporter, JavaSourceElementFactory javaSourceElementFactory) {
            Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
            Intrinsics.checkNotNullParameter(jvmBuiltInsKotlinClassFinder, "jvmBuiltInsKotlinClassFinder");
            Intrinsics.checkNotNullParameter(javaClassFinder, "javaClassFinder");
            Intrinsics.checkNotNullParameter(moduleName, "moduleName");
            Intrinsics.checkNotNullParameter(errorReporter, "errorReporter");
            Intrinsics.checkNotNullParameter(javaSourceElementFactory, "javaSourceElementFactory");
            LockBasedStorageManager lockBasedStorageManager = new LockBasedStorageManager("DeserializationComponentsForJava.ModuleData");
            JvmBuiltIns jvmBuiltIns = new JvmBuiltIns(lockBasedStorageManager, JvmBuiltIns.Kind.FROM_DEPENDENCIES);
            Name nameSpecial = Name.special("<" + moduleName + '>');
            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
            ModuleDescriptorImpl moduleDescriptorImpl = new ModuleDescriptorImpl(nameSpecial, lockBasedStorageManager, jvmBuiltIns, null, null, null, 56, null);
            jvmBuiltIns.setBuiltInsModule(moduleDescriptorImpl);
            ModuleDescriptorImpl moduleDescriptorImpl2 = moduleDescriptorImpl;
            jvmBuiltIns.initialize(moduleDescriptorImpl2, true);
            DeserializedDescriptorResolver deserializedDescriptorResolver = new DeserializedDescriptorResolver();
            SingleModuleClassResolver singleModuleClassResolver = new SingleModuleClassResolver();
            NotFoundClasses notFoundClasses = new NotFoundClasses(lockBasedStorageManager, moduleDescriptorImpl2);
            LazyJavaPackageFragmentProvider lazyJavaPackageFragmentProviderMakeLazyJavaPackageFragmentProvider = DeserializationComponentsForJavaKt.makeLazyJavaPackageFragmentProvider(javaClassFinder, moduleDescriptorImpl2, lockBasedStorageManager, notFoundClasses, kotlinClassFinder, deserializedDescriptorResolver, errorReporter, javaSourceElementFactory, singleModuleClassResolver, (512 & 512) != 0 ? PackagePartProvider.Empty.INSTANCE : null);
            DeserializationComponentsForJava deserializationComponentsForJavaMakeDeserializationComponentsForJava = DeserializationComponentsForJavaKt.makeDeserializationComponentsForJava(moduleDescriptorImpl2, lockBasedStorageManager, notFoundClasses, lazyJavaPackageFragmentProviderMakeLazyJavaPackageFragmentProvider, kotlinClassFinder, deserializedDescriptorResolver, errorReporter, JvmMetadataVersion.INSTANCE);
            deserializedDescriptorResolver.setComponents(deserializationComponentsForJavaMakeDeserializationComponentsForJava);
            JavaResolverCache EMPTY = JavaResolverCache.EMPTY;
            Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
            JavaDescriptorResolver javaDescriptorResolver = new JavaDescriptorResolver(lazyJavaPackageFragmentProviderMakeLazyJavaPackageFragmentProvider, EMPTY);
            singleModuleClassResolver.setResolver(javaDescriptorResolver);
            JvmBuiltInsPackageFragmentProvider jvmBuiltInsPackageFragmentProvider = new JvmBuiltInsPackageFragmentProvider(lockBasedStorageManager, jvmBuiltInsKotlinClassFinder, moduleDescriptorImpl2, notFoundClasses, jvmBuiltIns.getCustomizer(), jvmBuiltIns.getCustomizer(), DeserializationConfiguration.Default.INSTANCE, NewKotlinTypeChecker.INSTANCE.getDefault(), new SamConversionResolverImpl(lockBasedStorageManager, CollectionsKt.emptyList()));
            moduleDescriptorImpl.setDependencies(moduleDescriptorImpl);
            moduleDescriptorImpl.initialize(new CompositePackageFragmentProvider(CollectionsKt.listOf((Object[]) new PackageFragmentProviderOptimized[]{javaDescriptorResolver.getPackageFragmentProvider(), jvmBuiltInsPackageFragmentProvider}), "CompositeProvider@RuntimeModuleData for " + moduleDescriptorImpl));
            return new ModuleData(deserializationComponentsForJavaMakeDeserializationComponentsForJava, deserializedDescriptorResolver);
        }
    }
}
