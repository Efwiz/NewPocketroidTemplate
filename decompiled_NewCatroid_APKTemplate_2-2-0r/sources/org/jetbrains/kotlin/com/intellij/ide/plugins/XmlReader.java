package org.jetbrains.kotlin.com.intellij.ide.plugins;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import javax.xml.stream.XMLStreamException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.xinclude.XIncludeHandler;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentConfig;
import org.jetbrains.kotlin.com.intellij.openapi.components.ServiceDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.LoadingOrder;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.openapi.util.StaxFactory;
import org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.util.XmlDomReader;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;
import org.jetbrains.kotlin.com.intellij.util.XmlInterner;
import org.jetbrains.kotlin.com.intellij.util.messages.ListenerDescriptor;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.org.codehaus.stax2.LocationInfo;
import org.jetbrains.kotlin.org.codehaus.stax2.XMLStreamReader2;
import org.jetbrains.kotlin.org.codehaus.stax2.typed.TypedXMLStreamException;

/* JADX INFO: compiled from: XmlReader.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000ª\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a/\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0014\b\u0004\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00140\u0013H\u0082\b\u001a\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0002\u001a\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0002H\u0002\u001a\u0010\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001eH\u0002\u001a\u001a\u0010\u001f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u001eH\u0002\u001a\u0012\u0010!\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0002\u001a\u0012\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010$\u001a\u00020\u0002H\u0002\u001a \u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010'\u001a\u00020(H\u0002\u001a\u0018\u0010)\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010*\u001a\u00020\u0014H\u0002\u001a \u0010+\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u00102\u0006\u0010'\u001a\u00020(H\u0002\u001a \u0010,\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u00102\u0006\u0010'\u001a\u00020(H\u0002\u001a:\u0010-\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u00102\u0006\u0010'\u001a\u00020(2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u0002H\u0002\u001a \u00103\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u00102\u0006\u00104\u001a\u000205H\u0002\u001aB\u00106\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00107\u001a\u00020\u00102\u0006\u0010'\u001a\u00020(2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u00022\u0006\u00108\u001a\u00020\u0002H\u0002\u001a\u0018\u00109\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010*\u001a\u00020\u0014H\u0002\u001aD\u0010:\u001a\u00020\u00102\u0006\u0010;\u001a\u00020<2\u0006\u0010'\u001a\u00020(2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u00022\b\u00107\u001a\u0004\u0018\u00010\u00102\b\u0010=\u001a\u0004\u0018\u00010\u0002\u001aD\u0010:\u001a\u00020\u00102\u0006\u0010;\u001a\u00020>2\u0006\u0010'\u001a\u00020(2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u00022\b\u00107\u001a\u0004\u0018\u00010\u00102\b\u0010=\u001a\u0004\u0018\u00010\u0002\u001a<\u0010:\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010'\u001a\u00020(2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u00022\b\u00107\u001a\u0004\u0018\u00010\u0010H\u0002\u001a\u0010\u0010?\u001a\u00020\u00102\u0006\u0010;\u001a\u00020>H\u0007\u001a\u0018\u0010@\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u0010H\u0002\u001a\u0012\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010\u0002H\u0002\u001a\u0010\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020\u0002H\u0002\u001a\u0018\u0010G\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u0010H\u0002\u001a\u0018\u0010H\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u0010H\u0002\u001aB\u0010I\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u00102\u0006\u0010J\u001a\u00020\u00022\u0006\u0010'\u001a\u00020(2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u0002H\u0002\u001a\u001a\u0010K\u001a\u00020L2\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010M\u001a\u0004\u0018\u00010EH\u0002\u001a2\u0010N\u001a\u00020\u000e*\u00020O2#\b\u0004\u0010P\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\bQ\u0012\b\b\u001b\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u000e0\u0013H\u0082\b\u001a%\u0010N\u001a\u00020\u000e*\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00022\u000e\b\u0004\u0010P\u001a\b\u0012\u0004\u0012\u00020\u000e0RH\u0082\b\"\"\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u0001X\u0082\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0004\u0010\u0005\"\u0014\u0010\u0006\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u0002X\u0082T¢\u0006\u0002\n\u0000¨\u0006S"}, d2 = {"KNOWN_KOTLIN_PLUGIN_IDS", "Ljava/util/HashSet;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getKNOWN_KOTLIN_PLUGIN_IDS$annotations", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "getLOG", "()Lcom/intellij/openapi/diagnostic/Logger;", "dateTimeFormatter", "Ljava/time/format/DateTimeFormatter;", "defaultXPointerValue", "applyPartialContainer", "", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/RawPluginDescriptor;", "to", "extractor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ContainerDescriptor;", "checkXInclude", "", "elementName", "reader", "Lorg/jetbrains/kotlin/org/codehaus/stax2/XMLStreamReader2;", "findAttributeValue", "name", "getEventTypeString", "eventType", "", "getNullifiedAttributeValue", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "getNullifiedContent", "parseReleaseDate", "Ljava/time/LocalDate;", "dateString", "readActions", "descriptor", "readContext", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ReadModuleContext;", "readComponents", "containerDescriptor", "readContent", "readDependencies", "readExtensionPoints", "pathResolver", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PathResolver;", "dataLoader", "Lorg/jetbrains/kotlin/com/intellij/platform/util/plugins/DataLoader;", "includeBase", "readExtensions", "interner", "Lorg/jetbrains/kotlin/com/intellij/util/XmlInterner;", "readInclude", "readInto", "allowedPointer", "readListeners", "readModuleDescriptor", "input", "Ljava/io/InputStream;", "locationSource", "", "readModuleDescriptorForTest", "readOldDepends", "readOrder", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/LoadingOrder;", "orderAttr", "readOs", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionDescriptor$Os;", "value", "readProduct", "readRootAttributes", "readRootElementChild", "localName", "readServiceDescriptor", "Lorg/jetbrains/kotlin/com/intellij/openapi/components/ServiceDescriptor;", "os", "consumeChildElements", "Ljavax/xml/stream/XMLStreamReader;", "consumer", "Lkotlin/ParameterName;", "Lkotlin/Function0;", "intellij.platform.core.impl"}, k = 2, mv = {1, 5, 1})
public final class XmlReader {
    private static final HashSet<String> KNOWN_KOTLIN_PLUGIN_IDS = new HashSet<>(Arrays.asList(UserVisibleIrModulesSupport.KOTLIN_LIBRARY_PREFIX, "org.jetbrains.kotlin.com.intellij.appcode.kmm", "org.jetbrains.kotlin.native.appcode"));
    private static DateTimeFormatter dateTimeFormatter;

    public static final RawPluginDescriptor readModuleDescriptor(InputStream input, ReadModuleContext readContext, PathResolver pathResolver, DataLoader dataLoader, String str, RawPluginDescriptor rawPluginDescriptor, String str2) {
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(readContext, "readContext");
        Intrinsics.checkNotNullParameter(pathResolver, "pathResolver");
        Intrinsics.checkNotNullParameter(dataLoader, "dataLoader");
        return readModuleDescriptor(StaxFactory.createNonCoalescingXmlStreamReader(input, str2), readContext, pathResolver, dataLoader, str, rawPluginDescriptor);
    }

    public static final RawPluginDescriptor readModuleDescriptor(byte[] input, ReadModuleContext readContext, PathResolver pathResolver, DataLoader dataLoader, String str, RawPluginDescriptor rawPluginDescriptor, String str2) {
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(readContext, "readContext");
        Intrinsics.checkNotNullParameter(pathResolver, "pathResolver");
        Intrinsics.checkNotNullParameter(dataLoader, "dataLoader");
        return readModuleDescriptor(StaxFactory.createNonCoalescingXmlStreamReader(input, str2), readContext, pathResolver, dataLoader, str, rawPluginDescriptor);
    }

    private static final RawPluginDescriptor readModuleDescriptor(XMLStreamReader2 xMLStreamReader2, ReadModuleContext readModuleContext, PathResolver pathResolver, DataLoader dataLoader, String str, RawPluginDescriptor rawPluginDescriptor) throws XMLStreamException {
        try {
            if (xMLStreamReader2.getEventType() != 7) {
                throw new XMLStreamException("State 7 is expected, but current state is " + getEventTypeString(xMLStreamReader2.getEventType()), xMLStreamReader2.getLocation());
            }
            if (rawPluginDescriptor == null) {
                rawPluginDescriptor = new RawPluginDescriptor();
            }
            while (xMLStreamReader2.next() != 1) {
            }
            if (xMLStreamReader2.isStartElement()) {
                readRootAttributes(xMLStreamReader2, rawPluginDescriptor);
                XMLStreamReader2 xMLStreamReader3 = xMLStreamReader2;
                xMLStreamReader3.isStartElement();
                while (true) {
                    int next = xMLStreamReader3.next();
                    if (next != 9 && next != 12) {
                        switch (next) {
                            case 1:
                                String localName = xMLStreamReader3.getLocalName();
                                Intrinsics.checkNotNullExpressionValue(localName, "localName");
                                readRootElementChild(xMLStreamReader2, rawPluginDescriptor, localName, readModuleContext, pathResolver, dataLoader, str);
                                xMLStreamReader2.isEndElement();
                                xMLStreamReader3.isEndElement();
                                break;
                            case 2:
                                xMLStreamReader2.closeCompletely();
                                return rawPluginDescriptor;
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                break;
                            default:
                                throw new XMLStreamException("Unexpected state: " + getEventTypeString(xMLStreamReader3.getEventType()), xMLStreamReader3.getLocation());
                        }
                    }
                }
            } else {
                xMLStreamReader2.closeCompletely();
                return rawPluginDescriptor;
            }
        } catch (Throwable th) {
            xMLStreamReader2.closeCompletely();
            throw th;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static final void readRootAttributes(XMLStreamReader2 xMLStreamReader2, RawPluginDescriptor rawPluginDescriptor) {
        String nullifiedAttributeValue;
        int attributeCount = xMLStreamReader2.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i);
            if (attributeLocalName != null) {
                switch (attributeLocalName.hashCode()) {
                    case -1569215796:
                        if (attributeLocalName.equals("implementation-detail")) {
                            rawPluginDescriptor.implementationDetail = xMLStreamReader2.getAttributeAsBoolean(i);
                        }
                        break;
                    case -910340697:
                        if (attributeLocalName.equals("require-restart")) {
                            rawPluginDescriptor.isRestartRequired = xMLStreamReader2.getAttributeAsBoolean(i);
                        }
                        break;
                    case -807062458:
                        if (attributeLocalName.equals("package")) {
                            rawPluginDescriptor.package = getNullifiedAttributeValue(xMLStreamReader2, i);
                        }
                        break;
                    case 116079:
                        if (attributeLocalName.equals("url")) {
                            rawPluginDescriptor.url = getNullifiedAttributeValue(xMLStreamReader2, i);
                        }
                        break;
                    case 351608024:
                        if (attributeLocalName.equals("version") && (nullifiedAttributeValue = getNullifiedAttributeValue(xMLStreamReader2, i)) != null) {
                            try {
                                Integer.valueOf(Integer.parseInt(nullifiedAttributeValue));
                            } catch (NumberFormatException e) {
                                getLOG().error("Cannot parse version: " + nullifiedAttributeValue + '\'', e);
                                Unit unit = Unit.INSTANCE;
                            }
                        }
                        break;
                    case 1118021592:
                        if (attributeLocalName.equals("allow-bundled-update")) {
                            rawPluginDescriptor.isBundledUpdateAllowed = xMLStreamReader2.getAttributeAsBoolean(i);
                        }
                        break;
                    case 1851299131:
                        if (attributeLocalName.equals("use-idea-classloader")) {
                            rawPluginDescriptor.isUseIdeaClassLoader = xMLStreamReader2.getAttributeAsBoolean(i);
                        }
                        break;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:143:0x02b0  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final void readRootElementChild(XMLStreamReader2 xMLStreamReader2, RawPluginDescriptor rawPluginDescriptor, String str, ReadModuleContext readModuleContext, PathResolver pathResolver, DataLoader dataLoader, String str2) throws XMLStreamException {
        int i = 0;
        switch (str.hashCode()) {
            case -1865226545:
                if (!str.equals("applicationListeners")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readListeners(xMLStreamReader2, rawPluginDescriptor.appContainerDescriptor);
                }
                break;
            case -1809421292:
                if (!str.equals("extensions")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readExtensions(xMLStreamReader2, rawPluginDescriptor, readModuleContext.getInterner());
                }
                break;
            case -1724546052:
                if (!str.equals(MediaTrack.ROLE_DESCRIPTION)) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    rawPluginDescriptor.description = getNullifiedContent(xMLStreamReader2);
                }
                break;
            case -1185811868:
                if (!str.equals("change-notes")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    rawPluginDescriptor.changeNotes = getNullifiedContent(xMLStreamReader2);
                }
                break;
            case -1161803523:
                if (!str.equals("actions")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readActions(rawPluginDescriptor, xMLStreamReader2, readModuleContext);
                }
                break;
            case -1124611295:
                if (!str.equals("resource-bundle")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    rawPluginDescriptor.resourceBundleBaseName = getNullifiedContent(xMLStreamReader2);
                }
                break;
            case -1097462182:
                if (str.equals("locale")) {
                    xMLStreamReader2.skipElement();
                } else {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                }
                break;
            case -1068784020:
                if (str.equals("module")) {
                    String strFindAttributeValue = findAttributeValue(xMLStreamReader2, "value");
                    if (strFindAttributeValue != null) {
                        List<PluginId> list = rawPluginDescriptor.modules;
                        if (list == null) {
                            rawPluginDescriptor.modules = Collections.singletonList(PluginId.getId(strFindAttributeValue));
                        } else {
                            if (list.size() == 1) {
                                ArrayList arrayList = new ArrayList(4);
                                arrayList.addAll(list);
                                rawPluginDescriptor.modules = arrayList;
                                list = arrayList;
                            }
                            PluginId id2 = PluginId.getId(strFindAttributeValue);
                            Intrinsics.checkNotNullExpressionValue(id2, "PluginId.getId(moduleName)");
                            list.add(id2);
                        }
                    }
                    xMLStreamReader2.skipElement();
                } else {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                }
                break;
            case -963881203:
                if (!str.equals("product-descriptor")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readProduct(xMLStreamReader2, rawPluginDescriptor);
                }
                break;
            case -820075192:
                if (str.equals("vendor")) {
                    int attributeCount = xMLStreamReader2.getAttributeCount();
                    while (i < attributeCount) {
                        String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i);
                        if (attributeLocalName != null) {
                            int iHashCode = attributeLocalName.hashCode();
                            if (iHashCode != 116079) {
                                if (iHashCode == 96619420 && attributeLocalName.equals("email")) {
                                    rawPluginDescriptor.vendorEmail = getNullifiedAttributeValue(xMLStreamReader2, i);
                                }
                            } else if (attributeLocalName.equals("url")) {
                                rawPluginDescriptor.vendorUrl = getNullifiedAttributeValue(xMLStreamReader2, i);
                            }
                        }
                        i++;
                    }
                    rawPluginDescriptor.vendor = getNullifiedContent(xMLStreamReader2);
                } else {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                }
                break;
            case -708580790:
                if (!str.equals("project-components")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readComponents(xMLStreamReader2, rawPluginDescriptor.projectContainerDescriptor);
                }
                break;
            case -494005466:
                if (!str.equals("projectListeners")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readListeners(xMLStreamReader2, rawPluginDescriptor.projectContainerDescriptor);
                }
                break;
            case 3355:
                if (str.equals("id")) {
                    if (rawPluginDescriptor.id == null) {
                        rawPluginDescriptor.id = getNullifiedContent(xMLStreamReader2);
                    } else if (!KNOWN_KOTLIN_PLUGIN_IDS.contains(rawPluginDescriptor.id)) {
                        Logger log = getLOG();
                        StringBuilder sb = new StringBuilder("id redefinition (");
                        LocationInfo locationInfo = xMLStreamReader2.getLocationInfo();
                        Intrinsics.checkNotNullExpressionValue(locationInfo, "reader.locationInfo");
                        log.warn(sb.append(locationInfo.getLocation()).append(')').toString());
                        rawPluginDescriptor.id = getNullifiedContent(xMLStreamReader2);
                    } else {
                        xMLStreamReader2.skipElement();
                    }
                } else {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                }
                break;
            case 3373707:
                if (!str.equals("name")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    rawPluginDescriptor.name = getNullifiedContent(xMLStreamReader2);
                }
                break;
            case 50511102:
                if (!str.equals("category")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    rawPluginDescriptor.category = getNullifiedContent(xMLStreamReader2);
                }
                break;
            case 102922163:
                if (!str.equals("application-components")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readComponents(xMLStreamReader2, rawPluginDescriptor.appContainerDescriptor);
                }
                break;
            case 351608024:
                if (str.equals("version")) {
                    if (rawPluginDescriptor.version == null || !KNOWN_KOTLIN_PLUGIN_IDS.contains(rawPluginDescriptor.id)) {
                        rawPluginDescriptor.version = getNullifiedContent(xMLStreamReader2);
                    } else {
                        xMLStreamReader2.skipElement();
                    }
                } else {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                }
                break;
            case 503774505:
                if (!str.equals("dependencies")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readDependencies(xMLStreamReader2, rawPluginDescriptor, readModuleContext);
                }
                break;
            case 805837185:
                if (str.equals("helpset")) {
                    xMLStreamReader2.skipElement();
                } else {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                }
                break;
            case 881478935:
                if (!str.equals("module-components")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readComponents(xMLStreamReader2, rawPluginDescriptor.moduleContainerDescriptor);
                }
                break;
            case 933517314:
                if (str.equals("idea-version")) {
                    int attributeCount2 = xMLStreamReader2.getAttributeCount();
                    while (i < attributeCount2) {
                        String attributeLocalName2 = xMLStreamReader2.getAttributeLocalName(i);
                        if (attributeLocalName2 != null) {
                            int iHashCode2 = attributeLocalName2.hashCode();
                            if (iHashCode2 == 663943707) {
                                if (attributeLocalName2.equals("since-build")) {
                                    rawPluginDescriptor.sinceBuild = getNullifiedAttributeValue(xMLStreamReader2, i);
                                }
                            } else if (iHashCode2 == 1912134463 && attributeLocalName2.equals("until-build")) {
                                rawPluginDescriptor.untilBuild = getNullifiedAttributeValue(xMLStreamReader2, i);
                            }
                        }
                        i++;
                    }
                    xMLStreamReader2.skipElement();
                } else {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                }
                break;
            case 951530617:
                if (!str.equals(FirebaseAnalytics.Param.CONTENT)) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readContent(xMLStreamReader2, rawPluginDescriptor, readModuleContext);
                }
                break;
            case 1269955074:
                if (!str.equals("extensionPoints")) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readExtensionPoints(xMLStreamReader2, rawPluginDescriptor, readModuleContext, pathResolver, dataLoader, str2);
                }
                break;
            case 1296040026:
                if (str.equals("incompatible-with")) {
                    String nullifiedContent = getNullifiedContent(xMLStreamReader2);
                    if (nullifiedContent != null) {
                        ArrayList arrayList2 = rawPluginDescriptor.incompatibilities;
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                            rawPluginDescriptor.incompatibilities = arrayList2;
                        }
                        PluginId id3 = PluginId.getId(nullifiedContent);
                        Intrinsics.checkNotNullExpressionValue(id3, "PluginId.getId(it)");
                        arrayList2.add(id3);
                    }
                } else {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                }
                break;
            case 1554151303:
                if (!str.equals(KotlinLibraryKt.KLIB_PROPERTY_DEPENDS)) {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                } else {
                    readOldDepends(xMLStreamReader2, rawPluginDescriptor);
                }
                break;
            case 1942574248:
                if (str.equals("include")) {
                    readInclude(xMLStreamReader2, rawPluginDescriptor, readModuleContext, pathResolver, dataLoader, str2, "xpointer(/idea-plugin/*)");
                } else {
                    getLOG().error("Unknown element: " + str);
                    xMLStreamReader2.skipElement();
                }
                break;
            default:
                getLOG().error("Unknown element: " + str);
                xMLStreamReader2.skipElement();
                break;
        }
        if (!xMLStreamReader2.isEndElement()) {
            throw new XMLStreamException("Unexpected state (expected=END_ELEMENT, actual=" + getEventTypeString(xMLStreamReader2.getEventType()) + ", lastProcessedElement=" + str + Constant.AFTER_QUTO, xMLStreamReader2.getLocation());
        }
    }

    private static final void readActions(RawPluginDescriptor rawPluginDescriptor, XMLStreamReader2 xMLStreamReader2, ReadModuleContext readModuleContext) throws XMLStreamException {
        ArrayList arrayList = rawPluginDescriptor.actions;
        if (arrayList == null) {
            arrayList = new ArrayList();
            rawPluginDescriptor.actions = arrayList;
        }
        String strFindAttributeValue = findAttributeValue(xMLStreamReader2, "resource-bundle");
        XMLStreamReader2 xMLStreamReader3 = xMLStreamReader2;
        xMLStreamReader3.isStartElement();
        while (true) {
            int next = xMLStreamReader3.next();
            if (next != 9 && next != 12) {
                switch (next) {
                    case 1:
                        String localName = xMLStreamReader3.getLocalName();
                        Intrinsics.checkNotNullExpressionValue(localName, "localName");
                        if (!checkXInclude(localName, xMLStreamReader2)) {
                            arrayList.add(new RawPluginDescriptor.ActionDescriptor(localName, XmlDomReader.readXmlAsModel(xMLStreamReader2, localName, readModuleContext.getInterner()), strFindAttributeValue));
                        }
                        xMLStreamReader3.isEndElement();
                        break;
                    case 2:
                        return;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        break;
                    default:
                        throw new XMLStreamException("Unexpected state: " + getEventTypeString(xMLStreamReader3.getEventType()), xMLStreamReader3.getLocation());
                }
            }
        }
    }

    private static final void readOldDepends(XMLStreamReader2 xMLStreamReader2, RawPluginDescriptor rawPluginDescriptor) {
        String attributeValue = null;
        int attributeCount = xMLStreamReader2.getAttributeCount();
        boolean attributeAsBoolean = false;
        for (int i = 0; i < attributeCount; i++) {
            String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i);
            if (attributeLocalName != null) {
                int iHashCode = attributeLocalName.hashCode();
                if (iHashCode == -79017120) {
                    if (attributeLocalName.equals(SchemaSymbols.ATTVAL_OPTIONAL)) {
                        attributeAsBoolean = xMLStreamReader2.getAttributeAsBoolean(i);
                    }
                } else if (iHashCode == -29078233 && attributeLocalName.equals("config-file")) {
                    attributeValue = xMLStreamReader2.getAttributeValue(i);
                }
            }
        }
        String nullifiedContent = getNullifiedContent(xMLStreamReader2);
        if (nullifiedContent != null) {
            ArrayList arrayList = rawPluginDescriptor.depends;
            if (arrayList == null) {
                arrayList = new ArrayList();
                rawPluginDescriptor.depends = arrayList;
            }
            PluginId id2 = PluginId.getId(nullifiedContent);
            Intrinsics.checkNotNullExpressionValue(id2, "PluginId.getId(dependencyIdString)");
            arrayList.add(new PluginDependency(id2, attributeValue, attributeAsBoolean));
        }
    }

    /* JADX WARN: Code duplicated, block: B:68:0x0141  */
    /* JADX WARN: Code duplicated, block: B:70:0x0149  */
    /* JADX WARN: Code duplicated, block: B:71:0x014d  */
    /* JADX WARN: Code duplicated, block: B:73:0x0159  */
    /* JADX WARN: Code duplicated, block: B:75:0x0165  */
    /* JADX WARN: Code duplicated, block: B:78:0x016a  */
    /* JADX WARN: Code duplicated, block: B:81:0x0185  */
    /* JADX WARN: Code duplicated, block: B:82:0x0192  */
    /* JADX WARN: Code duplicated, block: B:84:0x0199  */
    /* JADX WARN: Code duplicated, block: B:85:0x01ae  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static final void readExtensions(XMLStreamReader2 xMLStreamReader2, RawPluginDescriptor rawPluginDescriptor, XmlInterner xmlInterner) throws XMLStreamException {
        XmlElement xmlAsModel;
        HashMap map;
        ExtensionDescriptor extensionDescriptor;
        List list;
        ContainerDescriptor containerDescriptor;
        String strFindAttributeValue = findAttributeValue(xMLStreamReader2, "defaultExtensionNs");
        XMLStreamReader2 xMLStreamReader3 = xMLStreamReader2;
        xMLStreamReader3.isStartElement();
        while (true) {
            int next = xMLStreamReader3.next();
            if (next != 9 && next != 12) {
                switch (next) {
                    case 1:
                        String localName = xMLStreamReader3.getLocalName();
                        Intrinsics.checkNotNullExpressionValue(localName, "localName");
                        if (!checkXInclude(localName, xMLStreamReader2)) {
                            XmlElement xmlElement = null;
                            LoadingOrder loadingOrder = LoadingOrder.ANY;
                            int attributeCount = xMLStreamReader2.getAttributeCount();
                            String strName = null;
                            String attributeValue = null;
                            ExtensionDescriptor.Os os = null;
                            String nullifiedAttributeValue = null;
                            LoadingOrder order = loadingOrder;
                            for (int i = 0; i < attributeCount; i++) {
                                String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i);
                                if (attributeLocalName != null) {
                                    switch (attributeLocalName.hashCode()) {
                                        case 3355:
                                            if (attributeLocalName.equals("id")) {
                                                nullifiedAttributeValue = getNullifiedAttributeValue(xMLStreamReader2, i);
                                            }
                                            break;
                                        case XKeySym.XK_Thai_saraaimaimalai /* 3556 */:
                                            if (attributeLocalName.equals("os")) {
                                                String attributeValue2 = xMLStreamReader2.getAttributeValue(i);
                                                Intrinsics.checkNotNullExpressionValue(attributeValue2, "reader.getAttributeValue(i)");
                                                os = readOs(attributeValue2);
                                            }
                                            break;
                                        case 106006350:
                                            if (attributeLocalName.equals("order")) {
                                                order = readOrder(xMLStreamReader2.getAttributeValue(i));
                                            }
                                            break;
                                        case 106845584:
                                            if (attributeLocalName.equals(KHRLightsPunctual.GLTFLight.TYPE_POINT)) {
                                                strName = getNullifiedAttributeValue(xMLStreamReader2, i);
                                            }
                                            break;
                                        case 152997094:
                                            if (attributeLocalName.equals("implementationClass")) {
                                                attributeValue = xMLStreamReader2.getAttributeValue(i);
                                            }
                                            break;
                                        case 1683336114:
                                            if (attributeLocalName.equals("implementation")) {
                                                attributeValue = xMLStreamReader2.getAttributeValue(i);
                                            }
                                            break;
                                    }
                                }
                            }
                            if (strName == null) {
                                strName = xmlInterner.name((strFindAttributeValue != null ? strFindAttributeValue : xMLStreamReader2.getNamespaceURI()) + '.' + localName);
                            }
                            String str = strName;
                            if (str != null) {
                                int iHashCode = str.hashCode();
                                if (iHashCode != -1947960913) {
                                    if (iHashCode != -1286155777) {
                                        if (iHashCode == -698444602 && str.equals("org.jetbrains.kotlin.com.intellij.projectService")) {
                                            containerDescriptor = rawPluginDescriptor.projectContainerDescriptor;
                                            containerDescriptor.addService(readServiceDescriptor(xMLStreamReader2, os));
                                            xMLStreamReader2.skipElement();
                                        } else {
                                            if (Intrinsics.areEqual(str, "org.jetbrains.kotlin.com.intellij.postStartupActivity")) {
                                                xMLStreamReader2.skipElement();
                                            } else {
                                                xmlAsModel = XmlDomReader.readXmlAsModel(xMLStreamReader2, null, xmlInterner);
                                                if (xmlAsModel.children.isEmpty() || !xmlAsModel.attributes.keySet().isEmpty()) {
                                                    xmlElement = xmlAsModel;
                                                }
                                            }
                                            map = rawPluginDescriptor.epNameToExtensions;
                                            if (map == null) {
                                                map = new HashMap();
                                                rawPluginDescriptor.epNameToExtensions = map;
                                            }
                                            Intrinsics.checkNotNullExpressionValue(order, "order");
                                            extensionDescriptor = new ExtensionDescriptor(attributeValue, os, nullifiedAttributeValue, order, xmlElement);
                                            list = (List) map.get(str);
                                            if (list == null) {
                                                List listSingletonList = Collections.singletonList(extensionDescriptor);
                                                Intrinsics.checkNotNullExpressionValue(listSingletonList, "Collections.singletonList(extensionDescriptor)");
                                                map.put(str, listSingletonList);
                                            } else if (list.size() == 1) {
                                                ArrayList arrayList = new ArrayList(4);
                                                arrayList.add(list.get(0));
                                                arrayList.add(extensionDescriptor);
                                                map.put(str, arrayList);
                                            } else {
                                                list.add(extensionDescriptor);
                                            }
                                            xMLStreamReader2.isEndElement();
                                        }
                                    } else if (str.equals("org.jetbrains.kotlin.com.intellij.moduleService")) {
                                        containerDescriptor = rawPluginDescriptor.moduleContainerDescriptor;
                                        containerDescriptor.addService(readServiceDescriptor(xMLStreamReader2, os));
                                        xMLStreamReader2.skipElement();
                                    } else {
                                        if (Intrinsics.areEqual(str, "org.jetbrains.kotlin.com.intellij.postStartupActivity")) {
                                            xMLStreamReader2.skipElement();
                                        } else {
                                            xmlAsModel = XmlDomReader.readXmlAsModel(xMLStreamReader2, null, xmlInterner);
                                            if (xmlAsModel.children.isEmpty()) {
                                                xmlElement = xmlAsModel;
                                            } else {
                                                xmlElement = xmlAsModel;
                                            }
                                        }
                                        map = rawPluginDescriptor.epNameToExtensions;
                                        if (map == null) {
                                            map = new HashMap();
                                            rawPluginDescriptor.epNameToExtensions = map;
                                        }
                                        Intrinsics.checkNotNullExpressionValue(order, "order");
                                        extensionDescriptor = new ExtensionDescriptor(attributeValue, os, nullifiedAttributeValue, order, xmlElement);
                                        list = (List) map.get(str);
                                        if (list == null) {
                                            List listSingletonList2 = Collections.singletonList(extensionDescriptor);
                                            Intrinsics.checkNotNullExpressionValue(listSingletonList2, "Collections.singletonList(extensionDescriptor)");
                                            map.put(str, listSingletonList2);
                                        } else if (list.size() == 1) {
                                            ArrayList arrayList2 = new ArrayList(4);
                                            arrayList2.add(list.get(0));
                                            arrayList2.add(extensionDescriptor);
                                            map.put(str, arrayList2);
                                        } else {
                                            list.add(extensionDescriptor);
                                        }
                                        xMLStreamReader2.isEndElement();
                                    }
                                } else if (str.equals("org.jetbrains.kotlin.com.intellij.applicationService")) {
                                    containerDescriptor = rawPluginDescriptor.appContainerDescriptor;
                                    containerDescriptor.addService(readServiceDescriptor(xMLStreamReader2, os));
                                    xMLStreamReader2.skipElement();
                                } else {
                                    if (Intrinsics.areEqual(str, "org.jetbrains.kotlin.com.intellij.postStartupActivity")) {
                                        xMLStreamReader2.skipElement();
                                    } else {
                                        xmlAsModel = XmlDomReader.readXmlAsModel(xMLStreamReader2, null, xmlInterner);
                                        if (xmlAsModel.children.isEmpty()) {
                                            xmlElement = xmlAsModel;
                                        } else {
                                            xmlElement = xmlAsModel;
                                        }
                                    }
                                    map = rawPluginDescriptor.epNameToExtensions;
                                    if (map == null) {
                                        map = new HashMap();
                                        rawPluginDescriptor.epNameToExtensions = map;
                                    }
                                    Intrinsics.checkNotNullExpressionValue(order, "order");
                                    extensionDescriptor = new ExtensionDescriptor(attributeValue, os, nullifiedAttributeValue, order, xmlElement);
                                    list = (List) map.get(str);
                                    if (list == null) {
                                        List listSingletonList3 = Collections.singletonList(extensionDescriptor);
                                        Intrinsics.checkNotNullExpressionValue(listSingletonList3, "Collections.singletonList(extensionDescriptor)");
                                        map.put(str, listSingletonList3);
                                    } else if (list.size() == 1) {
                                        ArrayList arrayList3 = new ArrayList(4);
                                        arrayList3.add(list.get(0));
                                        arrayList3.add(extensionDescriptor);
                                        map.put(str, arrayList3);
                                    } else {
                                        list.add(extensionDescriptor);
                                    }
                                    xMLStreamReader2.isEndElement();
                                }
                            } else {
                                if (Intrinsics.areEqual(str, "org.jetbrains.kotlin.com.intellij.postStartupActivity")) {
                                    xMLStreamReader2.skipElement();
                                } else {
                                    xmlAsModel = XmlDomReader.readXmlAsModel(xMLStreamReader2, null, xmlInterner);
                                    if (xmlAsModel.children.isEmpty()) {
                                        xmlElement = xmlAsModel;
                                    } else {
                                        xmlElement = xmlAsModel;
                                    }
                                }
                                map = rawPluginDescriptor.epNameToExtensions;
                                if (map == null) {
                                    map = new HashMap();
                                    rawPluginDescriptor.epNameToExtensions = map;
                                }
                                Intrinsics.checkNotNullExpressionValue(order, "order");
                                extensionDescriptor = new ExtensionDescriptor(attributeValue, os, nullifiedAttributeValue, order, xmlElement);
                                list = (List) map.get(str);
                                if (list == null) {
                                    List listSingletonList4 = Collections.singletonList(extensionDescriptor);
                                    Intrinsics.checkNotNullExpressionValue(listSingletonList4, "Collections.singletonList(extensionDescriptor)");
                                    map.put(str, listSingletonList4);
                                } else if (list.size() == 1) {
                                    ArrayList arrayList4 = new ArrayList(4);
                                    arrayList4.add(list.get(0));
                                    arrayList4.add(extensionDescriptor);
                                    map.put(str, arrayList4);
                                } else {
                                    list.add(extensionDescriptor);
                                }
                                xMLStreamReader2.isEndElement();
                            }
                        }
                        xMLStreamReader3.isEndElement();
                        break;
                    case 2:
                        return;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        break;
                    default:
                        throw new XMLStreamException("Unexpected state: " + getEventTypeString(xMLStreamReader3.getEventType()), xMLStreamReader3.getLocation());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LoadingOrder readOrder(String str) {
        if (str != null) {
            int iHashCode = str.hashCode();
            if (iHashCode != 3314326) {
                if (iHashCode == 97440432 && str.equals("first")) {
                    LoadingOrder loadingOrder = LoadingOrder.FIRST;
                    Intrinsics.checkNotNullExpressionValue(loadingOrder, "LoadingOrder.FIRST");
                    return loadingOrder;
                }
            } else if (str.equals("last")) {
                LoadingOrder loadingOrder2 = LoadingOrder.LAST;
                Intrinsics.checkNotNullExpressionValue(loadingOrder2, "LoadingOrder.LAST");
                return loadingOrder2;
            }
            return new LoadingOrder(str);
        }
        LoadingOrder loadingOrder3 = LoadingOrder.ANY;
        Intrinsics.checkNotNullExpressionValue(loadingOrder3, "LoadingOrder.ANY");
        return loadingOrder3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean checkXInclude(String str, XMLStreamReader2 xMLStreamReader2) throws XMLStreamException {
        if (!Intrinsics.areEqual(str, "include") || !Intrinsics.areEqual(xMLStreamReader2.getNamespaceURI(), "http://www.w3.org/2001/XInclude")) {
            return false;
        }
        getLOG().error("`include` is supported only on a root level (" + xMLStreamReader2.getLocation() + ')');
        xMLStreamReader2.skipElement();
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:108:0x023c  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static final void readExtensionPoints(XMLStreamReader2 xMLStreamReader2, RawPluginDescriptor rawPluginDescriptor, ReadModuleContext readModuleContext, PathResolver pathResolver, DataLoader dataLoader, String str) throws XMLStreamException {
        ContainerDescriptor containerDescriptor;
        String str2;
        XMLStreamReader2 xMLStreamReader3 = xMLStreamReader2;
        xMLStreamReader3.isStartElement();
        while (true) {
            int next = xMLStreamReader3.next();
            if (next != 9 && next != 12) {
                switch (next) {
                    case 1:
                        String localName = xMLStreamReader3.getLocalName();
                        Intrinsics.checkNotNullExpressionValue(localName, "localName");
                        if (!Intrinsics.areEqual(localName, "extensionPoint")) {
                            if (!Intrinsics.areEqual(localName, "include") || !Intrinsics.areEqual(xMLStreamReader2.getNamespaceURI(), "http://www.w3.org/2001/XInclude")) {
                                getLOG().error("Unknown element: " + localName + " (" + xMLStreamReader2.getLocation() + ')');
                                xMLStreamReader2.skipElement();
                            } else {
                                RawPluginDescriptor rawPluginDescriptor2 = new RawPluginDescriptor();
                                readInclude(xMLStreamReader2, rawPluginDescriptor2, readModuleContext, pathResolver, dataLoader, str, "xpointer(/idea-plugin/extensionPoints/*)");
                                getLOG().warn("`include` is supported only on a root level (" + xMLStreamReader2.getLocation() + ')');
                                List<ExtensionPointDescriptor> list = rawPluginDescriptor2.appContainerDescriptor.extensionPoints;
                                if (list != null) {
                                    ContainerDescriptor containerDescriptor2 = rawPluginDescriptor.appContainerDescriptor;
                                    if (containerDescriptor2.extensionPoints == null) {
                                        containerDescriptor2.extensionPoints = list;
                                    } else {
                                        List<ExtensionPointDescriptor> list2 = containerDescriptor2.extensionPoints;
                                        Intrinsics.checkNotNull(list2);
                                        list2.addAll(list);
                                    }
                                }
                                List<ExtensionPointDescriptor> list3 = rawPluginDescriptor2.projectContainerDescriptor.extensionPoints;
                                if (list3 != null) {
                                    ContainerDescriptor containerDescriptor3 = rawPluginDescriptor.projectContainerDescriptor;
                                    if (containerDescriptor3.extensionPoints == null) {
                                        containerDescriptor3.extensionPoints = list3;
                                    } else {
                                        List<ExtensionPointDescriptor> list4 = containerDescriptor3.extensionPoints;
                                        Intrinsics.checkNotNull(list4);
                                        list4.addAll(list3);
                                    }
                                }
                                List<ExtensionPointDescriptor> list5 = rawPluginDescriptor2.moduleContainerDescriptor.extensionPoints;
                                if (list5 != null) {
                                    ContainerDescriptor containerDescriptor4 = rawPluginDescriptor.moduleContainerDescriptor;
                                    if (containerDescriptor4.extensionPoints == null) {
                                        containerDescriptor4.extensionPoints = list5;
                                    } else {
                                        List<ExtensionPointDescriptor> list6 = containerDescriptor4.extensionPoints;
                                        Intrinsics.checkNotNull(list6);
                                        list6.addAll(list5);
                                    }
                                }
                            }
                        } else {
                            String nullifiedAttributeValue = null;
                            int attributeCount = xMLStreamReader2.getAttributeCount();
                            String nullifiedAttributeValue2 = null;
                            String nullifiedAttributeValue3 = null;
                            String attributeValue = null;
                            String nullifiedAttributeValue4 = null;
                            boolean attributeAsBoolean = false;
                            for (int i = 0; i < attributeCount; i++) {
                                String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i);
                                if (attributeLocalName != null) {
                                    switch (attributeLocalName.hashCode()) {
                                        case -1998752856:
                                            if (attributeLocalName.equals("beanClass")) {
                                                nullifiedAttributeValue = getNullifiedAttributeValue(xMLStreamReader2, i);
                                            }
                                            break;
                                        case -572444345:
                                            if (attributeLocalName.equals("qualifiedName")) {
                                                attributeValue = xMLStreamReader2.getAttributeValue(i);
                                            }
                                            break;
                                        case 3002509:
                                            if (attributeLocalName.equals("area")) {
                                                nullifiedAttributeValue3 = getNullifiedAttributeValue(xMLStreamReader2, i);
                                            }
                                            break;
                                        case 3373707:
                                            if (attributeLocalName.equals("name")) {
                                                nullifiedAttributeValue4 = getNullifiedAttributeValue(xMLStreamReader2, i);
                                            }
                                            break;
                                        case 502623545:
                                            if (attributeLocalName.equals(PsiKeyword.INTERFACE)) {
                                                nullifiedAttributeValue2 = getNullifiedAttributeValue(xMLStreamReader2, i);
                                            }
                                            break;
                                        case 2124767295:
                                            if (attributeLocalName.equals("dynamic")) {
                                                attributeAsBoolean = xMLStreamReader2.getAttributeAsBoolean(i);
                                            }
                                            break;
                                    }
                                }
                            }
                            if (nullifiedAttributeValue == null && nullifiedAttributeValue2 == null) {
                                throw new RuntimeException("Neither beanClass nor interface attribute is specified for extension point at " + xMLStreamReader2.getLocation());
                            }
                            if (nullifiedAttributeValue != null && nullifiedAttributeValue2 != null) {
                                throw new RuntimeException("Both beanClass and interface attributes are specified for extension point at " + xMLStreamReader2.getLocation());
                            }
                            xMLStreamReader2.skipElement();
                            if (nullifiedAttributeValue3 != null) {
                                int iHashCode = nullifiedAttributeValue3.hashCode();
                                if (iHashCode != -660214604) {
                                    if (iHashCode == -528560719 && nullifiedAttributeValue3.equals("IDEA_PROJECT")) {
                                        containerDescriptor = rawPluginDescriptor.projectContainerDescriptor;
                                    } else {
                                        getLOG().error("Unknown area: " + nullifiedAttributeValue3);
                                    }
                                } else if (nullifiedAttributeValue3.equals("IDEA_MODULE")) {
                                    containerDescriptor = rawPluginDescriptor.moduleContainerDescriptor;
                                } else {
                                    getLOG().error("Unknown area: " + nullifiedAttributeValue3);
                                }
                            } else {
                                containerDescriptor = rawPluginDescriptor.appContainerDescriptor;
                            }
                            ArrayList arrayList = containerDescriptor.extensionPoints;
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                containerDescriptor.extensionPoints = arrayList;
                            }
                            String str3 = attributeValue != null ? attributeValue : nullifiedAttributeValue4;
                            if (str3 != null) {
                                boolean z = attributeValue != null;
                                if (nullifiedAttributeValue2 != null) {
                                    str2 = nullifiedAttributeValue2;
                                } else {
                                    Intrinsics.checkNotNull(nullifiedAttributeValue);
                                    str2 = nullifiedAttributeValue;
                                }
                                arrayList.add(new ExtensionPointDescriptor(str3, z, str2, nullifiedAttributeValue2 == null, attributeAsBoolean));
                            } else {
                                throw new RuntimeException("`name` attribute not specified for extension point at " + xMLStreamReader2.getLocation());
                            }
                        }
                        xMLStreamReader3.isEndElement();
                        break;
                    case 2:
                        return;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        break;
                    default:
                        throw new XMLStreamException("Unexpected state: " + getEventTypeString(xMLStreamReader3.getEventType()), xMLStreamReader3.getLocation());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:43:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:72:0x0137  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final ServiceDescriptor readServiceDescriptor(XMLStreamReader2 xMLStreamReader2, ExtensionDescriptor.Os os) {
        ServiceDescriptor.ClientKind clientKind;
        ServiceDescriptor.PreloadMode preloadMode;
        ServiceDescriptor.PreloadMode preloadMode2 = ServiceDescriptor.PreloadMode.FALSE;
        int attributeCount = xMLStreamReader2.getAttributeCount();
        String nullifiedAttributeValue = null;
        String nullifiedAttributeValue2 = null;
        String nullifiedAttributeValue3 = null;
        String nullifiedAttributeValue4 = null;
        String attributeValue = null;
        ServiceDescriptor.ClientKind clientKind2 = null;
        ServiceDescriptor.PreloadMode preloadMode3 = preloadMode2;
        boolean attributeAsBoolean = false;
        for (int i = 0; i < attributeCount; i++) {
            String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i);
            if (attributeLocalName != null) {
                switch (attributeLocalName.hashCode()) {
                    case -1357712437:
                        if (attributeLocalName.equals("client")) {
                            String attributeValue2 = xMLStreamReader2.getAttributeValue(i);
                            if (attributeValue2 != null) {
                                int iHashCode = attributeValue2.hashCode();
                                if (iHashCode != 96673) {
                                    if (iHashCode != 98708952) {
                                        if (iHashCode == 103145323 && attributeValue2.equals("local")) {
                                            clientKind = ServiceDescriptor.ClientKind.LOCAL;
                                            clientKind2 = clientKind;
                                        } else {
                                            getLOG().error("Unknown client value: " + xMLStreamReader2.getAttributeValue(i) + " at " + xMLStreamReader2.getLocation());
                                        }
                                    } else if (attributeValue2.equals("guest")) {
                                        clientKind = ServiceDescriptor.ClientKind.GUEST;
                                        clientKind2 = clientKind;
                                    } else {
                                        getLOG().error("Unknown client value: " + xMLStreamReader2.getAttributeValue(i) + " at " + xMLStreamReader2.getLocation());
                                    }
                                } else if (attributeValue2.equals(DevModeOverwritingStrategies.ALL)) {
                                    clientKind = ServiceDescriptor.ClientKind.ALL;
                                    clientKind2 = clientKind;
                                } else {
                                    getLOG().error("Unknown client value: " + xMLStreamReader2.getAttributeValue(i) + " at " + xMLStreamReader2.getLocation());
                                }
                            } else {
                                getLOG().error("Unknown client value: " + xMLStreamReader2.getAttributeValue(i) + " at " + xMLStreamReader2.getLocation());
                            }
                        }
                        break;
                    case -749969881:
                        if (attributeLocalName.equals("overrides")) {
                            attributeAsBoolean = xMLStreamReader2.getAttributeAsBoolean(i);
                        }
                        break;
                    case -363536216:
                        if (attributeLocalName.equals("configurationSchemaKey")) {
                            attributeValue = xMLStreamReader2.getAttributeValue(i);
                        }
                        break;
                    case -318476791:
                        if (!attributeLocalName.equals("preload")) {
                            break;
                        } else {
                            String attributeValue3 = xMLStreamReader2.getAttributeValue(i);
                            if (attributeValue3 != null) {
                                switch (attributeValue3) {
                                    case "notHeadless":
                                        preloadMode = ServiceDescriptor.PreloadMode.NOT_HEADLESS;
                                        preloadMode3 = preloadMode;
                                        break;
                                    case "true":
                                        preloadMode = ServiceDescriptor.PreloadMode.TRUE;
                                        preloadMode3 = preloadMode;
                                        break;
                                    case "await":
                                        preloadMode = ServiceDescriptor.PreloadMode.AWAIT;
                                        preloadMode3 = preloadMode;
                                        break;
                                    case "notLightEdit":
                                        preloadMode = ServiceDescriptor.PreloadMode.NOT_LIGHT_EDIT;
                                        preloadMode3 = preloadMode;
                                        break;
                                    default:
                                        getLOG().error("Unknown preload mode value " + xMLStreamReader2.getAttributeValue(i) + " at " + xMLStreamReader2.getLocation());
                                        break;
                                }
                            } else {
                                getLOG().error("Unknown preload mode value " + xMLStreamReader2.getAttributeValue(i) + " at " + xMLStreamReader2.getLocation());
                                break;
                            }
                        }
                        break;
                    case -1080828:
                        if (attributeLocalName.equals("serviceInterface")) {
                            nullifiedAttributeValue = getNullifiedAttributeValue(xMLStreamReader2, i);
                        }
                        break;
                    case 703585735:
                        if (attributeLocalName.equals("serviceImplementation")) {
                            nullifiedAttributeValue2 = getNullifiedAttributeValue(xMLStreamReader2, i);
                        }
                        break;
                    case 842127445:
                        if (attributeLocalName.equals("testServiceImplementation")) {
                            nullifiedAttributeValue3 = getNullifiedAttributeValue(xMLStreamReader2, i);
                        }
                        break;
                    case 1422978955:
                        if (attributeLocalName.equals("headlessImplementation")) {
                            nullifiedAttributeValue4 = getNullifiedAttributeValue(xMLStreamReader2, i);
                        }
                        break;
                }
            }
        }
        return new ServiceDescriptor(nullifiedAttributeValue, nullifiedAttributeValue2, nullifiedAttributeValue3, nullifiedAttributeValue4, attributeAsBoolean, attributeValue, preloadMode3, clientKind2, os);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static final void readProduct(XMLStreamReader2 xMLStreamReader2, RawPluginDescriptor rawPluginDescriptor) throws XMLStreamException {
        int attributeCount = xMLStreamReader2.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i);
            if (attributeLocalName != null) {
                switch (attributeLocalName.hashCode()) {
                    case -2012031564:
                        if (attributeLocalName.equals("release-date")) {
                            String attributeValue = xMLStreamReader2.getAttributeValue(i);
                            Intrinsics.checkNotNullExpressionValue(attributeValue, "reader.getAttributeValue(i)");
                            rawPluginDescriptor.releaseDate = parseReleaseDate(attributeValue);
                        }
                        break;
                    case -960351982:
                        if (attributeLocalName.equals("release-version")) {
                            try {
                                rawPluginDescriptor.releaseVersion = xMLStreamReader2.getAttributeAsInt(i);
                            } catch (TypedXMLStreamException unused) {
                                rawPluginDescriptor.releaseVersion = 0;
                            }
                        }
                        break;
                    case -79017120:
                        if (attributeLocalName.equals(SchemaSymbols.ATTVAL_OPTIONAL)) {
                            rawPluginDescriptor.isLicenseOptional = xMLStreamReader2.getAttributeAsBoolean(i);
                        }
                        break;
                    case 3059181:
                        if (attributeLocalName.equals(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY)) {
                            rawPluginDescriptor.productCode = getNullifiedAttributeValue(xMLStreamReader2, i);
                        }
                        break;
                }
            }
        }
        xMLStreamReader2.skipElement();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:22:0x0094  */
    /* JADX WARN: Code duplicated, block: B:26:0x00c5  */
    private static final void readComponents(XMLStreamReader2 xMLStreamReader2, ContainerDescriptor containerDescriptor) throws XMLStreamException {
        String str;
        String str2;
        int i;
        List<ComponentConfig> componentListToAdd$intellij_platform_core_impl = containerDescriptor.getComponentListToAdd$intellij_platform_core_impl();
        XMLStreamReader2 xMLStreamReader3 = xMLStreamReader2;
        xMLStreamReader3.isStartElement();
        while (true) {
            int next = xMLStreamReader3.next();
            int i2 = 9;
            if (next != 9) {
                int i3 = 12;
                if (next != 12) {
                    String str3 = "Unexpected state: ";
                    switch (next) {
                        case 1:
                            String localName = xMLStreamReader3.getLocalName();
                            String str4 = "localName";
                            Intrinsics.checkNotNullExpressionValue(localName, "localName");
                            if (Intrinsics.areEqual("component", localName)) {
                                xMLStreamReader3.isStartElement();
                                HashMap map = null;
                                String nullifiedContent = null;
                                String nullifiedContent2 = null;
                                String elementText = null;
                                ExtensionDescriptor.Os os = null;
                                boolean z = false;
                                boolean z2 = false;
                                while (true) {
                                    int next2 = xMLStreamReader3.next();
                                    if (next2 == i2 || next2 == i3) {
                                        componentListToAdd$intellij_platform_core_impl = componentListToAdd$intellij_platform_core_impl;
                                        str = str3;
                                        str2 = str4;
                                        map = map;
                                    } else {
                                        switch (next2) {
                                            case 1:
                                                map = map;
                                                String localName2 = xMLStreamReader3.getLocalName();
                                                Intrinsics.checkNotNullExpressionValue(localName2, str4);
                                                boolean z3 = true;
                                                switch (localName2) {
                                                    case "option":
                                                        int attributeCount = xMLStreamReader2.getAttributeCount();
                                                        componentListToAdd$intellij_platform_core_impl = componentListToAdd$intellij_platform_core_impl;
                                                        String nullifiedAttributeValue = null;
                                                        String nullifiedAttributeValue2 = null;
                                                        int i4 = 0;
                                                        while (i4 < attributeCount) {
                                                            String str5 = str3;
                                                            String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i4);
                                                            String str6 = str4;
                                                            if (attributeLocalName == null) {
                                                                i = attributeCount;
                                                            } else {
                                                                int iHashCode = attributeLocalName.hashCode();
                                                                i = attributeCount;
                                                                if (iHashCode == 3373707) {
                                                                    if (attributeLocalName.equals("name")) {
                                                                        nullifiedAttributeValue2 = getNullifiedAttributeValue(xMLStreamReader2, i4);
                                                                    }
                                                                } else if (iHashCode == 111972721 && attributeLocalName.equals("value")) {
                                                                    nullifiedAttributeValue = getNullifiedAttributeValue(xMLStreamReader2, i4);
                                                                }
                                                            }
                                                            i4++;
                                                            str3 = str5;
                                                            str4 = str6;
                                                            attributeCount = i;
                                                        }
                                                        str = str3;
                                                        str2 = str4;
                                                        xMLStreamReader2.skipElement();
                                                        if (nullifiedAttributeValue2 != null && nullifiedAttributeValue != null) {
                                                            if (Intrinsics.areEqual(nullifiedAttributeValue2, "os")) {
                                                                os = readOs(nullifiedAttributeValue);
                                                            } else if (Intrinsics.areEqual(nullifiedAttributeValue2, "overrides")) {
                                                                z2 = Boolean.parseBoolean(nullifiedAttributeValue);
                                                            } else if (map == null) {
                                                                map = Collections.singletonMap(nullifiedAttributeValue2, nullifiedAttributeValue);
                                                            } else {
                                                                Intrinsics.checkNotNull(map);
                                                                map = map.size() == 1 ? new HashMap(map) : map;
                                                                Intrinsics.checkNotNull(map);
                                                                map.put(nullifiedAttributeValue2, nullifiedAttributeValue);
                                                            }
                                                            break;
                                                        }
                                                    case "skipForDefaultProject":
                                                        String value = xMLStreamReader2.getElementText();
                                                        Intrinsics.checkNotNullExpressionValue(value, "value");
                                                        if (value.length() == 0 || !StringsKt.equals(value, "false", true)) {
                                                            componentListToAdd$intellij_platform_core_impl = componentListToAdd$intellij_platform_core_impl;
                                                            str = str3;
                                                            str2 = str4;
                                                        }
                                                        z = z3;
                                                        str = str3;
                                                        str2 = str4;
                                                        break;
                                                    case "implementation-class":
                                                        nullifiedContent2 = getNullifiedContent(xMLStreamReader2);
                                                        str = str3;
                                                        str2 = str4;
                                                        break;
                                                    case "interface-class":
                                                        nullifiedContent = getNullifiedContent(xMLStreamReader2);
                                                        str = str3;
                                                        str2 = str4;
                                                        break;
                                                    case "headless-implementation-class":
                                                        elementText = xMLStreamReader2.getElementText();
                                                        str = str3;
                                                        str2 = str4;
                                                        break;
                                                    case "loadForDefaultProject":
                                                        String value2 = xMLStreamReader2.getElementText();
                                                        Intrinsics.checkNotNullExpressionValue(value2, "value");
                                                        if (value2.length() != 0 && !StringsKt.equals(value2, "true", true)) {
                                                            z3 = false;
                                                        }
                                                        z = z3;
                                                        str = str3;
                                                        str2 = str4;
                                                        break;
                                                    default:
                                                        componentListToAdd$intellij_platform_core_impl = componentListToAdd$intellij_platform_core_impl;
                                                        str = str3;
                                                        str2 = str4;
                                                        xMLStreamReader2.skipElement();
                                                }
                                                xMLStreamReader2.isEndElement();
                                                xMLStreamReader3.isEndElement();
                                                break;
                                            case 2:
                                                xMLStreamReader2.isEndElement();
                                                componentListToAdd$intellij_platform_core_impl.add(new ComponentConfig(nullifiedContent, nullifiedContent2, elementText, z, os, z2, map));
                                                xMLStreamReader2.isEndElement();
                                                componentListToAdd$intellij_platform_core_impl = componentListToAdd$intellij_platform_core_impl;
                                                break;
                                            case 3:
                                            case 4:
                                            case 5:
                                            case 6:
                                                componentListToAdd$intellij_platform_core_impl = componentListToAdd$intellij_platform_core_impl;
                                                str = str3;
                                                str2 = str4;
                                                map = map;
                                                break;
                                            default:
                                                throw new XMLStreamException(str3 + getEventTypeString(xMLStreamReader3.getEventType()), xMLStreamReader3.getLocation());
                                        }
                                    }
                                    componentListToAdd$intellij_platform_core_impl = componentListToAdd$intellij_platform_core_impl;
                                    str3 = str;
                                    str4 = str2;
                                    i2 = 9;
                                    i3 = 12;
                                }
                            } else {
                                componentListToAdd$intellij_platform_core_impl = componentListToAdd$intellij_platform_core_impl;
                                xMLStreamReader2.skipElement();
                            }
                            xMLStreamReader3.isEndElement();
                            continue;
                            componentListToAdd$intellij_platform_core_impl = componentListToAdd$intellij_platform_core_impl;
                            break;
                        case 2:
                            return;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            break;
                        default:
                            throw new XMLStreamException("Unexpected state: " + getEventTypeString(xMLStreamReader3.getEventType()), xMLStreamReader3.getLocation());
                    }
                }
            }
            componentListToAdd$intellij_platform_core_impl = componentListToAdd$intellij_platform_core_impl;
        }
    }

    private static final void readContent(XMLStreamReader2 xMLStreamReader2, RawPluginDescriptor rawPluginDescriptor, ReadModuleContext readModuleContext) throws XMLStreamException {
        ArrayList arrayList = rawPluginDescriptor.contentModules;
        if (arrayList == null) {
            arrayList = new ArrayList();
            rawPluginDescriptor.contentModules = arrayList;
        }
        XMLStreamReader2 xMLStreamReader3 = xMLStreamReader2;
        xMLStreamReader3.isStartElement();
        while (true) {
            int next = xMLStreamReader3.next();
            if (next != 9 && next != 12) {
                switch (next) {
                    case 1:
                        String localName = xMLStreamReader3.getLocalName();
                        Intrinsics.checkNotNullExpressionValue(localName, "localName");
                        if (localName.hashCode() == -1068784020 && localName.equals("module")) {
                            String string = null;
                            int attributeCount = xMLStreamReader2.getAttributeCount();
                            String strName = null;
                            for (int i = 0; i < attributeCount; i++) {
                                String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i);
                                if (attributeLocalName != null && attributeLocalName.hashCode() == 3373707 && attributeLocalName.equals("name")) {
                                    XmlInterner interner = readModuleContext.getInterner();
                                    String attributeValue = xMLStreamReader2.getAttributeValue(i);
                                    Intrinsics.checkNotNullExpressionValue(attributeValue, "reader.getAttributeValue(i)");
                                    strName = interner.name(attributeValue);
                                }
                            }
                            String str = strName;
                            if (str == null || str.length() == 0) {
                                throw new RuntimeException("Name is not specified at " + xMLStreamReader2.getLocation());
                            }
                            int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) str, '/', 0, false, 6, (Object) null);
                            if (iLastIndexOf$default != -1) {
                                StringBuilder sb = new StringBuilder();
                                if (strName == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                                }
                                String strSubstring = strName.substring(0, iLastIndexOf$default);
                                Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                                StringBuilder sbAppend = sb.append(strSubstring).append('.');
                                int i2 = iLastIndexOf$default + 1;
                                if (strName == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                                }
                                String strSubstring2 = strName.substring(i2);
                                Intrinsics.checkNotNullExpressionValue(strSubstring2, "(this as java.lang.String).substring(startIndex)");
                                string = sbAppend.append(strSubstring2).append(".xml").toString();
                            }
                            arrayList.add(new PluginContentDescriptor.ModuleItem(strName, string));
                            xMLStreamReader2.skipElement();
                            xMLStreamReader3.isEndElement();
                        } else {
                            throw new RuntimeException("Unknown content item type: " + localName);
                        }
                        break;
                    case 2:
                        xMLStreamReader2.isEndElement();
                        return;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        break;
                    default:
                        throw new XMLStreamException("Unexpected state: " + getEventTypeString(xMLStreamReader3.getEventType()), xMLStreamReader3.getLocation());
                }
            }
        }
    }

    private static final void readDependencies(XMLStreamReader2 xMLStreamReader2, RawPluginDescriptor rawPluginDescriptor, ReadModuleContext readModuleContext) throws XMLStreamException {
        XMLStreamReader2 xMLStreamReader3 = xMLStreamReader2;
        xMLStreamReader3.isStartElement();
        ArrayList arrayList = null;
        ArrayList arrayList2 = null;
        while (true) {
            int next = xMLStreamReader3.next();
            if (next != 9 && next != 12) {
                switch (next) {
                    case 1:
                        String localName = xMLStreamReader3.getLocalName();
                        Intrinsics.checkNotNullExpressionValue(localName, "localName");
                        int iHashCode = localName.hashCode();
                        int i = 0;
                        if (iHashCode == -1068784020) {
                            if (localName.equals("module")) {
                                int attributeCount = xMLStreamReader2.getAttributeCount();
                                String strName = null;
                                while (i < attributeCount) {
                                    String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i);
                                    if (attributeLocalName != null && attributeLocalName.hashCode() == 3373707 && attributeLocalName.equals("name")) {
                                        XmlInterner interner = readModuleContext.getInterner();
                                        String attributeValue = xMLStreamReader2.getAttributeValue(i);
                                        Intrinsics.checkNotNullExpressionValue(attributeValue, "reader.getAttributeValue(i)");
                                        strName = interner.name(attributeValue);
                                    }
                                    i++;
                                }
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                Intrinsics.checkNotNull(arrayList);
                                Intrinsics.checkNotNull(strName);
                                arrayList.add(new ModuleDependenciesDescriptor.ModuleReference(strName));
                                xMLStreamReader2.skipElement();
                                xMLStreamReader3.isEndElement();
                            } else {
                                throw new RuntimeException("Unknown content item type: " + localName);
                            }
                        } else if (iHashCode == -985174221 && localName.equals("plugin")) {
                            int attributeCount2 = xMLStreamReader2.getAttributeCount();
                            String strName2 = null;
                            while (i < attributeCount2) {
                                String attributeLocalName2 = xMLStreamReader2.getAttributeLocalName(i);
                                if (attributeLocalName2 != null && attributeLocalName2.hashCode() == 3355 && attributeLocalName2.equals("id")) {
                                    XmlInterner interner2 = readModuleContext.getInterner();
                                    String attributeValue2 = xMLStreamReader2.getAttributeValue(i);
                                    Intrinsics.checkNotNullExpressionValue(attributeValue2, "reader.getAttributeValue(i)");
                                    strName2 = interner2.name(attributeValue2);
                                }
                                i++;
                            }
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            Intrinsics.checkNotNull(arrayList2);
                            Intrinsics.checkNotNull(strName2);
                            PluginId id2 = PluginId.getId(strName2);
                            Intrinsics.checkNotNullExpressionValue(id2, "PluginId.getId(id!!)");
                            arrayList2.add(new ModuleDependenciesDescriptor.PluginReference(id2));
                            xMLStreamReader2.skipElement();
                            xMLStreamReader3.isEndElement();
                        } else {
                            throw new RuntimeException("Unknown content item type: " + localName);
                        }
                        break;
                    case 2:
                        if (arrayList == null) {
                            arrayList = Collections.emptyList();
                            Intrinsics.checkNotNullExpressionValue(arrayList, "Collections.emptyList()");
                        }
                        if (arrayList2 == null) {
                            arrayList2 = Collections.emptyList();
                            Intrinsics.checkNotNullExpressionValue(arrayList2, "Collections.emptyList()");
                        }
                        rawPluginDescriptor.dependencies = new ModuleDependenciesDescriptor(arrayList, arrayList2);
                        xMLStreamReader2.isEndElement();
                        return;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        break;
                    default:
                        throw new XMLStreamException("Unexpected state: " + getEventTypeString(xMLStreamReader3.getEventType()), xMLStreamReader3.getLocation());
                }
            }
        }
    }

    private static final String findAttributeValue(XMLStreamReader2 xMLStreamReader2, String str) {
        int attributeCount = xMLStreamReader2.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            if (Intrinsics.areEqual(xMLStreamReader2.getAttributeLocalName(i), str)) {
                return getNullifiedAttributeValue(xMLStreamReader2, i);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getNullifiedContent(XMLStreamReader2 xMLStreamReader2) {
        String it = xMLStreamReader2.getElementText();
        Intrinsics.checkNotNullExpressionValue(it, "it");
        if (it.length() == 0) {
            return null;
        }
        return it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getNullifiedAttributeValue(XMLStreamReader2 xMLStreamReader2, int i) {
        String it = xMLStreamReader2.getAttributeValue(i);
        Intrinsics.checkNotNullExpressionValue(it, "it");
        if (it.length() == 0) {
            return null;
        }
        return it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void readInclude(XMLStreamReader2 xMLStreamReader2, RawPluginDescriptor rawPluginDescriptor, ReadModuleContext readModuleContext, PathResolver pathResolver, DataLoader dataLoader, String str, String str2) throws XMLStreamException {
        IOException iOException = null;
        int attributeCount = xMLStreamReader2.getAttributeCount();
        boolean zLoadXIncludeReference = false;
        String attributeValue = null;
        String nullifiedAttributeValue = null;
        for (int i = 0; i < attributeCount; i++) {
            String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i);
            if (attributeLocalName != null) {
                int iHashCode = attributeLocalName.hashCode();
                if (iHashCode != -1716762939) {
                    if (iHashCode == 3211051 && attributeLocalName.equals("href")) {
                        nullifiedAttributeValue = getNullifiedAttributeValue(xMLStreamReader2, i);
                    }
                } else if (attributeLocalName.equals(XIncludeHandler.XPOINTER)) {
                    attributeValue = xMLStreamReader2.getAttributeValue(i);
                    if (attributeValue != null && attributeValue.length() != 0) {
                        if (!Intrinsics.areEqual(attributeValue, str2)) {
                        }
                    }
                    attributeValue = null;
                }
            }
            throw new RuntimeException("Unknown attribute " + xMLStreamReader2.getAttributeLocalName(i) + " (" + xMLStreamReader2.getLocation() + ')');
        }
        if (attributeValue != null) {
            throw new RuntimeException("Attribute `xpointer` is not supported anymore (xpointer=" + attributeValue + ", location=" + xMLStreamReader2.getLocation() + ')');
        }
        if (nullifiedAttributeValue == null) {
            throw new RuntimeException("Missing `href` attribute (" + xMLStreamReader2.getLocation() + ')');
        }
        XMLStreamReader2 xMLStreamReader3 = xMLStreamReader2;
        xMLStreamReader3.isStartElement();
        boolean z = false;
        while (true) {
            int next = xMLStreamReader3.next();
            if (next != 9 && next != 12) {
                switch (next) {
                    case 1:
                        String localName = xMLStreamReader3.getLocalName();
                        Intrinsics.checkNotNullExpressionValue(localName, "localName");
                        if (Intrinsics.areEqual("fallback", localName)) {
                            xMLStreamReader2.skipElement();
                            xMLStreamReader2.isEndElement();
                            z = true;
                        } else {
                            xMLStreamReader2.skipElement();
                        }
                        xMLStreamReader3.isEndElement();
                        break;
                    case 2:
                        try {
                            zLoadXIncludeReference = pathResolver.loadXIncludeReference(rawPluginDescriptor, readModuleContext, dataLoader, str, nullifiedAttributeValue);
                            break;
                        } catch (IOException e) {
                            iOException = e;
                        }
                        if (zLoadXIncludeReference || z) {
                            return;
                        }
                        if (readModuleContext.getIsMissingIncludeIgnored()) {
                            getLOG().info(nullifiedAttributeValue + " include ignored (dataLoader=" + dataLoader + ')', iOException);
                            return;
                        }
                        throw new RuntimeException("Cannot resolve " + nullifiedAttributeValue + " (dataLoader=" + dataLoader + ')', iOException);
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        break;
                    default:
                        throw new XMLStreamException("Unexpected state: " + getEventTypeString(xMLStreamReader3.getEventType()), xMLStreamReader3.getLocation());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Logger getLOG() {
        Logger logger = PluginManagerCore.getLogger();
        Intrinsics.checkNotNullExpressionValue(logger, "PluginManagerCore.getLogger()");
        return logger;
    }

    private static final LocalDate parseReleaseDate(String str) {
        if (str.length() == 0 || Intrinsics.areEqual(str, "__DATE__")) {
            return null;
        }
        DateTimeFormatter dateTimeFormatterOfPattern = dateTimeFormatter;
        if (dateTimeFormatterOfPattern == null) {
            dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.US);
            dateTimeFormatter = dateTimeFormatterOfPattern;
        }
        try {
            return LocalDate.parse(str, dateTimeFormatterOfPattern);
        } catch (ParseException e) {
            getLOG().error("Cannot parse release date", e);
            return null;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static final void readListeners(XMLStreamReader2 xMLStreamReader2, ContainerDescriptor containerDescriptor) throws XMLStreamException {
        ArrayList arrayList = containerDescriptor.listeners;
        if (arrayList == null) {
            arrayList = new ArrayList();
            containerDescriptor.listeners = arrayList;
        }
        XMLStreamReader2 xMLStreamReader3 = xMLStreamReader2;
        xMLStreamReader3.isStartElement();
        while (true) {
            int next = xMLStreamReader3.next();
            if (next != 9 && next != 12) {
                switch (next) {
                    case 1:
                        String localName = xMLStreamReader3.getLocalName();
                        Intrinsics.checkNotNullExpressionValue(localName, "localName");
                        if (Intrinsics.areEqual(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, localName)) {
                            int attributeCount = xMLStreamReader2.getAttributeCount();
                            ExtensionDescriptor.Os os = null;
                            String nullifiedAttributeValue = null;
                            String nullifiedAttributeValue2 = null;
                            boolean attributeAsBoolean = true;
                            boolean attributeAsBoolean2 = true;
                            for (int i = 0; i < attributeCount; i++) {
                                String attributeLocalName = xMLStreamReader2.getAttributeLocalName(i);
                                if (attributeLocalName != null) {
                                    switch (attributeLocalName.hashCode()) {
                                        case -1516231897:
                                            if (attributeLocalName.equals("activeInHeadlessMode")) {
                                                attributeAsBoolean2 = xMLStreamReader2.getAttributeAsBoolean(i);
                                            }
                                            break;
                                        case -867262144:
                                            if (attributeLocalName.equals("activeInTestMode")) {
                                                attributeAsBoolean = xMLStreamReader2.getAttributeAsBoolean(i);
                                            }
                                            break;
                                        case XKeySym.XK_Thai_saraaimaimalai /* 3556 */:
                                            if (attributeLocalName.equals("os")) {
                                                String attributeValue = xMLStreamReader2.getAttributeValue(i);
                                                Intrinsics.checkNotNullExpressionValue(attributeValue, "reader.getAttributeValue(i)");
                                                os = readOs(attributeValue);
                                            }
                                            break;
                                        case 94742904:
                                            if (attributeLocalName.equals("class")) {
                                                nullifiedAttributeValue = getNullifiedAttributeValue(xMLStreamReader2, i);
                                            }
                                            break;
                                        case 110546223:
                                            if (attributeLocalName.equals("topic")) {
                                                nullifiedAttributeValue2 = getNullifiedAttributeValue(xMLStreamReader2, i);
                                            }
                                            break;
                                    }
                                }
                            }
                            if (nullifiedAttributeValue == null || nullifiedAttributeValue2 == null) {
                                getLOG().error("Listener descriptor is not correct as " + xMLStreamReader2.getLocation());
                            } else {
                                arrayList.add(new ListenerDescriptor(os, nullifiedAttributeValue, nullifiedAttributeValue2, attributeAsBoolean, attributeAsBoolean2));
                            }
                            xMLStreamReader2.skipElement();
                            xMLStreamReader2.isEndElement();
                        } else {
                            xMLStreamReader2.skipElement();
                        }
                        xMLStreamReader3.isEndElement();
                        break;
                    case 2:
                        xMLStreamReader2.isEndElement();
                        return;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        break;
                    default:
                        throw new XMLStreamException("Unexpected state: " + getEventTypeString(xMLStreamReader3.getEventType()), xMLStreamReader3.getLocation());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final ExtensionDescriptor.Os readOs(String str) {
        switch (str.hashCode()) {
            case -603799481:
                if (str.equals("freebsd")) {
                    return ExtensionDescriptor.Os.freebsd;
                }
                break;
            case 107855:
                if (str.equals("mac")) {
                    return ExtensionDescriptor.Os.mac;
                }
                break;
            case 3594632:
                if (str.equals("unix")) {
                    return ExtensionDescriptor.Os.unix;
                }
                break;
            case 102977780:
                if (str.equals("linux")) {
                    return ExtensionDescriptor.Os.linux;
                }
                break;
            case 1349493379:
                if (str.equals("windows")) {
                    return ExtensionDescriptor.Os.windows;
                }
                break;
        }
        throw new IllegalArgumentException("Unknown OS: " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getEventTypeString(int i) {
        switch (i) {
            case 1:
                return "START_ELEMENT";
            case 2:
                return "END_ELEMENT";
            case 3:
                return "PROCESSING_INSTRUCTION";
            case 4:
                return "CHARACTERS";
            case 5:
                return "COMMENT";
            case 6:
                return "SPACE";
            case 7:
                return "START_DOCUMENT";
            case 8:
                return "END_DOCUMENT";
            case 9:
                return "ENTITY_REFERENCE";
            case 10:
                return "ATTRIBUTE";
            case 11:
                return "DTD";
            case 12:
                return "CDATA";
            default:
                return "UNKNOWN_EVENT_TYPE, " + i;
        }
    }
}
