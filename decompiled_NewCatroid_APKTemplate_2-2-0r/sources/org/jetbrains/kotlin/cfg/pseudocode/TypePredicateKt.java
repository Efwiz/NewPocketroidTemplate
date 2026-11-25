package org.jetbrains.kotlin.cfg.pseudocode;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.util.SmartFMap;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;

/* JADX INFO: compiled from: TypePredicate.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u001a\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u001a.\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00010\u0006\"\b\b\u0000\u0010\u0007*\u00020\b*\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00070\n\u001a\n\u0010\u000b\u001a\u00020\u0001*\u00020\f\u001a\f\u0010\r\u001a\u00020\u000e*\u00020\fH\u0002¨\u0006\u000f"}, d2 = {"and", "Lorg/jetbrains/kotlin/cfg/pseudocode/TypePredicate;", "predicates", "", "or", "expectedTypeFor", "", ExifInterface.GPS_DIRECTION_TRUE, "", UserMetadata.KEYDATA_FILENAME, "", "getSubtypesPredicate", "Lorg/jetbrains/kotlin/types/KotlinType;", "render", "", "cfg"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypePredicateKt {
    public static final TypePredicate and(Collection<? extends TypePredicate> predicates) {
        Intrinsics.checkNotNullParameter(predicates, "predicates");
        int size = predicates.size();
        if (size == 0) {
            return AllTypes.INSTANCE;
        }
        if (size == 1) {
            return (TypePredicate) CollectionsKt.first(predicates);
        }
        return new ForAllTypes(CollectionsKt.toList(predicates));
    }

    public static final TypePredicate or(Collection<? extends TypePredicate> predicates) {
        Intrinsics.checkNotNullParameter(predicates, "predicates");
        int size = predicates.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return (TypePredicate) CollectionsKt.first(predicates);
        }
        return new ForSomeType(CollectionsKt.toList(predicates));
    }

    public static final TypePredicate getSubtypesPredicate(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        if (KotlinBuiltIns.isAnyOrNullableAny(kotlinType) && kotlinType.getIsMarkedNullable()) {
            return AllTypes.INSTANCE;
        }
        return TypeUtils.canHaveSubtypes(KotlinTypeChecker.DEFAULT, kotlinType) ? new AllSubtypes(kotlinType) : new SingleType(kotlinType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String render(KotlinType kotlinType) {
        return DescriptorRenderer.SHORT_NAMES_IN_TYPES.renderType(kotlinType);
    }

    public static final <T> Map<T, TypePredicate> expectedTypeFor(TypePredicate typePredicate, Iterable<? extends T> keys) {
        Intrinsics.checkNotNullParameter(typePredicate, "<this>");
        Intrinsics.checkNotNullParameter(keys, "keys");
        SmartFMap smartFMapEmptyMap = SmartFMap.emptyMap();
        Iterator<? extends T> it = keys.iterator();
        while (it.hasNext()) {
            smartFMapEmptyMap = smartFMapEmptyMap.plus(it.next(), typePredicate);
        }
        Intrinsics.checkNotNullExpressionValue(smartFMapEmptyMap, "fold(...)");
        return smartFMapEmptyMap;
    }
}
