package org.jetbrains.kotlin.fir.types;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConeIntegerLiteralIntersector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0002J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0002J\u001e\u0010\u0007\u001a\u0004\u0018\u00010\u00042\b\u0010\b\u001a\u0004\u0018\u00010\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u0004H\u0002¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralIntersector;", "", "()V", "findCommonIntersectionType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", SpecTypePair.NAME_types, "", "fold", "left", "Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralType;", "right", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeIntegerLiteralIntersector {
    public static final ConeIntegerLiteralIntersector INSTANCE = new ConeIntegerLiteralIntersector();

    private ConeIntegerLiteralIntersector() {
    }

    public final ConeKotlinType findCommonIntersectionType(Collection<? extends ConeKotlinType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        if (types.isEmpty()) {
            return null;
        }
        Iterator<T> it = types.iterator();
        if (!it.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it.next();
        while (it.hasNext()) {
            ConeKotlinType coneKotlinType = (ConeKotlinType) it.next();
            next = INSTANCE.fold((ConeKotlinType) next, coneKotlinType);
        }
        return (ConeKotlinType) next;
    }

    private final ConeKotlinType fold(ConeKotlinType left, ConeKotlinType right) {
        if (left == null || right == null) {
            return null;
        }
        boolean z = left instanceof ConeIntegerLiteralType;
        if (z && (right instanceof ConeIntegerLiteralType)) {
            return fold((ConeIntegerLiteralType) left, (ConeIntegerLiteralType) right);
        }
        if (z) {
            return fold((ConeIntegerLiteralType) left, right);
        }
        if (right instanceof ConeIntegerLiteralType) {
            return fold((ConeIntegerLiteralType) right, left);
        }
        return null;
    }

    private final ConeKotlinType fold(ConeIntegerLiteralType left, ConeIntegerLiteralType right) {
        if (left.getPossibleTypes().containsAll(right.getPossibleTypes())) {
            return right;
        }
        if (right.getPossibleTypes().containsAll(left.getPossibleTypes())) {
            return left;
        }
        return null;
    }

    private final ConeKotlinType fold(ConeIntegerLiteralType left, ConeKotlinType right) {
        if (CollectionsKt.contains(left.getPossibleTypes(), right)) {
            return right;
        }
        return null;
    }
}
