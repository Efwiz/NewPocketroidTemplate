package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.jvm.ReflectJvmMapping;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.jvm.util.SourceCodeUtilsKt;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.cli.common.arguments.Argument;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderer;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: errorReporting.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0003\u001a'\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00120\u0011\"\u00020\u0012¢\u0006\u0002\u0010\u0013\u001a \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0019H\u0000\u001a \u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0019H\u0000\u001a \u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0019H\u0000\u001a]\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192&\u0010\u001f\u001a\u0014\u0012\u0010\b\u0001\u0012\f\u0012\u0004\u0012\u00020\u0017\u0012\u0002\b\u00030 0\u0011\"\f\u0012\u0004\u0012\u00020\u0017\u0012\u0002\b\u00030 H\u0002¢\u0006\u0002\u0010!\u001a\u0012\u0010\"\u001a\u00020\u0012*\u00020#2\u0006\u0010$\u001a\u00020\f\u001a\u0018\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00120&*\u00020'2\u0006\u0010$\u001a\u00020\f\u001a\f\u0010(\u001a\u00020\u001e*\u00020\u0002H\u0002\u001a\u000e\u0010)\u001a\u0004\u0018\u00010\u0002*\u00020\u001eH\u0002\"+\u0010\u0000\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001j\u0002`\u0005*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006*"}, d2 = {"reporter", "Lkotlin/Function2;", "Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;", "", "", "Lorg/jetbrains/kotlin/scripting/definitions/MessageReporter;", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "getReporter", "(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)Lkotlin/jvm/functions/Function2;", "failure", "Lkotlin/script/experimental/api/ResultWithDiagnostics$Failure;", "script", "Lkotlin/script/experimental/api/SourceCode;", "messageCollector", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ScriptDiagnosticsMessageCollector;", CrashHianalyticsData.MESSAGE, "diagnostics", "", "Lkotlin/script/experimental/api/ScriptDiagnostic;", "(Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ScriptDiagnosticsMessageCollector;[Lkotlin/script/experimental/api/ScriptDiagnostic;)Lkotlin/script/experimental/api/ResultWithDiagnostics$Failure;", "reportArgumentsIgnoredFromRefinement", "", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "reportingState", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/IgnoredOptionsReportingState;", "reportArgumentsIgnoredGenerally", "reportArgumentsNotAllowed", "reportInvalidArguments", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", "toIgnore", "Lkotlin/reflect/KMutableProperty1;", "(Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/IgnoredOptionsReportingState;[Lkotlin/reflect/KMutableProperty1;)Z", "asScriptDiagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "sourceCode", "scriptDiagnostics", "", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "toCompilerMessageSeverity", "toScriptingSeverity", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ErrorReportingKt {

    /* JADX INFO: compiled from: errorReporting.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

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
            try {
                iArr[CompilerMessageSeverity.LOGGING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[CompilerMessageSeverity.OUTPUT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ScriptDiagnostic.Severity.values().length];
            try {
                iArr2[ScriptDiagnostic.Severity.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[ScriptDiagnostic.Severity.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[ScriptDiagnostic.Severity.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[ScriptDiagnostic.Severity.DEBUG.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[ScriptDiagnostic.Severity.FATAL.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[Severity.values().length];
            try {
                iArr3[Severity.INFO.ordinal()] = 1;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr3[Severity.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr3[Severity.WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused15) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ScriptDiagnostic.Severity toScriptingSeverity(CompilerMessageSeverity compilerMessageSeverity) {
        switch (WhenMappings.$EnumSwitchMapping$0[compilerMessageSeverity.ordinal()]) {
            case 1:
            case 2:
                return ScriptDiagnostic.Severity.ERROR;
            case 3:
            case 4:
                return ScriptDiagnostic.Severity.WARNING;
            case 5:
                return ScriptDiagnostic.Severity.INFO;
            case 6:
                return ScriptDiagnostic.Severity.DEBUG;
            case 7:
                return null;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CompilerMessageSeverity toCompilerMessageSeverity(ScriptDiagnostic.Severity severity) {
        int i = WhenMappings.$EnumSwitchMapping$1[severity.ordinal()];
        if (i == 1) {
            return CompilerMessageSeverity.ERROR;
        }
        if (i == 2) {
            return CompilerMessageSeverity.WARNING;
        }
        if (i == 3) {
            return CompilerMessageSeverity.INFO;
        }
        if (i == 4) {
            return CompilerMessageSeverity.LOGGING;
        }
        if (i == 5) {
            return CompilerMessageSeverity.EXCEPTION;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final ResultWithDiagnostics.Failure failure(ScriptDiagnosticsMessageCollector messageCollector, ScriptDiagnostic... diagnostics) {
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        SpreadBuilder spreadBuilder = new SpreadBuilder(2);
        spreadBuilder.addSpread(messageCollector.getDiagnostics().toArray(new ScriptDiagnostic[0]));
        spreadBuilder.addSpread(diagnostics);
        return new ResultWithDiagnostics.Failure((ScriptDiagnostic[]) spreadBuilder.toArray(new ScriptDiagnostic[spreadBuilder.size()]));
    }

    public static final ResultWithDiagnostics.Failure failure(SourceCode script, ScriptDiagnosticsMessageCollector messageCollector, String message) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(message, "message");
        return failure(messageCollector, ErrorHandlingKt.asErrorDiagnostics$default(message, 0, script.getLocationId(), null, 5, null));
    }

    public static final boolean reportArgumentsNotAllowed(K2JVMCompilerArguments arguments, MessageCollector messageCollector, IgnoredOptionsReportingState reportingState) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(reportingState, "reportingState");
        return reportInvalidArguments(arguments, "The following compiler arguments are not allowed in the script compilation configuration: ", CompilerMessageSeverity.ERROR, messageCollector, reportingState, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsNotAllowed.1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getUseJavac()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getUseJavac());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "useJavac";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setUseJavac(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsNotAllowed.2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getUseK2()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getUseK2());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "useK2";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setUseK2(((Boolean) obj2).booleanValue());
            }
        });
    }

    public static final boolean reportArgumentsIgnoredGenerally(K2JVMCompilerArguments arguments, MessageCollector messageCollector, IgnoredOptionsReportingState reportingState) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(reportingState, "reportingState");
        return reportInvalidArguments(arguments, "The following compiler arguments are ignored on script compilation: ", CompilerMessageSeverity.STRONG_WARNING, messageCollector, reportingState, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getVersion()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getVersion());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "version";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setVersion(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getDestination()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getDestination();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return FirebaseAnalytics.Param.DESTINATION;
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setDestination((String) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.3
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getBuildFile()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getBuildFile();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "buildFile";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setBuildFile((String) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.4
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getCommonSources()[Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getCommonSources();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return ModuleXmlParser.COMMON_SOURCES;
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setCommonSources((String[]) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.5
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getAllWarningsAsErrors()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getAllWarningsAsErrors());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "allWarningsAsErrors";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setAllWarningsAsErrors(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.6
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getScript()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getScript());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "script";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setScript(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.7
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getExpression()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getExpression();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "expression";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setExpression((String) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.8
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getScriptTemplates()[Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getScriptTemplates();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "scriptTemplates";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setScriptTemplates((String[]) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.9
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getScriptResolverEnvironment()[Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getScriptResolverEnvironment();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "scriptResolverEnvironment";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setScriptResolverEnvironment((String[]) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.10
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getDisableStandardScript()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getDisableStandardScript());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "disableStandardScript";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setDisableStandardScript(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.11
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getDefaultScriptExtension()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getDefaultScriptExtension();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "defaultScriptExtension";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setDefaultScriptExtension((String) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.12
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getDisableDefaultScriptingPlugin()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getDisableDefaultScriptingPlugin());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "disableDefaultScriptingPlugin";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setDisableDefaultScriptingPlugin(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.13
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getUseJavac()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getUseJavac());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "useJavac";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setUseJavac(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.14
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getCompileJava()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getCompileJava());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "compileJava";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setCompileJava(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.15
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getReportPerf()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getReportPerf());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "reportPerf";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setReportPerf(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredGenerally.16
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getDumpPerf()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getDumpPerf();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "dumpPerf";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setDumpPerf((String) obj2);
            }
        });
    }

    public static final boolean reportArgumentsIgnoredFromRefinement(K2JVMCompilerArguments arguments, MessageCollector messageCollector, IgnoredOptionsReportingState reportingState) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(reportingState, "reportingState");
        return reportInvalidArguments(arguments, "The following compiler arguments are ignored when configured from refinement callbacks: ", CompilerMessageSeverity.STRONG_WARNING, messageCollector, reportingState, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredFromRefinement.1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getNoJdk()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getNoJdk());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "noJdk";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setNoJdk(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredFromRefinement.2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getJdkHome()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getJdkHome();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "jdkHome";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setJdkHome((String) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredFromRefinement.3
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getJavaModulePath()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getJavaModulePath();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "javaModulePath";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setJavaModulePath((String) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredFromRefinement.4
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getClasspath()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getClasspath();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return ModuleXmlParser.CLASSPATH;
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setClasspath((String) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredFromRefinement.5
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getNoStdlib()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getNoStdlib());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "noStdlib";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setNoStdlib(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredFromRefinement.6
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getNoReflect()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((K2JVMCompilerArguments) obj).getNoReflect());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "noReflect";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setNoReflect(((Boolean) obj2).booleanValue());
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredFromRefinement.7
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getPluginClasspaths()[Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getPluginClasspaths();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "pluginClasspaths";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setPluginClasspaths((String[]) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredFromRefinement.8
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getPluginOptions()[Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getPluginOptions();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "pluginOptions";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setPluginOptions((String[]) obj2);
            }
        }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt.reportArgumentsIgnoredFromRefinement.9
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getPluginConfigurations()[Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getPluginConfigurations();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "pluginConfigurations";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setPluginConfigurations((String[]) obj2);
            }
        });
    }

    public static final Function2<ScriptDiagnostic.Severity, String, Unit> getReporter(final MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(messageCollector, "<this>");
        return new Function2<ScriptDiagnostic.Severity, String, Unit>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.ErrorReportingKt$reporter$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(ScriptDiagnostic.Severity severity, String str) {
                invoke2(severity, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptDiagnostic.Severity severity, String message) {
                Intrinsics.checkNotNullParameter(severity, "severity");
                Intrinsics.checkNotNullParameter(message, "message");
                MessageCollector.report$default(messageCollector, ErrorReportingKt.toCompilerMessageSeverity(severity), message, null, 4, null);
            }
        };
    }

    public static final ScriptDiagnostic asScriptDiagnostic(KtDiagnostic ktDiagnostic, SourceCode sourceCode) {
        Pair pair;
        SourceCode.LocationWithId locationWithId;
        PsiFile containingFile;
        VirtualFile virtualFile;
        Intrinsics.checkNotNullParameter(ktDiagnostic, "<this>");
        Intrinsics.checkNotNullParameter(sourceCode, "sourceCode");
        int i = WhenMappings.$EnumSwitchMapping$2[ktDiagnostic.getSeverity().ordinal()];
        if (i == 1) {
            pair = TuplesKt.to(0, ScriptDiagnostic.Severity.INFO);
        } else if (i == 2) {
            pair = TuplesKt.to(-1, ScriptDiagnostic.Severity.ERROR);
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            pair = TuplesKt.to(0, ScriptDiagnostic.Severity.WARNING);
        }
        int iIntValue = ((Number) pair.component1()).intValue();
        ScriptDiagnostic.Severity severity = (ScriptDiagnostic.Severity) pair.component2();
        KtDiagnosticRenderer ktDiagnosticRendererInvoke = RootDiagnosticRendererFactory.INSTANCE.invoke(ktDiagnostic);
        String path = null;
        if (ktDiagnostic.getTextRanges().isEmpty()) {
            locationWithId = null;
        } else {
            TextRange textRange = (TextRange) CollectionsKt.first((List) ktDiagnostic.getTextRanges());
            TextRange textRange2 = (TextRange) CollectionsKt.last((List) ktDiagnostic.getTextRanges());
            PsiElement psi = KtSourceElementKt.getPsi(ktDiagnostic.getElement());
            if (psi != null && (containingFile = psi.getContainingFile()) != null && (virtualFile = containingFile.getVirtualFile()) != null) {
                path = virtualFile.getPath();
            }
            if (path == null) {
                path = "";
            }
            locationWithId = new SourceCode.LocationWithId(path, new SourceCode.Location(SourceCodeUtilsKt.toSourceCodePosition(textRange.getStartOffset(), sourceCode), SourceCodeUtilsKt.toSourceCodePosition(textRange2.getEndOffset(), sourceCode)));
        }
        return new ScriptDiagnostic(iIntValue, ktDiagnosticRendererInvoke.render(ktDiagnostic), severity, locationWithId, null, 16, null);
    }

    public static final List<ScriptDiagnostic> scriptDiagnostics(BaseDiagnosticsCollector baseDiagnosticsCollector, SourceCode sourceCode) {
        Intrinsics.checkNotNullParameter(baseDiagnosticsCollector, "<this>");
        Intrinsics.checkNotNullParameter(sourceCode, "sourceCode");
        List<KtDiagnostic> diagnostics = baseDiagnosticsCollector.getDiagnostics();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(diagnostics, 10));
        Iterator<T> it = diagnostics.iterator();
        while (it.hasNext()) {
            arrayList.add(asScriptDiagnostic((KtDiagnostic) it.next(), sourceCode));
        }
        return arrayList;
    }

    private static final boolean reportInvalidArguments(K2JVMCompilerArguments k2JVMCompilerArguments, String str, CompilerMessageSeverity compilerMessageSeverity, MessageCollector messageCollector, IgnoredOptionsReportingState ignoredOptionsReportingState, KMutableProperty1<K2JVMCompilerArguments, ?>... kMutableProperty1Arr) {
        String strValue;
        Argument argument;
        ArrayList arrayList = new ArrayList();
        for (KMutableProperty1<K2JVMCompilerArguments, ?> kMutableProperty1 : kMutableProperty1Arr) {
            if (Intrinsics.areEqual(kMutableProperty1.get(k2JVMCompilerArguments), kMutableProperty1.get(ignoredOptionsReportingState.getCurrentArguments()))) {
                strValue = null;
            } else {
                Field javaField = ReflectJvmMapping.getJavaField(kMutableProperty1);
                if (javaField == null || (argument = (Argument) javaField.getAnnotation(Argument.class)) == null || (strValue = argument.value()) == null) {
                    throw new IllegalStateException("unknown compiler argument property: " + kMutableProperty1 + ": no Argument annotation found");
                }
            }
            if (strValue != null) {
                arrayList.add(strValue);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return false;
        }
        MessageCollector.report$default(messageCollector, compilerMessageSeverity, str + CollectionsKt.joinToString$default(arrayList2, ", ", null, null, 0, null, null, 62, null), null, 4, null);
        return true;
    }
}
