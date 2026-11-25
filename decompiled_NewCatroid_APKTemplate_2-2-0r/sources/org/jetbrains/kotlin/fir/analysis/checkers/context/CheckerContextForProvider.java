package org.jetbrains.kotlin.fir.analysis.checkers.context;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInlineDeclarationChecker;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.SessionHolder;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: CheckerContextForProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0010H&J\u0010\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0010\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0010\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u0019H&J\u001e\u0010\u001a\u001a\u00020\u00002\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\n\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u001eH&J.\u0010\u001f\u001a\u00020\u00002\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H&J\b\u0010#\u001a\u00020$H&J\b\u0010%\u001a\u00020$H&J\b\u0010&\u001a\u00020$H&J\b\u0010'\u001a\u00020$H&J\b\u0010(\u001a\u00020$H&J\b\u0010)\u001a\u00020\u0000H&J\u0010\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020,H&J\b\u0010-\u001a\u00020\u0000H&J\u0010\u0010.\u001a\u00020\u00002\u0006\u0010+\u001a\u00020,H&J\u0010\u0010/\u001a\u00020\u00002\u0006\u00100\u001a\u000201H&J\b\u00102\u001a\u00020\u0000H&J-\u00103\u001a\u0002H4\"\u0004\b\u0000\u001042\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u00105\u001a\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u0002H406¢\u0006\u0002\u00107¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContextForProvider;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/AbstractCheckerContext;", "sessionHolder", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "allInfosSuppressed", "", "allWarningsSuppressed", "allErrorsSuppressed", "(Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;ZZZ)V", "addAnnotationContainer", "annotationContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "addCallOrAssignment", "qualifiedAccessOrAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "addDeclaration", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "addElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "addGetClassCall", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "addImplicitReceiver", "name", "Lorg/jetbrains/kotlin/name/Name;", "value", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "addSuppressedDiagnostics", "diagnosticNames", "", "", "dropAnnotationContainer", "", "dropCallOrAssignment", "dropDeclaration", "dropElement", "dropGetClassCall", "enterContractBody", "enterFile", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "exitContractBody", "exitFile", "setInlineFunctionBodyContext", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;", "unsetInlineFunctionBodyContext", "withDeclaration", ExifInterface.GPS_DIRECTION_TRUE, "f", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CheckerContextForProvider extends AbstractCheckerContext {
    public abstract CheckerContextForProvider addAnnotationContainer(FirAnnotationContainer annotationContainer);

    public abstract CheckerContextForProvider addCallOrAssignment(FirStatement qualifiedAccessOrAnnotationCall);

    public abstract CheckerContextForProvider addDeclaration(FirDeclaration declaration);

    public abstract CheckerContextForProvider addElement(FirElement element);

    public abstract CheckerContextForProvider addGetClassCall(FirGetClassCall getClassCall);

    public abstract CheckerContextForProvider addImplicitReceiver(Name name2, ImplicitReceiverValue<?> value);

    public abstract CheckerContextForProvider addSuppressedDiagnostics(Collection<String> diagnosticNames, boolean allInfosSuppressed, boolean allWarningsSuppressed, boolean allErrorsSuppressed);

    public abstract void dropAnnotationContainer();

    public abstract void dropCallOrAssignment();

    public abstract void dropDeclaration();

    public abstract void dropElement();

    public abstract void dropGetClassCall();

    public abstract CheckerContextForProvider enterContractBody();

    public abstract CheckerContextForProvider enterFile(FirFile file);

    public abstract CheckerContextForProvider exitContractBody();

    public abstract CheckerContextForProvider exitFile(FirFile file);

    public abstract CheckerContextForProvider setInlineFunctionBodyContext(FirInlineDeclarationChecker.InlineFunctionBodyContext context);

    public abstract CheckerContextForProvider unsetInlineFunctionBodyContext();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CheckerContextForProvider(SessionHolder sessionHolder, ReturnTypeCalculator returnTypeCalculator, boolean z, boolean z2, boolean z3) {
        super(sessionHolder, returnTypeCalculator, z, z2, z3);
        Intrinsics.checkNotNullParameter(sessionHolder, "sessionHolder");
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
    }

    public final <T> T withDeclaration(FirDeclaration declaration, Function1<? super CheckerContextForProvider, ? extends T> f) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(f, "f");
        CheckerContextForProvider checkerContextForProviderAddDeclaration = addDeclaration(declaration);
        try {
            return f.invoke(checkerContextForProviderAddDeclaration);
        } finally {
            checkerContextForProviderAddDeclaration.dropDeclaration();
        }
    }
}
