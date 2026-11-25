package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: InplaceArgumentsMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002¨\u0006\u0003"}, d2 = {"fixupLVT", "", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InplaceArgumentsMethodTransformerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void fixupLVT(MethodNode methodNode) {
        for (LocalVariableNode localVariableNode : methodNode.localVariables) {
            if (methodNode.instructions.indexOf(localVariableNode.end) < methodNode.instructions.indexOf(localVariableNode.start)) {
                LabelNode start = localVariableNode.start;
                Intrinsics.checkNotNullExpressionValue(start, "start");
                AbstractInsnNode next = start.getNext();
                while (next != null && !(next instanceof LabelNode)) {
                    next = next.getNext();
                }
                LabelNode labelNode = next instanceof LabelNode ? (LabelNode) next : null;
                if (labelNode == null) {
                    labelNode = localVariableNode.start;
                }
                localVariableNode.end = labelNode;
            }
        }
    }
}
