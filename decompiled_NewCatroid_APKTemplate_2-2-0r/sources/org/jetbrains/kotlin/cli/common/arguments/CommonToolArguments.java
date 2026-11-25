package org.jetbrains.kotlin.cli.common.arguments;

import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CommonToolArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0000\n\u0002\b\u0002\b&\u0018\u0000 12\u00020\u00012\u00020\u0002:\u00011B\u0005¢\u0006\u0002\u0010\u0003J\u0013\u0010.\u001a\u00020\u00052\b\u0010/\u001a\u0004\u0018\u000100H\u0096\u0002R,\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0007\u0010\u0003\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R&\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\t\"\u0004\b\u0014\u0010\u000bR0\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR&\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\t\"\u0004\b\u001e\u0010\u000bR0\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u00152\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0015@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0019\"\u0004\b\"\u0010\u001bR,\u0010#\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b$\u0010\u0003\u001a\u0004\b%\u0010\t\"\u0004\b&\u0010\u000bR,\u0010'\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b(\u0010\u0003\u001a\u0004\b)\u0010\t\"\u0004\b*\u0010\u000bR&\u0010+\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\t\"\u0004\b-\u0010\u000b¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "Lorg/jetbrains/kotlin/cli/common/arguments/Freezable;", "Ljava/io/Serializable;", "()V", "value", "", "allWarningsAsErrors", "getAllWarningsAsErrors$annotations", "getAllWarningsAsErrors", "()Z", "setAllWarningsAsErrors", "(Z)V", "errors", "Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;", "getErrors", "()Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;", "setErrors", "(Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;)V", "extraHelp", "getExtraHelp", "setExtraHelp", "", "", "freeArgs", "getFreeArgs", "()Ljava/util/List;", "setFreeArgs", "(Ljava/util/List;)V", "help", "getHelp", "setHelp", "Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgument;", "internalArguments", "getInternalArguments", "setInternalArguments", "suppressWarnings", "getSuppressWarnings$annotations", "getSuppressWarnings", "setSuppressWarnings", "verbose", "getVerbose$annotations", "getVerbose", "setVerbose", "version", "getVersion", "setVersion", Namer.EQUALS_METHOD_NAME, "other", "", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CommonToolArguments extends Freezable implements Serializable {
    private static final long serialVersionUID = 0;

    @Argument(description = "Report an error if there are any warnings", value = "-Werror")
    private boolean allWarningsAsErrors;
    private transient ArgumentParseErrors errors;

    @Argument(description = "Print a synopsis of advanced options", value = "-X")
    private boolean extraHelp;

    @Argument(description = "Print a synopsis of standard options", shortName = "-h", value = "-help")
    private boolean help;

    @Argument(description = "Generate no warnings", value = "-nowarn")
    private boolean suppressWarnings;

    @Argument(description = "Enable verbose logging output", value = "-verbose")
    private boolean verbose;

    @Argument(description = "Display compiler version", value = "-version")
    private boolean version;
    private List<String> freeArgs = CollectionsKt.emptyList();
    private List<? extends InternalArgument> internalArguments = CollectionsKt.emptyList();

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_FALSE_DEFAULT)
    public static /* synthetic */ void getAllWarningsAsErrors$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INTERNAL, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_FALSE_DEFAULT)
    public static /* synthetic */ void getSuppressWarnings$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INTERNAL, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_FALSE_DEFAULT)
    public static /* synthetic */ void getVerbose$annotations() {
    }

    public final List<String> getFreeArgs() {
        return this.freeArgs;
    }

    public final void setFreeArgs(List<String> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        checkFrozen();
        this.freeArgs = value;
    }

    public final ArgumentParseErrors getErrors() {
        return this.errors;
    }

    public final void setErrors(ArgumentParseErrors argumentParseErrors) {
        this.errors = argumentParseErrors;
    }

    public final boolean getHelp() {
        return this.help;
    }

    public final void setHelp(boolean z) {
        checkFrozen();
        this.help = z;
    }

    public final boolean getExtraHelp() {
        return this.extraHelp;
    }

    public final void setExtraHelp(boolean z) {
        checkFrozen();
        this.extraHelp = z;
    }

    public final boolean getVersion() {
        return this.version;
    }

    public final void setVersion(boolean z) {
        checkFrozen();
        this.version = z;
    }

    public final boolean getVerbose() {
        return this.verbose;
    }

    public final void setVerbose(boolean z) {
        checkFrozen();
        this.verbose = z;
    }

    public final boolean getSuppressWarnings() {
        return this.suppressWarnings;
    }

    public final void setSuppressWarnings(boolean z) {
        checkFrozen();
        this.suppressWarnings = z;
    }

    public final boolean getAllWarningsAsErrors() {
        return this.allWarningsAsErrors;
    }

    public final void setAllWarningsAsErrors(boolean z) {
        checkFrozen();
        this.allWarningsAsErrors = z;
    }

    public final List<InternalArgument> getInternalArguments() {
        return this.internalArguments;
    }

    public final void setInternalArguments(List<? extends InternalArgument> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        checkFrozen();
        this.internalArguments = value;
    }

    public boolean equals(Object other) {
        return super.equals(other);
    }
}
