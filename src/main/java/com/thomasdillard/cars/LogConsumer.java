package com.thomasdillard.cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogConsumer
{
	@RabbitListener(queues = CarApplication.QUEUE_NAME)
	public void	consumeMessage(final String cm)
	{
		log.info("Received Message: {}", cm);
	}
}
