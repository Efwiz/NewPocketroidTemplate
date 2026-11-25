package org.jetbrains.kotlin.serialization.js;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.metadata.js.JsProtoBuf;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragmentImpl;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerAbiStability;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.JsMetadataVersion;

/* JADX INFO: compiled from: KotlinJavascriptPackageFragment.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\"#B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0016R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R!\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u0015\u0012\b\u0012\u00060\u0016R\u00020\u00000\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptPackageFragment;", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializedPackageFragmentImpl;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "header", "Lorg/jetbrains/kotlin/metadata/js/JsProtoBuf$Header;", "metadataVersion", "Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;Lorg/jetbrains/kotlin/metadata/js/JsProtoBuf$Header;Lorg/jetbrains/kotlin/utils/JsMetadataVersion;Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;)V", "annotationDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationDeserializer;", "fileMap", "", "", "Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptPackageFragment$FileHolder;", "getFileMap", "()Ljava/util/Map;", "getContainingFileAnnotations", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "initialize", "", "components", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "FileHolder", "JsContainerSource", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinJavascriptPackageFragment extends DeserializedPackageFragmentImpl {
    private AnnotationDeserializer annotationDeserializer;
    private final Map<Integer, FileHolder> fileMap;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinJavascriptPackageFragment(FqName fqName, StorageManager storageManager, ModuleDescriptor module, ProtoBuf.PackageFragment proto, JsProtoBuf.Header header, JsMetadataVersion metadataVersion, DeserializationConfiguration configuration) {
        super(fqName, storageManager, module, proto, metadataVersion, new JsContainerSource(fqName, header, configuration));
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        List<JsProtoBuf.File> fileList = ((JsProtoBuf.Files) proto.getExtension(JsProtoBuf.packageFragmentFiles)).getFileList();
        Intrinsics.checkNotNullExpressionValue(fileList, "getFileList(...)");
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(fileList);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
        for (IndexedValue indexedValue : iterableWithIndex) {
            int index = indexedValue.getIndex();
            JsProtoBuf.File file = (JsProtoBuf.File) indexedValue.component2();
            if (file.hasId()) {
                index = file.getId();
            }
            Integer numValueOf = Integer.valueOf(index);
            List<ProtoBuf.Annotation> annotationList = file.getAnnotationList();
            Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
            Pair pair = TuplesKt.to(numValueOf, new FileHolder(this, annotationList));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.fileMap = linkedHashMap;
    }

    public final Map<Integer, FileHolder> getFileMap() {
        return this.fileMap;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragmentImpl, org.jetbrains.kotlin.serialization.deserialization.DeserializedPackageFragment
    public void initialize(DeserializationComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        super.initialize(components);
        this.annotationDeserializer = new AnnotationDeserializer(components.getModuleDescriptor(), components.getNotFoundClasses());
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0030  */
    public final List<AnnotationDescriptor> getContainingFileAnnotations(DeclarationDescriptor descriptor) {
        List<AnnotationDescriptor> annotations;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!Intrinsics.areEqual(DescriptorUtils.getParentOfType(descriptor, PackageFragmentDescriptor.class), this)) {
            throw new IllegalArgumentException("Provided descriptor " + descriptor + " does not belong to this package " + this);
        }
        Integer numExtractFileId = KotlinJavascriptSerializationUtilKt.extractFileId(descriptor);
        if (numExtractFileId != null) {
            FileHolder fileHolder = this.fileMap.get(Integer.valueOf(numExtractFileId.intValue()));
            if (fileHolder != null) {
                annotations = fileHolder.getAnnotations();
            } else {
                annotations = null;
            }
        } else {
            annotations = null;
        }
        return annotations == null ? CollectionsKt.emptyList() : annotations;
    }

    /* JADX INFO: compiled from: KotlinJavascriptPackageFragment.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R!\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptPackageFragment$FileHolder;", "", "annotationsProto", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "(Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptPackageFragment;Ljava/util/List;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getAnnotations", "()Ljava/util/List;", "annotations$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class FileHolder {
        static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FileHolder.class), "annotations", "getAnnotations()Ljava/util/List;"))};

        /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
        private final NotNullLazyValue annotations;
        private final List<ProtoBuf.Annotation> annotationsProto;
        final /* synthetic */ KotlinJavascriptPackageFragment this$0;

        public FileHolder(final KotlinJavascriptPackageFragment kotlinJavascriptPackageFragment, List<ProtoBuf.Annotation> annotationsProto) {
            Intrinsics.checkNotNullParameter(annotationsProto, "annotationsProto");
            this.this$0 = kotlinJavascriptPackageFragment;
            this.annotationsProto = annotationsProto;
            this.annotations = kotlinJavascriptPackageFragment.getStorageManager().createLazyValue(new Function0<List<? extends AnnotationDescriptor>>() { // from class: org.jetbrains.kotlin.serialization.js.KotlinJavascriptPackageFragment$FileHolder$annotations$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends AnnotationDescriptor> invoke() {
                    List<ProtoBuf.Annotation> list = this.this$0.annotationsProto;
                    KotlinJavascriptPackageFragment kotlinJavascriptPackageFragment2 = kotlinJavascriptPackageFragment;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (ProtoBuf.Annotation annotation : list) {
                        AnnotationDeserializer annotationDeserializer = kotlinJavascriptPackageFragment2.annotationDeserializer;
                        if (annotationDeserializer == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("annotationDeserializer");
                            annotationDeserializer = null;
                        }
                        arrayList.add(annotationDeserializer.deserializeAnnotation(annotation, kotlinJavascriptPackageFragment2.getNameResolver()));
                    }
                    return arrayList;
                }
            });
        }

        public final List<AnnotationDescriptor> getAnnotations() {
            return (List) StorageKt.getValue(this.annotations, this, (KProperty<?>) $$delegatedProperties[0]);
        }
    }

    /* JADX INFO: compiled from: KotlinJavascriptPackageFragment.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptPackageFragment$JsContainerSource;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "header", "Lorg/jetbrains/kotlin/metadata/js/JsProtoBuf$Header;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/metadata/js/JsProtoBuf$Header;Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;)V", "abiStability", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "getAbiStability", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "annotations", "", "Lorg/jetbrains/kotlin/name/ClassId;", "getAnnotations", "()Ljava/util/List;", "incompatibility", "Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "getIncompatibility", "()Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "isPreReleaseInvisible", "", "()Z", "presentableString", "", "getPresentableString", "()Ljava/lang/String;", "getContainingFile", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class JsContainerSource implements DeserializedContainerSource {
        private final List<ClassId> annotations;
        private final FqName fqName;
        private final boolean isPreReleaseInvisible;

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
        /* JADX INFO: renamed from: getIncompatibility */
        public IncompatibleVersionErrorData<?> mo6784getIncompatibility() {
            return null;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x0081  */
        public JsContainerSource(FqName fqName, JsProtoBuf.Header header, DeserializationConfiguration configuration) {
            ArrayList arrayListEmptyList;
            boolean z;
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(header, "header");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            this.fqName = fqName;
            if (header.getAnnotationCount() == 0) {
                arrayListEmptyList = CollectionsKt.emptyList();
            } else {
                ProtoBuf.StringTable strings = header.getStrings();
                Intrinsics.checkNotNullExpressionValue(strings, "getStrings(...)");
                ProtoBuf.QualifiedNameTable qualifiedNames = header.getQualifiedNames();
                Intrinsics.checkNotNullExpressionValue(qualifiedNames, "getQualifiedNames(...)");
                NameResolverImpl nameResolverImpl = new NameResolverImpl(strings, qualifiedNames);
                List<ProtoBuf.Annotation> annotationList = header.getAnnotationList();
                Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
                List<ProtoBuf.Annotation> list = annotationList;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(NameResolverUtilKt.getClassId(nameResolverImpl, ((ProtoBuf.Annotation) it.next()).getId()));
                }
                arrayListEmptyList = arrayList;
            }
            this.annotations = arrayListEmptyList;
            if (configuration.getReportErrorsOnPreReleaseDependencies()) {
                z = (header.getFlags() & 1) != 0;
            }
            this.isPreReleaseInvisible = z;
        }

        public final List<ClassId> getAnnotations() {
            return this.annotations;
        }

        @Override // org.jetbrains.kotlin.descriptors.SourceElement
        public SourceFile getContainingFile() {
            SourceFile NO_SOURCE_FILE = SourceFile.NO_SOURCE_FILE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE_FILE, "NO_SOURCE_FILE");
            return NO_SOURCE_FILE;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
        /* JADX INFO: renamed from: isPreReleaseInvisible, reason: from getter */
        public boolean getIsPreReleaseInvisible() {
            return this.isPreReleaseInvisible;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
        public DeserializedContainerAbiStability getAbiStability() {
            return DeserializedContainerAbiStability.STABLE;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
        public String getPresentableString() {
            return "Package '" + this.fqName + '\'';
        }
    }
}
