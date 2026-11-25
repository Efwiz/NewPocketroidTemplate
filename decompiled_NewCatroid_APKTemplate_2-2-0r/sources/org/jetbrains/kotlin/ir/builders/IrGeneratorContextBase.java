package org.jetbrains.kotlin.ir.builders;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrFactory;

/* JADX INFO: compiled from: IrGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContextBase;", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrGeneratorContextBase implements IrGeneratorContext {
    private final IrBuiltIns irBuiltIns;

    public IrGeneratorContextBase(IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        this.irBuiltIns = irBuiltIns;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContextInterface
    public IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContext
    public IrFactory getIrFactory() {
        return IrGeneratorContext.DefaultImpls.getIrFactory(this);
    }
}
