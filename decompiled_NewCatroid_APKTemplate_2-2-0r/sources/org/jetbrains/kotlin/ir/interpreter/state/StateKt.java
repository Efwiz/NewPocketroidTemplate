package org.jetbrains.kotlin.ir.interpreter.state;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.exceptions.ExceptionUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KFunctionState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.ReflectionState;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: State.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0000¢\u0006\u0002\u0010\u0004\u001a\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0000\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0002H\u0000\u001a\u000e\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\u0002H\u0000\u001a0\u0010\n\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0000\u001a\u0014\u0010\u0012\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0002H\u0000\u001a\f\u0010\u0014\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a\u0014\u0010\u0015\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\fH\u0000\u001a\u000e\u0010\u0016\u001a\u00020\u0001*\u0004\u0018\u00010\u0002H\u0000\u001a\u0016\u0010\u0017\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0000¨\u0006\u001a"}, d2 = {"asBoolean", "", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "asBooleanOrNull", "(Lorg/jetbrains/kotlin/ir/interpreter/state/State;)Ljava/lang/Boolean;", "asInt", "", "asString", "", "asStringOrNull", "checkNullability", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "exceptionToThrow", "Lkotlin/Function0;", "", "hasTheSameFieldsWith", "other", "isNull", "isSubtypeOf", "isUnit", "mustBeHandledAsReflection", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "ir.interpreter"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class StateKt {
    public static final boolean isNull(State state) {
        Intrinsics.checkNotNullParameter(state, "<this>");
        return (state instanceof Primitive) && ((Primitive) state).getValue() == null;
    }

    public static final boolean isUnit(State state) {
        return (state instanceof Common) && Intrinsics.areEqual(((Common) state).irClassFqName(), "kotlin.Unit");
    }

    public static final int asInt(State state) {
        Intrinsics.checkNotNullParameter(state, "<this>");
        Object value = ((Primitive) state).getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Int");
        return ((Integer) value).intValue();
    }

    public static final boolean asBoolean(State state) {
        Intrinsics.checkNotNullParameter(state, "<this>");
        Object value = ((Primitive) state).getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
        return ((Boolean) value).booleanValue();
    }

    public static final String asString(State state) {
        Intrinsics.checkNotNullParameter(state, "<this>");
        return String.valueOf(((Primitive) state).getValue());
    }

    public static final Boolean asBooleanOrNull(State state) {
        Intrinsics.checkNotNullParameter(state, "<this>");
        Primitive primitive = state instanceof Primitive ? (Primitive) state : null;
        Object value = primitive != null ? primitive.getValue() : null;
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return null;
    }

    public static final String asStringOrNull(State state) {
        Intrinsics.checkNotNullParameter(state, "<this>");
        Object value = ((Primitive) state).getValue();
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    public static final boolean isSubtypeOf(State state, IrType other) {
        IrClass owner;
        IrType funInterface;
        Intrinsics.checkNotNullParameter(state, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (isNull(state) && IrTypeUtilsKt.isNullable(other)) {
            return true;
        }
        boolean z = state instanceof Primitive;
        if (z && ((Primitive) state).getValue() == null) {
            return IrTypeUtilsKt.isNullable(other);
        }
        if (state instanceof ExceptionState) {
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(other);
            Intrinsics.checkNotNull(classOrNull);
            return ((ExceptionState) state).isSubtypeOf(classOrNull.getOwner());
        }
        if (z) {
            Primitive primitive = (Primitive) state;
            if ((IrTypePredicatesKt.isArray(primitive.getType()) || IrTypePredicatesKt.isNullableArray(primitive.getType())) && (IrTypePredicatesKt.isArray(other) || IrTypePredicatesKt.isNullableArray(other))) {
                return isSubtypeOf$arraySubtypeCheck(primitive.getType(), other);
            }
        }
        IrClassSymbol classOrNull2 = IrTypesKt.getClassOrNull(other);
        boolean z2 = false;
        if (classOrNull2 != null && (owner = classOrNull2.getOwner()) != null && owner.getIsFun()) {
            if ((state instanceof KFunctionState) && (funInterface = ((KFunctionState) state).getFunInterface()) != null) {
                IrClassSymbol classOrNull3 = IrTypesKt.getClassOrNull(other);
                Intrinsics.checkNotNull(classOrNull3);
                if (IrTypeUtilsKt.isSubtypeOfClass(funInterface, classOrNull3)) {
                    return true;
                }
            }
            return false;
        }
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(state.getIrClass());
        if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isFunction(other) && org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isKFunction(defaultType)) {
            List<IrDeclaration> declarations = state.getIrClass().getDeclarations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof IrSimpleFunction) {
                    arrayList.add(obj);
                }
            }
            Object obj2 = null;
            for (Object obj3 : arrayList) {
                if (Intrinsics.areEqual(((IrSimpleFunction) obj3).getName(), OperatorNameConventions.INVOKE)) {
                    if (z2) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    z2 = true;
                    obj2 = obj3;
                }
            }
            if (z2) {
                IrClass parentAsClass = IrUtilsKt.getParentAsClass(((IrSimpleFunctionSymbol) CollectionsKt.single((List) ((IrSimpleFunction) obj2).getOverriddenSymbols())).getOwner());
                IrClassSymbol classOrNull4 = IrTypesKt.getClassOrNull(other);
                Intrinsics.checkNotNull(classOrNull4);
                return IrUtilsKt.isSubclassOf(parentAsClass, classOrNull4.getOwner());
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        IrClassSymbol classOrNull5 = IrTypesKt.getClassOrNull(other);
        Intrinsics.checkNotNull(classOrNull5);
        return IrTypeUtilsKt.isSubtypeOfClass(defaultType, classOrNull5);
    }

    private static final boolean isSubtypeOf$arraySubtypeCheck(IrType irType, IrType irType2) {
        IrType typeOrNull;
        if (!(irType2 instanceof IrSimpleType) || !(irType instanceof IrSimpleType) || (typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) CollectionsKt.single((List) ((IrSimpleType) irType).getArguments()))) == null) {
            return false;
        }
        IrSimpleType irSimpleType = (IrSimpleType) irType2;
        IrType typeOrNull2 = IrTypesKt.getTypeOrNull((IrTypeArgument) CollectionsKt.single((List) irSimpleType.getArguments()));
        if (typeOrNull2 == null) {
            return CollectionsKt.single((List) irSimpleType.getArguments()) instanceof IrStarProjection;
        }
        if (IrTypePredicatesKt.isArray(typeOrNull) && IrTypePredicatesKt.isArray(typeOrNull2)) {
            return isSubtypeOf$arraySubtypeCheck(typeOrNull, typeOrNull2);
        }
        if (IrTypesKt.getClassOrNull(typeOrNull2) == null) {
            return true;
        }
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(typeOrNull);
        if (classOrNull == null) {
            return false;
        }
        IrClassSymbol classOrNull2 = IrTypesKt.getClassOrNull(typeOrNull2);
        Intrinsics.checkNotNull(classOrNull2);
        return IrTypeUtilsKt.isSubtypeOfClass(classOrNull, classOrNull2);
    }

    public static /* synthetic */ State checkNullability$default(State state, IrType irType, IrInterpreterEnvironment irInterpreterEnvironment, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            function0 = new Function0<NullPointerException>() { // from class: org.jetbrains.kotlin.ir.interpreter.state.StateKt.checkNullability.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final NullPointerException invoke() {
                    return new NullPointerException();
                }
            };
        }
        return checkNullability(state, irType, irInterpreterEnvironment, function0);
    }

    public static final State checkNullability(State state, IrType irType, IrInterpreterEnvironment environment, Function0<? extends Throwable> exceptionToThrow) {
        Intrinsics.checkNotNullParameter(state, "<this>");
        Intrinsics.checkNotNullParameter(environment, "environment");
        Intrinsics.checkNotNullParameter(exceptionToThrow, "exceptionToThrow");
        if (!(irType instanceof IrSimpleType) || !isNull(state) || IrTypeUtilsKt.isNullable(irType)) {
            return state;
        }
        ExceptionUtilsKt.handleUserException(exceptionToThrow.invoke(), environment);
        return null;
    }

    public static final boolean mustBeHandledAsReflection(State state, IrCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        IrSimpleFunction owner = call.getSymbol().getOwner();
        if (owner.getBody() == null && !Intrinsics.areEqual(owner.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE) && (state instanceof ReflectionState)) {
            return ((state instanceof KFunctionState) && KFunctionState.INSTANCE.isCallToInvokeOrMethodFromFunInterface(call)) ? false : true;
        }
        return false;
    }

    public static final boolean hasTheSameFieldsWith(State state, State other) {
        Intrinsics.checkNotNullParameter(state, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (state.getFields().size() != other.getFields().size()) {
            return false;
        }
        for (Pair pair : CollectionsKt.zip(state.getFields().values(), other.getFields().values())) {
            State state2 = (State) pair.component1();
            State state3 = (State) pair.component2();
            if ((state2 instanceof Primitive) && (state3 instanceof Primitive)) {
                if (!Intrinsics.areEqual(((Primitive) state2).getValue(), ((Primitive) state3).getValue())) {
                    return false;
                }
            } else if (state2 != state3) {
                return false;
            }
        }
        return true;
    }
}
