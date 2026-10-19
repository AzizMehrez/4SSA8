package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClientRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
        String nom,

        @NotBlank(message = "Le prénom est obligatoire")
        @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
        String prenom,

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Format d'email invalide")
        @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères")
        String email,

        @Pattern(regexp = "^$|^[+0-9][0-9\\s.-]{7,18}$", message = "Numéro de téléphone invalide")
        String telephone,

        @NotBlank(message = "Le numéro de permis est obligatoire")
        @Size(max = 30, message = "Le numéro de permis ne doit pas dépasser 30 caractères")
        String numPermis,

        @NotNull(message = "La date d'inscription est obligatoire")
        @PastOrPresent(message = "La date d'inscription ne peut pas être dans le futur")
        LocalDate dateInscription
) {
}
