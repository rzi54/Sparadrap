package testunitaire;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.luzi.defense.Regex;

public class RegexTest {

    @Test
    public void testIsValidPasswordTypeStrong() {
        assertTrue(Regex.isValidPasswordTypeStrong("Passw0rd!"));
        assertFalse(Regex.isValidPasswordTypeStrong("password")); // Pas de majuscule
        assertFalse(Regex.isValidPasswordTypeStrong("P@ssword")); // Pas de chiffre
        assertFalse(Regex.isValidPasswordTypeStrong("p@ssw0rd")); // Pas de majuscule
        assertFalse(Regex.isValidPasswordTypeStrong("P@ssw0r")); // Moins de 8 caractères
    }

    @Test
    public void testIsValidTypePseudonyme1() {
        assertTrue(Regex.isValidTypePseudonyme1("User123"));
        assertTrue(Regex.isValidTypePseudonyme1("user-name"));
        assertFalse(Regex.isValidTypePseudonyme1("User!Name")); // Caractère spécial
    }

    @Test
    public void testIsValidTypePseudonyme2() {
        assertTrue(Regex.isValidTypePseudonyme2("User123"));
        assertTrue(Regex.isValidTypePseudonyme2("user-name"));
        assertFalse(Regex.isValidTypePseudonyme2("User!Name")); // Caractère spécial
        assertFalse(Regex.isValidTypePseudonyme2("-UserName")); // Tiret en début
        assertFalse(Regex.isValidTypePseudonyme2("UserName-_/")); // Tiret en fin
        assertFalse(Regex.isValidTypePseudonyme2("-UserName-")); // Tirets en début et fin
    }

    @Test
    public void testIsValidTypeName() {
        assertTrue(Regex.isValidTypeName("John-Doe"));
        assertTrue(Regex.isValidTypeName("Élise"));
        assertFalse(Regex.isValidTypeName("123John")); // Commence par un chiffre
        assertFalse(Regex.isValidTypeName("J")); // Trop court
        assertFalse(Regex.isValidTypeName("ThisIsAReallyLongNameEtCeTestContinue")); // Trop long
    }

    @Test
    public void testIsValidTypeNameEnterprise() {
        assertTrue(Regex.isValidTypeNameEnterprise("MyCompany Inc."));
        assertTrue(Regex.isValidTypeNameEnterprise("ABC 123 & Co."));
        assertFalse(Regex.isValidTypeNameEnterprise("MyCompany @Inc.}~~")); // Caractère spécial
    }

    @Test
    public void testIsValidEmail() {
        assertTrue(Regex.isValidEmail("user@example.com"));
        assertTrue(Regex.isValidEmail("john.doe123@subdomain.example.co.uk"));
        assertFalse(Regex.isValidEmail("invalid-email")); // Pas de @
        assertFalse(Regex.isValidEmail("user@.com")); // Pas de nom de domaine
        assertFalse(Regex.isValidEmail("user@ex@mple.com")); // Caractère spécial dans le domaine
    }

    @Test
    public void testIsValidPhoneNumberFr() {
        assertTrue(Regex.isValidPhoneNumberFr("0123456789"));
        assertTrue(Regex.isValidPhoneNumberFr("+33123456789"));
        assertTrue(Regex.isValidPhoneNumberFr("0033123456789"));
        assertFalse(Regex.isValidPhoneNumberFr("123456789")); // Pas d'indicatif
        assertFalse(Regex.isValidPhoneNumberFr("+33 123456789")); // Espace non autorisé
        assertFalse(Regex.isValidPhoneNumberFr("+3312345678910")); // Trop de chiffres
    }

    @Test
    public void testIsValidPhoneNumber() {
        assertTrue(Regex.isValidPhoneNumber("+123456789"));
        assertTrue(Regex.isValidPhoneNumber("+1 234 567 89"));
        assertFalse(Regex.isValidPhoneNumber("123456789")); // Pas de +
        assertFalse(Regex.isValidPhoneNumber("+")); // Pas de chiffres après le +
        assertFalse(Regex.isValidPhoneNumber("+123451513216789102010")); // Trop de chiffres
    }

    @Test
    public void testIsValidAddressNumAndStreet() {
        assertTrue(Regex.isValidAddressNumAndStreet("3 rue de la commanderie"));
        assertTrue(Regex.isValidAddressNumAndStreet("5A Rue de la Liberté"));
        assertFalse(Regex.isValidAddressNumAndStreet("NoStreetName")); // Pas de numéro
        assertFalse(Regex.isValidAddressNumAndStreet("123@Main Street")); // Caractère spécial
    }

    @Test
    public void testIsValidPostalCode() {
        assertTrue(Regex.isValidPostalCode("12345"));
        assertFalse(Regex.isValidPostalCode("1234")); // Moins de 5 chiffres
        assertFalse(Regex.isValidPostalCode("123456")); // Plus de 5 chiffres
        assertFalse(Regex.isValidPostalCode("A1234")); // Lettres présentes
    }

    @Test
    public void testIsValidCityFr() {
        assertTrue(Regex.isValidCityFr("Paris"));
        assertTrue(Regex.isValidCityFr("Marseille-Étoile"));
        assertFalse(Regex.isValidCityFr("123City")); // Chiffres présents
        assertFalse(Regex.isValidCityFr("City!")); // Caractère spécial
    }

    @Test
    public void testIsValidAddressEuFR() {
        assertTrue(Regex.isValidAddressEuFR("123 Rue de la Liberté\n75001 Paris"));
        assertFalse(Regex.isValidAddressEuFR("Adresse invalide")); // Format incorrect
    }

    @Test
    public void testIsValidnumberRate2Digits() {
        assertTrue(Regex.isValidnumberRate2Digits("5"));
        assertTrue(Regex.isValidnumberRate2Digits("5,50"));
        assertTrue(Regex.isValidnumberRate2Digits("10,50"));
        assertFalse(Regex.isValidnumberRate2Digits(",5")); // Pas de chiffres avant la virgule
        assertFalse(Regex.isValidnumberRate2Digits("12,345")); // Trop de chiffres après la virgule
        assertFalse(Regex.isValidnumberRate2Digits("5,")); // Pas de chiffres après la virgule
    }
}
