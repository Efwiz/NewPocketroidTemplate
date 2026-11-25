package org.jetbrains.kotlin.ir.declarations;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrElementBase;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrModuleFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010!J/\u0010\"\u001a\u00020#\"\u0004\b\u0000\u0010\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010$J)\u0010%\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001d2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u001d0'2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010(J)\u0010)\u001a\u00020#\"\u0004\b\u0000\u0010\u001d2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u001d0'2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010*R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u000b¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/ir/IrElementBase;", "Lorg/jetbrains/kotlin/ir/IrElement;", "()V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "endOffset", "", "getEndOffset", "()I", "files", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getFiles", "()Ljava/util/List;", "irBuiltins", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrBuiltins", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "startOffset", "getStartOffset", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transform", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "transformChildren", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrModuleFragment extends IrElementBase implements IrElement {
    public abstract ModuleDescriptor getDescriptor();

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return -1;
    }

    public abstract List<IrFile> getFiles();

    public abstract IrBuiltIns getIrBuiltins();

    public abstract Name getName();

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return -1;
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public /* bridge */ /* synthetic */ IrElement transform(IrElementTransformer irElementTransformer, Object obj) {
        return transform((IrElementTransformer<? super Object>) irElementTransformer, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitModuleFragment(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> IrModuleFragment transform(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Object objAccept = accept(transformer, data);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrModuleFragment");
        return (IrModuleFragment) objAccept;
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getFiles().iterator();
        while (it.hasNext()) {
            ((IrFile) it.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        TransformKt.transformInPlace(getFiles(), transformer, data);
    }
}
