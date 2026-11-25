package org.jetbrains.kotlin.builtins;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.builtins.functions.BuiltInFictitiousFunctionClassFactory;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeAttributesKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;

/* JADX INFO: loaded from: classes2.dex */
public abstract class KotlinBuiltIns {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Name BUILTINS_MODULE_NAME = Name.special("<built-ins module>");
    private final MemoizedFunctionToNotNull<Name, ClassDescriptor> builtInClassesByName;
    private final NotNullLazyValue<Collection<PackageViewDescriptor>> builtInPackagesImportedByDefault;
    private ModuleDescriptorImpl builtInsModule;
    private NotNullLazyValue<ModuleDescriptorImpl> postponedBuiltInsModule;
    private final NotNullLazyValue<Primitives> primitives;
    private final StorageManager storageManager;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 13:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 69:
            case 70:
            case 71:
            case 75:
            case 82:
            case 85:
            case 87:
            case 88:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 9:
            case 10:
            case 12:
            case 14:
            case 16:
            case 17:
            case 47:
            case 54:
            case 68:
            case 72:
            case 73:
            case 74:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
            case 86:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 13:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 69:
            case 70:
            case 71:
            case 75:
            case 82:
            case 85:
            case 87:
            case 88:
                i2 = 2;
                break;
            case 9:
            case 10:
            case 12:
            case 14:
            case 16:
            case 17:
            case 47:
            case 54:
            case 68:
            case 72:
            case 73:
            case 74:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
            case 86:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 73:
                objArr[0] = "module";
                break;
            case 2:
                objArr[0] = "computation";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 13:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 69:
            case 70:
            case 71:
            case 75:
            case 82:
            case 85:
            case 87:
            case 88:
                objArr[0] = "org/jetbrains/kotlin/builtins/KotlinBuiltIns";
                break;
            case 9:
            case 10:
            case 77:
            case 78:
            case 90:
            case 97:
            case 104:
            case 108:
            case 109:
            case 144:
            case 147:
            case 148:
            case 150:
            case 158:
            case 159:
            case 160:
            case 161:
                objArr[0] = "descriptor";
                break;
            case 12:
            case 99:
            case 101:
            case 103:
            case 105:
            case 107:
            case 136:
                objArr[0] = "fqName";
                break;
            case 14:
                objArr[0] = Namer.METADATA_SIMPLE_NAME;
                break;
            case 16:
            case 17:
            case 54:
            case 89:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 98:
            case 100:
            case 106:
            case 110:
            case 111:
            case 112:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 145:
            case 146:
            case 149:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 163:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 47:
                objArr[0] = "classSimpleName";
                break;
            case 68:
                objArr[0] = "arrayType";
                break;
            case 72:
                objArr[0] = "notNullArrayType";
                break;
            case 74:
                objArr[0] = "primitiveType";
                break;
            case 76:
                objArr[0] = "kotlinType";
                break;
            case 79:
            case 83:
                objArr[0] = "projectionType";
                break;
            case 80:
            case 84:
            case 86:
                objArr[0] = "argument";
                break;
            case 81:
                objArr[0] = "annotations";
                break;
            case 102:
                objArr[0] = "typeConstructor";
                break;
            case 113:
                objArr[0] = "classDescriptor";
                break;
            case 162:
                objArr[0] = "declarationDescriptor";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "getAdditionalClassPartsProvider";
                break;
            case 4:
                objArr[1] = "getPlatformDependentDeclarationFilter";
                break;
            case 5:
                objArr[1] = "getClassDescriptorFactories";
                break;
            case 6:
                objArr[1] = "getStorageManager";
                break;
            case 7:
                objArr[1] = "getBuiltInsModule";
                break;
            case 8:
                objArr[1] = "getBuiltInPackagesImportedByDefault";
                break;
            case 9:
            case 10:
            case 12:
            case 14:
            case 16:
            case 17:
            case 47:
            case 54:
            case 68:
            case 72:
            case 73:
            case 74:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
            case 86:
            default:
                objArr[1] = "org/jetbrains/kotlin/builtins/KotlinBuiltIns";
                break;
            case 11:
                objArr[1] = "getBuiltInsPackageScope";
                break;
            case 13:
                objArr[1] = "getBuiltInClassByFqName";
                break;
            case 15:
                objArr[1] = "getBuiltInClassByName";
                break;
            case 18:
                objArr[1] = "getSuspendFunction";
                break;
            case 19:
                objArr[1] = "getKFunction";
                break;
            case 20:
                objArr[1] = "getKSuspendFunction";
                break;
            case 21:
                objArr[1] = Namer.GET_KCLASS;
                break;
            case 22:
                objArr[1] = "getKType";
                break;
            case 23:
                objArr[1] = "getKCallable";
                break;
            case 24:
                objArr[1] = "getKProperty";
                break;
            case 25:
                objArr[1] = "getKProperty0";
                break;
            case 26:
                objArr[1] = "getKProperty1";
                break;
            case 27:
                objArr[1] = "getKProperty2";
                break;
            case 28:
                objArr[1] = "getKMutableProperty0";
                break;
            case 29:
                objArr[1] = "getKMutableProperty1";
                break;
            case 30:
                objArr[1] = "getKMutableProperty2";
                break;
            case 31:
                objArr[1] = "getIterator";
                break;
            case 32:
                objArr[1] = "getIterable";
                break;
            case 33:
                objArr[1] = "getMutableIterable";
                break;
            case 34:
                objArr[1] = "getMutableIterator";
                break;
            case 35:
                objArr[1] = "getCollection";
                break;
            case 36:
                objArr[1] = "getMutableCollection";
                break;
            case 37:
                objArr[1] = "getList";
                break;
            case 38:
                objArr[1] = "getMutableList";
                break;
            case 39:
                objArr[1] = "getSet";
                break;
            case 40:
                objArr[1] = "getMutableSet";
                break;
            case 41:
                objArr[1] = "getMap";
                break;
            case 42:
                objArr[1] = "getMutableMap";
                break;
            case 43:
                objArr[1] = "getMapEntry";
                break;
            case 44:
                objArr[1] = "getMutableMapEntry";
                break;
            case 45:
                objArr[1] = "getListIterator";
                break;
            case 46:
                objArr[1] = "getMutableListIterator";
                break;
            case 48:
                objArr[1] = "getBuiltInTypeByClassName";
                break;
            case 49:
                objArr[1] = "getNothingType";
                break;
            case 50:
                objArr[1] = "getNullableNothingType";
                break;
            case 51:
                objArr[1] = "getAnyType";
                break;
            case 52:
                objArr[1] = "getNullableAnyType";
                break;
            case 53:
                objArr[1] = "getDefaultBound";
                break;
            case 55:
                objArr[1] = "getPrimitiveKotlinType";
                break;
            case 56:
                objArr[1] = "getNumberType";
                break;
            case 57:
                objArr[1] = "getByteType";
                break;
            case 58:
                objArr[1] = "getShortType";
                break;
            case 59:
                objArr[1] = "getIntType";
                break;
            case 60:
                objArr[1] = "getLongType";
                break;
            case 61:
                objArr[1] = "getFloatType";
                break;
            case 62:
                objArr[1] = "getDoubleType";
                break;
            case 63:
                objArr[1] = "getCharType";
                break;
            case 64:
                objArr[1] = "getBooleanType";
                break;
            case 65:
                objArr[1] = "getUnitType";
                break;
            case 66:
                objArr[1] = "getStringType";
                break;
            case 67:
                objArr[1] = "getIterableType";
                break;
            case 69:
            case 70:
            case 71:
                objArr[1] = "getArrayElementType";
                break;
            case 75:
                objArr[1] = "getPrimitiveArrayKotlinType";
                break;
            case 82:
            case 85:
                objArr[1] = "getArrayType";
                break;
            case 87:
                objArr[1] = "getEnumType";
                break;
            case 88:
                objArr[1] = "getAnnotationType";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "setBuiltInsModule";
                break;
            case 2:
                objArr[2] = "setPostponedBuiltinsModuleComputation";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 13:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 69:
            case 70:
            case 71:
            case 75:
            case 82:
            case 85:
            case 87:
            case 88:
                break;
            case 9:
                objArr[2] = "isBuiltIn";
                break;
            case 10:
                objArr[2] = "isUnderKotlinPackage";
                break;
            case 12:
                objArr[2] = "getBuiltInClassByFqName";
                break;
            case 14:
                objArr[2] = "getBuiltInClassByName";
                break;
            case 16:
                objArr[2] = "getPrimitiveClassDescriptor";
                break;
            case 17:
                objArr[2] = "getPrimitiveArrayClassDescriptor";
                break;
            case 47:
                objArr[2] = "getBuiltInTypeByClassName";
                break;
            case 54:
                objArr[2] = "getPrimitiveKotlinType";
                break;
            case 68:
                objArr[2] = "getArrayElementType";
                break;
            case 72:
            case 73:
                objArr[2] = "getElementTypeForUnsignedArray";
                break;
            case 74:
                objArr[2] = "getPrimitiveArrayKotlinType";
                break;
            case 76:
                objArr[2] = "getPrimitiveArrayKotlinTypeByPrimitiveKotlinType";
                break;
            case 77:
            case 94:
                objArr[2] = "getPrimitiveType";
                break;
            case 78:
                objArr[2] = "getPrimitiveArrayType";
                break;
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
                objArr[2] = "getArrayType";
                break;
            case 86:
                objArr[2] = "getEnumType";
                break;
            case 89:
                objArr[2] = "isArray";
                break;
            case 90:
            case 91:
                objArr[2] = "isArrayOrPrimitiveArray";
                break;
            case 92:
                objArr[2] = "isPrimitiveArray";
                break;
            case 93:
                objArr[2] = "getPrimitiveArrayElementType";
                break;
            case 95:
                objArr[2] = "isPrimitiveType";
                break;
            case 96:
                objArr[2] = "isPrimitiveTypeOrNullablePrimitiveType";
                break;
            case 97:
                objArr[2] = "isPrimitiveClass";
                break;
            case 98:
            case 99:
            case 100:
            case 101:
                objArr[2] = "isConstructedFromGivenClass";
                break;
            case 102:
            case 103:
                objArr[2] = "isTypeConstructorForGivenClass";
                break;
            case 104:
            case 105:
                objArr[2] = "classFqNameEquals";
                break;
            case 106:
            case 107:
                objArr[2] = "isNotNullConstructedFromGivenClass";
                break;
            case 108:
                objArr[2] = "isSpecialClassWithNoSupertypes";
                break;
            case 109:
            case 110:
                objArr[2] = "isAny";
                break;
            case 111:
            case 113:
                objArr[2] = "isBoolean";
                break;
            case 112:
                objArr[2] = "isBooleanOrNullableBoolean";
                break;
            case 114:
                objArr[2] = Namer.IS_NUMBER;
                break;
            case 115:
                objArr[2] = Namer.IS_CHAR;
                break;
            case 116:
                objArr[2] = "isCharOrNullableChar";
                break;
            case 117:
                objArr[2] = "isInt";
                break;
            case 118:
                objArr[2] = "isByte";
                break;
            case 119:
                objArr[2] = "isLong";
                break;
            case 120:
                objArr[2] = "isLongOrNullableLong";
                break;
            case 121:
                objArr[2] = "isShort";
                break;
            case 122:
                objArr[2] = "isFloat";
                break;
            case 123:
                objArr[2] = "isFloatOrNullableFloat";
                break;
            case 124:
                objArr[2] = "isDouble";
                break;
            case 125:
                objArr[2] = "isUByte";
                break;
            case 126:
                objArr[2] = "isUShort";
                break;
            case 127:
                objArr[2] = "isUInt";
                break;
            case 128:
                objArr[2] = "isULong";
                break;
            case 129:
                objArr[2] = "isUByteArray";
                break;
            case 130:
                objArr[2] = "isUShortArray";
                break;
            case 131:
                objArr[2] = "isUIntArray";
                break;
            case 132:
                objArr[2] = "isULongArray";
                break;
            case 133:
                objArr[2] = "isUnsignedArrayType";
                break;
            case 134:
                objArr[2] = "isDoubleOrNullableDouble";
                break;
            case 135:
            case 136:
                objArr[2] = "isConstructedFromGivenClassAndNotNullable";
                break;
            case 137:
                objArr[2] = "isNothing";
                break;
            case 138:
                objArr[2] = "isNullableNothing";
                break;
            case 139:
                objArr[2] = "isNothingOrNullableNothing";
                break;
            case 140:
                objArr[2] = "isAnyOrNullableAny";
                break;
            case 141:
                objArr[2] = "isNullableAny";
                break;
            case 142:
                objArr[2] = "isDefaultBound";
                break;
            case 143:
                objArr[2] = "isUnit";
                break;
            case 144:
                objArr[2] = "mayReturnNonUnitValue";
                break;
            case 145:
                objArr[2] = "isUnitOrNullableUnit";
                break;
            case 146:
                objArr[2] = "isBooleanOrSubtype";
                break;
            case 147:
                objArr[2] = "isMemberOfAny";
                break;
            case 148:
            case 149:
                objArr[2] = "isEnum";
                break;
            case 150:
            case 151:
                objArr[2] = "isComparable";
                break;
            case 152:
                objArr[2] = "isCollectionOrNullableCollection";
                break;
            case 153:
                objArr[2] = "isListOrNullableList";
                break;
            case 154:
                objArr[2] = "isSetOrNullableSet";
                break;
            case 155:
                objArr[2] = "isMapOrNullableMap";
                break;
            case 156:
                objArr[2] = "isIterableOrNullableIterable";
                break;
            case 157:
                objArr[2] = "isThrowableOrNullableThrowable";
                break;
            case 158:
                objArr[2] = "isThrowable";
                break;
            case 159:
                objArr[2] = "isKClass";
                break;
            case 160:
                objArr[2] = "isNonPrimitiveArray";
                break;
            case 161:
                objArr[2] = "isCloneable";
                break;
            case 162:
                objArr[2] = "isDeprecated";
                break;
            case 163:
                objArr[2] = "isNotNullOrNullableFunctionSupertype";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 13:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 69:
            case 70:
            case 71:
            case 75:
            case 82:
            case 85:
            case 87:
            case 88:
                throw new IllegalStateException(str2);
            case 9:
            case 10:
            case 12:
            case 14:
            case 16:
            case 17:
            case 47:
            case 54:
            case 68:
            case 72:
            case 73:
            case 74:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
            case 86:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    protected KotlinBuiltIns(StorageManager storageManager) {
        if (storageManager == null) {
            $$$reportNull$$$0(0);
        }
        this.storageManager = storageManager;
        this.builtInPackagesImportedByDefault = storageManager.createLazyValue(new Function0<Collection<PackageViewDescriptor>>() { // from class: org.jetbrains.kotlin.builtins.KotlinBuiltIns.1
            @Override // kotlin.jvm.functions.Function0
            public Collection<PackageViewDescriptor> invoke() {
                return Arrays.asList(KotlinBuiltIns.this.getBuiltInsModule().getPackage(StandardNames.BUILT_INS_PACKAGE_FQ_NAME), KotlinBuiltIns.this.getBuiltInsModule().getPackage(StandardNames.COLLECTIONS_PACKAGE_FQ_NAME), KotlinBuiltIns.this.getBuiltInsModule().getPackage(StandardNames.RANGES_PACKAGE_FQ_NAME), KotlinBuiltIns.this.getBuiltInsModule().getPackage(StandardNames.ANNOTATION_PACKAGE_FQ_NAME));
            }
        });
        this.primitives = storageManager.createLazyValue(new Function0<Primitives>() { // from class: org.jetbrains.kotlin.builtins.KotlinBuiltIns.2
            @Override // kotlin.jvm.functions.Function0
            public Primitives invoke() {
                EnumMap enumMap = new EnumMap(PrimitiveType.class);
                HashMap map = new HashMap();
                HashMap map2 = new HashMap();
                for (PrimitiveType primitiveType : PrimitiveType.values()) {
                    SimpleType builtInTypeByClassName = KotlinBuiltIns.this.getBuiltInTypeByClassName(primitiveType.getTypeName().asString());
                    SimpleType builtInTypeByClassName2 = KotlinBuiltIns.this.getBuiltInTypeByClassName(primitiveType.getArrayTypeName().asString());
                    enumMap.put(primitiveType, builtInTypeByClassName2);
                    map.put(builtInTypeByClassName, builtInTypeByClassName2);
                    map2.put(builtInTypeByClassName2, builtInTypeByClassName);
                }
                return new Primitives(enumMap, map, map2);
            }
        });
        this.builtInClassesByName = storageManager.createMemoizedFunction(new Function1<Name, ClassDescriptor>() { // from class: org.jetbrains.kotlin.builtins.KotlinBuiltIns.3
            @Override // kotlin.jvm.functions.Function1
            public ClassDescriptor invoke(Name name2) {
                ClassifierDescriptor contributedClassifier = KotlinBuiltIns.this.getBuiltInsPackageScope().mo7465getContributedClassifier(name2, NoLookupLocation.FROM_BUILTINS);
                if (contributedClassifier == null) {
                    throw new AssertionError("Built-in class " + StandardNames.BUILT_INS_PACKAGE_FQ_NAME.child(name2) + " is not found");
                }
                if (!(contributedClassifier instanceof ClassDescriptor)) {
                    throw new AssertionError("Must be a class descriptor " + name2 + ", but was " + contributedClassifier);
                }
                return (ClassDescriptor) contributedClassifier;
            }
        });
    }

