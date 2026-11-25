package org.luaj.vm2.lib;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import net.bytebuddy.description.type.TypeDescription;
import org.koin.core.instance.InstanceFactory;
import org.luaj.vm2.Globals;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaNil;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Print;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.Varargs;

/* JADX INFO: loaded from: classes6.dex */
public class DebugLib extends TwoArgFunction {
    private static final LuaString ACTIVELINES;
    private static final LuaString CALL;
    public static boolean CALLS;
    private static final LuaString COUNT;
    private static final LuaString CURRENTLINE;
    private static final LuaString FUNC;
    private static final LuaString ISTAILCALL;
    private static final LuaString ISVARARG;
    private static final LuaString LASTLINEDEFINED;
    private static final LuaString LINE;
    private static final LuaString LINEDEFINED;
    private static final LuaString LUA;
    private static final LuaString NAME;
    private static final LuaString NAMEWHAT;
    private static final LuaString NPARAMS;
    private static final LuaString NUPS;
    private static final LuaString QMARK;
    private static final LuaString RETURN;
    private static final LuaString SHORT_SRC;
    private static final LuaString SOURCE;
    public static boolean TRACE;
    private static final LuaString WHAT;
    Globals globals;

    static class CallFrame {
        LuaFunction f;
        int pc;
        CallFrame previous;
        LuaValue[] stack;
        int top;
        Varargs v;

        CallFrame() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int linedefined() {
            if (this.f.isclosure()) {
                return this.f.checkclosure().p.linedefined;
            }
            return -1;
        }

        int currentline() {
            int[] iArr;
            int i;
            if (this.f.isclosure() && (iArr = this.f.checkclosure().p.lineinfo) != null && (i = this.pc) >= 0 && i < iArr.length) {
                return iArr[i];
            }
            return -1;
        }

        Varargs getLocal(int i) {
            LuaString luaString = getlocalname(i);
            return luaString != null ? LuaValue.varargsOf(luaString, this.stack[i - 1]) : LuaValue.NIL;
        }

        LuaString getlocalname(int i) {
            if (this.f.isclosure()) {
                return this.f.checkclosure().p.getlocalname(i, this.pc);
            }
            return null;
        }

        void instr(int i, Varargs varargs, int i2) {
            this.pc = i;
            this.v = varargs;
            this.top = i2;
            if (DebugLib.TRACE) {
                Print.printState(this.f.checkclosure(), i, this.stack, i2, varargs);
            }
        }

        void reset() {
            this.f = null;
            this.v = null;
            this.stack = null;
        }

        void set(LuaClosure luaClosure, Varargs varargs, LuaValue[] luaValueArr) {
            this.f = luaClosure;
            this.v = varargs;
            this.stack = luaValueArr;
        }

        void set(LuaFunction luaFunction) {
            this.f = luaFunction;
        }

        Varargs setLocal(int i, LuaValue luaValue) {
            LuaString luaString = getlocalname(i);
            if (luaString == null) {
                return LuaValue.NIL;
            }
            this.stack[i - 1] = luaValue;
            return luaString;
        }

        public String shortsource() {
            return this.f.isclosure() ? this.f.checkclosure().p.shortsource() : "[Java]";
        }

        String sourceline() {
            return !this.f.isclosure() ? this.f.tojstring() : new StringBuffer().append(this.f.checkclosure().p.shortsource()).append(":").append(currentline()).toString();
        }
    }

    public static class CallStack {
        static final CallFrame[] EMPTY = new CallFrame[0];
        CallFrame[] frame = EMPTY;
        int calls = 0;

        CallStack() {
        }

        private synchronized CallFrame pushcall() {
            CallFrame[] callFrameArr;
            int i;
            int i2 = this.calls;
            CallFrame[] callFrameArr2 = this.frame;
            if (i2 >= callFrameArr2.length) {
                int iMax = Math.max(4, (callFrameArr2.length * 3) / 2);
                CallFrame[] callFrameArr3 = new CallFrame[iMax];
                CallFrame[] callFrameArr4 = this.frame;
                System.arraycopy(callFrameArr4, 0, callFrameArr3, 0, callFrameArr4.length);
                for (int length = this.frame.length; length < iMax; length++) {
                    callFrameArr3[length] = new CallFrame();
                }
                this.frame = callFrameArr3;
                for (int i3 = 1; i3 < iMax; i3++) {
                    callFrameArr3[i3].previous = callFrameArr3[i3 - 1];
                }
            }
            callFrameArr = this.frame;
            i = this.calls;
            this.calls = i + 1;
            return callFrameArr[i];
        }

