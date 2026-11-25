package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.renderer.RenderingUtilsKt;

/* JADX INFO: compiled from: ConeTypeRendererWithJavaFlexibleTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\b\u0016\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005B!\b\u0016\u0012\n\u0010\u0006\u001a\u00060\u0007j\u0002`\b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011H\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRendererWithJavaFlexibleTypes;", "Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRenderer;", "idRendererCreator", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/fir/renderer/ConeIdRenderer;", "(Lkotlin/jvm/functions/Function0;)V", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "(Ljava/lang/StringBuilder;Lkotlin/jvm/functions/Function0;)V", "render", "", "flexibleType", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralType;", "renderFlexibleType", "", "lowerRendered", "upperRendered", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeRendererWithJavaFlexibleTypes extends ConeTypeRenderer {
    private final Function0<ConeIdRenderer> idRendererCreator;

    /* JADX WARN: Multi-variable type inference failed */
    public ConeTypeRendererWithJavaFlexibleTypes(Function0<? extends ConeIdRenderer> idRendererCreator) {
        Intrinsics.checkNotNullParameter(idRendererCreator, "idRendererCreator");
        this.idRendererCreator = idRendererCreator;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ConeTypeRendererWithJavaFlexibleTypes(StringBuilder builder, Function0<? extends ConeIdRenderer> idRendererCreator) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(idRendererCreator, "idRendererCreator");
        setBuilder(builder);
        this.idRendererCreator = idRendererCreator;
        setIdRenderer((ConeIdRenderer) idRendererCreator.invoke());
        getIdRenderer().setBuilder(builder);
    }

    @Override // org.jetbrains.kotlin.fir.renderer.ConeTypeRenderer
    protected void render(ConeFlexibleType flexibleType) {
        Intrinsics.checkNotNullParameter(flexibleType, "flexibleType");
        ConeTypeRendererWithJavaFlexibleTypes coneTypeRendererWithJavaFlexibleTypes = new ConeTypeRendererWithJavaFlexibleTypes(new StringBuilder(), this.idRendererCreator);
        coneTypeRendererWithJavaFlexibleTypes.render((ConeKotlinType) flexibleType.getLowerBound());
        ConeTypeRendererWithJavaFlexibleTypes coneTypeRendererWithJavaFlexibleTypes2 = new ConeTypeRendererWithJavaFlexibleTypes(new StringBuilder(), this.idRendererCreator);
        coneTypeRendererWithJavaFlexibleTypes2.render((ConeKotlinType) flexibleType.getUpperBound());
        StringBuilder builder = getBuilder();
        String string = coneTypeRendererWithJavaFlexibleTypes.getBuilder().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String string2 = coneTypeRendererWithJavaFlexibleTypes2.getBuilder().toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        builder.append(renderFlexibleType(string, string2));
    }

    private final String renderFlexibleType(String lowerRendered, String upperRendered) {
        if (RenderingUtilsKt.typeStringsDifferOnlyInNullability(lowerRendered, upperRendered)) {
            if (StringsKt.startsWith$default(upperRendered, "(", false, 2, (Object) null)) {
                return "(" + lowerRendered + ")!";
            }
            return lowerRendered + '!';
        }
        StringBuilder sb = new StringBuilder();
        String strAsString = StandardNames.COLLECTIONS_PACKAGE_FQ_NAME.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String string = sb.append(StringsKt.replace$default(strAsString, ".", "/", false, 4, (Object) null)).append('/').toString();
        String strReplacePrefixesInTypeRepresentations = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, string + "Mutable", upperRendered, string, string + "(Mutable)");
        if (strReplacePrefixesInTypeRepresentations != null) {
            return strReplacePrefixesInTypeRepresentations;
        }
        String strReplacePrefixesInTypeRepresentations2 = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, string + "MutableMap.MutableEntry", upperRendered, string + "Map.Entry", string + "(Mutable)Map.(Mutable)Entry");
        if (strReplacePrefixesInTypeRepresentations2 != null) {
            return strReplacePrefixesInTypeRepresentations2;
        }
        String str = StandardNames.BUILT_INS_PACKAGE_FQ_NAME.asString() + '/';
        String strReplacePrefixesInTypeRepresentations3 = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, str + "Array<", upperRendered, str + "Array<out ", str + "Array<(out) ");
        return strReplacePrefixesInTypeRepresentations3 != null ? strReplacePrefixesInTypeRepresentations3 : "ft<" + lowerRendered + ", " + upperRendered + '>';
    }

    @Override // org.jetbrains.kotlin.fir.renderer.ConeTypeRenderer
    protected void render(ConeIntegerLiteralType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        render((ConeKotlinType) ConeIntegerLiteralType.getApproximatedType$default(type, null, 1, null));
    }
}
