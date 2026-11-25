package org.eclipse.jgit.api;

import com.beust.jcommander.Parameters;
import java.io.IOException;
import java.text.MessageFormat;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/* JADX INFO: loaded from: classes13.dex */
public class CreateBranchCommand extends GitCommand<Ref> {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result;
    private boolean force;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f634name;
    private RevCommit startCommit;
    private String startPoint;
    private SetupUpstreamMode upstreamMode;

    public enum SetupUpstreamMode {
        TRACK,
        NOTRACK,
        SET_UPSTREAM;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static SetupUpstreamMode[] valuesCustom() {
            SetupUpstreamMode[] setupUpstreamModeArrValuesCustom = values();
            int length = setupUpstreamModeArrValuesCustom.length;
            SetupUpstreamMode[] setupUpstreamModeArr = new SetupUpstreamMode[length];
            System.arraycopy(setupUpstreamModeArrValuesCustom, 0, setupUpstreamModeArr, 0, length);
            return setupUpstreamModeArr;
        }
    }

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

    protected CreateBranchCommand(Repository repository) {
        super(repository);
        this.force = false;
        this.startPoint = "HEAD";
    }

    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public Ref call() throws GitAPIException {
        String str;
        String str2;
        RefUpdate.Result resultUpdate;
        String shortMessage;
        Ref refFindRef;
        checkCallable();
        processOptions();
        Throwable th = null;
        try {
            RevWalk revWalk = new RevWalk(this.repo);
            try {
                Ref refFindRef2 = this.repo.findRef(this.f634name);
                boolean zStartsWith = true;
                boolean z = refFindRef2 != null && refFindRef2.getName().startsWith(Constants.R_HEADS);
                if (!this.force && z) {
                    throw new RefAlreadyExistsException(MessageFormat.format(JGitText.get().refAlreadyExists1, this.f634name));
                }
                ObjectId startPointObjectId = getStartPointObjectId();
                String name2 = (this.startPoint == null || (refFindRef = this.repo.findRef(this.startPoint)) == null) ? null : refFindRef.getName();
                String str3 = "";
                if (name2 == null) {
                    RevCommit revCommit = this.startCommit;
                    if (revCommit != null) {
                        shortMessage = revCommit.getShortMessage();
                    } else {
                        shortMessage = revWalk.parseCommit(this.repo.resolve(getStartPointOrHead())).getShortMessage();
                    }
                    if (z) {
                        str2 = "branch: Reset start-point to commit " + shortMessage;
                    } else {
                        str2 = "branch: Created from commit " + shortMessage;
                    }
                } else if (name2.startsWith(Constants.R_HEADS) || name2.startsWith(Constants.R_REMOTES)) {
                    if (z) {
                        str = "branch: Reset start-point to branch " + name2;
                    } else {
                        str = "branch: Created from branch " + name2;
                    }
                    String str4 = str;
                    str3 = name2;
                    str2 = str4;
                } else {
                    startPointObjectId = revWalk.peel(revWalk.parseAny(startPointObjectId));
                    if (z) {
                        str2 = "branch: Reset start-point to tag " + name2;
                    } else {
                        str2 = "branch: Created from tag " + name2;
                    }
                }
                RefUpdate refUpdateUpdateRef = this.repo.updateRef(Constants.R_HEADS + this.f634name);
                refUpdateUpdateRef.setNewObjectId(startPointObjectId);
                refUpdateUpdateRef.setRefLogMessage(str2, false);
                if (z && this.force) {
                    resultUpdate = refUpdateUpdateRef.forceUpdate();
                } else {
                    resultUpdate = refUpdateUpdateRef.update();
                }
                setCallable(false);
                int i = $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result()[resultUpdate.ordinal()];
                if (i != 3) {
                    if (i == 4) {
                        z = !z;
                    } else if (i != 5 && i != 6) {
                        z = false;
                    }
                }
                if (!z) {
                    throw new JGitInternalException(MessageFormat.format(JGitText.get().createBranchUnexpectedResult, resultUpdate.name()));
                }
                Ref refFindRef3 = this.repo.findRef(this.f634name);
                if (refFindRef3 == null) {
                    throw new JGitInternalException(JGitText.get().createBranchFailedUnknownReason);
                }
                if (str3.length() != 0) {
                    if (this.upstreamMode != SetupUpstreamMode.SET_UPSTREAM && this.upstreamMode != SetupUpstreamMode.TRACK) {
                        if (this.upstreamMode == SetupUpstreamMode.NOTRACK) {
                            zStartsWith = false;
                        } else {
                            String string = this.repo.getConfig().getString(ConfigConstants.CONFIG_BRANCH_SECTION, null, ConfigConstants.CONFIG_KEY_AUTOSETUPMERGE);
                            if ("false".equals(string)) {
                                zStartsWith = false;
                            } else if (!"always".equals(string)) {
                                zStartsWith = str3.startsWith(Constants.R_REMOTES);
                            }
                        }
                    }
                    if (zStartsWith) {
                        StoredConfig config = this.repo.getConfig();
                        String remoteName = this.repo.getRemoteName(str3);
                        if (remoteName != null) {
                            String strShortenRemoteBranchName = this.repo.shortenRemoteBranchName(str3);
                            config.setString(ConfigConstants.CONFIG_BRANCH_SECTION, this.f634name, "remote", remoteName);
                            config.setString(ConfigConstants.CONFIG_BRANCH_SECTION, this.f634name, "merge", Constants.R_HEADS + strShortenRemoteBranchName);
                        } else {
                            config.setString(ConfigConstants.CONFIG_BRANCH_SECTION, this.f634name, "remote", ".");
                            config.setString(ConfigConstants.CONFIG_BRANCH_SECTION, this.f634name, "merge", str3);
                        }
                        config.save();
                    }
                    revWalk.close();
                    return refFindRef3;
                }
                revWalk.close();
                return refFindRef3;
            } catch (Throwable th2) {
                revWalk.close();
                throw th2;
            }
        } catch (Throwable th3) {
            if (0 == 0) {
                throw th3;
            }
            if (null == th3) {
                throw null;
            }
            try {
                th.addSuppressed(th3);
                throw null;
            } catch (IOException e) {
                throw new JGitInternalException(e.getMessage(), e);
            }
            throw new JGitInternalException(e.getMessage(), e);
        }
    }

