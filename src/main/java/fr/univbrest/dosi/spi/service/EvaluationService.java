package fr.univbrest.dosi.spi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.dao.EvaluationRepository;
/**
 * 
 * @author Othman
 *
 */
@Service
public class EvaluationService {
	
	@Autowired
	EvaluationRepository evaRepo;
	
	@Autowired
	EnseignantService ensServ;
	
	@Autowired
	AuthentificationService authService;
	
	public List<Evaluation> getAllEvaluations(){
		return (List<Evaluation>) evaRepo.findAll();
	}
	
	public Evaluation getEvaluation(Integer idEvaluation){
		return evaRepo.findByIdEvaluation(idEvaluation);
	}
	
	public void addEvaluation(Evaluation e){
		Enseignant ens = ensServ.getEnseignant(1);
		e.setNoEnseignant(ens);
		evaRepo.save(e);
	}
	
	public void deleteEvaluation(Integer idEvaluation){
		Evaluation evaluation = evaRepo.findByIdEvaluation(idEvaluation);
		evaRepo.delete(evaluation);
	}
	
	public void updateEvaluation(Evaluation e){
		evaRepo.save(e);
	}
	
	public int nombreEvaluations(){
		return (int) evaRepo.count();
	}
	/**
	 * @author Othman
	 * @param codeFormation
	 * @param anneeUniversitaire
	 * @return le nombre d'évaluations concernant une promotion donnée
	 */
	public int nombreEvaluationsPromo(String codeFormation, String anneeUniversitaire){
		List<Evaluation> listeEvaluation = evaRepo.findByFormationAndAnnee(codeFormation, anneeUniversitaire);
		List<Evaluation> listeEvaluationsSorted = new ArrayList<Evaluation>();
		for(Evaluation eva : listeEvaluation){
			if(!(eva.getEtat().equalsIgnoreCase("ELA"))){
				listeEvaluationsSorted.add(eva);
			}
		}
		return listeEvaluationsSorted.size();
	}
	/**
	 * @author Othman
	 * @param codeFormation
	 * @param anneeUniversitaire
	 * @return liste d'évaluations concernant une promotion
	 */
	public List<Evaluation> getEvaluationsPromo(String codeFormation, String anneeUniversitaire){
		List<Evaluation> listeEvaluation = evaRepo.findByFormationAndAnnee(codeFormation, anneeUniversitaire);
		List<Evaluation> listeEvaluationsSorted = new ArrayList<Evaluation>();
		for(Evaluation eva : listeEvaluation){
			if(!(eva.getEtat().equalsIgnoreCase("ELA"))){
				listeEvaluationsSorted.add(eva);
			}
		}
		Collections.sort(listeEvaluationsSorted, new Comparator<Evaluation>() {
			@Override
			public int compare(Evaluation eva1, Evaluation eva2) {
				return eva1.getDesignation().compareTo(eva2.getDesignation());
			}
		});
		return listeEvaluationsSorted;
	}
	
	/**
	 * @author Othman
	 * @param enseignant
	 * @return liste des évaluations concernant un enseignant
	 */
	public List<Evaluation> getEvaluationsEnseignant(Enseignant enseignant){
		List<Evaluation> listeEvaluation = evaRepo.findByNoEnseignant(enseignant);
		Collections.sort(listeEvaluation, new Comparator<Evaluation>() {
			@Override
			public int compare(Evaluation eva1, Evaluation eva2) {
				return eva1.getDesignation().compareTo(eva2.getDesignation());
			}
		});
		return listeEvaluation;
	}
}