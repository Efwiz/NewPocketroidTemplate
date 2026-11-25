package org.jetbrains.kotlin.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.builtins.functions.FunctionInvokeDescriptor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory4;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation0;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation1;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation4;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.diagnostics.ParametrizedDiagnostic;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.ValueArgumentName;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceExpectedTypeConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ArgumentConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ArgumentConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.BuilderInferencePosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.BuilderInferenceSubstitutionConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.CallableReferenceConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.CallableReferenceConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.CapturedTypeFromSubtyping;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstrainingTypeIsError;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.DeclaredUpperBoundConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.DeclaredUpperBoundConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.DelegatedPropertyConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ExpectedTypeConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ExplicitTypeParameterConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.FixVariableConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.FixVariableConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.IncorporationConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.InferredEmptyIntersection;
import org.jetbrains.kotlin.resolve.calls.inference.model.InferredEmptyIntersectionError;
import org.jetbrains.kotlin.resolve.calls.inference.model.InferredEmptyIntersectionWarning;
import org.jetbrains.kotlin.resolve.calls.inference.model.InferredIntoDeclaredUpperBounds;
import org.jetbrains.kotlin.resolve.calls.inference.model.InjectedAnotherStubTypeConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.KnownTypeParameterConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.LHSArgumentConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.LambdaArgumentConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.LowerPriorityToPreserveCompatibility;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintError;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintMismatch;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintWarning;
import org.jetbrains.kotlin.resolve.calls.inference.model.NoSuccessfulFork;
import org.jetbrains.kotlin.resolve.calls.inference.model.NotEnoughInformationForTypeParameter;
import org.jetbrains.kotlin.resolve.calls.inference.model.NotEnoughInformationForTypeParameterImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.OnlyInputTypesDiagnostic;
import org.jetbrains.kotlin.resolve.calls.inference.model.ReceiverConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ReceiverConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.SimpleConstraintSystemConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableForLambdaReturnType;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.AbstractFakeOverrideSuperCall;
import org.jetbrains.kotlin.resolve.calls.model.AbstractSuperCall;
import org.jetbrains.kotlin.resolve.calls.model.AdaptedCallableReferenceIsUsedWithReflection;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentNullabilityErrorDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentNullabilityMismatchDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentNullabilityWarningDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentPassedTwice;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceCallCandidatesAmbiguity;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferencesDefaultArgumentUsed;
import org.jetbrains.kotlin.resolve.calls.model.CandidateChosenUsingOverloadResolutionByLambdaAnnotation;
import org.jetbrains.kotlin.resolve.calls.model.CompatibilityWarning;
import org.jetbrains.kotlin.resolve.calls.model.CompatibilityWarningOnArgument;
import org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter;
import org.jetbrains.kotlin.resolve.calls.model.EnumEntryAmbiguityWarning;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.InstantiationOfAbstractClass;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnosticsKt;
import org.jetbrains.kotlin.resolve.calls.model.KotlinConstraintSystemDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.MixingNamedAndPositionArguments;
import org.jetbrains.kotlin.resolve.calls.model.MultiLambdaBuilderInferenceRestriction;
import org.jetbrains.kotlin.resolve.calls.model.MultipleArgumentsApplicableForContextReceiver;
import org.jetbrains.kotlin.resolve.calls.model.NameForAmbiguousParameter;
import org.jetbrains.kotlin.resolve.calls.model.NameNotFound;
import org.jetbrains.kotlin.resolve.calls.model.NamedArgumentNotAllowed;
import org.jetbrains.kotlin.resolve.calls.model.NamedArgumentReference;
import org.jetbrains.kotlin.resolve.calls.model.NoContextReceiver;
import org.jetbrains.kotlin.resolve.calls.model.NoValueForParameter;
import org.jetbrains.kotlin.resolve.calls.model.NonApplicableCallForBuilderInferenceDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.NonVarargSpread;
import org.jetbrains.kotlin.resolve.calls.model.NoneCallableReferenceCallCandidates;
import org.jetbrains.kotlin.resolve.calls.model.NotCallableExpectedType;
import org.jetbrains.kotlin.resolve.calls.model.NotCallableMemberReference;
import org.jetbrains.kotlin.resolve.calls.model.NotEnoughInformationForLambdaParameter;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedToSamWithVarargDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SmartCastDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.StubBuilderInferenceReceiver;
import org.jetbrains.kotlin.resolve.calls.model.SuperAsExtensionReceiver;
import org.jetbrains.kotlin.resolve.calls.model.TooManyArguments;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeCheckerHasRanIntoRecursion;
import org.jetbrains.kotlin.resolve.calls.model.UnsafeCallError;
import org.jetbrains.kotlin.resolve.calls.model.UnstableSmartCast;
import org.jetbrains.kotlin.resolve.calls.model.VarargArgumentOutsideParentheses;
import org.jetbrains.kotlin.resolve.calls.model.WrongCountOfTypeArguments;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SingleSmartCast;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastManager;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastResult;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tower.CallableReferenceKotlinCallArgumentImpl;
import org.jetbrains.kotlin.resolve.calls.tower.ContextReceiverAmbiguity;
import org.jetbrains.kotlin.resolve.calls.tower.ExpressionKotlinCallArgumentImpl;
import org.jetbrains.kotlin.resolve.calls.tower.NewCallArgumentsKt;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallsKt;
import org.jetbrains.kotlin.resolve.calls.tower.SimpleTypeArgumentImpl;
import org.jetbrains.kotlin.resolve.calls.tower.TrackingBindingTrace;
import org.jetbrains.kotlin.resolve.calls.tower.UnsupportedContextualDeclarationCall;
import org.jetbrains.kotlin.resolve.calls.tower.VisibilityError;
import org.jetbrains.kotlin.resolve.calls.tower.VisibilityErrorOnArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstantChecker;
import org.jetbrains.kotlin.resolve.constants.TypedCompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.IntersectionTypeKt;
import org.jetbrains.kotlin.types.checker.NewTypeVariableConstructor;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.ControlStructureTypingUtils;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContextDelegate;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: DiagnosticReporterByTrackingStrategy.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0016J\u0012\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00100\u001a\u000201H\u0002J\u001e\u00102\u001a\b\u0012\u0004\u0012\u000204032\u0006\u00105\u001a\u0002042\u0006\u00106\u001a\u000207H\u0002J!\u00108\u001a\u00020!2\u0006\u00100\u001a\u000204H\u0002\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0001(\u0000J\u0010\u0010:\u001a\u00020+2\u0006\u0010;\u001a\u00020\fH\u0016J\u0018\u0010<\u001a\u00020+2\u0006\u0010=\u001a\u00020>2\u0006\u0010;\u001a\u00020\fH\u0016J\u0018\u0010?\u001a\u00020+2\u0006\u0010=\u001a\u00020>2\u0006\u0010;\u001a\u00020\fH\u0016J\u0018\u0010@\u001a\u00020+2\u0006\u0010=\u001a\u00020>2\u0006\u0010;\u001a\u00020\fH\u0016J\u0010\u0010A\u001a\u00020+2\u0006\u0010;\u001a\u00020\fH\u0016J\u0018\u0010B\u001a\u00020+2\u0006\u0010C\u001a\u00020D2\u0006\u0010;\u001a\u00020\fH\u0016J\u0010\u0010E\u001a\u00020+2\u0006\u0010;\u001a\u00020\fH\u0016J\u0018\u0010F\u001a\u00020+2\u0006\u0010G\u001a\u00020H2\u0006\u0010;\u001a\u00020\fH\u0016J\u0010\u0010I\u001a\u00020+2\u0006\u0010;\u001a\u00020\fH\u0016JX\u0010J\u001a\u00020+2\u0006\u0010,\u001a\u00020K2\u0006\u0010L\u001a\u00020>2\u0006\u0010M\u001a\u00020!2\u0018\u0010N\u001a\u0014\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u00020P\u0012\u0004\u0012\u00020P0O2\b\u0010Q\u001a\u0004\u0018\u00010R2\u0012\u0010S\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020+0TH\u0002J\u0018\u0010V\u001a\u00020+2\u0006\u0010,\u001a\u00020K2\u0006\u0010W\u001a\u00020XH\u0002J\u0018\u0010Y\u001a\u00020!2\u0006\u0010*\u001a\u00020K2\u0006\u0010Z\u001a\u00020/H\u0002J\u0018\u0010[\u001a\u00020+2\u0006\u0010,\u001a\u00020K2\u0006\u0010\\\u001a\u00020]H\u0002J\u0018\u0010^\u001a\u00020+2\u0006\u00105\u001a\u0002092\u0006\u0010,\u001a\u00020_H\u0002J\u0018\u0010`\u001a\u00020+2\u0006\u0010=\u001a\u00020>2\u0006\u0010;\u001a\u00020aH\u0002J\u0010\u0010b\u001a\u00020+2\u0006\u0010c\u001a\u00020dH\u0002J\u0010\u0010e\u001a\u00020+2\u0006\u0010f\u001a\u00020gH\u0002J\u0018\u0010h\u001a\u00020+2\u0006\u0010;\u001a\u00020\f2\u0006\u0010i\u001a\u00020jH\u0002J\u0014\u0010k\u001a\u00020!*\u00020P2\u0006\u00106\u001a\u000207H\u0002R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00158BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020!8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\u00020'8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000ò\u0001\u0004\n\u000209¨\u0006l"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/DiagnosticReporterByTrackingStrategy;", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "constantExpressionEvaluator", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "psiKotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "allDiagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "smartCastManager", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastManager;", "typeSystemContext", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContextDelegate;", "(Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastManager;Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContextDelegate;)V", "getAllDiagnostics", "()Ljava/util/List;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "getCall", "()Lorg/jetbrains/kotlin/psi/Call;", "getConstantExpressionEvaluator", "()Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "getContext", "()Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "getDataFlowValueFactory", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "getPsiKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "reportAdditionalErrors", "", "getReportAdditionalErrors", "()Z", "trace", "Lorg/jetbrains/kotlin/resolve/calls/tower/TrackingBindingTrace;", "tracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "getTracingStrategy", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "constraintError", "", "error", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "getArgumentsExpressionOrLastExpressionInBlock", "Lorg/jetbrains/kotlin/psi/KtExpression;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallArgument;", "getSubResolvedAtomsOfSpecialCallToReportUninferredTypeParameter", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "resolvedAtom", "uninferredTypeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "isSpecialFunction", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "onCall", "diagnostic", "onCallArgument", "callArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "onCallArgumentName", "onCallArgumentSpread", "onCallName", "onCallReceiver", "callReceiver", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "onExplicitReceiver", "onTypeArgument", "typeArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/TypeArgument;", "onTypeArguments", "reportArgumentConstraintErrorByPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintMismatch;", "argument", "isWarning", "typeMismatchDiagnostic", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;", "Lorg/jetbrains/kotlin/types/KotlinType;", "selectorCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "report", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "reportCallableReferenceConstraintError", "rhsExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "reportConstantTypeMismatch", "expression", "reportConstraintErrorByPosition", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "reportNotEnoughInformationForTypeParameterForSpecialCall", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NotEnoughInformationForTypeParameterImpl;", "reportNullabilityMismatchDiagnostic", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentNullabilityMismatchDiagnostic;", "reportSmartCast", "smartCastDiagnostic", "Lorg/jetbrains/kotlin/resolve/calls/model/SmartCastDiagnostic;", "reportUnstableSmartCast", "unstableSmartCast", "Lorg/jetbrains/kotlin/resolve/calls/model/UnstableSmartCast;", "unknownError", "onTarget", "", "containsUninferredTypeParameter", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticReporterByTrackingStrategy implements DiagnosticReporter {
    private final List<KotlinCallDiagnostic> allDiagnostics;
    private final ConstantExpressionEvaluator constantExpressionEvaluator;
    private final BasicCallResolutionContext context;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final PSIKotlinCall psiKotlinCall;
    private final SmartCastManager smartCastManager;
    private final TrackingBindingTrace trace;
    private final TypeSystemInferenceExtensionContextDelegate typeSystemContext;

    @Override // org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter
    public void onCallName(KotlinCallDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter
    public void onExplicitReceiver(KotlinCallDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter
    public void onTypeArgument(TypeArgument typeArgument, KotlinCallDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(typeArgument, "typeArgument");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DiagnosticReporterByTrackingStrategy(ConstantExpressionEvaluator constantExpressionEvaluator, BasicCallResolutionContext context, PSIKotlinCall psiKotlinCall, DataFlowValueFactory dataFlowValueFactory, List<? extends KotlinCallDiagnostic> allDiagnostics, SmartCastManager smartCastManager, TypeSystemInferenceExtensionContextDelegate typeSystemContext) {
        Intrinsics.checkNotNullParameter(constantExpressionEvaluator, "constantExpressionEvaluator");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(psiKotlinCall, "psiKotlinCall");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(allDiagnostics, "allDiagnostics");
        Intrinsics.checkNotNullParameter(smartCastManager, "smartCastManager");
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        this.constantExpressionEvaluator = constantExpressionEvaluator;
        this.context = context;
        this.psiKotlinCall = psiKotlinCall;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.allDiagnostics = allDiagnostics;
        this.smartCastManager = smartCastManager;
        this.typeSystemContext = typeSystemContext;
        BindingTrace bindingTrace = context.trace;
        Intrinsics.checkNotNull(bindingTrace, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.TrackingBindingTrace");
        this.trace = (TrackingBindingTrace) bindingTrace;
    }

    public final ConstantExpressionEvaluator getConstantExpressionEvaluator() {
        return this.constantExpressionEvaluator;
    }

    public final BasicCallResolutionContext getContext() {
        return this.context;
    }

    public final PSIKotlinCall getPsiKotlinCall() {
        return this.psiKotlinCall;
    }

    public final DataFlowValueFactory getDataFlowValueFactory() {
        return this.dataFlowValueFactory;
    }

    public final List<KotlinCallDiagnostic> getAllDiagnostics() {
        return this.allDiagnostics;
    }

    private final TracingStrategy getTracingStrategy() {
        return this.psiKotlinCall.getTracingStrategy();
    }

    private final Call getCall() {
        return this.psiKotlinCall.getPsiCall();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter
    public void onCall(KotlinCallDiagnostic diagnostic) {
        Object objMo6346getCompanionObjectDescriptor;
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        if (diagnostic instanceof VisibilityError) {
            getTracingStrategy().invisibleMember(this.trace, ((VisibilityError) diagnostic).getInvisibleMember());
            return;
        }
        if (diagnostic instanceof NoValueForParameter) {
            getTracingStrategy().noValueForParameter(this.trace, ((NoValueForParameter) diagnostic).getParameterDescriptor());
            return;
        }
        boolean z = true;
        if (diagnostic instanceof TypeCheckerHasRanIntoRecursion) {
            KtElement callElement = getCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
            for (PsiElement psiElement : PsiUtilsKt.getParents(callElement)) {
                if ((psiElement instanceof KtBinaryExpression) && KtTokens.AUGMENTED_ASSIGNMENTS.contains(((KtBinaryExpression) psiElement).getOperationToken())) {
                    getTracingStrategy().recursiveType(this.trace, this.context.languageVersionSettings, z);
                    return;
                }
            }
            z = false;
            getTracingStrategy().recursiveType(this.trace, this.context.languageVersionSettings, z);
            return;
        }
        if (diagnostic instanceof InstantiationOfAbstractClass) {
            getTracingStrategy().instantiationOfAbstractClass(this.trace);
            return;
        }
        if (diagnostic instanceof AbstractSuperCall) {
            KtExpression psiExpression = NewCallArgumentsKt.getPsiExpression(((AbstractSuperCall) diagnostic).getReceiver());
            objMo6346getCompanionObjectDescriptor = psiExpression instanceof KtSuperExpression ? (KtSuperExpression) psiExpression : null;
            if (this.context.languageVersionSettings.supportsFeature(LanguageFeature.ForbidSuperDelegationToAbstractAnyMethod) || objMo6346getCompanionObjectDescriptor == null || !Intrinsics.areEqual(this.trace.get(BindingContext.SUPER_EXPRESSION_FROM_ANY_MIGRATION, objMo6346getCompanionObjectDescriptor), (Object) true)) {
                getTracingStrategy().abstractSuperCall(this.trace);
                return;
            } else {
                getTracingStrategy().abstractSuperCallWarning(this.trace);
                return;
            }
        }
        if (diagnostic instanceof AbstractFakeOverrideSuperCall) {
            if (this.context.languageVersionSettings.supportsFeature(LanguageFeature.ForbidSuperDelegationToAbstractFakeOverride)) {
                getTracingStrategy().abstractSuperCall(this.trace);
                return;
            } else {
                getTracingStrategy().abstractSuperCallWarning(this.trace);
                return;
            }
        }
        if (diagnostic instanceof NonApplicableCallForBuilderInferenceDiagnostic) {
            KotlinCall kotlinCall = ((NonApplicableCallForBuilderInferenceDiagnostic) diagnostic).getKotlinCall();
            TrackingBindingTrace trackingBindingTrace = this.trace;
            Diagnostic diagnosticOn = Errors.NON_APPLICABLE_CALL_FOR_BUILDER_INFERENCE.on(PSIKotlinCallsKt.getPsiKotlinCall(kotlinCall).getPsiCall().getCallElement());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trackingBindingTrace, diagnosticOn);
            return;
        }
        if (diagnostic instanceof CandidateChosenUsingOverloadResolutionByLambdaAnnotation) {
            TrackingBindingTrace trackingBindingTrace2 = this.trace;
            Diagnostic diagnosticOn2 = Errors.CANDIDATE_CHOSEN_USING_OVERLOAD_RESOLUTION_BY_LAMBDA_ANNOTATION.on(this.psiKotlinCall.getPsiCall().getCallElement());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
            trackingBindingTrace2.report(diagnosticOn2);
            return;
        }
        if (diagnostic instanceof EnumEntryAmbiguityWarning) {
            EnumEntryAmbiguityWarning enumEntryAmbiguityWarning = (EnumEntryAmbiguityWarning) diagnostic;
            PropertyDescriptor property = enumEntryAmbiguityWarning.getProperty();
            ClassDescriptor enumEntry = enumEntryAmbiguityWarning.getEnumEntry();
            DeclarationDescriptor containingModule = enumEntry.getContainingModule();
            ClassDescriptor classDescriptor = containingModule instanceof ClassDescriptor ? (ClassDescriptor) containingModule : null;
            objMo6346getCompanionObjectDescriptor = classDescriptor != null ? classDescriptor.mo6346getCompanionObjectDescriptor() : null;
            if (objMo6346getCompanionObjectDescriptor == null || !Intrinsics.areEqual(property.getContainingModule(), objMo6346getCompanionObjectDescriptor)) {
                TrackingBindingTrace trackingBindingTrace3 = this.trace;
                Diagnostic diagnosticOn3 = Errors.DEPRECATED_RESOLVE_WITH_AMBIGUOUS_ENUM_ENTRY.on(this.psiKotlinCall.getPsiCall().getCallElement(), property, enumEntry);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn3, "on(...)");
                trackingBindingTrace3.report(diagnosticOn3);
                return;
            }
            return;
        }
        if (diagnostic instanceof CompatibilityWarning) {
            KtExpression callElement2 = this.psiKotlinCall.getPsiCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement2, "getCallElement(...)");
            TrackingBindingTrace trackingBindingTrace4 = this.trace;
            DiagnosticFactory1<KtElement, CallableDescriptor> diagnosticFactory1 = Errors.COMPATIBILITY_WARNING;
            KtExpression calleeExpressionIfAny = org.jetbrains.kotlin.resolve.calls.util.PsiUtilsKt.getCalleeExpressionIfAny(callElement2);
            if (calleeExpressionIfAny != null) {
                callElement2 = calleeExpressionIfAny;
            }
            Diagnostic diagnosticOn4 = diagnosticFactory1.on(callElement2, ((CompatibilityWarning) diagnostic).getCandidate());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn4, "on(...)");
            trackingBindingTrace4.report(diagnosticOn4);
            return;
        }
        if (diagnostic instanceof NoContextReceiver) {
            KtExpression callElement3 = this.psiKotlinCall.getPsiCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement3, "getCallElement(...)");
            TrackingBindingTrace trackingBindingTrace5 = this.trace;
            DiagnosticFactory1<KtElement, String> diagnosticFactory2 = Errors.NO_CONTEXT_RECEIVER;
            KtExpression calleeExpressionIfAny2 = org.jetbrains.kotlin.resolve.calls.util.PsiUtilsKt.getCalleeExpressionIfAny(callElement3);
            if (calleeExpressionIfAny2 != null) {
                callElement3 = calleeExpressionIfAny2;
            }
            Diagnostic diagnosticOn5 = diagnosticFactory2.on(callElement3, ((NoContextReceiver) diagnostic).getReceiverDescriptor().getValue().toString());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn5, "on(...)");
            trackingBindingTrace5.report(diagnosticOn5);
            return;
        }
        if (diagnostic instanceof MultipleArgumentsApplicableForContextReceiver) {
            KtElement callElement4 = this.psiKotlinCall.getPsiCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement4, "getCallElement(...)");
            TrackingBindingTrace trackingBindingTrace6 = this.trace;
            Diagnostic diagnosticOn6 = Errors.MULTIPLE_ARGUMENTS_APPLICABLE_FOR_CONTEXT_RECEIVER.on(callElement4, ((MultipleArgumentsApplicableForContextReceiver) diagnostic).getReceiverDescriptor().getValue().toString());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn6, "on(...)");
            trackingBindingTrace6.report(diagnosticOn6);
            return;
        }
        if (diagnostic instanceof ContextReceiverAmbiguity) {
            KtElement callElement5 = this.psiKotlinCall.getPsiCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement5, "getCallElement(...)");
            TrackingBindingTrace trackingBindingTrace7 = this.trace;
            Diagnostic diagnosticOn7 = Errors.AMBIGUOUS_CALL_WITH_IMPLICIT_CONTEXT_RECEIVER.on(callElement5);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn7, "on(...)");
            trackingBindingTrace7.report(diagnosticOn7);
            return;
        }
        if (diagnostic instanceof UnsupportedContextualDeclarationCall) {
            KtElement callElement6 = this.psiKotlinCall.getPsiCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement6, "getCallElement(...)");
            TrackingBindingTrace trackingBindingTrace8 = this.trace;
            Diagnostic diagnosticOn8 = Errors.UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL.on(callElement6);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn8, "on(...)");
            trackingBindingTrace8.report(diagnosticOn8);
        } else {
            if (diagnostic instanceof AdaptedCallableReferenceIsUsedWithReflection ? true : diagnostic instanceof NotCallableMemberReference ? true : diagnostic instanceof CallableReferencesDefaultArgumentUsed) {
                return;
            }
            unknownError(diagnostic, "onCall");
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter
    public void onTypeArguments(KotlinCallDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        KtExpression callElement = this.psiKotlinCall.getPsiCall().getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        if (callElement instanceof KtCallExpression) {
            KtCallExpression ktCallExpression = (KtCallExpression) callElement;
            KtTypeArgumentList typeArgumentList = ktCallExpression.getTypeArgumentList();
            if (typeArgumentList != null) {
                callElement = typeArgumentList;
            } else {
                KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
                if (calleeExpression != null) {
                    callElement = calleeExpression;
                }
            }
        }
        if (diagnostic instanceof WrongCountOfTypeArguments) {
            WrongCountOfTypeArguments wrongCountOfTypeArguments = (WrongCountOfTypeArguments) diagnostic;
            int size = wrongCountOfTypeArguments.getDescriptor().getTypeParameters().size();
            TrackingBindingTrace trackingBindingTrace = this.trace;
            Diagnostic diagnosticOn = Errors.WRONG_NUMBER_OF_TYPE_ARGUMENTS.on(callElement, Integer.valueOf(size), wrongCountOfTypeArguments.getDescriptor());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            trackingBindingTrace.report(diagnosticOn);
            return;
        }
        unknownError(diagnostic, "onTypeArguments");
    }

    private final void unknownError(KotlinCallDiagnostic diagnostic, String onTarget) {
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            throw new AssertionError(onTarget + " should not be called with " + diagnostic.getClass());
        }
        if (getReportAdditionalErrors()) {
            TrackingBindingTrace trackingBindingTrace = this.trace;
            Diagnostic diagnosticOn = Errors.NEW_INFERENCE_UNKNOWN_ERROR.on(this.psiKotlinCall.getPsiCall().getCallElement(), diagnostic.getCandidateApplicability(), onTarget);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            trackingBindingTrace.report(diagnosticOn);
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x003c  */
    @Override // org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter
    public void onCallReceiver(SimpleKotlinCallArgument callReceiver, KotlinCallDiagnostic diagnostic) {
        Name name2;
        Name name3;
        DeclarationDescriptor containingDeclaration;
        NewTypeVariableConstructor originalTypeVariable;
        boolean isForImplicitInvoke;
        Intrinsics.checkNotNullParameter(callReceiver, "callReceiver");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        if (diagnostic instanceof UnsafeCallError) {
            if (callReceiver instanceof ReceiverExpressionKotlinCallArgument) {
                isForImplicitInvoke = ((ReceiverExpressionKotlinCallArgument) callReceiver).getIsForImplicitInvoke();
            } else if (((UnsafeCallError) diagnostic).getIsForImplicitInvoke()) {
                isForImplicitInvoke = true;
            } else {
                KotlinType type = callReceiver.getReceiver().getReceiverValue().getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                if (FunctionTypesKt.isExtensionFunctionType(type)) {
                    isForImplicitInvoke = true;
                } else {
                    isForImplicitInvoke = false;
                }
            }
            getTracingStrategy().unsafeCall(this.trace, callReceiver.getReceiver().getReceiverValue().getType(), isForImplicitInvoke);
            return;
        }
        if (diagnostic instanceof SuperAsExtensionReceiver) {
            KtExpression psiExpression = NewCallArgumentsKt.getPsiExpression(callReceiver);
            if (psiExpression instanceof KtSuperExpression) {
                TrackingBindingTrace trackingBindingTrace = this.trace;
                Diagnostic diagnosticOn = Errors.SUPER_CANT_BE_EXTENSION_RECEIVER.on(psiExpression, ((KtSuperExpression) psiExpression).getText());
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                trackingBindingTrace.report(diagnosticOn);
                return;
            }
            return;
        }
        if (diagnostic instanceof StubBuilderInferenceReceiver) {
            KotlinType type2 = callReceiver.getReceiver().getReceiverValue().getType();
            TypeParameterDescriptor originalTypeParameter = null;
            StubTypeForBuilderInference stubTypeForBuilderInference = type2 instanceof StubTypeForBuilderInference ? (StubTypeForBuilderInference) type2 : null;
            if (stubTypeForBuilderInference != null && (originalTypeVariable = stubTypeForBuilderInference.getOriginalTypeVariable()) != null) {
                originalTypeParameter = originalTypeVariable.getOriginalTypeParameter();
            }
            TrackingBindingTrace trackingBindingTrace2 = this.trace;
            DiagnosticFactory2<PsiElement, Name, Name> diagnosticFactory2 = Errors.BUILDER_INFERENCE_STUB_RECEIVER;
            KtExpression psiExpression2 = NewCallArgumentsKt.getPsiExpression(callReceiver);
            KtElement callElement = psiExpression2 != null ? psiExpression2 : getCall().getCallElement();
            if (originalTypeParameter == null || (name2 = originalTypeParameter.getShortName()) == null) {
                name2 = SpecialNames.NO_NAME_PROVIDED;
            }
            if (originalTypeParameter == null || (containingDeclaration = originalTypeParameter.getContainingModule()) == null || (name3 = containingDeclaration.getShortName()) == null) {
                name3 = SpecialNames.NO_NAME_PROVIDED;
            }
            Diagnostic diagnosticOn2 = diagnosticFactory2.on(callElement, name2, name3);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
            trackingBindingTrace2.report(diagnosticOn2);
            return;
        }
        unknownError(diagnostic, "onCallReceiver");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter
    public void onCallArgument(KotlinCallArgument callArgument, KotlinCallDiagnostic diagnostic) {
        Name name2;
        Name name3;
        DeclarationDescriptor containingDeclaration;
        List<KtParameter> valueParameters;
        Intrinsics.checkNotNullParameter(callArgument, "callArgument");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        if (diagnostic instanceof SmartCastDiagnostic) {
            reportSmartCast((SmartCastDiagnostic) diagnostic);
            return;
        }
        if (diagnostic instanceof UnstableSmartCast) {
            reportUnstableSmartCast((UnstableSmartCast) diagnostic);
            return;
        }
        if (diagnostic instanceof VisibilityErrorOnArgument) {
            VisibilityErrorOnArgument visibilityErrorOnArgument = (VisibilityErrorOnArgument) diagnostic;
            DeclarationDescriptorWithVisibility invisibleMember = visibilityErrorOnArgument.getInvisibleMember();
            KtExpression argumentExpression = NewCallArgumentsKt.getPsiCallArgument(visibilityErrorOnArgument.getArgument()).getValueArgument().getArgumentExpression();
            KtExpression ktExpressionLastBlockStatementOrThis = argumentExpression != null ? KtPsiUtilKt.lastBlockStatementOrThis(argumentExpression) : null;
            if (ktExpressionLastBlockStatementOrThis != null) {
                TrackingBindingTrace trackingBindingTrace = this.trace;
                Diagnostic diagnosticOn = Errors.INVISIBLE_MEMBER.on(ktExpressionLastBlockStatementOrThis, invisibleMember, invisibleMember.getVisibility(), invisibleMember);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                trackingBindingTrace.report(diagnosticOn);
                return;
            }
            return;
        }
        if (diagnostic instanceof TooManyArguments) {
            TrackingBindingTrace trackingBindingTrace2 = this.trace;
            KtExpression psiExpression = NewCallArgumentsKt.getPsiExpression(callArgument);
            if (psiExpression != null) {
                if (!(psiExpression instanceof KtLambdaExpression) || !CallUtilKt.isTrailingLambdaOnNewLIne((KtLambdaExpression) psiExpression)) {
                    Diagnostic diagnosticOn2 = Errors.TOO_MANY_ARGUMENTS.on(psiExpression, ((TooManyArguments) diagnostic).getDescriptor());
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
                    trackingBindingTrace2.report(diagnosticOn2);
                } else {
                    trackingBindingTrace2.report(Errors.UNEXPECTED_TRAILING_LAMBDA_ON_A_NEW_LINE.on(psiExpression));
                }
            }
            this.trace.markAsReported();
            return;
        }
        if (diagnostic instanceof VarargArgumentOutsideParentheses) {
            TrackingBindingTrace trackingBindingTrace3 = this.trace;
            KtExpression psiExpression2 = NewCallArgumentsKt.getPsiExpression(callArgument);
            if (psiExpression2 != null) {
                if (!(psiExpression2 instanceof KtLambdaExpression) || !CallUtilKt.isTrailingLambdaOnNewLIne((KtLambdaExpression) psiExpression2)) {
                    Diagnostic diagnosticOn3 = Errors.VARARG_OUTSIDE_PARENTHESES.on(psiExpression2);
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn3, "on(...)");
                    trackingBindingTrace3.report(diagnosticOn3);
                    return;
                }
                trackingBindingTrace3.report(Errors.UNEXPECTED_TRAILING_LAMBDA_ON_A_NEW_LINE.on(psiExpression2));
                return;
            }
            return;
        }
        if (diagnostic instanceof MixingNamedAndPositionArguments) {
            TrackingBindingTrace trackingBindingTrace4 = this.trace;
            Diagnostic diagnosticOn4 = Errors.MIXING_NAMED_AND_POSITIONED_ARGUMENTS.on(NewCallArgumentsKt.getPsiCallArgument(callArgument).getValueArgument().getCallElement());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn4, "on(...)");
            trackingBindingTrace4.report(diagnosticOn4);
            return;
        }
        if (diagnostic instanceof NoneCallableReferenceCallCandidates) {
            CallableReferenceKotlinCallArgument argument = ((NoneCallableReferenceCallCandidates) diagnostic).getArgument();
            CallableReferenceKotlinCallArgumentImpl callableReferenceKotlinCallArgumentImpl = argument instanceof CallableReferenceKotlinCallArgumentImpl ? (CallableReferenceKotlinCallArgumentImpl) argument : null;
            KtCallableReferenceExpression ktCallableReferenceExpression = callableReferenceKotlinCallArgumentImpl != null ? callableReferenceKotlinCallArgumentImpl.getKtCallableReferenceExpression() : null;
            if (ktCallableReferenceExpression != null) {
                TrackingBindingTrace trackingBindingTrace5 = this.trace;
                Diagnostic diagnosticOn5 = Errors.UNRESOLVED_REFERENCE.on(ktCallableReferenceExpression.getCallableReference(), ktCallableReferenceExpression.getCallableReference());
                Intrinsics.checkNotNullExpressionValue(diagnosticOn5, "on(...)");
                trackingBindingTrace5.report(diagnosticOn5);
                return;
            }
            return;
        }
        if (diagnostic instanceof CallableReferenceCallCandidatesAmbiguity) {
            CallableReferenceCallCandidatesAmbiguity callableReferenceCallCandidatesAmbiguity = (CallableReferenceCallCandidatesAmbiguity) diagnostic;
            KtExpression psiExpression3 = NewCallArgumentsKt.getPsiExpression(callableReferenceCallCandidatesAmbiguity.getArgument());
            if (psiExpression3 instanceof KtPsiUtil.KtExpressionWrapper) {
                psiExpression3 = ((KtPsiUtil.KtExpressionWrapper) psiExpression3).getExpression();
            }
            KtCallableReferenceExpression ktCallableReferenceExpression2 = psiExpression3 instanceof KtCallableReferenceExpression ? (KtCallableReferenceExpression) psiExpression3 : null;
            Collection<CallableReferenceResolutionCandidate> candidates = callableReferenceCallCandidatesAmbiguity.getCandidates();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidates, 10));
            Iterator<T> it = candidates.iterator();
            while (it.hasNext()) {
                arrayList.add(((CallableReferenceResolutionCandidate) it.next()).getCandidate());
            }
            ArrayList arrayList2 = arrayList;
            if (ktCallableReferenceExpression2 != null) {
                TrackingBindingTrace trackingBindingTrace6 = this.trace;
                Diagnostic diagnosticOn6 = Errors.CALLABLE_REFERENCE_RESOLUTION_AMBIGUITY.on(ktCallableReferenceExpression2.getCallableReference(), arrayList2);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn6, "on(...)");
                DiagnosticUtilsKt.reportDiagnosticOnce(trackingBindingTrace6, diagnosticOn6);
                this.trace.record(BindingContext.AMBIGUOUS_REFERENCE_TARGET, ktCallableReferenceExpression2.getCallableReference(), arrayList2);
                return;
            }
            return;
        }
        if (diagnostic instanceof ArgumentNullabilityErrorDiagnostic) {
            reportNullabilityMismatchDiagnostic(callArgument, (ArgumentNullabilityMismatchDiagnostic) diagnostic);
            return;
        }
        if (diagnostic instanceof ArgumentNullabilityWarningDiagnostic) {
            reportNullabilityMismatchDiagnostic(callArgument, (ArgumentNullabilityMismatchDiagnostic) diagnostic);
            return;
        }
        if (diagnostic instanceof CallableReferencesDefaultArgumentUsed) {
            KtCallableReferenceExpression ktCallableReferenceExpressionExtractCallableReferenceExpression = CallUtilKt.extractCallableReferenceExpression(((CallableReferencesDefaultArgumentUsed) diagnostic).getArgument().getCall());
            if (ktCallableReferenceExpressionExtractCallableReferenceExpression == null) {
                throw new IllegalArgumentException("A call element must be callable reference for `CallableReferencesDefaultArgumentUsed`".toString());
            }
            TrackingBindingTrace trackingBindingTrace7 = this.trace;
            Diagnostic diagnosticOn7 = Errors.UNSUPPORTED_FEATURE.on(ktCallableReferenceExpressionExtractCallableReferenceExpression, TuplesKt.to(LanguageFeature.FunctionReferenceWithDefaultValueAsOtherType, this.context.languageVersionSettings));
            Intrinsics.checkNotNullExpressionValue(diagnosticOn7, "on(...)");
            trackingBindingTrace7.report(diagnosticOn7);
            return;
        }
        if (diagnostic instanceof ResolvedToSamWithVarargDiagnostic) {
            TrackingBindingTrace trackingBindingTrace8 = this.trace;
            Diagnostic diagnosticOn8 = Errors.TYPE_INFERENCE_CANDIDATE_WITH_SAM_AND_VARARG.on(NewCallArgumentsKt.getPsiCallArgument(callArgument).getValueArgument().getCallElement());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn8, "on(...)");
            trackingBindingTrace8.report(diagnosticOn8);
            return;
        }
        if (diagnostic instanceof NotEnoughInformationForLambdaParameter) {
            NotEnoughInformationForLambdaParameter notEnoughInformationForLambdaParameter = (NotEnoughInformationForLambdaParameter) diagnostic;
            LambdaKotlinCallArgument lambdaArgument = notEnoughInformationForLambdaParameter.getLambdaArgument();
            int parameterIndex = notEnoughInformationForLambdaParameter.getParameterIndex();
            KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(NewCallArgumentsKt.getPsiCallArgument(lambdaArgument).getValueArgument().getArgumentExpression());
            if (ktExpressionDeparenthesize instanceof KtLambdaExpression) {
                valueParameters = ((KtLambdaExpression) ktExpressionDeparenthesize).getValueParameters();
            } else if (!(ktExpressionDeparenthesize instanceof KtNamedFunction)) {
                return;
            } else {
                valueParameters = ((KtNamedFunction) ktExpressionDeparenthesize).getValueParameters();
            }
            Intrinsics.checkNotNull(valueParameters);
            KtParameter ktParameter = (KtParameter) CollectionsKt.getOrNull(valueParameters, parameterIndex);
            if (ktParameter != null) {
                TrackingBindingTrace trackingBindingTrace9 = this.trace;
                Diagnostic diagnosticOn9 = Errors.CANNOT_INFER_PARAMETER_TYPE.on(ktParameter);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn9, "on(...)");
                trackingBindingTrace9.report(diagnosticOn9);
                return;
            }
            return;
        }
        if (diagnostic instanceof CompatibilityWarningOnArgument) {
            TrackingBindingTrace trackingBindingTrace10 = this.trace;
            Diagnostic diagnosticOn10 = Errors.COMPATIBILITY_WARNING.on(NewCallArgumentsKt.getPsiCallArgument(callArgument).getValueArgument().getCallElement(), ((CompatibilityWarningOnArgument) diagnostic).getCandidate());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn10, "on(...)");
            trackingBindingTrace10.report(diagnosticOn10);
            return;
        }
        if (diagnostic instanceof AdaptedCallableReferenceIsUsedWithReflection) {
            TrackingBindingTrace trackingBindingTrace11 = this.trace;
            Diagnostic diagnosticOn11 = Errors.ADAPTED_CALLABLE_REFERENCE_AGAINST_REFLECTION_TYPE.on(NewCallArgumentsKt.getPsiCallArgument(callArgument).getValueArgument().getCallElement());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn11, "on(...)");
            trackingBindingTrace11.report(diagnosticOn11);
            return;
        }
        if (diagnostic instanceof MultiLambdaBuilderInferenceRestriction) {
            TypeParameterMarker typeParameter = ((MultiLambdaBuilderInferenceRestriction) diagnostic).getTypeParameter();
            TypeParameterDescriptor typeParameterDescriptor = typeParameter instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) typeParameter : null;
            TrackingBindingTrace trackingBindingTrace12 = this.trace;
            DiagnosticFactory2<PsiElement, Name, Name> diagnosticFactory2 = Errors.BUILDER_INFERENCE_MULTI_LAMBDA_RESTRICTION;
            KtElement callElement = NewCallArgumentsKt.getPsiCallArgument(callArgument).getValueArgument().getCallElement();
            if (typeParameterDescriptor == null || (name2 = typeParameterDescriptor.getShortName()) == null) {
                name2 = SpecialNames.NO_NAME_PROVIDED;
            }
            if (typeParameterDescriptor == null || (containingDeclaration = typeParameterDescriptor.getContainingModule()) == null || (name3 = containingDeclaration.getShortName()) == null) {
                name3 = SpecialNames.NO_NAME_PROVIDED;
            }
            Diagnostic diagnosticOn12 = diagnosticFactory2.on(callElement, name2, name3);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn12, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trackingBindingTrace12, diagnosticOn12);
            return;
        }
        if (diagnostic instanceof NotCallableMemberReference ? true : diagnostic instanceof NotCallableExpectedType) {
            return;
        }
        unknownError(diagnostic, "onCallArgument");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter
    public void onCallArgumentName(KotlinCallArgument callArgument, KotlinCallDiagnostic diagnostic) {
        KtSimpleNameExpression referenceExpression;
        Errors.BadNamedArgumentsTarget badNamedArgumentsTarget;
        Intrinsics.checkNotNullParameter(callArgument, "callArgument");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        ValueArgumentName argumentName = NewCallArgumentsKt.getPsiCallArgument(callArgument).getValueArgument().getArgumentName();
        if (argumentName == null || (referenceExpression = argumentName.getReferenceExpression()) == null) {
            return;
        }
        if (diagnostic instanceof NamedArgumentReference) {
            this.trace.record(BindingContext.REFERENCE_TARGET, referenceExpression, ((NamedArgumentReference) diagnostic).getParameterDescriptor());
            this.trace.markAsReported();
            return;
        }
        if (diagnostic instanceof NameForAmbiguousParameter) {
            TrackingBindingTrace trackingBindingTrace = this.trace;
            Diagnostic diagnosticOn = Errors.NAME_FOR_AMBIGUOUS_PARAMETER.on(referenceExpression);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            trackingBindingTrace.report(diagnosticOn);
            return;
        }
        if (diagnostic instanceof NameNotFound) {
            TrackingBindingTrace trackingBindingTrace2 = this.trace;
            Diagnostic diagnosticOn2 = Errors.NAMED_PARAMETER_NOT_FOUND.on(referenceExpression, referenceExpression);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
            trackingBindingTrace2.report(diagnosticOn2);
            return;
        }
        if (diagnostic instanceof NamedArgumentNotAllowed) {
            TrackingBindingTrace trackingBindingTrace3 = this.trace;
            DiagnosticFactory1<PsiElement, Errors.BadNamedArgumentsTarget> diagnosticFactory1 = Errors.NAMED_ARGUMENTS_NOT_ALLOWED;
            KtSimpleNameExpression ktSimpleNameExpression = referenceExpression;
            CallableDescriptor descriptor = ((NamedArgumentNotAllowed) diagnostic).getDescriptor();
            if (descriptor instanceof FunctionInvokeDescriptor) {
                badNamedArgumentsTarget = Errors.BadNamedArgumentsTarget.INVOKE_ON_FUNCTION_TYPE;
            } else {
                badNamedArgumentsTarget = descriptor instanceof DeserializedCallableMemberDescriptor ? Errors.BadNamedArgumentsTarget.INTEROP_FUNCTION : Errors.BadNamedArgumentsTarget.NON_KOTLIN_FUNCTION;
            }
            Diagnostic diagnosticOn3 = diagnosticFactory1.on(ktSimpleNameExpression, badNamedArgumentsTarget);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn3, "on(...)");
            trackingBindingTrace3.report(diagnosticOn3);
            return;
        }
        if (!(diagnostic instanceof ArgumentPassedTwice)) {
            unknownError(diagnostic, "onCallArgumentName");
            return;
        }
        TrackingBindingTrace trackingBindingTrace4 = this.trace;
        Diagnostic diagnosticOn4 = Errors.ARGUMENT_PASSED_TWICE.on(referenceExpression);
        Intrinsics.checkNotNullExpressionValue(diagnosticOn4, "on(...)");
        trackingBindingTrace4.report(diagnosticOn4);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter
    public void onCallArgumentSpread(KotlinCallArgument callArgument, KotlinCallDiagnostic diagnostic) {
        LeafPsiElement spreadElement;
        Intrinsics.checkNotNullParameter(callArgument, "callArgument");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        if (diagnostic instanceof NonVarargSpread) {
            PSIKotlinCallArgument pSIKotlinCallArgument = callArgument instanceof PSIKotlinCallArgument ? (PSIKotlinCallArgument) callArgument : null;
            ExpressionKotlinCallArgumentImpl expressionKotlinCallArgumentImpl = callArgument instanceof ExpressionKotlinCallArgumentImpl ? (ExpressionKotlinCallArgumentImpl) callArgument : null;
            if (expressionKotlinCallArgumentImpl != null) {
                LeafPsiElement spreadElement2 = expressionKotlinCallArgumentImpl.getValueArgument().getSpreadElement();
                if (spreadElement2 != null) {
                    this.trace.report(Errors.NON_VARARG_SPREAD.onError(spreadElement2));
                    return;
                }
                return;
            }
            if (pSIKotlinCallArgument == null || (spreadElement = pSIKotlinCallArgument.getValueArgument().getSpreadElement()) == null) {
                return;
            }
            TrackingBindingTrace trackingBindingTrace = this.trace;
            DiagnosticFactoryForDeprecation0<LeafPsiElement> diagnosticFactoryForDeprecation0 = Errors.NON_VARARG_SPREAD;
            LanguageVersionSettings languageVersionSettings = this.context.languageVersionSettings;
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
            trackingBindingTrace.report(diagnosticFactoryForDeprecation0.on(languageVersionSettings, spreadElement));
            return;
        }
        unknownError(diagnostic, "onCallArgumentSpread");
    }

    /* JADX WARN: Code duplicated, block: B:21:0x00bc  */
    /* JADX WARN: Multi-variable type inference failed */
    private final void reportSmartCast(SmartCastDiagnostic smartCastDiagnostic) {
        SmartCastResult smartCastResultCheckAndRecordPossibleCast$default;
        ResolvedCall<? extends CallableDescriptor> resolvedCall;
        PSIKotlinCall psiKotlinCall;
        Call psiCall;
        ExpressionKotlinCallArgument argument = smartCastDiagnostic.getArgument();
        if (argument instanceof ExpressionKotlinCallArgumentImpl) {
            this.trace.markAsReported();
            ExpressionKotlinCallArgumentImpl expressionKotlinCallArgumentImpl = (ExpressionKotlinCallArgumentImpl) argument;
            Context contextReplaceDataFlowInfo = this.context.replaceDataFlowInfo(expressionKotlinCallArgumentImpl.getDataFlowInfoBeforeThisArgument());
            Intrinsics.checkNotNullExpressionValue(contextReplaceDataFlowInfo, "replaceDataFlowInfo(...)");
            BasicCallResolutionContext basicCallResolutionContext = (BasicCallResolutionContext) contextReplaceDataFlowInfo;
            KtExpression lastElementDeparenthesized = KtPsiUtil.getLastElementDeparenthesized(expressionKotlinCallArgumentImpl.getValueArgument().getArgumentExpression(), basicCallResolutionContext.statementFilter);
            BasicCallResolutionContext basicCallResolutionContext2 = basicCallResolutionContext;
            DataFlowValue dataFlowValueCreateDataFlowValue = this.dataFlowValueFactory.createDataFlowValue(argument.getReceiver().getReceiverValue(), basicCallResolutionContext2);
            Call call = getCall().getCallElement() instanceof KtBinaryExpression ? null : getCall();
            if (expressionKotlinCallArgumentImpl.getValueArgument().isExternal()) {
                smartCastResultCheckAndRecordPossibleCast$default = null;
            } else {
                smartCastResultCheckAndRecordPossibleCast$default = SmartCastManager.checkAndRecordPossibleCast$default(this.smartCastManager, dataFlowValueCreateDataFlowValue, smartCastDiagnostic.getSmartCastType(), lastElementDeparenthesized, basicCallResolutionContext2, call, false, null, 64, null);
            }
        } else if (argument instanceof ReceiverExpressionKotlinCallArgument) {
            this.trace.markAsReported();
            ReceiverValue receiverValue = argument.getReceiver().getReceiverValue();
            DataFlowValue dataFlowValueCreateDataFlowValue2 = this.dataFlowValueFactory.createDataFlowValue(receiverValue, this.context);
            SmartCastManager smartCastManager = this.smartCastManager;
            UnwrappedType smartCastType = smartCastDiagnostic.getSmartCastType();
            ExpressionReceiver expressionReceiver = receiverValue instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValue : null;
            smartCastResultCheckAndRecordPossibleCast$default = SmartCastManager.checkAndRecordPossibleCast$default(smartCastManager, dataFlowValueCreateDataFlowValue2, smartCastType, expressionReceiver != null ? expressionReceiver.getExpression() : null, this.context, getCall(), true, null, 64, null);
        } else {
            smartCastResultCheckAndRecordPossibleCast$default = null;
        }
        KotlinCall kotlinCall = smartCastDiagnostic.getKotlinCall();
        if (kotlinCall == null || (psiKotlinCall = PSIKotlinCallsKt.getPsiKotlinCall(kotlinCall)) == null || (psiCall = psiKotlinCall.getPsiCall()) == null) {
            resolvedCall = null;
        } else {
            BindingContext context = this.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            resolvedCall = CallUtilKt.getResolvedCall(psiCall, context);
        }
        NewResolvedCallImpl newResolvedCallImpl = resolvedCall instanceof NewResolvedCallImpl ? (NewResolvedCallImpl) resolvedCall : null;
        if (newResolvedCallImpl == null || smartCastResultCheckAndRecordPossibleCast$default == null) {
            return;
        }
        if (Intrinsics.areEqual(newResolvedCallImpl.getExtensionReceiver(), argument.getReceiver().getReceiverValue())) {
            newResolvedCallImpl.updateExtensionReceiverWithSmartCastIfNeeded(smartCastResultCheckAndRecordPossibleCast$default.getResultType());
        }
        if (Intrinsics.areEqual(newResolvedCallImpl.getDispatchReceiver(), argument.getReceiver().getReceiverValue())) {
            newResolvedCallImpl.setSmartCastDispatchReceiverType(smartCastResultCheckAndRecordPossibleCast$default.getResultType());
        }
    }

    private final void reportUnstableSmartCast(UnstableSmartCast unstableSmartCast) {
        DataFlowValue dataFlowValueCreateDataFlowValue = this.dataFlowValueFactory.createDataFlowValue(unstableSmartCast.getArgument().getReceiver().getReceiverValue(), this.context);
        Set<KotlinType> typesFromSmartCasts = unstableSmartCast.getArgument().getReceiver().getTypesFromSmartCasts();
        KtExpression psiExpression = NewCallArgumentsKt.getPsiExpression(unstableSmartCast.getArgument());
        if (psiExpression == null) {
            return;
        }
        Set<KotlinType> set = typesFromSmartCasts;
        if (set.isEmpty()) {
            throw new IllegalArgumentException("Receiver for unstable smart cast without possible types".toString());
        }
        UnwrappedType unwrappedTypeIntersectWrappedTypes = IntersectionTypeKt.intersectWrappedTypes(set);
        this.trace.record(BindingContext.UNSTABLE_SMARTCAST, psiExpression, new SingleSmartCast(null, unwrappedTypeIntersectWrappedTypes));
        TrackingBindingTrace trackingBindingTrace = this.trace;
        Diagnostic diagnosticOn = Errors.SMARTCAST_IMPOSSIBLE.on(psiExpression, unwrappedTypeIntersectWrappedTypes, psiExpression.getText(), dataFlowValueCreateDataFlowValue.getKind().getDescription());
        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
        trackingBindingTrace.report(diagnosticOn);
    }

    private final void reportCallableReferenceConstraintError(NewConstraintMismatch error, KtSimpleNameExpression rhsExpression) {
        TrackingBindingTrace trackingBindingTrace = this.trace;
        Diagnostic diagnosticOn = Errors.TYPE_MISMATCH.on(rhsExpression, DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error), DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error));
        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
        trackingBindingTrace.report(diagnosticOn);
    }

    private final void reportConstraintErrorByPosition(NewConstraintMismatch error, ConstraintPosition position) {
        Call call;
        PSIKotlinCall psiKotlinCall;
        Call psiCall;
        if (position instanceof CallableReferenceConstraintPositionImpl) {
            KtCallableReferenceExpression ktCallableReferenceExpressionExtractCallableReferenceExpression = CallUtilKt.extractCallableReferenceExpression(((CallableReferenceConstraintPositionImpl) position).getCallableReferenceCall().getCall());
            if (ktCallableReferenceExpressionExtractCallableReferenceExpression == null) {
                throw new IllegalArgumentException("There should be the corresponding callable reference expression for `CallableReferenceConstraintPositionImpl`".toString());
            }
            KtSimpleNameExpression callableReference = ktCallableReferenceExpressionExtractCallableReferenceExpression.getCallableReference();
            Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
            reportCallableReferenceConstraintError(error, callableReference);
            return;
        }
        boolean z = error instanceof NewConstraintWarning;
        DiagnosticFactory2<KtExpression, KotlinType, KotlinType> diagnosticFactory2 = z ? Errors.TYPE_MISMATCH_WARNING : Errors.TYPE_MISMATCH;
        KFunction diagnosticReporterByTrackingStrategy$reportConstraintErrorByPosition$report$1 = z ? new DiagnosticReporterByTrackingStrategy$reportConstraintErrorByPosition$report$1(this.trace) : new DiagnosticReporterByTrackingStrategy$reportConstraintErrorByPosition$report$2(this.trace);
        if (position instanceof ArgumentConstraintPosition) {
            Object argument = ((ArgumentConstraintPosition) position).getArgument();
            Intrinsics.checkNotNull(argument, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument");
            Intrinsics.checkNotNull(diagnosticFactory2);
            reportArgumentConstraintErrorByPosition(error, (KotlinCallArgument) argument, z, diagnosticFactory2, null, (Function1) diagnosticReporterByTrackingStrategy$reportConstraintErrorByPosition$report$1);
            return;
        }
        if (position instanceof ReceiverConstraintPosition) {
            Object argument2 = ((ReceiverConstraintPosition) position).getArgument();
            Intrinsics.checkNotNull(argument2, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument");
            Intrinsics.checkNotNull(diagnosticFactory2);
            Intrinsics.checkNotNull(position, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.model.ReceiverConstraintPositionImpl");
            reportArgumentConstraintErrorByPosition(error, (KotlinCallArgument) argument2, z, diagnosticFactory2, ((ReceiverConstraintPositionImpl) position).getSelectorCall(), (Function1) diagnosticReporterByTrackingStrategy$reportConstraintErrorByPosition$report$1);
            return;
        }
        if (position instanceof LambdaArgumentConstraintPosition) {
            Object lambda = ((LambdaArgumentConstraintPosition) position).getLambda();
            Intrinsics.checkNotNull(lambda, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom");
            LambdaKotlinCallArgument atom = ((ResolvedLambdaAtom) lambda).getAtom();
            Intrinsics.checkNotNull(diagnosticFactory2);
            reportArgumentConstraintErrorByPosition(error, atom, z, diagnosticFactory2, null, (Function1) diagnosticReporterByTrackingStrategy$reportConstraintErrorByPosition$report$1);
            return;
        }
        if (position instanceof BuilderInferenceExpectedTypeConstraintPosition) {
            KotlinType lowerKotlinType = !TypeUtilsKt.isNullableNothing(DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error)) ? DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error) : TypeUtilsKt.makeNullable(DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error));
            TrackingBindingTrace trackingBindingTrace = this.trace;
            Diagnostic diagnosticOn = Errors.TYPE_MISMATCH.on(((BuilderInferenceExpectedTypeConstraintPosition) position).getTopLevelCall(), DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error), lowerKotlinType);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            trackingBindingTrace.report(diagnosticOn);
            return;
        }
        if (position instanceof ExpectedTypeConstraintPosition) {
            Object topLevelCall = ((ExpectedTypeConstraintPosition) position).getTopLevelCall();
            KotlinCall kotlinCall = topLevelCall instanceof KotlinCall ? (KotlinCall) topLevelCall : null;
            KtElement callElement = (kotlinCall == null || (psiKotlinCall = PSIKotlinCallsKt.getPsiKotlinCall(kotlinCall)) == null || (psiCall = psiKotlinCall.getPsiCall()) == null) ? null : psiCall.getCallElement();
            KtExpression ktExpression = callElement instanceof KtExpression ? (KtExpression) callElement : null;
            KotlinType lowerKotlinType2 = !TypeUtilsKt.isNullableNothing(DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error)) ? DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error) : TypeUtilsKt.makeNullable(DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error));
            if (ktExpression != null) {
                ParametrizedDiagnostic<E> parametrizedDiagnosticOn = diagnosticFactory2.on(ktExpression, DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error), lowerKotlinType2);
                Intrinsics.checkNotNullExpressionValue(parametrizedDiagnosticOn, "on(...)");
                ((Function1) diagnosticReporterByTrackingStrategy$reportConstraintErrorByPosition$report$1).invoke(parametrizedDiagnosticOn);
                return;
            }
            return;
        }
        if (position instanceof BuilderInferenceSubstitutionConstraintPosition) {
            reportConstraintErrorByPosition(error, ((BuilderInferenceSubstitutionConstraintPosition) position).getInitialConstraint().getPosition());
            return;
        }
        if (position instanceof ExplicitTypeParameterConstraintPosition) {
            Object typeArgument = ((ExplicitTypeParameterConstraintPosition) position).getTypeArgument();
            Intrinsics.checkNotNull(typeArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.SimpleTypeArgumentImpl");
            KtTypeReference typeReference = ((SimpleTypeArgumentImpl) typeArgument).getTypeProjection().getTypeReference();
            if (typeReference == null) {
                return;
            }
            ParametrizedDiagnostic<E> parametrizedDiagnosticOn2 = (z ? Errors.UPPER_BOUND_VIOLATED_WARNING : Errors.UPPER_BOUND_VIOLATED).on(typeReference, DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error), DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error));
            Intrinsics.checkNotNullExpressionValue(parametrizedDiagnosticOn2, "on(...)");
            ((Function1) diagnosticReporterByTrackingStrategy$reportConstraintErrorByPosition$report$1).invoke(parametrizedDiagnosticOn2);
            return;
        }
        if (position instanceof FixVariableConstraintPosition) {
            List<KotlinCallDiagnostic> list = this.allDiagnostics;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    ConstraintSystemError constraintSystemError = KotlinCallDiagnosticsKt.getConstraintSystemError((KotlinCallDiagnostic) it.next());
                    if (constraintSystemError != null && (constraintSystemError instanceof NewConstraintError) && !(((NewConstraintError) constraintSystemError).getPosition().getFrom() instanceof FixVariableConstraintPositionImpl)) {
                        return;
                    }
                }
            }
            Object resolvedAtom = ((FixVariableConstraintPosition) position).getResolvedAtom();
            ResolvedAtom resolvedAtom2 = resolvedAtom instanceof ResolvedAtom ? (ResolvedAtom) resolvedAtom : null;
            ResolutionAtom atom2 = resolvedAtom2 != null ? resolvedAtom2.getAtom() : null;
            PSIKotlinCall pSIKotlinCall = atom2 instanceof PSIKotlinCall ? (PSIKotlinCall) atom2 : null;
            if (pSIKotlinCall == null || (call = pSIKotlinCall.getPsiCall()) == null) {
                call = getCall();
            }
            KtExpression calleeExpression = call.getCalleeExpression();
            if (calleeExpression == null) {
                return;
            }
            TrackingBindingTrace trackingBindingTrace2 = this.trace;
            Diagnostic diagnosticOn2 = diagnosticFactory2.on(calleeExpression, DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error), DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error));
            Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trackingBindingTrace2, diagnosticOn2);
            return;
        }
        if (Intrinsics.areEqual(position, BuilderInferencePosition.INSTANCE)) {
            return;
        }
        if (position instanceof DeclaredUpperBoundConstraintPosition) {
            Intrinsics.checkNotNull(position, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.model.DeclaredUpperBoundConstraintPositionImpl");
            DeclaredUpperBoundConstraintPositionImpl declaredUpperBoundConstraintPositionImpl = (DeclaredUpperBoundConstraintPositionImpl) position;
            KotlinCall kotlinCall2 = declaredUpperBoundConstraintPositionImpl.getKotlinCall();
            TypeParameterDescriptor typeParameter = declaredUpperBoundConstraintPositionImpl.getTypeParameter();
            DeclarationDescriptor containingDeclaration = typeParameter.getContainingModule();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (getReportAdditionalErrors()) {
                TrackingBindingTrace trackingBindingTrace3 = this.trace;
                DiagnosticFactory4<KtElement, Name, Name, KotlinType, KotlinType> diagnosticFactory4 = Errors.UPPER_BOUND_VIOLATION_IN_CONSTRAINT;
                Intrinsics.checkNotNull(kotlinCall2, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCall");
                Diagnostic diagnosticOn3 = diagnosticFactory4.on(((PSIKotlinCall) kotlinCall2).getPsiCall().getCallElement(), typeParameter.getShortName(), containingDeclaration.getShortName(), DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error), DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error));
                Intrinsics.checkNotNullExpressionValue(diagnosticOn3, "on(...)");
                DiagnosticUtilsKt.reportDiagnosticOnce(trackingBindingTrace3, diagnosticOn3);
                return;
            }
            return;
        }
        if ((position instanceof DelegatedPropertyConstraintPosition) || (position instanceof KnownTypeParameterConstraintPosition)) {
            return;
        }
        if (position instanceof CallableReferenceConstraintPosition ? true : position instanceof IncorporationConstraintPosition ? true : position instanceof InjectedAnotherStubTypeConstraintPosition ? true : position instanceof LHSArgumentConstraintPosition ? true : Intrinsics.areEqual(position, SimpleConstraintSystemConstraintPosition.INSTANCE)) {
            if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
                throw new AssertionError("Constraint error in unexpected position: " + position);
            }
            if (getReportAdditionalErrors()) {
                ParametrizedDiagnostic<E> parametrizedDiagnosticOn3 = Errors.TYPE_MISMATCH_IN_CONSTRAINT.on(this.psiKotlinCall.getPsiCall().getCallElement(), DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error), DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error), position);
                Intrinsics.checkNotNullExpressionValue(parametrizedDiagnosticOn3, "on(...)");
                ((Function1) diagnosticReporterByTrackingStrategy$reportConstraintErrorByPosition$report$1).invoke(parametrizedDiagnosticOn3);
            }
        }
    }

    private final void reportArgumentConstraintErrorByPosition(NewConstraintMismatch error, KotlinCallArgument argument, boolean isWarning, DiagnosticFactory2<KtExpression, KotlinType, KotlinType> typeMismatchDiagnostic, KotlinCall selectorCall, Function1<? super Diagnostic, Unit> report) {
        Call psiCall;
        List<KtParameter> valueParameters;
        if (argument instanceof LambdaKotlinCallArgument) {
            UnwrappedType[] parametersTypes = ((LambdaKotlinCallArgument) argument).getParametersTypes();
            List list = parametersTypes != null ? ArraysKt.toList(parametersTypes) : null;
            if (list != null) {
                int iIndexOf = list.indexOf(DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error).unwrap());
                KtExpression psiExpression = NewCallArgumentsKt.getPsiExpression(argument);
                KtLambdaExpression ktLambdaExpression = psiExpression instanceof KtLambdaExpression ? (KtLambdaExpression) psiExpression : null;
                KtParameter ktParameter = (ktLambdaExpression == null || (valueParameters = ktLambdaExpression.getValueParameters()) == null) ? null : (KtParameter) CollectionsKt.getOrNull(valueParameters, iIndexOf);
                if (ktParameter != null) {
                    ParametrizedDiagnostic<E> parametrizedDiagnosticOn = (isWarning ? Errors.EXPECTED_PARAMETER_TYPE_MISMATCH_WARNING : Errors.EXPECTED_PARAMETER_TYPE_MISMATCH).on(ktParameter, DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error));
                    Intrinsics.checkNotNullExpressionValue(parametrizedDiagnosticOn, "on(...)");
                    report.invoke(parametrizedDiagnosticOn);
                    return;
                }
            }
        }
        KtExpression psiExpression2 = NewCallArgumentsKt.getPsiExpression(argument);
        if (psiExpression2 == null) {
            PSIKotlinCall pSIKotlinCall = selectorCall instanceof PSIKotlinCall ? (PSIKotlinCall) selectorCall : null;
            if (pSIKotlinCall == null || (psiCall = pSIKotlinCall.getPsiCall()) == null) {
                psiCall = this.psiKotlinCall.getPsiCall();
            }
            if (this.context.languageVersionSettings.supportsFeature(LanguageFeature.ProperTypeInferenceConstraintsProcessing) && getReportAdditionalErrors()) {
                DiagnosticFactory2<PsiElement, KotlinType, KotlinType> diagnosticFactory2 = Errors.RECEIVER_TYPE_MISMATCH;
                KtExpression calleeExpression = psiCall.getCalleeExpression();
                ParametrizedDiagnostic<E> parametrizedDiagnosticOn2 = diagnosticFactory2.on(calleeExpression != null ? calleeExpression : psiCall.getCallElement(), DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error), DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error));
                Intrinsics.checkNotNullExpressionValue(parametrizedDiagnosticOn2, "on(...)");
                report.invoke(parametrizedDiagnosticOn2);
                return;
            }
            return;
        }
        KtExpression ktExpressionSafeDeparenthesize = KtPsiUtil.safeDeparenthesize(psiExpression2);
        Intrinsics.checkNotNullExpressionValue(ktExpressionSafeDeparenthesize, "safeDeparenthesize(...)");
        if (reportConstantTypeMismatch(error, ktExpressionSafeDeparenthesize)) {
            return;
        }
        Object obj = this.trace.get(BindingContext.COMPILE_TIME_VALUE, ktExpressionSafeDeparenthesize);
        TypedCompileTimeConstant typedCompileTimeConstant = obj instanceof TypedCompileTimeConstant ? (TypedCompileTimeConstant) obj : null;
        if (typedCompileTimeConstant != null) {
            KotlinTypeInfo kotlinTypeInfo = (KotlinTypeInfo) this.trace.get(BindingContext.EXPRESSION_TYPE_INFO, psiExpression2);
            KotlinType type = kotlinTypeInfo != null ? kotlinTypeInfo.getType() : null;
            if (type != null && !UnsignedTypes.isUnsignedType(typedCompileTimeConstant.getType()) && UnsignedTypes.isUnsignedType(type)) {
                return;
            }
        }
        ParametrizedDiagnostic<E> parametrizedDiagnosticOn3 = typeMismatchDiagnostic.on(ktExpressionSafeDeparenthesize, DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(error), DiagnosticReporterByTrackingStrategyKt.getLowerKotlinType(error));
        Intrinsics.checkNotNullExpressionValue(parametrizedDiagnosticOn3, "on(...)");
        report.invoke(parametrizedDiagnosticOn3);
    }

    private final boolean getReportAdditionalErrors() {
        return !this.context.languageVersionSettings.supportsFeature(LanguageFeature.NoAdditionalErrorsInK1DiagnosticReporter);
    }

    /* JADX WARN: Code duplicated, block: B:41:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:64:0x0136  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter
    public void constraintError(ConstraintSystemError error) {
        String string;
        Diagnostic diagnosticOn;
        TypeParameterDescriptor originalTypeParameter;
        Name name2;
        KtExpression calleeExpression;
        KtExpression argumentExpression;
        String strAsString;
        DiagnosticFactory1<PsiElement, String> diagnosticFactory1;
        Object[] objArr;
        KtExpression callElement;
        KtExpression psiExpression;
        Intrinsics.checkNotNullParameter(error, "error");
        if (error instanceof NewConstraintMismatch) {
            NewConstraintMismatch newConstraintMismatch = (NewConstraintMismatch) error;
            reportConstraintErrorByPosition(newConstraintMismatch, newConstraintMismatch.getPosition().getFrom());
            return;
        }
        ArgumentConstraintPositionImpl argumentConstraintPositionImpl = null;
        if (error instanceof CapturedTypeFromSubtyping) {
            CapturedTypeFromSubtyping capturedTypeFromSubtyping = (CapturedTypeFromSubtyping) error;
            ConstraintPosition position = capturedTypeFromSubtyping.getPosition();
            ArgumentConstraintPositionImpl argumentConstraintPositionImpl2 = position instanceof ArgumentConstraintPositionImpl ? (ArgumentConstraintPositionImpl) position : null;
            if (argumentConstraintPositionImpl2 == null) {
                IncorporationConstraintPosition incorporationConstraintPosition = position instanceof IncorporationConstraintPosition ? (IncorporationConstraintPosition) position : null;
                ConstraintPosition from = incorporationConstraintPosition != null ? incorporationConstraintPosition.getFrom() : null;
                if (from instanceof ArgumentConstraintPositionImpl) {
                    argumentConstraintPositionImpl = (ArgumentConstraintPositionImpl) from;
                }
            } else {
                argumentConstraintPositionImpl = argumentConstraintPositionImpl2;
            }
            if (argumentConstraintPositionImpl == null || (psiExpression = NewCallArgumentsKt.getPsiExpression(argumentConstraintPositionImpl.getArgument())) == null) {
                return;
            }
            TrackingBindingTrace trackingBindingTrace = this.trace;
            Diagnostic diagnosticOn2 = Errors.NEW_INFERENCE_ERROR.on(psiExpression, "Capture type from subtyping " + capturedTypeFromSubtyping.getConstraintType() + " for variable " + capturedTypeFromSubtyping.getTypeVariable());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trackingBindingTrace, diagnosticOn2);
            return;
        }
        if (error instanceof InferredIntoDeclaredUpperBounds) {
            Call psiCall = this.psiKotlinCall.getPsiCall();
            if (psiCall instanceof CallTransformer.CallForImplicitInvoke) {
                callElement = ((CallTransformer.CallForImplicitInvoke) psiCall).getOuterCall().getCalleeExpression();
            } else {
                KtExpression calleeExpression2 = psiCall.getCalleeExpression();
                if (calleeExpression2 == null) {
                    callElement = psiCall.getCallElement();
                } else {
                    if (!calleeExpression2.getIsPhysical()) {
                        calleeExpression2 = null;
                    }
                    if (calleeExpression2 != null) {
                        callElement = calleeExpression2;
                    } else {
                        callElement = psiCall.getCallElement();
                    }
                }
            }
            if (callElement == null) {
                return;
            }
            TypeVariableMarker typeVariable = ((InferredIntoDeclaredUpperBounds) error).getTypeVariable();
            TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor = typeVariable instanceof TypeVariableFromCallableDescriptor ? (TypeVariableFromCallableDescriptor) typeVariable : null;
            if (typeVariableFromCallableDescriptor == null) {
                return;
            }
            TrackingBindingTrace trackingBindingTrace2 = this.trace;
            Diagnostic diagnosticOn3 = Errors.INFERRED_INTO_DECLARED_UPPER_BOUNDS.on(callElement, typeVariableFromCallableDescriptor.getOriginalTypeParameter().getShortName().asString());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn3, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trackingBindingTrace2, diagnosticOn3);
            return;
        }
        boolean z = true;
        if (error instanceof NotEnoughInformationForTypeParameterImpl) {
            NotEnoughInformationForTypeParameterImpl notEnoughInformationForTypeParameterImpl = (NotEnoughInformationForTypeParameterImpl) error;
            ResolvedAtom resolvedAtom = notEnoughInformationForTypeParameterImpl.getResolvedAtom();
            if (!isSpecialFunction(resolvedAtom)) {
                List<KotlinCallDiagnostic> list = this.allDiagnostics;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it = list.iterator();
                    do {
                        if (!it.hasNext()) {
                            z = false;
                            break;
                        }
                        KotlinCallDiagnostic kotlinCallDiagnostic = (KotlinCallDiagnostic) it.next();
                        if (kotlinCallDiagnostic instanceof WrongCountOfTypeArguments) {
                            objArr = true;
                        } else {
                            if (kotlinCallDiagnostic instanceof KotlinConstraintSystemDiagnostic) {
                                ConstraintSystemError error2 = ((KotlinConstraintSystemDiagnostic) kotlinCallDiagnostic).getError();
                                if (((error2 instanceof ConstrainingTypeIsError) && Intrinsics.areEqual(((ConstrainingTypeIsError) error2).getTypeVariable(), notEnoughInformationForTypeParameterImpl.getTypeVariable())) || (error2 instanceof NewConstraintError)) {
                                    objArr = true;
                                }
                            }
                            objArr = false;
                        }
                    } while (objArr == false);
                } else {
                    z = false;
                    break;
                }
                if (z) {
                    return;
                }
            }
            ResolutionAtom atom = notEnoughInformationForTypeParameterImpl.getResolvedAtom().getAtom();
            if (atom instanceof PSIKotlinCall) {
                Call psiCall2 = ((PSIKotlinCall) atom).getPsiCall();
                if (psiCall2 instanceof CallTransformer.CallForImplicitInvoke) {
                    argumentExpression = ((CallTransformer.CallForImplicitInvoke) psiCall2).getOuterCall().getCalleeExpression();
                } else {
                    argumentExpression = psiCall2.getCalleeExpression();
                }
            } else {
                argumentExpression = atom instanceof PSIKotlinCallArgument ? ((PSIKotlinCallArgument) atom).getValueArgument().getArgumentExpression() : getCall().getCalleeExpression();
            }
            if (argumentExpression == null) {
                return;
            }
            if (isSpecialFunction(resolvedAtom)) {
                reportNotEnoughInformationForTypeParameterForSpecialCall((ResolvedCallAtom) resolvedAtom, notEnoughInformationForTypeParameterImpl);
                return;
            }
            TypeVariableMarker typeVariable2 = notEnoughInformationForTypeParameterImpl.getTypeVariable();
            if (typeVariable2 instanceof TypeVariableFromCallableDescriptor) {
                strAsString = ((TypeVariableFromCallableDescriptor) typeVariable2).getOriginalTypeParameter().getShortName().asString();
            } else {
                if (!(typeVariable2 instanceof TypeVariableForLambdaReturnType)) {
                    throw new IllegalStateException(("Unsupported type variable: " + typeVariable2).toString());
                }
                strAsString = "return type of lambda";
            }
            Intrinsics.checkNotNull(strAsString);
            if (argumentExpression instanceof KtBlockExpression) {
                List<KtExpression> statements = ((KtBlockExpression) argumentExpression).getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                KtExpression ktExpression = (KtExpression) CollectionsKt.lastOrNull((List) statements);
                if (ktExpression != null) {
                    argumentExpression = ktExpression;
                }
            }
            if (notEnoughInformationForTypeParameterImpl.getCouldBeResolvedWithUnrestrictedBuilderInference()) {
                diagnosticFactory1 = Errors.COULD_BE_INFERRED_ONLY_WITH_UNRESTRICTED_BUILDER_INFERENCE;
            } else {
                diagnosticFactory1 = Errors.NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER;
            }
            TrackingBindingTrace trackingBindingTrace3 = this.trace;
            Diagnostic diagnosticOn4 = diagnosticFactory1.on(argumentExpression, strAsString);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn4, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trackingBindingTrace3, diagnosticOn4);
            return;
        }
        if (error instanceof OnlyInputTypesDiagnostic) {
            TypeVariableMarker typeVariable3 = ((OnlyInputTypesDiagnostic) error).getTypeVariable();
            TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor2 = typeVariable3 instanceof TypeVariableFromCallableDescriptor ? (TypeVariableFromCallableDescriptor) typeVariable3 : null;
            if (typeVariableFromCallableDescriptor2 == null || (calleeExpression = this.psiKotlinCall.getPsiCall().getCalleeExpression()) == null) {
                return;
            }
            TrackingBindingTrace trackingBindingTrace4 = this.trace;
            DiagnosticFactoryForDeprecation1<PsiElement, TypeParameterDescriptor> diagnosticFactoryForDeprecation1 = Errors.TYPE_INFERENCE_ONLY_INPUT_TYPES;
            LanguageVersionSettings languageVersionSettings = this.context.languageVersionSettings;
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
            trackingBindingTrace4.report(diagnosticFactoryForDeprecation1.on(languageVersionSettings, calleeExpression, typeVariableFromCallableDescriptor2.getOriginalTypeParameter()));
            return;
        }
        if (error instanceof InferredEmptyIntersectionError ? true : error instanceof InferredEmptyIntersectionWarning) {
            InferredEmptyIntersection inferredEmptyIntersection = (InferredEmptyIntersection) error;
            TypeVariableMarker typeVariable4 = inferredEmptyIntersection.getTypeVariable();
            KtExpression calleeExpression3 = this.psiKotlinCall.getPsiCall().getCalleeExpression();
            if (calleeExpression3 != null) {
                TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor3 = typeVariable4 instanceof TypeVariableFromCallableDescriptor ? (TypeVariableFromCallableDescriptor) typeVariable4 : null;
                if (typeVariableFromCallableDescriptor3 == null || (originalTypeParameter = typeVariableFromCallableDescriptor3.getOriginalTypeParameter()) == null || (name2 = originalTypeParameter.getShortName()) == null || (string = name2.asString()) == null) {
                    string = typeVariable4.toString();
                }
                Intrinsics.checkNotNull(string);
                List<KotlinTypeMarker> incompatibleTypes = inferredEmptyIntersection.getIncompatibleTypes();
                Intrinsics.checkNotNull(incompatibleTypes, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.types.KotlinType>");
                List<KotlinTypeMarker> causingTypes = inferredEmptyIntersection.getCausingTypes();
                Intrinsics.checkNotNull(causingTypes, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.types.KotlinType>");
                String str = Intrinsics.areEqual(incompatibleTypes, causingTypes) ? "" : ": " + CollectionsKt.joinToString$default(causingTypes, null, null, null, 0, null, null, 63, null);
                if (inferredEmptyIntersection.getKind().getIsDefinitelyEmpty()) {
                    DiagnosticFactoryForDeprecation4<PsiElement, String, Collection<KotlinType>, String, String> diagnosticFactoryForDeprecation4 = Errors.INFERRED_TYPE_VARIABLE_INTO_EMPTY_INTERSECTION;
                    LanguageVersionSettings languageVersionSettings2 = this.context.languageVersionSettings;
                    Intrinsics.checkNotNullExpressionValue(languageVersionSettings2, "languageVersionSettings");
                    diagnosticOn = diagnosticFactoryForDeprecation4.on(languageVersionSettings2, calleeExpression3, string, incompatibleTypes, inferredEmptyIntersection.getKind().getDescription(), str);
                } else {
                    diagnosticOn = Errors.INFERRED_TYPE_VARIABLE_INTO_POSSIBLE_EMPTY_INTERSECTION.on(calleeExpression3, string, incompatibleTypes, inferredEmptyIntersection.getKind().getDescription(), str);
                    Intrinsics.checkNotNull(diagnosticOn);
                }
                DiagnosticUtilsKt.reportDiagnosticOnce(this.trace, diagnosticOn);
                return;
            }
            return;
        }
        if (!(error instanceof ConstrainingTypeIsError) && !(error instanceof LowerPriorityToPreserveCompatibility) && !(error instanceof NoSuccessfulFork) && (error instanceof NotEnoughInformationForTypeParameter)) {
            throw new AssertionError("constraintError should not be called with " + error.getClass());
        }
    }

    private final void reportNullabilityMismatchDiagnostic(KotlinCallArgument callArgument, ArgumentNullabilityMismatchDiagnostic diagnostic) {
        DiagnosticFactory2<KtExpression, KotlinType, KotlinType> diagnosticFactory2;
        DiagnosticFactory1<KtConstantExpression, KotlinType> diagnosticFactory1;
        ValueArgument valueArgument;
        KtExpression argumentExpression;
        KtExpression ktExpression = null;
        PSIKotlinCallArgument pSIKotlinCallArgument = callArgument instanceof PSIKotlinCallArgument ? (PSIKotlinCallArgument) callArgument : null;
        if (pSIKotlinCallArgument != null && (valueArgument = pSIKotlinCallArgument.getValueArgument()) != null && (argumentExpression = valueArgument.getArgumentExpression()) != null) {
            KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(argumentExpression);
            ktExpression = ktExpressionDeparenthesize == null ? argumentExpression : ktExpressionDeparenthesize;
        }
        if (ktExpression != null) {
            if (PsiUtilsKt.isNull(ktExpression) && (ktExpression instanceof KtConstantExpression)) {
                if (diagnostic instanceof ArgumentNullabilityErrorDiagnostic) {
                    diagnosticFactory1 = Errors.NULL_FOR_NONNULL_TYPE;
                } else {
                    if (!(diagnostic instanceof ArgumentNullabilityWarningDiagnostic)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    diagnosticFactory1 = Errors.NULL_FOR_NONNULL_TYPE_WARNING;
                }
                TrackingBindingTrace trackingBindingTrace = this.trace;
                Diagnostic diagnosticOn = diagnosticFactory1.on(ktExpression, diagnostic.getExpectedType());
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                DiagnosticUtilsKt.reportDiagnosticOnce(trackingBindingTrace, diagnosticOn);
                return;
            }
            if (diagnostic instanceof ArgumentNullabilityErrorDiagnostic) {
                diagnosticFactory2 = Errors.TYPE_MISMATCH;
            } else {
                if (!(diagnostic instanceof ArgumentNullabilityWarningDiagnostic)) {
                    throw new NoWhenBranchMatchedException();
                }
                diagnosticFactory2 = Errors.TYPE_MISMATCH_WARNING;
            }
            TrackingBindingTrace trackingBindingTrace2 = this.trace;
            Diagnostic diagnosticOn2 = diagnosticFactory2.on(ktExpression, diagnostic.getExpectedType(), diagnostic.getActualType());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
            trackingBindingTrace2.report(diagnosticOn2);
        }
    }

    private final void reportNotEnoughInformationForTypeParameterForSpecialCall(ResolvedCallAtom resolvedAtom, NotEnoughInformationForTypeParameterImpl error) {
        KtExpression argumentsExpressionOrLastExpressionInBlock;
        Set<ResolvedAtom> subResolvedAtomsOfSpecialCallToReportUninferredTypeParameter = getSubResolvedAtomsOfSpecialCallToReportUninferredTypeParameter(resolvedAtom, error.getTypeVariable());
        if (subResolvedAtomsOfSpecialCallToReportUninferredTypeParameter.isEmpty()) {
            return;
        }
        Iterator<ResolvedAtom> it = subResolvedAtomsOfSpecialCallToReportUninferredTypeParameter.iterator();
        while (it.hasNext()) {
            Object atom = it.next().getAtom();
            ControlStructureTypingUtils.ResolveConstruct resolveConstruct = null;
            PSIKotlinCallArgument pSIKotlinCallArgument = atom instanceof PSIKotlinCallArgument ? (PSIKotlinCallArgument) atom : null;
            if (pSIKotlinCallArgument != null && (argumentsExpressionOrLastExpressionInBlock = getArgumentsExpressionOrLastExpressionInBlock(pSIKotlinCallArgument)) != null) {
                for (ControlStructureTypingUtils.ResolveConstruct resolveConstruct2 : ControlStructureTypingUtils.ResolveConstruct.values()) {
                    if (Intrinsics.areEqual(resolveConstruct2.getSpecialFunctionName(), resolvedAtom.get_candidateDescriptor().getShortName())) {
                        resolveConstruct = resolveConstruct2;
                        break;
                    }
                }
                if (resolveConstruct == null) {
                    throw new IllegalArgumentException(("Unsupported special construct: " + resolvedAtom.get_candidateDescriptor().getShortName() + " not found in special construct names").toString());
                }
                TrackingBindingTrace trackingBindingTrace = this.trace;
                Diagnostic diagnosticOn = Errors.NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER.on(argumentsExpressionOrLastExpressionInBlock, " for subcalls of " + resolveConstruct.getName() + " expression");
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                DiagnosticUtilsKt.reportDiagnosticOnce(trackingBindingTrace, diagnosticOn);
            }
        }
    }

    private final KtExpression getArgumentsExpressionOrLastExpressionInBlock(PSIKotlinCallArgument atom) {
        KtExpression argumentExpression = atom.getValueArgument().getArgumentExpression();
        if (!(argumentExpression instanceof KtBlockExpression)) {
            return argumentExpression;
        }
        List<KtExpression> statements = ((KtBlockExpression) argumentExpression).getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        return (KtExpression) CollectionsKt.lastOrNull((List) statements);
    }

    private final boolean containsUninferredTypeParameter(KotlinType kotlinType, final TypeVariableMarker typeVariableMarker) {
        return TypeUtilsKt.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.DiagnosticReporterByTrackingStrategy.containsUninferredTypeParameter.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(ErrorUtils.isUninferredTypeVariable(it) || Intrinsics.areEqual(it, TypeUtils.DONT_CARE) || Intrinsics.areEqual(it.getConstructor(), MarkerExtensionsKt.freshTypeConstructor(typeVariableMarker, this.typeSystemContext)));
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:19:0x004b  */
    private final Set<ResolvedAtom> getSubResolvedAtomsOfSpecialCallToReportUninferredTypeParameter(ResolvedAtom resolvedAtom, TypeVariableMarker uninferredTypeVariable) {
        UnwrappedType type;
        List<ResolvedAtom> subResolvedAtoms;
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        List<ResolvedAtom> subResolvedAtoms2 = resolvedAtom.getSubResolvedAtoms();
        if (subResolvedAtoms2 != null) {
            for (ResolvedAtom resolvedAtom2 : subResolvedAtoms2) {
                ResolutionAtom atom = resolvedAtom2.getAtom();
                if (!(resolvedAtom2 instanceof PostponedResolvedAtom)) {
                    if (!(atom instanceof SimpleKotlinCallArgument)) {
                        break;
                    }
                    type = ((SimpleKotlinCallArgument) atom).getReceiver().getReceiverValue().getType();
                    Intrinsics.checkNotNull(type);
                    if (containsUninferredTypeParameter(type, uninferredTypeVariable)) {
                        setCreateSetBuilder.add(resolvedAtom2);
                    }
                    subResolvedAtoms = resolvedAtom2.getSubResolvedAtoms();
                    if (subResolvedAtoms == null) {
                    }
                } else {
                    UnwrappedType expectedType = ((PostponedResolvedAtom) resolvedAtom2).getExpectedType();
                    if (expectedType == null) {
                        break;
                    }
                    type = expectedType;
                    Intrinsics.checkNotNull(type);
                    if (containsUninferredTypeParameter(type, uninferredTypeVariable)) {
                        setCreateSetBuilder.add(resolvedAtom2);
                    }
                    subResolvedAtoms = resolvedAtom2.getSubResolvedAtoms();
                    if (subResolvedAtoms == null && !subResolvedAtoms.isEmpty()) {
                        setCreateSetBuilder.addAll(getSubResolvedAtomsOfSpecialCallToReportUninferredTypeParameter(resolvedAtom2, uninferredTypeVariable));
                    }
                }
            }
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    private final boolean isSpecialFunction(ResolvedAtom atom) {
        if (!(atom instanceof ResolvedCallAtom)) {
            return false;
        }
        for (ControlStructureTypingUtils.ResolveConstruct resolveConstruct : ControlStructureTypingUtils.ResolveConstruct.values()) {
            if (Intrinsics.areEqual(resolveConstruct.getSpecialFunctionName(), ((ResolvedCallAtom) atom).get_candidateDescriptor().getShortName())) {
                return true;
            }
        }
        return false;
    }

    private final boolean reportConstantTypeMismatch(NewConstraintMismatch constraintError, KtExpression expression) {
        if (!(expression instanceof KtConstantExpression)) {
            return false;
        }
        ModuleDescriptor module = DescriptorUtilsKt.getModule(this.context.scope.getOwnerDescriptor());
        ConstantExpressionEvaluator constantExpressionEvaluator = this.constantExpressionEvaluator;
        TrackingBindingTrace trackingBindingTrace = this.trace;
        KotlinType expectedType = this.context.expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        return new CompileTimeConstantChecker(this.context, module, true).checkConstantExpressionType(constantExpressionEvaluator.evaluateToConstantValue(expression, trackingBindingTrace, expectedType), (KtConstantExpression) expression, DiagnosticReporterByTrackingStrategyKt.getUpperKotlinType(constraintError));
    }
}
