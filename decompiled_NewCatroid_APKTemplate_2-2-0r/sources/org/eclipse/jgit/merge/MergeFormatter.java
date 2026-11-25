package org.eclipse.jgit.merge;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.diff.RawText;

/* JADX INFO: loaded from: classes13.dex */
public class MergeFormatter {
    @Deprecated
    public void formatMerge(OutputStream outputStream, MergeResult<RawText> mergeResult, List<String> list, String str) throws IOException {
        formatMerge(outputStream, mergeResult, list, Charset.forName(str));
    }

    public void formatMerge(OutputStream outputStream, MergeResult<RawText> mergeResult, List<String> list, Charset charset) throws IOException {
        new MergeFormatterPass(outputStream, mergeResult, list, charset).formatMerge();
    }

    @Deprecated
    public void formatMerge(OutputStream outputStream, MergeResult mergeResult, String str, String str2, String str3, String str4) throws IOException {
        formatMerge(outputStream, mergeResult, str, str2, str3, Charset.forName(str4));
    }

    public void formatMerge(OutputStream outputStream, MergeResult mergeResult, String str, String str2, String str3, Charset charset) throws IOException {
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        arrayList.add(str2);
        arrayList.add(str3);
        formatMerge(outputStream, (MergeResult<RawText>) mergeResult, arrayList, charset);
    }
}
