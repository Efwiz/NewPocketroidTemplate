package org.jetbrains.kotlin.com.intellij.ide.plugins;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.openapi.application.PathManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.impl.ApplicationInfoImpl;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.openapi.util.BuildNumber;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.HtmlChunk;
import org.jetbrains.kotlin.com.intellij.util.lang.UrlClassLoader;

/* JADX INFO: loaded from: classes3.dex */
public final class PluginManagerCore {
    private static Reference<Map<PluginId, Set<String>>> brokenPluginVersions;
    private static BuildNumber ourBuildNumber;
    private static volatile PluginSet pluginSet;
    public static final PluginId CORE_ID = PluginId.getId("org.jetbrains.kotlin.com.intellij");
    public static final PluginId JAVA_PLUGIN_ID = PluginId.getId("org.jetbrains.kotlin.com.intellij.java");
    static final PluginId JAVA_MODULE_ID = PluginId.getId("org.jetbrains.kotlin.com.intellij.modules.java");
    static final PluginId ALL_MODULES_MARKER = PluginId.getId("org.jetbrains.kotlin.com.intellij.modules.all");
    public static final PluginId SPECIAL_IDEA_PLUGIN_ID = PluginId.getId("IDEA CORE");
    private static final boolean IGNORE_DISABLED_PLUGINS = Boolean.getBoolean("idea.ignore.disabled.plugins");
    private static final MethodType HAS_LOADED_CLASS_METHOD_TYPE = MethodType.methodType((Class<?>) Boolean.TYPE, (Class<?>) String.class);
    public static volatile boolean isUnitTestMode = Boolean.getBoolean("idea.is.unit.test");
    private static final List<Supplier<? extends HtmlChunk>> pluginErrors = new ArrayList();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 32:
            case 33:
            case 34:
            case 37:
            case 41:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 65:
            case 66:
            case 67:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 31:
            case 35:
            case 36:
            case 38:
            case 39:
            case 40:
            case 42:
            case 43:
            case 63:
            case 64:
            case 68:
            case 80:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 32:
            case 33:
            case 34:
            case 37:
            case 41:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 65:
            case 66:
            case 67:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
                i2 = 3;
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 31:
            case 35:
            case 36:
            case 38:
            case 39:
            case 40:
            case 42:
            case 43:
            case 63:
            case 64:
            case 68:
            case 80:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 6:
                objArr[0] = "value";
                break;
            case 7:
            case 32:
            case 82:
                objArr[0] = "pluginId";
                break;
            case 8:
            case 27:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 56:
            case 79:
                objArr[0] = "descriptor";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 31:
            case 35:
            case 36:
            case 38:
            case 39:
            case 40:
            case 42:
            case 43:
            case 63:
            case 64:
            case 68:
            case 80:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/ide/plugins/PluginManagerCore";
                break;
            case 13:
                objArr[0] = "ids";
                break;
            case 14:
                objArr[0] = "writer";
                break;
            case 15:
            case 16:
            case 57:
            case 65:
            case 66:
            case 67:
            case 83:
            case 84:
                objArr[0] = "id";
                break;
            case 17:
                objArr[0] = "dependentPluginId";
                break;
            case 18:
                objArr[0] = MLAsrCaptureConstants.ASR_ERROR_MESSAGE;
                break;
            case 19:
                objArr[0] = "pluginClass";
                break;
            case 20:
            case 21:
            case 22:
            case 25:
                objArr[0] = "className";
                break;
            case 23:
            case 81:
                objArr[0] = "plugin";
                break;
            case 24:
                objArr[0] = "vendorItem";
                break;
            case 26:
                objArr[0] = "loader";
                break;
            case 28:
                objArr[0] = "plugins";
                break;
            case 29:
                objArr[0] = "incompletePlugins";
                break;
            case 30:
                objArr[0] = "globalErrorsSuppliers";
                break;
            case 33:
                objArr[0] = "disabledIds";
                break;
            case 34:
                objArr[0] = "disabledRequiredIds";
                break;
            case 37:
                objArr[0] = "modules";
                break;
            case 41:
                objArr[0] = "coreClassLoader";
                break;
            case 44:
                objArr[0] = "descriptors";
                break;
            case 45:
            case 53:
            case 58:
                objArr[0] = "idMap";
                break;
            case 46:
                objArr[0] = "errors";
                break;
            case 52:
                objArr[0] = "ideBuildNumber";
                break;
            case 54:
            case 61:
                objArr[0] = "context";
                break;
            case 55:
            case 62:
                objArr[0] = "coreLoader";
                break;
            case 59:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 60:
                objArr[0] = "params";
                break;
            case 69:
            case 72:
            case 75:
                objArr[0] = "rootDescriptor";
                break;
            case 70:
            case 73:
            case 76:
                objArr[0] = "idToMap";
                break;
            case 71:
            case 74:
            case 78:
                objArr[0] = "consumer";
                break;
            case 77:
                objArr[0] = "depProcessed";
                break;
            case 85:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getPluginSet";
                break;
            case 2:
            case 3:
            case 4:
                objArr[1] = "getLoadedPlugins";
                break;
            case 5:
                objArr[1] = "getAndClearPluginLoadingErrors";
                break;
            case 6:
            case 7:
            case 8:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 32:
            case 33:
            case 34:
            case 37:
            case 41:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 65:
            case 66:
            case 67:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/ide/plugins/PluginManagerCore";
                break;
            case 9:
            case 10:
                objArr[1] = "getBrokenPluginVersions";
                break;
            case 11:
            case 12:
                objArr[1] = "readBrokenPluginFile";
                break;
            case 31:
                objArr[1] = "preparePluginsError";
                break;
            case 35:
            case 36:
                objArr[1] = "prepareActions";
                break;
            case 38:
            case 39:
                objArr[1] = "getOrScheduleLoading";
                break;
            case 40:
                objArr[1] = "getEnabledPluginRawList";
                break;
            case 42:
                objArr[1] = "initPlugins";
                break;
            case 43:
                objArr[1] = "getBuildNumber";
                break;
            case 63:
                objArr[1] = "loadAndInitializePlugins";
                break;
            case 64:
                objArr[1] = "getLogger";
                break;
            case 68:
                objArr[1] = "buildPluginIdMap";
                break;
            case 80:
                objArr[1] = "getNonOptionalDependenciesIds";
                break;
            default:
                objArr[1] = "getPlugins";
                break;
        }
        switch (i) {
            case 6:
                objArr[2] = "setPluginSet";
                break;
            case 7:
            case 82:
                objArr[2] = "isDisabled";
                break;
            case 8:
                objArr[2] = "isBrokenPlugin";
                break;
            case 13:
            case 14:
                objArr[2] = "writePluginsList";
                break;
            case 15:
            case 83:
                objArr[2] = "disablePlugin";
                break;
            case 16:
            case 84:
                objArr[2] = "enablePlugin";
                break;
            case 17:
                objArr[2] = "isModuleDependency";
                break;
            case 18:
            case 19:
                objArr[2] = "createPluginException";
                break;
            case 20:
                objArr[2] = "getPluginByClassName";
                break;
            case 21:
                objArr[2] = "getPluginOrPlatformByClassName";
                break;
            case 22:
                objArr[2] = "getPluginDescriptorOrPlatformByClassName";
                break;
            case 23:
                objArr[2] = "isDevelopedByJetBrains";
                break;
            case 24:
                objArr[2] = "isVendorJetBrains";
                break;
            case 25:
            case 26:
                objArr[2] = "hasLoadedClass";
                break;
            case 27:
                objArr[2] = "hasModuleDependencies";
                break;
            case 28:
            case 29:
                objArr[2] = "logPlugins";
                break;
            case 30:
                objArr[2] = "preparePluginsError";
                break;
            case 32:
                objArr[2] = "getLoadingError";
                break;
            case 33:
            case 34:
                objArr[2] = "prepareActions";
                break;
            case 37:
                objArr[2] = "getFirstPluginName";
                break;
            case 41:
                objArr[2] = "initPlugins";
                break;
            case 44:
            case 45:
            case 46:
                objArr[2] = "disableIncompatiblePlugins";
                break;
            case 47:
            case 48:
                objArr[2] = "isCompatible";
                break;
            case 49:
            case 50:
                objArr[2] = "isIncompatible";
                break;
            case 51:
            case 52:
                objArr[2] = "checkBuildNumberCompatibility";
                break;
            case 53:
                objArr[2] = "checkEssentialPluginsAreAvailable";
                break;
            case 54:
            case 55:
                objArr[2] = "initializePlugins";
                break;
            case 56:
            case 57:
            case 58:
                objArr[2] = "checkAndPut";
                break;
            case 59:
            case 60:
                objArr[2] = CrashHianalyticsData.MESSAGE;
                break;
            case 61:
            case 62:
                objArr[2] = "loadAndInitializePlugins";
                break;
            case 65:
                objArr[2] = "findPlugin";
                break;
            case 66:
                objArr[2] = "findPluginByModuleDependency";
                break;
            case 67:
                objArr[2] = "isPluginInstalled";
                break;
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
                objArr[2] = "processAllNonOptionalDependencies";
                break;
            case 79:
                objArr[2] = "getNonOptionalDependenciesIds";
                break;
            case 81:
                objArr[2] = "isUpdatedBundledPlugin";
                break;
            case 85:
                objArr[2] = "addDisablePluginListener";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 32:
            case 33:
            case 34:
            case 37:
            case 41:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 65:
            case 66:
            case 67:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
                throw new IllegalArgumentException(str2);
            case 9:
            case 10:
            case 11:
            case 12:
            case 31:
            case 35:
            case 36:
            case 38:
            case 39:
            case 40:
            case 42:
            case 43:
            case 63:
            case 64:
            case 68:
            case 80:
            default:
                throw new IllegalStateException(str2);
        }
    }

    static Map<PluginId, Set<String>> getBrokenPluginVersions() {
        if (IGNORE_DISABLED_PLUGINS) {
            Map<PluginId, Set<String>> mapEmptyMap = Collections.emptyMap();
            if (mapEmptyMap == null) {
                $$$reportNull$$$0(9);
            }
            return mapEmptyMap;
        }
        Reference<Map<PluginId, Set<String>>> reference = brokenPluginVersions;
        Map<PluginId, Set<String>> brokenPluginFile = reference == null ? null : reference.get();
        if (brokenPluginFile == null) {
            brokenPluginFile = readBrokenPluginFile();
            brokenPluginVersions = new SoftReference(brokenPluginFile);
        }
        if (brokenPluginFile == null) {
            $$$reportNull$$$0(10);
        }
        return brokenPluginFile;
    }

    private static Map<PluginId, Set<String>> readBrokenPluginFile() {
        Map<PluginId, Set<String>> mapTryReadBrokenPluginsFile;
        Path updatedBrokenPluginFile = getUpdatedBrokenPluginFile();
        if (Files.exists(updatedBrokenPluginFile, new LinkOption[0])) {
            mapTryReadBrokenPluginsFile = tryReadBrokenPluginsFile(updatedBrokenPluginFile);
            if (mapTryReadBrokenPluginsFile != null) {
                getLogger().debug("Using cached broken plugins file");
            }
        } else {
            mapTryReadBrokenPluginsFile = null;
        }
        if (mapTryReadBrokenPluginsFile == null && (mapTryReadBrokenPluginsFile = tryReadBrokenPluginsFile(Paths.get(PathManager.getBinPath() + "/brokenPlugins.db", new String[0]))) != null) {
            getLogger().debug("Using broken plugins file from IDE distribution");
        }
        if (mapTryReadBrokenPluginsFile != null) {
            if (mapTryReadBrokenPluginsFile == null) {
                $$$reportNull$$$0(11);
            }
            return mapTryReadBrokenPluginsFile;
        }
        Map<PluginId, Set<String>> mapEmptyMap = Collections.emptyMap();
        if (mapEmptyMap == null) {
            $$$reportNull$$$0(12);
        }
        return mapEmptyMap;
    }

    private static Map<PluginId, Set<String>> tryReadBrokenPluginsFile(Path path) {
        try {
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(Files.newInputStream(path, new OpenOption[0]), 32000));
            try {
                int unsignedByte = dataInputStream.readUnsignedByte();
                if (unsignedByte != 2) {
                    getLogger().info("Unsupported version of " + path + "(fileVersion=" + unsignedByte + ", supportedVersion=2)");
                    dataInputStream.close();
                    return null;
                }
                String utf = dataInputStream.readUTF();
                if (!utf.equals(getBuildNumber().toString())) {
                    getLogger().info("Ignoring cached broken plugins file from an earlier IDE build (" + utf + Constant.AFTER_QUTO);
                    dataInputStream.close();
                    return null;
                }
                int i = dataInputStream.readInt();
                HashMap map = new HashMap(i);
                for (int i2 = 0; i2 < i; i2++) {
                    PluginId id2 = PluginId.getId(dataInputStream.readUTF());
                    int unsignedShort = dataInputStream.readUnsignedShort();
                    String[] strArr = new String[unsignedShort];
                    for (int i3 = 0; i3 < unsignedShort; i3++) {
                        strArr[i3] = dataInputStream.readUTF();
                    }
                    map.put(id2, unsignedShort == 1 ? Collections.singleton(strArr[0]) : new HashSet(Arrays.asList(strArr)));
                }
                dataInputStream.close();
                return map;
            } catch (Throwable th) {
                try {
                    dataInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (NoSuchFileException unused) {
            return null;
        } catch (IOException e) {
            getLogger().error("Failed to read " + path, e);
            return null;
        }
    }

    public static void writePluginsList(Collection<PluginId> collection, Writer writer) throws IOException {
        if (collection == null) {
            $$$reportNull$$$0(13);
        }
        if (writer == null) {
            $$$reportNull$$$0(14);
        }
        ArrayList arrayList = new ArrayList(collection);
        arrayList.sort(null);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            writer.write(((PluginId) it.next()).getIdString());
            writer.write(10);
        }
    }

    public static PluginId getPluginByClassName(String str) {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        PluginId pluginOrPlatformByClassName = getPluginOrPlatformByClassName(str);
        if (pluginOrPlatformByClassName == null || CORE_ID.equals(pluginOrPlatformByClassName)) {
            return null;
        }
        return pluginOrPlatformByClassName;
    }

    public static PluginId getPluginOrPlatformByClassName(String str) {
        if (str == null) {
            $$$reportNull$$$0(21);
        }
        PluginDescriptor pluginDescriptorOrPlatformByClassName = getPluginDescriptorOrPlatformByClassName(str);
        if (pluginDescriptorOrPlatformByClassName == null) {
            return null;
        }
        return pluginDescriptorOrPlatformByClassName.getId();
    }

    public static PluginDescriptor getPluginDescriptorOrPlatformByClassName(String str) {
        IdeaPluginDescriptorImpl next;
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        PluginSet pluginSet2 = pluginSet;
        if (pluginSet2 == null || str.startsWith("java.") || str.startsWith("javax.") || str.startsWith("kotlin.") || str.startsWith("groovy.") || !str.contains(".")) {
            return null;
        }
        Iterator<IdeaPluginDescriptorImpl> it = pluginSet2.getRawListOfEnabledModules().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!hasLoadedClass(str, next.getClassLoader()));
        if (next == null) {
            return null;
        }
        if (!CORE_ID.equals(next.getId()) || str.startsWith("com.jetbrains.") || str.startsWith("org.jetbrains.") || str.startsWith("org.jetbrains.kotlin.com.intellij.") || str.startsWith("org.intellij.") || str.startsWith("com.android.") || str.startsWith("git4idea.") || str.startsWith("org.angularjs.")) {
            return next;
        }
        String resourceRoot = null;
        for (IdeaPluginDescriptorImpl ideaPluginDescriptorImpl : pluginSet2.enabledPlugins) {
            if (ideaPluginDescriptorImpl.isUseIdeaClassLoader) {
                if (resourceRoot == null && (resourceRoot = PathManager.getResourceRoot(next.getClassLoader(), str.replace('.', '/') + ".class")) == null) {
                    return null;
                }
                if (resourceRoot.startsWith(FileUtilRt.toSystemIndependentName(ideaPluginDescriptorImpl.getPath().toString()))) {
                    return ideaPluginDescriptorImpl;
                }
            }
        }
        return null;
    }

    private static Path getUpdatedBrokenPluginFile() {
        return Paths.get(PathManager.getConfigPath(), new String[0]).resolve("updatedBrokenPlugins.db");
    }

    private static boolean hasLoadedClass(String str, ClassLoader classLoader) {
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        if (classLoader == null) {
            $$$reportNull$$$0(26);
        }
        if (classLoader instanceof UrlClassLoader) {
            return ((UrlClassLoader) classLoader).hasLoadedClass(str);
        }
        Class<?> superclass = classLoader.getClass();
        if (superclass.isAnonymousClass() || superclass.isMemberClass()) {
            superclass = superclass.getSuperclass();
        }
        try {
            return (boolean) MethodHandles.publicLookup().findVirtual(superclass, "hasLoadedClass", HAS_LOADED_CLASS_METHOD_TYPE).invoke(classLoader, str);
        } catch (IllegalAccessError | IllegalAccessException | NoSuchMethodError unused) {
            return false;
        } catch (Throwable th) {
            getLogger().error(th);
            return false;
        }
    }

    public static BuildNumber getBuildNumber() {
        BuildNumber buildNumberFromPluginsCompatibleBuild = ourBuildNumber;
        if (buildNumberFromPluginsCompatibleBuild == null) {
            buildNumberFromPluginsCompatibleBuild = BuildNumber.fromPluginsCompatibleBuild();
            if (buildNumberFromPluginsCompatibleBuild == null) {
                if (isUnitTestMode) {
                    buildNumberFromPluginsCompatibleBuild = BuildNumber.currentVersion();
                } else {
                    try {
                        buildNumberFromPluginsCompatibleBuild = ApplicationInfoImpl.getShadowInstance().getApiVersionAsNumber();
                    } catch (RuntimeException unused) {
                        buildNumberFromPluginsCompatibleBuild = BuildNumber.currentVersion();
                    }
                }
            }
            ourBuildNumber = buildNumberFromPluginsCompatibleBuild;
        }
        if (buildNumberFromPluginsCompatibleBuild == null) {
            $$$reportNull$$$0(43);
        }
        return buildNumberFromPluginsCompatibleBuild;
    }

    public static PluginLoadingError checkBuildNumberCompatibility(IdeaPluginDescriptor ideaPluginDescriptor, BuildNumber buildNumber) {
        BuildNumber buildNumberFromString;
        if (ideaPluginDescriptor == null) {
            $$$reportNull$$$0(51);
        }
        if (buildNumber == null) {
            $$$reportNull$$$0(52);
        }
        String sinceBuild = ideaPluginDescriptor.getSinceBuild();
        String untilBuild = ideaPluginDescriptor.getUntilBuild();
        if (sinceBuild == null) {
            buildNumberFromString = null;
        } else {
            try {
                buildNumberFromString = BuildNumber.fromString(sinceBuild, ideaPluginDescriptor.getName(), null);
            } catch (Exception e) {
                getLogger().error((Throwable) e);
                return new PluginLoadingError(ideaPluginDescriptor, message("plugin.loading.error.long.failed.to.load.requirements.for.ide.version", ideaPluginDescriptor.getName()), message("plugin.loading.error.short.failed.to.load.requirements.for.ide.version", new Object[0]));
            }
        }
        if (buildNumberFromString != null && buildNumberFromString.compareTo(buildNumber) > 0) {
            return new PluginLoadingError(ideaPluginDescriptor, message("plugin.loading.error.long.incompatible.since.build", ideaPluginDescriptor.getName(), ideaPluginDescriptor.getVersion(), sinceBuild, buildNumber), message("plugin.loading.error.short.incompatible.since.build", sinceBuild));
        }
        BuildNumber buildNumberFromString2 = untilBuild == null ? null : BuildNumber.fromString(untilBuild, ideaPluginDescriptor.getName(), null);
        if (buildNumberFromString2 == null || buildNumberFromString2.compareTo(buildNumber) >= 0) {
            return null;
        }
        return new PluginLoadingError(ideaPluginDescriptor, message("plugin.loading.error.long.incompatible.until.build", ideaPluginDescriptor.getName(), ideaPluginDescriptor.getVersion(), untilBuild, buildNumber), message("plugin.loading.error.short.incompatible.until.build", untilBuild));
    }

    private static Supplier<String> message(final String str, final Object... objArr) {
        if (str == null) {
            $$$reportNull$$$0(59);
        }
        if (objArr == null) {
            $$$reportNull$$$0(60);
        }
        return new Supplier<String>() { // from class: org.jetbrains.kotlin.com.intellij.ide.plugins.PluginManagerCore.1
            @Override // java.util.function.Supplier
            public String get() {
                return CoreBundle.message(str, objArr);
            }
        };
    }

    public static Logger getLogger() {
        Logger logger = Logger.getInstance("#com.intellij.ide.plugins.PluginManager");
        if (logger == null) {
            $$$reportNull$$$0(64);
        }
        return logger;
    }
}
