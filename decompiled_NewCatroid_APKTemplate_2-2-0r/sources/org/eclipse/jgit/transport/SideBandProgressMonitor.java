package org.eclipse.jgit.transport;

import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.jgit.lib.BatchingProgressMonitor;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: loaded from: classes13.dex */
class SideBandProgressMonitor extends BatchingProgressMonitor {
    private final OutputStream out;
    private boolean write = true;

    SideBandProgressMonitor(OutputStream outputStream) {
        this.out = outputStream;
    }

    @Override // org.eclipse.jgit.lib.BatchingProgressMonitor
    protected void onUpdate(String str, int i) {
        StringBuilder sb = new StringBuilder();
        format(sb, str, i);
        sb.append("   \r");
        send(sb);
    }

    @Override // org.eclipse.jgit.lib.BatchingProgressMonitor
    protected void onEndTask(String str, int i) {
        StringBuilder sb = new StringBuilder();
        format(sb, str, i);
        sb.append(", done\n");
        send(sb);
    }

    private void format(StringBuilder sb, String str, int i) {
        sb.append(str);
        sb.append(": ");
        sb.append(i);
    }

    @Override // org.eclipse.jgit.lib.BatchingProgressMonitor
    protected void onUpdate(String str, int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        format(sb, str, i, i2, i3);
        sb.append("   \r");
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
        sb.append(str);
        sb.append(": ");
        if (i3 < 100) {
            sb.append(StringUtil.SPACE);
        }
        if (i3 < 10) {
            sb.append(StringUtil.SPACE);
        }
        sb.append(i3);
        sb.append("% (");
        sb.append(i);
        sb.append("/");
        sb.append(i2);
        sb.append(Constant.AFTER_QUTO);
    }

    private void send(StringBuilder sb) {
        if (this.write) {
            try {
                this.out.write(Constants.encode(sb.toString()));
                this.out.flush();
            } catch (IOException unused) {
                this.write = false;
            }
        }
    }
}
