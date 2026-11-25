package org.jetbrains.kotlin.fir.serialization;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.serialization.constant.ConstValueProvider;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirSerializerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ö\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020#2\u0006\u0010 \u001a\u00020!J1\u0010$\u001a\u0002H%\"\u0004\b\u0000\u0010%2\u0006\u0010&\u001a\u00020'2\u000e\b\u0004\u0010(\u001a\b\u0012\u0004\u0012\u0002H%0)H\u0080\bø\u0001\u0000¢\u0006\u0004\b*\u0010+J(\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016J \u00106\u001a\u00020-2\u0006\u00107\u001a\u0002082\u0006\u00100\u001a\u0002092\u0006\u00104\u001a\u000205H\u0016J\u0018\u0010:\u001a\u00020-2\u0006\u0010;\u001a\u00020<2\u0006\u00100\u001a\u00020=H\u0016J\u0018\u0010>\u001a\u00020-2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020BH\u0016J \u0010C\u001a\u00020-2\u0006\u0010?\u001a\u00020D2\u0006\u0010E\u001a\u00020B2\u0006\u0010F\u001a\u00020BH\u0016J*\u0010G\u001a\u00020-2\u0006\u0010H\u001a\u00020I2\u0006\u00100\u001a\u00020J2\b\u00102\u001a\u0004\u0018\u0001032\u0006\u00104\u001a\u000205H\u0016J\u0018\u0010K\u001a\u00020-2\u0006\u0010L\u001a\u00020M2\u0006\u00100\u001a\u00020NH\u0016J*\u0010O\u001a\u00020-2\u0006\u0010P\u001a\u00020Q2\u0006\u00100\u001a\u00020R2\b\u00102\u001a\u0004\u0018\u0001032\u0006\u00104\u001a\u000205H\u0016J\u0018\u0010S\u001a\u00020-2\u0006\u0010T\u001a\u00020U2\u0006\u00100\u001a\u00020VH\u0016J\u001e\u0010W\u001a\u00020-2\f\u0010X\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u00100\u001a\u00020BH\u0016J\u0018\u0010Y\u001a\u00020-2\u0006\u0010Z\u001a\u00020[2\u0006\u00100\u001a\u00020\\H\u0016J\u0018\u0010]\u001a\u00020-2\u0006\u0010^\u001a\u00020_2\u0006\u00100\u001a\u00020`H\u0016J\b\u0010a\u001a\u00020#H\u0016J\b\u0010b\u001a\u00020#H\u0016R\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\u0007\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u0014\u0010\r\u001a\u0004\u0018\u00010\u000eX¤\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006c"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirSerializerExtension;", "", "()V", "additionalAnnotationsProvider", "Lorg/jetbrains/kotlin/fir/serialization/FirAdditionalMetadataAnnotationsProvider;", "getAdditionalAnnotationsProvider", "()Lorg/jetbrains/kotlin/fir/serialization/FirAdditionalMetadataAnnotationsProvider;", "annotationSerializer", "Lorg/jetbrains/kotlin/fir/serialization/FirAnnotationSerializer;", "getAnnotationSerializer", "()Lorg/jetbrains/kotlin/fir/serialization/FirAnnotationSerializer;", "annotationSerializer$delegate", "Lkotlin/Lazy;", "constValueProvider", "Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "getConstValueProvider", "()Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "stringTable", "Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "getStringTable", "()Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "getAnnotationsGeneratedByPlugins", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "hasAdditionalAnnotations", "", "processFile", ExifInterface.GPS_DIRECTION_TRUE, "firFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "action", "Lkotlin/Function0;", "processFile$fir_serialization", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "serializeClass", "", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "childSerializer", "Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;", "serializeConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", "serializeEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry$Builder;", "serializeErrorType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeErrorType;", "builder", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", "serializeFlexibleType", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "lowerProto", "upperProto", "serializeFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "serializePackage", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package$Builder;", "serializeProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "serializeTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias$Builder;", "serializeTypeAnnotations", "annotations", "serializeTypeParameter", "typeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter$Builder;", "serializeValueParameter", "parameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter$Builder;", "shouldUseNormalizedVisibility", "shouldUseTypeTable", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSerializerExtension {

    /* JADX INFO: renamed from: annotationSerializer$delegate, reason: from kotlin metadata */
    private final Lazy annotationSerializer = LazyKt.lazy(new Function0<FirAnnotationSerializer>() { // from class: org.jetbrains.kotlin.fir.serialization.FirSerializerExtension$annotationSerializer$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final FirAnnotationSerializer invoke() {
            return new FirAnnotationSerializer(this.this$0.getSession(), this.this$0.getStringTable(), this.this$0.getConstValueProvider());
        }
    });

    protected abstract FirAdditionalMetadataAnnotationsProvider getAdditionalAnnotationsProvider();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract ConstValueProvider getConstValueProvider();

    public abstract BinaryVersion getMetadataVersion();

    public abstract FirSession getSession();

    public abstract FirElementAwareStringTable getStringTable();

    public void serializeClass(FirClass klass, ProtoBuf.Class.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
    }

    public void serializeConstructor(FirConstructor constructor, ProtoBuf.Constructor.Builder proto, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
    }

    public void serializeEnumEntry(FirEnumEntry enumEntry, ProtoBuf.EnumEntry.Builder proto) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public void serializeFlexibleType(ConeFlexibleType type, ProtoBuf.Type.Builder lowerProto, ProtoBuf.Type.Builder upperProto) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(lowerProto, "lowerProto");
        Intrinsics.checkNotNullParameter(upperProto, "upperProto");
    }

    public void serializeFunction(FirFunction function, ProtoBuf.Function.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
    }

    public void serializePackage(FqName packageFqName, ProtoBuf.Package.Builder proto) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public void serializeProperty(FirProperty property, ProtoBuf.Property.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
    }

    public void serializeTypeAnnotations(List<? extends FirAnnotation> annotations, ProtoBuf.Type.Builder proto) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public void serializeTypeParameter(FirTypeParameter typeParameter, ProtoBuf.TypeParameter.Builder proto) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public void serializeValueParameter(FirValueParameter parameter, ProtoBuf.ValueParameter.Builder proto) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
    }

    public boolean shouldUseNormalizedVisibility() {
        return false;
    }

    /* JADX INFO: renamed from: shouldUseTypeTable */
    public boolean getUseTypeTable() {
        return false;
    }

    public final FirAnnotationSerializer getAnnotationSerializer() {
        return (FirAnnotationSerializer) this.annotationSerializer.getValue();
    }

    public final <T> T processFile$fir_serialization(FirFile firFile, Function0<? extends T> action) {
        Intrinsics.checkNotNullParameter(firFile, "firFile");
        Intrinsics.checkNotNullParameter(action, "action");
        ConstValueProvider constValueProvider = getConstValueProvider();
        FirFile processingFirFile = constValueProvider != null ? constValueProvider.getProcessingFirFile() : null;
        ConstValueProvider constValueProvider2 = getConstValueProvider();
        if (constValueProvider2 != null) {
            constValueProvider2.setProcessingFirFile(firFile);
        }
        try {
            return action.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            ConstValueProvider constValueProvider3 = getConstValueProvider();
            if (constValueProvider3 != null) {
                constValueProvider3.setProcessingFirFile(processingFirFile);
            }
            InlineMarker.finallyEnd(1);
        }
    }

    public void serializeTypeAlias(FirTypeAlias typeAlias, ProtoBuf.TypeAlias.Builder proto) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Iterator<FirAnnotation> it = FirAnnotationUtilsKt.nonSourceAnnotations(typeAlias, getSession()).iterator();
        while (it.hasNext()) {
            proto.addAnnotation(getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    public final boolean hasAdditionalAnnotations(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        FirAdditionalMetadataAnnotationsProvider additionalAnnotationsProvider = getAdditionalAnnotationsProvider();
        if (additionalAnnotationsProvider != null) {
            return additionalAnnotationsProvider.hasGeneratedAnnotationsFor(declaration);
        }
        return false;
    }

    public final List<FirAnnotation> getAnnotationsGeneratedByPlugins(FirDeclaration declaration) {
        List<FirAnnotation> listFindGeneratedAnnotationsFor;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        FirAdditionalMetadataAnnotationsProvider additionalAnnotationsProvider = getAdditionalAnnotationsProvider();
        return (additionalAnnotationsProvider == null || (listFindGeneratedAnnotationsFor = additionalAnnotationsProvider.findGeneratedAnnotationsFor(declaration)) == null) ? CollectionsKt.emptyList() : listFindGeneratedAnnotationsFor;
    }

    public void serializeErrorType(ConeErrorType type, ProtoBuf.Type.Builder builder) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(builder, "builder");
        throw new IllegalStateException(("Cannot serialize error type: " + type).toString());
    }
}
