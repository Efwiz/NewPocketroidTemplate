package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.jgit.api.errors.CannotDeleteCurrentBranchException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NotMergedException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/* JADX INFO: loaded from: classes13.dex */
public class DeleteBranchCommand extends GitCommand<List<String>> {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result;
    private final Set<String> branchNames;
    private boolean force;

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[RefUpdate.Result.valuesCustom().length];
        try {
            iArr2[RefUpdate.Result.FAST_FORWARD.ordinal()] = 6;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[RefUpdate.Result.FORCED.ordinal()] = 5;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[RefUpdate.Result.IO_FAILURE.ordinal()] = 9;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[RefUpdate.Result.LOCK_FAILURE.ordinal()] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[RefUpdate.Result.NEW.ordinal()] = 4;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[RefUpdate.Result.NOT_ATTEMPTED.ordinal()] = 1;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            iArr2[RefUpdate.Result.NO_CHANGE.ordinal()] = 3;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            iArr2[RefUpdate.Result.REJECTED.ordinal()] = 7;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            iArr2[RefUpdate.Result.REJECTED_CURRENT_BRANCH.ordinal()] = 8;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            iArr2[RefUpdate.Result.REJECTED_MISSING_OBJECT.ordinal()] = 11;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            iArr2[RefUpdate.Result.REJECTED_OTHER_REASON.ordinal()] = 12;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            iArr2[RefUpdate.Result.RENAMED.ordinal()] = 10;
        } catch (NoSuchFieldError unused12) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result = iArr2;
        return iArr2;
    }

    protected DeleteBranchCommand(Repository repository) {
        super(repository);
        this.branchNames = new HashSet();
    }

    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public List<String> call() throws GitAPIException {
        Ref refFindRef;
        checkCallable();
        ArrayList arrayList = new ArrayList();
        if (this.branchNames.isEmpty()) {
            return arrayList;
        }
        try {
            String fullBranch = this.repo.getFullBranch();
            if (!this.force) {
                Throwable th = null;
                try {
                    RevWalk revWalk = new RevWalk(this.repo);
                    try {
                        RevCommit commit = revWalk.parseCommit(this.repo.resolve("HEAD"));
                        for (String str : this.branchNames) {
                            if (str != null && this.repo.findRef(str) != null && !revWalk.isMergedInto(revWalk.parseCommit(this.repo.resolve(str)), commit)) {
                                throw new NotMergedException();
                            }
                        }
                        revWalk.close();
                    } catch (Throwable th2) {
                        revWalk.close();
                        throw th2;
                    }
                } catch (Throwable th3) {
                    if (0 == 0) {
                        throw th3;
                    }
                    if (null != th3) {
                        th.addSuppressed(th3);
                    }
                    throw null;
                }
            }
            setCallable(false);
            for (String str2 : this.branchNames) {
                if (str2 != null && (refFindRef = this.repo.findRef(str2)) != null) {
                    String name2 = refFindRef.getName();
                    if (name2.equals(fullBranch)) {
                        throw new CannotDeleteCurrentBranchException(MessageFormat.format(JGitText.get().cannotDeleteCheckedOutBranch, str2));
                    }
                    RefUpdate refUpdateUpdateRef = this.repo.updateRef(name2);
                    refUpdateUpdateRef.setRefLogMessage("branch deleted", false);
                    refUpdateUpdateRef.setForceUpdate(true);
                    RefUpdate.Result resultDelete = refUpdateUpdateRef.delete();
                    int i = $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result()[resultDelete.ordinal()];
                    if (i != 2 && i != 7 && i != 9) {
                        arrayList.add(name2);
                        if (name2.startsWith(Constants.R_HEADS)) {
                            String strSubstring = name2.substring(Constants.R_HEADS.length());
                            StoredConfig config = this.repo.getConfig();
                            config.unsetSection(ConfigConstants.CONFIG_BRANCH_SECTION, strSubstring);
                            config.save();
                        }
                    } else {
                        throw new JGitInternalException(MessageFormat.format(JGitText.get().deleteBranchUnexpectedResult, resultDelete.name()));
                    }
                }
            }
            return arrayList;
        } catch (IOException e) {
            throw new JGitInternalException(e.getMessage(), e);
        }
    }

    public DeleteBranchCommand setBranchNames(String... strArr) {
        checkCallable();
        this.branchNames.clear();
        this.branchNames.addAll(Arrays.asList(strArr));
        return this;
    }

    public DeleteBranchCommand setForce(boolean z) {
        checkCallable();
        this.force = z;
        return this;
    }
}
