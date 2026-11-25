package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.extensions.internal.CandidateInterceptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.ImplicitIntegerCoercion;
import org.jetbrains.kotlin.resolve.MissingSupertypesResolver;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.StatementFilterKt;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.CallTransformer;
import org.jetbrains.kotlin.resolve.calls.DiagnosticReporterByTrackingStrategy;
import org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.components.AdditionalDiagnosticReporter;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CallPosition;
import org.jetbrains.kotlin.resolve.calls.inference.DescriptorRelatedInferenceUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.model.AllCandidatesResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMapping;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMappingKt;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch;
import org.jetbrains.kotlin.resolve.calls.model.CallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.CompletedCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.ErrorCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallKind;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallContainer;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallableReferenceCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.SingleCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastManager;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.error.ErrorScopeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.DataFlowAnalyzer;
import org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContextDelegate;

/* JADX INFO: compiled from: KotlinToResolvedCallTransformer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u009e\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 s2\u00020\u0001:\u0001sB¡\u0001\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\u0006\u0010\u0017\u001a\u00020\u0018\u0012\u0006\u0010\u0019\u001a\u00020\u001a\u0012\u0006\u0010\u001b\u001a\u00020\u001c\u0012\u0006\u0010\u001d\u001a\u00020\u001e\u0012\u0006\u0010\u001f\u001a\u00020 \u0012\u0006\u0010!\u001a\u00020\"\u0012\u0006\u0010#\u001a\u00020$\u0012\u0006\u0010%\u001a\u00020&¢\u0006\u0002\u0010'J!\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030-H\u0000¢\u0006\u0002\b.J\u001c\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\n\u0010/\u001a\u0006\u0012\u0002\b\u000300H\u0002J\u0018\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u00101\u001a\u000202H\u0002JB\u00103\u001a\b\u0012\u0004\u0012\u0002H400\"\b\b\u0000\u00104*\u0002052\u0006\u00106\u001a\u0002072\b\u0010*\u001a\u0004\u0018\u00010+2\b\u00108\u001a\u0004\u0018\u0001092\f\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;H\u0002J>\u0010=\u001a\b\u0012\u0004\u0012\u0002H400\"\b\b\u0000\u00104*\u0002052\u0006\u0010>\u001a\u0002072\u0006\u0010*\u001a\u00020+2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;2\b\u0010?\u001a\u0004\u0018\u000109J\u0016\u0010@\u001a\u0004\u0018\u00010A2\n\u0010B\u001a\u0006\u0012\u0002\b\u00030CH\u0002J\u001a\u0010D\u001a\u0004\u0018\u00010E2\u0006\u0010F\u001a\u00020E2\u0006\u0010G\u001a\u00020HH\u0002J,\u0010I\u001a\u00020)2\u0006\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020M2\n\u0010,\u001a\u0006\u0012\u0002\b\u0003002\u0006\u0010N\u001a\u00020OH\u0002J\u001c\u0010P\u001a\b\u0012\u0002\b\u0003\u0018\u0001002\u0006\u0010F\u001a\u00020E2\u0006\u0010L\u001a\u00020MJ\u0018\u0010Q\u001a\u00020R2\u0006\u0010F\u001a\u00020E2\u0006\u0010*\u001a\u00020+H\u0002J,\u0010S\u001a\b\u0012\u0004\u0012\u0002H400\"\b\b\u0000\u00104*\u0002052\u0006\u0010T\u001a\u0002072\f\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;J8\u0010U\u001a\u00020)2\u0006\u0010L\u001a\u00020M2\u0006\u0010*\u001a\u00020+2\n\u0010,\u001a\u0006\u0012\u0002\b\u0003002\u0006\u0010V\u001a\u0002052\f\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;J0\u0010W\u001a\u00020)2\u0006\u0010L\u001a\u00020M2\u0006\u0010*\u001a\u00020+2\n\u0010,\u001a\u0006\u0012\u0002\b\u0003002\f\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;J\u001a\u0010X\u001a\u00020)2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030-2\u0006\u0010L\u001a\u00020MJ\u001a\u0010Y\u001a\u00020)2\u0006\u0010L\u001a\u00020M2\n\u0010,\u001a\u0006\u0012\u0002\b\u000300J\u001a\u0010Z\u001a\u00020)2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030-2\u0006\u0010[\u001a\u00020\\J.\u0010]\u001a\b\u0012\u0004\u0012\u0002H400\"\b\b\u0000\u00104*\u0002052\u0006\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020M2\u0006\u0010N\u001a\u00020OJB\u0010^\u001a\b\u0012\u0004\u0012\u0002H400\"\b\b\u0000\u00104*\u0002052\u0006\u0010_\u001a\u0002072\b\u0010*\u001a\u0004\u0018\u00010+2\n\b\u0002\u00108\u001a\u0004\u0018\u0001092\f\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;J4\u0010`\u001a\u0004\u0018\u00010a2\u0006\u0010F\u001a\u00020E2\b\u0010b\u001a\u0004\u0018\u00010c2\u0006\u0010L\u001a\u00020M2\b\u0010d\u001a\u0004\u0018\u00010e2\u0006\u0010f\u001a\u00020RJ8\u0010g\u001a\u0004\u0018\u00010a2\b\u0010h\u001a\u0004\u0018\u00010a2\b\u0010i\u001a\u0004\u0018\u00010a2\b\u0010j\u001a\u0004\u0018\u00010a2\u0006\u0010k\u001a\u00020E2\u0006\u0010L\u001a\u00020MH\u0002J<\u0010l\u001a\u00020)*\u00020M2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030-2\b\u0010m\u001a\u0004\u0018\u00010n2\b\u0010o\u001a\u0004\u0018\u00010p2\u0006\u0010q\u001a\u00020R2\u0006\u0010r\u001a\u00020RH\u0002R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinToResolvedCallTransformer;", "", "callCheckers", "", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "additionalTypeCheckers", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AdditionalTypeChecker;", "dataFlowAnalyzer", "Lorg/jetbrains/kotlin/types/expressions/DataFlowAnalyzer;", "argumentTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "constantExpressionEvaluator", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "doubleColonExpressionResolver", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver;", "additionalDiagnosticReporter", "Lorg/jetbrains/kotlin/resolve/calls/components/AdditionalDiagnosticReporter;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "typeSystemContext", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContextDelegate;", "smartCastManager", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastManager;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "missingSupertypesResolver", "Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "candidateInterceptor", "Lorg/jetbrains/kotlin/extensions/internal/CandidateInterceptor;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "(Ljava/lang/Iterable;Ljava/lang/Iterable;Lorg/jetbrains/kotlin/types/expressions/DataFlowAnalyzer;Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver;Lorg/jetbrains/kotlin/resolve/calls/components/AdditionalDiagnosticReporter;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContextDelegate;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SmartCastManager;Lorg/jetbrains/kotlin/types/TypeApproximator;Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;Lorg/jetbrains/kotlin/extensions/internal/CandidateInterceptor;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;)V", "bind", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "bind$frontend", "simpleResolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;", "variableAsFunction", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewVariableAsFunctionResolvedCallImpl;", "createOrGet", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "completedSimpleAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "resultSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "createStubResolvedCallAndWriteItToTrace", "candidate", "substitutor", "createTypeForConvertableConstant", "Lorg/jetbrains/kotlin/types/SimpleType;", "constant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "deparenthesizeOrGetSelector", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expression", "statementFilter", "Lorg/jetbrains/kotlin/resolve/StatementFilter;", "forwardCallToInferenceSession", "baseResolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "tracingStrategy", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "getResolvedCallForArgumentExpression", "hasNecessarySafeCall", "", "onlyTransform", "resolvedCallAtom", "reportCallDiagnostic", "resultingDescriptor", "reportDiagnostics", "runAdditionalReceiversCheckers", "runArgumentsChecks", "runCallCheckers", "callCheckerContext", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "transformAndReport", "transformToResolvedCall", "completedCallAtom", "updateRecordedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "convertedArgumentType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "reportErrorForTypeMismatch", "updateRecordedTypeForArgument", "updatedType", "recordedType", "recordedTypeForParenthesized", "argumentExpression", "checkReceiver", "receiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "receiverArgument", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "isExplicitReceiver", "implicitInvokeCheck", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinToResolvedCallTransformer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final AdditionalDiagnosticReporter additionalDiagnosticReporter;
    private final Iterable<AdditionalTypeChecker> additionalTypeCheckers;
    private final ArgumentTypeResolver argumentTypeResolver;
    private final KotlinBuiltIns builtIns;
    private final Iterable<CallChecker> callCheckers;
    private final KotlinCallComponents callComponents;
    private final CandidateInterceptor candidateInterceptor;
    private final ConstantExpressionEvaluator constantExpressionEvaluator;
    private final DataFlowAnalyzer dataFlowAnalyzer;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final DeprecationResolver deprecationResolver;
    private final DoubleColonExpressionResolver doubleColonExpressionResolver;
    private final ExpressionTypingServices expressionTypingServices;
    private final MissingSupertypesResolver missingSupertypesResolver;
    private final ModuleDescriptor moduleDescriptor;
    private final SmartCastManager smartCastManager;
    private final TypeApproximator typeApproximator;
    private final TypeSystemInferenceExtensionContextDelegate typeSystemContext;

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinToResolvedCallTransformer(Iterable<? extends CallChecker> callCheckers, Iterable<? extends AdditionalTypeChecker> additionalTypeCheckers, DataFlowAnalyzer dataFlowAnalyzer, ArgumentTypeResolver argumentTypeResolver, ConstantExpressionEvaluator constantExpressionEvaluator, DeprecationResolver deprecationResolver, ExpressionTypingServices expressionTypingServices, DoubleColonExpressionResolver doubleColonExpressionResolver, AdditionalDiagnosticReporter additionalDiagnosticReporter, ModuleDescriptor moduleDescriptor, DataFlowValueFactory dataFlowValueFactory, KotlinBuiltIns builtIns, TypeSystemInferenceExtensionContextDelegate typeSystemContext, SmartCastManager smartCastManager, TypeApproximator typeApproximator, MissingSupertypesResolver missingSupertypesResolver, CandidateInterceptor candidateInterceptor, KotlinCallComponents callComponents) {
        Intrinsics.checkNotNullParameter(callCheckers, "callCheckers");
        Intrinsics.checkNotNullParameter(additionalTypeCheckers, "additionalTypeCheckers");
        Intrinsics.checkNotNullParameter(dataFlowAnalyzer, "dataFlowAnalyzer");
        Intrinsics.checkNotNullParameter(argumentTypeResolver, "argumentTypeResolver");
        Intrinsics.checkNotNullParameter(constantExpressionEvaluator, "constantExpressionEvaluator");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        Intrinsics.checkNotNullParameter(doubleColonExpressionResolver, "doubleColonExpressionResolver");
        Intrinsics.checkNotNullParameter(additionalDiagnosticReporter, "additionalDiagnosticReporter");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        Intrinsics.checkNotNullParameter(smartCastManager, "smartCastManager");
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(missingSupertypesResolver, "missingSupertypesResolver");
        Intrinsics.checkNotNullParameter(candidateInterceptor, "candidateInterceptor");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        this.callCheckers = callCheckers;
        this.additionalTypeCheckers = additionalTypeCheckers;
        this.dataFlowAnalyzer = dataFlowAnalyzer;
        this.argumentTypeResolver = argumentTypeResolver;
        this.constantExpressionEvaluator = constantExpressionEvaluator;
        this.deprecationResolver = deprecationResolver;
        this.expressionTypingServices = expressionTypingServices;
        this.doubleColonExpressionResolver = doubleColonExpressionResolver;
        this.additionalDiagnosticReporter = additionalDiagnosticReporter;
        this.moduleDescriptor = moduleDescriptor;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.builtIns = builtIns;
        this.typeSystemContext = typeSystemContext;
        this.smartCastManager = smartCastManager;
        this.typeApproximator = typeApproximator;
        this.missingSupertypesResolver = missingSupertypesResolver;
        this.candidateInterceptor = candidateInterceptor;
        this.callComponents = callComponents;
    }

    /* JADX INFO: compiled from: KotlinToResolvedCallTransformer.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinToResolvedCallTransformer$Companion;", "", "()V", "REPORT_MISSING_NEW_INFERENCE_DIAGNOSTIC", "", "getREPORT_MISSING_NEW_INFERENCE_DIAGNOSTIC", "()Z", "keyForPartiallyResolvedCall", "Lorg/jetbrains/kotlin/psi/Call;", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean getREPORT_MISSING_NEW_INFERENCE_DIAGNOSTIC() {
            return false;
        }

        private Companion() {
        }

        public final Call keyForPartiallyResolvedCall(ResolvedCallAtom resolvedCallAtom) {
            Intrinsics.checkNotNullParameter(resolvedCallAtom, "resolvedCallAtom");
            PSIKotlinCall psiKotlinCall = PSIKotlinCallsKt.getPsiKotlinCall(resolvedCallAtom.getAtom());
            if (psiKotlinCall instanceof PSIKotlinCallForInvoke) {
                return ((PSIKotlinCallForInvoke) psiKotlinCall).getBaseCall().getPsiCall();
            }
            return psiKotlinCall.getPsiCall();
        }
    }

    public final <D extends CallableDescriptor> NewAbstractResolvedCall<D> onlyTransform(ResolvedCallAtom resolvedCallAtom, Collection<? extends KotlinCallDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(resolvedCallAtom, "resolvedCallAtom");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        return transformToResolvedCall(resolvedCallAtom, null, null, diagnostics);
    }

    public final <D extends CallableDescriptor> NewAbstractResolvedCall<D> transformAndReport(CallResolutionResult baseResolvedCall, BasicCallResolutionContext context, TracingStrategy tracingStrategy) {
        List<ResolvedAtom> subResolvedAtoms;
        Intrinsics.checkNotNullParameter(baseResolvedCall, "baseResolvedCall");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tracingStrategy, "tracingStrategy");
        if (baseResolvedCall instanceof PartialCallResolutionResult) {
            PartialCallResolutionResult partialCallResolutionResult = (PartialCallResolutionResult) baseResolvedCall;
            ResolvedCallAtom resultCallAtom = partialCallResolutionResult.getResultCallAtom();
            Call callKeyForPartiallyResolvedCall = INSTANCE.keyForPartiallyResolvedCall(resultCallAtom);
            context.trace.record(BindingContext.ONLY_RESOLVED_CALL, callKeyForPartiallyResolvedCall, new PartialCallContainer(partialCallResolutionResult));
            context.trace.record(BindingContext.PARTIAL_CALL_RESOLUTION_CONTEXT, callKeyForPartiallyResolvedCall, context);
            if (partialCallResolutionResult.getForwardToInferenceSession()) {
                context.inferenceSession.addPartialCallInfo(new PSIPartialCallInfo(partialCallResolutionResult, context, tracingStrategy));
            }
            BindingTrace trace = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            return createStubResolvedCallAndWriteItToTrace(resultCallAtom, trace, baseResolvedCall.getDiagnostics(), null);
        }
        if (baseResolvedCall instanceof CompletedCallResolutionResult ? true : baseResolvedCall instanceof ErrorCallResolutionResult) {
            SingleCallResolutionResult singleCallResolutionResult = (SingleCallResolutionResult) baseResolvedCall;
            ResolvedCallAtom resultCallAtom2 = singleCallResolutionResult.getResultCallAtom();
            NewTypeSubstitutor newTypeSubstitutorBuildResultingSubstitutor$default = DescriptorRelatedInferenceUtilsKt.buildResultingSubstitutor$default(baseResolvedCall.getConstraintSystem().getBuilder().currentStorage(), this.typeSystemContext, false, 2, null);
            if (context.inferenceSession.writeOnlyStubs(singleCallResolutionResult)) {
                BindingTrace trace2 = context.trace;
                Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                NewAbstractResolvedCall<D> newAbstractResolvedCallCreateStubResolvedCallAndWriteItToTrace = createStubResolvedCallAndWriteItToTrace(resultCallAtom2, trace2, baseResolvedCall.getDiagnostics(), newTypeSubstitutorBuildResultingSubstitutor$default);
                forwardCallToInferenceSession(baseResolvedCall, context, newAbstractResolvedCallCreateStubResolvedCallAndWriteItToTrace, tracingStrategy);
                Intrinsics.checkNotNull(newAbstractResolvedCallCreateStubResolvedCallAndWriteItToTrace, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall<D of org.jetbrains.kotlin.resolve.calls.tower.KotlinToResolvedCallTransformer.transformAndReport>");
                return newAbstractResolvedCallCreateStubResolvedCallAndWriteItToTrace;
            }
            ResolvedAtomCompleter resolvedAtomCompleter = new ResolvedAtomCompleter(newTypeSubstitutorBuildResultingSubstitutor$default, context, this, this.expressionTypingServices, this.argumentTypeResolver, this.doubleColonExpressionResolver, this.builtIns, this.deprecationResolver, this.moduleDescriptor, this.dataFlowValueFactory, this.typeApproximator, this.missingSupertypesResolver, this.callComponents);
            if (context.inferenceSession.shouldCompleteResolvedSubAtomsOf(resultCallAtom2) && (subResolvedAtoms = resultCallAtom2.getSubResolvedAtoms()) != null) {
                Iterator<T> it = subResolvedAtoms.iterator();
                while (it.hasNext()) {
                    resolvedAtomCompleter.completeAll((ResolvedAtom) it.next());
                }
            }
            NewAbstractResolvedCall<D> newAbstractResolvedCall = (NewAbstractResolvedCall<D>) resolvedAtomCompleter.completeResolvedCall(resultCallAtom2, baseResolvedCall.completedDiagnostic(newTypeSubstitutorBuildResultingSubstitutor$default));
            Intrinsics.checkNotNull(newAbstractResolvedCall, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall<D of org.jetbrains.kotlin.resolve.calls.tower.KotlinToResolvedCallTransformer.transformAndReport>");
            forwardCallToInferenceSession(baseResolvedCall, context, newAbstractResolvedCall, tracingStrategy);
            return newAbstractResolvedCall;
        }
        if (baseResolvedCall instanceof SingleCallResolutionResult) {
            throw new IllegalStateException(("Call resolution result for one candidate didn't transformed: " + baseResolvedCall).toString());
        }
        if (baseResolvedCall instanceof AllCandidatesResolutionResult) {
            throw new IllegalStateException("Cannot transform result for ALL_CANDIDATES mode".toString());
        }
        throw new NoWhenBranchMatchedException();
    }

    private final void forwardCallToInferenceSession(CallResolutionResult baseResolvedCall, BasicCallResolutionContext context, NewAbstractResolvedCall<?> resolvedCall, TracingStrategy tracingStrategy) {
        if (baseResolvedCall instanceof CompletedCallResolutionResult) {
            context.inferenceSession.addCompletedCallInfo(new PSICompletedCallInfo((CompletedCallResolutionResult) baseResolvedCall, context, resolvedCall, tracingStrategy));
        }
    }

    public final <D extends CallableDescriptor> NewAbstractResolvedCall<D> createStubResolvedCallAndWriteItToTrace(ResolvedCallAtom candidate, BindingTrace trace, Collection<? extends KotlinCallDiagnostic> diagnostics, NewTypeSubstitutor substitutor) {
        TracingStrategy tracingStrategy;
        PSIKotlinCallImpl baseCall;
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        NewAbstractResolvedCall<D> newAbstractResolvedCallTransformToResolvedCall = transformToResolvedCall(candidate, trace, substitutor, diagnostics);
        PSIKotlinCall psiKotlinCall = PSIKotlinCallsKt.getPsiKotlinCall(candidate.getAtom());
        PSIKotlinCallForInvoke pSIKotlinCallForInvoke = psiKotlinCall instanceof PSIKotlinCallForInvoke ? (PSIKotlinCallForInvoke) psiKotlinCall : null;
        if (pSIKotlinCallForInvoke == null || (baseCall = pSIKotlinCallForInvoke.getBaseCall()) == null || (tracingStrategy = baseCall.getTracingStrategy()) == null) {
            tracingStrategy = psiKotlinCall.getTracingStrategy();
        }
        NewAbstractResolvedCall<D> newAbstractResolvedCall = newAbstractResolvedCallTransformToResolvedCall;
        tracingStrategy.bindReference(trace, newAbstractResolvedCall);
        tracingStrategy.bindResolvedCall(trace, newAbstractResolvedCall);
        return newAbstractResolvedCallTransformToResolvedCall;
    }

    public static /* synthetic */ NewAbstractResolvedCall transformToResolvedCall$default(KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer, ResolvedCallAtom resolvedCallAtom, BindingTrace bindingTrace, NewTypeSubstitutor newTypeSubstitutor, Collection collection, int i, Object obj) {
        if ((i & 4) != 0) {
            newTypeSubstitutor = null;
        }
        return kotlinToResolvedCallTransformer.transformToResolvedCall(resolvedCallAtom, bindingTrace, newTypeSubstitutor, collection);
    }

    public final <D extends CallableDescriptor> NewAbstractResolvedCall<D> transformToResolvedCall(ResolvedCallAtom completedCallAtom, BindingTrace trace, NewTypeSubstitutor resultSubstitutor, Collection<? extends KotlinCallDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(completedCallAtom, "completedCallAtom");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        PSIKotlinCall psiKotlinCall = PSIKotlinCallsKt.getPsiKotlinCall(completedCallAtom.getAtom());
        completedCallAtom.setCandidateDescriptor(this.candidateInterceptor.interceptResolvedCallAtomCandidate(completedCallAtom.get_candidateDescriptor(), completedCallAtom, trace, resultSubstitutor, diagnostics));
        if (psiKotlinCall instanceof PSIKotlinCallForInvoke) {
            List listEmptyList = completedCallAtom.get_candidateDescriptor() instanceof FunctionDescriptor ? CollectionsKt.emptyList() : diagnostics;
            if (!(completedCallAtom.get_candidateDescriptor() instanceof FunctionDescriptor)) {
                diagnostics = CollectionsKt.emptyList();
            }
            return new NewVariableAsFunctionResolvedCallImpl(createOrGet(((PSIKotlinCallForInvoke) psiKotlinCall).getVariableCall().getResolvedCall(), trace, resultSubstitutor, listEmptyList), createOrGet(completedCallAtom, trace, resultSubstitutor, diagnostics));
        }
        return createOrGet(completedCallAtom, trace, resultSubstitutor, diagnostics);
    }

    private final <D extends CallableDescriptor> NewAbstractResolvedCall<D> createOrGet(ResolvedCallAtom completedSimpleAtom, BindingTrace trace, NewTypeSubstitutor resultSubstitutor, Collection<? extends KotlinCallDiagnostic> diagnostics) {
        NewResolvedCallImpl resolvedPsiKotlinCall;
        if (trace != null && (resolvedPsiKotlinCall = PSIKotlinCallsKt.getResolvedPsiKotlinCall(PSIKotlinCallsKt.getPsiKotlinCall(completedSimpleAtom.getAtom()), trace)) != null) {
            resolvedPsiKotlinCall.setResultingSubstitutor(resultSubstitutor);
            resolvedPsiKotlinCall.updateDiagnostics(diagnostics);
            return resolvedPsiKotlinCall;
        }
        if (completedSimpleAtom.getAtom().getCallKind() == KotlinCallKind.CALLABLE_REFERENCE) {
            Intrinsics.checkNotNull(completedSimpleAtom, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ResolvedCallableReferenceCallAtom");
            TypeApproximator typeApproximator = this.typeApproximator;
            LanguageVersionSettings languageVersionSettings = this.expressionTypingServices.getLanguageVersionSettings();
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "getLanguageVersionSettings(...)");
            return new NewCallableReferenceResolvedCall((ResolvedCallableReferenceCallAtom) completedSimpleAtom, typeApproximator, languageVersionSettings, resultSubstitutor);
        }
        TypeApproximator typeApproximator2 = this.typeApproximator;
        LanguageVersionSettings languageVersionSettings2 = this.expressionTypingServices.getLanguageVersionSettings();
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings2, "getLanguageVersionSettings(...)");
        return new NewResolvedCallImpl(completedSimpleAtom, resultSubstitutor, diagnostics, typeApproximator2, languageVersionSettings2);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0031  */
    public final void runCallCheckers(ResolvedCall<?> resolvedCall, CallCheckerContext callCheckerContext) {
        KtExpression calleeExpression;
        KtExpression callElement;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(callCheckerContext, "callCheckerContext");
        boolean z = resolvedCall instanceof VariableAsFunctionResolvedCall;
        if (z) {
            calleeExpression = ((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall().get$fakeCall().getCalleeExpression();
        } else {
            calleeExpression = resolvedCall.get$fakeCall().getCalleeExpression();
        }
        if (calleeExpression != null) {
            callElement = calleeExpression;
            if (CallUtilKt.isFakeElement(callElement)) {
                callElement = resolvedCall.get$fakeCall().getCallElement();
            }
        } else {
            callElement = resolvedCall.get$fakeCall().getCallElement();
        }
        Intrinsics.checkNotNull(callElement);
        for (CallChecker callChecker : this.callCheckers) {
            KtElement ktElement = callElement;
            callChecker.check(resolvedCall, ktElement, callCheckerContext);
            if (z) {
                callChecker.check(((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall(), ktElement, callCheckerContext);
            }
        }
    }

    public final void runAdditionalReceiversCheckers(ResolvedCall<?> resolvedCall, BasicCallResolutionContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(context, "context");
        checkReceiver(context, resolvedCall, resolvedCall.getResultingDescriptor().getExtensionReceiverParameter(), resolvedCall.getExtensionReceiver(), resolvedCall.getExplicitReceiverKind().isExtensionReceiver(), false);
        checkReceiver(context, resolvedCall, resolvedCall.getResultingDescriptor().mo6753getDispatchReceiverParameter(), resolvedCall.getDispatchReceiver(), resolvedCall.getExplicitReceiverKind().isDispatchReceiver(), context.call instanceof CallTransformer.CallForImplicitInvoke);
    }

    private final void checkReceiver(BasicCallResolutionContext basicCallResolutionContext, ResolvedCall<?> resolvedCall, ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverValue receiverValue, boolean z, boolean z2) {
        if (receiverParameterDescriptor == null || receiverValue == null) {
            return;
        }
        boolean z3 = z && !z2 && resolvedCall.get$fakeCall().isSemanticallyEquivalentToSafeCall();
        Iterator<AdditionalTypeChecker> it = this.additionalTypeCheckers.iterator();
        while (it.hasNext()) {
            it.next().checkReceiver(receiverParameterDescriptor, receiverValue, z3, basicCallResolutionContext);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void runArgumentsChecks(BasicCallResolutionContext context, NewAbstractResolvedCall<?> resolvedCall) {
        Pair pair;
        ValueParameterDescriptor valueParameterDescriptor;
        SimpleType unknownIntegerType;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        if (resolvedCall instanceof NewResolvedCallImpl) {
            NewResolvedCallImpl newResolvedCallImpl = (NewResolvedCallImpl) resolvedCall;
            for (ValueArgument valueArgument : newResolvedCallImpl.get$fakeCall().getValueArguments()) {
                Intrinsics.checkNotNull(valueArgument);
                ArgumentMapping argumentMapping = resolvedCall.getArgumentMapping(valueArgument);
                UnwrappedType unwrappedTypeUnwrap = null;
                if (argumentMapping instanceof ArgumentMatch) {
                    ArgumentMatch argumentMatch = (ArgumentMatch) argumentMapping;
                    ValueParameterDescriptor valueParameter = argumentMatch.getValueParameter();
                    UnwrappedType expectedTypeForUnitConvertedArgument = newResolvedCallImpl.getExpectedTypeForUnitConvertedArgument(valueArgument);
                    pair = new Pair((expectedTypeForUnitConvertedArgument == null && (expectedTypeForUnitConvertedArgument = newResolvedCallImpl.getExpectedTypeForSuspendConvertedArgument(valueArgument)) == null && (expectedTypeForUnitConvertedArgument = newResolvedCallImpl.getExpectedTypeForSamConvertedArgument(valueArgument)) == null) ? CallResolverUtilKt.getEffectiveExpectedType(argumentMatch.getValueParameter(), valueArgument, context) : expectedTypeForUnitConvertedArgument, new CallPosition.ValueArgumentPosition(resolvedCall, argumentMatch.getValueParameter(), valueArgument));
                    valueParameterDescriptor = valueParameter;
                } else {
                    pair = new Pair(TypeUtils.NO_EXPECTED_TYPE, CallPosition.Unknown.INSTANCE);
                    valueParameterDescriptor = null;
                }
                Context contextReplaceCallPosition = ((BasicCallResolutionContext) ((BasicCallResolutionContext) context.replaceDataFlowInfo(newResolvedCallImpl.getDataFlowInfoForArguments().getInfo(valueArgument))).replaceExpectedType((KotlinType) pair.component1())).replaceCallPosition((CallPosition) pair.component2());
                Intrinsics.checkNotNullExpressionValue(contextReplaceCallPosition, "replaceCallPosition(...)");
                BasicCallResolutionContext basicCallResolutionContext = (BasicCallResolutionContext) contextReplaceCallPosition;
                IntegerValueTypeConstant argumentTypeForConstantConvertedArgument = newResolvedCallImpl.getArgumentTypeForConstantConvertedArgument(valueArgument);
                KtExpression argumentExpression = valueArgument.getArgumentExpression();
                if (argumentExpression != null) {
                    if (argumentTypeForConstantConvertedArgument != null) {
                        context.trace.record(BindingContext.COMPILE_TIME_VALUE, argumentExpression, argumentTypeForConstantConvertedArgument);
                        BindingContextUtils.updateRecordedType(argumentTypeForConstantConvertedArgument.getUnknownIntegerType(), argumentExpression, context.trace, false);
                    }
                    if (!valueArgument.isExternal()) {
                        if (argumentTypeForConstantConvertedArgument != null && (unknownIntegerType = argumentTypeForConstantConvertedArgument.getUnknownIntegerType()) != null) {
                            unwrappedTypeUnwrap = unknownIntegerType.unwrap();
                        }
                        updateRecordedType(argumentExpression, valueParameterDescriptor, basicCallResolutionContext, unwrappedTypeUnwrap, ArgumentMappingKt.isReallySuccess(resolvedCall));
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00a8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:31:0x00aa  */
    /* JADX WARN: Type inference failed for: r1v3, types: [org.jetbrains.kotlin.descriptors.CallableDescriptor] */
    public final KotlinType updateRecordedType(KtExpression expression, ValueParameterDescriptor parameter, BasicCallResolutionContext context, UnwrappedType convertedArgumentType, boolean reportErrorForTypeMismatch) {
        UnwrappedType unwrappedTypeMakeNullableTypeIfSafeReceiver;
        boolean z;
        SimpleType simpleTypeCreateTypeForConvertableConstant;
        KotlinType kotlinTypeUpdateResultArgumentTypeIfNotDenotable;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        KtExpression lastElementDeparenthesized = KtPsiUtil.getLastElementDeparenthesized(expression, context.statementFilter);
        if (lastElementDeparenthesized == null) {
            return null;
        }
        KotlinType type = context.trace.getType(lastElementDeparenthesized);
        KotlinType type2 = context.trace.getType(expression);
        if (convertedArgumentType != null) {
            unwrappedTypeMakeNullableTypeIfSafeReceiver = convertedArgumentType;
        } else {
            NewAbstractResolvedCall<?> resolvedCallForArgumentExpression = getResolvedCallForArgumentExpression(lastElementDeparenthesized, context);
            unwrappedTypeMakeNullableTypeIfSafeReceiver = resolvedCallForArgumentExpression != null ? ResolvedCallUtilKt.makeNullableTypeIfSafeReceiver(resolvedCallForArgumentExpression, resolvedCallForArgumentExpression.getResultingDescriptor().getReturnType(), context) : null;
            if (unwrappedTypeMakeNullableTypeIfSafeReceiver == null) {
                unwrappedTypeMakeNullableTypeIfSafeReceiver = type;
            }
        }
        if (type != null && !type.getConstructor().isDenotable() && (kotlinTypeUpdateResultArgumentTypeIfNotDenotable = this.argumentTypeResolver.updateResultArgumentTypeIfNotDenotable(context, lastElementDeparenthesized)) != null) {
            unwrappedTypeMakeNullableTypeIfSafeReceiver = kotlinTypeUpdateResultArgumentTypeIfNotDenotable;
        }
        if (parameter != null) {
            LanguageVersionSettings languageVersionSettings = context.languageVersionSettings;
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
            if (ImplicitIntegerCoercion.INSTANCE.isEnabledFor(parameter, languageVersionSettings)) {
                CompileTimeConstant<?> compileTimeConstant = (CompileTimeConstant) context.trace.get(BindingContext.COMPILE_TIME_VALUE, lastElementDeparenthesized);
                if (compileTimeConstant != null && compileTimeConstant.getParameters().isConvertableConstVal() && (simpleTypeCreateTypeForConvertableConstant = createTypeForConvertableConstant(compileTimeConstant)) != null) {
                    unwrappedTypeMakeNullableTypeIfSafeReceiver = this.argumentTypeResolver.updateResultArgumentTypeIfNotDenotable(context.trace, context.statementFilter, context.expectedType, simpleTypeCreateTypeForConvertableConstant, expression);
                    z = true;
                }
            } else if (convertedArgumentType != null) {
                context.trace.report(Errors.SIGNED_CONSTANT_CONVERTED_TO_UNSIGNED.on(lastElementDeparenthesized));
            }
            z = reportErrorForTypeMismatch;
        } else {
            if (convertedArgumentType != null) {
                context.trace.report(Errors.SIGNED_CONSTANT_CONVERTED_TO_UNSIGNED.on(lastElementDeparenthesized));
            }
            z = reportErrorForTypeMismatch;
        }
        KotlinType kotlinTypeUpdateRecordedTypeForArgument = updateRecordedTypeForArgument(unwrappedTypeMakeNullableTypeIfSafeReceiver, type, type2, expression, context);
        this.dataFlowAnalyzer.checkType(kotlinTypeUpdateRecordedTypeForArgument, lastElementDeparenthesized, context, z);
        return kotlinTypeUpdateRecordedTypeForArgument;
    }

    private final SimpleType createTypeForConvertableConstant(CompileTimeConstant<?> constant) {
        SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
        Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
        Object value = constant.getValue(NO_EXPECTED_TYPE);
        Number number = value instanceof Number ? (Number) value : null;
        if (number == null) {
            return null;
        }
        IntegerLiteralTypeConstructor integerLiteralTypeConstructor = new IntegerLiteralTypeConstructor(number.longValue(), this.moduleDescriptor, constant.getParameters());
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(TypeAttributes.INSTANCE.getEmpty(), integerLiteralTypeConstructor, CollectionsKt.emptyList(), false, ErrorUtils.createErrorScope(ErrorScopeKind.INTEGER_LITERAL_TYPE_SCOPE, true, integerLiteralTypeConstructor.toString()));
    }

    public final NewAbstractResolvedCall<?> getResolvedCallForArgumentExpression(KtExpression expression, BasicCallResolutionContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!ExpressionTypingUtils.dependsOnExpectedType(expression)) {
            return null;
        }
        BindingContext context2 = context.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(expression, context2);
        if (resolvedCall instanceof NewAbstractResolvedCall) {
            return (NewAbstractResolvedCall) resolvedCall;
        }
        return null;
    }

    private final KotlinType updateRecordedTypeForArgument(KotlinType updatedType, KotlinType recordedType, KotlinType recordedTypeForParenthesized, KtExpression argumentExpression, BasicCallResolutionContext context) {
        if ((!ErrorUtils.INSTANCE.containsErrorType(recordedType) && Intrinsics.areEqual(recordedType, updatedType) && Intrinsics.areEqual(recordedType, recordedTypeForParenthesized)) || updatedType == null) {
            return updatedType;
        }
        ArrayList<KtExpression> arrayList = new ArrayList();
        KtExpression ktExpressionDeparenthesizeOrGetSelector = argumentExpression;
        while (ktExpressionDeparenthesizeOrGetSelector != null) {
            arrayList.add(ktExpressionDeparenthesizeOrGetSelector);
            StatementFilter statementFilter = context.statementFilter;
            Intrinsics.checkNotNullExpressionValue(statementFilter, "statementFilter");
            ktExpressionDeparenthesizeOrGetSelector = deparenthesizeOrGetSelector(ktExpressionDeparenthesizeOrGetSelector, statementFilter);
        }
        CollectionsKt.reverse(arrayList);
        boolean zHasNecessarySafeCall = false;
        for (KtExpression ktExpression : arrayList) {
            if (!(ktExpression instanceof KtParenthesizedExpression) && !(ktExpression instanceof KtLabeledExpression) && !(ktExpression instanceof KtAnnotatedExpression)) {
                Intrinsics.checkNotNull(ktExpression);
                BindingTrace trace = context.trace;
                Intrinsics.checkNotNullExpressionValue(trace, "trace");
                zHasNecessarySafeCall = hasNecessarySafeCall(ktExpression, trace);
            }
            BindingContextUtils.updateRecordedType(updatedType, ktExpression, context.trace, zHasNecessarySafeCall);
        }
        return context.trace.getType(argumentExpression);
    }

    private final KtExpression deparenthesizeOrGetSelector(KtExpression expression, StatementFilter statementFilter) {
        KtExpression ktExpressionDeparenthesizeOnce = KtPsiUtil.deparenthesizeOnce(expression);
        if (!Intrinsics.areEqual(ktExpressionDeparenthesizeOnce, expression)) {
            return ktExpressionDeparenthesizeOnce;
        }
        if (expression instanceof KtBlockExpression) {
            return StatementFilterKt.getLastStatementInABlock(statementFilter, (KtBlockExpression) expression);
        }
        if (expression instanceof KtQualifiedExpression) {
            return ((KtQualifiedExpression) expression).getSelectorExpression();
        }
        return null;
    }

    private final boolean hasNecessarySafeCall(KtExpression expression, BindingTrace trace) {
        KotlinType type;
        return (expression instanceof KtSafeQualifiedExpression) && (type = trace.getType(((KtSafeQualifiedExpression) expression).getReceiverExpression())) != null && TypeUtils.isNullableType(type);
    }

    public final void bind$frontend(BindingTrace trace, ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        NewAbstractResolvedCall<?> newAbstractResolvedCall = resolvedCall instanceof NewAbstractResolvedCall ? (NewAbstractResolvedCall) resolvedCall : null;
        if (newAbstractResolvedCall != null) {
            bind(trace, newAbstractResolvedCall);
        }
        NewVariableAsFunctionResolvedCallImpl newVariableAsFunctionResolvedCallImpl = resolvedCall instanceof NewVariableAsFunctionResolvedCallImpl ? (NewVariableAsFunctionResolvedCallImpl) resolvedCall : null;
        if (newVariableAsFunctionResolvedCallImpl != null) {
            bind(trace, newVariableAsFunctionResolvedCallImpl);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [org.jetbrains.kotlin.descriptors.CallableDescriptor] */
    public final void reportDiagnostics(BasicCallResolutionContext context, BindingTrace trace, NewAbstractResolvedCall<?> resolvedCall, Collection<? extends KotlinCallDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        if (resolvedCall instanceof NewVariableAsFunctionResolvedCallImpl) {
            NewVariableAsFunctionResolvedCallImpl newVariableAsFunctionResolvedCallImpl = (NewVariableAsFunctionResolvedCallImpl) resolvedCall;
            NewAbstractResolvedCall<VariableDescriptor> variableCall = newVariableAsFunctionResolvedCallImpl.getVariableCall();
            NewAbstractResolvedCall<FunctionDescriptor> functionCall = newVariableAsFunctionResolvedCallImpl.getFunctionCall();
            VariableDescriptor resultingDescriptor = variableCall.getResultingDescriptor();
            Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
            reportCallDiagnostic(context, trace, variableCall, resultingDescriptor, diagnostics);
            FunctionDescriptor resultingDescriptor2 = functionCall.getResultingDescriptor();
            Intrinsics.checkNotNullExpressionValue(resultingDescriptor2, "getResultingDescriptor(...)");
            reportCallDiagnostic(context, trace, functionCall, resultingDescriptor2, CollectionsKt.emptyList());
            return;
        }
        if (resolvedCall.getResolvedCallAtom() != null) {
            reportCallDiagnostic(context, trace, resolvedCall, resolvedCall.getResultingDescriptor(), diagnostics);
        }
    }

    private final void bind(BindingTrace trace, NewAbstractResolvedCall<?> simpleResolvedCall) {
        TracingStrategy tracingStrategy = simpleResolvedCall.getPsiKotlinCall().getTracingStrategy();
        NewAbstractResolvedCall<?> newAbstractResolvedCall = simpleResolvedCall;
        tracingStrategy.bindReference(trace, newAbstractResolvedCall);
        tracingStrategy.bindResolvedCall(trace, newAbstractResolvedCall);
    }

    private final void bind(BindingTrace trace, NewVariableAsFunctionResolvedCallImpl variableAsFunction) {
        TracingStrategy tracingStrategy = variableAsFunction.getBaseCall().getTracingStrategy();
        NewAbstractResolvedCall<VariableDescriptor> variableCall = variableAsFunction.getVariableCall();
        NewAbstractResolvedCall<FunctionDescriptor> functionCall = variableAsFunction.getFunctionCall();
        tracingStrategy.bindReference(trace, variableCall);
        tracingStrategy.bindResolvedCall(trace, variableAsFunction);
        functionCall.getPsiKotlinCall().getTracingStrategy().bindReference(trace, functionCall);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v2, types: [org.jetbrains.kotlin.descriptors.CallableDescriptor] */
    public final void reportCallDiagnostic(BasicCallResolutionContext context, BindingTrace trace, NewAbstractResolvedCall<?> resolvedCall, CallableDescriptor resultingDescriptor, Collection<? extends KotlinCallDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(resultingDescriptor, "resultingDescriptor");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        TrackingBindingTrace trackingBindingTrace = new TrackingBindingTrace(trace);
        Context contextReplaceBindingTrace = context.replaceBindingTrace(trackingBindingTrace);
        Intrinsics.checkNotNullExpressionValue(contextReplaceBindingTrace, "replaceBindingTrace(...)");
        BasicCallResolutionContext basicCallResolutionContext = (BasicCallResolutionContext) contextReplaceBindingTrace;
        KotlinDiagnosticsHolder.SimpleHolder simpleHolder = new KotlinDiagnosticsHolder.SimpleHolder();
        ResolvedCallAtom resolvedCallAtom = resolvedCall.getResolvedCallAtom();
        if (resolvedCallAtom != null) {
            this.additionalDiagnosticReporter.reportAdditionalDiagnostics(resolvedCallAtom, resultingDescriptor, simpleHolder, diagnostics);
        }
        List<KotlinCallDiagnostic> listPlus = CollectionsKt.plus((Collection) diagnostics, (Iterable) simpleHolder.getDiagnostics());
        ConstantExpressionEvaluator constantExpressionEvaluator = this.constantExpressionEvaluator;
        PSIKotlinCall psiKotlinCall = resolvedCall.getPsiKotlinCall();
        DataFlowValueFactory dataFlowValueFactory = context.dataFlowValueFactory;
        Intrinsics.checkNotNullExpressionValue(dataFlowValueFactory, "dataFlowValueFactory");
        DiagnosticReporterByTrackingStrategy diagnosticReporterByTrackingStrategy = new DiagnosticReporterByTrackingStrategy(constantExpressionEvaluator, basicCallResolutionContext, psiKotlinCall, dataFlowValueFactory, listPlus, this.smartCastManager, this.typeSystemContext);
        for (KotlinCallDiagnostic kotlinCallDiagnostic : listPlus) {
            boolean z = false;
            trackingBindingTrace.setReported(false);
            kotlinCallDiagnostic.report(diagnosticReporterByTrackingStrategy);
            if (kotlinCallDiagnostic instanceof ResolvedUsingDeprecatedVisibility) {
                NewResolutionOldInferenceKt.reportResolvedUsingDeprecatedVisibility(resolvedCall.getPsiKotlinCall().getPsiCall(), resolvedCall.get$suspendPropertyDescriptor(), resultingDescriptor, (ResolvedUsingDeprecatedVisibility) kotlinCallDiagnostic, trace);
            }
            if ((kotlinCallDiagnostic instanceof ResolutionDiagnostic) && !(kotlinCallDiagnostic instanceof VisibilityError)) {
                z = true;
            }
            if (!trackingBindingTrace.getReported() && !z && INSTANCE.getREPORT_MISSING_NEW_INFERENCE_DIAGNOSTIC()) {
                trace.report((CandidateApplicabilityKt.isSuccess(kotlinCallDiagnostic.getCandidateApplicability()) ? Errors.NEW_INFERENCE_DIAGNOSTIC : Errors.NEW_INFERENCE_ERROR).on(diagnosticReporterByTrackingStrategy.getPsiKotlinCall().getPsiCall().getCallElement(), "Missing diagnostic: " + kotlinCallDiagnostic));
            }
        }
    }
}
