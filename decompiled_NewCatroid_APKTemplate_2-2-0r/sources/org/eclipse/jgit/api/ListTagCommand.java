package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;

/* JADX INFO: loaded from: classes13.dex */
public class ListTagCommand extends GitCommand<List<Ref>> {
    protected ListTagCommand(Repository repository) {
        super(repository);
    }

    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public List<Ref> call() throws GitAPIException {
        checkCallable();
        ArrayList arrayList = new ArrayList();
        Throwable th = null;
        try {
            RevWalk revWalk = new RevWalk(this.repo);
            try {
                Iterator<Ref> it = this.repo.getRefDatabase().getRefsByPrefix(Constants.R_TAGS).iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next());
                }
                revWalk.close();
                Collections.sort(arrayList, new Comparator() { // from class: org.eclipse.jgit.api.ListTagCommand$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ((Ref) obj).getName().compareTo(((Ref) obj2).getName());
                    }
                });
                setCallable(false);
                return arrayList;
            } catch (Throwable th2) {
                revWalk.close();
                throw th2;
            }
        } catch (Throwable th3) {
            if (0 == 0) {
                throw th3;
            }
            if (null != th3) {
                try {
                    th.addSuppressed(th3);
                } catch (IOException e) {
                    throw new JGitInternalException(e.getMessage(), e);
                }
            }
            throw null;
        }
    }
}
