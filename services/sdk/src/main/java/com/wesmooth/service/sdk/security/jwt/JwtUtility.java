/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.security.jwt;

import com.google.gson.Gson;
import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import com.wesmooth.service.sdk.security.jwt.dto.Jwt;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JWT Utility bean which creates a JWT string from {@link Jwt} instance or transforms a JWT string
 * to {@link Jwt} instance.<br>
 * <br>
 * <b>NOTE: </b> the provided public and private keys should be generated with <b>RSA</b> algorithm
 * in <b>PKCS8</b> format.
 *
 * @author Boris Georgiev
 */
@Component
public class JwtUtility {
  private final Gson gson;
  private final String privateKey;
  private final String publicKey;

  @Autowired
  public JwtUtility(Gson gson, ApplicationProperties applicationProperties)
      throws URISyntaxException, IOException {
    this.gson = gson;
    this.privateKey =
        normalizePrivateKey(
            Files.readString(
                Path.of(
                    ClassLoader.getSystemResource(
                            applicationProperties.getProperty("wesmooth.security.key.private"))
                        .toURI())));
    this.publicKey =
        normalizePublicKey(
            Files.readString(
                Path.of(
                    ClassLoader.getSystemResource(
                            applicationProperties.getProperty("wesmooth.security.key.public"))
                        .toURI())));
  }

  // Use this site to generate private / public keys
  // https://acte.ltd/utils/openssl
  // Required algorithm is RSA and format PKCS8

  private String normalizePrivateKey(String privateKey) {
    return privateKey
        .replace("-----BEGIN PRIVATE KEY-----", "")
        .replaceAll(System.lineSeparator(), "")
        .replace("-----END PRIVATE KEY-----", "");
  }

  private String normalizePublicKey(String privateKey) {
    return privateKey
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replaceAll(System.lineSeparator(), "")
        .replace("-----END PUBLIC KEY-----", "");
  }

  /**
   * Decrypts and transforms a JWT to {@link Jwt} object and verifies the signature.
   *
   * @param jwt the JWT in string format
   * @return the decrypted and transformed JWT
   * @throws NoSuchAlgorithmException exception during decryption
   * @throws InvalidKeySpecException exception during decryption
   * @throws NoSuchPaddingException exception during decryption
   * @throws InvalidKeyException exception during decryption
   * @throws IllegalBlockSizeException exception during decryption
   * @throws BadPaddingException exception during decryption
   */
  public Jwt decrypt(String jwt)
      throws NoSuchAlgorithmException,
          InvalidKeySpecException,
          NoSuchPaddingException,
          InvalidKeyException,
          IllegalBlockSizeException,
          BadPaddingException {
    byte[] privateKeyBytes = Base64.getDecoder().decode(this.privateKey);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
    RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
    Cipher rsaCipher = Cipher.getInstance("RSA");
    rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);

    String[] jwtContent = jwt.split("\\.");
    Jwt.Header header =
        gson.fromJson(
            new String(Base64.getDecoder().decode(jwtContent[0]), StandardCharsets.UTF_8),
            Jwt.Header.class);
    Jwt.Payload payload =
        gson.fromJson(
            new String(Base64.getDecoder().decode(jwtContent[1]), StandardCharsets.UTF_8),
            Jwt.Payload.class);

    byte[] decryptedBytes = rsaCipher.doFinal(Base64.getUrlDecoder().decode(jwtContent[2]));
    String signature = new String(decryptedBytes, StandardCharsets.UTF_8);

    String[] signatureContent = signature.split("\\.");
    Jwt.Header headerFromSignature =
        gson.fromJson(
            new String(Base64.getDecoder().decode(signatureContent[0])), Jwt.Header.class);
    Jwt.Payload payloadFromSignature =
        gson.fromJson(
            new String(Base64.getDecoder().decode(signatureContent[1])), Jwt.Payload.class);

    // Signature verification
    assert header.equals(headerFromSignature) && payload.equals(payloadFromSignature);

    return new Jwt(headerFromSignature, payloadFromSignature);
  }

  /**
   * Creates and encrypts JWT string from the provided {@link Jwt} object.
   *
   * @param jwt the JWT containing the content of the token
   * @return JWT in string format
   * @throws NoSuchAlgorithmException exception during decryption
   * @throws InvalidKeySpecException exception during decryption
   * @throws NoSuchPaddingException exception during decryption
   * @throws InvalidKeyException exception during decryption
   * @throws IllegalBlockSizeException exception during decryption
   * @throws BadPaddingException exception during decryption
   */
  public String createJwt(Jwt jwt)
      throws NoSuchAlgorithmException,
          InvalidKeySpecException,
          NoSuchPaddingException,
          InvalidKeyException,
          IllegalBlockSizeException,
          BadPaddingException {
    byte[] publicKeyBytes = Base64.getDecoder().decode(this.publicKey);
    KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
    EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
    PublicKey publicKey = publicKeyFactory.generatePublic(publicKeySpec);
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);

    Base64.Encoder encoder = Base64.getEncoder().withoutPadding();

    String encodedHeader =
        encoder.encodeToString(this.gson.toJson(jwt.getHeader()).getBytes(StandardCharsets.UTF_8));
    String encodedPayload =
        encoder.encodeToString(this.gson.toJson(jwt.getPayload()).getBytes(StandardCharsets.UTF_8));

    String unencryptedHeaderAndPayload = encodedHeader + "." + encodedPayload;

    String signatureEncoded =
        Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(cipher.doFinal(unencryptedHeaderAndPayload.getBytes()));

    String jwtStr = unencryptedHeaderAndPayload + "." + signatureEncoded;

    return jwtStr;
  }
}
