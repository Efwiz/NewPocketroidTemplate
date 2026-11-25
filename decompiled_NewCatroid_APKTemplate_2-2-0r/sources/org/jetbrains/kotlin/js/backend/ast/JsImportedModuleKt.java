package org.jetbrains.kotlin.js.backend.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsImportedModule.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u0014\u0010\u0003\u001a\u00020\u0001*\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"ESM_EXTENSION", "", "REGULAR_EXTENSION", "getRequireName", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "isEsm", "", "js.ast"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsImportedModuleKt {
    public static final String ESM_EXTENSION = ".mjs";
    public static final String REGULAR_EXTENSION = ".js";

    public static /* synthetic */ String getRequireName$default(JsImportedModule jsImportedModule, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return getRequireName(jsImportedModule, z);
    }

    public static final String getRequireName(JsImportedModule jsImportedModule, boolean z) {
        Intrinsics.checkNotNullParameter(jsImportedModule, "<this>");
        String relativeRequirePath = jsImportedModule.getRelativeRequirePath();
        if (relativeRequirePath != null) {
            String str = relativeRequirePath + (z ? ".mjs" : ".js");
            if (str != null) {
                return str;
            }
        }
        return jsImportedModule.getExternalName();
    }
}
