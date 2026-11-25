package org.eclipse.jgit.merge;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import org.eclipse.jgit.diff.RawText;

/* JADX INFO: loaded from: classes13.dex */
class MergeFormatterPass {
    private final Charset charset;
    private String lastConflictingName;
    private final EolAwareOutputStream out;
    private final MergeResult<RawText> res;
    private final List<String> seqName;
    private final boolean threeWayMerge;

    MergeFormatterPass(OutputStream outputStream, MergeResult<RawText> mergeResult, List<String> list, Charset charset) {
        this.out = new EolAwareOutputStream(outputStream);
        this.res = mergeResult;
        this.seqName = list;
        this.charset = charset;
        this.threeWayMerge = mergeResult.getSequences().size() == 3;
    }

    void formatMerge() throws IOException {
        boolean zIsMissingNewlineAtEnd = false;
        for (MergeChunk mergeChunk : this.res) {
            RawText rawText = (RawText) this.res.getSequences().get(mergeChunk.getSequenceIndex());
            writeConflictMetadata(mergeChunk);
            for (int begin = mergeChunk.getBegin(); begin < mergeChunk.getEnd(); begin++) {
                writeLine(rawText, begin);
            }
            zIsMissingNewlineAtEnd = rawText.isMissingNewlineAtEnd();
        }
        if (this.lastConflictingName != null) {
            writeConflictEnd();
        }
        if (zIsMissingNewlineAtEnd) {
            return;
        }
        this.out.beginln();
    }

    private void writeConflictMetadata(MergeChunk mergeChunk) throws IOException {
        if (this.lastConflictingName != null && mergeChunk.getConflictState() != MergeChunk.ConflictState.NEXT_CONFLICTING_RANGE) {
            writeConflictEnd();
        }
        if (mergeChunk.getConflictState() == MergeChunk.ConflictState.FIRST_CONFLICTING_RANGE) {
            writeConflictStart(mergeChunk);
        } else if (mergeChunk.getConflictState() == MergeChunk.ConflictState.NEXT_CONFLICTING_RANGE) {
            writeConflictChange(mergeChunk);
        }
    }

    private void writeConflictEnd() throws IOException {
        writeln(">>>>>>> " + this.lastConflictingName);
        this.lastConflictingName = null;
    }

    private void writeConflictStart(MergeChunk mergeChunk) throws IOException {
        this.lastConflictingName = this.seqName.get(mergeChunk.getSequenceIndex());
        writeln("<<<<<<< " + this.lastConflictingName);
    }

    private void writeConflictChange(MergeChunk mergeChunk) throws IOException {
        this.lastConflictingName = this.seqName.get(mergeChunk.getSequenceIndex());
        writeln(this.threeWayMerge ? "=======" : "======= " + this.lastConflictingName);
    }

    private void writeln(String str) throws IOException {
        this.out.beginln();
        this.out.write((String.valueOf(str) + "\n").getBytes(this.charset));
    }

    private void writeLine(RawText rawText, int i) throws IOException {
        this.out.beginln();
        rawText.writeLine(this.out, i);
        if (this.out.isBeginln()) {
            this.out.write(10);
        }
    }
}
