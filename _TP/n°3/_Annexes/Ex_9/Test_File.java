import java.awt.FileDialog;
import java.io.File;


public class Test_File {

	public static void main(String[] args) {
	File f= new File("/usr");
	System.out.println(f.getPath());
	
	FileDialog d= new FileDialog();
	
	// does the file exist
    if ( f.exists() )
        {
        System.out.println( "file exists" );
        }
    
    String[] files = f.list();

    System.out.println( "Files in this directory are:" );
    String file=null;
	    for (int i=0; i < files.length; i++)
	    {
	    		file=files[i];
	    		System.out.println( file );
	    }
	    
	System.out.println( "success in creating directory tree: "+ f.mkdir() );
	    
	}
}

/*
 * @(#)TestFile.java
 *
 * Summary: example use of java.io.File.
 *
 * Copyright: (c) 2009 Roedy Green, Canadian Mind Products, http://mindprod.com
 *
 * Licence: This software may be copied and used freely for any purpose but military.
 *          http://mindprod.com/contact/nonmil.html
 *
 * Requires: JDK 1.6+
 *
 * Created with: IntelliJ IDEA IDE.
 *
 * Version History:
 *  1.0 2009-01-01 - initial version
 */
/*package com.mindprod.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 * example use of java.io.File.
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.0 2009-01-01 - initial version
 * @since 2009-01-01
 */
