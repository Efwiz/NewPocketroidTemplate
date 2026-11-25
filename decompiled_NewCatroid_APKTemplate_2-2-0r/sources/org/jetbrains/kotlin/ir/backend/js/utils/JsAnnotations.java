package org.jetbrains.kotlin.ir.backend.js.utils;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: AnnotationUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0011\u0010\u001b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0006R\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/JsAnnotations;", "", "()V", "JsPolyfillFqn", "Lorg/jetbrains/kotlin/name/FqName;", "getJsPolyfillFqn", "()Lorg/jetbrains/kotlin/name/FqName;", "jsExportFqn", "getJsExportFqn", "jsExportIgnoreFqn", "getJsExportIgnoreFqn", "jsFileNameFqn", "getJsFileNameFqn", "jsFunFqn", "getJsFunFqn", "jsImplicitExportFqn", "getJsImplicitExportFqn", "jsModuleFqn", "getJsModuleFqn", "jsNameFqn", "getJsNameFqn", "jsNativeGetter", "getJsNativeGetter", "jsNativeInvoke", "getJsNativeInvoke", "jsNativeSetter", "getJsNativeSetter", "jsNonModuleFqn", "getJsNonModuleFqn", "jsQualifierFqn", "getJsQualifierFqn", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsAnnotations {
    public static final JsAnnotations INSTANCE = new JsAnnotations();
    private static final FqName jsModuleFqn = new FqName("kotlin.js.JsModule");
    private static final FqName jsNonModuleFqn = new FqName("kotlin.js.JsNonModule");
    private static final FqName jsNameFqn = new FqName("kotlin.js.JsName");
    private static final FqName jsFileNameFqn = new FqName("kotlin.js.JsFileName");
    private static final FqName jsQualifierFqn = new FqName("kotlin.js.JsQualifier");
    private static final FqName jsExportFqn = new FqName("kotlin.js.JsExport");
    private static final FqName jsImplicitExportFqn = new FqName("kotlin.js.JsImplicitExport");
    private static final FqName jsExportIgnoreFqn = new FqName("kotlin.js.JsExport.Ignore");
    private static final FqName jsNativeGetter = new FqName("kotlin.js.nativeGetter");
    private static final FqName jsNativeSetter = new FqName("kotlin.js.nativeSetter");
    private static final FqName jsNativeInvoke = new FqName("kotlin.js.nativeInvoke");
    private static final FqName jsFunFqn = new FqName("kotlin.js.JsFun");
    private static final FqName JsPolyfillFqn = new FqName("kotlin.js.JsPolyfill");

    private JsAnnotations() {
    }

    public final FqName getJsModuleFqn() {
        return jsModuleFqn;
    }

    public final FqName getJsNonModuleFqn() {
        return jsNonModuleFqn;
    }

    public final FqName getJsNameFqn() {
        return jsNameFqn;
    }

    public final FqName getJsFileNameFqn() {
        return jsFileNameFqn;
    }

    public final FqName getJsQualifierFqn() {
        return jsQualifierFqn;
    }

    public final FqName getJsExportFqn() {
        return jsExportFqn;
    }

    public final FqName getJsImplicitExportFqn() {
        return jsImplicitExportFqn;
    }

    public final FqName getJsExportIgnoreFqn() {
        return jsExportIgnoreFqn;
    }

    public final FqName getJsNativeGetter() {
        return jsNativeGetter;
    }

    public final FqName getJsNativeSetter() {
        return jsNativeSetter;
    }

    public final FqName getJsNativeInvoke() {
        return jsNativeInvoke;
    }

    public final FqName getJsFunFqn() {
        return jsFunFqn;
    }

    public final FqName getJsPolyfillFqn() {
        return JsPolyfillFqn;
    }
}
