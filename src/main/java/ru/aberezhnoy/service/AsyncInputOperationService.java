package ru.aberezhnoy.service;

import jakarta.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.config.OperationProperties;
import ru.aberezhnoy.domain.model.Operation;
import ru.aberezhnoy.dto.OperationDTO;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class AsyncInputOperationService {
    private static final Logger LOGGER = LogManager.getLogger(AsyncInputOperationService.class);
    private final OperationProperties operationProperties;
    private final Queue<OperationDTO.Request.OperationDto> operationQueue;
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
            OperationDTO.Request.OperationDto operationDto = operationQueue.poll();
            if (operationDto == null) {
                try {
                    LOGGER.info("Waiting for next operation in queue");
                    Thread.sleep(operationProperties.getSleepMilliSeconds());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Processing operation: " + operationDto.getOperationType());
                processOperation(operationDto);
            }
        }
    }

    private void processOperation(OperationDTO.Request.OperationDto operationDto) {
        operationService.save(operationDto);
    }

    public boolean addOperation(OperationDTO.Request.OperationDto operationDto) {
        System.out.println("Operation added for processing " + operationDto.getOperationType());
        return operationQueue.offer(operationDto);
    }
}
