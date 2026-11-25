package org.jetbrains.kotlin.cli.common.arguments;

import kotlin.DeprecationLevel;
import kotlin.Metadata;

/* JADX INFO: compiled from: K2JSDceArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 !2\u00020\u0001:\u0001!B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001f\u001a\u00020 H\u0014R8\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00042\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u000b\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR,\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f8\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u000e\u0010\u0002\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R*\u0010\u0013\u001a\u0004\u0018\u00010\u00052\b\u0010\u0003\u001a\u0004\u0018\u00010\u00058\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R0\u0010\u0018\u001a\u0004\u0018\u00010\u00052\b\u0010\u0003\u001a\u0004\u0018\u00010\u00058\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0019\u0010\u0002\u001a\u0004\b\u001a\u0010\u0015\"\u0004\b\u001b\u0010\u0017R&\u0010\u001c\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0010\"\u0004\b\u001e\u0010\u0012¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/K2JSDceArguments;", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "()V", "value", "", "", "declarationsToKeep", "getDeclarationsToKeep", "()[Ljava/lang/String;", "setDeclarationsToKeep", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "", "devMode", "getDevMode$annotations", "getDevMode", "()Z", "setDevMode", "(Z)V", "devModeOverwritingStrategy", "getDevModeOverwritingStrategy", "()Ljava/lang/String;", "setDevModeOverwritingStrategy", "(Ljava/lang/String;)V", "outputDirectory", "getOutputDirectory$annotations", "getOutputDirectory", "setOutputDirectory", "printReachabilityInfo", "getPrintReachabilityInfo", "setPrintReachabilityInfo", "copyOf", "Lorg/jetbrains/kotlin/cli/common/arguments/Freezable;", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2JSDceArguments extends CommonToolArguments {
    private static final long serialVersionUID = 0;

    @Argument(description = "List of fully-qualified names of declarations that shouldn't be eliminated", value = "-keep", valueDescription = "<fully.qualified.name[,]>")
    private String[] declarationsToKeep;

    @Argument(description = "Development mode: don't strip out any code, just copy dependencies", value = "-dev-mode")
    private boolean devMode;

    @Argument(description = "Overwriting strategy during copy dependencies in development mode", value = "-Xdev-mode-overwriting-strategy", valueDescription = "{older|all}")
    private String devModeOverwritingStrategy;

    @Argument(description = "Output directory", value = "-output-dir", valueDescription = "<path>")
    private String outputDirectory;

    @Argument(description = "Print declarations marked as reachable", value = "-Xprint-reachability-info")
    private boolean printReachabilityInfo;

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_FALSE_DEFAULT)
    public static /* synthetic */ void getDevMode$annotations() {
    }

    @GradleDeprecatedOption(level = DeprecationLevel.WARNING, message = "Use task 'destinationDirectory' to configure output directory", removeAfter = "1.9.0")
    @GradleOption(gradleInputType = GradleInputTypes.INTERNAL, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.STRING_NULL_DEFAULT)
    public static /* synthetic */ void getOutputDirectory$annotations() {
    }

    public final String getOutputDirectory() {
        return this.outputDirectory;
    }

    public final void setOutputDirectory(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.outputDirectory = str;
    }

    public final String[] getDeclarationsToKeep() {
        return this.declarationsToKeep;
    }

    public final void setDeclarationsToKeep(String[] strArr) {
        checkFrozen();
        this.declarationsToKeep = strArr;
    }

    public final boolean getPrintReachabilityInfo() {
        return this.printReachabilityInfo;
    }

    public final void setPrintReachabilityInfo(boolean z) {
        checkFrozen();
        this.printReachabilityInfo = z;
    }

    public final boolean getDevMode() {
        return this.devMode;
    }

    public final void setDevMode(boolean z) {
        checkFrozen();
        this.devMode = z;
    }

    public final String getDevModeOverwritingStrategy() {
        return this.devModeOverwritingStrategy;
    }

    public final void setDevModeOverwritingStrategy(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.devModeOverwritingStrategy = str;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.Freezable
    protected Freezable copyOf() {
        return K2JSDceArgumentsCopyGeneratedKt.copyK2JSDceArguments(this, new K2JSDceArguments());
    }
}
