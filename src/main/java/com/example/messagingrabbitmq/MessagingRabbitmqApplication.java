package com.example.messagingrabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
//@ComponentScan("com.example.messagingrabbitmq")
public class MessagingRabbitmqApplication {
	
	static final String topicExchangeName = "spring-boot-exchange";

	  static final String queueName = "spring-boot1";

	  @Bean
	  Queue queue() {
	    return new Queue(queueName, false);
	  }

	  @Bean
	  TopicExchange exchange() {
	    return new TopicExchange(topicExchangeName);
	  }

	  @Bean
	  Binding binding(Queue queue, TopicExchange exchange) {
	    return BindingBuilder.bind(queue).to(exchange).with("award");
	  }
	  
	  @Bean
	  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
	      final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	      rabbitTemplate.setMessageConverter(jsonMessageConverter());
	      return rabbitTemplate;
	  }

	  @Bean
	  public Jackson2JsonMessageConverter jsonMessageConverter() {
	      return new Jackson2JsonMessageConverter();
	  }

	  /*@Bean
	  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	      MessageListenerAdapter listenerAdapter) {
	    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames(queueName);
	      
	    
	    
	    
	    container.setMessageListener(listenerAdapter);
	    return container;
	  }*/

	 /* @Bean
	  MessageListenerAdapter listenerAdapter(Receiver receiver) {
	    return new MessageListenerAdapter(receiver, "receiveMessage");
	  }*/
	  
	  @Bean
	    public TaskExecutor threadPoolTaskExecutor() {
	        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setCorePoolSize(4);
	        executor.setMaxPoolSize(4);
	        executor.setThreadNamePrefix("default_task_executor_thread");
	        executor.initialize();
	        return executor;
	    }
	

	public static void main(String[] args) {
		SpringApplication.run(MessagingRabbitmqApplication.class, args);
		
	}

}
