package com.reandroid.dex.smali.formatters;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResValue;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes11.dex */
public interface ResourceIdComment extends SmaliComment {
    void writeComment(SmaliWriter smaliWriter, int i) throws IOException;

    public static class ResourceTableComment implements ResourceIdComment {
        private final PackageBlock packageBlock;
        private final TableBlock tableBlock;

        public ResourceTableComment(PackageBlock packageBlock) {
            this.packageBlock = packageBlock;
            this.tableBlock = packageBlock.getTableBlock();
        }

        @Override // com.reandroid.dex.smali.formatters.ResourceIdComment
        public void writeComment(SmaliWriter smaliWriter, int i) {
            String strBuildComment;
            if (PackageBlock.isResourceId(i) && (strBuildComment = buildComment(i)) != null) {
                smaliWriter.appendComment(strBuildComment);
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
            int length = str.length();
            for (int i = 0; i < length; i++) {
                sb.append(escapeChar(str.charAt(i)));
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
}
