package org.jetbrains.kotlin.com.intellij.openapi.util.text;

import gnu.trove.THashMap;
import gnu.trove.THashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.kotlin.codegen.SamWrapperCodegen;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.util.text.CaseInsensitiveStringHashingStrategy;

/* JADX INFO: loaded from: classes3.dex */
final class Pluralizer {
    static final Pluralizer PLURALIZER;
    private final Map<String, String> irregularSingles = new THashMap(CaseInsensitiveStringHashingStrategy.INSTANCE);
    private final Map<String, String> irregularPlurals = new THashMap(CaseInsensitiveStringHashingStrategy.INSTANCE);
    private final Set<String> uncountables = new THashSet(CaseInsensitiveStringHashingStrategy.INSTANCE);
    private final List<Pair<Pattern, String>> pluralRules = new ArrayList();
    private final List<Pair<Pattern, String>> singularRules = new ArrayList();

    Pluralizer() {
    }

    static String restoreCase(String str, String str2) {
        int iMin;
        char cCharAt;
        char upperCase;
        if (str == null || str2 == null || str == str2 || (iMin = Math.min(str2.length(), str.length())) == 0) {
            return str2;
        }
        char[] charArray = str2.toCharArray();
        int i = 0;
        while (i < iMin) {
            char cCharAt2 = str.charAt(i);
            char c = charArray[i];
            if (c != cCharAt2 || i == iMin - 1) {
                char upperCase2 = Character.toUpperCase(c);
                if (cCharAt2 != Character.toLowerCase(charArray[i]) && cCharAt2 != upperCase2) {
                    break;
                }
                charArray[i] = cCharAt2;
            }
            i++;
        }
        if (i > 0 && i < charArray.length && (upperCase = Character.toUpperCase((cCharAt = str.charAt(i - 1)))) != Character.toLowerCase(cCharAt)) {
            while (i < charArray.length) {
                charArray[i] = cCharAt == upperCase ? Character.toUpperCase(charArray[i]) : Character.toLowerCase(charArray[i]);
                i++;
            }
        }
        return new String(charArray);
    }

    private String sanitizeWord(String str, List<? extends Pair<Pattern, String>> list) {
        Pair<Pattern, String> pair;
        Matcher matcher;
        if (Strings.isEmpty(str) || this.uncountables.contains(str)) {
            return str;
        }
        int size = list.size();
        do {
            size--;
            if (size <= -1) {
                return null;
            }
            pair = list.get(size);
            matcher = pair.first.matcher(str);
        } while (!matcher.find());
        return matcher.replaceFirst(pair.second);
    }

    private String replaceWord(String str, Map<String, String> map, Map<String, String> map2, List<? extends Pair<Pattern, String>> list) {
        if (Strings.isEmpty(str) || map2.containsKey(str)) {
            return str;
        }
        String str2 = map.get(str);
        return str2 != null ? str2 : sanitizeWord(str, list);
    }

    public String plural(String str) {
        return restoreCase(str, replaceWord(str, this.irregularSingles, this.irregularPlurals, this.pluralRules));
    }

    public String singular(String str) {
        return restoreCase(str, replaceWord(str, this.irregularPlurals, this.irregularSingles, this.singularRules));
    }

    private static Pattern sanitizeRule(String str) {
        return Pattern.compile(str.startsWith("/") ? str.substring(1) : "^" + str + "$", 2);
    }

    private void addPluralRule(String str, String str2) {
        this.pluralRules.add(Pair.create(sanitizeRule(str), str2));
    }

    private void addSingularRule(String str, String str2) {
        this.singularRules.add(Pair.create(sanitizeRule(str), str2));
    }

    private void addUncountableRule(String str) {
        if (!str.startsWith("/")) {
            this.uncountables.add(str);
        } else {
            addPluralRule(str, SamWrapperCodegen.SAM_WRAPPER_SUFFIX);
            addSingularRule(str, SamWrapperCodegen.SAM_WRAPPER_SUFFIX);
        }
    }

    private void addIrregularRule(String str, String str2) {
        this.irregularSingles.put(str, str2);
        this.irregularPlurals.put(str2, str);
    }

