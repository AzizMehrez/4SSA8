package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.Equipement;

public record EquipementResponse(
        Long idEquipement,
        String libelle
) {
    public static EquipementResponse from(Equipement equipement) {
        return new EquipementResponse(equipement.getIdEquipement(), equipement.getLibelle());
    }
}
