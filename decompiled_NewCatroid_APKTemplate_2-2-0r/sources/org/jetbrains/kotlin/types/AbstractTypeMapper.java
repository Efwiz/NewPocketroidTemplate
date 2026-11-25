package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.signature.AsmTypeFactory;
import org.jetbrains.kotlin.load.kotlin.JvmDescriptorTypeWriter;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.load.kotlin.TypeSignatureMappingKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: AbstractTypeMapper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J,\u0010\b\u001a\u00020\u0007\"\u000e\b\u0000\u0010\t*\b\u0012\u0004\u0012\u00020\u00040\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f2\u0006\u0010\r\u001a\u00020\u000eJ4\u0010\u000f\u001a\u00020\u0004\"\u000e\b\u0000\u0010\t*\b\u0012\u0004\u0012\u00020\u00040\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0007JQ\u0010\u0013\u001a\u00020\u0004\"\u000e\b\u0000\u0010\t*\b\u0012\u0004\u0012\u00020\u00040\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u0001H\t2\b\b\u0002\u0010\u0012\u001a\u00020\u0007¢\u0006\u0002\u0010\u0017J\u0014\u0010\b\u001a\u00020\u0007*\u00020\u00182\u0006\u0010\r\u001a\u00020\u000eH\u0002JQ\u0010\u0019\u001a\u00020\u0004\"\u000e\b\u0000\u0010\t*\b\u0012\u0004\u0012\u00020\u00040\n*\u00020\u001a2\u0006\u0010\r\u001a\u00020\u001b2\b\u0010\u0016\u001a\u0004\u0018\u0001H\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0007H\u0002¢\u0006\u0002\u0010\u001cJY\u0010\u001d\u001a\u00020\u0004\"\u000e\b\u0000\u0010\t*\b\u0012\u0004\u0012\u00020\u00040\n*\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\r\u001a\u00020\u001b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f2\b\u0010\u0016\u001a\u0004\u0018\u0001H\t2\u0006\u0010\u0012\u001a\u00020\u0007H\u0002¢\u0006\u0002\u0010\u001eJQ\u0010\u001f\u001a\u00020\u0004\"\u000e\b\u0000\u0010\t*\b\u0012\u0004\u0012\u00020\u00040\n*\u00020\u001a2\u0006\u0010\r\u001a\u00020\u001b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u0001H\t2\u0006\u0010\u0012\u001a\u00020\u0007H\u0002¢\u0006\u0002\u0010 JQ\u0010\u0013\u001a\u00020\u0004\"\u000e\b\u0000\u0010\t*\b\u0012\u0004\u0012\u00020\u00040\n*\u00020\u001a2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u0001H\t2\u0006\u0010\u0012\u001a\u00020\u0007H\u0002¢\u0006\u0002\u0010!J\f\u0010\"\u001a\u00020#*\u00020$H\u0002¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractTypeMapper;", "", "()V", "boxTypeIfNeeded", "Lorg/jetbrains/org/objectweb/asm/Type;", "possiblyPrimitiveType", "needBoxedType", "", "isPrimitiveBacked", "Writer", "Lorg/jetbrains/kotlin/load/kotlin/JvmDescriptorTypeWriter;", "context", "Lorg/jetbrains/kotlin/types/TypeMappingContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "mapClass", "typeConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "materialized", "mapType", "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "sw", "(Lorg/jetbrains/kotlin/types/TypeMappingContext;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;Lorg/jetbrains/kotlin/load/kotlin/JvmDescriptorTypeWriter;Z)Lorg/jetbrains/org/objectweb/asm/Type;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "mapArrayType", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "(Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;Lorg/jetbrains/kotlin/load/kotlin/JvmDescriptorTypeWriter;Lorg/jetbrains/kotlin/types/TypeMappingContext;Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;Z)Lorg/jetbrains/org/objectweb/asm/Type;", "mapClassType", "(Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;Lorg/jetbrains/kotlin/types/TypeMappingContext;Lorg/jetbrains/kotlin/load/kotlin/JvmDescriptorTypeWriter;Z)Lorg/jetbrains/org/objectweb/asm/Type;", "mapSuspendFunctionType", "(Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;Lorg/jetbrains/kotlin/types/TypeMappingContext;Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;Lorg/jetbrains/kotlin/load/kotlin/JvmDescriptorTypeWriter;Z)Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;Lorg/jetbrains/kotlin/types/TypeMappingContext;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;Lorg/jetbrains/kotlin/load/kotlin/JvmDescriptorTypeWriter;Z)Lorg/jetbrains/org/objectweb/asm/Type;", "toVariance", "Lorg/jetbrains/kotlin/types/Variance;", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "backend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AbstractTypeMapper {
    public static final AbstractTypeMapper INSTANCE = new AbstractTypeMapper();

    /* JADX INFO: compiled from: AbstractTypeMapper.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeVariance.values().length];
            try {
                iArr[TypeVariance.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeVariance.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeVariance.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private AbstractTypeMapper() {
    }

    public final <Writer extends JvmDescriptorTypeWriter<Type>> Type mapClass(TypeMappingContext<Writer> context, TypeConstructorMarker typeConstructor, boolean materialized) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        TypeSystemCommonBackendContextForTypeMapping typeContext = context.getTypeContext();
        if (typeContext.isClassTypeConstructor(typeConstructor)) {
            return mapType$default(INSTANCE, context, typeContext.defaultType(typeConstructor), TypeMappingMode.CLASS_DECLARATION, null, materialized, 8, null);
        }
        if (typeContext.isTypeParameter(typeConstructor)) {
            return mapType$default(INSTANCE, context, typeContext.defaultType(typeConstructor), null, null, materialized, 12, null);
        }
        throw new IllegalStateException(("Unknown type constructor: " + typeConstructor).toString());
    }

    public static /* synthetic */ Type mapType$default(AbstractTypeMapper abstractTypeMapper, TypeMappingContext typeMappingContext, KotlinTypeMarker kotlinTypeMarker, TypeMappingMode typeMappingMode, JvmDescriptorTypeWriter jvmDescriptorTypeWriter, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            typeMappingMode = TypeMappingMode.DEFAULT;
        }
        TypeMappingMode typeMappingMode2 = typeMappingMode;
        if ((i & 8) != 0) {
            jvmDescriptorTypeWriter = null;
        }
        JvmDescriptorTypeWriter jvmDescriptorTypeWriter2 = jvmDescriptorTypeWriter;
        if ((i & 16) != 0) {
            z = true;
        }
        return abstractTypeMapper.mapType(typeMappingContext, kotlinTypeMarker, typeMappingMode2, jvmDescriptorTypeWriter2, z);
    }

    public final <Writer extends JvmDescriptorTypeWriter<Type>> Type mapType(TypeMappingContext<Writer> context, KotlinTypeMarker type, TypeMappingMode mode, Writer sw, boolean materialized) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(mode, "mode");
        return mapType(context.getTypeContext(), context, type, mode, sw, materialized);
    }

    private final <Writer extends JvmDescriptorTypeWriter<Type>> Type mapType(TypeSystemCommonBackendContextForTypeMapping typeSystemCommonBackendContextForTypeMapping, TypeMappingContext<Writer> typeMappingContext, KotlinTypeMarker kotlinTypeMarker, TypeMappingMode typeMappingMode, Writer writer, boolean z) {
        if (typeSystemCommonBackendContextForTypeMapping.isError(kotlinTypeMarker)) {
            String nameForErrorType = typeSystemCommonBackendContextForTypeMapping.getNameForErrorType(kotlinTypeMarker);
            if (nameForErrorType == null) {
                nameForErrorType = TypeSignatureMappingKt.NON_EXISTENT_CLASS_NAME;
            }
            Type objectType = Type.getObjectType(nameForErrorType);
            if (writer != null) {
                Intrinsics.checkNotNull(objectType);
                typeMappingContext.writeGenericType(writer, kotlinTypeMarker, objectType, typeMappingMode);
            }
            Intrinsics.checkNotNull(objectType);
            return objectType;
        }
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemCommonBackendContextForTypeMapping.typeConstructor(kotlinTypeMarker);
        if (kotlinTypeMarker instanceof SimpleTypeMarker) {
            Type type = (Type) TypeSignatureMappingKt.mapBuiltInType(typeSystemCommonBackendContextForTypeMapping, kotlinTypeMarker, AsmTypeFactory.INSTANCE, typeMappingMode);
            if (type != null) {
                Type typeBoxTypeIfNeeded = boxTypeIfNeeded(type, typeMappingMode.getNeedPrimitiveBoxing());
                if (writer != null) {
                    typeMappingContext.writeGenericType(writer, kotlinTypeMarker, typeBoxTypeIfNeeded, typeMappingMode);
                }
                return typeBoxTypeIfNeeded;
            }
            SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker) kotlinTypeMarker;
            if (typeSystemCommonBackendContextForTypeMapping.isSuspendFunction(simpleTypeMarker)) {
                return mapSuspendFunctionType(typeSystemCommonBackendContextForTypeMapping, simpleTypeMarker, typeMappingContext, typeMappingMode, writer, z);
            }
            if (typeSystemCommonBackendContextForTypeMapping.isArrayOrNullableArray(kotlinTypeMarker)) {
                return mapArrayType(typeSystemCommonBackendContextForTypeMapping, simpleTypeMarker, writer, typeMappingContext, typeMappingMode, z);
            }
            if (typeSystemCommonBackendContextForTypeMapping.isClassTypeConstructor(typeConstructorMarkerTypeConstructor)) {
                return mapClassType(typeSystemCommonBackendContextForTypeMapping, typeConstructorMarkerTypeConstructor, typeMappingMode, simpleTypeMarker, typeMappingContext, writer, z);
            }
        }
        if (typeSystemCommonBackendContextForTypeMapping.isTypeParameter(typeConstructorMarkerTypeConstructor)) {
            TypeParameterMarker typeParameterMarkerAsTypeParameter = typeSystemCommonBackendContextForTypeMapping.asTypeParameter(typeConstructorMarkerTypeConstructor);
            KotlinTypeMarker kotlinTypeMarkerRepresentativeUpperBound = typeSystemCommonBackendContextForTypeMapping.representativeUpperBound(typeParameterMarkerAsTypeParameter);
            if ((typeSystemCommonBackendContextForTypeMapping.isInlineClass(typeSystemCommonBackendContextForTypeMapping.typeConstructor(kotlinTypeMarkerRepresentativeUpperBound)) || ((kotlinTypeMarkerRepresentativeUpperBound instanceof SimpleTypeMarker) && typeSystemCommonBackendContextForTypeMapping.isPrimitiveType((SimpleTypeMarker) kotlinTypeMarkerRepresentativeUpperBound))) && typeSystemCommonBackendContextForTypeMapping.isNullableType(kotlinTypeMarker)) {
                kotlinTypeMarkerRepresentativeUpperBound = typeSystemCommonBackendContextForTypeMapping.makeNullable(kotlinTypeMarkerRepresentativeUpperBound);
            }
            Type typeMapType = mapType(typeSystemCommonBackendContextForTypeMapping, typeMappingContext, kotlinTypeMarkerRepresentativeUpperBound, typeMappingMode, null, z);
            if (writer == null) {
                return typeMapType;
            }
            writer.writeTypeVariable(typeSystemCommonBackendContextForTypeMapping.getName(typeParameterMarkerAsTypeParameter), typeMapType);
            return typeMapType;
        }
        if (typeSystemCommonBackendContextForTypeMapping.isFlexible(kotlinTypeMarker)) {
            return mapType(typeSystemCommonBackendContextForTypeMapping, typeMappingContext, typeSystemCommonBackendContextForTypeMapping.upperBoundIfFlexible(kotlinTypeMarker), typeMappingMode, writer, z);
        }
        if (kotlinTypeMarker instanceof DefinitelyNotNullTypeMarker) {
            return mapType(typeSystemCommonBackendContextForTypeMapping, typeMappingContext, typeSystemCommonBackendContextForTypeMapping.original((DefinitelyNotNullTypeMarker) kotlinTypeMarker), typeMappingMode, writer, z);
        }
        if (typeSystemCommonBackendContextForTypeMapping.isScript(typeConstructorMarkerTypeConstructor)) {
            Type objectType2 = Type.getObjectType(typeMappingContext.getScriptInternalName(typeConstructorMarkerTypeConstructor));
            if (writer != null) {
                Intrinsics.checkNotNull(objectType2);
                writer.writeClass(objectType2);
            }
            Intrinsics.checkNotNullExpressionValue(objectType2, "let(...)");
            return objectType2;
        }
        throw new UnsupportedOperationException("Unknown type " + kotlinTypeMarker);
    }

    private final <Writer extends JvmDescriptorTypeWriter<Type>> Type mapSuspendFunctionType(TypeSystemCommonBackendContextForTypeMapping typeSystemCommonBackendContextForTypeMapping, SimpleTypeMarker simpleTypeMarker, TypeMappingContext<Writer> typeMappingContext, TypeMappingMode typeMappingMode, Writer writer, boolean z) {
        int iArgumentsCount = typeSystemCommonBackendContextForTypeMapping.argumentsCount(simpleTypeMarker);
        TypeArgumentListMarker typeArgumentListMarkerAsArgumentList = typeSystemCommonBackendContextForTypeMapping.asArgumentList(simpleTypeMarker);
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        int i = iArgumentsCount - 1;
        for (int i2 = 0; i2 < i; i2++) {
            listCreateListBuilder.add(typeSystemCommonBackendContextForTypeMapping.adjustedType(typeSystemCommonBackendContextForTypeMapping.get(typeArgumentListMarkerAsArgumentList, i2)));
        }
        List list = listCreateListBuilder;
        list.add(typeSystemCommonBackendContextForTypeMapping.typeWithArguments(typeSystemCommonBackendContextForTypeMapping.continuationTypeConstructor(), typeSystemCommonBackendContextForTypeMapping.adjustedType(typeSystemCommonBackendContextForTypeMapping.get(typeArgumentListMarkerAsArgumentList, i))));
        list.add(typeSystemCommonBackendContextForTypeMapping.nullableAnyType());
        List<? extends KotlinTypeMarker> listBuild = CollectionsKt.build(listCreateListBuilder);
        return mapType(typeSystemCommonBackendContextForTypeMapping, typeMappingContext, typeSystemCommonBackendContextForTypeMapping.typeWithArguments(typeSystemCommonBackendContextForTypeMapping.functionNTypeConstructor(listBuild.size() - 1), listBuild), typeMappingMode, writer, z);
    }

    private final <Writer extends JvmDescriptorTypeWriter<Type>> Type mapArrayType(TypeSystemCommonBackendContextForTypeMapping typeSystemCommonBackendContextForTypeMapping, SimpleTypeMarker simpleTypeMarker, Writer writer, TypeMappingContext<Writer> typeMappingContext, TypeMappingMode typeMappingMode, boolean z) {
        Type OBJECT_TYPE;
        TypeArgumentMarker typeArgumentMarker = typeSystemCommonBackendContextForTypeMapping.get(typeSystemCommonBackendContextForTypeMapping.asArgumentList(simpleTypeMarker), 0);
        Pair pair = typeSystemCommonBackendContextForTypeMapping.isStarProjection(typeArgumentMarker) ? TuplesKt.to(Variance.OUT_VARIANCE, typeSystemCommonBackendContextForTypeMapping.nullableAnyType()) : TuplesKt.to(toVariance(typeSystemCommonBackendContextForTypeMapping.getVariance(typeArgumentMarker)), typeSystemCommonBackendContextForTypeMapping.getType(typeArgumentMarker));
        Variance variance = (Variance) pair.component1();
        KotlinTypeMarker kotlinTypeMarker = (KotlinTypeMarker) pair.component2();
        if (writer != null) {
            writer.writeArrayType();
        }
        if (variance == Variance.IN_VARIANCE) {
            OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
            Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
            if (writer != null) {
                writer.writeClass(OBJECT_TYPE);
            }
        } else {
            OBJECT_TYPE = mapType(typeSystemCommonBackendContextForTypeMapping, typeMappingContext, kotlinTypeMarker, typeMappingMode.toGenericArgumentMode(variance, true), writer, z);
        }
        if (writer != null) {
            writer.writeArrayEnd();
        }
        Type arrayType = AsmUtil.getArrayType(OBJECT_TYPE);
        Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
        return arrayType;
    }

    private final <Writer extends JvmDescriptorTypeWriter<Type>> Type mapClassType(TypeSystemCommonBackendContextForTypeMapping typeSystemCommonBackendContextForTypeMapping, TypeConstructorMarker typeConstructorMarker, TypeMappingMode typeMappingMode, SimpleTypeMarker simpleTypeMarker, TypeMappingContext<Writer> typeMappingContext, Writer writer, boolean z) {
        Type objectType;
        if (typeSystemCommonBackendContextForTypeMapping.isInlineClass(typeConstructorMarker) && !typeMappingMode.getNeedInlineClassWrapping()) {
            KotlinTypeMarker kotlinTypeMarkerComputeExpandedTypeForInlineClass = ExpandedTypeUtilsKt.computeExpandedTypeForInlineClass(typeSystemCommonBackendContextForTypeMapping, simpleTypeMarker);
            if (!(kotlinTypeMarkerComputeExpandedTypeForInlineClass == null ? true : kotlinTypeMarkerComputeExpandedTypeForInlineClass instanceof SimpleTypeMarker)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (kotlinTypeMarkerComputeExpandedTypeForInlineClass != null) {
                return mapType(typeSystemCommonBackendContextForTypeMapping, typeMappingContext, kotlinTypeMarkerComputeExpandedTypeForInlineClass, typeMappingMode.wrapInlineClassesMode(), writer, z);
            }
        }
        if (typeMappingMode.getIsForAnnotationParameter() && typeSystemCommonBackendContextForTypeMapping.isKClass(simpleTypeMarker)) {
            objectType = AsmTypes.JAVA_CLASS_TYPE;
        } else {
            objectType = Type.getObjectType(typeMappingContext.getClassInternalName(typeConstructorMarker));
        }
        if (writer != null) {
            Intrinsics.checkNotNull(objectType);
            typeMappingContext.writeGenericType(writer, simpleTypeMarker, objectType, typeMappingMode);
        }
        Intrinsics.checkNotNull(objectType);
        return objectType;
    }

    private final Type boxTypeIfNeeded(Type possiblyPrimitiveType, boolean needBoxedType) {
        if (!needBoxedType) {
            return possiblyPrimitiveType;
        }
        Type typeBoxType = AsmUtil.boxType(possiblyPrimitiveType);
        Intrinsics.checkNotNullExpressionValue(typeBoxType, "boxType(...)");
        return typeBoxType;
    }

    private final Variance toVariance(TypeVariance typeVariance) {
        int i = WhenMappings.$EnumSwitchMapping$0[typeVariance.ordinal()];
        if (i == 1) {
            return Variance.IN_VARIANCE;
        }
        if (i == 2) {
            return Variance.OUT_VARIANCE;
        }
        if (i == 3) {
            return Variance.INVARIANT;
        }
        throw new NoWhenBranchMatchedException();
    }

    public final <Writer extends JvmDescriptorTypeWriter<Type>> boolean isPrimitiveBacked(TypeMappingContext<Writer> context, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        return isPrimitiveBacked(context.getTypeContext(), type);
    }

    private final boolean isPrimitiveBacked(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker kotlinTypeMarker) {
        Pair pair;
        if (!typeSystemCommonBackendContext.isNullableType(kotlinTypeMarker)) {
            if ((kotlinTypeMarker instanceof SimpleTypeMarker) && typeSystemCommonBackendContext.isPrimitiveType((SimpleTypeMarker) kotlinTypeMarker)) {
                return true;
            }
            List<Pair<Name, SimpleTypeMarker>> valueClassProperties = typeSystemCommonBackendContext.getValueClassProperties(typeSystemCommonBackendContext.typeConstructor(kotlinTypeMarker));
            if (valueClassProperties != null && (pair = (Pair) CollectionsKt.singleOrNull((List) valueClassProperties)) != null && INSTANCE.isPrimitiveBacked(typeSystemCommonBackendContext, (KotlinTypeMarker) pair.getSecond())) {
                return true;
            }
        }
        return false;
    }
}
