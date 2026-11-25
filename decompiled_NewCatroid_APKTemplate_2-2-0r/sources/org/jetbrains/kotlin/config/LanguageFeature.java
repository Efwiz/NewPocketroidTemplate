package org.jetbrains.kotlin.config;

import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.DescriptionAware;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'BoundCallableReferences' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:315)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:288)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:160)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: LanguageVersionSettings.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000+\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0003\b\u0095\u0002\b\u0086\u0081\u0002\u0018\u0000 \u009d\u00022\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0006\u009d\u0002\u009e\u0002\u009f\u0002B;\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\b\u001a\u00020\tX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000eR\u0011\u0010\u0015\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4j\u0002\b5j\u0002\b6j\u0002\b7j\u0002\b8j\u0002\b9j\u0002\b:j\u0002\b;j\u0002\b<j\u0002\b=j\u0002\b>j\u0002\b?j\u0002\b@j\u0002\bAj\u0002\bBj\u0002\bCj\u0002\bDj\u0002\bEj\u0002\bFj\u0002\bGj\u0002\bHj\u0002\bIj\u0002\bJj\u0002\bKj\u0002\bLj\u0002\bMj\u0002\bNj\u0002\bOj\u0002\bPj\u0002\bQj\u0002\bRj\u0002\bSj\u0002\bTj\u0002\bUj\u0002\bVj\u0002\bWj\u0002\bXj\u0002\bYj\u0002\bZj\u0002\b[j\u0002\b\\j\u0002\b]j\u0002\b^j\u0002\b_j\u0002\b`j\u0002\baj\u0002\bbj\u0002\bcj\u0002\bdj\u0002\bej\u0002\bfj\u0002\bgj\u0002\bhj\u0002\bij\u0002\bjj\u0002\bkj\u0002\blj\u0002\bmj\u0002\bnj\u0002\boj\u0002\bpj\u0002\bqj\u0002\brj\u0002\bsj\u0002\btj\u0002\buj\u0002\bvj\u0002\bwj\u0002\bxj\u0002\byj\u0002\bzj\u0002\b{j\u0002\b|j\u0002\b}j\u0002\b~j\u0002\b\u007fj\u0003\b\u0080\u0001j\u0003\b\u0081\u0001j\u0003\b\u0082\u0001j\u0003\b\u0083\u0001j\u0003\b\u0084\u0001j\u0003\b\u0085\u0001j\u0003\b\u0086\u0001j\u0003\b\u0087\u0001j\u0003\b\u0088\u0001j\u0003\b\u0089\u0001j\u0003\b\u008a\u0001j\u0003\b\u008b\u0001j\u0003\b\u008c\u0001j\u0003\b\u008d\u0001j\u0003\b\u008e\u0001j\u0003\b\u008f\u0001j\u0003\b\u0090\u0001j\u0003\b\u0091\u0001j\u0003\b\u0092\u0001j\u0003\b\u0093\u0001j\u0003\b\u0094\u0001j\u0003\b\u0095\u0001j\u0003\b\u0096\u0001j\u0003\b\u0097\u0001j\u0003\b\u0098\u0001j\u0003\b\u0099\u0001j\u0003\b\u009a\u0001j\u0003\b\u009b\u0001j\u0003\b\u009c\u0001j\u0003\b\u009d\u0001j\u0003\b\u009e\u0001j\u0003\b\u009f\u0001j\u0003\b \u0001j\u0003\b¡\u0001j\u0003\b¢\u0001j\u0003\b£\u0001j\u0003\b¤\u0001j\u0003\b¥\u0001j\u0003\b¦\u0001j\u0003\b§\u0001j\u0003\b¨\u0001j\u0003\b©\u0001j\u0003\bª\u0001j\u0003\b«\u0001j\u0003\b¬\u0001j\u0003\b\u00ad\u0001j\u0003\b®\u0001j\u0003\b¯\u0001j\u0003\b°\u0001j\u0003\b±\u0001j\u0003\b²\u0001j\u0003\b³\u0001j\u0003\b´\u0001j\u0003\bµ\u0001j\u0003\b¶\u0001j\u0003\b·\u0001j\u0003\b¸\u0001j\u0003\b¹\u0001j\u0003\bº\u0001j\u0003\b»\u0001j\u0003\b¼\u0001j\u0003\b½\u0001j\u0003\b¾\u0001j\u0003\b¿\u0001j\u0003\bÀ\u0001j\u0003\bÁ\u0001j\u0003\bÂ\u0001j\u0003\bÃ\u0001j\u0003\bÄ\u0001j\u0003\bÅ\u0001j\u0003\bÆ\u0001j\u0003\bÇ\u0001j\u0003\bÈ\u0001j\u0003\bÉ\u0001j\u0003\bÊ\u0001j\u0003\bË\u0001j\u0003\bÌ\u0001j\u0003\bÍ\u0001j\u0003\bÎ\u0001j\u0003\bÏ\u0001j\u0003\bÐ\u0001j\u0003\bÑ\u0001j\u0003\bÒ\u0001j\u0003\bÓ\u0001j\u0003\bÔ\u0001j\u0003\bÕ\u0001j\u0003\bÖ\u0001j\u0003\b×\u0001j\u0003\bØ\u0001j\u0003\bÙ\u0001j\u0003\bÚ\u0001j\u0003\bÛ\u0001j\u0003\bÜ\u0001j\u0003\bÝ\u0001j\u0003\bÞ\u0001j\u0003\bß\u0001j\u0003\bà\u0001j\u0003\bá\u0001j\u0003\bâ\u0001j\u0003\bã\u0001j\u0003\bä\u0001j\u0003\bå\u0001j\u0003\bæ\u0001j\u0003\bç\u0001j\u0003\bè\u0001j\u0003\bé\u0001j\u0003\bê\u0001j\u0003\bë\u0001j\u0003\bì\u0001j\u0003\bí\u0001j\u0003\bî\u0001j\u0003\bï\u0001j\u0003\bð\u0001j\u0003\bñ\u0001j\u0003\bò\u0001j\u0003\bó\u0001j\u0003\bô\u0001j\u0003\bõ\u0001j\u0003\bö\u0001j\u0003\b÷\u0001j\u0003\bø\u0001j\u0003\bù\u0001j\u0003\bú\u0001j\u0003\bû\u0001j\u0003\bü\u0001j\u0003\bý\u0001j\u0003\bþ\u0001j\u0003\bÿ\u0001j\u0003\b\u0080\u0002j\u0003\b\u0081\u0002j\u0003\b\u0082\u0002j\u0003\b\u0083\u0002j\u0003\b\u0084\u0002j\u0003\b\u0085\u0002j\u0003\b\u0086\u0002j\u0003\b\u0087\u0002j\u0003\b\u0088\u0002j\u0003\b\u0089\u0002j\u0003\b\u008a\u0002j\u0003\b\u008b\u0002j\u0003\b\u008c\u0002j\u0003\b\u008d\u0002j\u0003\b\u008e\u0002j\u0003\b\u008f\u0002j\u0003\b\u0090\u0002j\u0003\b\u0091\u0002j\u0003\b\u0092\u0002j\u0003\b\u0093\u0002j\u0003\b\u0094\u0002j\u0003\b\u0095\u0002j\u0003\b\u0096\u0002j\u0003\b\u0097\u0002j\u0003\b\u0098\u0002j\u0003\b\u0099\u0002j\u0003\b\u009a\u0002j\u0003\b\u009b\u0002j\u0003\b\u009c\u0002¨\u0006 \u0002"}, d2 = {"Lorg/jetbrains/kotlin/config/LanguageFeature;", "", "sinceVersion", "Lorg/jetbrains/kotlin/config/LanguageVersion;", "sinceApiVersion", "Lorg/jetbrains/kotlin/config/ApiVersion;", "hintUrl", "", "isEnabledWithWarning", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/config/LanguageFeature$Kind;", "(Ljava/lang/String;ILorg/jetbrains/kotlin/config/LanguageVersion;Lorg/jetbrains/kotlin/config/ApiVersion;Ljava/lang/String;ZLorg/jetbrains/kotlin/config/LanguageFeature$Kind;)V", "getHintUrl", "()Ljava/lang/String;", "isEnabledWithWarning$util", "()Z", "getKind", "()Lorg/jetbrains/kotlin/config/LanguageFeature$Kind;", "presentableName", "getPresentableName", "presentableText", "getPresentableText", "getSinceApiVersion", "()Lorg/jetbrains/kotlin/config/ApiVersion;", "getSinceVersion", "()Lorg/jetbrains/kotlin/config/LanguageVersion;", "TypeAliases", "BoundCallableReferences", "LocalDelegatedProperties", "TopLevelSealedInheritance", "AdditionalBuiltInsMembers", "DataClassInheritance", "InlineProperties", "DestructuringLambdaParameters", "SingleUnderscoreForParameterName", "DslMarkersSupport", "UnderscoresInNumericLiterals", "DivisionByZeroInConstantExpressions", "InlineConstVals", "OperatorRem", "OperatorProvideDelegate", "ShortSyntaxForPropertyGetters", "RefinedSamAdaptersPriority", "SafeCallBoundSmartCasts", "TypeInferenceOnGenericsForCallableReferences", "NoDelegationToJavaDefaultInterfaceMembers", "DefaultImportOfPackageKotlinComparisons", "Coroutines", "InlineDefaultFunctionalParameters", "SoundSmartCastsAfterTry", "DeprecatedFieldForInvisibleCompanionObject", "NullabilityAssertionOnExtensionReceiver", "SafeCastCheckBoundSmartCasts", "CapturedInClosureSmartCasts", "LateinitTopLevelProperties", "LateinitLocalVariables", "InnerClassInEnumEntryClass", "CallableReferencesToClassMembersWithEmptyLHS", "ThrowNpeOnExplicitEqualsForBoxedNull", "JvmPackageName", "AssigningArraysToVarargsInNamedFormInAnnotations", "ExpectedTypeFromCast", "DefaultMethodsCallFromJava6TargetError", "RestrictionOfValReassignmentViaBackingField", "NestedClassesInEnumEntryShouldBeInner", "ProhibitDataClassesOverridingCopy", "RestrictionOfWrongAnnotationsWithUseSiteTargetsOnTypes", "ProhibitInnerClassesOfGenericClassExtendingThrowable", "ProperForInArrayLoopRangeVariableAssignmentSemantic", "NestedClassesInAnnotations", "JvmStaticInInterface", "JvmFieldInInterface", "ProhibitVisibilityOfNestedClassifiersFromSupertypesOfCompanion", "ProhibitNonConstValuesAsVarargsInAnnotations", "ReleaseCoroutines", "ReadDeserializedContracts", "UseReturnsEffect", "UseCallsInPlaceEffect", "AllowContractsForCustomFunctions", "VariableDeclarationInWhenSubject", "ProhibitLocalAnnotations", "ProhibitSmartcastsOnLocalDelegatedProperty", "ProhibitOperatorMod", "ProhibitAssigningSingleElementsToVarargsInNamedForm", "FunctionTypesWithBigArity", "RestrictRetentionForExpressionAnnotations", "NormalizeConstructorCalls", "StrictJavaNullabilityAssertions", "SoundSmartcastForEnumEntries", "ProhibitErroneousExpressionsInAnnotationsWithUseSiteTargets", "NewCapturedReceiverFieldNamingConvention", "ExtendedMainConvention", "ExperimentalBuilderInference", "InlineClasses", "DslMarkerOnFunctionTypeReceiver", "RestrictReturnStatementTarget", "NoConstantValueAttributeForNonConstVals", "WarningOnMainUnusedParameter", "PolymorphicSignature", "ProhibitConcurrentHashMapContains", "ProhibitTypeParametersForLocalVariables", "ProhibitJvmOverloadsOnConstructorsOfAnnotationClasses", "ProhibitTypeParametersInAnonymousObjects", "ProperInlineFromHigherPlatformDiagnostic", "ProhibitRepeatedUseSiteTargetAnnotations", "ProhibitUseSiteTargetAnnotationsOnSuperTypes", "ProhibitTypeParametersInClassLiteralsInAnnotationArguments", "ProhibitComparisonOfIncompatibleEnums", "BareArrayClassLiteral", "ProhibitGenericArrayClassLiteral", "NonParenthesizedAnnotationsOnFunctionalTypes", "UseGetterNameForPropertyAnnotationsMethodOnJvm", "AllowBreakAndContinueInsideWhen", "MixedNamedArgumentsInTheirOwnPosition", "ProhibitTailrecOnVirtualMember", "ProperComputationOrderOfTailrecDefaultParameters", "TrailingCommas", "ProhibitProtectedCallFromInline", "ProperFinally", "AllowAssigningArrayElementsToVarargsInNamedFormForFunctions", "AllowNullOperatorsForResult", "PreferJavaFieldOverload", "AllowContractsForNonOverridableMembers", "AllowReifiedGenericsInContracts", "ProperVisibilityForCompanionObjectInstanceField", "DoNotGenerateThrowsForDelegatedKotlinMembers", "ProperIeee754Comparisons", "FunctionalInterfaceConversion", "GenerateJvmOverloadsAsFinal", "MangleClassMembersReturningInlineClasses", "ImproveReportingDiagnosticsOnProtectedMembersOfBaseClass", "NewInference", "SamConversionForKotlinFunctions", "SamConversionPerArgument", "FunctionReferenceWithDefaultValueAsOtherType", "OverloadResolutionByLambdaReturnType", "ContractsOnCallsWithImplicitReceiver", "ProhibitSpreadOnSignaturePolymorphicCall", "ProhibitInvisibleAbstractMethodsInSuperclasses", "ProhibitNonReifiedArraysAsReifiedTypeArguments", "ProhibitVarargAsArrayAfterSamArgument", "CorrectSourceMappingSyntax", "ProperArrayConventionSetterWithDefaultCalls", "DisableCompatibilityModeForNewInference", "AdaptedCallableReferenceAgainstReflectiveType", "InferenceCompatibility", "RequiredPrimaryConstructorDelegationCallInEnums", "ApproximateAnonymousReturnTypesInPrivateInlineFunctions", "ForbidReferencingToUnderscoreNamedParameterOfCatchBlock", "UseCorrectExecutionOrderForVarargArguments", "JvmRecordSupport", "AllowNullOperatorsForResultAndResultReturnTypeByDefault", "AllowSealedInheritorsInDifferentFilesOfSamePackage", "SealedInterfaces", "JvmIrEnabledByDefault", "JvmInlineValueClasses", "SuspendFunctionsInFunInterfaces", "SamWrapperClassesAreSynthetic", "StrictOnlyInputTypesChecks", "ProhibitJvmFieldOnOverrideFromInterfaceInPrimaryConstructor", "PrivateInFileEffectiveVisibility", "ProhibitSelfCallsInNestedObjects", "ProperCheckAnnotationsTargetInTypeUsePositions", "SuspendFunctionAsSupertype", "UnrestrictedBuilderInference", "ClassTypeParameterAnnotations", "TypeInferenceOnCallsWithSelfTypes", "WarnAboutNonExhaustiveWhenOnAlgebraicTypes", "InstantiationOfAnnotationClasses", "OptInContagiousSignatures", "RepeatableAnnotations", "RepeatableAnnotationContainerConstraints", "UseBuilderInferenceOnlyIfNeeded", "SuspendConversion", "ProhibitSuperCallsFromPublicInline", "ProhibitProtectedConstructorCallFromPublicInline", "TypeEnhancementImprovementsInStrictMode", "OptInRelease", "ProhibitNonExhaustiveWhenOnAlgebraicTypes", "UseBuilderInferenceWithoutAnnotation", "ProhibitSmartcastsOnPropertyFromAlienBaseClass", "ProhibitInvalidCharsInNativeIdentifiers", "DefinitelyNonNullableTypes", "ProhibitSimplificationOfNonTrivialConstBooleanExpressions", "SafeCallsAreAlwaysNullable", "JvmPermittedSubclassesAttributeForSealed", "ProperTypeInferenceConstraintsProcessing", "ForbidExposingTypesInPrimaryConstructorProperties", "PartiallySpecifiedTypeArguments", "EliminateAmbiguitiesWithExternalTypeParameters", "EliminateAmbiguitiesOnInheritedSamInterfaces", "ConsiderExtensionReceiverFromConstrainsInLambda", "ProperInternalVisibilityCheckInImportingScope", "InlineClassImplementationByDelegation", "QualifiedSupertypeMayBeExtendedByOtherSupertype", "YieldIsNoMoreReserved", "NoDeprecationOnDeprecatedEnumEntries", "ProhibitQualifiedAccessToUninitializedEnumEntry", "ForbidRecursiveDelegateExpressions", "KotlinFunInterfaceConstructorReference", "SuspendOnlySamConversions", "DontLoseDiagnosticsDuringOverloadResolutionByReturnType", "ProhibitConfusingSyntaxInWhenBranches", "UseConsistentRulesForPrivateConstructorsOfSealedClasses", "ProgressionsChangingResolve", "AbstractClassMemberNotImplementedWithIntermediateAbstractClass", "ForbidSuperDelegationToAbstractAnyMethod", "ProperEqualityChecksInBuilderInferenceCalls", "ProhibitNonExhaustiveIfInRhsOfElvis", "ReportMissingUpperBoundsViolatedErrorOnAbbreviationAtSupertypes", "ForbidUsingExtensionPropertyTypeParameterInDelegate", "SynchronizedSuspendError", "ReportNonVarargSpreadOnGenericCalls", "RangeUntilOperator", "GenericInlineClassParameter", "ProhibitIllegalValueParameterUsageInDefaultArguments", "ProhibitConstructorCallOnFunctionalSupertype", "ProhibitArrayLiteralsInCompanionOfAnnotation", "ProhibitCyclesInAnnotations", "ForbidExtensionFunctionTypeOnNonFunctionTypes", "ProhibitEnumDeclaringClass", "StopPropagatingDeprecationThroughOverrides", "ReportTypeVarianceConflictOnQualifierArguments", "ReportErrorsOnRecursiveTypeInsidePlusAssignment", "ForbidExtensionCallsOnInlineFunctionalParameters", "SkipStandaloneScriptsInSourceRoots", "ModifierNonBuiltinSuspendFunError", "EnumEntries", "ForbidSuperDelegationToAbstractFakeOverride", "DataObjects", "ProhibitAccessToEnumCompanionMembersInEnumConstructorCall", "RefineTypeCheckingOnAssignmentsToJavaFields", "ValueClassesSecondaryConstructorWithBody", "NativeJsProhibitLateinitIsInitializedIntrinsicWithoutPrivateAccess", "TakeIntoAccountEffectivelyFinalInMustBeInitializedCheck", "ProhibitUsingNullableTypeParameterAgainstNotNullAnnotated", "NoSourceCodeInNotNullAssertionExceptions", "MultiplatformRestrictions", "EnhanceNullabilityOfPrimitiveArrays", "AllowEmptyIntersectionsInResultTypeResolver", "ProhibitSmartcastsOnPropertyFromAlienBaseClassInheritedInInvisibleClass", "ForbidInferringPostponedTypeVariableIntoDeclaredUpperBound", "ProhibitUseSiteGetTargetAnnotations", "KeepNullabilityWhenApproximatingLocalType", "ProhibitAccessToInvisibleSetterFromDerivedClass", "ProhibitOpenValDeferredInitialization", "ProhibitMissedMustBeInitializedWhenThereIsNoPrimaryConstructor", "MangleCallsToJavaMethodsWithValueClasses", "ForbidInferringTypeVariablesIntoEmptyIntersection", "ReferencesToSyntheticJavaProperties", "ProhibitImplementingVarByInheritedVal", "PrioritizedEnumEntries", "ExpectActualClasses", "IgnoreNullabilityForErasedValueParameters", "NoBuilderInferenceWithoutAnnotationRestriction", "ReportErrorsForComparisonOperators", "NoAdditionalErrorsInK1DiagnosticReporter", "ProhibitScriptTopLevelInnerClasses", "BreakContinueInInlineLambdas", "LightweightLambdas", "JsEnableExtensionFunctionInExternals", "PackagePrivateFileClassesWithAllPrivateMembers", "BooleanElvisBoundSmartCasts", "NewDataFlowForTryExpressions", "AllowResultInReturnType", "MultiPlatformProjects", "ProhibitComparisonOfIncompatibleClasses", "ExplicitBackingFields", "FunctionalTypeWithExtensionAsSupertype", "JsAllowInvalidCharsIdentifiersEscaping", "JsAllowValueClassesInExternals", "ContextReceivers", "ValueClasses", "JavaSamConversionEqualsHashCode", "UnitConversionsOnArbitraryExpressions", "JsAllowImplementingFunctionInterface", "CustomEqualsInValueClasses", "InlineLateinit", "EnableDfaWarningsInK2", "ContractSyntaxV2", "ImplicitSignedToUnsignedIntegerConversion", "IntrinsicConstEvaluation", "DisableCheckingChangedProgressionsResolve", "ContextSensitiveEnumResolutionInWhen", "Companion", Namer.CLASS_KIND_ENUM, "State", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LanguageFeature {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ LanguageFeature[] $VALUES;
    public static final LanguageFeature AbstractClassMemberNotImplementedWithIntermediateAbstractClass;
    public static final LanguageFeature AdaptedCallableReferenceAgainstReflectiveType;
    public static final LanguageFeature AdditionalBuiltInsMembers;
    public static final LanguageFeature AllowAssigningArrayElementsToVarargsInNamedFormForFunctions;
    public static final LanguageFeature AllowBreakAndContinueInsideWhen;
    public static final LanguageFeature AllowContractsForCustomFunctions;
    public static final LanguageFeature AllowContractsForNonOverridableMembers;
    public static final LanguageFeature AllowEmptyIntersectionsInResultTypeResolver;
    public static final LanguageFeature AllowNullOperatorsForResult;
    public static final LanguageFeature AllowNullOperatorsForResultAndResultReturnTypeByDefault;
    public static final LanguageFeature AllowReifiedGenericsInContracts;
    public static final LanguageFeature AllowResultInReturnType;
    public static final LanguageFeature AllowSealedInheritorsInDifferentFilesOfSamePackage;
    public static final LanguageFeature ApproximateAnonymousReturnTypesInPrivateInlineFunctions;
    public static final LanguageFeature AssigningArraysToVarargsInNamedFormInAnnotations;
    public static final LanguageFeature BareArrayClassLiteral;
    public static final LanguageFeature BooleanElvisBoundSmartCasts;
    public static final LanguageFeature BoundCallableReferences;
    public static final LanguageFeature BreakContinueInInlineLambdas;
    public static final LanguageFeature CallableReferencesToClassMembersWithEmptyLHS;
    public static final LanguageFeature CapturedInClosureSmartCasts;
    public static final LanguageFeature ClassTypeParameterAnnotations;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    public static final LanguageFeature ConsiderExtensionReceiverFromConstrainsInLambda;
    public static final LanguageFeature ContextReceivers;
    public static final LanguageFeature ContextSensitiveEnumResolutionInWhen;
    public static final LanguageFeature ContractSyntaxV2;
    public static final LanguageFeature ContractsOnCallsWithImplicitReceiver;
    public static final LanguageFeature Coroutines;
    public static final LanguageFeature CorrectSourceMappingSyntax;
    public static final LanguageFeature CustomEqualsInValueClasses;
    public static final LanguageFeature DataClassInheritance;
    public static final LanguageFeature DataObjects;
    public static final LanguageFeature DefaultImportOfPackageKotlinComparisons;
    public static final LanguageFeature DefaultMethodsCallFromJava6TargetError;
    public static final LanguageFeature DefinitelyNonNullableTypes;
    public static final LanguageFeature DeprecatedFieldForInvisibleCompanionObject;
    public static final LanguageFeature DestructuringLambdaParameters;
    public static final LanguageFeature DisableCheckingChangedProgressionsResolve;
    public static final LanguageFeature DisableCompatibilityModeForNewInference;
    public static final LanguageFeature DivisionByZeroInConstantExpressions;
    public static final LanguageFeature DoNotGenerateThrowsForDelegatedKotlinMembers;
    public static final LanguageFeature DontLoseDiagnosticsDuringOverloadResolutionByReturnType;
    public static final LanguageFeature DslMarkerOnFunctionTypeReceiver;
    public static final LanguageFeature DslMarkersSupport;
    public static final LanguageFeature EliminateAmbiguitiesOnInheritedSamInterfaces;
    public static final LanguageFeature EliminateAmbiguitiesWithExternalTypeParameters;
    public static final LanguageFeature EnableDfaWarningsInK2;
    public static final LanguageFeature EnhanceNullabilityOfPrimitiveArrays;
    public static final LanguageFeature EnumEntries;
    public static final LanguageFeature ExpectActualClasses;
    public static final LanguageFeature ExpectedTypeFromCast;
    public static final LanguageFeature ExperimentalBuilderInference;
    public static final LanguageFeature ExplicitBackingFields;
    public static final LanguageFeature ExtendedMainConvention;
    public static final LanguageFeature ForbidExposingTypesInPrimaryConstructorProperties;
    public static final LanguageFeature ForbidExtensionCallsOnInlineFunctionalParameters;
    public static final LanguageFeature ForbidExtensionFunctionTypeOnNonFunctionTypes;
    public static final LanguageFeature ForbidInferringPostponedTypeVariableIntoDeclaredUpperBound;
    public static final LanguageFeature ForbidInferringTypeVariablesIntoEmptyIntersection;
    public static final LanguageFeature ForbidRecursiveDelegateExpressions;
    public static final LanguageFeature ForbidReferencingToUnderscoreNamedParameterOfCatchBlock;
    public static final LanguageFeature ForbidSuperDelegationToAbstractAnyMethod;
    public static final LanguageFeature ForbidSuperDelegationToAbstractFakeOverride;
    public static final LanguageFeature ForbidUsingExtensionPropertyTypeParameterInDelegate;
    public static final LanguageFeature FunctionReferenceWithDefaultValueAsOtherType;
    public static final LanguageFeature FunctionTypesWithBigArity;
    public static final LanguageFeature FunctionalInterfaceConversion;
    public static final LanguageFeature FunctionalTypeWithExtensionAsSupertype;
    public static final LanguageFeature GenerateJvmOverloadsAsFinal;
    public static final LanguageFeature GenericInlineClassParameter;
    public static final LanguageFeature IgnoreNullabilityForErasedValueParameters;
    public static final LanguageFeature ImplicitSignedToUnsignedIntegerConversion;
    public static final LanguageFeature ImproveReportingDiagnosticsOnProtectedMembersOfBaseClass;
    public static final LanguageFeature InferenceCompatibility;
    public static final LanguageFeature InlineClassImplementationByDelegation;
    public static final LanguageFeature InlineClasses;
    public static final LanguageFeature InlineConstVals;
    public static final LanguageFeature InlineDefaultFunctionalParameters;
    public static final LanguageFeature InlineLateinit;
    public static final LanguageFeature InlineProperties;
    public static final LanguageFeature InnerClassInEnumEntryClass;
    public static final LanguageFeature InstantiationOfAnnotationClasses;
    public static final LanguageFeature IntrinsicConstEvaluation;
    public static final LanguageFeature JavaSamConversionEqualsHashCode;
    public static final LanguageFeature JsAllowImplementingFunctionInterface;
    public static final LanguageFeature JsAllowInvalidCharsIdentifiersEscaping;
    public static final LanguageFeature JsAllowValueClassesInExternals;
    public static final LanguageFeature JsEnableExtensionFunctionInExternals;
    public static final LanguageFeature JvmFieldInInterface;
    public static final LanguageFeature JvmInlineValueClasses;
    public static final LanguageFeature JvmIrEnabledByDefault;
    public static final LanguageFeature JvmPackageName;
    public static final LanguageFeature JvmPermittedSubclassesAttributeForSealed;
    public static final LanguageFeature JvmRecordSupport;
    public static final LanguageFeature JvmStaticInInterface;
    public static final LanguageFeature KeepNullabilityWhenApproximatingLocalType;
    public static final LanguageFeature KotlinFunInterfaceConstructorReference;
    public static final LanguageFeature LateinitLocalVariables;
    public static final LanguageFeature LateinitTopLevelProperties;
    public static final LanguageFeature LightweightLambdas;
    public static final LanguageFeature LocalDelegatedProperties;
    public static final LanguageFeature MangleCallsToJavaMethodsWithValueClasses;
    public static final LanguageFeature MangleClassMembersReturningInlineClasses;
    public static final LanguageFeature MixedNamedArgumentsInTheirOwnPosition;
    public static final LanguageFeature ModifierNonBuiltinSuspendFunError;
    public static final LanguageFeature MultiPlatformProjects;
    public static final LanguageFeature MultiplatformRestrictions;
    public static final LanguageFeature NativeJsProhibitLateinitIsInitializedIntrinsicWithoutPrivateAccess;
    public static final LanguageFeature NestedClassesInAnnotations;
    public static final LanguageFeature NestedClassesInEnumEntryShouldBeInner;
    public static final LanguageFeature NewCapturedReceiverFieldNamingConvention;
    public static final LanguageFeature NewDataFlowForTryExpressions;
    public static final LanguageFeature NewInference;
    public static final LanguageFeature NoAdditionalErrorsInK1DiagnosticReporter;
    public static final LanguageFeature NoBuilderInferenceWithoutAnnotationRestriction;
    public static final LanguageFeature NoConstantValueAttributeForNonConstVals;
    public static final LanguageFeature NoDelegationToJavaDefaultInterfaceMembers;
    public static final LanguageFeature NoDeprecationOnDeprecatedEnumEntries;
    public static final LanguageFeature NoSourceCodeInNotNullAssertionExceptions;
    public static final LanguageFeature NonParenthesizedAnnotationsOnFunctionalTypes;
    public static final LanguageFeature NormalizeConstructorCalls;
    public static final LanguageFeature NullabilityAssertionOnExtensionReceiver;
    public static final LanguageFeature OperatorProvideDelegate;
    public static final LanguageFeature OperatorRem;
    public static final LanguageFeature OptInContagiousSignatures;
    public static final LanguageFeature OptInRelease;
    public static final LanguageFeature OverloadResolutionByLambdaReturnType;
    public static final LanguageFeature PackagePrivateFileClassesWithAllPrivateMembers;
    public static final LanguageFeature PartiallySpecifiedTypeArguments;
    public static final LanguageFeature PolymorphicSignature;
    public static final LanguageFeature PreferJavaFieldOverload;
    public static final LanguageFeature PrioritizedEnumEntries;
    public static final LanguageFeature PrivateInFileEffectiveVisibility;
    public static final LanguageFeature ProgressionsChangingResolve;
    public static final LanguageFeature ProhibitAccessToEnumCompanionMembersInEnumConstructorCall;
    public static final LanguageFeature ProhibitAccessToInvisibleSetterFromDerivedClass;
    public static final LanguageFeature ProhibitArrayLiteralsInCompanionOfAnnotation;
    public static final LanguageFeature ProhibitAssigningSingleElementsToVarargsInNamedForm;
    public static final LanguageFeature ProhibitComparisonOfIncompatibleClasses;
    public static final LanguageFeature ProhibitComparisonOfIncompatibleEnums;
    public static final LanguageFeature ProhibitConcurrentHashMapContains;
    public static final LanguageFeature ProhibitConfusingSyntaxInWhenBranches;
    public static final LanguageFeature ProhibitConstructorCallOnFunctionalSupertype;
    public static final LanguageFeature ProhibitCyclesInAnnotations;
    public static final LanguageFeature ProhibitDataClassesOverridingCopy;
    public static final LanguageFeature ProhibitEnumDeclaringClass;
    public static final LanguageFeature ProhibitErroneousExpressionsInAnnotationsWithUseSiteTargets;
    public static final LanguageFeature ProhibitGenericArrayClassLiteral;
    public static final LanguageFeature ProhibitIllegalValueParameterUsageInDefaultArguments;
    public static final LanguageFeature ProhibitImplementingVarByInheritedVal;
    public static final LanguageFeature ProhibitInnerClassesOfGenericClassExtendingThrowable;
    public static final LanguageFeature ProhibitInvalidCharsInNativeIdentifiers;
    public static final LanguageFeature ProhibitInvisibleAbstractMethodsInSuperclasses;
    public static final LanguageFeature ProhibitJvmFieldOnOverrideFromInterfaceInPrimaryConstructor;
    public static final LanguageFeature ProhibitJvmOverloadsOnConstructorsOfAnnotationClasses;
    public static final LanguageFeature ProhibitLocalAnnotations;
    public static final LanguageFeature ProhibitMissedMustBeInitializedWhenThereIsNoPrimaryConstructor;
    public static final LanguageFeature ProhibitNonConstValuesAsVarargsInAnnotations;
    public static final LanguageFeature ProhibitNonExhaustiveIfInRhsOfElvis;
    public static final LanguageFeature ProhibitNonExhaustiveWhenOnAlgebraicTypes;
    public static final LanguageFeature ProhibitNonReifiedArraysAsReifiedTypeArguments;
    public static final LanguageFeature ProhibitOpenValDeferredInitialization;
    public static final LanguageFeature ProhibitOperatorMod;
    public static final LanguageFeature ProhibitProtectedCallFromInline;
    public static final LanguageFeature ProhibitProtectedConstructorCallFromPublicInline;
    public static final LanguageFeature ProhibitQualifiedAccessToUninitializedEnumEntry;
    public static final LanguageFeature ProhibitRepeatedUseSiteTargetAnnotations;
    public static final LanguageFeature ProhibitScriptTopLevelInnerClasses;
    public static final LanguageFeature ProhibitSelfCallsInNestedObjects;
    public static final LanguageFeature ProhibitSimplificationOfNonTrivialConstBooleanExpressions;
    public static final LanguageFeature ProhibitSmartcastsOnLocalDelegatedProperty;
    public static final LanguageFeature ProhibitSmartcastsOnPropertyFromAlienBaseClass;
    public static final LanguageFeature ProhibitSmartcastsOnPropertyFromAlienBaseClassInheritedInInvisibleClass;
    public static final LanguageFeature ProhibitSpreadOnSignaturePolymorphicCall;
    public static final LanguageFeature ProhibitSuperCallsFromPublicInline;
    public static final LanguageFeature ProhibitTailrecOnVirtualMember;
    public static final LanguageFeature ProhibitTypeParametersForLocalVariables;
    public static final LanguageFeature ProhibitTypeParametersInAnonymousObjects;
    public static final LanguageFeature ProhibitTypeParametersInClassLiteralsInAnnotationArguments;
    public static final LanguageFeature ProhibitUseSiteGetTargetAnnotations;
    public static final LanguageFeature ProhibitUseSiteTargetAnnotationsOnSuperTypes;
    public static final LanguageFeature ProhibitUsingNullableTypeParameterAgainstNotNullAnnotated;
    public static final LanguageFeature ProhibitVarargAsArrayAfterSamArgument;
    public static final LanguageFeature ProhibitVisibilityOfNestedClassifiersFromSupertypesOfCompanion;
    public static final LanguageFeature ProperArrayConventionSetterWithDefaultCalls;
    public static final LanguageFeature ProperCheckAnnotationsTargetInTypeUsePositions;
    public static final LanguageFeature ProperComputationOrderOfTailrecDefaultParameters;
    public static final LanguageFeature ProperEqualityChecksInBuilderInferenceCalls;
    public static final LanguageFeature ProperFinally;
    public static final LanguageFeature ProperForInArrayLoopRangeVariableAssignmentSemantic;
    public static final LanguageFeature ProperIeee754Comparisons;
    public static final LanguageFeature ProperInlineFromHigherPlatformDiagnostic;
    public static final LanguageFeature ProperInternalVisibilityCheckInImportingScope;
    public static final LanguageFeature ProperTypeInferenceConstraintsProcessing;
    public static final LanguageFeature ProperVisibilityForCompanionObjectInstanceField;
    public static final LanguageFeature QualifiedSupertypeMayBeExtendedByOtherSupertype;
    public static final LanguageFeature RangeUntilOperator;
    public static final LanguageFeature ReadDeserializedContracts;
    public static final LanguageFeature ReferencesToSyntheticJavaProperties;
    public static final LanguageFeature RefineTypeCheckingOnAssignmentsToJavaFields;
    public static final LanguageFeature RefinedSamAdaptersPriority;
    public static final LanguageFeature ReleaseCoroutines;
    public static final LanguageFeature RepeatableAnnotationContainerConstraints;
    public static final LanguageFeature RepeatableAnnotations;
    public static final LanguageFeature ReportErrorsForComparisonOperators;
    public static final LanguageFeature ReportErrorsOnRecursiveTypeInsidePlusAssignment;
    public static final LanguageFeature ReportMissingUpperBoundsViolatedErrorOnAbbreviationAtSupertypes;
    public static final LanguageFeature ReportNonVarargSpreadOnGenericCalls;
    public static final LanguageFeature ReportTypeVarianceConflictOnQualifierArguments;
    public static final LanguageFeature RequiredPrimaryConstructorDelegationCallInEnums;
    public static final LanguageFeature RestrictRetentionForExpressionAnnotations;
    public static final LanguageFeature RestrictReturnStatementTarget;
    public static final LanguageFeature RestrictionOfValReassignmentViaBackingField;
    public static final LanguageFeature RestrictionOfWrongAnnotationsWithUseSiteTargetsOnTypes;
    public static final LanguageFeature SafeCallBoundSmartCasts;
    public static final LanguageFeature SafeCallsAreAlwaysNullable;
    public static final LanguageFeature SafeCastCheckBoundSmartCasts;
    public static final LanguageFeature SamConversionForKotlinFunctions;
    public static final LanguageFeature SamConversionPerArgument;
    public static final LanguageFeature SamWrapperClassesAreSynthetic;
    public static final LanguageFeature SealedInterfaces;
    public static final LanguageFeature ShortSyntaxForPropertyGetters;
    public static final LanguageFeature SingleUnderscoreForParameterName;
    public static final LanguageFeature SkipStandaloneScriptsInSourceRoots;
    public static final LanguageFeature SoundSmartCastsAfterTry;
    public static final LanguageFeature SoundSmartcastForEnumEntries;
    public static final LanguageFeature StopPropagatingDeprecationThroughOverrides;
    public static final LanguageFeature StrictJavaNullabilityAssertions;
    public static final LanguageFeature StrictOnlyInputTypesChecks;
    public static final LanguageFeature SuspendConversion;
    public static final LanguageFeature SuspendFunctionAsSupertype;
    public static final LanguageFeature SuspendFunctionsInFunInterfaces;
    public static final LanguageFeature SuspendOnlySamConversions;
    public static final LanguageFeature SynchronizedSuspendError;
    public static final LanguageFeature TakeIntoAccountEffectivelyFinalInMustBeInitializedCheck;
    public static final LanguageFeature ThrowNpeOnExplicitEqualsForBoxedNull;
    public static final LanguageFeature TopLevelSealedInheritance;
    public static final LanguageFeature TrailingCommas;
    public static final LanguageFeature TypeAliases = new LanguageFeature("TypeAliases", 0, LanguageVersion.KOTLIN_1_1, null, null, false, null, 30, null);
    public static final LanguageFeature TypeEnhancementImprovementsInStrictMode;
    public static final LanguageFeature TypeInferenceOnCallsWithSelfTypes;
    public static final LanguageFeature TypeInferenceOnGenericsForCallableReferences;
    public static final LanguageFeature UnderscoresInNumericLiterals;
    public static final LanguageFeature UnitConversionsOnArbitraryExpressions;
    public static final LanguageFeature UnrestrictedBuilderInference;
    public static final LanguageFeature UseBuilderInferenceOnlyIfNeeded;
    public static final LanguageFeature UseBuilderInferenceWithoutAnnotation;
    public static final LanguageFeature UseCallsInPlaceEffect;
    public static final LanguageFeature UseConsistentRulesForPrivateConstructorsOfSealedClasses;
    public static final LanguageFeature UseCorrectExecutionOrderForVarargArguments;
    public static final LanguageFeature UseGetterNameForPropertyAnnotationsMethodOnJvm;
    public static final LanguageFeature UseReturnsEffect;
    public static final LanguageFeature ValueClasses;
    public static final LanguageFeature ValueClassesSecondaryConstructorWithBody;
    public static final LanguageFeature VariableDeclarationInWhenSubject;
    public static final LanguageFeature WarnAboutNonExhaustiveWhenOnAlgebraicTypes;
    public static final LanguageFeature WarningOnMainUnusedParameter;
    public static final LanguageFeature YieldIsNoMoreReserved;
    private final String hintUrl;
    private final boolean isEnabledWithWarning;
    private final Kind kind;
    private final ApiVersion sinceApiVersion;
    private final LanguageVersion sinceVersion;

    private static final /* synthetic */ LanguageFeature[] $values() {
        return new LanguageFeature[]{TypeAliases, BoundCallableReferences, LocalDelegatedProperties, TopLevelSealedInheritance, AdditionalBuiltInsMembers, DataClassInheritance, InlineProperties, DestructuringLambdaParameters, SingleUnderscoreForParameterName, DslMarkersSupport, UnderscoresInNumericLiterals, DivisionByZeroInConstantExpressions, InlineConstVals, OperatorRem, OperatorProvideDelegate, ShortSyntaxForPropertyGetters, RefinedSamAdaptersPriority, SafeCallBoundSmartCasts, TypeInferenceOnGenericsForCallableReferences, NoDelegationToJavaDefaultInterfaceMembers, DefaultImportOfPackageKotlinComparisons, Coroutines, InlineDefaultFunctionalParameters, SoundSmartCastsAfterTry, DeprecatedFieldForInvisibleCompanionObject, NullabilityAssertionOnExtensionReceiver, SafeCastCheckBoundSmartCasts, CapturedInClosureSmartCasts, LateinitTopLevelProperties, LateinitLocalVariables, InnerClassInEnumEntryClass, CallableReferencesToClassMembersWithEmptyLHS, ThrowNpeOnExplicitEqualsForBoxedNull, JvmPackageName, AssigningArraysToVarargsInNamedFormInAnnotations, ExpectedTypeFromCast, DefaultMethodsCallFromJava6TargetError, RestrictionOfValReassignmentViaBackingField, NestedClassesInEnumEntryShouldBeInner, ProhibitDataClassesOverridingCopy, RestrictionOfWrongAnnotationsWithUseSiteTargetsOnTypes, ProhibitInnerClassesOfGenericClassExtendingThrowable, ProperForInArrayLoopRangeVariableAssignmentSemantic, NestedClassesInAnnotations, JvmStaticInInterface, JvmFieldInInterface, ProhibitVisibilityOfNestedClassifiersFromSupertypesOfCompanion, ProhibitNonConstValuesAsVarargsInAnnotations, ReleaseCoroutines, ReadDeserializedContracts, UseReturnsEffect, UseCallsInPlaceEffect, AllowContractsForCustomFunctions, VariableDeclarationInWhenSubject, ProhibitLocalAnnotations, ProhibitSmartcastsOnLocalDelegatedProperty, ProhibitOperatorMod, ProhibitAssigningSingleElementsToVarargsInNamedForm, FunctionTypesWithBigArity, RestrictRetentionForExpressionAnnotations, NormalizeConstructorCalls, StrictJavaNullabilityAssertions, SoundSmartcastForEnumEntries, ProhibitErroneousExpressionsInAnnotationsWithUseSiteTargets, NewCapturedReceiverFieldNamingConvention, ExtendedMainConvention, ExperimentalBuilderInference, InlineClasses, DslMarkerOnFunctionTypeReceiver, RestrictReturnStatementTarget, NoConstantValueAttributeForNonConstVals, WarningOnMainUnusedParameter, PolymorphicSignature, ProhibitConcurrentHashMapContains, ProhibitTypeParametersForLocalVariables, ProhibitJvmOverloadsOnConstructorsOfAnnotationClasses, ProhibitTypeParametersInAnonymousObjects, ProperInlineFromHigherPlatformDiagnostic, ProhibitRepeatedUseSiteTargetAnnotations, ProhibitUseSiteTargetAnnotationsOnSuperTypes, ProhibitTypeParametersInClassLiteralsInAnnotationArguments, ProhibitComparisonOfIncompatibleEnums, BareArrayClassLiteral, ProhibitGenericArrayClassLiteral, NonParenthesizedAnnotationsOnFunctionalTypes, UseGetterNameForPropertyAnnotationsMethodOnJvm, AllowBreakAndContinueInsideWhen, MixedNamedArgumentsInTheirOwnPosition, ProhibitTailrecOnVirtualMember, ProperComputationOrderOfTailrecDefaultParameters, TrailingCommas, ProhibitProtectedCallFromInline, ProperFinally, AllowAssigningArrayElementsToVarargsInNamedFormForFunctions, AllowNullOperatorsForResult, PreferJavaFieldOverload, AllowContractsForNonOverridableMembers, AllowReifiedGenericsInContracts, ProperVisibilityForCompanionObjectInstanceField, DoNotGenerateThrowsForDelegatedKotlinMembers, ProperIeee754Comparisons, FunctionalInterfaceConversion, GenerateJvmOverloadsAsFinal, MangleClassMembersReturningInlineClasses, ImproveReportingDiagnosticsOnProtectedMembersOfBaseClass, NewInference, SamConversionForKotlinFunctions, SamConversionPerArgument, FunctionReferenceWithDefaultValueAsOtherType, OverloadResolutionByLambdaReturnType, ContractsOnCallsWithImplicitReceiver, ProhibitSpreadOnSignaturePolymorphicCall, ProhibitInvisibleAbstractMethodsInSuperclasses, ProhibitNonReifiedArraysAsReifiedTypeArguments, ProhibitVarargAsArrayAfterSamArgument, CorrectSourceMappingSyntax, ProperArrayConventionSetterWithDefaultCalls, DisableCompatibilityModeForNewInference, AdaptedCallableReferenceAgainstReflectiveType, InferenceCompatibility, RequiredPrimaryConstructorDelegationCallInEnums, ApproximateAnonymousReturnTypesInPrivateInlineFunctions, ForbidReferencingToUnderscoreNamedParameterOfCatchBlock, UseCorrectExecutionOrderForVarargArguments, JvmRecordSupport, AllowNullOperatorsForResultAndResultReturnTypeByDefault, AllowSealedInheritorsInDifferentFilesOfSamePackage, SealedInterfaces, JvmIrEnabledByDefault, JvmInlineValueClasses, SuspendFunctionsInFunInterfaces, SamWrapperClassesAreSynthetic, StrictOnlyInputTypesChecks, ProhibitJvmFieldOnOverrideFromInterfaceInPrimaryConstructor, PrivateInFileEffectiveVisibility, ProhibitSelfCallsInNestedObjects, ProperCheckAnnotationsTargetInTypeUsePositions, SuspendFunctionAsSupertype, UnrestrictedBuilderInference, ClassTypeParameterAnnotations, TypeInferenceOnCallsWithSelfTypes, WarnAboutNonExhaustiveWhenOnAlgebraicTypes, InstantiationOfAnnotationClasses, OptInContagiousSignatures, RepeatableAnnotations, RepeatableAnnotationContainerConstraints, UseBuilderInferenceOnlyIfNeeded, SuspendConversion, ProhibitSuperCallsFromPublicInline, ProhibitProtectedConstructorCallFromPublicInline, TypeEnhancementImprovementsInStrictMode, OptInRelease, ProhibitNonExhaustiveWhenOnAlgebraicTypes, UseBuilderInferenceWithoutAnnotation, ProhibitSmartcastsOnPropertyFromAlienBaseClass, ProhibitInvalidCharsInNativeIdentifiers, DefinitelyNonNullableTypes, ProhibitSimplificationOfNonTrivialConstBooleanExpressions, SafeCallsAreAlwaysNullable, JvmPermittedSubclassesAttributeForSealed, ProperTypeInferenceConstraintsProcessing, ForbidExposingTypesInPrimaryConstructorProperties, PartiallySpecifiedTypeArguments, EliminateAmbiguitiesWithExternalTypeParameters, EliminateAmbiguitiesOnInheritedSamInterfaces, ConsiderExtensionReceiverFromConstrainsInLambda, ProperInternalVisibilityCheckInImportingScope, InlineClassImplementationByDelegation, QualifiedSupertypeMayBeExtendedByOtherSupertype, YieldIsNoMoreReserved, NoDeprecationOnDeprecatedEnumEntries, ProhibitQualifiedAccessToUninitializedEnumEntry, ForbidRecursiveDelegateExpressions, KotlinFunInterfaceConstructorReference, SuspendOnlySamConversions, DontLoseDiagnosticsDuringOverloadResolutionByReturnType, ProhibitConfusingSyntaxInWhenBranches, UseConsistentRulesForPrivateConstructorsOfSealedClasses, ProgressionsChangingResolve, AbstractClassMemberNotImplementedWithIntermediateAbstractClass, ForbidSuperDelegationToAbstractAnyMethod, ProperEqualityChecksInBuilderInferenceCalls, ProhibitNonExhaustiveIfInRhsOfElvis, ReportMissingUpperBoundsViolatedErrorOnAbbreviationAtSupertypes, ForbidUsingExtensionPropertyTypeParameterInDelegate, SynchronizedSuspendError, ReportNonVarargSpreadOnGenericCalls, RangeUntilOperator, GenericInlineClassParameter, ProhibitIllegalValueParameterUsageInDefaultArguments, ProhibitConstructorCallOnFunctionalSupertype, ProhibitArrayLiteralsInCompanionOfAnnotation, ProhibitCyclesInAnnotations, ForbidExtensionFunctionTypeOnNonFunctionTypes, ProhibitEnumDeclaringClass, StopPropagatingDeprecationThroughOverrides, ReportTypeVarianceConflictOnQualifierArguments, ReportErrorsOnRecursiveTypeInsidePlusAssignment, ForbidExtensionCallsOnInlineFunctionalParameters, SkipStandaloneScriptsInSourceRoots, ModifierNonBuiltinSuspendFunError, EnumEntries, ForbidSuperDelegationToAbstractFakeOverride, DataObjects, ProhibitAccessToEnumCompanionMembersInEnumConstructorCall, RefineTypeCheckingOnAssignmentsToJavaFields, ValueClassesSecondaryConstructorWithBody, NativeJsProhibitLateinitIsInitializedIntrinsicWithoutPrivateAccess, TakeIntoAccountEffectivelyFinalInMustBeInitializedCheck, ProhibitUsingNullableTypeParameterAgainstNotNullAnnotated, NoSourceCodeInNotNullAssertionExceptions, MultiplatformRestrictions, EnhanceNullabilityOfPrimitiveArrays, AllowEmptyIntersectionsInResultTypeResolver, ProhibitSmartcastsOnPropertyFromAlienBaseClassInheritedInInvisibleClass, ForbidInferringPostponedTypeVariableIntoDeclaredUpperBound, ProhibitUseSiteGetTargetAnnotations, KeepNullabilityWhenApproximatingLocalType, ProhibitAccessToInvisibleSetterFromDerivedClass, ProhibitOpenValDeferredInitialization, ProhibitMissedMustBeInitializedWhenThereIsNoPrimaryConstructor, MangleCallsToJavaMethodsWithValueClasses, ForbidInferringTypeVariablesIntoEmptyIntersection, ReferencesToSyntheticJavaProperties, ProhibitImplementingVarByInheritedVal, PrioritizedEnumEntries, ExpectActualClasses, IgnoreNullabilityForErasedValueParameters, NoBuilderInferenceWithoutAnnotationRestriction, ReportErrorsForComparisonOperators, NoAdditionalErrorsInK1DiagnosticReporter, ProhibitScriptTopLevelInnerClasses, BreakContinueInInlineLambdas, LightweightLambdas, JsEnableExtensionFunctionInExternals, PackagePrivateFileClassesWithAllPrivateMembers, BooleanElvisBoundSmartCasts, NewDataFlowForTryExpressions, AllowResultInReturnType, MultiPlatformProjects, ProhibitComparisonOfIncompatibleClasses, ExplicitBackingFields, FunctionalTypeWithExtensionAsSupertype, JsAllowInvalidCharsIdentifiersEscaping, JsAllowValueClassesInExternals, ContextReceivers, ValueClasses, JavaSamConversionEqualsHashCode, UnitConversionsOnArbitraryExpressions, JsAllowImplementingFunctionInterface, CustomEqualsInValueClasses, InlineLateinit, EnableDfaWarningsInK2, ContractSyntaxV2, ImplicitSignedToUnsignedIntegerConversion, IntrinsicConstEvaluation, DisableCheckingChangedProgressionsResolve, ContextSensitiveEnumResolutionInWhen};
    }

    @JvmStatic
    public static final LanguageFeature fromString(String str) {
        return INSTANCE.fromString(str);
    }

    public static EnumEntries<LanguageFeature> getEntries() {
        return $ENTRIES;
    }

    public static LanguageFeature valueOf(String str) {
        return (LanguageFeature) Enum.valueOf(LanguageFeature.class, str);
    }

    public static LanguageFeature[] values() {
        return (LanguageFeature[]) $VALUES.clone();
    }

    private LanguageFeature(String str, int i, LanguageVersion languageVersion, ApiVersion apiVersion, String str2, boolean z, Kind kind) {
        super(str, i);
        this.sinceVersion = languageVersion;
        this.sinceApiVersion = apiVersion;
        this.hintUrl = str2;
        this.isEnabledWithWarning = z;
        this.kind = kind;
        if (languageVersion == null && z) {
            throw new IllegalStateException((this + ": 'isEnabledWithWarning' has no effect if the feature is disabled by default").toString());
        }
    }

    public final LanguageVersion getSinceVersion() {
        return this.sinceVersion;
    }

    /* synthetic */ LanguageFeature(String str, int i, LanguageVersion languageVersion, ApiVersion apiVersion, String str2, boolean z, Kind kind, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, languageVersion, (i2 & 2) != 0 ? ApiVersion.KOTLIN_1_0 : apiVersion, (i2 & 4) != 0 ? null : str2, (i2 & 8) != 0 ? false : z, (i2 & 16) != 0 ? Kind.OTHER : kind);
    }

    public final ApiVersion getSinceApiVersion() {
        return this.sinceApiVersion;
    }

    public final String getHintUrl() {
        return this.hintUrl;
    }

    /* JADX INFO: renamed from: isEnabledWithWarning$util, reason: from getter */
    public final boolean getIsEnabledWithWarning() {
        return this.isEnabledWithWarning;
    }

    public final Kind getKind() {
        return this.kind;
    }

    static {
        DefaultConstructorMarker defaultConstructorMarker = null;
        String str = null;
        boolean z = false;
        Kind kind = null;
        BoundCallableReferences = new LanguageFeature("BoundCallableReferences", 1, LanguageVersion.KOTLIN_1_1, ApiVersion.KOTLIN_1_1, str, z, kind, 28, defaultConstructorMarker);
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        String str2 = null;
        boolean z2 = false;
        Kind kind2 = null;
        LocalDelegatedProperties = new LanguageFeature("LocalDelegatedProperties", 2, LanguageVersion.KOTLIN_1_1, ApiVersion.KOTLIN_1_1, str2, z2, kind2, 28, defaultConstructorMarker2);
        int i = 30;
        ApiVersion apiVersion = null;
        TopLevelSealedInheritance = new LanguageFeature("TopLevelSealedInheritance", 3, LanguageVersion.KOTLIN_1_1, apiVersion, str, z, kind, i, defaultConstructorMarker);
        int i2 = 30;
        ApiVersion apiVersion2 = null;
        AdditionalBuiltInsMembers = new LanguageFeature("AdditionalBuiltInsMembers", 4, LanguageVersion.KOTLIN_1_1, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        DataClassInheritance = new LanguageFeature("DataClassInheritance", 5, LanguageVersion.KOTLIN_1_1, apiVersion, str, z, kind, i, defaultConstructorMarker);
        InlineProperties = new LanguageFeature("InlineProperties", 6, LanguageVersion.KOTLIN_1_1, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        DestructuringLambdaParameters = new LanguageFeature("DestructuringLambdaParameters", 7, LanguageVersion.KOTLIN_1_1, apiVersion, str, z, kind, i, defaultConstructorMarker);
        SingleUnderscoreForParameterName = new LanguageFeature("SingleUnderscoreForParameterName", 8, LanguageVersion.KOTLIN_1_1, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        DslMarkersSupport = new LanguageFeature("DslMarkersSupport", 9, LanguageVersion.KOTLIN_1_1, apiVersion, str, z, kind, i, defaultConstructorMarker);
        UnderscoresInNumericLiterals = new LanguageFeature("UnderscoresInNumericLiterals", 10, LanguageVersion.KOTLIN_1_1, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        DivisionByZeroInConstantExpressions = new LanguageFeature("DivisionByZeroInConstantExpressions", 11, LanguageVersion.KOTLIN_1_1, apiVersion, str, z, kind, i, defaultConstructorMarker);
        InlineConstVals = new LanguageFeature("InlineConstVals", 12, LanguageVersion.KOTLIN_1_1, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        OperatorRem = new LanguageFeature("OperatorRem", 13, LanguageVersion.KOTLIN_1_1, apiVersion, str, z, kind, i, defaultConstructorMarker);
        OperatorProvideDelegate = new LanguageFeature("OperatorProvideDelegate", 14, LanguageVersion.KOTLIN_1_1, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        ShortSyntaxForPropertyGetters = new LanguageFeature("ShortSyntaxForPropertyGetters", 15, LanguageVersion.KOTLIN_1_1, apiVersion, str, z, kind, i, defaultConstructorMarker);
        RefinedSamAdaptersPriority = new LanguageFeature("RefinedSamAdaptersPriority", 16, LanguageVersion.KOTLIN_1_1, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        SafeCallBoundSmartCasts = new LanguageFeature("SafeCallBoundSmartCasts", 17, LanguageVersion.KOTLIN_1_1, apiVersion, str, z, kind, i, defaultConstructorMarker);
        TypeInferenceOnGenericsForCallableReferences = new LanguageFeature("TypeInferenceOnGenericsForCallableReferences", 18, LanguageVersion.KOTLIN_1_1, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        NoDelegationToJavaDefaultInterfaceMembers = new LanguageFeature("NoDelegationToJavaDefaultInterfaceMembers", 19, LanguageVersion.KOTLIN_1_1, apiVersion, str, z, kind, i, defaultConstructorMarker);
        DefaultImportOfPackageKotlinComparisons = new LanguageFeature("DefaultImportOfPackageKotlinComparisons", 20, LanguageVersion.KOTLIN_1_1, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        Coroutines = new LanguageFeature("Coroutines", 21, LanguageVersion.KOTLIN_1_1, ApiVersion.KOTLIN_1_1, "https://kotlinlang.org/docs/diagnostics/experimental-coroutines", true, kind, 16, defaultConstructorMarker);
        InlineDefaultFunctionalParameters = new LanguageFeature("InlineDefaultFunctionalParameters", 22, LanguageVersion.KOTLIN_1_2, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        int i3 = 30;
        ApiVersion apiVersion3 = null;
        String str3 = null;
        boolean z3 = false;
        SoundSmartCastsAfterTry = new LanguageFeature("SoundSmartCastsAfterTry", 23, LanguageVersion.KOTLIN_1_2, apiVersion3, str3, z3, kind, i3, defaultConstructorMarker);
        DeprecatedFieldForInvisibleCompanionObject = new LanguageFeature("DeprecatedFieldForInvisibleCompanionObject", 24, LanguageVersion.KOTLIN_1_2, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        NullabilityAssertionOnExtensionReceiver = new LanguageFeature("NullabilityAssertionOnExtensionReceiver", 25, LanguageVersion.KOTLIN_1_2, apiVersion3, str3, z3, kind, i3, defaultConstructorMarker);
        SafeCastCheckBoundSmartCasts = new LanguageFeature("SafeCastCheckBoundSmartCasts", 26, LanguageVersion.KOTLIN_1_2, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        CapturedInClosureSmartCasts = new LanguageFeature("CapturedInClosureSmartCasts", 27, LanguageVersion.KOTLIN_1_2, apiVersion3, str3, z3, kind, i3, defaultConstructorMarker);
        LateinitTopLevelProperties = new LanguageFeature("LateinitTopLevelProperties", 28, LanguageVersion.KOTLIN_1_2, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        LateinitLocalVariables = new LanguageFeature("LateinitLocalVariables", 29, LanguageVersion.KOTLIN_1_2, apiVersion3, str3, z3, kind, i3, defaultConstructorMarker);
        InnerClassInEnumEntryClass = new LanguageFeature("InnerClassInEnumEntryClass", 30, LanguageVersion.KOTLIN_1_2, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        CallableReferencesToClassMembersWithEmptyLHS = new LanguageFeature("CallableReferencesToClassMembersWithEmptyLHS", 31, LanguageVersion.KOTLIN_1_2, apiVersion3, str3, z3, kind, i3, defaultConstructorMarker);
        ThrowNpeOnExplicitEqualsForBoxedNull = new LanguageFeature("ThrowNpeOnExplicitEqualsForBoxedNull", 32, LanguageVersion.KOTLIN_1_2, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        JvmPackageName = new LanguageFeature("JvmPackageName", 33, LanguageVersion.KOTLIN_1_2, apiVersion3, str3, z3, kind, i3, defaultConstructorMarker);
        AssigningArraysToVarargsInNamedFormInAnnotations = new LanguageFeature("AssigningArraysToVarargsInNamedFormInAnnotations", 34, LanguageVersion.KOTLIN_1_2, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        ExpectedTypeFromCast = new LanguageFeature("ExpectedTypeFromCast", 35, LanguageVersion.KOTLIN_1_2, apiVersion3, str3, z3, kind, i3, defaultConstructorMarker);
        DefaultMethodsCallFromJava6TargetError = new LanguageFeature("DefaultMethodsCallFromJava6TargetError", 36, LanguageVersion.KOTLIN_1_2, apiVersion2, str2, z2, kind2, i2, defaultConstructorMarker2);
        int i4 = 14;
        RestrictionOfValReassignmentViaBackingField = new LanguageFeature("RestrictionOfValReassignmentViaBackingField", 37, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, Kind.BUG_FIX, i4, defaultConstructorMarker);
        int i5 = 14;
        NestedClassesInEnumEntryShouldBeInner = new LanguageFeature("NestedClassesInEnumEntryShouldBeInner", 38, LanguageVersion.KOTLIN_1_3, apiVersion2, str2, z2, Kind.BUG_FIX, i5, defaultConstructorMarker2);
        ProhibitDataClassesOverridingCopy = new LanguageFeature("ProhibitDataClassesOverridingCopy", 39, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, Kind.BUG_FIX, i4, defaultConstructorMarker);
        RestrictionOfWrongAnnotationsWithUseSiteTargetsOnTypes = new LanguageFeature("RestrictionOfWrongAnnotationsWithUseSiteTargetsOnTypes", 40, LanguageVersion.KOTLIN_1_3, apiVersion2, str2, z2, Kind.BUG_FIX, i5, defaultConstructorMarker2);
        ProhibitInnerClassesOfGenericClassExtendingThrowable = new LanguageFeature("ProhibitInnerClassesOfGenericClassExtendingThrowable", 41, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, Kind.BUG_FIX, i4, defaultConstructorMarker);
        ProperForInArrayLoopRangeVariableAssignmentSemantic = new LanguageFeature("ProperForInArrayLoopRangeVariableAssignmentSemantic", 42, LanguageVersion.KOTLIN_1_3, apiVersion2, str2, z2, Kind.BUG_FIX, i5, defaultConstructorMarker2);
        NestedClassesInAnnotations = new LanguageFeature("NestedClassesInAnnotations", 43, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, null, 30, defaultConstructorMarker);
        JvmStaticInInterface = new LanguageFeature("JvmStaticInInterface", 44, LanguageVersion.KOTLIN_1_3, apiVersion2, str2, z2, Kind.UNSTABLE_FEATURE, i5, defaultConstructorMarker2);
        int i6 = 14;
        JvmFieldInInterface = new LanguageFeature("JvmFieldInInterface", 45, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, Kind.UNSTABLE_FEATURE, i6, defaultConstructorMarker);
        ProhibitVisibilityOfNestedClassifiersFromSupertypesOfCompanion = new LanguageFeature("ProhibitVisibilityOfNestedClassifiersFromSupertypesOfCompanion", 46, LanguageVersion.KOTLIN_1_3, apiVersion2, str2, z2, Kind.BUG_FIX, i5, defaultConstructorMarker2);
        ProhibitNonConstValuesAsVarargsInAnnotations = new LanguageFeature("ProhibitNonConstValuesAsVarargsInAnnotations", 47, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, Kind.BUG_FIX, i6, defaultConstructorMarker);
        ReleaseCoroutines = new LanguageFeature("ReleaseCoroutines", 48, LanguageVersion.KOTLIN_1_3, apiVersion2, str2, z2, Kind.UNSTABLE_FEATURE, i5, defaultConstructorMarker2);
        int i7 = 30;
        Kind kind3 = null;
        ReadDeserializedContracts = new LanguageFeature("ReadDeserializedContracts", 49, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, kind3, i7, defaultConstructorMarker);
        int i8 = 30;
        Kind kind4 = null;
        UseReturnsEffect = new LanguageFeature("UseReturnsEffect", 50, LanguageVersion.KOTLIN_1_3, apiVersion2, str2, z2, kind4, i8, defaultConstructorMarker2);
        UseCallsInPlaceEffect = new LanguageFeature("UseCallsInPlaceEffect", 51, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, kind3, i7, defaultConstructorMarker);
        AllowContractsForCustomFunctions = new LanguageFeature("AllowContractsForCustomFunctions", 52, LanguageVersion.KOTLIN_1_3, apiVersion2, str2, z2, kind4, i8, defaultConstructorMarker2);
        VariableDeclarationInWhenSubject = new LanguageFeature("VariableDeclarationInWhenSubject", 53, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, kind3, i7, defaultConstructorMarker);
        int i9 = 14;
        ProhibitLocalAnnotations = new LanguageFeature("ProhibitLocalAnnotations", 54, LanguageVersion.KOTLIN_1_3, apiVersion2, str2, z2, Kind.BUG_FIX, i9, defaultConstructorMarker2);
        int i10 = 14;
        ProhibitSmartcastsOnLocalDelegatedProperty = new LanguageFeature("ProhibitSmartcastsOnLocalDelegatedProperty", 55, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, Kind.BUG_FIX, i10, defaultConstructorMarker);
        ProhibitOperatorMod = new LanguageFeature("ProhibitOperatorMod", 56, LanguageVersion.KOTLIN_1_3, apiVersion2, str2, z2, Kind.BUG_FIX, i9, defaultConstructorMarker2);
        ProhibitAssigningSingleElementsToVarargsInNamedForm = new LanguageFeature("ProhibitAssigningSingleElementsToVarargsInNamedForm", 57, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, Kind.BUG_FIX, i10, defaultConstructorMarker);
        Kind kind5 = null;
        FunctionTypesWithBigArity = new LanguageFeature("FunctionTypesWithBigArity", 58, LanguageVersion.KOTLIN_1_3, ApiVersion.KOTLIN_1_3, str2, z2, kind5, 28, defaultConstructorMarker2);
        RestrictRetentionForExpressionAnnotations = new LanguageFeature("RestrictRetentionForExpressionAnnotations", 59, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, Kind.BUG_FIX, i10, defaultConstructorMarker);
        ApiVersion apiVersion4 = null;
        NormalizeConstructorCalls = new LanguageFeature("NormalizeConstructorCalls", 60, LanguageVersion.KOTLIN_1_3, apiVersion4, str2, z2, kind5, 30, defaultConstructorMarker2);
        StrictJavaNullabilityAssertions = new LanguageFeature("StrictJavaNullabilityAssertions", 61, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, Kind.BUG_FIX, i10, defaultConstructorMarker);
        int i11 = 14;
        SoundSmartcastForEnumEntries = new LanguageFeature("SoundSmartcastForEnumEntries", 62, LanguageVersion.KOTLIN_1_3, apiVersion4, str2, z2, Kind.BUG_FIX, i11, defaultConstructorMarker2);
        ProhibitErroneousExpressionsInAnnotationsWithUseSiteTargets = new LanguageFeature("ProhibitErroneousExpressionsInAnnotationsWithUseSiteTargets", 63, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, Kind.BUG_FIX, i10, defaultConstructorMarker);
        NewCapturedReceiverFieldNamingConvention = new LanguageFeature("NewCapturedReceiverFieldNamingConvention", 64, LanguageVersion.KOTLIN_1_3, apiVersion4, str2, z2, Kind.BUG_FIX, i11, defaultConstructorMarker2);
        ExtendedMainConvention = new LanguageFeature("ExtendedMainConvention", 65, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, z3, null, 30, defaultConstructorMarker);
        ExperimentalBuilderInference = new LanguageFeature("ExperimentalBuilderInference", 66, LanguageVersion.KOTLIN_1_3, apiVersion4, str2, z2, null, 30, defaultConstructorMarker2);
        InlineClasses = new LanguageFeature("InlineClasses", 67, LanguageVersion.KOTLIN_1_3, apiVersion3, str3, true, Kind.UNSTABLE_FEATURE, 6, defaultConstructorMarker);
        int i12 = 14;
        DslMarkerOnFunctionTypeReceiver = new LanguageFeature("DslMarkerOnFunctionTypeReceiver", 68, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, Kind.BUG_FIX, i12, defaultConstructorMarker2);
        boolean z4 = false;
        RestrictReturnStatementTarget = new LanguageFeature("RestrictReturnStatementTarget", 69, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, Kind.BUG_FIX, 14, defaultConstructorMarker);
        NoConstantValueAttributeForNonConstVals = new LanguageFeature("NoConstantValueAttributeForNonConstVals", 70, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, Kind.BUG_FIX, i12, defaultConstructorMarker2);
        WarningOnMainUnusedParameter = new LanguageFeature("WarningOnMainUnusedParameter", 71, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, null, 30, defaultConstructorMarker);
        PolymorphicSignature = new LanguageFeature("PolymorphicSignature", 72, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, null, 30, defaultConstructorMarker2);
        int i13 = 14;
        ProhibitConcurrentHashMapContains = new LanguageFeature("ProhibitConcurrentHashMapContains", 73, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, Kind.BUG_FIX, i13, defaultConstructorMarker);
        int i14 = 14;
        ProhibitTypeParametersForLocalVariables = new LanguageFeature("ProhibitTypeParametersForLocalVariables", 74, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, Kind.BUG_FIX, i14, defaultConstructorMarker2);
        ProhibitJvmOverloadsOnConstructorsOfAnnotationClasses = new LanguageFeature("ProhibitJvmOverloadsOnConstructorsOfAnnotationClasses", 75, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, Kind.BUG_FIX, i13, defaultConstructorMarker);
        ProhibitTypeParametersInAnonymousObjects = new LanguageFeature("ProhibitTypeParametersInAnonymousObjects", 76, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, Kind.BUG_FIX, i14, defaultConstructorMarker2);
        ProperInlineFromHigherPlatformDiagnostic = new LanguageFeature("ProperInlineFromHigherPlatformDiagnostic", 77, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, Kind.BUG_FIX, i13, defaultConstructorMarker);
        ProhibitRepeatedUseSiteTargetAnnotations = new LanguageFeature("ProhibitRepeatedUseSiteTargetAnnotations", 78, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, Kind.BUG_FIX, i14, defaultConstructorMarker2);
        ProhibitUseSiteTargetAnnotationsOnSuperTypes = new LanguageFeature("ProhibitUseSiteTargetAnnotationsOnSuperTypes", 79, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, Kind.BUG_FIX, i13, defaultConstructorMarker);
        ProhibitTypeParametersInClassLiteralsInAnnotationArguments = new LanguageFeature("ProhibitTypeParametersInClassLiteralsInAnnotationArguments", 80, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, Kind.BUG_FIX, i14, defaultConstructorMarker2);
        ProhibitComparisonOfIncompatibleEnums = new LanguageFeature("ProhibitComparisonOfIncompatibleEnums", 81, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, Kind.BUG_FIX, i13, defaultConstructorMarker);
        int i15 = 30;
        Kind kind6 = null;
        BareArrayClassLiteral = new LanguageFeature("BareArrayClassLiteral", 82, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, kind6, i15, defaultConstructorMarker2);
        int i16 = 30;
        Kind kind7 = null;
        ProhibitGenericArrayClassLiteral = new LanguageFeature("ProhibitGenericArrayClassLiteral", 83, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind7, i16, defaultConstructorMarker);
        NonParenthesizedAnnotationsOnFunctionalTypes = new LanguageFeature("NonParenthesizedAnnotationsOnFunctionalTypes", 84, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, kind6, i15, defaultConstructorMarker2);
        UseGetterNameForPropertyAnnotationsMethodOnJvm = new LanguageFeature("UseGetterNameForPropertyAnnotationsMethodOnJvm", 85, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind7, i16, defaultConstructorMarker);
        AllowBreakAndContinueInsideWhen = new LanguageFeature("AllowBreakAndContinueInsideWhen", 86, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, kind6, i15, defaultConstructorMarker2);
        MixedNamedArgumentsInTheirOwnPosition = new LanguageFeature("MixedNamedArgumentsInTheirOwnPosition", 87, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind7, i16, defaultConstructorMarker);
        ProhibitTailrecOnVirtualMember = new LanguageFeature("ProhibitTailrecOnVirtualMember", 88, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, Kind.BUG_FIX, 14, defaultConstructorMarker2);
        ProperComputationOrderOfTailrecDefaultParameters = new LanguageFeature("ProperComputationOrderOfTailrecDefaultParameters", 89, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind7, i16, defaultConstructorMarker);
        TrailingCommas = new LanguageFeature("TrailingCommas", 90, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, null, 30, defaultConstructorMarker2);
        ProhibitProtectedCallFromInline = new LanguageFeature("ProhibitProtectedCallFromInline", 91, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, Kind.BUG_FIX, 14, defaultConstructorMarker);
        ProperFinally = new LanguageFeature("ProperFinally", 92, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, Kind.BUG_FIX, 14, defaultConstructorMarker2);
        int i17 = 30;
        Kind kind8 = null;
        AllowAssigningArrayElementsToVarargsInNamedFormForFunctions = new LanguageFeature("AllowAssigningArrayElementsToVarargsInNamedFormForFunctions", 93, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind8, i17, defaultConstructorMarker);
        int i18 = 30;
        Kind kind9 = null;
        AllowNullOperatorsForResult = new LanguageFeature("AllowNullOperatorsForResult", 94, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, kind9, i18, defaultConstructorMarker2);
        PreferJavaFieldOverload = new LanguageFeature("PreferJavaFieldOverload", 95, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind8, i17, defaultConstructorMarker);
        AllowContractsForNonOverridableMembers = new LanguageFeature("AllowContractsForNonOverridableMembers", 96, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, kind9, i18, defaultConstructorMarker2);
        AllowReifiedGenericsInContracts = new LanguageFeature("AllowReifiedGenericsInContracts", 97, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind8, i17, defaultConstructorMarker);
        int i19 = 14;
        ProperVisibilityForCompanionObjectInstanceField = new LanguageFeature("ProperVisibilityForCompanionObjectInstanceField", 98, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, Kind.BUG_FIX, i19, defaultConstructorMarker2);
        DoNotGenerateThrowsForDelegatedKotlinMembers = new LanguageFeature("DoNotGenerateThrowsForDelegatedKotlinMembers", 99, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind8, i17, defaultConstructorMarker);
        ProperIeee754Comparisons = new LanguageFeature("ProperIeee754Comparisons", 100, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, Kind.BUG_FIX, i19, defaultConstructorMarker2);
        FunctionalInterfaceConversion = new LanguageFeature("FunctionalInterfaceConversion", 101, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, Kind.UNSTABLE_FEATURE, 14, defaultConstructorMarker);
        GenerateJvmOverloadsAsFinal = new LanguageFeature("GenerateJvmOverloadsAsFinal", 102, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, null, 30, defaultConstructorMarker2);
        int i20 = 30;
        Kind kind10 = null;
        MangleClassMembersReturningInlineClasses = new LanguageFeature("MangleClassMembersReturningInlineClasses", 103, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind10, i20, defaultConstructorMarker);
        ImproveReportingDiagnosticsOnProtectedMembersOfBaseClass = new LanguageFeature("ImproveReportingDiagnosticsOnProtectedMembersOfBaseClass", 104, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, Kind.BUG_FIX, 14, defaultConstructorMarker2);
        NewInference = new LanguageFeature("NewInference", 105, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind10, i20, defaultConstructorMarker);
        int i21 = 30;
        Kind kind11 = null;
        SamConversionForKotlinFunctions = new LanguageFeature("SamConversionForKotlinFunctions", 106, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, kind11, i21, defaultConstructorMarker2);
        SamConversionPerArgument = new LanguageFeature("SamConversionPerArgument", 107, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind10, i20, defaultConstructorMarker);
        FunctionReferenceWithDefaultValueAsOtherType = new LanguageFeature("FunctionReferenceWithDefaultValueAsOtherType", 108, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, kind11, i21, defaultConstructorMarker2);
        OverloadResolutionByLambdaReturnType = new LanguageFeature("OverloadResolutionByLambdaReturnType", 109, LanguageVersion.KOTLIN_1_4, apiVersion3, str3, z4, kind10, i20, defaultConstructorMarker);
        ContractsOnCallsWithImplicitReceiver = new LanguageFeature("ContractsOnCallsWithImplicitReceiver", 110, LanguageVersion.KOTLIN_1_4, apiVersion4, str2, z2, kind11, i21, defaultConstructorMarker2);
        int i22 = 14;
        ProhibitSpreadOnSignaturePolymorphicCall = new LanguageFeature("ProhibitSpreadOnSignaturePolymorphicCall", 111, LanguageVersion.KOTLIN_1_5, apiVersion3, str3, z4, Kind.BUG_FIX, i22, defaultConstructorMarker);
        int i23 = 14;
        ProhibitInvisibleAbstractMethodsInSuperclasses = new LanguageFeature("ProhibitInvisibleAbstractMethodsInSuperclasses", 112, LanguageVersion.KOTLIN_1_5, apiVersion4, str2, z2, Kind.BUG_FIX, i23, defaultConstructorMarker2);
        ProhibitNonReifiedArraysAsReifiedTypeArguments = new LanguageFeature("ProhibitNonReifiedArraysAsReifiedTypeArguments", 113, LanguageVersion.KOTLIN_1_5, apiVersion3, str3, z4, Kind.BUG_FIX, i22, defaultConstructorMarker);
        ProhibitVarargAsArrayAfterSamArgument = new LanguageFeature("ProhibitVarargAsArrayAfterSamArgument", 114, LanguageVersion.KOTLIN_1_5, apiVersion4, str2, z2, Kind.BUG_FIX, i23, defaultConstructorMarker2);
        CorrectSourceMappingSyntax = new LanguageFeature("CorrectSourceMappingSyntax", 115, LanguageVersion.KOTLIN_1_5, apiVersion3, str3, z4, Kind.UNSTABLE_FEATURE, i22, defaultConstructorMarker);
        ProperArrayConventionSetterWithDefaultCalls = new LanguageFeature("ProperArrayConventionSetterWithDefaultCalls", 116, LanguageVersion.KOTLIN_1_5, apiVersion4, str2, z2, Kind.OTHER, i23, defaultConstructorMarker2);
        DisableCompatibilityModeForNewInference = new LanguageFeature("DisableCompatibilityModeForNewInference", 117, null, apiVersion3, str3, z4, null, 30, defaultConstructorMarker);
        AdaptedCallableReferenceAgainstReflectiveType = new LanguageFeature("AdaptedCallableReferenceAgainstReflectiveType", 118, null, apiVersion4, str2, z2, null, 30, defaultConstructorMarker2);
        int i24 = 14;
        InferenceCompatibility = new LanguageFeature("InferenceCompatibility", 119, LanguageVersion.KOTLIN_1_5, apiVersion3, str3, z4, Kind.BUG_FIX, i24, defaultConstructorMarker);
        int i25 = 14;
        RequiredPrimaryConstructorDelegationCallInEnums = new LanguageFeature("RequiredPrimaryConstructorDelegationCallInEnums", 120, LanguageVersion.KOTLIN_1_5, apiVersion4, str2, z2, Kind.BUG_FIX, i25, defaultConstructorMarker2);
        ApproximateAnonymousReturnTypesInPrivateInlineFunctions = new LanguageFeature("ApproximateAnonymousReturnTypesInPrivateInlineFunctions", 121, LanguageVersion.KOTLIN_1_5, apiVersion3, str3, z4, Kind.BUG_FIX, i24, defaultConstructorMarker);
        ForbidReferencingToUnderscoreNamedParameterOfCatchBlock = new LanguageFeature("ForbidReferencingToUnderscoreNamedParameterOfCatchBlock", 122, LanguageVersion.KOTLIN_1_5, apiVersion4, str2, z2, Kind.BUG_FIX, i25, defaultConstructorMarker2);
        UseCorrectExecutionOrderForVarargArguments = new LanguageFeature("UseCorrectExecutionOrderForVarargArguments", 123, LanguageVersion.KOTLIN_1_5, apiVersion3, str3, z4, Kind.BUG_FIX, i24, defaultConstructorMarker);
        int i26 = 30;
        Kind kind12 = null;
        JvmRecordSupport = new LanguageFeature("JvmRecordSupport", 124, LanguageVersion.KOTLIN_1_5, apiVersion4, str2, z2, kind12, i26, defaultConstructorMarker2);
        int i27 = 30;
        Kind kind13 = null;
        AllowNullOperatorsForResultAndResultReturnTypeByDefault = new LanguageFeature("AllowNullOperatorsForResultAndResultReturnTypeByDefault", 125, LanguageVersion.KOTLIN_1_5, apiVersion3, str3, z4, kind13, i27, defaultConstructorMarker);
        AllowSealedInheritorsInDifferentFilesOfSamePackage = new LanguageFeature("AllowSealedInheritorsInDifferentFilesOfSamePackage", 126, LanguageVersion.KOTLIN_1_5, apiVersion4, str2, z2, kind12, i26, defaultConstructorMarker2);
        SealedInterfaces = new LanguageFeature("SealedInterfaces", 127, LanguageVersion.KOTLIN_1_5, apiVersion3, str3, z4, kind13, i27, defaultConstructorMarker);
        JvmIrEnabledByDefault = new LanguageFeature("JvmIrEnabledByDefault", 128, LanguageVersion.KOTLIN_1_5, apiVersion4, str2, z2, kind12, i26, defaultConstructorMarker2);
        int i28 = 14;
        JvmInlineValueClasses = new LanguageFeature("JvmInlineValueClasses", 129, LanguageVersion.KOTLIN_1_5, apiVersion3, str3, z4, Kind.OTHER, i28, defaultConstructorMarker);
        SuspendFunctionsInFunInterfaces = new LanguageFeature("SuspendFunctionsInFunInterfaces", 130, LanguageVersion.KOTLIN_1_5, apiVersion4, str2, z2, Kind.OTHER, 14, defaultConstructorMarker2);
        SamWrapperClassesAreSynthetic = new LanguageFeature("SamWrapperClassesAreSynthetic", 131, LanguageVersion.KOTLIN_1_5, apiVersion3, str3, z4, Kind.BUG_FIX, i28, defaultConstructorMarker);
        StrictOnlyInputTypesChecks = new LanguageFeature("StrictOnlyInputTypesChecks", 132, LanguageVersion.KOTLIN_1_5, apiVersion4, str2, z2, null, 30, defaultConstructorMarker2);
        ProhibitJvmFieldOnOverrideFromInterfaceInPrimaryConstructor = new LanguageFeature("ProhibitJvmFieldOnOverrideFromInterfaceInPrimaryConstructor", 133, LanguageVersion.KOTLIN_1_6, apiVersion3, str3, z4, Kind.BUG_FIX, i28, defaultConstructorMarker);
        int i29 = 14;
        PrivateInFileEffectiveVisibility = new LanguageFeature("PrivateInFileEffectiveVisibility", 134, LanguageVersion.KOTLIN_1_6, apiVersion4, str2, z2, Kind.BUG_FIX, i29, defaultConstructorMarker2);
        ProhibitSelfCallsInNestedObjects = new LanguageFeature("ProhibitSelfCallsInNestedObjects", 135, LanguageVersion.KOTLIN_1_6, apiVersion3, str3, z4, Kind.BUG_FIX, i28, defaultConstructorMarker);
        ProperCheckAnnotationsTargetInTypeUsePositions = new LanguageFeature("ProperCheckAnnotationsTargetInTypeUsePositions", 136, LanguageVersion.KOTLIN_1_6, apiVersion4, str2, z2, Kind.BUG_FIX, i29, defaultConstructorMarker2);
        int i30 = 30;
        Kind kind14 = null;
        SuspendFunctionAsSupertype = new LanguageFeature("SuspendFunctionAsSupertype", 137, LanguageVersion.KOTLIN_1_6, apiVersion3, str3, z4, kind14, i30, defaultConstructorMarker);
        int i31 = 30;
        Kind kind15 = null;
        UnrestrictedBuilderInference = new LanguageFeature("UnrestrictedBuilderInference", 138, LanguageVersion.KOTLIN_1_6, apiVersion4, str2, z2, kind15, i31, defaultConstructorMarker2);
        ClassTypeParameterAnnotations = new LanguageFeature("ClassTypeParameterAnnotations", 139, LanguageVersion.KOTLIN_1_6, apiVersion3, str3, z4, kind14, i30, defaultConstructorMarker);
        TypeInferenceOnCallsWithSelfTypes = new LanguageFeature("TypeInferenceOnCallsWithSelfTypes", 140, LanguageVersion.KOTLIN_1_6, apiVersion4, str2, z2, kind15, i31, defaultConstructorMarker2);
        int i32 = 14;
        WarnAboutNonExhaustiveWhenOnAlgebraicTypes = new LanguageFeature("WarnAboutNonExhaustiveWhenOnAlgebraicTypes", 141, LanguageVersion.KOTLIN_1_6, apiVersion3, str3, z4, Kind.BUG_FIX, i32, defaultConstructorMarker);
        InstantiationOfAnnotationClasses = new LanguageFeature("InstantiationOfAnnotationClasses", 142, LanguageVersion.KOTLIN_1_6, apiVersion4, str2, z2, kind15, i31, defaultConstructorMarker2);
        OptInContagiousSignatures = new LanguageFeature("OptInContagiousSignatures", 143, LanguageVersion.KOTLIN_1_6, apiVersion3, str3, z4, Kind.BUG_FIX, i32, defaultConstructorMarker);
        RepeatableAnnotations = new LanguageFeature("RepeatableAnnotations", 144, LanguageVersion.KOTLIN_1_6, apiVersion4, str2, z2, kind15, i31, defaultConstructorMarker2);
        RepeatableAnnotationContainerConstraints = new LanguageFeature("RepeatableAnnotationContainerConstraints", 145, LanguageVersion.KOTLIN_1_6, apiVersion3, str3, z4, Kind.BUG_FIX, i32, defaultConstructorMarker);
        UseBuilderInferenceOnlyIfNeeded = new LanguageFeature("UseBuilderInferenceOnlyIfNeeded", 146, LanguageVersion.KOTLIN_1_6, apiVersion4, str2, z2, kind15, i31, defaultConstructorMarker2);
        int i33 = 30;
        Kind kind16 = null;
        SuspendConversion = new LanguageFeature("SuspendConversion", 147, LanguageVersion.KOTLIN_1_6, apiVersion3, str3, z4, kind16, i33, defaultConstructorMarker);
        ProhibitSuperCallsFromPublicInline = new LanguageFeature("ProhibitSuperCallsFromPublicInline", 148, LanguageVersion.KOTLIN_1_6, apiVersion4, str2, z2, kind15, i31, defaultConstructorMarker2);
        ProhibitProtectedConstructorCallFromPublicInline = new LanguageFeature("ProhibitProtectedConstructorCallFromPublicInline", 149, LanguageVersion.KOTLIN_1_6, apiVersion3, str3, z4, kind16, i33, defaultConstructorMarker);
        TypeEnhancementImprovementsInStrictMode = new LanguageFeature("TypeEnhancementImprovementsInStrictMode", 150, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, kind15, i31, defaultConstructorMarker2);
        OptInRelease = new LanguageFeature("OptInRelease", 151, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, kind16, i33, defaultConstructorMarker);
        int i34 = 14;
        ProhibitNonExhaustiveWhenOnAlgebraicTypes = new LanguageFeature("ProhibitNonExhaustiveWhenOnAlgebraicTypes", 152, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, Kind.BUG_FIX, i34, defaultConstructorMarker2);
        UseBuilderInferenceWithoutAnnotation = new LanguageFeature("UseBuilderInferenceWithoutAnnotation", 153, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, kind16, i33, defaultConstructorMarker);
        ProhibitSmartcastsOnPropertyFromAlienBaseClass = new LanguageFeature("ProhibitSmartcastsOnPropertyFromAlienBaseClass", 154, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, Kind.BUG_FIX, i34, defaultConstructorMarker2);
        ProhibitInvalidCharsInNativeIdentifiers = new LanguageFeature("ProhibitInvalidCharsInNativeIdentifiers", 155, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, Kind.BUG_FIX, 14, defaultConstructorMarker);
        int i35 = 30;
        Kind kind17 = null;
        DefinitelyNonNullableTypes = new LanguageFeature("DefinitelyNonNullableTypes", 156, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, kind17, i35, defaultConstructorMarker2);
        int i36 = 30;
        Kind kind18 = null;
        ProhibitSimplificationOfNonTrivialConstBooleanExpressions = new LanguageFeature("ProhibitSimplificationOfNonTrivialConstBooleanExpressions", 157, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, kind18, i36, defaultConstructorMarker);
        SafeCallsAreAlwaysNullable = new LanguageFeature("SafeCallsAreAlwaysNullable", 158, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, kind17, i35, defaultConstructorMarker2);
        JvmPermittedSubclassesAttributeForSealed = new LanguageFeature("JvmPermittedSubclassesAttributeForSealed", 159, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, kind18, i36, defaultConstructorMarker);
        ProperTypeInferenceConstraintsProcessing = new LanguageFeature("ProperTypeInferenceConstraintsProcessing", 160, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, Kind.BUG_FIX, 14, defaultConstructorMarker2);
        ForbidExposingTypesInPrimaryConstructorProperties = new LanguageFeature("ForbidExposingTypesInPrimaryConstructorProperties", 161, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, Kind.BUG_FIX, 14, defaultConstructorMarker);
        int i37 = 30;
        Kind kind19 = null;
        PartiallySpecifiedTypeArguments = new LanguageFeature("PartiallySpecifiedTypeArguments", 162, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, kind19, i37, defaultConstructorMarker2);
        EliminateAmbiguitiesWithExternalTypeParameters = new LanguageFeature("EliminateAmbiguitiesWithExternalTypeParameters", 163, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, null, 30, defaultConstructorMarker);
        EliminateAmbiguitiesOnInheritedSamInterfaces = new LanguageFeature("EliminateAmbiguitiesOnInheritedSamInterfaces", 164, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, kind19, i37, defaultConstructorMarker2);
        ConsiderExtensionReceiverFromConstrainsInLambda = new LanguageFeature("ConsiderExtensionReceiverFromConstrainsInLambda", 165, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, Kind.BUG_FIX, 14, defaultConstructorMarker);
        ProperInternalVisibilityCheckInImportingScope = new LanguageFeature("ProperInternalVisibilityCheckInImportingScope", 166, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, Kind.BUG_FIX, 14, defaultConstructorMarker2);
        int i38 = 30;
        Kind kind20 = null;
        InlineClassImplementationByDelegation = new LanguageFeature("InlineClassImplementationByDelegation", 167, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, kind20, i38, defaultConstructorMarker);
        int i39 = 30;
        Kind kind21 = null;
        QualifiedSupertypeMayBeExtendedByOtherSupertype = new LanguageFeature("QualifiedSupertypeMayBeExtendedByOtherSupertype", 168, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, kind21, i39, defaultConstructorMarker2);
        YieldIsNoMoreReserved = new LanguageFeature("YieldIsNoMoreReserved", 169, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, kind20, i38, defaultConstructorMarker);
        NoDeprecationOnDeprecatedEnumEntries = new LanguageFeature("NoDeprecationOnDeprecatedEnumEntries", 170, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, kind21, i39, defaultConstructorMarker2);
        ProhibitQualifiedAccessToUninitializedEnumEntry = new LanguageFeature("ProhibitQualifiedAccessToUninitializedEnumEntry", 171, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, Kind.BUG_FIX, 14, defaultConstructorMarker);
        ForbidRecursiveDelegateExpressions = new LanguageFeature("ForbidRecursiveDelegateExpressions", 172, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, Kind.BUG_FIX, 14, defaultConstructorMarker2);
        int i40 = 30;
        Kind kind22 = null;
        KotlinFunInterfaceConstructorReference = new LanguageFeature("KotlinFunInterfaceConstructorReference", 173, LanguageVersion.KOTLIN_1_7, apiVersion3, str3, z4, kind22, i40, defaultConstructorMarker);
        SuspendOnlySamConversions = new LanguageFeature("SuspendOnlySamConversions", 174, LanguageVersion.KOTLIN_1_7, apiVersion4, str2, z2, null, 30, defaultConstructorMarker2);
        DontLoseDiagnosticsDuringOverloadResolutionByReturnType = new LanguageFeature("DontLoseDiagnosticsDuringOverloadResolutionByReturnType", 175, LanguageVersion.KOTLIN_1_8, apiVersion3, str3, z4, kind22, i40, defaultConstructorMarker);
        ProhibitConfusingSyntaxInWhenBranches = new LanguageFeature("ProhibitConfusingSyntaxInWhenBranches", 176, LanguageVersion.KOTLIN_1_8, apiVersion4, str2, z2, Kind.BUG_FIX, 14, defaultConstructorMarker2);
        int i41 = 14;
        UseConsistentRulesForPrivateConstructorsOfSealedClasses = new LanguageFeature("UseConsistentRulesForPrivateConstructorsOfSealedClasses", 177, LanguageVersion.KOTLIN_1_8, apiVersion3, str3, z4, Kind.BUG_FIX, i41, defaultConstructorMarker);
        ProgressionsChangingResolve = new LanguageFeature("ProgressionsChangingResolve", 178, LanguageVersion.KOTLIN_1_8, apiVersion4, str2, z2, null, 30, defaultConstructorMarker2);
        AbstractClassMemberNotImplementedWithIntermediateAbstractClass = new LanguageFeature("AbstractClassMemberNotImplementedWithIntermediateAbstractClass", 179, LanguageVersion.KOTLIN_1_8, apiVersion3, str3, z4, Kind.BUG_FIX, i41, defaultConstructorMarker);
        int i42 = 14;
        ForbidSuperDelegationToAbstractAnyMethod = new LanguageFeature("ForbidSuperDelegationToAbstractAnyMethod", 180, LanguageVersion.KOTLIN_1_8, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        ProperEqualityChecksInBuilderInferenceCalls = new LanguageFeature("ProperEqualityChecksInBuilderInferenceCalls", 181, LanguageVersion.KOTLIN_1_8, apiVersion3, str3, z4, Kind.BUG_FIX, i41, defaultConstructorMarker);
        ProhibitNonExhaustiveIfInRhsOfElvis = new LanguageFeature("ProhibitNonExhaustiveIfInRhsOfElvis", 182, LanguageVersion.KOTLIN_1_8, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        ReportMissingUpperBoundsViolatedErrorOnAbbreviationAtSupertypes = new LanguageFeature("ReportMissingUpperBoundsViolatedErrorOnAbbreviationAtSupertypes", 183, LanguageVersion.KOTLIN_1_8, apiVersion3, str3, z4, Kind.BUG_FIX, i41, defaultConstructorMarker);
        ForbidUsingExtensionPropertyTypeParameterInDelegate = new LanguageFeature("ForbidUsingExtensionPropertyTypeParameterInDelegate", 184, LanguageVersion.KOTLIN_1_8, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        SynchronizedSuspendError = new LanguageFeature("SynchronizedSuspendError", 185, LanguageVersion.KOTLIN_1_8, apiVersion3, str3, z4, Kind.BUG_FIX, i41, defaultConstructorMarker);
        ReportNonVarargSpreadOnGenericCalls = new LanguageFeature("ReportNonVarargSpreadOnGenericCalls", 186, LanguageVersion.KOTLIN_1_8, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        RangeUntilOperator = new LanguageFeature("RangeUntilOperator", 187, LanguageVersion.KOTLIN_1_8, apiVersion3, str3, z4, null, 30, defaultConstructorMarker);
        GenericInlineClassParameter = new LanguageFeature("GenericInlineClassParameter", 188, LanguageVersion.KOTLIN_1_8, apiVersion4, str2, z2, Kind.UNSTABLE_FEATURE, i42, defaultConstructorMarker2);
        int i43 = 14;
        ProhibitIllegalValueParameterUsageInDefaultArguments = new LanguageFeature("ProhibitIllegalValueParameterUsageInDefaultArguments", 189, LanguageVersion.KOTLIN_1_9, apiVersion3, str3, z4, Kind.BUG_FIX, i43, defaultConstructorMarker);
        ProhibitConstructorCallOnFunctionalSupertype = new LanguageFeature("ProhibitConstructorCallOnFunctionalSupertype", 190, LanguageVersion.KOTLIN_1_9, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        ProhibitArrayLiteralsInCompanionOfAnnotation = new LanguageFeature("ProhibitArrayLiteralsInCompanionOfAnnotation", 191, LanguageVersion.KOTLIN_1_9, apiVersion3, str3, z4, Kind.BUG_FIX, i43, defaultConstructorMarker);
        ProhibitCyclesInAnnotations = new LanguageFeature("ProhibitCyclesInAnnotations", 192, LanguageVersion.KOTLIN_1_9, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        ForbidExtensionFunctionTypeOnNonFunctionTypes = new LanguageFeature("ForbidExtensionFunctionTypeOnNonFunctionTypes", 193, LanguageVersion.KOTLIN_1_9, apiVersion3, str3, z4, Kind.BUG_FIX, i43, defaultConstructorMarker);
        ProhibitEnumDeclaringClass = new LanguageFeature("ProhibitEnumDeclaringClass", 194, LanguageVersion.KOTLIN_1_9, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        StopPropagatingDeprecationThroughOverrides = new LanguageFeature("StopPropagatingDeprecationThroughOverrides", 195, LanguageVersion.KOTLIN_1_9, apiVersion3, str3, z4, Kind.BUG_FIX, i43, defaultConstructorMarker);
        ReportTypeVarianceConflictOnQualifierArguments = new LanguageFeature("ReportTypeVarianceConflictOnQualifierArguments", 196, LanguageVersion.KOTLIN_1_9, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        ReportErrorsOnRecursiveTypeInsidePlusAssignment = new LanguageFeature("ReportErrorsOnRecursiveTypeInsidePlusAssignment", 197, LanguageVersion.KOTLIN_1_9, apiVersion3, str3, z4, Kind.BUG_FIX, i43, defaultConstructorMarker);
        ForbidExtensionCallsOnInlineFunctionalParameters = new LanguageFeature("ForbidExtensionCallsOnInlineFunctionalParameters", 198, LanguageVersion.KOTLIN_1_9, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        SkipStandaloneScriptsInSourceRoots = new LanguageFeature("SkipStandaloneScriptsInSourceRoots", 199, LanguageVersion.KOTLIN_1_9, apiVersion3, str3, z4, Kind.OTHER, i43, defaultConstructorMarker);
        ModifierNonBuiltinSuspendFunError = new LanguageFeature("ModifierNonBuiltinSuspendFunError", 200, LanguageVersion.KOTLIN_1_9, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        EnumEntries = new LanguageFeature("EnumEntries", 201, LanguageVersion.KOTLIN_1_9, ApiVersion.KOTLIN_1_8, str3, z4, Kind.UNSTABLE_FEATURE, 12, defaultConstructorMarker);
        ForbidSuperDelegationToAbstractFakeOverride = new LanguageFeature("ForbidSuperDelegationToAbstractFakeOverride", 202, LanguageVersion.KOTLIN_1_9, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        ApiVersion apiVersion5 = null;
        DataObjects = new LanguageFeature("DataObjects", 203, LanguageVersion.KOTLIN_1_9, apiVersion5, str3, z4, null, 30, defaultConstructorMarker);
        ProhibitAccessToEnumCompanionMembersInEnumConstructorCall = new LanguageFeature("ProhibitAccessToEnumCompanionMembersInEnumConstructorCall", 204, LanguageVersion.KOTLIN_1_9, apiVersion4, str2, z2, Kind.BUG_FIX, i42, defaultConstructorMarker2);
        int i44 = 14;
        RefineTypeCheckingOnAssignmentsToJavaFields = new LanguageFeature("RefineTypeCheckingOnAssignmentsToJavaFields", 205, LanguageVersion.KOTLIN_1_9, apiVersion5, str3, z4, Kind.BUG_FIX, i44, defaultConstructorMarker);
        ValueClassesSecondaryConstructorWithBody = new LanguageFeature("ValueClassesSecondaryConstructorWithBody", 206, LanguageVersion.KOTLIN_1_9, apiVersion4, str2, z2, Kind.UNSTABLE_FEATURE, i42, defaultConstructorMarker2);
        NativeJsProhibitLateinitIsInitializedIntrinsicWithoutPrivateAccess = new LanguageFeature("NativeJsProhibitLateinitIsInitializedIntrinsicWithoutPrivateAccess", 207, LanguageVersion.KOTLIN_1_9, apiVersion5, str3, z4, Kind.BUG_FIX, i44, defaultConstructorMarker);
        TakeIntoAccountEffectivelyFinalInMustBeInitializedCheck = new LanguageFeature("TakeIntoAccountEffectivelyFinalInMustBeInitializedCheck", 208, LanguageVersion.KOTLIN_1_9, apiVersion4, str2, z2, Kind.OTHER, i42, defaultConstructorMarker2);
        ProhibitUsingNullableTypeParameterAgainstNotNullAnnotated = new LanguageFeature("ProhibitUsingNullableTypeParameterAgainstNotNullAnnotated", 209, LanguageVersion.KOTLIN_1_9, apiVersion5, str3, z4, null, 30, defaultConstructorMarker);
        NoSourceCodeInNotNullAssertionExceptions = new LanguageFeature("NoSourceCodeInNotNullAssertionExceptions", 210, LanguageVersion.KOTLIN_1_9, ApiVersion.KOTLIN_1_4, str2, z2, Kind.OTHER, 12, defaultConstructorMarker2);
        int i45 = 14;
        MultiplatformRestrictions = new LanguageFeature("MultiplatformRestrictions", 211, LanguageVersion.KOTLIN_1_9, apiVersion5, str3, z4, Kind.BUG_FIX, i45, defaultConstructorMarker);
        int i46 = 14;
        ApiVersion apiVersion6 = null;
        EnhanceNullabilityOfPrimitiveArrays = new LanguageFeature("EnhanceNullabilityOfPrimitiveArrays", 212, LanguageVersion.KOTLIN_2_0, apiVersion6, str2, z2, Kind.BUG_FIX, i46, defaultConstructorMarker2);
        AllowEmptyIntersectionsInResultTypeResolver = new LanguageFeature("AllowEmptyIntersectionsInResultTypeResolver", 213, LanguageVersion.KOTLIN_2_0, apiVersion5, str3, z4, Kind.OTHER, i45, defaultConstructorMarker);
        ProhibitSmartcastsOnPropertyFromAlienBaseClassInheritedInInvisibleClass = new LanguageFeature("ProhibitSmartcastsOnPropertyFromAlienBaseClassInheritedInInvisibleClass", 214, LanguageVersion.KOTLIN_2_0, apiVersion6, str2, z2, Kind.BUG_FIX, i46, defaultConstructorMarker2);
        ForbidInferringPostponedTypeVariableIntoDeclaredUpperBound = new LanguageFeature("ForbidInferringPostponedTypeVariableIntoDeclaredUpperBound", 215, LanguageVersion.KOTLIN_2_0, apiVersion5, str3, z4, Kind.BUG_FIX, i45, defaultConstructorMarker);
        ProhibitUseSiteGetTargetAnnotations = new LanguageFeature("ProhibitUseSiteGetTargetAnnotations", 216, LanguageVersion.KOTLIN_2_0, apiVersion6, str2, z2, Kind.BUG_FIX, i46, defaultConstructorMarker2);
        KeepNullabilityWhenApproximatingLocalType = new LanguageFeature("KeepNullabilityWhenApproximatingLocalType", 217, LanguageVersion.KOTLIN_2_0, apiVersion5, str3, z4, Kind.BUG_FIX, i45, defaultConstructorMarker);
        ProhibitAccessToInvisibleSetterFromDerivedClass = new LanguageFeature("ProhibitAccessToInvisibleSetterFromDerivedClass", 218, LanguageVersion.KOTLIN_2_0, apiVersion6, str2, z2, Kind.BUG_FIX, i46, defaultConstructorMarker2);
        ProhibitOpenValDeferredInitialization = new LanguageFeature("ProhibitOpenValDeferredInitialization", 219, LanguageVersion.KOTLIN_2_0, apiVersion5, str3, z4, Kind.BUG_FIX, i45, defaultConstructorMarker);
        ProhibitMissedMustBeInitializedWhenThereIsNoPrimaryConstructor = new LanguageFeature("ProhibitMissedMustBeInitializedWhenThereIsNoPrimaryConstructor", 220, LanguageVersion.KOTLIN_2_0, apiVersion6, str2, z2, Kind.BUG_FIX, i46, defaultConstructorMarker2);
        MangleCallsToJavaMethodsWithValueClasses = new LanguageFeature("MangleCallsToJavaMethodsWithValueClasses", 221, LanguageVersion.KOTLIN_2_0, apiVersion5, str3, z4, Kind.OTHER, i45, defaultConstructorMarker);
        ForbidInferringTypeVariablesIntoEmptyIntersection = new LanguageFeature("ForbidInferringTypeVariablesIntoEmptyIntersection", 222, LanguageVersion.KOTLIN_2_0, apiVersion6, str2, z2, Kind.BUG_FIX, i46, defaultConstructorMarker2);
        ReferencesToSyntheticJavaProperties = new LanguageFeature("ReferencesToSyntheticJavaProperties", 223, LanguageVersion.KOTLIN_2_1, apiVersion5, str3, z4, null, 30, defaultConstructorMarker);
        ProhibitImplementingVarByInheritedVal = new LanguageFeature("ProhibitImplementingVarByInheritedVal", 224, LanguageVersion.KOTLIN_2_1, apiVersion6, str2, z2, Kind.BUG_FIX, i46, defaultConstructorMarker2);
        int i47 = 14;
        PrioritizedEnumEntries = new LanguageFeature("PrioritizedEnumEntries", 225, LanguageVersion.KOTLIN_2_1, apiVersion5, str3, z4, Kind.UNSTABLE_FEATURE, i47, defaultConstructorMarker);
        LanguageVersion languageVersion = null;
        ExpectActualClasses = new LanguageFeature("ExpectActualClasses", 226, languageVersion, apiVersion6, str2, z2, null, 30, defaultConstructorMarker2);
        LanguageVersion languageVersion2 = null;
        IgnoreNullabilityForErasedValueParameters = new LanguageFeature("IgnoreNullabilityForErasedValueParameters", XKeySym.XK_atilde, languageVersion2, apiVersion5, str3, z4, Kind.BUG_FIX, i47, defaultConstructorMarker);
        int i48 = 14;
        NoBuilderInferenceWithoutAnnotationRestriction = new LanguageFeature("NoBuilderInferenceWithoutAnnotationRestriction", XKeySym.XK_adiaeresis, languageVersion, apiVersion6, str2, z2, Kind.OTHER, i48, defaultConstructorMarker2);
        ReportErrorsForComparisonOperators = new LanguageFeature("ReportErrorsForComparisonOperators", XKeySym.XK_aring, languageVersion2, apiVersion5, str3, z4, Kind.BUG_FIX, i47, defaultConstructorMarker);
        NoAdditionalErrorsInK1DiagnosticReporter = new LanguageFeature("NoAdditionalErrorsInK1DiagnosticReporter", XKeySym.XK_ae, languageVersion, apiVersion6, str2, z2, Kind.OTHER, i48, defaultConstructorMarker2);
        ProhibitScriptTopLevelInnerClasses = new LanguageFeature("ProhibitScriptTopLevelInnerClasses", XKeySym.XK_ccedilla, languageVersion2, apiVersion5, str3, z4, Kind.OTHER, i47, defaultConstructorMarker);
        BreakContinueInInlineLambdas = new LanguageFeature("BreakContinueInInlineLambdas", XKeySym.XK_egrave, languageVersion, apiVersion6, str2, z2, null, 30, defaultConstructorMarker2);
        int i49 = 30;
        Kind kind23 = null;
        LightweightLambdas = new LanguageFeature("LightweightLambdas", XKeySym.XK_eacute, languageVersion2, apiVersion5, str3, z4, kind23, i49, defaultConstructorMarker);
        JsEnableExtensionFunctionInExternals = new LanguageFeature("JsEnableExtensionFunctionInExternals", XKeySym.XK_ecircumflex, languageVersion, apiVersion6, str2, z2, Kind.OTHER, 14, defaultConstructorMarker2);
        PackagePrivateFileClassesWithAllPrivateMembers = new LanguageFeature("PackagePrivateFileClassesWithAllPrivateMembers", XKeySym.XK_ediaeresis, languageVersion2, apiVersion5, str3, z4, kind23, i49, defaultConstructorMarker);
        int i50 = 30;
        Kind kind24 = null;
        BooleanElvisBoundSmartCasts = new LanguageFeature("BooleanElvisBoundSmartCasts", XKeySym.XK_igrave, languageVersion, apiVersion6, str2, z2, kind24, i50, defaultConstructorMarker2);
        NewDataFlowForTryExpressions = new LanguageFeature("NewDataFlowForTryExpressions", XKeySym.XK_iacute, languageVersion2, apiVersion5, str3, z4, kind23, i49, defaultConstructorMarker);
        AllowResultInReturnType = new LanguageFeature("AllowResultInReturnType", XKeySym.XK_icircumflex, languageVersion, apiVersion6, str2, z2, kind24, i50, defaultConstructorMarker2);
        MultiPlatformProjects = new LanguageFeature("MultiPlatformProjects", XKeySym.XK_idiaeresis, languageVersion2, apiVersion5, str3, z4, kind23, i49, defaultConstructorMarker);
        ProhibitComparisonOfIncompatibleClasses = new LanguageFeature("ProhibitComparisonOfIncompatibleClasses", 240, languageVersion, apiVersion6, str2, z2, Kind.BUG_FIX, 14, defaultConstructorMarker2);
        int i51 = 14;
        ExplicitBackingFields = new LanguageFeature("ExplicitBackingFields", XKeySym.XK_ntilde, languageVersion2, apiVersion5, str3, z4, Kind.UNSTABLE_FEATURE, i51, defaultConstructorMarker);
        FunctionalTypeWithExtensionAsSupertype = new LanguageFeature("FunctionalTypeWithExtensionAsSupertype", 242, languageVersion, apiVersion6, str2, z2, null, 30, defaultConstructorMarker2);
        JsAllowInvalidCharsIdentifiersEscaping = new LanguageFeature("JsAllowInvalidCharsIdentifiersEscaping", 243, languageVersion2, apiVersion5, str3, z4, Kind.UNSTABLE_FEATURE, i51, defaultConstructorMarker);
        int i52 = 14;
        JsAllowValueClassesInExternals = new LanguageFeature("JsAllowValueClassesInExternals", 244, languageVersion, apiVersion6, str2, z2, Kind.OTHER, i52, defaultConstructorMarker2);
        ContextReceivers = new LanguageFeature("ContextReceivers", 245, languageVersion2, apiVersion5, str3, z4, null, 30, defaultConstructorMarker);
        ValueClasses = new LanguageFeature("ValueClasses", 246, languageVersion, apiVersion6, str2, z2, Kind.UNSTABLE_FEATURE, i52, defaultConstructorMarker2);
        int i53 = 14;
        JavaSamConversionEqualsHashCode = new LanguageFeature("JavaSamConversionEqualsHashCode", 247, languageVersion2, apiVersion5, str3, z4, Kind.UNSTABLE_FEATURE, i53, defaultConstructorMarker);
        UnitConversionsOnArbitraryExpressions = new LanguageFeature("UnitConversionsOnArbitraryExpressions", 248, languageVersion, apiVersion6, str2, z2, null, 30, defaultConstructorMarker2);
        JsAllowImplementingFunctionInterface = new LanguageFeature("JsAllowImplementingFunctionInterface", 249, languageVersion2, apiVersion5, str3, z4, Kind.OTHER, i53, defaultConstructorMarker);
        int i54 = 14;
        CustomEqualsInValueClasses = new LanguageFeature("CustomEqualsInValueClasses", 250, languageVersion, apiVersion6, str2, z2, Kind.OTHER, i54, defaultConstructorMarker2);
        InlineLateinit = new LanguageFeature("InlineLateinit", 251, languageVersion2, apiVersion5, str3, z4, Kind.OTHER, i53, defaultConstructorMarker);
        EnableDfaWarningsInK2 = new LanguageFeature("EnableDfaWarningsInK2", 252, languageVersion, apiVersion6, str2, z2, Kind.OTHER, i54, defaultConstructorMarker2);
        ContractSyntaxV2 = new LanguageFeature("ContractSyntaxV2", 253, languageVersion2, apiVersion5, str3, z4, Kind.UNSTABLE_FEATURE, i53, defaultConstructorMarker);
        ImplicitSignedToUnsignedIntegerConversion = new LanguageFeature("ImplicitSignedToUnsignedIntegerConversion", 254, languageVersion, apiVersion6, str2, z2, null, 30, defaultConstructorMarker2);
        IntrinsicConstEvaluation = new LanguageFeature("IntrinsicConstEvaluation", 255, languageVersion2, apiVersion5, str3, z4, Kind.UNSTABLE_FEATURE, i53, defaultConstructorMarker);
        DisableCheckingChangedProgressionsResolve = new LanguageFeature("DisableCheckingChangedProgressionsResolve", 256, languageVersion, apiVersion6, str2, z2, Kind.OTHER, 14, defaultConstructorMarker2);
        ContextSensitiveEnumResolutionInWhen = new LanguageFeature("ContextSensitiveEnumResolutionInWhen", 257, languageVersion2, apiVersion5, str3, z4, Kind.UNSTABLE_FEATURE, i53, defaultConstructorMarker);
        LanguageFeature[] languageFeatureArr$values = $values();
        $VALUES = languageFeatureArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(languageFeatureArr$values);
        INSTANCE = new Companion(null);
    }

    public final String getPresentableName() {
        return CollectionsKt.joinToString$default(new Regex("(?<!^)(?=[A-Z])").split(name(), 0), " ", null, null, 0, null, LanguageFeature$presentableName$1.INSTANCE, 30, null);
    }

    public final String getPresentableText() {
        return this.hintUrl == null ? getPresentableName() : getPresentableName() + " (See: " + this.hintUrl + ')';
    }

    /* JADX INFO: compiled from: LanguageVersionSettings.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u000f\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/config/LanguageFeature$State;", "", "Lorg/jetbrains/kotlin/utils/DescriptionAware;", MediaTrack.ROLE_DESCRIPTION, "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "ENABLED", "ENABLED_WITH_WARNING", "DISABLED", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum State implements DescriptionAware {
        ENABLED("Enabled"),
        ENABLED_WITH_WARNING("Enabled with warning"),
        DISABLED("Disabled");

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final String description;

        public static EnumEntries<State> getEntries() {
            return $ENTRIES;
        }

        State(String str) {
            this.description = str;
        }

        @Override // org.jetbrains.kotlin.utils.DescriptionAware
        public String getDescription() {
            return this.description;
        }
    }

    /* JADX INFO: compiled from: LanguageVersionSettings.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/config/LanguageFeature$Kind;", "", "enabledInProgressiveMode", "", "forcesPreReleaseBinaries", "(Ljava/lang/String;IZZ)V", "getEnabledInProgressiveMode", "()Z", "getForcesPreReleaseBinaries", "BUG_FIX", "UNSTABLE_FEATURE", "OTHER", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Kind {
        BUG_FIX(true, false),
        UNSTABLE_FEATURE(false, true),
        OTHER(false, false);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final boolean enabledInProgressiveMode;
        private final boolean forcesPreReleaseBinaries;

        public static EnumEntries<Kind> getEntries() {
            return $ENTRIES;
        }

        Kind(boolean z, boolean z2) {
            this.enabledInProgressiveMode = z;
            this.forcesPreReleaseBinaries = z2;
        }

        public final boolean getEnabledInProgressiveMode() {
            return this.enabledInProgressiveMode;
        }

        public final boolean getForcesPreReleaseBinaries() {
            return this.forcesPreReleaseBinaries;
        }
    }

    /* JADX INFO: compiled from: LanguageVersionSettings.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/config/LanguageFeature$Companion;", "", "()V", "fromString", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "str", "", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final LanguageFeature fromString(String str) {
            Intrinsics.checkNotNullParameter(str, "str");
            for (LanguageFeature languageFeature : LanguageFeature.values()) {
                if (Intrinsics.areEqual(languageFeature.name(), str)) {
                    return languageFeature;
                }
            }
            return null;
        }
    }
}
