package org.jetbrains.kotlin.metadata.jvm.deserialization;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.metadata.deserialization.VersionSpecificBehaviorKt;
import org.jetbrains.kotlin.metadata.jvm.JvmModuleProtoBuf;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.protobuf.ProtocolStringList;

/* JADX INFO: compiled from: ModuleMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B3\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0006¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0013\u001a\u00020\u0006J\b\u0010\u0014\u001a\u00020\u0006H\u0016R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping;", "", "version", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "packageFqName2Parts", "", "", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/PackageParts;", "moduleData", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/BinaryModuleData;", "debugName", "(Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;Ljava/util/Map;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/BinaryModuleData;Ljava/lang/String;)V", "getModuleData", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/BinaryModuleData;", "getPackageFqName2Parts", "()Ljava/util/Map;", "getVersion", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "findPackageParts", "packageFqName", "toString", "Companion", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModuleMapping {
    public static final ModuleMapping CORRUPTED;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    public static final ModuleMapping EMPTY;
    public static final String MAPPING_FILE_EXT = "kotlin_module";
    public static final int STRICT_METADATA_VERSION_SEMANTICS_FLAG = 1;
    private final String debugName;
    private final BinaryModuleData moduleData;
    private final Map<String, PackageParts> packageFqName2Parts;
    private final JvmMetadataVersion version;

    public /* synthetic */ ModuleMapping(JvmMetadataVersion jvmMetadataVersion, Map map, BinaryModuleData binaryModuleData, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(jvmMetadataVersion, map, binaryModuleData, str);
    }

    private ModuleMapping(JvmMetadataVersion jvmMetadataVersion, Map<String, PackageParts> map, BinaryModuleData binaryModuleData, String str) {
        this.version = jvmMetadataVersion;
        this.packageFqName2Parts = map;
        this.moduleData = binaryModuleData;
        this.debugName = str;
    }

    public final JvmMetadataVersion getVersion() {
        return this.version;
    }

    public final Map<String, PackageParts> getPackageFqName2Parts() {
        return this.packageFqName2Parts;
    }

    public final BinaryModuleData getModuleData() {
        return this.moduleData;
    }

    public final PackageParts findPackageParts(String packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return this.packageFqName2Parts.get(packageFqName);
    }

    /* JADX INFO: renamed from: toString, reason: from getter */
    public String getDebugName() {
        return this.debugName;
    }

    /* JADX INFO: compiled from: ModuleMapping.kt */
    @Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0002JF\u0010\f\u001a\u00020\u00042\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u0016J6\u0010\u0018\u001a\u0004\u0018\u00010\u00072\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\t0\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00070\u001a2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020!R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping$Companion;", "", "()V", "CORRUPTED", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping;", "EMPTY", "MAPPING_FILE_EXT", "", "STRICT_METADATA_VERSION_SEMANTICS_FLAG", "", "emptyBinaryData", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/BinaryModuleData;", "loadModuleMapping", "bytes", "", "debugName", "skipMetadataVersionCheck", "", "isJvmPackageNameSupported", "metadataVersionFromLanguageVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "reportIncompatibleVersionError", "Lkotlin/Function1;", "", "loadMultiFileFacadeInternalName", "multifileFacadeIds", "", "multifileFacadeShortNames", "index", "packageFqName", "readVersionNumber", "", "stream", "Ljava/io/DataInputStream;", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int[] readVersionNumber(DataInputStream stream) {
            Intrinsics.checkNotNullParameter(stream, "stream");
            try {
                int i = stream.readInt();
                if (i >= 0 && i <= 1024) {
                    int[] iArr = new int[i];
                    for (int i2 = 0; i2 < i; i2++) {
                        iArr[i2] = stream.readInt();
                    }
                    return iArr;
                }
                return null;
            } catch (IOException unused) {
                return null;
            }
        }

        public static /* synthetic */ ModuleMapping loadModuleMapping$default(Companion companion, byte[] bArr, String str, boolean z, boolean z2, JvmMetadataVersion jvmMetadataVersion, Function1 function1, int i, Object obj) {
            if ((i & 16) != 0) {
                jvmMetadataVersion = JvmMetadataVersion.INSTANCE;
            }
            return companion.loadModuleMapping(bArr, str, z, z2, jvmMetadataVersion, function1);
        }

        /* JADX WARN: Code duplicated, block: B:50:0x011c A[PHI: r12
          0x011c: PHI (r12v7 java.lang.Integer) = (r12v3 java.lang.Integer), (r12v6 java.lang.Integer) binds: [B:49:0x011a, B:52:0x012e] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Code duplicated, block: B:57:0x0145  */
        /* JADX WARN: Code duplicated, block: B:83:0x0165 A[SYNTHETIC] */
        public final ModuleMapping loadModuleMapping(byte[] bytes, String debugName, boolean skipMetadataVersionCheck, boolean isJvmPackageNameSupported, JvmMetadataVersion metadataVersionFromLanguageVersion, Function1<? super JvmMetadataVersion, Unit> reportIncompatibleVersionError) throws IOException {
            String str;
            Intrinsics.checkNotNullParameter(debugName, "debugName");
            Intrinsics.checkNotNullParameter(metadataVersionFromLanguageVersion, "metadataVersionFromLanguageVersion");
            Intrinsics.checkNotNullParameter(reportIncompatibleVersionError, "reportIncompatibleVersionError");
            if (bytes == null) {
                return ModuleMapping.EMPTY;
            }
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
            int[] versionNumber = readVersionNumber(dataInputStream);
            if (versionNumber == null) {
                return ModuleMapping.CORRUPTED;
            }
            JvmMetadataVersion jvmMetadataVersion = new JvmMetadataVersion(Arrays.copyOf(versionNumber, versionNumber.length));
            if (!skipMetadataVersionCheck && !jvmMetadataVersion.isCompatible(metadataVersionFromLanguageVersion)) {
                reportIncompatibleVersionError.invoke(jvmMetadataVersion);
                return ModuleMapping.EMPTY;
            }
            JvmMetadataVersion jvmMetadataVersion2 = new JvmMetadataVersion(versionNumber, ((VersionSpecificBehaviorKt.isKotlin1Dot4OrLater(jvmMetadataVersion) ? dataInputStream.readInt() : 0) & 1) != 0);
            if (!skipMetadataVersionCheck && !jvmMetadataVersion2.isCompatible(metadataVersionFromLanguageVersion)) {
                reportIncompatibleVersionError.invoke(jvmMetadataVersion2);
                return ModuleMapping.EMPTY;
            }
            ExtensionRegistryLite extensionRegistryLiteNewInstance = ExtensionRegistryLite.newInstance();
            BuiltInsProtoBuf.registerAllExtensions(extensionRegistryLiteNewInstance);
            JvmModuleProtoBuf.Module from = JvmModuleProtoBuf.Module.parseFrom(dataInputStream, extensionRegistryLiteNewInstance);
            if (from == null) {
                return ModuleMapping.EMPTY;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (JvmModuleProtoBuf.PackageParts packageParts : from.getPackagePartsList()) {
                String packageFqName = packageParts.getPackageFqName();
                LinkedHashMap linkedHashMap2 = linkedHashMap;
                Intrinsics.checkNotNull(packageFqName);
                Object packageParts2 = linkedHashMap2.get(packageFqName);
                if (packageParts2 == null) {
                    packageParts2 = new PackageParts(packageFqName);
                    linkedHashMap2.put(packageFqName, packageParts2);
                }
                PackageParts packageParts3 = (PackageParts) packageParts2;
                int i = 0;
                for (String str2 : packageParts.getShortClassNameList()) {
                    Intrinsics.checkNotNull(str2);
                    String strInternalNameOf = ModuleMappingKt.internalNameOf(packageFqName, str2);
                    List<Integer> multifileFacadeShortNameIdList = packageParts.getMultifileFacadeShortNameIdList();
                    Intrinsics.checkNotNullExpressionValue(multifileFacadeShortNameIdList, "getMultifileFacadeShortNameIdList(...)");
                    ProtocolStringList multifileFacadeShortNameList = packageParts.getMultifileFacadeShortNameList();
                    Intrinsics.checkNotNullExpressionValue(multifileFacadeShortNameList, "getMultifileFacadeShortNameList(...)");
                    packageParts3.addPart(strInternalNameOf, loadMultiFileFacadeInternalName(multifileFacadeShortNameIdList, multifileFacadeShortNameList, i, packageFqName));
                    i++;
                }
                if (isJvmPackageNameSupported) {
                    int i2 = 0;
                    for (String str3 : packageParts.getClassWithJvmPackageNameShortNameList()) {
                        int i3 = i2 + 1;
                        List<Integer> classWithJvmPackageNamePackageIdList = packageParts.getClassWithJvmPackageNamePackageIdList();
                        Intrinsics.checkNotNullExpressionValue(classWithJvmPackageNamePackageIdList, "getClassWithJvmPackageNamePackageIdList(...)");
                        Integer num = (Integer) CollectionsKt.getOrNull(classWithJvmPackageNamePackageIdList, i2);
                        if (num == null) {
                            List<Integer> classWithJvmPackageNamePackageIdList2 = packageParts.getClassWithJvmPackageNamePackageIdList();
                            Intrinsics.checkNotNullExpressionValue(classWithJvmPackageNamePackageIdList2, "getClassWithJvmPackageNamePackageIdList(...)");
                            num = (Integer) CollectionsKt.lastOrNull((List) classWithJvmPackageNamePackageIdList2);
                            if (num != null) {
                                int iIntValue = num.intValue();
                                ProtocolStringList jvmPackageNameList = from.getJvmPackageNameList();
                                Intrinsics.checkNotNullExpressionValue(jvmPackageNameList, "getJvmPackageNameList(...)");
                                str = (String) CollectionsKt.getOrNull(jvmPackageNameList, iIntValue);
                                if (str == null) {
                                    Intrinsics.checkNotNull(str3);
                                    String strInternalNameOf2 = ModuleMappingKt.internalNameOf(str, str3);
                                    List<Integer> classWithJvmPackageNameMultifileFacadeShortNameIdList = packageParts.getClassWithJvmPackageNameMultifileFacadeShortNameIdList();
                                    Intrinsics.checkNotNullExpressionValue(classWithJvmPackageNameMultifileFacadeShortNameIdList, "getClassWithJvmPackageNa…acadeShortNameIdList(...)");
                                    ProtocolStringList multifileFacadeShortNameList2 = packageParts.getMultifileFacadeShortNameList();
                                    Intrinsics.checkNotNullExpressionValue(multifileFacadeShortNameList2, "getMultifileFacadeShortNameList(...)");
                                    packageParts3.addPart(strInternalNameOf2, loadMultiFileFacadeInternalName(classWithJvmPackageNameMultifileFacadeShortNameIdList, multifileFacadeShortNameList2, i2, str));
                                }
                            }
                        } else {
                            int iIntValue2 = num.intValue();
                            ProtocolStringList jvmPackageNameList2 = from.getJvmPackageNameList();
                            Intrinsics.checkNotNullExpressionValue(jvmPackageNameList2, "getJvmPackageNameList(...)");
                            str = (String) CollectionsKt.getOrNull(jvmPackageNameList2, iIntValue2);
                            if (str == null) {
                                Intrinsics.checkNotNull(str3);
                                String strInternalNameOf3 = ModuleMappingKt.internalNameOf(str, str3);
                                List<Integer> classWithJvmPackageNameMultifileFacadeShortNameIdList2 = packageParts.getClassWithJvmPackageNameMultifileFacadeShortNameIdList();
                                Intrinsics.checkNotNullExpressionValue(classWithJvmPackageNameMultifileFacadeShortNameIdList2, "getClassWithJvmPackageNa…acadeShortNameIdList(...)");
                                ProtocolStringList multifileFacadeShortNameList3 = packageParts.getMultifileFacadeShortNameList();
                                Intrinsics.checkNotNullExpressionValue(multifileFacadeShortNameList3, "getMultifileFacadeShortNameList(...)");
                                packageParts3.addPart(strInternalNameOf3, loadMultiFileFacadeInternalName(classWithJvmPackageNameMultifileFacadeShortNameIdList2, multifileFacadeShortNameList3, i2, str));
                            }
                        }
                        i2 = i3;
                    }
                }
            }
            for (JvmModuleProtoBuf.PackageParts packageParts4 : from.getMetadataPartsList()) {
                LinkedHashMap linkedHashMap3 = linkedHashMap;
                String packageFqName2 = packageParts4.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName2, "getPackageFqName(...)");
                Object packageParts5 = linkedHashMap3.get(packageFqName2);
                if (packageParts5 == null) {
                    String packageFqName3 = packageParts4.getPackageFqName();
                    Intrinsics.checkNotNullExpressionValue(packageFqName3, "getPackageFqName(...)");
                    packageParts5 = new PackageParts(packageFqName3);
                    linkedHashMap3.put(packageFqName2, packageParts5);
                }
                PackageParts packageParts6 = (PackageParts) packageParts5;
                ProtocolStringList shortClassNameList = packageParts4.getShortClassNameList();
                Intrinsics.checkNotNullExpressionValue(shortClassNameList, "getShortClassNameList(...)");
                Iterator<String> it = shortClassNameList.iterator();
                while (it.hasNext()) {
                    packageParts6.addMetadataPart(it.next());
                }
            }
            ProtoBuf.StringTable stringTable = from.getStringTable();
            Intrinsics.checkNotNullExpressionValue(stringTable, "getStringTable(...)");
            ProtoBuf.QualifiedNameTable qualifiedNameTable = from.getQualifiedNameTable();
            Intrinsics.checkNotNullExpressionValue(qualifiedNameTable, "getQualifiedNameTable(...)");
            NameResolverImpl nameResolverImpl = new NameResolverImpl(stringTable, qualifiedNameTable);
            List<ProtoBuf.Annotation> annotationList = from.getAnnotationList();
            Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
            List<ProtoBuf.Annotation> list = annotationList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                arrayList.add(nameResolverImpl.getQualifiedClassName(((ProtoBuf.Annotation) it2.next()).getId()));
            }
            List<ProtoBuf.Class> optionalAnnotationClassList = from.getOptionalAnnotationClassList();
            Intrinsics.checkNotNullExpressionValue(optionalAnnotationClassList, "getOptionalAnnotationClassList(...)");
            return new ModuleMapping(jvmMetadataVersion2, linkedHashMap, new BinaryModuleData(arrayList, optionalAnnotationClassList, nameResolverImpl), debugName, null);
        }

        private final String loadMultiFileFacadeInternalName(List<Integer> multifileFacadeIds, List<String> multifileFacadeShortNames, int index, String packageFqName) {
            Integer num = (Integer) CollectionsKt.getOrNull(multifileFacadeIds, index);
            Integer numValueOf = num != null ? Integer.valueOf(num.intValue() - 1) : null;
            String str = numValueOf != null ? (String) CollectionsKt.getOrNull(multifileFacadeShortNames, numValueOf.intValue()) : null;
            if (str != null) {
                return ModuleMappingKt.internalNameOf(packageFqName, str);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final BinaryModuleData emptyBinaryData() {
            List listEmptyList = CollectionsKt.emptyList();
            List listEmptyList2 = CollectionsKt.emptyList();
            ProtoBuf.StringTable defaultInstance = ProtoBuf.StringTable.getDefaultInstance();
            Intrinsics.checkNotNullExpressionValue(defaultInstance, "getDefaultInstance(...)");
            ProtoBuf.QualifiedNameTable defaultInstance2 = ProtoBuf.QualifiedNameTable.getDefaultInstance();
            Intrinsics.checkNotNullExpressionValue(defaultInstance2, "getDefaultInstance(...)");
            return new BinaryModuleData(listEmptyList, listEmptyList2, new NameResolverImpl(defaultInstance, defaultInstance2));
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        EMPTY = new ModuleMapping(JvmMetadataVersion.INSTANCE, MapsKt.emptyMap(), companion.emptyBinaryData(), "EMPTY");
        CORRUPTED = new ModuleMapping(JvmMetadataVersion.INSTANCE, MapsKt.emptyMap(), companion.emptyBinaryData(), "CORRUPTED");
    }
}
