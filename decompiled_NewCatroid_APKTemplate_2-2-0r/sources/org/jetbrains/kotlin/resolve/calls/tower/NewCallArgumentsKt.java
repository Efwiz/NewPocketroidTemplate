package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallContainer;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.QualifierReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.Qualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiverKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e\u001aR\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0000\u001a\"\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010!\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001aH\u0000\u001aB\u0010\"\u001a\u0004\u0018\u00010\u00012\u0006\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u00182\u0006\u0010\r\u001a\u00020\u000e2\b\u0010&\u001a\u0004\u0018\u00010'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+\u001a-\u0010,\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010.0-2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010/\u001a\u0002002\u0006\u0010*\u001a\u00020+H\u0002¢\u0006\u0002\u00101\u001a/\u00102\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010.\u0018\u00010-2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010/\u001a\u0002002\u0006\u0010*\u001a\u00020+H\u0002¢\u0006\u0002\u00101\u001a%\u00103\u001a\u00020.2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u00104\u001a\u0002052\u0006\u0010*\u001a\u00020+H\u0001¢\u0006\u0002\b6\u001a$\u00103\u001a\u0004\u0018\u00010.2\u0006\u0010\u000b\u001a\u00020\f2\b\u00104\u001a\u0004\u0018\u0001052\u0006\u0010*\u001a\u00020+H\u0000\u001a\u0014\u00107\u001a\u00020\n*\u00020\u00022\u0006\u00108\u001a\u00020\u0018H\u0000\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u00069"}, d2 = {"psiCallArgument", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "getPsiCallArgument", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;)Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallArgument;", "psiExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getPsiExpression", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;)Lorg/jetbrains/kotlin/psi/KtExpression;", "checkNoSpread", "", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "createSimplePSICallArgument", "Lorg/jetbrains/kotlin/resolve/calls/tower/SimplePSIKotlinCallArgument;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "statementFilter", "Lorg/jetbrains/kotlin/resolve/StatementFilter;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "dataFlowInfoBeforeThisArgument", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "typeInfoForArgument", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "contextForArgument", "processFunctionalExpression", "outerCallContext", "argumentExpression", "startDataFlowInfo", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "typeResolver", "Lorg/jetbrains/kotlin/resolve/TypeResolver;", "resolveContextReceiversTypes", "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "ktFunction", "Lorg/jetbrains/kotlin/psi/KtFunction;", "(Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/psi/KtFunction;Lorg/jetbrains/kotlin/resolve/TypeResolver;)[Lorg/jetbrains/kotlin/types/UnwrappedType;", "resolveParametersTypes", "resolveType", "typeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "resolveTypeWithGivenTypeReference", "setResultDataFlowInfoIfRelevant", "resultDataFlowInfo", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NewCallArgumentsKt {
    public static final PSIKotlinCallArgument getPsiCallArgument(KotlinCallArgument kotlinCallArgument) {
        Intrinsics.checkNotNullParameter(kotlinCallArgument, "<this>");
        boolean z = kotlinCallArgument instanceof PSIKotlinCallArgument;
        return (PSIKotlinCallArgument) kotlinCallArgument;
    }

    public static final KtExpression getPsiExpression(KotlinCallArgument kotlinCallArgument) {
        Intrinsics.checkNotNullParameter(kotlinCallArgument, "<this>");
        if (kotlinCallArgument instanceof ReceiverExpressionKotlinCallArgument) {
            ReceiverValue receiverValue = ((ReceiverExpressionKotlinCallArgument) kotlinCallArgument).getReceiver().getReceiverValue();
            ExpressionReceiver expressionReceiver = receiverValue instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValue : null;
            if (expressionReceiver != null) {
                return expressionReceiver.getExpression();
            }
            return null;
        }
        if (!(kotlinCallArgument instanceof QualifierReceiverKotlinCallArgument)) {
            return kotlinCallArgument instanceof EmptyLabeledReturn ? ((EmptyLabeledReturn) kotlinCallArgument).getReturnExpression() : getPsiCallArgument(kotlinCallArgument).getValueArgument().getArgumentExpression();
        }
        QualifierReceiver receiver = ((QualifierReceiverKotlinCallArgument) kotlinCallArgument).getReceiver();
        Qualifier qualifier = receiver instanceof Qualifier ? (Qualifier) receiver : null;
        if (qualifier != null) {
            return QualifierKt.getExpression(qualifier);
        }
        return null;
    }

    public static final void setResultDataFlowInfoIfRelevant(KotlinCallArgument kotlinCallArgument, DataFlowInfo resultDataFlowInfo) {
        Intrinsics.checkNotNullParameter(kotlinCallArgument, "<this>");
        Intrinsics.checkNotNullParameter(resultDataFlowInfo, "resultDataFlowInfo");
        if (kotlinCallArgument instanceof PSIFunctionKotlinCallArgument) {
            ((PSIFunctionKotlinCallArgument) kotlinCallArgument).setLambdaInitialDataFlowInfo(resultDataFlowInfo);
        }
    }

    public static final PSIKotlinCallArgument processFunctionalExpression(BasicCallResolutionContext outerCallContext, KtExpression argumentExpression, DataFlowInfo startDataFlowInfo, ValueArgument valueArgument, Name name2, KotlinBuiltIns builtIns, TypeResolver typeResolver) {
        KtFunction ktFunction;
        UnwrappedType unitType;
        FunctionExpressionImpl functionExpressionImpl;
        Intrinsics.checkNotNullParameter(outerCallContext, "outerCallContext");
        Intrinsics.checkNotNullParameter(argumentExpression, "argumentExpression");
        Intrinsics.checkNotNullParameter(startDataFlowInfo, "startDataFlowInfo");
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
        KtFunction functionLiteralArgumentIfAny = ArgumentTypeResolver.getFunctionLiteralArgumentIfAny(argumentExpression, outerCallContext);
        if (functionLiteralArgumentIfAny == null) {
            return null;
        }
        if (!(functionLiteralArgumentIfAny instanceof KtFunctionLiteral)) {
            ktFunction = functionLiteralArgumentIfAny;
        } else {
            ktFunction = (KtExpression) PsiTreeUtil.getParentOfType(functionLiteralArgumentIfAny, KtLambdaExpression.class, true);
        }
        if (ktFunction instanceof KtLambdaExpression) {
            KtLambdaExpression ktLambdaExpression = (KtLambdaExpression) ktFunction;
            KtFunctionLiteral functionLiteral = ktLambdaExpression.getFunctionLiteral();
            Intrinsics.checkNotNullExpressionValue(functionLiteral, "getFunctionLiteral(...)");
            functionExpressionImpl = new LambdaKotlinCallArgumentImpl(outerCallContext, valueArgument, startDataFlowInfo, name2, ktLambdaExpression, argumentExpression, resolveParametersTypes(outerCallContext, functionLiteral, typeResolver));
        } else {
            if (!(ktFunction instanceof KtNamedFunction) || !KtPsiUtilKt.isFunctionalExpression(ktFunction)) {
                return null;
            }
            KtNamedFunction ktNamedFunction = (KtNamedFunction) ktFunction;
            UnwrappedType unwrappedTypeResolveType = resolveType(outerCallContext, ktNamedFunction.mo7393getReceiverTypeReference(), typeResolver);
            KtFunction ktFunction2 = (KtFunction) ktFunction;
            UnwrappedType[] unwrappedTypeArrResolveContextReceiversTypes = resolveContextReceiversTypes(outerCallContext, ktFunction2, typeResolver);
            UnwrappedType[] unwrappedTypeArrResolveParametersTypes = resolveParametersTypes(outerCallContext, ktFunction2, typeResolver);
            if (unwrappedTypeArrResolveParametersTypes == null) {
                unwrappedTypeArrResolveParametersTypes = new UnwrappedType[0];
            }
            UnwrappedType[] unwrappedTypeArr = unwrappedTypeArrResolveParametersTypes;
            UnwrappedType unwrappedTypeResolveType2 = resolveType(outerCallContext, ktNamedFunction.mo7396getTypeReference(), typeResolver);
            if (unwrappedTypeResolveType2 == null) {
                unitType = ktNamedFunction.hasBlockBody() ? builtIns.getUnitType() : null;
            } else {
                unitType = unwrappedTypeResolveType2;
            }
            functionExpressionImpl = new FunctionExpressionImpl(outerCallContext, valueArgument, startDataFlowInfo, name2, argumentExpression, ktNamedFunction, unwrappedTypeResolveType, unwrappedTypeArrResolveContextReceiversTypes, unwrappedTypeArr, unitType);
        }
        checkNoSpread(outerCallContext, valueArgument);
        return functionExpressionImpl;
    }

    public static final void checkNoSpread(BasicCallResolutionContext context, ValueArgument valueArgument) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        LeafPsiElement spreadElement = valueArgument.getSpreadElement();
        if (spreadElement != null) {
            context.trace.report(Errors.SPREAD_OF_LAMBDA_OR_CALLABLE_REFERENCE.on(spreadElement));
        }
    }

    private static final UnwrappedType[] resolveParametersTypes(BasicCallResolutionContext basicCallResolutionContext, KtFunction ktFunction, TypeResolver typeResolver) {
        KtTypeReference ktTypeReferenceMo7396getTypeReference;
        KtParameterList valueParameterList = ktFunction.getValueParameterList();
        if (valueParameterList == null) {
            return null;
        }
        int size = valueParameterList.getParameters().size();
        UnwrappedType[] unwrappedTypeArr = new UnwrappedType[size];
        for (int i = 0; i < size; i++) {
            KtParameter ktParameter = valueParameterList.getParameters().get(i);
            unwrappedTypeArr[i] = (ktParameter == null || (ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference()) == null) ? null : resolveTypeWithGivenTypeReference(basicCallResolutionContext, ktTypeReferenceMo7396getTypeReference, typeResolver);
        }
        return unwrappedTypeArr;
    }

    private static final UnwrappedType[] resolveContextReceiversTypes(BasicCallResolutionContext basicCallResolutionContext, KtFunction ktFunction, TypeResolver typeResolver) {
        KtTypeReference ktTypeReferenceTypeReference;
        List<KtContextReceiver> contextReceivers = ktFunction.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        int size = contextReceivers.size();
        UnwrappedType[] unwrappedTypeArr = new UnwrappedType[size];
        for (int i = 0; i < size; i++) {
            KtContextReceiver ktContextReceiver = contextReceivers.get(i);
            unwrappedTypeArr[i] = (ktContextReceiver == null || (ktTypeReferenceTypeReference = ktContextReceiver.typeReference()) == null) ? null : resolveTypeWithGivenTypeReference(basicCallResolutionContext, ktTypeReferenceTypeReference, typeResolver);
        }
        return unwrappedTypeArr;
    }

    public static final UnwrappedType resolveTypeWithGivenTypeReference(BasicCallResolutionContext context, KtTypeReference typeReference, TypeResolver typeResolver) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(typeReference, "typeReference");
        Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
        LexicalScope scope = context.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        KotlinType kotlinTypeResolveType = typeResolver.resolveType(scope, typeReference, trace, true);
        ForceResolveUtil.forceResolveAllContents(kotlinTypeResolveType);
        return kotlinTypeResolveType.unwrap();
    }

    public static final UnwrappedType resolveType(BasicCallResolutionContext context, KtTypeReference ktTypeReference, TypeResolver typeResolver) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
        if (ktTypeReference == null) {
            return null;
        }
        return resolveTypeWithGivenTypeReference(context, ktTypeReference, typeResolver);
    }

    public static final SimplePSIKotlinCallArgument createSimplePSICallArgument(BasicCallResolutionContext contextForArgument, ValueArgument valueArgument, KotlinTypeInfo typeInfoForArgument) {
        Intrinsics.checkNotNullParameter(contextForArgument, "contextForArgument");
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(typeInfoForArgument, "typeInfoForArgument");
        BindingContext context = contextForArgument.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        StatementFilter statementFilter = contextForArgument.statementFilter;
        Intrinsics.checkNotNullExpressionValue(statementFilter, "statementFilter");
        DeclarationDescriptor ownerDescriptor = contextForArgument.scope.getOwnerDescriptor();
        DataFlowInfo dataFlowInfo = contextForArgument.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        LanguageVersionSettings languageVersionSettings = contextForArgument.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        DataFlowValueFactory dataFlowValueFactory = contextForArgument.dataFlowValueFactory;
        Intrinsics.checkNotNullExpressionValue(dataFlowValueFactory, "dataFlowValueFactory");
        Call call = contextForArgument.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        return createSimplePSICallArgument(context, statementFilter, ownerDescriptor, valueArgument, dataFlowInfo, typeInfoForArgument, languageVersionSettings, dataFlowValueFactory, call);
    }

    public static final SimplePSIKotlinCallArgument createSimplePSICallArgument(BindingContext bindingContext, StatementFilter statementFilter, DeclarationDescriptor ownerDescriptor, ValueArgument valueArgument, DataFlowInfo dataFlowInfoBeforeThisArgument, KotlinTypeInfo typeInfoForArgument, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, Call call) {
        KtSimpleNameExpression ktSimpleNameExpression;
        UnwrappedType freshReturnType;
        ResolvedCallAtom resultCallAtom;
        ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo;
        PartialCallContainer partialCallContainer;
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(statementFilter, "statementFilter");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoBeforeThisArgument, "dataFlowInfoBeforeThisArgument");
        Intrinsics.checkNotNullParameter(typeInfoForArgument, "typeInfoForArgument");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(call, "call");
        KtExpression lastElementDeparenthesized = KtPsiUtil.getLastElementDeparenthesized(valueArgument.getArgumentExpression(), statementFilter);
        if (lastElementDeparenthesized == null) {
            return null;
        }
        if (lastElementDeparenthesized instanceof KtCallableReferenceExpression) {
            KtSimpleNameExpression callableReference = ((KtCallableReferenceExpression) lastElementDeparenthesized).getCallableReference();
            Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
            ktSimpleNameExpression = callableReference;
        } else {
            ktSimpleNameExpression = lastElementDeparenthesized;
        }
        Call call2 = CallUtilKt.getCall(ktSimpleNameExpression, bindingContext);
        PartialCallResolutionResult result = (call2 == null || (partialCallContainer = (PartialCallContainer) bindingContext.get(BindingContext.ONLY_RESOLVED_CALL, call2)) == null) ? null : partialCallContainer.getResult();
        KotlinType type = typeInfoForArgument.getType();
        if (type == null || (freshReturnType = type.unwrap()) == null) {
            freshReturnType = (result == null || (resultCallAtom = result.getResultCallAtom()) == null) ? null : ResolutionAtomsKt.getFreshReturnType(resultCallAtom);
            if (freshReturnType == null) {
                return null;
            }
        }
        ExpressionReceiver expressionReceiverCreate = ExpressionReceiver.INSTANCE.create(lastElementDeparenthesized, freshReturnType, bindingContext);
        if ((lastElementDeparenthesized instanceof KtCallExpression) || result != null) {
            receiverValueWithSmartCastInfo = new ReceiverValueWithSmartCastInfo(expressionReceiverCreate, SetsKt.emptySet(), true, null, 8, null);
        } else {
            receiverValueWithSmartCastInfo = NewResolutionOldInferenceKt.transformToReceiverWithSmartCastInfo(ownerDescriptor, bindingContext, call.getCallType() == Call.CallType.CONTAINS ? dataFlowInfoBeforeThisArgument : typeInfoForArgument.getDataFlowInfo(), expressionReceiverCreate, languageVersionSettings, dataFlowValueFactory);
        }
        ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfoPrepareReceiverRegardingCaptureTypes = QualifierReceiverKt.prepareReceiverRegardingCaptureTypes(receiverValueWithSmartCastInfo);
        if (result != null) {
            return new SubKotlinCallArgumentImpl(valueArgument, dataFlowInfoBeforeThisArgument, typeInfoForArgument.getDataFlowInfo(), receiverValueWithSmartCastInfoPrepareReceiverRegardingCaptureTypes, result);
        }
        return new ExpressionKotlinCallArgumentImpl(valueArgument, dataFlowInfoBeforeThisArgument, typeInfoForArgument.getDataFlowInfo(), receiverValueWithSmartCastInfoPrepareReceiverRegardingCaptureTypes);
    }
}
