package org.antlr.runtime;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes12.dex */
public class TokenRewriteStream extends CommonTokenStream {
    public static final String DEFAULT_PROGRAM_NAME = "default";
    public static final int MIN_TOKEN_INDEX = 0;
    public static final int PROGRAM_INIT_SIZE = 100;
    protected Map<String, Integer> lastRewriteTokenIndexes;
    protected Map<String, List<RewriteOperation>> programs;

    public class RewriteOperation {
        protected int index;
        protected int instructionIndex;
        protected Object text;

        protected RewriteOperation(int i) {
            this.index = i;
        }

        protected RewriteOperation(int i, Object obj) {
            this.index = i;
            this.text = obj;
        }

        public int execute(StringBuffer stringBuffer) {
            return this.index;
        }

        public String toString() {
            String name2 = getClass().getName();
            return "<" + name2.substring(name2.indexOf(36) + 1, name2.length()) + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + TokenRewriteStream.this.tokens.get(this.index) + ":\"" + this.text + "\">";
        }
    }

    class InsertBeforeOp extends RewriteOperation {
        public InsertBeforeOp(int i, Object obj) {
            super(i, obj);
        }

        @Override // org.antlr.runtime.TokenRewriteStream.RewriteOperation
        public int execute(StringBuffer stringBuffer) {
            stringBuffer.append(this.text);
            if (TokenRewriteStream.this.tokens.get(this.index).getType() != -1) {
                stringBuffer.append(TokenRewriteStream.this.tokens.get(this.index).getText());
            }
            return this.index + 1;
        }
    }

    class ReplaceOp extends RewriteOperation {
        protected int lastIndex;

        public ReplaceOp(int i, int i2, Object obj) {
            super(i, obj);
            this.lastIndex = i2;
        }

        @Override // org.antlr.runtime.TokenRewriteStream.RewriteOperation
        public int execute(StringBuffer stringBuffer) {
            if (this.text != null) {
                stringBuffer.append(this.text);
            }
            return this.lastIndex + 1;
        }

        @Override // org.antlr.runtime.TokenRewriteStream.RewriteOperation
        public String toString() {
            if (this.text == null) {
                return "<DeleteOp@" + TokenRewriteStream.this.tokens.get(this.index) + ".." + TokenRewriteStream.this.tokens.get(this.lastIndex) + ">";
            }
            return "<ReplaceOp@" + TokenRewriteStream.this.tokens.get(this.index) + ".." + TokenRewriteStream.this.tokens.get(this.lastIndex) + ":\"" + this.text + "\">";
        }
    }

    public TokenRewriteStream() {
        this.programs = null;
        this.lastRewriteTokenIndexes = null;
        init();
    }

    protected void init() {
        HashMap map = new HashMap();
        this.programs = map;
        map.put("default", new ArrayList(100));
        this.lastRewriteTokenIndexes = new HashMap();
    }

    public TokenRewriteStream(TokenSource tokenSource) {
        super(tokenSource);
        this.programs = null;
        this.lastRewriteTokenIndexes = null;
        init();
    }

    public TokenRewriteStream(TokenSource tokenSource, int i) {
        super(tokenSource, i);
        this.programs = null;
        this.lastRewriteTokenIndexes = null;
        init();
    }

    public void rollback(int i) {
        rollback("default", i);
    }

    public void rollback(String str, int i) {
        List<RewriteOperation> list = this.programs.get(str);
        if (list != null) {
            this.programs.put(str, list.subList(0, i));
        }
    }

    public void deleteProgram() {
        deleteProgram("default");
    }

    public void deleteProgram(String str) {
        rollback(str, 0);
    }

    public void insertAfter(Token token, Object obj) {
        insertAfter("default", token, obj);
    }

    public void insertAfter(int i, Object obj) {
        insertAfter("default", i, obj);
    }

    public void insertAfter(String str, Token token, Object obj) {
        insertAfter(str, token.getTokenIndex(), obj);
    }

    public void insertAfter(String str, int i, Object obj) {
        insertBefore(str, i + 1, obj);
    }

    public void insertBefore(Token token, Object obj) {
        insertBefore("default", token, obj);
    }

    public void insertBefore(int i, Object obj) {
        insertBefore("default", i, obj);
    }

    public void insertBefore(String str, Token token, Object obj) {
        insertBefore(str, token.getTokenIndex(), obj);
    }

    public void insertBefore(String str, int i, Object obj) {
        InsertBeforeOp insertBeforeOp = new InsertBeforeOp(i, obj);
        List<RewriteOperation> program = getProgram(str);
        insertBeforeOp.instructionIndex = program.size();
        program.add(insertBeforeOp);
    }

    public void replace(int i, Object obj) {
        replace("default", i, i, obj);
    }

    public void replace(int i, int i2, Object obj) {
        replace("default", i, i2, obj);
    }

    public void replace(Token token, Object obj) {
        replace("default", token, token, obj);
    }

