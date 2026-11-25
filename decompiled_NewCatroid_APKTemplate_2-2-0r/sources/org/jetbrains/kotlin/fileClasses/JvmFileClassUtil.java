package org.jetbrains.kotlin.fileClasses;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.load.java.descriptors.UtilKt;
import org.jetbrains.kotlin.load.kotlin.PackagePartClassUtils;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFileAnnotationList;
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;

/* JADX INFO: compiled from: JvmFileClassUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0007J\u0010\u0010\u0010\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\fJ\u0010\u0010\u0017\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0016\u001a\u00020\fJ\u000e\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001aJ\u0018\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\bH\u0007J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fileClasses/JvmFileClassUtil;", "", "()V", "JVM_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getJVM_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "JVM_NAME_SHORT", "", "getJVM_NAME_SHORT", "()Ljava/lang/String;", "findAnnotationEntryOnFileNoResolve", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "shortName", "getFacadeClassInternalName", "getFileClassInfoNoResolve", "Lorg/jetbrains/kotlin/fileClasses/JvmFileClassInfo;", "getFileClassInternalName", "getLiteralStringEntryFromAnnotation", "Lorg/jetbrains/kotlin/psi/KtLiteralStringTemplateEntry;", "annotation", "getLiteralStringFromAnnotation", "getPartFqNameForDeserialized", "descriptor", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberDescriptor;", "manglePartName", "facadeName", PsiTreeChangeEvent.PROP_FILE_NAME, "parseJvmNameOnFileNoResolve", "Lorg/jetbrains/kotlin/fileClasses/ParsedJvmFileClassAnnotations;", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmFileClassUtil {
    public static final JvmFileClassUtil INSTANCE = new JvmFileClassUtil();
    private static final FqName JVM_NAME;
    private static final String JVM_NAME_SHORT;

    private JvmFileClassUtil() {
    }

    static {
        FqName fqName = new FqName("kotlin.jvm.JvmName");
        JVM_NAME = fqName;
        String strAsString = fqName.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        JVM_NAME_SHORT = strAsString;
    }

    public final FqName getJVM_NAME() {
        return JVM_NAME;
    }

    public final String getJVM_NAME_SHORT() {
        return JVM_NAME_SHORT;
    }

    public final FqName getPartFqNameForDeserialized(DeserializedMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        JvmClassName implClassNameForDeserialized = UtilKt.getImplClassNameForDeserialized(descriptor);
        FqName fqNameForTopLevelClassMaybeWithDollars = implClassNameForDeserialized != null ? implClassNameForDeserialized.getFqNameForTopLevelClassMaybeWithDollars() : null;
        if (fqNameForTopLevelClassMaybeWithDollars != null) {
            return fqNameForTopLevelClassMaybeWithDollars;
        }
        throw new IllegalStateException(("No implClassName for " + descriptor).toString());
    }

    @JvmStatic
    public static final String getFileClassInternalName(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return JvmFileClassUtilKt.getInternalNameWithoutInnerClasses(getFileClassInfoNoResolve(file).getFileClassFqName());
    }

    @JvmStatic
    public static final String getFacadeClassInternalName(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return JvmFileClassUtilKt.getInternalNameWithoutInnerClasses(getFileClassInfoNoResolve(file).getFacadeClassFqName());
    }

    @JvmStatic
    public static final String manglePartName(String facadeName, String fileName) {
        Intrinsics.checkNotNullParameter(facadeName, "facadeName");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        return facadeName + JvmNames.MULTIFILE_PART_NAME_DELIMITER + PackagePartClassUtils.getFilePartShortName(fileName);
    }

    @JvmStatic
    public static final JvmFileClassInfo getFileClassInfoNoResolve(KtFile file) {
        FqName packageFqName;
        Intrinsics.checkNotNullParameter(file, "file");
        ParsedJvmFileClassAnnotations jvmNameOnFileNoResolve = INSTANCE.parseJvmNameOnFileNoResolve(file);
        if (jvmNameOnFileNoResolve == null || (packageFqName = jvmNameOnFileNoResolve.getJvmPackageName()) == null) {
            packageFqName = file.getPackageFqName();
        }
        if (jvmNameOnFileNoResolve == null) {
            String name2 = file.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            return new JvmSimpleFileClassInfo(PackagePartClassUtils.getPackagePartFqName(packageFqName, name2), false);
        }
        String jvmName = jvmNameOnFileNoResolve.getJvmName();
        if (jvmName == null) {
            String name3 = file.getName();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            jvmName = PackagePartClassUtils.getFilePartShortName(name3);
        }
        FqName fqNameChild = packageFqName.child(Name.identifier(jvmName));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        if (jvmNameOnFileNoResolve.getIsMultifileClass()) {
            String name4 = file.getName();
            Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
            FqName fqNameChild2 = packageFqName.child(Name.identifier(manglePartName(jvmName, name4)));
            Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
            return new JvmMultifileClassPartInfo(fqNameChild2, fqNameChild);
        }
        return new JvmSimpleFileClassInfo(fqNameChild, true);
    }

    private final ParsedJvmFileClassAnnotations parseJvmNameOnFileNoResolve(KtFile file) {
        String literalStringFromAnnotation;
        String literalStringFromAnnotation2;
        KtAnnotationEntry ktAnnotationEntryFindAnnotationEntryOnFileNoResolve = findAnnotationEntryOnFileNoResolve(file, JVM_NAME_SHORT);
        if (ktAnnotationEntryFindAnnotationEntryOnFileNoResolve == null || (literalStringFromAnnotation = getLiteralStringFromAnnotation(ktAnnotationEntryFindAnnotationEntryOnFileNoResolve)) == null || !Name.isValidIdentifier(literalStringFromAnnotation)) {
            literalStringFromAnnotation = null;
        }
        KtAnnotationEntry ktAnnotationEntryFindAnnotationEntryOnFileNoResolve2 = findAnnotationEntryOnFileNoResolve(file, JvmNames.INSTANCE.getJVM_PACKAGE_NAME_SHORT());
        FqName fqName = (ktAnnotationEntryFindAnnotationEntryOnFileNoResolve2 == null || (literalStringFromAnnotation2 = getLiteralStringFromAnnotation(ktAnnotationEntryFindAnnotationEntryOnFileNoResolve2)) == null) ? null : new FqName(literalStringFromAnnotation2);
        if (literalStringFromAnnotation == null && fqName == null) {
            return null;
        }
        return new ParsedJvmFileClassAnnotations(literalStringFromAnnotation, fqName, JvmFileClassUtilKt.isJvmMultifileClassFile(file));
    }

    @JvmStatic
    public static final KtAnnotationEntry findAnnotationEntryOnFileNoResolve(KtFile file, String shortName) {
        List<KtAnnotationEntry> annotationEntries;
        KtSimpleNameExpression constructorReferenceExpression;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(shortName, "shortName");
        KtFileAnnotationList fileAnnotationList = file.getFileAnnotationList();
        Object obj = null;
        if (fileAnnotationList == null || (annotationEntries = fileAnnotationList.getAnnotationEntries()) == null) {
            return null;
        }
        for (Object obj2 : annotationEntries) {
            KtConstructorCalleeExpression calleeExpression = ((KtAnnotationEntry) obj2).getCalleeExpression();
            if (Intrinsics.areEqual((calleeExpression == null || (constructorReferenceExpression = calleeExpression.getConstructorReferenceExpression()) == null) ? null : constructorReferenceExpression.getReferencedName(), shortName)) {
                obj = obj2;
                break;
            }
        }
        return (KtAnnotationEntry) obj;
    }

    public final String getLiteralStringFromAnnotation(KtAnnotationEntry annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        KtLiteralStringTemplateEntry literalStringEntryFromAnnotation = getLiteralStringEntryFromAnnotation(annotation);
        if (literalStringEntryFromAnnotation != null) {
            return literalStringEntryFromAnnotation.getText();
        }
        return null;
    }

    public final KtLiteralStringTemplateEntry getLiteralStringEntryFromAnnotation(KtAnnotationEntry annotation) {
        KtStringTemplateExpression stringTemplateExpression;
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        List<? extends ValueArgument> valueArguments = annotation.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        ValueArgument valueArgument = (ValueArgument) CollectionsKt.firstOrNull((List) valueArguments);
        if (valueArgument == null) {
            return null;
        }
        if (valueArgument instanceof KtValueArgument) {
            stringTemplateExpression = ((KtValueArgument) valueArgument).getStringTemplateExpression();
        } else {
            KtExpression argumentExpression = valueArgument.getArgumentExpression();
            stringTemplateExpression = argumentExpression instanceof KtStringTemplateExpression ? (KtStringTemplateExpression) argumentExpression : null;
        }
        if (stringTemplateExpression == null) {
            return null;
        }
        KtStringTemplateEntry[] entries = stringTemplateExpression.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        Object objSingleOrNull = ArraysKt.singleOrNull(entries);
        if (objSingleOrNull instanceof KtLiteralStringTemplateEntry) {
            return (KtLiteralStringTemplateEntry) objSingleOrNull;
        }
        return null;
    }
}
