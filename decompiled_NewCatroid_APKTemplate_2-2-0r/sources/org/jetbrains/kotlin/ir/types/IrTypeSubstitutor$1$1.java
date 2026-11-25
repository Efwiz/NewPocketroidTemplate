package org.jetbrains.kotlin.ir.types;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: IrTypeSubstitutor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "it", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class IrTypeSubstitutor$1$1 extends Lambda implements Function1<IrTypeParameterSymbol, CharSequence> {
    public static final IrTypeSubstitutor$1$1 INSTANCE = new IrTypeSubstitutor$1$1();

    IrTypeSubstitutor$1$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final CharSequence invoke(IrTypeParameterSymbol it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return RenderIrElementKt.render$default(it.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null);
    }
}
