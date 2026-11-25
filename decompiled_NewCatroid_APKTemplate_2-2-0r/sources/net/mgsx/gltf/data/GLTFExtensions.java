package net.mgsx.gltf.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFExtensions implements Json.Serializable {
    private static final Json json = new Json();
    private ObjectMap<String, Object> extentions = new ObjectMap<>();
    private JsonValue value;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json2) {
        ObjectMap.Entries<String, Object> it = this.extentions.iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            json2.writeValue((String) next.key, next.value);
        }
    }

    @Override // com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json2, JsonValue jsonValue) {
        this.value = jsonValue;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public <T> T get(Class<T> cls, String str) {
        JsonValue jsonValue;
        T t = (T) this.extentions.get(str);
        if (t != null || (jsonValue = this.value) == null) {
            return t;
        }
        T t2 = (T) json.readValue(cls, jsonValue.get(str));
        this.extentions.put(str, t2);
        return t2;
    }

    public void set(String str, Object obj) {
        this.extentions.put(str, obj);
    }
}
