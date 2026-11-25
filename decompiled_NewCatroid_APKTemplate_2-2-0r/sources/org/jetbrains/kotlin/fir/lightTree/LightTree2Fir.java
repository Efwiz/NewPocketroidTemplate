package org.jetbrains.kotlin.fir.lightTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtIoFileSourceFile;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtSourceFileLinesMapping;
import org.jetbrains.kotlin.KtSourceFileLinesMappingKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.lightTree.converter.LightTreeRawFirDeclarationBuilder;
import org.jetbrains.kotlin.fir.scopes.FirScopeProvider;
import org.jetbrains.kotlin.lexer.KotlinLexer;
import org.jetbrains.kotlin.parsing.KotlinLightParser;
import org.jetbrains.kotlin.parsing.KotlinParserDefinition;

/* JADX INFO: compiled from: LightTree2Fir.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ$\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0017J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/LightTree2Fir;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "diagnosticsReporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;)V", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "buildFirFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "lightTree", "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "sourceFile", "Lorg/jetbrains/kotlin/KtSourceFile;", "linesMapping", "Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", "file", "Ljava/io/File;", "path", "Ljava/nio/file/Path;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "makeErrorListener", "Lorg/jetbrains/kotlin/fir/lightTree/LightTreeParsingErrorListener;", "Companion", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LightTree2Fir {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final KotlinParserDefinition parserDefinition = new KotlinParserDefinition();
    private final DiagnosticReporter diagnosticsReporter;
    private final FirScopeProvider scopeProvider;
    private final FirSession session;

    public LightTree2Fir(FirSession session, FirScopeProvider scopeProvider, DiagnosticReporter diagnosticReporter) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeProvider, "scopeProvider");
        this.session = session;
        this.scopeProvider = scopeProvider;
        this.diagnosticsReporter = diagnosticReporter;
    }

    public /* synthetic */ LightTree2Fir(FirSession firSession, FirScopeProvider firScopeProvider, DiagnosticReporter diagnosticReporter, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, firScopeProvider, (i & 4) != 0 ? null : diagnosticReporter);
    }

    public final FirSession getSession() {
        return this.session;
    }

    /* JADX INFO: compiled from: LightTree2Fir.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\b\u0010\f\u001a\u00020\rH\u0002J>\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00072\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0016\b\u0002\u0010\u0012\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00140\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/LightTree2Fir$Companion;", "", "()V", "parserDefinition", "Lorg/jetbrains/kotlin/parsing/KotlinParserDefinition;", "buildLightTree", "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "errorListener", "Lorg/jetbrains/kotlin/fir/lightTree/LightTreeParsingErrorListener;", "makeLexer", "Lorg/jetbrains/kotlin/lexer/KotlinLexer;", "reportErrors", "", "node", Constants.TYPE_TREE, "ref", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Ref;", "", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final KotlinLexer makeLexer() {
            return new KotlinLexer();
        }

        public final FlyweightCapableTreeStructure<LighterASTNode> buildLightTree(CharSequence code, LightTreeParsingErrorListener errorListener) {
            Intrinsics.checkNotNullParameter(code, "code");
            PsiBuilder psiBuilderCreateBuilder = PsiBuilderFactory.getInstance().createBuilder(LightTree2Fir.parserDefinition, makeLexer(), code);
            Intrinsics.checkNotNullExpressionValue(psiBuilderCreateBuilder, "createBuilder(...)");
            FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure = KotlinLightParser.parse(psiBuilderCreateBuilder);
            if (errorListener != null) {
                Companion companion = LightTree2Fir.INSTANCE;
                LighterASTNode root = flyweightCapableTreeStructure.getRoot();
                Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
                Intrinsics.checkNotNull(flyweightCapableTreeStructure);
                reportErrors$default(companion, root, flyweightCapableTreeStructure, errorListener, null, 8, null);
            }
            Intrinsics.checkNotNullExpressionValue(flyweightCapableTreeStructure, "also(...)");
            return flyweightCapableTreeStructure;
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ void reportErrors$default(Companion companion, LighterASTNode lighterASTNode, FlyweightCapableTreeStructure flyweightCapableTreeStructure, LightTreeParsingErrorListener lightTreeParsingErrorListener, Ref ref, int i, Object obj) {
            if ((i & 8) != 0) {
                ref = new Ref();
            }
            companion.reportErrors(lighterASTNode, flyweightCapableTreeStructure, lightTreeParsingErrorListener, ref);
        }

        private final void reportErrors(LighterASTNode node, FlyweightCapableTreeStructure<LighterASTNode> tree, LightTreeParsingErrorListener errorListener, Ref<LighterASTNode[]> ref) {
            tree.getChildren(node, ref);
            LighterASTNode[] lighterASTNodeArr = ref.get();
            if (lighterASTNodeArr == null) {
                return;
            }
            for (LighterASTNode lighterASTNode : lighterASTNodeArr) {
                if (lighterASTNode == null) {
                    return;
                }
                if (Intrinsics.areEqual(lighterASTNode.getTokenType(), TokenType.ERROR_ELEMENT)) {
                    errorListener.onError(lighterASTNode.getStartOffset(), lighterASTNode.getEndOffset(), PsiBuilderImpl.getErrorMessage(lighterASTNode));
                }
                ref.set(null);
                reportErrors(lighterASTNode, tree, errorListener, ref);
            }
        }
    }

    public final FirFile buildFirFile(Path path) {
        Intrinsics.checkNotNullParameter(path, "path");
        File file = path.toFile();
        Intrinsics.checkNotNullExpressionValue(file, "toFile(...)");
        return buildFirFile(file);
    }

    public final FirFile buildFirFile(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        KtIoFileSourceFile ktIoFileSourceFile = new KtIoFileSourceFile(file);
        Pair<CharSequence, KtSourceFileLinesMapping> sourceFileWithMapping = KtSourceFileLinesMappingKt.readSourceFileWithMapping(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8));
        return buildFirFile(sourceFileWithMapping.component1(), ktIoFileSourceFile, sourceFileWithMapping.component2());
    }

    public final FirFile buildFirFile(FlyweightCapableTreeStructure<LighterASTNode> lightTree, KtSourceFile sourceFile, KtSourceFileLinesMapping linesMapping) {
        Intrinsics.checkNotNullParameter(lightTree, "lightTree");
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(linesMapping, "linesMapping");
        LightTreeRawFirDeclarationBuilder lightTreeRawFirDeclarationBuilder = new LightTreeRawFirDeclarationBuilder(this.session, this.scopeProvider, lightTree, null, 8, null);
        LighterASTNode root = lightTree.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return lightTreeRawFirDeclarationBuilder.convertFile(root, sourceFile, linesMapping);
    }

    public final FirFile buildFirFile(CharSequence code, KtSourceFile sourceFile, KtSourceFileLinesMapping linesMapping) {
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(linesMapping, "linesMapping");
        return buildFirFile(INSTANCE.buildLightTree(code, makeErrorListener(sourceFile)), sourceFile, linesMapping);
    }

    private final LightTreeParsingErrorListener makeErrorListener(KtSourceFile sourceFile) {
        DiagnosticReporter diagnosticReporter = this.diagnosticsReporter;
        if (diagnosticReporter == null) {
            return null;
        }
        return LightTreeParsingErrorListenerKt.toKotlinParsingErrorListener(diagnosticReporter, sourceFile, FirLanguageSettingsComponentKt.getLanguageVersionSettings(this.session));
    }
}
