package org.jetbrains.kotlin.descriptors.konan;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.KlibModuleOrigin;
import org.jetbrains.kotlin.library.metadata.KlibModuleOriginKt;

/* JADX INFO: compiled from: Deprecated.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u001f\u0010\u0007\u001a\u00020\b*\u00020\u00028Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u000b*8\b\u0007\u0010\f\"\u00020\r2\u00020\rB*\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u001c\b\u0011\u0012\u0018\b\u000bB\u0014\b\u0012\u0012\u0006\b\u0013\u0012\u0002\b\f\u0012\b\b\u0014\u0012\u0004\b\b(\u0015*8\b\u0007\u0010\u0016\"\u00020\u00172\u00020\u0017B*\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0018\u0012\u001c\b\u0011\u0012\u0018\b\u000bB\u0014\b\u0012\u0012\u0006\b\u0013\u0012\u0002\b\f\u0012\b\b\u0014\u0012\u0004\b\b(\u0019*8\b\u0007\u0010\u001a\"\u00020\u001b2\u00020\u001bB*\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u001c\b\u0011\u0012\u0018\b\u000bB\u0014\b\u0012\u0012\u0006\b\u0013\u0012\u0002\b\f\u0012\b\b\u0014\u0012\u0004\b\b(\u001c*8\b\u0007\u0010\u001d\"\u00020\u001e2\u00020\u001eB*\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u001f\u0012\u001c\b\u0011\u0012\u0018\b\u000bB\u0014\b\u0012\u0012\u0006\b\u0013\u0012\u0002\b\f\u0012\b\b\u0014\u0012\u0004\b\b( *8\b\u0007\u0010!\"\u00020\u00012\u00020\u0001B*\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u001c\b\u0011\u0012\u0018\b\u000bB\u0014\b\u0012\u0012\u0006\b\u0013\u0012\u0002\b\f\u0012\b\b\u0014\u0012\u0004\b\b(\"*8\b\u0007\u0010#\"\u00020$2\u00020$B*\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0018\u0012\u001c\b\u0011\u0012\u0018\b\u000bB\u0014\b\u0012\u0012\u0006\b\u0013\u0012\u0002\b\f\u0012\b\b\u0014\u0012\u0004\b\b(%¨\u0006&"}, d2 = {"klibModuleOrigin", "Lorg/jetbrains/kotlin/library/metadata/KlibModuleOrigin;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getKlibModuleOrigin$annotations", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "getKlibModuleOrigin", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Lorg/jetbrains/kotlin/library/metadata/KlibModuleOrigin;", "kotlinLibrary", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "getKotlinLibrary$annotations", "getKotlinLibrary", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Lorg/jetbrains/kotlin/library/KotlinLibrary;", "CompiledKlibModuleOrigin", "Lorg/jetbrains/kotlin/library/metadata/CompiledKlibModuleOrigin;", "Lkotlin/Deprecated;", CrashHianalyticsData.MESSAGE, "This class has been moved from package org.jetbrains.kotlin.descriptors.konan to package org.jetbrains.kotlin.library.metadata", "replaceWith", "Lkotlin/ReplaceWith;", "imports", "expression", "org.jetbrains.kotlin.library.metadata.CompiledKlibModuleOrigin", "CurrentKlibModuleOrigin", "Lorg/jetbrains/kotlin/library/metadata/CurrentKlibModuleOrigin;", "This object has been moved from package org.jetbrains.kotlin.descriptors.konan to package org.jetbrains.kotlin.library.metadata", "org.jetbrains.kotlin.library.metadata.CurrentKlibModuleOrigin", "DeserializedKlibModuleOrigin", "Lorg/jetbrains/kotlin/library/metadata/DeserializedKlibModuleOrigin;", "org.jetbrains.kotlin.library.metadata.DeserializedKlibModuleOrigin", "KlibModuleDescriptorFactory", "Lorg/jetbrains/kotlin/library/metadata/KlibModuleDescriptorFactory;", "This interface has been moved from package org.jetbrains.kotlin.descriptors.konan to package org.jetbrains.kotlin.library.metadata", "org.jetbrains.kotlin.library.metadata.KlibModuleDescriptorFactory", "KlibModuleOrigin", "org.jetbrains.kotlin.library.metadata.KlibModuleOrigin", "SyntheticModulesOrigin", "Lorg/jetbrains/kotlin/library/metadata/SyntheticModulesOrigin;", "org.jetbrains.kotlin.library.metadata.SyntheticModulesOrigin", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeprecatedKt {
    @Deprecated(message = "This class has been moved from package org.jetbrains.kotlin.descriptors.konan to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.CompiledKlibModuleOrigin", imports = {}))
    public static /* synthetic */ void CompiledKlibModuleOrigin$annotations() {
    }

    @Deprecated(message = "This object has been moved from package org.jetbrains.kotlin.descriptors.konan to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.CurrentKlibModuleOrigin", imports = {}))
    public static /* synthetic */ void CurrentKlibModuleOrigin$annotations() {
    }

    @Deprecated(message = "This class has been moved from package org.jetbrains.kotlin.descriptors.konan to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.DeserializedKlibModuleOrigin", imports = {}))
    public static /* synthetic */ void DeserializedKlibModuleOrigin$annotations() {
    }

    @Deprecated(message = "This interface has been moved from package org.jetbrains.kotlin.descriptors.konan to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.KlibModuleDescriptorFactory", imports = {}))
    public static /* synthetic */ void KlibModuleDescriptorFactory$annotations() {
    }

    @Deprecated(message = "This class has been moved from package org.jetbrains.kotlin.descriptors.konan to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.KlibModuleOrigin", imports = {}))
    public static /* synthetic */ void KlibModuleOrigin$annotations() {
    }

    @Deprecated(message = "This object has been moved from package org.jetbrains.kotlin.descriptors.konan to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.library.metadata.SyntheticModulesOrigin", imports = {}))
    public static /* synthetic */ void SyntheticModulesOrigin$annotations() {
    }

    @Deprecated(message = "This property has been moved from package org.jetbrains.kotlin.descriptors.konan to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "klibModuleOrigin", imports = {"org.jetbrains.kotlin.library.metadata.klibModuleOrigin"}))
    public static /* synthetic */ void getKlibModuleOrigin$annotations(ModuleDescriptor moduleDescriptor) {
    }

    @Deprecated(message = "This property has been moved from package org.jetbrains.kotlin.descriptors.konan to package org.jetbrains.kotlin.library.metadata", replaceWith = @ReplaceWith(expression = "kotlinLibrary", imports = {"org.jetbrains.kotlin.library.metadata.kotlinLibrary"}))
    public static /* synthetic */ void getKotlinLibrary$annotations(ModuleDescriptor moduleDescriptor) {
    }

    public static final KlibModuleOrigin getKlibModuleOrigin(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return KlibModuleOriginKt.getKlibModuleOrigin(moduleDescriptor);
    }

    public static final KotlinLibrary getKotlinLibrary(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return KlibModuleOriginKt.getKotlinLibrary(moduleDescriptor);
    }
}
