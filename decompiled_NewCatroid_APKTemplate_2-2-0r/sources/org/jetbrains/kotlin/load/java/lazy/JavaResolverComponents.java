package org.jetbrains.kotlin.load.java.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
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
import org.jetbrains.kotlin.load.java.sources.JavaSourceElementFactory;
import org.jetbrains.kotlin.load.java.typeEnhancement.SignatureEnhancement;
import org.jetbrains.kotlin.load.kotlin.DeserializedDescriptorResolver;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.resolve.jvm.SyntheticJavaPartsProvider;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.serialization.deserialization.ErrorReporter;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b3\u0018\u00002\u00020\u0001BÇ\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u001b\u0012\u0006\u0010\u001c\u001a\u00020\u001d\u0012\u0006\u0010\u001e\u001a\u00020\u001f\u0012\u0006\u0010 \u001a\u00020!\u0012\u0006\u0010\"\u001a\u00020#\u0012\u0006\u0010$\u001a\u00020%\u0012\u0006\u0010&\u001a\u00020'\u0012\u0006\u0010(\u001a\u00020)\u0012\u0006\u0010*\u001a\u00020+\u0012\u0006\u0010,\u001a\u00020-\u0012\u0006\u0010.\u001a\u00020/\u0012\b\b\u0002\u00100\u001a\u000201¢\u0006\u0002\u00102J\u0010\u0010c\u001a\u00020\u00002\b\b\u0002\u0010\u000e\u001a\u00020\u000fR\u0011\u0010\"\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0011\u0010&\u001a\u00020'¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0011\u0010.\u001a\u00020/¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\bA\u0010BR\u0011\u0010,\u001a\u00020-¢\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\bE\u0010FR\u0011\u0010*\u001a\u00020+¢\u0006\b\n\u0000\u001a\u0004\bG\u0010HR\u0011\u0010\u001c\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0011\u0010\u001e\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\bK\u0010LR\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\bM\u0010NR\u0011\u0010\u0018\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\bO\u0010PR\u0011\u0010 \u001a\u00020!¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010RR\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\bS\u0010TR\u0011\u0010(\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\bU\u0010VR\u0011\u0010$\u001a\u00020%¢\u0006\b\n\u0000\u001a\u0004\bW\u0010XR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\bY\u0010ZR\u0011\u0010\u0014\u001a\u00020\u0015¢\u0006\b\n\u0000\u001a\u0004\b[\u0010\\R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b]\u0010^R\u0011\u0010\u001a\u001a\u00020\u001b¢\u0006\b\n\u0000\u001a\u0004\b_\u0010`R\u0011\u00100\u001a\u000201¢\u0006\b\n\u0000\u001a\u0004\ba\u0010b¨\u0006d"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverComponents;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "finder", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "deserializedDescriptorResolver", "Lorg/jetbrains/kotlin/load/kotlin/DeserializedDescriptorResolver;", "signaturePropagator", "Lorg/jetbrains/kotlin/load/java/components/SignaturePropagator;", "errorReporter", "Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;", "javaResolverCache", "Lorg/jetbrains/kotlin/load/java/components/JavaResolverCache;", "javaPropertyInitializerEvaluator", "Lorg/jetbrains/kotlin/load/java/components/JavaPropertyInitializerEvaluator;", "samConversionResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "sourceElementFactory", "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElementFactory;", "moduleClassResolver", "Lorg/jetbrains/kotlin/load/java/lazy/ModuleClassResolver;", "packagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "supertypeLoopChecker", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "reflectionTypes", "Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "annotationTypeQualifierResolver", "Lorg/jetbrains/kotlin/load/java/AnnotationTypeQualifierResolver;", "signatureEnhancement", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancement;", "javaClassesTracker", "Lorg/jetbrains/kotlin/load/java/JavaClassesTracker;", "settings", "Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverSettings;", "kotlinTypeChecker", "Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "javaTypeEnhancementState", "Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "javaModuleResolver", "Lorg/jetbrains/kotlin/load/java/JavaModuleAnnotationsProvider;", "syntheticPartsProvider", "Lorg/jetbrains/kotlin/resolve/jvm/SyntheticJavaPartsProvider;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/load/java/JavaClassFinder;Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;Lorg/jetbrains/kotlin/load/kotlin/DeserializedDescriptorResolver;Lorg/jetbrains/kotlin/load/java/components/SignaturePropagator;Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;Lorg/jetbrains/kotlin/load/java/components/JavaResolverCache;Lorg/jetbrains/kotlin/load/java/components/JavaPropertyInitializerEvaluator;Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElementFactory;Lorg/jetbrains/kotlin/load/java/lazy/ModuleClassResolver;Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;Lorg/jetbrains/kotlin/incremental/components/LookupTracker;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/builtins/ReflectionTypes;Lorg/jetbrains/kotlin/load/java/AnnotationTypeQualifierResolver;Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancement;Lorg/jetbrains/kotlin/load/java/JavaClassesTracker;Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverSettings;Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;Lorg/jetbrains/kotlin/load/java/JavaModuleAnnotationsProvider;Lorg/jetbrains/kotlin/resolve/jvm/SyntheticJavaPartsProvider;)V", "getAnnotationTypeQualifierResolver", "()Lorg/jetbrains/kotlin/load/java/AnnotationTypeQualifierResolver;", "getDeserializedDescriptorResolver", "()Lorg/jetbrains/kotlin/load/kotlin/DeserializedDescriptorResolver;", "getErrorReporter", "()Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;", "getFinder", "()Lorg/jetbrains/kotlin/load/java/JavaClassFinder;", "getJavaClassesTracker", "()Lorg/jetbrains/kotlin/load/java/JavaClassesTracker;", "getJavaModuleResolver", "()Lorg/jetbrains/kotlin/load/java/JavaModuleAnnotationsProvider;", "getJavaPropertyInitializerEvaluator", "()Lorg/jetbrains/kotlin/load/java/components/JavaPropertyInitializerEvaluator;", "getJavaResolverCache", "()Lorg/jetbrains/kotlin/load/java/components/JavaResolverCache;", "getJavaTypeEnhancementState", "()Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "getKotlinClassFinder", "()Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "getKotlinTypeChecker", "()Lorg/jetbrains/kotlin/types/checker/NewKotlinTypeChecker;", "getLookupTracker", "()Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModuleClassResolver", "()Lorg/jetbrains/kotlin/load/java/lazy/ModuleClassResolver;", "getPackagePartProvider", "()Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "getReflectionTypes", "()Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "getSamConversionResolver", "()Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "getSettings", "()Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverSettings;", "getSignatureEnhancement", "()Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancement;", "getSignaturePropagator", "()Lorg/jetbrains/kotlin/load/java/components/SignaturePropagator;", "getSourceElementFactory", "()Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElementFactory;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "getSupertypeLoopChecker", "()Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "getSyntheticPartsProvider", "()Lorg/jetbrains/kotlin/resolve/jvm/SyntheticJavaPartsProvider;", SchemaSymbols.ATTVAL_REPLACE, "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaResolverComponents {
    private final AnnotationTypeQualifierResolver annotationTypeQualifierResolver;
    private final DeserializedDescriptorResolver deserializedDescriptorResolver;
    private final ErrorReporter errorReporter;
    private final JavaClassFinder finder;
    private final JavaClassesTracker javaClassesTracker;
    private final JavaModuleAnnotationsProvider javaModuleResolver;
    private final JavaPropertyInitializerEvaluator javaPropertyInitializerEvaluator;
    private final JavaResolverCache javaResolverCache;
    private final JavaTypeEnhancementState javaTypeEnhancementState;
    private final KotlinClassFinder kotlinClassFinder;
    private final NewKotlinTypeChecker kotlinTypeChecker;
    private final LookupTracker lookupTracker;
    private final ModuleDescriptor module;
    private final ModuleClassResolver moduleClassResolver;
    private final PackagePartProvider packagePartProvider;
    private final ReflectionTypes reflectionTypes;
    private final SamConversionResolver samConversionResolver;
    private final JavaResolverSettings settings;
    private final SignatureEnhancement signatureEnhancement;
    private final SignaturePropagator signaturePropagator;
    private final JavaSourceElementFactory sourceElementFactory;
    private final StorageManager storageManager;
    private final SupertypeLoopChecker supertypeLoopChecker;
    private final SyntheticJavaPartsProvider syntheticPartsProvider;

    public JavaResolverComponents(StorageManager storageManager, JavaClassFinder finder, KotlinClassFinder kotlinClassFinder, DeserializedDescriptorResolver deserializedDescriptorResolver, SignaturePropagator signaturePropagator, ErrorReporter errorReporter, JavaResolverCache javaResolverCache, JavaPropertyInitializerEvaluator javaPropertyInitializerEvaluator, SamConversionResolver samConversionResolver, JavaSourceElementFactory sourceElementFactory, ModuleClassResolver moduleClassResolver, PackagePartProvider packagePartProvider, SupertypeLoopChecker supertypeLoopChecker, LookupTracker lookupTracker, ModuleDescriptor module, ReflectionTypes reflectionTypes, AnnotationTypeQualifierResolver annotationTypeQualifierResolver, SignatureEnhancement signatureEnhancement, JavaClassesTracker javaClassesTracker, JavaResolverSettings settings, NewKotlinTypeChecker kotlinTypeChecker, JavaTypeEnhancementState javaTypeEnhancementState, JavaModuleAnnotationsProvider javaModuleResolver, SyntheticJavaPartsProvider syntheticPartsProvider) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(finder, "finder");
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        Intrinsics.checkNotNullParameter(deserializedDescriptorResolver, "deserializedDescriptorResolver");
        Intrinsics.checkNotNullParameter(signaturePropagator, "signaturePropagator");
        Intrinsics.checkNotNullParameter(errorReporter, "errorReporter");
        Intrinsics.checkNotNullParameter(javaResolverCache, "javaResolverCache");
        Intrinsics.checkNotNullParameter(javaPropertyInitializerEvaluator, "javaPropertyInitializerEvaluator");
        Intrinsics.checkNotNullParameter(samConversionResolver, "samConversionResolver");
        Intrinsics.checkNotNullParameter(sourceElementFactory, "sourceElementFactory");
        Intrinsics.checkNotNullParameter(moduleClassResolver, "moduleClassResolver");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(supertypeLoopChecker, "supertypeLoopChecker");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(reflectionTypes, "reflectionTypes");
        Intrinsics.checkNotNullParameter(annotationTypeQualifierResolver, "annotationTypeQualifierResolver");
        Intrinsics.checkNotNullParameter(signatureEnhancement, "signatureEnhancement");
        Intrinsics.checkNotNullParameter(javaClassesTracker, "javaClassesTracker");
        Intrinsics.checkNotNullParameter(settings, "settings");
        Intrinsics.checkNotNullParameter(kotlinTypeChecker, "kotlinTypeChecker");
        Intrinsics.checkNotNullParameter(javaTypeEnhancementState, "javaTypeEnhancementState");
        Intrinsics.checkNotNullParameter(javaModuleResolver, "javaModuleResolver");
        Intrinsics.checkNotNullParameter(syntheticPartsProvider, "syntheticPartsProvider");
        this.storageManager = storageManager;
        this.finder = finder;
        this.kotlinClassFinder = kotlinClassFinder;
        this.deserializedDescriptorResolver = deserializedDescriptorResolver;
        this.signaturePropagator = signaturePropagator;
        this.errorReporter = errorReporter;
        this.javaResolverCache = javaResolverCache;
        this.javaPropertyInitializerEvaluator = javaPropertyInitializerEvaluator;
        this.samConversionResolver = samConversionResolver;
        this.sourceElementFactory = sourceElementFactory;
        this.moduleClassResolver = moduleClassResolver;
        this.packagePartProvider = packagePartProvider;
        this.supertypeLoopChecker = supertypeLoopChecker;
        this.lookupTracker = lookupTracker;
        this.module = module;
        this.reflectionTypes = reflectionTypes;
        this.annotationTypeQualifierResolver = annotationTypeQualifierResolver;
        this.signatureEnhancement = signatureEnhancement;
        this.javaClassesTracker = javaClassesTracker;
        this.settings = settings;
        this.kotlinTypeChecker = kotlinTypeChecker;
        this.javaTypeEnhancementState = javaTypeEnhancementState;
        this.javaModuleResolver = javaModuleResolver;
        this.syntheticPartsProvider = syntheticPartsProvider;
    }

    public final StorageManager getStorageManager() {
        return this.storageManager;
    }

    public final JavaClassFinder getFinder() {
        return this.finder;
    }

    public final KotlinClassFinder getKotlinClassFinder() {
        return this.kotlinClassFinder;
    }

    public final DeserializedDescriptorResolver getDeserializedDescriptorResolver() {
        return this.deserializedDescriptorResolver;
    }

    public final SignaturePropagator getSignaturePropagator() {
        return this.signaturePropagator;
    }

    public final ErrorReporter getErrorReporter() {
        return this.errorReporter;
    }

    public final JavaResolverCache getJavaResolverCache() {
        return this.javaResolverCache;
    }

    public final JavaPropertyInitializerEvaluator getJavaPropertyInitializerEvaluator() {
        return this.javaPropertyInitializerEvaluator;
    }

    public final SamConversionResolver getSamConversionResolver() {
        return this.samConversionResolver;
    }

    public final JavaSourceElementFactory getSourceElementFactory() {
        return this.sourceElementFactory;
    }

    public final ModuleClassResolver getModuleClassResolver() {
        return this.moduleClassResolver;
    }

    public final PackagePartProvider getPackagePartProvider() {
        return this.packagePartProvider;
    }

    public final SupertypeLoopChecker getSupertypeLoopChecker() {
        return this.supertypeLoopChecker;
    }

    public final LookupTracker getLookupTracker() {
        return this.lookupTracker;
    }

    public final ModuleDescriptor getModule() {
        return this.module;
    }

    public final ReflectionTypes getReflectionTypes() {
        return this.reflectionTypes;
    }

    public final AnnotationTypeQualifierResolver getAnnotationTypeQualifierResolver() {
        return this.annotationTypeQualifierResolver;
    }

    public final SignatureEnhancement getSignatureEnhancement() {
        return this.signatureEnhancement;
    }

    public final JavaClassesTracker getJavaClassesTracker() {
        return this.javaClassesTracker;
    }

    public final JavaResolverSettings getSettings() {
        return this.settings;
    }

    public final NewKotlinTypeChecker getKotlinTypeChecker() {
        return this.kotlinTypeChecker;
    }

    public final JavaTypeEnhancementState getJavaTypeEnhancementState() {
        return this.javaTypeEnhancementState;
    }

    public final JavaModuleAnnotationsProvider getJavaModuleResolver() {
        return this.javaModuleResolver;
    }

    public /* synthetic */ JavaResolverComponents(StorageManager storageManager, JavaClassFinder javaClassFinder, KotlinClassFinder kotlinClassFinder, DeserializedDescriptorResolver deserializedDescriptorResolver, SignaturePropagator signaturePropagator, ErrorReporter errorReporter, JavaResolverCache javaResolverCache, JavaPropertyInitializerEvaluator javaPropertyInitializerEvaluator, SamConversionResolver samConversionResolver, JavaSourceElementFactory javaSourceElementFactory, ModuleClassResolver moduleClassResolver, PackagePartProvider packagePartProvider, SupertypeLoopChecker supertypeLoopChecker, LookupTracker lookupTracker, ModuleDescriptor moduleDescriptor, ReflectionTypes reflectionTypes, AnnotationTypeQualifierResolver annotationTypeQualifierResolver, SignatureEnhancement signatureEnhancement, JavaClassesTracker javaClassesTracker, JavaResolverSettings javaResolverSettings, NewKotlinTypeChecker newKotlinTypeChecker, JavaTypeEnhancementState javaTypeEnhancementState, JavaModuleAnnotationsProvider javaModuleAnnotationsProvider, SyntheticJavaPartsProvider syntheticJavaPartsProvider, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(storageManager, javaClassFinder, kotlinClassFinder, deserializedDescriptorResolver, signaturePropagator, errorReporter, javaResolverCache, javaPropertyInitializerEvaluator, samConversionResolver, javaSourceElementFactory, moduleClassResolver, packagePartProvider, supertypeLoopChecker, lookupTracker, moduleDescriptor, reflectionTypes, annotationTypeQualifierResolver, signatureEnhancement, javaClassesTracker, javaResolverSettings, newKotlinTypeChecker, javaTypeEnhancementState, javaModuleAnnotationsProvider, (i & 8388608) != 0 ? SyntheticJavaPartsProvider.INSTANCE.getEMPTY() : syntheticJavaPartsProvider);
    }

    public final SyntheticJavaPartsProvider getSyntheticPartsProvider() {
        return this.syntheticPartsProvider;
    }

    public static /* synthetic */ JavaResolverComponents replace$default(JavaResolverComponents javaResolverComponents, JavaResolverCache javaResolverCache, int i, Object obj) {
        if ((i & 1) != 0) {
            javaResolverCache = javaResolverComponents.javaResolverCache;
        }
        return javaResolverComponents.replace(javaResolverCache);
    }

    public final JavaResolverComponents replace(JavaResolverCache javaResolverCache) {
        Intrinsics.checkNotNullParameter(javaResolverCache, "javaResolverCache");
        return new JavaResolverComponents(this.storageManager, this.finder, this.kotlinClassFinder, this.deserializedDescriptorResolver, this.signaturePropagator, this.errorReporter, javaResolverCache, this.javaPropertyInitializerEvaluator, this.samConversionResolver, this.sourceElementFactory, this.moduleClassResolver, this.packagePartProvider, this.supertypeLoopChecker, this.lookupTracker, this.module, this.reflectionTypes, this.annotationTypeQualifierResolver, this.signatureEnhancement, this.javaClassesTracker, this.settings, this.kotlinTypeChecker, this.javaTypeEnhancementState, this.javaModuleResolver, null, 8388608, null);
    }
}
