package local.example.batchservice.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import local.example.batchservice.component.OrderFieldMapper;
import local.example.batchservice.listener.CustomJobListener;
import local.example.batchservice.processor.OrderProcessor;
import local.example.entitymodule.entity.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class BatchImportOrdersConfig {
    static final String ORDER_ITEM_READER="orderItemReader";
    static final String ORDER_PROCESS_JOB="orderProcessJob";

    static final String BATCH_STEP="step";
        
    @Value("${app.csv.fileHeaders}")
    private String[] names;

    @Bean
    FlatFileItemReader<Order> reader(OrderFieldMapper fieldMapper) {
    	log.info("Load reader.");
    	BeanWrapperFieldSetMapper<Order> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Order.class);
        
        return new FlatFileItemReaderBuilder<Order>()
                .name(ORDER_ITEM_READER)
                .resource(new ClassPathResource("csv/orders.csv"))
                .linesToSkip(1)
                .delimited()
                .names(names)
                .lineMapper(lineMapper(fieldMapper))
                .fieldSetMapper(fieldSetMapper).build();
    }

    @Bean
    LineMapper<Order> lineMapper(OrderFieldMapper fieldMapper) {

        final DefaultLineMapper<Order> defaultLineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(names);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldMapper);

        return defaultLineMapper;
    }

    @Bean
    OrderProcessor processor() {
    	log.info("Load processor.");
        return new OrderProcessor();
    }

    @Bean
    JpaItemWriter<Order> writer(EntityManagerFactory entityManagerFactory) {
    	log.info("Load writer.");
        JpaItemWriter<Order> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager, JpaItemWriter<Order> writer, OrderFieldMapper fieldMapper) {
    	log.info("Load step "+ BATCH_STEP);
    	return new StepBuilder(BATCH_STEP, jobRepository)
                .<Order, Order> chunk(5, transactionManager)
                .reader(reader(fieldMapper))
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    Job job(JobRepository jobRepository, CustomJobListener listener, Step step) {
    	log.info("Load job "+ ORDER_PROCESS_JOB);
        return new JobBuilder(ORDER_PROCESS_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end()
                .build();
    }


}
