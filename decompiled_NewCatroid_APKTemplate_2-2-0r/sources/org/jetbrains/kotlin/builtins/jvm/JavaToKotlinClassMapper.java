package org.jetbrains.kotlin.builtins.jvm;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PlatformToKotlinClassMapper;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: JavaToKotlinClassMapper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004J1\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0016\u0010\f\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\rH\u0082\bJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J)\u0010\u0015\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00182\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a¢\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u001d2\u0006\u0010\u001e\u001a\u00020\u0004H\u0016J\u001c\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u001d2\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/builtins/jvm/JavaToKotlinClassMapper;", "Lorg/jetbrains/kotlin/builtins/PlatformToKotlinClassMapper;", "()V", "convertMutableToReadOnly", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "mutable", "convertReadOnlyToMutable", "readOnly", "convertToOppositeMutability", "descriptor", "mutabilityKindName", "", "oppositeNameExtractor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "Lorg/jetbrains/kotlin/name/FqName;", "isMutable", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "isReadOnly", "mapJavaToKotlin", "fqName", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "functionTypeArity", "", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Ljava/lang/Integer;)Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "mapPlatformClass", "", "classDescriptor", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaToKotlinClassMapper implements PlatformToKotlinClassMapper {
    public static final JavaToKotlinClassMapper INSTANCE = new JavaToKotlinClassMapper();

    private JavaToKotlinClassMapper() {
    }

    @Override // org.jetbrains.kotlin.builtins.PlatformToKotlinClassMapper
    public Collection<ClassDescriptor> mapPlatformClass(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        ClassDescriptor classDescriptor2 = classDescriptor;
        FqNameUnsafe fqName = DescriptorUtils.getFqName(classDescriptor2);
        Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
        if (fqName.isSafe()) {
            FqName safe = fqName.toSafe();
            Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
            return mapPlatformClass(safe, DescriptorUtilsKt.getBuiltIns(classDescriptor2));
        }
        return SetsKt.emptySet();
    }

    public final Collection<ClassDescriptor> mapPlatformClass(FqName fqName, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        ClassDescriptor classDescriptorMapJavaToKotlin$default = mapJavaToKotlin$default(this, fqName, builtIns, null, 4, null);
        if (classDescriptorMapJavaToKotlin$default == null) {
            return SetsKt.emptySet();
        }
        FqName onlyToMutable = JavaToKotlinClassMap.INSTANCE.readOnlyToMutable(DescriptorUtilsKt.getFqNameUnsafe(classDescriptorMapJavaToKotlin$default));
        if (onlyToMutable == null) {
            return SetsKt.setOf(classDescriptorMapJavaToKotlin$default);
        }
        ClassDescriptor builtInClassByFqName = builtIns.getBuiltInClassByFqName(onlyToMutable);
        Intrinsics.checkNotNullExpressionValue(builtInClassByFqName, "getBuiltInClassByFqName(...)");
        return CollectionsKt.listOf((Object[]) new ClassDescriptor[]{classDescriptorMapJavaToKotlin$default, builtInClassByFqName});
    }

    public static /* synthetic */ ClassDescriptor mapJavaToKotlin$default(JavaToKotlinClassMapper javaToKotlinClassMapper, FqName fqName, KotlinBuiltIns kotlinBuiltIns, Integer num, int i, Object obj) {
        if ((i & 4) != 0) {
            num = null;
        }
        return javaToKotlinClassMapper.mapJavaToKotlin(fqName, kotlinBuiltIns, num);
    }

    public final ClassDescriptor mapJavaToKotlin(FqName fqName, KotlinBuiltIns builtIns, Integer functionTypeArity) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        ClassId classIdMapJavaToKotlin = (functionTypeArity == null || !Intrinsics.areEqual(fqName, JavaToKotlinClassMap.INSTANCE.getFUNCTION_N_FQ_NAME())) ? JavaToKotlinClassMap.INSTANCE.mapJavaToKotlin(fqName) : StandardNames.getFunctionClassId(functionTypeArity.intValue());
        if (classIdMapJavaToKotlin != null) {
            return builtIns.getBuiltInClassByFqName(classIdMapJavaToKotlin.asSingleFqName());
        }
        return null;
    }

    public final boolean isMutable(ClassDescriptor mutable) {
        Intrinsics.checkNotNullParameter(mutable, "mutable");
        return JavaToKotlinClassMap.INSTANCE.isMutable(DescriptorUtils.getFqName(mutable));
    }

    public final boolean isMutable(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor(type);
        return classDescriptor != null && isMutable(classDescriptor);
    }

    public final boolean isReadOnly(ClassDescriptor readOnly) {
        Intrinsics.checkNotNullParameter(readOnly, "readOnly");
        return JavaToKotlinClassMap.INSTANCE.isReadOnly(DescriptorUtils.getFqName(readOnly));
    }

    public final boolean isReadOnly(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor(type);
        return classDescriptor != null && isReadOnly(classDescriptor);
    }

    public final ClassDescriptor convertMutableToReadOnly(ClassDescriptor mutable) {
        Intrinsics.checkNotNullParameter(mutable, "mutable");
        ClassDescriptor classDescriptor = mutable;
        FqName fqNameMutableToReadOnly = JavaToKotlinClassMap.INSTANCE.mutableToReadOnly(DescriptorUtils.getFqName(classDescriptor));
        if (fqNameMutableToReadOnly == null) {
            throw new IllegalArgumentException("Given class " + mutable + " is not a mutable collection");
        }
        ClassDescriptor builtInClassByFqName = DescriptorUtilsKt.getBuiltIns(classDescriptor).getBuiltInClassByFqName(fqNameMutableToReadOnly);
        Intrinsics.checkNotNullExpressionValue(builtInClassByFqName, "getBuiltInClassByFqName(...)");
        return builtInClassByFqName;
    }

    public final ClassDescriptor convertReadOnlyToMutable(ClassDescriptor readOnly) {
        Intrinsics.checkNotNullParameter(readOnly, "readOnly");
        ClassDescriptor classDescriptor = readOnly;
        FqName onlyToMutable = JavaToKotlinClassMap.INSTANCE.readOnlyToMutable(DescriptorUtils.getFqName(classDescriptor));
        if (onlyToMutable == null) {
            throw new IllegalArgumentException("Given class " + readOnly + " is not a read-only collection");
        }
        ClassDescriptor builtInClassByFqName = DescriptorUtilsKt.getBuiltIns(classDescriptor).getBuiltInClassByFqName(onlyToMutable);
        Intrinsics.checkNotNullExpressionValue(builtInClassByFqName, "getBuiltInClassByFqName(...)");
        return builtInClassByFqName;
    }
}
