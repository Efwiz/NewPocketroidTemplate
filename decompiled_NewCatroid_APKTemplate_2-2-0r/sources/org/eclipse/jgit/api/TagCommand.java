package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidTagNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.ServiceUnavailableException;
import org.eclipse.jgit.api.errors.UnsupportedSigningFormatException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.GpgConfig;
import org.eclipse.jgit.lib.GpgObjectSigner;
import org.eclipse.jgit.lib.GpgSigner;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.TagBuilder;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;

/* JADX INFO: loaded from: classes13.dex */
public class TagCommand extends GitCommand<Ref> {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result;
    private Boolean annotated;
    private CredentialsProvider credentialsProvider;
    private boolean forceUpdate;
    private GpgConfig gpgConfig;
    private GpgObjectSigner gpgSigner;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private RevObject f639id;
    private String message;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f640name;
    private Boolean signed;
    private String signingKey;
    private PersonIdent tagger;

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

    protected TagCommand(Repository repository) {
        super(repository);
        this.credentialsProvider = CredentialsProvider.getDefault();
    }

    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public Ref call() throws GitAPIException {
        checkCallable();
        processOptions(this.repo.getRepositoryState());
        Throwable th = null;
        try {
            RevWalk revWalk = new RevWalk(this.repo);
            try {
                if (this.f639id == null) {
                    ObjectId objectIdResolve = this.repo.resolve("HEAD^{commit}");
                    if (objectIdResolve == null) {
                        throw new NoHeadException(JGitText.get().tagOnRepoWithoutHEADCurrentlyNotSupported);
                    }
                    this.f639id = revWalk.parseCommit(objectIdResolve);
                }
                if (!isAnnotated()) {
                    Ref refUpdateTagRef = updateTagRef(this.f639id, revWalk, this.f640name, "SimpleTag[" + this.f640name + " : " + this.f639id + "]");
                    revWalk.close();
                    return refUpdateTagRef;
                }
                TagBuilder tagBuilder = new TagBuilder();
                tagBuilder.setTag(this.f640name);
                tagBuilder.setMessage(this.message);
                tagBuilder.setTagger(this.tagger);
                tagBuilder.setObjectId(this.f639id);
                GpgObjectSigner gpgObjectSigner = this.gpgSigner;
                if (gpgObjectSigner != null) {
                    gpgObjectSigner.signObject(tagBuilder, this.signingKey, this.tagger, this.credentialsProvider, this.gpgConfig);
                }
                try {
                    ObjectInserter objectInserterNewObjectInserter = this.repo.newObjectInserter();
                    try {
                        ObjectId objectIdInsert = objectInserterNewObjectInserter.insert(tagBuilder);
                        objectInserterNewObjectInserter.flush();
                        Ref refUpdateTagRef2 = updateTagRef(objectIdInsert, revWalk, tagBuilder.getTag(), tagBuilder.toString());
                        if (objectInserterNewObjectInserter != null) {
                            objectInserterNewObjectInserter.close();
                        }
                        revWalk.close();
                        return refUpdateTagRef2;
                    } catch (Throwable th2) {
                        if (objectInserterNewObjectInserter != null) {
                            objectInserterNewObjectInserter.close();
                        }
                        throw th2;
                    }
                } catch (Throwable th3) {
                    if (0 == 0) {
                        throw th3;
                    }
                    if (null == th3) {
                        throw null;
                    }
                    th.addSuppressed(th3);
                    throw null;
                }
            } catch (Throwable th4) {
                revWalk.close();
                throw th4;
            }
        } catch (Throwable th5) {
            if (0 == 0) {
                throw th5;
            }
            if (null == th5) {
                throw null;
            }
            try {
                th.addSuppressed(th5);
                throw null;
            } catch (IOException e) {
                throw new JGitInternalException(JGitText.get().exceptionCaughtDuringExecutionOfTagCommand, e);
            }
            throw new JGitInternalException(JGitText.get().exceptionCaughtDuringExecutionOfTagCommand, e);
        }
    }

    private Ref updateTagRef(ObjectId objectId, RevWalk revWalk, String str, String str2) throws ConcurrentRefUpdateException, IOException, RefAlreadyExistsException {
        String str3 = Constants.R_TAGS + str;
        RefUpdate refUpdateUpdateRef = this.repo.updateRef(str3);
        refUpdateUpdateRef.setNewObjectId(objectId);
        refUpdateUpdateRef.setForceUpdate(this.forceUpdate);
        refUpdateUpdateRef.setRefLogMessage("tagged " + this.f640name, false);
        RefUpdate.Result resultUpdate = refUpdateUpdateRef.update(revWalk);
        int i = $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result()[resultUpdate.ordinal()];
        if (i == 2) {
            throw new ConcurrentRefUpdateException(JGitText.get().couldNotLockHEAD, refUpdateUpdateRef.getRef(), resultUpdate);
        }
        if (i == 3) {
            if (this.forceUpdate) {
                return this.repo.exactRef(str3);
            }
            throw new RefAlreadyExistsException(MessageFormat.format(JGitText.get().tagAlreadyExists, str2), resultUpdate);
        }
        if (i == 4 || i == 5) {
            return this.repo.exactRef(str3);
        }
        if (i == 7) {
            throw new RefAlreadyExistsException(MessageFormat.format(JGitText.get().tagAlreadyExists, str2), resultUpdate);
        }
        throw new JGitInternalException(MessageFormat.format(JGitText.get().updatingRefFailed, str3, str2, resultUpdate));
    }

