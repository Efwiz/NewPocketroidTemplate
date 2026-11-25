package org.jetbrains.kotlin.ir.types;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.types.model.AnnotationMarker;

/* JADX INFO: compiled from: IrTypeSystemContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000/\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00020\u00012\u00020\u0002J6\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0096\u0001¢\u0006\u0002\u0010\u000fJ0\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0096\u0001¢\u0006\u0002\u0010\u0012J*\u0010\u0013\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u000b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u00152\u0006\u0010\u000e\u001a\u0002H\u000bH\u0096\u0001¢\u0006\u0002\u0010\u0016J*\u0010\u0017\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u000b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u00152\u0006\u0010\u000e\u001a\u0002H\u000bH\u0096\u0001¢\u0006\u0002\u0010\u0018R\u0012\u0010\u0003\u001a\u00020\u0004X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0004X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006¨\u0006\u0019"}, d2 = {"org/jetbrains/kotlin/ir/types/IrTypeSystemContext$getAttributes$1$1", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "Lorg/jetbrains/kotlin/ir/IrElement;", "endOffset", "", "getEndOffset", "()I", "startOffset", "getStartOffset", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transform", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "transformChildren", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTypeSystemContext$getAttributes$1$1 implements IrElement, AnnotationMarker {
    private final /* synthetic */ IrConstructorCall $$delegate_0;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return (R) this.$$delegate_0.accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        this.$$delegate_0.acceptChildren(visitor, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.$$delegate_0.getEndOffset();
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.$$delegate_0.getStartOffset();
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public <D> IrElement transform(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this.$$delegate_0.transform((IrElementTransformer) transformer, (Object) data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        this.$$delegate_0.transformChildren(transformer, data);
    }

    IrTypeSystemContext$getAttributes$1$1(IrConstructorCall irConstructorCall) {
        this.$$delegate_0 = irConstructorCall;
    }
}
