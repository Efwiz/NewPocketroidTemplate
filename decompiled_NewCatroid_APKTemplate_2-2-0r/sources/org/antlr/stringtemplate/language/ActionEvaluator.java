package org.antlr.stringtemplate.language;

import antlr.MismatchedTokenException;
import antlr.NoViableAltException;
import antlr.RecognitionException;
import antlr.TreeParser;
import antlr.collections.AST;
import antlr.collections.impl.BitSet;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateWriter;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes12.dex */
public class ActionEvaluator extends TreeParser implements ActionEvaluatorTokenTypes {
    public static final String[] _tokenNames = {"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "APPLY", "MULTI_APPLY", "ARGS", "INCLUDE", "\"if\"", "VALUE", "TEMPLATE", "FUNCTION", "SINGLEVALUEARG", "LIST", "NOTHING", "SEMI", "LPAREN", "RPAREN", "\"elseif\"", "COMMA", SchemaSymbols.ATTVAL_ID, "ASSIGN", "COLON", "NOT", "PLUS", "DOT", "\"first\"", "\"rest\"", "\"last\"", "\"length\"", "\"strip\"", "\"trunc\"", "\"super\"", "ANONYMOUS_TEMPLATE", "STRING", "INT", "LBRACK", "RBRACK", "DOTDOTDOT", "TEMPLATE_ARGS", "NESTED_ANONYMOUS_TEMPLATE", "ESC_CHAR", "WS", "WS_CHAR"};
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    protected ASTExpr chunk;
    protected StringTemplateWriter out;
    protected StringTemplate self;

    public static class NameValuePair {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f429name;
        public Object value;
    }

    public ActionEvaluator(StringTemplate stringTemplate, ASTExpr aSTExpr, StringTemplateWriter stringTemplateWriter) {
        this.self = stringTemplate;
        this.chunk = aSTExpr;
        this.out = stringTemplateWriter;
    }

    @Override // antlr.TreeParser
    public void reportError(RecognitionException recognitionException) {
        this.self.error("eval tree parse error", recognitionException);
    }

    public ActionEvaluator() {
        this.self = null;
        this.out = null;
        this.chunk = null;
        this.tokenNames = _tokenNames;
    }

    public final int action(AST ast) throws Throwable {
        int iWriteAttribute;
        if (ast != ASTNULL) {
        }
        try {
            Object objExpr = expr(ast);
            ast = this._retTree;
            iWriteAttribute = this.chunk.writeAttribute(this.self, objExpr, this.out);
        } catch (RecognitionException e) {
            reportError(e);
            iWriteAttribute = 0;
            if (ast != null) {
                ast = ast.getNextSibling();
            }
        }
        this._retTree = ast;
        return iWriteAttribute;
    }

