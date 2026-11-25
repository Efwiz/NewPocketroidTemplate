package org.mozilla.javascript;

import com.fasterxml.jackson.core.JsonFactory;
import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import okhttp3.HttpUrl;
import org.mozilla.javascript.json.JsonParser;

/* JADX INFO: loaded from: classes4.dex */
public final class NativeJSON extends IdScriptableObject {
    private static final int Id_parse = 2;
    private static final int Id_stringify = 3;
    private static final int Id_toSource = 1;
    private static final Object JSON_TAG = JsonFactory.FORMAT_NAME_JSON;
    private static final int LAST_METHOD_ID = 3;
    private static final int MAX_ID = 3;
    private static final int MAX_STRINGIFY_GAP_LENGTH = 10;
    private static final long serialVersionUID = -4567599697595654984L;

    static void init(Scriptable scriptable, boolean z) {
        NativeJSON nativeJSON = new NativeJSON();
        nativeJSON.activatePrototypeMap(3);
        nativeJSON.setPrototype(getObjectPrototype(scriptable));
        nativeJSON.setParentScope(scriptable);
        if (z) {
            nativeJSON.sealObject();
        }
        ScriptableObject.defineProperty(scriptable, JsonFactory.FORMAT_NAME_JSON, nativeJSON, 2);
    }

