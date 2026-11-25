package org.jetbrains.kotlin.scripting.compiler.plugin.definitions;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.SourceCode;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.scripting.resolve.ScriptReportSink;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: CliScriptReportSink.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u001c\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\u000eH\u0002J\f\u0010\u000f\u001a\u00020\u0010*\u00020\u0011H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/definitions/CliScriptReportSink;", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptReportSink;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)V", "attachReports", "", "scriptFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "reports", "", "Lkotlin/script/experimental/api/ScriptDiagnostic;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;", "Lkotlin/script/experimental/api/SourceCode$Location;", "convertSeverity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", "Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliScriptReportSink implements ScriptReportSink {
    private final MessageCollector messageCollector;

    /* JADX INFO: compiled from: CliScriptReportSink.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ScriptDiagnostic.Severity.values().length];
            try {
                iArr[ScriptDiagnostic.Severity.FATAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ScriptDiagnostic.Severity.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ScriptDiagnostic.Severity.WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ScriptDiagnostic.Severity.INFO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ScriptDiagnostic.Severity.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public CliScriptReportSink(MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        this.messageCollector = messageCollector;
    }

    @Override // org.jetbrains.kotlin.scripting.resolve.ScriptReportSink
    public void attachReports(VirtualFile scriptFile, List<ScriptDiagnostic> reports) {
        Intrinsics.checkNotNullParameter(scriptFile, "scriptFile");
        Intrinsics.checkNotNullParameter(reports, "reports");
        for (ScriptDiagnostic scriptDiagnostic : reports) {
            this.messageCollector.report(convertSeverity(scriptDiagnostic.getSeverity()), scriptDiagnostic.getMessage(), location(scriptFile, scriptDiagnostic.getLocation()));
        }
    }

    private final CompilerMessageLocation location(VirtualFile scriptFile, SourceCode.Location location) {
        if (location == null) {
            return CompilerMessageLocation.INSTANCE.create(scriptFile.getPath());
        }
        return CompilerMessageLocation.INSTANCE.create(scriptFile.getPath(), location.getStart().getLine(), location.getStart().getCol(), null);
    }

    private final CompilerMessageSeverity convertSeverity(ScriptDiagnostic.Severity severity) {
        int i = WhenMappings.$EnumSwitchMapping$0[severity.ordinal()];
        if (i == 1) {
            return CompilerMessageSeverity.ERROR;
        }
        if (i == 2) {
            return CompilerMessageSeverity.ERROR;
        }
        if (i == 3) {
            return CompilerMessageSeverity.WARNING;
        }
        if (i == 4) {
            return CompilerMessageSeverity.INFO;
        }
        if (i == 5) {
            return CompilerMessageSeverity.LOGGING;
        }
        throw new NoWhenBranchMatchedException();
    }
}
