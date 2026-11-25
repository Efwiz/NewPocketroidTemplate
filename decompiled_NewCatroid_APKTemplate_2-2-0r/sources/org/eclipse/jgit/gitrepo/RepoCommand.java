package org.eclipse.jgit.gitrepo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.TreeMap;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.SubmoduleAddCommand;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.gitrepo.internal.RepoText;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FileUtils;

/* JADX INFO: loaded from: classes13.dex */
public class RepoCommand extends GitCommand<RevCommit> {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result = null;
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int LOCK_FAILURE_MAX_RETRIES = 5;
    private static final int LOCK_FAILURE_MAX_RETRY_DELAY_MILLIS = 5000;
    private static final int LOCK_FAILURE_MIN_RETRY_DELAY_MILLIS = 50;
    private static final String SLASH = "/";
    private PersonIdent author;
    private String baseUri;
    private String branch;
    private RemoteReader callback;
    private String groupsParam;
    private boolean ignoreRemoteFailures;
    private ManifestParser.IncludedFileReader includedReader;
    private InputStream inputStream;
    private String manifestPath;
    private ProgressMonitor monitor;
    private boolean recordRemoteBranch;
    private boolean recordShallowSubmodules;
    private boolean recordSubmoduleLabels;
    private String targetBranch;
    private URI targetUri;

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

    public interface RemoteReader {
        RemoteFile readFileWithMode(String str, String str2, String str3) throws GitAPIException, IOException;

        @Nullable
        ObjectId sha1(String str, String str2) throws GitAPIException;

        @Deprecated
        default byte[] readFile(String str, String str2, String str3) throws GitAPIException, IOException {
            return readFileWithMode(str, str2, str3).getContents();
        }
    }

    public static final class RemoteFile {
        private final byte[] contents;
        private final FileMode fileMode;

        public RemoteFile(byte[] bArr, FileMode fileMode) {
            this.contents = (byte[]) Objects.requireNonNull(bArr);
            this.fileMode = (FileMode) Objects.requireNonNull(fileMode);
        }

        public byte[] getContents() {
            return this.contents;
        }

        public FileMode getFileMode() {
            return this.fileMode;
        }
    }

    public static class DefaultRemoteReader implements RemoteReader {
        @Override // org.eclipse.jgit.gitrepo.RepoCommand.RemoteReader
        public ObjectId sha1(String str, String str2) throws GitAPIException {
            Ref refFindRef = RefDatabase.findRef(Git.lsRemoteRepository().setRemote(str).callAsMap(), str2);
            if (refFindRef != null) {
                return refFindRef.getObjectId();
            }
            return null;
        }

        @Override // org.eclipse.jgit.gitrepo.RepoCommand.RemoteReader
        public RemoteFile readFileWithMode(String str, String str2, String str3) throws GitAPIException, IOException {
            Throwable th = null;
            File fileCreateTempDir = FileUtils.createTempDir("jgit_", ".git", null);
            try {
                Git gitCall = Git.cloneRepository().setBare(true).setDirectory(fileCreateTempDir).setURI(str).call();
                try {
                    Repository repository = gitCall.getRepository();
                    ObjectId objectIdSha1 = sha1(str, str2);
                    if (objectIdSha1 == null) {
                        throw new InvalidRefNameException(MessageFormat.format(JGitText.get().refNotResolved, str2));
                    }
                    TreeWalk treeWalkForPath = TreeWalk.forPath(repository, str3, repository.parseCommit(objectIdSha1).getTree());
                    RemoteFile remoteFile = new RemoteFile(treeWalkForPath.getObjectReader().open(treeWalkForPath.getObjectId(0)).getCachedBytes(Integer.MAX_VALUE), treeWalkForPath.getFileMode(0));
                    if (gitCall != null) {
                        gitCall.close();
                    }
                    FileUtils.delete(fileCreateTempDir, 1);
                    return remoteFile;
                } catch (Throwable th2) {
                    if (gitCall == null) {
                        throw th2;
                    }
                    gitCall.close();
                    throw th2;
                }
            } catch (Throwable th3) {
                if (0 == 0) {
                    throw th3;
                }
                if (null != th3) {
                    try {
                        th.addSuppressed(th3);
                    } catch (Throwable th4) {
                        FileUtils.delete(fileCreateTempDir, 1);
                        throw th4;
                    }
                }
                throw null;
            }
        }
    }

    private static class ManifestErrorException extends GitAPIException {
        ManifestErrorException(Throwable th) {
            super(RepoText.get().invalidManifest, th);
        }
    }

