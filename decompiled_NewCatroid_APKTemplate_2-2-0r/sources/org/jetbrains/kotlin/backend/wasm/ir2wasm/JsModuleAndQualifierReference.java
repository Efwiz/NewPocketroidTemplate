package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import java.util.Base64;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JsHelpers.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J!\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/JsModuleAndQualifierReference;", "", "module", "", "qualifier", "(Ljava/lang/String;Ljava/lang/String;)V", "jsVariableName", "getJsVariableName", "()Ljava/lang/String;", "getModule", "getQualifier", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JsModuleAndQualifierReference {
    private final String jsVariableName;
    private final String module;
    private final String qualifier;

    public static /* synthetic */ JsModuleAndQualifierReference copy$default(JsModuleAndQualifierReference jsModuleAndQualifierReference, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = jsModuleAndQualifierReference.module;
        }
        if ((i & 2) != 0) {
            str2 = jsModuleAndQualifierReference.qualifier;
        }
        return jsModuleAndQualifierReference.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getModule() {
        return this.module;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getQualifier() {
        return this.qualifier;
    }

    public final JsModuleAndQualifierReference copy(String module, String qualifier) {
        return new JsModuleAndQualifierReference(module, qualifier);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JsModuleAndQualifierReference)) {
            return false;
        }
        JsModuleAndQualifierReference jsModuleAndQualifierReference = (JsModuleAndQualifierReference) other;
        return Intrinsics.areEqual(this.module, jsModuleAndQualifierReference.module) && Intrinsics.areEqual(this.qualifier, jsModuleAndQualifierReference.qualifier);
    }

    public int hashCode() {
        String str = this.module;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.qualifier;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "JsModuleAndQualifierReference(module=" + this.module + ", qualifier=" + this.qualifier + ')';
    }

    public JsModuleAndQualifierReference(String str, String str2) {
        this.module = str;
        this.qualifier = str2;
        Base64.Encoder encoderWithoutPadding = Base64.getEncoder().withoutPadding();
        String str3 = this.module;
        String strEncodeToString = str3 != null ? encoderWithoutPadding.encodeToString(StringsKt.encodeToByteArray(str3)) : null;
        strEncodeToString = strEncodeToString == null ? "" : strEncodeToString;
        String str4 = this.qualifier;
        String strEncodeToString2 = str4 != null ? encoderWithoutPadding.encodeToString(StringsKt.encodeToByteArray(str4)) : null;
        this.jsVariableName = "_ref_" + strEncodeToString + '_' + (strEncodeToString2 != null ? strEncodeToString2 : "");
    }

    public final String getModule() {
        return this.module;
    }

    public final String getQualifier() {
        return this.qualifier;
    }

    public final String getJsVariableName() {
        return this.jsVariableName;
    }
}
