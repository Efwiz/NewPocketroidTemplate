package org.jetbrains.kotlin.cli.js.internal;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.gwt.dev.js.ThrowExceptionOnErrorReporter;
import org.jetbrains.kotlin.js.backend.JsToStringGenerationVisitor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsObjectScope;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.inline.util.FixForwardNameReferencesKt;
import org.jetbrains.kotlin.js.parser.ParserUtilsKt;
import org.jetbrains.kotlin.js.parser.sourcemaps.JSONKt;
import org.jetbrains.kotlin.js.parser.sourcemaps.JsonArray;
import org.jetbrains.kotlin.js.parser.sourcemaps.JsonNode;
import org.jetbrains.kotlin.js.parser.sourcemaps.JsonNull;
import org.jetbrains.kotlin.js.parser.sourcemaps.JsonObject;
import org.jetbrains.kotlin.js.parser.sourcemaps.JsonString;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapError;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapLocationRemapper;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapParseResult;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapParser;
import org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapSuccess;
import org.jetbrains.kotlin.js.sourceMap.SourceFilePathResolver;
import org.jetbrains.kotlin.js.sourceMap.SourceMap3Builder;
import org.jetbrains.kotlin.js.sourceMap.SourceMapBuilderConsumer;
import org.jetbrains.kotlin.js.util.TextOutputImpl;

