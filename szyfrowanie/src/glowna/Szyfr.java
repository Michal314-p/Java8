package glowna;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Szyfr
{
    private Cipher cipher;

    public Szyfr() throws NoSuchAlgorithmException, NoSuchPaddingException
    {
        this.cipher = Cipher.getInstance("RSA");
    }

    public PrivateKey getPrivate(String nazwa_pliku) throws Exception
    {
        byte[] bajty_klucz = Files.readAllBytes(new File(nazwa_pliku).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bajty_klucz);
        KeyFactory key_factory = KeyFactory.getInstance("RSA");
        return key_factory.generatePrivate(spec);
    }

    public PublicKey getPublic(String nazwa_pliku) throws Exception
    {
        byte[] bajty_klucz = Files.readAllBytes(new File(nazwa_pliku).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bajty_klucz);
        KeyFactory key_factory = KeyFactory.getInstance("RSA");
        return key_factory.generatePublic(spec);
    }

    public void zaszyfruj_plik(byte[] wejscie, File output, PublicKey klucz) throws IOException, GeneralSecurityException
    {
        this.cipher.init(Cipher.ENCRYPT_MODE, klucz);
        zapisz_do_pliku(output, this.cipher.doFinal(wejscie));
    }

    public void odszyfruj_plik(byte[] wejscie, File wyjscie, PrivateKey klucz) throws IOException, GeneralSecurityException
    {
        this.cipher.init(Cipher.DECRYPT_MODE, klucz);
        zapisz_do_pliku(wyjscie, this.cipher.doFinal(wejscie));
    }

    private void zapisz_do_pliku(File wyjscie, byte[] do_zapisu) throws IOException
    {
        FileOutputStream file_output_stream = new FileOutputStream(wyjscie);
        file_output_stream.write(do_zapisu);
        file_output_stream.flush();
        file_output_stream.close();
    }

    public byte[] wczytaj_bajty_z_pliku(File file) throws IOException
    {
        FileInputStream fis = new FileInputStream(file);
        byte[] fbytes = new byte[(int) file.length()];
        fis.read(fbytes);
        fis.close();
        return fbytes;
    }
}
