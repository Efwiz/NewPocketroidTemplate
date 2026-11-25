package org.mozilla.javascript;

import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class NativeJavaMap extends NativeJavaObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Map<Object, Object> map;

    public NativeJavaMap(Scriptable scriptable, Object obj) {
        super(scriptable, obj, obj.getClass());
        this.map = (Map) obj;
    }

    @Override // org.mozilla.javascript.NativeJavaObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "JavaMap";
    }

    @Override // org.mozilla.javascript.NativeJavaObject, org.mozilla.javascript.Scriptable
    public boolean has(String str, Scriptable scriptable) {
        if (this.map.containsKey(str)) {
            return true;
        }
        return super.has(str, scriptable);
    }

    @Override // org.mozilla.javascript.NativeJavaObject, org.mozilla.javascript.Scriptable
    public boolean has(int i, Scriptable scriptable) {
        if (this.map.containsKey(Integer.valueOf(i))) {
            return true;
        }
        return super.has(i, scriptable);
    }

    @Override // org.mozilla.javascript.NativeJavaObject, org.mozilla.javascript.Scriptable
    public Object get(String str, Scriptable scriptable) {
        if (this.map.containsKey(str)) {
            Context context = Context.getContext();
            Object obj = this.map.get(str);
            return context.getWrapFactory().wrap(context, this, obj, obj.getClass());
        }
        return super.get(str, scriptable);
    }

    @Override // org.mozilla.javascript.NativeJavaObject, org.mozilla.javascript.Scriptable
    public Object get(int i, Scriptable scriptable) {
        if (this.map.containsKey(Integer.valueOf(i))) {
            Context context = Context.getContext();
            Object obj = this.map.get(Integer.valueOf(i));
            return context.getWrapFactory().wrap(context, this, obj, obj.getClass());
        }
        return super.get(i, scriptable);
    }

    @Override // org.mozilla.javascript.NativeJavaObject, org.mozilla.javascript.Scriptable
    public void put(String str, Scriptable scriptable, Object obj) {
        this.map.put(str, Context.jsToJava(obj, Object.class));
    }

    @Override // org.mozilla.javascript.NativeJavaObject, org.mozilla.javascript.Scriptable
    public void put(int i, Scriptable scriptable, Object obj) {
        this.map.put(Integer.valueOf(i), Context.jsToJava(obj, Object.class));
    }

    @Override // org.mozilla.javascript.NativeJavaObject, org.mozilla.javascript.Scriptable
    public Object[] getIds() {
        ArrayList arrayList = new ArrayList(this.map.size());
        for (Object obj : this.map.keySet()) {
            if (obj instanceof Integer) {
                arrayList.add((Integer) obj);
            } else {
                arrayList.add(ScriptRuntime.toString(obj));
            }
        }
        return arrayList.toArray();
    }
}
