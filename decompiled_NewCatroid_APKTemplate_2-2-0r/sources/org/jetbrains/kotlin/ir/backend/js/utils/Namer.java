package org.jetbrains.kotlin.ir.backend.js.utils;

import kotlin.Metadata;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: Namer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b)\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u0014\u0010\u000f\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\nR\u0014\u0010\u0011\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\nR\u0014\u0010\u0013\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\nR\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u000e\u0010\u0017\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\nR\u0014\u0010\u001a\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\nR\u0014\u0010\u001c\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\nR\u0014\u0010\u001e\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\nR\u0014\u0010 \u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\nR\u0014\u0010\"\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\nR\u0014\u0010$\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\nR\u0014\u0010&\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\nR\u000e\u0010(\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u0014\u0010)\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\nR\u0014\u0010+\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\nR\u0014\u0010-\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\nR\u0014\u0010/\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\n¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/Namer;", "", "()V", "ARGUMENTS", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "getARGUMENTS", "()Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "BIND_FUNCTION", "", "getBIND_FUNCTION", "()Ljava/lang/String;", "CALL_FUNCTION", "getCALL_FUNCTION", "CONSTRUCTOR_NAME", "getCONSTRUCTOR_NAME", "DELEGATE", "getDELEGATE", "ES6_BOX_PARAMETER_NAME", "getES6_BOX_PARAMETER_NAME", "IMPLICIT_RECEIVER_NAME", "getIMPLICIT_RECEIVER_NAME", "JS_ERROR", "getJS_ERROR", "KCALLABLE_ARITY", "KCALLABLE_CACHE_SUFFIX", "getKCALLABLE_CACHE_SUFFIX", "KCALLABLE_GET_NAME", "getKCALLABLE_GET_NAME", "KCALLABLE_NAME", "getKCALLABLE_NAME", "KPROPERTY_GET", "getKPROPERTY_GET", "KPROPERTY_SET", "getKPROPERTY_SET", "METADATA", "getMETADATA", "OUTER_NAME", "getOUTER_NAME", "PROTOTYPE_NAME", "getPROTOTYPE_NAME", "SHARED_BOX_V", "SLICE_FUNCTION", "getSLICE_FUNCTION", "SYNTHETIC_RECEIVER_NAME", "getSYNTHETIC_RECEIVER_NAME", "THROWABLE_CONSTRUCTOR", "getTHROWABLE_CONSTRUCTOR", "UNREACHABLE_NAME", "getUNREACHABLE_NAME", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Namer {
    public static final String KCALLABLE_ARITY = "$arity";
    public static final String SHARED_BOX_V = "_v";
    public static final Namer INSTANCE = new Namer();
    private static final String CALL_FUNCTION = "call";
    private static final String BIND_FUNCTION = "bind";
    private static final String SLICE_FUNCTION = "slice";
    private static final String OUTER_NAME = org.jetbrains.kotlin.js.translate.context.Namer.OUTER_FIELD_NAME;
    private static final String UNREACHABLE_NAME = "$unreachable";
    private static final String THROWABLE_CONSTRUCTOR = "$throwableCtor";
    private static final String DELEGATE = JvmAbi.DELEGATED_PROPERTY_NAME_SUFFIX;
    private static final String IMPLICIT_RECEIVER_NAME = "this";
    private static final String SYNTHETIC_RECEIVER_NAME = "$this";
    private static final String ES6_BOX_PARAMETER_NAME = "$box";
    private static final JsNameRef ARGUMENTS = new JsNameRef("arguments");
    private static final String PROTOTYPE_NAME = "prototype";
    private static final String CONSTRUCTOR_NAME = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
    private static final JsNameRef JS_ERROR = new JsNameRef("Error");
    private static final String METADATA = org.jetbrains.kotlin.js.translate.context.Namer.METADATA;
    private static final String KCALLABLE_GET_NAME = "<get-name>";
    private static final String KCALLABLE_NAME = "callableName";
    private static final String KPROPERTY_GET = "get";
    private static final String KPROPERTY_SET = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
    private static final String KCALLABLE_CACHE_SUFFIX = "$cache";

    private Namer() {
    }

    public final String getCALL_FUNCTION() {
        return CALL_FUNCTION;
    }

    public final String getBIND_FUNCTION() {
        return BIND_FUNCTION;
    }

    public final String getSLICE_FUNCTION() {
        return SLICE_FUNCTION;
    }

    public final String getOUTER_NAME() {
        return OUTER_NAME;
    }

    public final String getUNREACHABLE_NAME() {
        return UNREACHABLE_NAME;
    }

    public final String getTHROWABLE_CONSTRUCTOR() {
        return THROWABLE_CONSTRUCTOR;
    }

    public final String getDELEGATE() {
        return DELEGATE;
    }

    public final String getIMPLICIT_RECEIVER_NAME() {
        return IMPLICIT_RECEIVER_NAME;
    }

    public final String getSYNTHETIC_RECEIVER_NAME() {
        return SYNTHETIC_RECEIVER_NAME;
    }

    public final String getES6_BOX_PARAMETER_NAME() {
        return ES6_BOX_PARAMETER_NAME;
    }

    public final JsNameRef getARGUMENTS() {
        return ARGUMENTS;
    }

    public final String getPROTOTYPE_NAME() {
        return PROTOTYPE_NAME;
    }

    public final String getCONSTRUCTOR_NAME() {
        return CONSTRUCTOR_NAME;
    }

    public final JsNameRef getJS_ERROR() {
        return JS_ERROR;
    }

    public final String getMETADATA() {
        return METADATA;
    }

    public final String getKCALLABLE_GET_NAME() {
        return KCALLABLE_GET_NAME;
    }

    public final String getKCALLABLE_NAME() {
        return KCALLABLE_NAME;
    }

    public final String getKPROPERTY_GET() {
        return KPROPERTY_GET;
    }

    public final String getKPROPERTY_SET() {
        return KPROPERTY_SET;
    }

    public final String getKCALLABLE_CACHE_SUFFIX() {
        return KCALLABLE_CACHE_SUFFIX;
    }
}
