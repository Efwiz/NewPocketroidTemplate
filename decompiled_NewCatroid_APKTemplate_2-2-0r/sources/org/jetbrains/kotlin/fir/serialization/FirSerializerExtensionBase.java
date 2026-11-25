package org.jetbrains.kotlin.fir.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.serialization.constant.ConstValueProvider;
import org.jetbrains.kotlin.fir.serialization.constant.FirToConstantValueTransformer;
import org.jetbrains.kotlin.fir.serialization.constant.FirToConstantValueTransformerData;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;

/* JADX INFO: compiled from: FirSerializerExtensionBase.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Æ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J \u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0019\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\u001cH\u0016J*\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u000f\u001a\u00020 2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u000f\u001a\u00020$H\u0016J*\u0010%\u001a\u00020\f2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u000f\u001a\u00020(2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u001e\u0010)\u001a\u00020\f2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+2\u0006\u0010\u000f\u001a\u00020-H\u0016J\u0018\u0010.\u001a\u00020\f2\u0006\u0010/\u001a\u0002002\u0006\u0010\u000f\u001a\u000201H\u0016J\u0018\u00102\u001a\u00020\f2\u0006\u00103\u001a\u0002042\u0006\u0010\u000f\u001a\u000205H\u0016Jm\u00106\u001a\u00020\f\"\u000e\b\u0000\u00107*\b\u0012\u0004\u0012\u0002H708\"\u0014\b\u0001\u00109*\u000e\u0012\u0004\u0012\u0002H7\u0012\u0004\u0012\u0002H90:\"\u0004\b\u0002\u0010;*\u000e\u0012\u0004\u0012\u0002H7\u0012\u0004\u0012\u0002H90:2\u0018\u0010<\u001a\u0014\u0012\u0004\u0012\u0002H7\u0012\n\u0012\b\u0012\u0004\u0012\u0002H;0+0=2\b\u0010>\u001a\u0004\u0018\u0001H;H\u0002¢\u0006\u0002\u0010?J\u001a\u0010@\u001a\b\u0012\u0004\u0012\u00020,0+*\u00020A2\u0006\u0010B\u001a\u00020CH\u0002Jh\u0010D\u001a\u00020\f\"\u000e\b\u0000\u00107*\b\u0012\u0004\u0012\u0002H708\"\u0014\b\u0001\u00109*\u000e\u0012\u0004\u0012\u0002H7\u0012\u0004\u0012\u0002H90:*\b\u0012\u0004\u0012\u00020,0+2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u0002H7\u0012\u0004\u0012\u0002H90:2\u001a\u0010<\u001a\u0016\u0012\u0004\u0012\u0002H7\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0+\u0018\u00010=H\u0002Jb\u0010D\u001a\u00020\f\"\u000e\b\u0000\u00107*\b\u0012\u0004\u0012\u0002H708\"\u0014\b\u0001\u00109*\u000e\u0012\u0004\u0012\u0002H7\u0012\u0004\u0012\u0002H90:*\u00020A2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u0002H7\u0012\u0004\u0012\u0002H90:2\u001a\u0010<\u001a\u0016\u0012\u0004\u0012\u0002H7\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0+\u0018\u00010=H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirSerializerExtensionBase;", "Lorg/jetbrains/kotlin/fir/serialization/FirSerializerExtension;", ConfigConstants.CONFIG_PROTOCOL_SECTION, "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "getProtocol", "()Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "stringTable", "Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareSerializableStringTable;", "getStringTable", "()Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareSerializableStringTable;", "serializeClass", "", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "childSerializer", "Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;", "serializeConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", "serializeEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry$Builder;", "serializeFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "serializePackage", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package$Builder;", "serializeProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "serializeTypeAnnotations", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", "serializeTypeParameter", "typeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter$Builder;", "serializeValueParameter", "parameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter$Builder;", "addExtensionOrNull", "MessageType", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableMessage;", "BuilderType", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableBuilder;", "Type", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;", "value", "(Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableBuilder;Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;Ljava/lang/Object;)V", "allRequiredAnnotations", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "serializeAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSerializerExtensionBase extends FirSerializerExtension {
    private final SerializerExtensionProtocol protocol;
    private final FirElementAwareSerializableStringTable stringTable;

    /* JADX INFO: compiled from: FirSerializerExtensionBase.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AnnotationUseSiteTarget.values().length];
            try {
                iArr[AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public /* bridge */ /* synthetic */ FirElementAwareStringTable getStringTable() {
        return this.stringTable;
    }

    public final SerializerExtensionProtocol getProtocol() {
        return this.protocol;
    }

    public FirSerializerExtensionBase(SerializerExtensionProtocol protocol) {
        Intrinsics.checkNotNullParameter(protocol, "protocol");
        this.protocol = protocol;
        this.stringTable = new FirElementAwareSerializableStringTable();
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public final FirElementAwareSerializableStringTable getStringTable() {
        return this.stringTable;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializePackage(FqName packageFqName, ProtoBuf.Package.Builder proto) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(proto, "proto");
        proto.setExtension(this.protocol.getPackageFqName(), Integer.valueOf(this.stringTable.getPackageFqNameIndex(packageFqName)));
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeClass(FirClass klass, ProtoBuf.Class.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        serializeAnnotations(klass, proto, this.protocol.getClassAnnotation());
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeConstructor(FirConstructor constructor, ProtoBuf.Constructor.Builder proto, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        serializeAnnotations(constructor, proto, this.protocol.getConstructorAnnotation());
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeFunction(FirFunction function, ProtoBuf.Function.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        ProtoBuf.Function.Builder builder = proto;
        serializeAnnotations(function, builder, this.protocol.getFunctionAnnotation());
        FirReceiverParameter receiverParameter = function.getReceiverParameter();
        if (receiverParameter != null) {
            serializeAnnotations(receiverParameter, builder, this.protocol.getFunctionExtensionReceiverAnnotation());
        }
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeProperty(FirProperty property, ProtoBuf.Property.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        FirExpression initializer;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        FirBackingField backingField = property.getBackingField();
        List<FirAnnotation> listAllRequiredAnnotations = backingField != null ? allRequiredAnnotations(backingField, getSession()) : null;
        if (listAllRequiredAnnotations == null) {
            listAllRequiredAnnotations = CollectionsKt.emptyList();
        }
        for (FirAnnotation firAnnotation : listAllRequiredAnnotations) {
            AnnotationUseSiteTarget useSiteTarget = firAnnotation.getUseSiteTarget();
            ((useSiteTarget == null ? -1 : WhenMappings.$EnumSwitchMapping$0[useSiteTarget.ordinal()]) == 1 ? arrayList2 : arrayList).add(firAnnotation);
        }
        ProtoBuf.Property.Builder builder = proto;
        serializeAnnotations(allRequiredAnnotations(property, getSession()), builder, this.protocol.getPropertyAnnotation());
        serializeAnnotations(arrayList, builder, this.protocol.getPropertyBackingFieldAnnotation());
        serializeAnnotations(arrayList2, builder, this.protocol.getPropertyDelegatedFieldAnnotation());
        FirPropertyAccessor getter = property.getGetter();
        if (getter != null) {
            serializeAnnotations(getter, builder, this.protocol.getPropertyGetterAnnotation());
        }
        FirPropertyAccessor setter = property.getSetter();
        if (setter != null) {
            serializeAnnotations(setter, builder, this.protocol.getPropertySetterAnnotation());
        }
        FirReceiverParameter receiverParameter = property.getReceiverParameter();
        if (receiverParameter != null) {
            serializeAnnotations(receiverParameter, builder, this.protocol.getPropertyExtensionReceiverAnnotation());
        }
        if (Flags.HAS_CONSTANT.get(proto.getFlags()).booleanValue() && (initializer = property.getInitializer()) != null) {
            FirSession session = getSession();
            ConstValueProvider constValueProvider = getConstValueProvider();
            ConstantValue<?> constantValueFindConstantValueFor = constValueProvider != null ? constValueProvider.findConstantValueFor(initializer) : null;
            if (!(constantValueFindConstantValueFor instanceof ConstantValue)) {
                constantValueFindConstantValueFor = null;
            }
            if (constantValueFindConstantValueFor == null) {
                Object objAccept = initializer.accept(FirToConstantValueTransformer.INSTANCE, new FirToConstantValueTransformerData(session, constValueProvider));
                constantValueFindConstantValueFor = (ConstantValue) (objAccept instanceof ConstantValue ? objAccept : null);
            }
            if (constantValueFindConstantValueFor != null) {
                proto.setExtension(this.protocol.getCompileTimeValue(), getAnnotationSerializer().valueProto$fir_serialization(constantValueFindConstantValueFor).build());
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeEnumEntry(FirEnumEntry enumEntry, ProtoBuf.EnumEntry.Builder proto) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(proto, "proto");
        serializeAnnotations(enumEntry, proto, this.protocol.getEnumEntryAnnotation());
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeValueParameter(FirValueParameter parameter, ProtoBuf.ValueParameter.Builder proto) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
        serializeAnnotations(parameter, proto, this.protocol.getParameterAnnotation());
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeTypeAnnotations(List<? extends FirAnnotation> annotations, ProtoBuf.Type.Builder proto) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(proto, "proto");
        serializeAnnotations(annotations, proto, this.protocol.getTypeAnnotation());
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeTypeParameter(FirTypeParameter typeParameter, ProtoBuf.TypeParameter.Builder proto) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
        serializeAnnotations(typeParameter, proto, this.protocol.getTypeParameterAnnotation());
    }

    private final <MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>> void serializeAnnotations(FirAnnotationContainer firAnnotationContainer, GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType> extendableBuilder, GeneratedMessageLite.GeneratedExtension<MessageType, List<ProtoBuf.Annotation>> generatedExtension) {
        if (generatedExtension == null) {
            return;
        }
        serializeAnnotations(allRequiredAnnotations(firAnnotationContainer, getSession()), extendableBuilder, generatedExtension);
    }

    private final <MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>> void serializeAnnotations(List<? extends FirAnnotation> list, GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType> extendableBuilder, GeneratedMessageLite.GeneratedExtension<MessageType, List<ProtoBuf.Annotation>> generatedExtension) {
        if (generatedExtension == null) {
            return;
        }
        Iterator<? extends FirAnnotation> it = list.iterator();
        while (it.hasNext()) {
            addExtensionOrNull(extendableBuilder, generatedExtension, getAnnotationSerializer().serializeAnnotation(it.next()));
        }
    }

    private final <MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>, Type> void addExtensionOrNull(GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType> extendableBuilder, GeneratedMessageLite.GeneratedExtension<MessageType, List<Type>> generatedExtension, Type type) {
        if (type != null) {
            extendableBuilder.addExtension(generatedExtension, type);
        }
    }

    private final List<FirAnnotation> allRequiredAnnotations(FirAnnotationContainer firAnnotationContainer, FirSession firSession) {
        List<FirAnnotation> listNonSourceAnnotations = FirAnnotationUtilsKt.nonSourceAnnotations(firAnnotationContainer, firSession);
        FirAdditionalMetadataAnnotationsProvider additionalAnnotationsProvider = getAdditionalAnnotationsProvider();
        return (!(firAnnotationContainer instanceof FirDeclaration) || additionalAnnotationsProvider == null) ? listNonSourceAnnotations : CollectionsKt.plus((Collection) listNonSourceAnnotations, (Iterable) additionalAnnotationsProvider.findGeneratedAnnotationsFor((FirDeclaration) firAnnotationContainer));
    }
}
