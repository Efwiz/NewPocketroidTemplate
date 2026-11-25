package org.jetbrains.kotlin.cli.jvm.javac;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Log;
import java.io.PrintWriter;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JavacLogger.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ5\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0016\u0010\u000f\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00110\u0010\"\u0004\u0018\u00010\u0011H\u0016¢\u0006\u0002\u0010\u0012¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/javac/JavacLogger;", "Lcom/sun/tools/javac/util/Log;", "context", "Lcom/sun/tools/javac/util/Context;", "errorWriter", "Ljava/io/PrintWriter;", "warningWriter", "infoWriter", "(Lcom/sun/tools/javac/util/Context;Ljava/io/PrintWriter;Ljava/io/PrintWriter;Ljava/io/PrintWriter;)V", "printLines", "", Namer.METADATA_CLASS_KIND, "Lcom/sun/tools/javac/util/Log$WriterKind;", CrashHianalyticsData.MESSAGE, "", "args", "", "", "(Lcom/sun/tools/javac/util/Log$WriterKind;Ljava/lang/String;[Ljava/lang/Object;)V", "Companion", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavacLogger extends Log {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public void printLines(Log.WriterKind kind, String message, Object... args) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(args, "args");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavacLogger(Context context, PrintWriter errorWriter, PrintWriter warningWriter, PrintWriter infoWriter) {
        super(context, errorWriter, warningWriter, infoWriter);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(errorWriter, "errorWriter");
        Intrinsics.checkNotNullParameter(warningWriter, "warningWriter");
        Intrinsics.checkNotNullParameter(infoWriter, "infoWriter");
        context.put(Log.outKey, infoWriter);
    }

    /* JADX INFO: compiled from: JavacLogger.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/javac/JavacLogger$Companion;", "", "()V", "preRegister", "", "context", "Lcom/sun/tools/javac/util/Context;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void preRegister(Context context, final MessageCollector messageCollector) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
            context.put(Log.logKey, new Context.Factory() { // from class: org.jetbrains.kotlin.cli.jvm.javac.JavacLogger$Companion$$ExternalSyntheticLambda0
                public final Object make(Context context2) {
                    return JavacLogger.Companion.preRegister$lambda$0(messageCollector, context2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Log preRegister$lambda$0(MessageCollector messageCollector, Context context) {
            Intrinsics.checkNotNullParameter(messageCollector, "$messageCollector");
            Intrinsics.checkNotNull(context);
            return new JavacLogger(context, new PrintWriter(new MessageCollectorAdapter(messageCollector, CompilerMessageSeverity.ERROR)), new PrintWriter(new MessageCollectorAdapter(messageCollector, CompilerMessageSeverity.WARNING)), new PrintWriter(new MessageCollectorAdapter(messageCollector, CompilerMessageSeverity.INFO)));
        }
    }
}
