package org.jetbrains.kotlin.js.translate.declaration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.expression.FunctionTranslatorKt;
import org.jetbrains.kotlin.js.translate.initializer.ClassInitializerTranslator;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.FunctionBodyTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DeclarationBodyVisitor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ$\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u000e\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u0014J\"\u0010 \u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020!2\u0006\u0010\"\u001a\u00020\u001b2\b\u0010#\u001a\u0004\u0018\u00010\u001bH\u0016J \u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010'\u001a\u00020(J\u0010\u0010)\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020!H\u0016J\u0018\u0010*\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020+2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010,\u001a\u00020\u00172\u0006\u0010%\u001a\u00020-2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010.\u001a\u00020\u00172\u0006\u0010/\u001a\u0002002\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u00101\u001a\u00020\u00172\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u0005H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\n¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\f¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/DeclarationBodyVisitor;", "Lorg/jetbrains/kotlin/js/translate/declaration/AbstractDeclarationVisitor;", "containingClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "enumInitializer", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)V", "enumEntries", "", "getEnumEntries", "()Ljava/util/List;", "enumEntryOrdinal", "", "enumInitializerName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getEnumInitializerName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "initializerStatements", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "getInitializerStatements", "addFunction", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "expression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "psi", "Lorg/jetbrains/kotlin/psi/KtElement;", "addInitializerStatement", "statement", "addProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getter", "setter", "generateClassOrObject", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "needCompanionInitializer", "", "getBackingFieldReference", "visitAnonymousInitializer", "Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;", "visitClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "visitSecondaryConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "data", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclarationBodyVisitor extends AbstractDeclarationVisitor {
    private final ClassDescriptor containingClass;
    private final TranslationContext context;
    private final List<ClassDescriptor> enumEntries;
    private int enumEntryOrdinal;
    private final JsFunction enumInitializer;
    private final List<JsStatement> initializerStatements;

    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public void visitAnonymousInitializer2(KtAnonymousInitializer expression, TranslationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: renamed from: visitSecondaryConstructor, reason: avoid collision after fix types in other method */
    public void visitSecondaryConstructor2(KtSecondaryConstructor constructor, TranslationContext data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(data, "data");
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public /* bridge */ /* synthetic */ Object visitAnonymousInitializer(KtAnonymousInitializer ktAnonymousInitializer, TranslationContext translationContext) {
        visitAnonymousInitializer2(ktAnonymousInitializer, translationContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor, org.jetbrains.kotlin.psi.KtVisitor
    public /* bridge */ /* synthetic */ Object visitClassOrObject(KtClassOrObject ktClassOrObject, TranslationContext translationContext) {
        visitClassOrObject2(ktClassOrObject, translationContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public /* bridge */ /* synthetic */ Object visitEnumEntry(KtEnumEntry ktEnumEntry, TranslationContext translationContext) {
        visitEnumEntry2(ktEnumEntry, translationContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public /* bridge */ /* synthetic */ Object visitSecondaryConstructor(KtSecondaryConstructor ktSecondaryConstructor, TranslationContext translationContext) {
        visitSecondaryConstructor2(ktSecondaryConstructor, translationContext);
        return Unit.INSTANCE;
    }

    public DeclarationBodyVisitor(ClassDescriptor containingClass, TranslationContext context, JsFunction jsFunction) {
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(context, "context");
        this.containingClass = containingClass;
        this.context = context;
        this.enumInitializer = jsFunction;
        this.initializerStatements = new ArrayList();
        this.enumEntries = new ArrayList();
    }

    public final List<JsStatement> getInitializerStatements() {
        return this.initializerStatements;
    }

    public final List<ClassDescriptor> getEnumEntries() {
        return this.enumEntries;
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor
    public JsName getEnumInitializerName() {
        JsFunction jsFunction = this.enumInitializer;
        if (jsFunction != null) {
            return jsFunction.getName();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor
    /* JADX INFO: renamed from: visitClassOrObject, reason: avoid collision after fix types in other method */
    public void visitClassOrObject2(KtClassOrObject classOrObject, TranslationContext context) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(context, "context");
        super.visitClassOrObject2(classOrObject, context);
        if ((classOrObject instanceof KtObjectDeclaration) && ((KtObjectDeclaration) classOrObject).isCompanion() && this.containingClass.getKind() != ClassKind.ENUM_CLASS) {
            DeclarationDescriptor descriptorForElement = BindingUtils.getDescriptorForElement(context.bindingContext(), classOrObject);
            Intrinsics.checkNotNull(descriptorForElement, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            JsStatement jsStatementMakeStmt = new JsInvocation(context.getNameForObjectInstance((ClassDescriptor) descriptorForElement).makeRef(), new JsExpression[0]).source((Object) classOrObject).makeStmt();
            Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
            addInitializerStatement(jsStatementMakeStmt);
        }
    }

    public static /* synthetic */ void generateClassOrObject$default(DeclarationBodyVisitor declarationBodyVisitor, KtPureClassOrObject ktPureClassOrObject, TranslationContext translationContext, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        declarationBodyVisitor.generateClassOrObject(ktPureClassOrObject, translationContext, z);
    }

    public final void generateClassOrObject(KtPureClassOrObject classOrObject, TranslationContext context, boolean needCompanionInitializer) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(context, "context");
        ClassTranslator.INSTANCE.translate(classOrObject, context);
        ClassDescriptor classDescriptor = BindingUtils.getClassDescriptor(context.bindingContext(), classOrObject);
        Intrinsics.checkNotNullExpressionValue(classDescriptor, "getClassDescriptor(...)");
        context.export(classDescriptor);
        if (needCompanionInitializer) {
            JsStatement jsStatementMakeStmt = new JsInvocation(context.getNameForObjectInstance(classDescriptor).makeRef(), new JsExpression[0]).source((Object) classOrObject).makeStmt();
            Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
            addInitializerStatement(jsStatementMakeStmt);
        }
    }

    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public void visitEnumEntry2(KtEnumEntry enumEntry, TranslationContext context) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(context, "context");
        JsFunction jsFunction = this.enumInitializer;
        Intrinsics.checkNotNull(jsFunction);
        ClassDescriptor classDescriptor = BindingUtils.getClassDescriptor(context.bindingContext(), enumEntry);
        Intrinsics.checkNotNullExpressionValue(classDescriptor, "getClassDescriptor(...)");
        List<KotlinType> supertypesWithoutFakes = JsDescriptorUtils.getSupertypesWithoutFakes(classDescriptor);
        Intrinsics.checkNotNullExpressionValue(supertypesWithoutFakes, "getSupertypesWithoutFakes(...)");
        this.enumEntries.add(classDescriptor);
        if (enumEntry.getBody() != null || supertypesWithoutFakes.size() > 1) {
            ClassTranslator.Companion companion = ClassTranslator.INSTANCE;
            JsName name2 = jsFunction.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            companion.translate(enumEntry, context, name2, this.enumEntryOrdinal);
            List<JsStatement> statements = jsFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(new JsNew(context.getInnerReference(classDescriptor)).source((Object) enumEntry).makeStmt());
        } else {
            ClassDescriptor classDescriptor2 = classDescriptor;
            JsName innerNameForDescriptor = context.getInnerNameForDescriptor(classDescriptor2);
            Intrinsics.checkNotNullExpressionValue(innerNameForDescriptor, "getInnerNameForDescriptor(...)");
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(innerNameForDescriptor.getIdent() + Namer.OBJECT_INSTANCE_VAR_SUFFIX);
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
            supertypesWithoutFakes.size();
            JsExpression jsExpressionGenerateEnumEntryInstanceCreation = ClassInitializerTranslator.generateEnumEntryInstanceCreation(context, enumEntry, this.enumEntryOrdinal);
            Intrinsics.checkNotNullExpressionValue(jsExpressionGenerateEnumEntryInstanceCreation, "generateEnumEntryInstanceCreation(...)");
            context.addDeclarationStatement(JsAstUtils.newVar(jsNameDeclareTemporaryName, null));
            List<JsStatement> statements2 = jsFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            statements2.add(JsAstUtils.assignment(JsAstUtils.pureFqn(jsNameDeclareTemporaryName, (JsExpression) null), jsExpressionGenerateEnumEntryInstanceCreation).source((Object) enumEntry).makeStmt());
            JsFunction jsFunctionCreateRootScopedFunction = context.createRootScopedFunction(classDescriptor2);
            Intrinsics.checkNotNullExpressionValue(jsFunctionCreateRootScopedFunction, "createRootScopedFunction(...)");
            jsFunctionCreateRootScopedFunction.setSource(enumEntry);
            jsFunctionCreateRootScopedFunction.setName(context.getNameForObjectInstance(classDescriptor));
            context.addDeclarationStatement(jsFunctionCreateRootScopedFunction.makeStmt());
            List<JsStatement> statements3 = jsFunctionCreateRootScopedFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
            statements3.add(new JsInvocation(JsAstUtils.pureFqn(jsFunction.getName(), (JsExpression) null), new JsExpression[0]).source((Object) enumEntry).makeStmt());
            List<JsStatement> statements4 = jsFunctionCreateRootScopedFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
            JsReturn jsReturn = new JsReturn(jsNameDeclareTemporaryName.makeRef().source((Object) enumEntry));
            jsReturn.setSource(enumEntry);
            statements4.add(jsReturn);
        }
        context.export(classDescriptor);
        this.enumEntryOrdinal++;
    }

    public final void addInitializerStatement(JsStatement statement) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        this.initializerStatements.add(statement);
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor
    public void addFunction(FunctionDescriptor descriptor, JsExpression expression, KtElement psi) {
        JsReturn jsReturn;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!UtilsKt.hasOrInheritsParametersWithDefaultValue(descriptor) || !ModalityUtilsKt.isOverridableOrOverrides(descriptor)) {
            if (expression != null) {
                TranslationContext translationContext = this.context;
                translationContext.addDeclarationStatement(UtilsKt.addFunctionToPrototype(translationContext, this.containingClass, descriptor, expression));
                return;
            }
            return;
        }
        FunctionDescriptor functionDescriptor = descriptor;
        JsName jsNameDeclareName = this.context.scope().declareName(this.context.getNameForDescriptor(functionDescriptor).getIdent() + "$default");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
        if (expression != null) {
            JsExpression jsExpressionPrototypeOf = JsAstUtils.prototypeOf(this.context.getInnerReference(this.containingClass));
            Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf, "prototypeOf(...)");
            this.context.addDeclarationStatement(JsAstUtils.assignment(new JsNameRef(jsNameDeclareName, jsExpressionPrototypeOf), expression).makeStmt());
        }
        if (UtilsKt.hasOwnParametersWithDefaultValue(descriptor)) {
            JsFunction jsFunction = new JsFunction(this.context.getScopeForDescriptor(this.containingClass), new JsBlock(), "");
            jsFunction.setSource(psi != null ? UtilsKt.getFinalElement(psi) : null);
            TranslationContext translationContextNewDeclaration = this.context.newDeclaration(functionDescriptor);
            Intrinsics.checkNotNullExpressionValue(translationContextNewDeclaration, "newDeclaration(...)");
            List<JsParameter> parameters = jsFunction.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            TranslationContext translationContextInnerBlock = FunctionTranslatorKt.translateAndAliasParameters(translationContextNewDeclaration, descriptor, parameters).innerBlock(jsFunction.getBody());
            Intrinsics.checkNotNullExpressionValue(translationContextInnerBlock, "innerBlock(...)");
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName("callback$default");
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
            JsInvocation jsInvocation = new JsInvocation(new JsNameRef(jsNameDeclareName, new JsThisRef()).source((Object) psi), (List<? extends JsExpression>) CollectionsKt.emptyList());
            jsInvocation.setSource(psi);
            JsInvocation jsInvocation2 = new JsInvocation(jsNameDeclareTemporaryName.makeRef(), new JsExpression[0]);
            jsInvocation2.setSource(psi);
            JsExpression jsExpressionSource = new JsConditional(jsNameDeclareTemporaryName.makeRef(), jsInvocation2, jsInvocation).source((Object) psi);
            List<JsExpression> arguments = jsInvocation.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            List<JsExpression> list = arguments;
            List<JsParameter> parameters2 = jsFunction.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
            List<JsParameter> list2 = parameters2;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(((JsParameter) it.next()).getName().makeRef());
            }
            CollectionsKt.addAll(list, arrayList);
            List<JsExpression> arguments2 = jsInvocation2.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
            List<JsExpression> list3 = arguments2;
            List<JsExpression> arguments3 = jsInvocation.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments3, "getArguments(...)");
            List<JsExpression> list4 = arguments3;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
            Iterator<T> it2 = list4.iterator();
            while (it2.hasNext()) {
                arrayList2.add(((JsExpression) it2.next()).deepCopy());
            }
            CollectionsKt.addAll(list3, arrayList2);
            jsFunction.getParameters().add(new JsParameter(jsNameDeclareTemporaryName));
            List<JsStatement> statements = jsFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            List<JsStatement> defaultValueForArguments = FunctionBodyTranslator.setDefaultValueForArguments(descriptor, translationContextInnerBlock);
            Intrinsics.checkNotNullExpressionValue(defaultValueForArguments, "setDefaultValueForArguments(...)");
            CollectionsKt.addAll(statements, defaultValueForArguments);
            KotlinType returnType = descriptor.getReturnType();
            Intrinsics.checkNotNull(returnType);
            if (KotlinBuiltIns.isUnit(returnType) && !descriptor.isSuspend()) {
                jsReturn = jsExpressionSource.makeStmt();
            } else {
                jsReturn = new JsReturn(jsExpressionSource);
            }
            Intrinsics.checkNotNull(jsReturn);
            List<JsStatement> statements2 = jsFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            statements2.add(jsReturn);
            TranslationContext translationContext2 = this.context;
            translationContext2.addDeclarationStatement(UtilsKt.addFunctionToPrototype(translationContext2, this.containingClass, descriptor, jsFunction));
        }
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor
    public void addProperty(PropertyDescriptor descriptor, JsExpression getter, JsExpression setter) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(getter, "getter");
        if (JsDescriptorUtils.isSimpleFinalProperty(descriptor)) {
            return;
        }
        JsObjectLiteral jsObjectLiteral = new JsObjectLiteral(true);
        List<JsPropertyInitializer> propertyInitializers = jsObjectLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
        propertyInitializers.add(new JsPropertyInitializer(new JsStringLiteral("configurable"), new JsBooleanLiteral(true)));
        List<JsPropertyInitializer> propertyInitializers2 = jsObjectLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers2, "getPropertyInitializers(...)");
        propertyInitializers2.add(new JsPropertyInitializer(new JsStringLiteral("get"), getter));
        if (setter != null) {
            List<JsPropertyInitializer> propertyInitializers3 = jsObjectLiteral.getPropertyInitializers();
            Intrinsics.checkNotNullExpressionValue(propertyInitializers3, "getPropertyInitializers(...)");
            propertyInitializers3.add(new JsPropertyInitializer(new JsStringLiteral(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX), setter));
        }
        UtilsKt.addAccessorsToPrototype(this.context, this.containingClass, descriptor, jsObjectLiteral);
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor
    public JsExpression getBackingFieldReference(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new JsNameRef(this.context.getNameForBackingField(descriptor), new JsThisRef());
    }
}
