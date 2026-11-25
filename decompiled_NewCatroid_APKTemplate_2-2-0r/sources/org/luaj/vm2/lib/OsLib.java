package org.luaj.vm2.lib;

import com.beust.jcommander.Parameters;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.Survey;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.luaj.vm2.Buffer;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes6.dex */
public class OsLib extends TwoArgFunction {
    private static final int CLOCK = 0;
    private static final int DATE = 1;
    private static final int DIFFTIME = 2;
    private static final int EXECUTE = 3;
    private static final int EXIT = 4;
    private static final int GETENV = 5;
    private static final String[] MonthName;
    private static final String[] MonthNameAbbrev;
    private static final String[] NAMES = {"clock", SchemaSymbols.ATTVAL_DATE, "difftime", "execute", "exit", "getenv", ProductAction.ACTION_REMOVE, "rename", "setlocale", "time", "tmpname"};
    private static final int REMOVE = 6;
    private static final int RENAME = 7;
    private static final int SETLOCALE = 8;
    private static final int TIME = 9;
    private static final int TMPNAME = 10;
    public static String TMP_PREFIX = ".luaj";
    public static String TMP_SUFFIX = "tmp";
    private static final String[] WeekdayName;
    private static final String[] WeekdayNameAbbrev;
    static /* synthetic */ Class class$org$luaj$vm2$lib$OsLib;
    private static final long t0;
    private static long tmpnames;
    protected Globals globals;