    protected void createBuiltInsModule(boolean z) {
        ModuleDescriptorImpl moduleDescriptorImpl = new ModuleDescriptorImpl(BUILTINS_MODULE_NAME, this.storageManager, this, null);
        this.builtInsModule = moduleDescriptorImpl;
        moduleDescriptorImpl.initialize(BuiltInsLoader.INSTANCE.getInstance().createPackageFragmentProvider(this.storageManager, this.builtInsModule, getClassDescriptorFactories(), getPlatformDependentDeclarationFilter(), getAdditionalClassPartsProvider(), z));
        ModuleDescriptorImpl moduleDescriptorImpl2 = this.builtInsModule;
        moduleDescriptorImpl2.setDependencies(moduleDescriptorImpl2);
    }

    public void setBuiltInsModule(final ModuleDescriptorImpl moduleDescriptorImpl) {
        if (moduleDescriptorImpl == null) {
            $$$reportNull$$$0(1);
        }
        this.storageManager.compute(new Function0<Void>() { // from class: org.jetbrains.kotlin.builtins.KotlinBuiltIns.4
            @Override // kotlin.jvm.functions.Function0
            public Void invoke() {
                if (KotlinBuiltIns.this.builtInsModule != null) {
                    throw new AssertionError("Built-ins module is already set: " + KotlinBuiltIns.this.builtInsModule + " (attempting to reset to " + moduleDescriptorImpl + Constant.AFTER_QUTO);
                }
                KotlinBuiltIns.this.builtInsModule = moduleDescriptorImpl;
                return null;
            }
        });
    }

