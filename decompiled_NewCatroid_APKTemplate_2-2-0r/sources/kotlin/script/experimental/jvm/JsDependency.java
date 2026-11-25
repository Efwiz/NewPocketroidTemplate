package kotlin.script.experimental.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptDependency;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmScriptCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lkotlin/script/experimental/jvm/JsDependency;", "Lkotlin/script/experimental/api/ScriptDependency;", "path", "", "(Ljava/lang/String;)V", "getPath", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JsDependency implements ScriptDependency {
    private final String path;

    public static /* synthetic */ JsDependency copy$default(JsDependency jsDependency, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = jsDependency.path;
        }
        return jsDependency.copy(str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    public final JsDependency copy(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new JsDependency(path);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof JsDependency) && Intrinsics.areEqual(this.path, ((JsDependency) other).path);
    }

    public int hashCode() {
        return this.path.hashCode();
    }

    public String toString() {
        return "JsDependency(path=" + this.path + ')';
    }

    public JsDependency(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.path = path;
    }

    public final String getPath() {
        return this.path;
    }
}
