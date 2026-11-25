package org.jetbrains.kotlin.backend.common.serialization.metadata;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.KlibMetadataMetadataVersionKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataVersion;

/* JADX INFO: compiled from: Deprecated.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\"!\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006*8\b\u0007\u0010\u0007\"\u00020\b2\u00020\bB*\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u001c\b\f\u0012\u0018\b\u000bB\u0014\b\r\u0012\u0006\b\u000e\u0012\u0002\b\f\u0012\b\b\u000f\u0012\u0004\b\b(\u0010*8\b\u0007\u0010\u0011\"\u00020\u00122\u00020\u0012B*\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u001c\b\f\u0012\u0018\b\u000bB\u0014\b\r\u0012\u0006\b\u000e\u0012\u0002\b\f\u0012\b\b\u000f\u0012\u0004\b\b(\u0013*8\b\u0007\u0010\u0014\"\u00020\u00012\u00020\u0001B*\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0015\u0012\u001c\b\f\u0012\u0018\b\u000bB\u0014\b\r\u0012\u0006\b\u000e\u0012\u0002\b\f\u0012\b\b\u000f\u0012\u0004\b\b(\u0016¨\u0006\u0017"}, d2 = {"metadataVersion", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataVersion;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "getMetadataVersion$annotations", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)V", "getMetadataVersion", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)Lorg/jetbrains/kotlin/library/metadata/KlibMetadataVersion;", "KlibMetadataDeserializedPackageFragmentsFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataDeserializedPackageFragmentsFactory;", "Lkotlin/Deprecated;", CrashHianalyticsData.MESSAGE, "This interface has been moved from package org.jetbrains.kotlin.backend.common.serialization.metadata to package org.jetbrains.kotlin.library.metadata", "replaceWith", "Lkotlin/ReplaceWith;", "imports", "expression", "org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializedPackageFragmentsFactory", "KlibMetadataModuleDescriptorFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataModuleDescriptorFactory;", "org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory", "KlibMetadataVersion", "This class has been moved from package org.jetbrains.kotlin.backend.common.serialization.metadata to package org.jetbrains.kotlin.library.metadata", "org.jetbrains.kotlin.library.metadata.KlibMetadataVersion", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeprecatedKt {
    @Deprecated(message = "This interface has been moved from package org.jetbrains.kotlin.backend.common.serialization.metadata to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializedPackageFragmentsFactory", imports = {}))
    public static /* synthetic */ void KlibMetadataDeserializedPackageFragmentsFactory$annotations() {
    }

    @Deprecated(message = "This interface has been moved from package org.jetbrains.kotlin.backend.common.serialization.metadata to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory", imports = {}))
    public static /* synthetic */ void KlibMetadataModuleDescriptorFactory$annotations() {
    }

    @Deprecated(message = "This class has been moved from package org.jetbrains.kotlin.backend.common.serialization.metadata to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.KlibMetadataVersion", imports = {}))
    public static /* synthetic */ void KlibMetadataVersion$annotations() {
    }

    @Deprecated(message = "This property has been moved from package org.jetbrains.kotlin.backend.common.serialization.metadata to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "metadataVersion", imports = {"org.jetbrains.kotlin.library.metadata.metadataVersion"}))
    public static /* synthetic */ void getMetadataVersion$annotations(KotlinLibrary kotlinLibrary) {
    }

    public static final KlibMetadataVersion getMetadataVersion(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        return KlibMetadataMetadataVersionKt.getMetadataVersion(kotlinLibrary);
    }
}
