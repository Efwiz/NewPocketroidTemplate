package org.jetbrains.kotlin.resolve.calls.inference.constraintPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConstraintPosition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0003\"\u00020\u0001¢\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/CompoundConstraintPosition;", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "positions", "", "([Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;)V", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "", "getPositions", "()Ljava/util/Collection;", "isStrong", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompoundConstraintPosition implements ConstraintPosition {
    private final Collection<ConstraintPosition> positions;

    public CompoundConstraintPosition(ConstraintPosition... positions) {
        List listListOf;
        Intrinsics.checkNotNullParameter(positions, "positions");
        ArrayList arrayList = new ArrayList();
        for (ConstraintPosition constraintPosition : positions) {
            CompoundConstraintPosition compoundConstraintPosition = constraintPosition instanceof CompoundConstraintPosition ? (CompoundConstraintPosition) constraintPosition : null;
            if (compoundConstraintPosition == null || (listListOf = compoundConstraintPosition.positions) == null) {
                listListOf = CollectionsKt.listOf(constraintPosition);
            }
            CollectionsKt.addAll(arrayList, listListOf);
        }
        this.positions = CollectionsKt.toSet(arrayList);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition
    public ConstraintPositionKind getKind() {
        return ConstraintPositionKind.COMPOUND_CONSTRAINT_POSITION;
    }

    public final Collection<ConstraintPosition> getPositions() {
        return this.positions;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition
    public boolean isStrong() {
        Collection<ConstraintPosition> collection = this.positions;
        if ((collection instanceof Collection) && collection.isEmpty()) {
            return false;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (((ConstraintPosition) it.next()).isStrong()) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return getKind() + '(' + CollectionsKt.joinToString$default(this.positions, null, null, null, 0, null, null, 63, null) + ')';
    }
}
