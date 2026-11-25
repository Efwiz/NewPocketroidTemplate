package org.apache.xerces.impl.xs.traversers;

import io.netty.util.internal.StringUtil;
import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.XMLConstants;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaNamespaceSupport;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAttributeDecl;
import org.apache.xerces.impl.xs.XSGrammarBucket;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XIntPool;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.QName;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
public class XSAttributeChecker {
    public static final int ATTIDX_ABSTRACT = 0;
    public static final int ATTIDX_AFORMDEFAULT = 1;
    public static final int ATTIDX_BASE;
    public static final int ATTIDX_BLOCK;
    public static final int ATTIDX_BLOCKDEFAULT;
    private static int ATTIDX_COUNT = 0;
    public static final int ATTIDX_DEFAULT;
    public static final int ATTIDX_EFORMDEFAULT;
    public static final int ATTIDX_ENUMNSDECLS;
    public static final int ATTIDX_FINAL;
    public static final int ATTIDX_FINALDEFAULT;
    public static final int ATTIDX_FIXED;
    public static final int ATTIDX_FORM;
    public static final int ATTIDX_FROMDEFAULT;
    public static final int ATTIDX_ID;
    public static final int ATTIDX_ISRETURNED;
    public static final int ATTIDX_ITEMTYPE;
    public static final int ATTIDX_MAXOCCURS;
    public static final int ATTIDX_MEMBERTYPES;
    public static final int ATTIDX_MINOCCURS;
    public static final int ATTIDX_MIXED;
    public static final int ATTIDX_NAME;
    public static final int ATTIDX_NAMESPACE;
    public static final int ATTIDX_NAMESPACE_LIST;
    public static final int ATTIDX_NILLABLE;
    public static final int ATTIDX_NONSCHEMA;
    public static final int ATTIDX_PROCESSCONTENTS;
    public static final int ATTIDX_PUBLIC;
    public static final int ATTIDX_REF;
    public static final int ATTIDX_REFER;
    public static final int ATTIDX_SCHEMALOCATION;
    public static final int ATTIDX_SOURCE;
    public static final int ATTIDX_SUBSGROUP;
    public static final int ATTIDX_SYSTEM;
    public static final int ATTIDX_TARGETNAMESPACE;
    public static final int ATTIDX_TYPE;
    public static final int ATTIDX_USE;
    public static final int ATTIDX_VALUE;
    public static final int ATTIDX_VERSION;
    public static final int ATTIDX_XML_LANG;
    public static final int ATTIDX_XPATH;
    private static final String ATTRIBUTE_N = "attribute_n";
    private static final String ATTRIBUTE_R = "attribute_r";
    protected static final int DT_ANYURI = 0;
    protected static final int DT_BLOCK = -1;
    protected static final int DT_BLOCK1 = -2;
    protected static final int DT_BOOLEAN = -15;
    protected static final int DT_COUNT = 9;
    protected static final int DT_FINAL = -3;
    protected static final int DT_FINAL1 = -4;
    protected static final int DT_FINAL2 = -5;
    protected static final int DT_FORM = -6;
    protected static final int DT_ID = 1;
    protected static final int DT_LANGUAGE = 8;
    protected static final int DT_MAXOCCURS = -7;
    protected static final int DT_MAXOCCURS1 = -8;
    protected static final int DT_MEMBERTYPES = -9;
    protected static final int DT_MINOCCURS1 = -10;
    protected static final int DT_NAMESPACE = -11;
    protected static final int DT_NCNAME = 5;
    protected static final int DT_NONNEGINT = -16;
    protected static final int DT_POSINT = -17;
    protected static final int DT_PROCESSCONTENTS = -12;
    protected static final int DT_QNAME = 2;
    protected static final int DT_STRING = 3;
    protected static final int DT_TOKEN = 4;
    protected static final int DT_USE = -13;
    protected static final int DT_WHITESPACE = -14;
    protected static final int DT_XPATH = 6;
    protected static final int DT_XPATH1 = 7;
    private static final String ELEMENT_N = "element_n";
    private static final String ELEMENT_R = "element_r";
    static final int INC_POOL_SIZE = 10;
    static final int INIT_POOL_SIZE = 10;
    private static final XInt INT_ANY_ANY;
    private static final XInt INT_ANY_LAX;
    private static final XInt INT_ANY_LIST;
    private static final XInt INT_ANY_NOT;
    private static final XInt INT_ANY_SKIP;
    private static final XInt INT_ANY_STRICT;
    private static final XInt INT_EMPTY_SET;
    private static final XInt INT_QUALIFIED;
    private static final XInt INT_UNBOUNDED;
    private static final XInt INT_UNQUALIFIED;
    private static final XInt INT_USE_OPTIONAL;
    private static final XInt INT_USE_PROHIBITED;
    private static final XInt INT_USE_REQUIRED;
    private static final XInt INT_WS_COLLAPSE;
    private static final XInt INT_WS_PRESERVE;
    private static final XInt INT_WS_REPLACE;
    private static final Hashtable fEleAttrsMapG;
    private static final Hashtable fEleAttrsMapL;
    private static final XSSimpleType[] fExtraDVs;
    private static boolean[] fSeenTemp;
    private static Object[] fTempArray;
    private static final XIntPool fXIntPool;
    Object[][] fArrayPool;
    int fPoolPos;
    protected XSDHandler fSchemaHandler;
    protected boolean[] fSeen;
    protected SymbolTable fSymbolTable = null;
    protected Hashtable fNonSchemaAttrs = new Hashtable();
    protected Vector fNamespaceList = new Vector();

