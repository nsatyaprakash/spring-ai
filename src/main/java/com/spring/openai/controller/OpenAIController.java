package com.spring.openai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.parser.ListOutputParser;
import org.springframework.ai.parser.MapOutputParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.openai.model.Author;

@RestController
public class OpenAIController {
	
	private ChatClient chatClient;
	
	@Value("classpath:/prompts/youtube.st")
	private Resource youtube;
	@Value("classpath:/prompts/artist.st")
	private Resource artist;
	@Value("classpath:/prompts/author.st")
	private Resource author;
	@Value("classpath:/prompts/books.st")
	private Resource books;
	
	public OpenAIController(ChatClient chatClient) {
		this.chatClient = chatClient;
	}
	
	@GetMapping("/dad-jokes")
	public String generate(@RequestParam(value ="message", defaultValue = "Tell me a dad jokes?") String message) {
		
		return chatClient.call(message);
	}
	
	@GetMapping("/popular")
	public String findPopularYoutuber(@RequestParam(value ="genre", defaultValue = "tech") String genre) {
		PromptTemplate promptTemplate = new PromptTemplate(this.youtube);
		Prompt prompt = promptTemplate.create(Map.of("genre", genre));
		return chatClient.call(prompt).getResult().getOutput().getContent();
	}
	
	@GetMapping("/songs")
	public List<String> getSongsByArtist(@RequestParam(value ="artist", defaultValue = "Arijit Singh") String artist) {
		
		ListOutputParser outputParser = new ListOutputParser(new DefaultConversionService());
		String format = outputParser.getFormat();
		
		PromptTemplate promptTemplate = new PromptTemplate(this.artist, Map.of("artist", artist, "format", format));
		Prompt prompt = promptTemplate.create();
		return outputParser.parse(chatClient.call(prompt).getResult().getOutput().getContent());
	}
	
	@GetMapping("/author")
	public Map<String, Object> getAuthorSocialLinks(@RequestParam(value ="author", defaultValue = "George R R Martin") String author) {
		
		MapOutputParser outputParser = new MapOutputParser();
		String format = outputParser.getFormat();
		
		PromptTemplate promptTemplate = new PromptTemplate(this.author, Map.of("author", author, "format", format));
		Prompt prompt = promptTemplate.create();
		return outputParser.parse(chatClient.call(prompt).getResult().getOutput().getContent());
	}
	
	@GetMapping("/books")
	public Author getBooksByAuthor(@RequestParam(value ="author", defaultValue = "George R R Martin") String author) {
		
		var outputParser = new BeanOutputParser<>(Author.class);
		String format = outputParser.getFormat();
		
		PromptTemplate promptTemplate = new PromptTemplate(this.books, Map.of("author", author, "format", format));
		Prompt prompt = promptTemplate.create();
		return outputParser.parse(chatClient.call(prompt).getResult().getOutput().getContent());
	}
}


