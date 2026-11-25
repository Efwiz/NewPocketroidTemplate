package org.jetbrains.kotlin.backend.jvm.ir;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: JvmIrTypeUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013\u001a\"\u0010\u0014\u001a\u00020\u0015*\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001a\u001a\u0018\u0010\u001b\u001a\u00020\u0005*\u00020\u00052\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u0011\u001a\u0012\u0010\u001b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u001a\u0010\u001b\u001a\u00020\u001d*\u00020\u001d2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u0011H\u0002\u001a\n\u0010\u001e\u001a\u00020\u0005*\u00020\u0005\u001a\f\u0010\u001e\u001a\u00020\u001d*\u00020\u001dH\u0002\u001a\n\u0010\u001f\u001a\u00020\b*\u00020\u0005\u001a\n\u0010 \u001a\u00020\b*\u00020\u0005\u001a\n\u0010!\u001a\u00020\b*\u00020\u0005\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\t\"\u0015\u0010\n\u001a\u00020\u0005*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u0015\u0010\r\u001a\u00020\u0005*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\""}, d2 = {"erasedUpperBound", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getErasedUpperBound", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "isReifiedTypeParameter", "", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Z", "representativeUpperBound", "getRepresentativeUpperBound", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;)Lorg/jetbrains/kotlin/ir/types/IrType;", "upperBound", "getUpperBound", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/types/IrType;", "collectVisibleTypeParameters", "", "scopeOwner", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "defaultValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "eraseToScope", "visibleTypeParameters", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "eraseTypeParameters", "isInlineClassType", "isMultiFieldValueClassType", "isValueClassType", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmIrTypeUtilsKt {
    public static final IrType eraseTypeParameters(IrType irType) {
        IrSimpleTypeImpl irSimpleTypeImpl;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (irType instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) irType;
            IrSymbolOwner owner = irSimpleType.getClassifier().getOwner();
            if (owner instanceof IrScript) {
                irSimpleType.getArguments().isEmpty();
                irSimpleTypeImpl = new IrSimpleTypeImpl(irSimpleType.getClassifier(), irSimpleType.getNullability(), CollectionsKt.emptyList(), irType.getAnnotations(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
            } else if (!(owner instanceof IrClass)) {
                if (owner instanceof IrTypeParameter) {
                    IrTypeParameter irTypeParameter = (IrTypeParameter) owner;
                    IrClass erasedUpperBound = getErasedUpperBound(irTypeParameter);
                    IrClassSymbol symbol = erasedUpperBound.getSymbol();
                    boolean zIsNullable = IrTypeUtilsKt.isNullable(irType);
                    int size = erasedUpperBound.getTypeParameters().size();
                    ArrayList arrayList = new ArrayList(size);
                    for (int i = 0; i < size; i++) {
                        arrayList.add(IrStarProjectionImpl.INSTANCE);
                    }
                    irSimpleTypeImpl = new IrSimpleTypeImpl(symbol, zIsNullable, arrayList, irTypeParameter.getAnnotations(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
                } else {
                    throw new IllegalStateException(("Unknown IrSimpleType classifier kind: " + owner).toString());
                }
            } else {
                IrClassifierSymbol classifier = irSimpleType.getClassifier();
                SimpleTypeNullability nullability = irSimpleType.getNullability();
                List<IrTypeArgument> arguments = irSimpleType.getArguments();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    arrayList2.add(eraseTypeParameters((IrTypeArgument) it.next()));
                }
                irSimpleTypeImpl = new IrSimpleTypeImpl(classifier, nullability, arrayList2, irType.getAnnotations(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
            }
            return irSimpleTypeImpl;
        }
        if (irType instanceof IrErrorType) {
            return irType;
        }
        throw new IllegalStateException(("Unknown IrType kind: " + irType).toString());
    }

    private static final IrTypeArgument eraseTypeParameters(IrTypeArgument irTypeArgument) {
        if (irTypeArgument instanceof IrStarProjection) {
            return irTypeArgument;
        }
        if (!(irTypeArgument instanceof IrTypeProjection)) {
            throw new NoWhenBranchMatchedException();
        }
        IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
        return IrSimpleTypeImplKt.makeTypeProjection(eraseTypeParameters(irTypeProjection.getType()), irTypeProjection.getVariance());
    }

    public static final IrClass getErasedUpperBound(IrTypeParameter irTypeParameter) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(irTypeParameter, "<this>");
        Iterator<IrType> it = irTypeParameter.getSuperTypes().iterator();
        while (it.hasNext()) {
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(it.next());
            if (classOrNull != null && (owner = classOrNull.getOwner()) != null && !JvmIrUtilsKt.isJvmInterface(owner)) {
                return owner;
            }
        }
        return getErasedUpperBound((IrType) CollectionsKt.first((List) irTypeParameter.getSuperTypes()));
    }

    public static final IrClass getErasedUpperBound(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        if (classifierOrNull instanceof IrClassSymbol) {
            return ((IrClassSymbol) classifierOrNull).getOwner();
        }
        if (classifierOrNull instanceof IrTypeParameterSymbol) {
            return getErasedUpperBound(((IrTypeParameterSymbol) classifierOrNull).getOwner());
        }
        if (classifierOrNull instanceof IrScriptSymbol) {
            IrClassSymbol targetClass = ((IrScriptSymbol) classifierOrNull).getOwner().getTargetClass();
            Intrinsics.checkNotNull(targetClass);
            return targetClass.getOwner();
        }
        if (classifierOrNull != null) {
            throw new NoWhenBranchMatchedException();
        }
        if (irType instanceof IrErrorType) {
            return ((IrErrorType) irType).getErrorClassStubSymbol().getOwner();
        }
        throw new IllegalStateException(RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null).toString());
    }

    public static final IrExpression defaultValue(IrType irType, int i, int i2, JvmBackendContext context) {
        IrClassSymbol classOrNull;
        IrClass owner;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        if (classifierOrNull instanceof IrTypeParameterSymbol) {
            return defaultValue(getRepresentativeUpperBound(((IrTypeParameterSymbol) classifierOrNull).getOwner()), i, i2, context);
        }
        if (!(irType instanceof IrSimpleType) || IrTypePredicatesKt.isMarkedNullable((IrSimpleType) irType) || (classOrNull = IrTypesKt.getClassOrNull(irType)) == null || (owner = classOrNull.getOwner()) == null || !IrDeclarationsKt.isSingleFieldValueClass(owner)) {
            return IrConstImpl.INSTANCE.defaultValueForType(i, i2, irType);
        }
        IrType irTypeUnboxInlineClass = InlineClassAbiKt.unboxInlineClass(irType);
        IrConstImpl<?> irConstImplDefaultValueForType = IrConstImpl.INSTANCE.defaultValueForType(i, i2, irTypeUnboxInlineClass);
        IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
        IrSimpleFunctionSymbol unsafeCoerceIntrinsic = context.getIr().getSymbols().getUnsafeCoerceIntrinsic();
        IrCallImpl irCallImplFromSymbolOwner = companion.fromSymbolOwner(i, i2, irType, unsafeCoerceIntrinsic, (240 & 16) != 0 ? unsafeCoerceIntrinsic.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? unsafeCoerceIntrinsic.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : null);
        irCallImplFromSymbolOwner.putTypeArgument(0, irTypeUnboxInlineClass);
        irCallImplFromSymbolOwner.putTypeArgument(1, irType);
        irCallImplFromSymbolOwner.putValueArgument(0, irConstImplDefaultValueForType);
        return irCallImplFromSymbolOwner;
    }

    public static final boolean isInlineClassType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return IrDeclarationsKt.isSingleFieldValueClass(getErasedUpperBound(irType));
    }

    public static final boolean isMultiFieldValueClassType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return IrDeclarationsKt.isMultiFieldValueClass(getErasedUpperBound(irType));
    }

    public static final boolean isValueClassType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return getErasedUpperBound(irType).getIsValue();
    }

    public static final IrType getUpperBound(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return IrTypesKt.getStarProjectedType(getErasedUpperBound(irType).getSymbol());
    }

    public static final IrType eraseToScope(IrType irType, IrTypeParametersContainer scopeOwner) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        return eraseToScope(irType, collectVisibleTypeParameters(scopeOwner));
    }

    public static final IrType eraseToScope(IrType irType, Set<? extends IrTypeParameter> visibleTypeParameters) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(visibleTypeParameters, "visibleTypeParameters");
        if (irType instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) irType;
            IrClassifierSymbol classifier = irSimpleType.getClassifier();
            if (classifier instanceof IrClassSymbol) {
                IrClassifierSymbol classifier2 = irSimpleType.getClassifier();
                SimpleTypeNullability nullability = irSimpleType.getNullability();
                List<IrTypeArgument> arguments = irSimpleType.getArguments();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    arrayList.add(eraseToScope((IrTypeArgument) it.next(), visibleTypeParameters));
                }
                return new IrSimpleTypeImpl(classifier2, nullability, arrayList, irType.getAnnotations(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
            }
            if (classifier instanceof IrTypeParameterSymbol) {
                return CollectionsKt.contains(visibleTypeParameters, irSimpleType.getClassifier().getOwner()) ? irType : IrTypesKt.mergeNullability(getUpperBound(irType), irType);
            }
            if (classifier instanceof IrScriptSymbol) {
                throw new IllegalArgumentException("Unexpected kind of " + IrClassifierSymbol.class.getTypeName() + ": " + irSimpleType.getClassifier());
            }
            throw new NoWhenBranchMatchedException();
        }
        throw new IllegalStateException(("Unexpected IrType kind: " + RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    private static final IrTypeArgument eraseToScope(IrTypeArgument irTypeArgument, Set<? extends IrTypeParameter> set) {
        if (irTypeArgument instanceof IrStarProjection) {
            return irTypeArgument;
        }
        if (!(irTypeArgument instanceof IrTypeProjection)) {
            throw new NoWhenBranchMatchedException();
        }
        IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
        return IrSimpleTypeImplKt.makeTypeProjection(eraseToScope(irTypeProjection.getType(), set), irTypeProjection.getVariance());
    }

    public static final Set<IrTypeParameter> collectVisibleTypeParameters(IrTypeParametersContainer scopeOwner) {
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        return SequencesKt.toSet(SequencesKt.flatMapIterable(SequencesKt.generateSequence(scopeOwner, new Function1<IrTypeParametersContainer, IrTypeParametersContainer>() { // from class: org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt.collectVisibleTypeParameters.1
            @Override // kotlin.jvm.functions.Function1
            public final IrTypeParametersContainer invoke(IrTypeParametersContainer current) {
                Intrinsics.checkNotNullParameter(current, "current");
                IrDeclarationParent parent = current.getParent();
                IrTypeParametersContainer irTypeParametersContainer = parent instanceof IrTypeParametersContainer ? (IrTypeParametersContainer) parent : null;
                if (!(irTypeParametersContainer instanceof IrClass) || !(current instanceof IrClass) || ((IrClass) current).getIsInner() || AdditionalIrUtilsKt.isLocal(current)) {
                    return irTypeParametersContainer;
                }
                return null;
            }
        }), new Function1<IrTypeParametersContainer, List<? extends IrTypeParameter>>() { // from class: org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt.collectVisibleTypeParameters.2
            @Override // kotlin.jvm.functions.Function1
            public final List<IrTypeParameter> invoke(IrTypeParametersContainer it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getTypeParameters();
            }
        }));
    }

    public static final boolean isReifiedTypeParameter(IrType irType) {
        IrTypeParameter owner;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        IrTypeParameterSymbol irTypeParameterSymbol = classifierOrNull instanceof IrTypeParameterSymbol ? (IrTypeParameterSymbol) classifierOrNull : null;
        return (irTypeParameterSymbol == null || (owner = irTypeParameterSymbol.getOwner()) == null || !owner.getIsReified()) ? false : true;
    }

    public static final IrType getRepresentativeUpperBound(IrTypeParameter irTypeParameter) {
        Object next;
        IrClass owner;
        Intrinsics.checkNotNullParameter(irTypeParameter, "<this>");
        irTypeParameter.getSuperTypes().isEmpty();
        Iterator<T> it = irTypeParameter.getSuperTypes().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrType) next);
            if (classOrNull != null && (owner = classOrNull.getOwner()) != null && owner.getKind() != ClassKind.INTERFACE && owner.getKind() != ClassKind.ANNOTATION_CLASS) {
                break;
            }
        }
        IrType irType = (IrType) next;
        return irType == null ? (IrType) CollectionsKt.first((List) irTypeParameter.getSuperTypes()) : irType;
    }
}
