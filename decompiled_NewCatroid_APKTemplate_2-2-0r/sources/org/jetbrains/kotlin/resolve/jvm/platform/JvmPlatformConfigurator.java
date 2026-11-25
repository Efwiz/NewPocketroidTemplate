package org.jetbrains.kotlin.resolve.jvm.platform;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMapper;
import org.jetbrains.kotlin.container.ContainerKt;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.PlatformExtensionsClashResolver;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.load.java.sam.JvmSamConversionOracle;
import org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker;
import org.jetbrains.kotlin.resolve.PlatformConfiguratorBase;
import org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.LateinitIntrinsicApplicabilityChecker;
import org.jetbrains.kotlin.resolve.checkers.BigFunctionTypeAvailabilityChecker;
import org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker;
import org.jetbrains.kotlin.resolve.jvm.JvmAdditionalClassPartsProvider;
import org.jetbrains.kotlin.resolve.jvm.JvmDeclarationReturnTypeSanitizer;
import org.jetbrains.kotlin.resolve.jvm.JvmDelegationFilter;
import org.jetbrains.kotlin.resolve.jvm.JvmOverloadFilter;
import org.jetbrains.kotlin.resolve.jvm.JvmOverridesBackwardCompatibilityHelper;
import org.jetbrains.kotlin.resolve.jvm.JvmPlatformAnnotationFeaturesSupport;
import org.jetbrains.kotlin.resolve.jvm.JvmPlatformOverloadsSpecificityComparator;
import org.jetbrains.kotlin.resolve.jvm.JvmTypeSpecificityComparatorDelegate;
import org.jetbrains.kotlin.resolve.jvm.RuntimeAssertionsOnExtensionReceiverCallChecker;
import org.jetbrains.kotlin.resolve.jvm.RuntimeAssertionsTypeChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.ApiVersionIsAtLeastArgumentsChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.BadInheritedJavaSignaturesChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.ClassInheritsJavaSealedClassChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.DefaultCheckerInTailrec;
import org.jetbrains.kotlin.resolve.jvm.checkers.EnumDeclaringClassDeprecationChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.ExplicitMetadataChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.ExternalFunChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.FileClassAnnotationsChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.FunctionDelegateMemberNameClashChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.InconsistentOperatorFromJavaCallChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.InlinePlatformCompatibilityChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.InterfaceDefaultMethodCallChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JavaAnnotationCallChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JavaClassOnCompanionChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JavaNullabilityChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JavaOverrideWithWrongNullabilityOverrideChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JavaTypeAccessibilityChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmAnnotationsTargetNonExistentAccessorChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmArrayVariableInLoopAssignmentChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmDefaultChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmFieldApplicabilityChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmInlineApplicabilityChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmModuleAccessibilityChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmMultifileClassStateChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmNameAnnotationChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmPropertyVsFieldAmbiguityCallChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmRecordApplicabilityChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmReflectionAPICallChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmSerializableLambdaAnnotationChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmSimpleNameBacktickChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmStaticChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmSyntheticApplicabilityChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmSyntheticAssignmentChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.LocalFunInlineChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.MissingBuiltInDeclarationChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.OverloadsAnnotationChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.PolymorphicSignatureCallChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.ProtectedInSuperClassCompanionCallChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.ProtectedSyntheticExtensionCallChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.RepeatableAnnotationChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.SamInterfaceConstructorReferenceCallChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.StrictfpApplicabilityChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.SuperCallWithDefaultArgumentsChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.SuspendInFunInterfaceChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.SuspensionPointInsideMutexLockChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.SynchronizedAnnotationChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.SynchronizedAnnotationOnLambdaChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.TypeParameterBoundIsNotArrayChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.UnsupportedSyntheticCallableReferenceChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.UpperBoundViolatedInTypealiasConstructorChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.WarningAwareUpperBoundChecker;
import org.jetbrains.kotlin.resolve.jvm.multiplatform.JavaActualAnnotationArgumentExtractor;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImpl;
import org.jetbrains.kotlin.synthetic.JavaSyntheticScopes;
import org.jetbrains.kotlin.types.expressions.FunctionWithBigAritySupport;
import org.jetbrains.kotlin.types.expressions.GenericArrayClassLiteralSupport;

