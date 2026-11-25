package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: DeferredMethodVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/DeferredMethodVisitor;", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "intermediate", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "resultNode", "Lkotlin/Function0;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Lkotlin/jvm/functions/Function0;)V", "getIntermediate", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "visitEnd", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeferredMethodVisitor extends MethodVisitor {
    private final MethodNode intermediate;
    private final Function0<MethodVisitor> resultNode;

    public final MethodNode getIntermediate() {
        return this.intermediate;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public DeferredMethodVisitor(MethodNode intermediate, Function0<? extends MethodVisitor> resultNode) {
        super(589824, intermediate);
        Intrinsics.checkNotNullParameter(intermediate, "intermediate");
        Intrinsics.checkNotNullParameter(resultNode, "resultNode");
        this.intermediate = intermediate;
        this.resultNode = resultNode;
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitEnd() {
        super.visitEnd();
        this.intermediate.accept(this.resultNode.invoke());
    }
}
