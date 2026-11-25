package org.jetbrains.kotlin.backend.common.actualizer;

import androidx.exifinterface.media.ExifInterface;
import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualCollectionArgumentsCompatibilityCheckStrategy;

/* JADX INFO: compiled from: irConstExpressionValuesEqualityChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a>\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u00022\u0006\u0010\u0005\u001a\u0002H\u00022\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0007H\u0082\b¢\u0006\u0002\u0010\b\u001a(\u0010\t\u001a\u00020\u0001*\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000¨\u0006\u0010"}, d2 = {"equalBy", "", ExifInterface.GPS_DIRECTION_TRUE, "", "first", "second", "selector", "Lkotlin/Function1;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Z", "areIrExpressionConstValuesEqual", "Lorg/jetbrains/kotlin/backend/common/actualizer/IrExpectActualMatchingContext;", "a", "Lorg/jetbrains/kotlin/ir/IrElement;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "collectionArgumentsCompatibilityCheckStrategy", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrConstExpressionValuesEqualityCheckerKt {
    public static final boolean areIrExpressionConstValuesEqual(final IrExpectActualMatchingContext irExpectActualMatchingContext, IrElement irElement, IrElement irElement2, final ExpectActualCollectionArgumentsCompatibilityCheckStrategy collectionArgumentsCompatibilityCheckStrategy) {
        Intrinsics.checkNotNullParameter(irExpectActualMatchingContext, "<this>");
        Intrinsics.checkNotNullParameter(collectionArgumentsCompatibilityCheckStrategy, "collectionArgumentsCompatibilityCheckStrategy");
        if (irElement == null || irElement2 == null) {
            if ((irElement == null) == (irElement2 == null)) {
                return true;
            }
        } else if (irElement.getClass() == irElement2.getClass()) {
            if ((irElement instanceof IrConst) && (irElement2 instanceof IrConst)) {
                return Intrinsics.areEqual(((IrConst) irElement).getValue(), ((IrConst) irElement2).getValue());
            }
            if (!(irElement instanceof IrClassReference) || !(irElement2 instanceof IrClassReference)) {
                if ((irElement instanceof IrGetEnumValue) && (irElement2 instanceof IrGetEnumValue)) {
                    IdSignature signature = ((IrGetEnumValue) irElement).getSymbol().getSignature();
                    String string = signature != null ? signature.toString() : null;
                    IdSignature signature2 = ((IrGetEnumValue) irElement2).getSymbol().getSignature();
                    return Intrinsics.areEqual(string, signature2 != null ? signature2.toString() : null);
                }
                if ((irElement instanceof IrVararg) && (irElement2 instanceof IrVararg)) {
                    return collectionArgumentsCompatibilityCheckStrategy.areCompatible(((IrVararg) irElement).getElements(), ((IrVararg) irElement2).getElements(), new Function2<IrVarargElement, IrVarargElement, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.actualizer.IrConstExpressionValuesEqualityCheckerKt.areIrExpressionConstValuesEqual.3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Boolean invoke(IrVarargElement f, IrVarargElement s) {
                            Intrinsics.checkNotNullParameter(f, "f");
                            Intrinsics.checkNotNullParameter(s, "s");
                            return Boolean.valueOf(IrConstExpressionValuesEqualityCheckerKt.areIrExpressionConstValuesEqual(irExpectActualMatchingContext, f, s, collectionArgumentsCompatibilityCheckStrategy));
                        }
                    });
                }
                if ((irElement instanceof IrConstructorCall) && (irElement2 instanceof IrConstructorCall)) {
                    IrConstructorCall irConstructorCall = (IrConstructorCall) irElement;
                    IrConstructorCall irConstructorCall2 = (IrConstructorCall) irElement2;
                    if (irConstructorCall.getValueArgumentsCount() == irConstructorCall2.getValueArgumentsCount() && irExpectActualMatchingContext.areCompatibleExpectActualTypes(irConstructorCall.getType(), irConstructorCall2.getType())) {
                        Iterable iterableUntil = RangesKt.until(0, irConstructorCall.getValueArgumentsCount());
                        if ((iterableUntil instanceof Collection) && ((Collection) iterableUntil).isEmpty()) {
                            return true;
                        }
                        Iterator it = iterableUntil.iterator();
                        while (it.hasNext()) {
                            int iNextInt = ((IntIterator) it).nextInt();
                            if (!areIrExpressionConstValuesEqual(irExpectActualMatchingContext, irConstructorCall.getValueArgument(iNextInt), irConstructorCall2.getValueArgument(iNextInt), collectionArgumentsCompatibilityCheckStrategy)) {
                            }
                        }
                        return true;
                    }
                } else {
                    throw new IllegalStateException(("Not handled expression types " + irElement + StringUtil.SPACE + irElement2).toString());
                }
            } else {
                return Intrinsics.areEqual(irExpectActualMatchingContext.getClassIdAfterActualization$ir_backend_common(irExpectActualMatchingContext.getClassId(IrTypesKt.getClassOrFail(((IrClassReference) irElement).getClassType()))), irExpectActualMatchingContext.getClassIdAfterActualization$ir_backend_common(irExpectActualMatchingContext.getClassId(IrTypesKt.getClassOrFail(((IrClassReference) irElement2).getClassType()))));
            }
        }
        return false;
    }
}
