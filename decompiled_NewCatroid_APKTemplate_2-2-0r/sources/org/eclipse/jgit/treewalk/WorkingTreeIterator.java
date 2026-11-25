package org.eclipse.jgit.treewalk;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.NotLinkException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jgit.api.errors.FilterFailedException;
import org.eclipse.jgit.attributes.AttributesNode;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.ignore.IgnoreNode;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.Holder;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.Paths;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;
import org.eclipse.jgit.util.sha1.SHA1;

/* JADX INFO: loaded from: classes14.dex */
public abstract class WorkingTreeIterator extends AbstractTreeIterator {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$lib$CoreConfig$AutoCRLF = null;
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$treewalk$WorkingTreeIterator$MetadataDiff = null;
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int BUFFER_SIZE = 2048;
    private static final long MAXIMUM_FILE_SIZE_TO_READ_FULLY = 65536;
    private static final int MAX_EXCEPTION_TEXT_SIZE = 10240;
    private long canonLen;
    private Holder<String> cleanFilterCommandHolder;
    private byte[] contentId;
    private int contentIdFromPtr;
    private int contentIdOffset;
    private Entry[] entries;
    private int entryCnt;
    private Holder<CoreConfig.EolStreamType> eolStreamTypeHolder;
    private IgnoreNode ignoreNode;
    private int ptr;
    protected Repository repository;
    private final IteratorState state;
    private final InstantComparator timestampComparator;
    protected static final Entry[] EOF = new Entry[0];
    private static final byte[] digits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57};
    private static final byte[] hblob = Constants.encodedTypeString(3);
    private static final Comparator<Entry> ENTRY_CMP = new Comparator() { // from class: org.eclipse.jgit.treewalk.WorkingTreeIterator$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            WorkingTreeIterator.Entry entry = (WorkingTreeIterator.Entry) obj;
            WorkingTreeIterator.Entry entry2 = (WorkingTreeIterator.Entry) obj2;
            return Paths.compare(entry.encodedName, 0, entry.encodedNameLen, entry.getMode().getBits(), entry2.encodedName, 0, entry2.encodedNameLen, entry2.getMode().getBits());
        }
    };

    public enum MetadataDiff {
        EQUAL,
        DIFFER_BY_METADATA,
        SMUDGED,
        DIFFER_BY_TIMESTAMP;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static MetadataDiff[] valuesCustom() {
            MetadataDiff[] metadataDiffArrValuesCustom = values();
            int length = metadataDiffArrValuesCustom.length;
            MetadataDiff[] metadataDiffArr = new MetadataDiff[length];
            System.arraycopy(metadataDiffArrValuesCustom, 0, metadataDiffArr, 0, length);
            return metadataDiffArr;
        }
    }

    @Override // org.eclipse.jgit.treewalk.AbstractTreeIterator
    public boolean isWorkTree() {
        return true;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$lib$CoreConfig$AutoCRLF() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$lib$CoreConfig$AutoCRLF;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[CoreConfig.AutoCRLF.valuesCustom().length];
        try {
            iArr2[CoreConfig.AutoCRLF.FALSE.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[CoreConfig.AutoCRLF.INPUT.ordinal()] = 3;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[CoreConfig.AutoCRLF.TRUE.ordinal()] = 2;
        } catch (NoSuchFieldError unused3) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$lib$CoreConfig$AutoCRLF = iArr2;
        return iArr2;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$treewalk$WorkingTreeIterator$MetadataDiff() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$treewalk$WorkingTreeIterator$MetadataDiff;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[MetadataDiff.valuesCustom().length];
        try {
            iArr2[MetadataDiff.DIFFER_BY_METADATA.ordinal()] = 2;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[MetadataDiff.DIFFER_BY_TIMESTAMP.ordinal()] = 4;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[MetadataDiff.EQUAL.ordinal()] = 1;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[MetadataDiff.SMUDGED.ordinal()] = 3;
        } catch (NoSuchFieldError unused4) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$treewalk$WorkingTreeIterator$MetadataDiff = iArr2;
        return iArr2;
    }

    protected WorkingTreeIterator(WorkingTreeOptions workingTreeOptions) {
        this.canonLen = -1L;
        this.timestampComparator = new InstantComparator();
        this.state = new IteratorState(workingTreeOptions);
    }

    protected WorkingTreeIterator(String str, WorkingTreeOptions workingTreeOptions) {
        super(str);
        this.canonLen = -1L;
        this.timestampComparator = new InstantComparator();
        this.state = new IteratorState(workingTreeOptions);
    }

    protected WorkingTreeIterator(WorkingTreeIterator workingTreeIterator) {
        super(workingTreeIterator);
        this.canonLen = -1L;
        this.timestampComparator = new InstantComparator();
        this.state = workingTreeIterator.state;
        this.repository = workingTreeIterator.repository;
    }

    protected void initRootIterator(Repository repository) {
        this.repository = repository;
        IgnoreNode ignoreNode = this.ignoreNode;
        this.ignoreNode = new RootIgnoreNode(ignoreNode instanceof PerDirectoryIgnoreNode ? ((PerDirectoryIgnoreNode) ignoreNode).entry : null, repository);
    }

    public void setDirCacheIterator(TreeWalk treeWalk, int i) {
        this.state.walk = treeWalk;
        this.state.dirCacheTree = i;
    }

    protected DirCacheIterator getDirCacheIterator() {
        if (this.state.dirCacheTree < 0 || this.state.walk == null) {
            return null;
        }
        return (DirCacheIterator) this.state.walk.getTree(this.state.dirCacheTree, DirCacheIterator.class);
    }

    public void setWalkIgnoredDirectories(boolean z) {
        this.state.walkIgnored = z;
    }

    public boolean walksIgnoredDirectories() {
        return this.state.walkIgnored;
    }

    @Override // org.eclipse.jgit.treewalk.AbstractTreeIterator
    public boolean hasId() {
        return this.contentIdFromPtr == this.ptr || (this.mode & 61440) == 32768;
    }

    @Override // org.eclipse.jgit.treewalk.AbstractTreeIterator
    public byte[] idBuffer() {
        DirCacheEntry dirCacheEntry;
        if (this.contentIdFromPtr == this.ptr) {
            return this.contentId;
        }
        if (this.state.walk != null) {
            DirCacheIterator dirCacheIterator = (DirCacheIterator) this.state.walk.getTree(this.state.dirCacheTree, DirCacheIterator.class);
            if (dirCacheIterator != null && (dirCacheEntry = dirCacheIterator.getDirCacheEntry()) != null && compareMetadata(dirCacheEntry) == MetadataDiff.EQUAL && (dirCacheEntry.getFileMode().getBits() & 61440) != 57344) {
                this.contentIdOffset = dirCacheIterator.idOffset();
                this.contentIdFromPtr = this.ptr;
                byte[] bArrIdBuffer = dirCacheIterator.idBuffer();
                this.contentId = bArrIdBuffer;
                return bArrIdBuffer;
            }
            this.contentIdOffset = 0;
        }
        int i = this.mode & 61440;
        if (i == 32768 || i == 40960) {
            int i2 = this.ptr;
            this.contentIdFromPtr = i2;
            byte[] bArrIdBufferBlob = idBufferBlob(this.entries[i2]);
            this.contentId = bArrIdBufferBlob;
            return bArrIdBufferBlob;
        }
        if (i == 57344) {
            int i3 = this.ptr;
            this.contentIdFromPtr = i3;
            byte[] bArrIdSubmodule = idSubmodule(this.entries[i3]);
            this.contentId = bArrIdSubmodule;
            return bArrIdSubmodule;
        }
        return zeroid;
    }

    protected byte[] idSubmodule(Entry entry) {
        Repository repository = this.repository;
        if (repository == null) {
            return zeroid;
        }
        try {
            return idSubmodule(repository.getWorkTree(), entry);
        } catch (NoWorkTreeException unused) {
            return zeroid;
        }
    }

    protected byte[] idSubmodule(File file, Entry entry) {
        Throwable th = null;
        try {
            String name2 = entry.getName();
            Repository repository = this.repository;
            Repository submoduleRepository = SubmoduleWalk.getSubmoduleRepository(file, name2, repository != null ? repository.getFS() : FS.DETECTED);
            try {
                if (submoduleRepository == null) {
                    byte[] bArr = zeroid;
                    if (submoduleRepository != null) {
                        submoduleRepository.close();
                    }
                    return bArr;
                }
                ObjectId objectIdResolve = submoduleRepository.resolve("HEAD");
                if (objectIdResolve == null) {
                    byte[] bArr2 = zeroid;
                    if (submoduleRepository != null) {
                        submoduleRepository.close();
                    }
                    return bArr2;
                }
                byte[] bArr3 = new byte[20];
                objectIdResolve.copyRawTo(bArr3, 0);
                if (submoduleRepository != null) {
                    submoduleRepository.close();
                }
                return bArr3;
            } catch (Throwable th2) {
                if (submoduleRepository == null) {
                    throw th2;
                }
                submoduleRepository.close();
                throw th2;
            }
        } catch (Throwable th3) {
            if (0 == 0) {
                throw th3;
            }
            if (null != th3) {
                try {
                    th.addSuppressed(th3);
                } catch (IOException unused) {
                    return zeroid;
                }
            }
            throw null;
        }
    }

    private byte[] idBufferBlob(Entry entry) {
        try {
            InputStream inputStreamOpenInputStream = entry.openInputStream();
            if (inputStreamOpenInputStream == null) {
                return zeroid;
            }
            try {
                this.state.initializeReadBuffer();
                return computeHash(possiblyFilteredInputStream(entry, inputStreamOpenInputStream, entry.getLength(), TreeWalk.OperationType.CHECKIN_OP), this.canonLen);
            } finally {
                safeClose(inputStreamOpenInputStream);
            }
        } catch (IOException unused) {
            return zeroid;
        }
    }

    private InputStream possiblyFilteredInputStream(Entry entry, InputStream inputStream, long j) throws IOException {
        return possiblyFilteredInputStream(entry, inputStream, j, null);
    }

    private InputStream possiblyFilteredInputStream(Entry entry, InputStream inputStream, long j, TreeWalk.OperationType operationType) throws IOException {
        if (getCleanFilterCommand() == null && getEolStreamType(operationType) == CoreConfig.EolStreamType.DIRECT) {
            this.canonLen = j;
            return inputStream;
        }
        if (j <= 65536) {
            ByteBuffer wholeStream = IO.readWholeStream(inputStream, (int) j);
            ByteBuffer byteBufferFilterClean = filterClean(wholeStream.array(), wholeStream.limit(), operationType);
            this.canonLen = byteBufferFilterClean.limit();
            return new ByteArrayInputStream(byteBufferFilterClean.array(), 0, (int) this.canonLen);
        }
        if (getCleanFilterCommand() == null && isBinary(entry)) {
            this.canonLen = j;
            return inputStream;
        }
        InputStream inputStreamFilterClean = filterClean(entry.openInputStream(), operationType);
        try {
            this.canonLen = computeLength(inputStreamFilterClean);
            return filterClean(inputStream, operationType);
        } finally {
            safeClose(inputStreamFilterClean);
        }
    }

    private static void safeClose(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException unused) {
        }
    }

    private static boolean isBinary(Entry entry) throws IOException {
        InputStream inputStreamOpenInputStream = entry.openInputStream();
        try {
            return RawText.isBinary(inputStreamOpenInputStream);
        } finally {
            safeClose(inputStreamOpenInputStream);
        }
    }

    private ByteBuffer filterClean(byte[] bArr, int i, TreeWalk.OperationType operationType) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            return IO.readWholeStream(filterClean(byteArrayInputStream, operationType), i);
        } finally {
            safeClose(byteArrayInputStream);
        }
    }

    private InputStream filterClean(InputStream inputStream) throws IOException {
        return filterClean(inputStream, null);
    }

    private InputStream filterClean(InputStream inputStream, TreeWalk.OperationType operationType) throws IOException {
        InputStream inputStreamHandleAutoCRLF = handleAutoCRLF(inputStream, operationType);
        String cleanFilterCommand = getCleanFilterCommand();
        if (cleanFilterCommand == null) {
            return inputStreamHandleAutoCRLF;
        }
        if (FilterCommandRegistry.isRegistered(cleanFilterCommand)) {
            TemporaryBuffer.LocalFile localFile = new TemporaryBuffer.LocalFile(null);
            while (FilterCommandRegistry.createFilterCommand(cleanFilterCommand, this.repository, inputStreamHandleAutoCRLF, localFile).run() != -1) {
            }
            return localFile.openInputStreamWithAutoDestroy();
        }
        FS fs = this.repository.getFS();
        ProcessBuilder processBuilderRunInShell = fs.runInShell(cleanFilterCommand, new String[0]);
        processBuilderRunInShell.directory(this.repository.getWorkTree());
        processBuilderRunInShell.environment().put(Constants.GIT_DIR_KEY, this.repository.getDirectory().getAbsolutePath());
        try {
            FS.ExecutionResult executionResultExecute = fs.execute(processBuilderRunInShell, inputStreamHandleAutoCRLF);
            int rc = executionResultExecute.getRc();
            if (rc != 0) {
                throw new IOException(new FilterFailedException(rc, cleanFilterCommand, getEntryPathString(), executionResultExecute.getStdout().toByteArray(10240), executionResultExecute.getStderr().toString(10240)));
            }
            return executionResultExecute.getStdout().openInputStreamWithAutoDestroy();
        } catch (IOException | InterruptedException e) {
            throw new IOException(new FilterFailedException(e, cleanFilterCommand, getEntryPathString()));
        }
    }

    private InputStream handleAutoCRLF(InputStream inputStream, TreeWalk.OperationType operationType) throws IOException {
        return EolStreamTypeUtil.wrapInputStream(inputStream, getEolStreamType(operationType));
    }

    public WorkingTreeOptions getOptions() {
        return this.state.options;
    }

    public Repository getRepository() {
        return this.repository;
    }

    @Override // org.eclipse.jgit.treewalk.AbstractTreeIterator
    public int idOffset() {
        return this.contentIdOffset;
    }

    @Override // org.eclipse.jgit.treewalk.AbstractTreeIterator
    public void reset() {
        if (first()) {
            return;
        }
        this.ptr = 0;
        if (eof()) {
            return;
        }
        parseEntry();
    }

    @Override // org.eclipse.jgit.treewalk.AbstractTreeIterator
    public boolean first() {
        return this.ptr == 0;
    }

    @Override // org.eclipse.jgit.treewalk.AbstractTreeIterator
    public boolean eof() {
        return this.ptr == this.entryCnt;
    }

    @Override // org.eclipse.jgit.treewalk.AbstractTreeIterator
    public void next(int i) throws CorruptObjectException {
        this.ptr += i;
        if (eof()) {
            return;
        }
        parseEntry();
    }

    @Override // org.eclipse.jgit.treewalk.AbstractTreeIterator
    public void back(int i) throws CorruptObjectException {
        this.ptr -= i;
        parseEntry();
    }

    private void parseEntry() {
        Entry entry = this.entries[this.ptr];
        this.mode = entry.getMode().getBits();
        int i = entry.encodedNameLen;
        ensurePathCapacity(this.pathOffset + i, this.pathOffset);
        System.arraycopy(entry.encodedName, 0, this.path, this.pathOffset, i);
        this.pathLen = this.pathOffset + i;
        this.canonLen = -1L;
        this.cleanFilterCommandHolder = null;
        this.eolStreamTypeHolder = null;
    }

    public long getEntryLength() {
        return current().getLength();
    }

    public long getEntryContentLength() throws IOException {
        if (this.canonLen == -1) {
            if (getEntryLength() == 0) {
                this.canonLen = 0L;
            }
            InputStream inputStreamOpenInputStream = current().openInputStream();
            try {
                possiblyFilteredInputStream(current(), inputStreamOpenInputStream, current().getLength());
            } finally {
                safeClose(inputStreamOpenInputStream);
            }
        }
        return this.canonLen;
    }

    @Deprecated
    public long getEntryLastModified() {
        return current().getLastModified();
    }

    public Instant getEntryLastModifiedInstant() {
        return current().getLastModifiedInstant();
    }

    public InputStream openEntryStream() throws IOException {
        InputStream inputStreamOpenInputStream = current().openInputStream();
        return (getCleanFilterCommand() == null && getEolStreamType() == CoreConfig.EolStreamType.DIRECT) ? inputStreamOpenInputStream : filterClean(inputStreamOpenInputStream);
    }

    public boolean isEntryIgnored() throws IOException {
        return isEntryIgnored(this.pathLen);
    }

    protected boolean isEntryIgnored(int i) throws IOException {
        return isEntryIgnored(i, this.mode);
    }

    private boolean isEntryIgnored(int i, int i2) throws IOException {
        String strPathOf = TreeWalk.pathOf(this.path, this.pathOffset > 0 ? this.pathOffset - 1 : this.pathOffset, i);
        if (isDirectoryIgnored(getParentPath(strPathOf))) {
            return true;
        }
        IgnoreNode ignoreNode = getIgnoreNode();
        Boolean boolCheckIgnored = ignoreNode != null ? ignoreNode.checkIgnored(strPathOf, FileMode.TREE.equals(i2)) : null;
        if (boolCheckIgnored != null) {
            return boolCheckIgnored.booleanValue();
        }
        return (this.parent instanceof WorkingTreeIterator) && ((WorkingTreeIterator) this.parent).isEntryIgnored(i, i2);
    }

    private IgnoreNode getIgnoreNode() throws IOException {
        IgnoreNode ignoreNode = this.ignoreNode;
        if (ignoreNode instanceof PerDirectoryIgnoreNode) {
            this.ignoreNode = ((PerDirectoryIgnoreNode) ignoreNode).load();
        }
        return this.ignoreNode;
    }

    public AttributesNode getEntryAttributesNode() throws IOException {
        if (this.attributesNode instanceof PerDirectoryAttributesNode) {
            this.attributesNode = ((PerDirectoryAttributesNode) this.attributesNode).load();
        }
        return this.attributesNode;
    }

    protected void init(Entry[] entryArr) {
        Entry[] entryArr2;
        this.entries = entryArr;
        CharsetEncoder charsetEncoder = this.state.nameEncoder;
        int i = 0;
        int i2 = 0;
        while (true) {
            entryArr2 = this.entries;
            if (i >= entryArr2.length) {
                break;
            }
            Entry entry = entryArr2[i];
            if (entry != null) {
                String name2 = entry.getName();
                if (!".".equals(name2) && !"..".equals(name2) && !".git".equals(name2)) {
                    if (".gitignore".equals(name2)) {
                        this.ignoreNode = new PerDirectoryIgnoreNode(String.valueOf(TreeWalk.pathOf(this.path, 0, this.pathOffset)) + ".gitignore", entry);
                    }
                    if (Constants.DOT_GIT_ATTRIBUTES.equals(name2)) {
                        this.attributesNode = new PerDirectoryAttributesNode(entry);
                    }
                    if (i != i2) {
                        this.entries[i2] = entry;
                    }
                    entry.encodeName(charsetEncoder);
                    i2++;
                }
            }
            i++;
        }
        this.entryCnt = i2;
        Arrays.sort(entryArr2, 0, i2, ENTRY_CMP);
        this.contentIdFromPtr = -1;
        this.ptr = 0;
        if (!eof()) {
            parseEntry();
        } else if (this.pathLen == 0) {
            this.pathLen = this.pathOffset;
        }
    }

    protected Entry current() {
        return this.entries[this.ptr];
    }

    public boolean isModeDifferent(int i) {
        int entryRawMode = getEntryRawMode() ^ i;
        if (entryRawMode == 0) {
            return false;
        }
        if (getOptions().getSymLinks() == CoreConfig.SymLinks.FALSE && FileMode.SYMLINK.equals(i)) {
            return false;
        }
        if (!this.state.options.isFileMode()) {
            entryRawMode &= ~FileMode.EXECUTABLE_FILE.getBits();
        }
        return entryRawMode != 0;
    }

    public MetadataDiff compareMetadata(DirCacheEntry dirCacheEntry) {
        if (dirCacheEntry.isAssumeValid()) {
            return MetadataDiff.EQUAL;
        }
        if (dirCacheEntry.isUpdateNeeded()) {
            return MetadataDiff.DIFFER_BY_METADATA;
        }
        if (isModeDifferent(dirCacheEntry.getRawMode())) {
            return MetadataDiff.DIFFER_BY_METADATA;
        }
        int i = this.mode & 61440;
        if (i == 16384 || i == 57344) {
            return MetadataDiff.EQUAL;
        }
        if (!dirCacheEntry.isSmudged() && dirCacheEntry.getLength() != ((int) getEntryLength())) {
            return MetadataDiff.DIFFER_BY_METADATA;
        }
        if (this.timestampComparator.compare(dirCacheEntry.getLastModifiedInstant(), getEntryLastModifiedInstant(), getOptions().getCheckStat() == CoreConfig.CheckStat.MINIMAL) != 0) {
            return MetadataDiff.DIFFER_BY_TIMESTAMP;
        }
        if (dirCacheEntry.isSmudged()) {
            return MetadataDiff.SMUDGED;
        }
        return MetadataDiff.EQUAL;
    }

    public boolean isModified(DirCacheEntry dirCacheEntry, boolean z, ObjectReader objectReader) throws IOException {
        if (dirCacheEntry == null) {
            return !FileMode.MISSING.equals(getEntryFileMode());
        }
        MetadataDiff metadataDiffCompareMetadata = compareMetadata(dirCacheEntry);
        int i = $SWITCH_TABLE$org$eclipse$jgit$treewalk$WorkingTreeIterator$MetadataDiff()[metadataDiffCompareMetadata.ordinal()];
        if (i == 1) {
            if (this.mode == FileMode.SYMLINK.getBits()) {
                return contentCheck(dirCacheEntry, objectReader);
            }
            return false;
        }
        if (i != 2) {
            if (i == 3) {
                return contentCheck(dirCacheEntry, objectReader);
            }
            if (i != 4) {
                throw new IllegalStateException(MessageFormat.format(JGitText.get().unexpectedCompareResult, metadataDiffCompareMetadata.name()));
            }
            if (z) {
                return contentCheck(dirCacheEntry, objectReader);
            }
            return true;
        }
        if (this.mode == FileMode.TREE.getBits() && dirCacheEntry.getFileMode().equals(FileMode.GITLINK)) {
            byte[] bArrIdBuffer = idBuffer();
            int iIdOffset = idOffset();
            if (dirCacheEntry.getObjectId().compareTo(bArrIdBuffer, iIdOffset) == 0) {
                return true;
            }
            if (ObjectId.zeroId().compareTo(bArrIdBuffer, iIdOffset) == 0) {
                return FileUtils.hasFiles(this.repository.getWorkTree().toPath().resolve(dirCacheEntry.getPathString()));
            }
            return false;
        }
        if (this.mode == FileMode.SYMLINK.getBits()) {
            return contentCheck(dirCacheEntry, objectReader);
        }
        return true;
    }

    public FileMode getIndexFileMode(DirCacheIterator dirCacheIterator) {
        FileMode entryFileMode = getEntryFileMode();
        if (dirCacheIterator == null) {
            return entryFileMode;
        }
        FileMode entryFileMode2 = dirCacheIterator.getEntryFileMode();
        if (getOptions().isFileMode() && entryFileMode2 != FileMode.GITLINK && entryFileMode2 != FileMode.TREE) {
            return entryFileMode;
        }
        if (!getOptions().isFileMode()) {
            if (FileMode.REGULAR_FILE == entryFileMode && FileMode.EXECUTABLE_FILE == entryFileMode2) {
                return entryFileMode2;
            }
            if (FileMode.EXECUTABLE_FILE == entryFileMode && FileMode.REGULAR_FILE == entryFileMode2) {
                return entryFileMode2;
            }
        }
        if (FileMode.GITLINK == entryFileMode2 && FileMode.TREE == entryFileMode && !getOptions().isDirNoGitLinks()) {
            return entryFileMode2;
        }
        return (FileMode.TREE == entryFileMode2 && FileMode.GITLINK == entryFileMode) ? entryFileMode2 : entryFileMode;
    }

    private boolean contentCheck(DirCacheEntry dirCacheEntry, ObjectReader objectReader) throws IOException {
        if (getEntryObjectId().equals((AnyObjectId) dirCacheEntry.getObjectId())) {
            dirCacheEntry.setLength((int) getEntryLength());
            return false;
        }
        if (this.mode == FileMode.SYMLINK.getBits()) {
            return !new File(readSymlinkTarget(current())).equals(new File(readContentAsNormalizedString(dirCacheEntry, objectReader)));
        }
        return true;
    }

    private static String readContentAsNormalizedString(DirCacheEntry dirCacheEntry, ObjectReader objectReader) throws IOException {
        return FS.detect().normalize(RawParseUtils.decode(objectReader.open(dirCacheEntry.getObjectId()).getCachedBytes()));
    }

    protected String readSymlinkTarget(Entry entry) throws IOException {
        if (!entry.getMode().equals(FileMode.SYMLINK)) {
            throw new NotLinkException(entry.getName());
        }
        byte[] bArr = new byte[(int) entry.getLength()];
        Throwable th = null;
        try {
            InputStream inputStreamOpenInputStream = entry.openInputStream();
            try {
                return FS.detect().normalize(RawParseUtils.decode(bArr, 0, IO.readFully(inputStreamOpenInputStream, bArr, 0)));
            } finally {
                if (inputStreamOpenInputStream != null) {
                    inputStreamOpenInputStream.close();
                }
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

    private static long computeLength(InputStream inputStream) throws IOException {
        long j = 0;
        while (true) {
            long jSkip = inputStream.skip(1048576L);
            if (jSkip <= 0) {
                return j;
            }
            j += jSkip;
        }
    }

    private byte[] computeHash(InputStream inputStream, long j) throws IOException {
        long j2;
        SHA1 sha1NewInstance = SHA1.newInstance();
        byte[] bArr = this.state.contentReadBuffer;
        sha1NewInstance.update(hblob);
        sha1NewInstance.update((byte) 32);
        if (j == 0) {
            sha1NewInstance.update((byte) 48);
            j2 = j;
        } else {
            int length = bArr.length;
            j2 = j;
            int i = length;
            do {
                i--;
                bArr[i] = digits[(int) (j2 % 10)];
                j2 /= 10;
            } while (j2 > 0);
            sha1NewInstance.update(bArr, i, length - i);
        }
        sha1NewInstance.update((byte) 0);
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 <= 0) {
                break;
            }
            sha1NewInstance.update(bArr, 0, i2);
            j2 += (long) i2;
        }
        if (j2 != j) {
            return zeroid;
        }
        return sha1NewInstance.digest();
    }

    public static abstract class Entry {
        byte[] encodedName;
        int encodedNameLen;

        @Deprecated
        public abstract long getLastModified();

        public abstract Instant getLastModifiedInstant();

        public abstract long getLength();

        public abstract FileMode getMode();

        public abstract String getName();

        public abstract InputStream openInputStream() throws IOException;

        void encodeName(CharsetEncoder charsetEncoder) {
            try {
                ByteBuffer byteBufferEncode = charsetEncoder.encode(CharBuffer.wrap(getName()));
                this.encodedNameLen = byteBufferEncode.limit();
                if (byteBufferEncode.hasArray() && byteBufferEncode.arrayOffset() == 0) {
                    this.encodedName = byteBufferEncode.array();
                    return;
                }
                byte[] bArr = new byte[this.encodedNameLen];
                this.encodedName = bArr;
                byteBufferEncode.get(bArr);
            } catch (CharacterCodingException e) {
                throw new RuntimeException(MessageFormat.format(JGitText.get().unencodeableFile, getName()), e);
            }
        }

        public String toString() {
            return String.valueOf(getMode().toString()) + " " + getName();
        }
    }

    private static class PerDirectoryIgnoreNode extends IgnoreNode {
        protected final Entry entry;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f666name;

        PerDirectoryIgnoreNode(String str, Entry entry) {
            super(Collections.emptyList());
            this.f666name = str;
            this.entry = entry;
        }

        IgnoreNode load() throws IOException {
            IgnoreNode ignoreNode = new IgnoreNode();
            Throwable th = null;
            try {
                InputStream inputStreamOpenInputStream = this.entry.openInputStream();
                try {
                    ignoreNode.parse(this.f666name, inputStreamOpenInputStream);
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                    if (ignoreNode.getRules().isEmpty()) {
                        return null;
                    }
                    return ignoreNode;
                } catch (Throwable th2) {
                    if (inputStreamOpenInputStream == null) {
                        throw th2;
                    }
                    inputStreamOpenInputStream.close();
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
    }

    private static class RootIgnoreNode extends PerDirectoryIgnoreNode {
        final Repository repository;

        RootIgnoreNode(Entry entry, Repository repository) {
            super(entry != null ? entry.getName() : null, entry);
            this.repository = repository;
        }

        @Override // org.eclipse.jgit.treewalk.WorkingTreeIterator.PerDirectoryIgnoreNode
        IgnoreNode load() throws IOException {
            IgnoreNode ignoreNode;
            if (this.entry == null || (ignoreNode = super.load()) == null) {
                ignoreNode = new IgnoreNode();
            }
            FS fs = this.repository.getFS();
            Path path = this.repository.getConfig().getPath(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_EXCLUDESFILE, fs, null, null);
            if (path != null) {
                loadRulesFromFile(ignoreNode, path.toFile());
            }
            loadRulesFromFile(ignoreNode, fs.resolve(this.repository.getDirectory(), Constants.INFO_EXCLUDE));
            if (ignoreNode.getRules().isEmpty()) {
                return null;
            }
            return ignoreNode;
        }

        private static void loadRulesFromFile(IgnoreNode ignoreNode, File file) throws IOException {
            if (FS.DETECTED.exists(file)) {
                Throwable th = null;
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        ignoreNode.parse(file.getAbsolutePath(), fileInputStream);
                    } finally {
                        fileInputStream.close();
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
        }
    }

    private static class PerDirectoryAttributesNode extends AttributesNode {
        final Entry entry;

        PerDirectoryAttributesNode(Entry entry) {
            super(Collections.emptyList());
            this.entry = entry;
        }

        AttributesNode load() throws IOException {
            AttributesNode attributesNode = new AttributesNode();
            Throwable th = null;
            try {
                InputStream inputStreamOpenInputStream = this.entry.openInputStream();
                try {
                    attributesNode.parse(inputStreamOpenInputStream);
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                    if (attributesNode.getRules().isEmpty()) {
                        return null;
                    }
                    return attributesNode;
                } catch (Throwable th2) {
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
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
    }

    private static final class IteratorState {
        byte[] contentReadBuffer;
        final WorkingTreeOptions options;
        TreeWalk walk;
        int dirCacheTree = -1;
        boolean walkIgnored = false;
        final Map<String, Boolean> directoryToIgnored = new HashMap();
        final CharsetEncoder nameEncoder = StandardCharsets.UTF_8.newEncoder();

        IteratorState(WorkingTreeOptions workingTreeOptions) {
            this.options = workingTreeOptions;
        }

        void initializeReadBuffer() {
            if (this.contentReadBuffer == null) {
                this.contentReadBuffer = new byte[2048];
            }
        }
    }

    public String getCleanFilterCommand() throws IOException {
        if (this.cleanFilterCommandHolder == null) {
            this.cleanFilterCommandHolder = new Holder<>(this.state.walk != null ? this.state.walk.getFilterCommand(Constants.ATTR_FILTER_TYPE_CLEAN) : null);
        }
        return this.cleanFilterCommandHolder.get();
    }

    public CoreConfig.EolStreamType getEolStreamType() throws IOException {
        return getEolStreamType(null);
    }

    private CoreConfig.EolStreamType getEolStreamType(TreeWalk.OperationType operationType) throws IOException {
        CoreConfig.EolStreamType eolStreamType;
        if (this.eolStreamTypeHolder == null) {
            if (this.state.walk != null) {
                eolStreamType = this.state.walk.getEolStreamType(operationType);
                if (operationType == null) {
                    operationType = this.state.walk.getOperationType();
                }
                if (TreeWalk.OperationType.CHECKIN_OP.equals(operationType) && CoreConfig.EolStreamType.AUTO_LF.equals(eolStreamType) && hasCrLfInIndex(getDirCacheIterator())) {
                    eolStreamType = CoreConfig.EolStreamType.DIRECT;
                }
            } else {
                int i = $SWITCH_TABLE$org$eclipse$jgit$lib$CoreConfig$AutoCRLF()[getOptions().getAutoCRLF().ordinal()];
                if (i == 1) {
                    eolStreamType = CoreConfig.EolStreamType.DIRECT;
                } else {
                    eolStreamType = (i == 2 || i == 3) ? CoreConfig.EolStreamType.AUTO_LF : null;
                }
            }
            this.eolStreamTypeHolder = new Holder<>(eolStreamType);
        }
        return this.eolStreamTypeHolder.get();
    }

    private boolean hasCrLfInIndex(DirCacheIterator dirCacheIterator) {
        ObjectId objectId;
        if (dirCacheIterator == null) {
            return false;
        }
        DirCacheEntry dirCacheEntry = dirCacheIterator.getDirCacheEntry();
        if ((dirCacheEntry.getRawMode() & 61440) == 32768) {
            ObjectId objectId2 = dirCacheEntry.getObjectId();
            Throwable th = null;
            if (dirCacheEntry.getStage() > 0 && dirCacheEntry.getStage() != 2) {
                byte[] rawPath = dirCacheEntry.getRawPath();
                int i = 0;
                while (true) {
                    if (!dirCacheIterator.eof()) {
                        dirCacheIterator.next(1);
                        i++;
                        DirCacheEntry dirCacheEntry2 = dirCacheIterator.getDirCacheEntry();
                        if (dirCacheEntry2 != null && Arrays.equals(rawPath, dirCacheEntry2.getRawPath())) {
                            if (dirCacheEntry2.getStage() == 2) {
                                if ((dirCacheEntry2.getRawMode() & 61440) == 32768) {
                                    objectId = dirCacheEntry2.getObjectId();
                                    break;
                                }
                            }
                        }
                    }
                    objectId = null;
                    break;
                }
                dirCacheIterator.back(i);
                objectId2 = objectId;
            }
            if (objectId2 != null) {
                try {
                    ObjectReader objectReaderNewObjectReader = this.repository.newObjectReader();
                    try {
                        ObjectLoader objectLoaderOpen = objectReaderNewObjectReader.open(objectId2, 3);
                        try {
                            try {
                                boolean zIsCrLfText = RawText.isCrLfText(objectLoaderOpen.getCachedBytes());
                                if (objectReaderNewObjectReader != null) {
                                    objectReaderNewObjectReader.close();
                                }
                                return zIsCrLfText;
                            } catch (Throwable th2) {
                                if (0 == 0) {
                                    throw th2;
                                }
                                if (null != th2) {
                                    th.addSuppressed(th2);
                                }
                                throw null;
                            }
                        } catch (LargeObjectException unused) {
                            ObjectStream objectStreamOpenStream = objectLoaderOpen.openStream();
                            try {
                                boolean zIsCrLfText2 = RawText.isCrLfText(objectStreamOpenStream);
                                if (objectStreamOpenStream != null) {
                                    objectStreamOpenStream.close();
                                }
                                if (objectReaderNewObjectReader != null) {
                                    objectReaderNewObjectReader.close();
                                }
                                return zIsCrLfText2;
                            } catch (Throwable th3) {
                                if (objectStreamOpenStream != null) {
                                    objectStreamOpenStream.close();
                                }
                                throw th3;
                            }
                        }
                    } catch (Throwable th4) {
                        if (objectReaderNewObjectReader == null) {
                            throw th4;
                        }
                        objectReaderNewObjectReader.close();
                        throw th4;
                    }
                } catch (Throwable th5) {
                    if (0 == 0) {
                        throw th5;
                    }
                    if (null != th5) {
                        try {
                            th.addSuppressed(th5);
                        } catch (IOException unused2) {
                        }
                    }
                    throw null;
                }
            }
        }
        return false;
    }

    private boolean isDirectoryIgnored(String str) throws IOException {
        return isDirectoryIgnored(str, concatPath(TreeWalk.pathOf(this.path, 0, this.pathOffset > 0 ? this.pathOffset - 1 : this.pathOffset), str));
    }

    private boolean isDirectoryIgnored(String str, String str2) throws IOException {
        Boolean bool = this.state.directoryToIgnored.get(str2);
        if (bool != null) {
            return bool.booleanValue();
        }
        String parentPath = getParentPath(str);
        if (parentPath != null && isDirectoryIgnored(parentPath)) {
            this.state.directoryToIgnored.put(str2, Boolean.TRUE);
            return true;
        }
        IgnoreNode ignoreNode = getIgnoreNode();
        for (String parentPath2 = str; ignoreNode != null && !"".equals(parentPath2); parentPath2 = getParentPath(parentPath2)) {
            Boolean boolCheckIgnored = ignoreNode.checkIgnored(parentPath2, true);
            if (boolCheckIgnored != null) {
                this.state.directoryToIgnored.put(str2, boolCheckIgnored);
                return boolCheckIgnored.booleanValue();
            }
        }
        if (!(this.parent instanceof WorkingTreeIterator)) {
            this.state.directoryToIgnored.put(str2, Boolean.FALSE);
            return false;
        }
        WorkingTreeIterator workingTreeIterator = (WorkingTreeIterator) this.parent;
        return workingTreeIterator.isDirectoryIgnored(concatPath(TreeWalk.pathOf(this.path, workingTreeIterator.pathOffset, this.pathOffset - 1), str), str2);
    }

    private static String getParentPath(String str) {
        int iLastIndexOf = str.lastIndexOf(47, str.length() - 2);
        if (iLastIndexOf > 0) {
            return str.substring(str.charAt(0) == '/' ? 1 : 0, iLastIndexOf);
        }
        if (str.length() > 0) {
            return "";
        }
        return null;
    }

    private static String concatPath(String str, String str2) {
        return String.valueOf(str) + ((str.length() <= 0 || str2.length() <= 0) ? "" : "/") + str2;
    }
}
