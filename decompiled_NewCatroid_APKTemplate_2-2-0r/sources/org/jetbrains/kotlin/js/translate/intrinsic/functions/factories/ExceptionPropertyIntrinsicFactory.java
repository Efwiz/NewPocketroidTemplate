package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfo;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.SuperCallReceiverValue;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ExceptionPropertyIntrinsicFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\tB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/ExceptionPropertyIntrinsicFactory;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/FunctionIntrinsicFactory;", "()V", "getIntrinsic", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "Intrinsic", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExceptionPropertyIntrinsicFactory implements FunctionIntrinsicFactory {
    public static final ExceptionPropertyIntrinsicFactory INSTANCE = new ExceptionPropertyIntrinsicFactory();

    private ExceptionPropertyIntrinsicFactory() {
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.FunctionIntrinsicFactory
    public FunctionIntrinsic getIntrinsic(FunctionDescriptor descriptor, TranslationContext context) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!(descriptor instanceof PropertyGetterDescriptor)) {
            return null;
        }
        DeclarationDescriptor containingDeclaration = ((PropertyGetterDescriptor) descriptor).getCorrespondingProperty().getDeclarationDescriptor();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null) {
            return null;
        }
        SimpleType defaultType = classDescriptor.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        if (TypeUtilsKt.isNotNullThrowable(defaultType)) {
            return Intrinsic.INSTANCE;
        }
        return null;
    }

    /* JADX INFO: compiled from: ExceptionPropertyIntrinsicFactory.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/ExceptionPropertyIntrinsicFactory$Intrinsic;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "()V", "apply", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "arguments", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Intrinsic extends FunctionIntrinsic {
        public static final Intrinsic INSTANCE = new Intrinsic();

        private Intrinsic() {
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
        public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext context) {
            Intrinsics.checkNotNullParameter(callInfo, "callInfo");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(context, "context");
            CallableDescriptor resultingDescriptor = callInfo.getResolvedCall().getResultingDescriptor();
            Intrinsics.checkNotNull(resultingDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) resultingDescriptor;
            if (!(callInfo.getResolvedCall().getCall().getExplicitReceiver() instanceof SuperCallReceiverValue)) {
                JsName nameForDescriptor = context.getNameForDescriptor(propertyDescriptor);
                Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
                JsExpression dispatchReceiver = callInfo.getDispatchReceiver();
                Intrinsics.checkNotNull(dispatchReceiver);
                return new JsNameRef(nameForDescriptor, dispatchReceiver);
            }
            ClassDescriptor classDescriptor = context.getClassDescriptor();
            Intrinsics.checkNotNull(classDescriptor);
            MemberScope unsubstitutedMemberScope = classDescriptor.getUnsubstitutedMemberScope();
            Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
            Collection contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, DescriptorKindFilter.CALLABLES, null, 2, null);
            ArrayList<PropertyDescriptor> arrayList = new ArrayList();
            for (Object obj : contributedDescriptors$default) {
                if (obj instanceof PropertyDescriptor) {
                    arrayList.add(obj);
                }
            }
            for (PropertyDescriptor propertyDescriptor2 : arrayList) {
                Collection<? extends PropertyDescriptor> overriddenDescriptors = propertyDescriptor2.getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                Collection<? extends PropertyDescriptor> collection = overriddenDescriptors;
                if (!(collection instanceof Collection) || !collection.isEmpty()) {
                    Iterator<T> it = collection.iterator();
                    while (it.hasNext()) {
                        if (Intrinsics.areEqual((PropertyDescriptor) it.next(), propertyDescriptor)) {
                            JsName nameForBackingField = context.getNameForBackingField(propertyDescriptor2);
                            JsExpression dispatchReceiver2 = callInfo.getDispatchReceiver();
                            Intrinsics.checkNotNull(dispatchReceiver2);
                            JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(nameForBackingField, dispatchReceiver2);
                            Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "pureFqn(...)");
                            return jsNameRefPureFqn;
                        }
                    }
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
    }
}
