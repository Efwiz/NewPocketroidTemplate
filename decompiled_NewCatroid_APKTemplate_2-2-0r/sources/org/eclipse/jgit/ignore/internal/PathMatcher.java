package org.eclipse.jgit.ignore.internal;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.ignore.IMatcher;

/* JADX INFO: loaded from: classes13.dex */
public class PathMatcher extends AbstractMatcher {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$ignore$internal$Strings$PatternState;
    private static final WildMatcher WILD_NO_DIRECTORY = new WildMatcher(false);
    private static final WildMatcher WILD_ONLY_DIRECTORY = new WildMatcher(true);
    private final boolean beginning;
    private final List<IMatcher> matchers;
    private final char slash;

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$ignore$internal$Strings$PatternState() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$ignore$internal$Strings$PatternState;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[Strings.PatternState.valuesCustom().length];
        try {
            iArr2[Strings.PatternState.COMPLEX.ordinal()] = 3;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[Strings.PatternState.LEADING_ASTERISK_ONLY.ordinal()] = 1;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[Strings.PatternState.NONE.ordinal()] = 4;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[Strings.PatternState.TRAILING_ASTERISK_ONLY.ordinal()] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$ignore$internal$Strings$PatternState = iArr2;
        return iArr2;
    }

    private PathMatcher(String str, Character ch, boolean z) throws InvalidPatternException {
        super(str, z);
        char pathSeparator = Strings.getPathSeparator(ch);
        this.slash = pathSeparator;
        this.beginning = str.indexOf(pathSeparator) == 0;
        if (isSimplePathWithSegments(str)) {
            this.matchers = null;
        } else {
            this.matchers = createMatchers(Strings.split(str, pathSeparator), ch, z);
        }
    }

    private boolean isSimplePathWithSegments(String str) {
        return !Strings.isWildCard(str) && str.indexOf(92) < 0 && Strings.count(str, this.slash, true) > 0;
    }

    private static List<IMatcher> createMatchers(List<String> list, Character ch, boolean z) throws InvalidPatternException {
        ArrayList arrayList = new ArrayList(list.size());
        int i = 0;
        while (i < list.size()) {
            IMatcher iMatcherCreateNameMatcher0 = createNameMatcher0(list.get(i), ch, z, i == list.size() - 1);
            if (i > 0) {
                IMatcher iMatcher = (IMatcher) arrayList.get(arrayList.size() - 1);
                if (isWild(iMatcherCreateNameMatcher0) && isWild(iMatcher)) {
                    arrayList.remove(arrayList.size() - 1);
                }
            }
            arrayList.add(iMatcherCreateNameMatcher0);
            i++;
        }
        return arrayList;
    }

    public static IMatcher createPathMatcher(String str, Character ch, boolean z) throws InvalidPatternException {
        String strTrim = trim(str);
        int iIndexOf = strTrim.indexOf(Strings.getPathSeparator(ch), 1);
        if (iIndexOf > 0 && iIndexOf < strTrim.length() - 1) {
            return new PathMatcher(strTrim, ch, z);
        }
        return createNameMatcher0(strTrim, ch, z, true);
    }

