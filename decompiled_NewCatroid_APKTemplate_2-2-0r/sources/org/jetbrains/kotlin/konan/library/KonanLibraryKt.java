package org.jetbrains.kotlin.konan.library;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.properties.PropertiesKt;

/* JADX INFO: compiled from: KonanLibrary.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u001b\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"KLIB_PROPERTY_INCLUDED_HEADERS", "", "KLIB_PROPERTY_LINKED_OPTS", KonanLibraryKt.KLIB_PROPERTY_INCLUDED_HEADERS, "", "Lorg/jetbrains/kotlin/konan/library/KonanLibrary;", "getIncludedHeaders", "(Lorg/jetbrains/kotlin/konan/library/KonanLibrary;)Ljava/util/List;", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class KonanLibraryKt {
    public static final String KLIB_PROPERTY_INCLUDED_HEADERS = "includedHeaders";
    public static final String KLIB_PROPERTY_LINKED_OPTS = "linkerOpts";

    public static final List<String> getIncludedHeaders(KonanLibrary konanLibrary) {
        Intrinsics.checkNotNullParameter(konanLibrary, "<this>");
        return PropertiesKt.propertyList$default(konanLibrary.getManifestProperties(), KLIB_PROPERTY_INCLUDED_HEADERS, null, true, 2, null);
    }
}
