package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: Add missing generic type declarations: [D] */
/* JADX INFO: compiled from: SymbolTableExtension.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u0002H\u0003\"\u0012\b\u0001\u0010\u0004*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u00060\u0005\"\b\b\u0002\u0010\u0006*\u00020\u0007\"\u0004\b\u0003\u0010\u0003\"\b\b\u0004\u0010\b*\u0002H\u0003\"\b\b\u0005\u0010\t*\u0002H\u0003\"\b\b\u0006\u0010\n*\u0002H\u0003\"\b\b\u0007\u0010\u000b*\u0002H\u0003\"\b\b\b\u0010\f*\u0002H\u0003\"\b\b\t\u0010\r*\u0002H\u0003\"\b\b\n\u0010\u000e*\u0002H\u0003\"\b\b\u000b\u0010\u000f*\u0002H\u00032\u0006\u0010\u0010\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0011\u0010\u0012"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "D", "Declaration", NativeSymbol.CLASS_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "SymbolOwner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Class", "TypeAlias", "Script", "Function", "Constructor", "Property", "ValueParameter", "TypeParameter", "it", "invoke", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/util/IdSignature;"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class SymbolTableExtension$declare$1<D> extends Lambda implements Function1<D, IdSignature> {
    final /* synthetic */ SymbolTableExtension<Declaration, Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolTableExtension$declare$1(SymbolTableExtension<Declaration, Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> symbolTableExtension) {
        super(1);
        this.this$0 = symbolTableExtension;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // kotlin.jvm.functions.Function1
    public final IdSignature invoke(D d) {
        return this.this$0.calculateSignature((Declaration) d);
    }
}
