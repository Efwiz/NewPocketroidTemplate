package org.jetbrains.kotlin.fir.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationBuildingUtilsKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttributeKt;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeKindServiceKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.exceptions.PlatformExceptionUtilsKt;

/* JADX INFO: compiled from: FirTypeDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0000\u0012\f\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0017H\u0002J3\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001f2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u001bH\u0002¢\u0006\u0002\u0010*J5\u0010+\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\u001f2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u001bH\u0002¢\u0006\u0002\u0010*J\u000e\u0010,\u001a\u00020-2\u0006\u0010\u001c\u001a\u00020\u001dJ\u001a\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u001bH\u0002J\u000e\u0010.\u001a\u00020/2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010.\u001a\u00020/2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u001bH\u0002J\u0010\u00100\u001a\u00020&2\u0006\u00101\u001a\u000202H\u0002J\u0012\u00103\u001a\u0004\u0018\u0001042\u0006\u00105\u001a\u00020\u0017H\u0002J\u000e\u00106\u001a\u0002072\u0006\u0010\u001c\u001a\u00020\u001dJ\u0012\u00108\u001a\u0004\u0018\u0001092\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\f\u0010:\u001a\u00020;*\u00020<H\u0002J\u0014\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b*\u0006\u0012\u0002\b\u00030>R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00120\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00120\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/FirTypeDeserializer;", "", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "annotationDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;", "typeParameterProtos", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "parent", "containingSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "(Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;Ljava/util/List;Lorg/jetbrains/kotlin/fir/deserialization/FirTypeDeserializer;Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)V", "ownTypeParameters", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "getOwnTypeParameters", "()Ljava/util/List;", "typeParameterDescriptors", "", "", "typeParameterNames", "", "attributesFromAnnotations", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "computeClassifier", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "fqNameIndex", "createSuspendFunctionType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "functionTypeConstructor", "arguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "isNullable", "", "attributes", "(Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;ZLorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "createSuspendFunctionTypeForBasicCase", "simpleType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeArgument", "typeArgumentProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Argument;", "typeParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/ConeTypeParameterLookupTag;", "typeParameterId", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "typeSymbol", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTag;", "convertVariance", "Lorg/jetbrains/kotlin/types/Variance;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter$Variance;", "typeParameters", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeDeserializer {
    private final AbstractAnnotationDeserializer annotationDeserializer;
    private final FirBasedSymbol<?> containingSymbol;
    private final FirModuleData moduleData;
    private final NameResolver nameResolver;
    private final FirTypeDeserializer parent;
    private final Map<Integer, FirTypeParameterSymbol> typeParameterDescriptors;
    private final Map<String, FirTypeParameterSymbol> typeParameterNames;
    private final TypeTable typeTable;

    /* JADX INFO: compiled from: FirTypeDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoBuf.TypeParameter.Variance.values().length];
            try {
                iArr[ProtoBuf.TypeParameter.Variance.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.TypeParameter.Variance.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.TypeParameter.Variance.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FirTypeDeserializer(FirModuleData moduleData, NameResolver nameResolver, TypeTable typeTable, AbstractAnnotationDeserializer annotationDeserializer, List<ProtoBuf.TypeParameter> typeParameterProtos, FirTypeDeserializer firTypeDeserializer, FirBasedSymbol<?> firBasedSymbol) {
        LinkedHashMap linkedHashMapEmptyMap;
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(annotationDeserializer, "annotationDeserializer");
        Intrinsics.checkNotNullParameter(typeParameterProtos, "typeParameterProtos");
        this.moduleData = moduleData;
        this.nameResolver = nameResolver;
        this.typeTable = typeTable;
        this.annotationDeserializer = annotationDeserializer;
        this.parent = firTypeDeserializer;
        this.containingSymbol = firBasedSymbol;
        List<ProtoBuf.TypeParameter> list = typeParameterProtos;
        if (!list.isEmpty()) {
            linkedHashMapEmptyMap = new LinkedHashMap();
        } else {
            linkedHashMapEmptyMap = MapsKt.emptyMap();
        }
        this.typeParameterDescriptors = linkedHashMapEmptyMap;
        if (!list.isEmpty()) {
            this.typeParameterNames = new LinkedHashMap();
            Intrinsics.checkNotNull(linkedHashMapEmptyMap, "null cannot be cast to non-null type java.util.LinkedHashMap<kotlin.Int, org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol>{ kotlin.collections.TypeAliasesKt.LinkedHashMap<kotlin.Int, org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol> }");
            LinkedHashMap linkedHashMap = (LinkedHashMap) linkedHashMapEmptyMap;
            ArrayList arrayList = new ArrayList();
            for (ProtoBuf.TypeParameter typeParameter : typeParameterProtos) {
                if (typeParameter.hasId()) {
                    Name name2 = NameResolverUtilKt.getName(this.nameResolver, typeParameter.getName());
                    FirTypeParameterSymbol firTypeParameterSymbol = new FirTypeParameterSymbol();
                    Map<String, FirTypeParameterSymbol> map = this.typeParameterNames;
                    String strAsString = name2.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    map.put(strAsString, firTypeParameterSymbol);
                    ArrayList arrayList2 = arrayList;
                    FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
                    firTypeParameterBuilder.setModuleData(this.moduleData);
                    firTypeParameterBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
                    firTypeParameterBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
                    firTypeParameterBuilder.setName(name2);
                    firTypeParameterBuilder.setSymbol(firTypeParameterSymbol);
                    FirBasedSymbol<?> firBasedSymbol2 = this.containingSymbol;
                    if (firBasedSymbol2 == null) {
                        throw new IllegalStateException("Top-level type parameter ???".toString());
                    }
                    firTypeParameterBuilder.setContainingDeclarationSymbol(firBasedSymbol2);
                    ProtoBuf.TypeParameter.Variance variance = typeParameter.getVariance();
                    Intrinsics.checkNotNullExpressionValue(variance, "getVariance(...)");
                    firTypeParameterBuilder.setVariance(convertVariance(variance));
                    firTypeParameterBuilder.setReified(typeParameter.getReified());
                    CollectionsKt.addAll(firTypeParameterBuilder.getAnnotations(), this.annotationDeserializer.loadTypeParameterAnnotations(typeParameter, this.nameResolver));
                    arrayList2.add(firTypeParameterBuilder);
                    linkedHashMap.put(Integer.valueOf(typeParameter.getId()), firTypeParameterSymbol);
                }
            }
            int i = 0;
            for (ProtoBuf.TypeParameter typeParameter2 : typeParameterProtos) {
                int i2 = i + 1;
                FirTypeParameterBuilder firTypeParameterBuilder2 = (FirTypeParameterBuilder) arrayList.get(i);
                List<ProtoBuf.Type> listUpperBounds = ProtoTypeTableUtilKt.upperBounds(typeParameter2, this.typeTable);
                List<FirTypeRef> bounds = firTypeParameterBuilder2.getBounds();
                for (ProtoBuf.Type type : listUpperBounds) {
                    FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                    firResolvedTypeRefBuilder.setType(type(type));
                    bounds.add(firResolvedTypeRefBuilder.mo6810build());
                }
                FirDeclarationBuildingUtilsKt.addDefaultBoundIfNecessary(firTypeParameterBuilder2);
                firTypeParameterBuilder2.mo6810build();
                i = i2;
            }
            return;
        }
        this.typeParameterNames = MapsKt.emptyMap();
    }

    public final List<FirTypeParameterSymbol> getOwnTypeParameters() {
        return CollectionsKt.toList(this.typeParameterDescriptors.values());
    }

    private final ConeClassLikeLookupTag computeClassifier(int fqNameIndex) {
        try {
            ClassId classId = NameResolverUtilKt.getClassId(this.nameResolver, fqNameIndex);
            if (classId.isLocal()) {
                classId = null;
            }
            if (classId == null) {
                classId = StandardClassIds.INSTANCE.getAny();
            }
            return TypeConstructionUtilsKt.toLookupTag(classId);
        } catch (Throwable th) {
            if (PlatformExceptionUtilsKt.shouldIjPlatformExceptionBeRethrown(th)) {
                throw th;
            }
            throw new RuntimeException("Looking up for " + NameResolverUtilKt.getClassId(this.nameResolver, fqNameIndex), th);
        }
    }

    private final ConeAttributes attributesFromAnnotations(ProtoBuf.Type proto) {
        return CopyUtilsKt.computeTypeAttributes$default(this.annotationDeserializer.loadTypeAnnotations(proto, this.nameResolver), this.moduleData.getSession(), null, null, false, 6, null);
    }

    public final ConeKotlinType type(ProtoBuf.Type proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        return type(proto, attributesFromAnnotations(proto));
    }

    public final ConeSimpleKotlinType simpleType(ProtoBuf.Type proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        ConeSimpleKotlinType coneSimpleKotlinTypeSimpleType = simpleType(proto, attributesFromAnnotations(proto));
        if (coneSimpleKotlinTypeSimpleType != null) {
            return coneSimpleKotlinTypeSimpleType;
        }
        return new ConeErrorType(new ConeSimpleDiagnostic("?!id:0", DiagnosticKind.DeserializationError), false, null, null, 14, null);
    }

    private final ConeKotlinType type(ProtoBuf.Type proto, ConeAttributes attributes) {
        if (proto.hasFlexibleTypeCapabilitiesId()) {
            ConeSimpleKotlinType coneSimpleKotlinTypeSimpleType = simpleType(proto, attributes);
            ProtoBuf.Type typeFlexibleUpperBound = ProtoTypeTableUtilKt.flexibleUpperBound(proto, this.typeTable);
            Intrinsics.checkNotNull(typeFlexibleUpperBound);
            ConeSimpleKotlinType coneSimpleKotlinTypeSimpleType2 = simpleType(typeFlexibleUpperBound, attributes);
            if (Intrinsics.areEqual(coneSimpleKotlinTypeSimpleType, FirTypeUtilsKt.getConeType(this.moduleData.getSession().getBuiltinTypes().getNothingType())) && Intrinsics.areEqual(coneSimpleKotlinTypeSimpleType2, FirTypeUtilsKt.getConeType(this.moduleData.getSession().getBuiltinTypes().getNullableAnyType()))) {
                return TypeUtilsKt.create(ConeDynamicType.INSTANCE, this.moduleData.getSession());
            }
            Intrinsics.checkNotNull(coneSimpleKotlinTypeSimpleType);
            Intrinsics.checkNotNull(coneSimpleKotlinTypeSimpleType2);
            return new ConeFlexibleType(coneSimpleKotlinTypeSimpleType, coneSimpleKotlinTypeSimpleType2);
        }
        ConeSimpleKotlinType coneSimpleKotlinTypeSimpleType3 = simpleType(proto, attributes);
        if (coneSimpleKotlinTypeSimpleType3 == null) {
            coneSimpleKotlinTypeSimpleType3 = new ConeErrorType(new ConeSimpleDiagnostic("?!id:0", DiagnosticKind.DeserializationError), false, null, null, 14, null);
        }
        return coneSimpleKotlinTypeSimpleType3;
    }

    private final ConeTypeParameterLookupTag typeParameterSymbol(int typeParameterId) {
        ConeTypeParameterLookupTag lookupTag;
        FirTypeParameterSymbol firTypeParameterSymbol = this.typeParameterDescriptors.get(Integer.valueOf(typeParameterId));
        if (firTypeParameterSymbol != null && (lookupTag = firTypeParameterSymbol.getLookupTag()) != null) {
            return lookupTag;
        }
        FirTypeDeserializer firTypeDeserializer = this.parent;
        if (firTypeDeserializer != null) {
            return firTypeDeserializer.typeParameterSymbol(typeParameterId);
        }
        return null;
    }

    private final Variance convertVariance(ProtoBuf.TypeParameter.Variance variance) {
        int i = WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
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

    public final List<FirTypeParameterSymbol> typeParameters(FirClassLikeSymbol<?> firClassLikeSymbol) {
        List<FirTypeParameterRef> typeParameters;
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        E fir = firClassLikeSymbol.getFir();
        ArrayList arrayList = null;
        FirTypeParameterRefsOwner firTypeParameterRefsOwner = fir instanceof FirTypeParameterRefsOwner ? (FirTypeParameterRefsOwner) fir : null;
        if (firTypeParameterRefsOwner != null && (typeParameters = firTypeParameterRefsOwner.getTypeParameters()) != null) {
            List<FirTypeParameterRef> list = typeParameters;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add(((FirTypeParameterRef) it.next()).getSymbol());
            }
            arrayList = arrayList2;
        }
        return arrayList == null ? CollectionsKt.emptyList() : arrayList;
    }

    private final ConeSimpleKotlinType simpleType(ProtoBuf.Type proto, ConeAttributes attributes) {
        ConeClassLikeTypeImpl coneClassLikeTypeImpl;
        ConeClassifierLookupTag coneClassifierLookupTagTypeSymbol = typeSymbol(proto);
        if (coneClassifierLookupTagTypeSymbol == null) {
            return null;
        }
        if (coneClassifierLookupTagTypeSymbol instanceof ConeTypeParameterLookupTag) {
            ConeTypeParameterTypeImpl coneTypeParameterTypeImpl = new ConeTypeParameterTypeImpl((ConeTypeParameterLookupTag) coneClassifierLookupTagTypeSymbol, proto.getNullable(), null, 4, null);
            Boolean bool = Flags.DEFINITELY_NOT_NULL_TYPE.get(proto.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            if (bool.booleanValue()) {
                ConeDefinitelyNotNullType coneDefinitelyNotNullTypeCreate = TypeUtilsKt.create(ConeDefinitelyNotNullType.INSTANCE, coneTypeParameterTypeImpl, TypeComponentsKt.getTypeContext(this.moduleData.getSession()), true);
                return coneDefinitelyNotNullTypeCreate != null ? coneDefinitelyNotNullTypeCreate : coneTypeParameterTypeImpl;
            }
            return coneTypeParameterTypeImpl;
        }
        if (!(coneClassifierLookupTagTypeSymbol instanceof ConeClassLikeLookupTag)) {
            return null;
        }
        List<ProtoBuf.Type.Argument> listSimpleType$collectAllArguments = simpleType$collectAllArguments(proto, this);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSimpleType$collectAllArguments, 10));
        Iterator<T> it = listSimpleType$collectAllArguments.iterator();
        while (it.hasNext()) {
            arrayList.add(typeArgument((ProtoBuf.Type.Argument) it.next()));
        }
        ConeTypeProjection[] coneTypeProjectionArr = (ConeTypeProjection[]) arrayList.toArray(new ConeTypeProjection[0]);
        ConeClassLikeLookupTag coneClassLikeLookupTag = (ConeClassLikeLookupTag) coneClassifierLookupTagTypeSymbol;
        FunctionTypeKind functionTypeKindExtractSingleExtensionKindForDeserializedConeType = FirFunctionTypeKindServiceKt.getFunctionTypeService(this.moduleData.getSession()).extractSingleExtensionKindForDeserializedConeType(coneClassLikeLookupTag.getClassId(), CustomAnnotationTypeAttributeKt.getCustomAnnotations(attributes));
        if (functionTypeKindExtractSingleExtensionKindForDeserializedConeType != null) {
            if (coneTypeProjectionArr.length == 0) {
                return new ConeErrorType(new ConeSimpleDiagnostic("Illegal number of arguments for extension functional type " + functionTypeKindExtractSingleExtensionKindForDeserializedConeType, null, 2, null), false, coneTypeProjectionArr, attributes, 2, null);
            }
            coneClassLikeTypeImpl = new ConeClassLikeTypeImpl(new ConeClassLikeLookupTagImpl(functionTypeKindExtractSingleExtensionKindForDeserializedConeType.numberedClassId(coneTypeProjectionArr.length - 1)), coneTypeProjectionArr, proto.getNullable(), attributes);
        } else {
            Boolean bool2 = Flags.SUSPEND_TYPE.get(proto.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
            if (bool2.booleanValue()) {
                coneClassLikeTypeImpl = createSuspendFunctionType(coneClassLikeLookupTag, coneTypeProjectionArr, proto.getNullable(), attributes);
            } else {
                coneClassLikeTypeImpl = new ConeClassLikeTypeImpl(coneClassLikeLookupTag, coneTypeProjectionArr, proto.getNullable(), attributes);
            }
        }
        return coneClassLikeTypeImpl;
    }

    private static final List<ProtoBuf.Type.Argument> simpleType$collectAllArguments(ProtoBuf.Type type, FirTypeDeserializer firTypeDeserializer) {
        List<ProtoBuf.Type.Argument> argumentList = type.getArgumentList();
        Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
        List<ProtoBuf.Type.Argument> list = argumentList;
        ProtoBuf.Type typeOuterType = ProtoTypeTableUtilKt.outerType(type, firTypeDeserializer.typeTable);
        List<ProtoBuf.Type.Argument> listSimpleType$collectAllArguments = typeOuterType != null ? simpleType$collectAllArguments(typeOuterType, firTypeDeserializer) : null;
        if (listSimpleType$collectAllArguments == null) {
            listSimpleType$collectAllArguments = CollectionsKt.emptyList();
        }
        return CollectionsKt.plus((Collection) list, (Iterable) listSimpleType$collectAllArguments);
    }

    private static final boolean createSuspendFunctionTypeForBasicCase$isContinuation(ConeClassLikeType coneClassLikeType) {
        return coneClassLikeType.getTypeArguments().length == 1 && Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getContinuation());
    }

    private final ConeClassLikeType createSuspendFunctionTypeForBasicCase(ConeClassLikeLookupTag functionTypeConstructor, ConeTypeProjection[] arguments, boolean isNullable, ConeAttributes attributes) {
        Object orNull = ArraysKt.getOrNull(arguments, ArraysKt.getLastIndex(arguments) - 1);
        ConeClassLikeType coneClassLikeType = orNull instanceof ConeClassLikeType ? (ConeClassLikeType) orNull : null;
        if (coneClassLikeType == null) {
            return null;
        }
        if (!createSuspendFunctionTypeForBasicCase$isContinuation(coneClassLikeType)) {
            return new ConeClassLikeTypeImpl(functionTypeConstructor, arguments, isNullable, attributes);
        }
        Object objSingle = ArraysKt.single(coneClassLikeType.getTypeArguments());
        Intrinsics.checkNotNull(objSingle, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection");
        List listDropLast = ArraysKt.dropLast(arguments, 2);
        FunctionTypeKind.SuspendFunction suspendFunction = FunctionTypeKind.SuspendFunction.INSTANCE;
        return new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(new ClassId(suspendFunction.getPackageFqName(), suspendFunction.numberedClassName(listDropLast.size()))), (ConeTypeProjection[]) CollectionsKt.plus((Collection<? extends ConeKotlinTypeProjection>) listDropLast, (ConeKotlinTypeProjection) objSingle).toArray(new ConeTypeProjection[0]), isNullable, attributes);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ConeClassLikeType createSuspendFunctionType(ConeClassLikeLookupTag functionTypeConstructor, ConeTypeProjection[] arguments, boolean isNullable, ConeAttributes attributes) {
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(functionTypeConstructor, this.moduleData.getSession());
        ConeClassLikeType coneClassLikeTypeCreateSuspendFunctionTypeForBasicCase = null;
        FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
        Intrinsics.checkNotNull(firClassLikeDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner");
        int size = firClassLikeDeclaration.getTypeParameters().size() - arguments.length;
        if (size == 0) {
            coneClassLikeTypeCreateSuspendFunctionTypeForBasicCase = createSuspendFunctionTypeForBasicCase(functionTypeConstructor, arguments, isNullable, attributes);
        } else if (size == 1) {
            int length = arguments.length - 1;
            if (length >= 0) {
                FunctionTypeKind.SuspendFunction suspendFunction = FunctionTypeKind.SuspendFunction.INSTANCE;
                coneClassLikeTypeCreateSuspendFunctionTypeForBasicCase = new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(new ClassId(suspendFunction.getPackageFqName(), suspendFunction.numberedClassName(length))), arguments, isNullable, attributes);
            }
            coneClassLikeTypeCreateSuspendFunctionTypeForBasicCase = coneClassLikeTypeCreateSuspendFunctionTypeForBasicCase;
        }
        if (coneClassLikeTypeCreateSuspendFunctionTypeForBasicCase == null) {
            return new ConeErrorType(new ConeSimpleDiagnostic("Bad suspend function in metadata with constructor: " + functionTypeConstructor, DiagnosticKind.DeserializationError), false, null, null, 14, null);
        }
        return coneClassLikeTypeCreateSuspendFunctionTypeForBasicCase;
    }

    private final ConeClassifierLookupTag typeSymbol(ProtoBuf.Type proto) {
        if (proto.hasClassName()) {
            return computeClassifier(proto.getClassName());
        }
        if (proto.hasTypeAliasName()) {
            return computeClassifier(proto.getTypeAliasName());
        }
        if (proto.hasTypeParameter()) {
            return typeParameterSymbol(proto.getTypeParameter());
        }
        if (!proto.hasTypeParameterName()) {
            return null;
        }
        FirTypeParameterSymbol firTypeParameterSymbol = this.typeParameterNames.get(this.nameResolver.getString(proto.getTypeParameterName()));
        return firTypeParameterSymbol != null ? firTypeParameterSymbol.getLookupTag() : null;
    }

    private final ConeTypeProjection typeArgument(ProtoBuf.Type.Argument typeArgumentProto) {
        if (typeArgumentProto.getProjection() == ProtoBuf.Type.Argument.Projection.STAR) {
            return ConeStarProjection.INSTANCE;
        }
        ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
        ProtoBuf.Type.Argument.Projection projection = typeArgumentProto.getProjection();
        Intrinsics.checkNotNullExpressionValue(projection, "getProjection(...)");
        Variance variance = protoEnumFlags.variance(projection);
        ProtoBuf.Type type = ProtoTypeTableUtilKt.type(typeArgumentProto, this.typeTable);
        if (type == null) {
            return new ConeErrorType(new ConeSimpleDiagnostic("No type recorded", DiagnosticKind.DeserializationError), false, null, null, 14, null);
        }
        return ConeTypeUtilsKt.toTypeProjection(type(type), variance);
    }

    public final FirTypeRef typeRef(ProtoBuf.Type proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), this.annotationDeserializer.loadTypeAnnotations(proto, this.nameResolver));
        firResolvedTypeRefBuilder.setType(type(proto, CopyUtilsKt.computeTypeAttributes$default(firResolvedTypeRefBuilder.getAnnotations(), this.moduleData.getSession(), null, null, false, 6, null)));
        return firResolvedTypeRefBuilder.mo6810build();
    }
}
