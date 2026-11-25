package org.jetbrains.kotlin.resolve.calls.inference.constraintPosition;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: ConstraintPosition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "", "(Ljava/lang/String;I)V", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "index", "", "RECEIVER_POSITION", "EXPECTED_TYPE_POSITION", "VALUE_PARAMETER_POSITION", "TYPE_BOUND_POSITION", "COMPOUND_CONSTRAINT_POSITION", "FROM_COMPLETER", "SPECIAL", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ConstraintPositionKind {
    RECEIVER_POSITION,
    EXPECTED_TYPE_POSITION,
    VALUE_PARAMETER_POSITION,
    TYPE_BOUND_POSITION,
    COMPOUND_CONSTRAINT_POSITION,
    FROM_COMPLETER,
    SPECIAL;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<ConstraintPositionKind> getEntries() {
        return $ENTRIES;
    }

    public final ConstraintPosition position() {
        SetsKt.setOf((Object[]) new ConstraintPositionKind[]{RECEIVER_POSITION, EXPECTED_TYPE_POSITION, FROM_COMPLETER, SPECIAL}).contains(this);
        return new ConstraintPositionImpl(this);
    }

    public final ConstraintPosition position(int index) {
        SetsKt.setOf((Object[]) new ConstraintPositionKind[]{VALUE_PARAMETER_POSITION, TYPE_BOUND_POSITION}).contains(this);
        return new ConstraintPositionWithIndex(this, index);
    }
}
