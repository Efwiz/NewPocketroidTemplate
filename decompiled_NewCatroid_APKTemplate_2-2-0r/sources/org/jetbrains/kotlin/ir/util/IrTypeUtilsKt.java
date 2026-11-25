package org.jetbrains.kotlin.ir.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: IrTypeUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\rH\u0002\u001a\u001e\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\rH\u0002\u001a\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u00132\u0006\u0010\n\u001a\u00020\u000b\u001a\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a\u0010\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0013*\u00020\u000b\u001a\f\u0010\u0017\u001a\u0004\u0018\u00010\u0018*\u00020\u0019\u001a\n\u0010\u001a\u001a\u00020\u001b*\u00020\u0019\u001a\u001c\u0010\u001c\u001a\u00020\u001b*\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0001H\u0002\u001a\u001c\u0010!\u001a\u00020\u001b*\u00020\u001d2\u0006\u0010\"\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0001H\u0002\u001a\n\u0010#\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010$\u001a\u00020\u001b*\u00020\u001d\u001a\n\u0010$\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010%\u001a\u00020\u001b*\u00020\u001d\u001a\n\u0010%\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010&\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010'\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010(\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010)\u001a\u00020\u001b*\u00020\u001d\u001a\n\u0010)\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010*\u001a\u00020\u001b*\u00020\u001d\u001a\n\u0010*\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010+\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010,\u001a\u00020\u001b*\u00020\u001d\u001a\n\u0010,\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010-\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010.\u001a\u00020\u001b*\u00020\u0019\u001a\n\u0010/\u001a\u00020\u001b*\u00020\u0019\u001a!\u00100\u001a\u00020\u001b*\u00020\u00192\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020\u001b02H\u0082\b\u001a\n\u00104\u001a\u00020\u001b*\u00020\u0019\u001a\n\u00105\u001a\u00020\u001b*\u00020\u0019\u001a\n\u00106\u001a\u00020\u001b*\u00020\u0019\u001a&\u00107\u001a\u00020\u0019*\u00020\u00192\f\u00108\u001a\b\u0012\u0004\u0012\u0002090\u00152\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00190\u0015\u001a\u001e\u00107\u001a\u00020\u0019*\u00020\u00192\u0012\u0010;\u001a\u000e\u0012\u0004\u0012\u00020=\u0012\u0004\u0012\u00020\u00190<\u001a\u0010\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00190\u0015*\u00020\u0019\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u0011\u0010\u0005\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0004\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"kotlinCoroutinesPackageFqn", "Lorg/jetbrains/kotlin/name/FqName;", "kotlinEnumsPackageFqn", "getKotlinEnumsPackageFqn", "()Lorg/jetbrains/kotlin/name/FqName;", "kotlinPackageFqn", "getKotlinPackageFqn", "kotlinReflectionPackageFqn", "collectAllSuperclasses", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "collectAllSupertypes", "irType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "result", "getAllSubstitutedSupertypes", "", "getImmediateSupertypes", "", "getAllSuperclasses", "getPrimitiveArrayElementType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isAnnotation", "", "isClassWithName", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "name", "", "packageFqName", "isClassWithNamePrefix", "prefix", "isExternalObject", "isFunction", "isFunctionMarker", "isFunctionOrKFunction", "isFunctionTypeOrSubtype", "isInterface", "isKFunction", "isKSuspendFunction", "isPrimitiveArray", "isSuspendFunction", "isSuspendFunctionOrKFunction", "isSuspendFunctionTypeOrSubtype", "isThrowable", "isTypeFromKotlinPackage", "namePredicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "isTypeParameter", "isUnsigned", "isUnsignedArray", "substitute", "params", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "arguments", "substitutionMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "superTypes", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrTypeUtilsKt {
    private static final FqName kotlinCoroutinesPackageFqn;
    private static final FqName kotlinEnumsPackageFqn;
    private static final FqName kotlinPackageFqn;
    private static final FqName kotlinReflectionPackageFqn;

    static {
        FqName fqNameFromSegments = FqName.fromSegments(CollectionsKt.listOf(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME));
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
        kotlinPackageFqn = fqNameFromSegments;
        FqName fqNameChild = fqNameFromSegments.child(Name.identifier("enums"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        kotlinEnumsPackageFqn = fqNameChild;
        FqName fqNameChild2 = fqNameFromSegments.child(Name.identifier("reflect"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        kotlinReflectionPackageFqn = fqNameChild2;
        FqName fqNameChild3 = fqNameFromSegments.child(Name.identifier("coroutines"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild3, "child(...)");
        kotlinCoroutinesPackageFqn = fqNameChild3;
    }

    public static final FqName getKotlinPackageFqn() {
        return kotlinPackageFqn;
    }

    public static final FqName getKotlinEnumsPackageFqn() {
        return kotlinEnumsPackageFqn;
    }

    public static final boolean isFunctionMarker(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        return classifierOrNull != null && isClassWithName(classifierOrNull, "Function", kotlinPackageFqn);
    }

    public static final boolean isFunction(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        return classifierOrNull != null && isClassWithNamePrefix(classifierOrNull, "Function", kotlinPackageFqn);
    }

    public static final boolean isKFunction(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        return classifierOrNull != null && isClassWithNamePrefix(classifierOrNull, StandardNames.K_FUNCTION_PREFIX, kotlinReflectionPackageFqn);
    }

    public static final boolean isSuspendFunction(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        return classifierOrNull != null && isClassWithNamePrefix(classifierOrNull, "SuspendFunction", kotlinCoroutinesPackageFqn);
    }

    public static final boolean isKSuspendFunction(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        return classifierOrNull != null && isClassWithNamePrefix(classifierOrNull, StandardNames.K_SUSPEND_FUNCTION_PREFIX, kotlinReflectionPackageFqn);
    }

    public static final boolean isFunctionMarker(IrClassifierSymbol irClassifierSymbol) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        return isClassWithName(irClassifierSymbol, "Function", kotlinPackageFqn);
    }

    public static final boolean isFunction(IrClassifierSymbol irClassifierSymbol) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        return isClassWithNamePrefix(irClassifierSymbol, "Function", kotlinPackageFqn);
    }

    public static final boolean isKFunction(IrClassifierSymbol irClassifierSymbol) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        return isClassWithNamePrefix(irClassifierSymbol, StandardNames.K_FUNCTION_PREFIX, kotlinReflectionPackageFqn);
    }

    public static final boolean isSuspendFunction(IrClassifierSymbol irClassifierSymbol) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        return isClassWithNamePrefix(irClassifierSymbol, "SuspendFunction", kotlinCoroutinesPackageFqn);
    }

    public static final boolean isKSuspendFunction(IrClassifierSymbol irClassifierSymbol) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        return isClassWithNamePrefix(irClassifierSymbol, StandardNames.K_SUSPEND_FUNCTION_PREFIX, kotlinReflectionPackageFqn);
    }

    private static final boolean isClassWithName(IrClassifierSymbol irClassifierSymbol, String str, FqName fqName) {
        IrSymbolOwner owner = irClassifierSymbol.getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName");
        IrDeclarationWithName irDeclarationWithName = (IrDeclarationWithName) owner;
        if (Intrinsics.areEqual(str, irDeclarationWithName.getName().asString())) {
            IrDeclarationParent parent = irDeclarationWithName.getParent();
            IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
            if (Intrinsics.areEqual(irPackageFragment != null ? irPackageFragment.getPackageFqName() : null, fqName)) {
                return true;
            }
        }
        return false;
    }

    private static final boolean isClassWithNamePrefix(IrClassifierSymbol irClassifierSymbol, String str, FqName fqName) {
        IrSymbolOwner owner = irClassifierSymbol.getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName");
        IrDeclarationWithName irDeclarationWithName = (IrDeclarationWithName) owner;
        String strAsString = irDeclarationWithName.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (!StringsKt.startsWith$default(strAsString, str, false, 2, (Object) null)) {
            return false;
        }
        IrDeclarationParent parent = irDeclarationWithName.getParent();
        IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
        return Intrinsics.areEqual(irPackageFragment != null ? irPackageFragment.getPackageFqName() : null, fqName);
    }

    public static final List<IrType> superTypes(IrType irType) {
        List<IrType> listSuperTypes;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        return (classifierOrNull == null || (listSuperTypes = org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.superTypes(classifierOrNull)) == null) ? CollectionsKt.emptyList() : listSuperTypes;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.util.IrTypeUtilsKt$isFunctionTypeOrSubtype$2, reason: invalid class name */
    /* JADX INFO: compiled from: IrTypeUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<IrType, Boolean> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        AnonymousClass2() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "isFunction(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "isFunction";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(IrTypeUtilsKt.class, "ir.tree");
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(IrType p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return Boolean.valueOf(IrTypeUtilsKt.isFunction(p0));
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.util.IrTypeUtilsKt$isSuspendFunctionTypeOrSubtype$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: IrTypeUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C47322 extends FunctionReference implements Function1<IrType, Boolean> {
        public static final C47322 INSTANCE = new C47322();

        C47322() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "isSuspendFunction(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "isSuspendFunction";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(IrTypeUtilsKt.class, "ir.tree");
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(IrType p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return Boolean.valueOf(IrTypeUtilsKt.isSuspendFunction(p0));
        }
    }

    public static final boolean isFunctionTypeOrSubtype(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf(irType), new IrTypeUtilsKt$$ExternalSyntheticLambda0(), AnonymousClass2.INSTANCE);
        Intrinsics.checkNotNullExpressionValue(boolIfAny, "ifAny(...)");
        return boolIfAny.booleanValue();
    }

    public static final boolean isSuspendFunctionTypeOrSubtype(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf(irType), new IrTypeUtilsKt$$ExternalSyntheticLambda0(), C47322.INSTANCE);
        Intrinsics.checkNotNullExpressionValue(boolIfAny, "ifAny(...)");
        return boolIfAny.booleanValue();
    }

    public static final boolean isTypeParameter(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return IrTypesKt.getClassifierOrNull(irType) instanceof IrTypeParameterSymbol;
    }

    public static final boolean isInterface(IrType irType) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        return ((classOrNull == null || (owner = classOrNull.getOwner()) == null) ? null : owner.getKind()) == ClassKind.INTERFACE;
    }

    public static final boolean isExternalObject(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        IrClass owner = classOrNull != null ? classOrNull.getOwner() : null;
        return (owner != null ? owner.getKind() : null) == ClassKind.OBJECT && owner.getIsExternal();
    }

    public static final boolean isAnnotation(IrType irType) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        return ((classOrNull == null || (owner = classOrNull.getOwner()) == null) ? null : owner.getKind()) == ClassKind.ANNOTATION_CLASS;
    }

    public static final boolean isFunctionOrKFunction(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isFunction(irType) || isKFunction(irType);
    }

    public static final boolean isSuspendFunctionOrKFunction(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isSuspendFunction(irType) || isKSuspendFunction(irType);
    }

    public static final PrimitiveType getPrimitiveArrayElementType(IrType irType) {
        FqName fqNameWhenAvailable;
        FqNameUnsafe unsafe;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrSimpleType irSimpleType = irType instanceof IrSimpleType ? (IrSimpleType) irType : null;
        if (irSimpleType == null) {
            return null;
        }
        IrSymbolOwner owner = irSimpleType.getClassifier().getOwner();
        IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
        if (irClass == null || (fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClass)) == null || (unsafe = fqNameWhenAvailable.toUnsafe()) == null) {
            return null;
        }
        return StandardNames.FqNames.arrayClassFqNameToPrimitiveType.get(unsafe);
    }

    public static final IrType substitute(IrType irType, List<? extends IrTypeParameter> params, List<? extends IrType> arguments) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(params, "params");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        List<? extends IrTypeParameter> list = params;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrTypeParameter) it.next()).getSymbol());
        }
        return substitute(irType, MapsKt.toMap(CollectionsKt.zip(arrayList, arguments)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrType substitute(IrType irType, Map<IrTypeParameterSymbol, ? extends IrType> substitutionMap) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(substitutionMap, "substitutionMap");
        if (!(irType instanceof IrSimpleType) || substitutionMap.isEmpty()) {
            return irType;
        }
        List<IrConstructorCall> annotations = irType.getAnnotations();
        ArrayList arrayList = new ArrayList(annotations.size());
        for (IrConstructorCall irConstructorCall : annotations) {
            DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, 0 == true ? 1 : 0);
            IrVisitorsKt.acceptVoid(irConstructorCall, deepCopySymbolRemapper);
            DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
            IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irConstructorCall.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), null);
            if (irElementPatchDeclarationParents != null) {
                arrayList.add((IrConstructorCall) irElementPatchDeclarationParents);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
            }
        }
        List listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        IrSimpleType irSimpleType = (IrSimpleType) irType;
        IrType irType2 = substitutionMap.get(irSimpleType.getClassifier());
        if (irType2 != null) {
            return IrTypesKt.addAnnotations(IrTypesKt.mergeNullability(irType2, irType), listCompactIfPossible);
        }
        List<IrTypeArgument> arguments = irSimpleType.getArguments();
        ArrayList arrayList2 = new ArrayList(arguments.size());
        for (IrTypeProjection irTypeProjectionMakeTypeProjection : arguments) {
            if (irTypeProjectionMakeTypeProjection instanceof IrTypeProjection) {
                IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeProjectionMakeTypeProjection;
                irTypeProjectionMakeTypeProjection = IrSimpleTypeImplKt.makeTypeProjection(substitute(irTypeProjection.getType(), substitutionMap), irTypeProjection.getVariance());
            } else if (!(irTypeProjectionMakeTypeProjection instanceof IrStarProjection)) {
                throw new NoWhenBranchMatchedException();
            }
            arrayList2.add(irTypeProjectionMakeTypeProjection);
        }
        return new IrSimpleTypeImpl(irSimpleType.getClassifier(), irSimpleType.getNullability(), org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2), listCompactIfPossible, (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
    }

    private static final List<IrSimpleType> getImmediateSupertypes(IrSimpleType irSimpleType) {
        IrClass irClass = IrTypesKt.getClass(irSimpleType);
        if (irClass == null) {
            throw new AssertionError("Not a class type: " + RenderIrElementKt.render$default(irSimpleType, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        List<IrType> superTypes = irClass.getSuperTypes();
        List<IrTypeArgument> arguments = irSimpleType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            IrType typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) it.next());
            if (typeOrNull == null) {
                throw new AssertionError("*-projection in supertype arguments: " + RenderIrElementKt.render$default(irSimpleType, (DumpIrTreeOptions) null, 1, (Object) null));
            }
            arrayList.add(typeOrNull);
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : superTypes) {
            if (IrTypesKt.getClassOrNull((IrType) obj) != null) {
                arrayList3.add(obj);
            }
        }
        ArrayList arrayList4 = arrayList3;
        ArrayList arrayList5 = new ArrayList(arrayList4.size());
        Iterator it2 = arrayList4.iterator();
        while (it2.hasNext()) {
            IrType irTypeSubstitute = substitute((IrType) it2.next(), irClass.getTypeParameters(), arrayList2);
            Intrinsics.checkNotNull(irTypeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            arrayList5.add((IrSimpleType) irTypeSubstitute);
        }
        return org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList5);
    }

    private static final void collectAllSupertypes(IrSimpleType irSimpleType, Set<IrSimpleType> set) {
        List<IrSimpleType> immediateSupertypes = getImmediateSupertypes(irSimpleType);
        set.addAll(immediateSupertypes);
        Iterator<IrSimpleType> it = immediateSupertypes.iterator();
        while (it.hasNext()) {
            collectAllSupertypes(it.next(), set);
        }
    }

    public static final Set<IrSimpleType> getAllSubstitutedSupertypes(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        HashSet hashSet = new HashSet();
        collectAllSupertypes(IrUtilsKt.getDefaultType(irClass), hashSet);
        return hashSet;
    }

    private static final void collectAllSuperclasses(IrClass irClass, Set<IrClass> set) {
        Iterator<IrType> it = irClass.getSuperTypes().iterator();
        while (it.hasNext()) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(it.next());
            IrClassSymbol irClassSymbol = classifierOrNull instanceof IrClassSymbol ? (IrClassSymbol) classifierOrNull : null;
            if (irClassSymbol != null) {
                IrClass owner = irClassSymbol.getOwner();
                if (set.add(owner)) {
                    collectAllSuperclasses(owner, set);
                }
            }
        }
    }

    public static final Set<IrClass> getAllSuperclasses(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        HashSet hashSet = new HashSet();
        collectAllSuperclasses(irClass, hashSet);
        return hashSet;
    }

    public static final boolean isThrowable(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (!(irType instanceof IrSimpleType)) {
            return false;
        }
        IrClassifierSymbol classifier = ((IrSimpleType) irType).getClassifier();
        IrClassSymbol irClassSymbol = classifier instanceof IrClassSymbol ? (IrClassSymbol) classifier : null;
        if (irClassSymbol == null || !Intrinsics.areEqual(irClassSymbol.getOwner().getName().asString(), "Throwable")) {
            return false;
        }
        IrDeclarationParent parent = irClassSymbol.getOwner().getParent();
        IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
        if (irPackageFragment == null) {
            return false;
        }
        return Intrinsics.areEqual(irPackageFragment.getPackageFqName(), kotlinPackageFqn);
    }

    public static final boolean isUnsigned(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (!(irType instanceof IrSimpleType)) {
            return false;
        }
        IrClassifierSymbol classifier = ((IrSimpleType) irType).getClassifier();
        IrClassSymbol irClassSymbol = classifier instanceof IrClassSymbol ? (IrClassSymbol) classifier : null;
        if (irClassSymbol == null) {
            return false;
        }
        if (!UnsignedTypes.INSTANCE.isShortNameOfUnsignedType(irClassSymbol.getOwner().getName())) {
            return false;
        }
        IrDeclarationParent parent = irClassSymbol.getOwner().getParent();
        IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
        if (irPackageFragment == null) {
            return false;
        }
        return Intrinsics.areEqual(irPackageFragment.getPackageFqName(), kotlinPackageFqn);
    }

    public static final boolean isUnsignedArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (!(irType instanceof IrSimpleType)) {
            return false;
        }
        IrClassifierSymbol classifier = ((IrSimpleType) irType).getClassifier();
        IrClassSymbol irClassSymbol = classifier instanceof IrClassSymbol ? (IrClassSymbol) classifier : null;
        if (irClassSymbol == null) {
            return false;
        }
        if (!UnsignedTypes.INSTANCE.isShortNameOfUnsignedArray(irClassSymbol.getOwner().getName())) {
            return false;
        }
        IrDeclarationParent parent = irClassSymbol.getOwner().getParent();
        IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
        if (irPackageFragment == null) {
            return false;
        }
        return Intrinsics.areEqual(irPackageFragment.getPackageFqName(), kotlinPackageFqn);
    }

    public static final boolean isPrimitiveArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (!(irType instanceof IrSimpleType)) {
            return false;
        }
        IrClassifierSymbol classifier = ((IrSimpleType) irType).getClassifier();
        IrClassSymbol irClassSymbol = classifier instanceof IrClassSymbol ? (IrClassSymbol) classifier : null;
        if (irClassSymbol == null) {
            return false;
        }
        if (!StandardNames.FqNames.primitiveArrayTypeShortNames.contains(irClassSymbol.getOwner().getName())) {
            return false;
        }
        IrDeclarationParent parent = irClassSymbol.getOwner().getParent();
        IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
        if (irPackageFragment == null) {
            return false;
        }
        return Intrinsics.areEqual(irPackageFragment.getPackageFqName(), kotlinPackageFqn);
    }
}