    private NativeJSON() {
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return JsonFactory.FORMAT_NAME_JSON;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i) {
        String str;
        int i2 = 3;
        if (i <= 3) {
            if (i == 1) {
                i2 = 0;
                str = "toSource";
            } else if (i == 2) {
                str = "parse";
                i2 = 2;
            } else if (i == 3) {
                str = "stringify";
            } else {
                throw new IllegalStateException(String.valueOf(i));
            }
            initPrototypeMethod(JSON_TAG, i, str, i2);
            return;
        }
        throw new IllegalStateException(String.valueOf(i));
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        Object obj2;
        Object obj3;
        if (!idFunctionObject.hasTag(JSON_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == 1) {
            return JsonFactory.FORMAT_NAME_JSON;
        }
        Object obj4 = null;
        if (iMethodId == 2) {
            String string = ScriptRuntime.toString(objArr, 0);
            obj4 = objArr.length > 1 ? objArr[1] : null;
            if (obj4 instanceof Callable) {
                return parse(context, scriptable, string, (Callable) obj4);
            }
            return parse(context, scriptable, string);
        }
        if (iMethodId == 3) {
            int length = objArr.length;
            if (length != 1) {
                if (length != 2) {
                    if (length != 3) {
                        obj3 = null;
                        obj2 = null;
                    } else {
                        obj4 = objArr[2];
                    }
                    return stringify(context, scriptable, obj4, obj3, obj2);
                }
                Object obj5 = obj4;
                obj4 = objArr[1];
                obj = obj5;
            } else {
                obj = null;
            }
            obj2 = obj;
            obj3 = obj4;
            obj4 = objArr[0];
            return stringify(context, scriptable, obj4, obj3, obj2);
        }
        throw new IllegalStateException(String.valueOf(iMethodId));
    }

    private static Object parse(Context context, Scriptable scriptable, String str) {
        try {
            return new JsonParser(context, scriptable).parseValue(str);
        } catch (JsonParser.ParseException e) {
            throw ScriptRuntime.constructError("SyntaxError", e.getMessage());
        }
    }

    public static Object parse(Context context, Scriptable scriptable, String str, Callable callable) {
        Object obj = parse(context, scriptable, str);
        Scriptable scriptableNewObject = context.newObject(scriptable);
        scriptableNewObject.put("", scriptableNewObject, obj);
        return walk(context, scriptable, callable, scriptableNewObject, "");
    }

    private static Object walk(Context context, Scriptable scriptable, Callable callable, Scriptable scriptable2, Object obj) {
        Object obj2;
        if (obj instanceof Number) {
            obj2 = scriptable2.get(((Number) obj).intValue(), scriptable2);
        } else {
            obj2 = scriptable2.get((String) obj, scriptable2);
        }
        if (obj2 instanceof Scriptable) {
            Scriptable scriptable3 = (Scriptable) obj2;
            if (scriptable3 instanceof NativeArray) {
                long length = ((NativeArray) scriptable3).getLength();
                for (long j = 0; j < length; j++) {
                    if (j > 2147483647L) {
                        String string = Long.toString(j);
                        Object objWalk = walk(context, scriptable, callable, scriptable3, string);
                        if (objWalk == Undefined.instance) {
                            scriptable3.delete(string);
                        } else {
                            scriptable3.put(string, scriptable3, objWalk);
                        }
                    } else {
                        int i = (int) j;
                        Object objWalk2 = walk(context, scriptable, callable, scriptable3, Integer.valueOf(i));
                        if (objWalk2 == Undefined.instance) {
                            scriptable3.delete(i);
                        } else {
                            scriptable3.put(i, scriptable3, objWalk2);
                        }
                    }
                }
            } else {
                for (Object obj3 : scriptable3.getIds()) {
                    Object objWalk3 = walk(context, scriptable, callable, scriptable3, obj3);
                    if (objWalk3 == Undefined.instance) {
                        if (obj3 instanceof Number) {
                            scriptable3.delete(((Number) obj3).intValue());
                        } else {
                            scriptable3.delete((String) obj3);
                        }
                    } else if (obj3 instanceof Number) {
                        scriptable3.put(((Number) obj3).intValue(), scriptable3, objWalk3);
                    } else {
                        scriptable3.put((String) obj3, scriptable3, objWalk3);
                    }
                }
            }
        }
        return callable.call(context, scriptable, scriptable2, new Object[]{obj, obj2});
    }

    private static String repeat(char c, int i) {
        char[] cArr = new char[i];
        Arrays.fill(cArr, c);
        return new String(cArr);
    }

    private static class StringifyState {
        Context cx;
        String gap;
        String indent;
        List<Object> propertyList;
        Callable replacer;
        Scriptable scope;
        Stack<Scriptable> stack = new Stack<>();

        StringifyState(Context context, Scriptable scriptable, String str, String str2, Callable callable, List<Object> list) {
            this.cx = context;
            this.scope = scriptable;
            this.indent = str;
            this.gap = str2;
            this.replacer = callable;
            this.propertyList = list;
        }
    }

    public static Object stringify(Context context, Scriptable scriptable, Object obj, Object obj2, Object obj3) {
        Callable callable;
        LinkedList linkedList;
        String str;
        String strSubstring;
        if (obj2 instanceof Callable) {
            callable = (Callable) obj2;
            linkedList = null;
        } else if (obj2 instanceof NativeArray) {
            LinkedList linkedList2 = new LinkedList();
            NativeArray nativeArray = (NativeArray) obj2;
            Iterator<Integer> it = nativeArray.getIndexIds().iterator();
            while (it.hasNext()) {
                Object obj4 = nativeArray.get(it.next().intValue(), nativeArray);
                if ((obj4 instanceof String) || (obj4 instanceof Number)) {
                    linkedList2.add(obj4);
                } else if ((obj4 instanceof NativeString) || (obj4 instanceof NativeNumber)) {
                    linkedList2.add(ScriptRuntime.toString(obj4));
                }
            }
            linkedList = linkedList2;
            callable = null;
        } else {
            callable = null;
            linkedList = null;
        }
        if (obj3 instanceof NativeNumber) {
            obj3 = Double.valueOf(ScriptRuntime.toNumber(obj3));
        } else if (obj3 instanceof NativeString) {
            obj3 = ScriptRuntime.toString(obj3);
        }
        if (obj3 instanceof Number) {
            int iMin = Math.min(10, (int) ScriptRuntime.toInteger(obj3));
            strSubstring = iMin > 0 ? repeat(StringUtil.SPACE, iMin) : "";
        } else {
            if (obj3 instanceof String) {
                String str2 = (String) obj3;
                if (str2.length() > 10) {
                    strSubstring = str2.substring(0, 10);
                } else {
                    str = str2;
                }
            } else {
                str = "";
            }
            StringifyState stringifyState = new StringifyState(context, scriptable, "", str, callable, linkedList);
            NativeObject nativeObject = new NativeObject();
            nativeObject.setParentScope(scriptable);
            nativeObject.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
            nativeObject.defineProperty("", obj, 0);
            return str("", nativeObject, stringifyState);
        }
        str = strSubstring;
        StringifyState stringifyState2 = new StringifyState(context, scriptable, "", str, callable, linkedList);
        NativeObject nativeObject2 = new NativeObject();
        nativeObject2.setParentScope(scriptable);
        nativeObject2.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        nativeObject2.defineProperty("", obj, 0);
        return str("", nativeObject2, stringifyState2);
    }

    private static Object str(Object obj, Scriptable scriptable, StringifyState stringifyState) {
        Object property;
        if (obj instanceof String) {
            property = getProperty(scriptable, (String) obj);
        } else {
            property = getProperty(scriptable, ((Number) obj).intValue());
        }
        if (property instanceof Scriptable) {
            Scriptable scriptable2 = (Scriptable) property;
            if (hasProperty(scriptable2, "toJSON") && (getProperty(scriptable2, "toJSON") instanceof Callable)) {
                property = callMethod(stringifyState.cx, scriptable2, "toJSON", new Object[]{obj});
            }
        }
        if (stringifyState.replacer != null) {
            property = stringifyState.replacer.call(stringifyState.cx, stringifyState.scope, scriptable, new Object[]{obj, property});
        }
        if (property instanceof NativeNumber) {
            property = Double.valueOf(ScriptRuntime.toNumber(property));
        } else if (property instanceof NativeString) {
            property = ScriptRuntime.toString(property);
        } else if (property instanceof NativeBoolean) {
            property = ((NativeBoolean) property).getDefaultValue(ScriptRuntime.BooleanClass);
        }
        if (property == null) {
            return "null";
        }
        if (property.equals(Boolean.TRUE)) {
            return "true";
        }
        if (property.equals(Boolean.FALSE)) {
            return "false";
        }
        if (property instanceof CharSequence) {
            return quote(property.toString());
        }
        if (property instanceof Number) {
            double dDoubleValue = ((Number) property).doubleValue();
            if (Double.isNaN(dDoubleValue) || dDoubleValue == Double.POSITIVE_INFINITY || dDoubleValue == Double.NEGATIVE_INFINITY) {
                return "null";
            }
            return ScriptRuntime.toString(property);
        }
        if ((property instanceof Scriptable) && !(property instanceof Callable)) {
            if (property instanceof NativeArray) {
                return ja((NativeArray) property, stringifyState);
            }
            return jo((Scriptable) property, stringifyState);
        }
        return Undefined.instance;
    }

    private static String join(Collection<Object> collection, String str) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        Iterator<Object> it = collection.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(it.next().toString());
        while (it.hasNext()) {
            sb.append(str).append(it.next());
        }
        return sb.toString();
    }

