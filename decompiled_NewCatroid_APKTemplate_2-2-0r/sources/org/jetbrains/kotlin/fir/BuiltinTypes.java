package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitAnnotationTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitAnyTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBooleanTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitByteTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitCharIteratorTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitCharSequenceTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitCharTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitDoubleTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitEnumTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitFloatTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitIntTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitLongTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitNothingTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitNullableAnyTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitNullableNothingTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitNumberTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitShortTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitStringTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitThrowableTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUIntTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUnitTypeRef;

/* JADX INFO: compiled from: FirSession.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0011\u0010\u001b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0006R\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006R\u0011\u0010#\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0006R\u0011\u0010%\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0006R\u0011\u0010'\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0006R\u0011\u0010)\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0006R\u0011\u0010+\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0006R\u0011\u0010-\u001a\u00020.¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0011\u00101\u001a\u000202¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0011\u00105\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0006¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/fir/BuiltinTypes;", "", "()V", "annotationType", "Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitBuiltinTypeRef;", "getAnnotationType", "()Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitBuiltinTypeRef;", SchemaSymbols.ATTVAL_ANYTYPE, "getAnyType", "booleanType", "getBooleanType", "byteType", "getByteType", "charIteratorType", "Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitCharIteratorTypeRef;", "getCharIteratorType", "()Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitCharIteratorTypeRef;", "charSequenceType", "Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitCharSequenceTypeRef;", "getCharSequenceType", "()Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitCharSequenceTypeRef;", "charType", "getCharType", "doubleType", "getDoubleType", "enumType", "getEnumType", "floatType", "getFloatType", "intType", "getIntType", "longType", "getLongType", "nothingType", "getNothingType", "nullableAnyType", "getNullableAnyType", "nullableNothingType", "getNullableNothingType", "numberType", "getNumberType", "shortType", "getShortType", "stringType", "getStringType", "throwableType", "Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitThrowableTypeRef;", "getThrowableType", "()Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitThrowableTypeRef;", "uIntType", "Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitUIntTypeRef;", "getUIntType", "()Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitUIntTypeRef;", "unitType", "getUnitType", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltinTypes {
    private final FirImplicitBuiltinTypeRef unitType = new FirImplicitUnitTypeRef(null);
    private final FirImplicitBuiltinTypeRef anyType = new FirImplicitAnyTypeRef(null);
    private final FirImplicitBuiltinTypeRef nullableAnyType = new FirImplicitNullableAnyTypeRef(null);
    private final FirImplicitBuiltinTypeRef enumType = new FirImplicitEnumTypeRef(null);
    private final FirImplicitBuiltinTypeRef annotationType = new FirImplicitAnnotationTypeRef(null);
    private final FirImplicitBuiltinTypeRef booleanType = new FirImplicitBooleanTypeRef(null);
    private final FirImplicitBuiltinTypeRef numberType = new FirImplicitNumberTypeRef(null);
    private final FirImplicitBuiltinTypeRef byteType = new FirImplicitByteTypeRef(null);
    private final FirImplicitBuiltinTypeRef shortType = new FirImplicitShortTypeRef(null);
    private final FirImplicitBuiltinTypeRef intType = new FirImplicitIntTypeRef(null);
    private final FirImplicitBuiltinTypeRef longType = new FirImplicitLongTypeRef(null);
    private final FirImplicitBuiltinTypeRef doubleType = new FirImplicitDoubleTypeRef(null);
    private final FirImplicitBuiltinTypeRef floatType = new FirImplicitFloatTypeRef(null);
    private final FirImplicitUIntTypeRef uIntType = new FirImplicitUIntTypeRef(null);
    private final FirImplicitBuiltinTypeRef nothingType = new FirImplicitNothingTypeRef(null);
    private final FirImplicitBuiltinTypeRef nullableNothingType = new FirImplicitNullableNothingTypeRef(null);
    private final FirImplicitBuiltinTypeRef charType = new FirImplicitCharTypeRef(null);
    private final FirImplicitBuiltinTypeRef stringType = new FirImplicitStringTypeRef(null);
    private final FirImplicitThrowableTypeRef throwableType = new FirImplicitThrowableTypeRef(null);
    private final FirImplicitCharSequenceTypeRef charSequenceType = new FirImplicitCharSequenceTypeRef(null);
    private final FirImplicitCharIteratorTypeRef charIteratorType = new FirImplicitCharIteratorTypeRef(null);

    public final FirImplicitBuiltinTypeRef getUnitType() {
        return this.unitType;
    }

    public final FirImplicitBuiltinTypeRef getAnyType() {
        return this.anyType;
    }

    public final FirImplicitBuiltinTypeRef getNullableAnyType() {
        return this.nullableAnyType;
    }

    public final FirImplicitBuiltinTypeRef getEnumType() {
        return this.enumType;
    }

    public final FirImplicitBuiltinTypeRef getAnnotationType() {
        return this.annotationType;
    }

    public final FirImplicitBuiltinTypeRef getBooleanType() {
        return this.booleanType;
    }

    public final FirImplicitBuiltinTypeRef getNumberType() {
        return this.numberType;
    }

    public final FirImplicitBuiltinTypeRef getByteType() {
        return this.byteType;
    }

    public final FirImplicitBuiltinTypeRef getShortType() {
        return this.shortType;
    }

    public final FirImplicitBuiltinTypeRef getIntType() {
        return this.intType;
    }

    public final FirImplicitBuiltinTypeRef getLongType() {
        return this.longType;
    }

    public final FirImplicitBuiltinTypeRef getDoubleType() {
        return this.doubleType;
    }

    public final FirImplicitBuiltinTypeRef getFloatType() {
        return this.floatType;
    }

    public final FirImplicitUIntTypeRef getUIntType() {
        return this.uIntType;
    }

    public final FirImplicitBuiltinTypeRef getNothingType() {
        return this.nothingType;
    }

    public final FirImplicitBuiltinTypeRef getNullableNothingType() {
        return this.nullableNothingType;
    }

    public final FirImplicitBuiltinTypeRef getCharType() {
        return this.charType;
    }

    public final FirImplicitBuiltinTypeRef getStringType() {
        return this.stringType;
    }

    public final FirImplicitThrowableTypeRef getThrowableType() {
        return this.throwableType;
    }

    public final FirImplicitCharSequenceTypeRef getCharSequenceType() {
        return this.charSequenceType;
    }

    public final FirImplicitCharIteratorTypeRef getCharIteratorType() {
        return this.charIteratorType;
    }
}
