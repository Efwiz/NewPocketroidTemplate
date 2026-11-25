package org.jetbrains.kotlin.library.abi.impl;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.jetbrains.kotlin.library.KotlinIrSignatureVersion;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.SingleFileResolveKt;
import org.jetbrains.kotlin.library.ToolingSingleFileKlibResolveStrategy;
import org.jetbrains.kotlin.library.abi.AbiReadingFilter;
import org.jetbrains.kotlin.library.abi.AbiSignatureVersion;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;
import org.jetbrains.kotlin.library.abi.LibraryAbi;
import org.jetbrains.kotlin.library.abi.LibraryManifest;

/* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\b\u0001\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0006\u0010\f\u001a\u00020\rJ\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryAbiReaderImpl;", "", "libraryFile", "Ljava/io/File;", "filters", "", "Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter;", "(Ljava/io/File;Ljava/util/List;)V", "compositeFilter", "Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter$Composite;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "readAbi", "Lorg/jetbrains/kotlin/library/abi/LibraryAbi;", "readManifest", "Lorg/jetbrains/kotlin/library/abi/LibraryManifest;", "readSupportedSignatureVersions", "", "Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class LibraryAbiReaderImpl {
    private final AbiReadingFilter.Composite compositeFilter;
    private final KotlinLibrary library;

    public LibraryAbiReaderImpl(File libraryFile, List<? extends AbiReadingFilter> filters) {
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        Intrinsics.checkNotNullParameter(filters, "filters");
        String absolutePath = libraryFile.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        this.library = SingleFileResolveKt.resolveSingleFileKlib$default(new org.jetbrains.kotlin.konan.file.File(absolutePath), null, ToolingSingleFileKlibResolveStrategy.INSTANCE, 2, null);
        this.compositeFilter = filters.isEmpty() ? null : new AbiReadingFilter.Composite(filters);
    }

    public final LibraryAbi readAbi() {
        Set<AbiSignatureVersion> supportedSignatureVersions = readSupportedSignatureVersions();
        return new LibraryAbi(readManifest(), KotlinLibraryKt.getUniqueName(this.library), supportedSignatureVersions, new LibraryDeserializer(this.library, supportedSignatureVersions, this.compositeFilter).deserialize());
    }

    private final LibraryManifest readManifest() {
        KotlinLibraryVersioning versions = this.library.getVersions();
        String builtInsPlatform = KotlinLibraryKt.getBuiltInsPlatform(this.library);
        List listSorted = CollectionsKt.sorted(KotlinLibraryKt.getNativeTargets(this.library));
        String compilerVersion = versions.getCompilerVersion();
        KotlinAbiVersion abiVersion = versions.getAbiVersion();
        return new LibraryManifest(builtInsPlatform, listSorted, compilerVersion, abiVersion != null ? abiVersion.toString() : null, versions.getLibraryVersion(), KotlinLibraryKt.getIrProviderName(this.library));
    }

    private final Set<AbiSignatureVersion> readSupportedSignatureVersions() {
        Set<KotlinIrSignatureVersion> irSignatureVersions = this.library.getVersions().getIrSignatureVersions();
        HashSet hashSet = new HashSet();
        Iterator<T> it = irSignatureVersions.iterator();
        while (it.hasNext()) {
            hashSet.add(AbiSignatureVersions.INSTANCE.resolveByVersionNumber(((KotlinIrSignatureVersion) it.next()).getNumber()));
        }
        return hashSet;
    }
}
