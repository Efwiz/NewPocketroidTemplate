package org.jetbrains.kotlin.contracts.parsing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.contracts.description.BooleanExpression;
import org.jetbrains.kotlin.contracts.description.CallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.ContractDescription;
import org.jetbrains.kotlin.contracts.description.EffectDeclaration;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanVariableReference;
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.ContractDescriptionValue;
import org.jetbrains.kotlin.contracts.description.expressions.VariableReference;
import org.jetbrains.kotlin.contracts.parsing.effects.PsiCallsEffectParser;
import org.jetbrains.kotlin.contracts.parsing.effects.PsiConditionalEffectParser;
import org.jetbrains.kotlin.contracts.parsing.effects.PsiReturnsEffectParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: PsiContractParserDispatcher.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ$\u0010\u0011\u001a\u00020\u00122\u001a\u0010\u0013\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0016\u0012\u0004\u0012\u00020\u00170\u00150\u0014H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0017H\u0002J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0017J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0017J\b\u0010\u001f\u001a\u0004\u0018\u00010 J\u0012\u0010!\u001a\u0004\u0018\u00010\u00162\b\u0010\u001a\u001a\u0004\u0018\u00010\u0017J\u0012\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0017J\u0012\u0010$\u001a\u0004\u0018\u00010%2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/contracts/parsing/PsiContractParserDispatcher;", "", "collector", "Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;", "callContext", "Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "conditionParser", "Lorg/jetbrains/kotlin/contracts/parsing/PsiConditionParser;", "constantParser", "Lorg/jetbrains/kotlin/contracts/parsing/PsiConstantParser;", "effectsParsers", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/contracts/parsing/PsiEffectParser;", "checkDuplicatedCallsEffectsAndReport", "", "effects", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "isValidEffectDeclaration", "", "expression", "parseCondition", "Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "parseConstant", "Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "parseContract", "Lorg/jetbrains/kotlin/contracts/description/ContractDescription;", "parseEffect", "parseValue", "Lorg/jetbrains/kotlin/contracts/description/expressions/ContractDescriptionValue;", "parseVariable", "Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiContractParserDispatcher {
    private final ContractCallContext callContext;
    private final ContractParsingDiagnosticsCollector collector;
    private final PsiConditionParser conditionParser;
    private final PsiConstantParser constantParser;
    private final Map<Name, PsiEffectParser> effectsParsers;
    private final StorageManager storageManager;

    public PsiContractParserDispatcher(ContractParsingDiagnosticsCollector collector, ContractCallContext callContext, StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(callContext, "callContext");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.collector = collector;
        this.callContext = callContext;
        this.storageManager = storageManager;
        this.conditionParser = new PsiConditionParser(collector, callContext, this);
        this.constantParser = new PsiConstantParser(callContext);
        this.effectsParsers = MapsKt.mapOf(TuplesKt.to(ContractsDslNames.INSTANCE.getRETURNS_EFFECT(), new PsiReturnsEffectParser(collector, callContext, this)), TuplesKt.to(ContractsDslNames.INSTANCE.getRETURNS_NOT_NULL_EFFECT(), new PsiReturnsEffectParser(collector, callContext, this)), TuplesKt.to(ContractsDslNames.INSTANCE.getCALLS_IN_PLACE_EFFECT(), new PsiCallsEffectParser(collector, callContext, this)), TuplesKt.to(ContractsDslNames.INSTANCE.getCONDITIONAL_EFFECT(), new PsiConditionalEffectParser(collector, callContext, this)));
    }

    public final ContractDescription parseContract() {
        List<KtExpression> statements;
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(this.callContext.getContractCallExpression(), this.callContext.getBindingContext());
        if (resolvedCall == null) {
            return null;
        }
        KtExpression ktExpressionFirstArgumentAsExpressionOrNull = PsiContractsUtilsKt.firstArgumentAsExpressionOrNull(resolvedCall);
        if (!(ktExpressionFirstArgumentAsExpressionOrNull instanceof KtLambdaExpression)) {
            if (ktExpressionFirstArgumentAsExpressionOrNull == null) {
                ktExpressionFirstArgumentAsExpressionOrNull = this.callContext.getContractCallExpression();
            }
            this.collector.badDescription("first argument of 'contract'-call should be a lambda expression", ktExpressionFirstArgumentAsExpressionOrNull);
            return null;
        }
        KtBlockExpression bodyExpression = ((KtLambdaExpression) ktExpressionFirstArgumentAsExpressionOrNull).getBodyExpression();
        if (bodyExpression == null || (statements = bodyExpression.getStatements()) == null) {
            return null;
        }
        List<KtExpression> list = statements;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KtExpression ktExpression : list) {
            arrayList.add(TuplesKt.to(parseEffect(ktExpression), ktExpression));
        }
        ArrayList arrayList2 = arrayList;
        checkDuplicatedCallsEffectsAndReport(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            EffectDeclaration effectDeclaration = (EffectDeclaration) ((Pair) it.next()).getFirst();
            if (effectDeclaration != null) {
                arrayList3.add(effectDeclaration);
            }
        }
        ArrayList arrayList4 = arrayList3;
        if (arrayList4.isEmpty()) {
            return null;
        }
        return new ContractDescription(arrayList4, this.callContext.getFunctionDescriptor(), this.storageManager);
    }

    public final BooleanExpression parseCondition(KtExpression expression) {
        if (expression != null) {
            return (BooleanExpression) expression.accept(this.conditionParser, Unit.INSTANCE);
        }
        return null;
    }

    public final EffectDeclaration parseEffect(KtExpression expression) {
        KotlinType type;
        if (expression == null || !isValidEffectDeclaration(expression) || (type = CallUtilKt.getType(expression, this.callContext.getBindingContext())) == null) {
            return null;
        }
        Map<Name, PsiEffectParser> map = this.effectsParsers;
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        PsiEffectParser psiEffectParser = map.get(this$0 != null ? this$0.getName() : null);
        if (psiEffectParser == null) {
            this.collector.badDescription("unrecognized effect", expression);
            return null;
        }
        return psiEffectParser.tryParseEffect(expression);
    }

    private final void checkDuplicatedCallsEffectsAndReport(List<? extends Pair<? extends EffectDeclaration, ? extends KtExpression>> effects) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Pair<? extends EffectDeclaration, ? extends KtExpression> pair : effects) {
            EffectDeclaration effectDeclarationComponent1 = pair.component1();
            KtExpression ktExpressionComponent2 = pair.component2();
            if (effectDeclarationComponent1 instanceof CallsEffectDeclaration) {
                ParameterDescriptor descriptor = ((CallsEffectDeclaration) effectDeclarationComponent1).getVariableReference().getDescriptor();
                if (linkedHashSet.contains(descriptor)) {
                    this.collector.badDescription("Duplicated contract for " + descriptor.getName() + ". Only one `callsInPlace` contract per parameter is allowed.", ktExpressionComponent2);
                } else {
                    linkedHashSet.add(descriptor);
                }
            }
        }
    }

    private final boolean isValidEffectDeclaration(KtExpression expression) {
        CallableDescriptor resultingDescriptor;
        if (!(expression instanceof KtCallExpression) && !(expression instanceof KtBinaryExpression)) {
            this.collector.badDescription("unexpected construction in contract description", expression);
            return false;
        }
        KtExpression ktExpression = expression;
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktExpression, this.callContext.getBindingContext());
        if (resolvedCall == null || (resultingDescriptor = resolvedCall.getResultingDescriptor()) == null) {
            return false;
        }
        if (PsiContractsUtilsKt.isFromContractDsl(resultingDescriptor)) {
            return true;
        }
        this.collector.badDescription("effects can be produced only by direct calls to ContractsDSL", ktExpression);
        return false;
    }

    public final ConstantReference parseConstant(KtExpression expression) {
        if (expression == null) {
            return null;
        }
        return (ConstantReference) expression.accept(this.constantParser, Unit.INSTANCE);
    }

    public final VariableReference parseVariable(KtExpression expression) {
        if (expression == null) {
            return null;
        }
        KtExpression ktExpression = expression;
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktExpression, this.callContext.getBindingContext());
        CallableDescriptor resultingDescriptor = resolvedCall != null ? resolvedCall.getResultingDescriptor() : null;
        if (!(resultingDescriptor instanceof ParameterDescriptor)) {
            if (!(expression instanceof KtConstantExpression)) {
                this.collector.badDescription("only references to parameters are allowed in contract description", ktExpression);
            }
            return null;
        }
        if (resultingDescriptor instanceof ReceiverParameterDescriptor) {
            ClassifierDescriptor this$0 = ((ReceiverParameterDescriptor) resultingDescriptor).getType().getConstructor().getThis$0();
            if (this$0 != null && PsiContractsUtilsKt.isFromContractDsl(this$0)) {
                this.collector.badDescription("only references to parameters are allowed. Did you miss label on <this>?", ktExpression);
                return null;
            }
            FunctionDescriptor functionDescriptor = this.callContext.getFunctionDescriptor();
            ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter == null) {
                extensionReceiverParameter = functionDescriptor.mo6753getDispatchReceiverParameter();
            }
            if (!Intrinsics.areEqual(resultingDescriptor, extensionReceiverParameter)) {
                this.collector.badDescription("only references to direct <this> are allowed", ktExpression);
                return null;
            }
        }
        ParameterDescriptor parameterDescriptor = (ParameterDescriptor) resultingDescriptor;
        if (KotlinBuiltIns.isBoolean(parameterDescriptor.getType())) {
            return new BooleanVariableReference(parameterDescriptor);
        }
        return new VariableReference(parameterDescriptor);
    }

    public final ContractDescriptionValue parseValue(KtExpression expression) {
        VariableReference variable = parseVariable(expression);
        if (variable != null) {
            return variable;
        }
        return parseConstant(expression);
    }
}
