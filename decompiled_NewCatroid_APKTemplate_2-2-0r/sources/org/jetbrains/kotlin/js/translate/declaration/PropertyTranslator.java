package org.jetbrains.kotlin.js.translate.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableAccessorDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.expression.FunctionTranslatorKt;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: PropertyTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0010H\u0002J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0012H\u0002J\b\u0010\u0016\u001a\u00020\u0014H\u0002J\b\u0010\u0017\u001a\u00020\u0014H\u0002J\b\u0010\u0018\u001a\u00020\u0014H\u0002J\b\u0010\u0019\u001a\u00020\u0014H\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u001bH\u0002J\u0014\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00140 J\u0010\u0010!\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u001bH\u0002R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/PropertyTranslator;", "Lorg/jetbrains/kotlin/js/translate/general/AbstractTranslator;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "declaration", "Lorg/jetbrains/kotlin/psi/KtProperty;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;Lorg/jetbrains/kotlin/psi/KtProperty;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "getDeclaration", "()Lorg/jetbrains/kotlin/psi/KtProperty;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "accessorDescription", "", "accessorDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableAccessorDescriptor;", "createFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "generateDefaultAccessor", "Lorg/jetbrains/kotlin/js/backend/ast/JsPropertyInitializer;", "function", "generateDefaultGetter", "generateDefaultSetter", "generateGetter", "generateSetter", "getCustomGetterDeclaration", "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "getCustomSetterDeclaration", "translate", "", "result", "", "translateCustomAccessor", "expression", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PropertyTranslator extends AbstractTranslator {
    private final KtProperty declaration;
    private final VariableDescriptorWithAccessors descriptor;

    public final VariableDescriptorWithAccessors getDescriptor() {
        return this.descriptor;
    }

    public final KtProperty getDeclaration() {
        return this.declaration;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PropertyTranslator(VariableDescriptorWithAccessors descriptor, KtProperty ktProperty, TranslationContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        this.descriptor = descriptor;
        this.declaration = ktProperty;
    }

    public final void translate(List<JsPropertyInitializer> result) {
        Intrinsics.checkNotNullParameter(result, "result");
        PropertyTranslatorKt.addGetterAndSetter(result, this.descriptor, new Function0<JsPropertyInitializer>() { // from class: org.jetbrains.kotlin.js.translate.declaration.PropertyTranslator.translate.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsPropertyInitializer invoke() {
                return PropertyTranslator.this.generateGetter();
            }
        }, new Function0<JsPropertyInitializer>() { // from class: org.jetbrains.kotlin.js.translate.declaration.PropertyTranslator.translate.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsPropertyInitializer invoke() {
                return PropertyTranslator.this.generateSetter();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsPropertyInitializer generateGetter() {
        KtProperty ktProperty = this.declaration;
        return ktProperty != null ? PropertyTranslatorKt.hasCustomGetter(ktProperty) : false ? translateCustomAccessor(getCustomGetterDeclaration()) : generateDefaultGetter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsPropertyInitializer generateSetter() {
        KtProperty ktProperty = this.declaration;
        return ktProperty != null ? PropertyTranslatorKt.hasCustomSetter(ktProperty) : false ? translateCustomAccessor(getCustomSetterDeclaration()) : generateDefaultSetter();
    }

    private final KtPropertyAccessor getCustomGetterDeclaration() {
        KtProperty ktProperty = this.declaration;
        KtPropertyAccessor getter = ktProperty != null ? ktProperty.getGetter() : null;
        if (getter != null) {
            return getter;
        }
        throw new IllegalStateException("declaration and getter should not be null descriptor=" + this.descriptor + " declaration=" + this.declaration);
    }

    private final KtPropertyAccessor getCustomSetterDeclaration() {
        KtProperty ktProperty = this.declaration;
        KtPropertyAccessor setter = ktProperty != null ? ktProperty.getSetter() : null;
        if (setter != null) {
            return setter;
        }
        throw new IllegalStateException("declaration and setter should not be null descriptor=" + this.descriptor + " declaration=" + this.declaration);
    }

    private final JsPropertyInitializer generateDefaultGetter() {
        VariableAccessorDescriptor getter = this.descriptor.getGetter();
        if (getter == null) {
            throw new IllegalStateException("Getter descriptor should not be null");
        }
        JsFunction jsFunctionCreateFunction = createFunction(getter);
        JsNameRef jsNameRef = new JsNameRef(context().getNameForBackingField(this.descriptor), new JsThisRef());
        VariableDescriptorWithAccessors variableDescriptorWithAccessors = this.descriptor;
        TranslationContext translationContextContext = context();
        Intrinsics.checkNotNullExpressionValue(translationContextContext, "context(...)");
        new DefaultPropertyTranslator(variableDescriptorWithAccessors, translationContextContext, jsNameRef).generateDefaultGetterFunction(getter, jsFunctionCreateFunction);
        return generateDefaultAccessor(getter, jsFunctionCreateFunction);
    }

    private final JsPropertyInitializer generateDefaultSetter() {
        VariableAccessorDescriptor setter = this.descriptor.getSetter();
        if (setter == null) {
            throw new IllegalStateException("Setter descriptor should not be null");
        }
        JsFunction jsFunctionCreateFunction = createFunction(setter);
        JsNameRef jsNameRef = new JsNameRef(context().getNameForBackingField(this.descriptor), new JsThisRef());
        VariableDescriptorWithAccessors variableDescriptorWithAccessors = this.descriptor;
        TranslationContext translationContextContext = context();
        Intrinsics.checkNotNullExpressionValue(translationContextContext, "context(...)");
        new DefaultPropertyTranslator(variableDescriptorWithAccessors, translationContextContext, jsNameRef).generateDefaultSetterFunction(setter, jsFunctionCreateFunction);
        return generateDefaultAccessor(setter, jsFunctionCreateFunction);
    }

    private final JsPropertyInitializer generateDefaultAccessor(VariableAccessorDescriptor accessorDescriptor, JsFunction function) {
        JsPropertyInitializer jsPropertyInitializerTranslateFunctionAsEcma5PropertyDescriptor = TranslationUtils.translateFunctionAsEcma5PropertyDescriptor(function, accessorDescriptor, context());
        Intrinsics.checkNotNullExpressionValue(jsPropertyInitializerTranslateFunctionAsEcma5PropertyDescriptor, "translateFunctionAsEcma5PropertyDescriptor(...)");
        return jsPropertyInitializerTranslateFunctionAsEcma5PropertyDescriptor;
    }

    private final JsPropertyInitializer translateCustomAccessor(KtPropertyAccessor expression) {
        KtPropertyAccessor ktPropertyAccessor = expression;
        FunctionDescriptor functionDescriptor = BindingUtils.getFunctionDescriptor(bindingContext(), ktPropertyAccessor);
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "getFunctionDescriptor(...)");
        JsFunction jsFunction = new JsFunction(context().getScopeForDescriptor(functionDescriptor), new JsBlock(), functionDescriptor.toString());
        jsFunction.setSource(expression);
        JsBlock body = jsFunction.getBody();
        PsiElement finalElement = UtilsKt.getFinalElement(expression);
        body.setSource(finalElement instanceof LeafPsiElement ? (LeafPsiElement) finalElement : null);
        TranslationContext translationContextContext = context();
        Intrinsics.checkNotNullExpressionValue(translationContextContext, "context(...)");
        List<JsParameter> parameters = jsFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        FunctionTranslatorKt.translateFunction(FunctionTranslatorKt.translateAndAliasParameters(translationContextContext, functionDescriptor, parameters), ktPropertyAccessor, jsFunction);
        JsPropertyInitializer jsPropertyInitializerTranslateFunctionAsEcma5PropertyDescriptor = TranslationUtils.translateFunctionAsEcma5PropertyDescriptor(jsFunction, functionDescriptor, context());
        Intrinsics.checkNotNullExpressionValue(jsPropertyInitializerTranslateFunctionAsEcma5PropertyDescriptor, "translateFunctionAsEcma5PropertyDescriptor(...)");
        return jsPropertyInitializerTranslateFunctionAsEcma5PropertyDescriptor;
    }

    private final JsFunction createFunction(VariableAccessorDescriptor descriptor) {
        JsFunction jsFunction = new JsFunction(context().getScopeForDescriptor(descriptor), new JsBlock(), accessorDescription(descriptor));
        SourceElement source = descriptor.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        jsFunction.setSource(psi);
        JsBlock body = jsFunction.getBody();
        PsiElement finalElement = psi != null ? UtilsKt.getFinalElement(psi) : null;
        body.setSource(finalElement instanceof LeafPsiElement ? (LeafPsiElement) finalElement : null);
        return jsFunction;
    }

    private final String accessorDescription(VariableAccessorDescriptor accessorDescriptor) {
        String str;
        if (accessorDescriptor instanceof PropertyGetterDescriptor ? true : accessorDescriptor instanceof LocalVariableAccessorDescriptor.Getter) {
            str = "getter";
        } else {
            if (accessorDescriptor instanceof PropertySetterDescriptor ? true : accessorDescriptor instanceof LocalVariableAccessorDescriptor.Setter) {
                str = "setter";
            } else {
                throw new IllegalArgumentException("Unknown accessor type " + accessorDescriptor.getClass());
            }
        }
        String strAsString = accessorDescriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return str + " for " + strAsString;
    }
}
