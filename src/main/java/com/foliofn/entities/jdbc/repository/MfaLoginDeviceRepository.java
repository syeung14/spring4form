package com.foliofn.entities.jdbc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class MfaLoginDeviceRepository {

	@Autowired
	@Qualifier("folio1")
	private JdbcTemplate folioJdbcTemplate;

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
    

	
}
