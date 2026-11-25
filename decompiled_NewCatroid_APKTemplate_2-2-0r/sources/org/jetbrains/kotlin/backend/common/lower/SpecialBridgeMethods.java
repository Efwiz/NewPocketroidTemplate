package org.jetbrains.kotlin.backend.common.lower;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SpecialBridgeMethods.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001.B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0018\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J&\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u0012\u0018\u00010 2\u0006\u0010!\u001a\u00020\u001a2\b\b\u0002\u0010\"\u001a\u00020\u0018J\u0010\u0010#\u001a\u0004\u0018\u00010\u00102\u0006\u0010!\u001a\u00020\u001aJ\u0010\u0010$\u001a\u00020%2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u0010&\u001a\u0004\u0018\u00010\u00122\u0006\u0010!\u001a\u00020\u001aJ\"\u0010'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\b\b\u0002\u0010,\u001a\u00020\u001cH\u0002J\f\u0010-\u001a\u00020\u000f*\u00020\u001aH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R!\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00120\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\n¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\f¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/SpecialBridgeMethods;", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "specialMethodNames", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/name/Name;", "Lkotlin/collections/HashSet;", "getSpecialMethodNames", "()Ljava/util/HashSet;", "specialMethods", "", "Lorg/jetbrains/kotlin/backend/common/lower/SpecialBridgeMethods$SpecialMethodDescription;", "Lorg/jetbrains/kotlin/backend/common/lower/BuiltInWithDifferentJvmName;", "specialMethodsWithDefaults", "Lorg/jetbrains/kotlin/backend/common/lower/SpecialMethodWithDefaultInfo;", "specialProperties", "specialPropertyNames", "getSpecialPropertyNames", "constFalse", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "bridge", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "constMinusOne", "", "constNull", "", "findSpecialWithOverride", "Lkotlin/Pair;", "irFunction", "includeSelf", "getBuiltInWithDifferentJvmName", "getSecondArg", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetValueImpl;", "getSpecialMethodInfo", "makeDescription", "classFqName", "Lorg/jetbrains/kotlin/name/FqName;", "funName", "", "arity", "toDescription", "SpecialMethodDescription", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SpecialBridgeMethods {
    private final CommonBackendContext context;
    private final HashSet<Name> specialMethodNames;
    private final Map<SpecialMethodDescription, BuiltInWithDifferentJvmName> specialMethods;
    private final Map<SpecialMethodDescription, SpecialMethodWithDefaultInfo> specialMethodsWithDefaults;
    private final Map<SpecialMethodDescription, BuiltInWithDifferentJvmName> specialProperties;
    private final HashSet<Name> specialPropertyNames;

    /* JADX INFO: compiled from: SpecialBridgeMethods.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0007HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/SpecialBridgeMethods$SpecialMethodDescription;", "", "kotlinFqClassName", "Lorg/jetbrains/kotlin/name/FqName;", "name", "Lorg/jetbrains/kotlin/name/Name;", "arity", "", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/Name;I)V", "getArity", "()I", "getKotlinFqClassName", "()Lorg/jetbrains/kotlin/name/FqName;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class SpecialMethodDescription {
        private final int arity;
        private final FqName kotlinFqClassName;
        private final Name name;

        public static /* synthetic */ SpecialMethodDescription copy$default(SpecialMethodDescription specialMethodDescription, FqName fqName, Name name2, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                fqName = specialMethodDescription.kotlinFqClassName;
            }
            if ((i2 & 2) != 0) {
                name2 = specialMethodDescription.name;
            }
            if ((i2 & 4) != 0) {
                i = specialMethodDescription.arity;
            }
            return specialMethodDescription.copy(fqName, name2, i);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FqName getKotlinFqClassName() {
            return this.kotlinFqClassName;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Name getName() {
            return this.name;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getArity() {
            return this.arity;
        }

        public final SpecialMethodDescription copy(FqName kotlinFqClassName, Name name2, int arity) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return new SpecialMethodDescription(kotlinFqClassName, name2, arity);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SpecialMethodDescription)) {
                return false;
            }
            SpecialMethodDescription specialMethodDescription = (SpecialMethodDescription) other;
            return Intrinsics.areEqual(this.kotlinFqClassName, specialMethodDescription.kotlinFqClassName) && Intrinsics.areEqual(this.name, specialMethodDescription.name) && this.arity == specialMethodDescription.arity;
        }

        public int hashCode() {
            FqName fqName = this.kotlinFqClassName;
            return ((((fqName == null ? 0 : fqName.hashCode()) * 31) + this.name.hashCode()) * 31) + Integer.hashCode(this.arity);
        }

        public String toString() {
            return "SpecialMethodDescription(kotlinFqClassName=" + this.kotlinFqClassName + ", name=" + this.name + ", arity=" + this.arity + ')';
        }

        public SpecialMethodDescription(FqName fqName, Name name2, int i) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.kotlinFqClassName = fqName;
            this.name = name2;
            this.arity = i;
        }

        public final int getArity() {
            return this.arity;
        }

        public final FqName getKotlinFqClassName() {
            return this.kotlinFqClassName;
        }

        public final Name getName() {
            return this.name;
        }
    }

    public SpecialBridgeMethods(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        Map<SpecialMethodDescription, SpecialMethodWithDefaultInfo> mapMapOf = MapsKt.mapOf(TuplesKt.to(makeDescription(StandardNames.FqNames.collection, "contains", 1), new SpecialMethodWithDefaultInfo(new SpecialBridgeMethods$specialMethodsWithDefaults$1(this), 1, false, false, 12, null)), TuplesKt.to(makeDescription(StandardNames.FqNames.mutableCollection, ProductAction.ACTION_REMOVE, 1), new SpecialMethodWithDefaultInfo(new SpecialBridgeMethods$specialMethodsWithDefaults$2(this), 1, false, false, 12, null)), TuplesKt.to(makeDescription(StandardNames.FqNames.map, "containsKey", 1), new SpecialMethodWithDefaultInfo(new SpecialBridgeMethods$specialMethodsWithDefaults$3(this), 1, false, false, 12, null)), TuplesKt.to(makeDescription(StandardNames.FqNames.map, "containsValue", 1), new SpecialMethodWithDefaultInfo(new SpecialBridgeMethods$specialMethodsWithDefaults$4(this), 1, false, false, 12, null)), TuplesKt.to(makeDescription(StandardNames.FqNames.mutableMap, ProductAction.ACTION_REMOVE, 2), new SpecialMethodWithDefaultInfo(new SpecialBridgeMethods$specialMethodsWithDefaults$5(this), 2, false, false, 12, null)), TuplesKt.to(makeDescription(StandardNames.FqNames.list, "indexOf", 1), new SpecialMethodWithDefaultInfo(new SpecialBridgeMethods$specialMethodsWithDefaults$6(this), 1, false, false, 12, null)), TuplesKt.to(makeDescription(StandardNames.FqNames.list, "lastIndexOf", 1), new SpecialMethodWithDefaultInfo(new SpecialBridgeMethods$specialMethodsWithDefaults$7(this), 1, false, false, 12, null)), TuplesKt.to(makeDescription(StandardNames.FqNames.map, "getOrDefault", 2), new SpecialMethodWithDefaultInfo(new SpecialBridgeMethods$specialMethodsWithDefaults$8(this), 1, true, true)), TuplesKt.to(makeDescription(StandardNames.FqNames.map, "get", 1), new SpecialMethodWithDefaultInfo(new SpecialBridgeMethods$specialMethodsWithDefaults$9(this), 1, true, true)), TuplesKt.to(makeDescription(StandardNames.FqNames.mutableMap, ProductAction.ACTION_REMOVE, 1), new SpecialMethodWithDefaultInfo(new SpecialBridgeMethods$specialMethodsWithDefaults$10(this), 1, true, true)));
        this.specialMethodsWithDefaults = mapMapOf;
        FqName safe = StandardNames.FqNames.charSequence.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
        this.specialProperties = MapsKt.mapOf(TuplesKt.to(makeDescription$default(this, StandardNames.FqNames.collection, "size", 0, 4, null), new BuiltInWithDifferentJvmName(false, false, 3, null)), TuplesKt.to(makeDescription$default(this, StandardNames.FqNames.map, "size", 0, 4, null), new BuiltInWithDifferentJvmName(false, false, 3, null)), TuplesKt.to(makeDescription$default(this, safe, "length", 0, 4, null), new BuiltInWithDifferentJvmName(false, false, 3, null)), TuplesKt.to(makeDescription$default(this, StandardNames.FqNames.map, UserMetadata.KEYDATA_FILENAME, 0, 4, null), new BuiltInWithDifferentJvmName(true, false, 2, null)), TuplesKt.to(makeDescription$default(this, StandardNames.FqNames.map, CompoundEntry.NAME_values, 0, 4, null), new BuiltInWithDifferentJvmName(true, false, 2, null)), TuplesKt.to(makeDescription$default(this, StandardNames.FqNames.map, TypeBlock.NAME_entries, 0, 4, null), new BuiltInWithDifferentJvmName(true, false, 2, null)));
        FqName safe2 = StandardNames.FqNames.number.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe2, "toSafe(...)");
        FqName safe3 = StandardNames.FqNames.number.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe3, "toSafe(...)");
        FqName safe4 = StandardNames.FqNames.number.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe4, "toSafe(...)");
        FqName safe5 = StandardNames.FqNames.number.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe5, "toSafe(...)");
        FqName safe6 = StandardNames.FqNames.number.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe6, "toSafe(...)");
        FqName safe7 = StandardNames.FqNames.number.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe7, "toSafe(...)");
        FqName safe8 = StandardNames.FqNames.charSequence.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe8, "toSafe(...)");
        Map<SpecialMethodDescription, BuiltInWithDifferentJvmName> mapMapOf2 = MapsKt.mapOf(TuplesKt.to(makeDescription$default(this, safe2, "toByte", 0, 4, null), new BuiltInWithDifferentJvmName(false, false, 3, null)), TuplesKt.to(makeDescription$default(this, safe3, "toShort", 0, 4, null), new BuiltInWithDifferentJvmName(false, false, 3, null)), TuplesKt.to(makeDescription$default(this, safe4, "toInt", 0, 4, null), new BuiltInWithDifferentJvmName(false, false, 3, null)), TuplesKt.to(makeDescription$default(this, safe5, "toLong", 0, 4, null), new BuiltInWithDifferentJvmName(false, false, 3, null)), TuplesKt.to(makeDescription$default(this, safe6, "toFloat", 0, 4, null), new BuiltInWithDifferentJvmName(false, false, 3, null)), TuplesKt.to(makeDescription$default(this, safe7, "toDouble", 0, 4, null), new BuiltInWithDifferentJvmName(false, false, 3, null)), TuplesKt.to(makeDescription(safe8, "get", 1), new BuiltInWithDifferentJvmName(false, false, 3, null)), TuplesKt.to(makeDescription(StandardNames.FqNames.mutableList, "removeAt", 1), new BuiltInWithDifferentJvmName(true, false)));
        this.specialMethods = mapMapOf2;
        Map mapPlus = MapsKt.plus(mapMapOf, mapMapOf2);
        ArrayList arrayList = new ArrayList(mapPlus.size());
        Iterator it = mapPlus.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(((SpecialMethodDescription) ((Map.Entry) it.next()).getKey()).getName());
        }
        this.specialMethodNames = CollectionsKt.toHashSet(arrayList);
        Map<SpecialMethodDescription, BuiltInWithDifferentJvmName> map = this.specialProperties;
        ArrayList arrayList2 = new ArrayList(map.size());
        Iterator<Map.Entry<SpecialMethodDescription, BuiltInWithDifferentJvmName>> it2 = map.entrySet().iterator();
        while (it2.hasNext()) {
            arrayList2.add(it2.next().getKey().getName());
        }
        this.specialPropertyNames = CollectionsKt.toHashSet(arrayList2);
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    static /* synthetic */ SpecialMethodDescription makeDescription$default(SpecialBridgeMethods specialBridgeMethods, FqName fqName, String str, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return specialBridgeMethods.makeDescription(fqName, str, i);
    }

    private final SpecialMethodDescription makeDescription(FqName classFqName, String funName, int arity) {
        Name nameIdentifier = Name.identifier(funName);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return new SpecialMethodDescription(classFqName, nameIdentifier, arity);
    }

    private final SpecialMethodDescription toDescription(IrSimpleFunction irSimpleFunction) {
        return new SpecialMethodDescription(IrUtilsKt.getFqNameWhenAvailable(IrUtilsKt.getParentAsClass(irSimpleFunction)), irSimpleFunction.getName(), irSimpleFunction.getValueParameters().size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<Boolean> constFalse(IrSimpleFunction bridge) {
        return new IrConstImpl<>(-1, -1, this.context.getIrBuiltIns().getBooleanType(), IrConstKind.Boolean.INSTANCE, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl constNull(IrSimpleFunction bridge) {
        return new IrConstImpl(-1, -1, this.context.getIrBuiltIns().getAnyNType(), IrConstKind.Null.INSTANCE, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<Integer> constMinusOne(IrSimpleFunction bridge) {
        return new IrConstImpl<>(-1, -1, this.context.getIrBuiltIns().getIntType(), IrConstKind.Int.INSTANCE, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrGetValueImpl getSecondArg(IrSimpleFunction bridge) {
        return new IrGetValueImpl(-1, -1, bridge.getValueParameters().get(1).getSymbol(), null, 8, null);
    }

    public final HashSet<Name> getSpecialMethodNames() {
        return this.specialMethodNames;
    }

    public final HashSet<Name> getSpecialPropertyNames() {
        return this.specialPropertyNames;
    }

    public static /* synthetic */ Pair findSpecialWithOverride$default(SpecialBridgeMethods specialBridgeMethods, IrSimpleFunction irSimpleFunction, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return specialBridgeMethods.findSpecialWithOverride(irSimpleFunction, z);
    }

    public final Pair<IrSimpleFunction, SpecialMethodWithDefaultInfo> findSpecialWithOverride(IrSimpleFunction irFunction, boolean includeSelf) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        if (!(irFunction.getParent() instanceof IrClass)) {
            return null;
        }
        for (IrSimpleFunction irSimpleFunction : IrUtilsKt.allOverridden(irFunction, includeSelf)) {
            SpecialMethodWithDefaultInfo specialMethodWithDefaultInfo = this.specialMethodsWithDefaults.get(toDescription(irSimpleFunction));
            if (specialMethodWithDefaultInfo != null) {
                return new Pair<>(irSimpleFunction, specialMethodWithDefaultInfo);
            }
        }
        return null;
    }

    public final SpecialMethodWithDefaultInfo getSpecialMethodInfo(IrSimpleFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        return this.specialMethodsWithDefaults.get(toDescription(irFunction));
    }

    public final BuiltInWithDifferentJvmName getBuiltInWithDifferentJvmName(IrSimpleFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        IrPropertySymbol correspondingPropertySymbol = irFunction.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol != null) {
            FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(IrUtilsKt.getParentAsClass(irFunction));
            if (fqNameWhenAvailable == null) {
                return null;
            }
            Map<SpecialMethodDescription, BuiltInWithDifferentJvmName> map = this.specialProperties;
            String strAsString = correspondingPropertySymbol.getOwner().getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return map.get(makeDescription$default(this, fqNameWhenAvailable, strAsString, 0, 4, null));
        }
        return this.specialMethods.get(toDescription(irFunction));
    }
}
