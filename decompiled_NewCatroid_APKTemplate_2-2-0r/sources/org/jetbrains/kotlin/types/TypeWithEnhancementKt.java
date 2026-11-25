package org.jetbrains.kotlin.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.Annotated;

/* JADX INFO: compiled from: TypeWithEnhancement.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\u001a\u0018\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0002\u001a\f\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u0004\u001a\f\u0010\u0005\u001a\u0004\u0018\u00010\u0004*\u00020\u0004\u001a\f\u0010\u0006\u001a\u00020\u0004*\u00020\u0004H\u0002\u001a\u0012\u0010\u0007\u001a\u00020\b*\u00020\b2\u0006\u0010\t\u001a\u00020\u0004\u001a&\u0010\u0007\u001a\u00020\b*\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u000b\u001a\n\u0010\f\u001a\u00020\u0004*\u00020\u0004\u001a\n\u0010\r\u001a\u00020\u0004*\u00020\u0004\u001a \u0010\u000e\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0002\u001a(\u0010\u000e\u001a\u00020\u000f*\u00020\u00042\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0002\u001a\u0014\u0010\u0012\u001a\u00020\b*\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004¨\u0006\u0014"}, d2 = {"enhanceTypeArguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "getEnhancement", "Lorg/jetbrains/kotlin/types/KotlinType;", "getEnhancementDeeply", "getEnhancementDeeplyInternal", "inheritEnhancement", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "origin", "transform", "Lkotlin/Function1;", "unwrapEnhancement", "unwrapEnhancementDeeply", "wereTypeArgumentsChanged", "", "newArguments", "newArgumentsForUpperBound", "wrapEnhancement", "enhancement", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeWithEnhancementKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final KotlinType getEnhancement(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        if (kotlinType instanceof TypeWithEnhancement) {
            return ((TypeWithEnhancement) kotlinType).getEnhancement();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3, types: [org.jetbrains.kotlin.types.KotlinType] */
    /* JADX WARN: Type inference failed for: r3v8 */
    private static final List<TypeProjection> enhanceTypeArguments(List<? extends TypeProjection> list) {
        List<? extends TypeProjection> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (TypeProjection typeProjectionReplaceType : list2) {
            if (!typeProjectionReplaceType.isStarProjection()) {
                Annotated type = typeProjectionReplaceType.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                KotlinType enhancementDeeplyInternal = getEnhancementDeeplyInternal(type instanceof TypeWithEnhancement ? ((TypeWithEnhancement) type).getEnhancement() : type);
                if (type != enhancementDeeplyInternal) {
                    typeProjectionReplaceType = typeProjectionReplaceType.replaceType(enhancementDeeplyInternal);
                    Intrinsics.checkNotNullExpressionValue(typeProjectionReplaceType, "replaceType(...)");
                }
            }
            arrayList.add(typeProjectionReplaceType);
        }
        return arrayList;
    }

    private static final boolean wereTypeArgumentsChanged(List<? extends TypeProjection> list, List<? extends TypeProjection> list2) {
        if (list2.size() == list.size()) {
            Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(list);
            if (!(iterableWithIndex instanceof Collection) || !((Collection) iterableWithIndex).isEmpty()) {
                for (IndexedValue indexedValue : iterableWithIndex) {
                    int index = indexedValue.getIndex();
                    if (list2.get(index) == ((TypeProjection) indexedValue.component2())) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    private static final boolean wereTypeArgumentsChanged(KotlinType kotlinType, List<? extends TypeProjection> list, List<? extends TypeProjection> list2) {
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
            return wereTypeArgumentsChanged(flexibleType.getLowerBound().getArguments(), list) || wereTypeArgumentsChanged(flexibleType.getUpperBound().getArguments(), list2);
        }
        return wereTypeArgumentsChanged(unwrappedTypeUnwrap.getArguments(), list);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [org.jetbrains.kotlin.types.KotlinType] */
    /* JADX WARN: Type inference failed for: r8v0, types: [org.jetbrains.kotlin.types.KotlinType] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v5, types: [org.jetbrains.kotlin.types.KotlinType] */
    private static final KotlinType getEnhancementDeeplyInternal(KotlinType kotlinType) {
        boolean z = kotlinType instanceof TypeWithEnhancement;
        List<TypeProjection> listEnhanceTypeArguments = enhanceTypeArguments(z ? ((TypeWithEnhancement) kotlinType).getEnhancement().getArguments() : kotlinType.getArguments());
        List<TypeProjection> listEnhanceTypeArguments2 = kotlinType instanceof FlexibleType ? enhanceTypeArguments(((FlexibleType) kotlinType).getUpperBound().getArguments()) : listEnhanceTypeArguments;
        if (z) {
            kotlinType = ((TypeWithEnhancement) kotlinType).getEnhancement();
        }
        ?? r2 = kotlinType;
        return !wereTypeArgumentsChanged(r2, listEnhanceTypeArguments, listEnhanceTypeArguments2) ? r2 : TypeSubstitutionKt.replace$default(r2, listEnhanceTypeArguments, null, listEnhanceTypeArguments2, 2, null);
    }

    public static final KotlinType getEnhancementDeeply(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        KotlinType enhancementDeeplyInternal = getEnhancementDeeplyInternal(kotlinType);
        if (enhancementDeeplyInternal == kotlinType) {
            return null;
        }
        return enhancementDeeplyInternal;
    }

    public static final KotlinType unwrapEnhancementDeeply(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        KotlinType enhancementDeeply = getEnhancementDeeply(kotlinType);
        return enhancementDeeply == null ? kotlinType : enhancementDeeply;
    }

    public static final KotlinType unwrapEnhancement(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        KotlinType enhancement = getEnhancement(kotlinType);
        return enhancement == null ? kotlinType : enhancement;
    }

    public static final UnwrappedType inheritEnhancement(UnwrappedType unwrappedType, KotlinType origin, Function1<? super KotlinType, ? extends KotlinType> transform) {
        Intrinsics.checkNotNullParameter(unwrappedType, "<this>");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(transform, "transform");
        KotlinType enhancement = getEnhancement(origin);
        return wrapEnhancement(unwrappedType, enhancement != null ? transform.invoke(enhancement) : null);
    }

    public static final UnwrappedType inheritEnhancement(UnwrappedType unwrappedType, KotlinType origin) {
        Intrinsics.checkNotNullParameter(unwrappedType, "<this>");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return wrapEnhancement(unwrappedType, getEnhancement(origin));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final UnwrappedType wrapEnhancement(UnwrappedType unwrappedType, KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(unwrappedType, "<this>");
        if (unwrappedType instanceof TypeWithEnhancement) {
            return wrapEnhancement(((TypeWithEnhancement) unwrappedType).getOrigin(), kotlinType);
        }
        if (kotlinType == null || Intrinsics.areEqual(kotlinType, unwrappedType)) {
            return unwrappedType;
        }
        if (unwrappedType instanceof SimpleType) {
            return new SimpleTypeWithEnhancement((SimpleType) unwrappedType, kotlinType);
        }
        if (unwrappedType instanceof FlexibleType) {
            return new FlexibleTypeWithEnhancement((FlexibleType) unwrappedType, kotlinType);
        }
        throw new NoWhenBranchMatchedException();
    }
}
