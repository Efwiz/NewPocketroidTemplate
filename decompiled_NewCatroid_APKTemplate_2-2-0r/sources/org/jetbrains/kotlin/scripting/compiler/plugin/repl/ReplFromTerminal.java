package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.repl.ReplEvalResult;
import org.jetbrains.kotlin.cli.common.repl.ReplUtilKt;
import org.jetbrains.kotlin.com.intellij.core.JavaCoreProjectEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.descriptors.runtime.components.ReflectJavaClassFinderKt;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ConsoleReplConfiguration;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.IdeReplConfiguration;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter;
import org.jetbrains.kotlin.utils.PathUtil;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ReplFromTerminal.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 $2\u00020\u0001:\u0002$%B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001aH\u0002J\u0012\u0010!\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\"\u001a\u00020#H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplFromTerminal;", "", "projectEnvironment", "Lorg/jetbrains/kotlin/com/intellij/core/JavaCoreProjectEnvironment;", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "replConfiguration", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/ReplConfiguration;", "(Lcom/intellij/core/JavaCoreProjectEnvironment;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/ReplConfiguration;)V", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "replInitializer", "Ljava/util/concurrent/Future;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplInterpreter;", "replInterpreter", "getReplInterpreter", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplInterpreter;", "writer", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;", "getWriter", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/writer/ReplWriter;", "doRun", "", "eval", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", Constant.LINE, "", "one", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplFromTerminal$WhatNextAfterOneLine;", ES6Iterator.NEXT_METHOD, "oneCommand", "", "command", "tryInterpretResultAsValueClass", "evalResult", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$ValueResult;", "Companion", "WhatNextAfterOneLine", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplFromTerminal {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final MessageCollector messageCollector;
    private final ReplConfiguration replConfiguration;
    private final Future<ReplInterpreter> replInitializer;

    /* JADX INFO: compiled from: ReplFromTerminal.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplFromTerminal$WhatNextAfterOneLine;", "", "(Ljava/lang/String;I)V", "READ_LINE", "INCOMPLETE", "QUIT", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum WhatNextAfterOneLine {
        READ_LINE,
        INCOMPLETE,
        QUIT;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<WhatNextAfterOneLine> getEntries() {
            return $ENTRIES;
        }
    }

    public ReplFromTerminal(final JavaCoreProjectEnvironment projectEnvironment, final CompilerConfiguration compilerConfiguration, ReplConfiguration replConfiguration) {
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(replConfiguration, "replConfiguration");
        this.replConfiguration = replConfiguration;
        Future<ReplInterpreter> futureSubmit = Executors.newSingleThreadExecutor().submit(new Callable() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return ReplFromTerminal.replInitializer$lambda$0(projectEnvironment, compilerConfiguration, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(futureSubmit, "submit(...)");
        this.replInitializer = futureSubmit;
        Object notNull = compilerConfiguration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        this.messageCollector = (MessageCollector) notNull;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ReplInterpreter replInitializer$lambda$0(JavaCoreProjectEnvironment projectEnvironment, CompilerConfiguration compilerConfiguration, ReplFromTerminal this$0) {
        Intrinsics.checkNotNullParameter(projectEnvironment, "$projectEnvironment");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "$compilerConfiguration");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        return new ReplInterpreter(projectEnvironment, compilerConfiguration, this$0.replConfiguration);
    }

    private final ReplInterpreter getReplInterpreter() throws ExecutionException, InterruptedException {
        ReplInterpreter replInterpreter = this.replInitializer.get();
        Intrinsics.checkNotNullExpressionValue(replInterpreter, "get(...)");
        return replInterpreter;
    }

    private final ReplWriter getWriter() {
        return this.replConfiguration.getWriter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0076, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0077, code lost:
    
        r3.replConfiguration.getExceptionReporter().report(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0083, code lost:
    
        throw r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void doRun() throws java.lang.Exception {
        /*
            r3 = this;
            java.lang.String r0 = "Welcome to Kotlin version "
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.writer.ReplWriter r1 = r3.getWriter()     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            java.lang.String r0 = org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            java.lang.String r2 = " (JRE "
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            java.lang.String r2 = "java.runtime.version"
            java.lang.String r2 = java.lang.System.getProperty(r2)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            r2 = 41
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            r1.printlnWelcomeMessage(r0)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            java.lang.String r0 = "Type :help for help, :quit for quit"
            r1.printlnWelcomeMessage(r0)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            org.jetbrains.kotlin.cli.common.messages.MessageCollector r0 = r3.messageCollector     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            boolean r0 = r0.hasErrors()     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            org.jetbrains.kotlin.cli.common.messages.MessageCollector r1 = r3.messageCollector     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            boolean r2 = r1 instanceof org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            if (r2 == 0) goto L42
            org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector r1 = (org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector) r1     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            goto L43
        L42:
            r1 = 0
        L43:
            if (r1 == 0) goto L48
            r1.flush()     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
        L48:
            if (r0 == 0) goto L62
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration r0 = r3.replConfiguration     // Catch: java.lang.Exception -> L54
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.ReplCommandReader r0 = r0.getCommandReader()     // Catch: java.lang.Exception -> L54
            r0.flushHistory()     // Catch: java.lang.Exception -> L54
            return
        L54:
            r0 = move-exception
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration r1 = r3.replConfiguration
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplExceptionReporter r1 = r1.getExceptionReporter()
            r2 = r0
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r1.report(r2)
            throw r0
        L62:
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal$WhatNextAfterOneLine r0 = org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal.WhatNextAfterOneLine.READ_LINE     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
        L64:
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal$WhatNextAfterOneLine r0 = r3.one(r0)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal$WhatNextAfterOneLine r1 = org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal.WhatNextAfterOneLine.QUIT     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L86
            if (r0 != r1) goto L64
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration r0 = r3.replConfiguration     // Catch: java.lang.Exception -> L76
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.ReplCommandReader r0 = r0.getCommandReader()     // Catch: java.lang.Exception -> L76
            r0.flushHistory()     // Catch: java.lang.Exception -> L76
            return
        L76:
            r0 = move-exception
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration r1 = r3.replConfiguration
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplExceptionReporter r1 = r1.getExceptionReporter()
            r2 = r0
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r1.report(r2)
            throw r0
        L84:
            r0 = move-exception
            goto L94
        L86:
            r0 = move-exception
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration r1 = r3.replConfiguration     // Catch: java.lang.Throwable -> L84
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplExceptionReporter r1 = r1.getExceptionReporter()     // Catch: java.lang.Throwable -> L84
            r2 = r0
            java.lang.Throwable r2 = (java.lang.Throwable) r2     // Catch: java.lang.Throwable -> L84
            r1.report(r2)     // Catch: java.lang.Throwable -> L84
            throw r0     // Catch: java.lang.Throwable -> L84
        L94:
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration r1 = r3.replConfiguration     // Catch: java.lang.Exception -> L9e
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.reader.ReplCommandReader r1 = r1.getCommandReader()     // Catch: java.lang.Exception -> L9e
            r1.flushHistory()     // Catch: java.lang.Exception -> L9e
            throw r0
        L9e:
            r0 = move-exception
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration r1 = r3.replConfiguration
            org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplExceptionReporter r1 = r1.getExceptionReporter()
            r2 = r0
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r1.report(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal.doRun():void");
    }

    private final WhatNextAfterOneLine one(WhatNextAfterOneLine next) {
        String line = this.replConfiguration.getCommandReader().readLine(next);
        if (line == null) {
            return WhatNextAfterOneLine.QUIT;
        }
        String strReplUnescapeLineBreaks = ReplUtilKt.replUnescapeLineBreaks(line);
        if (StringsKt.startsWith$default(strReplUnescapeLineBreaks, ":", false, 2, (Object) null) && (strReplUnescapeLineBreaks.length() == 1 || strReplUnescapeLineBreaks.charAt(1) != ':')) {
            String strSubstring = strReplUnescapeLineBreaks.substring(1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return oneCommand(strSubstring) ? WhatNextAfterOneLine.READ_LINE : WhatNextAfterOneLine.QUIT;
        }
        if (eval(strReplUnescapeLineBreaks) instanceof ReplEvalResult.Incomplete) {
            return WhatNextAfterOneLine.INCOMPLETE;
        }
        return WhatNextAfterOneLine.READ_LINE;
    }

    private final String tryInterpretResultAsValueClass(ReplEvalResult.ValueResult evalResult) {
        String type;
        String strSubstringBefore$default;
        ClassLoader classLoader;
        Class<?> clsTryLoadClass;
        Method method;
        Class<?> cls;
        Object value = evalResult.getValue();
        Class<?> cls2 = value != null ? value.getClass() : null;
        String typeName = cls2 != null ? cls2.getTypeName() : null;
        if (typeName == null || (type = evalResult.getType()) == null || (strSubstringBefore$default = StringsKt.substringBefore$default(type, '<', (String) null, 2, (Object) null)) == null || Intrinsics.areEqual(strSubstringBefore$default, typeName)) {
            return null;
        }
        Sequence sequenceGenerateSequence = SequencesKt.generateSequence(strSubstringBefore$default, new Function1<String, String>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplFromTerminal$tryInterpretResultAsValueClass$expectedTypesPossiblyInner$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) it, '.', 0, false, 6, (Object) null);
                if (iLastIndexOf$default <= 0) {
                    return null;
                }
                StringBuilder sb = new StringBuilder();
                String strSubstring = it.substring(0, iLastIndexOf$default);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                sb.append(strSubstring);
                sb.append('$');
                String strSubstring2 = it.substring(iLastIndexOf$default + 1);
                Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                sb.append(strSubstring2);
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        });
        Object snippetInstance = evalResult.getSnippetInstance();
        if ((snippetInstance == null || (cls = snippetInstance.getClass()) == null || (classLoader = cls.getClassLoader()) == null) && (classLoader = cls2.getClassLoader()) == null) {
            classLoader = ReplFromTerminal.class.getClassLoader();
        }
        Iterator it = sequenceGenerateSequence.iterator();
        do {
            if (!it.hasNext()) {
                clsTryLoadClass = null;
                break;
            }
            String str = (String) it.next();
            Intrinsics.checkNotNull(classLoader);
            clsTryLoadClass = ReflectJavaClassFinderKt.tryLoadClass(classLoader, str);
        } while (clsTryLoadClass == null);
        if (clsTryLoadClass == null) {
            return null;
        }
        Method[] declaredMethods = clsTryLoadClass.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "getDeclaredMethods(...)");
        Method[] methodArr = declaredMethods;
        int length = methodArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                method = null;
                break;
            }
            method = methodArr[i];
            if (Intrinsics.areEqual(method.getName(), "box-impl")) {
                break;
            }
            i++;
        }
        Method method2 = method;
        if (method2 == null) {
            return null;
        }
        try {
            return evalResult.getName() + ": " + evalResult.getType() + " = " + method2.invoke(null, evalResult.getValue()).toString();
        } catch (Throwable unused) {
            return null;
        }
    }

    private final ReplEvalResult eval(String line) {
        ReplEvalResult replEvalResultEval = getReplInterpreter().eval(line);
        boolean z = replEvalResultEval instanceof ReplEvalResult.ValueResult;
        if (z ? true : replEvalResultEval instanceof ReplEvalResult.UnitResult) {
            getWriter().notifyCommandSuccess();
            if (z) {
                ReplWriter writer = getWriter();
                String strTryInterpretResultAsValueClass = tryInterpretResultAsValueClass((ReplEvalResult.ValueResult) replEvalResultEval);
                if (strTryInterpretResultAsValueClass == null) {
                    strTryInterpretResultAsValueClass = replEvalResultEval.toString();
                }
                writer.outputCommandResult(strTryInterpretResultAsValueClass);
            }
        } else if (replEvalResultEval instanceof ReplEvalResult.Error.Runtime) {
            ReplEvalResult.Error.Runtime runtime = (ReplEvalResult.Error.Runtime) replEvalResultEval;
            if (runtime.getMessage().length() > 0) {
                getWriter().outputRuntimeError(runtime.getMessage());
            }
            getWriter().notifyErrorsReported();
        } else if (replEvalResultEval instanceof ReplEvalResult.Error.CompileTime) {
            ReplEvalResult.Error.CompileTime compileTime = (ReplEvalResult.Error.CompileTime) replEvalResultEval;
            if (compileTime.getMessage().length() > 0) {
                getWriter().outputCompileError(compileTime.getMessage());
            }
            getWriter().notifyErrorsReported();
        } else if (replEvalResultEval instanceof ReplEvalResult.Incomplete) {
            getWriter().notifyIncomplete();
        } else if (replEvalResultEval instanceof ReplEvalResult.HistoryMismatch) {
            getWriter().notifyErrorsReported();
        }
        return replEvalResultEval;
    }

    private final boolean oneCommand(String command) throws Exception {
        List listSplitCommand = INSTANCE.splitCommand(command);
        List list = listSplitCommand;
        if (!list.isEmpty() && Intrinsics.areEqual(command, "help")) {
            getWriter().printlnHelpMessage("Available commands:\n:help                   show this help\n:quit                   exit the interpreter\n:dump bytecode          dump classes to terminal\n:load <file>            load script from specified file");
            return true;
        }
        if (listSplitCommand.size() >= 2 && Intrinsics.areEqual(listSplitCommand.get(0), ArchiveStreamFactory.DUMP) && Intrinsics.areEqual(listSplitCommand.get(1), "bytecode")) {
            getReplInterpreter().dumpClasses(new PrintWriter(System.out));
            return true;
        }
        if (!list.isEmpty() && Intrinsics.areEqual(listSplitCommand.get(0), "quit")) {
            return false;
        }
        if (listSplitCommand.size() >= 2 && Intrinsics.areEqual(listSplitCommand.get(0), "load")) {
            try {
                String strLoadFile = FileUtil.loadFile(new File((String) listSplitCommand.get(1)));
                Intrinsics.checkNotNullExpressionValue(strLoadFile, "loadFile(...)");
                eval(strLoadFile);
            } catch (IOException e) {
                getWriter().outputCompileError("Can not load script: " + e.getMessage());
            }
            return true;
        }
        getWriter().printlnHelpMessage("Unknown command\nType :help for help");
        return true;
    }

    /* JADX INFO: compiled from: ReplFromTerminal.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\u000bH\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplFromTerminal$Companion;", "", "()V", "run", "", "projectEnvironment", "Lorg/jetbrains/kotlin/com/intellij/core/JavaCoreProjectEnvironment;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "splitCommand", "", "", "command", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<String> splitCommand(String command) {
            List listEmptyList;
            List<String> listSplit = new Regex(" ").split(command, 0);
            if (!listSplit.isEmpty()) {
                ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
                while (listIterator.hasPrevious()) {
                    if (listIterator.previous().length() != 0) {
                        listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                    }
                }
                listEmptyList = CollectionsKt.emptyList();
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            String[] strArr = (String[]) listEmptyList.toArray(new String[0]);
            return CollectionsKt.listOf(Arrays.copyOf(strArr, strArr.length));
        }

        public final void run(JavaCoreProjectEnvironment projectEnvironment, CompilerConfiguration configuration) throws Exception {
            Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            ReplConfiguration ideReplConfiguration = Intrinsics.areEqual(System.getProperty("kotlin.repl.ideMode"), "true") ? new IdeReplConfiguration() : new ConsoleReplConfiguration();
            try {
                new ReplFromTerminal(projectEnvironment, configuration, ideReplConfiguration).doRun();
            } catch (Exception e) {
                ideReplConfiguration.getExceptionReporter().report(e);
                throw e;
            }
        }
    }
}
