package org.jetbrains.kotlin.ir.interpreter.state.reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KTypeProjection;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KClassProxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KTypeParameterProxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KTypeProxy;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: KTypeState.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0096\u0002J\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0015\u001a\u00020\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u000e\u0010\u001c\u001a\u0004\u0018\u00010\u001d*\u00020\u001eH\u0002R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KTypeState;", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/ReflectionState;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "_arguments", "", "Lkotlin/reflect/KTypeProjection;", "_classifier", "Lkotlin/reflect/KClassifier;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIrType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getArguments", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "getClassifier", "hashCode", "", "toString", "", "getVariance", "Lorg/jetbrains/kotlin/types/Variance;", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KTypeState extends ReflectionState {
    private List<KTypeProjection> _arguments;
    private KClassifier _classifier;
    private final IrClass irClass;
    private final IrType irType;

    /* JADX INFO: compiled from: KTypeState.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Variance.OUT_VARIANCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public KTypeState(IrType irType, IrClass irClass) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        this.irType = irType;
        this.irClass = irClass;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrClass getIrClass() {
        return this.irClass;
    }

    public final IrType getIrType() {
        return this.irType;
    }

    public final KClassifier getClassifier(CallInterceptor callInterceptor) {
        KTypeParameterProxy kTypeParameterProxy;
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        KClassifier kClassifier = this._classifier;
        if (kClassifier != null) {
            Intrinsics.checkNotNull(kClassifier);
            return kClassifier;
        }
        IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(this.irType).getOwner();
        if (owner instanceof IrClass) {
            kTypeParameterProxy = new KClassProxy(new KClassState((IrClass) owner, callInterceptor.getIrBuiltIns().getKClassClass().getOwner()), callInterceptor);
        } else if (owner instanceof IrTypeParameter) {
            kTypeParameterProxy = new KTypeParameterProxy(new KTypeParameterState((IrTypeParameter) owner, callInterceptor.getEnvironment().getKTypeParameterClass$ir_interpreter().getOwner()), callInterceptor);
        } else {
            throw new NotImplementedError(null, 1, null);
        }
        this._classifier = kTypeParameterProxy;
        Intrinsics.checkNotNull(kTypeParameterProxy);
        return kTypeParameterProxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final List<KTypeProjection> getArguments(CallInterceptor callInterceptor) {
        KTypeProjection star;
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        List<KTypeProjection> list = this._arguments;
        if (list != null) {
            Intrinsics.checkNotNull(list);
            return list;
        }
        Map<Class<?>, IrClass> javaClassToIrClass$ir_interpreter = callInterceptor.getEnvironment().getJavaClassToIrClass$ir_interpreter();
        Pair pair = TuplesKt.to(KTypeProjection.class, callInterceptor.getEnvironment().getKTypeProjectionClass$ir_interpreter().getOwner());
        javaClassToIrClass$ir_interpreter.put(pair.getFirst(), pair.getSecond());
        IrType irType = this.irType;
        Intrinsics.checkNotNull(irType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        List<IrTypeArgument> arguments = ((IrSimpleType) irType).getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        for (IrTypeArgument irTypeArgument : arguments) {
            Variance variance = getVariance(irTypeArgument);
            int i = variance == null ? -1 : WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
            if (i == -1) {
                star = KTypeProjection.INSTANCE.getSTAR();
            } else if (i == 1) {
                KTypeProjection.Companion companion = KTypeProjection.INSTANCE;
                IrType typeOrNull = IrTypesKt.getTypeOrNull(irTypeArgument);
                Intrinsics.checkNotNull(typeOrNull);
                star = companion.invariant(new KTypeProxy(new KTypeState(typeOrNull, getIrClass()), callInterceptor));
            } else if (i == 2) {
                KTypeProjection.Companion companion2 = KTypeProjection.INSTANCE;
                IrType typeOrNull2 = IrTypesKt.getTypeOrNull(irTypeArgument);
                Intrinsics.checkNotNull(typeOrNull2);
                star = companion2.contravariant(new KTypeProxy(new KTypeState(typeOrNull2, getIrClass()), callInterceptor));
            } else if (i == 3) {
                KTypeProjection.Companion companion3 = KTypeProjection.INSTANCE;
                IrType typeOrNull3 = IrTypesKt.getTypeOrNull(irTypeArgument);
                Intrinsics.checkNotNull(typeOrNull3);
                star = companion3.covariant(new KTypeProxy(new KTypeState(typeOrNull3, getIrClass()), callInterceptor));
            } else {
                throw new NoWhenBranchMatchedException();
            }
            arrayList.add(star);
        }
        ArrayList arrayList2 = arrayList;
        this._arguments = arrayList2;
        Intrinsics.checkNotNull(arrayList2);
        return arrayList2;
    }

    private final Variance getVariance(IrTypeArgument irTypeArgument) {
        if (irTypeArgument instanceof IrSimpleType) {
            return Variance.INVARIANT;
        }
        if (irTypeArgument instanceof IrTypeProjection) {
            return ((IrTypeProjection) irTypeArgument).getVariance();
        }
        if (irTypeArgument instanceof IrStarProjection) {
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState");
        return Intrinsics.areEqual(this.irType, ((KTypeState) other).irType);
    }

    public int hashCode() {
        return this.irType.hashCode();
    }

    public String toString() {
        return renderType(this.irType);
    }
}
