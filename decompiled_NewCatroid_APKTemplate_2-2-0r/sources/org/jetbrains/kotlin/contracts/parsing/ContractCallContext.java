package org.jetbrains.kotlin.contracts.parsing;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;

/* JADX INFO: compiled from: ContractParsingServices.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;", "", "contractCallExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getContractCallExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "getFunctionDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContractCallContext {
    private final BindingContext bindingContext;
    private final KtExpression contractCallExpression;
    private final FunctionDescriptor functionDescriptor;
    private final LanguageVersionSettings languageVersionSettings;
    private final BindingTrace trace;

    public ContractCallContext(KtExpression contractCallExpression, FunctionDescriptor functionDescriptor, BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(contractCallExpression, "contractCallExpression");
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.contractCallExpression = contractCallExpression;
        this.functionDescriptor = functionDescriptor;
        this.trace = trace;
        this.languageVersionSettings = languageVersionSettings;
        BindingContext bindingContext = trace.getBindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        this.bindingContext = bindingContext;
    }

    public final KtExpression getContractCallExpression() {
        return this.contractCallExpression;
    }

    public final FunctionDescriptor getFunctionDescriptor() {
        return this.functionDescriptor;
    }

    public final BindingTrace getTrace() {
        return this.trace;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }
}
