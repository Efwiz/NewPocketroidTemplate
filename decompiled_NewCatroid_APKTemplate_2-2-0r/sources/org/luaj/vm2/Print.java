package org.luaj.vm2;

import com.huawei.hms.framework.common.ContainerUtils;
import io.netty.util.internal.StringUtil;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes6.dex */
public class Print extends Lua {
    private static final String STRING_FOR_NULL = "null";
    public static PrintStream ps = System.out;
    public static final String[] OPNAMES = {"MOVE", "LOADK", "LOADKX", "LOADBOOL", "LOADNIL", "GETUPVAL", "GETTABUP", "GETTABLE", "SETTABUP", "SETUPVAL", "SETTABLE", "NEWTABLE", "SELF", "ADD", "SUB", "MUL", "DIV", "MOD", "POW", "UNM", "NOT", "LEN", "CONCAT", "JMP", "EQ", "LT", "LE", "TEST", "TESTSET", "CALL", "TAILCALL", "RETURN", "FORLOOP", "FORPREP", "TFORCALL", "TFORLOOP", "SETLIST", "CLOSURE", "VARARG", "EXTRAARG", null};

    private void _assert(boolean z) {
        if (!z) {
            throw new NullPointerException("_assert failed");
        }
    }

    private static void format(String str, int i) {
        int length = str.length();
        if (length > i) {
            ps.print(str.substring(0, i));
            return;
        }
        ps.print(str);
        int i2 = i - length;
        while (true) {
            i2--;
            if (i2 < 0) {
                return;
            } else {
                ps.print(StringUtil.SPACE);
            }
        }
    }

    private static int getline(Prototype prototype, int i) {
        if (i <= 0 || prototype.lineinfo == null || i >= prototype.lineinfo.length) {
            return -1;
        }
        return prototype.lineinfo[i];
    }

    private static String id(Prototype prototype) {
        return "Proto";
    }

    public static void print(Prototype prototype) {
        printFunction(prototype, true);
    }

    public static void printCode(Prototype prototype) {
        int length = prototype.code.length;
        for (int i = 0; i < length; i++) {
            printOpCode(prototype, i);
            ps.println();
        }
    }

    static void printConstant(PrintStream printStream, Prototype prototype, int i) {
        printValue(printStream, prototype.k[i]);
    }

