package org.jetbrains.kotlin.com.intellij.util.containers;

import java.io.File;
import java.io.Serializable;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;

/* JADX INFO: loaded from: classes3.dex */
public final class FastUtilHashingStrategies {
    private static final Hash.Strategy<CharSequence> CASE_SENSITIVE = new FastUtilCharSequenceHashingStrategy(true);
    private static final Hash.Strategy<CharSequence> CASE_INSENSITIVE = new FastUtilCharSequenceHashingStrategy(false);
    public static final Hash.Strategy<File> FILE_HASH_STRATEGY = new SerializableHashStrategy<File>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.FastUtilHashingStrategies.1
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
        public int hashCode(File file) {
            return FileUtilRt.pathHashCode(file == null ? null : file.getPath());
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
        public boolean equals(File file, File file2) {
            return FileUtilRt.pathsEqual(file == null ? null : file.getPath(), file2 != null ? file2.getPath() : null);
        }
    };

    interface SerializableHashStrategy extends Hash.Strategy, Serializable {
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 4 ? 2 : 3];
        if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/FastUtilHashingStrategies";
        } else {
            objArr[0] = "hashingStrategy";
        }
        if (i == 1) {
            objArr[1] = "getStringStrategy";
        } else if (i == 2) {
            objArr[1] = "getCaseInsensitiveStringStrategy";
        } else if (i == 3) {
            objArr[1] = "getCanonicalStrategy";
        } else if (i != 4) {
            objArr[1] = "getCharSequenceStrategy";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/FastUtilHashingStrategies";
        }
        if (i == 4) {
            objArr[2] = "adaptAsNotNull";
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalArgumentException(str2);
        }
    }

    public static Hash.Strategy<CharSequence> getCharSequenceStrategy(boolean z) {
        Hash.Strategy<CharSequence> strategy = z ? CASE_SENSITIVE : CASE_INSENSITIVE;
        if (strategy == null) {
            $$$reportNull$$$0(0);
        }
        return strategy;
    }

    public static Hash.Strategy<String> getCaseInsensitiveStringStrategy() {
        Hash.Strategy<String> strategy = FastUtilCaseInsensitiveStringHashingStrategy.INSTANCE;
        if (strategy == null) {
            $$$reportNull$$$0(2);
        }
        return strategy;
    }
}
