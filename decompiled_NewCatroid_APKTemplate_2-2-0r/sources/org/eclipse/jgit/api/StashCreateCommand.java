package org.eclipse.jgit.api;

import java.io.IOException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/* JADX INFO: loaded from: classes13.dex */
public class StashCreateCommand extends GitCommand<RevCommit> {
    private static final String MSG_INDEX = "index on {0}: {1} {2}";
    private static final String MSG_UNTRACKED = "untracked files on {0}: {1} {2}";
    private static final String MSG_WORKING_DIR = "WIP on {0}: {1} {2}";
    private boolean includeUntracked;
    private String indexMessage;
    private PersonIdent person;
    private String ref;
    private String workingDirectoryMessage;

    public StashCreateCommand(Repository repository) {
        super(repository);
        this.indexMessage = MSG_INDEX;
        this.workingDirectoryMessage = MSG_WORKING_DIR;
        this.ref = Constants.R_STASH;
        this.person = new PersonIdent(repository);
    }

    public StashCreateCommand setIndexMessage(String str) {
        this.indexMessage = str;
        return this;
    }

    public StashCreateCommand setWorkingDirectoryMessage(String str) {
        this.workingDirectoryMessage = str;
        return this;
    }

    public StashCreateCommand setPerson(PersonIdent personIdent) {
        this.person = personIdent;
        return this;
    }

    public StashCreateCommand setRef(String str) {
        this.ref = str;
        return this;
    }

    public StashCreateCommand setIncludeUntracked(boolean z) {
        this.includeUntracked = z;
        return this;
    }

    private RevCommit parseCommit(ObjectReader objectReader, ObjectId objectId) throws IOException {
        Throwable th = null;
        try {
            RevWalk revWalk = new RevWalk(objectReader);
            try {
                return revWalk.parseCommit(objectId);
            } finally {
                revWalk.close();
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                th.addSuppressed(th2);
            }
            throw null;
        }
    }

    private CommitBuilder createBuilder() {
        CommitBuilder commitBuilder = new CommitBuilder();
        PersonIdent personIdent = this.person;
        if (personIdent == null) {
            personIdent = new PersonIdent(this.repo);
        }
        commitBuilder.setAuthor(personIdent);
        commitBuilder.setCommitter(personIdent);
        return commitBuilder;
    }

    private void updateStashRef(ObjectId objectId, PersonIdent personIdent, String str) throws IOException {
        if (this.ref == null) {
            return;
        }
        Ref refFindRef = this.repo.findRef(this.ref);
        RefUpdate refUpdateUpdateRef = this.repo.updateRef(this.ref);
        refUpdateUpdateRef.setNewObjectId(objectId);
        refUpdateUpdateRef.setRefLogIdent(personIdent);
        refUpdateUpdateRef.setRefLogMessage(str, false);
        refUpdateUpdateRef.setForceRefLog(true);
        if (refFindRef != null) {
            refUpdateUpdateRef.setExpectedOldObjectId(refFindRef.getObjectId());
        } else {
            refUpdateUpdateRef.setExpectedOldObjectId(ObjectId.zeroId());
        }
        refUpdateUpdateRef.forceUpdate();
    }

