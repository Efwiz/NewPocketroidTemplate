package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;

/* JADX INFO: compiled from: CoveringTryCatchNodeProcessor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/DefaultProcessor;", "Lorg/jetbrains/kotlin/codegen/inline/CoveringTryCatchNodeProcessor;", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "parameterSize", "", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;I)V", "getNode", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "instructionIndex", "inst", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultProcessor extends CoveringTryCatchNodeProcessor {
    private final MethodNode node;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultProcessor(MethodNode node, int i) {
        super(i);
        Intrinsics.checkNotNullParameter(node, "node");
        this.node = node;
        List<TryCatchBlockNode> tryCatchBlocks = node.tryCatchBlocks;
        Intrinsics.checkNotNullExpressionValue(tryCatchBlocks, "tryCatchBlocks");
        for (TryCatchBlockNode tryCatchBlockNode : tryCatchBlocks) {
            IntervalMetaInfo<TryCatchBlockNodeInfo> tryBlocksMetaInfo = getTryBlocksMetaInfo();
            Intrinsics.checkNotNull(tryCatchBlockNode);
            tryBlocksMetaInfo.addNewInterval(new TryCatchBlockNodeInfo(tryCatchBlockNode, false));
        }
        List<LocalVariableNode> localVariables = this.node.localVariables;
        Intrinsics.checkNotNullExpressionValue(localVariables, "localVariables");
        for (LocalVariableNode localVariableNode : localVariables) {
            IntervalMetaInfo<LocalVarNodeWrapper> localVarsMetaInfo = getLocalVarsMetaInfo();
            Intrinsics.checkNotNull(localVariableNode);
            localVarsMetaInfo.addNewInterval(new LocalVarNodeWrapper(localVariableNode));
        }
    }

    public final MethodNode getNode() {
        return this.node;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.CoveringTryCatchNodeProcessor
    public int instructionIndex(AbstractInsnNode inst) {
        Intrinsics.checkNotNullParameter(inst, "inst");
        return this.node.instructions.indexOf(inst);
    }
}