/* JADX INFO: compiled from: JvmPlatformConfigurator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/platform/JvmPlatformConfigurator;", "Lorg/jetbrains/kotlin/resolve/PlatformConfiguratorBase;", "()V", "configureModuleComponents", "", "container", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "configureModuleDependentCheckers", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPlatformConfigurator extends PlatformConfiguratorBase {
    public static final JvmPlatformConfigurator INSTANCE = new JvmPlatformConfigurator();

    private JvmPlatformConfigurator() {
        super(null, CollectionsKt.listOf((Object[]) new DeclarationChecker[]{new JvmNameAnnotationChecker(), new SynchronizedAnnotationChecker(), new LocalFunInlineChecker(), new ExternalFunChecker(), new OverloadsAnnotationChecker(), new JvmFieldApplicabilityChecker(), new TypeParameterBoundIsNotArrayChecker(), new JvmSyntheticApplicabilityChecker(), new JvmInlineApplicabilityChecker(), new StrictfpApplicabilityChecker(), new JvmAnnotationsTargetNonExistentAccessorChecker(), new SuspendInFunInterfaceChecker(), BadInheritedJavaSignaturesChecker.INSTANCE, JvmMultifileClassStateChecker.INSTANCE, DefaultCheckerInTailrec.INSTANCE, FunctionDelegateMemberNameClashChecker.INSTANCE, ClassInheritsJavaSealedClassChecker.INSTANCE, JavaOverrideWithWrongNullabilityOverrideChecker.INSTANCE}), CollectionsKt.listOf((Object[]) new CallChecker[]{MissingBuiltInDeclarationChecker.INSTANCE, new JavaAnnotationCallChecker(), new SuspensionPointInsideMutexLockChecker(), new JavaClassOnCompanionChecker(), new ProtectedInSuperClassCompanionCallChecker(), new UnsupportedSyntheticCallableReferenceChecker(), new SuperCallWithDefaultArgumentsChecker(), ProtectedSyntheticExtensionCallChecker.INSTANCE, RuntimeAssertionsOnExtensionReceiverCallChecker.INSTANCE, ApiVersionIsAtLeastArgumentsChecker.INSTANCE, InconsistentOperatorFromJavaCallChecker.INSTANCE, PolymorphicSignatureCallChecker.INSTANCE, SamInterfaceConstructorReferenceCallChecker.INSTANCE, EnumDeclaringClassDeprecationChecker.INSTANCE, UpperBoundViolatedInTypealiasConstructorChecker.INSTANCE, new LateinitIntrinsicApplicabilityChecker(false), JvmPropertyVsFieldAmbiguityCallChecker.INSTANCE}), CollectionsKt.listOf(JvmSyntheticAssignmentChecker.INSTANCE), CollectionsKt.listOf((Object[]) new AdditionalTypeChecker[]{RuntimeAssertionsTypeChecker.INSTANCE, JavaGenericVarianceViolationTypeChecker.INSTANCE, new JavaTypeAccessibilityChecker(), JvmArrayVariableInLoopAssignmentChecker.INSTANCE}), CollectionsKt.listOf((Object[]) new ClassifierUsageChecker[]{BigFunctionTypeAvailabilityChecker.INSTANCE, MissingBuiltInDeclarationChecker.ClassifierUsage.INSTANCE}), CollectionsKt.listOf((Object[]) new AdditionalAnnotationChecker[]{FileClassAnnotationsChecker.INSTANCE, ExplicitMetadataChecker.INSTANCE, SynchronizedAnnotationOnLambdaChecker.INSTANCE}), CollectionsKt.listOf(new PlatformExtensionsClashResolver.FallbackToDefault(SamConversionResolver.Empty.INSTANCE, SamConversionResolver.class)), JvmSimpleNameBacktickChecker.INSTANCE, JvmOverloadFilter.INSTANCE, JavaToKotlinClassMapper.INSTANCE, null, JvmDelegationFilter.INSTANCE, JvmOverridesBackwardCompatibilityHelper.INSTANCE, JvmDeclarationReturnTypeSanitizer.INSTANCE, 2049, null);
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformConfiguratorBase, org.jetbrains.kotlin.resolve.PlatformConfigurator
    public void configureModuleDependentCheckers(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        super.configureModuleDependentCheckers(container);
        ContainerKt.registerSingleton(container, ExpectedActualDeclarationChecker.class);
        ContainerKt.registerSingleton(container, RepeatableAnnotationChecker.class);
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformConfigurator
    public void configureModuleComponents(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        ContainerKt.registerSingleton(container, WarningAwareUpperBoundChecker.class);
        ContainerKt.registerSingleton(container, JavaNullabilityChecker.class);
        ContainerKt.registerSingleton(container, JvmStaticChecker.class);
        ContainerKt.registerSingleton(container, JvmReflectionAPICallChecker.class);
        ContainerKt.registerSingleton(container, JavaSyntheticScopes.class);
        ContainerKt.registerSingleton(container, SamConversionResolverImpl.class);
        ContainerKt.registerSingleton(container, InterfaceDefaultMethodCallChecker.class);
        ContainerKt.registerSingleton(container, JvmDefaultChecker.class);
        ContainerKt.registerSingleton(container, InlinePlatformCompatibilityChecker.class);
        ContainerKt.registerSingleton(container, JvmModuleAccessibilityChecker.class);
        ContainerKt.registerSingleton(container, JvmModuleAccessibilityChecker.ClassifierUsage.class);
        ContainerKt.registerSingleton(container, JvmTypeSpecificityComparatorDelegate.class);
        ContainerKt.registerSingleton(container, JvmPlatformOverloadsSpecificityComparator.class);
        ContainerKt.registerSingleton(container, JvmSamConversionOracle.class);
        ContainerKt.registerSingleton(container, JvmAdditionalClassPartsProvider.class);
        ContainerKt.registerSingleton(container, JvmRecordApplicabilityChecker.class);
        ContainerKt.registerSingleton(container, JvmPlatformAnnotationFeaturesSupport.class);
        DslKt.useInstance(container, FunctionWithBigAritySupport.LanguageVersionDependent.INSTANCE);
        DslKt.useInstance(container, GenericArrayClassLiteralSupport.Enabled.INSTANCE);
        DslKt.useInstance(container, new JavaActualAnnotationArgumentExtractor());
        DslKt.useInstance(container, JvmSerializableLambdaAnnotationChecker.INSTANCE);
    }
}
