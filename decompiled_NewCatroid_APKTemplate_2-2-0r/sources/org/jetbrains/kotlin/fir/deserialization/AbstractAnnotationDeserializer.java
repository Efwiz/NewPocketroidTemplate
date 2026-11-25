package org.jetbrains.kotlin.fir.deserialization;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationArgumentMappingBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArrayLiteralBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirClassReferenceExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt;
import org.jetbrains.kotlin.fir.expressions.builder.FirGetClassCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirPropertyAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.references.FirFromMissingDependenciesNamedReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.builder.FirFromMissingDependenciesNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolver;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.ConstantValueKind;

/* JADX INFO: compiled from: AbstractAnnotationDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ê\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b&\u0018\u00002\u00020\u0001:\u0001TB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J7\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u000b0\n\"\u0004\b\u0000\u0010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¢\u0006\u0002\u0010\u0011J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\"\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u001dJ\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0000H\u0016J4\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*H\u0016J\u001c\u0010+\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\u0006\u0010-\u001a\u00020.2\u0006\u0010\u0018\u001a\u00020\u0019J0\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u00100\u001a\u0002012\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*H\u0016J8\u00102\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u00103\u001a\u0002042\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*2\u0006\u0010\f\u001a\u000205H\u0016J0\u00106\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u00107\u001a\u0002082\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*H\u0016J:\u00109\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&2\b\u0010:\u001a\u0004\u0018\u00010.2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*H\u0016J0\u0010;\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*H\u0016J0\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*H\u0016J8\u0010=\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*2\u0006\u0010>\u001a\u00020?H\u0016J8\u0010@\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*2\u0006\u0010A\u001a\u00020?H\u0016J\u001c\u0010B\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\u0006\u0010C\u001a\u00020D2\u0006\u0010\u0018\u001a\u00020\u0019J\u001e\u0010E\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\u0006\u0010F\u001a\u00020G2\u0006\u0010\u0018\u001a\u00020\u0019H&J\u001e\u0010H\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\u0006\u0010I\u001a\u00020J2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016JR\u0010K\u001a\b\u0012\u0004\u0012\u00020\u001b0,2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u00103\u001a\u0002042\u0006\u0010L\u001a\u00020M2\b\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*2\u0006\u0010\f\u001a\u0002052\u0006\u0010N\u001a\u00020?H\u0016J(\u0010O\u001a\u00020\"2\u0006\u0010\u000e\u001a\u00020P2\u0006\u0010\u0018\u001a\u00020\u00192\u000e\u0010Q\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010S0RH\u0002R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006U²\u0006\u0018\u0010V\u001a\u0010\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020Y\u0018\u00010WX\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", ConfigConstants.CONFIG_PROTOCOL_SECTION, "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "getProtocol", "()Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", PsiKeyword.CONST, "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", ExifInterface.GPS_DIRECTION_TRUE, Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "value", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "(Lorg/jetbrains/kotlin/types/ConstantValueKind;Ljava/lang/Object;Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;)Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "createArgumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "deserializeAnnotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "useSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "inheritAnnotationInfo", "", "parent", "loadAnnotationPropertyDefaultValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "propertyProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "expectedPropertyType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "loadClassAnnotations", "", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "loadConstructorAnnotations", "constructorProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor;", "loadExtensionReceiverParameterAnnotations", "callableProto", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer$CallableKind;", "loadFunctionAnnotations", "functionProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "loadPropertyAnnotations", "containingClassProto", "loadPropertyBackingFieldAnnotations", "loadPropertyDelegatedFieldAnnotations", "loadPropertyGetterAnnotations", "getterFlags", "", "loadPropertySetterAnnotations", "setterFlags", "loadTypeAliasAnnotations", "aliasProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "loadTypeAnnotations", "typeProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "loadTypeParameterAnnotations", "typeParameterProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "loadValueParameterAnnotations", "valueParameterProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "parameterIndex", "resolveValue", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value;", "expectedType", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "CallableKind", "fir-deserialization", "parameterByName", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractAnnotationDeserializer {
    private final SerializerExtensionProtocol protocol;
    private final FirSession session;

    /* JADX INFO: compiled from: AbstractAnnotationDeserializer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer$CallableKind;", "", "(Ljava/lang/String;I)V", "PROPERTY", "PROPERTY_GETTER", "PROPERTY_SETTER", "OTHERS", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum CallableKind {
        PROPERTY,
        PROPERTY_GETTER,
        PROPERTY_SETTER,
        OTHERS;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<CallableKind> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: AbstractAnnotationDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoBuf.Annotation.Argument.Value.Type.values().length];
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.BYTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.SHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.INT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.LONG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.CHAR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.DOUBLE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.BOOLEAN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.STRING.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.ANNOTATION.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.CLASS.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.ENUM.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.ARRAY.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public void inheritAnnotationInfo(AbstractAnnotationDeserializer parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
    }

    public FirExpression loadAnnotationPropertyDefaultValue(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, FirTypeRef expectedPropertyType, NameResolver nameResolver, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(expectedPropertyType, "expectedPropertyType");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        return null;
    }

    public abstract List<FirAnnotation> loadTypeAnnotations(ProtoBuf.Type typeProto, NameResolver nameResolver);

    public AbstractAnnotationDeserializer(FirSession session, SerializerExtensionProtocol protocol) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(protocol, "protocol");
        this.session = session;
        this.protocol = protocol;
    }

    protected final SerializerExtensionProtocol getProtocol() {
        return this.protocol;
    }

    public final List<FirAnnotation> loadClassAnnotations(ProtoBuf.Class classProto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        if (!Flags.HAS_ANNOTATIONS.get(classProto.getFlags()).booleanValue()) {
            return CollectionsKt.emptyList();
        }
        List listEmptyList = (List) classProto.getExtension(this.protocol.getClassAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(deserializeAnnotation$default(this, (ProtoBuf.Annotation) it.next(), nameResolver, null, 4, null));
        }
        return arrayList;
    }

    public final List<FirAnnotation> loadTypeAliasAnnotations(ProtoBuf.TypeAlias aliasProto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(aliasProto, "aliasProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        if (!Flags.HAS_ANNOTATIONS.get(aliasProto.getFlags()).booleanValue()) {
            return CollectionsKt.emptyList();
        }
        List<ProtoBuf.Annotation> annotationList = aliasProto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        List<ProtoBuf.Annotation> list = annotationList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ProtoBuf.Annotation annotation : list) {
            Intrinsics.checkNotNull(annotation);
            arrayList.add(deserializeAnnotation$default(this, annotation, nameResolver, null, 4, null));
        }
        return arrayList;
    }

    public List<FirAnnotation> loadFunctionAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Function functionProto, NameResolver nameResolver, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(functionProto, "functionProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (!Flags.HAS_ANNOTATIONS.get(functionProto.getFlags()).booleanValue()) {
            return CollectionsKt.emptyList();
        }
        List listEmptyList = (List) functionProto.getExtension(this.protocol.getFunctionAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(deserializeAnnotation$default(this, (ProtoBuf.Annotation) it.next(), nameResolver, null, 4, null));
        }
        return arrayList;
    }

    public List<FirAnnotation> loadPropertyAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, ProtoBuf.Class containingClassProto, NameResolver nameResolver, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (!Flags.HAS_ANNOTATIONS.get(propertyProto.getFlags()).booleanValue()) {
            return CollectionsKt.emptyList();
        }
        List listEmptyList = (List) propertyProto.getExtension(this.protocol.getPropertyAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(deserializeAnnotation((ProtoBuf.Annotation) it.next(), nameResolver, AnnotationUseSiteTarget.PROPERTY));
        }
        return arrayList;
    }

    public List<FirAnnotation> loadPropertyBackingFieldAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, NameResolver nameResolver, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        return CollectionsKt.emptyList();
    }

    public List<FirAnnotation> loadPropertyDelegatedFieldAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, NameResolver nameResolver, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        return CollectionsKt.emptyList();
    }

    public List<FirAnnotation> loadPropertyGetterAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, NameResolver nameResolver, TypeTable typeTable, int getterFlags) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (!Flags.HAS_ANNOTATIONS.get(getterFlags).booleanValue()) {
            return CollectionsKt.emptyList();
        }
        List listEmptyList = (List) propertyProto.getExtension(this.protocol.getPropertyGetterAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(deserializeAnnotation((ProtoBuf.Annotation) it.next(), nameResolver, AnnotationUseSiteTarget.PROPERTY_GETTER));
        }
        return arrayList;
    }

    public List<FirAnnotation> loadPropertySetterAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, NameResolver nameResolver, TypeTable typeTable, int setterFlags) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (!Flags.HAS_ANNOTATIONS.get(setterFlags).booleanValue()) {
            return CollectionsKt.emptyList();
        }
        List listEmptyList = (List) propertyProto.getExtension(this.protocol.getPropertySetterAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(deserializeAnnotation((ProtoBuf.Annotation) it.next(), nameResolver, AnnotationUseSiteTarget.PROPERTY_SETTER));
        }
        return arrayList;
    }

    public List<FirAnnotation> loadConstructorAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Constructor constructorProto, NameResolver nameResolver, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(constructorProto, "constructorProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if (!Flags.HAS_ANNOTATIONS.get(constructorProto.getFlags()).booleanValue()) {
            return CollectionsKt.emptyList();
        }
        List listEmptyList = (List) constructorProto.getExtension(this.protocol.getConstructorAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(deserializeAnnotation$default(this, (ProtoBuf.Annotation) it.next(), nameResolver, null, 4, null));
        }
        return arrayList;
    }

    public List<FirAnnotation> loadValueParameterAnnotations(DeserializedContainerSource containerSource, MessageLite callableProto, ProtoBuf.ValueParameter valueParameterProto, ProtoBuf.Class classProto, NameResolver nameResolver, TypeTable typeTable, CallableKind kind, int parameterIndex) {
        Intrinsics.checkNotNullParameter(callableProto, "callableProto");
        Intrinsics.checkNotNullParameter(valueParameterProto, "valueParameterProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (!Flags.HAS_ANNOTATIONS.get(valueParameterProto.getFlags()).booleanValue()) {
            return CollectionsKt.emptyList();
        }
        List listEmptyList = (List) valueParameterProto.getExtension(this.protocol.getParameterAnnotation());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(deserializeAnnotation$default(this, (ProtoBuf.Annotation) it.next(), nameResolver, null, 4, null));
        }
        return arrayList;
    }

    public List<FirAnnotation> loadExtensionReceiverParameterAnnotations(DeserializedContainerSource containerSource, MessageLite callableProto, NameResolver nameResolver, TypeTable typeTable, CallableKind kind) {
        Intrinsics.checkNotNullParameter(callableProto, "callableProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return CollectionsKt.emptyList();
    }

    public List<FirAnnotation> loadTypeParameterAnnotations(ProtoBuf.TypeParameter typeParameterProto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(typeParameterProto, "typeParameterProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        return CollectionsKt.emptyList();
    }

    public static /* synthetic */ FirAnnotation deserializeAnnotation$default(AbstractAnnotationDeserializer abstractAnnotationDeserializer, ProtoBuf.Annotation annotation, NameResolver nameResolver, AnnotationUseSiteTarget annotationUseSiteTarget, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: deserializeAnnotation");
        }
        if ((i & 4) != 0) {
            annotationUseSiteTarget = null;
        }
        return abstractAnnotationDeserializer.deserializeAnnotation(annotation, nameResolver, annotationUseSiteTarget);
    }

    public final FirAnnotation deserializeAnnotation(ProtoBuf.Annotation proto, NameResolver nameResolver, AnnotationUseSiteTarget useSiteTarget) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        ClassId classId = NameResolverUtilKt.getClassId(nameResolver, proto.getId());
        FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(TypeConstructionUtilsKt.constructClassType$default(TypeConstructionUtilsKt.toLookupTag(classId), new ConeTypeProjection[0], false, null, 4, null));
        firAnnotationBuilder.setAnnotationTypeRef(firResolvedTypeRefBuilder.mo6809build());
        FirLazyDeclarationResolver lazyDeclarationResolver = FirLazyDeclarationResolverKt.getLazyDeclarationResolver(this.session);
        boolean lazyResolveContractChecksEnabled = lazyDeclarationResolver.getLazyResolveContractChecksEnabled();
        lazyDeclarationResolver.setLazyResolveContractChecksEnabled(false);
        try {
            firAnnotationBuilder.setArgumentMapping(createArgumentMapping(proto, classId, nameResolver));
            Unit unit = Unit.INSTANCE;
            lazyDeclarationResolver.setLazyResolveContractChecksEnabled(lazyResolveContractChecksEnabled);
            if (useSiteTarget != null) {
                firAnnotationBuilder.setUseSiteTarget(useSiteTarget);
            }
            return firAnnotationBuilder.mo6809build();
        } catch (Throwable th) {
            lazyDeclarationResolver.setLazyResolveContractChecksEnabled(lazyResolveContractChecksEnabled);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map<Name, FirValueParameter> createArgumentMapping$lambda$14$lambda$12(Lazy<? extends Map<Name, ? extends FirValueParameter>> lazy) {
        return (Map) lazy.getValue();
    }

    /* JADX WARN: Code duplicated, block: B:40:0x013f  */
    /* JADX WARN: Code duplicated, block: B:42:0x0156  */
    /* JADX WARN: Code duplicated, block: B:43:0x0159  */
    /* JADX WARN: Code duplicated, block: B:46:0x016d  */
    /* JADX WARN: Multi-variable type inference failed */
    private final FirExpression resolveValue(ProtoBuf.Annotation.Argument.Value value, NameResolver nameResolver, Function0<? extends ConeKotlinType> expectedType) {
        List listEmptyList;
        FirEnumEntry firEnumEntry;
        FirFromMissingDependenciesNamedReference firFromMissingDependenciesNamedReferenceBuild;
        FirResolvedNamedReference firResolvedNamedReferenceBuild;
        final ConeClassLikeType type;
        Boolean bool = Flags.IS_UNSIGNED.get(value.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        boolean zBooleanValue = bool.booleanValue();
        ProtoBuf.Annotation.Argument.Value.Type type2 = value.getType();
        Object obj = null;
        switch (type2 == null ? -1 : WhenMappings.$EnumSwitchMapping$0[type2.ordinal()]) {
            case 1:
                return m6889const(zBooleanValue ? ConstantValueKind.UnsignedByte.INSTANCE : ConstantValueKind.Byte.INSTANCE, Byte.valueOf((byte) value.getIntValue()), this.session.getBuiltinTypes().getByteType());
            case 2:
                return m6889const(zBooleanValue ? ConstantValueKind.UnsignedShort.INSTANCE : ConstantValueKind.Short.INSTANCE, Short.valueOf((short) value.getIntValue()), this.session.getBuiltinTypes().getShortType());
            case 3:
                return m6889const(zBooleanValue ? ConstantValueKind.UnsignedInt.INSTANCE : ConstantValueKind.Int.INSTANCE, Integer.valueOf((int) value.getIntValue()), this.session.getBuiltinTypes().getIntType());
            case 4:
                return m6889const(zBooleanValue ? ConstantValueKind.UnsignedLong.INSTANCE : ConstantValueKind.Long.INSTANCE, Long.valueOf(value.getIntValue()), this.session.getBuiltinTypes().getLongType());
            case 5:
                return m6889const(ConstantValueKind.Char.INSTANCE, Character.valueOf((char) value.getIntValue()), this.session.getBuiltinTypes().getCharType());
            case 6:
                return m6889const(ConstantValueKind.Float.INSTANCE, Float.valueOf(value.getFloatValue()), this.session.getBuiltinTypes().getFloatType());
            case 7:
                return m6889const(ConstantValueKind.Double.INSTANCE, Double.valueOf(value.getDoubleValue()), this.session.getBuiltinTypes().getDoubleType());
            case 8:
                return m6889const(ConstantValueKind.Boolean.INSTANCE, Boolean.valueOf(value.getIntValue() != 0), this.session.getBuiltinTypes().getBooleanType());
            case 9:
                return m6889const(ConstantValueKind.String.INSTANCE, nameResolver.getString(value.getStringValue()), this.session.getBuiltinTypes().getStringType());
            case 10:
                ProtoBuf.Annotation annotation = value.getAnnotation();
                Intrinsics.checkNotNullExpressionValue(annotation, "getAnnotation(...)");
                return deserializeAnnotation$default(this, annotation, nameResolver, null, 4, null);
            case 11:
                FirGetClassCallBuilder firGetClassCallBuilder = new FirGetClassCallBuilder();
                ConeLookupTagBasedType coneLookupTagBasedTypeConstructType$default = TypeConstructionUtilsKt.constructType$default((ConeClassifierLookupTag) TypeConstructionUtilsKt.toLookupTag(NameResolverUtilKt.getClassId(nameResolver, value.getClassId())), new ConeTypeProjection[0], false, (ConeAttributes) null, 4, (Object) null);
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder.setType(TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.INSTANCE.getKClass(), new ConeLookupTagBasedType[]{coneLookupTagBasedTypeConstructType$default}, false, null, 4, null));
                FirResolvedTypeRef firResolvedTypeRefBuild = firResolvedTypeRefBuilder.mo6809build();
                FirClassReferenceExpressionBuilder firClassReferenceExpressionBuilder = new FirClassReferenceExpressionBuilder();
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder2.setType(coneLookupTagBasedTypeConstructType$default);
                firClassReferenceExpressionBuilder.setClassTypeRef(firResolvedTypeRefBuilder2.mo6809build());
                FirResolvedTypeRef firResolvedTypeRef = firResolvedTypeRefBuild;
                firClassReferenceExpressionBuilder.setTypeRef(firResolvedTypeRef);
                firGetClassCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firClassReferenceExpressionBuilder.mo6809build()));
                firGetClassCallBuilder.setTypeRef(firResolvedTypeRef);
                return firGetClassCallBuilder.mo6809build();
            case 12:
                FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
                ClassId classId = NameResolverUtilKt.getClassId(nameResolver, value.getClassId());
                Name name2 = NameResolverUtilKt.getName(nameResolver, value.getEnumValueId());
                FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol((ConeClassLikeLookupTag) TypeConstructionUtilsKt.toLookupTag(classId), this.session);
                FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
                FirRegularClass firRegularClass = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
                if (firRegularClass == null || (listEmptyList = DeclarationUtilsKt.collectEnumEntries(firRegularClass)) == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                for (Object obj2 : listEmptyList) {
                    if (Intrinsics.areEqual(((FirEnumEntry) obj2).getName(), name2)) {
                        obj = obj2;
                        firEnumEntry = (FirEnumEntry) obj;
                        if (firEnumEntry != null) {
                            FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
                            firResolvedNamedReferenceBuilder.setName(name2);
                            firResolvedNamedReferenceBuilder.setResolvedSymbol(firEnumEntry.getSymbol());
                            firResolvedNamedReferenceBuild = firResolvedNamedReferenceBuilder.build();
                            if (firResolvedNamedReferenceBuild != null) {
                                firFromMissingDependenciesNamedReferenceBuild = firResolvedNamedReferenceBuild;
                            } else {
                                FirFromMissingDependenciesNamedReferenceBuilder firFromMissingDependenciesNamedReferenceBuilder = new FirFromMissingDependenciesNamedReferenceBuilder();
                                firFromMissingDependenciesNamedReferenceBuilder.setName(name2);
                                firFromMissingDependenciesNamedReferenceBuild = firFromMissingDependenciesNamedReferenceBuilder.build();
                            }
                        } else {
                            FirFromMissingDependenciesNamedReferenceBuilder firFromMissingDependenciesNamedReferenceBuilder2 = new FirFromMissingDependenciesNamedReferenceBuilder();
                            firFromMissingDependenciesNamedReferenceBuilder2.setName(name2);
                            firFromMissingDependenciesNamedReferenceBuild = firFromMissingDependenciesNamedReferenceBuilder2.build();
                        }
                        firPropertyAccessExpressionBuilder.setCalleeReference(firFromMissingDependenciesNamedReferenceBuild);
                        if (firEnumEntry != null) {
                            firPropertyAccessExpressionBuilder.setTypeRef(firEnumEntry.getReturnTypeRef());
                        }
                        return firPropertyAccessExpressionBuilder.mo6809build();
                    }
                }
                firEnumEntry = (FirEnumEntry) obj;
                if (firEnumEntry != null) {
                    FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder2 = new FirResolvedNamedReferenceBuilder();
                    firResolvedNamedReferenceBuilder2.setName(name2);
                    firResolvedNamedReferenceBuilder2.setResolvedSymbol(firEnumEntry.getSymbol());
                    firResolvedNamedReferenceBuild = firResolvedNamedReferenceBuilder2.build();
                    if (firResolvedNamedReferenceBuild != null) {
                        firFromMissingDependenciesNamedReferenceBuild = firResolvedNamedReferenceBuild;
                    } else {
                        FirFromMissingDependenciesNamedReferenceBuilder firFromMissingDependenciesNamedReferenceBuilder3 = new FirFromMissingDependenciesNamedReferenceBuilder();
                        firFromMissingDependenciesNamedReferenceBuilder3.setName(name2);
                        firFromMissingDependenciesNamedReferenceBuild = firFromMissingDependenciesNamedReferenceBuilder3.build();
                    }
                } else {
                    FirFromMissingDependenciesNamedReferenceBuilder firFromMissingDependenciesNamedReferenceBuilder4 = new FirFromMissingDependenciesNamedReferenceBuilder();
                    firFromMissingDependenciesNamedReferenceBuilder4.setName(name2);
                    firFromMissingDependenciesNamedReferenceBuild = firFromMissingDependenciesNamedReferenceBuilder4.build();
                }
                firPropertyAccessExpressionBuilder.setCalleeReference(firFromMissingDependenciesNamedReferenceBuild);
                if (firEnumEntry != null) {
                    firPropertyAccessExpressionBuilder.setTypeRef(firEnumEntry.getReturnTypeRef());
                }
                return firPropertyAccessExpressionBuilder.mo6809build();
            case 13:
                ConeKotlinType coneKotlinTypeInvoke = expectedType.invoke();
                if (coneKotlinTypeInvoke == null || (type = ArrayUtilsKt.arrayElementType(coneKotlinTypeInvoke)) == null) {
                    type = this.session.getBuiltinTypes().getAnyType().getType();
                }
                FirArrayLiteralBuilder firArrayLiteralBuilder = new FirArrayLiteralBuilder();
                FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
                List<ProtoBuf.Annotation.Argument.Value> arrayElementList = value.getArrayElementList();
                Intrinsics.checkNotNullExpressionValue(arrayElementList, "getArrayElementList(...)");
                List<FirExpression> arguments = firArgumentListBuilder.getArguments();
                for (ProtoBuf.Annotation.Argument.Value value2 : arrayElementList) {
                    Intrinsics.checkNotNull(value2);
                    arguments.add(resolveValue(value2, nameResolver, new Function0<ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer$resolveValue$3$1$1$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final ConeKotlinType invoke() {
                            return type;
                        }
                    }));
                }
                firArrayLiteralBuilder.setArgumentList(firArgumentListBuilder.build());
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder3 = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder3.setType(ArrayUtilsKt.createArrayType$default(type, false, false, 3, null));
                firArrayLiteralBuilder.setTypeRef(firResolvedTypeRefBuilder3.mo6809build());
                return firArrayLiteralBuilder.mo6809build();
            default:
                throw new IllegalStateException(("Unsupported annotation argument type: " + value.getType() + " (expected " + expectedType + ')').toString());
        }
    }

    /* JADX INFO: renamed from: const, reason: not valid java name */
    private final <T> FirConstExpression<T> m6889const(ConstantValueKind<T> kind, T value, FirResolvedTypeRef typeRef) {
        FirConstExpression<T> firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(null, kind, value, null, true, 8, null);
        firConstExpressionBuildConstExpression$default.replaceTypeRef(typeRef);
        return firConstExpressionBuildConstExpression$default;
    }

    private final FirAnnotationArgumentMapping createArgumentMapping(ProtoBuf.Annotation proto, final ClassId classId, NameResolver nameResolver) {
        FirAnnotationArgumentMappingBuilder firAnnotationArgumentMappingBuilder = new FirAnnotationArgumentMappingBuilder();
        if (proto.getArgumentCount() != 0) {
            final Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<Name, ? extends FirValueParameter>>() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer$createArgumentMapping$1$parameterByName$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function0
                public final Map<Name, ? extends FirValueParameter> invoke() {
                    FirRegularClass firRegularClass;
                    FirConstructor firConstructor;
                    FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol((ConeClassLikeLookupTag) TypeConstructionUtilsKt.toLookupTag(classId), this.session);
                    FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
                    if (firRegularClassSymbol == null || (firRegularClass = (FirRegularClass) firRegularClassSymbol.getFir()) == null) {
                        return null;
                    }
                    FirTypeScope firTypeScopeScope = ScopeUtilsKt.scope(ScopeUtilsKt.defaultType(firRegularClass), this.session, new ScopeSession(), FakeOverrideTypeCalculator.DoNothing.INSTANCE, null);
                    if (firTypeScopeScope == null) {
                        throw new IllegalStateException(("Null scope for " + classId).toString());
                    }
                    FirConstructorSymbol firConstructorSymbol = (FirConstructorSymbol) CollectionsKt.singleOrNull((List) FirScopeKt.getDeclaredConstructors(firTypeScopeScope));
                    if (firConstructorSymbol == null || (firConstructor = (FirConstructor) firConstructorSymbol.getFir()) == null) {
                        throw new IllegalStateException(("No single constructor found for " + classId).toString());
                    }
                    List<FirValueParameter> valueParameters = firConstructor.getValueParameters();
                    LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(valueParameters, 10)), 16));
                    for (Object obj : valueParameters) {
                        linkedHashMap.put(((FirValueParameter) obj).getName(), obj);
                    }
                    return linkedHashMap;
                }
            });
            List<ProtoBuf.Annotation.Argument> argumentList = proto.getArgumentList();
            Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
            ArrayList arrayList = new ArrayList();
            for (ProtoBuf.Annotation.Argument argument : argumentList) {
                final Name name2 = NameResolverUtilKt.getName(nameResolver, argument.getNameId());
                ProtoBuf.Annotation.Argument.Value value = argument.getValue();
                Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                Pair pair = TuplesKt.to(name2, resolveValue(value, nameResolver, new Function0<ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer$createArgumentMapping$1$1$value$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final ConeKotlinType invoke() {
                        FirValueParameter firValueParameter;
                        FirTypeRef returnTypeRef;
                        Map mapCreateArgumentMapping$lambda$14$lambda$12 = AbstractAnnotationDeserializer.createArgumentMapping$lambda$14$lambda$12(lazy);
                        if (mapCreateArgumentMapping$lambda$14$lambda$12 == null || (firValueParameter = (FirValueParameter) mapCreateArgumentMapping$lambda$14$lambda$12.get(name2)) == null || (returnTypeRef = firValueParameter.getReturnTypeRef()) == null) {
                            return null;
                        }
                        return FirTypeUtilsKt.getConeType(returnTypeRef);
                    }
                }));
                if (pair != null) {
                    arrayList.add(pair);
                }
            }
            MapsKt.toMap(arrayList, firAnnotationArgumentMappingBuilder.getMapping());
        }
        return firAnnotationArgumentMappingBuilder.build();
    }
}
