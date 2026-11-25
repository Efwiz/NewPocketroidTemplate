package org.jetbrains.kotlin.cli.jvm.compiler.pipeline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: compilerPipelineData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/pipeline/ModuleCompilerEnvironment;", "", "projectEnvironment", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "diagnosticsReporter", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "(Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;)V", "getDiagnosticsReporter", "()Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "getProjectEnvironment", "()Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ModuleCompilerEnvironment {
    private final BaseDiagnosticsCollector diagnosticsReporter;
    private final AbstractProjectEnvironment projectEnvironment;

    public static /* synthetic */ ModuleCompilerEnvironment copy$default(ModuleCompilerEnvironment moduleCompilerEnvironment, AbstractProjectEnvironment abstractProjectEnvironment, BaseDiagnosticsCollector baseDiagnosticsCollector, int i, Object obj) {
        if ((i & 1) != 0) {
            abstractProjectEnvironment = moduleCompilerEnvironment.projectEnvironment;
        }
        if ((i & 2) != 0) {
            baseDiagnosticsCollector = moduleCompilerEnvironment.diagnosticsReporter;
        }
        return moduleCompilerEnvironment.copy(abstractProjectEnvironment, baseDiagnosticsCollector);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final AbstractProjectEnvironment getProjectEnvironment() {
        return this.projectEnvironment;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final BaseDiagnosticsCollector getDiagnosticsReporter() {
        return this.diagnosticsReporter;
    }

    public final ModuleCompilerEnvironment copy(AbstractProjectEnvironment projectEnvironment, BaseDiagnosticsCollector diagnosticsReporter) {
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        return new ModuleCompilerEnvironment(projectEnvironment, diagnosticsReporter);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ModuleCompilerEnvironment)) {
            return false;
        }
        ModuleCompilerEnvironment moduleCompilerEnvironment = (ModuleCompilerEnvironment) other;
        return Intrinsics.areEqual(this.projectEnvironment, moduleCompilerEnvironment.projectEnvironment) && Intrinsics.areEqual(this.diagnosticsReporter, moduleCompilerEnvironment.diagnosticsReporter);
    }

    public int hashCode() {
        return (this.projectEnvironment.hashCode() * 31) + this.diagnosticsReporter.hashCode();
    }

    public String toString() {
        return "ModuleCompilerEnvironment(projectEnvironment=" + this.projectEnvironment + ", diagnosticsReporter=" + this.diagnosticsReporter + ')';
    }

    public ModuleCompilerEnvironment(AbstractProjectEnvironment projectEnvironment, BaseDiagnosticsCollector diagnosticsReporter) {
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        this.projectEnvironment = projectEnvironment;
        this.diagnosticsReporter = diagnosticsReporter;
    }

    public final AbstractProjectEnvironment getProjectEnvironment() {
        return this.projectEnvironment;
    }

    public final BaseDiagnosticsCollector getDiagnosticsReporter() {
        return this.diagnosticsReporter;
    }
}
