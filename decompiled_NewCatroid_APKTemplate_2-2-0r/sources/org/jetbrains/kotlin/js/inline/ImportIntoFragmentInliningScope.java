package org.jetbrains.kotlin.js.inline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsClassModel;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsDynamicScope;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModuleKey;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameBinding;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.LocalAlias;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.clean.EmergePrimitiveKClassKt;
import org.jetbrains.kotlin.js.inline.clean.RemoveUnusedFunctionDefinitionsKt;
import org.jetbrains.kotlin.js.inline.clean.RemoveUnusedImportsKt;
import org.jetbrains.kotlin.js.inline.clean.RenameLabelsKt;
import org.jetbrains.kotlin.js.inline.clean.SimplifyWrappedFunctionsKt;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;

/* JADX INFO: compiled from: InliningScope.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 #2\u00020\u0001:\u0001#B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0019H\u0014J\u001a\u0010\u001a\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001b\u001a\u00020\u0007H\u0014J\u0010\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u0012H\u0002J\u0018\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000eH\u0002J\u001a\u0010 \u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000eH\u0014J\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u0007H\u0014R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/ImportIntoFragmentInliningScope;", "Lorg/jetbrains/kotlin/js/inline/InliningScope;", "fragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;)V", "additionalDeclarations", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "allCode", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "getAllCode", "()Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "existingBindings", "", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "existingModules", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModuleKey;", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "getFragment", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "addImport", "", "tag", "vars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "addInlinedDeclaration", "declaration", "addInlinedModule", "module", "addNameBinding", "name", "hasImport", "preprocess", "statement", "Companion", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImportIntoFragmentInliningScope extends InliningScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<JsStatement> additionalDeclarations;
    private final Map<String, JsName> existingBindings;
    private final Map<JsImportedModuleKey, JsImportedModule> existingModules;
    private final JsProgramFragment fragment;

    public /* synthetic */ ImportIntoFragmentInliningScope(JsProgramFragment jsProgramFragment, DefaultConstructorMarker defaultConstructorMarker) {
        this(jsProgramFragment);
    }

    @Override // org.jetbrains.kotlin.js.inline.InliningScope
    public JsProgramFragment getFragment() {
        return this.fragment;
    }

    private ImportIntoFragmentInliningScope(JsProgramFragment jsProgramFragment) {
        super(null);
        this.fragment = jsProgramFragment;
        List<JsImportedModule> importedModules = getFragment().getImportedModules();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (JsImportedModule jsImportedModule : importedModules) {
            Pair pair = TuplesKt.to(jsImportedModule.getKey(), jsImportedModule);
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.existingModules = linkedHashMap;
        List<JsNameBinding> nameBindings = getFragment().getNameBindings();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (JsNameBinding jsNameBinding : nameBindings) {
            Pair pair2 = TuplesKt.to(jsNameBinding.getKey(), jsNameBinding.getName());
            linkedHashMap2.put(pair2.getFirst(), pair2.getSecond());
        }
        this.existingBindings = linkedHashMap2;
        this.additionalDeclarations = new ArrayList();
    }

    public final JsBlock getAllCode() {
        JsStatement[] jsStatementArr = new JsStatement[5];
        jsStatementArr[0] = new JsBlock((List<JsStatement>) CollectionsKt.toList(getFragment().getInlinedLocalDeclarations().values()));
        jsStatementArr[1] = getFragment().getDeclarationBlock();
        Collection<JsClassModel> collectionValues = getFragment().getClasses().values();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues, 10));
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            arrayList.add(((JsClassModel) it.next()).getPostDeclarationBlock());
        }
        jsStatementArr[2] = new JsBlock(arrayList);
        jsStatementArr[3] = getFragment().getExportBlock();
        jsStatementArr[4] = new JsExpressionStatement(new JsFunction(JsDynamicScope.INSTANCE, getFragment().getInitializerBlock(), ""));
        JsBlock jsBlock = new JsBlock(jsStatementArr);
        JsStatement tests = getFragment().getTests();
        if (tests != null) {
            jsBlock.getStatements().add(tests);
        }
        JsStatement mainFunction = getFragment().getMainFunction();
        if (mainFunction != null) {
            jsBlock.getStatements().add(mainFunction);
        }
        return jsBlock;
    }

    @Override // org.jetbrains.kotlin.js.inline.InliningScope
    protected JsName hasImport(JsName name2, String tag) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(tag, "tag");
        LocalAlias localAlias = MetadataProperties.getLocalAlias(name2);
        if (localAlias != null) {
            JsName name3 = localAlias.getName();
            String tag2 = localAlias.getTag();
            if (tag2 != null) {
                if (!this.existingBindings.containsKey(tag2)) {
                    addNameBinding(name3, tag2);
                }
                name3 = this.existingBindings.get(tag2);
            }
            if (name3 != null) {
                return name3;
            }
        }
        return this.existingBindings.get(tag);
    }

    private final void addNameBinding(JsName name2, String tag) {
        getFragment().getNameBindings().add(new JsNameBinding(tag, name2));
        this.existingBindings.put(tag, name2);
    }

    @Override // org.jetbrains.kotlin.js.inline.InliningScope
    protected void addImport(String tag, JsVars vars) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(vars, "vars");
        JsName name2 = vars.getVars().get(0).getName();
        JsExpression initExpression = vars.getVars().get(0).getInitExpression();
        Map<String, JsExpression> imports = getFragment().getImports();
        Intrinsics.checkNotNull(initExpression);
        imports.put(tag, initExpression);
        Intrinsics.checkNotNull(name2);
        addNameBinding(name2, tag);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsCompositeBlock addInlinedDeclaration$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (JsCompositeBlock) tmp0.invoke(obj);
    }

    @Override // org.jetbrains.kotlin.js.inline.InliningScope
    protected void addInlinedDeclaration(String tag, JsStatement declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (tag != null) {
            Map<String, JsCompositeBlock> inlinedLocalDeclarations = getFragment().getInlinedLocalDeclarations();
            final AnonymousClass1 anonymousClass1 = new Function1<String, JsCompositeBlock>() { // from class: org.jetbrains.kotlin.js.inline.ImportIntoFragmentInliningScope.addInlinedDeclaration.1
                @Override // kotlin.jvm.functions.Function1
                public final JsCompositeBlock invoke(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new JsCompositeBlock();
                }
            };
            inlinedLocalDeclarations.computeIfAbsent(tag, new Function() { // from class: org.jetbrains.kotlin.js.inline.ImportIntoFragmentInliningScope$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ImportIntoFragmentInliningScope.addInlinedDeclaration$lambda$7(anonymousClass1, obj);
                }
            }).getStatements().add(declaration);
            return;
        }
        this.additionalDeclarations.add(declaration);
    }

    @Override // org.jetbrains.kotlin.js.inline.InliningScope
    protected void preprocess(JsStatement statement) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.ImportIntoFragmentInliningScope.preprocess.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsNameRef x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                replaceIfNecessary(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsArrayAccess x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                replaceIfNecessary(x, ctx);
            }

            private final void replaceIfNecessary(JsExpression expression, JsContext<JsNode> ctx) {
                JsImportedModule localAlias = MetadataProperties.getLocalAlias(expression);
                if (localAlias != null) {
                    ctx.replaceMe(ImportIntoFragmentInliningScope.this.addInlinedModule(localAlias).makeRef());
                }
            }
        }.accept(statement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsName addInlinedModule(final JsImportedModule module) {
        Map<JsImportedModuleKey, JsImportedModule> map = this.existingModules;
        JsImportedModuleKey key = module.getKey();
        final Function1<JsImportedModuleKey, JsImportedModule> function1 = new Function1<JsImportedModuleKey, JsImportedModule>() { // from class: org.jetbrains.kotlin.js.inline.ImportIntoFragmentInliningScope.addInlinedModule.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final JsImportedModule invoke(JsImportedModuleKey it) {
                Intrinsics.checkNotNullParameter(it, "it");
                JsImportedModule jsImportedModule = new JsImportedModule(module.getExternalName(), module.getInternalName(), module.getPlainReference(), null, 8, null);
                this.getFragment().getImportedModules().add(jsImportedModule);
                return jsImportedModule;
            }
        };
        return map.computeIfAbsent(key, new Function() { // from class: org.jetbrains.kotlin.js.inline.ImportIntoFragmentInliningScope$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ImportIntoFragmentInliningScope.addInlinedModule$lambda$8(function1, obj);
            }
        }).getInternalName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsImportedModule addInlinedModule$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (JsImportedModule) tmp0.invoke(obj);
    }

    /* JADX INFO: compiled from: InliningScope.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00040\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/ImportIntoFragmentInliningScope$Companion;", "", "()V", "process", "", "fragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "fn", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/js/inline/ImportIntoFragmentInliningScope;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void process(JsProgramFragment fragment, Function1<? super ImportIntoFragmentInliningScope, Unit> fn) {
            Intrinsics.checkNotNullParameter(fragment, "fragment");
            Intrinsics.checkNotNullParameter(fn, "fn");
            ImportIntoFragmentInliningScope importIntoFragmentInliningScope = new ImportIntoFragmentInliningScope(fragment, null);
            fn.invoke(importIntoFragmentInliningScope);
            fragment.getDeclarationBlock().getStatements().addAll(0, importIntoFragmentInliningScope.additionalDeclarations);
            new InlineSuspendFunctionSplitter(importIntoFragmentInliningScope).accept(importIntoFragmentInliningScope.getAllCode());
            SimplifyWrappedFunctionsKt.simplifyWrappedFunctions(importIntoFragmentInliningScope.getAllCode());
            EmergePrimitiveKClassKt.emergePrimitiveKClass(importIntoFragmentInliningScope.getAllCode());
            RemoveUnusedFunctionDefinitionsKt.removeUnusedFunctionDefinitions(importIntoFragmentInliningScope.getAllCode(), CollectUtilsKt.collectNamedFunctions(importIntoFragmentInliningScope.getAllCode()));
            RemoveUnusedImportsKt.removeUnusedImports(fragment, importIntoFragmentInliningScope.getAllCode());
            RenameLabelsKt.renameLabels(importIntoFragmentInliningScope.getAllCode());
        }
    }
}
