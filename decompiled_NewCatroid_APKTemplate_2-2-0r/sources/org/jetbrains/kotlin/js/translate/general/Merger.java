package org.jetbrains.kotlin.js.translate.general;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.js.backend.ast.HasName;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsClassModel;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModuleKey;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameBinding;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: Merger.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 P2\u00020\u0001:\u0001PB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u00106\u001a\u0002072\f\u00108\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J,\u00106\u001a\u0002072\u0006\u00109\u001a\u00020\u000e2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\f\u00108\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0016\u0010;\u001a\u0002072\f\u00108\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J,\u0010;\u001a\u0002072\u0006\u00109\u001a\u00020\u000e2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\f\u00108\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0010\u0010<\u001a\u0002072\u0006\u0010=\u001a\u00020\u0018H\u0002J\u000e\u0010>\u001a\u0002072\u0006\u0010=\u001a\u00020\u0018J\u001c\u0010?\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0@2\u0006\u0010=\u001a\u00020\u0018H\u0002J\u0006\u0010A\u001a\u000200J\u0006\u0010B\u001a\u000207J\b\u0010C\u001a\u000207H\u0002J\u0012\u0010D\u001a\u000207*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u000e\u0010E\u001a\u0004\u0018\u00010F*\u00020\u000bH\u0002J\u000e\u0010G\u001a\u00020H*\u0004\u0018\u00010\u000bH\u0002J/\u0010I\u001a\u0002HJ\"\b\b\u0000\u0010J*\u00020K*\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0@2\u0006\u0010L\u001a\u0002HJH\u0002¢\u0006\u0002\u0010MJ \u0010I\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0@2\u0006\u00109\u001a\u00020\u000eH\u0002J \u0010I\u001a\u000207*\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0@2\u0006\u0010=\u001a\u00020\u0018H\u0002J\f\u0010N\u001a\u000207*\u00020\u0018H\u0002J\f\u0010O\u001a\u000207*\u00020\u0018H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001e0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010#\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u001c\u0010&\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u001a\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010/\u001a\u000200¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u000e\u00103\u001a\u000204X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00105\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Q"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/general/Merger;", "", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "moduleId", "", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Ljava/lang/String;Lorg/jetbrains/kotlin/serialization/js/ModuleKind;)V", "additionalFakeOverrides", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "classes", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsClassModel;", "declarationBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "declaredImports", "", "exportBlock", "exportedPackages", "exportedTags", "fragments", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "importBlock", "importedModuleTable", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModuleKey;", "importedModules", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "getImportedModules", "()Ljava/util/List;", "importedModulesImpl", "initializerBlock", "internalModuleName", "getInternalModuleName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "mainFn", "Lkotlin/Pair;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModuleId", "()Ljava/lang/String;", "getModuleKind", "()Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "nameTable", ConfigConstants.CONFIG_KEY_PROGRAM, "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "getProgram", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "rootFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "testsMap", "addClassPostDeclarations", "", "statements", "name", "visited", "addClassPrototypes", "addExportStatements", "fragment", "addFragment", "buildNameMap", "", "buildProgram", "merge", "mergeNames", "addImportForInlineDeclarationIfNecessary", "decomposeTestInvocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "isFakeOverrideAssignment", "", "rename", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "rootNode", "(Ljava/util/Map;Lorg/jetbrains/kotlin/js/backend/ast/JsNode;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "tryUpdateMain", "tryUpdateTests", "Companion", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Merger {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String ENUM_SIGNATURE = "kotlin$Enum";
    private final List<JsStatement> additionalFakeOverrides;
    private final Map<JsName, JsClassModel> classes;
    private final JsCompositeBlock declarationBlock;
    private final Set<String> declaredImports;
    private final JsCompositeBlock exportBlock;
    private final Map<String, JsName> exportedPackages;
    private final Set<String> exportedTags;
    private final List<JsProgramFragment> fragments;
    private final JsCompositeBlock importBlock;
    private final Map<JsImportedModuleKey, JsName> importedModuleTable;
    private final List<JsImportedModule> importedModulesImpl;
    private final JsCompositeBlock initializerBlock;
    private final JsName internalModuleName;
    private Pair<String, ? extends JsStatement> mainFn;
    private final ModuleDescriptor module;
    private final String moduleId;
    private final ModuleKind moduleKind;
    private final Map<String, JsName> nameTable;
    private final JsProgram program;
    private final JsFunction rootFunction;
    private final Map<String, JsStatement> testsMap;

    public Merger(ModuleDescriptor module, String moduleId, ModuleKind moduleKind) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(moduleId, "moduleId");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        this.module = module;
        this.moduleId = moduleId;
        this.moduleKind = moduleKind;
        JsProgram jsProgram = new JsProgram();
        this.program = jsProgram;
        JsName jsNameDeclareName = jsProgram.getScope().declareName("_");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
        this.internalModuleName = jsNameDeclareName;
        JsFunction jsFunction = new JsFunction(jsProgram.getRootScope(), new JsBlock(), "root function");
        jsFunction.getParameters().add(new JsParameter(jsNameDeclareName));
        this.rootFunction = jsFunction;
        this.nameTable = new LinkedHashMap();
        this.importedModuleTable = new LinkedHashMap();
        this.importBlock = new JsCompositeBlock();
        this.declarationBlock = new JsCompositeBlock();
        this.initializerBlock = new JsCompositeBlock();
        this.testsMap = new LinkedHashMap();
        this.exportBlock = new JsCompositeBlock();
        this.declaredImports = new LinkedHashSet();
        this.classes = new LinkedHashMap();
        this.importedModulesImpl = new ArrayList();
        this.exportedPackages = new LinkedHashMap();
        this.exportedTags = new LinkedHashSet();
        this.fragments = new ArrayList();
        this.additionalFakeOverrides = new ArrayList();
    }

    public final ModuleDescriptor getModule() {
        return this.module;
    }

    public final String getModuleId() {
        return this.moduleId;
    }

    public final ModuleKind getModuleKind() {
        return this.moduleKind;
    }

    public final JsProgram getProgram() {
        return this.program;
    }

    public final JsName getInternalModuleName() {
        return this.internalModuleName;
    }

    public final void addFragment(JsProgramFragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        this.fragments.add(fragment);
    }

    private final void tryUpdateTests(JsProgramFragment jsProgramFragment) {
        JsBlock jsBlockDecomposeTestInvocation;
        final JsStatement tests = jsProgramFragment.getTests();
        if (tests != null) {
            Map<String, JsStatement> map = this.testsMap;
            String packageFqn = jsProgramFragment.getPackageFqn();
            final Function1<String, JsStatement> function1 = new Function1<String, JsStatement>() { // from class: org.jetbrains.kotlin.js.translate.general.Merger$tryUpdateTests$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final JsStatement invoke(String it) {
                    List<JsStatement> statements;
                    Intrinsics.checkNotNullParameter(it, "it");
                    JsStatement jsStatementDeepCopy = tests.deepCopy();
                    Merger merger = this;
                    Intrinsics.checkNotNull(jsStatementDeepCopy);
                    JsBlock jsBlockDecomposeTestInvocation2 = merger.decomposeTestInvocation(jsStatementDeepCopy);
                    if (jsBlockDecomposeTestInvocation2 != null && (statements = jsBlockDecomposeTestInvocation2.getStatements()) != null) {
                        statements.clear();
                    }
                    return jsStatementDeepCopy;
                }
            };
            JsStatement jsStatementComputeIfAbsent = map.computeIfAbsent(packageFqn, new Function() { // from class: org.jetbrains.kotlin.js.translate.general.Merger$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Merger.tryUpdateTests$lambda$5$lambda$1(function1, obj);
                }
            });
            Intrinsics.checkNotNull(jsStatementComputeIfAbsent);
            JsBlock jsBlockDecomposeTestInvocation2 = decomposeTestInvocation(jsStatementComputeIfAbsent);
            if (jsBlockDecomposeTestInvocation2 == null || (jsBlockDecomposeTestInvocation = decomposeTestInvocation(tests)) == null) {
                return;
            }
            List<JsStatement> statements = jsBlockDecomposeTestInvocation2.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            List<JsStatement> statements2 = jsBlockDecomposeTestInvocation.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            CollectionsKt.addAll(statements, statements2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsStatement tryUpdateTests$lambda$5$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (JsStatement) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsBlock decomposeTestInvocation(JsStatement jsStatement) {
        JsExpressionStatement jsExpressionStatement = jsStatement instanceof JsExpressionStatement ? (JsExpressionStatement) jsStatement : null;
        if (jsExpressionStatement == null) {
            return null;
        }
        JsExpression expression = jsExpressionStatement.getExpression();
        JsInvocation jsInvocation = expression instanceof JsInvocation ? (JsInvocation) expression : null;
        if (jsInvocation == null) {
            return null;
        }
        List<JsExpression> arguments = jsInvocation.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        Object orNull = CollectionsKt.getOrNull(arguments, 2);
        JsFunction jsFunction = orNull instanceof JsFunction ? (JsFunction) orNull : null;
        if (jsFunction != null) {
            return jsFunction.getBody();
        }
        return null;
    }

    private final void tryUpdateMain(JsProgramFragment jsProgramFragment) {
        JsStatement mainFunction = jsProgramFragment.getMainFunction();
        if (mainFunction != null) {
            Pair<String, ? extends JsStatement> pair = this.mainFn;
            String first = pair != null ? pair.getFirst() : null;
            if (first == null || first.compareTo(jsProgramFragment.getPackageFqn()) > 0) {
                this.mainFn = TuplesKt.to(jsProgramFragment.getPackageFqn(), mainFunction);
            }
        }
    }

    public final List<JsImportedModule> getImportedModules() {
        return this.importedModulesImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsName rename(Map<JsName, ? extends JsName> map, JsName jsName) {
        JsName jsName2 = map.get(jsName);
        if (jsName2 != null) {
            jsName = jsName2;
        }
        return jsName;
    }

    private final Map<JsName, JsName> buildNameMap(JsProgramFragment fragment) {
        Merger merger = this;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (JsNameBinding jsNameBinding : fragment.getNameBindings()) {
            JsName name2 = jsNameBinding.getName();
            Map<String, JsName> map = merger.nameTable;
            String key = jsNameBinding.getKey();
            JsName jsNameDeclareTemporaryName = map.get(key);
            if (jsNameDeclareTemporaryName == null) {
                jsNameDeclareTemporaryName = JsScope.declareTemporaryName(jsNameBinding.getName().getIdent());
                jsNameDeclareTemporaryName.copyMetadataFrom(jsNameBinding.getName());
                Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "also(...)");
                map.put(key, jsNameDeclareTemporaryName);
            }
            linkedHashMap.put(name2, jsNameDeclareTemporaryName);
        }
        JsName jsNameFindName = fragment.getScope().findName(Namer.getRootPackageName());
        if (jsNameFindName != null) {
            linkedHashMap.put(jsNameFindName, merger.internalModuleName);
        }
        for (JsImportedModule jsImportedModule : fragment.getImportedModules()) {
            JsName internalName = jsImportedModule.getInternalName();
            Map<JsImportedModuleKey, JsName> map2 = merger.importedModuleTable;
            JsImportedModuleKey key2 = jsImportedModule.getKey();
            JsName jsNameDeclareTemporaryName2 = map2.get(key2);
            if (jsNameDeclareTemporaryName2 == null) {
                JsFunctionScope scope = merger.rootFunction.getScope();
                Intrinsics.checkNotNullExpressionValue(scope, "getScope(...)");
                JsName internalName2 = jsImportedModule.getInternalName();
                jsNameDeclareTemporaryName2 = internalName2.isTemporary() ? JsScope.declareTemporaryName(internalName2.getIdent()) : scope.declareName(internalName2.getIdent());
                Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName2, "let(...)");
                merger.importedModulesImpl.add(new JsImportedModule(jsImportedModule.getExternalName(), jsNameDeclareTemporaryName2, jsImportedModule.getPlainReference(), null, 8, null));
                jsNameDeclareTemporaryName2.copyMetadataFrom(jsImportedModule.getInternalName());
                map2.put(key2, jsNameDeclareTemporaryName2);
            }
            linkedHashMap.put(internalName, jsNameDeclareTemporaryName2);
            merger = this;
        }
        return linkedHashMap;
    }

    private final void addExportStatements(JsProgramFragment fragment) {
        String exportedTag;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (JsStatement jsStatement : fragment.getExportBlock().getStatements()) {
            if (jsStatement instanceof JsVars) {
                JsVars jsVars = (JsVars) jsStatement;
                if (MetadataProperties.getExportedPackage(jsVars) != null) {
                    String exportedPackage = MetadataProperties.getExportedPackage(jsVars);
                    Intrinsics.checkNotNull(exportedPackage);
                    JsName name2 = jsVars.getVars().get(0).getName();
                    if (this.exportedPackages.containsKey(exportedPackage)) {
                        Intrinsics.checkNotNull(name2);
                        JsName jsName = this.exportedPackages.get(exportedPackage);
                        Intrinsics.checkNotNull(jsName);
                        linkedHashMap.put(name2, jsName);
                    } else {
                        Map<String, JsName> map = this.exportedPackages;
                        Intrinsics.checkNotNull(name2);
                        map.put(exportedPackage, name2);
                        Collection statements = this.exportBlock.getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                        statements.add(rename(linkedHashMap, jsStatement.deepCopy()));
                    }
                }
            }
            if (!(jsStatement instanceof JsExpressionStatement) || (exportedTag = MetadataProperties.getExportedTag((JsExpressionStatement) jsStatement)) == null || this.exportedTags.add(exportedTag)) {
                Collection statements2 = this.exportBlock.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                statements2.add(rename(linkedHashMap, jsStatement.deepCopy()));
            }
        }
    }

    private final void rename(Map<JsName, ? extends JsName> map, JsProgramFragment jsProgramFragment) {
        rename(map, jsProgramFragment.getDeclarationBlock());
        rename(map, jsProgramFragment.getExportBlock());
        rename(map, jsProgramFragment.getInitializerBlock());
        for (JsNameBinding jsNameBinding : jsProgramFragment.getNameBindings()) {
            jsNameBinding.setName(rename(map, jsNameBinding.getName()));
        }
        Iterator<T> it = jsProgramFragment.getImports().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            entry.setValue(rename(map, (JsNode) entry.getValue()));
        }
        for (JsImportedModule jsImportedModule : jsProgramFragment.getImportedModules()) {
            jsImportedModule.setInternalName(rename(map, jsImportedModule.getInternalName()));
            JsExpression plainReference = jsImportedModule.getPlainReference();
            if (plainReference != null) {
                rename(map, plainReference);
            }
        }
        Collection<JsClassModel> collectionValues = jsProgramFragment.getClasses().values();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues, 10));
        for (JsClassModel jsClassModel : collectionValues) {
            JsName jsNameRename = rename(map, jsClassModel.getName());
            JsName superName = jsClassModel.getSuperName();
            JsClassModel jsClassModel2 = new JsClassModel(jsNameRename, superName != null ? rename(map, superName) : null);
            List<JsStatement> statements = jsClassModel2.getPostDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            List<JsStatement> statements2 = ((JsCompositeBlock) rename(map, jsClassModel.getPostDeclarationBlock())).getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            CollectionsKt.addAll(statements, statements2);
            Set<JsName> interfaces = jsClassModel.getInterfaces();
            Set<JsName> interfaces2 = jsClassModel2.getInterfaces();
            Iterator<T> it2 = interfaces.iterator();
            while (it2.hasNext()) {
                interfaces2.add(rename(map, (JsName) it2.next()));
            }
            arrayList.add(jsClassModel2);
        }
        jsProgramFragment.getClasses().clear();
        Map<JsName, JsClassModel> classes = jsProgramFragment.getClasses();
        ArrayList<JsClassModel> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (JsClassModel jsClassModel3 : arrayList2) {
            arrayList3.add(TuplesKt.to(jsClassModel3.getName(), jsClassModel3));
        }
        MapsKt.putAll(classes, arrayList3);
        Iterator<Map.Entry<String, JsExpression>> it3 = jsProgramFragment.getInlineModuleMap().entrySet().iterator();
        while (it3.hasNext()) {
            rename(map, it3.next().getValue());
        }
        JsStatement tests = jsProgramFragment.getTests();
        if (tests != null) {
        }
        JsStatement mainFunction = jsProgramFragment.getMainFunction();
        if (mainFunction != null) {
        }
        Iterator<T> it4 = jsProgramFragment.getInlinedLocalDeclarations().values().iterator();
        while (it4.hasNext()) {
            rename(map, (JsCompositeBlock) it4.next());
        }
    }

    private final <T extends JsNode> T rename(final Map<JsName, ? extends JsName> map, T t) {
        t.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.translate.general.Merger.rename.10
            @Override // org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitor
            protected void visitElement(JsNode node) {
                Intrinsics.checkNotNullParameter(node, "node");
                super.visitElement(node);
                if (node instanceof HasName) {
                    HasName hasName = (HasName) node;
                    JsName name2 = hasName.getName();
                    hasName.setName(name2 != null ? Merger.this.rename(map, name2) : null);
                }
            }
        });
        return t;
    }

    private final void mergeNames() {
        for (JsProgramFragment jsProgramFragment : this.fragments) {
            rename(buildNameMap(jsProgramFragment), jsProgramFragment);
            for (Map.Entry<String, JsExpression> entry : jsProgramFragment.getImports().entrySet()) {
                String key = entry.getKey();
                JsExpression value = entry.getValue();
                if (this.declaredImports.add(key)) {
                    JsName jsName = this.nameTable.get(key);
                    Intrinsics.checkNotNull(jsName);
                    List<JsStatement> statements = this.importBlock.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                    statements.add(JsAstUtils.newVar(jsName, value));
                }
            }
            Iterator<Map.Entry<String, JsCompositeBlock>> it = jsProgramFragment.getInlinedLocalDeclarations().entrySet().iterator();
            while (it.hasNext()) {
                List<JsStatement> statements2 = it.next().getValue().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                for (JsStatement jsStatement : statements2) {
                    if (isFakeOverrideAssignment(jsStatement)) {
                        this.additionalFakeOverrides.add(jsStatement);
                    } else {
                        List<JsStatement> statements3 = this.declarationBlock.getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                        statements3.add(jsStatement);
                    }
                }
            }
            List<JsStatement> statements4 = this.declarationBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
            statements4.add(jsProgramFragment.getDeclarationBlock());
            List<JsStatement> statements5 = this.initializerBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements5, "getStatements(...)");
            statements5.add(jsProgramFragment.getInitializerBlock());
            tryUpdateTests(jsProgramFragment);
            tryUpdateMain(jsProgramFragment);
            addExportStatements(jsProgramFragment);
            this.classes.putAll(jsProgramFragment.getClasses());
        }
    }

    private static final boolean isFakeOverrideAssignment$isMemberReference(JsExpression jsExpression) {
        JsNameRef jsNameRef = jsExpression instanceof JsNameRef ? (JsNameRef) jsExpression : null;
        JsExpression qualifier = jsNameRef != null ? jsNameRef.getQualifier() : null;
        JsNameRef jsNameRef2 = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
        return jsNameRef2 != null && jsNameRef2.getName() == null && Intrinsics.areEqual(jsNameRef2.getIdent(), "prototype");
    }

    private final boolean isFakeOverrideAssignment(JsStatement jsStatement) {
        JsExpressionStatement jsExpressionStatement = jsStatement instanceof JsExpressionStatement ? (JsExpressionStatement) jsStatement : null;
        JsExpression expression = jsExpressionStatement != null ? jsExpressionStatement.getExpression() : null;
        JsBinaryOperation jsBinaryOperation = expression instanceof JsBinaryOperation ? (JsBinaryOperation) expression : null;
        return jsBinaryOperation != null && jsBinaryOperation.getOperator() == JsBinaryOperator.ASG && isFakeOverrideAssignment$isMemberReference(jsBinaryOperation.getArg1()) && isFakeOverrideAssignment$isMemberReference(jsBinaryOperation.getArg2());
    }

    public final void merge() {
        JsStatement second;
        mergeNames();
        List<JsStatement> statements = this.rootFunction.getBody().getStatements();
        Intrinsics.checkNotNull(statements);
        addImportForInlineDeclarationIfNecessary(statements);
        List<JsStatement> list = statements;
        List<JsStatement> statements2 = this.importBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        CollectionsKt.addAll(list, statements2);
        addClassPrototypes(statements);
        List<JsStatement> statements3 = this.declarationBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
        CollectionsKt.addAll(list, statements3);
        List<JsStatement> statements4 = this.exportBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
        CollectionsKt.addAll(list, statements4);
        addClassPostDeclarations(statements);
        CollectionsKt.addAll(list, this.additionalFakeOverrides);
        List<JsStatement> statements5 = this.initializerBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements5, "getStatements(...)");
        CollectionsKt.addAll(list, statements5);
        CollectionsKt.addAll(list, this.testsMap.values());
        Pair<String, ? extends JsStatement> pair = this.mainFn;
        if (pair == null || (second = pair.getSecond()) == null) {
            return;
        }
        list.add(second);
    }

    public final JsProgram buildProgram() {
        merge();
        JsBlock body = this.rootFunction.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        List<JsStatement> statements = body.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        statements.add(0, new JsStringLiteral("use strict").makeStmt());
        Companion companion = INSTANCE;
        if (!companion.isBuiltinModule(this.fragments)) {
            companion.defineModule(this.program, statements, this.moduleId);
        }
        Iterator<JsImportedModule> it = getImportedModules().iterator();
        while (it.hasNext()) {
            this.rootFunction.getParameters().add(new JsParameter(it.next().getInternalName()));
        }
        statements.add(new JsReturn(this.internalModuleName.makeRef()));
        this.program.getGlobalBlock().getStatements().addAll(ModuleWrapperTranslation.wrapIfNecessary(this.moduleId, this.rootFunction, getImportedModules(), this.program, this.moduleKind));
        return this.program;
    }

    private final void addImportForInlineDeclarationIfNecessary(List<JsStatement> list) {
        JsName jsName = this.nameTable.get(Namer.IMPORTS_FOR_INLINE_PROPERTY);
        if (jsName == null) {
            return;
        }
        list.add(UtilsKt.definePackageAlias(Namer.IMPORTS_FOR_INLINE_PROPERTY, jsName, Namer.IMPORTS_FOR_INLINE_PROPERTY, new JsNameRef(Namer.getRootPackageName())));
    }

    private final void addClassPrototypes(List<JsStatement> statements) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<JsName> it = this.classes.keySet().iterator();
        while (it.hasNext()) {
            addClassPrototypes(it.next(), linkedHashSet, statements);
        }
    }

    private final void addClassPrototypes(JsName name2, Set<JsName> visited, List<JsStatement> statements) {
        JsClassModel jsClassModel;
        JsName superName;
        if (!visited.add(name2) || (jsClassModel = this.classes.get(name2)) == null || (superName = jsClassModel.getSuperName()) == null) {
            return;
        }
        addClassPrototypes(superName, visited, statements);
        CollectionsKt.addAll(statements, UtilsKt.createPrototypeStatements(superName, name2));
    }

    private final void addClassPostDeclarations(List<JsStatement> statements) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<JsName> it = this.classes.keySet().iterator();
        while (it.hasNext()) {
            addClassPostDeclarations(it.next(), linkedHashSet, statements);
        }
    }

    private final void addClassPostDeclarations(JsName name2, Set<JsName> visited, List<JsStatement> statements) {
        JsClassModel jsClassModel;
        if (visited.add(name2) && (jsClassModel = this.classes.get(name2)) != null) {
            JsName superName = jsClassModel.getSuperName();
            if (superName != null) {
                addClassPostDeclarations(superName, visited, statements);
            }
            Iterator<T> it = jsClassModel.getInterfaces().iterator();
            while (it.hasNext()) {
                addClassPostDeclarations((JsName) it.next(), visited, statements);
            }
            List<JsStatement> statements2 = jsClassModel.getPostDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            CollectionsKt.addAll(statements, statements2);
        }
    }

    /* JADX INFO: compiled from: Merger.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\u0004H\u0002J\u0016\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/general/Merger$Companion;", "", "()V", "ENUM_SIGNATURE", "", "defineModule", "", ConfigConstants.CONFIG_KEY_PROGRAM, "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "statements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "moduleId", "isBuiltinModule", "", "fragments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isBuiltinModule(List<JsProgramFragment> fragments) {
            for (JsProgramFragment jsProgramFragment : fragments) {
                Iterator<JsNameBinding> it = jsProgramFragment.getNameBindings().iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(it.next().getKey(), Merger.ENUM_SIGNATURE) && !jsProgramFragment.getImports().containsKey(Merger.ENUM_SIGNATURE)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void defineModule(JsProgram program, List<JsStatement> statements, String moduleId) {
            JsName jsNameFindName = program.getScope().findName(Namer.getRootPackageName());
            if (jsNameFindName != null) {
                Namer namerNewInstance = Namer.newInstance(program.getScope());
                Intrinsics.checkNotNullExpressionValue(namerNewInstance, "newInstance(...)");
                JsStatement jsStatementMakeStmt = new JsInvocation(namerNewInstance.kotlin("defineModule"), new JsStringLiteral(moduleId), jsNameFindName.makeRef()).makeStmt();
                Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
                statements.add(jsStatementMakeStmt);
            }
        }
    }
}
