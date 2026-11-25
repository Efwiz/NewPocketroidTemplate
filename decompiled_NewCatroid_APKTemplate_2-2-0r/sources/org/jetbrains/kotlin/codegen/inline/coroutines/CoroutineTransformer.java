package org.jetbrains.kotlin.codegen.inline.coroutines;

import androidx.profileinstaller.ProfileVerifier;
import com.reandroid.dex.sections.Marker;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineTransformerMethodVisitor;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineTransformerMethodVisitorKt;
import org.jetbrains.kotlin.codegen.coroutines.SuspendForInlineCopyingMethodVisitor;
import org.jetbrains.kotlin.codegen.inline.DeferredMethodVisitor;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.inline.InliningContext;
import org.jetbrains.kotlin.codegen.inline.RegeneratedClassContext;
import org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;

/* JADX INFO: compiled from: CoroutineTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u0000 (2\u00020\u0001:\u0001(B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\nH\u0002J\b\u0010\u0014\u001a\u00020\rH\u0002J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\bH\u0002J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\bH\u0002J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\bH\u0002J\u0010\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\bH\u0002J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\bJ\u0010\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\bH\u0002J\u0010\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\bH\u0002J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\n2\u0006\u0010\u001e\u001a\u00020\bJ\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\nJ\u000e\u0010\"\u001a\u00020 2\u0006\u0010\u0012\u001a\u00020\bJ\u000e\u0010#\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\bJ\u000e\u0010$\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\bJ\u000e\u0010%\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\bJ\u000e\u0010&\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\bJ\u0012\u0010'\u001a\u0004\u0018\u00010\u00052\u0006\u0010!\u001a\u00020\nH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/coroutines/CoroutineTransformer;", "", "inliningContext", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "classBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "methods", "", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "superClassName", "", "(Lorg/jetbrains/kotlin/codegen/inline/InliningContext;Lorg/jetbrains/kotlin/codegen/ClassBuilder;Ljava/util/List;Ljava/lang/String;)V", "generateForInline", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "createNewMethodFrom", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "node", "name", "isContinuationNotLambda", "isInvokeSuspend", "isStateMachine", "isSuspendFunctionWithFakeConstructorCall", "isSuspendLambda", "newMethod", "Lorg/jetbrains/kotlin/codegen/inline/DeferredMethodVisitor;", "newStateMachineForLambda", "newStateMachineForNamedFunction", "oldContinuationFrom", "method", "registerClassBuilder", "", "continuationClassName", "replaceFakesWithReals", "safeToRemoveContinuationClass", "shouldGenerateStateMachine", "shouldSkip", "suspendLambdaWithGeneratedStateMachine", "unregisterClassBuilder", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineTransformer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ClassBuilder classBuilder;
    private final boolean generateForInline;
    private final InliningContext inliningContext;
    private final List<MethodNode> methods;
    private final GenerationState state;
    private final String superClassName;

    /* JADX WARN: Multi-variable type inference failed */
    public CoroutineTransformer(InliningContext inliningContext, ClassBuilder classBuilder, List<? extends MethodNode> methods, String superClassName) {
        Intrinsics.checkNotNullParameter(inliningContext, "inliningContext");
        Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
        Intrinsics.checkNotNullParameter(methods, "methods");
        Intrinsics.checkNotNullParameter(superClassName, "superClassName");
        this.inliningContext = inliningContext;
        this.classBuilder = classBuilder;
        this.methods = methods;
        this.superClassName = superClassName;
        this.state = inliningContext.getState();
        this.generateForInline = inliningContext.getCallSiteInfo().isInlineOrInsideInline();
    }

    public final boolean shouldSkip(MethodNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        List<MethodNode> list = this.methods;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (MethodNode methodNode : list) {
            if (Intrinsics.areEqual(methodNode.f1016name, node.f1016name + CoroutineTransformerKt.FOR_INLINE_SUFFIX) && Intrinsics.areEqual(methodNode.desc, node.desc)) {
                return true;
            }
        }
        return false;
    }

    public final boolean shouldGenerateStateMachine(MethodNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        if (isContinuationNotLambda()) {
            return false;
        }
        return isSuspendFunctionWithFakeConstructorCall(node) || (isSuspendLambda(node) && !isStateMachine(node));
    }

    public final boolean suspendLambdaWithGeneratedStateMachine(MethodNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        return !isContinuationNotLambda() && isSuspendLambda(node) && isStateMachine(node);
    }

    private final boolean isContinuationNotLambda() {
        return this.inliningContext.getIsContinuation() && StringsKt.endsWith$default(this.superClassName, "ContinuationImpl", false, 2, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isStateMachine(MethodNode node) {
        InsnList instructions = node.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        for (AbstractInsnNode abstractInsnNode : UtilKt.asSequence(instructions)) {
            if ((abstractInsnNode instanceof LdcInsnNode) && Intrinsics.areEqual(((LdcInsnNode) abstractInsnNode).cst, CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isSuspendLambda(MethodNode node) {
        return isInvokeSuspend(node);
    }

    public final DeferredMethodVisitor newMethod(MethodNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        if (isInvokeSuspend(node)) {
            isStateMachine(node);
            return newStateMachineForLambda(node);
        }
        if (isSuspendFunctionWithFakeConstructorCall(node)) {
            return newStateMachineForNamedFunction(node);
        }
        throw new IllegalStateException(("no need to generate state maching for " + node.f1016name).toString());
    }

    private final boolean isInvokeSuspend(MethodNode node) {
        String name2 = node.f1016name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        return Intrinsics.areEqual(StringsKt.removeSuffix(name2, (CharSequence) CoroutineTransformerKt.FOR_INLINE_SUFFIX), CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME) && this.inliningContext.getIsContinuation();
    }

    private final boolean isSuspendFunctionWithFakeConstructorCall(MethodNode node) {
        return INSTANCE.findFakeContinuationConstructorClassName(node) != null;
    }

    private final DeferredMethodVisitor newStateMachineForLambda(final MethodNode node) {
        String name2 = node.f1016name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        final String strRemoveSuffix = StringsKt.removeSuffix(name2, (CharSequence) CoroutineTransformerKt.FOR_INLINE_SUFFIX);
        return new DeferredMethodVisitor(new MethodNode(node.access, strRemoveSuffix, node.desc, node.signature, ArrayUtil.toStringArray(node.exceptions)), new Function0<MethodVisitor>() { // from class: org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformer.newStateMachineForLambda.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MethodVisitor invoke() {
                final SourceCompilerForInline sourceCompilerForInline = CoroutineTransformer.this.inliningContext.getRoot().getSourceCompilerForInline();
                MethodVisitor methodVisitorCreateNewMethodFrom = CoroutineTransformer.this.createNewMethodFrom(node, strRemoveSuffix);
                int i = node.access;
                String str = strRemoveSuffix;
                String desc = node.desc;
                Intrinsics.checkNotNullExpressionValue(desc, "desc");
                String thisName = CoroutineTransformer.this.classBuilder.getThisName();
                Intrinsics.checkNotNullExpressionValue(thisName, "getThisName(...)");
                final CoroutineTransformer coroutineTransformer = CoroutineTransformer.this;
                Function0<ClassBuilder> function0 = new Function0<ClassBuilder>() { // from class: org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformer$newStateMachineForLambda$1$stateMachineBuilder$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final ClassBuilder invoke() {
                        return coroutineTransformer.classBuilder;
                    }
                };
                Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformer$newStateMachineForLambda$1$stateMachineBuilder$2
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
                        sourceCompilerForInline.reportSuspensionPointInsideMonitor(it);
                    }
                };
                int lineNumber = CoroutineTransformer.this.inliningContext.getCallSiteInfo().getLineNumber();
                File file = CoroutineTransformer.this.inliningContext.getCallSiteInfo().getFile();
                String name3 = file != null ? file.getName() : null;
                if (name3 == null) {
                    name3 = "";
                }
                CoroutineTransformerMethodVisitor coroutineTransformerMethodVisitor = new CoroutineTransformerMethodVisitor(methodVisitorCreateNewMethodFrom, i, str, desc, null, null, thisName, function0, false, false, function1, lineNumber, name3, false, null, false, null, false, 253952, null);
                if (CoroutineTransformer.this.generateForInline) {
                    int i2 = node.access;
                    String str2 = strRemoveSuffix;
                    String desc2 = node.desc;
                    Intrinsics.checkNotNullExpressionValue(desc2, "desc");
                    return new SuspendForInlineCopyingMethodVisitor(coroutineTransformerMethodVisitor, i2, str2, desc2, new C02831(CoroutineTransformer.this.classBuilder), false, 32, null);
                }
                return coroutineTransformerMethodVisitor;
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformer$newStateMachineForLambda$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: CoroutineTransformer.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class C02831 extends FunctionReference implements Function6<JvmDeclarationOrigin, Integer, String, String, String, String[], MethodVisitor> {
                C02831(Object obj) {
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
                    return Reflection.getOrCreateKotlinClass(ClassBuilder.class);
                }

                @Override // kotlin.jvm.functions.Function6
                public /* bridge */ /* synthetic */ MethodVisitor invoke(JvmDeclarationOrigin jvmDeclarationOrigin, Integer num, String str, String str2, String str3, String[] strArr) {
                    return invoke(jvmDeclarationOrigin, num.intValue(), str, str2, str3, strArr);
                }

                public final MethodVisitor invoke(JvmDeclarationOrigin p0, int i, String p2, String p3, String str, String[] strArr) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    Intrinsics.checkNotNullParameter(p2, "p2");
                    Intrinsics.checkNotNullParameter(p3, "p3");
                    return ((ClassBuilder) this.receiver).newMethod(p0, i, p2, p3, str, strArr);
                }
            }
        });
    }

    private final DeferredMethodVisitor newStateMachineForNamedFunction(final MethodNode node) {
        String name2 = node.f1016name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        final String strRemoveSuffix = StringsKt.removeSuffix(name2, (CharSequence) CoroutineTransformerKt.FOR_INLINE_SUFFIX);
        final String strFindFakeContinuationConstructorClassName = INSTANCE.findFakeContinuationConstructorClassName(node);
        boolean z = this.inliningContext instanceof RegeneratedClassContext;
        return new DeferredMethodVisitor(new MethodNode(node.access, strRemoveSuffix, node.desc, node.signature, ArrayUtil.toStringArray(node.exceptions)), new Function0<MethodVisitor>() { // from class: org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformer.newStateMachineForNamedFunction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MethodVisitor invoke() {
                Object next;
                List list = CoroutineTransformer.this.methods;
                String str = strRemoveSuffix;
                MethodNode methodNode = node;
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    MethodNode methodNode2 = (MethodNode) next;
                    if (Intrinsics.areEqual(methodNode2.f1016name, str) && Intrinsics.areEqual(methodNode2.desc, methodNode.desc)) {
                        break;
                    }
                }
                MethodNode methodNode3 = (MethodNode) next;
                boolean zIsStateMachine = methodNode3 != null ? CoroutineTransformer.this.isStateMachine(methodNode3) : false;
                final SourceCompilerForInline sourceCompilerForInline = CoroutineTransformer.this.inliningContext.getRoot().getSourceCompilerForInline();
                MethodVisitor methodVisitorCreateNewMethodFrom = CoroutineTransformer.this.createNewMethodFrom(node, strRemoveSuffix);
                int i = node.access;
                String str2 = strRemoveSuffix;
                String desc = node.desc;
                Intrinsics.checkNotNullExpressionValue(desc, "desc");
                String thisName = CoroutineTransformer.this.classBuilder.getThisName();
                Intrinsics.checkNotNullExpressionValue(thisName, "getThisName(...)");
                final CoroutineTransformer coroutineTransformer = CoroutineTransformer.this;
                final String str3 = strFindFakeContinuationConstructorClassName;
                Function0<ClassBuilder> function0 = new Function0<ClassBuilder>() { // from class: org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformer$newStateMachineForNamedFunction$1$stateMachineBuilder$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final ClassBuilder invoke() {
                        InliningContext inliningContext = coroutineTransformer.inliningContext;
                        Intrinsics.checkNotNull(inliningContext, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.inline.RegeneratedClassContext");
                        ClassBuilder classBuilder = ((RegeneratedClassContext) inliningContext).getContinuationBuilders().get(str3);
                        Intrinsics.checkNotNull(classBuilder);
                        return classBuilder;
                    }
                };
                Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformer$newStateMachineForNamedFunction$1$stateMachineBuilder$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(String str4) {
                        invoke2(str4);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(String it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        sourceCompilerForInline.reportSuspensionPointInsideMonitor(it2);
                    }
                };
                int lineNumber = CoroutineTransformer.this.inliningContext.getCallSiteInfo().getLineNumber();
                File file = CoroutineTransformer.this.inliningContext.getCallSiteInfo().getFile();
                String name3 = file != null ? file.getName() : null;
                CoroutineTransformerMethodVisitor coroutineTransformerMethodVisitor = new CoroutineTransformerMethodVisitor(methodVisitorCreateNewMethodFrom, i, str2, desc, null, null, thisName, function0, true, zIsStateMachine, function1, lineNumber, name3 == null ? "" : name3, true, CoroutineTransformer.this.classBuilder.getThisName(), !CoroutineTransformer.this.state.getIsIrBackend(), null, false, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, null);
                if (CoroutineTransformer.this.generateForInline) {
                    CoroutineTransformerMethodVisitor coroutineTransformerMethodVisitor2 = coroutineTransformerMethodVisitor;
                    int i2 = node.access;
                    String str4 = strRemoveSuffix;
                    String desc2 = node.desc;
                    Intrinsics.checkNotNullExpressionValue(desc2, "desc");
                    return new SuspendForInlineCopyingMethodVisitor(coroutineTransformerMethodVisitor2, i2, str4, desc2, new C02841(CoroutineTransformer.this.classBuilder), false, 32, null);
                }
                return coroutineTransformerMethodVisitor;
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformer$newStateMachineForNamedFunction$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: CoroutineTransformer.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class C02841 extends FunctionReference implements Function6<JvmDeclarationOrigin, Integer, String, String, String, String[], MethodVisitor> {
                C02841(Object obj) {
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
                    return Reflection.getOrCreateKotlinClass(ClassBuilder.class);
                }

                @Override // kotlin.jvm.functions.Function6
                public /* bridge */ /* synthetic */ MethodVisitor invoke(JvmDeclarationOrigin jvmDeclarationOrigin, Integer num, String str, String str2, String str3, String[] strArr) {
                    return invoke(jvmDeclarationOrigin, num.intValue(), str, str2, str3, strArr);
                }

                public final MethodVisitor invoke(JvmDeclarationOrigin p0, int i, String p2, String p3, String str, String[] strArr) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    Intrinsics.checkNotNullParameter(p2, "p2");
                    Intrinsics.checkNotNullParameter(p3, "p3");
                    return ((ClassBuilder) this.receiver).newMethod(p0, i, p2, p3, str, strArr);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MethodVisitor createNewMethodFrom(MethodNode node, String name2) {
        MethodVisitor methodVisitorNewMethod = this.classBuilder.newMethod(JvmDeclarationOrigin.NO_ORIGIN, node.access, name2, node.desc, node.signature, ArrayUtil.toStringArray(node.exceptions));
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        return methodVisitorNewMethod;
    }

    public final void replaceFakesWithReals(MethodNode node) {
        int lastParameterIndex;
        ClassBuilder classBuilderUnregisterClassBuilder;
        Intrinsics.checkNotNullParameter(node, "node");
        String strFindFakeContinuationConstructorClassName = INSTANCE.findFakeContinuationConstructorClassName(node);
        if (strFindFakeContinuationConstructorClassName != null && (classBuilderUnregisterClassBuilder = unregisterClassBuilder(strFindFakeContinuationConstructorClassName)) != null) {
            classBuilderUnregisterClassBuilder.done(this.state.getConfig().getGenerateSmapCopyToAnnotation());
        }
        if (this.inliningContext.getIsContinuation()) {
            lastParameterIndex = 0;
        } else {
            String desc = node.desc;
            Intrinsics.checkNotNullExpressionValue(desc, "desc");
            lastParameterIndex = CoroutineTransformerMethodVisitorKt.getLastParameterIndex(desc, node.access);
        }
        CoroutineTransformerMethodVisitorKt.replaceFakeContinuationsWithRealOnes(node, lastParameterIndex);
    }

    public final void registerClassBuilder(String continuationClassName) {
        Intrinsics.checkNotNullParameter(continuationClassName, "continuationClassName");
        InliningContext parent = this.inliningContext.getParent();
        InliningContext parent2 = parent != null ? parent.getParent() : null;
        RegeneratedClassContext regeneratedClassContext = parent2 instanceof RegeneratedClassContext ? (RegeneratedClassContext) parent2 : null;
        if (regeneratedClassContext == null) {
            throw new IllegalStateException("incorrect context".toString());
        }
        regeneratedClassContext.getContinuationBuilders().put(continuationClassName, this.classBuilder);
    }

    private final ClassBuilder unregisterClassBuilder(String continuationClassName) {
        InliningContext inliningContext = this.inliningContext;
        Intrinsics.checkNotNull(inliningContext, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.inline.RegeneratedClassContext");
        return ((RegeneratedClassContext) inliningContext).getContinuationBuilders().remove(continuationClassName);
    }

    public final boolean safeToRemoveContinuationClass(MethodNode method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return (this.generateForInline || isStateMachine(method)) ? false : true;
    }

    public final String oldContinuationFrom(MethodNode method) {
        Object next;
        Intrinsics.checkNotNullParameter(method, "method");
        Iterator<T> it = this.methods.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            MethodNode methodNode = (MethodNode) next;
            if (Intrinsics.areEqual(methodNode.f1016name, method.f1016name + CoroutineTransformerKt.FOR_INLINE_SUFFIX) && Intrinsics.areEqual(methodNode.desc, method.desc)) {
                break;
            }
        }
        MethodNode methodNode2 = (MethodNode) next;
        if (methodNode2 != null) {
            return INSTANCE.findFakeContinuationConstructorClassName(methodNode2);
        }
        return null;
    }

    /* JADX INFO: compiled from: CoroutineTransformer.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/coroutines/CoroutineTransformer$Companion;", "", "()V", "findFakeContinuationConstructorClassName", "", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String findFakeContinuationConstructorClassName(MethodNode node) {
            AbstractInsnNode next;
            Intrinsics.checkNotNullParameter(node, "node");
            InsnList instructions = node.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            Iterator<AbstractInsnNode> it = UtilKt.asSequence(instructions).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!InlineCodegenUtilsKt.isBeforeFakeContinuationConstructorCallMarker(next));
            AbstractInsnNode abstractInsnNode = next;
            if (abstractInsnNode == null) {
                return null;
            }
            AbstractInsnNode next2 = abstractInsnNode.getNext();
            if (next2 != null) {
                next2.getOpcode();
            }
            Intrinsics.checkNotNull(next2, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TypeInsnNode");
            return ((TypeInsnNode) next2).desc;
        }
    }
}
