package org.jetbrains.kotlin.resolve.constants.evaluate;

import com.gaurav.avnc.vnc.XKeySym;
import java.math.BigInteger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: OperationsMapGenerated.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\u001a0\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0001\u001a0\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\n\u001a \u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\n¨\u0006\u000e"}, d2 = {"checkBinaryOp", "Ljava/math/BigInteger;", "name", "", "leftType", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/CompileTimeType;", "left", "rightType", "right", "evalBinaryOp", "", "evalUnaryOp", ModuleXmlParser.TYPE, "value", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OperationsMapGeneratedKt {

    /* JADX INFO: compiled from: OperationsMapGenerated.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CompileTimeType.values().length];
            try {
                iArr[CompileTimeType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CompileTimeType.BYTE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CompileTimeType.CHAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CompileTimeType.DOUBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[CompileTimeType.FLOAT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[CompileTimeType.INT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[CompileTimeType.LONG.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[CompileTimeType.SHORT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[CompileTimeType.STRING.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[CompileTimeType.ANY.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final Object evalUnaryOp(String name2, CompileTimeType type, Object value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(value, "value");
        switch (WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
            case 1:
                if (Intrinsics.areEqual(name2, "not")) {
                    return Boolean.valueOf(!((Boolean) value).booleanValue());
                }
                if (Intrinsics.areEqual(name2, "toString")) {
                    return String.valueOf(((Boolean) value).booleanValue());
                }
                return null;
            case 2:
                switch (name2.hashCode()) {
                    case -1776922004:
                        if (name2.equals("toString")) {
                            return String.valueOf((int) ((Byte) value).byteValue());
                        }
                        return null;
                    case -1177945951:
                        if (name2.equals("toFloat")) {
                            return Float.valueOf(((Byte) value).byteValue());
                        }
                        return null;
                    case -1166058815:
                        if (name2.equals("toShort")) {
                            return Short.valueOf(((Byte) value).byteValue());
                        }
                        return null;
                    case -869388765:
                        if (name2.equals("toByte")) {
                            return Byte.valueOf(((Byte) value).byteValue());
                        }
                        return null;
                    case -869375887:
                        if (name2.equals("toChar")) {
                            return Character.valueOf((char) ((Byte) value).byteValue());
                        }
                        return null;
                    case -869100649:
                        if (name2.equals("toLong")) {
                            return Long.valueOf(((Byte) value).byteValue());
                        }
                        return null;
                    case 110508916:
                        if (name2.equals("toInt")) {
                            return Integer.valueOf(((Byte) value).byteValue());
                        }
                        return null;
                    case 414819457:
                        if (name2.equals("unaryMinus")) {
                            return Integer.valueOf(-((Byte) value).byteValue());
                        }
                        return null;
                    case 983305065:
                        if (name2.equals("unaryPlus")) {
                            return Integer.valueOf(((Byte) value).byteValue());
                        }
                        return null;
                    case 2084073004:
                        if (name2.equals("toDouble")) {
                            return Double.valueOf(((Byte) value).byteValue());
                        }
                        return null;
                    default:
                        return null;
                }
            case 3:
                switch (name2.hashCode()) {
                    case -1776922004:
                        if (name2.equals("toString")) {
                            return String.valueOf(((Character) value).charValue());
                        }
                        return null;
                    case -1177945951:
                        if (name2.equals("toFloat")) {
                            return Float.valueOf(((Character) value).charValue());
                        }
                        return null;
                    case -1166058815:
                        if (name2.equals("toShort")) {
                            return Short.valueOf((short) ((Character) value).charValue());
                        }
                        return null;
                    case -869388765:
                        if (name2.equals("toByte")) {
                            return Byte.valueOf((byte) ((Character) value).charValue());
                        }
                        return null;
                    case -869375887:
                        if (name2.equals("toChar")) {
                            return Character.valueOf(((Character) value).charValue());
                        }
                        return null;
                    case -869100649:
                        if (name2.equals("toLong")) {
                            return Long.valueOf(((Character) value).charValue());
                        }
                        return null;
                    case 3059181:
                        if (name2.equals(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY)) {
                            return Integer.valueOf(((Character) value).charValue());
                        }
                        return null;
                    case 110508916:
                        if (name2.equals("toInt")) {
                            return Integer.valueOf(((Character) value).charValue());
                        }
                        return null;
                    case 2084073004:
                        if (name2.equals("toDouble")) {
                            return Double.valueOf(((Character) value).charValue());
                        }
                        return null;
                    default:
                        return null;
                }
            case 4:
                switch (name2.hashCode()) {
                    case -1776922004:
                        if (name2.equals("toString")) {
                            return String.valueOf(((Double) value).doubleValue());
                        }
                        return null;
                    case -1177945951:
                        if (name2.equals("toFloat")) {
                            return Float.valueOf((float) ((Double) value).doubleValue());
                        }
                        return null;
                    case -1166058815:
                        if (name2.equals("toShort")) {
                            return Short.valueOf((short) ((Double) value).doubleValue());
                        }
                        return null;
                    case -869388765:
                        if (name2.equals("toByte")) {
                            return Byte.valueOf((byte) ((Double) value).doubleValue());
                        }
                        return null;
                    case -869375887:
                        if (name2.equals("toChar")) {
                            return Character.valueOf((char) ((Double) value).doubleValue());
                        }
                        return null;
                    case -869100649:
                        if (name2.equals("toLong")) {
                            return Long.valueOf((long) ((Double) value).doubleValue());
                        }
                        return null;
                    case 110508916:
                        if (name2.equals("toInt")) {
                            return Integer.valueOf((int) ((Double) value).doubleValue());
                        }
                        return null;
                    case 414819457:
                        if (name2.equals("unaryMinus")) {
                            return Double.valueOf(-((Double) value).doubleValue());
                        }
                        return null;
                    case 983305065:
                        if (name2.equals("unaryPlus")) {
                            return Double.valueOf(((Double) value).doubleValue());
                        }
                        return null;
                    case 2084073004:
                        if (name2.equals("toDouble")) {
                            return Double.valueOf(((Double) value).doubleValue());
                        }
                        return null;
                    default:
                        return null;
                }
            case 5:
                switch (name2.hashCode()) {
                    case -1776922004:
                        if (name2.equals("toString")) {
                            return String.valueOf(((Float) value).floatValue());
                        }
                        return null;
                    case -1177945951:
                        if (name2.equals("toFloat")) {
                            return Float.valueOf(((Float) value).floatValue());
                        }
                        return null;
                    case -1166058815:
                        if (name2.equals("toShort")) {
                            return Short.valueOf((short) ((Float) value).floatValue());
                        }
                        return null;
                    case -869388765:
                        if (name2.equals("toByte")) {
                            return Byte.valueOf((byte) ((Float) value).floatValue());
                        }
                        return null;
                    case -869375887:
                        if (name2.equals("toChar")) {
                            return Character.valueOf((char) ((Float) value).floatValue());
                        }
                        return null;
                    case -869100649:
                        if (name2.equals("toLong")) {
                            return Long.valueOf((long) ((Float) value).floatValue());
                        }
                        return null;
                    case 110508916:
                        if (name2.equals("toInt")) {
                            return Integer.valueOf((int) ((Float) value).floatValue());
                        }
                        return null;
                    case 414819457:
                        if (name2.equals("unaryMinus")) {
                            return Float.valueOf(-((Float) value).floatValue());
                        }
                        return null;
                    case 983305065:
                        if (name2.equals("unaryPlus")) {
                            return Float.valueOf(((Float) value).floatValue());
                        }
                        return null;
                    case 2084073004:
                        if (name2.equals("toDouble")) {
                            return Double.valueOf(((Float) value).floatValue());
                        }
                        return null;
                    default:
                        return null;
                }
            case 6:
                switch (name2.hashCode()) {
                    case -1776922004:
                        if (name2.equals("toString")) {
                            return String.valueOf(((Integer) value).intValue());
                        }
                        return null;
                    case -1177945951:
                        if (name2.equals("toFloat")) {
                            return Float.valueOf(((Integer) value).intValue());
                        }
                        return null;
                    case -1166058815:
                        if (name2.equals("toShort")) {
                            return Short.valueOf((short) ((Integer) value).intValue());
                        }
                        return null;
                    case -869388765:
                        if (name2.equals("toByte")) {
                            return Byte.valueOf((byte) ((Integer) value).intValue());
                        }
                        return null;
                    case -869375887:
                        if (name2.equals("toChar")) {
                            return Character.valueOf((char) ((Integer) value).intValue());
                        }
                        return null;
                    case -869100649:
                        if (name2.equals("toLong")) {
                            return Long.valueOf(((Integer) value).intValue());
                        }
                        return null;
                    case 104433:
                        if (name2.equals("inv")) {
                            return Integer.valueOf(~((Integer) value).intValue());
                        }
                        return null;
                    case 110508916:
                        if (name2.equals("toInt")) {
                            return Integer.valueOf(((Integer) value).intValue());
                        }
                        return null;
                    case 414819457:
                        if (name2.equals("unaryMinus")) {
                            return Integer.valueOf(-((Integer) value).intValue());
                        }
                        return null;
                    case 983305065:
                        if (name2.equals("unaryPlus")) {
                            return Integer.valueOf(((Integer) value).intValue());
                        }
                        return null;
                    case 2084073004:
                        if (name2.equals("toDouble")) {
                            return Double.valueOf(((Integer) value).intValue());
                        }
                        return null;
                    default:
                        return null;
                }
            case 7:
                switch (name2.hashCode()) {
                    case -1776922004:
                        if (name2.equals("toString")) {
                            return String.valueOf(((Long) value).longValue());
                        }
                        return null;
                    case -1177945951:
                        if (name2.equals("toFloat")) {
                            return Float.valueOf(((Long) value).longValue());
                        }
                        return null;
                    case -1166058815:
                        if (name2.equals("toShort")) {
                            return Short.valueOf((short) ((Long) value).longValue());
                        }
                        return null;
                    case -869388765:
                        if (name2.equals("toByte")) {
                            return Byte.valueOf((byte) ((Long) value).longValue());
                        }
                        return null;
                    case -869375887:
                        if (name2.equals("toChar")) {
                            return Character.valueOf((char) ((Long) value).longValue());
                        }
                        return null;
                    case -869100649:
                        if (name2.equals("toLong")) {
                            return Long.valueOf(((Long) value).longValue());
                        }
                        return null;
                    case 104433:
                        if (name2.equals("inv")) {
                            return Long.valueOf(~((Long) value).longValue());
                        }
                        return null;
                    case 110508916:
                        if (name2.equals("toInt")) {
                            return Integer.valueOf((int) ((Long) value).longValue());
                        }
                        return null;
                    case 414819457:
                        if (name2.equals("unaryMinus")) {
                            return Long.valueOf(-((Long) value).longValue());
                        }
                        return null;
                    case 983305065:
                        if (name2.equals("unaryPlus")) {
                            return Long.valueOf(((Long) value).longValue());
                        }
                        return null;
                    case 2084073004:
                        if (name2.equals("toDouble")) {
                            return Double.valueOf(((Long) value).longValue());
                        }
                        return null;
                    default:
                        return null;
                }
            case 8:
                switch (name2.hashCode()) {
                    case -1776922004:
                        if (name2.equals("toString")) {
                            return String.valueOf((int) ((Short) value).shortValue());
                        }
                        return null;
                    case -1177945951:
                        if (name2.equals("toFloat")) {
                            return Float.valueOf(((Short) value).shortValue());
                        }
                        return null;
                    case -1166058815:
                        if (name2.equals("toShort")) {
                            return Short.valueOf(((Short) value).shortValue());
                        }
                        return null;
                    case -869388765:
                        if (name2.equals("toByte")) {
                            return Byte.valueOf((byte) ((Short) value).shortValue());
                        }
                        return null;
                    case -869375887:
                        if (name2.equals("toChar")) {
                            return Character.valueOf((char) ((Short) value).shortValue());
                        }
                        return null;
                    case -869100649:
                        if (name2.equals("toLong")) {
                            return Long.valueOf(((Short) value).shortValue());
                        }
                        return null;
                    case 110508916:
                        if (name2.equals("toInt")) {
                            return Integer.valueOf(((Short) value).shortValue());
                        }
                        return null;
                    case 414819457:
                        if (name2.equals("unaryMinus")) {
                            return Integer.valueOf(-((Short) value).shortValue());
                        }
                        return null;
                    case 983305065:
                        if (name2.equals("unaryPlus")) {
                            return Integer.valueOf(((Short) value).shortValue());
                        }
                        return null;
                    case 2084073004:
                        if (name2.equals("toDouble")) {
                            return Double.valueOf(((Short) value).shortValue());
                        }
                        return null;
                    default:
                        return null;
                }
            case 9:
                if (Intrinsics.areEqual(name2, "length")) {
                    return Integer.valueOf(((String) value).length());
                }
                if (Intrinsics.areEqual(name2, "toString")) {
                    return ((String) value).toString();
                }
                return null;
            default:
                return null;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final Object evalBinaryOp(String name2, CompileTimeType leftType, Object left, CompileTimeType rightType, Object right) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(leftType, "leftType");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(rightType, "rightType");
        Intrinsics.checkNotNullParameter(right, "right");
        switch (WhenMappings.$EnumSwitchMapping$0[leftType.ordinal()]) {
            case 1:
                int i = WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()];
                if (i != 1) {
                    if (i == 10 && Intrinsics.areEqual(name2, Namer.EQUALS_METHOD_NAME)) {
                        return Boolean.valueOf(((Boolean) left).equals(right));
                    }
                    Unit unit = Unit.INSTANCE;
                    return null;
                }
                int iHashCode = name2.hashCode();
                if (iHashCode != -1412718016) {
                    if (iHashCode != 3555) {
                        if (iHashCode != 96727) {
                            if (iHashCode == 118875 && name2.equals("xor")) {
                                return Boolean.valueOf(((Boolean) left).booleanValue() ^ ((Boolean) right).booleanValue());
                            }
                        } else if (name2.equals("and")) {
                            return Boolean.valueOf(((Boolean) left).booleanValue() & ((Boolean) right).booleanValue());
                        }
                    } else if (name2.equals("or")) {
                        return Boolean.valueOf(((Boolean) left).booleanValue() | ((Boolean) right).booleanValue());
                    }
                } else if (name2.equals("compareTo")) {
                    return Integer.valueOf(Boolean.compare(((Boolean) left).booleanValue(), ((Boolean) right).booleanValue()));
                }
                Unit unit2 = Unit.INSTANCE;
                return null;
            case 2:
                switch (WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()]) {
                    case 2:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare((int) ((Byte) left).byteValue(), (int) ((Byte) right).byteValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Integer.valueOf(((Byte) left).byteValue() / ((Byte) right).byteValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    byte bByteValue = ((Byte) left).byteValue();
                                    byte bByteValue2 = ((Byte) right).byteValue();
                                    int i2 = bByteValue % bByteValue2;
                                    return Byte.valueOf((byte) (i2 + (bByteValue2 & (((i2 ^ bByteValue2) & ((-i2) | i2)) >> 31))));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Integer.valueOf(((Byte) left).byteValue() % ((Byte) right).byteValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Integer.valueOf(((Byte) left).byteValue() + ((Byte) right).byteValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Integer.valueOf(((Byte) left).byteValue() - ((Byte) right).byteValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Integer.valueOf(((Byte) left).byteValue() * ((Byte) right).byteValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    byte bByteValue3 = ((Byte) left).byteValue();
                                    byte bByteValue4 = ((Byte) right).byteValue();
                                    int i3 = bByteValue3 / bByteValue4;
                                    if ((bByteValue3 ^ bByteValue4) < 0 && bByteValue4 * i3 != bByteValue3) {
                                        i3--;
                                    }
                                    return Integer.valueOf(i3);
                                }
                                break;
                        }
                        Unit unit3 = Unit.INSTANCE;
                        return null;
                    case 4:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Double.compare(((Byte) left).byteValue(), ((Double) right).doubleValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Double.valueOf(((double) ((Byte) left).byteValue()) / ((Double) right).doubleValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Double.valueOf(((double) ((Byte) left).byteValue()) % ((Double) right).doubleValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Double.valueOf(((double) ((Byte) left).byteValue()) + ((Double) right).doubleValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Double.valueOf(((double) ((Byte) left).byteValue()) - ((Double) right).doubleValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Double.valueOf(((double) ((Byte) left).byteValue()) * ((Double) right).doubleValue());
                                }
                                break;
                        }
                        Unit unit4 = Unit.INSTANCE;
                        return null;
                    case 5:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Float.compare(((Byte) left).byteValue(), ((Float) right).floatValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Float.valueOf(((Byte) left).byteValue() / ((Float) right).floatValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Float.valueOf(((Byte) left).byteValue() % ((Float) right).floatValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Float.valueOf(((Byte) left).byteValue() + ((Float) right).floatValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Float.valueOf(((Byte) left).byteValue() - ((Float) right).floatValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Float.valueOf(((Byte) left).byteValue() * ((Float) right).floatValue());
                                }
                                break;
                        }
                        Unit unit5 = Unit.INSTANCE;
                        return null;
                    case 6:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare((int) ((Byte) left).byteValue(), ((Integer) right).intValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Integer.valueOf(((Byte) left).byteValue() / ((Integer) right).intValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    byte bByteValue5 = ((Byte) left).byteValue();
                                    int iIntValue = ((Integer) right).intValue();
                                    int i4 = bByteValue5 % iIntValue;
                                    return Integer.valueOf(i4 + (iIntValue & (((i4 ^ iIntValue) & ((-i4) | i4)) >> 31)));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Integer.valueOf(((Byte) left).byteValue() % ((Integer) right).intValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Integer.valueOf(((Byte) left).byteValue() + ((Integer) right).intValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Integer.valueOf(((Byte) left).byteValue() - ((Integer) right).intValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Integer.valueOf(((Byte) left).byteValue() * ((Integer) right).intValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    byte bByteValue6 = ((Byte) left).byteValue();
                                    int iIntValue2 = ((Integer) right).intValue();
                                    int i5 = bByteValue6 / iIntValue2;
                                    if ((bByteValue6 ^ iIntValue2) < 0 && iIntValue2 * i5 != bByteValue6) {
                                        i5--;
                                    }
                                    return Integer.valueOf(i5);
                                }
                                break;
                        }
                        Unit unit6 = Unit.INSTANCE;
                        return null;
                    case 7:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare(((Byte) left).byteValue(), ((Long) right).longValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Long.valueOf(((long) ((Byte) left).byteValue()) / ((Long) right).longValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    byte bByteValue7 = ((Byte) left).byteValue();
                                    long jLongValue = ((Long) right).longValue();
                                    long j = ((long) bByteValue7) % jLongValue;
                                    return Long.valueOf(j + (jLongValue & (((j ^ jLongValue) & ((-j) | j)) >> 63)));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Long.valueOf(((long) ((Byte) left).byteValue()) % ((Long) right).longValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Long.valueOf(((long) ((Byte) left).byteValue()) + ((Long) right).longValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Long.valueOf(((long) ((Byte) left).byteValue()) - ((Long) right).longValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Long.valueOf(((long) ((Byte) left).byteValue()) * ((Long) right).longValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    byte bByteValue8 = ((Byte) left).byteValue();
                                    long jLongValue2 = ((Long) right).longValue();
                                    long j2 = bByteValue8;
                                    long j3 = j2 / jLongValue2;
                                    if ((j2 ^ jLongValue2) < 0 && jLongValue2 * j3 != j2) {
                                        j3--;
                                    }
                                    return Long.valueOf(j3);
                                }
                                break;
                        }
                        Unit unit7 = Unit.INSTANCE;
                        return null;
                    case 8:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare((int) ((Byte) left).byteValue(), (int) ((Short) right).shortValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Integer.valueOf(((Byte) left).byteValue() / ((Short) right).shortValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    byte bByteValue9 = ((Byte) left).byteValue();
                                    short sShortValue = ((Short) right).shortValue();
                                    int i6 = bByteValue9 % sShortValue;
                                    return Short.valueOf((short) (i6 + (sShortValue & (((i6 ^ sShortValue) & ((-i6) | i6)) >> 31))));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Integer.valueOf(((Byte) left).byteValue() % ((Short) right).shortValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Integer.valueOf(((Byte) left).byteValue() + ((Short) right).shortValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Integer.valueOf(((Byte) left).byteValue() - ((Short) right).shortValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Integer.valueOf(((Byte) left).byteValue() * ((Short) right).shortValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    byte bByteValue10 = ((Byte) left).byteValue();
                                    short sShortValue2 = ((Short) right).shortValue();
                                    int i7 = bByteValue10 / sShortValue2;
                                    if ((bByteValue10 ^ sShortValue2) < 0 && sShortValue2 * i7 != bByteValue10) {
                                        i7--;
                                    }
                                    return Integer.valueOf(i7);
                                }
                                break;
                        }
                        Unit unit8 = Unit.INSTANCE;
                        return null;
                    case 10:
                        if (Intrinsics.areEqual(name2, Namer.EQUALS_METHOD_NAME)) {
                            return Boolean.valueOf(((Byte) left).equals(right));
                        }
                        break;
                }
                Unit unit9 = Unit.INSTANCE;
                return null;
            case 3:
                int i8 = WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()];
                if (i8 == 3) {
                    if (Intrinsics.areEqual(name2, "compareTo")) {
                        return Integer.valueOf(Intrinsics.compare((int) ((Character) left).charValue(), (int) ((Character) right).charValue()));
                    }
                    if (Intrinsics.areEqual(name2, "minus")) {
                        return Integer.valueOf(((Character) left).charValue() - ((Character) right).charValue());
                    }
                    Unit unit10 = Unit.INSTANCE;
                    return null;
                }
                if (i8 != 6) {
                    if (i8 == 10) {
                        if (Intrinsics.areEqual(name2, Namer.EQUALS_METHOD_NAME)) {
                            return Boolean.valueOf(((Character) left).equals(right));
                        }
                        Unit unit11 = Unit.INSTANCE;
                        return null;
                    }
                } else {
                    if (Intrinsics.areEqual(name2, "minus")) {
                        return Character.valueOf((char) (((Character) left).charValue() - ((Integer) right).intValue()));
                    }
                    if (Intrinsics.areEqual(name2, "plus")) {
                        return Character.valueOf((char) (((Character) left).charValue() + ((Integer) right).intValue()));
                    }
                }
                Unit unit12 = Unit.INSTANCE;
                return null;
            case 4:
                switch (WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()]) {
                    case 2:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Double.compare(((Double) left).doubleValue(), ((Byte) right).byteValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Double.valueOf(((Double) left).doubleValue() / ((double) ((Byte) right).byteValue()));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Double.valueOf(((Double) left).doubleValue() % ((double) ((Byte) right).byteValue()));
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Double.valueOf(((Double) left).doubleValue() + ((double) ((Byte) right).byteValue()));
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Double.valueOf(((Double) left).doubleValue() - ((double) ((Byte) right).byteValue()));
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Double.valueOf(((Double) left).doubleValue() * ((double) ((Byte) right).byteValue()));
                                }
                                break;
                        }
                        Unit unit13 = Unit.INSTANCE;
                        return null;
                    case 4:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Double.compare(((Double) left).doubleValue(), ((Double) right).doubleValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Double.valueOf(((Double) left).doubleValue() / ((Double) right).doubleValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    double dDoubleValue = ((Double) left).doubleValue();
                                    double dDoubleValue2 = ((Double) right).doubleValue();
                                    double d = dDoubleValue % dDoubleValue2;
                                    if (d != 0.0d && Math.signum(d) != Math.signum(dDoubleValue2)) {
                                        d += dDoubleValue2;
                                    }
                                    return Double.valueOf(d);
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Double.valueOf(((Double) left).doubleValue() % ((Double) right).doubleValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Double.valueOf(((Double) left).doubleValue() + ((Double) right).doubleValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Double.valueOf(((Double) left).doubleValue() - ((Double) right).doubleValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Double.valueOf(((Double) left).doubleValue() * ((Double) right).doubleValue());
                                }
                                break;
                        }
                        Unit unit14 = Unit.INSTANCE;
                        return null;
                    case 5:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Double.compare(((Double) left).doubleValue(), ((Float) right).floatValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Double.valueOf(((Double) left).doubleValue() / ((double) ((Float) right).floatValue()));
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    double dDoubleValue3 = ((Double) left).doubleValue();
                                    double dFloatValue = ((Float) right).floatValue();
                                    double d2 = dDoubleValue3 % dFloatValue;
                                    if (d2 != 0.0d && Math.signum(d2) != Math.signum(dFloatValue)) {
                                        d2 += dFloatValue;
                                    }
                                    return Double.valueOf(d2);
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Double.valueOf(((Double) left).doubleValue() % ((double) ((Float) right).floatValue()));
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Double.valueOf(((Double) left).doubleValue() + ((double) ((Float) right).floatValue()));
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Double.valueOf(((Double) left).doubleValue() - ((double) ((Float) right).floatValue()));
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Double.valueOf(((Double) left).doubleValue() * ((double) ((Float) right).floatValue()));
                                }
                                break;
                        }
                        Unit unit15 = Unit.INSTANCE;
                        return null;
                    case 6:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Double.compare(((Double) left).doubleValue(), ((Integer) right).intValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Double.valueOf(((Double) left).doubleValue() / ((double) ((Integer) right).intValue()));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Double.valueOf(((Double) left).doubleValue() % ((double) ((Integer) right).intValue()));
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Double.valueOf(((Double) left).doubleValue() + ((double) ((Integer) right).intValue()));
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Double.valueOf(((Double) left).doubleValue() - ((double) ((Integer) right).intValue()));
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Double.valueOf(((Double) left).doubleValue() * ((double) ((Integer) right).intValue()));
                                }
                                break;
                        }
                        Unit unit16 = Unit.INSTANCE;
                        return null;
                    case 7:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Double.compare(((Double) left).doubleValue(), ((Long) right).longValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Double.valueOf(((Double) left).doubleValue() / ((Long) right).longValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Double.valueOf(((Double) left).doubleValue() % ((Long) right).longValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Double.valueOf(((Double) left).doubleValue() + ((Long) right).longValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Double.valueOf(((Double) left).doubleValue() - ((Long) right).longValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Double.valueOf(((Double) left).doubleValue() * ((Long) right).longValue());
                                }
                                break;
                        }
                        Unit unit17 = Unit.INSTANCE;
                        return null;
                    case 8:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Double.compare(((Double) left).doubleValue(), ((Short) right).shortValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Double.valueOf(((Double) left).doubleValue() / ((double) ((Short) right).shortValue()));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Double.valueOf(((Double) left).doubleValue() % ((double) ((Short) right).shortValue()));
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Double.valueOf(((Double) left).doubleValue() + ((double) ((Short) right).shortValue()));
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Double.valueOf(((Double) left).doubleValue() - ((double) ((Short) right).shortValue()));
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Double.valueOf(((Double) left).doubleValue() * ((double) ((Short) right).shortValue()));
                                }
                                break;
                        }
                        Unit unit18 = Unit.INSTANCE;
                        return null;
                    case 10:
                        if (Intrinsics.areEqual(name2, Namer.EQUALS_METHOD_NAME)) {
                            return Boolean.valueOf(((Double) left).equals(right));
                        }
                        break;
                }
                Unit unit19 = Unit.INSTANCE;
                return null;
            case 5:
                switch (WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()]) {
                    case 2:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Float.compare(((Float) left).floatValue(), ((Byte) right).byteValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Float.valueOf(((Float) left).floatValue() / ((Byte) right).byteValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Float.valueOf(((Float) left).floatValue() % ((Byte) right).byteValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Float.valueOf(((Float) left).floatValue() + ((Byte) right).byteValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Float.valueOf(((Float) left).floatValue() - ((Byte) right).byteValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Float.valueOf(((Float) left).floatValue() * ((Byte) right).byteValue());
                                }
                                break;
                        }
                        Unit unit20 = Unit.INSTANCE;
                        return null;
                    case 4:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Double.compare(((Float) left).floatValue(), ((Double) right).doubleValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Double.valueOf(((double) ((Float) left).floatValue()) / ((Double) right).doubleValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    float fFloatValue = ((Float) left).floatValue();
                                    double dDoubleValue4 = ((Double) right).doubleValue();
                                    double d3 = ((double) fFloatValue) % dDoubleValue4;
                                    if (d3 != 0.0d && Math.signum(d3) != Math.signum(dDoubleValue4)) {
                                        d3 += dDoubleValue4;
                                    }
                                    return Double.valueOf(d3);
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Double.valueOf(((double) ((Float) left).floatValue()) % ((Double) right).doubleValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Double.valueOf(((double) ((Float) left).floatValue()) + ((Double) right).doubleValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Double.valueOf(((double) ((Float) left).floatValue()) - ((Double) right).doubleValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Double.valueOf(((double) ((Float) left).floatValue()) * ((Double) right).doubleValue());
                                }
                                break;
                        }
                        Unit unit21 = Unit.INSTANCE;
                        return null;
                    case 5:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Float.compare(((Float) left).floatValue(), ((Float) right).floatValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Float.valueOf(((Float) left).floatValue() / ((Float) right).floatValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    float fFloatValue2 = ((Float) left).floatValue();
                                    float fFloatValue3 = ((Float) right).floatValue();
                                    float f = fFloatValue2 % fFloatValue3;
                                    if (f != 0.0f && Math.signum(f) != Math.signum(fFloatValue3)) {
                                        f += fFloatValue3;
                                    }
                                    return Float.valueOf(f);
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Float.valueOf(((Float) left).floatValue() % ((Float) right).floatValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Float.valueOf(((Float) left).floatValue() + ((Float) right).floatValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Float.valueOf(((Float) left).floatValue() - ((Float) right).floatValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Float.valueOf(((Float) left).floatValue() * ((Float) right).floatValue());
                                }
                                break;
                        }
                        Unit unit22 = Unit.INSTANCE;
                        return null;
                    case 6:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Float.compare(((Float) left).floatValue(), ((Integer) right).intValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Float.valueOf(((Float) left).floatValue() / ((Integer) right).intValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Float.valueOf(((Float) left).floatValue() % ((Integer) right).intValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Float.valueOf(((Float) left).floatValue() + ((Integer) right).intValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Float.valueOf(((Float) left).floatValue() - ((Integer) right).intValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Float.valueOf(((Float) left).floatValue() * ((Integer) right).intValue());
                                }
                                break;
                        }
                        Unit unit23 = Unit.INSTANCE;
                        return null;
                    case 7:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Float.compare(((Float) left).floatValue(), ((Long) right).longValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Float.valueOf(((Float) left).floatValue() / ((Long) right).longValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Float.valueOf(((Float) left).floatValue() % ((Long) right).longValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Float.valueOf(((Float) left).floatValue() + ((Long) right).longValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Float.valueOf(((Float) left).floatValue() - ((Long) right).longValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Float.valueOf(((Float) left).floatValue() * ((Long) right).longValue());
                                }
                                break;
                        }
                        Unit unit24 = Unit.INSTANCE;
                        return null;
                    case 8:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Float.compare(((Float) left).floatValue(), ((Short) right).shortValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Float.valueOf(((Float) left).floatValue() / ((Short) right).shortValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Float.valueOf(((Float) left).floatValue() % ((Short) right).shortValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Float.valueOf(((Float) left).floatValue() + ((Short) right).shortValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Float.valueOf(((Float) left).floatValue() - ((Short) right).shortValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Float.valueOf(((Float) left).floatValue() * ((Short) right).shortValue());
                                }
                                break;
                        }
                        Unit unit25 = Unit.INSTANCE;
                        return null;
                    case 10:
                        if (Intrinsics.areEqual(name2, Namer.EQUALS_METHOD_NAME)) {
                            return Boolean.valueOf(((Float) left).equals(right));
                        }
                        break;
                }
                Unit unit26 = Unit.INSTANCE;
                return null;
            case 6:
                switch (WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()]) {
                    case 2:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare(((Integer) left).intValue(), (int) ((Byte) right).byteValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Integer.valueOf(((Integer) left).intValue() / ((Byte) right).byteValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    int iIntValue3 = ((Integer) left).intValue();
                                    byte bByteValue11 = ((Byte) right).byteValue();
                                    int i9 = iIntValue3 % bByteValue11;
                                    return Byte.valueOf((byte) (i9 + (bByteValue11 & (((i9 ^ bByteValue11) & ((-i9) | i9)) >> 31))));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Integer.valueOf(((Integer) left).intValue() % ((Byte) right).byteValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Integer.valueOf(((Integer) left).intValue() + ((Byte) right).byteValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Integer.valueOf(((Integer) left).intValue() - ((Byte) right).byteValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Integer.valueOf(((Integer) left).intValue() * ((Byte) right).byteValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    int iIntValue4 = ((Integer) left).intValue();
                                    byte bByteValue12 = ((Byte) right).byteValue();
                                    int i10 = iIntValue4 / bByteValue12;
                                    if ((iIntValue4 ^ bByteValue12) < 0 && bByteValue12 * i10 != iIntValue4) {
                                        i10--;
                                    }
                                    return Integer.valueOf(i10);
                                }
                                break;
                        }
                        Unit unit27 = Unit.INSTANCE;
                        return null;
                    case 4:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Double.compare(((Integer) left).intValue(), ((Double) right).doubleValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Double.valueOf(((double) ((Integer) left).intValue()) / ((Double) right).doubleValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Double.valueOf(((double) ((Integer) left).intValue()) % ((Double) right).doubleValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Double.valueOf(((double) ((Integer) left).intValue()) + ((Double) right).doubleValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Double.valueOf(((double) ((Integer) left).intValue()) - ((Double) right).doubleValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Double.valueOf(((double) ((Integer) left).intValue()) * ((Double) right).doubleValue());
                                }
                                break;
                        }
                        Unit unit28 = Unit.INSTANCE;
                        return null;
                    case 5:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Float.compare(((Integer) left).intValue(), ((Float) right).floatValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Float.valueOf(((Integer) left).intValue() / ((Float) right).floatValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Float.valueOf(((Integer) left).intValue() % ((Float) right).floatValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Float.valueOf(((Integer) left).intValue() + ((Float) right).floatValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Float.valueOf(((Integer) left).intValue() - ((Float) right).floatValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Float.valueOf(((Integer) left).intValue() * ((Float) right).floatValue());
                                }
                                break;
                        }
                        Unit unit29 = Unit.INSTANCE;
                        return null;
                    case 6:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare(((Integer) left).intValue(), ((Integer) right).intValue()));
                                }
                                break;
                            case XKeySym.XK_Thai_saraaimaimuan /* 3555 */:
                                if (name2.equals("or")) {
                                    return Integer.valueOf(((Integer) left).intValue() | ((Integer) right).intValue());
                                }
                                break;
                            case 96727:
                                if (name2.equals("and")) {
                                    return Integer.valueOf(((Integer) left).intValue() & ((Integer) right).intValue());
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Integer.valueOf(((Integer) left).intValue() / ((Integer) right).intValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    int iIntValue5 = ((Integer) left).intValue();
                                    int iIntValue6 = ((Integer) right).intValue();
                                    int i11 = iIntValue5 % iIntValue6;
                                    return Integer.valueOf(i11 + (iIntValue6 & (((i11 ^ iIntValue6) & ((-i11) | i11)) >> 31)));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Integer.valueOf(((Integer) left).intValue() % ((Integer) right).intValue());
                                }
                                break;
                            case 113847:
                                if (name2.equals("shl")) {
                                    return Integer.valueOf(((Integer) left).intValue() << ((Integer) right).intValue());
                                }
                                break;
                            case 113853:
                                if (name2.equals("shr")) {
                                    return Integer.valueOf(((Integer) left).intValue() >> ((Integer) right).intValue());
                                }
                                break;
                            case 118875:
                                if (name2.equals("xor")) {
                                    return Integer.valueOf(((Integer) left).intValue() ^ ((Integer) right).intValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Integer.valueOf(((Integer) left).intValue() + ((Integer) right).intValue());
                                }
                                break;
                            case 3599400:
                                if (name2.equals("ushr")) {
                                    return Integer.valueOf(((Integer) left).intValue() >>> ((Integer) right).intValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Integer.valueOf(((Integer) left).intValue() - ((Integer) right).intValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Integer.valueOf(((Integer) left).intValue() * ((Integer) right).intValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    int iIntValue7 = ((Integer) left).intValue();
                                    int iIntValue8 = ((Integer) right).intValue();
                                    int i12 = iIntValue7 / iIntValue8;
                                    if ((iIntValue7 ^ iIntValue8) < 0 && iIntValue8 * i12 != iIntValue7) {
                                        i12--;
                                    }
                                    return Integer.valueOf(i12);
                                }
                                break;
                        }
                        Unit unit30 = Unit.INSTANCE;
                        return null;
                    case 7:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare(((Integer) left).intValue(), ((Long) right).longValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Long.valueOf(((long) ((Integer) left).intValue()) / ((Long) right).longValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    int iIntValue9 = ((Integer) left).intValue();
                                    long jLongValue3 = ((Long) right).longValue();
                                    long j4 = ((long) iIntValue9) % jLongValue3;
                                    return Long.valueOf(j4 + (jLongValue3 & (((j4 ^ jLongValue3) & ((-j4) | j4)) >> 63)));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Long.valueOf(((long) ((Integer) left).intValue()) % ((Long) right).longValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Long.valueOf(((long) ((Integer) left).intValue()) + ((Long) right).longValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Long.valueOf(((long) ((Integer) left).intValue()) - ((Long) right).longValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Long.valueOf(((long) ((Integer) left).intValue()) * ((Long) right).longValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    int iIntValue10 = ((Integer) left).intValue();
                                    long jLongValue4 = ((Long) right).longValue();
                                    long j5 = iIntValue10;
                                    long j6 = j5 / jLongValue4;
                                    if ((j5 ^ jLongValue4) < 0 && jLongValue4 * j6 != j5) {
                                        j6--;
                                    }
                                    return Long.valueOf(j6);
                                }
                                break;
                        }
                        Unit unit31 = Unit.INSTANCE;
                        return null;
                    case 8:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare(((Integer) left).intValue(), (int) ((Short) right).shortValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Integer.valueOf(((Integer) left).intValue() / ((Short) right).shortValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    int iIntValue11 = ((Integer) left).intValue();
                                    short sShortValue3 = ((Short) right).shortValue();
                                    int i13 = iIntValue11 % sShortValue3;
                                    return Short.valueOf((short) (i13 + (sShortValue3 & (((i13 ^ sShortValue3) & ((-i13) | i13)) >> 31))));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Integer.valueOf(((Integer) left).intValue() % ((Short) right).shortValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Integer.valueOf(((Integer) left).intValue() + ((Short) right).shortValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Integer.valueOf(((Integer) left).intValue() - ((Short) right).shortValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Integer.valueOf(((Integer) left).intValue() * ((Short) right).shortValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    int iIntValue12 = ((Integer) left).intValue();
                                    short sShortValue4 = ((Short) right).shortValue();
                                    int i14 = iIntValue12 / sShortValue4;
                                    if ((iIntValue12 ^ sShortValue4) < 0 && sShortValue4 * i14 != iIntValue12) {
                                        i14--;
                                    }
                                    return Integer.valueOf(i14);
                                }
                                break;
                        }
                        Unit unit32 = Unit.INSTANCE;
                        return null;
                    case 10:
                        if (Intrinsics.areEqual(name2, Namer.EQUALS_METHOD_NAME)) {
                            return Boolean.valueOf(((Integer) left).equals(right));
                        }
                        break;
                }
                Unit unit33 = Unit.INSTANCE;
                return null;
            case 7:
                switch (WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()]) {
                    case 2:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare(((Long) left).longValue(), ((Byte) right).byteValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Long.valueOf(((Long) left).longValue() / ((long) ((Byte) right).byteValue()));
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    long jLongValue5 = ((Long) left).longValue();
                                    long jByteValue = ((Byte) right).byteValue();
                                    long j7 = jLongValue5 % jByteValue;
                                    return Byte.valueOf((byte) (j7 + (jByteValue & (((j7 ^ jByteValue) & ((-j7) | j7)) >> 63))));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Long.valueOf(((Long) left).longValue() % ((long) ((Byte) right).byteValue()));
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Long.valueOf(((Long) left).longValue() + ((long) ((Byte) right).byteValue()));
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Long.valueOf(((Long) left).longValue() - ((long) ((Byte) right).byteValue()));
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Long.valueOf(((Long) left).longValue() * ((long) ((Byte) right).byteValue()));
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    long jLongValue6 = ((Long) left).longValue();
                                    long jByteValue2 = ((Byte) right).byteValue();
                                    long j8 = jLongValue6 / jByteValue2;
                                    if ((jLongValue6 ^ jByteValue2) < 0 && jByteValue2 * j8 != jLongValue6) {
                                        j8--;
                                    }
                                    return Long.valueOf(j8);
                                }
                                break;
                        }
                        Unit unit34 = Unit.INSTANCE;
                        return null;
                    case 4:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Double.compare(((Long) left).longValue(), ((Double) right).doubleValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Double.valueOf(((Long) left).longValue() / ((Double) right).doubleValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Double.valueOf(((Long) left).longValue() % ((Double) right).doubleValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Double.valueOf(((Long) left).longValue() + ((Double) right).doubleValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Double.valueOf(((Long) left).longValue() - ((Double) right).doubleValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Double.valueOf(((Long) left).longValue() * ((Double) right).doubleValue());
                                }
                                break;
                        }
                        Unit unit35 = Unit.INSTANCE;
                        return null;
                    case 5:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Float.compare(((Long) left).longValue(), ((Float) right).floatValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Float.valueOf(((Long) left).longValue() / ((Float) right).floatValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Float.valueOf(((Long) left).longValue() % ((Float) right).floatValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Float.valueOf(((Long) left).longValue() + ((Float) right).floatValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Float.valueOf(((Long) left).longValue() - ((Float) right).floatValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Float.valueOf(((Long) left).longValue() * ((Float) right).floatValue());
                                }
                                break;
                        }
                        Unit unit36 = Unit.INSTANCE;
                        return null;
                    case 6:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare(((Long) left).longValue(), ((Integer) right).intValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Long.valueOf(((Long) left).longValue() / ((long) ((Integer) right).intValue()));
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    long jLongValue7 = ((Long) left).longValue();
                                    long jIntValue = ((Integer) right).intValue();
                                    long j9 = jLongValue7 % jIntValue;
                                    return Integer.valueOf((int) (j9 + (jIntValue & (((j9 ^ jIntValue) & ((-j9) | j9)) >> 63))));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Long.valueOf(((Long) left).longValue() % ((long) ((Integer) right).intValue()));
                                }
                                break;
                            case 113847:
                                if (name2.equals("shl")) {
                                    return Long.valueOf(((Long) left).longValue() << ((Integer) right).intValue());
                                }
                                break;
                            case 113853:
                                if (name2.equals("shr")) {
                                    return Long.valueOf(((Long) left).longValue() >> ((Integer) right).intValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Long.valueOf(((Long) left).longValue() + ((long) ((Integer) right).intValue()));
                                }
                                break;
                            case 3599400:
                                if (name2.equals("ushr")) {
                                    return Long.valueOf(((Long) left).longValue() >>> ((Integer) right).intValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Long.valueOf(((Long) left).longValue() - ((long) ((Integer) right).intValue()));
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Long.valueOf(((Long) left).longValue() * ((long) ((Integer) right).intValue()));
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    long jLongValue8 = ((Long) left).longValue();
                                    long jIntValue2 = ((Integer) right).intValue();
                                    long j10 = jLongValue8 / jIntValue2;
                                    if ((jLongValue8 ^ jIntValue2) < 0 && jIntValue2 * j10 != jLongValue8) {
                                        j10--;
                                    }
                                    return Long.valueOf(j10);
                                }
                                break;
                        }
                        Unit unit37 = Unit.INSTANCE;
                        return null;
                    case 7:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare(((Long) left).longValue(), ((Long) right).longValue()));
                                }
                                break;
                            case XKeySym.XK_Thai_saraaimaimuan /* 3555 */:
                                if (name2.equals("or")) {
                                    return Long.valueOf(((Long) left).longValue() | ((Long) right).longValue());
                                }
                                break;
                            case 96727:
                                if (name2.equals("and")) {
                                    return Long.valueOf(((Long) left).longValue() & ((Long) right).longValue());
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Long.valueOf(((Long) left).longValue() / ((Long) right).longValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    long jLongValue9 = ((Long) left).longValue();
                                    long jLongValue10 = ((Long) right).longValue();
                                    long j11 = jLongValue9 % jLongValue10;
                                    return Long.valueOf(j11 + (jLongValue10 & (((j11 ^ jLongValue10) & ((-j11) | j11)) >> 63)));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Long.valueOf(((Long) left).longValue() % ((Long) right).longValue());
                                }
                                break;
                            case 118875:
                                if (name2.equals("xor")) {
                                    return Long.valueOf(((Long) left).longValue() ^ ((Long) right).longValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Long.valueOf(((Long) left).longValue() + ((Long) right).longValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Long.valueOf(((Long) left).longValue() - ((Long) right).longValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Long.valueOf(((Long) left).longValue() * ((Long) right).longValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    long jLongValue11 = ((Long) left).longValue();
                                    long jLongValue12 = ((Long) right).longValue();
                                    long j12 = jLongValue11 / jLongValue12;
                                    if ((jLongValue11 ^ jLongValue12) < 0 && jLongValue12 * j12 != jLongValue11) {
                                        j12--;
                                    }
                                    return Long.valueOf(j12);
                                }
                                break;
                        }
                        Unit unit38 = Unit.INSTANCE;
                        return null;
                    case 8:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare(((Long) left).longValue(), ((Short) right).shortValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Long.valueOf(((Long) left).longValue() / ((long) ((Short) right).shortValue()));
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    long jLongValue13 = ((Long) left).longValue();
                                    long jShortValue = ((Short) right).shortValue();
                                    long j13 = jLongValue13 % jShortValue;
                                    return Short.valueOf((short) (j13 + (jShortValue & (((j13 ^ jShortValue) & ((-j13) | j13)) >> 63))));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Long.valueOf(((Long) left).longValue() % ((long) ((Short) right).shortValue()));
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Long.valueOf(((Long) left).longValue() + ((long) ((Short) right).shortValue()));
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Long.valueOf(((Long) left).longValue() - ((long) ((Short) right).shortValue()));
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Long.valueOf(((Long) left).longValue() * ((long) ((Short) right).shortValue()));
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    long jLongValue14 = ((Long) left).longValue();
                                    long jShortValue2 = ((Short) right).shortValue();
                                    long j14 = jLongValue14 / jShortValue2;
                                    if ((jLongValue14 ^ jShortValue2) < 0 && jShortValue2 * j14 != jLongValue14) {
                                        j14--;
                                    }
                                    return Long.valueOf(j14);
                                }
                                break;
                        }
                        Unit unit39 = Unit.INSTANCE;
                        return null;
                    case 10:
                        if (Intrinsics.areEqual(name2, Namer.EQUALS_METHOD_NAME)) {
                            return Boolean.valueOf(((Long) left).equals(right));
                        }
                        break;
                }
                Unit unit40 = Unit.INSTANCE;
                return null;
            case 8:
                switch (WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()]) {
                    case 2:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare((int) ((Short) left).shortValue(), (int) ((Byte) right).byteValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Integer.valueOf(((Short) left).shortValue() / ((Byte) right).byteValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    short sShortValue5 = ((Short) left).shortValue();
                                    byte bByteValue13 = ((Byte) right).byteValue();
                                    int i15 = sShortValue5 % bByteValue13;
                                    return Byte.valueOf((byte) (i15 + (bByteValue13 & (((i15 ^ bByteValue13) & ((-i15) | i15)) >> 31))));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Integer.valueOf(((Short) left).shortValue() % ((Byte) right).byteValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Integer.valueOf(((Short) left).shortValue() + ((Byte) right).byteValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Integer.valueOf(((Short) left).shortValue() - ((Byte) right).byteValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Integer.valueOf(((Short) left).shortValue() * ((Byte) right).byteValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    short sShortValue6 = ((Short) left).shortValue();
                                    byte bByteValue14 = ((Byte) right).byteValue();
                                    int i16 = sShortValue6 / bByteValue14;
                                    if ((sShortValue6 ^ bByteValue14) < 0 && bByteValue14 * i16 != sShortValue6) {
                                        i16--;
                                    }
                                    return Integer.valueOf(i16);
                                }
                                break;
                        }
                        Unit unit41 = Unit.INSTANCE;
                        return null;
                    case 4:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Double.compare(((Short) left).shortValue(), ((Double) right).doubleValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Double.valueOf(((double) ((Short) left).shortValue()) / ((Double) right).doubleValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Double.valueOf(((double) ((Short) left).shortValue()) % ((Double) right).doubleValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Double.valueOf(((double) ((Short) left).shortValue()) + ((Double) right).doubleValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Double.valueOf(((double) ((Short) left).shortValue()) - ((Double) right).doubleValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Double.valueOf(((double) ((Short) left).shortValue()) * ((Double) right).doubleValue());
                                }
                                break;
                        }
                        Unit unit42 = Unit.INSTANCE;
                        return null;
                    case 5:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Float.compare(((Short) left).shortValue(), ((Float) right).floatValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Float.valueOf(((Short) left).shortValue() / ((Float) right).floatValue());
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Float.valueOf(((Short) left).shortValue() % ((Float) right).floatValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Float.valueOf(((Short) left).shortValue() + ((Float) right).floatValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Float.valueOf(((Short) left).shortValue() - ((Float) right).floatValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Float.valueOf(((Short) left).shortValue() * ((Float) right).floatValue());
                                }
                                break;
                        }
                        Unit unit43 = Unit.INSTANCE;
                        return null;
                    case 6:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare((int) ((Short) left).shortValue(), ((Integer) right).intValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Integer.valueOf(((Short) left).shortValue() / ((Integer) right).intValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    short sShortValue7 = ((Short) left).shortValue();
                                    int iIntValue13 = ((Integer) right).intValue();
                                    int i17 = sShortValue7 % iIntValue13;
                                    return Integer.valueOf(i17 + (iIntValue13 & (((i17 ^ iIntValue13) & ((-i17) | i17)) >> 31)));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Integer.valueOf(((Short) left).shortValue() % ((Integer) right).intValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Integer.valueOf(((Short) left).shortValue() + ((Integer) right).intValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Integer.valueOf(((Short) left).shortValue() - ((Integer) right).intValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Integer.valueOf(((Short) left).shortValue() * ((Integer) right).intValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    short sShortValue8 = ((Short) left).shortValue();
                                    int iIntValue14 = ((Integer) right).intValue();
                                    int i18 = sShortValue8 / iIntValue14;
                                    if ((sShortValue8 ^ iIntValue14) < 0 && iIntValue14 * i18 != sShortValue8) {
                                        i18--;
                                    }
                                    return Integer.valueOf(i18);
                                }
                                break;
                        }
                        Unit unit44 = Unit.INSTANCE;
                        return null;
                    case 7:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare(((Short) left).shortValue(), ((Long) right).longValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Long.valueOf(((long) ((Short) left).shortValue()) / ((Long) right).longValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    short sShortValue9 = ((Short) left).shortValue();
                                    long jLongValue15 = ((Long) right).longValue();
                                    long j15 = ((long) sShortValue9) % jLongValue15;
                                    return Long.valueOf(j15 + (jLongValue15 & (((j15 ^ jLongValue15) & ((-j15) | j15)) >> 63)));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Long.valueOf(((long) ((Short) left).shortValue()) % ((Long) right).longValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Long.valueOf(((long) ((Short) left).shortValue()) + ((Long) right).longValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Long.valueOf(((long) ((Short) left).shortValue()) - ((Long) right).longValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Long.valueOf(((long) ((Short) left).shortValue()) * ((Long) right).longValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    short sShortValue10 = ((Short) left).shortValue();
                                    long jLongValue16 = ((Long) right).longValue();
                                    long j16 = sShortValue10;
                                    long j17 = j16 / jLongValue16;
                                    if ((j16 ^ jLongValue16) < 0 && jLongValue16 * j17 != j16) {
                                        j17--;
                                    }
                                    return Long.valueOf(j17);
                                }
                                break;
                        }
                        Unit unit45 = Unit.INSTANCE;
                        return null;
                    case 8:
                        switch (name2.hashCode()) {
                            case -1412718016:
                                if (name2.equals("compareTo")) {
                                    return Integer.valueOf(Intrinsics.compare((int) ((Short) left).shortValue(), (int) ((Short) right).shortValue()));
                                }
                                break;
                            case 99473:
                                if (name2.equals("div")) {
                                    return Integer.valueOf(((Short) left).shortValue() / ((Short) right).shortValue());
                                }
                                break;
                            case 108290:
                                if (name2.equals("mod")) {
                                    short sShortValue11 = ((Short) left).shortValue();
                                    short sShortValue12 = ((Short) right).shortValue();
                                    int i19 = sShortValue11 % sShortValue12;
                                    return Short.valueOf((short) (i19 + (sShortValue12 & (((i19 ^ sShortValue12) & ((-i19) | i19)) >> 31))));
                                }
                                break;
                            case 112794:
                                if (name2.equals("rem")) {
                                    return Integer.valueOf(((Short) left).shortValue() % ((Short) right).shortValue());
                                }
                                break;
                            case 3444122:
                                if (name2.equals("plus")) {
                                    return Integer.valueOf(((Short) left).shortValue() + ((Short) right).shortValue());
                                }
                                break;
                            case 103901296:
                                if (name2.equals("minus")) {
                                    return Integer.valueOf(((Short) left).shortValue() - ((Short) right).shortValue());
                                }
                                break;
                            case 110364486:
                                if (name2.equals("times")) {
                                    return Integer.valueOf(((Short) left).shortValue() * ((Short) right).shortValue());
                                }
                                break;
                            case 2022956261:
                                if (name2.equals("floorDiv")) {
                                    short sShortValue13 = ((Short) left).shortValue();
                                    short sShortValue14 = ((Short) right).shortValue();
                                    int i20 = sShortValue13 / sShortValue14;
                                    if ((sShortValue13 ^ sShortValue14) < 0 && sShortValue14 * i20 != sShortValue13) {
                                        i20--;
                                    }
                                    return Integer.valueOf(i20);
                                }
                                break;
                        }
                        Unit unit46 = Unit.INSTANCE;
                        return null;
                    case 10:
                        if (Intrinsics.areEqual(name2, Namer.EQUALS_METHOD_NAME)) {
                            return Boolean.valueOf(((Short) left).equals(right));
                        }
                        break;
                }
                Unit unit47 = Unit.INSTANCE;
                return null;
            case 9:
                int i21 = WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()];
                if (i21 != 6) {
                    if (i21 == 9) {
                        if (Intrinsics.areEqual(name2, "compareTo")) {
                            return Integer.valueOf(((String) left).compareTo((String) right));
                        }
                        Unit unit48 = Unit.INSTANCE;
                        return null;
                    }
                    if (i21 == 10) {
                        if (Intrinsics.areEqual(name2, Namer.EQUALS_METHOD_NAME)) {
                            return Boolean.valueOf(((String) left).equals(right));
                        }
                        if (Intrinsics.areEqual(name2, "plus")) {
                            return ((String) left) + right;
                        }
                        Unit unit49 = Unit.INSTANCE;
                        return null;
                    }
                } else if (Intrinsics.areEqual(name2, "get")) {
                    return Character.valueOf(((String) left).charAt(((Integer) right).intValue()));
                }
        }
        Unit unit50 = Unit.INSTANCE;
        return null;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final BigInteger checkBinaryOp(String name2, CompileTimeType leftType, BigInteger left, CompileTimeType rightType, BigInteger right) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(leftType, "leftType");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(rightType, "rightType");
        Intrinsics.checkNotNullParameter(right, "right");
        int i = WhenMappings.$EnumSwitchMapping$0[leftType.ordinal()];
        if (i == 2) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()];
            if (i2 == 2) {
                switch (name2.hashCode()) {
                    case 99473:
                        if (name2.equals("div")) {
                            return left.divide(right);
                        }
                        return null;
                    case 112794:
                        if (!name2.equals("rem")) {
                            return null;
                        }
                        BigInteger bigIntegerRemainder = left.remainder(right);
                        Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder, "remainder(...)");
                        return bigIntegerRemainder;
                    case 3444122:
                        if (name2.equals("plus")) {
                            return left.add(right);
                        }
                        return null;
                    case 103901296:
                        if (name2.equals("minus")) {
                            return left.subtract(right);
                        }
                        return null;
                    case 110364486:
                        if (name2.equals("times")) {
                            return left.multiply(right);
                        }
                        return null;
                    default:
                        return null;
                }
            }
            if (i2 == 6) {
                switch (name2.hashCode()) {
                    case 99473:
                        if (name2.equals("div")) {
                            return left.divide(right);
                        }
                        return null;
                    case 112794:
                        if (!name2.equals("rem")) {
                            return null;
                        }
                        BigInteger bigIntegerRemainder2 = left.remainder(right);
                        Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder2, "remainder(...)");
                        return bigIntegerRemainder2;
                    case 3444122:
                        if (name2.equals("plus")) {
                            return left.add(right);
                        }
                        return null;
                    case 103901296:
                        if (name2.equals("minus")) {
                            return left.subtract(right);
                        }
                        return null;
                    case 110364486:
                        if (name2.equals("times")) {
                            return left.multiply(right);
                        }
                        return null;
                    default:
                        return null;
                }
            }
            if (i2 == 7) {
                switch (name2.hashCode()) {
                    case 99473:
                        if (name2.equals("div")) {
                            return left.divide(right);
                        }
                        return null;
                    case 112794:
                        if (!name2.equals("rem")) {
                            return null;
                        }
                        BigInteger bigIntegerRemainder3 = left.remainder(right);
                        Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder3, "remainder(...)");
                        return bigIntegerRemainder3;
                    case 3444122:
                        if (name2.equals("plus")) {
                            return left.add(right);
                        }
                        return null;
                    case 103901296:
                        if (name2.equals("minus")) {
                            return left.subtract(right);
                        }
                        return null;
                    case 110364486:
                        if (name2.equals("times")) {
                            return left.multiply(right);
                        }
                        return null;
                    default:
                        return null;
                }
            }
            if (i2 != 8) {
                return null;
            }
            switch (name2.hashCode()) {
                case 99473:
                    if (name2.equals("div")) {
                        return left.divide(right);
                    }
                    return null;
                case 112794:
                    if (!name2.equals("rem")) {
                        return null;
                    }
                    BigInteger bigIntegerRemainder4 = left.remainder(right);
                    Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder4, "remainder(...)");
                    return bigIntegerRemainder4;
                case 3444122:
                    if (name2.equals("plus")) {
                        return left.add(right);
                    }
                    return null;
                case 103901296:
                    if (name2.equals("minus")) {
                        return left.subtract(right);
                    }
                    return null;
                case 110364486:
                    if (name2.equals("times")) {
                        return left.multiply(right);
                    }
                    return null;
                default:
                    return null;
            }
        }
        if (i == 6) {
            int i3 = WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()];
            if (i3 == 2) {
                switch (name2.hashCode()) {
                    case 99473:
                        if (name2.equals("div")) {
                            return left.divide(right);
                        }
                        return null;
                    case 112794:
                        if (!name2.equals("rem")) {
                            return null;
                        }
                        BigInteger bigIntegerRemainder5 = left.remainder(right);
                        Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder5, "remainder(...)");
                        return bigIntegerRemainder5;
                    case 3444122:
                        if (name2.equals("plus")) {
                            return left.add(right);
                        }
                        return null;
                    case 103901296:
                        if (name2.equals("minus")) {
                            return left.subtract(right);
                        }
                        return null;
                    case 110364486:
                        if (name2.equals("times")) {
                            return left.multiply(right);
                        }
                        return null;
                    default:
                        return null;
                }
            }
            if (i3 == 6) {
                switch (name2.hashCode()) {
                    case XKeySym.XK_Thai_saraaimaimuan /* 3555 */:
                        if (name2.equals("or")) {
                            return left.or(right);
                        }
                        return null;
                    case 96727:
                        if (name2.equals("and")) {
                            return left.and(right);
                        }
                        return null;
                    case 99473:
                        if (name2.equals("div")) {
                            return left.divide(right);
                        }
                        return null;
                    case 112794:
                        if (!name2.equals("rem")) {
                            return null;
                        }
                        BigInteger bigIntegerRemainder6 = left.remainder(right);
                        Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder6, "remainder(...)");
                        return bigIntegerRemainder6;
                    case 118875:
                        if (name2.equals("xor")) {
                            return left.xor(right);
                        }
                        return null;
                    case 3444122:
                        if (name2.equals("plus")) {
                            return left.add(right);
                        }
                        return null;
                    case 103901296:
                        if (name2.equals("minus")) {
                            return left.subtract(right);
                        }
                        return null;
                    case 110364486:
                        if (name2.equals("times")) {
                            return left.multiply(right);
                        }
                        return null;
                    default:
                        return null;
                }
            }
            if (i3 == 7) {
                switch (name2.hashCode()) {
                    case 99473:
                        if (name2.equals("div")) {
                            return left.divide(right);
                        }
                        return null;
                    case 112794:
                        if (!name2.equals("rem")) {
                            return null;
                        }
                        BigInteger bigIntegerRemainder7 = left.remainder(right);
                        Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder7, "remainder(...)");
                        return bigIntegerRemainder7;
                    case 3444122:
                        if (name2.equals("plus")) {
                            return left.add(right);
                        }
                        return null;
                    case 103901296:
                        if (name2.equals("minus")) {
                            return left.subtract(right);
                        }
                        return null;
                    case 110364486:
                        if (name2.equals("times")) {
                            return left.multiply(right);
                        }
                        return null;
                    default:
                        return null;
                }
            }
            if (i3 != 8) {
                return null;
            }
            switch (name2.hashCode()) {
                case 99473:
                    if (name2.equals("div")) {
                        return left.divide(right);
                    }
                    return null;
                case 112794:
                    if (!name2.equals("rem")) {
                        return null;
                    }
                    BigInteger bigIntegerRemainder8 = left.remainder(right);
                    Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder8, "remainder(...)");
                    return bigIntegerRemainder8;
                case 3444122:
                    if (name2.equals("plus")) {
                        return left.add(right);
                    }
                    return null;
                case 103901296:
                    if (name2.equals("minus")) {
                        return left.subtract(right);
                    }
                    return null;
                case 110364486:
                    if (name2.equals("times")) {
                        return left.multiply(right);
                    }
                    return null;
                default:
                    return null;
            }
        }
        if (i != 7) {
            if (i != 8) {
                return null;
            }
            int i4 = WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()];
            if (i4 == 2) {
                switch (name2.hashCode()) {
                    case 99473:
                        if (name2.equals("div")) {
                            return left.divide(right);
                        }
                        return null;
                    case 112794:
                        if (!name2.equals("rem")) {
                            return null;
                        }
                        BigInteger bigIntegerRemainder9 = left.remainder(right);
                        Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder9, "remainder(...)");
                        return bigIntegerRemainder9;
                    case 3444122:
                        if (name2.equals("plus")) {
                            return left.add(right);
                        }
                        return null;
                    case 103901296:
                        if (name2.equals("minus")) {
                            return left.subtract(right);
                        }
                        return null;
                    case 110364486:
                        if (name2.equals("times")) {
                            return left.multiply(right);
                        }
                        return null;
                    default:
                        return null;
                }
            }
            if (i4 == 6) {
                switch (name2.hashCode()) {
                    case 99473:
                        if (name2.equals("div")) {
                            return left.divide(right);
                        }
                        return null;
                    case 112794:
                        if (!name2.equals("rem")) {
                            return null;
                        }
                        BigInteger bigIntegerRemainder10 = left.remainder(right);
                        Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder10, "remainder(...)");
                        return bigIntegerRemainder10;
                    case 3444122:
                        if (name2.equals("plus")) {
                            return left.add(right);
                        }
                        return null;
                    case 103901296:
                        if (name2.equals("minus")) {
                            return left.subtract(right);
                        }
                        return null;
                    case 110364486:
                        if (name2.equals("times")) {
                            return left.multiply(right);
                        }
                        return null;
                    default:
                        return null;
                }
            }
            if (i4 == 7) {
                switch (name2.hashCode()) {
                    case 99473:
                        if (name2.equals("div")) {
                            return left.divide(right);
                        }
                        return null;
                    case 112794:
                        if (!name2.equals("rem")) {
                            return null;
                        }
                        BigInteger bigIntegerRemainder11 = left.remainder(right);
                        Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder11, "remainder(...)");
                        return bigIntegerRemainder11;
                    case 3444122:
                        if (name2.equals("plus")) {
                            return left.add(right);
                        }
                        return null;
                    case 103901296:
                        if (name2.equals("minus")) {
                            return left.subtract(right);
                        }
                        return null;
                    case 110364486:
                        if (name2.equals("times")) {
                            return left.multiply(right);
                        }
                        return null;
                    default:
                        return null;
                }
            }
            if (i4 != 8) {
                return null;
            }
            switch (name2.hashCode()) {
                case 99473:
                    if (name2.equals("div")) {
                        return left.divide(right);
                    }
                    return null;
                case 112794:
                    if (!name2.equals("rem")) {
                        return null;
                    }
                    BigInteger bigIntegerRemainder12 = left.remainder(right);
                    Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder12, "remainder(...)");
                    return bigIntegerRemainder12;
                case 3444122:
                    if (name2.equals("plus")) {
                        return left.add(right);
                    }
                    return null;
                case 103901296:
                    if (name2.equals("minus")) {
                        return left.subtract(right);
                    }
                    return null;
                case 110364486:
                    if (name2.equals("times")) {
                        return left.multiply(right);
                    }
                    return null;
                default:
                    return null;
            }
        }
        int i5 = WhenMappings.$EnumSwitchMapping$0[rightType.ordinal()];
        if (i5 == 2) {
            switch (name2.hashCode()) {
                case 99473:
                    if (name2.equals("div")) {
                        return left.divide(right);
                    }
                    return null;
                case 112794:
                    if (!name2.equals("rem")) {
                        return null;
                    }
                    BigInteger bigIntegerRemainder13 = left.remainder(right);
                    Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder13, "remainder(...)");
                    return bigIntegerRemainder13;
                case 3444122:
                    if (name2.equals("plus")) {
                        return left.add(right);
                    }
                    return null;
                case 103901296:
                    if (name2.equals("minus")) {
                        return left.subtract(right);
                    }
                    return null;
                case 110364486:
                    if (name2.equals("times")) {
                        return left.multiply(right);
                    }
                    return null;
                default:
                    return null;
            }
        }
        if (i5 == 6) {
            switch (name2.hashCode()) {
                case 99473:
                    if (name2.equals("div")) {
                        return left.divide(right);
                    }
                    return null;
                case 112794:
                    if (!name2.equals("rem")) {
                        return null;
                    }
                    BigInteger bigIntegerRemainder14 = left.remainder(right);
                    Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder14, "remainder(...)");
                    return bigIntegerRemainder14;
                case 3444122:
                    if (name2.equals("plus")) {
                        return left.add(right);
                    }
                    return null;
                case 103901296:
                    if (name2.equals("minus")) {
                        return left.subtract(right);
                    }
                    return null;
                case 110364486:
                    if (name2.equals("times")) {
                        return left.multiply(right);
                    }
                    return null;
                default:
                    return null;
            }
        }
        if (i5 != 7) {
            if (i5 != 8) {
                return null;
            }
            switch (name2.hashCode()) {
                case 99473:
                    if (name2.equals("div")) {
                        return left.divide(right);
                    }
                    return null;
                case 112794:
                    if (!name2.equals("rem")) {
                        return null;
                    }
                    BigInteger bigIntegerRemainder15 = left.remainder(right);
                    Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder15, "remainder(...)");
                    return bigIntegerRemainder15;
                case 3444122:
                    if (name2.equals("plus")) {
                        return left.add(right);
                    }
                    return null;
                case 103901296:
                    if (name2.equals("minus")) {
                        return left.subtract(right);
                    }
                    return null;
                case 110364486:
                    if (name2.equals("times")) {
                        return left.multiply(right);
                    }
                    return null;
                default:
                    return null;
            }
        }
        switch (name2.hashCode()) {
            case XKeySym.XK_Thai_saraaimaimuan /* 3555 */:
                if (name2.equals("or")) {
                    return left.or(right);
                }
                return null;
            case 96727:
                if (name2.equals("and")) {
                    return left.and(right);
                }
                return null;
            case 99473:
                if (name2.equals("div")) {
                    return left.divide(right);
                }
                return null;
            case 112794:
                if (!name2.equals("rem")) {
                    return null;
                }
                BigInteger bigIntegerRemainder16 = left.remainder(right);
                Intrinsics.checkNotNullExpressionValue(bigIntegerRemainder16, "remainder(...)");
                return bigIntegerRemainder16;
            case 118875:
                if (name2.equals("xor")) {
                    return left.xor(right);
                }
                return null;
            case 3444122:
                if (name2.equals("plus")) {
                    return left.add(right);
                }
                return null;
            case 103901296:
                if (name2.equals("minus")) {
                    return left.subtract(right);
                }
                return null;
            case 110364486:
                if (name2.equals("times")) {
                    return left.multiply(right);
                }
                return null;
            default:
                return null;
        }
    }
}
