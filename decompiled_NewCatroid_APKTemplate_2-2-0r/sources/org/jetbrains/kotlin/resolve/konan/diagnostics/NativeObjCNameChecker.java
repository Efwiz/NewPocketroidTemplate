package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.CharRange;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;

/* JADX INFO: compiled from: NativeObjCNameChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0018B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J \u0010\u0011\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J(\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u000e\u0010\u0015\u001a\u0004\u0018\u00010\u0014*\u00020\u000eH\u0002J\u0012\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0017*\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeObjCNameChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "objCNameFqName", "Lorg/jetbrains/kotlin/name/FqName;", "validChars", "", "", "validFirstChars", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkDeclaration", "checkObjCName", "objCName", "Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeObjCNameChecker$ObjCName;", "getObjCName", "getObjCNames", "", "ObjCName", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeObjCNameChecker implements DeclarationChecker {
    public static final NativeObjCNameChecker INSTANCE = new NativeObjCNameChecker();
    private static final FqName objCNameFqName = new FqName("kotlin.native.ObjCName");
    private static final Set<Character> validChars;
    private static final Set<Character> validFirstChars;

    private NativeObjCNameChecker() {
    }

    static {
        Set<Character> setPlus = SetsKt.plus((Set<? extends char>) SetsKt.plus(CollectionsKt.toSet(new CharRange('A', 'Z')), (Iterable) CollectionsKt.toSet(new CharRange('a', 'z'))), '_');
        validFirstChars = setPlus;
        validChars = SetsKt.plus((Set) setPlus, (Iterable) CollectionsKt.toSet(new CharRange(ExternalAnnotationProvider.NULLABLE, '9')));
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        checkDeclaration(declaration, descriptor, context);
        if (descriptor instanceof CallableMemberDescriptor) {
            NativeObjCNameOverridesChecker.INSTANCE.check(declaration, (CallableMemberDescriptor) descriptor, context);
        }
    }

    private final void checkDeclaration(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        List listFilterNotNull = CollectionsKt.filterNotNull(getObjCNames(descriptor));
        if (listFilterNotNull.isEmpty()) {
            return;
        }
        if (descriptor instanceof CallableMemberDescriptor) {
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = ((CallableMemberDescriptor) descriptor).getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            if (!overriddenDescriptors.isEmpty()) {
                Iterator it = listFilterNotNull.iterator();
                while (it.hasNext()) {
                    KtElement sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation(((ObjCName) it.next()).getAnnotation());
                    if (sourceFromAnnotation == null) {
                        sourceFromAnnotation = declaration;
                    }
                    context.getTrace().report(ErrorsNative.INAPPLICABLE_OBJC_NAME.on(sourceFromAnnotation));
                }
            }
        }
        Iterator it2 = listFilterNotNull.iterator();
        while (it2.hasNext()) {
            INSTANCE.checkObjCName((ObjCName) it2.next(), declaration, descriptor, context);
        }
    }

    private final void checkObjCName(ObjCName objCName, KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        String swiftName;
        Character chFirstOrNull;
        Set setEmptySet;
        Set setEmptySet2;
        Set<Character> set;
        Set<Character> set2;
        Character chFirstOrNull2;
        List<? extends ValueArgument> valueArguments;
        KtAnnotationEntry sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation(objCName.getAnnotation());
        if (sourceFromAnnotation != null && (valueArguments = sourceFromAnnotation.getValueArguments()) != null) {
            for (ValueArgument valueArgument : valueArguments) {
                KtExpression argumentExpression = valueArgument.getArgumentExpression();
                if (argumentExpression != null && !(argumentExpression instanceof KtConstantExpression) && !(argumentExpression instanceof KtStringTemplateExpression) && (!(valueArgument instanceof KtValueArgument) || ((KtValueArgument) valueArgument).getStringTemplateExpression() == null)) {
                    context.getTrace().report(ErrorsNative.NON_LITERAL_OBJC_NAME_ARG.on(argumentExpression));
                }
            }
        }
        KtDeclaration ktDeclaration = sourceFromAnnotation != null ? sourceFromAnnotation : declaration;
        if (objCName.getName() == null && objCName.getSwiftName() == null) {
            context.getTrace().report(ErrorsNative.INVALID_OBJC_NAME.on(ktDeclaration));
        }
        String name2 = objCName.getName();
        if ((name2 != null && name2.length() == 0) || ((swiftName = objCName.getSwiftName()) != null && swiftName.length() == 0)) {
            context.getTrace().report(ErrorsNative.EMPTY_OBJC_NAME.on(ktDeclaration));
        }
        String name3 = objCName.getName();
        Character ch = null;
        if (name3 == null || (chFirstOrNull = StringsKt.firstOrNull(name3)) == null || validFirstChars.contains(Character.valueOf(chFirstOrNull.charValue()))) {
            chFirstOrNull = null;
        }
        String swiftName2 = objCName.getSwiftName();
        if (swiftName2 != null && (chFirstOrNull2 = StringsKt.firstOrNull(swiftName2)) != null && !validFirstChars.contains(Character.valueOf(chFirstOrNull2.charValue()))) {
            ch = chFirstOrNull2;
        }
        Set ofNotNull = SetsKt.setOfNotNull((Object[]) new Character[]{chFirstOrNull, ch});
        if (!ofNotNull.isEmpty()) {
            context.getTrace().report(ErrorsNative.INVALID_OBJC_NAME_FIRST_CHAR.on(ktDeclaration, CollectionsKt.joinToString$default(ofNotNull, "", null, null, 0, null, null, 62, null)));
        }
        String name4 = objCName.getName();
        if (name4 == null || (set2 = StringsKt.toSet(name4)) == null || (setEmptySet = CollectionsKt.subtract(set2, validChars)) == null) {
            setEmptySet = SetsKt.emptySet();
        }
        String swiftName3 = objCName.getSwiftName();
        if (swiftName3 == null || (set = StringsKt.toSet(swiftName3)) == null || (setEmptySet2 = CollectionsKt.subtract(set, validChars)) == null) {
            setEmptySet2 = SetsKt.emptySet();
        }
        Set setPlus = SetsKt.plus(setEmptySet, (Iterable) setEmptySet2);
        if (!setPlus.isEmpty()) {
            context.getTrace().report(ErrorsNative.INVALID_OBJC_NAME_CHARS.on(ktDeclaration, CollectionsKt.joinToString$default(setPlus, "", null, null, 0, null, null, 62, null)));
        }
        if (objCName.getExact() && (!(descriptor instanceof ClassDescriptor) || ((ClassDescriptor) descriptor).getKind() == ClassKind.ENUM_ENTRY)) {
            context.getTrace().report(ErrorsNative.INAPPLICABLE_EXACT_OBJC_NAME.on(ktDeclaration));
        }
        if (objCName.getExact() && objCName.getName() == null) {
            context.getTrace().report(ErrorsNative.MISSING_EXACT_OBJC_NAME.on(ktDeclaration));
        }
    }

    /* JADX INFO: compiled from: NativeObjCNameChecker.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0011\u001a\u00020\b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeObjCNameChecker$ObjCName;", "", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;)V", "getAnnotation", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "exact", "", "getExact", "()Z", "name", "", "getName", "()Ljava/lang/String;", "swiftName", "getSwiftName", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ObjCName {
        private final AnnotationDescriptor annotation;
        private final boolean exact;
        private final String name;
        private final String swiftName;

        public ObjCName(AnnotationDescriptor annotation) {
            Intrinsics.checkNotNullParameter(annotation, "annotation");
            this.annotation = annotation;
            ConstantValue<?> constantValueArgumentValue = AnnotationUtilKt.argumentValue(annotation, "name");
            Object value = constantValueArgumentValue != null ? constantValueArgumentValue.getValue() : null;
            this.name = value instanceof String ? (String) value : null;
            ConstantValue<?> constantValueArgumentValue2 = AnnotationUtilKt.argumentValue(annotation, "swiftName");
            Object value2 = constantValueArgumentValue2 != null ? constantValueArgumentValue2.getValue() : null;
            this.swiftName = value2 instanceof String ? (String) value2 : null;
            ConstantValue<?> constantValueArgumentValue3 = AnnotationUtilKt.argumentValue(annotation, "exact");
            Object value3 = constantValueArgumentValue3 != null ? constantValueArgumentValue3.getValue() : null;
            Boolean bool = value3 instanceof Boolean ? (Boolean) value3 : null;
            this.exact = bool != null ? bool.booleanValue() : false;
        }

        public final AnnotationDescriptor getAnnotation() {
            return this.annotation;
        }

        public final String getName() {
            return this.name;
        }

        public final String getSwiftName() {
            return this.swiftName;
        }

        public final boolean getExact() {
            return this.exact;
        }

        public boolean equals(Object other) {
            if (other instanceof ObjCName) {
                ObjCName objCName = (ObjCName) other;
                if (Intrinsics.areEqual(this.name, objCName.name) && Intrinsics.areEqual(this.swiftName, objCName.swiftName) && this.exact == objCName.exact) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            String str = this.name;
            int iHashCode = (str != null ? str.hashCode() : 0) * 31;
            String str2 = this.swiftName;
            return ((iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31) + Boolean.hashCode(this.exact);
        }
    }

    private final ObjCName getObjCName(DeclarationDescriptor declarationDescriptor) {
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = declarationDescriptor.getAnnotations().mo6752findAnnotation(objCNameFqName);
        if (annotationDescriptorMo6752findAnnotation != null) {
            return new ObjCName(annotationDescriptorMo6752findAnnotation);
        }
        return null;
    }

    public final List<ObjCName> getObjCNames(DeclarationDescriptor declarationDescriptor) {
        ObjCName objCName;
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (declarationDescriptor instanceof FunctionDescriptor) {
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            NativeObjCNameChecker nativeObjCNameChecker = INSTANCE;
            listCreateListBuilder.add(nativeObjCNameChecker.getObjCName(declarationDescriptor));
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) declarationDescriptor;
            ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                Intrinsics.checkNotNull(extensionReceiverParameter);
                objCName = nativeObjCNameChecker.getObjCName(extensionReceiverParameter);
            } else {
                objCName = null;
            }
            listCreateListBuilder.add(objCName);
            List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            for (ValueParameterDescriptor valueParameterDescriptor : valueParameters) {
                NativeObjCNameChecker nativeObjCNameChecker2 = INSTANCE;
                Intrinsics.checkNotNull(valueParameterDescriptor);
                listCreateListBuilder.add(nativeObjCNameChecker2.getObjCName(valueParameterDescriptor));
            }
            return CollectionsKt.build(listCreateListBuilder);
        }
        return CollectionsKt.listOf(getObjCName(declarationDescriptor));
    }
}
