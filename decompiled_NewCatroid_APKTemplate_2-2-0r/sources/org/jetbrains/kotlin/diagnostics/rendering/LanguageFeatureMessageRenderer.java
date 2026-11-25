package org.jetbrains.kotlin.diagnostics.rendering;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;

/* JADX INFO: compiled from: LanguageFeatureMessageRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001:\u0001\u0011B\u0019\b\u0007\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ$\u0010\r\u001a\u00020\f2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/LanguageFeatureMessageRenderer;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/diagnostics/rendering/LanguageFeatureMessageRenderer$Type;", "useHtml", "", "(Lorg/jetbrains/kotlin/diagnostics/rendering/LanguageFeatureMessageRenderer$Type;Z)V", "additionalFeatureMessages", "", "", "render", "obj", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "Type", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LanguageFeatureMessageRenderer implements DiagnosticParameterRenderer<Pair<? extends LanguageFeature, ? extends LanguageVersionSettings>> {
    private final Map<LanguageFeature, String> additionalFeatureMessages;
    private final Type type;
    private final boolean useHtml;

    /* JADX INFO: compiled from: LanguageFeatureMessageRenderer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/LanguageFeatureMessageRenderer$Type;", "", "(Ljava/lang/String;I)V", "UNSUPPORTED", "WARNING", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Type {
        UNSUPPORTED,
        WARNING;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Type> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: LanguageFeatureMessageRenderer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Type.values().length];
            try {
                iArr[Type.UNSUPPORTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Type.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LanguageFeatureMessageRenderer(Type type) {
        this(type, false, 2, null);
        Intrinsics.checkNotNullParameter(type, "type");
    }

    public LanguageFeatureMessageRenderer(Type type, boolean z) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.useHtml = z;
        this.additionalFeatureMessages = MapsKt.mapOf(TuplesKt.to(LanguageFeature.UnitConversionsOnArbitraryExpressions, "You can also change the original type of this expression to (...) -> Unit"));
    }

    public /* synthetic */ LanguageFeatureMessageRenderer(Type type, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(type, (i & 2) != 0 ? false : z);
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
    public String render(Pair<? extends LanguageFeature, ? extends LanguageVersionSettings> obj, RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
        LanguageFeature languageFeatureComponent1 = obj.component1();
        LanguageVersionSettings languageVersionSettingsComponent2 = obj.component2();
        LanguageVersion sinceVersion = languageFeatureComponent1.getSinceVersion();
        StringBuilder sb = new StringBuilder("The feature \"");
        sb.append(languageFeatureComponent1.getPresentableName()).append("\" is ");
        int i = WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()];
        if (i != 1) {
            if (i == 2) {
                sb.append("experimental");
            }
        } else if (sinceVersion == null) {
            sb.append("experimental and should be enabled explicitly");
        } else if (sinceVersion.compareTo(languageVersionSettingsComponent2.getLanguageVersion()) > 0) {
            sb.append("only available since language version ").append(sinceVersion.getVersionString());
        } else if (languageFeatureComponent1.getSinceApiVersion().compareTo(languageVersionSettingsComponent2.getApiVersion()) > 0) {
            sb.append("only available since API version ").append(languageFeatureComponent1.getSinceApiVersion().getVersionString());
        } else {
            sb.append(CompilerOptions.DISABLED);
        }
        String hintUrl = languageFeatureComponent1.getHintUrl();
        if (hintUrl != null) {
            if (this.useHtml) {
                sb.append(" (see more <a href=\"").append(hintUrl).append("\">here</a>)");
            } else {
                sb.append(" (see: ").append(hintUrl).append(Constant.AFTER_QUTO);
            }
        }
        if (this.additionalFeatureMessages.containsKey(languageFeatureComponent1)) {
            sb.append(". " + this.additionalFeatureMessages.get(languageFeatureComponent1));
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
