package org.jetbrains.kotlin.load.kotlin;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.load.java.AnnotationTypeQualifierResolver;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.load.java.JavaClassesTracker;
import org.jetbrains.kotlin.load.java.JavaModuleAnnotationsProvider;
import org.jetbrains.kotlin.load.java.JavaTypeEnhancementState;
import org.jetbrains.kotlin.load.java.components.JavaPropertyInitializerEvaluator;
import org.jetbrains.kotlin.load.java.components.JavaResolverCache;
import org.jetbrains.kotlin.load.java.components.SignaturePropagator;
import org.jetbrains.kotlin.load.java.lazy.JavaResolverComponents;
import org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaPackageFragmentProvider;
import org.jetbrains.kotlin.load.java.lazy.ModuleClassResolver;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElementFactory;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.typeEnhancement.JavaTypeEnhancement;
import org.jetbrains.kotlin.load.java.typeEnhancement.SignatureEnhancement;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImpl;
import org.jetbrains.kotlin.serialization.deserialization.ContractDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.ErrorReporter;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.DefaultTypeAttributeTranslator;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;
import org.jetbrains.kotlin.types.extensions.TypeAttributeTranslators;

/* JADX INFO: compiled from: DeserializationComponentsForJava.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011\u001aX\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001a¨\u0006\u001b"}, d2 = {"makeDeserializationComponentsForJava", "Lorg/jetbrains/kotlin/load/kotlin/DeserializationComponentsForJava;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "notFoundClasses", "Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", "lazyJavaPackageFragmentProvider", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaPackageFragmentProvider;", "reflectKotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "deserializedDescriptorResolver", "Lorg/jetbrains/kotlin/load/kotlin/DeserializedDescriptorResolver;", "errorReporter", "Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "makeLazyJavaPackageFragmentProvider", "javaClassFinder", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder;", "javaSourceElementFactory", "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElementFactory;", "singleModuleClassResolver", "Lorg/jetbrains/kotlin/load/java/lazy/ModuleClassResolver;", "packagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "descriptors.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeserializationComponentsForJavaKt {
    public static final LazyJavaPackageFragmentProvider makeLazyJavaPackageFragmentProvider(JavaClassFinder javaClassFinder, ModuleDescriptor module, StorageManager storageManager, NotFoundClasses notFoundClasses, KotlinClassFinder reflectKotlinClassFinder, DeserializedDescriptorResolver deserializedDescriptorResolver, ErrorReporter errorReporter, JavaSourceElementFactory javaSourceElementFactory, ModuleClassResolver singleModuleClassResolver, PackagePartProvider packagePartProvider) {
        Intrinsics.checkNotNullParameter(javaClassFinder, "javaClassFinder");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(reflectKotlinClassFinder, "reflectKotlinClassFinder");
        Intrinsics.checkNotNullParameter(deserializedDescriptorResolver, "deserializedDescriptorResolver");
        Intrinsics.checkNotNullParameter(errorReporter, "errorReporter");
        Intrinsics.checkNotNullParameter(javaSourceElementFactory, "javaSourceElementFactory");
        Intrinsics.checkNotNullParameter(singleModuleClassResolver, "singleModuleClassResolver");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        SignaturePropagator DO_NOTHING = SignaturePropagator.DO_NOTHING;
        Intrinsics.checkNotNullExpressionValue(DO_NOTHING, "DO_NOTHING");
        JavaResolverCache EMPTY = JavaResolverCache.EMPTY;
        Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
        return new LazyJavaPackageFragmentProvider(new JavaResolverComponents(storageManager, javaClassFinder, reflectKotlinClassFinder, deserializedDescriptorResolver, DO_NOTHING, errorReporter, EMPTY, JavaPropertyInitializerEvaluator.DoNothing.INSTANCE, new SamConversionResolverImpl(storageManager, CollectionsKt.emptyList()), javaSourceElementFactory, singleModuleClassResolver, packagePartProvider, SupertypeLoopChecker.EMPTY.INSTANCE, LookupTracker.DO_NOTHING.INSTANCE, module, new ReflectionTypes(module, notFoundClasses), new AnnotationTypeQualifierResolver(JavaTypeEnhancementState.INSTANCE.getDEFAULT()), new SignatureEnhancement(new JavaTypeEnhancement(JavaResolverSettings.Default.INSTANCE)), JavaClassesTracker.Default.INSTANCE, JavaResolverSettings.Default.INSTANCE, NewKotlinTypeChecker.INSTANCE.getDefault(), JavaTypeEnhancementState.INSTANCE.getDEFAULT(), new JavaModuleAnnotationsProvider() { // from class: org.jetbrains.kotlin.load.kotlin.DeserializationComponentsForJavaKt$makeLazyJavaPackageFragmentProvider$javaResolverComponents$1
            @Override // org.jetbrains.kotlin.load.java.JavaModuleAnnotationsProvider
            public List<JavaAnnotation> getAnnotationsForModuleOwnerOfClass(ClassId classId) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                return null;
            }
        }, null, 8388608, null));
    }

    public static final DeserializationComponentsForJava makeDeserializationComponentsForJava(ModuleDescriptor module, StorageManager storageManager, NotFoundClasses notFoundClasses, LazyJavaPackageFragmentProvider lazyJavaPackageFragmentProvider, KotlinClassFinder reflectKotlinClassFinder, DeserializedDescriptorResolver deserializedDescriptorResolver, ErrorReporter errorReporter, JvmMetadataVersion jvmMetadataVersion) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(lazyJavaPackageFragmentProvider, "lazyJavaPackageFragmentProvider");
        Intrinsics.checkNotNullParameter(reflectKotlinClassFinder, "reflectKotlinClassFinder");
        Intrinsics.checkNotNullParameter(deserializedDescriptorResolver, "deserializedDescriptorResolver");
        Intrinsics.checkNotNullParameter(errorReporter, "errorReporter");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        return new DeserializationComponentsForJava(storageManager, module, DeserializationConfiguration.Default.INSTANCE, new JavaClassDataFinder(reflectKotlinClassFinder, deserializedDescriptorResolver), BinaryClassAnnotationAndConstantLoaderImplKt.createBinaryClassAnnotationAndConstantLoader(module, notFoundClasses, storageManager, reflectKotlinClassFinder, jvmMetadataVersion), lazyJavaPackageFragmentProvider, notFoundClasses, errorReporter, LookupTracker.DO_NOTHING.INSTANCE, ContractDeserializer.INSTANCE.getDEFAULT(), NewKotlinTypeChecker.INSTANCE.getDefault(), new TypeAttributeTranslators(CollectionsKt.listOf(DefaultTypeAttributeTranslator.INSTANCE)));
    }
}
