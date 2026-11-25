package org.jetbrains.kotlin.checkers.diagnostics;

import com.google.android.gms.cast.MediaTrack;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.checkers.diagnostics.factories.DebugInfoDiagnosticFactory1;
import org.jetbrains.kotlin.checkers.utils.CheckerTestUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.rendering.AbstractDiagnosticWithParametersRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticWithParameters1Renderer;
import org.jetbrains.kotlin.diagnostics.rendering.Renderers;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: TextDiagnostic.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\u0018\u0000 #2\u00020\u0001:\u0002#$B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u001a\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u0018J\u0011\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0001H\u0096\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\r\u001a\u00020\bH\u0016J\u0013\u0010\u001f\u001a\u00020\u00182\b\u0010\u001c\u001a\u0004\u0018\u00010 H\u0096\u0002J\b\u0010!\u001a\u00020\u001bH\u0016J\b\u0010\"\u001a\u00020\u0003H\u0016R\u0011\u0010\n\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u0019\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\f¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic;", "Lorg/jetbrains/kotlin/checkers/diagnostics/AbstractTestDiagnostic;", "name", "", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "parameters", "", "inference", "Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$InferenceCompatibility;", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$InferenceCompatibility;)V", MediaTrack.ROLE_DESCRIPTION, "getDescription", "()Ljava/lang/String;", "inferenceCompatibility", "getInferenceCompatibility", "()Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$InferenceCompatibility;", "setInferenceCompatibility", "(Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$InferenceCompatibility;)V", "getName", "getParameters", "()Ljava/util/List;", "getPlatform", "asString", "withNewInference", "", "renderParameters", "compareTo", "", "other", "enhanceInferenceCompatibility", "", Namer.EQUALS_METHOD_NAME, "", "hashCode", "toString", "Companion", "InferenceCompatibility", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TextDiagnostic implements AbstractTestDiagnostic {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Pattern crossPlatformLineBreak = Pattern.compile("\\r?\\n");
    private InferenceCompatibility inferenceCompatibility;
    private final String name;
    private final List<String> parameters;
    private final String platform;

    public TextDiagnostic(String name2, String str, List<String> list, InferenceCompatibility inferenceCompatibility) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
        this.platform = str;
        this.parameters = list;
        this.inferenceCompatibility = inferenceCompatibility == null ? InferenceCompatibility.ALL : inferenceCompatibility;
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.AbstractTestDiagnostic
    public String getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.AbstractTestDiagnostic
    public String getPlatform() {
        return this.platform;
    }

    public final List<String> getParameters() {
        return this.parameters;
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.AbstractTestDiagnostic
    public InferenceCompatibility getInferenceCompatibility() {
        return this.inferenceCompatibility;
    }

    public void setInferenceCompatibility(InferenceCompatibility inferenceCompatibility) {
        Intrinsics.checkNotNullParameter(inferenceCompatibility, "<set-?>");
        this.inferenceCompatibility = inferenceCompatibility;
    }

    public final String getDescription() {
        return (getPlatform() != null ? getPlatform() + ':' : "") + getName();
    }

    /* JADX INFO: compiled from: TextDiagnostic.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0000R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$InferenceCompatibility;", "", "abbreviation", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getAbbreviation$frontend", "()Ljava/lang/String;", "setAbbreviation$frontend", "(Ljava/lang/String;)V", "isCompatible", "", "other", "NEW", "OLD", "ALL", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum InferenceCompatibility {
        NEW(CheckerTestUtil.NEW_INFERENCE_PREFIX),
        OLD(CheckerTestUtil.OLD_INFERENCE_PREFIX),
        ALL(null);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private String abbreviation;

        public static EnumEntries<InferenceCompatibility> getEntries() {
            return $ENTRIES;
        }

        InferenceCompatibility(String str) {
            this.abbreviation = str;
        }

        /* JADX INFO: renamed from: getAbbreviation$frontend, reason: from getter */
        public final String getAbbreviation() {
            return this.abbreviation;
        }

        public final void setAbbreviation$frontend(String str) {
            this.abbreviation = str;
        }

        public final boolean isCompatible(InferenceCompatibility other) {
            InferenceCompatibility inferenceCompatibility;
            Intrinsics.checkNotNullParameter(other, "other");
            return this == other || this == (inferenceCompatibility = ALL) || other == inferenceCompatibility;
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(AbstractTestDiagnostic other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return getName().compareTo(other.getName());
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.AbstractTestDiagnostic
    public void enhanceInferenceCompatibility(InferenceCompatibility inferenceCompatibility) {
        Intrinsics.checkNotNullParameter(inferenceCompatibility, "inferenceCompatibility");
        setInferenceCompatibility(inferenceCompatibility);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !Intrinsics.areEqual(getClass(), other.getClass())) {
            return false;
        }
        TextDiagnostic textDiagnostic = (TextDiagnostic) other;
        if (!Intrinsics.areEqual(getName(), textDiagnostic.getName())) {
            return false;
        }
        if (getPlatform() == null ? textDiagnostic.getPlatform() != null : !Intrinsics.areEqual(getPlatform(), textDiagnostic.getPlatform())) {
            return false;
        }
        List<String> list = this.parameters;
        if (list == null ? textDiagnostic.parameters == null : Intrinsics.areEqual(list, textDiagnostic.parameters)) {
            return getInferenceCompatibility() == textDiagnostic.getInferenceCompatibility();
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = getName().hashCode() * 31;
        String platform = getPlatform();
        int iHashCode2 = (iHashCode + (platform != null ? platform.hashCode() : 0)) * 31;
        List<String> list = this.parameters;
        return ((iHashCode2 + (list != null ? list.hashCode() : 0)) * 31) + getInferenceCompatibility().hashCode();
    }

    public static /* synthetic */ String asString$default(TextDiagnostic textDiagnostic, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return textDiagnostic.asString(z, z2);
    }

    public final String asString(boolean withNewInference, boolean renderParameters) {
        StringBuilder sb = new StringBuilder();
        if (withNewInference && getInferenceCompatibility().getAbbreviation() != null) {
            sb.append(getInferenceCompatibility().getAbbreviation());
            sb.append(";");
        }
        if (getPlatform() != null) {
            sb.append(getPlatform());
            sb.append(":");
        }
        sb.append(getName());
        if (renderParameters && this.parameters != null) {
            sb.append("(");
            List<String> list = this.parameters;
            final AnonymousClass1 anonymousClass1 = new Function1<String, String>() { // from class: org.jetbrains.kotlin.checkers.diagnostics.TextDiagnostic.asString.1
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(String str) {
                    return "\"" + TextDiagnostic.crossPlatformLineBreak.matcher(str).replaceAll(" ") + '\"';
                }
            };
            sb.append(StringUtil.join((Collection) list, new Function() { // from class: org.jetbrains.kotlin.checkers.diagnostics.TextDiagnostic$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return TextDiagnostic.asString$lambda$0(anonymousClass1, obj);
                }
            }, ", "));
            sb.append(Constant.AFTER_QUTO);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String asString$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (String) tmp0.invoke(obj);
    }

    public String toString() {
        return asString$default(this, false, false, 3, null);
    }

    /* JADX INFO: compiled from: TextDiagnostic.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002J\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0012\u001a\u00020\u000fH\u0002J\u000e\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u000fR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$Companion;", "", "()V", "crossPlatformLineBreak", "Ljava/util/regex/Pattern;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "asTextDiagnostic", "Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic;", "abstractTestDiagnostic", "Lorg/jetbrains/kotlin/checkers/diagnostics/AbstractTestDiagnostic;", "actualDiagnostic", "Lorg/jetbrains/kotlin/checkers/diagnostics/ActualDiagnostic;", "computeInferenceCompatibility", "Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic$InferenceCompatibility;", "abbreviation", "", "extractDataBefore", "prefix", "anchor", "parseDiagnostic", "text", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final TextDiagnostic parseDiagnostic(String text) {
            Intrinsics.checkNotNullParameter(text, "text");
            Matcher matcher = CheckerTestUtil.INSTANCE.getIndividualDiagnosticPattern().matcher(text);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Could not parse diagnostic: " + text);
            }
            InferenceCompatibility inferenceCompatibilityComputeInferenceCompatibility = computeInferenceCompatibility(extractDataBefore(matcher.group(1), ";"));
            String strExtractDataBefore = extractDataBefore(matcher.group(2), ":");
            String strGroup = matcher.group(3);
            String strGroup2 = matcher.group(5);
            if (strGroup2 == null) {
                Intrinsics.checkNotNull(strGroup);
                return new TextDiagnostic(strGroup, strExtractDataBefore, null, inferenceCompatibilityComputeInferenceCompatibility);
            }
            Intrinsics.checkNotNull(strGroup);
            return new TextDiagnostic(strGroup, strExtractDataBefore, new Regex("\",\\s*\"").split(StringsKt.trim(strGroup2, '\"'), 0), inferenceCompatibilityComputeInferenceCompatibility);
        }

        private final InferenceCompatibility computeInferenceCompatibility(String abbreviation) {
            if (abbreviation == null) {
                return InferenceCompatibility.ALL;
            }
            InferenceCompatibility inferenceCompatibility = null;
            boolean z = false;
            for (InferenceCompatibility inferenceCompatibility2 : InferenceCompatibility.values()) {
                if (Intrinsics.areEqual(abbreviation, inferenceCompatibility2.getAbbreviation())) {
                    if (z) {
                        throw new IllegalArgumentException("Array contains more than one matching element.");
                    }
                    z = true;
                    inferenceCompatibility = inferenceCompatibility2;
                }
            }
            if (z) {
                return inferenceCompatibility;
            }
            throw new NoSuchElementException("Array contains no element matching the predicate.");
        }

        private final String extractDataBefore(String prefix, String anchor) {
            if (prefix != null) {
                StringsKt.endsWith$default(prefix, anchor, false, 2, (Object) null);
            }
            if (prefix != null) {
                return StringsKt.substringBeforeLast(prefix, anchor, prefix);
            }
            return null;
        }

        public final TextDiagnostic asTextDiagnostic(AbstractTestDiagnostic abstractTestDiagnostic) {
            Intrinsics.checkNotNullParameter(abstractTestDiagnostic, "abstractTestDiagnostic");
            if (abstractTestDiagnostic instanceof ActualDiagnostic) {
                return asTextDiagnostic((ActualDiagnostic) abstractTestDiagnostic);
            }
            return (TextDiagnostic) abstractTestDiagnostic;
        }

        private final TextDiagnostic asTextDiagnostic(ActualDiagnostic actualDiagnostic) {
            DiagnosticWithParameters1Renderer rendererForDiagnostic;
            Diagnostic diagnostic = actualDiagnostic.getDiagnostic();
            if (diagnostic.getFactory() instanceof DebugInfoDiagnosticFactory1) {
                rendererForDiagnostic = new DiagnosticWithParameters1Renderer("{0}", Renderers.TO_STRING);
            } else {
                rendererForDiagnostic = DefaultErrorMessages.getRendererForDiagnostic(diagnostic);
            }
            String name2 = actualDiagnostic.getName();
            if (rendererForDiagnostic instanceof AbstractDiagnosticWithParametersRenderer) {
                List map = ContainerUtil.map(((AbstractDiagnosticWithParametersRenderer) rendererForDiagnostic).renderParameters(diagnostic), new Function() { // from class: org.jetbrains.kotlin.checkers.diagnostics.TextDiagnostic$Companion$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return String.valueOf(obj);
                    }
                });
                Intrinsics.checkNotNullExpressionValue(map, "map(...)");
                return new TextDiagnostic(name2, actualDiagnostic.getPlatform(), map, actualDiagnostic.getInferenceCompatibility());
            }
            return new TextDiagnostic(name2, actualDiagnostic.getPlatform(), null, actualDiagnostic.getInferenceCompatibility());
        }
    }
}
