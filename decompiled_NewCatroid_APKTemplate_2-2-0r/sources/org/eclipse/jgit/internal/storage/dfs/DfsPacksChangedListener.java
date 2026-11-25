package org.eclipse.jgit.internal.storage.dfs;

import org.eclipse.jgit.events.RepositoryListener;

/* JADX INFO: loaded from: classes13.dex */
public interface DfsPacksChangedListener extends RepositoryListener {
    void onPacksChanged(DfsPacksChangedEvent dfsPacksChangedEvent);
}