    public void setPostponedBuiltinsModuleComputation(Function0<ModuleDescriptorImpl> function0) {
        if (function0 == null) {
            $$$reportNull$$$0(2);
        }
        this.postponedBuiltInsModule = this.storageManager.createLazyValue(function0);
    }

    protected AdditionalClassPartsProvider getAdditionalClassPartsProvider() {
        AdditionalClassPartsProvider.None none = AdditionalClassPartsProvider.None.INSTANCE;
        if (none == null) {
            $$$reportNull$$$0(3);
        }
        return none;
    }

    protected PlatformDependentDeclarationFilter getPlatformDependentDeclarationFilter() {
        PlatformDependentDeclarationFilter.NoPlatformDependent noPlatformDependent = PlatformDependentDeclarationFilter.NoPlatformDependent.INSTANCE;
        if (noPlatformDependent == null) {
            $$$reportNull$$$0(4);
        }
        return noPlatformDependent;
    }

    protected Iterable<ClassDescriptorFactory> getClassDescriptorFactories() {
        List listSingletonList = Collections.singletonList(new BuiltInFictitiousFunctionClassFactory(this.storageManager, getBuiltInsModule()));
        if (listSingletonList == null) {
            $$$reportNull$$$0(5);
        }
        return listSingletonList;
    }

