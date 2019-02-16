package com.thomasdillard.cars;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/cars")
public class CarController
{
	private final CarRepository carRepository;
	private final CarResourceAssembler assembler;
	private final RabbitTemplate rt;

	public CarController(CarRepository carRepository, CarResourceAssembler assembler, RabbitTemplate rt)
	{
		this.carRepository = carRepository;
		this.assembler = assembler;
		this.rt = rt;
	}

	@GetMapping("/id/(id)")
	public Resources<Resource<Car>> findOne(@PathVariable Long id)
	{
		List<Resource<Car>> car = carRepository.findById(id).stream()
				.map(assembler::toResource)
				.collect(Collectors.toList());

		return new Resources<>(car, linkTo(methodOn(CarController.class).findOne(id)).withSelfRel());
	}

	@GetMapping("/brand/{brand}")
	public Resources<Resource<Car>> findByBrand(@PathVariable String brand)
	{
		List<Resource<Car>> cars = carRepository.findAllByBrand(brand).stream()
				.map(assembler::toResource)
				.collect(Collectors.toList());

		return new Resources<>(cars, linkTo(methodOn(CarController.class).findByBrand(brand)).withSelfRel());
	}

	@GetMapping("/year/{year}")
	public Resources<Resource<Car>> findByYear(@PathVariable Long year)
	{
		List<Resource<Car>> cars = carRepository.findAllByYear(year).stream()
				.map(assembler::toResource)
				.collect(Collectors.toList());

		return new Resources<>(cars, linkTo(methodOn(CarController.class).findByYear(year)).withSelfRel());
	}

	@PostMapping("")
	public List<Car> postCars(@RequestBody List<Car> cars)
	{
		CarLog message = new CarLog("Data loaded");
		rt.convertAndSend(CarApplication.QUEUE_NAME, message.toJSON().toString());
		return carRepository.saveAll(cars);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteOne(@PathVariable Long id)
	{
		carRepository.deleteById(id);
		CarLog message = new CarLog(id + " Data deleted");
		rt.convertAndSend(CarApplication.QUEUE_NAME, message.toJSON().toString());
	}

}
