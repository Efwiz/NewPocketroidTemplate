package org.mozilla.javascript;

/* JADX INFO: loaded from: classes4.dex */
class SpecialRef extends Ref {
    private static final int SPECIAL_NONE = 0;
    private static final int SPECIAL_PARENT = 2;
    private static final int SPECIAL_PROTO = 1;
    private static final long serialVersionUID = -7521596632456797847L;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f1065name;
    private Scriptable target;
    private int type;

    private SpecialRef(Scriptable scriptable, int i, String str) {
        this.target = scriptable;
        this.type = i;
        this.f1065name = str;
    }

    static Ref createSpecial(Context context, Scriptable scriptable, Object obj, String str) {
        int i;
        Scriptable objectOrNull = ScriptRuntime.toObjectOrNull(context, obj, scriptable);
        if (objectOrNull == null) {
            throw ScriptRuntime.undefReadError(obj, str);
        }
        if (str.equals("__proto__")) {
            i = 1;
        } else {
            if (!str.equals("__parent__")) {
                throw new IllegalArgumentException(str);
            }
            i = 2;
        }
        if (!context.hasFeature(5)) {
            i = 0;
        }
        return new SpecialRef(objectOrNull, i, str);
    }

    @Override // org.mozilla.javascript.Ref
    public Object get(Context context) {
        int i = this.type;
        if (i == 0) {
            return ScriptRuntime.getObjectProp(this.target, this.f1065name, context);
        }
        if (i == 1) {
            return this.target.getPrototype();
        }
        if (i == 2) {
            return this.target.getParentScope();
        }
        throw Kit.codeBug();
    }

    @Override // org.mozilla.javascript.Ref
    @Deprecated
    public Object set(Context context, Object obj) {
        throw new IllegalStateException();
    }

    @Override // org.mozilla.javascript.Ref
    public Object set(Context context, Scriptable scriptable, Object obj) {
        int i = this.type;
        if (i == 0) {
            return ScriptRuntime.setObjectProp(this.target, this.f1065name, obj, context);
        }
        if (i == 1 || i == 2) {
            Scriptable objectOrNull = ScriptRuntime.toObjectOrNull(context, obj, scriptable);
            if (objectOrNull != null) {
                Scriptable parentScope = objectOrNull;
                do {
                    if (parentScope == this.target) {
                        throw Context.reportRuntimeError1("msg.cyclic.value", this.f1065name);
                    }
                    if (this.type == 1) {
                        parentScope = parentScope.getPrototype();
                    } else {
                        parentScope = parentScope.getParentScope();
                    }
                } while (parentScope != null);
            }
            if (this.type == 1) {
                if (context.getLanguageVersion() >= 200) {
                    if ((obj != null && !"object".equals(ScriptRuntime.typeof(obj))) || !"object".equals(ScriptRuntime.typeof(this.target))) {
                        return Undefined.instance;
                    }
                    this.target.setPrototype(objectOrNull);
                } else {
                    this.target.setPrototype(objectOrNull);
                }
            } else {
                this.target.setParentScope(objectOrNull);
            }
            return objectOrNull;
        }
        throw Kit.codeBug();
    }

    @Override // org.mozilla.javascript.Ref
    public boolean has(Context context) {
        if (this.type == 0) {
            return ScriptRuntime.hasObjectElem(this.target, this.f1065name, context);
        }
        return true;
    }

    @Override // org.mozilla.javascript.Ref
    public boolean delete(Context context) {
        if (this.type == 0) {
            return ScriptRuntime.deleteObjectElem(this.target, this.f1065name, context);
        }
        return false;
    }
}
