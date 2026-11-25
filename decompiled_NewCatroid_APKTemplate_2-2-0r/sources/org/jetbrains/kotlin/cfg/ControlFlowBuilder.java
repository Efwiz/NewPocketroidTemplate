package org.jetbrains.kotlin.cfg;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessTarget;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.CallInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithValue;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicKind;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MergeInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.OperationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.ReadValueInstruction;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtLoopExpression;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtThrowExpression;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: ControlFlowBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000þ\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001:\u0001{J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&J\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0007H&JD\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00072\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001b0\u001a2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001d0\u001aH&J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H&J\u0010\u0010\"\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020#H&J\b\u0010$\u001a\u00020\u000fH&J\u0010\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020&H&J\u0010\u0010'\u001a\u00020\r2\u0006\u0010(\u001a\u00020)H&J\u0018\u0010*\u001a\u00020\r2\u0006\u0010+\u001a\u00020\u00072\u0006\u0010,\u001a\u00020-H&J \u0010.\u001a\u00020\r2\u0006\u0010+\u001a\u00020\u00072\u0006\u0010,\u001a\u00020-2\u0006\u0010/\u001a\u000200H&J\u0010\u00101\u001a\u00020\r2\u0006\u00102\u001a\u000203H&J\u0010\u00104\u001a\u00020\r2\u0006\u00105\u001a\u000206H&J\u0010\u00107\u001a\u00020\r2\u0006\u00108\u001a\u00020\u0007H&J\u0010\u00109\u001a\u00020:2\u0006\u0010 \u001a\u00020\u0003H&J\u0010\u0010;\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u0003H&J\u001c\u0010<\u001a\u00020\r2\u0006\u0010+\u001a\u00020\u00072\n\b\u0002\u0010/\u001a\u0004\u0018\u000100H&J\u0010\u0010=\u001a\u00020\r2\u0006\u0010>\u001a\u00020?H&J\u0010\u0010@\u001a\u00020\r2\u0006\u00108\u001a\u00020\u0007H&J\u0010\u0010A\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u0003H&J\u001c\u0010B\u001a\u00020-2\u0006\u0010+\u001a\u00020\u00072\n\b\u0002\u0010/\u001a\u0004\u0018\u000100H&J\b\u0010C\u001a\u00020\rH&J\u0014\u0010D\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0007H&J\u0012\u0010E\u001a\u0004\u0018\u00010\u000f2\u0006\u0010F\u001a\u00020\u0003H&J\u0012\u0010G\u001a\u0004\u0018\u00010\u000f2\u0006\u0010F\u001a\u00020\u0003H&J\u0012\u0010H\u001a\u0004\u0018\u00010\u000f2\u0006\u0010I\u001a\u00020\u0007H&J\u0018\u0010J\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0007H&J\"\u0010K\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00072\b\u0010L\u001a\u0004\u0018\u00010\u0012H&J\"\u0010M\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00072\b\u0010L\u001a\u0004\u0018\u00010\u0012H&J\u0010\u0010N\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0007H&J\u001e\u0010O\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020P2\f\u0010Q\u001a\b\u0012\u0002\b\u0003\u0018\u00010RH&J\u001e\u0010S\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020T2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020\u00120VH&J\u0010\u0010W\u001a\u00020\r2\u0006\u0010 \u001a\u00020PH&J0\u0010X\u001a\u00020Y2\u0006\u0010Z\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\u00072\f\u0010U\u001a\b\u0012\u0004\u0012\u00020\u00120V2\u0006\u0010[\u001a\u00020\\H&J\u0010\u0010]\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0007H&J\u001e\u0010^\u001a\u00020_2\u0006\u0010 \u001a\u00020P2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020\u00120VH&J\u0012\u0010`\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0007H&J\u001e\u0010a\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0V2\u0006\u0010\u0013\u001a\u00020\u0007H&J\"\u0010a\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00072\b\u0010b\u001a\u0004\u0018\u00010\u0012H&J&\u0010c\u001a\u00020d2\u0006\u0010 \u001a\u00020P2\u0006\u0010e\u001a\u00020f2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020\u00120VH&J,\u0010g\u001a\u00020h2\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010i\u001a\u00020j2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001b0\u001aH&J0\u0010k\u001a\u00020h2\u0006\u0010 \u001a\u00020P2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001b0\u001aH&J\u0018\u0010l\u001a\u00020\r2\u0006\u0010m\u001a\u00020\u000f2\u0006\u0010n\u001a\u00020\u000fH&J\u0018\u0010o\u001a\u00020\r2\u0006\u0010p\u001a\u00020q2\u0006\u0010+\u001a\u00020\u0007H&J \u0010r\u001a\u00020\r2\u0006\u0010p\u001a\u00020P2\u0006\u0010r\u001a\u00020\u00122\u0006\u0010+\u001a\u00020\u0007H&J\u0018\u0010s\u001a\u00020\r2\u0006\u0010t\u001a\u00020u2\u0006\u0010v\u001a\u00020\u0012H&J<\u0010w\u001a\u00020\r2\u0006\u0010x\u001a\u00020\u00072\u0006\u0010y\u001a\u00020\u00072\u0006\u0010z\u001a\u00020\u00122\u0006\u0010i\u001a\u00020j2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001b0\u001aH&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006|À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder;", "", "currentLoop", "Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "getCurrentLoop", "()Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "currentSubroutine", "Lorg/jetbrains/kotlin/psi/KtElement;", "getCurrentSubroutine", "()Lorg/jetbrains/kotlin/psi/KtElement;", "returnSubroutine", "getReturnSubroutine", "bindLabel", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/cfg/Label;", "bindValue", "value", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "call", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/CallInstruction;", "valueElement", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "receiverValues", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "arguments", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "createAnonymousObject", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithValue;", "expression", "Lorg/jetbrains/kotlin/psi/KtObjectLiteralExpression;", "createLambda", "Lorg/jetbrains/kotlin/psi/KtFunction;", "createUnboundLabel", "name", "", "declareEntryOrObject", "entryOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "declareFunction", "subroutine", "pseudocode", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "declareInlinedFunction", "eventOccurrencesRange", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "declareParameter", "parameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "declareVariable", "property", "Lorg/jetbrains/kotlin/psi/KtVariableDeclaration;", "enterBlockScope", "block", "enterLoop", "Lorg/jetbrains/kotlin/cfg/LoopInfo;", "enterLoopBody", "enterSubroutine", "enterTryFinally", "trigger", "Lorg/jetbrains/kotlin/cfg/GenerationTrigger;", "exitBlockScope", "exitLoopBody", "exitSubroutine", "exitTryFinally", "getBoundValue", "getLoopConditionEntryPoint", "loop", "getLoopExitPoint", "getSubroutineExitPoint", "labelElement", "jump", "jumpOnFalse", "conditionValue", "jumpOnTrue", "jumpToError", "loadConstant", "Lorg/jetbrains/kotlin/psi/KtExpression;", "constant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "loadStringTemplate", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", "inputValues", "", "loadUnit", "magic", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicInstruction;", "instructionElement", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicKind;", "mark", "merge", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MergeInstruction;", "newValue", "nondeterministicJump", "inputValue", "predefinedOperation", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/OperationInstruction;", "operation", "Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder$PredefinedOperation;", "read", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/ReadValueInstruction;", "target", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "readVariable", "repeatPseudocode", "startLabel", "finishLabel", "returnNoValue", "returnExpression", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "returnValue", "throwException", "throwExpression", "Lorg/jetbrains/kotlin/psi/KtThrowExpression;", "thrownValue", "write", "assignment", "lValue", "rValue", "PredefinedOperation", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ControlFlowBuilder {

    /* JADX INFO: compiled from: ControlFlowBuilder.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder$PredefinedOperation;", "", "(Ljava/lang/String;I)V", "AND", "OR", "NOT_NULL_ASSERTION", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum PredefinedOperation {
        AND,
        OR,
        NOT_NULL_ASSERTION;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<PredefinedOperation> getEntries() {
            return $ENTRIES;
        }
    }

    void bindLabel(Label label);

    void bindValue(PseudoValue value, KtElement element);

    CallInstruction call(KtElement valueElement, ResolvedCall<?> resolvedCall, Map<PseudoValue, ? extends ReceiverValue> receiverValues, Map<PseudoValue, ? extends ValueParameterDescriptor> arguments);

    InstructionWithValue createAnonymousObject(KtObjectLiteralExpression expression);

    InstructionWithValue createLambda(KtFunction expression);

    Label createUnboundLabel();

    Label createUnboundLabel(String name2);

    void declareEntryOrObject(KtClassOrObject entryOrObject);

    void declareFunction(KtElement subroutine, Pseudocode pseudocode);

    void declareInlinedFunction(KtElement subroutine, Pseudocode pseudocode, EventOccurrencesRange eventOccurrencesRange);

    void declareParameter(KtParameter parameter);

    void declareVariable(KtVariableDeclaration property);

    void enterBlockScope(KtElement block);

    LoopInfo enterLoop(KtLoopExpression expression);

    void enterLoopBody(KtLoopExpression expression);

    void enterSubroutine(KtElement subroutine, EventOccurrencesRange eventOccurrencesRange);

    void enterTryFinally(GenerationTrigger trigger);

    void exitBlockScope(KtElement block);

    void exitLoopBody(KtLoopExpression expression);

    Pseudocode exitSubroutine(KtElement subroutine, EventOccurrencesRange eventOccurrencesRange);

    void exitTryFinally();

    PseudoValue getBoundValue(KtElement element);

    KtLoopExpression getCurrentLoop();

    KtElement getCurrentSubroutine();

    Label getLoopConditionEntryPoint(KtLoopExpression loop);

    Label getLoopExitPoint(KtLoopExpression loop);

    KtElement getReturnSubroutine();

    Label getSubroutineExitPoint(KtElement labelElement);

    void jump(Label label, KtElement element);

    void jumpOnFalse(Label label, KtElement element, PseudoValue conditionValue);

    void jumpOnTrue(Label label, KtElement element, PseudoValue conditionValue);

    void jumpToError(KtElement element);

    InstructionWithValue loadConstant(KtExpression expression, CompileTimeConstant<?> constant);

    InstructionWithValue loadStringTemplate(KtStringTemplateExpression expression, List<? extends PseudoValue> inputValues);

    void loadUnit(KtExpression expression);

    MagicInstruction magic(KtElement instructionElement, KtElement valueElement, List<? extends PseudoValue> inputValues, MagicKind kind);

    void mark(KtElement element);

    MergeInstruction merge(KtExpression expression, List<? extends PseudoValue> inputValues);

    PseudoValue newValue(KtElement element);

    void nondeterministicJump(List<? extends Label> label, KtElement element);

    void nondeterministicJump(Label label, KtElement element, PseudoValue inputValue);

    OperationInstruction predefinedOperation(KtExpression expression, PredefinedOperation operation, List<? extends PseudoValue> inputValues);

    ReadValueInstruction read(KtElement element, AccessTarget target, Map<PseudoValue, ? extends ReceiverValue> receiverValues);

    ReadValueInstruction readVariable(KtExpression expression, ResolvedCall<?> resolvedCall, Map<PseudoValue, ? extends ReceiverValue> receiverValues);

    void repeatPseudocode(Label startLabel, Label finishLabel);

    void returnNoValue(KtReturnExpression returnExpression, KtElement subroutine);

    void returnValue(KtExpression returnExpression, PseudoValue returnValue, KtElement subroutine);

    void throwException(KtThrowExpression throwExpression, PseudoValue thrownValue);

    void write(KtElement assignment, KtElement lValue, PseudoValue rValue, AccessTarget target, Map<PseudoValue, ? extends ReceiverValue> receiverValues);

    static /* synthetic */ void enterSubroutine$default(ControlFlowBuilder controlFlowBuilder, KtElement ktElement, EventOccurrencesRange eventOccurrencesRange, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: enterSubroutine");
        }
        if ((i & 2) != 0) {
            eventOccurrencesRange = null;
        }
        controlFlowBuilder.enterSubroutine(ktElement, eventOccurrencesRange);
    }

    static /* synthetic */ Pseudocode exitSubroutine$default(ControlFlowBuilder controlFlowBuilder, KtElement ktElement, EventOccurrencesRange eventOccurrencesRange, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: exitSubroutine");
        }
        if ((i & 2) != 0) {
            eventOccurrencesRange = null;
        }
        return controlFlowBuilder.exitSubroutine(ktElement, eventOccurrencesRange);
    }
}
