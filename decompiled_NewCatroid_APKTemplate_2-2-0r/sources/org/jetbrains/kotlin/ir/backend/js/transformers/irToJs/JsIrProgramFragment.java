package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;

/* JADX INFO: compiled from: JsIrProgramFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R$\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u001a\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001b\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000fR\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001d\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020#0\u0007¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u000bR\u0011\u0010%\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u000fR\u001c\u0010'\u001a\u0004\u0018\u00010#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0017R\u001d\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u000bR!\u0010/\u001a\u0012\u0012\u0004\u0012\u00020\u000300j\b\u0012\u0004\u0012\u00020\u0003`1¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\u0017R\u0011\u00105\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u000fR\u001c\u00107\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u001c\u0010<\u001a\u0004\u0018\u00010#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010)\"\u0004\b>\u0010+\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "", "name", "", "packageFqn", "(Ljava/lang/String;Ljava/lang/String;)V", "classes", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrIcClassModel;", "getClasses", "()Ljava/util/Map;", "declarations", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "getDeclarations", "()Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "definitions", "", "getDefinitions", "()Ljava/util/Set;", "dts", "Lorg/jetbrains/kotlin/ir/backend/js/export/TypeScriptFragment;", "getDts-J7-OZfo", "()Ljava/lang/String;", "setDts-Y7E3pzw", "(Ljava/lang/String;)V", "Ljava/lang/String;", PsiKeyword.EXPORTS, "getExports", "importedModules", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "getImportedModules", "()Ljava/util/List;", "imports", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "getImports", "initializers", "getInitializers", "mainFunction", "getMainFunction", "()Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "setMainFunction", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;)V", "getName", "nameBindings", "getNameBindings", "optionalCrossModuleImports", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "getOptionalCrossModuleImports", "()Ljava/util/HashSet;", "getPackageFqn", "polyfills", "getPolyfills", "suiteFn", "getSuiteFn", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "setSuiteFn", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)V", "testFunInvocation", "getTestFunInvocation", "setTestFunInvocation", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrProgramFragment {
    private final Map<JsName, JsIrIcClassModel> classes;
    private final JsCompositeBlock declarations;
    private final Set<String> definitions;
    private String dts;
    private final JsCompositeBlock exports;
    private final List<JsImportedModule> importedModules;
    private final Map<String, JsStatement> imports;
    private final JsCompositeBlock initializers;
    private JsStatement mainFunction;
    private final String name;
    private final Map<String, JsName> nameBindings;
    private final HashSet<String> optionalCrossModuleImports;
    private final String packageFqn;
    private final JsCompositeBlock polyfills;
    private JsName suiteFn;
    private JsStatement testFunInvocation;

    public JsIrProgramFragment(String name2, String packageFqn) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageFqn, "packageFqn");
        this.name = name2;
        this.packageFqn = packageFqn;
        this.nameBindings = new LinkedHashMap();
        this.optionalCrossModuleImports = new HashSet<>();
        this.declarations = new JsCompositeBlock();
        this.exports = new JsCompositeBlock();
        this.importedModules = new ArrayList();
        this.imports = new LinkedHashMap();
        this.classes = new LinkedHashMap();
        this.initializers = new JsCompositeBlock();
        this.definitions = new LinkedHashSet();
        this.polyfills = new JsCompositeBlock();
    }

    public final String getName() {
        return this.name;
    }

    public final String getPackageFqn() {
        return this.packageFqn;
    }

    public final Map<String, JsName> getNameBindings() {
        return this.nameBindings;
    }

    public final HashSet<String> getOptionalCrossModuleImports() {
        return this.optionalCrossModuleImports;
    }

    public final JsCompositeBlock getDeclarations() {
        return this.declarations;
    }

    public final JsCompositeBlock getExports() {
        return this.exports;
    }

    public final List<JsImportedModule> getImportedModules() {
        return this.importedModules;
    }

    public final Map<String, JsStatement> getImports() {
        return this.imports;
    }

    /* JADX INFO: renamed from: getDts-J7-OZfo, reason: not valid java name and from getter */
    public final String getDts() {
        return this.dts;
    }

    /* JADX INFO: renamed from: setDts-Y7E3pzw, reason: not valid java name */
    public final void m7200setDtsY7E3pzw(String str) {
        this.dts = str;
    }

    public final Map<JsName, JsIrIcClassModel> getClasses() {
        return this.classes;
    }

    public final JsCompositeBlock getInitializers() {
        return this.initializers;
    }

    public final JsStatement getMainFunction() {
        return this.mainFunction;
    }

    public final void setMainFunction(JsStatement jsStatement) {
        this.mainFunction = jsStatement;
    }

    public final JsStatement getTestFunInvocation() {
        return this.testFunInvocation;
    }

    public final void setTestFunInvocation(JsStatement jsStatement) {
        this.testFunInvocation = jsStatement;
    }

    public final JsName getSuiteFn() {
        return this.suiteFn;
    }

    public final void setSuiteFn(JsName jsName) {
        this.suiteFn = jsName;
    }

    public final Set<String> getDefinitions() {
        return this.definitions;
    }

    public final JsCompositeBlock getPolyfills() {
        return this.polyfills;
    }
}
