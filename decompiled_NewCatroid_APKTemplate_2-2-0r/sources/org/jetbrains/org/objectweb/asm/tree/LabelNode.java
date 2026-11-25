package org.jetbrains.org.objectweb.asm.tree;

import java.util.Map;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class LabelNode extends AbstractInsnNode {
    private Label value;

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public int getType() {
        return 8;
    }

    public LabelNode() {
        super(-1);
    }

    public LabelNode(Label label) {
        super(-1);
        this.value = label;
    }

    public Label getLabel() {
        if (this.value == null) {
            this.value = new Label();
        }
        return this.value;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitLabel(getLabel());
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> map) {
        return map.get(this);
    }

    public void resetLabel() {
        this.value = null;
    }
}
