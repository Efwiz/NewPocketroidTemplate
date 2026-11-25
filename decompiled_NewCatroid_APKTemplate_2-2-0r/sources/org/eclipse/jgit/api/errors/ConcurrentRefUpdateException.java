package org.eclipse.jgit.api.errors;

import java.text.MessageFormat;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;

/* JADX INFO: loaded from: classes13.dex */
public class ConcurrentRefUpdateException extends GitAPIException {
    private static final long serialVersionUID = 1;
    private RefUpdate.Result rc;
    private Ref ref;

    public ConcurrentRefUpdateException(String str, Ref ref, RefUpdate.Result result, Throwable th) {
        super(result != null ? String.valueOf(str) + ". " + MessageFormat.format(JGitText.get().refUpdateReturnCodeWas, result) : str, th);
        this.rc = result;
        this.ref = ref;
    }

    public ConcurrentRefUpdateException(String str, Ref ref, RefUpdate.Result result) {
        super(result != null ? String.valueOf(str) + ". " + MessageFormat.format(JGitText.get().refUpdateReturnCodeWas, result) : str);
        this.rc = result;
        this.ref = ref;
    }

    public Ref getRef() {
        return this.ref;
    }

    public RefUpdate.Result getResult() {
        return this.rc;
    }
}
