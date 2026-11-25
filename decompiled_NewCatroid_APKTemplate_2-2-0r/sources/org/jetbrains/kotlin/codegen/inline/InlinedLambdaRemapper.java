package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;

/* JADX INFO: compiled from: InlinedLambdaRemapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ \u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\bH\u0016J \u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u0015H\u0016J\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00132\b\u0010\u0019\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0013H\u0014J\u0018\u0010\u001c\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\bX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InlinedLambdaRemapper;", "Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;", "originalLambdaInternalName", "", "parent", "methodParams", "Lorg/jetbrains/kotlin/codegen/inline/Parameters;", "isDefaultBoundCallableReference", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;Lorg/jetbrains/kotlin/codegen/inline/Parameters;Z)V", "isInsideInliningLambda", "()Z", "canProcess", "fieldOwner", "fieldName", "isFolding", "findField", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamInfo;", "fieldInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", "captured", "", "getFieldForInline", "Lorg/jetbrains/kotlin/codegen/StackValue;", "node", "prefix", "getFieldNameForFolding", "insnNode", "isMyBoundReceiverForDefaultLambda", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlinedLambdaRemapper extends FieldRemapper {
    private final boolean isDefaultBoundCallableReference;
    private final boolean isInsideInliningLambda;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InlinedLambdaRemapper(String originalLambdaInternalName, FieldRemapper parent, Parameters methodParams, boolean z) {
        super(originalLambdaInternalName, parent, methodParams);
        Intrinsics.checkNotNullParameter(originalLambdaInternalName, "originalLambdaInternalName");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(methodParams, "methodParams");
        this.isDefaultBoundCallableReference = z;
        this.isInsideInliningLambda = true;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.FieldRemapper
    public boolean canProcess(String fieldOwner, String fieldName, boolean isFolding) {
        Intrinsics.checkNotNullParameter(fieldOwner, "fieldOwner");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        return isFolding && (isMyBoundReceiverForDefaultLambda(fieldOwner, fieldName) || super.canProcess(fieldOwner, fieldName, true));
    }

    private final boolean isMyBoundReceiverForDefaultLambda(String fieldOwner, String fieldName) {
        return this.isDefaultBoundCallableReference && Intrinsics.areEqual(fieldName, "receiver") && Intrinsics.areEqual(fieldOwner, getOriginalLambdaInternalName());
    }

    @Override // org.jetbrains.kotlin.codegen.inline.FieldRemapper
    protected String getFieldNameForFolding(FieldInsnNode insnNode) {
        Intrinsics.checkNotNullParameter(insnNode, "insnNode");
        String owner = insnNode.owner;
        Intrinsics.checkNotNullExpressionValue(owner, "owner");
        String name2 = insnNode.f1010name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        if (isMyBoundReceiverForDefaultLambda(owner, name2)) {
            return AsmUtil.RECEIVER_PARAMETER_NAME;
        }
        String name3 = insnNode.f1010name;
        Intrinsics.checkNotNullExpressionValue(name3, "name");
        return name3;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.FieldRemapper
    public CapturedParamInfo findField(FieldInsnNode fieldInsnNode, Collection<CapturedParamInfo> captured) {
        Intrinsics.checkNotNullParameter(fieldInsnNode, "fieldInsnNode");
        Intrinsics.checkNotNullParameter(captured, "captured");
        FieldRemapper fieldRemapper = this.parent;
        Intrinsics.checkNotNull(fieldRemapper);
        return fieldRemapper.findField(fieldInsnNode, captured);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.FieldRemapper
    /* JADX INFO: renamed from: isInsideInliningLambda, reason: from getter */
    public boolean getIsInsideInliningLambda() {
        return this.isInsideInliningLambda;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.FieldRemapper
    public StackValue getFieldForInline(FieldInsnNode node, StackValue prefix) {
        Intrinsics.checkNotNullParameter(node, "node");
        FieldRemapper fieldRemapper = this.parent;
        Intrinsics.checkNotNull(fieldRemapper);
        if (fieldRemapper.getIsRoot()) {
            return super.getFieldForInline(node, prefix);
        }
        return this.parent.getFieldForInline(node, prefix);
    }
}
