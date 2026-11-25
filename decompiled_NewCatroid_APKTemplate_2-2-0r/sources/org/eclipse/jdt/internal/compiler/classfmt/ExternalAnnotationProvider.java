package org.eclipse.jdt.internal.compiler.classfmt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryElementValuePair;
import org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.SignatureWrapper;

/* JADX INFO: loaded from: classes14.dex */
public class ExternalAnnotationProvider {
    public static final String ANNOTATION_FILE_EXTENSION = "eea";
    public static final String ANNOTATION_FILE_SUFFIX = ".eea";
    public static final String CLASS_PREFIX = "class ";
    public static final char NONNULL = '1';
    public static final char NO_ANNOTATION = '@';
    public static final char NULLABLE = '0';
    public static final String SUPER_PREFIX = "super ";
    private static final String TYPE_PARAMETER_PREFIX = " <";
    SingleMarkerAnnotation NONNULL_ANNOTATION;
    SingleMarkerAnnotation NULLABLE_ANNOTATION;
    private Map<String, String> fieldAnnotationSources;
    private Map<String, String> methodAnnotationSources;
    Map<String, String> supertypeAnnotationSources;
    private String typeName;
    String typeParametersAnnotationSource;

    public interface IMethodAnnotationWalker extends ITypeAnnotationWalker {
        int getParameterCount();
    }

    public ExternalAnnotationProvider(InputStream inputStream, String str) throws IOException {
        this.typeName = str;
        initialize(inputStream);
    }

