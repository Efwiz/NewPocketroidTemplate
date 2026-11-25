package org.mozilla.javascript;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.util.internal.StringUtil;
import java.text.Collator;
import java.text.Normalizer;
import java.util.Locale;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.mozilla.javascript.regexp.NativeRegExp;

/* JADX INFO: loaded from: classes4.dex */
final class NativeString extends IdScriptableObject {
    private static final int ConstructorId_charAt = -5;
    private static final int ConstructorId_charCodeAt = -6;
    private static final int ConstructorId_concat = -14;
    private static final int ConstructorId_equalsIgnoreCase = -30;
    private static final int ConstructorId_fromCharCode = -1;
    private static final int ConstructorId_fromCodePoint = -2;
    private static final int ConstructorId_indexOf = -7;
    private static final int ConstructorId_lastIndexOf = -8;
    private static final int ConstructorId_localeCompare = -34;
    private static final int ConstructorId_match = -31;
    private static final int ConstructorId_replace = -33;
    private static final int ConstructorId_search = -32;
    private static final int ConstructorId_slice = -15;
    private static final int ConstructorId_split = -9;
    private static final int ConstructorId_substr = -13;
    private static final int ConstructorId_substring = -10;
    private static final int ConstructorId_toLocaleLowerCase = -35;
    private static final int ConstructorId_toLowerCase = -11;
    private static final int ConstructorId_toUpperCase = -12;
    private static final int Id_anchor = 28;
    private static final int Id_big = 21;
    private static final int Id_blink = 22;
    private static final int Id_bold = 16;
    private static final int Id_charAt = 5;
    private static final int Id_charCodeAt = 6;
    private static final int Id_codePointAt = 45;
    private static final int Id_concat = 14;
    private static final int Id_constructor = 1;
    private static final int Id_endsWith = 42;
    private static final int Id_equals = 29;
    private static final int Id_equalsIgnoreCase = 30;
    private static final int Id_fixed = 18;
    private static final int Id_fontcolor = 26;
    private static final int Id_fontsize = 25;
    private static final int Id_includes = 40;
    private static final int Id_indexOf = 7;
    private static final int Id_italics = 17;
    private static final int Id_lastIndexOf = 8;
    private static final int Id_length = 1;
    private static final int Id_link = 27;
    private static final int Id_localeCompare = 34;
    private static final int Id_match = 31;
    private static final int Id_normalize = 43;
    private static final int Id_padEnd = 47;
    private static final int Id_padStart = 46;
    private static final int Id_repeat = 44;
    private static final int Id_replace = 33;
    private static final int Id_search = 32;
    private static final int Id_slice = 15;
    private static final int Id_small = 20;
    private static final int Id_split = 9;
    private static final int Id_startsWith = 41;
    private static final int Id_strike = 19;
    private static final int Id_sub = 24;
    private static final int Id_substr = 13;
    private static final int Id_substring = 10;
    private static final int Id_sup = 23;
    private static final int Id_toLocaleLowerCase = 35;
    private static final int Id_toLocaleUpperCase = 36;
    private static final int Id_toLowerCase = 11;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int Id_toUpperCase = 12;
    private static final int Id_trim = 37;
    private static final int Id_trimEnd = 50;
    private static final int Id_trimLeft = 38;
    private static final int Id_trimRight = 39;
    private static final int Id_trimStart = 49;
    private static final int Id_valueOf = 4;
    private static final int MAX_INSTANCE_ID = 1;
    private static final int MAX_PROTOTYPE_ID = 50;
    private static final Object STRING_TAG = CommonClassNames.JAVA_LANG_STRING_SHORT;
    private static final int SymbolId_iterator = 48;
    private static final long serialVersionUID = 920268368584188687L;
    private CharSequence string;

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return 1;
    }

    static void init(Scriptable scriptable, boolean z) {
        new NativeString("").exportAsJSClass(50, scriptable, z);
    }

    NativeString(CharSequence charSequence) {
        this.string = charSequence;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return CommonClassNames.JAVA_LANG_STRING_SHORT;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findInstanceIdInfo(String str) {
        if (str.equals("length")) {
            return instanceIdInfo(7, 1);
        }
        return super.findInstanceIdInfo(str);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i) {
        if (i == 1) {
            return "length";
        }
        return super.getInstanceIdName(i);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i) {
        if (i == 1) {
            return ScriptRuntime.wrapInt(this.string.length());
        }
        return super.getInstanceIdValue(i);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        Object obj = STRING_TAG;
        addIdFunctionProperty(idFunctionObject, obj, -1, "fromCharCode", 1);
        addIdFunctionProperty(idFunctionObject, obj, -2, "fromCodePoint", 1);
        addIdFunctionProperty(idFunctionObject, obj, -5, "charAt", 2);
        addIdFunctionProperty(idFunctionObject, obj, -6, "charCodeAt", 2);
        addIdFunctionProperty(idFunctionObject, obj, -7, "indexOf", 2);
        addIdFunctionProperty(idFunctionObject, obj, -8, "lastIndexOf", 2);
        addIdFunctionProperty(idFunctionObject, obj, -9, "split", 3);
        addIdFunctionProperty(idFunctionObject, obj, -10, "substring", 3);
        addIdFunctionProperty(idFunctionObject, obj, -11, "toLowerCase", 1);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_toUpperCase, "toUpperCase", 1);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_substr, "substr", 3);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_concat, "concat", 2);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_slice, "slice", 3);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_equalsIgnoreCase, "equalsIgnoreCase", 2);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_match, "match", 2);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_search, FirebaseAnalytics.Event.SEARCH, 2);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_replace, SchemaSymbols.ATTVAL_REPLACE, 2);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_localeCompare, "localeCompare", 2);
        addIdFunctionProperty(idFunctionObject, obj, ConstructorId_toLocaleLowerCase, "toLocaleLowerCase", 1);
        super.fillConstructorProperties(idFunctionObject);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i) {
        String str;
        String str2;
        String str3;
        int i2;
        String str4;
        if (i == 48) {
            initPrototypeMethod(STRING_TAG, i, SymbolKey.ITERATOR, "[Symbol.iterator]", 0);
            return;
        }
        switch (i) {
            case 1:
                str = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 2:
                str2 = "toString";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 3:
                str2 = "toSource";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 4:
                str2 = "valueOf";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 5:
                str = "charAt";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 6:
                str = "charCodeAt";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 7:
                str = "indexOf";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 8:
                str = "lastIndexOf";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 9:
                str3 = "split";
                i2 = 2;
                str4 = str3;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 10:
                str3 = "substring";
                i2 = 2;
                str4 = str3;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 11:
                str2 = "toLowerCase";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 12:
                str2 = "toUpperCase";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 13:
                str3 = "substr";
                i2 = 2;
                str4 = str3;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 14:
                str = "concat";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 15:
                str3 = "slice";
                i2 = 2;
                str4 = str3;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 16:
                str2 = "bold";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 17:
                str2 = "italics";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 18:
                str2 = "fixed";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 19:
                str2 = "strike";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 20:
                str2 = "small";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 21:
                str2 = "big";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 22:
                str2 = "blink";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 23:
                str2 = "sup";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 24:
                str2 = "sub";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 25:
                str2 = "fontsize";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 26:
                str2 = "fontcolor";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 27:
                str2 = "link";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 28:
                str2 = "anchor";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 29:
                str = Namer.EQUALS_METHOD_NAME;
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 30:
                str = "equalsIgnoreCase";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 31:
                str = "match";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 32:
                str = FirebaseAnalytics.Event.SEARCH;
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 33:
                str3 = SchemaSymbols.ATTVAL_REPLACE;
                i2 = 2;
                str4 = str3;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 34:
                str = "localeCompare";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 35:
                str2 = "toLocaleLowerCase";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 36:
                str2 = "toLocaleUpperCase";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 37:
                str2 = "trim";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 38:
                str2 = "trimLeft";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 39:
                str2 = "trimRight";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 40:
                str = "includes";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 41:
                str = "startsWith";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 42:
                str = "endsWith";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 43:
                str2 = "normalize";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 44:
                str = "repeat";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 45:
                str = "codePointAt";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 46:
                str = "padStart";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 47:
                str = "padEnd";
                str4 = str;
                i2 = 1;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 48:
            default:
                throw new IllegalArgumentException(String.valueOf(i));
            case 49:
                str2 = "trimStart";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
            case 50:
                str2 = "trimEnd";
                str4 = str2;
                i2 = 0;
                initPrototypeMethod(STRING_TAG, i, str4, (String) null, i2);
                return;
        }
    }

    /* JADX WARN: Switch 'out' block B:7:0x001b for B:12:0x0028 already processed. Defaulting to fallback option. */
    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        boolean zEqualsIgnoreCase;
        Normalizer.Form form;
        if (!idFunctionObject.hasTag(STRING_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        Scriptable object = scriptable2;
        Object[] objArr2 = objArr;
        while (true) {
            CharSequence charSequence = "";
            int i = 0;
            if (iMethodId == -2) {
                int length = objArr2.length;
                if (length < 1) {
                    return "";
                }
                int[] iArr = new int[length];
                for (int i2 = 0; i2 != length; i2++) {
                    Object obj = objArr2[i2];
                    int int32 = ScriptRuntime.toInt32(obj);
                    if (!ScriptRuntime.eqNumber(ScriptRuntime.toNumber(obj), Integer.valueOf(int32)) || !Character.isValidCodePoint(int32)) {
                        throw ScriptRuntime.rangeError("Invalid code point " + ScriptRuntime.toString(obj));
                    }
                    iArr[i2] = int32;
                }
                return new String(iArr, 0, length);
            }
            if (iMethodId == -1) {
                int length2 = objArr2.length;
                if (length2 < 1) {
                    return "";
                }
                char[] cArr = new char[length2];
                while (i != length2) {
                    cArr[i] = ScriptRuntime.toUint16(objArr2[i]);
                    i++;
                }
                return new String(cArr);
            }
            switch (iMethodId) {
                case ConstructorId_toLocaleLowerCase /* -35 */:
                case ConstructorId_localeCompare /* -34 */:
                case ConstructorId_replace /* -33 */:
                case ConstructorId_search /* -32 */:
                case ConstructorId_match /* -31 */:
                case ConstructorId_equalsIgnoreCase /* -30 */:
                    break;
                default:
                    switch (iMethodId) {
                        case ConstructorId_slice /* -15 */:
                        case ConstructorId_concat /* -14 */:
                        case ConstructorId_substr /* -13 */:
                        case ConstructorId_toUpperCase /* -12 */:
                        case -11:
                        case -10:
                        case -9:
                        case -8:
                        case -7:
                        case -6:
                        case -5:
                            break;
                        default:
                            int i3 = 3;
                            switch (iMethodId) {
                                case 1:
                                    if (objArr2.length != 0) {
                                        if (ScriptRuntime.isSymbol(objArr2[0]) && object != null) {
                                            charSequence = objArr2[0].toString();
                                        } else {
                                            charSequence = ScriptRuntime.toCharSequence(objArr2[0]);
                                        }
                                    }
                                    if (object == null) {
                                        return new NativeString(charSequence);
                                    }
                                    return charSequence instanceof String ? charSequence : charSequence.toString();
                                case 2:
                                case 4:
                                    CharSequence charSequence2 = realThis(object, idFunctionObject).string;
                                    return charSequence2 instanceof String ? charSequence2 : charSequence2.toString();
                                case 3:
                                    return "(new String(\"" + ScriptRuntime.escapeString(realThis(object, idFunctionObject).string.toString()) + "\"))";
                                case 5:
                                case 6:
                                    CharSequence charSequence3 = ScriptRuntime.toCharSequence(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject));
                                    double integer = ScriptRuntime.toInteger(objArr2, 0);
                                    if (integer < 0.0d || integer >= charSequence3.length()) {
                                        if (iMethodId == 5) {
                                            return "";
                                        }
                                        return ScriptRuntime.NaNobj;
                                    }
                                    char cCharAt = charSequence3.charAt((int) integer);
                                    if (iMethodId == 5) {
                                        return String.valueOf(cCharAt);
                                    }
                                    return ScriptRuntime.wrapInt(cCharAt);
                                case 7:
                                    return ScriptRuntime.wrapInt(js_indexOf(7, ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)), objArr2));
                                case 8:
                                    return ScriptRuntime.wrapInt(js_lastIndexOf(ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)), objArr2));
                                case 9:
                                    return ScriptRuntime.checkRegExpProxy(context).js_split(context, scriptable, ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)), objArr2);
                                case 10:
                                    return js_substring(context, ScriptRuntime.toCharSequence(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)), objArr2);
                                case 11:
                                    return ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)).toLowerCase(Locale.ROOT);
                                case 12:
                                    return ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)).toUpperCase(Locale.ROOT);
                                case 13:
                                    return js_substr(ScriptRuntime.toCharSequence(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)), objArr2);
                                case 14:
                                    return js_concat(ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)), objArr2);
                                case 15:
                                    return js_slice(ScriptRuntime.toCharSequence(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)), objArr2);
                                case 16:
                                    return tagify(object, JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, null, null);
                                case 17:
                                    return tagify(object, ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, null, null);
                                case 18:
                                    return tagify(object, "tt", null, null);
                                case 19:
                                    return tagify(object, "strike", null, null);
                                case 20:
                                    return tagify(object, "small", null, null);
                                case 21:
                                    return tagify(object, "big", null, null);
                                case 22:
                                    return tagify(object, "blink", null, null);
                                case 23:
                                    return tagify(object, "sup", null, null);
                                case 24:
                                    return tagify(object, "sub", null, null);
                                case 25:
                                    return tagify(object, "font", "size", objArr2);
                                case 26:
                                    return tagify(object, "font", TypedValues.Custom.S_COLOR, objArr2);
                                case 27:
                                    return tagify(object, "a", "href", objArr2);
                                case 28:
                                    return tagify(object, "a", "name", objArr2);
                                case 29:
                                case 30:
                                    String string = ScriptRuntime.toString(object);
                                    String string2 = ScriptRuntime.toString(objArr2, 0);
                                    if (iMethodId == 29) {
                                        zEqualsIgnoreCase = string.equals(string2);
                                    } else {
                                        zEqualsIgnoreCase = string.equalsIgnoreCase(string2);
                                    }
                                    return ScriptRuntime.wrapBoolean(zEqualsIgnoreCase);
                                case 31:
                                case 32:
                                case 33:
                                    if (iMethodId == 31) {
                                        i3 = 1;
                                    } else if (iMethodId != 32) {
                                        i3 = 2;
                                    }
                                    ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject);
                                    return ScriptRuntime.checkRegExpProxy(context).action(context, scriptable, object, objArr2, i3);
                                case 34:
                                    String string3 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject));
                                    Collator collator = Collator.getInstance(context.getLocale());
                                    collator.setStrength(3);
                                    collator.setDecomposition(1);
                                    return ScriptRuntime.wrapNumber(collator.compare(string3, ScriptRuntime.toString(objArr2, 0)));
                                case 35:
                                    return ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)).toLowerCase(context.getLocale());
                                case 36:
                                    return ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)).toUpperCase(context.getLocale());
                                case 37:
                                    String string4 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject));
                                    char[] charArray = string4.toCharArray();
                                    while (i < charArray.length && ScriptRuntime.isJSWhitespaceOrLineTerminator(charArray[i])) {
                                        i++;
                                    }
                                    int length3 = charArray.length;
                                    while (length3 > i && ScriptRuntime.isJSWhitespaceOrLineTerminator(charArray[length3 - 1])) {
                                        length3--;
                                    }
                                    return string4.substring(i, length3);
                                case 38:
                                case 49:
                                    String string5 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject));
                                    char[] charArray2 = string5.toCharArray();
                                    while (i < charArray2.length && ScriptRuntime.isJSWhitespaceOrLineTerminator(charArray2[i])) {
                                        i++;
                                    }
                                    return string5.substring(i, charArray2.length);
                                case 39:
                                case 50:
                                    String string6 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject));
                                    char[] charArray3 = string6.toCharArray();
                                    int length4 = charArray3.length;
                                    while (length4 > 0 && ScriptRuntime.isJSWhitespaceOrLineTerminator(charArray3[length4 - 1])) {
                                        length4--;
                                    }
                                    return string6.substring(0, length4);
                                case 40:
                                case 41:
                                case 42:
                                    String string7 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject));
                                    if (objArr2.length > 0 && (objArr2[0] instanceof NativeRegExp)) {
                                        throw ScriptRuntime.typeError2("msg.first.arg.not.regexp", String.class.getSimpleName(), idFunctionObject.getFunctionName());
                                    }
                                    int iJs_indexOf = js_indexOf(iMethodId, string7, objArr2);
                                    if (iMethodId == 40) {
                                        return Boolean.valueOf(iJs_indexOf != -1);
                                    }
                                    if (iMethodId == 41) {
                                        return Boolean.valueOf(iJs_indexOf == 0);
                                    }
                                    if (iMethodId == 42) {
                                        return Boolean.valueOf(iJs_indexOf != -1);
                                    }
                                    break;
                                case 43:
                                    if (objArr2.length == 0 || Undefined.isUndefined(objArr2[0])) {
                                        return Normalizer.normalize(ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)), Normalizer.Form.NFC);
                                    }
                                    String string8 = ScriptRuntime.toString(objArr2, 0);
                                    if (Normalizer.Form.NFD.name().equals(string8)) {
                                        form = Normalizer.Form.NFD;
                                    } else if (Normalizer.Form.NFKC.name().equals(string8)) {
                                        form = Normalizer.Form.NFKC;
                                    } else if (Normalizer.Form.NFKD.name().equals(string8)) {
                                        form = Normalizer.Form.NFKD;
                                    } else {
                                        if (!Normalizer.Form.NFC.name().equals(string8)) {
                                            throw ScriptRuntime.rangeError("The normalization form should be one of 'NFC', 'NFD', 'NFKC', 'NFKD'.");
                                        }
                                        form = Normalizer.Form.NFC;
                                    }
                                    return Normalizer.normalize(ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject)), form);
                                case 44:
                                    return js_repeat(context, object, idFunctionObject, objArr2);
                                case 45:
                                    String string9 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject));
                                    double integer2 = ScriptRuntime.toInteger(objArr2, 0);
                                    if (integer2 < 0.0d || integer2 >= string9.length()) {
                                        return Undefined.instance;
                                    }
                                    return Integer.valueOf(string9.codePointAt((int) integer2));
                                case 46:
                                case 47:
                                    break;
                                case 48:
                                    return new NativeStringIterator(scriptable, ScriptRuntimeES6.requireObjectCoercible(context, object, idFunctionObject));
                                default:
                                    throw new IllegalArgumentException("String.prototype has no method: " + idFunctionObject.getFunctionName());
                            }
                            return js_pad(context, object, idFunctionObject, objArr2, iMethodId == 46);
                    }
                    break;
            }
            if (objArr2.length > 0) {
                object = ScriptRuntime.toObject(context, scriptable, ScriptRuntime.toCharSequence(objArr2[0]));
                int length5 = objArr2.length - 1;
                Object[] objArr3 = new Object[length5];
                while (i < length5) {
                    int i4 = i + 1;
                    objArr3[i] = objArr2[i4];
                    i = i4;
                }
                objArr2 = objArr3;
            } else {
                object = ScriptRuntime.toObject(context, scriptable, ScriptRuntime.toCharSequence(object));
            }
            iMethodId = -iMethodId;
        }
    }

    private static NativeString realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (!(scriptable instanceof NativeString)) {
            throw incompatibleCallError(idFunctionObject);
        }
        return (NativeString) scriptable;
    }

    private static String tagify(Scriptable scriptable, String str, String str2, Object[] objArr) {
        String string = ScriptRuntime.toString(scriptable);
        StringBuilder sb = new StringBuilder("<");
        sb.append(str);
        if (str2 != null) {
            sb.append(StringUtil.SPACE).append(str2).append("=\"").append(ScriptRuntime.toString(objArr, 0)).append('\"');
        }
        sb.append('>').append(string).append("</").append(str).append('>');
        return sb.toString();
    }

    public CharSequence toCharSequence() {
        return this.string;
    }

    public String toString() {
        CharSequence charSequence = this.string;
        return charSequence instanceof String ? (String) charSequence : charSequence.toString();
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object get(int i, Scriptable scriptable) {
        if (i >= 0 && i < this.string.length()) {
            return String.valueOf(this.string.charAt(i));
        }
        return super.get(i, scriptable);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public void put(int i, Scriptable scriptable, Object obj) {
        if (i < 0 || i >= this.string.length()) {
            super.put(i, scriptable, obj);
        }
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public boolean has(int i, Scriptable scriptable) {
        if (i < 0 || i >= this.string.length()) {
            return super.has(i, scriptable);
        }
        return true;
    }

    @Override // org.mozilla.javascript.ScriptableObject
    public int getAttributes(int i) {
        if (i < 0 || i >= this.string.length()) {
            return super.getAttributes(i);
        }
        return Context.getContext().getLanguageVersion() < 200 ? 7 : 5;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject
    protected Object[] getIds(boolean z, boolean z2) {
        Context currentContext = Context.getCurrentContext();
        if (currentContext != null && currentContext.getLanguageVersion() >= 200) {
            Object[] ids = super.getIds(z, z2);
            Object[] objArr = new Object[ids.length + this.string.length()];
            int i = 0;
            while (i < this.string.length()) {
                objArr[i] = Integer.valueOf(i);
                i++;
            }
            System.arraycopy(ids, 0, objArr, i, ids.length);
            return objArr;
        }
        return super.getIds(z, z2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject
    protected ScriptableObject getOwnPropertyDescriptor(Context context, Object obj) {
        if (!(obj instanceof Symbol) && context != null && context.getLanguageVersion() >= 200) {
            ScriptRuntime.StringIdOrIndex stringIdOrIndex = ScriptRuntime.toStringIdOrIndex(context, obj);
            if (stringIdOrIndex.stringId == null && stringIdOrIndex.index >= 0 && stringIdOrIndex.index < this.string.length()) {
                return defaultIndexPropertyDescriptor(String.valueOf(this.string.charAt(stringIdOrIndex.index)));
            }
        }
        return super.getOwnPropertyDescriptor(context, obj);
    }

    private ScriptableObject defaultIndexPropertyDescriptor(Object obj) {
        Scriptable parentScope = getParentScope();
        if (parentScope == null) {
            parentScope = this;
        }
        NativeObject nativeObject = new NativeObject();
        ScriptRuntime.setBuiltinProtoAndParent(nativeObject, parentScope, TopLevel.Builtins.Object);
        nativeObject.defineProperty("value", obj, 0);
        nativeObject.defineProperty("writable", Boolean.FALSE, 0);
        nativeObject.defineProperty("enumerable", Boolean.TRUE, 0);
        nativeObject.defineProperty("configurable", Boolean.FALSE, 0);
        return nativeObject;
    }

    private static int js_indexOf(int i, String str, Object[] objArr) {
        int length;
        String string = ScriptRuntime.toString(objArr, 0);
        double integer = ScriptRuntime.toInteger(objArr, 1);
        if (i != 41 && i != 42 && string.length() == 0) {
            return integer > ((double) str.length()) ? str.length() : (int) integer;
        }
        if (i != 41 && i != 42 && integer > str.length()) {
            return -1;
        }
        if (integer < 0.0d) {
            integer = 0.0d;
        } else {
            if (integer > str.length()) {
                length = str.length();
            } else if (i == 42 && (Double.isNaN(integer) || integer > str.length())) {
                length = str.length();
            }
            integer = length;
        }
        if (42 != i) {
            if (i == 41) {
                return str.startsWith(string, (int) integer) ? 0 : -1;
            }
            return str.indexOf(string, (int) integer);
        }
        if (objArr.length == 0 || objArr.length == 1 || (objArr.length == 2 && objArr[1] == Undefined.instance)) {
            integer = str.length();
        }
        return str.substring(0, (int) integer).endsWith(string) ? 0 : -1;
    }

    private static int js_lastIndexOf(String str, Object[] objArr) {
        String string = ScriptRuntime.toString(objArr, 0);
        double number = ScriptRuntime.toNumber(objArr, 1);
        if (Double.isNaN(number) || number > str.length()) {
            number = str.length();
        } else if (number < 0.0d) {
            number = 0.0d;
        }
        return str.lastIndexOf(string, (int) number);
    }

    private static CharSequence js_substring(Context context, CharSequence charSequence, Object[] objArr) {
        int length = charSequence.length();
        double integer = ScriptRuntime.toInteger(objArr, 0);
        double d = 0.0d;
        if (integer < 0.0d) {
            integer = 0.0d;
        } else {
            double d2 = length;
            if (integer > d2) {
                integer = d2;
            }
        }
        if (objArr.length <= 1 || objArr[1] == Undefined.instance) {
            d = length;
        } else {
            double integer2 = ScriptRuntime.toInteger(objArr[1]);
            if (integer2 >= 0.0d) {
                d = length;
                if (integer2 <= d) {
                    d = integer2;
                }
            }
            if (d < integer) {
                if (context.getLanguageVersion() != 120) {
                    double d3 = integer;
                    integer = d;
                    d = d3;
                } else {
                    d = integer;
                }
            }
        }
        return charSequence.subSequence((int) integer, (int) d);
    }

    int getLength() {
        return this.string.length();
    }

    private static CharSequence js_substr(CharSequence charSequence, Object[] objArr) {
        if (objArr.length < 1) {
            return charSequence;
        }
        double integer = ScriptRuntime.toInteger(objArr[0]);
        int length = charSequence.length();
        if (integer < 0.0d) {
            integer += (double) length;
            if (integer < 0.0d) {
                integer = 0.0d;
            }
        } else {
            double d = length;
            if (integer > d) {
                integer = d;
            }
        }
        double d2 = length;
        if (objArr.length > 1) {
            Object obj = objArr[1];
            if (!Undefined.isUndefined(obj)) {
                double integer2 = ScriptRuntime.toInteger(obj);
                double d3 = (integer2 >= 0.0d ? integer2 : 0.0d) + integer;
                if (d3 <= d2) {
                    d2 = d3;
                }
            }
        }
        return charSequence.subSequence((int) integer, (int) d2);
    }

    private static String js_concat(String str, Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return str;
        }
        if (length == 1) {
            return str.concat(ScriptRuntime.toString(objArr[0]));
        }
        int length2 = str.length();
        String[] strArr = new String[length];
        for (int i = 0; i != length; i++) {
            String string = ScriptRuntime.toString(objArr[i]);
            strArr[i] = string;
            length2 += string.length();
        }
        StringBuilder sb = new StringBuilder(length2);
        sb.append(str);
        for (int i2 = 0; i2 != length; i2++) {
            sb.append(strArr[i2]);
        }
        return sb.toString();
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0047 A[PHI: r6
      0x0047: PHI (r6v5 double) = (r6v4 double), (r6v6 double) binds: [B:25:0x0044, B:22:0x003e] A[DONT_GENERATE, DONT_INLINE]] */
    private static CharSequence js_slice(CharSequence charSequence, Object[] objArr) {
        double d = 0.0d;
        double integer = objArr.length < 1 ? 0.0d : ScriptRuntime.toInteger(objArr[0]);
        int length = charSequence.length();
        if (integer < 0.0d) {
            integer += (double) length;
            if (integer < 0.0d) {
                integer = 0.0d;
            }
        } else {
            double d2 = length;
            if (integer > d2) {
                integer = d2;
            }
        }
        if (objArr.length < 2 || objArr[1] == Undefined.instance) {
            d = length;
        } else {
            double integer2 = ScriptRuntime.toInteger(objArr[1]);
            if (integer2 < 0.0d) {
                integer2 += (double) length;
                if (integer2 >= 0.0d) {
                    d = integer2;
                }
            } else {
                d = length;
                if (integer2 <= d) {
                    d = integer2;
                }
            }
            if (d < integer) {
                d = integer;
            }
        }
        return charSequence.subSequence((int) integer, (int) d);
    }

    private static String js_repeat(Context context, Scriptable scriptable, IdFunctionObject idFunctionObject, Object[] objArr) {
        String string = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, scriptable, idFunctionObject));
        double integer = ScriptRuntime.toInteger(objArr, 0);
        if (integer < 0.0d || integer == Double.POSITIVE_INFINITY) {
            throw ScriptRuntime.rangeError("Invalid count value");
        }
        if (integer == 0.0d || string.length() == 0) {
            return "";
        }
        long length = ((long) string.length()) * ((long) integer);
        if (integer > 2.147483647E9d || length > 2147483647L) {
            throw ScriptRuntime.rangeError("Invalid size or count value");
        }
        StringBuilder sb = new StringBuilder((int) length);
        sb.append(string);
        int i = (int) integer;
        int i2 = 1;
        while (i2 <= i / 2) {
            sb.append((CharSequence) sb);
            i2 *= 2;
        }
        if (i2 < i) {
            sb.append(sb.substring(0, string.length() * (i - i2)));
        }
        return sb.toString();
    }

    private static String js_pad(Context context, Scriptable scriptable, IdFunctionObject idFunctionObject, Object[] objArr, boolean z) {
        String string;
        String string2 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, scriptable, idFunctionObject));
        long length = ScriptRuntime.toLength(objArr, 0);
        if (length <= string2.length()) {
            return string2;
        }
        if (objArr.length >= 2 && !Undefined.isUndefined(objArr[1])) {
            string = ScriptRuntime.toString(objArr[1]);
            if (string.length() < 1) {
                return string2;
            }
        } else {
            string = " ";
        }
        int length2 = (int) (length - ((long) string2.length()));
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(string);
        } while (sb.length() < length2);
        sb.setLength(length2);
        if (z) {
            return sb.append(string2).toString();
        }
        return sb.insert(0, string2).toString();
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(Symbol symbol) {
        return SymbolKey.ITERATOR.equals(symbol) ? 48 : 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:148:0x0224  */
    /* JADX WARN: Code duplicated, block: B:150:0x0228 A[ADDED_TO_REGION] */
    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int i = 8;
        switch (str.length()) {
            case 3:
                char cCharAt = str.charAt(2);
                if (cCharAt == 'b') {
                    if (str.charAt(0) == 's' && str.charAt(1) == 'u') {
                        return 24;
                    }
                } else if (cCharAt == 'g') {
                    if (str.charAt(0) == 'b' && str.charAt(1) == 'i') {
                        return 21;
                    }
                } else if (cCharAt == 'p' && str.charAt(0) == 's' && str.charAt(1) == 'u') {
                    return 23;
                }
                str2 = null;
                i = 0;
                if (str2 != null || str2 == str || str2.equals(str)) {
                    return i;
                }
                return 0;
            case 4:
                char cCharAt2 = str.charAt(0);
                if (cCharAt2 == 'b') {
                    str2 = "bold";
                    i = 16;
                } else if (cCharAt2 == 'l') {
                    str2 = "link";
                    i = 27;
                } else if (cCharAt2 == 't') {
                    str2 = "trim";
                    i = 37;
                } else {
                    str2 = null;
                    i = 0;
                }
                if (str2 != null) {
                }
                return i;
            case 5:
                char cCharAt3 = str.charAt(4);
                if (cCharAt3 == 'd') {
                    str2 = "fixed";
                    i = 18;
                } else if (cCharAt3 == 'e') {
                    str2 = "slice";
                    i = 15;
                } else if (cCharAt3 == 'h') {
                    str2 = "match";
                    i = 31;
                } else if (cCharAt3 == 't') {
                    str2 = "split";
                    i = 9;
                } else if (cCharAt3 == 'k') {
                    str2 = "blink";
                    i = 22;
                } else if (cCharAt3 != 'l') {
                    str2 = null;
                    i = 0;
                } else {
                    str2 = "small";
                    i = 20;
                }
                if (str2 != null) {
                }
                return i;
            case 6:
                char cCharAt4 = str.charAt(1);
                if (cCharAt4 == 'a') {
                    str2 = "padEnd";
                    i = 47;
                } else if (cCharAt4 == 'e') {
                    char cCharAt5 = str.charAt(0);
                    if (cCharAt5 == 'r') {
                        str2 = "repeat";
                        i = 44;
                    } else if (cCharAt5 == 's') {
                        str2 = FirebaseAnalytics.Event.SEARCH;
                        i = 32;
                    } else {
                        str2 = null;
                        i = 0;
                    }
                } else if (cCharAt4 == 'h') {
                    str2 = "charAt";
                    i = 5;
                } else if (cCharAt4 == 'q') {
                    str2 = Namer.EQUALS_METHOD_NAME;
                    i = 29;
                } else if (cCharAt4 == 'n') {
                    str2 = "anchor";
                    i = 28;
                } else if (cCharAt4 == 'o') {
                    str2 = "concat";
                    i = 14;
                } else if (cCharAt4 == 't') {
                    str2 = "strike";
                    i = 19;
                } else if (cCharAt4 != 'u') {
                    str2 = null;
                    i = 0;
                } else {
                    str2 = "substr";
                    i = 13;
                }
                if (str2 != null) {
                }
                return i;
            case 7:
                char cCharAt6 = str.charAt(1);
                if (cCharAt6 == 'a') {
                    str2 = "valueOf";
                    i = 4;
                } else if (cCharAt6 == 'e') {
                    str2 = SchemaSymbols.ATTVAL_REPLACE;
                    i = 33;
                } else if (cCharAt6 == 'n') {
                    str2 = "indexOf";
                    i = 7;
                } else if (cCharAt6 == 'r') {
                    str2 = "trimEnd";
                    i = 50;
                } else if (cCharAt6 != 't') {
                    str2 = null;
                    i = 0;
                } else {
                    str2 = "italics";
                    i = 17;
                }
                if (str2 != null) {
                }
                return i;
            case 8:
                char cCharAt7 = str.charAt(6);
                if (cCharAt7 == 'c') {
                    str2 = "toSource";
                    i = 3;
                } else if (cCharAt7 == 'n') {
                    str2 = "toString";
                    i = 2;
                } else if (cCharAt7 == 'r') {
                    str2 = "padStart";
                    i = 46;
                } else if (cCharAt7 == 't') {
                    str2 = "endsWith";
                    i = 42;
                } else if (cCharAt7 == 'z') {
                    str2 = "fontsize";
                    i = 25;
                } else if (cCharAt7 == 'e') {
                    str2 = "includes";
                    i = 40;
                } else if (cCharAt7 != 'f') {
                    str2 = null;
                    i = 0;
                } else {
                    str2 = "trimLeft";
                    i = 38;
                }
                if (str2 != null) {
                }
                return i;
            case 9:
                char cCharAt8 = str.charAt(4);
                if (cCharAt8 == 'R') {
                    str2 = "trimRight";
                    i = 39;
                } else if (cCharAt8 == 'S') {
                    str2 = "trimStart";
                    i = 49;
                } else if (cCharAt8 == 'a') {
                    str2 = "normalize";
                    i = 43;
                } else if (cCharAt8 == 'c') {
                    str2 = "fontcolor";
                    i = 26;
                } else if (cCharAt8 != 't') {
                    str2 = null;
                    i = 0;
                } else {
                    str2 = "substring";
                    i = 10;
                }
                if (str2 != null) {
                }
                return i;
            case 10:
                char cCharAt9 = str.charAt(0);
                if (cCharAt9 == 'c') {
                    str2 = "charCodeAt";
                    i = 6;
                } else if (cCharAt9 == 's') {
                    str2 = "startsWith";
                    i = 41;
                } else {
                    str2 = null;
                    i = 0;
                }
                if (str2 != null) {
                }
                return i;
            case 11:
                char cCharAt10 = str.charAt(2);
                if (cCharAt10 == 'L') {
                    str2 = "toLowerCase";
                    i = 11;
                } else if (cCharAt10 == 'U') {
                    str2 = "toUpperCase";
                    i = 12;
                } else if (cCharAt10 == 'd') {
                    str2 = "codePointAt";
                    i = 45;
                } else if (cCharAt10 == 'n') {
                    str2 = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                    i = 1;
                } else if (cCharAt10 != 's') {
                    str2 = null;
                    i = 0;
                } else {
                    str2 = "lastIndexOf";
                }
                if (str2 != null) {
                }
                return i;
            case 12:
            case 14:
            case 15:
            default:
                str2 = null;
                i = 0;
                if (str2 != null) {
                }
                return i;
            case 13:
                str2 = "localeCompare";
                i = 34;
                if (str2 != null) {
                }
                return i;
            case 16:
                str2 = "equalsIgnoreCase";
                i = 30;
                if (str2 != null) {
                }
                return i;
            case 17:
                char cCharAt11 = str.charAt(8);
                if (cCharAt11 == 'L') {
                    str2 = "toLocaleLowerCase";
                    i = 35;
                } else if (cCharAt11 == 'U') {
                    str2 = "toLocaleUpperCase";
                    i = 36;
                } else {
                    str2 = null;
                    i = 0;
                }
                if (str2 != null) {
                }
                return i;
        }
    }
}
