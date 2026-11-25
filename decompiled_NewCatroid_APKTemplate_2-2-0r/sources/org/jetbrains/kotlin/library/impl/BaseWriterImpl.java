package org.jetbrains.kotlin.library.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.ZipUtilKt;
import org.jetbrains.kotlin.konan.properties.PropertiesKt;
import org.jetbrains.kotlin.library.BaseWriter;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.KotlinLibraryVersioningKt;

/* JADX INFO: compiled from: KotlinLibraryWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u000fJ\u0016\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u000bH\u0016J\u0014\u0010&\u001a\u00020#2\n\u0010'\u001a\u00060\u0017j\u0002`\u0018H\u0016J\b\u0010(\u001a\u00020#H\u0016R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0015\u0010\u0016\u001a\u00060\u0017j\u0002`\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/BaseWriterImpl;", "Lorg/jetbrains/kotlin/library/BaseWriter;", "libraryLayout", "Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutForWriter;", "moduleName", "", "_versions", "Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "builtInsPlatform", "Lorg/jetbrains/kotlin/library/impl/BuiltInsPlatform;", "nativeTargets", "", "nopack", "", "shortName", "(Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutForWriter;Ljava/lang/String;Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;Lorg/jetbrains/kotlin/library/impl/BuiltInsPlatform;Ljava/util/List;ZLjava/lang/String;)V", "klibFile", "Lorg/jetbrains/kotlin/konan/file/File;", "getKlibFile", "()Lorg/jetbrains/kotlin/konan/file/File;", "getLibraryLayout", "()Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutForWriter;", "manifestProperties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "getManifestProperties", "()Ljava/util/Properties;", "getNopack", "()Z", "getShortName", "()Ljava/lang/String;", "versions", "getVersions", "()Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "addLinkDependencies", "", "libraries", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "addManifestAddend", "properties", "commit", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class BaseWriterImpl implements BaseWriter {
    private final File klibFile;
    private final KotlinLibraryLayoutForWriter libraryLayout;
    private final Properties manifestProperties;
    private final boolean nopack;
    private final String shortName;
    private final KotlinLibraryVersioning versions;

    public BaseWriterImpl(KotlinLibraryLayoutForWriter libraryLayout, String moduleName, KotlinLibraryVersioning _versions, BuiltInsPlatform builtInsPlatform, List<String> nativeTargets, boolean z, String str) throws IOException {
        Intrinsics.checkNotNullParameter(libraryLayout, "libraryLayout");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(_versions, "_versions");
        Intrinsics.checkNotNullParameter(builtInsPlatform, "builtInsPlatform");
        Intrinsics.checkNotNullParameter(nativeTargets, "nativeTargets");
        this.libraryLayout = libraryLayout;
        this.nopack = z;
        this.shortName = str;
        File canonicalFile = libraryLayout.getLibFile().getCanonicalFile();
        this.klibFile = canonicalFile;
        Properties properties = new Properties();
        this.manifestProperties = properties;
        this.versions = _versions;
        canonicalFile.deleteRecursively();
        File parentFile = canonicalFile.getParentFile();
        if (!parentFile.getExists()) {
            parentFile.mkdirs();
        }
        libraryLayout.getResourcesDir().mkdirs();
        properties.setProperty(KotlinLibraryKt.KLIB_PROPERTY_UNIQUE_NAME, moduleName);
        KotlinLibraryVersioningKt.writeKonanLibraryVersioning(properties, _versions);
        if (builtInsPlatform != BuiltInsPlatform.COMMON) {
            properties.setProperty(KotlinLibraryKt.KLIB_PROPERTY_BUILTINS_PLATFORM, builtInsPlatform.name());
            if (builtInsPlatform == BuiltInsPlatform.NATIVE) {
                properties.setProperty(KotlinLibraryKt.KLIB_PROPERTY_NATIVE_TARGETS, CollectionsKt.joinToString$default(nativeTargets, " ", null, null, 0, null, null, 62, null));
            }
        }
        if (str != null) {
            properties.setProperty(KotlinLibraryKt.KLIB_PROPERTY_SHORT_NAME, str);
        }
    }

    public final KotlinLibraryLayoutForWriter getLibraryLayout() {
        return this.libraryLayout;
    }

    public /* synthetic */ BaseWriterImpl(KotlinLibraryLayoutForWriter kotlinLibraryLayoutForWriter, String str, KotlinLibraryVersioning kotlinLibraryVersioning, BuiltInsPlatform builtInsPlatform, List list, boolean z, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinLibraryLayoutForWriter, str, kotlinLibraryVersioning, builtInsPlatform, (i & 16) != 0 ? CollectionsKt.emptyList() : list, (i & 32) != 0 ? false : z, (i & 64) != 0 ? null : str2);
    }

    public final boolean getNopack() {
        return this.nopack;
    }

    public final String getShortName() {
        return this.shortName;
    }

    public final File getKlibFile() {
        return this.klibFile;
    }

    public final Properties getManifestProperties() {
        return this.manifestProperties;
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public KotlinLibraryVersioning getVersions() {
        return this.versions;
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public void addLinkDependencies(List<? extends KotlinLibrary> libraries) {
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        if (libraries.isEmpty()) {
            this.manifestProperties.remove(KotlinLibraryKt.KLIB_PROPERTY_DEPENDS);
            return;
        }
        List<? extends KotlinLibrary> list = libraries;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(KotlinLibraryKt.getUniqueName((KotlinLibrary) it.next()));
        }
        this.manifestProperties.setProperty(KotlinLibraryKt.KLIB_PROPERTY_DEPENDS, KotlinLibraryWriterImplKt.toSpaceSeparatedString(arrayList));
        for (KotlinLibrary kotlinLibrary : list) {
            if (kotlinLibrary.getVersions().getLibraryVersion() != null) {
                this.manifestProperties.setProperty("dependency_version_" + KotlinLibraryKt.getUniqueName(kotlinLibrary), kotlinLibrary.getVersions().getLibraryVersion());
            }
        }
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public void addManifestAddend(Properties properties) {
        Intrinsics.checkNotNullParameter(properties, "properties");
        this.manifestProperties.putAll(properties);
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public void commit() throws Exception {
        PropertiesKt.saveToFile(this.manifestProperties, this.libraryLayout.getManifestFile());
        if (this.nopack) {
            return;
        }
        ZipUtilKt.zipDirAs(this.libraryLayout.getUnzippedDir(), this.klibFile);
        this.libraryLayout.getUnzippedDir().deleteRecursively();
    }
}
