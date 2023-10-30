package local.example.batchservice.listener;


import static org.springframework.batch.core.BatchStatus.COMPLETED;
import static org.springframework.batch.core.BatchStatus.STARTED;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomJobListener implements JobExecutionListener  {
    
    @Override
    public void beforeJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == STARTED) {
            log.info("ORDER BATCH PROCESS STARTED...!");
        }
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == COMPLETED) {
            log.info("ORDER BATCH PROCESS COMPLETED...!");
        }
    }
}