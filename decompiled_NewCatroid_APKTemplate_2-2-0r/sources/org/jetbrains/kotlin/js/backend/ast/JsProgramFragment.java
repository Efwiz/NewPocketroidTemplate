package org.jetbrains.kotlin.js.backend.ast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsProgramFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00190\b¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\fR\u0011\u0010\u001b\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0010R\u001d\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00190\b¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\fR\u001d\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\b¢\u0006\b\n\u0000\u001a\u0004\b \u0010\fR\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u0017\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\u0014¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u001c\u0010.\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010$\"\u0004\b0\u0010&¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "", "scope", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "packageFqn", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsScope;Ljava/lang/String;)V", "classes", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsClassModel;", "getClasses", "()Ljava/util/Map;", "declarationBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "getDeclarationBlock", "()Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "exportBlock", "getExportBlock", "importedModules", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "getImportedModules", "()Ljava/util/List;", "imports", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getImports", "initializerBlock", "getInitializerBlock", "inlineModuleMap", "getInlineModuleMap", "inlinedLocalDeclarations", "getInlinedLocalDeclarations", "mainFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "getMainFunction", "()Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "setMainFunction", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;)V", "nameBindings", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameBinding;", "getNameBindings", "getPackageFqn", "()Ljava/lang/String;", "getScope", "()Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "tests", "getTests", "setTests", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsProgramFragment {
    private final Map<JsName, JsClassModel> classes;
    private final JsCompositeBlock declarationBlock;
    private final JsCompositeBlock exportBlock;
    private final List<JsImportedModule> importedModules;
    private final Map<String, JsExpression> imports;
    private final JsCompositeBlock initializerBlock;
    private final Map<String, JsExpression> inlineModuleMap;
    private final Map<String, JsCompositeBlock> inlinedLocalDeclarations;
    private JsStatement mainFunction;
    private final List<JsNameBinding> nameBindings;
    private final String packageFqn;
    private final JsScope scope;
    private JsStatement tests;

    public JsProgramFragment(JsScope scope, String packageFqn) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(packageFqn, "packageFqn");
        this.scope = scope;
        this.packageFqn = packageFqn;
        this.importedModules = new ArrayList();
        this.imports = new LinkedHashMap();
        this.declarationBlock = new JsCompositeBlock();
        this.exportBlock = new JsCompositeBlock();
        this.initializerBlock = new JsCompositeBlock();
        this.nameBindings = new ArrayList();
        this.classes = new LinkedHashMap();
        this.inlineModuleMap = new LinkedHashMap();
        this.inlinedLocalDeclarations = new LinkedHashMap();
    }

    public final String getPackageFqn() {
        return this.packageFqn;
    }

    public final JsScope getScope() {
        return this.scope;
    }

    public final List<JsImportedModule> getImportedModules() {
        return this.importedModules;
    }

    public final Map<String, JsExpression> getImports() {
        return this.imports;
    }

    public final JsCompositeBlock getDeclarationBlock() {
        return this.declarationBlock;
    }

    public final JsCompositeBlock getExportBlock() {
        return this.exportBlock;
    }

    public final JsCompositeBlock getInitializerBlock() {
        return this.initializerBlock;
    }

    public final List<JsNameBinding> getNameBindings() {
        return this.nameBindings;
    }

    public final Map<JsName, JsClassModel> getClasses() {
        return this.classes;
    }

    public final Map<String, JsExpression> getInlineModuleMap() {
        return this.inlineModuleMap;
    }

    public final JsStatement getTests() {
        return this.tests;
    }

    public final void setTests(JsStatement jsStatement) {
        this.tests = jsStatement;
    }

    public final JsStatement getMainFunction() {
        return this.mainFunction;
    }

    public final void setMainFunction(JsStatement jsStatement) {
        this.mainFunction = jsStatement;
    }

    public final Map<String, JsCompositeBlock> getInlinedLocalDeclarations() {
        return this.inlinedLocalDeclarations;
    }
}
