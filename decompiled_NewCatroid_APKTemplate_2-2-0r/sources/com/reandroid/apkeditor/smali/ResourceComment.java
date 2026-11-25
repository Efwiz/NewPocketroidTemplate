package com.reandroid.apkeditor.smali;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResValue;
import com.reandroid.arsc.value.ValueType;
import java.util.HashMap;
import java.util.Map;
import name.antonsmirnov.firmata.FormatHelper;
import org.jf.baksmali.CommentProvider;

/* JADX INFO: loaded from: classes10.dex */
public class ResourceComment extends CommentProvider {
    private final Map<Integer, String> mCommentCache = new HashMap();
    private final PackageBlock packageBlock;
    private final TableBlock tableBlock;

    public ResourceComment(TableBlock tableBlock) {
        this.tableBlock = tableBlock;
        this.packageBlock = tableBlock.pickOne();
    }

    @Override // org.jf.baksmali.CommentProvider
    public String getComment(int i) {
        if (!PackageBlock.isResourceId(i)) {
            return null;
        }
        synchronized (this) {
            String str = this.mCommentCache.get(Integer.valueOf(i));
            if (str != null) {
                return str;
            }
            String strBuildComment = buildComment(i);
            if (strBuildComment != null) {
                this.mCommentCache.put(Integer.valueOf(i), strBuildComment);
            }
            return strBuildComment;
        }
    }

    private String buildComment(int i) {
        Entry entry;
        ResValue resValue;
        String strDecodeValue;
        ResourceEntry resource = this.tableBlock.getResource(i);
        if (resource == null || !resource.isDeclared()) {
            return null;
        }
        String strBuildReference = resource.buildReference(this.packageBlock, ValueType.REFERENCE);
        if (resource.getPackageBlock().getTableBlock() != this.tableBlock || "id".equals(resource.getType()) || (entry = resource.get()) == null || (resValue = entry.getResValue()) == null || (strDecodeValue = resValue.decodeValue()) == null) {
            return strBuildReference;
        }
        if (strDecodeValue.length() > 100) {
            strDecodeValue = strDecodeValue.substring(0, 100) + " ...";
        }
        return strBuildReference + " '" + replaceNewLines(strDecodeValue) + FormatHelper.QUOTE;
    }

    private String replaceNewLines(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            sb.append(escapeChar(c));
        }
        return sb.toString();
    }

    private String escapeChar(char c) {
        if (c == '\n') {
            return "\\n";
        }
        if (c == '\t') {
            return "\\t";
        }
        if (c == '\r') {
            return "\\r";
        }
        return String.valueOf(c);
    }
}
