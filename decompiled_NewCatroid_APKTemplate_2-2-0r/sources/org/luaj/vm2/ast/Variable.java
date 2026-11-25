package org.luaj.vm2.ast;

import org.luaj.vm2.LuaValue;

/* JADX INFO: loaded from: classes6.dex */
public class Variable {
    public final NameScope definingScope;
    public boolean hasassignments;
    public LuaValue initialValue;
    public boolean isupvalue;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public final String f1055name;

    public Variable(String str) {
        this.f1055name = str;
        this.definingScope = null;
    }

    public Variable(String str, NameScope nameScope) {
        this.f1055name = str;
        this.definingScope = nameScope;
    }

    public boolean isConstant() {
        return (this.hasassignments || this.initialValue == null) ? false : true;
    }

    public boolean isLocal() {
        return this.definingScope != null;
    }
}
