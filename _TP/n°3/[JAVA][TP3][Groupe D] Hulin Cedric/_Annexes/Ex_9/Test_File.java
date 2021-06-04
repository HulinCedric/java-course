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
