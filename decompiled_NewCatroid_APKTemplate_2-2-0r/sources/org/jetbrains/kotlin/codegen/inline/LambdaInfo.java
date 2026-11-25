package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;

/* JADX INFO: compiled from: LambdaInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 .2\u00020\u0001:\u0001.B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0007R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0011\u0010 \u001a\u00020!¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\"\u0010$\u001a\u0010\u0012\u0004\u0012\u00020&\u0012\u0006\u0012\u0004\u0018\u00010'0%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;", "Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;", "()V", "capturedVars", "", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;", "getCapturedVars", "()Ljava/util/List;", "hasDispatchReceiver", "", "getHasDispatchReceiver", "()Z", "invokeMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getInvokeMethod", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "invokeMethodParameters", "Lorg/jetbrains/kotlin/types/KotlinType;", "getInvokeMethodParameters", "invokeMethodReturnType", "getInvokeMethodReturnType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "lambdaClassType", "Lorg/jetbrains/org/objectweb/asm/Type;", "getLambdaClassType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "node", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "getNode", "()Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "setNode", "(Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;)V", "reifiedTypeParametersUsages", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "getReifiedTypeParametersUsages", "()Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "returnLabels", "", "", "Lorg/jetbrains/org/objectweb/asm/Label;", "getReturnLabels", "()Ljava/util/Map;", "addAllParameters", "Lorg/jetbrains/kotlin/codegen/inline/Parameters;", "remapper", "Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LambdaInfo implements FunctionalArgument {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public SMAPAndMethodNode node;
    private final ReifiedTypeParametersUsages reifiedTypeParametersUsages = new ReifiedTypeParametersUsages();

    public abstract List<CapturedParamDesc> getCapturedVars();

    public boolean getHasDispatchReceiver() {
        return true;
    }

    public abstract Method getInvokeMethod();

    public abstract List<KotlinType> getInvokeMethodParameters();

    public abstract KotlinType getInvokeMethodReturnType();

    public abstract Type getLambdaClassType();

    public Map<String, Label> getReturnLabels() {
        return MapsKt.emptyMap();
    }

    public final SMAPAndMethodNode getNode() {
        SMAPAndMethodNode sMAPAndMethodNode = this.node;
        if (sMAPAndMethodNode != null) {
            return sMAPAndMethodNode;
        }
        Intrinsics.throwUninitializedPropertyAccessException("node");
        return null;
    }

    public final void setNode(SMAPAndMethodNode sMAPAndMethodNode) {
        Intrinsics.checkNotNullParameter(sMAPAndMethodNode, "<set-?>");
        this.node = sMAPAndMethodNode;
    }

    public final ReifiedTypeParametersUsages getReifiedTypeParametersUsages() {
        return this.reifiedTypeParametersUsages;
    }

    public final Parameters addAllParameters(FieldRemapper remapper) {
        Intrinsics.checkNotNullParameter(remapper, "remapper");
        ParametersBuilder parametersBuilderNewBuilder = ParametersBuilder.INSTANCE.newBuilder();
        if (getHasDispatchReceiver()) {
            parametersBuilderNewBuilder.addThis(getLambdaClassType(), true).setFunctionalArgument(this);
        }
        Type[] argumentTypes = Type.getArgumentTypes(getInvokeMethod().getDescriptor());
        Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
        for (Type type : argumentTypes) {
            Intrinsics.checkNotNull(type);
            ParametersBuilder.addNextParameter$default(parametersBuilderNewBuilder, type, false, null, 4, null);
        }
        for (CapturedParamDesc capturedParamDesc : getCapturedVars()) {
            CapturedParamInfo capturedParamInfoFindField$default = FieldRemapper.findField$default(remapper, new FieldInsnNode(0, capturedParamDesc.getContainingLambdaName(), capturedParamDesc.getFieldName(), ""), null, 2, null);
            if (capturedParamInfoFindField$default == null) {
                throw new IllegalStateException(("Captured field not found: " + capturedParamDesc.getContainingLambdaName() + '.' + capturedParamDesc.getFieldName()).toString());
            }
            CapturedParamInfo capturedParamInfoAddCapturedParam = parametersBuilderNewBuilder.addCapturedParam(capturedParamInfoFindField$default, capturedParamDesc.getFieldName());
            if (capturedParamDesc.getIsSuspend()) {
                capturedParamInfoAddCapturedParam.setFunctionalArgument(NonInlineArgumentForInlineSuspendParameter.INLINE_LAMBDA_AS_VARIABLE);
            }
        }
        return parametersBuilderNewBuilder.buildParameters();
    }

    /* JADX INFO: compiled from: LambdaInfo.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo$Companion;", "", "()V", "capturedParamDesc", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;", "Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;", "fieldName", "", "fieldType", "Lorg/jetbrains/org/objectweb/asm/Type;", "isSuspend", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CapturedParamDesc capturedParamDesc(LambdaInfo lambdaInfo, String fieldName, Type fieldType, boolean z) {
            Intrinsics.checkNotNullParameter(lambdaInfo, "<this>");
            Intrinsics.checkNotNullParameter(fieldName, "fieldName");
            Intrinsics.checkNotNullParameter(fieldType, "fieldType");
            return new CapturedParamDesc(lambdaInfo.getLambdaClassType(), fieldName, fieldType, z);
        }
    }
}