    protected StorageManager getStorageManager() {
        StorageManager storageManager = this.storageManager;
        if (storageManager == null) {
            $$$reportNull$$$0(6);
        }
        return storageManager;
    }

    private static class Primitives {
        public final Map<SimpleType, SimpleType> kotlinArrayTypeToPrimitiveKotlinType;
        public final Map<KotlinType, SimpleType> primitiveKotlinTypeToKotlinArrayType;
        public final Map<PrimitiveType, SimpleType> primitiveTypeToArrayKotlinType;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "primitiveKotlinTypeToKotlinArrayType";
            } else if (i != 2) {
                objArr[0] = "primitiveTypeToArrayKotlinType";
            } else {
                objArr[0] = "kotlinArrayTypeToPrimitiveKotlinType";
            }
            objArr[1] = "org/jetbrains/kotlin/builtins/KotlinBuiltIns$Primitives";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private Primitives(Map<PrimitiveType, SimpleType> map, Map<KotlinType, SimpleType> map2, Map<SimpleType, SimpleType> map3) {
            if (map == null) {
                $$$reportNull$$$0(0);
            }
            if (map2 == null) {
                $$$reportNull$$$0(1);
            }
            if (map3 == null) {
                $$$reportNull$$$0(2);
            }
            this.primitiveTypeToArrayKotlinType = map;
            this.primitiveKotlinTypeToKotlinArrayType = map2;
            this.kotlinArrayTypeToPrimitiveKotlinType = map3;
        }
    }

    public ModuleDescriptorImpl getBuiltInsModule() {
        if (this.builtInsModule == null) {
            this.builtInsModule = this.postponedBuiltInsModule.invoke();
        }
        ModuleDescriptorImpl moduleDescriptorImpl = this.builtInsModule;
        if (moduleDescriptorImpl == null) {
            $$$reportNull$$$0(7);
        }
        return moduleDescriptorImpl;
    }

    public Collection<PackageViewDescriptor> getBuiltInPackagesImportedByDefault() {
        Collection<PackageViewDescriptor> collectionInvoke = this.builtInPackagesImportedByDefault.invoke();
        if (collectionInvoke == null) {
            $$$reportNull$$$0(8);
        }
        return collectionInvoke;
    }

    public static boolean isBuiltIn(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        return DescriptorUtils.getParentOfType(declarationDescriptor, BuiltInsPackageFragment.class, false) != null;
    }

    public static boolean isUnderKotlinPackage(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        while (declarationDescriptor != null) {
            if (declarationDescriptor instanceof PackageFragmentDescriptor) {
                return ((PackageFragmentDescriptor) declarationDescriptor).getFqName().startsWith(StandardNames.BUILT_INS_PACKAGE_NAME);
            }
            declarationDescriptor = declarationDescriptor.getModuleDescriptor();
        }
        return false;
    }

    public MemberScope getBuiltInsPackageScope() {
        MemberScope memberScope = getBuiltInsModule().getPackage(StandardNames.BUILT_INS_PACKAGE_FQ_NAME).getMemberScope();
        if (memberScope == null) {
            $$$reportNull$$$0(11);
        }
        return memberScope;
    }

    public ClassDescriptor getBuiltInClassByFqName(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(12);
        }
        ClassDescriptor classDescriptorResolveClassByFqName = DescriptorUtilKt.resolveClassByFqName(getBuiltInsModule(), fqName, NoLookupLocation.FROM_BUILTINS);
        if (classDescriptorResolveClassByFqName == null) {
            $$$reportNull$$$0(13);
        }
        return classDescriptorResolveClassByFqName;
    }

    private ClassDescriptor getBuiltInClassByName(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        ClassDescriptor classDescriptorInvoke = this.builtInClassesByName.invoke(Name.identifier(str));
        if (classDescriptorInvoke == null) {
            $$$reportNull$$$0(15);
        }
        return classDescriptorInvoke;
    }

    public ClassDescriptor getAny() {
        return getBuiltInClassByName("Any");
    }

    public ClassDescriptor getNothing() {
        return getBuiltInClassByName("Nothing");
    }

    private ClassDescriptor getPrimitiveClassDescriptor(PrimitiveType primitiveType) {
        if (primitiveType == null) {
            $$$reportNull$$$0(16);
        }
        return getBuiltInClassByName(primitiveType.getTypeName().asString());
    }

    public ClassDescriptor getByte() {
        return getPrimitiveClassDescriptor(PrimitiveType.BYTE);
    }

    public ClassDescriptor getShort() {
        return getPrimitiveClassDescriptor(PrimitiveType.SHORT);
    }

    public ClassDescriptor getInt() {
        return getPrimitiveClassDescriptor(PrimitiveType.INT);
    }

    public ClassDescriptor getLong() {
        return getPrimitiveClassDescriptor(PrimitiveType.LONG);
    }

    public ClassDescriptor getFloat() {
        return getPrimitiveClassDescriptor(PrimitiveType.FLOAT);
    }

    public ClassDescriptor getDouble() {
        return getPrimitiveClassDescriptor(PrimitiveType.DOUBLE);
    }

    public ClassDescriptor getChar() {
        return getPrimitiveClassDescriptor(PrimitiveType.CHAR);
    }

    public ClassDescriptor getBoolean() {
        return getPrimitiveClassDescriptor(PrimitiveType.BOOLEAN);
    }

    public ClassDescriptor getArray() {
        return getBuiltInClassByName("Array");
    }

    public ClassDescriptor getPrimitiveArrayClassDescriptor(PrimitiveType primitiveType) {
        if (primitiveType == null) {
            $$$reportNull$$$0(17);
        }
        return getBuiltInClassByName(primitiveType.getArrayTypeName().asString());
    }

    public ClassDescriptor getNumber() {
        return getBuiltInClassByName("Number");
    }

    public ClassDescriptor getUnit() {
        return getBuiltInClassByName("Unit");
    }

    public ClassDescriptor getFunction(int i) {
        return getBuiltInClassByName(StandardNames.getFunctionName(i));
    }

    public ClassDescriptor getSuspendFunction(int i) {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.COROUTINES_PACKAGE_FQ_NAME.child(Name.identifier(StandardNames.getSuspendFunctionName(i))));
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(18);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getKFunction(int i) {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.getKFunctionFqName(i).toSafe());
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(19);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getKSuspendFunction(int i) {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.COROUTINES_PACKAGE_FQ_NAME.child(Name.identifier(FunctionTypeKind.KSuspendFunction.INSTANCE.getClassNamePrefix() + i)));
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(20);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getThrowable() {
        return getBuiltInClassByName("Throwable");
    }

    public ClassDescriptor getString() {
        return getBuiltInClassByName(CommonClassNames.JAVA_LANG_STRING_SHORT);
    }

    public ClassDescriptor getCharSequence() {
        return getBuiltInClassByName("CharSequence");
    }

    public ClassDescriptor getComparable() {
        return getBuiltInClassByName("Comparable");
    }

    public ClassDescriptor getEnum() {
        return getBuiltInClassByName("Enum");
    }

    public ClassDescriptor getAnnotation() {
        return getBuiltInClassByName("Annotation");
    }

    public ClassDescriptor getKClass() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.kClass.toSafe());
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(21);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getKType() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.kType.toSafe());
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(22);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getKCallable() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.kCallable.toSafe());
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(23);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getKProperty() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.kPropertyFqName.toSafe());
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(24);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getKProperty0() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.kProperty0.toSafe());
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(25);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getKProperty1() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.kProperty1.toSafe());
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(26);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getKProperty2() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.kProperty2.toSafe());
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(27);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getKMutableProperty0() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.kMutableProperty0.toSafe());
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(28);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getKMutableProperty1() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.kMutableProperty1.toSafe());
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(29);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getKMutableProperty2() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.kMutableProperty2.toSafe());
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(30);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getIterator() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.iterator);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(31);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getIterable() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.iterable);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(32);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getMutableIterable() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.mutableIterable);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(33);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getMutableIterator() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.mutableIterator);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(34);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getCollection() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.collection);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(35);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getMutableCollection() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.mutableCollection);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(36);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getList() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.list);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(37);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getMutableList() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.mutableList);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(38);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getSet() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.set);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(39);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getMutableSet() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.mutableSet);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(40);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getMap() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.map);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(41);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getMutableMap() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.mutableMap);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(42);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getMapEntry() {
        ClassDescriptor innerClassByName = DescriptorUtils.getInnerClassByName(getMap(), "Entry", NoLookupLocation.FROM_BUILTINS);
        if (innerClassByName == null) {
            $$$reportNull$$$0(43);
        }
        return innerClassByName;
    }

    public ClassDescriptor getMutableMapEntry() {
        ClassDescriptor innerClassByName = DescriptorUtils.getInnerClassByName(getMutableMap(), "MutableEntry", NoLookupLocation.FROM_BUILTINS);
        if (innerClassByName == null) {
            $$$reportNull$$$0(44);
        }
        return innerClassByName;
    }

    public ClassDescriptor getListIterator() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.listIterator);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(45);
        }
        return builtInClassByFqName;
    }

    public ClassDescriptor getMutableListIterator() {
        ClassDescriptor builtInClassByFqName = getBuiltInClassByFqName(StandardNames.FqNames.mutableListIterator);
        if (builtInClassByFqName == null) {
            $$$reportNull$$$0(46);
        }
        return builtInClassByFqName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SimpleType getBuiltInTypeByClassName(String str) {
        if (str == null) {
            $$$reportNull$$$0(47);
        }
        SimpleType defaultType = getBuiltInClassByName(str).getDefaultType();
        if (defaultType == null) {
            $$$reportNull$$$0(48);
        }
        return defaultType;
    }

    public SimpleType getNothingType() {
        SimpleType defaultType = getNothing().getDefaultType();
        if (defaultType == null) {
            $$$reportNull$$$0(49);
        }
        return defaultType;
    }

    public SimpleType getNullableNothingType() {
        SimpleType simpleTypeMakeNullableAsSpecified = getNothingType().makeNullableAsSpecified(true);
        if (simpleTypeMakeNullableAsSpecified == null) {
            $$$reportNull$$$0(50);
        }
        return simpleTypeMakeNullableAsSpecified;
    }

    public SimpleType getAnyType() {
        SimpleType defaultType = getAny().getDefaultType();
        if (defaultType == null) {
            $$$reportNull$$$0(51);
        }
        return defaultType;
    }

    public SimpleType getNullableAnyType() {
        SimpleType simpleTypeMakeNullableAsSpecified = getAnyType().makeNullableAsSpecified(true);
        if (simpleTypeMakeNullableAsSpecified == null) {
            $$$reportNull$$$0(52);
        }
        return simpleTypeMakeNullableAsSpecified;
    }

    public SimpleType getDefaultBound() {
        SimpleType nullableAnyType = getNullableAnyType();
        if (nullableAnyType == null) {
            $$$reportNull$$$0(53);
        }
        return nullableAnyType;
    }

    public SimpleType getPrimitiveKotlinType(PrimitiveType primitiveType) {
        if (primitiveType == null) {
            $$$reportNull$$$0(54);
        }
        SimpleType defaultType = getPrimitiveClassDescriptor(primitiveType).getDefaultType();
        if (defaultType == null) {
            $$$reportNull$$$0(55);
        }
        return defaultType;
    }

    public SimpleType getNumberType() {
        SimpleType defaultType = getNumber().getDefaultType();
        if (defaultType == null) {
            $$$reportNull$$$0(56);
        }
        return defaultType;
    }

    public SimpleType getByteType() {
        SimpleType primitiveKotlinType = getPrimitiveKotlinType(PrimitiveType.BYTE);
        if (primitiveKotlinType == null) {
            $$$reportNull$$$0(57);
        }
        return primitiveKotlinType;
    }

    public SimpleType getShortType() {
        SimpleType primitiveKotlinType = getPrimitiveKotlinType(PrimitiveType.SHORT);
        if (primitiveKotlinType == null) {
            $$$reportNull$$$0(58);
        }
        return primitiveKotlinType;
    }

    public SimpleType getIntType() {
        SimpleType primitiveKotlinType = getPrimitiveKotlinType(PrimitiveType.INT);
        if (primitiveKotlinType == null) {
            $$$reportNull$$$0(59);
        }
        return primitiveKotlinType;
    }

    public SimpleType getLongType() {
        SimpleType primitiveKotlinType = getPrimitiveKotlinType(PrimitiveType.LONG);
        if (primitiveKotlinType == null) {
            $$$reportNull$$$0(60);
        }
        return primitiveKotlinType;
    }

    public SimpleType getFloatType() {
        SimpleType primitiveKotlinType = getPrimitiveKotlinType(PrimitiveType.FLOAT);
        if (primitiveKotlinType == null) {
            $$$reportNull$$$0(61);
        }
        return primitiveKotlinType;
    }

    public SimpleType getDoubleType() {
        SimpleType primitiveKotlinType = getPrimitiveKotlinType(PrimitiveType.DOUBLE);
        if (primitiveKotlinType == null) {
            $$$reportNull$$$0(62);
        }
        return primitiveKotlinType;
    }

    public SimpleType getCharType() {
        SimpleType primitiveKotlinType = getPrimitiveKotlinType(PrimitiveType.CHAR);
        if (primitiveKotlinType == null) {
            $$$reportNull$$$0(63);
        }
        return primitiveKotlinType;
    }

    public SimpleType getBooleanType() {
        SimpleType primitiveKotlinType = getPrimitiveKotlinType(PrimitiveType.BOOLEAN);
        if (primitiveKotlinType == null) {
            $$$reportNull$$$0(64);
        }
        return primitiveKotlinType;
    }

    public SimpleType getUnitType() {
        SimpleType defaultType = getUnit().getDefaultType();
        if (defaultType == null) {
            $$$reportNull$$$0(65);
        }
        return defaultType;
    }

    public SimpleType getStringType() {
        SimpleType defaultType = getString().getDefaultType();
        if (defaultType == null) {
            $$$reportNull$$$0(66);
        }
        return defaultType;
    }

    public KotlinType getIterableType() {
        SimpleType defaultType = getIterable().getDefaultType();
        if (defaultType == null) {
            $$$reportNull$$$0(67);
        }
        return defaultType;
    }

    public KotlinType getArrayElementType(KotlinType kotlinType) {
        KotlinType elementTypeForUnsignedArray;
        if (kotlinType == null) {
            $$$reportNull$$$0(68);
        }
        if (isArray(kotlinType)) {
            if (kotlinType.getArguments().size() != 1) {
                throw new IllegalStateException();
            }
            KotlinType type = kotlinType.getArguments().get(0).getType();
            if (type == null) {
                $$$reportNull$$$0(69);
            }
            return type;
        }
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(kotlinType);
        SimpleType simpleType = this.primitives.invoke().kotlinArrayTypeToPrimitiveKotlinType.get(kotlinTypeMakeNotNullable);
        if (simpleType != null) {
            if (simpleType == null) {
                $$$reportNull$$$0(70);
            }
            return simpleType;
        }
        ModuleDescriptor containingModuleOrNull = DescriptorUtils.getContainingModuleOrNull(kotlinTypeMakeNotNullable);
        if (containingModuleOrNull == null || (elementTypeForUnsignedArray = getElementTypeForUnsignedArray(kotlinTypeMakeNotNullable, containingModuleOrNull)) == null) {
            throw new IllegalStateException("not array: " + kotlinType);
        }
        if (elementTypeForUnsignedArray == null) {
            $$$reportNull$$$0(71);
        }
        return elementTypeForUnsignedArray;
    }

    private static KotlinType getElementTypeForUnsignedArray(KotlinType kotlinType, ModuleDescriptor moduleDescriptor) {
        ClassId classId;
        ClassId unsignedClassIdByArrayClassId;
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies;
        if (kotlinType == null) {
            $$$reportNull$$$0(72);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(73);
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().mo7434getDeclarationDescriptor();
        if (classifierDescriptorMo7434getDeclarationDescriptor == null || !UnsignedTypes.INSTANCE.isShortNameOfUnsignedArray(classifierDescriptorMo7434getDeclarationDescriptor.getName()) || (classId = DescriptorUtilsKt.getClassId(classifierDescriptorMo7434getDeclarationDescriptor)) == null || (unsignedClassIdByArrayClassId = UnsignedTypes.INSTANCE.getUnsignedClassIdByArrayClassId(classId)) == null || (classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(moduleDescriptor, unsignedClassIdByArrayClassId)) == null) {
            return null;
        }
        return classDescriptorFindClassAcrossModuleDependencies.getDefaultType();
    }

    public SimpleType getPrimitiveArrayKotlinType(PrimitiveType primitiveType) {
        if (primitiveType == null) {
            $$$reportNull$$$0(74);
        }
        SimpleType simpleType = this.primitives.invoke().primitiveTypeToArrayKotlinType.get(primitiveType);
        if (simpleType == null) {
            $$$reportNull$$$0(75);
        }
        return simpleType;
    }

    public SimpleType getPrimitiveArrayKotlinTypeByPrimitiveKotlinType(KotlinType kotlinType) {
        ModuleDescriptor containingModuleOrNull;
        if (kotlinType == null) {
            $$$reportNull$$$0(76);
        }
        SimpleType simpleType = this.primitives.invoke().primitiveKotlinTypeToKotlinArrayType.get(kotlinType);
        if (simpleType != null) {
            return simpleType;
        }
        UnsignedTypes unsignedTypes = UnsignedTypes.INSTANCE;
        if (!UnsignedTypes.isUnsignedType(kotlinType) || TypeUtils.isNullableType(kotlinType) || (containingModuleOrNull = DescriptorUtils.getContainingModuleOrNull(kotlinType)) == null) {
            return null;
        }
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(containingModuleOrNull, UnsignedTypes.INSTANCE.getUnsignedArrayClassIdByUnsignedClassId(DescriptorUtilsKt.getClassId(kotlinType.getConstructor().mo7434getDeclarationDescriptor())));
        if (classDescriptorFindClassAcrossModuleDependencies == null) {
            return null;
        }
        return classDescriptorFindClassAcrossModuleDependencies.getDefaultType();
    }

    public static PrimitiveType getPrimitiveType(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(77);
        }
        if (StandardNames.FqNames.primitiveTypeShortNames.contains(declarationDescriptor.getName())) {
            return StandardNames.FqNames.fqNameToPrimitiveType.get(DescriptorUtils.getFqName(declarationDescriptor));
        }
        return null;
    }

    public static PrimitiveType getPrimitiveArrayType(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(78);
        }
        if (StandardNames.FqNames.primitiveArrayTypeShortNames.contains(declarationDescriptor.getName())) {
            return StandardNames.FqNames.arrayClassFqNameToPrimitiveType.get(DescriptorUtils.getFqName(declarationDescriptor));
        }
        return null;
    }

    public SimpleType getArrayType(Variance variance, KotlinType kotlinType, Annotations annotations) {
        if (variance == null) {
            $$$reportNull$$$0(79);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(80);
        }
        if (annotations == null) {
            $$$reportNull$$$0(81);
        }
        SimpleType simpleTypeSimpleNotNullType = KotlinTypeFactory.simpleNotNullType(TypeAttributesKt.toDefaultAttributes(annotations), getArray(), Collections.singletonList(new TypeProjectionImpl(variance, kotlinType)));
        if (simpleTypeSimpleNotNullType == null) {
            $$$reportNull$$$0(82);
        }
        return simpleTypeSimpleNotNullType;
    }

    public SimpleType getArrayType(Variance variance, KotlinType kotlinType) {
        if (variance == null) {
            $$$reportNull$$$0(83);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(84);
        }
        SimpleType arrayType = getArrayType(variance, kotlinType, Annotations.INSTANCE.getEMPTY());
        if (arrayType == null) {
            $$$reportNull$$$0(85);
        }
        return arrayType;
    }

    public SimpleType getEnumType(SimpleType simpleType) {
        if (simpleType == null) {
            $$$reportNull$$$0(86);
        }
        SimpleType simpleTypeSimpleNotNullType = KotlinTypeFactory.simpleNotNullType(TypeAttributes.INSTANCE.getEmpty(), getEnum(), Collections.singletonList(new TypeProjectionImpl(Variance.INVARIANT, simpleType)));
        if (simpleTypeSimpleNotNullType == null) {
            $$$reportNull$$$0(87);
        }
        return simpleTypeSimpleNotNullType;
    }

    public SimpleType getAnnotationType() {
        SimpleType defaultType = getAnnotation().getDefaultType();
        if (defaultType == null) {
            $$$reportNull$$$0(88);
        }
        return defaultType;
    }

    public static boolean isArray(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(89);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.array);
    }

    public static boolean isArrayOrPrimitiveArray(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(90);
        }
        return classFqNameEquals(classDescriptor, StandardNames.FqNames.array) || getPrimitiveArrayType(classDescriptor) != null;
    }

    public static boolean isArrayOrPrimitiveArray(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(91);
        }
        return isArray(kotlinType) || isPrimitiveArray(kotlinType);
    }

    public static boolean isPrimitiveArray(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(92);
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().mo7434getDeclarationDescriptor();
        return (classifierDescriptorMo7434getDeclarationDescriptor == null || getPrimitiveArrayType(classifierDescriptorMo7434getDeclarationDescriptor) == null) ? false : true;
    }

    public static PrimitiveType getPrimitiveArrayElementType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(93);
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().mo7434getDeclarationDescriptor();
        if (classifierDescriptorMo7434getDeclarationDescriptor == null) {
            return null;
        }
        return getPrimitiveArrayType(classifierDescriptorMo7434getDeclarationDescriptor);
    }

    public static PrimitiveType getPrimitiveType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(94);
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().mo7434getDeclarationDescriptor();
        if (classifierDescriptorMo7434getDeclarationDescriptor == null) {
            return null;
        }
        return getPrimitiveType(classifierDescriptorMo7434getDeclarationDescriptor);
    }

    public static boolean isPrimitiveType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(95);
        }
        return !kotlinType.getIsMarkedNullable() && isPrimitiveTypeOrNullablePrimitiveType(kotlinType);
    }

    public static boolean isPrimitiveTypeOrNullablePrimitiveType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(96);
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().mo7434getDeclarationDescriptor();
        return (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) && isPrimitiveClass((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor);
    }

    public static boolean isPrimitiveClass(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(97);
        }
        return getPrimitiveType(classDescriptor) != null;
    }

    private static boolean isConstructedFromGivenClass(KotlinType kotlinType, FqNameUnsafe fqNameUnsafe) {
        if (kotlinType == null) {
            $$$reportNull$$$0(98);
        }
        if (fqNameUnsafe == null) {
            $$$reportNull$$$0(99);
        }
        return isTypeConstructorForGivenClass(kotlinType.getConstructor(), fqNameUnsafe);
    }

    public static boolean isConstructedFromGivenClass(KotlinType kotlinType, FqName fqName) {
        if (kotlinType == null) {
            $$$reportNull$$$0(100);
        }
        if (fqName == null) {
            $$$reportNull$$$0(101);
        }
        return isConstructedFromGivenClass(kotlinType, fqName.toUnsafe());
    }

    public static boolean isTypeConstructorForGivenClass(TypeConstructor typeConstructor, FqNameUnsafe fqNameUnsafe) {
        if (typeConstructor == null) {
            $$$reportNull$$$0(102);
        }
        if (fqNameUnsafe == null) {
            $$$reportNull$$$0(103);
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = typeConstructor.mo7434getDeclarationDescriptor();
        return (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) && classFqNameEquals(classifierDescriptorMo7434getDeclarationDescriptor, fqNameUnsafe);
    }

    private static boolean classFqNameEquals(ClassifierDescriptor classifierDescriptor, FqNameUnsafe fqNameUnsafe) {
        if (classifierDescriptor == null) {
            $$$reportNull$$$0(104);
        }
        if (fqNameUnsafe == null) {
            $$$reportNull$$$0(105);
        }
        return classifierDescriptor.getName().equals(fqNameUnsafe.shortName()) && fqNameUnsafe.equals(DescriptorUtils.getFqName(classifierDescriptor));
    }

    private static boolean isNotNullConstructedFromGivenClass(KotlinType kotlinType, FqNameUnsafe fqNameUnsafe) {
        if (kotlinType == null) {
            $$$reportNull$$$0(106);
        }
        if (fqNameUnsafe == null) {
            $$$reportNull$$$0(107);
        }
        return !kotlinType.getIsMarkedNullable() && isConstructedFromGivenClass(kotlinType, fqNameUnsafe);
    }

    public static boolean isSpecialClassWithNoSupertypes(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(108);
        }
        return classFqNameEquals(classDescriptor, StandardNames.FqNames.any) || classFqNameEquals(classDescriptor, StandardNames.FqNames.nothing);
    }

    public static boolean isAny(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(109);
        }
        return classFqNameEquals(classDescriptor, StandardNames.FqNames.any);
    }

    public static boolean isAny(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(110);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames.any);
    }

    public static boolean isBoolean(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(111);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames._boolean);
    }

    public static boolean isBooleanOrNullableBoolean(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(112);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames._boolean);
    }

    public static boolean isBoolean(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(113);
        }
        return classFqNameEquals(classDescriptor, StandardNames.FqNames._boolean);
    }

    public static boolean isNumber(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(114);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames.number);
    }

    public static boolean isChar(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(115);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames._char);
    }

    public static boolean isCharOrNullableChar(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(116);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames._char);
    }

    public static boolean isInt(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(117);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames._int);
    }

    public static boolean isByte(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(118);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames._byte);
    }

    public static boolean isLong(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(119);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames._long);
    }

    public static boolean isLongOrNullableLong(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(120);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames._long);
    }

    public static boolean isShort(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(121);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames._short);
    }

    public static boolean isFloat(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(122);
        }
        return isFloatOrNullableFloat(kotlinType) && !kotlinType.getIsMarkedNullable();
    }

    public static boolean isFloatOrNullableFloat(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(123);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames._float);
    }

    public static boolean isDouble(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(124);
        }
        return isDoubleOrNullableDouble(kotlinType) && !kotlinType.getIsMarkedNullable();
    }

    public static boolean isUByte(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(125);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames.uByteFqName.toUnsafe());
    }

    public static boolean isUShort(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(126);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames.uShortFqName.toUnsafe());
    }

    public static boolean isUInt(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(127);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames.uIntFqName.toUnsafe());
    }

    public static boolean isULong(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(128);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames.uLongFqName.toUnsafe());
    }

    public static boolean isUByteArray(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(129);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames.uByteArrayFqName.toUnsafe());
    }

    public static boolean isUShortArray(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(130);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames.uShortArrayFqName.toUnsafe());
    }

    public static boolean isUIntArray(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(131);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames.uIntArrayFqName.toUnsafe());
    }

    public static boolean isULongArray(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(132);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames.uLongArrayFqName.toUnsafe());
    }

    public static boolean isUnsignedArrayType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(133);
        }
        return isUByteArray(kotlinType) || isUShortArray(kotlinType) || isUIntArray(kotlinType) || isULongArray(kotlinType);
    }

    public static boolean isDoubleOrNullableDouble(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(134);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames._double);
    }

    private static boolean isConstructedFromGivenClassAndNotNullable(KotlinType kotlinType, FqNameUnsafe fqNameUnsafe) {
        if (kotlinType == null) {
            $$$reportNull$$$0(135);
        }
        if (fqNameUnsafe == null) {
            $$$reportNull$$$0(136);
        }
        return isConstructedFromGivenClass(kotlinType, fqNameUnsafe) && !kotlinType.getIsMarkedNullable();
    }

    public static boolean isNothing(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(137);
        }
        return isNothingOrNullableNothing(kotlinType) && !TypeUtils.isNullableType(kotlinType);
    }

    public static boolean isNullableNothing(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(138);
        }
        return isNothingOrNullableNothing(kotlinType) && TypeUtils.isNullableType(kotlinType);
    }

    public static boolean isNothingOrNullableNothing(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(139);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.nothing);
    }

    public static boolean isAnyOrNullableAny(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(140);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.any);
    }

    public static boolean isNullableAny(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(141);
        }
        return isAnyOrNullableAny(kotlinType) && kotlinType.getIsMarkedNullable();
    }

    public static boolean isDefaultBound(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(142);
        }
        return isNullableAny(kotlinType);
    }

    public static boolean isUnit(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(143);
        }
        return isNotNullConstructedFromGivenClass(kotlinType, StandardNames.FqNames.unit);
    }

    public static boolean mayReturnNonUnitValue(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(144);
        }
        boolean z = !isUnit(functionDescriptor.getReturnType());
        for (FunctionDescriptor functionDescriptor2 : functionDescriptor.getOriginal().getOverriddenDescriptors()) {
            if (z) {
                break;
            }
            z = !isUnit(functionDescriptor2.getReturnType());
        }
        return z;
    }

    public static boolean isUnitOrNullableUnit(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(145);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.unit);
    }

    public boolean isBooleanOrSubtype(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(146);
        }
        return KotlinTypeChecker.DEFAULT.isSubtypeOf(kotlinType, getBooleanType());
    }

    public boolean isMemberOfAny(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(147);
        }
        return declarationDescriptor.getModuleDescriptor() == getAny();
    }

    public static boolean isEnum(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(148);
        }
        return classFqNameEquals(classDescriptor, StandardNames.FqNames._enum);
    }

    public static boolean isEnum(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(149);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames._enum);
    }

    public static boolean isComparable(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(150);
        }
        return classFqNameEquals(classDescriptor, StandardNames.FqNames.comparable.toUnsafe());
    }

    public static boolean isComparable(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(151);
        }
        return isConstructedFromGivenClassAndNotNullable(kotlinType, StandardNames.FqNames.comparable.toUnsafe());
    }

    public static boolean isCharSequence(KotlinType kotlinType) {
        return kotlinType != null && isNotNullConstructedFromGivenClass(kotlinType, StandardNames.FqNames.charSequence);
    }

    public static boolean isString(KotlinType kotlinType) {
        return kotlinType != null && isNotNullConstructedFromGivenClass(kotlinType, StandardNames.FqNames.string);
    }

    public static boolean isCharSequenceOrNullableCharSequence(KotlinType kotlinType) {
        return kotlinType != null && isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.charSequence);
    }

    public static boolean isStringOrNullableString(KotlinType kotlinType) {
        return kotlinType != null && isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.string);
    }

    public static boolean isCollectionOrNullableCollection(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(152);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.collection);
    }

    public static boolean isListOrNullableList(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(153);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.list);
    }

    public static boolean isSetOrNullableSet(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(154);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.set);
    }

    public static boolean isMapOrNullableMap(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(155);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.map);
    }

    public static boolean isIterableOrNullableIterable(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(156);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.iterable);
    }

    public static boolean isThrowableOrNullableThrowable(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(157);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.throwable);
    }

    public static boolean isThrowable(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(158);
        }
        return classFqNameEquals(classDescriptor, StandardNames.FqNames.throwable.toUnsafe());
    }

    public static boolean isKClass(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(159);
        }
        return classFqNameEquals(classDescriptor, StandardNames.FqNames.kClass);
    }

    public static boolean isNonPrimitiveArray(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(160);
        }
        return classFqNameEquals(classDescriptor, StandardNames.FqNames.array);
    }

    public static boolean isCloneable(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(161);
        }
        return classFqNameEquals(classDescriptor, StandardNames.FqNames.cloneable);
    }

    public static boolean isDeprecated(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(162);
        }
        if (declarationDescriptor.getOriginal().getAnnotations().hasAnnotation(StandardNames.FqNames.deprecated)) {
            return true;
        }
        if (!(declarationDescriptor instanceof PropertyDescriptor)) {
            return false;
        }
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) declarationDescriptor;
        boolean zIsVar = propertyDescriptor.isVar();
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        PropertySetterDescriptor setter = propertyDescriptor.getSetter();
        if (getter != null && isDeprecated(getter)) {
            if (!zIsVar) {
                return true;
            }
            if (setter != null && isDeprecated(setter)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotNullOrNullableFunctionSupertype(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(163);
        }
        return isConstructedFromGivenClass(kotlinType, StandardNames.FqNames.functionSupertype);
    }
}
