package org.jetbrains.kotlin.js.translate.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: JSTestGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0006\u0010\u001e\u001a\u00020\u001fJ<\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00142\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00140$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00140$2\u0006\u0010&\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020(H\u0002J\u0018\u0010)\u001a\u00020!2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\bH\u0002J\u001e\u0010)\u001a\u0004\u0018\u00010-2\u0006\u0010.\u001a\u00020/2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u00180$J<\u00101\u001a\u00020(2\u0006\u0010\"\u001a\u00020\u00142\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00140$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00140$2\u0006\u0010&\u001a\u00020\u001f2\u0006\u00102\u001a\u000203H\u0002J\u0018\u00104\u001a\u00020!2\u0006\u0010&\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020(H\u0002J-\u00105\u001a\u00020\u0013*\u00020\u00182\u0006\u00106\u001a\u00020\u001d2\u0012\u00107\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d08\"\u00020\u001dH\u0002¢\u0006\u0002\u00109J\u0014\u0010:\u001a\u00020\n*\u00020\u001f2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0010\u0010\fR\u0018\u0010\u0012\u001a\u00020\u0013*\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0015R\u0018\u0010\u0016\u001a\u00020\u0013*\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0015R\u0018\u0010\u0017\u001a\u00020\u0013*\u00020\u00188BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019R\u0018\u0010\u001a\u001a\u00020\u0013*\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0015¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/test/JSTestGenerator;", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "getContext", "()Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "promiseFqName", "Lorg/jetbrains/kotlin/name/FqName;", "suiteRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getSuiteRef", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "suiteRef$delegate", "Lkotlin/Lazy;", "testRef", "getTestRef", "testRef$delegate", "isAfter", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)Z", "isBefore", "isIgnored", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Z", "isTest", "findFunction", "name", "", "findPromise", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "generateCodeForTestMethod", "", "functionDescriptor", "beforeDescriptors", "", "afterDescriptors", "classDescriptor", "parentFun", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "generateTestCalls", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "packageName", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "fileMemberScope", "generateTestFunction", "scope", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "generateTestFunctions", "annotationFinder", "shortName", "packages", "", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Ljava/lang/String;[Ljava/lang/String;)Z", "instance", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JSTestGenerator {
    private final TranslationContext context;
    private final FqName promiseFqName;

    /* JADX INFO: renamed from: suiteRef$delegate, reason: from kotlin metadata */
    private final Lazy suiteRef;

    /* JADX INFO: renamed from: testRef$delegate, reason: from kotlin metadata */
    private final Lazy testRef;

    public JSTestGenerator(TranslationContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.suiteRef = LazyKt.lazy(new Function0<JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.test.JSTestGenerator$suiteRef$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsExpression invoke() {
                return this.this$0.findFunction("suite");
            }
        });
        this.testRef = LazyKt.lazy(new Function0<JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.test.JSTestGenerator$testRef$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsExpression invoke() {
                return this.this$0.findFunction("test");
            }
        });
        this.promiseFqName = new FqName("kotlin.js.Promise");
    }

    public final TranslationContext getContext() {
        return this.context;
    }

    public final JsStatement generateTestCalls(KtFile file, List<? extends DeclarationDescriptor> fileMemberScope) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(fileMemberScope, "fileMemberScope");
        JsFunction jsFunction = new JsFunction(this.context.scope(), new JsBlock(), file.getVirtualFilePath() + " file suite function");
        for (DeclarationDescriptor declarationDescriptor : fileMemberScope) {
            if (declarationDescriptor instanceof ClassDescriptor) {
                generateTestFunctions((ClassDescriptor) declarationDescriptor, jsFunction);
            }
        }
        if (jsFunction.getBody().isEmpty()) {
            return null;
        }
        return new JsInvocation(getSuiteRef(), new JsStringLiteral(file.getPackageFqName().asString()), new JsBooleanLiteral(false), jsFunction).makeStmt();
    }

    private final void generateTestFunctions(ClassDescriptor classDescriptor, JsFunction parentFun) {
        if (classDescriptor.getModality() == Modality.ABSTRACT || classDescriptor.get$isExpect()) {
            return;
        }
        JsFunction jsFunction = new JsFunction(this.context.scope(), new JsBlock(), "suite function");
        Collection<DeclarationDescriptor> contributedDescriptors = classDescriptor.getUnsubstitutedMemberScope().getContributedDescriptors(DescriptorKindFilter.FUNCTIONS, MemberScope.INSTANCE.getALL_NAME_FILTER());
        ArrayList arrayList = new ArrayList();
        for (Object obj : contributedDescriptors) {
            if (obj instanceof FunctionDescriptor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (isBefore((FunctionDescriptor) obj2)) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList();
        for (Object obj3 : contributedDescriptors) {
            if (obj3 instanceof FunctionDescriptor) {
                arrayList4.add(obj3);
            }
        }
        ArrayList arrayList5 = new ArrayList();
        for (Object obj4 : arrayList4) {
            if (isAfter((FunctionDescriptor) obj4)) {
                arrayList5.add(obj4);
            }
        }
        ArrayList arrayList6 = arrayList5;
        for (DeclarationDescriptor declarationDescriptor : contributedDescriptors) {
            if (declarationDescriptor instanceof ClassDescriptor) {
                generateTestFunctions((ClassDescriptor) declarationDescriptor, jsFunction);
            } else if (declarationDescriptor instanceof FunctionDescriptor) {
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) declarationDescriptor;
                if (isTest(functionDescriptor)) {
                    generateCodeForTestMethod(functionDescriptor, arrayList3, arrayList6, classDescriptor, jsFunction);
                }
            }
        }
        if (jsFunction.getBody().isEmpty()) {
            return;
        }
        JsStringLiteral jsStringLiteral = new JsStringLiteral(classDescriptor.getShortName().toString());
        List<JsStatement> statements = parentFun.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        statements.add(new JsInvocation(getSuiteRef(), jsStringLiteral, new JsBooleanLiteral(isIgnored(classDescriptor)), jsFunction).makeStmt());
    }

    private final void generateCodeForTestMethod(FunctionDescriptor functionDescriptor, List<? extends FunctionDescriptor> beforeDescriptors, List<? extends FunctionDescriptor> afterDescriptors, ClassDescriptor classDescriptor, JsFunction parentFun) {
        JsFunctionScope scope = parentFun.getScope();
        Intrinsics.checkNotNullExpressionValue(scope, "getScope(...)");
        JsFunction jsFunctionGenerateTestFunction = generateTestFunction(functionDescriptor, beforeDescriptors, afterDescriptors, classDescriptor, scope);
        JsStringLiteral jsStringLiteral = new JsStringLiteral(functionDescriptor.getShortName().toString());
        List<JsStatement> statements = parentFun.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        statements.add(new JsInvocation(getTestRef(), jsStringLiteral, new JsBooleanLiteral(isIgnored(functionDescriptor)), jsFunctionGenerateTestFunction).makeStmt());
    }

    private final JsFunction generateTestFunction(FunctionDescriptor functionDescriptor, List<? extends FunctionDescriptor> beforeDescriptors, List<? extends FunctionDescriptor> afterDescriptors, ClassDescriptor classDescriptor, JsScope scope) {
        TypeConstructor constructor;
        JsFunction jsFunction = new JsFunction(scope, new JsBlock(), "test function");
        TranslationContext translationContextContextWithScope = this.context.contextWithScope(jsFunction);
        Intrinsics.checkNotNullExpressionValue(translationContextContextWithScope, "contextWithScope(...)");
        JsExpression jsExpressionDefineTemporary = translationContextContextWithScope.defineTemporary(instance(classDescriptor, translationContextContextWithScope));
        Intrinsics.checkNotNullExpressionValue(jsExpressionDefineTemporary, "defineTemporary(...)");
        List<JsStatement> statements = jsFunction.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        List<JsStatement> list = statements;
        List<? extends FunctionDescriptor> list2 = beforeDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(generateTestFunction$buildCall((FunctionDescriptor) it.next(), this, jsExpressionDefineTemporary).makeStmt());
        }
        CollectionsKt.addAll(list, arrayList);
        if (afterDescriptors.isEmpty()) {
            List<JsStatement> statements2 = jsFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            statements2.add(new JsReturn(generateTestFunction$buildCall(functionDescriptor, this, jsExpressionDefineTemporary)));
            return jsFunction;
        }
        KotlinType returnType = functionDescriptor.getReturnType();
        ClassifierDescriptor this$0 = (returnType == null || (constructor = returnType.getConstructor()) == null) ? null : constructor.getThis$0();
        ClassDescriptor classDescriptorFindPromise = findPromise();
        if (Intrinsics.areEqual(this$0, classDescriptorFindPromise)) {
            JsExpression jsExpressionGenerateTestFunction$buildCall = generateTestFunction$buildCall(functionDescriptor, this, jsExpressionDefineTemporary);
            FunctionDescriptor functionByName = DescriptorUtils.getFunctionByName(classDescriptorFindPromise.getUnsubstitutedMemberScope(), Name.identifier(PsiKeyword.FINALLY));
            Intrinsics.checkNotNullExpressionValue(functionByName, "getFunctionByName(...)");
            List<JsStatement> statements3 = jsFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
            List<JsStatement> list3 = statements3;
            CallTranslator callTranslator = CallTranslator.INSTANCE;
            TranslationContext translationContext = this.context;
            List<? extends FunctionDescriptor> list4 = afterDescriptors;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
            Iterator<T> it2 = list4.iterator();
            while (it2.hasNext()) {
                arrayList2.add(generateTestFunction$buildCall((FunctionDescriptor) it2.next(), this, jsExpressionDefineTemporary).makeStmt());
            }
            list3.add(new JsReturn(callTranslator.buildCall(translationContext, functionByName, CollectionsKt.listOf(new JsFunction(scope, new JsBlock(arrayList2), "function for after funs test")), jsExpressionGenerateTestFunction$buildCall)));
            return jsFunction;
        }
        List<JsStatement> statements4 = jsFunction.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
        List<JsStatement> list5 = statements4;
        JsBlock jsBlock = new JsBlock(new JsReturn(generateTestFunction$buildCall(functionDescriptor, this, jsExpressionDefineTemporary)));
        List listEmptyList = CollectionsKt.emptyList();
        List<? extends FunctionDescriptor> list6 = afterDescriptors;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list6, 10));
        Iterator<T> it3 = list6.iterator();
        while (it3.hasNext()) {
            arrayList3.add(generateTestFunction$buildCall((FunctionDescriptor) it3.next(), this, jsExpressionDefineTemporary).makeStmt());
        }
        list5.add(new JsTry(jsBlock, (List<JsCatch>) listEmptyList, new JsBlock(arrayList3)));
        return jsFunction;
    }

    private static final JsExpression generateTestFunction$buildCall(FunctionDescriptor functionDescriptor, JSTestGenerator jSTestGenerator, JsExpression jsExpression) {
        return CallTranslator.INSTANCE.buildCall(jSTestGenerator.context, functionDescriptor, CollectionsKt.emptyList(), jsExpression);
    }

    private final JsExpression instance(ClassDescriptor classDescriptor, TranslationContext translationContext) {
        List listEmptyList;
        if (classDescriptor.getKind() == ClassKind.OBJECT) {
            JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(classDescriptor, translationContext);
            Intrinsics.checkNotNull(jsExpressionTranslateAsValueReference);
            return jsExpressionTranslateAsValueReference;
        }
        if (classDescriptor.getIsInner()) {
            DeclarationDescriptor containingDeclaration = classDescriptor.getContainingModule();
            Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            listEmptyList = CollectionsKt.listOf(instance((ClassDescriptor) containingDeclaration, translationContext));
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        return new JsNew(ReferenceTranslator.translateAsTypeReference(classDescriptor, translationContext), listEmptyList);
    }

    public final ClassDescriptor findPromise() {
        ModuleDescriptor currentModule = this.context.getCurrentModule();
        Intrinsics.checkNotNullExpressionValue(currentModule, "getCurrentModule(...)");
        ClassId classId = ClassId.topLevel(this.promiseFqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(currentModule, classId);
        Intrinsics.checkNotNull(classDescriptorFindClassAcrossModuleDependencies);
        return classDescriptorFindClassAcrossModuleDependencies;
    }

    private final JsExpression getSuiteRef() {
        return (JsExpression) this.suiteRef.getValue();
    }

    private final JsExpression getTestRef() {
        return (JsExpression) this.testRef.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsExpression findFunction(String name2) {
        ModuleDescriptor currentModule = this.context.getCurrentModule();
        FqName safe = new FqNameUnsafe("kotlin.test").toSafe();
        Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
        FunctionDescriptor functionByNameOrNull = DescriptorUtils.getFunctionByNameOrNull(currentModule.getPackage(safe).getMemberScope(), Name.identifier(name2));
        if (functionByNameOrNull == null) {
            return new JsNameRef(name2, new JsNameRef("Kotlin"));
        }
        JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(functionByNameOrNull, this.context);
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
        return jsExpressionTranslateAsValueReference;
    }

    private final boolean isTest(FunctionDescriptor functionDescriptor) {
        return annotationFinder(functionDescriptor, "Test", "kotlin.test");
    }

    private final boolean isIgnored(DeclarationDescriptor declarationDescriptor) {
        return annotationFinder(declarationDescriptor, "Ignore", "kotlin.test");
    }

    private final boolean isBefore(FunctionDescriptor functionDescriptor) {
        return annotationFinder(functionDescriptor, "BeforeTest", "kotlin.test");
    }

    private final boolean isAfter(FunctionDescriptor functionDescriptor) {
        return annotationFinder(functionDescriptor, "AfterTest", "kotlin.test");
    }

    private final boolean annotationFinder(DeclarationDescriptor declarationDescriptor, String str, String... strArr) {
        for (String str2 : strArr) {
            if (declarationDescriptor.getAnnotations().hasAnnotation(new FqName(str2 + '.' + str))) {
                return true;
            }
        }
        return false;
    }
}
