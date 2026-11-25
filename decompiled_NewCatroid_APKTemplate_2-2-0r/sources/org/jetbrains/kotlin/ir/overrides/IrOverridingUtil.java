package org.jetbrains.kotlin.ir.overrides;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrFunctionWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrPropertyWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeCheckerStateKt;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextWithAdditionalAxioms;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: IrOverridingUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u001f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\tH\u0002J\u0016\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\rJ<\u0010\"\u001a\b\u0012\u0004\u0012\u00020\t0\u00132\u0006\u0010\u001f\u001a\u00020 2\u000e\b\u0002\u0010#\u001a\b\u0012\u0004\u0012\u00020\t0\u00132\u0006\u0010$\u001a\u00020\r2\u000e\b\u0002\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013J\u0006\u0010&\u001a\u00020\u001eJ\u001e\u0010'\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020\t2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\t0*H\u0002J4\u0010+\u001a\u00020\u001e2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\t0-2\u0006\u0010.\u001a\u00020 2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\t002\u0006\u0010$\u001a\u00020\rH\u0002J4\u00101\u001a\u00020\u001e2\u0006\u00102\u001a\u00020 2\f\u00103\u001a\b\u0012\u0004\u0012\u00020\t0-2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\t002\u0006\u0010$\u001a\u00020\rH\u0002J$\u00104\u001a\u0002052\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002060\u00132\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002060\u0013H\u0002J\u001e\u00107\u001a\u0002082\f\u00109\u001a\b\u0012\u0004\u0012\u00020\t0-2\u0006\u00102\u001a\u00020 H\u0002J$\u0010:\u001a\b\u0012\u0004\u0012\u00020\t0-2\u0006\u0010;\u001a\u00020\t2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\t0-H\u0002J$\u0010=\u001a\b\u0012\u0004\u0012\u00020\t0-2\u0006\u0010>\u001a\u00020\t2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020\t0@H\u0002J\u001c\u0010A\u001a\b\u0012\u0004\u0012\u00020\t0-2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\t0-H\u0002J\u001c\u0010C\u001a\b\u0012\u0004\u0012\u00020\t0-2\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\t0-H\u0002J4\u0010E\u001a\u00020\u001e2\f\u0010F\u001a\b\u0012\u0004\u0012\u00020\t0\u00132\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\t0\u00132\u0006\u00102\u001a\u00020 2\u0006\u0010$\u001a\u00020\rH\u0002J\u0018\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020\t2\u0006\u0010K\u001a\u00020\tH\u0002J&\u0010L\u001a\u0002082\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\t0-2\u0006\u0010N\u001a\u00020\r2\u0006\u0010O\u001a\u000208H\u0002J\u0016\u0010P\u001a\b\u0012\u0004\u0012\u00020\t0Q2\u0006\u0010(\u001a\u00020\tH\u0002J\u001c\u0010R\u001a\u00020\r2\b\u0010\u001b\u001a\u0004\u0018\u00010S2\b\u0010\u001c\u001a\u0004\u0018\u00010SH\u0002J\u0018\u0010T\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\tH\u0002J\u001e\u0010U\u001a\u00020\r2\u0006\u0010V\u001a\u00020\t2\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\t0-H\u0002J(\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020\t2\u0006\u0010Z\u001a\u00020\t2\u0006\u0010[\u001a\u00020\r2\u0006\u0010\\\u001a\u00020\rH\u0002J(\u0010]\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010^\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010_\u001a\u00020\nH\u0002J\u0018\u0010`\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\tH\u0002J\u0016\u0010a\u001a\u00020\r2\u0006\u0010b\u001a\u00020\t2\u0006\u0010c\u001a\u00020\tJ\u0016\u0010d\u001a\u00020\t2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\t0-H\u0002J\u001c\u0010e\u001a\u00020\r*\u0002052\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\nH\u0002J\u001c\u0010f\u001a\u00020\r*\u0002052\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\nH\u0002J\u001e\u0010g\u001a\u0004\u0018\u00010S*\u00020S2\u0006\u0010h\u001a\u0002082\u0006\u0010i\u001a\u00020jH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\f\u001a\u00020\r*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0018\u0010\u000f\u001a\u00020\t*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R4\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013*\u00020\t2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006k"}, d2 = {"Lorg/jetbrains/kotlin/ir/overrides/IrOverridingUtil;", "", "typeSystem", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "fakeOverrideBuilder", "Lorg/jetbrains/kotlin/ir/overrides/FakeOverrideBuilderStrategy;", "(Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;Lorg/jetbrains/kotlin/ir/overrides/FakeOverrideBuilderStrategy;)V", "originalSuperTypes", "", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "originals", "isStaticMember", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;)Z", "original", "getOriginal", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;)Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;", "value", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "overriddenSymbols", "getOverriddenSymbols", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;)Ljava/util/List;", "setOverriddenSymbols", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;Ljava/util/List;)V", "areEquivalent", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "buildFakeOverridesForClass", "", "clazz", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "oldSignatures", "buildFakeOverridesForClassUsingOverriddenSymbols", "implementedMembers", "compatibilityMode", "ignoredParentSymbols", LineReader.CLEAR, "collectOverriddenDeclarations", "member", "result", "", "createAndBindFakeOverride", "overridables", "", "currentClass", "addedFakeOverrides", "", "createAndBindFakeOverrides", "current", "notOverridden", "createTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "determineModalityForFakeOverride", "Lorg/jetbrains/kotlin/descriptors/Modality;", "members", "extractAndBindOverridesForMember", "fromCurrent", "descriptorsFromSuper", "extractMembersOverridableInBothWays", "overrider", "extractFrom", "", "filterOutCustomizedFakeOverrides", "overridableMembers", "filterVisibleFakeOverrides", "toFilter", "generateOverridesInFunctionGroup", "membersFromSupertypes", "membersFromCurrent", "getBothWaysOverridability", "Lorg/jetbrains/kotlin/resolve/OverridingUtil$OverrideCompatibilityInfo$Result;", "overriderDescriptor", "candidateDescriptor", "getMinimalModality", "descriptors", "transformAbstractToClassModality", "classModality", "getOverriddenDeclarations", "", "isAccessorMoreSpecific", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isMoreSpecific", "isMoreSpecificThenAllOf", "candidate", "isOverridableBy", "Lorg/jetbrains/kotlin/resolve/OverridingUtil$OverrideCompatibilityInfo;", "superMember", "subMember", "checkIsInlineFlag", "checkReturnType", "isReturnTypeMoreSpecific", "aReturnType", "bReturnType", "isVisibilityMoreSpecific", "overrides", "f", "g", "selectMostSpecificMember", "equalTypes", "isSubtypeOf", "updateAccessorModalityAndVisibility", "newModality", "newVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrOverridingUtil {
    private final FakeOverrideBuilderStrategy fakeOverrideBuilder;
    private final Map<IrOverridableMember, IrType> originalSuperTypes;
    private final Map<IrOverridableMember, IrOverridableMember> originals;
    private final IrTypeSystemContext typeSystem;

    /* JADX INFO: compiled from: IrOverridingUtil.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[OverridingUtil.OverrideCompatibilityInfo.Result.values().length];
            try {
                iArr[OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[OverridingUtil.OverrideCompatibilityInfo.Result.CONFLICT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[OverridingUtil.OverrideCompatibilityInfo.Result.INCOMPATIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Modality.values().length];
            try {
                iArr2[Modality.FINAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[Modality.SEALED.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[Modality.OPEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[Modality.ABSTRACT.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    private final IrOverridableMember getOriginal(IrOverridableMember irOverridableMember) {
        IrOverridableMember irOverridableMember2 = this.originals.get(irOverridableMember);
        if (irOverridableMember2 != null) {
            return irOverridableMember2;
        }
        throw new IllegalStateException(("No original for " + RenderIrElementKt.render$default(irOverridableMember, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    public IrOverridingUtil(IrTypeSystemContext typeSystem, FakeOverrideBuilderStrategy fakeOverrideBuilder) {
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        Intrinsics.checkNotNullParameter(fakeOverrideBuilder, "fakeOverrideBuilder");
        this.typeSystem = typeSystem;
        this.fakeOverrideBuilder = fakeOverrideBuilder;
        this.originals = new LinkedHashMap();
        this.originalSuperTypes = new LinkedHashMap();
    }

    public final void clear() {
        this.originals.clear();
        this.originalSuperTypes.clear();
    }

    private final List<IrSymbol> getOverriddenSymbols(IrOverridableMember irOverridableMember) {
        if (irOverridableMember instanceof IrSimpleFunction) {
            return ((IrSimpleFunction) irOverridableMember).getOverriddenSymbols();
        }
        if (irOverridableMember instanceof IrProperty) {
            return ((IrProperty) irOverridableMember).getOverriddenSymbols();
        }
        throw new IllegalStateException(("Unexpected declaration for overriddenSymbols: " + irOverridableMember).toString());
    }

    private final void setOverriddenSymbols(IrOverridableMember irOverridableMember, List<? extends IrSymbol> list) {
        if (irOverridableMember instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irOverridableMember;
            List<? extends IrSymbol> list2 = list;
            ArrayList arrayList = new ArrayList(list2.size());
            for (IrSymbol irSymbol : list2) {
                IrSimpleFunctionSymbol irSimpleFunctionSymbol = irSymbol instanceof IrSimpleFunctionSymbol ? (IrSimpleFunctionSymbol) irSymbol : null;
                if (irSimpleFunctionSymbol == null) {
                    throw new IllegalStateException(("Unexpected function overridden symbol: " + irSymbol).toString());
                }
                arrayList.add(irSimpleFunctionSymbol);
            }
            irSimpleFunction.setOverriddenSymbols(CollectionsKt.compactIfPossible(arrayList));
            return;
        }
        if (irOverridableMember instanceof IrProperty) {
            List<? extends IrSymbol> list3 = list;
            ArrayList arrayList2 = new ArrayList(list3.size());
            for (IrSymbol irSymbol2 : list3) {
                IrPropertySymbol irPropertySymbol = irSymbol2 instanceof IrPropertySymbol ? (IrPropertySymbol) irSymbol2 : null;
                if (irPropertySymbol == null) {
                    throw new IllegalStateException(("Unexpected property overridden symbol: " + irSymbol2).toString());
                }
                arrayList2.add(irPropertySymbol);
            }
            List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList2);
            IrProperty irProperty = (IrProperty) irOverridableMember;
            IrSimpleFunction getter = irProperty.getGetter();
            if (getter == null) {
                throw new IllegalStateException(("Property has no getter: " + RenderIrElementKt.render$default(irOverridableMember, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            List list4 = listCompactIfPossible;
            List list5 = list4;
            ArrayList arrayList3 = new ArrayList(list4.size());
            Iterator it = list5.iterator();
            while (it.hasNext()) {
                IrSimpleFunction getter2 = ((IrPropertySymbol) it.next()).getOwner().getGetter();
                Intrinsics.checkNotNull(getter2);
                arrayList3.add(getter2.getSymbol());
            }
            getter.setOverriddenSymbols(CollectionsKt.compactIfPossible(arrayList3));
            IrSimpleFunction setter = irProperty.getSetter();
            if (setter != null) {
                ArrayList arrayList4 = new ArrayList();
                Iterator it2 = list5.iterator();
                while (it2.hasNext()) {
                    IrSimpleFunction setter2 = ((IrPropertySymbol) it2.next()).getOwner().getSetter();
                    IrSimpleFunctionSymbol symbol = setter2 != null ? setter2.getSymbol() : null;
                    if (symbol != null) {
                        arrayList4.add(symbol);
                    }
                }
                setter.setOverriddenSymbols(CollectionsKt.compactIfPossible(arrayList4));
            }
            irProperty.setOverriddenSymbols(listCompactIfPossible);
            return;
        }
        throw new IllegalStateException(("Unexpected declaration for overriddenSymbols: " + irOverridableMember).toString());
    }

    public final void buildFakeOverridesForClass(IrClass clazz, boolean oldSignatures) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        List<IrType> superTypes = clazz.getSuperTypes();
        List<IrDeclaration> declarations = clazz.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrOverridableMember) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (IrType irType : superTypes) {
            IrClass irClass = IrTypesKt.getClass(irType);
            if (irClass == null) {
                throw new IllegalStateException(("Unexpected super type: " + irType).toString());
            }
            List<IrDeclaration> declarations2 = irClass.getDeclarations();
            ArrayList arrayList4 = new ArrayList();
            for (Object obj2 : declarations2) {
                if (IrOverridingUtilKt.isOverridableMemberOrAccessor((IrDeclaration) obj2)) {
                    arrayList4.add(obj2);
                }
            }
            ArrayList<IrDeclaration> arrayList5 = arrayList4;
            ArrayList arrayList6 = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
            for (IrDeclaration irDeclaration : arrayList5) {
                Intrinsics.checkNotNull(irDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrOverridableMember");
                IrOverridableMember irOverridableMember = (IrOverridableMember) irDeclaration;
                IrOverridableMember irOverridableMemberFakeOverrideMember = this.fakeOverrideBuilder.fakeOverrideMember(irType, irOverridableMember, clazz);
                this.originals.put(irOverridableMemberFakeOverrideMember, irOverridableMember);
                this.originalSuperTypes.put(irOverridableMemberFakeOverrideMember, irType);
                arrayList6.add(irOverridableMemberFakeOverrideMember);
            }
            kotlin.collections.CollectionsKt.addAll(arrayList3, arrayList6);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj3 : arrayList3) {
            Name name2 = ((IrOverridableMember) obj3).getName();
            Object obj4 = linkedHashMap.get(name2);
            if (obj4 == null) {
                obj4 = (List) new ArrayList();
                linkedHashMap.put(name2, obj4);
            }
            ((List) obj4).add(obj3);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            List<? extends IrOverridableMember> list = (List) entry.getValue();
            ArrayList arrayList7 = new ArrayList();
            for (Object obj5 : arrayList2) {
                IrOverridableMember irOverridableMember2 = (IrOverridableMember) obj5;
                if (Intrinsics.areEqual(irOverridableMember2.getName(), entry.getKey()) && !isStaticMember(irOverridableMember2)) {
                    arrayList7.add(obj5);
                }
            }
            generateOverridesInFunctionGroup(list, arrayList7, clazz, oldSignatures);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ List buildFakeOverridesForClassUsingOverriddenSymbols$default(IrOverridingUtil irOverridingUtil, IrClass irClass, List list, boolean z, List list2, int i, Object obj) {
        if ((i & 2) != 0) {
            list = kotlin.collections.CollectionsKt.emptyList();
        }
        if ((i & 8) != 0) {
            list2 = kotlin.collections.CollectionsKt.emptyList();
        }
        return irOverridingUtil.buildFakeOverridesForClassUsingOverriddenSymbols(irClass, list, z, list2);
    }

    public final List<IrOverridableMember> buildFakeOverridesForClassUsingOverriddenSymbols(IrClass clazz, List<? extends IrOverridableMember> implementedMembers, boolean compatibilityMode, List<? extends IrSymbol> ignoredParentSymbols) {
        SmartList smartListEmptyList;
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(implementedMembers, "implementedMembers");
        Intrinsics.checkNotNullParameter(ignoredParentSymbols, "ignoredParentSymbols");
        List<IrDeclaration> declarations = clazz.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrOverridableMember) {
                arrayList.add(obj);
            }
        }
        List listPlus = kotlin.collections.CollectionsKt.plus((Collection) arrayList, (Iterable) implementedMembers);
        ArrayList arrayList2 = new ArrayList();
        Iterator it = listPlus.iterator();
        while (it.hasNext()) {
            List<IrSymbol> overriddenSymbols = getOverriddenSymbols((IrOverridableMember) it.next());
            ArrayList arrayList3 = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(overriddenSymbols, 10));
            Iterator<T> it2 = overriddenSymbols.iterator();
            while (it2.hasNext()) {
                arrayList3.add(((IrSymbol) it2.next()).getOwner());
            }
            kotlin.collections.CollectionsKt.addAll(arrayList2, arrayList3);
        }
        Set set = kotlin.collections.CollectionsKt.toSet(arrayList2);
        List<IrType> superTypes = clazz.getSuperTypes();
        ArrayList arrayList4 = new ArrayList();
        for (IrType irType : superTypes) {
            IrClass irClass = IrTypesKt.getClass(irType);
            if (irClass == null) {
                throw new IllegalStateException(("Unexpected super type: " + irType).toString());
            }
            List<IrDeclaration> declarations2 = irClass.getDeclarations();
            if (declarations2.isEmpty()) {
                smartListEmptyList = kotlin.collections.CollectionsKt.emptyList();
            } else {
                SmartList smartList = new SmartList();
                for (Object obj2 : declarations2) {
                    if (obj2 instanceof IrOverridableMember) {
                        IrOverridableMember irOverridableMember = (IrOverridableMember) obj2;
                        if (!set.contains(irOverridableMember) && !ignoredParentSymbols.contains(irOverridableMember.getSymbol()) && !isStaticMember(irOverridableMember) && !DescriptorVisibilities.isPrivate(irOverridableMember.getVisibility())) {
                            smartList.add(obj2);
                        }
                    }
                }
                smartListEmptyList = smartList;
            }
            Iterable<IrOverridableMember> iterable = smartListEmptyList;
            ArrayList arrayList5 = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(iterable, 10));
            for (IrOverridableMember irOverridableMember2 : iterable) {
                IrOverridableMember irOverridableMemberFakeOverrideMember = this.fakeOverrideBuilder.fakeOverrideMember(irType, irOverridableMember2, clazz);
                this.originals.put(irOverridableMemberFakeOverrideMember, irOverridableMember2);
                this.originalSuperTypes.put(irOverridableMemberFakeOverrideMember, irType);
                arrayList5.add(irOverridableMemberFakeOverrideMember);
            }
            kotlin.collections.CollectionsKt.addAll(arrayList4, arrayList5);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj3 : arrayList4) {
            Name name2 = ((IrOverridableMember) obj3).getName();
            Object obj4 = linkedHashMap.get(name2);
            if (obj4 == null) {
                obj4 = (List) new ArrayList();
                linkedHashMap.put(name2, obj4);
            }
            ((List) obj4).add(obj3);
        }
        ArrayList arrayList6 = new ArrayList();
        Iterator it3 = linkedHashMap.values().iterator();
        while (it3.hasNext()) {
            createAndBindFakeOverrides(clazz, (List) it3.next(), arrayList6, compatibilityMode);
        }
        return arrayList6;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean isStaticMember(IrOverridableMember irOverridableMember) {
        IrSimpleFunction getter;
        if (irOverridableMember instanceof IrFunction) {
            if (((IrFunction) irOverridableMember).getDispatchReceiverParameter() != null) {
                return false;
            }
        } else if (irOverridableMember instanceof IrProperty) {
            IrProperty irProperty = (IrProperty) irOverridableMember;
            IrField backingField = irProperty.getBackingField();
            if ((backingField == null || !backingField.getIsStatic()) && ((getter = irProperty.getGetter()) == null || getter.getDispatchReceiverParameter() != null)) {
                return false;
            }
        } else {
            throw new IllegalStateException(("Unknown overridable member: " + RenderIrElementKt.render$default(irOverridableMember, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        return true;
    }

    private final void generateOverridesInFunctionGroup(List<? extends IrOverridableMember> membersFromSupertypes, List<? extends IrOverridableMember> membersFromCurrent, IrClass current, boolean compatibilityMode) {
        Set mutableSet = kotlin.collections.CollectionsKt.toMutableSet(membersFromSupertypes);
        Iterator<? extends IrOverridableMember> it = membersFromCurrent.iterator();
        while (it.hasNext()) {
            kotlin.collections.CollectionsKt.removeAll((Collection) mutableSet, (Iterable) extractAndBindOverridesForMember(it.next(), membersFromSupertypes));
        }
        ArrayList arrayList = new ArrayList();
        createAndBindFakeOverrides(current, mutableSet, arrayList, compatibilityMode);
        current.getDeclarations().addAll(arrayList);
    }

    private final Collection<IrOverridableMember> extractAndBindOverridesForMember(IrOverridableMember fromCurrent, Collection<? extends IrOverridableMember> descriptorsFromSuper) {
        ArrayList arrayList = new ArrayList(descriptorsFromSuper.size());
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (IrOverridableMember irOverridableMember : descriptorsFromSuper) {
            int i = WhenMappings.$EnumSwitchMapping$0[isOverridableBy(irOverridableMember, fromCurrent, true, false).getResult().ordinal()];
            if (i == 1) {
                if (IrVisibilityUtilKt.isVisibleForOverride(fromCurrent, getOriginal(irOverridableMember))) {
                    linkedHashSet.add(irOverridableMember);
                }
                arrayList.add(irOverridableMember);
            } else if (i == 2) {
                arrayList.add(irOverridableMember);
            }
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        ArrayList arrayList2 = new ArrayList(linkedHashSet2.size());
        Iterator it = linkedHashSet2.iterator();
        while (it.hasNext()) {
            arrayList2.add(getOriginal((IrOverridableMember) it.next()).getSymbol());
        }
        setOverriddenSymbols(fromCurrent, CollectionsKt.compactIfPossible(arrayList2));
        return arrayList;
    }

    private final void createAndBindFakeOverrides(IrClass current, Collection<? extends IrOverridableMember> notOverridden, List<IrOverridableMember> addedFakeOverrides, boolean compatibilityMode) {
        Set mutableSet = kotlin.collections.CollectionsKt.toMutableSet(notOverridden);
        while (true) {
            Set set = mutableSet;
            if (set.isEmpty()) {
                return;
            } else {
                createAndBindFakeOverride(extractMembersOverridableInBothWays(IrVisibilityUtilKt.findMemberWithMaxVisibility(filterOutCustomizedFakeOverrides(set)), set), current, addedFakeOverrides, compatibilityMode);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Collection<IrOverridableMember> filterOutCustomizedFakeOverrides(Collection<? extends IrOverridableMember> overridableMembers) {
        if (overridableMembers.size() < 2) {
            return overridableMembers;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : overridableMembers) {
            if (Intrinsics.areEqual(((IrOverridableMember) obj).getOrigin(), IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE)) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list = (List) pair.component1();
        List list2 = (List) pair.component2();
        List list3 = list;
        if (!list3.isEmpty()) {
            list2 = list3;
        }
        return list2;
    }

    private final Collection<IrOverridableMember> filterVisibleFakeOverrides(Collection<? extends IrOverridableMember> toFilter) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : toFilter) {
            IrOverridableMember irOverridableMember = (IrOverridableMember) obj;
            if (!DescriptorVisibilities.isPrivate(irOverridableMember.getVisibility()) && !Intrinsics.areEqual(irOverridableMember.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private final Modality determineModalityForFakeOverride(Collection<? extends IrOverridableMember> members, IrClass current) {
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        for (IrOverridableMember irOverridableMember : members) {
            int i = WhenMappings.$EnumSwitchMapping$1[irOverridableMember.getModality().ordinal()];
            if (i == 1) {
                return Modality.FINAL;
            }
            if (i == 2) {
                throw new IllegalStateException("Member cannot have SEALED modality: " + irOverridableMember);
            }
            if (i == 3) {
                z2 = true;
            } else if (i == 4) {
                z3 = true;
            }
        }
        if (current.getIsExpect() && current.getModality() != Modality.ABSTRACT && current.getModality() != Modality.SEALED) {
            z = true;
        }
        if (z2 && !z3) {
            return Modality.OPEN;
        }
        if (!z2 && z3) {
            return z ? current.getModality() : Modality.ABSTRACT;
        }
        Collection<? extends IrOverridableMember> collection = members;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            IrOverridableMember irOverridableMember2 = this.originals.get((IrOverridableMember) it.next());
            Intrinsics.checkNotNull(irOverridableMember2);
            arrayList.add(irOverridableMember2);
        }
        return getMinimalModality(IrFakeOverrideUtilsKt.collectAndFilterRealOverrides(arrayList), z, current.getModality());
    }

    private final boolean areEquivalent(IrOverridableMember a, IrOverridableMember b) {
        return Intrinsics.areEqual(a, b);
    }

    public final boolean overrides(IrOverridableMember f, IrOverridableMember g) {
        Intrinsics.checkNotNullParameter(f, "f");
        Intrinsics.checkNotNullParameter(g, "g");
        if (!Intrinsics.areEqual(f, g) && areEquivalent(getOriginal(f), getOriginal(g))) {
            return true;
        }
        Iterator<IrOverridableMember> it = getOverriddenDeclarations(f).iterator();
        while (it.hasNext()) {
            if (areEquivalent(getOriginal(g), it.next())) {
                return true;
            }
        }
        return false;
    }

    private final Set<IrOverridableMember> getOverriddenDeclarations(IrOverridableMember member) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        collectOverriddenDeclarations(getOriginal(member), linkedHashSet);
        return linkedHashSet;
    }

    private final void collectOverriddenDeclarations(IrOverridableMember member, Set<IrOverridableMember> result) {
        if (IrFakeOverrideUtilsKt.isReal(member)) {
            result.add(member);
            return;
        }
        if (getOverriddenSymbols(member).isEmpty()) {
            throw new IllegalStateException(("No overridden descriptors found for (fake override) " + member).toString());
        }
        List<IrSymbol> overriddenSymbols = getOverriddenSymbols(getOriginal(member));
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(overriddenSymbols, 10));
        Iterator<T> it = overriddenSymbols.iterator();
        while (it.hasNext()) {
            IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrOverridableMember");
            arrayList.add((IrOverridableMember) owner);
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            IrOverridableMember original = getOriginal((IrOverridableMember) it2.next());
            collectOverriddenDeclarations(original, result);
            result.add(original);
        }
    }

    private final Modality getMinimalModality(Collection<? extends IrOverridableMember> descriptors, boolean transformAbstractToClassModality, Modality classModality) {
        Modality modality = Modality.ABSTRACT;
        for (IrOverridableMember irOverridableMember : descriptors) {
            Modality modality2 = (transformAbstractToClassModality && irOverridableMember.getModality() == Modality.ABSTRACT) ? classModality : irOverridableMember.getModality();
            if (modality2.compareTo(modality) < 0) {
                modality = modality2;
            }
        }
        return modality;
    }

    private final IrSimpleFunction updateAccessorModalityAndVisibility(IrSimpleFunction irSimpleFunction, Modality modality, DescriptorVisibility descriptorVisibility) {
        if (!(irSimpleFunction instanceof IrFunctionWithLateBinding)) {
            throw new IllegalArgumentException(("Unexpected fake override accessor kind: " + irSimpleFunction).toString());
        }
        if (Intrinsics.areEqual(irSimpleFunction.getVisibility(), DescriptorVisibilities.PRIVATE)) {
            return null;
        }
        irSimpleFunction.setVisibility(descriptorVisibility);
        irSimpleFunction.setModality(modality);
        return irSimpleFunction;
    }

    private final void createAndBindFakeOverride(Collection<? extends IrOverridableMember> overridables, IrClass currentClass, List<IrOverridableMember> addedFakeOverrides, boolean compatibilityMode) {
        Collection<IrOverridableMember> collectionFilterVisibleFakeOverrides = filterVisibleFakeOverrides(overridables);
        if (collectionFilterVisibleFakeOverrides.isEmpty()) {
            return;
        }
        Modality modalityDetermineModalityForFakeOverride = determineModalityForFakeOverride(collectionFilterVisibleFakeOverrides, currentClass);
        DescriptorVisibility visibility = IrVisibilityUtilKt.findMemberWithMaxVisibility(collectionFilterVisibleFakeOverrides).getVisibility();
        IrOverridableMember irOverridableMemberSelectMostSpecificMember = selectMostSpecificMember(collectionFilterVisibleFakeOverrides);
        if (irOverridableMemberSelectMostSpecificMember instanceof IrPropertyWithLateBinding) {
            irOverridableMemberSelectMostSpecificMember.setVisibility(visibility);
            irOverridableMemberSelectMostSpecificMember.setModality(modalityDetermineModalityForFakeOverride);
            IrPropertyWithLateBinding irPropertyWithLateBinding = (IrPropertyWithLateBinding) irOverridableMemberSelectMostSpecificMember;
            IrSimpleFunction getter = irPropertyWithLateBinding.getGetter();
            irPropertyWithLateBinding.setGetter(getter != null ? updateAccessorModalityAndVisibility(getter, modalityDetermineModalityForFakeOverride, visibility) : null);
            IrSimpleFunction setter = irPropertyWithLateBinding.getSetter();
            irPropertyWithLateBinding.setSetter(setter != null ? updateAccessorModalityAndVisibility(setter, modalityDetermineModalityForFakeOverride, visibility) : null);
        } else if (irOverridableMemberSelectMostSpecificMember instanceof IrFunctionWithLateBinding) {
            irOverridableMemberSelectMostSpecificMember.setVisibility(visibility);
            irOverridableMemberSelectMostSpecificMember.setModality(modalityDetermineModalityForFakeOverride);
        } else {
            throw new IllegalStateException(("Unexpected fake override kind: " + irOverridableMemberSelectMostSpecificMember).toString());
        }
        ArrayList arrayList = new ArrayList(collectionFilterVisibleFakeOverrides.size());
        Iterator<T> it = collectionFilterVisibleFakeOverrides.iterator();
        while (it.hasNext()) {
            arrayList.add(getOriginal((IrOverridableMember) it.next()).getSymbol());
        }
        setOverriddenSymbols(irOverridableMemberSelectMostSpecificMember, CollectionsKt.compactIfPossible(arrayList));
        if (getOverriddenSymbols(irOverridableMemberSelectMostSpecificMember).isEmpty()) {
            throw new IllegalArgumentException(("Overridden symbols should be set for " + CallableMemberDescriptor.Kind.FAKE_OVERRIDE).toString());
        }
        addedFakeOverrides.add(irOverridableMemberSelectMostSpecificMember);
        this.fakeOverrideBuilder.linkFakeOverride(irOverridableMemberSelectMostSpecificMember, compatibilityMode);
        this.fakeOverrideBuilder.postProcessGeneratedFakeOverride(irOverridableMemberSelectMostSpecificMember, currentClass);
    }

    private final boolean isVisibilityMoreSpecific(IrOverridableMember a, IrOverridableMember b) {
        Integer numCompare = DescriptorVisibilities.compare(a.getVisibility(), b.getVisibility());
        return numCompare == null || numCompare.intValue() >= 0;
    }

    private final boolean isAccessorMoreSpecific(IrSimpleFunction a, IrSimpleFunction b) {
        if (a == null || b == null) {
            return true;
        }
        return isVisibilityMoreSpecific(a, b);
    }

    private final boolean isSubtypeOf(TypeCheckerState typeCheckerState, IrType irType, IrType irType2) {
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, typeCheckerState, (KotlinTypeMarker) irType, (KotlinTypeMarker) irType2, false, 8, (Object) null);
    }

    private final boolean equalTypes(TypeCheckerState typeCheckerState, IrType irType, IrType irType2) {
        return AbstractTypeChecker.INSTANCE.equalTypes(typeCheckerState, irType, irType2);
    }

    private final TypeCheckerState createTypeCheckerState(List<? extends IrTypeParameter> a, List<? extends IrTypeParameter> b) {
        return IrTypeCheckerStateKt.createIrTypeCheckerState(new IrTypeSystemContextWithAdditionalAxioms(this.typeSystem, a, b));
    }

    private final boolean isReturnTypeMoreSpecific(IrOverridableMember a, IrType aReturnType, IrOverridableMember b, IrType bReturnType) {
        return isSubtypeOf(createTypeCheckerState(IrOverridingUtilKt.getTypeParameters(a), IrOverridingUtilKt.getTypeParameters(b)), aReturnType, bReturnType);
    }

    private final boolean isMoreSpecific(IrOverridableMember a, IrOverridableMember b) {
        IrType returnType = IrOverridingUtilKt.getReturnType(a);
        IrType returnType2 = IrOverridingUtilKt.getReturnType(b);
        if (!isVisibilityMoreSpecific(a, b)) {
            return false;
        }
        if (a instanceof IrSimpleFunction) {
            if (!(b instanceof IrSimpleFunction)) {
                throw new IllegalArgumentException(("b is " + b.getClass()).toString());
            }
            return isReturnTypeMoreSpecific(a, returnType, b, returnType2);
        }
        if (a instanceof IrProperty) {
            if (!(b instanceof IrProperty)) {
                throw new IllegalArgumentException(("b is " + b.getClass()).toString());
            }
            IrProperty irProperty = (IrProperty) a;
            IrProperty irProperty2 = (IrProperty) b;
            if (!isAccessorMoreSpecific(irProperty.getSetter(), irProperty2.getSetter())) {
                return false;
            }
            if (!irProperty.getIsVar() || !irProperty2.getIsVar()) {
                return (irProperty.getIsVar() || !irProperty2.getIsVar()) && isReturnTypeMoreSpecific(a, returnType, b, returnType2);
            }
            IrSimpleFunction getter = irProperty.getGetter();
            Intrinsics.checkNotNull(getter);
            List<IrTypeParameter> typeParameters = getter.getTypeParameters();
            IrSimpleFunction getter2 = irProperty2.getGetter();
            Intrinsics.checkNotNull(getter2);
            return equalTypes(createTypeCheckerState(typeParameters, getter2.getTypeParameters()), returnType, returnType2);
        }
        throw new IllegalStateException(("Unexpected callable: " + a).toString());
    }

    private final boolean isMoreSpecificThenAllOf(IrOverridableMember candidate, Collection<? extends IrOverridableMember> descriptors) {
        Iterator<? extends IrOverridableMember> it = descriptors.iterator();
        while (it.hasNext()) {
            if (!isMoreSpecific(candidate, it.next())) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:34:0x007e  */
    /* JADX WARN: Code duplicated, block: B:48:? A[RETURN, SYNTHETIC] */
    private final IrOverridableMember selectMostSpecificMember(Collection<? extends IrOverridableMember> overridables) {
        if (overridables.isEmpty()) {
            throw new IllegalArgumentException("Should have at least one overridable descriptor".toString());
        }
        if (overridables.size() == 1) {
            return (IrOverridableMember) kotlin.collections.CollectionsKt.first(overridables);
        }
        ArrayList<IrOverridableMember> arrayList = new ArrayList();
        IrOverridableMember irOverridableMember = (IrOverridableMember) kotlin.collections.CollectionsKt.first(overridables);
        IrOverridableMember irOverridableMember2 = irOverridableMember;
        for (IrOverridableMember irOverridableMember3 : overridables) {
            if (isMoreSpecificThenAllOf(irOverridableMember3, overridables)) {
                arrayList.add(irOverridableMember3);
            }
            if (isMoreSpecific(irOverridableMember3, irOverridableMember) && !isMoreSpecific(irOverridableMember, irOverridableMember3)) {
                irOverridableMember2 = irOverridableMember3;
            }
        }
        if (arrayList.isEmpty()) {
            return irOverridableMember2;
        }
        if (arrayList.size() == 1) {
            return (IrOverridableMember) kotlin.collections.CollectionsKt.first((List) arrayList);
        }
        for (IrOverridableMember irOverridableMember4 : arrayList) {
            if (!(IrOverridingUtilKt.getReturnType(irOverridableMember4) instanceof IrDynamicType)) {
                if (irOverridableMember4 == null) {
                    return (IrOverridableMember) kotlin.collections.CollectionsKt.first((List) arrayList);
                }
                return irOverridableMember4;
            }
        }
        irOverridableMember4 = null;
        if (irOverridableMember4 == null) {
            return (IrOverridableMember) kotlin.collections.CollectionsKt.first((List) arrayList);
        }
        return irOverridableMember4;
    }

    private final Collection<IrOverridableMember> extractMembersOverridableInBothWays(IrOverridableMember overrider, Collection<IrOverridableMember> extractFrom) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(overrider);
        Iterator<IrOverridableMember> it = extractFrom.iterator();
        while (it.hasNext()) {
            IrOverridableMember next = it.next();
            if (overrider == next) {
                it.remove();
            } else {
                OverridingUtil.OverrideCompatibilityInfo.Result bothWaysOverridability = getBothWaysOverridability(overrider, next);
                if (bothWaysOverridability == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE) {
                    arrayList.add(next);
                    it.remove();
                } else if (bothWaysOverridability == OverridingUtil.OverrideCompatibilityInfo.Result.CONFLICT) {
                    it.remove();
                }
            }
        }
        return arrayList;
    }

    private final OverridingUtil.OverrideCompatibilityInfo.Result getBothWaysOverridability(IrOverridableMember overriderDescriptor, IrOverridableMember candidateDescriptor) {
        OverridingUtil.OverrideCompatibilityInfo.Result result = isOverridableBy(candidateDescriptor, overriderDescriptor, false, false).getResult();
        Intrinsics.checkNotNullExpressionValue(result, "getResult(...)");
        OverridingUtil.OverrideCompatibilityInfo.Result result2 = isOverridableBy(overriderDescriptor, candidateDescriptor, false, false).getResult();
        Intrinsics.checkNotNullExpressionValue(result2, "getResult(...)");
        return result == result2 ? result : OverridingUtil.OverrideCompatibilityInfo.Result.INCOMPATIBLE;
    }

    private final OverridingUtil.OverrideCompatibilityInfo isOverridableBy(IrOverridableMember superMember, IrOverridableMember subMember, boolean checkIsInlineFlag, boolean checkReturnType) {
        return IrOverridingUtilKt.isOverridableByWithoutExternalConditions(this.typeSystem, superMember, subMember, checkIsInlineFlag, checkReturnType);
    }
}
