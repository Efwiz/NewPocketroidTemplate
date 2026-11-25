package org.eclipse.jgit.merge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.ChangeIdUtil;
import org.eclipse.jgit.util.StringUtils;

/* JADX INFO: loaded from: classes13.dex */
public class MergeMessageFormatter {
    public String format(List<Ref> list, Ref ref) {
        StringBuilder sb = new StringBuilder("Merge ");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        for (Ref ref2 : list) {
            if (ref2.getName().startsWith(Constants.R_HEADS)) {
                arrayList.add(FormatHelper.QUOTE + Repository.shortenRefName(ref2.getName()) + FormatHelper.QUOTE);
            } else if (ref2.getName().startsWith(Constants.R_REMOTES)) {
                arrayList2.add(FormatHelper.QUOTE + Repository.shortenRefName(ref2.getName()) + FormatHelper.QUOTE);
            } else if (ref2.getName().startsWith(Constants.R_TAGS)) {
                arrayList3.add(FormatHelper.QUOTE + Repository.shortenRefName(ref2.getName()) + FormatHelper.QUOTE);
            } else {
                ObjectId objectId = ref2.getObjectId();
                if (objectId != null && ref2.getName().equals(objectId.getName())) {
                    arrayList4.add(FormatHelper.QUOTE + ref2.getName() + FormatHelper.QUOTE);
                } else {
                    arrayList5.add(ref2.getName());
                }
            }
        }
        ArrayList arrayList6 = new ArrayList();
        if (!arrayList.isEmpty()) {
            arrayList6.add(joinNames(arrayList, ConfigConstants.CONFIG_BRANCH_SECTION, "branches"));
        }
        if (!arrayList2.isEmpty()) {
            arrayList6.add(joinNames(arrayList2, "remote-tracking branch", "remote-tracking branches"));
        }
        if (!arrayList3.isEmpty()) {
            arrayList6.add(joinNames(arrayList3, "tag", "tags"));
        }
        if (!arrayList4.isEmpty()) {
            arrayList6.add(joinNames(arrayList4, "commit", "commits"));
        }
        if (!arrayList5.isEmpty()) {
            arrayList6.add(StringUtils.join(arrayList5, ", ", " and "));
        }
        sb.append(StringUtils.join(arrayList6, ", "));
        String name2 = ref.getLeaf().getName();
        if (!name2.equals("refs/heads/master")) {
            sb.append(" into " + Repository.shortenRefName(name2));
        }
        return sb.toString();
    }

    public String formatWithConflicts(String str, List<String> list) {
        StringBuilder sb = new StringBuilder();
        String[] strArrSplit = str.split("\n");
        int iIndexOfFirstFooterLine = ChangeIdUtil.indexOfFirstFooterLine(strArrSplit);
        for (int i = 0; i < iIndexOfFirstFooterLine; i++) {
            sb.append(strArrSplit[i]).append('\n');
        }
        if (iIndexOfFirstFooterLine == strArrSplit.length && str.length() != 0) {
            sb.append('\n');
        }
        addConflictsMessage(list, sb);
        if (iIndexOfFirstFooterLine < strArrSplit.length) {
            sb.append('\n');
        }
        while (iIndexOfFirstFooterLine < strArrSplit.length) {
            sb.append(strArrSplit[iIndexOfFirstFooterLine]).append('\n');
            iIndexOfFirstFooterLine++;
        }
        return sb.toString();
    }

    private static void addConflictsMessage(List<String> list, StringBuilder sb) {
        sb.append("Conflicts:\n");
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            sb.append('\t').append(it.next()).append('\n');
        }
    }

    private static String joinNames(List<String> list, String str, String str2) {
        if (list.size() == 1) {
            return String.valueOf(str) + " " + list.get(0);
        }
        return String.valueOf(str2) + " " + StringUtils.join(list, ", ", " and ");
    }
}
