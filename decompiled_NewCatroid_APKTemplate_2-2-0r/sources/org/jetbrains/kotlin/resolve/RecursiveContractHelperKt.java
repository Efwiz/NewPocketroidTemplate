package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.AbstractContractProvider;
import org.jetbrains.kotlin.contracts.description.ContractProviderKey;
import org.jetbrains.kotlin.contracts.description.LazyContractProvider;
import org.jetbrains.kotlin.contracts.parsing.PsiContractsUtilsKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;

/* JADX INFO: compiled from: RecursiveContractHelper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"disableContractsInsideContractsBlock", "", "call", "Lorg/jetbrains/kotlin/psi/Call;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RecursiveContractHelperKt {
    public static final void disableContractsInsideContractsBlock(Call call, CallableDescriptor callableDescriptor, LexicalScope scope, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(trace, "trace");
        KtElement callElement = call.getCallElement();
        KtExpression ktExpression = callElement instanceof KtExpression ? (KtExpression) callElement : null;
        if (ktExpression != null && KtPsiUtilKt.isFirstStatement(ktExpression) && KtPsiUtilKt.isContractDescriptionCallPsiCheck(ktExpression)) {
            if (callableDescriptor == null || !PsiContractsUtilsKt.isContractCallDescriptor(callableDescriptor)) {
                DeclarationDescriptor ownerDescriptor = scope.getOwnerDescriptor();
                FunctionDescriptor functionDescriptor = ownerDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) ownerDescriptor : null;
                AbstractContractProvider abstractContractProvider = functionDescriptor != null ? (AbstractContractProvider) functionDescriptor.getUserData(ContractProviderKey.INSTANCE) : null;
                LazyContractProvider lazyContractProvider = abstractContractProvider instanceof LazyContractProvider ? (LazyContractProvider) abstractContractProvider : null;
                if (lazyContractProvider != null) {
                    lazyContractProvider.setContractDescription(null);
                    return;
                }
                return;
            }
            trace.record(BindingContext.IS_CONTRACT_DECLARATION_BLOCK, ktExpression, true);
        }
    }
}