    static {
        int i = 1 + 1;
        int i2 = i + 1;
        ATTIDX_BASE = i;
        int i3 = i + 2;
        ATTIDX_BLOCK = i2;
        int i4 = i + 3;
        ATTIDX_BLOCKDEFAULT = i3;
        int i5 = i + 4;
        ATTIDX_DEFAULT = i4;
        int i6 = i + 5;
        ATTIDX_EFORMDEFAULT = i5;
        int i7 = i + 6;
        ATTIDX_FINAL = i6;
        int i8 = i + 7;
        ATTIDX_FINALDEFAULT = i7;
        int i9 = i + 8;
        ATTIDX_FIXED = i8;
        int i10 = i + 9;
        ATTIDX_FORM = i9;
        int i11 = i + 10;
        ATTIDX_ID = i10;
        int i12 = i + 11;
        ATTIDX_ITEMTYPE = i11;
        int i13 = i + 12;
        ATTIDX_MAXOCCURS = i12;
        int i14 = i + 13;
        ATTIDX_MEMBERTYPES = i13;
        int i15 = i + 14;
        ATTIDX_MINOCCURS = i14;
        int i16 = i + 15;
        ATTIDX_MIXED = i15;
        int i17 = i + 16;
        ATTIDX_NAME = i16;
        ATTIDX_NAMESPACE = i17;
        int i18 = i + 18;
        ATTIDX_NAMESPACE_LIST = i + 17;
        ATTIDX_NILLABLE = i18;
        int i19 = i + 20;
        ATTIDX_NONSCHEMA = i + 19;
        int i20 = i + 21;
        ATTIDX_PROCESSCONTENTS = i19;
        int i21 = i + 22;
        ATTIDX_PUBLIC = i20;
        int i22 = i + 23;
        ATTIDX_REF = i21;
        int i23 = i + 24;
        ATTIDX_REFER = i22;
        int i24 = i + 25;
        ATTIDX_SCHEMALOCATION = i23;
        int i25 = i + 26;
        ATTIDX_SOURCE = i24;
        int i26 = i + 27;
        ATTIDX_SUBSGROUP = i25;
        int i27 = i + 28;
        ATTIDX_SYSTEM = i26;
        int i28 = i + 29;
        ATTIDX_TARGETNAMESPACE = i27;
        int i29 = i + 30;
        ATTIDX_TYPE = i28;
        int i30 = i + 31;
        ATTIDX_USE = i29;
        ATTIDX_VALUE = i30;
        int i31 = i + 33;
        ATTIDX_ENUMNSDECLS = i + 32;
        int i32 = i + 34;
        ATTIDX_VERSION = i31;
        int i33 = i + 35;
        ATTIDX_XML_LANG = i32;
        ATTIDX_XPATH = i33;
        ATTIDX_FROMDEFAULT = i + 36;
        ATTIDX_COUNT = i + 38;
        ATTIDX_ISRETURNED = i + 37;
        XIntPool xIntPool = new XIntPool();
        fXIntPool = xIntPool;
        INT_QUALIFIED = xIntPool.getXInt(1);
        XInt xInt = xIntPool.getXInt(0);
        INT_UNQUALIFIED = xInt;
        XInt xInt2 = xIntPool.getXInt(0);
        INT_EMPTY_SET = xInt2;
        XInt xInt3 = xIntPool.getXInt(1);
        INT_ANY_STRICT = xInt3;
        INT_ANY_LAX = xIntPool.getXInt(3);
        INT_ANY_SKIP = xIntPool.getXInt(2);
        XInt xInt4 = xIntPool.getXInt(1);
        INT_ANY_ANY = xInt4;
        INT_ANY_LIST = xIntPool.getXInt(3);
        INT_ANY_NOT = xIntPool.getXInt(2);
        XInt xInt5 = xIntPool.getXInt(0);
        INT_USE_OPTIONAL = xInt5;
        INT_USE_REQUIRED = xIntPool.getXInt(1);
        INT_USE_PROHIBITED = xIntPool.getXInt(2);
        INT_WS_PRESERVE = xIntPool.getXInt(0);
        INT_WS_REPLACE = xIntPool.getXInt(1);
        INT_WS_COLLAPSE = xIntPool.getXInt(2);
        INT_UNBOUNDED = xIntPool.getXInt(-1);
        Hashtable hashtable = new Hashtable(29);
        fEleAttrsMapG = hashtable;
        Hashtable hashtable2 = new Hashtable(79);
        fEleAttrsMapL = hashtable2;
        XSSimpleType[] xSSimpleTypeArr = new XSSimpleType[9];
        fExtraDVs = xSSimpleTypeArr;
        SchemaGrammar.BuiltinSchemaGrammar builtinSchemaGrammar = SchemaGrammar.SG_SchemaNS;
        xSSimpleTypeArr[0] = (XSSimpleType) builtinSchemaGrammar.getGlobalTypeDecl(SchemaSymbols.ATTVAL_ANYURI);
        xSSimpleTypeArr[1] = (XSSimpleType) builtinSchemaGrammar.getGlobalTypeDecl(SchemaSymbols.ATTVAL_ID);
        xSSimpleTypeArr[2] = (XSSimpleType) builtinSchemaGrammar.getGlobalTypeDecl(SchemaSymbols.ATTVAL_QNAME);
        xSSimpleTypeArr[3] = (XSSimpleType) builtinSchemaGrammar.getGlobalTypeDecl("string");
        xSSimpleTypeArr[4] = (XSSimpleType) builtinSchemaGrammar.getGlobalTypeDecl("token");
        xSSimpleTypeArr[5] = (XSSimpleType) builtinSchemaGrammar.getGlobalTypeDecl(SchemaSymbols.ATTVAL_NCNAME);
        XSSimpleType xSSimpleType = xSSimpleTypeArr[3];
        xSSimpleTypeArr[6] = xSSimpleType;
        xSSimpleTypeArr[6] = xSSimpleType;
        xSSimpleTypeArr[8] = (XSSimpleType) builtinSchemaGrammar.getGlobalTypeDecl("language");
        OneAttr[] oneAttrArr = {new OneAttr(SchemaSymbols.ATT_ABSTRACT, DT_BOOLEAN, 0, Boolean.FALSE), new OneAttr(SchemaSymbols.ATT_ATTRIBUTEFORMDEFAULT, -6, 1, xInt), new OneAttr(SchemaSymbols.ATT_BASE, 2, i, null), new OneAttr(SchemaSymbols.ATT_BASE, 2, i, null), new OneAttr(SchemaSymbols.ATT_BLOCK, -1, i2, null), new OneAttr(SchemaSymbols.ATT_BLOCK, -2, i2, null), new OneAttr(SchemaSymbols.ATT_BLOCKDEFAULT, -1, i3, xInt2), new OneAttr(SchemaSymbols.ATT_DEFAULT, 3, i4, null), new OneAttr(SchemaSymbols.ATT_ELEMENTFORMDEFAULT, -6, i5, xInt), new OneAttr(SchemaSymbols.ATT_FINAL, -3, i6, null), new OneAttr(SchemaSymbols.ATT_FINAL, -4, i6, null), new OneAttr(SchemaSymbols.ATT_FINALDEFAULT, -5, i7, xInt2), new OneAttr(SchemaSymbols.ATT_FIXED, 3, i8, null), new OneAttr(SchemaSymbols.ATT_FIXED, DT_BOOLEAN, i8, Boolean.FALSE), new OneAttr(SchemaSymbols.ATT_FORM, -6, i9, null), new OneAttr(SchemaSymbols.ATT_ID, 1, i10, null), new OneAttr(SchemaSymbols.ATT_ITEMTYPE, 2, i11, null), new OneAttr(SchemaSymbols.ATT_MAXOCCURS, -7, i12, xIntPool.getXInt(1)), new OneAttr(SchemaSymbols.ATT_MAXOCCURS, -8, i12, xIntPool.getXInt(1)), new OneAttr(SchemaSymbols.ATT_MEMBERTYPES, -9, i13, null), new OneAttr(SchemaSymbols.ATT_MINOCCURS, DT_NONNEGINT, i14, xIntPool.getXInt(1)), new OneAttr(SchemaSymbols.ATT_MINOCCURS, -10, i14, xIntPool.getXInt(1)), new OneAttr(SchemaSymbols.ATT_MIXED, DT_BOOLEAN, i15, Boolean.FALSE), new OneAttr(SchemaSymbols.ATT_MIXED, DT_BOOLEAN, i15, null), new OneAttr(SchemaSymbols.ATT_NAME, 5, i16, null), new OneAttr(SchemaSymbols.ATT_NAMESPACE, -11, i17, xInt4), new OneAttr(SchemaSymbols.ATT_NAMESPACE, 0, i17, null), new OneAttr(SchemaSymbols.ATT_NILLABLE, DT_BOOLEAN, i18, Boolean.FALSE), new OneAttr(SchemaSymbols.ATT_PROCESSCONTENTS, DT_PROCESSCONTENTS, i19, xInt3), new OneAttr(SchemaSymbols.ATT_PUBLIC, 4, i20, null), new OneAttr(SchemaSymbols.ATT_REF, 2, i21, null), new OneAttr(SchemaSymbols.ATT_REFER, 2, i22, null), new OneAttr(SchemaSymbols.ATT_SCHEMALOCATION, 0, i23, null), new OneAttr(SchemaSymbols.ATT_SCHEMALOCATION, 0, i23, null), new OneAttr(SchemaSymbols.ATT_SOURCE, 0, i24, null), new OneAttr(SchemaSymbols.ATT_SUBSTITUTIONGROUP, 2, i25, null), new OneAttr(SchemaSymbols.ATT_SYSTEM, 0, i26, null), new OneAttr(SchemaSymbols.ATT_TARGETNAMESPACE, 0, i27, null), new OneAttr(SchemaSymbols.ATT_TYPE, 2, i28, null), new OneAttr(SchemaSymbols.ATT_USE, DT_USE, i29, xInt5), new OneAttr(SchemaSymbols.ATT_VALUE, DT_NONNEGINT, i30, null), new OneAttr(SchemaSymbols.ATT_VALUE, DT_POSINT, i30, null), new OneAttr(SchemaSymbols.ATT_VALUE, 3, i30, null), new OneAttr(SchemaSymbols.ATT_VALUE, DT_WHITESPACE, i30, null), new OneAttr(SchemaSymbols.ATT_VERSION, 4, i31, null), new OneAttr(SchemaSymbols.ATT_XML_LANG, 8, i32, null), new OneAttr(SchemaSymbols.ATT_XPATH, 6, i33, null), new OneAttr(SchemaSymbols.ATT_XPATH, 7, i33, null)};
        Container container = Container.getContainer(5);
        container.put(SchemaSymbols.ATT_DEFAULT, oneAttrArr[7]);
        container.put(SchemaSymbols.ATT_FIXED, oneAttrArr[12]);
        container.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container.put(SchemaSymbols.ATT_NAME, oneAttrArr[24]);
        container.put(SchemaSymbols.ATT_TYPE, oneAttrArr[38]);
        hashtable.put(SchemaSymbols.ELT_ATTRIBUTE, container);
        Container container2 = Container.getContainer(7);
        container2.put(SchemaSymbols.ATT_DEFAULT, oneAttrArr[7]);
        container2.put(SchemaSymbols.ATT_FIXED, oneAttrArr[12]);
        container2.put(SchemaSymbols.ATT_FORM, oneAttrArr[14]);
        container2.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container2.put(SchemaSymbols.ATT_NAME, oneAttrArr[24]);
        container2.put(SchemaSymbols.ATT_TYPE, oneAttrArr[38]);
        container2.put(SchemaSymbols.ATT_USE, oneAttrArr[39]);
        hashtable2.put(ATTRIBUTE_N, container2);
        Container container3 = Container.getContainer(5);
        container3.put(SchemaSymbols.ATT_DEFAULT, oneAttrArr[7]);
        container3.put(SchemaSymbols.ATT_FIXED, oneAttrArr[12]);
        container3.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container3.put(SchemaSymbols.ATT_REF, oneAttrArr[30]);
        container3.put(SchemaSymbols.ATT_USE, oneAttrArr[39]);
        hashtable2.put(ATTRIBUTE_R, container3);
        Container container4 = Container.getContainer(10);
        container4.put(SchemaSymbols.ATT_ABSTRACT, oneAttrArr[0]);
        container4.put(SchemaSymbols.ATT_BLOCK, oneAttrArr[4]);
        container4.put(SchemaSymbols.ATT_DEFAULT, oneAttrArr[7]);
        container4.put(SchemaSymbols.ATT_FINAL, oneAttrArr[9]);
        container4.put(SchemaSymbols.ATT_FIXED, oneAttrArr[12]);
        container4.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container4.put(SchemaSymbols.ATT_NAME, oneAttrArr[24]);
        container4.put(SchemaSymbols.ATT_NILLABLE, oneAttrArr[27]);
        container4.put(SchemaSymbols.ATT_SUBSTITUTIONGROUP, oneAttrArr[35]);
        container4.put(SchemaSymbols.ATT_TYPE, oneAttrArr[38]);
        hashtable.put(SchemaSymbols.ELT_ELEMENT, container4);
        Container container5 = Container.getContainer(10);
        container5.put(SchemaSymbols.ATT_BLOCK, oneAttrArr[4]);
        container5.put(SchemaSymbols.ATT_DEFAULT, oneAttrArr[7]);
        container5.put(SchemaSymbols.ATT_FIXED, oneAttrArr[12]);
        container5.put(SchemaSymbols.ATT_FORM, oneAttrArr[14]);
        container5.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container5.put(SchemaSymbols.ATT_MAXOCCURS, oneAttrArr[17]);
        container5.put(SchemaSymbols.ATT_MINOCCURS, oneAttrArr[20]);
        container5.put(SchemaSymbols.ATT_NAME, oneAttrArr[24]);
        container5.put(SchemaSymbols.ATT_NILLABLE, oneAttrArr[27]);
        container5.put(SchemaSymbols.ATT_TYPE, oneAttrArr[38]);
        hashtable2.put(ELEMENT_N, container5);
        Container container6 = Container.getContainer(4);
        container6.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container6.put(SchemaSymbols.ATT_MAXOCCURS, oneAttrArr[17]);
        container6.put(SchemaSymbols.ATT_MINOCCURS, oneAttrArr[20]);
        container6.put(SchemaSymbols.ATT_REF, oneAttrArr[30]);
        hashtable2.put(ELEMENT_R, container6);
        Container container7 = Container.getContainer(6);
        container7.put(SchemaSymbols.ATT_ABSTRACT, oneAttrArr[0]);
        container7.put(SchemaSymbols.ATT_BLOCK, oneAttrArr[5]);
        container7.put(SchemaSymbols.ATT_FINAL, oneAttrArr[9]);
        container7.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container7.put(SchemaSymbols.ATT_MIXED, oneAttrArr[22]);
        container7.put(SchemaSymbols.ATT_NAME, oneAttrArr[24]);
        hashtable.put(SchemaSymbols.ELT_COMPLEXTYPE, container7);
        Container container8 = Container.getContainer(4);
        container8.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container8.put(SchemaSymbols.ATT_NAME, oneAttrArr[24]);
        container8.put(SchemaSymbols.ATT_PUBLIC, oneAttrArr[29]);
        container8.put(SchemaSymbols.ATT_SYSTEM, oneAttrArr[36]);
        hashtable.put(SchemaSymbols.ELT_NOTATION, container8);
        Container container9 = Container.getContainer(2);
        container9.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container9.put(SchemaSymbols.ATT_MIXED, oneAttrArr[22]);
        hashtable2.put(SchemaSymbols.ELT_COMPLEXTYPE, container9);
        Container container10 = Container.getContainer(1);
        container10.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        hashtable2.put(SchemaSymbols.ELT_SIMPLECONTENT, container10);
        Container container11 = Container.getContainer(2);
        container11.put(SchemaSymbols.ATT_BASE, oneAttrArr[3]);
        container11.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        hashtable2.put(SchemaSymbols.ELT_RESTRICTION, container11);
        Container container12 = Container.getContainer(2);
        container12.put(SchemaSymbols.ATT_BASE, oneAttrArr[2]);
        container12.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        hashtable2.put(SchemaSymbols.ELT_EXTENSION, container12);
        Container container13 = Container.getContainer(2);
        container13.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container13.put(SchemaSymbols.ATT_REF, oneAttrArr[30]);
        hashtable2.put(SchemaSymbols.ELT_ATTRIBUTEGROUP, container13);
        Container container14 = Container.getContainer(3);
        container14.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container14.put(SchemaSymbols.ATT_NAMESPACE, oneAttrArr[25]);
        container14.put(SchemaSymbols.ATT_PROCESSCONTENTS, oneAttrArr[28]);
        hashtable2.put(SchemaSymbols.ELT_ANYATTRIBUTE, container14);
        Container container15 = Container.getContainer(2);
        container15.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container15.put(SchemaSymbols.ATT_MIXED, oneAttrArr[23]);
        hashtable2.put(SchemaSymbols.ELT_COMPLEXCONTENT, container15);
        Container container16 = Container.getContainer(2);
        container16.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container16.put(SchemaSymbols.ATT_NAME, oneAttrArr[24]);
        hashtable.put(SchemaSymbols.ELT_ATTRIBUTEGROUP, container16);
        Container container17 = Container.getContainer(2);
        container17.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container17.put(SchemaSymbols.ATT_NAME, oneAttrArr[24]);
        hashtable.put(SchemaSymbols.ELT_GROUP, container17);
        Container container18 = Container.getContainer(4);
        container18.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container18.put(SchemaSymbols.ATT_MAXOCCURS, oneAttrArr[17]);
        container18.put(SchemaSymbols.ATT_MINOCCURS, oneAttrArr[20]);
        container18.put(SchemaSymbols.ATT_REF, oneAttrArr[30]);
        hashtable2.put(SchemaSymbols.ELT_GROUP, container18);
        Container container19 = Container.getContainer(3);
        container19.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container19.put(SchemaSymbols.ATT_MAXOCCURS, oneAttrArr[18]);
        container19.put(SchemaSymbols.ATT_MINOCCURS, oneAttrArr[21]);
        hashtable2.put(SchemaSymbols.ELT_ALL, container19);
        Container container20 = Container.getContainer(3);
        container20.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container20.put(SchemaSymbols.ATT_MAXOCCURS, oneAttrArr[17]);
        container20.put(SchemaSymbols.ATT_MINOCCURS, oneAttrArr[20]);
        hashtable2.put(SchemaSymbols.ELT_CHOICE, container20);
        hashtable2.put(SchemaSymbols.ELT_SEQUENCE, container20);
        Container container21 = Container.getContainer(5);
        container21.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container21.put(SchemaSymbols.ATT_MAXOCCURS, oneAttrArr[17]);
        container21.put(SchemaSymbols.ATT_MINOCCURS, oneAttrArr[20]);
        container21.put(SchemaSymbols.ATT_NAMESPACE, oneAttrArr[25]);
        container21.put(SchemaSymbols.ATT_PROCESSCONTENTS, oneAttrArr[28]);
        hashtable2.put(SchemaSymbols.ELT_ANY, container21);
        Container container22 = Container.getContainer(2);
        container22.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container22.put(SchemaSymbols.ATT_NAME, oneAttrArr[24]);
        hashtable2.put(SchemaSymbols.ELT_UNIQUE, container22);
        hashtable2.put(SchemaSymbols.ELT_KEY, container22);
        Container container23 = Container.getContainer(3);
        container23.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container23.put(SchemaSymbols.ATT_NAME, oneAttrArr[24]);
        container23.put(SchemaSymbols.ATT_REFER, oneAttrArr[31]);
        hashtable2.put(SchemaSymbols.ELT_KEYREF, container23);
        Container container24 = Container.getContainer(2);
        container24.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container24.put(SchemaSymbols.ATT_XPATH, oneAttrArr[46]);
        hashtable2.put(SchemaSymbols.ELT_SELECTOR, container24);
        Container container25 = Container.getContainer(2);
        container25.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container25.put(SchemaSymbols.ATT_XPATH, oneAttrArr[47]);
        hashtable2.put(SchemaSymbols.ELT_FIELD, container25);
        Container container26 = Container.getContainer(1);
        container26.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        hashtable.put(SchemaSymbols.ELT_ANNOTATION, container26);
        hashtable2.put(SchemaSymbols.ELT_ANNOTATION, container26);
        Container container27 = Container.getContainer(1);
        container27.put(SchemaSymbols.ATT_SOURCE, oneAttrArr[34]);
        hashtable.put(SchemaSymbols.ELT_APPINFO, container27);
        hashtable2.put(SchemaSymbols.ELT_APPINFO, container27);
        Container container28 = Container.getContainer(2);
        container28.put(SchemaSymbols.ATT_SOURCE, oneAttrArr[34]);
        container28.put(SchemaSymbols.ATT_XML_LANG, oneAttrArr[45]);
        hashtable.put(SchemaSymbols.ELT_DOCUMENTATION, container28);
        hashtable2.put(SchemaSymbols.ELT_DOCUMENTATION, container28);
        Container container29 = Container.getContainer(3);
        container29.put(SchemaSymbols.ATT_FINAL, oneAttrArr[10]);
        container29.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container29.put(SchemaSymbols.ATT_NAME, oneAttrArr[24]);
        hashtable.put(SchemaSymbols.ELT_SIMPLETYPE, container29);
        Container container30 = Container.getContainer(2);
        container30.put(SchemaSymbols.ATT_FINAL, oneAttrArr[10]);
        container30.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        hashtable2.put(SchemaSymbols.ELT_SIMPLETYPE, container30);
        Container container31 = Container.getContainer(2);
        container31.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container31.put(SchemaSymbols.ATT_ITEMTYPE, oneAttrArr[16]);
        hashtable2.put(SchemaSymbols.ELT_LIST, container31);
        Container container32 = Container.getContainer(2);
        container32.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container32.put(SchemaSymbols.ATT_MEMBERTYPES, oneAttrArr[19]);
        hashtable2.put(SchemaSymbols.ELT_UNION, container32);
        Container container33 = Container.getContainer(8);
        container33.put(SchemaSymbols.ATT_ATTRIBUTEFORMDEFAULT, oneAttrArr[1]);
        container33.put(SchemaSymbols.ATT_BLOCKDEFAULT, oneAttrArr[6]);
        container33.put(SchemaSymbols.ATT_ELEMENTFORMDEFAULT, oneAttrArr[8]);
        container33.put(SchemaSymbols.ATT_FINALDEFAULT, oneAttrArr[11]);
        container33.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container33.put(SchemaSymbols.ATT_TARGETNAMESPACE, oneAttrArr[37]);
        container33.put(SchemaSymbols.ATT_VERSION, oneAttrArr[44]);
        container33.put(SchemaSymbols.ATT_XML_LANG, oneAttrArr[45]);
        hashtable.put(SchemaSymbols.ELT_SCHEMA, container33);
        Container container34 = Container.getContainer(2);
        container34.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container34.put(SchemaSymbols.ATT_SCHEMALOCATION, oneAttrArr[32]);
        hashtable.put(SchemaSymbols.ELT_INCLUDE, container34);
        hashtable.put(SchemaSymbols.ELT_REDEFINE, container34);
        Container container35 = Container.getContainer(3);
        container35.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container35.put(SchemaSymbols.ATT_NAMESPACE, oneAttrArr[26]);
        container35.put(SchemaSymbols.ATT_SCHEMALOCATION, oneAttrArr[33]);
        hashtable.put(SchemaSymbols.ELT_IMPORT, container35);
        Container container36 = Container.getContainer(3);
        container36.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container36.put(SchemaSymbols.ATT_VALUE, oneAttrArr[40]);
        container36.put(SchemaSymbols.ATT_FIXED, oneAttrArr[13]);
        hashtable2.put(SchemaSymbols.ELT_LENGTH, container36);
        hashtable2.put(SchemaSymbols.ELT_MINLENGTH, container36);
        hashtable2.put(SchemaSymbols.ELT_MAXLENGTH, container36);
        hashtable2.put(SchemaSymbols.ELT_FRACTIONDIGITS, container36);
        Container container37 = Container.getContainer(3);
        container37.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container37.put(SchemaSymbols.ATT_VALUE, oneAttrArr[41]);
        container37.put(SchemaSymbols.ATT_FIXED, oneAttrArr[13]);
        hashtable2.put(SchemaSymbols.ELT_TOTALDIGITS, container37);
        Container container38 = Container.getContainer(2);
        container38.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container38.put(SchemaSymbols.ATT_VALUE, oneAttrArr[42]);
        hashtable2.put(SchemaSymbols.ELT_PATTERN, container38);
        Container container39 = Container.getContainer(2);
        container39.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container39.put(SchemaSymbols.ATT_VALUE, oneAttrArr[42]);
        hashtable2.put(SchemaSymbols.ELT_ENUMERATION, container39);
        Container container40 = Container.getContainer(3);
        container40.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container40.put(SchemaSymbols.ATT_VALUE, oneAttrArr[43]);
        container40.put(SchemaSymbols.ATT_FIXED, oneAttrArr[13]);
        hashtable2.put(SchemaSymbols.ELT_WHITESPACE, container40);
        Container container41 = Container.getContainer(3);
        container41.put(SchemaSymbols.ATT_ID, oneAttrArr[15]);
        container41.put(SchemaSymbols.ATT_VALUE, oneAttrArr[42]);
        container41.put(SchemaSymbols.ATT_FIXED, oneAttrArr[13]);
        hashtable2.put(SchemaSymbols.ELT_MAXINCLUSIVE, container41);
        hashtable2.put(SchemaSymbols.ELT_MAXEXCLUSIVE, container41);
        hashtable2.put(SchemaSymbols.ELT_MININCLUSIVE, container41);
        hashtable2.put(SchemaSymbols.ELT_MINEXCLUSIVE, container41);
        int i34 = ATTIDX_COUNT;
        fSeenTemp = new boolean[i34];
        fTempArray = new Object[i34];
    }

