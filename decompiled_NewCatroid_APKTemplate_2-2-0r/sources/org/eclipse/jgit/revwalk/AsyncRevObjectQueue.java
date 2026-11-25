package org.eclipse.jgit.revwalk;

import java.io.IOException;
import org.eclipse.jgit.lib.AsyncOperation;

/* JADX INFO: loaded from: classes13.dex */
public interface AsyncRevObjectQueue extends AsyncOperation {
    RevObject next() throws IOException;
}
