package org.zerock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies")
@RestController
@Log4j
@AllArgsConstructor
public class ReplyController {

	/**
	 * @AllArgsConstructor를 이용해서 ReplyService 타입의 객체를
	 * 필요로 하는 생성자를 만들어 사용(Spring 4.3 이상에서 가능)
	 */
	private ReplyService service;

	@PostMapping(value = "/new",
			produces = { MediaType.TEXT_PLAIN_VALUE },
			consumes = "application/json")
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		log.info("ReplyVO: " + vo);
		int insertCount = service.register(vo);
		log.info("Reply INSERT COUNT:" + insertCount);

		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping(value = "/pages/{bno}/{page}")
	public ResponseEntity<List<ReplyVO>> getList(
			@PathVariable("bno") Long bno,
			@PathVariable("page") int page) {
		log.info("getList............");
		Criteria cri = new Criteria(page, 10);
		log.info(cri);

		return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK);
	}

	@GetMapping(value = "/{rno}",
			produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {
		log.info("get: " + rno);

		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{rno}", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
		log.info("remove: " + rno);

		return service.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "",
			method = { RequestMethod.PUT, RequestMethod.PATCH},
			produces = { MediaType.TEXT_PLAIN_VALUE },
			consumes = "application/json")
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo,
			@PathVariable("rno") Long rno) {
		vo.setRno(rno);
		log.info("rno : " + rno);
		log.info("modify: " + vo);

		return service.modify(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
