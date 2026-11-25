package org.jetbrains.kotlin.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.incremental.UtilsKt;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: IncrementalTrackerUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a&\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0004¨\u0006\f"}, d2 = {PsiKeyword.RECORD, "", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "subjectType", "Lorg/jetbrains/kotlin/types/KotlinType;", "subjectExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "elseEntry", "Lorg/jetbrains/kotlin/psi/KtWhenEntry;", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "expression", ModuleXmlParser.TYPE, "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IncrementalTrackerUtilKt {
    public static final void record(LookupTracker lookupTracker, KtExpression expression, KotlinType type) {
        ClassifierDescriptor this$0;
        Intrinsics.checkNotNullParameter(lookupTracker, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(type, "type");
        if (KotlinTypeKt.isError(type) || TypeUtilsKt.isUnit(type) || (this$0 = type.getConstructor().getThis$0()) == null) {
            return;
        }
        DeclarationDescriptor containingDeclaration = this$0.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if ((containingDeclaration instanceof PackageFragmentDescriptor) && !DescriptorUtils.isLocal(this$0)) {
            Name name2 = this$0.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            UtilsKt.record(lookupTracker, new KotlinLookupLocation(expression), (PackageFragmentDescriptor) containingDeclaration, name2);
        } else if ((containingDeclaration instanceof ClassDescriptor) && !DescriptorUtils.isLocal(this$0)) {
            Name name3 = this$0.getShortName();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            UtilsKt.record(lookupTracker, new KotlinLookupLocation(expression), (ClassDescriptor) containingDeclaration, name3);
        }
        for (TypeProjection typeProjection : type.getArguments()) {
            if (!typeProjection.isStarProjection()) {
                KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                record(lookupTracker, expression, nullableAnyType);
            }
        }
    }

    public static final void record(EnumWhenTracker enumWhenTracker, KotlinType kotlinType, KtExpression subjectExpression, KtWhenEntry ktWhenEntry) {
        TypeConstructor constructor;
        ClassifierDescriptor this$0;
        ClassifierDescriptor classifierDescriptor;
        FqName fqNameContainingPackage;
        String string;
        String path;
        String strReplace$default;
        Intrinsics.checkNotNullParameter(enumWhenTracker, "<this>");
        Intrinsics.checkNotNullParameter(subjectExpression, "subjectExpression");
        if (ktWhenEntry != null || !(subjectExpression instanceof KtNameReferenceExpression) || kotlinType == null || (constructor = kotlinType.getConstructor()) == null || (this$0 = constructor.getThis$0()) == null || (fqNameContainingPackage = DescriptorUtilKt.containingPackage((classifierDescriptor = this$0))) == null || (string = fqNameContainingPackage.toString()) == null) {
            return;
        }
        String strAsString = DescriptorUtilsKt.getFqNameSafe(classifierDescriptor).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        VirtualFile virtualFile = ((KtNameReferenceExpression) subjectExpression).getContainingFile().getVirtualFile();
        if (virtualFile == null || (path = virtualFile.getPath()) == null) {
            return;
        }
        if (StringsKt.startsWith$default(strAsString, string + '.', false, 2, (Object) null)) {
            StringBuilder sbAppend = new StringBuilder().append(string).append('.');
            String strSubstring = strAsString.substring(string.length() + 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            strReplace$default = sbAppend.append(StringsKt.replace$default(strSubstring, ".", "$", false, 4, (Object) null)).toString();
        } else {
            strReplace$default = StringsKt.replace$default(strAsString, ".", "$", false, 4, (Object) null);
        }
        enumWhenTracker.report(path, strReplace$default);
    }
}
