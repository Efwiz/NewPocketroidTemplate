package org.jetbrains.kotlin.cfg.pseudocode;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.BlockInfo;
import org.jetbrains.kotlin.cfg.BreakableBlockInfo;
import org.jetbrains.kotlin.cfg.ControlFlowBuilder;
import org.jetbrains.kotlin.cfg.ControlFlowBuilderAdapter;
import org.jetbrains.kotlin.cfg.GenerationTrigger;
import org.jetbrains.kotlin.cfg.Label;
import org.jetbrains.kotlin.cfg.LoopInfo;
import org.jetbrains.kotlin.cfg.SubroutineInfo;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessTarget;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.CallInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithValue;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.LoadUnitValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicKind;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MergeInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.OperationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.ReadValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.WriteValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ConditionalJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.NondeterministicJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ReturnNoValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ReturnValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ThrowExceptionInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.UnconditionalJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.InlinedLocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.MarkInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineEnterInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineExitInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineSinkInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.VariableDeclarationInstruction;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDoWhileExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
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

/* JADX INFO: compiled from: ControlFlowInstructionsGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002%&B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00142\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u001a\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020\u00142\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\f\u0010 \u001a\u00060\u000bR\u00020\u0000H\u0002J \u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u00142\u0006\u0010#\u001a\u00020$H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\n\u001a\f\u0012\b\u0012\u00060\u000bR\u00020\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\t8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00120\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/ControlFlowInstructionsGenerator;", "Lorg/jetbrains/kotlin/cfg/ControlFlowBuilderAdapter;", "()V", "allBlocks", "Lorg/jetbrains/kotlin/com/intellij/util/containers/Stack;", "Lorg/jetbrains/kotlin/cfg/BlockInfo;", "blockScopes", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "builder", "Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder;", "builders", "Lorg/jetbrains/kotlin/cfg/pseudocode/ControlFlowInstructionsGenerator$ControlFlowInstructionsGeneratorWorker;", "delegateBuilder", "getDelegateBuilder", "()Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder;", "elementToLoopInfo", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "Lorg/jetbrains/kotlin/cfg/LoopInfo;", "elementToSubroutineInfo", "Lorg/jetbrains/kotlin/psi/KtElement;", "Lorg/jetbrains/kotlin/cfg/SubroutineInfo;", "labelCount", "", "loopInfo", "enterSubroutine", "", "subroutine", "eventOccurrencesRange", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "exitSubroutine", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "popBuilder", "pushBuilder", "scopingElement", "shouldInline", "", "ControlFlowInstructionsGeneratorWorker", "TryFinallyBlockInfo", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowInstructionsGenerator extends ControlFlowBuilderAdapter {
    private ControlFlowBuilder builder;
    private int labelCount;
    private final Stack<LoopInfo> loopInfo = new Stack<>();
    private final Stack<BlockScope> blockScopes = new Stack<>();
    private final HashMap<KtLoopExpression, LoopInfo> elementToLoopInfo = new HashMap<>();
    private final HashMap<KtElement, SubroutineInfo> elementToSubroutineInfo = new HashMap<>();
    private final Stack<ControlFlowInstructionsGeneratorWorker> builders = new Stack<>();
    private final Stack<BlockInfo> allBlocks = new Stack<>();

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilderAdapter
    protected ControlFlowBuilder getDelegateBuilder() {
        ControlFlowBuilder controlFlowBuilder = this.builder;
        if (controlFlowBuilder != null) {
            return controlFlowBuilder;
        }
        throw new AssertionError("Builder stack is empty in ControlFlowInstructionsGenerator!");
    }

    private final void pushBuilder(KtElement scopingElement, KtElement subroutine, boolean shouldInline) {
        ControlFlowInstructionsGeneratorWorker controlFlowInstructionsGeneratorWorker = new ControlFlowInstructionsGeneratorWorker(this, scopingElement, subroutine, shouldInline);
        this.builders.push(controlFlowInstructionsGeneratorWorker);
        this.builder = controlFlowInstructionsGeneratorWorker;
    }

    private final ControlFlowInstructionsGeneratorWorker popBuilder() {
        ControlFlowInstructionsGeneratorWorker controlFlowInstructionsGeneratorWorkerPop = this.builders.pop();
        this.builder = !this.builders.isEmpty() ? this.builders.peek() : null;
        Intrinsics.checkNotNull(controlFlowInstructionsGeneratorWorkerPop);
        return controlFlowInstructionsGeneratorWorkerPop;
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilderAdapter, org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public void enterSubroutine(KtElement subroutine, EventOccurrencesRange eventOccurrencesRange) {
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        ControlFlowBuilder controlFlowBuilder = this.builder;
        boolean z = eventOccurrencesRange != null;
        if (controlFlowBuilder != null && (subroutine instanceof KtFunctionLiteral)) {
            pushBuilder(subroutine, controlFlowBuilder.getReturnSubroutine(), z);
        } else {
            pushBuilder(subroutine, subroutine, z);
        }
        getDelegateBuilder().enterBlockScope(subroutine);
        ControlFlowBuilder.enterSubroutine$default(getDelegateBuilder(), subroutine, null, 2, null);
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilderAdapter, org.jetbrains.kotlin.cfg.ControlFlowBuilder
    public Pseudocode exitSubroutine(KtElement subroutine, EventOccurrencesRange eventOccurrencesRange) {
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        super.exitSubroutine(subroutine, eventOccurrencesRange);
        getDelegateBuilder().exitBlockScope(subroutine);
        ControlFlowInstructionsGeneratorWorker controlFlowInstructionsGeneratorWorkerPopBuilder = popBuilder();
        if (!this.builders.empty()) {
            ControlFlowInstructionsGeneratorWorker controlFlowInstructionsGeneratorWorkerPeek = this.builders.peek();
            if (eventOccurrencesRange == null) {
                controlFlowInstructionsGeneratorWorkerPeek.declareFunction(subroutine, controlFlowInstructionsGeneratorWorkerPopBuilder.getPseudocode());
            } else {
                controlFlowInstructionsGeneratorWorkerPeek.declareInlinedFunction(subroutine, controlFlowInstructionsGeneratorWorkerPopBuilder.getPseudocode(), eventOccurrencesRange);
            }
        }
        return controlFlowInstructionsGeneratorWorkerPopBuilder.getPseudocode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ControlFlowInstructionsGenerator.kt */
    @Metadata(d1 = {"\u0000£\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0001\u001c\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u0014H\u0016J\u0018\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0003H\u0016JD\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00032\n\u0010+\u001a\u0006\u0012\u0002\b\u00030,2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020/0.2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u0002010.H\u0016J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016J\u0010\u00106\u001a\u0002032\u0006\u00104\u001a\u000207H\u0016J\b\u00108\u001a\u00020\u0014H\u0016J\u0010\u00108\u001a\u00020\u00142\u0006\u00109\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020\u001f2\u0006\u0010<\u001a\u00020=H\u0016J\u0018\u0010>\u001a\u00020\u001f2\u0006\u0010?\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020@H\u0016J \u0010A\u001a\u00020\u001f2\u0006\u0010?\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020@2\u0006\u0010B\u001a\u00020CH\u0016J\u0010\u0010D\u001a\u00020\u001f2\u0006\u0010E\u001a\u00020FH\u0016J\u0010\u0010G\u001a\u00020\u001f2\u0006\u0010H\u001a\u00020IH\u0016J\u0010\u0010J\u001a\u00020\u001f2\u0006\u0010K\u001a\u00020\u0003H\u0016J\u0010\u0010L\u001a\u00020M2\u0006\u00104\u001a\u00020\tH\u0016J\u0010\u0010N\u001a\u00020\u001f2\u0006\u00104\u001a\u00020\tH\u0016J\u001a\u0010O\u001a\u00020\u001f2\u0006\u0010?\u001a\u00020\u00032\b\u0010B\u001a\u0004\u0018\u00010CH\u0016J\u0010\u0010P\u001a\u00020\u001f2\u0006\u0010Q\u001a\u00020RH\u0016J\u0010\u0010S\u001a\u00020\u001f2\u0006\u0010K\u001a\u00020\u0003H\u0016J\u0010\u0010T\u001a\u00020\u001f2\u0006\u00104\u001a\u00020\tH\u0016J\u001a\u0010U\u001a\u00020@2\u0006\u0010?\u001a\u00020\u00032\b\u0010B\u001a\u0004\u0018\u00010CH\u0016J\b\u0010V\u001a\u00020\u001fH\u0016J\u0014\u0010W\u001a\u0004\u0018\u00010&2\b\u0010'\u001a\u0004\u0018\u00010\u0003H\u0016J\u0012\u0010X\u001a\u0004\u0018\u00010\u00142\u0006\u0010Y\u001a\u00020\tH\u0016J\u0012\u0010Z\u001a\u0004\u0018\u00010\u00142\u0006\u0010Y\u001a\u00020\tH\u0016J\u0010\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020^H\u0002J\u0012\u0010_\u001a\u0004\u0018\u00010\u00142\u0006\u0010`\u001a\u00020\u0003H\u0016J\u0010\u0010a\u001a\u00020\u001f2\u0006\u0010b\u001a\u00020\u0014H\u0002J\u0018\u0010c\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u0003H\u0016J\"\u0010d\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u00032\b\u0010e\u001a\u0004\u0018\u00010&H\u0016J\"\u0010f\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u00032\b\u0010e\u001a\u0004\u0018\u00010&H\u0016J\u0010\u0010g\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020\u0003H\u0016J\u001e\u0010h\u001a\u0002032\u0006\u00104\u001a\u00020i2\f\u0010j\u001a\b\u0012\u0002\b\u0003\u0018\u00010kH\u0016J\u001e\u0010l\u001a\u00020m2\u0006\u00104\u001a\u00020n2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020&0pH\u0016J\u0010\u0010q\u001a\u00020\u001f2\u0006\u00104\u001a\u00020iH\u0016J0\u0010r\u001a\u00020s2\u0006\u0010t\u001a\u00020\u00032\b\u0010*\u001a\u0004\u0018\u00010\u00032\f\u0010o\u001a\b\u0012\u0004\u0012\u00020&0p2\u0006\u0010u\u001a\u00020\\H\u0016J\u0010\u0010v\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020\u0003H\u0016J\u001e\u0010w\u001a\u00020x2\u0006\u00104\u001a\u00020i2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020&0pH\u0016J\u0012\u0010y\u001a\u00020&2\b\u0010'\u001a\u0004\u0018\u00010\u0003H\u0016J\u001e\u0010z\u001a\u00020\u001f2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00140p2\u0006\u0010'\u001a\u00020\u0003H\u0016J\"\u0010z\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u00032\b\u0010{\u001a\u0004\u0018\u00010&H\u0016J&\u0010|\u001a\u00020}2\u0006\u00104\u001a\u00020i2\u0006\u0010]\u001a\u00020^2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020&0pH\u0016J-\u0010~\u001a\u0002032\u0006\u0010'\u001a\u00020\u00032\u0007\u0010\u007f\u001a\u00030\u0080\u00012\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020/0.H\u0016J6\u0010~\u001a\u0002032\u0006\u00104\u001a\u00020i2\u000e\b\u0002\u0010+\u001a\b\u0012\u0002\b\u0003\u0018\u00010,2\u0014\b\u0002\u0010-\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020/0.H\u0002J1\u0010\u0081\u0001\u001a\u0002032\u0006\u00104\u001a\u00020i2\n\u0010+\u001a\u0006\u0012\u0002\b\u00030,2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020/0.H\u0016J\u001b\u0010\u0082\u0001\u001a\u00020\u001f2\u0007\u0010\u0083\u0001\u001a\u00020\u00142\u0007\u0010\u0084\u0001\u001a\u00020\u0014H\u0016J\u001b\u0010\u0085\u0001\u001a\u00020\u001f2\b\u0010\u0086\u0001\u001a\u00030\u0087\u00012\u0006\u0010?\u001a\u00020\u0003H\u0016J#\u0010\u0088\u0001\u001a\u00020\u001f2\u0007\u0010\u0086\u0001\u001a\u00020i2\u0007\u0010\u0088\u0001\u001a\u00020&2\u0006\u0010?\u001a\u00020\u0003H\u0016J\u001c\u0010\u0089\u0001\u001a\u00020\u001f2\b\u0010\u008a\u0001\u001a\u00030\u008b\u00012\u0007\u0010\u008c\u0001\u001a\u00020&H\u0016JA\u0010\u008d\u0001\u001a\u00020\u001f2\u0007\u0010\u008e\u0001\u001a\u00020\u00032\u0007\u0010\u008f\u0001\u001a\u00020\u00032\u0007\u0010\u0090\u0001\u001a\u00020&2\u0007\u0010\u007f\u001a\u00030\u0080\u00012\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020/0.H\u0016R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u000e\u0010\u001a\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001d¨\u0006\u0091\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/ControlFlowInstructionsGenerator$ControlFlowInstructionsGeneratorWorker;", "Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder;", "scopingElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "returnSubroutine", "shouldInline", "", "(Lorg/jetbrains/kotlin/cfg/pseudocode/ControlFlowInstructionsGenerator;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/psi/KtElement;Z)V", "currentLoop", "Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "getCurrentLoop", "()Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "currentScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "getCurrentScope", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "currentSubroutine", "getCurrentSubroutine", "()Lorg/jetbrains/kotlin/psi/KtElement;", "error", "Lorg/jetbrains/kotlin/cfg/Label;", "pseudocode", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudocodeImpl;", "getPseudocode", "()Lorg/jetbrains/kotlin/cfg/pseudocode/PseudocodeImpl;", "getReturnSubroutine", "sink", "valueFactory", "org/jetbrains/kotlin/cfg/pseudocode/ControlFlowInstructionsGenerator$ControlFlowInstructionsGeneratorWorker$valueFactory$1", "Lorg/jetbrains/kotlin/cfg/pseudocode/ControlFlowInstructionsGenerator$ControlFlowInstructionsGeneratorWorker$valueFactory$1;", ProductAction.ACTION_ADD, "", "instruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "bindLabel", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "bindValue", "value", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "call", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/CallInstruction;", "valueElement", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "receiverValues", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "arguments", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "createAnonymousObject", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/ReadValueInstruction;", "expression", "Lorg/jetbrains/kotlin/psi/KtObjectLiteralExpression;", "createLambda", "Lorg/jetbrains/kotlin/psi/KtFunction;", "createUnboundLabel", "name", "", "declareEntryOrObject", "entryOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "declareFunction", "subroutine", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "declareInlinedFunction", "eventOccurrencesRange", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "declareParameter", "parameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "declareVariable", "property", "Lorg/jetbrains/kotlin/psi/KtVariableDeclaration;", "enterBlockScope", "block", "enterLoop", "Lorg/jetbrains/kotlin/cfg/LoopInfo;", "enterLoopBody", "enterSubroutine", "enterTryFinally", "trigger", "Lorg/jetbrains/kotlin/cfg/GenerationTrigger;", "exitBlockScope", "exitLoopBody", "exitSubroutine", "exitTryFinally", "getBoundValue", "getLoopConditionEntryPoint", "loop", "getLoopExitPoint", "getMagicKind", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicKind;", "operation", "Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder$PredefinedOperation;", "getSubroutineExitPoint", "labelElement", "handleJumpInsideTryFinally", "jumpTarget", "jump", "jumpOnFalse", "conditionValue", "jumpOnTrue", "jumpToError", "loadConstant", "Lorg/jetbrains/kotlin/psi/KtExpression;", "constant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "loadStringTemplate", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithValue;", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", "inputValues", "", "loadUnit", "magic", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicInstruction;", "instructionElement", Namer.METADATA_CLASS_KIND, "mark", "merge", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MergeInstruction;", "newValue", "nondeterministicJump", "inputValue", "predefinedOperation", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/OperationInstruction;", "read", "target", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "readVariable", "repeatPseudocode", "startLabel", "finishLabel", "returnNoValue", "returnExpression", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "returnValue", "throwException", "throwExpression", "Lorg/jetbrains/kotlin/psi/KtThrowExpression;", "thrownValue", "write", "assignment", "lValue", "rValue", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class ControlFlowInstructionsGeneratorWorker implements ControlFlowBuilder {
        private final Label error;
        private final PseudocodeImpl pseudocode;
        private final KtElement returnSubroutine;
        private final Label sink;
        final /* synthetic */ ControlFlowInstructionsGenerator this$0;
        private final ControlFlowInstructionsGenerator$ControlFlowInstructionsGeneratorWorker$valueFactory$1 valueFactory;

        /* JADX INFO: compiled from: ControlFlowInstructionsGenerator.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ControlFlowBuilder.PredefinedOperation.values().length];
                try {
                    iArr[ControlFlowBuilder.PredefinedOperation.AND.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ControlFlowBuilder.PredefinedOperation.OR.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ControlFlowBuilder.PredefinedOperation.NOT_NULL_ASSERTION.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public ControlFlowInstructionsGeneratorWorker(ControlFlowInstructionsGenerator controlFlowInstructionsGenerator, KtElement scopingElement, KtElement returnSubroutine, boolean z) {
            Intrinsics.checkNotNullParameter(scopingElement, "scopingElement");
            Intrinsics.checkNotNullParameter(returnSubroutine, "returnSubroutine");
            this.this$0 = controlFlowInstructionsGenerator;
            this.returnSubroutine = returnSubroutine;
            PseudocodeImpl pseudocodeImpl = new PseudocodeImpl(scopingElement, z);
            this.pseudocode = pseudocodeImpl;
            this.error = pseudocodeImpl.createLabel("error", null);
            this.sink = pseudocodeImpl.createLabel("sink", null);
            this.valueFactory = new ControlFlowInstructionsGenerator$ControlFlowInstructionsGeneratorWorker$valueFactory$1(this);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public /* bridge */ /* synthetic */ InstructionWithValue loadConstant(KtExpression ktExpression, CompileTimeConstant compileTimeConstant) {
            return loadConstant(ktExpression, (CompileTimeConstant<?>) compileTimeConstant);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public KtElement getReturnSubroutine() {
            return this.returnSubroutine;
        }

        public final PseudocodeImpl getPseudocode() {
            return this.pseudocode;
        }

        private final void add(Instruction instruction) {
            this.pseudocode.addInstruction(instruction);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public Label createUnboundLabel() {
            PseudocodeImpl pseudocodeImpl = this.pseudocode;
            StringBuilder sb = new StringBuilder("L");
            ControlFlowInstructionsGenerator controlFlowInstructionsGenerator = this.this$0;
            int i = controlFlowInstructionsGenerator.labelCount;
            controlFlowInstructionsGenerator.labelCount = i + 1;
            return pseudocodeImpl.createLabel(sb.append(i).toString(), null);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public Label createUnboundLabel(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            PseudocodeImpl pseudocodeImpl = this.pseudocode;
            StringBuilder sb = new StringBuilder("L");
            ControlFlowInstructionsGenerator controlFlowInstructionsGenerator = this.this$0;
            int i = controlFlowInstructionsGenerator.labelCount;
            controlFlowInstructionsGenerator.labelCount = i + 1;
            return pseudocodeImpl.createLabel(sb.append(i).toString(), name2);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public LoopInfo enterLoop(KtLoopExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            if (expression instanceof KtDoWhileExpression) {
                Pseudocode rootPseudocode = this.pseudocode.getRootPseudocode();
                Intrinsics.checkNotNull(rootPseudocode, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.pseudocode.PseudocodeImpl");
                ((PseudocodeImpl) rootPseudocode).setContainsDoWhile$cfg(true);
            }
            LoopInfo loopInfo = new LoopInfo(expression, createUnboundLabel("loop entry point"), createUnboundLabel("loop exit point"), createUnboundLabel("body entry point"), createUnboundLabel("body exit point"), createUnboundLabel("condition entry point"));
            bindLabel(loopInfo.getEntryPoint());
            this.this$0.elementToLoopInfo.put(expression, loopInfo);
            return loopInfo;
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void enterLoopBody(KtLoopExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Object obj = this.this$0.elementToLoopInfo.get(expression);
            Intrinsics.checkNotNull(obj);
            LoopInfo loopInfo = (LoopInfo) obj;
            bindLabel(loopInfo.getBodyEntryPoint());
            this.this$0.loopInfo.push(loopInfo);
            this.this$0.allBlocks.push(loopInfo);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void exitLoopBody(KtLoopExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            LoopInfo loopInfo = (LoopInfo) this.this$0.loopInfo.pop();
            this.this$0.elementToLoopInfo.remove(expression);
            this.this$0.allBlocks.pop();
            bindLabel(loopInfo.getBodyExitPoint());
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public KtLoopExpression getCurrentLoop() {
            if (this.this$0.loopInfo.empty()) {
                return null;
            }
            return ((LoopInfo) this.this$0.loopInfo.peek()).getElement();
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void enterSubroutine(KtElement subroutine, EventOccurrencesRange eventOccurrencesRange) {
            Intrinsics.checkNotNullParameter(subroutine, "subroutine");
            SubroutineInfo subroutineInfo = new SubroutineInfo(subroutine, createUnboundLabel(), createUnboundLabel());
            this.this$0.elementToSubroutineInfo.put(subroutine, subroutineInfo);
            this.this$0.allBlocks.push(subroutineInfo);
            bindLabel(subroutineInfo.getEntryPoint());
            add(new SubroutineEnterInstruction(subroutine, getCurrentScope()));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public KtElement getCurrentSubroutine() {
            return this.pseudocode.getCorrespondingElement();
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public Label getLoopConditionEntryPoint(KtLoopExpression loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            LoopInfo loopInfo = (LoopInfo) this.this$0.elementToLoopInfo.get(loop);
            if (loopInfo != null) {
                return loopInfo.getConditionEntryPoint();
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public Label getLoopExitPoint(KtLoopExpression loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            LoopInfo loopInfo = (LoopInfo) this.this$0.elementToLoopInfo.get(loop);
            if (loopInfo != null) {
                return loopInfo.getExitPoint();
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public Label getSubroutineExitPoint(KtElement labelElement) {
            Intrinsics.checkNotNullParameter(labelElement, "labelElement");
            SubroutineInfo subroutineInfo = (SubroutineInfo) this.this$0.elementToSubroutineInfo.get(labelElement);
            if (subroutineInfo != null) {
                return subroutineInfo.getExitPoint();
            }
            return null;
        }

        private final BlockScope getCurrentScope() {
            Object objPeek = this.this$0.blockScopes.peek();
            Intrinsics.checkNotNullExpressionValue(objPeek, "peek(...)");
            return (BlockScope) objPeek;
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void enterBlockScope(KtElement block) {
            Intrinsics.checkNotNullParameter(block, "block");
            this.this$0.blockScopes.push(new BlockScope(this.this$0.blockScopes.isEmpty() ? null : getCurrentScope(), block));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void exitBlockScope(KtElement block) {
            Intrinsics.checkNotNullParameter(block, "block");
            getCurrentScope().getBlock();
            this.this$0.blockScopes.pop();
        }

        private final void handleJumpInsideTryFinally(Label jumpTarget) {
            ArrayList arrayList = new ArrayList();
            for (BlockInfo blockInfo : CollectionsKt.asReversedMutable(this.this$0.allBlocks)) {
                if (blockInfo instanceof BreakableBlockInfo) {
                    if (((BreakableBlockInfo) blockInfo).getReferablePoints().contains(jumpTarget) || jumpTarget == this.error) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            ((TryFinallyBlockInfo) it.next()).generateFinallyBlock();
                        }
                        return;
                    }
                } else if (blockInfo instanceof TryFinallyBlockInfo) {
                    arrayList.add(blockInfo);
                }
            }
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public Pseudocode exitSubroutine(KtElement subroutine, EventOccurrencesRange eventOccurrencesRange) {
            Intrinsics.checkNotNullParameter(subroutine, "subroutine");
            Label subroutineExitPoint = getSubroutineExitPoint(subroutine);
            if (subroutineExitPoint != null) {
                bindLabel(subroutineExitPoint);
            }
            this.pseudocode.addExitInstruction(new SubroutineExitInstruction(subroutine, getCurrentScope(), false));
            bindLabel(this.error);
            this.pseudocode.addErrorInstruction(new SubroutineExitInstruction(subroutine, getCurrentScope(), true));
            bindLabel(this.sink);
            this.pseudocode.addSinkInstruction(new SubroutineSinkInstruction(subroutine, getCurrentScope(), "<SINK>"));
            this.this$0.elementToSubroutineInfo.remove(subroutine);
            this.this$0.allBlocks.pop();
            return this.pseudocode;
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void mark(KtElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            add(new MarkInstruction(element, getCurrentScope()));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public PseudoValue getBoundValue(KtElement element) {
            return this.pseudocode.getElementValue(element);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void bindValue(PseudoValue value, KtElement element) {
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(element, "element");
            this.pseudocode.bindElementToValue(element, value);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public PseudoValue newValue(KtElement element) {
            return this.valueFactory.newValue(element, null);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void returnValue(KtExpression returnExpression, PseudoValue returnValue, KtElement subroutine) {
            Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
            Intrinsics.checkNotNullParameter(returnValue, "returnValue");
            Intrinsics.checkNotNullParameter(subroutine, "subroutine");
            Label subroutineExitPoint = getSubroutineExitPoint(subroutine);
            if (subroutineExitPoint == null) {
                return;
            }
            handleJumpInsideTryFinally(subroutineExitPoint);
            add(new ReturnValueInstruction(returnExpression, getCurrentScope(), subroutineExitPoint, returnValue, subroutine));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void returnNoValue(KtReturnExpression returnExpression, KtElement subroutine) {
            Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
            Intrinsics.checkNotNullParameter(subroutine, "subroutine");
            Label subroutineExitPoint = getSubroutineExitPoint(subroutine);
            if (subroutineExitPoint == null) {
                return;
            }
            handleJumpInsideTryFinally(subroutineExitPoint);
            add(new ReturnNoValueInstruction(returnExpression, getCurrentScope(), subroutineExitPoint, subroutine));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void write(KtElement assignment, KtElement lValue, PseudoValue rValue, AccessTarget target, Map<PseudoValue, ? extends ReceiverValue> receiverValues) {
            Intrinsics.checkNotNullParameter(assignment, "assignment");
            Intrinsics.checkNotNullParameter(lValue, "lValue");
            Intrinsics.checkNotNullParameter(rValue, "rValue");
            Intrinsics.checkNotNullParameter(target, "target");
            Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
            add(new WriteValueInstruction(assignment, getCurrentScope(), target, receiverValues, lValue, rValue));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void declareParameter(KtParameter parameter) {
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            add(new VariableDeclarationInstruction(parameter, getCurrentScope()));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void declareVariable(KtVariableDeclaration property) {
            Intrinsics.checkNotNullParameter(property, "property");
            add(new VariableDeclarationInstruction(property, getCurrentScope()));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void declareFunction(KtElement subroutine, Pseudocode pseudocode) {
            Intrinsics.checkNotNullParameter(subroutine, "subroutine");
            Intrinsics.checkNotNullParameter(pseudocode, "pseudocode");
            add(new LocalFunctionDeclarationInstruction(subroutine, pseudocode, getCurrentScope()));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void declareInlinedFunction(KtElement subroutine, Pseudocode pseudocode, EventOccurrencesRange eventOccurrencesRange) {
            Intrinsics.checkNotNullParameter(subroutine, "subroutine");
            Intrinsics.checkNotNullParameter(pseudocode, "pseudocode");
            Intrinsics.checkNotNullParameter(eventOccurrencesRange, "eventOccurrencesRange");
            add(new InlinedLocalFunctionDeclarationInstruction(subroutine, pseudocode, getCurrentScope(), eventOccurrencesRange));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void declareEntryOrObject(KtClassOrObject entryOrObject) {
            Intrinsics.checkNotNullParameter(entryOrObject, "entryOrObject");
            add(new VariableDeclarationInstruction(entryOrObject, getCurrentScope()));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void loadUnit(KtExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            add(new LoadUnitValueInstruction(expression, getCurrentScope()));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void jump(Label label, KtElement element) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(element, "element");
            handleJumpInsideTryFinally(label);
            add(new UnconditionalJumpInstruction(element, label, getCurrentScope()));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void jumpOnFalse(Label label, KtElement element, PseudoValue conditionValue) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(element, "element");
            handleJumpInsideTryFinally(label);
            add(new ConditionalJumpInstruction(element, false, getCurrentScope(), label, conditionValue));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void jumpOnTrue(Label label, KtElement element, PseudoValue conditionValue) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(element, "element");
            handleJumpInsideTryFinally(label);
            add(new ConditionalJumpInstruction(element, true, getCurrentScope(), label, conditionValue));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void bindLabel(Label label) {
            Intrinsics.checkNotNullParameter(label, "label");
            this.pseudocode.bindLabel((PseudocodeLabel) label);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void nondeterministicJump(Label label, KtElement element, PseudoValue inputValue) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(element, "element");
            handleJumpInsideTryFinally(label);
            add(new NondeterministicJumpInstruction(element, CollectionsKt.listOf(label), getCurrentScope(), inputValue));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void nondeterministicJump(List<? extends Label> label, KtElement element) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(element, "element");
            add(new NondeterministicJumpInstruction(element, label, getCurrentScope(), null));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void jumpToError(KtElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            handleJumpInsideTryFinally(this.error);
            add(new UnconditionalJumpInstruction(element, this.error, getCurrentScope()));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void enterTryFinally(GenerationTrigger trigger) {
            Intrinsics.checkNotNullParameter(trigger, "trigger");
            this.this$0.allBlocks.push(new TryFinallyBlockInfo(trigger));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void throwException(KtThrowExpression throwExpression, PseudoValue thrownValue) {
            Intrinsics.checkNotNullParameter(throwExpression, "throwExpression");
            Intrinsics.checkNotNullParameter(thrownValue, "thrownValue");
            handleJumpInsideTryFinally(this.error);
            add(new ThrowExceptionInstruction(throwExpression, getCurrentScope(), this.error, thrownValue));
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void exitTryFinally() {
            boolean z = ((BlockInfo) this.this$0.allBlocks.pop()) instanceof TryFinallyBlockInfo;
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public void repeatPseudocode(Label startLabel, Label finishLabel) {
            Intrinsics.checkNotNullParameter(startLabel, "startLabel");
            Intrinsics.checkNotNullParameter(finishLabel, "finishLabel");
            ControlFlowInstructionsGenerator controlFlowInstructionsGenerator = this.this$0;
            controlFlowInstructionsGenerator.labelCount = this.pseudocode.repeatPart(startLabel, finishLabel, controlFlowInstructionsGenerator.labelCount);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public ReadValueInstruction loadConstant(KtExpression expression, CompileTimeConstant<?> constant) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return read$default(this, expression, null, null, 6, null);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public ReadValueInstruction createAnonymousObject(KtObjectLiteralExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return read$default(this, expression, null, null, 6, null);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public ReadValueInstruction createLambda(KtFunction expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            boolean z = expression instanceof KtFunctionLiteral;
            KtExpression ktExpression = expression;
            if (z) {
                PsiElement parent = expression.getLightParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtLambdaExpression");
                ktExpression = (KtLambdaExpression) parent;
            }
            return read$default(this, ktExpression, null, null, 6, null);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public InstructionWithValue loadStringTemplate(KtStringTemplateExpression expression, List<? extends PseudoValue> inputValues) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(inputValues, "inputValues");
            if (inputValues.isEmpty()) {
                return read$default(this, expression, null, null, 6, null);
            }
            KtStringTemplateExpression ktStringTemplateExpression = expression;
            return magic(ktStringTemplateExpression, ktStringTemplateExpression, inputValues, MagicKind.STRING_TEMPLATE);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public MagicInstruction magic(KtElement instructionElement, KtElement valueElement, List<? extends PseudoValue> inputValues, MagicKind kind) {
            Intrinsics.checkNotNullParameter(instructionElement, "instructionElement");
            Intrinsics.checkNotNullParameter(inputValues, "inputValues");
            Intrinsics.checkNotNullParameter(kind, "kind");
            MagicInstruction magicInstruction = new MagicInstruction(instructionElement, valueElement, getCurrentScope(), inputValues, kind, this.valueFactory);
            add(magicInstruction);
            return magicInstruction;
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public MergeInstruction merge(KtExpression expression, List<? extends PseudoValue> inputValues) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(inputValues, "inputValues");
            MergeInstruction mergeInstruction = new MergeInstruction(expression, getCurrentScope(), inputValues, this.valueFactory);
            add(mergeInstruction);
            return mergeInstruction;
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public ReadValueInstruction readVariable(KtExpression expression, ResolvedCall<?> resolvedCall, Map<PseudoValue, ? extends ReceiverValue> receiverValues) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
            return read(expression, resolvedCall, receiverValues);
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public CallInstruction call(KtElement valueElement, ResolvedCall<?> resolvedCall, Map<PseudoValue, ? extends ReceiverValue> receiverValues, Map<PseudoValue, ? extends ValueParameterDescriptor> arguments) {
            Intrinsics.checkNotNullParameter(valueElement, "valueElement");
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            CallInstruction callInstruction = new CallInstruction(valueElement, getCurrentScope(), resolvedCall, receiverValues, arguments, this.valueFactory);
            add(callInstruction);
            return callInstruction;
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public OperationInstruction predefinedOperation(KtExpression expression, ControlFlowBuilder.PredefinedOperation operation, List<? extends PseudoValue> inputValues) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(operation, "operation");
            Intrinsics.checkNotNullParameter(inputValues, "inputValues");
            KtExpression ktExpression = expression;
            return magic(ktExpression, ktExpression, inputValues, getMagicKind(operation));
        }

        private final MagicKind getMagicKind(ControlFlowBuilder.PredefinedOperation operation) {
            int i = WhenMappings.$EnumSwitchMapping$0[operation.ordinal()];
            if (i == 1) {
                return MagicKind.AND;
            }
            if (i == 2) {
                return MagicKind.OR;
            }
            if (i == 3) {
                return MagicKind.NOT_NULL_ASSERTION;
            }
            throw new NoWhenBranchMatchedException();
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowBuilder
        public ReadValueInstruction read(KtElement element, AccessTarget target, Map<PseudoValue, ? extends ReceiverValue> receiverValues) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(target, "target");
            Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
            ReadValueInstruction readValueInstruction = new ReadValueInstruction(element, getCurrentScope(), target, receiverValues, this.valueFactory);
            add(readValueInstruction);
            return readValueInstruction;
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ ReadValueInstruction read$default(ControlFlowInstructionsGeneratorWorker controlFlowInstructionsGeneratorWorker, KtExpression ktExpression, ResolvedCall resolvedCall, Map map, int i, Object obj) {
            if ((i & 2) != 0) {
                resolvedCall = null;
            }
            if ((i & 4) != 0) {
                map = MapsKt.emptyMap();
            }
            return controlFlowInstructionsGeneratorWorker.read(ktExpression, (ResolvedCall<?>) resolvedCall, (Map<PseudoValue, ? extends ReceiverValue>) map);
        }

        private final ReadValueInstruction read(KtExpression expression, ResolvedCall<?> resolvedCall, Map<PseudoValue, ? extends ReceiverValue> receiverValues) {
            return read(expression, resolvedCall != null ? new AccessTarget.Call(resolvedCall) : AccessTarget.BlackBox.INSTANCE, receiverValues);
        }
    }

    /* JADX INFO: compiled from: ControlFlowInstructionsGenerator.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/ControlFlowInstructionsGenerator$TryFinallyBlockInfo;", "Lorg/jetbrains/kotlin/cfg/BlockInfo;", "finallyBlock", "Lorg/jetbrains/kotlin/cfg/GenerationTrigger;", "(Lorg/jetbrains/kotlin/cfg/GenerationTrigger;)V", "generateFinallyBlock", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TryFinallyBlockInfo extends BlockInfo {
        private final GenerationTrigger finallyBlock;

        public TryFinallyBlockInfo(GenerationTrigger finallyBlock) {
            Intrinsics.checkNotNullParameter(finallyBlock, "finallyBlock");
            this.finallyBlock = finallyBlock;
        }

        public final void generateFinallyBlock() {
            this.finallyBlock.generate();
        }
    }
}