    private Ref getHead() throws GitAPIException {
        try {
            Ref refExactRef = this.repo.exactRef("HEAD");
            if (refExactRef == null || refExactRef.getObjectId() == null) {
                throw new NoHeadException(JGitText.get().headRequiredToStash);
            }
            return refExactRef;
        } catch (IOException e) {
            throw new JGitInternalException(JGitText.get().stashFailed, e);
        }
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x03d1 */
    /* JADX WARN: Code duplicated, block: B:109:0x01d5  */
    /* JADX WARN: Code duplicated, block: B:110:0x01e3 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:116:0x01ef  */
    /* JADX WARN: Code duplicated, block: B:119:0x0231 A[Catch: all -> 0x036c, TryCatch #4 {all -> 0x036c, blocks: (B:117:0x01f1, B:119:0x0231, B:120:0x023d, B:122:0x0243, B:125:0x0280, B:127:0x0286, B:136:0x02a7, B:138:0x02af, B:139:0x02b2, B:141:0x02e9, B:142:0x02ed, B:145:0x02f4, B:129:0x028c, B:130:0x0294, B:132:0x029a, B:133:0x029e, B:135:0x02a4, B:166:0x0351, B:167:0x0361, B:123:0x0271), top: B:239:0x01f1 }] */
    /* JADX WARN: Code duplicated, block: B:123:0x0271 A[Catch: all -> 0x036c, LOOP:1: B:120:0x023d->B:123:0x0271, LOOP_END, TryCatch #4 {all -> 0x036c, blocks: (B:117:0x01f1, B:119:0x0231, B:120:0x023d, B:122:0x0243, B:125:0x0280, B:127:0x0286, B:136:0x02a7, B:138:0x02af, B:139:0x02b2, B:141:0x02e9, B:142:0x02ed, B:145:0x02f4, B:129:0x028c, B:130:0x0294, B:132:0x029a, B:133:0x029e, B:135:0x02a4, B:166:0x0351, B:167:0x0361, B:123:0x0271), top: B:239:0x01f1 }] */
    /* JADX WARN: Code duplicated, block: B:124:0x027f  */
    /* JADX WARN: Code duplicated, block: B:129:0x028c A[Catch: all -> 0x036c, TryCatch #4 {all -> 0x036c, blocks: (B:117:0x01f1, B:119:0x0231, B:120:0x023d, B:122:0x0243, B:125:0x0280, B:127:0x0286, B:136:0x02a7, B:138:0x02af, B:139:0x02b2, B:141:0x02e9, B:142:0x02ed, B:145:0x02f4, B:129:0x028c, B:130:0x0294, B:132:0x029a, B:133:0x029e, B:135:0x02a4, B:166:0x0351, B:167:0x0361, B:123:0x0271), top: B:239:0x01f1 }] */
    /* JADX WARN: Code duplicated, block: B:138:0x02af A[Catch: all -> 0x036c, TryCatch #4 {all -> 0x036c, blocks: (B:117:0x01f1, B:119:0x0231, B:120:0x023d, B:122:0x0243, B:125:0x0280, B:127:0x0286, B:136:0x02a7, B:138:0x02af, B:139:0x02b2, B:141:0x02e9, B:142:0x02ed, B:145:0x02f4, B:129:0x028c, B:130:0x0294, B:132:0x029a, B:133:0x029e, B:135:0x02a4, B:166:0x0351, B:167:0x0361, B:123:0x0271), top: B:239:0x01f1 }] */
    /* JADX WARN: Code duplicated, block: B:141:0x02e9 A[Catch: all -> 0x036c, TryCatch #4 {all -> 0x036c, blocks: (B:117:0x01f1, B:119:0x0231, B:120:0x023d, B:122:0x0243, B:125:0x0280, B:127:0x0286, B:136:0x02a7, B:138:0x02af, B:139:0x02b2, B:141:0x02e9, B:142:0x02ed, B:145:0x02f4, B:129:0x028c, B:130:0x0294, B:132:0x029a, B:133:0x029e, B:135:0x02a4, B:166:0x0351, B:167:0x0361, B:123:0x0271), top: B:239:0x01f1 }] */
    /* JADX WARN: Code duplicated, block: B:145:0x02f4 A[Catch: all -> 0x036c, LOOP:2: B:142:0x02ed->B:145:0x02f4, LOOP_END, TRY_LEAVE, TryCatch #4 {all -> 0x036c, blocks: (B:117:0x01f1, B:119:0x0231, B:120:0x023d, B:122:0x0243, B:125:0x0280, B:127:0x0286, B:136:0x02a7, B:138:0x02af, B:139:0x02b2, B:141:0x02e9, B:142:0x02ed, B:145:0x02f4, B:129:0x028c, B:130:0x0294, B:132:0x029a, B:133:0x029e, B:135:0x02a4, B:166:0x0351, B:167:0x0361, B:123:0x0271), top: B:239:0x01f1 }] */
    /* JADX WARN: Code duplicated, block: B:161:0x0342  */
    /* JADX WARN: Code duplicated, block: B:165:0x0350  */
    /* JADX WARN: Code duplicated, block: B:167:0x0361 A[Catch: all -> 0x036c, LOOP:3: B:130:0x0294->B:167:0x0361, LOOP_END, TRY_LEAVE, TryCatch #4 {all -> 0x036c, blocks: (B:117:0x01f1, B:119:0x0231, B:120:0x023d, B:122:0x0243, B:125:0x0280, B:127:0x0286, B:136:0x02a7, B:138:0x02af, B:139:0x02b2, B:141:0x02e9, B:142:0x02ed, B:145:0x02f4, B:129:0x028c, B:130:0x0294, B:132:0x029a, B:133:0x029e, B:135:0x02a4, B:166:0x0351, B:167:0x0361, B:123:0x0271), top: B:239:0x01f1 }] */
    /* JADX WARN: Code duplicated, block: B:171:0x036e A[LOOP:0: B:29:0x00bb->B:171:0x036e, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:185:0x0393  */
    /* JADX WARN: Code duplicated, block: B:186:0x0395 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:191:0x039f A[Catch: all -> 0x039b, TryCatch #28 {all -> 0x039b, blocks: (B:191:0x039f, B:192:0x03a2, B:187:0x0397), top: B:284:0x0397 }] */
    /* JADX WARN: Code duplicated, block: B:197:0x03a9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:199:0x03af  */
    /* JADX WARN: Code duplicated, block: B:218:0x03c9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:220:0x03cf  */
    /* JADX WARN: Code duplicated, block: B:229:0x03e6 A[REMOVE] */
    /* JADX WARN: Code duplicated, block: B:237:0x01b3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:243:0x03ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:249:0x0335 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:255:0x01b8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:259:0x03be A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:267:0x03cb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:273:0x01c6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:282:0x0315 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:284:0x0397 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:288:0x01b1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:298:? A[Catch: all -> 0x039b, SYNTHETIC, TRY_LEAVE, TryCatch #28 {all -> 0x039b, blocks: (B:191:0x039f, B:192:0x03a2, B:187:0x0397), top: B:284:0x0397 }] */
    /* JADX WARN: Code duplicated, block: B:299:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:301:? A[Catch: all -> 0x03c2, SYNTHETIC, TRY_LEAVE, TryCatch #15 {all -> 0x03c2, blocks: (B:210:0x03be, B:211:0x03c1), top: B:259:0x03be }] */
    /* JADX WARN: Code duplicated, block: B:302:? A[RETURN, SYNTHETIC] */
    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.eclipse.jgit.revwalk.RevCommit call() throws org.eclipse.jgit.api.errors.GitAPIException {
        /*
            Method dump skipped, instruction units count: 1010
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jgit.api.StashCreateCommand.call():org.eclipse.jgit.revwalk.RevCommit");
    }
}
