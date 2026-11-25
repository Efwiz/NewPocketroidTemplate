package org.jetbrains.kotlin.ir.interpreter.builtins;

import androidx.exifinterface.media.ExifInterface;
import com.gaurav.avnc.vnc.XKeySym;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.ArrayIteratorsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.CharRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterMethodNotFoundError;
import org.jetbrains.kotlin.ir.interpreter.proxy.Proxy;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: IrBuiltInsMapGenerated.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\u001a6\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0000\u001aF\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u00012\b\u0010\n\u001a\u0004\u0018\u00010\u0001H\u0000\u001a$\u0010\u000b\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001H\u0000¨\u0006\r"}, d2 = {"interpretBinaryFunction", "", "name", "", "typeA", "typeB", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "interpretTernaryFunction", "typeC", "c", "interpretUnaryFunction", ModuleXmlParser.TYPE, "ir.interpreter"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrBuiltInsMapGeneratedKt {
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final Object interpretUnaryFunction(String name2, String type, Object obj) throws InterpreterMethodNotFoundError {
        String string;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        switch (name2.hashCode()) {
            case -1776922004:
                if (name2.equals("toString")) {
                    switch (type.hashCode()) {
                        case -2133288097:
                            if (type.equals("kotlin.Any")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Any");
                                return obj.toString();
                            }
                            break;
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return String.valueOf(((Integer) obj).intValue());
                            }
                            break;
                        case -1707421504:
                            if (type.equals("kotlin.Any?")) {
                                return (obj == null || (string = obj.toString()) == null) ? "null" : string;
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return String.valueOf((int) ((Byte) obj).byteValue());
                            }
                            break;
                        case -1707368381:
                            if (type.equals("kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return String.valueOf(((Character) obj).charValue());
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return String.valueOf(((Long) obj).longValue());
                            }
                            break;
                        case -1706826127:
                            if (type.equals("kotlin.Unit")) {
                                return Unit.INSTANCE.toString();
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return String.valueOf(((Float) obj).floatValue());
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return String.valueOf((int) ((Short) obj).shortValue());
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return String.valueOf(((Double) obj).doubleValue());
                            }
                            break;
                        case 366142910:
                            if (type.equals("kotlin.String")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                                return ((String) obj).toString();
                            }
                            break;
                        case 411999259:
                            if (type.equals("kotlin.Boolean")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                                return String.valueOf(((Boolean) obj).booleanValue());
                            }
                            break;
                    }
                }
                break;
            case -1177945951:
                if (name2.equals("toFloat")) {
                    switch (type.hashCode()) {
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Float.valueOf(((Integer) obj).intValue());
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Float.valueOf(((Byte) obj).byteValue());
                            }
                            break;
                        case -1707368381:
                            if (type.equals("kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return Float.valueOf(((Character) obj).charValue());
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Float.valueOf(((Long) obj).longValue());
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Float.valueOf(((Float) obj).floatValue());
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Float.valueOf(((Short) obj).shortValue());
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Float.valueOf((float) ((Double) obj).doubleValue());
                            }
                            break;
                        case 223764726:
                            if (type.equals("kotlin.Number")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
                                return Float.valueOf(((Number) obj).floatValue());
                            }
                            break;
                    }
                }
                break;
            case -1166058815:
                if (name2.equals("toShort")) {
                    switch (type.hashCode()) {
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Short.valueOf((short) ((Integer) obj).intValue());
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Short.valueOf(((Byte) obj).byteValue());
                            }
                            break;
                        case -1707368381:
                            if (type.equals("kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return Short.valueOf((short) ((Character) obj).charValue());
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Short.valueOf((short) ((Long) obj).longValue());
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Short.valueOf((short) ((Float) obj).floatValue());
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Short.valueOf(((Short) obj).shortValue());
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Short.valueOf((short) ((Double) obj).doubleValue());
                            }
                            break;
                        case 223764726:
                            if (type.equals("kotlin.Number")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
                                return Short.valueOf(((Number) obj).shortValue());
                            }
                            break;
                    }
                }
                break;
            case -1106363674:
                if (name2.equals("length")) {
                    if (Intrinsics.areEqual(type, "kotlin.String")) {
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                        return Integer.valueOf(((String) obj).length());
                    }
                    if (Intrinsics.areEqual(type, "kotlin.CharSequence")) {
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.CharSequence");
                        return Integer.valueOf(((CharSequence) obj).length());
                    }
                }
                break;
            case -869388765:
                if (name2.equals("toByte")) {
                    switch (type.hashCode()) {
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Byte.valueOf((byte) ((Integer) obj).intValue());
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Byte.valueOf(((Byte) obj).byteValue());
                            }
                            break;
                        case -1707368381:
                            if (type.equals("kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return Byte.valueOf((byte) ((Character) obj).charValue());
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Byte.valueOf((byte) ((Long) obj).longValue());
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Byte.valueOf((byte) ((Float) obj).floatValue());
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Byte.valueOf((byte) ((Short) obj).shortValue());
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Byte.valueOf((byte) ((Double) obj).doubleValue());
                            }
                            break;
                        case 223764726:
                            if (type.equals("kotlin.Number")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
                                return Byte.valueOf(((Number) obj).byteValue());
                            }
                            break;
                    }
                }
                break;
            case -869375887:
                if (name2.equals("toChar")) {
                    switch (type.hashCode()) {
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Character.valueOf((char) ((Integer) obj).intValue());
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Character.valueOf((char) ((Byte) obj).byteValue());
                            }
                            break;
                        case -1707368381:
                            if (type.equals("kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return Character.valueOf(((Character) obj).charValue());
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Character.valueOf((char) ((Long) obj).longValue());
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Character.valueOf((char) ((Float) obj).floatValue());
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Character.valueOf((char) ((Short) obj).shortValue());
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Character.valueOf((char) ((Double) obj).doubleValue());
                            }
                            break;
                        case 223764726:
                            if (type.equals("kotlin.Number")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
                                return Character.valueOf((char) ((Number) obj).intValue());
                            }
                            break;
                    }
                }
                break;
            case -869100649:
                if (name2.equals("toLong")) {
                    switch (type.hashCode()) {
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Long.valueOf(((Integer) obj).intValue());
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Long.valueOf(((Byte) obj).byteValue());
                            }
                            break;
                        case -1707368381:
                            if (type.equals("kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return Long.valueOf(((Character) obj).charValue());
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Long.valueOf(((Long) obj).longValue());
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Long.valueOf((long) ((Float) obj).floatValue());
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Long.valueOf(((Short) obj).shortValue());
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Long.valueOf((long) ((Double) obj).doubleValue());
                            }
                            break;
                        case 223764726:
                            if (type.equals("kotlin.Number")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
                                return Long.valueOf(((Number) obj).longValue());
                            }
                            break;
                    }
                }
                break;
            case 99330:
                if (name2.equals("dec")) {
                    switch (type.hashCode()) {
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Integer.valueOf(((Integer) obj).intValue() - 1);
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Byte.valueOf((byte) (((Byte) obj).byteValue() - 1));
                            }
                            break;
                        case -1707368381:
                            if (type.equals("kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return Character.valueOf((char) (((Character) obj).charValue() - 1));
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Long.valueOf(((Long) obj).longValue() - 1);
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Float.valueOf(((Float) obj).floatValue() - 1.0f);
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Short.valueOf((short) (((Short) obj).shortValue() - 1));
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Double.valueOf(((Double) obj).doubleValue() - 1.0d);
                            }
                            break;
                    }
                }
                break;
            case 104414:
                if (name2.equals("inc")) {
                    switch (type.hashCode()) {
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Integer.valueOf(((Integer) obj).intValue() + 1);
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Byte.valueOf((byte) (((Byte) obj).byteValue() + 1));
                            }
                            break;
                        case -1707368381:
                            if (type.equals("kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return Character.valueOf((char) (((Character) obj).charValue() + 1));
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Long.valueOf(((Long) obj).longValue() + 1);
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Float.valueOf(((Float) obj).floatValue() + 1.0f);
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Short.valueOf((short) (((Short) obj).shortValue() + 1));
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Double.valueOf(((Double) obj).doubleValue() + 1.0d);
                            }
                            break;
                    }
                }
                break;
            case 104433:
                if (name2.equals("inv")) {
                    if (Intrinsics.areEqual(type, "kotlin.Int")) {
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                        return Integer.valueOf(~((Integer) obj).intValue());
                    }
                    if (Intrinsics.areEqual(type, "kotlin.Long")) {
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                        return Long.valueOf(~((Long) obj).longValue());
                    }
                }
                break;
            case 109267:
                if (name2.equals("not") && Intrinsics.areEqual(type, "kotlin.Boolean")) {
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                    return Boolean.valueOf(!((Boolean) obj).booleanValue());
                }
                break;
            case 3059181:
                if (name2.equals(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY) && Intrinsics.areEqual(type, "kotlin.Char")) {
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                    return Integer.valueOf(((Character) obj).charValue());
                }
                break;
            case 3530753:
                if (name2.equals("size")) {
                    switch (type.hashCode()) {
                        case -1581820393:
                            if (type.equals("kotlin.IntArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.IntArray");
                                return Integer.valueOf(((int[]) obj).length);
                            }
                            break;
                        case -1577754486:
                            if (type.equals("kotlin.FloatArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.FloatArray");
                                return Integer.valueOf(((float[]) obj).length);
                            }
                            break;
                        case -1390345460:
                            if (type.equals("kotlin.Array")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                                return Integer.valueOf(((Object[]) obj).length);
                            }
                            break;
                        case -1163761552:
                            if (type.equals("kotlin.LongArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.LongArray");
                                return Integer.valueOf(((long[]) obj).length);
                            }
                            break;
                        case -993017989:
                            if (type.equals("kotlin.DoubleArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.DoubleArray");
                                return Integer.valueOf(((double[]) obj).length);
                            }
                            break;
                        case 270963670:
                            if (type.equals("kotlin.CharArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.CharArray");
                                return Integer.valueOf(((char[]) obj).length);
                            }
                            break;
                        case 951944548:
                            if (type.equals("kotlin.ByteArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ByteArray");
                                return Integer.valueOf(((byte[]) obj).length);
                            }
                            break;
                        case 1005081194:
                            if (type.equals("kotlin.ShortArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ShortArray");
                                return Integer.valueOf(((short[]) obj).length);
                            }
                            break;
                        case 1980710654:
                            if (type.equals("kotlin.BooleanArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.BooleanArray");
                                return Integer.valueOf(((boolean[]) obj).length);
                            }
                            break;
                    }
                }
                break;
            case 94434409:
                if (name2.equals("cause") && Intrinsics.areEqual(type, "kotlin.Throwable")) {
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Throwable");
                    return ((Throwable) obj).getCause();
                }
                break;
            case 110508916:
                if (name2.equals("toInt")) {
                    switch (type.hashCode()) {
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Integer.valueOf(((Integer) obj).intValue());
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Integer.valueOf(((Byte) obj).byteValue());
                            }
                            break;
                        case -1707368381:
                            if (type.equals("kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return Integer.valueOf(((Character) obj).charValue());
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Integer.valueOf((int) ((Long) obj).longValue());
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Integer.valueOf((int) ((Float) obj).floatValue());
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Integer.valueOf(((Short) obj).shortValue());
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Integer.valueOf((int) ((Double) obj).doubleValue());
                            }
                            break;
                        case 223764726:
                            if (type.equals("kotlin.Number")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
                                return Integer.valueOf(((Number) obj).intValue());
                            }
                            break;
                    }
                }
                break;
            case 147696667:
                if (name2.equals("hashCode")) {
                    switch (type.hashCode()) {
                        case -2133288097:
                            if (type.equals("kotlin.Any")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Any");
                                return Integer.valueOf(obj.hashCode());
                            }
                            break;
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Integer.valueOf(Integer.hashCode(((Integer) obj).intValue()));
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Integer.valueOf(Byte.hashCode(((Byte) obj).byteValue()));
                            }
                            break;
                        case -1707368381:
                            if (type.equals("kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return Integer.valueOf(Character.hashCode(((Character) obj).charValue()));
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Integer.valueOf(Long.hashCode(((Long) obj).longValue()));
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Integer.valueOf(Float.hashCode(((Float) obj).floatValue()));
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Integer.valueOf(Short.hashCode(((Short) obj).shortValue()));
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Integer.valueOf(Double.hashCode(((Double) obj).doubleValue()));
                            }
                            break;
                        case 366142910:
                            if (type.equals("kotlin.String")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                                return Integer.valueOf(((String) obj).hashCode());
                            }
                            break;
                        case 411999259:
                            if (type.equals("kotlin.Boolean")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                                return Integer.valueOf(Boolean.hashCode(((Boolean) obj).booleanValue()));
                            }
                            break;
                    }
                }
                break;
            case 414819457:
                if (name2.equals("unaryMinus")) {
                    switch (type.hashCode()) {
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Integer.valueOf(-((Integer) obj).intValue());
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Integer.valueOf(-((Byte) obj).byteValue());
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Long.valueOf(-((Long) obj).longValue());
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Float.valueOf(-((Float) obj).floatValue());
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Integer.valueOf(-((Short) obj).shortValue());
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Double.valueOf(-((Double) obj).doubleValue());
                            }
                            break;
                    }
                }
                break;
            case 954925063:
                if (name2.equals(CrashHianalyticsData.MESSAGE) && Intrinsics.areEqual(type, "kotlin.Throwable")) {
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Throwable");
                    return ((Throwable) obj).getMessage();
                }
                break;
            case 983305065:
                if (name2.equals("unaryPlus")) {
                    switch (type.hashCode()) {
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Integer.valueOf(((Integer) obj).intValue());
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Integer.valueOf(((Byte) obj).byteValue());
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Long.valueOf(((Long) obj).longValue());
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Float.valueOf(((Float) obj).floatValue());
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Integer.valueOf(((Short) obj).shortValue());
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Double.valueOf(((Double) obj).doubleValue());
                            }
                            break;
                    }
                }
                break;
            case 1182533742:
                if (name2.equals("iterator")) {
                    switch (type.hashCode()) {
                        case -1581820393:
                            if (type.equals("kotlin.IntArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.IntArray");
                                return ArrayIteratorsKt.iterator((int[]) obj);
                            }
                            break;
                        case -1577754486:
                            if (type.equals("kotlin.FloatArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.FloatArray");
                                return ArrayIteratorsKt.iterator((float[]) obj);
                            }
                            break;
                        case -1390345460:
                            if (type.equals("kotlin.Array")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                                return ArrayIteratorKt.iterator((Object[]) obj);
                            }
                            break;
                        case -1163761552:
                            if (type.equals("kotlin.LongArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.LongArray");
                                return ArrayIteratorsKt.iterator((long[]) obj);
                            }
                            break;
                        case -993017989:
                            if (type.equals("kotlin.DoubleArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.DoubleArray");
                                return ArrayIteratorsKt.iterator((double[]) obj);
                            }
                            break;
                        case 270963670:
                            if (type.equals("kotlin.CharArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.CharArray");
                                return ArrayIteratorsKt.iterator((char[]) obj);
                            }
                            break;
                        case 951944548:
                            if (type.equals("kotlin.ByteArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ByteArray");
                                return ArrayIteratorsKt.iterator((byte[]) obj);
                            }
                            break;
                        case 1005081194:
                            if (type.equals("kotlin.ShortArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ShortArray");
                                return ArrayIteratorsKt.iterator((short[]) obj);
                            }
                            break;
                        case 1980710654:
                            if (type.equals("kotlin.BooleanArray")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.BooleanArray");
                                return ArrayIteratorsKt.iterator((boolean[]) obj);
                            }
                            break;
                    }
                }
                break;
            case 1642711306:
                if (name2.equals(BuiltInOperatorNames.CHECK_NOT_NULL) && Intrinsics.areEqual(type, "T0?")) {
                    Intrinsics.checkNotNull(obj);
                    return obj;
                }
                break;
            case 2084073004:
                if (name2.equals("toDouble")) {
                    switch (type.hashCode()) {
                        case -2133280414:
                            if (type.equals("kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Double.valueOf(((Integer) obj).intValue());
                            }
                            break;
                        case -1707381259:
                            if (type.equals("kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Double.valueOf(((Byte) obj).byteValue());
                            }
                            break;
                        case -1707368381:
                            if (type.equals("kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return Double.valueOf(((Character) obj).charValue());
                            }
                            break;
                        case -1707093143:
                            if (type.equals("kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Double.valueOf(((Long) obj).longValue());
                            }
                            break;
                        case -1385909489:
                            if (type.equals("kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Double.valueOf(((Float) obj).floatValue());
                            }
                            break;
                        case -1374022353:
                            if (type.equals("kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Double.valueOf(((Short) obj).shortValue());
                            }
                            break;
                        case -67829378:
                            if (type.equals("kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Double.valueOf(((Double) obj).doubleValue());
                            }
                            break;
                        case 223764726:
                            if (type.equals("kotlin.Number")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
                                return Double.valueOf(((Number) obj).doubleValue());
                            }
                            break;
                    }
                }
                break;
        }
        throw new InterpreterMethodNotFoundError("Unknown function: " + name2 + '(' + type + ')');
    }

    /* JADX WARN: Code duplicated, block: B:1111:0x1b40  */
    /* JADX WARN: Code duplicated, block: B:203:0x03dc  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final Object interpretBinaryFunction(String name2, String typeA, String typeB, Object obj, Object obj2) throws InterpreterMethodNotFoundError {
        String str;
        boolean z;
        boolean z2;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(typeA, "typeA");
        Intrinsics.checkNotNullParameter(typeB, "typeB");
        switch (name2.hashCode()) {
            case -1880078536:
                str = name2;
                if (str.equals(BuiltInOperatorNames.LESS_OR_EQUAL)) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                int iIntValue = ((Integer) obj).intValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Boolean.valueOf(iIntValue <= ((Integer) obj2).intValue());
                            }
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte") && Intrinsics.areEqual(typeB, "kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                byte bByteValue = ((Byte) obj).byteValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                return Boolean.valueOf(bByteValue <= ((Byte) obj2).byteValue());
                            }
                            break;
                        case -1707368381:
                            if (typeA.equals("kotlin.Char") && Intrinsics.areEqual(typeB, "kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                char cCharValue = ((Character) obj).charValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Char");
                                return Boolean.valueOf(Intrinsics.compare((int) cCharValue, (int) ((Character) obj2).charValue()) <= 0);
                            }
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long") && Intrinsics.areEqual(typeB, "kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                long jLongValue = ((Long) obj).longValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                return Boolean.valueOf(jLongValue <= ((Long) obj2).longValue());
                            }
                            break;
                        case -1385909489:
                            if (typeA.equals("kotlin.Float") && Intrinsics.areEqual(typeB, "kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                float fFloatValue = ((Float) obj).floatValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                return Boolean.valueOf(fFloatValue <= ((Float) obj2).floatValue());
                            }
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short") && Intrinsics.areEqual(typeB, "kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                short sShortValue = ((Short) obj).shortValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                return Boolean.valueOf(sShortValue <= ((Short) obj2).shortValue());
                            }
                            break;
                        case -67829378:
                            if (typeA.equals("kotlin.Double") && Intrinsics.areEqual(typeB, "kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                double dDoubleValue = ((Double) obj).doubleValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                return Boolean.valueOf(dDoubleValue <= ((Double) obj2).doubleValue());
                            }
                            break;
                    }
                }
                break;
            case -1412718016:
                if (name2.equals("compareTo")) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue2 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(Intrinsics.compare(iIntValue2, ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue3 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(Intrinsics.compare(iIntValue3, (int) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        long jIntValue = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Integer.valueOf(Intrinsics.compare(jIntValue, ((Long) obj2).longValue()));
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        float fIntValue = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Integer.valueOf(Float.compare(fIntValue, ((Float) obj2).floatValue()));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue4 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(Intrinsics.compare(iIntValue4, (int) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        double dIntValue = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Integer.valueOf(Double.compare(dIntValue, ((Double) obj2).doubleValue()));
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue2 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(Intrinsics.compare((int) bByteValue2, ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue3 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(Intrinsics.compare((int) bByteValue3, (int) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        long jByteValue = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Integer.valueOf(Intrinsics.compare(jByteValue, ((Long) obj2).longValue()));
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        float fByteValue = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Integer.valueOf(Float.compare(fByteValue, ((Float) obj2).floatValue()));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue4 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(Intrinsics.compare((int) bByteValue4, (int) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        double dByteValue = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Integer.valueOf(Double.compare(dByteValue, ((Double) obj2).doubleValue()));
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707368381:
                            if (typeA.equals("kotlin.Char") && Intrinsics.areEqual(typeB, "kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                char cCharValue2 = ((Character) obj).charValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Char");
                                return Integer.valueOf(Intrinsics.compare((int) cCharValue2, (int) ((Character) obj2).charValue()));
                            }
                            str = name2;
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue2 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(Intrinsics.compare(jLongValue2, ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue3 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(Intrinsics.compare(jLongValue3, ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue4 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Integer.valueOf(Intrinsics.compare(jLongValue4, ((Long) obj2).longValue()));
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        float fLongValue = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Integer.valueOf(Float.compare(fLongValue, ((Float) obj2).floatValue()));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue5 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(Intrinsics.compare(jLongValue5, ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        double dLongValue = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Integer.valueOf(Double.compare(dLongValue, ((Double) obj2).doubleValue()));
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1385909489:
                            if (typeA.equals("kotlin.Float")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue2 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(Float.compare(fFloatValue2, ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue3 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(Float.compare(fFloatValue3, ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue4 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Integer.valueOf(Float.compare(fFloatValue4, ((Long) obj2).longValue()));
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue5 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Integer.valueOf(Float.compare(fFloatValue5, ((Float) obj2).floatValue()));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue6 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(Float.compare(fFloatValue6, ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        double dFloatValue = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Integer.valueOf(Double.compare(dFloatValue, ((Double) obj2).doubleValue()));
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1377832953:
                            if (typeA.equals("kotlin.Comparable") && Intrinsics.areEqual(typeB, ExifInterface.GPS_DIRECTION_TRUE)) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Comparable<kotlin.Any?>");
                                return Integer.valueOf(((Comparable) obj).compareTo(obj2));
                            }
                            str = name2;
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue2 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(Intrinsics.compare((int) sShortValue2, ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue3 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(Intrinsics.compare((int) sShortValue3, (int) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        long jShortValue = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Integer.valueOf(Intrinsics.compare(jShortValue, ((Long) obj2).longValue()));
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        float fShortValue = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Integer.valueOf(Float.compare(fShortValue, ((Float) obj2).floatValue()));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue4 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(Intrinsics.compare((int) sShortValue4, (int) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        double dShortValue = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Integer.valueOf(Double.compare(dShortValue, ((Double) obj2).doubleValue()));
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -67829378:
                            if (typeA.equals("kotlin.Double")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue2 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(Double.compare(dDoubleValue2, ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue3 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(Double.compare(dDoubleValue3, ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue4 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Integer.valueOf(Double.compare(dDoubleValue4, ((Long) obj2).longValue()));
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue5 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Integer.valueOf(Double.compare(dDoubleValue5, ((Float) obj2).floatValue()));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue6 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(Double.compare(dDoubleValue6, ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue7 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Integer.valueOf(Double.compare(dDoubleValue7, ((Double) obj2).doubleValue()));
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case 366142910:
                            if (typeA.equals("kotlin.String") && Intrinsics.areEqual(typeB, "kotlin.String")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
                                return Integer.valueOf(((String) obj).compareTo((String) obj2));
                            }
                            str = name2;
                            break;
                        case 411999259:
                            if (typeA.equals("kotlin.Boolean") && Intrinsics.areEqual(typeB, "kotlin.Boolean")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Boolean");
                                return Integer.valueOf(Boolean.compare(zBooleanValue, ((Boolean) obj2).booleanValue()));
                            }
                            str = name2;
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case -1295482945:
                if (name2.equals(Namer.EQUALS_METHOD_NAME)) {
                    switch (typeA.hashCode()) {
                        case -2133288097:
                            if (typeA.equals("kotlin.Any") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Any");
                                return Boolean.valueOf(obj.equals(obj2));
                            }
                            str = name2;
                            break;
                        case -2133280414:
                            if (typeA.equals("kotlin.Int") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                return Boolean.valueOf(((Integer) obj).equals(obj2));
                            }
                            str = name2;
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                return Boolean.valueOf(((Byte) obj).equals(obj2));
                            }
                            str = name2;
                            break;
                        case -1707368381:
                            if (typeA.equals("kotlin.Char") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                return Boolean.valueOf(((Character) obj).equals(obj2));
                            }
                            str = name2;
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                return Boolean.valueOf(((Long) obj).equals(obj2));
                            }
                            str = name2;
                            break;
                        case -1385909489:
                            if (typeA.equals("kotlin.Float") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                return Boolean.valueOf(((Float) obj).equals(obj2));
                            }
                            str = name2;
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                return Boolean.valueOf(((Short) obj).equals(obj2));
                            }
                            str = name2;
                            break;
                        case -67829378:
                            if (typeA.equals("kotlin.Double") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                return Boolean.valueOf(((Double) obj).equals(obj2));
                            }
                            str = name2;
                            break;
                        case 366142910:
                            if (typeA.equals("kotlin.String") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                                return Boolean.valueOf(((String) obj).equals(obj2));
                            }
                            str = name2;
                            break;
                        case 411999259:
                            if (typeA.equals("kotlin.Boolean") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                                return Boolean.valueOf(((Boolean) obj).equals(obj2));
                            }
                            str = name2;
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case -907625191:
                if (!name2.equals(BuiltInOperatorNames.IEEE754_EQUALS)) {
                    str = name2;
                } else if (Intrinsics.areEqual(typeA, "kotlin.Float?")) {
                    if (Intrinsics.areEqual(typeB, "kotlin.Float?")) {
                        return Boolean.valueOf(Intrinsics.areEqual((Float) obj, (Float) obj2));
                    }
                    str = name2;
                } else {
                    if (Intrinsics.areEqual(typeA, "kotlin.Double?") && Intrinsics.areEqual(typeB, "kotlin.Double?")) {
                        return Boolean.valueOf(Intrinsics.areEqual((Double) obj, (Double) obj2));
                    }
                    str = name2;
                }
                break;
            case XKeySym.XK_Thai_saraaimaimuan /* 3555 */:
                if (!name2.equals("or")) {
                    str = name2;
                } else {
                    int iHashCode = typeA.hashCode();
                    if (iHashCode != -2133280414) {
                        if (iHashCode != -1707093143) {
                            if (iHashCode == 411999259 && typeA.equals("kotlin.Boolean") && Intrinsics.areEqual(typeB, "kotlin.Boolean")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                                boolean zBooleanValue2 = ((Boolean) obj).booleanValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Boolean");
                                return Boolean.valueOf(((Boolean) obj2).booleanValue() | zBooleanValue2);
                            }
                            str = name2;
                        } else {
                            if (typeA.equals("kotlin.Long") && Intrinsics.areEqual(typeB, "kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                long jLongValue6 = ((Long) obj).longValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                return Long.valueOf(jLongValue6 | ((Long) obj2).longValue());
                            }
                            str = name2;
                        }
                    } else {
                        if (typeA.equals("kotlin.Int") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                            int iIntValue5 = ((Integer) obj).intValue();
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            return Integer.valueOf(iIntValue5 | ((Integer) obj2).intValue());
                        }
                        str = name2;
                    }
                }
                break;
            case 96727:
                if (!name2.equals("and")) {
                    str = name2;
                } else {
                    int iHashCode2 = typeA.hashCode();
                    if (iHashCode2 != -2133280414) {
                        if (iHashCode2 != -1707093143) {
                            if (iHashCode2 == 411999259 && typeA.equals("kotlin.Boolean") && Intrinsics.areEqual(typeB, "kotlin.Boolean")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                                boolean zBooleanValue3 = ((Boolean) obj).booleanValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Boolean");
                                return Boolean.valueOf(((Boolean) obj2).booleanValue() & zBooleanValue3);
                            }
                            str = name2;
                        } else {
                            if (typeA.equals("kotlin.Long") && Intrinsics.areEqual(typeB, "kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                long jLongValue7 = ((Long) obj).longValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                return Long.valueOf(jLongValue7 & ((Long) obj2).longValue());
                            }
                            str = name2;
                        }
                    } else {
                        if (typeA.equals("kotlin.Int") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                            int iIntValue6 = ((Integer) obj).intValue();
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            return Integer.valueOf(iIntValue6 & ((Integer) obj2).intValue());
                        }
                        str = name2;
                    }
                }
                break;
            case 99473:
                if (name2.equals("div")) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue7 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(iIntValue7 / ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue8 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(iIntValue8 / ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        long jIntValue2 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jIntValue2 / ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        float fIntValue2 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fIntValue2 / ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue9 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(iIntValue9 / ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        double dIntValue2 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dIntValue2 / ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue5 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(bByteValue5 / ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue6 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(bByteValue6 / ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        long jByteValue2 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jByteValue2 / ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        float fByteValue2 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fByteValue2 / ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue7 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(bByteValue7 / ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        double dByteValue2 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dByteValue2 / ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue8 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Long.valueOf(jLongValue8 / ((long) ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue9 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Long.valueOf(jLongValue9 / ((long) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue10 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jLongValue10 / ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        float fLongValue2 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fLongValue2 / ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue11 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Long.valueOf(jLongValue11 / ((long) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        double dLongValue2 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dLongValue2 / ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1385909489:
                            if (typeA.equals("kotlin.Float")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue7 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Float.valueOf(fFloatValue7 / ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue8 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Float.valueOf(fFloatValue8 / ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue9 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Float.valueOf(fFloatValue9 / ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue10 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fFloatValue10 / ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue11 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Float.valueOf(fFloatValue11 / ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        double dFloatValue2 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dFloatValue2 / ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue5 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(sShortValue5 / ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue6 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(sShortValue6 / ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        long jShortValue2 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jShortValue2 / ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        float fShortValue2 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fShortValue2 / ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue7 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(sShortValue7 / ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        double dShortValue2 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dShortValue2 / ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -67829378:
                            if (typeA.equals("kotlin.Double")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue8 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Double.valueOf(dDoubleValue8 / ((double) ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue9 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Double.valueOf(dDoubleValue9 / ((double) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue10 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Double.valueOf(dDoubleValue10 / ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue11 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Double.valueOf(dDoubleValue11 / ((double) ((Float) obj2).floatValue()));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue12 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Double.valueOf(dDoubleValue12 / ((double) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue13 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dDoubleValue13 / ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 102230:
                if (name2.equals("get")) {
                    switch (typeA.hashCode()) {
                        case -1581820393:
                            if (typeA.equals("kotlin.IntArray") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.IntArray");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Integer.valueOf(((int[]) obj)[((Integer) obj2).intValue()]);
                            }
                            str = name2;
                            break;
                        case -1577754486:
                            if (typeA.equals("kotlin.FloatArray") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.FloatArray");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Float.valueOf(((float[]) obj)[((Integer) obj2).intValue()]);
                            }
                            str = name2;
                            break;
                        case -1390345460:
                            if (typeA.equals("kotlin.Array") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return ((Object[]) obj)[((Integer) obj2).intValue()];
                            }
                            str = name2;
                            break;
                        case -1163761552:
                            if (typeA.equals("kotlin.LongArray") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.LongArray");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Long.valueOf(((long[]) obj)[((Integer) obj2).intValue()]);
                            }
                            str = name2;
                            break;
                        case -993017989:
                            if (typeA.equals("kotlin.DoubleArray") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.DoubleArray");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Double.valueOf(((double[]) obj)[((Integer) obj2).intValue()]);
                            }
                            str = name2;
                            break;
                        case 270963670:
                            if (typeA.equals("kotlin.CharArray") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.CharArray");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Character.valueOf(((char[]) obj)[((Integer) obj2).intValue()]);
                            }
                            str = name2;
                            break;
                        case 366142910:
                            if (typeA.equals("kotlin.String") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Character.valueOf(((String) obj).charAt(((Integer) obj2).intValue()));
                            }
                            str = name2;
                            break;
                        case 382699812:
                            if (typeA.equals("kotlin.CharSequence") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.CharSequence");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Character.valueOf(((CharSequence) obj).charAt(((Integer) obj2).intValue()));
                            }
                            str = name2;
                            break;
                        case 951944548:
                            if (typeA.equals("kotlin.ByteArray") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ByteArray");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Byte.valueOf(((byte[]) obj)[((Integer) obj2).intValue()]);
                            }
                            str = name2;
                            break;
                        case 1005081194:
                            if (typeA.equals("kotlin.ShortArray") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ShortArray");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Short.valueOf(((short[]) obj)[((Integer) obj2).intValue()]);
                            }
                            str = name2;
                            break;
                        case 1980710654:
                            if (typeA.equals("kotlin.BooleanArray") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.BooleanArray");
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Boolean.valueOf(((boolean[]) obj)[((Integer) obj2).intValue()]);
                            }
                            str = name2;
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 108290:
                if (name2.equals("mod")) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue10 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue11 = ((Integer) obj2).intValue();
                                        int i = iIntValue10 % iIntValue11;
                                        return Integer.valueOf(i + (iIntValue11 & (((i ^ iIntValue11) & ((-i) | i)) >> 31)));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue12 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue8 = ((Byte) obj2).byteValue();
                                        int i2 = iIntValue12 % bByteValue8;
                                        return Byte.valueOf((byte) (i2 + (bByteValue8 & (((i2 ^ bByteValue8) & ((-i2) | i2)) >> 31))));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue13 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue12 = ((Long) obj2).longValue();
                                        long j = ((long) iIntValue13) % jLongValue12;
                                        return Long.valueOf(j + (jLongValue12 & (((j ^ jLongValue12) & ((-j) | j)) >> 63)));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue14 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue8 = ((Short) obj2).shortValue();
                                        int i3 = iIntValue14 % sShortValue8;
                                        return Short.valueOf((short) (i3 + (sShortValue8 & (((i3 ^ sShortValue8) & ((-i3) | i3)) >> 31))));
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue9 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue15 = ((Integer) obj2).intValue();
                                        int i4 = bByteValue9 % iIntValue15;
                                        return Integer.valueOf(i4 + (iIntValue15 & (((i4 ^ iIntValue15) & ((-i4) | i4)) >> 31)));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue10 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue11 = ((Byte) obj2).byteValue();
                                        int i5 = bByteValue10 % bByteValue11;
                                        return Byte.valueOf((byte) (i5 + (bByteValue11 & (((i5 ^ bByteValue11) & ((-i5) | i5)) >> 31))));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue12 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue13 = ((Long) obj2).longValue();
                                        long j2 = ((long) bByteValue12) % jLongValue13;
                                        return Long.valueOf(j2 + (jLongValue13 & (((j2 ^ jLongValue13) & ((-j2) | j2)) >> 63)));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue13 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue9 = ((Short) obj2).shortValue();
                                        int i6 = bByteValue13 % sShortValue9;
                                        return Short.valueOf((short) (i6 + (sShortValue9 & (((i6 ^ sShortValue9) & ((-i6) | i6)) >> 31))));
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue14 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        long jIntValue3 = ((Integer) obj2).intValue();
                                        long j3 = jLongValue14 % jIntValue3;
                                        return Integer.valueOf((int) (j3 + (jIntValue3 & (((j3 ^ jIntValue3) & ((-j3) | j3)) >> 63))));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue15 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        long jByteValue3 = ((Byte) obj2).byteValue();
                                        long j4 = jLongValue15 % jByteValue3;
                                        return Byte.valueOf((byte) (j4 + (jByteValue3 & (((j4 ^ jByteValue3) & ((-j4) | j4)) >> 63))));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue16 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue17 = ((Long) obj2).longValue();
                                        long j5 = jLongValue16 % jLongValue17;
                                        return Long.valueOf(j5 + (jLongValue17 & (((j5 ^ jLongValue17) & ((-j5) | j5)) >> 63)));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue18 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        long jShortValue3 = ((Short) obj2).shortValue();
                                        long j6 = jLongValue18 % jShortValue3;
                                        return Short.valueOf((short) (j6 + (jShortValue3 & (((j6 ^ jShortValue3) & ((-j6) | j6)) >> 63))));
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1385909489:
                            if (!typeA.equals("kotlin.Float")) {
                                str = name2;
                            } else {
                                if (Intrinsics.areEqual(typeB, "kotlin.Float")) {
                                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                    float fFloatValue12 = ((Float) obj).floatValue();
                                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                    float fFloatValue13 = ((Float) obj2).floatValue();
                                    float f = fFloatValue12 % fFloatValue13;
                                    if (f != 0.0f && Math.signum(f) != Math.signum(fFloatValue13)) {
                                        f += fFloatValue13;
                                    }
                                    return Float.valueOf(f);
                                }
                                if (Intrinsics.areEqual(typeB, "kotlin.Double")) {
                                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                    float fFloatValue14 = ((Float) obj).floatValue();
                                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                    double dDoubleValue14 = ((Double) obj2).doubleValue();
                                    double d = ((double) fFloatValue14) % dDoubleValue14;
                                    if (d != 0.0d && Math.signum(d) != Math.signum(dDoubleValue14)) {
                                        d += dDoubleValue14;
                                    }
                                    return Double.valueOf(d);
                                }
                                str = name2;
                            }
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue10 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue16 = ((Integer) obj2).intValue();
                                        int i7 = sShortValue10 % iIntValue16;
                                        return Integer.valueOf(i7 + (iIntValue16 & (((i7 ^ iIntValue16) & ((-i7) | i7)) >> 31)));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue11 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue14 = ((Byte) obj2).byteValue();
                                        int i8 = sShortValue11 % bByteValue14;
                                        return Byte.valueOf((byte) (i8 + (bByteValue14 & (((i8 ^ bByteValue14) & ((-i8) | i8)) >> 31))));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue12 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue19 = ((Long) obj2).longValue();
                                        long j7 = ((long) sShortValue12) % jLongValue19;
                                        return Long.valueOf(j7 + (jLongValue19 & (((j7 ^ jLongValue19) & ((-j7) | j7)) >> 63)));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue13 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue14 = ((Short) obj2).shortValue();
                                        int i9 = sShortValue13 % sShortValue14;
                                        return Short.valueOf((short) (i9 + (sShortValue14 & (((i9 ^ sShortValue14) & ((-i9) | i9)) >> 31))));
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -67829378:
                            if (!typeA.equals("kotlin.Double")) {
                                str = name2;
                            } else {
                                if (Intrinsics.areEqual(typeB, "kotlin.Float")) {
                                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                    double dDoubleValue15 = ((Double) obj).doubleValue();
                                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                    double dFloatValue3 = ((Float) obj2).floatValue();
                                    double d2 = dDoubleValue15 % dFloatValue3;
                                    if (d2 != 0.0d && Math.signum(d2) != Math.signum(dFloatValue3)) {
                                        d2 += dFloatValue3;
                                    }
                                    return Double.valueOf(d2);
                                }
                                if (Intrinsics.areEqual(typeB, "kotlin.Double")) {
                                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                    double dDoubleValue16 = ((Double) obj).doubleValue();
                                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                    double dDoubleValue17 = ((Double) obj2).doubleValue();
                                    double d3 = dDoubleValue16 % dDoubleValue17;
                                    if (d3 != 0.0d && Math.signum(d3) != Math.signum(dDoubleValue17)) {
                                        d3 += dDoubleValue17;
                                    }
                                    return Double.valueOf(d3);
                                }
                                str = name2;
                            }
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 112794:
                if (name2.equals("rem")) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue17 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(iIntValue17 % ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue18 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(iIntValue18 % ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        long jIntValue4 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jIntValue4 % ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        float fIntValue3 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fIntValue3 % ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue19 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(iIntValue19 % ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        double dIntValue3 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dIntValue3 % ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue15 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(bByteValue15 % ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue16 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(bByteValue16 % ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        long jByteValue4 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jByteValue4 % ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        float fByteValue3 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fByteValue3 % ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue17 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(bByteValue17 % ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        double dByteValue3 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dByteValue3 % ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue20 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Long.valueOf(jLongValue20 % ((long) ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue21 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Long.valueOf(jLongValue21 % ((long) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue22 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jLongValue22 % ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        float fLongValue3 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fLongValue3 % ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue23 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Long.valueOf(jLongValue23 % ((long) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        double dLongValue3 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dLongValue3 % ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1385909489:
                            if (typeA.equals("kotlin.Float")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue15 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Float.valueOf(fFloatValue15 % ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue16 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Float.valueOf(fFloatValue16 % ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue17 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Float.valueOf(fFloatValue17 % ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue18 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fFloatValue18 % ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue19 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Float.valueOf(fFloatValue19 % ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        double dFloatValue4 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dFloatValue4 % ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue15 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(sShortValue15 % ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue16 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(sShortValue16 % ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        long jShortValue4 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jShortValue4 % ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        float fShortValue3 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fShortValue3 % ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue17 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(sShortValue17 % ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        double dShortValue3 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dShortValue3 % ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -67829378:
                            if (typeA.equals("kotlin.Double")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue18 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Double.valueOf(dDoubleValue18 % ((double) ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue19 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Double.valueOf(dDoubleValue19 % ((double) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue20 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Double.valueOf(dDoubleValue20 % ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue21 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Double.valueOf(dDoubleValue21 % ((double) ((Float) obj2).floatValue()));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue22 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Double.valueOf(dDoubleValue22 % ((double) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue23 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dDoubleValue23 % ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 113847:
                if (!name2.equals("shl")) {
                    str = name2;
                } else if (Intrinsics.areEqual(typeA, "kotlin.Int")) {
                    if (Intrinsics.areEqual(typeB, "kotlin.Int")) {
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                        int iIntValue20 = ((Integer) obj).intValue();
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                        return Integer.valueOf(iIntValue20 << ((Integer) obj2).intValue());
                    }
                    str = name2;
                } else {
                    if (Intrinsics.areEqual(typeA, "kotlin.Long") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                        long jLongValue24 = ((Long) obj).longValue();
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                        return Long.valueOf(jLongValue24 << ((Integer) obj2).intValue());
                    }
                    str = name2;
                }
                break;
            case 113853:
                if (!name2.equals("shr")) {
                    str = name2;
                } else if (Intrinsics.areEqual(typeA, "kotlin.Int")) {
                    if (Intrinsics.areEqual(typeB, "kotlin.Int")) {
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                        int iIntValue21 = ((Integer) obj).intValue();
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                        return Integer.valueOf(iIntValue21 >> ((Integer) obj2).intValue());
                    }
                    str = name2;
                } else {
                    if (Intrinsics.areEqual(typeA, "kotlin.Long") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                        long jLongValue25 = ((Long) obj).longValue();
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                        return Long.valueOf(jLongValue25 >> ((Integer) obj2).intValue());
                    }
                    str = name2;
                }
                break;
            case 118875:
                if (!name2.equals("xor")) {
                    str = name2;
                } else {
                    int iHashCode3 = typeA.hashCode();
                    if (iHashCode3 != -2133280414) {
                        if (iHashCode3 != -1707093143) {
                            if (iHashCode3 == 411999259 && typeA.equals("kotlin.Boolean") && Intrinsics.areEqual(typeB, "kotlin.Boolean")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                                boolean zBooleanValue4 = ((Boolean) obj).booleanValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Boolean");
                                return Boolean.valueOf(((Boolean) obj2).booleanValue() ^ zBooleanValue4);
                            }
                            str = name2;
                        } else {
                            if (typeA.equals("kotlin.Long") && Intrinsics.areEqual(typeB, "kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                long jLongValue26 = ((Long) obj).longValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                return Long.valueOf(jLongValue26 ^ ((Long) obj2).longValue());
                            }
                            str = name2;
                        }
                    } else {
                        if (typeA.equals("kotlin.Int") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                            int iIntValue22 = ((Integer) obj).intValue();
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            return Integer.valueOf(iIntValue22 ^ ((Integer) obj2).intValue());
                        }
                        str = name2;
                    }
                }
                break;
            case 2135640:
                if (name2.equals(BuiltInOperatorNames.EQEQ) && Intrinsics.areEqual(typeA, "kotlin.Any?") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                    return Boolean.valueOf(Intrinsics.areEqual(obj, obj2));
                }
                str = name2;
                break;
            case 2434822:
                if (name2.equals(BuiltInOperatorNames.OROR) && Intrinsics.areEqual(typeA, "kotlin.Boolean") && Intrinsics.areEqual(typeB, "kotlin.Boolean")) {
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                    if (((Boolean) obj).booleanValue()) {
                        z = true;
                    } else {
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Boolean");
                        if (((Boolean) obj2).booleanValue()) {
                            z = true;
                        } else {
                            z = false;
                        }
                    }
                    return Boolean.valueOf(z);
                }
                str = name2;
                break;
            case 3318169:
                if (name2.equals(BuiltInOperatorNames.LESS)) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                int iIntValue23 = ((Integer) obj).intValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Boolean.valueOf(iIntValue23 < ((Integer) obj2).intValue());
                            }
                            str = name2;
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte") && Intrinsics.areEqual(typeB, "kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                byte bByteValue18 = ((Byte) obj).byteValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                return Boolean.valueOf(bByteValue18 < ((Byte) obj2).byteValue());
                            }
                            str = name2;
                            break;
                        case -1707368381:
                            if (typeA.equals("kotlin.Char") && Intrinsics.areEqual(typeB, "kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                char cCharValue3 = ((Character) obj).charValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Char");
                                return Boolean.valueOf(Intrinsics.compare((int) cCharValue3, (int) ((Character) obj2).charValue()) < 0);
                            }
                            str = name2;
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long") && Intrinsics.areEqual(typeB, "kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                long jLongValue27 = ((Long) obj).longValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                return Boolean.valueOf(jLongValue27 < ((Long) obj2).longValue());
                            }
                            str = name2;
                            break;
                        case -1385909489:
                            if (typeA.equals("kotlin.Float") && Intrinsics.areEqual(typeB, "kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                float fFloatValue20 = ((Float) obj).floatValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                return Boolean.valueOf(fFloatValue20 < ((Float) obj2).floatValue());
                            }
                            str = name2;
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short") && Intrinsics.areEqual(typeB, "kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                short sShortValue18 = ((Short) obj).shortValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                return Boolean.valueOf(sShortValue18 < ((Short) obj2).shortValue());
                            }
                            str = name2;
                            break;
                        case -67829378:
                            if (typeA.equals("kotlin.Double") && Intrinsics.areEqual(typeB, "kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                double dDoubleValue24 = ((Double) obj).doubleValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                return Boolean.valueOf(dDoubleValue24 < ((Double) obj2).doubleValue());
                            }
                            str = name2;
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 3444122:
                if (name2.equals("plus")) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue24 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(iIntValue24 + ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue25 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(iIntValue25 + ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        long jIntValue5 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jIntValue5 + ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        float fIntValue4 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fIntValue4 + ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue26 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(iIntValue26 + ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        double dIntValue4 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dIntValue4 + ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue19 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(bByteValue19 + ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue20 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(bByteValue20 + ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        long jByteValue5 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jByteValue5 + ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        float fByteValue4 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fByteValue4 + ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue21 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(bByteValue21 + ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        double dByteValue4 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dByteValue4 + ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707368381:
                            if (typeA.equals("kotlin.Char") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                char cCharValue4 = ((Character) obj).charValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Character.valueOf((char) (cCharValue4 + ((Integer) obj2).intValue()));
                            }
                            str = name2;
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue28 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Long.valueOf(jLongValue28 + ((long) ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue29 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Long.valueOf(jLongValue29 + ((long) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue30 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jLongValue30 + ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        float fLongValue4 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fLongValue4 + ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue31 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Long.valueOf(jLongValue31 + ((long) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        double dLongValue4 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dLongValue4 + ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1385909489:
                            if (typeA.equals("kotlin.Float")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue21 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Float.valueOf(fFloatValue21 + ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue22 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Float.valueOf(fFloatValue22 + ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue23 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Float.valueOf(fFloatValue23 + ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue24 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fFloatValue24 + ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue25 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Float.valueOf(fFloatValue25 + ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        double dFloatValue5 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dFloatValue5 + ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue19 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(sShortValue19 + ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue20 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(sShortValue20 + ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        long jShortValue5 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jShortValue5 + ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        float fShortValue4 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fShortValue4 + ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue21 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(sShortValue21 + ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        double dShortValue4 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dShortValue4 + ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -67829378:
                            if (typeA.equals("kotlin.Double")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue25 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Double.valueOf(dDoubleValue25 + ((double) ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue26 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Double.valueOf(dDoubleValue26 + ((double) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue27 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Double.valueOf(dDoubleValue27 + ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue28 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Double.valueOf(dDoubleValue28 + ((double) ((Float) obj2).floatValue()));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue29 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Double.valueOf(dDoubleValue29 + ((double) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue30 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dDoubleValue30 + ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case 366142910:
                            if (typeA.equals("kotlin.String") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                                StringBuilder sb = new StringBuilder();
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                                return sb.append((String) obj).append(obj2).toString();
                            }
                            str = name2;
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 3599400:
                if (!name2.equals("ushr")) {
                    str = name2;
                } else if (Intrinsics.areEqual(typeA, "kotlin.Int")) {
                    if (Intrinsics.areEqual(typeB, "kotlin.Int")) {
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                        int iIntValue27 = ((Integer) obj).intValue();
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                        return Integer.valueOf(iIntValue27 >>> ((Integer) obj2).intValue());
                    }
                    str = name2;
                } else {
                    if (Intrinsics.areEqual(typeA, "kotlin.Long") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                        long jLongValue32 = ((Long) obj).longValue();
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                        return Long.valueOf(jLongValue32 >>> ((Integer) obj2).intValue());
                    }
                    str = name2;
                }
                break;
            case 103901296:
                if (name2.equals("minus")) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue28 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(iIntValue28 - ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue29 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(iIntValue29 - ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        long jIntValue6 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jIntValue6 - ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        float fIntValue5 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fIntValue5 - ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue30 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(iIntValue30 - ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        double dIntValue5 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dIntValue5 - ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue22 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(bByteValue22 - ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue23 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(bByteValue23 - ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        long jByteValue6 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jByteValue6 - ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        float fByteValue5 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fByteValue5 - ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue24 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(bByteValue24 - ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        double dByteValue5 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dByteValue5 - ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707368381:
                            if (!typeA.equals("kotlin.Char")) {
                                str = name2;
                            } else {
                                if (Intrinsics.areEqual(typeB, "kotlin.Char")) {
                                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                    char cCharValue5 = ((Character) obj).charValue();
                                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Char");
                                    return Integer.valueOf(cCharValue5 - ((Character) obj2).charValue());
                                }
                                if (Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                    char cCharValue6 = ((Character) obj).charValue();
                                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                    return Character.valueOf((char) (cCharValue6 - ((Integer) obj2).intValue()));
                                }
                                str = name2;
                            }
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue33 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Long.valueOf(jLongValue33 - ((long) ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue34 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Long.valueOf(jLongValue34 - ((long) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue35 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jLongValue35 - ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        float fLongValue5 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fLongValue5 - ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue36 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Long.valueOf(jLongValue36 - ((long) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        double dLongValue5 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dLongValue5 - ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1385909489:
                            if (typeA.equals("kotlin.Float")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue26 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Float.valueOf(fFloatValue26 - ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue27 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Float.valueOf(fFloatValue27 - ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue28 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Float.valueOf(fFloatValue28 - ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue29 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fFloatValue29 - ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue30 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Float.valueOf(fFloatValue30 - ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        double dFloatValue6 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dFloatValue6 - ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue22 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(sShortValue22 - ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue23 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(sShortValue23 - ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        long jShortValue6 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jShortValue6 - ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        float fShortValue5 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fShortValue5 - ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue24 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(sShortValue24 - ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        double dShortValue5 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dShortValue5 - ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -67829378:
                            if (typeA.equals("kotlin.Double")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue31 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Double.valueOf(dDoubleValue31 - ((double) ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue32 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Double.valueOf(dDoubleValue32 - ((double) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue33 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Double.valueOf(dDoubleValue33 - ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue34 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Double.valueOf(dDoubleValue34 - ((double) ((Float) obj2).floatValue()));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue35 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Double.valueOf(dDoubleValue35 - ((double) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue36 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dDoubleValue36 - ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 110364486:
                if (name2.equals("times")) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue31 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(iIntValue31 * ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue32 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(iIntValue32 * ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        long jIntValue7 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jIntValue7 * ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        float fIntValue6 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fIntValue6 * ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue33 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(iIntValue33 * ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        double dIntValue6 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dIntValue6 * ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue25 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(bByteValue25 * ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue26 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(bByteValue26 * ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        long jByteValue7 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jByteValue7 * ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        float fByteValue6 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fByteValue6 * ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue27 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(bByteValue27 * ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        double dByteValue6 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dByteValue6 * ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue37 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Long.valueOf(jLongValue37 * ((long) ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue38 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Long.valueOf(jLongValue38 * ((long) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue39 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jLongValue39 * ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        float fLongValue6 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fLongValue6 * ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue40 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Long.valueOf(jLongValue40 * ((long) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        double dLongValue6 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dLongValue6 * ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1385909489:
                            if (typeA.equals("kotlin.Float")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue31 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Float.valueOf(fFloatValue31 * ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue32 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Float.valueOf(fFloatValue32 * ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue33 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Float.valueOf(fFloatValue33 * ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue34 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fFloatValue34 * ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        float fFloatValue35 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Float.valueOf(fFloatValue35 * ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                        double dFloatValue7 = ((Float) obj).floatValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dFloatValue7 * ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue25 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Integer.valueOf(sShortValue25 * ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue26 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Integer.valueOf(sShortValue26 * ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        long jShortValue7 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Long.valueOf(jShortValue7 * ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        float fShortValue6 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Float.valueOf(fShortValue6 * ((Float) obj2).floatValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue27 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Integer.valueOf(sShortValue27 * ((Short) obj2).shortValue());
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        double dShortValue6 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dShortValue6 * ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -67829378:
                            if (typeA.equals("kotlin.Double")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue37 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return Double.valueOf(dDoubleValue37 * ((double) ((Integer) obj2).intValue()));
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue38 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return Double.valueOf(dDoubleValue38 * ((double) ((Byte) obj2).byteValue()));
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue39 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return Double.valueOf(dDoubleValue39 * ((Long) obj2).longValue());
                                    case "kotlin.Float":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue40 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                        return Double.valueOf(dDoubleValue40 * ((double) ((Float) obj2).floatValue()));
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue41 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return Double.valueOf(dDoubleValue41 * ((double) ((Short) obj2).shortValue()));
                                    case "kotlin.Double":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                        double dDoubleValue42 = ((Double) obj).doubleValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                        return Double.valueOf(dDoubleValue42 * ((Double) obj2).doubleValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 283601914:
                if (name2.equals(BuiltInOperatorNames.GREATER)) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                int iIntValue34 = ((Integer) obj).intValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Boolean.valueOf(iIntValue34 > ((Integer) obj2).intValue());
                            }
                            str = name2;
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte") && Intrinsics.areEqual(typeB, "kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                byte bByteValue28 = ((Byte) obj).byteValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                return Boolean.valueOf(bByteValue28 > ((Byte) obj2).byteValue());
                            }
                            str = name2;
                            break;
                        case -1707368381:
                            if (typeA.equals("kotlin.Char") && Intrinsics.areEqual(typeB, "kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                char cCharValue7 = ((Character) obj).charValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Char");
                                return Boolean.valueOf(Intrinsics.compare((int) cCharValue7, (int) ((Character) obj2).charValue()) > 0);
                            }
                            str = name2;
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long") && Intrinsics.areEqual(typeB, "kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                long jLongValue41 = ((Long) obj).longValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                return Boolean.valueOf(jLongValue41 > ((Long) obj2).longValue());
                            }
                            str = name2;
                            break;
                        case -1385909489:
                            if (typeA.equals("kotlin.Float") && Intrinsics.areEqual(typeB, "kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                float fFloatValue36 = ((Float) obj).floatValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                return Boolean.valueOf(fFloatValue36 > ((Float) obj2).floatValue());
                            }
                            str = name2;
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short") && Intrinsics.areEqual(typeB, "kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                short sShortValue28 = ((Short) obj).shortValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                return Boolean.valueOf(sShortValue28 > ((Short) obj2).shortValue());
                            }
                            str = name2;
                            break;
                        case -67829378:
                            if (typeA.equals("kotlin.Double") && Intrinsics.areEqual(typeB, "kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                double dDoubleValue43 = ((Double) obj).doubleValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                return Boolean.valueOf(dDoubleValue43 > ((Double) obj2).doubleValue());
                            }
                            str = name2;
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 376847799:
                if (name2.equals(BuiltInOperatorNames.GREATER_OR_EQUAL)) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int") && Intrinsics.areEqual(typeB, "kotlin.Int")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                int iIntValue35 = ((Integer) obj).intValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                return Boolean.valueOf(iIntValue35 >= ((Integer) obj2).intValue());
                            }
                            str = name2;
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte") && Intrinsics.areEqual(typeB, "kotlin.Byte")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                byte bByteValue29 = ((Byte) obj).byteValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                return Boolean.valueOf(bByteValue29 >= ((Byte) obj2).byteValue());
                            }
                            str = name2;
                            break;
                        case -1707368381:
                            if (typeA.equals("kotlin.Char") && Intrinsics.areEqual(typeB, "kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                char cCharValue8 = ((Character) obj).charValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Char");
                                return Boolean.valueOf(Intrinsics.compare((int) cCharValue8, (int) ((Character) obj2).charValue()) >= 0);
                            }
                            str = name2;
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long") && Intrinsics.areEqual(typeB, "kotlin.Long")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                long jLongValue42 = ((Long) obj).longValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                return Boolean.valueOf(jLongValue42 >= ((Long) obj2).longValue());
                            }
                            str = name2;
                            break;
                        case -1385909489:
                            if (typeA.equals("kotlin.Float") && Intrinsics.areEqual(typeB, "kotlin.Float")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
                                float fFloatValue37 = ((Float) obj).floatValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Float");
                                return Boolean.valueOf(fFloatValue37 >= ((Float) obj2).floatValue());
                            }
                            str = name2;
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short") && Intrinsics.areEqual(typeB, "kotlin.Short")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                short sShortValue29 = ((Short) obj).shortValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                return Boolean.valueOf(sShortValue29 >= ((Short) obj2).shortValue());
                            }
                            str = name2;
                            break;
                        case -67829378:
                            if (typeA.equals("kotlin.Double") && Intrinsics.areEqual(typeB, "kotlin.Double")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
                                double dDoubleValue44 = ((Double) obj).doubleValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Double");
                                return Boolean.valueOf(dDoubleValue44 >= ((Double) obj2).doubleValue());
                            }
                            str = name2;
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 977987736:
                if (name2.equals("rangeTo")) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue36 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return new IntRange(iIntValue36, ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue37 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return new IntRange(iIntValue37, ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        long jIntValue8 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return new LongRange(jIntValue8, ((Long) obj2).longValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue38 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return new IntRange(iIntValue38, ((Short) obj2).shortValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue30 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return new IntRange(bByteValue30, ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue31 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return new IntRange(bByteValue31, ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        long jByteValue8 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return new LongRange(jByteValue8, ((Long) obj2).longValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue32 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return new IntRange(bByteValue32, ((Short) obj2).shortValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707368381:
                            if (typeA.equals("kotlin.Char") && Intrinsics.areEqual(typeB, "kotlin.Char")) {
                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Char");
                                char cCharValue9 = ((Character) obj).charValue();
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Char");
                                return new CharRange(cCharValue9, ((Character) obj2).charValue());
                            }
                            str = name2;
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue43 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return new LongRange(jLongValue43, ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue44 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return new LongRange(jLongValue44, ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue45 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return new LongRange(jLongValue45, ((Long) obj2).longValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue46 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return new LongRange(jLongValue46, ((Short) obj2).shortValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue30 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        return new IntRange(sShortValue30, ((Integer) obj2).intValue());
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue31 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        return new IntRange(sShortValue31, ((Byte) obj2).byteValue());
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        long jShortValue8 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        return new LongRange(jShortValue8, ((Long) obj2).longValue());
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue32 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        return new IntRange(sShortValue32, ((Short) obj2).shortValue());
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 1935020192:
                if (name2.equals(BuiltInOperatorNames.ANDAND) && Intrinsics.areEqual(typeA, "kotlin.Boolean") && Intrinsics.areEqual(typeB, "kotlin.Boolean")) {
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                    if (((Boolean) obj).booleanValue()) {
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Boolean");
                        if (((Boolean) obj2).booleanValue()) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                    } else {
                        z2 = false;
                    }
                    return Boolean.valueOf(z2);
                }
                str = name2;
                break;
            case 2022956261:
                if (name2.equals("floorDiv")) {
                    switch (typeA.hashCode()) {
                        case -2133280414:
                            if (typeA.equals("kotlin.Int")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue39 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue40 = ((Integer) obj2).intValue();
                                        int i10 = iIntValue39 / iIntValue40;
                                        if ((iIntValue39 ^ iIntValue40) < 0 && iIntValue40 * i10 != iIntValue39) {
                                            i10--;
                                        }
                                        return Integer.valueOf(i10);
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue41 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue33 = ((Byte) obj2).byteValue();
                                        int i11 = iIntValue41 / bByteValue33;
                                        if ((iIntValue41 ^ bByteValue33) < 0 && bByteValue33 * i11 != iIntValue41) {
                                            i11--;
                                        }
                                        return Integer.valueOf(i11);
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue42 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue47 = ((Long) obj2).longValue();
                                        long j8 = iIntValue42;
                                        long j9 = j8 / jLongValue47;
                                        if ((j8 ^ jLongValue47) < 0 && jLongValue47 * j9 != j8) {
                                            j9--;
                                        }
                                        return Long.valueOf(j9);
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue43 = ((Integer) obj).intValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue33 = ((Short) obj2).shortValue();
                                        int i12 = iIntValue43 / sShortValue33;
                                        if ((iIntValue43 ^ sShortValue33) < 0 && sShortValue33 * i12 != iIntValue43) {
                                            i12--;
                                        }
                                        return Integer.valueOf(i12);
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707381259:
                            if (typeA.equals("kotlin.Byte")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue34 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue44 = ((Integer) obj2).intValue();
                                        int i13 = bByteValue34 / iIntValue44;
                                        if ((bByteValue34 ^ iIntValue44) < 0 && iIntValue44 * i13 != bByteValue34) {
                                            i13--;
                                        }
                                        return Integer.valueOf(i13);
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue35 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue36 = ((Byte) obj2).byteValue();
                                        int i14 = bByteValue35 / bByteValue36;
                                        if ((bByteValue35 ^ bByteValue36) < 0 && bByteValue36 * i14 != bByteValue35) {
                                            i14--;
                                        }
                                        return Integer.valueOf(i14);
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue37 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue48 = ((Long) obj2).longValue();
                                        long j10 = bByteValue37;
                                        long j11 = j10 / jLongValue48;
                                        if ((j10 ^ jLongValue48) < 0 && jLongValue48 * j11 != j10) {
                                            j11--;
                                        }
                                        return Long.valueOf(j11);
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue38 = ((Byte) obj).byteValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue34 = ((Short) obj2).shortValue();
                                        int i15 = bByteValue38 / sShortValue34;
                                        if ((bByteValue38 ^ sShortValue34) < 0 && sShortValue34 * i15 != bByteValue38) {
                                            i15--;
                                        }
                                        return Integer.valueOf(i15);
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1707093143:
                            if (typeA.equals("kotlin.Long")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue49 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        long jIntValue9 = ((Integer) obj2).intValue();
                                        long j12 = jLongValue49 / jIntValue9;
                                        if ((jLongValue49 ^ jIntValue9) < 0 && jIntValue9 * j12 != jLongValue49) {
                                            j12--;
                                        }
                                        return Long.valueOf(j12);
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue50 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        long jByteValue9 = ((Byte) obj2).byteValue();
                                        long j13 = jLongValue50 / jByteValue9;
                                        if ((jLongValue50 ^ jByteValue9) < 0 && jByteValue9 * j13 != jLongValue50) {
                                            j13--;
                                        }
                                        return Long.valueOf(j13);
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue51 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue52 = ((Long) obj2).longValue();
                                        long j14 = jLongValue51 / jLongValue52;
                                        if ((jLongValue51 ^ jLongValue52) < 0 && jLongValue52 * j14 != jLongValue51) {
                                            j14--;
                                        }
                                        return Long.valueOf(j14);
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue53 = ((Long) obj).longValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        long jShortValue9 = ((Short) obj2).shortValue();
                                        long j15 = jLongValue53 / jShortValue9;
                                        if ((jLongValue53 ^ jShortValue9) < 0 && jShortValue9 * j15 != jLongValue53) {
                                            j15--;
                                        }
                                        return Long.valueOf(j15);
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        case -1374022353:
                            if (typeA.equals("kotlin.Short")) {
                                switch (typeB) {
                                    case "kotlin.Int":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue35 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                                        int iIntValue45 = ((Integer) obj2).intValue();
                                        int i16 = sShortValue35 / iIntValue45;
                                        if ((sShortValue35 ^ iIntValue45) < 0 && iIntValue45 * i16 != sShortValue35) {
                                            i16--;
                                        }
                                        return Integer.valueOf(i16);
                                    case "kotlin.Byte":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue36 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Byte");
                                        byte bByteValue39 = ((Byte) obj2).byteValue();
                                        int i17 = sShortValue36 / bByteValue39;
                                        if ((sShortValue36 ^ bByteValue39) < 0 && bByteValue39 * i17 != sShortValue36) {
                                            i17--;
                                        }
                                        return Integer.valueOf(i17);
                                    case "kotlin.Long":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue37 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Long");
                                        long jLongValue54 = ((Long) obj2).longValue();
                                        long j16 = sShortValue37;
                                        long j17 = j16 / jLongValue54;
                                        if ((j16 ^ jLongValue54) < 0 && jLongValue54 * j17 != j16) {
                                            j17--;
                                        }
                                        return Long.valueOf(j17);
                                    case "kotlin.Short":
                                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue38 = ((Short) obj).shortValue();
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Short");
                                        short sShortValue39 = ((Short) obj2).shortValue();
                                        int i18 = sShortValue38 / sShortValue39;
                                        if ((sShortValue38 ^ sShortValue39) < 0 && sShortValue39 * i18 != sShortValue38) {
                                            i18--;
                                        }
                                        return Integer.valueOf(i18);
                                    default:
                                        str = name2;
                                        break;
                                }
                            } else {
                                str = name2;
                                break;
                            }
                            break;
                        default:
                            str = name2;
                            break;
                    }
                } else {
                    str = name2;
                    break;
                }
                break;
            case 2052352260:
                if (name2.equals(BuiltInOperatorNames.EQEQEQ) && Intrinsics.areEqual(typeA, "kotlin.Any?") && Intrinsics.areEqual(typeB, "kotlin.Any?")) {
                    return Boolean.valueOf(!((obj instanceof Proxy) && (obj2 instanceof Proxy)) ? obj != obj2 : ((Proxy) obj).getState() != ((Proxy) obj2).getState());
                }
                str = name2;
                break;
            default:
                str = name2;
                break;
        }
        throw new InterpreterMethodNotFoundError("Unknown function: " + str + '(' + typeA + ", " + typeB + ')');
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final Object interpretTernaryFunction(String name2, String typeA, String typeB, String typeC, Object obj, Object obj2, Object obj3) throws InterpreterMethodNotFoundError {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(typeA, "typeA");
        Intrinsics.checkNotNullParameter(typeB, "typeB");
        Intrinsics.checkNotNullParameter(typeC, "typeC");
        if (!Intrinsics.areEqual(name2, "subSequence")) {
            if (Intrinsics.areEqual(name2, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX)) {
                switch (typeA.hashCode()) {
                    case -1581820393:
                        if (typeA.equals("kotlin.IntArray") && Intrinsics.areEqual(typeB, "kotlin.Int") && Intrinsics.areEqual(typeC, "kotlin.Int")) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.IntArray");
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            int iIntValue = ((Integer) obj2).intValue();
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
                            ((int[]) obj)[iIntValue] = ((Integer) obj3).intValue();
                            return Unit.INSTANCE;
                        }
                        break;
                    case -1577754486:
                        if (typeA.equals("kotlin.FloatArray") && Intrinsics.areEqual(typeB, "kotlin.Int") && Intrinsics.areEqual(typeC, "kotlin.Float")) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.FloatArray");
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            int iIntValue2 = ((Integer) obj2).intValue();
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Float");
                            ((float[]) obj)[iIntValue2] = ((Float) obj3).floatValue();
                            return Unit.INSTANCE;
                        }
                        break;
                    case -1390345460:
                        if (typeA.equals("kotlin.Array") && Intrinsics.areEqual(typeB, "kotlin.Int") && Intrinsics.areEqual(typeC, ExifInterface.GPS_DIRECTION_TRUE)) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            ((Object[]) obj)[((Integer) obj2).intValue()] = obj3;
                            return Unit.INSTANCE;
                        }
                        break;
                    case -1163761552:
                        if (typeA.equals("kotlin.LongArray") && Intrinsics.areEqual(typeB, "kotlin.Int") && Intrinsics.areEqual(typeC, "kotlin.Long")) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.LongArray");
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            int iIntValue3 = ((Integer) obj2).intValue();
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Long");
                            ((long[]) obj)[iIntValue3] = ((Long) obj3).longValue();
                            return Unit.INSTANCE;
                        }
                        break;
                    case -993017989:
                        if (typeA.equals("kotlin.DoubleArray") && Intrinsics.areEqual(typeB, "kotlin.Int") && Intrinsics.areEqual(typeC, "kotlin.Double")) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.DoubleArray");
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            int iIntValue4 = ((Integer) obj2).intValue();
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Double");
                            ((double[]) obj)[iIntValue4] = ((Double) obj3).doubleValue();
                            return Unit.INSTANCE;
                        }
                        break;
                    case 270963670:
                        if (typeA.equals("kotlin.CharArray") && Intrinsics.areEqual(typeB, "kotlin.Int") && Intrinsics.areEqual(typeC, "kotlin.Char")) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.CharArray");
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            int iIntValue5 = ((Integer) obj2).intValue();
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Char");
                            ((char[]) obj)[iIntValue5] = ((Character) obj3).charValue();
                            return Unit.INSTANCE;
                        }
                        break;
                    case 951944548:
                        if (typeA.equals("kotlin.ByteArray") && Intrinsics.areEqual(typeB, "kotlin.Int") && Intrinsics.areEqual(typeC, "kotlin.Byte")) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ByteArray");
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            int iIntValue6 = ((Integer) obj2).intValue();
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Byte");
                            ((byte[]) obj)[iIntValue6] = ((Byte) obj3).byteValue();
                            return Unit.INSTANCE;
                        }
                        break;
                    case 1005081194:
                        if (typeA.equals("kotlin.ShortArray") && Intrinsics.areEqual(typeB, "kotlin.Int") && Intrinsics.areEqual(typeC, "kotlin.Short")) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ShortArray");
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            int iIntValue7 = ((Integer) obj2).intValue();
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Short");
                            ((short[]) obj)[iIntValue7] = ((Short) obj3).shortValue();
                            return Unit.INSTANCE;
                        }
                        break;
                    case 1980710654:
                        if (typeA.equals("kotlin.BooleanArray") && Intrinsics.areEqual(typeB, "kotlin.Int") && Intrinsics.areEqual(typeC, "kotlin.Boolean")) {
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.BooleanArray");
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                            int iIntValue8 = ((Integer) obj2).intValue();
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Boolean");
                            ((boolean[]) obj)[iIntValue8] = ((Boolean) obj3).booleanValue();
                            return Unit.INSTANCE;
                        }
                        break;
                }
            }
        } else if (Intrinsics.areEqual(typeA, "kotlin.String")) {
            if (Intrinsics.areEqual(typeB, "kotlin.Int") && Intrinsics.areEqual(typeC, "kotlin.Int")) {
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
                int iIntValue9 = ((Integer) obj2).intValue();
                Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
                return ((String) obj).subSequence(iIntValue9, ((Integer) obj3).intValue());
            }
        } else if (Intrinsics.areEqual(typeA, "kotlin.CharSequence") && Intrinsics.areEqual(typeB, "kotlin.Int") && Intrinsics.areEqual(typeC, "kotlin.Int")) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.CharSequence");
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue10 = ((Integer) obj2).intValue();
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
            return ((CharSequence) obj).subSequence(iIntValue10, ((Integer) obj3).intValue());
        }
        throw new InterpreterMethodNotFoundError("Unknown function: " + name2 + '(' + typeA + ", " + typeB + ", " + typeC + ')');
    }
}