    private void initialize(InputStream inputStream) throws IOException {
        String strSubstring;
        int lineNumber;
        String strSubstring2;
        Throwable th = null;
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(inputStream));
            try {
                assertClassHeader(lineNumberReader.readLine(), this.typeName);
                String line = lineNumberReader.readLine();
                if (line == null) {
                    return;
                }
                if (line.startsWith(TYPE_PARAMETER_PREFIX)) {
                    line = lineNumberReader.readLine();
                    if (line == null) {
                        return;
                    }
                    if (line.startsWith(TYPE_PARAMETER_PREFIX)) {
                        this.typeParametersAnnotationSource = line.substring(TYPE_PARAMETER_PREFIX.length());
                        line = lineNumberReader.readLine();
                        if (line == null) {
                            return;
                        }
                    }
                }
                while (true) {
                    String strTrim = line.trim();
                    if (!strTrim.isEmpty()) {
                        boolean zStartsWith = strTrim.startsWith(SUPER_PREFIX);
                        if (zStartsWith) {
                            strTrim = strTrim.substring(SUPER_PREFIX.length());
                        }
                        try {
                            String line2 = lineNumberReader.readLine();
                            if (line2 == null || line2.isEmpty() || line2.charAt(0) != ' ') {
                                lineNumber = lineNumberReader.getLineNumber();
                                strSubstring = null;
                            } else {
                                strSubstring = line2.substring(1);
                                lineNumber = -1;
                            }
                            try {
                                String line3 = lineNumberReader.readLine();
                                if (line3 != null && !line3.isEmpty()) {
                                    if (line3.charAt(0) != ' ') {
                                        line = line3;
                                    } else {
                                        strSubstring2 = line3.substring(1);
                                        if (strSubstring == null || strSubstring2 == null) {
                                            break;
                                        }
                                        String strTrimTail = trimTail(strSubstring2);
                                        if (zStartsWith) {
                                            if (this.supertypeAnnotationSources == null) {
                                                this.supertypeAnnotationSources = new HashMap();
                                            }
                                            this.supertypeAnnotationSources.put(String.valueOf('L') + strTrim + strSubstring + ';', strTrimTail);
                                        } else if (strSubstring.contains("(")) {
                                            if (this.methodAnnotationSources == null) {
                                                this.methodAnnotationSources = new HashMap();
                                            }
                                            this.methodAnnotationSources.put(String.valueOf(strTrim) + strSubstring, strTrimTail);
                                        } else {
                                            if (this.fieldAnnotationSources == null) {
                                                this.fieldAnnotationSources = new HashMap();
                                            }
                                            this.fieldAnnotationSources.put(String.valueOf(strTrim) + ':' + strSubstring, strTrimTail);
                                        }
                                    }
                                }
                            } catch (Exception unused) {
                                strSubstring2 = null;
                            }
                        } catch (Exception unused2) {
                            strSubstring = null;
                            lineNumber = -1;
                        }
                        if (line != null && (line = lineNumberReader.readLine()) == null) {
                            return;
                        }
                    }
                    line = null;
                    if (line != null) {
                    }
                }
                if (lineNumber == -1) {
                    lineNumber = lineNumberReader.getLineNumber();
                }
                throw new IOException("Illegal format for annotation file at line " + lineNumber);
            } finally {
                lineNumberReader.close();
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                th.addSuppressed(th2);
            }
            throw null;
        }
    }

    public static void assertClassHeader(String str, String str2) throws IOException {
        if (str != null && str.startsWith(CLASS_PREFIX)) {
            String strSubstring = str.substring(CLASS_PREFIX.length());
            if (!trimTail(strSubstring).equals(str2)) {
                throw new IOException("mismatching class name in annotation file, expected " + str2 + ", but header said " + strSubstring);
            }
            return;
        }
        throw new IOException("missing class header in annotation file");
    }

    public static String extractSignature(String str) {
        if (str == null || str.isEmpty() || str.charAt(0) != ' ') {
            return null;
        }
        return trimTail(str.substring(1));
    }

    protected static String trimTail(String str) {
        int iIndexOf = str.indexOf(32);
        if (iIndexOf == -1) {
            iIndexOf = str.indexOf(9);
        }
        return iIndexOf != -1 ? str.substring(0, iIndexOf) : str;
    }

    public ITypeAnnotationWalker forTypeHeader(LookupEnvironment lookupEnvironment) {
        if (this.typeParametersAnnotationSource != null || this.supertypeAnnotationSources != null) {
            return new DispatchingAnnotationWalker(lookupEnvironment);
        }
        return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
    }

    public ITypeAnnotationWalker forMethod(char[] cArr, char[] cArr2, LookupEnvironment lookupEnvironment) {
        String str;
        Map<String, String> map = this.methodAnnotationSources;
        if (map != null && (str = map.get(String.valueOf(CharOperation.concat(cArr, cArr2)))) != null) {
            return new MethodAnnotationWalker(str.toCharArray(), 0, lookupEnvironment);
        }
        return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
    }

    public ITypeAnnotationWalker forField(char[] cArr, char[] cArr2, LookupEnvironment lookupEnvironment) {
        String str;
        Map<String, String> map = this.fieldAnnotationSources;
        if (map != null && (str = map.get(String.valueOf(CharOperation.concat(cArr, cArr2, ':')))) != null) {
            return new FieldAnnotationWalker(str.toCharArray(), 0, lookupEnvironment);
        }
        return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("External Annotations for ");
        sb.append(this.typeName).append("\nMethods:\n");
        Map<String, String> map = this.methodAnnotationSources;
        if (map != null) {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                sb.append('\t').append(it.next().getKey()).append('\n');
            }
        }
        return sb.toString();
    }

    abstract class SingleMarkerAnnotation implements IBinaryAnnotation {
        SingleMarkerAnnotation() {
        }

        @Override // org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation
        public IBinaryElementValuePair[] getElementValuePairs() {
            return ElementValuePairInfo.NoMembers;
        }

        protected char[] getBinaryTypeName(char[][] cArr) {
            return CharOperation.concat('L', CharOperation.concatWith(cArr, '/'), ';');
        }
    }

    void initAnnotations(final LookupEnvironment lookupEnvironment) {
        if (this.NULLABLE_ANNOTATION == null) {
            this.NULLABLE_ANNOTATION = new SingleMarkerAnnotation(this) { // from class: org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation
                public char[] getTypeName() {
                    return getBinaryTypeName(lookupEnvironment.getNullableAnnotationName());
                }
            };
        }
        if (this.NONNULL_ANNOTATION == null) {
            this.NONNULL_ANNOTATION = new SingleMarkerAnnotation(this) { // from class: org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation
                public char[] getTypeName() {
                    return getBinaryTypeName(lookupEnvironment.getNonNullAnnotationName());
                }
            };
        }
    }

    class DispatchingAnnotationWalker implements ITypeAnnotationWalker {
        private LookupEnvironment environment;
        private TypeParametersAnnotationWalker typeParametersWalker;

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toField() {
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toMethodParameter(short s) {
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toMethodReturn() {
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toNextArrayDimension() {
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toNextNestedType() {
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toReceiver() {
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toThrows(int i) {
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeArgument(int i) {
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeBound(short s) {
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toWildcardBound() {
            return this;
        }

        public DispatchingAnnotationWalker(LookupEnvironment lookupEnvironment) {
            this.environment = lookupEnvironment;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeParameter(boolean z, int i) {
            String str = ExternalAnnotationProvider.this.typeParametersAnnotationSource;
            if (str == null) {
                return this;
            }
            if (this.typeParametersWalker == null) {
                this.typeParametersWalker = ExternalAnnotationProvider.this.new TypeParametersAnnotationWalker(str.toCharArray(), 0, 0, null, this.environment);
            }
            return this.typeParametersWalker.toTypeParameter(z, i);
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeParameterBounds(boolean z, int i) {
            TypeParametersAnnotationWalker typeParametersAnnotationWalker = this.typeParametersWalker;
            return typeParametersAnnotationWalker != null ? typeParametersAnnotationWalker.toTypeParameterBounds(z, i) : this;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toSupertype(short s, char[] cArr) {
            String str;
            Map<String, String> map = ExternalAnnotationProvider.this.supertypeAnnotationSources;
            return (map == null || (str = map.get(String.valueOf(cArr))) == null) ? this : ExternalAnnotationProvider.this.new SuperTypesAnnotationWalker(str.toCharArray(), this.environment);
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public IBinaryAnnotation[] getAnnotationsAtCursor(int i) {
            return NO_ANNOTATIONS;
        }
    }

    abstract class BasicAnnotationWalker implements ITypeAnnotationWalker {
        int currentTypeBound;
        LookupEnvironment environment;
        int pos;
        int prevTypeArgStart;
        char[] source;
        SignatureWrapper wrapper;

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toNextNestedType() {
            return this;
        }

        BasicAnnotationWalker(char[] cArr, int i, LookupEnvironment lookupEnvironment) {
            this.source = cArr;
            this.pos = i;
            this.environment = lookupEnvironment;
            ExternalAnnotationProvider.this.initAnnotations(lookupEnvironment);
        }

        SignatureWrapper wrapperWithStart(int i) {
            if (this.wrapper == null) {
                this.wrapper = new SignatureWrapper(this.source);
            }
            this.wrapper.start = i;
            return this.wrapper;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toReceiver() {
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeParameter(boolean z, int i) {
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeParameterBounds(boolean z, int i) {
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeBound(short s) {
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toSupertype(short s, char[] cArr) {
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeArgument(int i) {
            if (i == 0) {
                int iIndexOf = CharOperation.indexOf('<', this.source, this.pos) + 1;
                this.prevTypeArgStart = iIndexOf;
                return ExternalAnnotationProvider.this.new MethodAnnotationWalker(this.source, iIndexOf, this.environment);
            }
            int iComputeEnd = this.prevTypeArgStart;
            char c = this.source[iComputeEnd];
            if (c != '*') {
                if (c == '+' || c == '-') {
                    iComputeEnd++;
                }
                iComputeEnd = wrapperWithStart(iComputeEnd).computeEnd();
            }
            int i2 = iComputeEnd + 1;
            this.prevTypeArgStart = i2;
            return ExternalAnnotationProvider.this.new MethodAnnotationWalker(this.source, i2, this.environment);
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toWildcardBound() {
            char c = this.source[this.pos];
            if (c == '+' || c == '-') {
                return ExternalAnnotationProvider.this.new MethodAnnotationWalker(this.source, this.pos + 1, this.environment);
            }
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toNextArrayDimension() {
            char[] cArr = this.source;
            int i = this.pos;
            if (cArr[i] == '[') {
                int i2 = i + 1;
                char c = cArr[i2];
                if (c == '0' || c == '1') {
                    i2 = i + 2;
                }
                return ExternalAnnotationProvider.this.new MethodAnnotationWalker(this.source, i2, this.environment);
            }
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public IBinaryAnnotation[] getAnnotationsAtCursor(int i) {
            char c;
            int i2 = this.pos;
            if (i2 != -1) {
                char[] cArr = this.source;
                if (i2 < cArr.length - 2 && ((c = cArr[i2]) == '*' || c == '+' || c == '-' || c == 'L' || c == 'T' || c == '[')) {
                    char c2 = cArr[i2 + 1];
                    if (c2 == '0') {
                        return new IBinaryAnnotation[]{ExternalAnnotationProvider.this.NULLABLE_ANNOTATION};
                    }
                    if (c2 == '1') {
                        return new IBinaryAnnotation[]{ExternalAnnotationProvider.this.NONNULL_ANNOTATION};
                    }
                }
            }
            return NO_ANNOTATIONS;
        }
    }

    public class TypeParametersAnnotationWalker extends BasicAnnotationWalker {
        int currentRank;
        int[] rankStarts;

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public /* bridge */ /* synthetic */ ITypeAnnotationWalker toNextArrayDimension() {
            return super.toNextArrayDimension();
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public /* bridge */ /* synthetic */ ITypeAnnotationWalker toNextNestedType() {
            return super.toNextNestedType();
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public /* bridge */ /* synthetic */ ITypeAnnotationWalker toReceiver() {
            return super.toReceiver();
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public /* bridge */ /* synthetic */ ITypeAnnotationWalker toSupertype(short s, char[] cArr) {
            return super.toSupertype(s, cArr);
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public /* bridge */ /* synthetic */ ITypeAnnotationWalker toTypeArgument(int i) {
            return super.toTypeArgument(i);
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public /* bridge */ /* synthetic */ ITypeAnnotationWalker toWildcardBound() {
            return super.toWildcardBound();
        }

        /* JADX WARN: Code duplicated, block: B:40:0x0075  */
        /* JADX WARN: Code duplicated, block: B:43:0x007f  */
        /* JADX WARN: Code duplicated, block: B:65:0x0081 A[SYNTHETIC] */
        TypeParametersAnnotationWalker(char[] cArr, int i, int i2, int[] iArr, LookupEnvironment lookupEnvironment) {
            int i3;
            int i4;
            super(cArr, i, lookupEnvironment);
            this.currentRank = i2;
            if (iArr != null) {
                this.rankStarts = iArr;
                return;
            }
            int length = cArr.length;
            int[] iArr2 = new int[length];
            int i5 = 0;
            int i6 = 0;
            boolean z = true;
            while (i < length) {
                switch (this.source[i]) {
                    case ':':
                        z = i5 == 0 ? true : z;
                        do {
                            i++;
                            if (i < length) {
                            }
                            if (i < length && this.source[i] == 'L') {
                                i3 = i5;
                                while (i < length && (i3 != i5 || this.source[i] != ';')) {
                                    i3 = this.source[i] == '<' ? i3 + 1 : i3;
                                    if (this.source[i] == '>') {
                                        i3--;
                                    }
                                    i++;
                                }
                            }
                            i--;
                            break;
                        } while (this.source[i] == '[');
                        if (i < length) {
                            i3 = i5;
                            while (i < length) {
                                if (this.source[i] == '<') {
                                }
                                if (this.source[i] == '>') {
                                    i3--;
                                }
                                i++;
                            }
                        }
                        i--;
                        break;
                    case ';':
                        if (i5 == 0 && (i4 = i + 1) < length && this.source[i4] != ':') {
                            z = true;
                        }
                        break;
                    case '<':
                        i5++;
                        break;
                    case '=':
                    default:
                        if (z) {
                            iArr2[i6] = i;
                            i6++;
                            z = false;
                        }
                        break;
                    case '>':
                        i5--;
                        if (i5 < 0) {
                            int[] iArr3 = new int[i6];
                            this.rankStarts = iArr3;
                            System.arraycopy(iArr2, 0, iArr3, 0, i6);
                        }
                        break;
                }
                i++;
            }
            int[] iArr4 = new int[i6];
            this.rankStarts = iArr4;
            System.arraycopy(iArr2, 0, iArr4, 0, i6);
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeParameter(boolean z, int i) {
            if (i == this.currentRank) {
                return this;
            }
            if (i < this.rankStarts.length) {
                ExternalAnnotationProvider externalAnnotationProvider = ExternalAnnotationProvider.this;
                char[] cArr = this.source;
                int[] iArr = this.rankStarts;
                return externalAnnotationProvider.new TypeParametersAnnotationWalker(cArr, iArr[i], i, iArr, this.environment);
            }
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeParameterBounds(boolean z, int i) {
            ExternalAnnotationProvider externalAnnotationProvider = ExternalAnnotationProvider.this;
            char[] cArr = this.source;
            int[] iArr = this.rankStarts;
            return externalAnnotationProvider.new TypeParametersAnnotationWalker(cArr, iArr[i], i, iArr, this.environment);
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeBound(short s) {
            int iComputeEnd = this.pos;
            int i = this.currentTypeBound;
            while (true) {
                int iIndexOf = CharOperation.indexOf(':', this.source, iComputeEnd);
                if (iIndexOf != -1) {
                    iComputeEnd = iIndexOf + 1;
                }
                i++;
                if (i <= s) {
                    iComputeEnd = wrapperWithStart(iComputeEnd).computeEnd() + 1;
                } else {
                    this.pos = iComputeEnd;
                    this.currentTypeBound = s;
                    return this;
                }
            }
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toField() {
            throw new UnsupportedOperationException("Cannot navigate to fields");
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toMethodReturn() {
            throw new UnsupportedOperationException("Cannot navigate to method return");
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toMethodParameter(short s) {
            throw new UnsupportedOperationException("Cannot navigate to method parameter");
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toThrows(int i) {
            throw new UnsupportedOperationException("Cannot navigate to throws");
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public IBinaryAnnotation[] getAnnotationsAtCursor(int i) {
            if (this.pos != -1 && this.pos < this.source.length - 1) {
                char c = this.source[this.pos];
                if (c == '0') {
                    return new IBinaryAnnotation[]{ExternalAnnotationProvider.this.NULLABLE_ANNOTATION};
                }
                if (c == '1') {
                    return new IBinaryAnnotation[]{ExternalAnnotationProvider.this.NONNULL_ANNOTATION};
                }
            }
            return super.getAnnotationsAtCursor(i);
        }
    }

    class SuperTypesAnnotationWalker extends BasicAnnotationWalker {
        SuperTypesAnnotationWalker(char[] cArr, LookupEnvironment lookupEnvironment) {
            super(cArr, 0, lookupEnvironment);
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toField() {
            throw new UnsupportedOperationException("Supertype has no field annotations");
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toMethodReturn() {
            throw new UnsupportedOperationException("Supertype has no method return");
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toMethodParameter(short s) {
            throw new UnsupportedOperationException("Supertype has no method parameter");
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toThrows(int i) {
            throw new UnsupportedOperationException("Supertype has no throws");
        }
    }

    class MethodAnnotationWalker extends BasicAnnotationWalker implements IMethodAnnotationWalker {
        int prevParamStart;
        TypeParametersAnnotationWalker typeParametersWalker;

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toThrows(int i) {
            return this;
        }

        MethodAnnotationWalker(char[] cArr, int i, LookupEnvironment lookupEnvironment) {
            super(cArr, i, lookupEnvironment);
        }

        int typeEnd(int i) {
            while (this.source[i] == '[') {
                int i2 = i + 1;
                char c = this.source[i2];
                i = (c == '0' || c == '1') ? i + 2 : i2;
            }
            return wrapperWithStart(i).computeEnd();
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeParameter(boolean z, int i) {
            if (this.source[0] == '<') {
                TypeParametersAnnotationWalker typeParametersAnnotationWalker = this.typeParametersWalker;
                if (typeParametersAnnotationWalker == null) {
                    TypeParametersAnnotationWalker typeParametersAnnotationWalker2 = ExternalAnnotationProvider.this.new TypeParametersAnnotationWalker(this.source, this.pos + 1, i, null, this.environment);
                    this.typeParametersWalker = typeParametersAnnotationWalker2;
                    return typeParametersAnnotationWalker2;
                }
                return typeParametersAnnotationWalker.toTypeParameter(z, i);
            }
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.BasicAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toTypeParameterBounds(boolean z, int i) {
            TypeParametersAnnotationWalker typeParametersAnnotationWalker = this.typeParametersWalker;
            if (typeParametersAnnotationWalker != null) {
                return typeParametersAnnotationWalker.toTypeParameterBounds(z, i);
            }
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toMethodReturn() {
            int iIndexOf = CharOperation.indexOf(')', this.source);
            if (iIndexOf != -1) {
                this.pos = iIndexOf + 1;
                return this;
            }
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toMethodParameter(short s) {
            if (s == 0) {
                int iIndexOf = CharOperation.indexOf('(', this.source) + 1;
                this.prevParamStart = iIndexOf;
                this.pos = iIndexOf;
                return this;
            }
            int iTypeEnd = typeEnd(this.prevParamStart) + 1;
            this.prevParamStart = iTypeEnd;
            this.pos = iTypeEnd;
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toField() {
            throw new UnsupportedOperationException("Methods have no fields");
        }

        @Override // org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider.IMethodAnnotationWalker
        public int getParameterCount() {
            int iIndexOf = CharOperation.indexOf('(', this.source) + 1;
            int i = 0;
            while (iIndexOf < this.source.length && this.source[iIndexOf] != ')') {
                iIndexOf = typeEnd(iIndexOf) + 1;
                i++;
            }
            return i;
        }
    }

    class FieldAnnotationWalker extends BasicAnnotationWalker {
        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toField() {
            return this;
        }

        public FieldAnnotationWalker(char[] cArr, int i, LookupEnvironment lookupEnvironment) {
            super(cArr, i, lookupEnvironment);
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toMethodReturn() {
            throw new UnsupportedOperationException("Field has no method return");
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toMethodParameter(short s) {
            throw new UnsupportedOperationException("Field has no method parameter");
        }

        @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
        public ITypeAnnotationWalker toThrows(int i) {
            throw new UnsupportedOperationException("Field has no throws");
        }
    }
}
