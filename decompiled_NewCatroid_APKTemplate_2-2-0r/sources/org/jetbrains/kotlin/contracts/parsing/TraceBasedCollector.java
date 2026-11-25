package org.jetbrains.kotlin.contracts.parsing;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;

/* JADX INFO: compiled from: ContractParsingDiagnosticsCollector.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0016\u001a\u00020\u000fH\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/contracts/parsing/TraceBasedCollector;", "Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;", "callContext", "Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;", "(Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;)V", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "mainCall", "Lorg/jetbrains/kotlin/psi/KtExpression;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/psi/KtExpression;)V", "mainCallReportTarget", "reportedErrors", "", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "addFallbackErrorIfNecessary", "", "badDescription", CrashHianalyticsData.MESSAGE, "", "reportOn", "Lorg/jetbrains/kotlin/psi/KtElement;", "contractNotAllowed", "flushDiagnostics", "hasErrors", "", "unsupportedFeature", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TraceBasedCollector implements ContractParsingDiagnosticsCollector {
    private final BindingTrace bindingTrace;
    private final KtExpression mainCallReportTarget;
    private final List<Diagnostic> reportedErrors;

    public TraceBasedCollector(BindingTrace bindingTrace, KtExpression mainCall) {
        KtExpression calleeExpression;
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(mainCall, "mainCall");
        this.bindingTrace = bindingTrace;
        this.reportedErrors = new ArrayList();
        KtCallExpression ktCallExpression = mainCall instanceof KtCallExpression ? (KtCallExpression) mainCall : null;
        if (ktCallExpression != null && (calleeExpression = ktCallExpression.getCalleeExpression()) != null) {
            mainCall = calleeExpression;
        }
        this.mainCallReportTarget = mainCall;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TraceBasedCollector(ContractCallContext callContext) {
        this(callContext.getTrace(), callContext.getContractCallExpression());
        Intrinsics.checkNotNullParameter(callContext, "callContext");
    }

    @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
    public void contractNotAllowed(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.reportedErrors.add(Errors.CONTRACT_NOT_ALLOWED.on(this.mainCallReportTarget, message));
    }

    @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
    public void badDescription(String message, KtElement reportOn) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        this.reportedErrors.add(Errors.ERROR_IN_CONTRACT_DESCRIPTION.on(reportOn, message));
    }

    @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
    public void unsupportedFeature(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.reportedErrors.add(Errors.UNSUPPORTED_FEATURE.on(this.mainCallReportTarget, TuplesKt.to(LanguageFeature.AllowContractsForCustomFunctions, languageVersionSettings)));
    }

    @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
    public void addFallbackErrorIfNecessary() {
        if (this.reportedErrors.isEmpty()) {
            this.reportedErrors.add(Errors.ERROR_IN_CONTRACT_DESCRIPTION.on(this.mainCallReportTarget, "Error in contract description"));
        }
    }

    @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
    public void flushDiagnostics() {
        Iterator<T> it = this.reportedErrors.iterator();
        while (it.hasNext()) {
            this.bindingTrace.report((Diagnostic) it.next());
        }
    }

    @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
    public boolean hasErrors() {
        return !this.reportedErrors.isEmpty();
    }
}
