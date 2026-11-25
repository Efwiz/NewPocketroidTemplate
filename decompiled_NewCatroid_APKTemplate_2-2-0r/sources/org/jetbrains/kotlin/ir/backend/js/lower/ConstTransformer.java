package org.jetbrains.kotlin.ir.backend.js.lower;

import com.android.dx.rop.code.RegisterSpec;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.ir.backend.js.JsIntrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: ConstLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\nH\u0002Ja\u0010\u000b\u001a\u00020\u0006\"\u0004\b\u0000\u0010\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\r\u001a\u00020\u000e2$\u0010\u000f\u001a \u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\u00060\u00102\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\f0\u0014\"\u0002H\fH\u0002¢\u0006\u0002\u0010\u0015J\u0014\u0010\u0016\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ConstTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "createLong", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", RegisterSpec.PREFIX, "", "lowerConst", "C", "irClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "carrierFactory", "Lkotlin/Function4;", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "args", "", "(Lorg/jetbrains/kotlin/ir/expressions/IrConst;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Lkotlin/jvm/functions/Function4;[Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitConst", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstTransformer extends IrElementTransformerVoid {
    private final JsIrBackendContext context;

    public ConstTransformer(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    private final <C> IrExpression lowerConst(IrConst<?> expression, IrClassSymbol irClass, Function4<? super Integer, ? super Integer, ? super IrType, ? super C, ? extends IrExpression> carrierFactory, C... args) {
        IrConstructorSymbol irConstructorSymbol = null;
        boolean z = false;
        for (IrConstructorSymbol irConstructorSymbol2 : IrUtilsKt.getConstructors(irClass)) {
            if (irConstructorSymbol2.getOwner().getIsPrimary()) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irConstructorSymbol = irConstructorSymbol2;
            }
        }
        if (z) {
            IrConstructorSymbol irConstructorSymbol3 = irConstructorSymbol;
            IrType type = ((IrValueParameter) CollectionsKt.first((List) irConstructorSymbol3.getOwner().getValueParameters())).getType();
            IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, expression.getStartOffset(), expression.getEndOffset(), IrTypesKt.getDefaultType(irClass), irConstructorSymbol3, null, 16, null);
            int length = args.length;
            for (int i = 0; i < length; i++) {
                irConstructorCallImplFromSymbolOwner$default.putValueArgument(i, carrierFactory.invoke(Integer.valueOf(irConstructorCallImplFromSymbolOwner$default.getStartOffset()), Integer.valueOf(irConstructorCallImplFromSymbolOwner$default.getEndOffset()), type, (Object) args[i]));
            }
            return irConstructorCallImplFromSymbolOwner$default;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ConstTransformer$createLong$1, reason: invalid class name */
    /* JADX INFO: compiled from: ConstLowering.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function4<Integer, Integer, IrType, Integer, IrConstImpl<Integer>> {
        AnonymousClass1(Object obj) {
            super(4, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "int(IILorg/jetbrains/kotlin/ir/types/IrType;I)Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "int";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IrConstImpl.Companion.class);
        }

        @Override // kotlin.jvm.functions.Function4
        public /* bridge */ /* synthetic */ IrConstImpl<Integer> invoke(Integer num, Integer num2, IrType irType, Integer num3) {
            return invoke(num.intValue(), num2.intValue(), irType, num3.intValue());
        }

        public final IrConstImpl<Integer> invoke(int i, int i2, IrType p2, int i3) {
            Intrinsics.checkNotNullParameter(p2, "p2");
            return ((IrConstImpl.Companion) this.receiver).m7211int(i, i2, p2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression createLong(IrConst<?> expression, long v) {
        return lowerConst(expression, this.context.getIntrinsics().getLongClassSymbol(), new AnonymousClass1(IrConstImpl.INSTANCE), Integer.valueOf((int) v), Integer.valueOf((int) (v >> 32)));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitConst(final IrConst<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        JsIntrinsics intrinsics = this.context.getIntrinsics();
        if (IrTypeUtilsKt.isUnsigned(expression.getType()) && !Intrinsics.areEqual(expression.getKind(), IrConstKind.Null.INSTANCE)) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(expression.getType());
            if (Intrinsics.areEqual(classifierOrNull, intrinsics.getUByteClassSymbol())) {
                return lowerConst(expression, intrinsics.getUByteClassSymbol(), new ConstTransformer$visitConst$1$1(IrConstImpl.INSTANCE), IrConstKind.Byte.INSTANCE.valueOf(expression));
            }
            if (Intrinsics.areEqual(classifierOrNull, intrinsics.getUShortClassSymbol())) {
                return lowerConst(expression, intrinsics.getUShortClassSymbol(), new ConstTransformer$visitConst$1$2(IrConstImpl.INSTANCE), IrConstKind.Short.INSTANCE.valueOf(expression));
            }
            if (Intrinsics.areEqual(classifierOrNull, intrinsics.getUIntClassSymbol())) {
                return lowerConst(expression, intrinsics.getUIntClassSymbol(), new ConstTransformer$visitConst$1$3(IrConstImpl.INSTANCE), IrConstKind.Int.INSTANCE.valueOf(expression));
            }
            if (Intrinsics.areEqual(classifierOrNull, intrinsics.getULongClassSymbol())) {
                return lowerConst(expression, intrinsics.getULongClassSymbol(), new Function4<Integer, Integer, IrType, Long, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ConstTransformer$visitConst$1$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ IrExpression invoke(Integer num, Integer num2, IrType irType, Long l) {
                        return invoke(num.intValue(), num2.intValue(), irType, l.longValue());
                    }

                    public final IrExpression invoke(int i, int i2, IrType irType, long j) {
                        Intrinsics.checkNotNullParameter(irType, "<anonymous parameter 2>");
                        return this.this$0.createLong(expression, j);
                    }
                }, IrConstKind.Long.INSTANCE.valueOf(expression));
            }
            CompilationExceptionKt.compilationException("Unknown unsigned type", expression);
            throw null;
        }
        if (expression.getKind() instanceof IrConstKind.Char) {
            return lowerConst(expression, intrinsics.getCharClassSymbol(), new ConstTransformer$visitConst$1$5(IrConstImpl.INSTANCE), Integer.valueOf(IrConstKind.Char.INSTANCE.valueOf(expression).charValue()));
        }
        if (expression.getKind() instanceof IrConstKind.Long) {
            return createLong(expression, IrConstKind.Long.INSTANCE.valueOf(expression).longValue());
        }
        return super.visitConst(expression);
    }
}
