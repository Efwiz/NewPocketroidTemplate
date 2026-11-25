package org.jetbrains.kotlin.fir.analysis.diagnostics.p002native;

import kotlin.Metadata;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers;
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers;

/* JADX INFO: compiled from: FirNativeErrorsDefaultMessages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/diagnostics/native/FirNativeErrorsDefaultMessages;", "Lorg/jetbrains/kotlin/diagnostics/rendering/BaseDiagnosticRendererFactory;", "()V", "MAP", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "getMAP", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeErrorsDefaultMessages extends BaseDiagnosticRendererFactory {
    public static final FirNativeErrorsDefaultMessages INSTANCE = new FirNativeErrorsDefaultMessages();
    private static final KtDiagnosticFactoryToRendererMap MAP;

    private FirNativeErrorsDefaultMessages() {
    }

    static {
        KtDiagnosticFactoryToRendererMap ktDiagnosticFactoryToRendererMap = new KtDiagnosticFactoryToRendererMap("FIR");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getTHROWS_LIST_EMPTY(), "Throws must have non-empty class list");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINCOMPATIBLE_THROWS_OVERRIDE(), "Member overrides different @Throws filter from {0}", FirDiagnosticRenderers.INSTANCE.getSYMBOL());
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINCOMPATIBLE_THROWS_INHERITED(), "Member inherits different @Throws filters from {0}", FirDiagnosticRenderers.INSTANCE.getSYMBOLS());
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getMISSING_EXCEPTION_IN_THROWS_ON_SUSPEND(), "@Throws on suspend declaration must have {0} (or any of its superclasses) listed", KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINAPPLICABLE_SHARED_IMMUTABLE_PROPERTY(), "@SharedImmutable is applicable only to val with backing field or to property with delegation");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL(), "@SharedImmutable is applicable only to top level declarations");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINAPPLICABLE_THREAD_LOCAL(), "@ThreadLocal is applicable only to property with backing field, to property with delegation or to objects");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINAPPLICABLE_THREAD_LOCAL_TOP_LEVEL(), "@ThreadLocal is applicable only to top level declarations");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINVALID_CHARACTERS_NATIVE(), "Name {0}", KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getREDUNDANT_SWIFT_REFINEMENT(), "An ObjC refined declaration can't also be refined in Swift");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE(), "Refined declaration \"{0}\" overrides declarations with different or no refinement from {1}", FirDiagnosticRenderers.INSTANCE.getSYMBOL(), FirDiagnosticRenderers.INSTANCE.getSYMBOLS());
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINVALID_OBJC_HIDES_TARGETS(), "@HidesFromObjC annotation is only applicable to annotations with targets CLASS, FUNCTION and/or PROPERTY");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINVALID_REFINES_IN_SWIFT_TARGETS(), "@RefinesInSwift annotation is only applicable to annotations with targets FUNCTION and/or PROPERTY");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINAPPLICABLE_OBJC_NAME(), "@ObjCName is not applicable on overrides");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINVALID_OBJC_NAME(), "@ObjCName should have a name and/or swiftName");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getEMPTY_OBJC_NAME(), "Empty @ObjCName names aren't supported");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINVALID_OBJC_NAME_CHARS(), "@ObjCName contains illegal characters: {0}", KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINVALID_OBJC_NAME_FIRST_CHAR(), "@ObjCName contains illegal first characters: {0}", KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINCOMPATIBLE_OBJC_NAME_OVERRIDE(), "Member \"{0}\" inherits inconsistent @ObjCName from {1}", FirDiagnosticRenderers.INSTANCE.getSYMBOL(), FirDiagnosticRenderers.INSTANCE.getSYMBOLS());
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getINAPPLICABLE_EXACT_OBJC_NAME(), "Exact @ObjCName is only applicable to classes, objects and interfaces");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getMISSING_EXACT_OBJC_NAME(), "Exact @ObjCName is required to have an ObjC name");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getNON_LITERAL_OBJC_NAME_ARG(), "@ObjCName accepts only literal string and boolean values");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getSUBTYPE_OF_HIDDEN_FROM_OBJC(), "Only @HiddenFromObjC declaration can be a subtype of @HiddenFromObjC declaration");
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getCANNOT_CHECK_FOR_FORWARD_DECLARATION(), "Cannot check for forward declaration: ''{0}''", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getUNCHECKED_CAST_TO_FORWARD_DECLARATION(), "Unchecked cast to forward declaration: ''{0}'' to ''{1}''", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE(), FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getFORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT(), "Cannot pass forward declaration ''{0}'' for reified type parameter", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirNativeErrors.INSTANCE.getFORWARD_DECLARATION_AS_CLASS_LITERAL(), "Can't refer to forward declaration ''{0}'' from class literal", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        MAP = ktDiagnosticFactoryToRendererMap;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory
    public KtDiagnosticFactoryToRendererMap getMAP() {
        return MAP;
    }
}
