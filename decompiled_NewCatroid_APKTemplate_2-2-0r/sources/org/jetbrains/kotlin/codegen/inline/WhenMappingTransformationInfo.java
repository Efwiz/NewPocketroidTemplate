package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;

/* JADX INFO: compiled from: TransformationInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u0014\u001a\u00020\u0007H\u0016J&\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00072\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u0007H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u00020\u00058VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/WhenMappingTransformationInfo;", "Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;", "oldClassName", "", "parentNameGenerator", "Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "alreadyRegenerated", "", "fieldNode", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;ZLorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;)V", "getFieldNode", "()Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", "nameGenerator", "getNameGenerator", "()Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "nameGenerator$delegate", "Lkotlin/Lazy;", "getOldClassName", "()Ljava/lang/String;", "canRemoveAfterTransformation", "createTransformer", "Lorg/jetbrains/kotlin/codegen/inline/ObjectTransformer;", "inliningContext", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "sameModule", "continuationClassName", "shouldRegenerate", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WhenMappingTransformationInfo implements TransformationInfo {
    public static final String TRANSFORMED_WHEN_MAPPING_MARKER = "$wm$";
    private final boolean alreadyRegenerated;
    private final FieldInsnNode fieldNode;

    /* JADX INFO: renamed from: nameGenerator$delegate, reason: from kotlin metadata */
    private final Lazy nameGenerator;
    private final String oldClassName;

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public boolean canRemoveAfterTransformation() {
        return true;
    }

    public WhenMappingTransformationInfo(String oldClassName, final NameGenerator parentNameGenerator, boolean z, FieldInsnNode fieldNode) {
        Intrinsics.checkNotNullParameter(oldClassName, "oldClassName");
        Intrinsics.checkNotNullParameter(parentNameGenerator, "parentNameGenerator");
        Intrinsics.checkNotNullParameter(fieldNode, "fieldNode");
        this.oldClassName = oldClassName;
        this.alreadyRegenerated = z;
        this.fieldNode = fieldNode;
        this.nameGenerator = LazyKt.lazy(new Function0<NameGenerator>() { // from class: org.jetbrains.kotlin.codegen.inline.WhenMappingTransformationInfo$nameGenerator$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final NameGenerator invoke() {
                return parentNameGenerator.subGenerator(false, StringsKt.substringAfterLast$default(StringsKt.substringAfterLast$default(this.getOldClassName(), "/", (String) null, 2, (Object) null), WhenMappingTransformationInfo.TRANSFORMED_WHEN_MAPPING_MARKER, (String) null, 2, (Object) null));
            }
        });
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public String getOldClassName() {
        return this.oldClassName;
    }

    public final FieldInsnNode getFieldNode() {
        return this.fieldNode;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public NameGenerator getNameGenerator() {
        return (NameGenerator) this.nameGenerator.getValue();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public boolean shouldRegenerate(boolean sameModule) {
        return (this.alreadyRegenerated || sameModule) ? false : true;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public ObjectTransformer<?> createTransformer(InliningContext inliningContext, boolean sameModule, String continuationClassName) {
        Intrinsics.checkNotNullParameter(inliningContext, "inliningContext");
        return new WhenMappingTransformer(this, inliningContext);
    }
}
