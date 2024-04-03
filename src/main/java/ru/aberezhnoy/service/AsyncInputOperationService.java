package ru.aberezhnoy.service;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.config.OperationProperties;
import ru.aberezhnoy.dto.DemoOperationDTO;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class AsyncInputOperationService {
    private static final Logger LOGGER = LogManager.getLogger(AsyncInputOperationService.class);
    private final OperationProperties operationProperties;
    private final Queue<DemoOperationDTO> operationQueue;
    private final OperationService operationService;

    @Autowired
    public AsyncInputOperationService(OperationService operationService, OperationProperties operationProperties) {
        this.operationQueue = new LinkedList<>();
        this.operationService = operationService;
        this.operationProperties = operationProperties;
    }

    @PostConstruct
    public void init() {
        this.startAsyncOperationProcessing();
    }

    public void startAsyncOperationProcessing() {
        Thread t = new Thread(this::processQueue);
        t.start();
    }

    private void processQueue() {
        while (true) {
            DemoOperationDTO demoOperationDto = operationQueue.poll();
            if (demoOperationDto == null) {
                try {
                    LOGGER.info("Waiting for next operation in queue");
                    Thread.sleep(operationProperties.getSleepMilliSeconds());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                LOGGER.info("Processing operation: {}", demoOperationDto.getOperationType());
                processOperation(demoOperationDto);
            }
        }
    }

    private void processOperation(DemoOperationDTO demoOperationDto) {
        operationService.create(demoOperationDto);
    }

    public boolean addOperation(DemoOperationDTO demoOperationDto) {
        System.out.println("Operation added for processing " + demoOperationDto.getOperationType());
        return operationQueue.offer(demoOperationDto);
    }
}