    private ObjectId getStartPointObjectId() throws RefNotFoundException, IOException {
        RevCommit revCommit = this.startCommit;
        if (revCommit != null) {
            return revCommit.getId();
        }
        String startPointOrHead = getStartPointOrHead();
        ObjectId objectIdResolve = this.repo.resolve(startPointOrHead);
        if (objectIdResolve != null) {
            return objectIdResolve;
        }
        throw new RefNotFoundException(MessageFormat.format(JGitText.get().refNotResolved, startPointOrHead));
    }

    private String getStartPointOrHead() {
        String str = this.startPoint;
        return str != null ? str : "HEAD";
    }

    private void processOptions() throws InvalidRefNameException {
        if (this.f634name != null && Repository.isValidRefName(Constants.R_HEADS + this.f634name) && isValidBranchName(this.f634name)) {
            return;
        }
        String str = JGitText.get().branchNameInvalid;
        String str2 = this.f634name;
        if (str2 == null) {
            str2 = "<null>";
        }
        throw new InvalidRefNameException(MessageFormat.format(str, str2));
    }

    public static boolean isValidBranchName(String str) {
        if ("HEAD".equals(str)) {
            return false;
        }
        return !str.startsWith(Parameters.DEFAULT_OPTION_PREFIXES);
    }

    public CreateBranchCommand setName(String str) {
        checkCallable();
        this.f634name = str;
        return this;
    }

    public CreateBranchCommand setForce(boolean z) {
        checkCallable();
        this.force = z;
        return this;
    }

    public CreateBranchCommand setStartPoint(String str) {
        checkCallable();
        this.startPoint = str;
        this.startCommit = null;
        return this;
    }

    public CreateBranchCommand setStartPoint(RevCommit revCommit) {
        checkCallable();
        this.startCommit = revCommit;
        this.startPoint = null;
        return this;
    }

    public CreateBranchCommand setUpstreamMode(SetupUpstreamMode setupUpstreamMode) {
        checkCallable();
        this.upstreamMode = setupUpstreamMode;
        return this;
    }
}
