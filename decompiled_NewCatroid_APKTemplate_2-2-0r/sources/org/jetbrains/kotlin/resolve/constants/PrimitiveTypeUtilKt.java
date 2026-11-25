package org.jetbrains.kotlin.resolve.constants;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.ws.WebSocketProtocol;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: PrimitiveTypeUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0002H\u0000\u001a\f\u0010\u0014\u001a\u00020\u0013*\u00020\u0002H\u0000\u001a\u0014\u0010\u0015\u001a\u00020\t*\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0017H\u0000\"\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u001e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\"\u0018\u0010\b\u001a\u00020\t*\u00020\u00038@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u0018\u0010\f\u001a\u00020\t*\u00020\u00038@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000b\"\u0018\u0010\u000e\u001a\u00020\t*\u00020\u00038@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000b\"\u0018\u0010\u0010\u001a\u00020\t*\u00020\u00038@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000b¨\u0006\u0018"}, d2 = {"allSignedLiteralTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getAllSignedLiteralTypes", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Ljava/util/Collection;", "allUnsignedLiteralTypes", "getAllUnsignedLiteralTypes", "uByteType", "Lorg/jetbrains/kotlin/types/SimpleType;", "getUByteType", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Lorg/jetbrains/kotlin/types/SimpleType;", "uIntType", "getUIntType", "uLongType", "getULongType", "uShortType", "getUShortType", "maxValue", "", "minValue", "unsignedType", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveTypeUtilKt {
    public static final long minValue(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        if (UnsignedTypes.isUnsignedType(kotlinType)) {
            return 0L;
        }
        if (KotlinBuiltIns.isByte(kotlinType)) {
            return -128L;
        }
        if (KotlinBuiltIns.isShort(kotlinType)) {
            return -32768L;
        }
        if (KotlinBuiltIns.isInt(kotlinType)) {
            return -2147483648L;
        }
        throw new IllegalStateException(("Can't get min value for type: " + kotlinType).toString());
    }

    public static final long maxValue(KotlinType kotlinType) {
        long j;
        long j2;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        if (KotlinBuiltIns.isByte(kotlinType)) {
            return 127L;
        }
        if (KotlinBuiltIns.isShort(kotlinType)) {
            return 32767L;
        }
        if (KotlinBuiltIns.isInt(kotlinType)) {
            return 2147483647L;
        }
        if (KotlinBuiltIns.isUByte(kotlinType)) {
            j = -1;
            j2 = 255;
        } else if (KotlinBuiltIns.isUShort(kotlinType)) {
            j = -1;
            j2 = WebSocketProtocol.PAYLOAD_SHORT_MAX;
        } else {
            if (!KotlinBuiltIns.isUInt(kotlinType)) {
                throw new IllegalStateException(("Can't get max value for type: " + kotlinType).toString());
            }
            j = -1;
            j2 = 4294967295L;
        }
        return j & j2;
    }

    public static final SimpleType unsignedType(ModuleDescriptor moduleDescriptor, ClassId classId) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(moduleDescriptor, classId);
        Intrinsics.checkNotNull(classDescriptorFindClassAcrossModuleDependencies);
        SimpleType defaultType = classDescriptorFindClassAcrossModuleDependencies.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        return defaultType;
    }

    public static final SimpleType getUIntType(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return unsignedType(moduleDescriptor, StandardNames.FqNames.uInt);
    }

    public static final SimpleType getULongType(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return unsignedType(moduleDescriptor, StandardNames.FqNames.uLong);
    }

    public static final SimpleType getUByteType(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return unsignedType(moduleDescriptor, StandardNames.FqNames.uByte);
    }

    public static final SimpleType getUShortType(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return unsignedType(moduleDescriptor, StandardNames.FqNames.uShort);
    }

    public static final Collection<KotlinType> getAllSignedLiteralTypes(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return CollectionsKt.listOf((Object[]) new SimpleType[]{moduleDescriptor.getBuiltIns().getIntType(), moduleDescriptor.getBuiltIns().getLongType(), moduleDescriptor.getBuiltIns().getByteType(), moduleDescriptor.getBuiltIns().getShortType()});
    }

    public static final Collection<KotlinType> getAllUnsignedLiteralTypes(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        if (CompileTimeConstantKt.hasUnsignedTypesInModuleDependencies(moduleDescriptor)) {
            return CollectionsKt.listOf((Object[]) new SimpleType[]{unsignedType(moduleDescriptor, StandardNames.FqNames.uInt), unsignedType(moduleDescriptor, StandardNames.FqNames.uLong), unsignedType(moduleDescriptor, StandardNames.FqNames.uByte), unsignedType(moduleDescriptor, StandardNames.FqNames.uShort)});
        }
        return CollectionsKt.emptyList();
    }
}
