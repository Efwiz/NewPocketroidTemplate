package org.jetbrains.kotlin.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.typeEnhancement.TypeEnhancementUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;

/* JADX INFO: compiled from: typeSignatureMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a/\u0010\u0002\u001a\u0002H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u0004*\b\u0012\u0004\u0012\u0002H\u00030\u00052\u0006\u0010\u0006\u001a\u0002H\u00032\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\t\u001a9\u0010\n\u001a\u0004\u0018\u0001H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u0004*\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00052\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"NON_EXISTENT_CLASS_NAME", "", "boxTypeIfNeeded", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/load/kotlin/JvmTypeFactory;", "possiblyPrimitiveType", "needBoxedType", "", "(Lorg/jetbrains/kotlin/load/kotlin/JvmTypeFactory;Ljava/lang/Object;Z)Ljava/lang/Object;", "mapBuiltInType", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "typeFactory", "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "(Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/load/kotlin/JvmTypeFactory;Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;)Ljava/lang/Object;", "compiler.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeSignatureMappingKt {
    public static final String NON_EXISTENT_CLASS_NAME = "error/NonExistentClass";

    public static final <T> T boxTypeIfNeeded(JvmTypeFactory<T> jvmTypeFactory, T possiblyPrimitiveType, boolean z) {
        Intrinsics.checkNotNullParameter(jvmTypeFactory, "<this>");
        Intrinsics.checkNotNullParameter(possiblyPrimitiveType, "possiblyPrimitiveType");
        return z ? jvmTypeFactory.boxType(possiblyPrimitiveType) : possiblyPrimitiveType;
    }

    public static final <T> T mapBuiltInType(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker type, JvmTypeFactory<T> typeFactory, TypeMappingMode mode) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(typeFactory, "typeFactory");
        Intrinsics.checkNotNullParameter(mode, "mode");
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemCommonBackendContext.typeConstructor(type);
        if (!typeSystemCommonBackendContext.isClassTypeConstructor(typeConstructorMarkerTypeConstructor)) {
            return null;
        }
        PrimitiveType primitiveType = typeSystemCommonBackendContext.getPrimitiveType(typeConstructorMarkerTypeConstructor);
        if (primitiveType != null) {
            return (T) boxTypeIfNeeded(typeFactory, typeFactory.createPrimitiveType(primitiveType), typeSystemCommonBackendContext.isNullableType(type) || TypeEnhancementUtilsKt.hasEnhancedNullability(typeSystemCommonBackendContext, type));
        }
        PrimitiveType primitiveArrayType = typeSystemCommonBackendContext.getPrimitiveArrayType(typeConstructorMarkerTypeConstructor);
        if (primitiveArrayType != null) {
            return typeFactory.createFromString("[" + JvmPrimitiveType.get(primitiveArrayType).getDesc());
        }
        if (typeSystemCommonBackendContext.isUnderKotlinPackage(typeConstructorMarkerTypeConstructor)) {
            FqNameUnsafe classFqNameUnsafe = typeSystemCommonBackendContext.getClassFqNameUnsafe(typeConstructorMarkerTypeConstructor);
            ClassId classIdMapKotlinToJava = classFqNameUnsafe != null ? JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(classFqNameUnsafe) : null;
            if (classIdMapKotlinToJava != null) {
                if (!mode.getKotlinCollectionsToJavaCollections()) {
                    List<JavaToKotlinClassMap.PlatformMutabilityMapping> mutabilityMappings = JavaToKotlinClassMap.INSTANCE.getMutabilityMappings();
                    if (!(mutabilityMappings instanceof Collection) || !mutabilityMappings.isEmpty()) {
                        Iterator<T> it = mutabilityMappings.iterator();
                        while (it.hasNext()) {
                            if (Intrinsics.areEqual(((JavaToKotlinClassMap.PlatformMutabilityMapping) it.next()).getJavaClass(), classIdMapKotlinToJava)) {
                                return null;
                            }
                        }
                    }
                }
                String internalName = JvmClassName.byClassId(classIdMapKotlinToJava).getInternalName();
                Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                return typeFactory.createObjectType(internalName);
            }
        }
        return null;
    }
}
