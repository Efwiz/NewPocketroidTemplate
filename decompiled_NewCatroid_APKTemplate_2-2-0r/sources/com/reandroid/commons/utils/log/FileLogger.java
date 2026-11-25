package com.reandroid.commons.utils.log;

import com.reandroid.commons.utils.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class FileLogger extends WriterLogger {
    private static final int MAX_LINES = 25000;
    private File mFile;
    private final Object mLock;

    public FileLogger(File file) throws IOException {
        super(createWriter(file), countLines(file));
        this.mLock = new Object();
        this.mFile = file;
    }

    public File getFile() {
        return this.mFile;
    }

    public boolean setFile(File file) throws IOException {
        File file2 = getFile();
        if (Objects.equals(file2, file)) {
            return false;
        }
        synchronized (this.mLock) {
            Writer writerCreateWriter = createWriter(file);
            super.setWriter(writerCreateWriter);
            super.resetCount();
            writerCreateWriter.write("// Previous file: " + file2);
            writerCreateWriter.flush();
            this.mFile = file;
        }
        return true;
    }

    @Override // com.reandroid.commons.utils.log.WriterLogger
    void onTotalCount(Writer writer, int i) {
        if (i < MAX_LINES) {
            return;
        }
        try {
            if (setFile(FileUtil.getNextNumberedFile(FileUtil.getLastNumberedFile(getFile())))) {
                writer.close();
            }
        } catch (IOException unused) {
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getFile(), ((FileLogger) obj).getFile());
    }

    public int hashCode() {
        return Objects.hash(getFile());
    }

    public String toString() {
        return "FileLogger{" + getFile() + '}';
    }

    private static Writer createWriter(File file) throws IOException {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        return new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8);
    }

    public static FileLogger create(File file) {
        try {
            FileLogger fileLogger = new FileLogger(prepare(FileUtil.getLastNumberedFile(file)));
            fileLogger.setEnable(true);
            fileLogger.setIgnoreSameLine(true);
            return fileLogger;
        } catch (IOException unused) {
            return null;
        }
    }

    public static File prepare(File file) {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (countLines(file) < MAX_LINES) {
            return file;
        }
        File nextNumberedFile = FileUtil.getNextNumberedFile(file);
        return file.equals(nextNumberedFile) ? file : prepare(nextNumberedFile);
    }

    private static int countLines(File file) {
        try {
            return FileUtil.countLines(file);
        } catch (IOException unused) {
            return 0;
        }
    }
}
