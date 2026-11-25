package org.jetbrains.kotlin.js.sourceMap;

import java.io.File;

/* JADX INFO: loaded from: classes5.dex */
public interface SourceMapBuilder extends SourceMapMappingConsumer {
    String build();

    File getOutFile();

    void skipLinesAtBeginning(int i);
}