    private static String jo(Scriptable scriptable, StringifyState stringifyState) {
        Object[] ids;
        String str;
        if (stringifyState.stack.search(scriptable) != -1) {
            throw ScriptRuntime.typeError0("msg.cyclic.value");
        }
        stringifyState.stack.push(scriptable);
        String str2 = stringifyState.indent;
        stringifyState.indent += stringifyState.gap;
        if (stringifyState.propertyList != null) {
            ids = stringifyState.propertyList.toArray();
        } else {
            ids = scriptable.getIds();
        }
        LinkedList linkedList = new LinkedList();
        for (Object obj : ids) {
            Object objStr = str(obj, scriptable, stringifyState);
            if (objStr != Undefined.instance) {
                String str3 = quote(obj.toString()) + ":";
                if (stringifyState.gap.length() > 0) {
                    str3 = str3 + " ";
                }
                linkedList.add(str3 + objStr);
            }
        }
        if (linkedList.isEmpty()) {
            str = "{}";
        } else if (stringifyState.gap.length() == 0) {
            str = "{" + join(linkedList, ",") + '}';
        } else {
            str = "{\n" + stringifyState.indent + join(linkedList, ",\n" + stringifyState.indent) + '\n' + str2 + '}';
        }
        stringifyState.stack.pop();
        stringifyState.indent = str2;
        return str;
    }

    private static String ja(NativeArray nativeArray, StringifyState stringifyState) {
        String str;
        Object objStr;
        if (stringifyState.stack.search(nativeArray) != -1) {
            throw ScriptRuntime.typeError0("msg.cyclic.value");
        }
        stringifyState.stack.push(nativeArray);
        String str2 = stringifyState.indent;
        stringifyState.indent += stringifyState.gap;
        LinkedList linkedList = new LinkedList();
        long length = nativeArray.getLength();
        for (long j = 0; j < length; j++) {
            if (j > 2147483647L) {
                objStr = str(Long.toString(j), nativeArray, stringifyState);
            } else {
                objStr = str(Integer.valueOf((int) j), nativeArray, stringifyState);
            }
            if (objStr == Undefined.instance) {
                linkedList.add("null");
            } else {
                linkedList.add(objStr);
            }
        }
        if (linkedList.isEmpty()) {
            str = HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        } else if (stringifyState.gap.length() == 0) {
            str = "[" + join(linkedList, ",") + ']';
        } else {
            str = "[\n" + stringifyState.indent + join(linkedList, ",\n" + stringifyState.indent) + '\n' + str2 + ']';
        }
        stringifyState.stack.pop();
        stringifyState.indent = str2;
        return str;
    }

    private static String quote(String str) {
        StringBuilder sb = new StringBuilder(str.length() + 2);
        sb.append('\"');
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\f') {
                sb.append("\\f");
            } else if (cCharAt == '\r') {
                sb.append("\\r");
            } else if (cCharAt == '\"') {
                sb.append("\\\"");
            } else if (cCharAt == '\\') {
                sb.append("\\\\");
            } else {
                switch (cCharAt) {
                    case '\b':
                        sb.append("\\b");
                        break;
                    case '\t':
                        sb.append("\\t");
                        break;
                    case '\n':
                        sb.append("\\n");
                        break;
                    default:
                        if (cCharAt < ' ') {
                            sb.append("\\u");
                            sb.append(String.format("%04x", Integer.valueOf(cCharAt)));
                        } else {
                            sb.append(cCharAt);
                        }
                        break;
                }
            }
        }
        sb.append('\"');
        return sb.toString();
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int i;
        int length = str.length();
        if (length == 5) {
            str2 = "parse";
            i = 2;
        } else if (length == 8) {
            str2 = "toSource";
            i = 1;
        } else if (length != 9) {
            str2 = null;
            i = 0;
        } else {
            str2 = "stringify";
            i = 3;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }
}