    public void replace(Token token, Token token2, Object obj) {
        replace("default", token, token2, obj);
    }

    public void replace(String str, int i, int i2, Object obj) {
        if (i > i2 || i < 0 || i2 < 0 || i2 >= this.tokens.size()) {
            throw new IllegalArgumentException("replace: range invalid: " + i + ".." + i2 + "(size=" + this.tokens.size() + Constant.AFTER_QUTO);
        }
        ReplaceOp replaceOp = new ReplaceOp(i, i2, obj);
        List<RewriteOperation> program = getProgram(str);
        replaceOp.instructionIndex = program.size();
        program.add(replaceOp);
    }

    public void replace(String str, Token token, Token token2, Object obj) {
        replace(str, token.getTokenIndex(), token2.getTokenIndex(), obj);
    }

    public void delete(int i) {
        delete("default", i, i);
    }

    public void delete(int i, int i2) {
        delete("default", i, i2);
    }

    public void delete(Token token) {
        delete("default", token, token);
    }

    public void delete(Token token, Token token2) {
        delete("default", token, token2);
    }

    public void delete(String str, int i, int i2) {
        replace(str, i, i2, (Object) null);
    }

    public void delete(String str, Token token, Token token2) {
        replace(str, token, token2, (Object) null);
    }

    public int getLastRewriteTokenIndex() {
        return getLastRewriteTokenIndex("default");
    }

