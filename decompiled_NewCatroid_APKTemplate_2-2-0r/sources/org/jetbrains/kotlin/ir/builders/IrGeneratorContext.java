package org.jetbrains.kotlin.ir.builders;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrFactory;

/* JADX INFO: compiled from: IrGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContextInterface;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrGeneratorContext extends IrGeneratorContextInterface {
    IrFactory getIrFactory();

    /* JADX INFO: compiled from: IrGenerator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static IrFactory getIrFactory(IrGeneratorContext irGeneratorContext) {
            return irGeneratorContext.mo6786getIrBuiltIns().getIrFactory();
        }
    }
}
