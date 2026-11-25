package org.jetbrains.kotlin.ir.expressions;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrConst.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0001\u0010\u0010\"\u0004\b\u0002\u0010\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00110\u00132\u0006\u0010\u0014\u001a\u0002H\u0011H\u0016¢\u0006\u0002\u0010\u0015R\u001e\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\u00028\u0000X¦\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConst;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()V", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "getKind", "()Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "setKind", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;)V", "value", "getValue", "()Ljava/lang/Object;", "setValue", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrConst<T> extends IrExpression {
    public abstract IrConstKind<T> getKind();

    public abstract T getValue();

    public abstract void setKind(IrConstKind<T> irConstKind);

    public abstract void setValue(T t);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitConst(this, data);
    }
}
