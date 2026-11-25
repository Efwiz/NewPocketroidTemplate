package org.jetbrains.kotlin.js.dce;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InputFile.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/InputFile;", "", "resource", "Lorg/jetbrains/kotlin/js/dce/InputResource;", "sourceMapResource", "outputPath", "", "moduleName", "(Lorg/jetbrains/kotlin/js/dce/InputResource;Lorg/jetbrains/kotlin/js/dce/InputResource;Ljava/lang/String;Ljava/lang/String;)V", "getModuleName", "()Ljava/lang/String;", "getOutputPath", "getResource", "()Lorg/jetbrains/kotlin/js/dce/InputResource;", "getSourceMapResource", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InputFile {
    private final String moduleName;
    private final String outputPath;
    private final InputResource resource;
    private final InputResource sourceMapResource;

    public InputFile(InputResource resource, InputResource inputResource, String outputPath, String str) {
        Intrinsics.checkNotNullParameter(resource, "resource");
        Intrinsics.checkNotNullParameter(outputPath, "outputPath");
        this.resource = resource;
        this.sourceMapResource = inputResource;
        this.outputPath = outputPath;
        this.moduleName = str;
    }

    public /* synthetic */ InputFile(InputResource inputResource, InputResource inputResource2, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(inputResource, inputResource2, str, (i & 8) != 0 ? null : str2);
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final String getOutputPath() {
        return this.outputPath;
    }

    public final InputResource getResource() {
        return this.resource;
    }

    public final InputResource getSourceMapResource() {
        return this.sourceMapResource;
    }
}
