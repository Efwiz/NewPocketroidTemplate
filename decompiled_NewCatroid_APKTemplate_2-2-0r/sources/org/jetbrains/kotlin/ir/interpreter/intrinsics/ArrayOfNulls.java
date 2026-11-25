package org.jetbrains.kotlin.ir.interpreter.intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeBuilder;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: IntrinsicImplementations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/ArrayOfNulls;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/IntrinsicBase;", "()V", "evaluate", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getListOfAcceptableFunctions", "", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayOfNulls extends IntrinsicBase {
    public static final ArrayOfNulls INSTANCE = new ArrayOfNulls();

    private ArrayOfNulls() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<String> getListOfAcceptableFunctions() {
        return CollectionsKt.listOf("kotlin.arrayOfNulls");
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public void evaluate(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        Object[] objArr = new Object[StateKt.asInt(environment.getCallStack().loadState(((IrValueParameter) CollectionsKt.first((List) irFunction.getValueParameters())).getSymbol()))];
        List<IrTypeParameter> typeParameters = irFunction.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(environment.getCallStack().loadState(((IrTypeParameter) it.next()).getSymbol()));
        }
        Object objSingle = CollectionsKt.single((List<? extends Object>) arrayList);
        Intrinsics.checkNotNull(objSingle, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState");
        IrType returnType = irFunction.getReturnType();
        Intrinsics.checkNotNull(returnType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder((IrSimpleType) returnType);
        builder.setArguments(CollectionsKt.listOf(IrSimpleTypeImplKt.makeTypeProjection(((KTypeState) objSingle).getIrType(), Variance.INVARIANT)));
        environment.getCallStack().pushState(environment.convertToState$ir_interpreter(objArr, IrSimpleTypeImplKt.buildSimpleType(builder)));
    }
}
