package org.jetbrains.kotlin.fir;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: Primitives.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u001a\u0016\u0010\u0004\u001a\u00020\u0005*\u00020\u00022\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0002\u001a\n\u0010\b\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\b\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\b\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\u000b\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\u000b\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\u000b\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\f\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\f\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\f\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\r\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\r\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\r\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\u000e\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\u000e\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\u000e\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\u000f\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\u000f\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\u000f\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\u0010\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\u0010\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\u0010\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\u0011\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\u0011\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\u0011\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\u0012\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\u0012\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\u0012\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\u0013\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\u0013\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\u0013\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\u0014\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\u0014\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\u0014\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\u0015\u001a\u00020\u0007*\u00020\t\u001a\u000e\u0010\u0015\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\u0015\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\u0016\u001a\u00020\u0007*\u00020\u0005\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"PRIMITIVE_NUMBER_CLASS_IDS", "", "Lorg/jetbrains/kotlin/name/ClassId;", "PRIMITIVE_UNSIGNED_NUMBER_CLASS_IDS", "createType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "isNullable", "", "isBoolean", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "isByte", Namer.IS_CHAR, "isDouble", "isFloat", "isInt", "isLong", "isPrimitiveNumberOrUnsignedNumberType", "isPrimitiveNumberType", "isPrimitiveType", "isPrimitiveUnsignedNumberType", "isShort", "isULong", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PrimitivesKt {
    private static final Set<ClassId> PRIMITIVE_NUMBER_CLASS_IDS = SetsKt.setOf((Object[]) new ClassId[]{StandardClassIds.INSTANCE.getDouble(), StandardClassIds.INSTANCE.getFloat(), StandardClassIds.INSTANCE.getLong(), StandardClassIds.INSTANCE.getInt(), StandardClassIds.INSTANCE.getShort(), StandardClassIds.INSTANCE.getByte()});
    private static final Set<ClassId> PRIMITIVE_UNSIGNED_NUMBER_CLASS_IDS = SetsKt.setOf((Object[]) new ClassId[]{StandardClassIds.INSTANCE.getULong(), StandardClassIds.INSTANCE.getUInt(), StandardClassIds.INSTANCE.getUShort(), StandardClassIds.INSTANCE.getUByte()});

    static /* synthetic */ ConeClassLikeType createType$default(ClassId classId, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return createType(classId, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ConeClassLikeType createType(ClassId classId, boolean z) {
        return new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(classId), new ConeTypeProjection[0], z, null, 8, null);
    }

    public static final boolean isDouble(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getDouble());
    }

    public static final boolean isFloat(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getFloat());
    }

    public static final boolean isLong(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getLong());
    }

    public static final boolean isInt(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getInt());
    }

    public static final boolean isShort(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getShort());
    }

    public static final boolean isByte(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getByte());
    }

    public static final boolean isBoolean(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getBoolean());
    }

    public static final boolean isChar(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getChar());
    }

    public static final boolean isULong(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getULong());
    }

    public static final boolean isPrimitiveType(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return isPrimitiveNumberOrUnsignedNumberType(coneClassLikeType) || isBoolean(coneClassLikeType) || isByte(coneClassLikeType) || isShort(coneClassLikeType) || isChar(coneClassLikeType);
    }

    public static final boolean isPrimitiveNumberType(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return PRIMITIVE_NUMBER_CLASS_IDS.contains(coneClassLikeType.getLookupTag().getClassId());
    }

    public static final boolean isPrimitiveUnsignedNumberType(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return PRIMITIVE_UNSIGNED_NUMBER_CLASS_IDS.contains(coneClassLikeType.getLookupTag().getClassId());
    }

    public static final boolean isPrimitiveNumberOrUnsignedNumberType(ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        return isPrimitiveNumberType(coneClassLikeType) || isPrimitiveUnsignedNumberType(coneClassLikeType);
    }

    public static final boolean isDouble(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return Intrinsics.areEqual(FirDeclarationUtilKt.getClassId(firClass), StandardClassIds.INSTANCE.getDouble());
    }

    public static final boolean isFloat(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return Intrinsics.areEqual(FirDeclarationUtilKt.getClassId(firClass), StandardClassIds.INSTANCE.getFloat());
    }

    public static final boolean isLong(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return Intrinsics.areEqual(FirDeclarationUtilKt.getClassId(firClass), StandardClassIds.INSTANCE.getLong());
    }

    public static final boolean isInt(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return Intrinsics.areEqual(FirDeclarationUtilKt.getClassId(firClass), StandardClassIds.INSTANCE.getInt());
    }

    public static final boolean isShort(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return Intrinsics.areEqual(FirDeclarationUtilKt.getClassId(firClass), StandardClassIds.INSTANCE.getShort());
    }

    public static final boolean isByte(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return Intrinsics.areEqual(FirDeclarationUtilKt.getClassId(firClass), StandardClassIds.INSTANCE.getByte());
    }

    public static final boolean isBoolean(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return Intrinsics.areEqual(FirDeclarationUtilKt.getClassId(firClass), StandardClassIds.INSTANCE.getBoolean());
    }

    public static final boolean isChar(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return Intrinsics.areEqual(FirDeclarationUtilKt.getClassId(firClass), StandardClassIds.INSTANCE.getChar());
    }

    public static final boolean isPrimitiveType(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return isPrimitiveNumberOrUnsignedNumberType(firClass) || isBoolean(firClass) || isByte(firClass) || isShort(firClass) || isChar(firClass);
    }

    public static final boolean isPrimitiveNumberType(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return PRIMITIVE_NUMBER_CLASS_IDS.contains(FirDeclarationUtilKt.getClassId(firClass));
    }

    public static final boolean isPrimitiveUnsignedNumberType(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return PRIMITIVE_UNSIGNED_NUMBER_CLASS_IDS.contains(FirDeclarationUtilKt.getClassId(firClass));
    }

    public static final boolean isPrimitiveNumberOrUnsignedNumberType(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return isPrimitiveNumberType(firClass) || isPrimitiveUnsignedNumberType(firClass);
    }

    public static final boolean isDouble(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return Intrinsics.areEqual(firClassSymbol.getClassId(), StandardClassIds.INSTANCE.getDouble());
    }

    public static final boolean isFloat(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return Intrinsics.areEqual(firClassSymbol.getClassId(), StandardClassIds.INSTANCE.getFloat());
    }

    public static final boolean isLong(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return Intrinsics.areEqual(firClassSymbol.getClassId(), StandardClassIds.INSTANCE.getLong());
    }

    public static final boolean isInt(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return Intrinsics.areEqual(firClassSymbol.getClassId(), StandardClassIds.INSTANCE.getInt());
    }

    public static final boolean isShort(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return Intrinsics.areEqual(firClassSymbol.getClassId(), StandardClassIds.INSTANCE.getShort());
    }

    public static final boolean isByte(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return Intrinsics.areEqual(firClassSymbol.getClassId(), StandardClassIds.INSTANCE.getByte());
    }

    public static final boolean isBoolean(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return Intrinsics.areEqual(firClassSymbol.getClassId(), StandardClassIds.INSTANCE.getBoolean());
    }

    public static final boolean isChar(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return Intrinsics.areEqual(firClassSymbol.getClassId(), StandardClassIds.INSTANCE.getChar());
    }

    public static final boolean isPrimitiveType(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return isPrimitiveNumberOrUnsignedNumberType(firClassSymbol) || isBoolean(firClassSymbol) || isByte(firClassSymbol) || isShort(firClassSymbol) || isChar(firClassSymbol);
    }

    public static final boolean isPrimitiveNumberType(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return PRIMITIVE_NUMBER_CLASS_IDS.contains(firClassSymbol.getClassId());
    }

    public static final boolean isPrimitiveUnsignedNumberType(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return PRIMITIVE_UNSIGNED_NUMBER_CLASS_IDS.contains(firClassSymbol.getClassId());
    }

    public static final boolean isPrimitiveNumberOrUnsignedNumberType(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return isPrimitiveNumberType(firClassSymbol) || isPrimitiveUnsignedNumberType(firClassSymbol);
    }
}
