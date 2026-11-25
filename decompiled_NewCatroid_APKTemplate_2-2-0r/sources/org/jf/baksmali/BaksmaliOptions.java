package org.jf.baksmali;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jf.dexlib2.analysis.ClassPath;
import org.jf.dexlib2.analysis.InlineMethodResolver;
import org.jf.dexlib2.util.SyntheticAccessorResolver;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* JADX INFO: loaded from: classes6.dex */
public class BaksmaliOptions {
    public static final int ALL = 1;
    public static final int ALLPOST = 4;
    public static final int ALLPRE = 2;
    public static final int ARGS = 8;
    public static final int DEST = 16;
    public static final int FULLMERGE = 64;
    public static final int MERGE = 32;
    private CommentProvider commentProvider;
    public boolean dumpMarkers;
    public int apiLevel = 15;
    public boolean parameterRegisters = true;
    public boolean localsDirective = false;
    public boolean sequentialLabels = false;
    public boolean debugInfo = true;
    public boolean codeOffsets = false;
    public boolean accessorComments = true;
    public boolean allowOdex = false;
    public boolean deodex = false;
    public boolean implicitReferences = false;
    public boolean normalizeVirtualMethods = false;
    public int registerInfo = 0;
    public Map<Integer, String> resourceIds = new HashMap();
    public boolean autoLineNumbers = false;
    public boolean skipDuplicateLineNumbers = false;
    public InlineMethodResolver inlineResolver = null;
    public ClassPath classPath = null;
    public SyntheticAccessorResolver syntheticAccessorResolver = null;

    public CommentProvider getCommentProvider() {
        CommentProvider commentProvider = this.commentProvider;
        if (commentProvider != null) {
            return commentProvider;
        }
        CommentProvider.CommentMap commentMap = new CommentProvider.CommentMap(this.resourceIds);
        this.commentProvider = commentMap;
        return commentMap;
    }

    public void setCommentProvider(CommentProvider commentProvider) {
        this.commentProvider = commentProvider;
    }

    public void loadResourceIds(Map<String, File> map) throws SAXException, IOException {
        for (Map.Entry<String, File> entry : map.entrySet()) {
            try {
                SAXParserFactory sAXParserFactoryNewInstance = SAXParserFactory.newInstance();
                sAXParserFactoryNewInstance.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                SAXParser sAXParserNewSAXParser = sAXParserFactoryNewInstance.newSAXParser();
                final String key = entry.getKey();
                sAXParserNewSAXParser.parse(entry.getValue(), new DefaultHandler() { // from class: org.jf.baksmali.BaksmaliOptions.1
                    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
                    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
                        if (str3.equals("public")) {
                            String value = attributes.getValue(ModuleXmlParser.TYPE);
                            String strReplace = attributes.getValue("name").replace('.', '_');
                            BaksmaliOptions.this.resourceIds.put(Integer.decode(attributes.getValue("id")), String.format("%s.%s.%s", key, value, strReplace));
                        }
                    }
                });
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
