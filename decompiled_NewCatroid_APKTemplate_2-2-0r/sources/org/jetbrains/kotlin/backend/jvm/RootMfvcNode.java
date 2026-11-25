package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
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
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: MfvcNode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001BS\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\r\u001a\u00020\u000b\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010JN\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0016\u0010-\u001a\u0012\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u0002000.j\u0002`12\b\u00102\u001a\u0004\u0018\u0001032\u0006\u00104\u001a\u0002052\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020907H\u0016J\b\u0010:\u001a\u00020;H\u0016R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\f\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010!R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0015R\u0011\u0010\r\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0015R\u0014\u0010%\u001a\u00020&X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeWithSubnodes;", "mfvc", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "subnodes", "", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", "oldPrimaryConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "newPrimaryConstructor", "primaryConstructorImpl", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "boxMethod", "specializedEqualsMethod", "createdNewSpecializedEqualsMethod", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/util/List;Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Z)V", "allUnboxMethods", "getAllUnboxMethods", "()Ljava/util/List;", "getBoxMethod", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getCreatedNewSpecializedEqualsMethod", "()Z", "leavesCount", "", "getLeavesCount", "()I", "leavesUnboxMethods", "getLeavesUnboxMethods", "getMfvc", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getNewPrimaryConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getOldPrimaryConstructor", "getPrimaryConstructorImpl", "getSpecializedEqualsMethod", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "createInstanceFromBox", "Lorg/jetbrains/kotlin/backend/jvm/ReceiverBasedMfvcNodeInstance;", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/backend/jvm/TypeArguments;", "receiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "accessType", "Lorg/jetbrains/kotlin/backend/jvm/AccessType;", "saveVariable", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RootMfvcNode extends MfvcNodeWithSubnodes {
    private final IrSimpleFunction boxMethod;
    private final boolean createdNewSpecializedEqualsMethod;
    private final List<IrSimpleFunction> leavesUnboxMethods;
    private final IrClass mfvc;
    private final IrConstructor newPrimaryConstructor;
    private final IrConstructor oldPrimaryConstructor;
    private final IrSimpleFunction primaryConstructorImpl;
    private final IrSimpleFunction specializedEqualsMethod;
    private final IrSimpleType type;

    public final IrClass getMfvc() {
        return this.mfvc;
    }

    public final IrConstructor getOldPrimaryConstructor() {
        return this.oldPrimaryConstructor;
    }

    public final IrConstructor getNewPrimaryConstructor() {
        return this.newPrimaryConstructor;
    }

    public final IrSimpleFunction getPrimaryConstructorImpl() {
        return this.primaryConstructorImpl;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeWithSubnodes
    public IrSimpleFunction getBoxMethod() {
        return this.boxMethod;
    }

    public final IrSimpleFunction getSpecializedEqualsMethod() {
        return this.specializedEqualsMethod;
    }

    public final boolean getCreatedNewSpecializedEqualsMethod() {
        return this.createdNewSpecializedEqualsMethod;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RootMfvcNode(IrClass mfvc, List<? extends NameableMfvcNode> subnodes, IrConstructor irConstructor, IrConstructor irConstructor2, IrSimpleFunction irSimpleFunction, IrSimpleFunction boxMethod, IrSimpleFunction specializedEqualsMethod, boolean z) {
        List<IrValueParameter> valueParameters;
        List<IrValueParameter> valueParameters2;
        List<IrTypeParameter> typeParameters;
        super(subnodes, null);
        Intrinsics.checkNotNullParameter(mfvc, "mfvc");
        Intrinsics.checkNotNullParameter(subnodes, "subnodes");
        Intrinsics.checkNotNullParameter(boxMethod, "boxMethod");
        Intrinsics.checkNotNullParameter(specializedEqualsMethod, "specializedEqualsMethod");
        this.mfvc = mfvc;
        this.oldPrimaryConstructor = irConstructor;
        this.newPrimaryConstructor = irConstructor2;
        this.primaryConstructorImpl = irSimpleFunction;
        this.boxMethod = boxMethod;
        this.specializedEqualsMethod = specializedEqualsMethod;
        this.createdNewSpecializedEqualsMethod = z;
        this.type = IrUtilsKt.getDefaultType(mfvc);
        this.leavesUnboxMethods = MfvcNodeKt.collectLeavesUnboxMethods(this);
        if (!MfvcNodeKt.needsMfvcFlattening(getType())) {
            throw new IllegalArgumentException(("MFVC type expected but got: " + RenderIrElementKt.render$default(getType(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        for (IrConstructor irConstructor3 : CollectionsKt.listOfNotNull((Object[]) new IrConstructor[]{irConstructor, irConstructor2})) {
            if (!irConstructor3.getIsPrimary()) {
                throw new IllegalArgumentException(("Expected a primary constructor but got:\n" + DumpIrTreeKt.dump$default(irConstructor3, null, 1, null)).toString());
            }
        }
        IrClass[] irClassArr = new IrClass[9];
        irClassArr[0] = this.mfvc;
        IrConstructor irConstructor4 = this.oldPrimaryConstructor;
        irClassArr[1] = irConstructor4 != null ? IrUtilsKt.getParentAsClass(irConstructor4) : null;
        IrConstructor irConstructor5 = this.newPrimaryConstructor;
        irClassArr[2] = irConstructor5 != null ? IrUtilsKt.getParentAsClass(irConstructor5) : null;
        IrSimpleFunction irSimpleFunction2 = this.primaryConstructorImpl;
        irClassArr[3] = irSimpleFunction2 != null ? IrUtilsKt.getParentAsClass(irSimpleFunction2) : null;
        irClassArr[4] = IrUtilsKt.getParentAsClass(getBoxMethod());
        irClassArr[5] = IrUtilsKt.getParentAsClass(this.specializedEqualsMethod);
        IrConstructor irConstructor6 = this.oldPrimaryConstructor;
        irClassArr[6] = irConstructor6 != null ? AdditionalIrUtilsKt.getConstructedClass(irConstructor6) : null;
        IrConstructor irConstructor7 = this.newPrimaryConstructor;
        irClassArr[7] = irConstructor7 != null ? AdditionalIrUtilsKt.getConstructedClass(irConstructor7) : null;
        irClassArr[8] = JvmIrTypeUtilsKt.getErasedUpperBound(getBoxMethod().getReturnType());
        MfvcNodeKt.requireSameClasses(irClassArr);
        IrSimpleFunction irSimpleFunction3 = this.primaryConstructorImpl;
        if (irSimpleFunction3 != null && !IrTypePredicatesKt.isUnit(irSimpleFunction3.getReturnType())) {
            StringBuilder sb = new StringBuilder("Constructor-impl must return Unit but returns ");
            IrSimpleFunction irSimpleFunction4 = this.primaryConstructorImpl;
            Intrinsics.checkNotNull(irSimpleFunction4);
            throw new IllegalArgumentException(sb.append(RenderIrElementKt.render$default(irSimpleFunction4.getReturnType(), (DumpIrTreeOptions) null, 1, (Object) null)).toString().toString());
        }
        if (!IrTypePredicatesKt.isBoolean(this.specializedEqualsMethod.getReturnType())) {
            throw new IllegalArgumentException(("Specialized equals method must return Boolean but returns " + RenderIrElementKt.render$default(this.specializedEqualsMethod.getReturnType(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        IrConstructor irConstructor8 = this.oldPrimaryConstructor;
        List<IrTypeParameter> typeParameters2 = irConstructor8 != null ? irConstructor8.getTypeParameters() : null;
        if (typeParameters2 == null || typeParameters2.isEmpty()) {
            IrConstructor irConstructor9 = this.newPrimaryConstructor;
            List<IrTypeParameter> typeParameters3 = irConstructor9 != null ? irConstructor9.getTypeParameters() : null;
            if (typeParameters3 == null || typeParameters3.isEmpty()) {
                Integer[] numArr = new Integer[3];
                numArr[0] = Integer.valueOf(this.mfvc.getTypeParameters().size());
                numArr[1] = Integer.valueOf(getBoxMethod().getTypeParameters().size());
                IrSimpleFunction irSimpleFunction5 = this.primaryConstructorImpl;
                numArr[2] = (irSimpleFunction5 == null || (typeParameters = irSimpleFunction5.getTypeParameters()) == null) ? null : Integer.valueOf(typeParameters.size());
                MfvcNodeKt.requireSameSizes(numArr);
                if (!this.specializedEqualsMethod.getTypeParameters().isEmpty()) {
                    StringBuilder sb2 = new StringBuilder("Specialized equals method must not contain type parameters but has ");
                    List<IrTypeParameter> typeParameters4 = this.specializedEqualsMethod.getTypeParameters();
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters4, 10));
                    Iterator<T> it = typeParameters4.iterator();
                    while (it.hasNext()) {
                        arrayList.add(RenderIrElementKt.render$default(IrTypesKt.getDefaultType((IrTypeParameter) it.next()), (DumpIrTreeOptions) null, 1, (Object) null));
                    }
                    throw new IllegalArgumentException(sb2.append(arrayList).toString().toString());
                }
                IrConstructor irConstructor10 = this.oldPrimaryConstructor;
                if (irConstructor10 != null) {
                    MfvcNodeKt.requireSameSizes(Integer.valueOf(irConstructor10.getValueParameters().size()), Integer.valueOf(subnodes.size()));
                }
                Integer[] numArr2 = new Integer[4];
                numArr2[0] = Integer.valueOf(getLeavesCount());
                IrConstructor irConstructor11 = this.newPrimaryConstructor;
                numArr2[1] = (irConstructor11 == null || (valueParameters2 = irConstructor11.getValueParameters()) == null) ? null : Integer.valueOf(valueParameters2.size());
                IrSimpleFunction irSimpleFunction6 = this.primaryConstructorImpl;
                numArr2[2] = (irSimpleFunction6 == null || (valueParameters = irSimpleFunction6.getValueParameters()) == null) ? null : Integer.valueOf(valueParameters.size());
                numArr2[3] = Integer.valueOf(getBoxMethod().getValueParameters().size());
                MfvcNodeKt.requireSameSizes(numArr2);
                if (this.specializedEqualsMethod.getValueParameters().size() != 1) {
                    throw new IllegalArgumentException(("Specialized equals method must contain single value parameter but has\n" + CollectionsKt.joinToString$default(this.specializedEqualsMethod.getValueParameters(), "\n", null, null, 0, null, new Function1<IrValueParameter, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.RootMfvcNode$8$1
                        @Override // kotlin.jvm.functions.Function1
                        public final CharSequence invoke(IrValueParameter it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                            return DumpIrTreeKt.dump$default(it2, null, 1, null);
                        }
                    }, 30, null)).toString());
                }
                for (IrFunction irFunction : CollectionsKt.listOfNotNull((Object[]) new IrFunction[]{this.oldPrimaryConstructor, this.newPrimaryConstructor, this.primaryConstructorImpl, getBoxMethod(), this.specializedEqualsMethod})) {
                    if (irFunction.getExtensionReceiverParameter() != null) {
                        throw new IllegalArgumentException(("Extension receiver is not expected for " + RenderIrElementKt.render$default(irFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    if (irFunction.getContextReceiverParametersCount() != 0) {
                        throw new IllegalArgumentException(("Context receivers are not expected for " + RenderIrElementKt.render$default(irFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                }
                return;
            }
        }
        throw new IllegalArgumentException("Constructors do not support type parameters yet".toString());
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNode
    public IrSimpleType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNode
    public int getLeavesCount() {
        return getLeaves().size();
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeWithSubnodes
    public List<IrSimpleFunction> getLeavesUnboxMethods() {
        return this.leavesUnboxMethods;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNodeWithSubnodes
    public List<IrSimpleFunction> getAllUnboxMethods() {
        return getAllInnerUnboxMethods();
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNode
    public ReceiverBasedMfvcNodeInstance createInstanceFromBox(IrBlockBuilder scope, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, IrExpression receiver, AccessType accessType, Function1<? super IrVariable, Unit> saveVariable) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(accessType, "accessType");
        Intrinsics.checkNotNullParameter(saveVariable, "saveVariable");
        return new ReceiverBasedMfvcNodeInstance(scope, this, typeArguments, receiver, getFields(), null, accessType, saveVariable);
    }

    public String toString() {
        return RenderIrElementKt.render$default(getType(), (DumpIrTreeOptions) null, 1, (Object) null) + '\n' + StringsKt.prependIndent(CollectionsKt.joinToString$default(getSubnodes(), "\n", null, null, 0, null, null, 62, null), "    ");
    }
}
