package org.jetbrains.kotlin.backend.jvm.codegen;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: PromisedValue.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001e\u0010\u0007\u001a\u00020\b*\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\b0\u000b\u001a\n\u0010\r\u001a\u00020\u000e*\u00020\u0001\u001a\n\u0010\u000f\u001a\u00020\u0010*\u00020\u0001\u001a\u0012\u0010\u0011\u001a\u00020\u0010*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\t\u001a\u001a\u0010\u0011\u001a\u00020\u0010*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\t\u001a\u0012\u0010\u0015\u001a\u00020\u0010*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\t\u001a\n\u0010\u0016\u001a\u00020\u0017*\u00020\u0001\u001a\u0012\u0010\u0018\u001a\u00020\u0017*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\t\u001a$\u0010\u0018\u001a\u00020\u0017*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\t2\b\b\u0002\u0010\u0019\u001a\u00020\b\u001a\u0012\u0010\u001a\u001a\u00020\u0017*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\t\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u001b"}, d2 = {"nullConstant", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "getNullConstant", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;)Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "unitValue", "getUnitValue", "anyTypeArgument", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "check", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "coerceToBoolean", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;", "materialize", "", "materializeAt", "irTarget", "target", "Lorg/jetbrains/org/objectweb/asm/Type;", "materializeAtBoxed", "materialized", "Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "materializedAt", "castForReified", "materializedAtBoxed", "backend.jvm.codegen"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PromisedValueKt {
    public static final boolean anyTypeArgument(IrType irType, Function1<? super IrTypeParameter, Boolean> check) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(check, "check");
        if (IrTypeUtilsKt.isTypeParameter(irType)) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
            Intrinsics.checkNotNull(classifierOrNull, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol");
            if (check.invoke(((IrTypeParameterSymbol) classifierOrNull).getOwner()).booleanValue()) {
                return true;
            }
        } else if (irType instanceof IrSimpleType) {
            List<IrTypeArgument> arguments = ((IrSimpleType) irType).getArguments();
            if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    IrType typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) it.next());
                    if (typeOrNull != null ? anyTypeArgument(typeOrNull, check) : false) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public static final BooleanValue coerceToBoolean(final PromisedValue promisedValue) {
        Intrinsics.checkNotNullParameter(promisedValue, "<this>");
        return promisedValue instanceof BooleanValue ? (BooleanValue) promisedValue : new BooleanValue(promisedValue.getCodegen()) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt.coerceToBoolean.1
            @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
            public void jumpIfFalse(Label target) {
                Intrinsics.checkNotNullParameter(target, "target");
                PromisedValue promisedValue2 = promisedValue;
                Type BOOLEAN_TYPE = Type.BOOLEAN_TYPE;
                Intrinsics.checkNotNullExpressionValue(BOOLEAN_TYPE, "BOOLEAN_TYPE");
                promisedValue2.materializeAt(BOOLEAN_TYPE, getCodegen().getContext().getIrBuiltIns().getBooleanType(), false);
                Unit unit = Unit.INSTANCE;
                getMv().ifeq(target);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
            public void jumpIfTrue(Label target) {
                Intrinsics.checkNotNullParameter(target, "target");
                PromisedValue promisedValue2 = promisedValue;
                Type BOOLEAN_TYPE = Type.BOOLEAN_TYPE;
                Intrinsics.checkNotNullExpressionValue(BOOLEAN_TYPE, "BOOLEAN_TYPE");
                promisedValue2.materializeAt(BOOLEAN_TYPE, getCodegen().getContext().getIrBuiltIns().getBooleanType(), false);
                Unit unit = Unit.INSTANCE;
                getMv().ifne(target);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
            public void discard() {
                promisedValue.discard();
            }
        };
    }

    public static final void materializeAt(PromisedValue promisedValue, Type target, IrType irTarget) {
        Intrinsics.checkNotNullParameter(promisedValue, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(irTarget, "irTarget");
        promisedValue.materializeAt(target, irTarget, false);
    }

    public static /* synthetic */ MaterialValue materializedAt$default(PromisedValue promisedValue, Type type, IrType irType, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return materializedAt(promisedValue, type, irType, z);
    }

    public static final MaterialValue materializedAt(PromisedValue promisedValue, Type target, IrType irTarget, boolean z) {
        Intrinsics.checkNotNullParameter(promisedValue, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(irTarget, "irTarget");
        promisedValue.materializeAt(target, irTarget, z);
        return new MaterialValue(promisedValue.getCodegen(), target, irTarget);
    }

    public static final MaterialValue materialized(PromisedValue promisedValue) {
        Intrinsics.checkNotNullParameter(promisedValue, "<this>");
        return materializedAt$default(promisedValue, promisedValue.getType(), promisedValue.getIrType(), false, 4, null);
    }

    public static final MaterialValue materializedAt(PromisedValue promisedValue, IrType irTarget) {
        Intrinsics.checkNotNullParameter(promisedValue, "<this>");
        Intrinsics.checkNotNullParameter(irTarget, "irTarget");
        return materializedAt$default(promisedValue, IrTypeMapper.mapType$default(promisedValue.getTypeMapper(), irTarget, null, null, false, 14, null), irTarget, false, 4, null);
    }

    public static final MaterialValue materializedAtBoxed(PromisedValue promisedValue, IrType irTarget) {
        Intrinsics.checkNotNullParameter(promisedValue, "<this>");
        Intrinsics.checkNotNullParameter(irTarget, "irTarget");
        return materializedAt$default(promisedValue, promisedValue.getTypeMapper().boxType(irTarget), irTarget, false, 4, null);
    }

    public static final void materialize(PromisedValue promisedValue) {
        Intrinsics.checkNotNullParameter(promisedValue, "<this>");
        materializeAt(promisedValue, promisedValue.getType(), promisedValue.getIrType());
    }

    public static final void materializeAt(PromisedValue promisedValue, IrType irTarget) {
        Intrinsics.checkNotNullParameter(promisedValue, "<this>");
        Intrinsics.checkNotNullParameter(irTarget, "irTarget");
        materializeAt(promisedValue, IrTypeMapper.mapType$default(promisedValue.getTypeMapper(), irTarget, null, null, false, 14, null), irTarget);
    }

    public static final void materializeAtBoxed(PromisedValue promisedValue, IrType irTarget) {
        Intrinsics.checkNotNullParameter(promisedValue, "<this>");
        Intrinsics.checkNotNullParameter(irTarget, "irTarget");
        materializeAt(promisedValue, promisedValue.getTypeMapper().boxType(irTarget), irTarget);
    }

    public static final PromisedValue getUnitValue(ExpressionCodegen expressionCodegen) {
        Intrinsics.checkNotNullParameter(expressionCodegen, "<this>");
        Type VOID_TYPE = Type.VOID_TYPE;
        Intrinsics.checkNotNullExpressionValue(VOID_TYPE, "VOID_TYPE");
        return new MaterialValue(expressionCodegen, VOID_TYPE, expressionCodegen.getContext().getIrBuiltIns().getUnitType());
    }

    public static final PromisedValue getNullConstant(final ExpressionCodegen expressionCodegen) {
        Intrinsics.checkNotNullParameter(expressionCodegen, "<this>");
        final Type type = AsmTypes.OBJECT_TYPE;
        final IrType nothingNType = expressionCodegen.getContext().getIrBuiltIns().getNothingNType();
        return new PromisedValue(expressionCodegen, type, nothingNType) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt$nullConstant$1
            @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
            public void discard() {
            }

            {
                Intrinsics.checkNotNull(type);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
            public void materializeAt(Type target, IrType irTarget, boolean castForReified) {
                Intrinsics.checkNotNullParameter(target, "target");
                Intrinsics.checkNotNullParameter(irTarget, "irTarget");
                getMv().aconst(null);
            }
        };
    }
}
