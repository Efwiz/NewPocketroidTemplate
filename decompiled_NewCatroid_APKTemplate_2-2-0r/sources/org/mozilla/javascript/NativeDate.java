package org.mozilla.javascript;

import com.gaurav.avnc.vnc.XKeySym;
import io.netty.util.internal.StringUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import okhttp3.internal.http2.Http2Connection;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes4.dex */
final class NativeDate extends IdScriptableObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ConstructorId_UTC = -1;
    private static final int ConstructorId_now = -3;
    private static final int ConstructorId_parse = -2;
    private static final Object DATE_TAG = "Date";
    private static final double HalfTimeDomain = 8.64E15d;
    private static final double HoursPerDay = 24.0d;
    private static final int Id_constructor = 1;
    private static final int Id_getDate = 17;
    private static final int Id_getDay = 19;
    private static final int Id_getFullYear = 13;
    private static final int Id_getHours = 21;
    private static final int Id_getMilliseconds = 27;
    private static final int Id_getMinutes = 23;
    private static final int Id_getMonth = 15;
    private static final int Id_getSeconds = 25;
    private static final int Id_getTime = 11;
    private static final int Id_getTimezoneOffset = 29;
    private static final int Id_getUTCDate = 18;
    private static final int Id_getUTCDay = 20;
    private static final int Id_getUTCFullYear = 14;
    private static final int Id_getUTCHours = 22;
    private static final int Id_getUTCMilliseconds = 28;
    private static final int Id_getUTCMinutes = 24;
    private static final int Id_getUTCMonth = 16;
    private static final int Id_getUTCSeconds = 26;
    private static final int Id_getYear = 12;
    private static final int Id_setDate = 39;
    private static final int Id_setFullYear = 43;
    private static final int Id_setHours = 37;
    private static final int Id_setMilliseconds = 31;
    private static final int Id_setMinutes = 35;
    private static final int Id_setMonth = 41;
    private static final int Id_setSeconds = 33;
    private static final int Id_setTime = 30;
    private static final int Id_setUTCDate = 40;
    private static final int Id_setUTCFullYear = 44;
    private static final int Id_setUTCHours = 38;
    private static final int Id_setUTCMilliseconds = 32;
    private static final int Id_setUTCMinutes = 36;
    private static final int Id_setUTCMonth = 42;
    private static final int Id_setUTCSeconds = 34;
    private static final int Id_setYear = 45;
    private static final int Id_toDateString = 4;
    private static final int Id_toGMTString = 8;
    private static final int Id_toISOString = 46;
    private static final int Id_toJSON = 47;
    private static final int Id_toLocaleDateString = 7;
    private static final int Id_toLocaleString = 5;
    private static final int Id_toLocaleTimeString = 6;
    private static final int Id_toSource = 9;
    private static final int Id_toString = 2;
    private static final int Id_toTimeString = 3;
    private static final int Id_toUTCString = 8;
    private static final int Id_valueOf = 10;
    private static final double LocalTZA;
    private static final int MAXARGS = 7;
    private static final int MAX_PROTOTYPE_ID = 47;
    private static final double MinutesPerDay = 1440.0d;
    private static final double MinutesPerHour = 60.0d;
    private static final double SecondsPerDay = 86400.0d;
    private static final double SecondsPerHour = 3600.0d;
    private static final double SecondsPerMinute = 60.0d;
    private static final String js_NaN_date_str = "Invalid Date";
    private static final DateFormat localeDateFormatter;
    private static final DateFormat localeDateTimeFormatter;
    private static final DateFormat localeTimeFormatter;
    private static final double msPerDay = 8.64E7d;
    private static final double msPerHour = 3600000.0d;
    private static final double msPerMinute = 60000.0d;
    private static final double msPerSecond = 1000.0d;
    private static final long serialVersionUID = -8307438915861678966L;
    private static final TimeZone thisTimeZone;
    private static final DateFormat timeZoneFormatter;
    private double date;

    private static double MakeDate(double d, double d2) {
        return (d * msPerDay) + d2;
    }

    private static double MakeTime(double d, double d2, double d3, double d4) {
        return (((((d * 60.0d) + d2) * 60.0d) + d3) * msPerSecond) + d4;
    }

    private static double TimeWithinDay(double d) {
        double d2 = d % msPerDay;
        return d2 < 0.0d ? d2 + msPerDay : d2;
    }

    private static int msFromTime(double d) {
        double d2 = d % msPerSecond;
        if (d2 < 0.0d) {
            d2 += msPerSecond;
        }
        return (int) d2;
    }

    static {
        TimeZone timeZone = TimeZone.getDefault();
        thisTimeZone = timeZone;
        LocalTZA = timeZone.getRawOffset();
        timeZoneFormatter = new SimpleDateFormat("zzz");
        localeDateTimeFormatter = new SimpleDateFormat("MMMM d, yyyy h:mm:ss a z");
        localeDateFormatter = new SimpleDateFormat("MMMM d, yyyy");
        localeTimeFormatter = new SimpleDateFormat("h:mm:ss a z");
    }

    static void init(Scriptable scriptable, boolean z) {
        NativeDate nativeDate = new NativeDate();
        nativeDate.date = Double.NaN;
        nativeDate.exportAsJSClass(47, scriptable, z);
    }

    private NativeDate() {
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "Date";
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object getDefaultValue(Class<?> cls) {
        if (cls == null) {
            cls = ScriptRuntime.StringClass;
        }
        return super.getDefaultValue(cls);
    }

    double getJSTimeValue() {
        return this.date;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        Object obj = DATE_TAG;
        addIdFunctionProperty(idFunctionObject, obj, -3, "now", 0);
        addIdFunctionProperty(idFunctionObject, obj, -2, "parse", 1);
        addIdFunctionProperty(idFunctionObject, obj, -1, "UTC", 7);
        super.fillConstructorProperties(idFunctionObject);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i) {
        String str;
        String str2;
        int i2 = 4;
        int i3 = 0;
        switch (i) {
            case 1:
                i2 = 7;
                str = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                i3 = i2;
                str2 = str;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 2:
                str2 = "toString";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 3:
                str2 = "toTimeString";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 4:
                str2 = "toDateString";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 5:
                str2 = "toLocaleString";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 6:
                str2 = "toLocaleTimeString";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 7:
                str2 = "toLocaleDateString";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 8:
                str2 = "toUTCString";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 9:
                str2 = "toSource";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 10:
                str2 = "valueOf";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 11:
                str2 = "getTime";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 12:
                str2 = "getYear";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 13:
                str2 = "getFullYear";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 14:
                str2 = "getUTCFullYear";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 15:
                str2 = "getMonth";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 16:
                str2 = "getUTCMonth";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 17:
                str2 = "getDate";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 18:
                str2 = "getUTCDate";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 19:
                str2 = "getDay";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 20:
                str2 = "getUTCDay";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 21:
                str2 = "getHours";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 22:
                str2 = "getUTCHours";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 23:
                str2 = "getMinutes";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 24:
                str2 = "getUTCMinutes";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 25:
                str2 = "getSeconds";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 26:
                str2 = "getUTCSeconds";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 27:
                str2 = "getMilliseconds";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 28:
                str2 = "getUTCMilliseconds";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 29:
                str2 = "getTimezoneOffset";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 30:
                str2 = "setTime";
                i3 = 1;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 31:
                str2 = "setMilliseconds";
                i3 = 1;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 32:
                str2 = "setUTCMilliseconds";
                i3 = 1;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 33:
                str2 = "setSeconds";
                i3 = 2;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 34:
                str2 = "setUTCSeconds";
                i3 = 2;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 35:
                str2 = "setMinutes";
                i3 = 3;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 36:
                str2 = "setUTCMinutes";
                i3 = 3;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 37:
                str = "setHours";
                i3 = i2;
                str2 = str;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 38:
                str = "setUTCHours";
                i3 = i2;
                str2 = str;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 39:
                str2 = "setDate";
                i3 = 1;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 40:
                str2 = "setUTCDate";
                i3 = 1;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 41:
                str2 = "setMonth";
                i3 = 2;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 42:
                str2 = "setUTCMonth";
                i3 = 2;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 43:
                str2 = "setFullYear";
                i3 = 3;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 44:
                str2 = "setUTCFullYear";
                i3 = 3;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 45:
                str2 = "setYear";
                i3 = 1;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 46:
                str2 = "toISOString";
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            case 47:
                str2 = "toJSON";
                i3 = 1;
                initPrototypeMethod(DATE_TAG, i, str2, i3);
                return;
            default:
                throw new IllegalArgumentException(String.valueOf(i));
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        double dTimeClip;
        if (!idFunctionObject.hasTag(DATE_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == -3) {
            return ScriptRuntime.wrapNumber(now());
        }
        if (iMethodId == -2) {
            return ScriptRuntime.wrapNumber(date_parseString(ScriptRuntime.toString(objArr, 0)));
        }
        if (iMethodId == -1) {
            return ScriptRuntime.wrapNumber(jsStaticFunction_UTC(objArr));
        }
        if (iMethodId == 1) {
            if (scriptable2 != null) {
                return date_format(now(), 2);
            }
            return jsConstructor(objArr);
        }
        if (iMethodId == 47) {
            Scriptable object = ScriptRuntime.toObject(context, scriptable, scriptable2);
            Object primitive = ScriptRuntime.toPrimitive(object, ScriptRuntime.NumberClass);
            if (primitive instanceof Number) {
                double dDoubleValue = ((Number) primitive).doubleValue();
                if (Double.isNaN(dDoubleValue) || Double.isInfinite(dDoubleValue)) {
                    return null;
                }
            }
            Object property = ScriptableObject.getProperty(object, "toISOString");
            if (property == NOT_FOUND) {
                throw ScriptRuntime.typeError2("msg.function.not.found.in", "toISOString", ScriptRuntime.toString(object));
            }
            if (!(property instanceof Callable)) {
                throw ScriptRuntime.typeError3("msg.isnt.function.in", "toISOString", ScriptRuntime.toString(object), ScriptRuntime.toString(property));
            }
            Object objCall = ((Callable) property).call(context, scriptable, object, ScriptRuntime.emptyArgs);
            if (ScriptRuntime.isPrimitive(objCall)) {
                return objCall;
            }
            throw ScriptRuntime.typeError1("msg.toisostring.must.return.primitive", ScriptRuntime.toString(objCall));
        }
        if (!(scriptable2 instanceof NativeDate)) {
            throw incompatibleCallError(idFunctionObject);
        }
        NativeDate nativeDate = (NativeDate) scriptable2;
        double dYearFromTime = nativeDate.date;
        switch (iMethodId) {
            case 2:
            case 3:
            case 4:
                if (Double.isNaN(dYearFromTime)) {
                    return js_NaN_date_str;
                }
                return date_format(dYearFromTime, iMethodId);
            case 5:
            case 6:
            case 7:
                if (Double.isNaN(dYearFromTime)) {
                    return js_NaN_date_str;
                }
                return toLocale_helper(dYearFromTime, iMethodId);
            case 8:
                if (Double.isNaN(dYearFromTime)) {
                    return js_NaN_date_str;
                }
                return js_toUTCString(dYearFromTime);
            case 9:
                return "(new Date(" + ScriptRuntime.toString(dYearFromTime) + "))";
            case 10:
            case 11:
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 12:
            case 13:
            case 14:
                if (!Double.isNaN(dYearFromTime)) {
                    if (iMethodId != 14) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = YearFromTime(dYearFromTime);
                    if (iMethodId == 12 && (!context.hasFeature(1) || (1900.0d <= dYearFromTime && dYearFromTime < 2000.0d))) {
                        dYearFromTime -= 1900.0d;
                    }
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 15:
            case 16:
                if (!Double.isNaN(dYearFromTime)) {
                    if (iMethodId == 15) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = MonthFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 17:
            case 18:
                if (!Double.isNaN(dYearFromTime)) {
                    if (iMethodId == 17) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = DateFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 19:
            case 20:
                if (!Double.isNaN(dYearFromTime)) {
                    if (iMethodId == 19) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = WeekDay(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 21:
            case 22:
                if (!Double.isNaN(dYearFromTime)) {
                    if (iMethodId == 21) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = HourFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 23:
            case 24:
                if (!Double.isNaN(dYearFromTime)) {
                    if (iMethodId == 23) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = MinFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 25:
            case 26:
                if (!Double.isNaN(dYearFromTime)) {
                    if (iMethodId == 25) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = SecFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 27:
            case 28:
                if (!Double.isNaN(dYearFromTime)) {
                    if (iMethodId == 27) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = msFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 29:
                if (!Double.isNaN(dYearFromTime)) {
                    dYearFromTime = (dYearFromTime - LocalTime(dYearFromTime)) / msPerMinute;
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 30:
                double dTimeClip2 = TimeClip(ScriptRuntime.toNumber(objArr, 0));
                nativeDate.date = dTimeClip2;
                return ScriptRuntime.wrapNumber(dTimeClip2);
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
                double dMakeTime = makeTime(dYearFromTime, objArr, iMethodId);
                nativeDate.date = dMakeTime;
                return ScriptRuntime.wrapNumber(dMakeTime);
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                double dMakeDate = makeDate(dYearFromTime, objArr, iMethodId);
                nativeDate.date = dMakeDate;
                return ScriptRuntime.wrapNumber(dMakeDate);
            case 45:
                double number = ScriptRuntime.toNumber(objArr, 0);
                if (Double.isNaN(number) || Double.isInfinite(number)) {
                    dTimeClip = Double.NaN;
                } else {
                    double dLocalTime = Double.isNaN(dYearFromTime) ? 0.0d : LocalTime(dYearFromTime);
                    if (number >= 0.0d && number <= 99.0d) {
                        number += 1900.0d;
                    }
                    dTimeClip = TimeClip(internalUTC(MakeDate(MakeDay(number, MonthFromTime(dLocalTime), DateFromTime(dLocalTime)), TimeWithinDay(dLocalTime))));
                }
                nativeDate.date = dTimeClip;
                return ScriptRuntime.wrapNumber(dTimeClip);
            case 46:
                if (!Double.isNaN(dYearFromTime)) {
                    return js_toISOString(dYearFromTime);
                }
                throw ScriptRuntime.rangeError(ScriptRuntime.getMessage0("msg.invalid.date"));
            default:
                throw new IllegalArgumentException(String.valueOf(iMethodId));
        }
    }

    private static double Day(double d) {
        return Math.floor(d / msPerDay);
    }

    private static boolean IsLeapYear(int i) {
        return i % 4 == 0 && (i % 100 != 0 || i % 400 == 0);
    }

    private static double DayFromYear(double d) {
        return ((((d - 1970.0d) * 365.0d) + Math.floor((d - 1969.0d) / 4.0d)) - Math.floor((d - 1901.0d) / 100.0d)) + Math.floor((d - 1601.0d) / 400.0d);
    }

    private static double TimeFromYear(double d) {
        return DayFromYear(d) * msPerDay;
    }

    private static int YearFromTime(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return 0;
        }
        double dFloor = Math.floor(d / 3.1556952E10d) + 1970.0d;
        double dTimeFromYear = TimeFromYear(dFloor);
        if (dTimeFromYear > d) {
            dFloor -= 1.0d;
        } else if (dTimeFromYear + (DaysInYear(dFloor) * msPerDay) <= d) {
            dFloor += 1.0d;
        }
        return (int) dFloor;
    }

    private static double DayFromMonth(int i, int i2) {
        int i3;
        int i4;
        int i5 = i * 30;
        if (i >= 7) {
            i4 = i / 2;
        } else {
            if (i >= 2) {
                i4 = (i - 1) / 2;
            } else {
                i3 = i5 + i;
            }
            if (i >= 2 && IsLeapYear(i2)) {
                i3++;
            }
            return i3;
        }
        i3 = i5 + (i4 - 1);
        if (i >= 2) {
            i3++;
        }
        return i3;
    }

    private static double DaysInYear(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return Double.NaN;
        }
        return IsLeapYear((int) d) ? 366.0d : 365.0d;
    }

    private static int DaysInMonth(int i, int i2) {
        if (i2 == 2) {
            return IsLeapYear(i) ? 29 : 28;
        }
        return i2 >= 8 ? 31 - (i2 & 1) : (i2 & 1) + 30;
    }

    private static int MonthFromTime(double d) {
        int i;
        int iYearFromTime = YearFromTime(d);
        int iDay = (int) (Day(d) - DayFromYear(iYearFromTime));
        int i2 = iDay - 59;
        if (i2 < 0) {
            return i2 < -28 ? 0 : 1;
        }
        if (IsLeapYear(iYearFromTime)) {
            if (i2 == 0) {
                return 1;
            }
            i2 = iDay - 60;
        }
        int i3 = i2 / 30;
        switch (i3) {
            case 0:
                return 2;
            case 1:
                i = 31;
                break;
            case 2:
                i = 61;
                break;
            case 3:
                i = 92;
                break;
            case 4:
                i = 122;
                break;
            case 5:
                i = 153;
                break;
            case 6:
                i = 184;
                break;
            case 7:
                i = 214;
                break;
            case 8:
                i = 245;
                break;
            case 9:
                i = 275;
                break;
            case 10:
                return 11;
            default:
                throw Kit.codeBug();
        }
        return i2 >= i ? i3 + 2 : i3 + 1;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0059  */
    private static int DateFromTime(double d) {
        int i;
        int i2;
        int iYearFromTime = YearFromTime(d);
        int iDay = (int) (Day(d) - DayFromYear(iYearFromTime));
        int i3 = iDay - 59;
        if (i3 < 0) {
            return i3 < -28 ? iDay + 1 : iDay - 30;
        }
        if (IsLeapYear(iYearFromTime)) {
            if (i3 == 0) {
                return 29;
            }
            i3 = iDay - 60;
        }
        int i4 = 30;
        int i5 = 31;
        switch (i3 / 30) {
            case 0:
                return i3 + 1;
            case 1:
                i4 = 31;
                i2 = i3 - i5;
                if (i2 < 0) {
                    i2 += i4;
                }
                return i2 + 1;
            case 2:
                i5 = 61;
                i2 = i3 - i5;
                if (i2 < 0) {
                    i2 += i4;
                }
                return i2 + 1;
            case 3:
                i = 92;
                i4 = 31;
                i5 = i;
                i2 = i3 - i5;
                if (i2 < 0) {
                    i2 += i4;
                }
                return i2 + 1;
            case 4:
                i5 = 122;
                i2 = i3 - i5;
                if (i2 < 0) {
                    i2 += i4;
                }
                return i2 + 1;
            case 5:
                i = 153;
                i4 = 31;
                i5 = i;
                i2 = i3 - i5;
                if (i2 < 0) {
                    i2 += i4;
                }
                return i2 + 1;
            case 6:
                i = 184;
                i4 = 31;
                i5 = i;
                i2 = i3 - i5;
                if (i2 < 0) {
                    i2 += i4;
                }
                return i2 + 1;
            case 7:
                i5 = 214;
                i2 = i3 - i5;
                if (i2 < 0) {
                    i2 += i4;
                }
                return i2 + 1;
            case 8:
                i = 245;
                i4 = 31;
                i5 = i;
                i2 = i3 - i5;
                if (i2 < 0) {
                    i2 += i4;
                }
                return i2 + 1;
            case 9:
                i5 = 275;
                i2 = i3 - i5;
                if (i2 < 0) {
                    i2 += i4;
                }
                return i2 + 1;
            case 10:
                return i3 - 274;
            default:
                throw Kit.codeBug();
        }
    }

    private static int WeekDay(double d) {
        double dDay = (Day(d) + 4.0d) % 7.0d;
        if (dDay < 0.0d) {
            dDay += 7.0d;
        }
        return (int) dDay;
    }

    private static double now() {
        return System.currentTimeMillis();
    }

    private static double DaylightSavingTA(double d) {
        if (d < 0.0d) {
            d = MakeDate(MakeDay(EquivalentYear(YearFromTime(d)), MonthFromTime(d), DateFromTime(d)), TimeWithinDay(d));
        }
        if (thisTimeZone.inDaylightTime(new Date((long) d))) {
            return msPerHour;
        }
        return 0.0d;
    }

    private static int EquivalentYear(int i) {
        int iDayFromYear = (((int) DayFromYear(i)) + 4) % 7;
        if (iDayFromYear < 0) {
            iDayFromYear += 7;
        }
        if (IsLeapYear(i)) {
            switch (iDayFromYear) {
                case 0:
                    return 1984;
                case 1:
                    return XKeySym.XK_Greek_MU;
                case 2:
                    return 1980;
                case 3:
                    return XKeySym.XK_Greek_THETA;
                case 4:
                    return XKeySym.XK_Greek_upsilonaccent;
                case 5:
                    return XKeySym.XK_Greek_DELTA;
                case 6:
                    return XKeySym.XK_Greek_iotaaccent;
            }
        }
        switch (iDayFromYear) {
            case 0:
                return XKeySym.XK_Greek_upsilonaccentdieresis;
            case 1:
                return XKeySym.XK_Greek_iotadieresis;
            case 2:
                return XKeySym.XK_Greek_ALPHA;
            case 3:
                return XKeySym.XK_Greek_BETA;
            case 4:
                return 1981;
            case 5:
                return XKeySym.XK_Greek_etaaccent;
            case 6:
                return XKeySym.XK_Greek_upsilondieresis;
        }
        throw Kit.codeBug();
    }

    private static double LocalTime(double d) {
        return LocalTZA + d + DaylightSavingTA(d);
    }

    private static double internalUTC(double d) {
        double d2 = LocalTZA;
        return (d - d2) - DaylightSavingTA(d - d2);
    }

    private static int HourFromTime(double d) {
        double dFloor = Math.floor(d / msPerHour) % HoursPerDay;
        if (dFloor < 0.0d) {
            dFloor += HoursPerDay;
        }
        return (int) dFloor;
    }

    private static int MinFromTime(double d) {
        double dFloor = Math.floor(d / msPerMinute) % 60.0d;
        if (dFloor < 0.0d) {
            dFloor += 60.0d;
        }
        return (int) dFloor;
    }

    private static int SecFromTime(double d) {
        double dFloor = Math.floor(d / msPerSecond) % 60.0d;
        if (dFloor < 0.0d) {
            dFloor += 60.0d;
        }
        return (int) dFloor;
    }

    private static double MakeDay(double d, double d2, double d3) {
        double dFloor = d + Math.floor(d2 / 12.0d);
        double d4 = d2 % 12.0d;
        if (d4 < 0.0d) {
            d4 += 12.0d;
        }
        return ((Math.floor(TimeFromYear(dFloor) / msPerDay) + DayFromMonth((int) d4, (int) dFloor)) + d3) - 1.0d;
    }

    private static double TimeClip(double d) {
        if (Double.isNaN(d) || d == Double.POSITIVE_INFINITY || d == Double.NEGATIVE_INFINITY || Math.abs(d) > HalfTimeDomain) {
            return Double.NaN;
        }
        if (d > 0.0d) {
            return Math.floor(d + 0.0d);
        }
        return Math.ceil(d + 0.0d);
    }

    private static double date_msecFromDate(double d, double d2, double d3, double d4, double d5, double d6, double d7) {
        return MakeDate(MakeDay(d, d2, d3), MakeTime(d4, d5, d6, d7));
    }

    private static double date_msecFromArgs(Object[] objArr) {
        double[] dArr = new double[7];
        for (int i = 0; i < 7; i++) {
            if (i < objArr.length) {
                double number = ScriptRuntime.toNumber(objArr[i]);
                if (Double.isNaN(number) || Double.isInfinite(number)) {
                    return Double.NaN;
                }
                dArr[i] = ScriptRuntime.toInteger(objArr[i]);
            } else if (i == 2) {
                dArr[i] = 1.0d;
            } else {
                dArr[i] = 0.0d;
            }
        }
        double d = dArr[0];
        if (d >= 0.0d && d <= 99.0d) {
            dArr[0] = d + 1900.0d;
        }
        return date_msecFromDate(dArr[0], dArr[1], dArr[2], dArr[3], dArr[4], dArr[5], dArr[6]);
    }

    private static double jsStaticFunction_UTC(Object[] objArr) {
        if (objArr.length == 0) {
            return Double.NaN;
        }
        return TimeClip(date_msecFromArgs(objArr));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:14:0x0050  */
    /* JADX WARN: Code duplicated, block: B:69:0x00e5 A[PHI: r2 r13
      0x00e5: PHI (r2v36 char) = (r2v32 char), (r2v37 char) binds: [B:67:0x00e2, B:61:0x00d3] A[DONT_GENERATE, DONT_INLINE]
      0x00e5: PHI (r13v18 char) = (r13v15 char), (r13v19 char) binds: [B:67:0x00e2, B:61:0x00d3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:70:0x00e7 A[PHI: r2 r13
      0x00e7: PHI (r2v33 char) = (r2v32 char), (r2v32 char), (r2v37 char), (r2v37 char) binds: [B:66:0x00e0, B:67:0x00e2, B:60:0x00d1, B:61:0x00d3] A[DONT_GENERATE, DONT_INLINE]
      0x00e7: PHI (r13v16 char) = (r13v15 char), (r13v15 char), (r13v19 char), (r13v19 char) binds: [B:66:0x00e0, B:67:0x00e2, B:60:0x00d1, B:61:0x00d3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:78:0x00fa A[PHI: r13
      0x00fa: PHI (r13v13 char) = (r13v12 char), (r13v14 char) binds: [B:81:0x0100, B:76:0x00f7] A[DONT_GENERATE, DONT_INLINE]] */
    private static double parseISOString(String str) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        char c;
        char c2;
        char c3;
        char c4;
        int i8;
        int[] iArr = new int[9];
        iArr[0] = 1970;
        iArr[1] = 1;
        iArr[2] = 1;
        iArr[3] = 0;
        iArr[4] = 0;
        iArr[5] = 0;
        iArr[6] = 0;
        int i9 = -1;
        iArr[7] = -1;
        iArr[8] = -1;
        int length = str.length();
        if (length == 0) {
            i = 1;
            i2 = 1;
            i3 = 0;
            i4 = 0;
            i5 = 4;
        } else {
            char cCharAt = str.charAt(0);
            if (cCharAt == '+' || cCharAt == '-') {
                i = cCharAt == '-' ? -1 : 1;
                i4 = 1;
                i2 = 1;
                i3 = 0;
                i5 = 6;
            } else {
                if (cCharAt == 'T') {
                    i = 1;
                    i4 = 1;
                    i2 = 1;
                    i3 = 3;
                } else {
                    i = 1;
                    i2 = 1;
                    i3 = 0;
                    i4 = 0;
                }
                i5 = 4;
            }
        }
        while (true) {
            if (i3 != i9) {
                int i10 = i4 + (i3 == 0 ? i5 : i3 == 6 ? 3 : 2);
                if (i10 <= length) {
                    i7 = i4;
                    int i11 = 0;
                    while (true) {
                        if (i7 < i10) {
                            char cCharAt2 = str.charAt(i7);
                            if (cCharAt2 < '0' || cCharAt2 > '9') {
                                i6 = -1;
                                i3 = -1;
                            } else {
                                i11 = (i11 * 10) + (cCharAt2 - '0');
                                i7++;
                            }
                        } else {
                            iArr[i3] = i11;
                            if (i7 != length) {
                                i4 = i7 + 1;
                                char cCharAt3 = str.charAt(i7);
                                if (cCharAt3 == 'Z') {
                                    iArr[7] = 0;
                                    iArr[8] = 0;
                                    if (i3 == 4 || i3 == 5 || i3 == 6) {
                                        i7 = i4;
                                    }
                                } else {
                                    switch (i3) {
                                        case 0:
                                        case 1:
                                            c = '+';
                                            c2 = Util.C_TYPE_VARIABLE;
                                            c3 = '-';
                                            if (cCharAt3 != '-') {
                                                i3 = cCharAt3 != 'T' ? -1 : 3;
                                            } else {
                                                i3++;
                                            }
                                            break;
                                        case 2:
                                            c = '+';
                                            c2 = Util.C_TYPE_VARIABLE;
                                            i3 = cCharAt3 == 'T' ? 3 : -1;
                                            c3 = '-';
                                            break;
                                        case 3:
                                            c = '+';
                                            if (cCharAt3 == ':') {
                                                i3 = 4;
                                            } else {
                                                i3 = -1;
                                            }
                                            c2 = Util.C_TYPE_VARIABLE;
                                            c3 = '-';
                                            break;
                                        case 4:
                                            c = '+';
                                            if (cCharAt3 == ':') {
                                                i3 = 5;
                                            } else if (cCharAt3 == '+' || cCharAt3 == '-') {
                                                i3 = 7;
                                            } else {
                                                i3 = -1;
                                            }
                                            c2 = Util.C_TYPE_VARIABLE;
                                            c3 = '-';
                                            break;
                                        case 5:
                                            c4 = '-';
                                            c = '+';
                                            if (cCharAt3 == '.') {
                                                i8 = 6;
                                            } else if (cCharAt3 == '+' || cCharAt3 == '-') {
                                                i8 = 7;
                                            } else {
                                                i8 = -1;
                                            }
                                            c3 = c4;
                                            i3 = i8;
                                            c2 = Util.C_TYPE_VARIABLE;
                                            break;
                                        case 6:
                                            c = '+';
                                            c4 = '-';
                                            if (cCharAt3 == '+' || cCharAt3 == '-') {
                                                i8 = 7;
                                            } else {
                                                i8 = -1;
                                            }
                                            c3 = c4;
                                            i3 = i8;
                                            c2 = Util.C_TYPE_VARIABLE;
                                            break;
                                        case 7:
                                            if (cCharAt3 == ':') {
                                                i7 = i4;
                                            }
                                            i3 = 8;
                                            i4 = i7;
                                            c = '+';
                                            c2 = Util.C_TYPE_VARIABLE;
                                            c3 = '-';
                                            break;
                                        case 8:
                                            i3 = -1;
                                            c = '+';
                                            c2 = Util.C_TYPE_VARIABLE;
                                            c3 = '-';
                                            break;
                                        default:
                                            c = '+';
                                            c2 = Util.C_TYPE_VARIABLE;
                                            c3 = '-';
                                            break;
                                    }
                                    if (i3 == 7) {
                                        i2 = cCharAt3 == c3 ? -1 : 1;
                                    }
                                    i9 = -1;
                                }
                            } else if (i3 == 3 || i3 == 7) {
                                i3 = -1;
                            }
                            i6 = -1;
                        }
                    }
                }
                i7 = i4;
                i6 = -1;
                i3 = -1;
            } else {
                i6 = i9;
                i7 = i4;
            }
            if (i3 == i6 || i7 != length) {
                return Double.NaN;
            }
            int i12 = iArr[0];
            int i13 = iArr[1];
            int i14 = iArr[2];
            int i15 = iArr[3];
            int i16 = iArr[4];
            int i17 = iArr[5];
            int i18 = iArr[6];
            int i19 = iArr[7];
            int i20 = iArr[8];
            if (i12 > 275943 || i13 < 1 || i13 > 12 || i14 < 1 || i14 > DaysInMonth(i12, i13) || i15 > 24) {
                return Double.NaN;
            }
            if ((i15 == 24 && (i16 > 0 || i17 > 0 || i18 > 0)) || i16 > 59 || i17 > 59 || i19 > 23 || i20 > 59) {
                return Double.NaN;
            }
            double dDate_msecFromDate = date_msecFromDate(i12 * i, i13 - 1, i14, i15, i16, i17, i18);
            if (i19 != -1) {
                dDate_msecFromDate -= (((double) ((i19 * 60) + i20)) * msPerMinute) * ((double) i2);
            }
            if (dDate_msecFromDate < -8.64E15d || dDate_msecFromDate > HalfTimeDomain) {
                return Double.NaN;
            }
            return dDate_msecFromDate;
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x00fa  */
    /* JADX WARN: Code duplicated, block: B:102:0x0102  */
    /* JADX WARN: Code duplicated, block: B:105:0x0107  */
    /* JADX WARN: Code duplicated, block: B:132:0x0145  */
    /* JADX WARN: Code duplicated, block: B:136:0x0152  */
    /* JADX WARN: Code duplicated, block: B:140:0x016d  */
    /* JADX WARN: Code duplicated, block: B:149:0x017e  */
    /* JADX WARN: Code duplicated, block: B:152:0x0184  */
    /* JADX WARN: Code duplicated, block: B:154:0x0188 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:155:0x018a  */
    /* JADX WARN: Code duplicated, block: B:157:0x018d  */
    /* JADX WARN: Code duplicated, block: B:159:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:160:0x01a5  */
    /* JADX WARN: Code duplicated, block: B:161:0x01a8  */
    /* JADX WARN: Code duplicated, block: B:162:0x01ab  */
    /* JADX WARN: Code duplicated, block: B:163:0x01ae  */
    /* JADX WARN: Code duplicated, block: B:165:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:166:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:167:0x01bb A[PHI: r12 r16
      0x01bb: PHI (r12v4 int) = (r12v1 int), (r12v1 int), (r12v1 int), (r12v1 int), (r12v1 int), (r12v3 int), (r12v1 int), (r12v1 int) binds: [B:150:0x0181, B:162:0x01ab, B:161:0x01a8, B:164:0x01b0, B:159:0x01a1, B:155:0x018a, B:146:0x0178, B:144:0x0174] A[DONT_GENERATE, DONT_INLINE]
      0x01bb: PHI (r16v9 double) = 
      (r16v1 double)
      (r16v6 double)
      (r16v7 double)
      (r16v8 double)
      (r16v1 double)
      (r16v1 double)
      (r16v1 double)
      (r16v1 double)
     binds: [B:150:0x0181, B:162:0x01ab, B:161:0x01a8, B:164:0x01b0, B:159:0x01a1, B:155:0x018a, B:146:0x0178, B:144:0x0174] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:169:0x01c2 A[LOOP:2: B:133:0x0147->B:169:0x01c2, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:213:0x0144 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:214:0x0151 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:217:0x018c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:242:0x0168 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:97:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:99:0x00f7  */
    private static double date_parseString(String str) {
        int i;
        int i2;
        int i3;
        int i4;
        int iIndexOf;
        int i5;
        char c;
        int i6;
        int i7;
        int i8;
        int i9;
        double d;
        int i10;
        double iSOString = parseISOString(str);
        if (!Double.isNaN(iSOString)) {
            return iSOString;
        }
        int length = str.length();
        int i11 = -1;
        int i12 = -1;
        int i13 = -1;
        int i14 = -1;
        int i15 = -1;
        int i16 = -1;
        char c2 = 0;
        int i17 = 0;
        double d2 = -1.0d;
        boolean z = false;
        while (i17 < length) {
            char cCharAt = str.charAt(i17);
            int i18 = i17 + 1;
            if (cCharAt <= ' ' || cCharAt == ',' || cCharAt == '-') {
                char c3 = c2;
                i = i11;
                if (i18 < length) {
                    char cCharAt2 = str.charAt(i18);
                    if (cCharAt == '-' && '0' <= cCharAt2 && cCharAt2 <= '9') {
                        c2 = cCharAt;
                        i17 = i18;
                        i11 = i;
                    }
                }
                i17 = i18;
                i11 = i;
                c2 = c3;
            } else {
                int i19 = 1;
                if (cCharAt == '(') {
                    i17 = i18;
                    while (i17 < length) {
                        char cCharAt3 = str.charAt(i17);
                        i17++;
                        if (cCharAt3 != '(') {
                            if (cCharAt3 == ')' && (i19 = i19 - 1) <= 0) {
                                break;
                            }
                        } else {
                            i19++;
                        }
                    }
                } else if ('0' <= cCharAt && cCharAt <= '9') {
                    i17 = i18;
                    int i20 = cCharAt - '0';
                    while (i17 < length) {
                        cCharAt = str.charAt(i17);
                        if ('0' > cCharAt || cCharAt > '9') {
                            if (c2 != '+' || c2 == '-') {
                                if (i20 < 24) {
                                    i10 = i20 * 60;
                                } else {
                                    i10 = ((i20 / 100) * 60) + (i20 % 100);
                                }
                                if (c2 == '+') {
                                    i10 = -i10;
                                }
                                if (d2 == 0.0d && d2 != -1.0d) {
                                    return Double.NaN;
                                }
                                d2 = i10;
                                z = true;
                            } else if (i20 >= 70 || (c2 == '/' && i13 >= 0 && i14 >= 0 && i12 < 0)) {
                                if (i12 >= 0) {
                                    return Double.NaN;
                                }
                                if (cCharAt > ' ' && cCharAt != ',' && cCharAt != '/' && i17 < length) {
                                    return Double.NaN;
                                }
                                if (i20 < 100) {
                                    i20 += 1900;
                                }
                                i12 = i20;
                            } else if (cCharAt == ':') {
                                if (i11 < 0) {
                                    i11 = i20;
                                } else {
                                    if (i16 >= 0) {
                                        return Double.NaN;
                                    }
                                    i16 = i20;
                                }
                            } else if (cCharAt == '/') {
                                if (i13 < 0) {
                                    i13 = i20 - 1;
                                } else {
                                    if (i14 >= 0) {
                                        return Double.NaN;
                                    }
                                    i14 = i20;
                                }
                            } else {
                                if (i17 < length && cCharAt != ',' && cCharAt > ' ' && cCharAt != '-') {
                                    return Double.NaN;
                                }
                                if (z && i20 < 60) {
                                    d2 = d2 < 0.0d ? d2 - ((double) i20) : d2 + ((double) i20);
                                } else if (i11 >= 0 && i16 < 0) {
                                    i16 = i20;
                                } else if (i16 < 0 || i15 >= 0) {
                                    if (i14 >= 0) {
                                        return Double.NaN;
                                    }
                                    i14 = i20;
                                } else {
                                    i15 = i20;
                                }
                            }
                            c2 = 0;
                        } else {
                            i20 = ((i20 * 10) + cCharAt) - 48;
                            i17++;
                        }
                    }
                    if (c2 != '+') {
                        if (i20 < 24) {
                            i10 = i20 * 60;
                        } else {
                            i10 = ((i20 / 100) * 60) + (i20 % 100);
                        }
                        if (c2 == '+') {
                            i10 = -i10;
                        }
                        if (d2 == 0.0d) {
                        }
                        d2 = i10;
                        z = true;
                    } else {
                        if (i20 < 24) {
                            i10 = i20 * 60;
                        } else {
                            i10 = ((i20 / 100) * 60) + (i20 % 100);
                        }
                        if (c2 == '+') {
                            i10 = -i10;
                        }
                        if (d2 == 0.0d) {
                        }
                        d2 = i10;
                        z = true;
                    }
                    c2 = 0;
                } else if (cCharAt == '/' || cCharAt == ':' || cCharAt == '+' || cCharAt == '-') {
                    i = i11;
                    c2 = cCharAt;
                    i17 = i18;
                    i11 = i;
                } else {
                    int i21 = i18;
                    while (i21 < length) {
                        char cCharAt4 = str.charAt(i21);
                        if (('A' > cCharAt4 || cCharAt4 > 'Z') && ('a' > cCharAt4 || cCharAt4 > 'z')) {
                            i2 = i21 - i17;
                            if (i2 < 2) {
                                return Double.NaN;
                            }
                            i3 = 0;
                            i4 = 0;
                            while (true) {
                                iIndexOf = "am;pm;monday;tuesday;wednesday;thursday;friday;saturday;sunday;january;february;march;april;may;june;july;august;september;october;november;december;gmt;ut;utc;est;edt;cst;cdt;mst;mdt;pst;pdt;".indexOf(59, i4);
                                if (iIndexOf < 0) {
                                    return Double.NaN;
                                }
                                i5 = i3;
                                c = c2;
                                i6 = i17;
                                i7 = i21;
                                i8 = i11;
                                if ("am;pm;monday;tuesday;wednesday;thursday;friday;saturday;sunday;january;february;march;april;may;june;july;august;september;october;november;december;gmt;ut;utc;est;edt;cst;cdt;mst;mdt;pst;pdt;".regionMatches(true, i4, str, i17, i2)) {
                                    if (i5 < 2) {
                                        if (i8 <= 12 || i8 < 0) {
                                            return Double.NaN;
                                        }
                                        if (i5 == 0) {
                                            if (i8 == 12) {
                                                i11 = 0;
                                            } else {
                                                i11 = i8;
                                            }
                                        } else if (i8 != 12) {
                                            i11 = i8 + 12;
                                        } else {
                                            i11 = i8;
                                        }
                                    } else if (i5 - 2 >= 7) {
                                        i9 = i5 - 9;
                                        if (i9 < 12) {
                                            switch (i5 - 21) {
                                                case 0:
                                                case 1:
                                                case 2:
                                                    i11 = i8;
                                                    d2 = 0.0d;
                                                    break;
                                                case 3:
                                                case 6:
                                                    i11 = i8;
                                                    d2 = 300.0d;
                                                    break;
                                                case 4:
                                                    d = 240.0d;
                                                    d2 = d;
                                                    break;
                                                case 5:
                                                case 8:
                                                    d2 = 360.0d;
                                                    break;
                                                case 7:
                                                case 10:
                                                    d2 = 420.0d;
                                                    break;
                                                case 9:
                                                    d = 480.0d;
                                                    d2 = d;
                                                    break;
                                                default:
                                                    Kit.codeBug();
                                                    break;
                                            }
                                        } else {
                                            if (i13 < 0) {
                                                return Double.NaN;
                                            }
                                            i13 = i9;
                                        }
                                        i11 = i8;
                                    } else {
                                        i11 = i8;
                                    }
                                    c2 = c;
                                    i17 = i7;
                                    break;
                                }
                                i3 = i5 + 1;
                                i11 = i8;
                                c2 = c;
                                i17 = i6;
                                i21 = i7;
                                i4 = iIndexOf + 1;
                            }
                        } else {
                            i21++;
                        }
                    }
                    i2 = i21 - i17;
                    if (i2 < 2) {
                        return Double.NaN;
                    }
                    i3 = 0;
                    i4 = 0;
                    while (true) {
                        iIndexOf = "am;pm;monday;tuesday;wednesday;thursday;friday;saturday;sunday;january;february;march;april;may;june;july;august;september;october;november;december;gmt;ut;utc;est;edt;cst;cdt;mst;mdt;pst;pdt;".indexOf(59, i4);
                        if (iIndexOf < 0) {
                            return Double.NaN;
                        }
                        i5 = i3;
                        c = c2;
                        i6 = i17;
                        i7 = i21;
                        i8 = i11;
                        if ("am;pm;monday;tuesday;wednesday;thursday;friday;saturday;sunday;january;february;march;april;may;june;july;august;september;october;november;december;gmt;ut;utc;est;edt;cst;cdt;mst;mdt;pst;pdt;".regionMatches(true, i4, str, i17, i2)) {
                            if (i5 < 2) {
                                if (i8 <= 12) {
                                }
                                return Double.NaN;
                            }
                            if (i5 - 2 >= 7) {
                                i9 = i5 - 9;
                                if (i9 < 12) {
                                    switch (i5 - 21) {
                                        case 0:
                                        case 1:
                                        case 2:
                                            i11 = i8;
                                            d2 = 0.0d;
                                            break;
                                        case 3:
                                        case 6:
                                            i11 = i8;
                                            d2 = 300.0d;
                                            break;
                                        case 4:
                                            d = 240.0d;
                                            d2 = d;
                                            break;
                                        case 5:
                                        case 8:
                                            d2 = 360.0d;
                                            break;
                                        case 7:
                                        case 10:
                                            d2 = 420.0d;
                                            break;
                                        case 9:
                                            d = 480.0d;
                                            d2 = d;
                                            break;
                                        default:
                                            Kit.codeBug();
                                            break;
                                    }
                                } else {
                                    if (i13 < 0) {
                                        return Double.NaN;
                                    }
                                    i13 = i9;
                                }
                                i11 = i8;
                            } else {
                                i11 = i8;
                            }
                            c2 = c;
                            i17 = i7;
                            break;
                            break;
                        }
                        i3 = i5 + 1;
                        i11 = i8;
                        c2 = c;
                        i17 = i6;
                        i21 = i7;
                        i4 = iIndexOf + 1;
                    }
                }
            }
        }
        int i22 = i11;
        if (i12 < 0 || i13 < 0 || i14 < 0) {
            return Double.NaN;
        }
        if (i15 < 0) {
            i15 = 0;
        }
        if (i16 < 0) {
            i16 = 0;
        }
        double dDate_msecFromDate = date_msecFromDate(i12, i13, i14, i22 < 0 ? 0 : i22, i16, i15, 0.0d);
        return d2 == -1.0d ? internalUTC(dDate_msecFromDate) : dDate_msecFromDate + (d2 * msPerMinute);
    }

    private static String date_format(double d, int i) {
        StringBuilder sb = new StringBuilder(60);
        double dLocalTime = LocalTime(d);
        if (i != 3) {
            appendWeekDayName(sb, WeekDay(dLocalTime));
            sb.append(StringUtil.SPACE);
            appendMonthName(sb, MonthFromTime(dLocalTime));
            sb.append(StringUtil.SPACE);
            append0PaddedUint(sb, DateFromTime(dLocalTime), 2);
            sb.append(StringUtil.SPACE);
            int iYearFromTime = YearFromTime(dLocalTime);
            if (iYearFromTime < 0) {
                sb.append('-');
                iYearFromTime = -iYearFromTime;
            }
            append0PaddedUint(sb, iYearFromTime, 4);
            if (i != 4) {
                sb.append(StringUtil.SPACE);
            }
        }
        if (i != 4) {
            append0PaddedUint(sb, HourFromTime(dLocalTime), 2);
            sb.append(':');
            append0PaddedUint(sb, MinFromTime(dLocalTime), 2);
            sb.append(':');
            append0PaddedUint(sb, SecFromTime(dLocalTime), 2);
            int iFloor = (int) Math.floor((LocalTZA + DaylightSavingTA(d)) / msPerMinute);
            int i2 = ((iFloor / 60) * 100) + (iFloor % 60);
            if (i2 > 0) {
                sb.append(" GMT+");
            } else {
                sb.append(" GMT-");
                i2 = -i2;
            }
            append0PaddedUint(sb, i2, 4);
            if (d < 0.0d) {
                d = MakeDate(MakeDay(EquivalentYear(YearFromTime(dLocalTime)), MonthFromTime(d), DateFromTime(d)), TimeWithinDay(d));
            }
            sb.append(" (");
            Date date = new Date((long) d);
            DateFormat dateFormat = timeZoneFormatter;
            synchronized (dateFormat) {
                sb.append(dateFormat.format(date));
            }
            sb.append(')');
        }
        return sb.toString();
    }

    private static Object jsConstructor(Object[] objArr) {
        double number;
        NativeDate nativeDate = new NativeDate();
        if (objArr.length == 0) {
            nativeDate.date = now();
            return nativeDate;
        }
        if (objArr.length == 1) {
            Object defaultValue = objArr[0];
            if (defaultValue instanceof NativeDate) {
                nativeDate.date = ((NativeDate) defaultValue).date;
                return nativeDate;
            }
            if (defaultValue instanceof Scriptable) {
                defaultValue = ((Scriptable) defaultValue).getDefaultValue(null);
            }
            if (defaultValue instanceof CharSequence) {
                number = date_parseString(defaultValue.toString());
            } else {
                number = ScriptRuntime.toNumber(defaultValue);
            }
            nativeDate.date = TimeClip(number);
            return nativeDate;
        }
        double dDate_msecFromArgs = date_msecFromArgs(objArr);
        if (!Double.isNaN(dDate_msecFromArgs) && !Double.isInfinite(dDate_msecFromArgs)) {
            dDate_msecFromArgs = TimeClip(internalUTC(dDate_msecFromArgs));
        }
        nativeDate.date = dDate_msecFromArgs;
        return nativeDate;
    }

    private static String toLocale_helper(double d, int i) {
        DateFormat dateFormat;
        String str;
        if (i == 5) {
            dateFormat = localeDateTimeFormatter;
        } else if (i == 6) {
            dateFormat = localeTimeFormatter;
        } else if (i == 7) {
            dateFormat = localeDateFormatter;
        } else {
            throw new AssertionError();
        }
        synchronized (dateFormat) {
            str = dateFormat.format(new Date((long) d));
        }
        return str;
    }

    private static String js_toUTCString(double d) {
        StringBuilder sb = new StringBuilder(60);
        appendWeekDayName(sb, WeekDay(d));
        sb.append(", ");
        append0PaddedUint(sb, DateFromTime(d), 2);
        sb.append(StringUtil.SPACE);
        appendMonthName(sb, MonthFromTime(d));
        sb.append(StringUtil.SPACE);
        int iYearFromTime = YearFromTime(d);
        if (iYearFromTime < 0) {
            sb.append('-');
            iYearFromTime = -iYearFromTime;
        }
        append0PaddedUint(sb, iYearFromTime, 4);
        sb.append(StringUtil.SPACE);
        append0PaddedUint(sb, HourFromTime(d), 2);
        sb.append(':');
        append0PaddedUint(sb, MinFromTime(d), 2);
        sb.append(':');
        append0PaddedUint(sb, SecFromTime(d), 2);
        sb.append(" GMT");
        return sb.toString();
    }

    private static String js_toISOString(double d) {
        StringBuilder sb = new StringBuilder(27);
        int iYearFromTime = YearFromTime(d);
        if (iYearFromTime < 0) {
            sb.append('-');
            append0PaddedUint(sb, -iYearFromTime, 6);
        } else if (iYearFromTime > 9999) {
            append0PaddedUint(sb, iYearFromTime, 6);
        } else {
            append0PaddedUint(sb, iYearFromTime, 4);
        }
        sb.append('-');
        append0PaddedUint(sb, MonthFromTime(d) + 1, 2);
        sb.append('-');
        append0PaddedUint(sb, DateFromTime(d), 2);
        sb.append(Util.C_TYPE_VARIABLE);
        append0PaddedUint(sb, HourFromTime(d), 2);
        sb.append(':');
        append0PaddedUint(sb, MinFromTime(d), 2);
        sb.append(':');
        append0PaddedUint(sb, SecFromTime(d), 2);
        sb.append('.');
        append0PaddedUint(sb, msFromTime(d), 3);
        sb.append('Z');
        return sb.toString();
    }

    private static void append0PaddedUint(StringBuilder sb, int i, int i2) {
        int i3;
        if (i < 0) {
            Kit.codeBug();
        }
        int i4 = i2 - 1;
        if (i >= 10) {
            i3 = Http2Connection.DEGRADED_PONG_TIMEOUT_NS;
            if (i < 1000000000) {
                i3 = 1;
                while (true) {
                    int i5 = i3 * 10;
                    if (i < i5) {
                        break;
                    }
                    i4--;
                    i3 = i5;
                }
            } else {
                i4 = i2 - 10;
            }
        } else {
            i3 = 1;
        }
        while (i4 > 0) {
            sb.append(ExternalAnnotationProvider.NULLABLE);
            i4--;
        }
        while (i3 != 1) {
            sb.append((char) ((i / i3) + 48));
            i %= i3;
            i3 /= 10;
        }
        sb.append((char) (i + 48));
    }

    private static void appendMonthName(StringBuilder sb, int i) {
        int i2 = i * 3;
        for (int i3 = 0; i3 != 3; i3++) {
            sb.append("JanFebMarAprMayJunJulAugSepOctNovDec".charAt(i2 + i3));
        }
    }

    private static void appendWeekDayName(StringBuilder sb, int i) {
        int i2 = i * 3;
        for (int i3 = 0; i3 != 3; i3++) {
            sb.append("SunMonTueWedThuFriSat".charAt(i2 + i3));
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x002b  */
    /* JADX WARN: Code duplicated, block: B:24:0x002d  */
    /* JADX WARN: Code duplicated, block: B:27:0x0034  */
    /* JADX WARN: Code duplicated, block: B:29:0x0040  */
    /* JADX WARN: Code duplicated, block: B:33:0x004e  */
    /* JADX WARN: Code duplicated, block: B:36:0x0054  */
    private static double makeTime(double d, Object[] objArr, int i) {
        boolean z;
        int i2;
        int length;
        int i3;
        boolean z2;
        double dHourFromTime;
        double dMinFromTime;
        double dSecFromTime;
        double dMsFromTime;
        double number;
        if (objArr.length == 0) {
            return Double.NaN;
        }
        int i4 = 0;
        switch (i) {
            case 31:
                z = true;
                i2 = 1;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr = new double[4];
                z2 = false;
                for (i3 = 0; i3 < length; i3++) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (!Double.isNaN(number) || Double.isInfinite(number)) {
                        z2 = true;
                    } else {
                        dArr[i3] = ScriptRuntime.toInteger(number);
                    }
                }
                if (!z2 || Double.isNaN(d)) {
                    return Double.NaN;
                }
                double dLocalTime = z ? LocalTime(d) : d;
                if (i2 >= 4 && length > 0) {
                    dHourFromTime = dArr[0];
                    i4 = 1;
                } else {
                    dHourFromTime = HourFromTime(dLocalTime);
                }
                if (i2 >= 3 && i4 < length) {
                    dMinFromTime = dArr[i4];
                    i4++;
                } else {
                    dMinFromTime = MinFromTime(dLocalTime);
                }
                if (i2 >= 2 && i4 < length) {
                    double d2 = dArr[i4];
                    i4++;
                    dSecFromTime = d2;
                } else {
                    dSecFromTime = SecFromTime(dLocalTime);
                }
                if (i2 >= 1 && i4 < length) {
                    dMsFromTime = dArr[i4];
                } else {
                    dMsFromTime = msFromTime(dLocalTime);
                }
                double dMakeDate = MakeDate(Day(dLocalTime), MakeTime(dHourFromTime, dMinFromTime, dSecFromTime, dMsFromTime));
                if (z) {
                    dMakeDate = internalUTC(dMakeDate);
                }
                return TimeClip(dMakeDate);
            case 32:
                z = false;
                i2 = 1;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr2 = new double[4];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    break;
                }
                return Double.NaN;
            case 33:
                z = true;
                i2 = 2;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr3 = new double[4];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    break;
                }
                return Double.NaN;
            case 34:
                z = false;
                i2 = 2;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr4 = new double[4];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    break;
                }
                return Double.NaN;
            case 35:
                z = true;
                i2 = 3;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr5 = new double[4];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    break;
                }
                return Double.NaN;
            case 36:
                z = false;
                i2 = 3;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr6 = new double[4];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    break;
                }
                return Double.NaN;
            case 37:
                z = true;
                i2 = 4;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr7 = new double[4];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    break;
                }
                return Double.NaN;
            case 38:
                z = false;
                i2 = 4;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr8 = new double[4];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    break;
                }
                return Double.NaN;
            default:
                throw Kit.codeBug();
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0025  */
    /* JADX WARN: Code duplicated, block: B:21:0x0027  */
    /* JADX WARN: Code duplicated, block: B:24:0x002e  */
    /* JADX WARN: Code duplicated, block: B:26:0x003a  */
    /* JADX WARN: Code duplicated, block: B:30:0x0048  */
    /* JADX WARN: Code duplicated, block: B:33:0x004e A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:34:0x004f  */
    /* JADX WARN: Code duplicated, block: B:36:0x0055 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:37:0x0057 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:38:0x0058  */
    /* JADX WARN: Code duplicated, block: B:39:0x005b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:40:0x005d  */
    /* JADX WARN: Code duplicated, block: B:41:0x0062  */
    /* JADX WARN: Code duplicated, block: B:43:0x0066 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:45:0x006d  */
    /* JADX WARN: Code duplicated, block: B:47:0x0075 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:49:0x007e  */
    /* JADX WARN: Code duplicated, block: B:51:0x0086 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:53:0x008b  */
    /* JADX WARN: Code duplicated, block: B:56:0x00a0  */
    private static double makeDate(double d, Object[] objArr, int i) {
        boolean z;
        int i2;
        int length;
        int i3;
        boolean z2;
        double dLocalTime;
        double dYearFromTime;
        double dMonthFromTime;
        double dDateFromTime;
        double dMakeDate;
        double number;
        if (objArr.length == 0) {
            return Double.NaN;
        }
        int i4 = 0;
        switch (i) {
            case 39:
                z = true;
                i2 = 1;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr = new double[3];
                z2 = false;
                for (i3 = 0; i3 < length; i3++) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (!Double.isNaN(number) || Double.isInfinite(number)) {
                        z2 = true;
                    } else {
                        dArr[i3] = ScriptRuntime.toInteger(number);
                    }
                }
                if (z2) {
                    return Double.NaN;
                }
                if (Double.isNaN(d)) {
                    if (i2 < 3) {
                        return Double.NaN;
                    }
                    dLocalTime = 0.0d;
                } else if (z) {
                    dLocalTime = LocalTime(d);
                } else {
                    dLocalTime = d;
                }
                if (i2 < 3 && length > 0) {
                    dYearFromTime = dArr[0];
                    i4 = 1;
                } else {
                    dYearFromTime = YearFromTime(dLocalTime);
                }
                if (i2 < 2 && i4 < length) {
                    dMonthFromTime = dArr[i4];
                    i4++;
                } else {
                    dMonthFromTime = MonthFromTime(dLocalTime);
                }
                if (i2 < 1 && i4 < length) {
                    dDateFromTime = dArr[i4];
                } else {
                    dDateFromTime = DateFromTime(dLocalTime);
                }
                dMakeDate = MakeDate(MakeDay(dYearFromTime, dMonthFromTime, dDateFromTime), TimeWithinDay(dLocalTime));
                if (z) {
                    dMakeDate = internalUTC(dMakeDate);
                }
                return TimeClip(dMakeDate);
            case 40:
                z = false;
                i2 = 1;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr2 = new double[3];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    return Double.NaN;
                }
                if (Double.isNaN(d)) {
                    if (i2 < 3) {
                        return Double.NaN;
                    }
                    dLocalTime = 0.0d;
                } else if (z) {
                    dLocalTime = LocalTime(d);
                } else {
                    dLocalTime = d;
                }
                if (i2 < 3) {
                    dYearFromTime = YearFromTime(dLocalTime);
                } else {
                    dYearFromTime = YearFromTime(dLocalTime);
                }
                if (i2 < 2) {
                    dMonthFromTime = MonthFromTime(dLocalTime);
                } else {
                    dMonthFromTime = MonthFromTime(dLocalTime);
                }
                if (i2 < 1) {
                    dDateFromTime = DateFromTime(dLocalTime);
                } else {
                    dDateFromTime = DateFromTime(dLocalTime);
                }
                dMakeDate = MakeDate(MakeDay(dYearFromTime, dMonthFromTime, dDateFromTime), TimeWithinDay(dLocalTime));
                if (z) {
                    dMakeDate = internalUTC(dMakeDate);
                }
                return TimeClip(dMakeDate);
            case 41:
                z = true;
                i2 = 2;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr3 = new double[3];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    return Double.NaN;
                }
                if (Double.isNaN(d)) {
                    if (i2 < 3) {
                        return Double.NaN;
                    }
                    dLocalTime = 0.0d;
                } else if (z) {
                    dLocalTime = LocalTime(d);
                } else {
                    dLocalTime = d;
                }
                if (i2 < 3) {
                    dYearFromTime = YearFromTime(dLocalTime);
                } else {
                    dYearFromTime = YearFromTime(dLocalTime);
                }
                if (i2 < 2) {
                    dMonthFromTime = MonthFromTime(dLocalTime);
                } else {
                    dMonthFromTime = MonthFromTime(dLocalTime);
                }
                if (i2 < 1) {
                    dDateFromTime = DateFromTime(dLocalTime);
                } else {
                    dDateFromTime = DateFromTime(dLocalTime);
                }
                dMakeDate = MakeDate(MakeDay(dYearFromTime, dMonthFromTime, dDateFromTime), TimeWithinDay(dLocalTime));
                if (z) {
                    dMakeDate = internalUTC(dMakeDate);
                }
                return TimeClip(dMakeDate);
            case 42:
                z = false;
                i2 = 2;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr4 = new double[3];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    return Double.NaN;
                }
                if (Double.isNaN(d)) {
                    if (i2 < 3) {
                        return Double.NaN;
                    }
                    dLocalTime = 0.0d;
                } else if (z) {
                    dLocalTime = LocalTime(d);
                } else {
                    dLocalTime = d;
                }
                if (i2 < 3) {
                    dYearFromTime = YearFromTime(dLocalTime);
                } else {
                    dYearFromTime = YearFromTime(dLocalTime);
                }
                if (i2 < 2) {
                    dMonthFromTime = MonthFromTime(dLocalTime);
                } else {
                    dMonthFromTime = MonthFromTime(dLocalTime);
                }
                if (i2 < 1) {
                    dDateFromTime = DateFromTime(dLocalTime);
                } else {
                    dDateFromTime = DateFromTime(dLocalTime);
                }
                dMakeDate = MakeDate(MakeDay(dYearFromTime, dMonthFromTime, dDateFromTime), TimeWithinDay(dLocalTime));
                if (z) {
                    dMakeDate = internalUTC(dMakeDate);
                }
                return TimeClip(dMakeDate);
            case 43:
                z = true;
                i2 = 3;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr5 = new double[3];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    return Double.NaN;
                }
                if (Double.isNaN(d)) {
                    if (i2 < 3) {
                        return Double.NaN;
                    }
                    dLocalTime = 0.0d;
                } else if (z) {
                    dLocalTime = LocalTime(d);
                } else {
                    dLocalTime = d;
                }
                if (i2 < 3) {
                    dYearFromTime = YearFromTime(dLocalTime);
                } else {
                    dYearFromTime = YearFromTime(dLocalTime);
                }
                if (i2 < 2) {
                    dMonthFromTime = MonthFromTime(dLocalTime);
                } else {
                    dMonthFromTime = MonthFromTime(dLocalTime);
                }
                if (i2 < 1) {
                    dDateFromTime = DateFromTime(dLocalTime);
                } else {
                    dDateFromTime = DateFromTime(dLocalTime);
                }
                dMakeDate = MakeDate(MakeDay(dYearFromTime, dMonthFromTime, dDateFromTime), TimeWithinDay(dLocalTime));
                if (z) {
                    dMakeDate = internalUTC(dMakeDate);
                }
                return TimeClip(dMakeDate);
            case 44:
                z = false;
                i2 = 3;
                if (objArr.length < i2) {
                    length = objArr.length;
                } else {
                    length = i2;
                }
                double[] dArr6 = new double[3];
                z2 = false;
                while (i3 < length) {
                    number = ScriptRuntime.toNumber(objArr[i3]);
                    if (Double.isNaN(number)) {
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                }
                if (z2) {
                    return Double.NaN;
                }
                if (Double.isNaN(d)) {
                    if (i2 < 3) {
                        return Double.NaN;
                    }
                    dLocalTime = 0.0d;
                } else if (z) {
                    dLocalTime = LocalTime(d);
                } else {
                    dLocalTime = d;
                }
                if (i2 < 3) {
                    dYearFromTime = YearFromTime(dLocalTime);
                } else {
                    dYearFromTime = YearFromTime(dLocalTime);
                }
                if (i2 < 2) {
                    dMonthFromTime = MonthFromTime(dLocalTime);
                } else {
                    dMonthFromTime = MonthFromTime(dLocalTime);
                }
                if (i2 < 1) {
                    dDateFromTime = DateFromTime(dLocalTime);
                } else {
                    dDateFromTime = DateFromTime(dLocalTime);
                }
                dMakeDate = MakeDate(MakeDay(dYearFromTime, dMonthFromTime, dDateFromTime), TimeWithinDay(dLocalTime));
                if (z) {
                    dMakeDate = internalUTC(dMakeDate);
                }
                return TimeClip(dMakeDate);
            default:
                throw Kit.codeBug();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:145:0x0217  */
    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int i = 2;
        switch (str.length()) {
            case 6:
                char cCharAt = str.charAt(0);
                if (cCharAt == 'g') {
                    str2 = "getDay";
                    i = 19;
                } else if (cCharAt != 't') {
                    str2 = null;
                    i = 0;
                } else {
                    str2 = "toJSON";
                    i = 47;
                }
                break;
            case 7:
                char cCharAt2 = str.charAt(3);
                if (cCharAt2 == 'D') {
                    char cCharAt3 = str.charAt(0);
                    if (cCharAt3 == 'g') {
                        str2 = "getDate";
                        i = 17;
                    } else if (cCharAt3 != 's') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "setDate";
                        i = 39;
                    }
                } else if (cCharAt2 == 'T') {
                    char cCharAt4 = str.charAt(0);
                    if (cCharAt4 == 'g') {
                        str2 = "getTime";
                        i = 11;
                    } else if (cCharAt4 != 's') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "setTime";
                        i = 30;
                    }
                } else if (cCharAt2 == 'Y') {
                    char cCharAt5 = str.charAt(0);
                    if (cCharAt5 == 'g') {
                        str2 = "getYear";
                        i = 12;
                    } else if (cCharAt5 != 's') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "setYear";
                        i = 45;
                    }
                } else if (cCharAt2 == 'u') {
                    str2 = "valueOf";
                    i = 10;
                } else {
                    str2 = null;
                    i = 0;
                }
                break;
            case 8:
                char cCharAt6 = str.charAt(3);
                if (cCharAt6 == 'H') {
                    char cCharAt7 = str.charAt(0);
                    if (cCharAt7 == 'g') {
                        str2 = "getHours";
                        i = 21;
                    } else if (cCharAt7 != 's') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "setHours";
                        i = 37;
                    }
                } else if (cCharAt6 == 'M') {
                    char cCharAt8 = str.charAt(0);
                    if (cCharAt8 == 'g') {
                        str2 = "getMonth";
                        i = 15;
                    } else if (cCharAt8 != 's') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "setMonth";
                        i = 41;
                    }
                } else if (cCharAt6 == 'o') {
                    str2 = "toSource";
                    i = 9;
                } else if (cCharAt6 == 't') {
                    str2 = "toString";
                } else {
                    str2 = null;
                    i = 0;
                }
                break;
            case 9:
                str2 = "getUTCDay";
                i = 20;
                break;
            case 10:
                char cCharAt9 = str.charAt(3);
                if (cCharAt9 == 'M') {
                    char cCharAt10 = str.charAt(0);
                    if (cCharAt10 == 'g') {
                        str2 = "getMinutes";
                        i = 23;
                    } else if (cCharAt10 != 's') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "setMinutes";
                        i = 35;
                    }
                } else if (cCharAt9 == 'S') {
                    char cCharAt11 = str.charAt(0);
                    if (cCharAt11 == 'g') {
                        str2 = "getSeconds";
                        i = 25;
                    } else if (cCharAt11 != 's') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "setSeconds";
                        i = 33;
                    }
                } else if (cCharAt9 == 'U') {
                    char cCharAt12 = str.charAt(0);
                    if (cCharAt12 == 'g') {
                        str2 = "getUTCDate";
                        i = 18;
                    } else if (cCharAt12 != 's') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "setUTCDate";
                        i = 40;
                    }
                } else {
                    str2 = null;
                    i = 0;
                }
                break;
            case 11:
                char cCharAt13 = str.charAt(3);
                if (cCharAt13 == 'F') {
                    char cCharAt14 = str.charAt(0);
                    if (cCharAt14 == 'g') {
                        str2 = "getFullYear";
                        i = 13;
                    } else if (cCharAt14 != 's') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "setFullYear";
                        i = 43;
                    }
                } else {
                    if (cCharAt13 != 'M') {
                        if (cCharAt13 != 's') {
                            switch (cCharAt13) {
                                case 'S':
                                    str2 = "toISOString";
                                    i = 46;
                                    break;
                                case 'T':
                                    str2 = "toUTCString";
                                    break;
                                case 'U':
                                    char cCharAt15 = str.charAt(0);
                                    if (cCharAt15 == 'g') {
                                        char cCharAt16 = str.charAt(9);
                                        if (cCharAt16 == 'r') {
                                            str2 = "getUTCHours";
                                            i = 22;
                                        } else if (cCharAt16 == 't') {
                                            str2 = "getUTCMonth";
                                            i = 16;
                                        }
                                        break;
                                    } else if (cCharAt15 == 's') {
                                        char cCharAt17 = str.charAt(9);
                                        if (cCharAt17 == 'r') {
                                            str2 = "setUTCHours";
                                            i = 38;
                                        } else if (cCharAt17 == 't') {
                                            str2 = "setUTCMonth";
                                            i = 42;
                                        }
                                        break;
                                    }
                                default:
                                    str2 = null;
                                    i = 0;
                                    break;
                            }
                        } else {
                            str2 = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                            i = 1;
                            break;
                        }
                    } else {
                        str2 = "toGMTString";
                    }
                    i = 8;
                }
                break;
            case 12:
                char cCharAt18 = str.charAt(2);
                if (cCharAt18 == 'D') {
                    str2 = "toDateString";
                    i = 4;
                } else if (cCharAt18 != 'T') {
                    str2 = null;
                    i = 0;
                } else {
                    str2 = "toTimeString";
                    i = 3;
                }
                break;
            case 13:
                char cCharAt19 = str.charAt(0);
                if (cCharAt19 == 'g') {
                    char cCharAt20 = str.charAt(6);
                    if (cCharAt20 == 'M') {
                        str2 = "getUTCMinutes";
                        i = 24;
                    } else if (cCharAt20 != 'S') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "getUTCSeconds";
                        i = 26;
                    }
                } else if (cCharAt19 == 's') {
                    char cCharAt21 = str.charAt(6);
                    if (cCharAt21 == 'M') {
                        str2 = "setUTCMinutes";
                        i = 36;
                    } else if (cCharAt21 != 'S') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "setUTCSeconds";
                        i = 34;
                    }
                } else {
                    str2 = null;
                    i = 0;
                }
                break;
            case 14:
                char cCharAt22 = str.charAt(0);
                if (cCharAt22 == 'g') {
                    str2 = "getUTCFullYear";
                    i = 14;
                } else if (cCharAt22 == 's') {
                    str2 = "setUTCFullYear";
                    i = 44;
                } else if (cCharAt22 != 't') {
                    str2 = null;
                    i = 0;
                } else {
                    str2 = "toLocaleString";
                    i = 5;
                }
                break;
            case 15:
                char cCharAt23 = str.charAt(0);
                if (cCharAt23 == 'g') {
                    str2 = "getMilliseconds";
                    i = 27;
                } else if (cCharAt23 != 's') {
                    str2 = null;
                    i = 0;
                } else {
                    str2 = "setMilliseconds";
                    i = 31;
                }
                break;
            case 16:
            default:
                str2 = null;
                i = 0;
                break;
            case 17:
                str2 = "getTimezoneOffset";
                i = 29;
                break;
            case 18:
                char cCharAt24 = str.charAt(0);
                if (cCharAt24 == 'g') {
                    str2 = "getUTCMilliseconds";
                    i = 28;
                } else if (cCharAt24 == 's') {
                    str2 = "setUTCMilliseconds";
                    i = 32;
                } else if (cCharAt24 == 't') {
                    char cCharAt25 = str.charAt(8);
                    if (cCharAt25 == 'D') {
                        str2 = "toLocaleDateString";
                        i = 7;
                    } else if (cCharAt25 != 'T') {
                        str2 = null;
                        i = 0;
                    } else {
                        str2 = "toLocaleTimeString";
                        i = 6;
                    }
                } else {
                    str2 = null;
                    i = 0;
                }
                break;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }
}
