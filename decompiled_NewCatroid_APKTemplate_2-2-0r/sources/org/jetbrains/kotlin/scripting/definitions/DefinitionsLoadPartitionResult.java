package org.jetbrains.kotlin.scripting.definitions;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptiDefinitionsFromClasspathDiscoverySource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J)\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0006HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/DefinitionsLoadPartitionResult;", "", "loaded", "", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "notFound", "", "(Ljava/util/List;Ljava/util/List;)V", "getLoaded", "()Ljava/util/List;", "getNotFound", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class DefinitionsLoadPartitionResult {
    private final List<ScriptDefinition> loaded;
    private final List<String> notFound;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DefinitionsLoadPartitionResult copy$default(DefinitionsLoadPartitionResult definitionsLoadPartitionResult, List list, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = definitionsLoadPartitionResult.loaded;
        }
        if ((i & 2) != 0) {
            list2 = definitionsLoadPartitionResult.notFound;
        }
        return definitionsLoadPartitionResult.copy(list, list2);
    }

    public final List<ScriptDefinition> component1() {
        return this.loaded;
    }

    public final List<String> component2() {
        return this.notFound;
    }

    public final DefinitionsLoadPartitionResult copy(List<? extends ScriptDefinition> loaded, List<String> notFound) {
        Intrinsics.checkNotNullParameter(loaded, "loaded");
        Intrinsics.checkNotNullParameter(notFound, "notFound");
        return new DefinitionsLoadPartitionResult(loaded, notFound);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DefinitionsLoadPartitionResult)) {
            return false;
        }
        DefinitionsLoadPartitionResult definitionsLoadPartitionResult = (DefinitionsLoadPartitionResult) other;
        return Intrinsics.areEqual(this.loaded, definitionsLoadPartitionResult.loaded) && Intrinsics.areEqual(this.notFound, definitionsLoadPartitionResult.notFound);
    }

    public int hashCode() {
        return (this.loaded.hashCode() * 31) + this.notFound.hashCode();
    }

    public String toString() {
        return "DefinitionsLoadPartitionResult(loaded=" + this.loaded + ", notFound=" + this.notFound + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DefinitionsLoadPartitionResult(List<? extends ScriptDefinition> loaded, List<String> notFound) {
        Intrinsics.checkNotNullParameter(loaded, "loaded");
        Intrinsics.checkNotNullParameter(notFound, "notFound");
        this.loaded = loaded;
        this.notFound = notFound;
    }

    public final List<ScriptDefinition> getLoaded() {
        return this.loaded;
    }

    public final List<String> getNotFound() {
        return this.notFound;
    }
}
