package tech.zerofiltre.testing.calcul.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ConcurrentModel;
import tech.zerofiltre.testing.calcul.domain.model.Calculation;
import tech.zerofiltre.testing.calcul.domain.model.CalculationModel;
import tech.zerofiltre.testing.calcul.domain.model.CalculationType;
import tech.zerofiltre.testing.calcul.repository.CalculationModelRepository;
import tech.zerofiltre.testing.calcul.service.CalculatorService;

@ExtendWith(SpringExtension.class)
public class CalculatorControllerTest {

  CalculatorController calculatorController;

  @MockBean
  CalculatorService calculatorService;

  @MockBean
  CalculationModelRepository calculationModelRepository;

  @BeforeEach
  void init(){
    calculatorController = new CalculatorController(calculatorService,calculationModelRepository);
  }

  @Test
  void calculate_mustUseCalculationModelRepository(){
    CalculationModel calculationModel = new CalculationModel(CalculationType.ADDITION,12,13);
    Calculation calculation = new Calculation();
    calculation.setCalculationType(CalculationType.ADDITION.name());
    calculation.setLeftArgument(12);
    calculation.setRightArgument(13);

    //GIVEN
    when(calculatorService.calculate(any())).thenReturn(calculationModel);
    when(calculationModelRepository.save(any())).thenReturn(calculationModel);

    //when
    calculatorController.calculate(calculation,null,new ConcurrentModel());

    //THEN
    verify(calculationModelRepository,times(1)).save(calculationModel);

  }

}
