package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.FunctionCodegen;
import org.jetbrains.kotlin.codegen.FunctionGenerationStrategy;
import org.jetbrains.kotlin.codegen.TransformationMethodVisitor;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: SuspendFunctionGenerationStrategy.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001%B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\n\u0010\u0015\u001a\u0004\u0018\u00010\tH\u0002J(\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\tH\u0002J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010 \u001a\u00020!H\u0016J(\u0010\"\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\tH\u0016J\f\u0010#\u001a\u00020$*\u00020\u0005H\u0002R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/SuspendFunctionGenerationStrategy;", "Lorg/jetbrains/kotlin/codegen/FunctionGenerationStrategy$CodegenBased;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "originalSuspendDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "declaration", "Lorg/jetbrains/kotlin/psi/KtFunction;", "containingClassInternalName", "", "functionCodegen", "Lorg/jetbrains/kotlin/codegen/FunctionCodegen;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/psi/KtFunction;Ljava/lang/String;Lorg/jetbrains/kotlin/codegen/FunctionCodegen;)V", "classBuilderForCoroutineState", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "getClassBuilderForCoroutineState", "()Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "classBuilderForCoroutineState$delegate", "Lkotlin/Lazy;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "containingClassInternalNameOrNull", "createStateMachineBuilder", "Lorg/jetbrains/kotlin/codegen/coroutines/CoroutineTransformerMethodVisitor;", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "access", "", "name", "desc", "doGenerateBody", "", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "wrapMethodVisitor", "allOverriddenFunctionsReturnUnit", "", "AddConstructorCallForCoroutineRegeneration", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuspendFunctionGenerationStrategy extends FunctionGenerationStrategy.CodegenBased {

    /* JADX INFO: renamed from: classBuilderForCoroutineState$delegate, reason: from kotlin metadata */
    private final Lazy classBuilderForCoroutineState;
    private ExpressionCodegen codegen;
    private final String containingClassInternalName;
    private final KtFunction declaration;
    private final FunctionCodegen functionCodegen;
    private final FunctionDescriptor originalSuspendDescriptor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SuspendFunctionGenerationStrategy(final GenerationState state, FunctionDescriptor originalSuspendDescriptor, KtFunction declaration, String containingClassInternalName, FunctionCodegen functionCodegen) {
        super(state);
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(originalSuspendDescriptor, "originalSuspendDescriptor");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(containingClassInternalName, "containingClassInternalName");
        Intrinsics.checkNotNullParameter(functionCodegen, "functionCodegen");
        this.originalSuspendDescriptor = originalSuspendDescriptor;
        this.declaration = declaration;
        this.containingClassInternalName = containingClassInternalName;
        this.functionCodegen = functionCodegen;
        this.classBuilderForCoroutineState = LazyKt.lazy(new Function0<ClassBuilder>() { // from class: org.jetbrains.kotlin.codegen.coroutines.SuspendFunctionGenerationStrategy$classBuilderForCoroutineState$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassBuilder invoke() {
                ClassBuilder classBuilderNewVisitor = state.getFactory().newVisitor(JvmDeclarationOriginKt.OtherOrigin(this.declaration, this.originalSuspendDescriptor), CodegenBinding.asmTypeForAnonymousClass(state.getBindingContext(), this.originalSuspendDescriptor), this.declaration.getContainingFile());
                SuspendFunctionGenerationStrategy suspendFunctionGenerationStrategy = this;
                CoroutineCodegenForNamedFunction.Companion companion = CoroutineCodegenForNamedFunction.Companion;
                Intrinsics.checkNotNull(classBuilderNewVisitor);
                ExpressionCodegen expressionCodegen = suspendFunctionGenerationStrategy.codegen;
                if (expressionCodegen == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("codegen");
                    expressionCodegen = null;
                }
                companion.create(classBuilderNewVisitor, expressionCodegen, suspendFunctionGenerationStrategy.originalSuspendDescriptor, suspendFunctionGenerationStrategy.declaration).generate();
                return classBuilderNewVisitor;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassBuilder getClassBuilderForCoroutineState() {
        return (ClassBuilder) this.classBuilderForCoroutineState.getValue();
    }

    @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
    public MethodVisitor wrapMethodVisitor(MethodVisitor mv, int access, String name2, String desc) {
        Intrinsics.checkNotNullParameter(mv, "mv");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        if ((access & 1024) != 0) {
            return mv;
        }
        if (InlineOnlyKt.isEffectivelyInlineOnly(this.originalSuspendDescriptor)) {
            return new SuspendForInlineOnlyMethodVisitor(mv, access, name2, desc);
        }
        CoroutineTransformerMethodVisitor coroutineTransformerMethodVisitorCreateStateMachineBuilder = createStateMachineBuilder(mv, access, name2, desc);
        if (this.originalSuspendDescriptor.isInline()) {
            return new SuspendForInlineCopyingMethodVisitor(coroutineTransformerMethodVisitorCreateStateMachineBuilder, access, name2, desc, new C43861(this.functionCodegen), false);
        }
        if (Intrinsics.areEqual(this.state.getBindingContext().get(CodegenBinding.CAPTURES_CROSSINLINE_LAMBDA, this.originalSuspendDescriptor), (Object) true)) {
            return new AddConstructorCallForCoroutineRegeneration(new SuspendForInlineCopyingMethodVisitor(coroutineTransformerMethodVisitorCreateStateMachineBuilder, access, name2, desc, new C43872(this.functionCodegen), false, 32, null), access, name2, desc, null, null, new PropertyReference0(this) { // from class: org.jetbrains.kotlin.codegen.coroutines.SuspendFunctionGenerationStrategy.wrapMethodVisitor.3
                @Override // kotlin.jvm.internal.CallableReference
                public String getSignature() {
                    return "getClassBuilderForCoroutineState()Lorg/jetbrains/kotlin/codegen/ClassBuilder;";
                }

                @Override // kotlin.reflect.KProperty0
                public Object get() {
                    return ((SuspendFunctionGenerationStrategy) this.receiver).getClassBuilderForCoroutineState();
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public String getName() {
                    return "classBuilderForCoroutineState";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(SuspendFunctionGenerationStrategy.class);
                }
            }, this.containingClassInternalName, this.originalSuspendDescriptor.mo6753getDispatchReceiverParameter() != null, containingClassInternalNameOrNull());
        }
        return coroutineTransformerMethodVisitorCreateStateMachineBuilder;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.coroutines.SuspendFunctionGenerationStrategy$wrapMethodVisitor$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: SuspendFunctionGenerationStrategy.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C43861 extends FunctionReference implements Function6<JvmDeclarationOrigin, Integer, String, String, String, String[], MethodVisitor> {
        C43861(Object obj) {
            super(6, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "newMethod(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "newMethod";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FunctionCodegen.class);
        }

        @Override // kotlin.jvm.functions.Function6
        public /* bridge */ /* synthetic */ MethodVisitor invoke(JvmDeclarationOrigin jvmDeclarationOrigin, Integer num, String str, String str2, String str3, String[] strArr) {
            return invoke(jvmDeclarationOrigin, num.intValue(), str, str2, str3, strArr);
        }

        public final MethodVisitor invoke(JvmDeclarationOrigin p0, int i, String p2, String p3, String str, String[] strArr) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p2, "p2");
            Intrinsics.checkNotNullParameter(p3, "p3");
            return ((FunctionCodegen) this.receiver).newMethod(p0, i, p2, p3, str, strArr);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.coroutines.SuspendFunctionGenerationStrategy$wrapMethodVisitor$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: SuspendFunctionGenerationStrategy.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C43872 extends FunctionReference implements Function6<JvmDeclarationOrigin, Integer, String, String, String, String[], MethodVisitor> {
        C43872(Object obj) {
            super(6, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "newMethod(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "newMethod";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FunctionCodegen.class);
        }

        @Override // kotlin.jvm.functions.Function6
        public /* bridge */ /* synthetic */ MethodVisitor invoke(JvmDeclarationOrigin jvmDeclarationOrigin, Integer num, String str, String str2, String str3, String[] strArr) {
            return invoke(jvmDeclarationOrigin, num.intValue(), str, str2, str3, strArr);
        }

        public final MethodVisitor invoke(JvmDeclarationOrigin p0, int i, String p2, String p3, String str, String[] strArr) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p2, "p2");
            Intrinsics.checkNotNullParameter(p3, "p3");
            return ((FunctionCodegen) this.receiver).newMethod(p0, i, p2, p3, str, strArr);
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0037  */
    /* JADX WARN: Code duplicated, block: B:31:0x009b  */
    private final CoroutineTransformerMethodVisitor createStateMachineBuilder(MethodVisitor mv, int access, String name2, String desc) {
        boolean z;
        String strContainingClassInternalNameOrNull;
        String str = this.containingClassInternalName;
        PropertyReference0 propertyReference0 = new PropertyReference0(this) { // from class: org.jetbrains.kotlin.codegen.coroutines.SuspendFunctionGenerationStrategy.createStateMachineBuilder.1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getClassBuilderForCoroutineState()Lorg/jetbrains/kotlin/codegen/ClassBuilder;";
            }

            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return ((SuspendFunctionGenerationStrategy) this.receiver).getClassBuilderForCoroutineState();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "classBuilderForCoroutineState";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(SuspendFunctionGenerationStrategy.class);
            }
        };
        KotlinType returnType = this.originalSuspendDescriptor.getReturnType();
        if (returnType == null || !TypeUtilsKt.isUnit(returnType)) {
            z = false;
        } else {
            Collection<? extends FunctionDescriptor> overriddenDescriptors = this.originalSuspendDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            if (overriddenDescriptors.isEmpty() || allOverriddenFunctionsReturnUnit(this.originalSuspendDescriptor)) {
                z = false;
            } else {
                z = true;
            }
        }
        Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.codegen.coroutines.SuspendFunctionGenerationStrategy.createStateMachineBuilder.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str2) {
                invoke2(str2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                KtFunction ktFunction = SuspendFunctionGenerationStrategy.this.declaration;
                GenerationState generationState = SuspendFunctionGenerationStrategy.this.state;
                Intrinsics.checkNotNullExpressionValue(generationState, "access$getState$p$s38120415(...)");
                CoroutineCodegenKt.reportSuspensionPointInsideMonitor(ktFunction, generationState, it);
            }
        };
        Integer lineNumberForElement = CodegenUtil.getLineNumberForElement(this.declaration, false);
        int iIntValue = lineNumberForElement != null ? lineNumberForElement.intValue() : 0;
        String name3 = this.declaration.getContainingKtFile().get$name();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        boolean z2 = this.originalSuspendDescriptor.mo6753getDispatchReceiverParameter() != null;
        DeclarationDescriptor containingDeclaration = this.originalSuspendDescriptor.getContainingModule();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor != null) {
            String internalName = InlineClassesUtilsKt.isInlineClass(classDescriptor) ? this.state.getTypeMapper().mapType(classDescriptor).getInternalName() : null;
            if (internalName == null) {
                strContainingClassInternalNameOrNull = containingClassInternalNameOrNull();
            } else {
                strContainingClassInternalNameOrNull = internalName;
            }
        } else {
            strContainingClassInternalNameOrNull = containingClassInternalNameOrNull();
        }
        return new CoroutineTransformerMethodVisitor(mv, access, name2, desc, null, null, str, propertyReference0, true, z, function1, iIntValue, name3, z2, strContainingClassInternalNameOrNull, false, null, false, 229376, null);
    }

    private final boolean allOverriddenFunctionsReturnUnit(FunctionDescriptor functionDescriptor) {
        return allOverriddenFunctionsReturnUnit$bfs(new LinkedHashSet(), functionDescriptor);
    }

    private static final boolean allOverriddenFunctionsReturnUnit$bfs(Set<FunctionDescriptor> set, FunctionDescriptor functionDescriptor) {
        if (!set.add(functionDescriptor)) {
            return true;
        }
        KotlinType returnType = functionDescriptor.getOriginal().getReturnType();
        if (returnType == null || !TypeUtilsKt.isUnit(returnType)) {
            return false;
        }
        for (FunctionDescriptor functionDescriptor2 : functionDescriptor.getOverriddenDescriptors()) {
            Intrinsics.checkNotNull(functionDescriptor2);
            if (!allOverriddenFunctionsReturnUnit$bfs(set, functionDescriptor2)) {
                return false;
            }
        }
        return true;
    }

    private final String containingClassInternalNameOrNull() {
        Type typeMapClass;
        DeclarationDescriptor containingDeclaration = this.originalSuspendDescriptor.getContainingModule();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null || (typeMapClass = this.state.getTypeMapper().mapClass(classDescriptor)) == null) {
            return null;
        }
        return typeMapClass.getInternalName();
    }

    @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
    public void doGenerateBody(ExpressionCodegen codegen, JvmMethodSignature signature) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.codegen = codegen;
        KtExpression bodyExpression = this.declaration.getBodyExpression();
        if (bodyExpression == null) {
            throw new IllegalStateException(("Function has no body: " + PsiUtilsKt.getElementTextWithContext(this.declaration)).toString());
        }
        codegen.returnExpression(bodyExpression);
    }

    /* JADX INFO: compiled from: SuspendFunctionGenerationStrategy.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001Bi\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\u0010\u0010\n\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0007\u0018\u00010\u000b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0010\u000f\u001a\u00020\u0007\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\u0013J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0014R\u001b\u0010\u0014\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/SuspendFunctionGenerationStrategy$AddConstructorCallForCoroutineRegeneration;", "Lorg/jetbrains/kotlin/codegen/TransformationMethodVisitor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "access", "", "name", "", "desc", "signature", "exceptions", "", "obtainClassBuilderForCoroutineState", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "containingClassInternalName", "needDispatchReceiver", "", "internalNameForDispatchReceiver", "(Lorg/jetbrains/org/objectweb/asm/MethodVisitor;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Lkotlin/jvm/functions/Function0;Ljava/lang/String;ZLjava/lang/String;)V", "classBuilderForCoroutineState", "getClassBuilderForCoroutineState", "()Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "classBuilderForCoroutineState$delegate", "Lkotlin/Lazy;", "performTransformations", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class AddConstructorCallForCoroutineRegeneration extends TransformationMethodVisitor {

        /* JADX INFO: renamed from: classBuilderForCoroutineState$delegate, reason: from kotlin metadata */
        private final Lazy classBuilderForCoroutineState;
        private final String containingClassInternalName;
        private final String internalNameForDispatchReceiver;
        private final boolean needDispatchReceiver;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AddConstructorCallForCoroutineRegeneration(MethodVisitor delegate, int i, String name2, String desc, String str, String[] strArr, Function0<? extends ClassBuilder> obtainClassBuilderForCoroutineState, String containingClassInternalName, boolean z, String str2) {
            super(delegate, i, name2, desc, str, strArr, 0, 64, null);
            Intrinsics.checkNotNullParameter(delegate, "delegate");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            Intrinsics.checkNotNullParameter(obtainClassBuilderForCoroutineState, "obtainClassBuilderForCoroutineState");
            Intrinsics.checkNotNullParameter(containingClassInternalName, "containingClassInternalName");
            this.containingClassInternalName = containingClassInternalName;
            this.needDispatchReceiver = z;
            this.internalNameForDispatchReceiver = str2;
            this.classBuilderForCoroutineState = LazyKt.lazy(obtainClassBuilderForCoroutineState);
        }

        private final ClassBuilder getClassBuilderForCoroutineState() {
            return (ClassBuilder) this.classBuilderForCoroutineState.getValue();
        }

        @Override // org.jetbrains.kotlin.codegen.TransformationMethodVisitor
        protected void performTransformations(MethodNode methodNode) {
            Intrinsics.checkNotNullParameter(methodNode, "methodNode");
            Type objectType = Type.getObjectType(getClassBuilderForCoroutineState().getThisName());
            InsnList insnList = methodNode.instructions;
            MethodNode methodNode2 = new MethodNode();
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode2);
            InlineCodegenUtilsKt.addFakeContinuationConstructorCallMarker(instructionAdapter, true);
            CoroutineTransformerMethodVisitorKt.generateContinuationConstructorCall(instructionAdapter, objectType, methodNode, this.needDispatchReceiver, this.internalNameForDispatchReceiver, this.containingClassInternalName, getClassBuilderForCoroutineState());
            InlineCodegenUtilsKt.addFakeContinuationConstructorCallMarker(instructionAdapter, false);
            instructionAdapter.pop();
            InsnList instructions = methodNode2.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            insnList.insert(instructions);
        }
    }
}
