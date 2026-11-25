package org.jetbrains.kotlin.cli.common;

import io.netty.util.internal.StringUtil;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.cli.common.arguments.CommonToolArguments;
import org.jetbrains.kotlin.cli.common.arguments.ParseCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.FilteringMessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer;
import org.jetbrains.kotlin.cli.common.messages.PlainTextMessageRenderer;
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.CompatKt;
import org.jetbrains.kotlin.cli.jvm.compiler.CompileEnvironmentException;
import org.jetbrains.kotlin.config.KotlinCompilerVersion;
import org.jetbrains.kotlin.config.Services;

/* JADX INFO: compiled from: CLITool.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u0000 &*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001&B\u0005¢\u0006\u0002\u0010\u0004J\r\u0010\n\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\rH\u0002J'\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014¢\u0006\u0002\u0010\u0015J/\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014¢\u0006\u0002\u0010\u0018J5\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u00172\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013H\u0004¢\u0006\u0002\u0010\u001bJ#\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00028\u0000¢\u0006\u0002\u0010\u001fJ%\u0010 \u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u001fJ\b\u0010!\u001a\u00020\u0014H&J#\u0010\"\u001a\u00020\r2\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u00132\u0006\u0010\u001e\u001a\u00028\u0000¢\u0006\u0002\u0010#J'\u0010$\u001a\u00020\r\"\b\b\u0001\u0010\u0001*\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u0002H\u0001H\u0002¢\u0006\u0002\u0010%R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/CLITool;", "A", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "", "()V", "isReadingSettingsFromEnvironmentAllowed", "", "()Z", "setReadingSettingsFromEnvironmentAllowed", "(Z)V", "createArguments", "()Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "disableURLConnectionCaches", "", "exec", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "errStream", "Ljava/io/PrintStream;", "args", "", "", "(Ljava/io/PrintStream;[Ljava/lang/String;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "messageRenderer", "Lorg/jetbrains/kotlin/cli/common/messages/MessageRenderer;", "(Ljava/io/PrintStream;Lorg/jetbrains/kotlin/cli/common/messages/MessageRenderer;[Ljava/lang/String;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "services", "Lorg/jetbrains/kotlin/config/Services;", "(Ljava/io/PrintStream;Lorg/jetbrains/kotlin/config/Services;Lorg/jetbrains/kotlin/cli/common/messages/MessageRenderer;[Ljava/lang/String;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "arguments", "(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Lorg/jetbrains/kotlin/config/Services;Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "execImpl", "executableScriptFileName", "parseArguments", "([Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;)V", "printVersionIfNeeded", "(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;)V", "Companion", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CLITool<A extends CommonToolArguments> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private boolean isReadingSettingsFromEnvironmentAllowed;

    @JvmStatic
    public static final void doMain(CLITool<?> cLITool, String[] strArr) {
        INSTANCE.doMain(cLITool, strArr);
    }

    @JvmStatic
    public static final ExitCode doMainNoExit(CLITool<?> cLITool, String[] strArr) {
        return INSTANCE.doMainNoExit(cLITool, strArr);
    }

    @JvmStatic
    public static final ExitCode doMainNoExit(CLITool<?> cLITool, String[] strArr, MessageRenderer messageRenderer) {
        return INSTANCE.doMainNoExit(cLITool, strArr, messageRenderer);
    }

    public abstract A createArguments();

    protected abstract ExitCode execImpl(MessageCollector messageCollector, Services services, A arguments);

    public abstract String executableScriptFileName();

    public CLITool() {
        this.isReadingSettingsFromEnvironmentAllowed = getClass().getClassLoader().getResource("META-INF/allow-configuring-from-environment") != null;
    }

    public final ExitCode exec(PrintStream errStream, String... args) {
        Intrinsics.checkNotNullParameter(errStream, "errStream");
        Intrinsics.checkNotNullParameter(args, "args");
        return exec(errStream, Services.EMPTY, INSTANCE.defaultMessageRenderer(), args);
    }

    public final ExitCode exec(PrintStream errStream, MessageRenderer messageRenderer, String... args) {
        Intrinsics.checkNotNullParameter(errStream, "errStream");
        Intrinsics.checkNotNullParameter(messageRenderer, "messageRenderer");
        Intrinsics.checkNotNullParameter(args, "args");
        return exec(errStream, Services.EMPTY, messageRenderer, args);
    }

    protected final ExitCode exec(PrintStream errStream, Services services, MessageRenderer messageRenderer, String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(errStream, "errStream");
        Intrinsics.checkNotNullParameter(services, "services");
        Intrinsics.checkNotNullParameter(messageRenderer, "messageRenderer");
        Intrinsics.checkNotNullParameter(args, "args");
        CommonToolArguments commonToolArgumentsCreateArguments = createArguments();
        ParseCommandLineArgumentsKt.parseCommandLineArguments$default(ArraysKt.asList(args), commonToolArgumentsCreateArguments, false, 4, null);
        if (this.isReadingSettingsFromEnvironmentAllowed) {
            ParseCommandLineArgumentsKt.parseCommandLineArgumentsFromEnvironment(commonToolArgumentsCreateArguments);
        }
        PrintingMessageCollector printingMessageCollector = new PrintingMessageCollector(errStream, messageRenderer, commonToolArgumentsCreateArguments.getVerbose());
        try {
            if (messageRenderer instanceof PlainTextMessageRenderer) {
                ((PlainTextMessageRenderer) messageRenderer).enableColorsIfNeeded();
            }
            errStream.print(messageRenderer.renderPreamble());
            String strValidateArguments = ParseCommandLineArgumentsKt.validateArguments(commonToolArgumentsCreateArguments.getErrors());
            if (strValidateArguments != null) {
                printingMessageCollector.report(CompilerMessageSeverity.ERROR, strValidateArguments, null);
                printingMessageCollector.report(CompilerMessageSeverity.INFO, "Use -help for more information", null);
                return ExitCode.COMPILATION_ERROR;
            }
            if (!commonToolArgumentsCreateArguments.getHelp() && !commonToolArgumentsCreateArguments.getExtraHelp()) {
                return exec(printingMessageCollector, services, commonToolArgumentsCreateArguments);
            }
            errStream.print(messageRenderer.renderUsage(Usage.render(this, commonToolArgumentsCreateArguments)));
            return ExitCode.OK;
        } finally {
            errStream.print(messageRenderer.renderConclusion());
            if (messageRenderer instanceof PlainTextMessageRenderer) {
                ((PlainTextMessageRenderer) messageRenderer).disableColorsIfNeeded();
            }
        }
    }

    public final ExitCode exec(MessageCollector messageCollector, Services services, A arguments) {
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(services, "services");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        disableURLConnectionCaches();
        printVersionIfNeeded(messageCollector, arguments);
        if (arguments.getSuppressWarnings() && !arguments.getAllWarningsAsErrors()) {
            messageCollector = new FilteringMessageCollector(messageCollector, Predicate.isEqual(CompilerMessageSeverity.WARNING));
        }
        ArgumentsKt.reportArgumentParseProblems(messageCollector, arguments);
        return execImpl(messageCollector, services, arguments);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [org.jetbrains.kotlin.cli.common.CLITool$disableURLConnectionCaches$1] */
    private final void disableURLConnectionCaches() {
        new URLConnection(new URL("file:.")) { // from class: org.jetbrains.kotlin.cli.common.CLITool.disableURLConnectionCaches.1
            @Override // java.net.URLConnection
            public Void connect() {
                throw new UnsupportedOperationException();
            }
        }.setDefaultUseCaches(false);
    }

    public final void parseArguments(String[] args, A arguments) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        ParseCommandLineArgumentsKt.parseCommandLineArguments$default(ArraysKt.asList(args), arguments, false, 4, null);
        String strValidateArguments = ParseCommandLineArgumentsKt.validateArguments(arguments.getErrors());
        if (strValidateArguments != null) {
            throw new IllegalArgumentException(strValidateArguments);
        }
    }

    private final <A extends CommonToolArguments> void printVersionIfNeeded(MessageCollector messageCollector, A arguments) {
        if (arguments.getVersion()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.INFO, executableScriptFileName() + StringUtil.SPACE + KotlinCompilerVersion.VERSION + " (JRE " + System.getProperty(SystemProperties.JAVA_RUNTIME_VERSION) + ')', null, 4, null);
        }
    }

    /* JADX INFO: renamed from: isReadingSettingsFromEnvironmentAllowed, reason: from getter */
    public final boolean getIsReadingSettingsFromEnvironmentAllowed() {
        return this.isReadingSettingsFromEnvironmentAllowed;
    }

    public final void setReadingSettingsFromEnvironmentAllowed(boolean z) {
        this.isReadingSettingsFromEnvironmentAllowed = z;
    }

    /* JADX INFO: compiled from: CLITool.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J'\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0007¢\u0006\u0002\u0010\fJ1\u0010\r\u001a\u00020\u000e2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010\u000f\u001a\u00020\u0004H\u0007¢\u0006\u0002\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/CLITool$Companion;", "", "()V", "defaultMessageRenderer", "Lorg/jetbrains/kotlin/cli/common/messages/MessageRenderer;", "doMain", "", "compiler", "Lorg/jetbrains/kotlin/cli/common/CLITool;", "args", "", "", "(Lorg/jetbrains/kotlin/cli/common/CLITool;[Ljava/lang/String;)V", "doMainNoExit", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "messageRenderer", "(Lorg/jetbrains/kotlin/cli/common/CLITool;[Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/messages/MessageRenderer;)Lorg/jetbrains/kotlin/cli/common/ExitCode;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final ExitCode doMainNoExit(CLITool<?> compiler, String[] args) {
            Intrinsics.checkNotNullParameter(compiler, "compiler");
            Intrinsics.checkNotNullParameter(args, "args");
            return doMainNoExit$default(this, compiler, args, null, 4, null);
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final MessageRenderer defaultMessageRenderer() {
            String property = System.getProperty(MessageRenderer.PROPERTY_KEY);
            if (Intrinsics.areEqual(property, MessageRenderer.XML.getName())) {
                MessageRenderer XML = MessageRenderer.XML;
                Intrinsics.checkNotNullExpressionValue(XML, "XML");
                return XML;
            }
            if (Intrinsics.areEqual(property, MessageRenderer.GRADLE_STYLE.getName())) {
                MessageRenderer GRADLE_STYLE = MessageRenderer.GRADLE_STYLE;
                Intrinsics.checkNotNullExpressionValue(GRADLE_STYLE, "GRADLE_STYLE");
                return GRADLE_STYLE;
            }
            if (Intrinsics.areEqual(property, MessageRenderer.XCODE_STYLE.getName())) {
                MessageRenderer XCODE_STYLE = MessageRenderer.XCODE_STYLE;
                Intrinsics.checkNotNullExpressionValue(XCODE_STYLE, "XCODE_STYLE");
                return XCODE_STYLE;
            }
            if (Intrinsics.areEqual(property, MessageRenderer.WITHOUT_PATHS.getName())) {
                MessageRenderer WITHOUT_PATHS = MessageRenderer.WITHOUT_PATHS;
                Intrinsics.checkNotNullExpressionValue(WITHOUT_PATHS, "WITHOUT_PATHS");
                return WITHOUT_PATHS;
            }
            if (Intrinsics.areEqual(property, MessageRenderer.PLAIN_FULL_PATHS.getName())) {
                MessageRenderer PLAIN_FULL_PATHS = MessageRenderer.PLAIN_FULL_PATHS;
                Intrinsics.checkNotNullExpressionValue(PLAIN_FULL_PATHS, "PLAIN_FULL_PATHS");
                return PLAIN_FULL_PATHS;
            }
            MessageRenderer PLAIN_RELATIVE_PATHS = MessageRenderer.PLAIN_RELATIVE_PATHS;
            Intrinsics.checkNotNullExpressionValue(PLAIN_RELATIVE_PATHS, "PLAIN_RELATIVE_PATHS");
            return PLAIN_RELATIVE_PATHS;
        }

        @JvmStatic
        public final void doMain(CLITool<?> compiler, String[] args) {
            Intrinsics.checkNotNullParameter(compiler, "compiler");
            Intrinsics.checkNotNullParameter(args, "args");
            if (System.getProperty(SystemProperties.JAVA_AWT_HEADLESS) == null) {
                System.setProperty(SystemProperties.JAVA_AWT_HEADLESS, "true");
            }
            if (CompilerSystemProperties.KOTLIN_COLORS_ENABLED_PROPERTY.getValue() == null) {
                CompilerSystemProperties.KOTLIN_COLORS_ENABLED_PROPERTY.setValue("true");
            }
            CompatKt.setupIdeaStandaloneExecution();
            ExitCode exitCodeDoMainNoExit$default = doMainNoExit$default(this, compiler, args, null, 4, null);
            if (exitCodeDoMainNoExit$default == ExitCode.OK) {
                return;
            }
            System.exit(exitCodeDoMainNoExit$default.getCode());
            throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
        }

        public static /* synthetic */ ExitCode doMainNoExit$default(Companion companion, CLITool cLITool, String[] strArr, MessageRenderer messageRenderer, int i, Object obj) {
            if ((i & 4) != 0) {
                messageRenderer = companion.defaultMessageRenderer();
            }
            return companion.doMainNoExit(cLITool, strArr, messageRenderer);
        }

        @JvmStatic
        public final ExitCode doMainNoExit(CLITool<?> compiler, String[] args, MessageRenderer messageRenderer) {
            Intrinsics.checkNotNullParameter(compiler, "compiler");
            Intrinsics.checkNotNullParameter(args, "args");
            Intrinsics.checkNotNullParameter(messageRenderer, "messageRenderer");
            try {
                PrintStream err = System.err;
                Intrinsics.checkNotNullExpressionValue(err, "err");
                return compiler.exec(err, messageRenderer, (String[]) Arrays.copyOf(args, args.length));
            } catch (CompileEnvironmentException e) {
                System.err.println(e.getMessage());
                return ExitCode.INTERNAL_ERROR;
            }
        }
    }
}
