package org.jetbrains.kotlin.codegen.extensions;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.ImplementationBodyCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: ExpressionCodegenExtension.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \u00122\u00020\u0001:\u0002\u0012\u0013J&\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u00072\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J&\u0010\r\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u00072\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/extensions/ExpressionCodegenExtension;", "", "shouldGenerateClassSyntheticPartsInLightClassesMode", "", "getShouldGenerateClassSyntheticPartsInLightClassesMode", "()Z", "applyFunction", "Lorg/jetbrains/kotlin/codegen/StackValue;", "receiver", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "c", "Lorg/jetbrains/kotlin/codegen/extensions/ExpressionCodegenExtension$Context;", "applyProperty", "generateClassSyntheticParts", "", "codegen", "Lorg/jetbrains/kotlin/codegen/ImplementationBodyCodegen;", "Companion", "Context", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ExpressionCodegenExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    default StackValue applyFunction(StackValue receiver, ResolvedCall<?> resolvedCall, Context c) {
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(c, "c");
        return null;
    }

    default StackValue applyProperty(StackValue receiver, ResolvedCall<?> resolvedCall, Context c) {
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(c, "c");
        return null;
    }

    default void generateClassSyntheticParts(ImplementationBodyCodegen codegen) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
    }

    default boolean getShouldGenerateClassSyntheticPartsInLightClassesMode() {
        return false;
    }

    /* JADX INFO: compiled from: ExpressionCodegenExtension.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/codegen/extensions/ExpressionCodegenExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/codegen/extensions/ExpressionCodegenExtension;", "()V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<ExpressionCodegenExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.expressionCodegenExtension", ExpressionCodegenExtension.class);
        }
    }

    /* JADX INFO: compiled from: ExpressionCodegenExtension.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/extensions/ExpressionCodegenExtension$Context;", "", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;)V", "getCodegen", "()Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "getTypeMapper", "()Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "getV", "()Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Context {
        private final ExpressionCodegen codegen;
        private final KotlinTypeMapper typeMapper;
        private final InstructionAdapter v;

        public Context(ExpressionCodegen codegen, KotlinTypeMapper typeMapper, InstructionAdapter v) {
            Intrinsics.checkNotNullParameter(codegen, "codegen");
            Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
            Intrinsics.checkNotNullParameter(v, "v");
            this.codegen = codegen;
            this.typeMapper = typeMapper;
            this.v = v;
        }

        public final ExpressionCodegen getCodegen() {
            return this.codegen;
        }

        public final KotlinTypeMapper getTypeMapper() {
            return this.typeMapper;
        }

        public final InstructionAdapter getV() {
            return this.v;
        }
    }
}
