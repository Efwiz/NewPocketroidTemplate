package org.eclipse.jgit.events;

import org.eclipse.jgit.events.RepositoryListener;
import org.eclipse.jgit.lib.Repository;

/* JADX INFO: loaded from: classes13.dex */
public abstract class RepositoryEvent<T extends RepositoryListener> {
    private Repository repository;

    public abstract void dispatch(T t);

    public abstract Class<T> getListenerType();

    public void setRepository(Repository repository) {
        if (this.repository == null) {
            this.repository = repository;
        }
    }

    public Repository getRepository() {
        return this.repository;
    }

    public String toString() {
        String simpleName = getClass().getSimpleName();
        return this.repository == null ? simpleName : String.valueOf(simpleName) + "[" + this.repository + "]";
    }
}
