package ee461l.groupstudyendpoints;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Subclass;

import java.util.ArrayList;
import java.lang.Byte;

/**
 * Created by britne on 4/28/15.
 */
@Entity
@Subclass
@Cache
public class FilesEntity {

    @Id
    String id;
    private String fileName;
    private String fileContents;


    public FilesEntity() {

    }

    public FilesEntity(String fileName, String fileContents) {
        setId(fileName);
        setFileName(fileName);
        setFileContents(fileContents);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getFileContents() {
        return fileContents;
    }

    public void setFileContents(String fileContents) {
        this.fileContents = fileContents;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
