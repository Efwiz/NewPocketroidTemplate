package org.jetbrains.kotlin.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: compiled from: LibraryUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J \u0010\u0013\u001a\u0004\u0018\u00010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00162\u0006\u0010\u0017\u001a\u00020\u0007H\u0007J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u001e\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0014\u0010\u001f\u001a\u00020\u0007*\u00020 2\u0006\u0010!\u001a\u00020\u0007H\u0002R\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u0007X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/utils/LibraryUtils;", "", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "MANIFEST_PATH", "", "META_INF", "getMETA_INF", "()Ljava/lang/String;", "TITLE_KOTLIN_JAVASCRIPT_STDLIB", "checkAttributeValue", "", "library", "Ljava/io/File;", "expected", "attributeName", "Ljava/util/jar/Attributes$Name;", "getJarFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "classesRoots", "", "jarName", "getManifestFromDirectory", "Ljava/util/jar/Manifest;", "getManifestFromJar", "getManifestFromJarOrDirectory", "getManifestMainAttributesFromJarOrDirectory", "Ljava/util/jar/Attributes;", "isKotlinJavascriptStdLibrary", "getPropertyOrFail", "Ljava/util/Properties;", "propName", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LibraryUtils {
    public static final LibraryUtils INSTANCE;
    private static final Logger LOG;
    private static final String MANIFEST_PATH;
    private static final String META_INF;
    private static String TITLE_KOTLIN_JAVASCRIPT_STDLIB;

    private LibraryUtils() {
    }

    static {
        String propertyOrFail;
        LibraryUtils libraryUtils = new LibraryUtils();
        INSTANCE = libraryUtils;
        Logger logger = Logger.getInstance((Class<?>) LibraryUtils.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
        META_INF = "META-INF/";
        MANIFEST_PATH = "META-INF/MANIFEST.MF";
        InputStream resourceAsStream = LibraryUtils.class.getResourceAsStream("/kotlinManifest.properties");
        if (resourceAsStream != null) {
            try {
                Properties properties = new Properties();
                properties.load(resourceAsStream);
                propertyOrFail = libraryUtils.getPropertyOrFail(properties, "manifest.impl.title.kotlin.javascript.stdlib");
            } catch (IOException e) {
                LOG.error((Throwable) e);
                propertyOrFail = "";
            }
            TITLE_KOTLIN_JAVASCRIPT_STDLIB = propertyOrFail;
        }
        logger.error("Resource 'kotlinManifest.properties' not found.");
        propertyOrFail = "";
        TITLE_KOTLIN_JAVASCRIPT_STDLIB = propertyOrFail;
    }

    public final String getMETA_INF() {
        return META_INF;
    }

    @JvmStatic
    public static final VirtualFile getJarFile(List<? extends VirtualFile> classesRoots, String jarName) {
        Object next;
        Intrinsics.checkNotNullParameter(classesRoots, "classesRoots");
        Intrinsics.checkNotNullParameter(jarName, "jarName");
        Iterator<T> it = classesRoots.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(((VirtualFile) next).getName(), jarName)) {
                return (VirtualFile) next;
            }
        }
        next = null;
        return (VirtualFile) next;
    }

    @JvmStatic
    public static final boolean isKotlinJavascriptStdLibrary(File library) {
        Intrinsics.checkNotNullParameter(library, "library");
        LibraryUtils libraryUtils = INSTANCE;
        String str = TITLE_KOTLIN_JAVASCRIPT_STDLIB;
        Attributes.Name IMPLEMENTATION_TITLE = Attributes.Name.IMPLEMENTATION_TITLE;
        Intrinsics.checkNotNullExpressionValue(IMPLEMENTATION_TITLE, "IMPLEMENTATION_TITLE");
        return libraryUtils.checkAttributeValue(library, str, IMPLEMENTATION_TITLE);
    }

    private final Manifest getManifestFromJar(File library) {
        if (!library.canRead()) {
            return null;
        }
        try {
            JarFile jarFile = new JarFile(library);
            try {
                Manifest manifest = jarFile.getManifest();
                CloseableKt.closeFinally(jarFile, null);
                return manifest;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(jarFile, th);
                    throw th2;
                }
            }
        } catch (IOException unused) {
            return null;
        }
    }

    private final Manifest getManifestFromDirectory(File library) {
        if (library.canRead() && library.isDirectory()) {
            File file = new File(library, MANIFEST_PATH);
            if (!file.exists()) {
                return null;
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    return new Manifest(fileInputStream);
                } finally {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                LOG.warn("IOException " + e);
            }
        }
        return null;
    }

    private final Manifest getManifestFromJarOrDirectory(File library) {
        return library.isDirectory() ? getManifestFromDirectory(library) : getManifestFromJar(library);
    }

    private final Attributes getManifestMainAttributesFromJarOrDirectory(File library) {
        Manifest manifestFromJarOrDirectory = getManifestFromJarOrDirectory(library);
        if (manifestFromJarOrDirectory != null) {
            return manifestFromJarOrDirectory.getMainAttributes();
        }
        return null;
    }

    private final boolean checkAttributeValue(File library, String expected, Attributes.Name attributeName) {
        Attributes manifestMainAttributesFromJarOrDirectory = getManifestMainAttributesFromJarOrDirectory(library);
        String value = manifestMainAttributesFromJarOrDirectory != null ? manifestMainAttributesFromJarOrDirectory.getValue(attributeName) : null;
        return value != null && Intrinsics.areEqual(value, expected);
    }

    private final String getPropertyOrFail(Properties properties, String str) {
        String property = properties.getProperty(str);
        if (property == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            properties.list(new PrintStream(byteArrayOutputStream));
            LOG.error(str + " not found.\n " + byteArrayOutputStream);
        }
        Intrinsics.checkNotNull(property);
        return property;
    }
}
