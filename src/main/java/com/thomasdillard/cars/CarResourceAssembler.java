package com.thomasdillard.cars;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class CarResourceAssembler implements ResourceAssembler<Car, Resource<Car>>
{
	@Override
	public Resource<Car> toResource(Car car)
	{
		return new Resource<Car>(car);
	}
}
