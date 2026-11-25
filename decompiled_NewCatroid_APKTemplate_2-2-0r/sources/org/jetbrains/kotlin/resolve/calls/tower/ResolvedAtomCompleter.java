package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.NavigatablePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ReceiverParameterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.MissingSupertypesResolver;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.CommonSuperTypeUtilsKt;
import org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.checkers.NewSchemeOfIntegerOperatorResolutionChecker;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.components.CallableReceiver;
import org.jetbrains.kotlin.resolve.calls.components.CallableReferenceAdaptation;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.components.ReturnArgumentsInfo;
import org.jetbrains.kotlin.resolve.calls.components.SuspendConversionStrategy;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.inference.ComposedSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.EmptySubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutorByConstructorMap;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.FakeKotlinCallArgumentForCallableReference;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallContainer;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallableReferenceArgumentAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallableReferenceCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCollectionLiteralAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedExpressionAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedSubCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategyImpl;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.checkers.MissingDependencySupertypeChecker;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.WrappedType;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.CoercionStrategy;
import org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ResolvedAtomCompleter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008c\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001^Bm\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u001b¢\u0006\u0002\u0010\u001cJ&\u0010%\u001a\u00020&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010*\u001a\u00020 H\u0002J\u0010\u0010+\u001a\u00020&2\u0006\u0010,\u001a\u00020-H\u0002J\u0010\u0010.\u001a\u00020&2\u0006\u0010/\u001a\u000200H\u0002J\u000e\u00101\u001a\u00020&2\u0006\u0010/\u001a\u000200J4\u00102\u001a\u0004\u0018\u0001032\u0006\u00104\u001a\u0002052\b\u00106\u001a\u0004\u0018\u00010)2\n\u0010'\u001a\u0006\u0012\u0002\b\u0003072\n\b\u0002\u00108\u001a\u0004\u0018\u000109H\u0002J\u0010\u0010:\u001a\u0004\u0018\u0001032\u0006\u0010/\u001a\u00020;J\u0014\u0010<\u001a\u0004\u0018\u0001032\n\u0010'\u001a\u0006\u0012\u0002\b\u00030=J\u0010\u0010>\u001a\u00020&2\u0006\u0010?\u001a\u00020@H\u0002J\u000e\u0010A\u001a\u00020&2\u0006\u0010/\u001a\u00020BJ\u0010\u0010C\u001a\u00020&2\u0006\u0010/\u001a\u00020#H\u0002J\"\u0010D\u001a\b\u0012\u0002\b\u0003\u0018\u0001072\u0006\u0010,\u001a\u00020-2\f\u0010E\u001a\b\u0012\u0004\u0012\u00020G0FJ\u0010\u0010H\u001a\u00020&2\u0006\u0010I\u001a\u00020JH\u0002J\u0018\u0010K\u001a\u00020L2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020)H\u0002J\u0016\u0010M\u001a\b\u0012\u0004\u0012\u00020G0N2\u0006\u0010,\u001a\u00020-H\u0002J\u001c\u0010O\u001a\u00020\"2\n\u0010'\u001a\u0006\u0012\u0002\b\u0003072\u0006\u0010P\u001a\u00020QH\u0002J\u001e\u0010R\u001a\u00020&2\n\u0010'\u001a\u0006\u0012\u0002\b\u0003072\b\u0010P\u001a\u0004\u0018\u00010QH\u0002J \u0010S\u001a\u00020T2\b\u0010/\u001a\u0004\u0018\u00010#2\u0006\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020\u0003J\u0012\u0010X\u001a\u0004\u0018\u00010L2\u0006\u00104\u001a\u000205H\u0002J\u0014\u0010Y\u001a\u000203*\u0002032\u0006\u0010U\u001a\u00020)H\u0002J\u0014\u0010Z\u001a\u00020[*\u0002032\u0006\u0010W\u001a\u00020\u0003H\u0002J\u0014\u0010\\\u001a\u00020]*\u00020]2\u0006\u0010W\u001a\u00020\u0003H\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010!\u001a\u00020\"*\u00020#8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b!\u0010$¨\u0006_"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ResolvedAtomCompleter;", "", "resultSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "topLevelCallContext", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "kotlinToResolvedCallTransformer", "Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinToResolvedCallTransformer;", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "argumentTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "doubleColonExpressionResolver", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "missingSupertypesResolver", "Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinToResolvedCallTransformer;Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Lorg/jetbrains/kotlin/types/TypeApproximator;Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;)V", "topLevelCallCheckerContext", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "topLevelTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "isCoercedToUnit", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;)Z", "checkMissingReceiverSupertypes", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "trace", "clearPartiallyResolvedCall", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "complete", "resolvedAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "completeAll", "completeCallableReference", "Lorg/jetbrains/kotlin/types/KotlinType;", "callableCandidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "recordedDescriptor", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;", "additionalDataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "completeCallableReferenceArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceArgumentAtom;", "completeCallableReferenceCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewCallableReferenceResolvedCall;", "completeCollectionLiteralCalls", "collectionLiteralArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCollectionLiteralAtom;", "completeExpression", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedExpressionAtom;", "completeLambda", "completeResolvedCall", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "completeSubCallArgument", "resolvedSubCallArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedSubCallArgument;", "extractCallableReferenceResultTypeInfoFromDescriptor", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolvedAtomCompleter$CallableReferenceResultTypeInfo;", "extractDiagnosticsFromPartiallyResolvedCall", "", "isCallableReferenceWithImplicitConversion", "callableReferenceAdaptation", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceAdaptation;", "recordArgumentAdaptationForCallableReference", "substituteFunctionLiteralDescriptor", "Lorg/jetbrains/kotlin/resolve/calls/tower/FunctionLiteralTypes;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "substitutor", "updateCallableReferenceResultType", "replaceFunctionTypeArgumentsByDescriptor", "substituteAndApproximate", "Lorg/jetbrains/kotlin/resolve/calls/tower/FunctionLiteralTypes$ProcessedType;", "updateReceiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "CallableReferenceResultTypeInfo", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedAtomCompleter {
    private final ArgumentTypeResolver argumentTypeResolver;
    private final KotlinBuiltIns builtIns;
    private final KotlinCallComponents callComponents;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final DeprecationResolver deprecationResolver;
    private final DoubleColonExpressionResolver doubleColonExpressionResolver;
    private final ExpressionTypingServices expressionTypingServices;
    private final KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer;
    private final MissingSupertypesResolver missingSupertypesResolver;
    private final ModuleDescriptor moduleDescriptor;
    private final NewTypeSubstitutor resultSubstitutor;
    private final CallCheckerContext topLevelCallCheckerContext;
    private final BasicCallResolutionContext topLevelCallContext;
    private final BindingTrace topLevelTrace;
    private final TypeApproximator typeApproximator;

    /* JADX INFO: compiled from: ResolvedAtomCompleter.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ExplicitReceiverKind.values().length];
            try {
                iArr[ExplicitReceiverKind.DISPATCH_RECEIVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ExplicitReceiverKind.EXTENSION_RECEIVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ResolvedAtomCompleter(NewTypeSubstitutor resultSubstitutor, BasicCallResolutionContext topLevelCallContext, KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer, ExpressionTypingServices expressionTypingServices, ArgumentTypeResolver argumentTypeResolver, DoubleColonExpressionResolver doubleColonExpressionResolver, KotlinBuiltIns builtIns, DeprecationResolver deprecationResolver, ModuleDescriptor moduleDescriptor, DataFlowValueFactory dataFlowValueFactory, TypeApproximator typeApproximator, MissingSupertypesResolver missingSupertypesResolver, KotlinCallComponents callComponents) {
        Intrinsics.checkNotNullParameter(resultSubstitutor, "resultSubstitutor");
        Intrinsics.checkNotNullParameter(topLevelCallContext, "topLevelCallContext");
        Intrinsics.checkNotNullParameter(kotlinToResolvedCallTransformer, "kotlinToResolvedCallTransformer");
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        Intrinsics.checkNotNullParameter(argumentTypeResolver, "argumentTypeResolver");
        Intrinsics.checkNotNullParameter(doubleColonExpressionResolver, "doubleColonExpressionResolver");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(missingSupertypesResolver, "missingSupertypesResolver");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        this.resultSubstitutor = resultSubstitutor;
        this.topLevelCallContext = topLevelCallContext;
        this.kotlinToResolvedCallTransformer = kotlinToResolvedCallTransformer;
        this.expressionTypingServices = expressionTypingServices;
        this.argumentTypeResolver = argumentTypeResolver;
        this.doubleColonExpressionResolver = doubleColonExpressionResolver;
        this.builtIns = builtIns;
        this.deprecationResolver = deprecationResolver;
        this.moduleDescriptor = moduleDescriptor;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.typeApproximator = typeApproximator;
        this.missingSupertypesResolver = missingSupertypesResolver;
        this.callComponents = callComponents;
        CallCheckerContext callCheckerContext = new CallCheckerContext(topLevelCallContext, deprecationResolver, moduleDescriptor, missingSupertypesResolver, callComponents, null, 32, null);
        this.topLevelCallCheckerContext = callCheckerContext;
        this.topLevelTrace = callCheckerContext.getTrace();
    }

    /* JADX INFO: compiled from: ResolvedAtomCompleter.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B3\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0017\u001a\u00020\tHÆ\u0003JA\u0010\u0018\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ResolvedAtomCompleter$CallableReferenceResultTypeInfo;", "", "dispatchReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "extensionReceiver", "explicitReceiver", "substitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "resultType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "getExplicitReceiver", "getExtensionReceiver", "getResultType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getSubstitutor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class CallableReferenceResultTypeInfo {
        private final ReceiverValue dispatchReceiver;
        private final ReceiverValue explicitReceiver;
        private final ReceiverValue extensionReceiver;
        private final KotlinType resultType;
        private final NewTypeSubstitutor substitutor;

        public static /* synthetic */ CallableReferenceResultTypeInfo copy$default(CallableReferenceResultTypeInfo callableReferenceResultTypeInfo, ReceiverValue receiverValue, ReceiverValue receiverValue2, ReceiverValue receiverValue3, NewTypeSubstitutor newTypeSubstitutor, KotlinType kotlinType, int i, Object obj) {
            if ((i & 1) != 0) {
                receiverValue = callableReferenceResultTypeInfo.dispatchReceiver;
            }
            if ((i & 2) != 0) {
                receiverValue2 = callableReferenceResultTypeInfo.extensionReceiver;
            }
            ReceiverValue receiverValue4 = receiverValue2;
            if ((i & 4) != 0) {
                receiverValue3 = callableReferenceResultTypeInfo.explicitReceiver;
            }
            ReceiverValue receiverValue5 = receiverValue3;
            if ((i & 8) != 0) {
                newTypeSubstitutor = callableReferenceResultTypeInfo.substitutor;
            }
            NewTypeSubstitutor newTypeSubstitutor2 = newTypeSubstitutor;
            if ((i & 16) != 0) {
                kotlinType = callableReferenceResultTypeInfo.resultType;
            }
            return callableReferenceResultTypeInfo.copy(receiverValue, receiverValue4, receiverValue5, newTypeSubstitutor2, kotlinType);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ReceiverValue getDispatchReceiver() {
            return this.dispatchReceiver;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ReceiverValue getExtensionReceiver() {
            return this.extensionReceiver;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final ReceiverValue getExplicitReceiver() {
            return this.explicitReceiver;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final NewTypeSubstitutor getSubstitutor() {
            return this.substitutor;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final KotlinType getResultType() {
            return this.resultType;
        }

        public final CallableReferenceResultTypeInfo copy(ReceiverValue dispatchReceiver, ReceiverValue extensionReceiver, ReceiverValue explicitReceiver, NewTypeSubstitutor substitutor, KotlinType resultType) {
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            Intrinsics.checkNotNullParameter(resultType, "resultType");
            return new CallableReferenceResultTypeInfo(dispatchReceiver, extensionReceiver, explicitReceiver, substitutor, resultType);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CallableReferenceResultTypeInfo)) {
                return false;
            }
            CallableReferenceResultTypeInfo callableReferenceResultTypeInfo = (CallableReferenceResultTypeInfo) other;
            return Intrinsics.areEqual(this.dispatchReceiver, callableReferenceResultTypeInfo.dispatchReceiver) && Intrinsics.areEqual(this.extensionReceiver, callableReferenceResultTypeInfo.extensionReceiver) && Intrinsics.areEqual(this.explicitReceiver, callableReferenceResultTypeInfo.explicitReceiver) && Intrinsics.areEqual(this.substitutor, callableReferenceResultTypeInfo.substitutor) && Intrinsics.areEqual(this.resultType, callableReferenceResultTypeInfo.resultType);
        }

        public int hashCode() {
            ReceiverValue receiverValue = this.dispatchReceiver;
            int iHashCode = (receiverValue == null ? 0 : receiverValue.hashCode()) * 31;
            ReceiverValue receiverValue2 = this.extensionReceiver;
            int iHashCode2 = (iHashCode + (receiverValue2 == null ? 0 : receiverValue2.hashCode())) * 31;
            ReceiverValue receiverValue3 = this.explicitReceiver;
            return ((((iHashCode2 + (receiverValue3 != null ? receiverValue3.hashCode() : 0)) * 31) + this.substitutor.hashCode()) * 31) + this.resultType.hashCode();
        }

        public String toString() {
            return "CallableReferenceResultTypeInfo(dispatchReceiver=" + this.dispatchReceiver + ", extensionReceiver=" + this.extensionReceiver + ", explicitReceiver=" + this.explicitReceiver + ", substitutor=" + this.substitutor + ", resultType=" + this.resultType + ')';
        }

        public CallableReferenceResultTypeInfo(ReceiverValue receiverValue, ReceiverValue receiverValue2, ReceiverValue receiverValue3, NewTypeSubstitutor substitutor, KotlinType resultType) {
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            Intrinsics.checkNotNullParameter(resultType, "resultType");
            this.dispatchReceiver = receiverValue;
            this.extensionReceiver = receiverValue2;
            this.explicitReceiver = receiverValue3;
            this.substitutor = substitutor;
            this.resultType = resultType;
        }

        public final ReceiverValue getDispatchReceiver() {
            return this.dispatchReceiver;
        }

        public final ReceiverValue getExtensionReceiver() {
            return this.extensionReceiver;
        }

        public final ReceiverValue getExplicitReceiver() {
            return this.explicitReceiver;
        }

        public final NewTypeSubstitutor getSubstitutor() {
            return this.substitutor;
        }

        public final KotlinType getResultType() {
            return this.resultType;
        }
    }

    private final void complete(ResolvedAtom resolvedAtom) {
        if (this.topLevelCallContext.inferenceSession.callCompleted(resolvedAtom)) {
            return;
        }
        if (resolvedAtom instanceof ResolvedCollectionLiteralAtom) {
            completeCollectionLiteralCalls((ResolvedCollectionLiteralAtom) resolvedAtom);
            return;
        }
        if (resolvedAtom instanceof ResolvedCallableReferenceArgumentAtom) {
            completeCallableReferenceArgument((ResolvedCallableReferenceArgumentAtom) resolvedAtom);
            return;
        }
        if (resolvedAtom instanceof ResolvedLambdaAtom) {
            completeLambda((ResolvedLambdaAtom) resolvedAtom);
            return;
        }
        if (resolvedAtom instanceof ResolvedCallAtom) {
            completeResolvedCall((ResolvedCallAtom) resolvedAtom, CollectionsKt.emptyList());
        } else if (resolvedAtom instanceof ResolvedSubCallArgument) {
            completeSubCallArgument((ResolvedSubCallArgument) resolvedAtom);
        } else if (resolvedAtom instanceof ResolvedExpressionAtom) {
            completeExpression((ResolvedExpressionAtom) resolvedAtom);
        }
    }

    public final void completeExpression(ResolvedExpressionAtom resolvedAtom) {
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
        KtExpression psiExpression = NewCallArgumentsKt.getPsiExpression(resolvedAtom.getAtom());
        InferenceSession inferenceSession = this.topLevelCallContext.inferenceSession;
        Intrinsics.checkNotNullExpressionValue(inferenceSession, "inferenceSession");
        if ((psiExpression instanceof KtBlockExpression) && (inferenceSession instanceof BuilderInferenceSession)) {
            List<KtExpression> statements = ((KtBlockExpression) psiExpression).getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) statements);
            KtCallableReferenceExpression ktCallableReferenceExpression = objLastOrNull instanceof KtCallableReferenceExpression ? (KtCallableReferenceExpression) objLastOrNull : null;
            if (ktCallableReferenceExpression == null) {
                return;
            }
            BuilderInferenceSession builderInferenceSession = (BuilderInferenceSession) inferenceSession;
            builderInferenceSession.completeDoubleColonExpression(ktCallableReferenceExpression, builderInferenceSession.getNotFixedToInferredTypesSubstitutor());
        }
    }

    public final void completeAll(ResolvedAtom resolvedAtom) {
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
        if (resolvedAtom.getAnalyzed()) {
            List<ResolvedAtom> subResolvedAtoms = resolvedAtom.getSubResolvedAtoms();
            if (subResolvedAtoms != null) {
                Iterator<T> it = subResolvedAtoms.iterator();
                while (it.hasNext()) {
                    completeAll((ResolvedAtom) it.next());
                }
            }
            complete(resolvedAtom);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void completeSubCallArgument(ResolvedSubCallArgument resolvedSubCallArgument) {
        KtExpression lastElementDeparenthesized = KtPsiUtil.getLastElementDeparenthesized(NewCallArgumentsKt.getPsiExpression(resolvedSubCallArgument.getAtom()), this.topLevelCallContext.statementFilter);
        if (lastElementDeparenthesized instanceof KtCallableReferenceExpression) {
            KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer = this.kotlinToResolvedCallTransformer;
            KtSimpleNameExpression callableReference = ((KtCallableReferenceExpression) lastElementDeparenthesized).getCallableReference();
            Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
            NewAbstractResolvedCall<?> resolvedCallForArgumentExpression = kotlinToResolvedCallTransformer.getResolvedCallForArgumentExpression(callableReference, this.topLevelCallContext);
            NewCallableReferenceResolvedCall<?> newCallableReferenceResolvedCall = resolvedCallForArgumentExpression instanceof NewCallableReferenceResolvedCall ? (NewCallableReferenceResolvedCall) resolvedCallForArgumentExpression : null;
            if (newCallableReferenceResolvedCall != null) {
                completeCallableReferenceCall(newCallableReferenceResolvedCall);
                return;
            }
            return;
        }
        KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer2 = this.kotlinToResolvedCallTransformer;
        KtExpression psiExpression = NewCallArgumentsKt.getPsiExpression(resolvedSubCallArgument.getAtom());
        if (psiExpression == null) {
            return;
        }
        BasicCallResolutionContext basicCallResolutionContext = (BasicCallResolutionContext) this.topLevelCallContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE);
        Intrinsics.checkNotNull(basicCallResolutionContext);
        kotlinToResolvedCallTransformer2.updateRecordedType(psiExpression, null, basicCallResolutionContext, null, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.lang.Object, org.jetbrains.kotlin.resolve.calls.context.ResolutionContext] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final NewAbstractResolvedCall<?> completeResolvedCall(ResolvedCallAtom resolvedCallAtom, Collection<? extends KotlinCallDiagnostic> diagnostics) {
        NewAbstractResolvedCall newAbstractResolvedCall;
        CallCheckerContext callCheckerContext;
        KtExpression ktExpression;
        KotlinType type;
        Intrinsics.checkNotNullParameter(resolvedCallAtom, "resolvedCallAtom");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        Set<KotlinCallDiagnostic> setExtractDiagnosticsFromPartiallyResolvedCall = extractDiagnosticsFromPartiallyResolvedCall(resolvedCallAtom);
        clearPartiallyResolvedCall(resolvedCallAtom);
        KotlinCall atom = resolvedCallAtom.getAtom();
        if (PSIKotlinCallsKt.getPsiKotlinCall(atom) instanceof PSIKotlinCallForVariable) {
            return null;
        }
        Collection<? extends KotlinCallDiagnostic> collectionPlus = CollectionsKt.plus((Collection) diagnostics, (Iterable) setExtractDiagnosticsFromPartiallyResolvedCall);
        NewAbstractResolvedCall<?> newAbstractResolvedCallTransformToResolvedCall = this.kotlinToResolvedCallTransformer.transformToResolvedCall(resolvedCallAtom, this.topLevelTrace, this.resultSubstitutor, collectionPlus);
        if (newAbstractResolvedCallTransformToResolvedCall instanceof VariableAsFunctionResolvedCall) {
            ResolvedCall<FunctionDescriptor> functionCall = ((VariableAsFunctionResolvedCall) newAbstractResolvedCallTransformToResolvedCall).getFunctionCall();
            Intrinsics.checkNotNull(functionCall, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall<*>");
            newAbstractResolvedCall = (NewAbstractResolvedCall) functionCall;
        } else {
            newAbstractResolvedCall = newAbstractResolvedCallTransformToResolvedCall;
        }
        if (ErrorUtils.isError(newAbstractResolvedCallTransformToResolvedCall.get$suspendPropertyDescriptor())) {
            this.kotlinToResolvedCallTransformer.runArgumentsChecks(this.topLevelCallContext, newAbstractResolvedCall);
            checkMissingReceiverSupertypes(newAbstractResolvedCallTransformToResolvedCall, this.missingSupertypesResolver, this.topLevelTrace);
            return newAbstractResolvedCallTransformToResolvedCall;
        }
        Call psiCall = atom instanceof PSIKotlinCallForInvoke ? ((PSIKotlinCallForInvoke) atom).getBaseCall().getPsiCall() : PSIKotlinCallsKt.getPsiKotlinCall(atom).getPsiCall();
        KtElement callElement = psiCall.getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        if ((callElement instanceof KtExpression) && (type = this.topLevelCallContext.trace.getType((ktExpression = (KtExpression) callElement))) != null && TypeUtilsKt.shouldBeUpdated(type) && newAbstractResolvedCallTransformToResolvedCall.getResultingDescriptor().getReturnType() != null) {
            this.topLevelCallContext.trace.recordType(ktExpression, newAbstractResolvedCallTransformToResolvedCall.getResultingDescriptor().getReturnType());
        }
        BasicCallResolutionContext basicCallResolutionContext = (BasicCallResolutionContext) this.topLevelCallContext.trace.get(BindingContext.PARTIAL_CALL_RESOLUTION_CONTEXT, psiCall);
        if (basicCallResolutionContext != null) {
            ?? ReplaceBindingTrace = basicCallResolutionContext.replaceBindingTrace(this.topLevelTrace);
            Intrinsics.checkNotNullExpressionValue(ReplaceBindingTrace, "replaceBindingTrace(...)");
            callCheckerContext = new CallCheckerContext(ReplaceBindingTrace, this.deprecationResolver, this.moduleDescriptor, this.missingSupertypesResolver, this.callComponents, null, 32, null);
        } else {
            callCheckerContext = this.topLevelCallCheckerContext;
        }
        NewAbstractResolvedCall<?> newAbstractResolvedCall2 = newAbstractResolvedCallTransformToResolvedCall;
        this.kotlinToResolvedCallTransformer.bind$frontend(this.topLevelTrace, newAbstractResolvedCall2);
        this.kotlinToResolvedCallTransformer.runArgumentsChecks(this.topLevelCallContext, newAbstractResolvedCall);
        this.kotlinToResolvedCallTransformer.runCallCheckers(newAbstractResolvedCall2, callCheckerContext);
        this.kotlinToResolvedCallTransformer.runAdditionalReceiversCheckers(newAbstractResolvedCall2, this.topLevelCallContext);
        this.kotlinToResolvedCallTransformer.reportDiagnostics(this.topLevelCallContext, this.topLevelTrace, newAbstractResolvedCallTransformToResolvedCall, collectionPlus);
        return newAbstractResolvedCallTransformToResolvedCall;
    }

    private final void checkMissingReceiverSupertypes(ResolvedCall<CallableDescriptor> resolvedCall, MissingSupertypesResolver missingSupertypesResolver, BindingTrace trace) {
        KotlinType type;
        ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
        if (dispatchReceiver == null) {
            dispatchReceiver = resolvedCall.getExtensionReceiver();
        }
        if (dispatchReceiver == null || (type = dispatchReceiver.getType()) == null) {
            return;
        }
        MissingDependencySupertypeChecker missingDependencySupertypeChecker = MissingDependencySupertypeChecker.INSTANCE;
        KtElement callElement = resolvedCall.get$fakeCall().getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        missingDependencySupertypeChecker.checkSupertypes(type, callElement, trace, missingSupertypesResolver);
    }

    private final Set<KotlinCallDiagnostic> extractDiagnosticsFromPartiallyResolvedCall(ResolvedCallAtom resolvedCallAtom) {
        PartialCallResolutionResult result;
        PartialCallContainer partialCallContainer = (PartialCallContainer) this.topLevelTrace.get(BindingContext.ONLY_RESOLVED_CALL, KotlinToResolvedCallTransformer.INSTANCE.keyForPartiallyResolvedCall(resolvedCallAtom));
        List<KotlinCallDiagnostic> diagnostics = (partialCallContainer == null || (result = partialCallContainer.getResult()) == null) ? null : result.getDiagnostics();
        if (diagnostics == null) {
            diagnostics = CollectionsKt.emptyList();
        }
        return CollectionsKt.toSet(diagnostics);
    }

    private final void clearPartiallyResolvedCall(ResolvedCallAtom resolvedCallAtom) {
        Call callKeyForPartiallyResolvedCall = KotlinToResolvedCallTransformer.INSTANCE.keyForPartiallyResolvedCall(resolvedCallAtom);
        if (((PartialCallContainer) this.topLevelTrace.get(BindingContext.ONLY_RESOLVED_CALL, callKeyForPartiallyResolvedCall)) != null) {
            this.topLevelTrace.record(BindingContext.ONLY_RESOLVED_CALL, callKeyForPartiallyResolvedCall, PartialCallContainer.INSTANCE.getEmpty());
        }
    }

    private final boolean isCoercedToUnit(ResolvedLambdaAtom resolvedLambdaAtom) {
        ReceiverValueWithSmartCastInfo receiver;
        ReceiverValue receiverValue;
        KotlinType type;
        UnwrappedType unwrappedTypeUnwrap;
        ReturnArgumentsInfo resultArgumentsInfo = resolvedLambdaAtom.getResultArgumentsInfo();
        if (resultArgumentsInfo == null) {
            List<ResolvedAtom> subResolvedAtoms = resolvedLambdaAtom.getSubResolvedAtoms();
            Intrinsics.checkNotNull(subResolvedAtoms);
            Object objSingle = CollectionsKt.single((List<? extends Object>) subResolvedAtoms);
            Intrinsics.checkNotNull(objSingle, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom");
            return isCoercedToUnit((ResolvedLambdaAtom) objSingle);
        }
        List<KotlinCallArgument> nonErrorArguments = resultArgumentsInfo.getNonErrorArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(nonErrorArguments, 10));
        for (KotlinCallArgument kotlinCallArgument : nonErrorArguments) {
            UnwrappedType unwrappedTypeSafeSubstitute = null;
            SimpleKotlinCallArgument simpleKotlinCallArgument = kotlinCallArgument instanceof SimpleKotlinCallArgument ? (SimpleKotlinCallArgument) kotlinCallArgument : null;
            if (simpleKotlinCallArgument != null && (receiver = simpleKotlinCallArgument.getReceiver()) != null && (receiverValue = receiver.getReceiverValue()) != null && (type = receiverValue.getType()) != null) {
                Intrinsics.checkNotNull(type);
                if (type instanceof WrappedType) {
                    unwrappedTypeUnwrap = type.unwrap();
                } else {
                    if (!(type instanceof UnwrappedType)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    unwrappedTypeUnwrap = (UnwrappedType) type;
                }
                unwrappedTypeSafeSubstitute = this.resultSubstitutor.safeSubstitute(unwrappedTypeUnwrap);
            }
            arrayList.add(unwrappedTypeSafeSubstitute);
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty() && !resultArgumentsInfo.getReturnArgumentsExist()) {
            return true;
        }
        List listFilterNotNull = CollectionsKt.filterNotNull(arrayList2);
        if (listFilterNotNull.isEmpty()) {
            return false;
        }
        return TypeUtilsKt.isUnit(CommonSuperTypeUtilsKt.commonSuperType(NewCommonSuperTypeCalculator.INSTANCE, listFilterNotNull));
    }

    private final FunctionLiteralTypes.ProcessedType substituteAndApproximate(KotlinType kotlinType, NewTypeSubstitutor newTypeSubstitutor) {
        UnwrappedType unwrappedTypeSafeSubstitute = newTypeSubstitutor.safeSubstitute(kotlinType.unwrap());
        return new FunctionLiteralTypes.ProcessedType(unwrappedTypeSafeSubstitute, this.typeApproximator.approximateDeclarationType(unwrappedTypeSafeSubstitute, true));
    }

    /* JADX WARN: Code duplicated, block: B:62:0x0126  */
    /* JADX WARN: Code duplicated, block: B:87:0x0191  */
    public final FunctionLiteralTypes substituteFunctionLiteralDescriptor(ResolvedLambdaAtom resolvedAtom, SimpleFunctionDescriptorImpl descriptor, NewTypeSubstitutor substitutor) {
        UnwrappedType type;
        ArrayList contextReceivers;
        ArrayList parameters;
        FunctionLiteralTypes.ProcessedType processedTypeSubstituteAndApproximate;
        FunctionLiteralTypes.ProcessedType processedTypeSubstituteAndApproximate2;
        FunctionLiteralTypes.ProcessedType processedTypeSubstituteAndApproximate3;
        FunctionLiteralTypes.ProcessedType processedTypeSubstituteAndApproximate4;
        UnwrappedType receiver;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        SimpleType returnType = (resolvedAtom == null || !isCoercedToUnit(resolvedAtom)) ? resolvedAtom != null ? resolvedAtom.getReturnType() : null : this.builtIns.getUnitType();
        UnwrappedType returnType2 = returnType != null ? returnType : descriptor.getReturnType();
        if (resolvedAtom == null || (receiver = resolvedAtom.getReceiver()) == null) {
            ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
            type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
        } else {
            type = receiver;
        }
        if (resolvedAtom == null || (contextReceivers = resolvedAtom.getContextReceivers()) == null) {
            List<ReceiverParameterDescriptor> contextReceiverParameters = descriptor.getContextReceiverParameters();
            Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
            List<ReceiverParameterDescriptor> list = contextReceiverParameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((ReceiverParameterDescriptor) it.next()).getType());
            }
            contextReceivers = arrayList;
        }
        ReceiverParameterDescriptor dispatchReceiverParameter = descriptor.mo6753getDispatchReceiverParameter();
        KotlinType type2 = dispatchReceiverParameter != null ? dispatchReceiverParameter.getType() : null;
        if (resolvedAtom == null || (parameters = resolvedAtom.getParameters()) == null) {
            List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            List<ValueParameterDescriptor> list2 = valueParameters;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                arrayList2.add(((ValueParameterDescriptor) it2.next()).getType());
            }
            parameters = arrayList2;
        }
        if (returnType2 == null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        FunctionLiteralTypes.ProcessedType processedTypeSubstituteAndApproximate5 = substituteAndApproximate(returnType2, substitutor);
        descriptor.setReturnType(processedTypeSubstituteAndApproximate5.getApproximatedType());
        ReceiverParameterDescriptor extensionReceiverParameter2 = descriptor.getExtensionReceiverParameter();
        if (type == null || (processedTypeSubstituteAndApproximate = substituteAndApproximate(type, substitutor)) == null) {
            processedTypeSubstituteAndApproximate = null;
        } else if (extensionReceiverParameter2 != null) {
            substituteFunctionLiteralDescriptor$setOutTypeIfNecessary(extensionReceiverParameter2, processedTypeSubstituteAndApproximate);
        }
        List<ReceiverParameterDescriptor> contextReceiverParameters2 = descriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters2, "getContextReceiverParameters(...)");
        ArrayList arrayList3 = new ArrayList();
        int i = 0;
        int i2 = 0;
        for (Object obj : contextReceiverParameters2) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ReceiverParameterDescriptor receiverParameterDescriptor = (ReceiverParameterDescriptor) obj;
            KotlinType kotlinType = (KotlinType) CollectionsKt.getOrNull(contextReceivers, i2);
            if (kotlinType != null) {
                Intrinsics.checkNotNull(kotlinType);
                processedTypeSubstituteAndApproximate4 = substituteAndApproximate(kotlinType, substitutor);
                if (processedTypeSubstituteAndApproximate4 != null) {
                    Intrinsics.checkNotNull(receiverParameterDescriptor);
                    substituteFunctionLiteralDescriptor$setOutTypeIfNecessary(receiverParameterDescriptor, processedTypeSubstituteAndApproximate4);
                } else {
                    processedTypeSubstituteAndApproximate4 = null;
                }
            } else {
                processedTypeSubstituteAndApproximate4 = null;
            }
            if (processedTypeSubstituteAndApproximate4 != null) {
                arrayList3.add(processedTypeSubstituteAndApproximate4);
            }
            i2 = i3;
        }
        ArrayList arrayList4 = arrayList3;
        ReceiverParameterDescriptor dispatchReceiverParameter2 = descriptor.mo6753getDispatchReceiverParameter();
        if (type2 != null && (processedTypeSubstituteAndApproximate3 = substituteAndApproximate(type2, substitutor)) != null && dispatchReceiverParameter2 != null) {
            substituteFunctionLiteralDescriptor$setOutTypeIfNecessary(dispatchReceiverParameter2, processedTypeSubstituteAndApproximate3);
        }
        List<ValueParameterDescriptor> valueParameters2 = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : valueParameters2) {
            int i4 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) obj2;
            KotlinType kotlinType2 = (KotlinType) CollectionsKt.getOrNull(parameters, i);
            if (kotlinType2 != null) {
                Intrinsics.checkNotNull(kotlinType2);
                processedTypeSubstituteAndApproximate2 = substituteAndApproximate(kotlinType2, substitutor);
                if (processedTypeSubstituteAndApproximate2 == null) {
                    processedTypeSubstituteAndApproximate2 = null;
                } else if (valueParameterDescriptor instanceof ValueParameterDescriptorImpl) {
                    ValueParameterDescriptorImpl valueParameterDescriptorImpl = (ValueParameterDescriptorImpl) valueParameterDescriptor;
                    if (TypeUtilsKt.shouldBeUpdated(valueParameterDescriptorImpl.getType())) {
                        valueParameterDescriptorImpl.setOutType(processedTypeSubstituteAndApproximate2.getApproximatedType());
                    }
                }
            } else {
                processedTypeSubstituteAndApproximate2 = null;
            }
            if (processedTypeSubstituteAndApproximate2 != null) {
                arrayList5.add(processedTypeSubstituteAndApproximate2);
            }
            i = i4;
        }
        return new FunctionLiteralTypes(processedTypeSubstituteAndApproximate5, arrayList5, processedTypeSubstituteAndApproximate, arrayList4);
    }

    private static final void substituteFunctionLiteralDescriptor$setOutTypeIfNecessary(ReceiverParameterDescriptor receiverParameterDescriptor, FunctionLiteralTypes.ProcessedType processedType) {
        if (receiverParameterDescriptor instanceof ReceiverParameterDescriptorImpl) {
            ReceiverParameterDescriptorImpl receiverParameterDescriptorImpl = (ReceiverParameterDescriptorImpl) receiverParameterDescriptor;
            if (TypeUtilsKt.shouldBeUpdated(receiverParameterDescriptorImpl.getType())) {
                receiverParameterDescriptorImpl.setOutType(processedType.getApproximatedType());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void completeLambda(ResolvedLambdaAtom resolvedAtom) {
        Pair pair;
        KotlinType kotlinTypeUpdateRecordedType;
        ResolvedLambdaAtom resolvedLambdaAtomUnwrap = ResolutionAtomsKt.unwrap(resolvedAtom);
        ReturnArgumentsInfo resultArgumentsInfo = resolvedLambdaAtomUnwrap.getResultArgumentsInfo();
        Intrinsics.checkNotNull(resultArgumentsInfo);
        PSIKotlinCallArgument psiCallArgument = NewCallArgumentsKt.getPsiCallArgument(resolvedLambdaAtomUnwrap.getAtom());
        if (psiCallArgument instanceof LambdaKotlinCallArgumentImpl) {
            LambdaKotlinCallArgumentImpl lambdaKotlinCallArgumentImpl = (LambdaKotlinCallArgumentImpl) psiCallArgument;
            pair = TuplesKt.to(lambdaKotlinCallArgumentImpl.getKtLambdaExpression(), lambdaKotlinCallArgumentImpl.getKtLambdaExpression().getFunctionLiteral());
        } else {
            if (!(psiCallArgument instanceof FunctionExpressionImpl)) {
                throw new AssertionError("Unexpected psiCallArgument for resolved lambda argument: " + psiCallArgument);
            }
            FunctionExpressionImpl functionExpressionImpl = (FunctionExpressionImpl) psiCallArgument;
            pair = TuplesKt.to(functionExpressionImpl.getKtFunction(), functionExpressionImpl.getKtFunction());
        }
        Object objComponent1 = pair.component1();
        NavigatablePsiElement navigatablePsiElement = (NavigatablePsiElement) pair.component2();
        Object obj = this.topLevelTrace.getContext().get(BindingContext.FUNCTION, navigatablePsiElement);
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl = obj instanceof SimpleFunctionDescriptorImpl ? (SimpleFunctionDescriptorImpl) obj : null;
        if (simpleFunctionDescriptorImpl == null) {
            return;
        }
        FunctionLiteralTypes functionLiteralTypesSubstituteFunctionLiteralDescriptor = substituteFunctionLiteralDescriptor(resolvedLambdaAtomUnwrap, simpleFunctionDescriptorImpl, this.resultSubstitutor);
        KtExpression ktExpression = (KtExpression) objComponent1;
        KotlinType type = this.topLevelTrace.getType(ktExpression);
        if (type == null) {
            if (!(navigatablePsiElement instanceof KtNamedFunction) || ((KtNamedFunction) navigatablePsiElement).mo7392getNameIdentifier() == null) {
                throw new AssertionError("No type for resolved lambda argument: " + ktExpression.getText());
            }
            return;
        }
        KotlinBuiltIns kotlinBuiltIns = this.builtIns;
        Annotations annotations = type.getAnnotations();
        FunctionLiteralTypes.ProcessedType receiverType = functionLiteralTypesSubstituteFunctionLiteralDescriptor.getReceiverType();
        KotlinType substitutedType = receiverType != null ? receiverType.getSubstitutedType() : null;
        List<FunctionLiteralTypes.ProcessedType> contextReceiverTypes = functionLiteralTypesSubstituteFunctionLiteralDescriptor.getContextReceiverTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceiverTypes, 10));
        Iterator<T> it = contextReceiverTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(((FunctionLiteralTypes.ProcessedType) it.next()).getSubstitutedType());
        }
        ArrayList arrayList2 = arrayList;
        List<FunctionLiteralTypes.ProcessedType> parameterTypes = functionLiteralTypesSubstituteFunctionLiteralDescriptor.getParameterTypes();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(parameterTypes, 10));
        Iterator<T> it2 = parameterTypes.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((FunctionLiteralTypes.ProcessedType) it2.next()).getSubstitutedType());
        }
        this.topLevelTrace.recordType(ktExpression, FunctionTypesKt.createFunctionType(kotlinBuiltIns, annotations, substitutedType, arrayList2, arrayList3, null, functionLiteralTypesSubstituteFunctionLiteralDescriptor.getReturnType().getSubstitutedType(), resolvedLambdaAtomUnwrap.getIsSuspend()));
        for (KotlinCallArgument kotlinCallArgument : resultArgumentsInfo.getNonErrorArguments()) {
            PSIKotlinCallArgument pSIKotlinCallArgument = kotlinCallArgument instanceof PSIKotlinCallArgument ? (PSIKotlinCallArgument) kotlinCallArgument : null;
            if (pSIKotlinCallArgument != null) {
                Context contextReplaceBindingTrace = ((BasicCallResolutionContext) ((BasicCallResolutionContext) this.topLevelCallContext.replaceDataFlowInfo(pSIKotlinCallArgument.getDataFlowInfoAfterThisArgument())).replaceExpectedType(functionLiteralTypesSubstituteFunctionLiteralDescriptor.getReturnType().getApproximatedType())).replaceBindingTrace(this.topLevelTrace);
                Intrinsics.checkNotNullExpressionValue(contextReplaceBindingTrace, "replaceBindingTrace(...)");
                BasicCallResolutionContext basicCallResolutionContext = (BasicCallResolutionContext) contextReplaceBindingTrace;
                KtExpression argumentExpression = pSIKotlinCallArgument.getValueArgument().getArgumentExpression();
                if (argumentExpression != null && (kotlinTypeUpdateRecordedType = this.kotlinToResolvedCallTransformer.updateRecordedType(argumentExpression, null, basicCallResolutionContext, null, true)) != null) {
                    NewSchemeOfIntegerOperatorResolutionChecker.checkArgument(kotlinTypeUpdateRecordedType, argumentExpression, this.topLevelTrace, this.moduleDescriptor);
                }
            }
        }
    }

    private final CallableReferenceResultTypeInfo updateCallableReferenceResultType(CallableReferenceResolutionCandidate callableCandidate) {
        FreshVariableNewTypeSubstitutor freshVariablesSubstitutor;
        EmptySubstitutor composedSubstitutor;
        CallableReceiver dispatchReceiver;
        ReceiverValueWithSmartCastInfo receiver;
        ReceiverValue receiverValue;
        ReceiverValueWithSmartCastInfo receiver2;
        ReceiverValue receiverValue2;
        ReceiverValueWithSmartCastInfo receiver3;
        ReceiverValue receiverValue3;
        PsiElement parent = PSIKotlinCallsKt.getPsiKotlinCall(callableCandidate.getResolvedCall().getAtom()).getPsiCall().getCallElement().getLightParent();
        ReceiverValue receiverValueUpdateReceiverValue = null;
        KtCallableReferenceExpression ktCallableReferenceExpression = parent instanceof KtCallableReferenceExpression ? (KtCallableReferenceExpression) parent : null;
        if (ktCallableReferenceExpression == null || (freshVariablesSubstitutor = callableCandidate.getFreshVariablesSubstitutor()) == null) {
            return null;
        }
        List<TypeVariableFromCallableDescriptor> freshVariables = freshVariablesSubstitutor.getFreshVariables();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(freshVariables, 10));
        Iterator<T> it = freshVariables.iterator();
        while (it.hasNext()) {
            arrayList.add(this.resultSubstitutor.safeSubstitute(((TypeVariableFromCallableDescriptor) it.next()).getDefaultType()));
        }
        ArrayList arrayList2 = arrayList;
        List<TypeParameterDescriptor> typeParameters = callableCandidate.getCandidate().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((TypeParameterDescriptor) it2.next()).getTypeConstructor());
        }
        NewTypeSubstitutorByConstructorMap newTypeSubstitutorByConstructorMap = new NewTypeSubstitutorByConstructorMap(MapsKt.toMap(CollectionsKt.zip(arrayList3, arrayList2)));
        if (DescriptorUtilKt.isSupportedForCallableReference(callableCandidate.getCandidate())) {
            composedSubstitutor = new ComposedSubstitutor(newTypeSubstitutorByConstructorMap, this.resultSubstitutor);
        } else {
            composedSubstitutor = EmptySubstitutor.INSTANCE;
        }
        UnwrappedType unwrappedTypeSafeSubstitute = composedSubstitutor.safeSubstitute(callableCandidate.getReflectionCandidateType());
        this.argumentTypeResolver.updateResultArgumentTypeIfNotDenotable(this.topLevelTrace, this.expressionTypingServices.getStatementFilter(), unwrappedTypeSafeSubstitute, ktCallableReferenceExpression);
        CallableReceiver dispatchReceiver2 = callableCandidate.getDispatchReceiver();
        ReceiverValue receiverValueUpdateReceiverValue2 = (dispatchReceiver2 == null || (receiver3 = dispatchReceiver2.getReceiver()) == null || (receiverValue3 = receiver3.getReceiverValue()) == null) ? null : updateReceiverValue(receiverValue3, composedSubstitutor);
        CallableReceiver extensionReceiver = callableCandidate.getExtensionReceiver();
        ReceiverValue receiverValueUpdateReceiverValue3 = (extensionReceiver == null || (receiver2 = extensionReceiver.getReceiver()) == null || (receiverValue2 = receiver2.getReceiverValue()) == null) ? null : updateReceiverValue(receiverValue2, composedSubstitutor);
        CallableDescriptor candidate = callableCandidate.getCandidate();
        if (candidate instanceof FunctionDescriptor) {
            DoubleColonExpressionResolver doubleColonExpressionResolver = this.doubleColonExpressionResolver;
            BasicCallResolutionContext basicCallResolutionContext = this.topLevelCallContext;
            CallableDescriptor candidate2 = callableCandidate.getCandidate();
            Intrinsics.checkNotNull(candidate2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
            doubleColonExpressionResolver.bindFunctionReference$frontend(ktCallableReferenceExpression, unwrappedTypeSafeSubstitute, basicCallResolutionContext, (FunctionDescriptor) candidate2);
        } else if (candidate instanceof PropertyDescriptor) {
            DoubleColonExpressionResolver.bindPropertyReference$frontend$default(this.doubleColonExpressionResolver, ktCallableReferenceExpression, unwrappedTypeSafeSubstitute, this.topLevelCallContext, false, 8, null);
        }
        DoubleColonExpressionResolver doubleColonExpressionResolver2 = this.doubleColonExpressionResolver;
        CallableDescriptor candidate3 = callableCandidate.getCandidate();
        BindingTrace trace = this.topLevelCallContext.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        doubleColonExpressionResolver2.checkReferenceIsToAllowedMember$frontend(candidate3, trace, ktCallableReferenceExpression);
        int i = WhenMappings.$EnumSwitchMapping$0[callableCandidate.getExplicitReceiverKind().ordinal()];
        if (i == 1) {
            dispatchReceiver = callableCandidate.getDispatchReceiver();
        } else {
            dispatchReceiver = i != 2 ? null : callableCandidate.getExtensionReceiver();
        }
        if (dispatchReceiver != null && (receiver = dispatchReceiver.getReceiver()) != null && (receiverValue = receiver.getReceiverValue()) != null) {
            receiverValueUpdateReceiverValue = updateReceiverValue(receiverValue, composedSubstitutor);
        }
        return new CallableReferenceResultTypeInfo(receiverValueUpdateReceiverValue2, receiverValueUpdateReceiverValue3, receiverValueUpdateReceiverValue, composedSubstitutor, unwrappedTypeSafeSubstitute);
    }

    private final CallableReferenceResultTypeInfo extractCallableReferenceResultTypeInfoFromDescriptor(CallableReferenceResolutionCandidate callableCandidate, CallableDescriptor recordedDescriptor) {
        ReceiverValue receiverValue;
        ReceiverValueWithSmartCastInfo receiver;
        ReceiverValue receiverValue2;
        ReceiverValueWithSmartCastInfo receiver2;
        ReceiverValue receiverValue3;
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = recordedDescriptor.mo6753getDispatchReceiverParameter();
        if (receiverParameterDescriptorMo6753getDispatchReceiverParameter == null || (receiverValue = receiverParameterDescriptorMo6753getDispatchReceiverParameter.getValue()) == null) {
            CallableReceiver dispatchReceiver = callableCandidate.getDispatchReceiver();
            if (dispatchReceiver == null || (receiver = dispatchReceiver.getReceiver()) == null) {
                receiverValue = null;
            } else {
                ReceiverValue receiverValue4 = receiver.getReceiverValue();
                receiverValue = receiverValue4;
            }
        } else {
            receiverValue = receiverValue4;
        }
        ReceiverParameterDescriptor extensionReceiverParameter = recordedDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter == null || (receiverValue = extensionReceiverParameter.getValue()) == null) {
            CallableReceiver extensionReceiver = callableCandidate.getExtensionReceiver();
            if (extensionReceiver == null || (receiver2 = extensionReceiver.getReceiver()) == null) {
                receiverValue2 = null;
            } else {
                ReceiverValue receiverValue5 = receiver2.getReceiverValue();
                receiverValue2 = receiverValue5;
            }
        } else {
            receiverValue2 = receiverValue5;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[callableCandidate.getExplicitReceiverKind().ordinal()];
        if (i != 1) {
            receiverValue3 = i != 2 ? null : receiverValue2;
        } else {
            receiverValue3 = receiverValue;
        }
        return new CallableReferenceResultTypeInfo(receiverValue, receiverValue2, receiverValue3, EmptySubstitutor.INSTANCE, replaceFunctionTypeArgumentsByDescriptor(callableCandidate.getReflectionCandidateType(), recordedDescriptor));
    }

    private final KotlinType replaceFunctionTypeArgumentsByDescriptor(KotlinType kotlinType, CallableDescriptor callableDescriptor) {
        TypeProjection typeProjectionReplaceType;
        if (!(callableDescriptor instanceof CallableMemberDescriptor)) {
            return callableDescriptor instanceof ValueDescriptor ? TypeSubstitutionKt.replace$default(kotlinType, ((ValueDescriptor) callableDescriptor).getType().getArguments(), null, null, 6, null) : kotlinType;
        }
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) callableDescriptor;
        ReceiverParameterDescriptor extensionReceiverParameter = callableMemberDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            listCreateListBuilder.add(extensionReceiverParameter.getType());
        }
        List<ValueParameterDescriptor> valueParameters = callableMemberDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ValueParameterDescriptor) it.next()).getType());
        }
        listCreateListBuilder.addAll(arrayList);
        listCreateListBuilder.add(callableMemberDescriptor.getReturnType());
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        if (listBuild.size() != kotlinType.getArguments().size()) {
            return kotlinType;
        }
        List<TypeProjection> arguments = kotlinType.getArguments();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        int i = 0;
        for (Object obj : arguments) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection = (TypeProjection) obj;
            KotlinType kotlinType2 = (KotlinType) listBuild.get(i);
            if (kotlinType2 != null && (typeProjectionReplaceType = typeProjection.replaceType(kotlinType2)) != null) {
                typeProjection = typeProjectionReplaceType;
            }
            Intrinsics.checkNotNull(typeProjection);
            arrayList2.add(typeProjection);
            i = i2;
        }
        return TypeSubstitutionKt.replace$default(kotlinType, arrayList2, null, null, 6, null);
    }

    public final KotlinType completeCallableReferenceCall(NewCallableReferenceResolvedCall<?> resolvedCall) {
        CallableReferenceResolutionCandidate candidate;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        ResolvedCallableReferenceCallAtom resolvedCallAtom = resolvedCall.getResolvedCallAtom();
        if (resolvedCallAtom == null || (candidate = resolvedCallAtom.getCandidate()) == null) {
            return null;
        }
        return completeCallableReference$default(this, candidate, resolvedCall.getResultingDescriptor(), resolvedCall, null, 8, null);
    }

    public final KotlinType completeCallableReferenceArgument(ResolvedCallableReferenceArgumentAtom resolvedAtom) {
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
        CallableDescriptor callableDescriptor = null;
        if (resolvedAtom.getCompleted()) {
            return null;
        }
        PSIKotlinCallArgument psiCallArgument = NewCallArgumentsKt.getPsiCallArgument(resolvedAtom.getAtom());
        Intrinsics.checkNotNull(psiCallArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.CallableReferenceKotlinCallArgumentImpl");
        CallableReferenceKotlinCallArgumentImpl callableReferenceKotlinCallArgumentImpl = (CallableReferenceKotlinCallArgumentImpl) psiCallArgument;
        CallableReferenceResolutionCandidate candidate = resolvedAtom.getCandidate();
        if (candidate == null) {
            return null;
        }
        CallableDescriptor candidate2 = candidate.getCandidate();
        if (candidate2 instanceof FunctionDescriptor) {
            callableDescriptor = (CallableDescriptor) this.topLevelCallContext.trace.get(BindingContext.FUNCTION, callableReferenceKotlinCallArgumentImpl.getKtCallableReferenceExpression());
        } else if (candidate2 instanceof PropertyDescriptor) {
            callableDescriptor = (CallableDescriptor) this.topLevelCallContext.trace.get(BindingContext.VARIABLE, callableReferenceKotlinCallArgumentImpl.getKtCallableReferenceExpression());
        }
        DataFlowInfo dataFlowInfoAfterThisArgument = NewCallArgumentsKt.getPsiCallArgument(resolvedAtom.getAtom()).getDataFlowInfoAfterThisArgument();
        TypeApproximator typeApproximator = this.typeApproximator;
        LanguageVersionSettings languageVersionSettings = this.expressionTypingServices.getLanguageVersionSettings();
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "getLanguageVersionSettings(...)");
        KotlinType kotlinTypeCompleteCallableReference = completeCallableReference(candidate, callableDescriptor, new NewCallableReferenceResolvedCall(resolvedAtom, typeApproximator, languageVersionSettings, null, 8, null), dataFlowInfoAfterThisArgument);
        resolvedAtom.setCompleted(true);
        return kotlinTypeCompleteCallableReference;
    }

    static /* synthetic */ KotlinType completeCallableReference$default(ResolvedAtomCompleter resolvedAtomCompleter, CallableReferenceResolutionCandidate callableReferenceResolutionCandidate, CallableDescriptor callableDescriptor, NewAbstractResolvedCall newAbstractResolvedCall, DataFlowInfo dataFlowInfo, int i, Object obj) {
        if ((i & 8) != 0) {
            dataFlowInfo = null;
        }
        return resolvedAtomCompleter.completeCallableReference(callableReferenceResolutionCandidate, callableDescriptor, newAbstractResolvedCall, dataFlowInfo);
    }

    private final KotlinType completeCallableReference(CallableReferenceResolutionCandidate callableCandidate, CallableDescriptor recordedDescriptor, NewAbstractResolvedCall<?> resolvedCall, DataFlowInfo additionalDataFlowInfo) {
        CallableReferenceResultTypeInfo callableReferenceResultTypeInfoUpdateCallableReferenceResultType;
        KotlinTypeInfo kotlinTypeInfoCreateTypeInfo;
        CallableReceiver extensionReceiver = callableCandidate.getExtensionReceiver();
        boolean zSupportsFeature = this.topLevelCallContext.languageVersionSettings.supportsFeature(LanguageFeature.UnrestrictedBuilderInference);
        KtCallableReferenceExpression ktCallableReferenceExpressionExtractCallableReferenceExpression = CallUtilKt.extractCallableReferenceExpression(PSIKotlinCallsKt.getPsiKotlinCall(callableCandidate.getResolvedCall().getAtom()));
        if (ktCallableReferenceExpressionExtractCallableReferenceExpression == null) {
            return null;
        }
        if (extensionReceiver != null && !zSupportsFeature) {
            KotlinType type = extensionReceiver.getReceiver().getReceiverValue().getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.ResolvedAtomCompleter.completeCallableReference.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it instanceof StubTypeForBuilderInference);
                }
            })) {
                BindingTrace bindingTrace = this.topLevelTrace;
                Diagnostic diagnosticOn = Errors.TYPE_INFERENCE_POSTPONED_VARIABLE_IN_RECEIVER_TYPE.on(ktCallableReferenceExpressionExtractCallableReferenceExpression);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                DiagnosticUtilsKt.reportDiagnosticOnce(bindingTrace, diagnosticOn);
                return null;
            }
        }
        if (recordedDescriptor != null) {
            callableReferenceResultTypeInfoUpdateCallableReferenceResultType = extractCallableReferenceResultTypeInfoFromDescriptor(callableCandidate, recordedDescriptor);
        } else {
            callableReferenceResultTypeInfoUpdateCallableReferenceResultType = updateCallableReferenceResultType(callableCandidate);
        }
        if (callableReferenceResultTypeInfoUpdateCallableReferenceResultType == null) {
            return null;
        }
        if (callableReferenceResultTypeInfoUpdateCallableReferenceResultType.getDispatchReceiver() != null) {
            KotlinType type2 = callableReferenceResultTypeInfoUpdateCallableReferenceResultType.getDispatchReceiver().getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            resolvedCall.updateDispatchReceiverType(type2);
        }
        if (callableReferenceResultTypeInfoUpdateCallableReferenceResultType.getExtensionReceiver() != null) {
            KotlinType type3 = callableReferenceResultTypeInfoUpdateCallableReferenceResultType.getExtensionReceiver().getType();
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            resolvedCall.updateExtensionReceiverType(type3);
        }
        resolvedCall.setResultingSubstitutor(callableReferenceResultTypeInfoUpdateCallableReferenceResultType.getSubstitutor());
        recordArgumentAdaptationForCallableReference(resolvedCall, callableCandidate.getCallableReferenceAdaptation());
        Call callMakeCall = CallMaker.makeCall(ktCallableReferenceExpressionExtractCallableReferenceExpression.getCallableReference(), callableReferenceResultTypeInfoUpdateCallableReferenceResultType.getExplicitReceiver(), null, ktCallableReferenceExpressionExtractCallableReferenceExpression.getCallableReference(), CollectionsKt.emptyList());
        Intrinsics.checkNotNullExpressionValue(callMakeCall, "makeCall(...)");
        TracingStrategy tracingStrategyCreate = TracingStrategyImpl.create(ktCallableReferenceExpressionExtractCallableReferenceExpression.getCallableReference(), callMakeCall);
        Intrinsics.checkNotNullExpressionValue(tracingStrategyCreate, "create(...)");
        tracingStrategyCreate.bindCall(this.topLevelTrace, callMakeCall);
        NewAbstractResolvedCall<?> newAbstractResolvedCall = resolvedCall;
        tracingStrategyCreate.bindReference(this.topLevelTrace, newAbstractResolvedCall);
        tracingStrategyCreate.bindResolvedCall(this.topLevelTrace, newAbstractResolvedCall);
        if (additionalDataFlowInfo != null) {
            kotlinTypeInfoCreateTypeInfo = TypeInfoFactoryKt.createTypeInfo(callableReferenceResultTypeInfoUpdateCallableReferenceResultType.getResultType(), additionalDataFlowInfo);
        } else {
            kotlinTypeInfoCreateTypeInfo = TypeInfoFactoryKt.createTypeInfo(callableReferenceResultTypeInfoUpdateCallableReferenceResultType.getResultType());
        }
        this.topLevelTrace.record(BindingContext.EXPRESSION_TYPE_INFO, ktCallableReferenceExpressionExtractCallableReferenceExpression, kotlinTypeInfoCreateTypeInfo);
        this.topLevelTrace.record(BindingContext.PROCESSED, ktCallableReferenceExpressionExtractCallableReferenceExpression);
        this.kotlinToResolvedCallTransformer.runCallCheckers(newAbstractResolvedCall, this.topLevelCallCheckerContext);
        return callableReferenceResultTypeInfoUpdateCallableReferenceResultType.getResultType();
    }

    private final ReceiverValue updateReceiverValue(ReceiverValue receiverValue, NewTypeSubstitutor newTypeSubstitutor) {
        UnwrappedType unwrappedTypeSafeSubstitute = newTypeSubstitutor.safeSubstitute(receiverValue.getType().unwrap());
        UnwrappedType unwrappedTypeApproximateToSuperType = this.typeApproximator.approximateToSuperType(unwrappedTypeSafeSubstitute, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.FinalApproximationAfterResolutionAndInference.INSTANCE);
        if (unwrappedTypeApproximateToSuperType != null) {
            unwrappedTypeSafeSubstitute = unwrappedTypeApproximateToSuperType;
        }
        if (Intrinsics.areEqual(receiverValue.getType(), unwrappedTypeSafeSubstitute)) {
            return receiverValue;
        }
        Intrinsics.checkNotNull(unwrappedTypeSafeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
        ReceiverValue receiverValueMo7466replaceType = receiverValue.mo7466replaceType(unwrappedTypeSafeSubstitute);
        Intrinsics.checkNotNullExpressionValue(receiverValueMo7466replaceType, "replaceType(...)");
        return receiverValueMo7466replaceType;
    }

    private final void recordArgumentAdaptationForCallableReference(NewAbstractResolvedCall<?> resolvedCall, CallableReferenceAdaptation callableReferenceAdaptation) {
        VarargValueArgument varargValueArgument;
        ExpressionValueArgument expressionValueArgument;
        if (callableReferenceAdaptation == null) {
            return;
        }
        KtElement callElement = resolvedCall.get$fakeCall().getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        boolean z = resolvedCall.getDispatchReceiver() instanceof TransientReceiver;
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        for (Map.Entry<ValueParameterDescriptor, ResolvedCallArgument> entry : callableReferenceAdaptation.getMappedArguments().entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            ResolvedCallArgument value = entry.getValue();
            boolean z3 = true;
            if (Intrinsics.areEqual(value, ResolvedCallArgument.DefaultArgument.INSTANCE)) {
                varargValueArgument = DefaultValueArgument.DEFAULT;
            } else if (value instanceof ResolvedCallArgument.SimpleArgument) {
                ValueArgument valueArgumentRecordArgumentAdaptationForCallableReference$makeFakeValueArgument = recordArgumentAdaptationForCallableReference$makeFakeValueArgument(callElement, z, ((ResolvedCallArgument.SimpleArgument) value).getCallArgument());
                if (ArgumentsUtilsKt.isVararg(key)) {
                    expressionValueArgument = new VarargValueArgument(CollectionsKt.listOf(new FakeImplicitSpreadValueArgumentForCallableReferenceImpl(callElement, valueArgumentRecordArgumentAdaptationForCallableReference$makeFakeValueArgument)));
                } else {
                    expressionValueArgument = new ExpressionValueArgument(valueArgumentRecordArgumentAdaptationForCallableReference$makeFakeValueArgument);
                }
                z3 = z2;
                varargValueArgument = expressionValueArgument;
            } else {
                if (!(value instanceof ResolvedCallArgument.VarargArgument)) {
                    throw new NoWhenBranchMatchedException();
                }
                List<KotlinCallArgument> arguments = value.getArguments();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    arrayList2.add(recordArgumentAdaptationForCallableReference$makeFakeValueArgument(callElement, z, (KotlinCallArgument) it.next()));
                }
                varargValueArgument = new VarargValueArgument(arrayList2);
            }
            arrayList.add(TuplesKt.to(key, varargValueArgument));
            z2 = z3;
        }
        if (z2 || isCallableReferenceWithImplicitConversion(resolvedCall, callableReferenceAdaptation)) {
            resolvedCall.updateValueArguments(MapsKt.toMap(arrayList));
        }
    }

    private static final ValueArgument recordArgumentAdaptationForCallableReference$makeFakeValueArgument(KtElement ktElement, boolean z, KotlinCallArgument kotlinCallArgument) {
        FakeKotlinCallArgumentForCallableReference fakeKotlinCallArgumentForCallableReference = kotlinCallArgument instanceof FakeKotlinCallArgumentForCallableReference ? (FakeKotlinCallArgumentForCallableReference) kotlinCallArgument : null;
        if (fakeKotlinCallArgumentForCallableReference == null) {
            throw new AssertionError("FakeKotlinCallArgumentForCallableReference expected: " + kotlinCallArgument);
        }
        return new FakePositionalValueArgumentForCallableReferenceImpl(ktElement, z ? fakeKotlinCallArgumentForCallableReference.getIndex() + 1 : fakeKotlinCallArgumentForCallableReference.getIndex());
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [org.jetbrains.kotlin.descriptors.CallableDescriptor] */
    private final boolean isCallableReferenceWithImplicitConversion(NewAbstractResolvedCall<?> resolvedCall, CallableReferenceAdaptation callableReferenceAdaptation) {
        ?? resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (callableReferenceAdaptation.getCoercionStrategy() == CoercionStrategy.COERCION_TO_UNIT) {
            KotlinType returnType = resultingDescriptor.getReturnType();
            Intrinsics.checkNotNull(returnType);
            if (!TypeUtilsKt.isUnit(returnType)) {
                return true;
            }
        }
        return callableReferenceAdaptation.getSuspendConversionStrategy() == SuspendConversionStrategy.SUSPEND_CONVERSION;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void completeCollectionLiteralCalls(ResolvedCollectionLiteralAtom collectionLiteralArgument) {
        SimpleType simpleTypeSafeSubstitute;
        PSIKotlinCallArgument psiCallArgument = NewCallArgumentsKt.getPsiCallArgument(collectionLiteralArgument.getAtom());
        Intrinsics.checkNotNull(psiCallArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.CollectionLiteralKotlinCallArgumentImpl");
        CollectionLiteralKotlinCallArgumentImpl collectionLiteralKotlinCallArgumentImpl = (CollectionLiteralKotlinCallArgumentImpl) psiCallArgument;
        BasicCallResolutionContext outerCallContext = collectionLiteralKotlinCallArgumentImpl.getOuterCallContext();
        UnwrappedType expectedType = collectionLiteralArgument.getExpectedType();
        if (expectedType == null || (simpleTypeSafeSubstitute = this.resultSubstitutor.safeSubstitute(expectedType)) == null) {
            SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
            Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
            simpleTypeSafeSubstitute = NO_EXPECTED_TYPE;
        }
        Context contextReplaceContextDependency = ((BasicCallResolutionContext) ((BasicCallResolutionContext) outerCallContext.replaceBindingTrace(this.topLevelTrace)).replaceExpectedType(simpleTypeSafeSubstitute)).replaceContextDependency(ContextDependency.INDEPENDENT);
        Intrinsics.checkNotNullExpressionValue(contextReplaceContextDependency, "replaceContextDependency(...)");
        this.expressionTypingServices.getTypeInfo(collectionLiteralKotlinCallArgumentImpl.getCollectionLiteralExpression(), (BasicCallResolutionContext) contextReplaceContextDependency);
    }
}
