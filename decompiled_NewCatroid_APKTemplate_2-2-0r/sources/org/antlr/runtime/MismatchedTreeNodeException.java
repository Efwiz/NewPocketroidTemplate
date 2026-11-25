package org.antlr.runtime;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.antlr.runtime.tree.TreeNodeStream;

/* JADX INFO: loaded from: classes12.dex */
public class MismatchedTreeNodeException extends RecognitionException {
    public int expecting;

    public MismatchedTreeNodeException() {
    }

    public MismatchedTreeNodeException(int i, TreeNodeStream treeNodeStream) {
        super(treeNodeStream);
        this.expecting = i;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "MismatchedTreeNodeException(" + getUnexpectedType() + "!=" + this.expecting + Constant.AFTER_QUTO;
    }
}
