package org.jetbrains.kotlin.ir.types.impl;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IrSimpleTypeImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u001a\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b\u001a)\u0010\t\u001a\u00020\n*\u00020\n2\u0017\u0010\u000b\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f¢\u0006\u0002\b\u000fH\u0086\bø\u0001\u0000\u001a\n\u0010\t\u001a\u00020\u0010*\u00020\r\u001a\n\u0010\u0011\u001a\u00020\u0005*\u00020\r\u001a\n\u0010\u0012\u001a\u00020\r*\u00020\n\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0013"}, d2 = {"makeTypeIntersection", "Lorg/jetbrains/kotlin/ir/types/IrType;", SpecTypePair.NAME_types, "", "makeTypeProjection", "Lorg/jetbrains/kotlin/ir/types/IrTypeProjection;", ModuleXmlParser.TYPE, "variance", "Lorg/jetbrains/kotlin/types/Variance;", "buildSimpleType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/types/impl/IrSimpleTypeBuilder;", "", "Lkotlin/ExtensionFunctionType;", "Lorg/jetbrains/kotlin/ir/types/impl/IrSimpleTypeImpl;", "buildTypeProjection", "toBuilder", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrSimpleTypeImplKt {
    public static final IrSimpleTypeBuilder toBuilder(IrSimpleType irSimpleType) {
        Intrinsics.checkNotNullParameter(irSimpleType, "<this>");
        IrSimpleTypeBuilder irSimpleTypeBuilder = new IrSimpleTypeBuilder();
        irSimpleTypeBuilder.setKotlinType(IrTypeBaseKt.getOriginalKotlinType(irSimpleType));
        irSimpleTypeBuilder.setClassifier(irSimpleType.getClassifier());
        irSimpleTypeBuilder.setNullability(irSimpleType.getNullability());
        irSimpleTypeBuilder.setArguments(irSimpleType.getArguments());
        irSimpleTypeBuilder.setAnnotations(irSimpleType.getAnnotations());
        irSimpleTypeBuilder.setAbbreviation(irSimpleType.getAbbreviation());
        return irSimpleTypeBuilder;
    }

    public static final IrSimpleTypeImpl buildSimpleType(IrSimpleTypeBuilder irSimpleTypeBuilder) {
        Intrinsics.checkNotNullParameter(irSimpleTypeBuilder, "<this>");
        KotlinType kotlinType = irSimpleTypeBuilder.getKotlinType();
        IrClassifierSymbol classifier = irSimpleTypeBuilder.getClassifier();
        if (classifier != null) {
            return new IrSimpleTypeImpl(kotlinType, classifier, irSimpleTypeBuilder.getNullability(), CollectionsKt.compactIfPossible(irSimpleTypeBuilder.getArguments()), CollectionsKt.compactIfPossible(irSimpleTypeBuilder.getAnnotations()), irSimpleTypeBuilder.getAbbreviation());
        }
        throw new AssertionError("Classifier not provided");
    }

    public static final IrTypeProjection buildTypeProjection(IrSimpleTypeBuilder irSimpleTypeBuilder) {
        Intrinsics.checkNotNullParameter(irSimpleTypeBuilder, "<this>");
        if (irSimpleTypeBuilder.getVariance() == Variance.INVARIANT) {
            return buildSimpleType(irSimpleTypeBuilder);
        }
        return new IrTypeProjectionImpl(buildSimpleType(irSimpleTypeBuilder), irSimpleTypeBuilder.getVariance());
    }

    public static final IrSimpleType buildSimpleType(IrSimpleType irSimpleType, Function1<? super IrSimpleTypeBuilder, Unit> b) {
        Intrinsics.checkNotNullParameter(irSimpleType, "<this>");
        Intrinsics.checkNotNullParameter(b, "b");
        IrSimpleTypeBuilder builder = toBuilder(irSimpleType);
        b.invoke(builder);
        return buildSimpleType(builder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrTypeProjection makeTypeProjection(IrType type, Variance variance) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variance, "variance");
        if (type instanceof IrCapturedType) {
            return new IrTypeProjectionImpl(type, variance);
        }
        if (type instanceof IrTypeProjection) {
            IrTypeProjection irTypeProjection = (IrTypeProjection) type;
            if (irTypeProjection.getVariance() == variance) {
                return irTypeProjection;
            }
        }
        if (type instanceof IrSimpleType) {
            IrSimpleTypeBuilder builder = toBuilder((IrSimpleType) type);
            builder.setVariance(variance);
            return buildTypeProjection(builder);
        }
        if (type instanceof IrDynamicType) {
            return new IrDynamicTypeImpl(null, type.getAnnotations(), variance);
        }
        return type instanceof IrErrorType ? new IrErrorTypeImpl(null, type.getAnnotations(), variance, false, 8, null) : new IrTypeProjectionImpl(type, variance);
    }

    public static final IrType makeTypeIntersection(List<? extends IrType> types) {
        Object next;
        Intrinsics.checkNotNullParameter(types, "types");
        List<? extends IrType> list = types;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(makeTypeProjection((IrType) it.next(), Variance.INVARIANT).getType());
        }
        List listDistinct = kotlin.collections.CollectionsKt.distinct(arrayList);
        if (listDistinct.size() == 1) {
            return (IrType) kotlin.collections.CollectionsKt.single(listDistinct);
        }
        List<IrType> list2 = listDistinct;
        Iterator it2 = list2.iterator();
        while (true) {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
            IrType irType = (IrType) next;
            if (!IrTypePredicatesKt.isAny(irType) && !IrTypePredicatesKt.isNullableAny(irType)) {
                break;
            }
        }
        irType = (IrType) next;
        if (irType == null) {
            for (IrType irType2 : list2) {
                if (IrTypePredicatesKt.isAny(irType2)) {
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        return irType2;
    }
}
