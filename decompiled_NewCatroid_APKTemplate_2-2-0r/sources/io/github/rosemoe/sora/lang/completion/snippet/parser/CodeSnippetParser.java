package io.github.rosemoe.sora.lang.completion.snippet.parser;

import io.github.rosemoe.sora.lang.completion.snippet.CodeSnippet;
import io.github.rosemoe.sora.lang.completion.snippet.ConditionalFormat;
import io.github.rosemoe.sora.lang.completion.snippet.FormatString;
import io.github.rosemoe.sora.lang.completion.snippet.NextUpperCaseFormat;
import io.github.rosemoe.sora.lang.completion.snippet.NoFormat;
import io.github.rosemoe.sora.lang.completion.snippet.PlaceHolderElement;
import io.github.rosemoe.sora.lang.completion.snippet.PlaceholderDefinition;
import io.github.rosemoe.sora.lang.completion.snippet.PlainPlaceholderElement;
import io.github.rosemoe.sora.lang.completion.snippet.Transform;
import io.github.rosemoe.sora.lang.completion.snippet.VariableItem;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: loaded from: classes11.dex */
public class CodeSnippetParser {
    private final CodeSnippet.Builder builder;
    private final String src;
    private Token token;
    private final CodeSnippetTokenizer tokenizer;

    private CodeSnippetParser(String str, List<PlaceholderDefinition> list) {
        this.src = str;
        this.builder = new CodeSnippet.Builder(list);
        this.tokenizer = new CodeSnippetTokenizer(str);
    }

    private void next() {
        if (this.token.type == TokenType.EOF) {
            return;
        }
        this.token = this.tokenizer.nextToken();
    }

    private boolean accept(TokenType tokenType) {
        if (this.token.type != tokenType) {
            return false;
        }
        next();
        return true;
    }

    private String _accept(TokenType tokenType) {
        if (this.token.type != tokenType) {
            return null;
        }
        String strSubstring = this.src.substring(this.token.index, this.token.index + this.token.length);
        next();
        return strSubstring;
    }

    private boolean accept(TokenType... tokenTypeArr) {
        for (TokenType tokenType : tokenTypeArr) {
            if (this.token.type == tokenType) {
                next();
                return true;
            }
        }
        return false;
    }

    private String _accept(TokenType... tokenTypeArr) {
        if (tokenTypeArr.length == 0) {
            String strSubstring = this.src.substring(this.token.index, this.token.index + this.token.length);
            next();
            return strSubstring;
        }
        for (TokenType tokenType : tokenTypeArr) {
            if (this.token.type == tokenType) {
                String strSubstring2 = this.src.substring(this.token.index, this.token.index + this.token.length);
                next();
                return strSubstring2;
            }
        }
        return null;
    }

    private void backTo(Token token) {
        this.tokenizer.moveTo(token.index + token.length);
        this.token = token;
    }

    private void parse() {
        this.token = this.tokenizer.nextToken();
        while (parseInternal()) {
        }
    }

    private boolean parseInternal() {
        return parseEscaped() || parseTabStopOrVariableName() || parseComplexVariable() || parseComplexPlaceholder() || parseInterpolatedShell() || parseAnything();
    }

    private boolean parseEscaped() {
        if (!accept(TokenType.Backslash)) {
            return false;
        }
        String str_accept = _accept(TokenType.CurlyClose, TokenType.Dollar, TokenType.Backslash, TokenType.Backtick);
        if (str_accept == null) {
            str_accept = "\\";
        }
        this.builder.addPlainText(str_accept);
        return true;
    }

    private boolean parseInterpolatedShell() {
        Token token = this.token;
        if (accept(TokenType.Backtick)) {
            StringBuilder sb = new StringBuilder();
            while (!accept(TokenType.Backtick)) {
                if (accept(TokenType.Backslash)) {
                    if (accept(TokenType.Backtick)) {
                        sb.append('`');
                    } else {
                        sb.append('\\');
                    }
                } else {
                    if (this.token.type == TokenType.EOF) {
                        backTo(token);
                        return false;
                    }
                    sb.append(_accept(new TokenType[0]));
                }
            }
            this.builder.addInterpolatedShell(sb.toString());
            return true;
        }
        backTo(this.token);
        return false;
    }

