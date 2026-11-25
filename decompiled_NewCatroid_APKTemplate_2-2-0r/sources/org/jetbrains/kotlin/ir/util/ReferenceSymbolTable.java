package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ReferenceSymbolTable.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0010\u0010 \u001a\u00020!2\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0010\u0010\"\u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0010\u0010$\u001a\u00020%2\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0010\u0010&\u001a\u00020'2\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0010\u0010(\u001a\u00020)2\u0006\u0010\u001a\u001a\u00020\u001bH&RN\u0010\u0002\u001a6\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\u0003j\u0002`\f8&X§\u0004¢\u0006\f\u0012\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "", "descriptorExtension", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTableExtension;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/ir/util/DescriptorBasedReferenceSymbolTableExtension;", "getDescriptorExtension$annotations", "()V", "getDescriptorExtension", "()Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTableExtension;", "enterScope", "", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "leaveScope", "referenceClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "referenceConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "referenceEnumEntry", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "referenceField", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "referenceProperty", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "referenceSimpleFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "referenceTypeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "referenceTypeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReferenceSymbolTable {

    /* JADX INFO: compiled from: ReferenceSymbolTable.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @ObsoleteDescriptorBasedAPI
        public static /* synthetic */ void getDescriptorExtension$annotations() {
        }
    }

    void enterScope(IrDeclaration owner);

    void enterScope(IrSymbol symbol);

    ReferenceSymbolTableExtension<ClassDescriptor, TypeAliasDescriptor, ScriptDescriptor, FunctionDescriptor, ClassConstructorDescriptor, PropertyDescriptor, ParameterDescriptor, TypeParameterDescriptor> getDescriptorExtension();

    void leaveScope(IrDeclaration owner);

    void leaveScope(IrSymbol symbol);

    IrClassSymbol referenceClass(IdSignature signature);

    IrConstructorSymbol referenceConstructor(IdSignature signature);

    IrEnumEntrySymbol referenceEnumEntry(IdSignature signature);

    IrFieldSymbol referenceField(IdSignature signature);

    IrPropertySymbol referenceProperty(IdSignature signature);

    IrSimpleFunctionSymbol referenceSimpleFunction(IdSignature signature);

    IrTypeAliasSymbol referenceTypeAlias(IdSignature signature);

    IrTypeParameterSymbol referenceTypeParameter(IdSignature signature);
}
