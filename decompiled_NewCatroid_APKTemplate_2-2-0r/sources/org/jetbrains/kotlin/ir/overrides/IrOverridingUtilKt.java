package org.jetbrains.kotlin.ir.overrides;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.linkage.partial.IrUnimplementedOverridesStrategy;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeCheckerStateKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextKt;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextWithAdditionalAxioms;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: IrOverridingUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000n\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aD\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001d2\u001a\b\u0002\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020 \u0012\n\u0012\b\u0012\u0004\u0012\u00020 0!0\u001f2\b\b\u0002\u0010\"\u001a\u00020#\u001a2\u0010$\u001a\u00020\t2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0018\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020 \u0012\n\u0012\b\u0012\u0004\u0012\u00020 0!0\u001fH\u0002\u001a*\u0010(\u001a\u00020)*\u00020*2\u0006\u0010+\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u00122\u0006\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00020\t\u001a\n\u0010/\u001a\u00020\t*\u00020\u0006\u001a\n\u00100\u001a\u00020\t*\u000201\u001a\n\u00102\u001a\u00020\t*\u00020\u0003\u001a6\u00103\u001a\u00020\t*\u00020\u00122\u0006\u00104\u001a\u00020\u001d2\u0006\u00105\u001a\u00020\u001d2\u0018\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020 \u0012\n\u0012\b\u0012\u0004\u0012\u00020 0!0\u001fH\u0002\"\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0007\"\u001a\u0010\b\u001a\u00020\t*\u0004\u0018\u00010\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u001a\u0010\f\u001a\u00020\t*\u0004\u0018\u00010\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000b\"\u0018\u0010\u000e\u001a\u00020\t*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\"\u0018\u0010\u0010\u001a\u00020\u0011*\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\"\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0001*\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018\"\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0001*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005¨\u00066"}, d2 = {"compiledValueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getCompiledValueParameters", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Ljava/util/List;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Ljava/util/List;", "hasDispatchReceiver", "", "getHasDispatchReceiver", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Z", "hasExtensionReceiver", "getHasExtensionReceiver", "isInline", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Z", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;", "getReturnType", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;)Lorg/jetbrains/kotlin/ir/types/IrType;", "typeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getTypeParameters", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;)Ljava/util/List;", "buildFakeOverrideMember", "superType", "member", "clazz", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "friendModules", "", "", "", "unimplementedOverridesStrategy", "Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy;", "isInFriendModules", "fromModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "toModule", "isOverridableByWithoutExternalConditions", "Lorg/jetbrains/kotlin/resolve/OverridingUtil$OverrideCompatibilityInfo;", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "superMember", "subMember", "checkIsInlineFlag", "checkReturnType", "isOverridableFunction", "isOverridableMemberOrAccessor", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "isOverridableProperty", "isPrivateToThisModule", "thisClass", "memberClass", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrOverridingUtilKt {
    private static final boolean isPrivateToThisModule(IrOverridableMember irOverridableMember, IrClass irClass, IrClass irClass2, Map<String, ? extends Collection<String>> map) {
        if (!Intrinsics.areEqual(irOverridableMember.getVisibility(), DescriptorVisibilities.INTERNAL)) {
            return false;
        }
        ModuleDescriptor packageFragment = IrUtilsKt.getPackageFragment((IrDeclaration) irClass).getPackageFragmentDescriptor().getPackageFragment();
        ModuleDescriptor packageFragment2 = IrUtilsKt.getPackageFragment((IrDeclaration) irClass2).getPackageFragmentDescriptor().getPackageFragment();
        return (Intrinsics.areEqual(packageFragment, packageFragment2) || isInFriendModules(packageFragment, packageFragment2, map)) ? false : true;
    }

    private static final boolean isInFriendModules(ModuleDescriptor moduleDescriptor, ModuleDescriptor moduleDescriptor2, Map<String, ? extends Collection<String>> map) {
        if (map.isEmpty()) {
            return false;
        }
        String strAsStringStripSpecialMarkers = moduleDescriptor.getShortName().asStringStripSpecialMarkers();
        Intrinsics.checkNotNullExpressionValue(strAsStringStripSpecialMarkers, "asStringStripSpecialMarkers(...)");
        Collection<String> collection = map.get(strAsStringStripSpecialMarkers);
        if (collection == null) {
            return false;
        }
        String strAsStringStripSpecialMarkers2 = moduleDescriptor2.getShortName().asStringStripSpecialMarkers();
        Intrinsics.checkNotNullExpressionValue(strAsStringStripSpecialMarkers2, "asStringStripSpecialMarkers(...)");
        return collection.contains(strAsStringStripSpecialMarkers2);
    }

    public static /* synthetic */ IrOverridableMember buildFakeOverrideMember$default(IrType irType, IrOverridableMember irOverridableMember, IrClass irClass, Map map, IrUnimplementedOverridesStrategy irUnimplementedOverridesStrategy, int i, Object obj) {
        if ((i & 8) != 0) {
            map = MapsKt.emptyMap();
        }
        if ((i & 16) != 0) {
            irUnimplementedOverridesStrategy = IrUnimplementedOverridesStrategy.ProcessAsFakeOverrides.INSTANCE;
        }
        return buildFakeOverrideMember(irType, irOverridableMember, irClass, map, irUnimplementedOverridesStrategy);
    }

    public static final IrOverridableMember buildFakeOverrideMember(IrType superType, IrOverridableMember member, IrClass clazz, Map<String, ? extends Collection<String>> friendModules, IrUnimplementedOverridesStrategy unimplementedOverridesStrategy) {
        Intrinsics.checkNotNullParameter(superType, "superType");
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(friendModules, "friendModules");
        Intrinsics.checkNotNullParameter(unimplementedOverridesStrategy, "unimplementedOverridesStrategy");
        if (!(superType instanceof IrSimpleType)) {
            throw new IllegalArgumentException(("superType is " + superType + ", expected IrSimpleType").toString());
        }
        IrSimpleType irSimpleType = (IrSimpleType) superType;
        IrClassifierSymbol classifier = irSimpleType.getClassifier();
        if (!(classifier instanceof IrClassSymbol)) {
            throw new IllegalArgumentException(("superType classifier is not IrClassSymbol: " + classifier).toString());
        }
        IrClassSymbol irClassSymbol = (IrClassSymbol) classifier;
        List<IrTypeParameter> listExtractTypeParameters = IrTypeSystemContextKt.extractTypeParameters(irClassSymbol.getOwner());
        List<IrTypeArgument> arguments = irSimpleType.getArguments();
        if (listExtractTypeParameters.size() != arguments.size()) {
            throw new IllegalArgumentException(("typeParameters = " + listExtractTypeParameters + " size != typeArguments = " + arguments + " size ").toString());
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int size = listExtractTypeParameters.size();
        for (int i = 0; i < size; i++) {
            IrTypeParameter irTypeParameter = listExtractTypeParameters.get(i);
            IrTypeArgument irTypeArgument = arguments.get(i);
            if (!(irTypeArgument instanceof IrTypeProjection)) {
                throw new IllegalArgumentException(("Unexpected super type argument: " + RenderIrElementKt.render$default(irTypeArgument, (DumpIrTreeOptions) null, 1, (Object) null) + " @ " + i).toString());
            }
            IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
            if (irTypeProjection.getVariance() != Variance.INVARIANT) {
                throw new IllegalArgumentException(("Unexpected variance in super type argument: " + irTypeProjection.getVariance() + " @" + i).toString());
            }
            linkedHashMap.put(irTypeParameter.getSymbol(), irTypeProjection.getType());
        }
        IrOverridableMember irOverridableMemberCopy = new CopyIrTreeWithSymbolsForFakeOverrides(member, linkedHashMap, clazz, unimplementedOverridesStrategy).copy();
        if (isPrivateToThisModule(irOverridableMemberCopy, clazz, irClassSymbol.getOwner(), friendModules)) {
            DescriptorVisibility INVISIBLE_FAKE = DescriptorVisibilities.INVISIBLE_FAKE;
            Intrinsics.checkNotNullExpressionValue(INVISIBLE_FAKE, "INVISIBLE_FAKE");
            irOverridableMemberCopy.setVisibility(INVISIBLE_FAKE);
        }
        return irOverridableMemberCopy;
    }

    public static final OverridingUtil.OverrideCompatibilityInfo isOverridableByWithoutExternalConditions(IrTypeSystemContext irTypeSystemContext, IrOverridableMember superMember, IrOverridableMember subMember, boolean z, boolean z2) {
        List<IrTypeParameter> typeParameters;
        List<IrTypeParameter> typeParameters2;
        List<IrValueParameter> compiledValueParameters;
        List<IrValueParameter> compiledValueParameters2;
        Intrinsics.checkNotNullParameter(irTypeSystemContext, "<this>");
        Intrinsics.checkNotNullParameter(superMember, "superMember");
        Intrinsics.checkNotNullParameter(subMember, "subMember");
        if (superMember instanceof IrSimpleFunction) {
            if (!(subMember instanceof IrSimpleFunction)) {
                OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIncompatible = OverridingUtil.OverrideCompatibilityInfo.incompatible("Member kind mismatch");
                Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoIncompatible, "incompatible(...)");
                return overrideCompatibilityInfoIncompatible;
            }
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) superMember;
            IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) subMember;
            if (getHasExtensionReceiver(irSimpleFunction) != getHasExtensionReceiver(irSimpleFunction2)) {
                OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIncompatible2 = OverridingUtil.OverrideCompatibilityInfo.incompatible("Receiver presence mismatch");
                Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoIncompatible2, "incompatible(...)");
                return overrideCompatibilityInfoIncompatible2;
            }
            if (irSimpleFunction.getIsSuspend() != irSimpleFunction2.getIsSuspend()) {
                OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIncompatible3 = OverridingUtil.OverrideCompatibilityInfo.incompatible("Incompatible suspendability");
                Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoIncompatible3, "incompatible(...)");
                return overrideCompatibilityInfoIncompatible3;
            }
            if (z && irSimpleFunction.getIsInline()) {
                OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIncompatible4 = OverridingUtil.OverrideCompatibilityInfo.incompatible("Inline function can't be overridden");
                Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoIncompatible4, "incompatible(...)");
                return overrideCompatibilityInfoIncompatible4;
            }
            typeParameters = irSimpleFunction.getTypeParameters();
            typeParameters2 = irSimpleFunction2.getTypeParameters();
            compiledValueParameters = getCompiledValueParameters(irSimpleFunction);
            compiledValueParameters2 = getCompiledValueParameters(irSimpleFunction2);
        } else if (superMember instanceof IrProperty) {
            if (!(subMember instanceof IrProperty)) {
                OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIncompatible5 = OverridingUtil.OverrideCompatibilityInfo.incompatible("Member kind mismatch");
                Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoIncompatible5, "incompatible(...)");
                return overrideCompatibilityInfoIncompatible5;
            }
            IrProperty irProperty = (IrProperty) superMember;
            IrProperty irProperty2 = (IrProperty) subMember;
            if (getHasExtensionReceiver(irProperty.getGetter()) != getHasExtensionReceiver(irProperty2.getGetter())) {
                OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIncompatible6 = OverridingUtil.OverrideCompatibilityInfo.incompatible("Receiver presence mismatch");
                Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoIncompatible6, "incompatible(...)");
                return overrideCompatibilityInfoIncompatible6;
            }
            if (z && isInline(irProperty)) {
                OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIncompatible7 = OverridingUtil.OverrideCompatibilityInfo.incompatible("Inline property can't be overridden");
                Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoIncompatible7, "incompatible(...)");
                return overrideCompatibilityInfoIncompatible7;
            }
            typeParameters = getTypeParameters(irProperty);
            typeParameters2 = getTypeParameters(irProperty2);
            compiledValueParameters = getCompiledValueParameters(irProperty);
            compiledValueParameters2 = getCompiledValueParameters(irProperty2);
        } else {
            throw new IllegalStateException(("Unexpected type of declaration: " + superMember.getClass() + ", " + superMember).toString());
        }
        if (!Intrinsics.areEqual(superMember.getName(), subMember.getName())) {
            OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIncompatible8 = OverridingUtil.OverrideCompatibilityInfo.incompatible("Name mismatch");
            Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoIncompatible8, "incompatible(...)");
            return overrideCompatibilityInfoIncompatible8;
        }
        if (typeParameters.size() != typeParameters2.size()) {
            OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIncompatible9 = OverridingUtil.OverrideCompatibilityInfo.incompatible("Type parameter number mismatch");
            Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoIncompatible9, "incompatible(...)");
            return overrideCompatibilityInfoIncompatible9;
        }
        if (compiledValueParameters.size() != compiledValueParameters2.size()) {
            OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIncompatible10 = OverridingUtil.OverrideCompatibilityInfo.incompatible("Value parameter number mismatch");
            Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoIncompatible10, "incompatible(...)");
            return overrideCompatibilityInfoIncompatible10;
        }
        TypeCheckerState typeCheckerStateCreateIrTypeCheckerState = IrTypeCheckerStateKt.createIrTypeCheckerState(new IrTypeSystemContextWithAdditionalAxioms(irTypeSystemContext, typeParameters, typeParameters2));
        int i = 0;
        for (Object obj : compiledValueParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            if (!AbstractTypeChecker.INSTANCE.equalTypes(typeCheckerStateCreateIrTypeCheckerState, compiledValueParameters2.get(i).getType(), ((IrValueParameter) obj).getType())) {
                OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIncompatible11 = OverridingUtil.OverrideCompatibilityInfo.incompatible("Value parameter type mismatch");
                Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoIncompatible11, "incompatible(...)");
                return overrideCompatibilityInfoIncompatible11;
            }
            i = i2;
        }
        if (z2 && !AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, typeCheckerStateCreateIrTypeCheckerState, (KotlinTypeMarker) getReturnType(subMember), (KotlinTypeMarker) getReturnType(superMember), false, 8, (Object) null)) {
            OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoConflict = OverridingUtil.OverrideCompatibilityInfo.conflict("Return type mismatch");
            Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoConflict, "conflict(...)");
            return overrideCompatibilityInfoConflict;
        }
        OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoSuccess = OverridingUtil.OverrideCompatibilityInfo.success();
        Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfoSuccess, "success(...)");
        return overrideCompatibilityInfoSuccess;
    }

    private static final boolean getHasExtensionReceiver(IrSimpleFunction irSimpleFunction) {
        return (irSimpleFunction != null ? irSimpleFunction.getExtensionReceiverParameter() : null) != null;
    }

    private static final boolean getHasDispatchReceiver(IrSimpleFunction irSimpleFunction) {
        return (irSimpleFunction != null ? irSimpleFunction.getDispatchReceiverParameter() : null) != null;
    }

    private static final List<IrValueParameter> getCompiledValueParameters(IrSimpleFunction irSimpleFunction) {
        ArrayList arrayList = new ArrayList(irSimpleFunction.getValueParameters().size() + 1);
        IrValueParameter extensionReceiverParameter = irSimpleFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            arrayList.add(extensionReceiverParameter);
        }
        arrayList.addAll(irSimpleFunction.getValueParameters());
        return arrayList;
    }

    private static final List<IrValueParameter> getCompiledValueParameters(IrProperty irProperty) {
        IrValueParameter extensionReceiverParameter;
        IrSimpleFunction getter = irProperty.getGetter();
        List<IrValueParameter> listListOf = (getter == null || (extensionReceiverParameter = getter.getExtensionReceiverParameter()) == null) ? null : CollectionsKt.listOf(extensionReceiverParameter);
        return listListOf == null ? CollectionsKt.emptyList() : listListOf;
    }

    private static final List<IrTypeParameter> getTypeParameters(IrProperty irProperty) {
        IrSimpleFunction getter = irProperty.getGetter();
        List<IrTypeParameter> typeParameters = getter != null ? getter.getTypeParameters() : null;
        return typeParameters == null ? CollectionsKt.emptyList() : typeParameters;
    }

    private static final boolean isInline(IrProperty irProperty) {
        IrSimpleFunction getter = irProperty.getGetter();
        if (getter != null && getter.getIsInline()) {
            return true;
        }
        IrSimpleFunction setter = irProperty.getSetter();
        return setter != null && setter.getIsInline();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<IrTypeParameter> getTypeParameters(IrOverridableMember irOverridableMember) {
        if (irOverridableMember instanceof IrSimpleFunction) {
            return ((IrSimpleFunction) irOverridableMember).getTypeParameters();
        }
        if (!(irOverridableMember instanceof IrProperty)) {
            throw new IllegalStateException(("Unexpected type of declaration: " + irOverridableMember.getClass() + ", " + irOverridableMember).toString());
        }
        IrSimpleFunction getter = ((IrProperty) irOverridableMember).getGetter();
        List<IrTypeParameter> typeParameters = getter != null ? getter.getTypeParameters() : null;
        return typeParameters == null ? CollectionsKt.emptyList() : typeParameters;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrType getReturnType(IrOverridableMember irOverridableMember) {
        if (irOverridableMember instanceof IrSimpleFunction) {
            return ((IrSimpleFunction) irOverridableMember).getReturnType();
        }
        if (!(irOverridableMember instanceof IrProperty)) {
            throw new IllegalStateException(("Unexpected type of declaration: " + irOverridableMember.getClass() + ", " + irOverridableMember).toString());
        }
        IrSimpleFunction getter = ((IrProperty) irOverridableMember).getGetter();
        Intrinsics.checkNotNull(getter);
        return getter.getReturnType();
    }

    public static final boolean isOverridableFunction(IrSimpleFunction irSimpleFunction) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        return !Intrinsics.areEqual(irSimpleFunction.getVisibility(), DescriptorVisibilities.PRIVATE) && getHasDispatchReceiver(irSimpleFunction);
    }

    public static final boolean isOverridableProperty(IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        return !Intrinsics.areEqual(irProperty.getVisibility(), DescriptorVisibilities.PRIVATE) && (getHasDispatchReceiver(irProperty.getGetter()) || getHasDispatchReceiver(irProperty.getSetter()));
    }

    public static final boolean isOverridableMemberOrAccessor(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (irDeclaration instanceof IrSimpleFunction) {
            return isOverridableFunction((IrSimpleFunction) irDeclaration);
        }
        if (irDeclaration instanceof IrProperty) {
            return isOverridableProperty((IrProperty) irDeclaration);
        }
        return false;
    }
}
