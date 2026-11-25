package org.eclipse.jgit.transport;

import org.eclipse.jgit.storage.pack.PackStatistics;

/* JADX INFO: loaded from: classes13.dex */
public interface PostUploadHook {
    public static final PostUploadHook NULL = new PostUploadHook() { // from class: org.eclipse.jgit.transport.PostUploadHook$$ExternalSyntheticLambda0
        @Override // org.eclipse.jgit.transport.PostUploadHook
        public final void onPostUpload(PackStatistics packStatistics) {
            PostUploadHook.lambda$0(packStatistics);
        }
    };

    static /* synthetic */ void lambda$0(PackStatistics packStatistics) {
    }

    void onPostUpload(PackStatistics packStatistics);
}
