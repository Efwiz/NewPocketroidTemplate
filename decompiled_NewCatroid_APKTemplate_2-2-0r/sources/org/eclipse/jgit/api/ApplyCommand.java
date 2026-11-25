package org.eclipse.jgit.api;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.InflaterInputStream;
import org.eclipse.jgit.api.errors.FilterFailedException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.PatchApplyException;
import org.eclipse.jgit.api.errors.PatchFormatException;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.BinaryHunk;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.patch.Patch;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.NotIgnoredFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.io.BinaryDeltaInputStream;
import org.eclipse.jgit.util.io.BinaryHunkInputStream;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;
import org.eclipse.jgit.util.sha1.SHA1;

/* JADX INFO: loaded from: classes13.dex */
public class ApplyCommand extends GitCommand<ApplyResult> {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$diff$DiffEntry$ChangeType;
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$patch$BinaryHunk$Type;
    private InputStream in;

    /* JADX INFO: Access modifiers changed from: private */
    interface StreamSupplier {
        InputStream load() throws IOException;
    }

    static /* synthetic */ InputStream lambda$2(InputStream inputStream) throws IOException {
        return inputStream;
    }

    static /* synthetic */ InputStream lambda$3(SHA1InputStream sHA1InputStream) throws IOException {
        return sHA1InputStream;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$diff$DiffEntry$ChangeType() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$diff$DiffEntry$ChangeType;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[DiffEntry.ChangeType.valuesCustom().length];
        try {
            iArr2[DiffEntry.ChangeType.ADD.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[DiffEntry.ChangeType.COPY.ordinal()] = 5;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[DiffEntry.ChangeType.DELETE.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[DiffEntry.ChangeType.MODIFY.ordinal()] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[DiffEntry.ChangeType.RENAME.ordinal()] = 4;
        } catch (NoSuchFieldError unused5) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$diff$DiffEntry$ChangeType = iArr2;
        return iArr2;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$patch$BinaryHunk$Type() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$patch$BinaryHunk$Type;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[BinaryHunk.Type.valuesCustom().length];
        try {
            iArr2[BinaryHunk.Type.DELTA_DEFLATED.ordinal()] = 2;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[BinaryHunk.Type.LITERAL_DEFLATED.ordinal()] = 1;
        } catch (NoSuchFieldError unused2) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$patch$BinaryHunk$Type = iArr2;
        return iArr2;
    }

    ApplyCommand(Repository repository) {
        super(repository);
    }

    public ApplyCommand setPatch(InputStream inputStream) {
        checkCallable();
        this.in = inputStream;
        return this;
    }

    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public ApplyResult call() throws GitAPIException {
        File file;
        File file2;
        checkCallable();
        setCallable(false);
        ApplyResult applyResult = new ApplyResult();
        try {
            Patch patch = new Patch();
            try {
                patch.parse(this.in);
                this.in.close();
                if (!patch.getErrors().isEmpty()) {
                    throw new PatchFormatException(patch.getErrors());
                }
                Repository repository = getRepository();
                DirCache dirCache = repository.readDirCache();
                for (FileHeader fileHeader : patch.getFiles()) {
                    int i = $SWITCH_TABLE$org$eclipse$jgit$diff$DiffEntry$ChangeType()[fileHeader.getChangeType().ordinal()];
                    if (i == 1) {
                        file = getFile(fileHeader.getNewPath(), true);
                        apply(repository, fileHeader.getNewPath(), dirCache, file, fileHeader);
                    } else if (i == 2) {
                        file = getFile(fileHeader.getOldPath(), false);
                        apply(repository, fileHeader.getOldPath(), dirCache, file, fileHeader);
                    } else {
                        if (i == 3) {
                            file2 = getFile(fileHeader.getOldPath(), false);
                            if (!file2.delete()) {
                                throw new PatchApplyException(MessageFormat.format(JGitText.get().cannotDeleteFile, file2));
                            }
                        } else if (i == 4) {
                            file = getFile(fileHeader.getOldPath(), false);
                            File file3 = getFile(fileHeader.getNewPath(), false);
                            try {
                                FileUtils.mkdirs(file3.getParentFile(), true);
                                FileUtils.rename(file, file3, StandardCopyOption.ATOMIC_MOVE);
                                apply(repository, fileHeader.getOldPath(), dirCache, file3, fileHeader);
                            } catch (IOException e) {
                                throw new PatchApplyException(MessageFormat.format(JGitText.get().renameFileFailed, file, file3), e);
                            }
                        } else if (i != 5) {
                            file2 = null;
                        } else {
                            file = getFile(fileHeader.getOldPath(), false);
                            File file4 = getFile(fileHeader.getNewPath(), false);
                            FileUtils.mkdirs(file4.getParentFile(), true);
                            Files.copy(file.toPath(), file4.toPath(), new CopyOption[0]);
                            apply(repository, fileHeader.getOldPath(), dirCache, file4, fileHeader);
                        }
                        applyResult.addUpdatedFile(file2);
                    }
                    file2 = file;
                    applyResult.addUpdatedFile(file2);
                }
                return applyResult;
            } catch (Throwable th) {
                this.in.close();
                throw th;
            }
        } catch (IOException e2) {
            throw new PatchApplyException(MessageFormat.format(JGitText.get().patchApplyException, e2.getMessage()), e2);
        }
    }

    private File getFile(String str, boolean z) throws PatchApplyException {
        File file = new File(getRepository().getWorkTree(), str);
        if (z) {
            try {
                FileUtils.mkdirs(file.getParentFile(), true);
                FileUtils.createNewFile(file);
            } catch (IOException e) {
                throw new PatchApplyException(MessageFormat.format(JGitText.get().createNewFileFailed, file), e);
            }
        }
        return file;
    }

    private void apply(Repository repository, String str, DirCache dirCache, final File file, FileHeader fileHeader) throws PatchApplyException, IOException {
        DirCacheCheckout.CheckoutMetadata checkoutMetadata;
        RawText rawText;
        CoreConfig.EolStreamType eolStreamType;
        if (FileHeader.PatchType.BINARY.equals(fileHeader.getPatchType())) {
            return;
        }
        boolean zNeedsCrLfConversion = needsCrLfConversion(file, fileHeader);
        Throwable th = null;
        try {
            TreeWalk treeWalk = new TreeWalk(repository);
            try {
                treeWalk.setOperationType(TreeWalk.OperationType.CHECKIN_OP);
                FileTreeIterator fileTreeIterator = new FileTreeIterator(repository);
                int iAddTree = treeWalk.addTree(fileTreeIterator);
                fileTreeIterator.setDirCacheIterator(treeWalk, treeWalk.addTree(new DirCacheIterator(dirCache)));
                treeWalk.setFilter(AndTreeFilter.create(PathFilterGroup.createFromStrings(str), new NotIgnoredFilter(iAddTree)));
                treeWalk.setRecursive(true);
                if (treeWalk.next()) {
                    if (zNeedsCrLfConversion) {
                        eolStreamType = CoreConfig.EolStreamType.TEXT_CRLF;
                    } else {
                        eolStreamType = treeWalk.getEolStreamType(TreeWalk.OperationType.CHECKOUT_OP);
                    }
                    DirCacheCheckout.CheckoutMetadata checkoutMetadata2 = new DirCacheCheckout.CheckoutMetadata(eolStreamType, treeWalk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE));
                    final FileTreeIterator fileTreeIterator2 = (FileTreeIterator) treeWalk.getTree(iAddTree, FileTreeIterator.class);
                    if (fileTreeIterator2 != null) {
                        if (FileHeader.PatchType.GIT_BINARY.equals(fileHeader.getPatchType())) {
                            fileTreeIterator2.getClass();
                            applyBinary(repository, str, file, fileHeader, new StreamSupplier() { // from class: org.eclipse.jgit.api.ApplyCommand$$ExternalSyntheticLambda3
                                @Override // org.eclipse.jgit.api.ApplyCommand.StreamSupplier
                                public final InputStream load() {
                                    return fileTreeIterator2.openEntryStream();
                                }
                            }, fileTreeIterator2.getEntryObjectId(), checkoutMetadata2);
                        } else {
                            try {
                                InputStream inputStreamFilterClean = filterClean(repository, str, new FileInputStream(file), zNeedsCrLfConversion, treeWalk.getFilterCommand(Constants.ATTR_FILTER_TYPE_CLEAN));
                                try {
                                    RawText rawText2 = new RawText(IO.readWholeStream(inputStreamFilterClean, 0).array());
                                    if (inputStreamFilterClean != null) {
                                        inputStreamFilterClean.close();
                                    }
                                    applyText(repository, str, rawText2, file, fileHeader, checkoutMetadata2);
                                } catch (Throwable th2) {
                                    if (inputStreamFilterClean == null) {
                                        throw th2;
                                    }
                                    inputStreamFilterClean.close();
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
                        }
                        treeWalk.close();
                        return;
                    }
                }
                treeWalk.close();
                if (FileHeader.PatchType.GIT_BINARY.equals(fileHeader.getPatchType())) {
                    applyBinary(repository, str, file, fileHeader, new StreamSupplier() { // from class: org.eclipse.jgit.api.ApplyCommand$$ExternalSyntheticLambda4
                        @Override // org.eclipse.jgit.api.ApplyCommand.StreamSupplier
                        public final InputStream load() {
                            return ApplyCommand.lambda$1(file);
                        }
                    }, null, new DirCacheCheckout.CheckoutMetadata(CoreConfig.EolStreamType.DIRECT, null));
                    return;
                }
                if (zNeedsCrLfConversion) {
                    try {
                        InputStream inputStreamWrapInputStream = EolStreamTypeUtil.wrapInputStream(new FileInputStream(file), CoreConfig.EolStreamType.TEXT_LF);
                        try {
                            RawText rawText3 = new RawText(IO.readWholeStream(inputStreamWrapInputStream, 0).array());
                            if (inputStreamWrapInputStream != null) {
                                inputStreamWrapInputStream.close();
                            }
                            checkoutMetadata = new DirCacheCheckout.CheckoutMetadata(CoreConfig.EolStreamType.TEXT_CRLF, null);
                            rawText = rawText3;
                        } catch (Throwable th4) {
                            if (inputStreamWrapInputStream == null) {
                                throw th4;
                            }
                            inputStreamWrapInputStream.close();
                            throw th4;
                        }
                    } catch (Throwable th5) {
                        if (0 == 0) {
                            throw th5;
                        }
                        if (null == th5) {
                            throw null;
                        }
                        th.addSuppressed(th5);
                        throw null;
                    }
                } else {
                    rawText = new RawText(file);
                    checkoutMetadata = new DirCacheCheckout.CheckoutMetadata(CoreConfig.EolStreamType.DIRECT, null);
                }
                applyText(repository, str, rawText, file, fileHeader, checkoutMetadata);
            } catch (Throwable th6) {
                treeWalk.close();
                throw th6;
            }
        } catch (Throwable th7) {
            if (0 == 0) {
                throw th7;
            }
            if (null == th7) {
                throw null;
            }
            th.addSuppressed(th7);
        }
    }

    static /* synthetic */ InputStream lambda$1(File file) throws IOException {
        return new FileInputStream(file);
    }

    private boolean needsCrLfConversion(File file, FileHeader fileHeader) throws IOException {
        if (FileHeader.PatchType.GIT_BINARY.equals(fileHeader.getPatchType()) || hasCrLf(fileHeader)) {
            return false;
        }
        Throwable th = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                return RawText.isCrLfText(fileInputStream);
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

    private static boolean hasCrLf(FileHeader fileHeader) {
        char c;
        if (FileHeader.PatchType.GIT_BINARY.equals(fileHeader.getPatchType())) {
            return false;
        }
        for (HunkHeader hunkHeader : fileHeader.getHunks()) {
            byte[] buffer = hunkHeader.getBuffer();
            int endOffset = hunkHeader.getEndOffset();
            int startOffset = hunkHeader.getStartOffset();
            while (startOffset < endOffset) {
                int iNextLF = RawParseUtils.nextLF(buffer, startOffset);
                if (iNextLF > endOffset) {
                    iNextLF = endOffset;
                }
                if (iNextLF <= startOffset) {
                    break;
                }
                if (iNextLF - startOffset > 1 && (((c = (char) (buffer[startOffset] & 255)) == ' ' || c == '-') && buffer[iNextLF - 2] == 13)) {
                    return true;
                }
                startOffset = iNextLF;
            }
        }
        return false;
    }

    private InputStream filterClean(Repository repository, String str, InputStream inputStream, boolean z, String str2) throws IOException {
        if (z) {
            inputStream = EolStreamTypeUtil.wrapInputStream(inputStream, CoreConfig.EolStreamType.TEXT_LF);
        }
        if (StringUtils.isEmptyOrNull(str2)) {
            return inputStream;
        }
        if (FilterCommandRegistry.isRegistered(str2)) {
            TemporaryBuffer.LocalFile localFile = new TemporaryBuffer.LocalFile(null);
            while (FilterCommandRegistry.createFilterCommand(str2, repository, inputStream, localFile).run() != -1) {
            }
            return localFile.openInputStreamWithAutoDestroy();
        }
        FS fs = repository.getFS();
        ProcessBuilder processBuilderRunInShell = fs.runInShell(str2, new String[0]);
        processBuilderRunInShell.directory(repository.getWorkTree());
        processBuilderRunInShell.environment().put(Constants.GIT_DIR_KEY, repository.getDirectory().getAbsolutePath());
        try {
            FS.ExecutionResult executionResultExecute = fs.execute(processBuilderRunInShell, this.in);
            int rc = executionResultExecute.getRc();
            if (rc != 0) {
                throw new IOException(new FilterFailedException(rc, str2, str, executionResultExecute.getStdout().toByteArray(4096), RawParseUtils.decode(executionResultExecute.getStderr().toByteArray(4096))));
            }
            return executionResultExecute.getStdout().openInputStreamWithAutoDestroy();
        } catch (IOException | InterruptedException e) {
            throw new IOException(new FilterFailedException(e, str2, str));
        }
    }

    private static class StreamLoader extends ObjectLoader {
        private StreamSupplier data;
        private long size;

        @Override // org.eclipse.jgit.lib.ObjectLoader
        public int getType() {
            return 3;
        }

        @Override // org.eclipse.jgit.lib.ObjectLoader
        public boolean isLarge() {
            return true;
        }

        StreamLoader(StreamSupplier streamSupplier, long j) {
            this.data = streamSupplier;
            this.size = j;
        }

        @Override // org.eclipse.jgit.lib.ObjectLoader
        public long getSize() {
            return this.size;
        }

        @Override // org.eclipse.jgit.lib.ObjectLoader
        public byte[] getCachedBytes() throws LargeObjectException {
            throw new LargeObjectException();
        }

        @Override // org.eclipse.jgit.lib.ObjectLoader
        public ObjectStream openStream() throws IOException {
            return new ObjectStream.Filter(getType(), getSize(), new BufferedInputStream(this.data.load()));
        }
    }

    private void initHash(SHA1 sha1, long j) {
        sha1.update(Constants.encodedTypeString(3));
        sha1.update((byte) 32);
        sha1.update(Constants.encodeASCII(j));
        sha1.update((byte) 0);
    }

    private ObjectId hash(File file) throws IOException {
        SHA1 sha1NewInstance = SHA1.newInstance();
        initHash(sha1NewInstance, file.length());
        Throwable th = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[8192];
                while (true) {
                    int i = fileInputStream.read(bArr);
                    if (i >= 0) {
                        sha1NewInstance.update(bArr, 0, i);
                    } else {
                        fileInputStream.close();
                        return sha1NewInstance.toObjectId();
                    }
                }
            } catch (Throwable th2) {
                fileInputStream.close();
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

    private void checkOid(ObjectId objectId, ObjectId objectId2, DiffEntry.ChangeType changeType, File file, String str) throws PatchApplyException, IOException {
        boolean zEquals;
        if (objectId2 != null) {
            zEquals = objectId.equals((AnyObjectId) objectId2);
            if (!zEquals && DiffEntry.ChangeType.ADD.equals(changeType) && ObjectId.zeroId().equals((AnyObjectId) objectId)) {
                zEquals = Constants.EMPTY_BLOB_ID.equals((AnyObjectId) objectId2);
            }
        } else if (ObjectId.zeroId().equals((AnyObjectId) objectId)) {
            zEquals = !file.exists() || file.length() == 0;
        } else {
            zEquals = objectId.equals((AnyObjectId) hash(file));
        }
        if (!zEquals) {
            throw new PatchApplyException(MessageFormat.format(JGitText.get().applyBinaryBaseOidWrong, str));
        }
    }

    private void applyBinary(Repository repository, String str, File file, FileHeader fileHeader, StreamSupplier streamSupplier, ObjectId objectId, DirCacheCheckout.CheckoutMetadata checkoutMetadata) throws PatchApplyException, IOException {
        if (!fileHeader.getOldId().isComplete() || !fileHeader.getNewId().isComplete()) {
            throw new PatchApplyException(MessageFormat.format(JGitText.get().applyBinaryOidTooShort, str));
        }
        BinaryHunk forwardBinaryHunk = fileHeader.getForwardBinaryHunk();
        int iNextLF = RawParseUtils.nextLF(forwardBinaryHunk.getBuffer(), forwardBinaryHunk.getStartOffset());
        int endOffset = forwardBinaryHunk.getEndOffset() - iNextLF;
        SHA1 sha1NewInstance = SHA1.newInstance();
        Throwable th = null;
        TemporaryBuffer.LocalFile localFile = new TemporaryBuffer.LocalFile(null);
        try {
            int i = $SWITCH_TABLE$org$eclipse$jgit$patch$BinaryHunk$Type()[forwardBinaryHunk.getType().ordinal()];
            if (i == 1) {
                try {
                    checkOid(fileHeader.getOldId().toObjectId(), objectId, fileHeader.getChangeType(), file, str);
                    initHash(sha1NewInstance, forwardBinaryHunk.getSize());
                    try {
                        final SHA1InputStream sHA1InputStream = new SHA1InputStream(sha1NewInstance, new InflaterInputStream(new BinaryHunkInputStream(new ByteArrayInputStream(forwardBinaryHunk.getBuffer(), iNextLF, endOffset))));
                        try {
                            DirCacheCheckout.getContent(repository, str, checkoutMetadata, new StreamLoader(new StreamSupplier() { // from class: org.eclipse.jgit.api.ApplyCommand$$ExternalSyntheticLambda0
                                @Override // org.eclipse.jgit.api.ApplyCommand.StreamSupplier
                                public final InputStream load() {
                                    return ApplyCommand.lambda$2(sHA1InputStream);
                                }
                            }, forwardBinaryHunk.getSize()), null, localFile);
                            if (!fileHeader.getNewId().toObjectId().equals((AnyObjectId) sha1NewInstance.toObjectId())) {
                                throw new PatchApplyException(MessageFormat.format(JGitText.get().applyBinaryResultOidWrong, str));
                            }
                            sHA1InputStream.close();
                            localFile.close();
                            try {
                                InputStream inputStreamOpenInputStream = localFile.openInputStream();
                                try {
                                    Files.copy(inputStreamOpenInputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                    if (inputStreamOpenInputStream != null) {
                                        inputStreamOpenInputStream.close();
                                    }
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
                                if (null == th3) {
                                    throw null;
                                }
                                th.addSuppressed(th3);
                                throw null;
                            }
                        } catch (Throwable th4) {
                            sHA1InputStream.close();
                            throw th4;
                        }
                    } catch (Throwable th5) {
                        if (0 == 0) {
                            th = th5;
                        } else if (null != th5) {
                            th.addSuppressed(th5);
                        }
                        localFile.close();
                        throw th;
                    }
                } catch (Throwable th6) {
                    if (0 == 0) {
                        throw th6;
                    }
                    if (null == th6) {
                        throw null;
                    }
                    th.addSuppressed(th6);
                    throw null;
                }
                localFile.destroy();
                throw th;
            }
            if (i == 2) {
                try {
                    InputStream inputStreamLoad = streamSupplier.load();
                    try {
                        byte[] bArrArray = IO.readWholeStream(inputStreamLoad, 0).array();
                        if (inputStreamLoad != null) {
                            inputStreamLoad.close();
                        }
                        try {
                            BinaryDeltaInputStream binaryDeltaInputStream = new BinaryDeltaInputStream(bArrArray, new InflaterInputStream(new BinaryHunkInputStream(new ByteArrayInputStream(forwardBinaryHunk.getBuffer(), iNextLF, endOffset))));
                            try {
                                long expectedResultSize = binaryDeltaInputStream.getExpectedResultSize();
                                initHash(sha1NewInstance, expectedResultSize);
                                try {
                                    try {
                                        final SHA1InputStream sHA1InputStream2 = new SHA1InputStream(sha1NewInstance, binaryDeltaInputStream);
                                        try {
                                            DirCacheCheckout.getContent(repository, str, checkoutMetadata, new StreamLoader(new StreamSupplier() { // from class: org.eclipse.jgit.api.ApplyCommand$$ExternalSyntheticLambda1
                                                @Override // org.eclipse.jgit.api.ApplyCommand.StreamSupplier
                                                public final InputStream load() {
                                                    return ApplyCommand.lambda$3(sHA1InputStream2);
                                                }
                                            }, expectedResultSize), null, localFile);
                                            if (!fileHeader.getNewId().toObjectId().equals((AnyObjectId) sha1NewInstance.toObjectId())) {
                                                throw new PatchApplyException(MessageFormat.format(JGitText.get().applyBinaryResultOidWrong, str));
                                            }
                                            sHA1InputStream2.close();
                                            localFile.close();
                                            binaryDeltaInputStream.close();
                                            try {
                                                InputStream inputStreamOpenInputStream2 = localFile.openInputStream();
                                                try {
                                                    Files.copy(inputStreamOpenInputStream2, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                                    if (inputStreamOpenInputStream2 != null) {
                                                        inputStreamOpenInputStream2.close();
                                                    }
                                                } catch (Throwable th7) {
                                                    if (inputStreamOpenInputStream2 == null) {
                                                        throw th7;
                                                    }
                                                    inputStreamOpenInputStream2.close();
                                                    throw th7;
                                                }
                                            } catch (Throwable th8) {
                                                if (0 == 0) {
                                                    throw th8;
                                                }
                                                if (null == th8) {
                                                    throw null;
                                                }
                                                th.addSuppressed(th8);
                                                throw null;
                                            }
                                        } catch (Throwable th9) {
                                            sHA1InputStream2.close();
                                            throw th9;
                                        }
                                    } catch (Throwable th10) {
                                        if (0 == 0) {
                                            throw th10;
                                        }
                                        if (null == th10) {
                                            throw null;
                                        }
                                        th.addSuppressed(th10);
                                        throw null;
                                    }
                                } catch (Throwable th11) {
                                    if (0 == 0) {
                                        th = th11;
                                    } else if (null != th11) {
                                        th.addSuppressed(th11);
                                    }
                                    localFile.close();
                                    throw th;
                                }
                            } catch (Throwable th12) {
                                binaryDeltaInputStream.close();
                                throw th12;
                            }
                        } catch (Throwable th13) {
                            if (0 == 0) {
                                throw th13;
                            }
                            if (null == th13) {
                                throw null;
                            }
                            th.addSuppressed(th13);
                            throw null;
                        }
                    } catch (Throwable th14) {
                        if (inputStreamLoad == null) {
                            throw th14;
                        }
                        inputStreamLoad.close();
                        throw th14;
                    }
                } catch (Throwable th15) {
                    if (0 == 0) {
                        throw th15;
                    }
                    if (null == th15) {
                        throw null;
                    }
                    th.addSuppressed(th15);
                    throw null;
                }
                localFile.destroy();
                throw th;
            }
            localFile.destroy();
        } catch (Throwable th16) {
            localFile.destroy();
            throw th16;
        }
    }

    /* JADX WARN: Code duplicated, block: B:105:0x01b5 A[PHI: r3 r11
      0x01b5: PHI (r3v21 boolean) = (r3v12 boolean), (r3v8 boolean), (r3v8 boolean) binds: [B:96:0x0193, B:86:0x017a, B:88:0x0180] A[DONT_GENERATE, DONT_INLINE]
      0x01b5: PHI (r11v21 int) = (r11v9 int), (r11v23 int), (r11v23 int) binds: [B:96:0x0193, B:86:0x017a, B:88:0x0180] A[DONT_GENERATE, DONT_INLINE]] */
    private void applyText(Repository repository, String str, RawText rawText, File file, FileHeader fileHeader, DirCacheCheckout.CheckoutMetadata checkoutMetadata) throws PatchApplyException, IOException {
        ByteBuffer next;
        boolean zCanApplyAt;
        byte b;
        ArrayList arrayList = new ArrayList(rawText.size());
        boolean z = false;
        int i = 0;
        while (i < rawText.size()) {
            arrayList.add(rawText.getRawString(i));
            i++;
            z = false;
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        int newStartLine = -1;
        int newStartLine2 = z ? 1 : 0;
        int i2 = newStartLine2;
        for (HunkHeader hunkHeader : fileHeader.getHunks()) {
            if (hunkHeader.getNewStartLine() <= newStartLine) {
                throw new PatchApplyException(MessageFormat.format(JGitText.get().patchApplyException, hunkHeader));
            }
            newStartLine = hunkHeader.getNewStartLine();
            int endOffset = hunkHeader.getEndOffset() - hunkHeader.getStartOffset();
            byte[] bArr = new byte[endOffset];
            System.arraycopy(hunkHeader.getBuffer(), hunkHeader.getStartOffset(), bArr, z ? 1 : 0, endOffset);
            RawText rawText2 = new RawText(bArr);
            ArrayList arrayList3 = new ArrayList(rawText2.size());
            int i3 = z ? 1 : 0;
            while (i3 < rawText2.size()) {
                arrayList3.add(rawText2.getRawString(i3));
                i3++;
                z = false;
            }
            if (hunkHeader.getNewStartLine() == 0) {
                if (fileHeader.getHunks().size() == 1 && canApplyAt(arrayList3, arrayList2, z ? 1 : 0)) {
                    arrayList2.clear();
                    break;
                }
                throw new PatchApplyException(MessageFormat.format(JGitText.get().patchApplyException, hunkHeader));
            }
            int newStartLine3 = (hunkHeader.getNewStartLine() - 1) + (newStartLine2 == true ? 1 : 0);
            if (newStartLine3 < i2 && newStartLine2 < 0) {
                newStartLine3 = hunkHeader.getNewStartLine() - 1;
                newStartLine2 = z ? 1 : 0;
            }
            if (newStartLine3 < i2) {
                throw new PatchApplyException(MessageFormat.format(JGitText.get().patchApplyException, hunkHeader));
            }
            int linesContext = hunkHeader.getLinesContext() + hunkHeader.getOldImage().getLinesDeleted();
            if (linesContext <= 1) {
                zCanApplyAt = canApplyAt(arrayList3, arrayList2, newStartLine3);
                if (!zCanApplyAt && newStartLine2 != 0) {
                    newStartLine3 = hunkHeader.getNewStartLine() - 1;
                    if (newStartLine3 >= i2 && canApplyAt(arrayList3, arrayList2, newStartLine3)) {
                        zCanApplyAt = true;
                        break;
                    }
                    zCanApplyAt = z;
                }
            } else {
                int i4 = newStartLine3 - i2;
                int i5 = z ? 1 : 0;
                while (i5 <= i4) {
                    int i6 = newStartLine3 - i5;
                    if (canApplyAt(arrayList3, arrayList2, i6)) {
                        newStartLine3 = i6;
                        z = true;
                        break;
                    } else {
                        i5++;
                        z = false;
                    }
                }
                if (z) {
                    zCanApplyAt = z;
                } else {
                    int newStartLine4 = newStartLine2 + (hunkHeader.getNewStartLine() - 1);
                    int size = (arrayList2.size() - newStartLine4) - linesContext;
                    int i7 = 1;
                    while (true) {
                        if (i7 > size) {
                            zCanApplyAt = z ? 1 : 0;
                            newStartLine3 = newStartLine4;
                            break;
                        }
                        int i8 = newStartLine4 + i7;
                        if (canApplyAt(arrayList3, arrayList2, i8)) {
                            newStartLine3 = i8;
                            zCanApplyAt = true;
                            break;
                        }
                        i7++;
                    }
                }
            }
            if (!zCanApplyAt) {
                throw new PatchApplyException(MessageFormat.format(JGitText.get().patchApplyException, hunkHeader));
            }
            newStartLine2 = (newStartLine3 - hunkHeader.getNewStartLine()) + 1;
            int size2 = arrayList3.size();
            i2 = newStartLine3;
            for (int i9 = 1; i9 < size2; i9++) {
                ByteBuffer byteBuffer = arrayList3.get(i9);
                if (!byteBuffer.hasRemaining() || (b = byteBuffer.array()[byteBuffer.position()]) == 32) {
                    i2++;
                } else if (b == 43) {
                    arrayList2.add(i2, slice(byteBuffer, 1));
                    i2++;
                } else if (b == 45) {
                    arrayList2.remove(i2);
                }
            }
            z = false;
        }
        Throwable th = null;
        if (!isNoNewlineAtEndOfFile(fileHeader)) {
            arrayList2.add(null);
        }
        if (!rawText.isMissingNewlineAtEnd()) {
            arrayList.add(null);
        }
        if (arrayList.equals(arrayList2)) {
            return;
        }
        final TemporaryBuffer.LocalFile localFile = new TemporaryBuffer.LocalFile(null);
        try {
            try {
                try {
                    Iterator<ByteBuffer> it = arrayList2.iterator();
                    while (it.hasNext() && (next = it.next()) != null) {
                        localFile.write(next.array(), next.position(), next.remaining());
                        if (it.hasNext()) {
                            localFile.write(10);
                        }
                    }
                    localFile.close();
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        try {
                            DirCacheCheckout.getContent(repository, str, checkoutMetadata, new StreamLoader(new StreamSupplier() { // from class: org.eclipse.jgit.api.ApplyCommand$$ExternalSyntheticLambda2
                                @Override // org.eclipse.jgit.api.ApplyCommand.StreamSupplier
                                public final InputStream load() {
                                    return localFile.openInputStream();
                                }
                            }, localFile.length()), null, fileOutputStream);
                            fileOutputStream.close();
                            localFile.destroy();
                            FS fs = repository.getFS();
                            if (fileHeader.getNewMode() == FileMode.EXECUTABLE_FILE) {
                                z = true;
                            }
                            fs.setExecute(file, z);
                        } catch (Throwable th2) {
                            fileOutputStream.close();
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
                    }
                } catch (Throwable th4) {
                    if (0 == 0) {
                        throw th4;
                    }
                    if (null == th4) {
                        throw null;
                    }
                    th.addSuppressed(th4);
                }
            } catch (Throwable th5) {
                localFile.close();
                throw th5;
            }
        } catch (Throwable th6) {
            localFile.destroy();
            throw th6;
        }
    }

    private boolean canApplyAt(List<ByteBuffer> list, List<ByteBuffer> list2, int i) {
        int size = list.size();
        int size2 = list2.size();
        for (int i2 = 1; i2 < size; i2++) {
            ByteBuffer byteBuffer = list.get(i2);
            if (!byteBuffer.hasRemaining()) {
                if (i >= size2 || list2.get(i).hasRemaining()) {
                    return false;
                }
            } else {
                byte b = byteBuffer.array()[byteBuffer.position()];
                if (b == 32 || b == 45) {
                    if (i >= size2 || !list2.get(i).equals(slice(byteBuffer, 1))) {
                        return false;
                    }
                }
            }
            i++;
        }
        return true;
    }

    private ByteBuffer slice(ByteBuffer byteBuffer, int i) {
        int iPosition = byteBuffer.position() + i;
        return ByteBuffer.wrap(byteBuffer.array(), iPosition, byteBuffer.limit() - iPosition);
    }

    private boolean isNoNewlineAtEndOfFile(FileHeader fileHeader) {
        List<? extends HunkHeader> hunks = fileHeader.getHunks();
        if (hunks == null || hunks.isEmpty()) {
            return false;
        }
        HunkHeader hunkHeader = hunks.get(hunks.size() - 1);
        int endOffset = hunkHeader.getEndOffset() - hunkHeader.getStartOffset();
        byte[] bArr = new byte[endOffset];
        System.arraycopy(hunkHeader.getBuffer(), hunkHeader.getStartOffset(), bArr, 0, endOffset);
        RawText rawText = new RawText(bArr);
        return rawText.getString(rawText.size() - 1).equals("\\ No newline at end of file");
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SHA1InputStream extends InputStream {
        private final SHA1 hash;
        private final InputStream in;

        SHA1InputStream(SHA1 sha1, InputStream inputStream) {
            this.hash = sha1;
            this.in = inputStream;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            int i = this.in.read();
            if (i >= 0) {
                this.hash.update((byte) i);
            }
            return i;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int i3 = this.in.read(bArr, i, i2);
            if (i3 > 0) {
                this.hash.update(bArr, i, i3);
            }
            return i3;
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.in.close();
        }
    }
}
