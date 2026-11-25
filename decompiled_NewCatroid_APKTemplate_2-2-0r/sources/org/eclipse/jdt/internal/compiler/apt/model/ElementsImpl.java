package org.eclipse.jdt.internal.compiler.apt.model;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import org.apache.commons.lang3.SystemProperties;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Javadoc;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;

/* JADX INFO: loaded from: classes14.dex */
public class ElementsImpl implements Elements {
    private static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind;
    private static final Pattern INITIAL_DELIMITER = Pattern.compile("^\\s*/\\*+");
    private final BaseProcessingEnvImpl _env;

    static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind() {
        int[] iArr = $SWITCH_TABLE$javax$lang$model$element$ElementKind;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[ElementKind.values().length];
        try {
            iArr2[ElementKind.ANNOTATION_TYPE.ordinal()] = 4;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[ElementKind.CLASS.ordinal()] = 3;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[ElementKind.CONSTRUCTOR.ordinal()] = 12;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[ElementKind.ENUM.ordinal()] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[ElementKind.ENUM_CONSTANT.ordinal()] = 6;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[ElementKind.EXCEPTION_PARAMETER.ordinal()] = 10;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            iArr2[ElementKind.FIELD.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            iArr2[ElementKind.INSTANCE_INIT.ordinal()] = 14;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            iArr2[ElementKind.INTERFACE.ordinal()] = 5;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            iArr2[ElementKind.LOCAL_VARIABLE.ordinal()] = 9;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            iArr2[ElementKind.METHOD.ordinal()] = 11;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            iArr2[ElementKind.OTHER.ordinal()] = 16;
        } catch (NoSuchFieldError unused12) {
        }
        try {
            iArr2[ElementKind.PACKAGE.ordinal()] = 1;
        } catch (NoSuchFieldError unused13) {
        }
        try {
            iArr2[ElementKind.PARAMETER.ordinal()] = 8;
        } catch (NoSuchFieldError unused14) {
        }
        try {
            iArr2[ElementKind.RESOURCE_VARIABLE.ordinal()] = 17;
        } catch (NoSuchFieldError unused15) {
        }
        try {
            iArr2[ElementKind.STATIC_INIT.ordinal()] = 13;
        } catch (NoSuchFieldError unused16) {
        }
        try {
            iArr2[ElementKind.TYPE_PARAMETER.ordinal()] = 15;
        } catch (NoSuchFieldError unused17) {
        }
        $SWITCH_TABLE$javax$lang$model$element$ElementKind = iArr2;
        return iArr2;
    }

    public ElementsImpl(BaseProcessingEnvImpl baseProcessingEnvImpl) {
        this._env = baseProcessingEnvImpl;
    }

    public List<? extends AnnotationMirror> getAllAnnotationMirrors(Element element) {
        if (element.getKind() == ElementKind.CLASS && (element instanceof TypeElementImpl)) {
            ArrayList arrayList = new ArrayList();
            HashSet hashSet = new HashSet();
            ReferenceBinding referenceBindingSuperclass = (ReferenceBinding) ((TypeElementImpl) element)._binding;
            boolean z = false;
            while (referenceBindingSuperclass != null) {
                if (referenceBindingSuperclass instanceof ParameterizedTypeBinding) {
                    referenceBindingSuperclass = ((ParameterizedTypeBinding) referenceBindingSuperclass).genericType();
                }
                ReferenceBinding referenceBinding = referenceBindingSuperclass;
                for (AnnotationBinding annotationBinding : Factory.getPackedAnnotationBindings(referenceBinding.getAnnotations())) {
                    if (annotationBinding != null) {
                        ReferenceBinding annotationType = annotationBinding.getAnnotationType();
                        if ((!z || (annotationType.getAnnotationTagBits() & 281474976710656L) != 0) && !hashSet.contains(annotationType)) {
                            hashSet.add(annotationType);
                            arrayList.add(annotationBinding);
                        }
                    }
                }
                referenceBindingSuperclass = referenceBinding.superclass();
                z = true;
            }
            ArrayList arrayList2 = new ArrayList(arrayList.size());
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(this._env.getFactory().newAnnotationMirror((AnnotationBinding) it.next()));
            }
            return Collections.unmodifiableList(arrayList2);
        }
        return element.getAnnotationMirrors();
    }

