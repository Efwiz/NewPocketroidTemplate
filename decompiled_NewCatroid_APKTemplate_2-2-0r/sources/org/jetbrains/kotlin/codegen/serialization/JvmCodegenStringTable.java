package org.jetbrains.kotlin.codegen.serialization;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.DescriptorAwareStringTable;

/* JADX INFO: compiled from: JvmCodegenStringTable.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/serialization/JvmCodegenStringTable;", "Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;", "Lorg/jetbrains/kotlin/serialization/DescriptorAwareStringTable;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapperBase;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmNameResolver;", "(Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapperBase;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmNameResolver;)V", "isLocalClassIdReplacementKeptGeneric", "", "()Z", "getLocalClassIdReplacement", "Lorg/jetbrains/kotlin/name/ClassId;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmCodegenStringTable extends JvmStringTable implements DescriptorAwareStringTable {
    private final KotlinTypeMapperBase typeMapper;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public JvmCodegenStringTable(KotlinTypeMapperBase typeMapper) {
        this(typeMapper, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
    }

    @Override // org.jetbrains.kotlin.serialization.DescriptorAwareStringTable
    public boolean isLocalClassIdReplacementKeptGeneric() {
        return true;
    }

    public /* synthetic */ JvmCodegenStringTable(KotlinTypeMapperBase kotlinTypeMapperBase, JvmNameResolver jvmNameResolver, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinTypeMapperBase, (i & 2) != 0 ? null : jvmNameResolver);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmCodegenStringTable(KotlinTypeMapperBase typeMapper, JvmNameResolver jvmNameResolver) {
        super(jvmNameResolver);
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        this.typeMapper = typeMapper;
    }

    @Override // org.jetbrains.kotlin.serialization.DescriptorAwareStringTable
    public ClassId getLocalClassIdReplacement(ClassifierDescriptorWithTypeParameters descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        DeclarationDescriptor containingDeclaration = descriptor.getPackageFragment();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (containingDeclaration instanceof ClassifierDescriptorWithTypeParameters) {
            ClassId classIdCreateNestedClassId = getLocalClassIdReplacement((ClassifierDescriptorWithTypeParameters) containingDeclaration).createNestedClassId(descriptor.getShortName());
            Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
            return classIdCreateNestedClassId;
        }
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            throw new IllegalStateException("getLocalClassIdReplacement should only be called for local classes: " + descriptor);
        }
        String internalName = this.typeMapper.mapClass(descriptor).getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        FqName fqName = new FqName(StringsKt.replace$default(internalName, '/', '.', false, 4, (Object) null));
        return new ClassId(fqName.parent(), FqName.topLevel(fqName.shortName()), true);
    }
}
