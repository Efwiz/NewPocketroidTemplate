package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: compiled from: generateJava8ParameterNames.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a.\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b¨\u0006\f"}, d2 = {"generateParameterNames", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "jvmSignature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "isSynthetic", "", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class GenerateJava8ParameterNamesKt {

    /* JADX INFO: compiled from: generateJava8ParameterNames.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JvmMethodParameterKind.values().length];
            try {
                iArr[JvmMethodParameterKind.ENUM_NAME_OR_ORDINAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JvmMethodParameterKind.RECEIVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JvmMethodParameterKind.CONTEXT_RECEIVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[JvmMethodParameterKind.OUTER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[JvmMethodParameterKind.VALUE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[JvmMethodParameterKind.CONSTRUCTOR_MARKER.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[JvmMethodParameterKind.SUPER_CALL_PARAM.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[JvmMethodParameterKind.CAPTURED_LOCAL_VARIABLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[JvmMethodParameterKind.THIS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final void generateParameterNames(FunctionDescriptor functionDescriptor, MethodVisitor mv, JvmMethodSignature jvmSignature, GenerationState state, boolean z) {
        String nameForReceiverParameter;
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(mv, "mv");
        Intrinsics.checkNotNullParameter(jvmSignature, "jvmSignature");
        Intrinsics.checkNotNullParameter(state, "state");
        if (!state.getConfig().getGenerateParametersMetadata() || z) {
            return;
        }
        Iterator<ValueParameterDescriptor> it = functionDescriptor.getValueParameters().iterator();
        List<JvmMethodParameterSignature> valueParameters = jvmSignature.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        Iterator<JvmMethodParameterSignature> it2 = valueParameters.iterator();
        boolean z2 = true;
        int i = 0;
        while (it2.hasNext()) {
            int i2 = i + 1;
            JvmMethodParameterKind kind = it2.next().getKind();
            Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
            switch (WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                case 1:
                    boolean z3 = !z2;
                    nameForReceiverParameter = z2 ? "$enum$name" : "$enum$ordinal";
                    z2 = z3;
                    break;
                case 2:
                case 3:
                    nameForReceiverParameter = DescriptorAsmUtil.getNameForReceiverParameter(functionDescriptor, state.getBindingContext(), state.getLanguageVersionSettings());
                    Intrinsics.checkNotNull(nameForReceiverParameter);
                    break;
                case 4:
                    nameForReceiverParameter = AsmUtil.CAPTURED_THIS_FIELD;
                    break;
                case 5:
                    nameForReceiverParameter = it.next().getShortName().asString();
                    Intrinsics.checkNotNullExpressionValue(nameForReceiverParameter, "asString(...)");
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                    nameForReceiverParameter = ParameterDescription.NAME_PREFIX + i;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            int i3 = 32768;
            switch (WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                case 1:
                case 6:
                case 7:
                case 8:
                case 9:
                    i3 = 4096;
                    break;
                case 2:
                case 3:
                case 4:
                    break;
                case 5:
                    i3 = 0;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            mv.visitParameter(nameForReceiverParameter, i3);
            i = i2;
        }
    }
}
