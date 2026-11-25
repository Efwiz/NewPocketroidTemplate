package org.jetbrains.kotlin.resolve.deprecation;

import com.google.android.gms.cast.MediaError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.BuiltInAnnotationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;

/* JADX INFO: compiled from: Deprecation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u0000 \u00172\u00020\u0001:\u0003\u0017\u0018\u0019B)\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u000f8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0011R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u0082\u0001\u0002\u001a\u001b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation;", "Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "target", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "propagatesToOverrides", "", "forcePropagationToOverrides", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;ZZ)V", "getAnnotation", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getForcePropagationToOverrides", "()Z", CrashHianalyticsData.MESSAGE, "", "getMessage", "()Ljava/lang/String;", "getPropagatesToOverrides", "replaceWithValue", "getReplaceWithValue$frontend", "getTarget", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Companion", "DeprecatedSince", "StandardDeprecated", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation$DeprecatedSince;", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation$StandardDeprecated;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeprecatedByAnnotation extends DescriptorBasedDeprecationInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final AnnotationDescriptor annotation;
    private final boolean forcePropagationToOverrides;
    private final boolean propagatesToOverrides;
    private final DeclarationDescriptor target;

    public /* synthetic */ DeprecatedByAnnotation(AnnotationDescriptor annotationDescriptor, DeclarationDescriptor declarationDescriptor, boolean z, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotationDescriptor, declarationDescriptor, z, z2);
    }

    public /* synthetic */ DeprecatedByAnnotation(AnnotationDescriptor annotationDescriptor, DeclarationDescriptor declarationDescriptor, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotationDescriptor, declarationDescriptor, z, (i & 8) != 0 ? false : z2, null);
    }

    public final AnnotationDescriptor getAnnotation() {
        return this.annotation;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo
    public DeclarationDescriptor getTarget() {
        return this.target;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo, org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public final boolean getPropagatesToOverrides() {
        return this.propagatesToOverrides;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo
    public final boolean getForcePropagationToOverrides() {
        return this.forcePropagationToOverrides;
    }

    private DeprecatedByAnnotation(AnnotationDescriptor annotationDescriptor, DeclarationDescriptor declarationDescriptor, boolean z, boolean z2) {
        this.annotation = annotationDescriptor;
        this.target = declarationDescriptor;
        this.propagatesToOverrides = z;
        this.forcePropagationToOverrides = z2;
        if (z2 && !z) {
            throw new IllegalArgumentException("if something is `forcePropagationToOverrides`, it's expected that `propagatesToOverrides` == true, too".toString());
        }
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public String getMessage() {
        ConstantValue<?> constantValueArgumentValue = AnnotationUtilKt.argumentValue(this.annotation, CrashHianalyticsData.MESSAGE);
        StringValue stringValue = constantValueArgumentValue instanceof StringValue ? (StringValue) constantValueArgumentValue : null;
        if (stringValue != null) {
            return stringValue.getValue();
        }
        return null;
    }

    public final String getReplaceWithValue$frontend() {
        ConstantValue<?> constantValueArgumentValue = AnnotationUtilKt.argumentValue(this.annotation, "replaceWith");
        AnnotationValue annotationValue = constantValueArgumentValue instanceof AnnotationValue ? (AnnotationValue) constantValueArgumentValue : null;
        AnnotationDescriptor value = annotationValue != null ? annotationValue.getValue() : null;
        ConstantValue<?> constantValueArgumentValue2 = value != null ? AnnotationUtilKt.argumentValue(value, "expression") : null;
        StringValue stringValue = constantValueArgumentValue2 instanceof StringValue ? (StringValue) constantValueArgumentValue2 : null;
        if (stringValue != null) {
            return stringValue.getValue();
        }
        return null;
    }

    /* JADX INFO: compiled from: Deprecation.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u0013\u0010\u000e\u001a\u00020\u00072\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation$StandardDeprecated;", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation;", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "target", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "propagatesToOverrides", "", "forcePropagationToOverrides", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;ZZ)V", "deprecationLevel", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "getDeprecationLevel", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class StandardDeprecated extends DeprecatedByAnnotation {
        public /* synthetic */ StandardDeprecated(AnnotationDescriptor annotationDescriptor, DeclarationDescriptor declarationDescriptor, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(annotationDescriptor, declarationDescriptor, z, (i & 8) != 0 ? false : z2);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public StandardDeprecated(AnnotationDescriptor annotation, DeclarationDescriptor target, boolean z, boolean z2) {
            super(annotation, target, z, z2, null);
            Intrinsics.checkNotNullParameter(annotation, "annotation");
            Intrinsics.checkNotNullParameter(target, "target");
        }

        @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
        public DeprecationLevelValue getDeprecationLevel() {
            Name enumEntryName;
            ConstantValue<?> constantValueArgumentValue = AnnotationUtilKt.argumentValue(getAnnotation(), FirebaseAnalytics.Param.LEVEL);
            String strAsString = null;
            EnumValue enumValue = constantValueArgumentValue instanceof EnumValue ? (EnumValue) constantValueArgumentValue : null;
            if (enumValue != null && (enumEntryName = enumValue.getEnumEntryName()) != null) {
                strAsString = enumEntryName.asString();
            }
            if (strAsString != null) {
                int iHashCode = strAsString.hashCode();
                if (iHashCode != 66247144) {
                    if (iHashCode != 1842428796) {
                        if (iHashCode == 2130809258 && strAsString.equals(VirtualFile.PROP_HIDDEN)) {
                            return DeprecationLevelValue.HIDDEN;
                        }
                    } else if (strAsString.equals("WARNING")) {
                        return DeprecationLevelValue.WARNING;
                    }
                } else if (strAsString.equals(MediaError.ERROR_TYPE_ERROR)) {
                    return DeprecationLevelValue.ERROR;
                }
            }
            return DeprecationLevelValue.WARNING;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof StandardDeprecated)) {
                return false;
            }
            StandardDeprecated standardDeprecated = (StandardDeprecated) other;
            return Intrinsics.areEqual(getAnnotation(), standardDeprecated.getAnnotation()) && Intrinsics.areEqual(getTarget(), standardDeprecated.getTarget()) && getPropagatesToOverrides() == standardDeprecated.getPropagatesToOverrides();
        }

        public int hashCode() {
            return (((getAnnotation().hashCode() * 31) + getTarget().hashCode()) * 31) + Boolean.hashCode(getPropagatesToOverrides());
        }
    }

    /* JADX INFO: compiled from: Deprecation.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0013\u0010\r\u001a\u00020\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation$DeprecatedSince;", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation;", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "target", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "propagatesToOverrides", "", "deprecationLevel", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;ZLorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;)V", "getDeprecationLevel", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DeprecatedSince extends DeprecatedByAnnotation {
        private final DeprecationLevelValue deprecationLevel;

        @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
        public DeprecationLevelValue getDeprecationLevel() {
            return this.deprecationLevel;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DeprecatedSince(AnnotationDescriptor annotation, DeclarationDescriptor target, boolean z, DeprecationLevelValue deprecationLevel) {
            super(annotation, target, z, false, 8, null);
            Intrinsics.checkNotNullParameter(annotation, "annotation");
            Intrinsics.checkNotNullParameter(target, "target");
            Intrinsics.checkNotNullParameter(deprecationLevel, "deprecationLevel");
            this.deprecationLevel = deprecationLevel;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DeprecatedSince)) {
                return false;
            }
            DeprecatedSince deprecatedSince = (DeprecatedSince) other;
            return Intrinsics.areEqual(getAnnotation(), deprecatedSince.getAnnotation()) && Intrinsics.areEqual(getTarget(), deprecatedSince.getTarget()) && getPropagatesToOverrides() == deprecatedSince.getPropagatesToOverrides() && getDeprecationLevel() == deprecatedSince.getDeprecationLevel();
        }

        public int hashCode() {
            return (((((getAnnotation().hashCode() * 31) + getTarget().hashCode()) * 31) + Boolean.hashCode(getPropagatesToOverrides())) * 31) + getDeprecationLevel().hashCode();
        }
    }

    /* JADX INFO: compiled from: Deprecation.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J2\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation;", "deprecatedAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "deprecatedSinceKotlinAnnotation", "target", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "propagatesToOverrides", "", "apiVersion", "Lorg/jetbrains/kotlin/config/ApiVersion;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DeprecatedByAnnotation create(AnnotationDescriptor deprecatedAnnotation, AnnotationDescriptor deprecatedSinceKotlinAnnotation, DeclarationDescriptor target, boolean propagatesToOverrides, ApiVersion apiVersion) {
            Intrinsics.checkNotNullParameter(deprecatedAnnotation, "deprecatedAnnotation");
            Intrinsics.checkNotNullParameter(target, "target");
            Intrinsics.checkNotNullParameter(apiVersion, "apiVersion");
            if (deprecatedSinceKotlinAnnotation != null) {
                DeprecationLevelValue deprecationLevelValueComputeLevelForDeprecatedSinceKotlin = DeprecationUtilKt.computeLevelForDeprecatedSinceKotlin(deprecatedSinceKotlinAnnotation, apiVersion);
                if (deprecationLevelValueComputeLevelForDeprecatedSinceKotlin == null) {
                    return null;
                }
                return new DeprecatedSince(deprecatedAnnotation, target, propagatesToOverrides, deprecationLevelValueComputeLevelForDeprecatedSinceKotlin);
            }
            BuiltInAnnotationDescriptor builtInAnnotationDescriptor = deprecatedAnnotation instanceof BuiltInAnnotationDescriptor ? (BuiltInAnnotationDescriptor) deprecatedAnnotation : null;
            boolean z = true;
            boolean z2 = builtInAnnotationDescriptor != null && builtInAnnotationDescriptor.getForcePropagationDeprecationToOverrides();
            if (!propagatesToOverrides && !z2) {
                z = false;
            }
            return new StandardDeprecated(deprecatedAnnotation, target, z, z2);
        }
    }
}