    protected int getLastRewriteTokenIndex(String str) {
        Integer num = this.lastRewriteTokenIndexes.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    protected void setLastRewriteTokenIndex(String str, int i) {
        this.lastRewriteTokenIndexes.put(str, Integer.valueOf(i));
    }

    protected List<RewriteOperation> getProgram(String str) {
        List<RewriteOperation> list = this.programs.get(str);
        return list == null ? initializeProgram(str) : list;
    }

    private List<RewriteOperation> initializeProgram(String str) {
        ArrayList arrayList = new ArrayList(100);
        this.programs.put(str, arrayList);
        return arrayList;
    }

    public String toOriginalString() {
        fill();
        return toOriginalString(0, size() - 1);
    }

    public String toOriginalString(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        while (i >= 0 && i <= i2 && i < this.tokens.size()) {
            if (get(i).getType() != -1) {
                sb.append(get(i).getText());
            }
            i++;
        }
        return sb.toString();
    }

    @Override // org.antlr.runtime.BufferedTokenStream
    public String toString() {
        fill();
        return toString(0, size() - 1);
    }

    public String toString(String str) {
        fill();
        return toString(str, 0, size() - 1);
    }

    @Override // org.antlr.runtime.BufferedTokenStream, org.antlr.runtime.TokenStream
    public String toString(int i, int i2) {
        return toString("default", i, i2);
    }

    public String toString(String str, int i, int i2) {
        List<RewriteOperation> list = this.programs.get(str);
        if (i2 > this.tokens.size() - 1) {
            i2 = this.tokens.size() - 1;
        }
        if (i < 0) {
            i = 0;
        }
        if (list == null || list.isEmpty()) {
            return toOriginalString(i, i2);
        }
        StringBuffer stringBuffer = new StringBuffer();
        Map<Integer, ? extends RewriteOperation> mapReduceToSingleOperationPerIndex = reduceToSingleOperationPerIndex(list);
        while (i <= i2 && i < this.tokens.size()) {
            RewriteOperation rewriteOperation = mapReduceToSingleOperationPerIndex.get(Integer.valueOf(i));
            mapReduceToSingleOperationPerIndex.remove(Integer.valueOf(i));
            Token token = this.tokens.get(i);
            if (rewriteOperation == null) {
                if (token.getType() != -1) {
                    stringBuffer.append(token.getText());
                }
                i++;
            } else {
                i = rewriteOperation.execute(stringBuffer);
            }
        }
        if (i2 == this.tokens.size() - 1) {
            for (RewriteOperation rewriteOperation2 : mapReduceToSingleOperationPerIndex.values()) {
                if (rewriteOperation2.index >= this.tokens.size() - 1) {
                    stringBuffer.append(rewriteOperation2.text);
                }
            }
        }
        return stringBuffer.toString();
    }

    protected Map<Integer, ? extends RewriteOperation> reduceToSingleOperationPerIndex(List<? extends RewriteOperation> list) {
        for (int i = 0; i < list.size(); i++) {
            RewriteOperation rewriteOperation = list.get(i);
            if (rewriteOperation != null && (rewriteOperation instanceof ReplaceOp)) {
                ReplaceOp replaceOp = (ReplaceOp) list.get(i);
                List kindOfOps = getKindOfOps(list, InsertBeforeOp.class, i);
                for (int i2 = 0; i2 < kindOfOps.size(); i2++) {
                    InsertBeforeOp insertBeforeOp = (InsertBeforeOp) kindOfOps.get(i2);
                    if (insertBeforeOp.index == replaceOp.index) {
                        list.set(insertBeforeOp.instructionIndex, null);
                        replaceOp.text = insertBeforeOp.text.toString() + (replaceOp.text != null ? replaceOp.text.toString() : "");
                    } else if (insertBeforeOp.index > replaceOp.index && insertBeforeOp.index <= replaceOp.lastIndex) {
                        list.set(insertBeforeOp.instructionIndex, null);
                    }
                }
                List kindOfOps2 = getKindOfOps(list, ReplaceOp.class, i);
                for (int i3 = 0; i3 < kindOfOps2.size(); i3++) {
                    ReplaceOp replaceOp2 = (ReplaceOp) kindOfOps2.get(i3);
                    if (replaceOp2.index >= replaceOp.index && replaceOp2.lastIndex <= replaceOp.lastIndex) {
                        list.set(replaceOp2.instructionIndex, null);
                    } else {
                        boolean z = replaceOp2.lastIndex < replaceOp.index || replaceOp2.index > replaceOp.lastIndex;
                        boolean z2 = replaceOp2.index == replaceOp.index && replaceOp2.lastIndex == replaceOp.lastIndex;
                        if (replaceOp2.text == null && replaceOp.text == null && !z) {
                            list.set(replaceOp2.instructionIndex, null);
                            replaceOp.index = Math.min(replaceOp2.index, replaceOp.index);
                            replaceOp.lastIndex = Math.max(replaceOp2.lastIndex, replaceOp.lastIndex);
                            System.out.println("new rop " + replaceOp);
                        } else if (!z && !z2) {
                            throw new IllegalArgumentException("replace op boundaries of " + replaceOp + " overlap with previous " + replaceOp2);
                        }
                    }
                }
            }
        }
        for (int i4 = 0; i4 < list.size(); i4++) {
            RewriteOperation rewriteOperation2 = list.get(i4);
            if (rewriteOperation2 != null && (rewriteOperation2 instanceof InsertBeforeOp)) {
                InsertBeforeOp insertBeforeOp2 = (InsertBeforeOp) list.get(i4);
                List kindOfOps3 = getKindOfOps(list, InsertBeforeOp.class, i4);
                for (int i5 = 0; i5 < kindOfOps3.size(); i5++) {
                    InsertBeforeOp insertBeforeOp3 = (InsertBeforeOp) kindOfOps3.get(i5);
                    if (insertBeforeOp3.index == insertBeforeOp2.index) {
                        insertBeforeOp2.text = catOpText(insertBeforeOp2.text, insertBeforeOp3.text);
                        list.set(insertBeforeOp3.instructionIndex, null);
                    }
                }
                List kindOfOps4 = getKindOfOps(list, ReplaceOp.class, i4);
                for (int i6 = 0; i6 < kindOfOps4.size(); i6++) {
                    ReplaceOp replaceOp3 = (ReplaceOp) kindOfOps4.get(i6);
                    if (insertBeforeOp2.index == replaceOp3.index) {
                        replaceOp3.text = catOpText(insertBeforeOp2.text, replaceOp3.text);
                        list.set(i4, null);
                    } else if (insertBeforeOp2.index >= replaceOp3.index && insertBeforeOp2.index <= replaceOp3.lastIndex) {
                        throw new IllegalArgumentException("insert op " + insertBeforeOp2 + " within boundaries of previous " + replaceOp3);
                    }
                }
            }
        }
        HashMap map = new HashMap();
        for (int i7 = 0; i7 < list.size(); i7++) {
            RewriteOperation rewriteOperation3 = list.get(i7);
            if (rewriteOperation3 != null) {
                if (map.get(Integer.valueOf(rewriteOperation3.index)) != null) {
                    throw new Error("should only be one op per index");
                }
                map.put(Integer.valueOf(rewriteOperation3.index), rewriteOperation3);
            }
        }
        return map;
    }

    protected String catOpText(Object obj, Object obj2) {
        String string = "";
        String string2 = obj != null ? obj.toString() : "";
        if (obj2 != null) {
            string = obj2.toString();
        }
        return string2 + string;
    }

    protected <T extends RewriteOperation> List<? extends T> getKindOfOps(List<? extends RewriteOperation> list, Class<T> cls) {
        return getKindOfOps(list, cls, list.size());
    }

    protected <T extends RewriteOperation> List<? extends T> getKindOfOps(List<? extends RewriteOperation> list, Class<T> cls, int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i && i2 < list.size(); i2++) {
            RewriteOperation rewriteOperation = list.get(i2);
            if (rewriteOperation != null && cls.isInstance(rewriteOperation)) {
                arrayList.add(cls.cast(rewriteOperation));
            }
        }
        return arrayList;
    }

    public String toDebugString() {
        return toDebugString(0, size() - 1);
    }

    public String toDebugString(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        while (i >= 0 && i <= i2 && i < this.tokens.size()) {
            sb.append(get(i));
            i++;
        }
        return sb.toString();
    }
}
