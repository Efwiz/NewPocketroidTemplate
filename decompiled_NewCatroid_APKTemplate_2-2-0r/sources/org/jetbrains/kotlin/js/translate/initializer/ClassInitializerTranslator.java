package org.jetbrains.kotlin.js.translate.initializer;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.context.UsageTracker;
import org.jetbrains.kotlin.js.translate.declaration.DelegationTranslator;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.FunctionBodyTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: loaded from: classes5.dex */
public final class ClassInitializerTranslator extends AbstractTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KtPureClassOrObject classDeclaration;
    private final ClassDescriptor classDescriptor;
    private final TranslationContext context;
    private final JsFunction initFunction;
    private int ordinal;
    private final ConstructorDescriptor primaryConstructor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 6 || i == 11 || i == 17 || i == 19) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 6 || i == 11 || i == 17 || i == 19) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 7:
                objArr[0] = "context";
                break;
            case 2:
                objArr[0] = "initFunction";
                break;
            case 3:
            case 6:
            case 11:
            case 17:
            case 19:
                objArr[0] = "org/jetbrains/kotlin/js/translate/initializer/ClassInitializerTranslator";
                break;
            case 5:
                objArr[0] = "enumEntry";
                break;
            case 8:
                objArr[0] = "classDescriptor";
                break;
            case 9:
                objArr[0] = "superCall";
                break;
            case 10:
                objArr[0] = "receiver";
                break;
            case 12:
            case 15:
                objArr[0] = "arguments";
                break;
            case 13:
                objArr[0] = "initializer";
                break;
            case 14:
                objArr[0] = "psi";
                break;
            case 16:
                objArr[0] = "descriptor";
                break;
            case 18:
                objArr[0] = "ktParameter";
                break;
            case 20:
                objArr[0] = "jsParameter";
                break;
            case 21:
                objArr[0] = "jetParameter";
                break;
            case 22:
                objArr[0] = "initialValue";
                break;
            case 23:
                objArr[0] = "propertyDescriptor";
                break;
            default:
                objArr[0] = "classDeclaration";
                break;
        }
        if (i == 3) {
            objArr[1] = "context";
        } else if (i == 6) {
            objArr[1] = "generateEnumEntryInstanceCreation";
        } else if (i == 11) {
            objArr[1] = "getAdditionalArgumentsForEnumConstructor";
        } else if (i == 17) {
            objArr[1] = "translatePrimaryConstructorParameters";
        } else if (i != 19) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/initializer/ClassInitializerTranslator";
        } else {
            objArr[1] = "translateParameter";
        }
        switch (i) {
            case 3:
            case 6:
            case 11:
            case 17:
            case 19:
                break;
            case 4:
            case 5:
                objArr[2] = "generateEnumEntryInstanceCreation";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "emulateSuperCallToNativeError";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "addCallToSuperMethod";
                break;
            case 15:
            case 16:
                objArr[2] = "addCallToSuperSecondaryConstructor";
                break;
            case 18:
                objArr[2] = "translateParameter";
                break;
            case 20:
            case 21:
                objArr[2] = "mayBeAddInitializerStatementForProperty";
                break;
            case 22:
            case 23:
                objArr[2] = "addInitializerOrPropertyDefinition";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 6 && i != 11 && i != 17 && i != 19) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassInitializerTranslator(KtPureClassOrObject ktPureClassOrObject, TranslationContext translationContext, JsFunction jsFunction) {
        super(translationContext);
        if (ktPureClassOrObject == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        if (jsFunction == null) {
            $$$reportNull$$$0(2);
        }
        this.classDeclaration = ktPureClassOrObject;
        this.initFunction = jsFunction;
        this.context = translationContext.contextWithScope(jsFunction);
        ClassDescriptor classDescriptor = BindingUtils.getClassDescriptor(bindingContext(), ktPureClassOrObject);
        this.classDescriptor = classDescriptor;
        this.primaryConstructor = classDescriptor.mo6347getUnsubstitutedPrimaryConstructor();
    }

    public void setOrdinal(int i) {
        this.ordinal = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.general.AbstractTranslator
    public TranslationContext context() {
        TranslationContext translationContext = this.context;
        if (translationContext == null) {
            $$$reportNull$$$0(3);
        }
        return translationContext;
    }

    public void generateInitializeMethod(DelegationTranslator delegationTranslator) {
        addOuterClassReference(this.classDescriptor);
        if (this.primaryConstructor != null) {
            this.initFunction.getBody().getStatements().addAll(FunctionBodyTranslator.setDefaultValueForArguments(this.primaryConstructor, context()));
            mayBeAddCallToSuperMethod(this.initFunction);
            this.initFunction.getParameters().addAll(translatePrimaryConstructorParameters());
            if (this.classDescriptor.getClassKind() == ClassKind.ENUM_CLASS) {
                KtPureClassOrObject ktPureClassOrObject = this.classDeclaration;
                if (ktPureClassOrObject instanceof PsiElement) {
                    addEnumClassParameters(this.initFunction, (PsiElement) ktPureClassOrObject);
                }
            }
        }
        addThrowableCall();
        delegationTranslator.addInitCode(this.initFunction.getBody().getStatements());
        new InitializerVisitor().traverseContainer(this.classDeclaration, context().innerBlock(this.initFunction.getBody()));
    }

    private static void addEnumClassParameters(JsFunction jsFunction, PsiElement psiElement) {
        JsName jsNameDeclareFreshName = jsFunction.getScope().declareFreshName("name");
        JsName jsNameDeclareFreshName2 = jsFunction.getScope().declareFreshName("ordinal");
        jsFunction.getParameters().addAll(0, Arrays.asList(new JsParameter(jsNameDeclareFreshName), new JsParameter(jsNameDeclareFreshName2)));
        jsFunction.getBody().getStatements().add(JsAstUtils.assignmentToThisField(Namer.ENUM_NAME_FIELD, jsNameDeclareFreshName.makeRef().source((Object) psiElement)));
        jsFunction.getBody().getStatements().add(JsAstUtils.assignmentToThisField(Namer.ENUM_ORDINAL_FIELD, jsNameDeclareFreshName2.makeRef().source((Object) psiElement)));
    }

    private void addOuterClassReference(ClassDescriptor classDescriptor) {
        JsName outerClassReference = this.context.getOuterClassReference(classDescriptor);
        if (outerClassReference == null) {
            return;
        }
        this.initFunction.getParameters().add(0, new JsParameter(outerClassReference));
        this.initFunction.getBody().getStatements().add(new JsExpressionStatement(JsAstUtils.assignment(JsAstUtils.pureFqn(outerClassReference, new JsThisRef()), JsAstUtils.pureFqn(outerClassReference, (JsExpression) null)).source((Object) this.classDeclaration)));
    }

    public static JsExpression generateEnumEntryInstanceCreation(TranslationContext translationContext, KtEnumEntry ktEnumEntry, int i) {
        if (translationContext == null) {
            $$$reportNull$$$0(4);
        }
        if (ktEnumEntry == null) {
            $$$reportNull$$$0(5);
        }
        ResolvedCall superCall = BindingUtils.getSuperCall(translationContext.bindingContext(), ktEnumEntry);
        if (superCall == null) {
            superCall = CallUtilKt.getFunctionResolvedCallWithAssert(ktEnumEntry, translationContext.bindingContext());
        }
        List listAsList = Arrays.asList(new JsStringLiteral(ktEnumEntry.get$name()), new JsIntLiteral(i));
        JsExpression jsExpressionTranslate = CallTranslator.translate(translationContext, superCall);
        if (jsExpressionTranslate instanceof JsInvocation) {
            ((JsInvocation) jsExpressionTranslate).getArguments().addAll(0, listAsList);
        } else if (jsExpressionTranslate instanceof JsNew) {
            ((JsNew) jsExpressionTranslate).getArguments().addAll(0, listAsList);
        }
        JsExpression jsExpressionSource = jsExpressionTranslate.source((Object) ktEnumEntry);
        if (jsExpressionSource == null) {
            $$$reportNull$$$0(6);
        }
        return jsExpressionSource;
    }

    private void mayBeAddCallToSuperMethod(JsFunction jsFunction) {
        UsageTracker usageTracker;
        KtPureClassOrObject ktPureClassOrObject = this.classDeclaration;
        if ((ktPureClassOrObject instanceof KtClassOrObject) && ((KtClassOrObject) ktPureClassOrObject).hasModifier(KtTokens.ENUM_KEYWORD)) {
            addCallToSuperMethod(Collections.emptyList(), jsFunction, this.classDeclaration);
            return;
        }
        if (BindingUtils.hasAncestorClass(bindingContext(), this.classDeclaration)) {
            ResolvedCall<FunctionDescriptor> superCall = BindingUtils.getSuperCall(bindingContext(), this.classDeclaration);
            if (superCall == null) {
                if (DescriptorUtils.isEnumEntry(this.classDescriptor)) {
                    addCallToSuperMethod(getAdditionalArgumentsForEnumConstructor(), jsFunction, this.classDeclaration);
                    return;
                }
                return;
            }
            if (JsDescriptorUtils.isImmediateSubtypeOfError(this.classDescriptor)) {
                emulateSuperCallToNativeError(this.context, this.classDescriptor, superCall, new JsThisRef());
                return;
            }
            int iMax = 0;
            if (this.classDeclaration instanceof KtEnumEntry) {
                JsExpression jsExpressionTranslate = CallTranslator.translate(context(), superCall, null);
                jsExpressionTranslate.setSource(this.classDeclaration);
                this.initFunction.getBody().getStatements().add(AstUtilsKt.toInvocationWith(jsExpressionTranslate, getAdditionalArgumentsForEnumConstructor(), 0, new JsThisRef()).makeStmt());
                return;
            }
            ArrayList arrayList = new ArrayList();
            ConstructorDescriptor underlyingConstructorDescriptor = (ConstructorDescriptor) superCall.getResultingDescriptor();
            if (underlyingConstructorDescriptor instanceof TypeAliasConstructorDescriptor) {
                underlyingConstructorDescriptor = ((TypeAliasConstructorDescriptor) underlyingConstructorDescriptor).getUnderlyingConstructorDescriptor();
            }
            List<DeclarationDescriptor> classOrConstructorClosure = this.context.getClassOrConstructorClosure(underlyingConstructorDescriptor);
            if (classOrConstructorClosure != null && (usageTracker = this.context.usageTracker()) != null) {
                for (DeclarationDescriptor declarationDescriptor : classOrConstructorClosure) {
                    usageTracker.used(declarationDescriptor);
                    arrayList.add(usageTracker.getCapturedDescriptorToJsName().get(declarationDescriptor).makeRef());
                }
            }
            if (superCall.getDispatchReceiver() != null) {
                arrayList.add(this.context.getDispatchReceiver(JsDescriptorUtils.getReceiverParameterForReceiver(superCall.getDispatchReceiver())));
            }
            if (!DescriptorUtils.isAnonymousObject(this.classDescriptor)) {
                arrayList.addAll(CallArgumentTranslator.translate(superCall, null, context()).getTranslateArguments());
            } else {
                Iterator<ValueParameterDescriptor> it = underlyingConstructorDescriptor.getValueParameters().iterator();
                while (it.hasNext()) {
                    JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(it.next().getShortName().asString());
                    arrayList.add(jsNameDeclareTemporaryName.makeRef());
                    jsFunction.getParameters().add(new JsParameter(jsNameDeclareTemporaryName));
                }
            }
            if (underlyingConstructorDescriptor.isPrimary() || underlyingConstructorDescriptor.getConstructedClass().isExternal()) {
                addCallToSuperMethod(arrayList, jsFunction, superCall.get$fakeCall().getCallElement());
                return;
            }
            if (!DescriptorUtils.isAnonymousObject(this.classDescriptor)) {
                for (ValueParameterDescriptor valueParameterDescriptor : superCall.getValueArguments().keySet()) {
                    if (!(superCall.getValueArguments().get(valueParameterDescriptor) instanceof DefaultValueArgument)) {
                        iMax = Math.max(iMax, valueParameterDescriptor.getIndex() + 1);
                    }
                }
                int size = underlyingConstructorDescriptor.getValueParameters().size() - iMax;
                while (true) {
                    int i = size - 1;
                    if (size <= 0) {
                        break;
                    }
                    arrayList.add(Namer.getUndefinedExpression());
                    size = i;
                }
            }
            addCallToSuperSecondaryConstructor(arrayList, underlyingConstructorDescriptor);
        }
    }

    public static void emulateSuperCallToNativeError(TranslationContext translationContext, ClassDescriptor classDescriptor, ResolvedCall<? extends FunctionDescriptor> resolvedCall, JsExpression jsExpression) {
        JsExpression jsConditional;
        if (translationContext == null) {
            $$$reportNull$$$0(7);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(9);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(10);
        }
        JsInvocation jsInvocation = new JsInvocation(Namer.getFunctionCallRef(ReferenceTranslator.translateAsTypeReference(DescriptorUtilsKt.getSuperClassOrAny(classDescriptor), translationContext)), jsExpression.deepCopy());
        List<JsStatement> statements = translationContext.getCurrentBlock().getStatements();
        statements.add(JsAstUtils.asSyntheticStatement(jsInvocation));
        JsExpression undefinedExpression = Namer.getUndefinedExpression();
        JsExpression jsNullLiteral = new JsNullLiteral();
        for (ValueParameterDescriptor valueParameterDescriptor : ((FunctionDescriptor) resolvedCall.getResultingDescriptor()).getValueParameters()) {
            ResolvedValueArgument resolvedValueArgument = resolvedCall.getValueArguments().get(valueParameterDescriptor);
            if (resolvedValueArgument instanceof ExpressionValueArgument) {
                JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(((ExpressionValueArgument) resolvedValueArgument).getValueArgument().getArgumentExpression(), translationContext);
                if (KotlinBuiltIns.isStringOrNullableString(valueParameterDescriptor.getType())) {
                    undefinedExpression = translationContext.cacheExpressionIfNeeded(jsExpressionTranslateAsExpression);
                } else if (KotlinBuiltIns.isThrowableOrNullableThrowable(valueParameterDescriptor.getType())) {
                    jsNullLiteral = translationContext.cacheExpressionIfNeeded(jsExpressionTranslateAsExpression);
                } else {
                    statements.add(JsAstUtils.asSyntheticStatement(jsExpressionTranslateAsExpression));
                }
            }
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(translationContext.getNameForBackingField(DescriptorUtils.getPropertyByName(classDescriptor.getUnsubstitutedMemberScope(), Name.identifier(CrashHianalyticsData.MESSAGE))), jsExpression.deepCopy());
        JsBinaryOperation jsBinaryOperationTypeOfIs = JsAstUtils.typeOfIs(undefinedExpression, new JsStringLiteral("undefined"));
        JsBinaryOperation jsBinaryOperation = new JsBinaryOperation(JsBinaryOperator.NEQ, jsNullLiteral, new JsNullLiteral());
        JsBinaryOperation jsBinaryOperationAnd = JsAstUtils.and(jsBinaryOperationTypeOfIs, jsBinaryOperation);
        JsInvocation jsInvocation2 = new JsInvocation(JsAstUtils.pureFqn("toString", Namer.kotlinObject()), jsNullLiteral.deepCopy());
        if (jsNullLiteral instanceof JsNullLiteral) {
            jsConditional = undefinedExpression.deepCopy();
        } else {
            if (!JsAstUtils.isUndefinedExpression(undefinedExpression)) {
                jsBinaryOperation = jsBinaryOperationAnd;
            }
            jsConditional = new JsConditional(jsBinaryOperation, jsInvocation2, undefinedExpression);
        }
        statements.add(JsAstUtils.asSyntheticStatement(JsAstUtils.assignment(jsNameRefPureFqn, jsConditional)));
        statements.add(JsAstUtils.asSyntheticStatement(JsAstUtils.assignment(JsAstUtils.pureFqn(translationContext.getNameForBackingField(DescriptorUtils.getPropertyByName(classDescriptor.getUnsubstitutedMemberScope(), Name.identifier("cause"))), jsExpression.deepCopy()), jsNullLiteral.deepCopy())));
    }

    private List<JsExpression> getAdditionalArgumentsForEnumConstructor() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new JsStringLiteral(this.classDescriptor.getShortName().asString()));
        arrayList.add(new JsIntLiteral(this.ordinal));
        return arrayList;
    }

    private void addCallToSuperMethod(List<JsExpression> list, JsFunction jsFunction, KtPureElement ktPureElement) {
        if (list == null) {
            $$$reportNull$$$0(12);
        }
        if (jsFunction == null) {
            $$$reportNull$$$0(13);
        }
        if (ktPureElement == null) {
            $$$reportNull$$$0(14);
        }
        if (jsFunction.getName() == null) {
            jsFunction.setName(context().scope().declareName(Namer.CALLEE_NAME));
        }
        JsInvocation jsInvocation = new JsInvocation(Namer.getFunctionCallRef(context().getInnerReference(DescriptorUtilsKt.getSuperClassOrAny(this.classDescriptor))), new JsExpression[0]);
        jsInvocation.setSource(ktPureElement);
        jsInvocation.getArguments().add(new JsThisRef());
        jsInvocation.getArguments().addAll(list);
        this.initFunction.getBody().getStatements().add(jsInvocation.makeStmt());
    }

    private void addCallToSuperSecondaryConstructor(List<JsExpression> list, ConstructorDescriptor constructorDescriptor) {
        if (list == null) {
            $$$reportNull$$$0(15);
        }
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(16);
        }
        JsInvocation jsInvocation = new JsInvocation(this.context.getInnerReference(constructorDescriptor), new JsExpression[0]);
        jsInvocation.getArguments().addAll(list);
        jsInvocation.getArguments().add(new JsThisRef());
        this.initFunction.getBody().getStatements().add(jsInvocation.makeStmt());
    }

    private List<JsParameter> translatePrimaryConstructorParameters() {
        List<KtParameter> primaryConstructorParameters = PsiUtils.getPrimaryConstructorParameters(this.classDeclaration);
        ArrayList arrayList = new ArrayList();
        Iterator<KtParameter> it = primaryConstructorParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(translateParameter(it.next()));
        }
        return arrayList;
    }

    private JsParameter translateParameter(KtParameter ktParameter) {
        if (ktParameter == null) {
            $$$reportNull$$$0(18);
        }
        JsParameter jsParameter = new JsParameter(context().getNameForDescriptor(BindingUtils.getDescriptorForElement(bindingContext(), ktParameter)));
        mayBeAddInitializerStatementForProperty(jsParameter, ktParameter);
        return jsParameter;
    }

    private void mayBeAddInitializerStatementForProperty(JsParameter jsParameter, KtParameter ktParameter) {
        if (jsParameter == null) {
            $$$reportNull$$$0(20);
        }
        if (ktParameter == null) {
            $$$reportNull$$$0(21);
        }
        PropertyDescriptor propertyDescriptorForConstructorParameter = BindingUtils.getPropertyDescriptorForConstructorParameter(bindingContext(), ktParameter);
        if (propertyDescriptorForConstructorParameter == null) {
            return;
        }
        JsNameRef jsNameRefMakeRef = jsParameter.getName().makeRef();
        MetadataProperties.setType(jsNameRefMakeRef, propertyDescriptorForConstructorParameter.getType());
        addInitializerOrPropertyDefinition(TranslationUtils.coerce(context(), jsNameRefMakeRef, TranslationUtils.getReturnTypeForCoercion(propertyDescriptorForConstructorParameter)), propertyDescriptorForConstructorParameter);
    }

    private void addInitializerOrPropertyDefinition(JsExpression jsExpression, PropertyDescriptor propertyDescriptor) {
        if (jsExpression == null) {
            $$$reportNull$$$0(22);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(23);
        }
        this.initFunction.getBody().getStatements().add(InitializerUtils.generateInitializerForProperty(context(), propertyDescriptor, jsExpression));
    }

    private void addThrowableCall() {
        if (JsDescriptorUtils.isExceptionClass(this.classDescriptor)) {
            if (JsDescriptorUtils.isImmediateSubtypeOfError(this.classDescriptor)) {
                this.initFunction.getBody().getStatements().add(JsAstUtils.asSyntheticStatement(new JsInvocation(JsAstUtils.pureFqn("captureStack", Namer.kotlinObject()), ReferenceTranslator.translateAsTypeReference(DescriptorUtilsKt.getSuperClassOrAny(this.classDescriptor), context()), new JsThisRef())));
            }
            this.initFunction.getBody().getStatements().add(JsAstUtils.asSyntheticStatement(JsAstUtils.assignment(JsAstUtils.pureFqn("name", new JsThisRef()), new JsStringLiteral(this.context.getInnerNameForDescriptor(this.classDescriptor).getIdent()))));
        }
    }
}