    static {
        Pluralizer pluralizer = new Pluralizer();
        String[][] strArr = {new String[]{"this", "these"}, new String[]{"that", "those"}, new String[]{"echo", "echoes"}, new String[]{"dingo", "dingoes"}, new String[]{"volcano", "volcanoes"}, new String[]{"tornado", "tornadoes"}, new String[]{"torpedo", "torpedoes"}, new String[]{"genus", "genera"}, new String[]{"viscus", "viscera"}, new String[]{"stigma", "stigmata"}, new String[]{"stoma", "stomata"}, new String[]{"dogma", "dogmata"}, new String[]{"lemma", "lemmata"}, new String[]{"anathema", "anathemata"}, new String[]{"ox", "oxen"}, new String[]{"axe", "axes"}, new String[]{"die", "dice"}, new String[]{"yes", "yeses"}, new String[]{"foot", "feet"}, new String[]{"eave", "eaves"}, new String[]{"goose", "geese"}, new String[]{"tooth", "teeth"}, new String[]{"quiz", "quizzes"}, new String[]{"human", "humans"}, new String[]{"proof", "proofs"}, new String[]{"carve", "carves"}, new String[]{"valve", "valves"}, new String[]{"looey", "looies"}, new String[]{"thief", "thieves"}, new String[]{"groove", "grooves"}, new String[]{"pickaxe", "pickaxes"}, new String[]{"whiskey", "whiskies"}};
        int i = 0;
        for (int i2 = 32; i < i2; i2 = 32) {
            String[] strArr2 = strArr[i];
            pluralizer.addIrregularRule(strArr2[0], strArr2[1]);
            i++;
        }
        String[][] strArr3 = {new String[]{"/s?$", "s"}, new String[]{"/([^aeiou]ese)$", "$1"}, new String[]{"/(ax|test)is$", "$1es"}, new String[]{"/(alias|[^aou]us|t[lm]as|gas|ris)$", "$1es"}, new String[]{"/(e[mn]u)s?$", "$1s"}, new String[]{"/([^l]ias|[aeiou]las|[ejzr]as|[iu]am)$", "$1"}, new String[]{"/(alumn|syllab|octop|vir|radi|nucle|fung|cact|stimul|termin|bacill|foc|uter|loc|strat)(?:us|i)$", "$1i"}, new String[]{"/(alumn|alg|vertebr)(?:a|ae)$", "$1ae"}, new String[]{"/(seraph|cherub)(?:im)?$", "$1im"}, new String[]{"/(her|at|gr)o$", "$1oes"}, new String[]{"/(agend|addend|millenni|medi|dat|extrem|bacteri|desiderat|strat|candelabr|errat|ov|symposi|curricul|automat|quor)(?:a|um)$", "$1a"}, new String[]{"/(apheli|hyperbat|periheli|asyndet|noumen|phenomen|criteri|organ|prolegomen|hedr|automat)(?:a|on)$", "$1a"}, new String[]{"/sis$", "ses"}, new String[]{"/(?:(kni|wi|li)fe|(ar|l|ea|eo|oa|hoo)f)$", "$1$2ves"}, new String[]{"/([^aeiouy]|qu)y$", "$1ies"}, new String[]{"/([^ch][ieo][ln])ey$", "$1ies"}, new String[]{"/(x|ch|ss|sh|zz)$", "$1es"}, new String[]{"/(matr|cod|mur|sil|vert|ind|append)(?:ix|ex)$", "$1ices"}, new String[]{"(m|l)(?:ice|ouse)", "$1ice"}, new String[]{"/(pe)(?:rson|ople)$", "$1ople"}, new String[]{"/(child)(?:ren)?$", "$1ren"}, new String[]{"/eaux$", SamWrapperCodegen.SAM_WRAPPER_SUFFIX}, new String[]{"/m[ae]n$", "men"}};
        int i3 = 0;
        for (int i4 = 23; i3 < i4; i4 = 23) {
            String[] strArr4 = strArr3[i3];
            pluralizer.addPluralRule(strArr4[0], strArr4[1]);
            i3++;
        }
        String[][] strArr5 = {new String[]{"/(.)s$", "$1"}, new String[]{"/([^aeiou]s)es$", "$1"}, new String[]{"/(wi|kni|(?:after|half|high|low|mid|non|night|[^\\w]|^)li)ves$", "$1fe"}, new String[]{"/(ar|(?:wo|[ae])l|[eo][ao])ves$", "$1f"}, new String[]{"/ies$", "y"}, new String[]{"/\\b([pl]|zomb|(?:neck|cross)?t|coll|faer|food|gen|goon|group|lass|talk|goal|cut)ies$", "$1ie"}, new String[]{"/\\b(mon|smil)ies$", "$1ey"}, new String[]{"(m|l)ice", "$1ouse"}, new String[]{"/(seraph|cherub)im$", "$1"}, new String[]{"/.(x|ch|ss|sh|zz|tto|go|cho|alias|[^aou]us|t[lm]as|gas|(?:her|at|gr)o|ris)(?:es)?$", "$1"}, new String[]{"/(analy|^ba|diagno|parenthe|progno|synop|the|empha|cri)(?:sis|ses)$", "$1sis"}, new String[]{"/(movie|twelve|abuse|e[mn]u)s$", "$1"}, new String[]{"/(test)(?:is|es)$", "$1is"}, new String[]{"/(x|ch|.ss|sh|zz|tto|go|cho|alias|[^aou]us|tlas|gas|(?:her|at|gr)o|ris)(?:es)?$", "$1"}, new String[]{"/(e[mn]u)s?$", "$1"}, new String[]{"/(cookie|movie|twelve)s$", "$1"}, new String[]{"/(cris|test|diagnos)(?:is|es)$", "$1is"}, new String[]{"/(alumn|syllab|octop|vir|radi|nucle|fung|cact|stimul|termin|bacill|foc|uter|loc|strat)(?:us|i)$", "$1us"}, new String[]{"/(agend|addend|millenni|dat|extrem|bacteri|desiderat|strat|candelabr|errat|ov|symposi|curricul|quor)a$", "$1um"}, new String[]{"/(apheli|hyperbat|periheli|asyndet|noumen|phenomen|criteri|organ|prolegomen|hedr|automat)a$", "$1on"}, new String[]{"/(alumn|alg|vertebr)ae$", "$1a"}, new String[]{"/(cod|mur|sil|vert|ind)ices$", "$1ex"}, new String[]{"/(matr|append)ices$", "$1ix"}, new String[]{"/(pe)(rson|ople)$", "$1rson"}, new String[]{"/(child)ren$", "$1"}, new String[]{"/(eau)x?$", "$1"}, new String[]{"/men$", "man"}};
        for (int i5 = 0; i5 < 27; i5++) {
            String[] strArr6 = strArr5[i5];
            pluralizer.addSingularRule(strArr6[0], strArr6[1]);
        }
        String[] strArr7 = {"adulthood", "advice", "agenda", "aid", "alcohol", "ammo", "anime", "athletics", "audio", "bison", "blood", "bream", "buffalo", "butter", "carp", "cash", "chassis", "chess", "clothing", "cod", "commerce", "cooperation", "corps", "debris", "diabetes", "digestion", "elk", "energy", "equipment", "excretion", "expertise", "flounder", "fun", "gallows", "garbage", "graffiti", "headquarters", "health", "herpes", "highjinks", "homework", "housework", "information", "jeans", "justice", "kudos", "labour", "literature", "machinery", "mackerel", "mail", "media", "mews", "moose", "music", "news", "pike", "plankton", "pliers", "police", "pollution", "premises", "rain", "research", "rice", "salmon", "scissors", "series", "sewage", "shambles", "shrimp", "species", "staff", "swine", "tennis", "traffic", "transportation", "trout", "tuna", "wealth", "welfare", "whiting", "wildebeest", "wildlife", "you", "/[^aeiou]ese$/i", "/deer$", "/fish$", "/measles$", "/o[iu]s$", "/pox$", "/sheep$"};
        for (int i6 = 0; i6 < 92; i6++) {
            pluralizer.addUncountableRule(strArr7[i6]);
        }
        PLURALIZER = pluralizer;
    }
}
