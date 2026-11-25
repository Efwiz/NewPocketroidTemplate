package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: RedundantCheckcastsBeforeAastoreMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/RedundantCheckcastsBeforeAastoreMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantCheckcastsBeforeAastoreMethodTransformer extends MethodTransformer {
    public static final RedundantCheckcastsBeforeAastoreMethodTransformer INSTANCE = new RedundantCheckcastsBeforeAastoreMethodTransformer();

    private RedundantCheckcastsBeforeAastoreMethodTransformer() {
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.Object, java.util.ListIterator] */
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        AbstractInsnNode next;
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        ?? it = methodNode.instructions.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
            if (abstractInsnNode.getOpcode() == 192 && (next = abstractInsnNode.getNext()) != null && next.getOpcode() == 83) {
                ReifiedTypeInliner.Companion companion = ReifiedTypeInliner.INSTANCE;
                AbstractInsnNode previous = abstractInsnNode.getPrevious();
                Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
                boolean zIsOperationReifiedMarker = companion.isOperationReifiedMarker(previous);
                it.remove();
                if (zIsOperationReifiedMarker) {
                    for (int i = 1; i < 4; i++) {
                        it.previous();
                        it.remove();
                    }
                }
            }
        }
    }
}
