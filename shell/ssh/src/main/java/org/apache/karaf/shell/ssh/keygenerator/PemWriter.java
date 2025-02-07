/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.karaf.shell.ssh.keygenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.ssl.PEMItem;
import org.apache.commons.ssl.PEMUtil;

public class PemWriter {
    private Path privateKeyPath;
    private Path publicKeyPath;

    public PemWriter(Path privateKeyPath, Path publicKeyPath) {
        this.privateKeyPath = privateKeyPath;
        this.publicKeyPath = publicKeyPath;
    }

    public void writeKeyPair(String resource, KeyPair kp) throws IOException, FileNotFoundException {
        Collection<Object> items = new ArrayList<>();

        items.add(new PEMItem(kp.getPrivate().getEncoded(), "PRIVATE KEY"));
        byte[] bytes = PEMUtil.encode(items);
        try (OutputStream os = Files.newOutputStream(privateKeyPath)) {
            os.write(bytes);
        }

        items.clear();
        items.add(new PEMItem(kp.getPublic().getEncoded(), "PUBLIC KEY"));
        bytes = PEMUtil.encode(items);
        try (OutputStream os = Files.newOutputStream(publicKeyPath)) {
            os.write(bytes);
        }
    }
}
