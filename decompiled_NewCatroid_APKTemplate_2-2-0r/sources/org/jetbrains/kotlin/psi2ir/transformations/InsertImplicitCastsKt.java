package org.jetbrains.kotlin.psi2ir.transformations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;

/* JADX INFO: compiled from: InsertImplicitCasts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¨\u0006\u0006"}, d2 = {"insertImplicitCasts", "", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InsertImplicitCastsKt {
    public static final void insertImplicitCasts(IrFile file, GeneratorContext context) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(context, "context");
        new InsertImplicitCasts(context.getIrBuiltIns(), context.getTypeTranslator(), context.getCallToSubstitutedDescriptorMap$ir_psi2ir(), context.getExtensions(), context.getSymbolTable(), file).run(file);
    }
}
