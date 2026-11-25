package kotlin.script.templates.standard;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: templates.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0019\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lkotlin/script/templates/standard/ScriptTemplateWithArgs;", "", "args", "", "", "([Ljava/lang/String;)V", "getArgs", "()[Ljava/lang/String;", "[Ljava/lang/String;", PathUtil.KOTLIN_JAVA_SCRIPT_RUNTIME_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ScriptTemplateWithArgs {
    private final String[] args;

    public ScriptTemplateWithArgs(String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        this.args = args;
    }

    public final String[] getArgs() {
        return this.args;
    }
}