    private void processOptions(RepositoryState repositoryState) throws InvalidTagNameException, ServiceUnavailableException, UnsupportedSigningFormatException {
        if (this.f640name == null || !Repository.isValidRefName(Constants.R_TAGS + this.f640name)) {
            String str = JGitText.get().tagNameInvalid;
            String str2 = this.f640name;
            if (str2 == null) {
                str2 = "<null>";
            }
            throw new InvalidTagNameException(MessageFormat.format(str, str2));
        }
        if (!isAnnotated()) {
            String str3 = this.message;
            if ((str3 != null && !str3.isEmpty()) || this.tagger != null) {
                throw new JGitInternalException(JGitText.get().messageAndTaggerNotAllowedInUnannotatedTags);
            }
            return;
        }
        if (this.tagger == null) {
            this.tagger = new PersonIdent(this.repo);
        }
        if (Boolean.FALSE.equals(this.signed) && this.signingKey == null) {
            return;
        }
        if (this.gpgConfig == null) {
            this.gpgConfig = new GpgConfig(this.repo.getConfig());
        }
        boolean zIsSignAnnotated = isSigned() || this.gpgConfig.isSignAllTags();
        if (!Boolean.TRUE.equals(this.annotated) && !zIsSignAnnotated) {
            zIsSignAnnotated = this.gpgConfig.isSignAnnotated();
        }
        if (zIsSignAnnotated) {
            if (this.signingKey == null) {
                this.signingKey = this.gpgConfig.getSigningKey();
            }
            if (this.gpgSigner == null) {
                Object obj = GpgSigner.getDefault();
                if (!(obj instanceof GpgObjectSigner)) {
                    throw new ServiceUnavailableException(JGitText.get().signingServiceUnavailable);
                }
                this.gpgSigner = (GpgObjectSigner) obj;
            }
            String str4 = this.message;
            if (str4 == null || str4.isEmpty() || this.message.endsWith("\n")) {
                return;
            }
            this.message = String.valueOf(this.message) + '\n';
        }
    }

    public TagCommand setName(String str) {
        checkCallable();
        this.f640name = str;
        return this;
    }

    public String getName() {
        return this.f640name;
    }

    public String getMessage() {
        return this.message;
    }

    public TagCommand setMessage(String str) {
        checkCallable();
        this.message = str;
        return this;
    }

    public boolean isSigned() {
        return Boolean.TRUE.equals(this.signed) || this.signingKey != null;
    }

    public TagCommand setSigned(boolean z) {
        checkCallable();
        this.signed = Boolean.valueOf(z);
        return this;
    }

    public TagCommand setGpgSigner(GpgObjectSigner gpgObjectSigner) {
        checkCallable();
        this.gpgSigner = gpgObjectSigner;
        return this;
    }

    public TagCommand setGpgConfig(GpgConfig gpgConfig) {
        checkCallable();
        this.gpgConfig = gpgConfig;
        return this;
    }

    public TagCommand setTagger(PersonIdent personIdent) {
        checkCallable();
        this.tagger = personIdent;
        return this;
    }

    public PersonIdent getTagger() {
        return this.tagger;
    }

    public RevObject getObjectId() {
        return this.f639id;
    }

    public TagCommand setObjectId(RevObject revObject) {
        checkCallable();
        this.f639id = revObject;
        return this;
    }

    public boolean isForceUpdate() {
        return this.forceUpdate;
    }

    public TagCommand setForceUpdate(boolean z) {
        checkCallable();
        this.forceUpdate = z;
        return this;
    }

    public TagCommand setAnnotated(boolean z) {
        checkCallable();
        this.annotated = Boolean.valueOf(z);
        return this;
    }

    public boolean isAnnotated() {
        return Boolean.TRUE.equals(this.annotated) || isSigned() || this.annotated == null;
    }

    public TagCommand setSigningKey(String str) {
        checkCallable();
        this.signingKey = str;
        return this;
    }

    public String getSigningKey() {
        return this.signingKey;
    }

    public TagCommand setCredentialsProvider(CredentialsProvider credentialsProvider) {
        checkCallable();
        this.credentialsProvider = credentialsProvider;
        return this;
    }
}
