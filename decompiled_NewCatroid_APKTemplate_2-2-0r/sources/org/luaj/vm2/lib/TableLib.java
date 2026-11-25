package org.luaj.vm2.lib;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import org.eclipse.jgit.lib.ConfigConstants;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

/* JADX INFO: loaded from: classes6.dex */
public class TableLib extends TwoArgFunction {

    static class TableLibFunction extends LibFunction {
        TableLibFunction() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call() {
            return argerror(1, "table expected, got no value");
        }
    }

    static class concat extends TableLibFunction {
        concat() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return luaValue.checktable().concat(EMPTYSTRING, 1, luaValue.length());
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            return luaValue.checktable().concat(luaValue2.checkstring(), 1, luaValue.length());
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
            return luaValue.checktable().concat(luaValue2.checkstring(), luaValue3.checkint(), luaValue.length());
        }

        @Override // org.luaj.vm2.lib.LibFunction
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3, LuaValue luaValue4) {
            return luaValue.checktable().concat(luaValue2.checkstring(), luaValue3.checkint(), luaValue4.checkint());
        }
    }

    static class insert extends VarArgFunction {
        insert() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            int iNarg = varargs.narg();
            if (iNarg == 0 || iNarg == 1) {
                return argerror(2, "value expected");
            }
            if (iNarg != 2) {
                varargs.arg1().checktable().insert(varargs.checkint(2), varargs.arg(3));
                return NONE;
            }
            LuaTable luaTableChecktable = varargs.arg1().checktable();
            luaTableChecktable.insert(luaTableChecktable.length() + 1, varargs.arg(2));
            return NONE;
        }
    }

    static class pack extends VarArgFunction {
        pack() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaTable luaTableTableOf = tableOf(varargs, 1);
            luaTableTableOf.set("n", varargs.narg());
            return luaTableTableOf;
        }
    }

    static class remove extends VarArgFunction {
        remove() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            return varargs.arg1().checktable().remove(varargs.optint(2, 0));
        }
    }

    static class sort extends VarArgFunction {
        sort() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            varargs.arg1().checktable().sort(varargs.arg(2).isnil() ? NIL : varargs.arg(2).checkfunction());
            return NONE;
        }
    }

    static class unpack extends VarArgFunction {
        unpack() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaTable luaTableChecktable = varargs.checktable(1);
            int iNarg = varargs.narg();
            if (iNarg != 1) {
                return iNarg != 2 ? luaTableChecktable.unpack(varargs.checkint(2), varargs.checkint(3)) : luaTableChecktable.unpack(varargs.checkint(2));
            }
            return luaTableChecktable.unpack();
        }
    }

    @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaTable luaTable = new LuaTable();
        luaTable.set("concat", new concat());
        luaTable.set("insert", new insert());
        luaTable.set(ConfigConstants.CONFIG_PACK_SECTION, new pack());
        luaTable.set(ProductAction.ACTION_REMOVE, new remove());
        luaTable.set("sort", new sort());
        luaTable.set("unpack", new unpack());
        luaValue2.set("table", luaTable);
        luaValue2.get("package").get("loaded").set("table", luaTable);
        return NIL;
    }
}
