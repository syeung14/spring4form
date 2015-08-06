package com.foliofn.verification.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeckeySettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date instatedTimestamp;
    private Date setupTimestamp;
    private Date changedTimestamp;
    private Date changeByTimestamp;
    private Date goodLoginTimestamp;
    private List<Date> badLoginTimestamps = new ArrayList<Date>();

    public SeckeySettings() {
        this(null, null, null, null, null, null);
    }

    public SeckeySettings(Date instatedTs, Date setupTs, Date modifiedTs, Date changeByTs, Date loginTs,
            List<Date> failedLoginsTs) {
        instatedTimestamp = instatedTs;
        setupTimestamp = setupTs;
        changedTimestamp = modifiedTs;
        changeByTimestamp = changeByTs;
        goodLoginTimestamp = loginTs;

        if (failedLoginsTs != null)
            badLoginTimestamps.addAll(failedLoginsTs);
    }

    public Date getInstatedTimestamp() {
        return instatedTimestamp;
    }

    public void setInstatedTimestamp(Date ts) {
        instatedTimestamp = ts;
    }

    public Date getSetupTimestamp() {
        return setupTimestamp;
    }

    public void setSetupTimestamp(Date ts) {
        setupTimestamp = ts;
    }

    public Date getChangedTimestamp() {
        return changedTimestamp;
    }

    public void setChangedTimestamp(Date ts) {
        changedTimestamp = ts;
    }

    public Date getChangeByTimestamp() {
        return changeByTimestamp;
    }

    public void setChangeByTimestamp(Date ts) {
        changeByTimestamp = ts;
    }

    public Date getGoodLoginTimestamp() {
        return goodLoginTimestamp;
    }

    public void setGoodLoginTimestamp(Date ts) {
        goodLoginTimestamp = ts;
    }

    /**
     * Removes all the bad login dates prior to good login timestamp.
     */
    public void trimBadLoginTimestamps() {
        List<Date> removeList = new ArrayList<Date>();
        Date goodLogin;

        goodLogin = getGoodLoginTimestamp();
        for (Date o : badLoginTimestamps) {
            if (o.before(goodLogin))
                removeList.add(o);
        }

        if (!removeList.isEmpty())
            badLoginTimestamps.removeAll(removeList);
    }

    public void addBadLoginTimestamp(Date date) {
        badLoginTimestamps.add(date);
    }

    public List<Date> getBadLoginTimestamps() {
        return new ArrayList<Date>(badLoginTimestamps);
    }

    public void setBadLoginTimestamps(List<Date> tss) {
        badLoginTimestamps.clear();
        if (tss != null)
            badLoginTimestamps.addAll(tss);
    }
}