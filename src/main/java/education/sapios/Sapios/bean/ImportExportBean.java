package education.sapios.Sapios.bean;

import education.sapios.Sapios.entity.hallway.Course;
import education.sapios.Sapios.repository.CourseRepository;
import education.sapios.Sapios.service.CourseImportExportService;
import jakarta.faces.application.FacesMessage;
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
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a course to export");
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
        } catch (IOException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error exporting course: " + e.getMessage());
        }
    }
    
    /**
     * Import a course from a JSON file
     */
    public void importCourse() {
        if (uploadedFile == null || uploadedFile.getContent() == null || uploadedFile.getContent().length == 0) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a file to upload");
            return;
        }
        
        try {
            String json = new String(uploadedFile.getContent(), StandardCharsets.UTF_8);
            Course course = importExportService.importCourse(json);
            addMessage(FacesMessage.SEVERITY_INFO, "Success", "Course '" + course.getName() + "' imported successfully");
            
            // Refresh courses list
            courses = null;
        } catch (IOException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error importing course: " + e.getMessage());
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
    
    /**
     * Add a message to the faces context
     * 
     * @param severity The severity of the message
     * @param summary The summary of the message
     * @param detail The detail of the message
     */
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
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
