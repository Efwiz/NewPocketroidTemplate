package org.jetbrains.kotlin.fir.types;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: FirTypeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000t\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a-\u00106\u001a\u0004\u0018\u0001H7\"\n\b\u0000\u00107\u0018\u0001*\u00020\u0002*\u00020\u0005H\u0086\b\u0082\u0002\n\n\b\b\u0002\u001a\u0004\u0010\u0000(\u0000¢\u0006\u0002\u0010\t\u001a\u001e\u00109\u001a\u0002H7\"\n\b\u0000\u00107\u0018\u0001*\u00020\u0002*\u00020\u0005H\u0086\b¢\u0006\u0002\u0010\t\u001a\u0016\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00160;*\b\u0012\u0004\u0012\u00020\u00160;\u001a\u001c\u0010<\u001a\u00020\u0001*\u00020\u00052\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020\u0001H\u0002\u001a\u0014\u0010@\u001a\u00020\u0001*\u00020\u00162\u0006\u0010=\u001a\u00020>H\u0002\u001a\u001b\u0010A\u001a\u00020\u0001*\u00020\"\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0001\u001a\n\u0010C\u001a\u00020D*\u00020E\u001a\u0010\u0010F\u001a\b\u0012\u0002\b\u0003\u0018\u00010G*\u00020H\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006\"\u0015\u0010\u0007\u001a\u00020\u0002*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u0017\u0010\n\u001a\u0004\u0018\u00010\u0002*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t\"\u001e\u0010\f\u001a\u00020\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b\r\u0010\u000e\u001a\u0004\b\f\u0010\u0006\"\u001e\u0010\u000f\u001a\u00020\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u000e\u001a\u0004\b\u000f\u0010\u0006\"\u001e\u0010\u0011\u001a\u00020\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0012\u0010\u000e\u001a\u0004\b\u0011\u0010\u0006\"\u001e\u0010\u0013\u001a\u00020\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u000e\u001a\u0004\b\u0013\u0010\u0006\"\u0015\u0010\u0015\u001a\u00020\u0001*\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017\"\u001e\u0010\u0018\u001a\u00020\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\u000e\u001a\u0004\b\u0018\u0010\u0006\"\u0015\u0010\u001a\u001a\u00020\u0001*\u00020\u001b8F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001c\"\u0017\u0010\u001d\u001a\u0004\u0018\u00010\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\"\u001e\u0010\u001f\u001a\u00020\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b \u0010\u000e\u001a\u0004\b\u001f\u0010\u0006\"\u0015\u0010!\u001a\u00020\u0001*\u00020\"8F¢\u0006\u0006\u001a\u0004\b!\u0010#\"\u001e\u0010$\u001a\u00020\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b%\u0010\u000e\u001a\u0004\b$\u0010\u0006\"\u001e\u0010&\u001a\u00020\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b'\u0010\u000e\u001a\u0004\b&\u0010\u0006\"\u0015\u0010(\u001a\u00020\u0001*\u00020\u001b8F¢\u0006\u0006\u001a\u0004\b(\u0010\u001c\"\u001e\u0010)\u001a\u00020\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b*\u0010\u000e\u001a\u0004\b)\u0010\u0006\"\u001e\u0010+\u001a\u00020\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b,\u0010\u000e\u001a\u0004\b+\u0010\u0006\"\u001a\u0010-\u001a\u0004\u0018\u00010.*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b/\u00100\"\u0015\u00101\u001a\u000202*\u0002038F¢\u0006\u0006\u001a\u0004\b4\u00105ò\u0001\b\n\u000208\n\u00020B¨\u0006I"}, d2 = {"canBeNull", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getCanBeNull", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Z", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)Z", "coneType", "getConeType", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "coneTypeOrNull", "getConeTypeOrNull", "isAny", "isAny$annotations", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "isArrayType", "isArrayType$annotations", "isBoolean", "isBoolean$annotations", "isEnum", "isEnum$annotations", "isExtensionFunctionAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;)Z", "isInt", "isInt$annotations", "isLeftValidForDefinitelyNotNullable", "Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;)Z", "isMarkedNullable", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)Ljava/lang/Boolean;", "isNothing", "isNothing$annotations", "isNullLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Z", "isNullableAny", "isNullableAny$annotations", "isNullableNothing", "isNullableNothing$annotations", "isRightValidForDefinitelyNotNullable", "isString", "isString$annotations", "isUnit", "isUnit$annotations", "lookupTagBasedOrNull", "Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "getLookupTagBasedOrNull", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "parametersCount", "", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "getParametersCount", "(Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;)I", "coneTypeSafe", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "coneTypeUnsafe", "dropExtensionFunctionAnnotation", "", "isBuiltinType", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "isNullable", "isOfType", "isStableSmartcast", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "toConeTypeProjection", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "toConstKind", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirTypeUtilsKt {
    @UnexpandedTypeCheck
    public static /* synthetic */ void isAny$annotations(FirTypeRef firTypeRef) {
    }

    @UnexpandedTypeCheck
    public static /* synthetic */ void isArrayType$annotations(FirTypeRef firTypeRef) {
    }

    @UnexpandedTypeCheck
    public static /* synthetic */ void isBoolean$annotations(FirTypeRef firTypeRef) {
    }

    @UnexpandedTypeCheck
    public static /* synthetic */ void isEnum$annotations(FirTypeRef firTypeRef) {
    }

    @UnexpandedTypeCheck
    public static /* synthetic */ void isInt$annotations(FirTypeRef firTypeRef) {
    }

    @UnexpandedTypeCheck
    public static /* synthetic */ void isNothing$annotations(FirTypeRef firTypeRef) {
    }

    @UnexpandedTypeCheck
    public static /* synthetic */ void isNullableAny$annotations(FirTypeRef firTypeRef) {
    }

    @UnexpandedTypeCheck
    public static /* synthetic */ void isNullableNothing$annotations(FirTypeRef firTypeRef) {
    }

    @UnexpandedTypeCheck
    public static /* synthetic */ void isString$annotations(FirTypeRef firTypeRef) {
    }

    @UnexpandedTypeCheck
    public static /* synthetic */ void isUnit$annotations(FirTypeRef firTypeRef) {
    }

    public static final /* synthetic */ <T extends ConeKotlinType> T coneTypeUnsafe(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        T t = (T) ((FirResolvedTypeRef) firTypeRef).getType();
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        return t;
    }

    public static final /* synthetic */ <T extends ConeKotlinType> T coneTypeSafe(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        T t = null;
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        if (firResolvedTypeRef != null) {
            t = (T) firResolvedTypeRef.getType();
        }
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return t;
    }

    public static final boolean isAny(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        return isBuiltinType(firTypeRef, StandardClassIds.INSTANCE.getAny(), false);
    }

    public static final boolean isNullableAny(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        return isBuiltinType(firTypeRef, StandardClassIds.INSTANCE.getAny(), true);
    }

    public static final boolean isNothing(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        return isBuiltinType(firTypeRef, StandardClassIds.INSTANCE.getNothing(), false);
    }

    public static final boolean isNullableNothing(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        return isBuiltinType(firTypeRef, StandardClassIds.INSTANCE.getNothing(), true);
    }

    public static final boolean isUnit(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        return isBuiltinType(firTypeRef, StandardClassIds.INSTANCE.getUnit(), false);
    }

    public static final boolean isBoolean(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        return isBuiltinType(firTypeRef, StandardClassIds.INSTANCE.getBoolean(), false);
    }

    public static final boolean isInt(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        return isBuiltinType(firTypeRef, StandardClassIds.INSTANCE.getInt(), false);
    }

    public static final boolean isString(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        return isBuiltinType(firTypeRef, StandardClassIds.INSTANCE.getString(), false);
    }

    public static final boolean isEnum(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        return isBuiltinType(firTypeRef, StandardClassIds.INSTANCE.getEnum(), false);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0067  */
    /* JADX WARN: Code duplicated, block: B:28:0x0073 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:29:? A[LOOP:0: B:21:0x0061->B:29:?, LOOP_END, SYNTHETIC] */
    public static final boolean isArrayType(FirTypeRef firTypeRef) {
        Collection<ClassId> collectionValues;
        Iterator<T> it;
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        if (!isBuiltinType(firTypeRef, StandardClassIds.INSTANCE.getArray(), false)) {
            Collection<ClassId> collectionValues2 = StandardClassIds.INSTANCE.getPrimitiveArrayTypeByElementType().values();
            if ((collectionValues2 instanceof Collection) && collectionValues2.isEmpty()) {
                collectionValues = StandardClassIds.INSTANCE.getUnsignedArrayTypeByElementType().values();
                if (!(collectionValues instanceof Collection)) {
                }
                it = collectionValues.iterator();
                while (it.hasNext()) {
                    if (isBuiltinType(firTypeRef, (ClassId) it.next(), false)) {
                    }
                }
                return false;
            }
            Iterator<T> it2 = collectionValues2.iterator();
            while (it2.hasNext()) {
                if (isBuiltinType(firTypeRef, (ClassId) it2.next(), false)) {
                }
            }
            collectionValues = StandardClassIds.INSTANCE.getUnsignedArrayTypeByElementType().values();
            if (!(collectionValues instanceof Collection) && collectionValues.isEmpty()) {
                return false;
            }
            it = collectionValues.iterator();
            while (it.hasNext()) {
                if (isBuiltinType(firTypeRef, (ClassId) it.next(), false)) {
                }
            }
            return false;
        }
        return true;
    }

    public static final boolean isNullLiteral(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        if (firExpression instanceof FirConstExpression) {
            FirConstExpression firConstExpression = (FirConstExpression) firExpression;
            if (Intrinsics.areEqual(firConstExpression.getKind(), ConstantValueKind.Null.INSTANCE) && firConstExpression.getValue() == null && firExpression.getSource() != null) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isStableSmartcast(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        return (firExpression instanceof FirSmartCastExpression) && ((FirSmartCastExpression) firExpression).isStable();
    }

    private static final ConeLookupTagBasedType getLookupTagBasedOrNull(FirTypeRef firTypeRef) {
        if (firTypeRef instanceof FirImplicitBuiltinTypeRef) {
            return ((FirImplicitBuiltinTypeRef) firTypeRef).getType();
        }
        if (firTypeRef instanceof FirResolvedTypeRef) {
            ConeKotlinType type = ((FirResolvedTypeRef) firTypeRef).getType();
            if (type instanceof ConeLookupTagBasedType) {
                return (ConeLookupTagBasedType) type;
            }
        }
        return null;
    }

    private static final boolean isBuiltinType(FirTypeRef firTypeRef, ClassId classId, boolean z) {
        ConeClassLikeLookupTag lookupTag;
        ConeLookupTagBasedType lookupTagBasedOrNull = getLookupTagBasedOrNull(firTypeRef);
        if (lookupTagBasedOrNull == null) {
            return false;
        }
        ClassId classId2 = null;
        ConeClassLikeType coneClassLikeType = lookupTagBasedOrNull instanceof ConeClassLikeType ? (ConeClassLikeType) lookupTagBasedOrNull : null;
        if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
            classId2 = lookupTag.getClassId();
        }
        return Intrinsics.areEqual(classId2, classId) && ConeTypeUtilsKt.isNullable(lookupTagBasedOrNull) == z;
    }

    public static final Boolean isMarkedNullable(FirTypeRef firTypeRef) {
        boolean zIsMarkedNullable;
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        if (firTypeRef instanceof FirTypeRefWithNullability) {
            zIsMarkedNullable = ((FirTypeRefWithNullability) firTypeRef).getIsMarkedNullable();
        } else {
            ConeLookupTagBasedType lookupTagBasedOrNull = getLookupTagBasedOrNull(firTypeRef);
            if (lookupTagBasedOrNull == null) {
                return null;
            }
            zIsMarkedNullable = ConeTypeUtilsKt.isMarkedNullable(lookupTagBasedOrNull);
        }
        return Boolean.valueOf(zIsMarkedNullable);
    }

    public static final int getParametersCount(FirFunctionTypeRef firFunctionTypeRef) {
        Intrinsics.checkNotNullParameter(firFunctionTypeRef, "<this>");
        if (firFunctionTypeRef.getReceiverTypeRef() != null) {
            return firFunctionTypeRef.getParameters().size() + firFunctionTypeRef.getContextReceiverTypeRefs().size() + 1;
        }
        return firFunctionTypeRef.getParameters().size() + firFunctionTypeRef.getContextReceiverTypeRefs().size();
    }

    private static final boolean isOfType(FirAnnotation firAnnotation, ClassId classId) {
        FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        if (firResolvedTypeRef == null) {
            return false;
        }
        ConeKotlinType type = firResolvedTypeRef.getType();
        ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
        return Intrinsics.areEqual((Object) (coneClassLikeType != null ? Boolean.valueOf(Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), classId)) : null), (Object) true);
    }

    public static final boolean isExtensionFunctionAnnotationCall(FirAnnotation firAnnotation) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        return isOfType(firAnnotation, StandardClassIds.Annotations.INSTANCE.getExtensionFunctionType());
    }

    public static final List<FirAnnotation> dropExtensionFunctionAnnotation(List<? extends FirAnnotation> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!isExtensionFunctionAnnotationCall((FirAnnotation) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final ConstantValueKind<?> toConstKind(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        ClassId classId = coneClassLikeType.getLookupTag().getClassId();
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getByte())) {
            return ConstantValueKind.Byte.INSTANCE;
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getShort())) {
            return ConstantValueKind.Short.INSTANCE;
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getInt())) {
            return ConstantValueKind.Int.INSTANCE;
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getLong())) {
            return ConstantValueKind.Long.INSTANCE;
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getUInt())) {
            return ConstantValueKind.UnsignedInt.INSTANCE;
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getULong())) {
            return ConstantValueKind.UnsignedLong.INSTANCE;
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getUShort())) {
            return ConstantValueKind.UnsignedShort.INSTANCE;
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getUByte())) {
            return ConstantValueKind.UnsignedByte.INSTANCE;
        }
        return null;
    }

    public static final ConeTypeProjection toConeTypeProjection(FirTypeProjection firTypeProjection) {
        Intrinsics.checkNotNullParameter(firTypeProjection, "<this>");
        if (firTypeProjection instanceof FirStarProjection) {
            return ConeStarProjection.INSTANCE;
        }
        if (firTypeProjection instanceof FirTypeProjectionWithVariance) {
            FirTypeProjectionWithVariance firTypeProjectionWithVariance = (FirTypeProjectionWithVariance) firTypeProjection;
            return ConeTypeUtilsKt.toTypeProjection(getConeType(firTypeProjectionWithVariance.getTypeRef()), firTypeProjectionWithVariance.getVariance());
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unexpected " + Reflection.getOrCreateKotlinClass(firTypeProjection.getClass()).getSimpleName(), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "projection", firTypeProjection);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final boolean getCanBeNull(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        return getCanBeNull(getConeType(firTypeRef));
    }

    public static final boolean getCanBeNull(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        if (ConeTypeUtilsKt.isMarkedNullable(coneKotlinType)) {
            return true;
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            return getCanBeNull(((ConeFlexibleType) coneKotlinType).getUpperBound());
        }
        if (!(coneKotlinType instanceof ConeDefinitelyNotNullType)) {
            if (!(coneKotlinType instanceof ConeTypeParameterType)) {
                if (!(coneKotlinType instanceof ConeIntersectionType)) {
                    return ConeTypeUtilsKt.isNullable(coneKotlinType);
                }
                Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes();
                if ((intersectedTypes instanceof Collection) && intersectedTypes.isEmpty()) {
                    return true;
                }
                Iterator<T> it = intersectedTypes.iterator();
                while (it.hasNext()) {
                    if (!getCanBeNull((ConeKotlinType) it.next())) {
                    }
                }
                return true;
            }
            List<FirResolvedTypeRef> resolvedBounds = ((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol().getResolvedBounds();
            if ((resolvedBounds instanceof Collection) && resolvedBounds.isEmpty()) {
                return true;
            }
            Iterator<T> it2 = resolvedBounds.iterator();
            while (it2.hasNext()) {
                if (!getCanBeNull(getConeType((FirResolvedTypeRef) it2.next()))) {
                }
            }
            return true;
        }
        return false;
    }

    public static final boolean isLeftValidForDefinitelyNotNullable(FirIntersectionTypeRef firIntersectionTypeRef) {
        Intrinsics.checkNotNullParameter(firIntersectionTypeRef, "<this>");
        ConeKotlinType coneType = getConeType(firIntersectionTypeRef.getLeftType());
        return (coneType instanceof ConeTypeParameterType) && getCanBeNull(coneType) && !ConeTypeUtilsKt.isMarkedNullable(coneType);
    }

    public static final boolean isRightValidForDefinitelyNotNullable(FirIntersectionTypeRef firIntersectionTypeRef) {
        Intrinsics.checkNotNullParameter(firIntersectionTypeRef, "<this>");
        return ConeBuiltinTypeUtilsKt.isAny(getConeType(firIntersectionTypeRef.getRightType()));
    }

    public static final ConeKotlinType getConeType(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        if (type != null) {
            return type;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Expected " + Reflection.getOrCreateKotlinClass(FirResolvedTypeRef.class).getSimpleName() + " with " + Reflection.getOrCreateKotlinClass(ConeKotlinType.class).getSimpleName() + " but was " + Reflection.getOrCreateKotlinClass(firTypeRef.getClass()).getSimpleName(), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "typeRef", firTypeRef);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final ConeKotlinType getConeTypeOrNull(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (type instanceof ConeKotlinType) {
            return type;
        }
        return null;
    }
}
