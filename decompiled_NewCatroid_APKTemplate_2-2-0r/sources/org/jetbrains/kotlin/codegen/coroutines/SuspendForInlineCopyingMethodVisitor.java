package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.TransformationMethodVisitor;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformerKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: SuspendFunctionGenerationStrategy.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001Bk\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012:\u0010\t\u001a6\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\f\u0012\u0004\u0012\u00020\u00030\n\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0014R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000RB\u0010\t\u001a6\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\f\u0012\u0004\u0012\u00020\u00030\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/SuspendForInlineCopyingMethodVisitor;", "Lorg/jetbrains/kotlin/codegen/TransformationMethodVisitor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "access", "", "name", "", "desc", "newMethod", "Lkotlin/Function6;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "", "keepAccess", "", "(Lorg/jetbrains/org/objectweb/asm/MethodVisitor;ILjava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function6;Z)V", "performTransformations", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuspendForInlineCopyingMethodVisitor extends TransformationMethodVisitor {
    private final boolean keepAccess;
    private final Function6<JvmDeclarationOrigin, Integer, String, String, String, String[], MethodVisitor> newMethod;

    public /* synthetic */ SuspendForInlineCopyingMethodVisitor(MethodVisitor methodVisitor, int i, String str, String str2, Function6 function6, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(methodVisitor, i, str, str2, function6, (i2 & 32) != 0 ? true : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SuspendForInlineCopyingMethodVisitor(MethodVisitor delegate, int i, String name2, String desc, Function6<? super JvmDeclarationOrigin, ? super Integer, ? super String, ? super String, ? super String, ? super String[], ? extends MethodVisitor> newMethod, boolean z) {
        super(delegate, i, name2, desc, null, null, 0, 64, null);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        Intrinsics.checkNotNullParameter(newMethod, "newMethod");
        this.newMethod = newMethod;
        this.keepAccess = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // org.jetbrains.kotlin.codegen.TransformationMethodVisitor
    protected void performTransformations(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        int i = this.keepAccess ? methodNode.access : (methodNode.access | 2) & (-6);
        String str = methodNode.f1016name + CoroutineTransformerKt.FOR_INLINE_SUFFIX;
        String str2 = methodNode.desc;
        String str3 = methodNode.signature;
        List<String> exceptions = methodNode.exceptions;
        Intrinsics.checkNotNullExpressionValue(exceptions, "exceptions");
        MethodNode methodNode2 = new MethodNode(i, str, str2, str3, (String[]) exceptions.toArray(new String[0]));
        Function6<JvmDeclarationOrigin, Integer, String, String, String, String[], MethodVisitor> function6 = this.newMethod;
        JvmDeclarationOrigin jvmDeclarationOrigin = JvmDeclarationOrigin.NO_ORIGIN;
        Integer numValueOf = Integer.valueOf(methodNode2.access);
        String name2 = methodNode2.f1016name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        String desc = methodNode2.desc;
        Intrinsics.checkNotNullExpressionValue(desc, "desc");
        String str4 = methodNode2.signature;
        List<String> exceptions2 = methodNode2.exceptions;
        Intrinsics.checkNotNullExpressionValue(exceptions2, "exceptions");
        MethodVisitor methodVisitor = (MethodVisitor) function6.invoke(jvmDeclarationOrigin, numValueOf, name2, desc, str4, exceptions2.toArray(new String[0]));
        methodNode.instructions.resetLabels();
        methodNode.accept(methodNode2);
        InlineCodegenUtilsKt.preprocessSuspendMarkers(methodNode, false, false);
        InlineCodegenUtilsKt.preprocessSuspendMarkers(methodNode2, true, true);
        methodNode2.accept(methodVisitor);
    }
}
