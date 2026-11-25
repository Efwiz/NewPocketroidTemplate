package org.luaj.vm2;

/* JADX INFO: loaded from: classes6.dex */
public class Upvaldesc {
    public final short idx;
    public final boolean instack;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public LuaString f1043name;

    public Upvaldesc(LuaString luaString, boolean z, int i) {
        this.f1043name = luaString;
        this.instack = z;
        this.idx = (short) i;
    }

    public String toString() {
        return new StringBuffer().append((int) this.idx).append(this.instack ? " instack " : " closed ").append(String.valueOf(this.f1043name)).toString();
    }
}
