package org.jetbrains.kotlin.konan.library.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.FileKt;
import org.jetbrains.kotlin.konan.library.KonanLibrary;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.library.impl.BaseKotlinLibraryImpl;
import org.jetbrains.kotlin.library.impl.BaseLibraryAccess;
import org.jetbrains.kotlin.library.impl.IrLibraryAccess;
import org.jetbrains.kotlin.library.impl.IrMonoliticLibraryImpl;
import org.jetbrains.kotlin.library.impl.MetadataLibraryAccess;
import org.jetbrains.kotlin.library.impl.MetadataLibraryImpl;

/* JADX INFO: compiled from: KonanLibraryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a*\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u0006\u0010\f\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t¨\u0006\r"}, d2 = {"createKonanLibrary", "Lorg/jetbrains/kotlin/konan/library/KonanLibrary;", "libraryFilePossiblyDenormalized", "Lorg/jetbrains/kotlin/konan/file/File;", "component", "", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "isDefault", "", "createKonanLibraryComponents", "", "libraryFile", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class KonanLibraryImplKt {
    public static /* synthetic */ KonanLibrary createKonanLibrary$default(File file, String str, KonanTarget konanTarget, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            konanTarget = null;
        }
        if ((i & 8) != 0) {
            z = false;
        }
        return createKonanLibrary(file, str, konanTarget, z);
    }

    public static final KonanLibrary createKonanLibrary(File libraryFilePossiblyDenormalized, String component, KonanTarget konanTarget, boolean z) {
        Intrinsics.checkNotNullParameter(libraryFilePossiblyDenormalized, "libraryFilePossiblyDenormalized");
        Intrinsics.checkNotNullParameter(component, "component");
        Path pathNormalize = Paths.get(libraryFilePossiblyDenormalized.getAbsolutePath(), new String[0]).normalize();
        Intrinsics.checkNotNullExpressionValue(pathNormalize, "get(libraryFilePossiblyD…absolutePath).normalize()");
        File File = FileKt.File(pathNormalize);
        BaseLibraryAccess baseLibraryAccess = new BaseLibraryAccess(File, component, null, 4, null);
        TargetedLibraryAccess targetedLibraryAccess = new TargetedLibraryAccess(File, component, konanTarget);
        MetadataLibraryAccess metadataLibraryAccess = new MetadataLibraryAccess(File, component, null, 4, null);
        IrLibraryAccess irLibraryAccess = new IrLibraryAccess(File, component, null, 4, null);
        BitcodeLibraryAccess bitcodeLibraryAccess = new BitcodeLibraryAccess(File, component, konanTarget);
        TargetedLibraryImpl targetedLibraryImpl = new TargetedLibraryImpl(targetedLibraryAccess, new BaseKotlinLibraryImpl(baseLibraryAccess, z));
        return new KonanLibraryImpl(targetedLibraryImpl, new MetadataLibraryImpl(metadataLibraryAccess), new IrMonoliticLibraryImpl(irLibraryAccess), new BitcodeLibraryImpl(bitcodeLibraryAccess, targetedLibraryImpl));
    }

    public static /* synthetic */ List createKonanLibraryComponents$default(File file, KonanTarget konanTarget, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            konanTarget = null;
        }
        if ((i & 4) != 0) {
            z = true;
        }
        return createKonanLibraryComponents(file, konanTarget, z);
    }

    public static final List<KonanLibrary> createKonanLibraryComponents(File libraryFile, KonanTarget konanTarget, boolean z) {
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        List<String> componentList = new BaseKotlinLibraryImpl(new BaseLibraryAccess(libraryFile, null, null, 4, null), z).getComponentList();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(componentList, 10));
        Iterator<T> it = componentList.iterator();
        while (it.hasNext()) {
            arrayList.add(createKonanLibrary(libraryFile, (String) it.next(), konanTarget, z));
        }
        return arrayList;
    }
}
