package org.jetbrains.kotlin.com.intellij.ide.plugins;

import com.reandroid.apk.ApkUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader;
import org.jetbrains.kotlin.com.intellij.util.lang.ZipFilePool;

/* JADX INFO: compiled from: PluginXmlPathResolver.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J2\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0002J2\u0010\u0011\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J*\u0010\u0013\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u000f2\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J,\u0010\u0015\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginXmlPathResolver;", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PathResolver;", "pluginJarFiles", "", "Ljava/nio/file/Path;", "(Ljava/util/List;)V", "findInJarFiles", "", "readInto", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/RawPluginDescriptor;", "readContext", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ReadModuleContext;", "dataLoader", "Lorg/jetbrains/kotlin/com/intellij/platform/util/plugins/DataLoader;", "relativePath", "", "includeBase", "loadXIncludeReference", ApkUtil.DEF_MODULE_NAME, "resolveModuleFile", "path", "resolvePath", "Companion", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public final class PluginXmlPathResolver implements PathResolver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final PathResolver DEFAULT_PATH_RESOLVER;
    private final List<Path> pluginJarFiles;

    /* JADX INFO: compiled from: PluginXmlPathResolver.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J!\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\tJ\u0015\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\fJB\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\b\u001a\u00020\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u0006H\u0002J\u001f\u0010\u0019\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0000¢\u0006\u0002\b\u001aR\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginXmlPathResolver$Companion;", "", "()V", "DEFAULT_PATH_RESOLVER", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PathResolver;", "getChildBase", "", ApkUtil.DEF_MODULE_NAME, "relativePath", "getChildBase$intellij_platform_core_impl", "getParentPath", "path", "getParentPath$intellij_platform_core_impl", "loadUsingZipFile", "", "readInto", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/RawPluginDescriptor;", "readContext", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ReadModuleContext;", "pathResolver", "dataLoader", "Lorg/jetbrains/kotlin/com/intellij/platform/util/plugins/DataLoader;", "jarFile", "Ljava/nio/file/Path;", "includeBase", "toLoadPath", "toLoadPath$intellij_platform_core_impl", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean loadUsingZipFile(RawPluginDescriptor readInto, ReadModuleContext readContext, PathResolver pathResolver, DataLoader dataLoader, Path jarFile, String relativePath, String includeBase) throws IOException {
            String strSubstring = relativePath;
            ZipFile zipFile = new ZipFile(jarFile.toFile());
            try {
                try {
                    if (StringsKt.startsWith$default(strSubstring, "/", false, 2, (Object) null)) {
                        if (strSubstring == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        strSubstring = strSubstring.substring(1);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.String).substring(startIndex)");
                    }
                    ZipEntry entry = zipFile.getEntry(strSubstring);
                    if (entry == null) {
                        zipFile.close();
                        return false;
                    }
                    InputStream inputStream = zipFile.getInputStream(entry);
                    Intrinsics.checkNotNullExpressionValue(inputStream, "zipFile.getInputStream(entry)");
                    XmlReader.readModuleDescriptor(inputStream, readContext, pathResolver, dataLoader, includeBase, readInto, jarFile.toString());
                    zipFile.close();
                    return true;
                } catch (IOException e) {
                    Logger.getInstance((Class<?>) PluginXmlPathResolver.class).error("Corrupted jar file: " + jarFile, e);
                    zipFile.close();
                    return false;
                }
            } catch (Throwable th) {
                zipFile.close();
                throw th;
            }
        }

        public final String toLoadPath$intellij_platform_core_impl(String relativePath, String base) {
            Intrinsics.checkNotNullParameter(relativePath, "relativePath");
            if (relativePath.charAt(0) == '/') {
                String strSubstring = relativePath.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.String).substring(startIndex)");
                return strSubstring;
            }
            if (StringsKt.startsWith$default(relativePath, "intellij.", false, 2, (Object) null)) {
                return relativePath;
            }
            if (base == null) {
                return "META-INF/" + relativePath;
            }
            return base + '/' + relativePath;
        }

        public final String getChildBase$intellij_platform_core_impl(String base, String relativePath) {
            Intrinsics.checkNotNullParameter(relativePath, "relativePath");
            int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) relativePath, '/', 0, false, 6, (Object) null);
            if (iLastIndexOf$default <= 0 || StringsKt.startsWith$default(relativePath, "/META-INF/", false, 2, (Object) null)) {
                return base;
            }
            String strSubstring = relativePath.substring(0, iLastIndexOf$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return base == null ? strSubstring : base + '/' + strSubstring;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PluginXmlPathResolver(List<? extends Path> pluginJarFiles) {
        Intrinsics.checkNotNullParameter(pluginJarFiles, "pluginJarFiles");
        this.pluginJarFiles = pluginJarFiles;
    }

    @Override // org.jetbrains.kotlin.com.intellij.ide.plugins.PathResolver
    public boolean isFlat() {
        return PathResolver.DefaultImpls.isFlat(this);
    }

    static {
        List listEmptyList = Collections.emptyList();
        Intrinsics.checkNotNullExpressionValue(listEmptyList, "Collections.emptyList()");
        DEFAULT_PATH_RESOLVER = new PluginXmlPathResolver(listEmptyList);
    }

    @Override // org.jetbrains.kotlin.com.intellij.ide.plugins.PathResolver
    public boolean loadXIncludeReference(RawPluginDescriptor readInto, ReadModuleContext readContext, DataLoader dataLoader, String base, String relativePath) {
        InputStream resourceAsStream;
        Intrinsics.checkNotNullParameter(readInto, "readInto");
        Intrinsics.checkNotNullParameter(readContext, "readContext");
        Intrinsics.checkNotNullParameter(dataLoader, "dataLoader");
        Intrinsics.checkNotNullParameter(relativePath, "relativePath");
        Companion companion = INSTANCE;
        String loadPath$intellij_platform_core_impl = companion.toLoadPath$intellij_platform_core_impl(relativePath, base);
        InputStream inputStreamLoad = dataLoader.load(loadPath$intellij_platform_core_impl);
        if (inputStreamLoad != null) {
            XmlReader.readModuleDescriptor(inputStreamLoad, readContext, this, dataLoader, companion.getChildBase$intellij_platform_core_impl(base, relativePath), readInto, (String) null);
            return true;
        }
        if (findInJarFiles(readInto, readContext, dataLoader, loadPath$intellij_platform_core_impl, companion.getChildBase$intellij_platform_core_impl(base, relativePath))) {
            return true;
        }
        if (!StringsKt.startsWith$default(loadPath$intellij_platform_core_impl, "META-INF/", false, 2, (Object) null) || (resourceAsStream = PluginXmlPathResolver.class.getClassLoader().getResourceAsStream(loadPath$intellij_platform_core_impl)) == null) {
            return false;
        }
        XmlReader.readModuleDescriptor(resourceAsStream, readContext, this, dataLoader, (String) null, readInto, (String) null);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.ide.plugins.PathResolver
    public RawPluginDescriptor resolvePath(ReadModuleContext readContext, DataLoader dataLoader, String relativePath, RawPluginDescriptor readInto) {
        Intrinsics.checkNotNullParameter(readContext, "readContext");
        Intrinsics.checkNotNullParameter(dataLoader, "dataLoader");
        Intrinsics.checkNotNullParameter(relativePath, "relativePath");
        String loadPath$intellij_platform_core_impl = INSTANCE.toLoadPath$intellij_platform_core_impl(relativePath, null);
        InputStream inputStreamLoad = dataLoader.load(loadPath$intellij_platform_core_impl);
        if (inputStreamLoad != null) {
            return XmlReader.readModuleDescriptor(inputStreamLoad, readContext, this, dataLoader, (String) null, readInto, (String) null);
        }
        if (readInto == null) {
            readInto = new RawPluginDescriptor();
        }
        if (findInJarFiles(readInto, readContext, dataLoader, loadPath$intellij_platform_core_impl, null)) {
            return readInto;
        }
        if (StringsKt.startsWith$default(relativePath, "intellij.", false, 2, (Object) null)) {
            throw new RuntimeException("Cannot resolve " + loadPath$intellij_platform_core_impl + " (dataLoader=" + dataLoader + ')');
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.ide.plugins.PathResolver
    public RawPluginDescriptor resolveModuleFile(ReadModuleContext readContext, DataLoader dataLoader, String path, RawPluginDescriptor readInto) {
        Intrinsics.checkNotNullParameter(readContext, "readContext");
        Intrinsics.checkNotNullParameter(dataLoader, "dataLoader");
        Intrinsics.checkNotNullParameter(path, "path");
        InputStream inputStreamLoad = dataLoader.load(path);
        if (inputStreamLoad == null) {
            if (Intrinsics.areEqual(path, "intellij.profiler.clion")) {
                RawPluginDescriptor rawPluginDescriptor = new RawPluginDescriptor();
                rawPluginDescriptor.package = "org.jetbrains.kotlin.com.intellij.profiler.clion";
                return rawPluginDescriptor;
            }
            throw new RuntimeException("Cannot resolve " + path + " (dataLoader=" + dataLoader + ", pluginJarFiles=" + CollectionsKt.joinToString$default(this.pluginJarFiles, "\n  ", null, null, 0, null, null, 62, null) + ')');
        }
        return XmlReader.readModuleDescriptor(inputStreamLoad, readContext, this, dataLoader, (String) null, readInto, (String) null);
    }

    private final boolean findInJarFiles(RawPluginDescriptor readInto, ReadModuleContext readContext, DataLoader dataLoader, String relativePath, String includeBase) throws IOException {
        ZipFilePool pool = dataLoader.getPool();
        for (Path path : this.pluginJarFiles) {
            if (!dataLoader.isExcludedFromSubSearch(path)) {
                if (pool == null) {
                    if (INSTANCE.loadUsingZipFile(readInto, readContext, this, dataLoader, path, relativePath, includeBase)) {
                        return true;
                    }
                } else {
                    try {
                        ZipFilePool.EntryResolver entryResolverLoad = pool.load(path);
                        Intrinsics.checkNotNullExpressionValue(entryResolverLoad, "try {\n          pool.loa…       continue\n        }");
                        InputStream it = entryResolverLoad.loadZipEntry(relativePath);
                        if (it != null) {
                            Intrinsics.checkNotNullExpressionValue(it, "it");
                            XmlReader.readModuleDescriptor(it, readContext, this, dataLoader, includeBase, readInto, path.toString());
                            return true;
                        }
                    } catch (IOException e) {
                        Logger.getInstance((Class<?>) PluginXmlPathResolver.class).error("Corrupted jar file: " + path, e);
                    }
                }
            }
        }
        return false;
    }
}
