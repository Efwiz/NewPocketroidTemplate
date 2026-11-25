package org.jetbrains.kotlin.cli.common.repl;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: ReplApi.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB/\u0012\u0010\u0010\u0002\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\u0003\u0012\u0016\u0010\u0005\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00060\u0003¢\u0006\u0002\u0010\u0007R\u001d\u0010\u0002\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\u0003¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR#\u0010\u0005\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00060\u0003¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;", "Ljava/io/Serializable;", "scriptArgs", "", "", "scriptArgsTypes", "Lkotlin/reflect/KClass;", "([Ljava/lang/Object;[Lkotlin/reflect/KClass;)V", "getScriptArgs", "()[Ljava/lang/Object;", "[Ljava/lang/Object;", "getScriptArgsTypes", "()[Lkotlin/reflect/KClass;", "[Lkotlin/reflect/KClass;", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptArgsWithTypes implements Serializable {
    private static final long serialVersionUID = 8529357500L;
    private final Object[] scriptArgs;
    private final KClass<? extends Object>[] scriptArgsTypes;

    public ScriptArgsWithTypes(Object[] scriptArgs, KClass<? extends Object>[] scriptArgsTypes) {
        Intrinsics.checkNotNullParameter(scriptArgs, "scriptArgs");
        Intrinsics.checkNotNullParameter(scriptArgsTypes, "scriptArgsTypes");
        this.scriptArgs = scriptArgs;
        this.scriptArgsTypes = scriptArgsTypes;
        int length = scriptArgs.length;
        int length2 = scriptArgsTypes.length;
    }

    public final Object[] getScriptArgs() {
        return this.scriptArgs;
    }

    public final KClass<? extends Object>[] getScriptArgsTypes() {
        return this.scriptArgsTypes;
    }
}
