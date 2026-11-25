package org.jetbrains.kotlin.cfg.variable;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cfg.ControlFlowInfo;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.VariableDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.Edges;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.PseudocodeTraverserKt;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.TraversalOrder;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;

/* JADX INFO: compiled from: PseudocodeVariableDataCollector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006Jw\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000f0\u000e0\f\"\"\b\u0000\u0010\u000f*\u001c\u0012\u0002\b\u0003\u0012\u0004\u0012\u00020\u0011\u0012\u0002\b\u00030\u0010j\n\u0012\u0002\b\u0003\u0012\u0002\b\u0003`\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u0002H\u000f2$\u0010\u0016\u001a \u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000f0\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000f0\u000e0\u0017¢\u0006\u0002\u0010\u0019J\u0010\u0010\u001a\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002JI\u0010\u001b\u001a\u0002H\u000f\"\"\b\u0000\u0010\u000f*\u001c\u0012\u0002\b\u0003\u0012\u0004\u0012\u00020\u0011\u0012\u0002\b\u00030\u0010j\n\u0012\u0002\b\u0003\u0012\u0002\b\u0003`\u00122\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001e\u001a\u0002H\u000fH\u0002¢\u0006\u0002\u0010\u001fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariableDataCollector;", "", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "pseudocode", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;)V", "blockScopeVariableInfo", "Lorg/jetbrains/kotlin/cfg/variable/BlockScopeVariableInfo;", "getBlockScopeVariableInfo", "()Lorg/jetbrains/kotlin/cfg/variable/BlockScopeVariableInfo;", "collectData", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "Lorg/jetbrains/kotlin/cfg/pseudocodeTraverser/Edges;", "I", "Lorg/jetbrains/kotlin/cfg/ControlFlowInfo;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/cfg/variable/VariableUsageControlFlowInfo;", "traversalOrder", "Lorg/jetbrains/kotlin/cfg/pseudocodeTraverser/TraversalOrder;", "initialInfo", "instructionDataMergeStrategy", "Lkotlin/Function2;", "", "(Lorg/jetbrains/kotlin/cfg/pseudocodeTraverser/TraversalOrder;Lorg/jetbrains/kotlin/cfg/ControlFlowInfo;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "computeBlockScopeVariableInfo", "filterOutVariablesOutOfScope", TypedValues.TransitionType.S_FROM, "to", CompilerOptions.INFO, "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;Lorg/jetbrains/kotlin/cfg/ControlFlowInfo;)Lorg/jetbrains/kotlin/cfg/ControlFlowInfo;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PseudocodeVariableDataCollector {
    private final BindingContext bindingContext;
    private final BlockScopeVariableInfo blockScopeVariableInfo;
    private final Pseudocode pseudocode;

    public PseudocodeVariableDataCollector(BindingContext bindingContext, Pseudocode pseudocode) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(pseudocode, "pseudocode");
        this.bindingContext = bindingContext;
        this.pseudocode = pseudocode;
        this.blockScopeVariableInfo = computeBlockScopeVariableInfo(pseudocode);
    }

    public final BlockScopeVariableInfo getBlockScopeVariableInfo() {
        return this.blockScopeVariableInfo;
    }

    public final <I extends ControlFlowInfo<?, VariableDescriptor, ?>> Map<Instruction, Edges<I>> collectData(TraversalOrder traversalOrder, I initialInfo, Function2<? super Instruction, ? super Collection<? extends I>, ? extends Edges<? extends I>> instructionDataMergeStrategy) {
        Intrinsics.checkNotNullParameter(traversalOrder, "traversalOrder");
        Intrinsics.checkNotNullParameter(initialInfo, "initialInfo");
        Intrinsics.checkNotNullParameter(instructionDataMergeStrategy, "instructionDataMergeStrategy");
        return PseudocodeTraverserKt.collectData(this.pseudocode, traversalOrder, instructionDataMergeStrategy, new Function3<Instruction, Instruction, I, I>() { // from class: org.jetbrains.kotlin.cfg.variable.PseudocodeVariableDataCollector.collectData.1
            {
                super(3);
            }

            /* JADX WARN: Incorrect return type in method signature: (Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;TI;)TI; */
            @Override // kotlin.jvm.functions.Function3
            public final ControlFlowInfo invoke(Instruction from, Instruction to, ControlFlowInfo info) {
                Intrinsics.checkNotNullParameter(from, "from");
                Intrinsics.checkNotNullParameter(to, "to");
                Intrinsics.checkNotNullParameter(info, "info");
                return PseudocodeVariableDataCollector.this.filterOutVariablesOutOfScope(from, to, info);
            }
        }, initialInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <I extends ControlFlowInfo<?, VariableDescriptor, ?>> I filterOutVariablesOutOfScope(Instruction from, Instruction to, I info) {
        final int depth = to.getBlockScope().getDepth();
        if (depth >= from.getBlockScope().getDepth()) {
            return info;
        }
        I i = (I) info.retainAll(new Function1<VariableDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.cfg.variable.PseudocodeVariableDataCollector.filterOutVariablesOutOfScope.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(VariableDescriptor variable) {
                Intrinsics.checkNotNullParameter(variable, "variable");
                BlockScope blockScope = PseudocodeVariableDataCollector.this.getBlockScopeVariableInfo().getDeclaredIn().get(variable);
                return Boolean.valueOf((blockScope != null ? blockScope.getDepth() : -1) <= depth);
            }
        });
        Intrinsics.checkNotNull(i, "null cannot be cast to non-null type I of org.jetbrains.kotlin.cfg.variable.PseudocodeVariableDataCollector.filterOutVariablesOutOfScope");
        return i;
    }

    private final BlockScopeVariableInfo computeBlockScopeVariableInfo(Pseudocode pseudocode) {
        final BlockScopeVariableInfoImpl blockScopeVariableInfoImpl = new BlockScopeVariableInfoImpl();
        PseudocodeTraverserKt.traverse(pseudocode, TraversalOrder.FORWARD, new Function1<Instruction, Unit>() { // from class: org.jetbrains.kotlin.cfg.variable.PseudocodeVariableDataCollector.computeBlockScopeVariableInfo.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction) {
                invoke2(instruction);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Instruction instruction) {
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                if (instruction instanceof VariableDeclarationInstruction) {
                    VariableDeclarationInstruction variableDeclarationInstruction = (VariableDeclarationInstruction) instruction;
                    DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) PseudocodeVariableDataCollector.this.bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, variableDeclarationInstruction.getVariableDeclarationElement());
                    if (declarationDescriptor == null) {
                        return;
                    }
                    VariableDescriptor variableDescriptorVariableDescriptorForDeclaration = BindingContextUtils.variableDescriptorForDeclaration(declarationDescriptor);
                    if (variableDescriptorVariableDescriptorForDeclaration == null) {
                        throw new AssertionError("Variable or class descriptor should correspond to the instruction for " + variableDeclarationInstruction.getElement().getText() + ".\nDescriptor: " + declarationDescriptor);
                    }
                    blockScopeVariableInfoImpl.registerVariableDeclaredInScope(variableDescriptorVariableDescriptorForDeclaration, instruction.getBlockScope());
                }
            }
        });
        return blockScopeVariableInfoImpl;
    }
}
