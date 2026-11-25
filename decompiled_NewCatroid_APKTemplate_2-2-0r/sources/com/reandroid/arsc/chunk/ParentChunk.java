package com.reandroid.arsc.chunk;

import com.reandroid.arsc.pool.StringPool;

/* JADX INFO: loaded from: classes10.dex */
public interface ParentChunk {
    MainChunk getMainChunk();

    PackageBlock getPackageBlock();

    StringPool<?> getSpecStringPool();
}
