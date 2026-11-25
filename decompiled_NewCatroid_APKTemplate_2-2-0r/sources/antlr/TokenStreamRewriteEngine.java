package antlr;

import antlr.ASdebug.ASDebugStream;
import antlr.ASdebug.IASDebugStream;
import antlr.ASdebug.TokenOffsetInfo;
import antlr.collections.impl.BitSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes.dex */
public class TokenStreamRewriteEngine implements TokenStream, IASDebugStream {
    public static final String DEFAULT_PROGRAM_NAME = "default";
    public static final int MIN_TOKEN_INDEX = 0;
    public static final int PROGRAM_INIT_SIZE = 100;
    protected BitSet discardMask;
    protected int index;
    protected Map lastRewriteTokenIndexes;
    protected Map programs;
    protected TokenStream stream;
    protected List tokens;

    static class RewriteOperation {
        protected int index;
        protected String text;

        protected RewriteOperation(int i, String str) {
            this.index = i;
            this.text = str;
        }

        public int execute(StringBuffer stringBuffer) {
            return this.index;
        }

        public String toString() {
            String name2 = getClass().getName();
            return new StringBuffer().append(name2.substring(name2.indexOf(36) + 1, name2.length())).append(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT).append(this.index).append('\"').append(this.text).append('\"').toString();
        }
    }

    static class InsertBeforeOp extends RewriteOperation {
        public InsertBeforeOp(int i, String str) {
            super(i, str);
        }

        @Override // antlr.TokenStreamRewriteEngine.RewriteOperation
        public int execute(StringBuffer stringBuffer) {
            stringBuffer.append(this.text);
            return this.index;
        }
    }

    static class ReplaceOp extends RewriteOperation {
        protected int lastIndex;

        public ReplaceOp(int i, int i2, String str) {
            super(i, str);
            this.lastIndex = i2;
        }

        @Override // antlr.TokenStreamRewriteEngine.RewriteOperation
        public int execute(StringBuffer stringBuffer) {
            if (this.text != null) {
                stringBuffer.append(this.text);
            }
            return this.lastIndex + 1;
        }
    }

    static class DeleteOp extends ReplaceOp {
        public DeleteOp(int i, int i2) {
            super(i, i2, null);
        }
    }

    public TokenStreamRewriteEngine(TokenStream tokenStream) {
        this(tokenStream, 1000);
    }

    public TokenStreamRewriteEngine(TokenStream tokenStream, int i) {
        this.programs = null;
        this.lastRewriteTokenIndexes = null;
        this.index = 0;
        this.discardMask = new BitSet();
        this.stream = tokenStream;
        this.tokens = new ArrayList(i);
        HashMap map = new HashMap();
        this.programs = map;
        map.put("default", new ArrayList(100));
        this.lastRewriteTokenIndexes = new HashMap();
    }

    @Override // antlr.TokenStream
    public Token nextToken() throws TokenStreamException {
        TokenWithIndex tokenWithIndex;
        do {
            tokenWithIndex = (TokenWithIndex) this.stream.nextToken();
            if (tokenWithIndex != null) {
                tokenWithIndex.setIndex(this.index);
                if (tokenWithIndex.getType() != 1) {
                    this.tokens.add(tokenWithIndex);
                }
                this.index++;
            }
            if (tokenWithIndex == null) {
                break;
            }
        } while (this.discardMask.member(tokenWithIndex.getType()));
        return tokenWithIndex;
    }

    public void rollback(int i) {
        rollback("default", i);
    }

    public void rollback(String str, int i) {
        List list = (List) this.programs.get(str);
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

    protected void addToSortedRewriteList(RewriteOperation rewriteOperation) {
        addToSortedRewriteList("default", rewriteOperation);
    }

    protected void addToSortedRewriteList(String str, RewriteOperation rewriteOperation) {
        List program = getProgram(str);
        int iBinarySearch = Collections.binarySearch(program, rewriteOperation, new Comparator() { // from class: antlr.TokenStreamRewriteEngine.1
            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                RewriteOperation rewriteOperation2 = (RewriteOperation) obj;
                RewriteOperation rewriteOperation3 = (RewriteOperation) obj2;
                if (rewriteOperation2.index < rewriteOperation3.index) {
                    return -1;
                }
                return rewriteOperation2.index > rewriteOperation3.index ? 1 : 0;
            }
        });
        if (iBinarySearch >= 0) {
            while (iBinarySearch >= 0 && ((RewriteOperation) program.get(iBinarySearch)).index >= rewriteOperation.index) {
                iBinarySearch--;
            }
            int i = iBinarySearch + 1;
            if (rewriteOperation instanceof ReplaceOp) {
                int i2 = i;
                while (i2 < program.size()) {
                    RewriteOperation rewriteOperation2 = (RewriteOperation) program.get(i);
                    if (rewriteOperation2.index != rewriteOperation.index) {
                        break;
                    }
                    if (rewriteOperation2 instanceof ReplaceOp) {
                        program.set(i, rewriteOperation);
                        return;
                    }
                    i2++;
                }
                program.add(i2, rewriteOperation);
                return;
            }
            program.add(i, rewriteOperation);
            return;
        }
        program.add((-iBinarySearch) - 1, rewriteOperation);
    }

