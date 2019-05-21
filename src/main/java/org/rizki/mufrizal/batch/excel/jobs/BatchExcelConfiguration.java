package org.rizki.mufrizal.batch.excel.jobs;

import lombok.extern.slf4j.Slf4j;
import org.rizki.mufrizal.batch.excel.domain.Agent;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 21 May 2019
 * @Time 21:46
 * @Project Batch-Excel
 * @Package org.rizki.mufrizal.batch.excel.jobs
 * @File BatchExcelConfiguration
 */
@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchExcelConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MessageStreamWriter messageStreamWriter;

    @Autowired
    private OtherWriter otherWriter;

    @Bean
    public BeanWrapperRowMapper<Agent> beanWrapperRowMapper() {
        BeanWrapperRowMapper<Agent> beanWrapperRowMapper = new BeanWrapperRowMapper<>();
        beanWrapperRowMapper.setTargetType(Agent.class);
        return beanWrapperRowMapper;
    }

    @Bean
    public PoiItemReader<Agent> poiItemReader() {
        PoiItemReader<Agent> poiItemReader = new PoiItemReader<>();
        poiItemReader.setResource(new ClassPathResource("data_sample.xlsx"));
        poiItemReader.setRowMapper(beanWrapperRowMapper());
        return poiItemReader;
    }

    @Bean
    public CompositeItemWriter compositeItemWriter() {
        List<ItemWriter> writers = new ArrayList<>();
        writers.add(messageStreamWriter);
        writers.add(otherWriter);
        CompositeItemWriter itemWriter = new CompositeItemWriter();
        itemWriter.setDelegates(writers);
        return itemWriter;
    }

    @Bean
    public Step stepWriteDB() {
        return stepBuilderFactory.get("stepWriteDB").<Agent, Agent>chunk(1).reader(poiItemReader())
                .writer(compositeItemWriter()).build();
    }

    @Bean
    public Job importPersonJob(JobCompletionNotificationListener jobCompletionNotificationListener) {
        return jobBuilderFactory.get("importPersonJob").incrementer(new RunIdIncrementer())
                .listener(jobCompletionNotificationListener).flow(stepWriteDB()).end().build();
    }
}