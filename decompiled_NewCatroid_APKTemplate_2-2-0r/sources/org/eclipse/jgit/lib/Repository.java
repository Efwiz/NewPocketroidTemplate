package org.eclipse.jgit.lib;

import com.beust.jcommander.Parameters;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.attributes.AttributesNodeProvider;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.events.IndexChangedEvent;
import org.eclipse.jgit.events.IndexChangedListener;
import org.eclipse.jgit.events.ListenerList;
import org.eclipse.jgit.events.RepositoryEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.SystemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes13.dex */
public abstract class Repository implements AutoCloseable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final FS fs;
    private final File gitDir;
    private final File indexFile;
    private final String initialBranch;
    private final File workTree;
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) Repository.class);
    private static final ListenerList globalListeners = new ListenerList();
    private static final Pattern FORBIDDEN_BRANCH_NAME_COMPONENTS = Pattern.compile("(^|/)(aux|com[1-9]|con|lpt[1-9]|nul|prn)(\\.[^/]*)?", 2);
    final AtomicInteger useCnt = new AtomicInteger(1);
    final AtomicLong closedAt = new AtomicLong();
    private final ListenerList myListeners = new ListenerList();

    private static boolean isHex(char c) {
        if ('0' <= c && c <= '9') {
            return true;
        }
        if ('a' > c || c > 'f') {
            return 'A' <= c && c <= 'F';
        }
        return true;
    }

    public void autoGC(ProgressMonitor progressMonitor) {
    }

    public abstract void create(boolean z) throws IOException;

    public abstract AttributesNodeProvider createAttributesNodeProvider();

    public abstract StoredConfig getConfig();

    @Nullable
    public String getGitwebDescription() throws IOException {
        return null;
    }

    public abstract String getIdentifier();

    public abstract ObjectDatabase getObjectDatabase();

    public abstract RefDatabase getRefDatabase();

    @Nullable
    public abstract ReflogReader getReflogReader(String str) throws IOException;

    public abstract void notifyIndexChanged(boolean z);

    public abstract void scanForRepoChanges() throws IOException;

    public static ListenerList getGlobalListenerList() {
        return globalListeners;
    }

    protected Repository(BaseRepositoryBuilder baseRepositoryBuilder) {
        this.gitDir = baseRepositoryBuilder.getGitDir();
        this.fs = baseRepositoryBuilder.getFS();
        this.workTree = baseRepositoryBuilder.getWorkTree();
        this.indexFile = baseRepositoryBuilder.getIndexFile();
        this.initialBranch = baseRepositoryBuilder.getInitialBranch();
    }

    public ListenerList getListenerList() {
        return this.myListeners;
    }

    public void fireEvent(RepositoryEvent<?> repositoryEvent) {
        repositoryEvent.setRepository(this);
        this.myListeners.dispatch(repositoryEvent);
        globalListeners.dispatch(repositoryEvent);
    }

    public void create() throws IOException {
        create(false);
    }

    public File getDirectory() {
        return this.gitDir;
    }

    public ObjectInserter newObjectInserter() {
        return getObjectDatabase().newInserter();
    }

    public ObjectReader newObjectReader() {
        return getObjectDatabase().newReader();
    }

    public FS getFS() {
        return this.fs;
    }

    @Deprecated
    public boolean hasObject(AnyObjectId anyObjectId) {
        try {
            return getObjectDatabase().has(anyObjectId);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public ObjectLoader open(AnyObjectId anyObjectId) throws IOException {
        return getObjectDatabase().open(anyObjectId);
    }

    public ObjectLoader open(AnyObjectId anyObjectId, int i) throws IOException {
        return getObjectDatabase().open(anyObjectId, i);
    }

    public RefUpdate updateRef(String str) throws IOException {
        return updateRef(str, false);
    }

    public RefUpdate updateRef(String str, boolean z) throws IOException {
        return getRefDatabase().newUpdate(str, z);
    }

    public RefRename renameRef(String str, String str2) throws IOException {
        return getRefDatabase().newRename(str, str2);
    }

    @Nullable
    public ObjectId resolve(String str) throws RevisionSyntaxException, IOException {
        Throwable th = null;
        try {
            RevWalk revWalk = new RevWalk(this);
            try {
                revWalk.setRetainBody(false);
                Object objResolve = resolve(revWalk, str);
                if (objResolve instanceof String) {
                    Ref refFindRef = findRef((String) objResolve);
                    return refFindRef != null ? refFindRef.getLeaf().getObjectId() : null;
                }
                return (ObjectId) objResolve;
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

    @Nullable
    public String simplify(String str) throws IOException {
        Throwable th = null;
        try {
            RevWalk revWalk = new RevWalk(this);
            try {
                revWalk.setRetainBody(true);
                Object objResolve = resolve(revWalk, str);
                if (objResolve == null) {
                    return null;
                }
                if (objResolve instanceof String) {
                    return (String) objResolve;
                }
                return ((AnyObjectId) objResolve).getName();
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

    /* JADX WARN: Code duplicated, block: B:100:0x0150  */
    /* JADX WARN: Code duplicated, block: B:102:0x0159  */
    /* JADX WARN: Code duplicated, block: B:103:0x015b  */
    /* JADX WARN: Code duplicated, block: B:109:0x0179  */
    /* JADX WARN: Code duplicated, block: B:113:0x0181  */
    /* JADX WARN: Code duplicated, block: B:117:0x0192  */
    /* JADX WARN: Code duplicated, block: B:119:0x019a  */
    /* JADX WARN: Code duplicated, block: B:120:0x019f  */
    /* JADX WARN: Code duplicated, block: B:122:0x01a5  */
    /* JADX WARN: Code duplicated, block: B:123:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:125:0x01b2  */
    /* JADX WARN: Code duplicated, block: B:127:0x01ba  */
    /* JADX WARN: Code duplicated, block: B:130:0x01c2  */
    /* JADX WARN: Code duplicated, block: B:132:0x01c8  */
    /* JADX WARN: Code duplicated, block: B:138:0x01dc A[LOOP:4: B:110:0x017c->B:138:0x01dc, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:139:0x01df  */
    /* JADX WARN: Code duplicated, block: B:141:0x01e7  */
    /* JADX WARN: Code duplicated, block: B:143:0x01ef  */
    /* JADX WARN: Code duplicated, block: B:144:0x01f2  */
    /* JADX WARN: Code duplicated, block: B:190:0x02ac  */
    /* JADX WARN: Code duplicated, block: B:262:0x012b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:263:0x01d6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:264:0x01bc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:265:0x01d0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:266:0x01f8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:271:0x02af A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:288:0x0187 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:289:0x017f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x010d  */
    /* JADX WARN: Code duplicated, block: B:82:0x0111  */
    /* JADX WARN: Code duplicated, block: B:83:0x0114  */
    /* JADX WARN: Code duplicated, block: B:85:0x011c  */
    /* JADX WARN: Code duplicated, block: B:88:0x0126  */
    /* JADX WARN: Code duplicated, block: B:91:0x0131  */
    @Nullable
    private Object resolve(RevWalk revWalk, String str) throws IOException {
        String str2;
        String str3;
        RevObject revObjectResolveReflog;
        int i;
        RevObject revObjectPeel;
        RevCommit revCommit;
        char c;
        int i2;
        int i3;
        String str4;
        RevObject revObjectPeel2;
        RevObject revObjectPeel3;
        RevCommit commit;
        int i4;
        int i5;
        RevObject revObjectPeel4;
        RevCommit revCommit2;
        int i6;
        char[] charArray = str.toCharArray();
        int i7 = 0;
        RevObject simple = null;
        String str5 = null;
        int i8 = 0;
        int i9 = 0;
        while (i8 < charArray.length) {
            char c2 = charArray[i8];
            if (c2 == ':') {
                if (simple == null) {
                    if (str5 == null) {
                        str5 = new String(charArray, i9, i8);
                    }
                    simple = parseSimple(revWalk, str5.isEmpty() ? "HEAD" : str5);
                }
                if (simple == null) {
                    return null;
                }
                RevTree tree = revWalk.parseTree(simple);
                if (i8 == charArray.length - 1) {
                    return tree.copy();
                }
                TreeWalk treeWalkForPath = TreeWalk.forPath(revWalk.getObjectReader(), new String(charArray, i8 + 1, (charArray.length - i8) - 1), tree);
                if (treeWalkForPath != null) {
                    return treeWalkForPath.getObjectId(0);
                }
                return null;
            }
            if (c2 != '@') {
                if (c2 != '^') {
                    if (c2 == '~') {
                        if (simple == null) {
                            if (str5 == null) {
                                if (i9 == 0) {
                                    str5 = new String(charArray, i9, i8);
                                }
                            }
                            simple = parseSimple(revWalk, str5);
                            if (simple == null) {
                                return null;
                            }
                            str5 = null;
                        }
                        simple = revWalk.peel(simple);
                        if (!(simple instanceof RevCommit)) {
                            throw new IncorrectObjectTypeException(simple, "commit");
                        }
                        int i10 = i8 + 1;
                        int i11 = i10;
                        while (i11 < charArray.length && Character.isDigit(charArray[i11])) {
                            i11++;
                        }
                        int i12 = i11 - i8;
                        if (i12 > 1) {
                            try {
                                i6 = Integer.parseInt(new String(charArray, i10, i12 - 1));
                            } catch (NumberFormatException e) {
                                RevisionSyntaxException revisionSyntaxException = new RevisionSyntaxException(JGitText.get().invalidAncestryLength, str);
                                revisionSyntaxException.initCause(e);
                                throw revisionSyntaxException;
                            }
                        } else {
                            i6 = 1;
                        }
                        while (i6 > 0) {
                            RevCommit revCommit3 = (RevCommit) simple;
                            if (revCommit3.getParentCount() == 0) {
                                simple = null;
                                i8 = i11 - 1;
                                i9 = i11;
                            } else {
                                simple = revCommit3.getParent(i7);
                                revWalk.parseHeaders(simple);
                                i6--;
                            }
                        }
                        i8 = i11 - 1;
                        i9 = i11;
                    } else if (simple != null) {
                        throw new RevisionSyntaxException(str);
                    }
                } else if (simple == null) {
                    if (str5 == null) {
                        if (i9 == 0) {
                            str5 = new String(charArray, i9, i8);
                        }
                    }
                    simple = parseSimple(revWalk, str5);
                    if (simple == null) {
                        return null;
                    }
                    str5 = null;
                    i = i8 + 1;
                    if (i < charArray.length) {
                        c = charArray[i];
                        if (c != '{') {
                            switch (c) {
                                case '0':
                                case '1':
                                case '2':
                                case '3':
                                case '4':
                                case '5':
                                case '6':
                                case '7':
                                case '8':
                                case '9':
                                    commit = revWalk.parseCommit(simple);
                                    i4 = i;
                                    while (i4 < charArray.length) {
                                        i4++;
                                    }
                                    i5 = Integer.parseInt(new String(charArray, i, (i4 - i8) - 1));
                                    if (i5 != 0) {
                                        if (i5 > commit.getParentCount()) {
                                            commit = null;
                                        } else {
                                            commit = commit.getParent(i5 - 1);
                                        }
                                    }
                                    i8 = i4 - 1;
                                    simple = commit;
                                    break;
                                default:
                                    revObjectPeel4 = revWalk.peel(simple);
                                    if (revObjectPeel4 instanceof RevCommit) {
                                        revCommit2 = (RevCommit) revObjectPeel4;
                                        if (revCommit2.getParentCount() == 0) {
                                            simple = null;
                                        } else {
                                            simple = revCommit2.getParent(i7);
                                        }
                                    } else {
                                        throw new IncorrectObjectTypeException(revObjectPeel4, "commit");
                                    }
                                    break;
                            }
                        } else {
                            i2 = i8 + 2;
                            i3 = i2;
                            while (true) {
                                if (i3 >= charArray.length) {
                                    str4 = null;
                                } else if (charArray[i3] == '}') {
                                    str4 = new String(charArray, i2, (i3 - i8) - 2);
                                } else {
                                    i3++;
                                }
                            }
                            if (str4 != null) {
                                if (str4.equals(Constants.TYPE_TREE)) {
                                    revObjectPeel2 = revWalk.parseTree(simple);
                                } else if (str4.equals("commit")) {
                                    revObjectPeel2 = revWalk.parseCommit(simple);
                                } else if (str4.equals(Constants.TYPE_BLOB)) {
                                    revObjectPeel3 = revWalk.peel(simple);
                                    if (revObjectPeel3 instanceof RevBlob) {
                                        throw new IncorrectObjectTypeException(revObjectPeel3, Constants.TYPE_BLOB);
                                    }
                                    revObjectPeel2 = revObjectPeel3;
                                } else if (str4.isEmpty()) {
                                    revObjectPeel2 = revWalk.peel(simple);
                                } else {
                                    throw new RevisionSyntaxException(str);
                                }
                                simple = revObjectPeel2;
                                i8 = i3;
                            } else {
                                throw new RevisionSyntaxException(str);
                            }
                        }
                    } else {
                        revObjectPeel = revWalk.peel(simple);
                        if (revObjectPeel instanceof RevCommit) {
                            revCommit = (RevCommit) revObjectPeel;
                            if (revCommit.getParentCount() == 0) {
                                simple = null;
                            } else {
                                simple = revCommit.getParent(i7);
                            }
                        } else {
                            throw new IncorrectObjectTypeException(revObjectPeel, "commit");
                        }
                    }
                } else {
                    i = i8 + 1;
                    if (i < charArray.length) {
                        c = charArray[i];
                        if (c != '{') {
                            switch (c) {
                                case '0':
                                case '1':
                                case '2':
                                case '3':
                                case '4':
                                case '5':
                                case '6':
                                case '7':
                                case '8':
                                case '9':
                                    commit = revWalk.parseCommit(simple);
                                    i4 = i;
                                    while (i4 < charArray.length && Character.isDigit(charArray[i4])) {
                                        i4++;
                                    }
                                    try {
                                        i5 = Integer.parseInt(new String(charArray, i, (i4 - i8) - 1));
                                        if (i5 != 0) {
                                            if (i5 > commit.getParentCount()) {
                                                commit = null;
                                            } else {
                                                commit = commit.getParent(i5 - 1);
                                            }
                                        }
                                        i8 = i4 - 1;
                                        simple = commit;
                                    } catch (NumberFormatException e2) {
                                        RevisionSyntaxException revisionSyntaxException2 = new RevisionSyntaxException(JGitText.get().invalidCommitParentNumber, str);
                                        revisionSyntaxException2.initCause(e2);
                                        throw revisionSyntaxException2;
                                    }
                                    break;
                                default:
                                    revObjectPeel4 = revWalk.peel(simple);
                                    if (revObjectPeel4 instanceof RevCommit) {
                                        revCommit2 = (RevCommit) revObjectPeel4;
                                        if (revCommit2.getParentCount() == 0) {
                                            simple = null;
                                        } else {
                                            simple = revCommit2.getParent(i7);
                                        }
                                    } else {
                                        throw new IncorrectObjectTypeException(revObjectPeel4, "commit");
                                    }
                                    break;
                            }
                        } else {
                            i2 = i8 + 2;
                            i3 = i2;
                            while (true) {
                                if (i3 >= charArray.length) {
                                    str4 = null;
                                } else if (charArray[i3] == '}') {
                                    str4 = new String(charArray, i2, (i3 - i8) - 2);
                                } else {
                                    i3++;
                                }
                            }
                            if (str4 != null) {
                                if (str4.equals(Constants.TYPE_TREE)) {
                                    revObjectPeel2 = revWalk.parseTree(simple);
                                } else if (str4.equals("commit")) {
                                    revObjectPeel2 = revWalk.parseCommit(simple);
                                } else if (str4.equals(Constants.TYPE_BLOB)) {
                                    revObjectPeel3 = revWalk.peel(simple);
                                    if (revObjectPeel3 instanceof RevBlob) {
                                        throw new IncorrectObjectTypeException(revObjectPeel3, Constants.TYPE_BLOB);
                                    }
                                    revObjectPeel2 = revObjectPeel3;
                                } else if (str4.isEmpty()) {
                                    revObjectPeel2 = revWalk.peel(simple);
                                } else {
                                    throw new RevisionSyntaxException(str);
                                }
                                simple = revObjectPeel2;
                                i8 = i3;
                            } else {
                                throw new RevisionSyntaxException(str);
                            }
                        }
                    } else {
                        revObjectPeel = revWalk.peel(simple);
                        if (revObjectPeel instanceof RevCommit) {
                            revCommit = (RevCommit) revObjectPeel;
                            if (revCommit.getParentCount() == 0) {
                                simple = null;
                            } else {
                                simple = revCommit.getParent(i7);
                            }
                        } else {
                            throw new IncorrectObjectTypeException(revObjectPeel, "commit");
                        }
                    }
                }
                i9 = i8 + 1;
            } else {
                if (simple != null) {
                    throw new RevisionSyntaxException(str);
                }
                int i13 = i8 + 1;
                if (i13 != charArray.length && (i13 >= charArray.length || charArray[i13] == '{')) {
                    int i14 = i8 + 2;
                    int i15 = i14;
                    while (true) {
                        if (i15 >= charArray.length) {
                            str2 = null;
                        } else if (charArray[i15] == '}') {
                            str2 = new String(charArray, i14, (i15 - i8) - 2);
                        } else {
                            i15++;
                        }
                    }
                    if (str2 != null) {
                        if (str2.equals("upstream")) {
                            if (str5 == null) {
                                str5 = new String(charArray, i9, i8);
                            }
                            str3 = str5.isEmpty() ? "HEAD" : str5;
                            if (!isValidRefName("x/" + str3)) {
                                throw new RevisionSyntaxException(MessageFormat.format(JGitText.get().invalidRefName, str3), str);
                            }
                            Ref refFindRef = findRef(str3);
                            if (refFindRef == null) {
                                return null;
                            }
                            if (refFindRef.isSymbolic()) {
                                refFindRef = refFindRef.getLeaf();
                            }
                            String name2 = refFindRef.getName();
                            try {
                                RemoteConfig remoteConfig = new RemoteConfig(getConfig(), "origin");
                                String string = getConfig().getString(ConfigConstants.CONFIG_BRANCH_SECTION, shortenRefName(refFindRef.getName()), "merge");
                                for (RefSpec refSpec : remoteConfig.getFetchRefSpecs()) {
                                    if (refSpec.matchSource(string)) {
                                        name2 = refSpec.expandFromSource(string).getDestination();
                                        if (name2 != null) {
                                            throw new RevisionSyntaxException(str);
                                        }
                                        str5 = name2;
                                    }
                                }
                                if (name2 != null) {
                                    throw new RevisionSyntaxException(str);
                                }
                                str5 = name2;
                            } catch (URISyntaxException e3) {
                                RevisionSyntaxException revisionSyntaxException3 = new RevisionSyntaxException(str);
                                revisionSyntaxException3.initCause(e3);
                                throw revisionSyntaxException3;
                            }
                        } else {
                            if (str2.matches("^-\\d+$")) {
                                if (str5 != null) {
                                    throw new RevisionSyntaxException(str);
                                }
                                String strResolveReflogCheckout = resolveReflogCheckout(-Integer.parseInt(str2));
                                if (ObjectId.isId(strResolveReflogCheckout)) {
                                    revObjectResolveReflog = parseSimple(revWalk, strResolveReflogCheckout);
                                } else {
                                    str5 = strResolveReflogCheckout;
                                }
                            } else {
                                if (str5 == null) {
                                    str5 = new String(charArray, i9, i8);
                                }
                                str3 = str5.isEmpty() ? "HEAD" : str5;
                                if (!isValidRefName("x/" + str3)) {
                                    throw new RevisionSyntaxException(MessageFormat.format(JGitText.get().invalidRefName, str3), str);
                                }
                                Ref refFindRef2 = findRef(str3);
                                if (refFindRef2 == null) {
                                    return null;
                                }
                                if (refFindRef2.isSymbolic()) {
                                    refFindRef2 = refFindRef2.getLeaf();
                                }
                                revObjectResolveReflog = resolveReflog(revWalk, refFindRef2, str2);
                                str5 = null;
                            }
                            simple = revObjectResolveReflog;
                        }
                        i8 = i15;
                    } else {
                        throw new RevisionSyntaxException(str);
                    }
                }
            }
            i8++;
            i7 = 0;
        }
        if (simple != null) {
            return simple.copy();
        }
        if (str5 != null) {
            return str5;
        }
        if (i9 == str.length()) {
            return null;
        }
        String strSubstring = str.substring(i9);
        if (isValidRefName("x/" + strSubstring)) {
            return findRef(strSubstring) != null ? strSubstring : resolveSimple(strSubstring);
        }
        throw new RevisionSyntaxException(MessageFormat.format(JGitText.get().invalidRefName, strSubstring), str);
    }

    private static boolean isAllHex(String str, int i) {
        while (i < str.length()) {
            int i2 = i + 1;
            if (!isHex(str.charAt(i))) {
                return false;
            }
            i = i2;
        }
        return true;
    }

    @Nullable
    private RevObject parseSimple(RevWalk revWalk, String str) throws IOException {
        ObjectId objectIdResolveSimple = resolveSimple(str);
        if (objectIdResolveSimple != null) {
            return revWalk.parseAny(objectIdResolveSimple);
        }
        return null;
    }

    @Nullable
    private ObjectId resolveSimple(String str) throws IOException {
        Ref refFindRef;
        if (ObjectId.isId(str)) {
            return ObjectId.fromString(str);
        }
        if (isValidRefName("x/" + str) && (refFindRef = getRefDatabase().findRef(str)) != null) {
            return refFindRef.getObjectId();
        }
        if (AbbreviatedObjectId.isId(str)) {
            return resolveAbbreviation(str);
        }
        int iIndexOf = str.indexOf("-g");
        if (iIndexOf + 5 >= str.length() || iIndexOf < 0) {
            return null;
        }
        int i = iIndexOf + 2;
        if (!isHex(str.charAt(i)) || !isHex(str.charAt(iIndexOf + 3)) || !isAllHex(str, iIndexOf + 4)) {
            return null;
        }
        String strSubstring = str.substring(i);
        if (AbbreviatedObjectId.isId(strSubstring)) {
            return resolveAbbreviation(strSubstring);
        }
        return null;
    }

    @Nullable
    private String resolveReflogCheckout(int i) throws IOException {
        ReflogReader reflogReader = getReflogReader("HEAD");
        if (reflogReader == null) {
            return null;
        }
        Iterator<ReflogEntry> it = reflogReader.getReverseEntries().iterator();
        while (it.hasNext()) {
            CheckoutEntry checkout = it.next().parseCheckout();
            if (checkout != null) {
                int i2 = i - 1;
                if (i == 1) {
                    return checkout.getFromBranch();
                }
                i = i2;
            }
        }
        return null;
    }

    private RevCommit resolveReflog(RevWalk revWalk, Ref ref, String str) throws IOException {
        try {
            int i = Integer.parseInt(str);
            ReflogReader reflogReader = getReflogReader(ref.getName());
            if (reflogReader == null) {
                throw new RevisionSyntaxException(MessageFormat.format(JGitText.get().reflogEntryNotFound, Integer.valueOf(i), ref.getName()));
            }
            ReflogEntry reverseEntry = reflogReader.getReverseEntry(i);
            if (reverseEntry == null) {
                throw new RevisionSyntaxException(MessageFormat.format(JGitText.get().reflogEntryNotFound, Integer.valueOf(i), ref.getName()));
            }
            return revWalk.parseCommit(reverseEntry.getNewId());
        } catch (NumberFormatException e) {
            RevisionSyntaxException revisionSyntaxException = new RevisionSyntaxException(MessageFormat.format(JGitText.get().invalidReflogRevision, str));
            revisionSyntaxException.initCause(e);
            throw revisionSyntaxException;
        }
    }

    @Nullable
    private ObjectId resolveAbbreviation(String str) throws IOException {
        AbbreviatedObjectId abbreviatedObjectIdFromString = AbbreviatedObjectId.fromString(str);
        Throwable th = null;
        try {
            ObjectReader objectReaderNewObjectReader = newObjectReader();
            try {
                Collection<ObjectId> collectionResolve = objectReaderNewObjectReader.resolve(abbreviatedObjectIdFromString);
                if (collectionResolve.isEmpty()) {
                    if (objectReaderNewObjectReader != null) {
                        objectReaderNewObjectReader.close();
                    }
                    return null;
                }
                if (collectionResolve.size() == 1) {
                    ObjectId next = collectionResolve.iterator().next();
                    if (objectReaderNewObjectReader != null) {
                        objectReaderNewObjectReader.close();
                    }
                    return next;
                }
                throw new AmbiguousObjectException(abbreviatedObjectIdFromString, collectionResolve);
            } catch (Throwable th2) {
                if (objectReaderNewObjectReader == null) {
                    throw th2;
                }
                objectReaderNewObjectReader.close();
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

    public void incrementOpen() {
        this.useCnt.incrementAndGet();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        int iDecrementAndGet = this.useCnt.decrementAndGet();
        if (iDecrementAndGet == 0) {
            if (RepositoryCache.isCached(this)) {
                this.closedAt.set(System.currentTimeMillis());
                return;
            } else {
                doClose();
                return;
            }
        }
        if (iDecrementAndGet == -1) {
            String str = MessageFormat.format(JGitText.get().corruptUseCnt, toString());
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug(str, (Throwable) new IllegalStateException());
            } else {
                logger.warn(str);
            }
            if (RepositoryCache.isCached(this)) {
                this.closedAt.set(System.currentTimeMillis());
            }
        }
    }

    protected void doClose() {
        getObjectDatabase().close();
        getRefDatabase().close();
    }

    public String toString() {
        String path;
        File directory = getDirectory();
        if (directory != null) {
            path = directory.getPath();
        } else {
            path = String.valueOf(getClass().getSimpleName()) + Parameters.DEFAULT_OPTION_PREFIXES + System.identityHashCode(this);
        }
        return "Repository[" + path + "]";
    }

    @Nullable
    public String getFullBranch() throws IOException {
        Ref refExactRef = exactRef("HEAD");
        if (refExactRef == null) {
            return null;
        }
        if (refExactRef.isSymbolic()) {
            return refExactRef.getTarget().getName();
        }
        ObjectId objectId = refExactRef.getObjectId();
        if (objectId != null) {
            return objectId.name();
        }
        return null;
    }

    @Nullable
    public String getBranch() throws IOException {
        String fullBranch = getFullBranch();
        if (fullBranch != null) {
            return shortenRefName(fullBranch);
        }
        return null;
    }

    protected String getInitialBranch() {
        return this.initialBranch;
    }

    public Set<ObjectId> getAdditionalHaves() {
        return Collections.emptySet();
    }

    @Nullable
    public final Ref exactRef(String str) throws IOException {
        return getRefDatabase().exactRef(str);
    }

    @Nullable
    public final Ref findRef(String str) throws IOException {
        return getRefDatabase().findRef(str);
    }

    @Deprecated
    public Map<String, Ref> getAllRefs() {
        try {
            return getRefDatabase().getRefs("");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Deprecated
    public Map<String, Ref> getTags() {
        try {
            return getRefDatabase().getRefs(Constants.R_TAGS);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Deprecated
    public Ref peel(Ref ref) {
        try {
            return getRefDatabase().peel(ref);
        } catch (IOException unused) {
            return ref;
        }
    }

    public Map<AnyObjectId, Set<Ref>> getAllRefsByPeeledObjectId() {
        Map<String, Ref> allRefs = getAllRefs();
        HashMap map = new HashMap(allRefs.size());
        Iterator<Ref> it = allRefs.values().iterator();
        while (it.hasNext()) {
            Ref refPeel = peel(it.next());
            ObjectId peeledObjectId = refPeel.getPeeledObjectId();
            if (peeledObjectId == null) {
                peeledObjectId = refPeel.getObjectId();
            }
            Set hashSet = (Set) map.put(peeledObjectId, Collections.singleton(refPeel));
            if (hashSet != null) {
                if (hashSet.size() == 1) {
                    hashSet = new HashSet(hashSet);
                }
                map.put(peeledObjectId, hashSet);
                hashSet.add(refPeel);
            }
        }
        return map;
    }

    public File getIndexFile() throws NoWorkTreeException {
        if (isBare()) {
            throw new NoWorkTreeException();
        }
        return this.indexFile;
    }

    public RevCommit parseCommit(AnyObjectId anyObjectId) throws IOException {
        if (anyObjectId instanceof RevCommit) {
            RevCommit revCommit = (RevCommit) anyObjectId;
            if (revCommit.getRawBuffer() != null) {
                return revCommit;
            }
        }
        Throwable th = null;
        try {
            RevWalk revWalk = new RevWalk(this);
            try {
                return revWalk.parseCommit(anyObjectId);
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

    public DirCache readDirCache() throws NoWorkTreeException, IOException {
        return DirCache.read(this);
    }

    public DirCache lockDirCache() throws NoWorkTreeException, IOException {
        return DirCache.lock(this, new IndexChangedListener() { // from class: org.eclipse.jgit.lib.Repository$$ExternalSyntheticLambda0
            @Override // org.eclipse.jgit.events.IndexChangedListener
            public final void onIndexChanged(IndexChangedEvent indexChangedEvent) {
                this.f$0.m6043lambda$0$orgeclipsejgitlibRepository(indexChangedEvent);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$0$org-eclipse-jgit-lib-Repository, reason: not valid java name */
    /* synthetic */ void m6043lambda$0$orgeclipsejgitlibRepository(IndexChangedEvent indexChangedEvent) {
        notifyIndexChanged(true);
    }

    public RepositoryState getRepositoryState() {
        if (isBare() || getDirectory() == null) {
            return RepositoryState.BARE;
        }
        if (new File(getWorkTree(), ".dotest").exists()) {
            return RepositoryState.REBASING;
        }
        if (new File(getDirectory(), ".dotest-merge").exists()) {
            return RepositoryState.REBASING_INTERACTIVE;
        }
        if (new File(getDirectory(), "rebase-apply/rebasing").exists()) {
            return RepositoryState.REBASING_REBASING;
        }
        if (new File(getDirectory(), "rebase-apply/applying").exists()) {
            return RepositoryState.APPLY;
        }
        if (new File(getDirectory(), "rebase-apply").exists()) {
            return RepositoryState.REBASING;
        }
        if (new File(getDirectory(), "rebase-merge/interactive").exists()) {
            return RepositoryState.REBASING_INTERACTIVE;
        }
        if (new File(getDirectory(), RebaseCommand.REBASE_MERGE).exists()) {
            return RepositoryState.REBASING_MERGE;
        }
        if (new File(getDirectory(), Constants.MERGE_HEAD).exists()) {
            try {
                if (!readDirCache().hasUnmergedPaths()) {
                    return RepositoryState.MERGING_RESOLVED;
                }
                return RepositoryState.MERGING;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        if (new File(getDirectory(), "BISECT_LOG").exists()) {
            return RepositoryState.BISECTING;
        }
        if (new File(getDirectory(), Constants.CHERRY_PICK_HEAD).exists()) {
            try {
                if (!readDirCache().hasUnmergedPaths()) {
                    return RepositoryState.CHERRY_PICKING_RESOLVED;
                }
                return RepositoryState.CHERRY_PICKING;
            } catch (IOException e2) {
                throw new UncheckedIOException(e2);
            }
        }
        if (new File(getDirectory(), Constants.REVERT_HEAD).exists()) {
            try {
                if (!readDirCache().hasUnmergedPaths()) {
                    return RepositoryState.REVERTING_RESOLVED;
                }
                return RepositoryState.REVERTING;
            } catch (IOException e3) {
                throw new UncheckedIOException(e3);
            }
        }
        return RepositoryState.SAFE;
    }

    public static boolean isValidRefName(String str) {
        int length = str.length();
        if (length == 0 || str.endsWith(Constants.LOCK_SUFFIX)) {
            return false;
        }
        try {
            SystemReader.getInstance().checkPath(str);
            int i = 0;
            char c = 0;
            int i2 = 1;
            while (i < length) {
                char cCharAt = str.charAt(i);
                if (cCharAt > ' ' && cCharAt != '*' && cCharAt != ':' && cCharAt != '?' && cCharAt != '^') {
                    if (cCharAt != '{') {
                        if (cCharAt != '.') {
                            if (cCharAt != '/') {
                                if (cCharAt == '[' || cCharAt == '\\' || cCharAt == '~' || cCharAt == 127) {
                                }
                            } else {
                                if (i == 0 || i == length - 1 || c == '/') {
                                    return false;
                                }
                                i2++;
                            }
                        } else if (c == 0 || c == '.' || c == '/' || i == length - 1) {
                            return false;
                        }
                    } else if (c == '@') {
                        return false;
                    }
                    i++;
                    c = cCharAt;
                }
                return false;
            }
            return i2 > 1;
        } catch (CorruptObjectException unused) {
        }
    }

    /* JADX WARN: Code duplicated, block: B:51:0x00aa  */
    public static String normalizeBranchName(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        String strTrim = str.trim();
        if (isValidRefName(strTrim.startsWith(Constants.R_HEADS) ? strTrim : Constants.R_HEADS + strTrim)) {
            return strTrim;
        }
        String strReplaceAll = strTrim.replaceAll("(?:\\h|\\v)+", "_");
        StringBuilder sb = new StringBuilder();
        int length = strReplaceAll.length();
        char c = '/';
        for (int i = 0; i < length; i++) {
            char cCharAt = strReplaceAll.charAt(i);
            if (cCharAt >= ' ' && cCharAt != 127) {
                if (cCharAt != '\"' && cCharAt != '*' && cCharAt != ':' && cCharAt != '<' && cCharAt != '^' && cCharAt != '|' && cCharAt != '~' && cCharAt != '[' && cCharAt != '\\') {
                    switch (cCharAt) {
                        case '>':
                        case '?':
                        case '@':
                            cCharAt = '-';
                            break;
                    }
                } else {
                    cCharAt = '-';
                }
                if (cCharAt != '_') {
                    switch (cCharAt) {
                        case '-':
                        case '.':
                            if (c == '/' && c != '-') {
                                c = '-';
                                sb.append(cCharAt);
                            }
                            break;
                        case '/':
                            if (c != '/') {
                                c = '/';
                                sb.append(cCharAt);
                            }
                            break;
                        default:
                            c = cCharAt;
                            sb.append(cCharAt);
                            break;
                    }
                } else if (c == '/') {
                }
            }
        }
        return FORBIDDEN_BRANCH_NAME_COMPONENTS.matcher(sb.toString().replaceFirst("[/_.-]+$", "").replaceAll("\\.lock($|/)", "_lock$1")).replaceAll("$1+$2$3");
    }

    public static String stripWorkDir(File file, File file2) {
        String path = file2.getPath();
        String path2 = file.getPath();
        if (path.length() <= path2.length() || path.charAt(path2.length()) != File.separatorChar || !path.startsWith(path2)) {
            File absoluteFile = file.isAbsolute() ? file : file.getAbsoluteFile();
            File absoluteFile2 = file2.isAbsolute() ? file2 : file2.getAbsoluteFile();
            if (absoluteFile.equals(file) && absoluteFile2.equals(file2)) {
                return "";
            }
            return stripWorkDir(absoluteFile, absoluteFile2);
        }
        String strSubstring = path.substring(path2.length() + 1);
        return File.separatorChar != '/' ? strSubstring.replace(File.separatorChar, '/') : strSubstring;
    }

    public boolean isBare() {
        return this.workTree == null;
    }

    public File getWorkTree() throws NoWorkTreeException {
        if (isBare()) {
            throw new NoWorkTreeException();
        }
        return this.workTree;
    }

    public static String shortenRefName(String str) {
        if (str.startsWith(Constants.R_HEADS)) {
            return str.substring(Constants.R_HEADS.length());
        }
        if (str.startsWith(Constants.R_TAGS)) {
            return str.substring(Constants.R_TAGS.length());
        }
        return str.startsWith(Constants.R_REMOTES) ? str.substring(Constants.R_REMOTES.length()) : str;
    }

    @Nullable
    public String shortenRemoteBranchName(String str) {
        Iterator<String> it = getRemoteNames().iterator();
        while (it.hasNext()) {
            String str2 = Constants.R_REMOTES + it.next() + "/";
            if (str.startsWith(str2)) {
                return str.substring(str2.length());
            }
        }
        return null;
    }

    @Nullable
    public String getRemoteName(String str) {
        for (String str2 : getRemoteNames()) {
            if (str.startsWith(Constants.R_REMOTES + str2 + "/")) {
                return str2;
            }
        }
        return null;
    }

    public void setGitwebDescription(@Nullable String str) throws IOException {
        throw new IOException(JGitText.get().unsupportedRepositoryDescription);
    }

    @Nullable
    public String readMergeCommitMsg() throws NoWorkTreeException, IOException {
        return readCommitMsgFile(Constants.MERGE_MSG);
    }

    public void writeMergeCommitMsg(String str) throws IOException {
        writeCommitMsg(new File(this.gitDir, Constants.MERGE_MSG), str);
    }

    @Nullable
    public String readCommitEditMsg() throws NoWorkTreeException, IOException {
        return readCommitMsgFile(Constants.COMMIT_EDITMSG);
    }

    public void writeCommitEditMsg(String str) throws IOException {
        writeCommitMsg(new File(this.gitDir, Constants.COMMIT_EDITMSG), str);
    }

    @Nullable
    public List<ObjectId> readMergeHeads() throws NoWorkTreeException, IOException {
        if (isBare() || getDirectory() == null) {
            throw new NoWorkTreeException();
        }
        byte[] gitDirectoryFile = readGitDirectoryFile(Constants.MERGE_HEAD);
        if (gitDirectoryFile == null) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        for (int iNextLF = 0; iNextLF < gitDirectoryFile.length; iNextLF = RawParseUtils.nextLF(gitDirectoryFile, iNextLF + 40)) {
            linkedList.add(ObjectId.fromString(gitDirectoryFile, iNextLF));
        }
        return linkedList;
    }

    public void writeMergeHeads(List<? extends ObjectId> list) throws IOException {
        writeHeadsFile(list, Constants.MERGE_HEAD);
    }

    @Nullable
    public ObjectId readCherryPickHead() throws NoWorkTreeException, IOException {
        if (isBare() || getDirectory() == null) {
            throw new NoWorkTreeException();
        }
        byte[] gitDirectoryFile = readGitDirectoryFile(Constants.CHERRY_PICK_HEAD);
        if (gitDirectoryFile == null) {
            return null;
        }
        return ObjectId.fromString(gitDirectoryFile, 0);
    }

    @Nullable
    public ObjectId readRevertHead() throws NoWorkTreeException, IOException {
        if (isBare() || getDirectory() == null) {
            throw new NoWorkTreeException();
        }
        byte[] gitDirectoryFile = readGitDirectoryFile(Constants.REVERT_HEAD);
        if (gitDirectoryFile == null) {
            return null;
        }
        return ObjectId.fromString(gitDirectoryFile, 0);
    }

    public void writeCherryPickHead(ObjectId objectId) throws IOException {
        writeHeadsFile(objectId != null ? Collections.singletonList(objectId) : null, Constants.CHERRY_PICK_HEAD);
    }

    public void writeRevertHead(ObjectId objectId) throws IOException {
        writeHeadsFile(objectId != null ? Collections.singletonList(objectId) : null, Constants.REVERT_HEAD);
    }

    public void writeOrigHead(ObjectId objectId) throws IOException {
        writeHeadsFile(objectId != null ? Collections.singletonList(objectId) : null, Constants.ORIG_HEAD);
    }

    @Nullable
    public ObjectId readOrigHead() throws NoWorkTreeException, IOException {
        if (isBare() || getDirectory() == null) {
            throw new NoWorkTreeException();
        }
        byte[] gitDirectoryFile = readGitDirectoryFile(Constants.ORIG_HEAD);
        if (gitDirectoryFile != null) {
            return ObjectId.fromString(gitDirectoryFile, 0);
        }
        return null;
    }

    @Nullable
    public String readSquashCommitMsg() throws IOException {
        return readCommitMsgFile(Constants.SQUASH_MSG);
    }

    public void writeSquashCommitMsg(String str) throws IOException {
        writeCommitMsg(new File(this.gitDir, Constants.SQUASH_MSG), str);
    }

    @Nullable
    private String readCommitMsgFile(String str) throws IOException {
        if (isBare() || getDirectory() == null) {
            throw new NoWorkTreeException();
        }
        File file = new File(getDirectory(), str);
        try {
            return RawParseUtils.decode(IO.readFully(file));
        } catch (FileNotFoundException e) {
            if (file.exists()) {
                throw e;
            }
            return null;
        }
    }

    private void writeCommitMsg(File file, String str) throws IOException {
        if (str != null) {
            Throwable th = null;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    fileOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
                    return;
                } finally {
                    fileOutputStream.close();
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
        FileUtils.delete(file, 4);
    }

    private byte[] readGitDirectoryFile(String str) throws IOException {
        File file = new File(getDirectory(), str);
        try {
            byte[] fully = IO.readFully(file);
            if (fully.length > 0) {
                return fully;
            }
            return null;
        } catch (FileNotFoundException e) {
            if (file.exists()) {
                throw e;
            }
            return null;
        }
    }

    private void writeHeadsFile(List<? extends ObjectId> list, String str) throws IOException {
        File file = new File(getDirectory(), str);
        if (list != null) {
            Throwable th = null;
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                try {
                    Iterator<? extends ObjectId> it = list.iterator();
                    while (it.hasNext()) {
                        it.next().copyTo(bufferedOutputStream);
                        bufferedOutputStream.write(10);
                    }
                    bufferedOutputStream.close();
                    return;
                } catch (Throwable th2) {
                    bufferedOutputStream.close();
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
        FileUtils.delete(file, 4);
    }

    public List<RebaseTodoLine> readRebaseTodo(String str, boolean z) throws IOException {
        return new RebaseTodoFile(this).readRebaseTodo(str, z);
    }

    public void writeRebaseTodoFile(String str, List<RebaseTodoLine> list, boolean z) throws IOException {
        new RebaseTodoFile(this).writeRebaseTodoFile(str, list, z);
    }

    public Set<String> getRemoteNames() {
        return getConfig().getSubsections("remote");
    }
}
