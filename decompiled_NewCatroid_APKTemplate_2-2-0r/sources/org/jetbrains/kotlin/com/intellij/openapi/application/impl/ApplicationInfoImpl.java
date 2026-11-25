package org.jetbrains.kotlin.com.intellij.openapi.application.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.BrickValues;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.diagnostic.Activity;
import org.jetbrains.kotlin.com.intellij.diagnostic.ActivityCategory;
import org.jetbrains.kotlin.com.intellij.diagnostic.StartUpMeasurer;
import org.jetbrains.kotlin.com.intellij.ide.plugins.PluginManagerCore;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationNamesInfo;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationInfoEx;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ProgressSlide;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.openapi.util.BuildNumber;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;

/* JADX INFO: loaded from: classes3.dex */
public final class ApplicationInfoImpl extends ApplicationInfoEx {
    private static volatile ApplicationInfoImpl instance;
    private final List<PluginId> essentialPluginsIds;
    private long myAboutForeground;
    private String myAboutImageUrl;
    private long myAboutLinkColor;
    private int[] myAboutLogoRect;
    private String myApiVersion;
    private Calendar myBuildDate;
    private String myBuildNumber;
    private String myBuiltinPluginsUrl;
    private String myChannelsListUrl;
    private String myCodeName;
    private String myCompanyName;
    private String myCompanyUrl;
    private long myCopyrightForeground;
    private String myCopyrightStart;
    private String myDefaultDarkLaf;
    private String myDefaultLightLaf;
    private String myDocumentationUrl;
    private boolean myEAP;
    private String myEventLogSettingsUrl;
    private XmlElement myFeedbackForm;
    private String myFeedbackUrl;
    private String myFullVersionFormat;
    private boolean myHasContextHelp;
    private boolean myHasHelp;
    private String myJetBrainsTvUrl;
    private String myKeyConversionUrl;
    private String myMacKeymapUrl;
    private Calendar myMajorReleaseBuildDate;
    private String myMajorVersion;
    private String myMicroVersion;
    private String myMinorVersion;
    private String myPatchVersion;
    private String myPluginManagerUrl;
    private String myPluginsDownloadUrl;
    private String myPluginsListUrl;
    private long myProgressColor;
    private int myProgressHeight;
    private String myProgressTailIconName;
    private int myProgressY;
    private String myShortCompanyName;
    private boolean myShowLicensee;
    private String mySmallIconUrl;
    private String mySmallSvgEapIconUrl;
    private String mySmallSvgIconUrl;
    private String mySplashImageUrl;
    private String mySubscriptionAdditionalFormData;
    private String mySubscriptionFormId;
    private String mySubscriptionNewsKey;
    private String mySubscriptionNewsValue;
    private boolean mySubscriptionTipsAvailable;
    private String mySubscriptionTipsKey;
    private String mySupportUrl;
    private String mySvgEapIconUrl;
    private String mySvgIconUrl;
    private String myToolWindowIconUrl;
    private ApplicationInfoEx.UpdateUrls myUpdateUrls;
    private String myVersionSuffix;
    private String myWebHelpUrl;
    private String myWelcomeScreenDialog;
    private String myWelcomeScreenLogoUrl;
    private int myWhatsNewEligibility;
    private String myWhatsNewUrl;
    private String myWinKeymapUrl;
    private String myYoutrackUrl;
    private List<ProgressSlide> progressSlides;

