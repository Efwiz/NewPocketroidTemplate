package org.jetbrains.kotlin.js.translate.intrinsic.functions.basic;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: RangeToIntrinsic.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/RangeToIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsicWithReceiverComputed;", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "rangeTypeDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getRangeTypeDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "apply", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "receiver", "arguments", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RangeToIntrinsic extends FunctionIntrinsicWithReceiverComputed {
    private final ClassDescriptor rangeTypeDescriptor;

    public RangeToIntrinsic(FunctionDescriptor function) {
        Intrinsics.checkNotNullParameter(function, "function");
        KotlinType returnType = function.getReturnType();
        Intrinsics.checkNotNull(returnType);
        ClassifierDescriptor this$0 = returnType.getConstructor().getThis$0();
        Intrinsics.checkNotNull(this$0, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        this.rangeTypeDescriptor = (ClassDescriptor) this$0;
    }

    public final ClassDescriptor getRangeTypeDescriptor() {
        return this.rangeTypeDescriptor;
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
    public JsExpression apply(JsExpression receiver, List<? extends JsExpression> arguments, TranslationContext context) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(context, "context");
        DeclarationDescriptor containingDeclaration = this.rangeTypeDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor");
        Object objFirstOrNull = CollectionsKt.firstOrNull(context.getCurrentModule().getPackage(((PackageFragmentDescriptor) containingDeclaration).getFqName()).getMemberScope().getContributedDescriptors(DescriptorKindFilter.CLASSIFIERS, new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.RangeToIntrinsic$apply$existingClasses$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it, this.this$0.getRangeTypeDescriptor().getShortName()));
            }
        }));
        ClassDescriptor classDescriptor = objFirstOrNull instanceof ClassDescriptor ? (ClassDescriptor) objFirstOrNull : null;
        if (classDescriptor == null) {
            classDescriptor = this.rangeTypeDescriptor;
        }
        JsExpression jsExpressionTranslateAsTypeReference = ReferenceTranslator.translateAsTypeReference(classDescriptor, context);
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsTypeReference, "translateAsTypeReference(...)");
        return new JsNew(jsExpressionTranslateAsTypeReference, CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(receiver), (Iterable) arguments));
    }
}
