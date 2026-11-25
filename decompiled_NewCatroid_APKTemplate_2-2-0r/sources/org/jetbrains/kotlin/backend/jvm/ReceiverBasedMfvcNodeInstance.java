package org.jetbrains.kotlin.backend.jvm;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MfvcNodeInstance.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001Bm\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0016\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007j\u0002`\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u0015¢\u0006\u0002\u0010\u0018J\u0018\u0010)\u001a\u00020*2\u0006\u0010\u0004\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0002J\u0013\u0010.\u001a\u0004\u0018\u00010\u00002\u0006\u0010/\u001a\u000200H\u0096\u0002J,\u00101\u001a\b\u0012\u0004\u0012\u00020\f0\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010,\u001a\u00020-2\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u001703H\u0016J4\u00101\u001a\b\u0012\u0004\u0012\u00020\f0\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010,\u001a\u00020-2\u0006\u00104\u001a\u00020*2\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u001703H\u0002J&\u00105\u001a\u00020\f2\u0006\u0010\u0002\u001a\u0002062\u0006\u0010,\u001a\u00020-2\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u001703H\u0016J\n\u00107\u001a\u0004\u0018\u00010\fH\u0002J$\u00108\u001a\b\u0012\u0004\u0012\u0002090\u000e2\u0006\u0010\u0002\u001a\u0002062\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\f0\u000eH\u0016R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0019\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u00020\"X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R$\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007j\u0002`\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ReceiverBasedMfvcNodeInstance;", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "node", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/backend/jvm/TypeArguments;", "receiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "fields", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "unboxMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "accessType", "Lorg/jetbrains/kotlin/backend/jvm/AccessType;", "saveVariable", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "", "(Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;Ljava/util/Map;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Ljava/util/List;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/backend/jvm/AccessType;Lkotlin/jvm/functions/Function1;)V", "getAccessType", "()Lorg/jetbrains/kotlin/backend/jvm/AccessType;", "getFields", "()Ljava/util/List;", "getNode", "()Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "receiverCopier", "Lorg/jetbrains/kotlin/backend/jvm/ExpressionCopierImpl;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getTypeArguments", "()Ljava/util/Map;", "getUnboxMethod", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "canUsePrivateAccess", "", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", "currentClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "get", "name", "Lorg/jetbrains/kotlin/name/Name;", "makeFlattenedGetterExpressions", "registerPossibleExtraBoxCreation", "Lkotlin/Function0;", "isInsideRecursion", "makeGetterExpression", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "makeReceiverCopy", "makeSetterStatements", "Lorg/jetbrains/kotlin/ir/IrStatement;", CompoundEntry.NAME_values, "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReceiverBasedMfvcNodeInstance implements MfvcNodeInstance {
    private final AccessType accessType;
    private final List<IrField> fields;
    private final MfvcNode node;
    private final ExpressionCopierImpl receiverCopier;
    private final Function1<IrVariable, Unit> saveVariable;
    private final IrBlockBuilder scope;
    private final IrSimpleType type;
    private final Map<IrTypeParameterSymbol, IrType> typeArguments;
    private final IrSimpleFunction unboxMethod;

    /* JADX WARN: Multi-variable type inference failed */
    public ReceiverBasedMfvcNodeInstance(IrBlockBuilder scope, MfvcNode node, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, IrExpression irExpression, List<? extends IrField> list, IrSimpleFunction irSimpleFunction, AccessType accessType, Function1<? super IrVariable, Unit> saveVariable) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(accessType, "accessType");
        Intrinsics.checkNotNullParameter(saveVariable, "saveVariable");
        this.scope = scope;
        this.node = node;
        this.typeArguments = typeArguments;
        this.fields = list;
        this.unboxMethod = irSimpleFunction;
        this.accessType = accessType;
        this.saveVariable = saveVariable;
        this.type = MfvcNodeInstanceKt.makeTypeFromMfvcNodeAndTypeArguments(getNode(), getTypeArguments());
        this.receiverCopier = new ExpressionCopierImpl(irExpression, scope, saveVariable);
        if (list != 0 && list.isEmpty()) {
            throw new IllegalArgumentException("Empty list of fields".toString());
        }
        if ((getNode() instanceof RootMfvcNode) != (irSimpleFunction == null)) {
            throw new IllegalArgumentException("Only root node has node getter".toString());
        }
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public MfvcNode getNode() {
        return this.node;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public Map<IrTypeParameterSymbol, IrType> getTypeArguments() {
        return this.typeArguments;
    }

    public final List<IrField> getFields() {
        return this.fields;
    }

    public final IrSimpleFunction getUnboxMethod() {
        return this.unboxMethod;
    }

    public final AccessType getAccessType() {
        return this.accessType;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public IrSimpleType getType() {
        return this.type;
    }

    private final IrExpression makeReceiverCopy() {
        return this.receiverCopier.makeCopy();
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public List<IrExpression> makeFlattenedGetterExpressions(IrBlockBuilder scope, IrClass currentClass, Function0<Unit> registerPossibleExtraBoxCreation) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(currentClass, "currentClass");
        Intrinsics.checkNotNullParameter(registerPossibleExtraBoxCreation, "registerPossibleExtraBoxCreation");
        return makeFlattenedGetterExpressions(scope, currentClass, false, registerPossibleExtraBoxCreation);
    }

    private static final List<IrExpression> makeFlattenedGetterExpressions$makeRecursiveResult(ReceiverBasedMfvcNodeInstance receiverBasedMfvcNodeInstance, IrBlockBuilder irBlockBuilder, IrClass irClass, Function0<Unit> function0, MfvcNodeWithSubnodes mfvcNodeWithSubnodes) {
        List<NameableMfvcNode> subnodes = mfvcNodeWithSubnodes.getSubnodes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = subnodes.iterator();
        while (it.hasNext()) {
            ReceiverBasedMfvcNodeInstance receiverBasedMfvcNodeInstance2 = receiverBasedMfvcNodeInstance.get(MfvcNodeKt.getName((NameableMfvcNode) it.next()));
            Intrinsics.checkNotNull(receiverBasedMfvcNodeInstance2);
            CollectionsKt.addAll(arrayList, receiverBasedMfvcNodeInstance2.makeFlattenedGetterExpressions(irBlockBuilder, irClass, true, function0));
        }
        return arrayList;
    }

    private final List<IrExpression> makeFlattenedGetterExpressions(IrBlockBuilder scope, IrClass currentClass, boolean isInsideRecursion, Function0<Unit> registerPossibleExtraBoxCreation) {
        MfvcNode node = getNode();
        if (node instanceof LeafMfvcNode) {
            return CollectionsKt.listOf(makeGetterExpression(scope, currentClass, registerPossibleExtraBoxCreation));
        }
        if (node instanceof RootMfvcNode) {
            return makeFlattenedGetterExpressions$makeRecursiveResult(this, scope, currentClass, registerPossibleExtraBoxCreation, (MfvcNodeWithSubnodes) getNode());
        }
        if (!(node instanceof IntermediateMfvcNode)) {
            throw new NoWhenBranchMatchedException();
        }
        if (isInsideRecursion || ((IntermediateMfvcNode) getNode()).getHasPureUnboxMethod()) {
            return makeFlattenedGetterExpressions$makeRecursiveResult(this, scope, currentClass, registerPossibleExtraBoxCreation, (MfvcNodeWithSubnodes) getNode());
        }
        IrBlockBuilder irBlockBuilder = scope;
        IrBlockBuilder irBlockBuilder2 = scope;
        return ((IntermediateMfvcNode) getNode()).getRootNode().createInstanceFromBox(scope, getTypeArguments(), ExpressionHelpersKt.irGet(irBlockBuilder, MfvcNodeInstanceKt.savableStandaloneVariableWithSetter$default(irBlockBuilder2, makeGetterExpression(irBlockBuilder, currentClass, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.ReceiverBasedMfvcNodeInstance$makeFlattenedGetterExpressions$value$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }
        }), null, false, JvmLoweredDeclarationOrigin.TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE.INSTANCE, true, this.saveVariable, 6, null)), this.accessType, this.saveVariable).makeFlattenedGetterExpressions(scope, currentClass, registerPossibleExtraBoxCreation);
    }

    private static final IrGetFieldImpl makeGetterExpression$lambda$8$makeFieldRead(IrBuilderWithScope irBuilderWithScope, ReceiverBasedMfvcNodeInstance receiverBasedMfvcNodeInstance, IrField irField) {
        return ExpressionHelpersKt.irGetField$default(irBuilderWithScope, irField.getIsStatic() ? null : receiverBasedMfvcNodeInstance.makeReceiverCopy(), irField, null, 4, null);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public IrExpression makeGetterExpression(IrBuilderWithScope scope, IrClass currentClass, Function0<Unit> registerPossibleExtraBoxCreation) {
        List<IrField> list;
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(currentClass, "currentClass");
        Intrinsics.checkNotNullParameter(registerPossibleExtraBoxCreation, "registerPossibleExtraBoxCreation");
        if (getNode() instanceof RootMfvcNode) {
            IrExpression irExpressionMakeReceiverCopy = makeReceiverCopy();
            Intrinsics.checkNotNull(irExpressionMakeReceiverCopy);
            return irExpressionMakeReceiverCopy;
        }
        if ((getNode() instanceof LeafMfvcNode) && ((LeafMfvcNode) getNode()).getHasPureUnboxMethod() && canUsePrivateAccess((NameableMfvcNode) getNode(), currentClass) && (list = this.fields) != null) {
            return makeGetterExpression$lambda$8$makeFieldRead(scope, this, (IrField) CollectionsKt.single((List) list));
        }
        if ((getNode() instanceof LeafMfvcNode) && this.accessType == AccessType.UseFields) {
            List<IrField> list2 = this.fields;
            if (list2 == null) {
                throw new IllegalArgumentException(("Invalid getter to " + getNode()).toString());
            }
            return makeGetterExpression$lambda$8$makeFieldRead(scope, this, (IrField) CollectionsKt.single((List) list2));
        }
        if ((getNode() instanceof IntermediateMfvcNode) && this.accessType == AccessType.UseFields) {
            if (this.fields == null) {
                throw new IllegalArgumentException(("Invalid getter to " + getNode()).toString());
            }
            MfvcNodeWithSubnodes mfvcNodeWithSubnodes = (MfvcNodeWithSubnodes) getNode();
            Map<IrTypeParameterSymbol, IrType> typeArguments = getTypeArguments();
            List<IrField> list3 = this.fields;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            Iterator<T> it = list3.iterator();
            while (it.hasNext()) {
                arrayList.add(makeGetterExpression$lambda$8$makeFieldRead(scope, this, (IrField) it.next()));
            }
            return MfvcNodeKt.makeBoxedExpression(mfvcNodeWithSubnodes, scope, typeArguments, arrayList, registerPossibleExtraBoxCreation);
        }
        IrSimpleFunction irSimpleFunction = this.unboxMethod;
        if (irSimpleFunction != null) {
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(scope, irSimpleFunction);
            IrValueParameter dispatchReceiverParameter = this.unboxMethod.getDispatchReceiverParameter();
            if (dispatchReceiverParameter != null) {
                IrGetObjectValueImpl irGetObjectValueImplMakeReceiverCopy = makeReceiverCopy();
                if (irGetObjectValueImplMakeReceiverCopy == null) {
                    IrClass erasedUpperBound = JvmIrTypeUtilsKt.getErasedUpperBound(dispatchReceiverParameter.getType());
                    if (!erasedUpperBound.getIsCompanion()) {
                        throw new IllegalArgumentException(("Expected a dispatch receiver for:\n" + DumpIrTreeKt.dump$default(this.unboxMethod, null, 1, null)).toString());
                    }
                    irGetObjectValueImplMakeReceiverCopy = IrBuilderKt.irGetObject(scope, erasedUpperBound.getSymbol());
                }
                irFunctionAccessExpressionIrCall.setDispatchReceiver(irGetObjectValueImplMakeReceiverCopy);
            }
            return irFunctionAccessExpressionIrCall;
        }
        throw new IllegalStateException(("Unbox method must exist for " + getNode()).toString());
    }

    private final boolean canUsePrivateAccess(NameableMfvcNode node, IrClass currentClass) {
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(MfvcNodeKt.getUnboxMethod(node));
        if (parentAsClass.getIsCompanion()) {
            parentAsClass = IrUtilsKt.getParentAsClass(parentAsClass);
        }
        return Intrinsics.areEqual(parentAsClass, currentClass);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public ReceiverBasedMfvcNodeInstance get(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Pair<NameableMfvcNode, IntRange> subnodeAndIndices = MfvcNodeKt.getSubnodeAndIndices(getNode(), name2);
        if (subnodeAndIndices == null) {
            return null;
        }
        return subnodeAndIndices.component1().createInstanceFromBox(this.scope, getTypeArguments(), makeReceiverCopy(), this.accessType, this.saveVariable);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeInstance
    public List<IrStatement> makeSetterStatements(IrBuilderWithScope scope, List<? extends IrExpression> values) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(values, "values");
        MfvcNodeInstanceKt.checkValuesCount(this, values);
        List<IrField> list = this.fields;
        if (list == null) {
            throw new IllegalArgumentException((getNode() + " is immutable as it has custom getter and so no backing fields").toString());
        }
        List<IrField> list2 = list;
        Iterator<T> it = list2.iterator();
        List<? extends IrExpression> list3 = values;
        Iterator<T> it2 = list3.iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(list2, 10), CollectionsKt.collectionSizeOrDefault(list3, 10)));
        while (it.hasNext() && it2.hasNext()) {
            Object next = it.next();
            arrayList.add(ExpressionHelpersKt.irSetField$default(scope, makeReceiverCopy(), (IrField) next, (IrExpression) it2.next(), null, 8, null));
        }
        return arrayList;
    }
}
