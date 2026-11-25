package org.jetbrains.kotlin.fir.analysis.checkers.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
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
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MutableCheckerContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B§\u0001\b\u0002\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\n\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\n\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b\u0012\u0006\u0010\u001d\u001a\u00020\u0015\u0012\u0006\u0010\u001e\u001a\u00020\u0015\u0012\u0006\u0010\u001f\u001a\u00020\u0015¢\u0006\u0002\u0010 J\u0010\u00106\u001a\u00020\u00012\u0006\u00107\u001a\u00020\u0011H\u0016J\u0010\u00108\u001a\u00020\u00002\u0006\u00109\u001a\u00020\rH\u0016J\u0010\u0010:\u001a\u00020\u00002\u0006\u0010;\u001a\u00020\u000bH\u0016J\u0010\u0010<\u001a\u00020\u00012\u0006\u0010=\u001a\u00020\u0013H\u0016J\u0010\u0010>\u001a\u00020\u00002\u0006\u0010?\u001a\u00020\u000fH\u0016J\u001e\u0010@\u001a\u00020\u00002\b\u0010A\u001a\u0004\u0018\u00010B2\n\u0010C\u001a\u0006\u0012\u0002\b\u00030DH\u0016J.\u0010E\u001a\u00020\u00012\f\u0010F\u001a\b\u0012\u0004\u0012\u00020\u001c0G2\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\u0015H\u0016J\b\u0010H\u001a\u00020IH\u0016J\b\u0010J\u001a\u00020IH\u0016J\b\u0010K\u001a\u00020IH\u0016J\b\u0010L\u001a\u00020IH\u0016J\b\u0010M\u001a\u00020IH\u0016J\b\u0010N\u001a\u00020\u0001H\u0016J\u0010\u0010O\u001a\u00020\u00012\u0006\u0010P\u001a\u00020\u0019H\u0016J\b\u0010Q\u001a\u00020\u0001H\u0016J\u0010\u0010R\u001a\u00020\u00012\u0006\u0010P\u001a\u00020\u0019H\u0016J\u0010\u0010/\u001a\u00020\u00012\u0006\u0010S\u001a\u00020\u0017H\u0016J\b\u0010T\u001a\u00020\u0001H\u0016R\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\"R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\"R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\"R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\"R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u0010\u0014\u001a\u00020\u0015X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u00101\"\u0004\b2\u00103R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u00105¨\u0006U"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/context/MutableCheckerContext;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContextForProvider;", "sessionHolder", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "(Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;)V", "implicitReceiverStack", "Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;", "containingDeclarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "callsOrAssignments", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "getClassCalls", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "annotationContainers", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "containingElements", "Lorg/jetbrains/kotlin/fir/FirElement;", "isContractBody", "", "inlineFunctionBodyContext", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;", "containingFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "suppressedDiagnostics", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", "", "allInfosSuppressed", "allWarningsSuppressed", "allErrorsSuppressed", "(Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;ZLorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;Lorg/jetbrains/kotlin/fir/declarations/FirFile;Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;Lkotlinx/collections/immutable/PersistentSet;ZZZ)V", "getAnnotationContainers", "()Ljava/util/List;", "getCallsOrAssignments", "getContainingDeclarations", "getContainingElements", "getContainingFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "setContainingFile", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;)V", "getGetClassCalls", "getImplicitReceiverStack", "()Lorg/jetbrains/kotlin/fir/resolve/PersistentImplicitReceiverStack;", "getInlineFunctionBodyContext", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;", "setInlineFunctionBodyContext", "(Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;)V", "()Z", "setContractBody", "(Z)V", "getSuppressedDiagnostics", "()Lkotlinx/collections/immutable/PersistentSet;", "addAnnotationContainer", "annotationContainer", "addCallOrAssignment", "qualifiedAccessOrAnnotationCall", "addDeclaration", "declaration", "addElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "addGetClassCall", "getClassCall", "addImplicitReceiver", "name", "Lorg/jetbrains/kotlin/name/Name;", "value", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "addSuppressedDiagnostics", "diagnosticNames", "", "dropAnnotationContainer", "", "dropCallOrAssignment", "dropDeclaration", "dropElement", "dropGetClassCall", "enterContractBody", "enterFile", "file", "exitContractBody", "exitFile", "context", "unsetInlineFunctionBodyContext", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableCheckerContext extends CheckerContextForProvider {
    private final List<FirAnnotationContainer> annotationContainers;
    private final List<FirStatement> callsOrAssignments;
    private final List<FirDeclaration> containingDeclarations;
    private final List<FirElement> containingElements;
    private FirFile containingFile;
    private final List<FirGetClassCall> getClassCalls;
    private final PersistentImplicitReceiverStack implicitReceiverStack;
    private FirInlineDeclarationChecker.InlineFunctionBodyContext inlineFunctionBodyContext;
    private boolean isContractBody;
    private final PersistentSet<String> suppressedDiagnostics;

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public /* bridge */ /* synthetic */ CheckerContextForProvider addImplicitReceiver(Name name2, ImplicitReceiverValue implicitReceiverValue) {
        return addImplicitReceiver(name2, (ImplicitReceiverValue<?>) implicitReceiverValue);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public PersistentImplicitReceiverStack getImplicitReceiverStack() {
        return this.implicitReceiverStack;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public List<FirDeclaration> getContainingDeclarations() {
        return this.containingDeclarations;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public List<FirStatement> getCallsOrAssignments() {
        return this.callsOrAssignments;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public List<FirGetClassCall> getGetClassCalls() {
        return this.getClassCalls;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public List<FirAnnotationContainer> getAnnotationContainers() {
        return this.annotationContainers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public List<FirElement> getContainingElements() {
        return this.containingElements;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    /* JADX INFO: renamed from: isContractBody, reason: from getter */
    public boolean getIsContractBody() {
        return this.isContractBody;
    }

    public void setContractBody(boolean z) {
        this.isContractBody = z;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public FirInlineDeclarationChecker.InlineFunctionBodyContext getInlineFunctionBodyContext() {
        return this.inlineFunctionBodyContext;
    }

    /* JADX INFO: renamed from: setInlineFunctionBodyContext, reason: collision with other method in class */
    public void m6777setInlineFunctionBodyContext(FirInlineDeclarationChecker.InlineFunctionBodyContext inlineFunctionBodyContext) {
        this.inlineFunctionBodyContext = inlineFunctionBodyContext;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public FirFile getContainingFile() {
        return this.containingFile;
    }

    public void setContainingFile(FirFile firFile) {
        this.containingFile = firFile;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public PersistentSet<String> getSuppressedDiagnostics() {
        return this.suppressedDiagnostics;
    }

    private MutableCheckerContext(PersistentImplicitReceiverStack persistentImplicitReceiverStack, List<FirDeclaration> list, List<FirStatement> list2, List<FirGetClassCall> list3, List<FirAnnotationContainer> list4, List<FirElement> list5, boolean z, FirInlineDeclarationChecker.InlineFunctionBodyContext inlineFunctionBodyContext, FirFile firFile, SessionHolder sessionHolder, ReturnTypeCalculator returnTypeCalculator, PersistentSet<String> persistentSet, boolean z2, boolean z3, boolean z4) {
        super(sessionHolder, returnTypeCalculator, z2, z3, z4);
        this.implicitReceiverStack = persistentImplicitReceiverStack;
        this.containingDeclarations = list;
        this.callsOrAssignments = list2;
        this.getClassCalls = list3;
        this.annotationContainers = list4;
        this.containingElements = list5;
        this.isContractBody = z;
        this.inlineFunctionBodyContext = inlineFunctionBodyContext;
        this.containingFile = firFile;
        this.suppressedDiagnostics = persistentSet;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MutableCheckerContext(SessionHolder sessionHolder, ReturnTypeCalculator returnTypeCalculator) {
        this(new PersistentImplicitReceiverStack(), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), false, null, null, sessionHolder, returnTypeCalculator, ExtensionsKt.persistentSetOf(), false, false, false);
        Intrinsics.checkNotNullParameter(sessionHolder, "sessionHolder");
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public MutableCheckerContext addImplicitReceiver(Name name2, ImplicitReceiverValue<?> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return new MutableCheckerContext(PersistentImplicitReceiverStack.add$default(getImplicitReceiverStack(), name2, value, null, 4, null), getContainingDeclarations(), getCallsOrAssignments(), getGetClassCalls(), getAnnotationContainers(), getContainingElements(), getIsContractBody(), getInlineFunctionBodyContext(), getContainingFile(), getSessionHolder(), getReturnTypeCalculator(), getSuppressedDiagnostics(), getAllInfosSuppressed(), getAllWarningsSuppressed(), getAllErrorsSuppressed());
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public MutableCheckerContext addDeclaration(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        getContainingDeclarations().add(declaration);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public void dropDeclaration() {
        CollectionsKt.removeLast(getContainingDeclarations());
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public MutableCheckerContext addCallOrAssignment(FirStatement qualifiedAccessOrAnnotationCall) {
        Intrinsics.checkNotNullParameter(qualifiedAccessOrAnnotationCall, "qualifiedAccessOrAnnotationCall");
        getCallsOrAssignments().add(qualifiedAccessOrAnnotationCall);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public void dropCallOrAssignment() {
        CollectionsKt.removeLast(getCallsOrAssignments());
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public MutableCheckerContext addGetClassCall(FirGetClassCall getClassCall) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        getGetClassCalls().add(getClassCall);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public void dropGetClassCall() {
        CollectionsKt.removeLast(getGetClassCalls());
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider addAnnotationContainer(FirAnnotationContainer annotationContainer) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        getAnnotationContainers().add(annotationContainer);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public void dropAnnotationContainer() {
        CollectionsKt.removeLast(getAnnotationContainers());
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider addElement(FirElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        CollectionsKt.lastOrNull((List) getContainingElements());
        getContainingElements().add(element);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public void dropElement() {
        CollectionsKt.removeLast(getContainingElements());
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider addSuppressedDiagnostics(Collection<String> diagnosticNames, boolean allInfosSuppressed, boolean allWarningsSuppressed, boolean allErrorsSuppressed) {
        Intrinsics.checkNotNullParameter(diagnosticNames, "diagnosticNames");
        if (diagnosticNames.isEmpty()) {
            return this;
        }
        return new MutableCheckerContext(getImplicitReceiverStack(), getContainingDeclarations(), getCallsOrAssignments(), getGetClassCalls(), getAnnotationContainers(), getContainingElements(), getIsContractBody(), getInlineFunctionBodyContext(), getContainingFile(), getSessionHolder(), getReturnTypeCalculator(), getSuppressedDiagnostics().addAll((Collection<? extends String>) diagnosticNames), getAllInfosSuppressed() || allInfosSuppressed, getAllWarningsSuppressed() || allWarningsSuppressed, getAllErrorsSuppressed() || allErrorsSuppressed);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider enterContractBody() {
        if (getIsContractBody()) {
            throw new IllegalStateException("Check failed.".toString());
        }
        setContractBody(true);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider exitContractBody() {
        if (!getIsContractBody()) {
            throw new IllegalStateException("Check failed.".toString());
        }
        setContractBody(false);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider setInlineFunctionBodyContext(FirInlineDeclarationChecker.InlineFunctionBodyContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        m6777setInlineFunctionBodyContext(context);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider unsetInlineFunctionBodyContext() {
        m6777setInlineFunctionBodyContext((FirInlineDeclarationChecker.InlineFunctionBodyContext) null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider enterFile(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        setContainingFile(file);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider
    public CheckerContextForProvider exitFile(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        setContainingFile(file);
        return this;
    }
}
