package org.eclipse.jgit.events;

/* JADX INFO: loaded from: classes13.dex */
public class ListenerHandle {
    final RepositoryListener listener;
    private final ListenerList parent;
    final Class<? extends RepositoryListener> type;

    ListenerHandle(ListenerList listenerList, Class<? extends RepositoryListener> cls, RepositoryListener repositoryListener) {
        this.parent = listenerList;
        this.type = cls;
        this.listener = repositoryListener;
    }

    public void remove() {
        this.parent.remove(this);
    }

    public String toString() {
        return String.valueOf(this.type.getSimpleName()) + "[" + this.listener + "]";
    }
}