/*public final class TestFile
    {
    // -------------------------- STATIC METHODS --------------------------

    /**
     * Test the various ways you can get the file name.
     *
     * @throws IOException on I/O failure.
     */
 /*   private static void testFileFileManipulationMethods() throws IOException
        {
        System.out.println( "Testing File name-getting methods." );
        System.out
                .println(
                        "There must exist a TEST file called E:\\genus\\species\\crocdile.html" );

        // set up handle to file name.
        File f = new File( "E:\\genus\\species\\crocodile.html" );

        // does the file exist
        if ( f.exists() )
            {
            System.out.println( "file exists" );
            }

        // how long is the file in bytes?
        System.out.println( "file is " + f.length() + " bytes long" );

        // get a list of all files in a directory
        File dir = new File( "E:\\genus\\species" );
        String[] files = dir.list();
        System.out.println( "Files in this directory are:" );
        for ( String file : files )
            {
            System.out.println( file );
            }

        // Can I read this file? Does item exist and I have permission to read item?
        System.out
                .println( "file "
                          + ( f.canRead() ? "can" : "cannot" )
                          + " be read" );

        // can I write to this file?
        System.out
                .println( "file "
                          + ( f.canWrite() ? "can" : "cannot" )
                          + " be written" );

        // when was this file last modified?
        System.out.println( "last modified: " + new Date( f.lastModified() ) );

        // is this a directory?
        System.out
                .println( "file "
                          + ( f.isDirectory() ? "is" : "is not" )
                          + " a valid existing directory" );

        // is this a files?
        System.out
                .println( "file "
                          + ( f.isFile() ? "is" : "is not" )
                          + " a valid existing file" );

        // get a list of the root drives.
        // Note. listRoots is a static method.
        File[] roots = File.listRoots();
        for ( File root : roots )
            {
            System.out.println( root );
            }

        // create a directory tree
        File dirs = new File( "C:\\temp\\silly\\preposterous" );
        // It is considered a failure if the directory tree already exists.
        System.out
                .println( "success in creating directory tree: "
                          + dirs.mkdirs() );

        // create a single directory level
        File dirOneLevel = new File( "C:\\temp\\absurd" );
        // It is considered a failure if the directory already exists.
        System.out
                .println( "success in creating one directory level: "
                          + dirOneLevel.mkdirs() );

        // set a file's length
        RandomAccessFile raf =
                new RandomAccessFile( new File(
                        "E:\\genus\\species\\rabbit.html" ), "rw" );
        raf.setLength( 50 );

        // set file read-only
        f.setReadOnly();

        // set last modified timestamp
        f
                .setLastModified( System.currentTimeMillis() - ( 24
                                                                 * 60
                                                                 * 60
                                                                 * 1000 ) );
        }

    /**
     * Test the various ways you can get the file name.
     *
     * @throws IOException on I/O failure.
     */
 /*   private static void testFileNameGettingMethods() throws IOException
        {
        System.out.println( "Testing File name-getting methods." );
        System.out
                .println(
                        "There must exist a TEST file called E:\\genus\\species\\crocdile.html" );
        System.out.println( "The current directory must be E:\\genus" );

        // current directory is E:/genus
        File currentDirectory = new File( "." );
        // prints E:\genus
        System.out.println( "dir: " + currentDirectory.getCanonicalPath() );

        // The pre-existing file we use for this TEST is called
        // E:/genus/species/crocodile.html, lower case. You must create item
        // manually before running this program in default directory E:\genus.

        // U S E _ R E L A T I V E _ N A M E
        System.out.println( "Using relative name" );
        File f = new File( "species/CROCODILE.HTML" );

        // prints species\CROCODILE.HTML
        System.out.println( "toString: " + f.toString() );

        // prints E:\genus\species\CROCODILE.HTML
        System.out.println( "absolutePath: " + f.getAbsolutePath() );

        // prints CROCODILE.HTML
        System.out.println( "name: " + f.getName() );

        // prints species\CROCODILE.HTML
        System.out.println( "path: " + f.getPath() );

        // prints species
        System.out.println( "parent: " + f.getParent() );

        // prints E:\genus\species\crocodile.html
        // If you get unexpected case, item is a symptom the file does not exist.
        System.out.println( "canonicalPath: " + f.getCanonicalPath() );

        // U S E _ A B S O L U T E _ N A M E
        System.out.println( "Using absolute name" );
        f = new File( "E:\\genus\\species\\CROCODILE.HTML" );

        // prints E:\genus\species\CROCODILE.HTML
        System.out.println( "toString: " + f.toString() );

        // prints E:\genus\species\CROCODILE.HTML
        System.out.println( "absolutePath: " + f.getAbsolutePath() );

        // prints CROCODILE.HTML
        System.out.println( "name: " + f.getName() );

        // prints E:\genus\species\CROCODILE.HTML
        System.out.println( "path: " + f.getPath() );

        // prints E:\genus\species
        System.out.println( "parent: " + f.getParent() );

        // prints E:\genus\species\crocodile.html
        System.out.println( "canonicalPath: " + f.getCanonicalPath() );

        // R E L A T I V E _ N A M E _ W I T H _ D R I V E
        System.out.println( "Using relative name with drive" );
        f = new File( "e:species\\CROCODILE.HTML" );

        // prints E:\genus\species\CROCODILE.HTML
        System.out.println( "absolutePath: " + f.getAbsolutePath() );

        // prints CROCODILE.HTML
        System.out.println( "name: " + f.getName() );

        // prints e:species
        System.out.println( "parent: " + f.getParent() );

        // prints e:species\CROCODILE.HTML
        System.out.println( "path: " + f.getPath() );

        // prints E:\genus\species\crocodile.html
        System.out.println( "canonicalPath: " + f.getCanonicalPath() );
        }

    /**
     * Test temporary files
     *
     * @throws IOException on I/O failure.
     */
 /*   private static void testTemporaryFiles() throws IOException
        {
        // Create a temporary file whose name is guaranteed to be unique.
        // prefix, suffix, directory to use
        // To put the temporary in the same directory as another file,
        // use getParent to find the directory.
        // It will NOT automatically be deleted on exit, unless you use
        // deleteOnExit.
        File temp = File
                .createTempFile( "temp", ".tmp", new File( "C:\\temp" ) );

        // Arrange for this temporary file to be automatically deleted on exit
        // if we forget to.
        temp.deleteOnExit();

        // Delete the temporary file now
        System.out.println( "Success of delete: " + temp.delete() );
        }

    // --------------------------- main() method ---------------------------

    /**
     * Exercise various File methods
     *
     * @param args not used
     *
     * @throws IOException on I/O failure.
     */
 /*   public static void main( String[] args ) throws IOException
        {
        testFileFileManipulationMethods();
        testFileNameGettingMethods();
        testTemporaryFiles();
        }
    }*/