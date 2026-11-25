package org.jetbrains.kotlin.resolve.konan.diagnostics;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticFactoryToRendererMap;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRendererKt;
import org.jetbrains.kotlin.diagnostics.rendering.Renderers;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: DefaultErrorMessagesNative.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001b\u0010\u0000\u001a\u00020\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0006"}, d2 = {"DIAGNOSTIC_FACTORY_TO_RENDERER", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticFactoryToRendererMap;", "getDIAGNOSTIC_FACTORY_TO_RENDERER", "()Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticFactoryToRendererMap;", "DIAGNOSTIC_FACTORY_TO_RENDERER$delegate", "Lkotlin/Lazy;", "frontend.native"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DefaultErrorMessagesNativeKt {
    private static final Lazy DIAGNOSTIC_FACTORY_TO_RENDERER$delegate = LazyKt.lazy(new Function0<DiagnosticFactoryToRendererMap>() { // from class: org.jetbrains.kotlin.resolve.konan.diagnostics.DefaultErrorMessagesNativeKt$DIAGNOSTIC_FACTORY_TO_RENDERER$2
        @Override // kotlin.jvm.functions.Function0
        public final DiagnosticFactoryToRendererMap invoke() {
            DiagnosticFactoryToRendererMap diagnosticFactoryToRendererMap = new DiagnosticFactoryToRendererMap("Native");
            diagnosticFactoryToRendererMap.put(ErrorsNative.THROWS_LIST_EMPTY, "@Throws must have non-empty class list");
            diagnosticFactoryToRendererMap.put(ErrorsNative.INCOMPATIBLE_THROWS_OVERRIDE, "Member overrides different @Throws filter from {0}", Renderers.NAME);
            diagnosticFactoryToRendererMap.put(ErrorsNative.INCOMPATIBLE_THROWS_INHERITED, "Member inherits different @Throws filters from {0}", CommonRenderers.commaSeparated(Renderers.NAME));
            diagnosticFactoryToRendererMap.put(ErrorsNative.MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND, "@Throws on suspend declaration must have {0} (or any of its superclasses) listed", DiagnosticParameterRendererKt.Renderer(new Function1<FqName, String>() { // from class: org.jetbrains.kotlin.resolve.konan.diagnostics.DefaultErrorMessagesNativeKt$DIAGNOSTIC_FACTORY_TO_RENDERER$2$1$1
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(FqName fqName) {
                    String strAsString = fqName.shortName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    return strAsString;
                }
            }));
            diagnosticFactoryToRendererMap.put(ErrorsNative.INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY, "@SharedImmutable is applicable only to val with backing field or to property with delegation");
            diagnosticFactoryToRendererMap.put(ErrorsNative.INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL, "@SharedImmutable is applicable only to top level declarations");
            diagnosticFactoryToRendererMap.put(ErrorsNative.INAPPLICABLE_THREAD_LOCAL, "@ThreadLocal is applicable only to property with backing field, to property with delegation or to objects");
            diagnosticFactoryToRendererMap.put(ErrorsNative.INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL, "@ThreadLocal is applicable only to top level declarations");
            diagnosticFactoryToRendererMap.put(ErrorsNative.INVALID_CHARACTERS_NATIVE, "Name {0}", CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsNative.INAPPLICABLE_OBJC_NAME, "@ObjCName is not applicable on overrides");
            diagnosticFactoryToRendererMap.put(ErrorsNative.INVALID_OBJC_NAME, "@ObjCName should have a name and/or swiftName");
            diagnosticFactoryToRendererMap.put(ErrorsNative.INVALID_OBJC_NAME_CHARS, "@ObjCName contains illegal characters: {0}", CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsNative.INVALID_OBJC_NAME_FIRST_CHAR, "@ObjCName contains illegal first characters: {0}", CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsNative.EMPTY_OBJC_NAME, "Empty @ObjCName names aren't supported");
            diagnosticFactoryToRendererMap.put(ErrorsNative.INCOMPATIBLE_OBJC_NAME_OVERRIDE, "Member \"{0}\" inherits inconsistent @ObjCName from {1}", Renderers.NAME, CommonRenderers.commaSeparated(Renderers.NAME));
            diagnosticFactoryToRendererMap.put(ErrorsNative.INAPPLICABLE_EXACT_OBJC_NAME, "Exact @ObjCName is only applicable to classes, objects and interfaces");
            diagnosticFactoryToRendererMap.put(ErrorsNative.MISSING_EXACT_OBJC_NAME, "Exact @ObjCName is required to have an ObjC name");
            diagnosticFactoryToRendererMap.put(ErrorsNative.NON_LITERAL_OBJC_NAME_ARG, "@ObjCName accepts only literal string and boolean values");
            diagnosticFactoryToRendererMap.put(ErrorsNative.REDUNDANT_SWIFT_REFINEMENT, "An ObjC refined declaration can't also be refined in Swift");
            diagnosticFactoryToRendererMap.put(ErrorsNative.INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE, "Refined declaration \"{0}\" overrides declarations with different or no refinement from {1}", Renderers.NAME, CommonRenderers.commaSeparated(Renderers.NAME));
            diagnosticFactoryToRendererMap.put(ErrorsNative.INVALID_OBJC_HIDES_TARGETS, "@HidesFromObjC annotation is only applicable to annotations with targets CLASS, FUNCTION and/or PROPERTY");
            diagnosticFactoryToRendererMap.put(ErrorsNative.INVALID_REFINES_IN_SWIFT_TARGETS, "@RefinesInSwift annotation is only applicable to annotations with targets FUNCTION and/or PROPERTY");
            diagnosticFactoryToRendererMap.put(ErrorsNative.SUBTYPE_OF_HIDDEN_FROM_OBJC, "Only @HiddenFromObjC declaration can be a subtype of @HiddenFromObjC declaration");
            diagnosticFactoryToRendererMap.put(ErrorsNative.CANNOT_CHECK_FOR_FORWARD_DECLARATION, "Cannot check for forward declaration: ''{0}''", Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsNative.UNCHECKED_CAST_TO_FORWARD_DECLARATION, "Unchecked cast to forward declaration: ''{0}'' to ''{1}''", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsNative.FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT, "Cannot pass forward declaration ''{0}'' for reified type parameter", Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsNative.FORWARD_DECLARATION_AS_CLASS_LITERAL, "Can't refer to forward declaration ''{0}'' from class literal", Renderers.RENDER_TYPE);
            return diagnosticFactoryToRendererMap;
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final DiagnosticFactoryToRendererMap getDIAGNOSTIC_FACTORY_TO_RENDERER() {
        return (DiagnosticFactoryToRendererMap) DIAGNOSTIC_FACTORY_TO_RENDERER$delegate.getValue();
    }
}
