package org.jetbrains.kotlin.descriptors.annotations;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: annotationUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u001a&\u0010\u0006\u001a\u00020\u0007*\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n\u001a0\u0010\u0006\u001a\u00020\u0007*\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\u000e\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"DEPRECATED_LEVEL_NAME", "Lorg/jetbrains/kotlin/name/Name;", "DEPRECATED_MESSAGE_NAME", "DEPRECATED_REPLACE_WITH_NAME", "REPLACE_WITH_EXPRESSION_NAME", "REPLACE_WITH_IMPORTS_NAME", "createDeprecatedAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", CrashHianalyticsData.MESSAGE, "", "replaceWith", FirebaseAnalytics.Param.LEVEL, "forcePropagationDeprecationToOverrides", "", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnnotationUtilKt {
    private static final Name DEPRECATED_LEVEL_NAME;
    private static final Name DEPRECATED_MESSAGE_NAME;
    private static final Name DEPRECATED_REPLACE_WITH_NAME;
    private static final Name REPLACE_WITH_EXPRESSION_NAME;
    private static final Name REPLACE_WITH_IMPORTS_NAME;

    public static /* synthetic */ AnnotationDescriptor createDeprecatedAnnotation$default(KotlinBuiltIns kotlinBuiltIns, String str, String str2, String str3, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "";
        }
        if ((i & 4) != 0) {
            str3 = "WARNING";
        }
        if ((i & 8) != 0) {
            z = false;
        }
        return createDeprecatedAnnotation(kotlinBuiltIns, str, str2, str3, z);
    }

    public static final AnnotationDescriptor createDeprecatedAnnotation(final KotlinBuiltIns kotlinBuiltIns, String message, String replaceWith, String level, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(replaceWith, "replaceWith");
        Intrinsics.checkNotNullParameter(level, "level");
        BuiltInAnnotationDescriptor builtInAnnotationDescriptor = new BuiltInAnnotationDescriptor(kotlinBuiltIns, StandardNames.FqNames.replaceWith, MapsKt.mapOf(TuplesKt.to(REPLACE_WITH_EXPRESSION_NAME, new StringValue(replaceWith)), TuplesKt.to(REPLACE_WITH_IMPORTS_NAME, new ArrayValue(CollectionsKt.emptyList(), new Function1<ModuleDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.descriptors.annotations.AnnotationUtilKt$createDeprecatedAnnotation$replaceWithAnnotation$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(ModuleDescriptor module) {
                Intrinsics.checkNotNullParameter(module, "module");
                SimpleType arrayType = module.getBuiltIns().getArrayType(Variance.INVARIANT, kotlinBuiltIns.getStringType());
                Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
                return arrayType;
            }
        }))), false, 8, null);
        FqName fqName = StandardNames.FqNames.deprecated;
        Name name2 = DEPRECATED_LEVEL_NAME;
        ClassId classId = ClassId.topLevel(StandardNames.FqNames.deprecationLevel);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        Name nameIdentifier = Name.identifier(level);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return new BuiltInAnnotationDescriptor(kotlinBuiltIns, fqName, MapsKt.mapOf(TuplesKt.to(DEPRECATED_MESSAGE_NAME, new StringValue(message)), TuplesKt.to(DEPRECATED_REPLACE_WITH_NAME, new AnnotationValue(builtInAnnotationDescriptor)), TuplesKt.to(name2, new EnumValue(classId, nameIdentifier))), z);
    }

    public static /* synthetic */ AnnotationDescriptor createDeprecatedAnnotation$default(KotlinBuiltIns kotlinBuiltIns, String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "";
        }
        if ((i & 4) != 0) {
            str3 = "WARNING";
        }
        return createDeprecatedAnnotation(kotlinBuiltIns, str, str2, str3);
    }

    public static final AnnotationDescriptor createDeprecatedAnnotation(KotlinBuiltIns kotlinBuiltIns, String message, String replaceWith, String level) {
        Intrinsics.checkNotNullParameter(kotlinBuiltIns, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(replaceWith, "replaceWith");
        Intrinsics.checkNotNullParameter(level, "level");
        return createDeprecatedAnnotation(kotlinBuiltIns, message, replaceWith, level, false);
    }

    static {
        Name nameIdentifier = Name.identifier(CrashHianalyticsData.MESSAGE);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        DEPRECATED_MESSAGE_NAME = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("replaceWith");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        DEPRECATED_REPLACE_WITH_NAME = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier(FirebaseAnalytics.Param.LEVEL);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        DEPRECATED_LEVEL_NAME = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier("expression");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        REPLACE_WITH_EXPRESSION_NAME = nameIdentifier4;
        Name nameIdentifier5 = Name.identifier("imports");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        REPLACE_WITH_IMPORTS_NAME = nameIdentifier5;
    }
}
