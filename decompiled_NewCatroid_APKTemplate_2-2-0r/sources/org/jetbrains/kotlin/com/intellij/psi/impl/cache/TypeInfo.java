package org.jetbrains.kotlin.com.intellij.psi.impl.cache;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import okhttp3.HttpUrl;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class TypeInfo {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TypeInfo NULL;
    public final byte arrayCount;
    public final boolean isEllipsis;
    private TypeAnnotationContainer myTypeAnnotations;
    public final String text;
    public static final TypeInfo[] EMPTY_ARRAY = new TypeInfo[0];
    private static final String[] ourIndexFrequentType = {"", "boolean", "byte", PsiKeyword.CHAR, "double", "float", "int", "long", "null", "short", PsiKeyword.VOID, CommonClassNames.JAVA_LANG_OBJECT_SHORT, CommonClassNames.JAVA_LANG_OBJECT, CommonClassNames.JAVA_LANG_STRING_SHORT, CommonClassNames.JAVA_LANG_STRING};
    private static final Object2IntMap<String> ourFrequentTypeIndex = new Object2IntOpenHashMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 6 || i == 16 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 6 || i == 16 || i == 10 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 10:
            case 11:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/cache/TypeInfo";
                break;
            case 4:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 5:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 7:
            case 8:
                objArr[0] = "typeText";
                break;
            case 9:
                objArr[0] = PsiKeyword.RECORD;
                break;
            case 12:
                objArr[0] = "dataStream";
                break;
            case 13:
            case 14:
                objArr[0] = "typeInfo";
                break;
            case 15:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            default:
                objArr[0] = "typeAnnotations";
                break;
        }
        if (i == 1) {
            objArr[1] = "getTypeAnnotations";
        } else if (i == 2) {
            objArr[1] = "getShortTypeText";
        } else if (i == 3) {
            objArr[1] = "createConstructorType";
        } else if (i == 6) {
            objArr[1] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else if (i == 16) {
            objArr[1] = "internFrequentType";
        } else if (i == 10 || i == 11) {
            objArr[1] = "readTYPE";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/cache/TypeInfo";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 10:
            case 11:
            case 16:
                break;
            case 4:
            case 5:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 7:
            case 8:
                objArr[2] = "fromString";
                break;
            case 9:
                objArr[2] = "readTYPE";
                break;
            case 12:
            case 13:
                objArr[2] = "writeTYPE";
                break;
            case 14:
                objArr[2] = "createTypeText";
                break;
            case 15:
                objArr[2] = "internFrequentType";
                break;
            default:
                objArr[2] = "setTypeAnnotations";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 6 && i != 16 && i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static {
        int i = 0;
        while (true) {
            String[] strArr = ourIndexFrequentType;
            if (i < strArr.length) {
                ourFrequentTypeIndex.put(strArr[i], i);
                i++;
            } else {
                NULL = new TypeInfo(null);
                return;
            }
        }
    }

    public TypeInfo(String str) {
        this(str, (byte) 0, false);
    }

    public TypeInfo(String str, byte b, boolean z) {
        this.text = str == null ? null : internFrequentType(str);
        this.arrayCount = b;
        this.isEllipsis = z;
    }

    public void setTypeAnnotations(TypeAnnotationContainer typeAnnotationContainer) {
        if (typeAnnotationContainer == null) {
            $$$reportNull$$$0(0);
        }
        if (this.myTypeAnnotations != null) {
            throw new IllegalStateException();
        }
        this.myTypeAnnotations = typeAnnotationContainer;
    }

    public TypeAnnotationContainer getTypeAnnotations() {
        TypeAnnotationContainer typeAnnotationContainer = this.myTypeAnnotations;
        if (typeAnnotationContainer == null) {
            typeAnnotationContainer = TypeAnnotationContainer.EMPTY;
        }
        if (typeAnnotationContainer == null) {
            $$$reportNull$$$0(1);
        }
        return typeAnnotationContainer;
    }

    public String getShortTypeText() {
        String str = this.text;
        if (str == null) {
            return "";
        }
        String shortClassName = PsiNameHelper.getShortClassName(str);
        if (this.arrayCount > 0) {
            shortClassName = shortClassName + StringUtil.repeat(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, this.arrayCount);
        }
        if (shortClassName == null) {
            $$$reportNull$$$0(2);
        }
        return shortClassName;
    }

    public String toString() {
        String strCreateTypeText = createTypeText(this);
        return strCreateTypeText != null ? strCreateTypeText : "null";
    }

    public static TypeInfo createConstructorType() {
        TypeInfo typeInfo = NULL;
        if (typeInfo == null) {
            $$$reportNull$$$0(3);
        }
        return typeInfo;
    }

    /* JADX WARN: Code duplicated, block: B:45:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:48:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:59:0x00e6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:61:0x00e4 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:62:0x00ed A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:63:0x00ea A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:64:0x00f1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:66:0x00d0 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v12, types: [byte] */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16, types: [int] */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21 */
    public static TypeInfo create(LighterAST lighterAST, LighterASTNode lighterASTNode, StubElement<?> stubElement) {
        LighterASTNode lighterASTNodeFirstChildOfType;
        String filteredString;
        boolean z;
        boolean z2;
        int i;
        TypeInfo typeInfo;
        TypeAnnotationContainer.Collector collector;
        IElementType tokenType;
        byte b;
        if (lighterAST == null) {
            $$$reportNull$$$0(4);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(5);
        }
        if (lighterASTNode.getTokenType() == JavaElementType.ENUM_CONSTANT) {
            filteredString = ((PsiClassStub) stubElement).getName();
            lighterASTNodeFirstChildOfType = null;
            b = 0;
            z = false;
        } else {
            lighterASTNodeFirstChildOfType = null;
            byte b2 = 0;
            for (LighterASTNode lighterASTNode2 : lighterAST.getChildren(lighterASTNode)) {
                IElementType tokenType2 = lighterASTNode2.getTokenType();
                if (tokenType2 == JavaElementType.TYPE) {
                    lighterASTNodeFirstChildOfType = lighterASTNode2;
                } else if (tokenType2 == JavaTokenType.LBRACKET) {
                    b2 = (byte) (b2 + 1);
                }
            }
            if (lighterASTNodeFirstChildOfType == null && lighterASTNode.getTokenType() == JavaElementType.FIELD) {
                List<LighterASTNode> childrenOfType = LightTreeUtil.getChildrenOfType(lighterAST, lighterAST.getParent(lighterASTNode), JavaElementType.FIELD);
                for (int iIndexOf = childrenOfType.indexOf(lighterASTNode) - 1; iIndexOf >= 0 && lighterASTNodeFirstChildOfType == null; iIndexOf--) {
                    lighterASTNodeFirstChildOfType = LightTreeUtil.firstChildOfType(lighterAST, childrenOfType.get(iIndexOf), JavaElementType.TYPE);
                }
            }
            LighterASTNode lighterASTNodeFirstChildOfType2 = LightTreeUtil.firstChildOfType(lighterAST, lighterASTNodeFirstChildOfType, JavaElementType.TYPE);
            if (lighterASTNodeFirstChildOfType2 != null) {
                Iterator<LighterASTNode> it = lighterAST.getChildren(lighterASTNodeFirstChildOfType).iterator();
                z = false;
                z2 = false;
                while (it.hasNext()) {
                    IElementType tokenType3 = it.next().getTokenType();
                    if (tokenType3 == JavaTokenType.LBRACKET) {
                        b2 = (byte) (b2 + 1);
                    } else if (tokenType3 == JavaTokenType.ELLIPSIS) {
                        b2 = (byte) (b2 + 1);
                        z = true;
                    } else if (tokenType3 == JavaElementType.ANNOTATION) {
                        z2 = true;
                    }
                }
                filteredString = LightTreeUtil.toFilteredString(lighterAST, lighterASTNodeFirstChildOfType2, null);
                i = b2;
            } else {
                filteredString = LightTreeUtil.toFilteredString(lighterAST, lighterASTNodeFirstChildOfType, null);
                z = false;
                b = b2;
            }
            typeInfo = new TypeInfo(filteredString, i, z);
            if (z2) {
                collector = new TypeAnnotationContainer.Collector(typeInfo);
                for (LighterASTNode lighterASTNode3 : lighterAST.getChildren(lighterASTNodeFirstChildOfType)) {
                    tokenType = lighterASTNode3.getTokenType();
                    if (tokenType == JavaElementType.TYPE) {
                        i = 0;
                    } else if (tokenType == JavaTokenType.LBRACKET) {
                        i++;
                    } else if (tokenType == JavaElementType.ANNOTATION) {
                        String filteredString2 = LightTreeUtil.toFilteredString(lighterAST, lighterASTNode3, null);
                        byte[] bArr = new byte[i];
                        Arrays.fill(bArr, (byte) 0);
                        collector.add(bArr, filteredString2);
                    }
                }
                collector.install();
            }
            return typeInfo;
        }
        z2 = z;
        i = b;
        typeInfo = new TypeInfo(filteredString, i, z);
        if (z2) {
            collector = new TypeAnnotationContainer.Collector(typeInfo);
            while (r0.hasNext()) {
                tokenType = lighterASTNode3.getTokenType();
                if (tokenType == JavaElementType.TYPE) {
                    i = 0;
                } else if (tokenType == JavaTokenType.LBRACKET) {
                    i++;
                } else if (tokenType == JavaElementType.ANNOTATION) {
                    String filteredString3 = LightTreeUtil.toFilteredString(lighterAST, lighterASTNode3, null);
                    byte[] bArr2 = new byte[i];
                    Arrays.fill(bArr2, (byte) 0);
                    collector.add(bArr2, filteredString3);
                }
            }
            collector.install();
        }
        return typeInfo;
    }

    public static TypeInfo fromString(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        byte b = 0;
        while (str.endsWith(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI)) {
            b = (byte) (b + 1);
            str = str.substring(0, str.length() - 2);
        }
        return new TypeInfo(str, b, z);
    }

    public static TypeInfo fromString(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        boolean z = false;
        if (str.endsWith("...")) {
            str = str.substring(0, str.length() - 3);
            z = true;
        }
        return fromString(str, z);
    }

    public static TypeInfo readTYPE(StubInputStream stubInputStream) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(9);
        }
        byte b = stubInputStream.readByte();
        int i = b & 255;
        if (i == 31) {
            TypeInfo typeInfo = NULL;
            if (typeInfo == null) {
                $$$reportNull$$$0(10);
            }
            return typeInfo;
        }
        byte b2 = BitUtil.isSet(i, 64) ? stubInputStream.readByte() : (byte) 0;
        boolean zIsSet = BitUtil.isSet(i, 128);
        boolean zIsSet2 = BitUtil.isSet(i, 32);
        int i2 = b & 31;
        TypeInfo typeInfo2 = new TypeInfo(i2 == 0 ? stubInputStream.readNameString() : ourIndexFrequentType[i2], b2, zIsSet);
        typeInfo2.setTypeAnnotations(zIsSet2 ? TypeAnnotationContainer.readTypeAnnotations(stubInputStream) : TypeAnnotationContainer.EMPTY);
        return typeInfo2;
    }

    public static void writeTYPE(StubOutputStream stubOutputStream, TypeInfo typeInfo) throws IOException {
        if (stubOutputStream == null) {
            $$$reportNull$$$0(12);
        }
        if (typeInfo == null) {
            $$$reportNull$$$0(13);
        }
        if (typeInfo == NULL) {
            stubOutputStream.writeByte(31);
            return;
        }
        String str = typeInfo.text;
        byte b = typeInfo.arrayCount;
        int i = ourFrequentTypeIndex.getInt(str);
        TypeAnnotationContainer typeAnnotationContainer = typeInfo.myTypeAnnotations;
        boolean z = (typeAnnotationContainer == null || typeAnnotationContainer.isEmpty()) ? false : true;
        stubOutputStream.writeByte((z ? 32 : 0) | (typeInfo.isEllipsis ? 128 : 0) | (b != 0 ? 64 : 0) | i);
        if (b != 0) {
            stubOutputStream.writeByte(b);
        }
        if (i == 0) {
            stubOutputStream.writeName(str);
        }
        if (z) {
            TypeAnnotationContainer.writeTypeAnnotations(stubOutputStream, typeInfo.myTypeAnnotations);
        }
    }

    public static String createTypeText(TypeInfo typeInfo) {
        String str;
        if (typeInfo == null) {
            $$$reportNull$$$0(14);
        }
        if (typeInfo == NULL || (str = typeInfo.text) == null) {
            return null;
        }
        if (typeInfo.arrayCount == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(typeInfo.text);
        int i = typeInfo.isEllipsis ? typeInfo.arrayCount - 1 : typeInfo.arrayCount;
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        }
        if (typeInfo.isEllipsis) {
            sb.append("...");
        }
        return internFrequentType(sb.toString());
    }

    public static String internFrequentType(String str) {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        int i = ourFrequentTypeIndex.getInt(str);
        String strInternEmptyString = i == 0 ? StringUtil.internEmptyString(str) : ourIndexFrequentType[i];
        if (strInternEmptyString == null) {
            $$$reportNull$$$0(16);
        }
        return strInternEmptyString;
    }
}
