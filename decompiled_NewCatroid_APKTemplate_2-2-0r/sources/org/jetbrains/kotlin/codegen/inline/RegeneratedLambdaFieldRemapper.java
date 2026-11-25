package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;

/* JADX INFO: compiled from: RegeneratedLambdaFieldRemapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0010\n\u001a\u00020\u0001\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ \u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\fH\u0016J \u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00170\u001bH\u0016J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\u00192\b\u0010 \u001a\u0004\u0018\u00010\u001eH\u0016J\u0018\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\fH\u0002J\b\u0010#\u001a\u00020\fH\u0016R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/RegeneratedLambdaFieldRemapper;", "Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;", "originalLambdaInternalName", "", "newLambdaInternalName", "parameters", "Lorg/jetbrains/kotlin/codegen/inline/Parameters;", "recapturedLambdas", "", "Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;", "remapper", "isConstructor", "", "(Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/codegen/inline/Parameters;Ljava/util/Map;Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;Z)V", "getNewLambdaInternalName", "()Ljava/lang/String;", "getRecapturedLambdas", "()Ljava/util/Map;", "canProcess", "fieldOwner", "fieldName", "isFolding", "findField", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamInfo;", "fieldInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", "captured", "", "findFieldInSuper", "getFieldForInline", "Lorg/jetbrains/kotlin/codegen/StackValue;", "node", "prefix", "isRecapturedLambdaType", "owner", "shouldProcessNonAload0FieldAccessChains", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RegeneratedLambdaFieldRemapper extends FieldRemapper {
    private final boolean isConstructor;
    private final String newLambdaInternalName;
    private final Map<String, LambdaInfo> recapturedLambdas;

    @Override // org.jetbrains.kotlin.codegen.inline.FieldRemapper
    public String getNewLambdaInternalName() {
        return this.newLambdaInternalName;
    }

    public final Map<String, LambdaInfo> getRecapturedLambdas() {
        return this.recapturedLambdas;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public RegeneratedLambdaFieldRemapper(String originalLambdaInternalName, String newLambdaInternalName, Parameters parameters, Map<String, ? extends LambdaInfo> recapturedLambdas, FieldRemapper remapper, boolean z) {
        super(originalLambdaInternalName, remapper, parameters);
        Intrinsics.checkNotNullParameter(originalLambdaInternalName, "originalLambdaInternalName");
        Intrinsics.checkNotNullParameter(newLambdaInternalName, "newLambdaInternalName");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(recapturedLambdas, "recapturedLambdas");
        Intrinsics.checkNotNullParameter(remapper, "remapper");
        this.newLambdaInternalName = newLambdaInternalName;
        this.recapturedLambdas = recapturedLambdas;
        this.isConstructor = z;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.FieldRemapper
    public boolean canProcess(String fieldOwner, String fieldName, boolean isFolding) {
        Intrinsics.checkNotNullParameter(fieldOwner, "fieldOwner");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        return super.canProcess(fieldOwner, fieldName, isFolding) || isRecapturedLambdaType(fieldOwner, isFolding);
    }

    private final boolean isRecapturedLambdaType(String owner, boolean isFolding) {
        return this.recapturedLambdas.containsKey(owner) && (isFolding || !(this.parent instanceof InlinedLambdaRemapper));
    }

    @Override // org.jetbrains.kotlin.codegen.inline.FieldRemapper
    public CapturedParamInfo findField(FieldInsnNode fieldInsnNode, Collection<CapturedParamInfo> captured) {
        Intrinsics.checkNotNullParameter(fieldInsnNode, "fieldInsnNode");
        Intrinsics.checkNotNullParameter(captured, "captured");
        String owner = fieldInsnNode.owner;
        Intrinsics.checkNotNullExpressionValue(owner, "owner");
        String name2 = fieldInsnNode.f1010name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        if (!canProcess(owner, name2, false)) {
            FieldRemapper fieldRemapper = this.parent;
            Intrinsics.checkNotNull(fieldRemapper);
            return FieldRemapper.findField$default(fieldRemapper, fieldInsnNode, null, 2, null);
        }
        return findFieldInSuper(fieldInsnNode);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.FieldRemapper
    /* JADX INFO: renamed from: shouldProcessNonAload0FieldAccessChains, reason: from getter */
    public boolean getIsConstructor() {
        return this.isConstructor;
    }

    private final CapturedParamInfo findFieldInSuper(FieldInsnNode fieldInsnNode) {
        return super.findField(fieldInsnNode, getParameters().getCaptured());
    }

    @Override // org.jetbrains.kotlin.codegen.inline.FieldRemapper
    public StackValue getFieldForInline(FieldInsnNode node, StackValue prefix) {
        boolean z;
        Type type;
        Intrinsics.checkNotNullParameter(node, "node");
        String str = node.f1010name;
        Intrinsics.checkNotNull(str);
        CapturedParamInfo capturedParamInfo = null;
        StringsKt.startsWith$default(str, InlineCodegenUtilsKt.CAPTURED_FIELD_FOLD_PREFIX, false, 2, (Object) null);
        if (Intrinsics.areEqual(str, "$$$this")) {
            Intrinsics.areEqual(getOriginalLambdaInternalName(), node.owner);
            return StackValue.LOCAL_0;
        }
        CapturedParamInfo capturedParamInfoFindFieldInSuper = findFieldInSuper(new FieldInsnNode(node.getOpcode(), node.owner, StringsKt.substringAfter$default(str, InlineCodegenUtilsKt.CAPTURED_FIELD_FOLD_PREFIX, (String) null, 2, (Object) null), node.desc));
        if (capturedParamInfoFindFieldInSuper == null) {
            String originalLambdaInternalName = getOriginalLambdaInternalName();
            FieldRemapper fieldRemapper = this.parent;
            Intrinsics.checkNotNull(fieldRemapper);
            String originalLambdaInternalName2 = fieldRemapper.getOriginalLambdaInternalName();
            Intrinsics.checkNotNull(originalLambdaInternalName2);
            CapturedParamInfo capturedParamInfoFindFieldInSuper2 = findFieldInSuper(new FieldInsnNode(178, originalLambdaInternalName, AsmUtil.CAPTURED_THIS_FIELD, Type.getObjectType(originalLambdaInternalName2).getDescriptor()));
            if (capturedParamInfoFindFieldInSuper2 != null) {
                z = true;
                capturedParamInfo = capturedParamInfoFindFieldInSuper2;
            } else {
                z = false;
            }
            if (capturedParamInfo == null) {
                throw new AssertionError("Couldn't find captured this " + getOriginalLambdaInternalName() + " for " + str);
            }
            capturedParamInfoFindFieldInSuper = capturedParamInfo;
        } else {
            z = false;
        }
        if (capturedParamInfoFindFieldInSuper.getIsSkipped() && (capturedParamInfoFindFieldInSuper.getFunctionalArgument() instanceof LambdaInfo)) {
            FieldRemapper fieldRemapper2 = this.parent;
            Intrinsics.checkNotNull(fieldRemapper2);
            FieldRemapper fieldRemapper3 = fieldRemapper2.parent;
            Intrinsics.checkNotNull(fieldRemapper3);
            type = Type.getObjectType(fieldRemapper3.getNewLambdaInternalName());
        } else {
            type = capturedParamInfoFindFieldInSuper.getType();
        }
        Type objectType = Type.getObjectType(getNewLambdaInternalName());
        String newFieldName = capturedParamInfoFindFieldInSuper.getNewFieldName();
        if (prefix == null) {
            prefix = StackValue.LOCAL_0;
        }
        StackValue.Field field = StackValue.field(type, objectType, newFieldName, false, prefix);
        Intrinsics.checkNotNullExpressionValue(field, "field(...)");
        if (!z) {
            return field;
        }
        FieldRemapper fieldRemapper4 = this.parent;
        Intrinsics.checkNotNull(fieldRemapper4);
        return fieldRemapper4.getFieldForInline(node, field);
    }
}
