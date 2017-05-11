package de.codeboje.springbootbook.commentstore.restapi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.codeboje.springbootbook.commentstore.service.CommentService;
import de.codeboje.springbootbook.commons.CommentDTO;
import de.codeboje.springbootbook.model.CommentModel;

@RestController
public class ReadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReadController.class);

	@Autowired
	private CommentService service;

	@Autowired
	private CounterService counterService;

	@RequestMapping(value = "/list/{id}")
	public List<CommentDTO> getComments(@PathVariable(value = "id") String pageId) throws IOException {
		LOGGER.info("get comments for pageId {}", pageId);
		counterService.increment("commentstore.list_comments");
		List<CommentModel> r = service.list(pageId);
		if (r.isEmpty()) {
			LOGGER.info("get comments for pageId {} - not found", pageId);
			throw new FileNotFoundException("/list/" + pageId);
		}

		LOGGER.info("get comments for pageId {} - done", pageId);
		return transformToDTO(r);
	}

	@RequestMapping(value = "/listspam/{id}")
	public List<CommentDTO> getSpamComments(@PathVariable(value = "id") String pageId) throws IOException {
		LOGGER.info("get spam comments for pageId {}", pageId);
		counterService.increment("commentstore.list_spamcomments");
		List<CommentModel> r = service.listSpamComments(pageId);
		LOGGER.info("get spam comments for pageId {} - done", pageId);
		return transformToDTO(r);
	}

	private List<CommentDTO> transformToDTO(List<CommentModel> r) {
		List<CommentDTO> result = new LinkedList<CommentDTO>();

		for (CommentModel m : r) {
			CommentDTO dto = transformModelToDTO(m);
			result.add(dto);
		}
		return result;
	}

	private CommentDTO transformModelToDTO(CommentModel m) {
		CommentDTO dto = new CommentDTO();
		dto.setId(m.getId());
		dto.setUsername(m.getUsername());
		dto.setEmailAddress(m.getEmailAddress());
		dto.setComment(m.getComment());
		dto.setCreated(m.getCreationDate());
		dto.setSpam(m.isSpam());
		return dto;
	}

	@RequestMapping(value = "/{id}", method=RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public CommentDTO getComment(@PathVariable(value = "id") String commentId) throws IOException {
		LOGGER.info("get single comments with id {}", commentId);
		counterService.increment("commentstore.get_comment");
		CommentModel comment = service.get(commentId);
		if (comment == null) {
			throw new FileNotFoundException("/" + commentId);
		}
		LOGGER.info("get single comments with id {} - done", commentId);
		return transformModelToDTO(comment);
	}

	@RequestMapping(value = "/comments")
	public Page<CommentModel> listComments(@RequestParam(name="page", required=false) String pageIn,
			@RequestParam(name="size", required=false) String pageSizeIn) throws IOException {
		LOGGER.info("list comments");
		counterService.increment("commentstore.admin.list_comments");
		final PageRequest pageRequest = new PageRequest(getAsInteger(pageIn, 0), getAsInteger(pageSizeIn, 5), new Sort(Direction.DESC, "creationDate"));
	
		Page<CommentModel> r = service.list(pageRequest);
		
		LOGGER.info("list comments - done");
		return r;
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public void handle404(Exception ex, Locale locale) {
		LOGGER.debug("Resource not found {}", ex.getMessage());
	}
	
	protected int getAsInteger(String in, int defaultValue) {
		if(StringUtils.isNoneBlank(in) && in.matches("^\\d+$")) {
			return Integer.valueOf(in);
		}
		return defaultValue;
	}
}
