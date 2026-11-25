package io.github.rosemoe.sora.langs.java;

import android.os.Bundle;
import io.github.rosemoe.sora.lang.analysis.AsyncIncrementalAnalyzeManager;
import io.github.rosemoe.sora.lang.analysis.IncrementalAnalyzeManager;
import io.github.rosemoe.sora.lang.analysis.StyleReceiver;
import io.github.rosemoe.sora.lang.brackets.SimpleBracketsCollector;
import io.github.rosemoe.sora.lang.completion.IdentifierAutoComplete;
import io.github.rosemoe.sora.lang.styling.CodeBlock;
import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.SpanFactory;
import io.github.rosemoe.sora.lang.styling.TextStyle;
import io.github.rosemoe.sora.lang.styling.color.EditorColor;
import io.github.rosemoe.sora.lang.styling.span.SpanClickableUrl;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentReference;
import io.github.rosemoe.sora.util.ArrayList;
import io.github.rosemoe.sora.util.IntPair;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes11.dex */
public class JavaIncrementalAnalyzeManager extends AsyncIncrementalAnalyzeManager<State, HighlightToken> {
    private static final int STATE_INCOMPLETE_COMMENT = 1;
    private static final int STATE_NORMAL = 0;
    private static final Pattern URL_PATTERN = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
    private final ThreadLocal<JavaTextTokenizer> tokenizerProvider = new ThreadLocal<>();
    protected IdentifierAutoComplete.SyncIdentifiers identifiers = new IdentifierAutoComplete.SyncIdentifiers();

