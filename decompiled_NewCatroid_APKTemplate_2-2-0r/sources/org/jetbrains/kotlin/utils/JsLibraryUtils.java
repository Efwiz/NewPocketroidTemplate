package org.jetbrains.kotlin.utils;

import io.netty.util.internal.StringUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.utils.fileUtils.FileUtilsKt;

/* JADX INFO: compiled from: JsLibraryUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eH\u0002J(\u0010\u000f\u001a\u00020\t2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u00112\u0006\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007J \u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0019\u001a\u00020\u0007H\u0002J\u0010\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u000bH\u0007J\u0010\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u000bH\u0007J\u0010\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u000bH\u0002J\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u000bH\u0002J$\u0010\u001f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\t0!H\u0002J$\u0010\"\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000b2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\t0!H\u0002J$\u0010#\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\t0!H\u0002J*\u0010$\u001a\u00020\t2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00112\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\t0!H\u0007J$\u0010&\u001a\u00020\t2\u0006\u0010'\u001a\u00020\u000b2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\t0!H\u0007J\u000e\u0010(\u001a\u0004\u0018\u00010\u0007*\u00020\u000bH\u0002J\f\u0010)\u001a\u00020\u000b*\u00020\u000bH\u0002J(\u0010*\u001a\u00020\t*\u00020\u000b2\u0006\u0010+\u001a\u00020\u00072\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\t0!H\u0002R\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/utils/JsLibraryUtils;", "", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "META_INF_RESOURCES", "", "copyJsFilesFromDirectory", "", "dir", "Ljava/io/File;", "outputLibraryJsPath", "copySourceMap", "", "copyJsFilesFromLibraries", "libraries", "", "copyJsFilesFromZip", "file", "copyLibrary", "outputPath", "library", "Lorg/jetbrains/kotlin/utils/JsLibrary;", "getSuggestedPath", "path", "isKotlinJavascriptIrLibrary", "candidate", "isKotlinJavascriptLibrary", "isZippedKlib", "isZippedKlibInZip", "processDirectory", "action", "Lkotlin/Function1;", "traverseArchive", "traverseDirectory", "traverseJsLibraries", "libs", "traverseJsLibrary", "lib", "contentIfExists", "correspondingSourceMapFile", "runIfFileExists", "relativePath", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsLibraryUtils {
    public static final JsLibraryUtils INSTANCE = new JsLibraryUtils();
    private static final Logger LOG;
    private static final String META_INF_RESOURCES;

    private JsLibraryUtils() {
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) JsLibraryUtils.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
        META_INF_RESOURCES = LibraryUtils.INSTANCE.getMETA_INF() + "resources/";
    }

    @JvmStatic
    public static final boolean isKotlinJavascriptLibrary(File library) {
        Intrinsics.checkNotNullParameter(library, "library");
        return !KotlinJavascriptMetadataUtils.loadMetadata(library).isEmpty();
    }

    @JvmStatic
    public static final boolean isKotlinJavascriptIrLibrary(File candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        JsLibraryUtils jsLibraryUtils = INSTANCE;
        if (jsLibraryUtils.isZippedKlib(candidate)) {
            return true;
        }
        if (FileUtil.isJarOrZip(candidate)) {
            return jsLibraryUtils.isZippedKlibInZip(candidate);
        }
        return FilesKt.resolve(candidate, "default").isDirectory() && FilesKt.resolve(FilesKt.resolve(candidate, "default"), KotlinLibraryLayoutKt.KLIB_MANIFEST_FILE_NAME).isFile() && FilesKt.resolve(FilesKt.resolve(candidate, "default"), KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME).isDirectory();
    }

    public static /* synthetic */ void copyJsFilesFromLibraries$default(List list, String str, boolean z, int i, Object obj) throws IOException {
        if ((i & 4) != 0) {
            z = false;
        }
        copyJsFilesFromLibraries(list, str, z);
    }

    @JvmStatic
    public static final void copyJsFilesFromLibraries(List<String> libraries, String outputLibraryJsPath, boolean copySourceMap) throws IOException {
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(outputLibraryJsPath, "outputLibraryJsPath");
        Iterator<String> it = libraries.iterator();
        while (it.hasNext()) {
            File file = new File(it.next());
            file.exists();
            if (file.isDirectory()) {
                INSTANCE.copyJsFilesFromDirectory(file, outputLibraryJsPath, copySourceMap);
            } else {
                INSTANCE.copyJsFilesFromZip(file, outputLibraryJsPath, copySourceMap);
            }
        }
    }

    @JvmStatic
    public static final void traverseJsLibraries(List<? extends File> libs, Function1<? super JsLibrary, Unit> action) {
        Intrinsics.checkNotNullParameter(libs, "libs");
        Intrinsics.checkNotNullParameter(action, "action");
        Iterator<T> it = libs.iterator();
        while (it.hasNext()) {
            traverseJsLibrary((File) it.next(), action);
        }
    }

    @JvmStatic
    public static final void traverseJsLibrary(File lib, Function1<? super JsLibrary, Unit> action) {
        Intrinsics.checkNotNullParameter(lib, "lib");
        Intrinsics.checkNotNullParameter(action, "action");
        if (lib.isDirectory()) {
            INSTANCE.traverseDirectory(lib, action);
            return;
        }
        if (FileUtil.isJarOrZip(lib)) {
            INSTANCE.traverseArchive(lib, action);
            return;
        }
        String name2 = lib.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (kotlin.text.StringsKt.endsWith$default(name2, ".js", false, 2, (Object) null)) {
            JsLibraryUtils jsLibraryUtils = INSTANCE;
            String path = lib.getPath();
            Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
            jsLibraryUtils.runIfFileExists(lib, path, action);
            File fileWithReplacedExtensionOrNull = FileUtilsKt.withReplacedExtensionOrNull(lib, KotlinJavascriptMetadataUtils.META_JS_SUFFIX, ".js");
            if (fileWithReplacedExtensionOrNull != null) {
                String path2 = fileWithReplacedExtensionOrNull.getPath();
                Intrinsics.checkNotNullExpressionValue(path2, "getPath(...)");
                jsLibraryUtils.runIfFileExists(fileWithReplacedExtensionOrNull, path2, action);
            }
        }
    }

    private final boolean isZippedKlibInZip(File candidate) {
        ZipFile zipFile = new ZipFile(candidate);
        try {
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
            Intrinsics.checkNotNullExpressionValue(enumerationEntries, "entries(...)");
            Iterator it = kotlin.collections.CollectionsKt.iterator(enumerationEntries);
            boolean z = false;
            boolean z2 = false;
            while (it.hasNext()) {
                ZipEntry zipEntry = (ZipEntry) it.next();
                if (Intrinsics.areEqual(zipEntry.getName(), "default/manifest")) {
                    z = true;
                }
                if (Intrinsics.areEqual(zipEntry.getName(), "default/ir/")) {
                    z2 = true;
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipFile, null);
            return z && z2;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(zipFile, th);
                throw th2;
            }
        }
    }

    private final boolean isZippedKlib(File candidate) {
        return Intrinsics.areEqual(FilesKt.getExtension(candidate), "klib");
    }

    private final void runIfFileExists(File file, String str, Function1<? super JsLibrary, Unit> function1) {
        if (file.isFile()) {
            function1.invoke(new JsLibrary(FilesKt.readText$default(file, null, 1, null), str, contentIfExists(correspondingSourceMapFile(file)), file));
        }
    }

    private final void copyJsFilesFromDirectory(File dir, final String outputLibraryJsPath, final boolean copySourceMap) {
        traverseDirectory(dir, new Function1<JsLibrary, Unit>() { // from class: org.jetbrains.kotlin.utils.JsLibraryUtils.copyJsFilesFromDirectory.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(JsLibrary jsLibrary) {
                invoke2(jsLibrary);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JsLibrary it) {
                Intrinsics.checkNotNullParameter(it, "it");
                JsLibraryUtils.INSTANCE.copyLibrary(outputLibraryJsPath, it, copySourceMap);
            }
        });
    }

    private final String contentIfExists(File file) {
        if (file.exists()) {
            return FilesKt.readText$default(file, null, 1, null);
        }
        return null;
    }

    private final File correspondingSourceMapFile(File file) {
        return new File(file.getParentFile(), file.getName() + ".map");
    }

    private final void processDirectory(final File dir, final Function1<? super JsLibrary, Unit> action) {
        FileUtil.processFilesRecursively(dir, new Processor() { // from class: org.jetbrains.kotlin.utils.JsLibraryUtils$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return JsLibraryUtils.processDirectory$lambda$3(dir, action, (File) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean processDirectory$lambda$3(File dir, Function1 action, File file) {
        JsLibraryUtils jsLibraryUtils;
        String suggestedPath;
        Intrinsics.checkNotNullParameter(dir, "$dir");
        Intrinsics.checkNotNullParameter(action, "$action");
        String relativePath = FileUtil.getRelativePath(dir, file);
        if (relativePath == null) {
            throw new IllegalArgumentException("relativePath should not be null " + dir + StringUtil.SPACE + file);
        }
        if (!kotlin.text.StringsKt.endsWith$default(relativePath, ".js", false, 2, (Object) null) || (suggestedPath = (jsLibraryUtils = INSTANCE).getSuggestedPath(relativePath)) == null) {
            return true;
        }
        Intrinsics.checkNotNull(file);
        jsLibraryUtils.runIfFileExists(file, suggestedPath, action);
        return true;
    }

    private final void traverseDirectory(File dir, Function1<? super JsLibrary, Unit> action) {
        try {
            processDirectory(dir, action);
        } catch (IOException e) {
            LOG.error("Could not read files from directory " + dir.getName() + ": " + e.getMessage());
        }
    }

    private final void copyJsFilesFromZip(File file, final String outputLibraryJsPath, final boolean copySourceMap) throws IOException {
        traverseArchive(file, new Function1<JsLibrary, Unit>() { // from class: org.jetbrains.kotlin.utils.JsLibraryUtils.copyJsFilesFromZip.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(JsLibrary jsLibrary) {
                invoke2(jsLibrary);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JsLibrary it) {
                Intrinsics.checkNotNullParameter(it, "it");
                JsLibraryUtils.INSTANCE.copyLibrary(outputLibraryJsPath, it, copySourceMap);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void copyLibrary(String outputPath, JsLibrary library, boolean copySourceMap) {
        String sourceMapContent;
        File file = new File(outputPath, library.getPath());
        file.getParentFile().mkdirs();
        FilesKt.writeText$default(file, library.getContent(), null, 2, null);
        if (!copySourceMap || (sourceMapContent = library.getSourceMapContent()) == null) {
            return;
        }
        FilesKt.writeText$default(new File(file.getParent(), file.getName() + ".map"), sourceMapContent, null, 2, null);
    }

    private final void traverseArchive(File file, Function1<? super JsLibrary, Unit> action) throws IOException {
        try {
            ZipFile zipFile = new ZipFile(file.getPath());
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
                ArrayList arrayList = new ArrayList();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                while (enumerationEntries.hasMoreElements()) {
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                    String name2 = zipEntryNextElement.getName();
                    if (!zipEntryNextElement.isDirectory()) {
                        Intrinsics.checkNotNull(name2);
                        if (kotlin.text.StringsKt.endsWith$default(name2, ".js", false, 2, (Object) null)) {
                            String suggestedPath = getSuggestedPath(name2);
                            if (suggestedPath != null) {
                                InputStream inputStream = zipFile.getInputStream(zipEntryNextElement);
                                Intrinsics.checkNotNull(inputStream);
                                arrayList.add(new JsLibrary(TextStreamsKt.readText(new InputStreamReader(inputStream, Charsets.UTF_8)), suggestedPath, null, null));
                            }
                        } else if (kotlin.text.StringsKt.endsWith$default(name2, KotlinJavascriptMetadataUtils.JS_MAP_EXT, false, 2, (Object) null)) {
                            String str = kotlin.text.StringsKt.removeSuffix(name2, (CharSequence) KotlinJavascriptMetadataUtils.JS_MAP_EXT) + ".js";
                            Intrinsics.checkNotNull(zipEntryNextElement);
                            linkedHashMap.put(str, zipEntryNextElement);
                        }
                    }
                }
                ArrayList<JsLibrary> arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                for (JsLibrary jsLibraryCopy$default : arrayList2) {
                    ZipEntry zipEntry = (ZipEntry) linkedHashMap.get(jsLibraryCopy$default.getPath());
                    if (zipEntry != null) {
                        InputStream inputStream2 = zipFile.getInputStream(zipEntry);
                        Intrinsics.checkNotNull(inputStream2);
                        jsLibraryCopy$default = JsLibrary.copy$default(jsLibraryCopy$default, null, null, TextStreamsKt.readText(new InputStreamReader(inputStream2, Charsets.UTF_8)), null, 11, null);
                    }
                    arrayList3.add(jsLibraryCopy$default);
                }
                Iterator it = arrayList3.iterator();
                while (it.hasNext()) {
                    action.invoke(it.next());
                }
            } catch (IOException e) {
                LOG.error("Could not extract files from archive " + file.getName() + ": " + e.getMessage());
            } finally {
                zipFile.close();
            }
        } catch (ZipException e2) {
            throw new IOException("Failed to open zip file: " + file, e2);
        }
    }

    private final String getSuggestedPath(String path) {
        String systemIndependentName = FileUtil.toSystemIndependentName(path);
        Intrinsics.checkNotNullExpressionValue(systemIndependentName, "toSystemIndependentName(...)");
        if (!kotlin.text.StringsKt.startsWith$default(systemIndependentName, LibraryUtils.INSTANCE.getMETA_INF(), false, 2, (Object) null)) {
            return path;
        }
        String str = META_INF_RESOURCES;
        if (!kotlin.text.StringsKt.startsWith$default(systemIndependentName, str, false, 2, (Object) null)) {
            return null;
        }
        String strSubstring = path.substring(str.length());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }
}
