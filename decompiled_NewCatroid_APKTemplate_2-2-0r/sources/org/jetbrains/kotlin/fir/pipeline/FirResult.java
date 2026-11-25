package org.jetbrains.kotlin.fir.pipeline;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: convertToIr.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/pipeline/FirResult;", "", "outputs", "", "Lorg/jetbrains/kotlin/fir/pipeline/ModuleCompilerAnalyzedOutput;", "(Ljava/util/List;)V", "getOutputs", "()Ljava/util/List;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FirResult {
    private final List<ModuleCompilerAnalyzedOutput> outputs;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FirResult copy$default(FirResult firResult, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = firResult.outputs;
        }
        return firResult.copy(list);
    }

    public final List<ModuleCompilerAnalyzedOutput> component1() {
        return this.outputs;
    }

    public final FirResult copy(List<ModuleCompilerAnalyzedOutput> outputs) {
        Intrinsics.checkNotNullParameter(outputs, "outputs");
        return new FirResult(outputs);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof FirResult) && Intrinsics.areEqual(this.outputs, ((FirResult) other).outputs);
    }

    public int hashCode() {
        return this.outputs.hashCode();
    }

    public String toString() {
        return "FirResult(outputs=" + this.outputs + ')';
    }

    public FirResult(List<ModuleCompilerAnalyzedOutput> outputs) {
        Intrinsics.checkNotNullParameter(outputs, "outputs");
        this.outputs = outputs;
    }

    public final List<ModuleCompilerAnalyzedOutput> getOutputs() {
        return this.outputs;
    }
}
