package org.jetbrains.kotlin.codegen.context;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: CodegenContextUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0007J\u001c\u0010\u0007\u001a\u00020\b2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\t\u001a\u00020\nH\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/context/CodegenContextUtil;", "", "()V", "getImplementationOwnerClassType", "Lorg/jetbrains/org/objectweb/asm/Type;", "owner", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "isImplementationOwner", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CodegenContextUtil {
    public static final CodegenContextUtil INSTANCE = new CodegenContextUtil();

    private CodegenContextUtil() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JvmStatic
    public static final Type getImplementationOwnerClassType(CodegenContext<?> owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        if (owner instanceof MultifileClassFacadeContext) {
            return ((MultifileClassFacadeContext) owner).getFilePartType();
        }
        if (owner instanceof DelegatingToPartContext) {
            return ((DelegatingToPartContext) owner).getImplementationOwnerClassType();
        }
        return null;
    }

    @JvmStatic
    public static final boolean isImplementationOwner(CodegenContext<?> owner, DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor instanceof CallableDescriptor) {
            CallableDescriptor callableDescriptor = (CallableDescriptor) descriptor;
            DeclarationDescriptor containingDeclaration = callableDescriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (InlineClassesUtilsKt.isInlineClass(containingDeclaration)) {
                boolean z = owner.getContextKind() == OwnerKind.ERASED_INLINE_CLASS;
                if (InlineClassesUtilsKt.isGetterOfUnderlyingPropertyOfInlineClass(callableDescriptor)) {
                    return !z;
                }
                if (descriptor instanceof FunctionDescriptor) {
                    return z;
                }
                if (descriptor instanceof PropertyDescriptor) {
                    return !z;
                }
            }
        }
        return !(owner instanceof MultifileClassFacadeContext);
    }
}
