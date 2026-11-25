package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;

/* JADX INFO: compiled from: RedundantNopsCleanupMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001c\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0001H\u0000¨\u0006\u0004"}, d2 = {"getRequiredNopInRange", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "firstInclusive", "lastExclusive", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RedundantNopsCleanupMethodTransformerKt {
    public static final AbstractInsnNode getRequiredNopInRange(AbstractInsnNode firstInclusive, AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(firstInclusive, "firstInclusive");
        AbstractInsnNode abstractInsnNode2 = null;
        while (firstInclusive != null && !Intrinsics.areEqual(firstInclusive, abstractInsnNode)) {
            if (UtilKt.isMeaningful(firstInclusive) && firstInclusive.getOpcode() != 0) {
                return null;
            }
            if (firstInclusive.getOpcode() == 0 && abstractInsnNode2 == null) {
                abstractInsnNode2 = firstInclusive;
            }
            firstInclusive = firstInclusive.getNext();
        }
        return abstractInsnNode2;
    }
}
