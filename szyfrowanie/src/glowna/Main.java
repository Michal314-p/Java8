package glowna;

import java.io.File;
import java.io.IOException;
import java.security.*;
import java.util.Scanner;


public class Main {
    static PrivateKey private_key;
    static PublicKey public_key;

    public static void main(String[] args) throws Exception
    {
        Scanner wybor = new Scanner(System.in);
        String wybrana_opcja= null;

        while (!"end".equals(wybrana_opcja)) {
            System.out.println("Wybierz co chcesz zrobic");
            System.out.println("-------------------------\n");
            System.out.println("1 - Wygeneruj klucz");
            System.out.println("2 - Wczytaj klucz");
            System.out.println("3 - Zaszyfruj tekst z pliku");
            System.out.println("4 - Odszyfruj tekst z pliku");
            wybrana_opcja = wybor.nextLine();
            if ("1".equals(wybrana_opcja))
            {
                Generowanie_kluczy generowanie_kluczy;
                try {
                    generowanie_kluczy = new Generowanie_kluczy(1024);
                    generowanie_kluczy.createKeys();
                    generowanie_kluczy.zapisz_do_pliku("KeyPair/publicKey", generowanie_kluczy.getPublic_key().getEncoded());
                    generowanie_kluczy.zapisz_do_pliku("KeyPair/privateKey", generowanie_kluczy.getPrivate_key().getEncoded());
                } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                    System.err.println(e.getMessage());
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
                wybrana_opcja = null;
            }
            if ("2".equals(wybrana_opcja))
            {
                Szyfr szyfr = new Szyfr();
                private_key = szyfr.getPrivate("KeyPair/privateKey");
                public_key = szyfr.getPublic("KeyPair/publicKey");
                wybrana_opcja = null;
            }
            if ("3".equals(wybrana_opcja))
            {
                Szyfr szyfr = new Szyfr();
                szyfr.zaszyfruj_plik(szyfr.wczytaj_bajty_z_pliku(new File("KeyPair/text.txt")), new File("KeyPair/text_zaszyfrowany.txt"),public_key);
                wybrana_opcja = null;
            }
            if ("4".equals(wybrana_opcja))
            {
                Szyfr szyfr = new Szyfr();
                szyfr.odszyfruj_plik(szyfr.wczytaj_bajty_z_pliku(new File("KeyPair/text_zaszyfrowany.txt")), new File("KeyPair/text_odszyfrowany.txt"), private_key);
                wybrana_opcja = null;
            }
        }
        wybor.close();

    }
}