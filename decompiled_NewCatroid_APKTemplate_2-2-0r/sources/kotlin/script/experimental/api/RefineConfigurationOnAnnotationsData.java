package kotlin.script.experimental.api;

import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB1\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u001c\u0010\u0005\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0002`\n¢\u0006\u0002\u0010\u000bJ\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u001f\u0010\u0011\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0002`\nHÆ\u0003J9\u0010\u0012\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u001e\b\u0002\u0010\u0005\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0002`\nHÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR'\u0010\u0005\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0002`\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001c"}, d2 = {"Lkotlin/script/experimental/api/RefineConfigurationOnAnnotationsData;", "Ljava/io/Serializable;", "annotations", "", "Lkotlin/script/experimental/api/KotlinType;", "handler", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptConfigurationRefinementContext;", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "Lkotlin/script/experimental/api/RefineScriptCompilationConfigurationHandler;", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "getAnnotations", "()Ljava/util/List;", "getHandler", "()Lkotlin/jvm/functions/Function1;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "Companion", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class RefineConfigurationOnAnnotationsData implements Serializable {
    private static final long serialVersionUID = 1;
    private final List<KotlinType> annotations;
    private final Function1<ScriptConfigurationRefinementContext, ResultWithDiagnostics<ScriptCompilationConfiguration>> handler;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ RefineConfigurationOnAnnotationsData copy$default(RefineConfigurationOnAnnotationsData refineConfigurationOnAnnotationsData, List list, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            list = refineConfigurationOnAnnotationsData.annotations;
        }
        if ((i & 2) != 0) {
            function1 = refineConfigurationOnAnnotationsData.handler;
        }
        return refineConfigurationOnAnnotationsData.copy(list, function1);
    }

    public final List<KotlinType> component1() {
        return this.annotations;
    }

    public final Function1<ScriptConfigurationRefinementContext, ResultWithDiagnostics<ScriptCompilationConfiguration>> component2() {
        return this.handler;
    }

    public final RefineConfigurationOnAnnotationsData copy(List<KotlinType> annotations, Function1<? super ScriptConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptCompilationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(handler, "handler");
        return new RefineConfigurationOnAnnotationsData(annotations, handler);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RefineConfigurationOnAnnotationsData)) {
            return false;
        }
        RefineConfigurationOnAnnotationsData refineConfigurationOnAnnotationsData = (RefineConfigurationOnAnnotationsData) other;
        return Intrinsics.areEqual(this.annotations, refineConfigurationOnAnnotationsData.annotations) && Intrinsics.areEqual(this.handler, refineConfigurationOnAnnotationsData.handler);
    }

    public int hashCode() {
        return (this.annotations.hashCode() * 31) + this.handler.hashCode();
    }

    public String toString() {
        return "RefineConfigurationOnAnnotationsData(annotations=" + this.annotations + ", handler=" + this.handler + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RefineConfigurationOnAnnotationsData(List<KotlinType> annotations, Function1<? super ScriptConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptCompilationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.annotations = annotations;
        this.handler = handler;
    }

    public final List<KotlinType> getAnnotations() {
        return this.annotations;
    }

    public final Function1<ScriptConfigurationRefinementContext, ResultWithDiagnostics<ScriptCompilationConfiguration>> getHandler() {
        return this.handler;
    }
}
