package org.jetbrains.kotlin.serialization.js;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.js.JsProtoBuf;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.ProtocolStringList;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.OptionalAnnotationUtil;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.serialization.AnnotationSerializer;
import org.jetbrains.kotlin.serialization.ApproximatingStringTable;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.JsMetadataVersion;
import org.jetbrains.kotlin.utils.KotlinJavascriptMetadataUtils;

/* JADX INFO: compiled from: KotlinJavascriptSerializationUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ª\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u00017B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tJ&\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00072\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0010H\u0002J.\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007JD\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\b\u001a\u00020\t2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010\u001d\u001a\u00020\u001eJ \u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010!\u001a\u00020\"2\u0006\u0010.\u001a\u00020/H\u0002J \u00100\u001a\u0002012\u0006\u0010\b\u001a\u00020\t2\b\u00102\u001a\u0004\u0018\u00010\u00072\u0006\u0010&\u001a\u00020'J4\u00103\u001a\u0002042\u0006\u0010!\u001a\u00020\"2\f\u00105\u001a\b\u0012\u0004\u0012\u00020\t062\u0006\u0010&\u001a\u00020'2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020)R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptSerializationUtil;", "", "()V", "CLASS_METADATA_FILE_EXTENSION", "", "getPackagesFqNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getSubPackagesFqNames", "", "packageFragmentProvider", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "fqName", "result", "", "readDescriptors", "metadata", "Lorg/jetbrains/kotlin/serialization/js/PackagesWithHeaderMetadata;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "configuration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "readModuleAsProto", "Lorg/jetbrains/kotlin/serialization/js/KotlinJavaScriptLibraryParts;", "", "metadataVersion", "Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "serializeDescriptors", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "scope", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "serializeFiles", "Lorg/jetbrains/kotlin/metadata/js/JsProtoBuf$Files;", "fileRegistry", "Lorg/jetbrains/kotlin/serialization/js/KotlinFileRegistry;", "serializer", "Lorg/jetbrains/kotlin/serialization/AnnotationSerializer;", "serializeHeader", "Lorg/jetbrains/kotlin/metadata/js/JsProtoBuf$Header;", "packageFqName", "serializeMetadata", "Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptSerializationUtil$SerializedMetadata;", "jsDescriptor", "Lorg/jetbrains/kotlin/serialization/js/JsModuleDescriptor;", "SerializedMetadata", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinJavascriptSerializationUtil {
    public static final String CLASS_METADATA_FILE_EXTENSION = "kjsm";
    public static final KotlinJavascriptSerializationUtil INSTANCE = new KotlinJavascriptSerializationUtil();

    /* JADX INFO: compiled from: KotlinJavascriptSerializationUtil.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JsProtoBuf.Library.Kind.values().length];
            try {
                iArr[JsProtoBuf.Library.Kind.PLAIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JsProtoBuf.Library.Kind.AMD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JsProtoBuf.Library.Kind.COMMON_JS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[JsProtoBuf.Library.Kind.UMD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private KotlinJavascriptSerializationUtil() {
    }

    public final PackageFragmentProvider readDescriptors(PackagesWithHeaderMetadata metadata, StorageManager storageManager, ModuleDescriptor module, DeserializationConfiguration configuration, LookupTracker lookupTracker) throws IOException {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        List<byte[]> packages = metadata.getPackages();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(packages, 10));
        Iterator<T> it = packages.iterator();
        while (it.hasNext()) {
            arrayList.add(ProtoBuf.PackageFragment.parseFrom((byte[]) it.next(), JsSerializerProtocol.INSTANCE.getExtensionRegistry()));
        }
        ArrayList arrayList2 = arrayList;
        JsProtoBuf.Header from = JsProtoBuf.Header.parseFrom(CodedInputStream.newInstance(metadata.getHeader()), JsSerializerProtocol.INSTANCE.getExtensionRegistry());
        Intrinsics.checkNotNull(from);
        return KotlinJavascriptPackageFragmentProviderKt.createKotlinJavascriptPackageFragmentProvider(storageManager, module, from, arrayList2, metadata.getMetadataVersion(), configuration, lookupTracker);
    }

    public final SerializedMetadata serializeMetadata(BindingContext bindingContext, JsModuleDescriptor<? extends ModuleDescriptor> jsDescriptor, LanguageVersionSettings languageVersionSettings, JsMetadataVersion metadataVersion, Project project) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(jsDescriptor, "jsDescriptor");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(project, "project");
        return new SerializedMetadata(KotlinJavascriptSerializationUtilKt.missingMetadata(MapsKt.emptyMap(), bindingContext, jsDescriptor.getData(), languageVersionSettings, metadataVersion, project), jsDescriptor, languageVersionSettings, metadataVersion);
    }

    /* JADX INFO: compiled from: KotlinJavascriptSerializationUtil.kt */
    @Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u001dB7\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\u0005H\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0006\u0010\u0011\u001a\u00020\u0012J\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014J\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0018H\u0002J\u001c\u0010\u0019\u001a\u00020\u0017*\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0005H\u0002R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptSerializationUtil$SerializedMetadata;", "", "serializedFragments", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "jsDescriptor", "Lorg/jetbrains/kotlin/serialization/js/JsModuleDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "metadataVersion", "Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/serialization/js/JsModuleDescriptor;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/utils/JsMetadataVersion;)V", "asByteArray", "asLibrary", "Lorg/jetbrains/kotlin/metadata/js/JsProtoBuf$Library;", "asString", "", "serializedPackages", "", "Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptSerializationUtil$SerializedMetadata$SerializedPackage;", "appendPackageFragments", "", "Ljava/io/OutputStream;", "writeProto", "fieldNumber", "", FirebaseAnalytics.Param.CONTENT, "SerializedPackage", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SerializedMetadata {
        private final JsModuleDescriptor<ModuleDescriptor> jsDescriptor;
        private final LanguageVersionSettings languageVersionSettings;
        private final JsMetadataVersion metadataVersion;
        private final Map<FqName, byte[]> serializedFragments;

        /* JADX INFO: compiled from: KotlinJavascriptSerializationUtil.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ModuleKind.values().length];
                try {
                    iArr[ModuleKind.PLAIN.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ModuleKind.AMD.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ModuleKind.COMMON_JS.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[ModuleKind.UMD.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[ModuleKind.ES.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public SerializedMetadata(Map<FqName, byte[]> serializedFragments, JsModuleDescriptor<? extends ModuleDescriptor> jsDescriptor, LanguageVersionSettings languageVersionSettings, JsMetadataVersion metadataVersion) {
            Intrinsics.checkNotNullParameter(serializedFragments, "serializedFragments");
            Intrinsics.checkNotNullParameter(jsDescriptor, "jsDescriptor");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
            this.serializedFragments = serializedFragments;
            this.jsDescriptor = jsDescriptor;
            this.languageVersionSettings = languageVersionSettings;
            this.metadataVersion = metadataVersion;
        }

        /* JADX INFO: compiled from: KotlinJavascriptSerializationUtil.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/KotlinJavascriptSerializationUtil$SerializedMetadata$SerializedPackage;", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "bytes", "", "(Lorg/jetbrains/kotlin/name/FqName;[B)V", "getBytes", "()[B", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SerializedPackage {
            private final byte[] bytes;
            private final FqName fqName;

            public SerializedPackage(FqName fqName, byte[] bytes) {
                Intrinsics.checkNotNullParameter(fqName, "fqName");
                Intrinsics.checkNotNullParameter(bytes, "bytes");
                this.fqName = fqName;
                this.bytes = bytes;
            }

            public final byte[] getBytes() {
                return this.bytes;
            }

            public final FqName getFqName() {
                return this.fqName;
            }
        }

        public final List<SerializedPackage> serializedPackages() {
            ArrayList arrayList = new ArrayList();
            for (Map.Entry<FqName, byte[]> entry : this.serializedFragments.entrySet()) {
                FqName key = entry.getKey();
                byte[] value = entry.getValue();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ByteArrayOutputStream byteArrayOutputStream2 = byteArrayOutputStream;
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream2);
                int[] array = this.metadataVersion.getNumbers();
                dataOutputStream.writeInt(array.length);
                for (int i : array) {
                    dataOutputStream.writeInt(i);
                }
                KotlinJavascriptSerializationUtil.INSTANCE.serializeHeader(this.jsDescriptor.getData(), key, this.languageVersionSettings).writeDelimitedTo(byteArrayOutputStream2);
                byteArrayOutputStream.write(value);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                arrayList.add(new SerializedPackage(key, byteArray));
            }
            return arrayList;
        }

        public final String asString() {
            return KotlinJavascriptMetadataUtils.INSTANCE.formatMetadataAsString(this.jsDescriptor.getName(), asByteArray(), this.metadataVersion);
        }

        private final byte[] asByteArray() {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            try {
                GZIPOutputStream gZIPOutputStream2 = gZIPOutputStream;
                KotlinJavascriptSerializationUtil.INSTANCE.serializeHeader(this.jsDescriptor.getData(), null, this.languageVersionSettings).writeDelimitedTo(gZIPOutputStream2);
                asLibrary().writeTo(gZIPOutputStream2);
                appendPackageFragments(gZIPOutputStream2);
                Iterator<T> it = this.jsDescriptor.getImported().iterator();
                while (it.hasNext()) {
                    byte[] bytes = ((String) it.next()).getBytes(Charsets.UTF_8);
                    Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                    writeProto(gZIPOutputStream2, 3, bytes);
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(gZIPOutputStream, null);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                return byteArray;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(gZIPOutputStream, th);
                    throw th2;
                }
            }
        }

        private final JsProtoBuf.Library asLibrary() {
            JsProtoBuf.Library.Kind kind;
            ModuleKind kind2 = this.jsDescriptor.getKind();
            JsProtoBuf.Library.Builder builderNewBuilder = JsProtoBuf.Library.newBuilder();
            int i = WhenMappings.$EnumSwitchMapping$0[kind2.ordinal()];
            if (i == 1) {
                kind = JsProtoBuf.Library.Kind.PLAIN;
            } else if (i == 2) {
                kind = JsProtoBuf.Library.Kind.AMD;
            } else if (i == 3) {
                kind = JsProtoBuf.Library.Kind.COMMON_JS;
            } else {
                if (i != 4) {
                    if (i != 5) {
                        throw new NoWhenBranchMatchedException();
                    }
                    throw new IllegalStateException("Es modules serialization".toString());
                }
                kind = JsProtoBuf.Library.Kind.UMD;
            }
            if (builderNewBuilder.getKind() != kind) {
                builderNewBuilder.setKind(kind);
            }
            JsProtoBuf.Library libraryBuild = builderNewBuilder.build();
            Intrinsics.checkNotNullExpressionValue(libraryBuild, "build(...)");
            return libraryBuild;
        }

        private final void writeProto(OutputStream outputStream, int i, byte[] bArr) throws IOException {
            outputStream.write((i << 3) | 2);
            int length = bArr.length;
            while (length > 127) {
                outputStream.write((length & 127) | 128);
                length >>>= 7;
            }
            outputStream.write(length);
            outputStream.write(bArr);
        }

        private final void appendPackageFragments(OutputStream outputStream) throws IOException {
            Iterator it = CollectionsKt.sortedWith(this.serializedFragments.entrySet(), new Comparator() { // from class: org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtil$SerializedMetadata$appendPackageFragments$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((FqName) ((Map.Entry) t).getKey()).asString(), ((FqName) ((Map.Entry) t2).getKey()).asString());
                }
            }).iterator();
            while (it.hasNext()) {
                writeProto(outputStream, 2, (byte[]) ((Map.Entry) it.next()).getValue());
            }
        }
    }

    public final ProtoBuf.PackageFragment serializeDescriptors(BindingContext bindingContext, final ModuleDescriptor module, Collection<? extends DeclarationDescriptor> scope, FqName fqName, LanguageVersionSettings languageVersionSettings, Project project, JsMetadataVersion metadataVersion) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        ProtoBuf.PackageFragment.Builder builderNewBuilder = ProtoBuf.PackageFragment.newBuilder();
        Function1 function1 = new Function1<DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtil$serializeDescriptors$skip$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(DeclarationDescriptor descriptor) {
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                boolean z = true;
                if (!Intrinsics.areEqual(DescriptorUtilsKt.getModule(descriptor), module)) {
                    return true;
                }
                if ((descriptor instanceof MemberDescriptor) && ((MemberDescriptor) descriptor).get$isExpect()) {
                    if ((descriptor instanceof ClassDescriptor) && OptionalAnnotationUtil.shouldGenerateExpectClass((ClassDescriptor) descriptor)) {
                        z = false;
                    }
                    return Boolean.valueOf(z);
                }
                return false;
            }
        };
        KotlinFileRegistry kotlinFileRegistry = new KotlinFileRegistry();
        KotlinJavascriptSerializerExtension kotlinJavascriptSerializerExtension = new KotlinJavascriptSerializerExtension(kotlinFileRegistry, languageVersionSettings, metadataVersion);
        Collection<? extends DeclarationDescriptor> collection = scope;
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (obj instanceof ClassDescriptor) {
                arrayList.add(obj);
            }
        }
        List listSortedWith = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtil$serializeDescriptors$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(DescriptorUtilsKt.getFqNameSafe((ClassDescriptor) t).asString(), DescriptorUtilsKt.getFqNameSafe((ClassDescriptor) t2).asString());
            }
        });
        DescriptorSerializer descriptorSerializerCreateTopLevel$default = DescriptorSerializer.Companion.createTopLevel$default(DescriptorSerializer.INSTANCE, kotlinJavascriptSerializerExtension, languageVersionSettings, null, 4, null);
        serializeDescriptors$serializeClasses(function1, kotlinJavascriptSerializerExtension, languageVersionSettings, project, builderNewBuilder, listSortedWith, descriptorSerializerCreateTopLevel$default);
        ApproximatingStringTable stringTable = kotlinJavascriptSerializerExtension.getStringTable();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : collection) {
            if (!((Boolean) function1.invoke(obj2)).booleanValue()) {
                arrayList2.add(obj2);
            }
        }
        builderNewBuilder.setPackage(descriptorSerializerCreateTopLevel$default.packagePartProto(fqName, arrayList2).build());
        builderNewBuilder.setExtension(JsProtoBuf.packageFragmentFiles, serializeFiles(kotlinFileRegistry, bindingContext, new AnnotationSerializer(stringTable)));
        Pair<ProtoBuf.StringTable, ProtoBuf.QualifiedNameTable> pairBuildProto = stringTable.buildProto();
        ProtoBuf.StringTable stringTableComponent1 = pairBuildProto.component1();
        ProtoBuf.QualifiedNameTable qualifiedNameTableComponent2 = pairBuildProto.component2();
        builderNewBuilder.setStrings(stringTableComponent1);
        builderNewBuilder.setQualifiedNames(qualifiedNameTableComponent2);
        ProtoBuf.PackageFragment packageFragmentBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(packageFragmentBuild, "build(...)");
        return packageFragmentBuild;
    }

    private static final void serializeDescriptors$serializeClasses(Function1<? super DeclarationDescriptor, Boolean> function1, KotlinJavascriptSerializerExtension kotlinJavascriptSerializerExtension, LanguageVersionSettings languageVersionSettings, Project project, ProtoBuf.PackageFragment.Builder builder, Collection<? extends DeclarationDescriptor> collection, DescriptorSerializer descriptorSerializer) {
        for (DeclarationDescriptor declarationDescriptor : collection) {
            if ((declarationDescriptor instanceof ClassDescriptor) && !function1.invoke(declarationDescriptor).booleanValue()) {
                ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
                DescriptorSerializer descriptorSerializerCreate = DescriptorSerializer.INSTANCE.create(classDescriptor, kotlinJavascriptSerializerExtension, descriptorSerializer, languageVersionSettings, project);
                MemberScope unsubstitutedInnerClassesScope = classDescriptor.getUnsubstitutedInnerClassesScope();
                Intrinsics.checkNotNullExpressionValue(unsubstitutedInnerClassesScope, "getUnsubstitutedInnerClassesScope(...)");
                serializeDescriptors$serializeClasses(function1, kotlinJavascriptSerializerExtension, languageVersionSettings, project, builder, ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedInnerClassesScope, null, null, 3, null), descriptorSerializerCreate);
                ProtoBuf.Class classBuild = descriptorSerializerCreate.classProto(classDescriptor).build();
                if (classBuild == null) {
                    throw new IllegalStateException(("Class not serialized: " + declarationDescriptor).toString());
                }
                builder.addClass_(classBuild);
            }
        }
    }

    private final JsProtoBuf.Files serializeFiles(KotlinFileRegistry fileRegistry, BindingContext bindingContext, AnnotationSerializer serializer) {
        ArrayList annotations;
        JsProtoBuf.Files.Builder builderNewBuilder = JsProtoBuf.Files.newBuilder();
        for (Map.Entry entry : CollectionsKt.sortedWith(fileRegistry.getFileIds().entrySet(), new Comparator() { // from class: org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtil$serializeFiles$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues((Integer) ((Map.Entry) t).getValue(), (Integer) ((Map.Entry) t2).getValue());
            }
        })) {
            KotlinFileMetadata kotlinFileMetadata = (KotlinFileMetadata) entry.getKey();
            int iIntValue = ((Number) entry.getValue()).intValue();
            JsProtoBuf.File.Builder builderNewBuilder2 = JsProtoBuf.File.newBuilder();
            if (iIntValue != builderNewBuilder.getFileCount()) {
                builderNewBuilder2.setId(iIntValue);
            }
            if (!(kotlinFileMetadata instanceof KotlinPsiFileMetadata)) {
                if (!(kotlinFileMetadata instanceof KotlinDeserializedFileMetadata)) {
                    throw new NoWhenBranchMatchedException();
                }
                KotlinDeserializedFileMetadata kotlinDeserializedFileMetadata = (KotlinDeserializedFileMetadata) kotlinFileMetadata;
                KotlinJavascriptPackageFragment.FileHolder fileHolder = kotlinDeserializedFileMetadata.getPackageFragment().getFileMap().get(Integer.valueOf(kotlinDeserializedFileMetadata.getFileId()));
                Intrinsics.checkNotNull(fileHolder);
                annotations = fileHolder.getAnnotations();
            } else {
                List<KtAnnotationEntry> annotationEntries = ((KotlinPsiFileMetadata) kotlinFileMetadata).getKtFile().getAnnotationEntries();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotationEntries, 10));
                Iterator<T> it = annotationEntries.iterator();
                while (it.hasNext()) {
                    Object obj = bindingContext.get(BindingContext.ANNOTATION, (KtAnnotationEntry) it.next());
                    Intrinsics.checkNotNull(obj);
                    arrayList.add((AnnotationDescriptor) obj);
                }
                annotations = arrayList;
            }
            Iterator<AnnotationDescriptor> it2 = DescriptorUtilsKt.filterOutSourceAnnotations(annotations).iterator();
            while (it2.hasNext()) {
                builderNewBuilder2.addAnnotation(serializer.serializeAnnotation(it2.next()));
            }
            builderNewBuilder.addFile(builderNewBuilder2);
        }
        JsProtoBuf.Files filesBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(filesBuild, "build(...)");
        return filesBuild;
    }

    public final JsProtoBuf.Header serializeHeader(ModuleDescriptor module, FqName packageFqName, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        JsProtoBuf.Header.Builder builderNewBuilder = JsProtoBuf.Header.newBuilder();
        if (packageFqName != null) {
            builderNewBuilder.setPackageFqName(packageFqName.asString());
        }
        if (languageVersionSettings.isPreRelease()) {
            builderNewBuilder.setFlags(1);
        }
        JsProtoBuf.Header headerBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(headerBuild, "build(...)");
        return headerBuild;
    }

    public final Set<FqName> getPackagesFqNames(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        KotlinJavascriptSerializationUtil kotlinJavascriptSerializationUtil = INSTANCE;
        PackageFragmentProvider packageFragmentProviderForModuleContentWithoutDependencies = KotlinJavascriptSerializationUtilKt.getPackageFragmentProviderForModuleContentWithoutDependencies(module);
        FqName ROOT = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        kotlinJavascriptSerializationUtil.getSubPackagesFqNames(packageFragmentProviderForModuleContentWithoutDependencies, ROOT, linkedHashSet);
        FqName ROOT2 = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT2, "ROOT");
        linkedHashSet.add(ROOT2);
        return linkedHashSet;
    }

    private final void getSubPackagesFqNames(PackageFragmentProvider packageFragmentProvider, FqName fqName, Set<FqName> result) {
        if (!fqName.isRoot()) {
            result.add(fqName);
        }
        Iterator<FqName> it = packageFragmentProvider.getSubPackagesOf(fqName, new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtil.getSubPackagesFqNames.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Name it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return true;
            }
        }).iterator();
        while (it.hasNext()) {
            getSubPackagesFqNames(packageFragmentProvider, it.next(), result);
        }
    }

    @JvmStatic
    public static final KotlinJavaScriptLibraryParts readModuleAsProto(byte[] metadata, JsMetadataVersion metadataVersion) {
        ModuleKind moduleKind;
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        GZIPInputStream gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(metadata));
        try {
            GZIPInputStream gZIPInputStream2 = gZIPInputStream;
            Pair pair = TuplesKt.to(JsProtoBuf.Header.parseDelimitedFrom(gZIPInputStream2, JsSerializerProtocol.INSTANCE.getExtensionRegistry()), JsProtoBuf.Library.parseFrom(gZIPInputStream2, JsSerializerProtocol.INSTANCE.getExtensionRegistry()));
            CloseableKt.closeFinally(gZIPInputStream, null);
            JsProtoBuf.Header header = (JsProtoBuf.Header) pair.component1();
            JsProtoBuf.Library library = (JsProtoBuf.Library) pair.component2();
            JsProtoBuf.Library.Kind kind = library.getKind();
            int i = kind == null ? -1 : WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
            if (i == -1 || i == 1) {
                moduleKind = ModuleKind.PLAIN;
            } else if (i == 2) {
                moduleKind = ModuleKind.AMD;
            } else if (i == 3) {
                moduleKind = ModuleKind.COMMON_JS;
            } else {
                if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                moduleKind = ModuleKind.UMD;
            }
            Intrinsics.checkNotNull(header);
            List<ProtoBuf.PackageFragment> packageFragmentList = library.getPackageFragmentList();
            Intrinsics.checkNotNullExpressionValue(packageFragmentList, "getPackageFragmentList(...)");
            ProtocolStringList importedModuleList = library.getImportedModuleList();
            Intrinsics.checkNotNullExpressionValue(importedModuleList, "getImportedModuleList(...)");
            return new KotlinJavaScriptLibraryParts(header, packageFragmentList, moduleKind, importedModuleList, metadataVersion);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(gZIPInputStream, th);
                throw th2;
            }
        }
    }
}
