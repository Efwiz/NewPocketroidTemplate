package org.jetbrains.kotlin.com.intellij.util.indexing;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.ide.plugins.PluginUtil;
import org.jetbrains.kotlin.com.intellij.openapi.application.PathManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.IntObjectMap;
import org.jetbrains.kotlin.com.intellij.util.io.SimpleStringPersistentEnumerator;

/* JADX INFO: loaded from: classes3.dex */
public class ID<K, V> extends IndexId<K, V> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final short myUniqueId;
    private static final Logger LOG = Logger.getInstance((Class<?>) ID.class);
    private static final IntObjectMap<ID<?, ?>> ourRegistry = ContainerUtil.createConcurrentIntObjectMap();
    private static final SimpleStringPersistentEnumerator ourNameToIdRegistry = new SimpleStringPersistentEnumerator(getEnumFile());
    private static final Map<ID<?, ?>, PluginId> ourIdToPluginId = Collections.synchronizedMap(new HashMap());
    private static final Map<ID<?, ?>, Throwable> ourIdToRegistrationStackTrace = Collections.synchronizedMap(new HashMap());

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 5 || i == 6 || i == 7 || i == 10) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 5 || i == 6 || i == 7 || i == 10) ? 3 : 2];
        if (i == 1 || i == 2 || i == 3 || i == 5 || i == 6 || i == 7) {
            objArr[0] = "name";
        } else if (i != 10) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/indexing/ID";
        } else {
            objArr[0] = "id";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 10:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/indexing/ID";
                break;
            case 4:
                objArr[1] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 8:
                objArr[1] = "getInvalidIdAccessMessage";
                break;
            case 9:
                objArr[1] = "getRegistrationTrace";
                break;
            default:
                objArr[1] = "getEnumFile";
                break;
        }
        if (i == 1) {
            objArr[2] = "<init>";
        } else if (i == 2) {
            objArr[2] = "stringToId";
        } else if (i == 3) {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else if (i == 5 || i == 6) {
            objArr[2] = "findByName";
        } else if (i == 7) {
            objArr[2] = "getInvalidIdAccessMessage";
        } else if (i == 10) {
            objArr[2] = "unloadId";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 5 && i != 6 && i != 7 && i != 10) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public final boolean equals(Object obj) {
        return this == obj;
    }

    private static Path getEnumFile() {
        Path pathResolve = PathManager.getIndexRoot().resolve("indices.enum");
        if (pathResolve == null) {
            $$$reportNull$$$0(0);
        }
        return pathResolve;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected ID(String str, PluginId pluginId) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        short sStringToId = stringToId(str);
        this.myUniqueId = sStringToId;
        ourRegistry.put(sStringToId, this);
        ourIdToPluginId.put(this, pluginId);
        ourIdToRegistrationStackTrace.put(this, new Throwable());
    }

    private static short stringToId(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return ourNameToIdRegistry.enumerate(str);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001b A[Catch: all -> 0x0021, TRY_LEAVE, TryCatch #0 {, blocks: (B:6:0x0006, B:7:0x0009, B:9:0x0014, B:11:0x001b), top: B:17:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:9:0x0014 A[Catch: all -> 0x0021, TryCatch #0 {, blocks: (B:6:0x0006, B:7:0x0009, B:9:0x0014, B:11:0x001b), top: B:17:0x0006 }] */
    /* JADX WARN: In static synchronized method top region not synchronized by class const: (wrap java.lang.Class:0x0000: CONST_CLASS  A[WRAPPED] org.jetbrains.kotlin.com.intellij.util.indexing.ID.class) */
    public static synchronized <K, V> ID<K, V> create(String str) {
        PluginId callerPluginId;
        ID<K, V> idFindByName;
        synchronized (ID.class) {
            if (str == null) {
                $$$reportNull$$$0(3);
                callerPluginId = getCallerPluginId();
                idFindByName = findByName(str, true, callerPluginId);
                if (idFindByName == null) {
                    idFindByName = new ID<>(str, callerPluginId);
                }
                if (idFindByName == null) {
                    $$$reportNull$$$0(4);
                }
            } else {
                callerPluginId = getCallerPluginId();
                idFindByName = findByName(str, true, callerPluginId);
                if (idFindByName == null) {
                    idFindByName = new ID<>(str, callerPluginId);
                }
                if (idFindByName == null) {
                    $$$reportNull$$$0(4);
                }
            }
            throw th;
        }
        return idFindByName;
    }

    public static <K, V> ID<K, V> findByName(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return findByName(str, false, null);
    }

    protected static <K, V> ID<K, V> findByName(String str, boolean z, PluginId pluginId) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        ID<K, V> id2 = (ID<K, V>) findById(stringToId(str));
        if (z && id2 != null) {
            PluginId pluginId2 = ourIdToPluginId.get(id2);
            String idString = "IDEA Core";
            String idString2 = pluginId2 == null ? "IDEA Core" : pluginId2.getIdString();
            if (pluginId != null) {
                idString = pluginId.getIdString();
            }
            if (!Objects.equals(idString2, idString)) {
                Throwable th = ourIdToRegistrationStackTrace.get(id2);
                String invalidIdAccessMessage = getInvalidIdAccessMessage(str, idString2, idString, th);
                if (th != null) {
                    throw new AssertionError(invalidIdAccessMessage, th);
                }
                throw new AssertionError(invalidIdAccessMessage);
            }
        }
        return id2;
    }

    private static String getInvalidIdAccessMessage(String str, String str2, String str3, Throwable th) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        String str4 = "ID with name '" + str + "' requested for plugin " + str3 + " but registered for " + str2 + " plugin. Please use an instance field to access corresponding ID." + (th == null ? " Registration stack trace: " : "");
        if (str4 == null) {
            $$$reportNull$$$0(8);
        }
        return str4;
    }

    public Throwable getRegistrationTrace() {
        Throwable th = ourIdToRegistrationStackTrace.get(this);
        if (th == null) {
            $$$reportNull$$$0(9);
        }
        return th;
    }

    public final int hashCode() {
        return this.myUniqueId;
    }

    public int getUniqueId() {
        return this.myUniqueId;
    }

    public PluginId getPluginId() {
        return ourIdToPluginId.get(this);
    }

    public static ID<?, ?> findById(int i) {
        return ourRegistry.get(i);
    }

    protected static PluginId getCallerPluginId() {
        return PluginUtil.getInstance().getCallerPlugin(4);
    }

    /* JADX WARN: In static synchronized method top region not synchronized by class const: (wrap java.lang.Class:0x0000: CONST_CLASS  A[WRAPPED] org.jetbrains.kotlin.com.intellij.util.indexing.ID.class) */
    public static synchronized void unloadId(ID<?, ?> id2) {
        synchronized (ID.class) {
            if (id2 == null) {
                $$$reportNull$$$0(10);
            }
            LOG.assertTrue(id2.equals(ourRegistry.remove(id2.getUniqueId())));
            ourIdToPluginId.remove(id2);
            ourIdToRegistrationStackTrace.remove(id2);
        }
    }
}
