package org.luaj.vm2;

import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes6.dex */
public class LuaThread extends LuaValue {
    public static final int MAX_CALLSTACK = 256;
    public static final int STATUS_DEAD = 4;
    public static final int STATUS_INITIAL = 0;
    public static final String[] STATUS_NAMES = {"suspended", "suspended", "running", "normal", "dead"};
    public static final int STATUS_NORMAL = 3;
    public static final int STATUS_RUNNING = 2;
    public static final int STATUS_SUSPENDED = 1;
    public static int coroutine_count = 0;
    public static LuaValue s_metatable = null;
    public static long thread_orphan_check_interval = 5000;
    public Object callstack;
    public LuaValue errorfunc;
    public final Globals globals;
    public final State state;

    public static class State implements Runnable {
        public int bytecodes;
        public final LuaValue function;
        private final Globals globals;
        public boolean hookcall;
        public int hookcount;
        public LuaValue hookfunc;
        public boolean hookline;
        public boolean hookrtrn;
        public boolean inhook;
        public int lastline;
        final WeakReference lua_thread;
        Varargs args = LuaValue.NONE;
        Varargs result = LuaValue.NONE;
        String error = null;
        public int status = 0;

        State(Globals globals, LuaThread luaThread, LuaValue luaValue) {
            this.globals = globals;
            this.lua_thread = new WeakReference(luaThread);
            this.function = luaValue;
        }

        public synchronized Varargs lua_resume(LuaThread luaThread, Varargs varargs) {
            LuaBoolean luaBoolean;
            Varargs varargsValueOf;
            Varargs varargsVarargsOf;
            LuaThread luaThread2 = this.globals.running;
            try {
                try {
                    this.globals.running = luaThread;
                    this.args = varargs;
                    if (this.status == 0) {
                        this.status = 2;
                        StringBuffer stringBuffer = new StringBuffer("Coroutine-");
                        int i = LuaThread.coroutine_count + 1;
                        LuaThread.coroutine_count = i;
                        new Thread(this, stringBuffer.append(i).toString()).start();
                    } else {
                        notify();
                    }
                    if (luaThread2 != null) {
                        luaThread2.state.status = 3;
                    }
                    this.status = 2;
                    wait();
                    if (this.error != null) {
                        luaBoolean = LuaValue.FALSE;
                        varargsValueOf = LuaValue.valueOf(this.error);
                    } else {
                        luaBoolean = LuaValue.TRUE;
                        varargsValueOf = this.result;
                    }
                    varargsVarargsOf = LuaValue.varargsOf(luaBoolean, varargsValueOf);
                    this.args = LuaValue.NONE;
                    this.result = LuaValue.NONE;
                    this.error = null;
                    this.globals.running = luaThread2;
                    if (luaThread2 != null) {
                        this.globals.running.state.status = 2;
                    }
                } catch (InterruptedException unused) {
                    throw new OrphanedThread();
                }
            } catch (Throwable th) {
                this.args = LuaValue.NONE;
                this.result = LuaValue.NONE;
                this.error = null;
                this.globals.running = luaThread2;
                if (luaThread2 != null) {
                    this.globals.running.state.status = 2;
                }
                throw th;
            }
            return varargsVarargsOf;
        }

        public synchronized Varargs lua_yield(Varargs varargs) {
            Varargs varargs2;
            try {
                try {
                    this.result = varargs;
                    this.status = 1;
                    notify();
                    do {
                        wait(LuaThread.thread_orphan_check_interval);
                        if (this.lua_thread.get() == null) {
                            this.status = 4;
                            throw new OrphanedThread();
                        }
                    } while (this.status == 1);
                    varargs2 = this.args;
                    this.args = LuaValue.NONE;
                    this.result = LuaValue.NONE;
                } catch (InterruptedException unused) {
                    this.status = 4;
                    throw new OrphanedThread();
                }
            } catch (Throwable th) {
                this.args = LuaValue.NONE;
                this.result = LuaValue.NONE;
                throw th;
            }
            return varargs2;
        }

        @Override // java.lang.Runnable
        public synchronized void run() {
            try {
                Varargs varargs = this.args;
                this.args = LuaValue.NONE;
                this.result = this.function.invoke(varargs);
                this.status = 4;
            } catch (Throwable th) {
                try {
                    this.error = th.getMessage();
                    this.status = 4;
                } catch (Throwable th2) {
                    this.status = 4;
                    notify();
                    throw th2;
                }
            }
            notify();
        }
    }

    public LuaThread(Globals globals) {
        State state = new State(globals, this, null);
        this.state = state;
        state.status = 2;
        this.globals = globals;
    }

    public LuaThread(Globals globals, LuaValue luaValue) {
        LuaValue.assert_(luaValue != null, "function cannot be null");
        this.state = new State(globals, this, luaValue);
        this.globals = globals;
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaThread checkthread() {
        return this;
    }

    public String getStatus() {
        return STATUS_NAMES[this.state.status];
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue getmetatable() {
        return s_metatable;
    }

    public boolean isMainThread() {
        return this.state.function == null;
    }

    @Override // org.luaj.vm2.LuaValue
    public boolean isthread() {
        return true;
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaThread optthread(LuaThread luaThread) {
        return this;
    }

    public Varargs resume(Varargs varargs) {
        State state = this.state;
        if (state.status > 1) {
            return LuaValue.varargsOf(LuaValue.FALSE, LuaValue.valueOf(new StringBuffer("cannot resume ").append(state.status == 4 ? "dead" : "non-suspended").append(" coroutine").toString()));
        }
        return state.lua_resume(this, varargs);
    }

    @Override // org.luaj.vm2.LuaValue
    public int type() {
        return 8;
    }

    @Override // org.luaj.vm2.LuaValue
    public String typename() {
        return "thread";
    }
}