/* JADX INFO: compiled from: JSStdlibLinker.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0002\u001a\u0019\u0010\u0006\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\n\u001a.\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010H\u0002\u001a\u0012\u0010\u0011\u001a\u00020\u0012*\b\u0012\u0004\u0012\u00020\u00130\u0010H\u0002¨\u0006\u0014"}, d2 = {"collectFiles", "", "rootFile", "Ljava/io/File;", "target", "", "main", "args", "", "", "([Ljava/lang/String;)V", "mergeStdlibParts", "outputFile", "wrapperFile", "baseDir", "inputPaths", "", "createInsertionPlace", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "cli-js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JSStdlibLinker {
    public static final void main(String[] args) throws IOException {
        Intrinsics.checkNotNullParameter(args, "args");
        File file = new File(args[0]);
        File canonicalFile = new File(args[1]).getCanonicalFile();
        File file2 = new File(args[2]);
        List listDrop = ArraysKt.drop(args, 3);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDrop, 10));
        Iterator it = listDrop.iterator();
        while (it.hasNext()) {
            arrayList.add(new File((String) it.next()));
        }
        Intrinsics.checkNotNull(canonicalFile);
        mergeStdlibParts(file, file2, canonicalFile, arrayList);
    }

    private static final void mergeStdlibParts(File file, File file2, File file3, List<? extends File> list) throws IOException {
        JsonNode jsonString;
        JsProgram jsProgram = new JsProgram();
        Charset charset = null;
        int i = 1;
        String text$default = FilesKt.readText$default(file2, null, 1, null);
        ThrowExceptionOnErrorReporter throwExceptionOnErrorReporter = ThrowExceptionOnErrorReporter.INSTANCE;
        JsObjectScope scope = jsProgram.getScope();
        Intrinsics.checkNotNullExpressionValue(scope, "getScope(...)");
        List<JsStatement> list2 = ParserUtilsKt.parse(text$default, throwExceptionOnErrorReporter, scope, mergeStdlibParts$makeRelativeIfNecessary(file2, file3));
        if (list2 == null) {
            throw new IllegalStateException("Should not be null because of error reporter".toString());
        }
        JsBlock jsBlockCreateInsertionPlace = createInsertionPlace(list2);
        ArrayList<File> arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            collectFiles((File) it.next(), arrayList);
        }
        for (File file4 : arrayList) {
            String text$default2 = FilesKt.readText$default(file4, charset, i, charset);
            ThrowExceptionOnErrorReporter throwExceptionOnErrorReporter2 = ThrowExceptionOnErrorReporter.INSTANCE;
            JsObjectScope scope2 = jsProgram.getScope();
            Intrinsics.checkNotNullExpressionValue(scope2, "getScope(...)");
            List<JsStatement> list3 = ParserUtilsKt.parse(text$default2, throwExceptionOnErrorReporter2, scope2, mergeStdlibParts$makeRelativeIfNecessary(file4, file3));
            if (list3 == null) {
                throw new IllegalStateException("Should not be null because of error reporter".toString());
            }
            JsBlock jsBlock = new JsBlock(list3);
            FixForwardNameReferencesKt.fixForwardNameReferences(jsBlock);
            File file5 = new File(file4.getParent(), file4.getName() + ".map");
            if (file5.exists()) {
                SourceMapParseResult sourceMapParseResult = SourceMapParser.INSTANCE.parse(file5);
                if (sourceMapParseResult instanceof SourceMapError) {
                    System.err.println("Error parsing source map file " + file5 + ": " + ((SourceMapError) sourceMapParseResult).getMessage());
                    System.exit(1);
                    throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
                }
                if (sourceMapParseResult instanceof SourceMapSuccess) {
                    new SourceMapLocationRemapper(((SourceMapSuccess) sourceMapParseResult).getValue(), null, 2, null).remap(jsBlock);
                }
            }
            List<JsStatement> statements = jsBlockCreateInsertionPlace.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            CollectionsKt.addAll(statements, list3);
            charset = null;
            i = 1;
        }
        List<JsStatement> statements2 = jsProgram.getGlobalBlock().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        CollectionsKt.addAll(statements2, list2);
        File file6 = new File(file.getParentFile(), file.getName() + ".map");
        TextOutputImpl textOutputImpl = new TextOutputImpl();
        SourceMap3Builder sourceMap3Builder = new SourceMap3Builder(file, new JSStdlibLinker$mergeStdlibParts$sourceMapBuilder$1(textOutputImpl), "");
        jsProgram.getGlobalBlock().accept(new JsToStringGenerationVisitor(textOutputImpl, new SourceMapBuilderConsumer(new File("."), sourceMap3Builder, new SourceFilePathResolver(new ArrayList(), null, 2, null), true, true)));
        String strBuild = sourceMap3Builder.build();
        String string = textOutputImpl.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        FilesKt.writeText$default(file, string + "\n//# sourceMappingURL=" + file6.getName() + '\n', null, 2, null);
        JsonNode json = JSONKt.parseJson(strBuild);
        Intrinsics.checkNotNull(json, "null cannot be cast to non-null type org.jetbrains.kotlin.js.parser.sourcemaps.JsonObject");
        JsonObject jsonObject = (JsonObject) json;
        JsonNode jsonNode = jsonObject.getProperties().get("sources");
        Intrinsics.checkNotNull(jsonNode, "null cannot be cast to non-null type org.jetbrains.kotlin.js.parser.sourcemaps.JsonArray");
        Map<String, JsonNode> properties = jsonObject.getProperties();
        List<JsonNode> elements = ((JsonArray) jsonNode).getElements();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements, 10));
        for (JsonNode jsonNode2 : elements) {
            Intrinsics.checkNotNull(jsonNode2, "null cannot be cast to non-null type org.jetbrains.kotlin.js.parser.sourcemaps.JsonString");
            File file7 = new File(((JsonString) jsonNode2).getValue());
            if (file7.exists()) {
                jsonString = new JsonString(FilesKt.readText$default(file7, null, 1, null));
            } else {
                jsonString = JsonNull.INSTANCE;
            }
            arrayList2.add(jsonString);
        }
        JsonNode[] jsonNodeArr = (JsonNode[]) arrayList2.toArray(new JsonNode[0]);
        properties.put("sourcesContent", new JsonArray((JsonNode[]) Arrays.copyOf(jsonNodeArr, jsonNodeArr.length)));
        FilesKt.writeText$default(file6, json.toString(), null, 2, null);
    }

    private static final String mergeStdlibParts$makeRelativeIfNecessary(File file, File file2) throws IOException {
        File canonicalFile = file.getCanonicalFile();
        Intrinsics.checkNotNullExpressionValue(canonicalFile, "getCanonicalFile(...)");
        return FilesKt.toRelativeString(canonicalFile, file2);
    }

    private static final JsBlock createInsertionPlace(List<? extends JsStatement> list) {
        final JsCompositeBlock jsCompositeBlock = new JsCompositeBlock();
        JsVisitorWithContextImpl jsVisitorWithContextImpl = new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.cli.js.internal.JSStdlibLinker$createInsertionPlace$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsExpressionStatement x, JsContext<? super JsStatement> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                if (isInsertionPlace(expression)) {
                    ctx.replaceMe(jsCompositeBlock);
                    return false;
                }
                return super.visit(x, ctx);
            }

            private final boolean isInsertionPlace(JsExpression expression) {
                if (expression instanceof JsInvocation) {
                    JsInvocation jsInvocation = (JsInvocation) expression;
                    List<JsExpression> arguments = jsInvocation.getArguments();
                    Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                    if (arguments.isEmpty()) {
                        JsExpression qualifier = jsInvocation.getQualifier();
                        Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
                        if (qualifier instanceof JsNameRef) {
                            JsNameRef jsNameRef = (JsNameRef) qualifier;
                            if (jsNameRef.getQualifier() == null) {
                                return Intrinsics.areEqual(jsNameRef.getIdent(), "insertContent");
                            }
                        }
                    }
                }
                return false;
            }
        };
        Iterator<? extends JsStatement> it = list.iterator();
        while (it.hasNext()) {
            jsVisitorWithContextImpl.accept(it.next());
        }
        return jsCompositeBlock;
    }

    private static final void collectFiles(File file, List<File> list) {
        if (!file.isDirectory()) {
            if (Intrinsics.areEqual(FilesKt.getExtension(file), "js")) {
                list.add(file);
                return;
            }
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            throw new IllegalStateException(("Problem with listing files in " + file).toString());
        }
        for (File file2 : ArraysKt.sorted(fileArrListFiles)) {
            Intrinsics.checkNotNull(file2);
            collectFiles(file2, list);
        }
    }
}
