package org.eclipse.jgit.lib;

import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes13.dex */
public class TextProgressMonitor extends BatchingProgressMonitor {
    private final Writer out;
    private boolean write;

    public TextProgressMonitor() {
        this(new PrintWriter(new OutputStreamWriter(System.err, StandardCharsets.UTF_8)));
    }

    public TextProgressMonitor(Writer writer) {
        this.out = writer;
        this.write = true;
    }

    @Override // org.eclipse.jgit.lib.BatchingProgressMonitor
    protected void onUpdate(String str, int i) {
        StringBuilder sb = new StringBuilder();
        format(sb, str, i);
        send(sb);
    }

    @Override // org.eclipse.jgit.lib.BatchingProgressMonitor
    protected void onEndTask(String str, int i) {
        StringBuilder sb = new StringBuilder();
        format(sb, str, i);
        sb.append("\n");
        send(sb);
    }

    private void format(StringBuilder sb, String str, int i) {
        sb.append("\r");
        sb.append(str);
        sb.append(": ");
        while (sb.length() < 25) {
            sb.append(StringUtil.SPACE);
        }
        sb.append(i);
    }

    @Override // org.eclipse.jgit.lib.BatchingProgressMonitor
    protected void onUpdate(String str, int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        format(sb, str, i, i2, i3);
        send(sb);
    }

    @Override // org.eclipse.jgit.lib.BatchingProgressMonitor
    protected void onEndTask(String str, int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        format(sb, str, i, i2, i3);
        sb.append("\n");
        send(sb);
    }

    private void format(StringBuilder sb, String str, int i, int i2, int i3) {
        sb.append("\r");
        sb.append(str);
        sb.append(": ");
        while (sb.length() < 25) {
            sb.append(StringUtil.SPACE);
        }
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i);
        while (strValueOf2.length() < strValueOf.length()) {
            strValueOf2 = " " + strValueOf2;
        }
        if (i3 < 100) {
            sb.append(StringUtil.SPACE);
        }
        if (i3 < 10) {
            sb.append(StringUtil.SPACE);
        }
        sb.append(i3);
        sb.append("% (");
        sb.append(strValueOf2);
        sb.append("/");
        sb.append(strValueOf);
        sb.append(Constant.AFTER_QUTO);
    }

    private void send(StringBuilder sb) {
        if (this.write) {
            try {
                this.out.write(sb.toString());
                this.out.flush();
            } catch (IOException unused) {
                this.write = false;
            }
        }
    }
}
