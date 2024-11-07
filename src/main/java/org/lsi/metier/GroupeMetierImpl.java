// File: src/main/java/org/lsi/metier/GroupeMetierImpl.java
package org.lsi.metier;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.lsi.dao.EmployeRepository;
import org.lsi.dao.GroupeRepository;
import org.lsi.entities.Client;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupeMetierImpl implements GroupeMetier {

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private EmployeRepository employeRepository;


    @Override
    public Groupe saveGroupe(Groupe g) {
// TODO Auto-generated method stub
        return groupeRepository.save(g);
    }


    @Override
    public List<Groupe> listGroupe() {
// TODO Auto-generated method stub
        return groupeRepository.findAll();
    }

    @Override
    public void deleteGroupe(long Codegroupe ) {
// TODO Auto-generated method stub
        groupeRepository.deleteById(Codegroupe);
    }

    @Override
    public Groupe assignEmployeesToGroupe(Long codeGroupe, List<Long> employeIds) {
        // Récupérer le groupe
        Groupe groupe = groupeRepository.findById(codeGroupe)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec l'ID : " + codeGroupe));

        // Parcourir la liste d'IDs d'employés et les ajouter au groupe
        for (Long codeEmploye : employeIds) {
            Employe employe = employeRepository.findById(codeEmploye)
                    .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'ID : " + codeEmploye));

            // Ajouter l'employé au groupe
            groupe.getEmploye().add(employe);

            // Ajouter le groupe à l'employé (pour maintenir la relation bidirectionnelle)
            employe.getGroupes().add(groupe);
        }

        // Sauvegarder les changements
        groupeRepository.save(groupe);

        // Retourner le groupe avec les employés ajoutés
        return groupe;
    }
}




