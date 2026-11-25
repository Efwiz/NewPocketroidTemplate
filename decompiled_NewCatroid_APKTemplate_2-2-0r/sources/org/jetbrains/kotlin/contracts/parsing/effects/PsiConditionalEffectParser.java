package org.jetbrains.kotlin.contracts.parsing.effects;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.BooleanExpression;
import org.jetbrains.kotlin.contracts.description.ConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.EffectDeclaration;
import org.jetbrains.kotlin.contracts.parsing.AbstractPsiEffectParser;
import org.jetbrains.kotlin.contracts.parsing.ContractCallContext;
import org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector;
import org.jetbrains.kotlin.contracts.parsing.PsiContractParserDispatcher;
import org.jetbrains.kotlin.contracts.parsing.PsiContractsUtilsKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: PsiConditionalEffectParser.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/contracts/parsing/effects/PsiConditionalEffectParser;", "Lorg/jetbrains/kotlin/contracts/parsing/AbstractPsiEffectParser;", "collector", "Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;", "callContext", "Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;", "dispatcher", "Lorg/jetbrains/kotlin/contracts/parsing/PsiContractParserDispatcher;", "(Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;Lorg/jetbrains/kotlin/contracts/parsing/PsiContractParserDispatcher;)V", "tryParseEffect", "Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiConditionalEffectParser extends AbstractPsiEffectParser {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiConditionalEffectParser(ContractParsingDiagnosticsCollector collector, ContractCallContext callContext, PsiContractParserDispatcher dispatcher) {
        super(collector, callContext, dispatcher);
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(callContext, "callContext");
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
    }

    @Override // org.jetbrains.kotlin.contracts.parsing.PsiEffectParser
    public EffectDeclaration tryParseEffect(KtExpression expression) {
        BooleanExpression condition;
        Intrinsics.checkNotNullParameter(expression, "expression");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(expression, getCallContext().getBindingContext());
        if (resolvedCall == null || !PsiContractsUtilsKt.isImpliesCallDescriptor(resolvedCall.getResultingDescriptor())) {
            return null;
        }
        PsiContractParserDispatcher contractParserDispatcher = getContractParserDispatcher();
        ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
        ExpressionReceiver expressionReceiver = receiverValueMo7304getDispatchReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValueMo7304getDispatchReceiver : null;
        EffectDeclaration effect = contractParserDispatcher.parseEffect(expressionReceiver != null ? expressionReceiver.getExpression() : null);
        if (effect == null || (condition = getContractParserDispatcher().parseCondition(PsiContractsUtilsKt.firstArgumentAsExpressionOrNull(resolvedCall))) == null) {
            return null;
        }
        return new ConditionalEffectDeclaration(effect, condition);
    }
}
