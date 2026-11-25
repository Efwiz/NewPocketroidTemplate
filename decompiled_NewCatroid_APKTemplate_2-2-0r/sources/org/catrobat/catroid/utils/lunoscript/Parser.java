package org.catrobat.catroid.utils.lunoscript;

import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Parser.class
  classes14.dex
 */
/* JADX INFO: compiled from: Parser.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001:\u0001JB\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\u0004H\u0002J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003H\u0002J\b\u0010\f\u001a\u00020\u000bH\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u000bH\u0002J\b\u0010\u0014\u001a\u00020\u000eH\u0002J\u0018\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u001a\u001a\u00020\u000bH\u0002J\n\u0010\u001b\u001a\u0004\u0018\u00010\u000bH\u0002J\b\u0010\u001c\u001a\u00020\u000eH\u0002J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010 \u001a\u00020\u000eH\u0002J\b\u0010!\u001a\u00020\u000bH\u0002J\b\u0010\"\u001a\u00020\u000eH\u0002J\u0018\u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020\u0007H\u0002J\u0010\u0010&\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020\u000eH\u0002J\u0010\u0010(\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020\u000eH\u0002J\b\u0010)\u001a\u00020\u000bH\u0002J\u0010\u0010*\u001a\u00020\u000b2\u0006\u0010+\u001a\u00020\u0017H\u0002J\b\u0010,\u001a\u00020\u000eH\u0002J\b\u0010-\u001a\u00020\u000bH\u0002J\b\u0010.\u001a\u00020\u000bH\u0002J\b\u0010/\u001a\u00020\u0010H\u0002J\b\u00100\u001a\u00020\u0010H\u0002J\b\u00101\u001a\u00020\u000eH\u0002J\b\u00102\u001a\u00020\u000eH\u0002J\b\u00103\u001a\u00020\u000eH\u0002J\b\u00104\u001a\u00020\u000eH\u0002J!\u00105\u001a\u00020\u00102\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001207\"\u00020\u0012H\u0002¢\u0006\u0002\u00108J\u0006\u00109\u001a\u00020:J\b\u0010;\u001a\u00020\u000eH\u0002J\b\u0010<\u001a\u00020\u0004H\u0002J\b\u0010=\u001a\u00020\u0004H\u0002J\b\u0010>\u001a\u00020\u000eH\u0002J\b\u0010?\u001a\u00020\u000bH\u0002J\b\u0010@\u001a\u00020\u0019H\u0002J\b\u0010A\u001a\u00020\u000bH\u0002J\b\u0010B\u001a\u00020\u000bH\u0002J\b\u0010C\u001a\u00020\u0019H\u0002J\b\u0010D\u001a\u00020\u000eH\u0002J\b\u0010E\u001a\u00020\u000bH\u0002J\b\u0010F\u001a\u00020\u000eH\u0002J\b\u0010G\u001a\u00020\u000bH\u0002J\b\u0010H\u001a\u00020\u000bH\u0002J\f\u0010I\u001a\u00020\u0010*\u00020\u0012H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006K"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/Parser;", "", "tokens", "", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "(Ljava/util/List;)V", "current", "", "expressionDepth", "advance", "blockStatements", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "breakStatement", "call", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "check", "", ModuleXmlParser.TYPE, "Lorg/catrobat/catroid/utils/lunoscript/TokenType;", "classDeclaration", "comparison", "consume", CrashHianalyticsData.MESSAGE, "", "consumeSemicolon", "", "continueStatement", "declaration", "equality", "error", "Lorg/catrobat/catroid/utils/lunoscript/Parser$ParseError;", "token", "expression", "expressionStatement", "factor", "findMatchingBrace", FirebaseAnalytics.Param.CONTENT, "start", "finishCall", "callee", "finishIndexAccess", "forStatement", "funDeclaration", Namer.METADATA_CLASS_KIND, "functionExpression", "ifStatement", "importStatement", "isAtEnd", "isExpressionContext", "listLiteral", "logicalAnd", "logicalOr", "mapLiteral", "match", SpecTypePair.NAME_types, "", "([Lorg/catrobat/catroid/utils/lunoscript/TokenType;)Z", "parse", "Lorg/catrobat/catroid/utils/lunoscript/ProgramNode;", "parseFString", "peek", "previous", "primary", "returnStatement", "skipComments", "statement", "switchStatement", "synchronize", FirebaseAnalytics.Param.TERM, "tryStatement", "unary", "varDeclaration", "whileStatement", "isAssignmentOperator", "ParseError", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Parser {
    private int current;
    private int expressionDepth;
    private final List<Token> tokens;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Parser$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: Parser.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TokenType.values().length];
            try {
                iArr[TokenType.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TokenType.FUN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TokenType.VAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TokenType.FOR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[TokenType.IF.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[TokenType.WHILE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[TokenType.SWITCH.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[TokenType.RETURN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[TokenType.TRY.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[TokenType.RBRACE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private final boolean isExpressionContext() {
        return true;
    }

    public Parser(List<Token> tokens) {
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        this.tokens = tokens;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Parser$ParseError.class
      classes14.dex
     */
    /* JADX INFO: compiled from: Parser.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/Parser$ParseError;", "Lorg/catrobat/catroid/utils/lunoscript/LunoSyntaxError;", CrashHianalyticsData.MESSAGE, "", "token", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "(Ljava/lang/String;Lorg/catrobat/catroid/utils/lunoscript/Token;)V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ParseError extends LunoSyntaxError {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ParseError(String message, Token token) {
            super(message, token.getLine(), token.getPosition());
            Intrinsics.checkNotNullParameter(message, "message");
            Intrinsics.checkNotNullParameter(token, "token");
        }
    }

    public final ProgramNode parse() {
        ArrayList arrayList = new ArrayList();
        int line = (this.tokens.isEmpty() || this.tokens.get(0).getType() == TokenType.EOF) ? 1 : this.tokens.get(0).getLine();
        Log.d("LunoParser-Trace", "--- STARTING PARSE ---");
        while (!isAtEnd()) {
            skipComments();
            if (isAtEnd()) {
                break;
            }
            try {
                Statement statementDeclaration = declaration();
                if (statementDeclaration != null) {
                    arrayList.add(statementDeclaration);
                }
            } catch (ParseError e) {
                System.out.println((Object) ("Parser encountered an error: " + e.getMessage()));
                synchronize();
            } catch (LunoSyntaxError e2) {
                System.out.println((Object) ("Parser encountered an error: " + e2.getMessage()));
                synchronize();
            }
        }
        Log.d("LunoParser-Trace", "--- FINISHED PARSE ---");
        return new ProgramNode(arrayList, line);
    }

    private final Statement declaration() {
        try {
            skipComments();
            if (isAtEnd()) {
                return null;
            }
            if (match(TokenType.FUN)) {
                return funDeclaration("function");
            }
            if (match(TokenType.CLASS)) {
                return classDeclaration();
            }
            if (match(TokenType.VAR)) {
                return varDeclaration();
            }
            return match(TokenType.IMPORT) ? importStatement() : statement();
        } catch (ParseError unused) {
            synchronize();
            return null;
        }
    }

    private final Statement tryStatement() {
        Token token;
        BlockStatement blockStatement;
        Token tokenPrevious = previous();
        consume(TokenType.LBRACE, "Expect '{' before 'try' body.");
        List<Statement> listBlockStatements = blockStatements();
        consume(TokenType.RBRACE, "Expect '}' after 'try' body.");
        BlockStatement blockStatement2 = new BlockStatement(listBlockStatements, tokenPrevious.getLine());
        BlockStatement blockStatement3 = null;
        if (match(TokenType.CATCH)) {
            consume(TokenType.LPAREN, "Expect '(' after 'catch'.");
            Token tokenConsume = consume(TokenType.IDENTIFIER, "Expect exception variable name.");
            consume(TokenType.RPAREN, "Expect ')' after exception variable.");
            consume(TokenType.LBRACE, "Expect '{' before 'catch' body.");
            List<Statement> listBlockStatements2 = blockStatements();
            consume(TokenType.RBRACE, "Expect '}' after 'catch' body.");
            token = tokenConsume;
            blockStatement = new BlockStatement(listBlockStatements2, tokenConsume.getLine());
        } else {
            token = null;
            blockStatement = null;
        }
        if (match(TokenType.FINALLY)) {
            consume(TokenType.LBRACE, "Expect '{' before 'finally' body.");
            List<Statement> listBlockStatements3 = blockStatements();
            consume(TokenType.RBRACE, "Expect '}' after 'finally' body.");
            blockStatement3 = new BlockStatement(listBlockStatements3, previous().getLine());
        }
        BlockStatement blockStatement4 = blockStatement3;
        if (blockStatement == null && blockStatement4 == null) {
            throw error(tokenPrevious, "A 'try' statement must have at least a 'catch' or a 'finally' block.");
        }
        return new TryCatchStatement(blockStatement2, token, blockStatement, blockStatement4, tokenPrevious.getLine());
    }

    private final Statement importStatement() {
        Token tokenPrevious = previous();
        ArrayList arrayList = new ArrayList();
        do {
            arrayList.add(consume(TokenType.IDENTIFIER, "Expect package or class name."));
        } while (match(TokenType.DOT));
        consumeSemicolon("Expect ';' or newline after import statement.");
        return new ImportStatement(arrayList, tokenPrevious.getLine());
    }

    private final void skipComments() {
        while (check(TokenType.COMMENT)) {
            advance();
        }
    }

    private final Statement classDeclaration() {
        BlockStatement blockStatement;
        Token tokenPrevious = previous();
        Token tokenConsume = consume(TokenType.IDENTIFIER, "Expect class name.");
        Log.d("LunoParser-Trace", ">>> Entering classDeclaration for '" + tokenConsume.getLexeme() + FormatHelper.QUOTE);
        VariableExpr variableExpr = match(TokenType.LT) ? new VariableExpr(consume(TokenType.IDENTIFIER, "Expect superclass name."), previous().getLine()) : null;
        consume(TokenType.LBRACE, "Expect '{' before class body.");
        if (match(TokenType.STATIC)) {
            Token tokenPrevious2 = previous();
            consume(TokenType.LBRACE, "Expect '{' after 'static'.");
            List<Statement> listBlockStatements = blockStatements();
            consume(TokenType.RBRACE, "Expect '}' after static block.");
            blockStatement = new BlockStatement(listBlockStatements, tokenPrevious2.getLine());
        } else {
            blockStatement = null;
        }
        ArrayList arrayList = new ArrayList();
        while (!check(TokenType.RBRACE) && !isAtEnd()) {
            skipComments();
            if (check(TokenType.RBRACE)) {
                break;
            }
            try {
                if (match(TokenType.FUN)) {
                    Statement statementFunDeclaration = funDeclaration("method");
                    Intrinsics.checkNotNull(statementFunDeclaration, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.FunDeclarationStatement");
                    arrayList.add((FunDeclarationStatement) statementFunDeclaration);
                } else {
                    throw error(peek(), "Only 'fun' declarations are allowed inside a class body.");
                }
            } catch (ParseError e) {
                System.out.println((Object) ("Parser error inside class '" + tokenConsume.getLexeme() + "': " + e.getMessage()));
                synchronize();
            }
        }
        consume(TokenType.RBRACE, "Expect '}' after class body.");
        Log.d("LunoParser-Trace", "<<< Exiting classDeclaration for '" + tokenConsume.getLexeme() + FormatHelper.QUOTE);
        return new ClassDeclarationStatement(tokenConsume, arrayList, variableExpr, blockStatement, tokenPrevious.getLine());
    }

    private final Expression functionExpression() {
        Token tokenPrevious = previous();
        consume(TokenType.LPAREN, "Expect '(' after 'fun' for lambda expression.");
        ArrayList arrayList = new ArrayList();
        if (!check(TokenType.RPAREN)) {
            do {
                arrayList.add(consume(TokenType.IDENTIFIER, "Expect parameter name."));
            } while (match(TokenType.COMMA));
        }
        consume(TokenType.RPAREN, "Expect ')' after parameters.");
        consume(TokenType.LBRACE, "Expect '{' before lambda body.");
        List<Statement> listBlockStatements = blockStatements();
        consume(TokenType.RBRACE, "Expect '}' after lambda body.");
        return new LambdaExpr(arrayList, new BlockStatement(listBlockStatements, tokenPrevious.getLine()), tokenPrevious.getLine());
    }

    private final Statement funDeclaration(String kind) {
        Token tokenPrevious = previous();
        Token tokenConsume = consume(TokenType.IDENTIFIER, "Expect " + kind + " name.");
        Log.d("LunoParser-Trace", ">>> Entering funDeclaration for '" + tokenConsume.getLexeme() + "' (kind: " + kind + Constant.AFTER_QUTO);
        consume(TokenType.LPAREN, "Expect '(' after " + kind + " name.");
        ArrayList arrayList = new ArrayList();
        if (!check(TokenType.RPAREN)) {
            do {
                if (arrayList.size() >= 255) {
                    error(peek(), "Cannot have more than 255 parameters.");
                }
                arrayList.add(consume(TokenType.IDENTIFIER, "Expect parameter name."));
            } while (match(TokenType.COMMA));
        }
        consume(TokenType.RPAREN, "Expect ')' after parameters.");
        consume(TokenType.LBRACE, "Expect '{' before " + kind + " body.");
        List<Statement> listBlockStatements = blockStatements();
        consume(TokenType.RBRACE, "Expect '}' after " + kind + " body.");
        BlockStatement blockStatement = new BlockStatement(listBlockStatements, tokenPrevious.getLine());
        Log.d("LunoParser-Trace", "<<< Exiting funDeclaration for '" + tokenConsume.getLexeme() + FormatHelper.QUOTE);
        return new FunDeclarationStatement(tokenConsume, arrayList, blockStatement, tokenPrevious.getLine());
    }

    private final Statement varDeclaration() {
        boolean zAreEqual = Intrinsics.areEqual(previous().getLexeme(), "val");
        Token tokenConsume = consume(TokenType.IDENTIFIER, "Expect variable name.");
        Log.d("LunoParser", "Creating VarDeclarationStatement for '" + tokenConsume.getLexeme() + FormatHelper.QUOTE);
        Expression expression = match(TokenType.ASSIGN) ? expression() : null;
        consumeSemicolon("Expect ';' or newline after variable declaration.");
        return new VarDeclarationStatement(tokenConsume, expression, zAreEqual, tokenConsume.getLine());
    }

    private final Statement ifStatement() {
        Token tokenPrevious = previous();
        consume(TokenType.LPAREN, "Expect '(' after 'if'.");
        Expression expression = expression();
        consume(TokenType.RPAREN, "Expect ')' after if condition.");
        return new IfStatement(expression, statement(), match(TokenType.ELSE) ? statement() : null, tokenPrevious, tokenPrevious.getLine());
    }

    private final Statement whileStatement() {
        Token tokenPrevious = previous();
        consume(TokenType.LPAREN, "Expect '(' after 'while'.");
        Expression expression = expression();
        consume(TokenType.RPAREN, "Expect ')' after while condition.");
        return new WhileStatement(expression, statement(), tokenPrevious, tokenPrevious.getLine());
    }

    private final Statement forStatement() {
        Token tokenPrevious = previous();
        consume(TokenType.LPAREN, "Expect '(' after 'for'.");
        Token tokenConsume = consume(TokenType.IDENTIFIER, "Expect loop variable name.");
        consume(TokenType.IN, "Expect 'in' after loop variable.");
        Expression expression = expression();
        consume(TokenType.RPAREN, "Expect ')' after for-in expression.");
        return new ForInStatement(tokenConsume, expression, statement(), tokenPrevious, tokenPrevious.getLine());
    }

    private final Statement switchStatement() {
        Token tokenPrevious = previous();
        consume(TokenType.LPAREN, "Expect '(' after 'switch'.");
        Expression expression = expression();
        consume(TokenType.RPAREN, "Expect ')' after switch expression.");
        consume(TokenType.LBRACE, "Expect '{' before switch cases.");
        ArrayList arrayList = new ArrayList();
        SwitchCase switchCase = null;
        while (!check(TokenType.RBRACE) && !isAtEnd()) {
            Token tokenPeek = peek();
            ArrayList arrayList2 = new ArrayList();
            boolean z = true;
            if (match(TokenType.CASE)) {
                do {
                    arrayList2.add(expression());
                    if (!match(TokenType.COMMA)) {
                        break;
                    }
                } while (!check(TokenType.COLON));
                z = false;
            } else {
                if (!match(TokenType.DEFAULT)) {
                    throw error(peek(), "Expect 'case' or 'default'.");
                }
                if (switchCase != null) {
                    throw error(previous(), "Multiple default cases in switch statement.");
                }
            }
            consume(TokenType.COLON, "Expect ':' after case/default.");
            new ArrayList();
            Statement statement = statement();
            if (z) {
                arrayList2 = null;
            }
            SwitchCase switchCase2 = new SwitchCase(arrayList2, statement, tokenPeek, z);
            if (z) {
                switchCase = switchCase2;
            } else {
                arrayList.add(switchCase2);
            }
        }
        consume(TokenType.RBRACE, "Expect '}' after switch body.");
        if (switchCase != null) {
            arrayList.add(switchCase);
        }
        return new SwitchStatement(expression, arrayList, tokenPrevious, tokenPrevious.getLine());
    }

    private final Statement returnStatement() {
        Token tokenPrevious = previous();
        Expression expression = (check(TokenType.SEMICOLON) || check(TokenType.RBRACE)) ? null : expression();
        consumeSemicolon("Expect ';' or newline after return value.");
        return new ReturnStatement(tokenPrevious, expression, tokenPrevious.getLine());
    }

    private final List<Statement> blockStatements() {
        Log.d("LunoParser-Trace", "    [BS] >> Entering blockStatements. Looking for RBRACE. Current token: " + peek().getLexeme());
        ArrayList arrayList = new ArrayList();
        while (!check(TokenType.RBRACE) && !isAtEnd()) {
            Statement statementDeclaration = declaration();
            if (statementDeclaration != null) {
                arrayList.add(statementDeclaration);
            }
        }
        Log.d("LunoParser-Trace", "    [BS] << Exiting blockStatements. Current token: " + peek().getLexeme());
        return arrayList;
    }

    private final Statement breakStatement() {
        Token tokenPrevious = previous();
        consumeSemicolon("Expect ';' or newline after 'break'.");
        return new BreakStatement(tokenPrevious, tokenPrevious.getLine());
    }

    private final Statement continueStatement() {
        Token tokenPrevious = previous();
        consumeSemicolon("Expect ';' or newline after 'continue'.");
        return new ContinueStatement(tokenPrevious, tokenPrevious.getLine());
    }

    private final Statement expressionStatement() {
        Expression expressionCall = call();
        if (isAssignmentOperator(peek().getType())) {
            Log.d("LunoParser", "Creating AssignmentStatement for target: " + expressionCall);
            if (!(expressionCall instanceof VariableExpr) && !(expressionCall instanceof GetExpr) && !(expressionCall instanceof IndexAccessExpr) && !(expressionCall instanceof ThisExpr)) {
                throw error(peek(), "Invalid assignment target. Left side must be a variable, property, or index access.");
            }
            Token tokenAdvance = advance();
            Expression expression = expression();
            consumeSemicolon("Expect ';' or newline after assignment statement.");
            return new AssignmentStatement(expressionCall, expression, tokenAdvance, tokenAdvance.getLine());
        }
        Log.d("LunoParser", "Creating ExpressionStatement for expr: " + expressionCall);
        consumeSemicolon("Expect ';' or newline after expression statement.");
        return new ExpressionStatement(expressionCall, expressionCall.getLine());
    }

    private final Expression logicalOr() {
        LogicalExpr logicalExprLogicalAnd = logicalAnd();
        while (match(TokenType.OR)) {
            Token tokenPrevious = previous();
            logicalExprLogicalAnd = new LogicalExpr(logicalExprLogicalAnd, tokenPrevious, logicalAnd(), tokenPrevious.getLine());
        }
        return logicalExprLogicalAnd;
    }

    private final Expression logicalAnd() {
        LogicalExpr logicalExprEquality = equality();
        while (match(TokenType.AND)) {
            Token tokenPrevious = previous();
            logicalExprEquality = new LogicalExpr(logicalExprEquality, tokenPrevious, equality(), tokenPrevious.getLine());
        }
        return logicalExprEquality;
    }

    private final Expression equality() {
        BinaryExpr binaryExprComparison = comparison();
        while (match(TokenType.NEQ, TokenType.EQ)) {
            Token tokenPrevious = previous();
            binaryExprComparison = new BinaryExpr(binaryExprComparison, tokenPrevious, comparison(), tokenPrevious.getLine());
        }
        return binaryExprComparison;
    }

    private final Expression comparison() {
        BinaryExpr binaryExprTerm = term();
        while (match(TokenType.GT, TokenType.GTE, TokenType.LT, TokenType.LTE)) {
            Token tokenPrevious = previous();
            binaryExprTerm = new BinaryExpr(binaryExprTerm, tokenPrevious, term(), tokenPrevious.getLine());
        }
        return binaryExprTerm;
    }

    private final Expression term() {
        BinaryExpr binaryExprFactor = factor();
        while (match(TokenType.MINUS, TokenType.PLUS)) {
            Token tokenPrevious = previous();
            binaryExprFactor = new BinaryExpr(binaryExprFactor, tokenPrevious, factor(), tokenPrevious.getLine());
        }
        return binaryExprFactor;
    }

    private final Expression factor() {
        BinaryExpr binaryExprUnary = unary();
        while (match(TokenType.DIVIDE, TokenType.MULTIPLY, TokenType.MODULO)) {
            Token tokenPrevious = previous();
            binaryExprUnary = new BinaryExpr(binaryExprUnary, tokenPrevious, unary(), tokenPrevious.getLine());
        }
        return binaryExprUnary;
    }

    private final Expression unary() {
        if (match(TokenType.BANG, TokenType.MINUS)) {
            Token tokenPrevious = previous();
            return new UnaryExpr(tokenPrevious, unary(), tokenPrevious.getLine());
        }
        return call();
    }

    private final Expression call() {
        GetExpr getExprPrimary = primary();
        while (true) {
            if (match(TokenType.LPAREN)) {
                getExprPrimary = finishCall(getExprPrimary);
            } else if (match(TokenType.LBRACKET)) {
                getExprPrimary = finishIndexAccess(getExprPrimary);
            } else {
                if (!match(TokenType.DOT)) {
                    return getExprPrimary;
                }
                if (!match(TokenType.CLASS)) {
                    Token tokenConsume = consume(TokenType.IDENTIFIER, "Expect property name after '.'.");
                    getExprPrimary = new GetExpr(getExprPrimary, tokenConsume, tokenConsume.getLine());
                }
            }
        }
    }

    private final Expression finishCall(Expression callee) {
        ArrayList arrayList = new ArrayList();
        Token tokenPrevious = previous();
        if (!check(TokenType.RPAREN)) {
            do {
                if (arrayList.size() >= 255) {
                    error(peek(), "Cannot have more than 255 arguments.");
                }
                arrayList.add(expression());
            } while (match(TokenType.COMMA));
        }
        return new CallExpr(callee, arrayList, consume(TokenType.RPAREN, "Expect ')' after arguments."), tokenPrevious.getLine());
    }

    private final Expression finishIndexAccess(Expression callee) {
        Token tokenPrevious = previous();
        Expression expression = expression();
        consume(TokenType.RBRACKET, "Expect ']' after index.");
        return new IndexAccessExpr(callee, tokenPrevious, expression, tokenPrevious.getLine());
    }

    private final Expression primary() {
        peek();
        if (match(TokenType.FALSE)) {
            return new LiteralExpr(new LunoValue.Boolean(false), previous().getLine());
        }
        if (match(TokenType.TRUE)) {
            return new LiteralExpr(new LunoValue.Boolean(true), previous().getLine());
        }
        if (match(TokenType.NULL)) {
            return new LiteralExpr(LunoValue.Null.INSTANCE, previous().getLine());
        }
        if (match(TokenType.THIS)) {
            return new ThisExpr(previous(), previous().getLine());
        }
        if (match(TokenType.NUMBER_LITERAL)) {
            Object literal = previous().getLiteral();
            Intrinsics.checkNotNull(literal, "null cannot be cast to non-null type kotlin.Double");
            return new LiteralExpr(new LunoValue.Number(((Double) literal).doubleValue()), previous().getLine());
        }
        if (match(TokenType.FLOAT_LITERAL)) {
            Object literal2 = previous().getLiteral();
            Intrinsics.checkNotNull(literal2, "null cannot be cast to non-null type kotlin.Float");
            return new LiteralExpr(new LunoValue.Float(((Float) literal2).floatValue()), previous().getLine());
        }
        if (match(TokenType.STRING_LITERAL)) {
            Object literal3 = previous().getLiteral();
            Intrinsics.checkNotNull(literal3, "null cannot be cast to non-null type kotlin.String");
            return new LiteralExpr(new LunoValue.String((String) literal3), previous().getLine());
        }
        if (match(TokenType.IDENTIFIER)) {
            return new VariableExpr(previous(), previous().getLine());
        }
        if (match(TokenType.F_STRING)) {
            return parseFString();
        }
        if (match(TokenType.FUN)) {
            return functionExpression();
        }
        if (match(TokenType.LPAREN)) {
            previous().getLine();
            Expression expression = expression();
            consume(TokenType.RPAREN, "Expect ')' after expression.");
            return expression;
        }
        if (match(TokenType.LBRACKET)) {
            return listLiteral();
        }
        if (peek().getType() == TokenType.LBRACE && isExpressionContext()) {
            return mapLiteral();
        }
        throw error(peek(), "Expect expression, found " + peek().getType() + ".");
    }

    private final Expression parseFString() {
        Token tokenPrevious = previous();
        Object literal = tokenPrevious.getLiteral();
        Intrinsics.checkNotNull(literal, "null cannot be cast to non-null type kotlin.String");
        String str = (String) literal;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < str.length()) {
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, '{', i, false, 4, (Object) null);
            if (iIndexOf$default == -1) {
                if (i >= str.length()) {
                    break;
                }
                String strSubstring = str.substring(i);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                arrayList.add(new LiteralExpr(new LunoValue.String(strSubstring), tokenPrevious.getLine()));
                break;
            }
            if (iIndexOf$default > i) {
                String strSubstring2 = str.substring(i, iIndexOf$default);
                Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                arrayList.add(new LiteralExpr(new LunoValue.String(strSubstring2), tokenPrevious.getLine()));
            }
            int iFindMatchingBrace = findMatchingBrace(str, iIndexOf$default);
            if (iFindMatchingBrace == -1) {
                throw error(tokenPrevious, "Unmatched '{' in f-string.");
            }
            String strSubstring3 = str.substring(iIndexOf$default + 1, iFindMatchingBrace);
            Intrinsics.checkNotNullExpressionValue(strSubstring3, "substring(...)");
            if (StringsKt.isBlank(strSubstring3)) {
                throw error(tokenPrevious, "Empty expression block '{}' in f-string is not allowed.");
            }
            try {
                arrayList.add(new Parser(new Lexer(strSubstring3).scanTokens()).expression());
                i = iFindMatchingBrace + 1;
            } catch (Exception unused) {
                throw error(tokenPrevious, "Invalid expression inside f-string: {" + strSubstring3 + "}");
            }
        }
        return new InterpolatedStringExpr(arrayList, tokenPrevious.getLine());
    }

    private final int findMatchingBrace(String content, int start) {
        int i = 1;
        int length = content.length();
        for (int i2 = start + 1; i2 < length; i2++) {
            char cCharAt = content.charAt(i2);
            if (cCharAt == '{') {
                i++;
            } else if (cCharAt == '}' && (i = i - 1) == 0) {
                return i2;
            }
        }
        return -1;
    }

    private final Expression listLiteral() {
        Token tokenPrevious = previous();
        ArrayList arrayList = new ArrayList();
        if (!check(TokenType.RBRACKET)) {
            do {
                arrayList.add(expression());
            } while (match(TokenType.COMMA));
        }
        consume(TokenType.RBRACKET, "Expect ']' after list elements.");
        return new ListLiteralExpr(arrayList, tokenPrevious, tokenPrevious.getLine());
    }

    private final Expression mapLiteral() {
        Token tokenConsume = consume(TokenType.LBRACE, "Expect '{' for map literal.");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (!check(TokenType.RBRACE)) {
            do {
                if (!check(TokenType.IDENTIFIER) && !check(TokenType.STRING_LITERAL)) {
                    throw error(peek(), "Expect identifier or string as map key.");
                }
                Token tokenAdvance = advance();
                consume(TokenType.COLON, "Expect ':' after map key.");
                linkedHashMap.put(tokenAdvance, expression());
            } while (match(TokenType.COMMA));
        }
        consume(TokenType.RBRACE, "Expect '}' after map entries.");
        return new MapLiteralExpr(linkedHashMap, tokenConsume, tokenConsume.getLine());
    }

    private final Statement statement() {
        skipComments();
        if (isAtEnd()) {
            throw error(peek(), "Unexpected end of file.");
        }
        if (match(TokenType.IF)) {
            return ifStatement();
        }
        if (match(TokenType.WHILE)) {
            return whileStatement();
        }
        if (match(TokenType.FOR)) {
            return forStatement();
        }
        if (match(TokenType.SWITCH)) {
            return switchStatement();
        }
        if (match(TokenType.RETURN)) {
            return returnStatement();
        }
        if (match(TokenType.BREAK)) {
            return breakStatement();
        }
        if (match(TokenType.CONTINUE)) {
            return continueStatement();
        }
        if (match(TokenType.TRY)) {
            return tryStatement();
        }
        if (match(TokenType.LBRACE)) {
            Token tokenPrevious = previous();
            List<Statement> listBlockStatements = blockStatements();
            consume(TokenType.RBRACE, "Expect '}' after block.");
            return new BlockStatement(listBlockStatements, tokenPrevious.getLine());
        }
        return expressionStatement();
    }

    private final Expression expression() {
        return logicalOr();
    }

    private final boolean isAssignmentOperator(TokenType tokenType) {
        return tokenType == TokenType.ASSIGN || tokenType == TokenType.PLUS_ASSIGN || tokenType == TokenType.MINUS_ASSIGN || tokenType == TokenType.MULTIPLY_ASSIGN || tokenType == TokenType.DIVIDE_ASSIGN || tokenType == TokenType.MODULO_ASSIGN;
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

    private final Token consume(TokenType type, String message) {
        Log.d("LunoScriptParser", "CONSUME: Attempting to consume " + type + ". Current peek is " + peek().getType() + " ('" + peek().getLexeme() + "')");
        if (check(type)) {
            Token tokenAdvance = advance();
            Log.d("LunoScriptParser", "CONSUME: Successfully consumed " + tokenAdvance.getType() + ". Next peek is " + (!isAtEnd() ? peek().getType() : "EOF"));
            return tokenAdvance;
        }
        Log.e("LunoScriptParser", "CONSUME_ERROR: Failed to consume " + type + ". Peek is " + peek().getType() + ". Throwing error: " + message);
        throw error(peek(), message);
    }

    private final void consumeSemicolon(String message) {
        if (match(TokenType.SEMICOLON) || check(TokenType.RBRACE) || isAtEnd() || previous().getLine() < peek().getLine() || this.tokens.get(this.current - 1).getType() == TokenType.RBRACE) {
            return;
        }
        this.tokens.get(this.current - 1).getType();
        TokenType tokenType = TokenType.LBRACE;
    }

    private final boolean check(TokenType type) {
        return !isAtEnd() && peek().getType() == type;
    }

    private final Token advance() {
        if (!isAtEnd()) {
            this.current++;
        }
        return previous();
    }

    private final boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private final Token peek() {
        return this.tokens.get(this.current);
    }

    private final Token previous() {
        return this.tokens.get(this.current - 1);
    }

    private final ParseError error(Token token, String message) {
        return new ParseError(message, token);
    }

    private final void synchronize() {
        Token tokenPeek = peek();
        Log.d("LunoScriptParser", "SYNC: Attempting to recover. Error occurred at or before: " + tokenPeek.getLexeme() + " (" + tokenPeek.getType() + ") L" + tokenPeek.getLine() + ". Previous: " + (this.current > 0 ? previous().getLexeme() : "N/A"));
        if (!isAtEnd()) {
            advance();
        }
        while (!isAtEnd()) {
            if (previous().getType() == TokenType.SEMICOLON) {
                Log.d("LunoScriptParser", "SYNC: Resuming after semicolon.");
                return;
            }
            switch (WhenMappings.$EnumSwitchMapping$0[peek().getType().ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    Log.d("LunoScriptParser", "SYNC: Resuming at keyword " + peek().getType() + ".");
                    return;
                default:
                    advance();
                    break;
            }
        }
        Log.d("LunoScriptParser", "SYNC: Recovery reached EOF.");
    }
}
