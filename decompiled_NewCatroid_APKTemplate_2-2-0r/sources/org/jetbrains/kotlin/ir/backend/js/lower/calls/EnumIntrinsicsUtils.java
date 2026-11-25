package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: EnumIntrinsicsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J$\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tH\u0002J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/EnumIntrinsicsUtils;", "", "()V", "transformEnumEntriesIntrinsic", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "transformEnumTopLevelIntrinsic", "staticMethodPredicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "", "transformEnumValueOfIntrinsic", "transformEnumValuesIntrinsic", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumIntrinsicsUtils {
    public static final EnumIntrinsicsUtils INSTANCE = new EnumIntrinsicsUtils();

    private EnumIntrinsicsUtils() {
    }

    private final IrExpression transformEnumTopLevelIntrinsic(IrFunctionAccessExpression call, Function1<? super IrSimpleFunction, Boolean> staticMethodPredicate) {
        IrClass irClass;
        Object next;
        IrType typeArgument = call.getTypeArgument(0);
        if (typeArgument == null || (irClass = IrTypesKt.getClass(typeArgument)) == null) {
            return call;
        }
        if (!IrUtilsKt.isEnumClass(irClass)) {
            return call;
        }
        Iterator<T> it = irClass.getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrDeclaration irDeclaration = (IrDeclaration) next;
            if ((irDeclaration instanceof IrSimpleFunction) && staticMethodPredicate.invoke(irDeclaration).booleanValue()) {
                break;
            }
        }
        if (!(next instanceof IrSimpleFunction)) {
            next = null;
        }
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) next;
        if (irSimpleFunction == null || !IrDeclarationsKt.isStaticMethodOfClass(irSimpleFunction)) {
            CompilationExceptionKt.compilationException("Enum class should have static method for " + call.getSymbol().getOwner().getName(), call);
            throw null;
        }
        return IrUtilsKt.irCall(call, irSimpleFunction.getSymbol(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
    }

    public final IrExpression transformEnumValueOfIntrinsic(IrFunctionAccessExpression call) {
        Intrinsics.checkNotNullParameter(call, "call");
        return transformEnumTopLevelIntrinsic(call, new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.EnumIntrinsicsUtils.transformEnumValueOfIntrinsic.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                boolean z = false;
                if (Intrinsics.areEqual(it.getName(), Name.identifier("valueOf")) && it.getValueParameters().size() == 1 && IrTypePredicatesKt.isString(it.getValueParameters().get(0).getType())) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    public final IrExpression transformEnumValuesIntrinsic(IrFunctionAccessExpression call) {
        Intrinsics.checkNotNullParameter(call, "call");
        return transformEnumTopLevelIntrinsic(call, new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.EnumIntrinsicsUtils.transformEnumValuesIntrinsic.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getName(), Name.identifier(CompoundEntry.NAME_values)) && it.getValueParameters().size() == 0);
            }
        });
    }

    public final IrExpression transformEnumEntriesIntrinsic(IrFunctionAccessExpression call) {
        Intrinsics.checkNotNullParameter(call, "call");
        return transformEnumTopLevelIntrinsic(call, new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.EnumIntrinsicsUtils.transformEnumEntriesIntrinsic.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getName(), Name.special("<get-entries>")));
            }
        });
    }
}
