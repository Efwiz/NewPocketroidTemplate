package org.jetbrains.kotlin.org.jline.reader.impl.history;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.time.Instant;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;
import org.jetbrains.kotlin.org.jline.reader.History;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.org.jline.reader.impl.ReaderUtils;
import org.jetbrains.kotlin.org.jline.utils.Log;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultHistory implements History {
    private LineReader reader;
    private final LinkedList<History.Entry> items = new LinkedList<>();
    private int loaded = 0;
    private int offset = 0;
    private int index = 0;

    private Path getPath() {
        LineReader lineReader = this.reader;
        Object obj = lineReader != null ? lineReader.getVariables().get(LineReader.HISTORY_FILE) : null;
        if (obj instanceof Path) {
            return (Path) obj;
        }
        if (obj instanceof File) {
            return ((File) obj).toPath();
        }
        if (obj != null) {
            return Paths.get(obj.toString(), new String[0]);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public void attach(LineReader lineReader) {
        if (this.reader != lineReader) {
            this.reader = lineReader;
            try {
                load();
            } catch (IOException e) {
                Log.warn("Failed to load history", e);
            }
        }
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public void load() throws IOException {
        Path path = getPath();
        if (path != null) {
            try {
                if (Files.exists(path, new LinkOption[0])) {
                    Log.trace("Loading history from: ", path);
                    BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(path);
                    try {
                        internalClear();
                        bufferedReaderNewBufferedReader.lines().forEach(new Consumer() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.history.DefaultHistory$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                this.f$0.m7383xaca91f5e((String) obj);
                            }
                        });
                        this.loaded = this.items.size();
                        maybeResize();
                        if (bufferedReaderNewBufferedReader != null) {
                            bufferedReaderNewBufferedReader.close();
                        }
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            if (bufferedReaderNewBufferedReader != null) {
                                try {
                                    bufferedReaderNewBufferedReader.close();
                                } catch (Throwable th3) {
                                    th.addSuppressed(th3);
                                }
                            }
                            throw th2;
                        }
                    }
                }
            } catch (IOException e) {
                Log.debug("Failed to load history; clearing", e);
                internalClear();
                throw e;
            }
        }
    }

    /* JADX INFO: renamed from: lambda$load$0$org-jetbrains-kotlin-org-jline-reader-impl-history-DefaultHistory, reason: not valid java name */
    /* synthetic */ void m7383xaca91f5e(String str) {
        int iIndexOf = str.indexOf(58);
        internalAdd(Instant.ofEpochMilli(Long.parseLong(str.substring(0, iIndexOf))), unescape(str.substring(iIndexOf + 1)));
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public void purge() throws IOException {
        internalClear();
        Path path = getPath();
        if (path != null) {
            Log.trace("Purging history from: ", path);
            Files.deleteIfExists(path);
        }
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public void save() throws IOException {
        Path path = getPath();
        if (path != null) {
            Log.trace("Saving history to: ", path);
            Files.createDirectories(path.toAbsolutePath().getParent(), new FileAttribute[0]);
            BufferedWriter bufferedWriterNewBufferedWriter = Files.newBufferedWriter(path.toAbsolutePath(), StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            try {
                LinkedList<History.Entry> linkedList = this.items;
                Iterator<History.Entry> it = linkedList.subList(this.loaded, linkedList.size()).iterator();
                while (it.hasNext()) {
                    bufferedWriterNewBufferedWriter.append((CharSequence) format(it.next()));
                }
                if (bufferedWriterNewBufferedWriter != null) {
                    bufferedWriterNewBufferedWriter.close();
                }
                int i = ReaderUtils.getInt(this.reader, LineReader.HISTORY_FILE_SIZE, 10000);
                if (last() > (i / 4) + i) {
                    trimHistory(path, i);
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (bufferedWriterNewBufferedWriter != null) {
                        try {
                            bufferedWriterNewBufferedWriter.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                    }
                    throw th2;
                }
            }
        }
        this.loaded = this.items.size();
    }

    protected void trimHistory(Path path, int i) throws IOException {
        Log.trace("Trimming history path: ", path);
        final LinkedList linkedList = new LinkedList();
        BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(path);
        try {
            bufferedReaderNewBufferedReader.lines().forEach(new Consumer() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.history.DefaultHistory$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DefaultHistory.lambda$trimHistory$1(linkedList, (String) obj);
                }
            });
            if (bufferedReaderNewBufferedReader != null) {
                bufferedReaderNewBufferedReader.close();
            }
            doTrimHistory(linkedList, i);
            Path pathCreateTempFile = Files.createTempFile(path.toAbsolutePath().getParent(), path.getFileName().toString(), ".tmp", new FileAttribute[0]);
            BufferedWriter bufferedWriterNewBufferedWriter = Files.newBufferedWriter(pathCreateTempFile, StandardOpenOption.WRITE);
            try {
                Iterator it = linkedList.iterator();
                while (it.hasNext()) {
                    bufferedWriterNewBufferedWriter.append((CharSequence) format((History.Entry) it.next()));
                }
                if (bufferedWriterNewBufferedWriter != null) {
                    bufferedWriterNewBufferedWriter.close();
                }
                Files.move(pathCreateTempFile, path, StandardCopyOption.REPLACE_EXISTING);
                internalClear();
                this.items.addAll(linkedList);
                this.loaded = this.items.size();
                maybeResize();
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (bufferedWriterNewBufferedWriter != null) {
                        try {
                            bufferedWriterNewBufferedWriter.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                    }
                    throw th2;
                }
            }
        } catch (Throwable th4) {
            try {
                throw th4;
            } catch (Throwable th5) {
                if (bufferedReaderNewBufferedReader != null) {
                    try {
                        bufferedReaderNewBufferedReader.close();
                    } catch (Throwable th6) {
                        th4.addSuppressed(th6);
                    }
                }
                throw th5;
            }
        }
    }

    static /* synthetic */ void lambda$trimHistory$1(LinkedList linkedList, String str) {
        int iIndexOf = str.indexOf(58);
        linkedList.add(new EntryImpl(linkedList.size(), Instant.ofEpochMilli(Long.parseLong(str.substring(0, iIndexOf))), unescape(str.substring(iIndexOf + 1))));
    }

    private void internalClear() {
        this.offset = 0;
        this.index = 0;
        this.loaded = 0;
        this.items.clear();
    }

    static void doTrimHistory(List<History.Entry> list, int i) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            int size = (list.size() - i2) - 1;
            String strTrim = list.get(size).line().trim();
            ListIterator<History.Entry> listIterator = list.listIterator(size);
            while (listIterator.hasPrevious()) {
                if (strTrim.equals(listIterator.previous().line().trim())) {
                    listIterator.remove();
                }
            }
        }
        while (list.size() > i) {
            list.remove(0);
        }
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public int size() {
        return this.items.size();
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public int index() {
        return this.offset + this.index;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public int first() {
        return this.offset;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public int last() {
        return (this.offset + this.items.size()) - 1;
    }

    private String format(History.Entry entry) {
        return Long.toString(entry.time().toEpochMilli()) + ":" + escape(entry.line()) + "\n";
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public String get(int i) {
        return this.items.get(i - this.offset).line();
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public void add(Instant instant, String str) {
        Objects.requireNonNull(instant);
        Objects.requireNonNull(str);
        if (ReaderUtils.getBoolean(this.reader, LineReader.DISABLE_HISTORY, false)) {
            return;
        }
        if (ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_IGNORE_SPACE) && str.startsWith(" ")) {
            return;
        }
        if (ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_REDUCE_BLANKS)) {
            str = str.trim();
        }
        if ((ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_IGNORE_DUPS) && !this.items.isEmpty() && str.equals(this.items.getLast().line())) || matchPatterns(ReaderUtils.getString(this.reader, LineReader.HISTORY_IGNORE, ""), str)) {
            return;
        }
        internalAdd(instant, str);
        if (ReaderUtils.isSet(this.reader, LineReader.Option.HISTORY_INCREMENTAL)) {
            try {
                save();
            } catch (IOException e) {
                Log.warn("Failed to save history", e);
            }
        }
    }

    protected boolean matchPatterns(String str, String str2) {
        int i = 0;
        if (str == null || str.isEmpty()) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\\') {
                i++;
                sb.append(str.charAt(i));
            } else if (cCharAt == ':') {
                sb.append('|');
            } else if (cCharAt == '*') {
                sb.append(".*");
            }
            i++;
        }
        return str2.matches(sb.toString());
    }

    protected void internalAdd(Instant instant, String str) {
        this.items.add(new EntryImpl(this.offset + this.items.size(), instant, str));
        maybeResize();
    }

    private void maybeResize() {
        while (size() > ReaderUtils.getInt(this.reader, LineReader.HISTORY_SIZE, 500)) {
            this.items.removeFirst();
            this.loaded--;
            this.offset++;
        }
        this.index = size();
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public ListIterator<History.Entry> iterator(int i) {
        return this.items.listIterator(i - this.offset);
    }

    static class EntryImpl implements History.Entry {
        private final int index;
        private final String line;
        private final Instant time;

        public EntryImpl(int i, Instant instant, String str) {
            this.index = i;
            this.time = instant;
            this.line = str;
        }

        @Override // org.jetbrains.kotlin.org.jline.reader.History.Entry
        public int index() {
            return this.index;
        }

        @Override // org.jetbrains.kotlin.org.jline.reader.History.Entry
        public Instant time() {
            return this.time;
        }

        @Override // org.jetbrains.kotlin.org.jline.reader.History.Entry
        public String line() {
            return this.line;
        }

        public String toString() {
            return String.format("%d: %s", Integer.valueOf(this.index), this.line);
        }
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public boolean moveToLast() {
        int size = size() - 1;
        if (size < 0 || size == this.index) {
            return false;
        }
        this.index = size() - 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public boolean moveTo(int i) {
        int i2 = i - this.offset;
        if (i2 < 0 || i2 >= size()) {
            return false;
        }
        this.index = i2;
        return true;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public boolean moveToFirst() {
        if (size() <= 0 || this.index == 0) {
            return false;
        }
        this.index = 0;
        return true;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public void moveToEnd() {
        this.index = size();
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public String current() {
        if (this.index >= size()) {
            return "";
        }
        return this.items.get(this.index).line();
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public boolean previous() {
        int i = this.index;
        if (i <= 0) {
            return false;
        }
        this.index = i - 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.History
    public boolean next() {
        if (this.index >= size()) {
            return false;
        }
        this.index++;
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<History.Entry> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString()).append("\n");
        }
        return sb.toString();
    }

    private static String escape(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\n') {
                sb.append("\\n");
            } else if (cCharAt == '\\') {
                sb.append("\\\\");
            } else {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    private static String unescape(String str) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\\') {
                i++;
                char cCharAt2 = str.charAt(i);
                if (cCharAt2 == 'n') {
                    sb.append('\n');
                } else {
                    sb.append(cCharAt2);
                }
            } else {
                sb.append(cCharAt);
            }
            i++;
        }
        return sb.toString();
    }
}
