package org.luaj.vm2;

import com.beust.jcommander.Parameters;

/* JADX INFO: loaded from: classes6.dex */
public class LocVars {
    public int endpc;
    public int startpc;
    public LuaString varname;

    public LocVars(LuaString luaString, int i, int i2) {
        this.varname = luaString;
        this.startpc = i;
        this.endpc = i2;
    }

    public String tojstring() {
        return new StringBuffer().append(this.varname).append(" ").append(this.startpc).append(Parameters.DEFAULT_OPTION_PREFIXES).append(this.endpc).toString();
    }
}
