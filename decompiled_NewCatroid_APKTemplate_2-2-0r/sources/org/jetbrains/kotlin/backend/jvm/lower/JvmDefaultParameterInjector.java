package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JvmDefaultParameterInjector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0014J\"\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0014J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0014J\u0010\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0014J*\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0016*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\bH\u0014¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmDefaultParameterInjector;", "Lorg/jetbrains/kotlin/backend/common/lower/DefaultParameterInjector;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "context", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "defaultArgumentStubVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "isStatic", "", "nullConst", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", "irParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "useConstructorMarker", "argumentsForCall", "", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "stubFunction", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDefaultParameterInjector extends DefaultParameterInjector<JvmBackendContext> {
    /* JADX WARN: Illegal instructions before constructor call */
    public JvmDefaultParameterInjector(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        JvmBackendContext jvmBackendContext = context;
        super(jvmBackendContext, new JvmDefaultArgumentFunctionFactory(jvmBackendContext), false, false, false, 16, null);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector
    protected IrExpression nullConst(int startOffset, int endOffset, IrValueParameter irParameter) {
        Intrinsics.checkNotNullParameter(irParameter, "irParameter");
        return nullConst(startOffset, endOffset, irParameter.getType());
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector
    protected IrExpression nullConst(int startOffset, int endOffset, IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return JvmIrTypeUtilsKt.defaultValue(type, startOffset, endOffset, getContext());
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector
    protected DescriptorVisibility defaultArgumentStubVisibility(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return JvmIrUtilsKt.getJvmVisibilityOfDefaultArgumentStub(function);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector
    protected boolean useConstructorMarker(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return (function instanceof IrConstructor) || Intrinsics.areEqual(function.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_INLINE_CLASS_CONSTRUCTOR.INSTANCE) || Intrinsics.areEqual(function.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector
    protected boolean isStatic(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return Intrinsics.areEqual(function.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_INLINE_CLASS_REPLACEMENT.INSTANCE) || Intrinsics.areEqual(function.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector
    protected Map<IrValueParameter, IrExpression> argumentsForCall(IrBlockBuilder irBlockBuilder, final IrFunctionAccessExpression expression, IrFunction stubFunction) {
        Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(stubFunction, "stubFunction");
        final int startOffset = expression.getStartOffset();
        final int endOffset = expression.getEndOffset();
        IrFunction owner = expression.getSymbol().getOwner();
        List<IrValueParameter> valueParameters = owner.getValueParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueParameters) {
            if (!isMovedReceiver((IrValueParameter) obj)) {
                arrayList.add(obj);
            }
        }
        int size = (arrayList.size() + 31) / 32;
        final int[] iArr = new int[size];
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
        }
        IrValueParameter extensionReceiverParameter = owner.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
        }
        List<IrValueParameter> valueParameters2 = owner.getValueParameters();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters2, 10));
        int i = 0;
        for (Object obj2 : valueParameters2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList2.add(TuplesKt.to((IrValueParameter) obj2, expression.getValueArgument(i)));
            i = i2;
        }
        MapsKt.putAll(mapCreateMapBuilder, arrayList2);
        final Map mapBuild = MapsKt.build(mapCreateMapBuilder);
        List<IrValueParameter> valueParameters3 = owner.getValueParameters();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj3 : valueParameters3) {
            if (!isMovedReceiver((IrValueParameter) obj3)) {
                arrayList3.add(obj3);
            }
        }
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(arrayList3);
        final LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
        for (IndexedValue indexedValue : iterableWithIndex) {
            Pair pair = TuplesKt.to(indexedValue.getValue(), Integer.valueOf(indexedValue.getIndex()));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        Map<IrValueParameter, IrExpression> mapMapFunctionMfvcStructures = getContext().getMultiFieldValueClassReplacements().mapFunctionMfvcStructures(irBlockBuilder, stubFunction, owner, new Function2<IrValueParameter, IrType, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmDefaultParameterInjector$argumentsForCall$mainArguments$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrExpression invoke(IrValueParameter sourceParameter, IrType targetParameterType) {
                Intrinsics.checkNotNullParameter(sourceParameter, "sourceParameter");
                Intrinsics.checkNotNullParameter(targetParameterType, "targetParameterType");
                IrExpression irExpression = mapBuild.get(sourceParameter);
                if (irExpression == null) {
                    Integer num = linkedHashMap.get(sourceParameter);
                    Intrinsics.checkNotNull(num);
                    int iIntValue = num.intValue();
                    int[] iArr2 = iArr;
                    int i3 = iIntValue / 32;
                    iArr2[i3] = (1 << (iIntValue % 32)) | iArr2[i3];
                }
                return irExpression == null ? new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), targetParameterType, IrStatementOrigin.DEFAULT_VALUE.INSTANCE, CollectionsKt.listOf(this.nullConst(startOffset, endOffset, targetParameterType))) : irExpression;
            }
        });
        CollectionsKt.listOf((Object[]) new Integer[]{0, 1}).contains(Integer.valueOf((IrUtilsKt.getExplicitParametersCount(stubFunction) - mapMapFunctionMfvcStructures.size()) - size));
        Map mapCreateMapBuilder2 = MapsKt.createMapBuilder();
        mapCreateMapBuilder2.putAll(mapMapFunctionMfvcStructures);
        List<IrValueParameter> valueParameters4 = stubFunction.getValueParameters();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj4 : valueParameters4) {
            if (!mapMapFunctionMfvcStructures.containsKey((IrValueParameter) obj4)) {
                arrayList4.add(obj4);
            }
        }
        ArrayList arrayList5 = arrayList4;
        for (Pair pair2 : CollectionsKt.zip(arrayList5, ArraysKt.asList(iArr))) {
            IrValueParameter irValueParameter = (IrValueParameter) pair2.component1();
            mapCreateMapBuilder2.put(irValueParameter, IrConstImpl.INSTANCE.m7211int(startOffset, endOffset, irValueParameter.getType(), ((Number) pair2.component2()).intValue()));
        }
        if (arrayList5.size() > size) {
            IrValueParameter irValueParameter2 = (IrValueParameter) CollectionsKt.last((List) arrayList5);
            mapCreateMapBuilder2.put(irValueParameter2, IrConstImpl.INSTANCE.constNull(startOffset, endOffset, irValueParameter2.getType()));
        }
        return MapsKt.build(mapCreateMapBuilder2);
    }
}
