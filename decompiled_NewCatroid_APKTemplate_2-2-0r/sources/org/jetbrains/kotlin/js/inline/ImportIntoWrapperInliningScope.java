package org.jetbrains.kotlin.js.inline;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;

/* JADX INFO: compiled from: InliningScope.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0011H\u0014J\u001a\u0010\u0017\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0018\u001a\u00020\tH\u0014J\u001a\u0010\u0019\u001a\u0004\u0018\u00010\r2\u0006\u0010\u001a\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\fH\u0014R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/ImportIntoWrapperInliningScope;", "Lorg/jetbrains/kotlin/js/inline/InliningScope;", "wrapperBody", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "fragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;)V", "additionalStatements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "existingImports", "", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getFragment", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "importList", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "otherLocalStatements", "addImport", "", "tag", "vars", "addInlinedDeclaration", "declaration", "hasImport", "name", "Companion", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImportIntoWrapperInliningScope extends InliningScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<JsStatement> additionalStatements;
    private final Map<String, JsName> existingImports;
    private final JsProgramFragment fragment;
    private final List<JsVars> importList;
    private final List<JsStatement> otherLocalStatements;
    private final JsBlock wrapperBody;

    public /* synthetic */ ImportIntoWrapperInliningScope(JsBlock jsBlock, JsProgramFragment jsProgramFragment, DefaultConstructorMarker defaultConstructorMarker) {
        this(jsBlock, jsProgramFragment);
    }

    @Override // org.jetbrains.kotlin.js.inline.InliningScope
    public JsProgramFragment getFragment() {
        return this.fragment;
    }

    private ImportIntoWrapperInliningScope(JsBlock jsBlock, JsProgramFragment jsProgramFragment) {
        JsVars jsVars;
        String importTag;
        super(null);
        this.wrapperBody = jsBlock;
        this.fragment = jsProgramFragment;
        this.importList = new ArrayList();
        this.otherLocalStatements = new ArrayList();
        this.existingImports = new LinkedHashMap();
        for (JsStatement jsStatement : jsBlock.getStatements()) {
            if ((jsStatement instanceof JsVars) && (importTag = CollectUtilsKt.getImportTag((jsVars = (JsVars) jsStatement))) != null) {
                this.importList.add((JsVars) jsStatement);
                Map<String, JsName> map = this.existingImports;
                JsName name2 = jsVars.getVars().get(0).getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                map.put(importTag, name2);
            } else {
                List<JsStatement> list = this.otherLocalStatements;
                Intrinsics.checkNotNull(jsStatement);
                list.add(jsStatement);
            }
        }
        this.additionalStatements = new ArrayList();
    }

    @Override // org.jetbrains.kotlin.js.inline.InliningScope
    protected void addInlinedDeclaration(String tag, JsStatement declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        this.additionalStatements.add(declaration);
    }

    @Override // org.jetbrains.kotlin.js.inline.InliningScope
    protected JsName hasImport(JsName name2, String tag) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(tag, "tag");
        return this.existingImports.get(tag);
    }

    @Override // org.jetbrains.kotlin.js.inline.InliningScope
    protected void addImport(String tag, JsVars vars) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(vars, "vars");
        Map<String, JsName> map = this.existingImports;
        JsName name2 = vars.getVars().get(0).getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        map.put(tag, name2);
        this.importList.add(vars);
    }

    /* JADX INFO: compiled from: InliningScope.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00040\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/ImportIntoWrapperInliningScope$Companion;", "", "()V", "process", "", "wrapperBody", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "fragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "fn", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/js/inline/ImportIntoWrapperInliningScope;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void process(JsBlock wrapperBody, JsProgramFragment fragment, Function1<? super ImportIntoWrapperInliningScope, Unit> fn) {
            Intrinsics.checkNotNullParameter(wrapperBody, "wrapperBody");
            Intrinsics.checkNotNullParameter(fragment, "fragment");
            Intrinsics.checkNotNullParameter(fn, "fn");
            ImportIntoWrapperInliningScope importIntoWrapperInliningScope = new ImportIntoWrapperInliningScope(wrapperBody, fragment, null);
            fn.invoke(importIntoWrapperInliningScope);
            List<JsStatement> statements = wrapperBody.getStatements();
            statements.clear();
            statements.addAll(importIntoWrapperInliningScope.importList);
            statements.addAll(importIntoWrapperInliningScope.additionalStatements);
            statements.addAll(importIntoWrapperInliningScope.otherLocalStatements);
        }
    }
}
