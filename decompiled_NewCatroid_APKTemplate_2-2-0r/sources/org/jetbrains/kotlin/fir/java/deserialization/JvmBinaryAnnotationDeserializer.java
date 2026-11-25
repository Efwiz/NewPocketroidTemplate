package org.jetbrains.kotlin.fir.java.deserialization;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationBuilder;
import org.jetbrains.kotlin.fir.java.JavaUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoaderKt;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.MemberSignature;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmFlags;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.utils.MetadataHelpersKt;

/* JADX INFO: compiled from: JvmBinaryAnnotationDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¼\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u001a\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020#H\u0002J \u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010'\u001a\u00020\u00182\b\b\u0002\u0010(\u001a\u00020\u0017H\u0002J6\u0010)\u001a\u0004\u0018\u00010\u00182\u0006\u0010*\u001a\u00020#2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\b\b\u0002\u0010/\u001a\u0002002\b\b\u0002\u00101\u001a\u00020\u0017H\u0002J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u0001H\u0016J4\u00105\u001a\u0004\u0018\u0001062\b\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J0\u0010=\u001a\b\u0012\u0004\u0012\u00020&0%2\b\u00107\u001a\u0004\u0018\u0001082\u0006\u0010>\u001a\u00020?2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J8\u0010@\u001a\b\u0012\u0004\u0012\u00020&0%2\b\u00107\u001a\u0004\u0018\u0001082\u0006\u0010A\u001a\u00020#2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0016J0\u0010B\u001a\b\u0012\u0004\u0012\u00020&0%2\b\u00107\u001a\u0004\u0018\u0001082\u0006\u0010C\u001a\u00020D2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J:\u0010E\u001a\b\u0012\u0004\u0012\u00020&0%2\b\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020:2\b\u0010F\u001a\u0004\u0018\u00010!2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J0\u0010G\u001a\b\u0012\u0004\u0012\u00020&0%2\b\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020:2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J0\u0010H\u001a\b\u0012\u0004\u0012\u00020&0%2\b\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020:2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J8\u0010I\u001a\b\u0012\u0004\u0012\u00020&0%2\b\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020:2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010J\u001a\u00020\u001fH\u0016J8\u0010K\u001a\b\u0012\u0004\u0012\u00020&0%2\b\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020:2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010L\u001a\u00020\u001fH\u0016J\u001e\u0010M\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010N\u001a\u00020O2\u0006\u0010+\u001a\u00020,H\u0016J\u001e\u0010P\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010Q\u001a\u00020R2\u0006\u0010+\u001a\u00020,H\u0016JR\u0010S\u001a\b\u0012\u0004\u0012\u00020&0%2\b\u00107\u001a\u0004\u0018\u0001082\u0006\u0010A\u001a\u00020#2\u0006\u0010T\u001a\u00020U2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\u0006\u0010V\u001a\u00020\u001fH\u0016R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0011\u001a\u0004\u0018\u00010\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0010\u001a\u0004\b\u0012\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0018\u0010\u0016\u001a\u00020\u0017*\u00020\u00188BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0019R$\u0010\u001a\u001a\u00020\u0017\"\u0004\b\u0000\u0010\u001b*\b\u0012\u0004\u0012\u0002H\u001b0\u001c8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001d¨\u0006W"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/deserialization/JvmBinaryAnnotationDeserializer;", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "kotlinBinaryClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "byteContent", "", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;[B)V", "annotationInfo", "Lorg/jetbrains/kotlin/fir/java/deserialization/MemberAnnotations;", "getAnnotationInfo", "()Lorg/jetbrains/kotlin/fir/java/deserialization/MemberAnnotations;", "annotationInfo$delegate", "Lkotlin/Lazy;", "annotationInfoForDefaultImpls", "getAnnotationInfoForDefaultImpls", "annotationInfoForDefaultImpls$delegate", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "isDelegated", "", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "(Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;)Z", "isSignedNumber", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "(Lorg/jetbrains/kotlin/types/ConstantValueKind;)Z", "computeJvmParameterIndexShift", "", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", CrashHianalyticsData.MESSAGE, "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "findJvmBinaryClassAndLoadMemberAnnotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "memberSignature", "searchInDefaultImpls", "getCallableSignature", "proto", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer$CallableKind;", "requireHasFieldFlagForField", "inheritAnnotationInfo", "", "parent", "loadAnnotationPropertyDefaultValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "propertyProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "expectedPropertyType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "loadConstructorAnnotations", "constructorProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor;", "loadExtensionReceiverParameterAnnotations", "callableProto", "loadFunctionAnnotations", "functionProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "loadPropertyAnnotations", "containingClassProto", "loadPropertyBackingFieldAnnotations", "loadPropertyDelegatedFieldAnnotations", "loadPropertyGetterAnnotations", "getterFlags", "loadPropertySetterAnnotations", "setterFlags", "loadTypeAnnotations", "typeProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "loadTypeParameterAnnotations", "typeParameterProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "loadValueParameterAnnotations", "valueParameterProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "parameterIndex", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmBinaryAnnotationDeserializer extends AbstractAnnotationDeserializer {

    /* JADX INFO: renamed from: annotationInfo$delegate, reason: from kotlin metadata */
    private final Lazy annotationInfo;

    /* JADX INFO: renamed from: annotationInfoForDefaultImpls$delegate, reason: from kotlin metadata */
    private final Lazy annotationInfoForDefaultImpls;
    private final FirSession session;

    /* JADX INFO: compiled from: JvmBinaryAnnotationDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AbstractAnnotationDeserializer.CallableKind.values().length];
            try {
                iArr[AbstractAnnotationDeserializer.CallableKind.PROPERTY_GETTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AbstractAnnotationDeserializer.CallableKind.PROPERTY_SETTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[AbstractAnnotationDeserializer.CallableKind.PROPERTY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final FirSession getSession() {
        return this.session;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmBinaryAnnotationDeserializer(FirSession session, final KotlinJvmBinaryClass kotlinBinaryClass, final KotlinClassFinder kotlinClassFinder, final byte[] bArr) {
        super(session, BuiltInSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(kotlinBinaryClass, "kotlinBinaryClass");
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        this.session = session;
        this.annotationInfo = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<MemberAnnotations>() { // from class: org.jetbrains.kotlin.fir.java.deserialization.JvmBinaryAnnotationDeserializer$annotationInfo$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MemberAnnotations invoke() {
                return JvmBinaryAnnotationDeserializerKt.loadMemberAnnotations(this.this$0.getSession(), kotlinBinaryClass, bArr, kotlinClassFinder);
            }
        });
        this.annotationInfoForDefaultImpls = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<MemberAnnotations>() { // from class: org.jetbrains.kotlin.fir.java.deserialization.JvmBinaryAnnotationDeserializer$annotationInfoForDefaultImpls$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MemberAnnotations invoke() {
                ClassId classIdCreateNestedClassId = kotlinBinaryClass.getClassId().createNestedClassId(Name.identifier(JvmAbi.DEFAULT_IMPLS_CLASS_NAME));
                Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
                KotlinClassFinder.Result resultFindKotlinClassOrContent = kotlinClassFinder.findKotlinClassOrContent(classIdCreateNestedClassId, MetadataHelpersKt.toMetadataVersion(FirLanguageSettingsComponentKt.getLanguageVersionSettings(this.getSession()).getLanguageVersion()));
                KotlinClassFinder.Result.KotlinClass kotlinClass = resultFindKotlinClassOrContent instanceof KotlinClassFinder.Result.KotlinClass ? (KotlinClassFinder.Result.KotlinClass) resultFindKotlinClassOrContent : null;
                if (kotlinClass == null) {
                    return null;
                }
                return JvmBinaryAnnotationDeserializerKt.loadMemberAnnotations(this.getSession(), kotlinClass.getKotlinJvmBinaryClass(), kotlinClass.getByteContent(), kotlinClassFinder);
            }
        });
    }

    private final MemberAnnotations getAnnotationInfo() {
        return (MemberAnnotations) this.annotationInfo.getValue();
    }

    private final MemberAnnotations getAnnotationInfoForDefaultImpls() {
        return (MemberAnnotations) this.annotationInfoForDefaultImpls.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public void inheritAnnotationInfo(AbstractAnnotationDeserializer parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (parent instanceof JvmBinaryAnnotationDeserializer) {
            getAnnotationInfo().getMemberAnnotations().putAll(((JvmBinaryAnnotationDeserializer) parent).getAnnotationInfo().getMemberAnnotations());
        }
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadTypeAnnotations(ProtoBuf.Type typeProto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(typeProto, "typeProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List listEmptyList = (List) typeProto.getExtension(JvmProtoBuf.typeAnnotation);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List<ProtoBuf.Annotation> list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ProtoBuf.Annotation annotation : list) {
            Intrinsics.checkNotNull(annotation);
            arrayList.add(AbstractAnnotationDeserializer.deserializeAnnotation$default(this, annotation, nameResolver, null, 4, null));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadTypeParameterAnnotations(ProtoBuf.TypeParameter typeParameterProto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(typeParameterProto, "typeParameterProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List listEmptyList = (List) typeParameterProto.getExtension(JvmProtoBuf.typeParameterAnnotation);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List<ProtoBuf.Annotation> list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ProtoBuf.Annotation annotation : list) {
            Intrinsics.checkNotNull(annotation);
            arrayList.add(AbstractAnnotationDeserializer.deserializeAnnotation$default(this, annotation, nameResolver, null, 4, null));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadConstructorAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Constructor constructorProto, NameResolver nameResolver, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(constructorProto, "constructorProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        MemberSignature callableSignature$default = getCallableSignature$default(this, constructorProto, nameResolver, typeTable, null, false, 24, null);
        return callableSignature$default == null ? CollectionsKt.emptyList() : findJvmBinaryClassAndLoadMemberAnnotations$default(this, callableSignature$default, false, 2, null);
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadFunctionAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Function functionProto, NameResolver nameResolver, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(functionProto, "functionProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        MemberSignature callableSignature$default = getCallableSignature$default(this, functionProto, nameResolver, typeTable, null, false, 24, null);
        return callableSignature$default == null ? CollectionsKt.emptyList() : findJvmBinaryClassAndLoadMemberAnnotations$default(this, callableSignature$default, false, 2, null);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0067  */
    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadPropertyAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, ProtoBuf.Class containingClassProto, NameResolver nameResolver, TypeTable typeTable) {
        Boolean bool;
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        MemberSignature propertySignature$default = AbstractBinaryClassAnnotationLoaderKt.getPropertySignature$default(propertyProto, nameResolver, typeTable, false, true, false, 40, null);
        if (propertySignature$default == null) {
            return CollectionsKt.emptyList();
        }
        boolean z = containingClassProto != null && Flags.CLASS_KIND.get(containingClassProto.getFlags()) == ProtoBuf.Class.Kind.INTERFACE;
        Integer num = null;
        if ((containingClassProto != null && containingClassProto.hasExtension(JvmProtoBuf.jvmClassFlags)) && containingClassProto != null) {
            num = (Integer) containingClassProto.getExtension(JvmProtoBuf.jvmClassFlags);
        }
        if (num != null) {
            bool = JvmFlags.INSTANCE.getIS_COMPILED_IN_COMPATIBILITY_MODE().get(num.intValue());
            if (bool == null) {
                bool = true;
            }
        } else {
            bool = true;
        }
        List<FirAnnotation> listFindJvmBinaryClassAndLoadMemberAnnotations = findJvmBinaryClassAndLoadMemberAnnotations(propertySignature$default, z && bool.booleanValue());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFindJvmBinaryClassAndLoadMemberAnnotations, 10));
        for (FirAnnotation firAnnotation : listFindJvmBinaryClassAndLoadMemberAnnotations) {
            FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
            firAnnotationBuilder.setAnnotationTypeRef(firAnnotation.getAnnotationTypeRef());
            firAnnotationBuilder.setArgumentMapping(firAnnotation.getArgumentMapping());
            firAnnotationBuilder.setUseSiteTarget(AnnotationUseSiteTarget.PROPERTY);
            arrayList.add(firAnnotationBuilder.mo6809build());
        }
        return arrayList;
    }

    private final boolean isDelegated(MemberSignature memberSignature) {
        return StringsKt.contains$default((CharSequence) memberSignature.getSignature(), (CharSequence) JvmAbi.DELEGATED_PROPERTY_NAME_SUFFIX, false, 2, (Object) null);
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadPropertyBackingFieldAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, NameResolver nameResolver, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        MemberSignature propertySignature$default = AbstractBinaryClassAnnotationLoaderKt.getPropertySignature$default(propertyProto, nameResolver, typeTable, true, false, false, 48, null);
        if (propertySignature$default == null) {
            return CollectionsKt.emptyList();
        }
        if (isDelegated(propertySignature$default)) {
            return CollectionsKt.emptyList();
        }
        List<FirAnnotation> listFindJvmBinaryClassAndLoadMemberAnnotations$default = findJvmBinaryClassAndLoadMemberAnnotations$default(this, propertySignature$default, false, 2, null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFindJvmBinaryClassAndLoadMemberAnnotations$default, 10));
        for (FirAnnotation firAnnotation : listFindJvmBinaryClassAndLoadMemberAnnotations$default) {
            FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
            firAnnotationBuilder.setAnnotationTypeRef(firAnnotation.getAnnotationTypeRef());
            firAnnotationBuilder.setArgumentMapping(firAnnotation.getArgumentMapping());
            firAnnotationBuilder.setUseSiteTarget(AnnotationUseSiteTarget.FIELD);
            arrayList.add(firAnnotationBuilder.mo6809build());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadPropertyDelegatedFieldAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, NameResolver nameResolver, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        MemberSignature propertySignature$default = AbstractBinaryClassAnnotationLoaderKt.getPropertySignature$default(propertyProto, nameResolver, typeTable, true, false, false, 48, null);
        if (propertySignature$default == null) {
            return CollectionsKt.emptyList();
        }
        if (!isDelegated(propertySignature$default)) {
            return CollectionsKt.emptyList();
        }
        List<FirAnnotation> listFindJvmBinaryClassAndLoadMemberAnnotations$default = findJvmBinaryClassAndLoadMemberAnnotations$default(this, propertySignature$default, false, 2, null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFindJvmBinaryClassAndLoadMemberAnnotations$default, 10));
        for (FirAnnotation firAnnotation : listFindJvmBinaryClassAndLoadMemberAnnotations$default) {
            FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
            firAnnotationBuilder.setAnnotationTypeRef(firAnnotation.getAnnotationTypeRef());
            firAnnotationBuilder.setArgumentMapping(firAnnotation.getArgumentMapping());
            firAnnotationBuilder.setUseSiteTarget(AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD);
            arrayList.add(firAnnotationBuilder.mo6809build());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadPropertyGetterAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, NameResolver nameResolver, TypeTable typeTable, int getterFlags) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        MemberSignature callableSignature$default = getCallableSignature$default(this, propertyProto, nameResolver, typeTable, AbstractAnnotationDeserializer.CallableKind.PROPERTY_GETTER, false, 16, null);
        return callableSignature$default == null ? CollectionsKt.emptyList() : findJvmBinaryClassAndLoadMemberAnnotations$default(this, callableSignature$default, false, 2, null);
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadPropertySetterAnnotations(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, NameResolver nameResolver, TypeTable typeTable, int setterFlags) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        MemberSignature callableSignature$default = getCallableSignature$default(this, propertyProto, nameResolver, typeTable, AbstractAnnotationDeserializer.CallableKind.PROPERTY_SETTER, false, 16, null);
        return callableSignature$default == null ? CollectionsKt.emptyList() : findJvmBinaryClassAndLoadMemberAnnotations$default(this, callableSignature$default, false, 2, null);
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadValueParameterAnnotations(DeserializedContainerSource containerSource, MessageLite callableProto, ProtoBuf.ValueParameter valueParameterProto, ProtoBuf.Class classProto, NameResolver nameResolver, TypeTable typeTable, AbstractAnnotationDeserializer.CallableKind kind, int parameterIndex) {
        Intrinsics.checkNotNullParameter(callableProto, "callableProto");
        Intrinsics.checkNotNullParameter(valueParameterProto, "valueParameterProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(kind, "kind");
        MemberSignature callableSignature$default = getCallableSignature$default(this, callableProto, nameResolver, typeTable, kind, false, 16, null);
        if (callableSignature$default == null) {
            return CollectionsKt.emptyList();
        }
        return findJvmBinaryClassAndLoadMemberAnnotations$default(this, MemberSignature.INSTANCE.fromMethodSignatureAndParameterIndex(callableSignature$default, parameterIndex + computeJvmParameterIndexShift(classProto, callableProto)), false, 2, null);
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public List<FirAnnotation> loadExtensionReceiverParameterAnnotations(DeserializedContainerSource containerSource, MessageLite callableProto, NameResolver nameResolver, TypeTable typeTable, AbstractAnnotationDeserializer.CallableKind kind) {
        Intrinsics.checkNotNullParameter(callableProto, "callableProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(kind, "kind");
        MemberSignature callableSignature$default = getCallableSignature$default(this, callableProto, nameResolver, typeTable, kind, false, 16, null);
        return callableSignature$default == null ? CollectionsKt.emptyList() : findJvmBinaryClassAndLoadMemberAnnotations$default(this, MemberSignature.INSTANCE.fromMethodSignatureAndParameterIndex(callableSignature$default, 0), false, 2, null);
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractAnnotationDeserializer
    public FirExpression loadAnnotationPropertyDefaultValue(DeserializedContainerSource containerSource, ProtoBuf.Property propertyProto, FirTypeRef expectedPropertyType, NameResolver nameResolver, TypeTable typeTable) {
        Intrinsics.checkNotNullParameter(propertyProto, "propertyProto");
        Intrinsics.checkNotNullParameter(expectedPropertyType, "expectedPropertyType");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        MemberSignature callableSignature$default = getCallableSignature$default(this, propertyProto, nameResolver, typeTable, AbstractAnnotationDeserializer.CallableKind.PROPERTY_GETTER, false, 16, null);
        if (callableSignature$default == null) {
            return null;
        }
        FirExpression firExpression = getAnnotationInfo().getAnnotationMethodsDefaultValues().get(callableSignature$default);
        if (!(firExpression instanceof FirConstExpression) || !ConeBuiltinTypeUtilsKt.isUnsignedType(FirTypeUtilsKt.getConeType(expectedPropertyType))) {
            return firExpression;
        }
        FirConstExpression firConstExpression = (FirConstExpression) firExpression;
        return isSignedNumber(firConstExpression.getKind()) ? JavaUtilsKt.createConstantIfAny(firConstExpression.getValue(), this.session, true) : firExpression;
    }

    private final <T> boolean isSignedNumber(ConstantValueKind<T> constantValueKind) {
        return (constantValueKind instanceof ConstantValueKind.Byte) || (constantValueKind instanceof ConstantValueKind.Short) || (constantValueKind instanceof ConstantValueKind.Int) || (constantValueKind instanceof ConstantValueKind.Long);
    }

    private final int computeJvmParameterIndexShift(ProtoBuf.Class classProto, MessageLite message) {
        if (message instanceof ProtoBuf.Function) {
            if (ProtoTypeTableUtilKt.hasReceiver((ProtoBuf.Function) message)) {
                return 1;
            }
        } else if (message instanceof ProtoBuf.Property) {
            if (ProtoTypeTableUtilKt.hasReceiver((ProtoBuf.Property) message)) {
                return 1;
            }
        } else if (message instanceof ProtoBuf.Constructor) {
            Flags.FlagField<ProtoBuf.Class.Kind> flagField = Flags.CLASS_KIND;
            Intrinsics.checkNotNull(classProto);
            ProtoBuf.Class.Kind kind = flagField.get(classProto.getFlags());
            if (kind == null) {
                kind = ProtoBuf.Class.Kind.CLASS;
            }
            Boolean bool = Flags.IS_INNER.get(classProto.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            boolean zBooleanValue = bool.booleanValue();
            if (kind == ProtoBuf.Class.Kind.ENUM_CLASS) {
                return 2;
            }
            if (zBooleanValue) {
                return 1;
            }
        } else {
            throw new UnsupportedOperationException("Unsupported message: " + message.getClass());
        }
        return 0;
    }

    static /* synthetic */ MemberSignature getCallableSignature$default(JvmBinaryAnnotationDeserializer jvmBinaryAnnotationDeserializer, MessageLite messageLite, NameResolver nameResolver, TypeTable typeTable, AbstractAnnotationDeserializer.CallableKind callableKind, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            callableKind = AbstractAnnotationDeserializer.CallableKind.OTHERS;
        }
        AbstractAnnotationDeserializer.CallableKind callableKind2 = callableKind;
        if ((i & 16) != 0) {
            z = false;
        }
        return jvmBinaryAnnotationDeserializer.getCallableSignature(messageLite, nameResolver, typeTable, callableKind2, z);
    }

    private final MemberSignature getCallableSignature(MessageLite proto, NameResolver nameResolver, TypeTable typeTable, AbstractAnnotationDeserializer.CallableKind kind, boolean requireHasFieldFlagForField) {
        if (proto instanceof ProtoBuf.Constructor) {
            MemberSignature.Companion companion = MemberSignature.INSTANCE;
            JvmMemberSignature.Method jvmConstructorSignature = JvmProtoBufUtil.INSTANCE.getJvmConstructorSignature((ProtoBuf.Constructor) proto, nameResolver, typeTable);
            if (jvmConstructorSignature == null) {
                return null;
            }
            return companion.fromJvmMemberSignature(jvmConstructorSignature);
        }
        if (proto instanceof ProtoBuf.Function) {
            JvmMemberSignature.Method jvmMethodSignature = JvmProtoBufUtil.INSTANCE.getJvmMethodSignature((ProtoBuf.Function) proto, nameResolver, typeTable);
            if (jvmMethodSignature == null) {
                return null;
            }
            return MemberSignature.INSTANCE.fromJvmMemberSignature(jvmMethodSignature);
        }
        if (!(proto instanceof ProtoBuf.Property)) {
            return null;
        }
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, JvmProtoBuf.JvmPropertySignature> propertySignature = JvmProtoBuf.propertySignature;
        Intrinsics.checkNotNullExpressionValue(propertySignature, "propertySignature");
        JvmProtoBuf.JvmPropertySignature jvmPropertySignature = (JvmProtoBuf.JvmPropertySignature) ProtoBufUtilKt.getExtensionOrNull((GeneratedMessageLite.ExtendableMessage) proto, propertySignature);
        if (jvmPropertySignature == null) {
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        if (i == 1) {
            if (!jvmPropertySignature.hasGetter()) {
                return null;
            }
            MemberSignature.Companion companion2 = MemberSignature.INSTANCE;
            JvmProtoBuf.JvmMethodSignature getter = jvmPropertySignature.getGetter();
            Intrinsics.checkNotNullExpressionValue(getter, "getGetter(...)");
            return companion2.fromMethod(nameResolver, getter);
        }
        if (i != 2) {
            if (i != 3) {
                return null;
            }
            return AbstractBinaryClassAnnotationLoaderKt.getPropertySignature$default((ProtoBuf.Property) proto, nameResolver, typeTable, true, false, requireHasFieldFlagForField, 16, null);
        }
        if (!jvmPropertySignature.hasSetter()) {
            return null;
        }
        MemberSignature.Companion companion3 = MemberSignature.INSTANCE;
        JvmProtoBuf.JvmMethodSignature setter = jvmPropertySignature.getSetter();
        Intrinsics.checkNotNullExpressionValue(setter, "getSetter(...)");
        return companion3.fromMethod(nameResolver, setter);
    }

    static /* synthetic */ List findJvmBinaryClassAndLoadMemberAnnotations$default(JvmBinaryAnnotationDeserializer jvmBinaryAnnotationDeserializer, MemberSignature memberSignature, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return jvmBinaryAnnotationDeserializer.findJvmBinaryClassAndLoadMemberAnnotations(memberSignature, z);
    }

    private final List<FirAnnotation> findJvmBinaryClassAndLoadMemberAnnotations(MemberSignature memberSignature, boolean searchInDefaultImpls) {
        MemberAnnotations annotationInfo;
        if (searchInDefaultImpls) {
            annotationInfo = getAnnotationInfoForDefaultImpls();
            if (annotationInfo == null) {
                return CollectionsKt.emptyList();
            }
        } else {
            annotationInfo = getAnnotationInfo();
        }
        List<FirAnnotation> list = annotationInfo.getMemberAnnotations().get(memberSignature);
        return list == null ? CollectionsKt.emptyList() : list;
    }
}
