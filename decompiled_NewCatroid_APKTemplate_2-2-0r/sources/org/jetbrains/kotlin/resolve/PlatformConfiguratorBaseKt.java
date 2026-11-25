package org.jetbrains.kotlin.resolve;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.PlatformExtensionsClashResolver;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.resolve.calls.checkers.AbstractClassInstantiationChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.ApiVersionCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.AssigningNamedArgumentToVarargChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.BuilderFunctionsCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.BuilderInferenceAssignmentChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallReturnsArrayOfNothingChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallableReferenceCompatibilityChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CapturingInClosureChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CompanionInParenthesesLHSCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CompanionIncorrectlyUnboundedWhenUsedAsLHSCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.ConstructorHeaderCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.ContractNotAllowedCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CoroutineSuspendCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CustomEnumEntriesMigrationCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.DeprecatedCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.DslScopeViolationCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.EnumEntriesUnsupportedChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.EnumEntryVsCompanionPriorityCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.EqualityCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.FunInterfaceConstructorReferenceChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.ImplicitNothingAsTypeParameterCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.IncorrectCapturedApproximationCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.InfixCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.InlineCheckerWrapper;
import org.jetbrains.kotlin.resolve.calls.checkers.LambdaWithSuspendModifierCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.NamedFunAsExpressionChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.NewSchemeOfIntegerOperatorResolutionChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.NullableVarargArgumentCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.OperatorCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.ProtectedConstructorCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.ReferencingToUnderscoreNamedParameterOfCatchBlockChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.ReifiedTypeParameterSubstitutionChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.ResolutionToPrivateConstructorOfSealedClassChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.ResultTypeWithNullableOperatorsChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.SafeCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.SelfCallInNestedObjectConstructorChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.SuspendConversionCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.SynchronizedByValueChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.UnderscoreUsageChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.UnitConversionCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.UnsupportedUntilOperatorChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.UnsupportedUntilRangeDeclarationChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.UselessElvisCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.VarargWrongExecutionOrderChecker;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.resolve.checkers.ActualClassifierMustHasTheSameMembersAsNonFinalExpectClassifierChecker;
import org.jetbrains.kotlin.resolve.checkers.ActualTypealiasToSpecialAnnotationChecker;
import org.jetbrains.kotlin.resolve.checkers.AnnotationClassTargetAndRetentionChecker;
import org.jetbrains.kotlin.resolve.checkers.ApiVersionClassifierUsageChecker;
import org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker;
import org.jetbrains.kotlin.resolve.checkers.ConstModifierChecker;
import org.jetbrains.kotlin.resolve.checkers.ContextualDeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.ContractDescriptionBlockChecker;
import org.jetbrains.kotlin.resolve.checkers.CyclicAnnotationsChecker;
import org.jetbrains.kotlin.resolve.checkers.DataClassDeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DataObjectContentChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DelegationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeprecatedClassifierUsageChecker;
import org.jetbrains.kotlin.resolve.checkers.DeprecatedSinceKotlinAnnotationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeprecationInheritanceChecker;
import org.jetbrains.kotlin.resolve.checkers.DynamicReceiverChecker;
import org.jetbrains.kotlin.resolve.checkers.EnumCompanionInEnumConstructorCallChecker;
import org.jetbrains.kotlin.resolve.checkers.EnumEntriesRedeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.ExpectActualClassifiersAreInBetaChecker;
import org.jetbrains.kotlin.resolve.checkers.ExpectActualInTheSameModuleChecker;
import org.jetbrains.kotlin.resolve.checkers.ExplicitApiDeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.FunInterfaceDeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.InfixModifierChecker;
import org.jetbrains.kotlin.resolve.checkers.InlineParameterChecker;
import org.jetbrains.kotlin.resolve.checkers.InnerClassInsideValueClass;
import org.jetbrains.kotlin.resolve.checkers.KClassWithIncorrectTypeArgumentChecker;
import org.jetbrains.kotlin.resolve.checkers.LocalVariableTypeParametersChecker;
import org.jetbrains.kotlin.resolve.checkers.MissingDependencyClassChecker;
import org.jetbrains.kotlin.resolve.checkers.MissingDependencySupertypeChecker;
import org.jetbrains.kotlin.resolve.checkers.MultiFieldValueClassAnnotationsChecker;
import org.jetbrains.kotlin.resolve.checkers.NullableExtensionOperatorWithSafeCallChecker;
import org.jetbrains.kotlin.resolve.checkers.OptionalExpectationUsageChecker;
import org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressorClashesResolver;
import org.jetbrains.kotlin.resolve.checkers.PrimitiveNumericComparisonCallChecker;
import org.jetbrains.kotlin.resolve.checkers.PrivateInlineFunctionsReturningAnonymousObjectsChecker;
import org.jetbrains.kotlin.resolve.checkers.PropertiesWithBackingFieldsInsideValueClass;
import org.jetbrains.kotlin.resolve.checkers.ReifiedTypeParameterAnnotationChecker;
import org.jetbrains.kotlin.resolve.checkers.RequireKotlinAnnotationValueChecker;
import org.jetbrains.kotlin.resolve.checkers.ReservedMembersAndConstructsForValueClass;
import org.jetbrains.kotlin.resolve.checkers.ResultClassInReturnTypeChecker;
import org.jetbrains.kotlin.resolve.checkers.SealedInheritorInSameModuleChecker;
import org.jetbrains.kotlin.resolve.checkers.SealedInheritorInSamePackageChecker;
import org.jetbrains.kotlin.resolve.checkers.SealedInterfaceAllowedChecker;
import org.jetbrains.kotlin.resolve.checkers.SinceKotlinAnnotationValueChecker;
import org.jetbrains.kotlin.resolve.checkers.SubtypingBetweenContextReceiversChecker;
import org.jetbrains.kotlin.resolve.checkers.SuspendFunctionAsSupertypeChecker;
import org.jetbrains.kotlin.resolve.checkers.SuspendLimitationsChecker;
import org.jetbrains.kotlin.resolve.checkers.TailrecFunctionChecker;
import org.jetbrains.kotlin.resolve.checkers.TrailingCommaCallChecker;
import org.jetbrains.kotlin.resolve.checkers.TrailingCommaDeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.UnderscoreChecker;
import org.jetbrains.kotlin.resolve.checkers.ValueClassDeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.ValueParameterUsageInDefaultArgumentChecker;
import org.jetbrains.kotlin.resolve.checkers.VolatileAnnotationChecker;
import org.jetbrains.kotlin.resolve.lazy.AbsentDescriptorHandler;
import org.jetbrains.kotlin.types.DynamicTypesSettings;

