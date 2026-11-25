package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;

/* JADX INFO: compiled from: LabelNormalizationMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0014\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001\u001a\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001¨\u0006\u0006"}, d2 = {"removeNodeGetNext", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "oldNode", "replaceNodeGetNext", "newNode", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LabelNormalizationMethodTransformerKt {
    public static final AbstractInsnNode replaceNodeGetNext(InsnList insnList, AbstractInsnNode oldNode, AbstractInsnNode newNode) {
        Intrinsics.checkNotNullParameter(insnList, "<this>");
        Intrinsics.checkNotNullParameter(oldNode, "oldNode");
        Intrinsics.checkNotNullParameter(newNode, "newNode");
        insnList.insertBefore(oldNode, newNode);
        insnList.remove(oldNode);
        return newNode.getNext();
    }

    public static final AbstractInsnNode removeNodeGetNext(InsnList insnList, AbstractInsnNode oldNode) {
        Intrinsics.checkNotNullParameter(insnList, "<this>");
        Intrinsics.checkNotNullParameter(oldNode, "oldNode");
        AbstractInsnNode next = oldNode.getNext();
        insnList.remove(oldNode);
        return next;
    }
}
