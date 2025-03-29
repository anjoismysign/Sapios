package education.sapios.Sapios.bean;

import education.sapios.Sapios.entity.hallway.Course;
import education.sapios.Sapios.message.SapioFacesMessage;
import education.sapios.Sapios.repository.CourseRepository;
import education.sapios.Sapios.service.CourseImportExportService;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.file.UploadedFile;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Named
@ViewScoped
public class ImportExportBean implements Serializable {
    private static final SapioFacesMessage SENDER = SapioFacesMessage.INSTANCE;
    private static final long serialVersionUID = 1L;
    
    @Inject
    private CourseRepository courseRepository;
    
    @Inject
    private CourseImportExportService importExportService;
    
    private Course selectedCourse;
    private UploadedFile uploadedFile;
    private List<Course> courses;
    
    /**
     * Export the selected course to a JSON file
     */
    public void exportCourse() {
        if (selectedCourse == null) {
            SENDER.error("Please select a course to export");
            return;
        }

        try {
            String json = importExportService.exportCourse(selectedCourse.getId());
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            
            externalContext.responseReset();
            externalContext.setResponseContentType("application/json");
            externalContext.setResponseHeader("Content-Disposition", 
                    "attachment; filename=\"course_" + selectedCourse.getId() + ".json\"");
            
            OutputStream outputStream = externalContext.getResponseOutputStream();
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            
            facesContext.responseComplete();
        } catch (IOException exception) {
            SENDER.error(  "Error exporting course: " + exception.getMessage());
        }
    }
    
    /**
     * Import a course from a JSON file
     */
    public void importCourse() {
        if (uploadedFile == null || uploadedFile.getContent() == null || uploadedFile.getContent().length == 0) {
            SENDER.error(  "Please select a file to upload");
            return;
        }
        
        try {
            String json = new String(uploadedFile.getContent(), StandardCharsets.UTF_8);
            Course course = importExportService.importCourse(json);
            SENDER.info("Course '" + course.getName() + "' imported successfully");
            
            // Refresh courses list
            courses = null;
        } catch (IOException exception) {
            SENDER.error("Error importing course: " + exception.getMessage());
        }
    }
    
    /**
     * Get all courses
     * 
     * @return List of all courses
     */
    public List<Course> getCourses() {
        if (courses == null) {
            courses = courseRepository.findAll();
        }
        return courses;
    }
    
    // Getters and setters
    
    public Course getSelectedCourse() {
        return selectedCourse;
    }
    
    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
    
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }
    
    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
}