    static void printConstants(Prototype prototype) {
        int length = prototype.k.length;
        ps.print(new StringBuffer("constants (").append(length).append(") for ").append(id(prototype)).append(":\n").toString());
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            ps.print(new StringBuffer("  ").append(i2).append("  ").toString());
            printValue(ps, prototype.k[i]);
            ps.print("\n");
            i = i2;
        }
    }

    public static void printFunction(Prototype prototype, boolean z) {
        int length = prototype.p.length;
        printHeader(prototype);
        printCode(prototype);
        if (z) {
            printConstants(prototype);
            printLocals(prototype);
            printUpValues(prototype);
        }
        for (int i = 0; i < length; i++) {
            printFunction(prototype.p[i], z);
        }
    }

    static void printHeader(Prototype prototype) {
        String strSubstring;
        String strValueOf = String.valueOf(prototype.source);
        if (strValueOf.startsWith(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT) || strValueOf.startsWith(ContainerUtils.KEY_VALUE_DELIMITER)) {
            strSubstring = strValueOf.substring(1);
        } else {
            strSubstring = "\u001bLua".equals(strValueOf) ? "(bstring)" : "(string)";
        }
        ps.print(new StringBuffer("\n%").append(prototype.linedefined == 0 ? "main" : "function").append(" <").append(strSubstring).append(":").append(prototype.linedefined).append(",").append(prototype.lastlinedefined).append("> (").append(prototype.code.length).append(" instructions, ").append(prototype.code.length * 4).append(" bytes at ").append(id(prototype)).append(")\n").toString());
        ps.print(new StringBuffer().append(prototype.numparams).append(" param, ").append(prototype.maxstacksize).append(" slot, ").append(prototype.upvalues.length).append(" upvalue, ").toString());
        ps.print(new StringBuffer().append(prototype.locvars.length).append(" local, ").append(prototype.k.length).append(" constant, ").append(prototype.p.length).append(" function\n").toString());
    }

    static void printLocals(Prototype prototype) {
        int length = prototype.locvars.length;
        ps.print(new StringBuffer("locals (").append(length).append(") for ").append(id(prototype)).append(":\n").toString());
        for (int i = 0; i < length; i++) {
            ps.println(new StringBuffer("  ").append(i).append("  ").append(prototype.locvars[i].varname).append(" ").append(prototype.locvars[i].startpc + 1).append(" ").append(prototype.locvars[i].endpc + 1).toString());
        }
    }

    /* JADX WARN: Code duplicated, block: B:60:0x0192  */
    /* JADX WARN: Code duplicated, block: B:62:0x0198  */
    /* JADX WARN: Code duplicated, block: B:68:0x01c6  */
    /* JADX WARN: Code duplicated, block: B:76:0x01f0  */
    /* JADX WARN: Code duplicated, block: B:77:0x01f8  */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x018f, code lost:
    
        if (ISK(r5) != false) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01ad, code lost:
    
        if (ISK(r5) != false) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01b7, code lost:
    
        r13.print(com.beust.jcommander.Parameters.DEFAULT_OPTION_PREFIXES);
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0202, code lost:
    
        if (ISK(r5) != false) goto L65;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void printOpCode(java.io.PrintStream r13, org.luaj.vm2.Prototype r14, int r15) {
        /*
            Method dump skipped, instruction units count: 576
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.luaj.vm2.Print.printOpCode(java.io.PrintStream, org.luaj.vm2.Prototype, int):void");
    }

    public static void printOpCode(Prototype prototype, int i) {
        printOpCode(ps, prototype, i);
    }

    public static void printStack(LuaValue[] luaValueArr, int i, Varargs varargs) {
        PrintStream printStream;
        String string;
        ps.print('[');
        int i2 = 0;
        while (i2 < luaValueArr.length) {
            LuaValue luaValue = luaValueArr[i2];
            if (luaValue == null) {
                ps.print("null");
            } else {
                int iType = luaValue.type();
                if (iType == 4) {
                    LuaString luaStringCheckstring = luaValue.checkstring();
                    printStream = ps;
                    string = luaStringCheckstring.length() < 48 ? luaStringCheckstring.tojstring() : new StringBuffer().append(luaStringCheckstring.substring(0, 32).tojstring()).append("...+").append(luaStringCheckstring.length() - 32).append(JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME).toString();
                } else if (iType == 6 || iType != 7) {
                    printStream = ps;
                    string = luaValue.tojstring();
                } else {
                    Object obj = luaValue.touserdata();
                    if (obj != null) {
                        String name2 = obj.getClass().getName();
                        ps.print(new StringBuffer().append(name2.substring(name2.lastIndexOf(46) + 1)).append(": ").append(Integer.toHexString(obj.hashCode())).toString());
                    } else {
                        printStream = ps;
                        string = luaValue.toString();
                    }
                }
                printStream.print(string);
            }
            i2++;
            if (i2 == i) {
                ps.print(']');
            }
            ps.print(" | ");
        }
        ps.print(varargs);
    }

    public static void printState(LuaClosure luaClosure, int i, LuaValue[] luaValueArr, int i2, Varargs varargs) {
        PrintStream printStream = ps;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ps = new PrintStream(byteArrayOutputStream);
        printOpCode(luaClosure.p, i);
        ps.flush();
        ps.close();
        ps = printStream;
        format(byteArrayOutputStream.toString(), 50);
        printStack(luaValueArr, i2, varargs);
        ps.println();
    }

    static void printString(PrintStream printStream, LuaString luaString) {
        String strSubstring;
        printStream.print('\"');
        int i = luaString.m_length;
        for (int i2 = 0; i2 < i; i2++) {
            byte b = luaString.m_bytes[luaString.m_offset + i2];
            if (b < 32 || b > 126 || b == 34 || b == 92) {
                if (b == 34) {
                    strSubstring = "\\\"";
                } else if (b != 92) {
                    switch (b) {
                        case 7:
                            strSubstring = "\\a";
                            break;
                        case 8:
                            strSubstring = "\\b";
                            break;
                        case 9:
                            strSubstring = "\\t";
                            break;
                        case 10:
                            strSubstring = "\\n";
                            break;
                        case 11:
                            strSubstring = "\\v";
                            break;
                        case 12:
                            strSubstring = "\\f";
                            break;
                        case 13:
                            strSubstring = "\\r";
                            break;
                        default:
                            printStream.print('\\');
                            strSubstring = Integer.toString(b & 1255).substring(1);
                            break;
                    }
                } else {
                    strSubstring = "\\\\";
                }
                printStream.print(strSubstring);
            } else {
                printStream.print((char) b);
            }
        }
        printStream.print('\"');
    }

    static void printUpValues(Prototype prototype) {
        int length = prototype.upvalues.length;
        ps.print(new StringBuffer("upvalues (").append(length).append(") for ").append(id(prototype)).append(":\n").toString());
        for (int i = 0; i < length; i++) {
            ps.print(new StringBuffer("  ").append(i).append("  ").append(prototype.upvalues[i]).append("\n").toString());
        }
    }

    static void printUpvalue(PrintStream printStream, Upvaldesc upvaldesc) {
        printStream.print(new StringBuffer().append((int) upvaldesc.idx).append(" ").toString());
        printValue(printStream, upvaldesc.f1043name);
    }

    static void printValue(PrintStream printStream, LuaValue luaValue) {
        if (luaValue.type() != 4) {
            printStream.print(luaValue.tojstring());
        } else {
            printString(printStream, (LuaString) luaValue);
        }
    }
}
