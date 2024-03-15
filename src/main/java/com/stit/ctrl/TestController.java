package com.stit.ctrl;

import com.stit.common.Pair;
import com.stit.utils.RespUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "/api/test")
public class TestController {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private JdbcTemplate jdbc;

	@GetMapping()
	public ResponseEntity hello() {
		log.trace("Hello ...");

		try {
			Pair<String, String> pair = new Pair<>("oraclex", "甲骨文");
			return RespUtils.ok(pair);
		} catch (Exception ex) {
			return RespUtils.fail(ex.getMessage());
		}
	}

} // end class