    public void insertAfter(Token token, String str) {
        insertAfter("default", token, str);
    }

    public void insertAfter(int i, String str) {
        insertAfter("default", i, str);
    }

    public void insertAfter(String str, Token token, String str2) {
        insertAfter(str, ((TokenWithIndex) token).getIndex(), str2);
    }

    public void insertAfter(String str, int i, String str2) {
        insertBefore(str, i + 1, str2);
    }

    public void insertBefore(Token token, String str) {
        insertBefore("default", token, str);
    }

    public void insertBefore(int i, String str) {
        insertBefore("default", i, str);
    }

    public void insertBefore(String str, Token token, String str2) {
        insertBefore(str, ((TokenWithIndex) token).getIndex(), str2);
    }

    public void insertBefore(String str, int i, String str2) {
        addToSortedRewriteList(str, new InsertBeforeOp(i, str2));
    }

    public void replace(int i, String str) {
        replace("default", i, i, str);
    }

    public void replace(int i, int i2, String str) {
        replace("default", i, i2, str);
    }

    public void replace(Token token, String str) {
        replace("default", token, token, str);
    }

    public void replace(Token token, Token token2, String str) {
        replace("default", token, token2, str);
    }

    public void replace(String str, int i, int i2, String str2) {
        addToSortedRewriteList(new ReplaceOp(i, i2, str2));
    }

    public void replace(String str, Token token, Token token2, String str2) {
        replace(str, ((TokenWithIndex) token).getIndex(), ((TokenWithIndex) token2).getIndex(), str2);
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
        replace(str, i, i2, (String) null);
    }

    public void delete(String str, Token token, Token token2) {
        replace(str, token, token2, (String) null);
    }

    public void discard(int i) {
        this.discardMask.add(i);
    }

    public TokenWithIndex getToken(int i) {
        return (TokenWithIndex) this.tokens.get(i);
    }

    public int getTokenStreamSize() {
        return this.tokens.size();
    }

    public String toOriginalString() {
        return toOriginalString(0, getTokenStreamSize() - 1);
    }

    public String toOriginalString(int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        while (i >= 0 && i <= i2 && i < this.tokens.size()) {
            stringBuffer.append(getToken(i).getText());
            i++;
        }
        return stringBuffer.toString();
    }

    public String toString() {
        return toString(0, getTokenStreamSize() - 1);
    }

    public String toString(String str) {
        return toString(str, 0, getTokenStreamSize() - 1);
    }

    public String toString(int i, int i2) {
        return toString("default", i, i2);
    }

    public String toString(String str, int i, int i2) {
        List list = (List) this.programs.get(str);
        if (list == null || list.size() == 0) {
            return toOriginalString(i, i2);
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i3 = 0;
        while (i >= 0 && i <= i2 && i < this.tokens.size()) {
            if (i3 < list.size()) {
                RewriteOperation rewriteOperation = (RewriteOperation) list.get(i3);
                while (rewriteOperation.index < i && i3 < list.size()) {
                    i3++;
                    if (i3 < list.size()) {
                        rewriteOperation = (RewriteOperation) list.get(i3);
                    }
                }
                while (i == rewriteOperation.index && i3 < list.size()) {
                    i = rewriteOperation.execute(stringBuffer);
                    i3++;
                    if (i3 < list.size()) {
                        rewriteOperation = (RewriteOperation) list.get(i3);
                    }
                }
            }
            if (i <= i2) {
                stringBuffer.append(getToken(i).getText());
                i++;
            }
        }
        while (i3 < list.size()) {
            RewriteOperation rewriteOperation2 = (RewriteOperation) list.get(i3);
            if (rewriteOperation2.index >= size()) {
                rewriteOperation2.execute(stringBuffer);
            }
            i3++;
        }
        return stringBuffer.toString();
    }

    public String toDebugString() {
        return toDebugString(0, getTokenStreamSize() - 1);
    }

    public String toDebugString(int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        while (i >= 0 && i <= i2 && i < this.tokens.size()) {
            stringBuffer.append(getToken(i));
            i++;
        }
        return stringBuffer.toString();
    }

    public int getLastRewriteTokenIndex() {
        return getLastRewriteTokenIndex("default");
    }

    protected int getLastRewriteTokenIndex(String str) {
        Integer num = (Integer) this.lastRewriteTokenIndexes.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    protected void setLastRewriteTokenIndex(String str, int i) {
        this.lastRewriteTokenIndexes.put(str, new Integer(i));
    }

    protected List getProgram(String str) {
        List list = (List) this.programs.get(str);
        return list == null ? initializeProgram(str) : list;
    }

    private List initializeProgram(String str) {
        ArrayList arrayList = new ArrayList(100);
        this.programs.put(str, arrayList);
        return arrayList;
    }

    public int size() {
        return this.tokens.size();
    }

    public int index() {
        return this.index;
    }

    @Override // antlr.ASdebug.IASDebugStream
    public String getEntireText() {
        return ASDebugStream.getEntireText(this.stream);
    }

    @Override // antlr.ASdebug.IASDebugStream
    public TokenOffsetInfo getOffsetInfo(Token token) {
        return ASDebugStream.getOffsetInfo(this.stream, token);
    }
}
