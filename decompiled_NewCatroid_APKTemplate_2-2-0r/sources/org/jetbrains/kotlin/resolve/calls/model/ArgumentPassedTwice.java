package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentPassedTwice;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "firstOccurrence", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "getFirstOccurrence", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;", "getParameterDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArgumentPassedTwice extends KotlinCallDiagnostic {
    private final KotlinCallArgument argument;
    private final ResolvedCallArgument firstOccurrence;
    private final ValueParameterDescriptor parameterDescriptor;

    public final KotlinCallArgument getArgument() {
        return this.argument;
    }

    public final ValueParameterDescriptor getParameterDescriptor() {
        return this.parameterDescriptor;
    }

    public final ResolvedCallArgument getFirstOccurrence() {
        return this.firstOccurrence;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArgumentPassedTwice(KotlinCallArgument argument, ValueParameterDescriptor parameterDescriptor, ResolvedCallArgument firstOccurrence) {
        super(CandidateApplicability.INAPPLICABLE);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(parameterDescriptor, "parameterDescriptor");
        Intrinsics.checkNotNullParameter(firstOccurrence, "firstOccurrence");
        this.argument = argument;
        this.parameterDescriptor = parameterDescriptor;
        this.firstOccurrence = firstOccurrence;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCallArgumentName(this.argument, this);
    }
}
