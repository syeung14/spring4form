package com.foliofn.entities.jdbc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.foliofn.verification.mfa.domain.Constants.ISPERSONAL;
import com.foliofn.verification.mfa.domain.MfaUserInfo;
import com.foliofn.verification.mfa.domain.UserDeviceInfo;


public class MfaLoginDeviceRepository {
	public Logger logger = LoggerFactory.getLogger(MfaLoginDeviceRepository.class);
	@Autowired
	@Qualifier("folio1")
	private JdbcTemplate folioJdbcTemplate;
	
	private static JdbcTemplate staticFolioJdbcTemplate;
	
	public JdbcTemplate getStaticFolioJdbcTemplate() {
		return staticFolioJdbcTemplate;
	}
	public void setStaticFolioJdbcTemplate(JdbcTemplate staticFolioJdbcTemplate) {
		MfaLoginDeviceRepository.staticFolioJdbcTemplate = staticFolioJdbcTemplate;
	}
	
	public List<String> findAllDeviceByLoginId(String loginId) {
		List<String> list = folioJdbcTemplate.query("select deviceid from ftc_user_device where loginid = ? and active = ?",
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("deviceid").trim();
					}
				}, loginId, "Y");
		return list;
	}
	
	static String deactivate =  "update ftc_user_device set active= ? where loginid =? ";
	public void deactivateDevice(String loginId) {
		folioJdbcTemplate.update(deactivate, new Object[]{"N",loginId});
		
	}
	public boolean createDevice(String loginId, String deviceId) {
		folioJdbcTemplate.update("insert into ftc_user_device (loginid, deviceid,lastverificationts,nextverificationts,ispersonal,active) values (?,?,?,?,?,?) " , 
				new Object[]{loginId, deviceId,new Date(), new Date(), "N","Y"});
		
		
		int aa = folioJdbcTemplate.update("IF (SELECT count(1) FROM ftc_device WHERE deviceid = ?) = 0 " +
							" BEGIN  " + 
							" 	insert into ftc_device (deviceid) values (? ) "  + 
							" END ",
			new Object[]{deviceId,deviceId});
		System.out.println(";;;;;");
		
		return true;
	}
	
	
	public boolean isCurrentDeviceActive(String deviceId, String userId) {
		int count  = folioJdbcTemplate.queryForInt("select count(1) " +
								" from ftc_user_device " + 
								" where loginid = ? " + 
								" and deviceid= ? " + 
								" and active = 'Y' ", new Object[] {userId, deviceId});
		
		return count > 0;
	}

	public boolean aresOtherDeviceActive(String deviceId, String userId) {
		int count  = folioJdbcTemplate.queryForInt("select count(1) " + 
					" from ftc_user_device " +
					" where loginid = ? " + 
					" and active = 'Y' " +
					" and deviceid != ? ", new Object[] {userId, deviceId});
		
		return count > 0;
	}
    

    public UserDeviceInfo getUserLatestCookie(String userID, String deviceId) {
        String query = "select loginid, deviceid, lastverificationts ,nextverificationts,ispersonal,active "
                + "from ftc_user_device where  deviceid = ? "
                + "group by deviceid having loginid = ? and nextverificationts > getdate() and ispersonal = ? and active = ? and nextverificationts = max(nextverificationts) ";
									        
        @SuppressWarnings("unchecked")
        List<UserDeviceInfo> userDeviceInfoSList = ( List<UserDeviceInfo>)folioJdbcTemplate.query( query,
            new Object[] {userID, deviceId, UserDeviceInfo.YES,  ISPERSONAL.Y.toString(), },
            new ParameterizedRowMapper<UserDeviceInfo>() {
                @Override
                public UserDeviceInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    UserDeviceInfo data = 
                            new UserDeviceInfo(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getDate(3),resultSet.getDate(4), 
                        resultSet.getString(5), 
                        resultSet.getString(6));
                    return data;
                }
            });
        if ( userDeviceInfoSList.isEmpty() ){
            return null;
          }else if ( userDeviceInfoSList.size() == 1 ) { // list contains exactly 1 element
            return userDeviceInfoSList.get(0);
          }else{ 
            logger.error(String.format("User '%s' has more then one active cookies on device '%s' ", userID, deviceId ));
            return null;
          }
    }
    
    public MfaUserInfo getUserInfo(String userID) {
        
        ParameterizedRowMapper<MfaUserInfo> rm = new ParameterizedRowMapper<MfaUserInfo>() {

            @Override
            public MfaUserInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {

                MfaUserInfo data = new MfaUserInfo(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
                return data;
            }
        };

        MfaUserInfo info = (MfaUserInfo)folioJdbcTemplate.queryForObject("SELECT m.loginid,m.email1, m.email2,m.hometelephone,m.worktelephone " + 
        		 " FROM ftc_member m " +
        		 " WHERE upper(m.loginid) = upper(?) ", new Object[] {userID}, rm);
        
        return info;
    }
    
    
}
