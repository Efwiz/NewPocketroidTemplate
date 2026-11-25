package org.jetbrains.kotlin.fir.analysis.checkers.context;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.AbstractKtDiagnosticFactory;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInlineDeclarationChecker;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.ImplicitReceiverStack;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.SessionHolder;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;

/* JADX INFO: compiled from: CheckerContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010E\u001a\u00020\u00042\u0006\u0010F\u001a\u00020GH\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0012\u0010\t\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000fR\u0018\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000fR\u0018\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u000fR\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0018\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\fX¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u000fR\u0012\u0010$\u001a\u00020%X¦\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u0004\u0018\u00010)X¦\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0012\u0010,\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\u0006R\u0014\u0010-\u001a\u00020.8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R\u0012\u00101\u001a\u000202X¦\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u0011\u00105\u001a\u0002068F¢\u0006\u0006\u001a\u0004\b7\u00108R\u0011\u00109\u001a\u00020:8F¢\u0006\u0006\u001a\u0004\b;\u0010<R\u0012\u0010=\u001a\u00020>X¦\u0004¢\u0006\u0006\u001a\u0004\b?\u0010@R\u0018\u0010A\u001a\b\u0012\u0004\u0012\u00020\u001e0BX¦\u0004¢\u0006\u0006\u001a\u0004\bC\u0010D¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;", "()V", "allErrorsSuppressed", "", "getAllErrorsSuppressed", "()Z", "allInfosSuppressed", "getAllInfosSuppressed", "allWarningsSuppressed", "getAllWarningsSuppressed", "annotationContainers", "", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "getAnnotationContainers", "()Ljava/util/List;", "callsOrAssignments", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "getCallsOrAssignments", "containingDeclarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getContainingDeclarations", "containingElements", "Lorg/jetbrains/kotlin/fir/FirElement;", "getContainingElements", "containingFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getContainingFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "containingFilePath", "", "getContainingFilePath", "()Ljava/lang/String;", "getClassCalls", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "getGetClassCalls", "implicitReceiverStack", "Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "getImplicitReceiverStack", "()Lorg/jetbrains/kotlin/fir/resolve/ImplicitReceiverStack;", "inlineFunctionBodyContext", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;", "getInlineFunctionBodyContext", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;", "isContractBody", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "getReturnTypeCalculator", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "sessionHolder", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "getSessionHolder", "()Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "suppressedDiagnostics", "", "getSuppressedDiagnostics", "()Ljava/util/Set;", "isDiagnosticSuppressed", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CheckerContext implements DiagnosticContext {

    /* JADX INFO: compiled from: CheckerContext.kt */
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
                iArr[Severity.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Severity.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public abstract boolean getAllErrorsSuppressed();

    public abstract boolean getAllInfosSuppressed();

    public abstract boolean getAllWarningsSuppressed();

    public abstract List<FirAnnotationContainer> getAnnotationContainers();

    public abstract List<FirStatement> getCallsOrAssignments();

    public abstract List<FirDeclaration> getContainingDeclarations();

    public abstract List<FirElement> getContainingElements();

    public abstract FirFile getContainingFile();

    public abstract List<FirGetClassCall> getGetClassCalls();

    public abstract ImplicitReceiverStack getImplicitReceiverStack();

    public abstract FirInlineDeclarationChecker.InlineFunctionBodyContext getInlineFunctionBodyContext();

    public abstract ReturnTypeCalculator getReturnTypeCalculator();

    public abstract SessionHolder getSessionHolder();

    public abstract Set<String> getSuppressedDiagnostics();

    public abstract boolean isContractBody();

    public final FirSession getSession() {
        return getSessionHolder().getSession();
    }

    public final ScopeSession getScopeSession() {
        return getSessionHolder().getScopeSession();
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticContext
    public boolean isDiagnosticSuppressed(KtDiagnostic diagnostic) {
        boolean allInfosSuppressed;
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        AbstractKtDiagnosticFactory factory = diagnostic.getFactory();
        String name2 = factory.getName();
        int i = WhenMappings.$EnumSwitchMapping$0[factory.getSeverity().ordinal()];
        if (i == 1) {
            allInfosSuppressed = getAllInfosSuppressed();
        } else if (i == 2) {
            allInfosSuppressed = getAllWarningsSuppressed();
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            allInfosSuppressed = getAllErrorsSuppressed();
        }
        return allInfosSuppressed || getSuppressedDiagnostics().contains(name2);
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticContext
    public LanguageVersionSettings getLanguageVersionSettings() {
        return FirLanguageSettingsComponentKt.getLanguageVersionSettings(getSession());
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticContext
    public String getContainingFilePath() {
        KtSourceFile sourceFile;
        FirFile containingFile = getContainingFile();
        if (containingFile == null || (sourceFile = containingFile.getSourceFile()) == null) {
            return null;
        }
        return sourceFile.getPath();
    }
}
