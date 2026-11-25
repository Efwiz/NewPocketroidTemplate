package org.jetbrains.kotlin.resolve.jvm.jvmSignature;

/* JADX INFO: loaded from: classes6.dex */
public enum JvmMethodParameterKind {
    VALUE,
    THIS,
    OUTER,
    RECEIVER,
    CONTEXT_RECEIVER,
    CAPTURED_LOCAL_VARIABLE,
    ENUM_NAME_OR_ORDINAL,
    SUPER_CALL_PARAM,
    CONSTRUCTOR_MARKER;

    public boolean isSkippedInGenericSignature() {
        return this == OUTER || this == ENUM_NAME_OR_ORDINAL;
    }
}
