package org.apache.xerces.impl.dtd;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.badlogic.gdx.net.HttpStatus;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.cast.MediaError;
import io.netty.util.internal.StringUtil;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import org.apache.xerces.impl.dtd.models.CMAny;
import org.apache.xerces.impl.dtd.models.CMBinOp;
import org.apache.xerces.impl.dtd.models.CMLeaf;
import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.dtd.models.CMUniOp;
import org.apache.xerces.impl.dtd.models.ContentModelValidator;
import org.apache.xerces.impl.dtd.models.DFAContentModel;
import org.apache.xerces.impl.dtd.models.MixedContentModel;
import org.apache.xerces.impl.dtd.models.SimpleContentModel;
import org.apache.xerces.impl.dv.DatatypeValidator;
import org.apache.xerces.impl.validation.EntityState;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLDTDContentModelHandler;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.parser.XMLDTDContentModelSource;
import org.apache.xerces.xni.parser.XMLDTDSource;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes13.dex */
public class DTDGrammar implements XMLDTDHandler, XMLDTDContentModelHandler, EntityState, Grammar {
    private static final int CHUNK_MASK = 255;
    private static final int CHUNK_SHIFT = 8;
    private static final int CHUNK_SIZE = 256;
    private static final boolean DEBUG = false;
    private static final int INITIAL_CHUNK_COUNT = 4;
    private static final short LIST_FLAG = 128;
    private static final short LIST_MASK = -129;
    public static final int TOP_LEVEL_SCOPE = -1;
    protected int fCurrentAttributeIndex;
    protected int fCurrentElementIndex;
    protected XMLDTDDescription fGrammarDescription;
    private boolean fMixed;
    private SymbolTable fSymbolTable;
    protected XMLDTDSource fDTDSource = null;
    protected XMLDTDContentModelSource fDTDContentModelSource = null;
    protected boolean fReadingExternalDTD = false;
    private int fElementDeclCount = 0;
    private QName[][] fElementDeclName = new QName[4][];
    private short[][] fElementDeclType = new short[4][];
    private int[][] fElementDeclContentSpecIndex = new int[4][];
    private ContentModelValidator[][] fElementDeclContentModelValidator = new ContentModelValidator[4][];
    private int[][] fElementDeclFirstAttributeDeclIndex = new int[4][];
    private int[][] fElementDeclLastAttributeDeclIndex = new int[4][];
    private int fAttributeDeclCount = 0;
    private QName[][] fAttributeDeclName = new QName[4][];
    private boolean fIsImmutable = false;
    private short[][] fAttributeDeclType = new short[4][];
    private String[][][] fAttributeDeclEnumeration = new String[4][][];
    private short[][] fAttributeDeclDefaultType = new short[4][];
    private DatatypeValidator[][] fAttributeDeclDatatypeValidator = new DatatypeValidator[4][];
    private String[][] fAttributeDeclDefaultValue = new String[4][];
    private String[][] fAttributeDeclNonNormalizedDefaultValue = new String[4][];
    private int[][] fAttributeDeclNextAttributeDeclIndex = new int[4][];
    private int fContentSpecCount = 0;
    private short[][] fContentSpecType = new short[4][];
    private Object[][] fContentSpecValue = new Object[4][];
    private Object[][] fContentSpecOtherValue = new Object[4][];
    private int fEntityCount = 0;
    private String[][] fEntityName = new String[4][];
    private String[][] fEntityValue = new String[4][];
    private String[][] fEntityPublicId = new String[4][];
    private String[][] fEntitySystemId = new String[4][];
    private String[][] fEntityBaseSystemId = new String[4][];
    private String[][] fEntityNotation = new String[4][];
    private byte[][] fEntityIsPE = new byte[4][];
    private byte[][] fEntityInExternal = new byte[4][];
    private int fNotationCount = 0;
    private String[][] fNotationName = new String[4][];
    private String[][] fNotationPublicId = new String[4][];
    private String[][] fNotationSystemId = new String[4][];
    private String[][] fNotationBaseSystemId = new String[4][];
    private QNameHashtable fElementIndexMap = new QNameHashtable();
    private QNameHashtable fEntityIndexMap = new QNameHashtable();
    private QNameHashtable fNotationIndexMap = new QNameHashtable();
    private final QName fQName = new QName();
    private final QName fQName2 = new QName();
    protected final XMLAttributeDecl fAttributeDecl = new XMLAttributeDecl();
    private int fLeafCount = 0;
    private int fEpsilonIndex = -1;
    private XMLElementDecl fElementDecl = new XMLElementDecl();
    private XMLEntityDecl fEntityDecl = new XMLEntityDecl();
    private XMLSimpleType fSimpleType = new XMLSimpleType();
    private XMLContentSpec fContentSpec = new XMLContentSpec();
    Hashtable fElementDeclTab = new Hashtable();
    private short[] fOpStack = null;
    private int[] fNodeIndexStack = null;
    private int[] fPrevNodeIndexStack = null;
    private int fDepth = 0;
    private boolean[] fPEntityStack = new boolean[4];
    private int fPEDepth = 0;
    private int[][] fElementDeclIsExternal = new int[4][];
    private int[][] fAttributeDeclIsExternal = new int[4][];
    int valueIndex = -1;
    int prevNodeIndex = -1;
    int nodeIndex = -1;

    private static class ChildrenList {
        public int length = 0;
        public QName[] qname = new QName[2];
        public int[] type = new int[2];
    }

    protected static final class QNameHashtable {
        private static final int HASHTABLE_SIZE = 101;
        private static final int INITIAL_BUCKET_SIZE = 4;
        private static final int MAX_HASH_COLLISIONS = 40;
        private static final int MULTIPLIERS_MASK = 31;
        private static final int MULTIPLIERS_SIZE = 32;
        private int[] fHashMultipliers;
        private Object[][] fHashTable = new Object[101][];
        private int fTableSize = 101;
        private int fCount = 0;

        private static final class PrimeNumberSequenceGenerator {
            private static int[] PRIMES = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, XKeySym.XK_atilde, XKeySym.XK_aring, XKeySym.XK_eacute, XKeySym.XK_idiaeresis, XKeySym.XK_ntilde, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, TypedValues.AttributesType.TYPE_EASING, MediaError.DetailedErrorCode.SMOOTH_NETWORK, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, HttpStatus.SC_CONFLICT, 419, 421, 431, XKeySym.XK_aogonek, XKeySym.XK_caron, 443, 449, 457, 461, XKeySym.XK_Dcaron, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, TypedValues.MotionType.TYPE_PATHMOTION_ARC, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, XKeySym.XK_Hstroke, 677, XKeySym.XK_Gbreve, 691, 701, XKeySym.XK_Cabovedot, 719, 727};

            private PrimeNumberSequenceGenerator() {
            }

            static void generateSequence(int[] iArr) {
                Random random = new Random();
                for (int i = 0; i < iArr.length; i++) {
                    int[] iArr2 = PRIMES;
                    iArr[i] = iArr2[random.nextInt(iArr2.length)];
                }
            }
        }

        protected QNameHashtable() {
        }

        private int hash0(String str) {
            int length = str.length();
            int[] iArr = this.fHashMultipliers;
            int iCharAt = 0;
            for (int i = 0; i < length; i++) {
                iCharAt = (iCharAt * iArr[i & 31]) + str.charAt(i);
            }
            return iCharAt;
        }

        private void rebalance() {
            if (this.fHashMultipliers == null) {
                this.fHashMultipliers = new int[32];
            }
            PrimeNumberSequenceGenerator.generateSequence(this.fHashMultipliers);
            rehashCommon(this.fHashTable.length);
        }

        private void rehash() {
            rehashCommon((this.fHashTable.length * 2) + 1);
        }

        private void rehashCommon(int i) {
            int i2;
            int i3;
            Object[] objArr;
            Object[][] objArr2 = this.fHashTable;
            int length = objArr2.length;
            Object[][] objArr3 = new Object[i][];
            this.fHashTable = objArr3;
            this.fTableSize = objArr3.length;
            int i4 = 0;
            int i5 = 0;
            while (i5 < length) {
                Object[] objArr4 = objArr2[i5];
                if (objArr4 != null) {
                    int i6 = ((int[]) objArr4[i4])[i4];
                    int i7 = 1;
                    int i8 = i4;
                    int i9 = i8;
                    int i10 = 1;
                    while (i8 < i6) {
                        String str = (String) objArr4[i10];
                        Object obj = objArr4[i10 + 1];
                        int iHash = (hash(str) & Integer.MAX_VALUE) % this.fTableSize;
                        Object[][] objArr5 = this.fHashTable;
                        Object[] objArr6 = objArr5[iHash];
                        if (objArr6 == null) {
                            if (i9 != 0) {
                                objArr = new Object[9];
                                objArr[i4] = new int[]{i7};
                            } else {
                                ((int[]) objArr4[i4])[i4] = i7;
                                objArr = objArr4;
                                i9 = i7;
                            }
                            objArr[i7] = str;
                            objArr[2] = obj;
                            objArr5[iHash] = objArr;
                            objArr2 = objArr2;
                            i2 = i4;
                            i3 = i7;
                        } else {
                            int i11 = ((int[]) objArr6[i4])[i4];
                            int i12 = i11 * 2;
                            int i13 = i12 + 1;
                            if (i13 == objArr6.length) {
                                i3 = 1;
                                Object[] objArr7 = new Object[((i11 + 4) * 2) + 1];
                                i2 = 0;
                                System.arraycopy(objArr6, 0, objArr7, 0, i13);
                                this.fHashTable[iHash] = objArr7;
                                objArr6 = objArr7;
                            } else {
                                i2 = 0;
                                i3 = 1;
                            }
                            objArr6[i13] = str;
                            objArr6[i12 + 2] = obj;
                            ((int[]) objArr6[i2])[i2] = i11 + 1;
                        }
                        i10 += 2;
                        i8++;
                        i4 = i2;
                        i7 = i3;
                        objArr2 = objArr2;
                    }
                }
                i5++;
                i4 = i4;
                objArr2 = objArr2;
            }
        }

