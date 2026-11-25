package org.jetbrains.kotlin.contracts.parsing;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PsiEffectParser.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b \u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/contracts/parsing/AbstractPsiEffectParser;", "Lorg/jetbrains/kotlin/contracts/parsing/PsiEffectParser;", "collector", "Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;", "callContext", "Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;", "contractParserDispatcher", "Lorg/jetbrains/kotlin/contracts/parsing/PsiContractParserDispatcher;", "(Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;Lorg/jetbrains/kotlin/contracts/parsing/PsiContractParserDispatcher;)V", "getCallContext", "()Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;", "getCollector", "()Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;", "getContractParserDispatcher", "()Lorg/jetbrains/kotlin/contracts/parsing/PsiContractParserDispatcher;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractPsiEffectParser implements PsiEffectParser {
    private final ContractCallContext callContext;
    private final ContractParsingDiagnosticsCollector collector;
    private final PsiContractParserDispatcher contractParserDispatcher;

    public AbstractPsiEffectParser(ContractParsingDiagnosticsCollector collector, ContractCallContext callContext, PsiContractParserDispatcher contractParserDispatcher) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(callContext, "callContext");
        Intrinsics.checkNotNullParameter(contractParserDispatcher, "contractParserDispatcher");
        this.collector = collector;
        this.callContext = callContext;
        this.contractParserDispatcher = contractParserDispatcher;
    }

    public final ContractParsingDiagnosticsCollector getCollector() {
        return this.collector;
    }

    public final ContractCallContext getCallContext() {
        return this.callContext;
    }

    public final PsiContractParserDispatcher getContractParserDispatcher() {
        return this.contractParserDispatcher;
    }
}
