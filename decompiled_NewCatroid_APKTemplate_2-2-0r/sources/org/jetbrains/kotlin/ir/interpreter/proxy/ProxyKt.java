package org.jetbrains.kotlin.ir.interpreter.proxy;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.ReflectionProxy;
import org.jetbrains.kotlin.ir.interpreter.state.Common;
import org.jetbrains.kotlin.ir.interpreter.state.ExceptionState;
import org.jetbrains.kotlin.ir.interpreter.state.Primitive;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.interpreter.state.Wrapper;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.ReflectionState;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: Proxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0003\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\u0005H\u0000\u001a6\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007*\b\u0012\u0004\u0012\u00020\t0\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0000\u001a.\u0010\u0006\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00042\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0005H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"checkNotNullName", "", "eqeqName", "isObject", "", "Ljava/lang/Class;", "wrap", "", "", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "methodType", "Ljava/lang/invoke/MethodType;", "remainArraysAsIs", "extendFrom", "ir.interpreter"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProxyKt {
    private static final String checkNotNullName;
    private static final String eqeqName;

    public static /* synthetic */ Object wrap$default(State state, CallInterceptor callInterceptor, boolean z, Class cls, int i, Object obj) {
        if ((i & 4) != 0) {
            cls = null;
        }
        return wrap(state, callInterceptor, z, (Class<?>) cls);
    }

    public static final Object wrap(State state, CallInterceptor callInterceptor, boolean z, Class<?> cls) {
        Intrinsics.checkNotNullParameter(state, "<this>");
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        if (state instanceof ExceptionState) {
            return state;
        }
        if (state instanceof Wrapper) {
            return ((Wrapper) state).getValue();
        }
        if (state instanceof Primitive) {
            if (StateKt.isNull(state)) {
                return null;
            }
            Primitive primitive = (Primitive) state;
            if (IrTypePredicatesKt.isArray(primitive.getType()) || UtilsKt.isPrimitiveArray(primitive.getType())) {
                return z ? state : primitive.getValue();
            }
            return primitive.getValue();
        }
        if (state instanceof Common) {
            return CommonProxy.INSTANCE.asProxy$ir_interpreter((Common) state, callInterceptor, cls);
        }
        if (state instanceof ReflectionState) {
            return ReflectionProxy.INSTANCE.asProxy$ir_interpreter((ReflectionState) state, callInterceptor);
        }
        throw new AssertionError(Reflection.getOrCreateKotlinClass(state.getClass()) + " is unsupported as argument for wrap function");
    }

    static {
        String strAsString = IrBuiltIns.INSTANCE.getKOTLIN_INTERNAL_IR_FQN().child(Name.identifier(BuiltInOperatorNames.EQEQ)).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        eqeqName = strAsString;
        String strAsString2 = IrBuiltIns.INSTANCE.getKOTLIN_INTERNAL_IR_FQN().child(Name.identifier(BuiltInOperatorNames.CHECK_NOT_NULL)).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        checkNotNullName = strAsString2;
    }

    public static /* synthetic */ List wrap$default(List list, CallInterceptor callInterceptor, IrFunction irFunction, MethodType methodType, int i, Object obj) {
        if ((i & 4) != 0) {
            methodType = null;
        }
        return wrap((List<? extends State>) list, callInterceptor, irFunction, methodType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v12, types: [org.jetbrains.kotlin.ir.interpreter.state.State] */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v14, types: [java.lang.Object] */
    public static final List<Object> wrap(List<? extends State> list, CallInterceptor callInterceptor, IrFunction irFunction, MethodType methodType) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        String fqName = UtilsKt.getFqName(irFunction);
        int i = 0;
        if (Intrinsics.areEqual(fqName, eqeqName)) {
            List<? extends State> list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    if (((State) it.next()) instanceof Common) {
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                        for (Object obj : list2) {
                            int i2 = i + 1;
                            if (i < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }
                            ?? Wrap = (State) obj;
                            if (Wrap instanceof Common) {
                                Wrap = wrap((State) Wrap, callInterceptor, true, methodType != null ? methodType.parameterType(i) : null);
                            }
                            arrayList.add(Wrap);
                            i = i2;
                        }
                        return arrayList;
                    }
                }
            }
        }
        List<? extends State> list3 = list;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        int i3 = 0;
        for (Object obj2 : list3) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList2.add(wrap((State) obj2, callInterceptor, (Intrinsics.areEqual(fqName, "kotlin.Array.set") && i3 != 0) || Intrinsics.areEqual(fqName, "kotlin.Pair.<init>") || Intrinsics.areEqual(fqName, checkNotNullName), methodType != null ? methodType.parameterType(i3) : null));
            i3 = i4;
        }
        return arrayList2;
    }

    public static final boolean isObject(Class<?> cls) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        return Intrinsics.areEqual(cls, Object.class);
    }
}
