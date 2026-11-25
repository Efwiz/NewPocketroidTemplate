package org.jetbrains.kotlin.com.intellij.ide.plugins;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.com.intellij.openapi.application.PathManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationInfoEx;
import org.jetbrains.kotlin.com.intellij.openapi.application.impl.ApplicationInfoImpl;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.NioFiles;

/* JADX INFO: loaded from: classes3.dex */
public final class DisabledPluginsState {
    private static final List<Runnable> ourDisabledPluginListeners = new CopyOnWriteArrayList();
    private static volatile Set<PluginId> ourDisabledPlugins;
    private static volatile boolean ourIgnoreDisabledPlugins;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 21 || i == 28 || i == 30 || i == 25 || i == 26) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 21 || i == 28 || i == 30 || i == 25 || i == 26) ? 2 : 3];
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 21:
            case 25:
            case 26:
            case 28:
            case 30:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/ide/plugins/DisabledPluginsState";
                break;
            case 7:
                objArr[0] = "pluginId";
                break;
            case 8:
            case 9:
                objArr[0] = "descriptors";
                break;
            case 10:
            case 11:
            case 13:
            case 15:
            case 18:
            case 23:
            case 29:
                objArr[0] = "pluginIds";
                break;
            case 12:
                objArr[0] = "plugins";
                break;
            case 14:
            case 17:
            case 20:
                objArr[0] = "file";
                break;
            case 16:
                objArr[0] = "configDir";
                break;
            case 19:
                objArr[0] = "ids";
                break;
            case 22:
                objArr[0] = "id";
                break;
            case 24:
                objArr[0] = "applicationInfo";
                break;
            case 27:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            default:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
        }
        if (i == 2 || i == 3) {
            objArr[1] = "loadDisabledPlugins";
        } else if (i == 4) {
            objArr[1] = "disabledPlugins";
        } else if (i == 5 || i == 6) {
            objArr[1] = "getDisabledIds";
        } else if (i == 21) {
            objArr[1] = "getLogger";
        } else if (i == 28) {
            objArr[1] = "splitByComma";
        } else if (i == 30) {
            objArr[1] = "joinedPluginIds";
        } else if (i == 25 || i == 26) {
            objArr[1] = "getNonEssentialSuppressedPlugins";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/ide/plugins/DisabledPluginsState";
        }
        switch (i) {
            case 1:
                objArr[2] = "removeDisablePluginListener";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 21:
            case 25:
            case 26:
            case 28:
            case 30:
                break;
            case 7:
                objArr[2] = "isDisabled";
                break;
            case 8:
                objArr[2] = CommonCompilerArguments.ENABLE;
                break;
            case 9:
                objArr[2] = "disable";
                break;
            case 10:
                objArr[2] = "enableById";
                break;
            case 11:
                objArr[2] = "disableById";
                break;
            case 12:
                objArr[2] = "setEnabledState";
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "trySaveDisabledPlugins";
                break;
            case 16:
            case 17:
            case 18:
                objArr[2] = "saveDisabledPlugins";
                break;
            case 19:
            case 20:
                objArr[2] = "savePluginsList";
                break;
            case 22:
            case 23:
                objArr[2] = "addIdTo";
                break;
            case 24:
                objArr[2] = "getNonEssentialSuppressedPlugins";
                break;
            case 27:
                objArr[2] = "splitByComma";
                break;
            case 29:
                objArr[2] = "joinedPluginIds";
                break;
            default:
                objArr[2] = "addDisablePluginListener";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6 && i != 21 && i != 28 && i != 30 && i != 25 && i != 26) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    DisabledPluginsState() {
    }

    public static void setIgnoreDisabledPlugins(boolean z) {
        ourIgnoreDisabledPlugins = z;
    }

    /* JADX WARN: Code duplicated, block: B:44:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:48:0x00aa  */
    public static Set<PluginId> loadDisabledPlugins() throws Throwable {
        boolean z;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Path path = Paths.get(PathManager.getConfigPath(), "disabled_plugins.txt");
        if (!Files.isRegularFile(path, new LinkOption[0])) {
            return linkedHashSet;
        }
        ApplicationInfoEx shadowInstance = ApplicationInfoImpl.getShadowInstance();
        List<String> listSplitByComma = splitByComma("idea.required.plugins.id");
        try {
            try {
                BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(path);
                z = false;
                while (true) {
                    try {
                        try {
                            String line = bufferedReaderNewBufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            String strTrim = line.trim();
                            if (!strTrim.isEmpty()) {
                                if (listSplitByComma.contains(strTrim) || shadowInstance.isEssentialPlugin(strTrim)) {
                                    z = true;
                                } else {
                                    addIdTo(strTrim, linkedHashSet);
                                }
                            }
                        } catch (Throwable th) {
                            if (bufferedReaderNewBufferedReader != null) {
                                try {
                                    bufferedReaderNewBufferedReader.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e) {
                        e = e;
                        getLogger().info("Unable to load disabled plugins list from " + path, e);
                        if (z) {
                            trySaveDisabledPlugins(path, linkedHashSet, false);
                        }
                    }
                }
                Iterator<String> it = getNonEssentialSuppressedPlugins(shadowInstance).iterator();
                while (it.hasNext()) {
                    if (addIdTo(it.next(), linkedHashSet)) {
                        z = true;
                    }
                }
                if (bufferedReaderNewBufferedReader != null) {
                    bufferedReaderNewBufferedReader.close();
                }
                if (z) {
                    trySaveDisabledPlugins(path, linkedHashSet, false);
                }
            } catch (Throwable th3) {
                th = th3;
                if (0 != 0) {
                    trySaveDisabledPlugins(path, linkedHashSet, false);
                }
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            z = false;
        } catch (Throwable th4) {
            th = th4;
            if (0 != 0) {
                trySaveDisabledPlugins(path, linkedHashSet, false);
            }
            throw th;
        }
        return linkedHashSet;
    }

    public static Set<PluginId> disabledPlugins() {
        Set<PluginId> setUnmodifiableSet = Collections.unmodifiableSet(getDisabledIds());
        if (setUnmodifiableSet == null) {
            $$$reportNull$$$0(4);
        }
        return setUnmodifiableSet;
    }

    private static Set<PluginId> getDisabledIds() {
        Set<PluginId> setLoadDisabledPlugins;
        Set<PluginId> set = ourDisabledPlugins;
        if (set != null) {
            if (set == null) {
                $$$reportNull$$$0(5);
            }
            return set;
        }
        if (ourIgnoreDisabledPlugins || System.getProperty("idea.ignore.disabled.plugins") != null) {
            return new HashSet();
        }
        synchronized (DisabledPluginsState.class) {
            setLoadDisabledPlugins = ourDisabledPlugins;
            if (setLoadDisabledPlugins == null) {
                setLoadDisabledPlugins = loadDisabledPlugins();
                ourDisabledPlugins = setLoadDisabledPlugins;
            }
        }
        if (setLoadDisabledPlugins == null) {
            $$$reportNull$$$0(6);
        }
        return setLoadDisabledPlugins;
    }

    private static boolean trySaveDisabledPlugins(Path path, Collection<PluginId> collection, boolean z) {
        if (path == null) {
            $$$reportNull$$$0(14);
        }
        if (collection == null) {
            $$$reportNull$$$0(15);
        }
        try {
            saveDisabledPlugins(path, collection, z);
            return true;
        } catch (IOException e) {
            getLogger().warn("Unable to save disabled plugins list", e);
            return false;
        }
    }

    private static void saveDisabledPlugins(Path path, Collection<PluginId> collection, boolean z) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(17);
        }
        if (collection == null) {
            $$$reportNull$$$0(18);
        }
        savePluginsList(collection, path);
        if (z) {
            invalidate();
        }
        Iterator<Runnable> it = ourDisabledPluginListeners.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
    }

    public static void savePluginsList(Collection<PluginId> collection, Path path) throws IOException {
        if (collection == null) {
            $$$reportNull$$$0(19);
        }
        if (path == null) {
            $$$reportNull$$$0(20);
        }
        NioFiles.createDirectories(path.getParent());
        BufferedWriter bufferedWriterNewBufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);
        try {
            PluginManagerCore.writePluginsList(collection, bufferedWriterNewBufferedWriter);
            if (bufferedWriterNewBufferedWriter != null) {
                bufferedWriterNewBufferedWriter.close();
            }
        } catch (Throwable th) {
            if (bufferedWriterNewBufferedWriter != null) {
                try {
                    bufferedWriterNewBufferedWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static Logger getLogger() {
        Logger logger = Logger.getInstance("#com.intellij.ide.plugins.DisabledPluginsState");
        if (logger == null) {
            $$$reportNull$$$0(21);
        }
        return logger;
    }

    static void invalidate() {
        ourDisabledPlugins = null;
    }

    private static boolean addIdTo(String str, Collection<PluginId> collection) {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        if (collection == null) {
            $$$reportNull$$$0(23);
        }
        return collection.add(PluginId.getId(str));
    }

    private static List<String> getNonEssentialSuppressedPlugins(ApplicationInfoEx applicationInfoEx) {
        if (applicationInfoEx == null) {
            $$$reportNull$$$0(24);
        }
        List<String> listSplitByComma = splitByComma("idea.suppressed.plugins.id");
        if (listSplitByComma.isEmpty()) {
            List<String> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(25);
            }
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList(listSplitByComma.size());
        for (String str : listSplitByComma) {
            if (!applicationInfoEx.isEssentialPlugin(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    private static List<String> splitByComma(String str) {
        List<String> listEmptyList;
        if (str == null) {
            $$$reportNull$$$0(27);
        }
        String[] strArrSplit = System.getProperty(str, "").split(",");
        if (strArrSplit.length == 0 || (strArrSplit.length == 1 && strArrSplit[0].isEmpty())) {
            listEmptyList = Collections.emptyList();
        } else {
            listEmptyList = Arrays.asList(strArrSplit);
        }
        if (listEmptyList == null) {
            $$$reportNull$$$0(28);
        }
        return listEmptyList;
    }
}
