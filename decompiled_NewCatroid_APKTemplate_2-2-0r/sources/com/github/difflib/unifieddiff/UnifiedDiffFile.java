package com.github.difflib.unifieddiff;

import com.github.difflib.patch.Patch;

/* JADX INFO: loaded from: classes.dex */
public final class UnifiedDiffFile {
    private String deletedFileMode;
    private String diffCommand;
    private String fromFile;
    private String fromTimestamp;
    private String index;
    private String newFileMode;
    private String renameFrom;
    private String renameTo;
    private Integer similarityIndex;
    private String toFile;
    private String toTimestamp;
    private Patch<String> patch = new Patch<>();
    private boolean noNewLineAtTheEndOfTheFile = false;

    public String getDiffCommand() {
        return this.diffCommand;
    }

    public void setDiffCommand(String str) {
        this.diffCommand = str;
    }

    public String getFromFile() {
        return this.fromFile;
    }

    public void setFromFile(String str) {
        this.fromFile = str;
    }

    public String getToFile() {
        return this.toFile;
    }

    public void setToFile(String str) {
        this.toFile = str;
    }

    public void setIndex(String str) {
        this.index = str;
    }

    public String getIndex() {
        return this.index;
    }

    public Patch<String> getPatch() {
        return this.patch;
    }

    public String getFromTimestamp() {
        return this.fromTimestamp;
    }

    public void setFromTimestamp(String str) {
        this.fromTimestamp = str;
    }

    public String getToTimestamp() {
        return this.toTimestamp;
    }

    public void setToTimestamp(String str) {
        this.toTimestamp = str;
    }

    public Integer getSimilarityIndex() {
        return this.similarityIndex;
    }

    public void setSimilarityIndex(Integer num) {
        this.similarityIndex = num;
    }

    public String getRenameFrom() {
        return this.renameFrom;
    }

    public void setRenameFrom(String str) {
        this.renameFrom = str;
    }

    public String getRenameTo() {
        return this.renameTo;
    }

    public void setRenameTo(String str) {
        this.renameTo = str;
    }

    public static UnifiedDiffFile from(String str, String str2, Patch<String> patch) {
        UnifiedDiffFile unifiedDiffFile = new UnifiedDiffFile();
        unifiedDiffFile.setFromFile(str);
        unifiedDiffFile.setToFile(str2);
        unifiedDiffFile.patch = patch;
        return unifiedDiffFile;
    }

    public void setNewFileMode(String str) {
        this.newFileMode = str;
    }

    public String getNewFileMode() {
        return this.newFileMode;
    }

    public String getDeletedFileMode() {
        return this.deletedFileMode;
    }

    public void setDeletedFileMode(String str) {
        this.deletedFileMode = str;
    }

    public boolean isNoNewLineAtTheEndOfTheFile() {
        return this.noNewLineAtTheEndOfTheFile;
    }

    public void setNoNewLineAtTheEndOfTheFile(boolean z) {
        this.noNewLineAtTheEndOfTheFile = z;
    }
}