    private boolean parseTabStopOrVariableName() {
        Token token = this.token;
        if (accept(TokenType.Dollar)) {
            String str_accept = _accept(TokenType.Int);
            if (str_accept != null) {
                this.builder.addPlaceholder(Integer.parseInt(str_accept));
                return true;
            }
            String str_accept2 = _accept(TokenType.VariableName);
            if (str_accept2 != null) {
                this.builder.addVariable(str_accept2, (String) null);
                return true;
            }
            backTo(token);
        }
        return false;
    }

    private boolean parseComplexVariable() {
        VariableItem variableItem_parseComplexVariable = _parseComplexVariable();
        if (variableItem_parseComplexVariable != null) {
            this.builder.addVariable(variableItem_parseComplexVariable);
        }
        return variableItem_parseComplexVariable != null;
    }

    private VariableItem _parseComplexVariable() {
        String str_accept;
        Token token = this.token;
        if (accept(TokenType.Dollar) && accept(TokenType.CurlyOpen) && (str_accept = _accept(TokenType.VariableName)) != null) {
            if (accept(TokenType.Colon)) {
                StringBuilder sb = new StringBuilder();
                while (!accept(TokenType.CurlyClose)) {
                    if (accept(TokenType.Backslash)) {
                        String str_accept2 = _accept(TokenType.Backslash, TokenType.Dollar, TokenType.CurlyClose);
                        if (str_accept2 != null) {
                            sb.append(str_accept2);
                        } else {
                            sb.append('\\');
                        }
                    } else {
                        if (this.token.type == TokenType.EOF) {
                            backTo(token);
                            return null;
                        }
                        sb.append((CharSequence) this.src, this.token.index, this.token.index + this.token.length);
                        next();
                    }
                }
                return new VariableItem(-1, str_accept, sb.toString());
            }
            if (accept(TokenType.Forwardslash)) {
                Transform transform = new Transform();
                if (parseTransform(transform)) {
                    return new VariableItem(-1, str_accept, null, transform);
                }
                backTo(token);
                return null;
            }
            if (accept(TokenType.CurlyClose)) {
                return new VariableItem(-1, str_accept, "");
            }
            backTo(token);
            return null;
        }
        backTo(token);
        return null;
    }

    private boolean parseComplexPlaceholder() {
        String str_accept;
        Token token = this.token;
        if (accept(TokenType.Dollar) && accept(TokenType.CurlyOpen) && (str_accept = _accept(TokenType.Int)) != null) {
            if (accept(TokenType.Colon)) {
                ArrayList arrayList = new ArrayList();
                while (!accept(TokenType.CurlyClose)) {
                    if (accept(TokenType.Backslash)) {
                        String str_accept2 = _accept(TokenType.Backslash, TokenType.Dollar, TokenType.CurlyClose);
                        if (str_accept2 == null) {
                            str_accept2 = "\\";
                        }
                        appendPlaceholderElement(arrayList, str_accept2);
                    } else {
                        if (this.token.type == TokenType.EOF) {
                            backTo(token);
                            return false;
                        }
                        String simpleVariableName = parseSimpleVariableName();
                        if (simpleVariableName != null) {
                            arrayList.add(new VariableItem(this.token.index, simpleVariableName, ""));
                        } else {
                            VariableItem variableItem_parseComplexVariable = _parseComplexVariable();
                            if (variableItem_parseComplexVariable != null) {
                                variableItem_parseComplexVariable.setIndex(this.token.index);
                                arrayList.add(variableItem_parseComplexVariable);
                            } else {
                                appendPlaceholderElement(arrayList, this.src.substring(this.token.index, this.token.index + this.token.length));
                                next();
                            }
                        }
                    }
                }
                this.builder.addComplexPlaceholder(Integer.parseInt(str_accept), arrayList);
            } else {
                if (accept(TokenType.Pipe)) {
                    ArrayList arrayList2 = new ArrayList();
                    while (parseChoiceElement(arrayList2)) {
                        if (!accept(TokenType.Comma)) {
                            if (!accept(TokenType.Pipe) || !accept(TokenType.CurlyClose)) {
                                break;
                                break;
                            }
                            this.builder.addPlaceholder(Integer.parseInt(str_accept), arrayList2);
                            return true;
                        }
                    }
                    backTo(token);
                    return false;
                }
                if (accept(TokenType.Forwardslash)) {
                    Transform transform = new Transform();
                    if (parseTransform(transform)) {
                        this.builder.addPlaceholder(Integer.parseInt(str_accept), transform);
                        return true;
                    }
                    backTo(token);
                    return false;
                }
                if (accept(TokenType.CurlyClose)) {
                    this.builder.addPlaceholder(Integer.parseInt(str_accept));
                } else {
                    backTo(token);
                    return false;
                }
            }
            return true;
        }
        backTo(token);
        return false;
    }

