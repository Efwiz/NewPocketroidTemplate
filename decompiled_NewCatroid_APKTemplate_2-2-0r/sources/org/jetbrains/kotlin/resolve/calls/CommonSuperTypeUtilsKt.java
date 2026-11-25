package org.jetbrains.kotlin.resolve.calls;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: CommonSuperTypeUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004¨\u0006\u0005"}, d2 = {"commonSuperType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "Lorg/jetbrains/kotlin/resolve/calls/NewCommonSuperTypeCalculator;", SpecTypePair.NAME_types, "", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CommonSuperTypeUtilsKt {
    public static final UnwrappedType commonSuperType(NewCommonSuperTypeCalculator newCommonSuperTypeCalculator, List<? extends UnwrappedType> types) {
        Intrinsics.checkNotNullParameter(newCommonSuperTypeCalculator, "<this>");
        Intrinsics.checkNotNullParameter(types, "types");
        KotlinTypeMarker kotlinTypeMarkerCommonSuperType = newCommonSuperTypeCalculator.commonSuperType(SimpleClassicTypeSystemContext.INSTANCE, types);
        Intrinsics.checkNotNull(kotlinTypeMarkerCommonSuperType, "null cannot be cast to non-null type org.jetbrains.kotlin.types.UnwrappedType");
        return (UnwrappedType) kotlinTypeMarkerCommonSuperType;
    }
}
