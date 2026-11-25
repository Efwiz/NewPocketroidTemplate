package org.jetbrains.kotlin.codegen.optimization.common;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.Sequence;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: Util.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010(\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\bJ\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\rH\u0096\u0002R\u0011\u0010\u0006\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/InsnSequence;", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "insnList", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "(Lorg/jetbrains/org/objectweb/asm/tree/InsnList;)V", TypedValues.TransitionType.S_FROM, "to", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)V", "getFrom", "()Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getTo", "iterator", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InsnSequence implements Sequence<AbstractInsnNode> {
    private final AbstractInsnNode from;
    private final AbstractInsnNode to;

    public InsnSequence(AbstractInsnNode from, AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(from, "from");
        this.from = from;
        this.to = abstractInsnNode;
    }

    public final AbstractInsnNode getFrom() {
        return this.from;
    }

    public final AbstractInsnNode getTo() {
        return this.to;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public InsnSequence(InsnList insnList) {
        Intrinsics.checkNotNullParameter(insnList, "insnList");
        AbstractInsnNode first = insnList.getFirst();
        Intrinsics.checkNotNullExpressionValue(first, "getFirst(...)");
        this(first, null);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.optimization.common.InsnSequence$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: Util.kt */
    @Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\b\u001a\u00020\tH\u0096\u0002J\t\u0010\n\u001a\u00020\u0002H\u0096\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0002X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"org/jetbrains/kotlin/codegen/optimization/common/InsnSequence$iterator$1", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "current", "getCurrent", "()Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "setCurrent", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)V", "hasNext", "", ES6Iterator.NEXT_METHOD, Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 implements Iterator<AbstractInsnNode>, KMappedMarker {
        private AbstractInsnNode current;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        AnonymousClass1() {
            this.current = InsnSequence.this.getFrom();
        }

        public final AbstractInsnNode getCurrent() {
            return this.current;
        }

        public final void setCurrent(AbstractInsnNode abstractInsnNode) {
            this.current = abstractInsnNode;
        }

        @Override // java.util.Iterator
        public AbstractInsnNode next() {
            AbstractInsnNode abstractInsnNode = this.current;
            Intrinsics.checkNotNull(abstractInsnNode);
            this.current = abstractInsnNode.getNext();
            Intrinsics.checkNotNull(abstractInsnNode);
            return abstractInsnNode;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !Intrinsics.areEqual(this.current, InsnSequence.this.getTo());
        }
    }

    @Override // kotlin.sequences.Sequence
    public Iterator<AbstractInsnNode> iterator() {
        return new AnonymousClass1();
    }
}
