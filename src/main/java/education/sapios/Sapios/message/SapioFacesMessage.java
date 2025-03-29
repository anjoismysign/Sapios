package education.sapios.Sapios.message;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.jetbrains.annotations.NotNull;

public enum SapioFacesMessage {
    INSTANCE;

    private void addMessage(@NotNull FacesMessage.Severity severity,
                            @NotNull String summary,
                            @NotNull String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    /**
     * Add an error message to the faces context
     *
     * @param detail The detail of the message
     */
    public void error(@NotNull String detail){
        addMessage(FacesMessage.SEVERITY_ERROR, "Error", detail);
    }

    /**
     * Add an info message to the faces context
     *
     * @param detail The detail of the message
     */
    public void info(@NotNull String detail){
        addMessage(FacesMessage.SEVERITY_INFO, "Info", detail);
    }
}
