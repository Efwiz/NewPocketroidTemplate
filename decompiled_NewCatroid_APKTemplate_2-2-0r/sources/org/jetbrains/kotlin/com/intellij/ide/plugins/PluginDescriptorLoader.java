package org.jetbrains.kotlin.com.intellij.ide.plugins;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.zip.ZipFile;
import javax.xml.stream.XMLStreamException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.openapi.util.BuildNumber;
import org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader;
import org.jetbrains.kotlin.com.intellij.platform.util.plugins.LocalFsDataLoader;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.util.lang.ZipFilePool;

/* JADX INFO: compiled from: PluginDescriptorLoader.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000|\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010 \n\u0002\b\u0007\u001a$\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tH\u0002\u001a0\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000bH\u0002\u001a\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0000\u001a\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000bH\u0002\u001a^\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00112\b\u0010\u0019\u001a\u0004\u0018\u00010\u00172\u001a\u0010\u001a\u001a\u0016\u0012\u0004\u0012\u00020\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u001d\u0018\u00010\u001b2\u000e\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010 0\u001f2\u000e\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010 0\u001f\u001a2\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u00172\b\u0010&\u001a\u0004\u0018\u00010\u00172\u0006\u0010'\u001a\u00020\u00132\u0006\u0010(\u001a\u00020\u0013H\u0002\u001a\u001a\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020$H\u0007\u001a.\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020\u00172\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001d2\u0006\u0010.\u001a\u00020\u00132\u0006\u0010/\u001a\u000200\u001a\u001a\u00101\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020\u00172\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u001aL\u00102\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020\u00172\u0006\u00103\u001a\u00020\u000b2\b\u00104\u001a\u0004\u0018\u00010\u00172\u0006\u0010#\u001a\u00020$2\u0006\u0010.\u001a\u00020\u00132\u0006\u00105\u001a\u00020\u00132\u0006\u00106\u001a\u00020\u00132\u0006\u0010/\u001a\u000200H\u0002\u001aJ\u00107\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020\u00172\u0006\u0010#\u001a\u00020$2\u0006\u0010/\u001a\u0002002\u0006\u0010.\u001a\u00020\u00132\u0006\u00105\u001a\u00020\u00132\u0006\u00108\u001a\u00020\u00132\u0006\u00106\u001a\u00020\u00132\b\b\u0002\u0010'\u001a\u00020\u0013\u001aL\u00109\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020\u00172\u0006\u0010:\u001a\u00020\u000b2\u0006\u0010/\u001a\u0002002\u0006\u0010,\u001a\u00020$2\u0006\u0010.\u001a\u00020\u00132\u0006\u00105\u001a\u00020\u00132\u0006\u00106\u001a\u00020\u00132\b\u00104\u001a\u0004\u0018\u00010\u0017H\u0002\u001a\u0018\u0010;\u001a\u00020$2\u0006\u0010'\u001a\u00020\u00132\u0006\u0010(\u001a\u00020\u0013H\u0000\u001a\u0018\u0010<\u001a\u00020\u00052\u0006\u0010=\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020$H\u0002\u001a\u001a\u0010>\u001a\u0004\u0018\u00010*2\u0006\u0010?\u001a\u00020\u00172\u0006\u0010:\u001a\u00020\u000bH\u0000\u001aD\u0010@\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020$2\u0006\u0010.\u001a\u00020\u00132\u0006\u00105\u001a\u00020\u00132\u0006\u00106\u001a\u00020\u00132\u0006\u0010/\u001a\u0002002\b\b\u0002\u0010'\u001a\u00020\u0013H\u0002\u001a\u001c\u0010A\u001a\b\u0012\u0004\u0012\u00020*0B2\u0006\u0010'\u001a\u00020\u00132\u0006\u0010(\u001a\u00020\u0013\u001a\u001e\u0010C\u001a\u00020\u00052\u0006\u0010D\u001a\u00020\u00172\f\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00170\u001fH\u0002\u001a\u0016\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00170B2\u0006\u0010G\u001a\u00020\u0017H\u0002\u001a\u0016\u0010H\u001a\b\u0012\u0004\u0012\u00020 0B2\u0006\u0010\u0006\u001a\u00020\u0007H\u0007\"\u0014\u0010\u0000\u001a\u00020\u00018BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003¨\u0006I"}, d2 = {"LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "getLOG", "()Lcom/intellij/openapi/diagnostic/Logger;", "collectPluginFilesInClassPath", "", "loader", "Ljava/lang/ClassLoader;", "urls", "", "Ljava/net/URL;", "", "computePlatformPluginUrlAndCollectPluginUrls", "platformPrefix", "createPluginLoadingResult", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginLoadingResult;", "buildNumber", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/BuildNumber;", "fileNameIsLikeVersionedLibraryName", "", "name", "getDescriptorsToMigrate", "dir", "Ljava/nio/file/Path;", "compatibleBuildNumber", "bundledPluginsPath", "brokenPluginVersions", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId;", "", "pluginsToMigrate", "", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptor;", "incompatiblePlugins", "loadBundledDescriptorsAndDescriptorsFromDir", "context", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/DescriptorListLoadingContext;", "customPluginDir", "bundledPluginDir", "isUnitTestMode", "isRunningFromSources", "loadDescriptor", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptorImpl;", "file", "parentContext", "disabledPlugins", "isBundled", "pathResolver", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PathResolver;", "loadDescriptorFromArtifact", "loadDescriptorFromDir", "descriptorRelativePath", "pluginPath", "isEssential", "useCoreClassLoader", "loadDescriptorFromFileOrDir", "isDirectory", "loadDescriptorFromJar", PsiTreeChangeEvent.PROP_FILE_NAME, "loadDescriptors", "loadDescriptorsFromProperty", "result", "loadForCoreEnv", "pluginRoot", "loadFromPluginDir", "loadUncachedDescriptors", "", "putMoreLikelyPluginJarsFirst", "pluginDir", "filesInLibUnderPluginDir", "resolveArchives", "path", "testLoadDescriptorsFromClassPath", "intellij.platform.core.impl"}, k = 2, mv = {1, 5, 1})
public final class PluginDescriptorLoader {
    private static final Logger getLOG() {
        Logger logger = PluginManagerCore.getLogger();
        Intrinsics.checkNotNullExpressionValue(logger, "PluginManagerCore.getLogger()");
        return logger;
    }

    public static final PluginLoadingResult createPluginLoadingResult(final BuildNumber buildNumber) {
        Map<PluginId, Set<String>> brokenPluginVersions = PluginManagerCore.getBrokenPluginVersions();
        Intrinsics.checkNotNullExpressionValue(brokenPluginVersions, "PluginManagerCore.getBrokenPluginVersions()");
        return new PluginLoadingResult(brokenPluginVersions, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.ide.plugins.PluginDescriptorLoader.createPluginLoadingResult.1
            @Override // java.util.function.Supplier
            public final BuildNumber get() {
                BuildNumber buildNumber2 = buildNumber;
                if (buildNumber2 != null) {
                    return buildNumber2;
                }
                BuildNumber buildNumber3 = PluginManagerCore.getBuildNumber();
                Intrinsics.checkNotNullExpressionValue(buildNumber3, "PluginManagerCore.getBuildNumber()");
                return buildNumber3;
            }
        }, false, 4, null);
    }

    public static final IdeaPluginDescriptorImpl loadForCoreEnv(Path pluginRoot, String fileName) {
        Intrinsics.checkNotNullParameter(pluginRoot, "pluginRoot");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        PathResolver pathResolver = PluginXmlPathResolver.DEFAULT_PATH_RESOLVER;
        Set<PluginId> setDisabledPlugins = DisabledPluginsState.disabledPlugins();
        Intrinsics.checkNotNullExpressionValue(setDisabledPlugins, "DisabledPluginsState.disabledPlugins()");
        DescriptorListLoadingContext descriptorListLoadingContext = new DescriptorListLoadingContext(setDisabledPlugins, null, false, false, false, false, 62, null);
        if (Files.isDirectory(pluginRoot, new LinkOption[0])) {
            return loadDescriptorFromDir(pluginRoot, "META-INF/" + fileName, null, descriptorListLoadingContext, true, true, false, pathResolver);
        }
        return loadDescriptorFromJar(pluginRoot, fileName, pathResolver, descriptorListLoadingContext, true, true, false, null);
    }

    private static final IdeaPluginDescriptorImpl loadDescriptorFromDir(Path path, String str, Path path2, DescriptorListLoadingContext descriptorListLoadingContext, boolean z, boolean z2, boolean z3, PathResolver pathResolver) throws Throwable {
        try {
            try {
                byte[] input = Files.readAllBytes(path.resolve(str));
                try {
                    LocalFsDataLoader localFsDataLoader = new LocalFsDataLoader(path);
                    Intrinsics.checkNotNullExpressionValue(input, "input");
                    RawPluginDescriptor moduleDescriptor = XmlReader.readModuleDescriptor(input, descriptorListLoadingContext, pathResolver, localFsDataLoader, (String) null, (RawPluginDescriptor) null, path.toString());
                    IdeaPluginDescriptorImpl ideaPluginDescriptorImpl = new IdeaPluginDescriptorImpl(moduleDescriptor, path2 != null ? path2 : path, z, null, null, z3);
                    ideaPluginDescriptorImpl.readExternal(moduleDescriptor, pathResolver, descriptorListLoadingContext, false, localFsDataLoader);
                    ideaPluginDescriptorImpl.jarFiles = Collections.singletonList(path);
                    return ideaPluginDescriptorImpl;
                } catch (Throwable th) {
                    th = th;
                    if (z2) {
                        throw th;
                    }
                    getLOG().warn("Cannot load " + path.resolve(str), th);
                    return null;
                }
            } catch (NoSuchFileException unused) {
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:32:0x00a6 A[Catch: all -> 0x00cb, TRY_ENTER, TryCatch #2 {all -> 0x00cb, blocks: (B:32:0x00a6, B:34:0x00aa, B:35:0x00bf, B:36:0x00c0), top: B:47:0x00a4 }] */
    /* JADX WARN: Code duplicated, block: B:34:0x00aa A[Catch: all -> 0x00cb, TryCatch #2 {all -> 0x00cb, blocks: (B:32:0x00a6, B:34:0x00aa, B:35:0x00bf, B:36:0x00c0), top: B:47:0x00a4 }] */
    /* JADX WARN: Code duplicated, block: B:36:0x00c0 A[Catch: all -> 0x00cb, TRY_LEAVE, TryCatch #2 {all -> 0x00cb, blocks: (B:32:0x00a6, B:34:0x00aa, B:35:0x00bf, B:36:0x00c0), top: B:47:0x00a4 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:50:? A[RETURN, SYNTHETIC] */
    private static final IdeaPluginDescriptorImpl loadDescriptorFromJar(Path path, String str, PathResolver pathResolver, DescriptorListLoadingContext descriptorListLoadingContext, boolean z, boolean z2, boolean z3, Path path2) throws Throwable {
        ZipFile zipFile;
        ZipFile zipFile2;
        DataLoader javaZipFileDataLoader;
        try {
            ZipFilePool zipFilePool = ZipFilePool.POOL;
            if (zipFilePool == null || descriptorListLoadingContext.transient) {
                ZipFile zipFile3 = new ZipFile(path.toFile(), StandardCharsets.UTF_8);
                zipFile = zipFile3;
                try {
                    zipFile2 = zipFile;
                    javaZipFileDataLoader = new JavaZipFileDataLoader(zipFile3);
                } catch (Throwable th) {
                    th = th;
                    try {
                        if (z2) {
                            if (th instanceof XMLStreamException) {
                                throw new RuntimeException("Cannot read " + path, th);
                            }
                            throw th;
                        }
                        descriptorListLoadingContext.result.reportCannotLoad(path, th);
                        if (zipFile != null) {
                            zipFile.close();
                        }
                        return null;
                    } catch (Throwable th2) {
                        if (zipFile != null) {
                            zipFile.close();
                        }
                        throw th2;
                    }
                }
            } else {
                ZipFilePool.EntryResolver entryResolverLoad = zipFilePool.load(path);
                Intrinsics.checkNotNullExpressionValue(entryResolverLoad, "pool.load(file)");
                javaZipFileDataLoader = new ImmutableZipFileDataLoader(entryResolverLoad, path, zipFilePool);
                zipFile2 = null;
            }
            try {
                InputStream inputStreamLoad = javaZipFileDataLoader.load("META-INF/" + str);
                if (inputStreamLoad == null) {
                    if (zipFile2 != null) {
                        zipFile2.close();
                    }
                    return null;
                }
                RawPluginDescriptor moduleDescriptor = XmlReader.readModuleDescriptor(inputStreamLoad, descriptorListLoadingContext, pathResolver, javaZipFileDataLoader, (String) null, (RawPluginDescriptor) null, path.toString());
                IdeaPluginDescriptorImpl ideaPluginDescriptorImpl = new IdeaPluginDescriptorImpl(moduleDescriptor, path2 != null ? path2 : path, z, null, null, z3);
                ideaPluginDescriptorImpl.readExternal(moduleDescriptor, pathResolver, descriptorListLoadingContext, false, javaZipFileDataLoader);
                ideaPluginDescriptorImpl.jarFiles = Collections.singletonList(ideaPluginDescriptorImpl.getPath());
                if (zipFile2 != null) {
                    zipFile2.close();
                }
                return ideaPluginDescriptorImpl;
            } catch (Throwable th3) {
                th = th3;
                zipFile = zipFile2;
                if (z2) {
                    if (th instanceof XMLStreamException) {
                        throw new RuntimeException("Cannot read " + path, th);
                    }
                    throw th;
                }
                descriptorListLoadingContext.result.reportCannotLoad(path, th);
                if (zipFile != null) {
                    zipFile.close();
                }
                return null;
            }
        } catch (Throwable th4) {
            th = th4;
            zipFile = null;
        }
    }
}
