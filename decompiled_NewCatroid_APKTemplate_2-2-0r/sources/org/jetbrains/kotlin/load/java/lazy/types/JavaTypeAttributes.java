package org.jetbrains.kotlin.load.java.lazy.types;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.ErasureTypeAttributes;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUsage;

/* JADX INFO: compiled from: JavaTypeAttributes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\b\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0007HÆ\u0003J\u0011\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nHÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\rHÆ\u0003JO\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rHÆ\u0001J\u0013\u0010\u001f\u001a\u00020\u00072\b\u0010 \u001a\u0004\u0018\u00010!H\u0096\u0002J\b\u0010\"\u001a\u00020#H\u0016J\u000e\u0010$\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007J\t\u0010%\u001a\u00020&HÖ\u0001J\u0012\u0010'\u001a\u00020\u00002\b\u0010(\u001a\u0004\u0018\u00010\rH\u0016J\u000e\u0010)\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0005J\u0010\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020\u000bH\u0016R\u0016\u0010\f\u001a\u0004\u0018\u00010\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0015R\u001c\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/types/JavaTypeAttributes;", "Lorg/jetbrains/kotlin/types/ErasureTypeAttributes;", "howThisTypeIsUsed", "Lorg/jetbrains/kotlin/types/TypeUsage;", "flexibility", "Lorg/jetbrains/kotlin/load/java/lazy/types/JavaTypeFlexibility;", "isRaw", "", "isForAnnotationParameter", "visitedTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "defaultType", "Lorg/jetbrains/kotlin/types/SimpleType;", "(Lorg/jetbrains/kotlin/types/TypeUsage;Lorg/jetbrains/kotlin/load/java/lazy/types/JavaTypeFlexibility;ZZLjava/util/Set;Lorg/jetbrains/kotlin/types/SimpleType;)V", "getDefaultType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "getFlexibility", "()Lorg/jetbrains/kotlin/load/java/lazy/types/JavaTypeFlexibility;", "getHowThisTypeIsUsed", "()Lorg/jetbrains/kotlin/types/TypeUsage;", "()Z", "getVisitedTypeParameters", "()Ljava/util/Set;", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "markIsRaw", "toString", "", "withDefaultType", ModuleXmlParser.TYPE, "withFlexibility", "withNewVisitedTypeParameter", "typeParameter", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JavaTypeAttributes extends ErasureTypeAttributes {
    private final SimpleType defaultType;
    private final JavaTypeFlexibility flexibility;
    private final TypeUsage howThisTypeIsUsed;
    private final boolean isForAnnotationParameter;
    private final boolean isRaw;
    private final Set<TypeParameterDescriptor> visitedTypeParameters;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ JavaTypeAttributes copy$default(JavaTypeAttributes javaTypeAttributes, TypeUsage typeUsage, JavaTypeFlexibility javaTypeFlexibility, boolean z, boolean z2, Set set, SimpleType simpleType, int i, Object obj) {
        if ((i & 1) != 0) {
            typeUsage = javaTypeAttributes.howThisTypeIsUsed;
        }
        if ((i & 2) != 0) {
            javaTypeFlexibility = javaTypeAttributes.flexibility;
        }
        JavaTypeFlexibility javaTypeFlexibility2 = javaTypeFlexibility;
        if ((i & 4) != 0) {
            z = javaTypeAttributes.isRaw;
        }
        boolean z3 = z;
        if ((i & 8) != 0) {
            z2 = javaTypeAttributes.isForAnnotationParameter;
        }
        boolean z4 = z2;
        if ((i & 16) != 0) {
            set = javaTypeAttributes.visitedTypeParameters;
        }
        Set set2 = set;
        if ((i & 32) != 0) {
            simpleType = javaTypeAttributes.defaultType;
        }
        return javaTypeAttributes.copy(typeUsage, javaTypeFlexibility2, z3, z4, set2, simpleType);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final TypeUsage getHowThisTypeIsUsed() {
        return this.howThisTypeIsUsed;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final JavaTypeFlexibility getFlexibility() {
        return this.flexibility;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getIsRaw() {
        return this.isRaw;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getIsForAnnotationParameter() {
        return this.isForAnnotationParameter;
    }

    public final Set<TypeParameterDescriptor> component5() {
        return this.visitedTypeParameters;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final SimpleType getDefaultType() {
        return this.defaultType;
    }

    public final JavaTypeAttributes copy(TypeUsage howThisTypeIsUsed, JavaTypeFlexibility flexibility, boolean isRaw, boolean isForAnnotationParameter, Set<? extends TypeParameterDescriptor> visitedTypeParameters, SimpleType defaultType) {
        Intrinsics.checkNotNullParameter(howThisTypeIsUsed, "howThisTypeIsUsed");
        Intrinsics.checkNotNullParameter(flexibility, "flexibility");
        return new JavaTypeAttributes(howThisTypeIsUsed, flexibility, isRaw, isForAnnotationParameter, visitedTypeParameters, defaultType);
    }

    public String toString() {
        return "JavaTypeAttributes(howThisTypeIsUsed=" + this.howThisTypeIsUsed + ", flexibility=" + this.flexibility + ", isRaw=" + this.isRaw + ", isForAnnotationParameter=" + this.isForAnnotationParameter + ", visitedTypeParameters=" + this.visitedTypeParameters + ", defaultType=" + this.defaultType + ')';
    }

    @Override // org.jetbrains.kotlin.types.ErasureTypeAttributes
    public TypeUsage getHowThisTypeIsUsed() {
        return this.howThisTypeIsUsed;
    }

    public /* synthetic */ JavaTypeAttributes(TypeUsage typeUsage, JavaTypeFlexibility javaTypeFlexibility, boolean z, boolean z2, Set set, SimpleType simpleType, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeUsage, (i & 2) != 0 ? JavaTypeFlexibility.INFLEXIBLE : javaTypeFlexibility, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? null : set, (i & 32) != 0 ? null : simpleType);
    }

    public final JavaTypeFlexibility getFlexibility() {
        return this.flexibility;
    }

    public final boolean isRaw() {
        return this.isRaw;
    }

    public final boolean isForAnnotationParameter() {
        return this.isForAnnotationParameter;
    }

    @Override // org.jetbrains.kotlin.types.ErasureTypeAttributes
    public Set<TypeParameterDescriptor> getVisitedTypeParameters() {
        return this.visitedTypeParameters;
    }

    @Override // org.jetbrains.kotlin.types.ErasureTypeAttributes
    public SimpleType getDefaultType() {
        return this.defaultType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public JavaTypeAttributes(TypeUsage howThisTypeIsUsed, JavaTypeFlexibility flexibility, boolean z, boolean z2, Set<? extends TypeParameterDescriptor> set, SimpleType simpleType) {
        super(howThisTypeIsUsed, set, simpleType);
        Intrinsics.checkNotNullParameter(howThisTypeIsUsed, "howThisTypeIsUsed");
        Intrinsics.checkNotNullParameter(flexibility, "flexibility");
        this.howThisTypeIsUsed = howThisTypeIsUsed;
        this.flexibility = flexibility;
        this.isRaw = z;
        this.isForAnnotationParameter = z2;
        this.visitedTypeParameters = set;
        this.defaultType = simpleType;
    }

    public final JavaTypeAttributes withFlexibility(JavaTypeFlexibility flexibility) {
        Intrinsics.checkNotNullParameter(flexibility, "flexibility");
        return copy$default(this, null, flexibility, false, false, null, null, 61, null);
    }

    public final JavaTypeAttributes markIsRaw(boolean isRaw) {
        return copy$default(this, null, null, isRaw, false, null, null, 59, null);
    }

    @Override // org.jetbrains.kotlin.types.ErasureTypeAttributes
    public JavaTypeAttributes withDefaultType(SimpleType type) {
        return copy$default(this, null, null, false, false, null, type, 31, null);
    }

    @Override // org.jetbrains.kotlin.types.ErasureTypeAttributes
    public JavaTypeAttributes withNewVisitedTypeParameter(TypeParameterDescriptor typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return copy$default(this, null, null, false, false, getVisitedTypeParameters() != null ? SetsKt.plus(getVisitedTypeParameters(), typeParameter) : SetsKt.setOf(typeParameter), null, 47, null);
    }

    @Override // org.jetbrains.kotlin.types.ErasureTypeAttributes
    public boolean equals(Object other) {
        if (!(other instanceof JavaTypeAttributes)) {
            return false;
        }
        JavaTypeAttributes javaTypeAttributes = (JavaTypeAttributes) other;
        return Intrinsics.areEqual(javaTypeAttributes.getDefaultType(), getDefaultType()) && javaTypeAttributes.getHowThisTypeIsUsed() == getHowThisTypeIsUsed() && javaTypeAttributes.flexibility == this.flexibility && javaTypeAttributes.isRaw == this.isRaw && javaTypeAttributes.isForAnnotationParameter == this.isForAnnotationParameter;
    }

    @Override // org.jetbrains.kotlin.types.ErasureTypeAttributes
    public int hashCode() {
        SimpleType defaultType = getDefaultType();
        int iHashCode = defaultType != null ? defaultType.hashCode() : 0;
        int iHashCode2 = iHashCode + (iHashCode * 31) + getHowThisTypeIsUsed().hashCode();
        int iHashCode3 = iHashCode2 + (iHashCode2 * 31) + this.flexibility.hashCode();
        int i = iHashCode3 + (iHashCode3 * 31) + (this.isRaw ? 1 : 0);
        return i + (i * 31) + (this.isForAnnotationParameter ? 1 : 0);
    }
}
