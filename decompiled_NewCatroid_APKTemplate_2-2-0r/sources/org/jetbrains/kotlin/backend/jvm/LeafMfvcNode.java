package org.jetbrains.kotlin.backend.jvm;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MfvcNode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJN\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0016\u0010$\u001a\u0012\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00030%j\u0002`'2\b\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020+2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020/0-H\u0016J\b\u00100\u001a\u000201H\u0016R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/LeafMfvcNode;", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "methodFullNameMode", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl$Companion$MethodFullNameMode;", "nameParts", "", "Lorg/jetbrains/kotlin/name/Name;", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "unboxMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "defaultMethodsImplementationSourceNode", "Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation;", "(Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl$Companion$MethodFullNameMode;Ljava/util/List;Lorg/jetbrains/kotlin/ir/declarations/IrField;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation;)V", "getField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "hasPureUnboxMethod", "", "getHasPureUnboxMethod", "()Z", "leavesCount", "", "getLeavesCount", "()I", "namedNodeImpl", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl;", "getNamedNodeImpl", "()Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "createInstanceFromBox", "Lorg/jetbrains/kotlin/backend/jvm/ReceiverBasedMfvcNodeInstance;", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/backend/jvm/TypeArguments;", "receiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "accessType", "Lorg/jetbrains/kotlin/backend/jvm/AccessType;", "saveVariable", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LeafMfvcNode implements NameableMfvcNode {
    private final IrField field;
    private final boolean hasPureUnboxMethod;
    private final NameableMfvcNodeImpl namedNodeImpl;
    private final IrType type;

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNode
    public int getLeavesCount() {
        return 1;
    }

    public LeafMfvcNode(IrType type, NameableMfvcNodeImpl.Companion.MethodFullNameMode methodFullNameMode, List<Name> nameParts, IrField irField, IrSimpleFunction unboxMethod, UnboxFunctionImplementation defaultMethodsImplementationSourceNode) {
        IrClass parentAsClass;
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(methodFullNameMode, "methodFullNameMode");
        Intrinsics.checkNotNullParameter(nameParts, "nameParts");
        Intrinsics.checkNotNullParameter(unboxMethod, "unboxMethod");
        Intrinsics.checkNotNullParameter(defaultMethodsImplementationSourceNode, "defaultMethodsImplementationSourceNode");
        this.type = type;
        this.field = irField;
        this.hasPureUnboxMethod = defaultMethodsImplementationSourceNode.getHasPureUnboxMethod();
        this.namedNodeImpl = new NameableMfvcNodeImpl(methodFullNameMode, nameParts, unboxMethod);
        IrClass[] irClassArr = new IrClass[3];
        irClassArr[0] = (irField == null || (parentAsClass = IrUtilsKt.getParentAsClass(irField)) == null || IrUtilsKt.getParentAsClass(unboxMethod).getIsCompanion()) ? null : parentAsClass;
        irClassArr[1] = IrUtilsKt.getParentAsClass(unboxMethod);
        IrValueParameter dispatchReceiverParameter = unboxMethod.getDispatchReceiverParameter();
        IrType type2 = dispatchReceiverParameter != null ? dispatchReceiverParameter.getType() : null;
        IrSimpleType irSimpleType = type2 instanceof IrSimpleType ? (IrSimpleType) type2 : null;
        irClassArr[2] = irSimpleType != null ? JvmIrTypeUtilsKt.getErasedUpperBound(irSimpleType) : null;
        MfvcNodeKt.requireSameClasses(irClassArr);
        MfvcNodeKt.validateGettingAccessorParameters(unboxMethod);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNode
    public IrType getType() {
        return this.type;
    }

    public final IrField getField() {
        return this.field;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.NameableMfvcNode
    public boolean getHasPureUnboxMethod() {
        return this.hasPureUnboxMethod;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.NameableMfvcNode
    public NameableMfvcNodeImpl getNamedNodeImpl() {
        return this.namedNodeImpl;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNode
    public ReceiverBasedMfvcNodeInstance createInstanceFromBox(IrBlockBuilder scope, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, IrExpression receiver, AccessType accessType, Function1<? super IrVariable, Unit> saveVariable) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(accessType, "accessType");
        Intrinsics.checkNotNullParameter(saveVariable, "saveVariable");
        LeafMfvcNode leafMfvcNode = this;
        IrField irField = this.field;
        return new ReceiverBasedMfvcNodeInstance(scope, leafMfvcNode, typeArguments, receiver, irField != null ? CollectionsKt.listOf(irField) : null, MfvcNodeKt.getUnboxMethod(this), accessType, saveVariable);
    }

    public String toString() {
        return MfvcNodeKt.getFullFieldName(this) + ": " + RenderIrElementKt.render$default(getType(), (DumpIrTreeOptions) null, 1, (Object) null);
    }
}
