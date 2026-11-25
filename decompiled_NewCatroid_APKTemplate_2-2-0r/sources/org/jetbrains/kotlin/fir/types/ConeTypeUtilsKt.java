package org.jetbrains.kotlin.fir.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.fir.renderer.ConeIdRenderer;
import org.jetbrains.kotlin.fir.renderer.ConeIdRendererForDebugging;
import org.jetbrains.kotlin.fir.renderer.ConeIdShortRenderer;
import org.jetbrains.kotlin.fir.renderer.ConeTypeRendererForDebugging;
import org.jetbrains.kotlin.fir.renderer.ConeTypeRendererWithJavaFlexibleTypes;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: ConeTypeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u001e\u0010\t\u001a\u00020\u0006*\u00020\u00022\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u000b\u001a.\u0010\t\u001a\u00020\u0006*\u00020\u00022\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\rH\u0002\u001a\u001e\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000f0\u000b\u001a\n\u0010\u0011\u001a\u00020\u0006*\u00020\u0002\u001a\n\u0010\u0012\u001a\u00020\u0013*\u00020\u0002\u001a\u001e\u0010\u0014\u001a\u00020\u0015*\u00020\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u000b\u001a\n\u0010\u0017\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010\u0018\u001a\u00020\u0019*\u00020\u0002\u001a\n\u0010\u001a\u001a\u00020\u0019*\u00020\u0002\u001a\n\u0010\u001b\u001a\u00020\u0019*\u00020\u0002\u001a\n\u0010\u001c\u001a\u00020\u001d*\u00020\u001d\u001a\u0012\u0010\u001e\u001a\u00020\u001f*\u00020\u00022\u0006\u0010 \u001a\u00020!\u001a\u0012\u0010\u001e\u001a\u00020\u001f*\u00020\u00022\u0006\u0010\"\u001a\u00020#\u001a\n\u0010$\u001a\u00020\u0013*\u00020\u0002\u001a\u0012\u0010%\u001a\u00020\u0015*\u00020\u00152\u0006\u0010&\u001a\u00020\u0002\u001a\u001f\u0010'\u001a\u00020\u001d*\u00020\u001d2\u000e\u0010(\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001f0)¢\u0006\u0002\u0010*\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\"\u0015\u0010\b\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0007¨\u0006+"}, d2 = {"classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getClassId", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Lorg/jetbrains/kotlin/name/ClassId;", "isMarkedNullable", "", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Z", "isNullable", "contains", "predicate", "Lkotlin/Function1;", "visited", "Lorg/jetbrains/kotlin/utils/SmartSet;", "forEachType", "", "action", "hasError", "lowerBoundIfFlexible", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "mapTypes", "Lorg/jetbrains/kotlin/fir/types/ConeIntersectionType;", "func", "originalIfDefinitelyNotNullable", "renderForDebugging", "", "renderReadable", "renderReadableWithFqNames", "replaceArgumentsWithStarProjections", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "toTypeProjection", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "projectionKind", "Lorg/jetbrains/kotlin/fir/types/ProjectionKind;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "upperBoundIfFlexible", "withAlternative", "alternativeType", "withArguments", "typeArguments", "", "(Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;)Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "cones"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeUtilsKt {

    /* JADX INFO: compiled from: ConeTypeUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Variance.OUT_VARIANCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProjectionKind.values().length];
            try {
                iArr2[ProjectionKind.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ProjectionKind.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ProjectionKind.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[ProjectionKind.STAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static final boolean isNullable(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return coneKotlinType.getNullability() != ConeNullability.NOT_NULL;
    }

    public static final boolean isMarkedNullable(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return coneKotlinType.getNullability() == ConeNullability.NULLABLE;
    }

    public static final ClassId getClassId(ConeKotlinType coneKotlinType) {
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        ConeClassLikeType coneClassLikeType = coneKotlinType instanceof ConeClassLikeType ? (ConeClassLikeType) coneKotlinType : null;
        if (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) {
            return null;
        }
        return lookupTag.getClassId();
    }

    public static final void forEachType(ConeKotlinType coneKotlinType, Function1<? super ConeKotlinType, Unit> action) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        action.invoke(coneKotlinType);
        if (coneKotlinType instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
            forEachType(coneFlexibleType.getLowerBound(), action);
            forEachType(coneFlexibleType.getUpperBound(), action);
            return;
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            forEachType(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal(), action);
            return;
        }
        if (!(coneKotlinType instanceof ConeIntersectionType)) {
            for (ConeTypeProjection coneTypeProjection : coneKotlinType.getTypeArguments()) {
                if (coneTypeProjection instanceof ConeKotlinTypeProjection) {
                    forEachType(((ConeKotlinTypeProjection) coneTypeProjection).getType(), action);
                }
            }
            return;
        }
        Iterator<T> it = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes().iterator();
        while (it.hasNext()) {
            forEachType((ConeKotlinType) it.next(), action);
        }
    }

    public static final boolean contains(ConeKotlinType coneKotlinType, Function1<? super ConeKotlinType, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return contains(coneKotlinType, predicate, SmartSet.INSTANCE.create());
    }

    private static final boolean contains(ConeKotlinType coneKotlinType, Function1<? super ConeKotlinType, Boolean> function1, SmartSet<ConeKotlinType> smartSet) {
        if (smartSet.contains(coneKotlinType)) {
            return false;
        }
        if (function1.invoke(coneKotlinType).booleanValue()) {
            return true;
        }
        smartSet.add(coneKotlinType);
        if (!(coneKotlinType instanceof ConeFlexibleType)) {
            if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
                return contains(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal(), function1, smartSet);
            }
            if (!(coneKotlinType instanceof ConeIntersectionType)) {
                for (ConeTypeProjection coneTypeProjection : coneKotlinType.getTypeArguments()) {
                    if (!(coneTypeProjection instanceof ConeKotlinTypeProjection) || !contains(((ConeKotlinTypeProjection) coneTypeProjection).getType(), function1, smartSet)) {
                    }
                }
                return false;
            }
            Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes();
            if ((intersectedTypes instanceof Collection) && intersectedTypes.isEmpty()) {
                return false;
            }
            Iterator<T> it = intersectedTypes.iterator();
            while (it.hasNext()) {
                if (contains((ConeKotlinType) it.next(), function1, smartSet)) {
                }
            }
            return false;
        }
        ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
        if (!contains(coneFlexibleType.getLowerBound(), function1, smartSet) && !contains(coneFlexibleType.getUpperBound(), function1, smartSet)) {
            return false;
        }
        return true;
    }

    public static final ConeSimpleKotlinType upperBoundIfFlexible(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        if (coneKotlinType instanceof ConeSimpleKotlinType) {
            return (ConeSimpleKotlinType) coneKotlinType;
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            return ((ConeFlexibleType) coneKotlinType).getUpperBound();
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final ConeSimpleKotlinType lowerBoundIfFlexible(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        if (coneKotlinType instanceof ConeSimpleKotlinType) {
            return (ConeSimpleKotlinType) coneKotlinType;
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            return ((ConeFlexibleType) coneKotlinType).getLowerBound();
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final ConeKotlinType originalIfDefinitelyNotNullable(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return coneKotlinType instanceof ConeDefinitelyNotNullType ? ((ConeDefinitelyNotNullType) coneKotlinType).getOriginal() : coneKotlinType;
    }

    public static final ConeIntersectionType withAlternative(ConeIntersectionType coneIntersectionType, ConeKotlinType alternativeType) {
        Intrinsics.checkNotNullParameter(coneIntersectionType, "<this>");
        Intrinsics.checkNotNullParameter(alternativeType, "alternativeType");
        return new ConeIntersectionType(coneIntersectionType.getIntersectedTypes(), alternativeType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeIntersectionType mapTypes(ConeIntersectionType coneIntersectionType, Function1<? super ConeKotlinType, ? extends ConeKotlinType> func) {
        Intrinsics.checkNotNullParameter(coneIntersectionType, "<this>");
        Intrinsics.checkNotNullParameter(func, "func");
        Collection<ConeKotlinType> intersectedTypes = coneIntersectionType.getIntersectedTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intersectedTypes, 10));
        Iterator<T> it = intersectedTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(func.invoke(it.next()));
        }
        ArrayList arrayList2 = arrayList;
        ConeKotlinType alternativeType = coneIntersectionType.getAlternativeType();
        return new ConeIntersectionType(arrayList2, alternativeType != null ? func.invoke(alternativeType) : null);
    }

    public static final ConeClassLikeType withArguments(ConeClassLikeType coneClassLikeType, ConeTypeProjection[] typeArguments) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        if (coneClassLikeType instanceof ConeClassLikeTypeImpl) {
            return new ConeClassLikeTypeImpl(coneClassLikeType.getLookupTag(), typeArguments, isNullable(coneClassLikeType), coneClassLikeType.getAttributes());
        }
        if (coneClassLikeType instanceof ConeErrorType) {
            return coneClassLikeType;
        }
        throw new IllegalStateException(("Unknown cone type: " + Reflection.getOrCreateKotlinClass(coneClassLikeType.getClass())).toString());
    }

    public static final ConeTypeProjection toTypeProjection(ConeKotlinType coneKotlinType, Variance variance) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(variance, "variance");
        int i = WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
        if (i == 1) {
            return coneKotlinType;
        }
        if (i == 2) {
            return new ConeKotlinTypeProjectionIn(coneKotlinType);
        }
        if (i == 3) {
            return new ConeKotlinTypeProjectionOut(coneKotlinType);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final ConeTypeProjection toTypeProjection(ConeKotlinType coneKotlinType, ProjectionKind projectionKind) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(projectionKind, "projectionKind");
        int i = WhenMappings.$EnumSwitchMapping$1[projectionKind.ordinal()];
        if (i == 1) {
            return coneKotlinType;
        }
        if (i == 2) {
            return new ConeKotlinTypeProjectionIn(coneKotlinType);
        }
        if (i == 3) {
            return new ConeKotlinTypeProjectionOut(coneKotlinType);
        }
        if (i == 4) {
            return ConeStarProjection.INSTANCE;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final ConeClassLikeType replaceArgumentsWithStarProjections(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        if (coneClassLikeType.getTypeArguments().length == 0) {
            return coneClassLikeType;
        }
        int length = coneClassLikeType.getTypeArguments().length;
        ConeStarProjection[] coneStarProjectionArr = new ConeStarProjection[length];
        for (int i = 0; i < length; i++) {
            coneStarProjectionArr[i] = ConeStarProjection.INSTANCE;
        }
        return withArguments(coneClassLikeType, coneStarProjectionArr);
    }

    public static final String renderForDebugging(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        StringBuilder sb = new StringBuilder();
        new ConeTypeRendererForDebugging(sb).render(coneKotlinType);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final String renderReadable(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        StringBuilder sb = new StringBuilder();
        new ConeTypeRendererWithJavaFlexibleTypes(sb, new Function0<ConeIdRenderer>() { // from class: org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt.renderReadable.1
            @Override // kotlin.jvm.functions.Function0
            public final ConeIdRenderer invoke() {
                return new ConeIdShortRenderer();
            }
        }).render(coneKotlinType);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final String renderReadableWithFqNames(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        StringBuilder sb = new StringBuilder();
        new ConeTypeRendererWithJavaFlexibleTypes(sb, new Function0<ConeIdRenderer>() { // from class: org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt.renderReadableWithFqNames.1
            @Override // kotlin.jvm.functions.Function0
            public final ConeIdRenderer invoke() {
                return new ConeIdRendererForDebugging();
            }
        }).render(coneKotlinType);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final boolean hasError(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return contains(coneKotlinType, new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt.hasError.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ConeKotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it instanceof ConeErrorType);
            }
        });
    }
}
