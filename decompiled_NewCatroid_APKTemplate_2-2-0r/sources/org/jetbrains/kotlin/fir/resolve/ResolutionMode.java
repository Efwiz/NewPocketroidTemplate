package org.jetbrains.kotlin.fir.resolve;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ResolutionMode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \b2\u00020\u0001:\b\u0007\b\t\n\u000b\f\r\u000eB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0007\u000f\u0010\u0011\u0012\u0013\u0014\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "", "forceFullCompletion", "", "(Z)V", "getForceFullCompletion", "()Z", "AssignmentLValue", "Companion", "ContextDependent", "ContextIndependent", "LambdaResolution", "ReceiverResolution", "WithExpectedType", "WithStatus", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$AssignmentLValue;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ContextDependent;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ContextIndependent;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$LambdaResolution;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ReceiverResolution;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$WithExpectedType;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$WithStatus;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResolutionMode {
    private static final Companion Companion = new Companion(null);
    private final boolean forceFullCompletion;

    public /* synthetic */ ResolutionMode(boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(z);
    }

    /* JADX INFO: compiled from: ResolutionMode.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00032\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ContextDependent;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "()V", "Default", "Delegate", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ContextDependent$Default;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ContextDependent$Delegate;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ContextDependent extends ResolutionMode {

        /* JADX INFO: renamed from: Default, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        public /* synthetic */ ContextDependent(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.ResolutionMode$ContextDependent$Default, reason: from kotlin metadata */
        /* JADX INFO: compiled from: ResolutionMode.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ContextDependent$Default;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ContextDependent;", "()V", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion extends ContextDependent {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
                super(null);
            }

            public String toString() {
                return "ContextDependent";
            }
        }

        private ContextDependent() {
            super(false, null);
        }

        /* JADX INFO: compiled from: ResolutionMode.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ContextDependent$Delegate;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ContextDependent;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Delegate extends ContextDependent {
            public static final Delegate INSTANCE = new Delegate();

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Delegate)) {
                    return false;
                }
                return true;
            }

            public int hashCode() {
                return 1425488432;
            }

            public String toString() {
                return "Delegate";
            }

            private Delegate() {
                super(null);
            }
        }
    }

    private ResolutionMode(boolean z) {
        this.forceFullCompletion = z;
    }

    public final boolean getForceFullCompletion() {
        return this.forceFullCompletion;
    }

    /* JADX INFO: compiled from: ResolutionMode.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ContextIndependent;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ContextIndependent extends ResolutionMode {
        public static final ContextIndependent INSTANCE = new ContextIndependent();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ContextIndependent)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -242784130;
        }

        public String toString() {
            return "ContextIndependent";
        }

        private ContextIndependent() {
            super(true, null);
        }
    }

    /* JADX INFO: compiled from: ResolutionMode.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$ReceiverResolution;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ReceiverResolution extends ResolutionMode {
        public static final ReceiverResolution INSTANCE = new ReceiverResolution();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ReceiverResolution)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -872648146;
        }

        public String toString() {
            return "ReceiverResolution";
        }

        private ReceiverResolution() {
            super(true, null);
        }
    }

    /* JADX INFO: compiled from: ResolutionMode.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005¢\u0006\u0002\u0010\nJ\u001a\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u0005J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$WithExpectedType;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "expectedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "mayBeCoercionToUnitApplied", "", "expectedTypeMismatchIsReportedInChecker", "fromCast", "shouldBeStrictlyEnforced", "forceFullCompletion", "(Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;ZZZZZ)V", "getExpectedTypeMismatchIsReportedInChecker", "()Z", "getExpectedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "getFromCast", "getMayBeCoercionToUnitApplied", "getShouldBeStrictlyEnforced", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WithExpectedType extends ResolutionMode {
        private final boolean expectedTypeMismatchIsReportedInChecker;
        private final FirResolvedTypeRef expectedTypeRef;
        private final boolean fromCast;
        private final boolean mayBeCoercionToUnitApplied;
        private final boolean shouldBeStrictlyEnforced;

        public /* synthetic */ WithExpectedType(FirResolvedTypeRef firResolvedTypeRef, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(firResolvedTypeRef, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2, (i & 8) == 0 ? z3 : false, (i & 16) != 0 ? true : z4, (i & 32) == 0 ? z5 : true);
        }

        public final FirResolvedTypeRef getExpectedTypeRef() {
            return this.expectedTypeRef;
        }

        public final boolean getMayBeCoercionToUnitApplied() {
            return this.mayBeCoercionToUnitApplied;
        }

        public final boolean getExpectedTypeMismatchIsReportedInChecker() {
            return this.expectedTypeMismatchIsReportedInChecker;
        }

        public final boolean getFromCast() {
            return this.fromCast;
        }

        public final boolean getShouldBeStrictlyEnforced() {
            return this.shouldBeStrictlyEnforced;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public WithExpectedType(FirResolvedTypeRef expectedTypeRef, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            super(z5, null);
            Intrinsics.checkNotNullParameter(expectedTypeRef, "expectedTypeRef");
            this.expectedTypeRef = expectedTypeRef;
            this.mayBeCoercionToUnitApplied = z;
            this.expectedTypeMismatchIsReportedInChecker = z2;
            this.fromCast = z3;
            this.shouldBeStrictlyEnforced = z4;
        }

        public static /* synthetic */ WithExpectedType copy$default(WithExpectedType withExpectedType, boolean z, boolean z2, int i, Object obj) {
            if ((i & 1) != 0) {
                z = withExpectedType.mayBeCoercionToUnitApplied;
            }
            if ((i & 2) != 0) {
                z2 = withExpectedType.getForceFullCompletion();
            }
            return withExpectedType.copy(z, z2);
        }

        public final WithExpectedType copy(boolean mayBeCoercionToUnitApplied, boolean forceFullCompletion) {
            return new WithExpectedType(this.expectedTypeRef, mayBeCoercionToUnitApplied, this.expectedTypeMismatchIsReportedInChecker, this.fromCast, this.shouldBeStrictlyEnforced, forceFullCompletion);
        }

        public String toString() {
            return "WithExpectedType: " + ResolutionMode.Companion.prettyString(this.expectedTypeRef) + ", mayBeCoercionToUnitApplied=" + this.mayBeCoercionToUnitApplied + ", expectedTypeMismatchIsReportedInChecker=" + this.expectedTypeMismatchIsReportedInChecker + ", fromCast=" + this.fromCast + ", shouldBeStrictlyEnforced=" + this.shouldBeStrictlyEnforced + ", ";
        }
    }

    /* JADX INFO: compiled from: ResolutionMode.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$WithStatus;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WithStatus extends ResolutionMode {
        private final FirDeclarationStatus status;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public WithStatus(FirDeclarationStatus status) {
            super(false, null);
            Intrinsics.checkNotNullParameter(status, "status");
            this.status = status;
        }

        public final FirDeclarationStatus getStatus() {
            return this.status;
        }

        public String toString() {
            return "WithStatus: " + UtilsKt.render(this.status);
        }
    }

    /* JADX INFO: compiled from: ResolutionMode.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$LambdaResolution;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "expectedReturnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;)V", "getExpectedReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LambdaResolution extends ResolutionMode {
        private final FirResolvedTypeRef expectedReturnTypeRef;

        public LambdaResolution(FirResolvedTypeRef firResolvedTypeRef) {
            super(false, null);
            this.expectedReturnTypeRef = firResolvedTypeRef;
        }

        public final FirResolvedTypeRef getExpectedReturnTypeRef() {
            return this.expectedReturnTypeRef;
        }

        public String toString() {
            return "LambdaResolution: " + ResolutionMode.Companion.prettyString(this.expectedReturnTypeRef);
        }
    }

    /* JADX INFO: compiled from: ResolutionMode.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$AssignmentLValue;", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "(Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;)V", "getVariableAssignment", "()Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AssignmentLValue extends ResolutionMode {
        private final FirVariableAssignment variableAssignment;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AssignmentLValue(FirVariableAssignment variableAssignment) {
            super(true, null);
            Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
            this.variableAssignment = variableAssignment;
        }

        public final FirVariableAssignment getVariableAssignment() {
            return this.variableAssignment;
        }

        public String toString() {
            return "AssignmentLValue: " + UtilsKt.render(this.variableAssignment);
        }
    }

    /* JADX INFO: compiled from: ResolutionMode.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u0004*\u0004\u0018\u00010\u0005H\u0002¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode$Companion;", "", "()V", "prettyString", "", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String prettyString(FirTypeRef firTypeRef) {
            if (firTypeRef == null) {
                return "null";
            }
            FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
            if (coneKotlinType == null) {
                return UtilsKt.render(firTypeRef);
            }
            return ConeTypeUtilsKt.renderForDebugging(coneKotlinType);
        }
    }
}
