package org.jetbrains.kotlin.fir.analysis.checkers.context;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.zip.UnixStat;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInlineDeclarationChecker;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.PersistentImplicitReceiverStack;
import org.jetbrains.kotlin.fir.resolve.SessionHolder;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: PersistentCheckerContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B§\u0001\b\u0002\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\n\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\n\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019\u0012\u0006\u0010\u001b\u001a\u00020\u0015\u0012\u0006\u0010\u001c\u001a\u00020\u0015\u0012\u0006\u0010\u001d\u001a\u00020\u0015\u0012\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f¢\u0006\u0002\u0010 J\u0010\u00100\u001a\u00020\u00002\u0006\u00101\u001a\u00020\u0011H\u0016J\u0010\u00102\u001a\u00020\u00002\u0006\u00103\u001a\u00020\rH\u0016J\u0010\u00104\u001a\u00020\u00002\u0006\u00105\u001a\u00020\u000bH\u0016J\u0010\u00106\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u0013H\u0016J\u0010\u00108\u001a\u00020\u00002\u0006\u00109\u001a\u00020\u000fH\u0016J\u001e\u0010:\u001a\u00020\u00002\b\u0010;\u001a\u0004\u0018\u00010<2\n\u0010=\u001a\u0006\u0012\u0002\b\u00030>H\u0016J.\u0010?\u001a\u00020\u00012\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u001a0A2\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u0015H\u0016J²\u0001\u0010B\u001a\u00020\u00002\b\b\u0002\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010C\u001a\b\u0012\u0004\u0012\u00020\r0\n2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\n2\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\n2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\b\u0002\u0010\u001b\u001a\u00020\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u00152\b\b\u0002\u0010\u001d\u001a\u00020\u00152\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0002J\b\u0010D\u001a\u00020EH\u0016J\b\u0010F\u001a\u00020EH\u0016J\b\u0010G\u001a\u00020EH\u0016J\b\u0010H\u001a\u00020EH\u0016J\b\u0010I\u001a\u00020EH\u0016J\b\u0010J\u001a\u00020\u0001H\u0016J\u0010\u0010K\u001a\u00020\u00012\u0006\u0010L\u001a\u00020\u001fH\u0016J\b\u0010M\u001a\u00020\u0001H\u0016J\u0010\u0010N\u001a\u00020\u00012\u0006\u0010L\u001a\u00020\u001fH\u0016J\u0010\u0010O\u001a\u00020\u00002\u0006\u0010P\u001a\u00020\u0017H\u0016J\u0010\u0010Q\u001a\u00020\u00012\u0006\u0010R\u001a\u00020\u0015H\u0002J\b\u0010S\u001a\u00020\u0001H\u0016R\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\"R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\"R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\"R\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\"R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0014\u0010\u0014\u001a\u00020\u0015X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010-R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/¨\u0006T"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/context/PersistentCheckerContext;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContextForProvider;", "sessionHolder", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "(Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;)V", "implicitReceiverStack", "Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;", "containingDeclarations", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "callsOrAssignments", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "getClassCalls", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "annotationContainers", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "containingElements", "Lorg/jetbrains/kotlin/fir/FirElement;", "isContractBody", "", "inlineFunctionBodyContext", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;", "suppressedDiagnostics", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", "", "allInfosSuppressed", "allWarningsSuppressed", "allErrorsSuppressed", "containingFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "(Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;Lkotlinx/collections/immutable/PersistentList;Lkotlinx/collections/immutable/PersistentList;Lkotlinx/collections/immutable/PersistentList;Lkotlinx/collections/immutable/PersistentList;Lkotlinx/collections/immutable/PersistentList;ZLorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;Lkotlinx/collections/immutable/PersistentSet;ZZZLorg/jetbrains/kotlin/fir/declarations/FirFile;)V", "getAnnotationContainers", "()Lkotlinx/collections/immutable/PersistentList;", "getCallsOrAssignments", "getContainingDeclarations", "getContainingElements", "getContainingFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getGetClassCalls", "getImplicitReceiverStack", "()Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;", "getInlineFunctionBodyContext", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;", "()Z", "getSuppressedDiagnostics", "()Lkotlinx/collections/immutable/PersistentSet;", "addAnnotationContainer", "annotationContainer", "addCallOrAssignment", "qualifiedAccessOrAnnotationCall", "addDeclaration", "declaration", "addElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "addGetClassCall", "getClassCall", "addImplicitReceiver", "name", "Lorg/jetbrains/kotlin/name/Name;", "value", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "addSuppressedDiagnostics", "diagnosticNames", "", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "qualifiedAccessOrAssignmentsOrAnnotationCalls", "dropAnnotationContainer", "", "dropCallOrAssignment", "dropDeclaration", "dropElement", "dropGetClassCall", "enterContractBody", "enterFile", "file", "exitContractBody", "exitFile", "setInlineFunctionBodyContext", "context", "toggleContractBody", "newValue", "unsetInlineFunctionBodyContext", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PersistentCheckerContext extends CheckerContextForProvider {
    private final PersistentList<FirAnnotationContainer> annotationContainers;
    private final PersistentList<FirStatement> callsOrAssignments;
    private final PersistentList<FirDeclaration> containingDeclarations;
    private final PersistentList<FirElement> containingElements;
    private final FirFile containingFile;
    private final PersistentList<FirGetClassCall> getClassCalls;
    private final PersistentImplicitReceiverStack implicitReceiverStack;
    private final FirInlineDeclarationChecker.InlineFunctionBodyContext inlineFunctionBodyContext;
    private final boolean isContractBody;
    private final PersistentSet<String> suppressedDiagnostics;

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public void dropAnnotationContainer() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public void dropCallOrAssignment() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public void dropDeclaration() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public void dropElement() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public void dropGetClassCall() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public /* bridge */ /* synthetic */ CheckerContextForProvider addImplicitReceiver(Name name2, ImplicitReceiverValue implicitReceiverValue) {
        return addImplicitReceiver(name2, (ImplicitReceiverValue<?>) implicitReceiverValue);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public PersistentImplicitReceiverStack getImplicitReceiverStack() {
        return this.implicitReceiverStack;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public PersistentList<FirDeclaration> getContainingDeclarations() {
        return this.containingDeclarations;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public PersistentList<FirStatement> getCallsOrAssignments() {
        return this.callsOrAssignments;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public PersistentList<FirGetClassCall> getGetClassCalls() {
        return this.getClassCalls;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public PersistentList<FirAnnotationContainer> getAnnotationContainers() {
        return this.annotationContainers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public PersistentList<FirElement> getContainingElements() {
        return this.containingElements;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    /* JADX INFO: renamed from: isContractBody, reason: from getter */
    public boolean getIsContractBody() {
        return this.isContractBody;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public FirInlineDeclarationChecker.InlineFunctionBodyContext getInlineFunctionBodyContext() {
        return this.inlineFunctionBodyContext;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public PersistentSet<String> getSuppressedDiagnostics() {
        return this.suppressedDiagnostics;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public FirFile getContainingFile() {
        return this.containingFile;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private PersistentCheckerContext(PersistentImplicitReceiverStack persistentImplicitReceiverStack, PersistentList<? extends FirDeclaration> persistentList, PersistentList<? extends FirStatement> persistentList2, PersistentList<? extends FirGetClassCall> persistentList3, PersistentList<? extends FirAnnotationContainer> persistentList4, PersistentList<? extends FirElement> persistentList5, boolean z, FirInlineDeclarationChecker.InlineFunctionBodyContext inlineFunctionBodyContext, SessionHolder sessionHolder, ReturnTypeCalculator returnTypeCalculator, PersistentSet<String> persistentSet, boolean z2, boolean z3, boolean z4, FirFile firFile) {
        super(sessionHolder, returnTypeCalculator, z2, z3, z4);
        this.implicitReceiverStack = persistentImplicitReceiverStack;
        this.containingDeclarations = persistentList;
        this.callsOrAssignments = persistentList2;
        this.getClassCalls = persistentList3;
        this.annotationContainers = persistentList4;
        this.containingElements = persistentList5;
        this.isContractBody = z;
        this.inlineFunctionBodyContext = inlineFunctionBodyContext;
        this.suppressedDiagnostics = persistentSet;
        this.containingFile = firFile;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PersistentCheckerContext(SessionHolder sessionHolder, ReturnTypeCalculator returnTypeCalculator) {
        this(new PersistentImplicitReceiverStack(), ExtensionsKt.persistentListOf(), ExtensionsKt.persistentListOf(), ExtensionsKt.persistentListOf(), ExtensionsKt.persistentListOf(), ExtensionsKt.persistentListOf(), false, null, sessionHolder, returnTypeCalculator, ExtensionsKt.persistentSetOf(), false, false, false, null);
        Intrinsics.checkNotNullParameter(sessionHolder, "sessionHolder");
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public PersistentCheckerContext addImplicitReceiver(Name name2, ImplicitReceiverValue<?> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return copy$default(this, PersistentImplicitReceiverStack.add$default(getImplicitReceiverStack(), name2, value, null, 4, null), null, null, null, null, null, false, null, false, false, false, null, null, 8190, null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public PersistentCheckerContext addDeclaration(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return copy$default(this, null, null, null, null, null, getContainingDeclarations().add(declaration), false, null, false, false, false, null, null, 8159, null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public PersistentCheckerContext addCallOrAssignment(FirStatement qualifiedAccessOrAnnotationCall) {
        Intrinsics.checkNotNullParameter(qualifiedAccessOrAnnotationCall, "qualifiedAccessOrAnnotationCall");
        return copy$default(this, null, getCallsOrAssignments().add(qualifiedAccessOrAnnotationCall), null, null, null, null, false, null, false, false, false, null, null, 8189, null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public PersistentCheckerContext addGetClassCall(FirGetClassCall getClassCall) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        return copy$default(this, null, null, getGetClassCalls().add(getClassCall), null, null, null, false, null, false, false, false, null, null, 8187, null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public PersistentCheckerContext addAnnotationContainer(FirAnnotationContainer annotationContainer) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        return copy$default(this, null, null, null, getAnnotationContainers().add(annotationContainer), null, null, false, null, false, false, false, null, null, 8183, null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public PersistentCheckerContext addElement(FirElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return copy$default(this, null, null, null, null, getContainingElements().add(element), null, false, null, false, false, false, null, null, 8175, null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider addSuppressedDiagnostics(Collection<String> diagnosticNames, boolean allInfosSuppressed, boolean allWarningsSuppressed, boolean allErrorsSuppressed) {
        Intrinsics.checkNotNullParameter(diagnosticNames, "diagnosticNames");
        if (diagnosticNames.isEmpty()) {
            return this;
        }
        return copy$default(this, null, null, null, null, null, null, false, null, getAllInfosSuppressed() || allInfosSuppressed, getAllWarningsSuppressed() || allWarningsSuppressed, getAllErrorsSuppressed() || allErrorsSuppressed, getSuppressedDiagnostics().addAll((Collection<? extends String>) diagnosticNames), null, 4351, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ PersistentCheckerContext copy$default(PersistentCheckerContext persistentCheckerContext, PersistentImplicitReceiverStack persistentImplicitReceiverStack, PersistentList persistentList, PersistentList persistentList2, PersistentList persistentList3, PersistentList persistentList4, PersistentList persistentList5, boolean z, FirInlineDeclarationChecker.InlineFunctionBodyContext inlineFunctionBodyContext, boolean z2, boolean z3, boolean z4, PersistentSet persistentSet, FirFile firFile, int i, Object obj) {
        return persistentCheckerContext.copy((i & 1) != 0 ? persistentCheckerContext.getImplicitReceiverStack() : persistentImplicitReceiverStack, (i & 2) != 0 ? persistentCheckerContext.getCallsOrAssignments() : persistentList, (i & 4) != 0 ? persistentCheckerContext.getGetClassCalls() : persistentList2, (i & 8) != 0 ? persistentCheckerContext.getAnnotationContainers() : persistentList3, (i & 16) != 0 ? persistentCheckerContext.getContainingElements() : persistentList4, (i & 32) != 0 ? persistentCheckerContext.getContainingDeclarations() : persistentList5, (i & 64) != 0 ? persistentCheckerContext.getIsContractBody() : z, (i & 128) != 0 ? persistentCheckerContext.getInlineFunctionBodyContext() : inlineFunctionBodyContext, (i & 256) != 0 ? persistentCheckerContext.getAllInfosSuppressed() : z2, (i & 512) != 0 ? persistentCheckerContext.getAllWarningsSuppressed() : z3, (i & 1024) != 0 ? persistentCheckerContext.getAllErrorsSuppressed() : z4, (i & 2048) != 0 ? persistentCheckerContext.getSuppressedDiagnostics() : persistentSet, (i & 4096) != 0 ? persistentCheckerContext.getContainingFile() : firFile);
    }

    private final PersistentCheckerContext copy(PersistentImplicitReceiverStack implicitReceiverStack, PersistentList<? extends FirStatement> qualifiedAccessOrAssignmentsOrAnnotationCalls, PersistentList<? extends FirGetClassCall> getClassCalls, PersistentList<? extends FirAnnotationContainer> annotationContainers, PersistentList<? extends FirElement> containingElements, PersistentList<? extends FirDeclaration> containingDeclarations, boolean isContractBody, FirInlineDeclarationChecker.InlineFunctionBodyContext inlineFunctionBodyContext, boolean allInfosSuppressed, boolean allWarningsSuppressed, boolean allErrorsSuppressed, PersistentSet<String> suppressedDiagnostics, FirFile containingFile) {
        return new PersistentCheckerContext(implicitReceiverStack, containingDeclarations, qualifiedAccessOrAssignmentsOrAnnotationCalls, getClassCalls, annotationContainers, containingElements, isContractBody, inlineFunctionBodyContext, getSessionHolder(), getReturnTypeCalculator(), suppressedDiagnostics, allInfosSuppressed, allWarningsSuppressed, allErrorsSuppressed, containingFile);
    }

    private final CheckerContextForProvider toggleContractBody(boolean newValue) {
        if (getIsContractBody() == newValue) {
            throw new IllegalStateException("Check failed.".toString());
        }
        return copy$default(this, null, null, null, null, null, null, newValue, null, false, false, false, null, null, 8127, null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider enterContractBody() {
        return toggleContractBody(true);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider exitContractBody() {
        return toggleContractBody(false);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public PersistentCheckerContext setInlineFunctionBodyContext(FirInlineDeclarationChecker.InlineFunctionBodyContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return copy$default(this, null, null, null, null, null, null, false, context, false, false, false, null, null, 8063, null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider unsetInlineFunctionBodyContext() {
        return copy$default(this, null, null, null, null, null, null, false, null, false, false, false, null, null, 8063, null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider enterFile(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return copy$default(this, null, null, null, null, null, null, false, null, false, false, false, null, file, UnixStat.PERM_MASK, null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider exitFile(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return copy$default(this, null, null, null, null, null, null, false, null, false, false, false, null, null, UnixStat.PERM_MASK, null);
    }
}
