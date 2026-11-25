package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;

/* JADX INFO: compiled from: TypeSubstitution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B#\b\u0016\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007B+\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010\n\u001a\u00020\u000bH\u0016J\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0014\u001a\u00020\u0015H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u000bH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\b¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/types/IndexedParametersSubstitution;", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "parameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "argumentsList", "Lorg/jetbrains/kotlin/types/TypeProjection;", "(Ljava/util/List;Ljava/util/List;)V", "", "arguments", "approximateContravariantCapturedTypes", "", "([Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;[Lorg/jetbrains/kotlin/types/TypeProjection;Z)V", "getArguments", "()[Lorg/jetbrains/kotlin/types/TypeProjection;", "[Lorg/jetbrains/kotlin/types/TypeProjection;", "getParameters", "()[Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "[Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "get", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/types/KotlinType;", "isEmpty", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IndexedParametersSubstitution extends TypeSubstitution {
    private final boolean approximateContravariantCapturedTypes;
    private final TypeProjection[] arguments;
    private final TypeParameterDescriptor[] parameters;

    public /* synthetic */ IndexedParametersSubstitution(TypeParameterDescriptor[] typeParameterDescriptorArr, TypeProjection[] typeProjectionArr, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeParameterDescriptorArr, typeProjectionArr, (i & 4) != 0 ? false : z);
    }

    public final TypeParameterDescriptor[] getParameters() {
        return this.parameters;
    }

    public final TypeProjection[] getArguments() {
        return this.arguments;
    }

    public IndexedParametersSubstitution(TypeParameterDescriptor[] parameters, TypeProjection[] arguments, boolean z) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        this.parameters = parameters;
        this.arguments = arguments;
        this.approximateContravariantCapturedTypes = z;
        int length = parameters.length;
        int length2 = arguments.length;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IndexedParametersSubstitution(List<? extends TypeParameterDescriptor> parameters, List<? extends TypeProjection> argumentsList) {
        this((TypeParameterDescriptor[]) parameters.toArray(new TypeParameterDescriptor[0]), (TypeProjection[]) argumentsList.toArray(new TypeProjection[0]), false, 4, null);
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(argumentsList, "argumentsList");
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    public boolean isEmpty() {
        return this.arguments.length == 0;
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    /* JADX INFO: renamed from: approximateContravariantCapturedTypes, reason: from getter */
    public boolean getApproximateContravariantCapturedTypes() {
        return this.approximateContravariantCapturedTypes;
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    /* JADX INFO: renamed from: get */
    public TypeProjection mo7475get(KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = key.getConstructor().mo7434getDeclarationDescriptor();
        TypeParameterDescriptor typeParameterDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
        if (typeParameterDescriptor == null) {
            return null;
        }
        int index = typeParameterDescriptor.getIndex();
        TypeParameterDescriptor[] typeParameterDescriptorArr = this.parameters;
        if (index >= typeParameterDescriptorArr.length || !Intrinsics.areEqual(typeParameterDescriptorArr[index].getTypeConstructor(), typeParameterDescriptor.getTypeConstructor())) {
            return null;
        }
        return this.arguments[index];
    }
}
