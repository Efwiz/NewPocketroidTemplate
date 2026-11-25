package org.jetbrains.kotlin.resolve.calls.inference.constraintPosition;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConstraintPosition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007\u001a\f\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\u0001¨\u0006\n"}, d2 = {"valueParameterPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "index", "", "derivedFrom", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "getValidityConstraintForConstituentType", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ValidityConstraintForConstituentType;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstraintPositionKt {
    public static final ConstraintPosition valueParameterPosition(int i) {
        return ConstraintPositionKind.VALUE_PARAMETER_POSITION.position(i);
    }

    public static final boolean derivedFrom(ConstraintPosition constraintPosition, ConstraintPositionKind kind) {
        Intrinsics.checkNotNullParameter(constraintPosition, "<this>");
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (constraintPosition instanceof CompoundConstraintPosition) {
            Collection<ConstraintPosition> positions = ((CompoundConstraintPosition) constraintPosition).getPositions();
            if (!(positions instanceof Collection) || !positions.isEmpty()) {
                Iterator<T> it = positions.iterator();
                while (it.hasNext()) {
                    if (((ConstraintPosition) it.next()).getKind() == kind) {
                        return true;
                    }
                }
            }
        } else if (constraintPosition.getKind() == kind) {
            return true;
        }
        return false;
    }

    public static final ValidityConstraintForConstituentType getValidityConstraintForConstituentType(ConstraintPosition constraintPosition) {
        Intrinsics.checkNotNullParameter(constraintPosition, "<this>");
        if (constraintPosition instanceof ValidityConstraintForConstituentType) {
            return (ValidityConstraintForConstituentType) constraintPosition;
        }
        Object obj = null;
        if (!(constraintPosition instanceof CompoundConstraintPosition)) {
            return null;
        }
        for (Object obj2 : SequencesKt.map(CollectionsKt.asSequence(((CompoundConstraintPosition) constraintPosition).getPositions()), new Function1<ConstraintPosition, ValidityConstraintForConstituentType>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKt.getValidityConstraintForConstituentType.1
            @Override // kotlin.jvm.functions.Function1
            public final ValidityConstraintForConstituentType invoke(ConstraintPosition it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ConstraintPositionKt.getValidityConstraintForConstituentType(it);
            }
        })) {
            if (((ValidityConstraintForConstituentType) obj2) != null) {
                obj = obj2;
                break;
            }
        }
        return (ValidityConstraintForConstituentType) obj;
    }
}
