package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SamWrapperTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0011\u001a\u00020\u0007H\u0016J\"\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00072\b\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0007H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SamWrapperTransformationInfo;", "Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;", "oldClassName", "", "inliningContext", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "alreadyRegenerated", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/codegen/inline/InliningContext;Z)V", "nameGenerator", "Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "getNameGenerator", "()Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "newClassName", "getNewClassName", "()Ljava/lang/String;", "getOldClassName", "canRemoveAfterTransformation", "createTransformer", "Lorg/jetbrains/kotlin/codegen/inline/SamWrapperTransformer;", "sameModule", "continuationClassName", "shouldRegenerate", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamWrapperTransformationInfo implements TransformationInfo {
    private final boolean alreadyRegenerated;
    private final InliningContext inliningContext;
    private final String oldClassName;

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public boolean canRemoveAfterTransformation() {
        return false;
    }

    public SamWrapperTransformationInfo(String oldClassName, InliningContext inliningContext, boolean z) {
        Intrinsics.checkNotNullParameter(oldClassName, "oldClassName");
        Intrinsics.checkNotNullParameter(inliningContext, "inliningContext");
        this.oldClassName = oldClassName;
        this.inliningContext = inliningContext;
        this.alreadyRegenerated = z;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public String getOldClassName() {
        return this.oldClassName;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public NameGenerator getNameGenerator() {
        return new NameGenerator() { // from class: org.jetbrains.kotlin.codegen.inline.SamWrapperTransformationInfo$nameGenerator$1
            {
                super("stub");
            }

            @Override // org.jetbrains.kotlin.codegen.inline.NameGenerator
            public String getGeneratorClass() {
                throw new IllegalStateException(("Shouldn't be called on " + this.this$0.getOldClassName() + " transformation").toString());
            }

            @Override // org.jetbrains.kotlin.codegen.inline.NameGenerator
            public NameGenerator subGenerator(String inliningMethod) {
                throw new IllegalStateException(("Shouldn't be called on " + this.this$0.getOldClassName() + " transformation").toString());
            }

            @Override // org.jetbrains.kotlin.codegen.inline.NameGenerator
            public NameGenerator subGenerator(boolean lambdaNoWhen, String nameSuffix) {
                throw new IllegalStateException(("Shouldn't be called on " + this.this$0.getOldClassName() + " transformation").toString());
            }
        };
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public String getNewClassName() {
        return this.inliningContext.getRoot().getCallSiteInfo().getOwnerClassName() + InlineCodegenUtilsKt.INLINE_TRANSFORMATION_SUFFIX + ("$sam$" + StringsKt.substringAfter$default(getOldClassName(), "$sam$", (String) null, 2, (Object) null));
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public boolean shouldRegenerate(boolean sameModule) {
        return (sameModule || this.alreadyRegenerated) ? false : true;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public SamWrapperTransformer createTransformer(InliningContext inliningContext, boolean sameModule, String continuationClassName) {
        Intrinsics.checkNotNullParameter(inliningContext, "inliningContext");
        return new SamWrapperTransformer(this, inliningContext);
    }
}
