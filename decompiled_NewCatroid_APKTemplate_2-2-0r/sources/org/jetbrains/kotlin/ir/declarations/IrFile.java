package org.jetbrains.kotlin.ir.declarations;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrFile.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J5\u0010\u0015\u001a\u0002H\u0016\"\u0004\b\u0000\u0010\u0016\"\u0004\b\u0001\u0010\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u0002H\u00170\u00192\u0006\u0010\u001a\u001a\u0002H\u0017H\u0016¢\u0006\u0002\u0010\u001bJ/\u0010\u001c\u001a\u00020\u001d\"\u0004\b\u0000\u0010\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u0002H\u00170\u00192\u0006\u0010\u001a\u001a\u0002H\u0017H\u0016¢\u0006\u0002\u0010\u001eJ)\u0010\u001f\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00172\f\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00170!2\u0006\u0010\u001a\u001a\u0002H\u0017H\u0016¢\u0006\u0002\u0010\"J)\u0010#\u001a\u00020\u001d\"\u0004\b\u0000\u0010\u00172\f\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00170!2\u0006\u0010\u001a\u001a\u0002H\u0017H\u0016¢\u0006\u0002\u0010$R\u0018\u0010\u0005\u001a\u00020\u0006X¦\u000e¢\u0006\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u00020\fX¦\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "Lorg/jetbrains/kotlin/ir/declarations/IrMetadataSourceOwner;", "Lorg/jetbrains/kotlin/ir/declarations/IrMutableAnnotationContainer;", "()V", "fileEntry", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "getFileEntry", "()Lorg/jetbrains/kotlin/ir/IrFileEntry;", "setFileEntry", "(Lorg/jetbrains/kotlin/ir/IrFileEntry;)V", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getModule", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "setModule", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transform", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "transformChildren", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrFile extends IrPackageFragment implements IrMetadataSourceOwner, IrMutableAnnotationContainer {
    public abstract IrFileEntry getFileEntry();

    public abstract IrModuleFragment getModule();

    @Override // org.jetbrains.kotlin.ir.declarations.IrPackageFragment, org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public abstract IrFileSymbol getSymbol();

    public abstract void setFileEntry(IrFileEntry irFileEntry);

    public abstract void setModule(IrModuleFragment irModuleFragment);

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public /* bridge */ /* synthetic */ IrElement transform(IrElementTransformer irElementTransformer, Object obj) {
        return transform((IrElementTransformer<? super Object>) irElementTransformer, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitFile(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> IrFile transform(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Object objAccept = accept(transformer, data);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFile");
        return (IrFile) objAccept;
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getDeclarations().iterator();
        while (it.hasNext()) {
            ((IrDeclaration) it.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        TransformKt.transformInPlace(getDeclarations(), transformer, data);
    }
}
