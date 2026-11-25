package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u00022\u00020\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0010\u0010\u0004\u001a\u00028\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\r¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/BuilderInferenceSubstitutionConstraintPosition;", "L", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/OnlyInputTypeConstraintPosition;", "builderInferenceLambda", "initialConstraint", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "isFromNotSubstitutedDeclaredUpperBound", "", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;Z)V", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getInitialConstraint", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "()Z", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BuilderInferenceSubstitutionConstraintPosition<L> extends ConstraintPosition implements OnlyInputTypeConstraintPosition {
    private final L builderInferenceLambda;
    private final InitialConstraint initialConstraint;
    private final boolean isFromNotSubstitutedDeclaredUpperBound;

    public /* synthetic */ BuilderInferenceSubstitutionConstraintPosition(Object obj, InitialConstraint initialConstraint, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, initialConstraint, (i & 4) != 0 ? false : z);
    }

    public final InitialConstraint getInitialConstraint() {
        return this.initialConstraint;
    }

    /* JADX INFO: renamed from: isFromNotSubstitutedDeclaredUpperBound, reason: from getter */
    public final boolean getIsFromNotSubstitutedDeclaredUpperBound() {
        return this.isFromNotSubstitutedDeclaredUpperBound;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuilderInferenceSubstitutionConstraintPosition(L l, InitialConstraint initialConstraint, boolean z) {
        super(null);
        Intrinsics.checkNotNullParameter(initialConstraint, "initialConstraint");
        this.builderInferenceLambda = l;
        this.initialConstraint = initialConstraint;
        this.isFromNotSubstitutedDeclaredUpperBound = z;
    }

    public String toString() {
        return "Incorporated builder inference constraint " + this.initialConstraint + " into " + this.builderInferenceLambda + " call";
    }
}