/* JADX INFO: compiled from: PlatformConfiguratorBase.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a/\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0017\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00150\u0014¢\u0006\u0002\b\u0016\u001a\n\u0010\u0017\u001a\u00020\u0015*\u00020\u000e\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"DEFAULT_ANNOTATION_CHECKERS", "", "Lorg/jetbrains/kotlin/resolve/AdditionalAnnotationChecker;", "DEFAULT_CALL_CHECKERS", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "DEFAULT_CLASH_RESOLVERS", "Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;", "DEFAULT_CLASSIFIER_USAGE_CHECKERS", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageChecker;", "DEFAULT_DECLARATION_CHECKERS", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "DEFAULT_TYPE_CHECKERS", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AdditionalTypeChecker;", "createContainer", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "id", "", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "configureDefaultCheckers", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PlatformConfiguratorBaseKt {
    private static final List<DeclarationChecker> DEFAULT_DECLARATION_CHECKERS = CollectionsKt.listOf((Object[]) new DeclarationChecker[]{ExpectActualInTheSameModuleChecker.INSTANCE, ActualClassifierMustHasTheSameMembersAsNonFinalExpectClassifierChecker.INSTANCE, ExpectActualClassifiersAreInBetaChecker.INSTANCE, new DataClassDeclarationChecker(), ConstModifierChecker.INSTANCE, UnderscoreChecker.INSTANCE, InlineParameterChecker.INSTANCE, new InfixModifierChecker(), SinceKotlinAnnotationValueChecker.INSTANCE, RequireKotlinAnnotationValueChecker.INSTANCE, new ReifiedTypeParameterAnnotationChecker(), DynamicReceiverChecker.INSTANCE, new DelegationChecker(), KClassWithIncorrectTypeArgumentChecker.INSTANCE, SuspendLimitationsChecker.INSTANCE, ValueClassDeclarationChecker.INSTANCE, MultiFieldValueClassAnnotationsChecker.INSTANCE, new PropertiesWithBackingFieldsInsideValueClass(), new InnerClassInsideValueClass(), new AnnotationClassTargetAndRetentionChecker(), new ReservedMembersAndConstructsForValueClass(), new ResultClassInReturnTypeChecker(), new LocalVariableTypeParametersChecker(), new ExplicitApiDeclarationChecker(), TailrecFunctionChecker.INSTANCE, TrailingCommaDeclarationChecker.INSTANCE, MissingDependencySupertypeChecker.ForDeclarations.INSTANCE, new FunInterfaceDeclarationChecker(), DeprecationInheritanceChecker.INSTANCE, DeprecatedSinceKotlinAnnotationChecker.INSTANCE, ContractDescriptionBlockChecker.INSTANCE, PrivateInlineFunctionsReturningAnonymousObjectsChecker.INSTANCE, SealedInheritorInSamePackageChecker.INSTANCE, SealedInheritorInSameModuleChecker.INSTANCE, SealedInterfaceAllowedChecker.INSTANCE, SuspendFunctionAsSupertypeChecker.INSTANCE, EnumCompanionInEnumConstructorCallChecker.INSTANCE, ContextualDeclarationChecker.INSTANCE, SubtypingBetweenContextReceiversChecker.INSTANCE, ValueParameterUsageInDefaultArgumentChecker.INSTANCE, CyclicAnnotationsChecker.INSTANCE, UnsupportedUntilRangeDeclarationChecker.INSTANCE, DataObjectContentChecker.INSTANCE, EnumEntriesRedeclarationChecker.INSTANCE, VolatileAnnotationChecker.INSTANCE, ActualTypealiasToSpecialAnnotationChecker.INSTANCE});
    private static final List<CallChecker> DEFAULT_CALL_CHECKERS = CollectionsKt.listOf((Object[]) new CallChecker[]{new CapturingInClosureChecker(), new InlineCheckerWrapper(), new SynchronizedByValueChecker(), new SafeCallChecker(), TrailingCommaCallChecker.INSTANCE, DeprecatedCallChecker.INSTANCE, new CallReturnsArrayOfNothingChecker(), new InfixCallChecker(), new OperatorCallChecker(), ConstructorHeaderCallChecker.INSTANCE, ProtectedConstructorCallChecker.INSTANCE, ApiVersionCallChecker.INSTANCE, CoroutineSuspendCallChecker.INSTANCE, BuilderFunctionsCallChecker.INSTANCE, DslScopeViolationCallChecker.INSTANCE, MissingDependencyClassChecker.INSTANCE, new CallableReferenceCompatibilityChecker(), UnderscoreUsageChecker.INSTANCE, new AssigningNamedArgumentToVarargChecker(), ImplicitNothingAsTypeParameterCallChecker.INSTANCE, PrimitiveNumericComparisonCallChecker.INSTANCE, LambdaWithSuspendModifierCallChecker.INSTANCE, new UselessElvisCallChecker(), new ResultTypeWithNullableOperatorsChecker(), NullableVarargArgumentCallChecker.INSTANCE, NamedFunAsExpressionChecker.INSTANCE, ContractNotAllowedCallChecker.INSTANCE, new ReifiedTypeParameterSubstitutionChecker(), MissingDependencySupertypeChecker.ForCalls.INSTANCE, AbstractClassInstantiationChecker.INSTANCE, SuspendConversionCallChecker.INSTANCE, UnitConversionCallChecker.INSTANCE, FunInterfaceConstructorReferenceChecker.INSTANCE, NullableExtensionOperatorWithSafeCallChecker.INSTANCE, ReferencingToUnderscoreNamedParameterOfCatchBlockChecker.INSTANCE, VarargWrongExecutionOrderChecker.INSTANCE, SelfCallInNestedObjectConstructorChecker.INSTANCE, NewSchemeOfIntegerOperatorResolutionChecker.INSTANCE, EnumEntryVsCompanionPriorityCallChecker.INSTANCE, CompanionInParenthesesLHSCallChecker.INSTANCE, ResolutionToPrivateConstructorOfSealedClassChecker.INSTANCE, EqualityCallChecker.INSTANCE, UnsupportedUntilOperatorChecker.INSTANCE, BuilderInferenceAssignmentChecker.INSTANCE, IncorrectCapturedApproximationCallChecker.INSTANCE, CompanionIncorrectlyUnboundedWhenUsedAsLHSCallChecker.INSTANCE, CustomEnumEntriesMigrationCallChecker.INSTANCE, EnumEntriesUnsupportedChecker.INSTANCE});
    private static final List<AdditionalTypeChecker> DEFAULT_TYPE_CHECKERS = CollectionsKt.emptyList();
    private static final List<ClassifierUsageChecker> DEFAULT_CLASSIFIER_USAGE_CHECKERS = CollectionsKt.listOf((Object[]) new ClassifierUsageChecker[]{new DeprecatedClassifierUsageChecker(), ApiVersionClassifierUsageChecker.INSTANCE, MissingDependencyClassChecker.ClassifierUsage.INSTANCE, new OptionalExpectationUsageChecker()});
    private static final List<AdditionalAnnotationChecker> DEFAULT_ANNOTATION_CHECKERS = CollectionsKt.emptyList();
    private static final List<PlatformExtensionsClashResolver<?>> DEFAULT_CLASH_RESOLVERS = CollectionsKt.listOf((Object[]) new PlatformExtensionsClashResolver[]{new IdentifierCheckerClashesResolver(), new PlatformExtensionsClashResolver.FallbackToDefault(TypeSpecificityComparator.NONE.INSTANCE, TypeSpecificityComparator.class), new PlatformExtensionsClashResolver.FallbackToDefault(new DynamicTypesSettings(), DynamicTypesSettings.class), new PlatformExtensionsClashResolver.FirstWins(AbsentDescriptorHandler.class), new PlatformDiagnosticSuppressorClashesResolver()});

    public static final void configureDefaultCheckers(StorageComponentContainer storageComponentContainer) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Iterator<T> it = DEFAULT_DECLARATION_CHECKERS.iterator();
        while (it.hasNext()) {
            DslKt.useInstance(storageComponentContainer, (DeclarationChecker) it.next());
        }
        Iterator<T> it2 = DEFAULT_CALL_CHECKERS.iterator();
        while (it2.hasNext()) {
            DslKt.useInstance(storageComponentContainer, (CallChecker) it2.next());
        }
        Iterator<T> it3 = DEFAULT_TYPE_CHECKERS.iterator();
        while (it3.hasNext()) {
            DslKt.useInstance(storageComponentContainer, (AdditionalTypeChecker) it3.next());
        }
        Iterator<T> it4 = DEFAULT_CLASSIFIER_USAGE_CHECKERS.iterator();
        while (it4.hasNext()) {
            DslKt.useInstance(storageComponentContainer, (ClassifierUsageChecker) it4.next());
        }
        Iterator<T> it5 = DEFAULT_ANNOTATION_CHECKERS.iterator();
        while (it5.hasNext()) {
            DslKt.useInstance(storageComponentContainer, (AdditionalAnnotationChecker) it5.next());
        }
        Iterator<T> it6 = DEFAULT_CLASH_RESOLVERS.iterator();
        while (it6.hasNext()) {
            DslKt.useClashResolver(storageComponentContainer, (PlatformExtensionsClashResolver) it6.next());
        }
    }

    public static final StorageComponentContainer createContainer(String id2, PlatformDependentAnalyzerServices analyzerServices, Function1<? super StorageComponentContainer, Unit> init) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(init, "init");
        return DslKt.composeContainer(id2, analyzerServices.getPlatformConfigurator().getPlatformSpecificContainer(), init);
    }
}
