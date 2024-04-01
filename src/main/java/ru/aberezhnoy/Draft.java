//package ru.aberezhnoy;
//
//import jakarta.annotation.PostConstruct;
//import ru.aberezhnoy.domain.model.CashbackOperation;
//import ru.aberezhnoy.domain.model.LoanOperation;
//import ru.aberezhnoy.domain.model.Operation;
//import ru.aberezhnoy.dto.OperationDTO;
//import ru.aberezhnoy.exception.CustomerNotFound;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Draft {
//    public static void main(String[] args) {
//
//
//        /**
//         * Method for create demo data
//         */
//        @PostConstruct
//        public void initStorage() {
//            Thread t = new Thread(() -> {
//                try {
//                    Thread.sleep(5000);
//
//                    List<Operation> operations = new ArrayList<>();
//                    operations.add(new LoanOperation(customerRepository.findById(1L).orElseThrow(() -> new CustomerNotFound(1L)), new BigDecimal(100000), "desc1", 1L));
//                    operations.add(new LoanOperation(customerRepository.findById(1L).orElseThrow(() -> new CustomerNotFound(1L)), new BigDecimal(200000), "desc2", 2L));
//                    operations.add(new LoanOperation(customerRepository.findById(1L).orElseThrow(() -> new CustomerNotFound(1L)), new BigDecimal(1234000), "desc3", 3L));
//                    operations.add(new CashbackOperation(customerRepository.findById(1L).orElseThrow(() -> new CustomerNotFound(1L)), new BigDecimal(14000), "desc4"));
//                    operations.add(new CashbackOperation(customerRepository.findById(1L).orElseThrow(() -> new CustomerNotFound(1L)), new BigDecimal(1000), "desc5"));
//                    operations.add(new CashbackOperation(customerRepository.findById(1L).orElseThrow(() -> new CustomerNotFound(1L)), new BigDecimal(560), "desc6"));
//
//                    operations.add(new LoanOperation(customerRepository.findById(2L).orElseThrow(() -> new CustomerNotFound(2L)), new BigDecimal(700000), "desc7", 4L));
//                    operations.add(new LoanOperation(customerRepository.findById(2L).orElseThrow(() -> new CustomerNotFound(2L)), new BigDecimal(1200000), "desc8", 5L));
//                    operations.add(new LoanOperation(customerRepository.findById(2L).orElseThrow(() -> new CustomerNotFound(2L)), new BigDecimal(4000), "desc9", 6L));
//                    operations.add(new CashbackOperation(customerRepository.findById(2L).orElseThrow(() -> new CustomerNotFound(2L)), new BigDecimal(14000), "desc10"));
//                    operations.add(new CashbackOperation(customerRepository.findById(2L).orElseThrow(() -> new CustomerNotFound(2L)), new BigDecimal(12345), "desc11"));
//                    operations.add(new CashbackOperation(customerRepository.findById(2L).orElseThrow(() -> new CustomerNotFound(2L)), new BigDecimal(56789), "desc12"));
//
//                    List<OperationDTO.Request.OperationDto> operationDtos = operations.stream().map(this::mapToOperationDto).toList();
//
//                    for (OperationDTO.Request.OperationDto o : operationDtos) {
//                        this.save(o);
//                    }
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//            t.start();
//            t.stop();
//        }
//    }
//}
