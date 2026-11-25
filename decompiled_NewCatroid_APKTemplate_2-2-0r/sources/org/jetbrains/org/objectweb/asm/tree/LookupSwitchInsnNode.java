package org.jetbrains.org.objectweb.asm.tree;

import java.util.List;
import java.util.Map;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes6.dex */
public class LookupSwitchInsnNode extends AbstractInsnNode {
    public LabelNode dflt;
    public List<Integer> keys;
    public List<LabelNode> labels;

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public int getType() {
        return 12;
    }

    public LookupSwitchInsnNode(LabelNode labelNode, int[] iArr, LabelNode[] labelNodeArr) {
        super(171);
        this.dflt = labelNode;
        this.keys = Util.asArrayList(iArr);
        this.labels = Util.asArrayList(labelNodeArr);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public void accept(MethodVisitor methodVisitor) {
        int size = this.keys.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = this.keys.get(i).intValue();
        }
        int size2 = this.labels.size();
        Label[] labelArr = new Label[size2];
        for (int i2 = 0; i2 < size2; i2++) {
            labelArr[i2] = this.labels.get(i2).getLabel();
        }
        methodVisitor.visitLookupSwitchInsn(this.dflt.getLabel(), iArr, labelArr);
        acceptAnnotations(methodVisitor);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> map) {
        LookupSwitchInsnNode lookupSwitchInsnNode = new LookupSwitchInsnNode(clone(this.dflt, map), null, clone(this.labels, map));
        lookupSwitchInsnNode.keys.addAll(this.keys);
        return lookupSwitchInsnNode.cloneAnnotations(this);
    }
}
