package com.mcommerce.microserviceexpedition.web.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mcommerce.microserviceexpedition.dao.ExpeditionDao;
import com.mcommerce.microserviceexpedition.model.Expedition;
import com.mcommerce.microserviceexpedition.web.exception.ExpeditionNotFoundException;
import com.mcommerce.microserviceexpedition.web.exception.ImpossibleAjouterExpeditionException;

@RestController
public class ExpeditionController {

	@Autowired
	ExpeditionDao expeditionDao;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@PostMapping(value = "/expeditions")
	public ResponseEntity<Expedition> ajouterExpedition(@RequestBody Expedition expedition) {
		log.info("*************** microservice-expedition : ajouterExpedition avec expedition = " + expedition);

		Expedition nouvelleExpedition = expeditionDao.save(expedition);

		if (nouvelleExpedition == null)
			throw new ImpossibleAjouterExpeditionException("Impossible d'ajouter cette expedition");

		return new ResponseEntity<Expedition>(expedition, HttpStatus.CREATED);
	}

	@GetMapping(value = "/expeditions/{id}")
	public Optional<Expedition> recupererUneExpedition(@PathVariable int id) {
		log.info("*************** microservice-expedition : recupererUneExpedition avec id = " + id);

		Optional<Expedition> expedition = expeditionDao.findById(id);

		if (!expedition.isPresent())
			throw new ExpeditionNotFoundException("Cette expedition n'existe pas");

		return expedition;
	}

	/*
	 * Permet de mettre à jour une expedition existante.
	 * save() mettra à jours uniquement les champs renseignés dans l'objet expedition reçu. Ainsi dans ce cas, comme le champs date dans "expedition" n'est
	 * pas renseigné, la date précédemment enregistrée restera en place
	 **/
	@PutMapping(value = "/expeditions")
	public void updateExpedition(@RequestBody Expedition expedition) {
		log.info("*************** microservice-expedition : updateExpedition avec expedition = " + expedition);

		// Vérifie l'existance de l'expedition avant modification
		Optional<Expedition> expeditionRecu = expeditionDao.findById(expedition.getId());
		if(!expeditionRecu.isPresent()) throw new ExpeditionNotFoundException("Cette expedition n'existe pas");

		Expedition nouvelleExpedition = expeditionDao.save(expedition);

		if (nouvelleExpedition == null)
			throw new ImpossibleAjouterExpeditionException("Impossible d'ajouter cette expedition");
	}

}
