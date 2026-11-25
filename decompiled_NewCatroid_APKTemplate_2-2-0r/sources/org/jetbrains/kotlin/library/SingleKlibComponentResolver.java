package org.jetbrains.kotlin.library;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.impl.KotlinLibraryImplKt;
import org.jetbrains.kotlin.util.Logger;

/* JADX INFO: compiled from: SearchPathResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b¢\u0006\u0002\u0010\tJ\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/library/SingleKlibComponentResolver;", "Lorg/jetbrains/kotlin/library/KotlinLibraryProperResolverWithAttributes;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "klibFile", "", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "knownIrProviders", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/util/Logger;Ljava/util/List;)V", "libraryComponentBuilder", "file", "Lorg/jetbrains/kotlin/konan/file/File;", "isDefault", "", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SingleKlibComponentResolver extends KotlinLibraryProperResolverWithAttributes<KotlinLibrary> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SingleKlibComponentResolver(String klibFile, Logger logger, List<String> knownIrProviders) {
        super(CollectionsKt.emptyList(), CollectionsKt.listOf(klibFile), null, null, false, logger, knownIrProviders);
        Intrinsics.checkNotNullParameter(klibFile, "klibFile");
        Intrinsics.checkNotNullParameter(logger, "logger");
        Intrinsics.checkNotNullParameter(knownIrProviders, "knownIrProviders");
    }

    @Override // org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver
    public List<KotlinLibrary> libraryComponentBuilder(File file, boolean isDefault) {
        Intrinsics.checkNotNullParameter(file, "file");
        return KotlinLibraryImplKt.createKotlinLibraryComponents$default(file, isDefault, null, 4, null);
    }
}
