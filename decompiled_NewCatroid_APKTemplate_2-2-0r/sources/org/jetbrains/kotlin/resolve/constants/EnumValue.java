package org.jetbrains.kotlin.resolve.constants;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: constantValues.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0004¢\u0006\u0002\u0010\u0007J=\u0010\f\u001a\n \u000e*\u0004\u0018\u0001H\rH\r\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/EnumValue;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/name/Name;", "enumClassId", "enumEntryName", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/name/Name;)V", "getEnumClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getEnumEntryName", "()Lorg/jetbrains/kotlin/name/Name;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationArgumentVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationArgumentVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumValue extends ConstantValue<Pair<? extends ClassId, ? extends Name>> {
    private final ClassId enumClassId;
    private final Name enumEntryName;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EnumValue(ClassId enumClassId, Name enumEntryName) {
        super(TuplesKt.to(enumClassId, enumEntryName));
        Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
        Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
        this.enumClassId = enumClassId;
        this.enumEntryName = enumEntryName;
    }

    public final ClassId getEnumClassId() {
        return this.enumClassId;
    }

    public final Name getEnumEntryName() {
        return this.enumEntryName;
    }

    @Override // org.jetbrains.kotlin.resolve.constants.ConstantValue
    public KotlinType getType(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(module, this.enumClassId);
        SimpleType defaultType = null;
        if (classDescriptorFindClassAcrossModuleDependencies != null) {
            if (!DescriptorUtils.isEnumClass(classDescriptorFindClassAcrossModuleDependencies)) {
                classDescriptorFindClassAcrossModuleDependencies = null;
            }
            if (classDescriptorFindClassAcrossModuleDependencies != null) {
                defaultType = classDescriptorFindClassAcrossModuleDependencies.getDefaultType();
            }
        }
        if (defaultType != null) {
            return defaultType;
        }
        ErrorTypeKind errorTypeKind = ErrorTypeKind.ERROR_ENUM_TYPE;
        String string = this.enumClassId.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String string2 = this.enumEntryName.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        return ErrorUtils.createErrorType(errorTypeKind, string, string2);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.ConstantValue
    public <R, D> R accept(AnnotationArgumentVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitEnumValue(this, data);
    }

    @Override // org.jetbrains.kotlin.resolve.constants.ConstantValue
    public String toString() {
        return new StringBuilder().append(this.enumClassId.getShortClassName()).append('.').append(this.enumEntryName).toString();
    }
}
