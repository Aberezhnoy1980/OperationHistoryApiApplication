package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.contract.Contract;
import ru.aberezhnoy.domain.model.Operation;
import ru.aberezhnoy.dto.OperationDTO;
import ru.aberezhnoy.exception.DataSourceException;
import ru.aberezhnoy.util.mapper.OperationDTOMapper;
import ru.aberezhnoy.util.mapper.OperationModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OperationService {
    private final Contract.Model<Operation> operationRepository;
    private final OperationDTOMapper operationDTOMapper;
    private final OperationModelMapper operationModelMapper;

    @Autowired
    public OperationService(Contract.Model<Operation> operationRepository, OperationDTOMapper operationDTOMapper, OperationModelMapper operationModelMapper) {
        this.operationRepository = operationRepository;
        this.operationDTOMapper = operationDTOMapper;
        this.operationModelMapper = operationModelMapper;
    }

    public List<OperationDTO> findAll() {
        return operationRepository.findAll().orElseThrow(DataSourceException::new)
                .stream()
                .map(operationDTOMapper)
                .collect(Collectors.toList());
    }

    public Optional<OperationDTO> findById(long id) {
        return operationRepository.findById(id).map(operationDTOMapper);
    }

    public Operation save(OperationDTO operationDto) {
        return operationRepository.save(operationModelMapper.apply(operationDto));
    }

    public void removeById(long id) {
        operationRepository.removeById(id);
    }

    public Contract.Model<Operation> getOperationRepository() {
        return operationRepository;
    }

    public OperationDTOMapper getOperationDTOMapper() {
        return operationDTOMapper;
    }

    public OperationModelMapper getOperationModelMapper() {
        return operationModelMapper;
    }
}
