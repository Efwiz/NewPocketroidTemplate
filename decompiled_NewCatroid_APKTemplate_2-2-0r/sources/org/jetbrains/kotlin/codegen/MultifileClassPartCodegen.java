package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.codegen.context.MultifileClassPartContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: MultifileClassPartCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B=\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0014J\b\u0010\u0018\u001a\u00020\u0016H\u0014J\b\u0010\u0019\u001a\u00020\u0016H\u0014J\b\u0010\u001a\u001a\u00020\u0016H\u0014R\u0016\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/MultifileClassPartCodegen;", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "Lorg/jetbrains/kotlin/psi/KtFile;", RegisterSpec.PREFIX, "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "file", "packageFragment", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "superClassInternalName", "", "shouldGeneratePartHierarchy", "", "partContext", "Lorg/jetbrains/kotlin/codegen/context/MultifileClassPartContext;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/ClassBuilder;Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;Ljava/lang/String;ZLorg/jetbrains/kotlin/codegen/context/MultifileClassPartContext;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "facadeClassType", "Lorg/jetbrains/org/objectweb/asm/Type;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "partType", CompilerOptions.GENERATE, "", "generateBody", "generateDeclaration", "generateKotlinMetadataAnnotation", "generateSyntheticPartsAfterBody", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MultifileClassPartCodegen extends MemberCodegen<KtFile> {
    private final Type facadeClassType;
    private final PackageFragmentDescriptor packageFragment;
    private final Type partType;
    private final boolean shouldGeneratePartHierarchy;
    private final String superClassInternalName;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultifileClassPartCodegen(ClassBuilder v, KtFile file, PackageFragmentDescriptor packageFragment, String superClassInternalName, boolean z, MultifileClassPartContext partContext, GenerationState state) {
        super(state, null, partContext, file, v);
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
        Intrinsics.checkNotNullParameter(superClassInternalName, "superClassInternalName");
        Intrinsics.checkNotNullParameter(partContext, "partContext");
        Intrinsics.checkNotNullParameter(state, "state");
        this.packageFragment = packageFragment;
        this.superClassInternalName = superClassInternalName;
        this.shouldGeneratePartHierarchy = z;
        this.partType = partContext.getFilePartType();
        this.facadeClassType = partContext.getMultifileClassType();
        if (z) {
            List<KtDeclaration> declarations = file.getDeclarations();
            if ((declarations instanceof Collection) && declarations.isEmpty()) {
                return;
            }
            for (KtDeclaration ktDeclaration : declarations) {
                if ((ktDeclaration instanceof KtProperty) && shouldInitializeProperty((KtProperty) ktDeclaration)) {
                    throw new AssertionError("State is not allowed in multi-file classes with -Xmultifile-parts-inherit");
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    public void generate() {
        if (this.state.getClassBuilderMode().generateMultiFileFacadePartClasses) {
            super.generate();
            boolean z = this.state.getClassBuilderMode().generateBodies;
            if (this.shouldGeneratePartHierarchy) {
                MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.OtherOrigin(this.packageFragment), 1, "<init>", "()V", null, null);
                if (z) {
                    methodVisitorNewMethod.visitCode();
                    methodVisitorNewMethod.visitVarInsn(25, 0);
                    methodVisitorNewMethod.visitMethodInsn(183, this.superClassInternalName, "<init>", "()V", false);
                    methodVisitorNewMethod.visitInsn(177);
                    methodVisitorNewMethod.visitMaxs(1, 1);
                }
                methodVisitorNewMethod.visitEnd();
            }
        }
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateDeclaration */
    protected void mo6420generateDeclaration() {
        this.v.defineClass((PsiElement) this.element, this.state.getConfig().getClassFileVersion(), (this.shouldGeneratePartHierarchy ? 0 : 4112) | 32, this.partType.getInternalName(), null, this.superClassInternalName, ArrayUtil.EMPTY_STRING_ARRAY);
        this.v.visitSource(((KtFile) this.element).get$name(), null);
        generatePropertyMetadataArrayFieldIfNeeded(this.partType);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateBody */
    protected void mo6419generateBody() {
        T element = this.element;
        Intrinsics.checkNotNullExpressionValue(element, "element");
        Iterator<KtDeclaration> it = CodegenUtil.getMemberDeclarationsToGenerate((KtFile) element).iterator();
        while (it.hasNext()) {
            genSimpleMember(it.next());
        }
        if (this.state.getClassBuilderMode().generateBodies) {
            generateInitializers(new Function0<ExpressionCodegen>() { // from class: org.jetbrains.kotlin.codegen.MultifileClassPartCodegen.generateBody.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ExpressionCodegen invoke() {
                    return MultifileClassPartCodegen.this.createOrGetClInitCodegen();
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
    protected void mo6421generateKotlinMetadataAnnotation() {
        Pair<DescriptorSerializer, ProtoBuf.Package> pairSerializePackagePartMembers = PackagePartCodegen.serializePackagePartMembers(this, this.partType);
        final DescriptorSerializer descriptorSerializerComponent1 = pairSerializePackagePartMembers.component1();
        final ProtoBuf.Package packageComponent2 = pairSerializePackagePartMembers.component2();
        boolean z = this.shouldGeneratePartHierarchy;
        ClassBuilder v = this.v;
        Intrinsics.checkNotNullExpressionValue(v, "v");
        GenerationState state = this.state;
        Intrinsics.checkNotNullExpressionValue(state, "state");
        WriteAnnotationUtilKt.writeKotlinMetadata(v, state, KotlinClassHeader.Kind.MULTIFILE_CLASS_PART, false, z ? 1 : 0, new Function1<AnnotationVisitor, Unit>() { // from class: org.jetbrains.kotlin.codegen.MultifileClassPartCodegen.generateKotlinMetadataAnnotation.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationVisitor annotationVisitor) {
                invoke2(annotationVisitor);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationVisitor av) {
                Intrinsics.checkNotNullParameter(av, "av");
                DescriptorAsmUtil.writeAnnotationData(av, descriptorSerializerComponent1, packageComponent2);
                av.visit("xs", this.facadeClassType.getInternalName());
                FqName packageFqName = ((KtFile) this.element).getPackageFqName();
                if (Intrinsics.areEqual(packageFqName, JvmClassName.byInternalName(this.partType.getInternalName()).getPackageFqName())) {
                    return;
                }
                av.visit(JvmAnnotationNames.METADATA_PACKAGE_NAME_FIELD_NAME, packageFqName.asString());
            }
        });
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void generateSyntheticPartsAfterBody() {
        generateSyntheticAccessors();
    }
}
