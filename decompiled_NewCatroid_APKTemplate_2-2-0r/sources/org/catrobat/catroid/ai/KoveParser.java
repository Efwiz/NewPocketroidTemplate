package org.catrobat.catroid.ai;

import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveParser.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveParser.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\u0004H\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010\u0010\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\nH\u0002J\b\u0010\u0014\u001a\u00020\nH\u0002J\b\u0010\u0015\u001a\u00020\fH\u0002J\u001e\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u00122\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0003H\u0002J!\u0010\u0019\u001a\u00020\f2\u0012\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0\u001b\"\u00020\u000eH\u0002¢\u0006\u0002\u0010\u001cJ\b\u0010\u001d\u001a\u00020\nH\u0002J\n\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0002J\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u0003J\u0006\u0010!\u001a\u00020\nJ\b\u0010\"\u001a\u00020\u0004H\u0002J\b\u0010#\u001a\u00020\u0004H\u0002J\b\u0010$\u001a\u00020\u0004H\u0002J\b\u0010%\u001a\u00020\nH\u0002J\b\u0010&\u001a\u00020'H\u0002J\b\u0010(\u001a\u00020\nH\u0002J\b\u0010)\u001a\u00020\nH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/catrobat/catroid/ai/KoveParser;", "", "tokens", "", "Lorg/catrobat/catroid/ai/Token;", "(Ljava/util/List;)V", "current", "", "advance", "andExpr", "Lorg/catrobat/catroid/ai/ParsedFormulaElement;", "check", "", ModuleXmlParser.TYPE, "Lorg/catrobat/catroid/ai/TokenType;", "comparisonExpr", "consume", NotificationCompat.CATEGORY_MESSAGE, "", "equalityExpr", "factorExpr", "isAtEnd", "mapSpecialFunction", "name", "args", "match", SpecTypePair.NAME_types, "", "([Lorg/catrobat/catroid/ai/TokenType;)Z", "orExpr", "parseBrick", "Lorg/catrobat/catroid/ai/ParsedBrick;", "parseBricks", "parseExpression", "peek", "peekNext", "previous", "primaryExpr", "recoverToNextLine", "", "termExpr", "unaryExpr", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KoveParser {
    private int current;
    private final List<Token> tokens;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveParser$WhenMappings.class
      classes9.dex
     */
    /* JADX INFO: compiled from: KoveParser.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TokenType.values().length];
            try {
                iArr[TokenType.GREATER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TokenType.GREATER_EQUAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TokenType.LESS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TokenType.LESS_EQUAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[TokenType.MULT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[TokenType.DIV.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[TokenType.MOD.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public KoveParser(List<Token> tokens) {
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        this.tokens = tokens;
    }

    private final Token peek() {
        return this.tokens.get(this.current);
    }

    private final Token previous() {
        return this.tokens.get(this.current - 1);
    }

    private final boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private final Token advance() {
        if (!isAtEnd()) {
            this.current++;
        }
        return previous();
    }

    private final boolean check(TokenType type) {
        return !isAtEnd() && peek().getType() == type;
    }

    private final boolean match(TokenType... types) {
        for (TokenType tokenType : types) {
            if (check(tokenType)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private final Token peekNext() {
        return this.current + 1 >= this.tokens.size() ? new Token(TokenType.EOF, "", 0, 0) : this.tokens.get(this.current + 1);
    }

    private final Token consume(TokenType type, String msg) {
        if (check(type)) {
            return advance();
        }
        throw new RuntimeException("Parser error at line " + peek().getLine() + ", column " + peek().getColumn() + ": " + msg);
    }

    public final ParsedFormulaElement parseExpression() {
        return orExpr();
    }

    private final ParsedFormulaElement orExpr() {
        ParsedFormulaElement parsedFormulaElementAndExpr = andExpr();
        while (true) {
            ParsedFormulaElement parsedFormulaElement = parsedFormulaElementAndExpr;
            if (!match(TokenType.OR)) {
                return parsedFormulaElement;
            }
            parsedFormulaElementAndExpr = new ParsedFormulaElement(FormulaElementType.OPERATOR, "LOGICAL_OR", parsedFormulaElement, andExpr(), null, 16, null);
        }
    }

    private final ParsedFormulaElement andExpr() {
        ParsedFormulaElement parsedFormulaElementEqualityExpr = equalityExpr();
        while (true) {
            ParsedFormulaElement parsedFormulaElement = parsedFormulaElementEqualityExpr;
            if (!match(TokenType.AND)) {
                return parsedFormulaElement;
            }
            parsedFormulaElementEqualityExpr = new ParsedFormulaElement(FormulaElementType.OPERATOR, "LOGICAL_AND", parsedFormulaElement, equalityExpr(), null, 16, null);
        }
    }

    private final ParsedFormulaElement equalityExpr() {
        ParsedFormulaElement parsedFormulaElementComparisonExpr = comparisonExpr();
        while (true) {
            ParsedFormulaElement parsedFormulaElement = parsedFormulaElementComparisonExpr;
            if (!match(TokenType.EQUAL_DOUBLE, TokenType.NOT_EQUAL)) {
                return parsedFormulaElement;
            }
            parsedFormulaElementComparisonExpr = new ParsedFormulaElement(FormulaElementType.OPERATOR, previous().getType() == TokenType.EQUAL_DOUBLE ? "EQUAL" : "NOT_EQUAL", parsedFormulaElement, comparisonExpr(), null, 16, null);
        }
    }

    private final ParsedFormulaElement comparisonExpr() {
        String str;
        ParsedFormulaElement parsedFormulaElementTermExpr = termExpr();
        while (true) {
            ParsedFormulaElement parsedFormulaElement = parsedFormulaElementTermExpr;
            if (!match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL)) {
                return parsedFormulaElement;
            }
            int i = WhenMappings.$EnumSwitchMapping$0[previous().getType().ordinal()];
            if (i == 1) {
                str = "GREATER_THAN";
            } else if (i == 2) {
                str = "GREATER_OR_EQUAL";
            } else if (i == 3) {
                str = "SMALLER_THAN";
            } else if (i == 4) {
                str = "SMALLER_OR_EQUAL";
            } else {
                str = "";
            }
            parsedFormulaElementTermExpr = new ParsedFormulaElement(FormulaElementType.OPERATOR, str, parsedFormulaElement, termExpr(), null, 16, null);
        }
    }

    private final ParsedFormulaElement termExpr() {
        ParsedFormulaElement parsedFormulaElementFactorExpr = factorExpr();
        while (true) {
            ParsedFormulaElement parsedFormulaElement = parsedFormulaElementFactorExpr;
            if (!match(TokenType.PLUS, TokenType.MINUS)) {
                return parsedFormulaElement;
            }
            parsedFormulaElementFactorExpr = new ParsedFormulaElement(FormulaElementType.OPERATOR, previous().getType() == TokenType.PLUS ? "PLUS" : "MINUS", parsedFormulaElement, factorExpr(), null, 16, null);
        }
    }

    private final ParsedFormulaElement factorExpr() {
        String str;
        ParsedFormulaElement parsedFormulaElementUnaryExpr = unaryExpr();
        while (true) {
            ParsedFormulaElement parsedFormulaElement = parsedFormulaElementUnaryExpr;
            if (!match(TokenType.MULT, TokenType.DIV, TokenType.MOD)) {
                return parsedFormulaElement;
            }
            int i = WhenMappings.$EnumSwitchMapping$0[previous().getType().ordinal()];
            if (i == 5) {
                str = "MULT";
            } else if (i == 6) {
                str = "DIVIDE";
            } else if (i == 7) {
                str = "MOD";
            } else {
                str = "";
            }
            parsedFormulaElementUnaryExpr = new ParsedFormulaElement(FormulaElementType.OPERATOR, str, parsedFormulaElement, unaryExpr(), null, 16, null);
        }
    }

    private final ParsedFormulaElement unaryExpr() {
        if (match(TokenType.NOT)) {
            return new ParsedFormulaElement(FormulaElementType.OPERATOR, "LOGICAL_NOT", null, unaryExpr(), null, 16, null);
        }
        if (match(TokenType.MINUS)) {
            return new ParsedFormulaElement(FormulaElementType.OPERATOR, "MINUS", new ParsedFormulaElement(FormulaElementType.NUMBER, "0", null, null, null, 28, null), primaryExpr(), null, 16, null);
        }
        return primaryExpr();
    }

    private final ParsedFormulaElement primaryExpr() {
        if (match(TokenType.NUMBER)) {
            return new ParsedFormulaElement(FormulaElementType.NUMBER, previous().getValue(), null, null, null, 28, null);
        }
        if (match(TokenType.STRING)) {
            return new ParsedFormulaElement(FormulaElementType.STRING, previous().getValue(), null, null, null, 28, null);
        }
        if (match(TokenType.LPAREN)) {
            ParsedFormulaElement expression = parseExpression();
            consume(TokenType.RPAREN, "Expected ')'");
            return expression;
        }
        if (match(TokenType.IDENTIFIER)) {
            String value = previous().getValue();
            if (match(TokenType.LPAREN)) {
                ArrayList arrayList = new ArrayList();
                if (!check(TokenType.RPAREN)) {
                    do {
                        arrayList.add(parseExpression());
                    } while (match(TokenType.COMMA));
                }
                consume(TokenType.RPAREN, "Expected ')'");
                return mapSpecialFunction(value, arrayList);
            }
            return new ParsedFormulaElement(FormulaElementType.USER_VARIABLE, value, null, null, null, 28, null);
        }
        throw new RuntimeException("Unexpected expression token: " + peek().getValue());
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final ParsedFormulaElement mapSpecialFunction(String name2, List<ParsedFormulaElement> args) {
        String value;
        String value2;
        String value3;
        String value4;
        String value5;
        switch (name2.hashCode()) {
            case -1134815187:
                if (name2.equals("touches")) {
                    FormulaElementType formulaElementType = FormulaElementType.COLLISION_FORMULA;
                    ParsedFormulaElement parsedFormulaElement = (ParsedFormulaElement) CollectionsKt.firstOrNull((List) args);
                    return new ParsedFormulaElement(formulaElementType, (parsedFormulaElement == null || (value = parsedFormulaElement.getValue()) == null) ? "" : value, null, null, null, 28, null);
                }
                break;
            case -905948230:
                if (name2.equals("sensor")) {
                    FormulaElementType formulaElementType2 = FormulaElementType.SENSOR;
                    ParsedFormulaElement parsedFormulaElement2 = (ParsedFormulaElement) CollectionsKt.firstOrNull((List) args);
                    return new ParsedFormulaElement(formulaElementType2, (parsedFormulaElement2 == null || (value2 = parsedFormulaElement2.getValue()) == null) ? "" : value2, null, null, null, 28, null);
                }
                break;
            case 116519:
                if (name2.equals(PsiKeyword.VAR)) {
                    FormulaElementType formulaElementType3 = FormulaElementType.USER_VARIABLE;
                    ParsedFormulaElement parsedFormulaElement3 = (ParsedFormulaElement) CollectionsKt.firstOrNull((List) args);
                    return new ParsedFormulaElement(formulaElementType3, (parsedFormulaElement3 == null || (value3 = parsedFormulaElement3.getValue()) == null) ? "" : value3, null, null, null, 28, null);
                }
                break;
            case 3322014:
                if (name2.equals(SchemaSymbols.ATTVAL_LIST)) {
                    FormulaElementType formulaElementType4 = FormulaElementType.USER_LIST;
                    ParsedFormulaElement parsedFormulaElement4 = (ParsedFormulaElement) CollectionsKt.firstOrNull((List) args);
                    return new ParsedFormulaElement(formulaElementType4, (parsedFormulaElement4 == null || (value4 = parsedFormulaElement4.getValue()) == null) ? "" : value4, null, null, null, 28, null);
                }
                break;
            case 100358090:
                if (name2.equals("input")) {
                    FormulaElementType formulaElementType5 = FormulaElementType.USER_DEFINED_BRICK_INPUT;
                    ParsedFormulaElement parsedFormulaElement5 = (ParsedFormulaElement) CollectionsKt.firstOrNull((List) args);
                    return new ParsedFormulaElement(formulaElementType5, (parsedFormulaElement5 == null || (value5 = parsedFormulaElement5.getValue()) == null) ? "" : value5, null, null, null, 28, null);
                }
                break;
        }
        ParsedFormulaElement parsedFormulaElement6 = (ParsedFormulaElement) CollectionsKt.getOrNull(args, 0);
        ParsedFormulaElement parsedFormulaElement7 = (ParsedFormulaElement) CollectionsKt.getOrNull(args, 1);
        List listDrop = args.size() > 2 ? CollectionsKt.drop(args, 2) : CollectionsKt.emptyList();
        FormulaElementType formulaElementType6 = FormulaElementType.FUNCTION;
        String upperCase = name2.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        return new ParsedFormulaElement(formulaElementType6, upperCase, parsedFormulaElement6, parsedFormulaElement7, listDrop);
    }

    public final List<ParsedBrick> parseBricks() {
        ArrayList arrayList = new ArrayList();
        while (!isAtEnd() && !check(TokenType.DEDENT)) {
            try {
                ParsedBrick brick = parseBrick();
                if (brick != null) {
                    arrayList.add(brick);
                }
            } catch (Exception e) {
                Log.e("KOVE_PARSER", "Recovering from syntax error: " + e.getMessage());
                recoverToNextLine();
            }
            while (match(TokenType.NEWLINE)) {
            }
        }
        return arrayList;
    }

    private final void recoverToNextLine() {
        while (!isAtEnd() && !check(TokenType.NEWLINE) && !check(TokenType.DEDENT)) {
            advance();
        }
        if (check(TokenType.NEWLINE)) {
            advance();
        }
    }

    private final ParsedBrick parseBrick() {
        while (match(TokenType.NEWLINE)) {
        }
        List<ParsedBrick> bricks = null;
        if (!isAtEnd() && !check(TokenType.DEDENT)) {
            if (match(TokenType.AT)) {
                consume(TokenType.IDENTIFIER, "Expected decorator name after '@'");
                if (match(TokenType.LPAREN)) {
                    while (!isAtEnd() && !match(TokenType.RPAREN)) {
                        advance();
                    }
                }
                consume(TokenType.NEWLINE, "Expected newline after decorator");
                return null;
            }
            if (match(TokenType.DEF)) {
                consume(TokenType.IDENTIFIER, "Expected function name after 'def'");
                consume(TokenType.LPAREN, "Expected '('");
                while (!isAtEnd() && !check(TokenType.RPAREN)) {
                    advance();
                }
                consume(TokenType.RPAREN, "Expected ')'");
                consume(TokenType.COLON, "Expected ':'");
                consume(TokenType.NEWLINE, "Expected newline");
                return null;
            }
            if (match(TokenType.CLASS)) {
                consume(TokenType.IDENTIFIER, "Expected class name after 'class'");
                if (!match(TokenType.COLON) && match(TokenType.LPAREN)) {
                    while (!isAtEnd() && !match(TokenType.RPAREN)) {
                        advance();
                    }
                    consume(TokenType.COLON, "Expected ':'");
                }
                consume(TokenType.NEWLINE, "Expected newline");
                return null;
            }
            if (match(TokenType.PASS)) {
                consume(TokenType.NEWLINE, "Expected newline after 'pass'");
                return null;
            }
            if (match(TokenType.IF)) {
                ParsedFormulaElement expression = parseExpression();
                consume(TokenType.COLON, "Expected ':' after 'if'");
                consume(TokenType.NEWLINE, "Expected newline after 'if'");
                consume(TokenType.INDENT, "Expected indented block after 'if'");
                List<ParsedBrick> bricks2 = parseBricks();
                consume(TokenType.DEDENT, "Expected dedent");
                int i = this.current;
                while (match(TokenType.NEWLINE)) {
                }
                if (match(TokenType.ELSE)) {
                    consume(TokenType.COLON, "Expected ':' after 'else'");
                    consume(TokenType.NEWLINE, "Expected newline after 'else'");
                    consume(TokenType.INDENT, "Expected indented block after 'else'");
                    bricks = parseBricks();
                    consume(TokenType.DEDENT, "Expected dedent");
                } else {
                    this.current = i;
                }
                return new ParsedBrick.If(expression, bricks2, bricks);
            }
            if (match(TokenType.FOREVER)) {
                consume(TokenType.LPAREN, "Expected '('");
                consume(TokenType.RPAREN, "Expected ')'");
                consume(TokenType.COLON, "Expected ':'");
                consume(TokenType.NEWLINE, "Expected newline");
                consume(TokenType.INDENT, "Expected indented block");
                List<ParsedBrick> bricks3 = parseBricks();
                consume(TokenType.DEDENT, "Expected dedent");
                return new ParsedBrick.Loop("forever", MapsKt.emptyMap(), bricks3);
            }
            if (match(TokenType.REPEAT)) {
                consume(TokenType.LPAREN, "Expected '('");
                ParsedFormulaElement expression2 = parseExpression();
                consume(TokenType.RPAREN, "Expected ')'");
                consume(TokenType.COLON, "Expected ':'");
                consume(TokenType.NEWLINE, "Expected newline");
                consume(TokenType.INDENT, "Expected indented block");
                List<ParsedBrick> bricks4 = parseBricks();
                consume(TokenType.DEDENT, "Expected dedent");
                return new ParsedBrick.Loop("repeat", MapsKt.mapOf(TuplesKt.to("times", expression2)), bricks4);
            }
            if (match(TokenType.REPEAT_UNTIL)) {
                consume(TokenType.LPAREN, "Expected '('");
                ParsedFormulaElement expression3 = parseExpression();
                consume(TokenType.RPAREN, "Expected ')'");
                consume(TokenType.COLON, "Expected ':'");
                consume(TokenType.NEWLINE, "Expected newline");
                consume(TokenType.INDENT, "Expected indented block");
                List<ParsedBrick> bricks5 = parseBricks();
                consume(TokenType.DEDENT, "Expected dedent");
                return new ParsedBrick.Loop("repeat_until", MapsKt.mapOf(TuplesKt.to("cond", expression3)), bricks5);
            }
            if (match(TokenType.IDENTIFIER)) {
                String value = previous().getValue();
                consume(TokenType.LPAREN, "Expected '(' after block name '" + value + FormatHelper.QUOTE);
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                if (!check(TokenType.RPAREN)) {
                    int i2 = 1;
                    do {
                        if (check(TokenType.IDENTIFIER) && peekNext().getType() == TokenType.EQUAL_SINGLE) {
                            String value2 = advance().getValue();
                            consume(TokenType.EQUAL_SINGLE, "Expected '='");
                            linkedHashMap.put(value2, parseExpression());
                        } else {
                            linkedHashMap.put(i2 != 1 ? "val" + i2 : "val", parseExpression());
                            i2++;
                        }
                    } while (match(TokenType.COMMA));
                }
                consume(TokenType.RPAREN, "Expected ')'");
                consume(TokenType.NEWLINE, "Expected newline");
                return new ParsedBrick.Simple(value, linkedHashMap);
            }
            advance();
        }
        return null;
    }
}
