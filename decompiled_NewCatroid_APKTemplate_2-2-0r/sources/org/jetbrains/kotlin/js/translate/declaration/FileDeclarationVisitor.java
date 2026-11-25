package org.jetbrains.kotlin.js.translate.declaration;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: FileDeclarationVisitor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\"\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\nH\u0016J\u0010\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\u000eH\u0016J\u0018\u0010\u0012\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/FileDeclarationVisitor;", "Lorg/jetbrains/kotlin/js/translate/declaration/AbstractDeclarationVisitor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "addFunction", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "expression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "psi", "Lorg/jetbrains/kotlin/psi/KtElement;", "addProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getter", "setter", "getBackingFieldReference", "visitProperty", "Lorg/jetbrains/kotlin/psi/KtProperty;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileDeclarationVisitor extends AbstractDeclarationVisitor {
    private final TranslationContext context;

    public FileDeclarationVisitor(TranslationContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor, org.jetbrains.kotlin.psi.KtVisitor
    public /* bridge */ /* synthetic */ Object visitProperty(KtProperty ktProperty, TranslationContext translationContext) {
        visitProperty2(ktProperty, translationContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor
    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public void visitProperty2(KtProperty expression, TranslationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        PropertyDescriptor propertyDescriptor = BindingUtils.getPropertyDescriptor(context.bindingContext(), expression);
        Intrinsics.checkNotNullExpressionValue(propertyDescriptor, "getPropertyDescriptor(...)");
        JsName innerNameForDescriptor = context.getInnerNameForDescriptor(propertyDescriptor);
        Intrinsics.checkNotNullExpressionValue(innerNameForDescriptor, "getInnerNameForDescriptor(...)");
        Boolean bool = (Boolean) context.bindingContext().get(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor);
        if (bool == null) {
            bool = false;
        }
        if (bool.booleanValue() || expression.getDelegateExpression() != null) {
            JsExpression jsExpressionTranslateDelegateOrInitializerExpression = PropertyTranslatorKt.translateDelegateOrInitializerExpression(context, expression);
            context.addDeclarationStatement(JsAstUtils.newVar(innerNameForDescriptor, null));
            if (jsExpressionTranslateDelegateOrInitializerExpression != null) {
                context.addTopLevelStatement(JsAstUtils.assignment(innerNameForDescriptor.makeRef(), jsExpressionTranslateDelegateOrInitializerExpression).makeStmt());
            }
        }
        super.visitProperty2(expression, context);
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor
    public void addFunction(FunctionDescriptor descriptor, JsExpression expression, KtElement psi) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (expression == null) {
            return;
        }
        UtilsKt.addFunctionButNotExport(this.context, descriptor, expression);
        this.context.export(descriptor);
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor
    public void addProperty(PropertyDescriptor descriptor, JsExpression getter, JsExpression setter) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(getter, "getter");
        if (!JsDescriptorUtils.isSimpleFinalProperty(descriptor)) {
            TranslationContext translationContext = this.context;
            PropertyGetterDescriptor getter2 = descriptor.getGetter();
            Intrinsics.checkNotNull(getter2);
            UtilsKt.addFunctionButNotExport(translationContext, getter2, getter);
            if (setter != null) {
                TranslationContext translationContext2 = this.context;
                PropertySetterDescriptor setter2 = descriptor.getSetter();
                Intrinsics.checkNotNull(setter2);
                UtilsKt.addFunctionButNotExport(translationContext2, setter2, setter);
            }
        }
        this.context.export(descriptor);
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor
    public JsExpression getBackingFieldReference(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        JsExpression innerReference = this.context.getInnerReference(descriptor);
        Intrinsics.checkNotNullExpressionValue(innerReference, "getInnerReference(...)");
        return innerReference;
    }
}
