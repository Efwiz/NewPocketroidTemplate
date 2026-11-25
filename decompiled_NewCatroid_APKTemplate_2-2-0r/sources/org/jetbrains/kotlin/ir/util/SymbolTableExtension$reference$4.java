package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.name.SpecialNames;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: Add missing generic type declarations: [Symbol] */
/* JADX INFO: compiled from: SymbolTableExtension.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0002*\u0002H\u0003\"\u0012\b\u0001\u0010\u0001*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0002\u0010\u0005*\u00020\u0006\"\u0004\b\u0003\u0010\u0003\"\b\b\u0004\u0010\u0007*\u0002H\u0003\"\b\b\u0005\u0010\b*\u0002H\u0003\"\b\b\u0006\u0010\t*\u0002H\u0003\"\b\b\u0007\u0010\n*\u0002H\u0003\"\b\b\b\u0010\u000b*\u0002H\u0003\"\b\b\t\u0010\f*\u0002H\u0003\"\b\b\n\u0010\r*\u0002H\u0003\"\b\b\u000b\u0010\u000e*\u0002H\u0003H\n¢\u0006\u0004\b\u000f\u0010\u0010"}, d2 = {SpecialNames.ANONYMOUS_STRING, NativeSymbol.CLASS_NAME, "D", "Declaration", "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "SymbolOwner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Class", "TypeAlias", "Script", "Function", "Constructor", "Property", "ValueParameter", "TypeParameter", "invoke", "()Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class SymbolTableExtension$reference$4<Symbol> extends Lambda implements Function0<Symbol> {
    final /* synthetic */ D $declaration;
    final /* synthetic */ Function1<D, Symbol> $privateSymbolFactory;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SymbolTableExtension$reference$4(Function1<? super D, ? extends Symbol> function1, D d) {
        super(0);
        this.$privateSymbolFactory = function1;
        this.$declaration = d;
    }

    /* JADX WARN: Incorrect return type in method signature: ()TSymbol; */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // kotlin.jvm.functions.Function0
    public final IrBindableSymbol invoke() {
        return (IrBindableSymbol) this.$privateSymbolFactory.invoke(this.$declaration);
    }
}
