package glowna;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Generowanie_kluczy
{

    private KeyPairGenerator key_pair_generator;
    private KeyPair key_pair;
    private PrivateKey private_key;
    private PublicKey public_key;

    public Generowanie_kluczy(int dlugosc_klucza) throws NoSuchAlgorithmException, NoSuchProviderException
    {
        this.key_pair_generator = KeyPairGenerator.getInstance("RSA");
        this.key_pair_generator.initialize(dlugosc_klucza);
    }

    public void createKeys()
    {
        this.key_pair = this.key_pair_generator.generateKeyPair();
        this.private_key = key_pair.getPrivate();
        this.public_key = key_pair.getPublic();
    }

    public PrivateKey getPrivate_key()
    {
        return this.private_key;
    }

    public PublicKey getPublic_key()
    {
        return this.public_key;
    }

    public void zapisz_do_pliku(String sciezka_do_pliku, byte[] klucz) throws IOException
    {
        File file = new File(sciezka_do_pliku);
        file.getParentFile().mkdirs();

        FileOutputStream file_output_stream = new FileOutputStream(file);
        file_output_stream.write(klucz);
        file_output_stream.flush();
        file_output_stream.close();
    }

}