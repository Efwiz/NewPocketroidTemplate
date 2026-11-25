package org.jetbrains.kotlin.cli.common.arguments;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.config.LanguageFeature;

/* JADX INFO: compiled from: InternalCompilerArgument.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\"\u0010\u0004\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/LanguageSettingsParser;", "Lorg/jetbrains/kotlin/cli/common/arguments/AbstractInternalArgumentParser;", "Lorg/jetbrains/kotlin/cli/common/arguments/ManualLanguageFeatureSetting;", "()V", "parseTail", "tail", "", "wholeArgument", "errors", "Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LanguageSettingsParser extends AbstractInternalArgumentParser<ManualLanguageFeatureSetting> {
    public LanguageSettingsParser() {
        super("Language");
    }

    private static final Void parseTail$reportAndReturnNull(ArgumentParseErrors argumentParseErrors, String str) {
        argumentParseErrors.getInternalArgumentsParsingProblems().add(str);
        return null;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.AbstractInternalArgumentParser
    public ManualLanguageFeatureSetting parseTail(String tail, String wholeArgument, ArgumentParseErrors errors) {
        LanguageFeature.State state;
        Intrinsics.checkNotNullParameter(tail, "tail");
        Intrinsics.checkNotNullParameter(wholeArgument, "wholeArgument");
        Intrinsics.checkNotNullParameter(errors, "errors");
        String str = tail;
        Character orNull = StringsKt.getOrNull(str, 0);
        if (orNull == null || orNull.charValue() != ':') {
            return (ManualLanguageFeatureSetting) parseTail$reportAndReturnNull(errors, "Incorrect internal argument syntax, missing colon: " + wholeArgument);
        }
        Character orNull2 = StringsKt.getOrNull(str, 1);
        if (orNull2 != null && orNull2.charValue() == '+') {
            state = LanguageFeature.State.ENABLED;
        } else {
            if (orNull2 == null || orNull2.charValue() != '-') {
                return (ManualLanguageFeatureSetting) parseTail$reportAndReturnNull(errors, "Incorrect internal argument syntax, missing modificator: " + wholeArgument);
            }
            state = LanguageFeature.State.DISABLED;
        }
        String strSubstring = tail.substring(2);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        if (strSubstring.length() == 0) {
            return (ManualLanguageFeatureSetting) parseTail$reportAndReturnNull(errors, "Empty language feature name for internal argument '" + wholeArgument + '\'');
        }
        LanguageFeature languageFeatureFromString = LanguageFeature.INSTANCE.fromString(strSubstring);
        if (languageFeatureFromString == null) {
            return (ManualLanguageFeatureSetting) parseTail$reportAndReturnNull(errors, "Unknown language feature '" + strSubstring + "' in passed internal argument '" + wholeArgument + '\'');
        }
        return new ManualLanguageFeatureSetting(languageFeatureFromString, state, wholeArgument);
    }
}
