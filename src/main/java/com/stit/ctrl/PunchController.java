package com.stit.ctrl;

import com.stit.dto.PunchCardDto;
import com.stit.svc.PunchService;
import com.stit.utils.RespUtils;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/punch")
public class PunchController {

	private final Logger log = LogManager.getLogger();

	@Autowired
	private PunchService punchService;

	/**
	 * login
	 *
	 * @param empNo requestParam
	 * @param chgPswd
	 * @return
	 */
	@RequestMapping(path = "login", method = RequestMethod.GET)
	public ResponseEntity login(@RequestParam String empNo, @RequestParam String chgPswd) {
		// log.info("empNo:" + empNo);
		// log.info("chgPswd:" + chgPswd);

		try {
			boolean login = punchService.login(empNo, chgPswd);
			if (login) {
				return RespUtils.ok("登入成功!");
			} else {
				return RespUtils.fail("登入失敗!");
			}
		} catch (Exception ex) {
			log.error("err", ex);
			return RespUtils.fail(ex.getMessage());
		}
	}

	/**
	 * 新增
	 * @param 	punchCardDto
	 * @return	ResponseEntity
	 */
	@PostMapping()
	public ResponseEntity insert(@RequestBody PunchCardDto punchCardDto) {
		try {
			PunchCardDto dto = this.punchService.insert(punchCardDto);
			log.debug("ret: {}",	dto);
			return RespUtils.ok(dto);
		} catch (Exception ex) {
			return RespUtils.fail(ex.getMessage());
		}
	}

	/**
	 * 打卡查詢
	 * @param 	punchCardDto
	 * @return	ResponseEntity
	 */
	@GetMapping("query")
	public ResponseEntity queryPunch(@RequestParam String listType ) {
		try {
			List<PunchCardDto> dtoList = this.punchService.queryPunch(listType);
			log.debug("ret: {}",	dtoList);
			return RespUtils.ok(dtoList);
		} catch (Exception ex) {
			return RespUtils.fail(ex.getMessage());
		}
	}



} // end class
