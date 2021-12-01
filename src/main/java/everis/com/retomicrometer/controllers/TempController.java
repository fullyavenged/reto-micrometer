package everis.com.retomicrometer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;


@RestController
@RequestMapping("/temp/")
public class TempController {

	private final static Logger logger= LoggerFactory.getLogger(TempController.class);
	private Counter counterC;
	private Counter counterF;
	private Counter counterConverter;
	
	public TempController(MeterRegistry mRegistry) {
		
		this.counterC = Counter.builder("celsius.invokations").description("Total celsius invokations").register(mRegistry);
		this.counterF = Counter.builder("fahrenheit.invokations").description("Total fahrenheit invokations").register(mRegistry);
		this.counterConverter = Counter.builder("converter.invokations").description("Total converter invokations").register(mRegistry);
	}
	
	
	@Value("${some.valueCels}")
	private Integer c;
	@Value("${some.valueF}")
	private Integer f;
	
	@GetMapping("/celsius")
	public Integer celsius() {
		counterC.increment();
		logger.info("Llamada al endpoint de grados celsius: "+ c);
		return c;
	}
	
	@GetMapping("/far")
	public Integer far() {
		counterF.increment();
		logger.info("Llamada al endpoint de grados fahrenheit: "+ f);
		return f;
	}
	
	@GetMapping("/convertCToF/{tempC}")
	public Integer cToF(@PathVariable Integer tempC) {
		counterConverter.increment();
		logger.info("Llamada al endpoint de conversion de celsius a fahrenheit con resultado:  "+ tempC);
		return (tempC*9/5)+32;
	}
}
