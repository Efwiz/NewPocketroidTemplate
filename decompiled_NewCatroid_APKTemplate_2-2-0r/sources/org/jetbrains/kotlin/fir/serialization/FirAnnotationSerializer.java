package org.jetbrains.kotlin.fir.serialization;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.constant.AnnotationValue;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.constant.ErrorValue;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.serialization.constant.ConstValueProvider;
import org.jetbrains.kotlin.fir.serialization.constant.FirToConstantValueTransformer;
import org.jetbrains.kotlin.fir.serialization.constant.FirToConstantValueTransformerData;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: FirAnnotationSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000fJ*\u0010\u000b\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0016\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0013H\u0002J\u0019\u0010\u0016\u001a\u00020\u00172\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0015H\u0000¢\u0006\u0002\b\u0019R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirAnnotationSerializer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "stringTable", "Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "constValueProvider", "Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;)V", "getStringTable$fir_serialization", "()Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "serializeAnnotation", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "annotation", "Lorg/jetbrains/kotlin/constant/AnnotationValue;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "coneType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "argumentsMapping", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "valueProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value$Builder;", "constant", "valueProto$fir_serialization", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationSerializer {
    private final ConstValueProvider constValueProvider;
    private final FirSession session;
    private final FirElementAwareStringTable stringTable;

    public FirAnnotationSerializer(FirSession session, FirElementAwareStringTable stringTable, ConstValueProvider constValueProvider) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        this.session = session;
        this.stringTable = stringTable;
        this.constValueProvider = constValueProvider;
    }

    /* JADX INFO: renamed from: getStringTable$fir_serialization, reason: from getter */
    public final FirElementAwareStringTable getStringTable() {
        return this.stringTable;
    }

    public final ProtoBuf.Annotation serializeAnnotation(FirAnnotation annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        FirAnnotation firAnnotation = annotation;
        FirSession firSession = this.session;
        ConstValueProvider constValueProvider = this.constValueProvider;
        ConstantValue<?> constantValueFindConstantValueFor = constValueProvider != null ? constValueProvider.findConstantValueFor(firAnnotation) : null;
        if (!(constantValueFindConstantValueFor instanceof AnnotationValue)) {
            constantValueFindConstantValueFor = null;
        }
        AnnotationValue annotationValue = (AnnotationValue) constantValueFindConstantValueFor;
        if (annotationValue == null) {
            Object objAccept = firAnnotation.accept(FirToConstantValueTransformer.INSTANCE, new FirToConstantValueTransformerData(firSession, constValueProvider));
            annotationValue = (AnnotationValue) (objAccept instanceof AnnotationValue ? objAccept : null);
        }
        AnnotationValue annotationValue2 = annotationValue;
        if (annotationValue2 == null) {
            throw new IllegalStateException(("Cannot serialize annotation " + UtilsKt.render(annotation)).toString());
        }
        return serializeAnnotation(annotationValue2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ProtoBuf.Annotation serializeAnnotation(final ConeClassLikeType coneType, Map<Name, ? extends ConstantValue<?>> argumentsMapping) {
        ConeClassLikeLookupTag lookupTag;
        String message;
        ProtoBuf.Annotation.Builder builderNewBuilder = ProtoBuf.Annotation.newBuilder();
        if (coneType == null || (lookupTag = coneType.getLookupTag()) == null) {
            throw new IllegalStateException(new Function0<String>() { // from class: org.jetbrains.kotlin.fir.serialization.FirAnnotationSerializer$serializeAnnotation$1$lookupTag$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return "Annotation without proper lookup tag: " + coneType;
                }
            }.toString());
        }
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(lookupTag, this.session);
        builderNewBuilder.setId(symbol != null ? Integer.valueOf(this.stringTable.getFqNameIndex((FirClassLikeDeclaration) symbol.getFir())).intValue() : this.stringTable.getQualifiedClassNameIndex(lookupTag.getClassId()));
        for (Map.Entry<Name, ? extends ConstantValue<?>> entry : argumentsMapping.entrySet()) {
            Name key = entry.getKey();
            ConstantValue<?> value = entry.getValue();
            if (!(value instanceof ErrorValue)) {
                serializeAnnotation$lambda$1$addArgument(this, builderNewBuilder, value, key);
            } else if (!((Boolean) FirLanguageSettingsComponentKt.getLanguageVersionSettings(this.session).getFlag(AnalysisFlags.getMetadataCompilation())).booleanValue()) {
                ErrorValue.ErrorValueWithMessage errorValueWithMessage = value instanceof ErrorValue.ErrorValueWithMessage ? (ErrorValue.ErrorValueWithMessage) value : null;
                if (errorValueWithMessage == null || (message = errorValueWithMessage.getMessage()) == null) {
                    message = "Error value after conversion of expression of " + key + " argument";
                }
                throw new IllegalStateException(message.toString());
            }
        }
        ProtoBuf.Annotation annotationBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(annotationBuild, "build(...)");
        return annotationBuild;
    }

    private static final void serializeAnnotation$lambda$1$addArgument(FirAnnotationSerializer firAnnotationSerializer, ProtoBuf.Annotation.Builder builder, ConstantValue<?> constantValue, Name name2) {
        ProtoBuf.Annotation.Argument.Builder builderNewBuilder = ProtoBuf.Annotation.Argument.newBuilder();
        FirElementAwareStringTable firElementAwareStringTable = firAnnotationSerializer.stringTable;
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        builderNewBuilder.setNameId(firElementAwareStringTable.getStringIndex(strAsString));
        builderNewBuilder.setValue(firAnnotationSerializer.valueProto$fir_serialization(constantValue));
        builder.addArgument(builderNewBuilder);
    }

    public final ProtoBuf.Annotation.Argument.Value.Builder valueProto$fir_serialization(ConstantValue<?> constant) {
        Intrinsics.checkNotNullParameter(constant, "constant");
        ProtoBuf.Annotation.Argument.Value.Builder builderNewBuilder = ProtoBuf.Annotation.Argument.Value.newBuilder();
        FirAnnotationArgumentVisitor firAnnotationArgumentVisitor = FirAnnotationArgumentVisitor.INSTANCE;
        Intrinsics.checkNotNull(builderNewBuilder);
        constant.accept(firAnnotationArgumentVisitor, new FirAnnotationArgumentVisitorData(this, builderNewBuilder));
        Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "apply(...)");
        return builderNewBuilder;
    }

    public final ProtoBuf.Annotation serializeAnnotation(AnnotationValue annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        KotlinTypeMarker type = annotation.getValue().getType();
        if (!(type instanceof ConeClassLikeType)) {
            type = null;
        }
        return serializeAnnotation((ConeClassLikeType) type, annotation.getValue().getArgumentsMapping());
    }
}