    class OsLibFunc extends VarArgFunction {
        public OsLibFunc(int i, String str) {
            this.opcode = i;
            this.f1059name = str;
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            try {
                switch (this.opcode) {
                    case 0:
                        return valueOf(OsLib.this.clock());
                    case 1:
                        String strOptjstring = varargs.optjstring(1, "%c");
                        double dTime = varargs.isnumber(2) ? varargs.todouble(2) : OsLib.this.time(null);
                        if (!strOptjstring.equals("*t")) {
                            OsLib osLib = OsLib.this;
                            if (dTime == -1.0d) {
                                dTime = osLib.time(null);
                            }
                            return valueOf(osLib.date(strOptjstring, dTime));
                        }
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date((long) (dTime * 1000.0d)));
                        LuaTable luaTableTableOf = LuaValue.tableOf();
                        luaTableTableOf.set("year", LuaValue.valueOf(calendar.get(1)));
                        luaTableTableOf.set("month", LuaValue.valueOf(calendar.get(2) + 1));
                        luaTableTableOf.set("day", LuaValue.valueOf(calendar.get(5)));
                        luaTableTableOf.set("hour", LuaValue.valueOf(calendar.get(11)));
                        luaTableTableOf.set("min", LuaValue.valueOf(calendar.get(12)));
                        luaTableTableOf.set("sec", LuaValue.valueOf(calendar.get(13)));
                        luaTableTableOf.set("wday", LuaValue.valueOf(calendar.get(7)));
                        luaTableTableOf.set("yday", LuaValue.valueOf(calendar.get(6)));
                        luaTableTableOf.set("isdst", LuaValue.valueOf(OsLib.this.isDaylightSavingsTime(calendar)));
                        return luaTableTableOf;
                    case 2:
                        return valueOf(OsLib.this.difftime(varargs.checkdouble(1), varargs.checkdouble(2)));
                    case 3:
                        return OsLib.this.execute(varargs.optjstring(1, null));
                    case 4:
                        OsLib.this.exit(varargs.optint(1, 0));
                        return NONE;
                    case 5:
                        String str = OsLib.this.getenv(varargs.checkjstring(1));
                        return str != null ? valueOf(str) : NIL;
                    case 6:
                        OsLib.this.remove(varargs.checkjstring(1));
                        return LuaValue.TRUE;
                    case 7:
                        OsLib.this.rename(varargs.checkjstring(1), varargs.checkjstring(2));
                        return LuaValue.TRUE;
                    case 8:
                        String str2 = OsLib.this.setlocale(varargs.optjstring(1, null), varargs.optjstring(2, DevModeOverwritingStrategies.ALL));
                        return str2 != null ? valueOf(str2) : NIL;
                    case 9:
                        return valueOf(OsLib.this.time(varargs.opttable(1, null)));
                    case 10:
                        return valueOf(OsLib.this.tmpname());
                    default:
                        return NONE;
                }
            } catch (IOException e) {
                return varargsOf(NIL, valueOf(e.getMessage()));
            }
        }
    }

    static {
        long jCurrentTimeMillis = System.currentTimeMillis();
        t0 = jCurrentTimeMillis;
        tmpnames = jCurrentTimeMillis;
        WeekdayNameAbbrev = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        WeekdayName = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        MonthNameAbbrev = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        MonthName = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    }

    private Calendar beginningOfYear(Calendar calendar) {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(calendar.getTime());
        calendar2.set(2, 0);
        calendar2.set(5, 1);
        calendar2.set(11, 0);
        calendar2.set(12, 0);
        calendar2.set(13, 0);
        calendar2.set(14, 0);
        return calendar2;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDaylightSavingsTime(Calendar calendar) {
        return timeZoneOffset(calendar) != calendar.getTimeZone().getRawOffset() / 1000;
    }

    private int timeZoneOffset(Calendar calendar) {
        return calendar.getTimeZone().getOffset(1, calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(7), (((calendar.get(11) * Survey.MINIMUM_TIME_SPENT_IN_APP_IN_SECONDS) + (calendar.get(12) * 60)) + calendar.get(13)) * 1000) / 1000;
    }

    private int weekNumber(Calendar calendar, int i) {
        Calendar calendarBeginningOfYear = beginningOfYear(calendar);
        int i2 = i + 8;
        calendarBeginningOfYear.set(5, ((i2 - calendarBeginningOfYear.get(7)) % 7) + 1);
        if (calendarBeginningOfYear.after(calendar)) {
            calendarBeginningOfYear.set(1, calendarBeginningOfYear.get(1) - 1);
            calendarBeginningOfYear.set(5, ((i2 - calendarBeginningOfYear.get(7)) % 7) + 1);
        }
        return ((int) ((calendar.getTime().getTime() - calendarBeginningOfYear.getTime().getTime()) / 604800000)) + 1;
    }

    @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        this.globals = luaValue2.checkglobals();
        LuaTable luaTable = new LuaTable();
        int i = 0;
        while (true) {
            String[] strArr = NAMES;
            if (i >= strArr.length) {
                luaValue2.set("os", luaTable);
                luaValue2.get("package").get("loaded").set("os", luaTable);
                return luaTable;
            }
            luaTable.set(strArr[i], new OsLibFunc(i, strArr[i]));
            i++;
        }
    }

    protected double clock() {
        return (System.currentTimeMillis() - t0) / 1000.0d;
    }

    public String date(String str, double d) {
        int i;
        int time;
        String strSubstring;
        int i2;
        int iWeekNumber;
        String str2;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long) (d * 1000.0d)));
        if (str.startsWith("!")) {
            d -= (double) timeZoneOffset(calendar);
            calendar.setTime(new Date((long) (1000.0d * d)));
            str = str.substring(1);
        }
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        Buffer buffer = new Buffer(length);
        int i3 = 0;
        while (i3 < length) {
            int i4 = i3 + 1;
            byte b = bytes[i3];
            if (b == 10) {
                buffer.append("\n");
            } else if (b != 37) {
                buffer.append(b);
            } else if (i4 < length) {
                i3 += 2;
                byte b2 = bytes[i4];
                if (b2 != 37) {
                    if (b2 != 77) {
                        if (b2 != 83) {
                            if (b2 != 85) {
                                if (b2 == 106) {
                                    time = ((int) ((calendar.getTime().getTime() - beginningOfYear(calendar).getTime().getTime()) / DateUtils.MILLIS_PER_DAY)) + 1001;
                                } else if (b2 != 109) {
                                    if (b2 == 112) {
                                        strSubstring = calendar.get(11) < 12 ? "AM" : "PM";
                                    } else if (b2 == 65) {
                                        strSubstring = WeekdayName[calendar.get(7) - 1];
                                    } else if (b2 == 66) {
                                        strSubstring = MonthName[calendar.get(2)];
                                    } else if (b2 == 72) {
                                        i = calendar.get(11);
                                    } else if (b2 != 73) {
                                        switch (b2) {
                                            case 87:
                                                iWeekNumber = weekNumber(calendar, 1);
                                                break;
                                            case 88:
                                                str2 = "%H:%M:%S";
                                                strSubstring = date(str2, d);
                                                break;
                                            case 89:
                                                iWeekNumber = calendar.get(1);
                                                break;
                                            default:
                                                switch (b2) {
                                                    case 97:
                                                        strSubstring = WeekdayNameAbbrev[calendar.get(7) - 1];
                                                        break;
                                                    case 98:
                                                        strSubstring = MonthNameAbbrev[calendar.get(2)];
                                                        break;
                                                    case 99:
                                                        str2 = "%a %b %d %H:%M:%S %Y";
                                                        strSubstring = date(str2, d);
                                                        break;
                                                    case 100:
                                                        i2 = 5;
                                                        break;
                                                    default:
                                                        switch (b2) {
                                                            case 119:
                                                                iWeekNumber = (calendar.get(7) + 6) % 7;
                                                                break;
                                                            case 120:
                                                                str2 = "%m/%d/%y";
                                                                strSubstring = date(str2, d);
                                                                break;
                                                            case 121:
                                                                strSubstring = String.valueOf(calendar.get(1)).substring(2);
                                                                break;
                                                            case 122:
                                                                int iTimeZoneOffset = timeZoneOffset(calendar) / 60;
                                                                int iAbs = Math.abs(iTimeZoneOffset);
                                                                strSubstring = new StringBuffer().append(iTimeZoneOffset >= 0 ? Marker.ANY_NON_NULL_MARKER : Parameters.DEFAULT_OPTION_PREFIXES).append(String.valueOf((iAbs / 60) + 100).substring(1)).append(String.valueOf((iAbs % 60) + 100).substring(1)).toString();
                                                                break;
                                                            default:
                                                                LuaValue.argerror(1, new StringBuffer("invalid conversion specifier '%").append((int) b2).append(FormatHelper.QUOTE).toString());
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                    } else {
                                        i = calendar.get(11) % 12;
                                    }
                                    buffer.append(strSubstring);
                                } else {
                                    time = calendar.get(2) + 101;
                                }
                                strSubstring = String.valueOf(time).substring(1);
                                buffer.append(strSubstring);
                            } else {
                                iWeekNumber = weekNumber(calendar, 0);
                            }
                            strSubstring = String.valueOf(iWeekNumber);
                            buffer.append(strSubstring);
                        } else {
                            i2 = 13;
                        }
                        i = calendar.get(i2);
                    } else {
                        i = calendar.get(12);
                    }
                    time = i + 100;
                    strSubstring = String.valueOf(time).substring(1);
                    buffer.append(strSubstring);
                } else {
                    buffer.append((byte) 37);
                }
            }
            i3 = i4;
        }
        return buffer.tojstring();
    }

    protected double difftime(double d, double d2) {
        return d - d2;
    }

    protected Varargs execute(String str) {
        return varargsOf(NIL, valueOf("exit"), ONE);
    }

    protected void exit(int i) {
        System.exit(i);
    }

    protected String getenv(String str) {
        return System.getProperty(str);
    }

    protected void remove(String str) throws IOException {
        throw new IOException("not implemented");
    }

    protected void rename(String str, String str2) throws IOException {
        throw new IOException("not implemented");
    }

    protected String setlocale(String str, String str2) {
        return "C";
    }

    protected double time(LuaTable luaTable) {
        Date time;
        if (luaTable == null) {
            time = new Date();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1, luaTable.get("year").checkint());
            calendar.set(2, luaTable.get("month").checkint() - 1);
            calendar.set(5, luaTable.get("day").checkint());
            calendar.set(11, luaTable.get("hour").optint(12));
            calendar.set(12, luaTable.get("min").optint(0));
            calendar.set(13, luaTable.get("sec").optint(0));
            calendar.set(14, 0);
            time = calendar.getTime();
        }
        return time.getTime() / 1000.0d;
    }

    protected String tmpname() {
        String string;
        Class clsClass$ = class$org$luaj$vm2$lib$OsLib;
        if (clsClass$ == null) {
            clsClass$ = class$("org.luaj.vm2.lib.OsLib");
            class$org$luaj$vm2$lib$OsLib = clsClass$;
        }
        synchronized (clsClass$) {
            StringBuffer stringBufferAppend = new StringBuffer().append(TMP_PREFIX);
            long j = tmpnames;
            tmpnames = 1 + j;
            string = stringBufferAppend.append(j).append(TMP_SUFFIX).toString();
        }
        return string;
    }
}
