package org.jetbrains.kotlin.contracts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.contracts.description.AbstractContractProvider;
import org.jetbrains.kotlin.contracts.description.ContractDescription;
import org.jetbrains.kotlin.contracts.description.ContractProviderKey;
import org.jetbrains.kotlin.contracts.model.Computation;
import org.jetbrains.kotlin.contracts.model.ConditionalEffect;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESTypeSubstitution;
import org.jetbrains.kotlin.contracts.model.Functor;
import org.jetbrains.kotlin.contracts.model.functors.AndFunctor;
import org.jetbrains.kotlin.contracts.model.functors.EqualsFunctor;
import org.jetbrains.kotlin.contracts.model.functors.IsFunctor;
import org.jetbrains.kotlin.contracts.model.functors.NotFunctor;
import org.jetbrains.kotlin.contracts.model.functors.OrFunctor;
import org.jetbrains.kotlin.contracts.model.structure.CallComputation;
import org.jetbrains.kotlin.contracts.model.structure.ESBooleanType;
import org.jetbrains.kotlin.contracts.model.structure.ESConstants;
import org.jetbrains.kotlin.contracts.model.structure.ESReturns;
import org.jetbrains.kotlin.contracts.model.structure.ESType;
import org.jetbrains.kotlin.contracts.model.structure.TypesKt;
import org.jetbrains.kotlin.contracts.model.structure.UNKNOWN_COMPUTATION;
import org.jetbrains.kotlin.contracts.model.visitors.Reducer;
import org.jetbrains.kotlin.contracts.parsing.PsiContractsUtilsKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.inference.components.EmptySubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutorByConstructorMap;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.UnsignedErrorValueTypeConstant;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: EffectsExtractingVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B%\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0015J\u001d\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u001aJ\u001d\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010 J\u001d\u0010!\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\"J\u001f\u0010#\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020$2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010%J\u001d\u0010&\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020'2\u0006\u0010\u0019\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010(J\u001f\u0010)\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020*2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010+J\u001d\u0010,\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020-2\u0006\u0010\u0019\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010.J\u000e\u0010/\u001a\u0004\u0018\u000100*\u000201H\u0002J\f\u0010/\u001a\u000200*\u000202H\u0002J\u0018\u00103\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u000104*\u0006\u0012\u0002\b\u000305H\u0002J\u000e\u00106\u001a\u0004\u0018\u000107*\u000208H\u0002J\u0010\u00109\u001a\u00020:*\u0006\u0012\u0002\b\u000305H\u0002J\u0010\u0010;\u001a\u00020<*\u0006\u0012\u0002\b\u000305H\u0002J\u000e\u0010=\u001a\u0004\u0018\u00010\u0002*\u00020>H\u0002J\u000e\u0010=\u001a\u0004\u0018\u00010\u0002*\u00020?H\u0002J\f\u0010=\u001a\u00020\u0002*\u00020@H\u0002R\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/contracts/EffectsExtractingVisitor;", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "Lorg/jetbrains/kotlin/contracts/model/Computation;", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "reducer", "Lorg/jetbrains/kotlin/contracts/model/visitors/Reducer;", "extractOrGetCached", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "visitBinaryExpression", "expression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "data", "(Lorg/jetbrains/kotlin/psi/KtBinaryExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/Computation;", "visitConstantExpression", "Lorg/jetbrains/kotlin/psi/KtConstantExpression;", "(Lorg/jetbrains/kotlin/psi/KtConstantExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/Computation;", "visitIsExpression", "Lorg/jetbrains/kotlin/psi/KtIsExpression;", "(Lorg/jetbrains/kotlin/psi/KtIsExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/Computation;", "visitKtElement", "(Lorg/jetbrains/kotlin/psi/KtElement;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/Computation;", "visitLambdaExpression", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "(Lorg/jetbrains/kotlin/psi/KtLambdaExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/Computation;", "visitParenthesizedExpression", "Lorg/jetbrains/kotlin/psi/KtParenthesizedExpression;", "(Lorg/jetbrains/kotlin/psi/KtParenthesizedExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/Computation;", "visitSafeQualifiedExpression", "Lorg/jetbrains/kotlin/psi/KtSafeQualifiedExpression;", "(Lorg/jetbrains/kotlin/psi/KtSafeQualifiedExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/Computation;", "visitUnaryExpression", "Lorg/jetbrains/kotlin/psi/KtUnaryExpression;", "(Lorg/jetbrains/kotlin/psi/KtUnaryExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/Computation;", "createDataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExtensionReceiver;", "getCallArgumentsAsComputations", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getFunctor", "Lorg/jetbrains/kotlin/contracts/model/Functor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getTypeSubstitution", "Lorg/jetbrains/kotlin/contracts/model/ESTypeSubstitution;", "isCallWithUnsupportedReceiver", "", "toComputation", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EffectsExtractingVisitor extends KtVisitor<Computation, Unit> {
    private final DataFlowValueFactory dataFlowValueFactory;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor moduleDescriptor;
    private final Reducer reducer;
    private final BindingTrace trace;

    public EffectsExtractingVisitor(BindingTrace trace, ModuleDescriptor moduleDescriptor, DataFlowValueFactory dataFlowValueFactory, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.trace = trace;
        this.moduleDescriptor = moduleDescriptor;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.languageVersionSettings = languageVersionSettings;
        this.reducer = new Reducer(getBuiltIns());
    }

    private final KotlinBuiltIns getBuiltIns() {
        return this.moduleDescriptor.getBuiltIns();
    }

    public final Computation extractOrGetCached(KtElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        Computation computation = (Computation) this.trace.get(BindingContext.EXPRESSION_EFFECTS, element);
        if (computation != null) {
            return computation;
        }
        Computation computation2 = (Computation) element.accept(this, Unit.INSTANCE);
        this.trace.record(BindingContext.EXPRESSION_EFFECTS, element, computation2);
        return computation2;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Computation visitKtElement(KtElement element, Unit data) {
        List<Computation> callArgumentsAsComputations;
        List<ESEffect> listEmptyList;
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(element, context);
        if (resolvedCall != null && !isCallWithUnsupportedReceiver(resolvedCall) && (callArgumentsAsComputations = getCallArgumentsAsComputations(resolvedCall)) != null) {
            ESTypeSubstitution typeSubstitution = getTypeSubstitution(resolvedCall);
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            if (PsiContractsUtilsKt.isEqualsDescriptor(resultingDescriptor)) {
                return new CallComputation(ESBooleanType.INSTANCE, new EqualsFunctor(false).invokeWithArguments(callArgumentsAsComputations, typeSubstitution, this.reducer));
            }
            if (resultingDescriptor instanceof ValueDescriptor) {
                ValueDescriptor valueDescriptor = (ValueDescriptor) resultingDescriptor;
                DataFlowValue dataFlowValueCreateDataFlowValue = createDataFlowValue((KtExpression) element);
                return dataFlowValueCreateDataFlowValue == null ? UNKNOWN_COMPUTATION.INSTANCE : new ESVariableWithDataFlowValue(valueDescriptor, dataFlowValueCreateDataFlowValue);
            }
            if (resultingDescriptor instanceof FunctionDescriptor) {
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) resultingDescriptor;
                KotlinType returnType = functionDescriptor.getReturnType();
                ESType eSType = returnType != null ? TypesKt.toESType(returnType) : null;
                Functor functor = getFunctor(functionDescriptor);
                if (functor == null || (listEmptyList = functor.invokeWithArguments(callArgumentsAsComputations, typeSubstitution, this.reducer)) == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                return new CallComputation(eSType, listEmptyList);
            }
            return UNKNOWN_COMPUTATION.INSTANCE;
        }
        return UNKNOWN_COMPUTATION.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Computation visitLambdaExpression(KtLambdaExpression expression, Unit data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return UNKNOWN_COMPUTATION.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Computation visitParenthesizedExpression(KtParenthesizedExpression expression, Unit data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(expression);
        Computation computation = ktExpressionDeparenthesize != null ? (Computation) ktExpressionDeparenthesize.accept(this, data) : null;
        return computation == null ? UNKNOWN_COMPUTATION.INSTANCE : computation;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Computation visitConstantExpression(KtConstantExpression expression, Unit data) {
        CompileTimeConstant compileTimeConstant;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        KotlinType type = context.getType(expression);
        if (type != null && (compileTimeConstant = (CompileTimeConstant) context.get(BindingContext.COMPILE_TIME_VALUE, expression)) != null) {
            if (compileTimeConstant.isError() || (compileTimeConstant instanceof UnsignedErrorValueTypeConstant)) {
                return UNKNOWN_COMPUTATION.INSTANCE;
            }
            Object value = compileTimeConstant.getValue(type);
            if (value instanceof Boolean) {
                return ESConstants.INSTANCE.booleanValue(((Boolean) value).booleanValue());
            }
            if (value == null) {
                return ESConstants.INSTANCE.getNullValue();
            }
            return UNKNOWN_COMPUTATION.INSTANCE;
        }
        return UNKNOWN_COMPUTATION.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Computation visitIsExpression(KtIsExpression expression, Unit data) {
        ESType eSType;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        KotlinType kotlinType = (KotlinType) this.trace.get(BindingContext.TYPE, expression.getTypeReference());
        if (kotlinType == null || (eSType = TypesKt.toESType(kotlinType)) == null) {
            return UNKNOWN_COMPUTATION.INSTANCE;
        }
        KtExpression leftHandSide = expression.getLeftHandSide();
        Intrinsics.checkNotNullExpressionValue(leftHandSide, "getLeftHandSide(...)");
        return new CallComputation(ESBooleanType.INSTANCE, new IsFunctor(eSType, expression.isNegated()).invokeWithArguments(CollectionsKt.listOf(extractOrGetCached(leftHandSide)), ESTypeSubstitution.INSTANCE.empty(getBuiltIns()), this.reducer));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Computation visitSafeQualifiedExpression(KtSafeQualifiedExpression expression, Unit data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Computation computation = (Computation) super.visitSafeQualifiedExpression(expression, data);
        if (computation == UNKNOWN_COMPUTATION.INSTANCE) {
            Intrinsics.checkNotNull(computation);
            return computation;
        }
        List<ESEffect> effects = computation.getEffects();
        ArrayList arrayList = new ArrayList();
        for (Object obj : effects) {
            if (!visitSafeQualifiedExpression$containsReturnsNull((ESEffect) obj)) {
                arrayList.add(obj);
            }
        }
        return new CallComputation(computation.getType(), arrayList);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Computation visitBinaryExpression(KtBinaryExpression expression, Unit data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        KtExpression left = expression.getLeft();
        if (left == null) {
            return UNKNOWN_COMPUTATION.INSTANCE;
        }
        Computation computationExtractOrGetCached = extractOrGetCached(left);
        KtExpression right = expression.getRight();
        if (right == null) {
            return UNKNOWN_COMPUTATION.INSTANCE;
        }
        List<? extends Computation> listListOf = CollectionsKt.listOf((Object[]) new Computation[]{computationExtractOrGetCached, extractOrGetCached(right)});
        IElementType operationToken = expression.getOperationToken();
        if (Intrinsics.areEqual(operationToken, KtTokens.EXCLEQ)) {
            return new CallComputation(ESBooleanType.INSTANCE, new EqualsFunctor(true).invokeWithArguments(listListOf, ESTypeSubstitution.INSTANCE.empty(getBuiltIns()), this.reducer));
        }
        if (Intrinsics.areEqual(operationToken, KtTokens.EQEQ)) {
            return new CallComputation(ESBooleanType.INSTANCE, new EqualsFunctor(false).invokeWithArguments(listListOf, ESTypeSubstitution.INSTANCE.empty(getBuiltIns()), this.reducer));
        }
        if (Intrinsics.areEqual(operationToken, KtTokens.ANDAND)) {
            return new CallComputation(ESBooleanType.INSTANCE, new AndFunctor().invokeWithArguments(listListOf, ESTypeSubstitution.INSTANCE.empty(getBuiltIns()), this.reducer));
        }
        if (Intrinsics.areEqual(operationToken, KtTokens.OROR)) {
            return new CallComputation(ESBooleanType.INSTANCE, new OrFunctor().invokeWithArguments(listListOf, ESTypeSubstitution.INSTANCE.empty(getBuiltIns()), this.reducer));
        }
        return UNKNOWN_COMPUTATION.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Computation visitUnaryExpression(KtUnaryExpression expression, Unit data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        KtExpression baseExpression = expression.getBaseExpression();
        if (baseExpression == null) {
            return UNKNOWN_COMPUTATION.INSTANCE;
        }
        return Intrinsics.areEqual(expression.getOperationToken(), KtTokens.EXCL) ? new CallComputation(ESBooleanType.INSTANCE, new NotFunctor().invokeWithArguments(extractOrGetCached(baseExpression))) : UNKNOWN_COMPUTATION.INSTANCE;
    }

    private final Computation toComputation(ReceiverValue receiverValue) {
        if (receiverValue instanceof ExpressionReceiver) {
            return extractOrGetCached(((ExpressionReceiver) receiverValue).getExpression());
        }
        if (receiverValue instanceof ExtensionReceiver) {
            if (this.languageVersionSettings.supportsFeature(LanguageFeature.ContractsOnCallsWithImplicitReceiver)) {
                return new ESReceiverWithDataFlowValue(receiverValue, createDataFlowValue((ExtensionReceiver) receiverValue));
            }
            return UNKNOWN_COMPUTATION.INSTANCE;
        }
        return UNKNOWN_COMPUTATION.INSTANCE;
    }

    private final DataFlowValue createDataFlowValue(ExtensionReceiver extensionReceiver) {
        DataFlowValueFactory dataFlowValueFactory = this.dataFlowValueFactory;
        ExtensionReceiver extensionReceiver2 = extensionReceiver;
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        CallableDescriptor declarationDescriptor = extensionReceiver.getDeclarationDescriptor();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "<get-declarationDescriptor>(...)");
        return dataFlowValueFactory.createDataFlowValue(extensionReceiver2, context, declarationDescriptor);
    }

    private final DataFlowValue createDataFlowValue(KtExpression ktExpression) {
        DataFlowValueFactory dataFlowValueFactory = this.dataFlowValueFactory;
        KotlinType type = this.trace.getType(ktExpression);
        if (type == null) {
            return null;
        }
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        return dataFlowValueFactory.createDataFlowValue(ktExpression, type, context, this.moduleDescriptor);
    }

    private final Functor getFunctor(FunctionDescriptor functionDescriptor) {
        ContractDescription contractDescription;
        AbstractContractProvider abstractContractProvider = (AbstractContractProvider) functionDescriptor.getUserData(ContractProviderKey.INSTANCE);
        if (abstractContractProvider == null || (contractDescription = abstractContractProvider.getContractDescription()) == null) {
            return null;
        }
        return contractDescription.getFunctor(this.moduleDescriptor);
    }

    private final boolean isCallWithUnsupportedReceiver(ResolvedCall<?> resolvedCall) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall2;
        KtExpression expression;
        KtExpression expression2;
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        ResolvedCall<? extends CallableDescriptor> resolvedCall3 = null;
        ExpressionReceiver expressionReceiver = extensionReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) extensionReceiver : null;
        if (expressionReceiver == null || (expression2 = expressionReceiver.getExpression()) == null) {
            resolvedCall2 = null;
        } else {
            BindingContext context = this.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            resolvedCall2 = CallUtilKt.getResolvedCall(expression2, context);
        }
        if (!Intrinsics.areEqual(resolvedCall2, resolvedCall)) {
            ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
            ExpressionReceiver expressionReceiver2 = dispatchReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) dispatchReceiver : null;
            if (expressionReceiver2 != null && (expression = expressionReceiver2.getExpression()) != null) {
                BindingContext context2 = this.trace.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                resolvedCall3 = CallUtilKt.getResolvedCall(expression, context2);
            }
            if (!Intrinsics.areEqual(resolvedCall3, resolvedCall) && resolvedCall.getExplicitReceiverKind() != ExplicitReceiverKind.BOTH_RECEIVERS) {
                return false;
            }
        }
        return true;
    }

    private final List<Computation> getCallArgumentsAsComputations(ResolvedCall<?> resolvedCall) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = arrayList;
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, extensionReceiver != null ? toComputation(extensionReceiver) : null);
        ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, dispatchReceiver != null ? toComputation(dispatchReceiver) : null);
        List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex();
        if (valueArgumentsByIndex == null) {
            return null;
        }
        for (ResolvedValueArgument resolvedValueArgument : valueArgumentsByIndex) {
            Intrinsics.checkNotNull(resolvedValueArgument);
            Computation computation = toComputation(resolvedValueArgument);
            if (computation == null) {
                return null;
            }
            arrayList2.add(computation);
        }
        return arrayList;
    }

    private final ESTypeSubstitution getTypeSubstitution(ResolvedCall<?> resolvedCall) {
        EmptySubstitutor newTypeSubstitutorByConstructorMap;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Map<TypeParameterDescriptor, KotlinType> typeArguments = resolvedCall.getTypeArguments();
        Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
        for (Map.Entry<TypeParameterDescriptor, KotlinType> entry : typeArguments.entrySet()) {
            TypeParameterDescriptor key = entry.getKey();
            KotlinType value = entry.getValue();
            TypeConstructor typeConstructor = key.getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
            linkedHashMap.put(typeConstructor, value.unwrap());
        }
        if (!linkedHashMap.isEmpty()) {
            newTypeSubstitutorByConstructorMap = new NewTypeSubstitutorByConstructorMap(linkedHashMap);
        } else {
            newTypeSubstitutorByConstructorMap = EmptySubstitutor.INSTANCE;
        }
        return new ESTypeSubstitution(newTypeSubstitutorByConstructorMap, getBuiltIns());
    }

    private final Computation toComputation(ResolvedValueArgument resolvedValueArgument) {
        if (resolvedValueArgument instanceof DefaultValueArgument) {
            return UNKNOWN_COMPUTATION.INSTANCE;
        }
        if (!(resolvedValueArgument instanceof VarargValueArgument)) {
            if (!(resolvedValueArgument instanceof ExpressionValueArgument)) {
                throw new IllegalStateException("Unexpected ResolvedValueArgument " + resolvedValueArgument);
            }
            ValueArgument valueArgument = ((ExpressionValueArgument) resolvedValueArgument).getValueArgument();
            if (valueArgument != null) {
                return toComputation(valueArgument);
            }
        }
        return null;
    }

    private final Computation toComputation(ValueArgument valueArgument) {
        KtExpression argumentExpression;
        if (valueArgument instanceof KtLambdaArgument) {
            KtLambdaExpression lambdaExpression = ((KtLambdaArgument) valueArgument).getLambdaExpression();
            return lambdaExpression != null ? new ESLambda(lambdaExpression) : null;
        }
        if (!(valueArgument instanceof KtValueArgument) || (argumentExpression = valueArgument.getArgumentExpression()) == null) {
            return null;
        }
        return argumentExpression instanceof KtLambdaExpression ? new ESLambda((KtLambdaExpression) argumentExpression) : extractOrGetCached(argumentExpression);
    }

    private static final boolean visitSafeQualifiedExpression$containsReturnsNull(ESEffect eSEffect) {
        return ((eSEffect instanceof ESReturns) && Intrinsics.areEqual(((ESReturns) eSEffect).getValue(), ESConstants.INSTANCE.getNullValue())) || ((eSEffect instanceof ConditionalEffect) && visitSafeQualifiedExpression$containsReturnsNull(((ConditionalEffect) eSEffect).getSimpleEffect()));
    }
}