        synchronized DebugInfo auxgetinfo(String str, LuaFunction luaFunction, CallFrame callFrame) {
            DebugInfo debugInfo;
            NameWhat nameWhat;
            debugInfo = new DebugInfo();
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char cCharAt = str.charAt(i);
                if (cCharAt == 'S') {
                    debugInfo.funcinfo(luaFunction);
                } else if (cCharAt == 'l') {
                    debugInfo.currentline = (callFrame == null || !callFrame.f.isclosure()) ? -1 : callFrame.currentline();
                } else if (cCharAt == 'n') {
                    if (callFrame != null && callFrame.previous != null && callFrame.previous.f.isclosure() && (nameWhat = DebugLib.getfuncname(callFrame.previous)) != null) {
                        debugInfo.f1057name = nameWhat.f1058name;
                        debugInfo.namewhat = nameWhat.namewhat;
                    }
                    if (debugInfo.namewhat == null) {
                        debugInfo.namewhat = "";
                        debugInfo.f1057name = null;
                    }
                } else if (cCharAt == 't') {
                    debugInfo.istailcall = false;
                } else if (cCharAt == 'u') {
                    boolean z = true;
                    if (luaFunction == null || !luaFunction.isclosure()) {
                        debugInfo.nups = (short) 0;
                        debugInfo.isvararg = true;
                        debugInfo.nparams = (short) 0;
                    } else {
                        Prototype prototype = luaFunction.checkclosure().p;
                        debugInfo.nups = (short) prototype.upvalues.length;
                        debugInfo.nparams = (short) prototype.numparams;
                        if (prototype.is_vararg == 0) {
                            z = false;
                        }
                        debugInfo.isvararg = z;
                    }
                }
            }
            return debugInfo;
        }

        synchronized int currentline() {
            int i;
            i = this.calls;
            return i > 0 ? this.frame[i - 1].currentline() : -1;
        }

        synchronized CallFrame findCallFrame(LuaValue luaValue) {
            int i = 1;
            while (true) {
                int i2 = this.calls;
                if (i > i2) {
                    return null;
                }
                if (this.frame[i2 - i].f == luaValue) {
                    return this.frame[i];
                }
                i++;
            }
        }

        synchronized CallFrame getCallFrame(int i) {
            if (i >= 1) {
                int i2 = this.calls;
                if (i <= i2) {
                    return this.frame[i2 - i];
                }
            }
            return null;
        }

        final synchronized void onCall(LuaClosure luaClosure, Varargs varargs, LuaValue[] luaValueArr) {
            pushcall().set(luaClosure, varargs, luaValueArr);
        }

        final synchronized void onCall(LuaFunction luaFunction) {
            pushcall().set(luaFunction);
        }

        final synchronized void onInstruction(int i, Varargs varargs, int i2) {
            int i3 = this.calls;
            if (i3 > 0) {
                this.frame[i3 - 1].instr(i, varargs, i2);
            }
        }

        final synchronized void onReturn() {
            int i = this.calls;
            if (i > 0) {
                CallFrame[] callFrameArr = this.frame;
                int i2 = i - 1;
                this.calls = i2;
                callFrameArr[i2].reset();
            }
        }

