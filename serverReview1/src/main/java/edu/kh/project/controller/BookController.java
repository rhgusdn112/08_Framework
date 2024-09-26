package edu.kh.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.project.dto.Book;

@Controller
@RequestMapping("book")
public class BookController {

	@GetMapping("select")
	public String selectBook(@ModelAttribute Book book, Model model) {

		model.addAttribute("book", book);

		return "book/result";
	}
}
