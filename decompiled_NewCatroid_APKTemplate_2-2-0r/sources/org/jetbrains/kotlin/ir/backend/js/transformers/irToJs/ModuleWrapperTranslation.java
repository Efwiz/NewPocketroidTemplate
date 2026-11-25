package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import com.fasterxml.jackson.core.JsonFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsImport;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModuleKt;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsObjectScope;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: ModuleWrapperTranslation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001cB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J.\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0002J:\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00142\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0016J,\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0002J,\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\u000e\u001a\u00020\u0014H\u0002J4\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0002J4\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/ModuleWrapperTranslation;", "", "()V", "addModuleValidation", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "currentModuleId", "", ConfigConstants.CONFIG_KEY_PROGRAM, "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "module", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "makePlainInvocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "moduleId", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "importedModules", "", "makePlainModuleRef", "wrap", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", org.jetbrains.kotlin.js.translate.context.Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "wrapAmd", "wrapCommonJs", "wrapEsModule", "wrapPlain", "wrapUmd", "Namer", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModuleWrapperTranslation {
    public static final ModuleWrapperTranslation INSTANCE = new ModuleWrapperTranslation();

    /* JADX INFO: compiled from: ModuleWrapperTranslation.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ModuleKind.values().length];
            try {
                iArr[ModuleKind.AMD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ModuleKind.COMMON_JS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ModuleKind.UMD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ModuleKind.PLAIN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ModuleKind.ES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: compiled from: ModuleWrapperTranslation.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/ModuleWrapperTranslation$Namer;", "", "()V", "RESERVED_IDENTIFIERS", "", "", "requiresEscaping", "", "name", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Namer {
        public static final Namer INSTANCE = new Namer();
        private static final Set<String> RESERVED_IDENTIFIERS = SetsKt.setOf((Object[]) new String[]{"await", PsiKeyword.BREAK, PsiKeyword.CASE, PsiKeyword.CATCH, PsiKeyword.CONTINUE, "debugger", "default", "delete", PsiKeyword.DO, PsiKeyword.ELSE, PsiKeyword.FINALLY, PsiKeyword.FOR, "function", PsiKeyword.IF, "in", PsiKeyword.INSTANCEOF, PsiKeyword.NEW, "return", PsiKeyword.SWITCH, PsiKeyword.THROW, PsiKeyword.TRY, "typeof", PsiKeyword.VAR, PsiKeyword.VOID, PsiKeyword.WHILE, "with", "class", PsiKeyword.CONST, PsiKeyword.ENUM, "export", PsiKeyword.EXTENDS, PsiKeyword.IMPORT, PsiKeyword.SUPER, PsiKeyword.IMPLEMENTS, PsiKeyword.INTERFACE, "let", "package", "private", "protected", "public", "static", PsiKeyword.YIELD, "null", "true", "false", "eval", "arguments", "NaN", "isNaN", "Infinity", "undefined", "Error", CommonClassNames.JAVA_LANG_OBJECT_SHORT, "Number", CommonClassNames.JAVA_LANG_STRING_SHORT, "Math", CommonClassNames.JAVA_LANG_STRING_SHORT, "Boolean", "Date", "Array", "RegExp", JsonFactory.FORMAT_NAME_JSON, "Map", "require", "define", "module", ConfigConstants.CONFIG_KEY_WINDOW, "self", "globalThis"});

        private Namer() {
        }

        public final boolean requiresEscaping(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return !IdentifierPolicyKt.isValidES5Identifier(name2) || RESERVED_IDENTIFIERS.contains(name2);
        }
    }

    private ModuleWrapperTranslation() {
    }

    public final List<JsStatement> wrap(String moduleId, JsFunction function, List<JsImportedModule> importedModules, JsProgram program, ModuleKind kind) {
        Intrinsics.checkNotNullParameter(moduleId, "moduleId");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(importedModules, "importedModules");
        Intrinsics.checkNotNullParameter(program, "program");
        Intrinsics.checkNotNullParameter(kind, "kind");
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        if (i == 1) {
            return wrapAmd(function, importedModules, program);
        }
        if (i == 2) {
            return wrapCommonJs(function, importedModules, program);
        }
        if (i == 3) {
            return wrapUmd(moduleId, function, importedModules, program);
        }
        if (i == 4) {
            return wrapPlain(moduleId, function, importedModules, program);
        }
        if (i == 5) {
            return wrapEsModule(function);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final List<JsStatement> wrapUmd(String moduleId, JsExpression function, List<JsImportedModule> importedModules, JsProgram program) {
        JsNameRef jsNameRef;
        JsObjectScope scope = program.getScope();
        JsName jsNameDeclareName = scope.declareName("define");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
        JsName jsNameDeclareName2 = scope.declareName(PsiKeyword.EXPORTS);
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName2, "declareName(...)");
        JsBlock jsBlock = new JsBlock();
        JsFunction jsFunction = new JsFunction(program.getScope(), jsBlock, "Adapter");
        JsName jsNameDeclareName3 = jsFunction.getScope().declareName("root");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName3, "declareName(...)");
        JsName jsNameDeclareName4 = jsFunction.getScope().declareName("factory");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName4, "declareName(...)");
        List<JsParameter> parameters = jsFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        parameters.add(new JsParameter(jsNameDeclareName3));
        List<JsParameter> parameters2 = jsFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
        parameters2.add(new JsParameter(jsNameDeclareName4));
        JsAstUtils jsAstUtils = JsAstUtils.INSTANCE;
        JsAstUtils jsAstUtils2 = JsAstUtils.INSTANCE;
        JsNameRef jsNameRefMakeRef = jsNameDeclareName.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        JsBinaryOperation jsBinaryOperationAnd = jsAstUtils.and(jsAstUtils2.typeOfIs(jsNameRefMakeRef, new JsStringLiteral("function")), new JsNameRef(K2JsArgumentConstants.MODULE_AMD, jsNameDeclareName.makeRef()));
        JsAstUtils jsAstUtils3 = JsAstUtils.INSTANCE;
        JsNameRef jsNameRefMakeRef2 = jsNameDeclareName2.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef2, "makeRef(...)");
        JsBinaryOperation jsBinaryOperationTypeOfIs = jsAstUtils3.typeOfIs(jsNameRefMakeRef2, new JsStringLiteral("object"));
        JsNameRef jsNameRefMakeRef3 = jsNameDeclareName4.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef3, "makeRef(...)");
        JsBlock jsBlock2 = new JsBlock(wrapAmd(jsNameRefMakeRef3, importedModules, program));
        JsNameRef jsNameRefMakeRef4 = jsNameDeclareName4.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef4, "makeRef(...)");
        JsBlock jsBlock3 = new JsBlock(wrapCommonJs(jsNameRefMakeRef4, importedModules, program));
        JsNameRef jsNameRefMakeRef5 = jsNameDeclareName4.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef5, "makeRef(...)");
        JsInvocation jsInvocationMakePlainInvocation = makePlainInvocation(moduleId, jsNameRefMakeRef5, importedModules, program);
        if (Namer.INSTANCE.requiresEscaping(moduleId)) {
            jsNameRef = new JsArrayAccess(jsNameDeclareName3.makeRef(), new JsStringLiteral(moduleId));
        } else {
            jsNameRef = new JsNameRef(scope.declareName(moduleId), jsNameDeclareName3.makeRef());
        }
        JsBlock jsBlock4 = new JsBlock();
        for (JsImportedModule jsImportedModule : importedModules) {
            List<JsStatement> statements = jsBlock4.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(addModuleValidation(moduleId, program, jsImportedModule));
        }
        List<JsStatement> statements2 = jsBlock4.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        statements2.add(JsAstUtils.INSTANCE.assignment(jsNameRef, jsInvocationMakePlainInvocation).makeStmt());
        JsIf jsIfNewJsIf = JsAstUtils.INSTANCE.newJsIf(jsBinaryOperationAnd, jsBlock2, JsAstUtils.INSTANCE.newJsIf(jsBinaryOperationTypeOfIs, jsBlock3, jsBlock4));
        List<JsStatement> statements3 = jsBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
        statements3.add(jsIfNewJsIf);
        JsStatement jsStatementMakeStmt = new JsInvocation(jsFunction, new JsThisRef(), function).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return CollectionsKt.listOf(jsStatementMakeStmt);
    }

    private final List<JsStatement> wrapAmd(JsExpression function, List<JsImportedModule> importedModules, JsProgram program) {
        JsName jsNameDeclareName = program.getScope().declareName("define");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
        JsExpression[] jsExpressionArr = new JsExpression[2];
        List listListOf = CollectionsKt.listOf(new JsStringLiteral(PsiKeyword.EXPORTS));
        List<JsImportedModule> list = importedModules;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new JsStringLiteral(JsImportedModuleKt.getRequireName$default((JsImportedModule) it.next(), false, 1, null)));
        }
        jsExpressionArr[0] = new JsArrayLiteral(CollectionsKt.plus((Collection) listListOf, (Iterable) arrayList));
        jsExpressionArr[1] = function;
        JsStatement jsStatementMakeStmt = new JsInvocation(jsNameDeclareName.makeRef(), (List<? extends JsExpression>) CollectionsKt.listOf((Object[]) jsExpressionArr)).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return CollectionsKt.listOf(jsStatementMakeStmt);
    }

    private final List<JsStatement> wrapCommonJs(JsExpression function, List<JsImportedModule> importedModules, JsProgram program) {
        JsObjectScope scope = program.getScope();
        JsName jsNameDeclareName = scope.declareName("module");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
        JsName jsNameDeclareName2 = scope.declareName("require");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName2, "declareName(...)");
        List<JsImportedModule> list = importedModules;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new JsInvocation(jsNameDeclareName2.makeRef(), new JsStringLiteral(JsImportedModuleKt.getRequireName$default((JsImportedModule) it.next(), false, 1, null))));
        }
        JsStatement jsStatementMakeStmt = new JsInvocation(function, (List<? extends JsExpression>) CollectionsKt.plus((Collection) CollectionsKt.listOf(new JsNameRef(PsiKeyword.EXPORTS, jsNameDeclareName.makeRef())), (Iterable) arrayList)).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return CollectionsKt.listOf(jsStatementMakeStmt);
    }

    private final List<JsStatement> wrapEsModule(JsFunction function) {
        List<JsStatement> listListOf;
        List<JsStatement> statements = function.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        ArrayList arrayList = new ArrayList();
        for (JsStatement jsStatement : statements) {
            if (jsStatement instanceof JsCompositeBlock) {
                listListOf = ((JsCompositeBlock) jsStatement).getStatements();
                Intrinsics.checkNotNullExpressionValue(listListOf, "getStatements(...)");
            } else {
                listListOf = CollectionsKt.listOf(jsStatement);
            }
            CollectionsKt.addAll(arrayList, listListOf);
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList) {
            if (obj instanceof JsImport) {
                arrayList2.add(obj);
            } else {
                arrayList3.add(obj);
            }
        }
        Pair pair = new Pair(arrayList2, arrayList3);
        List list = (List) pair.component1();
        List list2 = (List) pair.component2();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : list) {
            if (((JsImport) obj2).getTarget() instanceof JsImport.Target.Elements) {
                arrayList4.add(obj2);
            } else {
                arrayList5.add(obj2);
            }
        }
        Pair pair2 = new Pair(arrayList4, arrayList5);
        List list3 = (List) pair2.component1();
        List list4 = (List) pair2.component2();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj3 : list3) {
            String module = ((JsImport) obj3).getModule();
            Object obj4 = linkedHashMap.get(module);
            if (obj4 == null) {
                obj4 = (List) new ArrayList();
                linkedHashMap.put(module, obj4);
            }
            ((List) obj4).add(obj3);
        }
        ArrayList arrayList6 = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            String str = (String) entry.getKey();
            List list5 = (List) entry.getValue();
            ArrayList arrayList7 = new ArrayList();
            Iterator it = list5.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList7, ((JsImport) it.next()).getElements());
            }
            HashSet hashSet = new HashSet();
            ArrayList arrayList8 = new ArrayList();
            for (Object obj5 : arrayList7) {
                if (hashSet.add(((JsImport.Element) obj5).getName().getIdent())) {
                    arrayList8.add(obj5);
                }
            }
            JsImport.Element[] elementArr = (JsImport.Element[]) arrayList8.toArray(new JsImport.Element[0]);
            arrayList6.add(new JsImport(str, (JsImport.Element[]) Arrays.copyOf(elementArr, elementArr.length)));
        }
        return CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) arrayList6, (Iterable) list4), (Iterable) CollectionsKt.dropLast(list2, 1));
    }

    private final List<JsStatement> wrapPlain(String moduleId, JsExpression function, List<JsImportedModule> importedModules, JsProgram program) {
        JsVars jsVarsNewVar;
        JsInvocation jsInvocationMakePlainInvocation = makePlainInvocation(moduleId, function, importedModules, program);
        ArrayList arrayList = new ArrayList();
        Iterator<JsImportedModule> it = importedModules.iterator();
        while (it.hasNext()) {
            arrayList.add(addModuleValidation(moduleId, program, it.next()));
        }
        ArrayList arrayList2 = arrayList;
        if (Namer.INSTANCE.requiresEscaping(moduleId)) {
            jsVarsNewVar = JsAstUtils.INSTANCE.assignment(makePlainModuleRef(moduleId, program), jsInvocationMakePlainInvocation).makeStmt();
        } else {
            JsAstUtils jsAstUtils = JsAstUtils.INSTANCE;
            JsName jsNameDeclareName = program.getRootScope().declareName(moduleId);
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
            jsVarsNewVar = jsAstUtils.newVar(jsNameDeclareName, jsInvocationMakePlainInvocation);
        }
        arrayList2.add(jsVarsNewVar);
        return arrayList;
    }

    private final JsStatement addModuleValidation(String currentModuleId, JsProgram program, JsImportedModule module) {
        return new JsIf(JsAstUtils.INSTANCE.typeOfIs(makePlainModuleRef(module, program), new JsStringLiteral("undefined")), new JsBlock(new JsThrow(new JsNew(new JsNameRef("Error"), CollectionsKt.listOf(new JsStringLiteral("Error loading module '" + currentModuleId + "'. Its dependency '" + module.getExternalName() + "' was not found. Please, check whether '" + module.getExternalName() + "' is loaded prior to '" + currentModuleId + "'."))))));
    }

    private final JsInvocation makePlainInvocation(String moduleId, JsExpression function, List<JsImportedModule> importedModules, JsProgram program) {
        List<JsImportedModule> list = importedModules;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(INSTANCE.makePlainModuleRef((JsImportedModule) it.next(), program));
        }
        JsExpression jsExpressionMakePlainModuleRef = makePlainModuleRef(moduleId, program);
        return new JsInvocation(function, (List<? extends JsExpression>) CollectionsKt.plus((Collection) CollectionsKt.listOf(new JsConditional(JsAstUtils.INSTANCE.typeOfIs(jsExpressionMakePlainModuleRef, new JsStringLiteral("undefined")), new JsObjectLiteral(false), jsExpressionMakePlainModuleRef.deepCopy())), (Iterable) arrayList));
    }

    private final JsExpression makePlainModuleRef(JsImportedModule module, JsProgram program) {
        JsExpression plainReference = module.getPlainReference();
        return plainReference == null ? makePlainModuleRef(module.getExternalName(), program) : plainReference;
    }

    private final JsExpression makePlainModuleRef(String moduleId, JsProgram program) {
        if (Namer.INSTANCE.requiresEscaping(moduleId)) {
            return new JsArrayAccess(new JsThisRef(), new JsStringLiteral(moduleId));
        }
        JsNameRef jsNameRefMakeRef = program.getScope().declareName(moduleId).makeRef();
        Intrinsics.checkNotNull(jsNameRefMakeRef);
        return jsNameRefMakeRef;
    }
}
