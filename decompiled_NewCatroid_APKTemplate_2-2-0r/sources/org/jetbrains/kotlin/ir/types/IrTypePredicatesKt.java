package org.jetbrains.kotlin.ir.types;

import androidx.exifinterface.media.ExifInterface;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.UnsignedType;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: irTypePredicates.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000d\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0017\u001a\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001a\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001a\u0016\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018\u001aI\u0010\u0019\u001a\u0004\u0018\u0001H\u001a\"\u000e\b\u0000\u0010\u001a*\b\u0012\u0004\u0012\u0002H\u001a0\u001b*\u00020\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u001a0\u00012\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u001a0\u0001¢\u0006\u0002\u0010\u001f\u001a\f\u0010 \u001a\u0004\u0018\u00010\u0003*\u00020\u001c\u001a\f\u0010!\u001a\u0004\u0018\u00010\u0005*\u00020\u001c\u001a\u0014\u0010\"\u001a\u00020\r*\u00020\u000f2\u0006\u0010#\u001a\u00020\u0002H\u0002\u001a\n\u0010$\u001a\u00020\r*\u00020\u001c\u001a\n\u0010%\u001a\u00020\r*\u00020\u001c\u001a\n\u0010&\u001a\u00020\r*\u00020\u001c\u001a\n\u0010'\u001a\u00020\r*\u00020\u001c\u001a\n\u0010(\u001a\u00020\r*\u00020\u001c\u001a\n\u0010)\u001a\u00020\r*\u00020\u001c\u001a\n\u0010*\u001a\u00020\r*\u00020\u001c\u001a\n\u0010+\u001a\u00020\r*\u00020\u001c\u001a\n\u0010,\u001a\u00020\r*\u00020\u001c\u001a%\u0010-\u001a\u00020\r*\u00020\u001c2\u0006\u0010#\u001a\u00020\u00022\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\rH\u0002¢\u0006\u0002\u0010/\u001a\u001a\u0010-\u001a\u00020\r*\u00020\u001c2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010.\u001a\u00020\r\u001a\u0012\u00100\u001a\u00020\r*\u0002012\u0006\u0010\u0010\u001a\u00020\u0011\u001a\n\u00102\u001a\u00020\r*\u00020\u001c\u001a\n\u00103\u001a\u00020\r*\u00020\u001c\u001a\n\u00104\u001a\u00020\r*\u00020\u001c\u001a\n\u00105\u001a\u00020\r*\u00020\u001c\u001a\n\u00106\u001a\u00020\r*\u00020\u001c\u001a\n\u00107\u001a\u00020\r*\u00020\u001c\u001a\n\u00108\u001a\u00020\r*\u00020\u001c\u001a\n\u00109\u001a\u00020\r*\u00020\u001c\u001a\n\u0010:\u001a\u00020\r*\u00020\u001c\u001a\n\u0010;\u001a\u00020\r*\u00020\u001c\u001a\n\u0010<\u001a\u00020\r*\u00020\u001c\u001a\n\u0010=\u001a\u00020\r*\u00020\u001c\u001a\n\u0010>\u001a\u00020\r*\u00020\u001c\u001a\n\u0010?\u001a\u00020\r*\u00020\u001c\u001a\n\u0010@\u001a\u00020\r*\u00020A\u001a\n\u0010@\u001a\u00020\r*\u00020\u001c\u001a\u0014\u0010B\u001a\u00020\r*\u00020\u001c2\u0006\u0010#\u001a\u00020\u0002H\u0002\u001a\n\u0010C\u001a\u00020\r*\u00020\u001c\u001a\n\u0010D\u001a\u00020\r*\u00020\u001c\u001a\n\u0010E\u001a\u00020\r*\u00020\u001c\u001a\u0014\u0010F\u001a\u00020\r*\u00020\u001c2\u0006\u0010#\u001a\u00020\u0002H\u0002\u001a\n\u0010G\u001a\u00020\r*\u00020\u001c\u001a\n\u0010H\u001a\u00020\r*\u00020\u001c\u001a\n\u0010I\u001a\u00020\r*\u00020\u001c\u001a\n\u0010J\u001a\u00020\r*\u00020\u001c\u001a\n\u0010K\u001a\u00020\r*\u00020\u001c\u001a\u0014\u0010L\u001a\u00020\r*\u00020\u001c2\b\b\u0002\u0010.\u001a\u00020\r\u001a\n\u0010M\u001a\u00020\r*\u00020\u001c\u001a\n\u0010N\u001a\u00020\r*\u00020\u001c\u001a\n\u0010O\u001a\u00020\r*\u00020\u001c\u001a\n\u0010P\u001a\u00020\r*\u00020\u001c\u001a\n\u0010Q\u001a\u00020\r*\u00020\u001c\u001a\n\u0010R\u001a\u00020\r*\u00020\u001c\u001a\n\u0010S\u001a\u00020\r*\u00020\u001c\u001a\n\u0010T\u001a\u00020\r*\u00020\u001c\u001a\n\u0010U\u001a\u00020\r*\u00020\u001c\u001a\n\u0010V\u001a\u00020\r*\u00020\u001c\u001a\u0014\u0010W\u001a\u00020\r*\u00020\u001c2\b\b\u0002\u0010.\u001a\u00020\r\"\u001a\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00050\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00050\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006X"}, d2 = {"idSignatureToPrimitiveType", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "idSignatureToUnsignedType", "Lorg/jetbrains/kotlin/builtins/UnsignedType;", "primitiveArrayTypesSignatures", "getPrimitiveArrayTypesSignatures", "()Ljava/util/Map;", "shortNameToPrimitiveType", "Lorg/jetbrains/kotlin/name/Name;", "shortNameToUnsignedType", "classFqNameEquals", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "fqName", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getPublicSignature", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "name", "", "getPrimitiveOrUnsignedType", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "byIdSignature", "byShortName", "(Lorg/jetbrains/kotlin/ir/types/IrType;Ljava/util/Map;Ljava/util/Map;)Ljava/lang/Enum;", "getPrimitiveType", "getUnsignedType", "hasFqNameEqualToSignature", "signature", "isAny", "isArray", "isBoolean", "isBooleanArray", "isByte", "isByteArray", Namer.IS_CHAR, "isCharArray", "isCharSequence", "isClassType", "nullable", "(Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;Ljava/lang/Boolean;)Z", "isClassWithFqName", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "isCollection", "isComparable", "isDouble", "isDoubleArray", "isDoubleOrFloatWithoutNullability", "isFloat", "isFloatArray", "isInt", "isIntArray", "isIterable", "isKClass", "isKotlinResult", "isLong", "isLongArray", "isMarkedNullable", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "isNotNullClassType", "isNothing", "isNullableAny", "isNullableArray", "isNullableClassType", "isNullableContinuation", "isNullableNothing", "isNullablePrimitiveType", "isNullableString", Namer.IS_NUMBER, "isPrimitiveType", "isSequence", "isShort", "isShortArray", "isString", "isStringClassType", "isUByte", "isUInt", "isULong", "isUShort", "isUnit", "isUnsignedType", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrTypePredicatesKt {
    private static final Map<IdSignature.CommonSignature, PrimitiveType> idSignatureToPrimitiveType;
    private static final Map<IdSignature.CommonSignature, UnsignedType> idSignatureToUnsignedType;
    private static final Map<PrimitiveType, IdSignature.CommonSignature> primitiveArrayTypesSignatures;
    private static final Map<Name, PrimitiveType> shortNameToPrimitiveType;
    private static final Map<Name, UnsignedType> shortNameToUnsignedType;

    private static final boolean isNotNullClassType(IrType irType, IdSignature.CommonSignature commonSignature) {
        return isClassType(irType, commonSignature, (Boolean) false);
    }

    private static final boolean isNullableClassType(IrType irType, IdSignature.CommonSignature commonSignature) {
        return isClassType(irType, commonSignature, (Boolean) true);
    }

    public static final IdSignature.CommonSignature getPublicSignature(FqName packageFqName, String name2) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        String strAsString = packageFqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return new IdSignature.CommonSignature(strAsString, name2, null, 0L, packageFqName.child(Name.identifier(name2)).asString());
    }

    static /* synthetic */ boolean isClassType$default(IrType irType, IdSignature.CommonSignature commonSignature, Boolean bool, int i, Object obj) {
        if ((i & 2) != 0) {
            bool = null;
        }
        return isClassType(irType, commonSignature, bool);
    }

    private static final boolean isClassType(IrType irType, IdSignature.CommonSignature commonSignature, Boolean bool) {
        if (!(irType instanceof IrSimpleType)) {
            return false;
        }
        if (bool != null && !Intrinsics.areEqual(Boolean.valueOf(isMarkedNullable((IrSimpleType) irType)), bool)) {
            return false;
        }
        IrSimpleType irSimpleType = (IrSimpleType) irType;
        if (!Intrinsics.areEqual(commonSignature, irSimpleType.getClassifier().getSignature())) {
            IrSymbolOwner owner = irSimpleType.getClassifier().getOwner();
            if (!(owner instanceof IrClass) || !hasFqNameEqualToSignature((IrClass) owner, commonSignature)) {
                return false;
            }
        }
        return true;
    }

    private static final boolean hasFqNameEqualToSignature(IrClass irClass, IdSignature.CommonSignature commonSignature) {
        return Intrinsics.areEqual(irClass.getName().asString(), commonSignature.getShortName()) && AdditionalIrUtilsKt.hasEqualFqName(irClass, new FqName(new StringBuilder().append(commonSignature.getPackageFqName()).append('.').append(commonSignature.getDeclarationFqName()).toString()));
    }

    public static final boolean isClassWithFqName(IrClassifierSymbol irClassifierSymbol, FqNameUnsafe fqName) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return (irClassifierSymbol instanceof IrClassSymbol) && classFqNameEquals((IrClassSymbol) irClassifierSymbol, fqName);
    }

    private static final boolean classFqNameEquals(IrClassSymbol irClassSymbol, FqNameUnsafe fqNameUnsafe) {
        irClassSymbol.isBound();
        return classFqNameEquals(irClassSymbol.getOwner(), fqNameUnsafe);
    }

    static {
        PrimitiveType[] primitiveTypeArrValues = PrimitiveType.values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(primitiveTypeArrValues.length), 16));
        for (PrimitiveType primitiveType : primitiveTypeArrValues) {
            FqName fqName = StandardNames.BUILT_INS_PACKAGE_FQ_NAME;
            String strAsString = primitiveType.getTypeName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            linkedHashMap.put(getPublicSignature(fqName, strAsString), primitiveType);
        }
        idSignatureToPrimitiveType = linkedHashMap;
        PrimitiveType[] primitiveTypeArrValues2 = PrimitiveType.values();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(primitiveTypeArrValues2.length), 16));
        for (PrimitiveType primitiveType2 : primitiveTypeArrValues2) {
            linkedHashMap2.put(primitiveType2.getTypeName(), primitiveType2);
        }
        shortNameToPrimitiveType = linkedHashMap2;
        UnsignedType[] unsignedTypeArrValues = UnsignedType.values();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(unsignedTypeArrValues.length), 16));
        for (UnsignedType unsignedType : unsignedTypeArrValues) {
            FqName fqName2 = StandardNames.BUILT_INS_PACKAGE_FQ_NAME;
            String strAsString2 = unsignedType.getTypeName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            linkedHashMap3.put(getPublicSignature(fqName2, strAsString2), unsignedType);
        }
        idSignatureToUnsignedType = linkedHashMap3;
        UnsignedType[] unsignedTypeArrValues2 = UnsignedType.values();
        LinkedHashMap linkedHashMap4 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(unsignedTypeArrValues2.length), 16));
        for (UnsignedType unsignedType2 : unsignedTypeArrValues2) {
            linkedHashMap4.put(unsignedType2.getTypeName(), unsignedType2);
        }
        shortNameToUnsignedType = linkedHashMap4;
        PrimitiveType[] primitiveTypeArrValues3 = PrimitiveType.values();
        LinkedHashMap linkedHashMap5 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(primitiveTypeArrValues3.length), 16));
        for (PrimitiveType primitiveType3 : primitiveTypeArrValues3) {
            linkedHashMap5.put(primitiveType3, getPublicSignature(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, primitiveType3.getTypeName().asString() + "Array"));
        }
        primitiveArrayTypesSignatures = linkedHashMap5;
    }

    public static final Map<PrimitiveType, IdSignature.CommonSignature> getPrimitiveArrayTypesSignatures() {
        return primitiveArrayTypesSignatures;
    }

    private static final boolean classFqNameEquals(IrClass irClass, FqNameUnsafe fqNameUnsafe) {
        FqName safe = fqNameUnsafe.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
        return AdditionalIrUtilsKt.hasEqualFqName(irClass, safe);
    }

    public static final boolean isAny(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.any);
    }

    public static final boolean isNullableAny(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNullableClassType(irType, IdSignatureValues.any);
    }

    public static final boolean isString(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.string);
    }

    public static final boolean isNullableString(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNullableClassType(irType, IdSignatureValues.string);
    }

    public static final boolean isStringClassType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isClassType$default(irType, IdSignatureValues.string, null, 2, null);
    }

    public static final boolean isArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.array);
    }

    public static final boolean isNullableArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNullableClassType(irType, IdSignatureValues.array);
    }

    public static final boolean isCollection(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.collection);
    }

    public static final boolean isNothing(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.nothing);
    }

    public static final boolean isNullableNothing(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNullableClassType(irType, IdSignatureValues.nothing);
    }

    public static /* synthetic */ boolean isPrimitiveType$default(IrType irType, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return isPrimitiveType(irType, z);
    }

    public static final boolean isPrimitiveType(IrType irType, boolean z) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return z == isMarkedNullable(irType) && getPrimitiveType(irType) != null;
    }

    public static final boolean isNullablePrimitiveType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isPrimitiveType(irType, true);
    }

    public static final PrimitiveType getPrimitiveType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return (PrimitiveType) getPrimitiveOrUnsignedType(irType, idSignatureToPrimitiveType, shortNameToPrimitiveType);
    }

    public static /* synthetic */ boolean isUnsignedType$default(IrType irType, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return isUnsignedType(irType, z);
    }

    public static final boolean isUnsignedType(IrType irType, boolean z) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return z == isMarkedNullable(irType) && getUnsignedType(irType) != null;
    }

    public static final UnsignedType getUnsignedType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return (UnsignedType) getPrimitiveOrUnsignedType(irType, idSignatureToUnsignedType, shortNameToUnsignedType);
    }

    public static final <T extends Enum<T>> T getPrimitiveOrUnsignedType(IrType irType, Map<IdSignature.CommonSignature, ? extends T> byIdSignature, Map<Name, ? extends T> byShortName) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(byIdSignature, "byIdSignature");
        Intrinsics.checkNotNullParameter(byShortName, "byShortName");
        if (!(irType instanceof IrSimpleType)) {
            return null;
        }
        IrClassifierSymbol classifier = ((IrSimpleType) irType).getClassifier();
        IrClassSymbol irClassSymbol = classifier instanceof IrClassSymbol ? (IrClassSymbol) classifier : null;
        if (irClassSymbol == null) {
            return null;
        }
        if (irClassSymbol.getSignature() != null) {
            return byIdSignature.get(irClassSymbol.getSignature());
        }
        IrClass owner = irClassSymbol.getOwner();
        IrDeclarationParent parent = owner.getParent();
        if ((parent instanceof IrPackageFragment) && Intrinsics.areEqual(((IrPackageFragment) parent).getPackageFqName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME)) {
            return byShortName.get(owner.getName());
        }
        return null;
    }

    public static final boolean isMarkedNullable(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrSimpleType irSimpleType = irType instanceof IrSimpleType ? (IrSimpleType) irType : null;
        return (irSimpleType != null ? irSimpleType.getNullability() : null) == SimpleTypeNullability.MARKED_NULLABLE;
    }

    public static final boolean isMarkedNullable(IrSimpleType irSimpleType) {
        Intrinsics.checkNotNullParameter(irSimpleType, "<this>");
        return irSimpleType.getNullability() == SimpleTypeNullability.MARKED_NULLABLE;
    }

    public static final boolean isUnit(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.unit);
    }

    public static final boolean isBoolean(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues._boolean);
    }

    public static final boolean isChar(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues._char);
    }

    public static final boolean isByte(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues._byte);
    }

    public static final boolean isShort(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues._short);
    }

    public static final boolean isInt(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues._int);
    }

    public static final boolean isLong(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues._long);
    }

    public static final boolean isUByte(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.uByte);
    }

    public static final boolean isUShort(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.uShort);
    }

    public static final boolean isUInt(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.uInt);
    }

    public static final boolean isULong(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.uLong);
    }

    public static final boolean isFloat(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues._float);
    }

    public static final boolean isDouble(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues._double);
    }

    public static final boolean isNumber(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.number);
    }

    public static final boolean isDoubleOrFloatWithoutNullability(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isClassType(irType, IdSignatureValues._double, (Boolean) null) || isClassType(irType, IdSignatureValues._float, (Boolean) null);
    }

    public static final boolean isComparable(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.comparable);
    }

    public static final boolean isCharSequence(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.charSequence);
    }

    public static final boolean isIterable(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.iterable);
    }

    public static final boolean isSequence(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isNotNullClassType(irType, IdSignatureValues.sequence);
    }

    public static final boolean isBooleanArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IdSignature.CommonSignature commonSignature = primitiveArrayTypesSignatures.get(PrimitiveType.BOOLEAN);
        Intrinsics.checkNotNull(commonSignature);
        return isNotNullClassType(irType, commonSignature);
    }

    public static final boolean isCharArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IdSignature.CommonSignature commonSignature = primitiveArrayTypesSignatures.get(PrimitiveType.CHAR);
        Intrinsics.checkNotNull(commonSignature);
        return isNotNullClassType(irType, commonSignature);
    }

    public static final boolean isByteArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IdSignature.CommonSignature commonSignature = primitiveArrayTypesSignatures.get(PrimitiveType.BYTE);
        Intrinsics.checkNotNull(commonSignature);
        return isNotNullClassType(irType, commonSignature);
    }

    public static final boolean isShortArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IdSignature.CommonSignature commonSignature = primitiveArrayTypesSignatures.get(PrimitiveType.SHORT);
        Intrinsics.checkNotNull(commonSignature);
        return isNotNullClassType(irType, commonSignature);
    }

    public static final boolean isIntArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IdSignature.CommonSignature commonSignature = primitiveArrayTypesSignatures.get(PrimitiveType.INT);
        Intrinsics.checkNotNull(commonSignature);
        return isNotNullClassType(irType, commonSignature);
    }

    public static final boolean isLongArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IdSignature.CommonSignature commonSignature = primitiveArrayTypesSignatures.get(PrimitiveType.LONG);
        Intrinsics.checkNotNull(commonSignature);
        return isNotNullClassType(irType, commonSignature);
    }

    public static final boolean isFloatArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IdSignature.CommonSignature commonSignature = primitiveArrayTypesSignatures.get(PrimitiveType.FLOAT);
        Intrinsics.checkNotNull(commonSignature);
        return isNotNullClassType(irType, commonSignature);
    }

    public static final boolean isDoubleArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IdSignature.CommonSignature commonSignature = primitiveArrayTypesSignatures.get(PrimitiveType.DOUBLE);
        Intrinsics.checkNotNull(commonSignature);
        return isNotNullClassType(irType, commonSignature);
    }

    public static final boolean isClassType(IrType irType, FqNameUnsafe fqName, boolean z) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (!(irType instanceof IrSimpleType)) {
            return false;
        }
        IrSimpleType irSimpleType = (IrSimpleType) irType;
        if (isMarkedNullable(irSimpleType) != z) {
            return false;
        }
        return isClassWithFqName(irSimpleType.getClassifier(), fqName);
    }

    public static final boolean isKotlinResult(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        FqNameUnsafe unsafe = StandardNames.RESULT_FQ_NAME.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        return isClassType(irType, unsafe, false);
    }

    public static final boolean isNullableContinuation(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        FqNameUnsafe unsafe = StandardNames.CONTINUATION_INTERFACE_FQ_NAME.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        return isClassType(irType, unsafe, true);
    }

    public static final boolean isKClass(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return isClassType(irType, StandardNames.FqNames.kClass, false);
    }
}
