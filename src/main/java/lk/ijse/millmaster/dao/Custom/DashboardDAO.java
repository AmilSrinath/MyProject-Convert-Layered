package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.SuperDAO;

import java.sql.SQLException;

public interface DashboardDAO extends SuperDAO {
    public int OrderComplete() throws SQLException;
    public int OrderActive() throws SQLException;
    public int getRedRawSum5kg() throws SQLException;
    public int getRedRawSum10kg() throws SQLException;
    public int getRedRawSum25kg() throws SQLException;
    public int getKeerisambaSum5kg() throws SQLException;
    public int getKeerisambaSum10kg() throws SQLException;
    public int getKeerisambaSum25kg() throws SQLException;
    public int getWhiteRawSum5kg() throws SQLException;
    public int getWhiteRawSum10kg() throws SQLException;
    public int getWhiteRawSum25kg() throws SQLException;
    public int getSambaSum5kg() throws SQLException;
    public int getSambaSum10kg() throws SQLException;
    public int getSambaSum25kg() throws SQLException;
    public int getNaduSum5kg() throws SQLException;
    public int getNaduSum10kg() throws SQLException;
    public int getNaduSum25kg() throws SQLException;
    public int getRedNaduSum5kg() throws SQLException;
    public int getRedNaduSum10kg() throws SQLException;
    public int getRedNaduSum25kg() throws SQLException;
    public int getPaddyQuntityColSum() throws SQLException;
    public int getPaddySambaQuntityColSum() throws SQLException;
    public int getPaddyNaduQuntityColSum() throws SQLException;
    public int getPaddyRedNaduQuntityColSum() throws SQLException;
    public int getPaddyWhiteRawQuntityColSum() throws SQLException;
    public int getPaddyKeerisambaQuntityColSum() throws SQLException;
    public int getPaddyRedRowQuntityColSum() throws SQLException;
    public int getProductQuntityColSum() throws SQLException;
}
