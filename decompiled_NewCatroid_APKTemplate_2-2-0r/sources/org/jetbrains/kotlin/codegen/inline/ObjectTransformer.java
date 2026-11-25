package org.jetbrains.kotlin.codegen.inline;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.inline.TransformationInfo;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ObjectTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0004J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0015H&R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0012\u0010\u0004\u001a\u00028\u00008\u0006X\u0087\u0004¢\u0006\u0004\n\u0002\u0010\nR\u0010\u0010\u000b\u001a\u00020\f8\u0004X\u0085\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ObjectTransformer;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;", "", "transformationInfo", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;", "transformationResult", "Lorg/jetbrains/kotlin/codegen/inline/InlineResult;", "createClassReader", "Lorg/jetbrains/org/objectweb/asm/ClassReader;", "createRemappingClassBuilderViaFactory", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "inliningContext", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "doTransform", "parentRemapper", "Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ObjectTransformer<T extends TransformationInfo> {
    private final GenerationState state;
    public final T transformationInfo;
    protected final InlineResult transformationResult;

    public abstract InlineResult doTransform(FieldRemapper parentRemapper);

    public ObjectTransformer(T transformationInfo, GenerationState state) {
        Intrinsics.checkNotNullParameter(transformationInfo, "transformationInfo");
        Intrinsics.checkNotNullParameter(state, "state");
        this.transformationInfo = transformationInfo;
        this.state = state;
        this.transformationResult = InlineResult.INSTANCE.create();
    }

    public final GenerationState getState() {
        return this.state;
    }

    protected final ClassBuilder createRemappingClassBuilderViaFactory(InliningContext inliningContext) {
        Intrinsics.checkNotNullParameter(inliningContext, "inliningContext");
        ClassBuilder classBuilderNewVisitor = this.state.getFactory().newVisitor(JvmDeclarationOrigin.NO_ORIGIN, Type.getObjectType(this.transformationInfo.getNewClassName()), CollectionsKt.listOfNotNull(inliningContext.getCallSiteInfo().getFile()));
        Intrinsics.checkNotNullExpressionValue(classBuilderNewVisitor, "newVisitor(...)");
        return new RemappingClassBuilder(classBuilderNewVisitor, new AsmTypeRemapper(inliningContext.getTypeRemapper(), this.transformationResult));
    }

    public final ClassReader createClassReader() {
        return new ClassReader(InlineCodegenUtilsKt.loadClassBytesByInternalName(this.state, this.transformationInfo.getOldClassName()));
    }
}
