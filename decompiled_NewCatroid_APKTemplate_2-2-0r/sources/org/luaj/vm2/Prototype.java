package org.luaj.vm2;

import com.beust.jcommander.Parameters;
import com.huawei.hms.framework.common.ContainerUtils;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes6.dex */
public class Prototype {
    public int[] code;
    public int is_vararg;
    public LuaValue[] k;
    public int lastlinedefined;
    public int linedefined;
    public int[] lineinfo;
    public LocVars[] locvars;
    public int maxstacksize;
    public int numparams;
    public Prototype[] p;
    public LuaString source;
    public Upvaldesc[] upvalues;
    private static final Upvaldesc[] NOUPVALUES = new Upvaldesc[0];
    private static final Prototype[] NOSUBPROTOS = new Prototype[0];

    public Prototype() {
        this.p = NOSUBPROTOS;
        this.upvalues = NOUPVALUES;
    }

    public Prototype(int i) {
        this.p = NOSUBPROTOS;
        this.upvalues = new Upvaldesc[i];
    }

    public LuaString getlocalname(int i, int i2) {
        int i3 = 0;
        while (true) {
            LocVars[] locVarsArr = this.locvars;
            if (i3 >= locVarsArr.length || locVarsArr[i3].startpc > i2) {
                return null;
            }
            if (i2 < this.locvars[i3].endpc && (i = i - 1) == 0) {
                return this.locvars[i3].varname;
            }
            i3++;
        }
    }

    public String shortsource() {
        String str = this.source.tojstring();
        if (str.startsWith(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT) || str.startsWith(ContainerUtils.KEY_VALUE_DELIMITER)) {
            return str.substring(1);
        }
        return str.startsWith("\u001b") ? LoadState.SOURCE_BINARY_STRING : str;
    }

    public String toString() {
        return new StringBuffer().append(this.source).append(":").append(this.linedefined).append(Parameters.DEFAULT_OPTION_PREFIXES).append(this.lastlinedefined).toString();
    }
}
