package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplInterpreter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\"\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"org/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplInterpreter$messageCollector$1", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "hasErrors", "", "messageRenderer", "Lorg/jetbrains/kotlin/cli/common/messages/MessageRenderer;", JvmProtoBufUtil.PLATFORM_TYPE_ID, LineReader.CLEAR, "", "report", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplInterpreter$messageCollector$1 implements MessageCollector {
    private boolean hasErrors;
    private final MessageRenderer messageRenderer = MessageRenderer.WITHOUT_PATHS;
    final /* synthetic */ ReplInterpreter this$0;

    /* JADX INFO: compiled from: ReplInterpreter.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CompilerMessageSeverity.values().length];
            try {
                iArr[CompilerMessageSeverity.EXCEPTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CompilerMessageSeverity.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CompilerMessageSeverity.STRONG_WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CompilerMessageSeverity.WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[CompilerMessageSeverity.INFO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    ReplInterpreter$messageCollector$1(ReplInterpreter replInterpreter) {
        this.this$0 = replInterpreter;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void clear() {
        this.hasErrors = false;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void report(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation location) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        String strRender = this.messageRenderer.render(severity, message, location);
        Intrinsics.checkNotNullExpressionValue(strRender, "render(...)");
        String string = StringsKt.trimEnd((CharSequence) strRender).toString();
        ReplWriter writer = this.this$0.replConfiguration.getWriter();
        int i = WhenMappings.$EnumSwitchMapping$0[severity.ordinal()];
        if (i == 1) {
            writer.sendInternalErrorReport(string);
        } else {
            if (i != 2) {
                return;
            }
            writer.outputCompileError(string);
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    /* JADX INFO: renamed from: hasErrors, reason: from getter */
    public boolean getHasErrors() {
        return this.hasErrors;
    }
}
