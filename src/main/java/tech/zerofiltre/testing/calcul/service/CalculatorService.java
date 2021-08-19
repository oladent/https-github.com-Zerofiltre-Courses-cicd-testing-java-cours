package tech.zerofiltre.testing.calcul.service;

import tech.zerofiltre.testing.calcul.domain.model.CalculationModel;

public interface CalculatorService {
	/**
	 * Effectuer le calcul demandé par un modèle
	 * 
	 * @param calculationModel Modèle de calcul
	 * @return Modèle de calcul rempli du résultat 
	 */
	public CalculationModel calculate(CalculationModel calculationModel);
}
