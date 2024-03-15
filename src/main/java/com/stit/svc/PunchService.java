package com.stit.svc;

import com.stit.dto.PunchCardDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Transactional
public class PunchService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PunchService.class);

    private SimpleDateFormat dateFmt = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * login
     *
     * @param empNo
     * @param chgPswd
     * @return
     */
    public boolean login(String empNo, String chgPswd) {
        String sql
                = "select count(*) from pwd_emp_pswd where "
                + " emp_no = ? and chg_pswd = ?";

        int count = jdbc.queryForObject(sql, new Object[]{empNo, chgPswd}, Integer.class);

        return count != 0;
    }

    /**
     * 打卡
     *
     * @param punchCardDto
     * @return
     */
    public PunchCardDto insert(PunchCardDto punchCardDto) {
        String sql
                = "insert into punch_card (emp_id, punch_time, lat, lng, kind)"
                + " values(?, ?, ?, ?, ?)";

        log.debug("{}", punchCardDto);

        LocalDateTime now = LocalDateTime.now();
        Timestamp punchTime = java.sql.Timestamp.valueOf(now);

        Object[] args = {
            punchCardDto.getEmpId(),
            punchTime,
            punchCardDto.getLat(),
            punchCardDto.getLng(),
            punchCardDto.getKind()
        };

        this.jdbc.update(sql, args);
        punchCardDto.setPunchTime(now);
        return punchCardDto;
    }

    public List<PunchCardDto> queryPunch(String listType) {
        log.debug("listType: {}", listType);
        String sql
                = "SELECT * "
                + "FROM punch_card "
                + "WHERE ((1 = ?"
                + "        AND TO_CHAR(punch_time,'YYYYMMDD') = TO_CHAR(SYSDATE,'YYYYMMDD')) OR"
                + "       (2 = ?"
                + "        AND TO_CHAR(punch_time,'YYYYMM') = TO_CHAR(SYSDATE,'YYYYMM')) OR"
                + "       (3 = ?"
                + "        AND TO_CHAR(punch_time,'YYYYMM') = TO_CHAR(SYSDATE,'YYYYMM') - 1)"
                + "      )"
                + "order by punch_time";
        
        Object[] args = {
            listType,
            listType,
            listType
        };
        
        List<PunchCardDto> dtoList = this.jdbc.query(sql, args, this::map2Dto);
        return dtoList;
    }

    private PunchCardDto map2Dto(ResultSet rs, int i) throws SQLException {
        PunchCardDto dto = new PunchCardDto();
        dto.setEmpId(rs.getString("emp_id"));
        dto.setLat(rs.getBigDecimal("lat"));
        dto.setLng(rs.getBigDecimal("lng"));
        dto.setKind(rs.getString("kind"));

        Timestamp timestamp = rs.getTimestamp("punch_time");
        dto.setPunchTime(timestamp.toLocalDateTime());

        return dto;
    }

}  // end class
