package org.jetbrains.kotlin.daemon.common;

import java.io.Serializable;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompilationOptions.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0011\b\u0016\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cBK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\b\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0007¢\u0006\u0002\u0010\rJ\b\u0010\u001b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001b\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0019\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0018\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;", "Ljava/io/Serializable;", "compilerMode", "Lorg/jetbrains/kotlin/daemon/common/CompilerMode;", "targetPlatform", "Lorg/jetbrains/kotlin/daemon/common/CompileService$TargetPlatform;", "reportCategories", "", "", "reportSeverity", "requestedCompilationResults", "kotlinScriptExtensions", "", "(Lorg/jetbrains/kotlin/daemon/common/CompilerMode;Lorg/jetbrains/kotlin/daemon/common/CompileService$TargetPlatform;[Ljava/lang/Integer;I[Ljava/lang/Integer;[Ljava/lang/String;)V", "getCompilerMode", "()Lorg/jetbrains/kotlin/daemon/common/CompilerMode;", "getKotlinScriptExtensions", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getReportCategories", "()[Ljava/lang/Integer;", "[Ljava/lang/Integer;", "getReportSeverity", "()I", "getRequestedCompilationResults", "getTargetPlatform", "()Lorg/jetbrains/kotlin/daemon/common/CompileService$TargetPlatform;", "toString", "Companion", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CompilationOptions implements Serializable {
    public static final long serialVersionUID = 0;
    private final CompilerMode compilerMode;
    private final String[] kotlinScriptExtensions;
    private final Integer[] reportCategories;
    private final int reportSeverity;
    private final Integer[] requestedCompilationResults;
    private final CompileService.TargetPlatform targetPlatform;

    public CompilationOptions(CompilerMode compilerMode, CompileService.TargetPlatform targetPlatform, Integer[] reportCategories, int i, Integer[] requestedCompilationResults, String[] strArr) {
        Intrinsics.checkNotNullParameter(compilerMode, "compilerMode");
        Intrinsics.checkNotNullParameter(targetPlatform, "targetPlatform");
        Intrinsics.checkNotNullParameter(reportCategories, "reportCategories");
        Intrinsics.checkNotNullParameter(requestedCompilationResults, "requestedCompilationResults");
        this.compilerMode = compilerMode;
        this.targetPlatform = targetPlatform;
        this.reportCategories = reportCategories;
        this.reportSeverity = i;
        this.requestedCompilationResults = requestedCompilationResults;
        this.kotlinScriptExtensions = strArr;
    }

    public /* synthetic */ CompilationOptions(CompilerMode compilerMode, CompileService.TargetPlatform targetPlatform, Integer[] numArr, int i, Integer[] numArr2, String[] strArr, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilerMode, targetPlatform, numArr, i, numArr2, (i2 & 32) != 0 ? null : strArr);
    }

    public final CompilerMode getCompilerMode() {
        return this.compilerMode;
    }

    public final CompileService.TargetPlatform getTargetPlatform() {
        return this.targetPlatform;
    }

    public final Integer[] getReportCategories() {
        return this.reportCategories;
    }

    public final int getReportSeverity() {
        return this.reportSeverity;
    }

    public final Integer[] getRequestedCompilationResults() {
        return this.requestedCompilationResults;
    }

    public final String[] getKotlinScriptExtensions() {
        return this.kotlinScriptExtensions;
    }

    public String toString() {
        return "CompilationOptions(compilerMode=" + this.compilerMode + ", targetPlatform=" + this.targetPlatform + ", reportCategories=" + Arrays.toString(this.reportCategories) + ", reportSeverity=" + this.reportSeverity + ", requestedCompilationResults=" + Arrays.toString(this.requestedCompilationResults) + ", kotlinScriptExtensions=" + Arrays.toString(this.kotlinScriptExtensions) + ')';
    }
}
