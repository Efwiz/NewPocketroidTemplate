package org.eclipse.jgit.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import org.eclipse.jgit.errors.CommandFailedException;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes14.dex */
public class FS_Win32 extends FS {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) FS_Win32.class);

    @Override // org.eclipse.jgit.util.FS
    public boolean canExecute(File file) {
        return false;
    }

    @Override // org.eclipse.jgit.util.FS
    public boolean isCaseSensitive() {
        return false;
    }

    @Override // org.eclipse.jgit.util.FS
    public boolean retryFailedLockFileCommit() {
        return true;
    }

    @Override // org.eclipse.jgit.util.FS
    public boolean setExecute(File file, boolean z) {
        return false;
    }

    @Override // org.eclipse.jgit.util.FS
    public boolean supportsExecute() {
        return false;
    }

    public FS_Win32() {
    }

    protected FS_Win32(FS fs) {
        super(fs);
    }

    @Override // org.eclipse.jgit.util.FS
    public FS newInstance() {
        return new FS_Win32(this);
    }

    @Override // org.eclipse.jgit.util.FS
    public WorkingTreeIterator.Entry[] list(File file, final FileTreeIterator.FileModeStrategy fileModeStrategy) {
        final ArrayList arrayList = new ArrayList();
        final boolean zSupportsExecute = supportsExecute();
        try {
            Files.walkFileTree(file.toPath(), EnumSet.noneOf(FileVisitOption.class), 1, new SimpleFileVisitor<Path>() { // from class: org.eclipse.jgit.util.FS_Win32.1
                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                    File file2 = path.toFile();
                    arrayList.add(new FileTreeIterator.FileEntry(file2, this, new FS.Attributes(this, file2, true, basicFileAttributes.isDirectory(), zSupportsExecute && file2.canExecute(), basicFileAttributes.isSymbolicLink(), basicFileAttributes.isRegularFile(), basicFileAttributes.creationTime().toMillis(), basicFileAttributes.lastModifiedTime().toInstant(), basicFileAttributes.size()), fileModeStrategy));
                    return FileVisitResult.CONTINUE;
                }

                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public FileVisitResult visitFileFailed(Path path, IOException iOException) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException unused) {
        }
        if (arrayList.isEmpty()) {
            return NO_ENTRIES;
        }
        return (WorkingTreeIterator.Entry[]) arrayList.toArray(new WorkingTreeIterator.Entry[0]);
    }

    @Override // org.eclipse.jgit.util.FS
    protected File discoverGitExe() {
        String str = SystemReader.getInstance().getenv("PATH");
        File fileSearchPath = searchPath(str, "git.exe", "git.cmd");
        if (fileSearchPath != null || searchPath(str, "bash.exe") == null) {
            return fileSearchPath;
        }
        try {
            String pipe = readPipe(userHome(), new String[]{"bash", "--login", "-c", "which git"}, Charset.defaultCharset().name());
            return !StringUtils.isEmptyOrNull(pipe) ? resolve(null, pipe) : fileSearchPath;
        } catch (CommandFailedException e) {
            LOG.warn(e.getMessage());
            return null;
        }
    }

    @Override // org.eclipse.jgit.util.FS
    protected File userHomeImpl() {
        String str;
        String str2 = SystemReader.getInstance().getenv("HOME");
        if (str2 != null) {
            return resolve(null, str2);
        }
        String str3 = SystemReader.getInstance().getenv("HOMEDRIVE");
        if (str3 != null && (str = SystemReader.getInstance().getenv("HOMEPATH")) != null) {
            return new File(str3, str);
        }
        String str4 = SystemReader.getInstance().getenv("HOMESHARE");
        if (str4 != null) {
            return new File(str4);
        }
        return super.userHomeImpl();
    }

    @Override // org.eclipse.jgit.util.FS
    public ProcessBuilder runInShell(String str, String[] strArr) {
        ArrayList arrayList = new ArrayList(strArr.length + 3);
        arrayList.add("cmd.exe");
        arrayList.add("/c");
        arrayList.add(str);
        arrayList.addAll(Arrays.asList(strArr));
        ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
        processBuilder.command(arrayList);
        return processBuilder;
    }

    @Override // org.eclipse.jgit.util.FS
    public FS.Attributes getAttributes(File file) {
        return FileUtils.getFileAttributesBasic(this, file);
    }
}
