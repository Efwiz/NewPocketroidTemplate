package org.apache.xerces.impl.dv.xs;

import io.netty.util.internal.StringUtil;
import java.math.BigInteger;
import java.util.AbstractList;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.xerces.impl.dv.DatatypeException;
import org.apache.xerces.impl.dv.InvalidDatatypeFacetException;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.dv.XSFacets;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xpath.regex.RegularExpression;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.util.ObjectListImpl;
import org.apache.xerces.impl.xs.util.ShortListImpl;
import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSFacet;
import org.apache.xerces.xs.XSMultiValueFacet;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.apache.xerces.xs.datatypes.ObjectList;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.SpriteActivity;
import org.w3c.dom.TypeInfo;

/* JADX INFO: loaded from: classes13.dex */
public class XSSimpleTypeDecl implements XSSimpleType, TypeInfo {
    public static final short ANYATOMICTYPE_DT = 49;
    static final String ANY_TYPE = "anyType";
    public static final short DAYTIMEDURATION_DT = 47;
    static final int DERIVATION_ANY = 0;
    static final int DERIVATION_EXTENSION = 2;
    static final int DERIVATION_LIST = 8;
    static final int DERIVATION_RESTRICTION = 1;
    static final int DERIVATION_UNION = 4;
    protected static final short DV_ANYATOMICTYPE = 29;
    protected static final short DV_ANYSIMPLETYPE = 0;
    protected static final short DV_ANYURI = 17;
    protected static final short DV_BASE64BINARY = 16;
    protected static final short DV_BOOLEAN = 2;
    protected static final short DV_DATE = 9;
    protected static final short DV_DATETIME = 7;
    protected static final short DV_DAYTIMEDURATION = 28;
    protected static final short DV_DECIMAL = 3;
    protected static final short DV_DOUBLE = 5;
    protected static final short DV_DURATION = 6;
    protected static final short DV_ENTITY = 23;
    protected static final short DV_FLOAT = 4;
    protected static final short DV_GDAY = 13;
    protected static final short DV_GMONTH = 14;
    protected static final short DV_GMONTHDAY = 12;
    protected static final short DV_GYEAR = 11;
    protected static final short DV_GYEARMONTH = 10;
    protected static final short DV_HEXBINARY = 15;
    protected static final short DV_ID = 21;
    protected static final short DV_IDREF = 22;
    protected static final short DV_INTEGER = 24;
    protected static final short DV_LIST = 25;
    protected static final short DV_NOTATION = 20;
    protected static final short DV_PRECISIONDECIMAL = 19;
    protected static final short DV_QNAME = 18;
    protected static final short DV_STRING = 1;
    protected static final short DV_TIME = 8;
    protected static final short DV_UNION = 26;
    protected static final short DV_YEARMONTHDURATION = 27;
    static final short NORMALIZE_FULL = 2;
    static final short NORMALIZE_NONE = 0;
    static final short NORMALIZE_TRIM = 1;
    public static final short PRECISIONDECIMAL_DT = 48;
    static final short SPECIAL_PATTERN_NAME = 2;
    static final short SPECIAL_PATTERN_NCNAME = 3;
    static final short SPECIAL_PATTERN_NMTOKEN = 1;
    static final short SPECIAL_PATTERN_NONE = 0;
    static final String URI_SCHEMAFORSCHEMA = "http://www.w3.org/2001/XMLSchema";
    public static final short YEARMONTHDURATION_DT = 46;
    static final XSSimpleTypeDecl fAnyAtomicType;
    static final XSSimpleTypeDecl fAnySimpleType;
    static final ValidationContext fDummyContext;
    public XSObjectList enumerationAnnotations;
    private ObjectList fActualEnumeration;
    private XSObjectList fAnnotations;
    private boolean fAnonymous;
    private XSSimpleTypeDecl fBase;
    private boolean fBounded;
    private short fBuiltInKind;
    private TypeValidator[] fDVs;
    private ValidatedInfo[] fEnumeration;
    private ObjectList fEnumerationItemTypeList;
    private int fEnumerationSize;
    private ShortList fEnumerationTypeList;
    private XSObjectListImpl fFacets;
    private short fFacetsDefined;
    private short fFinalSet;
    private boolean fFinite;
    private short fFixedFacet;
    private int fFractionDigits;
    private boolean fIsImmutable;
    private XSSimpleTypeDecl fItemType;
    private int fLength;
    private StringList fLexicalEnumeration;
    private StringList fLexicalPattern;
    private Object fMaxExclusive;
    private Object fMaxInclusive;
    private int fMaxLength;
    private XSSimpleTypeDecl[] fMemberTypes;
    private Object fMinExclusive;
    private Object fMinInclusive;
    private int fMinLength;
    private XSObjectListImpl fMultiValueFacets;
    private XSNamespaceItem fNamespaceItem;
    private boolean fNumeric;
    private short fOrdered;
    private Vector fPattern;
    private Vector fPatternStr;
    private short fPatternType;
    private String fTargetNamespace;
    private int fTotalDigits;
    private String fTypeName;
    private short fValidationDV;
    private short fVariety;
    private short fWhiteSpace;
    public XSAnnotation fractionDigitsAnnotation;
    public XSAnnotation lengthAnnotation;
    public XSAnnotation maxExclusiveAnnotation;
    public XSAnnotation maxInclusiveAnnotation;
    public XSAnnotation maxLengthAnnotation;
    public XSAnnotation minExclusiveAnnotation;
    public XSAnnotation minInclusiveAnnotation;
    public XSAnnotation minLengthAnnotation;
    public XSObjectListImpl patternAnnotations;
    public XSAnnotation totalDigitsAnnotation;
    public XSAnnotation whiteSpaceAnnotation;
    private static final TypeValidator[] gDVs = {new AnySimpleDV(), new StringDV(), new BooleanDV(), new DecimalDV(), new FloatDV(), new DoubleDV(), new DurationDV(), new DateTimeDV(), new TimeDV(), new DateDV(), new YearMonthDV(), new YearDV(), new MonthDayDV(), new DayDV(), new MonthDV(), new HexBinaryDV(), new Base64BinaryDV(), new AnyURIDV(), new QNameDV(), new PrecisionDecimalDV(), new QNameDV(), new IDDV(), new IDREFDV(), new EntityDV(), new IntegerDV(), new ListDV(), new UnionDV(), new YearMonthDurationDV(), new DayTimeDurationDV(), new AnyAtomicDV()};
    static final short[] fDVNormalizeType = {0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 0, 1, 1, 0};
    static final String[] SPECIAL_PATTERN_STRING = {"NONE", SchemaSymbols.ATTVAL_NMTOKEN, SchemaSymbols.ATTVAL_NAME, SchemaSymbols.ATTVAL_NCNAME};
    static final String[] WS_FACET_STRING = {"preserve", SchemaSymbols.ATTVAL_REPLACE, SchemaSymbols.ATTVAL_COLLAPSE};
    static final ValidationContext fEmptyContext = new ValidationContext() { // from class: org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl.1
        @Override // org.apache.xerces.impl.dv.ValidationContext
        public void addId(String str) {
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public void addIdRef(String str) {
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public Locale getLocale() {
            return Locale.getDefault();
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public String getSymbol(String str) {
            return str.intern();
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public String getURI(String str) {
            return null;
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean isEntityDeclared(String str) {
            return false;
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean isEntityUnparsed(String str) {
            return false;
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean isIdDeclared(String str) {
            return false;
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean needExtraChecking() {
            return false;
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean needFacetChecking() {
            return true;
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean needToNormalize() {
            return true;
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean useNamespaces() {
            return true;
        }
    };

    private static abstract class AbstractObjectList extends AbstractList implements ObjectList {
        private AbstractObjectList() {
        }

        @Override // java.util.AbstractList, java.util.List
        public Object get(int i) {
            if (i < 0 || i >= getLength()) {
                throw new IndexOutOfBoundsException("Index: " + i);
            }
            return item(i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return getLength();
        }
    }

    static final class ValidationContextImpl implements ValidationContext {
        final ValidationContext fExternal;
        NamespaceContext fNSContext;

        ValidationContextImpl(ValidationContext validationContext) {
            this.fExternal = validationContext;
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public void addId(String str) {
            this.fExternal.addId(str);
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public void addIdRef(String str) {
            this.fExternal.addIdRef(str);
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public Locale getLocale() {
            return this.fExternal.getLocale();
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public String getSymbol(String str) {
            return this.fExternal.getSymbol(str);
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public String getURI(String str) {
            NamespaceContext namespaceContext = this.fNSContext;
            return namespaceContext == null ? this.fExternal.getURI(str) : namespaceContext.getURI(str);
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean isEntityDeclared(String str) {
            return this.fExternal.isEntityDeclared(str);
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean isEntityUnparsed(String str) {
            return this.fExternal.isEntityUnparsed(str);
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean isIdDeclared(String str) {
            return this.fExternal.isIdDeclared(str);
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean needExtraChecking() {
            return this.fExternal.needExtraChecking();
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean needFacetChecking() {
            return this.fExternal.needFacetChecking();
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean needToNormalize() {
            return this.fExternal.needToNormalize();
        }

        void setNSContext(NamespaceContext namespaceContext) {
            this.fNSContext = namespaceContext;
        }

        @Override // org.apache.xerces.impl.dv.ValidationContext
        public boolean useNamespaces() {
            return true;
        }
    }

    private static final class XSFacetImpl implements XSFacet {
        final XSObjectList annotations;
        Object avalue;
        final boolean fixed;
        final int ivalue;
        final short kind;
        final String svalue;

        public XSFacetImpl(short s, String str, int i, Object obj, boolean z, XSAnnotation xSAnnotation) {
            this.kind = s;
            this.svalue = str;
            this.ivalue = i;
            this.avalue = obj;
            this.fixed = z;
            if (xSAnnotation == null) {
                this.annotations = XSObjectListImpl.EMPTY_LIST;
                return;
            }
            XSObjectListImpl xSObjectListImpl = new XSObjectListImpl();
            this.annotations = xSObjectListImpl;
            xSObjectListImpl.addXSObject(xSAnnotation);
        }

        @Override // org.apache.xerces.xs.XSFacet
        public Object getActualFacetValue() {
            if (this.avalue == null) {
                this.avalue = this.kind == 16 ? this.svalue : BigInteger.valueOf(this.ivalue);
            }
            return this.avalue;
        }

        @Override // org.apache.xerces.xs.XSFacet
        public XSAnnotation getAnnotation() {
            return (XSAnnotation) this.annotations.item(0);
        }

        @Override // org.apache.xerces.xs.XSFacet
        public XSObjectList getAnnotations() {
            return this.annotations;
        }

        @Override // org.apache.xerces.xs.XSFacet
        public short getFacetKind() {
            return this.kind;
        }

        @Override // org.apache.xerces.xs.XSFacet
        public boolean getFixed() {
            return this.fixed;
        }

        @Override // org.apache.xerces.xs.XSFacet
        public int getIntFacetValue() {
            return this.ivalue;
        }

        @Override // org.apache.xerces.xs.XSFacet
        public String getLexicalFacetValue() {
            return this.svalue;
        }

        @Override // org.apache.xerces.xs.XSObject
        public String getName() {
            return null;
        }

        @Override // org.apache.xerces.xs.XSObject
        public String getNamespace() {
            return null;
        }

        @Override // org.apache.xerces.xs.XSObject
        public XSNamespaceItem getNamespaceItem() {
            return null;
        }

        @Override // org.apache.xerces.xs.XSObject
        public short getType() {
            return (short) 13;
        }
    }

    private static final class XSMVFacetImpl implements XSMultiValueFacet {
        final XSObjectList annotations;
        final ObjectList avalues;
        final short kind;
        final StringList svalues;

        public XSMVFacetImpl(short s, StringList stringList, ObjectList objectList, XSObjectList xSObjectList) {
            this.kind = s;
            this.svalues = stringList;
            this.avalues = objectList;
            this.annotations = xSObjectList == null ? XSObjectListImpl.EMPTY_LIST : xSObjectList;
        }

        @Override // org.apache.xerces.xs.XSMultiValueFacet
        public XSObjectList getAnnotations() {
            return this.annotations;
        }

        @Override // org.apache.xerces.xs.XSMultiValueFacet
        public ObjectList getEnumerationValues() {
            return this.avalues;
        }

        @Override // org.apache.xerces.xs.XSMultiValueFacet
        public short getFacetKind() {
            return this.kind;
        }

        @Override // org.apache.xerces.xs.XSMultiValueFacet
        public StringList getLexicalFacetValues() {
            return this.svalues;
        }

        @Override // org.apache.xerces.xs.XSObject
        public String getName() {
            return null;
        }

        @Override // org.apache.xerces.xs.XSObject
        public String getNamespace() {
            return null;
        }

        @Override // org.apache.xerces.xs.XSObject
        public XSNamespaceItem getNamespaceItem() {
            return null;
        }

        @Override // org.apache.xerces.xs.XSObject
        public short getType() {
            return (short) 14;
        }
    }

    static {
        XSSimpleTypeDecl xSSimpleTypeDecl = new XSSimpleTypeDecl(null, SchemaSymbols.ATTVAL_ANYSIMPLETYPE, (short) 0, (short) 0, false, true, false, true, (short) 1);
        fAnySimpleType = xSSimpleTypeDecl;
        fAnyAtomicType = new XSSimpleTypeDecl(xSSimpleTypeDecl, "anyAtomicType", (short) 29, (short) 0, false, true, false, true, (short) 49);
        fDummyContext = new ValidationContext() { // from class: org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl.4
            @Override // org.apache.xerces.impl.dv.ValidationContext
            public void addId(String str) {
            }

            @Override // org.apache.xerces.impl.dv.ValidationContext
            public void addIdRef(String str) {
            }

            @Override // org.apache.xerces.impl.dv.ValidationContext
            public Locale getLocale() {
                return Locale.getDefault();
            }

            @Override // org.apache.xerces.impl.dv.ValidationContext
            public String getSymbol(String str) {
                return str.intern();
            }

            @Override // org.apache.xerces.impl.dv.ValidationContext
            public String getURI(String str) {
                return null;
            }

            @Override // org.apache.xerces.impl.dv.ValidationContext
            public boolean isEntityDeclared(String str) {
                return false;
            }

            @Override // org.apache.xerces.impl.dv.ValidationContext
            public boolean isEntityUnparsed(String str) {
                return false;
            }

            @Override // org.apache.xerces.impl.dv.ValidationContext
            public boolean isIdDeclared(String str) {
                return false;
            }

            @Override // org.apache.xerces.impl.dv.ValidationContext
            public boolean needExtraChecking() {
                return false;
            }

            @Override // org.apache.xerces.impl.dv.ValidationContext
            public boolean needFacetChecking() {
                return true;
            }

            @Override // org.apache.xerces.impl.dv.ValidationContext
            public boolean needToNormalize() {
                return false;
            }

            @Override // org.apache.xerces.impl.dv.ValidationContext
            public boolean useNamespaces() {
                return true;
            }
        };
    }

    public XSSimpleTypeDecl() {
        this.fDVs = gDVs;
        this.fIsImmutable = false;
        this.fFinalSet = (short) 0;
        this.fVariety = (short) -1;
        this.fValidationDV = (short) -1;
        this.fFacetsDefined = (short) 0;
        this.fFixedFacet = (short) 0;
        this.fWhiteSpace = (short) 0;
        this.fLength = -1;
        this.fMinLength = -1;
        this.fMaxLength = -1;
        this.fTotalDigits = -1;
        this.fFractionDigits = -1;
        this.fAnnotations = null;
        this.fPatternType = (short) 0;
        this.fNamespaceItem = null;
        this.fAnonymous = false;
    }

    protected XSSimpleTypeDecl(String str, String str2, short s, XSSimpleTypeDecl xSSimpleTypeDecl, boolean z, XSObjectList xSObjectList) {
        this.fDVs = gDVs;
        this.fIsImmutable = false;
        this.fLength = -1;
        this.fMinLength = -1;
        this.fMaxLength = -1;
        this.fTotalDigits = -1;
        this.fFractionDigits = -1;
        this.fPatternType = (short) 0;
        this.fNamespaceItem = null;
        this.fAnonymous = false;
        this.fBase = fAnySimpleType;
        this.fTypeName = str;
        this.fTargetNamespace = str2;
        this.fFinalSet = s;
        this.fAnnotations = xSObjectList;
        this.fVariety = (short) 2;
        this.fItemType = xSSimpleTypeDecl;
        this.fValidationDV = (short) 25;
        this.fFacetsDefined = (short) 16;
        this.fFixedFacet = (short) 16;
        this.fWhiteSpace = (short) 2;
        calcFundamentalFacets();
        this.fIsImmutable = z;
        this.fBuiltInKind = (short) 44;
    }

    protected XSSimpleTypeDecl(String str, String str2, short s, XSSimpleTypeDecl[] xSSimpleTypeDeclArr, XSObjectList xSObjectList) {
        this.fDVs = gDVs;
        this.fIsImmutable = false;
        this.fFixedFacet = (short) 0;
        this.fLength = -1;
        this.fMinLength = -1;
        this.fMaxLength = -1;
        this.fTotalDigits = -1;
        this.fFractionDigits = -1;
        this.fPatternType = (short) 0;
        this.fNamespaceItem = null;
        this.fAnonymous = false;
        this.fBase = fAnySimpleType;
        this.fTypeName = str;
        this.fTargetNamespace = str2;
        this.fFinalSet = s;
        this.fAnnotations = xSObjectList;
        this.fVariety = (short) 3;
        this.fMemberTypes = xSSimpleTypeDeclArr;
        this.fValidationDV = (short) 26;
        this.fFacetsDefined = (short) 16;
        this.fWhiteSpace = (short) 2;
        calcFundamentalFacets();
        this.fIsImmutable = false;
        this.fBuiltInKind = (short) 45;
    }

    protected XSSimpleTypeDecl(XSSimpleTypeDecl xSSimpleTypeDecl, String str, String str2, short s, boolean z, XSObjectList xSObjectList) {
        this.fDVs = gDVs;
        this.fIsImmutable = false;
        this.fVariety = (short) -1;
        this.fValidationDV = (short) -1;
        this.fFacetsDefined = (short) 0;
        this.fFixedFacet = (short) 0;
        this.fWhiteSpace = (short) 0;
        this.fLength = -1;
        this.fMinLength = -1;
        this.fMaxLength = -1;
        this.fTotalDigits = -1;
        this.fFractionDigits = -1;
        this.fPatternType = (short) 0;
        this.fNamespaceItem = null;
        this.fAnonymous = false;
        this.fBase = xSSimpleTypeDecl;
        this.fTypeName = str;
        this.fTargetNamespace = str2;
        this.fFinalSet = s;
        this.fAnnotations = xSObjectList;
        short s2 = xSSimpleTypeDecl.fVariety;
        this.fVariety = s2;
        this.fValidationDV = xSSimpleTypeDecl.fValidationDV;
        if (s2 == 2) {
            this.fItemType = xSSimpleTypeDecl.fItemType;
        } else if (s2 == 3) {
            this.fMemberTypes = xSSimpleTypeDecl.fMemberTypes;
        }
        this.fLength = xSSimpleTypeDecl.fLength;
        this.fMinLength = xSSimpleTypeDecl.fMinLength;
        this.fMaxLength = xSSimpleTypeDecl.fMaxLength;
        this.fPattern = xSSimpleTypeDecl.fPattern;
        this.fPatternStr = xSSimpleTypeDecl.fPatternStr;
        this.fEnumeration = xSSimpleTypeDecl.fEnumeration;
        this.fEnumerationSize = xSSimpleTypeDecl.fEnumerationSize;
        this.fWhiteSpace = xSSimpleTypeDecl.fWhiteSpace;
        this.fMaxExclusive = xSSimpleTypeDecl.fMaxExclusive;
        this.fMaxInclusive = xSSimpleTypeDecl.fMaxInclusive;
        this.fMinExclusive = xSSimpleTypeDecl.fMinExclusive;
        this.fMinInclusive = xSSimpleTypeDecl.fMinInclusive;
        this.fTotalDigits = xSSimpleTypeDecl.fTotalDigits;
        this.fFractionDigits = xSSimpleTypeDecl.fFractionDigits;
        this.fPatternType = xSSimpleTypeDecl.fPatternType;
        this.fFixedFacet = xSSimpleTypeDecl.fFixedFacet;
        this.fFacetsDefined = xSSimpleTypeDecl.fFacetsDefined;
        this.lengthAnnotation = xSSimpleTypeDecl.lengthAnnotation;
        this.minLengthAnnotation = xSSimpleTypeDecl.minLengthAnnotation;
        this.maxLengthAnnotation = xSSimpleTypeDecl.maxLengthAnnotation;
        this.patternAnnotations = xSSimpleTypeDecl.patternAnnotations;
        this.enumerationAnnotations = xSSimpleTypeDecl.enumerationAnnotations;
        this.whiteSpaceAnnotation = xSSimpleTypeDecl.whiteSpaceAnnotation;
        this.maxExclusiveAnnotation = xSSimpleTypeDecl.maxExclusiveAnnotation;
        this.maxInclusiveAnnotation = xSSimpleTypeDecl.maxInclusiveAnnotation;
        this.minExclusiveAnnotation = xSSimpleTypeDecl.minExclusiveAnnotation;
        this.minInclusiveAnnotation = xSSimpleTypeDecl.minInclusiveAnnotation;
        this.totalDigitsAnnotation = xSSimpleTypeDecl.totalDigitsAnnotation;
        this.fractionDigitsAnnotation = xSSimpleTypeDecl.fractionDigitsAnnotation;
        calcFundamentalFacets();
        this.fIsImmutable = z;
        this.fBuiltInKind = xSSimpleTypeDecl.fBuiltInKind;
    }

    protected XSSimpleTypeDecl(XSSimpleTypeDecl xSSimpleTypeDecl, String str, String str2, short s, boolean z, XSObjectList xSObjectList, short s2) {
        this(xSSimpleTypeDecl, str, str2, s, z, xSObjectList);
        this.fBuiltInKind = s2;
    }

    protected XSSimpleTypeDecl(XSSimpleTypeDecl xSSimpleTypeDecl, String str, short s, short s2, boolean z, boolean z2, boolean z3, boolean z4, short s3) {
        this.fDVs = gDVs;
        this.fFinalSet = (short) 0;
        this.fFixedFacet = (short) 0;
        this.fWhiteSpace = (short) 0;
        this.fLength = -1;
        this.fMinLength = -1;
        this.fMaxLength = -1;
        this.fTotalDigits = -1;
        this.fFractionDigits = -1;
        this.fAnnotations = null;
        this.fPatternType = (short) 0;
        this.fNamespaceItem = null;
        this.fAnonymous = false;
        this.fIsImmutable = z4;
        this.fBase = xSSimpleTypeDecl;
        this.fTypeName = str;
        this.fTargetNamespace = "http://www.w3.org/2001/XMLSchema";
        this.fVariety = (short) 1;
        this.fValidationDV = s;
        this.fFacetsDefined = (short) 16;
        if (s == 0 || s == 29 || s == 1) {
            this.fWhiteSpace = (short) 0;
        } else {
            this.fWhiteSpace = (short) 2;
            this.fFixedFacet = (short) 16;
        }
        this.fOrdered = s2;
        this.fBounded = z;
        this.fFinite = z2;
        this.fNumeric = z3;
        this.fAnnotations = null;
        this.fBuiltInKind = s3;
    }

    private void appendEnumString(StringBuffer stringBuffer) {
        stringBuffer.append('[');
        for (int i = 0; i < this.fEnumerationSize; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(this.fEnumeration[i].actualValue);
        }
        stringBuffer.append(']');
    }

    private void calcFundamentalFacets() {
        setOrdered();
        setNumeric();
        setBounded();
        setCardinality();
    }

    private void checkExtraRules(ValidationContext validationContext, ValidatedInfo validatedInfo) throws InvalidDatatypeValueException {
        Object obj = validatedInfo.actualValue;
        short s = this.fVariety;
        if (s == 1) {
            this.fDVs[this.fValidationDV].checkExtraRules(obj, validationContext);
            return;
        }
        if (s != 2) {
            ((XSSimpleTypeDecl) validatedInfo.memberType).checkExtraRules(validationContext, validatedInfo);
            return;
        }
        ListDV.ListData listData = (ListDV.ListData) obj;
        XSSimpleType xSSimpleType = validatedInfo.memberType;
        int length = listData.getLength();
        try {
            if (this.fItemType.fVariety == 3) {
                XSSimpleTypeDecl[] xSSimpleTypeDeclArr = (XSSimpleTypeDecl[]) validatedInfo.memberTypes;
                for (int i = length - 1; i >= 0; i--) {
                    validatedInfo.actualValue = listData.item(i);
                    validatedInfo.memberType = xSSimpleTypeDeclArr[i];
                    this.fItemType.checkExtraRules(validationContext, validatedInfo);
                }
            } else {
                for (int i2 = length - 1; i2 >= 0; i2--) {
                    validatedInfo.actualValue = listData.item(i2);
                    this.fItemType.checkExtraRules(validationContext, validatedInfo);
                }
            }
            validatedInfo.actualValue = listData;
            validatedInfo.memberType = xSSimpleType;
        } catch (Throwable th) {
            validatedInfo.actualValue = listData;
            validatedInfo.memberType = xSSimpleType;
            throw th;
        }
    }

    private void checkFacets(ValidatedInfo validatedInfo) throws InvalidDatatypeValueException {
        int iCompare;
        int iCompare2;
        int totalDigits;
        int fractionDigits;
        Object obj = validatedInfo.actualValue;
        String str = validatedInfo.normalizedValue;
        short s = validatedInfo.actualValueType;
        ShortList shortList = validatedInfo.itemValueTypes;
        short s2 = this.fValidationDV;
        if (s2 != 18 && s2 != 20) {
            int dataLength = this.fDVs[s2].getDataLength(obj);
            short s3 = this.fFacetsDefined;
            if ((s3 & 4) != 0 && dataLength > this.fMaxLength) {
                throw new InvalidDatatypeValueException("cvc-maxLength-valid", new Object[]{str, Integer.toString(dataLength), Integer.toString(this.fMaxLength), this.fTypeName});
            }
            if ((s3 & 2) != 0 && dataLength < this.fMinLength) {
                throw new InvalidDatatypeValueException("cvc-minLength-valid", new Object[]{str, Integer.toString(dataLength), Integer.toString(this.fMinLength), this.fTypeName});
            }
            if ((s3 & 1) != 0 && dataLength != this.fLength) {
                throw new InvalidDatatypeValueException("cvc-length-valid", new Object[]{str, Integer.toString(dataLength), Integer.toString(this.fLength), this.fTypeName});
            }
        }
        if ((this.fFacetsDefined & 2048) != 0) {
            int i = this.fEnumerationSize;
            short sConvertToPrimitiveKind = convertToPrimitiveKind(s);
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    StringBuffer stringBuffer = new StringBuffer();
                    appendEnumString(stringBuffer);
                    throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{str, stringBuffer.toString()});
                }
                short sConvertToPrimitiveKind2 = convertToPrimitiveKind(this.fEnumeration[i2].actualValueType);
                if ((sConvertToPrimitiveKind == sConvertToPrimitiveKind2 || ((sConvertToPrimitiveKind == 1 && sConvertToPrimitiveKind2 == 2) || (sConvertToPrimitiveKind == 2 && sConvertToPrimitiveKind2 == 1))) && this.fEnumeration[i2].actualValue.equals(obj)) {
                    if (sConvertToPrimitiveKind != 44 && sConvertToPrimitiveKind != 43) {
                        break;
                    }
                    ShortList shortList2 = this.fEnumeration[i2].itemValueTypes;
                    int length = shortList != null ? shortList.getLength() : 0;
                    if (length == (shortList2 != null ? shortList2.getLength() : 0)) {
                        int i3 = 0;
                        while (i3 < length) {
                            short sConvertToPrimitiveKind3 = convertToPrimitiveKind(shortList.item(i3));
                            short sConvertToPrimitiveKind4 = convertToPrimitiveKind(shortList2.item(i3));
                            if (sConvertToPrimitiveKind3 != sConvertToPrimitiveKind4 && ((sConvertToPrimitiveKind3 != 1 || sConvertToPrimitiveKind4 != 2) && (sConvertToPrimitiveKind3 != 2 || sConvertToPrimitiveKind4 != 1))) {
                                break;
                            } else {
                                i3++;
                            }
                        }
                        if (i3 == length) {
                            break;
                        }
                    } else {
                        continue;
                    }
                }
                i2++;
            }
        }
        if ((this.fFacetsDefined & 1024) != 0 && (fractionDigits = this.fDVs[this.fValidationDV].getFractionDigits(obj)) > this.fFractionDigits) {
            throw new InvalidDatatypeValueException("cvc-fractionDigits-valid", new Object[]{str, Integer.toString(fractionDigits), Integer.toString(this.fFractionDigits)});
        }
        if ((this.fFacetsDefined & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0 && (totalDigits = this.fDVs[this.fValidationDV].getTotalDigits(obj)) > this.fTotalDigits) {
            throw new InvalidDatatypeValueException("cvc-totalDigits-valid", new Object[]{str, Integer.toString(totalDigits), Integer.toString(this.fTotalDigits)});
        }
        if ((this.fFacetsDefined & 32) != 0 && (iCompare2 = this.fDVs[this.fValidationDV].compare(obj, this.fMaxInclusive)) != -1 && iCompare2 != 0) {
            throw new InvalidDatatypeValueException("cvc-maxInclusive-valid", new Object[]{str, this.fMaxInclusive, this.fTypeName});
        }
        if ((this.fFacetsDefined & 64) != 0 && this.fDVs[this.fValidationDV].compare(obj, this.fMaxExclusive) != -1) {
            throw new InvalidDatatypeValueException("cvc-maxExclusive-valid", new Object[]{str, this.fMaxExclusive, this.fTypeName});
        }
        if ((this.fFacetsDefined & 256) != 0 && (iCompare = this.fDVs[this.fValidationDV].compare(obj, this.fMinInclusive)) != 1 && iCompare != 0) {
            throw new InvalidDatatypeValueException("cvc-minInclusive-valid", new Object[]{str, this.fMinInclusive, this.fTypeName});
        }
        if ((this.fFacetsDefined & 128) != 0 && this.fDVs[this.fValidationDV].compare(obj, this.fMinExclusive) != 1) {
            throw new InvalidDatatypeValueException("cvc-minExclusive-valid", new Object[]{str, this.fMinExclusive, this.fTypeName});
        }
    }

    private short convertToPrimitiveKind(short s) {
        if (s <= 20) {
            return s;
        }
        if (s <= 29) {
            return (short) 2;
        }
        if (s <= 42) {
            return (short) 4;
        }
        return s;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0067  */
    /* JADX WARN: Multi-variable type inference failed */
    private Object getActualValue(Object obj, ValidationContext validationContext, ValidatedInfo validatedInfo, boolean z) throws InvalidDatatypeValueException {
        XSSimpleTypeDecl xSSimpleTypeDecl;
        short s;
        Object listData;
        XSSimpleTypeDecl xSSimpleTypeDecl2;
        short s2;
        boolean zIsValidNCName;
        String strNormalize = z ? normalize(obj, this.fWhiteSpace) : obj.toString();
        if ((this.fFacetsDefined & 8) != 0) {
            for (int size = this.fPattern.size() - 1; size >= 0; size--) {
                if (!((RegularExpression) this.fPattern.elementAt(size)).matches(strNormalize)) {
                    throw new InvalidDatatypeValueException("cvc-pattern-valid", new Object[]{obj, this.fPatternStr.elementAt(size), this.fTypeName});
                }
            }
        }
        short s3 = this.fVariety;
        int i = 0;
        if (s3 == 1) {
            short s4 = this.fPatternType;
            if (s4 != 0) {
                if (s4 == 1) {
                    zIsValidNCName = XMLChar.isValidNmtoken(strNormalize);
                } else if (s4 == 2) {
                    zIsValidNCName = XMLChar.isValidName(strNormalize);
                } else {
                    if (s4 == 3) {
                        zIsValidNCName = XMLChar.isValidNCName(strNormalize);
                    }
                    if (i != 0) {
                        throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{strNormalize, SPECIAL_PATTERN_STRING[this.fPatternType]});
                    }
                }
                i = !zIsValidNCName;
                if (i != 0) {
                    throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{strNormalize, SPECIAL_PATTERN_STRING[this.fPatternType]});
                }
            }
            validatedInfo.normalizedValue = strNormalize;
            listData = this.fDVs[this.fValidationDV].getActualValue(strNormalize, validationContext);
            validatedInfo.actualValue = listData;
            validatedInfo.actualValueType = this.fBuiltInKind;
        } else if (s3 == 2) {
            StringTokenizer stringTokenizer = new StringTokenizer(strNormalize, " ");
            int iCountTokens = stringTokenizer.countTokens();
            Object[] objArr = new Object[iCountTokens];
            boolean z2 = this.fItemType.getVariety() == 3;
            int i2 = z2 ? iCountTokens : 1;
            short[] sArr = new short[i2];
            if (!z2) {
                sArr[0] = this.fItemType.fBuiltInKind;
            }
            XSSimpleTypeDecl[] xSSimpleTypeDeclArr = new XSSimpleTypeDecl[iCountTokens];
            for (int i3 = 0; i3 < iCountTokens; i3++) {
                objArr[i3] = this.fItemType.getActualValue(stringTokenizer.nextToken(), validationContext, validatedInfo, false);
                if (validationContext.needFacetChecking() && (s2 = (xSSimpleTypeDecl2 = this.fItemType).fFacetsDefined) != 0 && s2 != 16) {
                    xSSimpleTypeDecl2.checkFacets(validatedInfo);
                }
                XSSimpleTypeDecl xSSimpleTypeDecl3 = (XSSimpleTypeDecl) validatedInfo.memberType;
                xSSimpleTypeDeclArr[i3] = xSSimpleTypeDecl3;
                if (z2) {
                    sArr[i3] = xSSimpleTypeDecl3.fBuiltInKind;
                }
            }
            listData = new ListDV.ListData(objArr);
            validatedInfo.actualValue = listData;
            validatedInfo.actualValueType = z2 ? (short) 43 : (short) 44;
            validatedInfo.memberType = null;
            validatedInfo.memberTypes = xSSimpleTypeDeclArr;
            validatedInfo.itemValueTypes = new ShortListImpl(sArr, i2);
            validatedInfo.normalizedValue = strNormalize;
        } else {
            Object string = (this.fMemberTypes.length <= 1 || obj == null) ? obj : obj.toString();
            int i4 = 0;
            while (true) {
                XSSimpleTypeDecl[] xSSimpleTypeDeclArr2 = this.fMemberTypes;
                if (i4 >= xSSimpleTypeDeclArr2.length) {
                    StringBuffer stringBuffer = new StringBuffer();
                    while (i < this.fMemberTypes.length) {
                        if (i != 0) {
                            stringBuffer.append(" | ");
                        }
                        XSSimpleTypeDecl xSSimpleTypeDecl4 = this.fMemberTypes[i];
                        if (xSSimpleTypeDecl4.fTargetNamespace != null) {
                            stringBuffer.append('{');
                            stringBuffer.append(xSSimpleTypeDecl4.fTargetNamespace);
                            stringBuffer.append('}');
                        }
                        stringBuffer.append(xSSimpleTypeDecl4.fTypeName);
                        if (xSSimpleTypeDecl4.fEnumeration != null) {
                            stringBuffer.append(" : ");
                            xSSimpleTypeDecl4.appendEnumString(stringBuffer);
                        }
                        i++;
                    }
                    throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{obj, this.fTypeName, stringBuffer.toString()});
                }
                try {
                    Object actualValue = xSSimpleTypeDeclArr2[i4].getActualValue(string, validationContext, validatedInfo, true);
                    if (validationContext.needFacetChecking() && (s = (xSSimpleTypeDecl = this.fMemberTypes[i4]).fFacetsDefined) != 0 && s != 16) {
                        xSSimpleTypeDecl.checkFacets(validatedInfo);
                    }
                    validatedInfo.memberType = this.fMemberTypes[i4];
                    validatedInfo.actualType = this;
                    return actualValue;
                } catch (InvalidDatatypeValueException unused) {
                    i4++;
                }
            }
        }
        validatedInfo.actualType = this;
        return listData;
    }

    protected static TypeValidator[] getGDVs() {
        return (TypeValidator[]) gDVs.clone();
    }

    private short getPrimitiveDV(short s) {
        if (s == 21 || s == 22 || s == 23) {
            return (short) 1;
        }
        if (s == 24) {
            return (short) 3;
        }
        return s;
    }

    private boolean isDerivedByAny(String str, String str2, XSTypeDefinition xSTypeDefinition) {
        XSTypeDefinition baseType;
        xSTypeDefinition = null;
        while (xSTypeDefinition != null && xSTypeDefinition != xSTypeDefinition) {
            if (str2.equals(xSTypeDefinition.getName()) && ((str == null && xSTypeDefinition.getNamespace() == null) || (str != null && str.equals(xSTypeDefinition.getNamespace())))) {
                return true;
            }
            if (isDerivedByRestriction(str, str2, xSTypeDefinition) || isDerivedByList(str, str2, xSTypeDefinition) || isDerivedByUnion(str, str2, xSTypeDefinition)) {
                return true;
            }
            XSSimpleTypeDecl xSSimpleTypeDecl = (XSSimpleTypeDecl) xSTypeDefinition;
            if (xSSimpleTypeDecl.getVariety() == 0 || xSSimpleTypeDecl.getVariety() == 1) {
                baseType = xSTypeDefinition.getBaseType();
                xSTypeDefinition = baseType;
            } else if (xSSimpleTypeDecl.getVariety() == 3) {
                if (xSSimpleTypeDecl.getMemberTypes().getLength() > 0) {
                    return isDerivedByAny(str, str2, (XSTypeDefinition) xSSimpleTypeDecl.getMemberTypes().item(0));
                }
            } else if (xSSimpleTypeDecl.getVariety() == 2) {
                baseType = xSSimpleTypeDecl.getItemType();
                xSTypeDefinition = baseType;
            }
        }
        return false;
    }

    private boolean isDerivedByList(String str, String str2, XSTypeDefinition xSTypeDefinition) {
        XSSimpleTypeDefinition itemType;
        if (xSTypeDefinition == null) {
            return false;
        }
        XSSimpleTypeDefinition xSSimpleTypeDefinition = (XSSimpleTypeDefinition) xSTypeDefinition;
        return xSSimpleTypeDefinition.getVariety() == 2 && (itemType = xSSimpleTypeDefinition.getItemType()) != null && isDerivedByRestriction(str, str2, itemType);
    }

    private boolean isDerivedByRestriction(String str, String str2, XSTypeDefinition xSTypeDefinition) {
        XSTypeDefinition xSTypeDefinition2 = null;
        while (xSTypeDefinition != null && xSTypeDefinition != xSTypeDefinition2) {
            if (str2.equals(xSTypeDefinition.getName())) {
                if (str != null && str.equals(xSTypeDefinition.getNamespace())) {
                    return true;
                }
                if (xSTypeDefinition.getNamespace() == null && str == null) {
                    return true;
                }
            }
            xSTypeDefinition2 = xSTypeDefinition;
            xSTypeDefinition = xSTypeDefinition.getBaseType();
        }
        return false;
    }

    private boolean isDerivedByUnion(String str, String str2, XSTypeDefinition xSTypeDefinition) {
        if (xSTypeDefinition != null) {
            XSSimpleTypeDefinition xSSimpleTypeDefinition = (XSSimpleTypeDefinition) xSTypeDefinition;
            if (xSSimpleTypeDefinition.getVariety() == 3) {
                XSObjectList memberTypes = xSSimpleTypeDefinition.getMemberTypes();
                for (int i = 0; i < memberTypes.getLength(); i++) {
                    if (memberTypes.item(i) != null && isDerivedByRestriction(str, str2, (XSSimpleTypeDefinition) memberTypes.item(i))) {
                        return true;
                    }
                }
            }
        }
        return false;
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

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0016, code lost:
    
        if ((r0 & 64) == 0) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x002b, code lost:
    
        if ((r0 & 4) == 0) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setBounded() {
        /*
            r6 = this;
            short r0 = r6.fVariety
            r1 = 0
            r2 = 1
            if (r0 != r2) goto L1c
            short r0 = r6.fFacetsDefined
            r3 = r0 & 256(0x100, float:3.59E-43)
            if (r3 != 0) goto L10
            r3 = r0 & 128(0x80, float:1.8E-43)
            if (r3 == 0) goto L19
        L10:
            r3 = r0 & 32
            if (r3 != 0) goto L2d
            r0 = r0 & 64
            if (r0 == 0) goto L19
            goto L2d
        L19:
            r6.fBounded = r1
            goto L5e
        L1c:
            r3 = 2
            if (r0 != r3) goto L30
            short r0 = r6.fFacetsDefined
            r3 = r0 & 1
            if (r3 != 0) goto L2d
            r3 = r0 & 2
            if (r3 == 0) goto L19
            r0 = r0 & 4
            if (r0 == 0) goto L19
        L2d:
            r6.fBounded = r2
            goto L5e
        L30:
            r3 = 3
            if (r0 != r3) goto L5e
            org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl[] r0 = r6.fMemberTypes
            int r3 = r0.length
            if (r3 <= 0) goto L41
            r3 = r0[r1]
            short r3 = r3.fValidationDV
            short r3 = r6.getPrimitiveDV(r3)
            goto L42
        L41:
            r3 = r1
        L42:
            r4 = r1
        L43:
            int r5 = r0.length
            if (r4 >= r5) goto L2d
            r5 = r0[r4]
            boolean r5 = r5.getBounded()
            if (r5 == 0) goto L5c
            r5 = r0[r4]
            short r5 = r5.fValidationDV
            short r5 = r6.getPrimitiveDV(r5)
            if (r3 == r5) goto L59
            goto L5c
        L59:
            int r4 = r4 + 1
            goto L43
        L5c:
            r6.fBounded = r1
        L5e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl.setBounded():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0036, code lost:
    
        if (specialCardinalityCheck() == false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0048, code lost:
    
        if ((r0 & 4) == 0) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setCardinality() {
        /*
            r5 = this;
            short r0 = r5.fVariety
            r1 = 0
            r2 = 1
            if (r0 != r2) goto L39
            org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl r0 = r5.fBase
            boolean r0 = r0.fFinite
            if (r0 == 0) goto Lf
        Lc:
            r5.fFinite = r2
            goto L65
        Lf:
            short r0 = r5.fFacetsDefined
            r3 = r0 & 1
            if (r3 != 0) goto Lc
            r3 = r0 & 4
            if (r3 != 0) goto Lc
            r3 = r0 & 512(0x200, float:7.17E-43)
            if (r3 == 0) goto L1e
            goto Lc
        L1e:
            r3 = r0 & 256(0x100, float:3.59E-43)
            if (r3 != 0) goto L26
            r3 = r0 & 128(0x80, float:1.8E-43)
            if (r3 == 0) goto L4b
        L26:
            r3 = r0 & 32
            if (r3 != 0) goto L2e
            r3 = r0 & 64
            if (r3 == 0) goto L4b
        L2e:
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 != 0) goto Lc
            boolean r0 = r5.specialCardinalityCheck()
            if (r0 == 0) goto L4b
            goto Lc
        L39:
            r3 = 2
            if (r0 != r3) goto L4e
            short r0 = r5.fFacetsDefined
            r3 = r0 & 1
            if (r3 != 0) goto Lc
            r3 = r0 & 2
            if (r3 == 0) goto L4b
            r0 = r0 & 4
            if (r0 == 0) goto L4b
            goto Lc
        L4b:
            r5.fFinite = r1
            goto L65
        L4e:
            r3 = 3
            if (r0 != r3) goto L65
            org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl[] r0 = r5.fMemberTypes
            r3 = r1
        L54:
            int r4 = r0.length
            if (r3 >= r4) goto Lc
            r4 = r0[r3]
            boolean r4 = r4.getFinite()
            if (r4 != 0) goto L62
            r5.fFinite = r1
            return
        L62:
            int r3 = r3 + 1
            goto L54
        L65:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl.setCardinality():void");
    }

    private void setNumeric() {
        short s = this.fVariety;
        if (s == 1) {
            this.fNumeric = this.fBase.fNumeric;
            return;
        }
        if (s == 2) {
            this.fNumeric = false;
            return;
        }
        if (s == 3) {
            for (XSSimpleTypeDecl xSSimpleTypeDecl : this.fMemberTypes) {
                if (!xSSimpleTypeDecl.getNumeric()) {
                    this.fNumeric = false;
                    return;
                }
            }
            this.fNumeric = true;
        }
    }

    private void setOrdered() {
        XSSimpleTypeDecl[] xSSimpleTypeDeclArr;
        XSSimpleTypeDecl xSSimpleTypeDecl;
        short s = this.fVariety;
        if (s != 1) {
            if (s != 2) {
                if (s != 3) {
                    return;
                }
                XSSimpleTypeDecl[] xSSimpleTypeDeclArr2 = this.fMemberTypes;
                if (xSSimpleTypeDeclArr2.length == 0) {
                    this.fOrdered = (short) 1;
                    return;
                }
                short primitiveDV = getPrimitiveDV(xSSimpleTypeDeclArr2[0].fValidationDV);
                boolean z = primitiveDV != 0;
                boolean z2 = this.fMemberTypes[0].fOrdered == 0;
                int i = 1;
                while (true) {
                    xSSimpleTypeDeclArr = this.fMemberTypes;
                    if (i >= xSSimpleTypeDeclArr.length || !(z || z2)) {
                        break;
                    }
                    if (z) {
                        z = primitiveDV == getPrimitiveDV(xSSimpleTypeDeclArr[i].fValidationDV);
                    }
                    if (z2) {
                        z2 = this.fMemberTypes[i].fOrdered == 0;
                    }
                    i++;
                }
                if (z) {
                    xSSimpleTypeDecl = xSSimpleTypeDeclArr[0];
                } else if (!z2) {
                    this.fOrdered = (short) 1;
                    return;
                }
            }
            this.fOrdered = (short) 0;
            return;
        }
        xSSimpleTypeDecl = this.fBase;
        this.fOrdered = xSSimpleTypeDecl.fOrdered;
    }

    private boolean specialCardinalityCheck() {
        short s = this.fBase.fValidationDV;
        return s == 9 || s == 10 || s == 11 || s == 12 || s == 13 || s == 14;
    }

    private String whiteSpaceValue(short s) {
        return WS_FACET_STRING[s];
    }

    @Override // org.apache.xerces.impl.dv.XSSimpleType
    public void applyFacets(XSFacets xSFacets, short s, short s2, ValidationContext validationContext) throws InvalidDatatypeFacetException {
        if (validationContext == null) {
            validationContext = fEmptyContext;
        }
        applyFacets(xSFacets, s, s2, (short) 0, validationContext);
    }

    /* JADX WARN: Code duplicated, block: B:310:0x07b7  */
    /* JADX WARN: Code duplicated, block: B:312:0x07bf  */
    /* JADX WARN: Code duplicated, block: B:314:0x07c5  */
    /* JADX WARN: Code duplicated, block: B:315:0x07dd  */
    /* JADX WARN: Code duplicated, block: B:317:0x07e1  */
    /* JADX WARN: Code duplicated, block: B:324:0x080b  */
    /* JADX WARN: Code duplicated, block: B:327:0x0828  */
    /* JADX WARN: Code duplicated, block: B:329:0x0830  */
    /* JADX WARN: Code duplicated, block: B:331:0x0836  */
    /* JADX WARN: Code duplicated, block: B:334:0x084f  */
    /* JADX WARN: Code duplicated, block: B:336:0x0857  */
    /* JADX WARN: Code duplicated, block: B:343:0x0882  */
    /* JADX WARN: Code duplicated, block: B:346:0x089f  */
    /* JADX WARN: Code duplicated, block: B:348:0x08a7  */
    /* JADX WARN: Code duplicated, block: B:355:0x08d2  */
    /* JADX WARN: Code duplicated, block: B:358:0x08ef  */
    /* JADX WARN: Code duplicated, block: B:365:0x0918  */
    /* JADX WARN: Code duplicated, block: B:367:0x0920  */
    /* JADX WARN: Code duplicated, block: B:378:0x0955  */
    /* JADX WARN: Code duplicated, block: B:379:0x096d  */
    /* JADX WARN: Code duplicated, block: B:386:0x098c  */
    /* JADX WARN: Code duplicated, block: B:388:0x0994  */
    /* JADX WARN: Code duplicated, block: B:496:0x03e9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:498:0x0319 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    void applyFacets(XSFacets xSFacets, short s, short s2, short s3, ValidationContext validationContext) throws InvalidDatatypeFacetException {
        short s4;
        XSObjectListImpl xSObjectListImpl;
        short s5;
        int i;
        int i2;
        int i3;
        int i4;
        XSSimpleTypeDecl xSSimpleTypeDecl;
        short s6;
        XSSimpleTypeDecl xSSimpleTypeDecl2;
        int i5;
        int i6;
        XSSimpleTypeDecl xSSimpleTypeDecl3;
        int i7;
        XSSimpleTypeDecl xSSimpleTypeDecl4;
        int i8;
        int i9;
        XSSimpleTypeDecl xSSimpleTypeDecl5;
        int i10;
        int i11;
        XSSimpleTypeDecl xSSimpleTypeDecl6;
        int i12;
        int i13;
        XSSimpleTypeDecl xSSimpleTypeDecl7;
        short s7;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int iCompare;
        int iCompare2;
        int i21;
        int i22;
        Vector vector;
        RegularExpression regularExpression;
        if (this.fIsImmutable) {
            return;
        }
        ValidatedInfo validatedInfo = new ValidatedInfo();
        int i23 = 0;
        this.fFacetsDefined = (short) 0;
        this.fFixedFacet = (short) 0;
        short allowedFacets = this.fDVs[this.fValidationDV].getAllowedFacets();
        if ((s & 1) != 0) {
            if ((allowedFacets & 1) == 0) {
                reportError("cos-applicable-facets", new Object[]{"length", this.fTypeName});
            } else {
                this.fLength = xSFacets.length;
                this.lengthAnnotation = xSFacets.lengthAnnotation;
                this.fFacetsDefined = (short) (this.fFacetsDefined | 1);
                if ((s2 & 1) != 0) {
                    this.fFixedFacet = (short) (this.fFixedFacet | 1);
                }
            }
        }
        if ((s & 2) != 0) {
            if ((allowedFacets & 2) == 0) {
                reportError("cos-applicable-facets", new Object[]{"minLength", this.fTypeName});
            } else {
                this.fMinLength = xSFacets.minLength;
                this.minLengthAnnotation = xSFacets.minLengthAnnotation;
                this.fFacetsDefined = (short) (this.fFacetsDefined | 2);
                if ((s2 & 2) != 0) {
                    this.fFixedFacet = (short) (this.fFixedFacet | 2);
                }
            }
        }
        if ((s & 4) != 0) {
            if ((allowedFacets & 4) == 0) {
                reportError("cos-applicable-facets", new Object[]{StageActivity.STYLE_MAX_LENGTH, this.fTypeName});
            } else {
                this.fMaxLength = xSFacets.maxLength;
                this.maxLengthAnnotation = xSFacets.maxLengthAnnotation;
                this.fFacetsDefined = (short) (this.fFacetsDefined | 4);
                if ((s2 & 4) != 0) {
                    this.fFixedFacet = (short) (this.fFixedFacet | 4);
                }
            }
        }
        if ((s & 8) != 0) {
            if ((allowedFacets & 8) == 0) {
                reportError("cos-applicable-facets", new Object[]{"pattern", this.fTypeName});
            } else {
                this.patternAnnotations = xSFacets.patternAnnotations;
                try {
                    regularExpression = new RegularExpression(xSFacets.pattern, SpriteActivity.EXTRA_X_TRANSFORM, validationContext.getLocale());
                } catch (Exception e) {
                    reportError("InvalidRegex", new Object[]{xSFacets.pattern, e.getLocalizedMessage()});
                    regularExpression = null;
                }
                if (regularExpression != null) {
                    Vector vector2 = new Vector();
                    this.fPattern = vector2;
                    vector2.addElement(regularExpression);
                    Vector vector3 = new Vector();
                    this.fPatternStr = vector3;
                    vector3.addElement(xSFacets.pattern);
                    this.fFacetsDefined = (short) (this.fFacetsDefined | 8);
                    if ((s2 & 8) != 0) {
                        this.fFixedFacet = (short) (this.fFixedFacet | 8);
                    }
                }
            }
        }
        if ((s & 16) != 0) {
            if ((allowedFacets & 16) == 0) {
                reportError("cos-applicable-facets", new Object[]{"whiteSpace", this.fTypeName});
            } else {
                this.fWhiteSpace = xSFacets.whiteSpace;
                this.whiteSpaceAnnotation = xSFacets.whiteSpaceAnnotation;
                this.fFacetsDefined = (short) (this.fFacetsDefined | 16);
                if ((s2 & 16) != 0) {
                    this.fFixedFacet = (short) (this.fFixedFacet | 16);
                }
            }
        }
        if ((s & 2048) != 0) {
            if ((allowedFacets & 2048) == 0) {
                reportError("cos-applicable-facets", new Object[]{"enumeration", this.fTypeName});
            } else {
                Vector vector4 = xSFacets.enumeration;
                int size = vector4.size();
                this.fEnumeration = new ValidatedInfo[size];
                Vector vector5 = xSFacets.enumNSDecls;
                ValidationContextImpl validationContextImpl = new ValidationContextImpl(validationContext);
                this.enumerationAnnotations = xSFacets.enumAnnotations;
                this.fEnumerationSize = 0;
                while (i23 < size) {
                    if (vector5 != null) {
                        validationContextImpl.setNSContext((NamespaceContext) vector5.elementAt(i23));
                    }
                    try {
                        ValidatedInfo actualEnumValue = getActualEnumValue((String) vector4.elementAt(i23), validationContextImpl, null);
                        ValidatedInfo[] validatedInfoArr = this.fEnumeration;
                        i22 = size;
                        try {
                            int i24 = this.fEnumerationSize;
                            vector = vector5;
                            try {
                                this.fEnumerationSize = i24 + 1;
                                validatedInfoArr[i24] = actualEnumValue;
                            } catch (InvalidDatatypeValueException unused) {
                                reportError("enumeration-valid-restriction", new Object[]{vector4.elementAt(i23), getBaseType().getName()});
                            }
                        } catch (InvalidDatatypeValueException unused2) {
                            vector = vector5;
                            reportError("enumeration-valid-restriction", new Object[]{vector4.elementAt(i23), getBaseType().getName()});
                            i23++;
                            size = i22;
                            vector5 = vector;
                        }
                    } catch (InvalidDatatypeValueException unused3) {
                        i22 = size;
                    }
                    i23++;
                    size = i22;
                    vector5 = vector;
                }
                this.fFacetsDefined = (short) (this.fFacetsDefined | 2048);
                if ((s2 & 2048) != 0) {
                    this.fFixedFacet = (short) (this.fFixedFacet | 2048);
                }
            }
        }
        if ((s & 32) != 0) {
            if ((allowedFacets & 32) == 0) {
                reportError("cos-applicable-facets", new Object[]{"maxInclusive", this.fTypeName});
            } else {
                this.maxInclusiveAnnotation = xSFacets.maxInclusiveAnnotation;
                try {
                    this.fMaxInclusive = this.fBase.getActualValue(xSFacets.maxInclusive, validationContext, validatedInfo, true);
                    this.fFacetsDefined = (short) (this.fFacetsDefined | 32);
                    if ((s2 & 32) != 0) {
                        this.fFixedFacet = (short) (this.fFixedFacet | 32);
                    }
                } catch (InvalidDatatypeValueException e2) {
                    reportError(e2.getKey(), e2.getArgs());
                    reportError("FacetValueFromBase", new Object[]{this.fTypeName, xSFacets.maxInclusive, "maxInclusive", this.fBase.getName()});
                }
                XSSimpleTypeDecl xSSimpleTypeDecl8 = this.fBase;
                if ((xSSimpleTypeDecl8.fFacetsDefined & 32) != 0 && (xSSimpleTypeDecl8.fFixedFacet & 32) != 0 && this.fDVs[this.fValidationDV].compare(this.fMaxInclusive, xSSimpleTypeDecl8.fMaxInclusive) != 0) {
                    reportError("FixedFacetValue", new Object[]{"maxInclusive", this.fMaxInclusive, this.fBase.fMaxInclusive, this.fTypeName});
                }
                try {
                    this.fBase.validate(validationContext, validatedInfo);
                } catch (InvalidDatatypeValueException e3) {
                    reportError(e3.getKey(), e3.getArgs());
                    reportError("FacetValueFromBase", new Object[]{this.fTypeName, xSFacets.maxInclusive, "maxInclusive", this.fBase.getName()});
                }
            }
        }
        if ((s & 64) != 0) {
            if ((allowedFacets & 64) == 0) {
                reportError("cos-applicable-facets", new Object[]{"maxExclusive", this.fTypeName});
            } else {
                this.maxExclusiveAnnotation = xSFacets.maxExclusiveAnnotation;
                try {
                    this.fMaxExclusive = this.fBase.getActualValue(xSFacets.maxExclusive, validationContext, validatedInfo, true);
                    this.fFacetsDefined = (short) (this.fFacetsDefined | 64);
                    if ((s2 & 64) != 0) {
                        this.fFixedFacet = (short) (this.fFixedFacet | 64);
                    }
                } catch (InvalidDatatypeValueException e4) {
                    reportError(e4.getKey(), e4.getArgs());
                    reportError("FacetValueFromBase", new Object[]{this.fTypeName, xSFacets.maxExclusive, "maxExclusive", this.fBase.getName()});
                }
                XSSimpleTypeDecl xSSimpleTypeDecl9 = this.fBase;
                if ((xSSimpleTypeDecl9.fFacetsDefined & 64) != 0) {
                    int iCompare3 = this.fDVs[this.fValidationDV].compare(this.fMaxExclusive, xSSimpleTypeDecl9.fMaxExclusive);
                    if ((this.fBase.fFixedFacet & 64) != 0 && iCompare3 != 0) {
                        reportError("FixedFacetValue", new Object[]{"maxExclusive", xSFacets.maxExclusive, this.fBase.fMaxExclusive, this.fTypeName});
                    }
                    if (iCompare3 == 0) {
                        XSSimpleTypeDecl xSSimpleTypeDecl10 = this.fBase;
                        if ((xSSimpleTypeDecl10.fFacetsDefined & 32) != 0 && this.fDVs[this.fValidationDV].compare(this.fMaxExclusive, xSSimpleTypeDecl10.fMaxInclusive) > 0) {
                            reportError("maxExclusive-valid-restriction.2", new Object[]{xSFacets.maxExclusive, this.fBase.fMaxInclusive});
                        }
                    } else {
                        try {
                            this.fBase.validate(validationContext, validatedInfo);
                        } catch (InvalidDatatypeValueException e5) {
                            reportError(e5.getKey(), e5.getArgs());
                            reportError("FacetValueFromBase", new Object[]{this.fTypeName, xSFacets.maxExclusive, "maxExclusive", this.fBase.getName()});
                        }
                    }
                } else {
                    this.fBase.validate(validationContext, validatedInfo);
                }
            }
        }
        if ((s & 128) != 0) {
            if ((allowedFacets & 128) == 0) {
                reportError("cos-applicable-facets", new Object[]{"minExclusive", this.fTypeName});
            } else {
                this.minExclusiveAnnotation = xSFacets.minExclusiveAnnotation;
                try {
                    this.fMinExclusive = this.fBase.getActualValue(xSFacets.minExclusive, validationContext, validatedInfo, true);
                    this.fFacetsDefined = (short) (this.fFacetsDefined | 128);
                    if ((s2 & 128) != 0) {
                        this.fFixedFacet = (short) (this.fFixedFacet | 128);
                    }
                } catch (InvalidDatatypeValueException e6) {
                    reportError(e6.getKey(), e6.getArgs());
                    reportError("FacetValueFromBase", new Object[]{this.fTypeName, xSFacets.minExclusive, "minExclusive", this.fBase.getName()});
                }
                XSSimpleTypeDecl xSSimpleTypeDecl11 = this.fBase;
                if ((xSSimpleTypeDecl11.fFacetsDefined & 128) != 0) {
                    int iCompare4 = this.fDVs[this.fValidationDV].compare(this.fMinExclusive, xSSimpleTypeDecl11.fMinExclusive);
                    if ((this.fBase.fFixedFacet & 128) != 0 && iCompare4 != 0) {
                        reportError("FixedFacetValue", new Object[]{"minExclusive", xSFacets.minExclusive, this.fBase.fMinExclusive, this.fTypeName});
                    }
                    if (iCompare4 == 0) {
                        XSSimpleTypeDecl xSSimpleTypeDecl12 = this.fBase;
                        if ((xSSimpleTypeDecl12.fFacetsDefined & 256) != 0 && this.fDVs[this.fValidationDV].compare(this.fMinExclusive, xSSimpleTypeDecl12.fMinInclusive) < 0) {
                            reportError("minExclusive-valid-restriction.3", new Object[]{xSFacets.minExclusive, this.fBase.fMinInclusive});
                        }
                    } else {
                        try {
                            this.fBase.validate(validationContext, validatedInfo);
                        } catch (InvalidDatatypeValueException e7) {
                            reportError(e7.getKey(), e7.getArgs());
                            reportError("FacetValueFromBase", new Object[]{this.fTypeName, xSFacets.minExclusive, "minExclusive", this.fBase.getName()});
                        }
                    }
                } else {
                    this.fBase.validate(validationContext, validatedInfo);
                }
            }
        }
        if ((s & 256) != 0) {
            if ((allowedFacets & 256) == 0) {
                reportError("cos-applicable-facets", new Object[]{"minInclusive", this.fTypeName});
            } else {
                this.minInclusiveAnnotation = xSFacets.minInclusiveAnnotation;
                try {
                    this.fMinInclusive = this.fBase.getActualValue(xSFacets.minInclusive, validationContext, validatedInfo, true);
                    this.fFacetsDefined = (short) (this.fFacetsDefined | 256);
                    if ((s2 & 256) != 0) {
                        this.fFixedFacet = (short) (this.fFixedFacet | 256);
                    }
                } catch (InvalidDatatypeValueException e8) {
                    reportError(e8.getKey(), e8.getArgs());
                    reportError("FacetValueFromBase", new Object[]{this.fTypeName, xSFacets.minInclusive, "minInclusive", this.fBase.getName()});
                }
                XSSimpleTypeDecl xSSimpleTypeDecl13 = this.fBase;
                if ((xSSimpleTypeDecl13.fFacetsDefined & 256) != 0 && (xSSimpleTypeDecl13.fFixedFacet & 256) != 0 && this.fDVs[this.fValidationDV].compare(this.fMinInclusive, xSSimpleTypeDecl13.fMinInclusive) != 0) {
                    reportError("FixedFacetValue", new Object[]{"minInclusive", xSFacets.minInclusive, this.fBase.fMinInclusive, this.fTypeName});
                }
                try {
                    this.fBase.validate(validationContext, validatedInfo);
                } catch (InvalidDatatypeValueException e9) {
                    reportError(e9.getKey(), e9.getArgs());
                    reportError("FacetValueFromBase", new Object[]{this.fTypeName, xSFacets.minInclusive, "minInclusive", this.fBase.getName()});
                }
            }
        }
        if ((s & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0) {
            if ((allowedFacets & XSSimpleTypeDefinition.FACET_TOTALDIGITS) == 0) {
                reportError("cos-applicable-facets", new Object[]{"totalDigits", this.fTypeName});
            } else {
                this.totalDigitsAnnotation = xSFacets.totalDigitsAnnotation;
                this.fTotalDigits = xSFacets.totalDigits;
                this.fFacetsDefined = (short) (this.fFacetsDefined | XSSimpleTypeDefinition.FACET_TOTALDIGITS);
                if ((s2 & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0) {
                    this.fFixedFacet = (short) (this.fFixedFacet | XSSimpleTypeDefinition.FACET_TOTALDIGITS);
                }
            }
        }
        if ((s & 1024) != 0) {
            if ((allowedFacets & 1024) == 0) {
                reportError("cos-applicable-facets", new Object[]{"fractionDigits", this.fTypeName});
            } else {
                this.fFractionDigits = xSFacets.fractionDigits;
                this.fractionDigitsAnnotation = xSFacets.fractionDigitsAnnotation;
                this.fFacetsDefined = (short) (this.fFacetsDefined | 1024);
                if ((s2 & 1024) != 0) {
                    this.fFixedFacet = (short) (this.fFixedFacet | 1024);
                }
            }
        }
        if (s3 != 0) {
            this.fPatternType = s3;
        }
        short s8 = this.fFacetsDefined;
        if (s8 != 0) {
            if ((s8 & 2) != 0 && (s8 & 4) != 0 && (i21 = this.fMinLength) > this.fMaxLength) {
                reportError("minLength-less-than-equal-to-maxLength", new Object[]{Integer.toString(i21), Integer.toString(this.fMaxLength), this.fTypeName});
            }
            short s9 = this.fFacetsDefined;
            if ((s9 & 64) != 0 && (s9 & 32) != 0) {
                reportError("maxInclusive-maxExclusive", new Object[]{this.fMaxInclusive, this.fMaxExclusive, this.fTypeName});
            }
            short s10 = this.fFacetsDefined;
            if ((s10 & 128) != 0 && (s10 & 256) != 0) {
                reportError("minInclusive-minExclusive", new Object[]{this.fMinInclusive, this.fMinExclusive, this.fTypeName});
            }
            short s11 = this.fFacetsDefined;
            if ((s11 & 32) != 0 && (s11 & 256) != 0 && (iCompare2 = this.fDVs[this.fValidationDV].compare(this.fMinInclusive, this.fMaxInclusive)) != -1 && iCompare2 != 0) {
                reportError("minInclusive-less-than-equal-to-maxInclusive", new Object[]{this.fMinInclusive, this.fMaxInclusive, this.fTypeName});
            }
            short s12 = this.fFacetsDefined;
            if ((s12 & 64) != 0 && (s12 & 128) != 0 && (iCompare = this.fDVs[this.fValidationDV].compare(this.fMinExclusive, this.fMaxExclusive)) != -1 && iCompare != 0) {
                reportError("minExclusive-less-than-equal-to-maxExclusive", new Object[]{this.fMinExclusive, this.fMaxExclusive, this.fTypeName});
            }
            short s13 = this.fFacetsDefined;
            if ((s13 & 32) != 0 && (s13 & 128) != 0 && this.fDVs[this.fValidationDV].compare(this.fMinExclusive, this.fMaxInclusive) != -1) {
                reportError("minExclusive-less-than-maxInclusive", new Object[]{this.fMinExclusive, this.fMaxInclusive, this.fTypeName});
            }
            short s14 = this.fFacetsDefined;
            if ((s14 & 64) != 0 && (s14 & 256) != 0 && this.fDVs[this.fValidationDV].compare(this.fMinInclusive, this.fMaxExclusive) != -1) {
                reportError("minInclusive-less-than-maxExclusive", new Object[]{this.fMinInclusive, this.fMaxExclusive, this.fTypeName});
            }
            short s15 = this.fFacetsDefined;
            if ((s15 & 1024) != 0 && (s15 & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0 && (i20 = this.fFractionDigits) > this.fTotalDigits) {
                reportError("fractionDigits-totalDigits", new Object[]{Integer.toString(i20), Integer.toString(this.fTotalDigits), this.fTypeName});
            }
            if ((this.fFacetsDefined & 1) != 0) {
                XSSimpleTypeDecl xSSimpleTypeDecl14 = this.fBase;
                if ((xSSimpleTypeDecl14.fFacetsDefined & 2) != 0 && (i19 = this.fLength) < xSSimpleTypeDecl14.fMinLength) {
                    reportError("length-minLength-maxLength.1.1", new Object[]{this.fTypeName, Integer.toString(i19), Integer.toString(this.fBase.fMinLength)});
                }
                XSSimpleTypeDecl xSSimpleTypeDecl15 = this.fBase;
                if ((xSSimpleTypeDecl15.fFacetsDefined & 4) != 0 && (i18 = this.fLength) > xSSimpleTypeDecl15.fMaxLength) {
                    reportError("length-minLength-maxLength.2.1", new Object[]{this.fTypeName, Integer.toString(i18), Integer.toString(this.fBase.fMaxLength)});
                }
                XSSimpleTypeDecl xSSimpleTypeDecl16 = this.fBase;
                if ((xSSimpleTypeDecl16.fFacetsDefined & 1) != 0 && (i17 = this.fLength) != xSSimpleTypeDecl16.fLength) {
                    reportError("length-valid-restriction", new Object[]{Integer.toString(i17), Integer.toString(this.fBase.fLength), this.fTypeName});
                }
            }
            XSSimpleTypeDecl xSSimpleTypeDecl17 = this.fBase;
            short s16 = xSSimpleTypeDecl17.fFacetsDefined;
            if ((s16 & 1) == 0) {
                s5 = 1;
                if ((this.fFacetsDefined & 1) != 0) {
                }
                if ((this.fFacetsDefined & 2) != 0) {
                    xSSimpleTypeDecl7 = this.fBase;
                    s7 = xSSimpleTypeDecl7.fFacetsDefined;
                    if ((s7 & 4) != 0) {
                        i16 = this.fMinLength;
                        if (i16 > xSSimpleTypeDecl7.fMaxLength) {
                            reportError("minLength-less-than-equal-to-maxLength", new Object[]{Integer.toString(i16), Integer.toString(this.fBase.fMaxLength), this.fTypeName});
                        }
                    } else if ((s7 & 2) != 0) {
                        if ((xSSimpleTypeDecl7.fFixedFacet & 2) != 0 && (i15 = this.fMinLength) != xSSimpleTypeDecl7.fMinLength) {
                            reportError("FixedFacetValue", new Object[]{"minLength", Integer.toString(i15), Integer.toString(this.fBase.fMinLength), this.fTypeName});
                        }
                        i14 = this.fMinLength;
                        if (i14 < this.fBase.fMinLength) {
                            reportError("minLength-valid-restriction", new Object[]{Integer.toString(i14), Integer.toString(this.fBase.fMinLength), this.fTypeName});
                        }
                    }
                }
                if ((this.fFacetsDefined & 4) != 0) {
                    xSSimpleTypeDecl6 = this.fBase;
                    if ((xSSimpleTypeDecl6.fFacetsDefined & 2) != 0) {
                        i12 = this.fMaxLength;
                        i13 = xSSimpleTypeDecl6.fMinLength;
                        if (i12 < i13) {
                            reportError("minLength-less-than-equal-to-maxLength", new Object[]{Integer.toString(i13), Integer.toString(this.fMaxLength)});
                        }
                    }
                }
                if ((this.fFacetsDefined & 4) != 0) {
                    xSSimpleTypeDecl5 = this.fBase;
                    if ((xSSimpleTypeDecl5.fFacetsDefined & 4) != 0) {
                        if ((xSSimpleTypeDecl5.fFixedFacet & 4) != 0 && (i11 = this.fMaxLength) != xSSimpleTypeDecl5.fMaxLength) {
                            reportError("FixedFacetValue", new Object[]{StageActivity.STYLE_MAX_LENGTH, Integer.toString(i11), Integer.toString(this.fBase.fMaxLength), this.fTypeName});
                        }
                        i10 = this.fMaxLength;
                        if (i10 > this.fBase.fMaxLength) {
                            reportError("maxLength-valid-restriction", new Object[]{Integer.toString(i10), Integer.toString(this.fBase.fMaxLength), this.fTypeName});
                        }
                    }
                }
                if ((this.fFacetsDefined & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0) {
                    xSSimpleTypeDecl4 = this.fBase;
                    if ((xSSimpleTypeDecl4.fFacetsDefined & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0) {
                        if ((xSSimpleTypeDecl4.fFixedFacet & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0 && (i9 = this.fTotalDigits) != xSSimpleTypeDecl4.fTotalDigits) {
                            reportError("FixedFacetValue", new Object[]{"totalDigits", Integer.toString(i9), Integer.toString(this.fBase.fTotalDigits), this.fTypeName});
                        }
                        i8 = this.fTotalDigits;
                        if (i8 > this.fBase.fTotalDigits) {
                            reportError("totalDigits-valid-restriction", new Object[]{Integer.toString(i8), Integer.toString(this.fBase.fTotalDigits), this.fTypeName});
                        }
                    }
                }
                if ((this.fFacetsDefined & 1024) != 0) {
                    xSSimpleTypeDecl3 = this.fBase;
                    if ((xSSimpleTypeDecl3.fFacetsDefined & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0 && (i7 = this.fFractionDigits) > xSSimpleTypeDecl3.fTotalDigits) {
                        reportError("fractionDigits-totalDigits", new Object[]{Integer.toString(i7), Integer.toString(this.fTotalDigits), this.fTypeName});
                    }
                }
                if ((this.fFacetsDefined & 1024) != 0) {
                    xSSimpleTypeDecl2 = this.fBase;
                    if ((xSSimpleTypeDecl2.fFacetsDefined & 1024) != 0) {
                        if (((xSSimpleTypeDecl2.fFixedFacet & 1024) == 0 && this.fFractionDigits != xSSimpleTypeDecl2.fFractionDigits) || (this.fValidationDV == 24 && this.fFractionDigits != 0)) {
                            reportError("FixedFacetValue", new Object[]{"fractionDigits", Integer.toString(this.fFractionDigits), Integer.toString(this.fBase.fFractionDigits), this.fTypeName});
                        }
                        i6 = this.fFractionDigits;
                        if (i6 > this.fBase.fFractionDigits) {
                            reportError("fractionDigits-valid-restriction", new Object[]{Integer.toString(i6), Integer.toString(this.fBase.fFractionDigits), this.fTypeName});
                        }
                    } else if (this.fValidationDV == 24 && (i5 = this.fFractionDigits) != 0) {
                        reportError("FixedFacetValue", new Object[]{"fractionDigits", Integer.toString(i5), "0", this.fTypeName});
                    }
                }
                if ((this.fFacetsDefined & 16) != 0) {
                    xSSimpleTypeDecl = this.fBase;
                    if ((xSSimpleTypeDecl.fFacetsDefined & 16) != 0) {
                        if ((xSSimpleTypeDecl.fFixedFacet & 16) != 0 && (s6 = this.fWhiteSpace) != xSSimpleTypeDecl.fWhiteSpace) {
                            reportError("FixedFacetValue", new Object[]{"whiteSpace", whiteSpaceValue(s6), whiteSpaceValue(this.fBase.fWhiteSpace), this.fTypeName});
                        }
                        if (this.fWhiteSpace == 0 && this.fBase.fWhiteSpace == 2) {
                            reportError("whiteSpace-valid-restriction.1", new Object[]{this.fTypeName, "preserve"});
                        }
                        if (this.fWhiteSpace == 1 && this.fBase.fWhiteSpace == 2) {
                            reportError("whiteSpace-valid-restriction.1", new Object[]{this.fTypeName, SchemaSymbols.ATTVAL_REPLACE});
                        }
                        if (this.fWhiteSpace == 0 && this.fBase.fWhiteSpace == 1) {
                            reportError("whiteSpace-valid-restriction.2", new Object[]{this.fTypeName});
                        }
                    }
                }
            } else {
                s5 = 1;
            }
            if ((this.fFacetsDefined & 2) != 0) {
                if ((s16 & s5) != 0 && (i4 = xSSimpleTypeDecl17.fLength) < this.fMinLength) {
                    reportError("length-minLength-maxLength.1.1", new Object[]{this.fTypeName, Integer.toString(i4), Integer.toString(this.fMinLength)});
                }
                if ((this.fFacetsDefined & 1) != 0 && (i3 = this.fLength) < this.fMinLength) {
                    reportError("length-minLength-maxLength.1.1", new Object[]{this.fTypeName, Integer.toString(i3), Integer.toString(this.fMinLength)});
                }
                if ((this.fBase.fFacetsDefined & 2) == 0) {
                    reportError("length-minLength-maxLength.1.2.a", new Object[]{this.fTypeName});
                }
                int i25 = this.fMinLength;
                if (i25 != this.fBase.fMinLength) {
                    reportError("length-minLength-maxLength.1.2.b", new Object[]{this.fTypeName, Integer.toString(i25), Integer.toString(this.fBase.fMinLength)});
                }
            }
            if ((this.fFacetsDefined & 4) != 0) {
                XSSimpleTypeDecl xSSimpleTypeDecl18 = this.fBase;
                if ((xSSimpleTypeDecl18.fFacetsDefined & 1) != 0 && (i2 = xSSimpleTypeDecl18.fLength) > this.fMaxLength) {
                    reportError("length-minLength-maxLength.2.1", new Object[]{this.fTypeName, Integer.toString(i2), Integer.toString(this.fMaxLength)});
                }
                if ((this.fFacetsDefined & 1) != 0 && (i = this.fLength) > this.fMaxLength) {
                    reportError("length-minLength-maxLength.2.1", new Object[]{this.fTypeName, Integer.toString(i), Integer.toString(this.fMaxLength)});
                }
                if ((this.fBase.fFacetsDefined & 4) == 0) {
                    reportError("length-minLength-maxLength.2.2.a", new Object[]{this.fTypeName});
                }
                int i26 = this.fMaxLength;
                if (i26 != this.fBase.fMaxLength) {
                    reportError("length-minLength-maxLength.2.2.b", new Object[]{this.fTypeName, Integer.toString(i26), Integer.toString(this.fBase.fBase.fMaxLength)});
                }
            }
            if ((this.fFacetsDefined & 2) != 0) {
                xSSimpleTypeDecl7 = this.fBase;
                s7 = xSSimpleTypeDecl7.fFacetsDefined;
                if ((s7 & 4) != 0) {
                    i16 = this.fMinLength;
                    if (i16 > xSSimpleTypeDecl7.fMaxLength) {
                        reportError("minLength-less-than-equal-to-maxLength", new Object[]{Integer.toString(i16), Integer.toString(this.fBase.fMaxLength), this.fTypeName});
                    }
                } else if ((s7 & 2) != 0) {
                    if ((xSSimpleTypeDecl7.fFixedFacet & 2) != 0) {
                        reportError("FixedFacetValue", new Object[]{"minLength", Integer.toString(i15), Integer.toString(this.fBase.fMinLength), this.fTypeName});
                    }
                    i14 = this.fMinLength;
                    if (i14 < this.fBase.fMinLength) {
                        reportError("minLength-valid-restriction", new Object[]{Integer.toString(i14), Integer.toString(this.fBase.fMinLength), this.fTypeName});
                    }
                }
            }
            if ((this.fFacetsDefined & 4) != 0) {
                xSSimpleTypeDecl6 = this.fBase;
                if ((xSSimpleTypeDecl6.fFacetsDefined & 2) != 0) {
                    i12 = this.fMaxLength;
                    i13 = xSSimpleTypeDecl6.fMinLength;
                    if (i12 < i13) {
                        reportError("minLength-less-than-equal-to-maxLength", new Object[]{Integer.toString(i13), Integer.toString(this.fMaxLength)});
                    }
                }
            }
            if ((this.fFacetsDefined & 4) != 0) {
                xSSimpleTypeDecl5 = this.fBase;
                if ((xSSimpleTypeDecl5.fFacetsDefined & 4) != 0) {
                    if ((xSSimpleTypeDecl5.fFixedFacet & 4) != 0) {
                        reportError("FixedFacetValue", new Object[]{StageActivity.STYLE_MAX_LENGTH, Integer.toString(i11), Integer.toString(this.fBase.fMaxLength), this.fTypeName});
                    }
                    i10 = this.fMaxLength;
                    if (i10 > this.fBase.fMaxLength) {
                        reportError("maxLength-valid-restriction", new Object[]{Integer.toString(i10), Integer.toString(this.fBase.fMaxLength), this.fTypeName});
                    }
                }
            }
            if ((this.fFacetsDefined & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0) {
                xSSimpleTypeDecl4 = this.fBase;
                if ((xSSimpleTypeDecl4.fFacetsDefined & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0) {
                    if ((xSSimpleTypeDecl4.fFixedFacet & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0) {
                        reportError("FixedFacetValue", new Object[]{"totalDigits", Integer.toString(i9), Integer.toString(this.fBase.fTotalDigits), this.fTypeName});
                    }
                    i8 = this.fTotalDigits;
                    if (i8 > this.fBase.fTotalDigits) {
                        reportError("totalDigits-valid-restriction", new Object[]{Integer.toString(i8), Integer.toString(this.fBase.fTotalDigits), this.fTypeName});
                    }
                }
            }
            if ((this.fFacetsDefined & 1024) != 0) {
                xSSimpleTypeDecl3 = this.fBase;
                if ((xSSimpleTypeDecl3.fFacetsDefined & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0) {
                    reportError("fractionDigits-totalDigits", new Object[]{Integer.toString(i7), Integer.toString(this.fTotalDigits), this.fTypeName});
                }
            }
            if ((this.fFacetsDefined & 1024) != 0) {
                xSSimpleTypeDecl2 = this.fBase;
                if ((xSSimpleTypeDecl2.fFacetsDefined & 1024) != 0) {
                    if ((xSSimpleTypeDecl2.fFixedFacet & 1024) == 0) {
                        reportError("FixedFacetValue", new Object[]{"fractionDigits", Integer.toString(this.fFractionDigits), Integer.toString(this.fBase.fFractionDigits), this.fTypeName});
                    } else {
                        reportError("FixedFacetValue", new Object[]{"fractionDigits", Integer.toString(this.fFractionDigits), Integer.toString(this.fBase.fFractionDigits), this.fTypeName});
                    }
                    i6 = this.fFractionDigits;
                    if (i6 > this.fBase.fFractionDigits) {
                        reportError("fractionDigits-valid-restriction", new Object[]{Integer.toString(i6), Integer.toString(this.fBase.fFractionDigits), this.fTypeName});
                    }
                } else if (this.fValidationDV == 24) {
                    reportError("FixedFacetValue", new Object[]{"fractionDigits", Integer.toString(i5), "0", this.fTypeName});
                }
            }
            if ((this.fFacetsDefined & 16) != 0) {
                xSSimpleTypeDecl = this.fBase;
                if ((xSSimpleTypeDecl.fFacetsDefined & 16) != 0) {
                    if ((xSSimpleTypeDecl.fFixedFacet & 16) != 0) {
                        reportError("FixedFacetValue", new Object[]{"whiteSpace", whiteSpaceValue(s6), whiteSpaceValue(this.fBase.fWhiteSpace), this.fTypeName});
                    }
                    if (this.fWhiteSpace == 0) {
                        reportError("whiteSpace-valid-restriction.1", new Object[]{this.fTypeName, "preserve"});
                    }
                    if (this.fWhiteSpace == 1) {
                        reportError("whiteSpace-valid-restriction.1", new Object[]{this.fTypeName, SchemaSymbols.ATTVAL_REPLACE});
                    }
                    if (this.fWhiteSpace == 0) {
                        reportError("whiteSpace-valid-restriction.2", new Object[]{this.fTypeName});
                    }
                }
            }
        }
        short s17 = this.fFacetsDefined;
        if ((s17 & 1) == 0) {
            XSSimpleTypeDecl xSSimpleTypeDecl19 = this.fBase;
            if ((xSSimpleTypeDecl19.fFacetsDefined & 1) != 0) {
                this.fFacetsDefined = (short) (s17 | 1);
                this.fLength = xSSimpleTypeDecl19.fLength;
                this.lengthAnnotation = xSSimpleTypeDecl19.lengthAnnotation;
            }
        }
        short s18 = this.fFacetsDefined;
        if ((s18 & 2) == 0) {
            XSSimpleTypeDecl xSSimpleTypeDecl20 = this.fBase;
            if ((xSSimpleTypeDecl20.fFacetsDefined & 2) != 0) {
                this.fFacetsDefined = (short) (s18 | 2);
                this.fMinLength = xSSimpleTypeDecl20.fMinLength;
                this.minLengthAnnotation = xSSimpleTypeDecl20.minLengthAnnotation;
            }
        }
        short s19 = this.fFacetsDefined;
        if ((s19 & 4) == 0) {
            XSSimpleTypeDecl xSSimpleTypeDecl21 = this.fBase;
            if ((xSSimpleTypeDecl21.fFacetsDefined & 4) != 0) {
                this.fFacetsDefined = (short) (s19 | 4);
                this.fMaxLength = xSSimpleTypeDecl21.fMaxLength;
                this.maxLengthAnnotation = xSSimpleTypeDecl21.maxLengthAnnotation;
            }
        }
        XSSimpleTypeDecl xSSimpleTypeDecl22 = this.fBase;
        if ((xSSimpleTypeDecl22.fFacetsDefined & 8) != 0) {
            short s20 = this.fFacetsDefined;
            if ((s20 & 8) == 0) {
                this.fFacetsDefined = (short) (s20 | 8);
                this.fPattern = xSSimpleTypeDecl22.fPattern;
                this.fPatternStr = xSSimpleTypeDecl22.fPatternStr;
                xSObjectListImpl = xSSimpleTypeDecl22.patternAnnotations;
            } else {
                for (int size2 = xSSimpleTypeDecl22.fPattern.size() - 1; size2 >= 0; size2--) {
                    this.fPattern.addElement(this.fBase.fPattern.elementAt(size2));
                    this.fPatternStr.addElement(this.fBase.fPatternStr.elementAt(size2));
                }
                xSObjectListImpl = this.fBase.patternAnnotations;
                if (xSObjectListImpl != null) {
                    if (this.patternAnnotations != null) {
                        for (int length = xSObjectListImpl.getLength() - 1; length >= 0; length--) {
                            this.patternAnnotations.addXSObject(this.fBase.patternAnnotations.item(length));
                        }
                    }
                }
            }
            this.patternAnnotations = xSObjectListImpl;
        }
        short s21 = this.fFacetsDefined;
        if ((s21 & 16) == 0) {
            XSSimpleTypeDecl xSSimpleTypeDecl23 = this.fBase;
            if ((xSSimpleTypeDecl23.fFacetsDefined & 16) != 0) {
                this.fFacetsDefined = (short) (s21 | 16);
                this.fWhiteSpace = xSSimpleTypeDecl23.fWhiteSpace;
                this.whiteSpaceAnnotation = xSSimpleTypeDecl23.whiteSpaceAnnotation;
            }
        }
        short s22 = this.fFacetsDefined;
        if ((s22 & 2048) == 0) {
            XSSimpleTypeDecl xSSimpleTypeDecl24 = this.fBase;
            if ((xSSimpleTypeDecl24.fFacetsDefined & 2048) != 0) {
                this.fFacetsDefined = (short) (s22 | 2048);
                this.fEnumeration = xSSimpleTypeDecl24.fEnumeration;
                this.fEnumerationSize = xSSimpleTypeDecl24.fEnumerationSize;
                this.enumerationAnnotations = xSSimpleTypeDecl24.enumerationAnnotations;
            }
        }
        XSSimpleTypeDecl xSSimpleTypeDecl25 = this.fBase;
        if ((xSSimpleTypeDecl25.fFacetsDefined & 64) != 0) {
            short s23 = this.fFacetsDefined;
            if ((s23 & 64) == 0 && (s23 & 32) == 0) {
                this.fFacetsDefined = (short) (s23 | 64);
                this.fMaxExclusive = xSSimpleTypeDecl25.fMaxExclusive;
                this.maxExclusiveAnnotation = xSSimpleTypeDecl25.maxExclusiveAnnotation;
            }
        }
        if ((xSSimpleTypeDecl25.fFacetsDefined & 32) != 0) {
            short s24 = this.fFacetsDefined;
            if ((s24 & 64) == 0 && (s24 & 32) == 0) {
                this.fFacetsDefined = (short) (s24 | 32);
                this.fMaxInclusive = xSSimpleTypeDecl25.fMaxInclusive;
                this.maxInclusiveAnnotation = xSSimpleTypeDecl25.maxInclusiveAnnotation;
            }
        }
        if ((xSSimpleTypeDecl25.fFacetsDefined & 128) != 0) {
            short s25 = this.fFacetsDefined;
            if ((s25 & 128) == 0 && (s25 & 256) == 0) {
                this.fFacetsDefined = (short) (s25 | 128);
                this.fMinExclusive = xSSimpleTypeDecl25.fMinExclusive;
                this.minExclusiveAnnotation = xSSimpleTypeDecl25.minExclusiveAnnotation;
            }
        }
        if ((xSSimpleTypeDecl25.fFacetsDefined & 256) != 0) {
            short s26 = this.fFacetsDefined;
            if ((s26 & 128) == 0 && (s26 & 256) == 0) {
                this.fFacetsDefined = (short) (s26 | 256);
                this.fMinInclusive = xSSimpleTypeDecl25.fMinInclusive;
                this.minInclusiveAnnotation = xSSimpleTypeDecl25.minInclusiveAnnotation;
            }
        }
        if ((xSSimpleTypeDecl25.fFacetsDefined & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0) {
            short s27 = this.fFacetsDefined;
            if ((s27 & XSSimpleTypeDefinition.FACET_TOTALDIGITS) == 0) {
                this.fFacetsDefined = (short) (s27 | XSSimpleTypeDefinition.FACET_TOTALDIGITS);
                this.fTotalDigits = xSSimpleTypeDecl25.fTotalDigits;
                this.totalDigitsAnnotation = xSSimpleTypeDecl25.totalDigitsAnnotation;
            }
        }
        if ((xSSimpleTypeDecl25.fFacetsDefined & 1024) != 0) {
            short s28 = this.fFacetsDefined;
            if ((s28 & 1024) == 0) {
                this.fFacetsDefined = (short) (s28 | 1024);
                this.fFractionDigits = xSSimpleTypeDecl25.fFractionDigits;
                this.fractionDigitsAnnotation = xSSimpleTypeDecl25.fractionDigitsAnnotation;
            }
        }
        if (this.fPatternType == 0 && (s4 = xSSimpleTypeDecl25.fPatternType) != 0) {
            this.fPatternType = s4;
        }
        this.fFixedFacet = (short) (xSSimpleTypeDecl25.fFixedFacet | this.fFixedFacet);
        calcFundamentalFacets();
    }

    void applyFacets1(XSFacets xSFacets, short s, short s2) {
        try {
            applyFacets(xSFacets, s, s2, (short) 0, fDummyContext);
            this.fIsImmutable = true;
        } catch (InvalidDatatypeFacetException unused) {
            throw new RuntimeException("internal error");
        }
    }

    void applyFacets1(XSFacets xSFacets, short s, short s2, short s3) {
        try {
            applyFacets(xSFacets, s, s2, s3, fDummyContext);
            this.fIsImmutable = true;
        } catch (InvalidDatatypeFacetException unused) {
            throw new RuntimeException("internal error");
        }
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public boolean derivedFrom(String str, String str2, short s) {
        if (str2 == null) {
            return false;
        }
        if ("http://www.w3.org/2001/XMLSchema".equals(str) && "anyType".equals(str2)) {
            return true;
        }
        XSTypeDefinition baseType = this;
        while (true) {
            if ((str2.equals(baseType.getName()) && ((str == null && baseType.getNamespace() == null) || (str != null && str.equals(baseType.getNamespace())))) || baseType == fAnySimpleType) {
                break;
            }
            baseType = baseType.getBaseType();
        }
        return baseType != fAnySimpleType;
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public boolean derivedFromType(XSTypeDefinition xSTypeDefinition, short s) {
        if (xSTypeDefinition == null) {
            return false;
        }
        while (xSTypeDefinition instanceof XSSimpleTypeDelegate) {
            xSTypeDefinition = ((XSSimpleTypeDelegate) xSTypeDefinition).type;
        }
        if (xSTypeDefinition.getBaseType() == xSTypeDefinition) {
            return true;
        }
        XSTypeDefinition baseType = this;
        while (baseType != xSTypeDefinition && baseType != fAnySimpleType) {
            baseType = baseType.getBaseType();
        }
        return baseType == xSTypeDefinition;
    }

    protected ValidatedInfo getActualEnumValue(String str, ValidationContext validationContext, ValidatedInfo validatedInfo) throws InvalidDatatypeValueException {
        return this.fBase.validateWithInfo(str, validationContext, validatedInfo);
    }

    public ObjectList getActualEnumeration() {
        if (this.fActualEnumeration == null) {
            this.fActualEnumeration = new AbstractObjectList() { // from class: org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl.2
                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, org.apache.xerces.xs.datatypes.ObjectList
                public boolean contains(Object obj) {
                    if (XSSimpleTypeDecl.this.fEnumeration == null) {
                        return false;
                    }
                    for (int i = 0; i < XSSimpleTypeDecl.this.fEnumerationSize; i++) {
                        if (XSSimpleTypeDecl.this.fEnumeration[i].getActualValue().equals(obj)) {
                            return true;
                        }
                    }
                    return false;
                }

                @Override // org.apache.xerces.xs.datatypes.ObjectList
                public int getLength() {
                    if (XSSimpleTypeDecl.this.fEnumeration != null) {
                        return XSSimpleTypeDecl.this.fEnumerationSize;
                    }
                    return 0;
                }

                @Override // org.apache.xerces.xs.datatypes.ObjectList
                public Object item(int i) {
                    if (i < 0 || i >= getLength()) {
                        return null;
                    }
                    return XSSimpleTypeDecl.this.fEnumeration[i].getActualValue();
                }
            };
        }
        return this.fActualEnumeration;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public XSObjectList getAnnotations() {
        XSObjectList xSObjectList = this.fAnnotations;
        return xSObjectList != null ? xSObjectList : XSObjectListImpl.EMPTY_LIST;
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public boolean getAnonymous() {
        return this.fAnonymous || this.fTypeName == null;
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public XSTypeDefinition getBaseType() {
        return this.fBase;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public boolean getBounded() {
        return this.fBounded;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public short getBuiltInKind() {
        return this.fBuiltInKind;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public short getDefinedFacets() {
        int i;
        short s = this.fValidationDV;
        if (s == 0 || s == 29) {
            return (short) 0;
        }
        if (this.fPatternType != 0) {
            i = this.fFacetsDefined | 8;
        } else {
            if (s != 24) {
                return this.fFacetsDefined;
            }
            i = this.fFacetsDefined | 1032;
        }
        return (short) i;
    }

    public ObjectList getEnumerationItemTypeList() {
        if (this.fEnumerationItemTypeList == null) {
            if (this.fEnumeration == null) {
                return null;
            }
            this.fEnumerationItemTypeList = new AbstractObjectList() { // from class: org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl.3
                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, org.apache.xerces.xs.datatypes.ObjectList
                public boolean contains(Object obj) {
                    if (XSSimpleTypeDecl.this.fEnumeration != null && (obj instanceof ShortList)) {
                        for (int i = 0; i < XSSimpleTypeDecl.this.fEnumerationSize; i++) {
                            if (XSSimpleTypeDecl.this.fEnumeration[i].itemValueTypes == obj) {
                                return true;
                            }
                        }
                    }
                    return false;
                }

                @Override // org.apache.xerces.xs.datatypes.ObjectList
                public int getLength() {
                    if (XSSimpleTypeDecl.this.fEnumeration != null) {
                        return XSSimpleTypeDecl.this.fEnumerationSize;
                    }
                    return 0;
                }

                @Override // org.apache.xerces.xs.datatypes.ObjectList
                public Object item(int i) {
                    if (i < 0 || i >= getLength()) {
                        return null;
                    }
                    return XSSimpleTypeDecl.this.fEnumeration[i].itemValueTypes;
                }
            };
        }
        return this.fEnumerationItemTypeList;
    }

    public ShortList getEnumerationTypeList() {
        if (this.fEnumerationTypeList == null) {
            if (this.fEnumeration == null) {
                return ShortListImpl.EMPTY_LIST;
            }
            short[] sArr = new short[this.fEnumerationSize];
            for (int i = 0; i < this.fEnumerationSize; i++) {
                sArr[i] = this.fEnumeration[i].actualValueType;
            }
            this.fEnumerationTypeList = new ShortListImpl(sArr, this.fEnumerationSize);
        }
        return this.fEnumerationTypeList;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public XSObject getFacet(int i) {
        int i2 = 0;
        if (i == 2048 || i == 8) {
            XSObjectList multiValueFacets = getMultiValueFacets();
            while (i2 < multiValueFacets.getLength()) {
                XSMultiValueFacet xSMultiValueFacet = (XSMultiValueFacet) multiValueFacets.item(i2);
                if (xSMultiValueFacet.getFacetKind() == i) {
                    return xSMultiValueFacet;
                }
                i2++;
            }
            return null;
        }
        XSObjectList facets = getFacets();
        while (i2 < facets.getLength()) {
            XSFacet xSFacet = (XSFacet) facets.item(i2);
            if (xSFacet.getFacetKind() == i) {
                return xSFacet;
            }
            i2++;
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:61:0x010c  */
    /* JADX WARN: Code duplicated, block: B:63:0x011c  */
    /* JADX WARN: Code duplicated, block: B:64:0x011e  */
    /* JADX WARN: Code duplicated, block: B:68:0x0130  */
    /* JADX WARN: Code duplicated, block: B:70:0x0140  */
    /* JADX WARN: Code duplicated, block: B:71:0x0142  */
    /* JADX WARN: Code duplicated, block: B:75:0x0154  */
    /* JADX WARN: Code duplicated, block: B:77:0x0164  */
    /* JADX WARN: Code duplicated, block: B:78:0x0166  */
    /* JADX WARN: Code duplicated, block: B:82:0x0178  */
    /* JADX WARN: Code duplicated, block: B:84:0x0188  */
    /* JADX WARN: Code duplicated, block: B:85:0x018a  */
    /* JADX WARN: Code duplicated, block: B:88:0x019a  */
    /* JADX WARN: Code duplicated, block: B:89:0x01a0  */
    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public XSObjectList getFacets() {
        short s;
        int i;
        XSObjectListImpl xSObjectListImpl;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        short s2;
        if (this.fFacets == null && ((s = this.fFacetsDefined) != 0 || this.fValidationDV == 24)) {
            XSFacetImpl[] xSFacetImplArr = new XSFacetImpl[10];
            if ((s & 16) == 0 || (s2 = this.fValidationDV) == 0 || s2 == 29) {
                i = 0;
            } else {
                xSFacetImplArr[0] = new XSFacetImpl((short) 16, WS_FACET_STRING[this.fWhiteSpace], 0, null, (this.fFixedFacet & 16) != 0, this.whiteSpaceAnnotation);
                i = 1;
            }
            if (this.fLength != -1) {
                xSFacetImplArr[i] = new XSFacetImpl((short) 1, Integer.toString(this.fLength), this.fLength, null, (this.fFixedFacet & 1) != 0, this.lengthAnnotation);
                i++;
            }
            if (this.fMinLength != -1) {
                xSFacetImplArr[i] = new XSFacetImpl((short) 2, Integer.toString(this.fMinLength), this.fMinLength, null, (this.fFixedFacet & 2) != 0, this.minLengthAnnotation);
                i++;
            }
            if (this.fMaxLength != -1) {
                xSFacetImplArr[i] = new XSFacetImpl((short) 4, Integer.toString(this.fMaxLength), this.fMaxLength, null, (this.fFixedFacet & 4) != 0, this.maxLengthAnnotation);
                i++;
            }
            if (this.fTotalDigits != -1) {
                xSFacetImplArr[i] = new XSFacetImpl(XSSimpleTypeDefinition.FACET_TOTALDIGITS, Integer.toString(this.fTotalDigits), this.fTotalDigits, null, (this.fFixedFacet & XSSimpleTypeDefinition.FACET_TOTALDIGITS) != 0, this.totalDigitsAnnotation);
                i++;
            }
            if (this.fValidationDV == 24) {
                xSFacetImplArr[i] = new XSFacetImpl((short) 1024, "0", 0, null, true, this.fractionDigitsAnnotation);
            } else {
                if (this.fFractionDigits != -1) {
                    xSFacetImplArr[i] = new XSFacetImpl((short) 1024, Integer.toString(this.fFractionDigits), this.fFractionDigits, null, (this.fFixedFacet & 1024) != 0, this.fractionDigitsAnnotation);
                }
                if (this.fMaxInclusive != null) {
                    String string = this.fMaxInclusive.toString();
                    Object obj = this.fMaxInclusive;
                    if ((this.fFixedFacet & 32) != 0) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    xSFacetImplArr[i] = new XSFacetImpl((short) 32, string, 0, obj, z4, this.maxInclusiveAnnotation);
                    i++;
                }
                if (this.fMaxExclusive != null) {
                    String string2 = this.fMaxExclusive.toString();
                    Object obj2 = this.fMaxExclusive;
                    if ((this.fFixedFacet & 64) != 0) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    xSFacetImplArr[i] = new XSFacetImpl((short) 64, string2, 0, obj2, z3, this.maxExclusiveAnnotation);
                    i++;
                }
                if (this.fMinExclusive != null) {
                    String string3 = this.fMinExclusive.toString();
                    Object obj3 = this.fMinExclusive;
                    if ((this.fFixedFacet & 128) != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    xSFacetImplArr[i] = new XSFacetImpl((short) 128, string3, 0, obj3, z2, this.minExclusiveAnnotation);
                    i++;
                }
                if (this.fMinInclusive != null) {
                    String string4 = this.fMinInclusive.toString();
                    Object obj4 = this.fMinInclusive;
                    if ((this.fFixedFacet & 256) != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    xSFacetImplArr[i] = new XSFacetImpl((short) 256, string4, 0, obj4, z, this.minInclusiveAnnotation);
                    i++;
                }
                if (i > 0) {
                    xSObjectListImpl = new XSObjectListImpl(xSFacetImplArr, i);
                } else {
                    xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
                }
                this.fFacets = xSObjectListImpl;
            }
            i++;
            if (this.fMaxInclusive != null) {
                String string5 = this.fMaxInclusive.toString();
                Object obj5 = this.fMaxInclusive;
                if ((this.fFixedFacet & 32) != 0) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                xSFacetImplArr[i] = new XSFacetImpl((short) 32, string5, 0, obj5, z4, this.maxInclusiveAnnotation);
                i++;
            }
            if (this.fMaxExclusive != null) {
                String string6 = this.fMaxExclusive.toString();
                Object obj6 = this.fMaxExclusive;
                if ((this.fFixedFacet & 64) != 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                xSFacetImplArr[i] = new XSFacetImpl((short) 64, string6, 0, obj6, z3, this.maxExclusiveAnnotation);
                i++;
            }
            if (this.fMinExclusive != null) {
                String string7 = this.fMinExclusive.toString();
                Object obj7 = this.fMinExclusive;
                if ((this.fFixedFacet & 128) != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                xSFacetImplArr[i] = new XSFacetImpl((short) 128, string7, 0, obj7, z2, this.minExclusiveAnnotation);
                i++;
            }
            if (this.fMinInclusive != null) {
                String string8 = this.fMinInclusive.toString();
                Object obj8 = this.fMinInclusive;
                if ((this.fFixedFacet & 256) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                xSFacetImplArr[i] = new XSFacetImpl((short) 256, string8, 0, obj8, z, this.minInclusiveAnnotation);
                i++;
            }
            if (i > 0) {
                xSObjectListImpl = new XSObjectListImpl(xSFacetImplArr, i);
            } else {
                xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
            }
            this.fFacets = xSObjectListImpl;
        }
        XSObjectListImpl xSObjectListImpl2 = this.fFacets;
        return xSObjectListImpl2 != null ? xSObjectListImpl2 : XSObjectListImpl.EMPTY_LIST;
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public short getFinal() {
        return this.fFinalSet;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public boolean getFinite() {
        return this.fFinite;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public short getFixedFacets() {
        return this.fValidationDV == 24 ? (short) (this.fFixedFacet | 1024) : this.fFixedFacet;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public XSSimpleTypeDefinition getItemType() {
        if (this.fVariety == 2) {
            return this.fItemType;
        }
        return null;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public StringList getLexicalEnumeration() {
        if (this.fLexicalEnumeration == null) {
            if (this.fEnumeration == null) {
                return StringListImpl.EMPTY_LIST;
            }
            int i = this.fEnumerationSize;
            String[] strArr = new String[i];
            for (int i2 = 0; i2 < i; i2++) {
                strArr[i2] = this.fEnumeration[i2].normalizedValue;
            }
            this.fLexicalEnumeration = new StringListImpl(strArr, i);
        }
        return this.fLexicalEnumeration;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public String getLexicalFacetValue(short s) {
        if (s == 1) {
            int i = this.fLength;
            if (i == -1) {
                return null;
            }
            return Integer.toString(i);
        }
        if (s == 2) {
            int i2 = this.fMinLength;
            if (i2 == -1) {
                return null;
            }
            return Integer.toString(i2);
        }
        if (s == 4) {
            int i3 = this.fMaxLength;
            if (i3 == -1) {
                return null;
            }
            return Integer.toString(i3);
        }
        if (s == 16) {
            short s2 = this.fValidationDV;
            if (s2 == 0 || s2 == 29) {
                return null;
            }
            return WS_FACET_STRING[this.fWhiteSpace];
        }
        if (s == 32) {
            Object obj = this.fMaxInclusive;
            if (obj == null) {
                return null;
            }
            return obj.toString();
        }
        if (s == 64) {
            Object obj2 = this.fMaxExclusive;
            if (obj2 == null) {
                return null;
            }
            return obj2.toString();
        }
        if (s == 128) {
            Object obj3 = this.fMinExclusive;
            if (obj3 == null) {
                return null;
            }
            return obj3.toString();
        }
        if (s == 256) {
            Object obj4 = this.fMinInclusive;
            if (obj4 == null) {
                return null;
            }
            return obj4.toString();
        }
        if (s == 512) {
            int i4 = this.fTotalDigits;
            if (i4 == -1) {
                return null;
            }
            return Integer.toString(i4);
        }
        if (s != 1024) {
            return null;
        }
        if (this.fValidationDV == 24) {
            return "0";
        }
        int i5 = this.fFractionDigits;
        if (i5 == -1) {
            return null;
        }
        return Integer.toString(i5);
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public StringList getLexicalPattern() {
        String[] strArr;
        if (this.fPatternType == 0 && this.fValidationDV != 24 && this.fPatternStr == null) {
            return StringListImpl.EMPTY_LIST;
        }
        if (this.fLexicalPattern == null) {
            Vector vector = this.fPatternStr;
            int size = vector == null ? 0 : vector.size();
            short s = this.fPatternType;
            if (s == 1) {
                strArr = new String[size + 1];
                strArr[size] = "\\c+";
            } else if (s == 2) {
                strArr = new String[size + 1];
                strArr[size] = "\\i\\c*";
            } else if (s == 3) {
                strArr = new String[size + 2];
                strArr[size] = "\\i\\c*";
                strArr[size + 1] = "[\\i-[:]][\\c-[:]]*";
            } else if (this.fValidationDV == 24) {
                strArr = new String[size + 1];
                strArr[size] = "[\\-+]?[0-9]+";
            } else {
                strArr = new String[size];
            }
            for (int i = 0; i < size; i++) {
                strArr[i] = (String) this.fPatternStr.elementAt(i);
            }
            this.fLexicalPattern = new StringListImpl(strArr, strArr.length);
        }
        return this.fLexicalPattern;
    }

    public Object getMaxExclusiveValue() {
        return this.fMaxExclusive;
    }

    public Object getMaxInclusiveValue() {
        return this.fMaxInclusive;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public XSObjectList getMemberTypes() {
        if (this.fVariety != 3) {
            return XSObjectListImpl.EMPTY_LIST;
        }
        XSSimpleTypeDecl[] xSSimpleTypeDeclArr = this.fMemberTypes;
        return new XSObjectListImpl(xSSimpleTypeDeclArr, xSSimpleTypeDeclArr.length);
    }

    public Object getMinExclusiveValue() {
        return this.fMinExclusive;
    }

    public Object getMinInclusiveValue() {
        return this.fMinInclusive;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public XSObjectList getMultiValueFacets() {
        if (this.fMultiValueFacets == null) {
            short s = this.fFacetsDefined;
            if ((s & 2048) != 0 || (s & 8) != 0 || this.fPatternType != 0 || this.fValidationDV == 24) {
                XSMVFacetImpl[] xSMVFacetImplArr = new XSMVFacetImpl[2];
                int i = 0;
                if ((s & 8) != 0 || this.fPatternType != 0 || this.fValidationDV == 24) {
                    xSMVFacetImplArr[0] = new XSMVFacetImpl((short) 8, getLexicalPattern(), null, this.patternAnnotations);
                    i = 1;
                }
                if (this.fEnumeration != null) {
                    xSMVFacetImplArr[i] = new XSMVFacetImpl((short) 2048, getLexicalEnumeration(), new ObjectListImpl(this.fEnumeration, this.fEnumerationSize), this.enumerationAnnotations);
                    i++;
                }
                this.fMultiValueFacets = new XSObjectListImpl(xSMVFacetImplArr, i);
            }
        }
        XSObjectListImpl xSObjectListImpl = this.fMultiValueFacets;
        return xSObjectListImpl != null ? xSObjectListImpl : XSObjectListImpl.EMPTY_LIST;
    }

    @Override // org.apache.xerces.xs.XSObject
    public String getName() {
        if (getAnonymous()) {
            return null;
        }
        return this.fTypeName;
    }

    @Override // org.apache.xerces.xs.XSObject
    public String getNamespace() {
        return this.fTargetNamespace;
    }

    @Override // org.apache.xerces.xs.XSObject
    public XSNamespaceItem getNamespaceItem() {
        return this.fNamespaceItem;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public boolean getNumeric() {
        return this.fNumeric;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public short getOrdered() {
        return this.fOrdered;
    }

    @Override // org.apache.xerces.impl.dv.XSSimpleType
    public short getPrimitiveKind() {
        short s;
        if (this.fVariety != 1 || (s = this.fValidationDV) == 0) {
            return (short) 0;
        }
        if (s == 21 || s == 22 || s == 23) {
            return (short) 1;
        }
        if (s == 24) {
            return (short) 3;
        }
        return s;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public XSSimpleTypeDefinition getPrimitiveType() {
        if (this.fVariety != 1 || this.fValidationDV == 0) {
            return null;
        }
        XSSimpleTypeDecl xSSimpleTypeDecl = this;
        while (true) {
            XSSimpleTypeDecl xSSimpleTypeDecl2 = xSSimpleTypeDecl.fBase;
            if (xSSimpleTypeDecl2 == fAnySimpleType) {
                return xSSimpleTypeDecl;
            }
            xSSimpleTypeDecl = xSSimpleTypeDecl2;
        }
    }

    @Override // org.apache.xerces.xs.XSObject
    public short getType() {
        return (short) 3;
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public short getTypeCategory() {
        return (short) 16;
    }

    @Override // org.w3c.dom.TypeInfo
    public String getTypeName() {
        return this.fTypeName;
    }

    @Override // org.w3c.dom.TypeInfo
    public String getTypeNamespace() {
        return getNamespace();
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public short getVariety() {
        if (this.fValidationDV == 0) {
            return (short) 0;
        }
        return this.fVariety;
    }

    @Override // org.apache.xerces.impl.dv.XSSimpleType
    public short getWhitespace() throws DatatypeException {
        if (this.fVariety != 3) {
            return this.fWhiteSpace;
        }
        throw new DatatypeException("dt-whitespace", new Object[]{this.fTypeName});
    }

    public boolean isDOMDerivedFrom(String str, String str2, int i) {
        if (str2 == null) {
            return false;
        }
        if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(str) && "anyType".equals(str2) && ((i & 1) != 0 || i == 0)) {
            return true;
        }
        int i2 = i & 1;
        if (i2 != 0 && isDerivedByRestriction(str, str2, this)) {
            return true;
        }
        int i3 = i & 8;
        if (i3 != 0 && isDerivedByList(str, str2, this)) {
            return true;
        }
        int i4 = i & 4;
        if (i4 != 0 && isDerivedByUnion(str, str2, this)) {
            return true;
        }
        int i5 = i & 2;
        if (!(i5 != 0 && i2 == 0 && i3 == 0 && i4 == 0) && i5 == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return isDerivedByAny(str, str2, this);
        }
        return false;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public boolean isDefinedFacet(short s) {
        short s2 = this.fValidationDV;
        if (s2 == 0 || s2 == 29) {
            return false;
        }
        if ((this.fFacetsDefined & s) != 0) {
            return true;
        }
        if (this.fPatternType != 0) {
            return s == 8;
        }
        if (s2 == 24) {
            return s == 8 || s == 1024;
        }
        return false;
    }

    @Override // org.w3c.dom.TypeInfo
    public boolean isDerivedFrom(String str, String str2, int i) {
        return isDOMDerivedFrom(str, str2, i);
    }

    @Override // org.apache.xerces.impl.dv.XSSimpleType
    public boolean isEqual(Object obj, Object obj2) {
        if (obj == null) {
            return false;
        }
        return obj.equals(obj2);
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public boolean isFinal(short s) {
        return (s & this.fFinalSet) != 0;
    }

    @Override // org.apache.xerces.xs.XSSimpleTypeDefinition
    public boolean isFixedFacet(short s) {
        if ((this.fFixedFacet & s) != 0) {
            return true;
        }
        return this.fValidationDV == 24 && s == 1024;
    }

    @Override // org.apache.xerces.impl.dv.XSSimpleType
    public boolean isIDType() {
        short s = this.fVariety;
        if (s == 1) {
            return this.fValidationDV == 21;
        }
        if (s == 2) {
            return this.fItemType.isIDType();
        }
        if (s == 3) {
            int i = 0;
            while (true) {
                XSSimpleTypeDecl[] xSSimpleTypeDeclArr = this.fMemberTypes;
                if (i >= xSSimpleTypeDeclArr.length) {
                    break;
                }
                if (xSSimpleTypeDeclArr[i].isIDType()) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    public boolean isIdentical(Object obj, Object obj2) {
        if (obj == null) {
            return false;
        }
        return this.fDVs[this.fValidationDV].isIdentical(obj, obj2);
    }

    protected String normalize(Object obj, short s) {
        int i;
        int i2;
        char cCharAt;
        if (obj == null) {
            return null;
        }
        if ((this.fFacetsDefined & 8) == 0) {
            short s2 = fDVNormalizeType[this.fValidationDV];
            if (s2 == 0) {
                return obj.toString();
            }
            if (s2 == 1) {
                return XMLChar.trim(obj.toString());
            }
        }
        if (!(obj instanceof StringBuffer)) {
            return normalize(obj.toString(), s);
        }
        StringBuffer stringBuffer = (StringBuffer) obj;
        int length = stringBuffer.length();
        if (length == 0) {
            return "";
        }
        if (s == 0) {
            return stringBuffer.toString();
        }
        if (s == 1) {
            for (int i3 = 0; i3 < length; i3++) {
                char cCharAt2 = stringBuffer.charAt(i3);
                if (cCharAt2 == '\t' || cCharAt2 == '\n' || cCharAt2 == '\r') {
                    stringBuffer.setCharAt(i3, StringUtil.SPACE);
                }
            }
        } else {
            boolean z = true;
            int i4 = 0;
            int i5 = 0;
            while (i4 < length) {
                char cCharAt3 = stringBuffer.charAt(i4);
                if (cCharAt3 == '\t' || cCharAt3 == '\n' || cCharAt3 == '\r' || cCharAt3 == ' ') {
                    while (true) {
                        i = length - 1;
                        if (i4 >= i || !((cCharAt = stringBuffer.charAt((i2 = i4 + 1))) == '\t' || cCharAt == '\n' || cCharAt == '\r' || cCharAt == ' ')) {
                            break;
                        }
                        i4 = i2;
                    }
                    if (i4 < i && !z) {
                        stringBuffer.setCharAt(i5, StringUtil.SPACE);
                        i5++;
                    }
                } else {
                    stringBuffer.setCharAt(i5, cCharAt3);
                    i5++;
                    z = false;
                }
                i4++;
            }
            stringBuffer.setLength(i5);
        }
        return stringBuffer.toString();
    }

    void reportError(String str, Object[] objArr) throws InvalidDatatypeFacetException {
        throw new InvalidDatatypeFacetException(str, objArr);
    }

    public void reset() {
        if (this.fIsImmutable) {
            return;
        }
        this.fItemType = null;
        this.fMemberTypes = null;
        this.fTypeName = null;
        this.fTargetNamespace = null;
        this.fFinalSet = (short) 0;
        this.fBase = null;
        this.fVariety = (short) -1;
        this.fValidationDV = (short) -1;
        this.fFacetsDefined = (short) 0;
        this.fFixedFacet = (short) 0;
        this.fWhiteSpace = (short) 0;
        this.fLength = -1;
        this.fMinLength = -1;
        this.fMaxLength = -1;
        this.fTotalDigits = -1;
        this.fFractionDigits = -1;
        this.fPattern = null;
        this.fPatternStr = null;
        this.fEnumeration = null;
        this.fLexicalPattern = null;
        this.fLexicalEnumeration = null;
        this.fActualEnumeration = null;
        this.fEnumerationTypeList = null;
        this.fEnumerationItemTypeList = null;
        this.fMaxInclusive = null;
        this.fMaxExclusive = null;
        this.fMinExclusive = null;
        this.fMinInclusive = null;
        this.lengthAnnotation = null;
        this.minLengthAnnotation = null;
        this.maxLengthAnnotation = null;
        this.whiteSpaceAnnotation = null;
        this.totalDigitsAnnotation = null;
        this.fractionDigitsAnnotation = null;
        this.patternAnnotations = null;
        this.enumerationAnnotations = null;
        this.maxInclusiveAnnotation = null;
        this.maxExclusiveAnnotation = null;
        this.minInclusiveAnnotation = null;
        this.minExclusiveAnnotation = null;
        this.fPatternType = (short) 0;
        this.fAnnotations = null;
        this.fFacets = null;
    }

    public void setAnonymous(boolean z) {
        this.fAnonymous = z;
    }

    protected void setDVs(TypeValidator[] typeValidatorArr) {
        this.fDVs = typeValidatorArr;
    }

    protected XSSimpleTypeDecl setListValues(String str, String str2, short s, XSSimpleTypeDecl xSSimpleTypeDecl, XSObjectList xSObjectList) {
        if (this.fIsImmutable) {
            return null;
        }
        this.fBase = fAnySimpleType;
        this.fAnonymous = false;
        this.fTypeName = str;
        this.fTargetNamespace = str2;
        this.fFinalSet = s;
        this.fAnnotations = xSObjectList;
        this.fVariety = (short) 2;
        this.fItemType = xSSimpleTypeDecl;
        this.fValidationDV = (short) 25;
        this.fFacetsDefined = (short) 16;
        this.fFixedFacet = (short) 16;
        this.fWhiteSpace = (short) 2;
        calcFundamentalFacets();
        this.fBuiltInKind = (short) 44;
        return this;
    }

    public void setNamespaceItem(XSNamespaceItem xSNamespaceItem) {
        this.fNamespaceItem = xSNamespaceItem;
    }

    protected XSSimpleTypeDecl setRestrictionValues(XSSimpleTypeDecl xSSimpleTypeDecl, String str, String str2, short s, XSObjectList xSObjectList) {
        if (this.fIsImmutable) {
            return null;
        }
        this.fBase = xSSimpleTypeDecl;
        this.fAnonymous = false;
        this.fTypeName = str;
        this.fTargetNamespace = str2;
        this.fFinalSet = s;
        this.fAnnotations = xSObjectList;
        short s2 = xSSimpleTypeDecl.fVariety;
        this.fVariety = s2;
        this.fValidationDV = xSSimpleTypeDecl.fValidationDV;
        if (s2 == 2) {
            this.fItemType = xSSimpleTypeDecl.fItemType;
        } else if (s2 == 3) {
            this.fMemberTypes = xSSimpleTypeDecl.fMemberTypes;
        }
        this.fLength = xSSimpleTypeDecl.fLength;
        this.fMinLength = xSSimpleTypeDecl.fMinLength;
        this.fMaxLength = xSSimpleTypeDecl.fMaxLength;
        this.fPattern = xSSimpleTypeDecl.fPattern;
        this.fPatternStr = xSSimpleTypeDecl.fPatternStr;
        this.fEnumeration = xSSimpleTypeDecl.fEnumeration;
        this.fEnumerationSize = xSSimpleTypeDecl.fEnumerationSize;
        this.fWhiteSpace = xSSimpleTypeDecl.fWhiteSpace;
        this.fMaxExclusive = xSSimpleTypeDecl.fMaxExclusive;
        this.fMaxInclusive = xSSimpleTypeDecl.fMaxInclusive;
        this.fMinExclusive = xSSimpleTypeDecl.fMinExclusive;
        this.fMinInclusive = xSSimpleTypeDecl.fMinInclusive;
        this.fTotalDigits = xSSimpleTypeDecl.fTotalDigits;
        this.fFractionDigits = xSSimpleTypeDecl.fFractionDigits;
        this.fPatternType = xSSimpleTypeDecl.fPatternType;
        this.fFixedFacet = xSSimpleTypeDecl.fFixedFacet;
        this.fFacetsDefined = xSSimpleTypeDecl.fFacetsDefined;
        calcFundamentalFacets();
        this.fBuiltInKind = xSSimpleTypeDecl.fBuiltInKind;
        return this;
    }

    protected XSSimpleTypeDecl setUnionValues(String str, String str2, short s, XSSimpleTypeDecl[] xSSimpleTypeDeclArr, XSObjectList xSObjectList) {
        if (this.fIsImmutable) {
            return null;
        }
        this.fBase = fAnySimpleType;
        this.fAnonymous = false;
        this.fTypeName = str;
        this.fTargetNamespace = str2;
        this.fFinalSet = s;
        this.fAnnotations = xSObjectList;
        this.fVariety = (short) 3;
        this.fMemberTypes = xSSimpleTypeDeclArr;
        this.fValidationDV = (short) 26;
        this.fFacetsDefined = (short) 16;
        this.fWhiteSpace = (short) 2;
        calcFundamentalFacets();
        this.fBuiltInKind = (short) 45;
        return this;
    }

    public String toString() {
        return this.fTargetNamespace + "," + this.fTypeName;
    }

    @Override // org.apache.xerces.impl.dv.XSSimpleType
    public Object validate(Object obj, ValidationContext validationContext, ValidatedInfo validatedInfo) throws InvalidDatatypeValueException {
        if (validationContext == null) {
            validationContext = fEmptyContext;
        }
        if (validatedInfo == null) {
            validatedInfo = new ValidatedInfo();
        } else {
            validatedInfo.memberType = null;
        }
        Object actualValue = getActualValue(obj, validationContext, validatedInfo, validationContext == null || validationContext.needToNormalize());
        validate(validationContext, validatedInfo);
        return actualValue;
    }

    @Override // org.apache.xerces.impl.dv.XSSimpleType
    public Object validate(String str, ValidationContext validationContext, ValidatedInfo validatedInfo) throws InvalidDatatypeValueException {
        if (validationContext == null) {
            validationContext = fEmptyContext;
        }
        if (validatedInfo == null) {
            validatedInfo = new ValidatedInfo();
        } else {
            validatedInfo.memberType = null;
        }
        Object actualValue = getActualValue(str, validationContext, validatedInfo, validationContext == null || validationContext.needToNormalize());
        validate(validationContext, validatedInfo);
        return actualValue;
    }

    @Override // org.apache.xerces.impl.dv.XSSimpleType
    public void validate(ValidationContext validationContext, ValidatedInfo validatedInfo) throws InvalidDatatypeValueException {
        short s;
        if (validationContext == null) {
            validationContext = fEmptyContext;
        }
        if (validationContext.needFacetChecking() && (s = this.fFacetsDefined) != 0 && s != 16) {
            checkFacets(validatedInfo);
        }
        if (validationContext.needExtraChecking()) {
            checkExtraRules(validationContext, validatedInfo);
        }
    }

    public ValidatedInfo validateWithInfo(String str, ValidationContext validationContext, ValidatedInfo validatedInfo) throws InvalidDatatypeValueException {
        if (validationContext == null) {
            validationContext = fEmptyContext;
        }
        if (validatedInfo == null) {
            validatedInfo = new ValidatedInfo();
        } else {
            validatedInfo.memberType = null;
        }
        getActualValue(str, validationContext, validatedInfo, validationContext == null || validationContext.needToNormalize());
        validate(validationContext, validatedInfo);
        return validatedInfo;
    }
}
