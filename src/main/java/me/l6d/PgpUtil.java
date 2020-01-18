/*
 * Copyright 2018 Monowi and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.l6d;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;

import java.io.*;
import java.security.SecureRandom;

public class PgpUtil {

    private static Logger logger = LogManager.getLogger("PgpUtil");

    public static void encryptFile(OutputStream out, String fileName, PGPPublicKey encKey, boolean armor, boolean withIntegrityCheck)
            throws IOException {
        if (armor) {
            out = new ArmoredOutputStream(out);
        }

        try {
            PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(
                    new JcePGPDataEncryptorBuilder(PGPEncryptedData.CAST5)
                            .setWithIntegrityPacket(withIntegrityCheck)
                            .setSecureRandom(new SecureRandom()).setProvider("BC"));
            cPk.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(encKey).setProvider("BC"));

            OutputStream cOut = cPk.open(out, new byte[1 << 16]);
            PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(PGPCompressedData.ZIP);
            PGPUtil.writeFileToLiteralData(comData.open(cOut), PGPLiteralData.BINARY, new File(fileName), new byte[1 << 16]);

            comData.close();
            cOut.close();

        } catch (PGPException e) {
            logger.error(e);
            if (e.getUnderlyingException() != null) {
                e.getUnderlyingException().printStackTrace();
            }
        } finally {
            out.close();
        }
    }
}