    private static void appendPlaceholderElement(ArrayList<PlaceHolderElement> arrayList, String str) {
        if (!arrayList.isEmpty() && (arrayList.get(arrayList.size() - 1) instanceof PlainPlaceholderElement)) {
            PlainPlaceholderElement plainPlaceholderElement = (PlainPlaceholderElement) arrayList.get(arrayList.size() - 1);
            plainPlaceholderElement.setText(plainPlaceholderElement.getText() + str);
        } else {
            arrayList.add(new PlainPlaceholderElement(str));
        }
    }

    private String parseSimpleVariableName() {
        String str_accept;
        Token token = this.token;
        if (accept(TokenType.Dollar) && (str_accept = _accept(TokenType.VariableName)) != null) {
            return str_accept;
        }
        backTo(token);
        return null;
    }

    private boolean parseChoiceElement(List<String> list) {
        Token token = this.token;
        StringBuilder sb = new StringBuilder();
        while (this.token.type != TokenType.Comma && this.token.type != TokenType.Pipe) {
            if (accept(TokenType.Backslash)) {
                String str_accept = _accept(TokenType.Pipe, TokenType.Comma, TokenType.Backslash);
                if (str_accept != null) {
                    sb.append(str_accept);
                } else {
                    sb.append('\\');
                }
            } else if (this.token.type != TokenType.EOF) {
                sb.append(_accept(new TokenType[0]));
            } else {
                backTo(token);
                return false;
            }
        }
        if (sb.length() == 0) {
            backTo(token);
            return false;
        }
        list.add(sb.toString());
        return true;
    }

