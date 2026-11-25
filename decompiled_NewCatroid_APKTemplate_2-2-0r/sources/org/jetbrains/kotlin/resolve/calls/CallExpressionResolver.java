package org.jetbrains.kotlin.resolve.calls;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.AstLoadingFilter;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.QualifiedExpressionResolveUtilKt;
import org.jetbrains.kotlin.resolve.QualifiedExpressionResolver;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CheckArgumentTypesMode;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.TemporaryTraceAndCache;
import org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArgumentsImpl;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsUtil;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.calls.util.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.calls.util.ResolveArgumentsMode;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassifierQualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.PackageQualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.Qualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.DataFlowAnalyzer;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: CallExpressionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000â\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u0000 M2\u00020\u0001:\u0001MBM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014J \u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0016\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u001c\u001a\u00020\u001dJ4\u0010!\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020 2\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020'H\u0002J\u0016\u0010(\u001a\u00020\u00182\u0006\u0010)\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001dJ@\u0010*\u001a\u0016\u0012\u0004\u0012\u00020,\u0012\f\u0012\n\u0012\u0004\u0012\u00020.\u0018\u00010-0+2\u0006\u0010/\u001a\u0002002\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u0003012\u0006\u00102\u001a\u0002032\u0006\u0010&\u001a\u00020'H\u0002J \u00104\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020#2\u0006\u00105\u001a\u0002062\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J*\u00107\u001a\u00020\u00182\u0006\u00108\u001a\u0002092\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\u001c\u001a\u00020\u001dJ4\u00107\u001a\u00020\u00182\u0006\u00108\u001a\u0002092\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020'H\u0002J4\u0010:\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010;\u001a\u0004\u0018\u00010<2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020'H\u0002J:\u0010=\u001a\u0010\u0012\u0004\u0012\u00020,\u0012\u0006\u0012\u0004\u0018\u00010>0+2\u0006\u00108\u001a\u0002092\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J \u0010?\u001a\u00020@2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010A\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\"\u0010B\u001a\u00020@2\u0006\u0010C\u001a\u00020D2\b\u0010;\u001a\u0004\u0018\u00010<2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J&\u0010E\u001a\b\u0012\u0004\u0012\u00020G0F2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010)\u001a\u0002092\u0006\u0010H\u001a\u00020IH\u0002J\u0010\u0010J\u001a\u00020@2\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u001a\u0010K\u001a\b\u0012\u0004\u0012\u0002060L*\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006N"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/CallExpressionResolver;", "", "callResolver", "Lorg/jetbrains/kotlin/resolve/calls/CallResolver;", "constantExpressionEvaluator", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "argumentTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "dataFlowAnalyzer", "Lorg/jetbrains/kotlin/types/expressions/DataFlowAnalyzer;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "qualifiedExpressionResolver", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(Lorg/jetbrains/kotlin/resolve/calls/CallResolver;Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;Lorg/jetbrains/kotlin/types/expressions/DataFlowAnalyzer;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "checkSelectorTypeInfo", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", SchemaSymbols.ATTVAL_QUALIFIED, "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "selectorTypeInfo", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "getCallExpressionTypeInfo", "callExpression", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "getCallExpressionTypeInfoWithoutFinalTypeCheck", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Receiver;", "callOperationNode", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "initialDataFlowInfoForArguments", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getQualifiedExpressionTypeInfo", "expression", "getResolvedCallForFunction", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "checkArguments", "Lorg/jetbrains/kotlin/resolve/calls/context/CheckArgumentTypesMode;", "getSafeOrUnsafeSelectorTypeInfo", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/resolve/calls/CallExpressionElement;", "getSimpleNameExpressionTypeInfo", "nameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "getUnsafeSelectorTypeInfo", "selectorExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getVariableType", "Lorg/jetbrains/kotlin/types/KotlinType;", "recordResultTypeInfo", "", "resultTypeInfo", "resolveDeferredReceiverInQualifiedExpression", "qualifier", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Qualifier;", "resolveSimpleName", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "traceAndCache", "Lorg/jetbrains/kotlin/resolve/calls/context/TemporaryTraceAndCache;", "setExpressionTypingServices", "elementChain", "", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallExpressionResolver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ArgumentTypeResolver argumentTypeResolver;
    private final KotlinBuiltIns builtIns;
    private final CallResolver callResolver;
    private final ConstantExpressionEvaluator constantExpressionEvaluator;
    private final DataFlowAnalyzer dataFlowAnalyzer;
    private final DataFlowValueFactory dataFlowValueFactory;
    private ExpressionTypingServices expressionTypingServices;
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final LanguageVersionSettings languageVersionSettings;
    private final QualifiedExpressionResolver qualifiedExpressionResolver;

    public CallExpressionResolver(CallResolver callResolver, ConstantExpressionEvaluator constantExpressionEvaluator, ArgumentTypeResolver argumentTypeResolver, DataFlowAnalyzer dataFlowAnalyzer, KotlinBuiltIns builtIns, QualifiedExpressionResolver qualifiedExpressionResolver, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(constantExpressionEvaluator, "constantExpressionEvaluator");
        Intrinsics.checkNotNullParameter(argumentTypeResolver, "argumentTypeResolver");
        Intrinsics.checkNotNullParameter(dataFlowAnalyzer, "dataFlowAnalyzer");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(qualifiedExpressionResolver, "qualifiedExpressionResolver");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.callResolver = callResolver;
        this.constantExpressionEvaluator = constantExpressionEvaluator;
        this.argumentTypeResolver = argumentTypeResolver;
        this.dataFlowAnalyzer = dataFlowAnalyzer;
        this.builtIns = builtIns;
        this.qualifiedExpressionResolver = qualifiedExpressionResolver;
        this.languageVersionSettings = languageVersionSettings;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
    }

    @Inject
    public final void setExpressionTypingServices(ExpressionTypingServices expressionTypingServices) {
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        this.expressionTypingServices = expressionTypingServices;
    }

    private final Pair<Boolean, ResolvedCall<FunctionDescriptor>> getResolvedCallForFunction(Call call, ResolutionContext<?> context, CheckArgumentTypesMode checkArguments, DataFlowInfo initialDataFlowInfoForArguments) {
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveFunctionCall = this.callResolver.resolveFunctionCall(BasicCallResolutionContext.create(context, call, checkArguments, new DataFlowInfoForArgumentsImpl(initialDataFlowInfoForArguments, call)));
        Intrinsics.checkNotNullExpressionValue(overloadResolutionResultsResolveFunctionCall, "resolveFunctionCall(...)");
        if (!overloadResolutionResultsResolveFunctionCall.isNothing()) {
            return new Pair<>(true, OverloadResolutionResultsUtil.getResultingCall(overloadResolutionResultsResolveFunctionCall, context));
        }
        return new Pair<>(false, null);
    }

    private final Pair<Boolean, KotlinType> getVariableType(KtSimpleNameExpression nameExpression, Receiver receiver, ASTNode callOperationNode, ExpressionTypingContext context) {
        KtSimpleNameExpression ktSimpleNameExpression = nameExpression;
        TemporaryTraceAndCache temporaryTraceAndCacheCreate = TemporaryTraceAndCache.create(context, "trace to resolve as local variable or property", ktSimpleNameExpression);
        Call callMakePropertyCall = CallMaker.makePropertyCall(receiver, callOperationNode, nameExpression);
        Intrinsics.checkNotNullExpressionValue(callMakePropertyCall, "makePropertyCall(...)");
        BasicCallResolutionContext basicCallResolutionContextCreate = BasicCallResolutionContext.create(context.replaceTraceAndCache(temporaryTraceAndCacheCreate), callMakePropertyCall, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS);
        Intrinsics.checkNotNullExpressionValue(basicCallResolutionContextCreate, "create(...)");
        OverloadResolutionResults<VariableDescriptor> overloadResolutionResultsResolveSimpleProperty = this.callResolver.resolveSimpleProperty(basicCallResolutionContextCreate);
        Intrinsics.checkNotNullExpressionValue(overloadResolutionResultsResolveSimpleProperty, "resolveSimpleProperty(...)");
        boolean zIsLHSOfDot = KtPsiUtil.isLHSOfDot(ktSimpleNameExpression);
        if (!overloadResolutionResultsResolveSimpleProperty.isNothing() && overloadResolutionResultsResolveSimpleProperty.getResultCode() != OverloadResolutionResults.Code.CANDIDATES_WITH_WRONG_RECEIVER && (!zIsLHSOfDot || !overloadResolutionResultsResolveSimpleProperty.isSingleResult() || !(overloadResolutionResultsResolveSimpleProperty.mo7437getResultingDescriptor() instanceof FakeCallableDescriptorForObject))) {
            temporaryTraceAndCacheCreate.commit();
            return new Pair<>(true, overloadResolutionResultsResolveSimpleProperty.isSingleResult() ? ((VariableDescriptor) overloadResolutionResultsResolveSimpleProperty.mo7437getResultingDescriptor()).getReturnType() : null);
        }
        temporaryTraceAndCacheCreate.commit();
        return new Pair<>(Boolean.valueOf(!overloadResolutionResultsResolveSimpleProperty.isNothing()), overloadResolutionResultsResolveSimpleProperty.isSingleResult() ? ((VariableDescriptor) overloadResolutionResultsResolveSimpleProperty.mo7437getResultingDescriptor()).getReturnType() : null);
    }

    public final KotlinTypeInfo getSimpleNameExpressionTypeInfo(KtSimpleNameExpression nameExpression, Receiver receiver, ASTNode callOperationNode, ExpressionTypingContext context) {
        Intrinsics.checkNotNullParameter(nameExpression, "nameExpression");
        Intrinsics.checkNotNullParameter(context, "context");
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        return getSimpleNameExpressionTypeInfo(nameExpression, receiver, callOperationNode, context, dataFlowInfo);
    }

    private final KotlinTypeInfo getSimpleNameExpressionTypeInfo(KtSimpleNameExpression nameExpression, Receiver receiver, ASTNode callOperationNode, ExpressionTypingContext context, DataFlowInfo initialDataFlowInfoForArguments) {
        ExpressionTypingContext expressionTypingContext = context;
        KtSimpleNameExpression ktSimpleNameExpression = nameExpression;
        TemporaryTraceAndCache temporaryTraceAndCacheCreate = TemporaryTraceAndCache.create(expressionTypingContext, "trace to resolve as variable", ktSimpleNameExpression);
        ExpressionTypingContext expressionTypingContextReplaceTraceAndCache = context.replaceTraceAndCache(temporaryTraceAndCacheCreate);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceTraceAndCache, "replaceTraceAndCache(...)");
        Pair<Boolean, KotlinType> variableType = getVariableType(nameExpression, receiver, callOperationNode, expressionTypingContextReplaceTraceAndCache);
        boolean zBooleanValue = variableType.component1().booleanValue();
        KotlinType kotlinTypeComponent2 = variableType.component2();
        if (zBooleanValue) {
            temporaryTraceAndCacheCreate.commit();
            return TypeInfoFactoryKt.createTypeInfo(kotlinTypeComponent2, initialDataFlowInfoForArguments);
        }
        Call callMakeCall = CallMaker.makeCall(nameExpression, receiver, callOperationNode, ktSimpleNameExpression, CollectionsKt.emptyList());
        Intrinsics.checkNotNullExpressionValue(callMakeCall, "makeCall(...)");
        TemporaryTraceAndCache temporaryTraceAndCacheCreate2 = TemporaryTraceAndCache.create(expressionTypingContext, "trace to resolve as function", ktSimpleNameExpression);
        ExpressionTypingContext expressionTypingContextReplaceTraceAndCache2 = context.replaceTraceAndCache(temporaryTraceAndCacheCreate2);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceTraceAndCache2, "replaceTraceAndCache(...)");
        Pair<Boolean, ResolvedCall<FunctionDescriptor>> resolvedCallForFunction = getResolvedCallForFunction(callMakeCall, expressionTypingContextReplaceTraceAndCache2, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, initialDataFlowInfoForArguments);
        boolean zBooleanValue2 = resolvedCallForFunction.component1().booleanValue();
        ResolvedCall<FunctionDescriptor> resolvedCallComponent2 = resolvedCallForFunction.component2();
        Unit unit = null;
        if (zBooleanValue2) {
            FunctionDescriptor functionDescriptor = resolvedCallComponent2 != null ? (FunctionDescriptor) resolvedCallComponent2.getResultingDescriptor() : null;
            if (!(functionDescriptor instanceof ConstructorDescriptor)) {
                temporaryTraceAndCacheCreate2.commit();
                context.trace.report(Errors.FUNCTION_CALL_EXPECTED.on(nameExpression, nameExpression, Boolean.valueOf(functionDescriptor == null || functionDescriptor.getValueParameters().size() > 0)));
                return TypeInfoFactoryKt.createTypeInfo(functionDescriptor != null ? functionDescriptor.getReturnType() : null, expressionTypingContext);
            }
        }
        TemporaryTraceAndCache temporaryTraceAndCacheCreate3 = TemporaryTraceAndCache.create(expressionTypingContext, "trace to resolve as qualifier", ktSimpleNameExpression);
        ExpressionTypingContext expressionTypingContextReplaceTraceAndCache3 = context.replaceTraceAndCache(temporaryTraceAndCacheCreate3);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceTraceAndCache3, "replaceTraceAndCache(...)");
        ExpressionTypingContext expressionTypingContext2 = expressionTypingContextReplaceTraceAndCache3;
        Qualifier qualifierResolveNameExpressionAsQualifierForDiagnostics = this.qualifiedExpressionResolver.resolveNameExpressionAsQualifierForDiagnostics(nameExpression, receiver, expressionTypingContext2);
        if (qualifierResolveNameExpressionAsQualifierForDiagnostics != null) {
            QualifiedExpressionResolveUtilKt.resolveQualifierAsStandaloneExpression(qualifierResolveNameExpressionAsQualifierForDiagnostics, expressionTypingContext2);
            temporaryTraceAndCacheCreate3.commit();
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            temporaryTraceAndCacheCreate.commit();
        }
        return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
    }

    public final KotlinTypeInfo getCallExpressionTypeInfo(KtCallExpression callExpression, ExpressionTypingContext context) {
        Intrinsics.checkNotNullParameter(callExpression, "callExpression");
        Intrinsics.checkNotNullParameter(context, "context");
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        KotlinTypeInfo callExpressionTypeInfoWithoutFinalTypeCheck = getCallExpressionTypeInfoWithoutFinalTypeCheck(callExpression, null, null, context, dataFlowInfo);
        if (context.contextDependency == ContextDependency.INDEPENDENT) {
            this.dataFlowAnalyzer.checkType(callExpressionTypeInfoWithoutFinalTypeCheck.getType(), callExpression, context);
        }
        return callExpressionTypeInfoWithoutFinalTypeCheck;
    }

    private final KotlinTypeInfo getCallExpressionTypeInfoWithoutFinalTypeCheck(KtCallExpression callExpression, Receiver receiver, ASTNode callOperationNode, ExpressionTypingContext context, DataFlowInfo initialDataFlowInfoForArguments) {
        DataFlowInfo jumpFlowInfo;
        Call callMakeCall = CallMaker.makeCall(receiver, callOperationNode, callExpression);
        Intrinsics.checkNotNullExpressionValue(callMakeCall, "makeCall(...)");
        ExpressionTypingContext expressionTypingContext = context;
        KtCallExpression ktCallExpression = callExpression;
        TemporaryTraceAndCache temporaryTraceAndCacheCreate = TemporaryTraceAndCache.create(expressionTypingContext, "trace to resolve as function call", ktCallExpression);
        ExpressionTypingContext expressionTypingContextReplaceTraceAndCache = context.replaceTraceAndCache(temporaryTraceAndCacheCreate);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceTraceAndCache, "replaceTraceAndCache(...)");
        Pair<Boolean, ResolvedCall<FunctionDescriptor>> resolvedCallForFunction = getResolvedCallForFunction(callMakeCall, expressionTypingContextReplaceTraceAndCache, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, initialDataFlowInfoForArguments);
        boolean zBooleanValue = resolvedCallForFunction.component1().booleanValue();
        ResolvedCall<FunctionDescriptor> resolvedCallComponent2 = resolvedCallForFunction.component2();
        boolean z = false;
        if (zBooleanValue) {
            FunctionDescriptor functionDescriptor = resolvedCallComponent2 != null ? (FunctionDescriptor) resolvedCallComponent2.getResultingDescriptor() : null;
            temporaryTraceAndCacheCreate.commit();
            if (callExpression.getValueArgumentList() == null && callExpression.getLambdaArguments().isEmpty()) {
                context.trace.report(Errors.FUNCTION_CALL_EXPECTED.on(callExpression, callExpression, Boolean.valueOf(functionDescriptor == null || functionDescriptor.getValueParameters().size() > 0)));
            }
            if (functionDescriptor == null) {
                return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
            }
            if (functionDescriptor instanceof ConstructorDescriptor) {
                ClassDescriptor constructedClass = ((ConstructorDescriptor) functionDescriptor).getConstructedClass();
                Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
                ClassDescriptor classDescriptor = constructedClass;
                if (DescriptorUtils.isAnnotationClass(classDescriptor)) {
                    Companion companion = INSTANCE;
                    BindingTrace trace = context.trace;
                    Intrinsics.checkNotNullExpressionValue(trace, "trace");
                    if (!companion.canInstantiateAnnotationClass(callExpression, trace) && !context.languageVersionSettings.supportsFeature(LanguageFeature.InstantiationOfAnnotationClasses)) {
                        context.trace.report(Errors.ANNOTATION_CLASS_CONSTRUCTOR_CALL.on(callExpression));
                    }
                }
                if (DescriptorUtils.isEnumClass(classDescriptor)) {
                    context.trace.report(Errors.ENUM_CLASS_CONSTRUCTOR_CALL.on(callExpression));
                }
                if (DescriptorUtils.isSealedClass(classDescriptor)) {
                    context.trace.report(Errors.SEALED_CLASS_CONSTRUCTOR_CALL.on(callExpression));
                }
            }
            KotlinType returnType = functionDescriptor.getReturnType();
            List<KtValueArgument> valueArguments = callExpression.getValueArguments();
            Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
            DataFlowInfo resultInfo = resolvedCallComponent2.getDataFlowInfoForArguments().getResultInfo();
            Intrinsics.checkNotNullExpressionValue(resultInfo, "getResultInfo(...)");
            Iterator<KtValueArgument> it = valueArguments.iterator();
            while (it.hasNext()) {
                KotlinTypeInfo kotlinTypeInfo = (KotlinTypeInfo) context.trace.get(BindingContext.EXPRESSION_TYPE_INFO, it.next().getArgumentExpression());
                if (kotlinTypeInfo != null && kotlinTypeInfo.getJumpOutPossible()) {
                    jumpFlowInfo = kotlinTypeInfo.getJumpFlowInfo();
                    z = true;
                    return TypeInfoFactoryKt.createTypeInfo(returnType, resultInfo, z, jumpFlowInfo);
                }
            }
            jumpFlowInfo = resultInfo;
            return TypeInfoFactoryKt.createTypeInfo(returnType, resultInfo, z, jumpFlowInfo);
        }
        KtExpression calleeExpression = callExpression.getCalleeExpression();
        if ((calleeExpression instanceof KtSimpleNameExpression) && callExpression.getTypeArgumentList() == null) {
            TemporaryTraceAndCache temporaryTraceAndCacheCreate2 = TemporaryTraceAndCache.create(expressionTypingContext, "trace to resolve as variable with 'invoke' call", ktCallExpression);
            ExpressionTypingContext expressionTypingContextReplaceTraceAndCache2 = context.replaceTraceAndCache(temporaryTraceAndCacheCreate2);
            Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceTraceAndCache2, "replaceTraceAndCache(...)");
            Pair<Boolean, KotlinType> variableType = getVariableType((KtSimpleNameExpression) calleeExpression, receiver, callOperationNode, expressionTypingContextReplaceTraceAndCache2);
            boolean zBooleanValue2 = variableType.component1().booleanValue();
            ErrorType errorTypeComponent2 = variableType.component2();
            TemporaryBindingTrace temporaryBindingTrace = temporaryTraceAndCacheCreate2.trace;
            WritableSlice<KtExpression, Qualifier> QUALIFIER = BindingContext.QUALIFIER;
            Intrinsics.checkNotNullExpressionValue(QUALIFIER, "QUALIFIER");
            Qualifier qualifier = (Qualifier) temporaryBindingTrace.get(QUALIFIER, calleeExpression);
            if (zBooleanValue2 && (qualifier == null || !(qualifier instanceof PackageQualifier))) {
                ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(callExpression, temporaryTraceAndCacheCreate2.trace.getContext());
                ResolvedCallImpl resolvedCallImpl = resolvedCall instanceof ResolvedCallImpl ? (ResolvedCallImpl) resolvedCall : null;
                if (resolvedCallImpl != null) {
                    resolvedCallImpl.addStatus(ResolutionStatus.OTHER_ERROR);
                }
                temporaryTraceAndCacheCreate2.commit();
                BindingTrace bindingTrace = context.trace;
                DiagnosticFactory2<KtExpression, KtExpression, KotlinType> diagnosticFactory2 = Errors.FUNCTION_EXPECTED;
                KtExpression ktExpression = calleeExpression;
                if (errorTypeComponent2 == null) {
                    errorTypeComponent2 = ErrorUtils.createErrorType(ErrorTypeKind.ERROR_EXPECTED_TYPE, new String[0]);
                }
                bindingTrace.report(diagnosticFactory2.on(ktExpression, calleeExpression, errorTypeComponent2));
                this.argumentTypeResolver.analyzeArgumentsAndRecordTypes(BasicCallResolutionContext.create(expressionTypingContext, callMakeCall, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, new DataFlowInfoForArgumentsImpl(initialDataFlowInfoForArguments, callMakeCall)), ResolveArgumentsMode.RESOLVE_FUNCTION_ARGUMENTS);
                return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
            }
        }
        temporaryTraceAndCacheCreate.commit();
        return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
    }

    private final List<CallExpressionElement> elementChain(KtQualifiedExpression ktQualifiedExpression, final ExpressionTypingContext expressionTypingContext) {
        return this.qualifiedExpressionResolver.resolveQualifierInExpressionAndUnroll(ktQualifiedExpression, expressionTypingContext, new Function1<KtSimpleNameExpression, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.CallExpressionResolver.elementChain.1

            /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.CallExpressionResolver$elementChain$1$WhenMappings */
            /* JADX INFO: compiled from: CallExpressionResolver.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[OverloadResolutionResults.Code.values().length];
                    try {
                        iArr[OverloadResolutionResults.Code.NAME_NOT_FOUND.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[OverloadResolutionResults.Code.CANDIDATES_WITH_WRONG_RECEIVER.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtSimpleNameExpression nameExpression) {
                Intrinsics.checkNotNullParameter(nameExpression, "nameExpression");
                TemporaryTraceAndCache temporaryTraceAndCacheCreate = TemporaryTraceAndCache.create(expressionTypingContext, "trace to resolve as local variable or property", nameExpression);
                CallExpressionResolver callExpressionResolver = this;
                ExpressionTypingContext expressionTypingContext2 = expressionTypingContext;
                Intrinsics.checkNotNull(temporaryTraceAndCacheCreate);
                OverloadResolutionResults overloadResolutionResultsResolveSimpleName = callExpressionResolver.resolveSimpleName(expressionTypingContext2, nameExpression, temporaryTraceAndCacheCreate);
                boolean z = false;
                if (!overloadResolutionResultsResolveSimpleName.isSingleResult() || !(overloadResolutionResultsResolveSimpleName.mo7437getResultingDescriptor() instanceof FakeCallableDescriptorForObject)) {
                    int i = WhenMappings.$EnumSwitchMapping$0[overloadResolutionResultsResolveSimpleName.getResultCode().ordinal()];
                    if (i != 1 && i != 2) {
                        boolean zSupportsFeature = expressionTypingContext.languageVersionSettings.supportsFeature(LanguageFeature.NewInference);
                        z = !zSupportsFeature || overloadResolutionResultsResolveSimpleName.isSuccess();
                        if (zSupportsFeature && z) {
                            temporaryTraceAndCacheCreate.commit();
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final OverloadResolutionResults<VariableDescriptor> resolveSimpleName(ExpressionTypingContext context, KtSimpleNameExpression expression, TemporaryTraceAndCache traceAndCache) {
        Call callMakePropertyCall = CallMaker.makePropertyCall(null, null, expression);
        Intrinsics.checkNotNullExpressionValue(callMakePropertyCall, "makePropertyCall(...)");
        BasicCallResolutionContext basicCallResolutionContextCreate = BasicCallResolutionContext.create(context.replaceTraceAndCache(traceAndCache), callMakePropertyCall, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS);
        Intrinsics.checkNotNullExpressionValue(basicCallResolutionContextCreate, "create(...)");
        OverloadResolutionResults<VariableDescriptor> overloadResolutionResultsResolveSimpleProperty = this.callResolver.resolveSimpleProperty(basicCallResolutionContextCreate);
        Intrinsics.checkNotNullExpressionValue(overloadResolutionResultsResolveSimpleProperty, "resolveSimpleProperty(...)");
        return overloadResolutionResultsResolveSimpleProperty;
    }

    private final KotlinTypeInfo getUnsafeSelectorTypeInfo(Receiver receiver, ASTNode callOperationNode, KtExpression selectorExpression, ExpressionTypingContext context, DataFlowInfo initialDataFlowInfoForArguments) {
        if (selectorExpression instanceof KtCallExpression) {
            return getCallExpressionTypeInfoWithoutFinalTypeCheck((KtCallExpression) selectorExpression, receiver, callOperationNode, context, initialDataFlowInfoForArguments);
        }
        if (selectorExpression instanceof KtSimpleNameExpression) {
            return getSimpleNameExpressionTypeInfo((KtSimpleNameExpression) selectorExpression, receiver, callOperationNode, context, initialDataFlowInfoForArguments);
        }
        if (selectorExpression != null) {
            ExpressionTypingServices expressionTypingServices = this.expressionTypingServices;
            if (expressionTypingServices == null) {
                Intrinsics.throwUninitializedPropertyAccessException("expressionTypingServices");
                expressionTypingServices = null;
            }
            ExpressionTypingContext expressionTypingContext = context;
            expressionTypingServices.getTypeInfo(selectorExpression, expressionTypingContext);
            context.trace.report(Errors.ILLEGAL_SELECTOR.on(selectorExpression));
            return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
        }
        return TypeInfoFactoryKt.noTypeInfo(context);
    }

    private final KotlinTypeInfo getSafeOrUnsafeSelectorTypeInfo(Receiver receiver, final CallExpressionElement element, ExpressionTypingContext context) {
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        ReceiverValue receiverValue = receiver instanceof ReceiverValue ? (ReceiverValue) receiver : null;
        DataFlowValue dataFlowValueCreateDataFlowValue = receiverValue != null ? this.dataFlowValueFactory.createDataFlowValue(receiverValue, context) : null;
        boolean z = true;
        boolean z2 = dataFlowValueCreateDataFlowValue != null && dataFlowInfo.getStableNullability(dataFlowValueCreateDataFlowValue).canBeNull();
        if (!z2 && !context.languageVersionSettings.supportsFeature(LanguageFeature.SafeCallsAreAlwaysNullable)) {
            z = false;
        }
        ASTNode aSTNode = (ASTNode) AstLoadingFilter.forceAllowTreeLoading(element.getQualified().getContainingFile(), new ThrowableComputable() { // from class: org.jetbrains.kotlin.resolve.calls.CallExpressionResolver$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return CallExpressionResolver.getSafeOrUnsafeSelectorTypeInfo$lambda$3(element);
            }
        });
        if (dataFlowValueCreateDataFlowValue != null && element.getSafe()) {
            if (z) {
                dataFlowInfo = dataFlowInfo.disequate(dataFlowValueCreateDataFlowValue, DataFlowValue.INSTANCE.nullValue(this.builtIns), this.languageVersionSettings);
            }
            if (!z2) {
                Companion companion = INSTANCE;
                BindingTrace trace = context.trace;
                Intrinsics.checkNotNullExpressionValue(trace, "trace");
                KotlinType type = ((ReceiverValue) receiver).getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                KtQualifiedExpression qualified = element.getQualified();
                Intrinsics.checkNotNull(aSTNode);
                LanguageVersionSettings languageVersionSettings = context.languageVersionSettings;
                Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
                companion.reportUnnecessarySafeCall(trace, type, qualified, aSTNode, receiver, languageVersionSettings);
            }
        }
        DataFlowInfo dataFlowInfo2 = dataFlowInfo;
        KtExpression selector = element.getSelector();
        KotlinTypeInfo unsafeSelectorTypeInfo = getUnsafeSelectorTypeInfo(receiver, aSTNode, selector, context, dataFlowInfo2);
        KotlinType type2 = unsafeSelectorTypeInfo.getType();
        if (type2 != null) {
            unsafeSelectorTypeInfo = unsafeSelectorTypeInfo.replaceType(this.kotlinTypeRefiner.refineType((KotlinTypeMarker) type2));
        }
        if (receiver instanceof Qualifier) {
            resolveDeferredReceiverInQualifiedExpression((Qualifier) receiver, selector, context);
        }
        KotlinType type3 = unsafeSelectorTypeInfo.getType();
        if (type3 != null) {
            if (element.getSafe() && z) {
                unsafeSelectorTypeInfo = unsafeSelectorTypeInfo.replaceType(TypeUtils.makeNullable(type3));
            }
            if (selector != null) {
                context.trace.recordType(selector, unsafeSelectorTypeInfo.getType());
            }
        }
        return unsafeSelectorTypeInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ASTNode getSafeOrUnsafeSelectorTypeInfo$lambda$3(CallExpressionElement element) {
        Intrinsics.checkNotNullParameter(element, "$element");
        return element.getNode();
    }

    private final KotlinTypeInfo checkSelectorTypeInfo(KtQualifiedExpression qualified, KotlinTypeInfo selectorTypeInfo, ExpressionTypingContext context) {
        INSTANCE.checkNestedClassAccess(qualified, context);
        ConstantExpressionEvaluator constantExpressionEvaluator = this.constantExpressionEvaluator;
        KtQualifiedExpression ktQualifiedExpression = qualified;
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        CompileTimeConstant<?> compileTimeConstantEvaluateExpression = constantExpressionEvaluator.evaluateExpression(ktQualifiedExpression, trace, context.expectedType);
        if (compileTimeConstantEvaluateExpression != null && compileTimeConstantEvaluateExpression.isPure()) {
            KotlinTypeInfo kotlinTypeInfoCreateCompileTimeConstantTypeInfo = this.dataFlowAnalyzer.createCompileTimeConstantTypeInfo(compileTimeConstantEvaluateExpression, ktQualifiedExpression, context);
            Intrinsics.checkNotNull(kotlinTypeInfoCreateCompileTimeConstantTypeInfo);
            return kotlinTypeInfoCreateCompileTimeConstantTypeInfo;
        }
        if (context.contextDependency != ContextDependency.INDEPENDENT) {
            return selectorTypeInfo;
        }
        this.dataFlowAnalyzer.checkType(selectorTypeInfo.getType(), ktQualifiedExpression, context);
        return selectorTypeInfo;
    }

    private final void recordResultTypeInfo(KtQualifiedExpression qualified, KotlinTypeInfo resultTypeInfo, ExpressionTypingContext context) {
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        if (Intrinsics.areEqual(trace.get(BindingContext.PROCESSED, qualified), (Object) true)) {
            return;
        }
        trace.record(BindingContext.PROCESSED, qualified);
        trace.record(BindingContext.EXPRESSION_TYPE_INFO, qualified, resultTypeInfo);
        LexicalScope scope = context.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        BindingContextUtilsKt.recordScope(trace, scope, qualified);
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = context.replaceDataFlowInfo(resultTypeInfo.getDataFlowInfo());
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceDataFlowInfo, "replaceDataFlowInfo(...)");
        BindingContextUtilsKt.recordDataFlowInfo(expressionTypingContextReplaceDataFlowInfo, qualified);
    }

    public final KotlinTypeInfo getQualifiedExpressionTypeInfo(KtQualifiedExpression expression, ExpressionTypingContext context) {
        KotlinTypeInfo kotlinTypeInfo;
        Qualifier qualifierCreate;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = context.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceContextDependency, "replaceContextDependency(...)");
        ExpressionTypingContext expressionTypingContext = expressionTypingContextReplaceContextDependency;
        BindingTrace trace = expressionTypingContext.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        List<CallExpressionElement> listElementChain = elementChain(expression, expressionTypingContext);
        KtExpression receiver = ((CallExpressionElement) CollectionsKt.first((List) listElementChain)).getReceiver();
        if (((Qualifier) trace.get(BindingContext.QUALIFIER, receiver)) == null) {
            ExpressionTypingServices expressionTypingServices = this.expressionTypingServices;
            if (expressionTypingServices == null) {
                Intrinsics.throwUninitializedPropertyAccessException("expressionTypingServices");
                expressionTypingServices = null;
            }
            kotlinTypeInfo = expressionTypingServices.getTypeInfo(receiver, expressionTypingContext);
        } else {
            DataFlowInfo dataFlowInfo = expressionTypingContext.dataFlowInfo;
            Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
            kotlinTypeInfo = new KotlinTypeInfo(null, dataFlowInfo, false, null, 12, null);
        }
        Intrinsics.checkNotNull(kotlinTypeInfo);
        DataFlowInfo dataFlowInfo2 = kotlinTypeInfo.getDataFlowInfo();
        int i = 1;
        DataFlowInfo dataFlowInfo3 = dataFlowInfo2;
        boolean z = true;
        KotlinTypeInfo kotlinTypeInfo2 = kotlinTypeInfo;
        for (CallExpressionElement callExpressionElement : listElementChain) {
            ErrorType type = kotlinTypeInfo2.getType();
            if (type == null) {
                ErrorTypeKind errorTypeKind = ErrorTypeKind.ERROR_RECEIVER_TYPE;
                String[] strArr = new String[i];
                KtExpression receiver2 = callExpressionElement.getReceiver();
                String referencedName = receiver2 instanceof KtNameReferenceExpression ? ((KtNameReferenceExpression) receiver2).getReferencedName() : receiver2.getText();
                Intrinsics.checkNotNull(referencedName);
                strArr[0] = referencedName;
                type = ErrorUtils.createErrorType(errorTypeKind, strArr);
            }
            Qualifier qualifier = (Qualifier) trace.get(BindingContext.QUALIFIER, callExpressionElement.getReceiver());
            if (qualifier != null) {
                qualifierCreate = qualifier;
            } else {
                ExpressionReceiver.Companion companion = ExpressionReceiver.INSTANCE;
                KtExpression receiver3 = callExpressionElement.getReceiver();
                BindingContext bindingContext = trace.getContext();
                Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
                qualifierCreate = companion.create(receiver3, type, bindingContext);
            }
            KtQualifiedExpression qualified = callExpressionElement.getQualified();
            boolean z2 = qualified == expression;
            ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = (z2 ? context : expressionTypingContext).replaceDataFlowInfo(((qualifierCreate instanceof ReceiverValue) && TypeUtils.isNullableType(((ReceiverValue) qualifierCreate).getType()) && !callExpressionElement.getSafe()) ? dataFlowInfo3 : kotlinTypeInfo2.getDataFlowInfo());
            Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceDataFlowInfo, "replaceDataFlowInfo(...)");
            ExpressionTypingContext expressionTypingContext2 = expressionTypingContextReplaceDataFlowInfo;
            KotlinTypeInfo safeOrUnsafeSelectorTypeInfo = getSafeOrUnsafeSelectorTypeInfo(qualifierCreate, callExpressionElement, expressionTypingContext2);
            z = z && !callExpressionElement.getSafe();
            if (z) {
                dataFlowInfo3 = safeOrUnsafeSelectorTypeInfo.getDataFlowInfo();
            }
            kotlinTypeInfo = checkSelectorTypeInfo(qualified, safeOrUnsafeSelectorTypeInfo, expressionTypingContext2).replaceDataFlowInfo(dataFlowInfo3);
            if (!z2) {
                recordResultTypeInfo(qualified, kotlinTypeInfo, expressionTypingContext2);
            }
            kotlinTypeInfo2 = safeOrUnsafeSelectorTypeInfo;
            i = 1;
        }
        return kotlinTypeInfo;
    }

    private final void resolveDeferredReceiverInQualifiedExpression(Qualifier qualifier, KtExpression selectorExpression, ExpressionTypingContext context) {
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(PsiUtilsKt.getCalleeExpressionIfAny(selectorExpression));
        KtReferenceExpression ktReferenceExpression = ktExpressionDeparenthesize instanceof KtReferenceExpression ? (KtReferenceExpression) ktExpressionDeparenthesize : null;
        QualifiedExpressionResolveUtilKt.resolveQualifierAsReceiverInExpression(qualifier, ktReferenceExpression != null ? (DeclarationDescriptor) context.trace.get(BindingContext.REFERENCE_TARGET, ktReferenceExpression) : null, context);
    }

    /* JADX INFO: compiled from: CallExpressionResolver.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J8\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/CallExpressionResolver$Companion;", "", "()V", "canInstantiateAnnotationClass", "", "expression", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "checkNestedClassAccess", "", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "isUnderAnnotationClassDeclaration", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "reportUnnecessarySafeCall", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "callElement", "callOperationNode", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Receiver;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean canInstantiateAnnotationClass(KtCallExpression expression, BindingTrace trace) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(trace, "trace");
            PsiElement parentOfType = PsiTreeUtil.getParentOfType(expression, (Class<? extends PsiElement>[]) new Class[]{KtValueArgument.class, KtParameter.class});
            if (!(parentOfType instanceof KtValueArgument)) {
                if (parentOfType instanceof KtParameter) {
                    return isUnderAnnotationClassDeclaration(trace, parentOfType);
                }
            } else {
                if (PsiTreeUtil.getParentOfType(parentOfType, KtAnnotationEntry.class, true) != null) {
                    return true;
                }
                PsiElement parentOfType2 = PsiTreeUtil.getParentOfType(parentOfType, KtParameter.class, true);
                if (parentOfType2 != null) {
                    return isUnderAnnotationClassDeclaration(trace, parentOfType2);
                }
            }
            return false;
        }

        public final void reportUnnecessarySafeCall(BindingTrace trace, KotlinType type, KtQualifiedExpression callElement, ASTNode callOperationNode, Receiver explicitReceiver, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(callElement, "callElement");
            Intrinsics.checkNotNullParameter(callOperationNode, "callOperationNode");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            if ((explicitReceiver instanceof ExpressionReceiver) && (((ExpressionReceiver) explicitReceiver).getExpression() instanceof KtSuperExpression)) {
                trace.report(Errors.UNEXPECTED_SAFE_CALL.on(callOperationNode.getPsi()));
            } else {
                if (KotlinTypeKt.isError(type)) {
                    return;
                }
                trace.report(Errors.UNNECESSARY_SAFE_CALL.on(callOperationNode.getPsi(), type));
                if (languageVersionSettings.supportsFeature(LanguageFeature.SafeCallsAreAlwaysNullable)) {
                    return;
                }
                trace.report(Errors.SAFE_CALL_WILL_CHANGE_NULLABILITY.on(callElement));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void checkNestedClassAccess(KtQualifiedExpression expression, ExpressionTypingContext context) {
            KtExpression selectorExpression = expression.getSelectorExpression();
            if (selectorExpression == null) {
                return;
            }
            Qualifier qualifier = (Qualifier) context.trace.get(BindingContext.QUALIFIER, expression);
            if (((Qualifier) context.trace.get(BindingContext.QUALIFIER, expression.getReceiverExpression())) != null || qualifier == null) {
                return;
            }
            boolean z = qualifier instanceof ClassifierQualifier;
            context.trace.report(Errors.NESTED_CLASS_ACCESSED_VIA_INSTANCE_REFERENCE.on(selectorExpression, ((ClassifierQualifier) qualifier).getDescriptor()));
        }

        private final boolean isUnderAnnotationClassDeclaration(BindingTrace trace, PsiElement parent) {
            KtClass ktClass = (KtClass) PsiTreeUtil.getParentOfType(parent, KtClass.class, true);
            if (ktClass != null) {
                return DescriptorUtils.isAnnotationClass((DeclarationDescriptor) trace.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktClass));
            }
            return false;
        }
    }
}
