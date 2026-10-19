package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AgenceRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
        String nom,

        @NotBlank(message = "La ville est obligatoire")
        @Size(max = 50, message = "La ville ne doit pas dépasser 50 caractères")
        String ville,

        @NotBlank(message = "L'adresse est obligatoire")
        @Size(max = 200, message = "L'adresse ne doit pas dépasser 200 caractères")
        String adresse,

        @Pattern(regexp = "^$|^[+0-9][0-9\\s.-]{7,18}$", message = "Numéro de téléphone invalide")
        String telephone
) {
}
