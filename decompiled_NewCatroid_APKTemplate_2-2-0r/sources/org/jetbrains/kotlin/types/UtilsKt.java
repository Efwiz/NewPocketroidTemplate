package org.jetbrains.kotlin.types;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.checker.ErrorTypesAreEqualToAnything;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001\u001a\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H\u0000¨\u0006\b"}, d2 = {"getEffectiveVariance", "Lorg/jetbrains/kotlin/types/Variance;", "parameterVariance", "projectionKind", "hackForTypeIntersector", "Lorg/jetbrains/kotlin/types/KotlinType;", SpecTypePair.NAME_types, "", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final KotlinType hackForTypeIntersector(Collection<? extends KotlinType> types) {
        Object next;
        Intrinsics.checkNotNullParameter(types, "types");
        if (types.size() < 2) {
            return (KotlinType) CollectionsKt.firstOrNull(types);
        }
        Collection<? extends KotlinType> collection = types;
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            next = it.next();
            KotlinType kotlinType = (KotlinType) next;
            if (!collection.isEmpty()) {
                Iterator<T> it2 = collection.iterator();
                do {
                    if (it2.hasNext()) {
                    }
                } while (ErrorTypesAreEqualToAnything.INSTANCE.isSubtypeOf(kotlinType, (KotlinType) it2.next()));
            }
            return (KotlinType) next;
        }
        next = null;
        return (KotlinType) next;
    }

    public static final Variance getEffectiveVariance(Variance parameterVariance, Variance projectionKind) {
        Intrinsics.checkNotNullParameter(parameterVariance, "parameterVariance");
        Intrinsics.checkNotNullParameter(projectionKind, "projectionKind");
        if (parameterVariance == Variance.INVARIANT) {
            return projectionKind;
        }
        return (projectionKind == Variance.INVARIANT || parameterVariance == projectionKind) ? parameterVariance : Variance.OUT_VARIANCE;
    }
}
