package kotlin.script.experimental.api;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptData.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lkotlin/script/experimental/api/ScriptSourceNamedFragment;", "Ljava/io/Serializable;", "name", "", "range", "Lkotlin/script/experimental/api/SourceCode$Range;", "(Ljava/lang/String;Lkotlin/script/experimental/api/SourceCode$Range;)V", "getName", "()Ljava/lang/String;", "getRange", "()Lkotlin/script/experimental/api/SourceCode$Range;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "Companion", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ScriptSourceNamedFragment implements Serializable {
    private static final long serialVersionUID = 1;
    private final String name;
    private final SourceCode.Range range;

    public static /* synthetic */ ScriptSourceNamedFragment copy$default(ScriptSourceNamedFragment scriptSourceNamedFragment, String str, SourceCode.Range range, int i, Object obj) {
        if ((i & 1) != 0) {
            str = scriptSourceNamedFragment.name;
        }
        if ((i & 2) != 0) {
            range = scriptSourceNamedFragment.range;
        }
        return scriptSourceNamedFragment.copy(str, range);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final SourceCode.Range getRange() {
        return this.range;
    }

    public final ScriptSourceNamedFragment copy(String name2, SourceCode.Range range) {
        Intrinsics.checkNotNullParameter(range, "range");
        return new ScriptSourceNamedFragment(name2, range);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScriptSourceNamedFragment)) {
            return false;
        }
        ScriptSourceNamedFragment scriptSourceNamedFragment = (ScriptSourceNamedFragment) other;
        return Intrinsics.areEqual(this.name, scriptSourceNamedFragment.name) && Intrinsics.areEqual(this.range, scriptSourceNamedFragment.range);
    }

    public int hashCode() {
        String str = this.name;
        return ((str == null ? 0 : str.hashCode()) * 31) + this.range.hashCode();
    }

    public String toString() {
        return "ScriptSourceNamedFragment(name=" + this.name + ", range=" + this.range + ')';
    }

    public ScriptSourceNamedFragment(String str, SourceCode.Range range) {
        Intrinsics.checkNotNullParameter(range, "range");
        this.name = str;
        this.range = range;
    }

    public final String getName() {
        return this.name;
    }

    public final SourceCode.Range getRange() {
        return this.range;
    }
}
