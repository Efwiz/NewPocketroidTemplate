package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.CharRange;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirNativeObjCNameChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003:\u0001 B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J \u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J(\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0016\u0010\u0019\u001a\u0004\u0018\u00010\u0018*\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u0018*\u0006\u0012\u0002\b\u00030\u001d2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u001e\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u001f*\u0006\u0012\u0002\b\u00030\u001d2\u0006\u0010\u001b\u001a\u00020\u001cR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeObjCNameChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "exactName", "Lorg/jetbrains/kotlin/name/Name;", "objCNameClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "swiftNameName", "validChars", "", "", "validFirstChars", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkDeclaration", "checkObjCName", "objCName", "Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeObjCNameChecker$ObjCName;", "getObjCName", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getObjCNames", "", "ObjCName", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeObjCNameChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirNativeObjCNameChecker INSTANCE = new FirNativeObjCNameChecker();
    private static final Name exactName;
    private static final ClassId objCNameClassId;
    private static final Name swiftNameName;
    private static final Set<Character> validChars;
    private static final Set<Character> validFirstChars;

    private FirNativeObjCNameChecker() {
    }

    static {
        ClassId classId = ClassId.topLevel(new FqName("kotlin.native.ObjCName"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        objCNameClassId = classId;
        Name nameIdentifier = Name.identifier("swiftName");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        swiftNameName = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("exact");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        exactName = nameIdentifier2;
        Set<Character> setPlus = SetsKt.plus((Set<? extends char>) SetsKt.plus(CollectionsKt.toSet(new CharRange('A', 'Z')), (Iterable) CollectionsKt.toSet(new CharRange('a', 'z'))), '_');
        validFirstChars = setPlus;
        validChars = SetsKt.plus((Set) setPlus, (Iterable) CollectionsKt.toSet(new CharRange(ExternalAnnotationProvider.NULLABLE, '9')));
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkDeclaration(declaration, context, reporter);
        if (declaration instanceof FirCallableDeclaration) {
            if ((declaration instanceof FirSimpleFunction) || (declaration instanceof FirProperty)) {
                Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getContainingDeclarations());
                FirClass firClass = objLastOrNull instanceof FirClass ? (FirClass) objLastOrNull : null;
                if (firClass != null) {
                    FirNativeObjCNameOverridesChecker.INSTANCE.check(FirHelpersKt.unsubstitutedScope(firClass, context), ((FirCallableDeclaration) declaration).getSymbol(), declaration, context, reporter);
                }
            }
        }
    }

    private final void checkDeclaration(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        if (declaration instanceof FirValueParameter) {
            return;
        }
        List listFilterNotNull = CollectionsKt.filterNotNull(getObjCNames(declaration.getSymbol(), context.getSession()));
        if (listFilterNotNull.isEmpty()) {
            return;
        }
        if ((declaration instanceof FirCallableDeclaration) && ((FirMemberDeclaration) declaration).getStatus().isOverride()) {
            Iterator it = listFilterNotNull.iterator();
            while (it.hasNext()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, ((ObjCName) it.next()).getAnnotation().getSource(), FirNativeErrors.INSTANCE.getINAPPLICABLE_OBJC_NAME(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        Iterator it2 = listFilterNotNull.iterator();
        while (it2.hasNext()) {
            INSTANCE.checkObjCName((ObjCName) it2.next(), declaration, context, reporter);
        }
    }

    private final void checkObjCName(ObjCName objCName, FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Character chFirstOrNull;
        String swiftName;
        Set setEmptySet;
        Set setEmptySet2;
        Set<Character> set;
        Set<Character> set2;
        Character chFirstOrNull2;
        KtSourceElement source = objCName.getAnnotation().getSource();
        Iterator<Map.Entry<Name, FirExpression>> it = objCName.getAnnotation().getArgumentMapping().getMapping().entrySet().iterator();
        while (it.hasNext()) {
            FirExpression value = it.next().getValue();
            if (!(value instanceof FirConstExpression)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, value.getSource(), FirNativeErrors.INSTANCE.getNON_LITERAL_OBJC_NAME_ARG(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        if (objCName.getName() == null && objCName.getSwiftName() == null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirNativeErrors.INSTANCE.getINVALID_OBJC_NAME(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        String name2 = objCName.getName();
        Character ch = null;
        if (name2 == null || (chFirstOrNull = StringsKt.firstOrNull(name2)) == null || validFirstChars.contains(Character.valueOf(chFirstOrNull.charValue()))) {
            chFirstOrNull = null;
        }
        String swiftName2 = objCName.getSwiftName();
        if (swiftName2 != null && (chFirstOrNull2 = StringsKt.firstOrNull(swiftName2)) != null && !validFirstChars.contains(Character.valueOf(chFirstOrNull2.charValue()))) {
            ch = chFirstOrNull2;
        }
        Set ofNotNull = SetsKt.setOfNotNull((Object[]) new Character[]{chFirstOrNull, ch});
        if (!ofNotNull.isEmpty()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirNativeErrors.INSTANCE.getINVALID_OBJC_NAME_FIRST_CHAR(), CollectionsKt.joinToString$default(ofNotNull, "", null, null, 0, null, null, 62, null), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
        String name3 = objCName.getName();
        if ((name3 != null && name3.length() == 0) || ((swiftName = objCName.getSwiftName()) != null && swiftName.length() == 0)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirNativeErrors.INSTANCE.getEMPTY_OBJC_NAME(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        String name4 = objCName.getName();
        if (name4 == null || (set2 = StringsKt.toSet(name4)) == null || (setEmptySet = CollectionsKt.subtract(set2, validChars)) == null) {
            setEmptySet = SetsKt.emptySet();
        }
        String swiftName3 = objCName.getSwiftName();
        if (swiftName3 == null || (set = StringsKt.toSet(swiftName3)) == null || (setEmptySet2 = CollectionsKt.subtract(set, validChars)) == null) {
            setEmptySet2 = SetsKt.emptySet();
        }
        if (!SetsKt.plus(setEmptySet, (Iterable) setEmptySet2).isEmpty()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirNativeErrors.INSTANCE.getINVALID_OBJC_NAME_CHARS(), CollectionsKt.joinToString$default(ofNotNull, "", null, null, 0, null, null, 62, null), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
        if (objCName.getExact() && (!(declaration instanceof FirClass) || ((FirClass) declaration).getClassKind() == ClassKind.ENUM_ENTRY)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirNativeErrors.INSTANCE.getINAPPLICABLE_EXACT_OBJC_NAME(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (objCName.getExact() && objCName.getName() == null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirNativeErrors.INSTANCE.getMISSING_EXACT_OBJC_NAME(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    /* JADX INFO: compiled from: FirNativeObjCNameChecker.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0011\u001a\u00020\b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeObjCNameChecker$ObjCName;", "", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;)V", "getAnnotation", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "exact", "", "getExact", "()Z", "name", "", "getName", "()Ljava/lang/String;", "swiftName", "getSwiftName", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ObjCName {
        private final FirAnnotation annotation;
        private final boolean exact;
        private final String name;
        private final String swiftName;

        public ObjCName(FirAnnotation annotation) {
            Intrinsics.checkNotNullParameter(annotation, "annotation");
            this.annotation = annotation;
            this.name = FirAnnotationUtilsKt.getStringArgument(annotation, StandardNames.NAME);
            this.swiftName = FirAnnotationUtilsKt.getStringArgument(annotation, FirNativeObjCNameChecker.swiftNameName);
            Boolean booleanArgument = FirAnnotationUtilsKt.getBooleanArgument(annotation, FirNativeObjCNameChecker.exactName);
            this.exact = booleanArgument != null ? booleanArgument.booleanValue() : false;
        }

        public final FirAnnotation getAnnotation() {
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

    private final ObjCName getObjCName(FirAnnotationContainer firAnnotationContainer, FirSession firSession) {
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firAnnotationContainer, objCNameClassId, firSession);
        if (annotationByClassId != null) {
            return new ObjCName(annotationByClassId);
        }
        return null;
    }

    private final ObjCName getObjCName(FirBasedSymbol<?> firBasedSymbol, FirSession firSession) {
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firBasedSymbol, objCNameClassId, firSession);
        if (annotationByClassId != null) {
            return new ObjCName(annotationByClassId);
        }
        return null;
    }

    public final List<ObjCName> getObjCNames(FirBasedSymbol<?> firBasedSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (firBasedSymbol instanceof FirFunctionSymbol) {
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            FirNativeObjCNameChecker firNativeObjCNameChecker = INSTANCE;
            listCreateListBuilder.add(firNativeObjCNameChecker.getObjCName(firBasedSymbol, session));
            FirFunctionSymbol firFunctionSymbol = (FirFunctionSymbol) firBasedSymbol;
            FirResolvedTypeRef resolvedReceiverTypeRef = firFunctionSymbol.getResolvedReceiverTypeRef();
            listCreateListBuilder.add(resolvedReceiverTypeRef != null ? firNativeObjCNameChecker.getObjCName(resolvedReceiverTypeRef, session) : null);
            FirReceiverParameter receiverParameter = firFunctionSymbol.getReceiverParameter();
            listCreateListBuilder.add(receiverParameter != null ? firNativeObjCNameChecker.getObjCName(receiverParameter, session) : null);
            Iterator<T> it = firFunctionSymbol.getValueParameterSymbols().iterator();
            while (it.hasNext()) {
                listCreateListBuilder.add(INSTANCE.getObjCName((FirValueParameterSymbol) it.next(), session));
            }
            return CollectionsKt.build(listCreateListBuilder);
        }
        return CollectionsKt.listOf(getObjCName(firBasedSymbol, session));
    }
}
