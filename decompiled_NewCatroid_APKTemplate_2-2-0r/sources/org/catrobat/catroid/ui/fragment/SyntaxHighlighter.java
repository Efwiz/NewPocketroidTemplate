package org.catrobat.catroid.ui.fragment;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/SyntaxHighlighter.class
  classes14.dex
 */
/* JADX INFO: compiled from: CommandPromptDialogFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/SyntaxHighlighter;", "", "()V", "COLOR_COMMAND", "", "COLOR_ERROR", "COLOR_SUCCESS", "COLOR_WARNING", "RULES", "", "Lorg/catrobat/catroid/ui/fragment/SyntaxHighlighter$Rule;", "highlight", "Landroid/text/SpannableStringBuilder;", "text", "", "Rule", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntaxHighlighter {
    private static final int COLOR_COMMAND;
    private static final int COLOR_ERROR;
    private static final int COLOR_SUCCESS;
    private static final int COLOR_WARNING;
    public static final SyntaxHighlighter INSTANCE = new SyntaxHighlighter();
    private static final List<Rule> RULES;

    private SyntaxHighlighter() {
    }

    static {
        int color = Color.parseColor("#f24e50");
        COLOR_ERROR = color;
        int color2 = Color.parseColor("#E68B00");
        COLOR_WARNING = color2;
        int color3 = Color.parseColor("#00FF00");
        COLOR_SUCCESS = color3;
        int color4 = Color.parseColor("#A8DFF4");
        COLOR_COMMAND = color4;
        Pattern patternCompile = Pattern.compile("\\b(error|traceback|exception|failed|fatal|failure|none)\\b", 2);
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(...)");
        Pattern patternCompile2 = Pattern.compile("\\b(warning|downloading|collecting|looking in indexes)\\b", 2);
        Intrinsics.checkNotNullExpressionValue(patternCompile2, "compile(...)");
        Pattern patternCompile3 = Pattern.compile("\\b(success|successfully|installed|complete)\\b", 2);
        Intrinsics.checkNotNullExpressionValue(patternCompile3, "compile(...)");
        Pattern patternCompile4 = Pattern.compile("^> .*$", 8);
        Intrinsics.checkNotNullExpressionValue(patternCompile4, "compile(...)");
        RULES = CollectionsKt.listOf((Object[]) new Rule[]{new Rule(patternCompile, color, true), new Rule(patternCompile2, color2, false, 4, null), new Rule(patternCompile3, color3, false, 4, null), new Rule(patternCompile4, color4, true)});
    }

    public final SpannableStringBuilder highlight(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        for (Rule rule : RULES) {
            Matcher matcher = rule.getPattern().matcher(spannableStringBuilder);
            while (matcher.find()) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(rule.getColor()), matcher.start(), matcher.end(), 33);
                if (rule.isBold()) {
                    spannableStringBuilder.setSpan(new StyleSpan(1), matcher.start(), matcher.end(), 33);
                }
            }
        }
        return spannableStringBuilder;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/SyntaxHighlighter$Rule.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CommandPromptDialogFragment.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0007HÆ\u0003J'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/SyntaxHighlighter$Rule;", "", "pattern", "Ljava/util/regex/Pattern;", TypedValues.Custom.S_COLOR, "", "isBold", "", "(Ljava/util/regex/Pattern;IZ)V", "getColor", "()I", "()Z", "getPattern", "()Ljava/util/regex/Pattern;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class Rule {
        private final int color;
        private final boolean isBold;
        private final Pattern pattern;

        public static /* synthetic */ Rule copy$default(Rule rule, Pattern pattern, int i, boolean z, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                pattern = rule.pattern;
            }
            if ((i2 & 2) != 0) {
                i = rule.color;
            }
            if ((i2 & 4) != 0) {
                z = rule.isBold;
            }
            return rule.copy(pattern, i, z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Pattern getPattern() {
            return this.pattern;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getColor() {
            return this.color;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getIsBold() {
            return this.isBold;
        }

        public final Rule copy(Pattern pattern, int color, boolean isBold) {
            Intrinsics.checkNotNullParameter(pattern, "pattern");
            return new Rule(pattern, color, isBold);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Rule)) {
                return false;
            }
            Rule rule = (Rule) other;
            return Intrinsics.areEqual(this.pattern, rule.pattern) && this.color == rule.color && this.isBold == rule.isBold;
        }

        public int hashCode() {
            return (((this.pattern.hashCode() * 31) + Integer.hashCode(this.color)) * 31) + Boolean.hashCode(this.isBold);
        }

        public String toString() {
            return "Rule(pattern=" + this.pattern + ", color=" + this.color + ", isBold=" + this.isBold + Constant.AFTER_QUTO;
        }

        public Rule(Pattern pattern, int i, boolean z) {
            Intrinsics.checkNotNullParameter(pattern, "pattern");
            this.pattern = pattern;
            this.color = i;
            this.isBold = z;
        }

        public /* synthetic */ Rule(Pattern pattern, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(pattern, i, (i2 & 4) != 0 ? false : z);
        }

        public final int getColor() {
            return this.color;
        }

        public final Pattern getPattern() {
            return this.pattern;
        }

        public final boolean isBold() {
            return this.isBold;
        }
    }
}
