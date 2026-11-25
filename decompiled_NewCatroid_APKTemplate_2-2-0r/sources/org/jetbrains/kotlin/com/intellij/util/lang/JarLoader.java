package org.jetbrains.kotlin.com.intellij.util.lang;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.jar.Attributes;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: loaded from: classes3.dex */
public class JarLoader extends Loader {
    private static final Map.Entry<Loader.Attribute, Attributes.Name>[] PACKAGE_FIELDS = {new AbstractMap.SimpleImmutableEntry(Loader.Attribute.SPEC_TITLE, Attributes.Name.SPECIFICATION_TITLE), new AbstractMap.SimpleImmutableEntry(Loader.Attribute.SPEC_VERSION, Attributes.Name.SPECIFICATION_VERSION), new AbstractMap.SimpleImmutableEntry(Loader.Attribute.SPEC_VENDOR, Attributes.Name.SPECIFICATION_VENDOR), new AbstractMap.SimpleImmutableEntry(Loader.Attribute.CLASS_PATH, Attributes.Name.CLASS_PATH), new AbstractMap.SimpleImmutableEntry(Loader.Attribute.IMPL_TITLE, Attributes.Name.IMPLEMENTATION_TITLE), new AbstractMap.SimpleImmutableEntry(Loader.Attribute.IMPL_VERSION, Attributes.Name.IMPLEMENTATION_VERSION), new AbstractMap.SimpleImmutableEntry(Loader.Attribute.IMPL_VENDOR, Attributes.Name.IMPLEMENTATION_VENDOR)};
    private volatile Map<Loader.Attribute, String> attributes;
    protected final ClassPath configuration;
    final URL url;
    protected final ResourceFile zipFile;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 9:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
                i2 = 2;
                break;
            case 9:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "configuration";
                break;
            case 2:
                objArr[0] = "zipFile";
                break;
            case 3:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
            case 4:
                objArr[0] = "classConsumer";
                break;
            case 5:
            default:
                objArr[0] = "file";
                break;
            case 6:
                objArr[0] = "attributes";
                break;
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/lang/JarLoader";
                break;
            case 9:
                objArr[0] = "resourceFile";
                break;
            case 14:
                objArr[0] = "name";
                break;
            case 15:
                objArr[0] = "dir";
                break;
            case 16:
                objArr[0] = "fileNameFilter";
                break;
            case 17:
                objArr[0] = "consumer";
                break;
            case 18:
                objArr[0] = CrashHianalyticsData.MESSAGE;
                break;
            case 19:
                objArr[0] = "t";
                break;
        }
        switch (i) {
            case 7:
            case 8:
                objArr[1] = "getAttributes";
                break;
            case 9:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/JarLoader";
                break;
            case 10:
            case 11:
            case 12:
                objArr[1] = "loadManifestAttributes";
                break;
            case 13:
                objArr[1] = "buildData";
                break;
        }
        switch (i) {
            case 3:
            case 4:
                objArr[2] = "findClass";
                break;
            case 5:
                objArr[2] = "fileToUri";
                break;
            case 6:
                objArr[2] = "getAttributes";
                break;
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
                break;
            case 9:
                objArr[2] = "loadManifestAttributes";
                break;
            case 14:
                objArr[2] = "getResource";
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "processResources";
                break;
            case 18:
            case 19:
                objArr[2] = "error";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
                throw new IllegalStateException(str2);
            case 9:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JarLoader(Path path, ClassPath classPath, ResourceFile resourceFile) throws IOException {
        super(path);
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        if (classPath == null) {
            $$$reportNull$$$0(1);
        }
        if (resourceFile == null) {
            $$$reportNull$$$0(2);
        }
        this.configuration = classPath;
        this.zipFile = resourceFile;
        this.url = new URL("jar", "", -1, fileToUri(path) + "!/");
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.Loader
    public final Map<Loader.Attribute, String> getAttributes() throws IOException {
        return loadManifestAttributes(this.zipFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.Loader
    final Class<?> findClass(String str, String str2, ClassPath.ClassDataConsumer classDataConsumer) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (classDataConsumer == null) {
            $$$reportNull$$$0(4);
        }
        return this.zipFile.findClass(str, str2, this, classDataConsumer);
    }

    public static URI fileToUri(Path path) {
        if (path == null) {
            $$$reportNull$$$0(5);
        }
        String strReplace = path.toString().replace(File.separatorChar, '/');
        if (!strReplace.startsWith("/")) {
            strReplace = "/" + strReplace;
        } else if (strReplace.startsWith("//")) {
            strReplace = "//" + strReplace;
        }
        try {
            return new URI("file", null, strReplace, null);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(strReplace, e);
        }
    }

    final String getClassPathManifestAttribute() throws IOException {
        return loadManifestAttributes(this.zipFile).get(Loader.Attribute.CLASS_PATH);
    }

    private static Map<Loader.Attribute, String> getAttributes(Attributes attributes) {
        if (attributes == null) {
            $$$reportNull$$$0(6);
        }
        if (attributes.isEmpty()) {
            Map<Loader.Attribute, String> mapEmptyMap = Collections.emptyMap();
            if (mapEmptyMap == null) {
                $$$reportNull$$$0(7);
            }
            return mapEmptyMap;
        }
        Map<Loader.Attribute, String> mapEmptyMap2 = null;
        for (Map.Entry<Loader.Attribute, Attributes.Name> entry : PACKAGE_FIELDS) {
            String value = attributes.getValue(entry.getValue());
            if (value != null) {
                if (mapEmptyMap2 == null) {
                    mapEmptyMap2 = new EnumMap<>(Loader.Attribute.class);
                }
                mapEmptyMap2.put(entry.getKey(), value);
            }
        }
        if (mapEmptyMap2 == null) {
            mapEmptyMap2 = Collections.emptyMap();
        }
        if (mapEmptyMap2 == null) {
            $$$reportNull$$$0(8);
        }
        return mapEmptyMap2;
    }

    private Map<Loader.Attribute, String> loadManifestAttributes(ResourceFile resourceFile) throws IOException {
        if (resourceFile == null) {
            $$$reportNull$$$0(9);
        }
        Map<Loader.Attribute, String> map = this.attributes;
        if (map != null) {
            if (map == null) {
                $$$reportNull$$$0(10);
            }
            return map;
        }
        synchronized (this) {
            Map<Loader.Attribute, String> map2 = this.attributes;
            if (map2 != null) {
                if (map2 == null) {
                    $$$reportNull$$$0(11);
                }
                return map2;
            }
            Map<Loader.Attribute, String> manifestData = this.configuration.getManifestData(this.path);
            if (manifestData == null) {
                Attributes attributesLoadManifestAttributes = resourceFile.loadManifestAttributes();
                manifestData = attributesLoadManifestAttributes == null ? Collections.emptyMap() : getAttributes(attributesLoadManifestAttributes);
                this.configuration.cacheManifestData(this.path, manifestData);
            }
            this.attributes = manifestData;
            if (manifestData == null) {
                $$$reportNull$$$0(12);
            }
            return manifestData;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.Loader
    public final ClasspathCache.IndexRegistrar buildData() throws IOException {
        ClasspathCache.IndexRegistrar indexRegistrarBuildClassPathCacheData = this.zipFile.buildClassPathCacheData();
        if (indexRegistrarBuildClassPathCacheData == null) {
            $$$reportNull$$$0(13);
        }
        return indexRegistrarBuildClassPathCacheData;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.lang.Loader
    final Resource getResource(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        try {
            return this.zipFile.getResource(str, this);
        } catch (IOException e) {
            error("url: " + this.path, e);
            return null;
        }
    }

    protected final void error(String str, Throwable th) {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        if (th == null) {
            $$$reportNull$$$0(19);
        }
        LoggerRt loggerRt = LoggerRt.getInstance((Class<?>) JarLoader.class);
        if (this.configuration.errorOnMissingJar) {
            loggerRt.error(str, th);
        } else {
            loggerRt.warn(str, th);
        }
    }

    public final String toString() {
        return "JarLoader [" + this.path + "]";
    }
}
