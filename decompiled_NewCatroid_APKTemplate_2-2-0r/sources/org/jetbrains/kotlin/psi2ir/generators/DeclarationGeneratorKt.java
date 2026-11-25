package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;

/* JADX INFO: compiled from: DeclarationGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0000¨\u0006\u0007"}, d2 = {"createBodyGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/BodyGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/Generator;", "scopeOwnerSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "parentLoopResolver", "Lorg/jetbrains/kotlin/psi2ir/generators/LoopResolver;", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationGeneratorKt {
    public static /* synthetic */ BodyGenerator createBodyGenerator$default(Generator generator, IrSymbol irSymbol, LoopResolver loopResolver, int i, Object obj) {
        if ((i & 2) != 0) {
            loopResolver = null;
        }
        return createBodyGenerator(generator, irSymbol, loopResolver);
    }

    public static final BodyGenerator createBodyGenerator(Generator generator, IrSymbol scopeOwnerSymbol, LoopResolver loopResolver) {
        Intrinsics.checkNotNullParameter(generator, "<this>");
        Intrinsics.checkNotNullParameter(scopeOwnerSymbol, "scopeOwnerSymbol");
        return new BodyGenerator(scopeOwnerSymbol, generator.getContext(), loopResolver);
    }
}
