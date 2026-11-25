package org.jetbrains.kotlin.js.parser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.com.google.gwt.dev.js.JsAstMapper;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePosition;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.Context;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.IRFactory;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.JavaScriptException;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.Node;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.Parser;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.TokenStream;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;

/* JADX INFO: compiled from: parserUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001aR\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u001d\u0010\u000f\u001a\u0019\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0010¢\u0006\u0002\b\u0014H\u0082\b\u001a.\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0003\u001a6\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u0003\u001a8\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0018\u001aG\u0010\u001e\u001a\u0002H\u001f\"\u0004\b\u0000\u0010\u001f*\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00032\u001d\u0010 \u001a\u0019\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u0002H\u001f0\u0010¢\u0006\u0002\b\u0014H\u0082\b¢\u0006\u0002\u0010\"¨\u0006#"}, d2 = {"StringReader", "Ljava/io/Reader;", "string", "", TypedValues.CycleType.S_WAVE_OFFSET, "", "parse", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/Node;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "startPosition", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/CodePosition;", "reporter", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/ErrorReporter;", "insideFunction", "", "parseAction", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/Parser;", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/TokenStream;", "", "Lkotlin/ExtensionFunctionType;", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "scope", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", PsiTreeChangeEvent.PROP_FILE_NAME, "parseExpressionOrStatement", "parseFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "position", "toJsAst", ExifInterface.GPS_DIRECTION_TRUE, "mapAction", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/JsAstMapper;", "(Lcom/google/gwt/dev/js/rhino/Node;Lorg/jetbrains/kotlin/js/backend/ast/JsScope;Ljava/lang/String;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "js.parser"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ParserUtilsKt {
    public static final List<JsStatement> parse(String code, ErrorReporter reporter, JsScope scope, String fileName) {
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        boolean z = scope instanceof JsFunctionScope;
        CodePosition codePosition = new CodePosition(0, 0);
        Context.enter().setErrorReporter(reporter);
        try {
            TokenStream tokenStream = new TokenStream(StringReader(code, 0), "<parser>", codePosition);
            Node node = new Parser(new IRFactory(tokenStream), z).parse(tokenStream);
            if (!(node instanceof Node)) {
                node = null;
            }
            Context.exit();
            if (node != null) {
                return new JsAstMapper(scope, fileName).mapStatements(node);
            }
            return null;
        } catch (Throwable th) {
            Context.exit();
            throw th;
        }
    }

    public static final List<JsStatement> parseExpressionOrStatement(String code, ErrorReporter reporter, JsScope scope, CodePosition startPosition, String fileName) {
        Node nodeExpr;
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(startPosition, "startPosition");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        AccumulatingReporter accumulatingReporter = new AccumulatingReporter();
        try {
            Context.enter().setErrorReporter(accumulatingReporter);
            try {
                TokenStream tokenStream = new TokenStream(StringReader(code, 0), "<parser>", startPosition);
                nodeExpr = new Parser(new IRFactory(tokenStream), true).expr(tokenStream, false);
                if (tokenStream.getToken() != 0) {
                    accumulatingReporter.setHasErrors(true);
                }
                Intrinsics.checkNotNull(nodeExpr);
                Context.exit();
            } catch (Throwable th) {
                Context.exit();
                throw th;
            }
        } catch (JavaScriptException unused) {
            nodeExpr = null;
        }
        if (!accumulatingReporter.getHasErrors()) {
            for (AccumulatingReporter.Warning warning : accumulatingReporter.getWarnings()) {
                reporter.warning(warning.getMessage(), warning.getStartPosition(), warning.getEndPosition());
            }
            JsExpression jsExpressionMapExpression = nodeExpr != null ? new JsAstMapper(scope, fileName).mapExpression(nodeExpr) : null;
            if (jsExpressionMapExpression != null) {
                return CollectionsKt.listOf(new JsExpressionStatement(jsExpressionMapExpression));
            }
            return null;
        }
        Context.enter().setErrorReporter(reporter);
        try {
            TokenStream tokenStream2 = new TokenStream(StringReader(code, 0), "<parser>", startPosition);
            Node node = new Parser(new IRFactory(tokenStream2), true).parse(tokenStream2);
            if (!(node instanceof Node)) {
                node = null;
            }
            Context.exit();
            if (node != null) {
                return new JsAstMapper(scope, fileName).mapStatements(node);
            }
            return null;
        } catch (Throwable th2) {
            Context.exit();
            throw th2;
        }
    }

    private static final Reader StringReader(String str, int i) throws IOException {
        StringReader stringReader = new StringReader(str);
        stringReader.skip(i);
        return stringReader;
    }

    public static final JsFunction parseFunction(String code, String fileName, CodePosition position, int i, ErrorReporter reporter, JsScope scope) {
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Context.enter().setErrorReporter(reporter);
        try {
            TokenStream tokenStream = new TokenStream(StringReader(code, i), "<parser>", position);
            Parser parser = new Parser(new IRFactory(tokenStream), false);
            parser.addListener(new FunctionParsingObserver());
            Node nodePrimaryExpr = parser.primaryExpr(tokenStream);
            Intrinsics.checkNotNullExpressionValue(nodePrimaryExpr, "primaryExpr(...)");
            if (!(nodePrimaryExpr instanceof Node)) {
                nodePrimaryExpr = null;
            }
            Context.exit();
            if (nodePrimaryExpr != null) {
                return new JsAstMapper(scope, fileName).mapFunction(nodePrimaryExpr);
            }
            return null;
        } catch (Throwable th) {
            Context.exit();
            throw th;
        }
    }
}
