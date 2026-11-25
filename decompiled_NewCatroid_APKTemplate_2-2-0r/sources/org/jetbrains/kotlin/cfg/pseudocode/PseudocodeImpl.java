package org.jetbrains.kotlin.cfg.pseudocode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cfg.Label;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionWithNext;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.KtElementInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicKind;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MergeInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.AbstractJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ConditionalJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.NondeterministicJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.InlinedLocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineEnterInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineExitInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineSinkInstruction;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.PseudocodeTraverserKt;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.TraversalOrder;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.TraverseInstructionResult;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.HashMultimap;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.com.intellij.util.containers.BidirectionalMap;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: PseudocodeImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¾\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010L\u001a\u00020M2\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010N\u001a\u00020M2\u0006\u0010\u001a\u001a\u00020\u0017J\u000e\u0010O\u001a\u00020M2\u0006\u0010P\u001a\u00020\u001eJ\u0010\u0010Q\u001a\u00020M2\u0006\u0010P\u001a\u00020RH\u0002J\u000e\u0010S\u001a\u00020M2\u0006\u0010G\u001a\u00020(J\u0018\u0010T\u001a\u00020M2\u0006\u0010U\u001a\u00020\u00112\u0006\u0010V\u001a\u00020\u001eH\u0002J\u0016\u0010W\u001a\u00020M2\u0006\u0010X\u001a\u00020\u00032\u0006\u0010U\u001a\u00020\u0011J\u000e\u0010Y\u001a\u00020M2\u0006\u0010Z\u001a\u00020*J\b\u0010[\u001a\u00020MH\u0002J\b\u0010\\\u001a\u00020MH\u0002J\b\u0010]\u001a\u00020\u0000H\u0016J$\u0010^\u001a\u00020\u001e2\u0006\u0010P\u001a\u00020\u001e2\u0012\u0010_\u001a\u000e\u0012\u0004\u0012\u00020a\u0012\u0004\u0012\u00020*0`H\u0002J0\u0010b\u001a\b\u0012\u0004\u0012\u00020a0K2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020a0c2\u0012\u0010_\u001a\u000e\u0012\u0004\u0012\u00020a\u0012\u0004\u0012\u00020*0`H\u0002J\u0018\u0010d\u001a\u00020*2\u0006\u0010e\u001a\u00020f2\b\u0010g\u001a\u0004\u0018\u00010fJ\u0014\u0010h\u001a\u0004\u0018\u00010\u00112\b\u0010X\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010i\u001a\u00020\u001e2\u0006\u0010j\u001a\u00020aH\u0002J\f\u0010k\u001a\b\u0012\u0004\u0012\u00020*0\"J\u0016\u0010.\u001a\b\u0012\u0004\u0012\u00020-0,2\u0006\u0010l\u001a\u00020\u0001H\u0002J\u0016\u0010m\u001a\b\u0012\u0004\u0012\u00020\u00110,2\u0006\u0010U\u001a\u00020\u0011H\u0002J\u0010\u0010n\u001a\u00020\u001e2\u0006\u0010o\u001a\u00020pH\u0002J\u0018\u0010q\u001a\b\u0012\u0004\u0012\u00020\u001e0K2\b\u0010U\u001a\u0004\u0018\u00010\u0011H\u0016J\u0018\u0010r\u001a\b\u0012\u0004\u0012\u00020\u00030\"2\b\u0010U\u001a\u0004\u0018\u00010\u0011H\u0016J\u0012\u0010s\u001a\u0004\u0018\u00010A2\u0006\u0010X\u001a\u00020\u0003H\u0016J\u0010\u0010t\u001a\u00020\u00052\u0006\u0010P\u001a\u00020\u001eH\u0016J\b\u0010u\u001a\u00020MH\u0002J\u0006\u0010v\u001a\u00020MJ,\u0010w\u001a\u00020p2\u0006\u0010x\u001a\u00020\u00002\b\u0010y\u001a\u0004\u0018\u00010a2\b\u0010z\u001a\u0004\u0018\u00010a2\u0006\u0010{\u001a\u00020pH\u0002J8\u0010|\u001a\u00020M2\u0006\u0010}\u001a\u00020\u001e2\u0012\u0010_\u001a\u000e\u0012\u0004\u0012\u00020a\u0012\u0004\u0012\u00020*0`2\u0012\u0010~\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020a0\u007fH\u0002J \u0010\u0080\u0001\u001a\u00020p2\u0006\u0010y\u001a\u00020a2\u0006\u0010z\u001a\u00020a2\u0007\u0010\u0081\u0001\u001a\u00020pJ\u0011\u0010\u0082\u0001\u001a\u00020M2\u0006\u0010x\u001a\u00020\u0000H\u0002J\u0015\u0010\u0083\u0001\u001a\u00020M*\u00020\u001e2\u0006\u0010o\u001a\u00020pH\u0002R$\u0010\b\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005@PX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0019R\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001e0\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0010\u0010%\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\nR\u0014\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b0\u00101\u001a\u0004\b.\u0010/R6\u00102\u001a*\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110,03j\u0014\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110,`4X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00105\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u0010 R\"\u00107\u001a\u0004\u0018\u00010\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u000e\u0010:\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R!\u0010;\u001a\u0012\u0012\u0004\u0012\u00020\u001e0<j\b\u0012\u0004\u0012\u00020\u001e`=¢\u0006\b\n\u0000\u001a\u0004\b>\u0010?R\u001a\u0010@\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020A03X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010B\u001a\b\u0012\u0004\u0012\u00020\u001e0\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bC\u0010$R\u0014\u0010D\u001a\u00020\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bE\u00109R\u001e\u0010F\u001a\u0012\u0012\u0004\u0012\u00020\u001e0<j\b\u0012\u0004\u0012\u00020\u001e`=X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010G\u001a\u00020(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u0010IR6\u0010J\u001a*\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0K03j\u0014\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0K`4X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0084\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/PseudocodeImpl;", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "correspondingElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "isInlined", "", "(Lorg/jetbrains/kotlin/psi/KtElement;Z)V", "<set-?>", "containsDoWhile", "getContainsDoWhile", "()Z", "setContainsDoWhile$cfg", "(Z)V", "getCorrespondingElement", "()Lorg/jetbrains/kotlin/psi/KtElement;", "elementsToValues", "Lorg/jetbrains/kotlin/com/intellij/util/containers/BidirectionalMap;", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "enterInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineEnterInstruction;", "getEnterInstruction", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineEnterInstruction;", "errorInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineExitInstruction;", "getErrorInstruction", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineExitInstruction;", "exitInstruction", "getExitInstruction", "instructions", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "getInstructions", "()Ljava/util/ArrayList;", "instructionsIncludingDeadCode", "", "getInstructionsIncludingDeadCode", "()Ljava/util/List;", "internalErrorInstruction", "internalExitInstruction", "internalSinkInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;", "labels", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudocodeLabel;", "localDeclarations", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/LocalFunctionDeclarationInstruction;", "getLocalDeclarations", "()Ljava/util/Set;", "localDeclarations$delegate", "Lkotlin/Lazy;", "mergedValues", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "mutableInstructionList", "getMutableInstructionList$cfg", "parent", "getParent", "()Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "postPrecessed", "reachableInstructions", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "getReachableInstructions", "()Ljava/util/HashSet;", "representativeInstructions", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/KtElementInstruction;", "reversedInstructions", "getReversedInstructions", "rootPseudocode", "getRootPseudocode", "sideEffectFree", "sinkInstruction", "getSinkInstruction", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;", "valueUsages", "", "addErrorInstruction", "", "addExitInstruction", "addInstruction", "instruction", "addMergedValues", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MergeInstruction;", "addSinkInstruction", "addValueUsage", "value", "usage", "bindElementToValue", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "bindLabel", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "collectAndCacheReachableInstructions", "collectReachableInstructions", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "copyInstruction", "originalToCopy", "", "Lorg/jetbrains/kotlin/cfg/Label;", "copyLabels", "", "createLabel", "name", "", "comment", "getElementValue", "getJumpTarget", "targetLabel", "getLabels", "pseudocode", "getMergedValues", "getNextPosition", "currentPosition", "", "getUsages", "getValueElements", "instructionForElement", "isSideEffectFree", "markDeadInstructions", "postProcess", "repeatInternal", "originalPseudocode", "startLabel", "finishLabel", "labelCountArg", "repeatLabelsBindingForInstruction", "originalInstruction", "originalLabelsForInstruction", "Lorg/jetbrains/kotlin/com/google/common/collect/Multimap;", "repeatPart", "labelCount", "repeatWhole", "processInstruction", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PseudocodeImpl implements Pseudocode {
    private boolean containsDoWhile;
    private final KtElement correspondingElement;
    private final BidirectionalMap<KtElement, PseudoValue> elementsToValues;
    private final ArrayList<Instruction> instructions;
    private SubroutineExitInstruction internalErrorInstruction;
    private SubroutineExitInstruction internalExitInstruction;
    private SubroutineSinkInstruction internalSinkInstruction;
    private final boolean isInlined;
    private final ArrayList<PseudocodeLabel> labels;

    /* JADX INFO: renamed from: localDeclarations$delegate, reason: from kotlin metadata */
    private final Lazy localDeclarations;
    private final HashMap<PseudoValue, Set<PseudoValue>> mergedValues;
    private final ArrayList<Instruction> mutableInstructionList;
    private Pseudocode parent;
    private boolean postPrecessed;
    private final HashSet<Instruction> reachableInstructions;
    private final HashMap<KtElement, KtElementInstruction> representativeInstructions;
    private final HashSet<Instruction> sideEffectFree;
    private final HashMap<PseudoValue, List<Instruction>> valueUsages;

    public PseudocodeImpl(KtElement correspondingElement, boolean z) {
        Intrinsics.checkNotNullParameter(correspondingElement, "correspondingElement");
        this.correspondingElement = correspondingElement;
        this.isInlined = z;
        this.mutableInstructionList = new ArrayList<>();
        this.instructions = new ArrayList<>();
        this.elementsToValues = new BidirectionalMap<>();
        this.valueUsages = new HashMap<>();
        this.mergedValues = new HashMap<>();
        this.sideEffectFree = new HashSet<>();
        this.localDeclarations = LazyKt.lazy(new Function0<Set<? extends LocalFunctionDeclarationInstruction>>() { // from class: org.jetbrains.kotlin.cfg.pseudocode.PseudocodeImpl$localDeclarations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends LocalFunctionDeclarationInstruction> invoke() {
                PseudocodeImpl pseudocodeImpl = this.this$0;
                return pseudocodeImpl.getLocalDeclarations(pseudocodeImpl);
            }
        });
        this.reachableInstructions = new HashSet<>();
        this.representativeInstructions = new HashMap<>();
        this.labels = new ArrayList<>();
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public KtElement getCorrespondingElement() {
        return this.correspondingElement;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    /* JADX INFO: renamed from: isInlined, reason: from getter */
    public boolean getIsInlined() {
        return this.isInlined;
    }

    public final ArrayList<Instruction> getMutableInstructionList$cfg() {
        return this.mutableInstructionList;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public ArrayList<Instruction> getInstructions() {
        return this.instructions;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public Pseudocode getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public Set<LocalFunctionDeclarationInstruction> getLocalDeclarations() {
        return (Set) this.localDeclarations.getValue();
    }

    public final HashSet<Instruction> getReachableInstructions() {
        return this.reachableInstructions;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public SubroutineExitInstruction getExitInstruction() {
        SubroutineExitInstruction subroutineExitInstruction = this.internalExitInstruction;
        if (subroutineExitInstruction != null) {
            return subroutineExitInstruction;
        }
        throw new AssertionError("Exit instruction is read before initialization");
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public SubroutineSinkInstruction getSinkInstruction() {
        SubroutineSinkInstruction subroutineSinkInstruction = this.internalSinkInstruction;
        if (subroutineSinkInstruction != null) {
            return subroutineSinkInstruction;
        }
        throw new AssertionError("Sink instruction is read before initialization");
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public SubroutineExitInstruction getErrorInstruction() {
        SubroutineExitInstruction subroutineExitInstruction = this.internalErrorInstruction;
        if (subroutineExitInstruction != null) {
            return subroutineExitInstruction;
        }
        throw new AssertionError("Error instruction is read before initialization");
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public boolean getContainsDoWhile() {
        return this.containsDoWhile;
    }

    public void setContainsDoWhile$cfg(boolean z) {
        this.containsDoWhile = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<LocalFunctionDeclarationInstruction> getLocalDeclarations(Pseudocode pseudocode) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Intrinsics.checkNotNull(pseudocode, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.pseudocode.PseudocodeImpl");
        for (Instruction instruction : ((PseudocodeImpl) pseudocode).mutableInstructionList) {
            if (instruction instanceof LocalFunctionDeclarationInstruction) {
                linkedHashSet.add(instruction);
                linkedHashSet.addAll(getLocalDeclarations(((LocalFunctionDeclarationInstruction) instruction).getBody()));
            }
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public Pseudocode getRootPseudocode() {
        for (Pseudocode parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent.getParent() == null) {
                return parent;
            }
        }
        return this;
    }

    public final PseudocodeLabel createLabel(String name2, String comment) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PseudocodeLabel pseudocodeLabel = new PseudocodeLabel(this, name2, comment);
        this.labels.add(pseudocodeLabel);
        return pseudocodeLabel;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public List<Instruction> getReversedInstructions() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        PseudocodeTraverserKt.traverseFollowingInstructions((Instruction) (getIsInlined() ? CollectionsKt.last((List) getInstructions()) : getSinkInstruction()), linkedHashSet2, TraversalOrder.BACKWARD, null);
        if (linkedHashSet.size() < getInstructions().size()) {
            for (Instruction instruction : CollectionsKt.reversed(getInstructions())) {
                if (!linkedHashSet.contains(instruction)) {
                    PseudocodeTraverserKt.traverseFollowingInstructions(instruction, linkedHashSet2, TraversalOrder.BACKWARD, null);
                }
            }
        }
        return CollectionsKt.toList(linkedHashSet);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public List<Instruction> getInstructionsIncludingDeadCode() {
        return this.mutableInstructionList;
    }

    public final List<PseudocodeLabel> getLabels() {
        return this.labels;
    }

    public final void addExitInstruction(SubroutineExitInstruction exitInstruction) {
        Intrinsics.checkNotNullParameter(exitInstruction, "exitInstruction");
        addInstruction(exitInstruction);
        this.internalExitInstruction = exitInstruction;
    }

    public final void addSinkInstruction(SubroutineSinkInstruction sinkInstruction) {
        Intrinsics.checkNotNullParameter(sinkInstruction, "sinkInstruction");
        addInstruction(sinkInstruction);
        this.internalSinkInstruction = sinkInstruction;
    }

    public final void addErrorInstruction(SubroutineExitInstruction errorInstruction) {
        Intrinsics.checkNotNullParameter(errorInstruction, "errorInstruction");
        addInstruction(errorInstruction);
        this.internalErrorInstruction = errorInstruction;
    }

    public final void addInstruction(Instruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        this.mutableInstructionList.add(instruction);
        instruction.setOwner(this);
        if (instruction instanceof KtElementInstruction) {
            KtElement element = ((KtElementInstruction) instruction).getElement();
            if (!this.representativeInstructions.containsKey(element)) {
                this.representativeInstructions.put(element, instruction);
            }
        }
        if (instruction instanceof MergeInstruction) {
            addMergedValues((MergeInstruction) instruction);
        }
        for (PseudoValue pseudoValue : instruction.getInputValues()) {
            addValueUsage(pseudoValue, instruction);
            Iterator<PseudoValue> it = getMergedValues(pseudoValue).iterator();
            while (it.hasNext()) {
                addValueUsage(it.next(), instruction);
            }
        }
        if (PseudocodeUtilsKt.calcSideEffectFree(instruction)) {
            this.sideEffectFree.add(instruction);
        }
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public SubroutineEnterInstruction getEnterInstruction() {
        Instruction instruction = this.mutableInstructionList.get(0);
        Intrinsics.checkNotNull(instruction, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineEnterInstruction");
        return (SubroutineEnterInstruction) instruction;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public PseudoValue getElementValue(KtElement element) {
        return this.elementsToValues.get(element);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public List<KtElement> getValueElements(PseudoValue value) {
        List<KtElement> keysByValue = this.elementsToValues.getKeysByValue(value);
        return keysByValue == null ? CollectionsKt.emptyList() : keysByValue;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public List<Instruction> getUsages(PseudoValue value) {
        List<Instruction> list = this.valueUsages.get(value);
        return list == null ? new ArrayList() : list;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public boolean isSideEffectFree(Instruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return this.sideEffectFree.contains(instruction);
    }

    public final void bindElementToValue(KtElement element, PseudoValue value) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(value, "value");
        this.elementsToValues.put(element, value);
    }

    public final void bindLabel(PseudocodeLabel label) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.areEqual(this, label.getPseudocode());
        label.setTargetInstructionIndex(this.mutableInstructionList.size());
    }

    private final Set<PseudoValue> getMergedValues(PseudoValue value) {
        Set<PseudoValue> set = this.mergedValues.get(value);
        return set == null ? SetsKt.emptySet() : set;
    }

    private final void addMergedValues(MergeInstruction instruction) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (PseudoValue pseudoValue : instruction.getInputValues()) {
            linkedHashSet.addAll(getMergedValues(pseudoValue));
            linkedHashSet.add(pseudoValue);
        }
        this.mergedValues.put(instruction.getResultValue(), linkedHashSet);
    }

    private final void addValueUsage(PseudoValue value, Instruction usage) {
        if (usage instanceof MergeInstruction) {
            return;
        }
        HashMap<PseudoValue, List<Instruction>> map = this.valueUsages;
        ArrayList arrayList = map.get(value);
        if (arrayList == null) {
            arrayList = new ArrayList();
            map.put(value, arrayList);
        }
        arrayList.add(usage);
    }

    public final void postProcess() {
        if (this.postPrecessed) {
            return;
        }
        this.postPrecessed = true;
        getErrorInstruction().setSink(getSinkInstruction());
        getExitInstruction().setSink(getSinkInstruction());
        Iterator<Instruction> it = this.mutableInstructionList.iterator();
        int i = 0;
        while (it.hasNext()) {
            processInstruction(it.next(), i);
            i++;
        }
        collectAndCacheReachableInstructions();
    }

    private final void collectAndCacheReachableInstructions() {
        collectReachableInstructions();
        for (Instruction instruction : this.mutableInstructionList) {
            if (this.reachableInstructions.contains(instruction)) {
                getInstructions().add(instruction);
            }
        }
        markDeadInstructions();
    }

    private final void processInstruction(Instruction instruction, final int i) {
        instruction.accept(new InstructionVisitor() { // from class: org.jetbrains.kotlin.cfg.pseudocode.PseudocodeImpl.processInstruction.1
            @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
            public void visitSubroutineExit(SubroutineExitInstruction instruction2) {
                Intrinsics.checkNotNullParameter(instruction2, "instruction");
            }

            @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
            public void visitSubroutineSink(SubroutineSinkInstruction instruction2) {
                Intrinsics.checkNotNullParameter(instruction2, "instruction");
            }

            @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
            public void visitInstructionWithNext(InstructionWithNext instruction2) {
                Intrinsics.checkNotNullParameter(instruction2, "instruction");
                instruction2.setNext(PseudocodeImpl.this.getNextPosition(i));
            }

            @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
            public void visitJump(AbstractJumpInstruction instruction2) {
                Intrinsics.checkNotNullParameter(instruction2, "instruction");
                instruction2.setResolvedTarget(PseudocodeImpl.this.getJumpTarget(instruction2.getTargetLabel()));
            }

            @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
            public void visitNondeterministicJump(NondeterministicJumpInstruction instruction2) {
                Intrinsics.checkNotNullParameter(instruction2, "instruction");
                instruction2.setNext(PseudocodeImpl.this.getNextPosition(i));
                for (Label label : instruction2.getTargetLabels()) {
                    instruction2.setResolvedTarget(label, PseudocodeImpl.this.getJumpTarget(label));
                }
            }

            @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
            public void visitConditionalJump(ConditionalJumpInstruction instruction2) {
                Intrinsics.checkNotNullParameter(instruction2, "instruction");
                Instruction nextPosition = PseudocodeImpl.this.getNextPosition(i);
                Instruction jumpTarget = PseudocodeImpl.this.getJumpTarget(instruction2.getTargetLabel());
                if (instruction2.getOnTrue()) {
                    instruction2.setNextOnFalse(nextPosition);
                    instruction2.setNextOnTrue(jumpTarget);
                } else {
                    instruction2.setNextOnFalse(jumpTarget);
                    instruction2.setNextOnTrue(nextPosition);
                }
                visitJump(instruction2);
            }

            @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
            public void visitLocalFunctionDeclarationInstruction(LocalFunctionDeclarationInstruction instruction2) {
                Intrinsics.checkNotNullParameter(instruction2, "instruction");
                Pseudocode body = instruction2.getBody();
                Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.pseudocode.PseudocodeImpl");
                PseudocodeImpl pseudocodeImpl = (PseudocodeImpl) body;
                pseudocodeImpl.parent = PseudocodeImpl.this;
                pseudocodeImpl.postProcess();
                instruction2.setNext(PseudocodeImpl.this.getSinkInstruction());
            }

            @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
            public void visitInlinedLocalFunctionDeclarationInstruction(InlinedLocalFunctionDeclarationInstruction instruction2) {
                Intrinsics.checkNotNullParameter(instruction2, "instruction");
                Pseudocode body = instruction2.getBody();
                Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.pseudocode.PseudocodeImpl");
                PseudocodeImpl pseudocodeImpl = (PseudocodeImpl) body;
                pseudocodeImpl.parent = PseudocodeImpl.this;
                pseudocodeImpl.postProcess();
                instruction2.setNext(pseudocodeImpl.getInstructions().contains(pseudocodeImpl.getExitInstruction()) ? PseudocodeImpl.this.getNextPosition(i) : PseudocodeImpl.this.getSinkInstruction());
            }

            @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
            public void visitInstruction(Instruction instruction2) {
                Intrinsics.checkNotNullParameter(instruction2, "instruction");
                throw new UnsupportedOperationException(instruction2.toString());
            }
        });
    }

    private final void collectReachableInstructions() {
        HashSet<Instruction> hashSet = new HashSet();
        PseudocodeTraverserKt.traverseFollowingInstructions(getEnterInstruction(), hashSet, TraversalOrder.FORWARD, new Function1<Instruction, TraverseInstructionResult>() { // from class: org.jetbrains.kotlin.cfg.pseudocode.PseudocodeImpl.collectReachableInstructions.1
            @Override // kotlin.jvm.functions.Function1
            public final TraverseInstructionResult invoke(Instruction instruction) {
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                if ((instruction instanceof MagicInstruction) && ((MagicInstruction) instruction).getKind() == MagicKind.EXHAUSTIVE_WHEN_ELSE) {
                    return TraverseInstructionResult.SKIP;
                }
                return TraverseInstructionResult.CONTINUE;
            }
        });
        if (!getIsInlined()) {
            hashSet.add(getExitInstruction());
            hashSet.add(getErrorInstruction());
            hashSet.add(getSinkInstruction());
        }
        for (Instruction instruction : hashSet) {
            Pseudocode owner = instruction.getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.pseudocode.PseudocodeImpl");
            ((PseudocodeImpl) owner).reachableInstructions.add(instruction);
        }
    }

    private final void markDeadInstructions() {
        Collection<Instruction> previousInstructions;
        HashSet hashSet = CollectionsKt.toHashSet(getInstructions());
        for (Instruction instruction : this.mutableInstructionList) {
            if (!hashSet.contains(instruction)) {
                InstructionImpl instructionImpl = instruction instanceof InstructionImpl ? (InstructionImpl) instruction : null;
                if (instructionImpl != null) {
                    instructionImpl.setMarkedAsDead(true);
                }
                for (Instruction instruction2 : instruction.getNextInstructions()) {
                    InstructionImpl instructionImpl2 = instruction2 instanceof InstructionImpl ? (InstructionImpl) instruction2 : null;
                    if (instructionImpl2 != null && (previousInstructions = instructionImpl2.getPreviousInstructions()) != null) {
                        previousInstructions.remove(instruction);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Instruction getJumpTarget(Label targetLabel) {
        return targetLabel.resolveToInstruction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Instruction getNextPosition(int currentPosition) {
        this.mutableInstructionList.size();
        Instruction instruction = this.mutableInstructionList.get(currentPosition + 1);
        Intrinsics.checkNotNullExpressionValue(instruction, "get(...)");
        return instruction;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public PseudocodeImpl copy() {
        PseudocodeImpl pseudocodeImpl = new PseudocodeImpl(getCorrespondingElement(), getIsInlined());
        pseudocodeImpl.repeatWhole(this);
        return pseudocodeImpl;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.Pseudocode
    public KtElementInstruction instructionForElement(KtElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return this.representativeInstructions.get(element);
    }

    private final void repeatWhole(PseudocodeImpl originalPseudocode) {
        repeatInternal(originalPseudocode, null, null, 0);
        this.parent = originalPseudocode.getParent();
    }

    public final int repeatPart(Label startLabel, Label finishLabel, int labelCount) {
        Intrinsics.checkNotNullParameter(startLabel, "startLabel");
        Intrinsics.checkNotNullParameter(finishLabel, "finishLabel");
        Pseudocode pseudocode = startLabel.getPseudocode();
        Intrinsics.checkNotNull(pseudocode, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.pseudocode.PseudocodeImpl");
        return repeatInternal((PseudocodeImpl) pseudocode, startLabel, finishLabel, labelCount);
    }

    private final int repeatInternal(PseudocodeImpl originalPseudocode, Label startLabel, Label finishLabel, int labelCountArg) {
        int targetInstructionIndex = startLabel != null ? startLabel.getTargetInstructionIndex() : 0;
        int targetInstructionIndex2 = finishLabel != null ? finishLabel.getTargetInstructionIndex() : originalPseudocode.mutableInstructionList.size();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Multimap<Instruction, Label> multimapCreate = HashMultimap.create();
        for (PseudocodeLabel pseudocodeLabel : originalPseudocode.labels) {
            int targetInstructionIndex3 = pseudocodeLabel.getTargetInstructionIndex();
            if (targetInstructionIndex3 >= 0 && pseudocodeLabel != startLabel && pseudocodeLabel != finishLabel && targetInstructionIndex <= targetInstructionIndex3 && targetInstructionIndex3 <= targetInstructionIndex2) {
                linkedHashMap.put(pseudocodeLabel, pseudocodeLabel.copy(this, labelCountArg));
                Intrinsics.checkNotNull(pseudocodeLabel);
                multimapCreate.put(getJumpTarget(pseudocodeLabel), pseudocodeLabel);
                labelCountArg++;
            }
        }
        Iterator it = linkedHashMap.values().iterator();
        while (it.hasNext()) {
            this.labels.add((PseudocodeLabel) it.next());
        }
        while (targetInstructionIndex < targetInstructionIndex2) {
            Instruction instruction = originalPseudocode.mutableInstructionList.get(targetInstructionIndex);
            Intrinsics.checkNotNullExpressionValue(instruction, "get(...)");
            Instruction instruction2 = instruction;
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            Intrinsics.checkNotNull(multimapCreate);
            repeatLabelsBindingForInstruction(instruction2, linkedHashMap2, multimapCreate);
            Instruction instructionCopyInstruction = copyInstruction(instruction2, linkedHashMap2);
            addInstruction(instructionCopyInstruction);
            if (instruction2 == originalPseudocode.internalErrorInstruction && (instructionCopyInstruction instanceof SubroutineExitInstruction)) {
                this.internalErrorInstruction = (SubroutineExitInstruction) instructionCopyInstruction;
            }
            if (instruction2 == originalPseudocode.internalExitInstruction && (instructionCopyInstruction instanceof SubroutineExitInstruction)) {
                this.internalExitInstruction = (SubroutineExitInstruction) instructionCopyInstruction;
            }
            if (instruction2 == originalPseudocode.internalSinkInstruction && (instructionCopyInstruction instanceof SubroutineSinkInstruction)) {
                this.internalSinkInstruction = (SubroutineSinkInstruction) instructionCopyInstruction;
            }
            targetInstructionIndex++;
        }
        if (targetInstructionIndex2 < originalPseudocode.mutableInstructionList.size()) {
            Instruction instruction3 = originalPseudocode.mutableInstructionList.get(targetInstructionIndex2);
            Intrinsics.checkNotNullExpressionValue(instruction3, "get(...)");
            Intrinsics.checkNotNull(multimapCreate);
            repeatLabelsBindingForInstruction(instruction3, linkedHashMap, multimapCreate);
        }
        return labelCountArg;
    }

    private final void repeatLabelsBindingForInstruction(Instruction originalInstruction, Map<Label, PseudocodeLabel> originalToCopy, Multimap<Instruction, Label> originalLabelsForInstruction) {
        Iterator<Label> it = originalLabelsForInstruction.get(originalInstruction).iterator();
        while (it.hasNext()) {
            PseudocodeLabel pseudocodeLabel = originalToCopy.get(it.next());
            Intrinsics.checkNotNull(pseudocodeLabel);
            bindLabel(pseudocodeLabel);
        }
    }

    private final Instruction copyInstruction(Instruction instruction, Map<Label, PseudocodeLabel> originalToCopy) {
        if (instruction instanceof AbstractJumpInstruction) {
            AbstractJumpInstruction abstractJumpInstruction = (AbstractJumpInstruction) instruction;
            PseudocodeLabel pseudocodeLabel = originalToCopy.get(abstractJumpInstruction.getTargetLabel());
            if (pseudocodeLabel != null) {
                return abstractJumpInstruction.copy(pseudocodeLabel);
            }
        }
        if (instruction instanceof NondeterministicJumpInstruction) {
            NondeterministicJumpInstruction nondeterministicJumpInstruction = (NondeterministicJumpInstruction) instruction;
            return nondeterministicJumpInstruction.copy(copyLabels(nondeterministicJumpInstruction.getTargetLabels(), originalToCopy));
        }
        Intrinsics.checkNotNull(instruction, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl");
        return ((InstructionImpl) instruction).copy();
    }

    private final List<Label> copyLabels(Collection<? extends Label> labels, Map<Label, PseudocodeLabel> originalToCopy) {
        ArrayList arrayList = new ArrayList();
        for (PseudocodeLabel pseudocodeLabel : labels) {
            PseudocodeLabel pseudocodeLabel2 = originalToCopy.get(pseudocodeLabel);
            if (pseudocodeLabel2 != null) {
                pseudocodeLabel = pseudocodeLabel2;
            }
            arrayList.add(pseudocodeLabel);
        }
        return arrayList;
    }
}
