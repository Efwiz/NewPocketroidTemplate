package org.jetbrains.kotlin.js.backend.ast.metadata;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: metadataProperties.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;", "", "suggestedName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getSuggestedName", "()Ljava/lang/String;", "DEFINE_INLINE_FUNCTION", "WRAP_FUNCTION", "TO_BOXED_CHAR", "UNBOX_CHAR", "SUSPEND_CALL", "COROUTINE_RESULT", "COROUTINE_CONTROLLER", "COROUTINE_RECEIVER", "SET_COROUTINE_RESULT", "GET_KCLASS", "GET_REIFIED_TYPE_PARAMETER_KTYPE", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum SpecialFunction {
    DEFINE_INLINE_FUNCTION(Namer.DEFINE_INLINE_FUNCTION),
    WRAP_FUNCTION("wrapFunction"),
    TO_BOXED_CHAR("toBoxedChar"),
    UNBOX_CHAR("unboxChar"),
    SUSPEND_CALL("suspendCall"),
    COROUTINE_RESULT("coroutineResult"),
    COROUTINE_CONTROLLER("coroutineController"),
    COROUTINE_RECEIVER("coroutineReceiver"),
    SET_COROUTINE_RESULT("setCoroutineResult"),
    GET_KCLASS(Namer.GET_KCLASS),
    GET_REIFIED_TYPE_PARAMETER_KTYPE(Namer.CREATE_REIFIED_KTYPE_PARAMETER);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String suggestedName;

    public static EnumEntries<SpecialFunction> getEntries() {
        return $ENTRIES;
    }

    SpecialFunction(String str) {
        this.suggestedName = str;
    }

    public final String getSuggestedName() {
        return this.suggestedName;
    }
}
