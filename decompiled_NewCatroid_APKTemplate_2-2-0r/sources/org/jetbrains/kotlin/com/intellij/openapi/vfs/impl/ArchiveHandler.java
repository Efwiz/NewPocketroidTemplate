package org.jetbrains.kotlin.com.intellij.openapi.vfs.impl;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileAttributes;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ArchiveHandler {
    public static final FileAttributes DIRECTORY_ATTRIBUTES = new FileAttributes(true, false, false, false, 0, -1, false, FileAttributes.CaseSensitivity.SENSITIVE);
    private static final AddonlyKeylessHash.KeyValueMapper<EntryInfo, Object> ourKeyValueMapper = new AddonlyKeylessHash.KeyValueMapper<EntryInfo, Object>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ArchiveHandler.1
    };
    private volatile Reference<AddonlyKeylessHash<EntryInfo, Object>> myChildrenEntries;
    private boolean myCorrupted;
    private volatile Reference<Map<String, EntryInfo>> myEntries;
    private final Object myLock;
    private final File myPath;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 9 || i == 15 || i == 18 || i == 20 || i == 4 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 9 || i == 15 || i == 18 || i == 20 || i == 4 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 15:
            case 18:
            case 20:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/ArchiveHandler";
                break;
            case 2:
            case 3:
            case 8:
            case 21:
                objArr[0] = "relativePath";
                break;
            case 10:
            case 12:
            case 16:
                objArr[0] = "map";
                break;
            case 11:
            case 13:
            case 14:
            case 17:
            case 19:
                objArr[0] = "entryName";
                break;
            default:
                objArr[0] = "path";
                break;
        }
        if (i == 1) {
            objArr[1] = "getFile";
        } else if (i == 9) {
            objArr[1] = "getEntriesMap";
        } else if (i == 15) {
            objArr[1] = "normalizeName";
        } else if (i == 18) {
            objArr[1] = "getOrCreate";
        } else if (i == 20) {
            objArr[1] = "splitPath";
        } else if (i == 4 || i == 5 || i == 6 || i == 7) {
            objArr[1] = SchemaSymbols.ATTVAL_LIST;
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/ArchiveHandler";
        }
        switch (i) {
            case 1:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 15:
            case 18:
            case 20:
                break;
            case 2:
                objArr[2] = "getAttributes";
                break;
            case 3:
                objArr[2] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 8:
                objArr[2] = "getEntryInfo";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[2] = "processEntry";
                break;
            case 14:
                objArr[2] = "normalizeName";
                break;
            case 16:
            case 17:
                objArr[2] = "getOrCreate";
                break;
            case 19:
                objArr[2] = "splitPath";
                break;
            case 21:
                objArr[2] = "getInputStream";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 9 && i != 15 && i != 18 && i != 20 && i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected abstract Map<String, EntryInfo> createEntriesMap() throws IOException;

    protected static class EntryInfo {
        public final boolean isDirectory;
        public final long length;
        public final EntryInfo parent;
        public final CharSequence shortName;
        public final long timestamp;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "shortName", "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/ArchiveHandler$EntryInfo", "<init>"));
        }

        public EntryInfo(CharSequence charSequence, boolean z, long j, long j2, EntryInfo entryInfo) {
            if (charSequence == null) {
                $$$reportNull$$$0(0);
            }
            this.parent = entryInfo;
            this.shortName = charSequence;
            this.isDirectory = z;
            this.length = j;
            this.timestamp = j2;
        }
    }

    protected ArchiveHandler(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myLock = new Object();
        this.myEntries = new SoftReference(null);
        this.myChildrenEntries = new SoftReference(null);
        this.myPath = new File(str);
    }

    public File getFile() {
        File file = this.myPath;
        if (file == null) {
            $$$reportNull$$$0(1);
        }
        return file;
    }

    public void clearCaches() {
        synchronized (this.myLock) {
            this.myEntries.clear();
            this.myChildrenEntries.clear();
            this.myCorrupted = false;
        }
    }

    protected EntryInfo getEntryInfo(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return getEntriesMap().get(str);
    }

    protected Map<String, EntryInfo> getEntriesMap() {
        Map<String, EntryInfo> mapEmptyMap = (Map) SoftReference.dereference(this.myEntries);
        if (mapEmptyMap == null) {
            synchronized (this.myLock) {
                mapEmptyMap = (Map) SoftReference.dereference(this.myEntries);
                if (mapEmptyMap == null) {
                    if (this.myCorrupted) {
                        mapEmptyMap = Collections.emptyMap();
                    } else {
                        try {
                            mapEmptyMap = createEntriesMap();
                        } catch (Exception e) {
                            this.myCorrupted = true;
                            Logger.getInstance(getClass()).warn(e.getMessage() + ": " + this.myPath, e);
                            mapEmptyMap = Collections.emptyMap();
                        }
                    }
                    this.myEntries = new SoftReference(mapEmptyMap);
                    this.myChildrenEntries = new SoftReference(null);
                }
            }
        }
        if (mapEmptyMap == null) {
            $$$reportNull$$$0(9);
        }
        return mapEmptyMap;
    }

    protected EntryInfo createRootEntry() {
        return new EntryInfo("", true, 0L, -1L, null);
    }

    protected final void processEntry(Map<String, EntryInfo> map, Logger logger, String str, BiFunction<EntryInfo, String, ? extends EntryInfo> biFunction) {
        if (map == null) {
            $$$reportNull$$$0(12);
        }
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        String strNormalizeName = normalizeName(str);
        if (strNormalizeName.isEmpty() || (strNormalizeName.contains("..") && ArrayUtil.contains("..", strNormalizeName.split("/")))) {
            if (logger != null) {
                logger.trace("invalid entry: " + getFile() + "!/" + str);
            }
        } else {
            if (biFunction == null) {
                directoryEntry(map, logger, strNormalizeName);
                return;
            }
            if (map.get(strNormalizeName) == null) {
                Pair<String, String> pairSplit = split(strNormalizeName);
                map.put(strNormalizeName, biFunction.apply(directoryEntry(map, logger, pairSplit.first), pairSplit.second));
            } else if (logger != null) {
                logger.trace("duplicate entry: " + getFile() + "!/" + strNormalizeName);
            }
        }
    }

    protected String normalizeName(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        String strTrimTrailing = StringUtil.trimTrailing(StringUtil.trimLeading(FileUtil.normalize(str), '/'), '/');
        if (strTrimTrailing == null) {
            $$$reportNull$$$0(15);
        }
        return strTrimTrailing;
    }

    private EntryInfo directoryEntry(Map<String, EntryInfo> map, Logger logger, String str) {
        EntryInfo entryInfo;
        EntryInfo entryInfo2 = map.get(str);
        if (entryInfo2 != null && entryInfo2.isDirectory) {
            return entryInfo2;
        }
        if (logger != null && entryInfo2 != null) {
            logger.trace("duplicate entry: " + getFile() + "!/" + str);
        }
        if (str.isEmpty()) {
            entryInfo = createRootEntry();
        } else {
            Pair<String, String> pairSplit = split(str);
            entryInfo = new EntryInfo(pairSplit.second, true, 0L, -1L, directoryEntry(map, logger, pairSplit.first));
        }
        EntryInfo entryInfo3 = entryInfo;
        map.put(str, entryInfo3);
        return entryInfo3;
    }

    private static Pair<String, String> split(String str) {
        int iLastIndexOf = str.lastIndexOf(47);
        String strSubstring = iLastIndexOf > 0 ? str.substring(0, iLastIndexOf) : "";
        if (iLastIndexOf > 0) {
            str = str.substring(iLastIndexOf + 1);
        }
        return new Pair<>(strSubstring, str);
    }
}