    private static String trim(String str) {
        while (str.length() > 0 && str.charAt(str.length() - 1) == ' ') {
            if (str.length() > 1 && str.charAt(str.length() - 2) == '\\') {
                return String.valueOf(str.substring(0, str.length() - 2)) + " ";
            }
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private static IMatcher createNameMatcher0(String str, Character ch, boolean z, boolean z2) throws InvalidPatternException {
        if ("**".equals(str) || "/**".equals(str)) {
            if (z && z2) {
                return WILD_ONLY_DIRECTORY;
            }
            return WILD_NO_DIRECTORY;
        }
        int i = $SWITCH_TABLE$org$eclipse$jgit$ignore$internal$Strings$PatternState()[Strings.checkWildCards(str).ordinal()];
        if (i == 1) {
            return new LeadingAsteriskMatcher(str, ch, z);
        }
        if (i == 2) {
            return new TrailingAsteriskMatcher(str, ch, z);
        }
        if (i == 3) {
            return new WildCardMatcher(str, ch, z);
        }
        return new NameMatcher(str, ch, z, true);
    }

    @Override // org.eclipse.jgit.ignore.IMatcher
    public boolean matches(String str, boolean z, boolean z2) {
        if (this.matchers == null) {
            return simpleMatch(str, z, z2);
        }
        return iterate(str, 0, str.length(), z, z2);
    }

    private boolean simpleMatch(String str, boolean z, boolean z2) {
        boolean z3 = str.indexOf(this.slash) == 0;
        if (this.beginning && !z3) {
            str = String.valueOf(this.slash) + str;
        }
        if (!this.beginning && z3) {
            str = str.substring(1);
        }
        if (str.equals(this.pattern)) {
            return !this.dirOnly || z;
        }
        String str2 = String.valueOf(this.pattern) + this.slash;
        if (z2) {
            return str.equals(str2) && (!this.dirOnly || z);
        }
        return str.startsWith(str2);
    }

    @Override // org.eclipse.jgit.ignore.IMatcher
    public boolean matches(String str, int i, int i2) {
        throw new UnsupportedOperationException("Path matcher works only on entire paths");
    }

    private boolean iterate(String str, int i, int i2, boolean z, boolean z2) {
        boolean zMatches;
        int i3 = i;
        boolean zMatches2 = false;
        int i4 = 0;
        int i5 = -1;
        int i6 = -1;
        while (true) {
            int iIndexOf = str.indexOf(this.slash, i3);
            if (iIndexOf == -1) {
                if (i3 < i2) {
                    zMatches = matches(i4, str, i3, i2, z, z2);
                } else {
                    zMatches = zMatches2 && !isWild(this.matchers.get(i4));
                }
                if (zMatches) {
                    if (i4 < this.matchers.size() - 1 && isWild(this.matchers.get(i4))) {
                        i4++;
                        zMatches = matches(i4, str, i3, i2, z, z2);
                    } else if (this.dirOnly && !z) {
                        return false;
                    }
                }
                return zMatches && i4 + 1 == this.matchers.size();
            }
            int i7 = i5 < 0 ? iIndexOf : i5;
            if (iIndexOf - i3 > 0) {
                zMatches2 = matches(i4, str, i3, iIndexOf, z, z2);
                if (zMatches2) {
                    boolean zIsWild = isWild(this.matchers.get(i4));
                    if (zIsWild) {
                        iIndexOf = i3 - 1;
                        i7 = -1;
                        i6 = i4;
                    }
                    i4++;
                    if (i4 != this.matchers.size()) {
                        i5 = i7;
                        i7 = iIndexOf;
                    } else {
                        if (!z2) {
                            return true;
                        }
                        if (iIndexOf == i2 - 1) {
                            return !this.dirOnly || z;
                        }
                        if (zIsWild) {
                            return true;
                        }
                        if (i6 < 0) {
                            return false;
                        }
                    }
                    i3 = i7 + 1;
                } else if (i6 == -1) {
                    return false;
                }
                i4 = i6 + 1;
                i5 = -1;
                i3 = i7 + 1;
            } else {
                i3 = iIndexOf + 1;
                i5 = i7;
            }
        }
    }

    private boolean matches(int i, String str, int i2, int i3, boolean z, boolean z2) {
        IMatcher iMatcher = this.matchers.get(i);
        boolean zMatches = iMatcher.matches(str, i2, i3);
        if (zMatches && z2 && i >= this.matchers.size() - 1 && (iMatcher instanceof AbstractMatcher)) {
            return z || !((AbstractMatcher) iMatcher).dirOnly;
        }
        return zMatches;
    }

    private static boolean isWild(IMatcher iMatcher) {
        return iMatcher == WILD_NO_DIRECTORY || iMatcher == WILD_ONLY_DIRECTORY;
    }
}
