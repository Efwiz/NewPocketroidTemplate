package org.jetbrains.kotlin.library;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.properties.PropertiesKt;

/* JADX INFO: compiled from: KotlinLibrary.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u00106\u001a\b\u0012\u0004\u0012\u0002090\u0019*\u00020\u001a2\b\b\u0002\u0010:\u001a\u00020 \"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u0017\u0010\u0014\u001a\u0004\u0018\u00010\u0001*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017\"\u001d\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0019*\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c\"\u0017\u0010\u001d\u001a\u0004\u0018\u00010\u0001*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0017\"\u0015\u0010\u001f\u001a\u00020 *\u00020\u00158F¢\u0006\u0006\u001a\u0004\b!\u0010\"\"\u001b\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\u0019*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b$\u0010%\"\u001b\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00010\u0019*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b'\u0010%\"\u0017\u0010(\u001a\u0004\u0018\u00010\u0001*\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b)\u0010*\"\u0015\u0010+\u001a\u00020 *\u00020\u00158F¢\u0006\u0006\u001a\u0004\b+\u0010\"\"\u0015\u0010,\u001a\u00020 *\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b,\u0010-\"\u001b\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00010\u0019*\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b/\u0010\u001c\"\u0017\u00100\u001a\u0004\u0018\u00010\u0001*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b1\u0010\u0017\"\u0017\u00102\u001a\u0004\u0018\u00010\u0001*\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b3\u0010*\"\u0015\u00104\u001a\u00020\u0001*\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b5\u0010*\"\u001b\u00106\u001a\b\u0012\u0004\u0012\u0002070\u0019*\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b8\u0010\u001c¨\u0006;"}, d2 = {"KLIB_PROPERTY_ABI_VERSION", "", "KLIB_PROPERTY_BUILTINS_PLATFORM", "KLIB_PROPERTY_COMMONIZER_NATIVE_TARGETS", "KLIB_PROPERTY_COMMONIZER_TARGET", "KLIB_PROPERTY_COMPILER_VERSION", "KLIB_PROPERTY_CONTAINS_ERROR_CODE", "KLIB_PROPERTY_DEPENDENCY_VERSION", "KLIB_PROPERTY_DEPENDS", "KLIB_PROPERTY_EXPORT_FORWARD_DECLARATIONS", "KLIB_PROPERTY_INCLUDED_FORWARD_DECLARATIONS", "KLIB_PROPERTY_INTEROP", "KLIB_PROPERTY_IR_PROVIDER", "KLIB_PROPERTY_IR_SIGNATURE_VERSIONS", "KLIB_PROPERTY_LIBRARY_VERSION", "KLIB_PROPERTY_METADATA_VERSION", "KLIB_PROPERTY_NATIVE_TARGETS", "KLIB_PROPERTY_PACKAGE", "KLIB_PROPERTY_SHORT_NAME", "KLIB_PROPERTY_UNIQUE_NAME", "builtInsPlatform", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "getBuiltInsPlatform", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)Ljava/lang/String;", "commonizerNativeTargets", "", "Lorg/jetbrains/kotlin/library/BaseKotlinLibrary;", "getCommonizerNativeTargets", "(Lorg/jetbrains/kotlin/library/BaseKotlinLibrary;)Ljava/util/List;", "commonizerTarget", "getCommonizerTarget", "containsErrorCode", "", "getContainsErrorCode", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)Z", KotlinLibraryKt.KLIB_PROPERTY_EXPORT_FORWARD_DECLARATIONS, "getExportForwardDeclarations", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)Ljava/util/List;", KotlinLibraryKt.KLIB_PROPERTY_INCLUDED_FORWARD_DECLARATIONS, "getIncludedForwardDeclarations", "irProviderName", "getIrProviderName", "(Lorg/jetbrains/kotlin/library/BaseKotlinLibrary;)Ljava/lang/String;", "isInterop", "isNativeStdlib", "(Lorg/jetbrains/kotlin/library/BaseKotlinLibrary;)Z", "nativeTargets", "getNativeTargets", "packageFqName", "getPackageFqName", "shortName", "getShortName", "uniqueName", "getUniqueName", "unresolvedDependencies", "Lorg/jetbrains/kotlin/library/RequiredUnresolvedLibrary;", "getUnresolvedDependencies", "Lorg/jetbrains/kotlin/library/UnresolvedLibrary;", "lenient", "kotlin-util-klib"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class KotlinLibraryKt {
    public static final String KLIB_PROPERTY_ABI_VERSION = "abi_version";
    public static final String KLIB_PROPERTY_BUILTINS_PLATFORM = "builtins_platform";
    public static final String KLIB_PROPERTY_COMMONIZER_NATIVE_TARGETS = "commonizer_native_targets";
    public static final String KLIB_PROPERTY_COMMONIZER_TARGET = "commonizer_target";
    public static final String KLIB_PROPERTY_COMPILER_VERSION = "compiler_version";
    public static final String KLIB_PROPERTY_CONTAINS_ERROR_CODE = "contains_error_code";
    public static final String KLIB_PROPERTY_DEPENDENCY_VERSION = "dependency_version";
    public static final String KLIB_PROPERTY_DEPENDS = "depends";
    public static final String KLIB_PROPERTY_EXPORT_FORWARD_DECLARATIONS = "exportForwardDeclarations";
    public static final String KLIB_PROPERTY_INCLUDED_FORWARD_DECLARATIONS = "includedForwardDeclarations";
    public static final String KLIB_PROPERTY_INTEROP = "interop";
    public static final String KLIB_PROPERTY_IR_PROVIDER = "ir_provider";
    public static final String KLIB_PROPERTY_IR_SIGNATURE_VERSIONS = "ir_signature_versions";
    public static final String KLIB_PROPERTY_LIBRARY_VERSION = "library_version";
    public static final String KLIB_PROPERTY_METADATA_VERSION = "metadata_version";
    public static final String KLIB_PROPERTY_NATIVE_TARGETS = "native_targets";
    public static final String KLIB_PROPERTY_PACKAGE = "package";
    public static final String KLIB_PROPERTY_SHORT_NAME = "short_name";
    public static final String KLIB_PROPERTY_UNIQUE_NAME = "unique_name";

    public static final String getUniqueName(BaseKotlinLibrary baseKotlinLibrary) {
        Intrinsics.checkNotNullParameter(baseKotlinLibrary, "<this>");
        String property = baseKotlinLibrary.getManifestProperties().getProperty(KLIB_PROPERTY_UNIQUE_NAME);
        Intrinsics.checkNotNull(property);
        return property;
    }

    public static final String getShortName(BaseKotlinLibrary baseKotlinLibrary) {
        Intrinsics.checkNotNullParameter(baseKotlinLibrary, "<this>");
        return baseKotlinLibrary.getManifestProperties().getProperty(KLIB_PROPERTY_SHORT_NAME);
    }

    public static final boolean isNativeStdlib(BaseKotlinLibrary baseKotlinLibrary) {
        Intrinsics.checkNotNullParameter(baseKotlinLibrary, "<this>");
        return Intrinsics.areEqual(getUniqueName(baseKotlinLibrary), "stdlib");
    }

    public static final List<RequiredUnresolvedLibrary> getUnresolvedDependencies(BaseKotlinLibrary baseKotlinLibrary) {
        Intrinsics.checkNotNullParameter(baseKotlinLibrary, "<this>");
        List<UnresolvedLibrary> listUnresolvedDependencies = unresolvedDependencies(baseKotlinLibrary, false);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listUnresolvedDependencies, 10));
        for (UnresolvedLibrary unresolvedLibrary : listUnresolvedDependencies) {
            Intrinsics.checkNotNull(unresolvedLibrary, "null cannot be cast to non-null type org.jetbrains.kotlin.library.RequiredUnresolvedLibrary");
            arrayList.add((RequiredUnresolvedLibrary) unresolvedLibrary);
        }
        return arrayList;
    }

    public static /* synthetic */ List unresolvedDependencies$default(BaseKotlinLibrary baseKotlinLibrary, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return unresolvedDependencies(baseKotlinLibrary, z);
    }

    public static final List<UnresolvedLibrary> unresolvedDependencies(BaseKotlinLibrary baseKotlinLibrary, boolean z) {
        Intrinsics.checkNotNullParameter(baseKotlinLibrary, "<this>");
        List<String> listPropertyList$default = PropertiesKt.propertyList$default(baseKotlinLibrary.getManifestProperties(), KLIB_PROPERTY_DEPENDS, null, true, 2, null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPropertyList$default, 10));
        for (String str : listPropertyList$default) {
            arrayList.add(UnresolvedLibraryKt.UnresolvedLibrary(str, baseKotlinLibrary.getManifestProperties().getProperty("dependency_version_" + str), z));
        }
        return arrayList;
    }

    public static final boolean isInterop(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        return Intrinsics.areEqual(kotlinLibrary.getManifestProperties().getProperty(KLIB_PROPERTY_INTEROP), "true");
    }

    public static final String getPackageFqName(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        return kotlinLibrary.getManifestProperties().getProperty("package");
    }

    public static final List<String> getExportForwardDeclarations(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        return PropertiesKt.propertyList$default(kotlinLibrary.getManifestProperties(), KLIB_PROPERTY_EXPORT_FORWARD_DECLARATIONS, null, true, 2, null);
    }

    public static final List<String> getIncludedForwardDeclarations(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        return PropertiesKt.propertyList$default(kotlinLibrary.getManifestProperties(), KLIB_PROPERTY_INCLUDED_FORWARD_DECLARATIONS, null, true, 2, null);
    }

    public static final String getIrProviderName(BaseKotlinLibrary baseKotlinLibrary) {
        Intrinsics.checkNotNullParameter(baseKotlinLibrary, "<this>");
        return baseKotlinLibrary.getManifestProperties().getProperty(KLIB_PROPERTY_IR_PROVIDER);
    }

    public static final List<String> getNativeTargets(BaseKotlinLibrary baseKotlinLibrary) {
        Intrinsics.checkNotNullParameter(baseKotlinLibrary, "<this>");
        return PropertiesKt.propertyList$default(baseKotlinLibrary.getManifestProperties(), KLIB_PROPERTY_NATIVE_TARGETS, null, false, 6, null);
    }

    public static final boolean getContainsErrorCode(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        return Intrinsics.areEqual(kotlinLibrary.getManifestProperties().getProperty(KLIB_PROPERTY_CONTAINS_ERROR_CODE), "true");
    }

    public static final String getCommonizerTarget(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        return kotlinLibrary.getManifestProperties().getProperty(KLIB_PROPERTY_COMMONIZER_TARGET);
    }

    public static final String getBuiltInsPlatform(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        return kotlinLibrary.getManifestProperties().getProperty(KLIB_PROPERTY_BUILTINS_PLATFORM);
    }

    public static final List<String> getCommonizerNativeTargets(BaseKotlinLibrary baseKotlinLibrary) {
        Intrinsics.checkNotNullParameter(baseKotlinLibrary, "<this>");
        if (baseKotlinLibrary.getManifestProperties().containsKey(KLIB_PROPERTY_COMMONIZER_NATIVE_TARGETS)) {
            return PropertiesKt.propertyList$default(baseKotlinLibrary.getManifestProperties(), KLIB_PROPERTY_COMMONIZER_NATIVE_TARGETS, null, true, 2, null);
        }
        return null;
    }
}