        synchronized String traceback(int i) {
            StringBuffer stringBuffer;
            String string;
            stringBuffer = new StringBuffer();
            stringBuffer.append("stack traceback:");
            while (true) {
                int i2 = i + 1;
                CallFrame callFrame = getCallFrame(i);
                if (callFrame != null) {
                    stringBuffer.append(InstanceFactory.ERROR_SEPARATOR);
                    stringBuffer.append(callFrame.shortsource());
                    stringBuffer.append(':');
                    if (callFrame.currentline() > 0) {
                        stringBuffer.append(new StringBuffer().append(callFrame.currentline()).append(":").toString());
                    }
                    stringBuffer.append(" in ");
                    DebugInfo debugInfoAuxgetinfo = auxgetinfo("n", callFrame.f, callFrame);
                    if (callFrame.linedefined() == 0) {
                        string = "main chunk";
                    } else {
                        if (debugInfoAuxgetinfo.f1057name != null) {
                            stringBuffer.append("function '");
                            stringBuffer.append(debugInfoAuxgetinfo.f1057name);
                            stringBuffer.append('\'');
                        } else {
                            string = new StringBuffer().append("function <").append(callFrame.shortsource()).append(":").append(callFrame.linedefined()).append(">").toString();
                        }
                        i = i2;
                    }
                    stringBuffer.append(string);
                    i = i2;
                } else {
                    stringBuffer.append("\n\t[Java]: in ?");
                }
            }
            return stringBuffer.toString();
        }
    }

    static class DebugInfo {
        CallFrame cf;
        int currentline;
        boolean istailcall;
        boolean isvararg;
        int lastlinedefined;
        int linedefined;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        String f1057name;
        String namewhat;
        short nparams;
        short nups;
        String short_src;
        String source;
        String what;

        DebugInfo() {
        }

        public void funcinfo(LuaFunction luaFunction) {
            String strName;
            if (luaFunction.isclosure()) {
                Prototype prototype = luaFunction.checkclosure().p;
                this.source = prototype.source != null ? prototype.source.tojstring() : "=?";
                this.linedefined = prototype.linedefined;
                this.lastlinedefined = prototype.lastlinedefined;
                this.what = this.linedefined == 0 ? "main" : "Lua";
                strName = prototype.shortsource();
            } else {
                this.source = "=[Java]";
                this.linedefined = -1;
                this.lastlinedefined = -1;
                this.what = "Java";
                strName = luaFunction.name();
            }
            this.short_src = strName;
        }
    }

    static class NameWhat {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        final String f1058name;
        final String namewhat;

        NameWhat(String str, String str2) {
            this.f1058name = str;
            this.namewhat = str2;
        }
    }

    static final class debug extends ZeroArgFunction {
        debug() {
        }

        @Override // org.luaj.vm2.lib.ZeroArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call() {
            return NONE;
        }
    }

    final class gethook extends VarArgFunction {
        gethook() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaThread.State state = (varargs.narg() > 0 ? varargs.checkthread(1) : DebugLib.this.globals.running).state;
            return varargsOf(state.hookfunc != null ? state.hookfunc : NIL, valueOf(new StringBuffer().append(state.hookcall ? "c" : "").append(state.hookline ? "l" : "").append(state.hookrtrn ? "r" : "").toString()), valueOf(state.hookcount));
        }
    }

    final class getinfo extends VarArgFunction {
        getinfo() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaThread luaThreadCheckthread;
            int i;
            CallFrame callFrameFindCallFrame;
            int i2 = 1;
            if (varargs.isthread(1)) {
                luaThreadCheckthread = varargs.checkthread(1);
                i = 2;
            } else {
                luaThreadCheckthread = DebugLib.this.globals.running;
                i = 1;
            }
            LuaValue luaValueArg = varargs.arg(i);
            String strOptjstring = varargs.optjstring(i + 1, "flnStu");
            CallStack callstack = DebugLib.this.callstack(luaThreadCheckthread);
            if (luaValueArg.isnumber()) {
                callFrameFindCallFrame = callstack.getCallFrame(luaValueArg.toint());
                if (callFrameFindCallFrame == null) {
                    return NONE;
                }
                luaValueArg = callFrameFindCallFrame.f;
            } else {
                if (!luaValueArg.isfunction()) {
                    return argerror(i, "function or level");
                }
                callFrameFindCallFrame = callstack.findCallFrame(luaValueArg);
            }
            DebugInfo debugInfoAuxgetinfo = callstack.auxgetinfo(strOptjstring, (LuaFunction) luaValueArg, callFrameFindCallFrame);
            LuaTable luaTable = new LuaTable();
            if (strOptjstring.indexOf(83) >= 0) {
                luaTable.set(DebugLib.WHAT, DebugLib.LUA);
                luaTable.set(DebugLib.SOURCE, valueOf(debugInfoAuxgetinfo.source));
                luaTable.set(DebugLib.SHORT_SRC, valueOf(debugInfoAuxgetinfo.short_src));
                luaTable.set(DebugLib.LINEDEFINED, valueOf(debugInfoAuxgetinfo.linedefined));
                luaTable.set(DebugLib.LASTLINEDEFINED, valueOf(debugInfoAuxgetinfo.lastlinedefined));
            }
            if (strOptjstring.indexOf(108) >= 0) {
                luaTable.set(DebugLib.CURRENTLINE, valueOf(debugInfoAuxgetinfo.currentline));
            }
            if (strOptjstring.indexOf(117) >= 0) {
                luaTable.set(DebugLib.NUPS, valueOf((int) debugInfoAuxgetinfo.nups));
                luaTable.set(DebugLib.NPARAMS, valueOf((int) debugInfoAuxgetinfo.nparams));
                luaTable.set(DebugLib.ISVARARG, debugInfoAuxgetinfo.isvararg ? ONE : ZERO);
            }
            if (strOptjstring.indexOf(110) >= 0) {
                luaTable.set(DebugLib.NAME, LuaValue.valueOf(debugInfoAuxgetinfo.f1057name != null ? debugInfoAuxgetinfo.f1057name : TypeDescription.Generic.OfWildcardType.SYMBOL));
                luaTable.set(DebugLib.NAMEWHAT, LuaValue.valueOf(debugInfoAuxgetinfo.namewhat));
            }
            if (strOptjstring.indexOf(116) >= 0) {
                luaTable.set(DebugLib.ISTAILCALL, ZERO);
            }
            if (strOptjstring.indexOf(76) >= 0) {
                LuaTable luaTable2 = new LuaTable();
                luaTable.set(DebugLib.ACTIVELINES, luaTable2);
                while (true) {
                    CallFrame callFrame = callstack.getCallFrame(i2);
                    if (callFrame == null) {
                        break;
                    }
                    if (callFrame.f == luaValueArg) {
                        luaTable2.insert(-1, valueOf(callFrame.currentline()));
                    }
                    i2++;
                }
            }
            if (strOptjstring.indexOf(102) >= 0 && luaValueArg != null) {
                luaTable.set(DebugLib.FUNC, luaValueArg);
            }
            return luaTable;
        }
    }

    final class getlocal extends VarArgFunction {
        getlocal() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            int i;
            LuaThread luaThreadCheckthread;
            if (varargs.isthread(1)) {
                luaThreadCheckthread = varargs.checkthread(1);
                i = 2;
            } else {
                i = 1;
                luaThreadCheckthread = DebugLib.this.globals.running;
            }
            int i2 = i + 1;
            int iCheckint = varargs.checkint(i);
            int iCheckint2 = varargs.checkint(i2);
            CallFrame callFrame = DebugLib.this.callstack(luaThreadCheckthread).getCallFrame(iCheckint);
            return callFrame != null ? callFrame.getLocal(iCheckint2) : NONE;
        }
    }

    final class getmetatable extends LibFunction {
        getmetatable() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            LuaValue luaValue2 = luaValue.getmetatable();
            return luaValue2 != null ? luaValue2 : NIL;
        }
    }

    final class getregistry extends ZeroArgFunction {
        getregistry() {
        }

        @Override // org.luaj.vm2.lib.ZeroArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call() {
            return DebugLib.this.globals;
        }
    }

    static final class getupvalue extends VarArgFunction {
        getupvalue() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaClosure luaClosure;
            LuaString luaStringFindupvalue;
            LuaFunction luaFunctionCheckfunction = varargs.checkfunction(1);
            int iCheckint = varargs.checkint(2);
            return (!(luaFunctionCheckfunction instanceof LuaClosure) || (luaStringFindupvalue = DebugLib.findupvalue((luaClosure = (LuaClosure) luaFunctionCheckfunction), iCheckint)) == null) ? NIL : varargsOf(luaStringFindupvalue, luaClosure.upValues[iCheckint - 1].getValue());
        }
    }

    static final class getuservalue extends LibFunction {
        getuservalue() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return luaValue.isuserdata() ? luaValue : NIL;
        }
    }

    final class sethook extends VarArgFunction {
        sethook() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaThread luaThreadCheckthread;
            int i;
            if (varargs.isthread(1)) {
                luaThreadCheckthread = varargs.checkthread(1);
                i = 2;
            } else {
                luaThreadCheckthread = DebugLib.this.globals.running;
                i = 1;
            }
            LuaFunction luaFunctionOptfunction = varargs.optfunction(i, null);
            String strOptjstring = varargs.optjstring(i + 1, "");
            int iOptint = varargs.optint(i + 2, 0);
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            for (int i2 = 0; i2 < strOptjstring.length(); i2++) {
                char cCharAt = strOptjstring.charAt(i2);
                if (cCharAt == 'c') {
                    z = true;
                } else if (cCharAt == 'l') {
                    z2 = true;
                } else if (cCharAt == 'r') {
                    z3 = true;
                }
            }
            LuaThread.State state = luaThreadCheckthread.state;
            state.hookfunc = luaFunctionOptfunction;
            state.hookcall = z;
            state.hookline = z2;
            state.hookcount = iOptint;
            state.hookrtrn = z3;
            return NONE;
        }
    }

    final class setlocal extends VarArgFunction {
        setlocal() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            int i;
            LuaThread luaThreadCheckthread;
            if (varargs.isthread(1)) {
                luaThreadCheckthread = varargs.checkthread(1);
                i = 2;
            } else {
                i = 1;
                luaThreadCheckthread = DebugLib.this.globals.running;
            }
            int iCheckint = varargs.checkint(i);
            int iCheckint2 = varargs.checkint(i + 1);
            LuaValue luaValueArg = varargs.arg(i + 2);
            CallFrame callFrame = DebugLib.this.callstack(luaThreadCheckthread).getCallFrame(iCheckint);
            return callFrame != null ? callFrame.setLocal(iCheckint2, luaValueArg) : NONE;
        }
    }

    final class setmetatable extends TwoArgFunction {
        setmetatable() {
        }

        @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            LuaTable luaTableOpttable = luaValue2.opttable(null);
            int iType = luaValue.type();
            if (iType == 0) {
                LuaNil.s_metatable = luaTableOpttable;
            } else if (iType == 1) {
                LuaBoolean.s_metatable = luaTableOpttable;
            } else if (iType == 3) {
                LuaNumber.s_metatable = luaTableOpttable;
            } else if (iType == 4) {
                LuaString.s_metatable = luaTableOpttable;
            } else if (iType == 6) {
                LuaFunction.s_metatable = luaTableOpttable;
            } else if (iType != 8) {
                luaValue.setmetatable(luaTableOpttable);
            } else {
                LuaThread.s_metatable = luaTableOpttable;
            }
            return luaValue;
        }
    }

    final class setupvalue extends VarArgFunction {
        setupvalue() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaClosure luaClosure;
            LuaString luaStringFindupvalue;
            LuaFunction luaFunctionCheckfunction = varargs.checkfunction(1);
            int iCheckint = varargs.checkint(2);
            LuaValue luaValueArg = varargs.arg(3);
            if (!(luaFunctionCheckfunction instanceof LuaClosure) || (luaStringFindupvalue = DebugLib.findupvalue((luaClosure = (LuaClosure) luaFunctionCheckfunction), iCheckint)) == null) {
                return NIL;
            }
            luaClosure.upValues[iCheckint - 1].setValue(luaValueArg);
            return luaStringFindupvalue;
        }
    }

    final class setuservalue extends VarArgFunction {
        setuservalue() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            varargs.checkuserdata(1);
            LuaValue luaValueCheckvalue = varargs.checkvalue(2);
            LuaUserdata luaUserdata = (LuaUserdata) varargs.arg1();
            luaUserdata.m_instance = luaValueCheckvalue.checkuserdata();
            luaUserdata.m_metatable = luaValueCheckvalue.getmetatable();
            return NONE;
        }
    }

    final class traceback extends VarArgFunction {
        traceback() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaThread luaThreadCheckthread;
            int i;
            if (varargs.isthread(1)) {
                luaThreadCheckthread = varargs.checkthread(1);
                i = 2;
            } else {
                luaThreadCheckthread = DebugLib.this.globals.running;
                i = 1;
            }
            int i2 = i + 1;
            String strOptjstring = varargs.optjstring(i, null);
            String strTraceback = DebugLib.this.callstack(luaThreadCheckthread).traceback(varargs.optint(i2, 1));
            if (strOptjstring != null) {
                strTraceback = new StringBuffer().append(strOptjstring).append("\n").append(strTraceback).toString();
            }
            return valueOf(strTraceback);
        }
    }

    final class upvalueid extends VarArgFunction {
        upvalueid() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaFunction luaFunctionCheckfunction = varargs.checkfunction(1);
            int iCheckint = varargs.checkint(2);
            if (luaFunctionCheckfunction instanceof LuaClosure) {
                LuaClosure luaClosure = (LuaClosure) luaFunctionCheckfunction;
                if (luaClosure.upValues != null && iCheckint > 0 && iCheckint <= luaClosure.upValues.length) {
                    return valueOf(luaClosure.upValues[iCheckint - 1].hashCode());
                }
            }
            return NIL;
        }
    }

    final class upvaluejoin extends VarArgFunction {
        upvaluejoin() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaClosure luaClosureCheckclosure = varargs.checkclosure(1);
            int iCheckint = varargs.checkint(2);
            LuaClosure luaClosureCheckclosure2 = varargs.checkclosure(3);
            int iCheckint2 = varargs.checkint(4);
            if (iCheckint < 1 || iCheckint > luaClosureCheckclosure.upValues.length) {
                argerror("index out of range");
            }
            if (iCheckint2 < 1 || iCheckint2 > luaClosureCheckclosure2.upValues.length) {
                argerror("index out of range");
            }
            luaClosureCheckclosure.upValues[iCheckint - 1] = luaClosureCheckclosure2.upValues[iCheckint2 - 1];
            return NONE;
        }
    }

    static {
        boolean z = true;
        try {
            CALLS = System.getProperty("CALLS") != null;
        } catch (Exception unused) {
        }
        try {
            if (System.getProperty("TRACE") == null) {
                z = false;
            }
            TRACE = z;
        } catch (Exception unused2) {
        }
        LUA = valueOf("Lua");
        QMARK = valueOf(TypeDescription.Generic.OfWildcardType.SYMBOL);
        CALL = valueOf("call");
        LINE = valueOf(Constant.LINE);
        COUNT = valueOf("count");
        RETURN = valueOf("return");
        FUNC = valueOf("func");
        ISTAILCALL = valueOf("istailcall");
        ISVARARG = valueOf("isvararg");
        NUPS = valueOf("nups");
        NPARAMS = valueOf("nparams");
        NAME = valueOf("name");
        NAMEWHAT = valueOf("namewhat");
        WHAT = valueOf("what");
        SOURCE = valueOf(FirebaseAnalytics.Param.SOURCE);
        SHORT_SRC = valueOf("short_src");
        LINEDEFINED = valueOf("linedefined");
        LASTLINEDEFINED = valueOf("lastlinedefined");
        CURRENTLINE = valueOf("currentline");
        ACTIVELINES = valueOf("activelines");
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0051  */
    static int findsetreg(Prototype prototype, int i, int i2) {
        int i3 = -1;
        int i4 = 0;
        while (i4 < i) {
            int i5 = prototype.code[i4];
            int iGET_OPCODE = Lua.GET_OPCODE(i5);
            int iGETARG_A = Lua.GETARG_A(i5);
            if (iGET_OPCODE == 4) {
                int iGETARG_B = Lua.GETARG_B(i5);
                if (iGETARG_A <= i2 && i2 <= iGETARG_A + iGETARG_B) {
                    i3 = i4;
                }
            } else if (iGET_OPCODE == 23) {
                int iGETARG_sBx = Lua.GETARG_sBx(i5);
                int i6 = i4 + 1 + iGETARG_sBx;
                if (i4 < i6 && i6 <= i) {
                    i4 += iGETARG_sBx;
                }
            } else if (iGET_OPCODE != 27) {
                if (iGET_OPCODE != 34) {
                    if (iGET_OPCODE == 29 || iGET_OPCODE == 30) {
                        if (i2 >= iGETARG_A) {
                            i3 = i4;
                        }
                    } else if (Lua.testAMode(iGET_OPCODE) && i2 == iGETARG_A) {
                        i3 = i4;
                    }
                } else if (i2 >= iGETARG_A + 2) {
                    i3 = i4;
                }
            } else if (i2 == iGETARG_A) {
                i3 = i4;
            }
            i4++;
        }
        return i3;
    }

    static LuaString findupvalue(LuaClosure luaClosure, int i) {
        if (luaClosure.upValues == null || i <= 0 || i > luaClosure.upValues.length) {
            return null;
        }
        return (luaClosure.p.upvalues == null || i > luaClosure.p.upvalues.length) ? LuaString.valueOf(new StringBuffer(".").append(i).toString()) : luaClosure.p.upvalues[i - 1].f1043name;
    }

    static NameWhat getfuncname(CallFrame callFrame) {
        LuaString luaString;
        if (!callFrame.f.isclosure()) {
            return new NameWhat(callFrame.f.classnamestub(), "Java");
        }
        Prototype prototype = callFrame.f.checkclosure().p;
        int i = callFrame.pc;
        int i2 = prototype.code[i];
        switch (Lua.GET_OPCODE(i2)) {
            case 6:
            case 7:
            case 12:
                luaString = LuaValue.INDEX;
                break;
            case 8:
            case 10:
                luaString = LuaValue.NEWINDEX;
                break;
            case 9:
            case 11:
            case 20:
            case 23:
            case 27:
            case 28:
            case 31:
            case 32:
            case 33:
            default:
                return null;
            case 13:
                luaString = LuaValue.ADD;
                break;
            case 14:
                luaString = LuaValue.SUB;
                break;
            case 15:
                luaString = LuaValue.MUL;
                break;
            case 16:
                luaString = LuaValue.DIV;
                break;
            case 17:
                luaString = LuaValue.MOD;
                break;
            case 18:
                luaString = LuaValue.POW;
                break;
            case 19:
                luaString = LuaValue.UNM;
                break;
            case 21:
                luaString = LuaValue.LEN;
                break;
            case 22:
                luaString = LuaValue.CONCAT;
                break;
            case 24:
                luaString = LuaValue.EQ;
                break;
            case 25:
                luaString = LuaValue.LT;
                break;
            case 26:
                luaString = LuaValue.LE;
                break;
            case 29:
            case 30:
                return getobjname(prototype, i, Lua.GETARG_A(i2));
            case 34:
                return new NameWhat("(for iterator)", "(for iterator");
        }
        return new NameWhat(luaString.tojstring(), "metamethod");
    }

    public static NameWhat getobjname(Prototype prototype, int i, int i2) {
        LuaString luaString;
        LuaString luaString2 = prototype.getlocalname(i2 + 1, i);
        if (luaString2 != null) {
            return new NameWhat(luaString2.tojstring(), "local");
        }
        int iFindsetreg = findsetreg(prototype, i, i2);
        if (iFindsetreg == -1) {
            return null;
        }
        int i3 = prototype.code[iFindsetreg];
        int iGET_OPCODE = Lua.GET_OPCODE(i3);
        if (iGET_OPCODE == 0) {
            int iGETARG_A = Lua.GETARG_A(i3);
            int iGETARG_B = Lua.GETARG_B(i3);
            if (iGETARG_B < iGETARG_A) {
                return getobjname(prototype, iFindsetreg, iGETARG_B);
            }
            return null;
        }
        if (iGET_OPCODE == 1 || iGET_OPCODE == 2) {
            int iGETARG_Bx = Lua.GET_OPCODE(i3) == 1 ? Lua.GETARG_Bx(i3) : Lua.GETARG_Ax(prototype.code[iFindsetreg + 1]);
            if (prototype.k[iGETARG_Bx].isstring()) {
                return new NameWhat(prototype.k[iGETARG_Bx].strvalue().tojstring(), "constant");
            }
            return null;
        }
        if (iGET_OPCODE == 5) {
            int iGETARG_B2 = Lua.GETARG_B(i3);
            return new NameWhat((iGETARG_B2 < prototype.upvalues.length ? prototype.upvalues[iGETARG_B2].f1043name : QMARK).tojstring(), "upvalue");
        }
        if (iGET_OPCODE != 6 && iGET_OPCODE != 7) {
            if (iGET_OPCODE != 12) {
                return null;
            }
            return new NameWhat(kname(prototype, Lua.GETARG_C(i3)).tojstring(), "method");
        }
        int iGETARG_C = Lua.GETARG_C(i3);
        int iGETARG_B3 = Lua.GETARG_B(i3);
        if (Lua.GET_OPCODE(i3) == 7) {
            luaString = prototype.getlocalname(iGETARG_B3 + 1, iFindsetreg);
        } else {
            luaString = iGETARG_B3 < prototype.upvalues.length ? prototype.upvalues[iGETARG_B3].f1043name : QMARK;
        }
        return new NameWhat(kname(prototype, iGETARG_C).tojstring(), (luaString == null || !luaString.eq_b(ENV)) ? "field" : "global");
    }

    static LuaString kname(Prototype prototype, int i) {
        return (Lua.ISK(i) && prototype.k[Lua.INDEXK(i)].isstring()) ? prototype.k[Lua.INDEXK(i)].strvalue() : QMARK;
    }

    static void lua_assert(boolean z) {
        if (!z) {
            throw new RuntimeException("lua_assert failed");
        }
    }

    @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        Globals globalsCheckglobals = luaValue2.checkglobals();
        this.globals = globalsCheckglobals;
        globalsCheckglobals.debuglib = this;
        LuaTable luaTable = new LuaTable();
        luaTable.set("debug", new debug());
        luaTable.set("gethook", new gethook());
        luaTable.set("getinfo", new getinfo());
        luaTable.set("getlocal", new getlocal());
        luaTable.set("getmetatable", new getmetatable());
        luaTable.set("getregistry", new getregistry());
        luaTable.set("getupvalue", new getupvalue());
        luaTable.set("getuservalue", new getuservalue());
        luaTable.set("sethook", new sethook());
        luaTable.set("setlocal", new setlocal());
        luaTable.set("setmetatable", new setmetatable());
        luaTable.set("setupvalue", new setupvalue());
        luaTable.set("setuservalue", new setuservalue());
        luaTable.set("traceback", new traceback());
        luaTable.set("upvalueid", new upvalueid());
        luaTable.set("upvaluejoin", new upvaluejoin());
        luaValue2.set("debug", luaTable);
        luaValue2.get("package").get("loaded").set("debug", luaTable);
        return luaTable;
    }

    void callHook(LuaThread.State state, LuaValue luaValue, LuaValue luaValue2) {
        if (state.inhook || state.hookfunc == null) {
            return;
        }
        state.inhook = true;
        try {
            try {
                state.hookfunc.call(luaValue, luaValue2);
                state.inhook = false;
            } catch (LuaError e) {
                throw e;
            } catch (RuntimeException e2) {
                throw new LuaError(e2);
            }
        } catch (Throwable th) {
            state.inhook = false;
            throw th;
        }
    }

    CallStack callstack() {
        return callstack(this.globals.running);
    }

    CallStack callstack(LuaThread luaThread) {
        if (luaThread.callstack == null) {
            luaThread.callstack = new CallStack();
        }
        return (CallStack) luaThread.callstack;
    }

    public void onCall(LuaClosure luaClosure, Varargs varargs, LuaValue[] luaValueArr) {
        LuaThread.State state = this.globals.running.state;
        if (state.inhook) {
            return;
        }
        callstack().onCall(luaClosure, varargs, luaValueArr);
        if (state.hookcall) {
            callHook(state, CALL, NIL);
        }
    }

    public void onCall(LuaFunction luaFunction) {
        LuaThread.State state = this.globals.running.state;
        if (state.inhook) {
            return;
        }
        callstack().onCall(luaFunction);
        if (state.hookcall) {
            callHook(state, CALL, NIL);
        }
    }

    public void onInstruction(int i, Varargs varargs, int i2) {
        int iCurrentline;
        LuaThread.State state = this.globals.running.state;
        if (state.inhook) {
            return;
        }
        callstack().onInstruction(i, varargs, i2);
        if (state.hookfunc == null) {
            return;
        }
        if (state.hookcount > 0) {
            int i3 = state.bytecodes + 1;
            state.bytecodes = i3;
            if (i3 % state.hookcount == 0) {
                callHook(state, COUNT, NIL);
            }
        }
        if (!state.hookline || (iCurrentline = callstack().currentline()) == state.lastline) {
            return;
        }
        state.lastline = iCurrentline;
        callHook(state, LINE, LuaValue.valueOf(iCurrentline));
    }

    public void onReturn() {
        LuaThread.State state = this.globals.running.state;
        if (state.inhook) {
            return;
        }
        callstack().onReturn();
        if (state.hookrtrn) {
            callHook(state, RETURN, NIL);
        }
    }

    public String traceback(int i) {
        return callstack().traceback(i);
    }
}
