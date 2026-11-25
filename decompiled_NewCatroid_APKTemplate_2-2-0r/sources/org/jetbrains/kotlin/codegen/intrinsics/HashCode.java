package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: HashCode.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/HashCode;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HashCode extends IntrinsicMethod {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(CallableMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        Type dispatchReceiverType = method.getDispatchReceiverType();
        if (dispatchReceiverType == null && (dispatchReceiverType = method.getExtensionReceiverType()) == null) {
            throw new IllegalStateException(("No receiver for callable: " + method).toString());
        }
        Type type = dispatchReceiverType;
        boolean zIsPrimitive = AsmUtil.isPrimitive(type);
        boolean z = !zIsPrimitive;
        Type type2 = Type.INT_TYPE;
        List listEmptyList = CollectionsKt.emptyList();
        Type dispatchReceiverType2 = method.getDispatchReceiverType();
        if (!zIsPrimitive) {
            dispatchReceiverType2 = nullOrObject(dispatchReceiverType2);
        }
        Type type3 = dispatchReceiverType2;
        Type extensionReceiverType = method.getExtensionReceiverType();
        if (!zIsPrimitive) {
            extensionReceiverType = nullOrObject(extensionReceiverType);
        }
        return new IntrinsicCallable(z, type, type2, listEmptyList, type3, extensionReceiverType) { // from class: org.jetbrains.kotlin.codegen.intrinsics.HashCode.toCallable.1
            final /* synthetic */ Type $receiverType;
            final /* synthetic */ boolean $useObjectHashCode;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(type2, listEmptyList, type3, extensionReceiverType, null, 16, null);
                Intrinsics.checkNotNull(type2);
            }

            @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable
            public void invokeIntrinsic(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                Companion companion = HashCode.INSTANCE;
                Type type4 = this.$useObjectHashCode ? AsmTypes.OBJECT_TYPE : this.$receiverType;
                Intrinsics.checkNotNull(type4);
                companion.invokeHashCode(v, type4);
            }
        };
    }

    /* JADX INFO: compiled from: HashCode.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/HashCode$Companion;", "", "()V", "invokeHashCode", "", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void invokeHashCode(InstructionAdapter instructionAdapter, Type type) {
            Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
            Intrinsics.checkNotNullParameter(type, "type");
            if (AsmUtil.isPrimitive(type)) {
                Type typeBoxType = AsmUtil.boxType(type);
                Intrinsics.checkNotNullExpressionValue(typeBoxType, "boxType(...)");
                instructionAdapter.visitMethodInsn(184, typeBoxType.getInternalName(), "hashCode", Type.getMethodDescriptor(Type.INT_TYPE, type), false);
                return;
            }
            instructionAdapter.visitMethodInsn(182, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "hashCode", "()I", false);
        }
    }
}
