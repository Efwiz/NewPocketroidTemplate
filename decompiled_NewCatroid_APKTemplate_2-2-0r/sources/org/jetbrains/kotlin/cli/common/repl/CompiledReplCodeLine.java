package org.jetbrains.kotlin.cli.common.repl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ReplApi.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/CompiledReplCodeLine;", "Ljava/io/Serializable;", "className", "", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;)V", "getClassName", "()Ljava/lang/String;", "getSource", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CompiledReplCodeLine implements Serializable {
    private static final long serialVersionUID = 8228307678L;
    private final String className;
    private final ReplCodeLine source;

    public static /* synthetic */ CompiledReplCodeLine copy$default(CompiledReplCodeLine compiledReplCodeLine, String str, ReplCodeLine replCodeLine, int i, Object obj) {
        if ((i & 1) != 0) {
            str = compiledReplCodeLine.className;
        }
        if ((i & 2) != 0) {
            replCodeLine = compiledReplCodeLine.source;
        }
        return compiledReplCodeLine.copy(str, replCodeLine);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getClassName() {
        return this.className;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ReplCodeLine getSource() {
        return this.source;
    }

    public final CompiledReplCodeLine copy(String className, ReplCodeLine source) {
        Intrinsics.checkNotNullParameter(className, "className");
        Intrinsics.checkNotNullParameter(source, "source");
        return new CompiledReplCodeLine(className, source);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CompiledReplCodeLine)) {
            return false;
        }
        CompiledReplCodeLine compiledReplCodeLine = (CompiledReplCodeLine) other;
        return Intrinsics.areEqual(this.className, compiledReplCodeLine.className) && Intrinsics.areEqual(this.source, compiledReplCodeLine.source);
    }

    public int hashCode() {
        return (this.className.hashCode() * 31) + this.source.hashCode();
    }

    public String toString() {
        return "CompiledReplCodeLine(className=" + this.className + ", source=" + this.source + ')';
    }

    public CompiledReplCodeLine(String className, ReplCodeLine source) {
        Intrinsics.checkNotNullParameter(className, "className");
        Intrinsics.checkNotNullParameter(source, "source");
        this.className = className;
        this.source = source;
    }

    public final String getClassName() {
        return this.className;
    }

    public final ReplCodeLine getSource() {
        return this.source;
    }
}
