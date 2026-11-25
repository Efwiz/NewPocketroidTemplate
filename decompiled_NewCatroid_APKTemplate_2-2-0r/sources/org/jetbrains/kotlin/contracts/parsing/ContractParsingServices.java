package org.jetbrains.kotlin.contracts.parsing;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.contracts.description.ContractDescription;
import org.jetbrains.kotlin.contracts.description.ContractProviderKey;
import org.jetbrains.kotlin.contracts.description.LazyContractProvider;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: ContractParsingServices.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\f\u0010\u0018\u001a\u00020\u0019*\u00020\u0017H\u0002J\u0014\u0010\u0018\u001a\u00020\u0019*\u00020\f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingServices;", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkContractAndRecordIfPresent", "", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "checkFeatureEnabled", "collector", "Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;", "parseContractAndReportErrors", "Lorg/jetbrains/kotlin/contracts/description/ContractDescription;", "callContext", "Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;", "isContractDescriptionCallPreciseCheck", "", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContractParsingServices {
    private final LanguageVersionSettings languageVersionSettings;
    private final StorageManager storageManager;

    public ContractParsingServices(LanguageVersionSettings languageVersionSettings, StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.languageVersionSettings = languageVersionSettings;
        this.storageManager = storageManager;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final void checkContractAndRecordIfPresent(KtExpression expression, BindingTrace trace, FunctionDescriptor ownerDescriptor) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        if (KtPsiUtilKt.isContractDescriptionCallPsiCheck(expression)) {
            ContractCallContext contractCallContext = new ContractCallContext(expression, ownerDescriptor, trace, this.languageVersionSettings);
            Object userData = ownerDescriptor.getUserData(ContractProviderKey.INSTANCE);
            LazyContractProvider lazyContractProvider = userData instanceof LazyContractProvider ? (LazyContractProvider) userData : null;
            try {
                if (!isContractDescriptionCallPreciseCheck(contractCallContext)) {
                    if (lazyContractProvider != null) {
                        lazyContractProvider.setContractDescription(null);
                    }
                } else {
                    ContractDescription contractAndReportErrors = parseContractAndReportErrors(contractCallContext);
                    if (lazyContractProvider != null) {
                        lazyContractProvider.setContractDescription(contractAndReportErrors);
                    }
                }
            } catch (Throwable th) {
                if (lazyContractProvider != null) {
                    lazyContractProvider.setContractDescription(null);
                }
                throw th;
            }
        }
    }

    private final boolean isContractDescriptionCallPreciseCheck(ContractCallContext contractCallContext) {
        return isContractDescriptionCallPreciseCheck(contractCallContext.getContractCallExpression(), contractCallContext.getBindingContext());
    }

    private final ContractDescription parseContractAndReportErrors(ContractCallContext callContext) {
        TraceBasedCollector traceBasedCollector = new TraceBasedCollector(callContext);
        try {
            checkFeatureEnabled(traceBasedCollector);
            boolean zAreEqual = Intrinsics.areEqual(callContext.getBindingContext().get(BindingContext.CONTRACT_NOT_ALLOWED, callContext.getContractCallExpression()), (Object) true);
            ContractDescription contractDescription = null;
            if (!traceBasedCollector.hasErrors() && !zAreEqual) {
                ContractDescription contract = new PsiContractParserDispatcher(traceBasedCollector, callContext, this.storageManager).parseContract();
                if (contract == null) {
                    traceBasedCollector.addFallbackErrorIfNecessary();
                }
                if (contract != null && !traceBasedCollector.hasErrors()) {
                    contractDescription = contract;
                }
                return contractDescription;
            }
            return null;
        } finally {
            traceBasedCollector.flushDiagnostics();
        }
    }

    private final void checkFeatureEnabled(ContractParsingDiagnosticsCollector collector) {
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.AllowContractsForCustomFunctions)) {
            return;
        }
        collector.unsupportedFeature(this.languageVersionSettings);
    }

    private final boolean isContractDescriptionCallPreciseCheck(KtExpression ktExpression, BindingContext bindingContext) {
        CallableDescriptor resultingDescriptor;
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktExpression, bindingContext);
        if (resolvedCall == null || (resultingDescriptor = resolvedCall.getResultingDescriptor()) == null) {
            return false;
        }
        return PsiContractsUtilsKt.isContractCallDescriptor(resultingDescriptor);
    }
}
