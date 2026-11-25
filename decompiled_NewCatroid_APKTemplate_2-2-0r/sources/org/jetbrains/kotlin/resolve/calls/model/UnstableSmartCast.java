package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u001f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u0082\u0001\u0002\u0012\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/UnstableSmartCast;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "targetType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "getTargetType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "Companion", "Lorg/jetbrains/kotlin/resolve/calls/model/UnstableSmartCastDiagnosticError;", "Lorg/jetbrains/kotlin/resolve/calls/model/UnstableSmartCastResolutionError;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class UnstableSmartCast extends KotlinCallDiagnostic {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ExpressionKotlinCallArgument argument;
    private final UnwrappedType targetType;

    public /* synthetic */ UnstableSmartCast(ExpressionKotlinCallArgument expressionKotlinCallArgument, UnwrappedType unwrappedType, CandidateApplicability candidateApplicability, DefaultConstructorMarker defaultConstructorMarker) {
        this(expressionKotlinCallArgument, unwrappedType, candidateApplicability);
    }

    public final ExpressionKotlinCallArgument getArgument() {
        return this.argument;
    }

    public final UnwrappedType getTargetType() {
        return this.targetType;
    }

    private UnstableSmartCast(ExpressionKotlinCallArgument expressionKotlinCallArgument, UnwrappedType unwrappedType, CandidateApplicability candidateApplicability) {
        super(candidateApplicability);
        this.argument = expressionKotlinCallArgument;
        this.targetType = unwrappedType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCallArgument(this.argument, this);
    }

    /* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J#\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0086\u0002¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/UnstableSmartCast$Companion;", "", "()V", "invoke", "Lorg/jetbrains/kotlin/resolve/calls/model/UnstableSmartCast;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "targetType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "isReceiver", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ UnstableSmartCast invoke$default(Companion companion, ExpressionKotlinCallArgument expressionKotlinCallArgument, UnwrappedType unwrappedType, boolean z, int i, Object obj) {
            if ((i & 4) != 0) {
                z = false;
            }
            return companion.invoke(expressionKotlinCallArgument, unwrappedType, z);
        }

        public final UnstableSmartCast invoke(ExpressionKotlinCallArgument argument, UnwrappedType targetType, boolean isReceiver) {
            Intrinsics.checkNotNullParameter(argument, "argument");
            Intrinsics.checkNotNullParameter(targetType, "targetType");
            return new UnstableSmartCastResolutionError(argument, targetType);
        }
    }
}
