package org.jetbrains.kotlin.backend.jvm;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
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
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002BI\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\b\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011JN\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0016\u0010.\u001a\u0012\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u0002010/j\u0002`22\b\u00103\u001a\u0004\u0018\u0001042\u0006\u00105\u001a\u0002062\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020:08H\u0016J\b\u0010;\u001a\u00020<H\u0016R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0014R\u0014\u0010\"\u001a\u00020#X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)¨\u0006="}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/IntermediateMfvcNode;", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeWithSubnodes;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "methodFullNameMode", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl$Companion$MethodFullNameMode;", "nameParts", "", "Lorg/jetbrains/kotlin/name/Name;", "subnodes", "unboxMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "defaultMethodsImplementationSourceNode", "Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation;", "rootNode", "Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;", "(Lorg/jetbrains/kotlin/ir/types/IrSimpleType;Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl$Companion$MethodFullNameMode;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation;Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;)V", "allUnboxMethods", "getAllUnboxMethods", "()Ljava/util/List;", "boxMethod", "getBoxMethod", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "hasPureUnboxMethod", "", "getHasPureUnboxMethod", "()Z", "leavesCount", "", "getLeavesCount", "()I", "leavesUnboxMethods", "getLeavesUnboxMethods", "namedNodeImpl", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl;", "getNamedNodeImpl", "()Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl;", "getRootNode", "()Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "createInstanceFromBox", "Lorg/jetbrains/kotlin/backend/jvm/ReceiverBasedMfvcNodeInstance;", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/backend/jvm/TypeArguments;", "receiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "accessType", "Lorg/jetbrains/kotlin/backend/jvm/AccessType;", "saveVariable", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntermediateMfvcNode extends MfvcNodeWithSubnodes implements NameableMfvcNode {
    private final List<IrSimpleFunction> allUnboxMethods;
    private final boolean hasPureUnboxMethod;
    private final List<IrSimpleFunction> leavesUnboxMethods;
    private final NameableMfvcNodeImpl namedNodeImpl;
    private final RootMfvcNode rootNode;
    private final IrSimpleType type;

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNode
    public IrSimpleType getType() {
        return this.type;
    }

    public final RootMfvcNode getRootNode() {
        return this.rootNode;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IntermediateMfvcNode(IrSimpleType type, NameableMfvcNodeImpl.Companion.MethodFullNameMode methodFullNameMode, List<Name> nameParts, List<? extends NameableMfvcNode> subnodes, IrSimpleFunction unboxMethod, UnboxFunctionImplementation defaultMethodsImplementationSourceNode, RootMfvcNode rootNode) {
        boolean z;
        super(subnodes, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(methodFullNameMode, "methodFullNameMode");
        Intrinsics.checkNotNullParameter(nameParts, "nameParts");
        Intrinsics.checkNotNullParameter(subnodes, "subnodes");
        Intrinsics.checkNotNullParameter(unboxMethod, "unboxMethod");
        Intrinsics.checkNotNullParameter(defaultMethodsImplementationSourceNode, "defaultMethodsImplementationSourceNode");
        Intrinsics.checkNotNullParameter(rootNode, "rootNode");
        this.type = type;
        this.rootNode = rootNode;
        if (defaultMethodsImplementationSourceNode.getHasPureUnboxMethod()) {
            List<? extends NameableMfvcNode> list = subnodes;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (!((NameableMfvcNode) it.next()).getHasPureUnboxMethod()) {
                            z = false;
                        }
                    }
                }
            }
            z = true;
        } else {
            z = false;
        }
        this.hasPureUnboxMethod = z;
        this.namedNodeImpl = new NameableMfvcNodeImpl(methodFullNameMode, nameParts, unboxMethod);
        if (!MfvcNodeKt.needsMfvcFlattening(getType())) {
            throw new IllegalArgumentException(("MFVC type expected but got% " + RenderIrElementKt.render$default(getType(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        if (!Intrinsics.areEqual(JvmIrTypeUtilsKt.getErasedUpperBound(getType()), this.rootNode.getMfvc())) {
            throw new IllegalArgumentException(("Root node must point at the RootNode of class " + RenderIrElementKt.render$default(JvmIrTypeUtilsKt.getErasedUpperBound(getType()), (DumpIrTreeOptions) null, 1, (Object) null) + " but points at " + RenderIrElementKt.render$default(this.rootNode.getMfvc(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        IrClass[] irClassArr = new IrClass[2];
        irClassArr[0] = IrUtilsKt.getParentAsClass(unboxMethod);
        IrValueParameter dispatchReceiverParameter = unboxMethod.getDispatchReceiverParameter();
        IrSimpleType irSimpleType = (IrSimpleType) (dispatchReceiverParameter != null ? dispatchReceiverParameter.getType() : null);
        irClassArr[1] = irSimpleType != null ? JvmIrTypeUtilsKt.getErasedUpperBound(irSimpleType) : null;
        MfvcNodeKt.requireSameClasses(irClassArr);
        MfvcNodeKt.validateGettingAccessorParameters(unboxMethod);
        this.allUnboxMethods = CollectionsKt.plus((Collection) getAllInnerUnboxMethods(), (Iterable) CollectionsKt.listOf(unboxMethod));
        this.leavesUnboxMethods = MfvcNodeKt.collectLeavesUnboxMethods(this);
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
    public int getLeavesCount() {
        return getLeaves().size();
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeWithSubnodes
    public List<IrSimpleFunction> getAllUnboxMethods() {
        return this.allUnboxMethods;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeWithSubnodes
    public IrSimpleFunction getBoxMethod() {
        return this.rootNode.getBoxMethod();
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeWithSubnodes
    public List<IrSimpleFunction> getLeavesUnboxMethods() {
        return this.leavesUnboxMethods;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNode
    public ReceiverBasedMfvcNodeInstance createInstanceFromBox(IrBlockBuilder scope, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, IrExpression receiver, AccessType accessType, Function1<? super IrVariable, Unit> saveVariable) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(accessType, "accessType");
        Intrinsics.checkNotNullParameter(saveVariable, "saveVariable");
        return new ReceiverBasedMfvcNodeInstance(scope, this, typeArguments, receiver, getFields(), MfvcNodeKt.getUnboxMethod(this), accessType, saveVariable);
    }

    public String toString() {
        return MfvcNodeKt.getFullFieldName(this) + ": " + RenderIrElementKt.render$default(getType(), (DumpIrTreeOptions) null, 1, (Object) null) + '\n' + StringsKt.prependIndent(CollectionsKt.joinToString$default(getSubnodes(), "\n", null, null, 0, null, null, 62, null), "    ");
    }
}
