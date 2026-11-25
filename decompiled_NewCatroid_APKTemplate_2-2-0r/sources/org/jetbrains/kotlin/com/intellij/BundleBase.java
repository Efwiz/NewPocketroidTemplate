package org.jetbrains.kotlin.com.intellij;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import kotlin.text.Typography;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.text.OrdinalFormat;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BundleBase {
    private static boolean assertOnMissedKeys;
    private static volatile BiConsumer<String, String> ourTranslationConsumer;
    public static final String MNEMONIC_STRING = Character.toString(27);
    public static final boolean SHOW_LOCALIZED_MESSAGES = Boolean.getBoolean("idea.l10n");
    public static final boolean SHOW_DEFAULT_MESSAGES = Boolean.getBoolean("idea.l10n.english");
    public static final boolean SHOW_KEYS = Boolean.getBoolean("idea.l10n.keys");
    private static final Logger LOG = Logger.getInstance((Class<?>) BundleBase.class);
    private static final String[] SUFFIXES = {"</body></html>", "</html>"};

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 11 || i == 22 || i == 25 || i == 14 || i == 15 || i == 17 || i == 18) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 11 || i == 22 || i == 25 || i == 14 || i == 15 || i == 17 || i == 18) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 10:
            case 16:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 2:
            case 5:
            case 8:
            case 21:
            case 24:
                objArr[0] = "params";
                break;
            case 3:
            case 9:
            case 19:
            default:
                objArr[0] = "bundle";
                break;
            case 6:
            case 11:
            case 14:
            case 15:
            case 17:
            case 18:
            case 22:
            case 25:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/BundleBase";
                break;
            case 12:
                objArr[0] = "result";
                break;
            case 13:
                objArr[0] = "suffixToAppend";
                break;
            case 20:
            case 23:
                objArr[0] = "value";
                break;
        }
        if (i == 6) {
            objArr[1] = CrashHianalyticsData.MESSAGE;
        } else if (i == 11) {
            objArr[1] = "getDefaultMessage";
        } else if (i == 22) {
            objArr[1] = "postprocessValue";
        } else if (i == 25) {
            objArr[1] = ConfigConstants.CONFIG_KEY_FORMAT;
        } else if (i == 14 || i == 15) {
            objArr[1] = "appendLocalizationSuffix";
        } else if (i == 17 || i == 18) {
            objArr[1] = "useDefaultValue";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/BundleBase";
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
                objArr[2] = CrashHianalyticsData.MESSAGE;
                break;
            case 6:
            case 11:
            case 14:
            case 15:
            case 17:
            case 18:
            case 22:
            case 25:
                break;
            case 7:
            case 8:
                objArr[2] = "messageOrDefault";
                break;
            case 9:
            case 10:
                objArr[2] = "getDefaultMessage";
                break;
            case 12:
            case 13:
                objArr[2] = "appendLocalizationSuffix";
                break;
            case 16:
                objArr[2] = "useDefaultValue";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "postprocessValue";
                break;
            case 23:
            case 24:
                objArr[2] = ConfigConstants.CONFIG_KEY_FORMAT;
                break;
            default:
                objArr[2] = "partialMessage";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 11 && i != 22 && i != 25 && i != 14 && i != 15 && i != 17 && i != 18) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static String messageOrDefault(ResourceBundle resourceBundle, String str, String str2, Object... objArr) {
        String strUseDefaultValue;
        boolean z;
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        if (objArr == null) {
            $$$reportNull$$$0(8);
        }
        if (resourceBundle == null) {
            return str2;
        }
        try {
            strUseDefaultValue = resourceBundle.getString(str);
            z = true;
        } catch (MissingResourceException unused) {
            strUseDefaultValue = useDefaultValue(resourceBundle, str, str2);
            z = false;
        }
        String strPostprocessValue = postprocessValue(resourceBundle, strUseDefaultValue, objArr);
        BiConsumer<String, String> biConsumer = ourTranslationConsumer;
        if (biConsumer != null) {
            biConsumer.accept(str, strPostprocessValue);
        }
        if (!z) {
            return strPostprocessValue;
        }
        boolean z2 = SHOW_KEYS;
        if (z2 && SHOW_DEFAULT_MESSAGES) {
            return appendLocalizationSuffix(strPostprocessValue, " (" + str + ContainerUtils.KEY_VALUE_DELIMITER + getDefaultMessage(resourceBundle, str) + Constant.AFTER_QUTO);
        }
        if (z2) {
            return appendLocalizationSuffix(strPostprocessValue, " (" + str + Constant.AFTER_QUTO);
        }
        if (SHOW_DEFAULT_MESSAGES) {
            return appendLocalizationSuffix(strPostprocessValue, " (" + getDefaultMessage(resourceBundle, str) + Constant.AFTER_QUTO);
        }
        return SHOW_LOCALIZED_MESSAGES ? appendLocalizationSuffix(strPostprocessValue, "🔅") : strPostprocessValue;
    }

    public static String getDefaultMessage(ResourceBundle resourceBundle, String str) {
        if (resourceBundle == null) {
            $$$reportNull$$$0(9);
        }
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        try {
            Field declaredField = ReflectionUtil.getDeclaredField(ResourceBundle.class, "parent");
            if (declaredField != null) {
                Object obj = declaredField.get(resourceBundle);
                if (obj instanceof ResourceBundle) {
                    String string = ((ResourceBundle) obj).getString(str);
                    if (string == null) {
                        $$$reportNull$$$0(11);
                    }
                    return string;
                }
                return "undefined";
            }
            return "undefined";
        } catch (IllegalAccessException unused) {
            LOG.warn("Cannot fetch default message with 'idea.l10n.english' enabled, by key '" + str + FormatHelper.QUOTE);
            return "undefined";
        }
    }

    protected static String appendLocalizationSuffix(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (str2 == null) {
            $$$reportNull$$$0(13);
        }
        for (String str3 : SUFFIXES) {
            if (str.endsWith(str3)) {
                String str4 = str.substring(0, str.length() - str3.length()) + "🔅" + str3;
                if (str4 == null) {
                    $$$reportNull$$$0(14);
                }
                return str4;
            }
        }
        String str5 = str + str2;
        if (str5 == null) {
            $$$reportNull$$$0(15);
        }
        return str5;
    }

    static String useDefaultValue(ResourceBundle resourceBundle, String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        if (str2 != null) {
            if (str2 == null) {
                $$$reportNull$$$0(17);
            }
            return str2;
        }
        if (assertOnMissedKeys) {
            LOG.error(FormatHelper.QUOTE + str + "' is not found in " + resourceBundle + (resourceBundle != null ? "(" + resourceBundle.getBaseBundleName() + Constant.AFTER_QUTO : ""));
        }
        String str3 = "!" + str + "!";
        if (str3 == null) {
            $$$reportNull$$$0(18);
        }
        return str3;
    }

    static String postprocessValue(ResourceBundle resourceBundle, String str, Object... objArr) {
        if (resourceBundle == null) {
            $$$reportNull$$$0(19);
        }
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        if (objArr == null) {
            $$$reportNull$$$0(21);
        }
        String strReplaceMnemonicAmpersand = replaceMnemonicAmpersand(str);
        if (objArr.length > 0 && strReplaceMnemonicAmpersand.indexOf(123) >= 0) {
            Locale locale = resourceBundle.getLocale();
            try {
                MessageFormat messageFormat = locale != null ? new MessageFormat(strReplaceMnemonicAmpersand, locale) : new MessageFormat(strReplaceMnemonicAmpersand);
                OrdinalFormat.apply(messageFormat);
                strReplaceMnemonicAmpersand = messageFormat.format(objArr);
            } catch (IllegalArgumentException unused) {
                strReplaceMnemonicAmpersand = "!invalid format: `" + strReplaceMnemonicAmpersand + "`!";
            }
        }
        if (strReplaceMnemonicAmpersand == null) {
            $$$reportNull$$$0(22);
        }
        return strReplaceMnemonicAmpersand;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0042  */
    /* JADX WARN: Code duplicated, block: B:28:0x0061  */
    /* JADX WARN: Code duplicated, block: B:31:0x0067  */
    public static String replaceMnemonicAmpersand(String str) {
        if (str == null || str.indexOf(38) < 0 || str.indexOf(27) >= 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        boolean zContains = str.contains("&&");
        int i = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\\') {
                if (i < str.length() - 1) {
                    int i2 = i + 1;
                    if (str.charAt(i2) == '&') {
                        sb.append(Typography.amp);
                        i = i2;
                    } else {
                        sb.append(cCharAt);
                    }
                } else {
                    sb.append(cCharAt);
                }
            } else if (cCharAt == '&') {
                if (i < str.length() - 1) {
                    int i3 = i + 1;
                    if (str.charAt(i3) == '&') {
                        if (SystemInfoRt.isMac) {
                            sb.append((char) 27);
                        }
                        i = i3;
                    } else if (SystemInfoRt.isMac || !zContains) {
                        sb.append((char) 27);
                    }
                } else if (SystemInfoRt.isMac) {
                    sb.append((char) 27);
                } else {
                    sb.append((char) 27);
                }
            } else {
                sb.append(cCharAt);
            }
            i++;
        }
        return sb.toString();
    }
}