    private static class RemoteUnavailableException extends GitAPIException {
        RemoteUnavailableException(String str) {
            super(MessageFormat.format(RepoText.get().errorRemoteUnavailable, str));
        }
    }

    public RepoCommand(Repository repository) {
        super(repository);
        this.targetBranch = "HEAD";
        this.recordRemoteBranch = true;
        this.recordSubmoduleLabels = true;
        this.recordShallowSubmodules = true;
        this.ignoreRemoteFailures = false;
    }

    public RepoCommand setPath(String str) {
        this.manifestPath = str;
        return this;
    }

    public RepoCommand setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public RepoCommand setURI(String str) {
        this.baseUri = str;
        return this;
    }

    public RepoCommand setTargetURI(String str) {
        this.targetUri = URI.create(String.valueOf(str) + "/");
        return this;
    }

    public RepoCommand setGroups(String str) {
        this.groupsParam = str;
        return this;
    }

    public RepoCommand setBranch(String str) {
        this.branch = str;
        return this;
    }

    public RepoCommand setTargetBranch(String str) {
        this.targetBranch = Constants.R_HEADS + str;
        return this;
    }

    public RepoCommand setRecordRemoteBranch(boolean z) {
        this.recordRemoteBranch = z;
        return this;
    }

    public RepoCommand setRecordSubmoduleLabels(boolean z) {
        this.recordSubmoduleLabels = z;
        return this;
    }

    public RepoCommand setRecommendShallow(boolean z) {
        this.recordShallowSubmodules = z;
        return this;
    }

    public RepoCommand setProgressMonitor(ProgressMonitor progressMonitor) {
        this.monitor = progressMonitor;
        return this;
    }

    public RepoCommand setIgnoreRemoteFailures(boolean z) {
        this.ignoreRemoteFailures = z;
        return this;
    }

    public RepoCommand setAuthor(PersonIdent personIdent) {
        this.author = personIdent;
        return this;
    }

    public RepoCommand setRemoteReader(RemoteReader remoteReader) {
        this.callback = remoteReader;
        return this;
    }

    public RepoCommand setIncludedFileReader(ManifestParser.IncludedFileReader includedFileReader) {
        this.includedReader = includedFileReader;
        return this;
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x035a */
    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.eclipse.jgit.revwalk.RevCommit call() throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 873
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jgit.gitrepo.RepoCommand.call():org.eclipse.jgit.revwalk.RevCommit");
    }

    private RevCommit commitTreeOnCurrentTip(ObjectInserter objectInserter, RevWalk revWalk, ObjectId objectId) throws ConcurrentRefUpdateException, IOException {
        ObjectId objectIdResolve = this.repo.resolve(String.valueOf(this.targetBranch) + "^{commit}");
        if (objectIdResolve != null && revWalk.parseCommit(objectIdResolve).getTree().getId().equals((AnyObjectId) objectId)) {
            return revWalk.parseCommit(objectIdResolve);
        }
        CommitBuilder commitBuilder = new CommitBuilder();
        commitBuilder.setTreeId(objectId);
        if (objectIdResolve != null) {
            commitBuilder.setParentIds(objectIdResolve);
        }
        commitBuilder.setAuthor(this.author);
        commitBuilder.setCommitter(this.author);
        commitBuilder.setMessage(RepoText.get().repoCommitMessage);
        ObjectId objectIdInsert = objectInserter.insert(commitBuilder);
        objectInserter.flush();
        RefUpdate refUpdateUpdateRef = this.repo.updateRef(this.targetBranch);
        refUpdateUpdateRef.setNewObjectId(objectIdInsert);
        if (objectIdResolve == null) {
            objectIdResolve = ObjectId.zeroId();
        }
        refUpdateUpdateRef.setExpectedOldObjectId(objectIdResolve);
        RefUpdate.Result resultUpdate = refUpdateUpdateRef.update(revWalk);
        int i = $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result()[resultUpdate.ordinal()];
        if (i != 2) {
            if (i == 4 || i == 5 || i == 6) {
                return revWalk.parseCommit(objectIdInsert);
            }
            if (i != 7) {
                throw new JGitInternalException(MessageFormat.format(JGitText.get().updatingRefFailed, this.targetBranch, objectIdInsert.name(), resultUpdate));
            }
        }
        throw new ConcurrentRefUpdateException(MessageFormat.format(JGitText.get().cannotLock, this.targetBranch), refUpdateUpdateRef.getRef(), resultUpdate);
    }

