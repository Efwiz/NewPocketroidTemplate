package org.jetbrains.kotlin.types.error;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: ErrorScopeKind.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/types/error/ErrorScopeKind;", "", "debugMessage", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDebugMessage", "()Ljava/lang/String;", "CAPTURED_TYPE_SCOPE", "INTEGER_LITERAL_TYPE_SCOPE", "ERASED_RECEIVER_TYPE_SCOPE", "SCOPE_FOR_ABBREVIATION_TYPE", "STUB_TYPE_SCOPE", "NON_CLASSIFIER_SUPER_TYPE_SCOPE", "ERROR_TYPE_SCOPE", "UNSUPPORTED_TYPE_SCOPE", "SCOPE_FOR_ERROR_CLASS", "SCOPE_FOR_ERROR_RESOLUTION_CANDIDATE", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ErrorScopeKind {
    CAPTURED_TYPE_SCOPE("No member resolution should be done on captured type, it used only during constraint system resolution"),
    INTEGER_LITERAL_TYPE_SCOPE("Scope for integer literal type (%s)"),
    ERASED_RECEIVER_TYPE_SCOPE("Error scope for erased receiver type"),
    SCOPE_FOR_ABBREVIATION_TYPE("Scope for abbreviation %s"),
    STUB_TYPE_SCOPE("Scope for stub type %s"),
    NON_CLASSIFIER_SUPER_TYPE_SCOPE("A scope for common supertype which is not a normal classifier"),
    ERROR_TYPE_SCOPE("Scope for error type %s"),
    UNSUPPORTED_TYPE_SCOPE("Scope for unsupported type %s"),
    SCOPE_FOR_ERROR_CLASS("Error scope for class %s with arguments: %s"),
    SCOPE_FOR_ERROR_RESOLUTION_CANDIDATE("Error resolution candidate for call %s");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String debugMessage;

    public static EnumEntries<ErrorScopeKind> getEntries() {
        return $ENTRIES;
    }

    ErrorScopeKind(String str) {
        this.debugMessage = str;
    }

    public final String getDebugMessage() {
        return this.debugMessage;
    }
}
