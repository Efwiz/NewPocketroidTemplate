package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;

/* JADX INFO: compiled from: SymbolTableExtension.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u0003*\u0004\b\u0003\u0010\u0004*\u0004\b\u0004\u0010\u0005*\u0004\b\u0005\u0010\u0006*\u0004\b\u0006\u0010\u0007*\u0004\b\u0007\u0010\b2\u00020\tB\u0005¢\u0006\u0002\u0010\nJ\u0015\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000eJ\u0015\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00028\u0004H&¢\u0006\u0002\u0010\u0011J\u0015\u0010\u0012\u001a\u00020\u00132\u0006\u0010\r\u001a\u00028\u0003H&¢\u0006\u0002\u0010\u0014J\u0015\u0010\u0015\u001a\u00020\u00162\u0006\u0010\r\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0017J\u0015\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u00028\u0005H&¢\u0006\u0002\u0010\u001aJ\u0015\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\r\u001a\u00028\u0005H&¢\u0006\u0002\u0010\u001dJ\u0015\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\r\u001a\u00028\u0007H&¢\u0006\u0002\u0010 J\u0015\u0010!\u001a\u00020\"2\u0006\u0010\r\u001a\u00028\u0002H&¢\u0006\u0002\u0010#J\u0015\u0010$\u001a\u00020\u00132\u0006\u0010\r\u001a\u00028\u0003H&¢\u0006\u0002\u0010\u0014J\u0015\u0010%\u001a\u00020&2\u0006\u0010\r\u001a\u00028\u0001H&¢\u0006\u0002\u0010'J\u0015\u0010(\u001a\u00020\u001f2\u0006\u0010\r\u001a\u00028\u0007H&¢\u0006\u0002\u0010 J\u0015\u0010)\u001a\u00020*2\u0006\u0010\r\u001a\u00028\u0006H&¢\u0006\u0002\u0010+¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTableExtension;", "Class", "TypeAlias", "Script", "Function", "Constructor", "Property", "ValueParameter", "TypeParameter", "", "()V", "referenceClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "declaration", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "referenceConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "referenceDeclaredFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "referenceEnumEntry", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "referenceField", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "referenceProperty", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "referenceScopedTypeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "referenceScript", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "referenceSimpleFunction", "referenceTypeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "referenceTypeParameter", "referenceValueParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ReferenceSymbolTableExtension<Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> {
    public abstract IrClassSymbol referenceClass(Class declaration);

    public abstract IrConstructorSymbol referenceConstructor(Constructor declaration);

    public abstract IrSimpleFunctionSymbol referenceDeclaredFunction(Function declaration);

    public abstract IrEnumEntrySymbol referenceEnumEntry(Class declaration);

    public abstract IrFieldSymbol referenceField(Property declaration);

    public abstract IrPropertySymbol referenceProperty(Property declaration);

    public abstract IrTypeParameterSymbol referenceScopedTypeParameter(TypeParameter declaration);

    public abstract IrScriptSymbol referenceScript(Script declaration);

    public abstract IrSimpleFunctionSymbol referenceSimpleFunction(Function declaration);

    public abstract IrTypeAliasSymbol referenceTypeAlias(TypeAlias declaration);

    public abstract IrTypeParameterSymbol referenceTypeParameter(TypeParameter declaration);

    public abstract IrValueParameterSymbol referenceValueParameter(ValueParameter declaration);
}
