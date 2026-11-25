package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: IteratorNext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004H\u0002J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IteratorNext;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "getKotlinPrimitiveClassName", "Lorg/jetbrains/kotlin/name/Name;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "getPrimitiveIteratorType", "primitiveClassName", "toCallable", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IteratorNext extends IntrinsicMethod {
    public static final IteratorNext INSTANCE = new IteratorNext();

    private IteratorNext() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public IntrinsicFunction toCallable(IrFunctionAccessExpression expression, JvmMethodSignature signature, ClassCodegen classCodegen) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        final Type typeUnboxType = AsmUtil.isBoxedPrimitiveType(signature.getReturnType()) ? AsmUtil.unboxType(signature.getReturnType()) : signature.getReturnType();
        Intrinsics.checkNotNull(typeUnboxType);
        JvmMethodSignature jvmMethodSignatureNewReturnType = IntrinsicMethod.INSTANCE.newReturnType(signature, typeUnboxType);
        final Name kotlinPrimitiveClassName = getKotlinPrimitiveClassName(typeUnboxType);
        return IntrinsicFunction.INSTANCE.create(expression, jvmMethodSignatureNewReturnType, classCodegen, CollectionsKt.listOf(getPrimitiveIteratorType(kotlinPrimitiveClassName)), new Function2<IntrinsicFunction, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.IteratorNext.toCallable.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IntrinsicFunction intrinsicFunction, InstructionAdapter instructionAdapter) {
                invoke2(intrinsicFunction, instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IntrinsicFunction create, InstructionAdapter it) {
                Intrinsics.checkNotNullParameter(create, "$this$create");
                Intrinsics.checkNotNullParameter(it, "it");
                it.invokevirtual(IteratorNext.INSTANCE.getPrimitiveIteratorType(kotlinPrimitiveClassName).getInternalName(), ES6Iterator.NEXT_METHOD + kotlinPrimitiveClassName.asString(), "()" + typeUnboxType.getDescriptor(), false);
            }
        });
    }

    private final Name getKotlinPrimitiveClassName(Type type) {
        return JvmPrimitiveType.get(type.getClassName()).getPrimitiveType().getTypeName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Type getPrimitiveIteratorType(Name primitiveClassName) {
        Name nameIdentifier = Name.identifier(primitiveClassName.asString() + "Iterator");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        FqName fqNameChild = StandardNames.COLLECTIONS_PACKAGE_FQ_NAME.child(nameIdentifier);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        Type objectType = Type.getObjectType(JvmFileClassUtilKt.getInternalNameWithoutInnerClasses(fqNameChild));
        Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
        return objectType;
    }
}
