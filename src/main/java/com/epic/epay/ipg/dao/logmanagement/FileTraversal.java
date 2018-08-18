/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.logmanagement;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ruwan_e
 */
public class FileTraversal {
        public final void traverse( final File f ) throws IOException {
                if (f.isDirectory()) {
                        onDirectory(f);
                        final File[] childs = f.listFiles();
                        for( File child : childs ) {
                                traverse(child);
                        }
                        return;
                }
                onFile(f);
        }

        public void onDirectory( final File d ) {
        }

        public void onFile( final File f ) {
        }
}
