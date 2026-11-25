package org.jetbrains.kotlin.js.translate.declaration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DelegationResolver;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: DelegationTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001$B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013J(\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J \u0010\u001d\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0006\u0010\u001f\u001a\u00020\u0011J \u0010 \u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\u000fH\u0002J\u0012\u0010#\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\t\u001a\r\u0012\t\u0012\u00070\u000b¢\u0006\u0002\b\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/DelegationTranslator;", "Lorg/jetbrains/kotlin/js/translate/general/AbstractTranslator;", "classDeclaration", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "delegationBySpecifiers", "", "Lorg/jetbrains/kotlin/psi/KtDelegatedSuperTypeEntry;", "Lkotlin/internal/NoInfer;", "fields", "", "Lorg/jetbrains/kotlin/js/translate/declaration/DelegationTranslator$Field;", "addInitCode", "", "statements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "generateDelegateCallForFunctionMember", "specifier", "Lorg/jetbrains/kotlin/psi/KtSuperTypeListEntry;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "overriddenDescriptor", "delegateName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "generateDelegateCallForPropertyMember", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "generateDelegated", "generateDelegates", "toClass", "field", "getSuperClass", "Field", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegationTranslator extends AbstractTranslator {
    private final ClassDescriptor classDescriptor;
    private final List<KtDelegatedSuperTypeEntry> delegationBySpecifiers;
    private final Map<KtDelegatedSuperTypeEntry, Field> fields;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DelegationTranslator(KtPureClassOrObject classDeclaration, TranslationContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(classDeclaration, "classDeclaration");
        Intrinsics.checkNotNullParameter(context, "context");
        ClassDescriptor classDescriptor = BindingUtils.getClassDescriptor(context.bindingContext(), classDeclaration);
        Intrinsics.checkNotNullExpressionValue(classDescriptor, "getClassDescriptor(...)");
        this.classDescriptor = classDescriptor;
        List<KtSuperTypeListEntry> superTypeListEntries = classDeclaration.getSuperTypeListEntries();
        Intrinsics.checkNotNullExpressionValue(superTypeListEntries, "getSuperTypeListEntries(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : superTypeListEntries) {
            if (obj instanceof KtDelegatedSuperTypeEntry) {
                arrayList.add(obj);
            }
        }
        ArrayList<KtDelegatedSuperTypeEntry> arrayList2 = arrayList;
        this.delegationBySpecifiers = arrayList2;
        this.fields = new LinkedHashMap();
        for (KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry : arrayList2) {
            KtExpression delegateExpression = ktDelegatedSuperTypeEntry.getDelegateExpression();
            if (delegateExpression == null) {
                throw new IllegalArgumentException("delegate expression should not be null: " + ktDelegatedSuperTypeEntry.getText());
            }
            ClassDescriptor classDescriptor2 = this.classDescriptor;
            BindingContext bindingContext = bindingContext();
            Intrinsics.checkNotNullExpressionValue(bindingContext, "bindingContext(...)");
            PropertyDescriptor delegatePropertyIfAny = CodegenUtil.getDelegatePropertyIfAny(delegateExpression, classDescriptor2, bindingContext);
            BindingContext bindingContext2 = bindingContext();
            Intrinsics.checkNotNullExpressionValue(bindingContext2, "bindingContext(...)");
            if (CodegenUtil.isFinalPropertyWithBackingField(delegatePropertyIfAny, bindingContext2)) {
                Intrinsics.checkNotNull(delegatePropertyIfAny);
                JsName nameForDescriptor = context.getNameForDescriptor(delegatePropertyIfAny);
                Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
                this.fields.put(ktDelegatedSuperTypeEntry, new Field(nameForDescriptor, false));
            } else {
                FqNameUnsafe fqName = DescriptorUtils.getFqName(this.classDescriptor);
                Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
                String strAsString = fqName.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                NameSuggestion.Companion companion = NameSuggestion.INSTANCE;
                String delegatePrefix = Namer.getDelegatePrefix();
                Intrinsics.checkNotNullExpressionValue(delegatePrefix, "getDelegatePrefix(...)");
                JsName jsNameDeclareFreshName = context.getScopeForDescriptor(this.classDescriptor).declareFreshName(companion.getStableMangledName(delegatePrefix, strAsString) + "_0");
                Intrinsics.checkNotNullExpressionValue(jsNameDeclareFreshName, "declareFreshName(...)");
                this.fields.put(ktDelegatedSuperTypeEntry, new Field(jsNameDeclareFreshName, true));
            }
        }
    }

    /* JADX INFO: compiled from: DelegationTranslator.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/DelegationTranslator$Field;", "", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "generateField", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Z)V", "getGenerateField", "()Z", "getName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Field {
        private final boolean generateField;
        private final JsName name;

        public Field(JsName name2, boolean z) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
            this.generateField = z;
        }

        public final boolean getGenerateField() {
            return this.generateField;
        }

        public final JsName getName() {
            return this.name;
        }
    }

    public final void addInitCode(List<JsStatement> statements) {
        Intrinsics.checkNotNullParameter(statements, "statements");
        for (KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry : this.delegationBySpecifiers) {
            Field field = this.fields.get(ktDelegatedSuperTypeEntry);
            Intrinsics.checkNotNull(field);
            Field field2 = field;
            if (field2.getGenerateField()) {
                KtExpression delegateExpression = ktDelegatedSuperTypeEntry.getDelegateExpression();
                Intrinsics.checkNotNull(delegateExpression);
                TranslationContext translationContextInnerBlock = context().innerBlock();
                Intrinsics.checkNotNullExpressionValue(translationContextInnerBlock, "innerBlock(...)");
                JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(delegateExpression, translationContextInnerBlock);
                Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsExpression, "translateAsExpression(...)");
                List<JsStatement> list = statements;
                List<JsStatement> statements2 = translationContextInnerBlock.dynamicContext().jsBlock().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                CollectionsKt.addAll(list, statements2);
                JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(field2.getName(), new JsThisRef());
                Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "pureFqn(...)");
                JsBinaryOperation jsBinaryOperationAssignment = JsAstUtils.assignment(jsNameRefPureFqn, jsExpressionTranslateAsExpression);
                jsBinaryOperationAssignment.setSource(ktDelegatedSuperTypeEntry);
                list.add(jsBinaryOperationAssignment.makeStmt());
            }
        }
    }

    public final void generateDelegated() {
        for (KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry : this.delegationBySpecifiers) {
            KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry2 = ktDelegatedSuperTypeEntry;
            ClassDescriptor superClass = getSuperClass(ktDelegatedSuperTypeEntry2);
            if (superClass != null) {
                Field field = this.fields.get(ktDelegatedSuperTypeEntry);
                Intrinsics.checkNotNull(field);
                generateDelegates(ktDelegatedSuperTypeEntry2, superClass, field);
            }
        }
    }

    private final ClassDescriptor getSuperClass(KtSuperTypeListEntry specifier) {
        BindingContext bindingContext = bindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "bindingContext(...)");
        ClassDescriptor superClassBySuperTypeListEntry = CodegenUtil.getSuperClassBySuperTypeListEntry(specifier, bindingContext);
        if (superClassBySuperTypeListEntry != null) {
            return superClassBySuperTypeListEntry;
        }
        throw new IllegalStateException(("ClassDescriptor of superType should not be null: " + specifier.getText()).toString());
    }

    private final void generateDelegates(KtSuperTypeListEntry specifier, ClassDescriptor toClass, Field field) {
        for (Map.Entry entry : DelegationResolver.Companion.getDelegates$default(DelegationResolver.INSTANCE, this.classDescriptor, toClass, null, 4, null).entrySet()) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) entry.getKey();
            CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) entry.getValue();
            if (callableMemberDescriptor instanceof PropertyDescriptor) {
                generateDelegateCallForPropertyMember(specifier, (PropertyDescriptor) callableMemberDescriptor, field.getName());
            } else if (callableMemberDescriptor instanceof FunctionDescriptor) {
                Intrinsics.checkNotNull(callableMemberDescriptor2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
                generateDelegateCallForFunctionMember(specifier, (FunctionDescriptor) callableMemberDescriptor, (FunctionDescriptor) callableMemberDescriptor2, field.getName());
            } else {
                throw new IllegalArgumentException("Expected property or function " + callableMemberDescriptor);
            }
        }
    }

    private final void generateDelegateCallForPropertyMember(KtSuperTypeListEntry specifier, PropertyDescriptor descriptor, JsName delegateName) {
        String strAsString = descriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (DescriptorUtilsKt.isExtensionProperty(descriptor) || TranslationUtils.shouldAccessViaFunctions((CallableDescriptor) descriptor)) {
            PropertyGetterDescriptor getter = descriptor.getGetter();
            Intrinsics.checkNotNull(getter);
            TranslationContext translationContextContext = context();
            TranslationContext translationContextContext2 = context();
            Intrinsics.checkNotNullExpressionValue(translationContextContext2, "context(...)");
            translationContextContext.addDeclarationStatement(UtilsKt.addFunctionToPrototype(translationContextContext2, this.classDescriptor, getter, generateDelegateCallForPropertyMember$generateDelegateGetterFunction(delegateName, descriptor, this, strAsString, specifier, getter)));
            if (descriptor.isVar()) {
                PropertySetterDescriptor setter = descriptor.getSetter();
                Intrinsics.checkNotNull(setter);
                TranslationContext translationContextContext3 = context();
                TranslationContext translationContextContext4 = context();
                Intrinsics.checkNotNullExpressionValue(translationContextContext4, "context(...)");
                translationContextContext3.addDeclarationStatement(UtilsKt.addFunctionToPrototype(translationContextContext4, this.classDescriptor, setter, generateDelegateCallForPropertyMember$generateDelegateSetterFunction(this, specifier, delegateName, descriptor, strAsString, setter)));
                return;
            }
            return;
        }
        JsObjectLiteral jsObjectLiteral = new JsObjectLiteral(true);
        List<JsPropertyInitializer> propertyInitializers = jsObjectLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
        propertyInitializers.add(new JsPropertyInitializer(new JsStringLiteral("configurable"), new JsBooleanLiteral(true)));
        List<JsPropertyInitializer> propertyInitializers2 = jsObjectLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers2, "getPropertyInitializers(...)");
        PropertyTranslatorKt.addGetterAndSetter(propertyInitializers2, descriptor, new AnonymousClass1(descriptor, delegateName, this, strAsString, specifier), new AnonymousClass2(descriptor, this, specifier, delegateName, strAsString));
        TranslationContext translationContextContext5 = context();
        Intrinsics.checkNotNullExpressionValue(translationContextContext5, "context(...)");
        UtilsKt.addAccessorsToPrototype(translationContextContext5, this.classDescriptor, descriptor, jsObjectLiteral);
    }

    private static final JsFunction generateDelegateCallForPropertyMember$generateDelegateGetterFunction(JsName jsName, PropertyDescriptor propertyDescriptor, DelegationTranslator delegationTranslator, String str, KtSuperTypeListEntry ktSuperTypeListEntry, PropertyGetterDescriptor propertyGetterDescriptor) {
        JsNameRef jsNameRef;
        JsNameRef jsNameRef2 = new JsNameRef(jsName, new JsThisRef());
        PropertyDescriptor propertyDescriptor2 = propertyDescriptor;
        if (DescriptorUtils.isExtension(propertyDescriptor2)) {
            JsName nameForDescriptor = delegationTranslator.context().getNameForDescriptor(propertyGetterDescriptor);
            Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
            String receiverParameterName = Namer.getReceiverParameterName();
            Intrinsics.checkNotNullExpressionValue(receiverParameterName, "getReceiverParameterName(...)");
            jsNameRef = new JsInvocation(new JsNameRef(nameForDescriptor, jsNameRef2), new JsNameRef(receiverParameterName));
        } else {
            jsNameRef = new JsNameRef(str, jsNameRef2);
        }
        jsNameRef.source((Object) ktSuperTypeListEntry);
        JsFunction jsFunctionSimpleReturnFunction = TranslationUtils.simpleReturnFunction(delegationTranslator.context().getScopeForDescriptor(propertyGetterDescriptor.getContainingModule()), jsNameRef);
        Intrinsics.checkNotNullExpressionValue(jsFunctionSimpleReturnFunction, "simpleReturnFunction(...)");
        jsFunctionSimpleReturnFunction.setSource(ktSuperTypeListEntry);
        if (DescriptorUtils.isExtension(propertyDescriptor2)) {
            JsName jsNameDeclareName = jsFunctionSimpleReturnFunction.getScope().declareName(Namer.getReceiverParameterName());
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
            jsFunctionSimpleReturnFunction.getParameters().add(new JsParameter(jsNameDeclareName));
        }
        return jsFunctionSimpleReturnFunction;
    }

    private static final JsFunction generateDelegateCallForPropertyMember$generateDelegateSetterFunction(DelegationTranslator delegationTranslator, KtSuperTypeListEntry ktSuperTypeListEntry, JsName jsName, PropertyDescriptor propertyDescriptor, String str, PropertySetterDescriptor propertySetterDescriptor) {
        JsBinaryOperation jsInvocation;
        JsFunction jsFunction = new JsFunction(delegationTranslator.context().program().getRootScope(), "setter for " + propertySetterDescriptor.getShortName().asString());
        jsFunction.setSource(ktSuperTypeListEntry);
        propertySetterDescriptor.getValueParameters().size();
        JsParameter jsParameter = new JsParameter(JsScope.declareTemporary());
        JsNameRef jsNameRefMakeRef = jsParameter.getName().makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        JsNameRef jsNameRef = new JsNameRef(jsName, new JsThisRef());
        if (DescriptorUtils.isExtension(propertyDescriptor)) {
            JsName nameForDescriptor = delegationTranslator.context().getNameForDescriptor(propertySetterDescriptor);
            Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
            JsNameRef jsNameRef2 = new JsNameRef(nameForDescriptor, jsNameRef);
            JsName jsNameDeclareName = jsFunction.getScope().declareName(Namer.getReceiverParameterName());
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
            jsFunction.getParameters().add(new JsParameter(jsNameDeclareName));
            jsInvocation = new JsInvocation(jsNameRef2, new JsNameRef(jsNameDeclareName), jsNameRefMakeRef);
        } else {
            JsBinaryOperation jsBinaryOperationAssignment = JsAstUtils.assignment(new JsNameRef(str, jsNameRef), jsNameRefMakeRef);
            Intrinsics.checkNotNull(jsBinaryOperationAssignment);
            jsInvocation = jsBinaryOperationAssignment;
        }
        jsFunction.getParameters().add(jsParameter);
        jsInvocation.setSource(ktSuperTypeListEntry);
        jsFunction.setBody(new JsBlock(jsInvocation.makeStmt()));
        return jsFunction;
    }

    private static final JsPropertyInitializer generateDelegateCallForPropertyMember$generateDelegateAccessor(DelegationTranslator delegationTranslator, PropertyAccessorDescriptor propertyAccessorDescriptor, JsFunction jsFunction) {
        JsPropertyInitializer jsPropertyInitializerTranslateFunctionAsEcma5PropertyDescriptor = TranslationUtils.translateFunctionAsEcma5PropertyDescriptor(jsFunction, propertyAccessorDescriptor, delegationTranslator.context());
        Intrinsics.checkNotNullExpressionValue(jsPropertyInitializerTranslateFunctionAsEcma5PropertyDescriptor, "translateFunctionAsEcma5PropertyDescriptor(...)");
        return jsPropertyInitializerTranslateFunctionAsEcma5PropertyDescriptor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsPropertyInitializer generateDelegateCallForPropertyMember$generateDelegateGetter(PropertyDescriptor propertyDescriptor, JsName jsName, DelegationTranslator delegationTranslator, String str, KtSuperTypeListEntry ktSuperTypeListEntry) {
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        if (getter == null) {
            throw new IllegalStateException("Getter descriptor should not be null");
        }
        return generateDelegateCallForPropertyMember$generateDelegateAccessor(delegationTranslator, getter, generateDelegateCallForPropertyMember$generateDelegateGetterFunction(jsName, propertyDescriptor, delegationTranslator, str, ktSuperTypeListEntry, getter));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsPropertyInitializer generateDelegateCallForPropertyMember$generateDelegateSetter(PropertyDescriptor propertyDescriptor, DelegationTranslator delegationTranslator, KtSuperTypeListEntry ktSuperTypeListEntry, JsName jsName, String str) {
        PropertySetterDescriptor setter = propertyDescriptor.getSetter();
        if (setter == null) {
            throw new IllegalStateException("Setter descriptor should not be null");
        }
        return generateDelegateCallForPropertyMember$generateDelegateAccessor(delegationTranslator, setter, generateDelegateCallForPropertyMember$generateDelegateSetterFunction(delegationTranslator, ktSuperTypeListEntry, jsName, propertyDescriptor, str, setter));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.js.translate.declaration.DelegationTranslator$generateDelegateCallForPropertyMember$1, reason: invalid class name */
    /* JADX INFO: compiled from: DelegationTranslator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function0<JsPropertyInitializer> {
        final /* synthetic */ JsName $delegateName;
        final /* synthetic */ PropertyDescriptor $descriptor;
        final /* synthetic */ String $propertyName;
        final /* synthetic */ KtSuperTypeListEntry $specifier;
        final /* synthetic */ DelegationTranslator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(PropertyDescriptor propertyDescriptor, JsName jsName, DelegationTranslator delegationTranslator, String str, KtSuperTypeListEntry ktSuperTypeListEntry) {
            super(0);
            this.$descriptor = propertyDescriptor;
            this.$delegateName = jsName;
            this.this$0 = delegationTranslator;
            this.$propertyName = str;
            this.$specifier = ktSuperTypeListEntry;
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "generateDelegateCallForPropertyMember$generateDelegateGetter(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/translate/declaration/DelegationTranslator;Ljava/lang/String;Lorg/jetbrains/kotlin/psi/KtSuperTypeListEntry;)Lorg/jetbrains/kotlin/js/backend/ast/JsPropertyInitializer;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "generateDelegateGetter";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function0
        public final JsPropertyInitializer invoke() {
            return DelegationTranslator.generateDelegateCallForPropertyMember$generateDelegateGetter(this.$descriptor, this.$delegateName, this.this$0, this.$propertyName, this.$specifier);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.js.translate.declaration.DelegationTranslator$generateDelegateCallForPropertyMember$2, reason: invalid class name */
    /* JADX INFO: compiled from: DelegationTranslator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function0<JsPropertyInitializer> {
        final /* synthetic */ JsName $delegateName;
        final /* synthetic */ PropertyDescriptor $descriptor;
        final /* synthetic */ String $propertyName;
        final /* synthetic */ KtSuperTypeListEntry $specifier;
        final /* synthetic */ DelegationTranslator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(PropertyDescriptor propertyDescriptor, DelegationTranslator delegationTranslator, KtSuperTypeListEntry ktSuperTypeListEntry, JsName jsName, String str) {
            super(0);
            this.$descriptor = propertyDescriptor;
            this.this$0 = delegationTranslator;
            this.$specifier = ktSuperTypeListEntry;
            this.$delegateName = jsName;
            this.$propertyName = str;
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "generateDelegateCallForPropertyMember$generateDelegateSetter(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/js/translate/declaration/DelegationTranslator;Lorg/jetbrains/kotlin/psi/KtSuperTypeListEntry;Lorg/jetbrains/kotlin/js/backend/ast/JsName;Ljava/lang/String;)Lorg/jetbrains/kotlin/js/backend/ast/JsPropertyInitializer;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "generateDelegateSetter";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function0
        public final JsPropertyInitializer invoke() {
            return DelegationTranslator.generateDelegateCallForPropertyMember$generateDelegateSetter(this.$descriptor, this.this$0, this.$specifier, this.$delegateName, this.$propertyName);
        }
    }

    private final void generateDelegateCallForFunctionMember(KtSuperTypeListEntry specifier, FunctionDescriptor descriptor, FunctionDescriptor overriddenDescriptor, JsName delegateName) {
        JsNameRef jsNameRef = new JsNameRef(delegateName, new JsThisRef());
        TranslationContext translationContextNewDeclaration = context().newDeclaration(overriddenDescriptor);
        Intrinsics.checkNotNullExpressionValue(translationContextNewDeclaration, "newDeclaration(...)");
        context().addDeclarationStatement(UtilsKt.generateDelegateCall(this.classDescriptor, descriptor, overriddenDescriptor, jsNameRef, translationContextNewDeclaration, true, specifier));
    }
}
