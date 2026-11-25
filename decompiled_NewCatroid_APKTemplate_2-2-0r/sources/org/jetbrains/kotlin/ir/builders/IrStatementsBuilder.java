package org.jetbrains.kotlin.ir.builders;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;

/* JADX INFO: compiled from: IrBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u00032\u00020\u0004B%\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0002\u0010\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H$J\r\u0010\u0011\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0012J\u0013\u0010\u0013\u001a\u00020\u000e*\b\u0012\u0004\u0012\u00020\u00100\u0014H\u0086\u0002J\r\u0010\u0013\u001a\u00020\u000e*\u00020\u0010H\u0086\u0002¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/IrStatementsBuilder;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorWithScope;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "startOffset", "", "endOffset", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;Lorg/jetbrains/kotlin/ir/builders/Scope;II)V", "addStatement", "", "irStatement", "Lorg/jetbrains/kotlin/ir/IrStatement;", "doBuild", "()Lorg/jetbrains/kotlin/ir/IrElement;", "unaryPlus", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrStatementsBuilder<T extends IrElement> extends IrBuilderWithScope implements IrGeneratorWithScope {
    protected abstract void addStatement(IrStatement irStatement);

    public abstract T doBuild();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrStatementsBuilder(IrGeneratorContext context, Scope scope, int i, int i2) {
        super(context, scope, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scope, "scope");
    }

    public final void unaryPlus(IrStatement irStatement) {
        Intrinsics.checkNotNullParameter(irStatement, "<this>");
        addStatement(irStatement);
    }

    public final void unaryPlus(List<? extends IrStatement> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            addStatement((IrStatement) it.next());
        }
    }
}
