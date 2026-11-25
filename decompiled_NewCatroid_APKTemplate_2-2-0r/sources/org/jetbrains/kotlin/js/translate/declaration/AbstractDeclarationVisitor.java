package org.jetbrains.kotlin.js.translate.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.expression.FunctionTranslatorKt;
import org.jetbrains.kotlin.js.translate.general.TranslatorVisitor;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.FunctionBodyTranslator;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: AbstractDeclarationVisitor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J$\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH&J\"\u0010\u000f\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\f2\b\u0010\u0012\u001a\u0004\u0018\u00010\fH&J\u0010\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\u0010\u0010\u0016\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0010H&J,\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00150\u00182\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00192\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u001e2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020 2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u001a\u0010!\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0015H\u0016R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/AbstractDeclarationVisitor;", "Lorg/jetbrains/kotlin/js/translate/general/TranslatorVisitor;", "", "()V", "enumInitializerName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getEnumInitializerName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "addFunction", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "expression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "psi", "Lorg/jetbrains/kotlin/psi/KtElement;", "addProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getter", "setter", "emptyResult", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "getBackingFieldReference", "translateFunction", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/psi/KtDeclarationWithBody;", "visitClassOrObject", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "visitNamedFunction", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "visitProperty", "Lorg/jetbrains/kotlin/psi/KtProperty;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "data", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractDeclarationVisitor extends TranslatorVisitor<Unit> {
    public abstract void addFunction(FunctionDescriptor descriptor, JsExpression expression, KtElement psi);

    public abstract void addProperty(PropertyDescriptor descriptor, JsExpression getter, JsExpression setter);

    /* JADX INFO: renamed from: emptyResult, reason: avoid collision after fix types in other method */
    protected void emptyResult2(TranslationContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public abstract JsExpression getBackingFieldReference(PropertyDescriptor descriptor);

    public JsName getEnumInitializerName() {
        return null;
    }

    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public void visitTypeAlias2(KtTypeAlias typeAlias, TranslationContext data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
    }

    @Override // org.jetbrains.kotlin.js.translate.general.TranslatorVisitor
    public /* bridge */ /* synthetic */ Unit emptyResult(TranslationContext translationContext) {
        emptyResult2(translationContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public /* bridge */ /* synthetic */ Object visitClassOrObject(KtClassOrObject ktClassOrObject, TranslationContext translationContext) {
        visitClassOrObject2(ktClassOrObject, translationContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public /* bridge */ /* synthetic */ Object visitNamedFunction(KtNamedFunction ktNamedFunction, TranslationContext translationContext) {
        visitNamedFunction2(ktNamedFunction, translationContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public /* bridge */ /* synthetic */ Object visitProperty(KtProperty ktProperty, TranslationContext translationContext) {
        visitProperty2(ktProperty, translationContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public /* bridge */ /* synthetic */ Object visitTypeAlias(KtTypeAlias ktTypeAlias, TranslationContext translationContext) {
        visitTypeAlias2(ktTypeAlias, translationContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitClassOrObject, reason: avoid collision after fix types in other method */
    public void visitClassOrObject2(KtClassOrObject classOrObject, TranslationContext context) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(context, "context");
        ClassTranslator.INSTANCE.translate(classOrObject, context, getEnumInitializerName());
        ClassDescriptor classDescriptor = BindingUtils.getClassDescriptor(context.bindingContext(), classOrObject);
        Intrinsics.checkNotNullExpressionValue(classDescriptor, "getClassDescriptor(...)");
        context.export(classDescriptor);
    }

    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public void visitProperty2(KtProperty expression, TranslationContext context) {
        JsFunction first;
        JsFunction first2;
        KtPropertyAccessor ktPropertyAccessor;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        PropertyDescriptor propertyDescriptor = BindingUtils.getPropertyDescriptor(context.bindingContext(), expression);
        Intrinsics.checkNotNullExpressionValue(propertyDescriptor, "getPropertyDescriptor(...)");
        if (propertyDescriptor.getModality() == Modality.ABSTRACT) {
            return;
        }
        PropertyDescriptor propertyDescriptor2 = propertyDescriptor;
        TranslationContext translationContextNewDeclaration = context.newDeclaration(propertyDescriptor2);
        Intrinsics.checkNotNullExpressionValue(translationContextNewDeclaration, "newDeclaration(...)");
        DefaultPropertyTranslator defaultPropertyTranslator = new DefaultPropertyTranslator(propertyDescriptor, context, getBackingFieldReference(propertyDescriptor));
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        Intrinsics.checkNotNull(getter);
        if (PropertyTranslatorKt.hasCustomGetter(expression)) {
            KtPropertyAccessor getter2 = expression.getGetter();
            Intrinsics.checkNotNull(getter2);
            first = translateFunction(getter, getter2, translationContextNewDeclaration).getFirst();
        } else {
            JsFunction functionObject = context.getFunctionObject(getter);
            Intrinsics.checkNotNullExpressionValue(functionObject, "getFunctionObject(...)");
            functionObject.setSource(expression);
            defaultPropertyTranslator.generateDefaultGetterFunction(getter, functionObject);
            first = functionObject;
        }
        if (propertyDescriptor.isVar()) {
            PropertySetterDescriptor setter = propertyDescriptor.getSetter();
            Intrinsics.checkNotNull(setter);
            if (PropertyTranslatorKt.hasCustomSetter(expression)) {
                KtPropertyAccessor setter2 = expression.getSetter();
                Intrinsics.checkNotNull(setter2);
                first2 = translateFunction(setter, setter2, translationContextNewDeclaration).getFirst();
            } else {
                JsFunction functionObject2 = context.getFunctionObject(setter);
                Intrinsics.checkNotNullExpressionValue(functionObject2, "getFunctionObject(...)");
                functionObject2.setSource(expression);
                defaultPropertyTranslator.generateDefaultSetterFunction(setter, functionObject2);
                first2 = functionObject2;
            }
        } else {
            first2 = null;
        }
        if (TranslationUtils.shouldAccessViaFunctions((CallableDescriptor) propertyDescriptor) || DescriptorUtilsKt.isExtensionProperty(propertyDescriptor2)) {
            PropertyGetterDescriptor getter3 = propertyDescriptor.getGetter();
            Intrinsics.checkNotNull(getter3);
            PropertyGetterDescriptor propertyGetterDescriptor = getter3;
            KtPropertyAccessor getter4 = expression.getGetter();
            addFunction(propertyGetterDescriptor, first, getter4 == null ? expression : getter4);
            PropertySetterDescriptor setter3 = propertyDescriptor.getSetter();
            if (setter3 != null) {
                PropertySetterDescriptor propertySetterDescriptor = setter3;
                Intrinsics.checkNotNull(first2);
                KtPropertyAccessor setter4 = expression.getSetter();
                if (setter4 == null) {
                    ktPropertyAccessor = expression;
                } else {
                    Intrinsics.checkNotNull(setter4);
                    ktPropertyAccessor = setter4;
                }
                addFunction(propertySetterDescriptor, first2, ktPropertyAccessor);
                return;
            }
            return;
        }
        addProperty(propertyDescriptor, first, first2);
    }

    /* JADX INFO: renamed from: visitNamedFunction, reason: avoid collision after fix types in other method */
    public void visitNamedFunction2(KtNamedFunction expression, TranslationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        KtNamedFunction ktNamedFunction = expression;
        FunctionDescriptor functionDescriptor = BindingUtils.getFunctionDescriptor(context.bindingContext(), ktNamedFunction);
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "getFunctionDescriptor(...)");
        Pair<JsExpression, TranslationContext> pairTranslateFunction = functionDescriptor.getModality() != Modality.ABSTRACT ? translateFunction(functionDescriptor, ktNamedFunction, context) : null;
        addFunction(functionDescriptor, pairTranslateFunction != null ? pairTranslateFunction.getFirst() : null, expression);
    }

    private final Pair<JsExpression, TranslationContext> translateFunction(FunctionDescriptor descriptor, KtDeclarationWithBody expression, TranslationContext context) {
        JsFunction functionObject = context.getFunctionObject(descriptor);
        Intrinsics.checkNotNullExpressionValue(functionObject, "getFunctionObject(...)");
        functionObject.setSource(expression);
        JsBlock body = functionObject.getBody();
        PsiElement finalElement = UtilsKt.getFinalElement(expression);
        body.setSource(finalElement instanceof LeafPsiElement ? (LeafPsiElement) finalElement : null);
        TranslationContext translationContextNewDeclaration = context.newDeclaration(descriptor);
        Intrinsics.checkNotNullExpressionValue(translationContextNewDeclaration, "newDeclaration(...)");
        List<JsParameter> parameters = functionObject.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        TranslationContext translationContextTranslateAndAliasParameters = FunctionTranslatorKt.translateAndAliasParameters(translationContextNewDeclaration, descriptor, parameters);
        if (descriptor.isSuspend()) {
            UtilsKt.fillCoroutineMetadata(functionObject, translationContextTranslateAndAliasParameters, descriptor, false);
        }
        if (!ModalityUtilsKt.isOverridable(descriptor)) {
            List<JsStatement> statements = functionObject.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            List<JsStatement> defaultValueForArguments = FunctionBodyTranslator.setDefaultValueForArguments(descriptor, translationContextTranslateAndAliasParameters);
            Intrinsics.checkNotNullExpressionValue(defaultValueForArguments, "setDefaultValueForArguments(...)");
            CollectionsKt.addAll(statements, defaultValueForArguments);
        }
        FunctionTranslatorKt.translateFunction(translationContextTranslateAndAliasParameters, expression, functionObject);
        return new Pair<>(FunctionTranslatorKt.wrapWithInlineMetadata(translationContextTranslateAndAliasParameters, context, functionObject, descriptor), translationContextTranslateAndAliasParameters);
    }
}
