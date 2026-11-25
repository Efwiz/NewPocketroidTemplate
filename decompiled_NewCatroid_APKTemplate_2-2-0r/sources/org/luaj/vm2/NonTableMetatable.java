package org.luaj.vm2;

/* JADX INFO: loaded from: classes6.dex */
class NonTableMetatable implements Metatable {
    private final LuaValue value;

    public NonTableMetatable(LuaValue luaValue) {
        this.value = luaValue;
    }

    @Override // org.luaj.vm2.Metatable
    public LuaValue arrayget(LuaValue[] luaValueArr, int i) {
        return luaValueArr[i];
    }

    @Override // org.luaj.vm2.Metatable
    public LuaTable.Slot entry(LuaValue luaValue, LuaValue luaValue2) {
        return LuaTable.defaultEntry(luaValue, luaValue2);
    }

    @Override // org.luaj.vm2.Metatable
    public LuaValue toLuaValue() {
        return this.value;
    }

    @Override // org.luaj.vm2.Metatable
    public boolean useWeakKeys() {
        return false;
    }

    @Override // org.luaj.vm2.Metatable
    public boolean useWeakValues() {
        return false;
    }

    @Override // org.luaj.vm2.Metatable
    public LuaValue wrap(LuaValue luaValue) {
        return luaValue;
    }
}
