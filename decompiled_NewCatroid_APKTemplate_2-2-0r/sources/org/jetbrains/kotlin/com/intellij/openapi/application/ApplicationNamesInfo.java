package org.jetbrains.kotlin.com.intellij.openapi.application;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.stream.XMLStreamException;
import org.jetbrains.kotlin.com.intellij.util.XmlDomReader;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;

/* JADX INFO: loaded from: classes3.dex */
public final class ApplicationNamesInfo {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static volatile ApplicationNamesInfo instance;
    private final String myDefaultLauncherName;
    private final String myEditionName;
    private final String myFullProductName;
    private final String myMotto;
    private final String myProductName;
    private final String myScriptName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 6 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 6 ? 2 : 3];
        if (i != 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/ApplicationNamesInfo";
        } else {
            objArr[0] = "rootElement";
        }
        if (i == 4) {
            objArr[1] = "initAndGetRawData";
        } else if (i == 5) {
            objArr[1] = "getInstance";
        } else if (i == 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/ApplicationNamesInfo";
        } else if (i != 7) {
            objArr[1] = "loadData";
        } else {
            objArr[1] = "getMotto";
        }
        if (i == 6) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 6) {
            throw new IllegalArgumentException(str2);
        }
    }

    private static XmlElement loadData() throws XMLStreamException {
        String property;
        String str;
        String property2 = System.getProperty("idea.platform.prefix", "");
        if (Boolean.getBoolean("idea.use.dev.build.server")) {
            if (property2.isEmpty() || property2.equals("idea")) {
                str = "intellij.idea.ultimate.resources";
            } else {
                str = property2.equals("WebStorm") ? "intellij.webstorm" : null;
            }
            if (str != null) {
                Path path = Paths.get(PathManager.getHomePath() + "/out/classes/production/" + str + "/idea/" + (property2.equals("idea") ? "" : property2) + "ApplicationInfo.xml", new String[0]);
                try {
                    XmlElement xmlAsModel = XmlDomReader.readXmlAsModel(Files.newInputStream(path, new OpenOption[0]));
                    if (xmlAsModel == null) {
                        $$$reportNull$$$0(0);
                    }
                    return xmlAsModel;
                } catch (NoSuchFileException unused) {
                } catch (Exception e) {
                    throw new RuntimeException("Cannot load " + path, e);
                }
            }
        } else {
            if (property2.equals("Gateway") && (property = System.getProperty("idea.application.info.value")) != null) {
                try {
                    XmlElement xmlAsModel2 = XmlDomReader.readXmlAsModel(Files.newInputStream(Paths.get(property, new String[0]), new OpenOption[0]));
                    if (xmlAsModel2 == null) {
                        $$$reportNull$$$0(1);
                    }
                    return xmlAsModel2;
                } catch (Exception e2) {
                    throw new RuntimeException("Cannot load custom application info file " + property, e2);
                }
            }
            String appInfoData = getAppInfoData();
            if (!appInfoData.isEmpty()) {
                XmlElement xmlAsModel3 = XmlDomReader.readXmlAsModel(appInfoData.getBytes(StandardCharsets.UTF_8));
                if (xmlAsModel3 == null) {
                    $$$reportNull$$$0(2);
                }
                return xmlAsModel3;
            }
        }
        String str2 = "idea/" + (property2.equals("idea") ? "" : property2) + "ApplicationInfo.xml";
        InputStream resourceAsStream = ApplicationNamesInfo.class.getClassLoader().getResourceAsStream(str2);
        if (resourceAsStream == null) {
            throw new RuntimeException("Resource not found: " + str2);
        }
        try {
            XmlElement xmlAsModel4 = XmlDomReader.readXmlAsModel(resourceAsStream);
            if (xmlAsModel4 == null) {
                $$$reportNull$$$0(3);
            }
            return xmlAsModel4;
        } catch (Exception e3) {
            throw new RuntimeException("Cannot load resource: " + str2, e3);
        }
    }

    private static String getAppInfoData() {
        return "";
    }

    public static XmlElement initAndGetRawData() {
        XmlElement xmlElementLoadData;
        synchronized (ApplicationNamesInfo.class) {
            xmlElementLoadData = loadData();
            if (instance == null) {
                instance = new ApplicationNamesInfo(xmlElementLoadData);
            }
        }
        if (xmlElementLoadData == null) {
            $$$reportNull$$$0(4);
        }
        return xmlElementLoadData;
    }

    private ApplicationNamesInfo(XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(6);
        }
        XmlElement child = xmlElement.getChild("names");
        String attributeValue = child.getAttributeValue("product");
        this.myProductName = attributeValue;
        this.myFullProductName = child.getAttributeValue("fullname", attributeValue);
        this.myEditionName = child.getAttributeValue("edition");
        String attributeValue2 = child.getAttributeValue("script");
        this.myScriptName = attributeValue2;
        this.myDefaultLauncherName = child.getAttributeValue("default-launcher-name", attributeValue2);
        this.myMotto = child.getAttributeValue("motto", "The Drive to Develop");
    }
}