    /* JADX WARN: Code duplicated, block: B:11:0x0014  */
    /* JADX WARN: Code duplicated, block: B:6:0x000a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 16) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 16) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/impl/ApplicationInfoImpl";
                break;
            case 14:
            case 15:
                objArr[0] = "pluginId";
                break;
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
        }
        if (i != 16) {
            switch (i) {
                case 1:
                case 2:
                    objArr[1] = "getShadowInstanceImpl";
                    break;
                case 3:
                    objArr[1] = "orFromPluginsCompatibleBuild";
                    break;
                case 4:
                    objArr[1] = "getBuild";
                    break;
                case 5:
                    objArr[1] = "getApiVersion";
                    break;
                case 6:
                case 7:
                    objArr[1] = "getApiVersionAsNumber";
                    break;
                case 8:
                    objArr[1] = "getFullVersion";
                    break;
                case 9:
                    objArr[1] = "getStrictVersion";
                    break;
                case 10:
                    objArr[1] = "getSmallIconUrl";
                    break;
                case 11:
                    objArr[1] = "getProgressSlides";
                    break;
                case 12:
                    objArr[1] = "getPluginsCompatibleBuild";
                    break;
                case 13:
                    objArr[1] = "getPluginsCompatibleBuildAsNumber";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/impl/ApplicationInfoImpl";
                    break;
            }
        } else {
            objArr[1] = "getEssentialPluginsIds";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
                break;
            case 14:
            case 15:
                objArr[2] = "isEssentialPlugin";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 16) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    private ApplicationInfoImpl() {
        this(ApplicationNamesInfo.initAndGetRawData());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [org.jetbrains.kotlin.com.intellij.openapi.application.impl.ApplicationInfoImpl$1] */
    /* JADX WARN: Type inference failed for: r4v71 */
    ApplicationInfoImpl(XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(0);
        }
        this.myCompanyName = "JetBrains s.r.o.";
        this.myCopyrightStart = "2000";
        this.myCompanyUrl = "https://www.jetbrains.com/";
        this.myProgressColor = -1L;
        this.myCopyrightForeground = -1L;
        this.myAboutForeground = -1L;
        this.myAboutLinkColor = -1L;
        this.myProgressHeight = 2;
        this.myProgressY = BrickValues.DRONE_ROTATION_MAX;
        this.mySmallIconUrl = "/icon_small.png";
        this.myToolWindowIconUrl = "/toolwindows/toolWindowProject.svg";
        this.myShowLicensee = true;
        this.myHasHelp = true;
        this.myHasContextHelp = true;
        this.myWebHelpUrl = "https://www.jetbrains.com/idea/webhelp/";
        this.essentialPluginsIds = new ArrayList();
        this.myEventLogSettingsUrl = "https://resources.jetbrains.com/storage/fus/config/v4/%s/%s.json";
        this.myKeyConversionUrl = "https://www.jetbrains.com/shop/eform/keys-exchange";
        this.progressSlides = Collections.emptyList();
        Iterator<XmlElement> it = xmlElement.children.iterator();
        while (true) {
            String str = 0;
            if (it.hasNext()) {
                XmlElement next = it.next();
                String str2 = next.name;
                str2.hashCode();
                switch (str2) {
                    case "support":
                        this.mySupportUrl = next.getAttributeValue("url");
                        break;
                    case "keymap":
                        this.myWinKeymapUrl = next.getAttributeValue("win");
                        this.myMacKeymapUrl = next.getAttributeValue("mac");
                        break;
                    case "icon-eap":
                        this.mySvgEapIconUrl = next.getAttributeValue("svg");
                        this.mySmallSvgEapIconUrl = next.getAttributeValue("svg-small");
                        break;
                    case "youtrack":
                        this.myYoutrackUrl = next.getAttributeValue("url");
                        break;
                    case "update-urls":
                        this.myUpdateUrls = new UpdateUrlsImpl(next);
                        break;
                    case "plugins":
                        readPluginInfo(next);
                        break;
                    case "default-laf":
                        String attributeValue = getAttributeValue(next, "light");
                        if (attributeValue != null) {
                            this.myDefaultLightLaf = attributeValue.trim();
                        }
                        String attributeValue2 = getAttributeValue(next, "dark");
                        if (attributeValue2 != null) {
                            this.myDefaultDarkLaf = attributeValue2.trim();
                            break;
                        } else {
                            break;
                        }
                        break;
                    case "feedback":
                        this.myFeedbackUrl = next.getAttributeValue("url");
                        if (next.getAttributeValue("zendesk-form-id") != null) {
                            this.myFeedbackForm = next;
                            break;
                        } else {
                            break;
                        }
                        break;
                    case "essential-plugin":
                        String str3 = next.content;
                        if (str3 != null && !str3.isEmpty()) {
                            this.essentialPluginsIds.add(PluginId.getId(str3));
                            break;
                        } else {
                            break;
                        }
                        break;
                    case "statistics":
                        this.myEventLogSettingsUrl = next.getAttributeValue("event-log-settings");
                        break;
                    case "help":
                        String attributeValue3 = getAttributeValue(next, "webhelp-url");
                        if (attributeValue3 != null) {
                            this.myWebHelpUrl = attributeValue3;
                        }
                        String attributeValue4 = next.getAttributeValue("has-help");
                        this.myHasHelp = attributeValue4 == null || Boolean.parseBoolean(attributeValue4);
                        String attributeValue5 = next.getAttributeValue("has-context-help");
                        this.myHasContextHelp = attributeValue5 == null || Boolean.parseBoolean(attributeValue5);
                        break;
                    case "icon":
                        this.mySmallIconUrl = next.getAttributeValue("size16", this.mySmallIconUrl);
                        String attributeValue6 = getAttributeValue(next, "size12");
                        if (attributeValue6 != null) {
                            this.myToolWindowIconUrl = attributeValue6;
                        }
                        this.mySvgIconUrl = next.getAttributeValue("svg");
                        this.mySmallSvgIconUrl = next.getAttributeValue("svg-small");
                        break;
                    case "logo":
                        readLogoInfo(next);
                        break;
                    case "about":
                        this.myAboutImageUrl = next.getAttributeValue("url");
                        String attributeValue7 = next.getAttributeValue("foreground");
                        if (attributeValue7 != null) {
                            this.myAboutForeground = parseColor(attributeValue7);
                        }
                        String attributeValue8 = next.getAttributeValue("copyrightForeground");
                        if (attributeValue8 != null) {
                            this.myCopyrightForeground = parseColor(attributeValue8);
                        }
                        String attributeValue9 = next.getAttributeValue("linkColor");
                        if (attributeValue9 != null) {
                            this.myAboutLinkColor = parseColor(attributeValue9);
                        }
                        String attributeValue10 = next.getAttributeValue("logoX");
                        String attributeValue11 = next.getAttributeValue("logoY");
                        String attributeValue12 = next.getAttributeValue("logoW");
                        String attributeValue13 = next.getAttributeValue("logoH");
                        if (attributeValue10 != null && attributeValue11 != null && attributeValue12 != null && attributeValue13 != null) {
                            try {
                                this.myAboutLogoRect = new int[]{Integer.parseInt(attributeValue10), Integer.parseInt(attributeValue11), Integer.parseInt(attributeValue12), Integer.parseInt(attributeValue13)};
                            } catch (NumberFormatException unused) {
                            }
                            break;
                        } else {
                            break;
                        }
                        break;
                    case "build":
                        readBuildInfo(next);
                        break;
                    case "welcome-screen":
                        this.myWelcomeScreenLogoUrl = next.getAttributeValue("logo-url");
                        break;
                    case "welcome-wizard":
                        this.myWelcomeScreenDialog = getAttributeValue(next, "dialog");
                        break;
                    case "version":
                        this.myMajorVersion = next.getAttributeValue("major");
                        this.myMinorVersion = next.getAttributeValue("minor");
                        this.myMicroVersion = next.getAttributeValue("micro");
                        this.myPatchVersion = next.getAttributeValue("patch");
                        this.myFullVersionFormat = next.getAttributeValue("full");
                        this.myCodeName = next.getAttributeValue("codename");
                        boolean z = Boolean.parseBoolean(next.getAttributeValue("eap"));
                        this.myEAP = z;
                        this.myVersionSuffix = next.getAttributeValue("suffix", z ? "EAP" : 0);
                        break;
                    case "licensee":
                        this.myShowLicensee = Boolean.parseBoolean(next.getAttributeValue("show"));
                        break;
                    case "company":
                        String attributeValue14 = next.getAttributeValue("name", this.myCompanyName);
                        this.myCompanyName = attributeValue14;
                        this.myShortCompanyName = next.getAttributeValue("shortName", attributeValue14 != null ? shortenCompanyName(attributeValue14) : null);
                        this.myCompanyUrl = next.getAttributeValue("url", this.myCompanyUrl);
                        this.myCopyrightStart = next.getAttributeValue("copyrightStart", this.myCopyrightStart);
                        break;
                    case "licensing":
                        String attributeValue15 = getAttributeValue(next, "key-conversion-url");
                        if (attributeValue15 != null) {
                            this.myKeyConversionUrl = attributeValue15.trim();
                            break;
                        } else {
                            break;
                        }
                        break;
                    case "documentation":
                        this.myDocumentationUrl = next.getAttributeValue("url");
                        break;
                    case "jetbrains-tv":
                        this.myJetBrainsTvUrl = next.getAttributeValue("url");
                        break;
                    case "whatsnew":
                        this.myWhatsNewUrl = next.getAttributeValue("url");
                        String attributeValue16 = next.getAttributeValue("eligibility");
                        if ("embed".equals(attributeValue16)) {
                            this.myWhatsNewEligibility = 1;
                            break;
                        } else {
                            if ("auto".equals(attributeValue16)) {
                                this.myWhatsNewEligibility = 2;
                            }
                            break;
                        }
                        break;
                    case "subscriptions":
                        this.mySubscriptionFormId = next.getAttributeValue("formid");
                        this.mySubscriptionNewsKey = next.getAttributeValue("news-key");
                        this.mySubscriptionNewsValue = next.getAttributeValue("news-value", "yes");
                        this.mySubscriptionTipsKey = next.getAttributeValue("tips-key");
                        this.mySubscriptionTipsAvailable = Boolean.parseBoolean(next.getAttributeValue("tips-available"));
                        this.mySubscriptionAdditionalFormData = next.getAttributeValue("additional-form-data");
                        break;
                }
            } else {
                this.essentialPluginsIds.sort(null);
                return;
            }
        }
    }

    private void readLogoInfo(XmlElement xmlElement) {
        this.mySplashImageUrl = getAttributeValue(xmlElement, "url");
        String attributeValue = xmlElement.getAttributeValue("progressColor");
        if (attributeValue != null && !attributeValue.isEmpty()) {
            this.myProgressColor = parseColor(attributeValue);
        }
        String attributeValue2 = xmlElement.getAttributeValue("progressTailIcon");
        if (attributeValue2 != null && !attributeValue2.isEmpty()) {
            this.myProgressTailIconName = attributeValue2;
        }
        String attributeValue3 = xmlElement.getAttributeValue("progressHeight");
        if (attributeValue3 != null && !attributeValue3.isEmpty()) {
            this.myProgressHeight = Integer.parseInt(attributeValue3);
        }
        String attributeValue4 = xmlElement.getAttributeValue("progressY");
        if (attributeValue4 != null && !attributeValue4.isEmpty()) {
            this.myProgressY = Integer.parseInt(attributeValue4);
        }
        if (xmlElement.children.isEmpty()) {
            return;
        }
        this.progressSlides = new ArrayList(xmlElement.children.size());
        for (XmlElement xmlElement2 : xmlElement.children) {
            if (xmlElement2.name.equals("progressSlide")) {
                String str = (String) Objects.requireNonNull(xmlElement2.getAttributeValue("url"));
                String str2 = (String) Objects.requireNonNull(xmlElement2.getAttributeValue("progressPercent"));
                int i = Integer.parseInt(str2);
                if (i < 0 || i > 100) {
                    throw new IllegalArgumentException("Expected [0, 100], got " + str2);
                }
                this.progressSlides.add(new ProgressSlide(str, i / 100.0f));
            }
        }
    }

    public static ApplicationInfoEx getShadowInstance() {
        return getShadowInstanceImpl();
    }

    public static ApplicationInfoImpl getShadowInstanceImpl() {
        ApplicationInfoImpl applicationInfoImpl;
        ApplicationInfoImpl applicationInfoImpl2 = instance;
        if (applicationInfoImpl2 != null) {
            if (applicationInfoImpl2 == null) {
                $$$reportNull$$$0(1);
            }
            return applicationInfoImpl2;
        }
        synchronized (ApplicationInfoImpl.class) {
            applicationInfoImpl = instance;
            if (applicationInfoImpl == null) {
                Activity activityStartActivity = StartUpMeasurer.startActivity("app info loading", ActivityCategory.DEFAULT);
                try {
                    ApplicationInfoImpl applicationInfoImpl3 = new ApplicationInfoImpl(ApplicationNamesInfo.initAndGetRawData());
                    instance = applicationInfoImpl3;
                    activityStartActivity.end();
                    applicationInfoImpl = applicationInfoImpl3;
                } catch (Throwable th) {
                    activityStartActivity.end();
                    throw th;
                }
            }
        }
        if (applicationInfoImpl == null) {
            $$$reportNull$$$0(2);
        }
        return applicationInfoImpl;
    }

    public BuildNumber getBuild() {
        BuildNumber buildNumber = (BuildNumber) Objects.requireNonNull(BuildNumber.fromString(this.myBuildNumber));
        if (buildNumber == null) {
            $$$reportNull$$$0(4);
        }
        return buildNumber;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationInfoEx
    public BuildNumber getApiVersionAsNumber() {
        BuildNumber buildNumberFromStringWithProductCode;
        BuildNumber build = getBuild();
        String str = this.myApiVersion;
        if (str == null || (buildNumberFromStringWithProductCode = BuildNumber.fromStringWithProductCode(str, build.getProductCode())) == null) {
            if (build == null) {
                $$$reportNull$$$0(7);
            }
            return build;
        }
        if (buildNumberFromStringWithProductCode == null) {
            $$$reportNull$$$0(6);
        }
        return buildNumberFromStringWithProductCode;
    }

    private static String getAttributeValue(XmlElement xmlElement, String str) {
        String attributeValue = xmlElement.getAttributeValue(str);
        if (attributeValue == null || attributeValue.isEmpty()) {
            return null;
        }
        return attributeValue;
    }

    private void readBuildInfo(XmlElement xmlElement) {
        this.myBuildNumber = getAttributeValue(xmlElement, "number");
        this.myApiVersion = getAttributeValue(xmlElement, "apiVersion");
        String attributeValue = xmlElement.getAttributeValue(SchemaSymbols.ATTVAL_DATE);
        if (attributeValue != null && !attributeValue.equals("__BUILD_DATE__")) {
            this.myBuildDate = parseDate(attributeValue);
        }
        String attributeValue2 = xmlElement.getAttributeValue("majorReleaseDate");
        if (attributeValue2 != null) {
            this.myMajorReleaseBuildDate = parseDate(attributeValue2);
        }
    }

    private void readPluginInfo(XmlElement xmlElement) {
        String attributeValue;
        String str = null;
        this.myChannelsListUrl = null;
        this.myPluginsDownloadUrl = null;
        String strSubstring = "https://plugins.jetbrains.com";
        if (xmlElement != null) {
            String attributeValue2 = xmlElement.getAttributeValue("url");
            if (attributeValue2 != null) {
                strSubstring = attributeValue2.endsWith("/") ? attributeValue2.substring(0, attributeValue2.length() - 1) : attributeValue2;
            }
            attributeValue = xmlElement.getAttributeValue("list-url");
            if (attributeValue == null) {
                attributeValue = null;
            }
            String attributeValue3 = xmlElement.getAttributeValue("channel-list-url");
            if (attributeValue3 != null) {
                this.myChannelsListUrl = attributeValue3;
            }
            String attributeValue4 = xmlElement.getAttributeValue("download-url");
            if (attributeValue4 != null) {
                this.myPluginsDownloadUrl = attributeValue4;
            }
            String attributeValue5 = xmlElement.getAttributeValue("builtin-url");
            if (attributeValue5 != null && !attributeValue5.isEmpty()) {
                this.myBuiltinPluginsUrl = attributeValue5;
            }
        } else {
            attributeValue = null;
        }
        String property = System.getProperty("idea.plugins.host");
        if (property != null) {
            if (property.endsWith("/")) {
                property = property.substring(0, property.length() - 1);
            }
            strSubstring = property;
            this.myPluginsDownloadUrl = null;
            this.myChannelsListUrl = null;
        } else {
            str = attributeValue;
        }
        this.myPluginManagerUrl = strSubstring;
        if (str == null) {
            str = strSubstring + "/plugins/list/";
        }
        this.myPluginsListUrl = str;
        if (this.myChannelsListUrl == null) {
            this.myChannelsListUrl = strSubstring + "/channels/list/";
        }
        if (this.myPluginsDownloadUrl == null) {
            this.myPluginsDownloadUrl = strSubstring + "/pluginManager/";
        }
    }

    private static String shortenCompanyName(String str) {
        if (str.endsWith(" s.r.o.")) {
            str = str.substring(0, str.length() - " s.r.o.".length());
        }
        return str.endsWith(" Inc.") ? str.substring(0, str.length() - " Inc.".length()) : str;
    }

    private static GregorianCalendar parseDate(String str) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        try {
            gregorianCalendar.set(1, Integer.parseInt(str.substring(0, 4)));
            gregorianCalendar.set(2, Integer.parseInt(str.substring(4, 6)) - 1);
            gregorianCalendar.set(5, Integer.parseInt(str.substring(6, 8)));
            if (str.length() > 8) {
                gregorianCalendar.set(11, Integer.parseInt(str.substring(8, 10)));
                gregorianCalendar.set(12, Integer.parseInt(str.substring(10, 12)));
            } else {
                gregorianCalendar.set(11, 0);
                gregorianCalendar.set(12, 0);
            }
        } catch (Exception unused) {
        }
        return gregorianCalendar;
    }

    private static long parseColor(String str) {
        return Long.parseLong(str, 16);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationInfoEx
    public boolean isEssentialPlugin(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        return "org.jetbrains.kotlin.com.intellij".equals(str) || isEssentialPlugin(PluginId.getId(str));
    }

    public boolean isEssentialPlugin(PluginId pluginId) {
        if (pluginId == null) {
            $$$reportNull$$$0(15);
        }
        return PluginManagerCore.CORE_ID.equals(pluginId) || Collections.binarySearch(this.essentialPluginsIds, pluginId) >= 0;
    }

    private static final class UpdateUrlsImpl implements ApplicationInfoEx.UpdateUrls {
        private final String myCheckingUrl;
        private final String myPatchesUrl;

        private UpdateUrlsImpl(XmlElement xmlElement) {
            this.myCheckingUrl = xmlElement.getAttributeValue("check");
            this.myPatchesUrl = xmlElement.getAttributeValue("patches");
        }
    }
}
