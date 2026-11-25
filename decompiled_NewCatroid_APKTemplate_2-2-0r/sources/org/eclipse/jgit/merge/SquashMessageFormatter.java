package org.eclipse.jgit.merge;

import java.util.List;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.GitDateFormatter;
import org.koin.core.instance.InstanceFactory;

/* JADX INFO: loaded from: classes13.dex */
public class SquashMessageFormatter {
    private GitDateFormatter dateFormatter = new GitDateFormatter(GitDateFormatter.Format.DEFAULT);

    public String format(List<RevCommit> list, Ref ref) {
        StringBuilder sb = new StringBuilder("Squashed commit of the following:\n");
        for (RevCommit revCommit : list) {
            sb.append("\ncommit ");
            sb.append(revCommit.getName());
            sb.append("\n");
            sb.append(toString(revCommit.getAuthorIdent()));
            sb.append(InstanceFactory.ERROR_SEPARATOR);
            sb.append(revCommit.getShortMessage());
            sb.append("\n");
        }
        return sb.toString();
    }

    private String toString(PersonIdent personIdent) {
        return "Author: " + personIdent.getName() + " <" + personIdent.getEmailAddress() + ">\nDate:   " + this.dateFormatter.formatDate(personIdent) + "\n";
    }
}
