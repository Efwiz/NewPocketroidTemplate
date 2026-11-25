package org.eclipse.jgit.lib;

import java.io.IOException;
import org.eclipse.jgit.lib.ObjectId;

/* JADX INFO: loaded from: classes13.dex */
public interface AsyncObjectSizeQueue<T extends ObjectId> extends AsyncOperation {
    T getCurrent();

    ObjectId getObjectId();

    long getSize();

    boolean next() throws IOException;
}
