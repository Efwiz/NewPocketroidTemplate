package org.jetbrains.kotlin.js.translate.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
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
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: ModuleWrapperTranslation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J.\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0002J,\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0002J,\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0002J<\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J4\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0002J4\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u00112\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/general/ModuleWrapperTranslation;", "", "()V", "addModuleValidation", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "currentModuleId", "", ConfigConstants.CONFIG_KEY_PROGRAM, "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "module", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "makePlainInvocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "moduleId", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "importedModules", "", "makePlainModuleRef", "wrapAmd", "wrapCommonJs", "wrapIfNecessary", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "wrapPlain", "wrapUmd", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
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

    private ModuleWrapperTranslation() {
    }

    @JvmStatic
    public static final List<JsStatement> wrapIfNecessary(String moduleId, JsExpression function, List<JsImportedModule> importedModules, JsProgram program, ModuleKind kind) {
        Intrinsics.checkNotNullParameter(moduleId, "moduleId");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(importedModules, "importedModules");
        Intrinsics.checkNotNullParameter(program, "program");
        Intrinsics.checkNotNullParameter(kind, "kind");
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        if (i == 1) {
            return INSTANCE.wrapAmd(function, importedModules, program);
        }
        if (i == 2) {
            return INSTANCE.wrapCommonJs(function, importedModules, program);
        }
        if (i == 3) {
            return INSTANCE.wrapUmd(moduleId, function, importedModules, program);
        }
        if (i == 4) {
            return INSTANCE.wrapPlain(moduleId, function, importedModules, program);
        }
        if (i != 5) {
            throw new NoWhenBranchMatchedException();
        }
        throw new IllegalStateException("ES modules are not supported in legacy wrapper".toString());
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
        JsBinaryOperation jsBinaryOperationAnd = JsAstUtils.and(JsAstUtils.typeOfIs(jsNameDeclareName.makeRef(), new JsStringLiteral("function")), new JsNameRef(K2JsArgumentConstants.MODULE_AMD, jsNameDeclareName.makeRef()));
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAnd, "and(...)");
        JsBinaryOperation jsBinaryOperationTypeOfIs = JsAstUtils.typeOfIs(jsNameDeclareName2.makeRef(), new JsStringLiteral("object"));
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationTypeOfIs, "typeOfIs(...)");
        JsNameRef jsNameRefMakeRef = jsNameDeclareName4.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        JsBlock jsBlock2 = new JsBlock(wrapAmd(jsNameRefMakeRef, importedModules, program));
        JsNameRef jsNameRefMakeRef2 = jsNameDeclareName4.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef2, "makeRef(...)");
        JsBlock jsBlock3 = new JsBlock(wrapCommonJs(jsNameRefMakeRef2, importedModules, program));
        JsNameRef jsNameRefMakeRef3 = jsNameDeclareName4.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef3, "makeRef(...)");
        JsInvocation jsInvocationMakePlainInvocation = makePlainInvocation(moduleId, jsNameRefMakeRef3, importedModules, program);
        if (Namer.requiresEscaping(moduleId)) {
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
        statements2.add(JsAstUtils.assignment(jsNameRef, jsInvocationMakePlainInvocation).makeStmt());
        JsIf jsIfNewJsIf = JsAstUtils.newJsIf(jsBinaryOperationAnd, jsBlock2, JsAstUtils.newJsIf(jsBinaryOperationTypeOfIs, jsBlock3, jsBlock4));
        Intrinsics.checkNotNullExpressionValue(jsIfNewJsIf, "newJsIf(...)");
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
            arrayList.add(new JsStringLiteral(((JsImportedModule) it.next()).getExternalName()));
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
            arrayList.add(new JsInvocation(jsNameDeclareName2.makeRef(), new JsStringLiteral(((JsImportedModule) it.next()).getExternalName())));
        }
        JsStatement jsStatementMakeStmt = new JsInvocation(function, (List<? extends JsExpression>) CollectionsKt.plus((Collection) CollectionsKt.listOf(new JsNameRef(PsiKeyword.EXPORTS, jsNameDeclareName.makeRef())), (Iterable) arrayList)).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return CollectionsKt.listOf(jsStatementMakeStmt);
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
        if (Namer.requiresEscaping(moduleId)) {
            jsVarsNewVar = JsAstUtils.assignment(makePlainModuleRef(moduleId, program), jsInvocationMakePlainInvocation).makeStmt();
        } else {
            jsVarsNewVar = JsAstUtils.newVar(program.getRootScope().declareName(moduleId), jsInvocationMakePlainInvocation);
        }
        arrayList2.add(jsVarsNewVar);
        return arrayList;
    }

    private final JsStatement addModuleValidation(String currentModuleId, JsProgram program, JsImportedModule module) {
        JsBinaryOperation jsBinaryOperationTypeOfIs = JsAstUtils.typeOfIs(makePlainModuleRef(module, program), new JsStringLiteral("undefined"));
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationTypeOfIs, "typeOfIs(...)");
        return new JsIf(jsBinaryOperationTypeOfIs, new JsBlock(new JsThrow(new JsNew(new JsNameRef("Error"), CollectionsKt.listOf(new JsStringLiteral("Error loading module '" + currentModuleId + "'. Its dependency '" + module.getExternalName() + "' was not found. Please, check whether '" + module.getExternalName() + "' is loaded prior to '" + currentModuleId + "'."))))));
    }

    private final JsInvocation makePlainInvocation(String moduleId, JsExpression function, List<JsImportedModule> importedModules, JsProgram program) {
        List<JsImportedModule> list = importedModules;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(INSTANCE.makePlainModuleRef((JsImportedModule) it.next(), program));
        }
        JsExpression jsExpressionMakePlainModuleRef = makePlainModuleRef(moduleId, program);
        JsBinaryOperation jsBinaryOperationTypeOfIs = JsAstUtils.typeOfIs(jsExpressionMakePlainModuleRef, new JsStringLiteral("undefined"));
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationTypeOfIs, "typeOfIs(...)");
        return new JsInvocation(function, (List<? extends JsExpression>) CollectionsKt.plus((Collection) CollectionsKt.listOf(new JsConditional(jsBinaryOperationTypeOfIs, new JsObjectLiteral(false), jsExpressionMakePlainModuleRef.deepCopy())), (Iterable) arrayList));
    }

    private final JsExpression makePlainModuleRef(JsImportedModule module, JsProgram program) {
        JsExpression plainReference = module.getPlainReference();
        return plainReference == null ? makePlainModuleRef(module.getExternalName(), program) : plainReference;
    }

    private final JsExpression makePlainModuleRef(String moduleId, JsProgram program) {
        if (Namer.requiresEscaping(moduleId)) {
            return new JsArrayAccess(new JsThisRef(), new JsStringLiteral(moduleId));
        }
        JsNameRef jsNameRefMakeRef = program.getScope().declareName(moduleId).makeRef();
        Intrinsics.checkNotNull(jsNameRefMakeRef);
        return jsNameRefMakeRef;
    }
}
