package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.BundleWriter;

/* JADX INFO: loaded from: classes13.dex */
public class DfsBundleWriter {
    public static void writeEntireRepositoryAsBundle(ProgressMonitor progressMonitor, OutputStream outputStream, DfsRepository dfsRepository) throws IOException {
        final BundleWriter bundleWriter = new BundleWriter(dfsRepository);
        dfsRepository.getRefDatabase().getRefs().forEach(new Consumer() { // from class: org.eclipse.jgit.internal.storage.dfs.DfsBundleWriter$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                bundleWriter.include((Ref) obj);
            }
        });
        ArrayList arrayList = new ArrayList();
        for (DfsPackFile dfsPackFile : dfsRepository.getObjectDatabase().getPacks()) {
            arrayList.add(new DfsCachedPack(dfsPackFile));
        }
        bundleWriter.addObjectsAsIs(arrayList);
        bundleWriter.writeBundle(progressMonitor, outputStream);
    }

    private DfsBundleWriter() {
    }
}
