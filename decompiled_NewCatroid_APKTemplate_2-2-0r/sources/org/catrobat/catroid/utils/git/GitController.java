package org.catrobat.catroid.utils.git;

import android.util.Log;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.internal.storage.file.WindowCache;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.file.WindowCacheConfig;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/GitController.class
  classes14.dex
 */
/* JADX INFO: compiled from: GitController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0002J$\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000e2\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0003J,\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u000e2\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006J$\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\f0\u000e2\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0006J\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\f0\u000e2\u0006\u0010\u0017\u001a\u00020\u0006J\"\u0010\u001b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J$\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\f0\u000e2\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0003J\"\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020!H\u0002J \u0010&\u001a\u00020\u00062\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020!2\u0006\u0010*\u001a\u00020\u0006H\u0002J$\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u001eJ\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\f0\u000eJ\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u000e2\u0006\u0010\u0010\u001a\u00020\u0006J\u0018\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0003H\u0002R\u0014\u0010\u0005\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/catrobat/catroid/utils/git/GitController;", "", ProjectUploadActivityKt.PROJECT_DIR, "Ljava/io/File;", "(Ljava/io/File;)V", "TAG", "", "getTAG", "()Ljava/lang/String;", "projectMerger", "Lorg/catrobat/catroid/utils/git/ProjectMerger;", "clearLocks", "", "cloneRepository", "Lorg/catrobat/catroid/utils/git/GitResult;", "remoteUrl", "authToken", "targetDir", "commitAndPush", "commitMessage", "authorName", "authorEmail", "commitAndPushFeature", "branchName", "token", CrashHianalyticsData.MESSAGE, "createAndCheckoutBranch", "createGitHubRepository", "repoName", "isPrivate", "", "downloadAndUnpack", "findMergeBase", "Lorg/eclipse/jgit/lib/ObjectId;", "git", "Lorg/eclipse/jgit/api/Git;", "commit1", "commit2", "getFileContent", "reader", "Lorg/eclipse/jgit/lib/ObjectReader;", "commitId", "filePath", "initializeAndPushNewRepository", "initializeRepository", "pullAndMerge", "Lorg/catrobat/catroid/utils/git/MergeResult;", "unzip", "zipFile", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GitController {
    private final String TAG;
    private final File projectDir;
    private final ProjectMerger projectMerger;

    public GitController(File projectDir) {
        Intrinsics.checkNotNullParameter(projectDir, "projectDir");
        this.projectDir = projectDir;
        this.projectMerger = new ProjectMerger();
        this.TAG = "GitController";
    }

    public final String getTAG() {
        return this.TAG;
    }

    public final GitResult<Unit> initializeRepository() {
        GitResult.Error errorM2898constructorimpl;
        try {
            Result.Companion companion = Result.INSTANCE;
            GitController gitController = this;
            Git.init().setDirectory(this.projectDir).call().close();
            errorM2898constructorimpl = Result.m2898constructorimpl(new GitResult.Success(Unit.INSTANCE));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            errorM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(errorM2898constructorimpl);
        if (thM2901exceptionOrNullimpl != null) {
            errorM2898constructorimpl = new GitResult.Error("Failed to initialize repository", thM2901exceptionOrNullimpl);
        }
        return (GitResult) errorM2898constructorimpl;
    }

    public final GitResult<Unit> cloneRepository(String remoteUrl, String authToken, File targetDir) {
        GitResult.Error errorM2898constructorimpl;
        Intrinsics.checkNotNullParameter(remoteUrl, "remoteUrl");
        Intrinsics.checkNotNullParameter(authToken, "authToken");
        Intrinsics.checkNotNullParameter(targetDir, "targetDir");
        try {
            Result.Companion companion = Result.INSTANCE;
            GitController gitController = this;
            Git.cloneRepository().setURI(remoteUrl).setDirectory(targetDir).setCredentialsProvider(new UsernamePasswordCredentialsProvider(authToken, "")).call().close();
            errorM2898constructorimpl = Result.m2898constructorimpl(new GitResult.Success(Unit.INSTANCE));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            errorM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(errorM2898constructorimpl);
        if (thM2901exceptionOrNullimpl != null) {
            thM2901exceptionOrNullimpl.printStackTrace();
            errorM2898constructorimpl = new GitResult.Error("Failed to clone repository: " + thM2901exceptionOrNullimpl.getMessage(), thM2901exceptionOrNullimpl);
        }
        return (GitResult) errorM2898constructorimpl;
    }

    public final GitResult<MergeResult> pullAndMerge(String authToken) {
        Intrinsics.checkNotNullParameter(authToken, "authToken");
        try {
            Git gitOpen = Git.open(this.projectDir);
            try {
                Git git = gitOpen;
                RepositoryState repositoryState = git.getRepository().getRepositoryState();
                if (repositoryState == RepositoryState.MERGING || repositoryState == RepositoryState.MERGING_RESOLVED) {
                    Log.w(this.TAG, "Repository was in a stale merging state (" + repositoryState + "). Resetting merge state before proceeding.");
                    git.reset().setMode(ResetCommand.ResetType.MERGE).call();
                }
                if (!git.status().call().isClean()) {
                    Log.d(this.TAG, "Uncommitted local changes detected. Creating a temporary local commit.");
                    git.add().addFilepattern(".").call();
                    git.commit().setMessage("Local work-in-progress").call();
                }
                Ref refFindRef = git.getRepository().findRef("HEAD");
                if (refFindRef == null) {
                    GitResult.Error error = new GitResult.Error("Local HEAD not found", null, 2, null);
                    AutoCloseableKt.closeFinally(gitOpen, null);
                    return error;
                }
                Intrinsics.checkNotNull(refFindRef);
                Log.d(this.TAG, "Fetching from remote...");
                git.fetch().setCredentialsProvider(new UsernamePasswordCredentialsProvider(authToken, "")).call();
                Ref refFindRef2 = git.getRepository().findRef("refs/remotes/origin/main");
                if (refFindRef2 == null) {
                    refFindRef2 = git.getRepository().findRef("refs/remotes/origin/master");
                }
                if (refFindRef2 == null) {
                    GitResult.Error error2 = new GitResult.Error("Remote branch not found", null, 2, null);
                    AutoCloseableKt.closeFinally(gitOpen, null);
                    return error2;
                }
                if (Intrinsics.areEqual(refFindRef.getObjectId(), refFindRef2.getObjectId())) {
                    Log.d(this.TAG, "Already up-to-date.");
                    XStreamUtilGit xStreamUtilGit = XStreamUtilGit.INSTANCE;
                    File file = new File(this.projectDir, Constants.CODE_XML_FILE_NAME);
                    Charset UTF_8 = StandardCharsets.UTF_8;
                    Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
                    GitResult.Success success = new GitResult.Success(new MergeResult(xStreamUtilGit.fromXML(FilesKt.readText(file, UTF_8)), CollectionsKt.emptyList()));
                    AutoCloseableKt.closeFinally(gitOpen, null);
                    return success;
                }
                org.eclipse.jgit.api.MergeResult mergeResultCall = git.merge().include(refFindRef2).call();
                if (mergeResultCall.getMergeStatus().isSuccessful()) {
                    Log.d(this.TAG, "Standard merge successful with status: " + mergeResultCall.getMergeStatus());
                    XStreamUtilGit xStreamUtilGit2 = XStreamUtilGit.INSTANCE;
                    File file2 = new File(this.projectDir, Constants.CODE_XML_FILE_NAME);
                    Charset UTF_9 = StandardCharsets.UTF_8;
                    Intrinsics.checkNotNullExpressionValue(UTF_9, "UTF_8");
                    GitResult.Success success2 = new GitResult.Success(new MergeResult(xStreamUtilGit2.fromXML(FilesKt.readText(file2, UTF_9)), CollectionsKt.emptyList()));
                    AutoCloseableKt.closeFinally(gitOpen, null);
                    return success2;
                }
                Log.w(this.TAG, "Git merge resulted in conflicts. Starting semantic merge as conflict resolution.");
                git.reset().setMode(ResetCommand.ResetType.HARD).setRef(refFindRef.getName()).call();
                Intrinsics.checkNotNull(git);
                ObjectId objectId = refFindRef.getObjectId();
                Intrinsics.checkNotNullExpressionValue(objectId, "getObjectId(...)");
                ObjectId objectId2 = refFindRef2.getObjectId();
                Intrinsics.checkNotNullExpressionValue(objectId2, "getObjectId(...)");
                ObjectId objectIdFindMergeBase = findMergeBase(git, objectId, objectId2);
                if (objectIdFindMergeBase == null) {
                    GitResult.Error error3 = new GitResult.Error("No common ancestor found", null, 2, null);
                    AutoCloseableKt.closeFinally(gitOpen, null);
                    return error3;
                }
                ObjectReader objectReaderNewObjectReader = git.getRepository().newObjectReader();
                Intrinsics.checkNotNull(objectReaderNewObjectReader);
                String fileContent = getFileContent(objectReaderNewObjectReader, objectIdFindMergeBase, Constants.CODE_XML_FILE_NAME);
                ObjectId objectId3 = refFindRef.getObjectId();
                Intrinsics.checkNotNullExpressionValue(objectId3, "getObjectId(...)");
                String fileContent2 = getFileContent(objectReaderNewObjectReader, objectId3, Constants.CODE_XML_FILE_NAME);
                ObjectId objectId4 = refFindRef2.getObjectId();
                Intrinsics.checkNotNullExpressionValue(objectId4, "getObjectId(...)");
                MergeResultData mergeResultDataMerge = this.projectMerger.merge(XStreamUtilGit.INSTANCE.fromXML(fileContent), XStreamUtilGit.INSTANCE.fromXML(fileContent2), XStreamUtilGit.INSTANCE.fromXML(getFileContent(objectReaderNewObjectReader, objectId4, Constants.CODE_XML_FILE_NAME)));
                File file3 = new File(this.projectDir, Constants.CODE_XML_FILE_NAME);
                String xml = XStreamUtilGit.INSTANCE.toXML(mergeResultDataMerge.getMergedProject());
                Charset UTF_10 = StandardCharsets.UTF_8;
                Intrinsics.checkNotNullExpressionValue(UTF_10, "UTF_8");
                FilesKt.writeText(file3, xml, UTF_10);
                git.add().addFilepattern(Constants.CODE_XML_FILE_NAME).call();
                git.commit().setMessage("Semantic merge of remote changes").call();
                Log.d(this.TAG, "Semantic merge complete. Merge commit created.");
                GitResult.Success success3 = new GitResult.Success(new MergeResult(mergeResultDataMerge.getMergedProject(), mergeResultDataMerge.getConflicts()));
                AutoCloseableKt.closeFinally(gitOpen, null);
                return success3;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    AutoCloseableKt.closeFinally(gitOpen, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            Exception exc = e;
            Log.e(this.TAG, "Pull and merge failed", exc);
            try {
                Git gitOpen2 = Git.open(this.projectDir);
                try {
                    gitOpen2.reset().setMode(ResetCommand.ResetType.HARD).call();
                    AutoCloseableKt.closeFinally(gitOpen2, null);
                } catch (Throwable th3) {
                    try {
                        throw th3;
                    } catch (Throwable th4) {
                        AutoCloseableKt.closeFinally(gitOpen2, th3);
                        throw th4;
                    }
                }
            } catch (Exception e2) {
                Log.e(this.TAG, "Hard reset also failed", e2);
                return new GitResult.Error("Merge failed: " + e.getMessage(), exc);
            }
            return new GitResult.Error("Merge failed: " + e.getMessage(), exc);
        }
    }

    public final GitResult<Unit> commitAndPush(String commitMessage, String authorName, String authorEmail, String authToken) {
        org.eclipse.jgit.api.MergeResult mergeResultCall;
        boolean z;
        Intrinsics.checkNotNullParameter(commitMessage, "commitMessage");
        Intrinsics.checkNotNullParameter(authorName, "authorName");
        Intrinsics.checkNotNullParameter(authorEmail, "authorEmail");
        Intrinsics.checkNotNullParameter(authToken, "authToken");
        try {
            Git gitOpen = Git.open(this.projectDir);
            try {
                Git git = gitOpen;
                if (!git.status().call().isClean()) {
                    Log.d(this.TAG, "Uncommitted changes found. Committing local work.");
                    git.add().addFilepattern(".").call();
                    git.commit().setMessage(commitMessage).setAuthor(authorName, authorEmail).call();
                } else {
                    Integer.valueOf(Log.d(this.TAG, "Working tree is clean. Nothing new to commit locally."));
                }
                AutoCloseableKt.closeFinally(gitOpen, null);
                Git gitOpen2 = Git.open(this.projectDir);
                try {
                    Git git2 = gitOpen2;
                    Log.d(this.TAG, "Fetching from remote repository...");
                    git2.fetch().setCredentialsProvider(new UsernamePasswordCredentialsProvider(authToken, "")).call();
                    ObjectId objectIdResolve = git2.getRepository().resolve("HEAD");
                    ObjectId objectIdResolve2 = git2.getRepository().resolve("refs/remotes/origin/main");
                    if (objectIdResolve2 == null) {
                        objectIdResolve2 = git2.getRepository().resolve("refs/remotes/origin/master");
                    }
                    if (objectIdResolve2 == null) {
                        GitResult.Error error = new GitResult.Error("Remote branch not found after fetch.", null, 2, null);
                        AutoCloseableKt.closeFinally(gitOpen2, null);
                        return error;
                    }
                    if (!Intrinsics.areEqual(objectIdResolve, objectIdResolve2)) {
                        Log.d(this.TAG, "Local and remote history has diverged. Starting merge process...");
                        mergeResultCall = git2.merge().include(objectIdResolve2).setCommit(false).call();
                        Unit unit = Unit.INSTANCE;
                        z = true;
                    } else {
                        Integer.valueOf(Log.d(this.TAG, "Local history is up-to-date. No merge needed."));
                        mergeResultCall = null;
                        z = false;
                    }
                    AutoCloseableKt.closeFinally(gitOpen2, null);
                    if (z) {
                        if (mergeResultCall == null) {
                            throw new IllegalStateException("Merge was needed but mergeResult is null.");
                        }
                        if (mergeResultCall.getMergeStatus().isSuccessful()) {
                            Git gitOpen3 = Git.open(this.projectDir);
                            try {
                                Log.d(this.TAG, "Standard merge successful. Committing.");
                                gitOpen3.commit().setMessage("Merge remote changes").call();
                                AutoCloseableKt.closeFinally(gitOpen3, null);
                            } catch (Throwable th) {
                                try {
                                    throw th;
                                } catch (Throwable th2) {
                                    AutoCloseableKt.closeFinally(gitOpen3, th);
                                    throw th2;
                                }
                            }
                        } else if (mergeResultCall.getMergeStatus() == org.eclipse.jgit.api.MergeResult.MergeStatus.CONFLICTING) {
                            Log.w(this.TAG, "Standard Git merge resulted in conflicts. Resolving...");
                            Map<String, int[][]> conflicts = mergeResultCall.getConflicts();
                            if (conflicts == null) {
                                throw new IllegalStateException("Conflict status but no conflicts map.");
                            }
                            if (conflicts.containsKey(Constants.CODE_XML_FILE_NAME)) {
                                Log.d(this.TAG, "Resolving 'code.xml' semantically...");
                                Git gitOpen4 = Git.open(this.projectDir);
                                try {
                                    Git git3 = gitOpen4;
                                    Repository repository = git3.getRepository();
                                    ObjectReader objectReaderNewObjectReader = repository.newObjectReader();
                                    ObjectId objectIdResolve3 = repository.resolve("HEAD");
                                    ObjectId objectIdResolve4 = repository.resolve(org.eclipse.jgit.lib.Constants.MERGE_HEAD);
                                    if (objectIdResolve4 == null) {
                                        throw new IllegalStateException("MERGE_HEAD not found");
                                    }
                                    Intrinsics.checkNotNull(objectIdResolve4);
                                    Intrinsics.checkNotNull(git3);
                                    Intrinsics.checkNotNull(objectIdResolve3);
                                    ObjectId objectIdFindMergeBase = findMergeBase(git3, objectIdResolve3, objectIdResolve4);
                                    if (objectIdFindMergeBase == null) {
                                        GitResult.Error error2 = new GitResult.Error("No common ancestor.", null, 2, null);
                                        AutoCloseableKt.closeFinally(gitOpen4, null);
                                        return error2;
                                    }
                                    Intrinsics.checkNotNull(objectReaderNewObjectReader);
                                    MergeResultData mergeResultDataMerge = this.projectMerger.merge(XStreamUtilGit.INSTANCE.fromXML(getFileContent(objectReaderNewObjectReader, objectIdFindMergeBase, Constants.CODE_XML_FILE_NAME)), XStreamUtilGit.INSTANCE.fromXML(getFileContent(objectReaderNewObjectReader, objectIdResolve3, Constants.CODE_XML_FILE_NAME)), XStreamUtilGit.INSTANCE.fromXML(getFileContent(objectReaderNewObjectReader, objectIdResolve4, Constants.CODE_XML_FILE_NAME)));
                                    File file = new File(this.projectDir, Constants.CODE_XML_FILE_NAME);
                                    String xml = XStreamUtilGit.INSTANCE.toXML(mergeResultDataMerge.getMergedProject());
                                    Charset UTF_8 = StandardCharsets.UTF_8;
                                    Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
                                    FilesKt.writeText(file, xml, UTF_8);
                                    Unit unit2 = Unit.INSTANCE;
                                    AutoCloseableKt.closeFinally(gitOpen4, null);
                                } catch (Throwable th3) {
                                    try {
                                        throw th3;
                                    } catch (Throwable th4) {
                                        AutoCloseableKt.closeFinally(gitOpen4, th3);
                                        throw th4;
                                    }
                                }
                            }
                            Git gitOpen5 = Git.open(this.projectDir);
                            try {
                                Git git4 = gitOpen5;
                                Log.d(this.TAG, "Adding resolved files to index...");
                                AddCommand addCommandAdd = git4.add();
                                Iterator<T> it = conflicts.keySet().iterator();
                                while (it.hasNext()) {
                                    addCommandAdd.addFilepattern((String) it.next());
                                }
                                addCommandAdd.call();
                                Log.d(this.TAG, "All conflicts resolved. Creating merge commit...");
                                Repository repository2 = git4.getRepository();
                                ObjectInserter objectInserterNewObjectInserter = repository2.newObjectInserter();
                                try {
                                    DirCache dirCacheLockDirCache = repository2.lockDirCache();
                                    try {
                                        ObjectId objectIdWriteTree = dirCacheLockDirCache.writeTree(objectInserterNewObjectInserter);
                                        dirCacheLockDirCache.unlock();
                                        ObjectId objectIdResolve5 = repository2.resolve("HEAD");
                                        ObjectId objectIdResolve6 = repository2.resolve(org.eclipse.jgit.lib.Constants.MERGE_HEAD);
                                        if (objectIdResolve6 == null) {
                                            throw new IllegalStateException("MERGE_HEAD not found.");
                                        }
                                        Intrinsics.checkNotNull(objectIdResolve6);
                                        CommitBuilder commitBuilder = new CommitBuilder();
                                        commitBuilder.setTreeId(objectIdWriteTree);
                                        commitBuilder.setParentIds(objectIdResolve5, objectIdResolve6);
                                        commitBuilder.setAuthor(new PersonIdent(authorName, authorEmail));
                                        commitBuilder.setCommitter(new PersonIdent(authorName, authorEmail));
                                        commitBuilder.setMessage("Semantic merge of remote changes");
                                        ObjectId objectIdInsert = objectInserterNewObjectInserter.insert(commitBuilder);
                                        Intrinsics.checkNotNullExpressionValue(objectIdInsert, "insert(...)");
                                        objectInserterNewObjectInserter.flush();
                                        RefUpdate refUpdateUpdateRef = repository2.updateRef("HEAD");
                                        refUpdateUpdateRef.setNewObjectId(objectIdInsert);
                                        refUpdateUpdateRef.setExpectedOldObjectId(objectIdResolve5);
                                        RefUpdate.Result resultUpdate = refUpdateUpdateRef.update();
                                        if (resultUpdate != RefUpdate.Result.FAST_FORWARD && resultUpdate != RefUpdate.Result.NEW) {
                                            throw new IOException("Failed to update HEAD. Result: " + resultUpdate);
                                        }
                                        repository2.writeMergeCommitMsg(null);
                                        repository2.writeMergeHeads(null);
                                        objectInserterNewObjectInserter.close();
                                        Log.d(this.TAG, "Merge commit created: " + objectIdInsert.name());
                                        AutoCloseableKt.closeFinally(gitOpen5, null);
                                    } catch (Throwable th5) {
                                        dirCacheLockDirCache.unlock();
                                        throw th5;
                                    }
                                } catch (Throwable th6) {
                                    objectInserterNewObjectInserter.close();
                                    throw th6;
                                }
                            } catch (Throwable th7) {
                                try {
                                    throw th7;
                                } catch (Throwable th8) {
                                    AutoCloseableKt.closeFinally(gitOpen5, th7);
                                    throw th8;
                                }
                            }
                        } else {
                            throw new Exception("Merge failed with unexpected status: " + mergeResultCall.getMergeStatus());
                        }
                    }
                    Git gitOpen6 = Git.open(this.projectDir);
                    try {
                        Log.d(this.TAG, "Attempting to push merged history to remote...");
                        gitOpen6.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(authToken, "")).call();
                        Log.d(this.TAG, "Push successful.");
                        AutoCloseableKt.closeFinally(gitOpen6, null);
                        return new GitResult.Success(Unit.INSTANCE);
                    } catch (Throwable th9) {
                        try {
                            throw th9;
                        } catch (Throwable th10) {
                            AutoCloseableKt.closeFinally(gitOpen6, th9);
                            throw th10;
                        }
                    }
                } catch (Throwable th11) {
                    try {
                        throw th11;
                    } catch (Throwable th12) {
                        AutoCloseableKt.closeFinally(gitOpen2, th11);
                        throw th12;
                    }
                }
            } catch (Throwable th13) {
                try {
                    throw th13;
                } catch (Throwable th14) {
                    AutoCloseableKt.closeFinally(gitOpen, th13);
                    throw th14;
                }
            }
        } catch (Exception e) {
            Exception exc = e;
            Log.e(this.TAG, "The entire commit-and-push process failed", exc);
            try {
                Git gitOpen7 = Git.open(this.projectDir);
                try {
                    Git git5 = gitOpen7;
                    Log.w(this.TAG, "Attempting to hard reset HEAD due to a critical failure.");
                    File file2 = new File(git5.getRepository().getDirectory(), "index.lock");
                    if (file2.exists()) {
                        Log.w(this.TAG, "Deleting stale index.lock file.");
                        file2.delete();
                    }
                    git5.reset().setMode(ResetCommand.ResetType.HARD).call();
                    AutoCloseableKt.closeFinally(gitOpen7, null);
                    return new GitResult.Error("Publication failed: " + e.getMessage(), exc);
                } catch (Throwable th15) {
                    try {
                        throw th15;
                    } catch (Throwable th16) {
                        AutoCloseableKt.closeFinally(gitOpen7, th15);
                        throw th16;
                    }
                }
            } catch (Exception e2) {
                Log.e(this.TAG, "Failed to reset repository after a critical error", e2);
            }
        }
    }

    private final ObjectId findMergeBase(Git git, ObjectId commit1, ObjectId commit2) throws Exception {
        RevWalk revWalk = new RevWalk(git.getRepository());
        try {
            RevWalk revWalk2 = revWalk;
            RevCommit commit = revWalk2.parseCommit(commit1);
            RevCommit commit3 = revWalk2.parseCommit(commit2);
            revWalk2.setRevFilter(RevFilter.MERGE_BASE);
            revWalk2.markStart(commit);
            revWalk2.markStart(commit3);
            RevCommit next = revWalk2.next();
            AutoCloseableKt.closeFinally(revWalk, null);
            return next;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(revWalk, th);
                throw th2;
            }
        }
    }

    private final String getFileContent(ObjectReader reader, ObjectId commitId, String filePath) throws Exception {
        RevWalk revWalk = new RevWalk(reader);
        try {
            TreeWalk treeWalkForPath = TreeWalk.forPath(reader, filePath, revWalk.parseCommit(commitId).getTree());
            if (treeWalkForPath == null) {
                AutoCloseableKt.closeFinally(revWalk, null);
                return "";
            }
            Intrinsics.checkNotNull(treeWalkForPath);
            TreeWalk treeWalk = treeWalkForPath;
            try {
                byte[] bytes = reader.open(treeWalk.getObjectId(0)).getBytes();
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                Charset UTF_8 = StandardCharsets.UTF_8;
                Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
                String str = new String(bytes, UTF_8);
                AutoCloseableKt.closeFinally(treeWalk, null);
                AutoCloseableKt.closeFinally(revWalk, null);
                return str;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    AutoCloseableKt.closeFinally(treeWalk, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                AutoCloseableKt.closeFinally(revWalk, th3);
                throw th4;
            }
        }
    }

    public final GitResult<String> initializeAndPushNewRepository(String authToken, String repoName, boolean isPrivate) {
        GitResult.Error errorM2898constructorimpl;
        GitResult.Success success;
        Intrinsics.checkNotNullParameter(authToken, "authToken");
        Intrinsics.checkNotNullParameter(repoName, "repoName");
        try {
            Result.Companion companion = Result.INSTANCE;
            GitController gitController = this;
            String strCreateGitHubRepository = createGitHubRepository(authToken, repoName, isPrivate);
            if (strCreateGitHubRepository == null) {
                success = new GitResult.Error("Failed to create GitHub repository", null, 2, null);
            } else {
                Git gitCall2 = Git.init().setDirectory(this.projectDir).call();
                try {
                    Git git = gitCall2;
                    git.add().addFilepattern(".").call();
                    git.commit().setMessage("Initial commit").call();
                    git.remoteAdd().setName("origin").setUri(new URIish(strCreateGitHubRepository)).call();
                    git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(authToken, "")).call();
                    AutoCloseableKt.closeFinally(gitCall2, null);
                    success = new GitResult.Success(strCreateGitHubRepository);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        AutoCloseableKt.closeFinally(gitCall2, th);
                        throw th2;
                    }
                }
            }
            errorM2898constructorimpl = Result.m2898constructorimpl(success);
        } catch (Throwable th3) {
            Result.Companion companion2 = Result.INSTANCE;
            errorM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th3));
        }
        Throwable thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(errorM2898constructorimpl);
        if (thM2901exceptionOrNullimpl != null) {
            thM2901exceptionOrNullimpl.printStackTrace();
            errorM2898constructorimpl = new GitResult.Error("Failed to initialize and push repository", thM2901exceptionOrNullimpl);
        }
        return (GitResult) errorM2898constructorimpl;
    }

    private final String createGitHubRepository(String token, String repoName, boolean isPrivate) throws JSONException {
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("name", repoName);
        jSONObject.put("private", isPrivate);
        RequestBody.Companion companion = RequestBody.INSTANCE;
        String string = jSONObject.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        try {
            Response responseExecute = okHttpClient.newCall(new Request.Builder().url("https://api.github.com/user/repos").header("Authorization", "Bearer " + token).post(companion.create(string, MediaType.INSTANCE.get("application/json; charset=utf-8"))).build()).execute();
            try {
                Response response = responseExecute;
                if (!response.isSuccessful()) {
                    PrintStream printStream = System.err;
                    int iCode = response.code();
                    ResponseBody responseBodyBody = response.body();
                    printStream.println("GitHub API Error: " + iCode + " " + (responseBodyBody != null ? responseBodyBody.string() : null));
                    CloseableKt.closeFinally(responseExecute, null);
                    return null;
                }
                ResponseBody responseBodyBody2 = response.body();
                String strString = responseBodyBody2 != null ? responseBodyBody2.string() : null;
                String strOptString = strString != null ? new JSONObject(strString).optString("clone_url", null) : null;
                CloseableKt.closeFinally(responseExecute, null);
                return strOptString;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(responseExecute, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:48:0x0170  */
    public final GitResult<Unit> downloadAndUnpack(String remoteUrl, String authToken, File targetDir) {
        GitResult.Error errorM2898constructorimpl;
        Throwable thM2901exceptionOrNullimpl;
        InputStream inputStreamByteStream;
        Intrinsics.checkNotNullParameter(remoteUrl, "remoteUrl");
        Intrinsics.checkNotNullParameter(authToken, "authToken");
        Intrinsics.checkNotNullParameter(targetDir, "targetDir");
        try {
            Result.Companion companion = Result.INSTANCE;
            GitController gitController = this;
            Response responseExecute = new OkHttpClient().newCall(new Request.Builder().url(StringsKt.replace$default(remoteUrl, ".git", "", false, 4, (Object) null) + "/archive/refs/heads/main.zip").header("Authorization", "token " + authToken).build()).execute();
            try {
                Response response = responseExecute;
                if (!response.isSuccessful()) {
                    throw new Exception("Failed to download ZIP: " + response.code());
                }
                File file = new File(targetDir.getParent(), "temp_" + System.currentTimeMillis() + Constants.ZIP_EXTENSION);
                ResponseBody responseBodyBody = response.body();
                if (responseBodyBody != null && (inputStreamByteStream = responseBodyBody.byteStream()) != null) {
                    InputStream inputStream = inputStreamByteStream;
                    try {
                        InputStream inputStream2 = inputStream;
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        try {
                            long jCopyTo$default = ByteStreamsKt.copyTo$default(inputStream2, fileOutputStream, 0, 2, null);
                            CloseableKt.closeFinally(fileOutputStream, null);
                            Long.valueOf(jCopyTo$default);
                            CloseableKt.closeFinally(inputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(fileOutputStream, th);
                                throw th2;
                            }
                        }
                    } catch (Throwable th3) {
                        try {
                            throw th3;
                        } catch (Throwable th4) {
                            CloseableKt.closeFinally(inputStream, th3);
                            throw th4;
                        }
                    }
                }
                unzip(file, targetDir);
                file.delete();
                CloseableKt.closeFinally(responseExecute, null);
                Git gitCall2 = Git.init().setDirectory(targetDir).call();
                try {
                    Git git = gitCall2;
                    git.remoteAdd().setName("origin").setUri(new URIish(remoteUrl)).call();
                    Log.d("GIT_DEBUG", "Создание базового коммита...");
                    git.add().addFilepattern(".").call();
                    git.commit().setMessage("Initial state from ZIP").setAuthor("NewCatroid IDE", "ide@newcatroid.org").call();
                    AutoCloseableKt.closeFinally(gitCall2, null);
                    errorM2898constructorimpl = Result.m2898constructorimpl(new GitResult.Success(Unit.INSTANCE));
                    thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(errorM2898constructorimpl);
                    if (thM2901exceptionOrNullimpl != null) {
                        thM2901exceptionOrNullimpl.printStackTrace();
                        errorM2898constructorimpl = new GitResult.Error("Download failed: " + thM2901exceptionOrNullimpl.getMessage(), thM2901exceptionOrNullimpl);
                    }
                    return (GitResult) errorM2898constructorimpl;
                } catch (Throwable th5) {
                    try {
                        throw th5;
                    } catch (Throwable th6) {
                        AutoCloseableKt.closeFinally(gitCall2, th5);
                        throw th6;
                    }
                }
            } catch (Throwable th7) {
                try {
                    throw th7;
                } catch (Throwable th8) {
                    CloseableKt.closeFinally(responseExecute, th7);
                    throw th8;
                }
            }
        } catch (Throwable th9) {
            Result.Companion companion2 = Result.INSTANCE;
            errorM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th9));
        }
        Result.Companion companion3 = Result.INSTANCE;
        errorM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th9));
        thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(errorM2898constructorimpl);
        if (thM2901exceptionOrNullimpl != null) {
            thM2901exceptionOrNullimpl.printStackTrace();
            errorM2898constructorimpl = new GitResult.Error("Download failed: " + thM2901exceptionOrNullimpl.getMessage(), thM2901exceptionOrNullimpl);
        }
        return (GitResult) errorM2898constructorimpl;
    }

    public final GitResult<Unit> commitAndPushFeature(String branchName, String token, String message) {
        Intrinsics.checkNotNullParameter(branchName, "branchName");
        Intrinsics.checkNotNullParameter(token, "token");
        Intrinsics.checkNotNullParameter(message, "message");
        try {
            System.gc();
            File file = new File(this.projectDir, ".git/index.lock");
            if (file.exists()) {
                file.delete();
            }
            WindowCacheConfig windowCacheConfig = new WindowCacheConfig();
            windowCacheConfig.setPackedGitMMAP(false);
            windowCacheConfig.setPackedGitLimit(TagBits.HasUnresolvedSuperinterfaces);
            windowCacheConfig.setPackedGitWindowSize(16384);
            windowCacheConfig.install();
            Git gitOpen = Git.open(this.projectDir);
            try {
                Git git = gitOpen;
                File file2 = new File(this.projectDir, ".gitignore");
                if (!file2.exists()) {
                    FilesKt.writeText$default(file2, "/build/\n/bin/\n/gen/\n/out/\n.gradle/\n.idea/\n*.apk\n*.dex", null, 2, null);
                }
                Log.d("GIT_DEBUG", "3. ADD...");
                git.add().addFilepattern(".").call();
                Log.d("GIT_DEBUG", "4. COMMIT...");
                git.commit().setMessage(message).setAuthor("AI Developer", "ai@newcatroid.com").call();
                Log.d("GIT_DEBUG", "5. PUSH...");
                git.push().setRemote("origin").setRefSpecs(new RefSpec("HEAD:refs/heads/" + branchName)).setForce(true).setCredentialsProvider(new UsernamePasswordCredentialsProvider(token, "")).call();
                AutoCloseableKt.closeFinally(gitOpen, null);
                Log.d("GIT_DEBUG", "6. УСПЕХ!");
                return new GitResult.Success(Unit.INSTANCE);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    AutoCloseableKt.closeFinally(gitOpen, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            String message2 = th3.getMessage();
            if (message2 != null && StringsKt.contains$default((CharSequence) message2, (CharSequence) "Inflater", false, 2, (Object) null)) {
                Log.e("GIT_DEBUG", "Критический баг Inflater. Пробую сбросить кэш...");
                WindowCache.reconfigure(new WindowCacheConfig());
            }
            String message3 = th3.getMessage();
            if (message3 == null) {
                message3 = "Push error";
            }
            return new GitResult.Error(message3, th3);
        }
    }

    private final void clearLocks() {
        File file = new File(this.projectDir, ".git/index.lock");
        if (file.exists()) {
            Log.d("GIT_DEBUG", "Удаляем застрявший lock-файл");
            file.delete();
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0093  */
    private final void unzip(File zipFile, File targetDir) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        for (ZipEntry nextEntry = zipInputStream.getNextEntry(); nextEntry != null; nextEntry = zipInputStream.getNextEntry()) {
            String name2 = nextEntry.getName();
            Intrinsics.checkNotNull(name2);
            List listSplit$default = StringsKt.split$default((CharSequence) name2, new String[]{"/"}, false, 0, 6, (Object) null);
            if (listSplit$default.size() > 1) {
                String strJoinToString$default = CollectionsKt.joinToString$default(CollectionsKt.drop(listSplit$default, 1), "/", null, null, 0, null, null, 62, null);
                if (strJoinToString$default.length() > 0) {
                    File file = new File(targetDir, strJoinToString$default);
                    if (nextEntry.isDirectory()) {
                        file.mkdirs();
                    } else {
                        File parentFile = file.getParentFile();
                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        try {
                            ByteStreamsKt.copyTo$default(zipInputStream, fileOutputStream, 0, 2, null);
                            CloseableKt.closeFinally(fileOutputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(fileOutputStream, th);
                                throw th2;
                            }
                        }
                    }
                }
            }
            zipInputStream.closeEntry();
        }
        zipInputStream.close();
    }

    public final GitResult<Unit> createAndCheckoutBranch(String branchName) {
        GitResult.Error errorM2898constructorimpl;
        Intrinsics.checkNotNullParameter(branchName, "branchName");
        try {
            Result.Companion companion = Result.INSTANCE;
            GitController gitController = this;
            Git gitOpen = Git.open(this.projectDir);
            try {
                gitOpen.checkout().setCreateBranch(true).setName(branchName).call();
                AutoCloseableKt.closeFinally(gitOpen, null);
                errorM2898constructorimpl = Result.m2898constructorimpl(new GitResult.Success(Unit.INSTANCE));
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    AutoCloseableKt.closeFinally(gitOpen, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            Result.Companion companion2 = Result.INSTANCE;
            errorM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th3));
        }
        Throwable thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(errorM2898constructorimpl);
        if (thM2901exceptionOrNullimpl != null) {
            thM2901exceptionOrNullimpl.printStackTrace();
            errorM2898constructorimpl = new GitResult.Error("Failed to create branch: " + thM2901exceptionOrNullimpl.getMessage(), thM2901exceptionOrNullimpl);
        }
        return (GitResult) errorM2898constructorimpl;
    }
}
