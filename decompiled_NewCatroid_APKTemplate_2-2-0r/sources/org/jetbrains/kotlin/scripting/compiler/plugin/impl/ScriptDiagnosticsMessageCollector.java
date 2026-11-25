package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.SourceCode;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: errorReporting.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0003J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0006J\"\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016R\u001e\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ScriptDiagnosticsMessageCollector;", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "parentMessageCollector", "(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)V", "_diagnostics", "Ljava/util/ArrayList;", "Lkotlin/script/experimental/api/ScriptDiagnostic;", "Lkotlin/collections/ArrayList;", "diagnostics", "", "getDiagnostics", "()Ljava/util/List;", LineReader.CLEAR, "", "hasErrors", "", "report", "diagnostic", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptDiagnosticsMessageCollector implements MessageCollector {
    private final ArrayList<ScriptDiagnostic> _diagnostics = new ArrayList<>();
    private final MessageCollector parentMessageCollector;

    public ScriptDiagnosticsMessageCollector(MessageCollector messageCollector) {
        this.parentMessageCollector = messageCollector;
    }

    public final List<ScriptDiagnostic> getDiagnostics() {
        return this._diagnostics;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void clear() {
        this._diagnostics.clear();
        MessageCollector messageCollector = this.parentMessageCollector;
        if (messageCollector != null) {
            messageCollector.clear();
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public boolean hasErrors() {
        ArrayList<ScriptDiagnostic> arrayList = this._diagnostics;
        if (!(arrayList instanceof Collection) || !arrayList.isEmpty()) {
            Iterator<T> it = arrayList.iterator();
            while (it.hasNext()) {
                if (((ScriptDiagnostic) it.next()).getSeverity() == ScriptDiagnostic.Severity.ERROR) {
                    return true;
                }
            }
        }
        MessageCollector messageCollector = this.parentMessageCollector;
        return messageCollector != null && messageCollector.hasErrors();
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void report(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation location) {
        SourceCode.Location location2;
        SourceCode.Location location3;
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        ScriptDiagnostic.Severity scriptingSeverity = ErrorReportingKt.toScriptingSeverity(severity);
        if (scriptingSeverity != null) {
            if (location != null) {
                if (location.getLine() < 0 && location.getColumn() < 0) {
                    location3 = null;
                } else if (location.getLineEnd() < 0 && location.getColumnEnd() < 0) {
                    location3 = new SourceCode.Location(new SourceCode.Position(location.getLine(), location.getColumn(), null, 4, null), null, 2, null);
                } else {
                    location3 = new SourceCode.Location(new SourceCode.Position(location.getLine(), location.getColumn(), null, 4, null), new SourceCode.Position(location.getLineEnd(), location.getColumnEnd(), null, 4, null));
                }
                location2 = location3;
            } else {
                location2 = null;
            }
            this._diagnostics.add(new ScriptDiagnostic(-1, message, scriptingSeverity, location != null ? location.getPath() : null, location2, null, 32, null));
        }
        MessageCollector messageCollector = this.parentMessageCollector;
        if (messageCollector != null) {
            messageCollector.report(severity, message, location);
        }
    }

    public final void report(ScriptDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this._diagnostics.add(diagnostic);
        MessageCollector messageCollector = this.parentMessageCollector;
        if (messageCollector == null) {
            return;
        }
        if (messageCollector instanceof ScriptDiagnosticsMessageCollector) {
            ((ScriptDiagnosticsMessageCollector) messageCollector).report(diagnostic);
            return;
        }
        SourceCode.Location location = diagnostic.getLocation();
        SourceCode.Position start = location != null ? location.getStart() : null;
        this.parentMessageCollector.report(ErrorReportingKt.toCompilerMessageSeverity(diagnostic.getSeverity()), diagnostic.getMessage(), CompilerMessageLocation.INSTANCE.create(null, start != null ? start.getLine() : -1, start != null ? start.getCol() : -1, null));
    }
}
