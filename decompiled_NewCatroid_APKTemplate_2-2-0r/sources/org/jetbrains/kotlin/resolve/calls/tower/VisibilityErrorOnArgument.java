package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;

/* JADX INFO: compiled from: ImplicitScopeTower.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/VisibilityErrorOnArgument;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "invisibleMember", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "getInvisibleMember", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VisibilityErrorOnArgument extends ResolutionDiagnostic {
    private final KotlinCallArgument argument;
    private final DeclarationDescriptorWithVisibility invisibleMember;

    public final KotlinCallArgument getArgument() {
        return this.argument;
    }

    public final DeclarationDescriptorWithVisibility getInvisibleMember() {
        return this.invisibleMember;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VisibilityErrorOnArgument(KotlinCallArgument argument, DeclarationDescriptorWithVisibility invisibleMember) {
        super(CandidateApplicability.K1_RUNTIME_ERROR);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(invisibleMember, "invisibleMember");
        this.argument = argument;
        this.invisibleMember = invisibleMember;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ResolutionDiagnostic, org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCallArgument(this.argument, this);
    }
}
