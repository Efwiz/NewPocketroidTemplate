package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.codegen.context.FieldOwnerContext;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotatedImpl;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class PackagePartCodegen extends MemberCodegen<KtFile> {
    private final Type packagePartType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "file";
                break;
            case 2:
            case 6:
                objArr[0] = "packagePartType";
                break;
            case 3:
                objArr[0] = "context";
                break;
            case 4:
                objArr[0] = "state";
                break;
            case 5:
                objArr[0] = "codegen";
                break;
            default:
                objArr[0] = RegisterSpec.PREFIX;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/PackagePartCodegen";
        if (i == 5 || i == 6) {
            objArr[2] = "serializePackagePartMembers";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackagePartCodegen(ClassBuilder classBuilder, KtFile ktFile, Type type, FieldOwnerContext fieldOwnerContext, GenerationState generationState) {
        super(generationState, null, fieldOwnerContext, ktFile, classBuilder);
        if (classBuilder == null) {
            $$$reportNull$$$0(0);
        }
        if (ktFile == null) {
            $$$reportNull$$$0(1);
        }
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        if (fieldOwnerContext == null) {
            $$$reportNull$$$0(3);
        }
        if (generationState == null) {
            $$$reportNull$$$0(4);
        }
        this.packagePartType = type;
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateDeclaration */
    protected void mo6420generateDeclaration() {
        ArrayList arrayList = new ArrayList();
        Iterator<KtAnnotationEntry> it = ((KtFile) this.element).getAnnotationEntries().iterator();
        boolean z = false;
        while (it.hasNext()) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) this.state.getBindingContext().get(BindingContext.ANNOTATION, it.next());
            if (annotationDescriptor != null) {
                arrayList.add(annotationDescriptor);
                if (Objects.equals(annotationDescriptor.getFqName(), JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME)) {
                    z = true;
                }
            }
        }
        this.v.defineClass((PsiElement) this.element, this.state.getConfig().getClassFileVersion(), (z ? 4096 : 0) | 49, this.packagePartType.getInternalName(), null, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, ArrayUtil.EMPTY_STRING_ARRAY);
        this.v.visitSource(((KtFile) this.element).get$name(), null);
        generatePropertyMetadataArrayFieldIfNeeded(this.packagePartType);
        AnnotationCodegen.forClass(this.v.getVisitor(), this, this.state).genAnnotations(new AnnotatedImpl(Annotations.INSTANCE.create(arrayList)), null, null);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateBody */
    protected void mo6419generateBody() {
        Iterator<KtDeclaration> it = CodegenUtil.getMemberDeclarationsToGenerate((KtFile) this.element).iterator();
        while (it.hasNext()) {
            genSimpleMember(it.next());
        }
        if (this.state.getClassBuilderMode().generateBodies) {
            generateInitializers(new Function0() { // from class: org.jetbrains.kotlin.codegen.PackagePartCodegen$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return this.f$0.createOrGetClInitCodegen();
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
    protected void mo6421generateKotlinMetadataAnnotation() {
        final Pair<DescriptorSerializer, ProtoBuf.Package> pairSerializePackagePartMembers = serializePackagePartMembers(this, this.packagePartType);
        WriteAnnotationUtilKt.writeKotlinMetadata(this.v, this.state, KotlinClassHeader.Kind.FILE_FACADE, false, 0, new Function1() { // from class: org.jetbrains.kotlin.codegen.PackagePartCodegen$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6423x543e6199(pairSerializePackagePartMembers, (AnnotationVisitor) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateKotlinMetadataAnnotation$0$org-jetbrains-kotlin-codegen-PackagePartCodegen, reason: not valid java name */
    /* synthetic */ Unit m6423x543e6199(Pair pair, AnnotationVisitor annotationVisitor) {
        DescriptorAsmUtil.writeAnnotationData(annotationVisitor, (DescriptorSerializer) pair.getFirst(), (MessageLite) pair.getSecond());
        FqName packageFqName = ((KtFile) this.element).getPackageFqName();
        if (!packageFqName.equals(JvmClassName.byInternalName(this.packagePartType.getInternalName()).getPackageFqName())) {
            annotationVisitor.visit(JvmAnnotationNames.METADATA_PACKAGE_NAME_FIELD_NAME, packageFqName.asString());
        }
        return Unit.INSTANCE;
    }

    protected static Pair<DescriptorSerializer, ProtoBuf.Package> serializePackagePartMembers(MemberCodegen<? extends KtFile> memberCodegen, Type type) {
        if (memberCodegen == null) {
            $$$reportNull$$$0(5);
        }
        if (type == null) {
            $$$reportNull$$$0(6);
        }
        List<MemberDescriptor> memberDescriptorsToGenerate = CodegenUtil.getMemberDescriptorsToGenerate((KtFile) memberCodegen.element, memberCodegen.bindingContext);
        JvmSerializerExtension jvmSerializerExtension = new JvmSerializerExtension(memberCodegen.v.getSerializationBindings(), memberCodegen.state);
        DescriptorSerializer descriptorSerializerCreateTopLevel = DescriptorSerializer.createTopLevel(jvmSerializerExtension, memberCodegen.state.getLanguageVersionSettings(), null);
        ProtoBuf.Package.Builder builderPackagePartProto = descriptorSerializerCreateTopLevel.packagePartProto(((KtFile) memberCodegen.element).getPackageFqName(), memberDescriptorsToGenerate);
        jvmSerializerExtension.serializeJvmPackage(builderPackagePartProto, type);
        return new Pair<>(descriptorSerializerCreateTopLevel, builderPackagePartProto.build());
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void generateSyntheticPartsAfterBody() {
        generateSyntheticAccessors();
    }
}
