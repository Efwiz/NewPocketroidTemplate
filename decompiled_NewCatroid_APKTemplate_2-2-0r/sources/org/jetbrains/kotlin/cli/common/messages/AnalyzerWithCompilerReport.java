package org.jetbrains.kotlin.cli.common.messages;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.AbstractAnalyzerWithCompilerReport;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiFormatUtil;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtils;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.diagnostics.GenericDiagnostics;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.diagnostics.SimpleDiagnostic;
import org.jetbrains.kotlin.diagnostics.UnboundDiagnostic;
import org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages;
import org.jetbrains.kotlin.load.java.components.TraceBasedErrorReporter;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.AnalyzingUtils;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.CompilerEnvironment;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.TargetEnvironment;
import org.jetbrains.kotlin.resolve.checkers.OptInUsageChecker;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.resolve.jvm.JvmBindingContextSlices;

/* JADX INFO: compiled from: AnalyzerWithCompilerReport.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 !2\u00020\u0001:\u0003!\"#B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ$\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\r0\u001cH\u0016J\b\u0010\u001d\u001a\u00020\nH\u0016J\b\u0010\u001e\u001a\u00020\u0017H\u0002J\b\u0010\u001f\u001a\u00020\u0017H\u0002J\u0016\u0010 \u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0002R\u001a\u0010\f\u001a\u00020\rX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/AnalyzerWithCompilerReport;", "Lorg/jetbrains/kotlin/analyzer/AbstractAnalyzerWithCompilerReport;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)V", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "renderDiagnosticName", "", "(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Z)V", "analysisResult", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "getAnalysisResult", "()Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "setAnalysisResult", "(Lorg/jetbrains/kotlin/analyzer/AnalysisResult;)V", "targetEnvironment", "Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "getTargetEnvironment", "()Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "analyzeAndReport", "", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "analyze", "Lkotlin/Function0;", "hasErrors", "reportAlternativeSignatureErrors", "reportIncompleteHierarchies", "reportSyntaxErrors", "Companion", "MyDiagnostic", "SyntaxErrorReport", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnalyzerWithCompilerReport implements AbstractAnalyzerWithCompilerReport {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final DiagnosticFactory0<PsiErrorElement> SYNTAX_ERROR_FACTORY;
    public AnalysisResult analysisResult;
    private final LanguageVersionSettings languageVersionSettings;
    private final MessageCollector messageCollector;
    private final boolean renderDiagnosticName;

    public AnalyzerWithCompilerReport(MessageCollector messageCollector, LanguageVersionSettings languageVersionSettings, boolean z) {
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.messageCollector = messageCollector;
        this.languageVersionSettings = languageVersionSettings;
        this.renderDiagnosticName = z;
    }

    @Override // org.jetbrains.kotlin.analyzer.AbstractAnalyzerWithCompilerReport
    public TargetEnvironment getTargetEnvironment() {
        return CompilerEnvironment.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.analyzer.AbstractAnalyzerWithCompilerReport
    public AnalysisResult getAnalysisResult() {
        AnalysisResult analysisResult = this.analysisResult;
        if (analysisResult != null) {
            return analysisResult;
        }
        Intrinsics.throwUninitializedPropertyAccessException("analysisResult");
        return null;
    }

    public void setAnalysisResult(AnalysisResult analysisResult) {
        Intrinsics.checkNotNullParameter(analysisResult, "<set-?>");
        this.analysisResult = analysisResult;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AnalyzerWithCompilerReport(CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        MessageCollector none = (MessageCollector) configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        none = none == null ? MessageCollector.INSTANCE.getNONE() : none;
        Intrinsics.checkNotNull(none);
        this(none, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), configuration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
    }

    private final void reportIncompleteHierarchies() {
        BindingContext bindingContext = getAnalysisResult().getBindingContext();
        Collection<ClassDescriptor> keys = bindingContext.getKeys(TraceBasedErrorReporter.INCOMPLETE_HIERARCHY);
        Intrinsics.checkNotNullExpressionValue(keys, "getKeys(...)");
        if (keys.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder("Supertypes of the following classes cannot be resolved. Please make sure you have the required dependencies in the classpath:\n");
        for (ClassDescriptor classDescriptor : keys) {
            String strAsString = DescriptorUtils.getFqName(classDescriptor).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            List list = (List) bindingContext.get(TraceBasedErrorReporter.INCOMPLETE_HIERARCHY, classDescriptor);
            if (list != null) {
                list.isEmpty();
            }
            StringBuilder sbAppend = sb.append("    class ").append(strAsString).append(", unresolved supertypes: ");
            Intrinsics.checkNotNull(list);
            sbAppend.append(CollectionsKt.joinToString$default(list, null, null, null, 0, null, null, 63, null)).append("\n");
        }
        if (!((Boolean) this.languageVersionSettings.getFlag(AnalysisFlags.getExtendedCompilerChecks())).booleanValue()) {
            sb.append("Adding -Xextended-compiler-checks argument might provide additional information.\n");
        }
        MessageCollector messageCollector = this.messageCollector;
        CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.ERROR;
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        MessageCollector.report$default(messageCollector, compilerMessageSeverity, string, null, 4, null);
    }

    private final void reportAlternativeSignatureErrors() {
        BindingContext bindingContext = getAnalysisResult().getBindingContext();
        Collection<DeclarationDescriptor> keys = bindingContext.getKeys(JvmBindingContextSlices.LOAD_FROM_JAVA_SIGNATURE_ERRORS);
        Intrinsics.checkNotNullExpressionValue(keys, "getKeys(...)");
        if (keys.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder("The following Java entities have annotations with wrong Kotlin signatures:\n");
        for (DeclarationDescriptor declarationDescriptor : keys) {
            Intrinsics.checkNotNull(declarationDescriptor);
            PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(declarationDescriptor);
            boolean z = psiElementDescriptorToDeclaration instanceof PsiModifierListOwner;
            List list = (List) bindingContext.get(JvmBindingContextSlices.LOAD_FROM_JAVA_SIGNATURE_ERRORS, declarationDescriptor);
            if (list != null) {
                list.isEmpty();
            }
            Intrinsics.checkNotNull(psiElementDescriptorToDeclaration, "null cannot be cast to non-null type com.intellij.psi.PsiModifierListOwner");
            sb.append(PsiFormatUtil.getExternalName((PsiModifierListOwner) psiElementDescriptorToDeclaration)).append(":\n");
            Intrinsics.checkNotNull(list);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                sb.append("    ").append((String) it.next()).append("\n");
            }
        }
        MessageCollector messageCollector = this.messageCollector;
        CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.ERROR;
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        MessageCollector.report$default(messageCollector, compilerMessageSeverity, string, null, 4, null);
    }

    private final void reportSyntaxErrors(Collection<? extends KtFile> files) {
        Iterator<? extends KtFile> it = files.iterator();
        while (it.hasNext()) {
            INSTANCE.reportSyntaxErrors(it.next(), this.messageCollector);
        }
    }

    /* JADX INFO: compiled from: AnalyzerWithCompilerReport.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/AnalyzerWithCompilerReport$SyntaxErrorReport;", "", "isHasErrors", "", "isAllErrorsAtEof", "(ZZ)V", "()Z", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SyntaxErrorReport {
        private final boolean isAllErrorsAtEof;
        private final boolean isHasErrors;

        public SyntaxErrorReport(boolean z, boolean z2) {
            this.isHasErrors = z;
            this.isAllErrorsAtEof = z2;
        }

        /* JADX INFO: renamed from: isAllErrorsAtEof, reason: from getter */
        public final boolean getIsAllErrorsAtEof() {
            return this.isAllErrorsAtEof;
        }

        /* JADX INFO: renamed from: isHasErrors, reason: from getter */
        public final boolean getIsHasErrors() {
            return this.isHasErrors;
        }
    }

    @Override // org.jetbrains.kotlin.analyzer.AbstractAnalyzerWithCompilerReport
    public boolean hasErrors() {
        return this.messageCollector.getHasErrors();
    }

    @Override // org.jetbrains.kotlin.analyzer.AbstractAnalyzerWithCompilerReport
    public void analyzeAndReport(Collection<? extends KtFile> files, Function0<? extends AnalysisResult> analyze) {
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(analyze, "analyze");
        setAnalysisResult(analyze.invoke());
        if (!getAnalysisResult().isError()) {
            OptInUsageChecker.INSTANCE.checkCompilerArguments(getAnalysisResult().getModuleDescriptor(), this.languageVersionSettings, new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport.analyzeAndReport.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String message) {
                    Intrinsics.checkNotNullParameter(message, "message");
                    MessageCollector.report$default(AnalyzerWithCompilerReport.this.messageCollector, CompilerMessageSeverity.ERROR, message, null, 4, null);
                }
            }, new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport.analyzeAndReport.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String message) {
                    Intrinsics.checkNotNullParameter(message, "message");
                    MessageCollector.report$default(AnalyzerWithCompilerReport.this.messageCollector, CompilerMessageSeverity.WARNING, message, null, 4, null);
                }
            });
        }
        reportSyntaxErrors(files);
        Companion companion = INSTANCE;
        Diagnostics diagnostics = getAnalysisResult().getBindingContext().getDiagnostics();
        Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
        companion.reportDiagnostics(diagnostics, this.messageCollector, this.renderDiagnosticName);
        reportIncompleteHierarchies();
        reportAlternativeSignatureErrors();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AnalyzerWithCompilerReport.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B#\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0014\u0010\n\u001a\u00020\u000bX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/AnalyzerWithCompilerReport$MyDiagnostic;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/diagnostics/SimpleDiagnostic;", "psiElement", "factory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;", CrashHianalyticsData.MESSAGE, "", "(Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;Ljava/lang/String;)V", "isValid", "", "()Z", "getMessage", "()Ljava/lang/String;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class MyDiagnostic<E extends PsiElement> extends SimpleDiagnostic<E> {
        private final boolean isValid;
        private final String message;

        public final String getMessage() {
            return this.message;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MyDiagnostic(E psiElement, DiagnosticFactory0<E> factory, String message) {
            super(psiElement, factory, Severity.ERROR);
            Intrinsics.checkNotNullParameter(psiElement, "psiElement");
            Intrinsics.checkNotNullParameter(factory, "factory");
            Intrinsics.checkNotNullParameter(message, "message");
            this.message = message;
            this.isValid = true;
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractDiagnostic, org.jetbrains.kotlin.diagnostics.UnboundDiagnostic
        /* JADX INFO: renamed from: isValid, reason: from getter */
        public boolean getIsValid() {
            return this.isValid;
        }
    }

    /* JADX INFO: compiled from: AnalyzerWithCompilerReport.kt */
    @Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u0002J\"\u0010\u0012\u001a\u00020\f2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fJ\"\u0010\u0012\u001a\u00020\f2\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\fJ\u0016\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u0017R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/AnalyzerWithCompilerReport$Companion;", "", "()V", "SYNTAX_ERROR_FACTORY", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiErrorElement;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "convertSeverity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "reportDiagnostic", "", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "reporter", "Lorg/jetbrains/kotlin/cli/common/messages/DiagnosticMessageReporter;", "renderDiagnosticName", "reportDiagnostics", "unsortedDiagnostics", "Lorg/jetbrains/kotlin/diagnostics/GenericDiagnostics;", "diagnostics", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "renderInternalDiagnosticName", "reportSyntaxErrors", "Lorg/jetbrains/kotlin/cli/common/messages/AnalyzerWithCompilerReport$SyntaxErrorReport;", "file", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: AnalyzerWithCompilerReport.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Severity.values().length];
                try {
                    iArr[Severity.INFO.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Severity.ERROR.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[Severity.WARNING.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CompilerMessageSeverity convertSeverity(Severity severity) {
            Intrinsics.checkNotNullParameter(severity, "severity");
            int i = WhenMappings.$EnumSwitchMapping$0[severity.ordinal()];
            if (i == 1) {
                return CompilerMessageSeverity.INFO;
            }
            if (i == 2) {
                return CompilerMessageSeverity.ERROR;
            }
            if (i == 3) {
                return CompilerMessageSeverity.WARNING;
            }
            throw new IllegalStateException("Unknown severity: " + severity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean reportDiagnostic(Diagnostic diagnostic, DiagnosticMessageReporter reporter, boolean renderDiagnosticName) {
            String strRender;
            if (!diagnostic.getIsValid()) {
                return false;
            }
            MyDiagnostic myDiagnostic = diagnostic instanceof MyDiagnostic ? (MyDiagnostic) diagnostic : null;
            if (myDiagnostic == null || (strRender = myDiagnostic.getMessage()) == null) {
                strRender = DefaultErrorMessages.render(diagnostic);
                Intrinsics.checkNotNullExpressionValue(strRender, "render(...)");
            }
            if (renderDiagnosticName) {
                strRender = "[" + diagnostic.getFactoryName() + "] " + strRender;
            } else if (renderDiagnosticName) {
                throw new NoWhenBranchMatchedException();
            }
            reporter.report(diagnostic, diagnostic.getPsiFile(), strRender);
            return diagnostic.getSeverity() == Severity.ERROR;
        }

        public final boolean reportDiagnostics(GenericDiagnostics<?> unsortedDiagnostics, DiagnosticMessageReporter reporter, boolean renderDiagnosticName) {
            Intrinsics.checkNotNullParameter(unsortedDiagnostics, "unsortedDiagnostics");
            Intrinsics.checkNotNullParameter(reporter, "reporter");
            Iterable iterableAll = unsortedDiagnostics.all();
            ArrayList arrayList = new ArrayList();
            for (Object obj : iterableAll) {
                if (obj instanceof Diagnostic) {
                    arrayList.add(obj);
                }
            }
            List<Diagnostic> listSortedDiagnostics = DiagnosticUtils.sortedDiagnostics(arrayList);
            Intrinsics.checkNotNullExpressionValue(listSortedDiagnostics, "sortedDiagnostics(...)");
            boolean zReportDiagnostic = false;
            for (Diagnostic diagnostic : listSortedDiagnostics) {
                Intrinsics.checkNotNull(diagnostic);
                zReportDiagnostic |= reportDiagnostic(diagnostic, reporter, renderDiagnosticName);
            }
            return zReportDiagnostic;
        }

        public final boolean reportDiagnostics(GenericDiagnostics<?> diagnostics, MessageCollector messageCollector, boolean renderInternalDiagnosticName) {
            Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
            Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
            boolean zReportDiagnostics = reportDiagnostics(diagnostics, new DefaultDiagnosticReporter(messageCollector), renderInternalDiagnosticName);
            GenericDiagnostics<?> genericDiagnostics = diagnostics;
            boolean z = genericDiagnostics instanceof Collection;
            if (!z || !((Collection) genericDiagnostics).isEmpty()) {
                Iterator<?> it = genericDiagnostics.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(((UnboundDiagnostic) it.next()).getFactory(), Errors.INCOMPATIBLE_CLASS)) {
                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Incompatible classes were found in dependencies. Remove them from the classpath or use '-Xskip-metadata-version-check' to suppress errors", null, 4, null);
                        break;
                    }
                }
            }
            if (!z || !((Collection) genericDiagnostics).isEmpty()) {
                Iterator<?> it2 = genericDiagnostics.iterator();
                while (it2.hasNext()) {
                    if (Intrinsics.areEqual(((UnboundDiagnostic) it2.next()).getFactory(), Errors.PRE_RELEASE_CLASS)) {
                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Pre-release classes were found in dependencies. Remove them from the classpath, recompile with a release compiler or use '-Xskip-prerelease-check' to suppress errors", null, 4, null);
                        break;
                    }
                }
            }
            if (!z || !((Collection) genericDiagnostics).isEmpty()) {
                Iterator<?> it3 = genericDiagnostics.iterator();
                while (it3.hasNext()) {
                    if (Intrinsics.areEqual(((UnboundDiagnostic) it3.next()).getFactory(), Errors.IR_WITH_UNSTABLE_ABI_COMPILED_CLASS)) {
                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Classes compiled by an unstable version of the Kotlin compiler were found in dependencies. Remove them from the classpath or use '-Xallow-unstable-dependencies' to suppress errors", null, 4, null);
                        break;
                    }
                }
            }
            if (!z || !((Collection) genericDiagnostics).isEmpty()) {
                Iterator<?> it4 = genericDiagnostics.iterator();
                while (it4.hasNext()) {
                    if (Intrinsics.areEqual(((UnboundDiagnostic) it4.next()).getFactory(), Errors.FIR_COMPILED_CLASS)) {
                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Classes compiled by the new Kotlin compiler frontend were found in dependencies. Remove them from the classpath or use '-Xallow-unstable-dependencies' to suppress errors", null, 4, null);
                        break;
                    }
                }
            }
            return zReportDiagnostics;
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport$Companion$reportSyntaxErrors$ErrorReportingVisitor] */
        public final SyntaxErrorReport reportSyntaxErrors(PsiElement file, final DiagnosticMessageReporter reporter) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(reporter, "reporter");
            ?? r0 = new AnalyzingUtils.PsiErrorElementVisitor(reporter) { // from class: org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport$Companion$reportSyntaxErrors$ErrorReportingVisitor
                final /* synthetic */ DiagnosticMessageReporter $reporter;
                private boolean allErrorsAtEof;
                private boolean hasErrors;

                {
                    Intrinsics.checkNotNullParameter(reporter, "$reporter");
                    this.$reporter = reporter;
                    this.allErrorsAtEof = true;
                }

                public final boolean getHasErrors() {
                    return this.hasErrors;
                }

                public final void setHasErrors(boolean z) {
                    this.hasErrors = z;
                }

                public final boolean getAllErrorsAtEof() {
                    return this.allErrorsAtEof;
                }

                public final void setAllErrorsAtEof(boolean z) {
                    this.allErrorsAtEof = z;
                }

                private final <E extends PsiElement> void reportDiagnostic(E e, DiagnosticFactory0<E> diagnosticFactory0, String str) {
                    AnalyzerWithCompilerReport.INSTANCE.reportDiagnostic(new AnalyzerWithCompilerReport.MyDiagnostic(e, diagnosticFactory0, str), this.$reporter, false);
                    if (this.allErrorsAtEof && !isAtEof(e)) {
                        this.allErrorsAtEof = false;
                    }
                    this.hasErrors = true;
                }

                private final boolean isAtEof(PsiElement psiElement) {
                    do {
                        psiElement = psiElement.getNextSibling();
                        if (psiElement == null) {
                            return true;
                        }
                        if (!(psiElement instanceof PsiWhiteSpace)) {
                            return false;
                        }
                    } while (psiElement instanceof PsiComment);
                    return false;
                }

                @Override // org.jetbrains.kotlin.resolve.AnalyzingUtils.PsiErrorElementVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
                public void visitErrorElement(PsiErrorElement element) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    String errorDescription = element.getErrorDescription();
                    Intrinsics.checkNotNullExpressionValue(errorDescription, "getErrorDescription(...)");
                    PsiErrorElement psiErrorElement = element;
                    DiagnosticFactory0 diagnosticFactory0 = AnalyzerWithCompilerReport.SYNTAX_ERROR_FACTORY;
                    if (StringUtil.isEmpty(errorDescription)) {
                        errorDescription = "Syntax error";
                    }
                    reportDiagnostic(psiErrorElement, diagnosticFactory0, errorDescription);
                }
            };
            file.accept((PsiElementVisitor) r0);
            return new SyntaxErrorReport(r0.getHasErrors(), r0.getAllErrorsAtEof());
        }

        public final SyntaxErrorReport reportSyntaxErrors(PsiElement file, MessageCollector messageCollector) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
            return reportSyntaxErrors(file, new DefaultDiagnosticReporter(messageCollector));
        }
    }

    static {
        DiagnosticFactory0<PsiErrorElement> diagnosticFactory0Create = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create, "create(...)");
        SYNTAX_ERROR_FACTORY = diagnosticFactory0Create;
    }
}
