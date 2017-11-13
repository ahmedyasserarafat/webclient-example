package org.sego.webclient.example;

import java.time.Duration;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class WebClientController {

	@Autowired
	protected WebClient webClient;
	
	// Works
	@PostConstruct
	public void foo() {
		System.out.println(getPeopleRsp(2l).block());
		System.out.println(getPeopleRsp(3l).block());
		System.out.println(getPeopleRsp(4l).block());
		System.out.println(getPeopleRsp(5l).block());
	}

	///
	// Don't works (>1 time). Freeze application
	///
	@GetMapping(path = "/people1/{id}", produces = "application/json;charset=UTF-8")
	public String getPeopleBlock(@PathVariable Long id) {
		System.out.println("People block request: " + id);
		return getPeopleRsp(id).block();
	}

	// Works
	@GetMapping(path = "/people2/{id}")
	public Mono<String> getPeople(@PathVariable Long id) {
		System.out.println("People request: " + id);
		return getPeopleRsp(id);
	}

	// Works
	@GetMapping(path = "/hello1")
	public String getHelloBlock() {
		System.out.println("Hello block request");
		return getHelloRsp().block();
	}

	// Works
	@GetMapping(path = "/hello2")
	public Mono<String> getHello() {
		System.out.println("Hello request");
		return getHelloRsp();
	}

	private Mono<String> getPeopleRsp(Long id) {
		String url = "https://swapi.co/api/people/" + id + "/?format=json";
		return webClient.get().uri(url).accept(MediaType.APPLICATION_JSON_UTF8).header("User-Agent", "Chrome")
				.exchange().log().flatMap(r -> r.bodyToMono(String.class));
	}

	private Mono<String> getHelloRsp() {
		return Mono.fromSupplier(new Supplier<String>() {
			@Override
			public String get() {
				return "Hello";
			}
		}).delayElement(Duration.ofSeconds(2));
	}

}