    /* JADX WARN: Code duplicated, block: B:44:0x00af A[Catch: RecognitionException -> 0x00b6, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x00b6, blocks: (B:8:0x000b, B:9:0x000d, B:27:0x0032, B:28:0x0035, B:29:0x003a, B:30:0x003b, B:32:0x0057, B:33:0x005c, B:34:0x0063, B:35:0x006a, B:36:0x0071, B:38:0x0081, B:40:0x009e, B:43:0x00a8, B:44:0x00af), top: B:52:0x000b }] */
    /* JADX WARN: Code duplicated, block: B:49:0x00bc  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v2, types: [int] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r7v0, types: [org.antlr.stringtemplate.language.ActionEvaluator] */
    /* JADX WARN: Type inference failed for: r8v0, types: [antlr.collections.AST] */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12, types: [antlr.collections.AST] */
    /* JADX WARN: Type inference failed for: r8v13, types: [antlr.collections.AST] */
    /* JADX WARN: Type inference failed for: r8v14, types: [antlr.collections.AST] */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v18 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v2, types: [antlr.collections.AST] */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v3, types: [antlr.collections.AST] */
    /* JADX WARN: Type inference failed for: r8v4, types: [antlr.collections.AST] */
    /* JADX WARN: Type inference failed for: r8v5, types: [antlr.collections.AST] */
    /* JADX WARN: Type inference failed for: r8v6, types: [antlr.collections.AST] */
    /* JADX WARN: Type inference failed for: r8v7, types: [antlr.collections.AST] */
    /* JADX WARN: Type inference failed for: r8v9 */
    public final Object expr(AST ast) throws Throwable {
        ?? type;
        if (ast != ASTNULL) {
        }
        Object objTemplateApplication = null;
        ?? r8 = ast;
        if (ast == 0) {
            try {
                r8 = ASTNULL;
                type = r8.getType();
                if (type != 4 || type == 5) {
                    objTemplateApplication = templateApplication(r8);
                    ast = this._retTree;
                } else if (type == 7) {
                    objTemplateApplication = templateInclude(r8);
                    ast = this._retTree;
                } else {
                    try {
                        if (type == 9) {
                            match(r8, 9);
                            Object objExpr = expr(r8.getFirstChild());
                            AST ast2 = this._retTree;
                            AST nextSibling = r8.getNextSibling();
                            StringWriter stringWriter = new StringWriter();
                            StringTemplateWriter stringTemplateWriter = this.self.getGroup().getStringTemplateWriter(stringWriter);
                            type = stringWriter;
                            ast = nextSibling;
                            if (this.chunk.writeAttribute(this.self, objExpr, stringTemplateWriter) > 0) {
                                objTemplateApplication = stringWriter.toString();
                                type = stringWriter;
                                ast = nextSibling;
                            }
                        } else if (type == 11) {
                            objTemplateApplication = function(r8);
                            ast = this._retTree;
                        } else if (type != 13) {
                            if (type != 20) {
                                if (type == 24) {
                                    match(r8, 24);
                                    Object objExpr2 = expr(r8.getFirstChild());
                                    Object objExpr3 = expr(this._retTree);
                                    AST ast3 = this._retTree;
                                    objTemplateApplication = this.chunk.add(objExpr2, objExpr3);
                                    type = ast3;
                                    ast = r8.getNextSibling();
                                } else if (type != 25) {
                                    switch (type) {
                                        case 33:
                                        case 34:
                                        case 35:
                                            break;
                                        default:
                                            throw new NoViableAltException(r8);
                                    }
                                }
                            }
                            objTemplateApplication = attribute(r8);
                            ast = this._retTree;
                        } else {
                            objTemplateApplication = list(r8);
                            ast = this._retTree;
                        }
                    } catch (RecognitionException e) {
                        ?? r6 = type;
                        e = e;
                        ast = r6;
                        reportError(e);
                        if (ast != 0) {
                            ast = ast.getNextSibling();
                        }
                    }
                }
            } catch (RecognitionException e2) {
                e = e2;
                reportError(e);
                if (ast != 0) {
                    ast = ast.getNextSibling();
                }
                this._retTree = ast;
                return objTemplateApplication;
            }
        } else {
            type = r8.getType();
            if (type != 4) {
                objTemplateApplication = templateApplication(r8);
                ast = this._retTree;
            } else {
                objTemplateApplication = templateApplication(r8);
                ast = this._retTree;
            }
        }
        this._retTree = ast;
        return objTemplateApplication;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0020  */
    /* JADX WARN: Code duplicated, block: B:13:0x0023 A[Catch: RecognitionException -> 0x00c6, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x00c6, blocks: (B:8:0x0015, B:9:0x0017, B:13:0x0023, B:21:0x0076, B:27:0x0084, B:28:0x0089, B:29:0x008a, B:38:0x00b8), top: B:50:0x0015 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x003d A[Catch: RecognitionException -> 0x0081, LOOP:0: B:14:0x002d->B:18:0x003d, LOOP_END, TryCatch #2 {RecognitionException -> 0x0081, blocks: (B:15:0x002f, B:16:0x0031, B:18:0x003d, B:20:0x004b, B:23:0x007b, B:24:0x0080), top: B:52:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:20:0x004b A[Catch: RecognitionException -> 0x0081, TRY_LEAVE, TryCatch #2 {RecognitionException -> 0x0081, blocks: (B:15:0x002f, B:16:0x0031, B:18:0x003d, B:20:0x004b, B:23:0x007b, B:24:0x0080), top: B:52:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:23:0x007b A[Catch: RecognitionException -> 0x0081, TRY_ENTER, TryCatch #2 {RecognitionException -> 0x0081, blocks: (B:15:0x002f, B:16:0x0031, B:18:0x003d, B:20:0x004b, B:23:0x007b, B:24:0x0080), top: B:52:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:27:0x0084 A[Catch: RecognitionException -> 0x00c6, TRY_ENTER, TryCatch #0 {RecognitionException -> 0x00c6, blocks: (B:8:0x0015, B:9:0x0017, B:13:0x0023, B:21:0x0076, B:27:0x0084, B:28:0x0089, B:29:0x008a, B:38:0x00b8), top: B:50:0x0015 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x008a A[Catch: RecognitionException -> 0x00c6, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x00c6, blocks: (B:8:0x0015, B:9:0x0017, B:13:0x0023, B:21:0x0076, B:27:0x0084, B:28:0x0089, B:29:0x008a, B:38:0x00b8), top: B:50:0x0015 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x009c A[Catch: RecognitionException -> 0x00c3, TryCatch #1 {RecognitionException -> 0x00c3, blocks: (B:30:0x0094, B:32:0x009c, B:33:0x009e, B:35:0x00a6, B:37:0x00b0, B:40:0x00bd, B:41:0x00c2), top: B:51:0x0094 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x00a6 A[Catch: RecognitionException -> 0x00c3, LOOP:1: B:31:0x009a->B:35:0x00a6, LOOP_END, TryCatch #1 {RecognitionException -> 0x00c3, blocks: (B:30:0x0094, B:32:0x009c, B:33:0x009e, B:35:0x00a6, B:37:0x00b0, B:40:0x00bd, B:41:0x00c2), top: B:51:0x0094 }] */
    /* JADX WARN: Code duplicated, block: B:37:0x00b0 A[Catch: RecognitionException -> 0x00c3, TRY_LEAVE, TryCatch #1 {RecognitionException -> 0x00c3, blocks: (B:30:0x0094, B:32:0x009c, B:33:0x009e, B:35:0x00a6, B:37:0x00b0, B:40:0x00bd, B:41:0x00c2), top: B:51:0x0094 }] */
    /* JADX WARN: Code duplicated, block: B:40:0x00bd A[Catch: RecognitionException -> 0x00c3, TRY_ENTER, TryCatch #1 {RecognitionException -> 0x00c3, blocks: (B:30:0x0094, B:32:0x009c, B:33:0x009e, B:35:0x00a6, B:37:0x00b0, B:40:0x00bd, B:41:0x00c2), top: B:51:0x0094 }] */
    /* JADX WARN: Code duplicated, block: B:47:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:52:0x002f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:53:0x0049 A[EDGE_INSN: B:53:0x0049->B:19:0x0049 BREAK  A[LOOP:0: B:14:0x002d->B:18:0x003d], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:0x00ae A[EDGE_INSN: B:54:0x00ae->B:36:0x00ae BREAK  A[LOOP:1: B:31:0x009a->B:35:0x00a6], SYNTHETIC] */
    public final Object templateApplication(AST ast) throws Throwable {
        int type;
        int i;
        AST firstChild;
        Object objExpr;
        AST ast2;
        AST firstChild2;
        if (ast != ASTNULL) {
        }
        Vector vector = new Vector();
        ArrayList arrayList = new ArrayList();
        Object objApplyListOfAlternatingTemplates = null;
        if (ast == null) {
            try {
                ast = ASTNULL;
                type = ast.getType();
                i = 0;
                if (type != 4) {
                    match(ast, 4);
                    firstChild = ast.getFirstChild();
                    try {
                        objExpr = expr(firstChild);
                        ast2 = this._retTree;
                        while (true) {
                            if (ast2 == null) {
                                ast2 = ASTNULL;
                            }
                            if (ast2.getType() == 10) {
                                break;
                            }
                            template(ast2, vector);
                            ast2 = this._retTree;
                            i++;
                        }
                        if (i >= 1) {
                            throw new NoViableAltException(ast2);
                        }
                        objApplyListOfAlternatingTemplates = this.chunk.applyListOfAlternatingTemplates(this.self, objExpr, vector);
                        ast = ast.getNextSibling();
                    } catch (RecognitionException e) {
                        e = e;
                        ast = firstChild;
                        reportError(e);
                        if (ast != null) {
                            ast = ast.getNextSibling();
                        }
                    }
                } else if (type == 5) {
                    match(ast, 5);
                    firstChild2 = ast.getFirstChild();
                    while (true) {
                        if (firstChild2 == null) {
                            try {
                                firstChild2 = ASTNULL;
                            } catch (RecognitionException e2) {
                                e = e2;
                                ast = firstChild2;
                                reportError(e);
                                if (ast != null) {
                                    ast = ast.getNextSibling();
                                }
                            }
                        }
                        if (_tokenSet_0.member(firstChild2.getType())) {
                            break;
                        }
                        Object objExpr2 = expr(firstChild2);
                        firstChild2 = this._retTree;
                        arrayList.add(objExpr2);
                        i++;
                    }
                    if (i >= 1) {
                        throw new NoViableAltException(firstChild2);
                    }
                    match(firstChild2, 22);
                    AST nextSibling = firstChild2.getNextSibling();
                    StringTemplateAST stringTemplateAST = (StringTemplateAST) nextSibling;
                    match(nextSibling, 33);
                    nextSibling.getNextSibling();
                    vector.addElement(stringTemplateAST.getStringTemplate());
                    objApplyListOfAlternatingTemplates = this.chunk.applyTemplateToListOfAttributes(this.self, arrayList, stringTemplateAST.getStringTemplate());
                    ast = ast.getNextSibling();
                } else {
                    throw new NoViableAltException(ast);
                }
            } catch (RecognitionException e3) {
                e = e3;
                reportError(e);
                if (ast != null) {
                    ast = ast.getNextSibling();
                }
                this._retTree = ast;
                return objApplyListOfAlternatingTemplates;
            }
        } else {
            type = ast.getType();
            i = 0;
            if (type != 4) {
                match(ast, 4);
                firstChild = ast.getFirstChild();
                objExpr = expr(firstChild);
                ast2 = this._retTree;
                while (true) {
                    if (ast2 == null) {
                        ast2 = ASTNULL;
                    }
                    if (ast2.getType() == 10) {
                        break;
                        break;
                    }
                    template(ast2, vector);
                    ast2 = this._retTree;
                    i++;
                }
                if (i >= 1) {
                    throw new NoViableAltException(ast2);
                }
                objApplyListOfAlternatingTemplates = this.chunk.applyListOfAlternatingTemplates(this.self, objExpr, vector);
                ast = ast.getNextSibling();
            } else if (type == 5) {
                match(ast, 5);
                firstChild2 = ast.getFirstChild();
                while (true) {
                    if (firstChild2 == null) {
                        firstChild2 = ASTNULL;
                    }
                    if (_tokenSet_0.member(firstChild2.getType())) {
                        break;
                        break;
                    }
                    Object objExpr3 = expr(firstChild2);
                    firstChild2 = this._retTree;
                    arrayList.add(objExpr3);
                    i++;
                }
                if (i >= 1) {
                    throw new NoViableAltException(firstChild2);
                }
                match(firstChild2, 22);
                AST nextSibling2 = firstChild2.getNextSibling();
                StringTemplateAST stringTemplateAST2 = (StringTemplateAST) nextSibling2;
                match(nextSibling2, 33);
                nextSibling2.getNextSibling();
                vector.addElement(stringTemplateAST2.getStringTemplate());
                objApplyListOfAlternatingTemplates = this.chunk.applyTemplateToListOfAttributes(this.self, arrayList, stringTemplateAST2.getStringTemplate());
                ast = ast.getNextSibling();
            } else {
                throw new NoViableAltException(ast);
            }
        }
        this._retTree = ast;
        return objApplyListOfAlternatingTemplates;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0015  */
    /* JADX WARN: Code duplicated, block: B:13:0x0019 A[Catch: RecognitionException -> 0x00f1, TryCatch #2 {RecognitionException -> 0x00f1, blocks: (B:8:0x000b, B:9:0x000d, B:13:0x0019, B:14:0x001c, B:15:0x0021, B:16:0x0022, B:17:0x0039, B:18:0x004b, B:20:0x0061, B:21:0x007c, B:37:0x00c7, B:42:0x00dc), top: B:54:0x000b }] */
    /* JADX WARN: Code duplicated, block: B:14:0x001c A[Catch: RecognitionException -> 0x00f1, TryCatch #2 {RecognitionException -> 0x00f1, blocks: (B:8:0x000b, B:9:0x000d, B:13:0x0019, B:14:0x001c, B:15:0x0021, B:16:0x0022, B:17:0x0039, B:18:0x004b, B:20:0x0061, B:21:0x007c, B:37:0x00c7, B:42:0x00dc), top: B:54:0x000b }] */
    /* JADX WARN: Code duplicated, block: B:16:0x0022 A[Catch: RecognitionException -> 0x00f1, TryCatch #2 {RecognitionException -> 0x00f1, blocks: (B:8:0x000b, B:9:0x000d, B:13:0x0019, B:14:0x001c, B:15:0x0021, B:16:0x0022, B:17:0x0039, B:18:0x004b, B:20:0x0061, B:21:0x007c, B:37:0x00c7, B:42:0x00dc), top: B:54:0x000b }] */
    /* JADX WARN: Code duplicated, block: B:17:0x0039 A[Catch: RecognitionException -> 0x00f1, TryCatch #2 {RecognitionException -> 0x00f1, blocks: (B:8:0x000b, B:9:0x000d, B:13:0x0019, B:14:0x001c, B:15:0x0021, B:16:0x0022, B:17:0x0039, B:18:0x004b, B:20:0x0061, B:21:0x007c, B:37:0x00c7, B:42:0x00dc), top: B:54:0x000b }] */
    /* JADX WARN: Code duplicated, block: B:18:0x004b A[Catch: RecognitionException -> 0x00f1, TryCatch #2 {RecognitionException -> 0x00f1, blocks: (B:8:0x000b, B:9:0x000d, B:13:0x0019, B:14:0x001c, B:15:0x0021, B:16:0x0022, B:17:0x0039, B:18:0x004b, B:20:0x0061, B:21:0x007c, B:37:0x00c7, B:42:0x00dc), top: B:54:0x000b }] */
    /* JADX WARN: Code duplicated, block: B:20:0x0061 A[Catch: RecognitionException -> 0x00f1, TryCatch #2 {RecognitionException -> 0x00f1, blocks: (B:8:0x000b, B:9:0x000d, B:13:0x0019, B:14:0x001c, B:15:0x0021, B:16:0x0022, B:17:0x0039, B:18:0x004b, B:20:0x0061, B:21:0x007c, B:37:0x00c7, B:42:0x00dc), top: B:54:0x000b }] */
    /* JADX WARN: Code duplicated, block: B:21:0x007c A[Catch: RecognitionException -> 0x00f1, TRY_LEAVE, TryCatch #2 {RecognitionException -> 0x00f1, blocks: (B:8:0x000b, B:9:0x000d, B:13:0x0019, B:14:0x001c, B:15:0x0021, B:16:0x0022, B:17:0x0039, B:18:0x004b, B:20:0x0061, B:21:0x007c, B:37:0x00c7, B:42:0x00dc), top: B:54:0x000b }] */
    /* JADX WARN: Code duplicated, block: B:24:0x008e A[Catch: RecognitionException -> 0x00d7, TryCatch #0 {RecognitionException -> 0x00d7, blocks: (B:22:0x0086, B:24:0x008e, B:25:0x0090, B:28:0x009a, B:29:0x00a9, B:30:0x00ae, B:31:0x00af, B:33:0x00bf), top: B:51:0x0086 }] */
    /* JADX WARN: Code duplicated, block: B:27:0x0098 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:28:0x009a A[Catch: RecognitionException -> 0x00d7, TryCatch #0 {RecognitionException -> 0x00d7, blocks: (B:22:0x0086, B:24:0x008e, B:25:0x0090, B:28:0x009a, B:29:0x00a9, B:30:0x00ae, B:31:0x00af, B:33:0x00bf), top: B:51:0x0086 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x00a9 A[Catch: RecognitionException -> 0x00d7, TryCatch #0 {RecognitionException -> 0x00d7, blocks: (B:22:0x0086, B:24:0x008e, B:25:0x0090, B:28:0x009a, B:29:0x00a9, B:30:0x00ae, B:31:0x00af, B:33:0x00bf), top: B:51:0x0086 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x00af A[Catch: RecognitionException -> 0x00d7, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x00d7, blocks: (B:22:0x0086, B:24:0x008e, B:25:0x0090, B:28:0x009a, B:29:0x00a9, B:30:0x00ae, B:31:0x00af, B:33:0x00bf), top: B:51:0x0086 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x00c4  */
    /* JADX WARN: Code duplicated, block: B:36:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:42:0x00dc A[Catch: RecognitionException -> 0x00f1, TRY_LEAVE, TryCatch #2 {RecognitionException -> 0x00f1, blocks: (B:8:0x000b, B:9:0x000d, B:13:0x0019, B:14:0x001c, B:15:0x0021, B:16:0x0022, B:17:0x0039, B:18:0x004b, B:20:0x0061, B:21:0x007c, B:37:0x00c7, B:42:0x00dc), top: B:54:0x000b }] */
    /* JADX WARN: Code duplicated, block: B:47:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:48:0x00fb A[PHI: r0 r8
      0x00fb: PHI (r0v2 java.lang.String) = (r0v1 java.lang.String), (r0v1 java.lang.String), (r0v3 java.lang.String) binds: [B:46:0x00f5, B:47:0x00f7, B:19:0x005f] A[DONT_GENERATE, DONT_INLINE]
      0x00fb: PHI (r8v4 antlr.collections.AST) = (r8v2 antlr.collections.AST), (r8v3 antlr.collections.AST), (r8v13 antlr.collections.AST) binds: [B:46:0x00f5, B:47:0x00f7, B:19:0x005f] A[DONT_GENERATE, DONT_INLINE]] */
    public final Object attribute(AST ast) throws RecognitionException {
        Object attribute;
        int type;
        AST firstChild;
        AST ast2;
        int type2;
        AST firstChild2;
        Object objExpr;
        Object text;
        StringTemplateAST stringTemplateAST;
        if (ast != ASTNULL) {
        }
        String text2 = null;
        if (ast == null) {
            try {
                ast = ASTNULL;
                type = ast.getType();
                if (type != 20) {
                    StringTemplateAST stringTemplateAST2 = (StringTemplateAST) ast;
                    match(ast, 20);
                    ast = ast.getNextSibling();
                    attribute = this.self.getAttribute(stringTemplateAST2.getText());
                } else if (type != 25) {
                    match(ast, 25);
                    firstChild = ast.getFirstChild();
                    try {
                        Object objExpr2 = expr(firstChild);
                        ast2 = this._retTree;
                        if (ast2 == null) {
                            ast2 = ASTNULL;
                        }
                        type2 = ast2.getType();
                        if (type2 != 9) {
                            match(ast2, 9);
                            firstChild2 = ast2.getFirstChild();
                            try {
                                objExpr = expr(firstChild2);
                                AST ast3 = this._retTree;
                                ast2.getNextSibling();
                                if (objExpr != null) {
                                    text = objExpr;
                                } else {
                                    text = null;
                                }
                            } catch (RecognitionException e) {
                                e = e;
                                ast = firstChild2;
                                reportError(e);
                                if (ast != null) {
                                    ast = ast.getNextSibling();
                                }
                                attribute = text2;
                            }
                        } else if (type2 == 20) {
                            match(ast2, 20);
                            ast2.getNextSibling();
                            text = ((StringTemplateAST) ast2).getText();
                        } else {
                            throw new NoViableAltException(ast2);
                        }
                        ast = ast.getNextSibling();
                        attribute = this.chunk.getObjectProperty(this.self, objExpr2, text);
                    } catch (RecognitionException e2) {
                        e = e2;
                        ast = firstChild;
                    }
                } else {
                    switch (type) {
                        case 33:
                            stringTemplateAST = (StringTemplateAST) ast;
                            match(ast, 33);
                            ast = ast.getNextSibling();
                            text2 = stringTemplateAST.getText();
                            if (stringTemplateAST.getText() != null) {
                                attribute = text2;
                            } else {
                                StringTemplate stringTemplate = new StringTemplate(this.self.getGroup(), stringTemplateAST.getText());
                                stringTemplate.setEnclosingInstance(this.self);
                                stringTemplate.setName("<anonymous template argument>");
                                attribute = stringTemplate;
                            }
                            break;
                        case 34:
                            StringTemplateAST stringTemplateAST3 = (StringTemplateAST) ast;
                            match(ast, 34);
                            ast = ast.getNextSibling();
                            attribute = stringTemplateAST3.getText();
                            break;
                        case 35:
                            StringTemplateAST stringTemplateAST4 = (StringTemplateAST) ast;
                            match(ast, 35);
                            ast = ast.getNextSibling();
                            attribute = new Integer(stringTemplateAST4.getText());
                            break;
                        default:
                            throw new NoViableAltException(ast);
                    }
                }
            } catch (RecognitionException e3) {
                e = e3;
                reportError(e);
                if (ast != null) {
                    ast = ast.getNextSibling();
                }
                attribute = text2;
                this._retTree = ast;
                return attribute;
            }
        } else {
            type = ast.getType();
            if (type != 20) {
                StringTemplateAST stringTemplateAST5 = (StringTemplateAST) ast;
                match(ast, 20);
                ast = ast.getNextSibling();
                attribute = this.self.getAttribute(stringTemplateAST5.getText());
            } else if (type != 25) {
                match(ast, 25);
                firstChild = ast.getFirstChild();
                Object objExpr3 = expr(firstChild);
                ast2 = this._retTree;
                if (ast2 == null) {
                    ast2 = ASTNULL;
                }
                type2 = ast2.getType();
                if (type2 != 9) {
                    match(ast2, 9);
                    firstChild2 = ast2.getFirstChild();
                    objExpr = expr(firstChild2);
                    AST ast4 = this._retTree;
                    ast2.getNextSibling();
                    if (objExpr != null) {
                        text = objExpr;
                    } else {
                        text = null;
                    }
                } else if (type2 == 20) {
                    match(ast2, 20);
                    ast2.getNextSibling();
                    text = ((StringTemplateAST) ast2).getText();
                } else {
                    throw new NoViableAltException(ast2);
                }
                ast = ast.getNextSibling();
                attribute = this.chunk.getObjectProperty(this.self, objExpr3, text);
            } else {
                switch (type) {
                    case 33:
                        stringTemplateAST = (StringTemplateAST) ast;
                        match(ast, 33);
                        ast = ast.getNextSibling();
                        text2 = stringTemplateAST.getText();
                        if (stringTemplateAST.getText() != null) {
                            attribute = text2;
                        } else {
                            StringTemplate stringTemplate2 = new StringTemplate(this.self.getGroup(), stringTemplateAST.getText());
                            stringTemplate2.setEnclosingInstance(this.self);
                            stringTemplate2.setName("<anonymous template argument>");
                            attribute = stringTemplate2;
                        }
                        break;
                    case 34:
                        StringTemplateAST stringTemplateAST6 = (StringTemplateAST) ast;
                        match(ast, 34);
                        ast = ast.getNextSibling();
                        attribute = stringTemplateAST6.getText();
                        break;
                    case 35:
                        StringTemplateAST stringTemplateAST7 = (StringTemplateAST) ast;
                        match(ast, 35);
                        ast = ast.getNextSibling();
                        attribute = new Integer(stringTemplateAST7.getText());
                        break;
                    default:
                        throw new NoViableAltException(ast);
                }
            }
        }
        this._retTree = ast;
        return attribute;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0020  */
    /* JADX WARN: Code duplicated, block: B:14:0x0024 A[Catch: RecognitionException -> 0x0085, TryCatch #1 {RecognitionException -> 0x0085, blocks: (B:9:0x0016, B:10:0x0018, B:14:0x0024, B:16:0x0033, B:17:0x003c, B:18:0x0041, B:19:0x0042, B:20:0x0047, B:21:0x0048, B:25:0x0060, B:27:0x0066), top: B:51:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x0033 A[Catch: RecognitionException -> 0x0085, TryCatch #1 {RecognitionException -> 0x0085, blocks: (B:9:0x0016, B:10:0x0018, B:14:0x0024, B:16:0x0033, B:17:0x003c, B:18:0x0041, B:19:0x0042, B:20:0x0047, B:21:0x0048, B:25:0x0060, B:27:0x0066), top: B:51:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:17:0x003c A[Catch: RecognitionException -> 0x0085, TryCatch #1 {RecognitionException -> 0x0085, blocks: (B:9:0x0016, B:10:0x0018, B:14:0x0024, B:16:0x0033, B:17:0x003c, B:18:0x0041, B:19:0x0042, B:20:0x0047, B:21:0x0048, B:25:0x0060, B:27:0x0066), top: B:51:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:19:0x0042 A[Catch: RecognitionException -> 0x0085, TryCatch #1 {RecognitionException -> 0x0085, blocks: (B:9:0x0016, B:10:0x0018, B:14:0x0024, B:16:0x0033, B:17:0x003c, B:18:0x0041, B:19:0x0042, B:20:0x0047, B:21:0x0048, B:25:0x0060, B:27:0x0066), top: B:51:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:21:0x0048 A[Catch: RecognitionException -> 0x0085, TRY_LEAVE, TryCatch #1 {RecognitionException -> 0x0085, blocks: (B:9:0x0016, B:10:0x0018, B:14:0x0024, B:16:0x0033, B:17:0x003c, B:18:0x0041, B:19:0x0042, B:20:0x0047, B:21:0x0048, B:25:0x0060, B:27:0x0066), top: B:51:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:24:0x005d A[Catch: RecognitionException -> 0x0082, TRY_LEAVE, TryCatch #2 {RecognitionException -> 0x0082, blocks: (B:22:0x0052, B:24:0x005d, B:35:0x007c, B:36:0x0081), top: B:52:0x0052 }] */
    /* JADX WARN: Code duplicated, block: B:27:0x0066 A[Catch: RecognitionException -> 0x0085, TRY_LEAVE, TryCatch #1 {RecognitionException -> 0x0085, blocks: (B:9:0x0016, B:10:0x0018, B:14:0x0024, B:16:0x0033, B:17:0x003c, B:18:0x0041, B:19:0x0042, B:20:0x0047, B:21:0x0048, B:25:0x0060, B:27:0x0066), top: B:51:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x006b  */
    /* JADX WARN: Code duplicated, block: B:33:0x0073 A[Catch: RecognitionException -> 0x0087, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x0087, blocks: (B:7:0x0009, B:31:0x006d, B:33:0x0073), top: B:49:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x007c A[Catch: RecognitionException -> 0x0082, TRY_ENTER, TryCatch #2 {RecognitionException -> 0x0082, blocks: (B:22:0x0052, B:24:0x005d, B:35:0x007c, B:36:0x0081), top: B:52:0x0052 }] */
    /* JADX WARN: Code duplicated, block: B:45:0x0090  */
    /* JADX WARN: Code duplicated, block: B:46:0x0095  */
    public final Object templateInclude(AST ast) throws Throwable {
        AST firstChild;
        RecognitionException e;
        int type;
        AST firstChild2;
        Object objExpr;
        AST ast2;
        StringTemplateAST stringTemplateAST;
        String string;
        StringTemplateAST stringTemplateAST2;
        StringTemplateAST stringTemplateAST3;
        AST nextSibling;
        if (ast != ASTNULL) {
        }
        StringTemplate templateInclude = null;
        try {
            match(ast, 7);
            firstChild = ast.getFirstChild();
            if (firstChild == null) {
                try {
                    firstChild = ASTNULL;
                    type = firstChild.getType();
                    if (type != 9) {
                        match(firstChild, 9);
                        firstChild2 = firstChild.getFirstChild();
                        try {
                            objExpr = expr(firstChild2);
                            ast2 = this._retTree;
                            stringTemplateAST = (StringTemplateAST) ast2;
                            if (ast2 != null) {
                                throw new MismatchedTokenException();
                            }
                            ast2.getNextSibling();
                            firstChild.getNextSibling();
                            if (objExpr != null) {
                                string = objExpr.toString();
                            } else {
                                string = null;
                            }
                            stringTemplateAST2 = stringTemplateAST;
                        } catch (RecognitionException e2) {
                            e = e2;
                            firstChild = firstChild2;
                            reportError(e);
                            if (firstChild != null) {
                                ast = firstChild.getNextSibling();
                            } else {
                                ast = firstChild;
                            }
                        }
                    } else if (type == 20) {
                        stringTemplateAST3 = (StringTemplateAST) firstChild;
                        match(firstChild, 20);
                        nextSibling = firstChild.getNextSibling();
                        stringTemplateAST2 = (StringTemplateAST) nextSibling;
                        if (nextSibling != null) {
                            throw new MismatchedTokenException();
                        }
                        nextSibling.getNextSibling();
                        string = stringTemplateAST3.getText();
                    } else {
                        throw new NoViableAltException(firstChild);
                    }
                    ast = ast.getNextSibling();
                    if (string != null) {
                        templateInclude = this.chunk.getTemplateInclude(this.self, string, stringTemplateAST2);
                    }
                } catch (RecognitionException e3) {
                    e = e3;
                    reportError(e);
                    if (firstChild != null) {
                        ast = firstChild.getNextSibling();
                    } else {
                        ast = firstChild;
                    }
                    this._retTree = ast;
                    return templateInclude;
                }
            } else {
                type = firstChild.getType();
                if (type != 9) {
                    match(firstChild, 9);
                    firstChild2 = firstChild.getFirstChild();
                    objExpr = expr(firstChild2);
                    ast2 = this._retTree;
                    stringTemplateAST = (StringTemplateAST) ast2;
                    if (ast2 != null) {
                        throw new MismatchedTokenException();
                    }
                    ast2.getNextSibling();
                    firstChild.getNextSibling();
                    if (objExpr != null) {
                        string = objExpr.toString();
                    } else {
                        string = null;
                    }
                    stringTemplateAST2 = stringTemplateAST;
                } else if (type == 20) {
                    stringTemplateAST3 = (StringTemplateAST) firstChild;
                    match(firstChild, 20);
                    nextSibling = firstChild.getNextSibling();
                    stringTemplateAST2 = (StringTemplateAST) nextSibling;
                    if (nextSibling != null) {
                        throw new MismatchedTokenException();
                    }
                    nextSibling.getNextSibling();
                    string = stringTemplateAST3.getText();
                } else {
                    throw new NoViableAltException(firstChild);
                }
                ast = ast.getNextSibling();
                if (string != null) {
                    templateInclude = this.chunk.getTemplateInclude(this.self, string, stringTemplateAST2);
                }
            }
        } catch (RecognitionException e4) {
            firstChild = ast;
            e = e4;
        }
        this._retTree = ast;
        return templateInclude;
    }

    public final Object function(AST ast) throws RecognitionException {
        AST firstChild;
        RecognitionException e;
        AST nextSibling;
        if (ast != ASTNULL) {
        }
        Object objFirst = null;
        try {
            match(ast, 11);
            firstChild = ast.getFirstChild();
            if (firstChild == null) {
                try {
                    firstChild = ASTNULL;
                } catch (RecognitionException e2) {
                    e = e2;
                    reportError(e);
                    nextSibling = firstChild != null ? firstChild.getNextSibling() : firstChild;
                }
            }
            switch (firstChild.getType()) {
                case 26:
                    match(firstChild, 26);
                    Object objSingleFunctionArg = singleFunctionArg(firstChild.getNextSibling());
                    AST ast2 = this._retTree;
                    objFirst = this.chunk.first(objSingleFunctionArg);
                    break;
                case 27:
                    match(firstChild, 27);
                    Object objSingleFunctionArg2 = singleFunctionArg(firstChild.getNextSibling());
                    AST ast3 = this._retTree;
                    objFirst = this.chunk.rest(objSingleFunctionArg2);
                    break;
                case 28:
                    match(firstChild, 28);
                    Object objSingleFunctionArg3 = singleFunctionArg(firstChild.getNextSibling());
                    AST ast4 = this._retTree;
                    objFirst = this.chunk.last(objSingleFunctionArg3);
                    break;
                case 29:
                    match(firstChild, 29);
                    Object objSingleFunctionArg4 = singleFunctionArg(firstChild.getNextSibling());
                    AST ast5 = this._retTree;
                    objFirst = this.chunk.length(objSingleFunctionArg4);
                    break;
                case 30:
                    match(firstChild, 30);
                    Object objSingleFunctionArg5 = singleFunctionArg(firstChild.getNextSibling());
                    AST ast6 = this._retTree;
                    objFirst = this.chunk.strip(objSingleFunctionArg5);
                    break;
                case 31:
                    match(firstChild, 31);
                    Object objSingleFunctionArg6 = singleFunctionArg(firstChild.getNextSibling());
                    AST ast7 = this._retTree;
                    objFirst = this.chunk.trunc(objSingleFunctionArg6);
                    break;
                default:
                    throw new NoViableAltException(firstChild);
            }
            nextSibling = ast.getNextSibling();
        } catch (RecognitionException e3) {
            firstChild = ast;
            e = e3;
        }
        this._retTree = nextSibling;
        return objFirst;
    }

    /* JADX WARN: Code duplicated, block: B:41:0x007a A[Catch: RecognitionException -> 0x0080, TRY_LEAVE, TryCatch #1 {RecognitionException -> 0x0080, blocks: (B:10:0x001c, B:11:0x001e, B:36:0x0055, B:37:0x005a, B:38:0x005b, B:39:0x0072, B:41:0x007a), top: B:55:0x001c }] */
    /* JADX WARN: Code duplicated, block: B:49:0x008b  */
    /* JADX WARN: Code duplicated, block: B:50:0x0090  */
    /* JADX WARN: Code duplicated, block: B:60:0x007d A[SYNTHETIC] */
    public final Object list(AST ast) throws RecognitionException {
        AST firstChild;
        RecognitionException e;
        Cat cat;
        Object objExpr;
        if (ast != ASTNULL) {
        }
        ArrayList arrayList = new ArrayList();
        try {
            match(ast, 13);
            firstChild = ast.getFirstChild();
            int i = 0;
            while (true) {
                if (firstChild == null) {
                    try {
                        firstChild = ASTNULL;
                    } catch (RecognitionException e2) {
                        e = e2;
                        reportError(e);
                        cat = null;
                        if (firstChild != null) {
                            ast = firstChild.getNextSibling();
                        } else {
                            ast = firstChild;
                        }
                    }
                }
                int type = firstChild.getType();
                if (type == 4 || type == 5 || type == 7 || type == 9 || type == 11 || type == 20 || type == 13) {
                    objExpr = expr(firstChild);
                    firstChild = this._retTree;
                    if (objExpr != null) {
                        arrayList.add(objExpr);
                    }
                } else if (type != 14) {
                    if (type != 24 && type != 25) {
                        switch (type) {
                            case 33:
                            case 34:
                            case 35:
                                break;
                            default:
                                if (i < 1) {
                                    throw new NoViableAltException(firstChild);
                                }
                                ast = ast.getNextSibling();
                                cat = new Cat(arrayList);
                                this._retTree = ast;
                                return cat;
                                reportError(e);
                                cat = null;
                                if (firstChild != null) {
                                    ast = firstChild.getNextSibling();
                                } else {
                                    ast = firstChild;
                                }
                                this._retTree = ast;
                                return cat;
                        }
                    }
                    objExpr = expr(firstChild);
                    firstChild = this._retTree;
                    if (objExpr != null) {
                        arrayList.add(objExpr);
                    }
                } else {
                    match(firstChild, 14);
                    firstChild = firstChild.getNextSibling();
                    arrayList.add(new ArrayList() { // from class: org.antlr.stringtemplate.language.ActionEvaluator.1
                        {
                            add(null);
                        }
                    }.iterator());
                }
                i++;
            }
        } catch (RecognitionException e3) {
            firstChild = ast;
            e = e3;
        }
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:47:0x00ce  */
    public final void template(AST ast, Vector vector) throws Throwable {
        AST firstChild;
        RecognitionException e;
        AST nextSibling;
        if (ast != ASTNULL) {
        }
        try {
            match(ast, 10);
            firstChild = ast.getFirstChild();
            if (firstChild == null) {
                try {
                    firstChild = ASTNULL;
                } catch (RecognitionException e2) {
                    e = e2;
                    reportError(e);
                    if (firstChild != null) {
                        nextSibling = firstChild.getNextSibling();
                    } else {
                        nextSibling = firstChild;
                    }
                    this._retTree = nextSibling;
                }
            }
            int type = firstChild.getType();
            if (type == 9) {
                match(firstChild, 9);
                AST firstChild2 = firstChild.getFirstChild();
                try {
                    Object objExpr = expr(firstChild2);
                    AST ast2 = this._retTree;
                    StringTemplateAST stringTemplateAST = (StringTemplateAST) ast2;
                    if (ast2 == null) {
                        throw new MismatchedTokenException();
                    }
                    ast2.getNextSibling();
                    if (objExpr != null) {
                        StringTemplate embeddedInstanceOf = this.self.getGroup().getEmbeddedInstanceOf(this.self, objExpr.toString());
                        if (embeddedInstanceOf != null) {
                            embeddedInstanceOf.setArgumentsAST(stringTemplateAST);
                            vector.addElement(embeddedInstanceOf);
                        }
                    }
                    firstChild.getNextSibling();
                } catch (RecognitionException e3) {
                    e = e3;
                    firstChild = firstChild2;
                    reportError(e);
                    if (firstChild != null) {
                        nextSibling = firstChild.getNextSibling();
                    } else {
                        nextSibling = firstChild;
                    }
                }
            } else if (type == 20) {
                StringTemplateAST stringTemplateAST2 = (StringTemplateAST) firstChild;
                match(firstChild, 20);
                AST nextSibling2 = firstChild.getNextSibling();
                StringTemplateAST stringTemplateAST3 = (StringTemplateAST) nextSibling2;
                if (nextSibling2 == null) {
                    throw new MismatchedTokenException();
                }
                nextSibling2.getNextSibling();
                StringTemplate embeddedInstanceOf2 = this.self.getGroup().getEmbeddedInstanceOf(this.self, stringTemplateAST2.getText());
                if (embeddedInstanceOf2 != null) {
                    embeddedInstanceOf2.setArgumentsAST(stringTemplateAST3);
                    vector.addElement(embeddedInstanceOf2);
                }
            } else if (type == 33) {
                match(firstChild, 33);
                firstChild.getNextSibling();
                StringTemplate stringTemplate = ((StringTemplateAST) firstChild).getStringTemplate();
                stringTemplate.setGroup(this.self.getGroup());
                vector.addElement(stringTemplate);
            } else {
                throw new NoViableAltException(firstChild);
            }
            nextSibling = ast.getNextSibling();
        } catch (RecognitionException e4) {
            firstChild = ast;
            e = e4;
        }
        this._retTree = nextSibling;
    }

    public final Object singleFunctionArg(AST ast) throws RecognitionException {
        if (ast != ASTNULL) {
        }
        Object objExpr = null;
        try {
            match(ast, 12);
            AST firstChild = ast.getFirstChild();
            try {
                objExpr = expr(firstChild);
                AST ast2 = this._retTree;
                ast = ast.getNextSibling();
            } catch (RecognitionException e) {
                e = e;
                ast = firstChild;
                reportError(e);
                if (ast != null) {
                    ast = ast.getNextSibling();
                }
            }
        } catch (RecognitionException e2) {
            e = e2;
        }
        this._retTree = ast;
        return objExpr;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0059 A[Catch: RecognitionException -> 0x0066, FALL_THROUGH, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x0066, blocks: (B:7:0x000a, B:8:0x000c, B:22:0x0029, B:23:0x002c, B:24:0x002f, B:25:0x0034, B:26:0x0035, B:28:0x0047, B:31:0x0059), top: B:39:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:36:0x006d  */
    public final boolean ifCondition(AST ast) throws Throwable {
        boolean zTestAttributeTrue;
        int type;
        if (ast != ASTNULL) {
        }
        if (ast == null) {
            try {
                ast = ASTNULL;
                type = ast.getType();
                if (type != 4 || type == 5 || type == 7 || type == 9 || type == 11 || type == 13 || type == 20) {
                    Object objIfAtom = ifAtom(ast);
                    ast = this._retTree;
                    zTestAttributeTrue = this.chunk.testAttributeTrue(objIfAtom);
                } else {
                    switch (type) {
                        case 23:
                            match(ast, 23);
                            AST firstChild = ast.getFirstChild();
                            try {
                                Object objIfAtom2 = ifAtom(firstChild);
                                AST ast2 = this._retTree;
                                ast = ast.getNextSibling();
                                zTestAttributeTrue = !this.chunk.testAttributeTrue(objIfAtom2);
                            } catch (RecognitionException e) {
                                e = e;
                                ast = firstChild;
                                reportError(e);
                                zTestAttributeTrue = false;
                                if (ast != null) {
                                    ast = ast.getNextSibling();
                                }
                            }
                            break;
                        default:
                            switch (type) {
                                case 33:
                                case 34:
                                case 35:
                                    break;
                                default:
                                    throw new NoViableAltException(ast);
                            }
                        case 24:
                        case 25:
                            Object objIfAtom3 = ifAtom(ast);
                            ast = this._retTree;
                            zTestAttributeTrue = this.chunk.testAttributeTrue(objIfAtom3);
                            break;
                    }
                }
            } catch (RecognitionException e2) {
                e = e2;
                reportError(e);
                zTestAttributeTrue = false;
                if (ast != null) {
                    ast = ast.getNextSibling();
                }
                this._retTree = ast;
                return zTestAttributeTrue;
            }
        } else {
            type = ast.getType();
            if (type != 4) {
                Object objIfAtom4 = ifAtom(ast);
                ast = this._retTree;
                zTestAttributeTrue = this.chunk.testAttributeTrue(objIfAtom4);
            } else {
                Object objIfAtom5 = ifAtom(ast);
                ast = this._retTree;
                zTestAttributeTrue = this.chunk.testAttributeTrue(objIfAtom5);
            }
        }
        this._retTree = ast;
        return zTestAttributeTrue;
    }

    public final Object ifAtom(AST ast) throws Throwable {
        Object objExpr;
        if (ast != ASTNULL) {
        }
        try {
            objExpr = expr(ast);
            try {
                ast = this._retTree;
            } catch (RecognitionException e) {
                e = e;
                reportError(e);
                if (ast != null) {
                    ast = ast.getNextSibling();
                }
            }
        } catch (RecognitionException e2) {
            e = e2;
            objExpr = null;
        }
        this._retTree = ast;
        return objExpr;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x001a  */
    /* JADX WARN: Code duplicated, block: B:14:0x001e A[Catch: RecognitionException -> 0x0057, TryCatch #0 {RecognitionException -> 0x0057, blocks: (B:9:0x0011, B:10:0x0013, B:14:0x001e, B:15:0x0024, B:16:0x0029, B:17:0x002a, B:26:0x0049), top: B:38:0x0011 }] */
    /* JADX WARN: Code duplicated, block: B:15:0x0024 A[Catch: RecognitionException -> 0x0057, TryCatch #0 {RecognitionException -> 0x0057, blocks: (B:9:0x0011, B:10:0x0013, B:14:0x001e, B:15:0x0024, B:16:0x0029, B:17:0x002a, B:26:0x0049), top: B:38:0x0011 }] */
    /* JADX WARN: Code duplicated, block: B:17:0x002a A[Catch: RecognitionException -> 0x0057, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x0057, blocks: (B:9:0x0011, B:10:0x0013, B:14:0x001e, B:15:0x0024, B:16:0x0029, B:17:0x002a, B:26:0x0049), top: B:38:0x0011 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x005d  */
    /* JADX WARN: Code duplicated, block: B:39:0x0036 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public final Map argList(AST ast, StringTemplate stringTemplate, Map map) throws Throwable {
        int type;
        AST firstChild;
        if (ast != ASTNULL) {
        }
        if (map == null) {
            map = new HashMap();
        }
        if (ast == null) {
            try {
                ast = ASTNULL;
                type = ast.getType();
                if (type != 6) {
                    match(ast, 6);
                    firstChild = ast.getFirstChild();
                    while (true) {
                        if (firstChild == null) {
                            try {
                                firstChild = ASTNULL;
                            } catch (RecognitionException e) {
                                e = e;
                                ast = firstChild;
                                reportError(e);
                                if (ast != null) {
                                    ast = ast.getNextSibling();
                                }
                            }
                        }
                        if (firstChild.getType() == 21 && firstChild.getType() != 38) {
                            break;
                        }
                        argumentAssignment(firstChild, stringTemplate, map);
                        firstChild = this._retTree;
                    }
                    ast = ast.getNextSibling();
                } else if (type == 12) {
                    singleTemplateArg(ast, stringTemplate, map);
                    ast = this._retTree;
                } else {
                    throw new NoViableAltException(ast);
                }
            } catch (RecognitionException e2) {
                e = e2;
                reportError(e);
                if (ast != null) {
                    ast = ast.getNextSibling();
                }
                this._retTree = ast;
                return map;
            }
        } else {
            type = ast.getType();
            if (type != 6) {
                match(ast, 6);
                firstChild = ast.getFirstChild();
                while (true) {
                    if (firstChild == null) {
                        firstChild = ASTNULL;
                    }
                    if (firstChild.getType() == 21) {
                    }
                    argumentAssignment(firstChild, stringTemplate, map);
                    firstChild = this._retTree;
                }
                ast = ast.getNextSibling();
            } else if (type == 12) {
                singleTemplateArg(ast, stringTemplate, map);
                ast = this._retTree;
            } else {
                throw new NoViableAltException(ast);
            }
        }
        this._retTree = ast;
        return map;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0014  */
    /* JADX WARN: Code duplicated, block: B:12:0x0018 A[Catch: RecognitionException -> 0x005c, TryCatch #0 {RecognitionException -> 0x005c, blocks: (B:7:0x000a, B:8:0x000c, B:12:0x0018, B:13:0x0027, B:14:0x002c, B:15:0x002d, B:17:0x0049, B:19:0x004f), top: B:29:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:13:0x0027 A[Catch: RecognitionException -> 0x005c, TryCatch #0 {RecognitionException -> 0x005c, blocks: (B:7:0x000a, B:8:0x000c, B:12:0x0018, B:13:0x0027, B:14:0x002c, B:15:0x002d, B:17:0x0049, B:19:0x004f), top: B:29:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:15:0x002d A[Catch: RecognitionException -> 0x005c, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x005c, blocks: (B:7:0x000a, B:8:0x000c, B:12:0x0018, B:13:0x0027, B:14:0x002c, B:15:0x002d, B:17:0x0049, B:19:0x004f), top: B:29:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:19:0x004f A[Catch: RecognitionException -> 0x005c, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x005c, blocks: (B:7:0x000a, B:8:0x000c, B:12:0x0018, B:13:0x0027, B:14:0x002c, B:15:0x002d, B:17:0x0049, B:19:0x004f), top: B:29:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:26:0x0062  */
    public final void argumentAssignment(AST ast, StringTemplate stringTemplate, Map map) throws Throwable {
        int type;
        AST firstChild;
        StringTemplateAST stringTemplateAST;
        Object objExpr;
        if (ast != ASTNULL) {
        }
        if (ast == null) {
            try {
                ast = ASTNULL;
                type = ast.getType();
                if (type != 21) {
                    match(ast, 21);
                    firstChild = ast.getFirstChild();
                    try {
                        stringTemplateAST = (StringTemplateAST) firstChild;
                        match(firstChild, 20);
                        objExpr = expr(firstChild.getNextSibling());
                        AST ast2 = this._retTree;
                        ast = ast.getNextSibling();
                        if (objExpr != null) {
                            this.self.rawSetArgumentAttribute(stringTemplate, map, stringTemplateAST.getText(), objExpr);
                        }
                    } catch (RecognitionException e) {
                        e = e;
                        ast = firstChild;
                        reportError(e);
                        if (ast != null) {
                            ast = ast.getNextSibling();
                        }
                    }
                } else if (type == 38) {
                    match(ast, 38);
                    ast = ast.getNextSibling();
                    stringTemplate.setPassThroughAttributes(true);
                } else {
                    throw new NoViableAltException(ast);
                }
            } catch (RecognitionException e2) {
                e = e2;
                reportError(e);
                if (ast != null) {
                    ast = ast.getNextSibling();
                }
                this._retTree = ast;
            }
        } else {
            type = ast.getType();
            if (type != 21) {
                match(ast, 21);
                firstChild = ast.getFirstChild();
                stringTemplateAST = (StringTemplateAST) firstChild;
                match(firstChild, 20);
                objExpr = expr(firstChild.getNextSibling());
                AST ast3 = this._retTree;
                ast = ast.getNextSibling();
                if (objExpr != null) {
                    this.self.rawSetArgumentAttribute(stringTemplate, map, stringTemplateAST.getText(), objExpr);
                }
            } else if (type == 38) {
                match(ast, 38);
                ast = ast.getNextSibling();
                stringTemplate.setPassThroughAttributes(true);
            } else {
                throw new NoViableAltException(ast);
            }
        }
        this._retTree = ast;
    }

    public final void singleTemplateArg(AST ast, StringTemplate stringTemplate, Map map) throws Throwable {
        if (ast != ASTNULL) {
        }
        try {
            match(ast, 12);
            AST firstChild = ast.getFirstChild();
            try {
                Object objExpr = expr(firstChild);
                AST ast2 = this._retTree;
                ast = ast.getNextSibling();
                if (objExpr != null) {
                    Map formalArguments = stringTemplate.getFormalArguments();
                    boolean z = true;
                    String str = null;
                    if (formalArguments != null) {
                        Set setKeySet = formalArguments.keySet();
                        if (setKeySet.size() == 1) {
                            z = false;
                            str = (String) setKeySet.toArray()[0];
                        }
                    }
                    if (z) {
                        this.self.error(new StringBuffer("template ").append(stringTemplate.getName()).append(" must have exactly one formal arg in template context ").append(this.self.getEnclosingInstanceStackString()).toString());
                    } else {
                        this.self.rawSetArgumentAttribute(stringTemplate, map, str, objExpr);
                    }
                }
            } catch (RecognitionException e) {
                e = e;
                ast = firstChild;
                reportError(e);
                if (ast != null) {
                    ast = ast.getNextSibling();
                }
            }
        } catch (RecognitionException e2) {
            e = e2;
        }
        this._retTree = ast;
    }

    private static final long[] mk_tokenSet_0() {
        return new long[]{60180933296L, 0};
    }
}
