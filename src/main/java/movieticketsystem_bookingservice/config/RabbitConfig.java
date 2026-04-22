package movieticketsystem_bookingservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "movie_topic_exchange";
    public static final String PAYMENT_RES_QUEUE = "booking.payment.res.queue";

    @Bean
    public TopicExchange movieExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue paymentResponseQueue() {
        // durable: true để tin nhắn không mất khi RabbitMQ restart
        return new Queue(PAYMENT_RES_QUEUE, true);
    }

    @Bean
    public Binding bindingPayment(Queue paymentResponseQueue, TopicExchange movieExchange) {
        // Lắng nghe tất cả các event bắt đầu bằng payment.
        return BindingBuilder.bind(paymentResponseQueue)
                .to(movieExchange)
                .with("payment.#");
    }

    // Quan trọng: Converter này giúp gửi/nhận Object dưới dạng JSON
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}