    public XSAttributeChecker(XSDHandler xSDHandler) {
        this.fSchemaHandler = null;
        int i = ATTIDX_COUNT;
        this.fSeen = new boolean[i];
        this.fArrayPool = (Object[][]) Array.newInstance((Class<?>) Object.class, 10, i);
        this.fPoolPos = 0;
        this.fSchemaHandler = xSDHandler;
    }

    public static String normalize(String str, short s) {
        int i;
        int i2;
        char cCharAt;
        int length = str == null ? 0 : str.length();
        if (length == 0 || s == 0) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (s == 1) {
            for (int i3 = 0; i3 < length; i3++) {
                char cCharAt2 = str.charAt(i3);
                if (cCharAt2 == '\t' || cCharAt2 == '\n' || cCharAt2 == '\r') {
                    stringBuffer.append(StringUtil.SPACE);
                } else {
                    stringBuffer.append(cCharAt2);
                }
            }
        } else {
            int i4 = 0;
            boolean z = true;
            while (i4 < length) {
                char cCharAt3 = str.charAt(i4);
                if (cCharAt3 == '\t' || cCharAt3 == '\n' || cCharAt3 == '\r' || cCharAt3 == ' ') {
                    while (true) {
                        i = length - 1;
                        if (i4 >= i || !((cCharAt = str.charAt((i2 = i4 + 1))) == '\t' || cCharAt == '\n' || cCharAt == '\r' || cCharAt == ' ')) {
                            break;
                        }
                        i4 = i2;
                    }
                    if (i4 < i && !z) {
                        stringBuffer.append(StringUtil.SPACE);
                    }
                } else {
                    stringBuffer.append(cCharAt3);
                    z = false;
                }
                i4++;
            }
        }
        return stringBuffer.toString();
    }

