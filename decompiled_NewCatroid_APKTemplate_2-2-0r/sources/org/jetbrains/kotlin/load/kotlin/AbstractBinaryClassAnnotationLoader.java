package org.jetbrains.kotlin.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.SpecialJvmAnnotations;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader.AnnotationsContainer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ClassMapperLite;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.serialization.deserialization.AnnotatedCallableKind;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;

/* JADX INFO: compiled from: AbstractBinaryClassAnnotationLoader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ä\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 T*\b\b\u0000\u0010\u0001*\u00020\u0002*\u000e\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00010\u00042\b\u0012\u0004\u0012\u0002H\u00010\u0005:\u0003STUB\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002JM\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u001a2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u001a2\b\b\u0002\u0010\u001d\u001a\u00020\u001aH\u0002¢\u0006\u0002\u0010\u001eJ\u001c\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010!\u001a\u0004\u0018\u00010 H\u0004J\u0015\u0010\"\u001a\u00028\u00012\u0006\u0010#\u001a\u00020 H$¢\u0006\u0002\u0010$J\u0012\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010'\u001a\u00020 H\u0014J4\u0010(\u001a\u0004\u0018\u00010\u00182\u0006\u0010)\u001a\u00020\u00142\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\b\b\u0002\u00100\u001a\u00020\u001aH\u0004J\u0010\u00101\u001a\u00020\u001a2\u0006\u00102\u001a\u000203H\u0004J\u001d\u00104\u001a\u00028\u00002\u0006\u0010)\u001a\u0002052\u0006\u0010*\u001a\u00020+H&¢\u0006\u0002\u00106J(\u00104\u001a\u0004\u0018\u0001072\u0006\u00108\u001a\u0002032\u0006\u00109\u001a\u00020:2\f\u0010;\u001a\b\u0012\u0004\u0012\u00028\u00000<H$J(\u0010=\u001a\u0004\u0018\u0001072\u0006\u00108\u001a\u0002032\u0006\u00109\u001a\u00020:2\f\u0010;\u001a\b\u0012\u0004\u0012\u00028\u00000<H\u0004J&\u0010>\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010)\u001a\u00020\u00142\u0006\u0010.\u001a\u00020/H\u0016J\u0016\u0010?\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\u0011\u001a\u00020@H\u0016J\u001e\u0010A\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010)\u001a\u00020BH\u0016J&\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010)\u001a\u00020\u00142\u0006\u0010.\u001a\u00020/H\u0016J&\u0010D\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010)\u001a\u00020E2\u0006\u0010F\u001a\u00020GH\u0002J\u001e\u0010H\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010)\u001a\u00020EH\u0016J\u001e\u0010I\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010)\u001a\u00020EH\u0016J\u001e\u0010J\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010)\u001a\u00020K2\u0006\u0010*\u001a\u00020+H\u0016J\u001e\u0010L\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010)\u001a\u00020M2\u0006\u0010*\u001a\u00020+H\u0016J6\u0010N\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010O\u001a\u00020\u00142\u0006\u0010.\u001a\u00020/2\u0006\u0010P\u001a\u00020\u00102\u0006\u0010)\u001a\u00020QH\u0016J\u000e\u0010R\u001a\u0004\u0018\u00010 *\u00020@H\u0002R\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006V"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationLoader;", "A", "", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationLoader$AnnotationsContainer;", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationLoader;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;)V", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "getJvmMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "getKotlinClassFinder", "()Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "computeJvmParameterIndexShift", "", "container", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", CrashHianalyticsData.MESSAGE, "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "findClassAndLoadMemberAnnotations", "", "signature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "property", "", "field", "isConst", "isMovedFromInterfaceCompanion", "(Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;ZZLjava/lang/Boolean;Z)Ljava/util/List;", "findClassWithAnnotationsAndInitializers", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "specialCase", "getAnnotationsContainer", "binaryClass", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;)Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationLoader$AnnotationsContainer;", "getCachedFileContent", "", "kotlinClass", "getCallableSignature", "proto", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotatedCallableKind;", "requireHasFieldFlagForField", "isImplicitRepeatableContainer", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "loadAnnotation", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "annotationClassId", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "result", "", "loadAnnotationIfNotSpecial", "loadCallableAnnotations", "loadClassAnnotations", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "loadEnumEntryAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry;", "loadExtensionReceiverParameterAnnotations", "loadPropertyAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationLoader$PropertyRelatedElement;", "loadPropertyBackingFieldAnnotations", "loadPropertyDelegateFieldAnnotations", "loadTypeAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "loadTypeParameterAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "loadValueParameterAnnotations", "callableProto", "parameterIndex", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "toBinaryClass", "AnnotationsContainer", "Companion", "PropertyRelatedElement", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractBinaryClassAnnotationLoader<A, S extends AnnotationsContainer<? extends A>> implements AnnotationLoader<A> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final KotlinClassFinder kotlinClassFinder;

    /* JADX INFO: compiled from: AbstractBinaryClassAnnotationLoader.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\b&\u0018\u0000*\u0006\b\u0002\u0010\u0001 \u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003R$\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\u00070\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationLoader$AnnotationsContainer;", "A", "", "()V", "memberAnnotations", "", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "", "getMemberAnnotations", "()Ljava/util/Map;", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class AnnotationsContainer<A> {
        public abstract Map<MemberSignature, List<A>> getMemberAnnotations();
    }

    /* JADX INFO: compiled from: AbstractBinaryClassAnnotationLoader.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationLoader$PropertyRelatedElement;", "", "(Ljava/lang/String;I)V", "PROPERTY", "BACKING_FIELD", "DELEGATE_FIELD", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum PropertyRelatedElement {
        PROPERTY,
        BACKING_FIELD,
        DELEGATE_FIELD;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<PropertyRelatedElement> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: AbstractBinaryClassAnnotationLoader.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AnnotatedCallableKind.values().length];
            try {
                iArr[AnnotatedCallableKind.PROPERTY_GETTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AnnotatedCallableKind.PROPERTY_SETTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[AnnotatedCallableKind.PROPERTY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected abstract S getAnnotationsContainer(KotlinJvmBinaryClass binaryClass);

    protected byte[] getCachedFileContent(KotlinJvmBinaryClass kotlinClass) {
        Intrinsics.checkNotNullParameter(kotlinClass, "kotlinClass");
        return null;
    }

    public abstract JvmMetadataVersion getJvmMetadataVersion();

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public abstract A loadAnnotation(ProtoBuf.Annotation proto, NameResolver nameResolver);

    protected abstract KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotation(ClassId annotationClassId, SourceElement source, List<A> result);

    public AbstractBinaryClassAnnotationLoader(KotlinClassFinder kotlinClassFinder) {
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        this.kotlinClassFinder = kotlinClassFinder;
    }

    protected final KotlinClassFinder getKotlinClassFinder() {
        return this.kotlinClassFinder;
    }

    protected final KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotationIfNotSpecial(ClassId annotationClassId, SourceElement source, List<A> result) {
        Intrinsics.checkNotNullParameter(annotationClassId, "annotationClassId");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(result, "result");
        if (SpecialJvmAnnotations.INSTANCE.getSPECIAL_ANNOTATIONS().contains(annotationClassId)) {
            return null;
        }
        return loadAnnotation(annotationClassId, source, result);
    }

    private final KotlinJvmBinaryClass toBinaryClass(ProtoContainer.Class r3) {
        SourceElement source = r3.getSource();
        KotlinJvmBinarySourceElement kotlinJvmBinarySourceElement = source instanceof KotlinJvmBinarySourceElement ? (KotlinJvmBinarySourceElement) source : null;
        if (kotlinJvmBinarySourceElement != null) {
            return kotlinJvmBinarySourceElement.getBinaryClass();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<A> loadClassAnnotations(ProtoContainer.Class container) {
        Intrinsics.checkNotNullParameter(container, "container");
        KotlinJvmBinaryClass binaryClass = toBinaryClass(container);
        if (binaryClass == null) {
            throw new IllegalStateException(("Class for loading annotations is not found: " + container.getFqName()).toString());
        }
        final ArrayList arrayList = new ArrayList(1);
        binaryClass.loadClassAnnotations(new KotlinJvmBinaryClass.AnnotationVisitor(this) { // from class: org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader.loadClassAnnotations.1
            final /* synthetic */ AbstractBinaryClassAnnotationLoader<A, S> this$0;

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
            public void visitEnd() {
            }

            {
                this.this$0 = this;
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
            public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(ClassId classId, SourceElement source) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                Intrinsics.checkNotNullParameter(source, "source");
                return this.this$0.loadAnnotationIfNotSpecial(classId, source, arrayList);
            }
        }, getCachedFileContent(binaryClass));
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<A> loadCallableAnnotations(ProtoContainer container, MessageLite proto, AnnotatedCallableKind kind) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (kind == AnnotatedCallableKind.PROPERTY) {
            return loadPropertyAnnotations(container, (ProtoBuf.Property) proto, PropertyRelatedElement.PROPERTY);
        }
        MemberSignature callableSignature$default = getCallableSignature$default(this, proto, container.getNameResolver(), container.getTypeTable(), kind, false, 16, null);
        return callableSignature$default == null ? CollectionsKt.emptyList() : findClassAndLoadMemberAnnotations$default(this, container, callableSignature$default, false, false, null, false, 60, null);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<A> loadPropertyBackingFieldAnnotations(ProtoContainer container, ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        return loadPropertyAnnotations(container, proto, PropertyRelatedElement.BACKING_FIELD);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<A> loadPropertyDelegateFieldAnnotations(ProtoContainer container, ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        return loadPropertyAnnotations(container, proto, PropertyRelatedElement.DELEGATE_FIELD);
    }

    private final List<A> loadPropertyAnnotations(ProtoContainer container, ProtoBuf.Property proto, PropertyRelatedElement element) {
        Boolean bool = Flags.IS_CONST.get(proto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        boolean zBooleanValue = bool.booleanValue();
        boolean zIsMovedFromInterfaceCompanion = JvmProtoBufUtil.isMovedFromInterfaceCompanion(proto);
        if (element == PropertyRelatedElement.PROPERTY) {
            MemberSignature propertySignature$default = AbstractBinaryClassAnnotationLoaderKt.getPropertySignature$default(proto, container.getNameResolver(), container.getTypeTable(), false, true, false, 40, null);
            return propertySignature$default == null ? CollectionsKt.emptyList() : findClassAndLoadMemberAnnotations$default(this, container, propertySignature$default, true, false, Boolean.valueOf(zBooleanValue), zIsMovedFromInterfaceCompanion, 8, null);
        }
        MemberSignature propertySignature$default2 = AbstractBinaryClassAnnotationLoaderKt.getPropertySignature$default(proto, container.getNameResolver(), container.getTypeTable(), true, false, false, 48, null);
        if (propertySignature$default2 == null) {
            return CollectionsKt.emptyList();
        }
        return StringsKt.contains$default((CharSequence) propertySignature$default2.getSignature(), (CharSequence) JvmAbi.DELEGATED_PROPERTY_NAME_SUFFIX, false, 2, (Object) null) != (element == PropertyRelatedElement.DELEGATE_FIELD) ? CollectionsKt.emptyList() : findClassAndLoadMemberAnnotations(container, propertySignature$default2, true, true, Boolean.valueOf(zBooleanValue), zIsMovedFromInterfaceCompanion);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<A> loadEnumEntryAnnotations(ProtoContainer container, ProtoBuf.EnumEntry proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        MemberSignature.Companion companion = MemberSignature.INSTANCE;
        String string = container.getNameResolver().getString(proto.getName());
        String strAsString = ((ProtoContainer.Class) container).getClassId().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return findClassAndLoadMemberAnnotations$default(this, container, companion.fromFieldNameAndDesc(string, ClassMapperLite.mapClass(strAsString)), false, false, null, false, 60, null);
    }

    static /* synthetic */ List findClassAndLoadMemberAnnotations$default(AbstractBinaryClassAnnotationLoader abstractBinaryClassAnnotationLoader, ProtoContainer protoContainer, MemberSignature memberSignature, boolean z, boolean z2, Boolean bool, boolean z3, int i, Object obj) {
        if (obj == null) {
            return abstractBinaryClassAnnotationLoader.findClassAndLoadMemberAnnotations(protoContainer, memberSignature, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? null : bool, (i & 32) != 0 ? false : z3);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: findClassAndLoadMemberAnnotations");
    }

    private final List<A> findClassAndLoadMemberAnnotations(ProtoContainer container, MemberSignature signature, boolean property, boolean field, Boolean isConst, boolean isMovedFromInterfaceCompanion) {
        KotlinJvmBinaryClass kotlinJvmBinaryClassFindClassWithAnnotationsAndInitializers = findClassWithAnnotationsAndInitializers(container, INSTANCE.getSpecialCaseContainerClass(container, property, field, isConst, isMovedFromInterfaceCompanion, this.kotlinClassFinder, getJvmMetadataVersion()));
        if (kotlinJvmBinaryClassFindClassWithAnnotationsAndInitializers == null) {
            return CollectionsKt.emptyList();
        }
        List<A> list = getAnnotationsContainer(kotlinJvmBinaryClassFindClassWithAnnotationsAndInitializers).getMemberAnnotations().get(signature);
        return list == null ? CollectionsKt.emptyList() : list;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<A> loadValueParameterAnnotations(ProtoContainer container, MessageLite callableProto, AnnotatedCallableKind kind, int parameterIndex, ProtoBuf.ValueParameter proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(callableProto, "callableProto");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(proto, "proto");
        MemberSignature callableSignature$default = getCallableSignature$default(this, callableProto, container.getNameResolver(), container.getTypeTable(), kind, false, 16, null);
        if (callableSignature$default != null) {
            return findClassAndLoadMemberAnnotations$default(this, container, MemberSignature.INSTANCE.fromMethodSignatureAndParameterIndex(callableSignature$default, parameterIndex + computeJvmParameterIndexShift(container, callableProto)), false, false, null, false, 60, null);
        }
        return CollectionsKt.emptyList();
    }

    private final int computeJvmParameterIndexShift(ProtoContainer container, MessageLite message) {
        if (message instanceof ProtoBuf.Function) {
            if (ProtoTypeTableUtilKt.hasReceiver((ProtoBuf.Function) message)) {
                return 1;
            }
        } else if (message instanceof ProtoBuf.Property) {
            if (ProtoTypeTableUtilKt.hasReceiver((ProtoBuf.Property) message)) {
                return 1;
            }
        } else if (message instanceof ProtoBuf.Constructor) {
            Intrinsics.checkNotNull(container, "null cannot be cast to non-null type org.jetbrains.kotlin.serialization.deserialization.ProtoContainer.Class");
            ProtoContainer.Class r4 = (ProtoContainer.Class) container;
            if (r4.getKind() == ProtoBuf.Class.Kind.ENUM_CLASS) {
                return 2;
            }
            if (r4.getIsInner()) {
                return 1;
            }
        } else {
            throw new UnsupportedOperationException("Unsupported message: " + message.getClass());
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<A> loadExtensionReceiverParameterAnnotations(ProtoContainer container, MessageLite proto, AnnotatedCallableKind kind) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(kind, "kind");
        MemberSignature callableSignature$default = getCallableSignature$default(this, proto, container.getNameResolver(), container.getTypeTable(), kind, false, 16, null);
        if (callableSignature$default != null) {
            return findClassAndLoadMemberAnnotations$default(this, container, MemberSignature.INSTANCE.fromMethodSignatureAndParameterIndex(callableSignature$default, 0), false, false, null, false, 60, null);
        }
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<A> loadTypeAnnotations(ProtoBuf.Type proto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Object extension = proto.getExtension(JvmProtoBuf.typeAnnotation);
        Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
        Iterable<ProtoBuf.Annotation> iterable = (Iterable) extension;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (ProtoBuf.Annotation annotation : iterable) {
            Intrinsics.checkNotNull(annotation);
            arrayList.add(loadAnnotation(annotation, nameResolver));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public List<A> loadTypeParameterAnnotations(ProtoBuf.TypeParameter proto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Object extension = proto.getExtension(JvmProtoBuf.typeParameterAnnotation);
        Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
        Iterable<ProtoBuf.Annotation> iterable = (Iterable) extension;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (ProtoBuf.Annotation annotation : iterable) {
            Intrinsics.checkNotNull(annotation);
            arrayList.add(loadAnnotation(annotation, nameResolver));
        }
        return arrayList;
    }

    protected final KotlinJvmBinaryClass findClassWithAnnotationsAndInitializers(ProtoContainer container, KotlinJvmBinaryClass specialCase) {
        Intrinsics.checkNotNullParameter(container, "container");
        if (specialCase != null) {
            return specialCase;
        }
        if (container instanceof ProtoContainer.Class) {
            return toBinaryClass((ProtoContainer.Class) container);
        }
        return null;
    }

    public static /* synthetic */ MemberSignature getCallableSignature$default(AbstractBinaryClassAnnotationLoader abstractBinaryClassAnnotationLoader, MessageLite messageLite, NameResolver nameResolver, TypeTable typeTable, AnnotatedCallableKind annotatedCallableKind, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getCallableSignature");
        }
        if ((i & 16) != 0) {
            z = false;
        }
        return abstractBinaryClassAnnotationLoader.getCallableSignature(messageLite, nameResolver, typeTable, annotatedCallableKind, z);
    }

    protected final MemberSignature getCallableSignature(MessageLite proto, NameResolver nameResolver, TypeTable typeTable, AnnotatedCallableKind kind, boolean requireHasFieldFlagForField) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (proto instanceof ProtoBuf.Constructor) {
            MemberSignature.Companion companion = MemberSignature.INSTANCE;
            JvmMemberSignature.Method jvmConstructorSignature = JvmProtoBufUtil.INSTANCE.getJvmConstructorSignature((ProtoBuf.Constructor) proto, nameResolver, typeTable);
            if (jvmConstructorSignature == null) {
                return null;
            }
            return companion.fromJvmMemberSignature(jvmConstructorSignature);
        }
        if (proto instanceof ProtoBuf.Function) {
            MemberSignature.Companion companion2 = MemberSignature.INSTANCE;
            JvmMemberSignature.Method jvmMethodSignature = JvmProtoBufUtil.INSTANCE.getJvmMethodSignature((ProtoBuf.Function) proto, nameResolver, typeTable);
            if (jvmMethodSignature == null) {
                return null;
            }
            return companion2.fromJvmMemberSignature(jvmMethodSignature);
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
            MemberSignature.Companion companion3 = MemberSignature.INSTANCE;
            JvmProtoBuf.JvmMethodSignature getter = jvmPropertySignature.getGetter();
            Intrinsics.checkNotNullExpressionValue(getter, "getGetter(...)");
            return companion3.fromMethod(nameResolver, getter);
        }
        if (i != 2) {
            if (i != 3) {
                return null;
            }
            return AbstractBinaryClassAnnotationLoaderKt.getPropertySignature((ProtoBuf.Property) proto, nameResolver, typeTable, true, true, requireHasFieldFlagForField);
        }
        if (!jvmPropertySignature.hasSetter()) {
            return null;
        }
        MemberSignature.Companion companion4 = MemberSignature.INSTANCE;
        JvmProtoBuf.JvmMethodSignature setter = jvmPropertySignature.getSetter();
        Intrinsics.checkNotNullExpressionValue(setter, "getSetter(...)");
        return companion4.fromMethod(nameResolver, setter);
    }

    protected final boolean isImplicitRepeatableContainer(ClassId classId) {
        KotlinJvmBinaryClass kotlinJvmBinaryClassFindKotlinClass;
        Intrinsics.checkNotNullParameter(classId, "classId");
        return classId.getOuterClassId() != null && Intrinsics.areEqual(classId.getShortClassName().asString(), JvmAbi.REPEATABLE_ANNOTATION_CONTAINER_NAME) && (kotlinJvmBinaryClassFindKotlinClass = KotlinClassFinderKt.findKotlinClass(this.kotlinClassFinder, classId, getJvmMetadataVersion())) != null && SpecialJvmAnnotations.INSTANCE.isAnnotatedWithContainerMetaAnnotation(kotlinJvmBinaryClassFindKotlinClass);
    }

    /* JADX INFO: compiled from: AbstractBinaryClassAnnotationLoader.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JG\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationLoader$Companion;", "", "()V", "getSpecialCaseContainerClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "container", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "property", "", "field", "isConst", "isMovedFromInterfaceCompanion", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "(Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;ZZLjava/lang/Boolean;ZLorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;)Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinJvmBinaryClass getSpecialCaseContainerClass(ProtoContainer container, boolean property, boolean field, Boolean isConst, boolean isMovedFromInterfaceCompanion, KotlinClassFinder kotlinClassFinder, JvmMetadataVersion jvmMetadataVersion) {
            ProtoContainer.Class outerClass;
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
            Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
            if (property) {
                if (isConst == null) {
                    throw new IllegalStateException(("isConst should not be null for property (container=" + container + ')').toString());
                }
                if (container instanceof ProtoContainer.Class) {
                    ProtoContainer.Class r8 = (ProtoContainer.Class) container;
                    if (r8.getKind() == ProtoBuf.Class.Kind.INTERFACE) {
                        ClassId classIdCreateNestedClassId = r8.getClassId().createNestedClassId(Name.identifier(JvmAbi.DEFAULT_IMPLS_CLASS_NAME));
                        Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
                        return KotlinClassFinderKt.findKotlinClass(kotlinClassFinder, classIdCreateNestedClassId, jvmMetadataVersion);
                    }
                }
                if (isConst.booleanValue() && (container instanceof ProtoContainer.Package)) {
                    SourceElement source = container.getSource();
                    JvmPackagePartSource jvmPackagePartSource = source instanceof JvmPackagePartSource ? (JvmPackagePartSource) source : null;
                    JvmClassName facadeClassName = jvmPackagePartSource != null ? jvmPackagePartSource.getFacadeClassName() : null;
                    if (facadeClassName != null) {
                        String internalName = facadeClassName.getInternalName();
                        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                        ClassId classId = ClassId.topLevel(new FqName(StringsKt.replace$default(internalName, '/', '.', false, 4, (Object) null)));
                        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
                        return KotlinClassFinderKt.findKotlinClass(kotlinClassFinder, classId, jvmMetadataVersion);
                    }
                }
            }
            if (field && (container instanceof ProtoContainer.Class)) {
                ProtoContainer.Class r9 = (ProtoContainer.Class) container;
                if (r9.getKind() == ProtoBuf.Class.Kind.COMPANION_OBJECT && (outerClass = r9.getOuterClass()) != null && (outerClass.getKind() == ProtoBuf.Class.Kind.CLASS || outerClass.getKind() == ProtoBuf.Class.Kind.ENUM_CLASS || (isMovedFromInterfaceCompanion && (outerClass.getKind() == ProtoBuf.Class.Kind.INTERFACE || outerClass.getKind() == ProtoBuf.Class.Kind.ANNOTATION_CLASS)))) {
                    SourceElement source2 = outerClass.getSource();
                    KotlinJvmBinarySourceElement kotlinJvmBinarySourceElement = source2 instanceof KotlinJvmBinarySourceElement ? (KotlinJvmBinarySourceElement) source2 : null;
                    if (kotlinJvmBinarySourceElement != null) {
                        return kotlinJvmBinarySourceElement.getBinaryClass();
                    }
                    return null;
                }
            }
            if (!(container instanceof ProtoContainer.Package) || !(container.getSource() instanceof JvmPackagePartSource)) {
                return null;
            }
            SourceElement source3 = container.getSource();
            Intrinsics.checkNotNull(source3, "null cannot be cast to non-null type org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource");
            JvmPackagePartSource jvmPackagePartSource2 = (JvmPackagePartSource) source3;
            KotlinJvmBinaryClass knownJvmBinaryClass = jvmPackagePartSource2.getKnownJvmBinaryClass();
            return knownJvmBinaryClass == null ? KotlinClassFinderKt.findKotlinClass(kotlinClassFinder, jvmPackagePartSource2.getClassId(), jvmMetadataVersion) : knownJvmBinaryClass;
        }
    }
}
