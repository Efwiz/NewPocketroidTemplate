package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/NameForAmbiguousParameter;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "overriddenParameterWithOtherName", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "getOverriddenParameterWithOtherName", "()Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getParameterDescriptor", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NameForAmbiguousParameter extends KotlinCallDiagnostic {
    private final KotlinCallArgument argument;
    private final ValueParameterDescriptor overriddenParameterWithOtherName;
    private final ValueParameterDescriptor parameterDescriptor;

    public final KotlinCallArgument getArgument() {
        return this.argument;
    }

    public final ValueParameterDescriptor getParameterDescriptor() {
        return this.parameterDescriptor;
    }

    public final ValueParameterDescriptor getOverriddenParameterWithOtherName() {
        return this.overriddenParameterWithOtherName;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NameForAmbiguousParameter(KotlinCallArgument argument, ValueParameterDescriptor parameterDescriptor, ValueParameterDescriptor overriddenParameterWithOtherName) {
        super(CandidateApplicability.CONVENTION_ERROR);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(parameterDescriptor, "parameterDescriptor");
        Intrinsics.checkNotNullParameter(overriddenParameterWithOtherName, "overriddenParameterWithOtherName");
        this.argument = argument;
        this.parameterDescriptor = parameterDescriptor;
        this.overriddenParameterWithOtherName = overriddenParameterWithOtherName;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCallArgumentName(this.argument, this);
    }
}