    /* JADX WARN: Code duplicated, block: B:278:0x0269 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:280:0x0231 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:58:0x00e8  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v45, types: [org.apache.xerces.impl.xs.util.XInt] */
    /* JADX WARN: Type inference failed for: r0v47, types: [org.apache.xerces.impl.xs.util.XInt] */
    /* JADX WARN: Type inference failed for: r0v52 */
    /* JADX WARN: Type inference failed for: r0v53, types: [java.util.Vector] */
    /* JADX WARN: Type inference failed for: r2v28, types: [java.lang.Object] */
    private Object validate(Object[] objArr, String str, String str2, int i, XSDocumentInfo xSDocumentInfo) throws InvalidDatatypeValueException {
        ?? vector;
        String strAddSymbol;
        XIntPool xIntPool;
        int i2;
        if (str2 == null) {
            return null;
        }
        String strTrim = XMLChar.trim(str2);
        int i3 = 0;
        switch (i) {
            case DT_POSINT /* -17 */:
                try {
                    if (strTrim.length() > 0 && strTrim.charAt(0) == '+') {
                        strTrim = strTrim.substring(1);
                    }
                    XInt xInt = fXIntPool.getXInt(Integer.parseInt(strTrim));
                    if (xInt.intValue() > 0) {
                        return xInt;
                    }
                    throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{strTrim, SchemaSymbols.ATTVAL_POSITIVEINTEGER});
                } catch (NumberFormatException unused) {
                    throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{strTrim, SchemaSymbols.ATTVAL_POSITIVEINTEGER});
                }
            case DT_NONNEGINT /* -16 */:
                try {
                    if (strTrim.length() > 0 && strTrim.charAt(0) == '+') {
                        strTrim = strTrim.substring(1);
                    }
                    XInt xInt2 = fXIntPool.getXInt(Integer.parseInt(strTrim));
                    if (xInt2.intValue() >= 0) {
                        return xInt2;
                    }
                    throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{strTrim, SchemaSymbols.ATTVAL_NONNEGATIVEINTEGER});
                } catch (NumberFormatException unused2) {
                    throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{strTrim, SchemaSymbols.ATTVAL_NONNEGATIVEINTEGER});
                }
            case DT_BOOLEAN /* -15 */:
                if (strTrim.equals("false") || strTrim.equals("0")) {
                    return Boolean.FALSE;
                }
                if (strTrim.equals("true") || strTrim.equals(SchemaSymbols.ATTVAL_TRUE_1)) {
                    return Boolean.TRUE;
                }
                throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{strTrim, "boolean"});
            case DT_WHITESPACE /* -14 */:
                if (strTrim.equals("preserve")) {
                    return INT_WS_PRESERVE;
                }
                if (strTrim.equals(SchemaSymbols.ATTVAL_REPLACE)) {
                    return INT_WS_REPLACE;
                }
                if (strTrim.equals(SchemaSymbols.ATTVAL_COLLAPSE)) {
                    return INT_WS_COLLAPSE;
                }
                throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{strTrim, "(preserve | replace | collapse)"});
            case DT_USE /* -13 */:
                if (strTrim.equals(SchemaSymbols.ATTVAL_OPTIONAL)) {
                    return INT_USE_OPTIONAL;
                }
                if (strTrim.equals("required")) {
                    return INT_USE_REQUIRED;
                }
                if (strTrim.equals(SchemaSymbols.ATTVAL_PROHIBITED)) {
                    return INT_USE_PROHIBITED;
                }
                throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{strTrim, "(optional | prohibited | required)"});
            case DT_PROCESSCONTENTS /* -12 */:
                if (strTrim.equals(SchemaSymbols.ATTVAL_STRICT)) {
                    return INT_ANY_STRICT;
                }
                if (strTrim.equals(SchemaSymbols.ATTVAL_LAX)) {
                    return INT_ANY_LAX;
                }
                if (strTrim.equals(SchemaSymbols.ATTVAL_SKIP)) {
                    return INT_ANY_SKIP;
                }
                throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{strTrim, "(lax | skip | strict)"});
            case -11:
                if (strTrim.equals(SchemaSymbols.ATTVAL_TWOPOUNDANY)) {
                    return INT_ANY_ANY;
                }
                if (strTrim.equals(SchemaSymbols.ATTVAL_TWOPOUNDOTHER)) {
                    vector = INT_ANY_NOT;
                    objArr[ATTIDX_NAMESPACE_LIST] = new String[]{xSDocumentInfo.fTargetNamespace, null};
                } else {
                    vector = INT_ANY_LIST;
                    this.fNamespaceList.removeAllElements();
                    StringTokenizer stringTokenizer = new StringTokenizer(strTrim, " \n\t\r");
                    while (stringTokenizer.hasMoreTokens()) {
                        try {
                            String strNextToken = stringTokenizer.nextToken();
                            if (strNextToken.equals(SchemaSymbols.ATTVAL_TWOPOUNDLOCAL)) {
                                strAddSymbol = null;
                            } else {
                                if (strNextToken.equals(SchemaSymbols.ATTVAL_TWOPOUNDTARGETNS)) {
                                    strAddSymbol = xSDocumentInfo.fTargetNamespace;
                                } else {
                                    fExtraDVs[0].validate(strNextToken, (ValidationContext) xSDocumentInfo.fValidationContext, (ValidatedInfo) null);
                                    strAddSymbol = this.fSymbolTable.addSymbol(strNextToken);
                                }
                                if (!this.fNamespaceList.contains(strAddSymbol)) {
                                    this.fNamespaceList.addElement(strAddSymbol);
                                }
                            }
                            if (!this.fNamespaceList.contains(strAddSymbol)) {
                                this.fNamespaceList.addElement(strAddSymbol);
                            }
                        } catch (InvalidDatatypeValueException unused3) {
                            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{strTrim, "((##any | ##other) | List of (anyURI | (##targetNamespace | ##local)) )"});
                        }
                        break;
                    }
                    String[] strArr = new String[this.fNamespaceList.size()];
                    this.fNamespaceList.copyInto(strArr);
                    objArr[ATTIDX_NAMESPACE_LIST] = strArr;
                }
                return vector;
            case -10:
                if (strTrim.equals("0")) {
                    xIntPool = fXIntPool;
                } else {
                    if (!strTrim.equals(SchemaSymbols.ATTVAL_TRUE_1)) {
                        throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{strTrim, "(0 | 1)"});
                    }
                    xIntPool = fXIntPool;
                    i3 = 1;
                }
                return xIntPool.getXInt(i3);
            case -9:
                vector = new Vector();
                try {
                    StringTokenizer stringTokenizer2 = new StringTokenizer(strTrim, " \n\t\r");
                    while (stringTokenizer2.hasMoreTokens()) {
                        QName qName = (QName) fExtraDVs[2].validate(stringTokenizer2.nextToken(), (ValidationContext) xSDocumentInfo.fValidationContext, (ValidatedInfo) null);
                        if (qName.prefix == XMLSymbols.EMPTY_STRING && qName.uri == null && xSDocumentInfo.fIsChameleonSchema) {
                            qName.uri = xSDocumentInfo.fTargetNamespace;
                        }
                        vector.addElement(qName);
                        break;
                    }
                    return vector;
                } catch (InvalidDatatypeValueException unused4) {
                    throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.2", new Object[]{strTrim, "(List of QName)"});
                }
            case -8:
                if (!strTrim.equals(SchemaSymbols.ATTVAL_TRUE_1)) {
                    throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{strTrim, "(1)"});
                }
                xIntPool = fXIntPool;
                i3 = 1;
                return xIntPool.getXInt(i3);
            case -7:
                if (strTrim.equals(SchemaSymbols.ATTVAL_UNBOUNDED)) {
                    return INT_UNBOUNDED;
                }
                try {
                    return validate(objArr, str, strTrim, DT_NONNEGINT, xSDocumentInfo);
                } catch (NumberFormatException unused5) {
                    throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{strTrim, "(nonNegativeInteger | unbounded)"});
                }
            case -6:
                if (strTrim.equals(SchemaSymbols.ATTVAL_QUALIFIED)) {
                    return INT_QUALIFIED;
                }
                if (strTrim.equals(SchemaSymbols.ATTVAL_UNQUALIFIED)) {
                    return INT_UNQUALIFIED;
                }
                throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{strTrim, "(qualified | unqualified)"});
            case -5:
                if (strTrim.equals(SchemaSymbols.ATTVAL_POUNDALL)) {
                    i2 = 31;
                } else {
                    StringTokenizer stringTokenizer3 = new StringTokenizer(strTrim, " \n\t\r");
                    while (stringTokenizer3.hasMoreTokens()) {
                        String strNextToken2 = stringTokenizer3.nextToken();
                        if (strNextToken2.equals(SchemaSymbols.ATTVAL_EXTENSION)) {
                            i3 |= 1;
                        } else if (strNextToken2.equals(SchemaSymbols.ATTVAL_RESTRICTION)) {
                            i3 |= 2;
                        } else if (strNextToken2.equals(SchemaSymbols.ATTVAL_LIST)) {
                            i3 |= 16;
                        } else {
                            if (!strNextToken2.equals(SchemaSymbols.ATTVAL_UNION)) {
                                throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{strTrim, "(#all | List of (extension | restriction | list | union))"});
                            }
                            i3 |= 8;
                        }
                    }
                    i2 = i3;
                }
                return fXIntPool.getXInt(i2);
            case -4:
                if (strTrim.equals(SchemaSymbols.ATTVAL_POUNDALL)) {
                    i2 = 31;
                } else {
                    StringTokenizer stringTokenizer4 = new StringTokenizer(strTrim, " \n\t\r");
                    while (stringTokenizer4.hasMoreTokens()) {
                        String strNextToken3 = stringTokenizer4.nextToken();
                        if (strNextToken3.equals(SchemaSymbols.ATTVAL_LIST)) {
                            i3 |= 16;
                        } else if (strNextToken3.equals(SchemaSymbols.ATTVAL_UNION)) {
                            i3 |= 8;
                        } else {
                            if (!strNextToken3.equals(SchemaSymbols.ATTVAL_RESTRICTION)) {
                                throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{strTrim, "(#all | List of (list | union | restriction))"});
                            }
                            i3 |= 2;
                        }
                    }
                    i2 = i3;
                }
                return fXIntPool.getXInt(i2);
            case -3:
            case -2:
                if (strTrim.equals(SchemaSymbols.ATTVAL_POUNDALL)) {
                    i2 = 31;
                } else {
                    StringTokenizer stringTokenizer5 = new StringTokenizer(strTrim, " \n\t\r");
                    while (stringTokenizer5.hasMoreTokens()) {
                        String strNextToken4 = stringTokenizer5.nextToken();
                        if (strNextToken4.equals(SchemaSymbols.ATTVAL_EXTENSION)) {
                            i3 |= 1;
                        } else {
                            if (!strNextToken4.equals(SchemaSymbols.ATTVAL_RESTRICTION)) {
                                throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{strTrim, "(#all | List of (extension | restriction))"});
                            }
                            i3 |= 2;
                        }
                    }
                    i2 = i3;
                }
                return fXIntPool.getXInt(i2);
            case -1:
                if (strTrim.equals(SchemaSymbols.ATTVAL_POUNDALL)) {
                    i2 = 31;
                } else {
                    StringTokenizer stringTokenizer6 = new StringTokenizer(strTrim, " \n\t\r");
                    while (stringTokenizer6.hasMoreTokens()) {
                        String strNextToken5 = stringTokenizer6.nextToken();
                        if (strNextToken5.equals(SchemaSymbols.ATTVAL_EXTENSION)) {
                            i3 |= 1;
                        } else if (strNextToken5.equals(SchemaSymbols.ATTVAL_RESTRICTION)) {
                            i3 |= 2;
                        } else {
                            if (!strNextToken5.equals(SchemaSymbols.ATTVAL_SUBSTITUTION)) {
                                throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{strTrim, "(#all | List of (extension | restriction | substitution))"});
                            }
                            i3 |= 4;
                        }
                    }
                    i2 = i3;
                }
                return fXIntPool.getXInt(i2);
            default:
                return null;
        }
    }

    public Object[] checkAttributes(Element element, boolean z, XSDocumentInfo xSDocumentInfo) {
        return checkAttributes(element, z, xSDocumentInfo, false);
    }

    /* JADX WARN: Code duplicated, block: B:119:0x01b6 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:22:0x005e A[PHI: r0
      0x005e: PHI (r0v4 java.util.Hashtable) = (r0v3 java.util.Hashtable), (r0v39 java.util.Hashtable) binds: [B:9:0x002e, B:17:0x004e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:56:0x0107  */
    /* JADX WARN: Code duplicated, block: B:58:0x010d  */
    /* JADX WARN: Code duplicated, block: B:59:0x0115  */
    /* JADX WARN: Code duplicated, block: B:62:0x011f A[Catch: InvalidDatatypeValueException -> 0x0182, TryCatch #1 {InvalidDatatypeValueException -> 0x0182, blocks: (B:60:0x011b, B:62:0x011f, B:64:0x0124, B:66:0x0129, B:68:0x012e, B:70:0x013f, B:72:0x0148, B:74:0x014c, B:76:0x0150, B:77:0x0154, B:78:0x0159, B:80:0x0163), top: B:114:0x011b }] */
    /* JADX WARN: Code duplicated, block: B:64:0x0124 A[Catch: InvalidDatatypeValueException -> 0x0182, TryCatch #1 {InvalidDatatypeValueException -> 0x0182, blocks: (B:60:0x011b, B:62:0x011f, B:64:0x0124, B:66:0x0129, B:68:0x012e, B:70:0x013f, B:72:0x0148, B:74:0x014c, B:76:0x0150, B:77:0x0154, B:78:0x0159, B:80:0x0163), top: B:114:0x011b }] */
    /* JADX WARN: Code duplicated, block: B:78:0x0159 A[Catch: InvalidDatatypeValueException -> 0x0182, TryCatch #1 {InvalidDatatypeValueException -> 0x0182, blocks: (B:60:0x011b, B:62:0x011f, B:64:0x0124, B:66:0x0129, B:68:0x012e, B:70:0x013f, B:72:0x0148, B:74:0x014c, B:76:0x0150, B:77:0x0154, B:78:0x0159, B:80:0x0163), top: B:114:0x011b }] */
    /* JADX WARN: Code duplicated, block: B:80:0x0163 A[Catch: InvalidDatatypeValueException -> 0x0182, TRY_LEAVE, TryCatch #1 {InvalidDatatypeValueException -> 0x0182, blocks: (B:60:0x011b, B:62:0x011f, B:64:0x0124, B:66:0x0129, B:68:0x012e, B:70:0x013f, B:72:0x0148, B:74:0x014c, B:76:0x0150, B:77:0x0154, B:78:0x0159, B:80:0x0163), top: B:114:0x011b }] */
    /* JADX WARN: Code duplicated, block: B:93:0x01a9 A[ADDED_TO_REGION] */
    /* JADX WARN: Multi-variable type inference failed */
    public Object[] checkAttributes(Element element, boolean z, XSDocumentInfo xSDocumentInfo, boolean z2) {
        String str;
        String str2;
        OneAttr oneAttr;
        int i;
        int i2;
        Attr[] attrArr;
        ValidatedInfo validatedInfo = null;
        if (element == null) {
            return null;
        }
        Attr[] attrs = DOMUtil.getAttrs(element);
        resolveNamespace(element, attrs, xSDocumentInfo.fNamespaceSupport);
        String namespaceURI = DOMUtil.getNamespaceURI(element);
        String localName = DOMUtil.getLocalName(element);
        if (!SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(namespaceURI)) {
            reportSchemaError("s4s-elt-schema-ns", new Object[]{localName}, element);
        }
        Hashtable hashtable = fEleAttrsMapG;
        if (z) {
            str = localName;
        } else {
            hashtable = fEleAttrsMapL;
            if (localName.equals(SchemaSymbols.ELT_ELEMENT)) {
                str = DOMUtil.getAttr(element, SchemaSymbols.ATT_REF) != null ? ELEMENT_R : ELEMENT_N;
            } else if (localName.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
                str = DOMUtil.getAttr(element, SchemaSymbols.ATT_REF) != null ? ATTRIBUTE_R : ATTRIBUTE_N;
            } else {
                str = localName;
            }
        }
        Container container = (Container) hashtable.get(str);
        if (container == null) {
            reportSchemaError("s4s-elt-invalid", new Object[]{localName}, element);
            return null;
        }
        Object[] availableArray = getAvailableArray();
        System.arraycopy(fSeenTemp, 0, this.fSeen, 0, ATTIDX_COUNT);
        int length = attrs.length;
        int i3 = 0;
        while (i3 < length) {
            Attr attr = attrs[i3];
            String name2 = attr.getName();
            String namespaceURI2 = DOMUtil.getNamespaceURI(attr);
            String value = DOMUtil.getValue(attr);
            if (!name2.startsWith("xml")) {
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                if (str2 != 0 || str2.length() == 0) {
                    oneAttr = container.get(name2);
                    if (oneAttr == null) {
                        reportSchemaError("s4s-att-not-allowed", new Object[]{localName, name2}, element);
                    } else {
                        this.fSeen[oneAttr.valueIndex] = true;
                        try {
                            if (oneAttr.dvIndex >= 0) {
                                if (oneAttr.dvIndex != 3 || oneAttr.dvIndex == 6 || oneAttr.dvIndex == 7) {
                                    availableArray[oneAttr.valueIndex] = value;
                                } else {
                                    Object objValidate = fExtraDVs[oneAttr.dvIndex].validate(value, (ValidationContext) xSDocumentInfo.fValidationContext, validatedInfo);
                                    if (oneAttr.dvIndex == 2) {
                                        QName qName = (QName) objValidate;
                                        if (qName.prefix == XMLSymbols.EMPTY_STRING && qName.uri == null && xSDocumentInfo.fIsChameleonSchema) {
                                            qName.uri = xSDocumentInfo.fTargetNamespace;
                                        }
                                    }
                                    availableArray[oneAttr.valueIndex] = objValidate;
                                }
                                i = i3;
                                i2 = length;
                                attrArr = attrs;
                            } else {
                                attrArr = attrs;
                                i = i3;
                                i2 = length;
                                try {
                                    availableArray[oneAttr.valueIndex] = validate(availableArray, name2, value, oneAttr.dvIndex, xSDocumentInfo);
                                } catch (InvalidDatatypeValueException e) {
                                    e = e;
                                    reportSchemaError("s4s-att-invalid-value", new Object[]{localName, name2, e.getMessage()}, element);
                                    if (oneAttr.dfltValue != null) {
                                        availableArray[oneAttr.valueIndex] = oneAttr.dfltValue;
                                    }
                                }
                            }
                        } catch (InvalidDatatypeValueException e2) {
                            e = e2;
                            i = i3;
                            i2 = length;
                            attrArr = attrs;
                        }
                        if (!localName.equals(SchemaSymbols.ELT_ENUMERATION) && z2) {
                            availableArray[ATTIDX_ENUMNSDECLS] = new SchemaNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                        }
                    }
                } else if (str2.equals(SchemaSymbols.URI_SCHEMAFORSCHEMA)) {
                    reportSchemaError("s4s-att-not-allowed", new Object[]{localName, name2}, element);
                } else {
                    int i4 = ATTIDX_NONSCHEMA;
                    if (availableArray[i4] == null) {
                        availableArray[i4] = new Vector(4, 2);
                    }
                    ((Vector) availableArray[i4]).addElement(name2);
                    ((Vector) availableArray[i4]).addElement(value);
                }
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                i = i3;
                i2 = length;
                attrArr = attrs;
            } else if (XMLConstants.XMLNS_ATTRIBUTE.equals(DOMUtil.getPrefix(attr)) || XMLConstants.XMLNS_ATTRIBUTE.equals(name2)) {
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                i = i3;
                i2 = length;
                attrArr = attrs;
            } else {
                if (SchemaSymbols.ATT_XML_LANG.equals(name2) && (SchemaSymbols.ELT_SCHEMA.equals(localName) || SchemaSymbols.ELT_DOCUMENTATION.equals(localName))) {
                    str2 = namespaceURI2;
                    str2 = namespaceURI2;
                    str2 = namespaceURI2;
                    str2 = namespaceURI2;
                    str2 = namespaceURI2;
                    str2 = validatedInfo;
                }
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                str2 = namespaceURI2;
                if (str2 != 0) {
                    oneAttr = container.get(name2);
                    if (oneAttr == null) {
                        reportSchemaError("s4s-att-not-allowed", new Object[]{localName, name2}, element);
                        str2 = namespaceURI2;
                        str2 = namespaceURI2;
                        i = i3;
                        i2 = length;
                        attrArr = attrs;
                    } else {
                        this.fSeen[oneAttr.valueIndex] = true;
                        if (oneAttr.dvIndex >= 0) {
                            if (oneAttr.dvIndex != 3) {
                                availableArray[oneAttr.valueIndex] = value;
                            } else {
                                availableArray[oneAttr.valueIndex] = value;
                            }
                            i = i3;
                            i2 = length;
                            attrArr = attrs;
                        } else {
                            attrArr = attrs;
                            i = i3;
                            i2 = length;
                            availableArray[oneAttr.valueIndex] = validate(availableArray, name2, value, oneAttr.dvIndex, xSDocumentInfo);
                        }
                        if (!localName.equals(SchemaSymbols.ELT_ENUMERATION)) {
                        }
                    }
                } else {
                    oneAttr = container.get(name2);
                    if (oneAttr == null) {
                        reportSchemaError("s4s-att-not-allowed", new Object[]{localName, name2}, element);
                        str2 = namespaceURI2;
                        str2 = namespaceURI2;
                        i = i3;
                        i2 = length;
                        attrArr = attrs;
                    } else {
                        this.fSeen[oneAttr.valueIndex] = true;
                        if (oneAttr.dvIndex >= 0) {
                            if (oneAttr.dvIndex != 3) {
                                availableArray[oneAttr.valueIndex] = value;
                            } else {
                                availableArray[oneAttr.valueIndex] = value;
                            }
                            i = i3;
                            i2 = length;
                            attrArr = attrs;
                        } else {
                            attrArr = attrs;
                            i = i3;
                            i2 = length;
                            availableArray[oneAttr.valueIndex] = validate(availableArray, name2, value, oneAttr.dvIndex, xSDocumentInfo);
                        }
                        if (!localName.equals(SchemaSymbols.ELT_ENUMERATION)) {
                        }
                    }
                }
            }
            i3 = i + 1;
            length = i2;
            attrs = attrArr;
            validatedInfo = null;
        }
        long j = 0;
        for (OneAttr oneAttr2 : container.values) {
            if (oneAttr2.dfltValue != null && !this.fSeen[oneAttr2.valueIndex]) {
                availableArray[oneAttr2.valueIndex] = oneAttr2.dfltValue;
                j |= (long) (1 << oneAttr2.valueIndex);
            }
        }
        availableArray[ATTIDX_FROMDEFAULT] = new Long(j);
        int i5 = ATTIDX_MAXOCCURS;
        if (availableArray[i5] != null) {
            int i6 = ATTIDX_MINOCCURS;
            int iIntValue = ((XInt) availableArray[i6]).intValue();
            int iIntValue2 = ((XInt) availableArray[i5]).intValue();
            if (iIntValue2 != -1 && iIntValue > iIntValue2) {
                reportSchemaError("p-props-correct.2.1", new Object[]{localName, availableArray[i6], availableArray[i5]}, element);
                availableArray[i6] = availableArray[i5];
            }
        }
        return availableArray;
    }

    public void checkNonSchemaAttributes(XSGrammarBucket xSGrammarBucket) {
        XSAttributeDecl globalAttributeDecl;
        XSSimpleType xSSimpleType;
        for (Map.Entry entry : this.fNonSchemaAttrs.entrySet()) {
            String str = (String) entry.getKey();
            String strSubstring = str.substring(0, str.indexOf(44));
            String strSubstring2 = str.substring(str.indexOf(44) + 1);
            SchemaGrammar grammar = xSGrammarBucket.getGrammar(strSubstring);
            if (grammar != null && (globalAttributeDecl = grammar.getGlobalAttributeDecl(strSubstring2)) != null && (xSSimpleType = (XSSimpleType) globalAttributeDecl.getTypeDefinition()) != null) {
                Vector vector = (Vector) entry.getValue();
                String str2 = (String) vector.elementAt(0);
                int size = vector.size();
                for (int i = 1; i < size; i += 2) {
                    String str3 = (String) vector.elementAt(i);
                    try {
                        xSSimpleType.validate((String) vector.elementAt(i + 1), (ValidationContext) null, (ValidatedInfo) null);
                    } catch (InvalidDatatypeValueException e) {
                        reportSchemaError("s4s-att-invalid-value", new Object[]{str3, str2, e.getMessage()}, null);
                    }
                }
            }
        }
    }

    protected Object[] getAvailableArray() {
        int length = this.fArrayPool.length;
        int i = this.fPoolPos;
        if (length == i) {
            this.fArrayPool = new Object[i + 10][];
            while (true) {
                Object[][] objArr = this.fArrayPool;
                if (i >= objArr.length) {
                    break;
                }
                objArr[i] = new Object[ATTIDX_COUNT];
                i++;
            }
        }
        Object[][] objArr2 = this.fArrayPool;
        int i2 = this.fPoolPos;
        Object[] objArr3 = objArr2[i2];
        this.fPoolPos = i2 + 1;
        objArr2[i2] = null;
        System.arraycopy(fTempArray, 0, objArr3, 0, ATTIDX_COUNT - 1);
        objArr3[ATTIDX_ISRETURNED] = Boolean.FALSE;
        return objArr3;
    }

    void reportSchemaError(String str, Object[] objArr, Element element) {
        this.fSchemaHandler.reportSchemaError(str, objArr, element);
    }

    public void reset(SymbolTable symbolTable) {
        this.fSymbolTable = symbolTable;
        this.fNonSchemaAttrs.clear();
    }

    public void resolveNamespace(Element element, Attr[] attrArr, SchemaNamespaceSupport schemaNamespaceSupport) {
        schemaNamespaceSupport.pushContext();
        for (Attr attr : attrArr) {
            String name2 = DOMUtil.getName(attr);
            String strAddSymbol = name2.equals(XMLSymbols.PREFIX_XMLNS) ? XMLSymbols.EMPTY_STRING : name2.startsWith("xmlns:") ? this.fSymbolTable.addSymbol(DOMUtil.getLocalName(attr)) : null;
            if (strAddSymbol != null) {
                String strAddSymbol2 = this.fSymbolTable.addSymbol(DOMUtil.getValue(attr));
                schemaNamespaceSupport.declarePrefix(strAddSymbol, strAddSymbol2.length() != 0 ? strAddSymbol2 : null);
            }
        }
    }

    public void returnAttrArray(Object[] objArr, XSDocumentInfo xSDocumentInfo) {
        if (xSDocumentInfo != null) {
            xSDocumentInfo.fNamespaceSupport.popContext();
        }
        if (this.fPoolPos == 0 || objArr == null || objArr.length != ATTIDX_COUNT) {
            return;
        }
        int i = ATTIDX_ISRETURNED;
        if (((Boolean) objArr[i]).booleanValue()) {
            return;
        }
        objArr[i] = Boolean.TRUE;
        Object obj = objArr[ATTIDX_NONSCHEMA];
        if (obj != null) {
            ((Vector) obj).clear();
        }
        Object[][] objArr2 = this.fArrayPool;
        int i2 = this.fPoolPos - 1;
        this.fPoolPos = i2;
        objArr2[i2] = objArr;
    }
}