    private void addSubmodule(String str, String str2, String str3, String str4, List<RepoProject.CopyFile> list, List<RepoProject.LinkFile> list2, Git git) throws Throwable {
        if (!list2.isEmpty()) {
            throw new UnsupportedOperationException(JGitText.get().nonBareLinkFilesNotSupported);
        }
        SubmoduleAddCommand uri = git.submoduleAdd().setName(str).setPath(str3).setURI(str2);
        ProgressMonitor progressMonitor = this.monitor;
        if (progressMonitor != null) {
            uri.setProgressMonitor(progressMonitor);
        }
        Repository repositoryCall = uri.call();
        if (str4 != null) {
            Throwable th = null;
            try {
                Git git2 = new Git(repositoryCall);
                try {
                    git2.checkout().setName(findRef(str4, repositoryCall)).call();
                    git2.close();
                    repositoryCall.close();
                    git.add().addFilepattern(str3).call();
                } catch (Throwable th2) {
                    git2.close();
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
        for (RepoProject.CopyFile copyFile : list) {
            copyFile.copy();
            git.add().addFilepattern(copyFile.dest).call();
        }
    }

    private List<RepoProject> renameProjects(List<RepoProject> list) {
        TreeMap treeMap = new TreeMap();
        for (RepoProject repoProject : list) {
            List arrayList = (List) treeMap.get(repoProject.getName());
            if (arrayList == null) {
                arrayList = new ArrayList();
                treeMap.put(repoProject.getName(), arrayList);
            }
            arrayList.add(repoProject);
        }
        ArrayList arrayList2 = new ArrayList();
        for (List<RepoProject> list2 : treeMap.values()) {
            boolean z = list2.size() != 1;
            for (RepoProject repoProject2 : list2) {
                String name2 = repoProject2.getName();
                if (z) {
                    name2 = String.valueOf(name2) + "/" + repoProject2.getPath();
                }
                RepoProject repoProject3 = new RepoProject(name2, repoProject2.getPath(), repoProject2.getRevision(), null, repoProject2.getGroups(), repoProject2.getRecommendShallow());
                repoProject3.setUrl(repoProject2.getUrl());
                repoProject3.addCopyFiles(repoProject2.getCopyFiles());
                repoProject3.addLinkFiles(repoProject2.getLinkFiles());
                arrayList2.add(repoProject3);
            }
        }
        return arrayList2;
    }

    static URI relativize(URI uri, URI uri2) {
        String strSubstring;
        if (!Objects.equals(uri.getHost(), uri2.getHost())) {
            return uri2;
        }
        String path = uri.normalize().getPath();
        String path2 = uri2.normalize().getPath();
        if (path.startsWith("/") != path2.startsWith("/")) {
            return uri2;
        }
        while (path.startsWith("/")) {
            path = path.substring(1);
        }
        while (path2.startsWith("/")) {
            path2 = path2.substring(1);
        }
        if (path.indexOf(47) == -1 || path2.indexOf(47) == -1) {
            path = String.valueOf("prefix/") + path;
            path2 = String.valueOf("prefix/") + path2;
        }
        int i = 0;
        if (!path.endsWith("/")) {
            path = path.substring(0, path.lastIndexOf(47));
        }
        if (path2.endsWith("/")) {
            strSubstring = "";
        } else {
            strSubstring = path2.substring(path2.lastIndexOf(47) + 1, path2.length());
            path2 = path2.substring(0, path2.lastIndexOf(47));
        }
        String str = strSubstring;
        String[] strArrSplit = path.split("/");
        String[] strArrSplit2 = path2.split("/");
        while (i < strArrSplit.length && i < strArrSplit2.length && strArrSplit[i].equals(strArrSplit2[i])) {
            i++;
        }
        StringJoiner stringJoiner = new StringJoiner("/");
        for (int i2 = i; i2 < strArrSplit.length; i2++) {
            stringJoiner.add("..");
        }
        while (i < strArrSplit2.length) {
            stringJoiner.add(strArrSplit2[i]);
            i++;
        }
        stringJoiner.add(str);
        return URI.create(stringJoiner.toString());
    }

    private static String findRef(String str, Repository repository) throws IOException {
        Ref refExactRef;
        return (ObjectId.isId(str) || (refExactRef = repository.exactRef(new StringBuilder("refs/remotes/origin/").append(str).toString())) == null) ? str : refExactRef.getName();
    }
}
