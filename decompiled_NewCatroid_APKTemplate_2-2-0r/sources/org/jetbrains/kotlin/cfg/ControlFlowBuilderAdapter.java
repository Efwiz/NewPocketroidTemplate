package org.jetbrains.kotlin.cfg;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
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

/* JADX INFO: compiled from: ControlFlowBuilderAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0080\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\bH\u0016JD\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\b2\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u001f0\u001e2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020!0\u001eH\u0016J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020#2\u0006\u0010$\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020\u0013H\u0016J\u0010\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\u00112\u0006\u0010,\u001a\u00020-H\u0016J\u0018\u0010.\u001a\u00020\u00112\u0006\u0010/\u001a\u00020\b2\u0006\u00100\u001a\u000201H\u0016J \u00102\u001a\u00020\u00112\u0006\u0010/\u001a\u00020\b2\u0006\u00100\u001a\u0002012\u0006\u00103\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020\u00112\u0006\u00106\u001a\u000207H\u0016J\u0010\u00108\u001a\u00020\u00112\u0006\u00109\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020\u00112\u0006\u0010<\u001a\u00020\bH\u0016J\u0010\u0010=\u001a\u00020>2\u0006\u0010$\u001a\u00020\u0004H\u0016J\u0010\u0010?\u001a\u00020\u00112\u0006\u0010$\u001a\u00020\u0004H\u0016J\u001a\u0010@\u001a\u00020\u00112\u0006\u0010/\u001a\u00020\b2\b\u00103\u001a\u0004\u0018\u000104H\u0016J\u0010\u0010A\u001a\u00020\u00112\u0006\u0010B\u001a\u00020CH\u0016J\u0010\u0010D\u001a\u00020\u00112\u0006\u0010<\u001a\u00020\bH\u0016J\u0010\u0010E\u001a\u00020\u00112\u0006\u0010$\u001a\u00020\u0004H\u0016J\u001a\u0010F\u001a\u0002012\u0006\u0010/\u001a\u00020\b2\b\u00103\u001a\u0004\u0018\u000104H\u0016J\b\u0010G\u001a\u00020\u0011H\u0016J\u0014\u0010H\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0016J\u0012\u0010I\u001a\u0004\u0018\u00010\u00132\u0006\u0010J\u001a\u00020\u0004H\u0016J\u0012\u0010K\u001a\u0004\u0018\u00010\u00132\u0006\u0010J\u001a\u00020\u0004H\u0016J\u0012\u0010L\u001a\u0004\u0018\u00010\u00132\u0006\u0010M\u001a\u00020\bH\u0016J\u0018\u0010N\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\bH\u0016J\"\u0010O\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\b2\b\u0010P\u001a\u0004\u0018\u00010\u0016H\u0016J\"\u0010Q\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\b2\b\u0010P\u001a\u0004\u0018\u00010\u0016H\u0016J\u0010\u0010R\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\bH\u0016J\u001e\u0010S\u001a\u00020#2\u0006\u0010$\u001a\u00020T2\f\u0010U\u001a\b\u0012\u0002\b\u0003\u0018\u00010VH\u0016J\u001e\u0010W\u001a\u00020#2\u0006\u0010$\u001a\u00020X2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00160ZH\u0016J\u0010\u0010[\u001a\u00020\u00112\u0006\u0010$\u001a\u00020TH\u0016J0\u0010\\\u001a\u00020]2\u0006\u0010^\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\b2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00160Z2\u0006\u0010_\u001a\u00020`H\u0016J\u0010\u0010a\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\bH\u0016J\u001e\u0010b\u001a\u00020c2\u0006\u0010$\u001a\u00020T2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00160ZH\u0016J\u0012\u0010d\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0016J\u001e\u0010e\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130Z2\u0006\u0010\u0017\u001a\u00020\bH\u0016J\"\u0010e\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\b2\b\u0010f\u001a\u0004\u0018\u00010\u0016H\u0016J&\u0010g\u001a\u00020h2\u0006\u0010$\u001a\u00020T2\u0006\u0010i\u001a\u00020j2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00160ZH\u0016J,\u0010k\u001a\u00020l2\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010m\u001a\u00020n2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016J0\u0010o\u001a\u00020l2\u0006\u0010$\u001a\u00020T2\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016J\u0018\u0010p\u001a\u00020\u00112\u0006\u0010q\u001a\u00020\u00132\u0006\u0010r\u001a\u00020\u0013H\u0016J\u0018\u0010s\u001a\u00020\u00112\u0006\u0010t\u001a\u00020u2\u0006\u0010/\u001a\u00020\bH\u0016J \u0010v\u001a\u00020\u00112\u0006\u0010t\u001a\u00020T2\u0006\u0010v\u001a\u00020\u00162\u0006\u0010/\u001a\u00020\bH\u0016J\u0018\u0010w\u001a\u00020\u00112\u0006\u0010x\u001a\u00020y2\u0006\u0010z\u001a\u00020\u0016H\u0016J<\u0010{\u001a\u00020\u00112\u0006\u0010|\u001a\u00020\b2\u0006\u0010}\u001a\u00020\b2\u0006\u0010~\u001a\u00020\u00162\u0006\u0010m\u001a\u00020n2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\u0001X¤\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\n¨\u0006\u007f"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowBuilderAdapter;", "Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder;", "()V", "currentLoop", "Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "getCurrentLoop", "()Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "currentSubroutine", "Lorg/jetbrains/kotlin/psi/KtElement;", "getCurrentSubroutine", "()Lorg/jetbrains/kotlin/psi/KtElement;", "delegateBuilder", "getDelegateBuilder", "()Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder;", "returnSubroutine", "getReturnSubroutine", "bindLabel", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/cfg/Label;", "bindValue", "value", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "call", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/CallInstruction;", "valueElement", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "receiverValues", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "arguments", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "createAnonymousObject", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithValue;", "expression", "Lorg/jetbrains/kotlin/psi/KtObjectLiteralExpression;", "createLambda", "Lorg/jetbrains/kotlin/psi/KtFunction;", "createUnboundLabel", "name", "", "declareEntryOrObject", "entryOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "declareFunction", "subroutine", "pseudocode", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "declareInlinedFunction", "eventOccurrencesRange", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "declareParameter", "parameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "declareVariable", "property", "Lorg/jetbrains/kotlin/psi/KtVariableDeclaration;", "enterBlockScope", "block", "enterLoop", "Lorg/jetbrains/kotlin/cfg/LoopInfo;", "enterLoopBody", "enterSubroutine", "enterTryFinally", "trigger", "Lorg/jetbrains/kotlin/cfg/GenerationTrigger;", "exitBlockScope", "exitLoopBody", "exitSubroutine", "exitTryFinally", "getBoundValue", "getLoopConditionEntryPoint", "loop", "getLoopExitPoint", "getSubroutineExitPoint", "labelElement", "jump", "jumpOnFalse", "conditionValue", "jumpOnTrue", "jumpToError", "loadConstant", "Lorg/jetbrains/kotlin/psi/KtExpression;", "constant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "loadStringTemplate", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", "inputValues", "", "loadUnit", "magic", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicInstruction;", "instructionElement", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicKind;", "mark", "merge", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MergeInstruction;", "newValue", "nondeterministicJump", "inputValue", "predefinedOperation", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/OperationInstruction;", "operation", "Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder$PredefinedOperation;", "read", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/ReadValueInstruction;", "target", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "readVariable", "repeatPseudocode", "startLabel", "finishLabel", "returnNoValue", "returnExpression", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "returnValue", "throwException", "throwExpression", "Lorg/jetbrains/kotlin/psi/KtThrowExpression;", "thrownValue", "write", "assignment", "lValue", "rValue", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ControlFlowBuilderAdapter implements ControlFlowBuilder {
    protected abstract ControlFlowBuilder getDelegateBuilder();

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void loadUnit(KtExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        getDelegateBuilder().loadUnit(expression);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public InstructionWithValue loadConstant(KtExpression expression, CompileTimeConstant<?> constant) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return getDelegateBuilder().loadConstant(expression, constant);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public InstructionWithValue createAnonymousObject(KtObjectLiteralExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return getDelegateBuilder().createAnonymousObject(expression);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public InstructionWithValue createLambda(KtFunction expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return getDelegateBuilder().createLambda(expression);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public InstructionWithValue loadStringTemplate(KtStringTemplateExpression expression, List<? extends PseudoValue> inputValues) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(inputValues, "inputValues");
        return getDelegateBuilder().loadStringTemplate(expression, inputValues);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public MagicInstruction magic(KtElement instructionElement, KtElement valueElement, List<? extends PseudoValue> inputValues, MagicKind kind) {
        Intrinsics.checkNotNullParameter(instructionElement, "instructionElement");
        Intrinsics.checkNotNullParameter(inputValues, "inputValues");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return getDelegateBuilder().magic(instructionElement, valueElement, inputValues, kind);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public MergeInstruction merge(KtExpression expression, List<? extends PseudoValue> inputValues) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(inputValues, "inputValues");
        return getDelegateBuilder().merge(expression, inputValues);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public ReadValueInstruction readVariable(KtExpression expression, ResolvedCall<?> resolvedCall, Map<PseudoValue, ? extends ReceiverValue> receiverValues) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
        return getDelegateBuilder().readVariable(expression, resolvedCall, receiverValues);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public CallInstruction call(KtElement valueElement, ResolvedCall<?> resolvedCall, Map<PseudoValue, ? extends ReceiverValue> receiverValues, Map<PseudoValue, ? extends ValueParameterDescriptor> arguments) {
        Intrinsics.checkNotNullParameter(valueElement, "valueElement");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return getDelegateBuilder().call(valueElement, resolvedCall, receiverValues, arguments);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public OperationInstruction predefinedOperation(KtExpression expression, ControlFlowBuilder.PredefinedOperation operation, List<? extends PseudoValue> inputValues) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(operation, "operation");
        Intrinsics.checkNotNullParameter(inputValues, "inputValues");
        return getDelegateBuilder().predefinedOperation(expression, operation, inputValues);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public Label createUnboundLabel() {
        return getDelegateBuilder().createUnboundLabel();
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public Label createUnboundLabel(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getDelegateBuilder().createUnboundLabel(name2);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void bindLabel(Label label) {
        Intrinsics.checkNotNullParameter(label, "label");
        getDelegateBuilder().bindLabel(label);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void jump(Label label, KtElement element) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(element, "element");
        getDelegateBuilder().jump(label, element);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void jumpOnFalse(Label label, KtElement element, PseudoValue conditionValue) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(element, "element");
        getDelegateBuilder().jumpOnFalse(label, element, conditionValue);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void jumpOnTrue(Label label, KtElement element, PseudoValue conditionValue) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(element, "element");
        getDelegateBuilder().jumpOnTrue(label, element, conditionValue);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void nondeterministicJump(Label label, KtElement element, PseudoValue inputValue) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(element, "element");
        getDelegateBuilder().nondeterministicJump(label, element, inputValue);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void nondeterministicJump(List<? extends Label> label, KtElement element) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(element, "element");
        getDelegateBuilder().nondeterministicJump(label, element);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void jumpToError(KtElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        getDelegateBuilder().jumpToError(element);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void throwException(KtThrowExpression throwExpression, PseudoValue thrownValue) {
        Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
        Intrinsics.checkNotNullParameter(thrownValue, "thrownValue");
        getDelegateBuilder().throwException(throwExpression, thrownValue);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public Label getSubroutineExitPoint(KtElement labelElement) {
        Intrinsics.checkNotNullParameter(labelElement, "labelElement");
        return getDelegateBuilder().getSubroutineExitPoint(labelElement);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public Label getLoopConditionEntryPoint(KtLoopExpression loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return getDelegateBuilder().getLoopConditionEntryPoint(loop);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public Label getLoopExitPoint(KtLoopExpression loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        return getDelegateBuilder().getLoopExitPoint(loop);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public LoopInfo enterLoop(KtLoopExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return getDelegateBuilder().enterLoop(expression);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void enterLoopBody(KtLoopExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        getDelegateBuilder().enterLoopBody(expression);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void exitLoopBody(KtLoopExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        getDelegateBuilder().exitLoopBody(expression);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public KtLoopExpression getCurrentLoop() {
        return getDelegateBuilder().getCurrentLoop();
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void enterTryFinally(GenerationTrigger trigger) {
        Intrinsics.checkNotNullParameter(trigger, "trigger");
        getDelegateBuilder().enterTryFinally(trigger);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void exitTryFinally() {
        getDelegateBuilder().exitTryFinally();
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void enterSubroutine(KtElement subroutine, EventOccurrencesRange eventOccurrencesRange) {
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        getDelegateBuilder().enterSubroutine(subroutine, eventOccurrencesRange);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public Pseudocode exitSubroutine(KtElement subroutine, EventOccurrencesRange eventOccurrencesRange) {
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        return getDelegateBuilder().exitSubroutine(subroutine, eventOccurrencesRange);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public KtElement getCurrentSubroutine() {
        return getDelegateBuilder().getCurrentSubroutine();
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public KtElement getReturnSubroutine() {
        return getDelegateBuilder().getReturnSubroutine();
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void returnValue(KtExpression returnExpression, PseudoValue returnValue, KtElement subroutine) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        Intrinsics.checkNotNullParameter(returnValue, "returnValue");
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        getDelegateBuilder().returnValue(returnExpression, returnValue, subroutine);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void returnNoValue(KtReturnExpression returnExpression, KtElement subroutine) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        getDelegateBuilder().returnNoValue(returnExpression, subroutine);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public ReadValueInstruction read(KtElement element, AccessTarget target, Map<PseudoValue, ? extends ReceiverValue> receiverValues) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
        return getDelegateBuilder().read(element, target, receiverValues);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void write(KtElement assignment, KtElement lValue, PseudoValue rValue, AccessTarget target, Map<PseudoValue, ? extends ReceiverValue> receiverValues) {
        Intrinsics.checkNotNullParameter(assignment, "assignment");
        Intrinsics.checkNotNullParameter(lValue, "lValue");
        Intrinsics.checkNotNullParameter(rValue, "rValue");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
        getDelegateBuilder().write(assignment, lValue, rValue, target, receiverValues);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void declareParameter(KtParameter parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        getDelegateBuilder().declareParameter(parameter);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void declareVariable(KtVariableDeclaration property) {
        Intrinsics.checkNotNullParameter(property, "property");
        getDelegateBuilder().declareVariable(property);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void declareFunction(KtElement subroutine, Pseudocode pseudocode) {
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        Intrinsics.checkNotNullParameter(pseudocode, "pseudocode");
        getDelegateBuilder().declareFunction(subroutine, pseudocode);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void declareInlinedFunction(KtElement subroutine, Pseudocode pseudocode, EventOccurrencesRange eventOccurrencesRange) {
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        Intrinsics.checkNotNullParameter(pseudocode, "pseudocode");
        Intrinsics.checkNotNullParameter(eventOccurrencesRange, "eventOccurrencesRange");
        getDelegateBuilder().declareInlinedFunction(subroutine, pseudocode, eventOccurrencesRange);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void declareEntryOrObject(KtClassOrObject entryOrObject) {
        Intrinsics.checkNotNullParameter(entryOrObject, "entryOrObject");
        getDelegateBuilder().declareEntryOrObject(entryOrObject);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void repeatPseudocode(Label startLabel, Label finishLabel) {
        Intrinsics.checkNotNullParameter(startLabel, "startLabel");
        Intrinsics.checkNotNullParameter(finishLabel, "finishLabel");
        getDelegateBuilder().repeatPseudocode(startLabel, finishLabel);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void mark(KtElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        getDelegateBuilder().mark(element);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public PseudoValue getBoundValue(KtElement element) {
        return getDelegateBuilder().getBoundValue(element);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void bindValue(PseudoValue value, KtElement element) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(element, "element");
        getDelegateBuilder().bindValue(value, element);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public PseudoValue newValue(KtElement element) {
        return getDelegateBuilder().newValue(element);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void enterBlockScope(KtElement block) {
        Intrinsics.checkNotNullParameter(block, "block");
        getDelegateBuilder().enterBlockScope(block);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void exitBlockScope(KtElement block) {
        Intrinsics.checkNotNullParameter(block, "block");
        getDelegateBuilder().exitBlockScope(block);
    }
}