    private synchronized JavaTextTokenizer obtainTokenizer() {
        JavaTextTokenizer javaTextTokenizer;
        javaTextTokenizer = this.tokenizerProvider.get();
        if (javaTextTokenizer == null) {
            javaTextTokenizer = new JavaTextTokenizer("");
            this.tokenizerProvider.set(javaTextTokenizer);
        }
        return javaTextTokenizer;
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AsyncIncrementalAnalyzeManager
    public List<CodeBlock> computeBlocks(Content content, AsyncIncrementalAnalyzeManager<State, HighlightToken>.CodeBlockAnalyzeDelegate codeBlockAnalyzeDelegate) {
        Stack stack = new Stack();
        ArrayList arrayList = new ArrayList();
        final SimpleBracketsCollector simpleBracketsCollector = new SimpleBracketsCollector();
        Stack stack2 = new Stack();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < content.getLineCount() && codeBlockAnalyzeDelegate.isNotCancelled(); i3++) {
            IncrementalAnalyzeManager.LineTokenizeResult<State, HighlightToken> state = getState(i3);
            int i4 = 1;
            boolean z = state.state.state == 0 || (state.state.state == 1 && state.tokens.size() > 1);
            if (state.state.hasBraces || z) {
                int i5 = 0;
                while (i5 < state.tokens.size()) {
                    HighlightToken highlightToken = state.tokens.get(i5);
                    Tokens tokens = highlightToken.token;
                    if (tokens == Tokens.LBRACE) {
                        int i6 = highlightToken.offset;
                        if (stack.isEmpty()) {
                            if (i2 > i) {
                                i = i2;
                            }
                            i2 = 0;
                        }
                        i2 += i4;
                        CodeBlock codeBlock = new CodeBlock();
                        codeBlock.startLine = i3;
                        codeBlock.startColumn = i6;
                        stack.push(codeBlock);
                    } else if (tokens == Tokens.RBRACE) {
                        int i7 = highlightToken.offset;
                        if (!stack.isEmpty()) {
                            CodeBlock codeBlock2 = (CodeBlock) stack.pop();
                            codeBlock2.endLine = i3;
                            codeBlock2.endColumn = i7;
                            if (codeBlock2.startLine != codeBlock2.endLine) {
                                arrayList.add(codeBlock2);
                            }
                        }
                    }
                    int type = getType(tokens);
                    if (type > 0) {
                        if (isStart(tokens)) {
                            stack2.push(Long.valueOf(IntPair.pack(type, content.getCharIndex(i3, highlightToken.offset))));
                        } else if (!stack2.isEmpty()) {
                            Long l = (Long) stack2.pop();
                            if (IntPair.getFirst(l.longValue()) == type) {
                                simpleBracketsCollector.add(IntPair.getSecond(l.longValue()), content.getCharIndex(i3, highlightToken.offset));
                            } else if (type == 3) {
                                while (!stack2.isEmpty()) {
                                    Long l2 = (Long) stack2.pop();
                                    if (IntPair.getFirst(l2.longValue()) == 3) {
                                        simpleBracketsCollector.add(IntPair.getSecond(l2.longValue()), content.getCharIndex(i3, highlightToken.offset));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    i5++;
                    i4 = 1;
                }
            }
        }
        if (codeBlockAnalyzeDelegate.isNotCancelled()) {
            withReceiver(new AsyncIncrementalAnalyzeManager.ReceiverConsumer() { // from class: io.github.rosemoe.sora.langs.java.JavaIncrementalAnalyzeManager$$ExternalSyntheticLambda0
                @Override // io.github.rosemoe.sora.lang.analysis.AsyncIncrementalAnalyzeManager.ReceiverConsumer
                public final void accept(StyleReceiver styleReceiver) {
                    this.f$0.lambda$computeBlocks$0(simpleBracketsCollector, styleReceiver);
                }
            });
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$computeBlocks$0(SimpleBracketsCollector simpleBracketsCollector, StyleReceiver styleReceiver) {
        styleReceiver.updateBracketProvider(this, simpleBracketsCollector);
    }

    private static int getType(Tokens tokens) {
        if (tokens == Tokens.LBRACE || tokens == Tokens.RBRACE) {
            return 3;
        }
        if (tokens == Tokens.LBRACK || tokens == Tokens.RBRACK) {
            return 2;
        }
        return (tokens == Tokens.LPAREN || tokens == Tokens.RPAREN) ? 1 : 0;
    }

    private static boolean isStart(Tokens tokens) {
        return tokens == Tokens.LBRACE || tokens == Tokens.LBRACK || tokens == Tokens.LPAREN;
    }

    @Override // io.github.rosemoe.sora.lang.analysis.IncrementalAnalyzeManager
    public State getInitialState() {
        return new State();
    }

    @Override // io.github.rosemoe.sora.lang.analysis.IncrementalAnalyzeManager
    public boolean stateEquals(State state, State state2) {
        return state.equals(state2);
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AsyncIncrementalAnalyzeManager, io.github.rosemoe.sora.lang.analysis.IncrementalAnalyzeManager
    public void onAddState(State state) {
        if (state.identifiers != null) {
            Iterator<String> it = state.identifiers.iterator();
            while (it.hasNext()) {
                this.identifiers.identifierIncrease(it.next());
            }
        }
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AsyncIncrementalAnalyzeManager, io.github.rosemoe.sora.lang.analysis.IncrementalAnalyzeManager
    public void onAbandonState(State state) {
        if (state.identifiers != null) {
            Iterator<String> it = state.identifiers.iterator();
            while (it.hasNext()) {
                this.identifiers.identifierDecrease(it.next());
            }
        }
    }

    @Override // io.github.rosemoe.sora.lang.util.BaseAnalyzeManager, io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void reset(ContentReference contentReference, Bundle bundle) {
        super.reset(contentReference, bundle);
        this.identifiers.clear();
    }

    @Override // io.github.rosemoe.sora.lang.analysis.IncrementalAnalyzeManager
    public IncrementalAnalyzeManager.LineTokenizeResult<State, HighlightToken> tokenizeLine(CharSequence charSequence, State state, int i) {
        int i2;
        ArrayList arrayList = new ArrayList();
        State state2 = new State();
        if (state.state == 0) {
            i2 = tokenizeNormal(charSequence, 0, arrayList, state2);
        } else if (state.state == 1) {
            long jTryFillIncompleteComment = tryFillIncompleteComment(charSequence, arrayList);
            i2 = IntPair.getFirst(jTryFillIncompleteComment) == 0 ? tokenizeNormal(charSequence, IntPair.getSecond(jTryFillIncompleteComment), arrayList, state2) : 1;
        } else {
            i2 = 0;
        }
        if (arrayList.isEmpty()) {
            arrayList.add(new HighlightToken(Tokens.UNKNOWN, 0));
        }
        state2.state = i2;
        return new IncrementalAnalyzeManager.LineTokenizeResult<>(state2, arrayList);
    }

    private long tryFillIncompleteComment(CharSequence charSequence, List<HighlightToken> list) {
        char c = 0;
        char c2 = 0;
        int i = 0;
        while (true) {
            if ((c == '*' && c2 == '/') || i >= charSequence.length()) {
                break;
            }
            char cCharAt = charSequence.charAt(i);
            i++;
            char c3 = c2;
            c2 = cCharAt;
            c = c3;
        }
        if (c == '*' && c2 == '/') {
            if (i < 1000) {
                detectHighlightUrls(charSequence.subSequence(0, i), 0, Tokens.LONG_COMMENT_COMPLETE, list);
            } else {
                list.add(new HighlightToken(Tokens.LONG_COMMENT_COMPLETE, 0));
            }
            return IntPair.pack(0, i);
        }
        if (i < 1000) {
            detectHighlightUrls(charSequence.subSequence(0, i), 0, Tokens.LONG_COMMENT_INCOMPLETE, list);
        } else {
            list.add(new HighlightToken(Tokens.LONG_COMMENT_INCOMPLETE, 0));
        }
        return IntPair.pack(1, i);
    }

    private int tokenizeNormal(CharSequence charSequence, int i, List<HighlightToken> list, State state) {
        JavaTextTokenizer javaTextTokenizerObtainTokenizer = obtainTokenizer();
        javaTextTokenizerObtainTokenizer.reset(charSequence);
        javaTextTokenizerObtainTokenizer.offset = i;
        while (true) {
            Tokens tokensNextToken = javaTextTokenizerObtainTokenizer.nextToken();
            if (tokensNextToken == Tokens.EOF) {
                return 0;
            }
            if (javaTextTokenizerObtainTokenizer.getTokenLength() < 1000 && (tokensNextToken == Tokens.STRING || tokensNextToken == Tokens.LONG_COMMENT_COMPLETE || tokensNextToken == Tokens.LONG_COMMENT_INCOMPLETE || tokensNextToken == Tokens.LINE_COMMENT)) {
                detectHighlightUrls(javaTextTokenizerObtainTokenizer.getTokenText(), javaTextTokenizerObtainTokenizer.offset, tokensNextToken, list);
                if (tokensNextToken == Tokens.LONG_COMMENT_INCOMPLETE) {
                    return 1;
                }
            } else {
                list.add(new HighlightToken(tokensNextToken, javaTextTokenizerObtainTokenizer.offset));
                if (tokensNextToken == Tokens.LBRACE || tokensNextToken == Tokens.RBRACE) {
                    state.hasBraces = true;
                }
                if (tokensNextToken == Tokens.IDENTIFIER) {
                    state.addIdentifier(javaTextTokenizerObtainTokenizer.getTokenText());
                }
                if (tokensNextToken == Tokens.LONG_COMMENT_INCOMPLETE) {
                    return 1;
                }
            }
        }
    }

    private void detectHighlightUrls(CharSequence charSequence, int i, Tokens tokens, List<HighlightToken> list) {
        Matcher matcher = URL_PATTERN.matcher(charSequence);
        int i2 = 0;
        while (i2 < charSequence.length() && matcher.find(i2)) {
            int iStart = matcher.start();
            int iEnd = matcher.end();
            if (iStart > i2) {
                list.add(new HighlightToken(tokens, i2 + i));
            }
            list.add(new HighlightToken(tokens, iStart + i, matcher.group()));
            i2 = iEnd;
        }
        if (i2 != charSequence.length()) {
            list.add(new HighlightToken(tokens, i + i2));
        }
    }

    /* JADX WARN: Code duplicated, block: B:32:0x007b  */
    /* JADX WARN: Code duplicated, block: B:33:0x0083  */
    @Override // io.github.rosemoe.sora.lang.analysis.IncrementalAnalyzeManager
    public List<Span> generateSpansForLine(IncrementalAnalyzeManager.LineTokenizeResult<State, HighlightToken> lineTokenizeResult) {
        Span spanObtain;
        boolean z;
        Span spanObtain2;
        boolean z2;
        int i;
        boolean z3;
        ArrayList arrayList = new ArrayList();
        List<HighlightToken> list = lineTokenizeResult.tokens;
        Tokens tokens = Tokens.UNKNOWN;
        int i2 = 0;
        boolean z4 = false;
        while (i2 < list.size()) {
            HighlightToken highlightToken = list.get(i2);
            Tokens tokens2 = highlightToken.token;
            int i3 = highlightToken.offset;
            switch (AnonymousClass1.$SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[tokens2.ordinal()]) {
                case 1:
                case 2:
                    spanObtain = SpanFactory.obtain(i3, TextStyle.makeStyle(5));
                    Span span = spanObtain;
                    z2 = z4;
                    spanObtain2 = span;
                    break;
                case 3:
                case 4:
                case 5:
                    spanObtain = SpanFactory.obtain(i3, TextStyle.makeStyle(22, 0, false, true, false, true));
                    Span span2 = spanObtain;
                    z2 = z4;
                    spanObtain2 = span2;
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                    z = false;
                    spanObtain2 = SpanFactory.obtain(i3, TextStyle.makeStyle(24, true));
                    z2 = z;
                    break;
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    spanObtain2 = SpanFactory.obtain(i3, TextStyle.makeStyle(21, 0, true, false, false));
                    z2 = true;
                    break;
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                    z = false;
                    spanObtain2 = SpanFactory.obtain(i3, TextStyle.makeStyle(21, 0, true, false, false));
                    z2 = z;
                    break;
                case 66:
                    if (z4) {
                        i = 25;
                        z3 = false;
                    } else if (tokens == Tokens.AT) {
                        z3 = z4;
                        i = 28;
                    } else {
                        Tokens tokens3 = Tokens.UNKNOWN;
                        for (int i4 = i2 + 1; i4 < list.size(); i4++) {
                            tokens3 = list.get(i4).token;
                            int i5 = AnonymousClass1.$SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[tokens3.ordinal()];
                            if (i5 != 1 && i5 != 2 && i5 != 3 && i5 != 4 && i5 != 5) {
                                if (tokens3 == Tokens.LPAREN) {
                                    boolean z5 = z4;
                                    i = 27;
                                    z3 = z5;
                                } else {
                                    i = 26;
                                    z3 = true;
                                }
                            }
                        }
                        if (tokens3 == Tokens.LPAREN) {
                            boolean z6 = z4;
                            i = 27;
                            z3 = z6;
                        } else {
                            i = 26;
                            z3 = true;
                        }
                    }
                    spanObtain2 = SpanFactory.obtain(i3, TextStyle.makeStyle(i));
                    z2 = z3;
                    break;
                default:
                    z = false;
                    if (tokens2 == Tokens.LBRACK || (tokens2 == Tokens.RBRACK && tokens == Tokens.LBRACK)) {
                        spanObtain = SpanFactory.obtain(i3, 23L);
                        Span span3 = spanObtain;
                        z2 = z4;
                        spanObtain2 = span3;
                    } else {
                        spanObtain2 = SpanFactory.obtain(i3, 23L);
                        z2 = z;
                    }
                    break;
            }
            int i6 = AnonymousClass1.$SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[tokens2.ordinal()];
            if (i6 != 1 && i6 != 2 && i6 != 3 && i6 != 4 && i6 != 5) {
                tokens = tokens2;
            }
            if (highlightToken.url != null) {
                spanObtain2.setSpanExt(2, new SpanClickableUrl(highlightToken.url));
                spanObtain2.setUnderlineColor(new EditorColor(spanObtain2.getForegroundColorId()));
            }
            arrayList.add(spanObtain2);
            i2++;
            z4 = z2;
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: io.github.rosemoe.sora.langs.java.JavaIncrementalAnalyzeManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens;

        static {
            int[] iArr = new int[Tokens.values().length];
            $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens = iArr;
            try {
                iArr[Tokens.WHITESPACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.NEWLINE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.LONG_COMMENT_INCOMPLETE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.LONG_COMMENT_COMPLETE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.LINE_COMMENT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.CHARACTER_LITERAL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.FLOATING_POINT_LITERAL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.INTEGER_LITERAL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.STRING.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.INT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.LONG.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.BOOLEAN.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.BYTE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.CHAR.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.FLOAT.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.DOUBLE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.SHORT.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.VOID.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.VAR.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.ABSTRACT.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.ASSERT.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.CLASS.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.DO.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.FINAL.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.FOR.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.IF.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.NEW.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.PUBLIC.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.PRIVATE.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.PROTECTED.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.PACKAGE.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.RETURN.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.STATIC.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.SUPER.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.SWITCH.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.ELSE.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.VOLATILE.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.SYNCHRONIZED.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.STRICTFP.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.GOTO.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.CONTINUE.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.BREAK.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.TRANSIENT.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.TRY.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.CATCH.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.FINALLY.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.WHILE.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.CASE.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.DEFAULT.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.CONST.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.ENUM.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.EXTENDS.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.IMPLEMENTS.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.IMPORT.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.INSTANCEOF.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.INTERFACE.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.NATIVE.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.THIS.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.THROW.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.THROWS.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.TRUE.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.FALSE.ordinal()] = 62;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.NULL.ordinal()] = 63;
            } catch (NoSuchFieldError unused63) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.SEALED.ordinal()] = 64;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.PERMITS.ordinal()] = 65;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$langs$java$Tokens[Tokens.IDENTIFIER.ordinal()] = 66;
            } catch (NoSuchFieldError unused66) {
            }
        }
    }

    public static class HighlightToken {
        public int offset;
        public Tokens token;
        public String url;

        public HighlightToken(Tokens tokens, int i) {
            this.token = tokens;
            this.offset = i;
        }

        public HighlightToken(Tokens tokens, int i, String str) {
            this.token = tokens;
            this.offset = i;
            this.url = str;
        }
    }
}
