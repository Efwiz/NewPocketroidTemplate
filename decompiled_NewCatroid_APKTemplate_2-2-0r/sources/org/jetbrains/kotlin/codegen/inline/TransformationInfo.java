package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TransformationInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\f\u001a\u00020\rH&J&\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0007H&J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;", "", "nameGenerator", "Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "getNameGenerator", "()Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "newClassName", "", "getNewClassName", "()Ljava/lang/String;", "oldClassName", "getOldClassName", "canRemoveAfterTransformation", "", "createTransformer", "Lorg/jetbrains/kotlin/codegen/inline/ObjectTransformer;", "inliningContext", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "sameModule", "continuationClassName", "shouldRegenerate", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TransformationInfo {
    boolean canRemoveAfterTransformation();

    ObjectTransformer<?> createTransformer(InliningContext inliningContext, boolean sameModule, String continuationClassName);

    NameGenerator getNameGenerator();

    String getOldClassName();

    boolean shouldRegenerate(boolean sameModule);

    default String getNewClassName() {
        String generatorClass = getNameGenerator().getGeneratorClass();
        Intrinsics.checkNotNullExpressionValue(generatorClass, "getGeneratorClass(...)");
        return generatorClass;
    }
}