        public int get(String str) {
            Object[] objArr = this.fHashTable[(hash(str) & Integer.MAX_VALUE) % this.fTableSize];
            if (objArr == null) {
                return -1;
            }
            int i = ((int[]) objArr[0])[0];
            int i2 = 1;
            for (int i3 = 0; i3 < i; i3++) {
                if (((String) objArr[i2]) == str) {
                    return ((int[]) objArr[i2 + 1])[0];
                }
                i2 += 2;
            }
            return -1;
        }

        public int hash(String str) {
            return this.fHashMultipliers == null ? str.hashCode() : hash0(str);
        }

        public void put(String str, int i) {
            int iHash = hash(str) & Integer.MAX_VALUE;
            int i2 = this.fTableSize;
            int i3 = iHash % i2;
            Object[][] objArr = this.fHashTable;
            Object[] objArr2 = objArr[i3];
            if (objArr2 == null) {
                Object[] objArr3 = new Object[9];
                objArr3[0] = new int[]{1};
                objArr3[1] = str;
                objArr3[2] = new int[]{i};
                objArr[i3] = objArr3;
                int i4 = this.fCount + 1;
                this.fCount = i4;
                if (i4 <= i2) {
                    return;
                }
            } else {
                int i5 = ((int[]) objArr2[0])[0];
                int i6 = i5 * 2;
                int i7 = i6 + 1;
                if (i7 == objArr2.length) {
                    Object[] objArr4 = new Object[((i5 + 4) * 2) + 1];
                    System.arraycopy(objArr2, 0, objArr4, 0, i7);
                    this.fHashTable[i3] = objArr4;
                    objArr2 = objArr4;
                }
                int i8 = 1;
                for (int i9 = 0; i9 < i5; i9++) {
                    if (((String) objArr2[i8]) == str) {
                        ((int[]) objArr2[i8 + 1])[0] = i;
                        return;
                    }
                    i8 += 2;
                }
                objArr2[i7] = str;
                objArr2[i6 + 2] = new int[]{i};
                int i10 = i5 + 1;
                ((int[]) objArr2[0])[0] = i10;
                int i11 = this.fCount + 1;
                this.fCount = i11;
                if (i11 <= this.fTableSize) {
                    if (i10 > 40) {
                        rebalance();
                        return;
                    }
                    return;
                }
            }
            rehash();
        }
    }

    public DTDGrammar(SymbolTable symbolTable, XMLDTDDescription xMLDTDDescription) {
        this.fGrammarDescription = null;
        this.fSymbolTable = symbolTable;
        this.fGrammarDescription = xMLDTDDescription;
    }

    private void appendContentSpec(XMLContentSpec xMLContentSpec, StringBuffer stringBuffer, boolean z, int i) {
        Object obj;
        String str;
        char c;
        String str2;
        int i2 = xMLContentSpec.type & 15;
        switch (i2) {
            case 0:
                if (xMLContentSpec.value == null && xMLContentSpec.otherValue == null) {
                    str = "#PCDATA";
                    stringBuffer.append(str);
                } else {
                    if (xMLContentSpec.value == null && xMLContentSpec.otherValue != null) {
                        stringBuffer = stringBuffer.append("##any:uri=");
                        obj = xMLContentSpec.otherValue;
                    } else if (xMLContentSpec.value == null) {
                        stringBuffer.append(SchemaSymbols.ATTVAL_TWOPOUNDANY);
                    } else {
                        obj = xMLContentSpec.value;
                    }
                    stringBuffer.append(obj);
                }
                break;
            case 1:
                if (i == 3 || i == 2 || i == 1) {
                    getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec);
                    stringBuffer.append('(');
                    appendContentSpec(xMLContentSpec, stringBuffer, true, i2);
                    stringBuffer.append(')');
                } else {
                    getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec);
                    appendContentSpec(xMLContentSpec, stringBuffer, true, i2);
                }
                c = MangleConstant.Q_MARK;
                stringBuffer.append(c);
                break;
            case 2:
                if (i == 3 || i == 2 || i == 1) {
                    getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec);
                    stringBuffer.append('(');
                    appendContentSpec(xMLContentSpec, stringBuffer, true, i2);
                    stringBuffer.append(')');
                } else {
                    getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec);
                    appendContentSpec(xMLContentSpec, stringBuffer, true, i2);
                }
                c = '*';
                stringBuffer.append(c);
                break;
            case 3:
                if (i == 3 || i == 2 || i == 1) {
                    stringBuffer.append('(');
                    getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec);
                    appendContentSpec(xMLContentSpec, stringBuffer, true, i2);
                    stringBuffer.append(')');
                } else {
                    getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec);
                    appendContentSpec(xMLContentSpec, stringBuffer, true, i2);
                }
                c = '+';
                stringBuffer.append(c);
                break;
            case 4:
            case 5:
                if (z) {
                    stringBuffer.append('(');
                }
                short s = xMLContentSpec.type;
                int i3 = ((int[]) xMLContentSpec.otherValue)[0];
                getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec);
                appendContentSpec(xMLContentSpec, stringBuffer, xMLContentSpec.type != s, i2);
                stringBuffer.append(s == 4 ? '|' : ',');
                getContentSpec(i3, xMLContentSpec);
                appendContentSpec(xMLContentSpec, stringBuffer, true, i2);
                if (z) {
                    stringBuffer.append(')');
                }
                break;
            case 6:
                stringBuffer.append(SchemaSymbols.ATTVAL_TWOPOUNDANY);
                if (xMLContentSpec.otherValue != null) {
                    str2 = ":uri=";
                    stringBuffer.append(str2);
                    obj = xMLContentSpec.otherValue;
                    stringBuffer.append(obj);
                }
                break;
            case 7:
                str2 = "##other:uri=";
                stringBuffer.append(str2);
                obj = xMLContentSpec.otherValue;
                stringBuffer.append(obj);
                break;
            case 8:
                str = SchemaSymbols.ATTVAL_TWOPOUNDLOCAL;
                stringBuffer.append(str);
                break;
            default:
                str = "???";
                stringBuffer.append(str);
                break;
        }
    }

    private final CMNode buildSyntaxTree(int i, XMLContentSpec xMLContentSpec) {
        getContentSpec(i, xMLContentSpec);
        if ((xMLContentSpec.type & 15) == 6) {
            short s = xMLContentSpec.type;
            String str = (String) xMLContentSpec.otherValue;
            int i2 = this.fLeafCount;
            this.fLeafCount = i2 + 1;
            return new CMAny(s, str, i2);
        }
        if ((xMLContentSpec.type & 15) == 7) {
            short s2 = xMLContentSpec.type;
            String str2 = (String) xMLContentSpec.otherValue;
            int i3 = this.fLeafCount;
            this.fLeafCount = i3 + 1;
            return new CMAny(s2, str2, i3);
        }
        if ((xMLContentSpec.type & 15) == 8) {
            short s3 = xMLContentSpec.type;
            int i4 = this.fLeafCount;
            this.fLeafCount = i4 + 1;
            return new CMAny(s3, null, i4);
        }
        if (xMLContentSpec.type == 0) {
            this.fQName.setValues(null, (String) xMLContentSpec.value, (String) xMLContentSpec.value, (String) xMLContentSpec.otherValue);
            QName qName = this.fQName;
            int i5 = this.fLeafCount;
            this.fLeafCount = i5 + 1;
            return new CMLeaf(qName, i5);
        }
        int i6 = ((int[]) xMLContentSpec.value)[0];
        int i7 = ((int[]) xMLContentSpec.otherValue)[0];
        if (xMLContentSpec.type == 4 || xMLContentSpec.type == 5) {
            return new CMBinOp(xMLContentSpec.type, buildSyntaxTree(i6, xMLContentSpec), buildSyntaxTree(i7, xMLContentSpec));
        }
        if (xMLContentSpec.type != 2 && xMLContentSpec.type != 2 && xMLContentSpec.type != 1 && xMLContentSpec.type != 3) {
            throw new RuntimeException("ImplementationMessages.VAL_CST");
        }
        CMUniOp cMUniOp = new CMUniOp(xMLContentSpec.type, buildSyntaxTree(i6, xMLContentSpec));
        return cMUniOp;
    }

    private void contentSpecTree(int i, XMLContentSpec xMLContentSpec, ChildrenList childrenList) {
        getContentSpec(i, xMLContentSpec);
        if (xMLContentSpec.type == 0 || (xMLContentSpec.type & 15) == 6 || (xMLContentSpec.type & 15) == 8 || (xMLContentSpec.type & 15) == 7) {
            if (childrenList.length == childrenList.qname.length) {
                QName[] qNameArr = new QName[childrenList.length * 2];
                System.arraycopy(childrenList.qname, 0, qNameArr, 0, childrenList.length);
                childrenList.qname = qNameArr;
                int[] iArr = new int[childrenList.length * 2];
                System.arraycopy(childrenList.type, 0, iArr, 0, childrenList.length);
                childrenList.type = iArr;
            }
            childrenList.qname[childrenList.length] = new QName(null, (String) xMLContentSpec.value, (String) xMLContentSpec.value, (String) xMLContentSpec.otherValue);
            childrenList.type[childrenList.length] = xMLContentSpec.type;
            childrenList.length++;
            return;
        }
        int i2 = xMLContentSpec.value != null ? ((int[]) xMLContentSpec.value)[0] : -1;
        if (xMLContentSpec.otherValue != null) {
            int i3 = ((int[]) xMLContentSpec.otherValue)[0];
            if (xMLContentSpec.type == 4 || xMLContentSpec.type == 5) {
                contentSpecTree(i2, xMLContentSpec, childrenList);
                contentSpecTree(i3, xMLContentSpec, childrenList);
            } else {
                if (xMLContentSpec.type != 1 && xMLContentSpec.type != 2 && xMLContentSpec.type != 3) {
                    throw new RuntimeException("Invalid content spec type seen in contentSpecTree() method of AbstractDTDGrammar class : " + ((int) xMLContentSpec.type));
                }
                contentSpecTree(i2, xMLContentSpec, childrenList);
            }
        }
    }

    private synchronized ContentModelValidator createChildModel(int i) {
        XMLContentSpec xMLContentSpec = new XMLContentSpec();
        getContentSpec(i, xMLContentSpec);
        if ((xMLContentSpec.type & 15) != 6 && (xMLContentSpec.type & 15) != 7 && (xMLContentSpec.type & 15) != 8) {
            if (xMLContentSpec.type == 0) {
                if (xMLContentSpec.value == null && xMLContentSpec.otherValue == null) {
                    throw new RuntimeException("ImplementationMessages.VAL_NPCD");
                }
                this.fQName.setValues(null, (String) xMLContentSpec.value, (String) xMLContentSpec.value, (String) xMLContentSpec.otherValue);
                return new SimpleContentModel(xMLContentSpec.type, this.fQName, null);
            }
            if (xMLContentSpec.type == 4 || xMLContentSpec.type == 5) {
                XMLContentSpec xMLContentSpec2 = new XMLContentSpec();
                XMLContentSpec xMLContentSpec3 = new XMLContentSpec();
                getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec2);
                getContentSpec(((int[]) xMLContentSpec.otherValue)[0], xMLContentSpec3);
                if (xMLContentSpec2.type == 0 && xMLContentSpec3.type == 0) {
                    this.fQName.setValues(null, (String) xMLContentSpec2.value, (String) xMLContentSpec2.value, (String) xMLContentSpec2.otherValue);
                    this.fQName2.setValues(null, (String) xMLContentSpec3.value, (String) xMLContentSpec3.value, (String) xMLContentSpec3.otherValue);
                    return new SimpleContentModel(xMLContentSpec.type, this.fQName, this.fQName2);
                }
            } else {
                if (xMLContentSpec.type != 1 && xMLContentSpec.type != 2 && xMLContentSpec.type != 3) {
                    throw new RuntimeException("ImplementationMessages.VAL_CST");
                }
                XMLContentSpec xMLContentSpec4 = new XMLContentSpec();
                getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec4);
                if (xMLContentSpec4.type == 0) {
                    this.fQName.setValues(null, (String) xMLContentSpec4.value, (String) xMLContentSpec4.value, (String) xMLContentSpec4.otherValue);
                    return new SimpleContentModel(xMLContentSpec.type, this.fQName, null);
                }
            }
        }
        this.fLeafCount = 0;
        return new DFAContentModel(buildSyntaxTree(i, xMLContentSpec), this.fLeafCount, false);
    }

    private void ensureAttributeDeclCapacity(int i) {
        QName[][] qNameArr = this.fAttributeDeclName;
        if (i >= qNameArr.length) {
            int[][] iArr = this.fAttributeDeclIsExternal;
            this.fAttributeDeclIsExternal = resize(iArr, iArr.length * 2);
            QName[][] qNameArr2 = this.fAttributeDeclName;
            this.fAttributeDeclName = resize(qNameArr2, qNameArr2.length * 2);
            short[][] sArr = this.fAttributeDeclType;
            this.fAttributeDeclType = resize(sArr, sArr.length * 2);
            String[][][] strArr = this.fAttributeDeclEnumeration;
            this.fAttributeDeclEnumeration = resize(strArr, strArr.length * 2);
            short[][] sArr2 = this.fAttributeDeclDefaultType;
            this.fAttributeDeclDefaultType = resize(sArr2, sArr2.length * 2);
            DatatypeValidator[][] datatypeValidatorArr = this.fAttributeDeclDatatypeValidator;
            this.fAttributeDeclDatatypeValidator = resize(datatypeValidatorArr, datatypeValidatorArr.length * 2);
            String[][] strArr2 = this.fAttributeDeclDefaultValue;
            this.fAttributeDeclDefaultValue = resize(strArr2, strArr2.length * 2);
            String[][] strArr3 = this.fAttributeDeclNonNormalizedDefaultValue;
            this.fAttributeDeclNonNormalizedDefaultValue = resize(strArr3, strArr3.length * 2);
            int[][] iArr2 = this.fAttributeDeclNextAttributeDeclIndex;
            this.fAttributeDeclNextAttributeDeclIndex = resize(iArr2, iArr2.length * 2);
        } else if (qNameArr[i] != null) {
            return;
        }
        this.fAttributeDeclIsExternal[i] = new int[256];
        this.fAttributeDeclName[i] = new QName[256];
        this.fAttributeDeclType[i] = new short[256];
        this.fAttributeDeclEnumeration[i] = new String[256][];
        this.fAttributeDeclDefaultType[i] = new short[256];
        this.fAttributeDeclDatatypeValidator[i] = new DatatypeValidator[256];
        this.fAttributeDeclDefaultValue[i] = new String[256];
        this.fAttributeDeclNonNormalizedDefaultValue[i] = new String[256];
        this.fAttributeDeclNextAttributeDeclIndex[i] = new int[256];
    }

    private void ensureContentSpecCapacity(int i) {
        short[][] sArr = this.fContentSpecType;
        if (i >= sArr.length) {
            this.fContentSpecType = resize(sArr, sArr.length * 2);
            Object[][] objArr = this.fContentSpecValue;
            this.fContentSpecValue = resize(objArr, objArr.length * 2);
            Object[][] objArr2 = this.fContentSpecOtherValue;
            this.fContentSpecOtherValue = resize(objArr2, objArr2.length * 2);
        } else if (sArr[i] != null) {
            return;
        }
        this.fContentSpecType[i] = new short[256];
        this.fContentSpecValue[i] = new Object[256];
        this.fContentSpecOtherValue[i] = new Object[256];
    }

    private void ensureElementDeclCapacity(int i) {
        QName[][] qNameArr = this.fElementDeclName;
        if (i >= qNameArr.length) {
            int[][] iArr = this.fElementDeclIsExternal;
            this.fElementDeclIsExternal = resize(iArr, iArr.length * 2);
            QName[][] qNameArr2 = this.fElementDeclName;
            this.fElementDeclName = resize(qNameArr2, qNameArr2.length * 2);
            short[][] sArr = this.fElementDeclType;
            this.fElementDeclType = resize(sArr, sArr.length * 2);
            ContentModelValidator[][] contentModelValidatorArr = this.fElementDeclContentModelValidator;
            this.fElementDeclContentModelValidator = resize(contentModelValidatorArr, contentModelValidatorArr.length * 2);
            int[][] iArr2 = this.fElementDeclContentSpecIndex;
            this.fElementDeclContentSpecIndex = resize(iArr2, iArr2.length * 2);
            int[][] iArr3 = this.fElementDeclFirstAttributeDeclIndex;
            this.fElementDeclFirstAttributeDeclIndex = resize(iArr3, iArr3.length * 2);
            int[][] iArr4 = this.fElementDeclLastAttributeDeclIndex;
            this.fElementDeclLastAttributeDeclIndex = resize(iArr4, iArr4.length * 2);
        } else if (qNameArr[i] != null) {
            return;
        }
        this.fElementDeclIsExternal[i] = new int[256];
        this.fElementDeclName[i] = new QName[256];
        this.fElementDeclType[i] = new short[256];
        this.fElementDeclContentModelValidator[i] = new ContentModelValidator[256];
        this.fElementDeclContentSpecIndex[i] = new int[256];
        this.fElementDeclFirstAttributeDeclIndex[i] = new int[256];
        this.fElementDeclLastAttributeDeclIndex[i] = new int[256];
    }

    private void ensureEntityDeclCapacity(int i) {
        String[][] strArr = this.fEntityName;
        if (i >= strArr.length) {
            this.fEntityName = resize(strArr, strArr.length * 2);
            String[][] strArr2 = this.fEntityValue;
            this.fEntityValue = resize(strArr2, strArr2.length * 2);
            String[][] strArr3 = this.fEntityPublicId;
            this.fEntityPublicId = resize(strArr3, strArr3.length * 2);
            String[][] strArr4 = this.fEntitySystemId;
            this.fEntitySystemId = resize(strArr4, strArr4.length * 2);
            String[][] strArr5 = this.fEntityBaseSystemId;
            this.fEntityBaseSystemId = resize(strArr5, strArr5.length * 2);
            String[][] strArr6 = this.fEntityNotation;
            this.fEntityNotation = resize(strArr6, strArr6.length * 2);
            byte[][] bArr = this.fEntityIsPE;
            this.fEntityIsPE = resize(bArr, bArr.length * 2);
            byte[][] bArr2 = this.fEntityInExternal;
            this.fEntityInExternal = resize(bArr2, bArr2.length * 2);
        } else if (strArr[i] != null) {
            return;
        }
        this.fEntityName[i] = new String[256];
        this.fEntityValue[i] = new String[256];
        this.fEntityPublicId[i] = new String[256];
        this.fEntitySystemId[i] = new String[256];
        this.fEntityBaseSystemId[i] = new String[256];
        this.fEntityNotation[i] = new String[256];
        this.fEntityIsPE[i] = new byte[256];
        this.fEntityInExternal[i] = new byte[256];
    }

    private void ensureNotationDeclCapacity(int i) {
        String[][] strArr = this.fNotationName;
        if (i >= strArr.length) {
            this.fNotationName = resize(strArr, strArr.length * 2);
            String[][] strArr2 = this.fNotationPublicId;
            this.fNotationPublicId = resize(strArr2, strArr2.length * 2);
            String[][] strArr3 = this.fNotationSystemId;
            this.fNotationSystemId = resize(strArr3, strArr3.length * 2);
            String[][] strArr4 = this.fNotationBaseSystemId;
            this.fNotationBaseSystemId = resize(strArr4, strArr4.length * 2);
        } else if (strArr[i] != null) {
            return;
        }
        this.fNotationName[i] = new String[256];
        this.fNotationPublicId[i] = new String[256];
        this.fNotationSystemId[i] = new String[256];
        this.fNotationBaseSystemId[i] = new String[256];
    }

    private void printAttribute(int i) {
        XMLAttributeDecl xMLAttributeDecl = new XMLAttributeDecl();
        if (getAttributeDecl(i, xMLAttributeDecl)) {
            System.out.print(" { ");
            System.out.print(xMLAttributeDecl.f473name.localpart);
            System.out.print(" }");
        }
    }

    private static byte[][] resize(byte[][] bArr, int i) {
        byte[][] bArr2 = new byte[i][];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    private static int[][] resize(int[][] iArr, int i) {
        int[][] iArr2 = new int[i][];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        return iArr2;
    }

    private static Object[][] resize(Object[][] objArr, int i) {
        Object[][] objArr2 = new Object[i][];
        System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
        return objArr2;
    }

    private static String[][] resize(String[][] strArr, int i) {
        String[][] strArr2 = new String[i][];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        return strArr2;
    }

    private static ContentModelValidator[][] resize(ContentModelValidator[][] contentModelValidatorArr, int i) {
        ContentModelValidator[][] contentModelValidatorArr2 = new ContentModelValidator[i][];
        System.arraycopy(contentModelValidatorArr, 0, contentModelValidatorArr2, 0, contentModelValidatorArr.length);
        return contentModelValidatorArr2;
    }

    private static DatatypeValidator[][] resize(DatatypeValidator[][] datatypeValidatorArr, int i) {
        DatatypeValidator[][] datatypeValidatorArr2 = new DatatypeValidator[i][];
        System.arraycopy(datatypeValidatorArr, 0, datatypeValidatorArr2, 0, datatypeValidatorArr.length);
        return datatypeValidatorArr2;
    }

    private static QName[][] resize(QName[][] qNameArr, int i) {
        QName[][] qNameArr2 = new QName[i][];
        System.arraycopy(qNameArr, 0, qNameArr2, 0, qNameArr.length);
        return qNameArr2;
    }

    private static short[][] resize(short[][] sArr, int i) {
        short[][] sArr2 = new short[i][];
        System.arraycopy(sArr, 0, sArr2, 0, sArr.length);
        return sArr2;
    }

    private static String[][][] resize(String[][][] strArr, int i) {
        String[][][] strArr2 = new String[i][][];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        return strArr2;
    }

    protected int addContentSpecNode(short s, int i, int i2) {
        int iCreateContentSpec = createContentSpec();
        this.fContentSpec.setValues(s, new int[]{i}, new int[]{i2});
        setContentSpec(iCreateContentSpec, this.fContentSpec);
        return iCreateContentSpec;
    }

    protected int addContentSpecNode(short s, String str) {
        int iCreateContentSpec = createContentSpec();
        this.fContentSpec.setValues(s, str, null);
        setContentSpec(iCreateContentSpec, this.fContentSpec);
        return iCreateContentSpec;
    }

    protected void addContentSpecToElement(XMLElementDecl xMLElementDecl) {
        int i = this.fDepth;
        if ((i == 0 || (i == 1 && xMLElementDecl.type == 2)) && this.fNodeIndexStack != null) {
            if (xMLElementDecl.type == 2) {
                int iAddUniqueLeafNode = addUniqueLeafNode(null);
                int[] iArr = this.fNodeIndexStack;
                int i2 = iArr[0];
                if (i2 == -1) {
                    iArr[0] = iAddUniqueLeafNode;
                } else {
                    iArr[0] = addContentSpecNode((short) 4, iAddUniqueLeafNode, i2);
                }
            }
            setContentSpecIndex(this.fCurrentElementIndex, this.fNodeIndexStack[this.fDepth]);
        }
    }

    protected int addUniqueLeafNode(String str) {
        int iCreateContentSpec = createContentSpec();
        this.fContentSpec.setValues((short) 0, str, null);
        setContentSpec(iCreateContentSpec, this.fContentSpec);
        return iCreateContentSpec;
    }

    @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
    public void any(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void attributeDecl(String str, String str2, String str3, String[] strArr, String str4, XMLString xMLString, XMLString xMLString2, Augmentations augmentations) throws XNIException {
        XMLSimpleType xMLSimpleType;
        short s;
        if (!this.fElementDeclTab.containsKey(str)) {
            this.fCurrentElementIndex = createElementDecl();
            XMLElementDecl xMLElementDecl = new XMLElementDecl();
            xMLElementDecl.f474name.setValues(null, str, str, null);
            xMLElementDecl.scope = -1;
            this.fElementDeclTab.put(str, xMLElementDecl);
            setElementDecl(this.fCurrentElementIndex, xMLElementDecl);
        }
        int elementDeclIndex = getElementDeclIndex(str);
        if (getAttributeDeclIndex(elementDeclIndex, str2) != -1) {
            return;
        }
        this.fCurrentAttributeIndex = createAttributeDecl();
        this.fSimpleType.clear();
        if (str4 != null) {
            if (str4.equals("#FIXED")) {
                this.fSimpleType.defaultType = (short) 1;
            } else if (str4.equals("#IMPLIED")) {
                this.fSimpleType.defaultType = (short) 0;
            } else if (str4.equals("#REQUIRED")) {
                this.fSimpleType.defaultType = (short) 2;
            }
        }
        this.fSimpleType.defaultValue = xMLString != null ? xMLString.toString() : null;
        this.fSimpleType.nonNormalizedDefaultValue = xMLString2 != null ? xMLString2.toString() : null;
        this.fSimpleType.enumeration = strArr;
        if (str3.equals("CDATA")) {
            this.fSimpleType.type = (short) 0;
        } else {
            if (str3.equals(SchemaSymbols.ATTVAL_ID)) {
                xMLSimpleType = this.fSimpleType;
                s = 3;
            } else if (str3.startsWith(SchemaSymbols.ATTVAL_IDREF)) {
                this.fSimpleType.type = (short) 4;
                if (str3.indexOf(ExifInterface.LATITUDE_SOUTH) > 0) {
                    this.fSimpleType.list = true;
                }
            } else {
                if (str3.equals(SchemaSymbols.ATTVAL_ENTITIES)) {
                    this.fSimpleType.type = (short) 1;
                } else if (str3.equals(SchemaSymbols.ATTVAL_ENTITY)) {
                    this.fSimpleType.type = (short) 1;
                } else if (str3.equals(SchemaSymbols.ATTVAL_NMTOKENS)) {
                    this.fSimpleType.type = (short) 5;
                } else if (str3.equals(SchemaSymbols.ATTVAL_NMTOKEN)) {
                    this.fSimpleType.type = (short) 5;
                } else if (str3.startsWith(SchemaSymbols.ATTVAL_NOTATION)) {
                    xMLSimpleType = this.fSimpleType;
                    s = 6;
                } else if (str3.startsWith("ENUMERATION")) {
                    this.fSimpleType.type = (short) 2;
                } else {
                    System.err.println("!!! unknown attribute type " + str3);
                }
                this.fSimpleType.list = true;
            }
            xMLSimpleType.type = s;
        }
        this.fQName.setValues(null, str2, str2, null);
        this.fAttributeDecl.setValues(this.fQName, this.fSimpleType, false);
        setAttributeDecl(elementDeclIndex, this.fCurrentAttributeIndex, this.fAttributeDecl);
        int i = this.fCurrentAttributeIndex;
        int i2 = i >> 8;
        int i3 = i & 255;
        ensureAttributeDeclCapacity(i2);
        this.fAttributeDeclIsExternal[i2][i3] = (this.fReadingExternalDTD || this.fPEDepth > 0) ? 1 : 0;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    protected int createAttributeDecl() {
        int i = this.fAttributeDeclCount;
        int i2 = i >> 8;
        int i3 = i & 255;
        ensureAttributeDeclCapacity(i2);
        this.fAttributeDeclName[i2][i3] = new QName();
        this.fAttributeDeclType[i2][i3] = -1;
        this.fAttributeDeclDatatypeValidator[i2][i3] = null;
        this.fAttributeDeclEnumeration[i2][i3] = null;
        this.fAttributeDeclDefaultType[i2][i3] = 0;
        this.fAttributeDeclDefaultValue[i2][i3] = null;
        this.fAttributeDeclNonNormalizedDefaultValue[i2][i3] = null;
        this.fAttributeDeclNextAttributeDeclIndex[i2][i3] = -1;
        int i4 = this.fAttributeDeclCount;
        this.fAttributeDeclCount = i4 + 1;
        return i4;
    }

    protected int createContentSpec() {
        int i = this.fContentSpecCount;
        int i2 = i >> 8;
        int i3 = i & 255;
        ensureContentSpecCapacity(i2);
        this.fContentSpecType[i2][i3] = -1;
        this.fContentSpecValue[i2][i3] = null;
        this.fContentSpecOtherValue[i2][i3] = null;
        int i4 = this.fContentSpecCount;
        this.fContentSpecCount = i4 + 1;
        return i4;
    }

    protected int createElementDecl() {
        int i = this.fElementDeclCount;
        int i2 = i >> 8;
        int i3 = i & 255;
        ensureElementDeclCapacity(i2);
        this.fElementDeclName[i2][i3] = new QName();
        this.fElementDeclType[i2][i3] = -1;
        this.fElementDeclContentModelValidator[i2][i3] = null;
        this.fElementDeclFirstAttributeDeclIndex[i2][i3] = -1;
        this.fElementDeclLastAttributeDeclIndex[i2][i3] = -1;
        int i4 = this.fElementDeclCount;
        this.fElementDeclCount = i4 + 1;
        return i4;
    }

    protected int createEntityDecl() {
        int i = this.fEntityCount;
        int i2 = i >> 8;
        int i3 = i & 255;
        ensureEntityDeclCapacity(i2);
        this.fEntityIsPE[i2][i3] = 0;
        this.fEntityInExternal[i2][i3] = 0;
        int i4 = this.fEntityCount;
        this.fEntityCount = i4 + 1;
        return i4;
    }

    protected int createNotationDecl() {
        ensureNotationDeclCapacity(this.fNotationCount >> 8);
        int i = this.fNotationCount;
        this.fNotationCount = i + 1;
        return i;
    }

    public void element(String str, Augmentations augmentations) throws XNIException {
        if (!this.fMixed) {
            this.fNodeIndexStack[this.fDepth] = addContentSpecNode((short) 0, str);
            return;
        }
        int[] iArr = this.fNodeIndexStack;
        int i = this.fDepth;
        int i2 = iArr[i];
        if (i2 == -1) {
            iArr[i] = addUniqueLeafNode(str);
        } else {
            iArr[i] = addContentSpecNode((short) 4, i2, addUniqueLeafNode(str));
        }
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void elementDecl(String str, String str2, Augmentations augmentations) throws XNIException {
        int iCreateElementDecl;
        XMLElementDecl xMLElementDecl = (XMLElementDecl) this.fElementDeclTab.get(str);
        if (xMLElementDecl == null) {
            iCreateElementDecl = createElementDecl();
        } else if (xMLElementDecl.type != -1) {
            return;
        } else {
            iCreateElementDecl = getElementDeclIndex(str);
        }
        this.fCurrentElementIndex = iCreateElementDecl;
        XMLElementDecl xMLElementDecl2 = new XMLElementDecl();
        this.fQName.setValues(null, str, str, null);
        xMLElementDecl2.f474name.setValues(this.fQName);
        xMLElementDecl2.contentModelValidator = null;
        xMLElementDecl2.scope = -1;
        if (str2.equals("EMPTY")) {
            xMLElementDecl2.type = (short) 1;
        } else if (str2.equals("ANY")) {
            xMLElementDecl2.type = (short) 0;
        } else if (str2.startsWith("(")) {
            xMLElementDecl2.type = str2.indexOf("#PCDATA") > 0 ? (short) 2 : (short) 3;
        }
        this.fElementDeclTab.put(str, xMLElementDecl2);
        this.fElementDecl = xMLElementDecl2;
        addContentSpecToElement(xMLElementDecl2);
        setElementDecl(this.fCurrentElementIndex, this.fElementDecl);
        int i = this.fCurrentElementIndex;
        int i2 = i >> 8;
        int i3 = i & 255;
        ensureElementDeclCapacity(i2);
        this.fElementDeclIsExternal[i2][i3] = (this.fReadingExternalDTD || this.fPEDepth > 0) ? 1 : 0;
    }

    @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
    public void empty(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void endAttlist(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void endConditional(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
    public void endContentModel(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void endDTD(Augmentations augmentations) throws XNIException {
        this.fIsImmutable = true;
        if (this.fGrammarDescription.getRootName() == null) {
            int i = this.fElementDeclCount;
            ArrayList arrayList = new ArrayList(i);
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(this.fElementDeclName[i2 >> 8][i2 & 255].rawname);
            }
            this.fGrammarDescription.setPossibleRoots(arrayList);
        }
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void endExternalSubset(Augmentations augmentations) throws XNIException {
        this.fReadingExternalDTD = false;
    }

    public void endGroup(Augmentations augmentations) throws XNIException {
        if (this.fMixed) {
            return;
        }
        int[] iArr = this.fPrevNodeIndexStack;
        int i = this.fDepth;
        int i2 = iArr[i];
        if (i2 != -1) {
            int[] iArr2 = this.fNodeIndexStack;
            iArr2[i] = addContentSpecNode(this.fOpStack[i], i2, iArr2[i]);
        }
        int[] iArr3 = this.fNodeIndexStack;
        int i3 = this.fDepth;
        int i4 = i3 - 1;
        this.fDepth = i4;
        iArr3[i4] = iArr3[i3];
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void endParameterEntity(String str, Augmentations augmentations) throws XNIException {
        int i = this.fPEDepth - 1;
        this.fPEDepth = i;
        this.fReadingExternalDTD = this.fPEntityStack[i];
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void externalEntityDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        if (getEntityDeclIndex(str) == -1) {
            int iCreateEntityDecl = createEntityDecl();
            boolean zStartsWith = str.startsWith("%");
            boolean z = this.fReadingExternalDTD || this.fPEDepth > 0;
            XMLEntityDecl xMLEntityDecl = new XMLEntityDecl();
            xMLEntityDecl.setValues(str, xMLResourceIdentifier.getPublicId(), xMLResourceIdentifier.getLiteralSystemId(), xMLResourceIdentifier.getBaseSystemId(), null, null, zStartsWith, z);
            setEntityDecl(iCreateEntityDecl, xMLEntityDecl);
        }
    }

    public boolean getAttributeDecl(int i, XMLAttributeDecl xMLAttributeDecl) {
        boolean z = false;
        if (i < 0 || i >= this.fAttributeDeclCount) {
            return false;
        }
        int i2 = i >> 8;
        int i3 = i & 255;
        xMLAttributeDecl.f473name.setValues(this.fAttributeDeclName[i2][i3]);
        short s = this.fAttributeDeclType[i2][i3];
        short s2 = -1;
        if (s != -1) {
            s2 = (short) (s & LIST_MASK);
            if ((s & 128) != 0) {
                z = true;
            }
        }
        xMLAttributeDecl.simpleType.setValues(s2, this.fAttributeDeclName[i2][i3].localpart, this.fAttributeDeclEnumeration[i2][i3], z, this.fAttributeDeclDefaultType[i2][i3], this.fAttributeDeclDefaultValue[i2][i3], this.fAttributeDeclNonNormalizedDefaultValue[i2][i3], this.fAttributeDeclDatatypeValidator[i2][i3]);
        return true;
    }

    public int getAttributeDeclIndex(int i, String str) {
        if (i == -1) {
            return -1;
        }
        int firstAttributeDeclIndex = getFirstAttributeDeclIndex(i);
        while (firstAttributeDeclIndex != -1) {
            getAttributeDecl(firstAttributeDeclIndex, this.fAttributeDecl);
            if (this.fAttributeDecl.f473name.rawname == str || str.equals(this.fAttributeDecl.f473name.rawname)) {
                return firstAttributeDeclIndex;
            }
            firstAttributeDeclIndex = getNextAttributeDeclIndex(firstAttributeDeclIndex);
        }
        return -1;
    }

    public boolean getAttributeDeclIsExternal(int i) {
        if (i < 0) {
            return false;
        }
        return this.fAttributeDeclIsExternal[i >> 8][i & 255] != 0;
    }

    public boolean getContentSpec(int i, XMLContentSpec xMLContentSpec) {
        if (i < 0 || i >= this.fContentSpecCount) {
            return false;
        }
        int i2 = i >> 8;
        int i3 = i & 255;
        xMLContentSpec.type = this.fContentSpecType[i2][i3];
        xMLContentSpec.value = this.fContentSpecValue[i2][i3];
        xMLContentSpec.otherValue = this.fContentSpecOtherValue[i2][i3];
        return true;
    }

    public String getContentSpecAsString(int i) {
        char c;
        String str;
        String str2;
        if (i >= 0 && i < this.fElementDeclCount) {
            int i2 = this.fElementDeclContentSpecIndex[i >> 8][i & 255];
            XMLContentSpec xMLContentSpec = new XMLContentSpec();
            if (getContentSpec(i2, xMLContentSpec)) {
                StringBuffer stringBuffer = new StringBuffer();
                int i3 = xMLContentSpec.type & 15;
                switch (i3) {
                    case 0:
                        stringBuffer.append('(');
                        if (xMLContentSpec.value == null && xMLContentSpec.otherValue == null) {
                            stringBuffer.append("#PCDATA");
                        } else {
                            stringBuffer.append(xMLContentSpec.value);
                        }
                        stringBuffer.append(')');
                        break;
                    case 1:
                        getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec);
                        short s = xMLContentSpec.type;
                        if (s != 0) {
                            if (s == 3 || s == 2 || s == 1) {
                                stringBuffer.append('(');
                                appendContentSpec(xMLContentSpec, stringBuffer, true, i3);
                            } else {
                                appendContentSpec(xMLContentSpec, stringBuffer, true, i3);
                            }
                            c = MangleConstant.Q_MARK;
                            stringBuffer.append(c);
                        } else {
                            stringBuffer.append('(');
                            stringBuffer.append(xMLContentSpec.value);
                        }
                        stringBuffer.append(')');
                        c = MangleConstant.Q_MARK;
                        stringBuffer.append(c);
                        break;
                    case 2:
                        getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec);
                        short s2 = xMLContentSpec.type;
                        if (s2 != 0) {
                            if (s2 == 3 || s2 == 2 || s2 == 1) {
                                stringBuffer.append('(');
                            } else {
                                appendContentSpec(xMLContentSpec, stringBuffer, true, i3);
                            }
                            c = '*';
                            stringBuffer.append(c);
                        } else {
                            stringBuffer.append('(');
                            if (xMLContentSpec.value == null && xMLContentSpec.otherValue == null) {
                                stringBuffer.append("#PCDATA");
                            } else if (xMLContentSpec.otherValue != null) {
                                stringBuffer.append("##any:uri=").append(xMLContentSpec.otherValue);
                            } else if (xMLContentSpec.value == null) {
                                stringBuffer.append(SchemaSymbols.ATTVAL_TWOPOUNDANY);
                            }
                            stringBuffer.append(')');
                            c = '*';
                            stringBuffer.append(c);
                        }
                        appendContentSpec(xMLContentSpec, stringBuffer, true, i3);
                        stringBuffer.append(')');
                        c = '*';
                        stringBuffer.append(c);
                        break;
                    case 3:
                        getContentSpec(((int[]) xMLContentSpec.value)[0], xMLContentSpec);
                        short s3 = xMLContentSpec.type;
                        if (s3 != 0) {
                            if (s3 == 3 || s3 == 2 || s3 == 1) {
                                stringBuffer.append('(');
                                appendContentSpec(xMLContentSpec, stringBuffer, true, i3);
                            } else {
                                appendContentSpec(xMLContentSpec, stringBuffer, true, i3);
                            }
                            c = '+';
                            stringBuffer.append(c);
                            break;
                        } else {
                            stringBuffer.append('(');
                            if (xMLContentSpec.value == null && xMLContentSpec.otherValue == null) {
                                stringBuffer.append("#PCDATA");
                            } else if (xMLContentSpec.otherValue != null) {
                                stringBuffer.append("##any:uri=").append(xMLContentSpec.otherValue);
                            } else if (xMLContentSpec.value == null) {
                                stringBuffer.append(SchemaSymbols.ATTVAL_TWOPOUNDANY);
                            } else {
                                stringBuffer.append(xMLContentSpec.value);
                            }
                        }
                        stringBuffer.append(')');
                        c = '+';
                        stringBuffer.append(c);
                        break;
                    case 4:
                    case 5:
                        appendContentSpec(xMLContentSpec, stringBuffer, true, i3);
                        break;
                    case 6:
                        stringBuffer.append(SchemaSymbols.ATTVAL_TWOPOUNDANY);
                        if (xMLContentSpec.otherValue != null) {
                            str = ":uri=";
                            stringBuffer.append(str);
                            stringBuffer.append(xMLContentSpec.otherValue);
                        }
                        break;
                    case 7:
                        str = "##other:uri=";
                        stringBuffer.append(str);
                        stringBuffer.append(xMLContentSpec.otherValue);
                        break;
                    case 8:
                        str2 = SchemaSymbols.ATTVAL_TWOPOUNDLOCAL;
                        stringBuffer.append(str2);
                        break;
                    default:
                        str2 = "???";
                        stringBuffer.append(str2);
                        break;
                }
                return stringBuffer.toString();
            }
        }
        return null;
    }

    public int getContentSpecIndex(int i) {
        if (i < 0 || i >= this.fElementDeclCount) {
            return -1;
        }
        return this.fElementDeclContentSpecIndex[i >> 8][i & 255];
    }

    public short getContentSpecType(int i) {
        if (i < 0 || i >= this.fElementDeclCount) {
            return (short) -1;
        }
        short s = this.fElementDeclType[i >> 8][i & 255];
        if (s == -1) {
            return (short) -1;
        }
        return (short) (s & LIST_MASK);
    }

    @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
    public XMLDTDContentModelSource getDTDContentModelSource() {
        return this.fDTDContentModelSource;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public XMLDTDSource getDTDSource() {
        return this.fDTDSource;
    }

    protected ContentModelValidator getElementContentModelValidator(int i) {
        ContentModelValidator contentModelValidatorCreateChildModel;
        int i2 = i >> 8;
        int i3 = i & 255;
        ContentModelValidator contentModelValidator = this.fElementDeclContentModelValidator[i2][i3];
        if (contentModelValidator != null) {
            return contentModelValidator;
        }
        short s = this.fElementDeclType[i2][i3];
        if (s == 4) {
            return null;
        }
        int i4 = this.fElementDeclContentSpecIndex[i2][i3];
        XMLContentSpec xMLContentSpec = new XMLContentSpec();
        getContentSpec(i4, xMLContentSpec);
        if (s == 2) {
            ChildrenList childrenList = new ChildrenList();
            contentSpecTree(i4, xMLContentSpec, childrenList);
            contentModelValidatorCreateChildModel = new MixedContentModel(childrenList.qname, childrenList.type, 0, childrenList.length, false);
        } else {
            if (s != 3) {
                throw new RuntimeException("Unknown content type for a element decl in getElementContentModelValidator() in AbstractDTDGrammar class");
            }
            contentModelValidatorCreateChildModel = createChildModel(i4);
        }
        this.fElementDeclContentModelValidator[i2][i3] = contentModelValidatorCreateChildModel;
        return contentModelValidatorCreateChildModel;
    }

    public boolean getElementDecl(int i, XMLElementDecl xMLElementDecl) {
        if (i < 0 || i >= this.fElementDeclCount) {
            return false;
        }
        int i2 = i >> 8;
        int i3 = i & 255;
        xMLElementDecl.f474name.setValues(this.fElementDeclName[i2][i3]);
        short s = this.fElementDeclType[i2][i3];
        if (s == -1) {
            xMLElementDecl.type = (short) -1;
            xMLElementDecl.simpleType.list = false;
        } else {
            xMLElementDecl.type = (short) (s & LIST_MASK);
            xMLElementDecl.simpleType.list = (this.fElementDeclType[i2][i3] & 128) != 0;
        }
        if (xMLElementDecl.type == 3 || xMLElementDecl.type == 2) {
            xMLElementDecl.contentModelValidator = getElementContentModelValidator(i);
        }
        xMLElementDecl.simpleType.datatypeValidator = null;
        xMLElementDecl.simpleType.defaultType = (short) -1;
        xMLElementDecl.simpleType.defaultValue = null;
        return true;
    }

    public int getElementDeclIndex(String str) {
        return this.fElementIndexMap.get(str);
    }

    public int getElementDeclIndex(QName qName) {
        return getElementDeclIndex(qName.rawname);
    }

    public boolean getElementDeclIsExternal(int i) {
        if (i < 0) {
            return false;
        }
        return this.fElementDeclIsExternal[i >> 8][i & 255] != 0;
    }

    QName getElementDeclName(int i) {
        if (i < 0 || i >= this.fElementDeclCount) {
            return null;
        }
        return this.fElementDeclName[i >> 8][i & 255];
    }

    public boolean getEntityDecl(int i, XMLEntityDecl xMLEntityDecl) {
        if (i < 0 || i >= this.fEntityCount) {
            return false;
        }
        int i2 = i >> 8;
        int i3 = i & 255;
        xMLEntityDecl.setValues(this.fEntityName[i2][i3], this.fEntityPublicId[i2][i3], this.fEntitySystemId[i2][i3], this.fEntityBaseSystemId[i2][i3], this.fEntityNotation[i2][i3], this.fEntityValue[i2][i3], this.fEntityIsPE[i2][i3] != 0, this.fEntityInExternal[i2][i3] != 0);
        return true;
    }

    public int getEntityDeclIndex(String str) {
        if (str == null) {
            return -1;
        }
        return this.fEntityIndexMap.get(str);
    }

    public int getFirstAttributeDeclIndex(int i) {
        return this.fElementDeclFirstAttributeDeclIndex[i >> 8][i & 255];
    }

    public int getFirstElementDeclIndex() {
        return this.fElementDeclCount >= 0 ? 0 : -1;
    }

    @Override // org.apache.xerces.xni.grammars.Grammar
    public XMLGrammarDescription getGrammarDescription() {
        return this.fGrammarDescription;
    }

    public int getNextAttributeDeclIndex(int i) {
        return this.fAttributeDeclNextAttributeDeclIndex[i >> 8][i & 255];
    }

    public int getNextElementDeclIndex(int i) {
        if (i < this.fElementDeclCount - 1) {
            return i + 1;
        }
        return -1;
    }

    public boolean getNotationDecl(int i, XMLNotationDecl xMLNotationDecl) {
        if (i < 0 || i >= this.fNotationCount) {
            return false;
        }
        int i2 = i >> 8;
        int i3 = i & 255;
        xMLNotationDecl.setValues(this.fNotationName[i2][i3], this.fNotationPublicId[i2][i3], this.fNotationSystemId[i2][i3], this.fNotationBaseSystemId[i2][i3]);
        return true;
    }

    public int getNotationDeclIndex(String str) {
        if (str == null) {
            return -1;
        }
        return this.fNotationIndexMap.get(str);
    }

    public SymbolTable getSymbolTable() {
        return this.fSymbolTable;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void ignoredCharacters(XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    protected void initializeContentModelStack() {
        short[] sArr = this.fOpStack;
        if (sArr == null) {
            this.fOpStack = new short[8];
            this.fNodeIndexStack = new int[8];
            this.fPrevNodeIndexStack = new int[8];
        } else {
            int i = this.fDepth;
            if (i == sArr.length) {
                short[] sArr2 = new short[i * 2];
                System.arraycopy(sArr, 0, sArr2, 0, i);
                this.fOpStack = sArr2;
                int i2 = this.fDepth;
                int[] iArr = new int[i2 * 2];
                System.arraycopy(this.fNodeIndexStack, 0, iArr, 0, i2);
                this.fNodeIndexStack = iArr;
                int i3 = this.fDepth;
                int[] iArr2 = new int[i3 * 2];
                System.arraycopy(this.fPrevNodeIndexStack, 0, iArr2, 0, i3);
                this.fPrevNodeIndexStack = iArr2;
            }
        }
        short[] sArr3 = this.fOpStack;
        int i4 = this.fDepth;
        sArr3[i4] = -1;
        this.fNodeIndexStack[i4] = -1;
        this.fPrevNodeIndexStack[i4] = -1;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void internalEntityDecl(String str, XMLString xMLString, XMLString xMLString2, Augmentations augmentations) throws XNIException {
        if (getEntityDeclIndex(str) == -1) {
            int iCreateEntityDecl = createEntityDecl();
            boolean zStartsWith = str.startsWith("%");
            boolean z = this.fReadingExternalDTD || this.fPEDepth > 0;
            XMLEntityDecl xMLEntityDecl = new XMLEntityDecl();
            xMLEntityDecl.setValues(str, null, null, null, null, xMLString.toString(), zStartsWith, z);
            setEntityDecl(iCreateEntityDecl, xMLEntityDecl);
        }
    }

    public boolean isCDATAAttribute(QName qName, QName qName2) {
        return !getAttributeDecl(getElementDeclIndex(qName), this.fAttributeDecl) || this.fAttributeDecl.simpleType.type == 0;
    }

    @Override // org.apache.xerces.impl.validation.EntityState
    public boolean isEntityDeclared(String str) {
        return getEntityDeclIndex(str) != -1;
    }

    @Override // org.apache.xerces.impl.validation.EntityState
    public boolean isEntityUnparsed(String str) {
        int entityDeclIndex = getEntityDeclIndex(str);
        if (entityDeclIndex > -1) {
            return this.fEntityNotation[entityDeclIndex >> 8][entityDeclIndex & 255] != null;
        }
        return false;
    }

    boolean isImmutable() {
        return this.fIsImmutable;
    }

    public boolean isNamespaceAware() {
        return false;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void notationDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        XMLNotationDecl xMLNotationDecl = new XMLNotationDecl();
        xMLNotationDecl.setValues(str, xMLResourceIdentifier.getPublicId(), xMLResourceIdentifier.getLiteralSystemId(), xMLResourceIdentifier.getBaseSystemId());
        if (getNotationDeclIndex(str) == -1) {
            setNotationDecl(createNotationDecl(), xMLNotationDecl);
        }
    }

    public void occurrence(short s, Augmentations augmentations) throws XNIException {
        if (this.fMixed) {
            return;
        }
        if (s == 2) {
            int[] iArr = this.fNodeIndexStack;
            int i = this.fDepth;
            iArr[i] = addContentSpecNode((short) 1, iArr[i], -1);
        } else if (s == 3) {
            int[] iArr2 = this.fNodeIndexStack;
            int i2 = this.fDepth;
            iArr2[i2] = addContentSpecNode((short) 2, iArr2[i2], -1);
        } else if (s == 4) {
            int[] iArr3 = this.fNodeIndexStack;
            int i3 = this.fDepth;
            iArr3[i3] = addContentSpecNode((short) 3, iArr3[i3], -1);
        }
    }

    public void pcdata(Augmentations augmentations) throws XNIException {
        this.fMixed = true;
    }

    public void printAttributes(int i) {
        int firstAttributeDeclIndex = getFirstAttributeDeclIndex(i);
        System.out.print(i);
        PrintStream printStream = System.out;
        String str = " [";
        while (true) {
            printStream.print(str);
            do {
                if (firstAttributeDeclIndex == -1) {
                    System.out.println(" ]");
                    return;
                }
                System.out.print(StringUtil.SPACE);
                System.out.print(firstAttributeDeclIndex);
                printAttribute(firstAttributeDeclIndex);
                firstAttributeDeclIndex = getNextAttributeDeclIndex(firstAttributeDeclIndex);
            } while (firstAttributeDeclIndex == -1);
            printStream = System.out;
            str = ",";
        }
    }

    public void printElements() {
        XMLElementDecl xMLElementDecl = new XMLElementDecl();
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!getElementDecl(i, xMLElementDecl)) {
                return;
            }
            System.out.println("element decl: " + xMLElementDecl.f474name + ", " + xMLElementDecl.f474name.rawname);
            i = i2;
        }
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    protected void putElementNameMapping(QName qName, int i, int i2) {
    }

    public void separator(short s, Augmentations augmentations) throws XNIException {
        if (this.fMixed) {
            return;
        }
        short[] sArr = this.fOpStack;
        int i = this.fDepth;
        short s2 = sArr[i];
        if (s2 != 5 && s == 0) {
            int i2 = this.fPrevNodeIndexStack[i];
            if (i2 != -1) {
                int[] iArr = this.fNodeIndexStack;
                iArr[i] = addContentSpecNode(s2, i2, iArr[i]);
            }
            int[] iArr2 = this.fPrevNodeIndexStack;
            int i3 = this.fDepth;
            iArr2[i3] = this.fNodeIndexStack[i3];
            this.fOpStack[i3] = 4;
            return;
        }
        if (s2 == 4 || s != 1) {
            return;
        }
        int i4 = this.fPrevNodeIndexStack[i];
        if (i4 != -1) {
            int[] iArr3 = this.fNodeIndexStack;
            iArr3[i] = addContentSpecNode(s2, i4, iArr3[i]);
        }
        int[] iArr4 = this.fPrevNodeIndexStack;
        int i5 = this.fDepth;
        iArr4[i5] = this.fNodeIndexStack[i5];
        this.fOpStack[i5] = 5;
    }

    protected void setAttributeDecl(int i, int i2, XMLAttributeDecl xMLAttributeDecl) {
        int i3 = i2 >> 8;
        int i4 = i2 & 255;
        this.fAttributeDeclName[i3][i4].setValues(xMLAttributeDecl.f473name);
        this.fAttributeDeclType[i3][i4] = xMLAttributeDecl.simpleType.type;
        if (xMLAttributeDecl.simpleType.list) {
            short[] sArr = this.fAttributeDeclType[i3];
            sArr[i4] = (short) (sArr[i4] | 128);
        }
        this.fAttributeDeclEnumeration[i3][i4] = xMLAttributeDecl.simpleType.enumeration;
        this.fAttributeDeclDefaultType[i3][i4] = xMLAttributeDecl.simpleType.defaultType;
        this.fAttributeDeclDatatypeValidator[i3][i4] = xMLAttributeDecl.simpleType.datatypeValidator;
        this.fAttributeDeclDefaultValue[i3][i4] = xMLAttributeDecl.simpleType.defaultValue;
        this.fAttributeDeclNonNormalizedDefaultValue[i3][i4] = xMLAttributeDecl.simpleType.nonNormalizedDefaultValue;
        int i5 = i >> 8;
        int i6 = i & 255;
        int i7 = this.fElementDeclFirstAttributeDeclIndex[i5][i6];
        while (i7 != -1 && i7 != i2) {
            i7 = this.fAttributeDeclNextAttributeDeclIndex[i7 >> 8][i7 & 255];
        }
        if (i7 == -1) {
            int[] iArr = this.fElementDeclFirstAttributeDeclIndex[i5];
            if (iArr[i6] == -1) {
                iArr[i6] = i2;
            } else {
                int i8 = this.fElementDeclLastAttributeDeclIndex[i5][i6];
                this.fAttributeDeclNextAttributeDeclIndex[i8 >> 8][i8 & 255] = i2;
            }
            this.fElementDeclLastAttributeDeclIndex[i5][i6] = i2;
        }
    }

    protected void setContentSpec(int i, XMLContentSpec xMLContentSpec) {
        int i2 = i >> 8;
        int i3 = i & 255;
        this.fContentSpecType[i2][i3] = xMLContentSpec.type;
        this.fContentSpecValue[i2][i3] = xMLContentSpec.value;
        this.fContentSpecOtherValue[i2][i3] = xMLContentSpec.otherValue;
    }

    protected void setContentSpecIndex(int i, int i2) {
        if (i < 0 || i >= this.fElementDeclCount) {
            return;
        }
        this.fElementDeclContentSpecIndex[i >> 8][i & 255] = i2;
    }

    @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
    public void setDTDContentModelSource(XMLDTDContentModelSource xMLDTDContentModelSource) {
        this.fDTDContentModelSource = xMLDTDContentModelSource;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void setDTDSource(XMLDTDSource xMLDTDSource) {
        this.fDTDSource = xMLDTDSource;
    }

    protected void setElementDecl(int i, XMLElementDecl xMLElementDecl) {
        if (i < 0 || i >= this.fElementDeclCount) {
            return;
        }
        int i2 = i >> 8;
        int i3 = i & 255;
        this.fElementDeclName[i2][i3].setValues(xMLElementDecl.f474name);
        this.fElementDeclType[i2][i3] = xMLElementDecl.type;
        this.fElementDeclContentModelValidator[i2][i3] = xMLElementDecl.contentModelValidator;
        if (xMLElementDecl.simpleType.list) {
            short[] sArr = this.fElementDeclType[i2];
            sArr[i3] = (short) (sArr[i3] | 128);
        }
        this.fElementIndexMap.put(xMLElementDecl.f474name.rawname, i);
    }

    protected void setEntityDecl(int i, XMLEntityDecl xMLEntityDecl) {
        int i2 = i >> 8;
        int i3 = i & 255;
        this.fEntityName[i2][i3] = xMLEntityDecl.f475name;
        this.fEntityValue[i2][i3] = xMLEntityDecl.value;
        this.fEntityPublicId[i2][i3] = xMLEntityDecl.publicId;
        this.fEntitySystemId[i2][i3] = xMLEntityDecl.systemId;
        this.fEntityBaseSystemId[i2][i3] = xMLEntityDecl.baseSystemId;
        this.fEntityNotation[i2][i3] = xMLEntityDecl.notation;
        this.fEntityIsPE[i2][i3] = xMLEntityDecl.isPE;
        this.fEntityInExternal[i2][i3] = xMLEntityDecl.inExternal;
        this.fEntityIndexMap.put(xMLEntityDecl.f475name, i);
    }

    protected void setFirstAttributeDeclIndex(int i, int i2) {
        if (i < 0 || i >= this.fElementDeclCount) {
            return;
        }
        this.fElementDeclFirstAttributeDeclIndex[i >> 8][i & 255] = i2;
    }

    protected void setNotationDecl(int i, XMLNotationDecl xMLNotationDecl) {
        int i2 = i >> 8;
        int i3 = i & 255;
        this.fNotationName[i2][i3] = xMLNotationDecl.f476name;
        this.fNotationPublicId[i2][i3] = xMLNotationDecl.publicId;
        this.fNotationSystemId[i2][i3] = xMLNotationDecl.systemId;
        this.fNotationBaseSystemId[i2][i3] = xMLNotationDecl.baseSystemId;
        this.fNotationIndexMap.put(xMLNotationDecl.f476name, i);
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void startAttlist(String str, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void startConditional(short s, Augmentations augmentations) throws XNIException {
    }

    public void startContentModel(String str, Augmentations augmentations) throws XNIException {
        XMLElementDecl xMLElementDecl = (XMLElementDecl) this.fElementDeclTab.get(str);
        if (xMLElementDecl != null) {
            this.fElementDecl = xMLElementDecl;
        }
        this.fDepth = 0;
        initializeContentModelStack();
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void startDTD(XMLLocator xMLLocator, Augmentations augmentations) throws XNIException {
        this.fOpStack = null;
        this.fNodeIndexStack = null;
        this.fPrevNodeIndexStack = null;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void startExternalSubset(XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        this.fReadingExternalDTD = true;
    }

    public void startGroup(Augmentations augmentations) throws XNIException {
        this.fDepth++;
        initializeContentModelStack();
        this.fMixed = false;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void startParameterEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        int i = this.fPEDepth;
        boolean[] zArr = this.fPEntityStack;
        if (i == zArr.length) {
            boolean[] zArr2 = new boolean[zArr.length * 2];
            System.arraycopy(zArr, 0, zArr2, 0, zArr.length);
            this.fPEntityStack = zArr2;
        }
        boolean[] zArr3 = this.fPEntityStack;
        int i2 = this.fPEDepth;
        zArr3[i2] = this.fReadingExternalDTD;
        this.fPEDepth = i2 + 1;
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void textDecl(String str, String str2, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDTDHandler
    public void unparsedEntityDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        XMLEntityDecl xMLEntityDecl = new XMLEntityDecl();
        xMLEntityDecl.setValues(str, xMLResourceIdentifier.getPublicId(), xMLResourceIdentifier.getLiteralSystemId(), xMLResourceIdentifier.getBaseSystemId(), str2, null, str.startsWith("%"), this.fReadingExternalDTD || this.fPEDepth > 0);
        if (getEntityDeclIndex(str) == -1) {
            setEntityDecl(createEntityDecl(), xMLEntityDecl);
        }
    }
}
