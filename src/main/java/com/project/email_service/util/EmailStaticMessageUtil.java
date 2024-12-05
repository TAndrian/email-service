package com.project.email_service.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class EmailStaticMessageUtil {
    public static final String NO_REPLY_CONTENT =
            "<strong>Merci de ne pas répondre</strong>" +
                    "<br/>" +
                    "<br/>" +
                    "<p>Bonjour Madame, Monsieur, </p>" +
                    "<br/>" +
                    "<p>J'espère que vous allez bien.</p>" +
                    "<p>Merci de m'avoir contacter, je prendrai connaissance de votre email dès que possible.</p>" +
                    "<br/>" +
                    "<p>Excellente " + getDayName() + " à vous.</p>" +
                    "<p>Cordialement</p>";

    /**
     * Get day name based on the current time.
     *
     * @return "journée" if it is before 6pm, "soirée" otherwise.
     */
    private static String getDayName() {
        LocalDate now = LocalDate.now();
        return now.isBefore(LocalDate.parse("18:00")) ? "journée" : "soirée";
    }
}