    public List<? extends Element> getAllMembers(TypeElement typeElement) {
        if (typeElement == null || !(typeElement instanceof TypeElementImpl)) {
            return Collections.emptyList();
        }
        ReferenceBinding referenceBindingSuperclass = (ReferenceBinding) ((TypeElementImpl) typeElement)._binding;
        Map<String, ReferenceBinding> map = new HashMap<>();
        List<FieldBinding> arrayList = new ArrayList<>();
        Map<String, Set<MethodBinding>> map2 = new HashMap<>();
        Set<ReferenceBinding> linkedHashSet = new LinkedHashSet<>();
        boolean z = true;
        while (referenceBindingSuperclass != null) {
            addMembers(referenceBindingSuperclass, z, map, arrayList, map2);
            Set<ReferenceBinding> linkedHashSet2 = new LinkedHashSet<>();
            collectSuperInterfaces(referenceBindingSuperclass, linkedHashSet, linkedHashSet2);
            Iterator<ReferenceBinding> it = linkedHashSet2.iterator();
            while (it.hasNext()) {
                addMembers(it.next(), false, map, arrayList, map2);
            }
            linkedHashSet.addAll(linkedHashSet2);
            referenceBindingSuperclass = referenceBindingSuperclass.superclass();
            z = false;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<ReferenceBinding> it2 = map.values().iterator();
        while (it2.hasNext()) {
            arrayList2.add(this._env.getFactory().newElement(it2.next()));
        }
        Iterator<FieldBinding> it3 = arrayList.iterator();
        while (it3.hasNext()) {
            arrayList2.add(this._env.getFactory().newElement(it3.next()));
        }
        Iterator<Set<MethodBinding>> it4 = map2.values().iterator();
        while (it4.hasNext()) {
            Iterator<MethodBinding> it5 = it4.next().iterator();
            while (it5.hasNext()) {
                arrayList2.add(this._env.getFactory().newElement(it5.next()));
            }
        }
        return arrayList2;
    }

    private void collectSuperInterfaces(ReferenceBinding referenceBinding, Set<ReferenceBinding> set, Set<ReferenceBinding> set2) {
        for (ReferenceBinding referenceBinding2 : referenceBinding.superInterfaces()) {
            if (!set.contains(referenceBinding2) && !set2.contains(referenceBinding2)) {
                set2.add(referenceBinding2);
                collectSuperInterfaces(referenceBinding2, set, set2);
            }
        }
    }

    private void addMembers(ReferenceBinding referenceBinding, boolean z, Map<String, ReferenceBinding> map, List<FieldBinding> list, Map<String, Set<MethodBinding>> map2) {
        for (ReferenceBinding referenceBinding2 : referenceBinding.memberTypes()) {
            if (z || !referenceBinding2.isPrivate()) {
                String str = new String(referenceBinding2.sourceName());
                if (map.get(str) == null) {
                    map.put(str, referenceBinding2);
                }
            }
        }
        for (FieldBinding fieldBinding : referenceBinding.fields()) {
            if (z || !fieldBinding.isPrivate()) {
                list.add(fieldBinding);
            }
        }
        for (MethodBinding methodBinding : referenceBinding.methods()) {
            if (!methodBinding.isSynthetic() && (z || (!methodBinding.isPrivate() && !methodBinding.isConstructor()))) {
                String str2 = new String(methodBinding.selector);
                Set<MethodBinding> set = map2.get(str2);
                if (set == null) {
                    HashSet hashSet = new HashSet(4);
                    map2.put(str2, hashSet);
                    hashSet.add(methodBinding);
                } else if (!z) {
                    Iterator<MethodBinding> it = set.iterator();
                    do {
                        if (!it.hasNext()) {
                            set.add(methodBinding);
                            break;
                        }
                    } while (!this._env.getLookupEnvironment().methodVerifier().doesMethodOverride(it.next(), methodBinding));
                } else {
                    set.add(methodBinding);
                    break;
                    break;
                }
            }
        }
    }

    public Name getBinaryName(TypeElement typeElement) {
        return new NameImpl(CharOperation.replaceOnCopy(((ReferenceBinding) ((TypeElementImpl) typeElement)._binding).constantPoolName(), '/', '.'));
    }

    public String getConstantExpression(Object obj) {
        if (!(obj instanceof Integer) && !(obj instanceof Byte) && !(obj instanceof Float) && !(obj instanceof Double) && !(obj instanceof Long) && !(obj instanceof Short) && !(obj instanceof Character) && !(obj instanceof String) && !(obj instanceof Boolean)) {
            throw new IllegalArgumentException("Not a valid wrapper type : " + obj.getClass());
        }
        if (obj instanceof Character) {
            StringBuilder sb = new StringBuilder();
            sb.append('\'').append(obj).append('\'');
            return String.valueOf(sb);
        }
        if (obj instanceof String) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append('\"').append(obj).append('\"');
            return String.valueOf(sb2);
        }
        if (obj instanceof Float) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(obj).append('f');
            return String.valueOf(sb3);
        }
        if (obj instanceof Long) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(obj).append('L');
            return String.valueOf(sb4);
        }
        if (obj instanceof Short) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("(short)").append(obj);
            return String.valueOf(sb5);
        }
        if (obj instanceof Byte) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append("(byte)0x");
            String hexString = Integer.toHexString(((Byte) obj).byteValue() & 255);
            if (hexString.length() < 2) {
                sb6.append(ExternalAnnotationProvider.NULLABLE);
            }
            sb6.append(hexString);
            return String.valueOf(sb6);
        }
        return String.valueOf(obj);
    }

    public String getDocComment(Element element) {
        return formatJavadoc(getUnparsedDocComment(element));
    }

    /* JADX WARN: Code duplicated, block: B:27:0x008f  */
    private char[] getUnparsedDocComment(Element element) {
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod;
        Javadoc javadoc;
        ReferenceContext referenceContext;
        char[] contents;
        TypeDeclaration typeDeclaration;
        TypeDeclaration typeDeclaration2;
        TypeDeclaration typeDeclaration3;
        int i = $SWITCH_TABLE$javax$lang$model$element$ElementKind()[element.getKind().ordinal()];
        if (i != 11 && i != 12) {
            switch (i) {
                case 1:
                    ReferenceBinding type = this._env.getLookupEnvironment().getType(CharOperation.arrayConcat(((PackageBinding) ((PackageElementImpl) element)._binding).compoundName, TypeConstants.PACKAGE_INFO_NAME));
                    if (type != null && type.isValidBinding() && (type instanceof SourceTypeBinding)) {
                        typeDeclaration = ((SourceTypeBinding) type).scope.referenceContext;
                        javadoc = typeDeclaration.javadoc;
                    } else {
                        referenceContext = null;
                        javadoc = null;
                    }
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                    ReferenceBinding referenceBinding = (ReferenceBinding) ((TypeElementImpl) element)._binding;
                    if (!(referenceBinding instanceof SourceTypeBinding)) {
                        referenceContext = null;
                        javadoc = null;
                    } else {
                        typeDeclaration2 = ((SourceTypeBinding) referenceBinding).scope.referenceContext;
                        javadoc = typeDeclaration2.javadoc;
                    }
                    break;
                case 6:
                case 7:
                    FieldBinding fieldBinding = (FieldBinding) ((VariableElementImpl) element)._binding;
                    FieldDeclaration fieldDeclarationSourceField = fieldBinding.sourceField();
                    if (fieldDeclarationSourceField == null) {
                        referenceContext = null;
                        javadoc = null;
                    } else {
                        javadoc = fieldDeclarationSourceField.javadoc;
                        if (!(fieldBinding.declaringClass instanceof SourceTypeBinding)) {
                            referenceContext = null;
                        } else {
                            typeDeclaration3 = ((SourceTypeBinding) fieldBinding.declaringClass).scope.referenceContext;
                        }
                    }
                    break;
                default:
                    return null;
            }
        } else {
            abstractMethodDeclarationSourceMethod = ((MethodBinding) ((ExecutableElementImpl) element)._binding).sourceMethod();
            if (abstractMethodDeclarationSourceMethod != null) {
                javadoc = abstractMethodDeclarationSourceMethod.javadoc;
            } else {
                referenceContext = null;
                javadoc = null;
            }
        }
        if (javadoc != null && referenceContext != null && (contents = referenceContext.compilationResult().getCompilationUnit().getContents()) != null) {
            return CharOperation.subarray(contents, javadoc.sourceStart, javadoc.sourceEnd - 1);
        }
        referenceContext = abstractMethodDeclarationSourceMethod;
        referenceContext = typeDeclaration;
        referenceContext = typeDeclaration2;
        referenceContext = typeDeclaration3;
        return null;
    }

    private static String formatJavadoc(char[] cArr) {
        if (cArr == null || cArr.length < 5) {
            return null;
        }
        String[] strArrSplit = new String(cArr).split("\n");
        int i = 0;
        Matcher matcher = INITIAL_DELIMITER.matcher(strArrSplit[0]);
        if (!matcher.find()) {
            return null;
        }
        String strSubstring = strArrSplit[0].substring(matcher.end());
        strArrSplit[0] = strSubstring;
        int i2 = 1;
        if (strArrSplit.length == 1) {
            StringBuilder sb = new StringBuilder();
            char[] charArray = strArrSplit[0].toCharArray();
            for (char c : charArray) {
                if (!Character.isWhitespace(c)) {
                    sb.append(c);
                    i2 = 0;
                } else if (i2 == 0) {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        int i3 = strSubstring.trim().length() > 0 ? 0 : 1;
        int length = strArrSplit[strArrSplit.length - 1].trim().length() > 0 ? strArrSplit.length - 1 : strArrSplit.length - 2;
        StringBuilder sb2 = new StringBuilder();
        if (strArrSplit[0].length() != 0 && i3 == 1) {
            sb2.append('\n');
        }
        boolean z = strArrSplit[0].length() == 0;
        for (int i4 = i3; i4 <= length; i4++) {
            char[] charArray2 = strArrSplit[i4].toCharArray();
            int stars = getStars(charArray2);
            int length2 = charArray2.length;
            int i5 = i;
            int i6 = i5;
            int i7 = i2;
            while (i5 < length2) {
                char c2 = charArray2[i5];
                if (c2 != ' ') {
                    if (i6 != 0) {
                        int i8 = i6 / 8;
                        if (i8 != 0) {
                            while (i < i8) {
                                sb2.append("        ");
                                i++;
                                i2 = 1;
                            }
                            if (i6 % 8 >= i2) {
                                sb2.append(StringUtil.SPACE);
                            }
                        } else if (i4 != 0) {
                            for (int i9 = 0; i9 < i6; i9++) {
                                sb2.append(StringUtil.SPACE);
                            }
                        }
                        sb2.append(c2);
                        i6 = 0;
                    } else if (c2 == '\t') {
                        if (i5 >= stars) {
                            sb2.append(c2);
                        }
                    } else if (c2 != '*' || i5 > stars) {
                        sb2.append(c2);
                    }
                    i7 = 0;
                } else if (stars == -1) {
                    if (i7 != 0) {
                        i6++;
                    } else {
                        sb2.append(c2);
                    }
                } else if (i5 >= stars) {
                    sb2.append(c2);
                }
                i5++;
                i = 0;
                i2 = 1;
            }
            int length3 = strArrSplit.length - i2;
            if (i4 < length3) {
                sb2.append('\n');
            } else if (z && i4 == length3) {
                sb2.append('\n');
            }
        }
        return sb2.toString();
    }

    private static int getStars(char[] cArr) {
        int length = cArr.length;
        int i = 0;
        while (i < length) {
            char c = cArr[i];
            if (!Character.isWhitespace(c)) {
                if (c != '*') {
                    return -1;
                }
                do {
                    i++;
                    if (i >= length) {
                        return length - 1;
                    }
                } while (cArr[i] == '*');
                return i;
            }
            i++;
        }
        return -1;
    }

    public Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValuesWithDefaults(AnnotationMirror annotationMirror) {
        return ((AnnotationMirrorImpl) annotationMirror).getElementValuesWithDefaults();
    }

    public Name getName(CharSequence charSequence) {
        return new NameImpl(charSequence);
    }

    public PackageElement getPackageElement(CharSequence charSequence) {
        LookupEnvironment lookupEnvironment = this._env.getLookupEnvironment();
        if (charSequence.length() == 0) {
            return new PackageElementImpl(this._env, lookupEnvironment.defaultPackage);
        }
        PackageBinding packageBindingCreatePackage = lookupEnvironment.createPackage(CharOperation.splitOn('.', charSequence.toString().toCharArray()));
        if (packageBindingCreatePackage == null) {
            return null;
        }
        return new PackageElementImpl(this._env, packageBindingCreatePackage);
    }

    public PackageElement getPackageOf(Element element) {
        switch ($SWITCH_TABLE$javax$lang$model$element$ElementKind()[element.getKind().ordinal()]) {
            case 1:
                return (PackageElement) element;
            case 2:
            case 3:
            case 4:
            case 5:
                return this._env.getFactory().newElement(((ReferenceBinding) ((TypeElementImpl) element)._binding).fPackage);
            case 6:
            case 7:
                return this._env.getFactory().newElement(((FieldBinding) ((VariableElementImpl) element)._binding).declaringClass.fPackage);
            case 8:
                return this._env.getFactory().newElement(((LocalVariableBinding) ((VariableElementImpl) element)._binding).declaringScope.classScope().referenceContext.binding.fPackage);
            case 9:
            case 10:
            default:
                return null;
            case 11:
            case 12:
                return this._env.getFactory().newElement(((MethodBinding) ((ExecutableElementImpl) element)._binding).declaringClass.fPackage);
        }
    }

    public TypeElement getTypeElement(CharSequence charSequence) {
        int i;
        LookupEnvironment lookupEnvironment = this._env.getLookupEnvironment();
        char[][] cArrSplitOn = CharOperation.splitOn('.', charSequence.toString().toCharArray());
        ReferenceBinding type = lookupEnvironment.getType(cArrSplitOn);
        if (type == null) {
            int length = cArrSplitOn.length;
            ReferenceBinding type2 = null;
            while (true) {
                i = length - 1;
                if (i <= 0) {
                    break;
                }
                char[][] cArr = new char[i][];
                for (int i2 = 0; i2 < i; i2++) {
                    cArr[i2] = cArrSplitOn[i2];
                }
                type2 = lookupEnvironment.getType(cArr);
                if (type2 != null) {
                    break;
                }
                length = i;
            }
            if (type2 == null) {
                return null;
            }
            type = type2;
            while (type != null && i < cArrSplitOn.length) {
                type = type.getMemberType(cArrSplitOn[i]);
                i++;
            }
        }
        if (type == null) {
            return null;
        }
        return new TypeElementImpl(this._env, type, null);
    }

    public boolean hides(Element element, Element element2) {
        element2.getClass();
        return ((ElementImpl) element).hides(element2);
    }

    public boolean isDeprecated(Element element) {
        return (element instanceof ElementImpl) && (((ElementImpl) element)._binding.getAnnotationTagBits() & 70368744177664L) != 0;
    }

    public boolean overrides(ExecutableElement executableElement, ExecutableElement executableElement2, TypeElement typeElement) {
        if (executableElement2 == null || typeElement == null) {
            throw null;
        }
        return ((ExecutableElementImpl) executableElement).overrides(executableElement2, typeElement);
    }

    public void printElements(Writer writer, Element... elementArr) {
        String property = System.getProperty(SystemProperties.LINE_SEPARATOR);
        for (Element element : elementArr) {
            try {
                writer.write(element.toString());
                writer.write(property);
            } catch (IOException unused) {
            }
        }
        try {
            writer.flush();
        } catch (IOException unused2) {
        }
    }

    public boolean isFunctionalInterface(TypeElement typeElement) {
        if (typeElement == null || typeElement.getKind() != ElementKind.INTERFACE) {
            return false;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) ((TypeElementImpl) typeElement)._binding;
        if (referenceBinding instanceof SourceTypeBinding) {
            return referenceBinding.isFunctionalInterface(((SourceTypeBinding) referenceBinding).scope);
        }
        return false;
    }
}
