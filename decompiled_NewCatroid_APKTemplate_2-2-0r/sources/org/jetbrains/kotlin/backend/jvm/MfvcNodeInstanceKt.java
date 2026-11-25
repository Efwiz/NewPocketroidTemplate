package org.jetbrains.kotlin.backend.jvm;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MfvcNodeInstance.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000~\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0016\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u0002`\rH\u0002\u001a \u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u001a\u001a\u0010\u0015\u001a\u00020\u000f*\u00020\u00022\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002\u001a\f\u0010\u0016\u001a\u00020\u0017*\u0004\u0018\u00010\u0014\u001a\f\u0010\u0018\u001a\u00020\u0017*\u0004\u0018\u00010\u0014\u001a\f\u0010\u0019\u001a\u00020\u0017*\u0004\u0018\u00010\u0014\u001a \u0010\u001a\u001a\u00020\u0014*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u001b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u001aL\u0010\u001c\u001a\u00020\u001d*\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\f2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020#2\b\b\u0002\u0010$\u001a\u00020\u00172\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u000f0&\u001a^\u0010'\u001a\u00020\u001d\"\b\b\u0000\u0010(*\u00020)*\b\u0012\u0004\u0012\u0002H(0*2\u0006\u0010+\u001a\u00020\u00142\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\b\b\u0002\u0010,\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020#2\b\b\u0002\u0010$\u001a\u00020\u00172\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u000f0&\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006-"}, d2 = {"size", "", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;", "getSize", "(Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeInstance;)I", "makeTypeFromMfvcNodeAndTypeArguments", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "node", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/backend/jvm/TypeArguments;", "addSetterStatements", "", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", CompoundEntry.NAME_values, "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "checkValuesCount", "isRepeatableAccessor", "", "isRepeatableGetter", "isRepeatableSetter", "makeSetterExpressions", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "savableStandaloneVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", ModuleXmlParser.TYPE, "name", "", "isVar", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "isTemporary", "saveVariable", "Lkotlin/Function1;", "savableStandaloneVariableWithSetter", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/builders/IrStatementsBuilder;", "expression", "isMutable", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MfvcNodeInstanceKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleType makeTypeFromMfvcNodeAndTypeArguments(MfvcNode mfvcNode, Map<IrTypeParameterSymbol, ? extends IrType> map) {
        IrType irTypeSubstitute = IrTypeUtilsKt.substitute(mfvcNode.getType(), map);
        Intrinsics.checkNotNull(irTypeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        return (IrSimpleType) irTypeSubstitute;
    }

    public static final void addSetterStatements(MfvcNodeInstance mfvcNodeInstance, IrBlockBuilder scope, List<? extends IrExpression> values) {
        Intrinsics.checkNotNullParameter(mfvcNodeInstance, "<this>");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(values, "values");
        Iterator<IrStatement> it = mfvcNodeInstance.makeSetterStatements(scope, values).iterator();
        while (it.hasNext()) {
            scope.unaryPlus(it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkValuesCount(MfvcNodeInstance mfvcNodeInstance, List<? extends IrExpression> list) {
        if (list.size() == mfvcNodeInstance.getNode().getLeavesCount()) {
            return;
        }
        StringBuilder sbAppend = new StringBuilder("Node ").append(mfvcNodeInstance.getNode()).append(" requires ").append(mfvcNodeInstance.getNode().getLeavesCount()).append(" values but got ");
        List<? extends IrExpression> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(RenderIrElementKt.render$default((IrExpression) it.next(), (DumpIrTreeOptions) null, 1, (Object) null));
        }
        throw new IllegalArgumentException(sbAppend.append(arrayList).toString().toString());
    }

    public static final boolean isRepeatableGetter(IrExpression irExpression) {
        if (irExpression == null || (irExpression instanceof IrConst) || (irExpression instanceof IrGetValue)) {
            return true;
        }
        if (irExpression instanceof IrGetField) {
            return isRepeatableGetter(((IrGetField) irExpression).getReceiver());
        }
        if (irExpression instanceof IrTypeOperatorCallImpl) {
            return isRepeatableGetter(((IrTypeOperatorCallImpl) irExpression).getArgument());
        }
        if (irExpression instanceof IrContainerExpression) {
            List<IrStatement> statements = ((IrContainerExpression) irExpression).getStatements();
            if ((statements instanceof Collection) && statements.isEmpty()) {
                return true;
            }
            for (IrStatement irStatement : statements) {
                if (!(irStatement instanceof IrExpression) || !isRepeatableGetter((IrExpression) irStatement)) {
                    if (irStatement instanceof IrVariable) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:41:0x0096  */
    /* JADX WARN: Code duplicated, block: B:42:0x0098  */
    /* JADX WARN: Code duplicated, block: B:47:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:59:? A[RETURN, SYNTHETIC] */
    public static final boolean isRepeatableSetter(IrExpression irExpression) {
        IrStatement irStatement;
        boolean z;
        boolean z2;
        if (irExpression == null || (irExpression instanceof IrConst)) {
            return true;
        }
        if (irExpression instanceof IrSetValue) {
            return isRepeatableGetter(((IrSetValue) irExpression).getValue());
        }
        if (irExpression instanceof IrSetField) {
            IrSetField irSetField = (IrSetField) irExpression;
            if (isRepeatableGetter(irSetField.getReceiver()) && isRepeatableGetter(irSetField.getValue())) {
                return true;
            }
        } else {
            if (irExpression instanceof IrTypeOperatorCallImpl) {
                return isRepeatableSetter(((IrTypeOperatorCallImpl) irExpression).getArgument());
            }
            if (irExpression instanceof IrContainerExpression) {
                IrContainerExpression irContainerExpression = (IrContainerExpression) irExpression;
                List<IrStatement> listDropLast = CollectionsKt.dropLast(irContainerExpression.getStatements(), 1);
                if ((listDropLast instanceof Collection) && listDropLast.isEmpty()) {
                    irStatement = (IrStatement) CollectionsKt.lastOrNull((List) irContainerExpression.getStatements());
                    if (irStatement == null) {
                        z = true;
                    } else {
                        z = irStatement instanceof IrExpression;
                    }
                    if (z) {
                        z2 = false;
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        return true;
                    }
                } else {
                    for (IrStatement irStatement2 : listDropLast) {
                        if (!(irStatement2 instanceof IrExpression) || !isRepeatableGetter((IrExpression) irStatement2)) {
                            if (irStatement2 instanceof IrVariable) {
                            }
                        }
                    }
                    irStatement = (IrStatement) CollectionsKt.lastOrNull((List) irContainerExpression.getStatements());
                    if (irStatement == null) {
                        z = true;
                    } else {
                        z = irStatement instanceof IrExpression;
                    }
                    if (z || !isRepeatableSetter((IrExpression) irStatement)) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (z2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final boolean isRepeatableAccessor(IrExpression irExpression) {
        return isRepeatableGetter(irExpression) || isRepeatableSetter(irExpression);
    }

    public static final int getSize(MfvcNodeInstance mfvcNodeInstance) {
        Intrinsics.checkNotNullParameter(mfvcNodeInstance, "<this>");
        return mfvcNodeInstance.getNode().getLeavesCount();
    }

    public static /* synthetic */ IrVariable savableStandaloneVariable$default(IrBuilderWithScope irBuilderWithScope, IrType irType, String str, boolean z, IrDeclarationOrigin irDeclarationOrigin, boolean z2, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        String str2 = str;
        if ((i & 16) != 0) {
            z2 = Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE) || Intrinsics.areEqual(irDeclarationOrigin, JvmLoweredDeclarationOrigin.TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE.INSTANCE) || Intrinsics.areEqual(irDeclarationOrigin, JvmLoweredDeclarationOrigin.TEMPORARY_MULTI_FIELD_VALUE_CLASS_PARAMETER.INSTANCE);
        }
        return savableStandaloneVariable(irBuilderWithScope, irType, str2, z, irDeclarationOrigin, z2, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrVariable savableStandaloneVariable(IrBuilderWithScope irBuilderWithScope, IrType type, String str, boolean z, IrDeclarationOrigin origin, boolean z2, Function1<? super IrVariable, Unit> saveVariable) {
        IrVariableImpl irVariableImplCreateTemporaryVariableDeclaration;
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(saveVariable, "saveVariable");
        if (z2 || str == null) {
            irVariableImplCreateTemporaryVariableDeclaration = irBuilderWithScope.getScope().createTemporaryVariableDeclaration(type, str, z, origin, irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset());
        } else {
            int startOffset = irBuilderWithScope.getStartOffset();
            int endOffset = irBuilderWithScope.getEndOffset();
            IrVariableSymbolImpl irVariableSymbolImpl = new IrVariableSymbolImpl(null, 1, 0 == true ? 1 : 0);
            Name nameIdentifier = Name.identifier(str);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            IrVariableImpl irVariableImpl = new IrVariableImpl(startOffset, endOffset, origin, irVariableSymbolImpl, nameIdentifier, type, z, false, false);
            irVariableImpl.setParent(irBuilderWithScope.getScope().getLocalDeclarationParent());
            irVariableImplCreateTemporaryVariableDeclaration = irVariableImpl;
        }
        saveVariable.invoke(irVariableImplCreateTemporaryVariableDeclaration);
        return irVariableImplCreateTemporaryVariableDeclaration;
    }

    public static /* synthetic */ IrVariable savableStandaloneVariableWithSetter$default(IrStatementsBuilder irStatementsBuilder, IrExpression irExpression, String str, boolean z, IrDeclarationOrigin irDeclarationOrigin, boolean z2, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        String str2 = str;
        if ((i & 4) != 0) {
            z = false;
        }
        boolean z3 = z;
        if ((i & 16) != 0) {
            z2 = Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE);
        }
        return savableStandaloneVariableWithSetter(irStatementsBuilder, irExpression, str2, z3, irDeclarationOrigin, z2, function1);
    }

    public static final <T extends IrElement> IrVariable savableStandaloneVariableWithSetter(IrStatementsBuilder<? extends T> irStatementsBuilder, IrExpression expression, String str, boolean z, IrDeclarationOrigin origin, boolean z2, Function1<? super IrVariable, Unit> saveVariable) {
        Intrinsics.checkNotNullParameter(irStatementsBuilder, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(saveVariable, "saveVariable");
        IrStatementsBuilder<? extends T> irStatementsBuilder2 = irStatementsBuilder;
        IrVariable irVariableSavableStandaloneVariable = savableStandaloneVariable(irStatementsBuilder2, expression.getType(), str, z, origin, z2, saveVariable);
        irStatementsBuilder.unaryPlus(ExpressionHelpersKt.irSet$default(irStatementsBuilder2, irVariableSavableStandaloneVariable, expression, (IrStatementOrigin) null, 4, (Object) null));
        return irVariableSavableStandaloneVariable;
    }

    public static final IrExpression makeSetterExpressions(MfvcNodeInstance mfvcNodeInstance, IrBuilderWithScope scope, List<? extends IrExpression> values) {
        Intrinsics.checkNotNullParameter(mfvcNodeInstance, "<this>");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(values, "values");
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(scope.getContext(), scope.getScope(), scope.getStartOffset(), scope.getEndOffset(), null, null, false, 64, null);
        addSetterStatements(mfvcNodeInstance, irBlockBuilder, values);
        return irBlockBuilder.getIrBlockBody();
    }
}
