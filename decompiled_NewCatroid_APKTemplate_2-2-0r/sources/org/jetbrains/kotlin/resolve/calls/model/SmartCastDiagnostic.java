package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/SmartCastDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "smartCastType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "getKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getSmartCastType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SmartCastDiagnostic extends KotlinCallDiagnostic {
    private final ExpressionKotlinCallArgument argument;
    private final KotlinCall kotlinCall;
    private final UnwrappedType smartCastType;

    public final ExpressionKotlinCallArgument getArgument() {
        return this.argument;
    }

    public final UnwrappedType getSmartCastType() {
        return this.smartCastType;
    }

    public final KotlinCall getKotlinCall() {
        return this.kotlinCall;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartCastDiagnostic(ExpressionKotlinCallArgument argument, UnwrappedType smartCastType, KotlinCall kotlinCall) {
        super(CandidateApplicability.RESOLVED);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(smartCastType, "smartCastType");
        this.argument = argument;
        this.smartCastType = smartCastType;
        this.kotlinCall = kotlinCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCallArgument(this.argument, this);
    }
}