    private boolean parseTransform(Transform transform) {
        Token token = this.token;
        StringBuilder sb = new StringBuilder();
        while (!accept(TokenType.Forwardslash)) {
            if (accept(TokenType.Backslash)) {
                if (accept(TokenType.Forwardslash)) {
                    sb.append('/');
                } else {
                    sb.append('\\');
                }
            } else {
                if (this.token.type == TokenType.EOF) {
                    return false;
                }
                sb.append(_accept(new TokenType[0]));
            }
        }
        ArrayList arrayList = new ArrayList();
        while (true) {
            int i = 2;
            if (!accept(TokenType.Forwardslash)) {
                if (accept(TokenType.Backslash)) {
                    String str_accept = _accept(TokenType.Backslash, TokenType.Forwardslash);
                    if (str_accept != null) {
                        arrayList.add(new NoFormat(str_accept));
                    } else {
                        String str_accept2 = _accept(TokenType.VariableName);
                        if (str_accept2 != null) {
                            if ("u".equals(str_accept2)) {
                                arrayList.add(new NextUpperCaseFormat());
                            } else {
                                arrayList.add(new NoFormat("\\" + str_accept2));
                            }
                        } else {
                            arrayList.add(new NoFormat("\\"));
                        }
                    }
                } else if (!parseFormatString(arrayList) && !parseAnything(arrayList)) {
                    return false;
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                while (!accept(TokenType.CurlyClose)) {
                    if (this.token.type == TokenType.EOF) {
                        return false;
                    }
                    sb2.append(_accept(new TokenType[0]));
                }
                try {
                    if (sb2.indexOf(ASTExpr.DEFAULT_INDEX_VARIABLE_NAME) == -1) {
                        i = 0;
                    }
                    if (sb2.indexOf("m") != -1) {
                        i |= 8;
                    }
                    transform.globalMode = sb2.indexOf("g") != -1;
                    transform.regexp = Pattern.compile(sb.toString(), i);
                    transform.format = arrayList;
                    return true;
                } catch (PatternSyntaxException unused) {
                    backTo(token);
                    return false;
                }
            }
        }
    }

    private boolean parseFormatString(List<FormatString> list) {
        Token token = this.token;
        if (!accept(TokenType.Dollar)) {
            return false;
        }
        boolean zAccept = accept(TokenType.CurlyOpen);
        String str_accept = _accept(TokenType.Int);
        if (str_accept == null) {
            backTo(token);
            return false;
        }
        int i = Integer.parseInt(str_accept);
        ConditionalFormat conditionalFormat = new ConditionalFormat();
        conditionalFormat.setGroup(i);
        if (zAccept) {
            if (accept(TokenType.Colon)) {
                if (accept(TokenType.Forwardslash)) {
                    String str_accept2 = _accept(TokenType.VariableName);
                    if (str_accept2 != null && accept(TokenType.CurlyClose)) {
                        conditionalFormat.setShorthand(str_accept2);
                        list.add(conditionalFormat);
                        return true;
                    }
                } else if (accept(TokenType.Plus)) {
                    String strUntil = until(TokenType.CurlyClose);
                    if (strUntil != null) {
                        accept(TokenType.CurlyClose);
                        conditionalFormat.setIfValue(strUntil);
                        list.add(conditionalFormat);
                        return true;
                    }
                } else if (accept(TokenType.Dash)) {
                    String strUntil2 = until(TokenType.CurlyClose);
                    if (strUntil2 != null) {
                        accept(TokenType.CurlyClose);
                        conditionalFormat.setElseValue(strUntil2);
                        list.add(conditionalFormat);
                        return true;
                    }
                } else if (accept(TokenType.QuestionMark)) {
                    String strUntil3 = until(TokenType.Colon);
                    accept(TokenType.Colon);
                    String strUntil4 = until(TokenType.CurlyClose);
                    if (strUntil3 != null && strUntil4 != null) {
                        accept(TokenType.CurlyClose);
                        conditionalFormat.setIfValue(strUntil3);
                        conditionalFormat.setElseValue(strUntil4);
                        list.add(conditionalFormat);
                        return true;
                    }
                } else {
                    String strUntil5 = until(TokenType.CurlyClose);
                    if (strUntil5 != null) {
                        accept(TokenType.CurlyClose);
                        conditionalFormat.setElseValue(strUntil5);
                        list.add(conditionalFormat);
                        return true;
                    }
                }
            }
            backTo(token);
            return false;
        }
        list.add(conditionalFormat);
        return true;
    }

    private boolean parseAnything(List<FormatString> list) {
        if (this.token.type == TokenType.EOF) {
            return false;
        }
        list.add(new NoFormat(_accept(new TokenType[0])));
        return true;
    }

    private boolean parseAnything() {
        if (this.token.type == TokenType.EOF) {
            return false;
        }
        this.builder.addPlainText(_accept(new TokenType[0]));
        return true;
    }

    private String until(TokenType tokenType) {
        Token token = this.token;
        StringBuilder sb = new StringBuilder();
        while (this.token.type != tokenType) {
            if (this.token.type == TokenType.EOF) {
                backTo(token);
                return null;
            }
            if (accept(TokenType.Backslash)) {
                String str_accept = _accept(TokenType.Backslash, TokenType.Dollar, TokenType.CurlyClose);
                if (str_accept != null) {
                    sb.append(str_accept);
                } else {
                    backTo(token);
                    return null;
                }
            } else {
                sb.append(_accept(new TokenType[0]));
            }
        }
        return sb.toString();
    }

    public static CodeSnippet parse(String str) {
        return parse(str, new ArrayList());
    }

    public static CodeSnippet parse(String str, List<PlaceholderDefinition> list) {
        CodeSnippetParser codeSnippetParser = new CodeSnippetParser(str, list);
        codeSnippetParser.parse();
        return codeSnippetParser.builder.build();
    }
}
