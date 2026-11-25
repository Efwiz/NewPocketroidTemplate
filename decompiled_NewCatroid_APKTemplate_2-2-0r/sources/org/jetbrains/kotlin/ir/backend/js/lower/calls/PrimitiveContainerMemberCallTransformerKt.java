package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;

/* JADX INFO: compiled from: PrimitiveContainerMemberCallTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0005\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0018\u0010\u0007\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004\"\u0018\u0010\t\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0004\"\u0018\u0010\u000b\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0004\"\u0018\u0010\r\u001a\u00020\u000e*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\"\u0018\u0010\u0011\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0004\"\u0018\u0010\u0013\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0004¨\u0006\u0015"}, d2 = {"getFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getGetFunction", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "hashCodeFunction", "getHashCodeFunction", "iterator", "getIterator", "lengthProperty", "getLengthProperty", "setFunction", "getSetFunction", "sizeConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getSizeConstructor", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "sizeProperty", "getSizeProperty", "subSequence", "getSubSequence", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveContainerMemberCallTransformerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunctionSymbol getSizeProperty(IrClassSymbol irClassSymbol) {
        IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(irClassSymbol, "size");
        Intrinsics.checkNotNull(propertyGetter);
        return propertyGetter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunctionSymbol getGetFunction(IrClassSymbol irClassSymbol) {
        IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(irClassSymbol, "get");
        Intrinsics.checkNotNull(simpleFunction);
        return simpleFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunctionSymbol getSetFunction(IrClassSymbol irClassSymbol) {
        IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(irClassSymbol, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX);
        Intrinsics.checkNotNull(simpleFunction);
        return simpleFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunctionSymbol getIterator(IrClassSymbol irClassSymbol) {
        IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(irClassSymbol, "iterator");
        Intrinsics.checkNotNull(simpleFunction);
        return simpleFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrConstructorSymbol getSizeConstructor(IrClassSymbol irClassSymbol) {
        Sequence<IrConstructor> sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(irClassSymbol.getOwner().getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.PrimitiveContainerMemberCallTransformerKt$special$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrConstructor);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        for (IrConstructor irConstructor : sequenceFilter) {
            if (irConstructor.getValueParameters().size() == 1) {
                return irConstructor.getSymbol();
            }
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunctionSymbol getLengthProperty(IrClassSymbol irClassSymbol) {
        IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(irClassSymbol, "length");
        Intrinsics.checkNotNull(propertyGetter);
        return propertyGetter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunctionSymbol getSubSequence(IrClassSymbol irClassSymbol) {
        IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(irClassSymbol, "subSequence");
        Intrinsics.checkNotNull(simpleFunction);
        return simpleFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunctionSymbol getHashCodeFunction(IrClassSymbol irClassSymbol) {
        IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(irClassSymbol, "hashCode");
        Intrinsics.checkNotNull(simpleFunction);
        return simpleFunction;
    }
}
