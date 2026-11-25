package org.jetbrains.kotlin.ir.descriptors;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.DynamicType;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StarProjectionImpl;
import org.jetbrains.kotlin.types.TypeProjectionBase;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Î\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a0\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002\u001a\n\u0010\u000f\u001a\u00020\u0010*\u00020\u0011\u001a\n\u0010\u000f\u001a\u00020\u0012*\u00020\u0013\u001a\n\u0010\u000f\u001a\u00020\u0001*\u00020\u0003\u001a\n\u0010\u000f\u001a\u00020\u0014*\u00020\u0015\u001a\n\u0010\u000f\u001a\u00020\u0016*\u00020\u0017\u001a\n\u0010\u000f\u001a\u00020\u0018*\u00020\u0019\u001a\n\u0010\u000f\u001a\u00020\u001a*\u00020\u001b\u001a\n\u0010\u000f\u001a\u00020\u001c*\u00020\u001d\u001a\n\u0010\u000f\u001a\u00020\u001e*\u00020\u001f\u001a\f\u0010\u000f\u001a\u00020\u0001*\u00020 H\u0002\u001a\n\u0010\u000f\u001a\u00020!*\u00020\"\u001a\n\u0010\u000f\u001a\u00020#*\u00020$\u001a\n\u0010\u000f\u001a\u00020%*\u00020&\u001a\n\u0010\u000f\u001a\u00020'*\u00020(\u001a\n\u0010\u000f\u001a\u00020)*\u00020*\u001a\f\u0010+\u001a\u00020,*\u00020-H\u0002\u001a\f\u0010+\u001a\u00020.*\u00020/H\u0002\u001a\f\u0010+\u001a\u000200*\u000201H\u0002\u001a\f\u0010+\u001a\u00020\u001a*\u000202H\u0002\u001a\f\u0010+\u001a\u000203*\u000204H\u0002\u001a\u0016\u00105\u001a\u000206*\u0002072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e¨\u00068"}, d2 = {"getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "makeKotlinType", "Lorg/jetbrains/kotlin/types/SimpleType;", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "arguments", "", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "hasQuestionMark", "", "builtins", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "toIrBasedDescriptor", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedClassDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedEnumEntryDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedErrorDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedFieldDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedVariableDescriptorWithAccessor;", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedPropertyDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedSimpleFunctionDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedTypeAliasDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedTypeParameterDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedVariableDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "toIrBasedDescriptorIfPossible", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "toIrBasedKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrBasedDescriptorsKt {
    public static final DeclarationDescriptor toIrBasedDescriptor(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (irDeclaration instanceof IrValueParameter) {
            return toIrBasedDescriptor((IrValueParameter) irDeclaration);
        }
        if (irDeclaration instanceof IrTypeParameter) {
            return toIrBasedDescriptor((IrTypeParameter) irDeclaration);
        }
        if (irDeclaration instanceof IrVariable) {
            return toIrBasedDescriptor((IrVariable) irDeclaration);
        }
        if (irDeclaration instanceof IrLocalDelegatedProperty) {
            return toIrBasedDescriptor((IrLocalDelegatedProperty) irDeclaration);
        }
        if (irDeclaration instanceof IrFunction) {
            return toIrBasedDescriptor((IrFunction) irDeclaration);
        }
        if (irDeclaration instanceof IrClass) {
            return toIrBasedDescriptor((IrClass) irDeclaration);
        }
        if (irDeclaration instanceof IrAnonymousInitializer) {
            return toIrBasedDescriptor(IrUtilsKt.getParentAsClass(irDeclaration));
        }
        if (irDeclaration instanceof IrEnumEntry) {
            return toIrBasedDescriptor((IrEnumEntry) irDeclaration);
        }
        if (irDeclaration instanceof IrProperty) {
            return toIrBasedDescriptor((IrProperty) irDeclaration);
        }
        if (irDeclaration instanceof IrField) {
            return toIrBasedDescriptor((IrField) irDeclaration);
        }
        if (irDeclaration instanceof IrTypeAlias) {
            return toIrBasedDescriptor((IrTypeAlias) irDeclaration);
        }
        if (irDeclaration instanceof IrErrorDeclaration) {
            return toIrBasedDescriptor((IrErrorDeclaration) irDeclaration);
        }
        if (irDeclaration instanceof IrScript) {
            return toIrBasedDescriptor((IrScript) irDeclaration);
        }
        throw new IllegalStateException("Unknown declaration kind".toString());
    }

    public static final CallableDescriptor toIrBasedDescriptor(IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        if (irValueParameter.getIndex() < 0) {
            return new IrBasedReceiverParameterDescriptor(irValueParameter);
        }
        return new IrBasedValueParameterDescriptor(irValueParameter);
    }

    public static final IrBasedTypeParameterDescriptor toIrBasedDescriptor(IrTypeParameter irTypeParameter) {
        Intrinsics.checkNotNullParameter(irTypeParameter, "<this>");
        return new IrBasedTypeParameterDescriptor(irTypeParameter);
    }

    public static final IrBasedVariableDescriptor toIrBasedDescriptor(IrVariable irVariable) {
        Intrinsics.checkNotNullParameter(irVariable, "<this>");
        return new IrBasedVariableDescriptor(irVariable);
    }

    public static final IrBasedVariableDescriptorWithAccessor toIrBasedDescriptor(IrLocalDelegatedProperty irLocalDelegatedProperty) {
        Intrinsics.checkNotNullParameter(irLocalDelegatedProperty, "<this>");
        return new IrBasedVariableDescriptorWithAccessor(irLocalDelegatedProperty);
    }

    public static final IrBasedSimpleFunctionDescriptor toIrBasedDescriptor(IrSimpleFunction irSimpleFunction) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        if (AdditionalIrUtilsKt.isGetter(irSimpleFunction2)) {
            return new IrBasedPropertyGetterDescriptor(irSimpleFunction);
        }
        return AdditionalIrUtilsKt.isSetter(irSimpleFunction2) ? new IrBasedPropertySetterDescriptor(irSimpleFunction) : new IrBasedSimpleFunctionDescriptor(irSimpleFunction);
    }

    public static final IrBasedClassConstructorDescriptor toIrBasedDescriptor(IrConstructor irConstructor) {
        Intrinsics.checkNotNullParameter(irConstructor, "<this>");
        return new IrBasedClassConstructorDescriptor(irConstructor);
    }

    public static final FunctionDescriptor toIrBasedDescriptor(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction instanceof IrSimpleFunction) {
            return toIrBasedDescriptor((IrSimpleFunction) irFunction);
        }
        if (irFunction instanceof IrConstructor) {
            return toIrBasedDescriptor((IrConstructor) irFunction);
        }
        throw new IllegalStateException("Unknown function kind".toString());
    }

    public static final IrBasedClassDescriptor toIrBasedDescriptor(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return new IrBasedClassDescriptor(irClass);
    }

    public static final IrBasedEnumEntryDescriptor toIrBasedDescriptor(IrEnumEntry irEnumEntry) {
        Intrinsics.checkNotNullParameter(irEnumEntry, "<this>");
        return new IrBasedEnumEntryDescriptor(irEnumEntry);
    }

    public static final IrBasedPropertyDescriptor toIrBasedDescriptor(IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        return new IrBasedPropertyDescriptor(irProperty);
    }

    public static final IrBasedTypeAliasDescriptor toIrBasedDescriptor(IrTypeAlias irTypeAlias) {
        Intrinsics.checkNotNullParameter(irTypeAlias, "<this>");
        return new IrBasedTypeAliasDescriptor(irTypeAlias);
    }

    public static final IrBasedFieldDescriptor toIrBasedDescriptor(IrField irField) {
        Intrinsics.checkNotNullParameter(irField, "<this>");
        if (Intrinsics.areEqual(irField.getOrigin(), IrDeclarationOrigin.DELEGATE.INSTANCE)) {
            return new IrBasedDelegateFieldDescriptor(irField);
        }
        return new IrBasedFieldDescriptor(irField);
    }

    public static final IrBasedErrorDescriptor toIrBasedDescriptor(IrErrorDeclaration irErrorDeclaration) {
        Intrinsics.checkNotNullParameter(irErrorDeclaration, "<this>");
        return new IrBasedErrorDescriptor(irErrorDeclaration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DeclarationDescriptor getContainingDeclaration(IrDeclaration irDeclaration) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        IrDeclarationParent parent = irDeclaration.getParent();
        if ((irDeclaration instanceof IrTypeParameter) && (parent instanceof IrSimpleFunction) && (correspondingPropertySymbol = ((IrSimpleFunction) parent).getCorrespondingPropertySymbol()) != null && (owner = correspondingPropertySymbol.getOwner()) != null) {
            return toIrBasedDescriptor(owner);
        }
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
        IrSymbolOwner irSymbolOwner = (IrSymbolOwner) parent;
        DeclarationDescriptor irBasedDescriptor = irSymbolOwner instanceof IrDeclaration ? toIrBasedDescriptor((IrDeclaration) irSymbolOwner) : irSymbolOwner.getSymbol().getDescriptor();
        if (!(parent instanceof IrClass) || !IrUtilsKt.isFileClass((IrDeclaration) parent)) {
            return irBasedDescriptor;
        }
        DeclarationDescriptor moduleDescriptor = irBasedDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNull(moduleDescriptor);
        Intrinsics.checkNotNull(moduleDescriptor);
        return moduleDescriptor;
    }

    public static /* synthetic */ KotlinType toIrBasedKotlinType$default(IrType irType, KotlinBuiltIns kotlinBuiltIns, int i, Object obj) {
        if ((i & 1) != 0) {
            kotlinBuiltIns = null;
        }
        return toIrBasedKotlinType(irType, kotlinBuiltIns);
    }

    public static final KotlinType toIrBasedKotlinType(IrType irType, KotlinBuiltIns kotlinBuiltIns) {
        DefinitelyNotNullType definitelyNotNullTypeMakeDefinitelyNotNull$default;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (irType instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) irType;
            DefinitelyNotNullType definitelyNotNullTypeMakeKotlinType = makeKotlinType(irSimpleType.getClassifier(), irSimpleType.getArguments(), IrTypePredicatesKt.isMarkedNullable(irSimpleType), kotlinBuiltIns);
            if ((irSimpleType.getClassifier() instanceof IrTypeParameterSymbol) && irSimpleType.getNullability() == SimpleTypeNullability.DEFINITELY_NOT_NULL && (definitelyNotNullTypeMakeDefinitelyNotNull$default = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$default(DefinitelyNotNullType.INSTANCE, definitelyNotNullTypeMakeKotlinType.unwrap(), false, true, 2, null)) != null) {
                definitelyNotNullTypeMakeKotlinType = definitelyNotNullTypeMakeDefinitelyNotNull$default;
            }
            return definitelyNotNullTypeMakeKotlinType;
        }
        if (!(irType instanceof IrDynamicType)) {
            if (!(irType instanceof IrErrorType)) {
                throw new AssertionError("Unexpected type: " + irType + " = " + RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null));
            }
            KotlinType kotlinType = ((IrErrorType) irType).getKotlinType();
            if (kotlinType != null) {
                return kotlinType;
            }
            throw new IllegalStateException(("Can't find KotlinType in IrErrorType: " + RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        KotlinType kotlinType2 = ((IrDynamicType) irType).getKotlinType();
        if (kotlinType2 != null) {
            return kotlinType2;
        }
        DynamicType dynamicTypeCreateDynamicType = kotlinBuiltIns != null ? DynamicTypesKt.createDynamicType(kotlinBuiltIns) : null;
        if (dynamicTypeCreateDynamicType != null) {
            return dynamicTypeCreateDynamicType;
        }
        throw new IllegalStateException("Couldn't instantiate DynamicType".toString());
    }

    private static final SimpleType makeKotlinType(IrClassifierSymbol irClassifierSymbol, List<? extends IrTypeArgument> list, boolean z, KotlinBuiltIns kotlinBuiltIns) {
        TypeProjectionBase starProjectionImpl;
        if (irClassifierSymbol instanceof IrTypeParameterSymbol) {
            return toIrBasedDescriptorIfPossible((IrTypeParameterSymbol) irClassifierSymbol).getDefaultType().makeNullableAsSpecified(z);
        }
        if (!(irClassifierSymbol instanceof IrClassSymbol)) {
            if (irClassifierSymbol instanceof IrScriptSymbol) {
                return TypeUtils.makeUnsubstitutedType(toIrBasedDescriptorIfPossible((IrScriptSymbol) irClassifierSymbol), MemberScope.Empty.INSTANCE, KotlinTypeFactory.INSTANCE.getEMPTY_REFINED_TYPE_FACTORY()).makeNullableAsSpecified(z);
            }
            throw new NoWhenBranchMatchedException();
        }
        IrClassSymbol irClassSymbol = (IrClassSymbol) irClassifierSymbol;
        ClassDescriptor irBasedDescriptorIfPossible = toIrBasedDescriptorIfPossible(irClassSymbol);
        List<? extends IrTypeArgument> list2 = list;
        ArrayList arrayList = new ArrayList(list2.size());
        int i = 0;
        for (Object obj : list2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrTypeArgument irTypeArgument = (IrTypeArgument) obj;
            if (irTypeArgument instanceof IrTypeProjection) {
                IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
                starProjectionImpl = new TypeProjectionImpl(irTypeProjection.getVariance(), toIrBasedKotlinType(irTypeProjection.getType(), kotlinBuiltIns));
            } else {
                if (!(irTypeArgument instanceof IrStarProjection)) {
                    throw new NoWhenBranchMatchedException();
                }
                TypeParameterDescriptor typeParameterDescriptor = irBasedDescriptorIfPossible.getTypeConstructor().getParameters().get(i);
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "get(...)");
                starProjectionImpl = new StarProjectionImpl(typeParameterDescriptor);
            }
            arrayList.add(starProjectionImpl);
            i = i2;
        }
        List listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        try {
            SimpleType defaultType = irBasedDescriptorIfPossible.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            return TypeSubstitutionKt.replace$default(defaultType, listCompactIfPossible, null, 2, null).makeNullableAsSpecified(z);
        } catch (Throwable th) {
            StringBuilder sbAppend = new StringBuilder("Classifier: ").append(RenderIrElementKt.render$default(irClassSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).append("\nType parameters:\n");
            List<TypeParameterDescriptor> parameters = irBasedDescriptorIfPossible.getDefaultType().getConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            throw new RuntimeException(sbAppend.append(CollectionsKt.joinToString$default(CollectionsKt.withIndex(parameters), "\n", null, null, 0, null, new Function1<IndexedValue<? extends TypeParameterDescriptor>, CharSequence>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt.makeKotlinType.1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(IndexedValue<? extends TypeParameterDescriptor> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    TypeParameterDescriptor value = it.getValue();
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.descriptors.IrBasedTypeParameterDescriptor");
                    IrTypeParameter owner = ((IrBasedTypeParameterDescriptor) value).getOwner();
                    return it.getIndex() + ": " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null) + " of " + RenderIrElementKt.render$default(owner.getParent(), (DumpIrTreeOptions) null, 1, (Object) null);
                }
            }, 30, null)).append("\nType arguments:\n").append(CollectionsKt.joinToString$default(CollectionsKt.withIndex(list), "\n", null, null, 0, null, new Function1<IndexedValue<? extends IrTypeArgument>, CharSequence>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt.makeKotlinType.2
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(IndexedValue<? extends IrTypeArgument> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getIndex() + ": " + RenderIrElementKt.render$default(it.getValue(), (DumpIrTreeOptions) null, 1, (Object) null);
                }
            }, 30, null)).toString(), th);
        }
    }

    private static final ClassDescriptor toIrBasedDescriptorIfPossible(IrClassSymbol irClassSymbol) {
        return irClassSymbol.isBound() ? toIrBasedDescriptor(irClassSymbol.getOwner()) : irClassSymbol.getDescriptor();
    }

    private static final TypeParameterDescriptor toIrBasedDescriptorIfPossible(IrTypeParameterSymbol irTypeParameterSymbol) {
        return irTypeParameterSymbol.isBound() ? toIrBasedDescriptor(irTypeParameterSymbol.getOwner()) : irTypeParameterSymbol.getDescriptor();
    }

    private static final ScriptDescriptor toIrBasedDescriptorIfPossible(IrScriptSymbol irScriptSymbol) {
        return irScriptSymbol.getDescriptor();
    }

    private static final DeclarationDescriptor toIrBasedDescriptor(IrScript irScript) {
        return irScript.getDescriptor();
    }
}
