package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;

/* JADX INFO: compiled from: SamWrapperTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SamWrapperTransformer;", "Lorg/jetbrains/kotlin/codegen/inline/ObjectTransformer;", "Lorg/jetbrains/kotlin/codegen/inline/SamWrapperTransformationInfo;", "transformationInfo", "inliningContext", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "(Lorg/jetbrains/kotlin/codegen/inline/SamWrapperTransformationInfo;Lorg/jetbrains/kotlin/codegen/inline/InliningContext;)V", "doTransform", "Lorg/jetbrains/kotlin/codegen/inline/InlineResult;", "parentRemapper", "Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamWrapperTransformer extends ObjectTransformer<SamWrapperTransformationInfo> {
    private final InliningContext inliningContext;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SamWrapperTransformer(SamWrapperTransformationInfo transformationInfo, InliningContext inliningContext) {
        super(transformationInfo, inliningContext.getState());
        Intrinsics.checkNotNullParameter(transformationInfo, "transformationInfo");
        Intrinsics.checkNotNullParameter(inliningContext, "inliningContext");
        this.inliningContext = inliningContext;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ObjectTransformer
    public InlineResult doTransform(FieldRemapper parentRemapper) {
        Intrinsics.checkNotNullParameter(parentRemapper, "parentRemapper");
        ClassReader classReaderCreateClassReader = createClassReader();
        final ClassBuilder classBuilderCreateRemappingClassBuilderViaFactory = createRemappingClassBuilderViaFactory(this.inliningContext);
        classReaderCreateClassReader.accept(new ClassVisitor(classBuilderCreateRemappingClassBuilderViaFactory.getVisitor()) { // from class: org.jetbrains.kotlin.codegen.inline.SamWrapperTransformer.doTransform.1
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visit(int version, int access, String name2, String signature, String superName, String[] interfaces) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(superName, "superName");
                Intrinsics.checkNotNullParameter(interfaces, "interfaces");
                classBuilderCreateRemappingClassBuilderViaFactory.defineClass(null, Math.max(version, this.getState().getConfig().getClassFileVersion()), access, name2, signature, superName, interfaces);
            }
        }, 4);
        classBuilderCreateRemappingClassBuilderViaFactory.done(this.inliningContext.getState().getConfig().getGenerateSmapCopyToAnnotation());
        return this.transformationResult;
    }
}
