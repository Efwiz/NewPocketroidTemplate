package org.jetbrains.kotlin.js.inline;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.context.FunctionDefinitionLoader;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;

/* JADX INFO: compiled from: InlinerCycleReporter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0001$B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J&\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00160\u001cJ\u001a\u0010\u001d\u001a\u00020\u00162\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001e\u001a\u00020\bH\u0002J'\u0010\u001f\u001a\u0002H \"\u0004\b\u0000\u0010 2\u0006\u0010!\u001a\u00020\b2\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H 0\u001c¢\u0006\u0002\u0010#R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/InlinerCycleReporter;", "", "trace", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "functionDefinitionLoader", "Lorg/jetbrains/kotlin/js/inline/context/FunctionDefinitionLoader;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;Lorg/jetbrains/kotlin/js/inline/context/FunctionDefinitionLoader;)V", "currentNamedFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "getCurrentNamedFunction", "()Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "functionVisitingState", "", "Lorg/jetbrains/kotlin/js/inline/InlinerCycleReporter$VisitedState;", "inlineCallInfos", "Ljava/util/LinkedList;", "Lorg/jetbrains/kotlin/js/inline/JsCallInfo;", "namedFunctionsStack", "Ljava/util/Stack;", "getTrace", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "processInlineFunction", "", "definition", "Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;", "call", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "doProcess", "Lkotlin/Function0;", "reportInlineCycle", "calledFunction", "withFunction", ExifInterface.GPS_DIRECTION_TRUE, "function", "body", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "VisitedState", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlinerCycleReporter {
    private final FunctionDefinitionLoader functionDefinitionLoader;
    private final Map<JsFunction, VisitedState> functionVisitingState;
    private final LinkedList<JsCallInfo> inlineCallInfos;
    private final Stack<JsFunction> namedFunctionsStack;
    private final DiagnosticSink trace;

    /* JADX INFO: compiled from: InlinerCycleReporter.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/InlinerCycleReporter$VisitedState;", "", "(Ljava/lang/String;I)V", "IN_PROCESS", "PROCESSED", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum VisitedState {
        IN_PROCESS,
        PROCESSED;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<VisitedState> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: InlinerCycleReporter.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VisitedState.values().length];
            try {
                iArr[VisitedState.IN_PROCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VisitedState.PROCESSED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public InlinerCycleReporter(DiagnosticSink trace, FunctionDefinitionLoader functionDefinitionLoader) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(functionDefinitionLoader, "functionDefinitionLoader");
        this.trace = trace;
        this.functionDefinitionLoader = functionDefinitionLoader;
        this.functionVisitingState = new LinkedHashMap();
        this.namedFunctionsStack = new Stack<>();
        this.inlineCallInfos = new LinkedList<>();
    }

    public final DiagnosticSink getTrace() {
        return this.trace;
    }

    private final JsFunction getCurrentNamedFunction() {
        if (this.namedFunctionsStack.empty()) {
            return null;
        }
        return this.namedFunctionsStack.peek();
    }

    public final <T> T withFunction(JsFunction function, Function0<? extends T> body) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(body, "body");
        if (this.functionDefinitionLoader.getFunctionsByFunctionNodes().keySet().contains(function)) {
            this.namedFunctionsStack.push(function);
        }
        T tInvoke = body.invoke();
        if (Intrinsics.areEqual(getCurrentNamedFunction(), function)) {
            this.namedFunctionsStack.pop();
        }
        return tInvoke;
    }

    public final void processInlineFunction(FunctionWithWrapper definition, JsInvocation call, Function0<Unit> doProcess) {
        boolean zIsEmpty;
        boolean zAreEqual;
        JsFunction currentNamedFunction;
        Intrinsics.checkNotNullParameter(definition, "definition");
        Intrinsics.checkNotNullParameter(doProcess, "doProcess");
        JsFunction function = definition.getFunction();
        if (call != null && (currentNamedFunction = getCurrentNamedFunction()) != null) {
            this.inlineCallInfos.add(new JsCallInfo(call, currentNamedFunction));
        }
        try {
            VisitedState visitedState = this.functionVisitingState.get(definition.getFunction());
            int i = visitedState == null ? -1 : WhenMappings.$EnumSwitchMapping$0[visitedState.ordinal()];
            if (i == 1) {
                reportInlineCycle(call, definition.getFunction());
                if (zIsEmpty) {
                    return;
                }
            } else {
                if (i == 2) {
                    if (zIsEmpty) {
                        return;
                    }
                    if (zAreEqual) {
                        return;
                    } else {
                        return;
                    }
                }
                this.functionVisitingState.put(function, VisitedState.IN_PROCESS);
                withFunction(function, doProcess);
                this.functionVisitingState.put(function, VisitedState.PROCESSED);
                if (zIsEmpty) {
                    return;
                }
            }
        } finally {
            if (!this.inlineCallInfos.isEmpty() && Intrinsics.areEqual(this.inlineCallInfos.getLast().getCall(), call)) {
                this.inlineCallInfos.removeLast();
            }
        }
    }

    private final void reportInlineCycle(JsInvocation call, JsFunction calledFunction) {
        if (call != null) {
            MetadataProperties.setInline(call, (Boolean) false);
        }
        Iterator<JsCallInfo> itDescendingIterator = this.inlineCallInfos.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            JsCallInfo next = itDescendingIterator.next();
            PsiElement psiElement = MetadataProperties.getPsiElement(next.getCall());
            CallableDescriptor descriptor = MetadataProperties.getDescriptor(next.getCall());
            if (psiElement != null && descriptor != null) {
                this.trace.report(Errors.INLINE_CALL_CYCLE.on(psiElement, descriptor));
            }
            if (Intrinsics.areEqual(next.getContainingFunction(), calledFunction)) {
                return;
            }
        }
    }
}
