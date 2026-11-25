package org.jetbrains.kotlin.ir.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.impl.IrDynamicTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeBuilder;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.types.impl.IrTypeBaseKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StarProjectionImpl;
import org.jetbrains.kotlin.types.TypeProjectionBase;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: irTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000p\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\b\u001a&\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\f2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020 0)2\u0006\u0010*\u001a\u00020+H\u0003\u001a\u0018\u0010,\u001a\u00020\u0002*\u00020\u00022\f\u0010-\u001a\b\u0012\u0004\u0012\u00020.0)\u001a \u0010/\u001a\u00020\u0012*\u00020\u00062\u0006\u0010*\u001a\u00020+2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020 0)\u001a\f\u00100\u001a\u0004\u0018\u00010\u001c*\u00020\u0002\u001a\n\u00101\u001a\u00020\u0002*\u00020\u0002\u001a\n\u00102\u001a\u00020\u0002*\u00020\u0002\u001a\u0012\u00103\u001a\u00020\u0002*\u00020\u00022\u0006\u00104\u001a\u00020\u0002\u001a\n\u00105\u001a\u00020\u0002*\u00020\u0002\u001a\u001e\u00105\u001a\u00020\u0002*\u00020\u00022\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020+07\u001a\f\u00108\u001a\u000209*\u00020\u0002H\u0007\u001a#\u0010:\u001a\u00020\u0012*\u00020\u001c2\u0012\u0010(\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020;\"\u00020\u0002¢\u0006\u0002\u0010<\u001a\u0018\u0010:\u001a\u00020\u0012*\u00020\u001c2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00020)\u001a#\u0010:\u001a\u00020\u0012*\u00020\f2\u0012\u0010(\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020;\"\u00020\u0002¢\u0006\u0002\u0010=\u001a\u0018\u0010:\u001a\u00020\u0012*\u00020\f2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00020)\u001a\u0018\u0010>\u001a\u00020\u0012*\u00020\f2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020 0)\u001a\u0018\u0010?\u001a\u00020\u0012*\u00020\f2\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00130)\u001a\u0014\u0010A\u001a\u00020\u0012*\u00020\u00122\u0006\u0010B\u001a\u00020+H\u0002\u001a\u0014\u0010A\u001a\u00020\u0002*\u00020\u00022\u0006\u0010B\u001a\u00020+H\u0002\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0017\u0010\t\u001a\u0004\u0018\u00010\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\n\u0010\b\"\u0015\u0010\u000b\u001a\u00020\f*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\"\u0017\u0010\u000f\u001a\u0004\u0018\u00010\f*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000e\"\u0015\u0010\u0011\u001a\u00020\u0012*\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\"\u0015\u0010\u0011\u001a\u00020\u0002*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0016\"\u0015\u0010\u0017\u001a\u00020\u0012*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019\"\u001b\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00130\u001b*\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\"\u0015\u0010\u001f\u001a\u00020\u0002*\u00020 8F¢\u0006\u0006\u001a\u0004\b!\u0010\"\"\u0017\u0010#\u001a\u0004\u0018\u00010\u0002*\u00020 8F¢\u0006\u0006\u001a\u0004\b$\u0010\"¨\u0006C"}, d2 = {"classFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getClassFqName", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/name/FqName;", "classOrFail", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getClassOrFail", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "classOrNull", "getClassOrNull", "classifierOrFail", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getClassifierOrFail", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "classifierOrNull", "getClassifierOrNull", "defaultType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getDefaultType", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;)Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;)Lorg/jetbrains/kotlin/ir/types/IrType;", "starProjectedType", "getStarProjectedType", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "typeConstructorParameters", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getTypeConstructorParameters", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lkotlin/sequences/Sequence;", "typeOrFail", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "getTypeOrFail", "(Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;)Lorg/jetbrains/kotlin/ir/types/IrType;", "typeOrNull", "getTypeOrNull", "makeKotlinType", "Lorg/jetbrains/kotlin/types/SimpleType;", "classifier", "arguments", "", "hasQuestionMark", "", "addAnnotations", "newAnnotations", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "createType", "getClass", "makeNotNull", "makeNullable", "mergeNullability", "other", "removeAnnotations", "predicate", "Lkotlin/Function1;", "toKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeWith", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;[Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;[Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "typeWithArguments", "typeWithParameters", "parameters", "withNullability", "newNullability", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrTypesKt {

    /* JADX INFO: compiled from: irTypes.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SimpleTypeNullability.values().length];
            try {
                iArr[SimpleTypeNullability.MARKED_NULLABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SimpleTypeNullability.NOT_SPECIFIED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SimpleTypeNullability.DEFINITELY_NOT_NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final IrClassSymbol getClassOrFail(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassSymbol classOrNull = getClassOrNull(irType);
        if (classOrNull != null) {
            return classOrNull;
        }
        throw new IllegalStateException("Expect type to be a class type".toString());
    }

    public static final IrClassifierSymbol getClassifierOrFail(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassifierSymbol classifierOrNull = getClassifierOrNull(irType);
        if (classifierOrNull != null) {
            return classifierOrNull;
        }
        throw new IllegalStateException(("Can't get classifier of " + RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    private static final IrType withNullability(IrType irType, boolean z) {
        return irType instanceof IrSimpleType ? withNullability((IrSimpleType) irType, z) : irType;
    }

    private static final IrSimpleType withNullability(IrSimpleType irSimpleType, boolean z) {
        DefinitelyNotNullType definitelyNotNullTypeMakeNotNullable;
        SimpleTypeNullability simpleTypeNullability = z ? SimpleTypeNullability.MARKED_NULLABLE : SimpleTypeNullability.DEFINITELY_NOT_NULL;
        if (irSimpleType.getNullability() == simpleTypeNullability) {
            return irSimpleType;
        }
        IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder(irSimpleType);
        builder.setNullability(simpleTypeNullability);
        KotlinType originalKotlinType = IrTypeBaseKt.getOriginalKotlinType(irSimpleType);
        if (originalKotlinType == null) {
            definitelyNotNullTypeMakeNotNullable = null;
        } else if (z) {
            definitelyNotNullTypeMakeNotNullable = TypeUtils.makeNullable(originalKotlinType);
        } else {
            DefinitelyNotNullType definitelyNotNullTypeMakeDefinitelyNotNull$default = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$default(DefinitelyNotNullType.INSTANCE, originalKotlinType.unwrap(), false, false, 6, null);
            definitelyNotNullTypeMakeNotNullable = definitelyNotNullTypeMakeDefinitelyNotNull$default != null ? definitelyNotNullTypeMakeDefinitelyNotNull$default : TypeUtils.makeNotNullable(originalKotlinType);
        }
        builder.setKotlinType(definitelyNotNullTypeMakeNotNullable);
        return IrSimpleTypeImplKt.buildSimpleType(builder);
    }

    public static final IrType addAnnotations(IrType irType, List<? extends IrConstructorCall> newAnnotations) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        if (newAnnotations.isEmpty()) {
            return irType;
        }
        if (!(irType instanceof IrSimpleType)) {
            return irType instanceof IrDynamicType ? new IrDynamicTypeImpl(null, MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irType.getAnnotations(), (List) newAnnotations), Variance.INVARIANT) : irType;
        }
        IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder((IrSimpleType) irType);
        builder.setAnnotations(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) builder.getAnnotations(), (List) newAnnotations));
        return IrSimpleTypeImplKt.buildSimpleType(builder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrType removeAnnotations(IrType irType, Function1<? super IrConstructorCall, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        if (irType instanceof IrSimpleType) {
            IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder((IrSimpleType) irType);
            List<IrConstructorCall> annotations = builder.getAnnotations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : annotations) {
                if (!predicate.invoke(obj).booleanValue()) {
                    arrayList.add(obj);
                }
            }
            builder.setAnnotations(CollectionsKt.compactIfPossible(arrayList));
            return IrSimpleTypeImplKt.buildSimpleType(builder);
        }
        if (!(irType instanceof IrDynamicType)) {
            return irType;
        }
        List<IrConstructorCall> annotations2 = irType.getAnnotations();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : annotations2) {
            if (!predicate.invoke(obj2).booleanValue()) {
                arrayList2.add(obj2);
            }
        }
        return new IrDynamicTypeImpl(null, CollectionsKt.compactIfPossible(arrayList2), Variance.INVARIANT);
    }

    public static final IrType removeAnnotations(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (!(irType instanceof IrSimpleType)) {
            return irType instanceof IrDynamicType ? new IrDynamicTypeImpl(null, kotlin.collections.CollectionsKt.emptyList(), Variance.INVARIANT) : irType;
        }
        IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder((IrSimpleType) irType);
        builder.setAnnotations(kotlin.collections.CollectionsKt.emptyList());
        return IrSimpleTypeImplKt.buildSimpleType(builder);
    }

    public static final IrClassifierSymbol getClassifierOrNull(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (irType instanceof IrSimpleType) {
            return ((IrSimpleType) irType).getClassifier();
        }
        return null;
    }

    public static final IrClassSymbol getClassOrNull(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassifierSymbol classifierOrNull = getClassifierOrNull(irType);
        if (classifierOrNull instanceof IrClassSymbol) {
            return (IrClassSymbol) classifierOrNull;
        }
        if (classifierOrNull instanceof IrScriptSymbol) {
            return ((IrScriptSymbol) classifierOrNull).getOwner().getTargetClass();
        }
        return null;
    }

    public static final FqName getClassFqName(IrType irType) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassSymbol classOrNull = getClassOrNull(irType);
        if (classOrNull == null || (owner = classOrNull.getOwner()) == null) {
            return null;
        }
        return IrUtilsKt.getFqNameWhenAvailable(owner);
    }

    public static final IrType getTypeOrNull(IrTypeArgument irTypeArgument) {
        Intrinsics.checkNotNullParameter(irTypeArgument, "<this>");
        IrTypeProjection irTypeProjection = irTypeArgument instanceof IrTypeProjection ? (IrTypeProjection) irTypeArgument : null;
        if (irTypeProjection != null) {
            return irTypeProjection.getType();
        }
        return null;
    }

    public static final IrType getTypeOrFail(IrTypeArgument irTypeArgument) {
        Intrinsics.checkNotNullParameter(irTypeArgument, "<this>");
        if (!(irTypeArgument instanceof IrTypeProjection)) {
            throw new IllegalArgumentException(("Type argument should be of type `IrTypeProjection`, but was `" + Reflection.getOrCreateKotlinClass(irTypeArgument.getClass()) + "` instead").toString());
        }
        return ((IrTypeProjection) irTypeArgument).getType();
    }

    public static final IrType makeNotNull(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return withNullability(irType, false);
    }

    public static final IrType makeNullable(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return withNullability(irType, true);
    }

    public static final IrType mergeNullability(IrType irType, IrType other) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (!(other instanceof IrSimpleType)) {
            return irType;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[((IrSimpleType) other).getNullability().ordinal()];
        if (i == 1) {
            return makeNullable(irType);
        }
        if (i == 2) {
            return irType;
        }
        if (i == 3) {
            return makeNotNull(irType);
        }
        throw new NoWhenBranchMatchedException();
    }

    @ObsoleteDescriptorBasedAPI
    public static final KotlinType toKotlinType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        KotlinType originalKotlinType = IrTypeBaseKt.getOriginalKotlinType(irType);
        if (originalKotlinType != null) {
            return originalKotlinType;
        }
        if (!(irType instanceof IrSimpleType)) {
            throw new NotImplementedError("An operation is not implemented: " + irType.toString());
        }
        IrSimpleType irSimpleType = (IrSimpleType) irType;
        return makeKotlinType(irSimpleType.getClassifier(), irSimpleType.getArguments(), irSimpleType.getNullability() == SimpleTypeNullability.MARKED_NULLABLE);
    }

    public static final IrClass getClass(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassSymbol classOrNull = getClassOrNull(irType);
        if (classOrNull != null) {
            return classOrNull.getOwner();
        }
        return null;
    }

    public static final IrSimpleType createType(IrClassSymbol irClassSymbol, boolean z, List<? extends IrTypeArgument> arguments) {
        Intrinsics.checkNotNullParameter(irClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return new IrSimpleTypeImpl(irClassSymbol, z, arguments, kotlin.collections.CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
    }

    @ObsoleteDescriptorBasedAPI
    private static final SimpleType makeKotlinType(IrClassifierSymbol irClassifierSymbol, List<? extends IrTypeArgument> list, boolean z) {
        TypeProjectionBase starProjectionImpl;
        List<? extends IrTypeArgument> list2 = list;
        ArrayList arrayList = new ArrayList(list2.size());
        int i = 0;
        for (Object obj : list2) {
            int i2 = i + 1;
            if (i < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            IrTypeArgument irTypeArgument = (IrTypeArgument) obj;
            if (irTypeArgument instanceof IrTypeProjection) {
                IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
                starProjectionImpl = new TypeProjectionImpl(irTypeProjection.getVariance(), toKotlinType(irTypeProjection.getType()));
            } else {
                if (!(irTypeArgument instanceof IrStarProjection)) {
                    throw new NoWhenBranchMatchedException();
                }
                ClassifierDescriptor descriptor = irClassifierSymbol.getDescriptor();
                Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                TypeParameterDescriptor typeParameterDescriptor = ((ClassDescriptor) descriptor).getTypeConstructor().getParameters().get(i);
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "get(...)");
                starProjectionImpl = new StarProjectionImpl(typeParameterDescriptor);
            }
            arrayList.add(starProjectionImpl);
            i = i2;
        }
        List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList);
        SimpleType defaultType = irClassifierSymbol.getDescriptor().getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        return TypeSubstitutionKt.replace$default(defaultType, listCompactIfPossible, null, 2, null).makeNullableAsSpecified(z);
    }

    public static final IrType getDefaultType(IrClassifierSymbol irClassifierSymbol) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        if (irClassifierSymbol instanceof IrClassSymbol) {
            return IrUtilsKt.getDefaultType(((IrClassSymbol) irClassifierSymbol).getOwner());
        }
        if (irClassifierSymbol instanceof IrTypeParameterSymbol) {
            return getDefaultType(((IrTypeParameterSymbol) irClassifierSymbol).getOwner());
        }
        if (!(irClassifierSymbol instanceof IrScriptSymbol)) {
            throw new NoWhenBranchMatchedException();
        }
        throw new IllegalArgumentException("Unexpected kind of " + IrClassifierSymbol.class.getTypeName() + ": " + irClassifierSymbol);
    }

    public static final IrSimpleType getDefaultType(IrTypeParameter irTypeParameter) {
        Intrinsics.checkNotNullParameter(irTypeParameter, "<this>");
        return new IrSimpleTypeImpl(irTypeParameter.getSymbol(), SimpleTypeNullability.NOT_SPECIFIED, kotlin.collections.CollectionsKt.emptyList(), kotlin.collections.CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
    }

    public static final IrSimpleType getStarProjectedType(IrClassSymbol irClassSymbol) {
        Intrinsics.checkNotNullParameter(irClassSymbol, "<this>");
        return new IrSimpleTypeImpl(irClassSymbol, SimpleTypeNullability.NOT_SPECIFIED, SequencesKt.toList(SequencesKt.map(getTypeConstructorParameters(irClassSymbol.getOwner()), new Function1<IrTypeParameter, IrStarProjectionImpl>() { // from class: org.jetbrains.kotlin.ir.types.IrTypesKt$starProjectedType$1
            @Override // kotlin.jvm.functions.Function1
            public final IrStarProjectionImpl invoke(IrTypeParameter it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return IrStarProjectionImpl.INSTANCE;
            }
        })), kotlin.collections.CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
    }

    public static final Sequence<IrTypeParameter> getTypeConstructorParameters(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return SequencesKt.flatMapIterable(SequencesKt.generateSequence(irClass, new Function1<IrTypeParametersContainer, IrTypeParametersContainer>() { // from class: org.jetbrains.kotlin.ir.types.IrTypesKt$typeConstructorParameters$1
            @Override // kotlin.jvm.functions.Function1
            public final IrTypeParametersContainer invoke(IrTypeParametersContainer current) {
                Intrinsics.checkNotNullParameter(current, "current");
                IrDeclarationParent parent = current.getParent();
                IrTypeParametersContainer irTypeParametersContainer = parent instanceof IrTypeParametersContainer ? (IrTypeParametersContainer) parent : null;
                if ((irTypeParametersContainer instanceof IrSimpleFunction) && AdditionalIrUtilsKt.isPropertyAccessor(irTypeParametersContainer)) {
                    return null;
                }
                if ((current instanceof IrSimpleFunction) && IrUtilsKt.isStatic((IrFunction) current)) {
                    return null;
                }
                IrTypeParametersContainer irTypeParametersContainer2 = current;
                if (AdditionalIrUtilsKt.isAnonymousObject(irTypeParametersContainer2)) {
                    return null;
                }
                if (((irTypeParametersContainer instanceof IrClass) && (current instanceof IrClass) && !((IrClass) current).getIsInner()) || AdditionalIrUtilsKt.isJvmInlineClassConstructor(irTypeParametersContainer2)) {
                    return null;
                }
                return irTypeParametersContainer;
            }
        }), new Function1<IrTypeParametersContainer, List<? extends IrTypeParameter>>() { // from class: org.jetbrains.kotlin.ir.types.IrTypesKt$typeConstructorParameters$2
            @Override // kotlin.jvm.functions.Function1
            public final List<IrTypeParameter> invoke(IrTypeParametersContainer it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getTypeParameters();
            }
        });
    }

    public static final IrSimpleType typeWithParameters(IrClassifierSymbol irClassifierSymbol, List<? extends IrTypeParameter> parameters) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        List<? extends IrTypeParameter> list = parameters;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(getDefaultType((IrTypeParameter) it.next()));
        }
        return typeWith(irClassifierSymbol, arrayList);
    }

    public static final IrSimpleType typeWith(IrClassifierSymbol irClassifierSymbol, IrType... arguments) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return typeWith(irClassifierSymbol, (List<? extends IrType>) ArraysKt.toList(arguments));
    }

    public static final IrSimpleType typeWith(IrClassifierSymbol irClassifierSymbol, List<? extends IrType> arguments) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        SimpleTypeNullability simpleTypeNullability = SimpleTypeNullability.NOT_SPECIFIED;
        List<? extends IrType> list = arguments;
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(IrSimpleTypeImplKt.makeTypeProjection((IrType) it.next(), Variance.INVARIANT));
        }
        return new IrSimpleTypeImpl(irClassifierSymbol, simpleTypeNullability, CollectionsKt.compactIfPossible(arrayList), kotlin.collections.CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
    }

    public static final IrSimpleType typeWithArguments(IrClassifierSymbol irClassifierSymbol, List<? extends IrTypeArgument> arguments) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return new IrSimpleTypeImpl(irClassifierSymbol, SimpleTypeNullability.NOT_SPECIFIED, arguments, kotlin.collections.CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
    }

    public static final IrSimpleType typeWith(IrClass irClass, List<? extends IrType> arguments) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return typeWith(irClass.getSymbol(), arguments);
    }

    public static final IrSimpleType typeWith(IrClass irClass, IrType... arguments) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return typeWith(irClass.getSymbol(), (List<? extends IrType>) ArraysKt.toList(arguments));
    }
}
