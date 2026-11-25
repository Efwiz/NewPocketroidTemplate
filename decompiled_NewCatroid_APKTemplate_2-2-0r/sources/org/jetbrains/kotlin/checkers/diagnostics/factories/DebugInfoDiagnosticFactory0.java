package org.jetbrains.kotlin.checkers.diagnostics.factories;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.checkers.diagnostics.DebugInfoDiagnostic;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.PositioningStrategies;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;

/* JADX INFO: compiled from: DebugInfoDiagnosticFactory0.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001c2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001\u001cB\u0019\b\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ6\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016R\u0014\u0010\t\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\rX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory0;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory;", "privateName", "", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/Severity;)V", "name", "getName", "()Ljava/lang/String;", "withExplicitDefinitionOnly", "", "getWithExplicitDefinitionOnly", "()Z", "createDiagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DebugInfoDiagnosticFactory0 extends DiagnosticFactory0<PsiElement> implements DebugInfoDiagnosticFactory {
    private static final DebugInfoDiagnosticFactory0 ELEMENT_WITH_ERROR_TYPE;
    private static final DebugInfoDiagnosticFactory0 MISSING_UNRESOLVED;
    private static final DebugInfoDiagnosticFactory0 UNRESOLVED_WITH_TARGET;
    private final String privateName;
    private final boolean withExplicitDefinitionOnly;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final DebugInfoDiagnosticFactory0 SMARTCAST = new DebugInfoDiagnosticFactory0("SMARTCAST", Severity.INFO);
    private static final DebugInfoDiagnosticFactory0 IMPLICIT_RECEIVER_SMARTCAST = new DebugInfoDiagnosticFactory0("IMPLICIT_RECEIVER_SMARTCAST", Severity.INFO);
    private static final DebugInfoDiagnosticFactory0 CONSTANT = new DebugInfoDiagnosticFactory0("CONSTANT", Severity.INFO);
    private static final DebugInfoDiagnosticFactory0 LEAKING_THIS = new DebugInfoDiagnosticFactory0("LEAKING_THIS", Severity.INFO);
    private static final DebugInfoDiagnosticFactory0 IMPLICIT_EXHAUSTIVE = new DebugInfoDiagnosticFactory0("IMPLICIT_EXHAUSTIVE", Severity.INFO);
    private static final DebugInfoDiagnosticFactory0 DYNAMIC = new DebugInfoDiagnosticFactory0("DYNAMIC", Severity.INFO);

    /* synthetic */ DebugInfoDiagnosticFactory0(String str, Severity severity, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? Severity.ERROR : severity);
    }

    private DebugInfoDiagnosticFactory0(String str, Severity severity) {
        super(severity, PositioningStrategies.DEFAULT);
        this.privateName = str;
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.factories.DebugInfoDiagnosticFactory
    public boolean getWithExplicitDefinitionOnly() {
        return this.withExplicitDefinitionOnly;
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.factories.DebugInfoDiagnosticFactory
    public Diagnostic createDiagnostic(KtElement element, BindingContext bindingContext, DataFlowValueFactory dataFlowValueFactory, LanguageVersionSettings languageVersionSettings, ModuleDescriptorImpl moduleDescriptor) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        return new DebugInfoDiagnostic(element, this);
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticFactory
    public String getName() {
        return "DEBUG_INFO_" + this.privateName;
    }

    /* JADX INFO: compiled from: DebugInfoDiagnosticFactory0.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory0$Companion;", "", "()V", "CONSTANT", "Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory0;", "getCONSTANT", "()Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory0;", "DYNAMIC", "getDYNAMIC", "ELEMENT_WITH_ERROR_TYPE", "getELEMENT_WITH_ERROR_TYPE", "IMPLICIT_EXHAUSTIVE", "getIMPLICIT_EXHAUSTIVE", "IMPLICIT_RECEIVER_SMARTCAST", "getIMPLICIT_RECEIVER_SMARTCAST", "LEAKING_THIS", "getLEAKING_THIS", "MISSING_UNRESOLVED", "getMISSING_UNRESOLVED", "SMARTCAST", "getSMARTCAST", "UNRESOLVED_WITH_TARGET", "getUNRESOLVED_WITH_TARGET", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DebugInfoDiagnosticFactory0 getSMARTCAST() {
            return DebugInfoDiagnosticFactory0.SMARTCAST;
        }

        public final DebugInfoDiagnosticFactory0 getIMPLICIT_RECEIVER_SMARTCAST() {
            return DebugInfoDiagnosticFactory0.IMPLICIT_RECEIVER_SMARTCAST;
        }

        public final DebugInfoDiagnosticFactory0 getCONSTANT() {
            return DebugInfoDiagnosticFactory0.CONSTANT;
        }

        public final DebugInfoDiagnosticFactory0 getLEAKING_THIS() {
            return DebugInfoDiagnosticFactory0.LEAKING_THIS;
        }

        public final DebugInfoDiagnosticFactory0 getIMPLICIT_EXHAUSTIVE() {
            return DebugInfoDiagnosticFactory0.IMPLICIT_EXHAUSTIVE;
        }

        public final DebugInfoDiagnosticFactory0 getELEMENT_WITH_ERROR_TYPE() {
            return DebugInfoDiagnosticFactory0.ELEMENT_WITH_ERROR_TYPE;
        }

        public final DebugInfoDiagnosticFactory0 getUNRESOLVED_WITH_TARGET() {
            return DebugInfoDiagnosticFactory0.UNRESOLVED_WITH_TARGET;
        }

        public final DebugInfoDiagnosticFactory0 getMISSING_UNRESOLVED() {
            return DebugInfoDiagnosticFactory0.MISSING_UNRESOLVED;
        }

        public final DebugInfoDiagnosticFactory0 getDYNAMIC() {
            return DebugInfoDiagnosticFactory0.DYNAMIC;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        int i = 2;
        ELEMENT_WITH_ERROR_TYPE = new DebugInfoDiagnosticFactory0("ELEMENT_WITH_ERROR_TYPE", 0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
        UNRESOLVED_WITH_TARGET = new DebugInfoDiagnosticFactory0("UNRESOLVED_WITH_TARGET", 0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
        MISSING_UNRESOLVED = new DebugInfoDiagnosticFactory0("MISSING_UNRESOLVED", 0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
    }
}
