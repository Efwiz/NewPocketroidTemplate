package org.jetbrains.kotlin.compilerRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class OutputItemsCollectorImpl implements OutputItemsCollector {
    private final List<SimpleOutputItem> outputs = new ArrayList();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/compilerRunner/OutputItemsCollectorImpl", "getOutputs"));
    }

    @Override // org.jetbrains.kotlin.compilerRunner.OutputItemsCollector
    public void add(Collection<File> collection, File file) {
        this.outputs.add(new SimpleOutputItem(collection, file));
    }

    public List<SimpleOutputItem> getOutputs() {
        List<SimpleOutputItem> list = this.outputs;
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        return list;
    }
}
