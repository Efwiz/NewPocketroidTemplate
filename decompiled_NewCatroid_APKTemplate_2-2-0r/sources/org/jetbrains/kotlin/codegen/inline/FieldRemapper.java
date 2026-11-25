package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: FieldRemapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 *2\u00020\u0001:\u0001*B!\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J \u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\tH\u0014J\"\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00170\u001bH\u0017J(\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J\u001e\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001f2\u0006\u0010\"\u001a\u00020#J\u001c\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\"\u001a\u00020\u00192\b\u0010&\u001a\u0004\u0018\u00010%H\u0016J\u0010\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u0019H\u0014J\b\u0010)\u001a\u00020\tH\u0016R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\nR\u0011\u0010\u000b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0014\u0010\f\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0014\u0010\u0005\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00008\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;", "", "originalLambdaInternalName", "", "parent", "parameters", "Lorg/jetbrains/kotlin/codegen/inline/Parameters;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;Lorg/jetbrains/kotlin/codegen/inline/Parameters;)V", "isInsideInliningLambda", "", "()Z", "isRoot", "newLambdaInternalName", "getNewLambdaInternalName", "()Ljava/lang/String;", "getOriginalLambdaInternalName", "getParameters", "()Lorg/jetbrains/kotlin/codegen/inline/Parameters;", "canProcess", "fieldOwner", "fieldName", "isFolding", "findField", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamInfo;", "fieldInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", "captured", "", "foldFieldAccessChainIfNeeded", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "capturedFieldAccess", "", "currentInstruction", "", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "getFieldForInline", "Lorg/jetbrains/kotlin/codegen/StackValue;", "prefix", "getFieldNameForFolding", "insnNode", "shouldProcessNonAload0FieldAccessChains", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FieldRemapper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean isInsideInliningLambda;
    private final boolean isRoot;
    private final String originalLambdaInternalName;
    private final Parameters parameters;
    public final FieldRemapper parent;

    public final CapturedParamInfo findField(FieldInsnNode fieldInsnNode) {
        Intrinsics.checkNotNullParameter(fieldInsnNode, "fieldInsnNode");
        return findField$default(this, fieldInsnNode, null, 2, null);
    }

    public boolean shouldProcessNonAload0FieldAccessChains() {
        return false;
    }

    public FieldRemapper(String str, FieldRemapper fieldRemapper, Parameters parameters) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.originalLambdaInternalName = str;
        this.parent = fieldRemapper;
        this.parameters = parameters;
        this.isRoot = fieldRemapper == null;
        this.isInsideInliningLambda = fieldRemapper != null ? fieldRemapper.getIsInsideInliningLambda() : false;
    }

    public final String getOriginalLambdaInternalName() {
        return this.originalLambdaInternalName;
    }

    protected final Parameters getParameters() {
        return this.parameters;
    }

    /* JADX INFO: renamed from: isRoot, reason: from getter */
    public final boolean getIsRoot() {
        return this.isRoot;
    }

    /* JADX INFO: renamed from: isInsideInliningLambda, reason: from getter */
    public boolean getIsInsideInliningLambda() {
        return this.isInsideInliningLambda;
    }

    protected boolean canProcess(String fieldOwner, String fieldName, boolean isFolding) {
        Intrinsics.checkNotNullParameter(fieldOwner, "fieldOwner");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        return Intrinsics.areEqual(fieldOwner, this.originalLambdaInternalName) && InlineCodegenUtilsKt.isCapturedFieldName(fieldName);
    }

    public final AbstractInsnNode foldFieldAccessChainIfNeeded(List<? extends AbstractInsnNode> capturedFieldAccess, MethodNode node) {
        Intrinsics.checkNotNullParameter(capturedFieldAccess, "capturedFieldAccess");
        Intrinsics.checkNotNullParameter(node, "node");
        if (capturedFieldAccess.size() == 1) {
            return null;
        }
        return foldFieldAccessChainIfNeeded(capturedFieldAccess, 1, node);
    }

    private final AbstractInsnNode foldFieldAccessChainIfNeeded(List<? extends AbstractInsnNode> capturedFieldAccess, int currentInstruction, MethodNode node) {
        FieldRemapper fieldRemapper;
        AbstractInsnNode abstractInsnNodeFoldFieldAccessChainIfNeeded;
        if (currentInstruction < CollectionsKt.getLastIndex(capturedFieldAccess) && (fieldRemapper = this.parent) != null && (abstractInsnNodeFoldFieldAccessChainIfNeeded = fieldRemapper.foldFieldAccessChainIfNeeded(capturedFieldAccess, currentInstruction + 1, node)) != null) {
            return abstractInsnNodeFoldFieldAccessChainIfNeeded;
        }
        AbstractInsnNode abstractInsnNode = capturedFieldAccess.get(currentInstruction);
        Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.FieldInsnNode");
        FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
        String owner = fieldInsnNode.owner;
        Intrinsics.checkNotNullExpressionValue(owner, "owner");
        String name2 = fieldInsnNode.f1010name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        if (!canProcess(owner, name2, true)) {
            return null;
        }
        fieldInsnNode.f1010name = INSTANCE.foldName(getFieldNameForFolding(fieldInsnNode));
        fieldInsnNode.setOpcode(178);
        MethodInlinerUtilKt.remove(node, new InsnSequence(capturedFieldAccess.get(0), fieldInsnNode));
        return capturedFieldAccess.get(capturedFieldAccess.size() - 1);
    }

    protected String getFieldNameForFolding(FieldInsnNode insnNode) {
        Intrinsics.checkNotNullParameter(insnNode, "insnNode");
        String name2 = insnNode.f1010name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        return name2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CapturedParamInfo findField$default(FieldRemapper fieldRemapper, FieldInsnNode fieldInsnNode, Collection collection, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: findField");
        }
        if ((i & 2) != 0) {
            collection = fieldRemapper.parameters.getCaptured();
        }
        return fieldRemapper.findField(fieldInsnNode, collection);
    }

    public CapturedParamInfo findField(FieldInsnNode fieldInsnNode, Collection<CapturedParamInfo> captured) {
        Intrinsics.checkNotNullParameter(fieldInsnNode, "fieldInsnNode");
        Intrinsics.checkNotNullParameter(captured, "captured");
        for (CapturedParamInfo capturedParamInfo : captured) {
            if (Intrinsics.areEqual(capturedParamInfo.getOriginalFieldName(), fieldInsnNode.f1010name) && Intrinsics.areEqual(capturedParamInfo.getContainingLambdaName(), fieldInsnNode.owner)) {
                return capturedParamInfo;
            }
        }
        return null;
    }

    public String getNewLambdaInternalName() {
        String str = this.originalLambdaInternalName;
        Intrinsics.checkNotNull(str);
        return str;
    }

    public StackValue getFieldForInline(FieldInsnNode node, StackValue prefix) {
        Intrinsics.checkNotNullParameter(node, "node");
        return MethodInliner.Companion.findCapturedField(node, this).getRemapValue();
    }

    /* JADX INFO: compiled from: FieldRemapper.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper$Companion;", "", "()V", "foldName", "", "fieldName", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String foldName(String fieldName) {
            Intrinsics.checkNotNullParameter(fieldName, "fieldName");
            return InlineCodegenUtilsKt.CAPTURED_FIELD_FOLD_PREFIX + fieldName;
        }
    }
}
