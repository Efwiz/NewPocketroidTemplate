package org.jetbrains.kotlin.fir.resolve.substitution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeCapturedTypeConstructor;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeConflictingProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: Substitutors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\rH&J\u0018\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\rH\u0004J\u000e\u0010\u0013\u001a\u0004\u0018\u00010\r*\u00020\u0014H\u0002J\u000e\u0010\u0015\u001a\u0004\u0018\u00010\u0016*\u00020\u0016H\u0002J\u000e\u0010\u0017\u001a\u0004\u0018\u00010\u0018*\u00020\u0018H\u0002J\u000e\u0010\u0019\u001a\u0004\u0018\u00010\u001a*\u00020\u001aH\u0002J\u000e\u0010\u001b\u001a\u0004\u0018\u00010\r*\u00020\u001cH\u0002J\u000e\u0010\u001d\u001a\u0004\u0018\u00010\r*\u00020\rH\u0002J\u0016\u0010\u001e\u001a\u0004\u0018\u00010\r*\u0004\u0018\u00010\r2\u0006\u0010\u001f\u001a\u00020\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006 ²\u0006\u0012\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\"X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/substitution/AbstractConeSubstitutor;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "(Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;)V", "getTypeContext", "()Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "substituteArgument", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "projection", "index", "", "substituteOrNull", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ModuleXmlParser.TYPE, "substituteType", "wrapProjection", "old", "newType", "substituteArguments", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "substituteBounds", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "substituteCapturedType", "Lorg/jetbrains/kotlin/fir/types/ConeCapturedType;", "substituteIntersectedTypes", "Lorg/jetbrains/kotlin/fir/types/ConeIntersectionType;", "substituteOriginal", "Lorg/jetbrains/kotlin/fir/types/ConeDefinitelyNotNullType;", "substituteRecursive", "updateNullabilityIfNeeded", "originalType", "providers", "newArguments", ""}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractConeSubstitutor extends ConeSubstitutor {
    private final ConeTypeContext typeContext;

    public abstract ConeKotlinType substituteType(ConeKotlinType type);

    public AbstractConeSubstitutor(ConeTypeContext typeContext) {
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
        this.typeContext = typeContext;
    }

    protected final ConeTypeContext getTypeContext() {
        return this.typeContext;
    }

    protected final ConeTypeProjection wrapProjection(ConeTypeProjection old, ConeKotlinType newType) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(newType, "newType");
        if (old instanceof ConeStarProjection) {
            return old;
        }
        if (old instanceof ConeKotlinTypeProjectionIn) {
            return new ConeKotlinTypeProjectionIn(newType);
        }
        if (old instanceof ConeKotlinTypeProjectionOut) {
            return new ConeKotlinTypeProjectionOut(newType);
        }
        if (old instanceof ConeKotlinTypeConflictingProjection) {
            return new ConeKotlinTypeConflictingProjection(newType);
        }
        return old instanceof ConeKotlinType ? newType : old;
    }

    public ConeTypeProjection substituteArgument(ConeTypeProjection projection, int index) {
        ConeKotlinType type;
        ConeKotlinType coneKotlinTypeSubstituteOrNull;
        Intrinsics.checkNotNullParameter(projection, "projection");
        ConeKotlinTypeProjection coneKotlinTypeProjection = projection instanceof ConeKotlinTypeProjection ? (ConeKotlinTypeProjection) projection : null;
        if (coneKotlinTypeProjection == null || (type = coneKotlinTypeProjection.getType()) == null || (coneKotlinTypeSubstituteOrNull = substituteOrNull(type)) == null) {
            return null;
        }
        return wrapProjection(projection, coneKotlinTypeSubstituteOrNull);
    }

    public final ConeKotlinType updateNullabilityIfNeeded(ConeKotlinType coneKotlinType, ConeKotlinType originalType) {
        Intrinsics.checkNotNullParameter(originalType, "originalType");
        if (originalType instanceof ConeDefinitelyNotNullType) {
            if (coneKotlinType != null) {
                return TypeUtilsKt.withNullability$default(coneKotlinType, ConeNullability.NOT_NULL, this.typeContext, null, false, 12, null);
            }
        } else {
            if (!ConeTypeUtilsKt.isMarkedNullable(originalType)) {
                return coneKotlinType;
            }
            if (coneKotlinType != null) {
                return TypeUtilsKt.withNullability$default(coneKotlinType, ConeNullability.NULLABLE, this.typeContext, null, false, 12, null);
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor
    public ConeKotlinType substituteOrNull(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        ConeKotlinType coneKotlinTypeSubstituteType = substituteType(type);
        if (coneKotlinTypeSubstituteType == null || !(type instanceof ConeDefinitelyNotNullType)) {
            return coneKotlinTypeSubstituteType == null ? substituteRecursive(type) : coneKotlinTypeSubstituteType;
        }
        return TypeUtilsKt.makeConeTypeDefinitelyNotNullOrNotNull(coneKotlinTypeSubstituteType, this.typeContext, false);
    }

    private final ConeKotlinType substituteRecursive(ConeKotlinType coneKotlinType) {
        if (coneKotlinType instanceof ConeClassLikeType) {
            return substituteArguments((ConeClassLikeType) coneKotlinType);
        }
        if (coneKotlinType instanceof ConeLookupTagBasedType) {
            return null;
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleTypeSubstituteBounds = substituteBounds((ConeFlexibleType) coneKotlinType);
            if (coneFlexibleTypeSubstituteBounds != null) {
                return Intrinsics.areEqual(coneFlexibleTypeSubstituteBounds.getLowerBound(), coneFlexibleTypeSubstituteBounds.getUpperBound()) ? coneFlexibleTypeSubstituteBounds.getLowerBound() : coneFlexibleTypeSubstituteBounds;
            }
            return null;
        }
        if (coneKotlinType instanceof ConeCapturedType) {
            return substituteCapturedType((ConeCapturedType) coneKotlinType);
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return substituteOriginal((ConeDefinitelyNotNullType) coneKotlinType);
        }
        if (coneKotlinType instanceof ConeIntersectionType) {
            return substituteIntersectedTypes((ConeIntersectionType) coneKotlinType);
        }
        if ((coneKotlinType instanceof ConeStubType) || (coneKotlinType instanceof ConeIntegerLiteralType)) {
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final ConeCapturedType substituteCapturedType(ConeCapturedType coneCapturedType) {
        ArrayList arrayList;
        ConeKotlinType lowerType = coneCapturedType.getLowerType();
        if (lowerType == null) {
            lowerType = ConeTypeProjectionKt.getType(coneCapturedType.getConstructor().getProjection());
        }
        ConeKotlinType coneKotlinTypeSubstituteOrNull = ConeSubstitutorKt.substituteOrNull(this, lowerType);
        if (coneKotlinTypeSubstituteOrNull == null) {
            return null;
        }
        if (coneKotlinTypeSubstituteOrNull instanceof ConeCapturedType) {
            return (ConeCapturedType) coneKotlinTypeSubstituteOrNull;
        }
        List<ConeKotlinType> supertypes = coneCapturedType.getConstructor().getSupertypes();
        if (supertypes != null) {
            List<ConeKotlinType> list = supertypes;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add(substituteOrSelf((ConeKotlinType) it.next()));
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        return new ConeCapturedType(coneCapturedType.getCaptureStatus(), coneCapturedType.getLowerType() != null ? coneKotlinTypeSubstituteOrNull : null, null, new ConeCapturedTypeConstructor(wrapProjection(coneCapturedType.getConstructor().getProjection(), coneKotlinTypeSubstituteOrNull), arrayList, coneCapturedType.getConstructor().getTypeParameterMarker()), null, false, 52, null);
    }

    private final ConeIntersectionType substituteIntersectedTypes(ConeIntersectionType coneIntersectionType) {
        ArrayList arrayList = new ArrayList(coneIntersectionType.getIntersectedTypes().size());
        boolean z = false;
        for (ConeKotlinType coneKotlinType : coneIntersectionType.getIntersectedTypes()) {
            ConeKotlinType coneKotlinTypeSubstituteOrNull = substituteOrNull(coneKotlinType);
            if (coneKotlinTypeSubstituteOrNull != null) {
                z = true;
                coneKotlinType = coneKotlinTypeSubstituteOrNull;
            }
            arrayList.add(coneKotlinType);
        }
        if (z) {
            return new ConeIntersectionType(arrayList, null, 2, null);
        }
        return null;
    }

    private final ConeKotlinType substituteOriginal(ConeDefinitelyNotNullType coneDefinitelyNotNullType) {
        ConeKotlinType coneKotlinTypeSubstituteOrNull = substituteOrNull(coneDefinitelyNotNullType.getOriginal());
        if (coneKotlinTypeSubstituteOrNull == null) {
            return null;
        }
        ConeKotlinType coneKotlinTypeWithNullability = TypeUtilsKt.withNullability(coneKotlinTypeSubstituteOrNull, ConeNullability.NOT_NULL, this.typeContext, coneKotlinTypeSubstituteOrNull.getAttributes().add(coneDefinitelyNotNullType.getOriginal().getAttributes()), true);
        ConeDefinitelyNotNullType coneDefinitelyNotNullTypeCreate = TypeUtilsKt.create(ConeDefinitelyNotNullType.INSTANCE, coneKotlinTypeWithNullability, this.typeContext, true);
        return coneDefinitelyNotNullTypeCreate != null ? coneDefinitelyNotNullTypeCreate : coneKotlinTypeWithNullability;
    }

    private final ConeFlexibleType substituteBounds(ConeFlexibleType coneFlexibleType) {
        ConeSimpleKotlinType lowerBound;
        ConeSimpleKotlinType upperBound;
        ConeKotlinType coneKotlinTypeSubstituteOrNull = substituteOrNull(coneFlexibleType.getLowerBound());
        ConeKotlinType coneKotlinTypeSubstituteOrNull2 = substituteOrNull(coneFlexibleType.getUpperBound());
        if (coneKotlinTypeSubstituteOrNull == null && coneKotlinTypeSubstituteOrNull2 == null) {
            return null;
        }
        if (coneKotlinTypeSubstituteOrNull == null || (lowerBound = ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinTypeSubstituteOrNull)) == null) {
            lowerBound = coneFlexibleType.getLowerBound();
        }
        if (coneKotlinTypeSubstituteOrNull2 == null || (upperBound = ConeTypeUtilsKt.upperBoundIfFlexible(coneKotlinTypeSubstituteOrNull2)) == null) {
            upperBound = coneFlexibleType.getUpperBound();
        }
        return new ConeFlexibleType(lowerBound, upperBound);
    }

    private final ConeKotlinType substituteArguments(final ConeClassLikeType coneClassLikeType) {
        Lazy lazy = LazyKt.lazy(new Function0<ConeTypeProjection[]>() { // from class: org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor$substituteArguments$newArguments$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ConeTypeProjection[] invoke() {
                return new ConeTypeProjection[coneClassLikeType.getTypeArguments().length];
            }
        });
        ConeTypeProjection[] typeArguments = coneClassLikeType.getTypeArguments();
        int length = typeArguments.length;
        int i = 0;
        boolean z = false;
        while (true) {
            ConeTypeProjection coneTypeProjection = null;
            if (i >= length) {
                break;
            }
            ConeTypeProjection coneTypeProjection2 = typeArguments[i];
            ConeTypeProjection[] coneTypeProjectionArrSubstituteArguments$lambda$3 = substituteArguments$lambda$3(lazy);
            ConeTypeProjection coneTypeProjectionSubstituteArgument = substituteArgument(coneTypeProjection2, i);
            if (coneTypeProjectionSubstituteArgument != null) {
                Unit unit = Unit.INSTANCE;
                z = true;
                coneTypeProjection = coneTypeProjectionSubstituteArgument;
            }
            coneTypeProjectionArrSubstituteArguments$lambda$3[i] = coneTypeProjection;
            i++;
        }
        if (!z) {
            return null;
        }
        ConeTypeProjection[] typeArguments2 = coneClassLikeType.getTypeArguments();
        int length2 = typeArguments2.length;
        for (int i2 = 0; i2 < length2; i2++) {
            ConeTypeProjection coneTypeProjection3 = typeArguments2[i2];
            if (substituteArguments$lambda$3(lazy)[i2] == null) {
                substituteArguments$lambda$3(lazy)[i2] = coneTypeProjection3;
            }
        }
        if (coneClassLikeType instanceof ConeClassLikeTypeImpl) {
            return new ConeClassLikeTypeImpl(coneClassLikeType.getLookupTag(), substituteArguments$lambda$3(lazy), coneClassLikeType.getNullability().isNullable(), coneClassLikeType.getAttributes());
        }
        if (coneClassLikeType instanceof ConeErrorType) {
            ConeErrorType coneErrorType = (ConeErrorType) coneClassLikeType;
            return new ConeErrorType(coneErrorType.getDiagnostic(), coneErrorType.getIsUninferredParameter(), substituteArguments$lambda$3(lazy), coneClassLikeType.getAttributes());
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unknown class-like type to substitute, " + Reflection.getOrCreateKotlinClass(coneClassLikeType.getClass()), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, ModuleXmlParser.TYPE, coneClassLikeType);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    private static final ConeTypeProjection[] substituteArguments$lambda$3(Lazy<ConeTypeProjection[]> lazy) {
        return lazy.getValue();
    }
}
