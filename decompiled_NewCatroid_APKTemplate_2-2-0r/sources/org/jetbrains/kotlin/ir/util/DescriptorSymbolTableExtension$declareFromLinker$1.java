package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.name.SpecialNames;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: Add missing generic type declarations: [Symbol] */
/* JADX INFO: compiled from: DescriptorSymbolTableExtension.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0012\b\u0001\u0010\u0001*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0002\u0010\u0005*\u00020\u0006H\n¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {SpecialNames.ANONYMOUS_STRING, NativeSymbol.CLASS_NAME, "D", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "SymbolOwner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "invoke", "()Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class DescriptorSymbolTableExtension$declareFromLinker$1<Symbol> extends Lambda implements Function0<Symbol> {

    /* JADX INFO: Incorrect field signature: TD; */
    final /* synthetic */ DeclarationDescriptor $descriptor;
    final /* synthetic */ IdSignature $signature;
    final /* synthetic */ Function2<D, IdSignature, Symbol> $symbolFactory;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Incorrect types in method signature: (Lkotlin/jvm/functions/Function2<-TD;-Lorg/jetbrains/kotlin/ir/util/IdSignature;+TSymbol;>;TD;Lorg/jetbrains/kotlin/ir/util/IdSignature;)V */
    public DescriptorSymbolTableExtension$declareFromLinker$1(Function2 function2, DeclarationDescriptor declarationDescriptor, IdSignature idSignature) {
        super(0);
        this.$symbolFactory = function2;
        this.$descriptor = declarationDescriptor;
        this.$signature = idSignature;
    }

    /* JADX WARN: Incorrect return type in method signature: ()TSymbol; */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // kotlin.jvm.functions.Function0
    public final IrBindableSymbol invoke() {
        return (IrBindableSymbol) this.$symbolFactory.invoke((D) this.$descriptor, this.$signature);
    }
}
