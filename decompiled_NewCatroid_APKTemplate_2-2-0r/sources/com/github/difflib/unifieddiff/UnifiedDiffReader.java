package com.github.difflib.unifieddiff;

import com.github.difflib.patch.ChangeDelta;
import com.github.difflib.patch.Chunk;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class UnifiedDiffReader {
    private final InternalUnifiedDiffReader READER;
    private UnifiedDiffFile actualFile;
    private int new_ln;
    private int new_size;
    private int old_ln;
    private int old_size;
    static final Pattern UNIFIED_DIFF_CHUNK_REGEXP = Pattern.compile("^@@\\s+-(?:(\\d+)(?:,(\\d+))?)\\s+\\+(?:(\\d+)(?:,(\\d+))?)\\s+@@");
    static final Pattern TIMESTAMP_REGEXP = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}[T ]\\d{2}:\\d{2}:\\d{2}\\.\\d{3,})(?: [+-]\\d+)?");
    private static final Logger LOG = Logger.getLogger(UnifiedDiffReader.class.getName());
    private final UnifiedDiff data = new UnifiedDiff();
    private final UnifiedDiffLine DIFF_COMMAND = new UnifiedDiffLine(true, "^diff\\s", (BiConsumer<MatchResult, String>) new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda0
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processDiff((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine SIMILARITY_INDEX = new UnifiedDiffLine(true, "^similarity index (\\d+)%$", (BiConsumer<MatchResult, String>) new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda7
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processSimilarityIndex((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine INDEX = new UnifiedDiffLine(true, "^index\\s[\\da-zA-Z]+\\.\\.[\\da-zA-Z]+(\\s(\\d+))?$", (BiConsumer<MatchResult, String>) new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda8
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processIndex((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine FROM_FILE = new UnifiedDiffLine(true, "^---\\s", (BiConsumer<MatchResult, String>) new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda9
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processFromFile((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine TO_FILE = new UnifiedDiffLine(true, "^\\+\\+\\+\\s", (BiConsumer<MatchResult, String>) new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda10
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processToFile((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine RENAME_FROM = new UnifiedDiffLine(true, "^rename\\sfrom\\s(.+)$", (BiConsumer<MatchResult, String>) new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda11
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processRenameFrom((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine RENAME_TO = new UnifiedDiffLine(true, "^rename\\sto\\s(.+)$", (BiConsumer<MatchResult, String>) new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda12
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processRenameTo((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine NEW_FILE_MODE = new UnifiedDiffLine(true, "^new\\sfile\\smode\\s(\\d+)", (BiConsumer<MatchResult, String>) new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda1
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processNewFileMode((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine DELETED_FILE_MODE = new UnifiedDiffLine(true, "^deleted\\sfile\\smode\\s(\\d+)", (BiConsumer<MatchResult, String>) new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda2
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processDeletedFileMode((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine CHUNK = new UnifiedDiffLine(false, UNIFIED_DIFF_CHUNK_REGEXP, (BiConsumer<MatchResult, String>) new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda3
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processChunk((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine LINE_NORMAL = new UnifiedDiffLine(this, "^\\s", new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda4
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processNormalLine((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine LINE_DEL = new UnifiedDiffLine(this, "^-", new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda5
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processDelLine((MatchResult) obj, (String) obj2);
        }
    });
    private final UnifiedDiffLine LINE_ADD = new UnifiedDiffLine(this, "^\\+", new BiConsumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffReader$$ExternalSyntheticLambda6
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            this.f$0.processAddLine((MatchResult) obj, (String) obj2);
        }
    });
    private List<String> originalTxt = new ArrayList();
    private List<String> revisedTxt = new ArrayList();
    private List<Integer> addLineIdxList = new ArrayList();
    private List<Integer> delLineIdxList = new ArrayList();
    private int delLineIdx = 0;
    private int addLineIdx = 0;

    UnifiedDiffReader(Reader reader) {
        this.READER = new InternalUnifiedDiffReader(reader);
    }

    private UnifiedDiff parse() throws UnifiedDiffParserException, IOException {
        String str;
        String line = this.READER.readLine();
        while (true) {
            str = "";
            if (line == null) {
                break;
            }
            LOG.log(Level.FINE, "header parsing");
            String str2 = "";
            while (line != null) {
                LOG.log(Level.FINE, "parsing line {0}", line);
                if (validLine(line, this.DIFF_COMMAND, this.SIMILARITY_INDEX, this.INDEX, this.FROM_FILE, this.TO_FILE, this.RENAME_FROM, this.RENAME_TO, this.NEW_FILE_MODE, this.DELETED_FILE_MODE, this.CHUNK)) {
                    break;
                }
                str2 = str2 + line + "\n";
                line = this.READER.readLine();
            }
            if (!"".equals(str2)) {
                this.data.setHeader(str2);
            }
            if (line != null && !this.CHUNK.validLine(line)) {
                initFileIfNecessary();
                while (line != null && !this.CHUNK.validLine(line)) {
                    if (!processLine(line, this.DIFF_COMMAND, this.SIMILARITY_INDEX, this.INDEX, this.FROM_FILE, this.TO_FILE, this.RENAME_FROM, this.RENAME_TO, this.NEW_FILE_MODE, this.DELETED_FILE_MODE)) {
                        throw new UnifiedDiffParserException("expected file start line not found");
                    }
                    line = this.READER.readLine();
                }
            }
            if (line != null) {
                processLine(line, this.CHUNK);
                while (true) {
                    String line2 = this.READER.readLine();
                    if (line2 == null) {
                        break;
                    }
                    if (!processLine(checkForNoNewLineAtTheEndOfTheFile(line2), this.LINE_NORMAL, this.LINE_ADD, this.LINE_DEL)) {
                        throw new UnifiedDiffParserException("expected data line not found");
                    }
                    if ((this.originalTxt.size() == this.old_size && this.revisedTxt.size() == this.new_size) || (this.old_size == 0 && this.new_size == 0 && this.originalTxt.size() == this.old_ln && this.revisedTxt.size() == this.new_ln)) {
                        finalizeChunk();
                        break;
                    }
                }
                line = checkForNoNewLineAtTheEndOfTheFile(this.READER.readLine());
            }
            if (line == null || (line.startsWith("--") && !line.startsWith("---"))) {
                break;
            }
        }
        if (this.READER.ready()) {
            while (this.READER.ready()) {
                if (str.length() > 0) {
                    str = str + "\n";
                }
                str = str + this.READER.readLine();
            }
            this.data.setTailTxt(str);
        }
        return this.data;
    }

    private String checkForNoNewLineAtTheEndOfTheFile(String str) throws IOException {
        if (!"\\ No newline at end of file".equals(str)) {
            return str;
        }
        this.actualFile.setNoNewLineAtTheEndOfTheFile(true);
        return this.READER.readLine();
    }

    static String[] parseFileNames(String str) {
        String[] strArrSplit = str.split(" ");
        return new String[]{strArrSplit[2].replaceAll("^a/", ""), strArrSplit[3].replaceAll("^b/", "")};
    }

    public static UnifiedDiff parseUnifiedDiff(InputStream inputStream) throws UnifiedDiffParserException, IOException {
        return new UnifiedDiffReader(new BufferedReader(new InputStreamReader(inputStream))).parse();
    }

    private boolean processLine(String str, UnifiedDiffLine... unifiedDiffLineArr) throws UnifiedDiffParserException {
        if (str == null) {
            return false;
        }
        for (UnifiedDiffLine unifiedDiffLine : unifiedDiffLineArr) {
            if (unifiedDiffLine.processLine(str)) {
                LOG.fine("  >>> processed rule " + unifiedDiffLine.toString());
                return true;
            }
        }
        LOG.warning("  >>> no rule matched " + str);
        return false;
    }

    private boolean validLine(String str, UnifiedDiffLine... unifiedDiffLineArr) {
        if (str == null) {
            return false;
        }
        for (UnifiedDiffLine unifiedDiffLine : unifiedDiffLineArr) {
            if (unifiedDiffLine.validLine(str)) {
                LOG.fine("  >>> accepted rule " + unifiedDiffLine.toString());
                return true;
            }
        }
        return false;
    }

    private void initFileIfNecessary() {
        if (!this.originalTxt.isEmpty() || !this.revisedTxt.isEmpty()) {
            throw new IllegalStateException();
        }
        this.actualFile = null;
        UnifiedDiffFile unifiedDiffFile = new UnifiedDiffFile();
        this.actualFile = unifiedDiffFile;
        this.data.addFile(unifiedDiffFile);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processDiff(MatchResult matchResult, String str) {
        LOG.log(Level.FINE, "start {0}", str);
        String[] fileNames = parseFileNames(this.READER.lastLine());
        this.actualFile.setFromFile(fileNames[0]);
        this.actualFile.setToFile(fileNames[1]);
        this.actualFile.setDiffCommand(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSimilarityIndex(MatchResult matchResult, String str) {
        this.actualFile.setSimilarityIndex(Integer.valueOf(matchResult.group(1)));
    }

    private void finalizeChunk() {
        if (this.originalTxt.isEmpty() && this.revisedTxt.isEmpty()) {
            return;
        }
        this.actualFile.getPatch().addDelta(new ChangeDelta(new Chunk(this.old_ln - 1, this.originalTxt, this.delLineIdxList), new Chunk(this.new_ln - 1, this.revisedTxt, this.addLineIdxList)));
        this.old_ln = 0;
        this.new_ln = 0;
        this.originalTxt.clear();
        this.revisedTxt.clear();
        this.addLineIdxList.clear();
        this.delLineIdxList.clear();
        this.delLineIdx = 0;
        this.addLineIdx = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processNormalLine(MatchResult matchResult, String str) {
        String strSubstring = str.substring(1);
        this.originalTxt.add(strSubstring);
        this.revisedTxt.add(strSubstring);
        this.delLineIdx++;
        this.addLineIdx++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processAddLine(MatchResult matchResult, String str) {
        this.revisedTxt.add(str.substring(1));
        int i = this.addLineIdx + 1;
        this.addLineIdx = i;
        this.addLineIdxList.add(Integer.valueOf((this.new_ln - 1) + i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processDelLine(MatchResult matchResult, String str) {
        this.originalTxt.add(str.substring(1));
        int i = this.delLineIdx + 1;
        this.delLineIdx = i;
        this.delLineIdxList.add(Integer.valueOf((this.old_ln - 1) + i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processChunk(MatchResult matchResult, String str) {
        this.old_ln = toInteger(matchResult, 1, 1).intValue();
        this.old_size = toInteger(matchResult, 2, 1).intValue();
        this.new_ln = toInteger(matchResult, 3, 1).intValue();
        this.new_size = toInteger(matchResult, 4, 1).intValue();
        if (this.old_ln == 0) {
            this.old_ln = 1;
        }
        if (this.new_ln == 0) {
            this.new_ln = 1;
        }
    }

    private static Integer toInteger(MatchResult matchResult, int i, int i2) throws NumberFormatException {
        return Integer.valueOf(Objects.toString(matchResult.group(i), "" + i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processIndex(MatchResult matchResult, String str) {
        LOG.log(Level.FINE, "index {0}", str);
        this.actualFile.setIndex(str.substring(6));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processFromFile(MatchResult matchResult, String str) {
        this.actualFile.setFromFile(extractFileName(str));
        this.actualFile.setFromTimestamp(extractTimestamp(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processToFile(MatchResult matchResult, String str) {
        this.actualFile.setToFile(extractFileName(str));
        this.actualFile.setToTimestamp(extractTimestamp(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processRenameFrom(MatchResult matchResult, String str) {
        this.actualFile.setRenameFrom(matchResult.group(1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processRenameTo(MatchResult matchResult, String str) {
        this.actualFile.setRenameTo(matchResult.group(1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processNewFileMode(MatchResult matchResult, String str) {
        this.actualFile.setNewFileMode(matchResult.group(1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processDeletedFileMode(MatchResult matchResult, String str) {
        this.actualFile.setDeletedFileMode(matchResult.group(1));
    }

    private String extractFileName(String str) {
        Matcher matcher = TIMESTAMP_REGEXP.matcher(str);
        if (matcher.find()) {
            str = str.substring(0, matcher.start());
        }
        return str.split("\t")[0].substring(4).replaceFirst("^(a|b|old|new)/", "").trim();
    }

    private String extractTimestamp(String str) {
        Matcher matcher = TIMESTAMP_REGEXP.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    final class UnifiedDiffLine {
        private final BiConsumer<MatchResult, String> command;
        private final Pattern pattern;
        private final boolean stopsHeaderParsing;

        public UnifiedDiffLine(UnifiedDiffReader unifiedDiffReader, String str, BiConsumer<MatchResult, String> biConsumer) {
            this(false, str, biConsumer);
        }

        public UnifiedDiffLine(boolean z, String str, BiConsumer<MatchResult, String> biConsumer) {
            this.pattern = Pattern.compile(str);
            this.command = biConsumer;
            this.stopsHeaderParsing = z;
        }

        public UnifiedDiffLine(boolean z, Pattern pattern, BiConsumer<MatchResult, String> biConsumer) {
            this.pattern = pattern;
            this.command = biConsumer;
            this.stopsHeaderParsing = z;
        }

        public boolean validLine(String str) {
            return this.pattern.matcher(str).find();
        }

        public boolean processLine(String str) throws UnifiedDiffParserException {
            Matcher matcher = this.pattern.matcher(str);
            if (!matcher.find()) {
                return false;
            }
            this.command.accept(matcher.toMatchResult(), str);
            return true;
        }

        public boolean isStopsHeaderParsing() {
            return this.stopsHeaderParsing;
        }

        public String toString() {
            return "UnifiedDiffLine{pattern=" + this.pattern + ", stopsHeaderParsing=" + this.stopsHeaderParsing + '}';
        }
    }
}
