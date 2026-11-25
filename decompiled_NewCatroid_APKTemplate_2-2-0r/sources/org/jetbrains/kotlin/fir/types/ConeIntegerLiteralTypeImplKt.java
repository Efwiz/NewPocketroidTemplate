package org.jetbrains.kotlin.fir.types;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: ConeIntegerLiteralTypeImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u001a\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007¨\u0006\b"}, d2 = {"approximateIntegerLiteralType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "expectedType", "findCommonSuperType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralType$Companion;", SpecTypePair.NAME_types, "", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConeIntegerLiteralTypeImplKt {
    public static final SimpleTypeMarker findCommonSuperType(ConeIntegerLiteralType.Companion companion, Collection<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(types, "types");
        return ConeIntegerLiteralTypeExtensions.INSTANCE.findCommonSuperType(types);
    }

    public static /* synthetic */ ConeKotlinType approximateIntegerLiteralType$default(ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2, int i, Object obj) {
        if ((i & 1) != 0) {
            coneKotlinType2 = null;
        }
        return approximateIntegerLiteralType(coneKotlinType, coneKotlinType2);
    }

    public static final ConeKotlinType approximateIntegerLiteralType(ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        if (coneKotlinType instanceof ConeIntegerLiteralType) {
            return ((ConeIntegerLiteralType) coneKotlinType).getApproximatedType(coneKotlinType2);
        }
        return coneKotlinType instanceof ConeFlexibleType ? ConeIntegerLiteralTypeExtensions.INSTANCE.approximateIntegerLiteralBounds((ConeFlexibleType) coneKotlinType, coneKotlinType2) : coneKotlinType;
    }
}
