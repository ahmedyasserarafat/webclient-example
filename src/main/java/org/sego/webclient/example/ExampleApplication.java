package org.sego.webclient.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
@EnableCaching
public class ExampleApplication {
	
	Logger log = LoggerFactory.getLogger(ExampleApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}

    @Bean
    WebClient webClient() {
        return WebClient.create();
        /*return WebClient.builder().filter(new ExchangeFilterFunction() {
			
			@Override
			public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction next) {
		           log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
		           clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
		           Mono<ClientResponse> rsp = next.exchange(clientRequest);
		           rsp.log().subscribe(new Consumer<ClientResponse>() {

						@Override
						public void accept(ClientResponse t) {
							t.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
							t.bodyToMono(Map.class).log().doOnEach(new Consumer<Signal<Map>>() {
								@Override
								public void accept(Signal<Map> t) {
									log.info(String.valueOf(t.get()));
									
								}
							}).subscribe();
						}
		           });
		           return rsp;
			}
		}).build();*/
    }
}
