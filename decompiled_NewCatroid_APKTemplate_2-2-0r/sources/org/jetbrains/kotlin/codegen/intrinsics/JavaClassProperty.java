package org.jetbrains.kotlin.codegen.intrinsics;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: JavaClassProperty.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016J\u0016\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u0004J,\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/JavaClassProperty;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicPropertyGetter;", "()V", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/codegen/StackValue;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "receiver", "generateImpl", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "fd", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isSuper", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassProperty extends IntrinsicPropertyGetter {
    public static final JavaClassProperty INSTANCE = new JavaClassProperty();

    private JavaClassProperty() {
    }

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicPropertyGetter
    public StackValue generate(ResolvedCall<?> resolvedCall, ExpressionCodegen codegen, final Type returnType, final StackValue receiver) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        return StackValue.operation(returnType, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.JavaClassProperty.generate.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                invoke2(instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstructionAdapter instructionAdapter) {
                JavaClassProperty javaClassProperty = JavaClassProperty.INSTANCE;
                Intrinsics.checkNotNull(instructionAdapter);
                StackValue.coerce(javaClassProperty.generateImpl(instructionAdapter, receiver), returnType, instructionAdapter);
            }
        });
    }

    public final Type generateImpl(InstructionAdapter v, StackValue receiver) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Type type = receiver.type;
        Intrinsics.checkNotNullExpressionValue(type, "type");
        KotlinType kotlinType = receiver.kotlinType;
        if (Intrinsics.areEqual(type, Type.VOID_TYPE)) {
            receiver.put(Type.VOID_TYPE, v);
            StackValue.unit().put(v);
            v.invokevirtual(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "getClass", "()Ljava/lang/Class;", false);
        } else if (kotlinType != null && InlineClassesUtilsKt.isInlineClassType(kotlinType)) {
            receiver.put(type, kotlinType, v);
            StackValue.coerce(type, kotlinType, AsmTypes.OBJECT_TYPE, kotlinType.getConstructor().getBuiltIns().getNullableAnyType(), v);
            v.invokevirtual(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "getClass", "()Ljava/lang/Class;", false);
        } else if (AsmUtil.isPrimitive(type)) {
            if (!StackValue.couldSkipReceiverOnStaticCall(receiver)) {
                receiver.put(type, v);
                AsmUtil.pop(v, type);
            }
            v.getstatic(AsmUtil.boxType(type).getInternalName(), "TYPE", "Ljava/lang/Class;");
        } else {
            receiver.put(type, v);
            v.invokevirtual(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "getClass", "()Ljava/lang/Class;", false);
        }
        Type type2 = AsmTypes.getType(Class.class);
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        return type2;
    }

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    public Callable toCallable(FunctionDescriptor fd, boolean isSuper, ResolvedCall<?> resolvedCall, ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(fd, "fd");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        KotlinTypeMapper typeMapper = codegen.getState().getTypeMapper();
        ReceiverValue dispatchReceiver = resolvedCall.getCall().getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        KotlinType type = dispatchReceiver.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return new IntrinsicCallable(AsmTypes.getType(Class.class), CollectionsKt.emptyList()) { // from class: org.jetbrains.kotlin.codegen.intrinsics.JavaClassProperty.toCallable.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(type, list, this.$classType, null, null, 16, null);
                Intrinsics.checkNotNull(type);
            }

            @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable
            public void invokeIntrinsic(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                JavaClassProperty javaClassProperty = JavaClassProperty.INSTANCE;
                StackValue stackValueNone = StackValue.none();
                Intrinsics.checkNotNullExpressionValue(stackValueNone, "none(...)");
                javaClassProperty.generateImpl(v, stackValueNone);
            }

            @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable, org.jetbrains.kotlin.codegen.Callable
            public boolean isStaticCall() {
                return AsmUtil.isPrimitive(this.$classType);
            }
        };
    }
}
