package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.bytebuddy.description.type.TypeDescription;
import okhttp3.HttpUrl;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassReferenceListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiTypeParameterStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.cls.ClsFormatException;
import org.jetbrains.org.objectweb.asm.TypeReference;

/* JADX INFO: loaded from: classes3.dex */
public final class SignatureParsing {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/SignatureParsing";
        if (i != 1) {
            objArr[1] = "parseTypeParametersDeclaration";
        } else {
            objArr[1] = "parseTypeString";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    private SignatureParsing() {
    }

    static class TypeParametersDeclaration {
        static final TypeParametersDeclaration EMPTY = new TypeParametersDeclaration(Collections.emptyList());
        private final List<TypeParameterDeclaration> myDeclarations;

        private TypeParametersDeclaration(List<TypeParameterDeclaration> list) {
            this.myDeclarations = list;
        }

        TypeInfo getBoundType(TypeReference typeReference) {
            int typeParameterIndex = typeReference.getTypeParameterIndex();
            int typeParameterBoundIndex = typeReference.getTypeParameterBoundIndex();
            if (typeParameterIndex >= this.myDeclarations.size()) {
                return null;
            }
            TypeParameterDeclaration typeParameterDeclaration = this.myDeclarations.get(typeParameterIndex);
            if (typeParameterBoundIndex < typeParameterDeclaration.myBounds.length) {
                return typeParameterDeclaration.myBounds[typeParameterBoundIndex];
            }
            return null;
        }

        TypeInfo getParameterType(TypeReference typeReference) {
            int typeParameterIndex = typeReference.getTypeParameterIndex();
            if (typeParameterIndex < this.myDeclarations.size()) {
                return this.myDeclarations.get(typeParameterIndex).myTypeParameter;
            }
            return null;
        }

        void fillInTypeParameterList(StubElement<?> stubElement) {
            PsiTypeParameterListStub psiTypeParameterListStub = (PsiTypeParameterListStub) stubElement.findChildStubByType(JavaStubElementTypes.TYPE_PARAMETER_LIST);
            if (psiTypeParameterListStub == null) {
                return;
            }
            Iterator<TypeParameterDeclaration> it = this.myDeclarations.iterator();
            while (it.hasNext()) {
                it.next().createTypeParameter(psiTypeParameterListStub);
            }
        }
    }

    private static class TypeParameterDeclaration {
        private final TypeInfo[] myBounds;
        private final TypeInfo myTypeParameter;

        private TypeParameterDeclaration(String str, TypeInfo[] typeInfoArr) {
            this.myTypeParameter = new TypeInfo(str);
            this.myBounds = typeInfoArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void createTypeParameter(PsiTypeParameterListStub psiTypeParameterListStub) {
            PsiTypeParameterStubImpl psiTypeParameterStubImpl = new PsiTypeParameterStubImpl(psiTypeParameterListStub, this.myTypeParameter.text);
            this.myTypeParameter.getTypeAnnotations().createAnnotationStubs(psiTypeParameterStubImpl);
            TypeInfo[] typeInfoArr = this.myBounds;
            if (typeInfoArr.length > 0 && typeInfoArr[0].text == null) {
                typeInfoArr = (TypeInfo[]) Arrays.copyOfRange(typeInfoArr, 1, typeInfoArr.length);
            }
            new PsiClassReferenceListStubImpl(JavaStubElementTypes.EXTENDS_BOUND_LIST, psiTypeParameterStubImpl, typeInfoArr);
        }
    }

    static TypeParametersDeclaration parseTypeParametersDeclaration(CharacterIterator characterIterator, Function<? super String, String> function) throws ClsFormatException {
        if (characterIterator.current() != '<') {
            TypeParametersDeclaration typeParametersDeclaration = TypeParametersDeclaration.EMPTY;
            if (typeParametersDeclaration == null) {
                $$$reportNull$$$0(0);
            }
            return typeParametersDeclaration;
        }
        ArrayList arrayList = new ArrayList();
        characterIterator.next();
        while (characterIterator.current() != '>') {
            arrayList.add(parseTypeParameter(characterIterator, function));
        }
        characterIterator.next();
        return new TypeParametersDeclaration(arrayList);
    }

    private static TypeParameterDeclaration parseTypeParameter(CharacterIterator characterIterator, Function<? super String, String> function) throws ClsFormatException {
        StringBuilder sb = new StringBuilder();
        while (characterIterator.current() != ':' && characterIterator.current() != 65535) {
            sb.append(characterIterator.current());
            characterIterator.next();
        }
        if (characterIterator.current() == 65535) {
            throw new ClsFormatException();
        }
        String strFun = function.fun(sb.toString());
        SmartList smartList = new SmartList();
        while (characterIterator.current() == ':') {
            characterIterator.next();
            String topLevelClassRefSignature = parseTopLevelClassRefSignature(characterIterator, function);
            if (smartList.isEmpty() || topLevelClassRefSignature != null) {
                smartList.add(new TypeInfo(topLevelClassRefSignature));
            }
        }
        return new TypeParameterDeclaration(strFun, (TypeInfo[]) smartList.toArray(TypeInfo.EMPTY_ARRAY));
    }

    public static String parseTopLevelClassRefSignature(CharacterIterator characterIterator, Function<? super String, String> function) throws ClsFormatException {
        char cCurrent = characterIterator.current();
        if (cCurrent == 'L') {
            return parseParameterizedClassRefSignature(characterIterator, function);
        }
        if (cCurrent != 'T') {
            return null;
        }
        return parseTypeVariableRefSignature(characterIterator);
    }

    private static String parseTypeVariableRefSignature(CharacterIterator characterIterator) throws ClsFormatException {
        StringBuilder sb = new StringBuilder();
        characterIterator.next();
        while (characterIterator.current() != ';' && characterIterator.current() != '>' && characterIterator.current() != 65535) {
            sb.append(characterIterator.current());
            characterIterator.next();
        }
        if (characterIterator.current() == 65535) {
            throw new ClsFormatException();
        }
        if (characterIterator.current() == ';') {
            characterIterator.next();
        }
        return sb.toString();
    }

    private static String parseParameterizedClassRefSignature(CharacterIterator characterIterator, Function<? super String, String> function) throws ClsFormatException {
        StringBuilder sb = new StringBuilder();
        characterIterator.next();
        boolean z = false;
        while (characterIterator.current() != ';' && characterIterator.current() != 65535) {
            char cCurrent = characterIterator.current();
            char c = '<';
            if (cCurrent == '<') {
                StringBuilder sb2 = new StringBuilder(function.fun(sb.toString()));
                characterIterator.next();
                while (true) {
                    sb2.append(c).append(parseClassOrTypeVariableElement(characterIterator, function));
                    if (characterIterator.current() == '>') {
                        break;
                    }
                    c = ',';
                }
                sb2.append('>');
                z = true;
                sb = sb2;
            } else if (cCurrent != ' ') {
                sb.append(cCurrent);
            }
            characterIterator.next();
        }
        if (characterIterator.current() == 65535) {
            throw new ClsFormatException();
        }
        characterIterator.next();
        String string = sb.toString();
        return !z ? function.fun(string) : string;
    }

    private static String parseClassOrTypeVariableElement(CharacterIterator characterIterator, Function<? super String, String> function) throws ClsFormatException {
        char variance = parseVariance(characterIterator);
        if (variance == '*') {
            return decorateTypeText(null, variance);
        }
        int dimensions = parseDimensions(characterIterator);
        String typeWithoutVariance = parseTypeWithoutVariance(characterIterator, function);
        if (typeWithoutVariance == null) {
            throw new ClsFormatException();
        }
        if (dimensions > 0) {
            typeWithoutVariance = typeWithoutVariance + StringUtil.repeat(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, dimensions);
        }
        return decorateTypeText(typeWithoutVariance, variance);
    }

    private static String decorateTypeText(String str, char c) {
        if (c == 0) {
            return str;
        }
        if (c == '-') {
            return PsiWildcardType.SUPER_PREFIX + str;
        }
        if (c == '*') {
            return TypeDescription.Generic.OfWildcardType.SYMBOL;
        }
        if (c != '+') {
            return null;
        }
        return PsiWildcardType.EXTENDS_PREFIX + str;
    }

    private static char parseVariance(CharacterIterator characterIterator) {
        char cCurrent = characterIterator.current();
        if (cCurrent == '*' || cCurrent == '+' || cCurrent == '-') {
            char cCurrent2 = characterIterator.current();
            characterIterator.next();
            return cCurrent2;
        }
        if (cCurrent == '.' || cCurrent == '=') {
            characterIterator.next();
        }
        return (char) 0;
    }

    private static int parseDimensions(CharacterIterator characterIterator) {
        int i = 0;
        while (characterIterator.current() == '[') {
            i++;
            characterIterator.next();
        }
        return i;
    }

    public static String parseTypeString(CharacterIterator characterIterator, Function<? super String, String> function) throws ClsFormatException {
        int dimensions = parseDimensions(characterIterator);
        String typeWithoutVariance = parseTypeWithoutVariance(characterIterator, function);
        if (typeWithoutVariance == null) {
            throw new ClsFormatException();
        }
        if (dimensions > 0) {
            typeWithoutVariance = typeWithoutVariance + StringUtil.repeat(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, dimensions);
        }
        if (typeWithoutVariance == null) {
            $$$reportNull$$$0(1);
        }
        return typeWithoutVariance;
    }

    private static String parseTypeWithoutVariance(CharacterIterator characterIterator, Function<? super String, String> function) throws ClsFormatException {
        char cCurrent = characterIterator.current();
        if (cCurrent == 'F') {
            characterIterator.next();
            return "float";
        }
        if (cCurrent == 'L') {
            return parseParameterizedClassRefSignature(characterIterator, function);
        }
        if (cCurrent == 'V') {
            characterIterator.next();
            return PsiKeyword.VOID;
        }
        if (cCurrent == 'Z') {
            characterIterator.next();
            return "boolean";
        }
        if (cCurrent == 'I') {
            characterIterator.next();
            return "int";
        }
        if (cCurrent == 'J') {
            characterIterator.next();
            return "long";
        }
        if (cCurrent == 'S') {
            characterIterator.next();
            return "short";
        }
        if (cCurrent == 'T') {
            return parseTypeVariableRefSignature(characterIterator);
        }
        switch (cCurrent) {
            case 'B':
                characterIterator.next();
                return "byte";
            case 'C':
                characterIterator.next();
                return PsiKeyword.CHAR;
            case 'D':
                characterIterator.next();
                return "double";
            default:
                return null;
        }
    }
}
