package org.catrobat.catroid.utils;

import android.content.Context;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.eclipse.jgit.transport.WalkEncryption;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/HtmlRegexExtractor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HtmlRegexExtractor {
    private Context context;

    public HtmlRegexExtractor(Context context) {
        this.context = context;
    }

    public String searchKeyword(String keyword, String html) {
        String strHtmlToRegexConverter = htmlToRegexConverter(findKeyword(keyword, html), html);
        if (strHtmlToRegexConverter != null) {
            return strHtmlToRegexConverter;
        }
        showError();
        return "";
    }

    private void showError() {
        ToastUtil.showError(this.context, R.string.formula_editor_function_regex_html_extractor_not_found);
    }

    public String findKeyword(String keyword, String html) {
        if (keyword.equals("")) {
            return null;
        }
        return html.contains(keyword) ? keyword : findKeywordWithHtmlBetweenWordsInText(keyword, html);
    }

    private String findKeywordWithHtmlBetweenWordsInText(String keywords, String html) {
        String[] strArrSplit = keywords.split(WalkEncryption.Vals.REGEX_WS);
        StringBuilder sbAppend = new StringBuilder(keywords.length()).append(Pattern.quote(strArrSplit[0]));
        for (int i = 1; i < strArrSplit.length; i++) {
            sbAppend.append("(\\s|&nbsp;|<[^>]+>)+?");
            sbAppend.append(Pattern.quote(strArrSplit[i]));
        }
        Matcher matcher = Pattern.compile(sbAppend.toString()).matcher(html);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public String htmlToRegexConverter(String keyword, String html) {
        String str;
        if (keyword == null) {
            return null;
        }
        int iIndexOf = html.indexOf(keyword);
        if (keyword.equals(html) || iIndexOf < 0) {
            return "(.+)";
        }
        int i = 0;
        while (true) {
            i++;
            int iMax = Math.max(0, iIndexOf - i);
            String strSubstring = html.substring(iMax, iIndexOf);
            int iMin = Math.min(keyword.length() + iIndexOf + i, html.length());
            str = Pattern.quote(strSubstring) + "(.+?)" + Pattern.quote(html.substring(keyword.length() + iIndexOf, iMin));
            if (matchesFirst(str, html, keyword) || (iMax <= 0 && iMin >= html.length())) {
                break;
            }
        }
        return str;
    }

    private boolean matchesFirst(String pattern, String html, String expectedMatch) {
        Matcher matcher = Pattern.compile(pattern).matcher(html);
        return matcher.find() && expectedMatch.equals(matcher.group(1));
    }
}
