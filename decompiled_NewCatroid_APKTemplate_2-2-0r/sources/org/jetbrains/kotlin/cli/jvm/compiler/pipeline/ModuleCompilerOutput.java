package org.jetbrains.kotlin.cli.jvm.compiler.pipeline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: compilerPipelineData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/pipeline/ModuleCompilerOutput;", "", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "getGenerationState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ModuleCompilerOutput {
    private final GenerationState generationState;

    public static /* synthetic */ ModuleCompilerOutput copy$default(ModuleCompilerOutput moduleCompilerOutput, GenerationState generationState, int i, Object obj) {
        if ((i & 1) != 0) {
            generationState = moduleCompilerOutput.generationState;
        }
        return moduleCompilerOutput.copy(generationState);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final GenerationState getGenerationState() {
        return this.generationState;
    }

    public final ModuleCompilerOutput copy(GenerationState generationState) {
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        return new ModuleCompilerOutput(generationState);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ModuleCompilerOutput) && Intrinsics.areEqual(this.generationState, ((ModuleCompilerOutput) other).generationState);
    }

    public int hashCode() {
        return this.generationState.hashCode();
    }

    public String toString() {
        return "ModuleCompilerOutput(generationState=" + this.generationState + ')';
    }

    public ModuleCompilerOutput(GenerationState generationState) {
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        this.generationState = generationState;
    }

    public final GenerationState getGenerationState() {
        return this.generationState;
    }
}
