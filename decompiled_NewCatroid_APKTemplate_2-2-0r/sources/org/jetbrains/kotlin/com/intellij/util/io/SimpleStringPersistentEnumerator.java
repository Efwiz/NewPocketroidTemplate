package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortMaps;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortOpenHashMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;

/* JADX INFO: loaded from: classes3.dex */
public final class SimpleStringPersistentEnumerator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Path myFile;
    private final Object2ShortMap<String> myState;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/SimpleStringPersistentEnumerator";
        } else if (i != 4) {
            objArr[0] = "file";
        } else {
            objArr[0] = "state";
        }
        if (i == 1) {
            objArr[1] = "dumpToString";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/SimpleStringPersistentEnumerator";
        } else {
            objArr[1] = "readStorageFromDisk";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "readStorageFromDisk";
            } else if (i != 3) {
                if (i == 4 || i == 5) {
                    objArr[2] = "writeStorageToDisk";
                } else {
                    objArr[2] = "<init>";
                }
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public SimpleStringPersistentEnumerator(Path path) {
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        this.myFile = path;
        this.myState = readStorageFromDisk(path);
    }

    public synchronized short enumerate(String str) {
        if (this.myState.containsKey(str)) {
            return this.myState.getShort(str);
        }
        short size = (short) (this.myState.size() + 1);
        this.myState.put(str, size);
        writeStorageToDisk(this.myState, this.myFile);
        return size;
    }

    private static Object2ShortMap<String> readStorageFromDisk(Path path) {
        if (path == null) {
            $$$reportNull$$$0(2);
        }
        try {
            Object2ShortOpenHashMap object2ShortOpenHashMap = new Object2ShortOpenHashMap();
            List<String> allLines = Files.readAllLines(path, Charset.defaultCharset());
            int i = 0;
            while (i < allLines.size()) {
                String str = allLines.get(i);
                i++;
                object2ShortOpenHashMap.put(str, (short) i);
            }
            return object2ShortOpenHashMap;
        } catch (IOException unused) {
            writeStorageToDisk(Object2ShortMaps.emptyMap(), path);
            return new Object2ShortOpenHashMap();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void writeStorageToDisk(Object2ShortMap<String> object2ShortMap, Path path) {
        if (object2ShortMap == null) {
            $$$reportNull$$$0(4);
        }
        if (path == null) {
            $$$reportNull$$$0(5);
        }
        try {
            String[] strArr = new String[object2ShortMap.size()];
            ObjectIterator objectIteratorFastIterator = Object2ShortMaps.fastIterator(object2ShortMap);
            while (objectIteratorFastIterator.hasNext()) {
                Object2ShortMap.Entry entry = (Object2ShortMap.Entry) objectIteratorFastIterator.next();
                strArr[entry.getShortValue() - 1] = (String) entry.getKey();
            }
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            Files.write(path, Arrays.asList(strArr), Charset.defaultCharset(), new OpenOption[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
