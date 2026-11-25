package org.jetbrains.kotlin.ir.expressions;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrMemberAccessExpression.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J/\u0010%\u001a\u00020&\"\u0004\b\u0001\u0010'2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u0002H'0)2\u0006\u0010*\u001a\u0002H'H\u0016¢\u0006\u0002\u0010+J\u0010\u0010,\u001a\u0004\u0018\u00010\u00192\u0006\u0010-\u001a\u00020\u001dJ\u0010\u0010.\u001a\u0004\u0018\u00010\u00062\u0006\u0010-\u001a\u00020\u001dJ\u0018\u0010/\u001a\u00020&2\u0006\u0010-\u001a\u00020\u001d2\b\u00100\u001a\u0004\u0018\u00010\u0019J\u0018\u00101\u001a\u00020&2\u0006\u0010-\u001a\u00020\u001d2\b\u00102\u001a\u0004\u0018\u00010\u0006J)\u00103\u001a\u00020&\"\u0004\b\u0001\u0010'2\f\u00104\u001a\b\u0012\u0004\u0012\u0002H'052\u0006\u0010*\u001a\u0002H'H\u0016¢\u0006\u0002\u00106R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0018X¤\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u001d8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0018X¤\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020\u001d8F¢\u0006\u0006\u001a\u0004\b$\u0010\u001f¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "()V", "dispatchReceiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setDispatchReceiver", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "extensionReceiver", "getExtensionReceiver", "setExtensionReceiver", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getTypeArguments", "()[Lorg/jetbrains/kotlin/ir/types/IrType;", "typeArgumentsCount", "", "getTypeArgumentsCount", "()I", "valueArguments", "getValueArguments", "()[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "valueArgumentsCount", "getValueArgumentsCount", "acceptChildren", "", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "getTypeArgument", "index", "getValueArgument", "putTypeArgument", ModuleXmlParser.TYPE, "putValueArgument", "valueArgument", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrMemberAccessExpression<S extends IrSymbol> extends IrDeclarationReference {
    private IrExpression dispatchReceiver;
    private IrExpression extensionReceiver;

    public abstract IrStatementOrigin getOrigin();

    @Override // org.jetbrains.kotlin.ir.expressions.IrDeclarationReference
    public abstract S getSymbol();

    protected abstract IrType[] getTypeArguments();

    protected abstract IrExpression[] getValueArguments();

    public abstract void setOrigin(IrStatementOrigin irStatementOrigin);

    public final IrExpression getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public final void setDispatchReceiver(IrExpression irExpression) {
        this.dispatchReceiver = irExpression;
    }

    public final IrExpression getExtensionReceiver() {
        return this.extensionReceiver;
    }

    public final void setExtensionReceiver(IrExpression irExpression) {
        this.extensionReceiver = irExpression;
    }

    public final int getValueArgumentsCount() {
        return getValueArguments().length;
    }

    public final int getTypeArgumentsCount() {
        return getTypeArguments().length;
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        IrExpression irExpression = this.dispatchReceiver;
        if (irExpression != null) {
            irExpression.accept(visitor, data);
        }
        IrExpression irExpression2 = this.extensionReceiver;
        if (irExpression2 != null) {
            irExpression2.accept(visitor, data);
        }
        for (IrExpression irExpression3 : getValueArguments()) {
            if (irExpression3 != null) {
                irExpression3.accept(visitor, data);
            }
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrExpression irExpression = this.dispatchReceiver;
        this.dispatchReceiver = irExpression != null ? irExpression.transform((IrElementTransformer) transformer, (Object) data) : null;
        IrExpression irExpression2 = this.extensionReceiver;
        this.extensionReceiver = irExpression2 != null ? irExpression2.transform((IrElementTransformer) transformer, (Object) data) : null;
        TransformKt.transformInPlace(getValueArguments(), transformer, data);
    }

    public final IrExpression getValueArgument(int index) {
        IrExpressionsKt.checkArgumentSlotAccess(this, "value", index, getValueArguments().length);
        return getValueArguments()[index];
    }

    public final IrType getTypeArgument(int index) {
        IrExpressionsKt.checkArgumentSlotAccess(this, ModuleXmlParser.TYPE, index, getTypeArguments().length);
        return getTypeArguments()[index];
    }

    public final void putValueArgument(int index, IrExpression valueArgument) {
        IrExpressionsKt.checkArgumentSlotAccess(this, "value", index, getValueArguments().length);
        getValueArguments()[index] = valueArgument;
    }

    public final void putTypeArgument(int index, IrType type) {
        IrExpressionsKt.checkArgumentSlotAccess(this, ModuleXmlParser.TYPE, index, getTypeArguments().length);
        getTypeArguments()[index] = type;
    }
}
